package com.kp.cms.helpers.attendance;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentRemarks;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamMarksEntryDetailsBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsSubInternalBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.admission.DisciplinaryDetailsForm;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.admission.CurriculumSchemeHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admission.SessionAttendnceToAm;
import com.kp.cms.to.admission.SessionAttendnceToPm;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.reports.IStudentWiseAttendanceSummaryTransaction;
import com.kp.cms.transactionsimpl.reports.StudentWiseAttendanceSummaryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class StudentAttendanceSummaryHelper {
	private static final Log log = LogFactory.getLog(StudentAttendanceSummaryHelper.class);
	private static volatile StudentAttendanceSummaryHelper studentAttendanceSummaryHelper = null;
	
	private StudentAttendanceSummaryHelper() {
		
	}
	
	public static StudentAttendanceSummaryHelper getInstance() {
		if(studentAttendanceSummaryHelper == null) {
			studentAttendanceSummaryHelper = new StudentAttendanceSummaryHelper();
		}
		return studentAttendanceSummaryHelper;
	}

	/**
	 * Constructs the search query for student wise attendance summary information summary for conducted classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQueryForConducted(int studentId,int classId) {
		log.info("Entering into getAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "	select attendanceStudents.attendance.subject.id , sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.attendance.subject.name  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.id = " + studentId+" and ac.classSchemewise.classes.id="+classId				
				+ "  group by attendanceStudents.attendance.subject.id ";
	log.info("Leaving into getAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	/**
	 * Constructs the search query for student wise attendance summary information summary for conducted classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getStudentAbsenceInformationSummaryQueryForConducted(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
		int acadamicYear = currBo.getAcademicYear();
		int termNo =currBo.getSemesterYearNo();
		String studentWiseAttendanceSummary = "";
		if(acadamicYear>2017){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when attendanceStudents.attendance.hoursHeld is not null then attendanceStudents.attendance.hoursHeld else 0 end)" 
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				//mary code
				//+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 0  " //mary code end
		/*		+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else if(acadamicYear==2017 && (termNo!=1 && termNo!=3 && termNo!=5)){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when attendanceStudents.attendance.hoursHeld is not null then attendanceStudents.attendance.hoursHeld else 0 end)" 
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				//mary code
				//+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 0  " //mary code end
		/*		+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else{
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when attendanceStudents.attendance.hoursHeld is not null then 1 else 0 end)" 
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				//mary code
				//+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 0  " //mary code end
		/*		+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";

		}
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	
	/**
	 * Constructs the search query for student wise attendance summary information summary for present classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQueryForPresent(int studentId,int classId) {
		log.info("Entering into getAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "	select attendanceStudents.attendance.subject.id,  sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.attendance.subject.name  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 "
				+ " and attendanceStudents.isPresent = 1 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.id = " +studentId+" and ac.classSchemewise.classes.id="+classId
				+ "  group by attendanceStudents.attendance.subject.id ";
		
		log.info("Leaving into getAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}
	/**
	 * Constructs the search query for student wise attendance summary information summary for present classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getStudentAbsenceInformationSummaryQueryForPresent(int studentId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
			+ "attendanceStudents.attendance.subject.name, " 
			+ "attendanceStudents.attendance.attendanceType.id, " 
			+ "attendanceStudents.attendance.attendanceType.name, " 
			+ "attendanceStudents.attendance.id, " 
			+ "attendanceStudents.student.id, " 
			+ "sum(attendanceStudents.attendance.hoursHeld)" 
			+ "from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			/*+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
			//mary code
			//+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 0  " //mary code end
			+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
			+ "and  attendanceStudents.attendance.isCanceled = 0 and "
			+ "attendanceStudents.isPresent = 1 and "
			+ "student.id = " + studentId
			+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
			+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}
	
	public Map<Integer, StudentWiseSubjectSummaryTO> convertListToMap(
			List subjectWiseAttendanceSummaryList, boolean isPresent) {
		log.info("Entering into convertListToMap of StudentAttendanceSummaryHelper");
		Map<Integer, StudentWiseSubjectSummaryTO> subjectWiseAttendanceSummaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();

		if (subjectWiseAttendanceSummaryList != null) {
			Iterator subjectWiseAttendanceSummaryIterator = subjectWiseAttendanceSummaryList
					.iterator();
			while (subjectWiseAttendanceSummaryIterator.hasNext()) {
				Object[] object = (Object[]) subjectWiseAttendanceSummaryIterator
						.next();
				StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
				if (isPresent) {
					studentWiseSubjectSummaryTO
							.setClassesPresent(((Long) object[1]).intValue());
				} else {
					studentWiseSubjectSummaryTO
							.setConductedClasses(((Long) object[1]).intValue());
				}
				if (object[2] != null) {
					studentWiseSubjectSummaryTO.setSubjectName(object[2]
							.toString());
				}
				subjectWiseAttendanceSummaryMap.put((Integer) object[0],
						studentWiseSubjectSummaryTO);

			}
		}
		log.info("Leaving into convertListToMap of StudentAttendanceSummaryHelper");
		return subjectWiseAttendanceSummaryMap;
	}
	
	public List<StudentWiseSubjectSummaryTO> convertFromMapToList(Map<Integer, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<Integer, StudentWiseSubjectSummaryTO> classesPresentMap, Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeaveMap, Map<Integer, StudentWiseSubjectSummaryTO> classesOnCocurricularLeaveMap) {
		log.info("Entering into convertFromMapToList of StudentAttendanceSummaryHelper");
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		Iterator<Integer> subjectIdIterator = classesConductedMap.keySet()
				.iterator();
		while (subjectIdIterator.hasNext()) {
			Integer subjectId = (Integer) subjectIdIterator.next();
			StudentWiseSubjectSummaryTO studentWiseSubjectSummary = classesConductedMap
					.get(subjectId);
			if(subjectId!= null ){
				studentWiseSubjectSummary.setSubjectID(subjectId.toString());
			}
			if (classesPresentMap.containsKey(subjectId) && classesOnLeaveMap.containsKey(subjectId)) {
				studentWiseSubjectSummary.setClassesPresent(classesPresentMap
						.get(subjectId).getClassesPresent());
				studentWiseSubjectSummary
						.setClassesAbsent(studentWiseSubjectSummary
								.getConductedClasses()
								- studentWiseSubjectSummary.getClassesPresent());
				
				studentWiseSubjectSummary.setLeaveApproved(classesOnLeaveMap
						.get(subjectId).getLeaveApproved());
			}
			else if (classesPresentMap.containsKey(subjectId) && !classesOnLeaveMap.containsKey(subjectId)) {
				studentWiseSubjectSummary.setClassesPresent(classesPresentMap
						.get(subjectId).getClassesPresent());
				studentWiseSubjectSummary
						.setClassesAbsent(studentWiseSubjectSummary
								.getConductedClasses()
								- studentWiseSubjectSummary.getClassesPresent());
				
				studentWiseSubjectSummary.setLeaveApproved(0);
			} 
			else {
				studentWiseSubjectSummary.setClassesPresent(0);
				studentWiseSubjectSummary
						.setClassesAbsent(studentWiseSubjectSummary
								.getConductedClasses());
				if(classesOnLeaveMap.containsKey(subjectId)){
					studentWiseSubjectSummary.setLeaveApproved(classesOnLeaveMap
							.get(subjectId).getLeaveApproved());
				}
				else{
					studentWiseSubjectSummary.setLeaveApproved(0);
				}
			}
			
			//Satish Patruni
			if(classesOnCocurricularLeaveMap.containsKey(subjectId) && classesOnCocurricularLeaveMap.get(subjectId) != null){
				studentWiseSubjectSummary.setCocurricularLeave(classesOnCocurricularLeaveMap
							.get(subjectId).getCocurricularLeave());
			}else{
				studentWiseSubjectSummary.setCocurricularLeave(0);
			}			
			float percentageWithLeave = ((studentWiseSubjectSummary.getClassesPresent() + studentWiseSubjectSummary.getLeaveApproved()) * 100)
			/studentWiseSubjectSummary.getConductedClasses();
			
			
			
			//Satish Patruni			
			float percentageWithCocurricularLeave = ((studentWiseSubjectSummary.getClassesPresent() + studentWiseSubjectSummary.getCocurricularLeave()) * 100)
			/studentWiseSubjectSummary.getConductedClasses();
			studentWiseSubjectSummary.setPercentageWithCocurricularLeave(percentageWithCocurricularLeave);
			
			
			float totalPercentage = ((studentWiseSubjectSummary.getClassesPresent() + 
			studentWiseSubjectSummary.getLeaveApproved() + studentWiseSubjectSummary.getCocurricularLeave()) * 100)
			/studentWiseSubjectSummary.getConductedClasses();
			
			log.info("percentageWithCocurricularLeave: " + percentageWithCocurricularLeave);
			float percentageWithoutLeave = (studentWiseSubjectSummary.getClassesPresent() * 100)/studentWiseSubjectSummary.getConductedClasses();
			studentWiseSubjectSummary.setPercentageWithoutLeave(percentageWithoutLeave);
			studentWiseSubjectSummary.setPercentageWithLeave(percentageWithLeave);
			studentWiseSubjectSummary.setTotalAttPercentage(totalPercentage);
			
			studentWiseSubjectSummaryTOList.add(studentWiseSubjectSummary);
		}
		log.info("Leaving into convertFromMapToList of StudentAttendanceSummaryHelper");
		return studentWiseSubjectSummaryTOList;
	}

	private static String buildDynamicQuery(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into buildDynamicQuery of StudentAttendanceSummaryHelper");
		String searchCriteria = "";
		boolean containSearchCriteria = false;
		if (attendanceSummaryForm.getStartRegisterNo().trim().length() > 0) {
			containSearchCriteria=true;
			String regNo = "  st.registerNo = '"
					+ attendanceSummaryForm.getStartRegisterNo()+"'";
			searchCriteria = searchCriteria + regNo;
		}

		if (attendanceSummaryForm.getStartRollNo().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String rollNo = "  st.rollNo = '"
					+ attendanceSummaryForm.getStartRollNo()+"'";
			searchCriteria = searchCriteria + rollNo;
		}

		if (attendanceSummaryForm.getStudentName().trim().length() > 0) {
			if(containSearchCriteria) {
				searchCriteria = searchCriteria + "  and  ";
			}
			containSearchCriteria=true;
			String firstName = "  st.admAppln.personalData.firstName like '%"
					+ attendanceSummaryForm.getStudentName() + "%'";
			String middleName = " or st.admAppln.personalData.middleName like '%"
				+ attendanceSummaryForm.getStudentName() + "%'";

			String lastName = " or st.admAppln.personalData.lastName like '%"
				+ attendanceSummaryForm.getStudentName() + "%'";
			
			searchCriteria = searchCriteria + firstName + middleName + lastName;
		}
		searchCriteria = searchCriteria + " and st.isActive = 1";
		log.info("Leaving into buildDynamicQuery of StudentAttendanceSummaryHelper");
		return searchCriteria;
	}
	
	public static String getSelectionSearchCriteria(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getSelectionSearchCriteria of StudentAttendanceSummaryHelper");
		String statusCriteria = buildDynamicQuery(attendanceSummaryForm);
		String searchCriteria= "";
		searchCriteria =  " select st.admAppln.personalData.firstName, st.admAppln.personalData.middleName, st.admAppln.personalData.lastName, st.registerNo, st.rollNo,"+
			" st.id, st.admAppln.appliedYear, st.admAppln.applnNo, st.admAppln.courseBySelectedCourseId.id from Student st " 
			+ " where " + statusCriteria;					
		log.info("Leaving into getSelectionSearchCriteria of StudentAttendanceSummaryHelper");
		return searchCriteria;
	}

	public static List<StudentTO> populateTOList(List<Object[]> studentList) {
		log.info("Entering into populateTOList of StudentAttendanceSummaryHelper");
		List<StudentTO> studentTOList = new ArrayList<StudentTO>();
		Iterator<Object[]> itrStudent = studentList.iterator();
		while (itrStudent.hasNext()) {
			Object[] objects = (Object[]) itrStudent.next();
			if(objects!=null){
				String firstName = "";
				String middleName = "";
				String lastName = "";
				String regNo = "";
				String rollNo = "";
				Integer studentID = 0;
				int appliedYear = 0;
				int applnNo = 0;
				int courseId = 0;
				
				StudentTO studentTO = new StudentTO();
				if(objects[0]!=null){
				firstName = (String)objects[0];				
				}
				if(objects[1]!=null){					
					middleName = (String)objects[1];
					firstName = firstName +" "+ middleName;  
				}
				if(objects[2]!=null){
					lastName = (String)objects[2];
					firstName = firstName +" "+ lastName; 
				}
				studentTO.setStudentName(firstName);
				if(objects[3]!=null){
					regNo = (String)objects[3];
					studentTO.setRegisterNo(regNo);
				}				
				if(objects[4]!=null){
					rollNo = (String)objects[4];
					studentTO.setRollNo(rollNo);
				}	
				if(objects[5]!=null){
					studentID = (Integer)objects[5];
					studentTO.setId(studentID);
				}	
				if(objects[6]!=null){
					appliedYear = (Integer)objects[6];
					studentTO.setAppliedYear(appliedYear);
				}					
				if(objects[7]!=null){
					applnNo = (Integer)objects[7];
					studentTO.setApplicationNo(applnNo);
				}					
				if(objects[8]!=null){
					courseId = (Integer)objects[8];
					studentTO.setCourseId(courseId);
				}					
				
				studentTOList.add(studentTO);
			}
		}
		log.info("Leaving into populateTOList of StudentAttendanceSummaryHelper");
		return studentTOList;
	}

	/**
	 * 
	 * @param classesConductedList
	 * @return the map containing key as subjectId and attendanceType ID and
	 * values as StudentWiseSubjectSummaryTO (Containing informations)
	 */
	public Map<String, StudentWiseSubjectSummaryTO> convertAttendanceSummaryListToMap(
			List subjectWiseAttendanceSummaryList) {
		log.info("Entering into convertAttendanceSummaryListToMap of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> subjectWiseAttendanceSummaryMap = new LinkedHashMap<String, StudentWiseSubjectSummaryTO>();
		StringBuffer sb = null;
		
		if (subjectWiseAttendanceSummaryList != null) {
			Iterator subjectWiseAttendanceSummaryIterator = subjectWiseAttendanceSummaryList
					.iterator();
			while (subjectWiseAttendanceSummaryIterator.hasNext()) {
				Object[] object = (Object[]) subjectWiseAttendanceSummaryIterator
						.next();
				StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
				sb = new StringBuffer();
				
					if(object[0] != null){
						studentWiseSubjectSummaryTO
								.setSubjectID(((Integer) object[0]).toString());
						sb.append((Integer) object[0]).toString();
					}
					if(object[1] != null){
						studentWiseSubjectSummaryTO
						.setSubjectName(((String) object[1]));						
					}
					if(object[2] != null){
						studentWiseSubjectSummaryTO
						.setAttendanceTypeID(((Integer) object[2]).toString());
						sb.append((Integer) object[2]).toString();
					}
					if(object[3] != null){
						studentWiseSubjectSummaryTO
						.setAttendanceTypeName(((String) object[3]));
					}
					if(object[4] != null){
						studentWiseSubjectSummaryTO
						.setAttendanceID(((Integer) object[4]).toString());
					}
					if(object[5] != null){
						studentWiseSubjectSummaryTO
						.setStudentId(((Integer) object[5]).toString());
					}
					if(object[6] != null){
						studentWiseSubjectSummaryTO
								.setConductedClasses(((Long) object[6]).intValue());
					}
					studentWiseSubjectSummaryTO.setClassesPresent(0);
					studentWiseSubjectSummaryTO.setClassesAbsent(0);
					studentWiseSubjectSummaryTO.setPercentageWithoutLeave(0);
				
				subjectWiseAttendanceSummaryMap.put(sb.toString(),studentWiseSubjectSummaryTO);
			}
		}
		log.info("Leaving into convertAttendanceSummaryListToMap of StudentAttendanceSummaryHelper");
		return subjectWiseAttendanceSummaryMap;
	}
	
	/**
	 * 
	 * @param classesConductedMap
	 * @param classesPresentMap
	 * @param courseId 
	 * @param mode 
	 * @param attendanceSummaryForm 
	 * @param studentId 
	 * @returns the final list contatining attendance informations of the student
	 */
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToList(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap, int studentId, StudentWiseAttendanceSummaryForm attendanceSummaryForm, String mode, String courseId) {
		log.info("Entering into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
				if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){

					typeList = new ArrayList<AttendanceTypeTO>();					
					summaryTO = new StudentWiseSubjectSummaryTO();
					
					if(conductedSubjectsummaryTO.getSubjectName()!=null){
						summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
					}
					attendanceTypeTO = new AttendanceTypeTO();
					
					attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
					attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
					attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
					attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
					
					if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
						attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
					}
					attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
					
					attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
					attendanceTypeTO.setClassesAbsent(
						conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
					
					float percentage = (attendanceTypeTO.getClassesPresent() * 100)/attendanceTypeTO.getConductedClasses();
					attendanceTypeTO.setPercentage(percentage);
					
					typeList.add(attendanceTypeTO);
					summaryTO.setAttendanceTypeList(typeList);
					summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
				}
				
				else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
					
					summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
					typeList = summaryTO.getAttendanceTypeList();
					
					attendanceTypeTO = new AttendanceTypeTO();
					
					attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
					attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
					attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
					attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
					
					if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
						attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
					}
					attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
					
					attendanceTypeTO.setClassesPresent(0);
					attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
					
					float percentage = (attendanceTypeTO.getClassesPresent() * 100)/attendanceTypeTO.getConductedClasses();
					attendanceTypeTO.setPercentage(percentage);
					
					typeList.add(attendanceTypeTO);
					summaryTO.setAttendanceTypeList(typeList);
				}
				else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
					
					summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
					typeList = summaryTO.getAttendanceTypeList();
					
					attendanceTypeTO = new AttendanceTypeTO();
					
					attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
					attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
					attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
					attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
					
					if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
						attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
					}
					attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());

					attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
					attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
					presentSubjectsummaryTO.getConductedClasses());
					
					float percentage = (attendanceTypeTO.getClassesPresent() * 100)/attendanceTypeTO.getConductedClasses();
					attendanceTypeTO.setPercentage(percentage);
					
					typeList.add(attendanceTypeTO);
					summaryTO.setAttendanceTypeList(typeList);
				}
			}
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		log.info("Leaving into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		return studentWiseSubjectSummaryTOList;
	}

	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns search criteria for student absence period details
	 */
/*	public String getAbsenceSearchCriteria(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		String absenceSearchCriteria = "from AttendanceStudent attendancestudent " 
		+ "where attendancestudent.attendance.attendanceType.id = " + Integer.parseInt(attendanceSummaryForm.getAttendanceTypeId()) 
		+ "and attendancestudent.attendance.subject.id = " + Integer.parseInt(attendanceSummaryForm.getSubjectId())
		+ "and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID()) 
		+ "and attendancestudent.isPresent = 0 order by attendancestudent.attendance.attendanceDate";
		log.info("Leaving into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return absenceSearchCriteria;
	}*/

	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns search criteria for student absence period details
	 */
	public String getAbsenceSearchCriteria(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		StringBuffer absenceSearchCriteria = new StringBuffer("from AttendanceStudent attendancestudent "
		+ " where attendancestudent.attendance.subject.id = " + Integer.parseInt(attendanceSummaryForm.getSubjectId())
		+ " and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID()) 
		+ " and attendancestudent.isPresent = 0 and  attendancestudent.attendance.isCanceled = 0");
		if(attendanceSummaryForm.getAttendanceTypeId()!= null && !attendanceSummaryForm.getAttendanceTypeId().trim().isEmpty()){
			absenceSearchCriteria.append(" and attendancestudent.attendance.attendanceType.id = " + Integer.parseInt(attendanceSummaryForm.getAttendanceTypeId())); 
		}
		absenceSearchCriteria.append(" order by attendancestudent.attendance.attendanceDate");
		log.info("Leaving into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return absenceSearchCriteria.toString();
	}	
	/**
	 * 
	 * @param attendanceStudentList
	 * @returns absence period informations
	 */
	public List<PeriodTO> populateAbsencePeriodInformations(
			List<AttendanceStudent> attendanceStudentList, StudentWiseAttendanceSummaryForm attendanceSummaryForm, boolean isSubjectAttendance,List<Integer> period) throws Exception{
		log.info("Entering into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		Map<Date, PeriodTO> periodMap = new LinkedHashMap<Date, PeriodTO>();
		PeriodTO periodTO = null;
		if(attendanceStudentList!=null){
			Iterator<AttendanceStudent> iterator = attendanceStudentList.iterator();
			while (iterator.hasNext()) {
				AttendanceStudent attendanceStudent = iterator.next();
				if(attendanceStudent.getAttendance()!=null){
					if(isSubjectAttendance){
						if(attendanceStudent.getAttendance().getSubject()!=null && attendanceStudent.getAttendance().getSubject().getName()!=null){
							attendanceSummaryForm.setSubjectName(attendanceStudent.getAttendance().getSubject().getName());
						}
					}
					else{
						if(attendanceStudent.getAttendance().getActivity()!=null && attendanceStudent.getAttendance().getActivity().getName()!=null){
							attendanceSummaryForm.setActivityName(attendanceStudent.getAttendance().getActivity().getName());
						}
					}
					periodTO = new PeriodTO();
					periodTO.setDate((CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate
					(attendanceStudent.getAttendance().getAttendanceDate()!=null ? 
					attendanceStudent.getAttendance().getAttendanceDate():null), "dd-MMM-yyyy","dd-MMM-yyyy")));;

					if(attendanceStudent.getAttendance().getAttendancePeriods()!=null){
						Iterator<AttendancePeriod> periodIterator = attendanceStudent.getAttendance().getAttendancePeriods().iterator();
						while (periodIterator.hasNext()) {
							AttendancePeriod attendancePeriod = periodIterator.next();
							if(attendancePeriod.getPeriod()!=null && attendancePeriod.getPeriod().getPeriodName()!=null){
								if(period.contains(attendancePeriod.getPeriod().getId())){
								if(attendanceStudent.getAttendance().getAttendanceDate()!=null){
									if(!periodMap.containsKey(attendanceStudent.getAttendance().getAttendanceDate())){
										periodTO.setPeriodName(attendancePeriod.getPeriod().getPeriodName());
										if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
											periodTO.setName("Co-CurricularLeave");
										}else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
											periodTO.setName("Leave");
										}else{
											periodTO.setName("-");
										}
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}
									else{
										periodTO = periodMap.get(attendanceStudent.getAttendance().getAttendanceDate());
										periodTO.setPeriodName(periodTO.getPeriodName()+", "+attendancePeriod.getPeriod().getPeriodName());
										if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
											periodTO.setName(periodTO.getName()+",Co-CurricularLeave");
										}else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
											periodTO.setName(periodTO.getName()+",Leave");
										}else{
											periodTO.setName(periodTO.getName()+",-");
										}
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}	
								}
								}
							}
							
						}
					}
					periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
				}
			}
		}
		List<PeriodTO> periodList = new ArrayList<PeriodTO>(); 
		periodList.addAll(periodMap.values());
		log.info("Leaving into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		return periodList;
	}
	/**
	 * 
	 * @param studentId
	 * @param currentYear 
	 * @returns activity conducted summary information query
	 */

	public String getActivityInformationSummaryQueryForConducted(int studentId, int currentYear) {
		log.info("Entering into getActivityInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		/*String studentWiseActivityConductedAttendanceSummary = "select attendanceStudents.attendance.activity.id, " 
		+ "attendanceStudents.attendance.activity.name, "
		+ "attendanceStudents.student.id, "
		+ "count(attendanceStudents.attendance.hoursHeld)," 
		+ "attendanceStudents.attendance.attendanceType.name"
		+ " from Student student " 
		+ "inner join student.attendanceStudents attendanceStudents " 
		+"inner join attendanceStudents.attendance attendance "
		+"inner join attendance.attendanceClasses attendanceClasses "
		+"inner join attendanceClasses.classSchemewise classSchemewise "
		+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
		+"where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
		+"and attendanceStudents.attendance.isActivityAttendance = 1 " 
		+"and  attendanceStudents.attendance.isCanceled = 0 and "
		+"student.id = " + studentId +" and "
		+"curriculumSchemeDuration.academicYear="+currentYear
		+" group by attendanceStudents.attendance.activity.id "
		+"order by attendanceStudents.attendance.activity.id";*/
		
		
//		Query Changed by priyatham
		String studentWiseActivityConductedAttendanceSummary = "select activity.id, " 
			+ "activity.name, "
			+ "attendanceStudents.student.id, "
			+ "sum(attendance.hoursHeld)," 
			+ "attendance.attendanceType.name"
			+ " from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			+"inner join attendanceStudents.attendance attendance "
			+"left join attendance.activity activity "
			+"left join attendance.subject subject "
			+"inner join attendance.attendanceClasses attendanceClasses "
			+"inner join attendanceClasses.classSchemewise classSchemewise "
			+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
			+"where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
			+"and (attendance.isActivityAttendance=1 or (activity.name=null and subject.name=null)) " 
			+"and  attendanceStudents.attendance.isCanceled = 0 and "
			+"student.id = " + studentId +" and "
			+"curriculumSchemeDuration.academicYear="+currentYear
			+" and classSchemewise.id= student.classSchemewise.id group by attendanceStudents.attendance.activity.id "
			+"order by attendanceStudents.attendance.activity.id";
		log.info("Leaving into getActivityInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseActivityConductedAttendanceSummary;
	}

	/**
	 * 
	 * @param studentId
	 * @param currentYear 
	 * @returns activity present summary information query
	 */
	public String getActivityInformationSummaryQueryForPresent(int studentId, int currentYear) {
		log.info("Entering into getActivityInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		/*String studentWiseActivityPresentAttendanceSummary = "select attendanceStudents.attendance.activity.id, " 
		+ "attendanceStudents.attendance.activity.name, "
		+ "attendanceStudents.student.id, "
		+ "count(attendanceStudents.attendance.hoursHeld),"
		+ "attendanceStudents.attendance.attendanceType.name "
		+ "from Student student " 
		+ "inner join student.attendanceStudents attendanceStudents "
		+"inner join attendanceStudents.attendance attendance "
		+"inner join attendance.attendanceClasses attendanceClasses "
		+"inner join attendanceClasses.classSchemewise classSchemewise "
		+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
		+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
		+ "and attendanceStudents.attendance.isActivityAttendance = 1 " 
		+ "and  attendanceStudents.attendance.isCanceled = 0 and "
		+ "attendanceStudents.isPresent = 1 and "
		+ "student.id = " + studentId 
		+" and curriculumSchemeDuration.academicYear="+currentYear
		+ "group by attendanceStudents.attendance.activity.id "
		+ "order by attendanceStudents.attendance.activity.id";*/
//		Query Changed by priyatham
		String studentWiseActivityPresentAttendanceSummary = "select activity.id, " 
			+ "activity.name, "
			+ "attendanceStudents.student.id, "
			+ "sum(attendance.hoursHeld)," 
			+ "attendance.attendanceType.name"
			+ " from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			+"inner join attendanceStudents.attendance attendance "
			+"left join attendance.activity activity "
			+"left join attendance.subject subject "
			+"inner join attendance.attendanceClasses attendanceClasses "
			+"inner join attendanceClasses.classSchemewise classSchemewise "
			+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
			+"where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
			+"and (attendance.isActivityAttendance=1 or (activity.name=null and subject.name=null)) " 
			+"and  attendanceStudents.attendance.isCanceled = 0 and (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) and "
			+"student.id = " + studentId +" and "
			+"curriculumSchemeDuration.academicYear="+currentYear
			+" and classSchemewise.id= student.classSchemewise.id group by attendanceStudents.attendance.activity.id "
			+"order by attendanceStudents.attendance.activity.id";
		log.info("Leaving into getActivityInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		return studentWiseActivityPresentAttendanceSummary;
	}
	/**
	 * 
	 * @param classesConductedList
	 * @returns ActivityMap
	 */
	public Map<String, StudentWiseSubjectSummaryTO> convertActivityAttendanceSummaryListToMap(
			List<Object[]> classesConductedList) {
		log.info("Entering into convertActivityAttendanceSummaryListToMap of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> activityMap = new LinkedHashMap<String, StudentWiseSubjectSummaryTO>();
		StudentWiseSubjectSummaryTO summaryTO = null;
		if(classesConductedList!=null){
			Iterator<Object[]> iterator = classesConductedList.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				summaryTO = new StudentWiseSubjectSummaryTO();
				if(objects[0] != null){
					summaryTO.setActivityID(((Integer) objects[0]).toString());
				}else{
					summaryTO.setActivityID("");
				}
				if(objects[1] != null){
					summaryTO.setActivityName(((String) objects[1]));						
				}else{
					summaryTO.setActivityName("");
				}
				if(objects[2] != null){
					summaryTO.setStudentId(((Integer) objects[2]).toString());
				}
				if(objects[3] != null){
					summaryTO.setConductedClasses(((Long) objects[3]).intValue());
				}
				if(objects[4]!=null){
					summaryTO.setAttendanceTypeName((String)objects[4]);
				}
				if(objects[0] != null){
					activityMap.put(((Integer) objects[0]).toString(), summaryTO);
				}else{
					activityMap.put("",summaryTO);
				}
			}
		}
		log.info("Leaving into convertActivityAttendanceSummaryListToMap of StudentAttendanceSummaryHelper");
		return activityMap;
	}
	/**
	 * 
	 * @param classesConductedMap
	 * @param classesPresentMap
	 * @returns activity attendance information summary list
	 */
	public List<StudentWiseSubjectSummaryTO> convertFromActivityAttendanceSummaryMapToList(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap) {
		log.info("Entering into convertFromActivityAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		List<StudentWiseSubjectSummaryTO> activityAttendanceSummaryList = new ArrayList<StudentWiseSubjectSummaryTO>();
		Iterator<String> activityIterator = classesConductedMap.keySet().iterator();
		while (activityIterator.hasNext()) {
			String activityID = activityIterator.next();
			StudentWiseSubjectSummaryTO classesConductedTO = classesConductedMap.get(activityID);
			if(classesPresentMap.containsKey(activityID)){
				StudentWiseSubjectSummaryTO classesPresentTO = classesPresentMap.get(activityID);
				classesConductedTO.setClassesPresent(classesPresentTO.getConductedClasses());
			}
			else{
				classesConductedTO.setClassesPresent(0);
			}
			classesConductedTO.setClassesAbsent(classesConductedTO.getConductedClasses()-
					classesConductedTO.getClassesPresent());
			float percentage = (classesConductedTO.getClassesPresent() * 100)/classesConductedTO.getConductedClasses();
			classesConductedTO.setPercentageWithoutLeave(percentage);
			activityAttendanceSummaryList.add(classesConductedTO);
		}
		log.info("Leaving into convertFromActivityAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		return activityAttendanceSummaryList;
	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns search criteria for student Activity absence period details
	 */
	public String getActivityAbsenceSearchCriteria(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getActivityAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		Integer activityId=null;
		if(attendanceSummaryForm.getActivityId()!=null && !attendanceSummaryForm.getActivityId().isEmpty())
		 activityId=Integer.parseInt(attendanceSummaryForm.getActivityId());
		String activityAbsenceSearchCriteria = "from AttendanceStudent attendancestudent ";
			if(activityId!=null){
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "where attendancestudent.attendance.activity.id = " + activityId;
			}else{
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "where attendancestudent.attendance.activity.id is null " ;
			}
		activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ " and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID())
		+ " and attendancestudent.isPresent = 0 ";
		if(activityId!=null){
			activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "and attendancestudent.attendance.isActivityAttendance = 1 ";
		}else{
			activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "and attendancestudent.attendance.isActivityAttendance = 0 ";
			if(attendanceSummaryForm.getAttendanceTypeName()!=null && !attendanceSummaryForm.getAttendanceTypeName().isEmpty())
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+" and attendancestudent.attendance.attendanceType.name='"+attendanceSummaryForm.getAttendanceTypeName()+"' ";
		}
		activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "and attendancestudent.attendance.isMonthlyAttendance = 0 "
		+ " order by attendancestudent.attendance.attendanceDate";
		log.info("Leaving into getActivityAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return activityAbsenceSearchCriteria;
	}

	public String getAbsenceInformationSummaryQueryIsOnLeave(int studentId,int classId) {
		log.info("Entering into getAbsenceInformationSummaryQueryIsOnLeave of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "	select attendanceStudents.attendance.subject.id,  " 
				+"sum(attendanceStudents.attendance.hoursHeld)," 
				+"attendanceStudents.attendance.subject.name  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 "
				+ " and attendanceStudents.student.admAppln.isCancelled = 0 "
				+ " and attendanceStudents.isOnLeave = 1 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.id = " +studentId+" and ac.classSchemewise.classes.id="+classId
				+ "  group by attendanceStudents.attendance.subject.id ";
		
		log.info("Leaving into getAbsenceInformationSummaryQueryIsOnLeave of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}
	//Satish Patruni
	public String getAbsenceInformationSummaryQueryIsOnCocurricularLeave(int studentId,int classId) {
		log.info("Entering into getAbsenceInformationSummaryQueryIsOnCocurricularLeave of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "	select attendanceStudents.attendance.subject.id,  " 
				+"sum(attendanceStudents.attendance.hoursHeld)," 
				+"attendanceStudents.attendance.subject.name  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 "
				+ " and attendanceStudents.student.admAppln.isCancelled = 0 "
				+ " and attendanceStudents.isCoCurricularLeave = 1 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.id = " +studentId+" and ac.classSchemewise.classes.id="+classId
				+ "  group by attendanceStudents.attendance.subject.id ";
		
		log.info("Leaving into getAbsenceInformationSummaryQueryIsOnCocurricularLeave of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}

	public Map<Integer, StudentWiseSubjectSummaryTO> convertclassesOnLeaveListToMap(
			List<Object[]> classesOnLeaveList) {
		log.info("Entering into convertclassesOnLeaveListToMap of StudentAttendanceSummaryHelper");
		Map<Integer, StudentWiseSubjectSummaryTO> subjectWiseAttendanceSummaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();

		if (classesOnLeaveList != null) {
			Iterator leaveWiseAttendanceSummaryIterator = classesOnLeaveList
					.iterator();
			while (leaveWiseAttendanceSummaryIterator.hasNext()) {
				Object[] object = (Object[]) leaveWiseAttendanceSummaryIterator
						.next();
				StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
				if(object[1]!=null){
					studentWiseSubjectSummaryTO.setLeaveApproved(((Long) object[1]).intValue());
				}
				if (object[2] != null) {
					studentWiseSubjectSummaryTO.setSubjectName(object[2]
							.toString());
				}
				subjectWiseAttendanceSummaryMap.put((Integer) object[0],
						studentWiseSubjectSummaryTO);

			}
		}
		log.info("Leaving into convertclassesOnLeaveListToMap of StudentAttendanceSummaryHelper");
		return subjectWiseAttendanceSummaryMap;
	}
	//Satish Patruni
	public Map<Integer, StudentWiseSubjectSummaryTO> convertclassesOnCoCurricularLeaveListToMap(
			List<Object[]> classesOnCoCurricularLeaveList) {
		log.info("Entering into convertclassesOnLeaveListToMap of StudentAttendanceSummaryHelper");
		Map<Integer, StudentWiseSubjectSummaryTO> subjectWiseAttendanceSummaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();

		if (classesOnCoCurricularLeaveList != null) {
			Iterator leaveWiseAttendanceSummaryIterator = classesOnCoCurricularLeaveList
					.iterator();
			while (leaveWiseAttendanceSummaryIterator.hasNext()) {
				Object[] object = (Object[]) leaveWiseAttendanceSummaryIterator
						.next();
				StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
				if(object[1]!=null){
					studentWiseSubjectSummaryTO.setCocurricularLeave(((Long) object[1]).intValue());
					log.info("studentWiseSubjectSummaryTO.setCocurricularLeave: " + object[1].toString());
				}
				if (object[2] != null) {
					studentWiseSubjectSummaryTO.setSubjectName(object[2]
							.toString());
					log.info("studentWiseSubjectSummaryTO.setSubjectName: " + object[2].toString());
				}
				subjectWiseAttendanceSummaryMap.put((Integer) object[0],
						studentWiseSubjectSummaryTO);
			}
		}
		log.info("Leaving into convertclassesOnLeaveListToMap of StudentAttendanceSummaryHelper");
		return subjectWiseAttendanceSummaryMap;
	}
	/**
	 * to display remarks
	 * @param remarkBOs
	 * @return
	 */
	public List<StudentRemarksTO> convertRemarkBoToTO(
			List<StudentRemarks> remarkBOs) {
		log.info("Enter convertRemarkBoToTO ...");
		List<StudentRemarksTO> remarktoList= new ArrayList<StudentRemarksTO>();
		List<Integer> remtypeList= new ArrayList<Integer>();
		if(remarkBOs!=null){
			Iterator<StudentRemarks> remItr=remarkBOs.iterator();
			
			while (remItr.hasNext()) {
				StudentRemarks remarks = (StudentRemarks) remItr.next();
				if(remarks.getRemarkType()!=null && !remtypeList.contains(remarks.getRemarkType().getId())){
					StudentRemarksTO remTo= new StudentRemarksTO();
					remTo.setStudentRemarkId(remarks.getId());
					remTo.setCreatedBy(remarks.getCreatedBy());
					remTo.setCreatedDate(remarks.getCreatedDate());
					remTo.setModifiedBy(remarks.getModifiedBy());
					remTo.setLastModifiedDate(remarks.getLastModifiedDate());
					remTo.setRemarkType(remarks.getRemarkType().getRemarkType());
					int remId=remarks.getRemarkType().getId();
					remtypeList.add(remId);
					List<String> comments=new ArrayList<String>();
					String colorCode="";
					int remCount=0;
					Iterator<StudentRemarks> cmtItr=remarkBOs.iterator();
					while (cmtItr.hasNext()) {
						StudentRemarks studentRemarks = (StudentRemarks) cmtItr.next();
						if(studentRemarks.getRemarkType().getId()==remId){
							remCount++;
							comments.add(studentRemarks.getComments());
							if(remCount > studentRemarks.getRemarkType().getMaxOccurrences()){
								colorCode=studentRemarks.getRemarkType().getColor();
								remTo.setColourCode(colorCode);
							}
						}
					}
					
					remTo.setComments(comments);
					remTo.setOccurance(remCount);
					if(!StringUtils.isEmpty(remTo.getColourCode()))
						remTo.setColourPresent(true);
					remarktoList.add(remTo);
				}
			}
		}
		log.info("Exit convertRemarkBoToTO ...");
		return remarktoList;
	}
	/**
	 * 
	 * @param attendanceSummaryForm
	 * @returns search criteria for student absence period details
	 */
	public String getApprovedLeaveSearchCriteria(
			StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		StringBuffer absenceSearchCriteria = new StringBuffer("from AttendanceStudent attendancestudent " 
		+ " where attendancestudent.attendance.subject.id = " + Integer.parseInt(attendanceSummaryForm.getSubjectId())
		+ " and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID()) 
		+ " and attendancestudent.isPresent = 0 and  attendancestudent.isOnLeave = 1" 
		+ " and (attendancestudent.isCoCurricularLeave is null or attendancestudent.isCoCurricularLeave = 0)");
		if(attendanceSummaryForm.getAttendanceTypeId()!= null && !attendanceSummaryForm.getAttendanceTypeId().trim().isEmpty()){
			absenceSearchCriteria.append(" and attendancestudent.attendance.attendanceType.id = " + Integer.parseInt(attendanceSummaryForm.getAttendanceTypeId())); 
		}
		absenceSearchCriteria.append(" order by attendancestudent.attendance.attendanceDate");
		log.info("Leaving into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return absenceSearchCriteria.toString();
	}
	/**
	 * 
	 * @param examMarksEntryDetailsBOList
	 * @return
	 */
	public Map<Integer, List<ExamMarksEntryDetailsTO>> convertMarkListToMap(List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList, List<Integer> examIds, boolean isStudentAtt){
		log.info("Entering into convertMarkListToMap of StudentAttendanceSummaryHelper");
//		Iterator<ExamMarksEntryDetailsBO> markBoItr = examMarksEntryDetailsBOList.iterator();
		ExamMarksEntryDetailsTO examMarksEntryDetailsTO;
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMap = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList1 = new ArrayList<ExamMarksEntryDetailsTO>();
		Set<String> examCodeMap = new HashSet<String>();		
		Iterator<ExamMarksEntryDetailsBO> markBoItr = examMarksEntryDetailsBOList.iterator();
		while (markBoItr.hasNext()) {
			ExamMarksEntryDetailsBO examMarksEntryDetailsBO = (ExamMarksEntryDetailsBO) markBoItr.next();
			if(isStudentAtt){
				if(!examIds.contains(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamId())){
					continue;
				}
			}
			
			examMarksEntryDetailsTO = new ExamMarksEntryDetailsTO();
			examMarksEntryDetailsTO.setSubjectId(examMarksEntryDetailsBO.getSubjectId());
			int subjectId = examMarksEntryDetailsBO.getSubjectId();
			if(examMarksEntryDetailsBO.getTheoryMarks()!= null){
				examMarksEntryDetailsTO.setTheoryMarks(examMarksEntryDetailsBO.getTheoryMarks());
			}
			if(examMarksEntryDetailsBO.getPracticalMarks()!= null && !examMarksEntryDetailsBO.getPracticalMarks().isEmpty()){
				examMarksEntryDetailsTO.setPracticalMarks(examMarksEntryDetailsBO.getPracticalMarks());
			}
			examMarksEntryDetailsTO.setExamCode(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode());
			examMarksEntryDetailsTO.setExamName(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getName());
			
			if(examMarkMap.containsKey(subjectId)){
				examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
			}
			else
			{
				examMarksEntryDetailsTOList = new ArrayList<ExamMarksEntryDetailsTO>();
			}
			if(examMarksEntryDetailsBO.getExamMarksEntryBO()!= null && 
					examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO()!= null && examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode()!= null){
				
  				if(!examCodeMap.contains(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode().trim())){
  					examCodeMap.add(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode().trim());
  				}
			}
			examMarksEntryDetailsTOList1.add(examMarksEntryDetailsTO);
			examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
			examMarkMap.put(subjectId, examMarksEntryDetailsTOList);
		}
		
		Iterator<Integer> keyIterator = examMarkMap.keySet().iterator();
  		ExamMarksEntryDetailsTO tempTo;  
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMapNew = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
  		
  		while (keyIterator.hasNext()) {
  			Integer subjectId = (Integer) keyIterator.next();
  			examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
  			Iterator<ExamMarksEntryDetailsTO> tempItr = examMarksEntryDetailsTOList.iterator();
  			Set<String> tempMap = new HashSet<String>();
  			while(tempItr.hasNext()){
  				tempTo = tempItr.next();
  				tempMap.add(tempTo.getExamCode());
  			}
  			ExamMarksEntryDetailsTO newTo; 
  			Iterator<String> origItr = examCodeMap.iterator();
  			while(origItr.hasNext()){
  				String code = origItr.next();
  				if(!tempMap.contains(code)){
  					newTo = new ExamMarksEntryDetailsTO();
  					newTo.setExamCode(code);
  					newTo.setTheoryMarks("");
  					newTo.setPracticalMarks("");
  					examMarksEntryDetailsTOList.add(newTo);
  				}
  			}
  			examMarkMapNew.put(subjectId, examMarksEntryDetailsTOList);
  		}				
		
		
		log.info("Exit convertMarkListToMap of StudentAttendanceSummaryHelper");
		return examMarkMapNew;
	}
	//Code added by Balaji
	/**
	 * Constructs the search query for student wise attendance summary information summary for present classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getStudentAbsenceInformationSummaryQueryForPresent1(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
		int acadamicYear = currBo.getAcademicYear();
		int termNo = currBo.getSemesterYearNo();
		String studentWiseAttendanceSummary = "";
		if(acadamicYear>2017){
		 	studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then (case when attendanceStudents.attendance.hoursHeld is not null then attendanceStudents.attendance.hoursHeld else 0 end) else 0 end)"
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				/*+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
//						+ " (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else if(acadamicYear==2017 && (termNo!=1 && termNo!=3 && termNo!=5)){
		 	studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then (case when attendanceStudents.attendance.hoursHeld is not null then attendanceStudents.attendance.hoursHeld else 0 end) else 0 end)"
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				/*+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
//						+ " (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else{
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then (case when attendanceStudents.attendance.hoursHeld is not null then 1 else 0 end) else 0 end)"
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				/*+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
//				+ " (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";

		}
			
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	/**
	 * 
	 * @param classesConductedMap
	 * @param classesPresentMap
	 * @returns the final list contatining attendance informations of the student
	 */
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToListView(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,int studentId,StudentWiseAttendanceSummaryForm attendanceSummaryForm, StudentWiseSubjectSummaryTO studentWiseSubjectSummary) throws Exception {
		log.info("Entering into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdLogin(subjectString.toString());
			}
		}
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						
						summaryTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						summaryTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						summaryTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						//attendanceTypeTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							summaryTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						summaryTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						summaryTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						summaryTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=summaryTO.getClassesPresent();
						float cc=summaryTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						summaryTO.setTotalAttPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + summaryTO.getClassesPresent();
//						totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						totalAbscent=totalAbscent+summaryTO.getClassesAbsent();
						//typeList.add(summaryTO);
						//summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						summaryTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						summaryTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						summaryTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							summaryTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						summaryTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						summaryTO.setClassesPresent(0);
						summaryTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=summaryTO.getClassesPresent();
						float cc=summaryTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						summaryTO.setTotalAttPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + summaryTO.getClassesPresent();
						/*if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}*/
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						//typeList.add(attendanceTypeTO);
						//summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						summaryTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						summaryTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						summaryTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							summaryTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						summaryTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						summaryTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						summaryTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=summaryTO.getClassesPresent();
						float cc=summaryTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						summaryTO.setTotalAttPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + summaryTO.getClassesPresent();
						if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}
						//typeList.add(attendanceTypeTO);
					//	summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
			
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		log.info("Leaving into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		/*if(studentWiseSubjectSummaryTOList!=null && !studentWiseSubjectSummaryTOList.isEmpty()){
			totalPercentage=totalPercentage/studentWiseSubjectSummaryTOList.size();
			attendanceSummaryForm.setTotalPercentage(String.valueOf(totalPercentage));
		}*/
		totalPercentage=(totalPresent/totalConducted * 100);
		DecimalFormat df = new DecimalFormat("#.##");
		String s=df.format(totalPercentage);
		attendanceSummaryForm.setTotalPercentage(s);
		attendanceSummaryForm.setTotalConducted(String.valueOf(totalConducted));
		attendanceSummaryForm.setTotalPresent(String.valueOf(totalPresent));
		attendanceSummaryForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}
	
	
	
	
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToListAdditional(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,int studentId,StudentWiseAttendanceSummaryForm attendanceSummaryForm,String mode) throws Exception {
		log.info("Entering into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if(!mode.equalsIgnoreCase("Previous")){
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdAdditional(subjectString.toString());
			 }
		   }
		}else{
			subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsByClassIdAdditional(attendanceSummaryForm.getClassesId());
		}
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						attendanceTypeTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(0);
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
			
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		log.info("Leaving into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		/*if(studentWiseSubjectSummaryTOList!=null && !studentWiseSubjectSummaryTOList.isEmpty()){
			totalPercentage=totalPercentage/studentWiseSubjectSummaryTOList.size();
			attendanceSummaryForm.setTotalPercentage(String.valueOf(totalPercentage));
		}*/
		totalPercentage=(totalPresent/totalConducted * 100);
		 DecimalFormat df = new DecimalFormat("#.##");
		 String s=df.format(totalPercentage);
		attendanceSummaryForm.setTotalPercentage(s);
		attendanceSummaryForm.setTotalConducted(String.valueOf(totalConducted));
		attendanceSummaryForm.setTotalPresent(String.valueOf(totalPresent));
		attendanceSummaryForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}

	/**
	 * @param examMarksEntryDetailsBOList
	 * @param examIdList
	 * @param studentWiseAttendanceSummaryForm 
	 * @param courseId 
	 * @return
	 */
	public List<ExamMarksEntryDetailsTO> convertMarkListBOToMarksTO(List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList,
			List<Integer> examIdList, String courseId, StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm, int studentId) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList = new ArrayList<ExamMarksEntryDetailsTO>();
		Iterator<ExamMarksEntryDetailsBO> markBoIterator = examMarksEntryDetailsBOList.iterator();
		while (markBoIterator.hasNext()) {
			ExamMarksEntryDetailsBO examMarksEntryDetailsBO = (ExamMarksEntryDetailsBO) markBoIterator
					.next();
			if(!examIdList.contains(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamId())){
				continue;
			}
			
			ExamMarksEntryDetailsTO examMarksEntryDetailsTO = new ExamMarksEntryDetailsTO();
			examMarksEntryDetailsTO.setExamCode(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode());
			examMarksEntryDetailsTO.setExamName(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getName());
			if(examMarksEntryDetailsBO.getTheoryMarks()!= null){
				examMarksEntryDetailsTO.setTheoryMarks(examMarksEntryDetailsBO.getTheoryMarks());
			}
			if(examMarksEntryDetailsBO.getPracticalMarks()!= null && !examMarksEntryDetailsBO.getPracticalMarks().isEmpty()){
				examMarksEntryDetailsTO.setPracticalMarks(examMarksEntryDetailsBO.getPracticalMarks());
			}
			if(examMarksEntryDetailsBO.getSubjectUtilBO().getName() != null && !examMarksEntryDetailsBO.getSubjectUtilBO().getName().isEmpty()){
				examMarksEntryDetailsTO.setSubjectName(examMarksEntryDetailsBO.getSubjectUtilBO().getName());
			}
			int internalExamTypeId = examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getInternalExamTypeId();
			int subjectId = examMarksEntryDetailsBO.getSubjectId();
			List<ExamSubjectRuleSettingsSubInternalBO> examSubjectRuleSettingsSubInternalBOs = studentWiseAttendanceSummaryTransaction.getMaxMarksFromExamSubjectRuleSettingsSubInternal(internalExamTypeId, subjectId, courseId, studentId);
			Iterator<ExamSubjectRuleSettingsSubInternalBO> examSubjectRule = examSubjectRuleSettingsSubInternalBOs.iterator();
			while (examSubjectRule.hasNext()) {
				ExamSubjectRuleSettingsSubInternalBO examSubjectRuleSettingsSubInternalBO = (ExamSubjectRuleSettingsSubInternalBO) examSubjectRule
						.next();
				if(examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical().equalsIgnoreCase("t")){
					BigDecimal maxmarks = examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark();
					examMarksEntryDetailsTO.setTheoryMaxMarks(String.valueOf(maxmarks.intValue()));
				}
				if(examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical().equalsIgnoreCase("p")){
					examMarksEntryDetailsTO.setPracticalMaxMarks(examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark().toString());
				}
			}
			examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
		}
		return examMarksEntryDetailsTOList;
	
	}
	/**
	 * @param examMarksEntryDetailsBOList
	 * @param examIds
	 * @param isStudentAtt
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, List<ExamMarksEntryDetailsTO>> convertMarkListToMapList(List<ExamMarksEntryDetailsBO> examMarksEntryDetailsBOList, List<Integer> examIds, boolean isStudentAtt, String courseId, int studentId) throws Exception{
		
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		ExamMarksEntryDetailsTO examMarksEntryDetailsTO;
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMap = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		Set<String> examCodeMap = new HashSet<String>();		
		Iterator<ExamMarksEntryDetailsBO> markBoItr = examMarksEntryDetailsBOList.iterator();
		while (markBoItr.hasNext()) {
			ExamMarksEntryDetailsBO examMarksEntryDetailsBO = (ExamMarksEntryDetailsBO) markBoItr.next();
			if(isStudentAtt){
				if(!examIds.contains(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamId())){
					continue;
				}
			}
			
			examMarksEntryDetailsTO = new ExamMarksEntryDetailsTO();
			examMarksEntryDetailsTO.setSubjectId(examMarksEntryDetailsBO.getSubjectId());
			int subjectId = examMarksEntryDetailsBO.getSubjectId();
			int internalExamTypeId = examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getInternalExamTypeId();
			int subId = examMarksEntryDetailsBO.getSubjectId();
			List<ExamSubjectRuleSettingsSubInternalBO> examSubjectRuleSettingsSubInternalBOs = studentWiseAttendanceSummaryTransaction.getMaxMarksFromExamSubjectRuleSettingsSubInternal(internalExamTypeId, subId, courseId, studentId);
			Iterator<ExamSubjectRuleSettingsSubInternalBO> examSubjectRule = examSubjectRuleSettingsSubInternalBOs.iterator();
			while (examSubjectRule.hasNext()) {
				ExamSubjectRuleSettingsSubInternalBO examSubjectRuleSettingsSubInternalBO = (ExamSubjectRuleSettingsSubInternalBO) examSubjectRule
						.next();
				if(examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical().equalsIgnoreCase("t")){
					BigDecimal maxmarks = examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark();
					examMarksEntryDetailsTO.setTheoryMaxMarks(String.valueOf(maxmarks.intValue()));
				}
				if(examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical() != null && examSubjectRuleSettingsSubInternalBO.getIsTheoryPractical().equalsIgnoreCase("p")){
					BigDecimal maxmarks = examSubjectRuleSettingsSubInternalBO.getEnteredMaxMark();
					examMarksEntryDetailsTO.setPracticalMaxMarks(String.valueOf(maxmarks.intValue()));
				}
			}
			if(examMarksEntryDetailsBO.getTheoryMarks()!= null && !examMarksEntryDetailsBO.getTheoryMarks().isEmpty()){
				examMarksEntryDetailsTO.setTheoryMarks(examMarksEntryDetailsBO.getTheoryMarks()+"/"+examMarksEntryDetailsTO.getTheoryMaxMarks());
			}
			if(examMarksEntryDetailsBO.getPracticalMarks()!= null && !examMarksEntryDetailsBO.getPracticalMarks().isEmpty()){
				examMarksEntryDetailsTO.setPracticalMarks(examMarksEntryDetailsBO.getPracticalMarks()+"/"+examMarksEntryDetailsTO.getPracticalMaxMarks());
			}
			examMarksEntryDetailsTO.setExamCode(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode());
			examMarksEntryDetailsTO.setExamName(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getName());
			
			if(examMarkMap.containsKey(subjectId)){
				examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
			}
			else
			{
				examMarksEntryDetailsTOList = new ArrayList<ExamMarksEntryDetailsTO>();
			}
			if(examMarksEntryDetailsBO.getExamMarksEntryBO()!= null && 
					examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO()!= null && examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode()!= null){
				
  				if(!examCodeMap.contains(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode().trim())){
  					examCodeMap.add(examMarksEntryDetailsBO.getExamMarksEntryBO().getExamDefinitionBO().getExamCode().trim());
  				}
			}

			examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
			examMarkMap.put(subjectId, examMarksEntryDetailsTOList);
		}
		
		Iterator<Integer> keyIterator = examMarkMap.keySet().iterator();
  		ExamMarksEntryDetailsTO tempTo;  
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMapNew = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
  		
  		while (keyIterator.hasNext()) {
  			Integer subjectId = (Integer) keyIterator.next();
  			examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
  			Iterator<ExamMarksEntryDetailsTO> tempItr = examMarksEntryDetailsTOList.iterator();
  			Set<String> tempMap = new HashSet<String>();
  			while(tempItr.hasNext()){
  				tempTo = tempItr.next();
  				tempMap.add(tempTo.getExamCode());
  			}
  			ExamMarksEntryDetailsTO newTo; 
  			Iterator<String> origItr = examCodeMap.iterator();
  			while(origItr.hasNext()){
  				String code = origItr.next();
  				if(!tempMap.contains(code)){
  					newTo = new ExamMarksEntryDetailsTO();
  					newTo.setExamCode(code);
  					newTo.setTheoryMarks("");
  					newTo.setPracticalMarks("");
  					examMarksEntryDetailsTOList.add(newTo);
  				}
  			}
  			examMarkMapNew.put(subjectId, examMarksEntryDetailsTOList);
  		}			
		log.info("Exit convertMarkListToMap of StudentAttendanceSummaryHelper");
		return examMarkMapNew;
	}
	
	
	/**
	 * @param examMarkMapNew
	 * @throws Exception 
	 */
	public List<ExamDefinitionBO> sortExamMarkMap(List<SubjectTO> examMarkMapNew) throws Exception{
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		List<ExamDefinitionBO> examDefinationBoList = new ArrayList<ExamDefinitionBO>();
		List<String> examCodeList = new ArrayList<String>();
		List<ExamMarksEntryDetailsTO> examMarksCodeList = new ArrayList<ExamMarksEntryDetailsTO>();

		if(examMarkMapNew != null && !examMarkMapNew.isEmpty()){
			Iterator<SubjectTO> listIterator = examMarkMapNew.iterator();
			while (listIterator.hasNext()) {
				SubjectTO studentSubjectList = (SubjectTO) listIterator.next();
				List<ExamMarksEntryDetailsTO> examMarksCodeList1 = studentSubjectList.getExamMarksEntryDetailsTOList();
				if(examMarksCodeList1 != null && !examMarksCodeList1.isEmpty()){
					examMarksCodeList.addAll(examMarksCodeList1);
				}
			}
			if(examMarksCodeList != null && !examMarksCodeList.isEmpty()){
				Iterator<ExamMarksEntryDetailsTO> ExamlistIterator = examMarksCodeList.iterator();
				while (ExamlistIterator.hasNext()) {
					ExamMarksEntryDetailsTO examMarksEntryDetailsTO2 = (ExamMarksEntryDetailsTO) ExamlistIterator
							.next();
					String examCode = examMarksEntryDetailsTO2.getExamCode();
					examCodeList.add(examCode);
				}
			}
			if(examCodeList != null && !examCodeList.isEmpty()){
				examDefinationBoList= studentWiseAttendanceSummaryTransaction.getExamDefinationList(examCodeList);
			}
		}
		return examDefinationBoList;
	}

	/**
	 * @param subjectBOList
	 * @return
	 * @throws Exception
	 */
	public List<SubjectTO> convertSubjectBOToSubjectTO (List<Subject> subjectBOList) throws Exception{
		
		List<SubjectTO> subjectToList = new ArrayList<SubjectTO>();
		if(subjectBOList != null && !subjectBOList.isEmpty()){
			Iterator<Subject> subIterator = subjectBOList.iterator();
			while (subIterator.hasNext()) {
				Subject subject = (Subject) subIterator.next();
				SubjectTO subTo = new SubjectTO();
				subTo.setSubjectID(String.valueOf(subject.getId()));
				subTo.setSubjectName(subject.getName());
				subjectToList.add(subTo);
			}
		}
		
		return subjectToList;
	}

	public Map<Integer, List<ExamMarksEntryDetailsTO>> convertMarkListToMapView(List<MarksEntryDetails> examMarksEntryDetailsBOList, List<Integer> examIds, boolean isStudentAtt){
		log.info("Entering into convertMarkListToMap of StudentAttendanceSummaryHelper");
//		Iterator<ExamMarksEntryDetailsBO> markBoItr = examMarksEntryDetailsBOList.iterator();
		ExamMarksEntryDetailsTO examMarksEntryDetailsTO;
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMap = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
		List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList1 = new ArrayList<ExamMarksEntryDetailsTO>();
		Set<String> examCodeMap = new HashSet<String>();		
		Iterator<MarksEntryDetails> markBoItr = examMarksEntryDetailsBOList.iterator();
		
		Map<Integer, Map<Integer,ExamMarksEntryDetailsTO>> mainMap = new HashMap<Integer, Map<Integer,ExamMarksEntryDetailsTO>>();
		Map<Integer,ExamMarksEntryDetailsTO> subMap=null;
		
		//Code has Changed by balaji
		while (markBoItr.hasNext()) {
			MarksEntryDetails examMarksEntryDetailsBO = (MarksEntryDetails) markBoItr.next();
			if(isStudentAtt){
				if(!examIds.contains(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamTypeID())){
					continue;
				}
			}
			
			if(mainMap.containsKey(examMarksEntryDetailsBO.getSubject().getId())){
				subMap=mainMap.remove(examMarksEntryDetailsBO.getSubject().getId());
			}else
				subMap=new HashMap<Integer, ExamMarksEntryDetailsTO>();
			
			if(subMap.containsKey(examMarksEntryDetailsBO.getMarksEntry().getExam().getId()))
				examMarksEntryDetailsTO=subMap.remove(examMarksEntryDetailsBO.getMarksEntry().getExam().getId());
			else
				examMarksEntryDetailsTO = new ExamMarksEntryDetailsTO();
			
			
			examMarksEntryDetailsTO.setSubjectId(examMarksEntryDetailsBO.getSubject().getId());
			if(examMarksEntryDetailsBO.getTheoryMarks()!= null){
				examMarksEntryDetailsTO.setTheoryMarks(examMarksEntryDetailsBO.getTheoryMarks());
			}
			if(examMarksEntryDetailsBO.getPracticalMarks()!= null && !examMarksEntryDetailsBO.getPracticalMarks().isEmpty()){
				examMarksEntryDetailsTO.setPracticalMarks(examMarksEntryDetailsBO.getPracticalMarks());
			}
			examMarksEntryDetailsTO.setExamCode(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode());
			examMarksEntryDetailsTO.setExamName(examMarksEntryDetailsBO.getMarksEntry().getExam().getName());
			//by giri
			examMarksEntryDetailsTO.setExamId(examMarksEntryDetailsBO.getMarksEntry().getExam().getId());
			//end by giri
			
			if(examMarksEntryDetailsBO.getMarksEntry()!= null && 
					examMarksEntryDetailsBO.getMarksEntry().getExam()!= null && examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode()!= null){
				
  				if(!examCodeMap.contains(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode().trim())){
  					examCodeMap.add(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode().trim());
  				}
			}
			examMarksEntryDetailsTOList1.add(examMarksEntryDetailsTO);
			subMap.put(examMarksEntryDetailsBO.getMarksEntry().getExam().getId(), examMarksEntryDetailsTO);
			mainMap.put(examMarksEntryDetailsBO.getSubject().getId(), subMap);
		}
		if(!mainMap.isEmpty()){
			Iterator<Map.Entry<Integer,Map<Integer,ExamMarksEntryDetailsTO>>> map=mainMap.entrySet().iterator();
			while (map.hasNext()) {
				Map.Entry<Integer, Map<Integer,ExamMarksEntryDetailsTO>> entry = map .next();
				examMarkMap.put(entry.getKey(),new ArrayList<ExamMarksEntryDetailsTO>(entry.getValue().values()));
			}
		}
		// code completed by balaji
		
		/*while (markBoItr.hasNext()) {
			MarksEntryDetails examMarksEntryDetailsBO = (MarksEntryDetails) markBoItr.next();
			if(isStudentAtt){
				if(!examIds.contains(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamTypeID())){
					continue;
				}
			}
			
			examMarksEntryDetailsTO = new ExamMarksEntryDetailsTO();
			examMarksEntryDetailsTO.setSubjectId(examMarksEntryDetailsBO.getSubject().getId());
			int subjectId = examMarksEntryDetailsBO.getSubject().getId();
			if(examMarksEntryDetailsBO.getTheoryMarks()!= null){
				examMarksEntryDetailsTO.setTheoryMarks(examMarksEntryDetailsBO.getTheoryMarks().toString());
			}
			if(examMarksEntryDetailsBO.getPracticalMarks()!= null && examMarksEntryDetailsBO.getPracticalMarks()!= ""){
				examMarksEntryDetailsTO.setPracticalMarks(examMarksEntryDetailsBO.getPracticalMarks());
			}
			examMarksEntryDetailsTO.setExamCode(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode());
			examMarksEntryDetailsTO.setExamName(examMarksEntryDetailsBO.getMarksEntry().getExam().getName());
			
			if(examMarkMap.containsKey(subjectId)){
				examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
			}
			else
			{
				examMarksEntryDetailsTOList = new ArrayList<ExamMarksEntryDetailsTO>();
			}
			if(examMarksEntryDetailsBO.getMarksEntry()!= null && 
					examMarksEntryDetailsBO.getMarksEntry().getExam()!= null && examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode()!= null){
				
  				if(!examCodeMap.contains(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode().trim())){
  					examCodeMap.add(examMarksEntryDetailsBO.getMarksEntry().getExam().getExamCode().trim());
  				}
			}
			examMarksEntryDetailsTOList1.add(examMarksEntryDetailsTO);
			examMarksEntryDetailsTOList.add(examMarksEntryDetailsTO);
			examMarkMap.put(subjectId, examMarksEntryDetailsTOList);
		}*/
		Iterator<Integer> keyIterator = examMarkMap.keySet().iterator();
  		ExamMarksEntryDetailsTO tempTo;  
		Map<Integer, List<ExamMarksEntryDetailsTO>> examMarkMapNew = new HashMap<Integer, List<ExamMarksEntryDetailsTO>>();
  		
  		while (keyIterator.hasNext()) {
  			Integer subjectId = (Integer) keyIterator.next();
  			List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList = examMarkMap.get(subjectId);
  			Iterator<ExamMarksEntryDetailsTO> tempItr = examMarksEntryDetailsTOList.iterator();
  			Set<String> tempMap = new HashSet<String>();
  			while(tempItr.hasNext()){
  				tempTo = tempItr.next();
  				tempMap.add(tempTo.getExamCode());
  			}
  			ExamMarksEntryDetailsTO newTo; 
  			Iterator<String> origItr = examCodeMap.iterator();
  			while(origItr.hasNext()){
  				String code = origItr.next();
  				if(!tempMap.contains(code)){
  					newTo = new ExamMarksEntryDetailsTO();
  					newTo.setExamCode(code);
  					newTo.setTheoryMarks("");
  					newTo.setPracticalMarks("");
  					newTo.setExamName(code);
  					examMarksEntryDetailsTOList.add(newTo);
  				}
  			}
  			examMarkMapNew.put(subjectId, examMarksEntryDetailsTOList);
  		}				
		
		
		log.info("Exit convertMarkListToMap of StudentAttendanceSummaryHelper");
		return examMarkMapNew;
	}
	
	public String getStudentAbsenceInfoAdditionalQueryForConducted(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
		+ "attendanceStudents.attendance.subject.name, " 
		+ "attendanceStudents.attendance.attendanceType.id, " 
		+ "attendanceStudents.attendance.attendanceType.name, " 
		+ "attendanceStudents.attendance.id, " 
		+ "attendanceStudents.student.id, " 
		+ "sum(attendanceStudents.attendance.hoursHeld)" 
		+ " from Student student " 
		+ "inner join student.attendanceStudents attendanceStudents " 
		+ "inner join attendanceStudents.attendance.attendanceClasses ac "
		//mary code
		+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 1  " //mary code end
/*		+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
		+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
		+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
		+ "student.id = " + studentId
		+ " and ac.classSchemewise.classes.id = " + classId
		+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
		+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	public String getStudentAbsenceInfoAdditionalQueryForPresent1(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		String studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
			+ "attendanceStudents.attendance.subject.name, " 
			+ "attendanceStudents.attendance.attendanceType.id, " 
			+ "attendanceStudents.attendance.attendanceType.name, " 
			+ "attendanceStudents.attendance.id, " 
			+ "attendanceStudents.student.id, " 
			+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then attendanceStudents.attendance.hoursHeld else 0 end)"
			+ "from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			/*+ "where attendanceStudents.attendance.isMonthlyAttendance = 0 "*/ 
			+  "where attendanceStudents.attendance.subject.isAdditionalSubject = 1  "
			+ " inner join attendanceStudents.attendance.attendanceClasses ac "
			+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
			+ "and  attendanceStudents.attendance.isCanceled = 0 and "
//			+ " (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) and "
			+ "student.id = " + studentId
			+ " and ac.classSchemewise.classes.id = " + classId
			+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
			+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}
	
	
	
	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToList(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,int studentId,StudentWiseAttendanceSummaryForm attendanceSummaryForm,String mode) throws Exception {
		log.info("Entering into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;
		
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if(!mode.equalsIgnoreCase("Previous")){
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdLogin(subjectString.toString());
		 	}
		 }
	  }else{
		  subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsByClassId(attendanceSummaryForm.getClassesId());
	  }
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			float subjectConductedHrs = 0;
			float presentHrs = 0;
			float absentHrs = 0;
			float SubHrspercentage = 0f;
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						attendanceTypeTO = new AttendanceTypeTO();
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						//attendanceTypeTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
//						totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
						CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
						int acadamicYear = currBo.getAcademicYear();
						int termNo =currBo.getSemesterYearNo();
						Integer subjectId = Integer.parseInt(attendanceTypeTO.getSubjectId());
						String courseId = String.valueOf(currBo.getCurriculumScheme().getCourse().getId());
						List<Object[]> subjectWiseAttendence = studentWiseAttendanceSummaryTransaction.getSubjectWiseAttendenceInHrs(studentId,courseId,classId,acadamicYear,subjectId);
						Iterator<Object[]> itr = subjectWiseAttendence.iterator();
						while(itr.hasNext()){
							Object[] data = itr.next();
							if(subjectId.equals(data[2])){
								if(data[18] != null && !data[18].toString().isEmpty() ){
								subjectConductedHrs = subjectConductedHrs + Float.parseFloat(data[18].toString());
								}
								if(data[19] != null && !data[19].toString().isEmpty() ){
									presentHrs = presentHrs + Float.parseFloat(data[19].toString());
								}
								
							}
						}
						if(subjectConductedHrs != 0 || presentHrs != 0){
						absentHrs = subjectConductedHrs - presentHrs;
						}
						attendanceTypeTO.setConductedClassesHours(String.valueOf(subjectConductedHrs));
						attendanceTypeTO.setClassesPresentHours(String.valueOf(presentHrs));
						attendanceTypeTO.setClassesAbsentHours(String.valueOf(absentHrs));
						if(subjectConductedHrs != 0 || presentHrs != 0 || absentHrs != 0){
							SubHrspercentage = CommonUtil.roundToDecimal(((presentHrs/subjectConductedHrs)*100), 2);
						}
						attendanceTypeTO.setSubjectHrspercentage(String.valueOf(SubHrspercentage));
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(0);
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						/*if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}*/
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
						CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
						int acadamicYear = currBo.getAcademicYear();
						int termNo =currBo.getSemesterYearNo();
						Integer subjectId = Integer.parseInt(attendanceTypeTO.getSubjectId());
						String courseId = String.valueOf(currBo.getCurriculumScheme().getCourse().getId());
						List<Object[]> subjectWiseAttendence = studentWiseAttendanceSummaryTransaction.getSubjectWiseAttendenceInHrs(studentId,courseId,classId,acadamicYear,subjectId);
						Iterator<Object[]> itr = subjectWiseAttendence.iterator();
						while(itr.hasNext()){
							Object[] data = itr.next();
							if(subjectId.equals(data[2])){
								if(data[18] != null && !data[18].toString().isEmpty() && Integer.parseInt(data[18].toString()) != 0){
								subjectConductedHrs = subjectConductedHrs + Float.parseFloat(data[18].toString());
								}
								if(data[19] != null && !data[19].toString().isEmpty() && Integer.parseInt(data[19].toString()) != 0){
									presentHrs = presentHrs + Float.parseFloat(data[19].toString());
								}
								
							}
						}
						if(subjectConductedHrs != 0 || presentHrs != 0){
						absentHrs = subjectConductedHrs - presentHrs;
						}
						attendanceTypeTO.setConductedClassesHours(String.valueOf(subjectConductedHrs));
						attendanceTypeTO.setClassesPresentHours(String.valueOf(presentHrs));
						attendanceTypeTO.setClassesAbsentHours(String.valueOf(absentHrs));
						if(subjectConductedHrs != 0 || presentHrs != 0 || absentHrs != 0){
							SubHrspercentage = CommonUtil.roundToDecimal(((presentHrs/subjectConductedHrs)*100), 2);
						}
						attendanceTypeTO.setSubjectHrspercentage(String.valueOf(SubHrspercentage));
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}
						int classId = studentWiseAttendanceSummaryTransaction.getClassId(studentId);
						CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
						int acadamicYear = currBo.getAcademicYear();
						int termNo =currBo.getSemesterYearNo();
						Integer subjectId = Integer.parseInt(attendanceTypeTO.getSubjectId());
						String courseId = String.valueOf(currBo.getCurriculumScheme().getCourse().getId());
						List<Object[]> subjectWiseAttendence = studentWiseAttendanceSummaryTransaction.getSubjectWiseAttendenceInHrs(studentId,courseId,classId,acadamicYear,subjectId);
						Iterator<Object[]> itr = subjectWiseAttendence.iterator();
						while(itr.hasNext()){
							Object[] data = itr.next();
							if(subjectId.equals(data[2])){
								if(data[18] != null && !data[18].toString().isEmpty() && Integer.parseInt(data[18].toString()) != 0){
								subjectConductedHrs = subjectConductedHrs + Float.parseFloat(data[18].toString());
								}
								if(data[19] != null && !data[19].toString().isEmpty() && Integer.parseInt(data[19].toString()) != 0){
									presentHrs = presentHrs + Float.parseFloat(data[19].toString());
								}
								
							}
						}
						if(subjectConductedHrs != 0 || presentHrs != 0){
						absentHrs = subjectConductedHrs - presentHrs;
						}
						attendanceTypeTO.setConductedClassesHours(String.valueOf(subjectConductedHrs));
						attendanceTypeTO.setClassesPresentHours(String.valueOf(presentHrs));
						attendanceTypeTO.setClassesAbsentHours(String.valueOf(absentHrs));
						if(subjectConductedHrs != 0 || presentHrs != 0 || absentHrs != 0){
							SubHrspercentage = CommonUtil.roundToDecimal(((presentHrs/subjectConductedHrs)*100), 2);
						}
						attendanceTypeTO.setSubjectHrspercentage(String.valueOf(SubHrspercentage));
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
			
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		log.info("Leaving into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		/*if(studentWiseSubjectSummaryTOList!=null && !studentWiseSubjectSummaryTOList.isEmpty()){
			totalPercentage=totalPercentage/studentWiseSubjectSummaryTOList.size();
			attendanceSummaryForm.setTotalPercentage(String.valueOf(totalPercentage));
		}*/
		totalPercentage=(totalPresent/totalConducted * 100);
		 DecimalFormat df = new DecimalFormat("#.##");
		 String s=df.format(totalPercentage);
		attendanceSummaryForm.setTotalPercentage(s);
		attendanceSummaryForm.setTotalConducted(String.valueOf(totalConducted));
		attendanceSummaryForm.setTotalPresent(String.valueOf(totalPresent));
		attendanceSummaryForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}
	
	

	

	public List<StudentWiseSubjectSummaryTO> convertFromAttendanceSummaryMapToListView(
			Map<String, StudentWiseSubjectSummaryTO> classesConductedMap,
			Map<String, StudentWiseSubjectSummaryTO> classesPresentMap,int studentId,DisciplinaryDetailsForm attendanceSummaryForm) throws Exception {
		log.info("Entering into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		Map<String, StudentWiseSubjectSummaryTO> summaryMap = new HashMap<String, StudentWiseSubjectSummaryTO>();		
		List<AttendanceTypeTO> typeList = null;
		StudentWiseSubjectSummaryTO summaryTO = null;
		AttendanceTypeTO attendanceTypeTO = null;	
		float totalPercentage=0;
		float totalConducted = 0;
		float totalPresent = 0;
		float totalAbscent=0;
		IStudentWiseAttendanceSummaryTransaction studentWiseAttendanceSummaryTransaction = new StudentWiseAttendanceSummaryTransactionImpl();
		Student student = studentWiseAttendanceSummaryTransaction.getStudentByRegdRollNo(studentId);
		HashMap<Integer, String> subjectMap = new HashMap<Integer, String>();
		if( student.getAdmAppln().getApplicantSubjectGroups()!= null){
			Set<ApplicantSubjectGroup> applSubjectGroup = student.getAdmAppln().getApplicantSubjectGroups();
			if(applSubjectGroup!= null && applSubjectGroup.size() > 0){
				int len = applSubjectGroup.size();
				Iterator<ApplicantSubjectGroup> appSetIt = applSubjectGroup.iterator();
				String [] subjGroupString = new String[applSubjectGroup.size()];
				int count = 0;
				String commaString;
				while (appSetIt.hasNext()) {
					ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) appSetIt
							.next();

					if(count + 1 < len){
						commaString = ",";
					}else{
						commaString = "";
					}
					

					subjGroupString[count] = Integer.toString(applicantSubjectGroup.getSubjectGroup().getId()) + commaString;
					count++;
				}
				StringBuffer subjectString = new StringBuffer();
				if(subjGroupString!= null && subjGroupString.length > 0){
					for (int x = 0; x < subjGroupString.length; x++){
						subjectString.append(subjGroupString[x]);	
					}
					
				}
				subjectMap = studentWiseAttendanceSummaryTransaction.getSubjectsBySubjectGroupIdLogin(subjectString.toString());
			}
		}
		Iterator<String> subjectIdIterator = classesConductedMap.keySet()
				.iterator();		
		while (subjectIdIterator.hasNext()) {
			String subjectAttendanceTypeId = (String) subjectIdIterator.next();			
			StudentWiseSubjectSummaryTO conductedSubjectsummaryTO = classesConductedMap.get(subjectAttendanceTypeId);
			StudentWiseSubjectSummaryTO presentSubjectsummaryTO = classesPresentMap.get(subjectAttendanceTypeId);
			
			if(!subjectMap.isEmpty() && subjectMap.containsKey(Integer.parseInt(conductedSubjectsummaryTO.getSubjectID()))){
				if(classesPresentMap!=null && !classesPresentMap.isEmpty()){
					if(classesPresentMap.containsKey(subjectAttendanceTypeId) && !summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						typeList = new ArrayList<AttendanceTypeTO>();					
						summaryTO = new StudentWiseSubjectSummaryTO();
						
						if(conductedSubjectsummaryTO.getSubjectName()!=null){
							summaryTO.setSubjectName(conductedSubjectsummaryTO.getSubjectName());
							summaryTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						}
						//-------
						summaryTO.setSubjectID(conductedSubjectsummaryTO.getSubjectID());
						//-----------
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						//attendanceTypeTO.setIsAdditionalSubject(conductedSubjectsummaryTO.getIsAdditionalSubject());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(
								conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						totalPercentage=totalPercentage+percentage;
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
//						totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
						summaryMap.put(conductedSubjectsummaryTO.getSubjectID(), summaryTO);
					}
					
					else if(!classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(0);
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						/*if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}*/
						totalAbscent=totalAbscent+attendanceTypeTO.getClassesAbsent();
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
					else if(classesPresentMap.containsKey(subjectAttendanceTypeId) && summaryMap.containsKey(conductedSubjectsummaryTO.getSubjectID())){
						
						summaryTO = summaryMap.get(conductedSubjectsummaryTO.getSubjectID());
						typeList = summaryTO.getAttendanceTypeList();
						
						attendanceTypeTO = new AttendanceTypeTO();
						
						attendanceTypeTO.setAttendanceID(conductedSubjectsummaryTO.getAttendanceID());
						attendanceTypeTO.setStudentId(conductedSubjectsummaryTO.getStudentId());
						attendanceTypeTO.setSubjectId(conductedSubjectsummaryTO.getSubjectID());
						attendanceTypeTO.setAttendanceTypeID(conductedSubjectsummaryTO.getAttendanceTypeID());
						
						if(conductedSubjectsummaryTO.getAttendanceTypeName()!=null){
							attendanceTypeTO.setAttendanceTypeName(conductedSubjectsummaryTO.getAttendanceTypeName());
						}
						attendanceTypeTO.setConductedClasses(conductedSubjectsummaryTO.getConductedClasses());
						
						attendanceTypeTO.setClassesPresent(presentSubjectsummaryTO.getConductedClasses());
						attendanceTypeTO.setClassesAbsent(conductedSubjectsummaryTO.getConductedClasses()-
								presentSubjectsummaryTO.getConductedClasses());
						
						float cp=attendanceTypeTO.getClassesPresent();
						float cc=attendanceTypeTO.getConductedClasses();
						float percentage = (cp/cc* 100);
						DecimalFormat df = new DecimalFormat("#.##");
						attendanceTypeTO.setPercentage(Float.parseFloat(df.format(percentage)));
						totalConducted = totalConducted + conductedSubjectsummaryTO.getConductedClasses();
						totalPresent = totalPresent + attendanceTypeTO.getClassesPresent();
						if(conductedSubjectsummaryTO!=null ){
							if(presentSubjectsummaryTO!=null)
								totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses()-presentSubjectsummaryTO.getConductedClasses());
						}else{
							totalAbscent=totalAbscent+(conductedSubjectsummaryTO.getConductedClasses());
						}
						typeList.add(attendanceTypeTO);
						summaryTO.setAttendanceTypeList(typeList);
					}
				}
			}
			
		}
		List<StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryTOList = new ArrayList<StudentWiseSubjectSummaryTO>();
		studentWiseSubjectSummaryTOList.addAll(summaryMap.values());
		log.info("Leaving into convertFromAttendanceSummaryMapToList of StudentAttendanceSummaryHelper");
		/*if(studentWiseSubjectSummaryTOList!=null && !studentWiseSubjectSummaryTOList.isEmpty()){
			totalPercentage=totalPercentage/studentWiseSubjectSummaryTOList.size();
			attendanceSummaryForm.setTotalPercentage(String.valueOf(totalPercentage));
		}*/
		totalPercentage=(totalPresent/totalConducted * 100);
		 DecimalFormat df = new DecimalFormat("#.##");
		 String s=df.format(totalPercentage);
		attendanceSummaryForm.setTotalPercentage(s);
		attendanceSummaryForm.setTotalConducted(totalConducted);
		attendanceSummaryForm.setTotalPresent(totalPresent);
		attendanceSummaryForm.setTotalAbscent(String.valueOf(totalAbscent));
		return studentWiseSubjectSummaryTOList;
	}
	/**
	 * @param studentId
	 * @param classId
	 * @return
	 */
	public String getPreviousStudentAbsenceQueryForConducted(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
		int acadamicYear = currBo.getAcademicYear();
		int termNo = currBo.getSemesterYearNo();
		String studentWiseAttendanceSummary = "";
		if(acadamicYear>2017){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(attendanceStudents.attendance.hoursHeld)" 
				+ "sum(case when (attendanceStudents.attendance.hoursHeld is not null) then attendanceStudents.attendance.hoursHeld else 0 end) as HH "
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";

		}
		else if(acadamicYear==2017 && (termNo!=1 && termNo!=3 && termNo!=5)){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(attendanceStudents.attendance.hoursHeld)" 
				+ "sum(case when (attendanceStudents.attendance.hoursHeld is not null) then attendanceStudents.attendance.hoursHeld else 0 end) as HH "
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";

		}
		else{
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(attendanceStudents.attendance.hoursHeld)" 
				+ "sum(case when (attendanceStudents.attendance.hoursHeld is not null) then 1 else 0 end) as HH "
				+ " from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ "inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and " 
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	/**
	 * @param studentId
	 * @param classId
	 * @return
	 */
	public String getPreviousStudentAbsenceQueryForPresent1(int studentId, int classId) {
		log.info("Entering into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		CurriculumSchemeDuration currBo = CurriculumSchemeHandler.getInstance().getCurriculumSchemeDurationByClassId(classId);
		int acadamicYear = currBo.getAcademicYear();
		int termNo = currBo.getSemesterYearNo();
		String studentWiseAttendanceSummary = "";
		if(acadamicYear>2017){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then attendanceStudents.attendance.hoursHeld else 0 end)"
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then "
			    + "(case when (attendanceStudents.attendance.hoursHeld is not null) then attendanceStudents.attendance.hoursHeld else 0 end) else 0 end) as HA "
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else if(acadamicYear==2017  && (termNo!=1 && termNo!=3 && termNo!=5)){
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then attendanceStudents.attendance.hoursHeld else 0 end)"
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then "
			    + "(case when (attendanceStudents.attendance.hoursHeld is not null) then attendanceStudents.attendance.hoursHeld else 0 end) else 0 end) as HA "
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		else{
			studentWiseAttendanceSummary = "select attendanceStudents.attendance.subject.id, " 
				+ "attendanceStudents.attendance.subject.name, " 
				+ "attendanceStudents.attendance.attendanceType.id, " 
				+ "attendanceStudents.attendance.attendanceType.name, " 
				+ "attendanceStudents.attendance.id, " 
				+ "attendanceStudents.student.id, " 
				//+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then attendanceStudents.attendance.hoursHeld else 0 end)"
				+ "sum(case when (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1) then "
			    + "(case when (attendanceStudents.attendance.hoursHeld is not null) then 1 else 0 end) else 0 end) as HA "
				+ "from Student student " 
				+ "inner join student.attendanceStudents attendanceStudents " 
				+ " inner join attendanceStudents.attendance.attendanceClasses ac "
				+ "where attendanceStudents.attendance.isActivityAttendance = 0  " 
				+ "and  attendanceStudents.attendance.isCanceled = 0 and "
				+ "student.id = " + studentId
				+ " and ac.classSchemewise.classes.id = " + classId
				+ "group by attendanceStudents.attendance.attendanceType.id, attendanceStudents.attendance.subject.id "
				+ "order by attendanceStudents.attendance.subject.id, attendanceStudents.attendance.attendanceType.id";
		}
		
		log.info("Leaving into getStudentAbsenceInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
			return studentWiseAttendanceSummary;
	}
	/**
	 * 
	 * @param studentId
	 * @param currentYear 
	 * @param classId 
	 * @returns activity conducted summary information query
	 */

	public String getPreviousActivityInformationSummaryQueryForConducted(int studentId, int currentYear, int classId) {
		log.info("Entering into getActivityInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		
		String studentWiseActivityConductedAttendanceSummary = "select activity.id, " 
			+ "activity.name, "
			+ "attendanceStudents.student.id, "
			+ "sum(attendance.hoursHeld)," 
			+ "attendance.attendanceType.name"
			+ " from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			+"inner join attendanceStudents.attendance attendance "
			+"left join attendance.activity activity "
			+"left join attendance.subject subject "
			+"inner join attendance.attendanceClasses attendanceClasses "
			+"inner join attendanceClasses.classSchemewise classSchemewise "
			+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
			+"where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
			+"and (attendance.isActivityAttendance=1 or (activity.name=null and subject.name=null)) " 
			+"and  attendanceStudents.attendance.isCanceled = 0 "
			+" and student.id = " + studentId 
			+" and curriculumSchemeDuration.academicYear="+currentYear
			+ " and classSchemewise.classes.id = " + classId
			+" group by attendanceStudents.attendance.activity.id "
			+"order by attendanceStudents.attendance.activity.id";
		log.info("Leaving into getActivityInformationSummaryQueryForConducted of StudentAttendanceSummaryHelper");
		return studentWiseActivityConductedAttendanceSummary;
	}

	/**
	 * 
	 * @param studentId
	 * @param currentYear 
	 * @param classId 
	 * @returns activity present summary information query
	 */
	public String getPreviousActivityInformationSummaryQueryForPresent(int studentId, int currentYear, int classId) {
		log.info("Entering into getActivityInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		String studentWiseActivityPresentAttendanceSummary = "select activity.id, " 
			+ "activity.name, "
			+ "attendanceStudents.student.id, "
			+ "sum(attendance.hoursHeld)," 
			+ "attendance.attendanceType.name"
			+ " from Student student " 
			+ "inner join student.attendanceStudents attendanceStudents " 
			+"inner join attendanceStudents.attendance attendance "
			+"left join attendance.activity activity "
			+"left join attendance.subject subject "
			+"inner join attendance.attendanceClasses attendanceClasses "
			+"inner join attendanceClasses.classSchemewise classSchemewise "
			+"inner join classSchemewise.curriculumSchemeDuration curriculumSchemeDuration "
			+"where attendanceStudents.attendance.isMonthlyAttendance = 0 "  
			+"and (attendance.isActivityAttendance=1 or (activity.name=null and subject.name=null)) " 
			+"and  attendanceStudents.attendance.isCanceled = 0 and (attendanceStudents.isPresent = 1 or attendanceStudents.isCoCurricularLeave = 1)"
			+" and student.id = " + studentId 
			+" and curriculumSchemeDuration.academicYear="+currentYear
			+ " and classSchemewise.classes.id = " + classId
			+" group by attendanceStudents.attendance.activity.id "
			+"order by attendanceStudents.attendance.activity.id";
		log.info("Leaving into getActivityInformationSummaryQueryForPresent of StudentAttendanceSummaryHelper");
		return studentWiseActivityPresentAttendanceSummary;
	}
	/**
	 * @param attendanceSummaryForm
	 * @return
	 */
	public String getPreviousAbsenceSearchCriteria(	StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		StringBuffer absenceSearchCriteria = new StringBuffer("select attendancestudent from AttendanceStudent attendancestudent" 
		+ " join attendancestudent.attendance att" 
		+ " join att.attendanceClasses attcls " 
		+ " join attcls.classSchemewise cls" 
		+ " where attendancestudent.attendance.subject.id = " + Integer.parseInt(attendanceSummaryForm.getSubjectId())
		+ " and cls.classes.id = " + Integer.parseInt(attendanceSummaryForm.getClassesId())
		+ " and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID()) 
		+ " and attendancestudent.isPresent = 0 and  attendancestudent.attendance.isCanceled = 0");
		if(attendanceSummaryForm.getAttendanceTypeId()!= null && !attendanceSummaryForm.getAttendanceTypeId().trim().isEmpty()){
			absenceSearchCriteria.append(" and attendancestudent.attendance.attendanceType.id = " + Integer.parseInt(attendanceSummaryForm.getAttendanceTypeId())); 
		}
		absenceSearchCriteria.append(" group by attendancestudent.id order by attendancestudent.attendance.attendanceDate");
		log.info("Leaving into getAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return absenceSearchCriteria.toString();
	}

	public String getPreviousActivityAbsenceSearchCriteria(StudentWiseAttendanceSummaryForm attendanceSummaryForm) {
		log.info("Entering into getActivityAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		Integer activityId=null;
		if(attendanceSummaryForm.getActivityId()!=null && !attendanceSummaryForm.getActivityId().isEmpty())
		 activityId=Integer.parseInt(attendanceSummaryForm.getActivityId());
		String activityAbsenceSearchCriteria = "select attendancestudent from AttendanceStudent attendancestudent "
		     + " join attendancestudent.attendance att" 
		     + " join att.attendanceClasses attcls " 
		     + " join attcls.classSchemewise cls " ;
			if(activityId!=null){
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "where attendancestudent.attendance.activity.id = " + activityId;
			}else{
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "where attendancestudent.attendance.activity.id is null " ;
			}
		activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ " and attendancestudent.student.id = " + Integer.parseInt(attendanceSummaryForm.getStudentID())
		+ " and attendancestudent.isPresent = 0 ";
		if(activityId!=null){
			activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "and attendancestudent.attendance.isActivityAttendance = 1 ";
		}else{
			activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ "and attendancestudent.attendance.isActivityAttendance = 0 ";
			if(attendanceSummaryForm.getAttendanceTypeName()!=null && !attendanceSummaryForm.getAttendanceTypeName().isEmpty())
				activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+" and attendancestudent.attendance.attendanceType.name='"+attendanceSummaryForm.getAttendanceTypeName()+"' ";
		}
		activityAbsenceSearchCriteria=activityAbsenceSearchCriteria+ " and cls.classes.id = " + Integer.parseInt(attendanceSummaryForm.getClassesId())
		+ " group by attendancestudent.id order by attendancestudent.attendance.attendanceDate";
		log.info("Leaving into getActivityAbsenceSearchCriteria of StudentAttendanceSummaryHelper");
		return activityAbsenceSearchCriteria;
	}
	/**
	 * 
	 * @param attendanceStudentList
	 * @returns absence period informations
	 */
	public List<PeriodTO> PreviousAbsencePeriodInformations(
			List<AttendanceStudent> attendanceStudentList, StudentWiseAttendanceSummaryForm attendanceSummaryForm, boolean isSubjectAttendance,List<String> period) throws Exception{
		log.info("Entering into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		Map<Date, PeriodTO> periodMap = new LinkedHashMap<Date, PeriodTO>();
		PeriodTO periodTO = null;
		if(attendanceStudentList!=null){
			Iterator<AttendanceStudent> iterator = attendanceStudentList.iterator();
			while (iterator.hasNext()) {
				AttendanceStudent attendanceStudent = iterator.next();
				if(attendanceStudent.getAttendance()!=null){
					if(isSubjectAttendance){
						if(attendanceStudent.getAttendance().getSubject()!=null && attendanceStudent.getAttendance().getSubject().getName()!=null){
							attendanceSummaryForm.setSubjectName(attendanceStudent.getAttendance().getSubject().getName());
						}
					}
					else{
						if(attendanceStudent.getAttendance().getActivity()!=null && attendanceStudent.getAttendance().getActivity().getName()!=null){
							attendanceSummaryForm.setActivityName(attendanceStudent.getAttendance().getActivity().getName());
						}
					}
					periodTO = new PeriodTO();
					periodTO.setDate((CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate
					(attendanceStudent.getAttendance().getAttendanceDate()!=null ? 
					attendanceStudent.getAttendance().getAttendanceDate():null), "dd-MMM-yyyy","dd-MMM-yyyy")));;

					if(attendanceStudent.getAttendance().getAttendancePeriods()!=null){
						Iterator<AttendancePeriod> periodIterator = attendanceStudent.getAttendance().getAttendancePeriods().iterator();
						while (periodIterator.hasNext()) {
							AttendancePeriod attendancePeriod = periodIterator.next();
							if(attendancePeriod.getPeriod()!=null && attendancePeriod.getPeriod().getPeriodName()!=null){
								if(period.contains(attendancePeriod.getPeriod().getPeriodName())){
								if(attendanceStudent.getAttendance().getAttendanceDate()!=null){
									if(!periodMap.containsKey(attendanceStudent.getAttendance().getAttendanceDate())){
										periodTO.setPeriodName(attendancePeriod.getPeriod().getPeriodName());
										if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
											periodTO.setName("Co-CurricularLeave");
										}else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
											periodTO.setName("Leave");
										}else{
											periodTO.setName("-");
										}
										periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									}
									else{
										if(periodTO.getPeriodName()!=null && !periodTO.getPeriodName().isEmpty()){
										   if(!periodTO.getPeriodName().contains(attendancePeriod.getPeriod().getPeriodName())){
										     periodTO = periodMap.get(attendanceStudent.getAttendance().getAttendanceDate());
										     periodTO.setPeriodName(periodTO.getPeriodName()+", "+attendancePeriod.getPeriod().getPeriodName());
										     if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
										  	 periodTO.setName(periodTO.getName()+",Co-CurricularLeave");
										     }else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
										  	 periodTO.setName(periodTO.getName()+",Leave");
										     }else{
											 periodTO.setName(periodTO.getName()+",-");
										    }
										  periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									    }
									  }else{
										     periodTO = periodMap.get(attendanceStudent.getAttendance().getAttendanceDate());
										     periodTO.setPeriodName(periodTO.getPeriodName()+", "+attendancePeriod.getPeriod().getPeriodName());
										     if(attendanceStudent.getIsCoCurricularLeave()!=null && attendanceStudent.getIsCoCurricularLeave()){
										  	 periodTO.setName(periodTO.getName()+",Co-CurricularLeave");
										     }else if(attendanceStudent.getIsOnLeave()!=null && attendanceStudent.getIsOnLeave()){
										  	 periodTO.setName(periodTO.getName()+",Leave");
										     }else{
											 periodTO.setName(periodTO.getName()+",-");
										    }
										  periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
									    
									  }
								    }
								  }
								}
							}
							
						}
					}
					periodMap.put(attendanceStudent.getAttendance().getAttendanceDate(), periodTO);
				}
			}
		}
		List<PeriodTO> periodList = new ArrayList<PeriodTO>(); 
		periodList.addAll(periodMap.values());
		log.info("Leaving into populateAbsencePeriodInformations of StudentAttendanceSummaryHelper");
		return periodList;
	}
	
	
	
	
	
public void convertBoToSessionAttendance(List dateList,List sessionAttendanceList,StudentWiseAttendanceSummaryForm objForm)throws Exception {
		
		if (dateList != null && dateList.size() > 0 && sessionAttendanceList != null && sessionAttendanceList.size() > 0) { 
			
			int amsestot=0;
			int pmsestot=0;
			int amsesheld=0;
			int pmsesheld=0;
			int amattabs=0;
			int pmattabs=0;
			int amattabsheld=0;
			int pmattabsheld=0;
			
			int amattpr=0;
			int pmattpr=0;
			
			List<SessionAttendnceToAm> lam=new LinkedList<SessionAttendnceToAm>();
			List<SessionAttendnceToPm> lpm=new LinkedList<SessionAttendnceToPm>();
			
			String periodsam="";
			String leavesam="";
			String periodspm="";
			String leavespm="";
			
			Iterator itr=dateList.iterator();
			while(itr.hasNext()){
				
				
				Object[] dtobj = (Object[]) itr.next();
				String date=dtobj[0].toString();
				amsesheld=0;
				pmsesheld=0;
				amattabsheld=0;
				pmattabsheld=0;
				
				
				SessionAttendnceToAm toam=new SessionAttendnceToAm();
				toam.setAttdate(date);
				
				List<String> pnamesam=new LinkedList<String>();
				List<String> coLeavesam=new LinkedList<String>();
				
				SessionAttendnceToPm topm=new SessionAttendnceToPm();
				topm.setAttdate(date);
				
				List<String> pnamespm=new LinkedList<String>();
				List<String> coLeavespm=new LinkedList<String>();
				
				
				
				Iterator i=sessionAttendanceList.iterator();
				while(i.hasNext()){
					
				
					Object[] attobj = (Object[]) i.next();
					
					objForm.setClassName(attobj[8].toString());
					
					if(date.equalsIgnoreCase(attobj[0].toString())){
						
						if(attobj[4].toString().equalsIgnoreCase("am")){
							amsesheld=1;
							
							if((Boolean)attobj[5]){
								
							}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
								
								if((Boolean)attobj[7]){
									String l="Co-CurricullarLeave";
									coLeavesam.add(l);
									String p=attobj[2].toString();
									pnamesam.add(p);
									
									periodsam=periodsam+p+",";
									leavesam=leavesam+"Co-CurricullarLeave,";
								}
								if((Boolean)attobj[6]){
									String l="On-Leave";
									coLeavesam.add(l);
									String p=attobj[2].toString();
									pnamesam.add(p);
									
									periodsam=periodsam+p+",";
									leavesam=leavesam+"On-Leave,";
								}
								
								
							}else{
								amattabsheld=1;
								System.out.println("-----absent am----- ");
								System.out.println(attobj[0].toString());
								
								String p=attobj[2].toString();
								pnamesam.add(p);
								periodsam=periodsam+p+",";
								leavesam=leavesam+"-,";
							}
							
							
						}else{
							pmsesheld=1;
							
								if((Boolean)attobj[5]){
									
								}else if((Boolean)attobj[6]||(Boolean)attobj[7]){
									
									if((Boolean)attobj[7]){
										String l="CoCurricullarLeave";
										coLeavespm.add(l);
										String p=attobj[2].toString();
										pnamespm.add(p);
										
										periodspm=periodspm+p+",";
										leavespm=leavespm+"Co-CurricullarLeave,";
									}
									if((Boolean)attobj[6]){
										String l="On-Leave";
										coLeavespm.add(l);
										String p=attobj[2].toString();
										pnamespm.add(p);
										
										periodspm=periodspm+p+",";
										leavespm=leavespm+"On-Leave,";
									}
								}else{
									pmattabsheld=1;
									System.out.println("-----absent pm----- ");
									System.out.println(attobj[0].toString());
									
									String p=attobj[2].toString();
									pnamespm.add(p);
									
									periodspm=periodspm+p+",";
									leavespm=leavespm+"-,";
								}
								
						}
						
						
							if(pnamesam.size()!=0)
							toam.setPnames(pnamesam);
							if(coLeavesam.size()!=0)
							toam.setCoLeaves(coLeavesam);
							if(pnamespm.size()!=0)
							topm.setPnames(pnamespm);
							if(coLeavespm.size()!=0)
							topm.setCoLeaves(coLeavespm);
							
							if(!periodsam.equalsIgnoreCase(""))
							toam.setPeriods(periodsam);
							if(!periodspm.equalsIgnoreCase(""))
							topm.setPeriods(periodspm);
							if(!leavesam.equalsIgnoreCase(""))
							toam.setLeaves(leavesam);
							if(!periodspm.equalsIgnoreCase(""))
							topm.setLeaves(leavespm);
					}
				
				}
				
				//raghu write newly if they didnot take attendance for any session we consider student was present for that session
				
				/*if(amsesheld!=1){
					amsesheld=1;
				}
				if(pmsesheld!=1){
					pmsesheld=1;
				}*/
				
				amattabs=amattabs+amattabsheld;
				pmattabs=pmattabs+pmattabsheld;
				amsestot=amsestot+amsesheld;
				pmsestot=pmsestot+pmsesheld;
				
				if((toam.getPnames()!=null && toam.getPnames().size()!=0 )|| (toam.getCoLeaves()!=null && toam.getCoLeaves().size()!=0))
					lam.add(toam);
				if((topm.getPnames()!=null && topm.getPnames().size()!=0 )|| (topm.getCoLeaves()!=null && topm.getCoLeaves().size()!=0))
					lpm.add(topm);
				
				periodsam="";
				leavesam="";
				periodspm="";
				leavespm="";
			}
			
			amattpr=amsestot-amattabs;
			pmattpr=pmsestot-pmattabs;
			float amper=CommonUtil.roundToDecimal((((float)amattpr/amsestot)*100),2);
			float pmper=CommonUtil.roundToDecimal((((float)pmattpr/pmsestot)*100),2);
			float totper=CommonUtil.roundToDecimal((amper+pmper)/2,2);
			float totper1=CommonUtil.roundToDecimal(((float)(amattpr+pmattpr)/(amsestot+pmsestot))*100, 2);
			
		System.out.println(amsestot+"-----tot----- "+pmsestot);
		System.out.println(amattpr+"-----pr----- "+pmattpr);
		System.out.println(amattabs+"----abs------ "+pmattabs);
		System.out.println((amper)+"----per------ "+(pmper));
		System.out.println((totper)+"----totper------ "+(totper1));
		
		objForm.setTotalPercentage(new Float(totper1).toString());
		objForm.setTotalPresent(new Integer((amattpr+pmattpr)).toString());
		objForm.setTotalConducted(new Integer((amsestot+pmsestot)).toString());
		objForm.setTotalAbscent(new Integer((amattabs+pmattabs)).toString());
		
		objForm.setAmList(lam);
		objForm.setPmList(lpm);
		objForm.setTotpmattabs(new Integer(pmattabs).toString());
		objForm.setTotamattabs(new Integer(amattabs).toString());
		objForm.setTotamattper(new Float(amper).toString());
		objForm.setTotpmattper(new Float(pmper).toString());
		objForm.setAm("Morning Session");
		objForm.setPm("AfterNoon Session");
		objForm.setTotpmattcon(new Integer(pmsestot).toString());
		objForm.setTotamattcon(new Integer(amsestot).toString());
		objForm.setTotamattpre(new Float(amattpr).toString());
		objForm.setTotpmattpre(new Float(pmattpr).toString());
		
		}
		
		
		
	}




	public void getTeacherNotTakenAttendanceSummary(List<Object[]> dateList,StudentWiseAttendanceSummaryForm attendanceSummaryForm)throws Exception {
		
		List<StudentAttendanceTO> list=new LinkedList<StudentAttendanceTO>();
		if(dateList!=null && dateList.size()!=0){
			Iterator  subItr = dateList.iterator();
			while (subItr.hasNext()) {
				StudentAttendanceTO to=new StudentAttendanceTO();
				Object[] obj = (Object[]) subItr.next();
				
				to.setDay(obj[0].toString());
				to.setTeacherName(obj[1].toString());
				to.setSubjectName(obj[2].toString());
				to.setPeriodName(obj[3].toString());
				to.setDate(obj[4].toString());
				list.add(to);
			}
			attendanceSummaryForm.setAttDateList(list);
		}
		
	}

	public float getTotalWorkingDays(List<Object[]> totalAttendence) throws Exception {
		float totalWorkingDays = 0;
		Iterator<Object[]> itr = totalAttendence.iterator();
		while(itr.hasNext()){
			Object[] data = itr.next();
			if(data[5]!= null && !data[5].toString().isEmpty()){
				totalWorkingDays = totalWorkingDays + Float.parseFloat(data[5].toString());
			}
		}
		return totalWorkingDays;
	}

	public float getTotalPresentDays(List<Object[]> totalAttendence) throws Exception {
		float totalPresentDays = 0;
		float presentDays = 0;
		float halfDays = 0;
		Iterator<Object[]> itr = totalAttendence.iterator();
		while(itr.hasNext()){
			Object[] data = itr.next();
			if(data[22] != null && !data[22].toString().isEmpty()){
				presentDays = presentDays + Float.parseFloat(data[22].toString());
			}
			if(data[23] != null && !data[23].toString().isEmpty()){
				halfDays = halfDays + Float.parseFloat(data[23].toString());
			}
			totalPresentDays = presentDays + halfDays;
			
		}
		return totalPresentDays;
	}

	
	
	
	
}

