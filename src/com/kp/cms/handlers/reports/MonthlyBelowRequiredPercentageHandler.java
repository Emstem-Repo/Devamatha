package com.kp.cms.handlers.reports;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.reports.MonthlyBelowRequiredPercentageReportForm;
import com.kp.cms.helpers.reports.MonthlyBelowRequiredPercentageHelper;
import com.kp.cms.to.reports.BelowRequiredPercentageTO;
import com.kp.cms.transactions.reports.IMonthlyBelowRequiredPercentageTransaction;
import com.kp.cms.transactionsimpl.reports.MonthlyBelowRequiredPercentageTransactionImpl;

public class MonthlyBelowRequiredPercentageHandler {

	private static final Log log = LogFactory.getLog(MonthlyBelowRequiredPercentageHandler.class);
	public static volatile MonthlyBelowRequiredPercentageHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static MonthlyBelowRequiredPercentageHandler getInstance(){
		if(self==null)
			self= new MonthlyBelowRequiredPercentageHandler();
		return self;
	}
	/**
	 * 
	 */
	private MonthlyBelowRequiredPercentageHandler(){
		
	}
	
	/**
	 * 
	 * @param MonthlyBelowRequiredPercentageForm
	 * @return
	 * @throws Exception
	 */
	public List<BelowRequiredPercentageTO> getMonthlyAttendanceBelowPercentage(
			MonthlyBelowRequiredPercentageReportForm monthlyPercentageForm) throws Exception {
		log.info("Entered getMonthlyAttendanceBelowPercentage of MonthlyBelowRequiredPercentageHandler");
		IMonthlyBelowRequiredPercentageTransaction transaction = new MonthlyBelowRequiredPercentageTransactionImpl();
		MonthlyBelowRequiredPercentageHelper monthlyHelper = MonthlyBelowRequiredPercentageHelper.getInstance();		
		List<AttendanceStudent> classesHeldAttendedList = transaction.getStudentsAttendance(monthlyHelper.getClassesHeldAttended(monthlyPercentageForm));
		List<BelowRequiredPercentageTO> studentList = monthlyHelper.convertMonthlyBoToTo(classesHeldAttendedList, monthlyPercentageForm.getRequiredPercentage(),monthlyPercentageForm);
		log.info("Exit getMonthlyAttendanceBelowPercentage of MonthlyBelowRequiredPercentageHandler");
		return studentList;
	}
}
