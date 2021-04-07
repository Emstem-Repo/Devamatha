package com.kp.cms.handlers.attendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.LeaveType;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentLeave;
import com.kp.cms.bo.admin.StudentLeaveDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.helpers.attendance.ActivityAttendenceHelper;
import com.kp.cms.helpers.attendance.ApproveLeaveHelper;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.IApproveLeaveTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.ApproveLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Single ton class for the ApproveLeaveHandler
 *
 */
public class ApproveLeaveHandler {

	/**
	 * Represents single ton object of the ApproveLeaveHandler object
	 */
	private static volatile ApproveLeaveHandler approveLeaveHandler = null;
	private static final Log log = LogFactory.getLog(ApproveLeaveHandler.class);
	private ApproveLeaveHandler() {

	}

	/**
	 * @return single ton object of the ApproveLeaveHandler object
	 */
	public static ApproveLeaveHandler getInstance() {
		if (approveLeaveHandler == null) {
			approveLeaveHandler = new ApproveLeaveHandler();
		}
		return approveLeaveHandler;
	}
	
	/**
	 * Returns true if the register no is set for selected class scheme, false
	 * otherwise.
	 * 
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean getIsRegisterNo(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into getIsRegisterNo of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		int classSchemewiseId = Integer.parseInt(approveLeaveForm
				.getClassSchemewiseId());
		log.info("exit of getIsRegisterNo of ApproveLeaveHandler class.");
		return activityAttendenceTransaction
				.checkIsRegisterNo(classSchemewiseId);

	}

	/**
	 * Construct ApproveLeave TO Map object.
	 * @param approveLeaveForm
	 * @param mode
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, List<ApproveLeaveTO>> constructApproveLeave(
			ApproveLeaveForm approveLeaveForm, String mode)
			throws ApplicationException {
		log.info("entering into constructApproveLeave of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<Attendance> attenenceList = approveLeaveTransaction
				.isAttendenceTaken(approveLeaveForm);

		Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = new HashMap<Integer, List<ApproveLeaveTO>>();

		List<ApproveLeaveTO> approveListForNewDates = constructApproveLeaveTOFromAttendence(
				attenenceList, approveLeaveForm);
		approveLeaveToMap.put(1, approveListForNewDates);

		if ("update".equals(mode)) {

			ApproveLeaveForm newApproveLeaveForm = new ApproveLeaveForm();

			ArrayList<String> studentIds = new ArrayList<String>();
			if (approveLeaveForm.getOldRegisterNoEntry() != null) {
				String[] ids = approveLeaveForm.getOldRegisterNoEntry().split(
						",");
				for (String num : ids) {
					studentIds.add(num);
				}
			}

			newApproveLeaveForm.setRegisterNoList(studentIds);
			newApproveLeaveForm.setFromDate(CommonUtil.formatDate(
					approveLeaveForm.getStudentLeaveTO().getStartDate(),
					"dd/MM/yyyy"));
			newApproveLeaveForm.setToDate(CommonUtil.formatDate(
					approveLeaveForm.getStudentLeaveTO().getEndDate(),
					"dd/MM/yyyy"));
			newApproveLeaveForm.setFromPeriod(approveLeaveForm.getFromPeriod());
			newApproveLeaveForm.setToPeriod(approveLeaveForm.getToPeriod());
			newApproveLeaveForm.setClassSchemewiseId(approveLeaveForm
					.getClassSchemewiseId());
			newApproveLeaveForm
					.setRegisterNo(getIsRegisterNo(newApproveLeaveForm));

			List<ApproveLeaveTO> approveListForOldDates = constructApproveLeaveTOFromAttendence(
					attenenceList, newApproveLeaveForm);
			approveLeaveToMap.put(2, approveListForOldDates);
		}
		log.info("exit of constructApproveLeave of ApproveLeaveHandler class.");
		return approveLeaveToMap;
	}
	
	/**
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, List<ApproveLeaveTO>> constructApproveLeaveForDelete(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		
		Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = new HashMap<Integer, List<ApproveLeaveTO>>();
		
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<Attendance> attenenceList = approveLeaveTransaction
				.isAttendenceTaken(approveLeaveForm);
		ApproveLeaveForm newApproveLeaveForm = new ApproveLeaveForm();

		ArrayList<String> studentIds = new ArrayList<String>();
		if (approveLeaveForm.getOldRegisterNoEntry() != null) {
			String[] ids = approveLeaveForm.getOldRegisterNoEntry().split(",");
			for (String num : ids) {
				studentIds.add(num);
			}
		}

		newApproveLeaveForm.setRegisterNoList(studentIds);
		newApproveLeaveForm.setFromDate(CommonUtil.formatDate(approveLeaveForm
				.getStudentLeaveTO().getStartDate(), "dd/MM/yyyy"));
		newApproveLeaveForm.setToDate(CommonUtil.formatDate(approveLeaveForm
				.getStudentLeaveTO().getEndDate(), "dd/MM/yyyy"));
		newApproveLeaveForm.setFromPeriod(approveLeaveForm.getFromPeriod());
		newApproveLeaveForm.setToPeriod(approveLeaveForm.getToPeriod());
		newApproveLeaveForm.setClassSchemewiseId(approveLeaveForm
				.getClassSchemewiseId());
		newApproveLeaveForm.setRegisterNo(getIsRegisterNo(newApproveLeaveForm));

		List<ApproveLeaveTO> approveListForOldDates = constructApproveLeaveTOFromAttendence(
				attenenceList, newApproveLeaveForm);
		approveLeaveToMap.put(2, approveListForOldDates);
		return approveLeaveToMap;

	}

	/**
	 * Construct Approve Leave TO list from Attendance.
	 * @param attenenceList
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	private List<ApproveLeaveTO> constructApproveLeaveTOFromAttendence(
			List<Attendance> attenenceList, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		List<ApproveLeaveTO> approveLeaveToList = new ArrayList<ApproveLeaveTO>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// Converts form date to formatted string date
		String stdate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getFromDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		// get the commaseperated values of periods for start day.
		List<Period> periodListforStartDate = getPeriodListforStartDate(approveLeaveForm);
		Set<Integer> periodsForStartDay = getCommaSeperatedPeriods(periodListforStartDate);

		// get the commaseperated values of periods for end day.
		List<Period> periodListforEndDate = getPeriodListforEndDate(approveLeaveForm);
		Set<Integer>  periodsForEndDay = getCommaSeperatedPeriods(periodListforEndDate);

		// get the commaseperated values of periods for a single day.
		List<Period> periodList = getPeriodList(approveLeaveForm);
		Set<Integer>  periods = getCommaSeperatedPeriods(periodList);

		// increment start day and get the string representation.
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		startCal.add(Calendar.DATE, 1);
		Date incrementalStartDate = startCal.getTime();
		String incrementalStartDateString = formatter
				.format(incrementalStartDate);

		// decrement the end date and get the string representation.
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		endCal.add(Calendar.DATE, -1);
		Date incrementalEndDate = endCal.getTime();
		String incrementalEndDateString = formatter.format(incrementalEndDate);

		if (attenenceList != null && !attenenceList.isEmpty()) {
			if (startDate.compareTo(endDate) == 0) {

				List<AttendanceStudent> attendenceForStartDay = getAttendenceListForStartDay(
						approveLeaveForm.getFromDate(), periodsForStartDay,
						approveLeaveForm);
				approveLeaveToList = ApproveLeaveHelper.getInstance()
						.convertBOtoTO(attendenceForStartDay,
								approveLeaveToList);


			} else {

				List<AttendanceStudent> attendenceForStartDay = getAttendenceListForStartDay(
						approveLeaveForm.getFromDate(), periodsForStartDay,
						approveLeaveForm);
				List<AttendanceStudent> attendenceForEndDay = getAttendenceListForEndDay(
						approveLeaveForm.getToDate(), periodsForEndDay,
						approveLeaveForm);
				List<AttendanceStudent> attendenceFordaysRange = getAttendenceListForDaysRange(
						incrementalStartDateString, incrementalEndDateString,
						periods, approveLeaveForm);

				approveLeaveToList = ApproveLeaveHelper.getInstance()
						.convertBOtoTO(attendenceForStartDay,
								approveLeaveToList);
				approveLeaveToList = ApproveLeaveHelper.getInstance()
						.convertBOtoTO(attendenceForEndDay, approveLeaveToList);
				approveLeaveToList = ApproveLeaveHelper.getInstance()
						.convertBOtoTO(attendenceFordaysRange,
								approveLeaveToList);
			}
		}
		log.info("exit of constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		return approveLeaveToList;

	}

	/**
	 * Get the period set.
	 * @param periodListforStartDate
	 * @return
	 */
	private Set<Integer> getCommaSeperatedPeriods(
			List<Period> periodListforStartDate) {
		log.info("entering into getCommaSeperatedPeriods of ApproveLeaveHandler class.");
		Iterator<Period> periodIterator = periodListforStartDate.iterator();
		Set<Integer> periodsForStartDay = new HashSet<Integer>();
		while (periodIterator.hasNext()) {
			Period period = (Period) periodIterator.next();
			periodsForStartDay.add(period.getId());
		}
		log.info("exit of getCommaSeperatedPeriods of ApproveLeaveHandler class.");
		return periodsForStartDay;
	}

	/**
	 * Get the attendance student list for the start day.
	 * @param startDay
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForStartDay(
			String startDay, Set<Integer>  periods, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into getAttendenceListForStartDay of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceList(startDay, periods, approveLeaveForm);
		log.info("exit of getAttendenceListForStartDay of ApproveLeaveHandler class.");
		return attendenceList;
	}



	/**
	 * Get the attendance list for the end day.
	 * @param startDay
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForEndDay(String startDay,
			Set<Integer>  periods, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into getAttendenceListForEndDay of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceList(startDay, periods, approveLeaveForm);
		log.info("exit of getAttendenceListForEndDay of ApproveLeaveHandler class.");
		return attendenceList;

	}

	/**
	 * Get the attendance list between days.
	 * @param dayAfterStart
	 * @param dayAfterEnd
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForDaysRange(
			String dayAfterStart, String dayAfterEnd, Set<Integer>  periods,
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into getAttendenceListForDaysRange of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceListBetweenDays(dayAfterStart, dayAfterEnd,
						periods, approveLeaveForm);
		log.info("exit of getAttendenceListForDaysRange of ApproveLeaveHandler class.");
		return attendenceList;
	}

	/**
	 * Performs validations for the approve leave entry.
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public ActionMessage validateActivityAttendence(ApproveLeaveForm approveLeaveForm,
			List<String> registerNoList) throws ApplicationException,
			ParseException {
		log.info("entering into validateActivityAttendence of ApproveLeaveHandler class.");
		String errorMessage = "";

		String stdate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getFromDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		int appliedYear = Integer.valueOf(approveLeaveForm.getYear());
		int year=appliedYear+1;

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		int startYear = startCal.get(Calendar.YEAR);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		
		int endYear = endCal.get(Calendar.YEAR);

		if (startYear > appliedYear && startYear > year) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterstartyear";
		} else if (endYear > appliedYear && endYear > year) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterendyear";
		} else if (startYear < appliedYear && startYear < year) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserstartyear";
		} else if (endYear < appliedYear && endYear < year) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserendyear";
		} else if (startDate.compareTo(endDate) > 0) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterenddate";
		}

		if (errorMessage.isEmpty()) {

			Period fromPeriod = ActivityAttendenceHelper.getInstance()
					.getPeriod(
							Integer.valueOf(approveLeaveForm.getFromPeriod()));
			Period toPeriod = ActivityAttendenceHelper.getInstance().getPeriod(
					Integer.valueOf(approveLeaveForm.getToPeriod()));
			
			approveLeaveForm.setPeriodFrom(fromPeriod);
			approveLeaveForm.setPeriodTo(toPeriod);
			String[] periodStartTime =	fromPeriod.getStartTime().split(":");
			startCal.set(Calendar.HOUR, Integer.valueOf(periodStartTime[0]));
			startCal.set(Calendar.MINUTE, Integer.valueOf(periodStartTime[1]));
			startCal.set(Calendar.SECOND, Integer.valueOf(periodStartTime[2]));
			
			String[] periodEndTime = toPeriod.getStartTime().split(":");
			endCal.set(Calendar.HOUR, Integer.valueOf(periodEndTime[0]));
			endCal.set(Calendar.MINUTE, Integer.valueOf(periodEndTime[1]));
			endCal.set(Calendar.SECOND, Integer.valueOf(periodEndTime[2]));
			
			
			if (startCal.compareTo(endCal) > 0) {
				errorMessage = "knowledgepro.attendance.activityattendence.lesstoperiod";
			}
		}
		String invalidRegisterNo = "";
		if (errorMessage.isEmpty()) {
			invalidRegisterNo = ApproveLeaveHelper.getInstance()
					.isRegisterNoValied(approveLeaveForm, registerNoList);
			if (!invalidRegisterNo.isEmpty()) {
				if (approveLeaveForm.isRegisterNo()) {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedregisterno";
				} else {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedrollno";
				}
				
			}
		}
		
		if (errorMessage.isEmpty()) {
			if(isLeaveApproved(approveLeaveForm)) {
				errorMessage = "knowledgepro.attendance.approveleave.leaveapproved";
			} else if(isCocurricularLeave(approveLeaveForm)) {
				errorMessage = "knowledgepro.attendance.approveleave.attendancetaken";
			}
		}
		ActionMessage message  = null;
		if(!errorMessage.isEmpty()) {
			if(invalidRegisterNo.isEmpty() ) {
				message = new ActionMessage(errorMessage );
			} else {
				message = new ActionMessage(errorMessage,invalidRegisterNo);
			}
			 
		}
		log.info("exit of validateActivityAttendence of ApproveLeaveHandler class.");
		return message;
	}

	private boolean isCocurricularLeave(ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into isActivityAttendance of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		log.info("exit of isActivityAttendance of ApproveLeaveHandler class.");
		return approveLeaveTransaction.isCocurricularLeave(approveLeaveForm);
	}

	/**
	 * Performs validations for the approve leave entry.
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 * @throws ParseException
	 */
	public ActionMessage validateModifyAttendence(ApproveLeaveForm approveLeaveForm,
			List<String> registerNoList) throws ApplicationException,
			ParseException {
		log.info("entering into validateModifyAttendence of ApproveLeaveHandler class.");
		String errorMessage = "";

		String stdate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getFromDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		int appliedYear = Integer.valueOf(approveLeaveForm.getYear());
		int year=appliedYear+1;
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		int startYear = startCal.get(Calendar.YEAR);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		
		int endYear = endCal.get(Calendar.YEAR);

		if (startYear > appliedYear && startYear > year) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterstartyear";
		} else if (endYear > appliedYear && endYear > year) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterendyear";
		} else if (startYear < appliedYear && startYear < year) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserstartyear";
		} else if (endYear < appliedYear && endYear < year) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserendyear";
		} else if (startDate.compareTo(endDate) > 0) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterenddate";
		}

		if (errorMessage.isEmpty()) {

			Period fromPeriod = ActivityAttendenceHelper.getInstance()
					.getPeriod(
							Integer.valueOf(approveLeaveForm.getFromPeriod()));
			Period toPeriod = ActivityAttendenceHelper.getInstance().getPeriod(
					Integer.valueOf(approveLeaveForm.getToPeriod()));
			String[] periodStartTime =	fromPeriod.getStartTime().split(":");
			startCal.set(Calendar.HOUR, Integer.valueOf(periodStartTime[0]));
			startCal.set(Calendar.MINUTE, Integer.valueOf(periodStartTime[1]));
			startCal.set(Calendar.SECOND, Integer.valueOf(periodStartTime[2]));
			
			String[] periodEndTime = toPeriod.getStartTime().split(":");
			endCal.set(Calendar.HOUR, Integer.valueOf(periodEndTime[0]));
			endCal.set(Calendar.MINUTE, Integer.valueOf(periodEndTime[1]));
			endCal.set(Calendar.SECOND, Integer.valueOf(periodEndTime[2]));
			
			
			if (startCal.compareTo(endCal) > 0) {
				errorMessage = "knowledgepro.attendance.activityattendence.lesstoperiod";
			}
		}
		String invalidRegisterNo = "";
		if (errorMessage.isEmpty()) {
			invalidRegisterNo = ApproveLeaveHelper.getInstance()
					.isRegisterNoValied(approveLeaveForm, registerNoList);
			if (!invalidRegisterNo.isEmpty()) {
				if (approveLeaveForm.isRegisterNo()) {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedregisterno";
				} else {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedrollno";
				}
				
			}
		}
		
		/*if (errorMessage.isEmpty() && isLeaveApprovedOnModify(approveLeaveForm)) {
				errorMessage = "knowledgepro.attendance.approveleave.leaveapproved";
		}*/
		ActionMessage message  = null;
		if(!errorMessage.isEmpty()) {
			if(invalidRegisterNo.isEmpty() ) {
				message = new ActionMessage(errorMessage );
			} else {
				message = new ActionMessage(errorMessage,invalidRegisterNo);
			}
			 
		}
		log.info("exit of validateModifyAttendence of ApproveLeaveHandler class.");
		return message;
	}
	
	private boolean isLeaveApproved(ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into isLeaveApproved of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();	
		log.info("exit of isLeaveApproved of ApproveLeaveHandler class.");
		return approveLeaveTransaction.isLeaveAlreadyApproved(approveLeaveForm);
		
	}

	
   private boolean isLeaveApprovedOnModify(ApproveLeaveForm approveLeaveForm) throws ApplicationException {
	   log.info("entering into isLeaveApprovedOnModify of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();	
		log.info("exit of isLeaveApprovedOnModify of ApproveLeaveHandler class.");
		return approveLeaveTransaction.isLeaveAlreadyApproved(approveLeaveForm);
		
	}

	/**
	 * Get the period list for the start date.
	 * @param approveLeaveForm0
	 * @return
	 * @throws ApplicationException
	 */
	public List<Period> getPeriodListforStartDate(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into getPeriodListforStartDate of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		Period fromPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getFromPeriod()));
		Period toPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getToPeriod()));

		List<Period> periodList = activityAttendenceTransaction
				.getPeriodListForStartDate(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()), fromPeriod.getStartTime(),
						toPeriod.getStartTime());
		log.info("exit of getPeriodListforStartDate of ApproveLeaveHandler class.");
		return periodList;
	}

	/**
	 * Get the period by period id.
	 * @param periodId
	 * @return
	 * @throws ApplicationException
	 */
	public Period getPeriod(int periodId) throws ApplicationException {
		log.info("entering into getPeriod of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		log.info("exit of getPeriod of ApproveLeaveHandler class.");
		return activityAttendenceTransaction.getPeriod(periodId);
	}

	/**
	 * Get the period list for the end date.
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	private List<Period> getPeriodListforEndDate(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log.info("entering into getPeriodListforEndDate of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		Period toPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getToPeriod()));
		List<Period> periodList = activityAttendenceTransaction
				.getPeriodListForEndDate(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()), toPeriod.getEndTime());
		log.info("exit of getPeriodListforEndDate of ApproveLeaveHandler class.");
		return periodList;
	}

	private List<Period> getPeriodList(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log.info("entering into getPeriodList of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		List<Period> periodList = activityAttendenceTransaction
				.getPeriodList(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()));
		log.info("exit of getPeriodList of ApproveLeaveHandler class.");
		return periodList;
	}

	/**
	 * Persists the approve leave.
	 * @param approveLeaveToMap
	 * @param approveLeaveForm
	 * @param mode
	 * @throws ApplicationException
	 */
	public void updateApproveLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm approveLeaveForm, String mode)
			throws ApplicationException {
		log.info("entering into updateApproveLeave of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();

		StudentLeave studentLeave = new StudentLeave();

		LeaveType leaveType = new LeaveType();
		leaveType.setId(Integer.parseInt(approveLeaveForm.getLeaveType()));
		studentLeave.setLeaveType(leaveType);
		Period startPeriod = new Period();
		Period endPeriod = new Period();
		startPeriod.setId(Integer.parseInt(approveLeaveForm.getFromPeriod()));
		endPeriod.setId(Integer.parseInt(approveLeaveForm.getToPeriod()));

		studentLeave.setStartPeriod(startPeriod);
		studentLeave.setEndPeriod(endPeriod);

		Date startDate = CommonUtil.ConvertStringToSQLDate(approveLeaveForm
				.getFromDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(approveLeaveForm
				.getToDate());
		studentLeave.setStartDate(startDate);
		studentLeave.setEndDate(endDate);

		studentLeave.setCreatedBy(approveLeaveForm.getUserId());
		studentLeave.setCreatedDate(new Date());
		studentLeave.setModifiedBy(approveLeaveForm.getUserId());
		studentLeave.setLastModifiedDate(new Date());
		studentLeave.setFirstApproved(true);
		studentLeave.setSecondApproved(true);
		studentLeave.setLeaveCanceled(false);

		ClassSchemewise classSchemewise = new ClassSchemewise();
		classSchemewise.setId(Integer.parseInt(approveLeaveForm
				.getClassSchemewiseId()));
		studentLeave.setClassSchemewise(classSchemewise);

		boolean isRegnoCheck = approveLeaveForm.isRegisterNo();

		Set<Integer> studentIds = new HashSet<Integer>();
		if (approveLeaveForm.getRegisterNoEntry() != null) {
			String[] ids = approveLeaveForm.getRegisterNoEntry().split(",");
			for (String num : ids) {
				Integer studentId = approveLeaveTransaction.getStudentIdByCourseAnd(
						classSchemewise.getId(), isRegnoCheck, num);
				if (studentId != null)
					studentIds.add(studentId);
			}
		}
		Iterator<Integer> itr = studentIds.iterator();
		StudentLeaveDetails studentLeaveDetails;
		Student student;
		Set<StudentLeaveDetails> set = new HashSet<StudentLeaveDetails>();
		while (itr.hasNext()) {
			studentLeaveDetails = new StudentLeaveDetails();
			student = new Student();
			student.setId(itr.next());
			studentLeaveDetails.setStudent(student);
			set.add(studentLeaveDetails);
		}

		if ("update".equals(mode)) {
			studentLeave.setId(Integer.parseInt(approveLeaveForm.getId()));
		}
		studentLeave.setStudentLeaveDetails(set);
		approveLeaveTransaction.updateApproveLeave(approveLeaveToMap,
				approveLeaveForm, studentLeave, mode);
		log.info("exit of updateApproveLeave of ApproveLeaveHandler class.");
	}

	/**
	 * Get the leave type.
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, String> getLeaveType() throws ApplicationException {
		log.info("entering into getLeaveType of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<LeaveType> leaveList = approveLeaveTransaction.getLeaveType();
		Map<Integer, String> leaveMap = new HashMap<Integer, String>();
		Iterator<LeaveType> iterator = leaveList.iterator();
		LeaveType leaveType;
		while (iterator.hasNext()) {
			leaveType = iterator.next();
			leaveMap.put(leaveType.getId(), leaveType.getName());

		}
		log.info("exit of getLeaveType of ApproveLeaveHandler class.");
		return leaveMap;
	}

	/**
	 * search for the approved leaves.
	 * @param approveLeaveForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public void approvedLeaveSearch(ApproveLeaveForm approveLeaveForm)
			throws  Exception {
		log.info("entering into approvedLeaveSearch of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		Date fromDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getFromDate());
		Date toDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getToDate());
		List<StudentLeave> list = approveLeaveTransaction.getApprovedLeaves(fromDate, toDate);

		List<StudentLeaveTO> leaveList = ApproveLeaveHelper.getInstance().convertStudentLeaveBOtoTO(
				list);
		
		approveLeaveForm.setLeaveList(leaveList);
		log.info("exit of approvedLeaveSearch of ApproveLeaveHandler class.");
	}
	
	/**
	 * search for the approved leaves.
	 * @param approveLeaveForm
	 * @throws DataNotFoundException
	 * @throws Exception
	 */
	public List<StudentLeaveTO>  getApprovedLeaves(ApproveLeaveForm approveLeaveForm)
			throws  Exception {
		log.info("entering into approvedLeaveSearch of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		Date fromDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getFromDate());
		Date toDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getToDate());
		List<StudentLeave> list = approveLeaveTransaction.getApprovedLeaves(fromDate, toDate);

		List<StudentLeaveTO> leaveList = ApproveLeaveHelper.getInstance().convertStudentLeaveBOtoTO(
				list);
		
		log.info("exit of approvedLeaveSearch of ApproveLeaveHandler class.");
		return leaveList;
	}


	/**
	 * delete the leave by id.
	 * @param approveLeaveForm
	 * @param approveLeaveToMap 
	 * @throws Exception
	 */
	public void deleteLeave(ApproveLeaveForm approveLeaveForm, Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap) throws Exception {
		log.info("entering into deleteLeave of ApproveLeaveHandler class.");
		int leaveId = Integer.parseInt(approveLeaveForm.getId());
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		approveLeaveTransaction.deleteLeaveApproval(leaveId,approveLeaveToMap,approveLeaveForm.getUserId());
		log.info("exit of deleteLeave of ApproveLeaveHandler class.");
	}

	/**
	 * edit the leave by id.
	 * @param approveLeaveForm
	 * @throws Exception
	 */
	public void editLeave(ApproveLeaveForm approveLeaveForm) throws Exception {
		log.info("entering into editLeave of ApproveLeaveHandler class.");
		int leaveId = Integer.parseInt(approveLeaveForm.getId());
		StudentLeave studentLeave;
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		studentLeave = approveLeaveTransaction.getLeaveApproval(leaveId);
		ApproveLeaveHelper.getInstance().coypBoToForm(approveLeaveForm,
				studentLeave);
		log.info("exit of editLeave of ApproveLeaveHandler class.");
	}
}