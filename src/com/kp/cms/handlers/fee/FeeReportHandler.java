package com.kp.cms.handlers.fee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.helpers.fee.FeeReportHelper;
import com.kp.cms.to.fee.FeeReportTO;
import com.kp.cms.transactions.fee.IFeeReportTxn;
import com.kp.cms.transactionsimpl.fee.FeeReportTxnImpl;


/**
 * @author kalyan.c
 *
 */
public class FeeReportHandler {
	private static FeeReportHandler self = null;
	private static Log log = LogFactory.getLog(FeeReportHandler.class);
	private FeeReportHandler() {
	}

	/**
	 * @return
	 * This method is used to get the instance of this class
	 */
	public static FeeReportHandler getInstance() {
		if (self == null) {
			self = new FeeReportHandler();
		}
		return self;
	}
	/**
	 * @param applnNo
	 * @return
	 * @throws Exception
	 * This method is used to get candidates by application number
	 */
	public List getFeePaymentsByApplicationNoAndSems(String applnNo) throws Exception {
		IFeeReportTxn feeReport = FeeReportTxnImpl.getInstance();
		List<FeePayment> feePaymentList = feeReport.getFeePaymentsByApplicationNoAndSems(applnNo);
		List <FeeReportTO> feeReportList = FeeReportHelper.convertBoToTo(feePaymentList);
		
		return feeReportList;
	}
	/**
	 * @param regNo
	 * @return
	 * @throws Exception
	 * This method is used to get the candidates by registration number
	 */
	public List getFeePaymentsByRegistrationNoAndSems(String regNo) throws Exception {
		IFeeReportTxn feeReport = FeeReportTxnImpl.getInstance();
		List<FeePayment> feePaymentList = feeReport.getFeePaymentsByRegistrationNoAndSems(regNo);
		List <FeeReportTO> feeReportList = FeeReportHelper.convertBoToTo(feePaymentList);
		
		return feeReportList;
	}
	/**
	 * @param courseid
	 * @param year
	 * @param semister
	 * @return
	 * @throws Exception
	 * This method is used to get the candidates based on the search criteria
	 */
	public List getFeePaymentsByStudentDetails(int courseid,int year, int semister,String status) throws Exception {
		IFeeReportTxn feeReport = FeeReportTxnImpl.getInstance();
		List feePaymentList = feeReport.getFeePaymentsByStudentDetails(courseid,year,semister,status);
		List <FeeReportTO> feeReportList = FeeReportHelper.convertBoToToObject(feePaymentList);
		
		return feeReportList;
	}

}
