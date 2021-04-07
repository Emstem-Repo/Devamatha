package com.kp.cms.actions.inventory;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.CashPurchaseReportForm;
import com.kp.cms.handlers.inventory.CashPurchaseReportHandler;
import com.kp.cms.handlers.inventory.InventoryRequestHandler;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.inventory.InvCashPurchaseItemTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CashPuchaseReportAction extends BaseDispatchAction {
	public static Log log = LogFactory.getLog(CashPuchaseReportAction.class);
	
	/**
	init method
	*/
	
	public ActionForward initCashPurchaseReport(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		CashPurchaseReportForm cashPurchaseReportForm = (CashPurchaseReportForm)form;
		try {
			cashPurchaseReportForm.resetFields();
			getInventoryLocationList(request);
			HttpSession session = request.getSession(false);
			session.removeAttribute("cashPurchaseList");
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			cashPurchaseReportForm.setErrorMessage(msg);
			cashPurchaseReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CASHPURCHASE_REPORT);
	}

	/**
	 * creating list and forwarding to jsp page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward SearchCashPurchaseItems(ActionMapping mapping, ActionForm form, 
					HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		CashPurchaseReportForm cashPurchaseReportForm = (CashPurchaseReportForm)form;	
		HttpSession session = request.getSession(false);
		if(session.getAttribute("cashPurchaseList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = cashPurchaseReportForm.validate(mapping, request);
			boolean isValidStartDate = true;
			boolean isValidEndDate = true;
			Date startDate = null;
			Date endDate = null;

			try {
				if(!errors.isEmpty()){
					saveErrors(request, errors);
					getInventoryLocationList(request);
					return mapping.findForward(CMSConstants.CASHPURCHASE_REPORT);
				}
				if(cashPurchaseReportForm.getStartDate()!= null && !cashPurchaseReportForm.getStartDate().trim().isEmpty()){
					isValidStartDate = CommonUtil.isValidDate(cashPurchaseReportForm.getStartDate());
				}
				if(cashPurchaseReportForm.getEndDate()!= null && !cashPurchaseReportForm.getEndDate().trim().isEmpty()){
					isValidEndDate = CommonUtil.isValidDate(cashPurchaseReportForm.getEndDate());
				}
				//date validation
				if(!isValidStartDate || !isValidEndDate){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
					saveErrors(request, errors);
					getInventoryLocationList(request);
					return mapping.findForward(CMSConstants.CASHPURCHASE_REPORT);		
				}	
				
				if(cashPurchaseReportForm.getStartDate()!= null && !cashPurchaseReportForm.getStartDate().trim().isEmpty()){
					startDate = CommonUtil.ConvertStringToDate(cashPurchaseReportForm.getStartDate());
				}
				if(cashPurchaseReportForm.getEndDate()!= null && !cashPurchaseReportForm.getEndDate().trim().isEmpty()){
					endDate = CommonUtil.ConvertStringToDate(cashPurchaseReportForm.getEndDate());
				}
				
				if(startDate!= null && endDate!= null){	
					if(startDate.compareTo(endDate) == 1){
						errors.add("error", new ActionError("knowledgepro.inventory.cash.purch.report.from.date.cannot.greater"));
						saveErrors(request, errors);
						getInventoryLocationList(request);
						return mapping.findForward(CMSConstants.CASHPURCHASE_REPORT);		
					}
				}
				
				
				List<InvCashPurchaseItemTO> cashPurchaseList = CashPurchaseReportHandler.getInstance().getListOfCashPurchases(cashPurchaseReportForm);
				session.setAttribute("cashPurchaseList", cashPurchaseList);			
			}catch (BusinessException businessException) {
				log.info("Exception submitPerformaReport");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				cashPurchaseReportForm.setErrorMessage(msg);
				cashPurchaseReportForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.CASHPURCHASE_REPORT_RESULT);
	}

		/**
	 * Method to set all active Inventory Locations to the form
	 * @param inventoryRequestForm
	 * @throws Exception
	 */
	public void getInventoryLocationList(HttpServletRequest request) throws Exception{
		List<SingleFieldMasterTO> inventoryLocationList = InventoryRequestHandler.getInstance().getInventoryLocation();
		if( inventoryLocationList != null ){
			request.setAttribute("inventoryLocationList", inventoryLocationList);
		}
	}
	
	

}
