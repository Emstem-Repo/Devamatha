package com.kp.cms.actions.inventory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.InvPurchaseOrderItem;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.PurchaseReturnForm;
import com.kp.cms.handlers.inventory.PurchaseReturnHandler;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnItemTO;
import com.kp.cms.transactions.inventory.IPurchaseReturnTransaction;
import com.kp.cms.transactionsimpl.inventory.PurchaseReturnTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * Action class for Purchase return
 *
 */
public class PurchaseReturnAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PurchaseReturnAction.class);
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	/**
	 * initialize Purchase return page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPurchaseReturn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initPurchaseReturn page...");
			PurchaseReturnForm returnForm= (PurchaseReturnForm)form;
			setUserId(request, returnForm);

		try {
			returnForm.setPurchaseOrderNo(null);
			returnForm.setReturnItems(null);
			returnForm.setReturnReason(null);
			returnForm.setVendorBillDt(null);
			returnForm.setVendorBillNo(null);
			returnForm.setPurchaseOrder(null);
			returnForm.setPurchaseRtnDate(null);
		} 
		catch (Exception e) {
			log.error("error in initPurchaseReturn...",e);
				throw e;
			
		}
		
		log.info("exit initPurchaseReturn page...");
		return mapping.findForward(CMSConstants.INIT_PURCHASE_RETURN_PAGE);
	}
	
	
	/**
	 *  Purchase return first page submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInitPurchaseReturn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitInitPurchaseReturn page...");
		PurchaseReturnForm returnForm= (PurchaseReturnForm)form;
			//validation if needed
			ActionMessages errors=returnForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_PURCHASE_RETURN_PAGE);
			}
			//fetch purchase order details
			InvPurchaseOrderTO orderTo= PurchaseReturnHandler.getInstance().getPurchaseOrderDetails(returnForm);
			if(orderTo!=null)
			returnForm.setPurchaseOrder(orderTo);
			else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveErrors(request, messages);
				return mapping.findForward(CMSConstants.INIT_PURCHASE_RETURN_PAGE);
			}
		} 
		catch (Exception e) {
			log.error("error in submitInitPurchaseReturn...",e);
				throw e;
			
		}
		
		log.info("exit submitInitPurchaseReturn page...");
		return mapping.findForward(CMSConstants.PURCHASE_RETURN_MAIN_PAGE);
	}
	
	
	/**
	 *  Purchase return final submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitFinalPurchaseReturn(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitFinalPurchaseReturn page...");
		PurchaseReturnForm returnForm= (PurchaseReturnForm)form;
		//validation if needed
		ActionMessages errors=returnForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			//purchase return date validation
			if(returnForm.getPurchaseRtnDate()!=null && !StringUtils.isEmpty(returnForm.getPurchaseRtnDate())&& !CommonUtil.isValidDate(returnForm.getPurchaseRtnDate())){
				if (errors.get(CMSConstants.PURCHASERETURNDATE_INVALID)!=null && !errors.get(CMSConstants.PURCHASERETURNDATE_INVALID).hasNext()) {
					errors.add(CMSConstants.PURCHASERETURNDATE_INVALID, new ActionError(CMSConstants.PURCHASERETURNDATE_INVALID));
				}
			}
			//vendor bill date validation
			if(returnForm.getVendorBillDt()!=null && !StringUtils.isEmpty(returnForm.getVendorBillDt())&& !CommonUtil.isValidDate(returnForm.getVendorBillDt())){
				if (errors.get(CMSConstants.PURCHASERETURN_BILLDATE_INVALID)!=null && !errors.get(CMSConstants.PURCHASERETURN_BILLDATE_INVALID).hasNext()) {
					errors.add(CMSConstants.PURCHASERETURN_BILLDATE_INVALID, new ActionError(CMSConstants.PURCHASERETURN_BILLDATE_INVALID));
				}
			}
			//vendor bill date and purchase date past
			
			
			if(returnForm.getVendorBillDt()!=null && !StringUtils.isEmpty(returnForm.getVendorBillDt())&& CommonUtil.isValidDate(returnForm.getVendorBillDt())
					&& returnForm.getPurchaseRtnDate()!=null && !StringUtils.isEmpty(returnForm.getPurchaseRtnDate())&& CommonUtil.isValidDate(returnForm.getPurchaseRtnDate())){
				
					String vnddate=CommonUtil.ConvertStringToDateFormat(returnForm.getVendorBillDt(), PurchaseReturnAction.FROM_DATEFORMAT,PurchaseReturnAction.TO_DATEFORMAT);
					String rtndate=CommonUtil.ConvertStringToDateFormat(returnForm.getPurchaseRtnDate(), PurchaseReturnAction.FROM_DATEFORMAT,PurchaseReturnAction.TO_DATEFORMAT);
					Date startdate=new Date(vnddate);
					Date enddate=new Date(rtndate);
					
					
						if(startdate.compareTo(enddate)==1)
						{
							if (errors.get(CMSConstants.PURCHASERETURN_BILLDATE_PAST)!=null && !errors.get(CMSConstants.PURCHASERETURN_BILLDATE_PAST).hasNext()) {
								errors.add(CMSConstants.PURCHASERETURN_BILLDATE_PAST, new ActionError(CMSConstants.PURCHASERETURN_BILLDATE_PAST));
							}
						}
						

				
			}
			
			
			
			
			
			
			
			//reason length validation
			if(returnForm.getReturnReason()!=null && !StringUtils.isEmpty(returnForm.getReturnReason())&& returnForm.getReturnReason().length()>=100){
				if (errors.get(CMSConstants.PURCHASERETURN_REASON_LARGE)!=null && !errors.get(CMSConstants.PURCHASERETURN_REASON_LARGE).hasNext()) {
					errors.add(CMSConstants.PURCHASERETURN_REASON_LARGE, new ActionError(CMSConstants.PURCHASERETURN_REASON_LARGE));
				}
			}
			if(returnForm.getReturnItems()!=null && !returnForm.getReturnItems().isEmpty()){
			
				Iterator<InvPurchaseReturnItemTO> itmItr=returnForm.getReturnItems().iterator();
				while (itmItr.hasNext()) {
					InvPurchaseReturnItemTO rtnItmTO = (InvPurchaseReturnItemTO) itmItr.next();
					if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity())){
						if(	StringUtils.isNumeric(rtnItmTO.getQuantity()))
						{
							if(Integer.parseInt(rtnItmTO.getQuantity())> rtnItmTO.getReceivedQuantity()){
								if (errors.get(CMSConstants.PURCHASERETURN_RTNQTY_LARGE)!=null && !errors.get(CMSConstants.PURCHASERETURN_RTNQTY_LARGE).hasNext()) {
									errors.add(CMSConstants.PURCHASERETURN_RTNQTY_LARGE, new ActionError(CMSConstants.PURCHASERETURN_RTNQTY_LARGE));
								}
							}
						}else{
							if (errors.get(CMSConstants.PURCHASERETURN_RTNQTY_INVALID)!=null && !errors.get(CMSConstants.PURCHASERETURN_RTNQTY_INVALID).hasNext()) {
								errors.add(CMSConstants.PURCHASERETURN_RTNQTY_INVALID, new ActionError(CMSConstants.PURCHASERETURN_RTNQTY_INVALID));
							}
						}
					}
				}
				
			}
			// validate already returned qty with received qty
			validateItemQty(returnForm.getPurchaseOrder(),returnForm.getReturnItems(),errors);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PURCHASE_RETURN_MAIN_PAGE);
			}
			// save the purchase return items
			boolean result=PurchaseReturnHandler.getInstance().savePurchaseReturns(returnForm);
			
			if(!result){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionError(CMSConstants.PURCHASERETURN_SUBMIT_FAILURE);
				messages.add("messages", message);
				saveErrors(request, messages);
				return mapping.findForward(CMSConstants.PURCHASE_RETURN_MAIN_PAGE);
			}
		} 
		catch (Exception e) {
			log.error("error in submitFinalPurchaseReturn...",e);
				throw e;
			
		}
		
		log.info("exit submitFinalPurchaseReturn page...");
		return mapping.findForward(CMSConstants.PURCHASE_RETURN_CONFIRM_PAGE);
	}


	/**
	 * compare return qty < already returned
	 * @param purchaseOrder 
	 * @param purchaseOrder
	 * @param returnItems
	 * @param errors
	 */
	private void validateItemQty(InvPurchaseOrderTO purchaseOrder, List<InvPurchaseReturnItemTO> returnItems, ActionMessages errors) throws Exception{
//		IPurchaseReturnTransaction txn= new PurchaseReturnTransactionImpl();
		if(returnItems!=null){
			
			
				Iterator<InvPurchaseReturnItemTO> retItr=returnItems.iterator();
				
					 while (retItr.hasNext()) {
						InvPurchaseReturnItemTO returnItem = (InvPurchaseReturnItemTO) retItr.next();
						
								double receivedqty=returnItem.getReceivedQuantity();
//								double alreadyreturnedqty=txn.getAlreadyReturnedQty(returnItem.getInvItemId().getId(),purchaseOrder.getId());
								double alreadyreturnedqty=returnItem.getAlreadyRtndQty();
								double returnRequested=0;
								if(returnItem.getQuantity()!=null && !StringUtils.isEmpty(returnItem.getQuantity())&& CommonUtil.isValidDecimal(returnItem.getQuantity()))
								returnRequested=Double.parseDouble(returnItem.getQuantity());
								if(alreadyreturnedqty==receivedqty)
								{
									if (errors.get(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED)!=null && !errors.get(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED).hasNext()) {
										errors.add(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED, new ActionError(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED));
									}
								}else if(receivedqty<alreadyreturnedqty+returnRequested){
									if (errors.get(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED)!=null && !errors.get(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED).hasNext()) {
										errors.add(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED, new ActionError(CMSConstants.PURCHASERETURN_RTNQTY_NOTALLOWED));
									}
								}
						
			}
		}
		
	}
	
}
