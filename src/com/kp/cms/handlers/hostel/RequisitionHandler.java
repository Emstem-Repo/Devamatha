package com.kp.cms.handlers.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.forms.hostel.ViewRequisitionsForm;
import com.kp.cms.helpers.hostel.RequisitionHelper;
import com.kp.cms.to.hostel.RequisitionsTo;
import com.kp.cms.to.hostel.VRequisitionsTO;
import com.kp.cms.transactions.hostel.IRequisitionTransaction;
import com.kp.cms.transactionsimpl.hostel.RequisitionTransactionImpl;

public class RequisitionHandler {
	
	private static final Log log = LogFactory.getLog(RequisitionHandler.class);
	
	private static volatile RequisitionHandler requisitionHandler = null;

	private RequisitionHandler() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static RequisitionHandler getInstance() {
		if (requisitionHandler == null) {
			requisitionHandler = new RequisitionHandler();
		}
		return requisitionHandler;
	}
	/**
	 * @param viewRequisitionsForm
	 * @return
	 * @throws Exception
	 */
	public List<VRequisitionsTO> getRequisitionDetails(ViewRequisitionsForm viewRequisitionsForm) throws Exception {
		log.info("Inside handler getRequisitionDetails");
		IRequisitionTransaction transaction=new RequisitionTransactionImpl();
		String dynamicQuery =RequisitionHelper.buildQuery(viewRequisitionsForm);
		List<Object[]> requisitionBO = transaction.getRequisitionDetails(dynamicQuery);
		List<VRequisitionsTO> vRequisitionsList =RequisitionHelper.copyRequisitionBOToTO(requisitionBO,viewRequisitionsForm);
		log.info("Leaving into getAddres Details of of RequisitionHandler");
		return  vRequisitionsList;
	}
	/**
	 * @param hlAppId
	 * @param viewRequisitionsForm
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public RequisitionsTo getRequisitionDetailsToShow(int hlAppId,ViewRequisitionsForm viewRequisitionsForm,HttpServletRequest req) throws Exception {
		log.info("Inside handler getRequisitionDetailsToShow");
		IRequisitionTransaction transaction=new RequisitionTransactionImpl();
		String dynamicQuery =RequisitionHelper.getQuery(hlAppId,viewRequisitionsForm);
		List<HlApplicationForm> requisitionBO = transaction.getRequisitionDetailsToShow(dynamicQuery);
		RequisitionsTo requisitionsTo =RequisitionHelper.copyRequisitionBOToTO(requisitionBO,req);
		log.info("Leaving into getAddres Details of of RequisitionHandler");
		return requisitionsTo;
	}
	
	/**
	 * @param viewRequisitionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean getapprvieIds(ViewRequisitionsForm viewRequisitionsForm) throws Exception {
		log.info("Inside handler getRequisitionDetailsToShow");
		IRequisitionTransaction transaction=new RequisitionTransactionImpl();
		List<VRequisitionsTO> list1 =RequisitionHelper.getapprvieIds(viewRequisitionsForm);
	boolean suc=transaction.markAsApprove(list1);
		return suc;
	}
	/**
	 * @param hlAppId
	 * @param viewRequisitionsForm
	 * @return
	 */
	public RequisitionsTo getRequisitionDetailsToDelete(int hlAppId,
			ViewRequisitionsForm viewRequisitionsForm) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * @param viewRequisitionsForm
	 * @return
	 * @throws Exception
	 */
	public boolean getapprvieIdforSingle(ViewRequisitionsForm viewRequisitionsForm) throws Exception {
		log.info("Inside handler getRequisitionDetailsToShow");
		IRequisitionTransaction transaction=new RequisitionTransactionImpl();
		int hlId=viewRequisitionsForm.getHlAppId();
		String rommTypeId=viewRequisitionsForm.getRoomtype();
		int rid=Integer.parseInt(rommTypeId);
		String hostelId=viewRequisitionsForm.getHostelId1();
		int hid=Integer.parseInt(hostelId);
		String  status=viewRequisitionsForm.getStatus1();
		boolean suc=transaction.updateStatus(hlId,rid,hid,status);
		return suc;
	}
	
	

}
