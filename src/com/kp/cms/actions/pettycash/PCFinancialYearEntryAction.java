package com.kp.cms.actions.pettycash;

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
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.pettycash.PCFinancialYearEntryForm;
import com.kp.cms.handlers.pettycash.PCFinancialYearHandler;
import com.kp.cms.to.pettycash.PCFinancialYearTO;

/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class PCFinancialYearEntryAction extends BaseDispatchAction {

	private static final Logger log = Logger
			.getLogger(PCFinancialYearEntryAction.class);

	/**
	 * Used to get PCFinancialYear Details and bind to Form i.e binding	
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward initPCFinancialYearEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
				.info("Start of initPCFinancialYearEntry in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		try {
			pcFinancialYearEntryForm.clear();
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			pcFinancialYearEntryForm.setErrorMessage(msg);
			pcFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
				.info("End of initPCFinancialYearEntry in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to add PCFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addPCFinancialYearEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of addPCFinancialYearEntry in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = pcFinancialYearEntryForm.validate(mapping, request);
		boolean isFinancialYearAdded = false;
		try {
			if (errors.isEmpty()) {
				String pcFinancialYearValue = null;
				setUserId(request, pcFinancialYearEntryForm);
				pcFinancialYearValue = pcFinancialYearEntryForm
						.getPcFinancialYear();
				PcFinancialYear pcFinancialYear = PCFinancialYearHandler
						.getInstance().getPcFinancialYear(
								pcFinancialYearValue);
				if (pcFinancialYear != null) {
					if (pcFinancialYear.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.PC_FINANCIAL_YEAR_EXISTS));
						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						pcFinancialYearEntryForm.clear();
						saveErrors(request, errors);
					} else if (!pcFinancialYear.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE));
						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						saveErrors(request, errors);
					}
				} else {
					isFinancialYearAdded = PCFinancialYearHandler
							.getInstance().addPcFinancialYear(
									pcFinancialYearEntryForm,errors);
					if (isFinancialYearAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(
								CMSConstants.PC_FINANCIAL_YEAR_ADD_SUCCESS));
						saveMessages(request, messages);
						pcFinancialYearEntryForm.clear();
//						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
					} else {
						errors.add("errors", new ActionError(
								CMSConstants.PC_FINANCIAL_YEAR_ADD_FAILED));
//						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						saveErrors(request, errors);
					}
				}
			} else {
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
				saveErrors(request, errors);
			}
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
		}
		catch (Exception e) {
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
				return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
			} 
			else{
				String msg = super.handleApplicationException(e);
				pcFinancialYearEntryForm.setErrorMessage(msg);
				pcFinancialYearEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log
		.info("End of addPCFinancialYearEntry in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to edit PCFinancialYear
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward editPCFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of editPCFinancialYearDetails in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		try {
			int feeId = pcFinancialYearEntryForm.getId();
			PCFinancialYearTO pcFinancialYearTO = PCFinancialYearHandler
					.getInstance().getPCFinancialDetailsWithId(feeId);
			// Keeping the year in session scope
			if (pcFinancialYearTO != null) {
				pcFinancialYearEntryForm.setIsCurrent(pcFinancialYearTO
						.getIsCurrent());
				pcFinancialYearEntryForm
						.setPcFinancialYearSel(pcFinancialYearTO.getYear()
								.substring(0, 4));
				String previousYear = pcFinancialYearTO.getYear();
				HttpSession session = request.getSession(true);
				session.setAttribute("previousYear", previousYear);
				pcFinancialYearEntryForm.setPcFinancialYearTO(pcFinancialYearTO);
			}
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			pcFinancialYearEntryForm.setErrorMessage(msg);
			pcFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
		.info("End of editPCFinancialYearDetails in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
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
	public ActionForward updatePCFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updatePCFinancialYearDetails in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		 ActionErrors errors = pcFinancialYearEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if (isCancelled(request)) {
				int feeId = pcFinancialYearEntryForm.getId();
				PCFinancialYearTO pcFinancialYearTO = PCFinancialYearHandler.getInstance().getPCFinancialDetailsWithId(feeId);
				if (pcFinancialYearTO != null) {
					pcFinancialYearEntryForm.setIsCurrent(pcFinancialYearTO.getIsCurrent());
					pcFinancialYearEntryForm.setPcFinancialYear(pcFinancialYearTO.getYear());
				}
				pcFinancialYearEntryForm.setPcFinancialYearTO(pcFinancialYearTO);
				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
			} else if (errors.isEmpty()) {
				setUserId(request, pcFinancialYearEntryForm);
				HttpSession session = request.getSession(false);
				String prevoiusYear = String.valueOf(session.getAttribute("previousYear"));
				String currentYear = pcFinancialYearEntryForm.getPcFinancialYear();
				boolean isUpdated;
				if(prevoiusYear.equals(currentYear) && pcFinancialYearEntryForm.getPcFinancialYearTO().getIsCurrent().equals(pcFinancialYearEntryForm.getIsCurrent())){
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_UPDATE_SUCCESS));
					saveMessages(request, messages);
					pcFinancialYearEntryForm.clear();
					assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
					return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
				}
				if (!prevoiusYear.equals(currentYear)) {
					PcFinancialYear feeFinancialYear = PCFinancialYearHandler.getInstance().getPcFinancialYear(currentYear);
					if (feeFinancialYear != null) {
						if (feeFinancialYear.getIsActive()) {
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PC_FINANCIAL_YEAR_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
//							assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						}
						if (!feeFinancialYear.getIsActive()) {
							errors.add("error",new ActionError(CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE));
							saveErrors(request, errors);
//							assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						}
					} else {
						isUpdated = PCFinancialYearHandler.getInstance().updatePCFinancialYear(pcFinancialYearEntryForm,errors);
						if (isUpdated) {
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_UPDATE_SUCCESS));
							saveMessages(request, messages);
							pcFinancialYearEntryForm.clear();
//							assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						}
						if (!isUpdated) {
							errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.PC_FINANCIAL_YEAR_UPDATE_FAILED));
							saveErrors(request, errors);
							pcFinancialYearEntryForm.clear();
//							assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
						}
					}
				} else {
					isUpdated = PCFinancialYearHandler.getInstance().updatePCFinancialYear(pcFinancialYearEntryForm,errors);
					if (isUpdated) {
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_UPDATE_SUCCESS));
						saveMessages(request, messages);
						pcFinancialYearEntryForm.clear();
//						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
					} else {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.PC_FINANCIAL_YEAR_UPDATE_FAILED));
						saveErrors(request, errors);
						pcFinancialYearEntryForm.clear();
//						assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			}
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
		} catch (Exception e) {			
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				request.setAttribute(CMSConstants.OPERATION,CMSConstants.EDIT_OPERATION);
				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
				return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
			} 
			else{
				String msg = super.handleApplicationException(e);
				pcFinancialYearEntryForm.setErrorMessage(msg);
				pcFinancialYearEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}		
		}
		log
		.info("End of updatePCFinancialYearDetails in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
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
	public ActionForward deletePCFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of deletePCFinancialYearDetails in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, pcFinancialYearEntryForm);
			String userId = pcFinancialYearEntryForm.getUserId();
			int feeId = pcFinancialYearEntryForm.getId();
			boolean isDeleted;
			isDeleted = PCFinancialYearHandler.getInstance().deletePCFinancialYearDetails(feeId, userId);

			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_DELETE_SUCCESS));
				saveMessages(request, messages);
				pcFinancialYearEntryForm.clear();
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.PC_FINANCIAL_YEAR_DELETE_FAILED));
				saveErrors(request, errors);
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			}
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
		} catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			pcFinancialYearEntryForm.setErrorMessage(msg);
			pcFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of deletePCFinancialYearDetails in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
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
	public ActionForward reActivatePCFinancialYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivatePCFinancialYear in PCFinancialYearEntryAction class");
		PCFinancialYearEntryForm pcFinancialYearEntryForm = (PCFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, pcFinancialYearEntryForm);
			String userId = pcFinancialYearEntryForm.getUserId();
			String year = pcFinancialYearEntryForm.getPcFinancialYear();
			boolean isReactivate;
			isReactivate = PCFinancialYearHandler.getInstance().reActivatePCFinancialYear(year, userId);
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				pcFinancialYearEntryForm.clear();
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			} else {
				errors.add("error", new ActionError(CMSConstants.PC_FINANCIAL_YEAR_REACTIVATE_FAILED));
				saveErrors(request, errors);
//				assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
			}
			assignPCFinancialYearListToForm(pcFinancialYearEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			pcFinancialYearEntryForm.setErrorMessage(msg);
			pcFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivatePCFinancialYear in PCFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.PC_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to assign PCFinancialYear List to Form
	 * @param org.apache.struts.action.ActionForm
	 * @return void
	 * @throws Exception
	 */
	public void assignPCFinancialYearListToForm(
			PCFinancialYearEntryForm pcFinancialYearEntryForm)
			throws Exception {
		log.info("Start of assignPCFinancialYearListToForm of PCFinancialYearEntryAction");
		List<PCFinancialYearTO> pcFinaniclalYearDetailsList = PCFinancialYearHandler.getInstance().getPCFinancialDetails();
		pcFinancialYearEntryForm.setPcFinancialYearList(pcFinaniclalYearDetailsList);
		log.info("End of assignPCFinancialYearListToForm in PCFinancialYearEntryAction class");
	}
}
