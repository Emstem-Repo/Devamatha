package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.StockReceiptForm;
import com.kp.cms.handlers.inventory.StocksReceiptHandler;
import com.kp.cms.to.inventory.InvAmcTO;
import com.kp.cms.to.inventory.InvLocationTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvStockRecieptItemTo;
import com.kp.cms.utilities.CommonUtil;

/**
 * Action Class for Goods Receipt Note
 *
 */
public class StockReceiptAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StockReceiptAction.class);
	private static final String COUNTID="countID";
	/**
	 * initialize Stock Reciept page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initStocksReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter initStocksReceipt page...");
			StockReceiptForm recieptForm= (StockReceiptForm)form;
			setUserId(request, recieptForm);

		try {
			recieptForm.setPurchaseOrderNo(null);
			recieptForm.setInventoryId(null);
			recieptForm.setReceiptDate(null);
			recieptForm.setReceiptItems(null);
			recieptForm.setItemAmcs(new ArrayList<InvAmcTO>());
		} 
		catch (Exception e) {
			log.error("error in initStocksReceipt...",e);
				throw e;
			
		}
		
		log.info("exit initStocksReceipt page...");
		return mapping.findForward(CMSConstants.INIT_STOCK_RECEIPT_PAGE);
	}
	
	/**
	 * stock Receipt first page submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInitStockReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter submitInitStockReceipt page...");
			StockReceiptForm recieptForm= (StockReceiptForm)form;
			//validation if needed
			ActionMessages errors=recieptForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_STOCK_RECEIPT_PAGE);
			}
			//fetch inventory locations
			List<InvLocationTO> locations=StocksReceiptHandler.getInstance().getAllInventoryLocation();
			recieptForm.setInvLocations(locations);
			//fetch purchase order details
		    InvPurchaseOrderTO orderTo=StocksReceiptHandler.getInstance().getPurchaseOrderDetails(recieptForm);
		    if(orderTo!=null)
			recieptForm.setPurchaseOrder(orderTo);
		    else{
		    	ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveErrors(request, messages);
				return mapping.findForward(CMSConstants.INIT_STOCK_RECEIPT_PAGE);
		    }	
			//fetch existing itemnos in DB
			recieptForm.setPresentItemNos(StocksReceiptHandler.getInstance().getItemNosInAMC());
		} 
		catch (Exception e) {
			log.error("error in submitInitStockReceipt...",e);
				throw e;
			
		}
		
		log.info("exit submitInitStockReceipt page...");
		return mapping.findForward(CMSConstants.STOCK_RECEIPT_MAIN_PAGE);
	}
	
	/**
	 * stock Receipt finalsubmit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitFinalStockReceipt(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			log.info("enter submitFinalStockReceipt page...");
			StockReceiptForm recieptForm= (StockReceiptForm)form;
			//validation if needed
			ActionMessages errors=recieptForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			if(recieptForm.getReceiptDate()!=null && !StringUtils.isEmpty(recieptForm.getReceiptDate().trim())&& !CommonUtil.isValidDate(recieptForm.getReceiptDate().trim())){
				if(errors.get(CMSConstants.STOCK_RECEIPT_DATE_INVALID)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_DATE_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.STOCK_RECEIPT_DATE_INVALID);
					errors.add(CMSConstants.STOCK_RECEIPT_DATE_INVALID,error);
				}
			}
			validateItems(errors,recieptForm.getReceiptItems());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STOCK_RECEIPT_MAIN_PAGE);
			}
			// save the purchase return items
			boolean result=StocksReceiptHandler.getInstance().saveStockReceipts(recieptForm);
			
			if(!result){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionError(CMSConstants.STOCK_RECEIPT_SUBMIT_FAILURE);
				messages.add("messages", message);
				saveErrors(request, messages);
				
				return mapping.findForward(CMSConstants.STOCK_RECEIPT_MAIN_PAGE);
			}
		} 
		catch (Exception e) {
			log.error("error in submitFinalStockReceipt...",e);
				throw e;
			
		}
		
		log.info("exit submitFinalStockReceipt page...");
		return mapping.findForward(CMSConstants.STOCK_RECEIPT_CONFIRM_PAGE);
	}
	/**
	 * @param errors
	 * @param receiptItems
	 */
	private void validateItems(ActionMessages errors,
			List<InvStockRecieptItemTo> receiptItems) {
		if(receiptItems!=null){
			Iterator<InvStockRecieptItemTo> itmItr=receiptItems.iterator();
			while (itmItr.hasNext()) {
				InvStockRecieptItemTo rtnItmTO = (InvStockRecieptItemTo) itmItr.next();
				if(rtnItmTO.getPurchasePrice()!=null 
						&& !StringUtils.isEmpty(rtnItmTO.getPurchasePrice())
						&& !CommonUtil.isValidDecimal(rtnItmTO.getPurchasePrice()))
				{
					// received price not valid
					if(errors.get(CMSConstants.STOCK_RECEIPT_ITEMPRICE_INVALID)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_ITEMPRICE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.STOCK_RECEIPT_ITEMPRICE_INVALID);
						errors.add(CMSConstants.STOCK_RECEIPT_ITEMPRICE_INVALID,error);
					}
				}

				if(rtnItmTO.getQuantity()!=null 
						&& !StringUtils.isEmpty(rtnItmTO.getQuantity()) 
						&& !CommonUtil.isValidDecimal(rtnItmTO.getQuantity()))
				{
					// received price not valid
					if(errors.get(CMSConstants.STOCK_RECEIPT_ITEMQTY_INVALID)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_ITEMQTY_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.STOCK_RECEIPT_ITEMQTY_INVALID);
						errors.add(CMSConstants.STOCK_RECEIPT_ITEMQTY_INVALID,error);
					}
				}else if(rtnItmTO.getQuantity()!=null 
						&& !StringUtils.isEmpty(rtnItmTO.getQuantity()) 
						&& CommonUtil.isValidDecimal(rtnItmTO.getQuantity()) && 
						Double.parseDouble(rtnItmTO.getQuantity()) > rtnItmTO.getOrderQty())
				{
					if(errors.get(CMSConstants.STOCK_RECEIPT_ITEMQTY_LARGE)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_ITEMQTY_LARGE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.STOCK_RECEIPT_ITEMQTY_LARGE);
						errors.add(CMSConstants.STOCK_RECEIPT_ITEMQTY_LARGE,error);
					}
				}
				
				// validate qty. exceed
				double orderdqty=rtnItmTO.getOrderQty();
				double alreadyreceivedqty=rtnItmTO.getAlreadyRcvQty();
				double recieveRequested=0;
				if(rtnItmTO.getQuantity()!=null && !StringUtils.isEmpty(rtnItmTO.getQuantity())&& CommonUtil.isValidDecimal(rtnItmTO.getQuantity()))
					recieveRequested=Double.parseDouble(rtnItmTO.getQuantity());
				if(alreadyreceivedqty==orderdqty && recieveRequested!=0.0)
				{
					if (errors.get(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED).hasNext()) {
						errors.add(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED, new ActionError(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED));
					}
				}else if(orderdqty<alreadyreceivedqty+recieveRequested && recieveRequested!=0.0){
					if (errors.get(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED)!=null && !errors.get(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED).hasNext()) {
						errors.add(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED, new ActionError(CMSConstants.STOCK_RECEIPT_RCVQTY_NOTALLOWED));
					}
				}
				
				
			}
		}
		
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
			String indexString = request.getParameter(StockReceiptAction.COUNTID);
		try {
			
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(StockReceiptAction.COUNTID, indexString);
				}else
					session.removeAttribute(StockReceiptAction.COUNTID);
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
		return mapping.findForward(CMSConstants.STOCK_RECEIPT_AMC_PAGE);
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
				return mapping.findForward(CMSConstants.STOCK_RECEIPT_AMC_PAGE);
			}
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (session.getAttribute(StockReceiptAction.COUNTID) != null){
					index=Integer.parseInt((String)session.getAttribute(StockReceiptAction.COUNTID));
					
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
		return mapping.findForward(CMSConstants.STOCK_RECEIPT_MAIN_PAGE);
	}
	
	
	/**
	 * warranty validation
	 * @param errors
	 * @param recieptForm
	 */
	private void validateAMCDetails(ActionMessages errors,
			StockReceiptForm recieptForm) {
		List<String> itemnos= new ArrayList<String>();
		if(recieptForm.getItemAmcs()!=null){
			Iterator<InvAmcTO> qualItr=recieptForm.getItemAmcs().iterator();
			while (qualItr.hasNext()) {
				InvAmcTO itemTO = (InvAmcTO) qualItr.next();
				
					if(itemTO.getItemNo()==null || StringUtils.isEmpty(itemTO.getItemNo())){
						if(errors.get(CMSConstants.AMC_ITEMNO_REQUIRED)!=null && !errors.get(CMSConstants.AMC_ITEMNO_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_REQUIRED);
							errors.add(CMSConstants.AMC_ITEMNO_REQUIRED,error);
						}
//					}
//					else if(!StringUtils.isNumeric(itemTO.getItemNo())){
//						if(errors.get(CMSConstants.AMC_ITEMNO_INVALID)!=null && !errors.get(CMSConstants.AMC_ITEMNO_INVALID).hasNext()) {
//							ActionMessage error = new ActionMessage(CMSConstants.AMC_ITEMNO_INVALID);
//							errors.add(CMSConstants.AMC_ITEMNO_INVALID,error);
//						}

					}else if(itemTO.getItemNo()!=null && !StringUtils.isEmpty(itemTO.getItemNo())){
						
						if(!StringUtils.isNumeric(itemTO.getItemNo())){
							errors.add(CMSConstants.ERROR,new ActionError("errors.integer","Serial Number"));
						}else{
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
			}
			
			
			
			
			
			
		}
		
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
	public ActionForward forwardStockReceiptMainPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(CMSConstants.STOCK_RECEIPT_MAIN_PAGE);
	}
}
