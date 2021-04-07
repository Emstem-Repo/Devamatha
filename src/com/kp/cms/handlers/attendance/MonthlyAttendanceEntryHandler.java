package com.kp.cms.handlers.attendance;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.BatchStudent;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.MonthlyAttendanceEntryForm;
import com.kp.cms.helpers.attendance.AttendanceTypeHelper;
import com.kp.cms.to.attendance.AttendanceTypeMandatoryTO;
import com.kp.cms.to.attendance.AttendanceTypeTO;
import com.kp.cms.to.attendance.HoursHeld;
import com.kp.cms.to.attendance.HourseHeldTO;
import com.kp.cms.to.attendance.MonthlyAttendanceDaysTO;
import com.kp.cms.to.attendance.MonthlyAttendanceTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.IAttendanceTypeTransaction;
import com.kp.cms.transactions.attandance.IMonthlyAttendanceTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceTypeTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.MonthlyAttendanceTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class MonthlyAttendanceEntryHandler {

	private static volatile MonthlyAttendanceEntryHandler monthlyAttendanceEntryHandler = null;
	
	private static final Log log = LogFactory.getLog(MonthlyAttendanceEntryHandler.class);
	
	private MonthlyAttendanceEntryHandler() {

	}

	/**
	 * @return single instance of the {@link MonthlyAttendanceEntryHandler}
	 *         object.
	 */
	public static MonthlyAttendanceEntryHandler getInstance() {
		if (monthlyAttendanceEntryHandler == null) {
			monthlyAttendanceEntryHandler = new MonthlyAttendanceEntryHandler();
		}
		return monthlyAttendanceEntryHandler;
	}

	/**
	 * Returns true if the register no is set for selected class scheme, false
	 * otherwise.
	 * 
	 * @param attendanceEntryForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean getIsRegisterNo(
			MonthlyAttendanceEntryForm attendanceEntryForm)
			throws ApplicationException {
		log.info("entering into getIsRegisterNo of MonthlyAttendanceEntryHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		int	classSchemewiseId = Integer.parseInt(attendanceEntryForm.getClasses()[0]);
		
		log.info("exit of getIsRegisterNo of MonthlyAttendanceEntryHandler class.");
		return activityAttendenceTransaction
				.checkIsRegisterNo(classSchemewiseId);

	}

	/**
	 * Get the List of attendanceClasses by date.
	 * 
	 * @param attendanceEntryForm
	 * @return
	 * @throws ApplicationException
	 */
	public List getAttendanceByDate(
			MonthlyAttendanceEntryForm attendanceEntryForm)
			throws ApplicationException {
		log.info("entering into getAttendanceByDate of MonthlyAttendanceEntryHandler class.");
		IMonthlyAttendanceTransaction monthlyAttendanceTransaction = new MonthlyAttendanceTransactionImpl();
		
		Integer month = Integer.valueOf(attendanceEntryForm.getMonth()) +1;
		
		java.sql.Date fromDate = CommonUtil.ConvertStringToSQLDate("01/"
				+ month + "/"
				+ attendanceEntryForm.getYear1());
		java.sql.Date toDate = CommonUtil.ConvertStringToSQLDate(
				attendanceEntryForm.getNoOfDaysInMonth()
				+ "/"
				+month
				+ "/"
				+ attendanceEntryForm.getYear1());
		List attendanceClassList = monthlyAttendanceTransaction
				.getAttendenceStudents(fromDate, toDate, attendanceEntryForm
						.getSelectedClasses(),attendanceEntryForm);
		log.info("exit of getAttendanceByDate of MonthlyAttendanceEntryHandler class.");
		return attendanceClassList;
	}

	/**
	 * Construct List of MonthlyAttendanceTO object.
	 * 
	 * @param monthlyAttendanceEntryForm
	 * @param daysList 
	 * @return
	 * @throws ApplicationException
	 */
	public List<MonthlyAttendanceTO> constructMonthlyAttendanceTO(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm , List attendanceEntryByDate, List<MonthlyAttendanceDaysTO> daysList)
			throws Exception {
		log
				.info("entering into constructMonthlyAttendanceTO of MonthlyAttendanceEntryHandler class.");
		List<MonthlyAttendanceTO> monthlyAttendanceTOList = new ArrayList<MonthlyAttendanceTO>();

		Iterator attendanceEntryByDateIterator = attendanceEntryByDate
				.iterator();
		Map<Integer, HourseHeldTO> hourseHeldMap = new LinkedHashMap<Integer, HourseHeldTO>();

		Map<Integer, String> daysMap = new LinkedHashMap<Integer, String>();
		while (attendanceEntryByDateIterator.hasNext()) {
			Object[] object = (Object[]) attendanceEntryByDateIterator.next();
			Student student=(Student)object[3];
			if(student.getAdmAppln().getIsCancelled()!=null && !student.getAdmAppln().getIsCancelled()){
				Attendance attendance = (Attendance) object[4];
				java.sql.Date date = (Date) object[1];
				if (!daysMap.containsKey(date.getDate() - 1)) {
					daysMap.put(date.getDate() - 1, String.valueOf(attendance
							.getHoursHeldMonthly()));
					
					daysList.get(date.getDate() - 1).setHoursHeld(
							String.valueOf(attendance.getHoursHeldMonthly()));
					daysList.get(date.getDate() - 1).setHoursHeldOld(
							String.valueOf(attendance.getHoursHeldMonthly()));
				}
				
				if (hourseHeldMap.containsKey(((Student) (object[3])).getId())) {
					
					HourseHeldTO hoursHeldList = hourseHeldMap
					.get(((Student) (object[3])).getId());
					
					LinkedList<HoursHeld> hoursHeldLinkedList = hoursHeldList
					.getHoursHeldList();
					if (hoursHeldLinkedList == null) {
						hoursHeldLinkedList = new LinkedList<HoursHeld>();
					}
					
					hoursHeldList.setAttenndenceId(Integer.valueOf(object[0]
					                                                      .toString()));
					hoursHeldList.setStudent((Student) (object[3]));
					
					HoursHeld hoursHeld = new HoursHeld();
					hoursHeld.setDay(date.getDate());
					hoursHeld.setHourseHeld(object[2].toString());
					hoursHeld.setAttendanceId((Integer) object[0]);
					hoursHeldLinkedList.add(hoursHeld);
					
					hourseHeldMap.put(((Student) (object[3])).getId(),
							hoursHeldList);
				} else {
					HourseHeldTO hourseHeldTO = new HourseHeldTO();
					
					LinkedList<HoursHeld> hoursHeldLinkedList = new LinkedList<HoursHeld>();
					
					HoursHeld hoursHeld = new HoursHeld();
					hoursHeld.setDay(date.getDate());
					
					hoursHeld.setHourseHeld(object[2].toString());
					hoursHeld.setAttendanceId((Integer) object[0]);
					hoursHeldLinkedList.add(hoursHeld);
					
					hourseHeldTO.setHoursHeldList(hoursHeldLinkedList);
					
					hourseHeldTO.setHourseHeld(object[2].toString());
					hourseHeldTO.setAttenndenceId(Integer.valueOf(object[0]
					                                                     .toString()));
					hourseHeldTO.setStudent((Student) (object[3]));
					
					hourseHeldMap
					.put(((Student) (object[3])).getId(), hourseHeldTO);
				}
			}
			}

		Map<Integer, Student> studentByClass = getStudentByClass(monthlyAttendanceEntryForm);
		Iterator<Student> studentValues = studentByClass.values().iterator();

		boolean isFirstTime = false;
		if (monthlyAttendanceEntryForm.getBatchId() != null
				&& monthlyAttendanceEntryForm.getBatchId().trim().length() > 0) {

			while (studentValues.hasNext()) {
				Student student = (Student) studentValues.next();
				constructMonthlyAttendanceTOForBatch(
						monthlyAttendanceEntryForm, monthlyAttendanceTOList,
						hourseHeldMap, isFirstTime, student);

			}

		} else {
			String subjectId = monthlyAttendanceEntryForm.getSubjectId();
			int subject = 0;

			if (subjectId != null && subjectId.length() != 0) {
				subject = Integer.parseInt(subjectId);
			}
			while (studentValues.hasNext()) {
				Student student = (Student) studentValues.next();
				Set<ApplicantSubjectGroup> subGrpList = student.getAdmAppln().getApplicantSubjectGroups();
				if (subject == 0
						|| checkSubjectPresentInGroup(subject, subGrpList)) {

					constructMonthlyAttendanceTOForBatch(
							monthlyAttendanceEntryForm,
							monthlyAttendanceTOList, hourseHeldMap,
							isFirstTime, student);

				}
			}
		}

		log
				.info("exit of constructMonthlyAttendanceTO of MonthlyAttendanceEntryHandler class.");
		
		Collections.sort(monthlyAttendanceTOList);
		
		setMonthlyAttendanceTabOrder(monthlyAttendanceTOList,daysList);
		return monthlyAttendanceTOList;

	}

	/**
	 * @param monthlyAttendanceTOList
	 * @param daysList 
	 */
	private void setMonthlyAttendanceTabOrder(
			List<MonthlyAttendanceTO> monthlyAttendanceTOList, List<MonthlyAttendanceDaysTO> daysList) {
		int[] mainTabIndex = new int[31];
		int rowIndex = 0;
		int columnIndex = 0;
		
		int tabIndexRange = 0;
		int tabOrder = 0;
		boolean isSetMainTabOrder  = false;
		Iterator<MonthlyAttendanceTO> monthlyAttendanceTabIterator = monthlyAttendanceTOList
				.iterator();
		
		while (monthlyAttendanceTabIterator.hasNext()) {
			MonthlyAttendanceTO monthlyAttendanceTO = (MonthlyAttendanceTO) monthlyAttendanceTabIterator
					.next();
			rowIndex = rowIndex + 1;

			if (tabOrder == 0) {

				tabOrder = 2;
			} else {

				tabOrder = rowIndex;
			}

			tabIndexRange = tabIndexRange + 1;
			monthlyAttendanceTO.setDayTabIndex(tabOrder);
			Iterator<HourseHeldTO> hoursHeldIterator = monthlyAttendanceTO
					.getHoursHeldTOList().iterator();
			while (hoursHeldIterator.hasNext()) {
				HourseHeldTO hourseHeldTO = (HourseHeldTO) hoursHeldIterator
						.next();
				columnIndex = columnIndex + 1;

				if (rowIndex == 1 && columnIndex == 1) {
					tabOrder = 2;
				} else if (rowIndex > 1 && columnIndex == 1) {
					tabOrder = tabOrder + 1;
				} else {
					tabOrder = tabOrder + monthlyAttendanceTOList.size() + 1;
				}
				if (!isSetMainTabOrder) {
					mainTabIndex[columnIndex - 1] = tabOrder - 1;
				}
				hourseHeldTO.setTabIndex(tabOrder);

			}
			isSetMainTabOrder = true;
			columnIndex = 0;
		}
		tabIndexRange = 0;
		Iterator<MonthlyAttendanceDaysTO> daysListIterator = daysList.iterator();
		while (daysListIterator.hasNext()) {
			MonthlyAttendanceDaysTO monthlyAttendanceDaysTO = (MonthlyAttendanceDaysTO) daysListIterator
					.next();
			monthlyAttendanceDaysTO.setTabOrder(mainTabIndex[tabIndexRange]);
			tabIndexRange = tabIndexRange+1;
		}
		

	}

	/**
	 * Construct constructMonthlyAttendanceTO for batch and subject.
	 * @param monthlyAttendanceEntryForm
	 * @param monthlyAttendanceTOList
	 * @param hourseHeldMap
	 * @param isFirstTime
	 * @param student
	 */
	// common code extracted from constructMonthlyAttendanceTO
	private void constructMonthlyAttendanceTOForBatch(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm,
			List<MonthlyAttendanceTO> monthlyAttendanceTOList,
			Map<Integer, HourseHeldTO> hourseHeldMap, boolean isFirstTime,
			Student student) {
		StringBuffer studentName = new StringBuffer();

		if (student.getAdmAppln().getPersonalData() != null
				&& student.getAdmAppln().getPersonalData()
						.getFirstName() != null) {
			studentName.append(student.getAdmAppln()
					.getPersonalData().getFirstName());
			studentName.append(' ');
		}
		if (student.getAdmAppln().getPersonalData() != null
				&& student.getAdmAppln().getPersonalData()
						.getMiddleName() != null) {
			studentName.append(student.getAdmAppln()
					.getPersonalData().getMiddleName());
			studentName.append(' ');
		}
		if (student.getAdmAppln().getPersonalData() != null
				&& student.getAdmAppln().getPersonalData()
						.getLastName() != null) {
			studentName.append(student.getAdmAppln()
					.getPersonalData().getLastName());
			studentName.append(' ');
		}
		
		if (hourseHeldMap.containsKey(student.getId())) {
			HourseHeldTO hourseHeldTOFromMap = hourseHeldMap
					.get(student.getId());
			int tempDayStart = 0;
			MonthlyAttendanceTO monthlyAttendanceTO = new MonthlyAttendanceTO();

			monthlyAttendanceTO.setStudent(student);
			if (monthlyAttendanceEntryForm.isRegNoDisplay()) {
				if(hourseHeldTOFromMap
								.getStudent().getRegisterNo()!= null) {
					monthlyAttendanceTO
					.setRegisterNo(hourseHeldTOFromMap
							.getStudent().getRegisterNo());
				}
				
			} else {
				if(hourseHeldTOFromMap
								.getStudent().getRollNo()!= null) {
					monthlyAttendanceTO
					.setRegisterNo(hourseHeldTOFromMap
							.getStudent().getRollNo());
				}
				
			}

		

			monthlyAttendanceTO.setStudentName(studentName
					.toString());
			LinkedList<HoursHeld> hoursHeldList = hourseHeldTOFromMap
					.getHoursHeldList();
			Iterator<HoursHeld> hoursHeldIterator = hoursHeldList
					.iterator();
			List<HourseHeldTO> hoursHeldTOList = new ArrayList<HourseHeldTO>();
			while (hoursHeldIterator.hasNext()) {

				HoursHeld hoursHeld = (HoursHeld) hoursHeldIterator
						.next();
				if (tempDayStart == 0 && hoursHeld.getDay() == 1) {
					tempDayStart = 1;
					isFirstTime = true;
					HourseHeldTO hoursHeldTO = new HourseHeldTO();
					hoursHeldTO.setDay(hoursHeld.getDay());
					hoursHeldTO.setHourseHeld(hoursHeld
							.getHourseHeld());
					hoursHeldTO.setAttenndenceId(hoursHeld
							.getAttendanceId());
					hoursHeldTOList.add(hoursHeldTO);
				} else {

					if (isFirstTime) {

						if (hoursHeld.getDay() == tempDayStart + 1) {
							HourseHeldTO hoursHeldTO = new HourseHeldTO();
							hoursHeldTO.setDay(hoursHeld.getDay());
							hoursHeldTO.setHourseHeld(hoursHeld
									.getHourseHeld());
							hoursHeldTO.setAttenndenceId(hoursHeld
									.getAttendanceId());
							hoursHeldTOList.add(hoursHeldTO);
						} else {
							for (int i = tempDayStart + 1; i <= hoursHeld
									.getDay(); i++) {
								HourseHeldTO hoursHeldTO = new HourseHeldTO();
								hoursHeldTO.setDay(i);
								if (i == hoursHeld.getDay()) {
									hoursHeldTO
											.setHourseHeld(hoursHeld
													.getHourseHeld());
									hoursHeldTO
											.setAttenndenceId(hoursHeld
													.getAttendanceId());
								} else {
									hoursHeldTO.setHourseHeld("");
								}

								hoursHeldTOList.add(hoursHeldTO);

							}
						}
					} else {
						for (int i = 1; i <= hoursHeld.getDay(); i++) {
							HourseHeldTO hoursHeldTO = new HourseHeldTO();
							hoursHeldTO.setDay(i);
							if (i == hoursHeld.getDay()) {
								hoursHeldTO.setHourseHeld(hoursHeld
										.getHourseHeld());
								hoursHeldTO
										.setAttenndenceId(hoursHeld
												.getAttendanceId());
							} else {
								hoursHeldTO.setHourseHeld("");
							}
							hoursHeldTOList.add(hoursHeldTO);
						}
						isFirstTime = true;
					}
					tempDayStart = hoursHeld.getDay();
				}

			}

			if (tempDayStart < monthlyAttendanceEntryForm
					.getNoOfDaysInMonth()) {
				for (int i = tempDayStart + 1; i <= monthlyAttendanceEntryForm
						.getNoOfDaysInMonth(); i++) {
					HourseHeldTO hoursHeldTO = new HourseHeldTO();
					hoursHeldTO.setDay(i);
					hoursHeldTO.setHourseHeld("");
					hoursHeldTOList.add(hoursHeldTO);
				}
			}

			monthlyAttendanceTO.setHoursHeldTOList(hoursHeldTOList);
			monthlyAttendanceTOList.add(monthlyAttendanceTO);
			
			
		} else {

			MonthlyAttendanceTO monthlyAttendanceTO = new MonthlyAttendanceTO();
			monthlyAttendanceTO.setStudent(student);
			monthlyAttendanceTO.setAttendanceId("");
			if (monthlyAttendanceEntryForm.isRegNoDisplay()) {
				if(student
						.getRegisterNo()== null) {
					monthlyAttendanceTO.setRegisterNo("");
				} else {
					monthlyAttendanceTO.setRegisterNo(student
							.getRegisterNo());
				}
				
			} else {
				if(student
						.getRollNo() == null) {
					monthlyAttendanceTO.setRegisterNo("");
				} else {
					monthlyAttendanceTO.setRegisterNo(student
							.getRollNo());
				}
			
			}

			monthlyAttendanceTO
					.setStudentName(studentName.toString());
			List<HourseHeldTO> hoursHeldTOList = new ArrayList<HourseHeldTO>();
			for (int i = 1; i <= monthlyAttendanceEntryForm
					.getNoOfDaysInMonth(); i++) {
				HourseHeldTO hoursHeldTO = new HourseHeldTO();
				hoursHeldTO.setDay(i);
				hoursHeldTO.setHourseHeld("");
				hoursHeldTOList.add(hoursHeldTO);
			}
			monthlyAttendanceTO.setHoursHeldTOList(hoursHeldTOList);
			monthlyAttendanceTOList.add(monthlyAttendanceTO);
		}
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
			Iterator<SubjectGroupSubjects> itr1 = applicantSubjectGroup.getSubjectGroup().getSubjectGroupSubjectses().iterator();
			while(itr1.hasNext()) {
				subjectGroupSubjects = itr1.next();
				if(subjectGroupSubjects.getSubject()!=null && subjectGroupSubjects.getSubject().getId() == subjectId && subjectGroupSubjects.getIsActive() && subjectGroupSubjects.getSubject().getIsActive()) {
					result = true;
					continue label;
				}
					
			}
		}
		log.info("Handler : Leaving checkSubjectPresentInGroup");
		return result;
	}
	
	/**
	 * Persists the monthly attendance.
	 * @param monthlyAttendanceEntryForm
	 * @throws ApplicationException
	 */
	public void persistMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm)
			throws ApplicationException {
		log.info("entering into persistMonthlyAttendance of MonthlyAttendanceEntryHandler class.");
		IMonthlyAttendanceTransaction monthlyAttendanceTransaction = new MonthlyAttendanceTransactionImpl();
		monthlyAttendanceTransaction
				.persistMonthlyAttendance(monthlyAttendanceEntryForm);
		log.info("exit of persistMonthlyAttendance of MonthlyAttendanceEntryHandler class.");
	}

	/**
	 * Returns the student map for the set of classes.
	 * 
	 * @param attendanceEntryForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, Student> getStudentByClass(
			MonthlyAttendanceEntryForm attendanceEntryForm) throws Exception {
		log.info("entering into getStudentByClass of MonthlyAttendanceEntryHandler class.");
		IAttendanceEntryTransaction attendanceEntryTransaction = new AttendanceEntryTransactionImpl();
		
		Map<Integer, Student> studentMap = null;
		
		if (attendanceEntryForm.getBatchId() != null
				&& attendanceEntryForm.getBatchId().length() != 0) {
			int batchId = Integer.parseInt(attendanceEntryForm.getBatchId());
			List<BatchStudent> batchStudentList = attendanceEntryTransaction
					.getStudentByBatch(batchId);
			studentMap = getStudentMapFromBatch(batchStudentList);
		} else {
			List<Student> studentListForClasses = attendanceEntryTransaction
			.getStudentByClass(attendanceEntryForm.getSelectedClasses());
			studentMap = getStudentMapFromList(studentListForClasses);
		}
		
		log.info("exit of getStudentByClass of MonthlyAttendanceEntryHandler class.");

		return studentMap;
	}

	private Map<Integer, Student> getStudentMapFromBatch(
			List<BatchStudent> batchStudentList) {
		log.info("entering into getStudentMapFromBatch of MonthlyAttendanceEntryHandler class.");
		Map<Integer, Student> studentMap = new HashMap<Integer, Student>();
		if(batchStudentList != null) {
		Iterator<BatchStudent> batchStudentIterator = batchStudentList.iterator();
		while (batchStudentIterator.hasNext()) {
			BatchStudent batchStudent = (BatchStudent) batchStudentIterator
					.next();
			if(batchStudent.getStudent().getAdmAppln().getIsCancelled()!=null && !batchStudent.getStudent().getAdmAppln().getIsCancelled())
			studentMap.put(batchStudent.getStudent().getId(), batchStudent.getStudent());
		}
			
		}
		log.info("exit of getStudentMapFromBatch of MonthlyAttendanceEntryHandler class.");
		return studentMap;
	}

	/**
	 * Get the student map from the list.
	 * @param studentListForClasses
	 * @return
	 */
	private Map<Integer, Student> getStudentMapFromList(
			List<Student> studentListForClasses) {
		log.info("entering into getIsRegisterNo of MonthlyAttendanceEntryHandler class.");
		Map<Integer, Student> studentMap = new HashMap<Integer, Student>();
		if(studentListForClasses != null) {
			Iterator<Student> studentIterator = studentListForClasses.iterator();
			while (studentIterator.hasNext()) {
				Student student = (Student) studentIterator.next();
				studentMap.put(student.getId(), student);
			}
		}
		log.info("exit of getAttendanceByDate of MonthlyAttendanceEntryHandler class.");
		return studentMap;
	}

	/**
	 * set the fields which are mandatory for the selected class
	 * 
	 * @param attendanceEntryForm
	 * @throws Exception
	 */
	public void getAttendanceTypeMandatory(
			MonthlyAttendanceEntryForm attendanceEntryForm,String attenType) throws Exception {
		

		log.debug("inside getAttendanceType");
		IAttendanceTypeTransaction iAttendanceTypeTransaction = AttendanceTypeTransactionImpl.getInstance();
		List<AttendanceType> attendanceTypeList = iAttendanceTypeTransaction.getAttendanceType(); 
		List<AttendanceTypeTO> attendanceTypeToList = AttendanceTypeHelper.getInstance().copyAttendanceTypeBosToTosWithMandatory(attendanceTypeList); 
		Map<Integer,String> attendanceMap = new HashMap<Integer, String>();
		AttendanceTypeTO attendanceTypeTO ;
		
		Iterator<AttendanceTypeTO> itr = attendanceTypeToList.iterator();
		attendanceEntryForm.setClassMandatry(CMSConstants.NO);
		attendanceEntryForm.setSubjectMandatory(CMSConstants.NO);
		attendanceEntryForm.setPeriodMandatory(CMSConstants.NO);
		attendanceEntryForm.setTeacherMandatory(CMSConstants.NO);
		attendanceEntryForm.setBatchMandatory(CMSConstants.NO);
		attendanceEntryForm.setActivityTypeMandatory(CMSConstants.NO);
		while(itr.hasNext()) {
			attendanceTypeTO = itr.next();
			attendanceMap.put(attendanceTypeTO.getId(), attendanceTypeTO.getAttendanceTypeName());
			if(attenType != null && attenType.equals(String.valueOf(attendanceTypeTO.getId()))){
					Iterator<AttendanceTypeMandatoryTO> itr1 = attendanceTypeTO.getAttendanceTypeMandatoryTOList().iterator();
					AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
					
					while(itr1.hasNext()) {
						attendanceTypeMandatoryTO = itr1.next();
						if(attendanceTypeMandatoryTO.isActive()) {
							if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.CLASS)){
								attendanceEntryForm.setClassMandatry(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.SUBJECT)) {
								attendanceEntryForm.setSubjectMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.PERIOD)) {
								attendanceEntryForm.setPeriodMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.TEACHER)) {
								attendanceEntryForm.setTeacherMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.BATCH_NAME)) {
								attendanceEntryForm.setBatchMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.ACTIVITY_TYPE)) {
								attendanceEntryForm.setActivityTypeMandatory(CMSConstants.YES);
							} 
						}	
					}
			} 
			
			if(attenType == null && attendanceTypeTO.getDefaultValue().equals(CMSConstants.YES) && attendanceTypeTO.getAttendanceTypeMandatoryTOList() != null) {
					attendanceEntryForm.setAttendanceTypeId(String.valueOf(attendanceTypeTO.getId()));
					Iterator<AttendanceTypeMandatoryTO> itr1 = attendanceTypeTO.getAttendanceTypeMandatoryTOList().iterator();
					AttendanceTypeMandatoryTO attendanceTypeMandatoryTO;
					
					while(itr1.hasNext()) {
						attendanceTypeMandatoryTO = itr1.next();
						if(attendanceTypeMandatoryTO.isActive()) {
							if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.CLASS)){
								attendanceEntryForm.setClassMandatry(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.SUBJECT)) {
								attendanceEntryForm.setSubjectMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.PERIOD)) {
								attendanceEntryForm.setPeriodMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.TEACHER)) {
								attendanceEntryForm.setTeacherMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.BATCH_NAME)) {
								attendanceEntryForm.setBatchMandatory(CMSConstants.YES);
							} else if(attendanceTypeMandatoryTO.getName().equals(CMSConstants.ACTIVITY_TYPE)) {
								attendanceEntryForm.setActivityTypeMandatory(CMSConstants.YES);
							} 
						}	
					}
				}
		}
		attendanceEntryForm.setAttendanceTypes(attendanceMap);
		log.debug("leaving getAttendanceType");
		return;
	
		
		
		
		
	}

	/**
	 * Performs cancel monthly attendance action.
	 * @param monthlyAttendanceEntryForm
	 * @throws ApplicationException
	 */
	public void cancelMonthlyAttendance(
			MonthlyAttendanceEntryForm monthlyAttendanceEntryForm) throws ApplicationException{
		log.info("entering into cancelMonthlyAttendance of MonthlyAttendanceEntryHandler class.");
		IMonthlyAttendanceTransaction monthlyAttendanceTransaction = new MonthlyAttendanceTransactionImpl();
		monthlyAttendanceTransaction.cancelMonthlyAttendance(monthlyAttendanceEntryForm);
		log.info("exit of cancelMonthlyAttendance of MonthlyAttendanceEntryHandler class.");
	}
}