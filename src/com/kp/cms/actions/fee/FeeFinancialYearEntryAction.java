package com.kp.cms.actions.fee;

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
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeeFinancialYearEntryForm;
import com.kp.cms.handlers.fee.FeeFinancialYearHandler;
import com.kp.cms.to.fee.FeeFinancialYearTO;

/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class FeeFinancialYearEntryAction extends BaseDispatchAction {

	private static final Logger log = Logger
			.getLogger(FeeFinancialYearEntryAction.class);

	/**
	 * Used to get FeeFinancialYear Details and bind to Form i.e binding	
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward initFeeFinancialYearEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
				.info("Start of initFeeFinancialYearEntry in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		try {
			assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			feeFinancialYearEntryForm.setErrorMessage(msg);
			feeFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
				.info("End of initFeeFinancialYearEntry in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to add FeeFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward addFeeFinancialYearEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of addFeeFinancialYearEntry in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		errors = feeFinancialYearEntryForm.validate(mapping, request);
		boolean isFinancialYearAdded = false;
		try {
			if (errors.isEmpty()) {
				String feeFinancialYearValue = null;
				setUserId(request, feeFinancialYearEntryForm);
				feeFinancialYearValue = feeFinancialYearEntryForm
						.getFeeFinancialYear();
				FeeFinancialYear feeFinancialYear = FeeFinancialYearHandler
						.getInstance().getFeeFinancialYear(
								feeFinancialYearValue);
				if (feeFinancialYear != null) {
					if (feeFinancialYear.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.FEE_FINANCIAL_YEAR_EXISTS));
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						feeFinancialYearEntryForm.clear();
						saveErrors(request, errors);
					} else if (!feeFinancialYear.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.FEE_FINANCIAL_YEAR_REACTIVATE));
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						saveErrors(request, errors);
					}
				} else {
					isFinancialYearAdded = FeeFinancialYearHandler
							.getInstance().addFeeFinancialYear(
									feeFinancialYearEntryForm,errors);
					if (isFinancialYearAdded) {
						messages.add(CMSConstants.MESSAGES, new ActionMessage(
								CMSConstants.FEE_FINANCIAL_YEAR_ADD_SUCCESS));
						saveMessages(request, messages);
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						feeFinancialYearEntryForm.clear();
					} else {
						errors.add("errors", new ActionError(
								CMSConstants.FEE_FINANCIAL_YEAR_ADD_FAILED));
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						saveErrors(request, errors);
					}
				}
			} else {
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
				saveErrors(request, errors);
			}
		}
		catch (Exception e) {
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
				return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
			} 
			else{
				String msg = super.handleApplicationException(e);
				feeFinancialYearEntryForm.setErrorMessage(msg);
				feeFinancialYearEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log
		.info("End of addFeeFinancialYearEntry in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to edit FeeFinancialYear
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	public ActionForward editFeeFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of editFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		try {
			int feeId = feeFinancialYearEntryForm.getId();
			FeeFinancialYearTO feeFinancialYearTO = FeeFinancialYearHandler
					.getInstance().getFeeFinancialDetailsWithId(feeId);
			// Keeping the year in session scope
			if (feeFinancialYearTO != null) {
				feeFinancialYearEntryForm.setIsCurrent(feeFinancialYearTO
						.getIsCurrent());
				feeFinancialYearEntryForm
						.setFeeFinancialYearSel(feeFinancialYearTO.getYear()
								.substring(0, 4));
				String previousYear = feeFinancialYearTO.getYear();
				HttpSession session = request.getSession(true);
				session.setAttribute("previousYear", previousYear);
			}
			assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
			request.setAttribute(CMSConstants.OPERATION,
					CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			feeFinancialYearEntryForm.setErrorMessage(msg);
			feeFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
		.info("End of editFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to update FeeFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward updateFeeFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of updateFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		errors = feeFinancialYearEntryForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();

		try {
			if (isCancelled(request)) {
				int feeId = feeFinancialYearEntryForm.getId();
				FeeFinancialYearTO feeFinancialYearTO = FeeFinancialYearHandler
						.getInstance().getFeeFinancialDetailsWithId(feeId);
				if (feeFinancialYearTO != null) {
					feeFinancialYearEntryForm.setIsCurrent(feeFinancialYearTO
							.getIsCurrent());
					feeFinancialYearEntryForm
							.setFeeFinancialYear(feeFinancialYearTO.getYear());
				}
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping
						.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
			} else if (errors.isEmpty()) {
				setUserId(request, feeFinancialYearEntryForm);
				HttpSession session = request.getSession(false);
				String prevoiusYear = String.valueOf(session
						.getAttribute("previousYear"));
				String currentYear = feeFinancialYearEntryForm
						.getFeeFinancialYear();
				boolean isUpdated;
				if (!prevoiusYear.equals(currentYear)) {
					FeeFinancialYear feeFinancialYear = FeeFinancialYearHandler
							.getInstance().getFeeFinancialYear(currentYear);

					if (feeFinancialYear != null) {
						if (feeFinancialYear.getIsActive()) {
							errors.add(CMSConstants.ERROR, new ActionError(
									CMSConstants.FEE_FINANCIAL_YEAR_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,
									CMSConstants.EDIT_OPERATION);
							assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						}
						if (!feeFinancialYear.getIsActive()) {
							errors
									.add(
											"error",
											new ActionError(
													CMSConstants.FEE_FINANCIAL_YEAR_REACTIVATE));
							saveErrors(request, errors);
							assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						}
					} else {
						isUpdated = FeeFinancialYearHandler.getInstance()
								.updateFeeFinancialYear(
										feeFinancialYearEntryForm,errors);
						if (isUpdated) {
							messages
									.add(
											CMSConstants.MESSAGES,
											new ActionMessage(
													CMSConstants.FEE_FINANCIAL_YEAR_UPDATE_SUCCESS));
							saveMessages(request, messages);
							feeFinancialYearEntryForm.clear();
							assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						}
						if (!isUpdated) {
							errors
									.add(
											CMSConstants.ERROR,
											new ActionError(
													CMSConstants.FEE_FINANCIAL_YEAR_UPDATE_FAILED));
							saveErrors(request, errors);
							feeFinancialYearEntryForm.clear();
							assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
						}
					}
				} else {
					isUpdated = FeeFinancialYearHandler.getInstance()
							.updateFeeFinancialYear(feeFinancialYearEntryForm,errors);
					if (isUpdated) {
						messages
								.add(
										CMSConstants.MESSAGES,
										new ActionMessage(
												CMSConstants.FEE_FINANCIAL_YEAR_UPDATE_SUCCESS));
						saveMessages(request, messages);
						feeFinancialYearEntryForm.clear();
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
					} else {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.FEE_FINANCIAL_YEAR_UPDATE_FAILED));
						saveErrors(request, errors);
						feeFinancialYearEntryForm.clear();
						assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
			}
		} catch (Exception e) {			
			if (e instanceof BusinessException) {
				addErrors(request, errors);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
				return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
			} 
			else{
				String msg = super.handleApplicationException(e);
				feeFinancialYearEntryForm.setErrorMessage(msg);
				feeFinancialYearEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}		
		}
		log
		.info("End of updateFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to delete FeeFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward deleteFeeFinancialYearDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of deleteFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, feeFinancialYearEntryForm);
			String userId = feeFinancialYearEntryForm.getUserId();
			int feeId = feeFinancialYearEntryForm.getId();
			boolean isDeleted;
			isDeleted = FeeFinancialYearHandler.getInstance()
					.deleteFeeFinancialYearDetails(feeId, userId);

			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.FEE_FINANCIAL_YEAR_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
				feeFinancialYearEntryForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.FEE_FINANCIAL_YEAR_DELETE_FAILED));
				saveErrors(request, errors);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
			}

		} catch (Exception e) {			
			String msg = super.handleApplicationException(e);
			feeFinancialYearEntryForm.setErrorMessage(msg);
			feeFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
		.info("End of deleteFeeFinancialYearDetails in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to reactivate FeeFinancialYear Details
	 * @param org
	 *            .apache.struts.action.ActionMapping,
	 *            org.apache.struts.action.ActionForm,
	 *            javax.servlet.http.HttpServletRequest,
	 *            javax.servlet.http.HttpServletResponse
	 * @return org.apache.struts.action.ActionForward
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward reActivateFeeFinancialYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
		.info("Start of reActivateFeeFinancialYear in FeeFinancialYearEntryAction class");
		FeeFinancialYearEntryForm feeFinancialYearEntryForm = (FeeFinancialYearEntryForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, feeFinancialYearEntryForm);
			String userId = feeFinancialYearEntryForm.getUserId();
			String year = feeFinancialYearEntryForm.getFeeFinancialYear();
			boolean isReactivate;
			isReactivate = FeeFinancialYearHandler.getInstance()
					.reActivateFeeFinancialYear(year, userId);
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.FEE_FINANCIAL_YEAR_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				feeFinancialYearEntryForm.clear();
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
			} else {
				errors.add("error", new ActionError(
						CMSConstants.FEE_FINANCIAL_YEAR_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignFeeFinancialYearListToForm(feeFinancialYearEntryForm);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			feeFinancialYearEntryForm.setErrorMessage(msg);
			feeFinancialYearEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log
		.info("End of reActivateFeeFinancialYear in FeeFinancialYearEntryAction class");
		return mapping.findForward(CMSConstants.FEE_FINANCIAL_YEAR_ENTRY);
	}
	
	/**
	 * Used to assign FeeFinancialYear List to Form
	 * @param org.apache.struts.action.ActionForm
	 * @return void
	 * @throws Exception
	 */
	public void assignFeeFinancialYearListToForm(
			FeeFinancialYearEntryForm feeFinancialYearEntryForm)
			throws Exception {
		log
				.info("Start of assignFeeFinancialYearListToForm of FeeFinancialYearEntryAction");
		List<FeeFinancialYearTO> feeFinaniclalYearDetailsList = FeeFinancialYearHandler
				.getInstance().getFeeFinancialDetails();
		feeFinancialYearEntryForm
				.setFeeFinancialYearList(feeFinaniclalYearDetailsList);
		log
		.info("End of assignFeeFinancialYearListToForm in FeeFinancialYearEntryAction class");
	}
}
