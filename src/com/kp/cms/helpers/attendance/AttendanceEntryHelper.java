package com.kp.cms.helpers.attendance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceClass;
import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.StudentLeaveDetails;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.bo.admin.TTUsers;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;

/**
 * This Handler class will be used in attendance entry.
 * This class contains methods to data copy from BO's to TO's 
 *
 */
public class AttendanceEntryHelper {
	
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
	
	private static volatile AttendanceEntryHelper attendanceEntryHelper= null;
	private static final Log log = LogFactory.getLog(AttendanceEntryHelper.class);
	/**
	 * This method returns the single instance of AttendanceEntryHelper impl.
	 * @return
	 */
	public static AttendanceEntryHelper getInstance() {
	      if(attendanceEntryHelper == null) {
	    	  attendanceEntryHelper = new AttendanceEntryHelper();
	    	  return attendanceEntryHelper;
	      }
	      return attendanceEntryHelper;
	}
	
	/**
	 *        This method copies the data from BO's to TO's objects
	 * @param studentList
	 * @param subjectId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<StudentTO> copyStudentBoToTO(List<Student> studentList,String subjectId ,List<Integer> listOfDetainedStudents,HttpSession session,AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("Handler : Inside copyStudentBoToTO");
		//code added by mehaboob
		List<StudentTO> list = new ArrayList<StudentTO>();
		// which subject which class added by mehaboob 
		Map<Integer, List<String>> subjectClassMap=new HashMap<Integer, List<String>>();
		//end
		Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
		if(subjectId != null && !subjectId.isEmpty()) {
			//code added by mehaboob start
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
			if(subjectCodeGroupMap.containsKey(subjectId)){
			  List<Integer> subjectIds=subjectCodeGroupMap.get(subjectId);
			  for (Integer integer : subjectIds) {
			    	setStudentDetailsBySubjectId(list,integer,studentList,listOfDetainedStudents,subjectClassMap);
			 }
			}else{
				setStudentDetailsBySubjectId(list,Integer.parseInt(subjectId),studentList,listOfDetainedStudents,subjectClassMap);
			}
			}else{
				setStudentDetailsBySubjectId(list,Integer.parseInt(subjectId),studentList,listOfDetainedStudents,subjectClassMap);
			}
			
		}else{
			setStudentDetailsBySubjectId(list,0,studentList,listOfDetainedStudents,subjectClassMap);
		}
		//end
		log.info("Handler : Leaving copyStudentBoToTO");
		//added by mahi start
		if(!subjectClassMap.isEmpty()){
			attendanceEntryForm.setSubjectClassMap(subjectClassMap);
		}//end
	    return list;	
	}
	
	
	/**
	 * 		  This method checks the subject present in the particular subject group
	 * @param subjectId
	 * @param subGrpList
	 * @return
	 * @throws Exception
	 */
	public boolean checkSubjectPresentInGroup(int subjectId,Set<ApplicantSubjectGroup> subGrpList) throws Exception {
		log.info("Handler : Inside checkSubjectPresentInGroup");
		if(subGrpList.isEmpty()){
			return false;
		}
		boolean result = false;
		ApplicantSubjectGroup applicantSubjectGroup;
		SubjectGroupSubjects subjectGroupSubjects ;
		Iterator<ApplicantSubjectGroup> itr = subGrpList.iterator();
		label :while(itr.hasNext()) {
			applicantSubjectGroup = itr.next();
			if(applicantSubjectGroup.getSubjectGroup()!=null && applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses()!=null){
				Iterator<SubjectGroupSubjects> itr1 = applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses().iterator();
				while(itr1.hasNext()) {
					subjectGroupSubjects = itr1.next();
					if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getId() == subjectId && subjectGroupSubjects.getSubject().getIsActive() && subjectGroupSubjects.getIsActive()) {
						result = true;
						continue label;
					}
					
				}
			}
		}
		log.info("Handler : Leaving checkSubjectPresentInGroup");
		return result;
	}
	
	
	/**
	 * 	 	  Copy batch student BO's to studentTO's
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> copyBatchStudentBosToStudentTO(List<BatchStudent> studentList, List<Integer> listOfDetainedStudents) throws Exception {
		log.info("Handler : Inside copyBatchStudentBosToStudentTO");
		List<StudentTO> list = new ArrayList<StudentTO>();
		Iterator<BatchStudent> itr = studentList.iterator();
		StudentTO studentTo;
		BatchStudent batchStudent;
		StringBuffer studentName = new StringBuffer();
		while(itr.hasNext()) {
			studentTo = new StudentTO();
			batchStudent = itr.next();
			if(!listOfDetainedStudents.contains(batchStudent.getStudent().getId())){
			studentTo.setId(batchStudent.getStudent().getId());
			studentTo.setRegisterNo(batchStudent.getStudent().getRegisterNo());
			studentTo.setRollNo(batchStudent.getStudent().getRollNo());
			studentTo.setChecked(false);
			studentTo.setTempChecked(false);
			studentTo.setIsTaken(false);
			studentTo.setStudentName(batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName());
			
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName()).append(" ");	
			} 
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName()).append(" ");
			}
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName()).append(" ");
			}
			studentTo.setStudentName(studentName.toString());
			list.add(studentTo);
			studentName=new StringBuffer();
		}
		}
		log.info("Handler : Leaving copyBatchStudentBosToStudentTO");
	    return list;	
	}
	
	/**
	 * 		  This method get the constructs the attendance object from form.
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 * HttpSession Added by mehaboob 
	 */
	@SuppressWarnings("unchecked")
	public List<Attendance> getAttendanceObj(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {
		log.info("Handler : Inside getAttendanceObj");
		List<Attendance> attendanceList=new ArrayList<Attendance>();
		//code changed by mehaboob for combined class start added 
		if(attendanceEntryForm.getSubjectId()!=null && !attendanceEntryForm.getSubjectId().isEmpty()) {
			Map<Integer, List<Integer>> subjectBatchMap= (Map<Integer, List<Integer>>) session.getAttribute("SubjectBatchMap");
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
			    if(subjectCodeGroupMap.containsKey(attendanceEntryForm.getSubjectId())){
			    	List<Integer> subjectIdList=subjectCodeGroupMap.get(attendanceEntryForm.getSubjectId());
			    	for (Integer integer : subjectIdList) {
			    		int batchId=0;
			    		if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
			    			batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
			    			createAttendanceObjectBySubject1(integer,attendanceEntryForm,attendanceList,batchId);
			    		}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
			    			batchId=attendanceEntryForm.getSubjectBatchMap().get(integer);
			    			createAttendanceObjectBySubject1(integer,attendanceEntryForm,attendanceList,batchId);
			    		}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
			    			for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
			    				if(batchIds!=null && !batchIds.isEmpty()){
			    				if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
			    				 List<Integer> batchidList=subjectBatchMap.get(integer);
			    				 if(batchidList!=null && !batchidList.isEmpty()){
			    				 for (Integer batchIdFromMap : batchidList) {
									if(batchIdFromMap==Integer.parseInt(batchIds)){
										createAttendanceObjectBySubject1(integer,attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
									}
								}
			    				 }
			    				}
			    				}else if(attendanceEntryForm.getBatchIdsArray().length==1){
			    					createAttendanceObjectBySubject1(integer,attendanceEntryForm,attendanceList,0);
			    				}
							}
			    		}else{
			    			createAttendanceObjectBySubject1(integer,attendanceEntryForm,attendanceList,batchId);
			    		}
					}
			    	
			    }else{
			    	int batchId=0;
		    		if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
		    			batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
		    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
		    		}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
		    			batchId=attendanceEntryForm.getSubjectBatchMap().get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
		    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
		    		}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
		    			for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
		    				if(batchIds!=null && !batchIds.isEmpty()){
		    				if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
		    				 List<Integer> batchidList=subjectBatchMap.get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
		    				 for (Integer batchIdFromMap : batchidList) {
								if(batchIdFromMap==Integer.parseInt(batchIds)){
									createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
								}
							}
		    				}
		    				}else if(attendanceEntryForm.getBatchIdsArray().length==1){
			    					createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,0);
			    			}
						}
		    		}else{
		    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
		    		}
			    }
			}else{
				int batchId=0;
	    		if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
	    			batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
	    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
	    		}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
	    			batchId=attendanceEntryForm.getSubjectBatchMap().get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
	    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
	    		}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
	    			for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
	    				if(batchIds!=null && !batchIds.isEmpty()){
	    				if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
	    				 List<Integer> batchidList=subjectBatchMap.get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
	    				 for (Integer batchIdFromMap : batchidList) {
							if(batchIdFromMap==Integer.parseInt(batchIds)){
								createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
							}
						}
	    				}
	    				}else if(attendanceEntryForm.getBatchIdsArray().length==1){
	    					createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,0);
		    			}
					}
	    		}else{
	    			createAttendanceObjectBySubject1(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
	    		}
			}
			
		}else{
			if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
					createAttendanceObjectBySubject1(0,attendanceEntryForm,attendanceList,Integer.parseInt(batchId));
					}else if(attendanceEntryForm.getBatchIdsArray().length==1){
    					createAttendanceObjectBySubject1(0,attendanceEntryForm,attendanceList,0);
	    			}
				}
			}else if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
				createAttendanceObjectBySubject1(0,attendanceEntryForm,attendanceList,Integer.parseInt(attendanceEntryForm.getBatchId()));
			}else{
				createAttendanceObjectBySubject1(0,attendanceEntryForm,attendanceList,0);
			}
			
		}
		//end
		log.info("Handler : Leaving getAttendanceObj");
		return attendanceList;
	}

	/**
	 *  This returns the attendance object loaded from database
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public Attendance getAttendanceObjUpdate(AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("Handler : Inside getAttendanceObjUpdate");
		Attendance attendance = new Attendance();
		
		AttendanceType attendanceType = new AttendanceType();
		attendanceType.setId(Integer.parseInt(attendanceEntryForm.getAttendanceTypeId()));
		attendance.setAttendanceType(attendanceType);
		if(attendanceEntryForm.getHoursHeld()!=null && attendanceEntryForm.getHoursHeld().length() != 0){
			attendance.setHoursHeld(Integer.parseInt(attendanceEntryForm.getHoursHeld()));
		}
		attendance.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate()));
		attendance.setId(Integer.parseInt(attendanceEntryForm.getAttendanceId()));
		
		if(attendanceEntryForm.getActivityId() != null && attendanceEntryForm.getActivityId().length()!= 0) {
			Activity activity = new Activity();
			activity.setId(Integer.parseInt(attendanceEntryForm.getActivityId()));
			attendance.setActivity(activity);
			attendance.setIsActivityAttendance(true);
		}
		if(attendanceEntryForm.getSubjectId()!=null && attendanceEntryForm.getSubjectId().length() != 0) {
			Subject subject = new Subject();
			subject.setId(Integer.parseInt(attendanceEntryForm.getSubjectId()));
			attendance.setSubject(subject);
		}
		if(attendanceEntryForm.getBatchId()!=null && attendanceEntryForm.getBatchId().length() != 0) {
			Batch batch = new Batch();
			batch.setId(Integer.parseInt(attendanceEntryForm.getBatchId()));
			attendance.setBatch(batch);
		}
		attendance.setCreatedBy(attendanceEntryForm.getUserId());
		attendance.setModifiedBy(attendanceEntryForm.getUserId());
		attendance.setCreatedDate(new Date());
		attendance.setLastModifiedDate(new Date());
		
		
		Set<AttendanceClass> attendanceClassSet = new HashSet<AttendanceClass>();
		AttendanceClass attendanceClass;
		ClassSchemewise classSchemewise;
		if(attendanceEntryForm.getClasses() != null) {
			int i=0;
			int i1=attendanceEntryForm.getAttendanceClassId().length;
			for(String str :attendanceEntryForm.getClasses()) {
				attendanceClass = new AttendanceClass();
				
				classSchemewise = new ClassSchemewise();
				classSchemewise.setId(Integer.parseInt(str));
				attendanceClass.setClassSchemewise(classSchemewise);
				
				attendanceClass.setCreatedBy(attendanceEntryForm.getUserId());
				attendanceClass.setModifiedBy(attendanceEntryForm.getUserId());
				attendanceClass.setCreatedDate(new Date());
				attendanceClass.setLastModifiedDate(new Date());
				
				//raghu
				if(attendanceEntryForm.getAttendanceClassId()!=null && i<i1)
				attendanceClass.setId(Integer.parseInt(attendanceEntryForm.getAttendanceClassId()[i]));
				
				attendanceClassSet.add(attendanceClass);	
				i++;
			}
			
		}
		attendance.setAttendanceClasses(attendanceClassSet);
		
		Set<AttendancePeriod> attendancePeriodSet = new HashSet<AttendancePeriod>();
		AttendancePeriod attendancePeriod;
		Period period;
		if(attendanceEntryForm.getPeriods() != null) {	
			int i=0;
			int i1=attendanceEntryForm.getAttendancePeriodId().length;
			for(String str :attendanceEntryForm.getPeriods()) {
				attendancePeriod = new AttendancePeriod();
				
				period = new Period();
				period.setId(Integer.parseInt(str));
				attendancePeriod.setPeriod(period);
				
				attendancePeriod.setCreatedBy(attendanceEntryForm.getUserId());
				attendancePeriod.setModifiedBy(attendanceEntryForm.getUserId());
				attendancePeriod.setCreatedDate(new Date());
				attendancePeriod.setLastModifiedDate(new Date());
				
				//raghu
				if(attendanceEntryForm.getAttendancePeriodId()!=null && i<i1)
					attendancePeriod.setId(Integer.parseInt(attendanceEntryForm.getAttendancePeriodId()[i]));
					
				attendancePeriodSet.add(attendancePeriod);	
				i++;
			}
			
		}	
		attendance.setAttendancePeriods(attendancePeriodSet);
		
		Set<AttendanceInstructor> attendanceInstructorSet = new HashSet<AttendanceInstructor>();
		AttendanceInstructor attendanceInstructor;
		Users users;
		if(attendanceEntryForm.getTeachers() != null) {
			int i=0;
			int i1=attendanceEntryForm.getAttendanceInstructorId().length;
			for(String str :attendanceEntryForm.getTeachers()) {
				attendanceInstructor = new AttendanceInstructor();
				users = new Users();
				users.setId(Integer.parseInt(str));
				attendanceInstructor.setUsers(users);
				attendanceInstructor.setCreatedBy(attendanceEntryForm.getUserId());
				attendanceInstructor.setModifiedBy(attendanceEntryForm.getUserId());
				attendanceInstructor.setCreatedDate(new Date());
				attendanceInstructor.setLastModifiedDate(new Date());
				
				//raghu
				if(attendanceEntryForm.getAttendanceInstructorId()!=null && i<i1)
				attendanceInstructor.setId(Integer.parseInt(attendanceEntryForm.getAttendanceInstructorId()[i]));
					
				attendanceInstructorSet.add(attendanceInstructor);	
				i++;
			}
			
		}
		attendance.setAttendanceInstructors(attendanceInstructorSet);
		
		
		/*
		
		Set<AttendanceStudent> studentSet = new HashSet<AttendanceStudent>();
		AttendanceStudent attendanceStudent;
		StudentTO studentTO;
		Student student;
		Iterator<StudentTO> itr = attendanceEntryForm.getStudentList().iterator();
		while(itr.hasNext()) {
			attendanceStudent = new AttendanceStudent();
			studentTO = itr.next();
			if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
				attendanceStudent.setIsPresent(false);
			} else {
				attendanceStudent.setIsPresent(true);
			}
			student = new Student();
			student.setId(studentTO.getStudent().getId());
			attendanceStudent.setId(studentTO.getId());
			attendanceStudent.setStudent(student);
			
			//Added by Manu.leaves are not updating if taking attendance then put leaves then make attendance as absent
			boolean flagg=false;
			if(studentTO.getStudentLeave()) {
				attendanceStudent.setIsOnLeave(true);
			} else {
				  if(studentTO.isChecked()){
						IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
						Set<Integer> newPeriodsSet = new HashSet<Integer>();
						Set<Integer> classSet = new HashSet<Integer>();
						String reg=studentTO.getRegisterNo();
						Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
						List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
						Collections.sort(periodsList);
						for (String str : attendanceEntryForm.getClasses()) {
							if(str != null){
								classSet.add(Integer.parseInt(str));
							}
						}
						List<StudentLeave> studentLeaves = attendanceEntryTransaction.getLeavesDetails(reg,date, classSet);
						Iterator<StudentLeave> itrr = studentLeaves.iterator();
						StudentLeave studentLeave;
						Date startDate;
						Date endDate;
						while(itrr.hasNext()) {
							studentLeave = itrr.next();
							startDate = studentLeave.getStartDate();
							endDate = studentLeave.getEndDate();
						List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
						newPeriodsSet.addAll(totalperiodList);
						Set<Integer> daysPeriod=getDaysPeriod(date,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
						Iterator<Integer> itr1 = newPeriodsSet.iterator();
						while(itr1.hasNext()) {
							if(daysPeriod.contains(itr1.next())){
								flagg=true;
							    }
						       }
						     }
						   }
					if(flagg==true && studentTO.isChecked()){				
						attendanceStudent.setIsOnLeave(true);
					 }else{
						 attendanceStudent.setIsOnLeave(false);
					     }
			}	
			
			//Added by manu.CoCurricularLeave are not updating if taking attendance then put CoCurricularLeave then make attendance as absent
			boolean flag=false;
			if(studentTO.getCoCurricularLeavePresent()) {
				attendanceStudent.setIsCoCurricularLeave(true);
			} else {
				  if(studentTO.isChecked()){
				IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
				Set<Integer> newPeriodsSet = new HashSet<Integer>();
				Set<Integer> classSet = new HashSet<Integer>();
				String reg=studentTO.getRegisterNo();
				Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
				List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
				Collections.sort(periodsList);
				for (String str : attendanceEntryForm.getClasses()) {
					if(str != null){
						classSet.add(Integer.parseInt(str));
					}
				}
				List<StuCocurrLeave> stuCocurrLeave=attendanceEntryTransaction.stuCocurrLeaveResult(reg,attendanceEntryForm,classSet);
				Iterator<StuCocurrLeave> itrr = stuCocurrLeave.iterator();
				StuCocurrLeave studentLeave;
				Date startDate;
				Date endDate;
				while(itrr.hasNext()) {
					studentLeave = itrr.next();
					startDate = studentLeave.getStartDate();
					endDate = studentLeave.getEndDate();
				List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
				newPeriodsSet.addAll(totalperiodList);
				Set<Integer> daysPeriod=getDaysPeriod(date,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
				Iterator<Integer> itr1 = newPeriodsSet.iterator();
				while(itr1.hasNext()) {
					if(daysPeriod.contains(itr1.next())){
						flag=true;
					    }
				       }
				     }
				   }
			if(flag==true && studentTO.isChecked()){				
		    	attendanceStudent.setIsCoCurricularLeave(true);
			 }else{
			    attendanceStudent.setIsCoCurricularLeave(false);
			     }
			}
			attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
			attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
			attendanceStudent.setCreatedDate(new Date());
			attendanceStudent.setLastModifiedDate(new Date());
			studentSet.add(attendanceStudent);
		}
		attendance.setAttendanceStudents(studentSet);
		*/
		
		 //if(studentTO.getIsCurrent() && !studentTO.getIsTaken())
		//new code raghu
		
		
		Set<AttendanceStudent> studentSet = new HashSet<AttendanceStudent>();
		AttendanceStudent attendanceStudent;
		StudentTO studentTO;
		Student student;
		List<StudentTO> studentTOList;
		
		Iterator<List<StudentTO>> itr1 = attendanceEntryForm.getStudentsList().iterator();
		while(itr1.hasNext()) {
		
		Iterator<StudentTO> itr = itr1.next().iterator();
		while(itr.hasNext()) {
			attendanceStudent = new AttendanceStudent();
			studentTO = itr.next();
			
			if(studentTO.getIsCurrent() && studentTO.getIsModify() && !studentTO.getIsTaken()){
			
			if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
				attendanceStudent.setIsPresent(false);
			} else {
				attendanceStudent.setIsPresent(true);
			}
			student = new Student();
			//student.setId(studentTO.getStudent().getId());
			student.setId(studentTO.getId());
			attendanceStudent.setStudent(student);
			
			if(studentTO.getAttendanceStudentId()!=null && !studentTO.getAttendanceStudentId().equalsIgnoreCase(""))
			attendanceStudent.setId(Integer.parseInt(studentTO.getAttendanceStudentId()));
			
			//Added by Manu.leaves are not updating if taking attendance then put leaves then make attendance as absent
			boolean flagg=false;
			if(studentTO.getStudentLeave()) {
				attendanceStudent.setIsOnLeave(true);
			} else {
				  if(studentTO.isChecked()){
						IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
						Set<Integer> newPeriodsSet = new HashSet<Integer>();
						Set<Integer> classSet = new HashSet<Integer>();
						String reg=studentTO.getRegisterNo();
						Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
						List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
						Collections.sort(periodsList);
						for (String str : attendanceEntryForm.getClasses()) {
							if(str != null){
								classSet.add(Integer.parseInt(str));
							}
						}
						List<StudentLeave> studentLeaves = attendanceEntryTransaction.getLeavesDetails(reg,date, classSet);
						Iterator<StudentLeave> itrr = studentLeaves.iterator();
						StudentLeave studentLeave;
						Date startDate;
						Date endDate;
						while(itrr.hasNext()) {
							studentLeave = itrr.next();
							startDate = studentLeave.getStartDate();
							endDate = studentLeave.getEndDate();
						List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
						newPeriodsSet.addAll(totalperiodList);
						Set<Integer> daysPeriod=getDaysPeriod(date,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
						Iterator<Integer> itr2 = newPeriodsSet.iterator();
						while(itr2.hasNext()) {
							if(daysPeriod.contains(itr2.next())){
								flagg=true;
							    }
						       }
						     }
						   }
					if(flagg==true && studentTO.isChecked()){				
						attendanceStudent.setIsOnLeave(true);
					 }else{
						 attendanceStudent.setIsOnLeave(false);
					     }
			}	
			
			//Added by manu.CoCurricularLeave are not updating if taking attendance then put CoCurricularLeave then make attendance as absent
			boolean flag=false;
			if(studentTO.getCoCurricularLeavePresent()) {
				attendanceStudent.setIsCoCurricularLeave(true);
			} else {
				  if(studentTO.isChecked()){
				IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
				Set<Integer> newPeriodsSet = new HashSet<Integer>();
				Set<Integer> classSet = new HashSet<Integer>();
				String reg=studentTO.getRegisterNo();
				Date date = CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate());
				List<Period> periodsList=attendanceEntryTransaction.getperiodsByIds(attendanceEntryForm.getClasses());
				Collections.sort(periodsList);
				for (String str : attendanceEntryForm.getClasses()) {
					if(str != null){
						classSet.add(Integer.parseInt(str));
					}
				}
				List<StuCocurrLeave> stuCocurrLeave=attendanceEntryTransaction.stuCocurrLeaveResult(reg,attendanceEntryForm,classSet);
				Iterator<StuCocurrLeave> itrr = stuCocurrLeave.iterator();
				StuCocurrLeave studentLeave;
				Date startDate;
				Date endDate;
				while(itrr.hasNext()) {
					studentLeave = itrr.next();
					startDate = studentLeave.getStartDate();
					endDate = studentLeave.getEndDate();
				List<Integer> totalperiodList=attendanceEntryTransaction.getAllPeriodIdsByInput(attendanceEntryForm);
				newPeriodsSet.addAll(totalperiodList);
				Set<Integer> daysPeriod=getDaysPeriod(date,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
				Iterator<Integer> itr2 = newPeriodsSet.iterator();
				while(itr2.hasNext()) {
					if(daysPeriod.contains(itr2.next())){
						flag=true;
					    }
				       }
				     }
				   }
			if(flag==true && studentTO.isChecked()){				
		    	attendanceStudent.setIsCoCurricularLeave(true);
			 }else{
			    attendanceStudent.setIsCoCurricularLeave(false);
			     }
			}
			attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
			attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
			attendanceStudent.setCreatedDate(new Date());
			attendanceStudent.setLastModifiedDate(new Date());
			
			// raghu from vasavi
			if(studentTO.getIsSmsSent()!=null && studentTO.getIsSmsSent())
			{
				attendanceStudent.setIsSmsSent(Boolean.TRUE);
			}
			else
			{
				attendanceStudent.setIsSmsSent(Boolean.FALSE);
			}
			
			studentSet.add(attendanceStudent);
			
			}// close if
			
		}
		
		}
		
		attendance.setAttendanceStudents(studentSet);
		
		//raghu
		attendance.setSlipNo(attendanceEntryForm.getSlipNo());
		attendance.setIsTimeTable(attendanceEntryForm.getIsTimeTable());
		
		
		
		
		log.info("Handler : Leaving getAttendanceObjUpdate");
		return attendance;
	}
	
	/**
	 * 		  This returns the list of AttendanceTO to display at user.
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AttendanceTO> getAttendanceTOS(List<Attendance> list) throws Exception {
		log.info("Handler : Inside getAttendanceTOS");
		List<AttendanceTO> attenanceList = new ArrayList<AttendanceTO>();
		Iterator<Attendance> itr = list.iterator();
		AttendanceTO attendanceTO;
		Attendance attendance;
		StringBuffer periods = new StringBuffer();
		String newPeriods = "";
		Iterator<AttendancePeriod> periodsItr;
		AttendancePeriod attendancePeriod;
		Set<Integer> attendenseIds = new HashSet<Integer>();
		Iterator<AttendanceInstructor> instructorItr;
		AttendanceInstructor attendanceInstructor;
		StringBuffer instructors = new StringBuffer();
		IAttendanceEntryTransaction transaction = new AttendanceEntryTransactionImpl();
		Map<Integer,String> userMap=transaction.getUsers();
		while(itr.hasNext()) {
			attendance = itr.next();
			attendanceTO = new AttendanceTO();
			if(!attendenseIds.contains(attendance.getId())) {
				attendenseIds.add(attendance.getId());
				attendanceTO.setId(attendance.getId());
				if(attendance.getIsActivityAttendance()){
					attendanceTO.setType(CMSConstants.ACTIVITY_ATTEN);
				} else {
					attendanceTO.setType(CMSConstants.CLASS_ATTEN);
				}
				
				instructorItr = attendance.getAttendanceInstructors().iterator();
				while(instructorItr.hasNext()) {
					attendanceInstructor = instructorItr.next();
					instructors.append(attendanceInstructor.getUsers().getUserName()).append("<br>");
				}
				attendanceTO.setTeachers(instructors.toString());
				
				if(attendance.getSubject() != null) {
					attendanceTO.setSubject(attendance.getSubject().getName()+"("+attendance.getSubject().getCode()+")");
				} else {
					attendanceTO.setSubject("-");
				}	
				if(attendance.getModifiedBy() != null && !attendance.getModifiedBy().isEmpty()) {
					if(userMap.containsKey(Integer.parseInt(attendance.getModifiedBy())))
					attendanceTO.setModifiedBy(userMap.get(Integer.parseInt(attendance.getModifiedBy())));
					
				}else {
					attendanceTO.setModifiedBy("-");
				}
				if(attendance.getLastModifiedDate() != null)
					attendanceTO.setLastModifiedDate(CommonUtil.getStringDateAndTime(attendance.getLastModifiedDate()));
				
				
				periodsItr = attendance.getAttendancePeriods().iterator();
				while(periodsItr.hasNext()) {
					attendancePeriod = periodsItr.next();
					if(attendancePeriod.getPeriod().getStartTime()!=null && !attendancePeriod.getPeriod().getStartTime().isEmpty() && attendancePeriod.getPeriod().getEndTime()!=null && !attendancePeriod.getPeriod().getEndTime().isEmpty()){
						int st=Integer.parseInt(attendancePeriod.getPeriod().getStartTime().substring(0,2));
						int et=Integer.parseInt(attendancePeriod.getPeriod().getEndTime().substring(0,2));
						if(st<=12){
							//periods.append(attendancePeriod.getPeriod().getPeriodName()+"("+attendancePeriod.getPeriod().getStartTime().substring(0,5)+"-"+attendancePeriod.getPeriod().getEndTime().substring(0,5)+")").append("<br>");
							periods.append(attendancePeriod.getPeriod().getPeriodName()).append("<br>");
						}else{
							//periods.append(attendancePeriod.getPeriod().getPeriodName()+"("+pMap.get(st)+attendancePeriod.getPeriod().getStartTime().substring(2,5)+"-"+pMap.get(et)+attendancePeriod.getPeriod().getEndTime().substring(2,5)+")").append("<br>");
							periods.append(attendancePeriod.getPeriod().getPeriodName()).append("<br>");
						}
						}
				}
				if(periods.length() != 0) {
					newPeriods = periods.substring(0, periods.length()-1);
				}
				attendanceTO.setPeriods(newPeriods);
				if(attendance.getActivity() == null) {
					attendanceTO.setActivity("-");
				} else {
					attendanceTO.setActivity(attendance.getActivity().getName());
				}	
				attendanceTO.setChecked(false);
				if(attendance.getBatch() != null) {
					attendanceTO.setBatch(attendance.getBatch().getBatchName());
				} else {
					attendanceTO.setBatch("-");
				}	
				
				//raghu
				attendanceTO.setSlipNo(attendance.getSlipNo());
				attendanceTO.setIsTimeTable(attendance.getIsTimeTable());
				
				attenanceList.add(attendanceTO);
				periods = new StringBuffer();
				instructors = new StringBuffer();
				newPeriods = "";
			}
		}
		log.info("Handler : Leaving getAttendanceTOS");
		return attenanceList;
	}
	
	/**
	 * 	      This method will copy the attendanceTO's to BO's
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public Set<Integer> copyAttendanceToToBO(List<AttendanceTO> list) throws Exception {
		log.info("Handler : Inside copyAttendanceToToBO");
		Set<Integer> attendanceList = new HashSet<Integer>();
		Iterator<AttendanceTO> itr = list.iterator();
		AttendanceTO attendanceTO;
		while(itr.hasNext()) {
			attendanceTO = itr.next();
			if(attendanceTO.isChecked()) {
				attendanceList.add(attendanceTO.getId());
			}	
		}
		log.info("Handler : Leaving copyAttendanceToToBO");
		return attendanceList;
	}
	
	public Set<Integer> getLeaveStudents(Date attendnceDate,List<StudentLeave> leaveStudentList,List<Period>periodsList,Set<Integer>newPeriodsSet) throws Exception {
		Set<Integer> leaveStudents = new HashSet<Integer>();
		Set<StudentLeaveDetails> studentsLeaveDeatils = new HashSet<StudentLeaveDetails>();
		Iterator<StudentLeave> itr = leaveStudentList.iterator();
		StudentLeave studentLeave;
		Date startDate;
		Date endDate;
		while(itr.hasNext()) {
			studentLeave = itr.next();
			startDate = studentLeave.getStartDate();
			endDate = studentLeave.getEndDate();
			Set<Integer> daysPeriod = getDaysPeriod(attendnceDate,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
			Iterator<Integer> itr1 = newPeriodsSet.iterator();
			while(itr1.hasNext()) {
				if(daysPeriod.contains(itr1.next())){
					studentsLeaveDeatils.addAll(studentLeave.getStudentLeaveDetails());
				}
			}
		}
		StudentLeaveDetails LeaveDetails;
		Iterator<StudentLeaveDetails> itr2 = studentsLeaveDeatils.iterator();
		while(itr2.hasNext()){
			LeaveDetails = itr2.next();
			leaveStudents.add(LeaveDetails.getStudent().getId());
		}
		
		return leaveStudents;
	}
	
	public Set<Integer> getDaysPeriod(Date atenDate,Date startDate,Date endDate,int startId,int endId,List<Period> periodList) throws Exception {
		Set<Integer> daysPeriod = new HashSet<Integer>();
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		Calendar attenCal = Calendar.getInstance();
		attenCal.setTime(atenDate);
		boolean conti = false;
		Iterator<Period> itr = periodList.iterator();
		Period period;
		
		if(startCal.compareTo(attenCal)==0 && endCal.compareTo(attenCal)==0) {
			while(itr.hasNext()) {
				period = itr.next();
				if(period.getId() == startId) {
					conti = true;
				}
				if(period.getId() == endId) {
					daysPeriod.add(period.getId());
					conti = false;
				}
				if(conti) {
					daysPeriod.add(period.getId());
				}
			}
			
		} else if(startCal.compareTo(attenCal)==0){
			while(itr.hasNext()) {
				period = itr.next();
				if(period.getId() == startId) {
					conti = true;
				}
				if(conti) {
					daysPeriod.add(period.getId());
				}
			}	
				
		} else if(endCal.compareTo(attenCal)==0) {
			while(itr.hasNext()) {
				period = itr.next();
				daysPeriod.add(period.getId());
				if(period.getId() == endId) {
					break;
				}	
			}	
		} else {
			while(itr.hasNext()) {
				period = itr.next();
				daysPeriod.add(period.getId());
			}	
		}
		
		
		return daysPeriod;
	}
	
	
	public Set<Integer> getCoCurricularLeaveStudents(Date attendnceDate,List<StuCocurrLeave> leaveStudentList,List<Period>periodsList,Set<Integer>newPeriodsSet) throws Exception {
		Set<Integer> leaveStudents = new HashSet<Integer>();
		Set<StuCocurrLeaveDetails> studentsLeaveDeatils = new HashSet<StuCocurrLeaveDetails>();
		Iterator<StuCocurrLeave> itr = leaveStudentList.iterator();
		StuCocurrLeave studentLeave;
		Date startDate;
		Date endDate;
		while(itr.hasNext()) {
			studentLeave = itr.next();
			startDate = studentLeave.getStartDate();
			endDate = studentLeave.getEndDate();
			Set<Integer> daysPeriod = getDaysPeriod(attendnceDate,startDate,endDate,studentLeave.getStartPeriod().getId(), studentLeave.getEndPeriod().getId(),periodsList);
			Iterator<Integer> itr1 = newPeriodsSet.iterator();
			while(itr1.hasNext()) {
				if(daysPeriod.contains(itr1.next())){
					studentsLeaveDeatils.addAll(studentLeave.getStuCocurrLeaveDetailses());
				}
			}
		}
		StuCocurrLeaveDetails LeaveDetails;
		Iterator<StuCocurrLeaveDetails> itr2 = studentsLeaveDeatils.iterator();
		while(itr2.hasNext()){
			LeaveDetails = itr2.next();
			leaveStudents.add(LeaveDetails.getStudent().getId());
		}
		
		return leaveStudents;
	}
	/**
	 * 		  This method get the constructs the attendance object from form.
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendSMS(AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("Handler : Inside getAttendanceObj");
		boolean sent=false;
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		String date =attendanceEntryForm.getAttendancedate();
		String subjectName="";
		if(attendanceEntryForm.getSubjectId()!=null && attendanceEntryForm.getSubjectId().length() != 0) {
			subjectName=transaction.getSubjectNameById(Integer.parseInt(attendanceEntryForm.getSubjectId()));
		}
		String periodNames="";
		if(attendanceEntryForm.getPeriods() != null) {	
			String [] tempArray = attendanceEntryForm.getPeriods();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			String query="select p.periodName from Period p where p.id in ("+intType+")";
			periodNames=transaction.getPeriodNamesById(query);
		}	
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_ENTRY);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_SUBJECT, subjectName);
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_PERIOD, periodNames);
		}
		AttendanceStudent attendanceStudent;
		StudentTO studentTO;
		
		Properties prop = new Properties();
		try {
			InputStream in = AttendanceEntryHelper.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		Iterator<StudentTO> itr = attendanceEntryForm.getStudentList().iterator();
		while(itr.hasNext()) {
			attendanceStudent = new AttendanceStudent();
			studentTO = itr.next();
			if(studentTO.isChecked() && !studentTO.getCoCurricularLeavePresent() && !studentTO.getStudentLeave()) {
					attendanceStudent.setIsPresent(false);
				} else {
					attendanceStudent.setIsPresent(true);
				}
				if(studentTO.getStudentLeave()) {
					attendanceStudent.setIsOnLeave(true);
				} else {
					attendanceStudent.setIsOnLeave(false);
				}	
				
				if(studentTO.getCoCurricularLeavePresent()) {
					attendanceStudent.setIsCoCurricularLeave(true);
				} else {
					attendanceStudent.setIsCoCurricularLeave(false);
				}	
				if(!attendanceStudent.getIsPresent() && !attendanceStudent.getIsOnLeave() && !attendanceStudent.getIsCoCurricularLeave()){
					if(studentTO.getMobileNo1()!=null && !studentTO.getMobileNo1().isEmpty() &&  studentTO.getMobileNo2()!=null && !studentTO.getMobileNo2().isEmpty() && studentTO.getRegisterNo()!=null){
						desc=desc.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO, studentTO.getRegisterNo());
						if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2()) && (studentTO.getMobileNo1()+studentTO.getMobileNo2()).length()==12 && desc.length()<=160){
							MobileMessaging mob=new MobileMessaging();
							mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
							mob.setMessageBody(desc);
							mob.setMessagePriority(3);
							mob.setSenderName(senderName);
							mob.setSenderNumber(senderNumber);
							mob.setMessageEnqueueDate(new Date());
							mob.setIsMessageSent(false);
							smsList.add(mob);
						}
					}
				}
		}
		PropertyUtil.getInstance().saveSMSList(smsList);
		log.info("Handler : Leaving getAttendanceObj");
		return sent;
	}
	/**
	 *  This returns the attendance object loaded from database
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendSMSUpdate(AttendanceEntryForm attendanceEntryForm) throws Exception {
		boolean flag=false;
		log.info("Handler : Inside getAttendanceObjUpdate");
		Properties prop = new Properties();
		try {
			InputStream in = AttendanceEntryHelper.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
		String date=attendanceEntryForm.getAttendancedate();
		String subjectName="";
		if(attendanceEntryForm.getSubjectId()!=null && attendanceEntryForm.getSubjectId().length() != 0) {
			subjectName=transaction.getSubjectNameById(Integer.parseInt(attendanceEntryForm.getSubjectId()));
		}
		String periodNames="";
		if(attendanceEntryForm.getPeriods() != null) {	
			String [] tempArray = attendanceEntryForm.getPeriods();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
			String query="select p.periodName from Period p where p.id in ("+intType+")";
			periodNames=transaction.getPeriodNamesById(query);
		}	
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_ENTRY);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_SUBJECT, subjectName);
			desc=desc.replace(CMSConstants.TEMPLATE_SMS_PERIOD, periodNames);
		}
		String desc1="";
		List<SMSTemplate> list1= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_CORRECTION);
		if(list1 != null && !list1.isEmpty()) {
			desc1 = list1.get(0).getTemplateDescription();
			desc1=desc1.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
			desc1=desc1.replace(CMSConstants.TEMPLATE_SMS_SUBJECT, subjectName);
			desc1=desc1.replace(CMSConstants.TEMPLATE_SMS_PERIOD, periodNames);
		}
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		StudentTO studentTO;
		Iterator<StudentTO> itr = attendanceEntryForm.getStudentList().iterator();
		while(itr.hasNext()) {
			studentTO = itr.next();
			if(!studentTO.isTempChecked() && studentTO.isChecked()) {
				desc=desc.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO, studentTO.getRegisterNo());
				if(studentTO.getMobileNo1()!=null && !studentTO.getMobileNo1().isEmpty() && studentTO.getMobileNo2()!=null && !studentTO.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2())){
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
						mob.setMessageBody(desc);
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						smsList.add(mob);
					}
				}
			} else if(studentTO.isTempChecked() && !studentTO.isChecked()){
				desc1=desc1.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO, studentTO.getRegisterNo());
				if(studentTO.getMobileNo1()!=null && !studentTO.getMobileNo1().isEmpty() && studentTO.getMobileNo2()!=null && !studentTO.getMobileNo2().isEmpty()){
					if(StringUtils.isNumeric(studentTO.getMobileNo1()+studentTO.getMobileNo2())){
						MobileMessaging mob=new MobileMessaging();
						mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
						mob.setMessageBody(desc1);
						mob.setMessagePriority(3);
						mob.setSenderName(senderName);
						mob.setSenderNumber(senderNumber);
						mob.setMessageEnqueueDate(new Date());
						mob.setIsMessageSent(false);
						smsList.add(mob);
					}
				}
			}
		}
		PropertyUtil.getInstance().saveSMSList(smsList);
		log.info("Handler : Leaving getAttendanceObjUpdate");
		return flag;
	}
	
	/**
	 * 	 	  Copy batch student BO's to studentTO's
	 * @param studentList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> copyBatchStudentBosToStudentTO(List<BatchStudent> studentList, List<Integer> listOfDetainedStudents,Set<Integer> classSet,AttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("Handler : Inside copyBatchStudentBosToStudentTO");
		List<StudentTO> list = new ArrayList<StudentTO>();
		Iterator<BatchStudent> itr = studentList.iterator();
		StudentTO studentTo;
		BatchStudent batchStudent;
		StringBuffer studentName = new StringBuffer();
		// which subject which classes added by mehaboob 
		Map<Integer, List<String>> subjectClassMap=new HashMap<Integer, List<String>>();
		//end
		while(itr.hasNext()) {
			studentTo = new StudentTO();
			batchStudent = itr.next();
			if(!listOfDetainedStudents.contains(batchStudent.getStudent().getId()) && classSet.contains(batchStudent.getClassSchemewise().getId())){
			studentTo.setId(batchStudent.getStudent().getId());
			studentTo.setRegisterNo(batchStudent.getStudent().getRegisterNo());
			studentTo.setRollNo(batchStudent.getStudent().getRollNo());
			studentTo.setChecked(false);
			studentTo.setTempChecked(false);
			studentTo.setIsTaken(false);
			studentTo.setStudentName(batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName());
			
			//added by mehaboob start
			if(batchStudent.getBatch()!=null ){
			if(batchStudent.getBatch().getSubject()!=null && batchStudent.getBatch().getSubject().getId()!=0){
			     studentTo.setSubjectId(String.valueOf(batchStudent.getBatch().getSubject().getId()));
				if(!subjectClassMap.containsKey(batchStudent.getBatch().getSubject().getId())){
					if(batchStudent.getClassSchemewise()!=null && batchStudent.getClassSchemewise().getId()!=0){
						List<String> classList=new ArrayList<String>();
						classList.add(String.valueOf(batchStudent.getClassSchemewise().getId()));
						subjectClassMap.put(batchStudent.getBatch().getSubject().getId(), classList);
					}
				}else{
					List<String> classList=subjectClassMap.get(batchStudent.getBatch().getSubject().getId());
					if(batchStudent.getClassSchemewise()!=null && batchStudent.getClassSchemewise().getId()!=0){
						if(!classList.contains(String.valueOf(batchStudent.getClassSchemewise().getId()))){
							classList.add(String.valueOf(batchStudent.getClassSchemewise().getId()));
						}
						subjectClassMap.put(batchStudent.getBatch().getSubject().getId(), classList);
					}
				}
			}
			}
			//end
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getFirstName()).append(" ");	
			} 
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getMiddleName()).append(" ");
			}
			if(batchStudent.getStudent().getAdmAppln().getPersonalData()!= null && batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName() != null) {
				studentName.append(batchStudent.getStudent().getAdmAppln().getPersonalData().getLastName()).append(" ");
			}
			studentTo.setStudentName(studentName.toString());
			list.add(studentTo);
			studentName=new StringBuffer();
		}
		}
		//added by mahi start
		if(!subjectClassMap.isEmpty()){
		attendanceEntryForm.setSubjectClassMap(subjectClassMap);
		}
		//end
		log.info("Handler : Leaving copyBatchStudentBosToStudentTO");
	    return list;	
	}
	
	public void setStudentDetailsBySubjectId(List<StudentTO> list,int subject,List<Student> studentList,List<Integer> listOfDetainedStudents,Map<Integer, List<String>> subjectClassMap) throws Exception{

		Iterator<Student> itr = studentList.iterator();
		StudentTO studentTo;
		Student student;
		StringBuffer studentName=new StringBuffer();
		while(itr.hasNext()) {
			
			studentTo = new StudentTO();
			student = itr.next();
			if(!listOfDetainedStudents.contains(student.getId())){
				Set<ApplicantSubjectGroup> subGrpList = student.getAdmAppln().getApplicantSubjectGroups();
				if(subject == 0 || checkSubjectPresentInGroup(subject,subGrpList) || (CMSConstants.ACTIVITY_SUBJECT_IDS.contains(subject))) {
					studentTo.setId(student.getId());
					studentTo.setRegisterNo(student.getRegisterNo());
					studentTo.setRollNo(student.getRollNo());
					studentTo.setChecked(false);
					studentTo.setTempChecked(false);
//					if(student.getAdmAppln().getPersonalData().getFirstName() != null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
//					studentTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
//					}
					studentTo.setIsTaken(false);
					
					//added by mehaboob start
					if(subject!=0){
					studentTo.setSubjectId(String.valueOf(subject));
						if(!subjectClassMap.containsKey(subject)){
							List<String> classList=new ArrayList<String>();
							if(student.getClassSchemewise()!=null && student.getClassSchemewise().getId()!=0){
								classList.add(String.valueOf(student.getClassSchemewise().getId()));
								subjectClassMap.put(subject, classList);
							}
						}else{
							List<String> classList=subjectClassMap.get(subject);
							if(student.getClassSchemewise()!=null && student.getClassSchemewise().getId()!=0){
								if(!classList.contains(String.valueOf(student.getClassSchemewise().getId()))){
								classList.add(String.valueOf(student.getClassSchemewise().getId()));
								}
								subjectClassMap.put(subject, classList);
							}
						}
					}
					//end
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getFirstName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getFirstName()).append(" ");
					} 
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getMiddleName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getMiddleName()).append(" ");
					}
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getLastName() != null) {
						studentName.append(student.getAdmAppln().getPersonalData().getLastName()).append(" ");
					}
					if(student.getAdmAppln().getPersonalData()!= null && student.getAdmAppln().getPersonalData().getParentMob1() != null && student.getAdmAppln().getPersonalData().getParentMob2() != null) {
						if(student.getAdmAppln().getPersonalData().getParentMob1()!=null && !student.getAdmAppln().getPersonalData().getParentMob1().isEmpty()){
							studentTo.setMobileNo1(student.getAdmAppln().getPersonalData().getParentMob1());
						}else{
							studentTo.setMobileNo1("91");
						}
						studentTo.setMobileNo2(student.getAdmAppln().getPersonalData().getParentMob2());
					}
					studentTo.setStudentName(studentName.toString());
					//studentTo.setStudentName(studentName.toString()+"("+student.getClassSchemewise().getClasses().getName()+")");
					list.add(studentTo);
					studentName = new StringBuffer();
				}	
		}
		}
	}
	
	/**
	 * @param subjectId
	 * @param attendanceEntryForm
	 * @param attendanceList
	 * @throws Exception
	 * code added by mehaboob for common subject 
	 */
	private void createAttendanceObjectBySubject(Integer subjectId,AttendanceEntryForm attendanceEntryForm,List<Attendance> attendanceList,int batchId) throws Exception {
			Attendance attendance = new Attendance();
			if(attendanceEntryForm.getTimeTableFormat()!=null &&  attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes"))
				attendance.setIsTimeTable(true);
			else
				attendance.setIsTimeTable(false);
			AttendanceType attendanceType = new AttendanceType();
			attendanceType.setId(Integer.parseInt(attendanceEntryForm.getAttendanceTypeId()));
			attendance.setAttendanceType(attendanceType);
			if(attendanceEntryForm.getHoursHeld()!=null && attendanceEntryForm.getHoursHeld().length() != 0){
				attendance.setHoursHeld(Integer.parseInt(attendanceEntryForm.getHoursHeld()));
			}
			attendance.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate()));
			
			if(attendanceEntryForm.getActivityId() != null && attendanceEntryForm.getActivityId().length()!= 0) {
				Activity activity = new Activity();
				activity.setId(Integer.parseInt(attendanceEntryForm.getActivityId()));
				attendance.setActivity(activity);
				attendance.setIsActivityAttendance(true);
			}
			
			if(subjectId!=0){
			Subject subject = new Subject();
			subject.setId(subjectId);
			attendance.setSubject(subject);
			}
			
			if(batchId!=0) {
				Batch batch = new Batch();
				batch.setId(batchId);
				attendance.setBatch(batch);
			}
			attendance.setCreatedBy(attendanceEntryForm.getUserId());
			attendance.setModifiedBy(attendanceEntryForm.getUserId());
			 Calendar cal=Calendar.getInstance();
			 String finalTime =CommonUtil.getTodayDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
			attendance.setCreatedDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
			attendance.setLastModifiedDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
			
			//code added by mahi for only which subject contains which classes start attendanceEntryForm.getSubjectClassMap()
			if(attendanceEntryForm.getSubjectClassMap()!=null && !attendanceEntryForm.getSubjectClassMap().isEmpty()){
				Set<AttendanceClass> attendanceClassSet = new HashSet<AttendanceClass>();
				AttendanceClass attendanceClass;
				ClassSchemewise classSchemewise;
				List<String> classList=attendanceEntryForm.getSubjectClassMap().get(subjectId);
				if(classList!=null && !classList.isEmpty()){
					for (String classId : classList) {
						 attendanceClass = new AttendanceClass();
							
							classSchemewise = new ClassSchemewise();
							classSchemewise.setId(Integer.parseInt(classId));
							attendanceClass.setClassSchemewise(classSchemewise);
							
							attendanceClass.setCreatedBy(attendanceEntryForm.getUserId());
							attendanceClass.setModifiedBy(attendanceEntryForm.getUserId());
							attendanceClass.setCreatedDate(new Date());
							attendanceClass.setLastModifiedDate(new Date());
							attendanceClassSet.add(attendanceClass);
					}
					attendance.setAttendanceClasses(attendanceClassSet);
				}
			}else{
				Set<AttendanceClass> attendanceClassSet = new HashSet<AttendanceClass>();
				AttendanceClass attendanceClass;
				ClassSchemewise classSchemewise;
				
				if(attendanceEntryForm.getClasses() != null) {
					for(String str :attendanceEntryForm.getClasses()) {
						if(str != null){
	                        attendanceClass = new AttendanceClass();
							
							classSchemewise = new ClassSchemewise();
							classSchemewise.setId(Integer.parseInt(str));
							attendanceClass.setClassSchemewise(classSchemewise);
							
							attendanceClass.setCreatedBy(attendanceEntryForm.getUserId());
							attendanceClass.setModifiedBy(attendanceEntryForm.getUserId());
							attendanceClass.setCreatedDate(new Date());
							attendanceClass.setLastModifiedDate(new Date());
							attendanceClassSet.add(attendanceClass);
						}
					}
				}
				attendance.setAttendanceClasses(attendanceClassSet);
			}
			//code added by mahi start
			if(attendanceEntryForm.getSubjectClassMap()!=null && !attendanceEntryForm.getSubjectClassMap().isEmpty()){
				List<String> classList=attendanceEntryForm.getSubjectClassMap().get(subjectId);
			    if(classList!=null && !classList.isEmpty()){
			    	String[] classes=classList.toArray(new String[classList.size()]);;
			    	attendanceEntryForm.setClasses(classes);
			    }
			}
			//end
			Set<AttendancePeriod> attendancePeriodSet = new HashSet<AttendancePeriod>();
			AttendancePeriod attendancePeriod;
			Period period;
			if(attendanceEntryForm.getPeriods() != null) {	
				// Code written by Balaji
				IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
				List<Integer> periodList=transaction.getAllPeriodIdsByInput(attendanceEntryForm);
				Iterator<Integer> itr=periodList.iterator();
				while (itr.hasNext()) {
					Integer str = (Integer) itr.next();
					attendancePeriod = new AttendancePeriod();
					
					period = new Period();
					period.setId(str);
					attendancePeriod.setPeriod(period);
					
					attendancePeriod.setCreatedBy(attendanceEntryForm.getUserId());
					attendancePeriod.setModifiedBy(attendanceEntryForm.getUserId());
					attendancePeriod.setCreatedDate(new Date());
					attendancePeriod.setLastModifiedDate(new Date());
					attendancePeriodSet.add(attendancePeriod);	
					
				}
				
	//			for(String str :attendanceEntryForm.getPeriods()) {
	//			}
			}
			
			attendance.setAttendancePeriods(attendancePeriodSet);
			
			Set<AttendanceInstructor> attendanceInstructorSet = new HashSet<AttendanceInstructor>();
			AttendanceInstructor attendanceInstructor;
			Users users;
			if(attendanceEntryForm.getTeachers() != null) {
				for(String str :attendanceEntryForm.getTeachers()) {
					if(str != null){
						attendanceInstructor = new AttendanceInstructor();
						users = new Users();
						users.setId(Integer.parseInt(str));
						attendanceInstructor.setUsers(users);
						attendanceInstructor.setCreatedBy(attendanceEntryForm.getUserId());
						attendanceInstructor.setModifiedBy(attendanceEntryForm.getUserId());
						attendanceInstructor.setCreatedDate(new Date());
						attendanceInstructor.setLastModifiedDate(new Date());
						attendanceInstructorSet.add(attendanceInstructor);	
					}
				}
			}
			attendance.setAttendanceInstructors(attendanceInstructorSet);
			
			Set<AttendanceStudent> studentSet = new HashSet<AttendanceStudent>();
			AttendanceStudent attendanceStudent;
			StudentTO studentTO;
			Student student;
			Iterator<StudentTO> itr = attendanceEntryForm.getStudentList().iterator();
			while(itr.hasNext()) {
				studentTO = itr.next();
                //if condition added by mehaboob subjectId
				if(subjectId!=0 && studentTO.getSubjectId()!=null && !studentTO.getSubjectId().isEmpty()){
					if(studentTO.getSubjectId().equalsIgnoreCase(String.valueOf(subjectId))){
	                	 attendanceStudent = new AttendanceStudent();
	     				if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
	     						attendanceStudent.setIsPresent(false);
	     					} else {
	     						attendanceStudent.setIsPresent(true);
	     					}
	     					student = new Student();
	     					student.setId(studentTO.getId());
	     					attendanceStudent.setStudent(student);
	     					if(studentTO.getStudentLeave()) {
	     						attendanceStudent.setIsOnLeave(true);
	     					} else {
	     						attendanceStudent.setIsOnLeave(false);
	     					}	
	     					
	     					if(studentTO.getCoCurricularLeavePresent()) {
	     						attendanceStudent.setIsCoCurricularLeave(true);
	     					} else {
	     						attendanceStudent.setIsCoCurricularLeave(false);
	     					}	
	     					
	     					attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
	     					attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
	     					attendanceStudent.setCreatedDate(new Date());
	     					attendanceStudent.setLastModifiedDate(new Date());
	     					studentSet.add(attendanceStudent);
					}
				}else{
					 attendanceStudent = new AttendanceStudent();
	     				if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
	     						attendanceStudent.setIsPresent(false);
	     					} else {
	     						attendanceStudent.setIsPresent(true);
	     					}
	     					student = new Student();
	     					student.setId(studentTO.getId());
	     					attendanceStudent.setStudent(student);
	     					if(studentTO.getStudentLeave()) {
	     						attendanceStudent.setIsOnLeave(true);
	     					} else {
	     						attendanceStudent.setIsOnLeave(false);
	     					}	
	     					
	     					if(studentTO.getCoCurricularLeavePresent()) {
	     						attendanceStudent.setIsCoCurricularLeave(true);
	     					} else {
	     						attendanceStudent.setIsCoCurricularLeave(false);
	     					}	
	     					
	     					attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
	     					attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
	     					attendanceStudent.setCreatedDate(new Date());
	     					attendanceStudent.setLastModifiedDate(new Date());
	     					studentSet.add(attendanceStudent);
				}
			
			}
			attendance.setAttendanceStudents(studentSet);
			if(attendanceEntryForm.getSlipNo()!=null && !attendanceEntryForm.getSlipNo().isEmpty())
			attendance.setSlipNo(attendanceEntryForm.getSlipNo());
			
			attendanceList.add(attendance);
	}
	
	
	//raghu write new
	private void createAttendanceObjectBySubject1(Integer subjectId,AttendanceEntryForm attendanceEntryForm,List<Attendance> attendanceList,int batchId) throws Exception {
		Attendance attendance = new Attendance();
		if(attendanceEntryForm.getTimeTableFormat()!=null &&  attendanceEntryForm.getTimeTableFormat().equalsIgnoreCase("yes"))
			attendance.setIsTimeTable(true);
		else
			attendance.setIsTimeTable(false);
		AttendanceType attendanceType = new AttendanceType();
		attendanceType.setId(Integer.parseInt(attendanceEntryForm.getAttendanceTypeId()));
		attendance.setAttendanceType(attendanceType);
		if(attendanceEntryForm.getHoursHeld()!=null && attendanceEntryForm.getHoursHeld().length() != 0){
			attendance.setHoursHeld(Integer.parseInt(attendanceEntryForm.getHoursHeld()));
		}
		attendance.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(attendanceEntryForm.getAttendancedate()));
		if (attendanceEntryForm.getDutyType()!=null && (attendanceEntryForm.getDutyType()!="other" && !attendanceEntryForm.getDutyType().equalsIgnoreCase("other"))) {
			attendance.setDutyType(attendanceEntryForm.getDutyType());
		}else if(attendanceEntryForm.getDutyType()=="other" || attendanceEntryForm.getDutyType().equalsIgnoreCase("other") && attendanceEntryForm.getDutyTypeOther()!=null)
			attendance.setDutyType(attendanceEntryForm.getDutyTypeOther());
		if (attendanceEntryForm.getClassType()!=null) {
			attendance.setClassType(attendanceEntryForm.getClassType());
		}
		if (attendanceEntryForm.getPlatform()!=null && (attendanceEntryForm.getPlatform()!="other" & !attendanceEntryForm.getPlatform().equalsIgnoreCase("other"))) {
			attendance.setPlatform(attendanceEntryForm.getPlatform());
		}else if(attendanceEntryForm.getDutyType()=="other" || attendanceEntryForm.getPlatform().equalsIgnoreCase("other") && attendanceEntryForm.getPlatformOther()!=null)
			attendance.setPlatform(attendanceEntryForm.getPlatformOther());
		if (attendanceEntryForm.getTopic()!=null) {
			attendance.setTopic(attendanceEntryForm.getTopic());
		}
		if(attendanceEntryForm.getActivityId() != null && attendanceEntryForm.getActivityId().length()!= 0) {
			Activity activity = new Activity();
			activity.setId(Integer.parseInt(attendanceEntryForm.getActivityId()));
			attendance.setActivity(activity);
			attendance.setIsActivityAttendance(true);
		}
		
		if(subjectId!=0){
		Subject subject = new Subject();
		subject.setId(subjectId);
		attendance.setSubject(subject);
		}
		
		if(batchId!=0) {
			Batch batch = new Batch();
			batch.setId(batchId);
			attendance.setBatch(batch);
		}
		attendance.setCreatedBy(attendanceEntryForm.getUserId());
		attendance.setModifiedBy(attendanceEntryForm.getUserId());
		 Calendar cal=Calendar.getInstance();
		 String finalTime =CommonUtil.getTodayDate()+" "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+":"+cal.get(Calendar.SECOND);
		attendance.setCreatedDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
		attendance.setLastModifiedDate(CommonUtil.ConvertStringToSQLDateTime(finalTime));
		
		//code added by mahi for only which subject contains which classes start attendanceEntryForm.getSubjectClassMap()
		if(attendanceEntryForm.getSubjectClassMap()!=null && !attendanceEntryForm.getSubjectClassMap().isEmpty()){
			Set<AttendanceClass> attendanceClassSet = new HashSet<AttendanceClass>();
			AttendanceClass attendanceClass;
			ClassSchemewise classSchemewise;
			List<String> classList=attendanceEntryForm.getSubjectClassMap().get(subjectId);
			if(classList!=null && !classList.isEmpty()){
				for (String classId : classList) {
					 attendanceClass = new AttendanceClass();
						
						classSchemewise = new ClassSchemewise();
						classSchemewise.setId(Integer.parseInt(classId));
						attendanceClass.setClassSchemewise(classSchemewise);
						
						attendanceClass.setCreatedBy(attendanceEntryForm.getUserId());
						attendanceClass.setModifiedBy(attendanceEntryForm.getUserId());
						attendanceClass.setCreatedDate(new Date());
						attendanceClass.setLastModifiedDate(new Date());
						attendanceClassSet.add(attendanceClass);
				}
				attendance.setAttendanceClasses(attendanceClassSet);
			}
		}else{
			Set<AttendanceClass> attendanceClassSet = new HashSet<AttendanceClass>();
			AttendanceClass attendanceClass;
			ClassSchemewise classSchemewise;
			
			if(attendanceEntryForm.getClasses() != null) {
				for(String str :attendanceEntryForm.getClasses()) {
					if(str != null){
                        attendanceClass = new AttendanceClass();
						
						classSchemewise = new ClassSchemewise();
						classSchemewise.setId(Integer.parseInt(str));
						attendanceClass.setClassSchemewise(classSchemewise);
						
						attendanceClass.setCreatedBy(attendanceEntryForm.getUserId());
						attendanceClass.setModifiedBy(attendanceEntryForm.getUserId());
						attendanceClass.setCreatedDate(new Date());
						attendanceClass.setLastModifiedDate(new Date());
						attendanceClassSet.add(attendanceClass);
					}
				}
			}
			attendance.setAttendanceClasses(attendanceClassSet);
		}
		//code added by mahi start
		if(attendanceEntryForm.getSubjectClassMap()!=null && !attendanceEntryForm.getSubjectClassMap().isEmpty()){
			List<String> classList=attendanceEntryForm.getSubjectClassMap().get(subjectId);
		    if(classList!=null && !classList.isEmpty()){
		    	String[] classes=classList.toArray(new String[classList.size()]);;
		    	attendanceEntryForm.setClasses(classes);
		    }
		}
		//end
		Set<AttendancePeriod> attendancePeriodSet = new HashSet<AttendancePeriod>();
		AttendancePeriod attendancePeriod;
		Period period;
		if(attendanceEntryForm.getPeriods() != null) {	
			// Code written by Balaji
			IAttendanceEntryTransaction transaction=new AttendanceEntryTransactionImpl();
			List<Integer> periodList=transaction.getAllPeriodIdsByInput(attendanceEntryForm);
			Iterator<Integer> itr=periodList.iterator();
			while (itr.hasNext()) {
				Integer str = (Integer) itr.next();
				attendancePeriod = new AttendancePeriod();
				
				period = new Period();
				period.setId(str);
				attendancePeriod.setPeriod(period);
				
				attendancePeriod.setCreatedBy(attendanceEntryForm.getUserId());
				attendancePeriod.setModifiedBy(attendanceEntryForm.getUserId());
				attendancePeriod.setCreatedDate(new Date());
				attendancePeriod.setLastModifiedDate(new Date());
				attendancePeriodSet.add(attendancePeriod);	
				
			}
			
//			for(String str :attendanceEntryForm.getPeriods()) {
//			}
		}
		
		attendance.setAttendancePeriods(attendancePeriodSet);
		
		if (attendanceEntryForm.getAttendanceTypeId() =="2" || attendanceEntryForm.getAttendanceTypeId().equals("2")) {
			if (attendanceEntryForm.getCoAtteachers()!=null) {
			String[] tempArray=new String[attendanceEntryForm.getCoAtteachers().length+attendanceEntryForm.getTeachers().length];
			int i=0;
			for(String str :attendanceEntryForm.getCoAtteachers()){
				if (str!=null) {
					tempArray[i]=str;
					i++;
				}
				
			}
			for(String str :attendanceEntryForm.getTeachers()){
				if (str!=null) {
					System.out.println(attendanceEntryForm.getUserId());
					tempArray[i]=str;
					i++;
				}
			}
			attendanceEntryForm.setTeachers(tempArray);
		}
		}
		Set<AttendanceInstructor> attendanceInstructorSet = new HashSet<AttendanceInstructor>();
		AttendanceInstructor attendanceInstructor;
		Users users;
		if(attendanceEntryForm.getTeachers() != null) {
			for(String str :attendanceEntryForm.getTeachers()) {
				if(str != null){
					attendanceInstructor = new AttendanceInstructor();
					users = new Users();
					users.setId(Integer.parseInt(str));
					attendanceInstructor.setUsers(users);
					attendanceInstructor.setCreatedBy(attendanceEntryForm.getUserId());
					attendanceInstructor.setModifiedBy(attendanceEntryForm.getUserId());
					attendanceInstructor.setCreatedDate(new Date());
					attendanceInstructor.setLastModifiedDate(new Date());
					attendanceInstructorSet.add(attendanceInstructor);	
				}
			}
		}
		attendance.setAttendanceInstructors(attendanceInstructorSet);
		
		Set<AttendanceStudent> studentSet = new HashSet<AttendanceStudent>();
		AttendanceStudent attendanceStudent;
		StudentTO studentTO;
		Student student;
		List<StudentTO> studentTOList;
		
		Iterator<List<StudentTO>> itr1 = attendanceEntryForm.getStudentsList().iterator();
		while(itr1.hasNext()) {
		
		Iterator<StudentTO> itr = itr1.next().iterator();
		while(itr.hasNext()) {
			studentTO = itr.next();
            if(studentTO.getIsCurrent() && !studentTO.getIsTaken())
			if(subjectId!=0 && studentTO.getSubjectId()!=null && !studentTO.getSubjectId().isEmpty()){
				if(studentTO.getSubjectId().equalsIgnoreCase(String.valueOf(subjectId))){
                	 attendanceStudent = new AttendanceStudent();
     				if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
     						attendanceStudent.setIsPresent(false);
     					} else {
     						attendanceStudent.setIsPresent(true);
     					}
     					student = new Student();
     					student.setId(studentTO.getId());
     					attendanceStudent.setStudent(student);
     					if(studentTO.getStudentLeave()) {
     						attendanceStudent.setIsOnLeave(true);
     					} else {
     						attendanceStudent.setIsOnLeave(false);
     					}	
     					
     					if(studentTO.getCoCurricularLeavePresent()) {
     						attendanceStudent.setIsCoCurricularLeave(true);
     					} else {
     						attendanceStudent.setIsCoCurricularLeave(false);
     					}	
     					
     					attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
     					attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
     					attendanceStudent.setCreatedDate(new Date());
     					attendanceStudent.setLastModifiedDate(new Date());
     					//sms
     					attendanceStudent.setIsSmsSent(false);
     					studentSet.add(attendanceStudent);
				}
			}else{
				 attendanceStudent = new AttendanceStudent();
     				if(studentTO.isChecked() || studentTO.getCoCurricularLeavePresent()||studentTO.getStudentLeave()) {
     						attendanceStudent.setIsPresent(false);
     					} else {
     						attendanceStudent.setIsPresent(true);
     					}
     					student = new Student();
     					student.setId(studentTO.getId());
     					attendanceStudent.setStudent(student);
     					if(studentTO.getStudentLeave()) {
     						attendanceStudent.setIsOnLeave(true);
     					} else {
     						attendanceStudent.setIsOnLeave(false);
     					}	
     					
     					if(studentTO.getCoCurricularLeavePresent()) {
     						attendanceStudent.setIsCoCurricularLeave(true);
     					} else {
     						attendanceStudent.setIsCoCurricularLeave(false);
     					}	
     					
     					attendanceStudent.setCreatedBy(attendanceEntryForm.getUserId());
     					attendanceStudent.setModifiedBy(attendanceEntryForm.getUserId());
     					attendanceStudent.setCreatedDate(new Date());
     					attendanceStudent.setLastModifiedDate(new Date());
     					//sms
     					attendanceStudent.setIsSmsSent(false);
     					studentSet.add(attendanceStudent);
			}
		
		}
		
		}
		
		attendance.setAttendanceStudents(studentSet);
		if(attendanceEntryForm.getSlipNo()!=null && !attendanceEntryForm.getSlipNo().isEmpty())
		attendance.setSlipNo(attendanceEntryForm.getSlipNo());
		
		attendanceList.add(attendance);
}
	
	public List<Attendance> getAttendanceObjActiveSubject(AttendanceEntryForm attendanceEntryForm,HttpSession session) throws Exception {
		log.info("Handler : Inside getAttendanceObj");
		List<Attendance> attendanceList=new ArrayList<Attendance>();
		//code changed by mehaboob for combined class start added 
		if(attendanceEntryForm.getSubjectId()!=null && !attendanceEntryForm.getSubjectId().isEmpty()) {
			Map<Integer, List<Integer>> subjectBatchMap= (Map<Integer, List<Integer>>) session.getAttribute("SubjectBatchMap");
			Map<String, List<Integer>> subjectCodeGroupMap= (Map<String, List<Integer>>) session.getAttribute("SubjectCodeGroupMap");
			if(subjectCodeGroupMap!=null && !subjectCodeGroupMap.isEmpty()){
				if(subjectCodeGroupMap.containsKey(attendanceEntryForm.getSubjectId())){
					List<Integer> subjectIdList=subjectCodeGroupMap.get(attendanceEntryForm.getSubjectId());
					for (Integer integer : subjectIdList) {
						int batchId=0;
						if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
							batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
							createAttendanceObjectBySubject(integer,attendanceEntryForm,attendanceList,batchId);
						}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
							batchId=attendanceEntryForm.getSubjectBatchMap().get(integer);
							createAttendanceObjectBySubject(integer,attendanceEntryForm,attendanceList,batchId);
						}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
							for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
								if(batchIds!=null && !batchIds.isEmpty()){
									if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
										List<Integer> batchidList=subjectBatchMap.get(integer);
										if(batchidList!=null && !batchidList.isEmpty()){
											for (Integer batchIdFromMap : batchidList) {
												if(batchIdFromMap==Integer.parseInt(batchIds)){
													createAttendanceObjectBySubject(integer,attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
												}
											}
										}
									}
								}else if(attendanceEntryForm.getBatchIdsArray().length==1){
									createAttendanceObjectBySubject(integer,attendanceEntryForm,attendanceList,0);
								}
							}
						}else{
							createAttendanceObjectBySubject(integer,attendanceEntryForm,attendanceList,batchId);
						}
					}

				}else{
					int batchId=0;
					if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
						batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
						createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
					}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
						batchId=attendanceEntryForm.getSubjectBatchMap().get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
						createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
					}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
						for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
							if(batchIds!=null && !batchIds.isEmpty()){
								if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
									List<Integer> batchidList=subjectBatchMap.get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
									for (Integer batchIdFromMap : batchidList) {
										if(batchIdFromMap==Integer.parseInt(batchIds)){
											createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
										}
									}
								}
							}else if(attendanceEntryForm.getBatchIdsArray().length==1){
								createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,0);
							}
						}
					}else{
						createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
					}
				}
			}else{
				int batchId=0;
				if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
					batchId=Integer.parseInt(attendanceEntryForm.getBatchId());
					createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
				}else if(attendanceEntryForm.getSubjectBatchMap()!=null && !attendanceEntryForm.getSubjectBatchMap().isEmpty()){
					batchId=attendanceEntryForm.getSubjectBatchMap().get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
					createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
				}else if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
					for (String batchIds : attendanceEntryForm.getBatchIdsArray()) {
						if(batchIds!=null && !batchIds.isEmpty()){
							if(subjectBatchMap!=null && !subjectBatchMap.isEmpty()){
								List<Integer> batchidList=subjectBatchMap.get(Integer.parseInt(attendanceEntryForm.getSubjectId()));
								for (Integer batchIdFromMap : batchidList) {
									if(batchIdFromMap==Integer.parseInt(batchIds)){
										createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,Integer.parseInt(batchIds));
									}
								}
							}
						}else if(attendanceEntryForm.getBatchIdsArray().length==1){
							createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,0);
						}
					}
				}else{
					createAttendanceObjectBySubject(Integer.parseInt(attendanceEntryForm.getSubjectId()),attendanceEntryForm,attendanceList,batchId);
				}
			}

		}else{
			if(attendanceEntryForm.getBatchIdsArray()!=null && !attendanceEntryForm.getBatchIdsArray().toString().isEmpty()){
				for (String batchId : attendanceEntryForm.getBatchIdsArray()) {
					if(batchId!=null && !batchId.isEmpty()){
						createAttendanceObjectBySubject(0,attendanceEntryForm,attendanceList,Integer.parseInt(batchId));
					}else if(attendanceEntryForm.getBatchIdsArray().length==1){
						createAttendanceObjectBySubject(0,attendanceEntryForm,attendanceList,0);
					}
				}
			}else if(attendanceEntryForm.getBatchId()!=null && !attendanceEntryForm.getBatchId().isEmpty()){
				createAttendanceObjectBySubject(0,attendanceEntryForm,attendanceList,Integer.parseInt(attendanceEntryForm.getBatchId()));
			}else{
				createAttendanceObjectBySubject(0,attendanceEntryForm,attendanceList,0);
			}

		}
		//end
		log.info("Handler : Leaving getAttendanceObj");
		return attendanceList;
	}

	public String createQueryForCoTeachersForAtendance(AttendanceEntryForm attendanceEntryForm) {
		String query=null;
		String[] classesId=null;
		String[] periodId=null;
		String periods=null;
		classesId=attendanceEntryForm.getClasses();
		periodId=attendanceEntryForm.getPeriods();
		StringBuffer periodIds=new StringBuffer("('");
		for (String period :periodId) {
			periodIds.append(period+"','");
		}
		periodIds.deleteCharAt(periodIds.length()-1);
		periodIds.deleteCharAt(periodIds.length()-1);
		periodIds.append(")");
		periods=periodIds.toString();
		query="from TTUsers tt where tt.ttSubjectBatch.subject.id="+attendanceEntryForm.getSubjectId()+" and tt.ttSubjectBatch.ttPeriodWeek.ttClasses.classSchemewise.id="+classesId[0]+" and tt.ttSubjectBatch.ttPeriodWeek.period.id in"+periods+" and tt.users.id not in('"+attendanceEntryForm.getUserId()+"') and tt.ttSubjectBatch.subject.isTheoryPractical='P'";
		
		return query;
		
	}
	
	public void convertCoBoTo(List<TTUsers> coTeachers, AttendanceEntryForm attendanceEntryForm) {
		Map<Integer, String> attCoteachers=new HashMap<Integer, String>();
		for (TTUsers batchIdFromMap : coTeachers) {
			System.out.println(batchIdFromMap.getUsers().getId()+"____"+batchIdFromMap.getUsers().getUserName());
			attCoteachers.put(batchIdFromMap.getUsers().getId(), batchIdFromMap.getUsers().getUserName());
		}
		attendanceEntryForm.setAttCoTeachers(attCoteachers);
		
	}

	
}
