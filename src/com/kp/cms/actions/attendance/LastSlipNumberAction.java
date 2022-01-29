package com.kp.cms.actions.attendance;

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
import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.LastSlipNumberForm;
import com.kp.cms.handlers.attendance.LastSlipNumberHandler;
import com.kp.cms.to.attendance.LastSlipNumberTo;
import com.kp.cms.to.pettycash.FinancialYearTO;
@SuppressWarnings("deprecation")
public class LastSlipNumberAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(LastSlipNumberAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Inside initLastSlipNumber");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm) form;
		try {
			
			assignListToForm(lastSlipNumberForm);
			setFinancialYearList(lastSlipNumberForm);
			lastSlipNumberForm.clear();
		} catch (Exception e) {
			log.error("error initLastReceiptNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Existing initLastSlipNumber");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the addLastSlipNumber of LastSlipNumberAction");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm) form;
		 ActionErrors errors = lastSlipNumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {

			if (errors.isEmpty()) {
				setUserId(request, lastSlipNumberForm);
				final int year = Integer.parseInt(lastSlipNumberForm
						.getAcademicYear());
				AttendanceSlipNumber attendanceSlipNumberBo = LastSlipNumberHandler
						.getInstance().getLastSlipNumberYear(year);
				/**
				 * Checks for the duplicate entry based on the year. If it
				 * exists in active mode then add the appropriate error message
				 */
				if (attendanceSlipNumberBo != null) {
					if (attendanceSlipNumberBo.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError(
								CMSConstants.ATTENDANCE_SLIPNUMBER_EXISTS));
						assignListToForm(lastSlipNumberForm);
						setFinancialYearList(lastSlipNumberForm);
						lastSlipNumberForm.clear();
						saveErrors(request, errors);
					}
					/**
					 * If it is in inactive mode then show the message to
					 * reactivate the same.
					 */
					else if (!attendanceSlipNumberBo.getIsActive()) {
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.alreadyexist.reactivate"));
						assignListToForm(lastSlipNumberForm);
						setFinancialYearList(lastSlipNumberForm);
						saveErrors(request, errors);
					}
				} else {
					boolean isAdded;

					isAdded = LastSlipNumberHandler.getInstance()
							.addLastSlipNumber(lastSlipNumberForm);

					/**
					 * If add operation is success then append the success
					 * message else add the appropriate error message
					 */
					if (isAdded) {
						messages
								.add(
										CMSConstants.MESSAGES,
										new ActionMessage(
												CMSConstants.ATTENDANCE_SLIPNUMBER_ADD_SUCCESS));
						saveMessages(request, messages);
						assignListToForm(lastSlipNumberForm);
						setFinancialYearList(lastSlipNumberForm);
						lastSlipNumberForm.clear();
					} else {
						errors.add("error", new ActionError(
								CMSConstants.ATTENDANCE_SLIPNUMBER_ADD_FAILED));
						assignListToForm(lastSlipNumberForm);
						saveErrors(request, errors);
					}
				}
			} else {
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("Error in adding LastSlipNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("End of addlastSlipNumber of LastSlipNumberAction");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into deleteLastSlipNumber of LastSlipNumberAction");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, lastSlipNumberForm);
			String userId = lastSlipNumberForm.getUserId();
			int SlipId = lastSlipNumberForm.getId();
			boolean isDeleted;

			isDeleted = LastSlipNumberHandler.getInstance()
					.deleteLastSlipNumber(SlipId, userId);
			/**
			 * If delete operation is success then add the success message. Else
			 * add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(
						CMSConstants.ATTENDANCE_SLIPNUMBER_DELETED_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
				lastSlipNumberForm.clear();
			} else {
				errors.add(CMSConstants.ERROR, new ActionError(
						CMSConstants.ATTENDANCE_SLIPNUMBER_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
			}
		} catch (Exception e) {
			log.error("Error in deleteLastSlipNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Existing  deleteLastSlipNumber of LastSlipNumberAction");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateLastSlipNumber of LastSlipNumberAction");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, lastSlipNumberForm);
			String userId = lastSlipNumberForm.getUserId();
			final int year = Integer.parseInt(request.getParameter("academicYear"));
			boolean isReactivate;
			isReactivate = LastSlipNumberHandler.getInstance()
					.reActivateLastSlipNumber(year, userId);
			/**
			 * If reactivation is success then add the success message. Else add
			 * the appropriate error message.
			 */
			if (isReactivate) {
				messages.add(CMSConstants.MESSAGES,new ActionMessage(
										CMSConstants.ATTENDANCE_SLIPNUMBER_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				//lastSlipNumberForm.clear();
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
				
			} else {
				errors.add("error",new ActionError(CMSConstants.ATTENDANCE_SLIPNUMBER_REACTIVATE_FAILED));
				saveErrors(request, errors);
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating lastSlipNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		List<LastSlipNumberTo> slipNumberToList = LastSlipNumberHandler.getInstance().getSlipNumberDetails();
		lastSlipNumberForm.setSlipNumberList(slipNumberToList);
		log.info("End of reActivateLastSlipNumber of LastSlipNumberAction");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);
	}

	/**
	 * 
	 *Used While editing
	 */

	public ActionForward editLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of editLastSlipNumber of LastSlipNumberAction");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm) form;
		try {
			int slipId = lastSlipNumberForm.getId();
			LastSlipNumberTo lastSlipNumberTO = LastSlipNumberHandler
					.getInstance().getLastSlipNumberDetailsonId(slipId);
			// Keeping the year in session scope
			if (lastSlipNumberTO != null) {
				lastSlipNumberForm.setSlipNo(String.valueOf(lastSlipNumberTO
						.getSlipNo()));
				lastSlipNumberForm.setAcademicYear(String
						.valueOf(lastSlipNumberTO.getFinacialYearID()));
				int previousYear = lastSlipNumberTO.getFinacialYearID();
				HttpSession session = request.getSession(true);
				session.setAttribute("previousYear", previousYear);
			}
			assignListToForm(lastSlipNumberForm);
			setFinancialYearList(lastSlipNumberForm);
			request.setAttribute(CMSConstants.OPERATION,
					CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error in editing lastSlipNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log
				.info("exiting from editLastSlipNumber of LastSlipNumberAction");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);

	}

	public ActionForward updateLastSlipNumber(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of updateLastSlipNumber of LastSlipNumberAction");
		LastSlipNumberForm lastSlipNumberForm = (LastSlipNumberForm) form;
		 ActionErrors errors = lastSlipNumberForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();

		try {
			if (isCancelled(request)) {
				int slipId = lastSlipNumberForm.getId();

				LastSlipNumberTo lastSlipNumberTO = LastSlipNumberHandler
						.getInstance().getLastSlipNumberDetailsonId(slipId);
				if (lastSlipNumberTO != null) {
					lastSlipNumberForm.setSlipNo(String
							.valueOf(lastSlipNumberTO.getSlipNo()));
					lastSlipNumberForm.setAcademicYear(String
							.valueOf(lastSlipNumberTO.getFinacialYearID()));
					int previousYear = lastSlipNumberTO.getFinacialYearID();
					HttpSession session = request.getSession(true);
					session.setAttribute("previousYear", previousYear);
				}
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				return mapping
						.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);
			} else if (errors.isEmpty()) {
				setUserId(request, lastSlipNumberForm);
				/**
				 * Getting the previous year from session and comparing with the
				 * current year.
				 * 
				 */
				HttpSession session = request.getSession(false);
				final int prevoiusYear = (Integer) session
						.getAttribute("previousYear");
				final int currentYear = Integer.parseInt(lastSlipNumberForm
						.getAcademicYear());
				boolean isUpdated;
				if (prevoiusYear != currentYear) {
					AttendanceSlipNumber attendanceSlipNumberBo = LastSlipNumberHandler
							.getInstance().getLastSlipNumberYear(currentYear);
					/**
					 * Checking for duplicate entry. And add the appropriate
					 * error message
					 */
					if (attendanceSlipNumberBo != null) {
						if (attendanceSlipNumberBo.getIsActive()) {
							errors
									.add(
											CMSConstants.ERROR,
											new ActionError(
													CMSConstants.ATTENDANCE_SLIPNUMBER_EXISTS));
							saveErrors(request, errors);
							request.setAttribute(CMSConstants.OPERATION,
									CMSConstants.EDIT_OPERATION);
							assignListToForm(lastSlipNumberForm);
							setFinancialYearList(lastSlipNumberForm);

						}
						if (!attendanceSlipNumberBo.getIsActive()) {
							errors
									.add(
											"error",
											new ActionError("knowledgepro.attendance.alreadyexist.reactivate"));
							saveErrors(request, errors);
							assignListToForm(lastSlipNumberForm);
							setFinancialYearList(lastSlipNumberForm);
						}
					} else {
						isUpdated = LastSlipNumberHandler.getInstance()
								.updateLastSlipNumber(lastSlipNumberForm);

						// If update is successful then add the success message
						// else show the error message

						if (isUpdated) {
							messages
									.add(
											CMSConstants.MESSAGES,
											new ActionMessage(
													CMSConstants.ATTENDANCE_SLIPNUMBER_UPDATE_SUCCESS));
							saveMessages(request, messages);
							lastSlipNumberForm.clear();
							assignListToForm(lastSlipNumberForm);
							setFinancialYearList(lastSlipNumberForm);
						}
						if (!isUpdated) {
							errors
									.add(
											CMSConstants.ERROR,
											new ActionError(
													CMSConstants.ATTENDANCE_SLIPNUMBER_UPDATE_FAILED));
							saveErrors(request, errors);
							lastSlipNumberForm.clear();
							assignListToForm(lastSlipNumberForm);
							setFinancialYearList(lastSlipNumberForm);
						}
					}

				} else {
					isUpdated = LastSlipNumberHandler.getInstance()
							.updateLastSlipNumber(lastSlipNumberForm);

					// If update is successful then add the success message else
					// show the error message

					if (isUpdated) {
						messages
								.add(
										CMSConstants.MESSAGES,
										new ActionMessage(
												CMSConstants.ATTENDANCE_SLIPNUMBER_UPDATE_SUCCESS));
						saveMessages(request, messages);
						lastSlipNumberForm.clear();
						assignListToForm(lastSlipNumberForm);
						setFinancialYearList(lastSlipNumberForm);
					} else {
						errors
								.add(
										CMSConstants.ERROR,
										new ActionError(
												CMSConstants.ATTENDANCE_SLIPNUMBER_UPDATE_FAILED));
						saveErrors(request, errors);
						lastSlipNumberForm.clear();
						assignListToForm(lastSlipNumberForm);
						setFinancialYearList(lastSlipNumberForm);
					}
				}
			} else {
				request.setAttribute(CMSConstants.OPERATION,
						CMSConstants.EDIT_OPERATION);
				saveErrors(request, errors);
				assignListToForm(lastSlipNumberForm);
				setFinancialYearList(lastSlipNumberForm);
			}
		} catch (Exception e) {
			log.error("Error in updating feeBillNumber");
			String msg = super.handleApplicationException(e);
			lastSlipNumberForm.setErrorMessage(msg);
			lastSlipNumberForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exiting from updateLastSlipNumber of LastSlipNumberAction");
		return mapping.findForward(CMSConstants.LAST_SLIP_NUMBER_DISPLAY);
	}

	/**
	 * @param lastSlipNumberForm
	 * @throws Exception
	 */
	public void assignListToForm(LastSlipNumberForm lastSlipNumberForm)
			throws Exception {

		log.info("starting  assignListToForm of LastSlipNumberAction");
		List<LastSlipNumberTo> slipNumberToList = LastSlipNumberHandler.getInstance().getSlipNumberDetails();
		lastSlipNumberForm.setSlipNumberList(slipNumberToList);
		log.info("Existing of assignListToForm of LastSlipNumberAction");
	}

	/**
	 * @param lastSlipNumberForm
	 * @throws Exception
	 */
	public void setFinancialYearList(LastSlipNumberForm lastSlipNumberForm)
			throws Exception {
		log.info("starting setFinancialYearList of LastSlipNumberAction");
		List<FinancialYearTO> financialYearToList = LastSlipNumberHandler
				.getInstance().getFinancialYear();
		lastSlipNumberForm.setFinancilYearList(financialYearToList);
		log.info("Existing setFinancialYearList of LastSlipNumberAction");
	}
	

}
