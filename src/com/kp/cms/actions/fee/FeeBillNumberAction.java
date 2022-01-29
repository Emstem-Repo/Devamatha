package com.kp.cms.actions.fee;

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
import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.constants.CMSConstants;

import com.kp.cms.forms.fee.FeeBillNumberForm;

import com.kp.cms.handlers.fee.FeeBillNumberHandler;

import com.kp.cms.to.fee.FeeBillNumberTO;

@SuppressWarnings("deprecation")
public class FeeBillNumberAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(FeeBillNumberAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes FeeBillNumber
	 * @throws Exception
	 */
	public ActionForward initFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FeeBillNumberForm feeBillNumberForm = (FeeBillNumberForm) form;
		log.info("Start of initFeeBillNumber of FeeBillNumberAction");
		try {
			assignListToForm(feeBillNumberForm);
			feeBillNumberForm.clear();
		} catch (Exception e) {
			log.error("Error in initializing feeBillNumber");			
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of initFeeBillNumber of FeeBillNumberAction");
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Used while inserting a fee bill number
	 * @throws Exception
	 */

	public ActionForward addFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of addFeeBillNumber of FeeBillNumberAction");
		FeeBillNumberForm feeBillNumberForm = (FeeBillNumberForm) form;
		ActionErrors errors = new ActionErrors();
		errors = feeBillNumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				setUserId(request, feeBillNumberForm);
				final int year = Integer.parseInt(feeBillNumberForm.getAcademicYear());
				FeeBillNumber number = FeeBillNumberHandler.getInstance().getFeeBillNoYear(year);
				/**
				 * Checks for the duplicate entry based on the year.
				 * If it exists in active mode then add the appropriate error message
				 */
				if (number != null) {
					if(number.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_BILLNUMBER_EXISTS));					
					assignListToForm(feeBillNumberForm);
					feeBillNumberForm.clear();
					saveErrors(request, errors);
					}				
				/**
				 * If it is in inactive mode then show the message to reactivate the same.
				 */
					else if(!number.getIsActive()){
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.FEE_BILLNUMBER_REACTIVATE));
					assignListToForm(feeBillNumberForm);
					saveErrors(request, errors);
					}
				}
				else{
				boolean isAdded;

				isAdded = FeeBillNumberHandler.getInstance().addFeeBillNumber(feeBillNumberForm);
				/**
				 * If add operation is success then append the success message else add the appropriate error message
				 */
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES,  new ActionMessage(CMSConstants.FEE_BILLNUMBER_ADD_SUCCESS));
					saveMessages(request, messages);
					assignListToForm(feeBillNumberForm);
					feeBillNumberForm.clear();
				}
				else {
					errors.add("error", new ActionError(CMSConstants.FEE_BILLNUMBER_ADD_FAILED));
					assignListToForm(feeBillNumberForm);
					saveErrors(request, errors);
				}
				}
			}
			else{
				assignListToForm(feeBillNumberForm);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error in adding feeBillNumber");
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
		log.info("End of addFeeBillNumber of FeeBillNumberAction");
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
	}

	/**
	 * Used in deletint a fee bill number (Makes inactive)
	 */

	public ActionForward deleteFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of deleteFeeBillNumber of FeeBillNumberAction");
		FeeBillNumberForm feeBillNumberForm = (FeeBillNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, feeBillNumberForm);
			String userId=feeBillNumberForm.getUserId();
			int feeId = feeBillNumberForm.getId();
			boolean isDeleted;
			isDeleted = FeeBillNumberHandler.getInstance().deleteFeeBillNumber(feeId, userId);
			/**
			 * If delete operation is success then add the success message.
			 * Else add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.FEE_BILLNUMBER_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(feeBillNumberForm);
				feeBillNumberForm.clear();
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_BILLNUMBER_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(feeBillNumberForm);
			}

		} catch (Exception e) {
			log.error("Error in adding feeBillNumber");			
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of deleteFeeBillNumber of FeeBillNumberAction");
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
	}

	/**
	 * In reactivation
	 */

	public ActionForward reActivateFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateFeeBillNumber of FeeBillNumberAction");
		FeeBillNumberForm feeBillNumberForm = (FeeBillNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, feeBillNumberForm);
			String userId=feeBillNumberForm.getUserId();
			final int year = Integer.parseInt(feeBillNumberForm.getAcademicYear());
			boolean isReactivate;
			isReactivate = FeeBillNumberHandler.getInstance().reActivateFeeBillNumber(year, userId);
			/**
			 * If reactivation is success then add the success message.
			 * Else add the appropriate error message.
			 */
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.FEE_BILLNUMBER_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				feeBillNumberForm.clear();
				assignListToForm(feeBillNumberForm);
			} else {
				errors.add("error", new ActionError(CMSConstants.FEE_BILLNUMBER_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(feeBillNumberForm);
			}
		} catch (Exception e) {
			log.error("Error in adding feeBillNumber");			
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateFeeBillNumber of FeeBillNumberAction");
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
	}

	/**
	 * 
	 *Used While editing
	 */

	public ActionForward editFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editFeeBillNumber of FeeBillNumberAction");
		FeeBillNumberForm feeBillNumberForm=(FeeBillNumberForm)form;
		try {
			int feeId = feeBillNumberForm.getId();
			FeeBillNumberTO feeBillNumberTO=FeeBillNumberHandler.getInstance().getFeeBillNumberDetailsonId(feeId);
			//Keeping the year in session scope
			if(feeBillNumberTO!=null){
				feeBillNumberForm.setBillNo(String.valueOf(feeBillNumberTO.getBillNo()));
				feeBillNumberForm.setAcademicYear(String.valueOf(feeBillNumberTO.getFinacialYearID()));
				int previousYear=feeBillNumberTO.getFinacialYearID();
				HttpSession session=request.getSession(true);
				session.setAttribute("previousYear", previousYear);
			}
			assignListToForm(feeBillNumberForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error in deleting feeBillNumber");
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of editFeeBillNumber of FeeBillNumberAction");
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);

	}
	
	/**
	 * 
	 * @param feeForm
	 * @throws Exception
	 * Used in updation
	 */
	
	public ActionForward updateFeeBillNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updateFeeBillNumber of FeeBillNumberAction");
		FeeBillNumberForm feeBillNumberForm=(FeeBillNumberForm)form;
		ActionErrors errors = new ActionErrors();
		errors = feeBillNumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			if(isCancelled(request)){
				int feeId = feeBillNumberForm.getId();
				FeeBillNumberTO feeBillNumberTO=FeeBillNumberHandler.getInstance().getFeeBillNumberDetailsonId(feeId);
				if(feeBillNumberTO!=null){
					feeBillNumberForm.setBillNo(String.valueOf(feeBillNumberTO.getBillNo()));
					feeBillNumberForm.setAcademicYear(String.valueOf(feeBillNumberTO.getAcademicYear()));
					int previousYear=feeBillNumberTO.getFinacialYearID();
					HttpSession session=request.getSession(true);
					session.setAttribute("previousYear", previousYear);
				}			
				assignListToForm(feeBillNumberForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
			}
			else if(errors.isEmpty()){
				setUserId(request, feeBillNumberForm);						
				/**
				 * Getting the previous year from session and comparing with the current year.
				 * 
				 */
					HttpSession session=request.getSession(false);
					final int prevoiusYear= (Integer)session.getAttribute("previousYear");
					final int currentYear = Integer.parseInt(feeBillNumberForm.getAcademicYear());
					boolean isUpdated;
				if(prevoiusYear!=currentYear){	
					FeeBillNumber number = FeeBillNumberHandler.getInstance().getFeeBillNoYear(currentYear);
				/**
				 * Checking for duplicate entry.
				 * And add the appropriate error message
				 */
						if (number != null) {
							if(number.getIsActive()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_BILLNUMBER_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							assignListToForm(feeBillNumberForm);
							}
							if (!number.getIsActive()) {
							errors.add("error",new ActionError(CMSConstants.FEE_BILLNUMBER_REACTIVATE));
							saveErrors(request, errors);
							assignListToForm(feeBillNumberForm);
							}
						}
						else{
						isUpdated=FeeBillNumberHandler.getInstance().updateFeeBillNumber(feeBillNumberForm);
						
						//If update is successful then add the success message else show the error message
						
							if(isUpdated){
								messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.FEE_BILLNUMBER_UPDATE_SUCCESS));
								saveMessages(request, messages);
								feeBillNumberForm.clear();
								assignListToForm(feeBillNumberForm);	
							}
							if(!isUpdated){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_BILLNUMBER_UPDATE_FAILED));
								saveErrors(request, errors);
								feeBillNumberForm.clear();
								assignListToForm(feeBillNumberForm);
							}
						}
					
				}else{
					isUpdated=FeeBillNumberHandler.getInstance().updateFeeBillNumber(feeBillNumberForm);
					
					//If update is successful then add the success message else show the error message
					
					if(isUpdated){
						messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.FEE_BILLNUMBER_UPDATE_SUCCESS));
						saveMessages(request, messages);
						feeBillNumberForm.clear();
						assignListToForm(feeBillNumberForm);	
					}
					else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.FEE_BILLNUMBER_UPDATE_FAILED));
						saveErrors(request, errors);
						feeBillNumberForm.clear();
						assignListToForm(feeBillNumberForm);
					}
				}
			}
			else{
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignListToForm(feeBillNumberForm);
			}
		} catch (Exception e) {
			log.error("Error in updating feeBillNumber");
				String msg = super.handleApplicationException(e);
				feeBillNumberForm.setErrorMessage(msg);
				feeBillNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- FeeBillNumberAction --- updateFeeBillNumber");	
		return mapping.findForward(CMSConstants.INIT_FEEBILL_ENTRY);
	}
	/*
	 * 
	 * @param form Getting all the records of FeeBillNumber from DB and assigned
	 * to form
	 * 
	 * @throws Exception
	 */
	public void assignListToForm(FeeBillNumberForm feeForm) throws Exception {
		log.info("Start of assignListToForm of FeeBillNumberAction");
		List<FeeBillNumberTO> feeBillNumberList = FeeBillNumberHandler.getInstance().getFeeBillNumberDetails();
			feeForm.setFeeBillNumberList(feeBillNumberList);
		log.info("End of assignListToForm of FeeBillNumberAction");
	}

}
