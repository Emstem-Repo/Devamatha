package com.kp.cms.handlers.fee;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.forms.fee.BulkChallanPrintForm;
import com.kp.cms.helpers.fee.BulkChallanPrintHelper;
import com.kp.cms.transactions.fee.IBulkChallanPrintTransaction;
import com.kp.cms.transactionsimpl.fee.BulkChallanPrintTransactionimpl;
import com.kp.cms.utilities.CommonUtil;

public class BulkChallanPrintHandler {
	/**
	 * Singleton object of BulkChallanPrintHandler
	 */
	private static volatile BulkChallanPrintHandler bulkChallanPrintHandler = null;
	private static final Log log = LogFactory.getLog(BulkChallanPrintHandler.class);
	private BulkChallanPrintHandler() {
		
	}
	/**
	 * return singleton object of BulkChallanPrintHandler.
	 * @return
	 */
	public static BulkChallanPrintHandler getInstance() {
		if (bulkChallanPrintHandler == null) {
			bulkChallanPrintHandler = new BulkChallanPrintHandler();
		}
		return bulkChallanPrintHandler;
	}
	
	/**
	 * querying the database to get the challan data to print
	 * @param bulkChallanPrintForm
	 * @return
	 * @throws Exception
	 */
	public boolean getChallanData(BulkChallanPrintForm bulkChallanPrintForm,HttpServletRequest request) throws Exception{
		boolean dataAvailable=false;
		String fromDate=CommonUtil.ConvertStringToDateFormat(bulkChallanPrintForm.getFromDate(), "dd/MM/yyyy","yyyy-MM-dd");
		String toDate=CommonUtil.ConvertStringToDateFormat(bulkChallanPrintForm.getToDate(), "dd/MM/yyyy","yyyy-MM-dd");
		IBulkChallanPrintTransaction txn=BulkChallanPrintTransactionimpl.getInstance();
		List<FeePayment> feePaymentBoList=txn.getChallanData(fromDate,toDate);
		//dataAvailable=BulkChallanPrintHelper.getInstance().convertBOtoTO(feePaymentBoList,bulkChallanPrintForm,request);
		return dataAvailable;
	}
	/**
	 * @param accId
	 * @return
	 * @throws Exception
	 */
	public byte[] getLogoByAccountId(int accId) throws Exception {
		IBulkChallanPrintTransaction transaction=BulkChallanPrintTransactionimpl.getInstance();
		return transaction.getLogoById(accId);
	}
}
