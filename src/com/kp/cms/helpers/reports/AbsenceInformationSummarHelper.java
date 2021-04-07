package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.forms.reports.AbsenceInformationSummaryForm;
import com.kp.cms.to.reports.AbsenceInfoMapTO;
import com.kp.cms.to.reports.AbsenceInformationSummaryTO;
import com.kp.cms.utilities.CommonUtil;

public class AbsenceInformationSummarHelper {

	private static volatile AbsenceInformationSummarHelper absenceInformationSummaryHelper = null;
	private static final Log log = LogFactory.getLog(AbsenceInformationSummarHelper.class);
	private AbsenceInformationSummarHelper() {

	}

	public static AbsenceInformationSummarHelper getInstance() {
		if (absenceInformationSummaryHelper == null) {
			absenceInformationSummaryHelper = new AbsenceInformationSummarHelper();
		}
		return absenceInformationSummaryHelper;
	}

	/**
	 * Converts from BoList to List of AbsenceInformationSummaryTO objects.
	 * 
	 * @param absenceInformationList
	 * @return
	 */
	public List<AbsenceInformationSummaryTO> convertBotoTO(
			List absenceInformationList,
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into convertBotoTO of AbsenceInformationSummarHelper class.");
		List<AbsenceInformationSummaryTO> absenceInformationToList = new ArrayList<AbsenceInformationSummaryTO>();
		Map<Integer, AbsenceInformationSummaryTO> absenceInfoMap = new HashMap<Integer, AbsenceInformationSummaryTO>();
		if (absenceInformationList != null) {
			Iterator absenceInformationIterator = absenceInformationList
					.iterator();

			while (absenceInformationIterator.hasNext()) {
				Object[] absenceInformation = (Object[]) absenceInformationIterator
						.next();
				AbsenceInformationSummaryTO absenceInformationSummaryTO = null;
				if (absenceInfoMap
						.containsKey(((Student) absenceInformation[0]).getId())) {

					absenceInformationSummaryTO = absenceInfoMap
							.get(((Student) absenceInformation[0]).getId());
					List<AbsenceInfoMapTO> subjectList = absenceInformationSummaryTO
							.getSubjectMaplist();
					Iterator<AbsenceInfoMapTO> subjectIterator = subjectList
							.iterator();
					Subject subject = ((Subject) absenceInformation[2]);
					while (subjectIterator.hasNext()) {
						AbsenceInfoMapTO absenceInfoMapTO = (AbsenceInfoMapTO) subjectIterator
								.next();
						if (absenceInfoMapTO.getTitle().trim().equals(
								subject.getName()+"("+subject.getCode()+")")) {
							absenceInfoMapTO.setValue(String
									.valueOf(absenceInformation[3]));
						}

					}

				} else {

					absenceInformationSummaryTO = new AbsenceInformationSummaryTO();

					AdmAppln admAppln = ((AdmAppln) absenceInformation[1]);
					
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
					absenceInformationSummaryTO.setStudentName(applicantName.toString());
					Student student = ((Student) absenceInformation[0]);
					absenceInformationSummaryTO.setRegisterNo(student
							.getRegisterNo());
					if(student.getRollNo()!=null)
						absenceInformationSummaryTO.setRegisterNo(absenceInformationSummaryTO.getRegisterNo()+ "\\" + student.getRollNo());

					Subject subject = ((Subject) absenceInformation[2]);

					Iterator<String> subjectIterator = absenceInformationSummaryForm
							.getSubjectList().iterator();

					List<AbsenceInfoMapTO> subjectList = new ArrayList<AbsenceInfoMapTO>();
					while (subjectIterator.hasNext()) {
						String subjectName = (String) subjectIterator.next();
						AbsenceInfoMapTO absenceInfoMapTO = new AbsenceInfoMapTO();
						if (subjectName.trim().equals(subject.getName()+"("+subject.getCode()+")")) {
							absenceInfoMapTO.setTitle(subjectName);
							absenceInfoMapTO.setValue(String
									.valueOf(absenceInformation[3]));
						} else {
							absenceInfoMapTO.setTitle(subjectName);
							absenceInfoMapTO.setValue("");
						}
						subjectList.add(absenceInfoMapTO);
					}
					absenceInformationSummaryTO.setSubjectMaplist(subjectList);
					absenceInfoMap.put(student.getId(),
							absenceInformationSummaryTO);
				}

			}
		}
		absenceInformationToList.addAll(absenceInfoMap.values());
		log.info("exit of  convertBotoTO of AbsenceInformationSummarHelper class.");
		return absenceInformationToList;

	}

	/**
	 * Constructs the search query for absence information summary.
	 * 
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	public String getAbsenceInformationSummaryQuery(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into getAbsenceInformationSummaryQuery of AbsenceInformationSummarHelper class.");
		StringBuffer searchCriteria = getCommonQuery(absenceInformationSummaryForm);
		if (absenceInformationSummaryForm.getStartRegisterNo() != null
				&& !absenceInformationSummaryForm.getStartRegisterNo()
						.isEmpty()
				&& absenceInformationSummaryForm.getEndRegisterNo() != null
				&& !absenceInformationSummaryForm.getEndRegisterNo().isEmpty()) {

			searchCriteria
					.append(" and attendanceStudent.student.registerNo between '");
			searchCriteria.append(absenceInformationSummaryForm
					.getStartRegisterNo());
			searchCriteria.append("' and '");
			searchCriteria.append(absenceInformationSummaryForm
					.getEndRegisterNo());
			searchCriteria.append("'");	
		} else if (absenceInformationSummaryForm.getStartRollNo() != null
				&& !absenceInformationSummaryForm.getStartRollNo().isEmpty()
				&& absenceInformationSummaryForm.getEndRollNo() != null
				&& !absenceInformationSummaryForm.getEndRollNo().isEmpty()) {

			searchCriteria
					.append(" and attendanceStudent.student.rollNo between '");
			searchCriteria.append(absenceInformationSummaryForm
					.getStartRollNo());
			searchCriteria.append("' and '");
			searchCriteria.append(absenceInformationSummaryForm.getEndRollNo());
			searchCriteria.append("'");	
		} else if (absenceInformationSummaryForm.getStartRegisterNo() != null
				&& !absenceInformationSummaryForm.getStartRegisterNo()
						.isEmpty()
				&& absenceInformationSummaryForm.getEndRegisterNo() == null) {

			searchCriteria
					.append(" and attendanceStudent.student.registerNo >= '");
			searchCriteria.append(absenceInformationSummaryForm
					.getStartRegisterNo());
			searchCriteria.append("'");

		} else if (absenceInformationSummaryForm.getStartRollNo() != null
				&& !absenceInformationSummaryForm.getStartRollNo().isEmpty()
				&& absenceInformationSummaryForm.getEndRollNo() == null) {

			searchCriteria.append(" and attendanceStudent.student.rollNo >= '");
			searchCriteria.append(absenceInformationSummaryForm
					.getStartRollNo());
			searchCriteria.append("'");

		}
		
		 String absenceInfoSummaryQuery = "	select attendanceStudent.student, attendanceStudent.student.admAppln, attendanceStudent.attendance.subject, "
				+ " sum(attendanceStudent.attendance.hoursHeld)  "
				+ "   from AttendanceStudent attendanceStudent  "
				+ "   where attendanceStudent.attendance.isCanceled = 0  " +
						" and attendanceStudent.student.admAppln.isCancelled = 0 " +
						" and attendanceStudent.isPresent = 0 and (attendanceStudent.student.isHide = false or attendanceStudent.student.isHide is null)"
				+ "  and attendanceStudent.attendance.isMonthlyAttendance =0  "
				+ "  and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ absenceInformationSummaryForm.getAcademicYear()
				+ searchCriteria
				+ " group by attendanceStudent.student.id,attendanceStudent.attendance.subject.id ";
	
		 log.info("exit of  getAbsenceInformationSummaryQuery of AbsenceInformationSummarHelper class.");
		return absenceInfoSummaryQuery;

	}

	/**
	 * Constructs a query for getting subject names.
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	public String getSubjectList(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into getSubjectList of AbsenceInformationSummarHelper class.");
		StringBuffer searchCriteria = getCommonQuery(absenceInformationSummaryForm);
	
		 String absenceInfoSummaryQuery = "	select  attendanceStudent.attendance.subject.name,attendanceStudent.attendance.subject.code "
				+ "   from AttendanceStudent attendanceStudent  "
				+ "   where attendanceStudent.attendance.isCanceled = 0  " +
						" and attendanceStudent.isPresent = 0 "
				+ "  and attendanceStudent.attendance.isMonthlyAttendance =0  "
				+ "  and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ absenceInformationSummaryForm.getAcademicYear()
				+ searchCriteria
				+ " group by attendanceStudent.attendance.subject.id ";
	
		 log.info("exit of  getSubjectList of AbsenceInformationSummarHelper class.");
		return absenceInfoSummaryQuery;
	}

	/**
	 * Constructs the common query.
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	private StringBuffer getCommonQuery(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into getCommonQuery of AbsenceInformationSummarHelper class.");
		StringBuffer searchCriteria = new StringBuffer();

//		if (absenceInformationSummaryForm.getProgramTypeId() != null
//				&& absenceInformationSummaryForm.getProgramTypeId().trim().length() > 0) {
//			searchCriteria.append(" and attendanceStudent.student.admAppln.courseBySelectedCourseId.program.programType.id = " );
//			
//			searchCriteria.append( absenceInformationSummaryForm.getProgramTypeId());
//		}
//
//		if (absenceInformationSummaryForm.getProgramId() != null
//				&& absenceInformationSummaryForm.getProgramId().trim().length() > 0) {
//			searchCriteria.append(" and attendanceStudent.student.admAppln.courseBySelectedCourseId.program.id = ");
//					searchCriteria.append(absenceInformationSummaryForm.getProgramId());
//		}
//
//		if (absenceInformationSummaryForm.getCourseId() != null
//				&& absenceInformationSummaryForm.getCourseId().trim().length() > 0) {
//			searchCriteria.append( " and attendanceStudent.student.admAppln.courseBySelectedCourseId.id = ");
//			searchCriteria.append(absenceInformationSummaryForm.getCourseId());
//		}
//
		if (absenceInformationSummaryForm.getClassesName() != null) {
			String [] classArray = absenceInformationSummaryForm.getClassesName();
			StringBuilder className = new StringBuilder();
			for(int i=0;i<classArray.length;i++){
				className.append(classArray[i]);
				if(i<(classArray.length-1)){
					className.append(",");
				}
			}

			searchCriteria.append(" and attendanceStudent.student.classSchemewise.id in ( ");
			searchCriteria.append(className);
			searchCriteria.append(" ) ");
		}
		
		searchCriteria.append( " and attendanceStudent.attendance.attendanceDate between '");
				
				searchCriteria.append(CommonUtil
						.ConvertStringToSQLDate(absenceInformationSummaryForm
								.getStartDate()));
				searchCriteria.append("'and '");
				searchCriteria.append(CommonUtil
						.ConvertStringToSQLDate(absenceInformationSummaryForm
								.getEndDate())); 
								searchCriteria.append("'");	
		log.info("exit of  getCommonQuery of AbsenceInformationSummarHelper class.");
		return searchCriteria;
	}

	/**
	 * Constructs the monthly absence information query.
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	public String getMonthlyAbsenceInformationSummaryQuery(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into getMonthlyAbsenceInformationSummaryQuery of AbsenceInformationSummarHelper class.");
		StringBuffer searchCriteria = getCommonQuery(absenceInformationSummaryForm);
	
		 String absenceInfoSummaryQuery = "	select attendanceStudent.student, attendanceStudent.student.admAppln, attendanceStudent.attendance.subject, "
				+ " sum(attendanceStudent.attendance.hoursHeld) , sum(attendanceStudent.attendance.hoursHeldMonthly) "
				+ "   from AttendanceStudent attendanceStudent  "
				+ "   where attendanceStudent.attendance.isCanceled = 0  " +
						" and attendanceStudent.student.admAppln.isCancelled = 0 "
				+ "  and attendanceStudent.attendance.isMonthlyAttendance =1  "
				+ "  and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ absenceInformationSummaryForm.getAcademicYear()
				+ searchCriteria
				+ " group by attendanceStudent.student.id,attendanceStudent.attendance.subject.id ";
		 log.info("exit of  getMonthlyAbsenceInformationSummaryQuery of AbsenceInformationSummarHelper class.");

		return absenceInfoSummaryQuery;
	}
	
	/**
	 * Constructs a query for getting subject names.
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	public String getSubjectListMonthly(
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into getSubjectListMonthly of AbsenceInformationSummarHelper class.");
		StringBuffer searchCriteria = getCommonQuery(absenceInformationSummaryForm);
	
		
		 String absenceInfoSummaryQuery = "	select attendanceStudent.attendance.subject.name,attendanceStudent.attendance.subject.code "				
				+ "   from AttendanceStudent attendanceStudent  "
				+ "   where attendanceStudent.attendance.isCanceled = 0  "
				+ "  and attendanceStudent.attendance.isMonthlyAttendance =1  "
				+ "  and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ absenceInformationSummaryForm.getAcademicYear()
				+ searchCriteria
				+ " group by attendanceStudent.attendance.subject.id ";
		 log.info("exit of  getSubjectListMonthly of AbsenceInformationSummarHelper class.");
		return absenceInfoSummaryQuery;
	}

	/**Converts from BO to To for monthly attendance.
	 * @param monthlyAbsenceInformationList
	 * @param absenceInformationSummaryForm
	 * @return
	 */
	public List<AbsenceInformationSummaryTO> convertBotoTOMonthly(
			List monthlyAbsenceInformationList,
			AbsenceInformationSummaryForm absenceInformationSummaryForm) {
		log.info("entering into convertBotoTOMonthly of AbsenceInformationSummarHelper class.");

		List<AbsenceInformationSummaryTO> absenceInformationToList = new ArrayList<AbsenceInformationSummaryTO>();
		Map<Integer, AbsenceInformationSummaryTO> absenceInfoMap = new HashMap<Integer, AbsenceInformationSummaryTO>();
		if (monthlyAbsenceInformationList != null) {
			Iterator absenceInformationIterator = monthlyAbsenceInformationList
					.iterator();

			while (absenceInformationIterator.hasNext()) {
				Object[] absenceInformation = (Object[]) absenceInformationIterator
						.next();
				AbsenceInformationSummaryTO absenceInformationSummaryTO = null;
				
				Long classesConducted = (Long) absenceInformation[4];
				Long classesAttended = (Long) absenceInformation[3];
				
				if (absenceInfoMap
						.containsKey(((Student) absenceInformation[0]).getId())) {

					absenceInformationSummaryTO = absenceInfoMap
							.get(((Student) absenceInformation[0]).getId());
					List<AbsenceInfoMapTO> subjectList = absenceInformationSummaryTO
							.getSubjectMaplist();
					Iterator<AbsenceInfoMapTO> subjectIterator = subjectList
							.iterator();
					Subject subject = ((Subject) absenceInformation[2]);
					while (subjectIterator.hasNext()) {
						AbsenceInfoMapTO absenceInfoMapTO = (AbsenceInfoMapTO) subjectIterator
								.next();
						if (absenceInfoMapTO.getTitle().trim().equals(
								subject.getName()+"("+subject.getCode()+")")) {
							if (absenceInfoMapTO.getValue().trim().length() > 0) {

								Long absentHours = Long.valueOf(
										absenceInfoMapTO.getValue())
										+ (classesConducted - classesAttended);
								absenceInfoMapTO.setValue(String
										.valueOf(absentHours));
							} else {
								absenceInfoMapTO.setValue(String
										.valueOf(classesConducted
												- classesAttended));
							}

							absenceInfoMapTO.setValue(String
									.valueOf(absenceInformation[3]));
						}

					}

				} else {

					absenceInformationSummaryTO = new AbsenceInformationSummaryTO();

					AdmAppln admAppln = ((AdmAppln) absenceInformation[1]);
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

					absenceInformationSummaryTO.setStudentName(applicantName.toString());
					Student student = ((Student) absenceInformation[0]);
					absenceInformationSummaryTO.setRegisterNo(student
							.getRegisterNo()
							+ "\\" + student.getRollNo());

					Subject subject = ((Subject) absenceInformation[2]);

					Iterator<String> subjectIterator = absenceInformationSummaryForm
							.getSubjectList().iterator();

					List<AbsenceInfoMapTO> subjectList = new ArrayList<AbsenceInfoMapTO>();
					while (subjectIterator.hasNext()) {
						String subjectName = (String) subjectIterator.next();
						AbsenceInfoMapTO absenceInfoMapTO = new AbsenceInfoMapTO();
						if (subjectName.trim().equals(subject.getName()+"("+subject.getCode()+")")) {
							absenceInfoMapTO.setTitle(subjectName);
							
							absenceInfoMapTO.setValue(String
									.valueOf(classesConducted - classesAttended));
						} else {
							absenceInfoMapTO.setTitle(subjectName);
							absenceInfoMapTO.setValue("");
						}
						subjectList.add(absenceInfoMapTO);
					}
					absenceInformationSummaryTO.setSubjectMaplist(subjectList);
					absenceInfoMap.put(student.getId(),
							absenceInformationSummaryTO);
				}

			}
		}
		absenceInformationToList.addAll(absenceInfoMap.values());
		log.info("exit of  convertBotoTOMonthly of AbsenceInformationSummarHelper class.");
		return absenceInformationToList;
	}
}