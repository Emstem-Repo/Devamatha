package com.kp.cms.handlers.attendance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceTimeRestrictionClasswise;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.HolidayBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.attendance.ActivityTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.IApproveLeaveTransaction;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.IMobileSMSCriteriaTransaction;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.ApproveLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.MobileSMSCriteriaTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ExamComparatorForAlphanumeric;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.StudentRollNoComparator;

/**
 * This is a handler class for attendance entry. will be used in loading the
 * records from attendance table, saving data to dataase.
 * 
 */
public class AttendanceEntryHandler {
	
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private static volatile AttendanceEntryHandler attendanceEntryHandler = null;
	private static final Log log = LogFactory
			.getLog(AttendanceEntryHandler.class);
	private static Map<Integer, String> pMap = null;
	static {
		pMap = new HashMap<Integer, String>();
		pMap.put(13, "01");
		pMap.put(14, "02");
		pMap.put(15, "03");
		pMap.put(16, "04");
		pMap.put(17, "05");
		pMap.put(18, "06");
		pMap.put(19, "07");
		pMap.put(20, "08");
		pMap.put(21, "09");
		pMap.put(22, "10");
		pMap.put(23, "11");
		pMap.put(24, "12");
	}
	
	private static Map<Integer, Integer> periodMap = null;
	static {
		periodMap = new HashMap<Integer, Integer>();
		periodMap.put(13, 1);
		periodMap.put(14, 2);
		periodMap.put(15, 3);
		periodMap.put(16, 4);
		periodMap.put(17, 5);
		periodMap.put(18, 6);
		periodMap.put(19, 7);
		periodMap.put(20, 8);
		periodMap.put(21, 9);
		periodMap.put(22, 10);
		periodMap.put(23, 11);
		periodMap.put(24, 12);
	}
	/**
	 * This method returns the single instance of ClassEntryTxn impl.
	 * 
	 * @return
	 */
	public static AttendanceEntryHandler getInstance() {
		if (attendanceEntryHandler == null) {
			attendanceEntryHandler = new AttendanceEntryHandler();
			return attendanceEntryHandler;
		}
		return attendanceEntryHandler;
	}

	/**
	 * This method will loads the students for particular class,subject & batch.
	 * 
	 */
	@SuppressWarnings("null")
	public void getStudents(AttendanceEntryForm attendanceEntryForm,HttpSession session)
			throws Exception {
		log.info("Handler : Inside getStudents");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		
		List<Integer> countList=new ArrayList<Integer>();
		int count=0;
		Date startDate=CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
		
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		List<Student> students;
		Set<Integer> classSet = new HashSet<Integer>();
		List<BatchStudent> batchStudentList = new ArrayList<BatchStudent>();
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		for (String str : attendanceEntryForm.getClasses()) {
			if(str != null){
				classSet.add(Integer.parseInt(str));
			}
		}
		// studentlist will contain student of classes based on batches and
		// subjects
		if (attendanceEntryForm.getBatchId() != null
				&& attendanceEntryForm.getBatchId().length() != 0) {
			int batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
			 batchStudentList = attendanceEntryTransaction
					.getStudentByBatch(batchId);
			// Commented by Balaji For Requirement
//			studentList = AttendanceEntryHelper.getInstance()
//					.copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents );
			
			studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
		}
		//added by mehaboob only automatic attendance entry start
		else if(attendanceEntryForm.getBatchList()!=null && !attendanceEntryForm.getBatchList().isEmpty()){
			for (Integer integer : attendanceEntryForm.getBatchList()) {
				List<BatchStudent> batchStudents = attendanceEntryTransaction
				.getStudentByBatch(integer);
				batchStudentList.addAll(batchStudents);
			}
			studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
		}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
			boolean check=false;
			for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
				if(batchId!=null && !batchId.isEmpty()){
				List<BatchStudent> batchStudents = attendanceEntryTransaction
				.getStudentByBatch(Integer.parseInt(batchId));
				batchStudentList.addAll(batchStudents);
				}else if(attendanceEntryForm.getBatchIdsArray().length==1){
					check=true;
					students = attendanceEntryTransaction.getStudentByClass(classSet,startDate);
					
					studentList = AttendanceEntryHelper.getInstance()
							.copyStudentBoToTO(students,
									attendanceEntryForm.getSubjectId(), listOfDetainedStudents,session,attendanceEntryForm);
				}
			}
			if(!check){
			studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
			}
		}//end
		else {
			students = attendanceEntryTransaction.getStudentByClass(classSet,startDate);
						
			studentList = AttendanceEntryHelper.getInstance()
					.copyStudentBoToTO(students,
							attendanceEntryForm.getSubjectId(), listOfDetainedStudents,session,attendanceEntryForm);
		}
		// after loading marking some student by default absent
		// if prefix + regno matches regno in studentList
		int classSchemeId = 0;

		// Load the activity attendance if any on particular date and time. and
		// mark as present

		Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
				.getAttendancedate());
		
		Set<Integer> newPeriodsSet = new HashSet<Integer>();
//		. Balaji Has Commented 
//		if (attendanceEntryForm.getPeriods() != null) {
//			for (String str : attendanceEntryForm.getPeriods()) {
//				newPeriodsSet.add(Integer.parseInt(str));
//			}
//		}
		List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
		newPeriodsSet.addAll(totalperiodList);
		
		List<StuCocurrLeave> attendanceList = attendanceEntryTransaction
				.getActivityAttendanceOnDateClassPeriod(classSet,
						newPeriodsSet, date);

		// finding the pre-approved leaves.
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<StudentLeave> leaveStudentList = approveLeaveTransaction
				.getApprovedLeavesForDayAndClass(date, classSet);
		Set<Integer> leaveStudents = new HashSet<Integer>();

		// load the periods for the class. Balaji Has Commented 
//		classSchemeId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
//		List<Period> periodsList = attendanceEntryTransaction
//				.getPeriodsForClass(classSchemeId);
		List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
		
		Collections.sort(periodsList);
		// listing the students only for particular class.
		leaveStudents = AttendanceEntryHelper.getInstance().getLeaveStudents(
				date, leaveStudentList, periodsList, newPeriodsSet);

		 Set<Integer> coCoCurricularLeaveStudents = AttendanceEntryHelper.getInstance()
				.getCoCurricularLeaveStudents(date, attendanceList,
						periodsList, newPeriodsSet);

		// finding isRegnoCheck is true for particular class.
		if (attendanceEntryForm.getClasses() != null) {
			classSchemeId = Integer
					.parseInt(attendanceEntryForm.getClasses()[0]);
		}
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		boolean isRegnoCheck = activityAttendenceTransaction
				.checkIsRegisterNo(classSchemeId);
		attendanceEntryForm.setRegNoDisplay(isRegnoCheck);

		// here marking the students as absentees.
		if (attendanceEntryForm.getAbsentees() != null
				&& attendanceEntryForm.getAbsentees().length() != 0) {

			Set<String> absenteesRegNo = new HashSet<String>();
			String[] absentees = attendanceEntryForm.getAbsentees().split(",");
			String prefix = attendanceEntryForm.getPrefix().trim();
			String regno = "";
			for (String absent : absentees) {
				if (prefix.length() == 0) {
					regno = absent;

				} else {
					regno = prefix + absent;
				}
				absenteesRegNo.add(regno.toLowerCase());
			}

			Iterator<StudentTO> itr1 = studentList.iterator();
			StudentTO studentTO;

			while (itr1.hasNext()) {
				studentTO = itr1.next();
				if (studentTO.getRegisterNo() != null
						&& isRegnoCheck
						&& absenteesRegNo.contains(studentTO.getRegisterNo()
								.toLowerCase())) {
					if(!countList.contains(studentTO.getId()))
						count= count+1;
					else
						countList.add(studentTO.getId());
						
					studentTO.setTempChecked(true);
				} else if (studentTO.getRollNo() != null
						&& (!isRegnoCheck)
						&& absenteesRegNo.contains(studentTO.getRollNo()
								.toLowerCase())) {
					if(!countList.contains(studentTO.getId()))
						count= count+1;
					else
						countList.add(studentTO.getId());
					
					studentTO.setTempChecked(true);
				}

			}

		}

		// Already taken activity attendance is making as absent
		if (!coCoCurricularLeaveStudents.isEmpty()) {

			Iterator<StudentTO> itr1 = studentList.iterator();
			StudentTO studentTO;
			while (itr1.hasNext()) {
				studentTO = itr1.next();
				if (coCoCurricularLeaveStudents.contains(studentTO.getId())) {
					
					if(!countList.contains(studentTO.getId()))
						count= count+1;
					else
						countList.add(studentTO.getId());
					
					studentTO.setTempChecked(true);
					studentTO.setCoCurricularLeavePresent(true);
				}
			}
		}
		// Approved Leave attendance is making as absent
		if (!leaveStudents.isEmpty()) {

			Iterator<StudentTO> itr1 = studentList.iterator();
			StudentTO studentTO;
			while (itr1.hasNext()) {
				studentTO = itr1.next();
				if (leaveStudents.contains(studentTO.getId())) {
					
					if(!countList.contains(studentTO.getId()))
						count= count+1;
					else
						countList.add(studentTO.getId());
					
					studentTO.setTempChecked(true);
					studentTO.setStudentLeave(true);
				}
			}
		}
		StudentRollNoComparator sRollNo = new StudentRollNoComparator();
		sRollNo.setRegNoCheck(isRegnoCheck);
		
		ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
		e.setCompare(1);
		Collections.sort(studentList,e);

		// below property is used in ui to display data.
		attendanceEntryForm.setStudentList(studentList);
		int halfLength = 0;
		int totLength = studentList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		attendanceEntryForm.setHalfLength(halfLength);
		//attendanceEntryForm.setCount(count);
		
		log.info("Handler : Leaving getStudents");
	}

	/**
	 * This method will persist the attendance entry to database.
	 */
	public boolean saveAttendance(AttendanceEntryForm attendanceEntryForm,HttpSession session)
			throws Exception {
		log.info("Handler : inside saveAttendance");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		//List of Attendance Object Changed by mehaboob 
		List<Attendance> attendance=null;
		if(CMSConstants.ACTIVITY_SUBJECT_IDS.contains(Integer.parseInt(attendanceEntryForm.getSubjectId()))){
			
			attendance = AttendanceEntryHelper.getInstance().getAttendanceObj(
					attendanceEntryForm,session);
		}else{
			attendance = AttendanceEntryHelper.getInstance().getAttendanceObj(
					attendanceEntryForm,session);
		}
		
		
		log.info("Handler : Leaving saveAttendance");
		return attendanceEntryTransaction.addAttendance(attendance,attendanceEntryForm);
	}

	/**
	 * This method will update the particular attendance.
	 */
	public void updateAttendance(AttendanceEntryForm attendanceEntryForm)
			throws Exception {
		log.info("Handler : inside updateAttendance");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		Attendance attendance = AttendanceEntryHelper.getInstance().getAttendanceObjUpdate(attendanceEntryForm);
		attendanceEntryTransaction.updateAttendance(attendance);
		log.info("Handler : Leaving saveAttendance");
	}

	/**
	 * This method check weather the loading of second page is required or not.
	 * 
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean isSecondPageRequired(AttendanceEntryForm attendanceEntryForm)
			throws Exception {
		log.info("Handler : inside isSecondPageRequired");
		ILoginTransaction loginTransaction = new LoginTransactionImpl();
		List<Object[]> list = loginTransaction
				.getNonMenuLinksForUser(attendanceEntryForm.getUserId());
		Iterator<Object[]> itr = list.iterator();
		Object[] obj;
		if (list != null && !list.isEmpty()) {
			while (itr.hasNext()) {
				obj = itr.next();
				if (obj[0].equals(CMSConstants.ATTENDANCE_ENTRY_SECONDPAGE)) {
					return true;
				}
			}
		}
		log.info("Handler : Leaving isSecondPageRequired");
		return false;
	}

	/**
	 * This method loads the students for particular attendance this will be
	 * used in edit mode.
	 * 
	 * @param attendanceEntryForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void searchStudents(AttendanceEntryForm attendanceEntryForm)
			throws DataNotFoundException, Exception {
		log.info("Handler : inside searchStudents");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		List<StudentTO> studentList = new LinkedList<StudentTO>();
		int attendanceId = 0;

		if (attendanceEntryForm.getAttendanceId() != null
				&& attendanceEntryForm.getAttendanceId().length() != 0) {
			attendanceId = Integer.parseInt(attendanceEntryForm
					.getAttendanceId());
		}

		if (attendanceId == 0) {
			throw new DataNotFoundException();
		}

		Attendance attendance = attendanceEntryTransaction
				.getAttendanceStudents(attendanceEntryForm, attendanceId);

		Set<AttendanceStudent> attendanceSet = attendance
				.getAttendanceStudents();
		Iterator<AttendanceStudent> itr = attendanceSet.iterator();
		AttendanceStudent attendanceStudent;
		StudentTO studentTO;
		AttendanceStudent attenStudent;
		StringBuffer studentName = new StringBuffer();
		while (itr.hasNext()) {
			studentTO = new StudentTO();
			attenStudent = new AttendanceStudent();
			attendanceStudent = itr.next();
			//if(attendanceStudent.getStudent().getTcNo()==null){
			if (attendanceStudent.getStudent().getIsHide()==null || attendanceStudent.getStudent().getIsHide().equals(false))
			{
			if (attendanceStudent.getStudent().getIsAdmitted()) {
				attenStudent.setId(attendanceStudent.getStudent().getId());
				studentTO.setStudent(attenStudent);
				studentTO.setId(attendanceStudent.getId());

				if (attendanceStudent.getStudent().getAdmAppln()
						.getPersonalData() != null
						&& attendanceStudent.getStudent().getAdmAppln()
								.getPersonalData().getFirstName() != null) {
					studentName.append(
							attendanceStudent.getStudent().getAdmAppln()
									.getPersonalData().getFirstName()).append(
							" ");
				}
				if (attendanceStudent.getStudent().getAdmAppln()
						.getPersonalData() != null
						&& attendanceStudent.getStudent().getAdmAppln()
								.getPersonalData().getMiddleName() != null) {
					studentName.append(
							attendanceStudent.getStudent().getAdmAppln()
									.getPersonalData().getMiddleName()).append(
							" ");
				}
				if (attendanceStudent.getStudent().getAdmAppln()
						.getPersonalData() != null
						&& attendanceStudent.getStudent().getAdmAppln()
								.getPersonalData().getLastName() != null) {
					studentName.append(
							attendanceStudent.getStudent().getAdmAppln()
									.getPersonalData().getLastName()).append(
							" ");
				}
				studentTO.setStudentName(studentName.toString());
				studentTO.setRegisterNo(attendanceStudent.getStudent()
						.getRegisterNo());
				studentTO.setRollNo(attendanceStudent.getStudent().getRollNo());
			/*	if (attendanceStudent.getIsPresent()) {
					studentTO.setTempChecked(false);
				} else {*/
					studentTO.setTempChecked(true);
				//}
				if (attendanceStudent.getIsOnLeave() != null
						&& attendanceStudent.getIsOnLeave()) {
					studentTO.setStudentLeave(true);
					studentTO.setTempChecked(true);
				} else {
					studentTO.setStudentLeave(false);
				}

				if (attendanceStudent.getIsCoCurricularLeave() != null
						&& attendanceStudent.getIsCoCurricularLeave()) {
					studentTO.setCoCurricularLeavePresent(true);
					studentTO.setTempChecked(true);
				} else {
					studentTO.setCoCurricularLeavePresent(false);
				}
				// raghu from vasavi college
			  /*  if(attendanceStudent.getIsSmsSent()!=null && attendanceStudent.getIsSmsSent() )
			    {
			    	studentTO.setIsSmsSent(Boolean.TRUE);
			    }
			    else
			    {
			    	studentTO.setIsSmsSent(Boolean.FALSE);
			    }*/
			    
			    
			    //raghu
			    
			    if(attendanceStudent.getIsPresent()!=null && attendanceStudent.getIsPresent() )
			    {
			    	//studentTO.setIsAbsent(Boolean.FALSE);
			    }
			    else
			    {
			    	studentTO.setIsAbsent(Boolean.TRUE);
			    }
			    studentTO.setId(attendanceStudent.getStudent().getId());
				studentList.add(studentTO);
				studentName = new StringBuffer();
			}else{
				System.out.println("admit"+attendanceStudent.getStudent().getId());
			}
			}else{
				System.out.println("hide"+attendanceStudent.getStudent().getId());
			}
		}
		

		Set<AttendancePeriod> periods = attendance.getAttendancePeriods();
		AttendancePeriod attendancePeriod;
		Set<Integer> newPeriodsSet = new HashSet<Integer>();
		Iterator<AttendancePeriod> i = periods.iterator();
		int countper = 0;
		String[] perArray = new String[attendance.getAttendancePeriods().size()];
		
		while (i.hasNext()) {
			attendancePeriod = i.next();
			newPeriodsSet.add(attendancePeriod.getPeriod().getId());
			//raghu
			if(attendancePeriod.getAttendance()!=null) {
			perArray[countper++] = String.valueOf(attendancePeriod.getId());
			attendanceEntryForm.setAttendancePeriodId(perArray);
			}
		}
		Set<Integer> classSet = new HashSet<Integer>();
		Iterator<AttendanceClass> attendanceIterator = attendance.getAttendanceClasses().iterator();
		int countatt = 0;
		String[] attArray = new String[attendance.getAttendanceClasses().size()];
		while (attendanceIterator.hasNext()) {
			AttendanceClass attendanceClass=attendanceIterator.next();
			//classSet.add(attendanceIterator.next().getClassSchemewise().getId());
			classSet.add(attendanceClass.getClassSchemewise().getId());
			//raghu
			if(attendanceClass.getAttendance()!=null) {
			attArray[countatt++] = String.valueOf(attendanceClass.getId());
			attendanceEntryForm.setAttendanceClassId(attArray);
			}
		}
		
		//raghu
		attendanceEntryForm.setSlipNo(attendance.getSlipNo());
		attendanceEntryForm.setIsTimeTable(attendance.getIsTimeTable());

		// after loading marking some student by default absent
		// if prefix + regno matches regno in studentList
		int classSchemeId = 0;

		if (attendanceEntryForm.getClasses() != null) {
			classSchemeId = Integer
					.parseInt(attendanceEntryForm.getClasses()[0]);
		}
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		boolean isRegnoCheck = activityAttendenceTransaction
				.checkIsRegisterNo(classSchemeId);
		attendanceEntryForm.setRegNoDisplay(isRegnoCheck);

		/*ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
		e.setCompare(1);
		Collections.sort(studentList, e);*/
		Collections.sort(studentList,new Comparator<StudentTO>() {
			@Override
			public int compare(StudentTO arg0, StudentTO arg1) {
				//String string = arg0.getRollNo();
				//String string2=arg1.getRollNo();
				Integer n1=new Integer(arg0.getRollNo());
				Integer n2=new Integer(arg1.getRollNo());
				//return string.compareTo(string2);
				return n1.compareTo(n2);
			}
		})
		;
		attendanceEntryForm.setStudentList(studentList);
		int halfLength = 0;
		int totLength = studentList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		attendanceEntryForm.setHalfLength(halfLength);

		attendanceEntryForm.setHoursHeld(String.valueOf(attendance
				.getHoursHeld()));
		attendanceEntryForm.setAttendanceId(String.valueOf(attendance.getId()));

		String[] periodArray = new String[newPeriodsSet.size()];
		Iterator<Integer> periodSetIterator = newPeriodsSet.iterator();
		int count = 0;
		while (periodSetIterator.hasNext()) {
			periodArray[count++] = periodSetIterator.next().toString();
		}

		String[] instArray = new String[attendance.getAttendanceInstructors().size()];
		Iterator<AttendanceInstructor> itr5 = attendance.getAttendanceInstructors().iterator();
		count = 0;
		AttendanceInstructor attendanceInstructor;
		int countins = 0;
		String[] insArray = new String[attendance.getAttendanceInstructors().size()];
		
		while (itr5.hasNext()) {
			attendanceInstructor = itr5.next();
			instArray[count++] = String.valueOf(attendanceInstructor.getUsers().getId());
			//raghu
			if(attendanceInstructor.getAttendance()!=null) {
			insArray[countins++] = String.valueOf(attendanceInstructor.getId());
			attendanceEntryForm.setAttendanceInstructorId(insArray);
			}
		}

		attendanceEntryForm.setAttendanceTypeId(String.valueOf(attendance
				.getAttendanceType().getId()));
		attendanceEntryForm.setHoursHeld(String.valueOf(attendance
				.getHoursHeld()));
		attendanceEntryForm.setPeriods(periodArray);
		attendanceEntryForm.setTeachers(instArray);

		if (attendance.getSubject() != null) {
			attendanceEntryForm.setSubjectId(String.valueOf(attendance
					.getSubject().getId()));
		}
		if (attendance.getActivity() != null) {
			attendanceEntryForm.setActivityId(String.valueOf(attendance
					.getActivity().getId()));
		}
		if (attendance.getBatch() != null) {
			attendanceEntryForm.setBatchId(String.valueOf(attendance.getBatch()
					.getId()));
		}

		attendanceEntryForm.setOldDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendance.getAttendanceDate()), AttendanceEntryHandler.SQL_DATEFORMAT,AttendanceEntryHandler.FROM_DATEFORMAT));
		attendanceEntryForm.setAttendancedate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(attendance.getAttendanceDate()), AttendanceEntryHandler.SQL_DATEFORMAT,AttendanceEntryHandler.FROM_DATEFORMAT));
		attendanceEntryForm.setOldPeriods(periodArray);
		String[] classesArray = new String[classSet.size()];
		Iterator<Integer> classSetIterator = classSet.iterator();
		int count1 = 0;
		while (classSetIterator.hasNext()) {
			classesArray[count1++] = classSetIterator.next().toString();
		}
		attendanceEntryForm.setClasses(classesArray);
		
		log.info("Handler : Leaving searchStudents");
	}

	/**
	 * This method checks the need of duplicate check in edit mode.
	 * 
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean isDuplicateCheckRequired(
			AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("Handler : inside isDuplicateCheckRequired");
		String[] oldPeriods = attendanceEntryForm.getOldPeriods();
		String oldDate = attendanceEntryForm.getOldDate();
		Set<Integer> oldPeriodsSet = new HashSet<Integer>();
		for (String str : oldPeriods) {
			oldPeriodsSet.add(Integer.parseInt(str));
		}
		Set<Integer> newPeriodsSet = new HashSet<Integer>();
		if (attendanceEntryForm.getPeriods() != null) {
			for (String str : attendanceEntryForm.getPeriods()) {
				newPeriodsSet.add(Integer.parseInt(str));
			}
		}
		log.info("Handler : Leaving isDuplicateCheckRequired");
		boolean newPeriodFound=false;
		for(Integer newPeriod:newPeriodsSet)
		{
			if(!oldPeriodsSet.contains(newPeriod))
			{	
				newPeriodFound=true;
				break;
			}
		}
		if (oldDate.equals(attendanceEntryForm.getAttendancedate())	&& (newPeriodsSet.containsAll(oldPeriodsSet) || (oldPeriodsSet.containsAll(newPeriodsSet))) && !newPeriodFound && newPeriodsSet.size()==oldPeriodsSet.size())
		{
			return false;
		}
		else 
		{
			return true;
		}

	}

	/**
	 * This method returns the true is data is duplicate/ else false will be
	 * returned back.
	 * 
	 * @param attendanceEntryForm
	 * @throws DuplicateException
	 * @throws Exception
	 * code changed by mehaboob duplicate check
	 */
	@SuppressWarnings("unchecked")
	public void duplicateCheck(AttendanceEntryForm attendanceEntryForm,HttpServletRequest request)
			throws DuplicateException, Exception {
		log.info("Handler : inside duplicateCheck");
		//code changed by mehaboob Start
		HttpSession session=request.getSession();
		String batchName="";
		Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
		if (attendanceEntryForm.getSubjectId() != null && !attendanceEntryForm.getSubjectId().isEmpty()) {
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
				if(subjectCodeGroupMap.containsKey(attendanceEntryForm.getSubjectId())){
					List<Integer> subjectIdList=subjectCodeGroupMap.get(attendanceEntryForm.getSubjectId());
					if(!subjectIdList.isEmpty()){
						for (Integer integer : subjectIdList) {
							int batchId=0;
							if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
								batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
							    batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(batchId);
							    duplicateCheckWithSubjectSettings(request,attendanceEntryForm,integer,batchId);
							}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
								batchId=attendanceEntryForm.getSubjectBatchMap().get(integer);
								duplicateCheckWithSubjectSettings(request,attendanceEntryForm,integer,batchId);
							}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
								String[] batchIds=attendanceEntryForm.getBatchIdsArray();
								for (String batchIdFromArray : batchIds) {
									if(batchIdFromArray!=null && !batchIdFromArray.isEmpty()){
								    batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(Integer.parseInt(batchIdFromArray));
									duplicateCheckWithSubjectSettings(request,attendanceEntryForm,integer,Integer.parseInt(batchIdFromArray));
									}
								}
							}else{
								duplicateCheckWithSubjectSettings(request,attendanceEntryForm,integer,batchId);
							}
							
						}
					}
				}else{
					int subjectId = Integer.parseInt(attendanceEntryForm.getSubjectId());
					int batchId=0;
					if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
						batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
					    batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(batchId);
					    duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
					}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
						 batchId=attendanceEntryForm.getSubjectBatchMap().get(subjectId);
						duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
					}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
						for (String batchIds: attendanceEntryForm.getBatchIdsArray()) {
							if(batchIds!=null && !batchIds.isEmpty()){
							batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(Integer.parseInt(batchIds));
							duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,Integer.parseInt(batchIds));
							}
						}
					}else{
						duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
					}
					
				}
			}else{
				int subjectId = Integer.parseInt(attendanceEntryForm.getSubjectId());
				int batchId=0;
				if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
					batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
					batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(batchId);
					duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
				}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
					 batchId=attendanceEntryForm.getSubjectBatchMap().get(subjectId);
					duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
				}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
					for (String batchIds: attendanceEntryForm.getBatchIdsArray()) {
						if(batchIds!=null && !batchIds.isEmpty()){
							batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(Integer.parseInt(batchIds));
							duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,Integer.parseInt(batchIds));
						}
					}
				}else{
					duplicateCheckWithSubjectSettings(request,attendanceEntryForm,subjectId,batchId);
				}
			}
			
		}else{
			if(attendanceEntryForm.getBatchIdsArray()!=null && attendanceEntryForm.getBatchIdsArray().length>0){
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
					batchName=batchName+", "+attendanceEntryForm.getBatchMap().get(Integer.parseInt(batchId));
					duplicateCheckWithSubjectSettings(request,attendanceEntryForm,0,Integer.parseInt(batchId));
					}
				}
			}else{
				duplicateCheckWithSubjectSettings(request,attendanceEntryForm,0,0);
			}
			
		}
		if(!batchName.isEmpty()){
			batchName=removeDuplicateWord(batchName);
			//attendanceEntryForm.setAttendanceBatchName(batchName);
			attendanceEntryForm.setBatchNameWithCode(batchName);
		}
      //end
		log.info("Handler : Leaving duplicateCheck");
	}

	/**
	 * This method loads the particular attendance.
	 * 
	 * @param attendanceEntryForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void getAttendance(AttendanceEntryForm attendanceEntryForm)
			throws DataNotFoundException, Exception {
		log.info("Handler : inside getAttendance");
		Set<Integer> classSet = new HashSet<Integer>();
		if (attendanceEntryForm.getClasses() != null) {
			for (String str : attendanceEntryForm.getClasses()) {
				classSet.add(Integer.parseInt(str));
			}
		}

		int AttendaceTypeId = Integer.parseInt(attendanceEntryForm
				.getAttendanceTypeId());
		Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
				.getAttendancedate());
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		List<Attendance> attendanceList = attendanceEntryTransaction
				.getAttendances(classSet, date, AttendaceTypeId);

		if (attendanceList.isEmpty()) {
			throw new DataNotFoundException();
		}

		AttendanceEntryHelper attendanceEntryHelper = new AttendanceEntryHelper();
		List<AttendanceTO> attendanceToList = attendanceEntryHelper
				.getAttendanceTOS(attendanceList);
		attendanceEntryForm.setAttendanceList(attendanceToList);
		log.info("Handler : Leaving getAttendance");

	}

	/**
	 * This method loads the particular attendance.
	 * 
	 * @param attendanceEntryForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void getAttendanceNoException(AttendanceEntryForm attendanceEntryForm)
			throws Exception {
		log.info("Handler : inside getAttendanceNoException");
		Set<Integer> classSet = new HashSet<Integer>();
		if (attendanceEntryForm.getClasses() != null) {
			for (String str : attendanceEntryForm.getClasses()) {
				classSet.add(Integer.parseInt(str));
			}
		}

		int AttendaceTypeId = Integer.parseInt(attendanceEntryForm
				.getAttendanceTypeId());
		Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
				.getAttendancedate());
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		List<Attendance> attendanceList = attendanceEntryTransaction
				.getAttendances(classSet, date, AttendaceTypeId);

		AttendanceEntryHelper attendanceEntryHelper = new AttendanceEntryHelper();
		List<AttendanceTO> attendanceToList = attendanceEntryHelper
				.getAttendanceTOS(attendanceList);
		attendanceEntryForm.setAttendanceList(attendanceToList);
		log.info("Handler : Leaving getAttendanceNoException");

	}

	/**
	 * This method loads the particular attendance.
	 * 
	 * @param attendanceEntryForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void getAttendanceForModify(AttendanceEntryForm attendanceEntryForm)
			throws DataNotFoundException, Exception {
		log.info("Handler : inside getAttendance");
		Set<Integer> classSet = new HashSet<Integer>();
		if (attendanceEntryForm.getClasses() != null) {
			for (String str : attendanceEntryForm.getClasses()) {
				classSet.add(Integer.parseInt(str));
			}
		}

		// int AttendaceTypeId =
		// Integer.parseInt(attendanceEntryForm.getAttendanceTypeId());
		Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
				.getAttendancedate());
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		List<Attendance> attendanceList = attendanceEntryTransaction
				.getClassAttendances(classSet, date);

		if (attendanceList.isEmpty()) {
			throw new DataNotFoundException();
		}
		else{
		AttendanceEntryHelper attendanceEntryHelper = new AttendanceEntryHelper();
		List<AttendanceTO> attendanceToList = attendanceEntryHelper
				.getAttendanceTOS(attendanceList);
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance()
				.getTeachingStaff();
		if(teachersMap!=null && !teachersMap.isEmpty())
		attendanceEntryForm.setTeachersMap(teachersMap);
		if(!attendanceToList.isEmpty())
		attendanceEntryForm.setAttendanceList(attendanceToList);
		}
		log.info("Handler : Leaving getAttendance");

	}

	/**
	 * This method marks the attendance cancel.
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void cancelAttendance(AttendanceEntryForm attendanceEntryForm)
			throws DataNotFoundException, Exception {
		log.info("Handler : inside cancelAttendance");
		AttendanceEntryHelper attendanceEntryHelper = new AttendanceEntryHelper();
		Set<Integer> attendanceList = attendanceEntryHelper
				.copyAttendanceToToBO(attendanceEntryForm.getAttendanceList());
		if (attendanceList.isEmpty()) {
			throw new DataNotFoundException();
		}
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		attendanceEntryTransaction.cancelAttendance(attendanceList);
		log.info("Handler : Leaving cancelAttendance");
	}

	public Map<Integer, String> getClassesByTeacher(String[] teachers,
			String year) throws Exception {
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl
				.getInstance();
		Map<Integer, String> classMap = transaction.getClassesByTeacher(
				teachers, year);
		return classMap;
	}

	public String getQueryForNumericCode(AttendanceEntryForm attendanceEntryForm)
			throws Exception {
		String query = "from TeacherClassSubject t where t.teacherId.isActive=1 and t.numericCode='"
				+ attendanceEntryForm.getNumericCode() + "'";
		return query;
	}

	public void setDataToForm(AttendanceEntryForm attendanceEntryForm,
			List<TeacherClassSubject> duplicateList,HttpSession session) throws Exception {
		Map<String, String> subjectMap = new LinkedHashMap<String, String>();
		Map<Integer, String> periodMap = new LinkedHashMap<Integer, String>();
		Iterator<TeacherClassSubject> itr = duplicateList.iterator();
		String teacherId = "";
		Set<Integer> classIdSet = new HashSet<Integer>();
		String subjectId = "";
		String year="";
		while (itr.hasNext()) {
			TeacherClassSubject teacherClassSubject = (TeacherClassSubject) itr
					.next();
			if (teacherClassSubject.getTeacherId() != null) {
				teacherId = Integer.toString(teacherClassSubject.getTeacherId()
						.getId());
			}
			if (teacherClassSubject.getClassId() != null) {
				if (!classIdSet.contains(teacherClassSubject.getClassId()
						.getId())) {
					classIdSet.add(teacherClassSubject.getClassId().getId());
				}
			}
			if (teacherClassSubject.getSubject() != null) {
				subjectId = Integer.toString(teacherClassSubject.getSubject()
						.getId());
			}
			if(teacherClassSubject.getYear()!=null){
				year=teacherClassSubject.getYear();
			}
		}
		String[] teacherIds = { teacherId };
		Object[] objectArray = classIdSet.toArray();
		int length = objectArray.length;
		String[] classIds = new String[length];
		for (int i = 0; i < length; i++) {
			classIds[i] = objectArray[i].toString();
		}
		attendanceEntryForm.setTeachers(teacherIds);
		attendanceEntryForm.setSubjectId(subjectId);
		attendanceEntryForm.setClasses(classIds);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if (year != null
				&& !year.isEmpty()) {
			currentYear = Integer.parseInt(year);
		}
		attendanceEntryForm.setYear(year);
		 Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByTeacher(
				Integer.parseInt(teacherId), String.valueOf(currentYear));
		attendanceEntryForm.setClassMap(classMap);
		if(!classMap.isEmpty()){
			//code commented by mehaboob there is no use of this code start
			
			/*List<ClassSchemewise> classSchemewiseList = CommonAjaxHandler
			.getInstance().getDetailsonClassSchemewiseId(classIdSet);
			Iterator<ClassSchemewise> itr1 = classSchemewiseList.iterator();
			ClassSchemewise classSchemewise;
			while (itr1.hasNext()) {
				classSchemewise = itr1.next();
				if (classSchemewise.getCurriculumSchemeDuration().getAcademicYear() != null
						&& classSchemewise.getClasses().getCourse().getId() != 0
						&& classSchemewise.getClasses().getTermNumber() != 0) {
					int year1 = classSchemewise.getCurriculumSchemeDuration()
					.getAcademicYear();
					int courseId = classSchemewise.getClasses().getCourse().getId();
					int term = classSchemewise.getClasses().getTermNumber();*/
			
			//end
					Map<String, String> tempMap = CommonAjaxHandler.getInstance()
					.getSubjectByCourseIdTermYearTeacher(Integer.parseInt(teacherId),attendanceEntryForm.getClasses(),session,false);
					subjectMap.putAll(tempMap);
			/*	}
			}*/
			if(classIdSet!=null && !classIdSet.isEmpty()){
				Set<Integer> temClassIdsSet=new HashSet<Integer>();
				Iterator<Integer> itr2=classIdSet.iterator();
				if (itr2.hasNext()) {
					Integer integer = (Integer) itr2.next();
					temClassIdsSet.add(integer);
				}
				periodMap = CommonAjaxHandler.getInstance()
				.getPeriodByClassSchemewiseId(temClassIdsSet);
			}
			attendanceEntryForm.setPeriodMap(periodMap);
			attendanceEntryForm.setSubjectMap(subjectMap);
		}
	}
	
	/**
	 * @param attendanceEntryForm
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public AttendanceSlipNumber checkDuplicateSlipNo(AttendanceEntryForm attendanceEntryForm) throws Exception
	{
		log.info("Handler : inside DuplicateCheckSlipNo");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		log.info("Handler : Leaving duplicateCheckSlipNo");
		int year = Integer.parseInt(attendanceEntryForm.getYear());
		return attendanceEntryTransaction.checkDuplicateSlipNo(year);
	}

	/**
	 * @param classes
	 * @return
	 */
	public boolean checkAttendanceDateMinRange(String[] classes,String attendancedate) throws Exception {
		boolean isChanged=false;
		if (classes != null && classes.length>0) {
			String query="select c.startDate from CurriculumSchemeDuration c join c.classSchemewises c1 where c1.id="+classes[0];
			IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
			java.util.Date startDate =attendanceEntryTransaction.checkAttendanceDateMinRange(query);
			java.util.Date endDate = CommonUtil.ConvertStringToDate(attendancedate);

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				isChanged=true;
			}
		}
		return isChanged;
	}
	
	public void duplicateCheckWhileUpdate(AttendanceEntryForm attendanceEntryForm)
	throws DuplicateException, Exception {
		log.info("Handler : inside duplicateCheck");
		Set<Integer> classSet = new HashSet<Integer>();
		Set<Integer> periodSet = new HashSet<Integer>();
		int batchId = 0;
		if (attendanceEntryForm.getBatchId() != null
				&& attendanceEntryForm.getBatchId().length() != 0) {
			batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
		}
		if (attendanceEntryForm.getClasses() != null) {
			for (String str : attendanceEntryForm.getClasses()) {
				classSet.add(Integer.parseInt(str));
			}
		}
		if (attendanceEntryForm.getPeriods() != null) {
			for (String str : attendanceEntryForm.getPeriods()) {
				periodSet.add(Integer.parseInt(str));
			}
		}
		int subjectId = 0;
		if (attendanceEntryForm.getSubjectId() != null
				&& attendanceEntryForm.getSubjectId().length() != 0) {
			subjectId = Integer.parseInt(attendanceEntryForm.getSubjectId());
		}
		
		Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
				.getAttendancedate());
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
				.getInstance();
		List<Integer> commonSubGrp=attendanceEntryTransaction.getCommonSubGrpForClass(classSet,Integer.parseInt(attendanceEntryForm.getYear()));
		List<Integer> list = attendanceEntryTransaction.duplicateAttendanceCheckWhileUpdate(classSet, periodSet, date, batchId,subjectId,Integer.parseInt(attendanceEntryForm.getAttendanceId()));
		if (!list.isEmpty()) {
			if(subjectId!=0 && batchId==0){
				Iterator<Integer> itr=list.iterator();
				while (itr.hasNext()) {
					Integer integer = (Integer) itr.next();
					if(commonSubGrp.contains(integer)){
						throw new DuplicateException();
					}else{
						if(subjectId==integer){
							throw new DuplicateException();
						}else if(commonSubGrp.contains(subjectId)){
							throw new DuplicateException();
						}
					}
				}
			}else{
				throw new DuplicateException();
			}
		}
		log.info("Handler : Leaving duplicateCheck");
}
	/**
	 * @param attendanceEntryForm
	 */
	public void setUserDetailsToForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		Users user = transaction.getUserDetails(attendanceEntryForm);
		if(user.getEmployee() != null && user.getEmployee().getDepartment() != null && user.getEmployee().getDepartment().getName() != null){
			attendanceEntryForm.setDepartmentName(user.getEmployee().getDepartment().getName());
		}
		if(user.getEmployee() != null && user.getEmployee().getFirstName() != null){
			attendanceEntryForm.setTeacherName(user.getEmployee().getFirstName());
		}
	}
	/**
	 * @param classList
	 * @param attendanceEntryForm 
	 * @return
	 * @throws Exception
	 */
	public List<Integer> getClassesArray(List<ClassesTO> classList, AttendanceEntryForm attendanceEntryForm)  throws Exception{
		List<Integer> classes = new ArrayList<Integer>();
		List<ClassesTO> newClassList = new ArrayList<ClassesTO>();
		String[] classesArray = new String[classList.size()];
		String classids="";
		StringBuilder classNames = new StringBuilder();
		if(classList != null){
			Iterator<ClassesTO> iterator = classList.iterator();
			int count =0;
			while (iterator.hasNext()) {
				ClassesTO classesTO = (ClassesTO) iterator.next();
				if(attendanceEntryForm.getClassId() != null && !attendanceEntryForm.getClassId().trim().isEmpty()){
					if(classesTO.getId() == Integer.parseInt(attendanceEntryForm.getClassId())){
						if(attendanceEntryForm.getChecked() != null){
							if(attendanceEntryForm.getChecked().equalsIgnoreCase("on")){
								classesTO.setChecked("on");
							}else{
								classesTO.setChecked(null);
							}
						}
					}
				}
				if(classesTO.getChecked() != null){
					if(classesTO.getChecked().equalsIgnoreCase("on")){
						classes.add(classesTO.getId());
						classesArray[count]=String.valueOf(classesTO.getId());
						classids=classids +classesTO.getId()+",";
						classNames.append(classesTO.getClassName()).append(", ");
						count=count + 1;
					}
				}
				newClassList.add(classesTO);
			}
			int len=classNames.length()-2;
	        if(classNames.toString().endsWith(", ")){
	            classNames.setCharAt(len, ' ');
	        }
			attendanceEntryForm.setAttendanceClass(classNames.toString().trim());
			attendanceEntryForm.setClassIds(classids);
			attendanceEntryForm.setClasses(classesArray);
			attendanceEntryForm.setClassList(newClassList);
		}
		return classes;
	}

	/**
	 * @param classes
	 * @param attendanceEntryForm 
	 * @return
	 * @throws Exception
	 */
	public List<PeriodTO> getPeriodList(List<Integer> classes, AttendanceEntryForm attendanceEntryForm) throws Exception{
		
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		List<Period> boList =  new ArrayList<Period>();
		int currentPeriodId = 0;
		if(classes != null && !classes.isEmpty()){
			boList =  transaction.getPeriodsList(classes);
			Iterator<Period> itr = boList.iterator();
			Period period;
			List<Integer> periodList=new ArrayList<Integer>();
			while (itr.hasNext()) {
				period = itr.next();
				periodList.add(period.getId());
			}
			currentPeriodId = transaction.getCurrentPeriodId(periodList);
		}
		List<PeriodTO> periodTOs = new ArrayList<PeriodTO>();
		if(!boList.isEmpty()){
			Iterator<Period> iterator =boList.iterator();
			while (iterator.hasNext()) {
				Period period = (Period) iterator.next();
				if(period.getId() == currentPeriodId){
					int st=Integer.parseInt(period.getStartTime().substring(0,2));
					int et=Integer.parseInt(period.getEndTime().substring(0,2));
					if(st<=12){
						String[] periods = {String.valueOf(period.getId())};
						attendanceEntryForm.setPeriods(periods);
						attendanceEntryForm.setCurrentPeriodName( period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
					}else{
						String[] periods = {String.valueOf(period.getId())};
						attendanceEntryForm.setPeriods(periods);
						attendanceEntryForm.setCurrentPeriodName( period.getPeriodName()+"("+pMap.get(st)+period.getStartTime().substring(2,5)+"-"+pMap.get(et)+period.getEndTime().substring(2,5)+")");
					}
				}
			}
		}
		return periodTOs;
	}

	/**
	 * @param classes
	 * @param attendanceEntryForm 
	 * @return
	 * @throws Exception
	 */
	public List<SubjectTO> getSubjectList(List<Integer> classes, AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception{
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		List<Subject> bolist = new ArrayList<Subject>();
		Map<String, List<Integer>> subjectCodeGroupMap=new HashMap<String, List<Integer>>();
		if(classes != null && !classes.isEmpty()){
			bolist = transaction.getSubjectList(classes,attendanceEntryForm);
		}
		List<SubjectTO> subjectTOs = new ArrayList<SubjectTO>();
		Map<Integer, SubjectTO> subjectToMap=null;
		int subjectId=0;
		if(bolist != null){
			Iterator<Subject> iterator = bolist.iterator();
			while (iterator.hasNext()) {
				Subject teacherClassSubject = (Subject) iterator.next();
				//code added by mehaboob for common subject
				if(teacherClassSubject.getSubjectCodeGroup()!=null && teacherClassSubject.getSubjectCodeGroup().getId()!=0){
					String subjectCodeName=teacherClassSubject.getSubjectCodeGroup().getSubjectsGroupName();
					if(subjectCodeName.contains("&")){
						subjectCodeName=subjectCodeName.replace("&", " and ");
					}
				 if(subjectCodeGroupMap.containsKey(String.valueOf(teacherClassSubject.getSubjectCodeGroup().getId())+"_"+subjectCodeName)){
					List<Integer> subjectIds=subjectCodeGroupMap.get(String.valueOf(teacherClassSubject.getSubjectCodeGroup().getId())+"_"+subjectCodeName);
					subjectIds.add(teacherClassSubject.getId());
					
					if(subjectToMap!=null && !subjectToMap.isEmpty()){
					   SubjectTO to=subjectToMap.get(subjectId);	
					   to.setSubjectID(String.valueOf(teacherClassSubject.getSubjectCodeGroup().getId())+"_"+subjectCodeName);
					   to.setName(subjectCodeName);
					   to.setTheoryPractical(teacherClassSubject.getIsTheoryPractical());
					   subjectTOs.add(to);
					   subjectToMap=null;
					   subjectId=0;
					}
					subjectCodeGroupMap.put(String.valueOf(teacherClassSubject.getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
				 }else{
					 if(subjectToMap!=null && !subjectToMap.isEmpty()){
						SubjectTO to=subjectToMap.get(subjectId);
						subjectTOs.add(to);
						subjectToMap=null;
						subjectId=0;
					 }if(teacherClassSubject.getIsActive()){
							 List<Integer> subjectIds=new ArrayList<Integer>();
							 subjectIds.add(teacherClassSubject.getId());
							 subjectCodeGroupMap.put(String.valueOf(teacherClassSubject.getSubjectCodeGroup().getId())+"_"+subjectCodeName, subjectIds);
							 subjectToMap=new HashMap<Integer, SubjectTO>();
							 SubjectTO to = new SubjectTO();
							 subjectId=teacherClassSubject.getId();
							 to.setSubjectID(String.valueOf(teacherClassSubject.getId()));
							 to.setName(teacherClassSubject.getName()+"("+teacherClassSubject.getCode()+")");
							 to.setTheoryPractical(teacherClassSubject.getIsTheoryPractical());
							 subjectToMap.put(teacherClassSubject.getId(), to);							
						 }
				 }
				}else{
					SubjectTO to = new SubjectTO();
					//to.setId(teacherClassSubject.getId());
					to.setSubjectID(String.valueOf(teacherClassSubject.getId()));
					to.setName(teacherClassSubject.getName()+"("+teacherClassSubject.getCode()+")");
					to.setTheoryPractical(teacherClassSubject.getIsTheoryPractical());
					subjectTOs.add(to);
				}
			}
 		}
		 if(subjectToMap!=null && !subjectToMap.isEmpty()){
				SubjectTO to=subjectToMap.get(subjectId);
				subjectTOs.add(to);
				subjectToMap=null;
				subjectId=0;
			 }
		session.setAttribute("SubjectCodeGroupMap", subjectCodeGroupMap);
		//end
		return subjectTOs;
	}

	/**
	 * @param subjectId
	 * @param classSchemewiseIds
	 * @return
	 * @throws Exception
	 * code changed by mehaboob For subject
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, String> getBatchesBySubjectAndClassIds1(String subjectId,List<Integer> classSchemewiseIds,HttpSession session) throws Exception {
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		//code added by mehaboob start
		
		/*Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
		Map<Integer, String> BatchesBySubjectAndClassMap = new HashMap<Integer, String>();
		if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
			if(subjectCodeGroupMap.containsKey(subjectId)){
				List<Integer> subjectIdList=subjectCodeGroupMap.get(subjectId);
				for (Integer subjectId1 : subjectIdList) {
					if(classSchemewiseIds != null && !classSchemewiseIds.isEmpty()){
						Map<Integer, String> batchMap = transaction.getBatchesBySubjectAndClassScheme1(subjectId1,classSchemewiseIds);
						if(!batchMap.isEmpty()){
						batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
						BatchesBySubjectAndClassMap.putAll(batchMap);
						}
					}
				}
			}else{
				if(classSchemewiseIds != null && !classSchemewiseIds.isEmpty()){
					Map<Integer, String> batchMap = transaction.getBatchesBySubjectAndClassScheme1(Integer.parseInt(subjectId),classSchemewiseIds);
					if(!batchMap.isEmpty()){
						batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
						BatchesBySubjectAndClassMap.putAll(batchMap);
					}
				}
			}
		}else{
			if(classSchemewiseIds != null && !classSchemewiseIds.isEmpty()){
				Map<Integer, String> batchMap = transaction.getBatchesBySubjectAndClassScheme1(Integer.parseInt(subjectId),classSchemewiseIds);
				if(!batchMap.isEmpty()){
					batchMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(batchMap);
					BatchesBySubjectAndClassMap.putAll(batchMap);
				}
			}
		}*/
		Map<Integer, String> BatchesBySubjectAndClassMap = new HashMap<Integer, String>();
		if(classSchemewiseIds != null && !classSchemewiseIds.isEmpty()){
			BatchesBySubjectAndClassMap = transaction.getBatchesBySubjectAndClassScheme1(subjectId,classSchemewiseIds,session);
		}
		BatchesBySubjectAndClassMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(BatchesBySubjectAndClassMap);
		//end
        return BatchesBySubjectAndClassMap;
	}
	
	/**
	 * @param subjectId
	 * @param classSchemewiseIds
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getBatchesByActivityAndClassIds(int subjectId,
			List<Integer> classSchemewiseIds) throws Exception {
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		 Map<Integer, String> classSchemewiseMap = transaction.getBatchesByActivityAndClassScheme(subjectId,
				classSchemewiseIds);
		classSchemewiseMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classSchemewiseMap);
		return classSchemewiseMap;
		
	}

	/**
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void setPeriodsToForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
		List<PeriodTO> periodList = attendanceEntryForm.getPeriodList();
		if(periodList != null){
			String[] periods = new String[periodList.size()+1]; 
			periods[0] = attendanceEntryForm.getPeriods()[0];
			String periodNames="";
			if(periodList != null && !periodList.isEmpty()){
				Iterator<PeriodTO> iterator = periodList.iterator();
				int count=1;
				while (iterator.hasNext()) {
					PeriodTO periodTO = (PeriodTO) iterator.next();
					if(periodTO.getChecked() != null){
						if(periodTO.getTempChecked() != null){
							if(periodTO.getTempChecked().equalsIgnoreCase("on")){
								if(periodTO.getChecked().equalsIgnoreCase("On")){
									periods[count]=String.valueOf(periodTO.getId());
									count = count + 1;
								}
							}
						}
					}
				}
			}
			attendanceEntryForm.setPeriods(periods);
		}
	}

	/**
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void setRequiredDataToNextPage(AttendanceEntryForm attendanceEntryForm) throws Exception{
		StringBuilder teacherNames = new StringBuilder();
		teacherNames.append(attendanceEntryForm.getTeacherName() +"(" +attendanceEntryForm.getDepartmentName()+")");
		List<TeacherClassEntryTo> teacherList = attendanceEntryForm.getTeachersList();
		if(teacherList != null ){ 
			Iterator<TeacherClassEntryTo> iterator = teacherList.iterator();
			while (iterator.hasNext()) {
				TeacherClassEntryTo teacherClassEntryTo = (TeacherClassEntryTo) iterator.next();
				if(!(teacherClassEntryTo.getTeacherId()==Integer.parseInt(attendanceEntryForm.getUserId()))){
					if(teacherClassEntryTo.getChecked() != null){
						if(teacherClassEntryTo.getChecked().equalsIgnoreCase("on")){
							teacherNames.append(", ").append(teacherClassEntryTo.getTeacherName()+"("+teacherClassEntryTo.getDepartmentName()+")");
						}
					}
				}
			}
		}
		attendanceEntryForm.setAttendanceTeacher(teacherNames.toString());
		List<AttendanceTypeTO> attTypeList = attendanceEntryForm.getAttendanceTypeList();
		if(attTypeList!=null){
		Iterator<AttendanceTypeTO> iterator = attTypeList.iterator();
		while (iterator.hasNext()) {
			AttendanceTypeTO attendanceTypeTO = (AttendanceTypeTO) iterator.next();
			if(attendanceEntryForm.getAttendanceTypeId() != null){
				if(attendanceTypeTO.getId() == Integer.parseInt(attendanceEntryForm.getAttendanceTypeId())){
					attendanceEntryForm.setAttenType(attendanceTypeTO.getAttendanceTypeName());
				}
			}
		}
		}
		setTeachersIdsTOForm(attendanceEntryForm);
	}

	/**
	 * @param classes
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void getMultiTeachersForPractical(List<Integer> classes,AttendanceEntryForm attendanceEntryForm)  throws Exception{
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		List<TeacherClassSubject> teacherClassSubjects = transaction.getMultiTeachers(classes,attendanceEntryForm);
		if(teacherClassSubjects != null){
			if(teacherClassSubjects.size()>1){
				List<TeacherClassEntryTo> teachersList = new ArrayList<TeacherClassEntryTo>();
				Iterator<TeacherClassSubject> iterator = teacherClassSubjects.iterator();
				while (iterator.hasNext()) {
					TeacherClassSubject teacherClassSubject = (TeacherClassSubject) iterator.next();
					if(!(Integer.parseInt(attendanceEntryForm.getUserId()) == teacherClassSubject.getTeacherId().getId())){
						if(teacherClassSubject.getTeacherId() != null && teacherClassSubject.getTeacherId().getEmployee() != null && teacherClassSubject.getTeacherId().getEmployee().getFirstName() != null){
							TeacherClassEntryTo to = new TeacherClassEntryTo();
							to.setTeacherName(teacherClassSubject.getTeacherId().getEmployee().getFirstName());
							if(teacherClassSubject.getTeacherId() !=null && teacherClassSubject.getTeacherId().getId() != 0){
								to.setTeacherId(teacherClassSubject.getTeacherId().getId());
							}
							if(teacherClassSubject.getTeacherId().getEmployee().getDepartment()!= null && teacherClassSubject.getTeacherId().getEmployee().getDepartment().getName() != null){
								to.setDepartmentName(teacherClassSubject.getTeacherId().getEmployee().getDepartment().getName());
							}
							teachersList.add(to);
						}else if(teacherClassSubject.getTeacherId() != null && teacherClassSubject.getTeacherId().getUserName() != null ){
							TeacherClassEntryTo to = new TeacherClassEntryTo();
							to.setTeacherName(teacherClassSubject.getTeacherId().getUserName().toUpperCase());
							if(teacherClassSubject.getTeacherId() !=null && teacherClassSubject.getTeacherId().getId() != 0){
								to.setTeacherId(teacherClassSubject.getTeacherId().getId());
							}
							to.setDepartmentName("");
							teachersList.add(to);
						}
					}
				}
				attendanceEntryForm.setTeachersList(teachersList);
			}
		}
	}

	/**
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void setTeachersIdsTOForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
			List<TeacherClassEntryTo> teacherList = attendanceEntryForm.getTeachersList();
			if(teacherList != null ){ 
				String[] teachers = new String[teacherList.size()+1];
				teachers[0] = attendanceEntryForm.getUserId();
				Iterator<TeacherClassEntryTo> iterator = teacherList.iterator();
				int count = 1;
				while (iterator.hasNext()) {
					TeacherClassEntryTo teacherClassEntryTo = (TeacherClassEntryTo) iterator.next();
					if(!(teacherClassEntryTo.getTeacherId()==Integer.parseInt(attendanceEntryForm.getUserId()))){
						if(teacherClassEntryTo.getChecked() != null){
							if(teacherClassEntryTo.getChecked().equalsIgnoreCase("on")){
								teachers[count] = String.valueOf(teacherClassEntryTo.getTeacherId());
								count = count +1;
							}
						}
					}
				}
			attendanceEntryForm.setTeachers(teachers);
		}else{
			String[] teachers = {attendanceEntryForm.getUserId()};
			attendanceEntryForm.setTeachers(teachers);
		}
	}

	/**
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void setActivityListTOForm(AttendanceEntryForm attendanceEntryForm) throws Exception{
		if(attendanceEntryForm.getAttendanceTypeId()!=null && attendanceEntryForm.getAttendanceTypeId().equalsIgnoreCase("3")) {
			Set<Integer> set = new HashSet<Integer>();
			set.add(Integer.valueOf(attendanceEntryForm.getAttendanceTypeId()));
			// This will add activities in to map they belong to particular attendancetypeid.
			Map<Integer,String> activityMap = CommonAjaxHandler.getInstance().getActivityByAttendenceType(set);
			attendanceEntryForm.setActivityMap(activityMap);
			List<ActivityTO> activityList = new ArrayList<ActivityTO>();
			if(!activityMap.isEmpty()){
				Iterator<Entry<Integer, String>> iterator = activityMap.entrySet().iterator();
				while (iterator.hasNext()) {
					Map.Entry<Integer, String> entry = (Map.Entry<Integer, String>) iterator.next();
					ActivityTO to = new ActivityTO();
					to.setName(entry.getValue());
					to.setId(entry.getKey());
					activityList.add(to);
				}
				attendanceEntryForm.setActivityList(activityList);
			}
		}
	}

	/**
	 * @param attendanceEntryForm
	 */
	public void updatePeriodList(AttendanceEntryForm attendanceEntryForm) {
		
		 List<PeriodTO> boList =  attendanceEntryForm.getPeriodList();
		List<PeriodTO> periodTOs = new ArrayList<PeriodTO>();
		if(boList != null ){
			Iterator<PeriodTO> iterator =boList.iterator();
			while (iterator.hasNext()) {
				PeriodTO period = (PeriodTO) iterator.next();
				period.setChecked(period.getTempChecked());
				periodTOs.add(period);
			}
			if(periodTOs.size()%2!=0){
				PeriodTO to = new PeriodTO();
				periodTOs.add(to);
			}
		}
		attendanceEntryForm.setPeriodList(periodTOs);
	}

	/**
	 * @param classes
	 * @param attendanceEntryForm
	 * @throws Exception
	 * code changed by mehaboob to get periods for subjects
	 */
	@SuppressWarnings("unchecked")
	public void getAditionalPeriods(List<Integer> classes,AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception{
		
		//code added by mehaboob start
		Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
		List<PeriodTO> periodTOs = new ArrayList<PeriodTO>();
		List<TeacherClassEntryTo> userList=new ArrayList<TeacherClassEntryTo>();
		if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
			if(subjectCodeGroupMap.containsKey(attendanceEntryForm.getSubjectId())){
			   List<Integer> subjectIdList=subjectCodeGroupMap.get(attendanceEntryForm.getSubjectId());
			   for (Integer subjectId : subjectIdList) {
				   getAdditionalPeriodsBySubjectId(String.valueOf(subjectId),periodTOs,attendanceEntryForm,userList);
			}
			}else{
				getAdditionalPeriodsBySubjectId(attendanceEntryForm.getSubjectId(),periodTOs,attendanceEntryForm,userList);
			}
		}else{
			getAdditionalPeriodsBySubjectId(attendanceEntryForm.getSubjectId(),periodTOs,attendanceEntryForm,userList);
		}
		attendanceEntryForm.setPeriodList(periodTOs);
		attendanceEntryForm.setTeachersList(userList);
		//end
	}

	

	/**
	 * @param teacherId
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassesByTeacher(int teacherId, String year) throws Exception{
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		Map<Integer, String> classMap = transaction.getClassesByTeacher(
				teacherId, year);
		classMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(classMap);
		return classMap;
	}
	/*
	 * check duplicate attendance entry
	 */
	public boolean checkAttendanceDuplication(java.util.Date date,int teacherId,String startTime,String endTime, String subjId, HttpServletRequest request, String time, String teacherName, int teachId,Set<Integer> classesIdsSet,String batchId,Set<Integer> periodsIdsSet)throws Exception{
		boolean flag=false;
		IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
		List<Attendance> attendances=transaction.checkAttendanceDuplication(date,teacherId,startTime,endTime);
		String subjectNames="";
		String batchNames="";
		String attendanceClass="";
		if(attendances!=null && !attendances.isEmpty()){
			Iterator<Attendance> iterator=attendances.iterator();
			while (iterator.hasNext()) {
				Attendance attendance = (Attendance) iterator.next();
					if(subjectNames==null || subjectNames.isEmpty()){
						if(attendance.getSubject()!=null)
							subjectNames=subjectNames+attendance.getSubject().getName().toLowerCase();
					}else{
						if(attendance.getSubject()!=null )
						subjectNames=subjectNames+","+attendance.getSubject().getName().toLowerCase();
					}
				
					if(batchNames.isEmpty()){
						if(attendance.getBatch()!=null)
						batchNames=attendance.getBatch().getBatchName().toLowerCase();
					}else{
						if(attendance.getBatch()!=null)
							batchNames=batchNames+","+attendance.getBatch().getBatchName().toLowerCase();
					}
				
					
			}
			if(subjectNames.isEmpty() && batchNames.isEmpty()){
				Iterator<Attendance> itr=attendances.iterator();
				while (itr.hasNext()) {
					Attendance attendance = (Attendance) itr.next();
					Set<AttendanceClass> attendanceClasses=attendance.getAttendanceClasses();
					if(attendanceClasses!=null){
						Iterator<AttendanceClass> iterator1=attendanceClasses.iterator();
						while (iterator1.hasNext()) {
							AttendanceClass attclass=iterator1.next();
							if(attclass!=null){
									if(attendanceClass==null || attendanceClass.isEmpty()){
											attendanceClass=attclass.getClassSchemewise().getClasses().getName();
									}else{
										attendanceClass=attendanceClass+","+attclass.getClassSchemewise().getClasses().getName();
									}
								}
							}
						}
					}
				}
			}
		
		
		if(!flag && attendances!=null && !attendances.isEmpty()){
			// /* code added by chandra 
			/*Iterator<Attendance> iterator=attendances.iterator();
			while (iterator.hasNext()) {
				Attendance attendance = (Attendance) iterator.next();
				Set<AttendanceClass> attClasses=attendance.getAttendanceClasses();
				Set<AttendanceInstructor> att=attendance.getAttendanceInstructors();
				Iterator<AttendanceClass> iterator1=attClasses.iterator();
				while (iterator1.hasNext()) {
					AttendanceClass attClass = (AttendanceClass) iterator1.next();
					if(classesIdsSet!=null && !classesIdsSet.isEmpty()){
						Iterator<Integer> iterator2=classesIdsSet.iterator();
						while (iterator2.hasNext()) {
							Integer selectedclassId = (Integer) iterator2.next();
						if(attClass.getClassSchemewise().getId()==selectedclassId){
						
								Iterator<AttendanceInstructor> iterator3=att.iterator();
								while (iterator3.hasNext()) {
									AttendanceInstructor attIns = (AttendanceInstructor) iterator3.next();
									if(attIns.getUsers().getId()!=0 && attIns.getUsers().getId()==teacherId){
					*/				request.setAttribute("isSubjectId", "true");
										request.setAttribute("time", time);
										request.setAttribute("teacherName", teacherName);
										if(subjId!=null && !subjId.isEmpty() && batchId!=null && !batchId.isEmpty()){
											request.setAttribute("subjectName",subjectNames);
											request.setAttribute("batchNames",batchNames);
										}else if(subjectNames!=null && !subjectNames.isEmpty()){
											request.setAttribute("subjectName",subjectNames);
										}else{
											if(!batchNames.isEmpty())
											request.setAttribute("batchNames",batchNames);
										}
										if(attendanceClass!=null && !attendanceClass.isEmpty())
										request.setAttribute("attendanceClass",attendanceClass);
										
										//raghu write new
										flag=checkAttendanceDuplicationStudentWise(date, teacherId, subjId, classesIdsSet, periodsIdsSet, batchId);
										if(flag)
										throw new DuplicateException();
					}
		
							//raghu
							else
							{
							flag=checkAttendanceDuplicationStudentWise(date,teacherId,subjId,classesIdsSet,periodsIdsSet,batchId);
							if(flag)
							throw new DuplicateException();
							}
										/*}
										}
									}
								}	
							}
						}
					}*/
		return flag;
	}
	

/**
 * @param request
 * @param attendanceEntryForm
 * @param subjectId
 * @throws Exception
 * code added by mehaboob for combined period  New Method
 */
public void duplicateCheckWithSubjectSettings(HttpServletRequest request,AttendanceEntryForm attendanceEntryForm,int subjectId,int batchId) throws Exception{
	Set<Integer> classSet = new HashSet<Integer>();
	Set<Integer> periodSet = new HashSet<Integer>();
	/*int batchId = 0;
	if (attendanceEntryForm.getBatchId() != null
			&& attendanceEntryForm.getBatchId().length() != 0) {
		batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
	}*/
	if (attendanceEntryForm.getClasses() != null) {
		for (String str : attendanceEntryForm.getClasses()) {
			if(str!=null){
				classSet.add(Integer.parseInt(str));
			}
		}
	}
	if (attendanceEntryForm.getPeriods() != null) {
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		List<Integer> periodList=transaction.getAllPeriodIdsByInput(attendanceEntryForm);
		periodSet.addAll(periodList);
	//	for (String str : attendanceEntryForm.getPeriods()) {
	//		periodSet.add(Integer.parseInt(str));
	//	}
	}
	
	Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm
			.getAttendancedate());
	IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl
			.getInstance();
	List<Integer> commonSubGrp=attendanceEntryTransaction.getCommonSubGrpForClass(classSet,Integer.parseInt(attendanceEntryForm.getYear()));
	List<Object[]> list = attendanceEntryTransaction.duplicateAttendanceCheck(classSet, periodSet, date, batchId,subjectId);
	// /*code added by chandra
	List<Integer> batchList = attendanceEntryTransaction.duplicateAttendanceCheckForBatches(classSet, periodSet, date,batchId);
	// */
	if (list!=null && !list.isEmpty()) {
		if(subjectId!=0 && batchId==0){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] integer = (Object[]) itr.next();
				if(integer!=null){
				if(commonSubGrp.contains(Integer.valueOf(String.valueOf(integer[0])))){
					if(integer[1]==null){
						throw new DuplicateException();
					}else if(integer[1]!=null){
	
						StringBuffer subjectNames=new StringBuffer();
						StringBuffer batchNames=new StringBuffer();
						StringBuffer classNames=new StringBuffer();
						
						String[] str = null;
						String[] batch;
						String[] calss;
						List<Object[]>  subjectAndBatchName=attendanceEntryTransaction.duplicateAttendanceCheckForCommonSubject(classSet, periodSet,date);
						
						if(subjectAndBatchName!=null && !subjectAndBatchName.isEmpty()){
								Iterator<Object[]> itr1=subjectAndBatchName.iterator();
								while (itr1.hasNext()) {
									Object[] obj = (Object[]) itr1.next();
									if(subjectNames.toString()==null || subjectNames.toString().isEmpty()){
										if(obj[0]!=null)
											subjectNames.append(obj[0]);
									}else{
										String dupSubject=subjectNames.toString();
										if(obj[0]!=null)
											 str = dupSubject.split(",");
										for (int x = 0; x < str.length; x++) {
											if (str[x] != null && str[x].length() > 0) {
												if(!str[x].equalsIgnoreCase(String.valueOf(obj[0]))){
													subjectNames=subjectNames.append(",").append(String.valueOf(obj[0]));
												}
											}
										}
									}
									if(batchNames.toString()==null || batchNames.toString().isEmpty()){
										if(obj[1]!=null)
											batchNames.append(obj[1]);
									}else{
										String dupBatches=batchNames.toString();
										if(obj[1]!=null){
											batch = dupBatches.split(",");
											
										for (int x = 0; x < batch.length; x++) {
											if (batch[x] != null && batch[x].length() > 0) {
												if(!batch[x].equalsIgnoreCase(String.valueOf(obj[1]))){
													batchNames=batchNames.append(",").append(String.valueOf(obj[1]));
												}
											}
										}
										}	
									}
									
									if(classNames.toString()==null || classNames.toString().isEmpty()){
										if(obj[2]!=null)
											classNames.append(obj[2]);
									}else{
										String dupclass=classNames.toString();
										if(obj[2]!=null){
											calss = dupclass.split(",");
										for (int x = 0; x < calss.length; x++) {
											if (calss[x] != null && calss[x].length() > 0) {
												if(!calss[x].equalsIgnoreCase(String.valueOf(obj[2]))){
													classNames=classNames.append(",").append(String.valueOf(obj[2]));
												}
											}
										}
									}
								}
							}
						}
								if(subjectNames.toString()!=null && !subjectNames.toString().isEmpty() && batchNames.toString()!=null && !batchNames.toString().isEmpty() 
										&& classNames.toString()!=null && !classNames.toString().isEmpty()){
									/*String periodId[]=attendanceEntryForm.getPeriods();
									String time="";
									for(int j=0;j<periodId.length;j++){
										int perId=Integer.parseInt(periodId[j]);
										Period period=attendanceEntryTransaction.getPeriods(perId);
										String startTime=period.getStartTime();
										String endTime=period.getEndTime();
										
										if(time==null || time.isEmpty()){
												time="("+startTime.substring(0,5)+"-"+endTime.substring(0,5)+")";
										}else{
												time=time+",("+startTime.substring(0,5)+"-"+endTime.substring(0,5)+")";
										}
									}	
										 
									request.setAttribute("timr", time);*/
									request.setAttribute("subjectNames", subjectNames.toString());
									request.setAttribute("batchNames", batchNames.toString());
									request.setAttribute("classNames", classNames.toString());
									request.setAttribute("isCommonSubject", "true");
								throw new DuplicateException();
							}
					}
					if(subjectId==Integer.parseInt(String.valueOf(integer[0]))){
						throw new DuplicateException();
					}else if(commonSubGrp.contains(subjectId)){
						throw new DuplicateException();
					}
				}
				}else{
					throw new DuplicateException();
				}
			}
		}else{
			// /* code added by chandra
			if(batchId==0 || batchList.contains(batchId))
			//	*/
				throw new DuplicateException();
			}
	}
}

  /**
 * @param subjectId
 * @param periodTOs
 * @param attendanceEntryForm
 * @param userList
 * @throws Exception
 * new method added by mehaboob getting periods by passing subjectId
 */
private void getAdditionalPeriodsBySubjectId(String subjectId,List<PeriodTO> periodTOs, AttendanceEntryForm attendanceEntryForm,
		List<TeacherClassEntryTo> userList) throws Exception {
	  IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
	  List<Period> periods= transaction.getPeriodsByTeacher(attendanceEntryForm.getTeachers(), attendanceEntryForm.getYear(),CommonUtil.getDayForADate(attendanceEntryForm.getDate()),attendanceEntryForm.getDate(),subjectId);
	  List<TeacherClassEntryTo> userList1= transaction.getAditionalTeachers(attendanceEntryForm.getPeriods(),CommonUtil.getDayForADate(attendanceEntryForm.getDate()),attendanceEntryForm.getTeachers(), subjectId);	
		if(periods != null && !periods.isEmpty()){
			Iterator<Period> iterator =periods.iterator();
			while (iterator.hasNext()) {
				Period period = (Period) iterator.next();
				String  periodName = attendanceEntryForm.getCurrentPeriodName();
				PeriodTO to = new PeriodTO();
				to.setId(period.getId());
				int st=Integer.parseInt(period.getStartTime().substring(0,2));
				int et=Integer.parseInt(period.getEndTime().substring(0,2));
				if(st<=12){
					to.setName( period.getPeriodName()+"("+period.getStartTime().substring(0,5)+"-"+period.getEndTime().substring(0,5)+")");
				}else{
					to.setName( period.getPeriodName()+"("+pMap.get(st)+period.getStartTime().substring(2,5)+"-"+pMap.get(et)+period.getEndTime().substring(2,5)+")");
				}
				if(!to.getName().equalsIgnoreCase(periodName)){
					periodTOs.add(to);
				}
			}
		}
		if(userList1!=null && !userList1.isEmpty()){
		userList.addAll(userList1);	
		}
   }

/**
 * @param subjectId
 * @return
 * new method added by mehaboob to get subject Code
 * @throws Exception 
 */
public String getsubjectCodeBySubjectId(int subjectId) throws Exception {
	IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
	return transaction.getSubjectCodeBySubId(subjectId);
}

/**
 * @param word
 * @return
 * added by mehaboob to remove duplicate Words
 */
public String removeDuplicateWord(String word){
	String[] wordList = word.split(",");
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < wordList.length; i++) {
	    boolean found = false;
	    for (int j = i+1; j < wordList.length; j++) {
	        if (wordList[j].equals(wordList[i])) {
	            found = true;
	            break;
	        }
	    }
	    if (!found) {
	        if (sb.length() > 0)
	            sb.append(',');
	        sb.append(wordList[i]);
	    }
	}
	return sb.toString();
	
}



//raghu write for new

/**
 * This method will loads the students for particular class,subject & batch.
 * 
 */
@SuppressWarnings("null")
public void getStudentsList(AttendanceEntryForm attendanceEntryForm,HttpSession session)
		throws Exception {
	log.info("Handler : Inside getStudents");
	IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
	
	List<Integer> countList=new ArrayList<Integer>();
	int count=0;
	List<StudentTO> studentList;
	List<Student> students;
	Set<Integer> classSet = new HashSet<Integer>();
	List<BatchStudent> batchStudentList = new ArrayList<BatchStudent>();
	List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
	for (String str : attendanceEntryForm.getClasses()) {
		if(str != null){
			classSet.add(Integer.parseInt(str));
		}
	}
	
	//raghu write for new
	Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	

//raghu over	
	
	
	studentList = new ArrayList<StudentTO>();
	Date startDate=CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	
	// studentlist will contain student of classes based on batches and
	// subjects
	if (attendanceEntryForm.getBatchId() != null
			&& attendanceEntryForm.getBatchId().length() != 0) {
		int batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
		 batchStudentList = attendanceEntryTransaction
				.getStudentByBatch(batchId);
		// Commented by Balaji For Requirement
//		studentList = AttendanceEntryHelper.getInstance()
//				.copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents );
		
		studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
	}
	//added by mehaboob only automatic attendance entry start
	else if(attendanceEntryForm.getBatchList()!=null && !attendanceEntryForm.getBatchList().isEmpty()){
		for (Integer integer : attendanceEntryForm.getBatchList()) {
			List<BatchStudent> batchStudents = attendanceEntryTransaction
			.getStudentByBatch(integer);
			batchStudentList.addAll(batchStudents);
		}
		studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
	}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
		boolean check=false;
		for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
			if(batchId!=null && !batchId.isEmpty()){
			List<BatchStudent> batchStudents = attendanceEntryTransaction
			.getStudentByBatch(Integer.parseInt(batchId));
			batchStudentList.addAll(batchStudents);
			}else if(attendanceEntryForm.getBatchIdsArray().length==1){
				check=true;
				
				students = attendanceEntryTransaction.getStudentByClass(classSet,startDate);
				
				studentList = AttendanceEntryHelper.getInstance()
						.copyStudentBoToTO(students,
								attendanceEntryForm.getSubjectId(), listOfDetainedStudents,session,attendanceEntryForm);
			}
		}
		if(!check){
		studentList = AttendanceEntryHelper.getInstance().copyBatchStudentBosToStudentTO(batchStudentList, listOfDetainedStudents,classSet,attendanceEntryForm);
		}
	}//end
	else {
		students = attendanceEntryTransaction.getStudentByClass(classSet,startDate);
					
		studentList = AttendanceEntryHelper.getInstance()
				.copyStudentBoToTO(students,
						attendanceEntryForm.getSubjectId(), listOfDetainedStudents,session,attendanceEntryForm);
	}
	// after loading marking some student by default absent
	// if prefix + regno matches regno in studentList
	int classSchemeId = 0;

	// Load the activity attendance if any on particular date and time. and
	// mark as present

	
	
	Set<Integer> newPeriodsSet = new HashSet<Integer>();
//	. Balaji Has Commented 
//	if (attendanceEntryForm.getPeriods() != null) {
//		for (String str : attendanceEntryForm.getPeriods()) {
//			newPeriodsSet.add(Integer.parseInt(str));
//		}
//	}
	List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
	newPeriodsSet.addAll(totalperiodList);
	
	List<StuCocurrLeave> attendanceList = attendanceEntryTransaction
			.getActivityAttendanceOnDateClassPeriod(classSet,
					newPeriodsSet, date);

	// finding the pre-approved leaves.
	IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
	List<StudentLeave> leaveStudentList = approveLeaveTransaction
			.getApprovedLeavesForDayAndClass(date, classSet);
	Set<Integer> leaveStudents = new HashSet<Integer>();

	// load the periods for the class. Balaji Has Commented 
//	classSchemeId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
//	List<Period> periodsList = attendanceEntryTransaction
//			.getPeriodsForClass(classSchemeId);
	List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
	     if( !periodsList.isEmpty()){
	    	 for( Period p : periodsList){
	    		 String name = p.getPeriodName();
	    		 if(name.equalsIgnoreCase("Hour 1")){
	    			 p.setPeriodName("Hour 1");
	    		 }
	    		 else if(name.equalsIgnoreCase("Hour 2")){
	    			 p.setPeriodName("Hour 2");
	    		 }
                 else if(name.equalsIgnoreCase("Hour 3")){
                	 p.setPeriodName("Hour 3");
	    		 }
                 else if(name.equalsIgnoreCase("Hour 4")){
                	 p.setPeriodName("Hour 4");
	    		 }
                  else if(name.equalsIgnoreCase("Hour 5")){
                	  p.setPeriodName("Hour 5");
	    		 }
                  else if(name.equalsIgnoreCase("Hour 6")){
                	  p.setPeriodName("Hour 6");
 	    		 }
	    	 }
	     }
	 
	Collections.sort(periodsList);
	// listing the students only for particular class.
	leaveStudents = AttendanceEntryHelper.getInstance().getLeaveStudents(
			date, leaveStudentList, periodsList, newPeriodsSet);

	 Set<Integer> coCoCurricularLeaveStudents = AttendanceEntryHelper.getInstance()
			.getCoCurricularLeaveStudents(date, attendanceList,
					periodsList, newPeriodsSet);

	 
	// finding isRegnoCheck is true for particular class.
	if (attendanceEntryForm.getClasses() != null) {
		classSchemeId = Integer
				.parseInt(attendanceEntryForm.getClasses()[0]);
	}
	IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
	boolean isRegnoCheck = activityAttendenceTransaction
			.checkIsRegisterNo(classSchemeId);
	attendanceEntryForm.setRegNoDisplay(isRegnoCheck);

	// here marking the students as absentees.
	if (attendanceEntryForm.getAbsentees() != null
			&& attendanceEntryForm.getAbsentees().length() != 0) {

		Set<String> absenteesRegNo = new HashSet<String>();
		String[] absentees = attendanceEntryForm.getAbsentees().split(",");
		String prefix = attendanceEntryForm.getPrefix().trim();
		String regno = "";
		for (String absent : absentees) {
			if (prefix.length() == 0) {
				regno = absent;

			} else {
				regno = prefix + absent;
			}
			absenteesRegNo.add(regno.toLowerCase());
		}

		Iterator<StudentTO> itr1 = studentList.iterator();
		StudentTO studentTO;

		while (itr1.hasNext()) {
			studentTO = itr1.next();
			if (studentTO.getRegisterNo() != null
					&& isRegnoCheck
					&& absenteesRegNo.contains(studentTO.getRegisterNo()
							.toLowerCase())) {
				if(!countList.contains(studentTO.getId()))
					count= count+1;
				else
					countList.add(studentTO.getId());
					
				studentTO.setTempChecked(true);
			} else if (studentTO.getRollNo() != null
					&& (!isRegnoCheck)
					&& absenteesRegNo.contains(studentTO.getRollNo()
							.toLowerCase())) {
				if(!countList.contains(studentTO.getId()))
					count= count+1;
				else
					countList.add(studentTO.getId());
				
				studentTO.setTempChecked(true);
			}

		}

	}

	// Already taken activity attendance is making as absent
	if (!coCoCurricularLeaveStudents.isEmpty()) {

		Iterator<StudentTO> itr1 = studentList.iterator();
		StudentTO studentTO;
		while (itr1.hasNext()) {
			studentTO = itr1.next();
			if (coCoCurricularLeaveStudents.contains(studentTO.getId())) {
				
				if(!countList.contains(studentTO.getId()))
					count= count+1;
				else
					countList.add(studentTO.getId());
				
				studentTO.setTempChecked(true);
				studentTO.setCoCurricularLeavePresent(true);
			}
		}
	}
	// Approved Leave attendance is making as absent
	if (!leaveStudents.isEmpty()) {

		Iterator<StudentTO> itr1 = studentList.iterator();
		StudentTO studentTO;
		while (itr1.hasNext()) {
			studentTO = itr1.next();
			if (leaveStudents.contains(studentTO.getId())) {
				
				if(!countList.contains(studentTO.getId()))
					count= count+1;
				else
					countList.add(studentTO.getId());
				
				studentTO.setTempChecked(true);
				studentTO.setStudentLeave(true);
			}
		}
	}
	/*ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
	e.setCompare(1);
	Collections.sort(studentList, e);
	*/
	Collections.sort(studentList,new Comparator<StudentTO>() {
		@Override
		public int compare(StudentTO arg0, StudentTO arg1) {
			//String string = arg0.getRollNo();
			//String string2=arg1.getRollNo();
			Integer n1=new Integer(arg0.getRollNo());
			Integer n2=new Integer(arg1.getRollNo());
			//return string.compareTo(string2);
			return n1.compareTo(n2);
		}
	})
	;

	// below property is used in ui to display data.
	attendanceEntryForm.setStudentList(studentList);

	getStudentsList1(attendanceEntryForm,session);
	
	int halfLength = 0;
	int totLength = studentList.size();
	if (totLength % 2 == 0) {
		halfLength = totLength / 2;
	} else {
		halfLength = (totLength / 2) + 1;
	}
	attendanceEntryForm.setHalfLength(halfLength);
	//attendanceEntryForm.setCount(count);
	Set<Integer> periodSet = new HashSet<Integer>();
	if (attendanceEntryForm.getPeriods() != null) {
		for (String str : attendanceEntryForm.getPeriods()) {
			periodSet.add(Integer.parseInt(str));
		}
	}
	Map<Integer, List<PeriodTO>> studentPeroidMap= new LinkedHashMap<Integer, List<PeriodTO>>();
	List pnList=attendanceEntryTransaction.getPeriodNmaesOnPeriods(periodSet);
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		List<PeriodTO> periodList= new LinkedList<PeriodTO>();
		for(List<StudentTO> lists1:attendanceEntryForm.getStudentsList()){
			PeriodTO periodTO = null;
			for(StudentTO studentTO : lists1){
				if(studentTO.getId()==to.getId() && !pnList.contains(studentTO.getPeriodName())){
					periodTO= new PeriodTO();
					periodTO.setPeriodName(studentTO.getPeriodName());
					periodTO.setHoursTaken(String.valueOf(studentTO.getIsTaken()));
					periodTO.setTempChecked(String.valueOf(studentTO.isTempChecked()));
				}
			}
			if(periodTO!=null){
				periodList.add(periodTO);
			}
		}
		studentPeroidMap.put(to.getId(), periodList);
	}
	List<StudentTO> studentTOs = new LinkedList<StudentTO>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		if(studentPeroidMap.containsKey(to.getId())){
			List<PeriodTO> perioList= studentPeroidMap.get(to.getId());
			to.setPeriodTOs(perioList);
		}
		studentTOs.add(to);
	}
	attendanceEntryForm.setStudentList(studentTOs);
	log.info("Handler : Leaving getStudents");
}

// raghu new duplication check
public boolean checkAttendanceDuplicationStudentWise(java.util.Date date,int teacherId,String subjId, Set<Integer> classesIdsSet,Set<Integer> periodIdsSet,String batchId)throws Exception{
	boolean flag=false;
	
	IAttendanceEntryTransaction transaction = AttendanceEntryTransactionImpl.getInstance();
	List<Integer> studentIds=transaction.getStudentIdsByClassSubject(classesIdsSet,Integer.parseInt(subjId));
	List<Integer> attendaceStudentIds=transaction.getClassAttendancesStudentsIds(classesIdsSet, periodIdsSet, date,batchId);
	
	Iterator<Integer> itr1 = studentIds.iterator();
	Integer id;
	while (itr1.hasNext()) {
		id = itr1.next();
		if (attendaceStudentIds.contains(id)) {
			flag=true;
			break;
		}
	}
	return flag;
}




// for attendance entry
public void getStudentsList1(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {


	IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
	
	List<StudentTO> studentList= new LinkedList<StudentTO>();
	List<List<StudentTO>> studentsList=new LinkedList<List<StudentTO>>();
	
	List attList = null;
	List pList = null;
	List sList = null;
	List pnList = null;
	Map m=new HashMap<Integer,StudentTO>();
	Set<Integer> classSet = new HashSet<Integer>();
	boolean once=true;
	for (String str : attendanceEntryForm.getClasses()) {
		if(str != null){
			classSet.add(Integer.parseInt(str));
		}
	}
	
	Set<Integer> periodSet = new HashSet<Integer>();
	if (attendanceEntryForm.getPeriods() != null) {
		for (String str : attendanceEntryForm.getPeriods()) {
			periodSet.add(Integer.parseInt(str));
		}
	}
	
	Set<Integer> batchSet = new HashSet<Integer>();
	if (attendanceEntryForm.getBatchIdsArray() != null) {
		for (String str : attendanceEntryForm.getBatchIdsArray()) {
			if(str != null && !str.equalsIgnoreCase(""))
			batchSet.add(Integer.parseInt(str));
		}
	}
	
	
	Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	Set<Integer> newPeriodsSet = new HashSet<Integer>();
	List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
	newPeriodsSet.addAll(totalperiodList);
	
	List<StuCocurrLeave> attendanceList = attendanceEntryTransaction.getActivityAttendanceOnDateClassPeriod(classSet,newPeriodsSet, date);

	// finding the pre-approved leaves.
	IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
	List<StudentLeave> leaveStudentList = approveLeaveTransaction.getApprovedLeavesForDayAndClass(date, classSet);
	Set<Integer> leaveStudents = new HashSet<Integer>();

	List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
	
	Collections.sort(periodsList);
	// listing the students only for particular class.
	leaveStudents = AttendanceEntryHelper.getInstance().getLeaveStudents(date, leaveStudentList, periodsList, newPeriodsSet);

	Set<Integer> coCoCurricularLeaveStudents = AttendanceEntryHelper.getInstance().getCoCurricularLeaveStudents(date, attendanceList,	periodsList, newPeriodsSet);

	
	
	attList=attendanceEntryTransaction.getClassAttendancesStudents(classSet, date);
	pList=attendanceEntryTransaction.getPeriodNamesOnClass(classSet);
	pnList=attendanceEntryTransaction.getPeriodNmaesOnPeriods(periodSet);
	Date startDate=CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	if(batchSet.size()==0){
		sList=attendanceEntryTransaction.getStudentsOnClasses(classSet, Integer.parseInt(attendanceEntryForm.getYear()),Integer.parseInt(attendanceEntryForm.getSubjectId()),startDate);
		
	}else{
		sList=attendanceEntryTransaction.getStudentsOnBatches(batchSet,classSet);
		
	}
	if(pnList!=null &&  !pnList.isEmpty()){
		attendanceEntryForm.setPeriodName(pnList.get(0).toString());
	}
	// ashwini did for common attendance
	if((sList.isEmpty() || sList.size()==0) && CMSConstants.ACTIVITY_SUBJECT_IDS.contains(Integer.parseInt(attendanceEntryForm.getSubjectId()))){
		sList=attendanceEntryTransaction.getStudentByClass(classSet);
	}
	//List<String> li=null;
	//raghu write for new
	
	 attendanceEntryForm.setPeriodsList(pList);
	 int periodCount=0;
	Iterator<String> i = pList.iterator();
	while (i.hasNext()) {
		
		Iterator is = sList.iterator();
		String p =i.next();
		//li=new LinkedList<String>();
		
		
		while (is.hasNext()) {
			
			if(batchSet.size()==0){
			Student s=(Student)is.next();
			StudentTO st=new StudentTO();
			st.setId(s.getId());
			st.setRollNo(s.getRollNo());
			st.setRegisterNo(s.getRegisterNo());
			st.setStudentName(s.getAdmAppln().getPersonalData().getFirstName());
			st.setIsTaken(false);
			if(pnList.contains(p) && once){
				st.setIsCurrent(true);
				attendanceEntryForm.setPeriodCount(periodCount);
			}
			else
			{
				st.setIsCurrent(false);
			}
			st.setPeriodName(p);
			st.setSubjectId(attendanceEntryForm.getSubjectId());
			
			// Already taken activity attendance is making as absent
			if (!coCoCurricularLeaveStudents.isEmpty()) {

					if (coCoCurricularLeaveStudents.contains(st.getId())) {
						
						st.setTempChecked(true);
						st.setCoCurricularLeavePresent(true);
					}
				
			}
			
			// Approved Leave attendance is making as absent
			if (!leaveStudents.isEmpty()) {

					if (leaveStudents.contains(st.getId())) {
						
						st.setTempChecked(true);
						st.setStudentLeave(true);
					}
				
			}
			
			
			m.put(s.getId(), st);
			
			//batch student start
			}else{
				
				BatchStudent bs=(BatchStudent)is.next();
				Student s=bs.getStudent();
				StudentTO st=new StudentTO();
				st.setId(s.getId());
				st.setRollNo(s.getRollNo());
				st.setRegisterNo(s.getRegisterNo());
				st.setStudentName(s.getAdmAppln().getPersonalData().getFirstName());
				st.setIsTaken(false);
				if(pnList.contains(p) && once){
					st.setIsCurrent(true);
				}
				else
				{
					st.setIsCurrent(false);
				}
				
				st.setSubjectId(attendanceEntryForm.getSubjectId());
				
				// Already taken activity attendance is making as absent
				if (!coCoCurricularLeaveStudents.isEmpty()) {

						if (coCoCurricularLeaveStudents.contains(st.getId())) {
							
							st.setTempChecked(true);
							st.setCoCurricularLeavePresent(true);
						}
					
				}
				
				// Approved Leave attendance is making as absent
				if (!leaveStudents.isEmpty()) {

						if (leaveStudents.contains(st.getId())) {
							
							st.setTempChecked(true);
							st.setStudentLeave(true);
						}
					
				}
				
				
				m.put(s.getId(), st);
			
				
			}
			//batch student iver
			
		}//while close
		
		
		
		
		if(pnList.contains(p))
			once=false;
		
		//System.out.println("----------------------");
		Iterator<Object[]> i1 = attList.iterator();
		while (i1.hasNext()) {

		Object[] object =i1.next();
		String s=object[1].toString();	
		//System.out.println(p.getPeriodName()+"==="+s);
		if(p.equalsIgnoreCase(s)){
		//li.add(object[0].toString()+"="+object[1].toString());	
		
		if(m.containsKey(Integer.parseInt(object[0].toString()))){
			StudentTO sto= (StudentTO)m.get(Integer.parseInt(object[0].toString()));
			sto.setIsTaken(true);
			sto.setCoCurricularLeavePresent(new Boolean(object[3].toString()));
			sto.setStudentLeave(new Boolean(object[4].toString()));
			sto.setTempChecked(new Boolean(object[5].toString()));
			sto.setIsCurrent(false);
			sto.setSubjectId(attendanceEntryForm.getSubjectId());
			sto.setTeacherName(object[6].toString());
			sto.setClassName(object[7].toString());
			sto.setSubjectName(object[8].toString());
			m.put(sto.getId(), sto);
		}
		
		}
		 
	}
	
	
	studentList=new LinkedList<StudentTO>();
	Set<Entry<Integer, StudentTO>> studentCourseAllotmentSet= m.entrySet();
	Iterator<Entry<Integer, StudentTO>> iset=studentCourseAllotmentSet.iterator();
	while(iset.hasNext())
	{
	Entry<Integer, StudentTO> e=iset.next();
	StudentTO sto=e.getValue();
	studentList.add(sto);
	//System.out.println(p+"=====pname========"+sto.getRollNo()+"====rno====="+sto.getIsTaken()+"======take======"+sto.isTempChecked()+"====cur===="+""+sto.getIsCurrent());
	}
	/*Collections.sort(studentList,new Comparator<StudentTO>() {
		@Override
		public int compare(StudentTO arg0, StudentTO arg1) {
			String string = arg0.getStudentName();
			String string2=arg1.getStudentName();
			return string.compareTo(string2);
		}
	})
	;*/
	int classSchemeId = 0;
	if (attendanceEntryForm.getClasses() != null) {
			classSchemeId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
		}
	
	IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
	 
	 boolean isRegnoCheck = activityAttendenceTransaction.checkIsRegisterNo(classSchemeId);
	 attendanceEntryForm.setRegNoDisplay(isRegnoCheck);
//	 ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
//	 e.setCompare(1);
	 Collections.sort(studentList,new Comparator<StudentTO>() {
			@Override
			public int compare(StudentTO arg0, StudentTO arg1) {
				//String string = arg0.getRollNo();
				//String string2=arg1.getRollNo();
				Integer n1=new Integer(arg0.getRollNo());
				Integer n2=new Integer(arg1.getRollNo());
				//return string.compareTo(string2);
				return n1.compareTo(n2);
			}
		})
		;
	// Collections.sort(studentList,e);
	//li1.add(li);
	 periodCount++;
	 studentsList.add(studentList);
	}

	attendanceEntryForm.setStudentsList(studentsList);
	Map<Integer, List<PeriodTO>> studentPeroidMap= new LinkedHashMap<Integer, List<PeriodTO>>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		List<PeriodTO> periodList= new LinkedList<PeriodTO>();
		for(List<StudentTO> lists1:attendanceEntryForm.getStudentsList()){
			PeriodTO periodTO = null;
			for(StudentTO studentTO : lists1){
				if(studentTO.getId()==to.getId() && !pnList.contains(studentTO.getPeriodName())){
					periodTO= new PeriodTO();
					periodTO.setPeriodName(studentTO.getPeriodName());
					periodTO.setHoursTaken(String.valueOf(studentTO.getIsTaken()));
					periodTO.setTempChecked(String.valueOf(studentTO.isTempChecked()));
				}
			}
			if(periodTO!=null){
				periodList.add(periodTO);
			}
		}
		studentPeroidMap.put(to.getId(), periodList);
	}
	List<StudentTO> studentTOs = new LinkedList<StudentTO>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		if(studentPeroidMap.containsKey(to.getId())){
			List<PeriodTO> perioList= studentPeroidMap.get(to.getId());
			to.setPeriodTOs(perioList);
		}
		studentTOs.add(to);
	}
	attendanceEntryForm.setStudentList(studentTOs);
}





//for attendance view 
public void getStudentsList2(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {


	IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
	
	List<StudentTO> studentList=null;
	List<List<StudentTO>> studentsList=new LinkedList<List<StudentTO>>();
	
	List attList = null;
	List pList = null;
	List sList = null;
	List pnList = null;
	Map m=new HashMap<Integer,StudentTO>();
	Set<Integer> classSet = new HashSet<Integer>();
	boolean once=true;
	for (String str : attendanceEntryForm.getClasses()) {
		if(str != null){
			classSet.add(Integer.parseInt(str));
		}
	}
	
	Set<Integer> periodSet = new HashSet<Integer>();
	if (attendanceEntryForm.getPeriods() != null) {
		for (String str : attendanceEntryForm.getPeriods()) {
			periodSet.add(Integer.parseInt(str));
		}
	}
	
	
	Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	Set<Integer> newPeriodsSet = new HashSet<Integer>();
	List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
	newPeriodsSet.addAll(totalperiodList);
	
	List<StuCocurrLeave> attendanceList = attendanceEntryTransaction.getActivityAttendanceOnDateClassPeriod(classSet,newPeriodsSet, date);

	// finding the pre-approved leaves.
	IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
	List<StudentLeave> leaveStudentList = approveLeaveTransaction.getApprovedLeavesForDayAndClass(date, classSet);
	Set<Integer> leaveStudents = new HashSet<Integer>();

	List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
	
	Collections.sort(periodsList);
	// listing the students only for particular class.
	leaveStudents = AttendanceEntryHelper.getInstance().getLeaveStudents(date, leaveStudentList, periodsList, newPeriodsSet);

	Set<Integer> coCoCurricularLeaveStudents = AttendanceEntryHelper.getInstance().getCoCurricularLeaveStudents(date, attendanceList,	periodsList, newPeriodsSet);

	
	
	attList=attendanceEntryTransaction.getClassAttendancesStudents(classSet, date);
	pList=attendanceEntryTransaction.getPeriodNamesOnClass(classSet);
	pnList=attendanceEntryTransaction.getPeriodNmaesOnPeriods(periodSet);
	sList=attendanceEntryTransaction.getStudentsOnAttendance(Integer.parseInt(attendanceEntryForm.getAttendanceId()));
	if(pnList!=null &&  !pnList.isEmpty()){
		attendanceEntryForm.setPeriodName(pnList.get(0).toString());
	}
	//List<String> li=null;
	//raghu write for new
	 attendanceEntryForm.setPeriodsList(pList);
	 
	Iterator<String> i = pList.iterator();
	while (i.hasNext()) {
		int periodCount=0;
		Iterator<Student> is = sList.iterator();
		String p =i.next();
		//li=new LinkedList<String>();
		
			
		while (is.hasNext()) {
			
			Student s=is.next();
			if (s.getIsHide()==null || s.getIsHide().equals(false))
			{
			//if (s.getIsAdmitted()) {
			

			
			StudentTO st=new StudentTO();
			st.setId(s.getId());
			st.setRollNo(s.getRollNo());
			st.setStudentName(s.getAdmAppln().getPersonalData().getFirstName());
			st.setRegisterNo(s.getRegisterNo());
			st.setIsTaken(false);
			if(pnList.contains(p) && once){
				st.setIsCurrent(true);
				attendanceEntryForm.setPeriodCount(periodCount);
			}
			else
			{
				st.setIsCurrent(false);
			}
			st.setPeriodName(p);
			st.setSubjectId(attendanceEntryForm.getSubjectId());
			
			// Already taken activity attendance is making as absent
			if (!coCoCurricularLeaveStudents.isEmpty()) {

					if (coCoCurricularLeaveStudents.contains(st.getId())) {
						
						st.setTempChecked(false);
						st.setCoCurricularLeavePresent(true);
					}
				
			}
			
			// Approved Leave attendance is making as absent
			if (!leaveStudents.isEmpty()) {

					if (leaveStudents.contains(st.getId())) {
						
						st.setTempChecked(false);
						st.setStudentLeave(true);
					}
				
			}
			
			
			m.put(s.getId(), st);
			
			//}
			
			}
		}
		
		if(pnList.contains(p))
			once=false;
		
		//System.out.println("----------------------");
		Iterator<Object[]> i1 = attList.iterator();
		while (i1.hasNext()) {

		Object[] object =i1.next();
		String s=object[1].toString();	
		//System.out.println(p.getPeriodName()+"==="+s);
		if(p.equalsIgnoreCase(s)){
		//li.add(object[0].toString()+"="+object[1].toString());	
		
		if(m.containsKey(Integer.parseInt(object[0].toString()))){
			StudentTO sto= (StudentTO)m.get(Integer.parseInt(object[0].toString()));
			sto.setIsTaken(true);
			sto.setCoCurricularLeavePresent(new Boolean(object[3].toString()));
			sto.setStudentLeave(new Boolean(object[4].toString()));
			sto.setTempChecked(new Boolean(object[5].toString()));
			sto.setIsCurrent(false);
			sto.setSubjectId(attendanceEntryForm.getSubjectId());
			sto.setTeacherName(object[6].toString());
			sto.setClassName(object[7].toString());
			sto.setSubjectName(object[8].toString());
			m.put(sto.getId(), sto);
		}
		
		}
		 
	}
	
	
	studentList=new LinkedList<StudentTO>();
	Set<Entry<Integer, StudentTO>> studentCourseAllotmentSet= m.entrySet();
	Iterator<Entry<Integer, StudentTO>> iset=studentCourseAllotmentSet.iterator();
	while(iset.hasNext())
	{
	Entry<Integer, StudentTO> e=iset.next();
	StudentTO sto=e.getValue();
	studentList.add(sto);
	//System.out.println(p+"=====pname========"+sto.getRollNo()+"====rno====="+sto.getIsTaken()+"======take======"+sto.isTempChecked()+"====cur===="+""+sto.getIsCurrent());
	}
	
	/*Collections.sort(studentList,new Comparator<StudentTO>() {
		
		public int compare(StudentTO arg0,StudentTO arg1){
			String string = arg0.getStudentName();
			String string1 = arg1.getStudentName();
			return string.compareTo(string1);
		}
	});*/
	int classSchemeId = 0;
	if (attendanceEntryForm.getClasses() != null) {
			classSchemeId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
		}
	
	IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
	 
	 boolean isRegnoCheck = activityAttendenceTransaction.checkIsRegisterNo(classSchemeId);
	 attendanceEntryForm.setRegNoDisplay(isRegnoCheck);

//	 ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
//	 e.setCompare(1);
//
//	 Collections.sort(studentList ,e);
	 Collections.sort(studentList,new Comparator<StudentTO>() {
			@Override
			public int compare(StudentTO arg0, StudentTO arg1) {
				//String string = arg0.getRollNo();
				//String string2=arg1.getRollNo();
				Integer n1=new Integer(arg0.getRollNo());
				Integer n2=new Integer(arg1.getRollNo());
				//return string.compareTo(string2);
				return n1.compareTo(n2);
			}
		})
		;
	
	//li1.add(li);
	 studentsList.add(studentList);
	}

	attendanceEntryForm.setStudentsList(studentsList);
	Map<Integer, List<PeriodTO>> studentPeroidMap= new LinkedHashMap<Integer, List<PeriodTO>>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		List<PeriodTO> periodList= new LinkedList<PeriodTO>();
		for(List<StudentTO> lists1:attendanceEntryForm.getStudentsList()){
			PeriodTO periodTO = null;
			for(StudentTO studentTO : lists1){
				if(studentTO.getId()==to.getId()){
					periodTO= new PeriodTO();
					periodTO.setPeriodName(studentTO.getPeriodName());
					periodTO.setHoursTaken(String.valueOf(studentTO.getIsTaken()));
					periodTO.setTempChecked(String.valueOf(studentTO.isTempChecked()));
				}
			}
			if(periodTO!=null){
				periodList.add(periodTO);
			}
		}
		studentPeroidMap.put(to.getId(), periodList);
	}
	List<StudentTO> studentTOs = new LinkedList<StudentTO>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		if(studentPeroidMap.containsKey(to.getId())){
			List<PeriodTO> perioList= studentPeroidMap.get(to.getId());
			to.setPeriodTOs(perioList);
		}
		studentTOs.add(to);
	}
	attendanceEntryForm.setStudentList(studentTOs);
}



//for modify attendance
public void getStudentsList3(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {


	IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
	
	List<StudentTO> studentList= null;
	List<List<StudentTO>> studentsList=new LinkedList<List<StudentTO>>();
	
	List attList = null;
	List pList = null;
	List sList = null;
	List pnList = null;
	Map m=new HashMap<Integer,StudentTO>();
	Set<Integer> classSet = new HashSet<Integer>();
	boolean once=true;
	for (String str : attendanceEntryForm.getClasses()) {
		if(str != null){
			classSet.add(Integer.parseInt(str));
		}
	}
	
	Set<Integer> periodSet = new HashSet<Integer>();
	if (attendanceEntryForm.getPeriods() != null) {
		for (String str : attendanceEntryForm.getPeriods()) {
			periodSet.add(Integer.parseInt(str));
		}
	}
	
	
	Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
	Set<Integer> newPeriodsSet = new HashSet<Integer>();
	List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
	newPeriodsSet.addAll(totalperiodList);
	
	List<StuCocurrLeave> attendanceList = attendanceEntryTransaction.getActivityAttendanceOnDateClassPeriod(classSet,newPeriodsSet, date);

	// finding the pre-approved leaves.
	IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
	List<StudentLeave> leaveStudentList = approveLeaveTransaction.getApprovedLeavesForDayAndClass(date, classSet);
	Set<Integer> leaveStudents = new HashSet<Integer>();

	List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
	
	Collections.sort(periodsList);
	// listing the students only for particular class.
	leaveStudents = AttendanceEntryHelper.getInstance().getLeaveStudents(date, leaveStudentList, periodsList, newPeriodsSet);

	Set<Integer> coCoCurricularLeaveStudents = AttendanceEntryHelper.getInstance().getCoCurricularLeaveStudents(date, attendanceList,	periodsList, newPeriodsSet);

	
	
	attList=attendanceEntryTransaction.getClassAttendancesStudents(classSet, date);
	pList=attendanceEntryTransaction.getPeriodNamesOnClass(classSet);
	pnList=attendanceEntryTransaction.getPeriodNmaesOnPeriods(periodSet);
	sList=attendanceEntryTransaction.getStudentsOnAttendance(Integer.parseInt(attendanceEntryForm.getAttendanceId()));
	if(pnList!=null &&  !pnList.isEmpty()){
		attendanceEntryForm.setPeriodName(pnList.get(0).toString());
	}
	//List<String> li=null;
	//raghu write for new
	 attendanceEntryForm.setPeriodsList(pList);
	 int periodCount=0;
	Iterator<String> i = pList.iterator();
	while (i.hasNext()) {
		
		Iterator<Student> is = sList.iterator();
		String p =i.next();
		//li=new LinkedList<String>();
		
			
		while (is.hasNext()) {
			
			Student s=is.next();
			if (s.getIsHide()==null || s.getIsHide().equals(false))
			{
			if (s.getIsAdmitted()) {
			
			
			
			StudentTO st=new StudentTO();
			st.setId(s.getId());
			st.setRollNo(s.getRollNo());
			st.setStudentName(s.getAdmAppln().getPersonalData().getFirstName());
			st.setRegisterNo(s.getRegisterNo());
			st.setIsTaken(false);
			if(pnList.contains(p) && once){
				attendanceEntryForm.setPeriodCount(periodCount);
				st.setIsCurrent(true);
				st.setIsModify(true);
			}
			else
			{
				st.setIsCurrent(false);
				st.setIsModify(false);
			}
			st.setPeriodName(p);
			st.setSubjectId(attendanceEntryForm.getSubjectId());
			
			// Already taken activity attendance is making as absent
			if (!coCoCurricularLeaveStudents.isEmpty()) {

					if (coCoCurricularLeaveStudents.contains(st.getId())) {
						
						st.setTempChecked(false);
						st.setCoCurricularLeavePresent(true);
					}
				
			}
			
			// Approved Leave attendance is making as absent
			if (!leaveStudents.isEmpty()) {

					if (leaveStudents.contains(st.getId())) {
						
						st.setTempChecked(false);
						st.setStudentLeave(true);
					}
				
			}
			
			
			m.put(s.getId(), st);
			
			
			}
			}
			
			
		}
		
		if(pnList.contains(p))
			once=false;
		
		//System.out.println("----------------------");
		Iterator<Object[]> i1 = attList.iterator();
		while (i1.hasNext()) {

		Object[] object =i1.next();
		String s=object[1].toString();	
		//System.out.println(p.getPeriodName()+"==="+s);
		if(p.equalsIgnoreCase(s)){
		//li.add(object[0].toString()+"="+object[1].toString());	
		
		if(m.containsKey(Integer.parseInt(object[0].toString()))){
			StudentTO sto= (StudentTO)m.get(Integer.parseInt(object[0].toString()));
			if(sto.getIsModify()){
				sto.setIsTaken(false);
				sto.setIsCurrent(true);
				sto.setIsModify(true);
				if(new Boolean(object[5].toString())){
					sto.setTempChecked(false);
				}else{
					sto.setTempChecked(true);
				}
			}else{
				sto.setIsTaken(true);
				sto.setIsCurrent(false);
				sto.setIsModify(false);
				sto.setTempChecked(new Boolean(object[5].toString()));
			}
			
			sto.setCoCurricularLeavePresent(new Boolean(object[3].toString()));
			sto.setStudentLeave(new Boolean(object[4].toString()));
			
			
			
			sto.setSubjectId(attendanceEntryForm.getSubjectId());
			sto.setTeacherName(object[6].toString());
			sto.setClassName(object[7].toString());
			sto.setSubjectName(object[8].toString());
			sto.setAttendanceStudentId(object[10].toString());
			m.put(sto.getId(), sto);
		}
		
		}
		 
	}
	
	
	studentList=new LinkedList<StudentTO>();
	Set<Entry<Integer, StudentTO>> studentCourseAllotmentSet= m.entrySet();
	Iterator<Entry<Integer, StudentTO>> iset=studentCourseAllotmentSet.iterator();
	while(iset.hasNext())
	{
	Entry<Integer, StudentTO> e=iset.next();
	StudentTO sto=e.getValue();
	studentList.add(sto);
	//System.out.println(p+"=====pname========"+sto.getRollNo()+"====rno====="+sto.getIsTaken()+"======take======"+sto.isTempChecked()+"====cur===="+""+sto.getIsCurrent());
	}
	/*Collections.sort(studentList,new Comparator<StudentTO>() {
		@Override
		public int compare(StudentTO arg0, StudentTO arg1) {
			String string = arg0.getStudentName();
			String string2=arg1.getStudentName();
			return string.compareTo(string2);
		}
	})
	;*/
	int classSchemeId = 0;
	if (attendanceEntryForm.getClasses() != null) {
			classSchemeId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
		}
	
	IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
	 
	 boolean isRegnoCheck = activityAttendenceTransaction.checkIsRegisterNo(classSchemeId);
	 attendanceEntryForm.setRegNoDisplay(isRegnoCheck);
	 
	 /*ExamComparatorForAlphanumeric e=new ExamComparatorForAlphanumeric();
	 e.setCompare(1);

	 Collections.sort(studentList,e);*/
	 
	 Collections.sort(studentList,new Comparator<StudentTO>() {
			@Override
			public int compare(StudentTO arg0, StudentTO arg1) {
				//String string = arg0.getRollNo();
				//String string2=arg1.getRollNo();
				Integer n1=new Integer(arg0.getRollNo());
				Integer n2=new Integer(arg1.getRollNo());
				//return string.compareTo(string2);
				return n1.compareTo(n2);
			}
		})
		;

	//li1.add(li);
	 periodCount++;
	 studentsList.add(studentList);
	}

	attendanceEntryForm.setStudentsList(studentsList);
	Map<Integer, List<PeriodTO>> studentPeroidMap= new LinkedHashMap<Integer, List<PeriodTO>>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		List<PeriodTO> periodList= new LinkedList<PeriodTO>();
		for(List<StudentTO> lists1:attendanceEntryForm.getStudentsList()){
			PeriodTO periodTO = null;
			for(StudentTO studentTO : lists1){
				if(studentTO.getId()==to.getId() && !pnList.contains(studentTO.getPeriodName())){
					periodTO= new PeriodTO();
					periodTO.setPeriodName(studentTO.getPeriodName());
					periodTO.setHoursTaken(String.valueOf(studentTO.getIsTaken()));
					periodTO.setTempChecked(String.valueOf(studentTO.isTempChecked()));
				}
			}
			if(periodTO!=null){
				periodList.add(periodTO);
			}
		}
		studentPeroidMap.put(to.getId(), periodList);
	}
	List<StudentTO> studentTOs = new LinkedList<StudentTO>();
	for(StudentTO to : attendanceEntryForm.getStudentList()){
		if(studentPeroidMap.containsKey(to.getId())){
			List<PeriodTO> perioList= studentPeroidMap.get(to.getId());
			to.setPeriodTOs(perioList);
		}
		studentTOs.add(to);
	}
	attendanceEntryForm.setStudentList(studentTOs);

}

public boolean saveAttendanceTimeRestriction(AttendanceEntryForm attendanceEntryForm) throws Exception {
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	AttendanceTimeRestrictionClasswise bo = txn.alreadyExist(attendanceEntryForm);
	if(bo!=null){
		throw new DuplicateException();
	}else{
		bo = new AttendanceTimeRestrictionClasswise();
		ClassSchemewise classBo = new ClassSchemewise();
		classBo.setId(Integer.valueOf(attendanceEntryForm.getClassId()));
		bo.setClassSchemewise(classBo);
		bo.setCreatedBy(attendanceEntryForm.getUserId());
		bo.setCreatedDate(new java.util.Date());
		bo.setModifiedBy(attendanceEntryForm.getUserId());
		bo.setLastModifiedDate(new java.util.Date());
		String formatDate = attendanceEntryForm.getTimeHours()+":"+attendanceEntryForm.getTimeMins()+":00 ";
		bo.setTime(formatDate);
		bo.setYear(Integer.valueOf(attendanceEntryForm.getYear()));
		bo.setIsActive(true);
		boolean saved = txn.saveAttendanceTimeRestrictionBo(bo);
		return saved;
	}
}

public List<AttendanceTO> getAttendanceTimeRestrictedList(AttendanceEntryForm attendanceEntryForm) throws Exception {
		IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
		List<AttendanceTimeRestrictionClasswise> boList = txn.getAttendanceTimeRestrictedList();
		List<AttendanceTO> toList = new ArrayList<AttendanceTO>();
		Iterator<AttendanceTimeRestrictionClasswise> itr = boList.iterator();
		while(itr.hasNext()){
			AttendanceTimeRestrictionClasswise bo = itr.next();
			AttendanceTO to = new AttendanceTO();
			to.setId(bo.getId());
			to.setClassSchemewiseId(bo.getClassSchemewise().getId());
			to.setClassName(bo.getClassSchemewise().getClasses().getName());
			to.setTime(bo.getTime());
			String time = bo.getTime();
			to.setTimeHours(time.substring(0,2));
			to.setTimeMins(time.substring(3,5));
			to.setSessionName(time.substring(8).trim());
			to.setAcadamicYear(String.valueOf(bo.getYear()));
			//attendanceEntryForm.setYear(String.valueOf(bo.getYear()));
			if(bo.getClassSchemewise().getClasses().getTermNumber()%2==0)
				to.setEvenOrOdd("even");
			else
				to.setEvenOrOdd("odd");
			toList.add(to);
		}
		return toList;
	}

public boolean updateAttendanceTimeRestriction(AttendanceEntryForm attendanceEntryForm) throws Exception {
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	boolean isUpdate = false;
	
	String timeHours = attendanceEntryForm.getTimeHours();	
	String timeMins = attendanceEntryForm.getTimeMins();
	
	AttendanceTimeRestrictionClasswise attBo = new AttendanceTimeRestrictionClasswise();
	attBo.setId(attendanceEntryForm.getAttTimeRestrictionId());
	ClassSchemewise classSchemewise = new ClassSchemewise();
	classSchemewise.setId(Integer.valueOf(attendanceEntryForm.getClassId()));
	attBo.setClassSchemewise(classSchemewise);
	attBo.setTime(timeHours+":"+timeMins+":00");
	attBo.setYear(Integer.valueOf(attendanceEntryForm.getAcademicYear()));
	attBo.setLastModifiedDate(new java.util.Date());
	attBo.setModifiedBy(attendanceEntryForm.getUserId());
	attBo.setIsActive(true);
	isUpdate = txn.updateAttendanceTimeRestrictionBo(attBo);
	return isUpdate;
}

public boolean deleteAttendanceTimeRestriction(AttendanceEntryForm attendanceEntryForm) {
	log.debug("call of deleteAttendanceTimeRestriction in AttendanceEntryHandler.class ");
	boolean isDeletd=false;
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	isDeletd=txn.deleteAttendanceTimeRestriction(attendanceEntryForm);
	log.debug("end of deleteAttendanceTimeRestriction in AttendanceEntryHandler.class ");
	return isDeletd;
}

public List<AttendanceTimeRestrictionClasswise> getAttendanceTimeRestriction(
		String[] classes) throws Exception {
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	List<AttendanceTimeRestrictionClasswise> bo = txn.getAttendanceTimeRestriction(classes);
	return bo;
}

public boolean checkHolidayDate(String attendancedate) throws Exception{
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	boolean checkDate=false;
	List<HolidayBO> holidayList=txn.checkHolidayDate();
	Iterator<HolidayBO> itr=holidayList.iterator();
	while(itr.hasNext()){
		HolidayBO bo = itr.next();
		java.util.Date holidayDate= bo.getHolidayDate();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String holidayDateString=dateFormat.format(holidayDate);
		if(holidayDateString!=null){
		if(attendancedate.equals(holidayDateString)){
			checkDate=true;
			return checkDate;
		}
	  }
	}
	return checkDate;
}

public String getclassName(int classId) throws Exception{
	String className = null;
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	className = txn.getclassName(classId);
	return className;
}

public void setCoEntryTeachersName(AttendanceEntryForm attendanceEntryForm) {
	IAttendanceEntryTransaction txn = AttendanceEntryTransactionImpl.getInstance();
	List<TTUsers> coTeachers=null;
	String query=null;
	query=AttendanceEntryHelper.getInstance().createQueryForCoTeachersForAtendance(attendanceEntryForm);
	coTeachers=txn.getCoTeachersForAteendance(query);
	AttendanceEntryHelper.getInstance().convertCoBoTo(coTeachers,attendanceEntryForm);
	
}

}
