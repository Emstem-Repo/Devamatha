package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceInstructor;
import com.kp.cms.bo.admin.AttendancePeriod;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentAbsenceDetailsForm;
import com.kp.cms.to.reports.StudentAbsenceDetailsReportTO;
import com.kp.cms.to.reports.StudentAbsenceDetailsTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentAbsenceDetailsHelper {
	
private static final Log log = LogFactory.getLog(StudentAbsenceDetailsHelper.class);

	/**
	 * Method to generate the search criteria query depending on the input selected
	 * @param studentAbsenceDetailsForm
	 * @return
	 */
	private String commonSearch(StudentAbsenceDetailsForm studentAbsenceDetailsForm) {
		
		log.info("entered commonSearch..");
		String searchCriteria = "";
		
		if (studentAbsenceDetailsForm.getAcademicYear().trim().length() > 0) {
			String appliedYear = " attendanceStudents.student.classSchemewise.curriculumSchemeDuration.academicYear = "
					+ studentAbsenceDetailsForm.getAcademicYear();
			String admApplnStr = " and attendanceStudents.student.admAppln.isCancelled = 0";
			searchCriteria = searchCriteria + appliedYear + admApplnStr;
		}
		
		if (studentAbsenceDetailsForm.getAttendanceType() != null && studentAbsenceDetailsForm.getAttendanceType().length > 0) {
				String [] tempArray = studentAbsenceDetailsForm.getAttendanceType();
				StringBuilder attType=new StringBuilder();
				for(int i=0;i<tempArray.length;i++){
					 attType.append(tempArray[i]);
					 if(i<(tempArray.length-1)){
						 attType.append(",");
					 }
				}
			String attendanceType = " and attendanceStudents.attendance.attendanceType.id in ("+ attType +")";
			searchCriteria = searchCriteria + attendanceType;
		}
		
		if (studentAbsenceDetailsForm.getClassesName() != null && studentAbsenceDetailsForm.getClassesName().length > 0) {
			String [] tempClassesArray = studentAbsenceDetailsForm.getClassesName();
			String classes ="";
			for(int i=0;i<tempClassesArray.length;i++){
				classes = classes+tempClassesArray[i];
				 if(i<(tempClassesArray.length-1)){
					 classes = classes+",";
				 }
			}
		//String classCodes = " and attendanceStudents.student.classSchemewise.classes.id in ("+ classes +")";
		String classCodes = " and attendanceStudents.student.classSchemewise.id in ("+ classes +")";
		searchCriteria = searchCriteria + classCodes;
		}

		
		if (studentAbsenceDetailsForm.getSubjects() != null && studentAbsenceDetailsForm.getSubjects().length > 0) {
			String [] tempSubjectArray = studentAbsenceDetailsForm.getSubjects();
			String subjects ="";
			for(int i=0;i<tempSubjectArray.length;i++){
				subjects = subjects+tempSubjectArray[i];
				 if(i<(tempSubjectArray.length-1)){
					 subjects = subjects+",";
				 }
			}
		String subjectCodes = " and attendanceStudents.attendance.subject.id in ("+ subjects +")";
		searchCriteria = searchCriteria + subjectCodes;
		}
		
		if (studentAbsenceDetailsForm.getStartDate().trim().length() > 0 && studentAbsenceDetailsForm.getEndDate().trim().length() > 0) {
			String betweenDate = " and attendanceStudents.attendance.attendanceDate between "+
					"'"+ CommonUtil.ConvertStringToSQLDate(studentAbsenceDetailsForm.getStartDate())+"'" +
					 " and " +
					 "'" + CommonUtil.ConvertStringToSQLDate(studentAbsenceDetailsForm.getEndDate())+"' " ;
			searchCriteria = searchCriteria + betweenDate;
		}
		
		if (studentAbsenceDetailsForm.getFromRegisterNo()!=null && studentAbsenceDetailsForm.getFromRegisterNo().trim().length() > 0 && studentAbsenceDetailsForm.getToRegisterNo()!=null && studentAbsenceDetailsForm.getToRegisterNo().trim().length() > 0) {
			String betweenRegisterNo = " and attendanceStudents.student.registerNo between "+
					"'"+ studentAbsenceDetailsForm.getFromRegisterNo()+"'" +
					 " and " +
					 "'" + studentAbsenceDetailsForm.getToRegisterNo()+"' " ;
			searchCriteria = searchCriteria + betweenRegisterNo;
		}else if (studentAbsenceDetailsForm.getFromRegisterNo()!=null && studentAbsenceDetailsForm.getFromRegisterNo().trim().length() > 0) {
			String fromRegisterNo = " and attendanceStudents.student.registerNo >= "+
					"'"+ studentAbsenceDetailsForm.getFromRegisterNo()+"'";
			searchCriteria = searchCriteria + fromRegisterNo;
		}else if(studentAbsenceDetailsForm.getFromRollNo()!=null && studentAbsenceDetailsForm.getFromRollNo().trim().length() > 0 && studentAbsenceDetailsForm.getToRollNo()!=null && studentAbsenceDetailsForm.getToRollNo().trim().length() > 0){
			String betweenRollNo = " and attendanceStudents.student.rollNo between "+
			"'"+ studentAbsenceDetailsForm.getFromRollNo()+"'" +
			 " and " +
			 "'" + studentAbsenceDetailsForm.getToRollNo()+"' " ;
			searchCriteria = searchCriteria + betweenRollNo;
		}else if(studentAbsenceDetailsForm.getFromRollNo()!=null && studentAbsenceDetailsForm.getFromRollNo().trim().length() > 0) {
			String fromRollNo = " and attendanceStudents.student.rollNo >= "+
			"'"+ studentAbsenceDetailsForm.getFromRollNo()+"'";
			searchCriteria = searchCriteria + fromRollNo;
		}
		
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param studentAbsenceDetailsForm
	 * @return
	 */
	public String getStudentAbsenceDetails(
			StudentAbsenceDetailsForm studentAbsenceDetailsForm) {
		
		log.info("Entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(studentAbsenceDetailsForm);
		
		String searchCriteria= "";
		
		searchCriteria = "select attendanceStudents " +
				" from AttendanceStudent attendanceStudents " +
				" where " +statusCriteria+
				" and attendanceStudents.attendance.isMonthlyAttendance = 0 " +
				" and attendanceStudents.isPresent = 0 " +
				" and attendanceStudents.attendance.isCanceled = 0 " +
				" order by attendanceStudents.attendance.attendanceDate";
 		
		log.info("Exit getSelectionSearchCriteria..");
		return searchCriteria;
	}
	
	/**
	 * 
	 * @param studentAbsenceDetailsListBO
	 * @return
	 */
	public List<StudentAbsenceDetailsTO> convertBoToTo(List<AttendanceStudent> studentAbsenceDetailsListBO) {
		log.info("entered convertBoToTo..");
		List<StudentAbsenceDetailsTO> studentsAbsenceDetailsTOList = new ArrayList<StudentAbsenceDetailsTO>();
		Map<Integer,StudentAbsenceDetailsTO> studentAbsenceDetailsMap = new HashMap<Integer,StudentAbsenceDetailsTO>();
		
		if (studentAbsenceDetailsListBO != null && studentAbsenceDetailsListBO.size()>0) {

			Iterator<AttendanceStudent> studentAbsenceDetailsListBOIterator = studentAbsenceDetailsListBO
					.iterator();
			while (studentAbsenceDetailsListBOIterator.hasNext()) {
				StudentAbsenceDetailsTO studentAbsenceDetailsTO = null;
				AttendanceStudent attendanceStudent = (AttendanceStudent) studentAbsenceDetailsListBOIterator
						.next();

				Student student = attendanceStudent.getStudent();
				if (studentAbsenceDetailsMap.containsKey(student.getId())) {
					studentAbsenceDetailsTO = studentAbsenceDetailsMap
							.get(student.getId());
					List<StudentAbsenceDetailsReportTO> studentDetailList = studentAbsenceDetailsTO
							.getStudentAbsenceDetailsList();
					
					studentDetailList= getAbsenceDetailsReportTO(
							attendanceStudent, student,studentDetailList);

				} else {
					studentAbsenceDetailsTO = new StudentAbsenceDetailsTO();
					String registerNumber = "";
					
					
					
					/*if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty() && student.getRollNo()!=null && !student.getRollNo().isEmpty()){
						registerNumber = registerNumber+"/"+student.getRollNo();
					}else if(student.getRegisterNo()!=null && student.getRegisterNo().isEmpty()){
						registerNumber = student.getRollNo();
					}*/
					if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty() && student.getRollNo()!=null && !student.getRollNo().isEmpty()){
						registerNumber = registerNumber+"/"+student.getRollNo();
					}
					else if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty()){
						registerNumber = student.getRegisterNo();
					}
					else if(student.getRollNo()!=null && !student.getRollNo().isEmpty()){
						registerNumber = student.getRollNo();
					}					
					
					studentAbsenceDetailsTO.setRegisterNumber(registerNumber);
					
					if(student.getAdmAppln()!=null && student.getAdmAppln().getPersonalData()!=null){
						if(student.getAdmAppln().getPersonalData().getFirstName()!=null && student.getAdmAppln().getPersonalData().getMiddleName()!=null && student.getAdmAppln().getPersonalData().getLastName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty() && !student.getAdmAppln().getPersonalData().getMiddleName().isEmpty() && !student.getAdmAppln().getPersonalData().getLastName().isEmpty()){
							studentAbsenceDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName()+" "+ student.getAdmAppln().getPersonalData().getMiddleName() +" "+student.getAdmAppln().getPersonalData().getLastName());
						}else if(student.getAdmAppln().getPersonalData().getFirstName()!=null && student.getAdmAppln().getPersonalData().getMiddleName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty() && !student.getAdmAppln().getPersonalData().getMiddleName().isEmpty()){
							studentAbsenceDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName()+" "+ student.getAdmAppln().getPersonalData().getMiddleName());
						}else if(student.getAdmAppln().getPersonalData().getFirstName()!=null){
							studentAbsenceDetailsTO.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
						}
					}
					
					List<StudentAbsenceDetailsReportTO> studentDetailList = new ArrayList<StudentAbsenceDetailsReportTO>();
					
					 studentDetailList = getAbsenceDetailsReportTO(
							attendanceStudent, student,studentDetailList);
					studentAbsenceDetailsTO.setStudentAbsenceDetailsList(studentDetailList);

				}
				studentAbsenceDetailsMap.put(student.getId(), studentAbsenceDetailsTO);
			}
		}
		studentsAbsenceDetailsTOList.addAll(studentAbsenceDetailsMap.values());
		
		log.info("exit convertBoToTo..");
		return studentsAbsenceDetailsTOList;
	}

	/**
	 * 
	 * @param attendanceStudent
	 * @param student
	 * @param studentDetailList
	 * @return
	 */
	private List<StudentAbsenceDetailsReportTO> getAbsenceDetailsReportTO(
			AttendanceStudent attendanceStudent, Student student,List<StudentAbsenceDetailsReportTO> studentDetailList) {
	
		StudentAbsenceDetailsReportTO studentAbsenceDetailsReportTO = new StudentAbsenceDetailsReportTO();
		if (student.getClassSchemewise() != null
				&& student.getClassSchemewise().getClasses() != null) {
			studentAbsenceDetailsReportTO.setClassName("\n"+student
					.getClassSchemewise().getClasses().getName());
		}
		if (attendanceStudent.getAttendance() != null
				&& attendanceStudent.getAttendance().getSubject() != null) {
			studentAbsenceDetailsReportTO
					.setSubjectCode("\n"+attendanceStudent
							.getAttendance().getSubject().getCode());
		}
		if (attendanceStudent.getAttendance() != null) {
			studentAbsenceDetailsReportTO
					.setAttendanceDate("\n"+CommonUtil
							.getStringDate(attendanceStudent
									.getAttendance()
									.getAttendanceDate()));
		}
		String period = "";
		Iterator<AttendancePeriod> attendancePeriodItr = attendanceStudent
				.getAttendance().getAttendancePeriods().iterator();
		while (attendancePeriodItr.hasNext()) {
			AttendancePeriod attendancePeriod = (AttendancePeriod) attendancePeriodItr
					.next();
			if (period.equals("")) {
				period = attendancePeriod.getPeriod()
						.getPeriodName();
			} else {
				period = period
						+ ","
						+ attendancePeriod.getPeriod()
								.getPeriodName();
			}
		}
		studentAbsenceDetailsReportTO.setPeriodName("\n"+period);

		Iterator<AttendanceInstructor> attendanceInstructorItr = attendanceStudent
				.getAttendance().getAttendanceInstructors()
				.iterator();
		while (attendanceInstructorItr.hasNext()) {
			AttendanceInstructor attendanceInstructor = (AttendanceInstructor) attendanceInstructorItr
					.next();
			if (attendanceInstructor.getUsers() != null) {
				studentAbsenceDetailsReportTO
						.setFacultyName("\n"+attendanceInstructor
								.getUsers().getUserName());
			}
		}
		studentDetailList.add(studentAbsenceDetailsReportTO);
		return studentDetailList;
	}
}