package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.AttendenceFinalSummaryForm;
import com.kp.cms.to.reports.AttendanceFinalSummaryReportTO;
import com.kp.cms.to.reports.StudentFinalSummaryTO;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceFinalSummaryHelper {

	private static volatile AttendanceFinalSummaryHelper attendanceFinalSummaryHelper = null;
	private static final Log log = LogFactory.getLog(AttendanceFinalSummaryHelper.class);
	private AttendanceFinalSummaryHelper() {

	}

	/**
	 * @return single instance of attendanceFinalSummaryHelper object.
	 */
	public static AttendanceFinalSummaryHelper getInstance() {
		if (attendanceFinalSummaryHelper == null) {
			attendanceFinalSummaryHelper = new AttendanceFinalSummaryHelper();
		}
		return attendanceFinalSummaryHelper;
	}

	/**
	 * Get the common search criteria query.
	 * 
	 * @param attendenceFinalSummaryForm
	 * @return Query string.
	 */
	private static String commonSearch(
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) {
		log.info("entering into commonSearch of AttendanceFinalSummaryHelper class.");
		StringBuffer searchCriteria = new StringBuffer();

//		if (attendenceFinalSummaryForm.getCourseId().trim().length() > 0) {
//			String course = " and attendanceStudent.student.admAppln.course.id = "
//					+ attendenceFinalSummaryForm.getCourseId();
//			searchCriteria.append(course);
//		}
//
//		if (attendenceFinalSummaryForm.getProgramId().trim().length() > 0) {
//			String program = " and attendanceStudent.student.admAppln.course.program.id = "
//					+ attendenceFinalSummaryForm.getProgramId();
//			searchCriteria.append(program);
//		}

		
		if (attendenceFinalSummaryForm.getClassesName() != null && attendenceFinalSummaryForm.getClassesName().length > 0) {
			String [] tempClassesArray = attendenceFinalSummaryForm.getClassesName();
			StringBuilder classes = new StringBuilder();
			for(int i=0;i<tempClassesArray.length;i++){
				classes.append(tempClassesArray[i]);
				 if(i<(tempClassesArray.length-1)){
					 classes.append(",");
				 }
			}
		String classCodes = " and attendanceStudent.student.classSchemewise.classes.id in ("+ classes +")";
		searchCriteria = searchCriteria.append(classCodes);
		}
		
//		if (attendenceFinalSummaryForm.getSemister().trim().length() > 0) {
//			String semister = " and attendanceStudent.student.classSchemewise.classes.termNumber = "
//					+ attendenceFinalSummaryForm.getSemister();
//			searchCriteria.append(semister);
//		}

		if (attendenceFinalSummaryForm.getAttendanceType().length > 0) {
			String[] tempArray = attendenceFinalSummaryForm.getAttendanceType();
			StringBuffer attType = new StringBuffer();
			for (int index = 0; index < tempArray.length; index++) {
				attType.append(tempArray[index]);
				if (index < (tempArray.length - 1)) {
					attType.append(',');
				}

			}

			String attendanceType = " and attendanceStudent.attendance.attendanceType.id in ("
					+ attType + ")";
			searchCriteria.append(attendanceType);
		}
		if (attendenceFinalSummaryForm.getActivityId() != null && attendenceFinalSummaryForm.getActivityId().trim().length() > 0) {
			String activityId = " and attendanceStudent.attendance.activity <> null and attendanceStudent.attendance.activity.id = "
					+ attendenceFinalSummaryForm.getActivityId();
			searchCriteria.append(activityId);
		} else {
			String activityId = " and attendanceStudent.attendance.isActivityAttendance = 0 ";

			searchCriteria.append(activityId);
		}

		if (attendenceFinalSummaryForm.getStartDate().trim().length() > 0) {
			String startDate = " and attendanceStudent.attendance.attendanceDate between '"
					+ CommonUtil
							.ConvertStringToSQLDate(attendenceFinalSummaryForm
									.getStartDate())
					+ "'"
					+ " and "
					+ "'"
					+ CommonUtil
							.ConvertStringToSQLDate(attendenceFinalSummaryForm
									.getEndDate()) + "'";
			searchCriteria.append(startDate);
		}
		log.info("exit of  commonSearch of AttendanceFinalSummaryHelper class.");
		return searchCriteria.toString();
	}

	/**
	 * Get search query for attendance final summary report.
	 * 
	 * @param attendenceFinalSummaryForm
	 *            .
	 * @return 
	 */
	public String getAttendanceFinalSummaryQuery(
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) {
		log.info("entering into getAttendanceFinalSummaryQuery of AttendanceFinalSummaryHelper class.");
		String commonSearch = commonSearch(attendenceFinalSummaryForm);

		String conductedQuery = "from AttendanceStudent attendanceStudent where "
				+ "attendanceStudent.attendance.isCanceled = 0 "
				+ "and attendanceStudent.attendance.isMonthlyAttendance = 0 "
				+ "and attendanceStudent.student.admAppln.isCancelled = 0 "
				+ "and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ attendenceFinalSummaryForm.getAcademicYear() + commonSearch;
		log.info("exit of  getAttendanceFinalSummaryQuery of AttendanceFinalSummaryHelper class.");
		return conductedQuery;

	}

	/**
	 * @param conductedQueryList
	 * @param attendenceFinalSummaryForm
	 * @return List of AttendanceFinalSummaryReportTO object.
	 */
	public List<AttendanceFinalSummaryReportTO> getAttendanceFinalSummaryReportTOListFromMap(
			List<AttendanceStudent> conductedQueryList,
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) {
		log.info("entering into getAttendanceFinalSummaryReportTOListFromMap of AttendanceFinalSummaryHelper class.");
		Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentSummaryTOMap = getStudentMapFromList(
				conductedQueryList, attendenceFinalSummaryForm);

		List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryReportTOList = new ArrayList<AttendanceFinalSummaryReportTO>();

		Iterator<Map<Integer, StudentFinalSummaryTO>> studentSummaryMapValues = studentSummaryTOMap
				.values().iterator();

		while (studentSummaryMapValues.hasNext()) {
			Map<Integer, StudentFinalSummaryTO> subjectMap = (Map<Integer, StudentFinalSummaryTO>) studentSummaryMapValues
					.next();
			Iterator<StudentFinalSummaryTO> subjectMapValues = subjectMap
					.values().iterator();
			AttendanceFinalSummaryReportTO attendanceFinalSummaryReportTO = new AttendanceFinalSummaryReportTO();
			boolean isStudentNameSet = false;
			while (subjectMapValues.hasNext()) {
				StudentFinalSummaryTO studentFinalSummaryTO = (StudentFinalSummaryTO) subjectMapValues
						.next();
				if (!isStudentNameSet) {
					AdmAppln admAppln = studentFinalSummaryTO.getAdmAppln();

					setStudentNameToTO(attendanceFinalSummaryReportTO,
							studentFinalSummaryTO, admAppln);
					isStudentNameSet = true;
				}

				float percentage = (studentFinalSummaryTO.getClassesPresent() * 100)
						/ studentFinalSummaryTO.getConductedClasses();

				setPercentageDataToTO(attendanceFinalSummaryReportTO,
						percentage);

			}
			attendanceFinalSummaryReportTOList
					.add(attendanceFinalSummaryReportTO);
		}
		log.info("exit of  getAttendanceFinalSummaryReportTOListFromMap of AttendanceFinalSummaryHelper class.");
		return attendanceFinalSummaryReportTOList;

	}

	/**
	 * set the percentage data to TO
	 * 
	 * @param attendanceFinalSummaryReportTO
	 * @param percentage
	 */
	private void setPercentageDataToTO(
			AttendanceFinalSummaryReportTO attendanceFinalSummaryReportTO,
			float percentage) {
		log.info("entering into setPercentageDataToTO of AttendanceFinalSummaryHelper class.");
		if (percentage >= 85) {
			attendanceFinalSummaryReportTO
					.setAboveEightyFive(attendanceFinalSummaryReportTO
							.getAboveEightyFive() + 1);
		} else if (percentage < 85 && percentage >= 80) {
			attendanceFinalSummaryReportTO
					.setAboveEighty(attendanceFinalSummaryReportTO
							.getAboveEighty() + 1);

		} else if (percentage < 80 && percentage >= 75) {
			attendanceFinalSummaryReportTO
					.setAboveSeventyFive(attendanceFinalSummaryReportTO
							.getAboveSeventyFive() + 1);

		} else if (percentage < 75 && percentage >= 70) {
			attendanceFinalSummaryReportTO
					.setAboveSeventy(attendanceFinalSummaryReportTO
							.getAboveSeventy() + 1);

		} else if (percentage < 70 && percentage >= 65) {
			attendanceFinalSummaryReportTO
					.setAboveSixtyFive(attendanceFinalSummaryReportTO
							.getAboveSixtyFive() + 1);

		} else if (percentage < 65 && percentage >= 60) {
			attendanceFinalSummaryReportTO
					.setAboveSixty(attendanceFinalSummaryReportTO
							.getAboveSixty() + 1);

		} else if (percentage < 60) {
			attendanceFinalSummaryReportTO
					.setBelowSixty(attendanceFinalSummaryReportTO
							.getBelowSixty() + 1);

		}
		log.info("exit of  setPercentageDataToTO of AttendanceFinalSummaryHelper class.");
	}

	/**
	 * Converts from List to Map<Integer, Map<Integer, StudentFinalSummaryTO>>
	 * StudentId and Subject Id as key.
	 * 
	 * @param conductedQueryList
	 * @return
	 */
	private Map<Integer, Map<Integer, StudentFinalSummaryTO>> getStudentMapFromList(
			List<AttendanceStudent> conductedQueryList,
			AttendenceFinalSummaryForm attendanceFinalSummaryForm) {
		log.info("entering into getStudentMapFromList of AttendanceFinalSummaryHelper class.");
		Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentSummaryMap = new HashMap<Integer, Map<Integer, StudentFinalSummaryTO>>();

		if (conductedQueryList != null) {
			Iterator<AttendanceStudent> conductedQueryIterator = conductedQueryList
					.iterator();

			while (conductedQueryIterator.hasNext()) {
				AttendanceStudent attendanceStudent = conductedQueryIterator
						.next();
				Student student = attendanceStudent.getStudent();
				AdmAppln admAppln = attendanceStudent.getStudent()
						.getAdmAppln();
				Attendance attendance = attendanceStudent.getAttendance();
				Map<Integer, StudentFinalSummaryTO> subjectTOMap = null;

				if (studentSummaryMap.containsKey(student.getId())) {
					subjectTOMap = studentSummaryMap.get(student.getId());
					StudentFinalSummaryTO studentFinalSummaryTO = null;
					if (attendance.getIsActivityAttendance()) {

						if (subjectTOMap.containsKey(attendance.getActivity()
								.getId())) {
							studentFinalSummaryTO = subjectTOMap.get(attendance
									.getActivity().getId());
							setDataToTO(attendanceFinalSummaryForm,
									attendanceStudent, studentFinalSummaryTO);

						} else {
							studentFinalSummaryTO = new StudentFinalSummaryTO();
							studentFinalSummaryTO.setAdmAppln(admAppln);
							studentFinalSummaryTO.setStudent(student);
							setDataToTO(attendanceFinalSummaryForm,
									attendanceStudent, studentFinalSummaryTO);

							subjectTOMap.put(attendance.getActivity().getId(),
									studentFinalSummaryTO);
						}

					} else {

						if (subjectTOMap.containsKey(attendance.getSubject()
								.getId())) {
							studentFinalSummaryTO = subjectTOMap.get(attendance
									.getSubject().getId());
							setDataToTO(attendanceFinalSummaryForm,
									attendanceStudent, studentFinalSummaryTO);

						} else {
							studentFinalSummaryTO = new StudentFinalSummaryTO();
							studentFinalSummaryTO.setAdmAppln(admAppln);
							studentFinalSummaryTO.setStudent(student);
							setDataToTO(attendanceFinalSummaryForm,
									attendanceStudent, studentFinalSummaryTO);

							subjectTOMap.put(attendance.getSubject().getId(),
									studentFinalSummaryTO);
						}
						subjectTOMap.put(attendance.getSubject().getId(),
								studentFinalSummaryTO);
					}

					studentSummaryMap.put(student.getId(), subjectTOMap);

				} else {
					subjectTOMap = new HashMap<Integer, StudentFinalSummaryTO>();
					StudentFinalSummaryTO studentFinalSummaryTO = new StudentFinalSummaryTO();
					studentFinalSummaryTO.setAdmAppln(admAppln);
					studentFinalSummaryTO.setStudent(student);
					setDataToTO(attendanceFinalSummaryForm, attendanceStudent,
							studentFinalSummaryTO);

					if (attendance.getIsActivityAttendance()) {
						subjectTOMap.put(attendance.getActivity().getId(),
								studentFinalSummaryTO);
					} else {
						subjectTOMap.put(attendance.getSubject().getId(),
								studentFinalSummaryTO);
					}

					studentSummaryMap.put(student.getId(), subjectTOMap);
				}

			}
		}
		log.info("exit of  getStudentMapFromList of AttendanceFinalSummaryHelper class.");
		return studentSummaryMap;

	}

	/**
	 * set the data to TO.
	 * 
	 * @param attendanceFinalSummaryForm
	 * @param attendanceStudent
	 * @param studentFinalSummaryTO
	 */
	private void setDataToTO(
			AttendenceFinalSummaryForm attendanceFinalSummaryForm,
			AttendanceStudent attendanceStudent,
			StudentFinalSummaryTO studentFinalSummaryTO) {
		log.info("entering into setDataToTO of AttendanceFinalSummaryHelper class.");
		studentFinalSummaryTO.setConductedClasses(studentFinalSummaryTO
				.getConductedClasses() + 1);
		if (attendanceStudent.getIsPresent()) {
			studentFinalSummaryTO.setClassesPresent(studentFinalSummaryTO
					.getClassesPresent() + 1);
		} else {
			if (attendanceFinalSummaryForm.getLeaveType() != null
					&& attendanceFinalSummaryForm.getLeaveType()) {
				studentFinalSummaryTO.setClassesPresent(studentFinalSummaryTO
						.getClassesPresent() + 1);
			}
		}
		log.info("exit of  setDataToTO of AttendanceFinalSummaryHelper class.");
	}

	/**
	 * Constructs monthly attendance final summary query.
	 * 
	 * @param attendenceFinalSummaryForm
	 * @return
	 */
	public String getAttendanceFinalSummaryQueryForMonthly(
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) {
		log.info("entering into getAttendanceFinalSummaryQueryForMonthly of AttendanceFinalSummaryHelper class.");
		String commonSearch = commonSearch(attendenceFinalSummaryForm);

		String conductedQuery = " from AttendanceStudent attendanceStudent where "
				+ "attendanceStudent.attendance.isCanceled = 0 and (attendanceStudent.student.isHide = 0 or attendanceStudent.student.isHide is null)   "
				+ "and attendanceStudent.attendance.isMonthlyAttendance = 1 "
				+ "and attendanceStudent.student.admAppln.isCancelled = 0 "
				+ "and attendanceStudent.student.classSchemewise.curriculumSchemeDuration.academicYear = "
				+ attendenceFinalSummaryForm.getAcademicYear() + commonSearch;
		log.info("exit of  getAttendanceFinalSummaryQueryForMonthly of AttendanceFinalSummaryHelper class.");
		return conductedQuery;
	}

	/**
	 * Constructs List of AttendanceFinalSummaryReportTO objects.
	 * 
	 * @param conductedQueryList
	 * @param attendenceFinalSummaryForm
	 * @return
	 */
	public List<AttendanceFinalSummaryReportTO> getAttendanceFinalSummaryReportTOListForMonthly(
			List<AttendanceStudent> conductedQueryList,
			AttendenceFinalSummaryForm attendenceFinalSummaryForm) {
		log.info("entering into getAttendanceFinalSummaryReportTOListForMonthly of AttendanceFinalSummaryHelper class.");

		Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentSummaryTOMap = getMonthlyStudentMapFromList(conductedQueryList);

		List<AttendanceFinalSummaryReportTO> attendanceFinalSummaryReportTOList = new ArrayList<AttendanceFinalSummaryReportTO>();

		Iterator<Map<Integer, StudentFinalSummaryTO>> studentSummaryMapValues = studentSummaryTOMap
				.values().iterator();

		while (studentSummaryMapValues.hasNext()) {
			Map<Integer, StudentFinalSummaryTO> subjectMap = (Map<Integer, StudentFinalSummaryTO>) studentSummaryMapValues
					.next();
			Iterator<StudentFinalSummaryTO> subjectMapValues = subjectMap
					.values().iterator();
			AttendanceFinalSummaryReportTO attendanceFinalSummaryReportTO = new AttendanceFinalSummaryReportTO();
			boolean isStudentNameSet = false;
			while (subjectMapValues.hasNext()) {
				StudentFinalSummaryTO studentFinalSummaryTO = (StudentFinalSummaryTO) subjectMapValues
						.next();
				if (!isStudentNameSet) {
					AdmAppln admAppln = studentFinalSummaryTO.getAdmAppln();

					setStudentNameToTO(attendanceFinalSummaryReportTO,
							studentFinalSummaryTO, admAppln);
					isStudentNameSet = true;
				}

				float percentage = (studentFinalSummaryTO.getClassesPresent() * 100)
						/ studentFinalSummaryTO.getConductedClasses();

				setPercentageDataToTO(attendanceFinalSummaryReportTO,
						percentage);

			}
			attendanceFinalSummaryReportTOList
					.add(attendanceFinalSummaryReportTO);
		}
		log.info("exit of  getAttendanceFinalSummaryReportTOListForMonthly of AttendanceFinalSummaryHelper class.");
		return attendanceFinalSummaryReportTOList;

	}

	/**
	 * Set the student name to TO
	 * 
	 * @param attendanceFinalSummaryReportTO
	 * @param studentFinalSummaryTO
	 * @param admAppln
	 */
	private void setStudentNameToTO(
			AttendanceFinalSummaryReportTO attendanceFinalSummaryReportTO,
			StudentFinalSummaryTO studentFinalSummaryTO, AdmAppln admAppln) {
		log.info("entering into setStudentNameToTO of AttendanceFinalSummaryHelper class.");
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

		attendanceFinalSummaryReportTO.setStudentName(applicantName.toString());
		Student student = studentFinalSummaryTO.getStudent();
		attendanceFinalSummaryReportTO.setRegisterNo(student.getRegisterNo()
				+ "\\" + student.getRollNo());
		log.info("exit of  setStudentNameToTO of AttendanceFinalSummaryHelper class.");
	}

	/**
	 * @param conductedQueryList
	 * @param attendanceFinalSummaryForm
	 * @return
	 */
	private Map<Integer, Map<Integer, StudentFinalSummaryTO>> getMonthlyStudentMapFromList(
			List<AttendanceStudent> conductedQueryList) {
		log.info("entering into getMonthlyStudentMapFromList of AttendanceFinalSummaryHelper class.");

		Map<Integer, Map<Integer, StudentFinalSummaryTO>> studentSummaryMap = new HashMap<Integer, Map<Integer, StudentFinalSummaryTO>>();

		if (conductedQueryList != null) {
			Iterator<AttendanceStudent> conductedQueryIterator = conductedQueryList
					.iterator();

			while (conductedQueryIterator.hasNext()) {
				AttendanceStudent attendanceStudent = conductedQueryIterator
						.next();
				Student student = attendanceStudent.getStudent();
				AdmAppln admAppln = attendanceStudent.getStudent()
						.getAdmAppln();
				Attendance attendance = attendanceStudent.getAttendance();
				Map<Integer, StudentFinalSummaryTO> subjectTOMap = null;

				if (studentSummaryMap.containsKey(student.getId())) {
					subjectTOMap = studentSummaryMap.get(student.getId());
					StudentFinalSummaryTO studentFinalSummaryTO = null;
					if (attendance.getIsActivityAttendance()) {

						if (subjectTOMap.containsKey(attendance.getActivity()
								.getId())) {
							studentFinalSummaryTO = subjectTOMap.get(attendance
									.getActivity().getId());
							studentFinalSummaryTO
									.setClassesPresent(studentFinalSummaryTO
											.getClassesPresent()
											+ attendance.getHoursHeld());
							studentFinalSummaryTO
									.setConductedClasses(studentFinalSummaryTO
											.getConductedClasses()
											+ attendance.getHoursHeldMonthly());

						} else {
							studentFinalSummaryTO = new StudentFinalSummaryTO();
							studentFinalSummaryTO.setAdmAppln(admAppln);
							studentFinalSummaryTO.setStudent(student);
							studentFinalSummaryTO.setClassesPresent(attendance
									.getHoursHeld());
							studentFinalSummaryTO
									.setConductedClasses(attendance
											.getHoursHeldMonthly());

							subjectTOMap.put(attendance.getActivity().getId(),
									studentFinalSummaryTO);
						}

					} else {

						if (subjectTOMap.containsKey(attendance.getSubject()
								.getId())) {
							studentFinalSummaryTO = subjectTOMap.get(attendance
									.getSubject().getId());
							studentFinalSummaryTO
									.setClassesPresent(studentFinalSummaryTO
											.getClassesPresent()
											+ attendance.getHoursHeld());
							studentFinalSummaryTO
									.setConductedClasses(studentFinalSummaryTO
											.getConductedClasses()
											+ attendance.getHoursHeldMonthly());
						} else {
							studentFinalSummaryTO = new StudentFinalSummaryTO();
							studentFinalSummaryTO.setAdmAppln(admAppln);
							studentFinalSummaryTO.setStudent(student);
							studentFinalSummaryTO.setClassesPresent(attendance
									.getHoursHeld());
							studentFinalSummaryTO
									.setConductedClasses(attendance
											.getHoursHeldMonthly());
							subjectTOMap.put(attendance.getSubject().getId(),
									studentFinalSummaryTO);
						}
						subjectTOMap.put(attendance.getSubject().getId(),
								studentFinalSummaryTO);
					}

					studentSummaryMap.put(student.getId(), subjectTOMap);

				} else {
					subjectTOMap = new HashMap<Integer, StudentFinalSummaryTO>();
					StudentFinalSummaryTO studentFinalSummaryTO = new StudentFinalSummaryTO();
					studentFinalSummaryTO.setAdmAppln(admAppln);
					studentFinalSummaryTO.setStudent(student);
					studentFinalSummaryTO.setClassesPresent(attendance
							.getHoursHeld());
					studentFinalSummaryTO.setConductedClasses(attendance
							.getHoursHeldMonthly());
					if (attendance.getIsActivityAttendance()) {
						subjectTOMap.put(attendance.getActivity().getId(),
								studentFinalSummaryTO);
					} else {
						subjectTOMap.put(attendance.getSubject().getId(),
								studentFinalSummaryTO);
					}

					studentSummaryMap.put(student.getId(), subjectTOMap);
				}

			}
		}
		log.info("exit of  getMonthlyStudentMapFromList of AttendanceFinalSummaryHelper class.");
		return studentSummaryMap;
	}
}