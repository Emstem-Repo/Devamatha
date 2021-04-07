package com.kp.cms.handlers.attendance;

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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessage;

import com.kp.cms.bo.admin.Activity;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.AttendanceType;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.StuCocurrLeave;
import com.kp.cms.bo.admin.StuCocurrLeaveDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.ApproveLeaveForm;
import com.kp.cms.helpers.attendance.ActivityAttendenceHelper;
import com.kp.cms.helpers.attendance.ApproveLeaveHelper;
import com.kp.cms.to.attendance.ActivityAttendenceTO;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.StudentLeaveTO;
import com.kp.cms.transactions.attandance.IActivityAttendenceTransaction;
import com.kp.cms.transactions.attandance.IApproveLeaveTransaction;
import com.kp.cms.transactionsimpl.attendance.ActivityAttendenceTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.ApproveLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ActivityAttendenceHandler {

	private static volatile ActivityAttendenceHandler activityAttendenceHandler = null;
	private static final Log log = LogFactory
			.getLog(ActivityAttendenceHandler.class);

	private ActivityAttendenceHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return singleton class of the ActivityAttendenceHandler
	 */
	public static ActivityAttendenceHandler getInstance() {
		if (activityAttendenceHandler == null) {
			activityAttendenceHandler = new ActivityAttendenceHandler();
		}
		return activityAttendenceHandler;
	}

	/**
	 * Returns true if the register no is set for selected class scheme, false
	 * otherwise.
	 * 
	 * @param activityAttendanceForm
	 * @return
	 * @throws ApplicationException
	 */
	public boolean getIsRegisterNo(ApproveLeaveForm activityAttendanceForm)
			throws ApplicationException {
		log
				.info("entering into getIsRegisterNo of ActivityAttendenceHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		int classSchemewiseId = Integer.parseInt(activityAttendanceForm
				.getClassSchemewiseId());
		log.info("exit of getIsRegisterNo of ActivityAttendenceHandler class.");
		return activityAttendenceTransaction
				.checkIsRegisterNo(classSchemewiseId);

	}

	/**
	 * Performs validations against selected fields from activity attendance
	 * form.
	 * 
	 * @param activityAttendanceForm
	 * @param registerNoList
	 * @return - error message , empty string otherwise.
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionMessage validateActivityAttendence(
			ApproveLeaveForm activityAttendanceForm, List<String> registerNoList)
			throws Exception {
		log.info("entering into validateActivityAttendence of ActivityAttendenceHandler class.");
		String errorMessage = "";
		ActionMessage message = null;
		// DateFormat inputDateFormat = new SimpleDateFormat("dd/MM/yyyy");

		String stdate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getFromDate(), "dd/MM/yyyy",
				"MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		int appliedYear = Integer.valueOf(activityAttendanceForm.getYear());

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		int startYear = startCal.get(Calendar.YEAR);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		startCal.setTime(endDate);
		int endYear = endCal.get(Calendar.YEAR);

//		if (startYear > appliedYear) {
//			errorMessage = "knowledgepro.attendance.activityattendence.greaterstartyear";
//		} else if (endYear > appliedYear) {
//			errorMessage = "knowledgepro.attendance.activityattendence.greaterendyear";
//		}
//		else
			if (startYear < appliedYear) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserstartyear";
		} else if (endYear < appliedYear) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserendyear";
		} else if (startDate.compareTo(endDate) > 0) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterenddate";
		}
		String startTime="";
		String endTime="";
		if (errorMessage.isEmpty()) {
			if(activityAttendanceForm.getFullDay()!=null && !activityAttendanceForm.getFullDay().equalsIgnoreCase("Yes")){
					Period fromPeriod = ActivityAttendenceHelper.getInstance()
							.getPeriod(
									Integer.valueOf(activityAttendanceForm
											.getFromPeriod()));
					Period toPeriod = ActivityAttendenceHelper.getInstance().getPeriod(
							Integer.valueOf(activityAttendanceForm.getToPeriod()));
					startTime=fromPeriod.getStartTime();
					String[] periodStartTime = fromPeriod.getStartTime().split(":");
					startCal.set(Calendar.HOUR, Integer.valueOf(periodStartTime[0]));
					startCal.set(Calendar.MINUTE, Integer.valueOf(periodStartTime[1]));
					startCal.set(Calendar.SECOND, Integer.valueOf(periodStartTime[2]));
					endTime=toPeriod.getStartTime();
					String[] periodEndTime = toPeriod.getStartTime().split(":");
					endCal.set(Calendar.HOUR, Integer.valueOf(periodEndTime[0]));
					endCal.set(Calendar.MINUTE, Integer.valueOf(periodEndTime[1]));
					endCal.set(Calendar.SECOND, Integer.valueOf(periodEndTime[2]));
					if (startCal.compareTo(endCal) > 0) {
						errorMessage = "knowledgepro.attendance.activityattendence.lesstoperiod";
					}
			}
		}

		String invalidRegisterNo = "";
		if (errorMessage.isEmpty()) {
			invalidRegisterNo = ActivityAttendenceHelper.getInstance()
					.isRegisterNoValied(activityAttendanceForm, registerNoList);
			if (!invalidRegisterNo.isEmpty()) {
				if (activityAttendanceForm.isRegisterNo()) {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedregisterno";
				} else {
					errorMessage = "knowledgepro.attendance.activityattendence.invaliedrollno";
				}

			}
		}

		if (errorMessage.isEmpty()) {
			boolean isCoCurricularLeaveApproved = isCoCurricularLeaveApproved(activityAttendanceForm,startTime,endTime);
			if (isCoCurricularLeaveApproved) {
				message = new ActionMessage("knowledgepro.attendance.activityattendence.attendencetaken", activityAttendanceForm.getExistingCurrLeaveRegNos());
			}
		}

		if (errorMessage.isEmpty()) {
			if(isLeaveApproved(activityAttendanceForm,startTime,endTime)) {
				errorMessage = "knowledgepro.attendance.activityattendence.attendencetaken";
			}
		}
		
		if(message==null){
			if (!errorMessage.isEmpty()) {
				if (invalidRegisterNo.isEmpty()) {
					message = new ActionMessage(errorMessage);
				} else {
					message = new ActionMessage(errorMessage, invalidRegisterNo);
				}
			}
		}
		log.info("exit of validateActivityAttendence of ActivityAttendenceHandler class.");
		return message;
	}

	private boolean isLeaveApproved(ApproveLeaveForm approveLeaveForm,String startTime,String endTime) throws ApplicationException {
		log.info("entering into isLeaveApproved of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();	
		log.info("exit of isLeaveApproved of ApproveLeaveHandler class.");
		return approveLeaveTransaction.isLeaveAlreadyApproved(approveLeaveForm,startTime,endTime);
		
	}
	/**
	 * true if the attendance taken for selected dates and period, false
	 * otherwise.
	 * 
	 * @param activityAttendenceForm
	 * @return
	 * @throws ApplicationException
	 */
	private boolean isCoCurricularLeaveApproved(
			ApproveLeaveForm activityAttendenceForm,String startTime,String endTime)
			throws ApplicationException {
		log.info("entering into isAttendenceTaken of ActivityAttendenceHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		boolean isCoCurricularLeaveApproved = false;
		if(activityAttendenceForm.getFullDay()!=null && activityAttendenceForm.getFullDay().equalsIgnoreCase("No")){
			if(!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)){
			isCoCurricularLeaveApproved = activityAttendenceTransaction.isCoCurricularLeaveApproved(activityAttendenceForm,startTime,endTime);
			}
		}else if(activityAttendenceForm.getFullDay()!=null && activityAttendenceForm.getFullDay().equalsIgnoreCase("Yes")){
			isCoCurricularLeaveApproved = activityAttendenceTransaction.isCoCurricularLeaveApproved(activityAttendenceForm,startTime,endTime);
		}
		log.info("exit of isAttendenceTaken of ActivityAttendenceHandler class.");
		return isCoCurricularLeaveApproved;
	}

	/**
	 * Initialize modify activity attendance
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 * @throws Exception
	 */
	public List<ActivityAttendenceTO> initModifyActivityAttendence(
			String fromDate, String toDate) throws Exception {
		log
				.info("entering into updateActivityAttendence of ActivityAttendenceHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		List<Attendance> modifyActivityAttendanceList = activityAttendenceTransaction
				.initModifyActivityAttendence(fromDate, toDate);

		List<ActivityAttendenceTO> modifyActivityAttendanceTOList = ActivityAttendenceHelper
				.getInstance().modifyActivityAttendenceBOtoTO(
						modifyActivityAttendanceList);
		log
				.info("exit of updateActivityAttendence of ActivityAttendenceHandler class.");
		return modifyActivityAttendanceTOList;
	}

	/**
	 * Get the Activity attendance by Id.
	 * 
	 * @param attendanceId
	 * @return
	 * @throws Exception
	 */
	public ActivityAttendenceTO getActivityAttendanceById(int attendanceId)
			throws Exception {
		log
				.info("entering into getActivityAttendanceById of ActivityAttendenceHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		Attendance getActivityAttendanceById = activityAttendenceTransaction
				.getActivityAttendanceById(attendanceId);

		ActivityAttendenceTO getActivityAttendanceByIdTO = ActivityAttendenceHelper
				.getInstance().getActivityAttendanceByIdBOtoTO(
						getActivityAttendanceById);
		log
				.info("exit of getActivityAttendanceById of ActivityAttendenceHandler class.");
		return getActivityAttendanceByIdTO;
	}

	/**
	 * Constructs constructCoCcurricularLeave To Map
	 * @param activityAttendanceForm
	 * @param mode
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, List<ApproveLeaveTO>> constructCoCcurricularLeave(
			ApproveLeaveForm activityAttendanceForm, String mode)throws Exception {
		
		log.info("entering into constructApproveLeave of ApproveLeaveHandler class.");
		//added by mahi
		
		String startTime=null;
		String endTime=null;
		boolean periodNotFound=false;
		if(activityAttendanceForm.getFromPeriodName()!=null && !activityAttendanceForm.getFromPeriodName().isEmpty()){
			Period period=getPeriod(Integer.parseInt(activityAttendanceForm.getFutureFromPeriod()));
			 startTime=period.getStartTime();
			Integer periodId=getPeriodIdByClassIdAndPeriodName(activityAttendanceForm.getFromPeriodName(),
					activityAttendanceForm.getClassSchemewiseId(),period.getStartTime(),period.getEndTime());
			if(periodId==null){
				    periodNotFound=true;	
			}else{
				activityAttendanceForm.setFromPeriod(String.valueOf(periodId));	
			}
			
		}if(activityAttendanceForm.getToPeriodName()!=null && !activityAttendanceForm.getToPeriodName().isEmpty()){
			Period period=getPeriod(Integer.parseInt(activityAttendanceForm.getFutureToPeriod()));
			endTime=period.getStartTime();
			Integer periodId=getPeriodIdByClassIdAndPeriodName(activityAttendanceForm.getToPeriodName(),
					activityAttendanceForm.getClassSchemewiseId(),period.getStartTime(),period.getEndTime());
			if(periodId==null){
				    periodNotFound=true;	
			}else{
				activityAttendanceForm.setToPeriod(String.valueOf(periodId));
			}
		}
		if(periodNotFound){
			if(startTime!=null && endTime!=null){
				IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
				List<Integer> periodIdList=activityAttendenceTransaction.getStartPeriodAndEndPeriodByClassId(activityAttendanceForm,startTime,endTime);
				if(!periodIdList.isEmpty()){
					activityAttendanceForm.setFromPeriod(String.valueOf(periodIdList.get(0)));
					activityAttendanceForm.setToPeriod(String.valueOf(periodIdList.get(1)));
				}
			}
		}
		//end
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<Attendance> attenenceList = approveLeaveTransaction.isAttendenceTaken(activityAttendanceForm);

		Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = new HashMap<Integer, List<ApproveLeaveTO>>();
		List<ApproveLeaveTO> approveListForNewDates =null;
		if((activityAttendanceForm.getFromPeriod()!=null && !activityAttendanceForm.getFromPeriod().isEmpty())&&
				(activityAttendanceForm.getToPeriod()!=null && !activityAttendanceForm.getToPeriod().isEmpty())){
		approveListForNewDates= constructCoCcurricularLeaveTOFromAttendence(attenenceList, activityAttendanceForm);
		
		}else{
			approveListForNewDates = constructCoCcurricularLeaveTOFromAttendenceForFullDay(attenenceList, activityAttendanceForm);
		}
		if(approveListForNewDates!=null &&!approveListForNewDates.isEmpty())
		approveLeaveToMap.put(1, approveListForNewDates);

		if ("update".equals(mode)) {
			ApproveLeaveForm newApproveLeaveForm = new ApproveLeaveForm();
			ArrayList<String> studentIds = new ArrayList<String>();
			if (activityAttendanceForm.getOldRegisterNoEntry() != null) {
				String[] ids = activityAttendanceForm.getOldRegisterNoEntry().split(",");
				for (String num : ids) {
					studentIds.add(num);
				}
			}
			newApproveLeaveForm.setRegisterNoList(studentIds);
			newApproveLeaveForm.setFromDate(CommonUtil.formatDate(activityAttendanceForm.getStudentLeaveTO().getStartDate(),"dd/MM/yyyy"));
			newApproveLeaveForm.setToDate(CommonUtil.formatDate(activityAttendanceForm.getStudentLeaveTO().getEndDate(),"dd/MM/yyyy"));
			newApproveLeaveForm.setFromPeriod(activityAttendanceForm.getFromPeriod());
			newApproveLeaveForm.setToPeriod(activityAttendanceForm.getToPeriod());
			newApproveLeaveForm.setClassSchemewiseId(activityAttendanceForm.getClassSchemewiseId());
			newApproveLeaveForm.setRegisterNo(getIsRegisterNo(newApproveLeaveForm));
			List<ApproveLeaveTO> approveListForOldDates = constructApproveLeaveTOFromAttendence(attenenceList, newApproveLeaveForm);
			if(approveListForOldDates!=null && !approveListForOldDates.isEmpty())
			approveLeaveToMap.put(2, approveListForOldDates);
		}
		log.info("exit of constructApproveLeave of ApproveLeaveHandler class.");
		return approveLeaveToMap;

	}

	/**
	 * Construct Approve Leave TO list from Attendance.
	 * 
	 * @param attenenceList
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	private List<ApproveLeaveTO> constructApproveLeaveTOFromAttendence(
			List<Attendance> attenenceList, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log
				.info("entering into constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		List<ApproveLeaveTO> approveLeaveToList = new ArrayList<ApproveLeaveTO>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// Converts form date to formatted string date
		String stdate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getFromDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		if((approveLeaveForm.getFromPeriod()!=null && !approveLeaveForm.getFromPeriod().isEmpty())&&
				(approveLeaveForm.getToPeriod()!=null && !approveLeaveForm.getToPeriod().isEmpty())){
		
					// get the commaseperated values of periods for start day.
					List<Period> periodListforStartDate = getPeriodListforStartDate(approveLeaveForm);
					Set<Integer> periodsForStartDay = getCommaSeperatedPeriods(periodListforStartDate);
			
					// get the commaseperated values of periods for end day.
					List<Period> periodListforEndDate = getPeriodListforEndDate(approveLeaveForm);
					Set<Integer> periodsForEndDay = getCommaSeperatedPeriods(periodListforEndDate);
			
					// get the commaseperated values of periods for a single day.
					List<Period> periodList = getPeriodList(approveLeaveForm);
					Set<Integer> periods = getCommaSeperatedPeriods(periodList);
			
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
		
		}else{
			
			// get the commaseperated values of periods for a single day.
			List<Period> periodList = getPeriodList(approveLeaveForm);
			Set<Integer> periods = getCommaSeperatedPeriods(periodList);
			// increment start day and get the string representation.
			Calendar startCal = Calendar.getInstance();
			startCal.setTime(startDate);
			//startCal.add(Calendar.DATE, 1);
			Date incrementalStartDate = startCal.getTime();
			String incrementalStartDateString = formatter
					.format(incrementalStartDate);
	
			// decrement the end date and get the string representation.
			Calendar endCal = Calendar.getInstance();
			endCal.setTime(endDate);
			//endCal.add(Calendar.DATE, -1);
			Date incrementalEndDate = endCal.getTime();
			String incrementalEndDateString = formatter.format(incrementalEndDate);
			
			List<AttendanceStudent> attendenceFordaysRange = getAttendenceListForDaysRange(
					incrementalStartDateString, incrementalEndDateString,periods, approveLeaveForm);
			
			approveLeaveToList = ApproveLeaveHelper.getInstance().convertBOtoTO(attendenceFordaysRange,approveLeaveToList);
		}
		log.info("exit of constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		return approveLeaveToList;

	}

	/**
	 * Constructs constructCoCcurricularLeaveTOFromAttendence objects
	 * @param attenenceList
	 * @param activityAttendanceForm
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	private List<ApproveLeaveTO> constructCoCcurricularLeaveTOFromAttendence(
			List<Attendance> attenenceList,
			ApproveLeaveForm activityAttendanceForm)
			throws ApplicationException {

		
		log
				.info("entering into constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		List<ApproveLeaveTO> approveLeaveToList = new ArrayList<ApproveLeaveTO>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// Converts form date to formatted string date
		String stdate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getFromDate(), "dd/MM/yyyy",
				"MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		// get the commaseperated values of periods for start day.
		List<Period> periodListforStartDate = getPeriodListforStartDate(activityAttendanceForm);
		Set<Integer> periodsForStartDay = getCommaSeperatedPeriods(periodListforStartDate);
		
		// get the commaseperated values of periods for start day for different days.
		List<Period> periodListforStartDateIfMoreThanOneDay = getPeriodListforStartDateIfselectedDateMoreThanOneDay(activityAttendanceForm);
		Set<Integer> periodsForStartDayIfMoreThanOneDay = getCommaSeperatedPeriods(periodListforStartDateIfMoreThanOneDay);
		
		// get the commaseperated values of periods for end day.
		List<Period> periodListforEndDate = getPeriodListforEndDate(activityAttendanceForm);
		Set<Integer> periodsForEndDay = getCommaSeperatedPeriods(periodListforEndDate);

		// get the commaseperated values of periods for a single day.
		List<Period> periodList = getPeriodList(activityAttendanceForm);
		Set<Integer> periods = getCommaSeperatedPeriods(periodList);
        
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
						activityAttendanceForm.getFromDate(),
						periodsForStartDay, activityAttendanceForm);
				approveLeaveToList = ApproveLeaveHelper.getInstance()
						.convertBOtoTO(attendenceForStartDay,
								approveLeaveToList);

			} else {

				List<AttendanceStudent> attendenceForStartDay = getAttendenceListForStartDay(
						activityAttendanceForm.getFromDate(),
						periodsForStartDayIfMoreThanOneDay, activityAttendanceForm);
				List<AttendanceStudent> attendenceForEndDay = getAttendenceListForEndDay(
						activityAttendanceForm.getToDate(), periodsForEndDay,
						activityAttendanceForm);
				List<AttendanceStudent> attendenceFordaysRange = getAttendenceListForDaysRange(
						incrementalStartDateString, incrementalEndDateString,
						periods, activityAttendanceForm);

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
		log
				.info("exit of constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		return approveLeaveToList;

	}

	/**
	 * Get the attendance student list for the start day.
	 * 
	 * @param startDay
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForStartDay(
			String startDay, Set<Integer> periods,
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log
				.info("entering into getAttendenceListForStartDay of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceList(startDay, periods, approveLeaveForm);
		log
				.info("exit of getAttendenceListForStartDay of ApproveLeaveHandler class.");
		return attendenceList;
	}

	/**
	 * Get the attendance list for the end day.
	 * 
	 * @param startDay
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForEndDay(String startDay,
			Set<Integer> periods, ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {
		log
				.info("entering into getAttendenceListForEndDay of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceList(startDay, periods, approveLeaveForm);
		log
				.info("exit of getAttendenceListForEndDay of ApproveLeaveHandler class.");
		return attendenceList;

	}

	/**
	 * Get the attendance list between days.
	 * 
	 * @param dayAfterStart
	 * @param dayAfterEnd
	 * @param periods
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public List<AttendanceStudent> getAttendenceListForDaysRange(
			String dayAfterStart, String dayAfterEnd, Set<Integer> periods,
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log
				.info("entering into getAttendenceListForDaysRange of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<AttendanceStudent> attendenceList = approveLeaveTransaction
				.getAttendenceListBetweenDays(dayAfterStart, dayAfterEnd,
						periods, approveLeaveForm);
		log
				.info("exit of getAttendenceListForDaysRange of ApproveLeaveHandler class.");
		return attendenceList;
	}

	/**
	 * Get the period list for the start date.
	 * 
	 * @param approveLeaveForm0
	 * @return
	 * @throws ApplicationException
	 */
	public List<Period> getPeriodListforStartDateIfselectedDateMoreThanOneDay(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log
				.info("entering into getPeriodListforStartDate of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		Period fromPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getFromPeriod()));
		/*Period toPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getToPeriod()));*/

		List<Period> periodList = activityAttendenceTransaction
				.getPeriodListForStartDateSelectedDateMoreThanOneDay(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()), fromPeriod.getStartTime());
		log
				.info("exit of getPeriodListforStartDate of ApproveLeaveHandler class.");
		return periodList;
	}
	
	public List<Period> getPeriodListforStartDate(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log
				.info("entering into getPeriodListforStartDate of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		Period fromPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getFromPeriod()));
		Period toPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getToPeriod()));
		 List<Period> periodList = activityAttendenceTransaction
				.getPeriodListForStartDate(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()), fromPeriod.getStartTime(),
						toPeriod.getStartTime());
		log
				.info("exit of getPeriodListforStartDate of ApproveLeaveHandler class.");
		return periodList;
	}

	/**
	 * Get the period list for the end date.
	 * 
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	private List<Period> getPeriodListforEndDate(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {
		log
				.info("entering into getPeriodListforEndDate of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();

		Period toPeriod = getPeriod(Integer.valueOf(approveLeaveForm
				.getToPeriod()));
		List<Period> periodList = activityAttendenceTransaction
				.getPeriodListForEndDate(Integer.valueOf(approveLeaveForm
						.getClassSchemewiseId()), toPeriod.getEndTime());
		log
				.info("exit of getPeriodListforEndDate of ApproveLeaveHandler class.");
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
	 * Get the period by period id.
	 * 
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
	 * Get the period set.
	 * 
	 * @param periodListforStartDate
	 * @return
	 */
	private Set<Integer> getCommaSeperatedPeriods(
			List<Period> periodListforStartDate) {
		log
				.info("entering into getCommaSeperatedPeriods of ApproveLeaveHandler class.");
		Iterator<Period> periodIterator = periodListforStartDate.iterator();
		Set<Integer> periodsForStartDay = new HashSet<Integer>();
		while (periodIterator.hasNext()) {
			Period period = (Period) periodIterator.next();
			periodsForStartDay.add(period.getId());
		}
		log
				.info("exit of getCommaSeperatedPeriods of ApproveLeaveHandler class.");
		return periodsForStartDay;
	}

	/**
	 * updates updateCoCcurricularLeave
	 * @param approveLeaveToMap
	 * @param activityAttendanceForm
	 * @param mode
	 * @throws ApplicationException
	 */
	public void updateCoCcurricularLeave(
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
			ApproveLeaveForm activityAttendanceForm, String mode)
			throws ApplicationException {

		log.info("entering into updateApproveLeave of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		StuCocurrLeave studentLeave = new StuCocurrLeave();

		Activity leaveType = new Activity();
		/*leaveType.setId(Integer.parseInt(activityAttendanceForm
				.getLeaveType()));*/
		leaveType.setId(Integer.parseInt(activityAttendanceForm.getActivityTypeId()));
		
		studentLeave.setActivity(leaveType);
		
		AttendanceType attendanceType = new AttendanceType();
		attendanceType.setId(Integer.parseInt(activityAttendanceForm
				.getAttendanceTypeId()));
		studentLeave.setAttendanceType(attendanceType);
		if(((activityAttendanceForm.getFromPeriod()!=null && !activityAttendanceForm.getFromPeriod().isEmpty()))
			&&(activityAttendanceForm.getToPeriod()!=null && !activityAttendanceForm.getToPeriod().isEmpty())){
				
		Period startPeriod = new Period();
		Period endPeriod = new Period();
		startPeriod.setId(Integer.parseInt(activityAttendanceForm
				.getFromPeriod()));
		endPeriod.setId(Integer.parseInt(activityAttendanceForm.getToPeriod()));

		studentLeave.setStartPeriod(startPeriod);
		studentLeave.setEndPeriod(endPeriod);
		}
		studentLeave.setIsCocurrLeaveCancelled(false);
		Date startDate = CommonUtil
				.ConvertStringToSQLDate(activityAttendanceForm.getFromDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(activityAttendanceForm
				.getToDate());
		studentLeave.setStartDate(startDate);
		studentLeave.setEndDate(endDate);

		studentLeave.setCreatedBy(activityAttendanceForm.getUserId());
		studentLeave.setCreatedDate(new Date());
		studentLeave.setModifiedBy(activityAttendanceForm.getUserId());
		studentLeave.setLastModifiedDate(new Date());

		ClassSchemewise classSchemewise = new ClassSchemewise();
		classSchemewise.setId(Integer.parseInt(activityAttendanceForm
				.getClassSchemewiseId()));
		studentLeave.setClassSchemewise(classSchemewise);

		boolean isRegnoCheck = activityAttendanceForm.isRegisterNo();

		Set<Integer> studentIds = new HashSet<Integer>();
		if (activityAttendanceForm.getRegisterNoEntry() != null) {
			String[] ids = activityAttendanceForm.getRegisterNoEntry().split(
					",");
			for (String num : ids) {
				Integer studentId = approveLeaveTransaction
						.getStudentIdByCourseAnd(classSchemewise.getId(),
								isRegnoCheck, num);
				if (studentId != null) {
					studentIds.add(studentId);
				}
					
			}
		}
		Iterator<Integer> itr = studentIds.iterator();
		StuCocurrLeaveDetails stuCocurrLeaveDetails;
		Student student;
		Set<StuCocurrLeaveDetails> set = new HashSet<StuCocurrLeaveDetails>();
		while (itr.hasNext()) {
			stuCocurrLeaveDetails = new StuCocurrLeaveDetails();
			student = new Student();
			student.setId(itr.next());
			stuCocurrLeaveDetails.setStudent(student);
			set.add(stuCocurrLeaveDetails);
		}

		if ("update".equals(mode)) {
			studentLeave
					.setId(Integer.parseInt(activityAttendanceForm.getId()));
		}
		studentLeave.setStuCocurrLeaveDetailses(set);
		activityAttendenceTransaction.updateCoCcurricularLeave(
				approveLeaveToMap, activityAttendanceForm, studentLeave, mode);
		log.info("exit of updateApproveLeave of ApproveLeaveHandler class.");

	}

	/**
	 * get CoCcurricularLeave List
	 * @param approveLeaveForm
	 * @throws ApplicationException
	 */
	public void getCoCcurricularLeaves(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {

		log
				.info("entering into approvedLeaveSearch of ApproveLeaveHandler class.");
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		Date fromDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getFromDate());
		Date toDate = CommonUtil.ConvertStringToDate(approveLeaveForm
				.getToDate());
		List<StuCocurrLeave> list = activityAttendenceTransaction.getCoCcurricularLeaves(fromDate,
				toDate);

		List<StudentLeaveTO> leaveList = ActivityAttendenceHelper.getInstance()
				.convertStudentLeaveBOtoTO(list);
		approveLeaveForm.setLeaveList(leaveList);
		
		
		
		log.info("exit of approvedLeaveSearch of ApproveLeaveHandler class.");

	}

	/**
	 * @param approveLeaveForm
	 * @throws ApplicationException
	 */
	public void editCoCurricularLeave(ApproveLeaveForm approveLeaveForm)
			throws ApplicationException {

		log.info("entering into editLeave of ApproveLeaveHandler class.");
		int leaveId = Integer.parseInt(approveLeaveForm.getId());
		StuCocurrLeave studentLeave;
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		studentLeave = activityAttendenceTransaction.getCoCurricularLeave(leaveId);
		coypBoToForm(approveLeaveForm, studentLeave);
		log.info("exit of editLeave of ApproveLeaveHandler class.");
	}

	
	/**
	 * @param approveLeaveForm
	 * @param studentLeave
	 * @throws ApplicationException
	 */
	public void coypBoToForm(ApproveLeaveForm approveLeaveForm,
			StuCocurrLeave studentLeave) throws ApplicationException {

		log.info("entering into coypBoToForm of ApproveLeaveHelper class.");
		approveLeaveForm.setId(String.valueOf(studentLeave.getId()));
		approveLeaveForm.setClassSchemewiseId(String.valueOf(studentLeave
				.getClassSchemewise().getId()));
		//added by mahi
		approveLeaveForm.setClassValues(String.valueOf(studentLeave
				.getClassSchemewise().getId()));
		//end
		approveLeaveForm.setLeaveType(String.valueOf(studentLeave.getActivity()
				.getId()));
		approveLeaveForm.setActivityTypeId(String.valueOf(studentLeave.getActivity()
				.getId()));
		approveLeaveForm.setFromDate(CommonUtil.formatDate(studentLeave
				.getStartDate(), "dd/MM/yyyy"));
		approveLeaveForm.setToDate(CommonUtil.formatDate(studentLeave
				.getEndDate(), "dd/MM/yyyy"));
		if(studentLeave.getStartPeriod()!=null && studentLeave.getEndPeriod()!=null){
			if(studentLeave.getStartPeriod().getId()!=0)
				approveLeaveForm.setFromPeriod(String.valueOf(studentLeave.getStartPeriod().getId()));
			if(studentLeave.getEndPeriod().getId()!=0)
			approveLeaveForm.setToPeriod(String.valueOf(studentLeave.getEndPeriod().getId()));
		}
				
		approveLeaveForm.setAttendanceTypeId(String.valueOf(studentLeave.getAttendanceType()
				.getId()));
		Iterator<StuCocurrLeaveDetails> itr = studentLeave
				.getStuCocurrLeaveDetailses().iterator();
		StuCocurrLeaveDetails studentLeaveDetails;
		StringBuffer registerNos = new StringBuffer();
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		boolean isRegnoCheck = activityAttendenceTransaction
				.checkIsRegisterNo(studentLeave.getClassSchemewise().getId());
		while (itr.hasNext()) {
			studentLeaveDetails = itr.next();
			if (isRegnoCheck) {
				registerNos.append(studentLeaveDetails.getStudent()
						.getRegisterNo());
				registerNos.append(',');

			} else {
				registerNos
						.append(studentLeaveDetails.getStudent().getRollNo());
				registerNos.append(',');
			}
		}
		approveLeaveForm.setRegisterNoEntry(registerNos.substring(0,
				(registerNos.length() - 1)));

		approveLeaveForm.setOldRegisterNoEntry(registerNos.substring(0,
				(registerNos.length() - 1)));
		StudentLeaveTO studentLeaveTO = new StudentLeaveTO();
		studentLeaveTO.setStartDate(studentLeave.getStartDate());
		studentLeaveTO.setStDate(CommonUtil.formatDate(studentLeave
				.getStartDate(), "dd/MM/yyyy"));
		studentLeaveTO.setEdDate(CommonUtil.formatDate(studentLeave
				.getEndDate(), "dd/MM/yyyy"));
		studentLeaveTO.setLeaveType(String.valueOf(studentLeave.getActivity()
				.getId()));
		studentLeaveTO.setEndDate(studentLeave.getEndDate());
		if(approveLeaveForm.getFromPeriod()!=null && !approveLeaveForm.getFromPeriod().isEmpty())
			studentLeaveTO.setStartPeriod(approveLeaveForm.getFromPeriod());
		if(approveLeaveForm.getToPeriod()!=null && !approveLeaveForm.getToPeriod().isEmpty())
		studentLeaveTO.setEndPeriod(approveLeaveForm.getToPeriod());
		studentLeaveTO.setClassSchemewiseId(studentLeave.getClassSchemewise()
				.getId());
		approveLeaveForm.setStudentLeaveTO(studentLeaveTO);
		log.info("exit of coypBoToForm of ApproveLeaveHelper class.");

	}

	/**
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	public ActionMessage validateModifyAttendence(
			ApproveLeaveForm approveLeaveForm, ArrayList<String> registerNoList)
			throws ApplicationException {

		log
				.info("entering into validateModifyAttendence of ApproveLeaveHandler class.");
		String errorMessage = "";

		String stdate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getFromDate(), "dd/MM/yyyy", "MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(approveLeaveForm
				.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		int appliedYear = Integer.valueOf(approveLeaveForm.getYear());

		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		int startYear = startCal.get(Calendar.YEAR);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int endYear = endCal.get(Calendar.YEAR);

//		if (startYear > appliedYear) {
//			errorMessage = "knowledgepro.attendance.activityattendence.greaterstartyear";
//		} else if (endYear > appliedYear) {
//			errorMessage = "knowledgepro.attendance.activityattendence.greaterendyear";
//		} else 
		if (startYear < appliedYear) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserstartyear";
		} else if (endYear < appliedYear) {
			errorMessage = "knowledgepro.attendance.activityattendence.lesserendyear";
		} else if (startDate.compareTo(endDate) > 0) {
			errorMessage = "knowledgepro.attendance.activityattendence.greaterenddate";
		}
		String startTime="";
		String endTime="";
		if (errorMessage.isEmpty()) {
		 if((approveLeaveForm.getFromPeriod()!=null && !approveLeaveForm.getFromPeriod().isEmpty())&&
				 (approveLeaveForm.getToPeriod()!=null && !approveLeaveForm.getToPeriod().isEmpty())){
			Period fromPeriod = ActivityAttendenceHelper.getInstance()
					.getPeriod(
							Integer.valueOf(approveLeaveForm.getFromPeriod()));
			Period toPeriod = ActivityAttendenceHelper.getInstance().getPeriod(
					Integer.valueOf(approveLeaveForm.getToPeriod()));
			startTime=fromPeriod.getStartTime();
			String[] periodStartTime = fromPeriod.getStartTime().split(":");
			startCal.set(Calendar.HOUR, Integer.valueOf(periodStartTime[0]));
			startCal.set(Calendar.MINUTE, Integer.valueOf(periodStartTime[1]));
			startCal.set(Calendar.SECOND, Integer.valueOf(periodStartTime[2]));
			endTime=toPeriod.getStartTime();
			String[] periodEndTime = toPeriod.getStartTime().split(":");
			endCal.set(Calendar.HOUR, Integer.valueOf(periodEndTime[0]));
			endCal.set(Calendar.MINUTE, Integer.valueOf(periodEndTime[1]));
			endCal.set(Calendar.SECOND, Integer.valueOf(periodEndTime[2]));

				if (startCal.compareTo(endCal) > 0) {
					errorMessage = "knowledgepro.attendance.activityattendence.lesstoperiod";
				}
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

		if (errorMessage.isEmpty()
				&& isCoCurricularLeaveApproved(approveLeaveForm,startTime,endTime)) {
			errorMessage = "knowledgepro.attendance.approveleave.leaveapproved";
		}
		ActionMessage message = null;
		if (!errorMessage.isEmpty()) {
			if (invalidRegisterNo.isEmpty()) {
				message = new ActionMessage(errorMessage);
			} else {
				message = new ActionMessage(errorMessage, invalidRegisterNo);
			}

		}
		log
				.info("exit of validateModifyAttendence of ApproveLeaveHandler class.");
		return message;

	}

	/**
	 * @param approveLeaveForm
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, List<ApproveLeaveTO>> constructCoCcurricularLeaveForDelete(
			ApproveLeaveForm approveLeaveForm) throws ApplicationException {

		log
				.info("entering into constructApproveLeave of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<Attendance> attenenceList = approveLeaveTransaction
				.isAttendenceTaken(approveLeaveForm);

		Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = new HashMap<Integer, List<ApproveLeaveTO>>();

		ApproveLeaveForm newApproveLeaveForm = new ApproveLeaveForm();

		ArrayList<String> studentIds = new ArrayList<String>();
		if (approveLeaveForm.getOldRegisterNoEntry() != null) {
			String[] ids = approveLeaveForm.getOldRegisterNoEntry().split(",");
			for (String num : ids) {
				studentIds.add(num.trim());
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

		log.info("exit of constructApproveLeave of ApproveLeaveHandler class.");
		return approveLeaveToMap;

	}

	/**
	 * deletes deleteCoCcurricularLeave object
	 * @param approveLeaveForm
	 * @param approveLeaveToMap
	 * @throws ApplicationException
	 */
	public void deleteCoCcurricularLeave(ApproveLeaveForm approveLeaveForm,
			Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap)throws ApplicationException {
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		activityAttendenceTransaction.deleteCoCcurricularLeave(approveLeaveForm,approveLeaveToMap);
		
	}
	
	/**
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 */
	public ArrayList<String>  getStudentListByclass(ApproveLeaveForm approveLeaveForm,ArrayList<String> registerNoList)
						throws ApplicationException{
		IActivityAttendenceTransaction transaction = new ActivityAttendenceTransactionImpl();
		Map<String,String> stuMap=transaction.getStudentListByclass(approveLeaveForm);
		ArrayList<String> regNoList= ActivityAttendenceHelper.getInstance().getStudentRegNos(stuMap,registerNoList);
		return regNoList;
	}
	
	/**
	 * @param approveLeaveToMap
	 * @param activityAttendanceForm
	 * @param mode
	 * @throws Exception 
	 */
	public void updateCoCcurricularLeaveForAddMethod(Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap,
							ApproveLeaveForm activityAttendanceForm, String mode)throws Exception {
		log.info("entering into updateApproveLeave of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		IActivityAttendenceTransaction activityAttendenceTransaction = new ActivityAttendenceTransactionImpl();
		StuCocurrLeave studentLeave = new StuCocurrLeave();

		Activity leaveType = new Activity();
		/*leaveType.setId(Integer.parseInt(activityAttendanceForm
				.getLeaveType()));*/
		leaveType.setId(Integer.parseInt(activityAttendanceForm.getActivityTypeId()));
		
		studentLeave.setActivity(leaveType);
		
		AttendanceType attendanceType = new AttendanceType();
		attendanceType.setId(Integer.parseInt(activityAttendanceForm.getAttendanceTypeId()));
		studentLeave.setAttendanceType(attendanceType);
		if(activityAttendanceForm.getFullDay()!=null && !activityAttendanceForm.getFullDay().equalsIgnoreCase("Yes")){
			if((activityAttendanceForm.getFromPeriod()!=null && !activityAttendanceForm.getFromPeriod().isEmpty())&&
					(activityAttendanceForm.getToPeriod()!=null && !activityAttendanceForm.getToPeriod().isEmpty())){
				Period startPeriod = new Period();
				Period endPeriod = new Period();
				startPeriod.setId(Integer.parseInt(activityAttendanceForm.getFromPeriod()));
				endPeriod.setId(Integer.parseInt(activityAttendanceForm.getToPeriod()));
				studentLeave.setStartPeriod(startPeriod);
				studentLeave.setEndPeriod(endPeriod);
			}
	//added by mahi start		
	}else if(activityAttendanceForm.getFullDay()!=null && activityAttendanceForm.getFullDay().equalsIgnoreCase("Yes")){
		List<Integer> periodIdList=activityAttendenceTransaction.getStartPeriodAndEndPeriodByClassId(activityAttendanceForm,null,null);
		if(!periodIdList.isEmpty()){
			Period startPeriod = new Period();
			Period endPeriod = new Period();
			startPeriod.setId(periodIdList.get(0));
			endPeriod.setId(periodIdList.get(1));
			studentLeave.setStartPeriod(startPeriod);
			studentLeave.setEndPeriod(endPeriod);
		}
	}
	//end	
		studentLeave.setIsCocurrLeaveCancelled(false);
		Date startDate = CommonUtil.ConvertStringToSQLDate(activityAttendanceForm.getFromDate());
		Date endDate = CommonUtil.ConvertStringToSQLDate(activityAttendanceForm.getToDate());
				
		studentLeave.setStartDate(startDate);
		studentLeave.setEndDate(endDate);

		studentLeave.setCreatedBy(activityAttendanceForm.getUserId());
		studentLeave.setCreatedDate(new Date());
		studentLeave.setModifiedBy(activityAttendanceForm.getUserId());
		studentLeave.setLastModifiedDate(new Date());

		ClassSchemewise classSchemewise = new ClassSchemewise();
		classSchemewise.setId(Integer.parseInt(activityAttendanceForm.getClassSchemewiseId()));
		studentLeave.setClassSchemewise(classSchemewise);

		boolean isRegnoCheck = activityAttendanceForm.isRegisterNo();

		Set<Integer> studentIds = new HashSet<Integer>();
		if (activityAttendanceForm.getRegisterNoEntry() != null) {
			List<String> idsList= activityAttendanceForm.getRegisterNoList();
			Iterator<String> itr1 = idsList.iterator();
			while (itr1.hasNext()) {
				String num=(String)itr1.next();
				Integer studentId = approveLeaveTransaction.getStudentIdByCourseAnd(classSchemewise.getId(),isRegnoCheck, num);
				if (studentId != null) {
					studentIds.add(studentId);
				}
					
			}
		}
		Iterator<Integer> itr = studentIds.iterator();
		StuCocurrLeaveDetails stuCocurrLeaveDetails;
		Student student;
		Set<StuCocurrLeaveDetails> set = new HashSet<StuCocurrLeaveDetails>();
		while (itr.hasNext()) {
			stuCocurrLeaveDetails = new StuCocurrLeaveDetails();
			student = new Student();
			student.setId(itr.next());
			stuCocurrLeaveDetails.setStudent(student);
			set.add(stuCocurrLeaveDetails);
		}

		if ("update".equals(mode)) {
			studentLeave
					.setId(Integer.parseInt(activityAttendanceForm.getId()));
		}
		studentLeave.setStuCocurrLeaveDetailses(set);
		activityAttendenceTransaction.updateCoCcurricularLeave(
				approveLeaveToMap, activityAttendanceForm, studentLeave, mode);
		log.info("exit of updateApproveLeave of ApproveLeaveHandler class.");

	}
	
	/**
	 * @param activityAttendanceForm
	 * @param mode
	 * @return
	 * @throws ApplicationException
	 */
	public Map<Integer, List<ApproveLeaveTO>> constructCoCcurricularLeaveForFullDay
									(ApproveLeaveForm activityAttendanceForm, String mode)throws ApplicationException {
		log.info("entering into constructCoCcurricularLeaveForFullDay of ApproveLeaveHandler class.");
		IApproveLeaveTransaction approveLeaveTransaction = new ApproveLeaveTransactionImpl();
		List<Attendance> attenenceList = approveLeaveTransaction.isAttendenceTaken(activityAttendanceForm);
		Map<Integer, List<ApproveLeaveTO>> approveLeaveToMap = new HashMap<Integer, List<ApproveLeaveTO>>();

		List<ApproveLeaveTO> approveListForNewDates = constructCoCcurricularLeaveTOFromAttendenceForFullDay(attenenceList, activityAttendanceForm);
		approveLeaveToMap.put(1, approveListForNewDates);
		
		log.info("exit of constructCoCcurricularLeaveForFullDay of ApproveLeaveHandler class.");
		return approveLeaveToMap;

	}
	
	
	/**
	 * @param attenenceList
	 * @param activityAttendanceForm
	 * @return
	 * @throws ApplicationException
	 */
	@SuppressWarnings("deprecation")
	private List<ApproveLeaveTO> constructCoCcurricularLeaveTOFromAttendenceForFullDay(
			List<Attendance> attenenceList,ApproveLeaveForm activityAttendanceForm)throws ApplicationException {

		log.info("entering into constructCoCcurricularLeaveTOFromAttendenceForFullDay of ApproveLeaveHandler class.");
		List<ApproveLeaveTO> approveLeaveToList = new ArrayList<ApproveLeaveTO>();

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		// Converts form date to formatted string date
		String stdate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getFromDate(), "dd/MM/yyyy",
				"MM/dd/yyyy");
		String edate = CommonUtil.ConvertStringToDateFormat(
				activityAttendanceForm.getToDate(), "dd/MM/yyyy", "MM/dd/yyyy");

		Date startDate = new Date(stdate);
		Date endDate = new Date(edate);

		// get the commaseperated values of periods for a single day.
		List<Period> periodList = getPeriodList(activityAttendanceForm);
		Set<Integer> periods = getCommaSeperatedPeriods(periodList);

		// increment start day and get the string representation.
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);
		//startCal.add(Calendar.DATE, 1);
		Date incrementalStartDate = startCal.getTime();
		String incrementalStartDateString = formatter
				.format(incrementalStartDate);

		// decrement the end date and get the string representation.
		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);
		//endCal.add(Calendar.DATE, -1);
		Date incrementalEndDate = endCal.getTime();
		String incrementalEndDateString = formatter.format(incrementalEndDate);

	if (attenenceList != null && !attenenceList.isEmpty()) {
				
		List<AttendanceStudent> attendenceFordaysRange = getAttendenceListForDaysRange(incrementalStartDateString, incrementalEndDateString,periods, activityAttendanceForm);
		approveLeaveToList = ApproveLeaveHelper.getInstance().convertBOtoTO(attendenceFordaysRange,approveLeaveToList);
	}
		log.info("exit of constructApproveLeaveTOFromAttendence of ApproveLeaveHandler class.");
		return approveLeaveToList;

	}
	
	
	
	/**
	 * @param approveLeaveForm
	 * @param registerNoList
	 * @return
	 * @throws ApplicationException
	 */
	public String  validateRegisterNumbers(ApproveLeaveForm approveLeaveForm,ArrayList<String> registerNoList)
	throws ApplicationException{
		IActivityAttendenceTransaction transaction = new ActivityAttendenceTransactionImpl();
		Map<String,String> stuMap=transaction.getStudentRegisterNumbersListByclass(approveLeaveForm);
		String regNoList= ActivityAttendenceHelper.getInstance().validateRegNos(stuMap,registerNoList);
		return regNoList;
	}
	
	public Map<Integer, String> getClassesBySemisterType(ApproveLeaveForm approveLeaveForm)throws ApplicationException {
		IActivityAttendenceTransaction transaction = new ActivityAttendenceTransactionImpl();
		Map<Integer,String> classMap=transaction.getClassesBySemType(approveLeaveForm);
		return classMap;
	}

	/**
	 * @param periodName
	 * @param classId
	 * @throws Exception
	 * Added by mahi
	 */
	public Integer getPeriodIdByClassIdAndPeriodName(String periodName,String classId,String startTime,String endTime) throws Exception {
		IActivityAttendenceTransaction transaction = new ActivityAttendenceTransactionImpl();
		return transaction.getPeriodIdByNameAndClassId(periodName,Integer.parseInt(classId),startTime,endTime);
	}
	
	
}