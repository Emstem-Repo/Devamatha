package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.inventory.VendorWisePOForm;
import com.kp.cms.handlers.inventory.QuotationReportHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.handlers.inventory.VendorWisePOHandler;
import com.kp.cms.to.inventory.InvPurchaseOrderItemTO;
import com.kp.cms.to.inventory.InvPurchaseOrderTO;
import com.kp.cms.to.inventory.InvPurchaseReturnTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;

public class VendorWisePOAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(VendorWisePOAction.class);
	public static final String POLIST = "poList";
	public static final String PORETURNLIST = "poReturnList";
	/**
	 * Initializes vendorwise PO report
	 * with all the active vendors
	 */
	public ActionForward initVendorWisePO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("start of initVendorWisePO of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		try {
			setVendorDetailsToForm(poForm);
			poForm.clear();
			HttpSession session = request.getSession(false);
			if(session.getAttribute(POLIST)!=null){
				session.removeAttribute(POLIST);
			}
			if (session.getAttribute(PORETURNLIST) != null) {
				session.removeAttribute(PORETURNLIST);
			}
		} catch (Exception e) {
			log.error("Error occured in initVendorWisePO of VendorWisePOAction", e);
			String msg = super.handleApplicationException(e);
			poForm.setErrorMessage(msg);
			poForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of initVendorWisePO of VendorWisePOAction");
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO);
	}

	/**
	 * setting vendorList to Form
	 * @param request
	 * @throws Exception 
	 */
	public void setVendorDetailsToForm(VendorWisePOForm poForm) throws Exception{
		log.info("start setVendorDetailsToForm of VendorWisePOAction");
		List<VendorTO> vendorList = VendorHandler.getInstance().getVendorDetails();
		poForm.setVendorList(vendorList);
		log.info("exit setVendorDetailsToForm of VendorWisePOAction");
	}
	
	/**
	 * Used to get vendor details based on the vendor
	 */
	
	public ActionForward submitVendorWisePO(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("start of submitVendorWise of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(POLIST)==null){
			 ActionErrors errors = poForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					List<InvPurchaseOrderTO> poList = VendorWisePOHandler.getInstance().
					getPurchaseOrdersByVendor(Integer.valueOf(poForm.getVendorId().trim()));				
					session.setAttribute(POLIST, poList);
				}
				else{
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO);
				}
			}
			catch (Exception e) {
				log.error("Error occured in submitVendorWise of VendorWisePOAction", e);
				String msg = super.handleApplicationException(e);
				poForm.setErrorMessage(msg);
				poForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}			
		}
		log.info("End of submitVendorWise of VendorWisePOAction");		
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_RESULT);		
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return gets the purchase order details by purchase order Id
	 * @throws Exception
	 */
	public ActionForward getPurchaseOrderDetailsByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("start of getPurchaseOrderDetailsByID of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		try {
			List<InvPurchaseOrderItemTO> poItemlist = VendorWisePOHandler
					.getInstance().getPurchaseOrderDetailsByID(
							Integer.valueOf(poForm.getPurchaseOrderId()));
			poForm.setPoItemList(poItemlist);
		} catch (Exception e) {
			log.error("Error occured in getPurchaseOrderDetailsByID of VendorWisePOAction", e);
			String msg = super.handleApplicationException(e);
			poForm.setErrorMessage(msg);
			poForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("start of getPurchaseDetailsByID of VendorWisePOAction");
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_DETAILS);
	}
	
	/**
	 * Intializes venderwise PO Return report
	 * with all the purchase order bill nos
	 */
	public ActionForward initVendorWisePOReturns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("start of initVendorWisePOReturns of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		try {
			setAllPurchaseOrderNOToForm(poForm);
			HttpSession session = request.getSession(false);
			if (session.getAttribute(PORETURNLIST) != null) {
				session.removeAttribute(PORETURNLIST);
			}
			if (session.getAttribute(POLIST) != null) {
				session.removeAttribute(POLIST);
			}
			poForm.clear();
		} catch (Exception e) {
			log.error("Error occured in initVendorWisePOReturns of VendorWisePOAction", e);
			String msg = super.handleApplicationException(e);
			poForm.setErrorMessage(msg);
			poForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("End of initVendorWisePOReturns of VendorWisePOAction");
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_RETURNS);
	}
	/**
	 * Used to get vendor wise Returns based on the purchase order no.
	 */
	public ActionForward submitVendorWisePOReturns(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("start of submitVendorWisePOReturns of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute(PORETURNLIST) == null){
			 ActionErrors errors = poForm.validate(mapping, request);
			try {
				if (errors.isEmpty()) {
					List<InvPurchaseReturnTO> poReturnList=new ArrayList<InvPurchaseReturnTO>();
					String prefix=QuotationReportHandler.getInstance().getQuotationPrefix(CMSConstants.PURCHASE_ORDER_COUNTER);
					String maxorderNo=poForm.getPurchaseOrderNo();
					String tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
			 		
					if(tempMax!=null && !tempMax.isEmpty() && StringUtils.isNumeric(tempMax))
					poReturnList = VendorWisePOHandler.getInstance().getPurchaseReturnsByPurchaseOrderNo(tempMax);
					
					session.setAttribute(PORETURNLIST, poReturnList);					
				}
				else{
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_RETURNS);
				}
			}catch (Exception e) {
				log.error("Error occured in submitVendorWisePOReturns of VendorWisePOAction", e);
				String msg = super.handleApplicationException(e);
				poForm.setErrorMessage(msg);
				poForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}			
		}
		log.info("End of submitVendorWisePOReturns of VendorWisePOAction");		
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_RETURNS_RESULT);
	}
	/**
	 * setting vendorList to Form
	 * @param request
	 * @throws Exception 
	 */
	public void setAllPurchaseOrderNOToForm(VendorWisePOForm poForm) throws Exception{
		log.info("start setAllPurchaseOrderNOToForm of VendorWisePOAction");
		List<InvPurchaseOrderTO> orderNoList = VendorWisePOHandler.getInstance().getAllPurchaseOrderNos();
		poForm.setOrderNoList(orderNoList);
		log.info("exit setAllPurchaseOrderNOToForm of VendorWisePOAction");
	}
	
	/**
	 * Gets purchase return item details by purchase return Id
	 */
	public ActionForward getPurchaseReturnDetailsByID(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Start of getPurchaseReturnDetailsByID of VendorWisePOAction");
		VendorWisePOForm poForm = (VendorWisePOForm)form;
		try {
			List<ItemTO> itemList = VendorWisePOHandler.getInstance().
			getPurchaseReturnDetailsByID(poForm.getPuchaseReturnId());
			poForm.setItemList(itemList);			
		} catch (Exception e) {
			log.error("Error occured in getPurchaseReturnDetailsByID of VendorWisePOAction", e);
			String msg = super.handleApplicationException(e);
			poForm.setErrorMessage(msg);
			poForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("End of getPurchaseReturnDetailsByID of VendorWisePOAction");
		return mapping.findForward(CMSConstants.INVENTORY_VENDERWISE_PO_RETURNS_DETAILS);
	}
}
