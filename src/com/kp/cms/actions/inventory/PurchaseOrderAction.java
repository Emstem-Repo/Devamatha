package com.kp.cms.actions.inventory;

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
import com.kp.cms.bo.admin.InvPurchaseOrder;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.PurchaseOrderForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.inventory.InventoryLocationHandler;
import com.kp.cms.handlers.inventory.ItemHandler;
import com.kp.cms.handlers.inventory.PurchaseOrderHandler;
import com.kp.cms.handlers.inventory.QuotationHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.inventory.InvCampusTo;
import com.kp.cms.to.inventory.InvItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.VendorTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * Class made for handling purchase order transactions
 * 
 */
@SuppressWarnings("deprecation")
public class PurchaseOrderAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PurchaseOrderAction.class);

	/**
	 * initialize Purchase order page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initPurchaseOrder page...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		setUserId(request, orderForm);
		try {
			cleanupPageData(orderForm);
			setRequiredDataToForm(orderForm);
		} catch (Exception e) {
			log.error("error in initPurchaseOrder...", e);
			throw e;

		}
		log.info("exit initPurchaseOrder page...");
		return mapping.findForward(CMSConstants.INIT_PURCHASE_ORDER_PAGE);
	}

	/**
	 * @param orderForm
	 * @throws Exception
	 */
	private void setRequiredDataToForm(PurchaseOrderForm orderForm) throws Exception {
	//	PurchaseOrderHandler.getInstance().setPurchaseOrderNo(orderForm);
		List<VendorTO> vendorList = VendorHandler.getInstance()
		.getVendorDetails();
		orderForm.setVendorList(vendorList);
		orderForm.setCampusList(InventoryLocationHandler.getInstance().getCampus());
		orderForm.setInvCompanyList(QuotationHandler.getInstance().getCompany());
		String savedTcDesc=PurchaseOrderHandler.getInstance().getTermsAndConditions();
		if(savedTcDesc!=null){
		String[] args=savedTcDesc.split("<br>");
		StringBuilder strTc=new StringBuilder();
		for (String string : args) {
			strTc.append(string);
			strTc.append("\r\n");
		}
		if(strTc!=null && !strTc.toString().isEmpty())
			orderForm.setTermConditions(strTc.toString());
		}
	}

	/**
	 * @param orderForm
	 */
	private void cleanupPageData(PurchaseOrderForm orderForm) {
		log.info("enter cleanupPageData...");
		orderForm.setPurchaseOrderNo(null);
		orderForm.setPurchaseDate(null);
		orderForm.setVendorId(null);
		orderForm.setRemarks(null);
		orderForm.setSiteDelivery(null);
		orderForm.setPurchaseItemList(null);
		orderForm.setSelectedItemId(null);
		orderForm.setSelectedItemQty(null);
		orderForm.setVat(null);
		orderForm.setVatCost(null);
		orderForm.setServiceTax(null);
		orderForm.setServiceTaxCost(null);
		orderForm.setTotalTaxCost(null);
		orderForm.setPrintReceipt(null);
		orderForm.setCampusId(null);
		orderForm.setDeliverySchedule(null);
		orderForm.setQuotationNo(null);
		orderForm.setCompanyId(null);
		orderForm.setMode(null);
		orderForm.setDataExistsForEdit(false);
		orderForm.setTotalCost(null);
		orderForm.setTotalPriceExcludingVat(null);
		orderForm.setTotalDiscount(null);
		orderForm.setTotalVatAmt(null);
		orderForm.setAdditionalCost(null);
		orderForm.setAddnDiscount(null);
		log.info("exit cleanupPageData...");

	}

	/**
	 * Purchase order first page submit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitInitPurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitInitPurchaseOrder page...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		// validation if needed
		ActionMessages errors = orderForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			//Validation for Edit of Quotation 
			if(orderForm.getMode()!=null && !orderForm.getMode().isEmpty() && orderForm.getMode().equalsIgnoreCase("Edit")){
				if(orderForm.getPurchaseOrderNo()==null || orderForm.getPurchaseOrderNo().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.po.no.required"));
			}
			// purchase date validation
			if (orderForm.getPurchaseDate() != null
					&& !StringUtils.isEmpty(orderForm.getPurchaseDate())
					&& !CommonUtil.isValidDate(orderForm.getPurchaseDate())) {
				if (errors.get(CMSConstants.PURCHASEORDERDATE_INVALID) != null
						&& !errors.get(CMSConstants.PURCHASEORDERDATE_INVALID)
								.hasNext()) {
					errors.add(CMSConstants.PURCHASEORDERDATE_INVALID,
							new ActionError(
									CMSConstants.PURCHASEORDERDATE_INVALID));
				}
			}
			// terms condition length validation
			if (orderForm.getTermConditions() != null
					&& !StringUtils.isEmpty(orderForm.getTermConditions())
					&& orderForm.getTermConditions().length() >= 2000) {
				if (errors.get(CMSConstants.PURCHASEORDER_TC_LARGE) != null
						&& !errors.get(CMSConstants.PURCHASEORDER_TC_LARGE)
								.hasNext()) {
					errors.add(CMSConstants.PURCHASEORDER_TC_LARGE,new ActionError(CMSConstants.PURCHASEORDER_TC_LARGE));
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
			// site delivery length validation
			if (orderForm.getRemarks() != null
					&& !StringUtils.isEmpty(orderForm.getRemarks())
					&& orderForm.getRemarks().length() >= 200) {
				if (errors.get(CMSConstants.PURCHASEORDER_REMARK_LARGE) != null
						&& !errors.get(CMSConstants.PURCHASEORDER_REMARK_LARGE)
								.hasNext()) {
					errors.add(CMSConstants.PURCHASEORDER_REMARK_LARGE,
							new ActionError(
									CMSConstants.PURCHASEORDER_REMARK_LARGE));
				}
			}
			// remarks length validation
			if (orderForm.getSiteDelivery() != null
					&& !StringUtils.isEmpty(orderForm.getSiteDelivery())
					&& orderForm.getSiteDelivery().length() >= 200) {
				if (errors.get(CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE) != null
						&& !errors.get(
								CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE)
								.hasNext()) {
					errors
							.add(
									CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE,
									new ActionError(
											CMSConstants.PURCHASEORDER_SITEDELIVERY_LARGE));
				}
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.INIT_PURCHASE_ORDER_PAGE);
			}

			// set vendor name to form
			if (orderForm.getVendorId() != null
					&& !StringUtils.isEmpty(orderForm.getVendorId())
					&& StringUtils.isNumeric(orderForm.getVendorId())
					&& orderForm.getVendorList() != null) {
				Iterator<VendorTO> vItr = orderForm.getVendorList().iterator();
				while (vItr.hasNext()) {
					VendorTO vendorTO = (VendorTO) vItr.next();
					if (vendorTO.getId() == Integer.parseInt(orderForm
							.getVendorId())) {
						orderForm.setVendorName(vendorTO.getVendorName());
						orderForm.setVendorAddress1(vendorTO
								.getVendorAddressLine1());
						orderForm.setVendorAddress2(vendorTO
								.getVendorAddressLine2());
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
					}
					
				}
				
			}
			if(orderForm.getCompanyId()!=null && !StringUtils.isEmpty(orderForm.getCompanyId())
					&& StringUtils.isNumeric(orderForm.getCompanyId())){
				orderForm.setCompanyName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(orderForm.getCompanyId()),"InvCompany",true,"name"));
				}
			
			PurchaseOrderHandler.getInstance().getPurchaseOrderDetails(orderForm, false);
			
		} 
		catch (BusinessException e) {
			log.error("error in submitInitPurchaseOrder -BusinessException...", e);
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PURCHASE_ORDER_QUOTAION_NOT_EXIST));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			log.error("error in submitInitPurchaseOrder...", e);
			throw e;

		}
		log.info("exit submitInitPurchaseOrder page...");
		return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
	}

	/**
	 * Purchase order final submit
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitFinalPurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitFinalPurchaseOrder page...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		// validation if needed
		ActionErrors errors = new ActionErrors();
		try {
			validatePurchaseOrderMain(orderForm,errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
			}

			boolean updated = PurchaseOrderHandler.getInstance().placeFinalPurchaseOrder(orderForm);
			if (!updated) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionError(
						CMSConstants.PURCHASEORDER_SUBMIT_FAILURE);
				messages.add("messages", message);
				saveErrors(request, messages);
				return mapping
						.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
			}
		} catch (Exception e) {
			log.error("error in submitFinalPurchaseOrder...", e);
			throw e;
		}
		orderForm.setTotalTaxCost(null);
		orderForm.setTotalCost(null);
		orderForm.setVat(null);
		orderForm.setVatCost(null);
		orderForm.setServiceTax(null);
		orderForm.setServiceTaxCost(null);
		orderForm.setPrintReceipt("false");
		log.info("exit submitFinalPurchaseOrder page...");
		return mapping.findForward(CMSConstants.PURCHASE_ORDER_CONFIRM_PAGE);
	}

	/**
	 * adds Selected Item to list
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addItemList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("enter addItemList ...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		try {
			// add selected items to item list
			List<InvPurchaseOrderItemTO> pchItemList = orderForm.getPurchaseItemList();
			PurchaseOrderHandler.getInstance().updatePurchaseItemList(pchItemList, orderForm);
			//  set count id
			int count = 1;
			if (pchItemList != null) {
				Iterator<InvPurchaseOrderItemTO> itmItr = pchItemList
						.iterator();
				while (itmItr.hasNext()) {
					InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr
							.next();
					orderItmTO.setCountId(count);
					orderForm.setCountId(String.valueOf(count));
					count++;
				}
			}
			orderForm.setSearchItem(null);
			orderForm.setPurchaseItemList(pchItemList);
		} catch (Exception e) {
			log.error("error in addItemList...", e);
			throw e;

		}

		log.info("exit addItemList...");
		return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
	}

	/**
	 * adds Selected Item to list
	 * 
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
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		try {
			// delete selected items to item list
			Double totalPriceExcludingVat=0.0;
			Double totalVatAmt=0.0;
			Double totalDiscount=0.0;
			Double totalCost=0.0;
			Double serviceCost=0.0;
			Double addnDiscount=0.0;
			Double addnCost=0.0;
			List<InvPurchaseOrderItemTO> pchItemList = orderForm .getPurchaseItemList();
			if (pchItemList != null && orderForm.getDeleteItemId() != 0) {
				Iterator<InvPurchaseOrderItemTO> itmItr = pchItemList
						.iterator();
				while (itmItr.hasNext()) {
					InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr .next();
					if (orderItmTO.getCountId() != 0 && orderItmTO.getCountId() == orderForm.getDeleteItemId()) {
						itmItr.remove();
					}
				}
			}

		int count=1;
		if (pchItemList != null) {
				Iterator<InvPurchaseOrderItemTO> itmItr2 = pchItemList .iterator();
			while (itmItr2.hasNext()) {
				InvPurchaseOrderItemTO orderItmTO = (InvPurchaseOrderItemTO) itmItr2.next();
				orderItmTO.setCountId(count);
				orderForm.setCountId(String.valueOf(count));
				if(orderItmTO.getTotalCost()!=null && !orderItmTO.getTotalCost().trim().isEmpty()){
					totalPriceExcludingVat=totalPriceExcludingVat+Double.parseDouble(orderItmTO.getTotalCost());
				}
				if(orderItmTO.getVatCost()!=null && !orderItmTO.getVatCost().trim().isEmpty()){
					totalVatAmt=totalVatAmt+Double.parseDouble(orderItmTO.getVatCost());
				}
				if(orderItmTO.getDiscount()!=null && !orderItmTO.getDiscount().trim().isEmpty()){
					totalDiscount=totalDiscount+Double.parseDouble(orderItmTO.getDiscount());
				}
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
		} catch (Exception e) {
			log.error("error in deleteItemFormList...", e);
			throw e;

		}

		log.info("exit deleteItemFormList...");
		return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
	}

	/**
	 * CLEANS UP OLD FORM DATA FROM SESSION
	 * 
	 * @param session
	 */
	private void cleanupFormFromSession(HttpSession session) {
		log.info("enter cleanupFormFromSession...");
		if (session.getAttribute("purchaseOrderForm") != null)
			session.removeAttribute("purchaseOrderForm");
		log.info("exit cleanupFormFromSession...");
	}

	public ActionForward printPurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter printPurchaseOrder ...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		ActionErrors errors = new ActionErrors();
		try {
				validatePurchaseOrderMain(orderForm,errors);
				if (errors != null && !errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
				}
				PurchaseOrderHandler.getInstance().placeFinalPurchaseOrder(orderForm);
				orderForm.setDate(CommonUtil.getStringDate(new Date()));
				
				double taxAmount=0.0;
				if(orderForm.getTotalVatAmt()!=null && !orderForm.getTotalVatAmt().isEmpty() && orderForm.getServiceTaxCost()!=null && !orderForm.getServiceTaxCost().isEmpty()){
					 taxAmount = new Double(orderForm.getTotalVatAmt())+ new Double(orderForm.getServiceTaxCost());
				}
	
				orderForm.setTotalTaxCost(Double.toString(taxAmount));
				orderForm.setPrintReceipt("true");
				return mapping.findForward(CMSConstants.PURCHASE_ORDER_CONFIRM_PAGE);
		} catch (Exception e) {
			log.error("error in printPurchaseOrder...", e);
			throw e;
		}

	}

	/**
	 * validating the form
	 * @param orderForm
	 * @param errors
	 */
	private void validatePurchaseOrderMain(PurchaseOrderForm orderForm,ActionErrors errors) {
		// Item List validation
		if (orderForm.getPurchaseItemList() == null || orderForm.getPurchaseItemList().isEmpty()) {
			if (errors.get(CMSConstants.QUOTATION_ITEMS_EMPTY) != null && !errors.get(CMSConstants.QUOTATION_ITEMS_EMPTY) .hasNext()) {
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
		
		// total cost validation-Commented the below code as TotalCost Field is disabled and user can't modify it.
		/*if (orderForm.getTotalCost() != null && !StringUtils.isEmpty(orderForm.getTotalCost()) && !CommonUtil.isValidDecimal(orderForm.getTotalCost())) {
			if (errors.get(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID) != null && !errors.get(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID).hasNext()){
				errors .add( CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID, new ActionError(CMSConstants.PURCHASEORDER_TOTAL_COST_INVALID));
			}
		}
*/
		// Additional cost validation
		if (orderForm.getAdditionalCost() != null && !StringUtils.isEmpty(orderForm.getAdditionalCost()) && !CommonUtil .isValidDecimal(orderForm.getAdditionalCost())) {
			if (errors.get(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID) != null && !errors.get(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID).hasNext()){
				errors .add(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID, new ActionError(CMSConstants.PURCHASEORDER_ADDNCOST_INVALID));
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
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printpurchaseOrderAfterSave(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter printPurchaseOrder ...");
		//PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		return mapping.findForward(CMSConstants.PRINT_PURCHASE_ORDER_DETAILS);
	}
	
	public ActionForward initRePrintpurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter printPurchaseOrder ...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		orderForm.setPrint(false);
		orderForm.setOrderNo(null);
		
		return mapping.findForward(CMSConstants.INIT_REPRINT_PURCHASE_ORDER);
	}
	
	public ActionForward rePrintpurchaseOrder(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter rePrintpurchaseOrder ...");
		PurchaseOrderForm orderForm = (PurchaseOrderForm) form;
		 ActionErrors errors = orderForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				InvPurchaseOrder orderBO =PurchaseOrderHandler.getInstance().getPrintData(orderForm);
				orderForm.setOrderNo(null);
				if(orderBO==null){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
				}else{
					PurchaseOrderHandler.getInstance().setBoDataToForm(orderBO,orderForm);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				orderForm.setErrorMessage(msg);
				orderForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			saveErrors(request, errors);
			log.info("Exit rePrintpurchaseOrder errors not empty ");
		}
		log.info("Exit rePrintpurchaseOrder ");
		return mapping.findForward(CMSConstants.INIT_REPRINT_PURCHASE_ORDER);
	}
	
	/**
	 * Method to set the required data to the form to display in itemMaster.jsp
	 * after Add New Master
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
		log.info("Entered into goTOMainPage- PurchaseOrderAction");
		PurchaseOrderForm orderForm= (PurchaseOrderForm)form;
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
			return mapping.findForward(CMSConstants.PURCHASE_ORDER_MAIN_PAGE);
		}
		if(orderForm.getCampusId()!=null && !orderForm.getCampusId().isEmpty()){
			orderForm.setInvLocationMap(CommonAjaxHandler.getInstance().getInvLocation(orderForm.getCampusId()));
		}
		orderForm.setSuperMainPage(null);
		session.removeAttribute("newEntryName");
		session.removeAttribute("campusId");
		orderForm.setSuperAddNewType(null);
		orderForm.setDestinationMethod(null);
		log.info("Exit goTOMainPage- PurchaseOrderAction");		
		return mapping.findForward(CMSConstants.INIT_PURCHASE_ORDER_PAGE);
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
	public ActionForward getInitPODetailsEdit(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		PurchaseOrderForm orderForm=(PurchaseOrderForm)form;
		ActionErrors errors=new ActionErrors();
		orderForm.setDataExistsForEdit(false);
		try {
			//Validation for Edit of PO 
			if(orderForm.getMode()!=null && !orderForm.getMode().isEmpty() && orderForm.getMode().equalsIgnoreCase("Edit")){
				if(orderForm.getPurchaseOrderNo()==null || orderForm.getPurchaseOrderNo().isEmpty())
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.po.no.required"));
			}
			else if (orderForm.getMode()!=null && !orderForm.getMode().isEmpty() && orderForm.getMode().equalsIgnoreCase("Add")){
				if(orderForm.getQuotationNo()==null || orderForm.getQuotationNo().isEmpty())
					errors.add(CMSConstants.ERROR,new ActionError("inventory.quotation.no.required"));
			}
			if(errors.isEmpty()){
				PurchaseOrderHandler.getInstance().getPurchaseOrderDetails(orderForm,true);
				if(orderForm.getMode().equalsIgnoreCase("Edit") && !orderForm.getDataExistsForEdit()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.po.details.does.not.exist"));
					addErrors(request, errors);
				}
			} else {
				addErrors(request, errors);
				saveErrors(request, errors);
				log.info("Exit getInitPODetailsEdit errors not empty ");
			}
		} 	
		catch (BusinessException e) {
			log.error("error in getInitPODetailsEdit -BusinessException...", e);
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PURCHASE_ORDER_QUOTAION_NOT_EXIST));
			saveErrors(request, errors);
		}
		catch (Exception e) {
			log.error("error in getInitPODetailsEdit...", e);
			throw e;

		}
		
		return mapping.findForward(CMSConstants.INIT_PURCHASE_ORDER_PAGE);
	}
}
