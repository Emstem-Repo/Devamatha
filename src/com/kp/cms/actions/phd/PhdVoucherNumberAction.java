package com.kp.cms.actions.phd;

import java.util.List;

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
import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.phd.PhdVoucherNumberForm;
import com.kp.cms.handlers.phd.PhdVoucherNumberHandler;
import com.kp.cms.to.phd.PhdVoucherNumberTO;

/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class PhdVoucherNumberAction extends BaseDispatchAction {

	private static final Logger log = Logger.getLogger(PhdVoucherNumberAction.class);

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initVoucherNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of initVoucherNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		phdVoucherNumberForm.clearPage();
		setDataToToList(phdVoucherNumberForm);
		log.info("End of initVoucherNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
	
	private void setDataToToList(PhdVoucherNumberForm phdVoucherNumberForm) throws Exception {
	List<PhdVoucherNumberTO> voucherList=PhdVoucherNumberHandler.getInstance().setDataToToList();
	phdVoucherNumberForm.setPhdVoucherNumberList(voucherList);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addValuationNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of addValuationNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = phdVoucherNumberForm.validate(mapping, request);
		boolean isAdded = false;
		boolean duplicate = false;
		try {
			if (errors.isEmpty()) {
				setUserId(request, phdVoucherNumberForm);
				duplicate = PhdVoucherNumberHandler.getInstance().duplicatecheck(phdVoucherNumberForm,errors);
				if(!duplicate){
					
					if(phdVoucherNumberForm.getCurrentYear().equalsIgnoreCase("true")){
						PhdVoucherNumberHandler.getInstance().changeAllToNo(phdVoucherNumberForm);
					}
					
				isAdded = PhdVoucherNumberHandler.getInstance().addValuationNumber(phdVoucherNumberForm,errors);
				if (isAdded) {
					if(errors.isEmpty()){
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.internalGuide.addsuccess"));
					saveMessages(request, messages);
					phdVoucherNumberForm.clearPage();
					setDataToToList(phdVoucherNumberForm);
					}else{
						setDataToToList(phdVoucherNumberForm);
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
					}
			      }else{
			    	  setDataToToList(phdVoucherNumberForm);
					  saveErrors(request, errors);
					  return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
				    }
			}else{
				setDataToToList(phdVoucherNumberForm);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
			} }else {
				saveErrors(request, errors);
			}
		}
		catch (Exception e) {
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
			} 
			else{
				String msg = super.handleApplicationException(e);
				phdVoucherNumberForm.setErrorMessage(msg);
				phdVoucherNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("End of addValuationNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editValuationNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editValuationNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		try {
			    PhdVoucherNumberHandler.getInstance().getVoucherNumberById(phdVoucherNumberForm);
			    String previousYear = phdVoucherNumberForm.getFinancialYear();
				HttpSession session = request.getSession(true);
				session.setAttribute("previousYear", previousYear);
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			phdVoucherNumberForm.setErrorMessage(msg);
			phdVoucherNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
		.info("End of editValuationNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
	
	/**
	 * Used to update PCFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward updateValuationNumber(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updateValuationNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		 ActionErrors errors = phdVoucherNumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (isCancelled(request)) {
				phdVoucherNumberForm.reset(mapping, request);
				String formName = mapping.getName();
				request.getSession().setAttribute(CMSConstants.FORMNAME,formName);
				PhdVoucherNumberHandler.getInstance().getVoucherNumberById(phdVoucherNumberForm);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
			} else if (errors.isEmpty()) {
				setUserId(request, phdVoucherNumberForm);
				HttpSession session = request.getSession(false);
				String prevoiusYear = String.valueOf(session.getAttribute("previousYear"));
				String currentYear = phdVoucherNumberForm.getFinancialYear();
				if(phdVoucherNumberForm.getCurrentYear().equalsIgnoreCase("true")){
					PhdVoucherNumberHandler.getInstance().changeAllToNo(phdVoucherNumberForm);
				}
				boolean isUpdated;
				if(prevoiusYear.equals(currentYear)){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.internalguide.update.success"));
					saveMessages(request, messages);
					isUpdated = PhdVoucherNumberHandler.getInstance().updateVoucherNumber(phdVoucherNumberForm,errors);
					phdVoucherNumberForm.clearPage();
					setDataToToList(phdVoucherNumberForm);
					return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
				}
				if (!prevoiusYear.equals(currentYear)) {
					PhdVoucherNumber phdVoucherNumber = PhdVoucherNumberHandler.getInstance().getPhdFinancialYear(currentYear);
					if (phdVoucherNumber != null) {
						if (phdVoucherNumber.getIsActive()) {
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
						}
						if (!phdVoucherNumber.getIsActive()) {
							errors.add("error",new ActionError(CMSConstants.PHD_FINANCIAL_YEAR_REACTIVATE));
							saveErrors(request, errors);
						}
					} else {
						isUpdated = PhdVoucherNumberHandler.getInstance().updateVoucherNumber(phdVoucherNumberForm,errors);
						if (isUpdated) {
							messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.internalguide.update.success"));
							saveMessages(request, messages);
							phdVoucherNumberForm.clearPage();
						}
						if (!isUpdated) {
							errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.phd.internal.update.failed"));
							saveErrors(request, errors);
							phdVoucherNumberForm.clearPage();
						}
					}
				} else {
					isUpdated = PhdVoucherNumberHandler.getInstance().updateVoucherNumber(phdVoucherNumberForm,errors);
					if (isUpdated) {
						messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.phd.internalguide.update.success"));
						saveMessages(request, messages);
						phdVoucherNumberForm.clearPage();
					} else {
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.internal.update.failed"));
						saveErrors(request, errors);
						phdVoucherNumberForm.clearPage();
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
			}
		} catch (Exception e) {			
			if (e instanceof BusinessException) {
				setDataToToList(phdVoucherNumberForm);
				addErrors(request, errors);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
			} 
			else{
				String msg = super.handleApplicationException(e);
				phdVoucherNumberForm.setErrorMessage(msg);
				phdVoucherNumberForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}		
		}
		setDataToToList(phdVoucherNumberForm);
		log.info("End of updateValuationNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
	
	/**
	 * Used to delete PCFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward deleteValuationNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of deleteValuationNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, phdVoucherNumberForm);
			String userId = phdVoucherNumberForm.getUserId();
			int feeId = phdVoucherNumberForm.getId();
			boolean isDeleted;
			isDeleted = PhdVoucherNumberHandler.getInstance().deleteVoucherNumber(feeId, userId);

			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.phd.internal.deletesuccess"));
				saveMessages(request, messages);
				phdVoucherNumberForm.clearPage();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.phd.ginternal.deletefailure"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			phdVoucherNumberForm.setErrorMessage(msg);
			phdVoucherNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToToList(phdVoucherNumberForm);
		log.info("End of deleteValuationNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
	
	/**
	 * Used to reactivate PCFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward reActivateValuationNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateValuationNumber in PhdVoucherNumberAction class");
		PhdVoucherNumberForm phdVoucherNumberForm = (PhdVoucherNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, phdVoucherNumberForm);
			String userId = phdVoucherNumberForm.getUserId();
			String year = phdVoucherNumberForm.getFinancialYear();
			boolean isReactivate;
			isReactivate = PhdVoucherNumberHandler.getInstance().reActivateVoucherNumber(year, userId,phdVoucherNumberForm);
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				phdVoucherNumberForm.clearPage();
			} else {
				errors.add("error", new ActionError(CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE_FAILED));
				saveErrors(request, errors);
			}
			setDataToToList(phdVoucherNumberForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			phdVoucherNumberForm.setErrorMessage(msg);
			phdVoucherNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateValuationNumber in PhdVoucherNumberAction class");
		return mapping.findForward(CMSConstants.PHD_VOUCHER_NUMBER);
	}
}
