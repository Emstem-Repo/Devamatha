package com.kp.cms.handlers.attendance;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.smartcardio.ATR;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.attendance.ClassWiseAttendanceStatusForm;
import com.kp.cms.helpers.attendance.AttendanceSlipDetailsHelper;
import com.kp.cms.helpers.attendance.ClassWiseAttendanceStatusHelper;
import com.kp.cms.helpers.attendance.HolidayHelper;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.attandance.AttendanceSlipDetailsTransactionImpl;
import com.kp.cms.transactions.attandance.IAttendanceSlipDetailsTransaction;
import com.kp.cms.transactions.attandance.IClassWiseAttendanceStatusTransaction;
import com.kp.cms.transactionsimpl.attendance.ClassWiseAttendanceStatusTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ClassWiseAttendanceStatusHandler {
	public static volatile ClassWiseAttendanceStatusHandler classWiseAttendanceStatusHandler = null;
	public static final Log log = LogFactory
			.getLog(ClassWiseAttendanceStatusHandler.class);
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";

	private ClassWiseAttendanceStatusHandler() {

	}

	public static ClassWiseAttendanceStatusHandler getInstance() {
		if (classWiseAttendanceStatusHandler == null) {
			classWiseAttendanceStatusHandler = new ClassWiseAttendanceStatusHandler();
		}
		return classWiseAttendanceStatusHandler;
	}

	public boolean checkingDatesInCoCurriculumSchemeDuration(
			ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm)
			throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean isFromDate=false;
		boolean isToDate=false;
		boolean isBetweenStartAndEnd=false;
		IClassWiseAttendanceStatusTransaction iClassWiseAttendanceStatusTransaction = ClassWiseAttendanceStatusTransactionImpl
				.getInstance();
		CurriculumSchemeDuration curriculumSchemeDuration = iClassWiseAttendanceStatusTransaction
				.checkingDatesInCoCurriculumSchemeDuration(classWiseAttendanceStatusForm);
		if (curriculumSchemeDuration != null
				&& curriculumSchemeDuration.getStartDate() != null
				&& curriculumSchemeDuration.getEndDate() != null
				&& classWiseAttendanceStatusForm.getFromDate() != null
				&& classWiseAttendanceStatusForm.getToDate() != null) {
			Date fromDate = sdf.parse(classWiseAttendanceStatusForm
					.getFromDate());
			Date toDate = sdf.parse(classWiseAttendanceStatusForm.getToDate());
			Date startDate = sdf.parse(CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(curriculumSchemeDuration
							.getStartDate()),
					ClassWiseAttendanceStatusHandler.SQL_DATEFORMAT,
					ClassWiseAttendanceStatusHandler.FROM_DATEFORMAT));
			Date endDate = sdf.parse(CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(curriculumSchemeDuration
							.getEndDate()),
					ClassWiseAttendanceStatusHandler.SQL_DATEFORMAT,
					ClassWiseAttendanceStatusHandler.FROM_DATEFORMAT));

			if (fromDate.compareTo(startDate) > 0) {
				if (fromDate.compareTo(endDate) < 0) {
					isFromDate = true;
				} else {
					if (fromDate.compareTo(startDate) == 0) {
						isFromDate = true;
					}

				}
			}
			if (toDate.compareTo(startDate) > 0) {
				if (toDate.compareTo(endDate) < 0) {
					isToDate = true;
				} else {
					if (toDate.compareTo(endDate) == 0) {
						isToDate = true;
					}

				}

			}
			if (isFromDate && isToDate) {
				isBetweenStartAndEnd = true;
			}

		}
		
		
		return isBetweenStartAndEnd;
	}


	public String getClassName(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm) throws Exception{
		IClassWiseAttendanceStatusTransaction iClassWiseAttendanceStatusTransaction = ClassWiseAttendanceStatusTransactionImpl.getInstance();
		String className=iClassWiseAttendanceStatusTransaction.getClassName(classWiseAttendanceStatusForm);
		return className;
	}

	public static Map<String, Map<String, List<AttendanceTO>>> getAttendanceDetailsClassWise(ClassWiseAttendanceStatusForm classWiseAttendanceStatusForm,HttpServletRequest request) throws Exception {
		log.info("Inside of getAttendanceDetailsClassWise of ClassWiseAttendanceStatusHandler");
		IClassWiseAttendanceStatusTransaction iClassWiseAttendanceStatusTransaction = ClassWiseAttendanceStatusTransactionImpl.getInstance();
		List<Integer> periodList=iClassWiseAttendanceStatusTransaction.getPeriodsIdsUsingClassId(classWiseAttendanceStatusForm);
		String searchCriteria=ClassWiseAttendanceStatusHelper.getInstance().getSearchDatesQuery(classWiseAttendanceStatusForm);
		List<String> periodNames=iClassWiseAttendanceStatusTransaction.getPeriodNamesUsingClassId(classWiseAttendanceStatusForm);
		List<Attendance> attendaceClassWiseList=iClassWiseAttendanceStatusTransaction.getAttendanceListDetails(searchCriteria,periodList);
	    Map<String, Map<String, List<AttendanceTO>>> listOfClassWiseAttendanceDetails=ClassWiseAttendanceStatusHelper.getInstance().convertBotoTo(attendaceClassWiseList,periodNames,classWiseAttendanceStatusForm,request);
		return listOfClassWiseAttendanceDetails;
	}

}
