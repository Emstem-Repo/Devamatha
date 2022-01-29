package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.handlers.inventory.UpdateWarrantyDetailsHandler;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class UpdateWarrantyItemsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UpdateWarrantyItemsAction.class);
	private static final String COUNTID="countID";
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         UPDATE_WARRANTY
	 * @throws Exception
	 */
	public ActionForward initUpdateWarrantyDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initUpdateWarrantyDetails ");
		StockReceiptForm stockReceiptForm = (StockReceiptForm) form;
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			setUserId(request, stockReceiptForm);
			stockReceiptForm.setPurchaseOrderNo(null);
		} catch (Exception e) {
			log.error("error initUpdateWarrantyDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stockReceiptForm.setErrorMessage(msg);
				stockReceiptForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initUpdateWarrantyDetails ");
	
		return mapping.findForward(CMSConstants.UPDATE_WARRANTY);
	}	
	
	/** setting amc list to request
	 * forward to the second page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return SUBMIT_AMC_DETAILS_ENTRY
	 * @throws Exception
	 */
	public ActionForward submitUpdateWarrantyDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside submitUpdateAmcDetails");
		StockReceiptForm stockReceiptForm = (StockReceiptForm) form;
		
		 ActionErrors errors = stockReceiptForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_WARRANTY);
			}
			
			setReceiptListForm(request, stockReceiptForm);
			if(stockReceiptForm.getReceiptItems() == null || stockReceiptForm.getReceiptItems().size() <= 0){
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_WARRANTY);
			}
			
		} catch (Exception e) {
			log.error("error in submitAmcDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stockReceiptForm.setErrorMessage(msg);
				stockReceiptForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("leaving submitUpdateAmcDetails");
		return mapping.findForward(CMSConstants.SUBMIT_UPDATE_WARRANTY);	
	}
	
	/**
	 * 
	 * @param request
	 *            This method sets the amcList to Request
	 * @throws Exception
	 */
	public void setReceiptListForm(HttpServletRequest request, StockReceiptForm stockReceiptForm) throws Exception {
		log.debug("inside setAmcListRequest");
		String orderNo = "";
		if(stockReceiptForm.getPurchaseOrderNo()!= null && !stockReceiptForm.getPurchaseOrderNo().trim().isEmpty()){
			orderNo = stockReceiptForm.getPurchaseOrderNo();
		}
		List<InvStockRecieptItemTo> receiptList = UpdateWarrantyDetailsHandler.getInstance().getReceiptDetails(orderNo); 
		stockReceiptForm.setReceiptItems(receiptList);
	}
	

	/**
	 * items AMC entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAmcEntryPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter initAmcEntryPage page...");
			StockReceiptForm recieptForm= (StockReceiptForm)form;
			String indexString = request.getParameter(UpdateWarrantyItemsAction.COUNTID);
		try {
			
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(UpdateWarrantyItemsAction.COUNTID, indexString);
				}else
					session.removeAttribute(UpdateWarrantyItemsAction.COUNTID);
			}
			
			List<InvStockRecieptItemTo> items=recieptForm.getReceiptItems();
			if(items!=null ){
				
				Iterator<InvStockRecieptItemTo> qualItr=items.iterator();
				while (qualItr.hasNext()) {
						InvStockRecieptItemTo itemTO = (InvStockRecieptItemTo) qualItr.next();
							if (itemTO.getCountId()==index) {
//								if (itemTO.getInvAmcs() != null)
//									recieptForm.setItemAmcs(itemTO.getInvAmcs());
								
								if(itemTO.getInvAmcs()!=null && !itemTO.getInvAmcs().isEmpty()){
									recieptForm.setItemAmcs(itemTO.getInvAmcs());
								}else if(itemTO.getQuantity()!=null 
										&& !StringUtils.isEmpty(itemTO.getQuantity())
										&& StringUtils.isNumeric(itemTO.getQuantity()))
								{
									int noofitems=Integer.parseInt(itemTO.getQuantity());
									List<InvAmcTO> amcs= new ArrayList<InvAmcTO>();
									for(int i=0; i<noofitems;i++){
										InvAmcTO amcTo= new InvAmcTO();
										amcs.add(amcTo);
									}
									recieptForm.setItemAmcs(amcs);
								}
							}
							
						}
			}
			
		} 
		catch (Exception e) {
			log.error("error in initAmcEntryPage...",e);
				throw e;
			
		}
		
		log.info("exit initAmcEntryPage page...");
		return mapping.findForward(CMSConstants.UPDATE_WARRANTY_AMC_PAGE);
	}
	


	/**
	 * items AMC entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAMCEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter addAMCEntry page...");
			StockReceiptForm recieptForm= (StockReceiptForm)form;
			ActionMessages errors=recieptForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			
			validateAMCDetails(errors,recieptForm);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_WARRANTY_AMC_PAGE);
			}
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (session.getAttribute(UpdateWarrantyItemsAction.COUNTID) != null){
					index=Integer.parseInt((String)session.getAttribute(UpdateWarrantyItemsAction.COUNTID));
					
				}
			}
			
			List<InvStockRecieptItemTo> items=recieptForm.getReceiptItems();
			if(items!=null ){
				
				Iterator<InvStockRecieptItemTo> qualItr=items.iterator();
				while (qualItr.hasNext()) {
						InvStockRecieptItemTo itemTO = (InvStockRecieptItemTo) qualItr.next();
							if (itemTO.getCountId()==index) {
									itemTO.setInvAmcs(recieptForm.getItemAmcs());
									
							}
							
						}
			}
			
		} 
		catch (Exception e) {
			log.error("error in addAMCEntry...",e);
				throw e;
			
		}
		
		log.info("exit addAMCEntry page...");
		return mapping.findForward(CMSConstants.SUBMIT_UPDATE_WARRANTY);
	}
		
	/**
	 * warranty validation
	 * @param errors
	 * @param recieptForm
	 */
	private void validateAMCDetails(ActionMessages errors,
			StockReceiptForm recieptForm) {
		List<String> itemnos= new ArrayList<String>();
		Date startDate = null;
		Date endDate = null;
		if(recieptForm.getItemAmcs()!=null){
			Iterator<InvAmcTO> qualItr=recieptForm.getItemAmcs().iterator();
			while (qualItr.hasNext()) {
				InvAmcTO itemTO = (InvAmcTO) qualItr.next();
				
					if(itemTO.getItemNo()==null || StringUtils.isEmpty(itemTO.getItemNo().trim())){
						if(errors.get(CMSConstants.AMC_ITEMNO_REQUIRED)!=null && !errors.get(CMSConstants.AMC_ITEMNO_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_REQUIRED);
							errors.add(CMSConstants.AMC_ITEMNO_REQUIRED,error);
						}
					
					/*}else if(!StringUtils.isNumeric(itemTO.getItemNo())){
						if(errors.get(CMSConstants.AMC_ITEMNO_INVALID)!=null && !errors.get(CMSConstants.AMC_ITEMNO_INVALID).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_INVALID);
							errors.add(CMSConstants.AMC_ITEMNO_INVALID,error);
						}
					*/
					}else if(itemTO.getItemNo()!=null && !StringUtils.isEmpty(itemTO.getItemNo())){
						//check duplicate item nos
						if(!itemnos.contains(itemTO.getItemNo())){
							itemnos.add(itemTO.getItemNo());
						}else
						{
							if(errors.get(CMSConstants.AMC_ITEMNO_DUPLICATE)!=null && !errors.get(CMSConstants.AMC_ITEMNO_DUPLICATE).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_DUPLICATE);
								errors.add(CMSConstants.AMC_ITEMNO_DUPLICATE,error);
							}
						}
					}
					
					if(itemTO.getItemNo()!=null && !StringUtils.isEmpty(itemTO.getItemNo())){
						
						if(recieptForm.getPresentItemNos()!=null 
								&& recieptForm.getPresentItemNos().contains(itemTO.getItemNo())){
							if(errors.get(CMSConstants.AMC_ITEMNO_PRESENT)!=null && !errors.get(CMSConstants.AMC_ITEMNO_PRESENT).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_PRESENT);
								errors.add(CMSConstants.AMC_ITEMNO_PRESENT,error);
							}
						}
					}
					
					if(itemTO.getWarrantyStartDate()==null || StringUtils.isEmpty(itemTO.getWarrantyStartDate())){
						if(errors.get(CMSConstants.AMC_STARTDATE_REQUIRED)!=null && !errors.get(CMSConstants.AMC_STARTDATE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_STARTDATE_REQUIRED);
							errors.add(CMSConstants.AMC_STARTDATE_REQUIRED,error);
						}
					}else if(!CommonUtil.isValidDate(itemTO.getWarrantyStartDate())){
						if(errors.get(CMSConstants.AMC_STARTDATE_INVALID)!=null && !errors.get(CMSConstants.AMC_STARTDATE_INVALID).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_STARTDATE_INVALID);
							errors.add(CMSConstants.AMC_STARTDATE_INVALID,error);
						}
					}
					
					
					if(itemTO.getWarrantyEndDate()==null || StringUtils.isEmpty(itemTO.getWarrantyEndDate())){
						if(errors.get(CMSConstants.AMC_ENDDATE_REQUIRED)!=null && !errors.get(CMSConstants.AMC_ENDDATE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_ENDDATE_REQUIRED);
							errors.add(CMSConstants.AMC_ENDDATE_REQUIRED,error);
						}
					}else if(!CommonUtil.isValidDate(itemTO.getWarrantyEndDate())){
						if(errors.get(CMSConstants.AMC_ENDDATE_INVALID)!=null && !errors.get(CMSConstants.AMC_ENDDATE_INVALID).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_ENDDATE_INVALID);
							errors.add(CMSConstants.AMC_ENDDATE_INVALID,error);
						}
					}
					
					if(itemTO.getWarrantyStartDate()!= null && !itemTO.getWarrantyStartDate().trim().isEmpty()){
						startDate = CommonUtil.ConvertStringToDate(itemTO.getWarrantyStartDate());
					}
					if(itemTO.getWarrantyEndDate()!= null && !itemTO.getWarrantyEndDate().trim().isEmpty()){
						endDate = CommonUtil.ConvertStringToDate(itemTO.getWarrantyEndDate());
					}
					
					
					if(startDate!= null && endDate!= null){	
						if(startDate.compareTo(endDate) == 1){
							errors.add("error", new ActionError("knowledgepro.startdate.connotbeless"));
							break;
						}
					}
					
			}
			
		
			
		}
		
	}
	

	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return updateWarrantyDetails
	 * @throws Exception
	 */
	public ActionForward updateWarrantyDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request,	HttpServletResponse response) throws Exception {
		log.debug("inside updateWarrantyDetails");
		StockReceiptForm stockReceiptForm = (StockReceiptForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		Boolean isAdded;
		try {
			if(!isAmcDetailsFound(stockReceiptForm)){
				errors.add("error", new ActionError("knowledgepro.inventory.update.warranty.details.no.entry.done"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.SUBMIT_UPDATE_WARRANTY);
			}			
			isAdded = UpdateWarrantyDetailsHandler.getInstance().saveWarrantyDetails(stockReceiptForm);

		} catch (Exception e) {
			log.error("error in updateAmcDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stockReceiptForm.setErrorMessage(msg);
				stockReceiptForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.inventory.update.warranty.details.add.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			stockReceiptForm.setPurchaseOrderNo(null);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.update.warranty.details.add.failure"));
			saveErrors(request, errors);
		}
		
		log.debug("leaving updateWarrantyDetails");
		return mapping.findForward(CMSConstants.UPDATE_WARRANTY);
	}
		
	/**
	 * 
	 * @param stockReceiptForm
	 * @return
	 */
	private boolean isAmcDetailsFound(StockReceiptForm stockReceiptForm){
		InvStockRecieptItemTo itemTo;
		InvAmcTO invAmcTO;
		Iterator<InvStockRecieptItemTo> itr = stockReceiptForm.getReceiptItems().iterator();
		Boolean isFound = false;
		while(itr.hasNext()){
			itemTo = itr.next();
			if(itemTo.getInvAmcs()!= null){
				Iterator<InvAmcTO> amcitr =itemTo.getInvAmcs().iterator();
				while(amcitr.hasNext()){
					invAmcTO = amcitr.next();
					if(invAmcTO.getItemNo()!= null && !invAmcTO.getItemNo().trim().isEmpty()){
						isFound = true;
					}
				}
				 
			}
		}
		return isFound;	
		
	}

	/**
	 * forward main page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardToMainPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(CMSConstants.SUBMIT_UPDATE_WARRANTY);
	}	
}
