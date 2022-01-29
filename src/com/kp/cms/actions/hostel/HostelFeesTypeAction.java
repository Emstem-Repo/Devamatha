package com.kp.cms.actions.hostel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ComplaintsForm;
import com.kp.cms.forms.hostel.HostelFeesTypeForm;
import com.kp.cms.handlers.hostel.HostelFeesTypeHandler;
import com.kp.cms.to.hostel.HostelFeesTypeTo;

/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class HostelFeesTypeAction extends BaseDispatchAction {

	private static final Logger log = Logger.getLogger(HostelFeesTypeAction.class);

	/**
	 * Used to get HostelFeesType Details and bind to Form i.e binding	
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward initHostelFeesTypeEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of initHostelFeesTypeEntry in HostelFeesTypeAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		try {
			assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of initHostelFeesTypeEntry in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to add Hostel Fees Type Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addHostelFeesTypeEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of addFeeFinancialYearEntry in FeeFinancialYearEntryAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = hostelFeesTypeForm.validate(mapping, request);
		validateFormSpecialCharacter(hostelFeesTypeForm, errors, request);
		boolean isAdded = false;
		try {
			if (errors.isEmpty()) {				
				setUserId(request, hostelFeesTypeForm);
				HlFeeType hlFeeType=HostelFeesTypeHandler.getInstance().getHostelFeesType(hostelFeesTypeForm.getHostelFeesType());
				if(hlFeeType!=null){
					if(hlFeeType.getIsActive()){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_EXISTS));
						hostelFeesTypeForm.clear();
					}else if(!hlFeeType.getIsActive()){
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_REACTIVATE));
					}
					assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
					saveErrors(request, errors);
				}else {
					isAdded = HostelFeesTypeHandler.getInstance().addHostelFeesType(hostelFeesTypeForm,errors);
					if (isAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_FEES_TYPE_ADD_SUCCESS));
						saveMessages(request, messages);
						assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						hostelFeesTypeForm.clear();
					} else {
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_ADD_FAIL));
						assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						saveErrors(request, errors);
					}
				}
			} else {
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
				saveErrors(request, errors);
			}
		}
		catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);		
		}
		log.info("End of addHostelFeesTypeEntry in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to edit Hostel Fees Type
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward editHostelFeesTypeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editHostelFeesTypeDetails in HostelFeesTypeAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		try {
			int feeId = hostelFeesTypeForm.getId();
			HostelFeesTypeTo hostelFeesTypeTo = HostelFeesTypeHandler.getInstance().getHostelFeesTypeDetailsWithId(feeId);
			// Keeping the year in session scope
			if (hostelFeesTypeTo != null) {
				hostelFeesTypeForm.setHostelFeesType(hostelFeesTypeTo.getHostelFeesType());				
			}
			HttpSession session = request.getSession(true);
			session.setAttribute("prevHostelFeeType", hostelFeesTypeTo.getHostelFeesType());
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
			assignHostelFeesTypeToListToForm(hostelFeesTypeForm);			
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
		.info("End of editHostelFeesTypeDetails in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to update HlFeeType Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward updateHostelFeesTypeEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updateHostelFeesTypeEntry in HostelFeesTypeAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		 ActionErrors errors = hostelFeesTypeForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		validateFormSpecialCharacter(hostelFeesTypeForm, errors, request);
		try {
			if (isCancelled(request)) {
				int feeId = hostelFeesTypeForm.getId();
				HostelFeesTypeTo hostelFeesTypeTo = HostelFeesTypeHandler.getInstance().getHostelFeesTypeDetailsWithId(feeId);
				if (hostelFeesTypeTo != null) {					
					hostelFeesTypeForm.setHostelFeesType(hostelFeesTypeTo.getHostelFeesType());							
				}
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
			} else if (errors.isEmpty()) {
				setUserId(request, hostelFeesTypeForm);
				HttpSession session = request.getSession(false);
				String prevHostelFeeType = String.valueOf(session.getAttribute("prevHostelFeeType"));
				String currentHostelFeeType = hostelFeesTypeForm.getHostelFeesType();
				boolean isUpdated;
				if (!prevHostelFeeType.equals(currentHostelFeeType)) {
					HlFeeType hlFeeType = HostelFeesTypeHandler.getInstance().getHostelFeesType(currentHostelFeeType);
					if (hlFeeType != null) {
						if (hlFeeType.getIsActive()) {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
							assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						}
						if (!hlFeeType.getIsActive()) {
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_FEES_TYPE_REACTIVATE));
							saveErrors(request, errors);
							assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						}
					} else {
						isUpdated = HostelFeesTypeHandler.getInstance().updateHostelFeesTypeDetails(hostelFeesTypeForm,errors);
						if (isUpdated) {
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_FEES_TYPE_UPDATE_SUCCESS));
							saveMessages(request, messages);
							hostelFeesTypeForm.clear();
							assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						}
						if (!isUpdated) {
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_FEES_TYPE_UPDATE_FAILED));
							saveErrors(request, errors);
							hostelFeesTypeForm.clear();
							assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
						}
					}
				} else {
					isUpdated = HostelFeesTypeHandler.getInstance().updateHostelFeesTypeDetails(hostelFeesTypeForm,errors);
					if (isUpdated) {
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.HOSTEL_FEES_TYPE_UPDATE_SUCCESS));
						saveMessages(request, messages);
						hostelFeesTypeForm.clear();
						assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
					} else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_UPDATE_FAILED));
						saveErrors(request, errors);
						hostelFeesTypeForm.clear();
						assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
			}
		} catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}		
		log.info("End of updateHostelFeesTypeEntry in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to delete HlFeeType Details
	 * @param org.apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward deleteHostelFeesTypeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of deleteHostelFeesTypeDetails in HostelFeesTypeAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, hostelFeesTypeForm);
			String userId = hostelFeesTypeForm.getUserId();
			int feeId = hostelFeesTypeForm.getId();
			boolean isDeleted;
			isDeleted = HostelFeesTypeHandler.getInstance().deleteHostelFeesTypeDetails(feeId, userId);

			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_FEES_TYPE_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
				hostelFeesTypeForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.HOSTEL_FEES_TYPE_DELETE_FAILED));
				saveErrors(request, errors);
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
			}

		} catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of deleteHostelFeesTypeDetails in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to reactivate HlFeeType Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward reActivateHostelFeesType(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateHostelFeesType in HostelFeesTypeAction class");
		HostelFeesTypeForm hostelFeesTypeForm = (HostelFeesTypeForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, hostelFeesTypeForm);
			String userId = hostelFeesTypeForm.getUserId();
			String feeType = hostelFeesTypeForm.getHostelFeesType();
			boolean isReactivate;
			isReactivate = HostelFeesTypeHandler.getInstance().reActivateHostelFeesType(feeType, userId);
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_FEES_TYPE_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				hostelFeesTypeForm.clear();
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
			} else {
				errors.add("error", new ActionError(CMSConstants.HOSTEL_FEES_TYPE_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignHostelFeesTypeToListToForm(hostelFeesTypeForm);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hostelFeesTypeForm.setErrorMessage(msg);
			hostelFeesTypeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateHostelFeesType in HostelFeesTypeAction class");
		return mapping.findForward(CMSConstants.HOSTEL_FEES_TYPE_ENTRY);
	}
	
	/**
	 * Used to assign FeeFinancialYear List to Form
	 * @param org.apache.struts.action.ActionForm
	 * @return void
	 * @throws Exception
	 */
	public void assignHostelFeesTypeToListToForm(HostelFeesTypeForm hostelFeesTypeForm) throws Exception {
		log.info("Start of assignHostelFeesTypeToListToForm of HostelFeesTypeAction");
		List<HostelFeesTypeTo> hostelFeesTypeDetailsList = HostelFeesTypeHandler.getInstance().getHostelFeesTypeDetails();		
		hostelFeesTypeForm.setHostelFeesTypeToList(hostelFeesTypeDetailsList);
		log.info("End of assignFeeFinancialYearListToForm in FeeFinancialYearEntryAction class");
	}
	
	/**
	 * special character validation
	 * 
	 * @param name
	 * @return
	 */
	private boolean nameValidate(String name) {
		boolean result = false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9 \\. \\s \t \\/ \\( \\) ]+");

		Matcher matcher = pattern.matcher(name);
		result = matcher.find();
		return result;

	}
	
	private void validateFormSpecialCharacter(HostelFeesTypeForm hostelFeesTypeForm, ActionErrors errors, HttpServletRequest request)throws Exception
	{
		if(hostelFeesTypeForm.getHostelFeesType() != null && !hostelFeesTypeForm.getHostelFeesType().isEmpty() && nameValidate(hostelFeesTypeForm.getHostelFeesType()))
		{
			errors.add("error", new ActionError("knowledgepro.hostel.vistorinfo.specialCharacter","Hostel Fees Type"));
		}
	}
}
