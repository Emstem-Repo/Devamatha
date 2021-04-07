package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.reports.StudentWiseAttendanceSummaryReportTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.TeacherReportComparator;

public class StudentWiseAttendanceSummaryHelper {
	private static final Log log = LogFactory.getLog(StudentWiseAttendanceSummaryHelper.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	private static volatile StudentWiseAttendanceSummaryHelper studentWiseAttendanceSummaryHelper = null;
	
	private StudentWiseAttendanceSummaryHelper() {
		
	}
	
	public static StudentWiseAttendanceSummaryHelper getInsatnce() {
		if(studentWiseAttendanceSummaryHelper == null) {
			studentWiseAttendanceSummaryHelper = new StudentWiseAttendanceSummaryHelper();
		}
		return studentWiseAttendanceSummaryHelper;
	}
	
	/**
	 * Constructs the search query for student wise attendance summary information summary for conducted classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQueryForConducted(
			StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm) {
		log.info("Entering into getAbsenceInformationSummaryQueryForConducted of StudentWiseAttendanceSummaryHelper");
		String searchCriteria = getCommonQuery(studentWiseAttendanceSummaryForm);
		String studentWiseAttendanceSummary = "	select student, sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.attendance  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 " +
						" and attendanceStudents.student.admAppln.isCancelled = 0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null) "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ studentWiseAttendanceSummaryForm.getAcademicYear()
				+ searchCriteria
				+ "  group by student.id,attendanceStudents.attendance.subject.id ";
		log.info("Leaving into getAbsenceInformationSummaryQueryForConducted of StudentWiseAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	
	/**
	 * Constructs the search query for student wise attendance summary information summary for present classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQueryForPresent(
			StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm) {
		log.info("Entering into getAbsenceInformationSummaryQueryForPresent of StudentWiseAttendanceSummaryHelper");
		String searchCriteria = getCommonQuery(studentWiseAttendanceSummaryForm);
		String studentWiseAttendanceSummary = "	select student,  sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.attendance  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 " +
						" and attendanceStudents.student.admAppln.isCancelled = 0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null) "
				+ " and attendanceStudents.isPresent = 1 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ studentWiseAttendanceSummaryForm.getAcademicYear()
				+ searchCriteria
				+ "  group by student.id,attendanceStudents.attendance.subject.id ";
		log.info("Leaving into getAbsenceInformationSummaryQueryForPresent of StudentWiseAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	
	/**
	 * Constructs the search query for student wise attendance summary information summary for isonleave classes.
	 * 
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQueryForIsOnLeave(
			StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm) {
		log.info("Entering into getAbsenceInformationSummaryQueryForIsOnLeave of StudentWiseAttendanceSummaryHelper");
		String searchCriteria = getCommonQuery(studentWiseAttendanceSummaryForm);
		String studentWiseAttendanceSummary = "	select student,  sum(attendanceStudents.attendance.hoursHeld),attendanceStudents.attendance  from Student student "
				+ " inner join student.attendanceStudents attendanceStudents "
				+ "  where attendanceStudents.attendance.isMonthlyAttendance = 0  "
				+ "  and   attendanceStudents.attendance.isActivityAttendance = 0 " +
						" and attendanceStudents.student.admAppln.isCancelled = 0 and (attendanceStudents.student.isHide = 0 or attendanceStudents.student.isHide is null)"
				+ "  and   attendanceStudents.isOnLeave = 1 "
				+ "  and  attendanceStudents.attendance.isCanceled = 0 and student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ studentWiseAttendanceSummaryForm.getAcademicYear()
				+ searchCriteria
				+ "  group by student.id,attendanceStudents.attendance.subject.id ";
		log.info("Leaving into getAbsenceInformationSummaryQueryForIsOnLeave of StudentWiseAttendanceSummaryHelper");
		return studentWiseAttendanceSummary;
	}
	
	/**
	 * Converts from Bo to Map of studentid and StudentWiseAttendanceSummaryReportTO object.
	 * @param studentInformationBoList
	 * @return
	 */
	public Map<Integer,StudentWiseAttendanceSummaryReportTO> convertBoToTo(
			List studentInformationBoList) {
		log.info("Entering into convertBoToTo of StudentWiseAttendanceSummaryHelper");
		Map<Integer, StudentWiseAttendanceSummaryReportTO> studentInfoMap = new HashMap<Integer, StudentWiseAttendanceSummaryReportTO>();

		if (studentInformationBoList != null) {
			Iterator studentInformationIterator = studentInformationBoList
					.iterator();
			while (studentInformationIterator.hasNext()) {
				Object[] object = (Object[]) studentInformationIterator.next();
				Student student = ((Student) object[0]);
				Attendance attendance = ((Attendance) object[2]);
				StudentWiseAttendanceSummaryReportTO studentWiseAttendanceSummaryReportTO = null;
				if (studentInfoMap.containsKey(student.getId())) {

					 studentWiseAttendanceSummaryReportTO = studentInfoMap
							.get(student.getId());

					StudentWiseSubjectSummaryTO StudentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();

					if (attendance.getSubject() != null) {
						StudentWiseSubjectSummaryTO.setSubjectName(attendance
								.getSubject().getName());
						StudentWiseSubjectSummaryTO
								.setConductedClasses(((Long) object[1]).intValue());
						Map<Integer, StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryMap = studentWiseAttendanceSummaryReportTO
								.getSummaryMap();
						studentWiseSubjectSummaryMap.put(attendance
								.getSubject().getId(),
								StudentWiseSubjectSummaryTO);
						studentWiseAttendanceSummaryReportTO
								.setSummaryMap(studentWiseSubjectSummaryMap);
					}

				} else {
				    studentWiseAttendanceSummaryReportTO = new StudentWiseAttendanceSummaryReportTO();
					studentWiseAttendanceSummaryReportTO.setRegisterNo(student
							.getRegisterNo());
					studentWiseAttendanceSummaryReportTO.setRollNo(student
							.getRollNo());
					AdmAppln admAppln = student.getAdmAppln();
					
					
					StringBuffer applicantName = new StringBuffer();
					if (admAppln.getPersonalData().getFirstName() != null) {
						 applicantName.append(admAppln.getPersonalData().getFirstName());
						 applicantName.append(' ');
						
						
					}
					if (admAppln.getPersonalData().getMiddleName() != null) {
						 applicantName.append(admAppln.getPersonalData().getMiddleName());
						 applicantName.append(' ');
						
					}
					if (admAppln.getPersonalData().getLastName() != null) {
						 applicantName.append(admAppln.getPersonalData().getLastName());
						 applicantName.append(' ');						
					}
					
					studentWiseAttendanceSummaryReportTO
							.setStudentName(applicantName.toString());

					StudentWiseSubjectSummaryTO StudentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();

					if (attendance.getSubject() != null) {
						StudentWiseSubjectSummaryTO.setSubjectName(attendance
								.getSubject().getName());
						StudentWiseSubjectSummaryTO
								.setConductedClasses(((Long) object[1]).intValue());
						Map<Integer, StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();
						studentWiseSubjectSummaryMap.put(attendance
								.getSubject().getId(),
								StudentWiseSubjectSummaryTO);
						studentWiseAttendanceSummaryReportTO
								.setSummaryMap(studentWiseSubjectSummaryMap);
					}
					studentInfoMap.put(student.getId(), studentWiseAttendanceSummaryReportTO);
				}

			}
		}
		log.info("Leaving into convertBoToTo of StudentWiseAttendanceSummaryHelper");
		return studentInfoMap;

	}
	
	/**
	 * Converts from List of Bo to Map of studentid and subjectId ,StudentWiseSubjectSummaryTO object.
	 * @param studentWiseSummaryList
	 * @param isPresent
	 * @return
	 */
	public Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> convertFromListToMap(
			List studentWiseSummaryList, boolean isPresent) {
		log.info("Entering into convertFromListToMap of StudentWiseAttendanceSummaryHelper");
		Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> studentInfoMap = new HashMap<Integer, Map<Integer, StudentWiseSubjectSummaryTO>>();

		if (studentWiseSummaryList != null) {
			Iterator studentWiseSummaryIterator = studentWiseSummaryList
					.iterator();
			while (studentWiseSummaryIterator.hasNext()) {
				Object[] object = (Object[]) studentWiseSummaryIterator.next();
				Student student = ((Student) object[0]);
				Attendance attendance = ((Attendance) object[2]);
				Map<Integer, StudentWiseSubjectSummaryTO> studentWiseSubjectSummaryMap = null;
				if (studentInfoMap.containsKey(student.getId())) {

					studentWiseSubjectSummaryMap = studentInfoMap.get(student
							.getId());

					StudentWiseSubjectSummaryTO StudentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
					if (attendance.getSubject() != null) {

						if (isPresent) {
							StudentWiseSubjectSummaryTO
									.setClassesPresent(((Long) object[1])
											.intValue());
						} else {
							StudentWiseSubjectSummaryTO
									.setLeaveApproved(((Long) object[1])
											.intValue());
						}

						studentWiseSubjectSummaryMap.put(attendance
								.getSubject().getId(),
								StudentWiseSubjectSummaryTO);

					}

				} else {
					studentWiseSubjectSummaryMap = new HashMap<Integer, StudentWiseSubjectSummaryTO>();
					StudentWiseSubjectSummaryTO StudentWiseSubjectSummaryTO = new StudentWiseSubjectSummaryTO();
					if (attendance.getSubject() != null) {

						if (isPresent) {
							StudentWiseSubjectSummaryTO
									.setClassesPresent(((Long) object[1])
											.intValue());
						} else {
							StudentWiseSubjectSummaryTO
									.setLeaveApproved(((Long) object[1])
											.intValue());
						}
						studentWiseSubjectSummaryMap.put(attendance
								.getSubject().getId(),
								StudentWiseSubjectSummaryTO);

					}
				}
				studentInfoMap.put(student.getId(),
						studentWiseSubjectSummaryMap);

			}

		}
		log.info("Leaving into convertFromListToMap of StudentWiseAttendanceSummaryHelper");
		return studentInfoMap;

	}
	
	/**
	 * Converts from Map of List of StudentWiseAttendanceSummaryReportTO objects.
	 * @param conductedClassesMap
	 * @param classesPresentMap
	 * @param isOnLeaveMap
	 * @return
	 */
	public List<StudentWiseAttendanceSummaryReportTO> convertToList(
			Map<Integer, StudentWiseAttendanceSummaryReportTO> conductedClassesMap,
			Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> classesPresentMap,
			Map<Integer, Map<Integer, StudentWiseSubjectSummaryTO>> isOnLeaveMap) {
		log.info("Entering into convertToList of StudentWiseAttendanceSummaryHelper");
		Set<Integer> conductedClassesKeys = conductedClassesMap.keySet();

		Iterator<Integer> conductedClassesIterator = conductedClassesKeys
				.iterator();

		while (conductedClassesIterator.hasNext()) {
			Integer integer = (Integer) conductedClassesIterator.next();
			StudentWiseAttendanceSummaryReportTO conductedClasses = conductedClassesMap
					.get(integer);
			Iterator<Integer> subjectIterator = conductedClasses
					.getSummaryMap().keySet().iterator();
			Iterator<Integer> subjectIterator1 = conductedClasses
			.getSummaryMap().keySet().iterator();
			if (classesPresentMap.containsKey(integer)) {
				Map<Integer, StudentWiseSubjectSummaryTO> classesPresent = classesPresentMap
						.get(integer);
				while (subjectIterator.hasNext()) {
					Integer subjectId = (Integer) subjectIterator.next();

					StudentWiseSubjectSummaryTO conductedClassesFromMap = conductedClasses
							.getSummaryMap().get(subjectId);

					if (classesPresent.containsKey(subjectId)) {
						StudentWiseSubjectSummaryTO classesPresentSubject = classesPresent
								.get(subjectId);

						conductedClassesFromMap
								.setClassesPresent(classesPresentSubject
										.getClassesPresent());

						conductedClassesFromMap
								.setClassesAbsent(conductedClassesFromMap
										.getConductedClasses()
										- classesPresentSubject
												.getClassesPresent());
						conductedClassesFromMap
								.setPercentageWithoutLeave((conductedClassesFromMap
										.getClassesPresent() * 100)
										/ conductedClassesFromMap
												.getConductedClasses());
					} else {
						conductedClassesFromMap.setClassesPresent(0);
						conductedClassesFromMap
								.setClassesAbsent(conductedClassesFromMap
										.getConductedClasses()
										- conductedClassesFromMap
												.getClassesPresent());
						conductedClassesFromMap
								.setPercentageWithoutLeave((conductedClassesFromMap
										.getClassesPresent() * 100)
										/ conductedClassesFromMap
												.getConductedClasses());
					}

				}

			} else {
				Iterator<StudentWiseSubjectSummaryTO> subjectSummaryTOIterator = conductedClasses
						.getSummaryMap().values().iterator();

				while (subjectSummaryTOIterator.hasNext()) {
					StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjectSummaryTOIterator
							.next();
					studentWiseSubjectSummaryTO.setClassesPresent(0);
					studentWiseSubjectSummaryTO
							.setClassesAbsent(studentWiseSubjectSummaryTO
									.getConductedClasses()
									- studentWiseSubjectSummaryTO
											.getClassesPresent());
					studentWiseSubjectSummaryTO
							.setPercentageWithoutLeave((studentWiseSubjectSummaryTO
									.getClassesPresent() * 100)
									/ studentWiseSubjectSummaryTO
											.getConductedClasses());

				}

			}

			if (isOnLeaveMap.containsKey(integer)) {
				Map<Integer, StudentWiseSubjectSummaryTO> classesOnLeave = isOnLeaveMap
						.get(integer);
				while (subjectIterator1.hasNext()) {
					Integer subjectId = (Integer) subjectIterator1.next();
					StudentWiseSubjectSummaryTO conductedClassesFromMap = conductedClasses
							.getSummaryMap().get(subjectId);

					if (classesOnLeave.containsKey(subjectId)) {

						StudentWiseSubjectSummaryTO classesisOnLeave = classesOnLeave
								.get(subjectId);
						conductedClassesFromMap
								.setLeaveApproved(classesisOnLeave
										.getLeaveApproved());

						float percentageWithLeave = ((conductedClassesFromMap
								.getClassesPresent() + conductedClassesFromMap
								.getLeaveApproved()) * 100)
								/ conductedClassesFromMap.getConductedClasses();

						conductedClassesFromMap
								.setPercentageWithLeave(percentageWithLeave);

					} else {
						conductedClassesFromMap.setLeaveApproved(0);
						float percentageWithLeave = ((conductedClassesFromMap
								.getClassesPresent() + conductedClassesFromMap
								.getLeaveApproved()) * 100)
								/ conductedClassesFromMap.getConductedClasses();

						conductedClassesFromMap
								.setPercentageWithLeave(percentageWithLeave);
					}
				}
			} else {

				Iterator<StudentWiseSubjectSummaryTO> subjectSummaryTOIterator = conductedClasses
						.getSummaryMap().values().iterator();

				while (subjectSummaryTOIterator.hasNext()) {
					StudentWiseSubjectSummaryTO studentWiseSubjectSummaryTO = (StudentWiseSubjectSummaryTO) subjectSummaryTOIterator
							.next();
					studentWiseSubjectSummaryTO.setLeaveApproved(0);
					float percentageWithLeave = ((studentWiseSubjectSummaryTO
							.getClassesPresent() + studentWiseSubjectSummaryTO
							.getLeaveApproved()) * 100)
							/ studentWiseSubjectSummaryTO.getConductedClasses();

					studentWiseSubjectSummaryTO
							.setPercentageWithLeave(percentageWithLeave);

				}

			}
		}

		Collection<StudentWiseAttendanceSummaryReportTO> studentWiseAttendanceSummaryList = conductedClassesMap
				.values();
		List<StudentWiseAttendanceSummaryReportTO> studentWiseAttendanceSummaryReportTOList = new ArrayList<StudentWiseAttendanceSummaryReportTO>();

		studentWiseAttendanceSummaryReportTOList
				.addAll(studentWiseAttendanceSummaryList);
		Iterator<StudentWiseAttendanceSummaryReportTO> studentWiseAttendanceSummaryReportTOIterator = studentWiseAttendanceSummaryReportTOList
				.iterator();

		while (studentWiseAttendanceSummaryReportTOIterator.hasNext()) {
			StudentWiseAttendanceSummaryReportTO studentWiseAttendanceSummaryReportTO = (StudentWiseAttendanceSummaryReportTO) studentWiseAttendanceSummaryReportTOIterator
					.next();
			studentWiseAttendanceSummaryReportTO.getSubjectWiseSummaryToList()
					.addAll(
							studentWiseAttendanceSummaryReportTO
									.getSummaryMap().values());
		}
		log.info("Leaving into convertToList of StudentWiseAttendanceSummaryHelper");
		return studentWiseAttendanceSummaryReportTOList;
	}

	/**
	 * Constructs the common query.
	 * @param studentWiseAttendanceSummaryForm
	 * @return
	 */
	private String getCommonQuery(
			StudentWiseAttendanceSummaryForm studentWiseAttendanceSummaryForm) {
		log.info("Entering into getCommonQuery of StudentWiseAttendanceSummaryHelper");
		StringBuffer searchCriteria = new StringBuffer();

//		if (studentWiseAttendanceSummaryForm.getProgramTypeId() != null
//				&& studentWiseAttendanceSummaryForm.getProgramTypeId().trim().length() > 0) {
//			searchCriteria.append(" and student.admAppln.courseBySelectedCourseId.program.programType.id = ");
//			searchCriteria.append( studentWiseAttendanceSummaryForm.getProgramTypeId());
//		}
//
//		if (studentWiseAttendanceSummaryForm.getProgramId() != null
//				&& studentWiseAttendanceSummaryForm.getProgramId().trim().length() > 0) {
//			searchCriteria.append(" and student.admAppln.courseBySelectedCourseId.program.id = ");
//			searchCriteria.append(studentWiseAttendanceSummaryForm.getProgramId());
//		}
//
//		if (studentWiseAttendanceSummaryForm.getCourseId() != null
//				&& studentWiseAttendanceSummaryForm.getCourseId().trim().length() > 0) {
//			searchCriteria.append(" and student.admAppln.courseBySelectedCourseId.id = ");
//			searchCriteria.append(studentWiseAttendanceSummaryForm.getCourseId());
//		}
//
//		if (studentWiseAttendanceSummaryForm.getSemister() != null
//				&& studentWiseAttendanceSummaryForm.getSemister().trim().length() > 0) {
//			searchCriteria.append( " and student.classSchemewise.classes.termNumber = ");
//			searchCriteria.append(studentWiseAttendanceSummaryForm.getSemister());
//		}
		
		if (studentWiseAttendanceSummaryForm.getClassesName() != null && studentWiseAttendanceSummaryForm.getClassesName().length > 0) {
			String [] tempClassesArray = studentWiseAttendanceSummaryForm.getClassesName();
			StringBuilder classes=new StringBuilder();
			for(int i=0;i<tempClassesArray.length;i++){
				classes.append(tempClassesArray[i]);
				 if(i<(tempClassesArray.length-1)){
					 classes.append(",");
				 }
			}
		//String classCodes = " and attendanceStudents.student.classSchemewise.classes.id in ("+ classes +")";
		String classCodes = " and attendanceStudents.student.classSchemewise.id in ("+ classes +")";
		searchCriteria = searchCriteria.append(classCodes);
		}
		
		if (studentWiseAttendanceSummaryForm.getAttendanceType() != null
				&& studentWiseAttendanceSummaryForm.getAttendanceType().length > 0) {
			String[] attendanceType = studentWiseAttendanceSummaryForm.getAttendanceType();
		
			
			StringBuffer attType =new StringBuffer();
			for(int i=0;i<attendanceType.length;i++){
				attType.append(attendanceType[i]);
				 if(i<(attendanceType.length-1)){
					 attType.append(',');
				 }
			}
		
			
			searchCriteria.append(" and attendanceStudents.attendance.attendanceType.id in (");
			searchCriteria.append(attType); 
			searchCriteria.append(')');
		}

		searchCriteria.append(" and attendanceStudents.attendance.attendanceDate between '");
		searchCriteria.append(CommonUtil
						.ConvertStringToSQLDate(studentWiseAttendanceSummaryForm
								.getStartDate()));
		searchCriteria.append("' and '");
		searchCriteria.append(CommonUtil
						.ConvertStringToSQLDate(studentWiseAttendanceSummaryForm
								.getEndDate())) ;
		searchCriteria.append("'");
		
		if (studentWiseAttendanceSummaryForm.getStartRegisterNo() != null
				&& !studentWiseAttendanceSummaryForm.getStartRegisterNo()
						.isEmpty()
				&& studentWiseAttendanceSummaryForm.getEndRegisterNo() != null
				&& !studentWiseAttendanceSummaryForm.getEndRegisterNo().isEmpty()) {

			searchCriteria
					.append(" and attendanceStudents.student.registerNo between '");
			searchCriteria.append(studentWiseAttendanceSummaryForm
					.getStartRegisterNo());
			searchCriteria.append("' and '");
			searchCriteria.append(studentWiseAttendanceSummaryForm
					.getEndRegisterNo());
			searchCriteria.append("'");	
		} else if (studentWiseAttendanceSummaryForm.getStartRollNo() != null
				&& !studentWiseAttendanceSummaryForm.getStartRollNo().isEmpty()
				&& studentWiseAttendanceSummaryForm.getEndRollNo() != null
				&& !studentWiseAttendanceSummaryForm.getEndRollNo().isEmpty()) {

			searchCriteria
					.append(" and attendanceStudents.student.rollNo between '");
			searchCriteria.append(studentWiseAttendanceSummaryForm
					.getStartRollNo());
			searchCriteria.append("' and '");
			searchCriteria.append(studentWiseAttendanceSummaryForm.getEndRollNo());
			searchCriteria.append("'");	
		} else if (studentWiseAttendanceSummaryForm.getStartRegisterNo() != null
				&& !studentWiseAttendanceSummaryForm.getStartRegisterNo()
						.isEmpty()
				&& studentWiseAttendanceSummaryForm.getEndRegisterNo() == null) {

			searchCriteria
					.append(" and attendanceStudents.student.registerNo >= '");
			searchCriteria.append(studentWiseAttendanceSummaryForm
					.getStartRegisterNo());
			searchCriteria.append("'");

		} else if (studentWiseAttendanceSummaryForm.getStartRollNo() != null
				&& !studentWiseAttendanceSummaryForm.getStartRollNo().isEmpty()
				&& studentWiseAttendanceSummaryForm.getEndRollNo() == null) {

			searchCriteria.append(" and attendanceStudents.student.rollNo >= '");
			searchCriteria.append(studentWiseAttendanceSummaryForm
					.getStartRollNo());
			searchCriteria.append("'");

		}
		log.info("Leaving into getCommonQuery of StudentWiseAttendanceSummaryHelper");
		return searchCriteria.toString();
	}

	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String getAttendanceDataByStudentQuery(String studentId) throws Exception {
		String query="select attStu from AttendanceStudent attStu" +
				" join attStu.attendance.attendanceClasses ac where attStu.attendance.isCanceled=0" +
				" and ac.classSchemewise.id= attStu.student.classSchemewise.id and attStu.isPresent=0 and attStu.student.id="+studentId+" order by attStu.attendance.attendanceDate";
		return query;
	}

	/**
	 * @param attList
	 * @return
	 */
	public List<StudentAttendanceTO> convertAttendanceStudentBotoTo(List<AttendanceStudent> attList,Map<String,Integer> posMap,String studentId,StudentWiseAttendanceSummaryForm attendanceSummaryForm) throws Exception{
		Map<String,StudentAttendanceTO> stuMap=new HashMap<String, StudentAttendanceTO>();
		Map<String,Integer> subMap=new HashMap<String, Integer>();
		int totalCoLeave=0;
		int totalAbscent=0;
		if(attList!=null && !attList.isEmpty()){
			Iterator<AttendanceStudent> itr=attList.iterator();
			while (itr.hasNext()) {
				AttendanceStudent bo = (AttendanceStudent) itr.next();
				int hoursHeld=bo.getAttendance().getHoursHeld();
				String sub="";
				if(bo.getAttendance().getSubject()!=null){
					sub=bo.getAttendance().getSubject().getName();
					if(bo.getAttendance().getSubject().getCode()!=null)
						sub=sub+"("+bo.getAttendance().getSubject().getCode()+")";
				}else if(bo.getAttendance().getAttendanceType()!=null){
					sub=bo.getAttendance().getAttendanceType().getName();
				}
				
				if(stuMap.containsKey(bo.getAttendance().getAttendanceDate().toString())){
					StudentAttendanceTO to=stuMap.get(bo.getAttendance().getAttendanceDate().toString());
					boolean isCoLeave=false;
					if(bo.getIsCoCurricularLeave()!=null && bo.getIsCoCurricularLeave())
						isCoLeave=true;
					
					if(!isCoLeave){
						if(!subMap.containsKey(sub)){
							subMap.put(sub,hoursHeld);
						}else{
							int subHeld=subMap.remove(sub)+hoursHeld;
							subMap.put(sub, subHeld);
						}
					}
					
					if(isCoLeave)
						totalCoLeave=totalCoLeave+hoursHeld;
					else
						totalAbscent=totalAbscent+hoursHeld;
					
					
					List<PeriodTO> periodList=to.getPeriodList();
					List<Integer> toPosList=to.getToPosList();
					Set<AttendancePeriod> appPeriod=bo.getAttendance().getAttendancePeriods();
					Iterator<AttendancePeriod> appItr=appPeriod.iterator();
					while (appItr.hasNext()) {
						AttendancePeriod attendancePeriod = (AttendancePeriod) appItr.next();
						if(attendancePeriod.getPeriod().getClassSchemewise().getId()==bo.getStudent().getClassSchemewise().getId()){
							PeriodTO pto=new PeriodTO();
							if(attendancePeriod.getAttendance().getSubject()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getSubject().getCode());
							else if(attendancePeriod.getAttendance().getAttendanceType()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getAttendanceType().getName());
							pto.setCoLeave(isCoLeave);
							pto.setPosition(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							toPosList.add(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							periodList.add(pto);
						}
					}
					to.setPeriodList(periodList);
					to.setToPosList(toPosList);
					to.setHoursHeldByDay(to.getHoursHeldByDay()+hoursHeld);
					stuMap.put(bo.getAttendance().getAttendanceDate().toString(),to);
				}else{
					StudentAttendanceTO to=new StudentAttendanceTO();
					to.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(bo.getAttendance().getAttendanceDate()), StudentWiseAttendanceSummaryHelper.SQL_DATEFORMAT,StudentWiseAttendanceSummaryHelper.FROM_DATEFORMAT));
					to.setDay(CommonUtil.sayDayName(bo.getAttendance().getAttendanceDate()));
					boolean isCoLeave=false;
					if(bo.getIsCoCurricularLeave()!=null && bo.getIsCoCurricularLeave())
						isCoLeave=true;
					if(!isCoLeave){
						if(!subMap.containsKey(sub)){
							subMap.put(sub,hoursHeld);
						}else{
							int subHeld=subMap.remove(sub)+hoursHeld;
							subMap.put(sub, subHeld);
						}
					}
					if(isCoLeave)
						totalCoLeave=totalCoLeave+hoursHeld;
					else
						totalAbscent=totalAbscent+hoursHeld;
					List<PeriodTO> periodList=new ArrayList<PeriodTO>();
					List<Integer> toPosList=new ArrayList<Integer>();
					Set<AttendancePeriod> appPeriod=bo.getAttendance().getAttendancePeriods();
					Iterator<AttendancePeriod> appItr=appPeriod.iterator();
					while (appItr.hasNext()) {
						AttendancePeriod attendancePeriod = (AttendancePeriod) appItr.next();
						if(attendancePeriod.getPeriod().getClassSchemewise().getId()==bo.getStudent().getClassSchemewise().getId()){
							PeriodTO pto=new PeriodTO();
							if(attendancePeriod.getAttendance().getSubject()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getSubject().getCode());
							else if(attendancePeriod.getAttendance().getAttendanceType()!=null)
								pto.setPeriodName(attendancePeriod.getAttendance().getAttendanceType().getName());
							pto.setCoLeave(isCoLeave);
							pto.setPosition(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							toPosList.add(posMap.get(attendancePeriod.getPeriod().getPeriodName()));
							periodList.add(pto);
						}
					}
					to.setPeriodList(periodList);
					to.setHoursHeldByDay(hoursHeld);
					to.setToPosList(toPosList);
					stuMap.put(bo.getAttendance().getAttendanceDate().toString(),to);
				}
				
			}
		}
		attendanceSummaryForm.setTotalCoLeave(totalCoLeave);
		attendanceSummaryForm.setAbscent(totalAbscent);
		attendanceSummaryForm.setTotal(totalAbscent+totalCoLeave);
		
		List<StudentAttendanceTO> mainList=new ArrayList<StudentAttendanceTO>(stuMap.values());
		List<StudentAttendanceTO> finalList=new ArrayList<StudentAttendanceTO>();
		List<Integer> posList=new ArrayList<Integer>(posMap.values());
		if(!mainList.isEmpty()){
			Iterator<StudentAttendanceTO> itr=mainList.iterator();
			while (itr.hasNext()) {
				StudentAttendanceTO to = itr.next();
				List<PeriodTO> periodList=to.getPeriodList();
				List<Integer> positions=to.getToPosList();
				Iterator<Integer> pItr=posList.iterator();
				while (pItr.hasNext()) {
					Integer pos =pItr.next();
					if(!positions.contains(pos)){
						PeriodTO pto=new PeriodTO();
						pto.setPeriodName("");
						pto.setPosition(pos);
						periodList.add(pto);
					}
				}
				TeacherReportComparator comparator=new TeacherReportComparator();
				comparator.setCompare(1);
				Collections.sort(periodList,comparator);
				to.setPeriodList(periodList);
				finalList.add(to);
			}
		}
		Collections.sort(finalList);
		attendanceSummaryForm.setSubMap(subMap);
		return finalList;
	}

	/**
	 * @param studentId
	 * @return
	 * @throws Exception
	 */
	public String getPeriodsForStudent(String studentId) throws Exception{
		String query="select p.periodName from Period p where p.isActive=1 and p.classSchemewise.id = (select s.classSchemewise.id from Student s where s.id="+studentId+") order by p.periodName";
		return query;
	}

	/**
	 * @param periodList
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getPositionsForPeriod(List<String> periodList) throws Exception {
		Iterator<String> itr=periodList.iterator();
		Map<String, Integer> map=new HashMap<String, Integer>();
		int count=1;
		while (itr.hasNext()) {
			String periodName = (String) itr.next();
			map.put(periodName, count);
			count++;
		}
		return map;
	}

}
