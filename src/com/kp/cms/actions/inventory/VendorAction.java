package com.kp.cms.actions.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.bo.admin.InvVendorItemCategory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.inventory.VendorForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.inventory.VendorHandler;
import com.kp.cms.to.inventory.VendorTO;

@SuppressWarnings("deprecation")
public class VendorAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(VendorAction.class);
	/**
	 * initilize
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 * @throws Exception
	 */
	public ActionForward initVendor(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		VendorForm vendorForm = (VendorForm)form;
		try{
			initFields(vendorForm);
			setUserId(request, vendorForm);  //setting user id to update last changed details
			setCountriesMapToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setVendorDetailsToRequest(request);
			if(request.getParameter("mainPageExists")==null){
				vendorForm.setMainPage(null);
				vendorForm.setSuperMainPage(null);
			}
		} catch (Exception e) {
			log.error("error in initVendor...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				vendorForm.setErrorMessage(msg);
				vendorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}
	
	
	/**
	 * save method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("inside addVendor Action");
		VendorForm vendorForm = (VendorForm) form;
		HttpSession session=request.getSession();
		vendorForm.setId(0);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = vendorForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			String[] str = request.getParameterValues("selectedCategory");
			if(str==null || str.length==0){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Category of Item Sold by Vendor "));
			}
			if(vendorForm.getPhone()!=null && !StringUtils.isEmpty(vendorForm.getPhone()))
			{
				if(phoneNoNotValid(vendorForm.getPhone())){
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
			}
			
			if(vendorForm.getMobileCountryCode()!=null && !StringUtils.isEmpty(vendorForm.getMobileCountryCode())){
				if(phoneNoNotValid(vendorForm.getMobileCountryCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Mobile Country Code"));
				}
			}
			
			if(vendorForm.getPhoneCountryCode()!=null && !StringUtils.isEmpty(vendorForm.getPhoneCountryCode())){
				if(phoneNoNotValid(vendorForm.getPhoneCountryCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Phone Country Code"));
				}
				
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Phone Country Code"));
			}
			
			if(vendorForm.getPhoneStateCode()!=null && !StringUtils.isEmpty(vendorForm.getPhoneStateCode())){
				if(phoneNoNotValid(vendorForm.getPhoneStateCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Phone Area Code"));
				}
				
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Phone Area Code"));
			}
			
			if(vendorForm.getMobile()!=null && !StringUtils.isEmpty(vendorForm.getMobile()))
			{
				if(phoneNoNotValid(vendorForm.getMobile())){
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(vendorForm.getMobile().length()!=10){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.mobileno.not.valid"));
				}
				
			}
			if(vendorForm.getFax()!=null && !StringUtils.isEmpty(vendorForm.getFax()) && !StringUtils.isNumeric(vendorForm.getFax()))
			{
				if (errors.get(CMSConstants.VENDOR_FAX_INVALID)!=null && !errors.get(CMSConstants.VENDOR_FAX_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.VENDOR_FAX_INVALID);
					errors.add(CMSConstants.VENDOR_FAX_INVALID, error);
				}
			}
			if(vendorForm.getRemarks()!= null && vendorForm.getRemarks().length() > 200){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.tc.remarks"));
			}
			if(vendorForm.getStateId()!= null && vendorForm.getStateId().equalsIgnoreCase("Other")){
				if(vendorForm.getStateOthers() == null || vendorForm.getStateOthers().trim().isEmpty()){
					ActionMessage error = new ActionMessage("admissionFormForm.education.state.required");
					errors.add("admissionFormForm.education.state.required", error);
				}
			}
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setCountriesMapToRequest(request);
				setItemCategoryMapToRequest(vendorForm);
				setVendorDetailsToRequest(request);
				return mapping.findForward(CMSConstants.VENDOR_ENTRY);
			}
			isAdded = VendorHandler.getInstance().addVendor(vendorForm, "add");
			setCountriesMapToRequest(request);
			setVendorDetailsToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.inventory.vendorForm.vendor.entry.exists", vendorForm.getVendorName()));
			saveErrors(request, errors);
			setCountriesMapToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setVendorDetailsToRequest(request);
			return mapping.findForward(CMSConstants.VENDOR_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.VENDOR_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setCountriesMapToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setVendorDetailsToRequest(request);
			return mapping.findForward(CMSConstants.VENDOR_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of addVendor page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				vendorForm.setErrorMessage(msg);
				vendorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			if(vendorForm.getSuperMainPage()!=null && !vendorForm.getSuperMainPage().isEmpty()){
				session.setAttribute("newEntryName",vendorForm.getVendorName());
			}
			ActionMessage message = new ActionMessage("knowledgepro.inventory.vendor.add.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(vendorForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.vendor.add.failure"));
			saveErrors(request, errors);
		}
		log.debug("exit addVendor Action");
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}	


	/**
	 * update method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		log.debug("inside addVendor Action");
		VendorForm vendorForm = (VendorForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = vendorForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			String[] str = request.getParameterValues("selectedCategory");
			if(str==null || str.length==0){
					errors.add(CMSConstants.ERROR,new ActionError("errors.required","Category of Item Sold by Vendor "));
			}
			if(vendorForm.getPhone()!=null && !StringUtils.isEmpty(vendorForm.getPhone()))
			{
				if(phoneNoNotValid(vendorForm.getPhone())){
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
			}
			if(vendorForm.getMobileCountryCode()!=null && !StringUtils.isEmpty(vendorForm.getMobileCountryCode())){
				if(phoneNoNotValid(vendorForm.getMobileCountryCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Mobile Country Code"));
				}
			}
			
			if(vendorForm.getPhoneCountryCode()!=null && !StringUtils.isEmpty(vendorForm.getPhoneCountryCode())){
				if(phoneNoNotValid(vendorForm.getPhoneCountryCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Phone Country Code"));
				}
				
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Phone Country Code"));
			}
			
			if(vendorForm.getPhoneStateCode()!=null && !StringUtils.isEmpty(vendorForm.getPhoneStateCode())){
				if(phoneNoNotValid(vendorForm.getPhoneStateCode())){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.fee.feePaymentScholarship", "Phone Area Code"));
				}
				
			}else{
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Phone Area Code"));
			}
			if(vendorForm.getMobile()!=null && !StringUtils.isEmpty(vendorForm.getMobile()))
			{
				if(phoneNoNotValid(vendorForm.getMobile())){
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(vendorForm.getMobile().length()!=10){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.inventory.mobileno.not.valid"));
				}
			}
			if(vendorForm.getFax()!=null && !StringUtils.isEmpty(vendorForm.getFax()) && !StringUtils.isNumeric(vendorForm.getFax()))
			{
				if (errors.get(CMSConstants.VENDOR_FAX_INVALID)!=null && !errors.get(CMSConstants.VENDOR_FAX_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.VENDOR_FAX_INVALID);
					errors.add(CMSConstants.VENDOR_FAX_INVALID, error);
				}
			}
			if(vendorForm.getRemarks()!= null && vendorForm.getRemarks().length() > 200){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.tc.remarks"));
			}
			if(vendorForm.getStateId()!= null && vendorForm.getStateId().equalsIgnoreCase("Other")){
				if(vendorForm.getStateOthers() == null || vendorForm.getStateOthers().trim().isEmpty()){
					ActionMessage error = new ActionMessage("admissionFormForm.education.state.required");
					errors.add("admissionFormForm.education.state.required", error);
				}
			}
			
			if(isCancelled(request)){
				setRequiredDataToForm(vendorForm, request);
				setCountriesMapToRequest(request);
				setstateMapToRequest(request,vendorForm);
				setItemCategoryMapToRequest(vendorForm);
				setVendorDetailsToRequest(request);				
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.VENDOR_ENTRY);
			}		
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				setCountriesMapToRequest(request);
				setItemCategoryMapToRequest(vendorForm);
				setVendorDetailsToRequest(request);
				setstateMapToRequest(request,vendorForm);
				request.setAttribute(CMSConstants.OPERATION, "edit");
				return mapping.findForward(CMSConstants.VENDOR_ENTRY);
			}
			isUpdated = VendorHandler.getInstance().addVendor(vendorForm, "edit");
			setCountriesMapToRequest(request);
			setVendorDetailsToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setstateMapToRequest(request,vendorForm);
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.inventory.vendorForm.vendor.entry.exists", vendorForm.getVendorName()));
			saveErrors(request, errors);
			setCountriesMapToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setVendorDetailsToRequest(request);
			setstateMapToRequest(request,vendorForm);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.VENDOR_ENTRY);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(CMSConstants.VENDOR_MASTER_EXIST_REACTIVATE));
			saveErrors(request, errors);
			setCountriesMapToRequest(request);
			setItemCategoryMapToRequest(vendorForm);
			setstateMapToRequest(request,vendorForm);
			setVendorDetailsToRequest(request);
			request.setAttribute(CMSConstants.OPERATION, "edit");
			return mapping.findForward(CMSConstants.VENDOR_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of updateVendor page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				vendorForm.setErrorMessage(msg);
				vendorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isUpdated) {
			HttpSession session=request.getSession();
			// success .
			if(vendorForm.getSuperMainPage()!=null && !vendorForm.getSuperMainPage().isEmpty()){
				session.setAttribute("newEntryName",vendorForm.getVendorName());
			}
			ActionMessage message = new ActionMessage("knowledgepro.inventory.vendor.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(vendorForm);
		}
		else
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.inventory.vendor.update.failure"));
			saveErrors(request, errors);
		}
		request.setAttribute(CMSConstants.OPERATION, "add");
		log.debug("exit addVendor Action");
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}	
	
	
	/**
	 * load details for edit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  This will load a particular record for edit.
	 * @return to mapping
	 * @throws Exception
	 */
	public ActionForward loadVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
										HttpServletResponse response) throws Exception {

		log.debug("inside loadVendor");
		VendorForm vendorForm = (VendorForm) form;
		try{
			initFields(vendorForm);
			setRequiredDataToForm(vendorForm, request);
			setCountriesMapToRequest(request);
			setVendorDetailsToRequest(request);
			setstateMapToRequest(request,vendorForm);
		}
		catch (Exception e) {
			log.error("Error occured in loadVendor of VendorAction Action");
		}
		request.setAttribute(CMSConstants.OPERATION, "edit");
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}
	
	
	
	
	/**
	 * 
	 * @param request
	 *            This method sets the countries map to Request useful in
	 *            populating in country selection.
	 */
	public void setCountriesMapToRequest(HttpServletRequest request) {
		log.debug("inside setCountriesMapToRequest");
		 Map<Integer,String> countriesMap = CountryHandler.getInstance().getCountriesMap();
		request.setAttribute("countriesMap", countriesMap);
	}
	/**
	 * 
	 * @param request
	 *            This method sets the categoryMap map to Request 
	 * @throws Exception 
	 */
	public void setItemCategoryMapToRequest(VendorForm vendorForm) throws Exception {
		log.debug("inside setItemCategoryMapToRequest");
		 Map<Integer,String> categoryMap = VendorHandler.getInstance().getCategoryMap();
		vendorForm.setCategoryMap(categoryMap);
	}
	/**
	 * setting vendorList to request
	 * @param request
	 * @throws Exception 
	 */
	public void setVendorDetailsToRequest(HttpServletRequest request) throws Exception{
		log.debug("start setVendorDetailsToRequest");
		List<VendorTO> vendorList = VendorHandler.getInstance().getVendorDetails();
		request.setAttribute("vendorList", vendorList);
		log.debug("exit setVendorDetailsToRequest");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    This will set all the required data for edit.
	 * @return to mapping
	 * @throws Exception
	 */

	public void setRequiredDataToForm(VendorForm vendorForm, HttpServletRequest request) throws Exception {
		log.debug("inside setRequiredDataToForm");
		vendorForm.setSelectedCategory(null);
		vendorForm.setStateOthers(null);
		
		int vendorId = Integer.parseInt(request.getParameter("id"));
		InvVendor invVendor = VendorHandler.getInstance().getVendor(vendorId);
		vendorForm.setAddressLine1(invVendor.getAddressLine1());
		if(invVendor.getAddressLine2()!= null){
			vendorForm.setAddressLine2(invVendor.getAddressLine2());
		}
		if(invVendor.getIsAuthorized()){
			vendorForm.setAuthorisedVendor("true");
		}
		else{
			vendorForm.setAuthorisedVendor("false");
		}
		if(invVendor.getBankAccountNo()!= null){
			vendorForm.setBankAcNo(invVendor.getBankAccountNo());
		}
		if(invVendor.getRemarks()!=null){
			vendorForm.setRemarks(invVendor.getRemarks());
		}
		if(invVendor.getBankBranch()!= null){
			vendorForm.setBankBranch(invVendor.getBankBranch());
		}
		if(invVendor.getCity()!= null){
			vendorForm.setCity(invVendor.getCity());
		}
		vendorForm.setContactPerson(invVendor.getContactPerson());
		if(invVendor.getCountry()!= null){
			vendorForm.setCountryId(Integer.toString(invVendor.getCountry().getId()));
			vendorForm.setCountryName(invVendor.getCountry().getName());
		}
		if(invVendor.getDeliverySchedule()!= null){
			vendorForm.setDeliverySchedule(invVendor.getDeliverySchedule());
		}
		if(invVendor.getEmail()!= null){
			vendorForm.setEmailId(invVendor.getEmail());
		}
		if(invVendor.getFaxNo()!= null){
			vendorForm.setFax(invVendor.getFaxNo());
		}
		if(invVendor.getMobile()!= null){
			vendorForm.setMobile(invVendor.getMobile());
		}
		if(invVendor.getMobile1()!=null){
			vendorForm.setMobileCountryCode(invVendor.getMobile1());
		}
		if(invVendor.getPhone1()!=null){
			vendorForm.setPhoneCountryCode(invVendor.getPhone1());
		}
		if(invVendor.getPhone2()!=null){
			vendorForm.setPhoneStateCode(invVendor.getPhone2());
		}
		if(invVendor.getPanNo()!= null){
			vendorForm.setPan(invVendor.getPanNo());
		}
		if(invVendor.getPaymentMode()!= null){
			vendorForm.setPaymentMode(invVendor.getPaymentMode());
		}
		vendorForm.setPhone(invVendor.getPhone());
		if(invVendor.getPin()!= null){
			vendorForm.setPin(invVendor.getPin());
		}
		if(invVendor.getState()!= null){
			vendorForm.setStateId(Integer.toString(invVendor.getState().getId()));
			vendorForm.setStateName(invVendor.getState().getName());
		}
		if(invVendor.getTanNo()!= null){
			vendorForm.setTan(invVendor.getTanNo());
		}
		if(invVendor.getVat()!= null){
			vendorForm.setVat(invVendor.getVat());
		}
		if(invVendor.getRemarks()!= null){
			vendorForm.setRemarks(invVendor.getRemarks());
		}
		vendorForm.setVendorName(invVendor.getName());
		if(invVendor.getZipCode()!= null){
			vendorForm.setZipCode(Integer.toString(invVendor.getZipCode()));
		}
		if(invVendor.getPaymentTerms()!= null){
			vendorForm.setPaymentTerms(invVendor.getPaymentTerms());
		}
		if(invVendor.getStateOthers()!= null){
				vendorForm.setStateId("Other");
				vendorForm.setStateOthers(invVendor.getStateOthers());
		}		
		int categoryArrayLen = invVendor.getInvVendorItemCategories().size(); 
		String categoryIdarray[] = new String[categoryArrayLen];
		Iterator<InvVendorItemCategory> itr = invVendor.getInvVendorItemCategories().iterator();
		List<String> categoryNameList = new ArrayList<String>();
		int count = 0;
		while (itr.hasNext()){
			InvVendorItemCategory invVendorItemCategory = itr.next();
			if(invVendorItemCategory.getIsActive()){
				categoryIdarray[count] = Integer.toString(invVendorItemCategory.getInvItemCategory().getId());
				categoryNameList.add(invVendorItemCategory.getInvItemCategory().getName());
			}
			count = count + 1;
		}
		vendorForm.setSelectedCategory(categoryIdarray);
		vendorForm.setInvVendor(invVendor);
		request.setAttribute("categoryList", categoryNameList);
		log.debug("leaving setRequiredDataToForm");
	}
	
	/**
	 * 
	 * @param request
	 * @param VendorForm
	 */
	public void setstateMapToRequest(HttpServletRequest request, VendorForm vendorForm) {
		if (vendorForm.getCountryId() != null
				&& (!vendorForm.getCountryId().isEmpty())) {
			Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(vendorForm.getCountryId()));
			request.setAttribute("stateMap", stateMap);
		}
		
	}

	/**
	*initialize method
	*/
	public void initFields(VendorForm vendorForm){
		vendorForm.setVendorName(null);
		vendorForm.setContactPerson(null);
		vendorForm.setAddressLine1(null);
		vendorForm.setAddressLine2(null);
		vendorForm.setCity(null);
		vendorForm.setZipCode(null);
		vendorForm.setEmailId(null);
		vendorForm.setTan(null);
		vendorForm.setPan(null);
		vendorForm.setPin(null);
		vendorForm.setVat(null);
		vendorForm.setBankAcNo(null);
		vendorForm.setBankBranch(null);
		vendorForm.setPaymentTerms(null);
		vendorForm.setDeliverySchedule(null);
		vendorForm.setPhone(null);
		vendorForm.setMobile(null);
		vendorForm.setAuthorisedVendor(null);
		vendorForm.setFax(null);
		vendorForm.setPaymentMode(null);
		vendorForm.setSelectedCategory(null);
		vendorForm.setCountryId(null);
		vendorForm.setStateId(null);
		vendorForm.setStateOthers(null);
		vendorForm.setRemarks(null);
		vendorForm.setPhoneCountryCode(null);
		vendorForm.setPhoneStateCode(null);
		vendorForm.setMobileCountryCode(null);
	}
	/**
	 * view method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
	public ActionForward initView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("Entering initView ");
		VendorForm vendorForm = (VendorForm) form;
		try {
			setRequiredDataToForm(vendorForm, request);
		} catch (Exception e) {
			log.error("error in vendor...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				vendorForm.setErrorMessage(msg);
				vendorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}

		log.debug("Leaving initView ");
		return mapping.findForward(CMSConstants.VIEW_VENDOR);
	}
	
			/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this will delete the vendor
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
		log.debug("inside deleteVendor");
		VendorForm vendorForm = (VendorForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (vendorForm.getId() != 0) {
				int id = vendorForm.getId();
				isDeleted = VendorHandler.getInstance().deleteVendor(id, false, vendorForm);
			}
		} catch (Exception e) {
			log.error("error in deleteVendor...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				vendorForm.setErrorMessage(msg);
				vendorForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
	
		setCountriesMapToRequest(request);
		setVendorDetailsToRequest(request);
		setItemCategoryMapToRequest(vendorForm);
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.inventory.vendor.entry.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(vendorForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.inventory.vendor.entry.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("leaving deleteVendor");
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}

/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response.. this method is to activate the vendor master
	 * @return
	 * @throws Exception
	 */
	public ActionForward activateVendor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			 HttpServletResponse response) throws Exception {
		log.debug("Entering activateVendor");
		VendorForm vendorForm = (VendorForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (vendorForm.getDuplId() != 0) {
				int id = vendorForm.getDuplId();  //setting id for activate
				isActivated = VendorHandler.getInstance().deleteVendor(id, true, vendorForm);
				//using for activate & delete. so for identifying activate true/false param sending to handler
			}
		} catch (Exception e) {
			errors.add("error", new ActionError(CMSConstants.VENDOR_MASTER_ACTIVATE_FAILURE));
			saveErrors(request, errors);
		}
		setCountriesMapToRequest(request);
		setVendorDetailsToRequest(request);
		setItemCategoryMapToRequest(vendorForm);
		if (isActivated) {
			HttpSession session=request.getSession();
			// success .
			if(vendorForm.getSuperMainPage()!=null && !vendorForm.getSuperMainPage().isEmpty()){
				String reactivatedName=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(vendorForm.getDuplId(), "InvVendor", true, "name");
				session.setAttribute("newEntryName",reactivatedName);
			}
			ActionMessage message = new ActionMessage(CMSConstants.VENDOR_MASTER_ACTIVATE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		log.debug("leaving activateVendor");
		return mapping.findForward(CMSConstants.VENDOR_ENTRY);
	}
	
	private boolean phoneNoNotValid(String name)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^0-9 \\s \t]+");
        Matcher matcher = pattern.matcher(name);
        result = matcher.find();
        return result;

	}
	
}
