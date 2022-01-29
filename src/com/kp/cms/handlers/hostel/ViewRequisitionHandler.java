package com.kp.cms.handlers.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.forms.hostel.ViewReqStatusForm;
import com.kp.cms.helpers.hostel.RequisitionStatusHelper;
import com.kp.cms.to.hostel.VReqStatusTo;
import com.kp.cms.transactions.hostel.IViewReqStatusTransaction;
import com.kp.cms.transactionsimpl.hostel.ViewReqStatusTransactionImpl;

public class ViewRequisitionHandler {

private static final Log log = LogFactory.getLog(ViewRequisitionHandler.class);
	
	private static volatile ViewRequisitionHandler requisitionHandler = null;

	private ViewRequisitionHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static ViewRequisitionHandler getInstance() {
		if (requisitionHandler == null) {
			requisitionHandler = new ViewRequisitionHandler();
		}
		return requisitionHandler;
	}
	/**
	 * @param viewReqStatusForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<VReqStatusTo> getRequisitionStatusDetails(ViewReqStatusForm viewReqStatusForm, HttpServletRequest request) throws Exception {
		log.info("Inside handler getRequisitionStatusDetails");
		IViewReqStatusTransaction transaction=new ViewReqStatusTransactionImpl();
		List<VReqStatusTo> vReqStatusTo=null;;
		String query =RequisitionStatusHelper.Query(viewReqStatusForm);
		List<HlApplicationForm> requisitionBO = transaction.getRequisitionStatusDetails(query);
		vReqStatusTo =RequisitionStatusHelper.copyRequisitionBOToTO(requisitionBO);
		log.info("Leaving into getRequisitionStatusDetails Details of of ViewRequisitionHandler");
		return  vReqStatusTo;
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	

