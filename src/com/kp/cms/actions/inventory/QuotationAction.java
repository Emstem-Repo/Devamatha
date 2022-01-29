package com.kp.cms.actions.inventory;

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
import com.kp.cms.forms.inventory.QuotationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.QuotationHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Class made for handling Quotation transactions
 *
 */
@SuppressWarnings("deprecation")
public class QuotationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(QuotationAction.class);
	/**
	 * initialize Purchase order page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initQuotaion(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initPurchaseOrder page...");
			QuotationForm orderForm= (QuotationForm)form;
			setUserId(request, orderForm);
		try {
				cleanupPageData(orderForm);
				//setQuoteNo(orderForm);
				setRequiredDataToForm(orderForm);
		} 
		catch (Exception e) {
			log.error("error in initPurchaseOrder...",e);
				throw e;
		}
		log.info("exit initPurchaseOrder page...");
		return mapping.findForward(CMSConstants.INIT_QUOTATION_PAGE);
	}
	
	
	/**
	 * @param orderForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(QuotationForm orderForm) throws Exception {
		List<VendorTO> vendorList=VendorHandler.getInstance().getVendorDetails();
		orderForm.setVendorList(vendorList);
		orderForm.setCampusList(InventoryLocationHandler.getInstance().getCampus());
		orderForm.setInvCompanyList(QuotationHandler.getInstance().getCompany());
	}


	/**
	 * @param orderForm
	 */
	private void setQuoteNo(QuotationForm orderForm) throws Exception {
		log.info("enter setPurchaseOrderNo...");
		QuotationHandler handler=QuotationHandler.getInstance();
		handler.setQuoteNo(orderForm);
		log.info("exit setPurchaseOrderNo...");
		
	}


	/**
	 * @param orderForm
	 */
	private void cleanupPageData(QuotationForm orderForm) {
		log.info("enter cleanupPageData...");
		orderForm.setQuotationNo(null);
		orderForm.setPurchaseDate(null);
		orderForm.setVendorId(null);
		orderForm.setRemarks(null);
		orderForm.setTermConditions(null);
		orderForm.setSiteDelivery(null);
		orderForm.setPurchaseItemList(null);
		orderForm.setSelectedItemId(null);
		orderForm.setSelectedItemQty(null);
		orderForm.setCampusId(null);
		orderForm.setCompanyId(null);
		orderForm.setMode(null);
		orderForm.setDataExistsForEdit(false);
		orderForm.setServiceTax(null);
		orderForm.setServiceTaxCost(null);
		log.info("exit cleanupPageData...");
		
	}


	/**
	 *  Purchase order first page submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInitQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitInitPurchaseOrder page...");
			QuotationForm orderForm= (QuotationForm)form;
			//validation if needed
			ActionMessages errors=orderForm.validate(mapping, request);
			if(errors==null)
				errors= new ActionMessages();
		try {
			//Validation for Edit of Quotation 
			if(orderForm.getMode()!=null && !orderForm.getMode().isEmpty() && orderForm.getMode().equalsIgnoreCase("Edit")){
				if(orderForm.getQuotationNo()==null || orderForm.getQuotationNo().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("inventory.quotation.no.required"));
			}
				//purchase date validation
				if(orderForm.getPurchaseDate()!=null && !StringUtils.isEmpty(orderForm.getPurchaseDate())&& !CommonUtil.isValidDate(orderForm.getPurchaseDate())){
					if (errors.get(CMSConstants.PURCHASEORDERDATE_INVALID)!=null && !errors.get(CMSConstants.PURCHASEORDERDATE_INVALID).hasNext()) {
						errors.add(CMSConstants.PURCHASEORDERDATE_INVALID, new ActionError(CMSConstants.PURCHASEORDERDATE_INVALID));
					}
				}
				//terms condition length validation
				if(orderForm.getTermConditions()!=null && !StringUtils.isEmpty(orderForm.getTermConditions())&& orderForm.getTermConditions().length()>=2000){
					if (errors.get(CMSConstants.PURCHASEORDER_TC_LARGE)!=null && !errors.get(CMSConstants.PURCHASEORDER_TC_LARGE).hasNext()) {
						errors.add(CMSConstants.PURCHASEORDER_TC_LARGE, new ActionError(CMSConstants.PURCHASEORDER_TC_LARGE));
					}
				}
				//replacing the enter key with <br> (new line) to set in database and display the same in print PO jsp
				if (orderForm.getTermConditions() != null
						&& !StringUtils.isEmpty(orderForm.getTermConditions())){
					String[] args=orderForm.getTermConditions().split("\r\n");
					StringBuilder strTc=new StringBuilder();
					for (String string : args) {
						strTc.append(string);
						strTc.append("<br>");
					}
					if(strTc!=null && !strTc.toString().isEmpty())
					orderForm.setTermConditions(strTc.toString());
				}
				//remarks length validation
				if(orderForm.getRemarks()!=null && !StringUtils.isEmpty(orderForm.getRemarks())&& orderForm.getRemarks().length()>=200){
					if (errors.get(CMSConstants.PURCHASEORDER_REMARK_LARGE)!=null && !errors.get(CMSConstants.PURCHASEORDER_REMARK_LARGE).hasNext()) {
						errors.add(CMSConstants.PURCHASEORDER_REMARK_LARGE, new ActionError(CMSConstants.PURCHASEORDER_REMARK_LARGE));
					}
				}
				// site delivery  length validation
				if(orderForm.getSiteDelivery()!=null && !StringUtils.isEmpty(orderForm.getSiteDelivery())&& orderForm.getSiteDelivery().length()>=200){
					if (errors.get(CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE)!=null && !errors.get(CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE).hasNext()) {
						errors.add(CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE, new ActionError(CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE));
					}
				}	
		
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_QUOTATION_PAGE);
			}
			// set vendor name to form
			if(orderForm.getVendorId()!=null && !StringUtils.isEmpty(orderForm.getVendorId())
					&& StringUtils.isNumeric(orderForm.getVendorId()) && orderForm.getVendorList()!=null){
				Iterator<VendorTO> vItr=orderForm.getVendorList().iterator();
				while (vItr.hasNext()) {
					VendorTO vendorTO = (VendorTO) vItr.next();
					if(vendorTO.getId()==Integer.parseInt(orderForm.getVendorId())){
						orderForm.setVendorName(vendorTO.getVendorName());
						break;
					}
					
				}
				
			}
			//set campus name and company  to form
			if(orderForm.getCampusId()!=null && !StringUtils.isEmpty(orderForm.getCampusId())
					&& StringUtils.isNumeric(orderForm.getCampusId()) && orderForm.getCampusList()!=null){
				Iterator<InvCampusTo> cItr=orderForm.getCampusList().iterator();
				while (cItr.hasNext()) {
					InvCampusTo campusTo = (InvCampusTo) cItr.next();
					if(campusTo.getId()==Integer.parseInt(orderForm.getCampusId())){
						orderForm.setCampusName(campusTo.getCampusName());
						break;
					}
					
				}
				
			}
			if(orderForm.getCompanyId()!=null && !StringUtils.isEmpty(orderForm.getCompanyId())
					&& StringUtils.isNumeric(orderForm.getCompanyId())){
				orderForm.setCompanyName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(orderForm.getCompanyId()),"InvCompany",true,"name"));
				}
			QuotationHandler.getInstance().getQuotationDetails(orderForm,false);
		} 
		catch (Exception e) {
			log.error("error in submitInitPurchaseOrder...",e);
				throw e;
			
		}
		
		log.info("exit submitInitPurchaseOrder page...");
		return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
	}
	
	/**
	 *  Purchase order final submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitFinalQuotation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitFinalPurchaseOrder page...");
			QuotationForm orderForm= (QuotationForm)form;
			//validation if needed
			ActionMessages errors=new ActionMessages();
		try {
			validateForm(orderForm,errors);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
			}
			boolean updated=QuotationHandler.getInstance().placeFinalQuotation(orderForm);
			if(!updated){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionError(CMSConstants.QUOTATION_SUBMIT_FAILURE);
				messages.add("messages", message);
				saveErrors(request, messages);
				return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
			}
		} 
		catch (Exception e) {
			log.error("error in submitFinalPurchaseOrder...",e);
				throw e;
			
		}
		orderForm.setTotalCost(null);
		orderForm.setServiceTax(null);
		orderForm.setServiceTaxCost(null);
		log.info("exit submitFinalPurchaseOrder page...");
		return mapping.findForward(CMSConstants.QUOTATION_CONFIRM_PAGE);
	}
	
	
	
	/**
	 * validating the form
	 * @param orderForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateForm(QuotationForm orderForm, ActionMessages errors) throws Exception {
		// Item List validation
		if(orderForm.getPurchaseItemList()==null || orderForm.getPurchaseItemList().isEmpty()){
			if (errors.get(CMSConstants.QUOTATION_ITEMS_EMPTY)!=null && !errors.get(CMSConstants.QUOTATION_ITEMS_EMPTY).hasNext()) {
				errors.add(CMSConstants.QUOTATION_ITEMS_EMPTY, new ActionError(CMSConstants.QUOTATION_ITEMS_EMPTY));
			}
		}
		// Quantity and Purchase Cost validation
		if (orderForm.getPurchaseItemList() != null	&& !orderForm.getPurchaseItemList().isEmpty()) {
			for (InvPurchaseOrderItemTO invPurchaseOrderItemTO : orderForm.getPurchaseItemList()) {
				//item required validation
				if(invPurchaseOrderItemTO.getSelectedItemId()==null || invPurchaseOrderItemTO.getSelectedItemId().isEmpty()){
					if (errors.get(CMSConstants.ITEM_REQUIRE) != null
							&& !errors.get(CMSConstants.ITEM_REQUIRE).hasNext()) {
						errors .add( CMSConstants.ITEM_REQUIRE, new ActionError( CMSConstants.ITEM_REQUIRE));
					}	
				}
				
				
				//validating quantity
				if(invPurchaseOrderItemTO.getQuantity()==null || invPurchaseOrderItemTO.getQuantity().isEmpty()){
					if (errors.get(CMSConstants.PURCHASEORDER_QTY_REQD) != null
							&& !errors.get(CMSConstants.PURCHASEORDER_QTY_REQD).hasNext()) {
						errors .add( CMSConstants.PURCHASEORDER_QTY_REQD, new ActionError( CMSConstants.PURCHASEORDER_QTY_REQD));
					}	
				}
				//validation for purchase cost
				if(invPurchaseOrderItemTO.getInvItem().getPurchaseCost()==null || invPurchaseOrderItemTO.getInvItem().getPurchaseCost().isEmpty()){
					if (errors.get(CMSConstants.PURCHASEORDER_UNIT_COST_REQD) != null
							&& !errors.get(CMSConstants.PURCHASEORDER_UNIT_COST_REQD).hasNext()) {
						errors .add( CMSConstants.PURCHASEORDER_UNIT_COST_REQD, new ActionError( CMSConstants.PURCHASEORDER_UNIT_COST_REQD));
					}	
				}
			}
		}
		/*// total cost validation-Commented the below code as TotalCost Field is disabled and user cant modify it.
		if(orderForm.getTotalCost()!=null && !StringUtils.isEmpty(orderForm.getTotalCost())&& !CommonUtil.isValidDecimal(orderForm.getTotalCost())){
			if (errors.get(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID)!=null && !errors.get(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID).hasNext()) {
				errors.add(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID, new ActionError(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID));
			}
		}*/
		
		// Additional cost validation
		if(orderForm.getAdditionalCost()!=null && !StringUtils.isEmpty(orderForm.getAdditionalCost())&& !CommonUtil.isValidDecimal(orderForm.getAdditionalCost())){
			if (errors.get(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID)!=null && !errors.get(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID).hasNext()) {
				errors.add(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID, new ActionError(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID));
			}
		}
		// Additional discount validation
		if(orderForm.getAddnDiscount()!=null && !StringUtils.isEmpty(orderForm.getAddnDiscount())&& !CommonUtil.isValidDecimal(orderForm.getAddnDiscount())){
			if (errors.get(CMSConstants.PURCHASEORDER_ADDNDISCOUNT_INVALID)!=null && !errors.get(CMSConstants.PURCHASEORDER_ADDNDISCOUNT_INVALID).hasNext()) {
				errors.add(CMSConstants.PURCHASEORDER_ADDNDISCOUNT_INVALID, new ActionError(CMSConstants.PURCHASEORDER_ADDNDISCOUNT_INVALID));
			}
		}
	}


	/**
	 *  adds Selected Item to list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItemList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter addItemList ...");
			QuotationForm orderForm= (QuotationForm)form;
		try {
			// add selected items to item list
			List<InvPurchaseOrderItemTO> pchItemList=orderForm.getPurchaseItemList();
			QuotationHandler.getInstance().updatePurchaseItemList(pchItemList,orderForm);
			int count=1;
			if(pchItemList!=null){
				Iterator<InvPurchaseOrderItemTO> itmItr=pchItemList.iterator();
				while (itmItr.hasNext()) {
					InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr.next();
					orderItmTO.setCountId(count);
					orderForm.setCountId(String.valueOf(count));
 					count++;
				}
			}
			orderForm.setSearchItem(null);
			orderForm.setPurchaseItemList(pchItemList);
		} 
		catch (Exception e) {
			log.error("error in addItemList...",e);
				throw e;
			
		}
		log.info("exit addItemList...");
		return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
	}
	
	/**
	 *  adds Selected Item to list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteItemFormList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter deleteItemFormList ...");
			QuotationForm orderForm= (QuotationForm)form;
		try {
			// add selected items to item list
			int count=1;
			Double totalPriceExcludingVat=0.0;
			Double totalVatAmt=0.0;
			Double totalDiscount=0.0;
			Double totalCost=0.0;
			Double serviceCost=0.0;
			Double addnDiscount=0.0;
			Double addnCost=0.0;
			List<InvPurchaseOrderItemTO> pchItemList=orderForm.getPurchaseItemList();
			if(pchItemList!=null && orderForm.getDeleteItemId()!=0){
				Iterator<InvPurchaseOrderItemTO> itmItr=pchItemList.iterator();
				while (itmItr.hasNext()) {
					InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr.next();
					if(orderItmTO.getCountId()!=0 && orderItmTO.getCountId()==orderForm.getDeleteItemId()){
						itmItr.remove();
					}
				}
			}
			
			
			if(pchItemList!=null){
				Iterator<InvPurchaseOrderItemTO> itmItr2=pchItemList.iterator();
				while (itmItr2.hasNext()) {
					InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr2.next();
					orderItmTO.setCountId(count);
					if(orderItmTO.getTotalCost()!=null && !orderItmTO.getTotalCost().trim().isEmpty()){
						totalPriceExcludingVat=totalPriceExcludingVat+Double.parseDouble(orderItmTO.getTotalCost());
					}
					if(orderItmTO.getVatCost()!=null && !orderItmTO.getVatCost().trim().isEmpty()){
						totalVatAmt=totalVatAmt+Double.parseDouble(orderItmTO.getVatCost());
					}
					if(orderItmTO.getDiscount()!=null && !orderItmTO.getDiscount().trim().isEmpty()){
						totalDiscount=totalDiscount+Double.parseDouble(orderItmTO.getDiscount());
					}
					orderForm.setCountId(String.valueOf(count));
				count++;
				}
			}
			orderForm.setTotalPriceExcludingVat(totalPriceExcludingVat.toString());
			orderForm.setTotalVatAmt(totalVatAmt.toString());
			if(orderForm.getAddnDiscount()!=null && !orderForm.getAddnDiscount().trim().isEmpty()){
				addnDiscount=Double.parseDouble(orderForm.getAddnDiscount());
				totalDiscount=totalDiscount+Double.parseDouble(orderForm.getAddnDiscount());
			}
			orderForm.setTotalDiscount(totalDiscount.toString());

			totalCost=totalCost+totalPriceExcludingVat+totalVatAmt-addnDiscount;
			
			if(orderForm.getAdditionalCost()!=null && !orderForm.getAdditionalCost().trim().isEmpty()){
				addnCost=Double.parseDouble(orderForm.getAdditionalCost());
				totalCost=totalCost+Double.parseDouble(orderForm.getAdditionalCost());
			}
				
			
			if(orderForm.getServiceTax()!=null && !orderForm.getServiceTax().trim().isEmpty()){
				serviceCost=CommonUtil.Round((((totalPriceExcludingVat+totalVatAmt+addnCost-addnDiscount)*Double.parseDouble(orderForm.getServiceTax()))/100),2);
			}
				
			orderForm.setServiceTaxCost(serviceCost.toString());
				totalCost=totalCost+serviceCost;
		
			orderForm.setTotalCost(totalCost.toString());
			
			orderForm.setSearchItem(null);
			orderForm.setPurchaseItemList(pchItemList);
		} 
		catch (Exception e) {
			log.error("error in deleteItemFormList...",e);
				throw e;
			
		}
		
		log.info("exit deleteItemFormList...");
		return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
	}
	
	/**
	 * CLEANS UP OLD FORM DATA FROM SESSION
	 * @param session
	 */
	private void cleanupFormFromSession(HttpSession session) {
		log.info("enter cleanupFormFromSession...");
		if(session.getAttribute("quotationForm")!=null)
			session.removeAttribute("quotationForm");
		log.info("exit cleanupFormFromSession...");
	}
	/**
	 * Method to set the required data to the form to display in itemMaster.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToMainPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered goTOMainPage Action");
		QuotationForm orderForm= (QuotationForm)form;
		HttpSession session=request.getSession(false);
		if(orderForm.getSuperAddNewType()!=null && !orderForm.getSuperAddNewType().isEmpty() && orderForm.getSuperAddNewType().equalsIgnoreCase("Vendor")){
			List<VendorTO> vendorList=VendorHandler.getInstance().getVendorDetails();
			orderForm.setVendorList(vendorList);
			if(session.getAttribute("newEntryName")!=null){
				orderForm.setVendorId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString(),"InvVendor"));
			}
			else orderForm.setVendorId(null);
		}
		else if (orderForm.getSuperAddNewType()!=null && !orderForm.getSuperAddNewType().isEmpty() 
				&& orderForm.getSuperAddNewType().equalsIgnoreCase("SingleFieldMasterEntry")){
			orderForm.setCampusList(InventoryLocationHandler.getInstance().getCampus());
			if(session.getAttribute("newEntryName")!=null){
			orderForm.setCampusId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString(),"InvCampus"));
			}
			else orderForm.setCampusId(null);
		}
		else if(orderForm.getSuperAddNewType()!=null && !orderForm.getSuperAddNewType().isEmpty() 
				&& orderForm.getSuperAddNewType().equalsIgnoreCase("InvLocation")){
			if(session.getAttribute("newEntryName")!=null && session.getAttribute("campusId")!=null){
				orderForm.setCampusId(session.getAttribute("campusId").toString());
			orderForm.setInvLocationMap(CommonAjaxHandler.getInstance().getInvLocation(session.getAttribute("campusId").toString()));
			orderForm.setLocationId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString(),"InvLocation_"+session.getAttribute("campusId").toString()));
			}
			else orderForm.setLocationId(null);	
		}
		else if(orderForm.getSuperAddNewType()!=null && !orderForm.getSuperAddNewType().isEmpty() 
				&& orderForm.getSuperAddNewType().equalsIgnoreCase("ItemMaster")){
			if(orderForm.getPurchaseItemList()!=null && !orderForm.getPurchaseItemList().isEmpty()){
				for(InvPurchaseOrderItemTO invPurchaseOrderItemTO:orderForm.getPurchaseItemList()){
					List<InvItemTO> itemList=QuotationHandler.getInstance().getItemList();
					invPurchaseOrderItemTO.setItemList(itemList);
						if(session.getAttribute("newEntryName")!=null && String.valueOf(invPurchaseOrderItemTO.getCountId()).equalsIgnoreCase(orderForm.getItemToCountNo())){
							invPurchaseOrderItemTO.setSelectedItemId(ItemHandler.getInstance().getNewEntryId(session.getAttribute("newEntryName").toString(),"InvItem"));
							InvItemTO invItemTO=invPurchaseOrderItemTO.getInvItem();
							for(InvItemTO invTo:itemList){
								if(String.valueOf(invTo.getId()).equalsIgnoreCase(invPurchaseOrderItemTO.getSelectedItemId())){
									invItemTO.setInvUomName(invTo.getInvUomName());
									invItemTO.setPurchaseCost(invTo.getPurchaseCost());
									invPurchaseOrderItemTO.setInvItem(invItemTO);
									break;
								}
							}
						}else orderForm.setSelectedItemId(null);
				}
			}
				
			orderForm.setSuperMainPage(null);
			session.removeAttribute("newEntryName");
			orderForm.setSuperAddNewType(null);
			orderForm.setDestinationMethod(null);
			log.info("Exit goTOMainPage Action");	
			return mapping.findForward(CMSConstants.QUOTATION_MAIN_PAGE);
		}
		orderForm.setSuperMainPage(null);
		session.removeAttribute("newEntryName");
		session.removeAttribute("campusId");
		orderForm.setSuperAddNewType(null);
		orderForm.setDestinationMethod(null);
		log.info("Exit goTOMainPage Action");		
		return mapping.findForward(CMSConstants.INIT_QUOTATION_PAGE);
	}

	/**
	 * Gets the data of the quotationNo entered in the form for edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInitQuotationDetailsEdit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		QuotationForm orderForm=(QuotationForm)form;
		ActionErrors errors=new ActionErrors();
		//Validation for Edit of Quotation 
		if(orderForm.getMode()!=null && !orderForm.getMode().isEmpty() && orderForm.getMode().equalsIgnoreCase("Edit")){
			if(orderForm.getQuotationNo()==null || orderForm.getQuotationNo().isEmpty())
			errors.add(CMSConstants.ERROR,new ActionError("inventory.quotation.no.required"));
		}
		if(errors.isEmpty()){
			QuotationHandler.getInstance().getQuotationDetails(orderForm,true);
			if(!orderForm.getDataExistsForEdit()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.quotation.details.does.not.exist"));
				addErrors(request, errors);
			}
		}
		return mapping.findForward(CMSConstants.INIT_QUOTATION_PAGE);
	}
}
