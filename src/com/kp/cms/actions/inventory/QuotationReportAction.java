package com.kp.cms.actions.inventory;

import java.util.ArrayList;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.InvCounter;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.inventory.QuotationForm;
import com.kp.cms.handlers.inventory.QuotationReportHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.inventory.InvQuotationItemTO;
import com.kp.cms.to.inventory.InvQuotationTO;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.inventory.VendorTO;

@SuppressWarnings("deprecation")
public class QuotationReportAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(QuotationReportAction.class);

	/**
	 * setting vendor list to request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to
	 *         QUOTATION_REPORT_SEARCH
	 * @throws Exception
	 */
	public ActionForward initQuotationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
									HttpServletResponse response) throws Exception {
		log.debug("Entering initQuotationDetails ");
		QuotationForm quotationForm = (QuotationForm) form;
		quotationForm.setQuotationNo(null);
		try {
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);			
			HttpSession session = request.getSession(false);
			if(session.getAttribute("quotList")!=null){
				session.removeAttribute("quotList");
			}
			//setVendorDetailsToRequest(request);
		} catch (Exception e) {
			log.error("error initQuotationDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				quotationForm.setErrorMessage(msg);
				quotationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		log.debug("Leaving initQuotationDetails ");	
		return mapping.findForward(CMSConstants.QUOTATION_REPORT_SEARCH);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of quotations based on the id
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside getDetails");
		QuotationForm quotationForm = (QuotationForm) form;
		
		 ActionErrors errors = quotationForm.validate(mapping, request);
		try {
			
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				setVendorDetailsToRequest(request);
				return mapping.findForward(CMSConstants.QUOTATION_REPORT_SEARCH);
			}
			
			HttpSession session = request.getSession();
			List<ItemTO> quotList  =new ArrayList<ItemTO>();
			String prefix=QuotationReportHandler.getInstance().getQuotationPrefix(CMSConstants.QUOTATION_COUNTER);
			String maxorderNo=quotationForm.getQuotationNo();
			String tempMax="";
			if(prefix!=null && !prefix.isEmpty() && maxorderNo.startsWith(prefix) )
			tempMax=maxorderNo.substring((maxorderNo.substring(maxorderNo.lastIndexOf(prefix), prefix.length()).length()),maxorderNo.length());
	 		
			if(tempMax!=null && !tempMax.isEmpty() && StringUtils.isNumeric(tempMax))
			quotList  = QuotationReportHandler.getInstance().getQuotationDetails(tempMax);
	 		
			if (quotList.isEmpty()) {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setVendorDetailsToRequest(request);
				return mapping.findForward(CMSConstants.QUOTATION_REPORT_SEARCH);
			} 
			session.setAttribute("quotList",quotList);
		} catch (Exception e) {
			log.error("error in getDetails...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				quotationForm.setErrorMessage(msg);
				quotationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		
		log.debug("leaving getDetails");
		return mapping.findForward(CMSConstants.QUOTATION_REPORT_SUBMIT);
	}	
		
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	public void setVendorDetailsToRequest(HttpServletRequest request) throws Exception{
		log.info("start setVendorDetailsToRequest");
		List<VendorTO> vendorList = VendorHandler.getInstance().getVendorDetails();
		request.setAttribute("vendorList", vendorList);
		log.info("exit setVendorDetailsToRequest");
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of quotation details based on  id
	 * @throws Exception
	 */
	public ActionForward getQuotationDetailsByID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		log.debug("inside getQuotationDetailsByID");
		QuotationForm quotationForm = (QuotationForm) form;
		try {
			HttpSession session = request.getSession();
	 		List<InvQuotationItemTO> quotItemList  = QuotationReportHandler.getInstance().getQuotationItemsById(Integer.parseInt(quotationForm.getSelectedItemId()));
			session.setAttribute("quotItemList",quotItemList);
		} catch (Exception e) {
			log.error("error in getQuotationDetailsByID...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				quotationForm.setErrorMessage(msg);
				quotationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		
		log.debug("leaving getQuotationDetailsByID");
		return mapping.findForward(CMSConstants.QUOTATION_ITEM_VIEW);
	}	
	
}
