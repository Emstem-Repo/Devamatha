package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EvaStudentFeedbackOpenConnectionForm;
import com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm;
import com.kp.cms.handlers.admin.EvaStudentFeedbackOpenConnectionHandler;
import com.kp.cms.handlers.admin.PeersEvaluationOpenSessionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationOpenSessionTo;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class PeersEvaluationOpenSessionAction extends BaseDispatchAction {
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPeersEvaluationOpenSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		peersEvaluationOpenSessionForm.clear(mapping,request);
		setUserId(request, peersEvaluationOpenSessionForm);
		try{
			setDepartmentListToRequest(peersEvaluationOpenSessionForm);
			setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
			setSessionListToRequest(request,peersEvaluationOpenSessionForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}

	/**
	 * @param request
	 * @param peersEvaluationOpenSessionForm
	 * @throws Exception
	 */
	private void setSessionListToRequest(HttpServletRequest request, PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		Map<Integer, String> sessionMap;
		try {
			if (peersEvaluationOpenSessionForm.getAcademicYear() == null || peersEvaluationOpenSessionForm.getAcademicYear().isEmpty()) {
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}
				sessionMap = PeersEvaluationOpenSessionHandler.getInstance().getSessionByYear(currentYear);
				
			} else {
				sessionMap = PeersEvaluationOpenSessionHandler.getInstance().getSessionByYear(Integer.parseInt(peersEvaluationOpenSessionForm.getAcademicYear()));
			}
			request.setAttribute("sessionMap", sessionMap);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
	}

	/**
	 * @param peersEvaluationOpenSessionForm
	 * @throws Exception
	 */
	private void setOpenConnectionListToForm( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		List<PeersEvaluationOpenSessionTo> openSessionTos = PeersEvaluationOpenSessionHandler.getInstance().getOpenConnectionList(peersEvaluationOpenSessionForm);
		peersEvaluationOpenSessionForm.setSessionToList(openSessionTos);
	}

	/**
	 * @param request
	 * @param peersEvaluationOpenSessionForm
	 * @throws Exception
	 */
	private void setDepartmentListToRequest(PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) throws Exception{
		List<DepartmentEntryTO> departmentTO= PeersEvaluationOpenSessionHandler.getInstance().getDepartmentList();
		Collections.sort(departmentTO);
		peersEvaluationOpenSessionForm.setDepartmentTO(departmentTO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOpenSessionDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		setUserId(request, peersEvaluationOpenSessionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = peersEvaluationOpenSessionForm.validate(mapping, request);
		validateStartDate(peersEvaluationOpenSessionForm, errors);
		try{
			if (errors.isEmpty()) {
				setUserId(request, peersEvaluationOpenSessionForm);
				boolean duplicatecheck = PeersEvaluationOpenSessionHandler .getInstance().duplicateCheck(peersEvaluationOpenSessionForm);
				if (!duplicatecheck) {
					boolean isAdded = PeersEvaluationOpenSessionHandler .getInstance().submitOpenConnectionDetails(peersEvaluationOpenSessionForm);
					if (isAdded) {
						ActionMessage message = new ActionError( "knowledgepro.admin.faculty.evaluation.addsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						peersEvaluationOpenSessionForm.clear(mapping, request);
					} else {
						errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.addfailure"));
						saveErrors(request, errors);
					}
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.exists.already"));
					saveErrors(request, errors);
				}
			} else {
				saveErrors(request, errors);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		peersEvaluationOpenSessionForm.setFlag(false);
		setDepartmentListToRequest(peersEvaluationOpenSessionForm);
		setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
		setSessionListToRequest(request,peersEvaluationOpenSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}

	/**
	 * @param peersEvaluationOpenSessionForm
	 * @param errors
	 */
	private void validateStartDate( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm, ActionErrors errors) {
		if (peersEvaluationOpenSessionForm.getStartDate() != null
				&& !StringUtils.isEmpty(peersEvaluationOpenSessionForm.getStartDate())
				&& !CommonUtil.isValidDate(peersEvaluationOpenSessionForm.getStartDate())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(
							CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID)
							.hasNext()) {
				errors
						.add(
								CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,
								new ActionError(
										CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (peersEvaluationOpenSessionForm.getEndDate() != null
				&& !StringUtils.isEmpty(peersEvaluationOpenSessionForm.getEndDate())
				&& !CommonUtil.isValidDate(peersEvaluationOpenSessionForm.getEndDate())) {
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null
					&& !errors.get(
							CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID)
							.hasNext()) {
				errors
						.add(
								CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,
								new ActionError(
										CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if (peersEvaluationOpenSessionForm.getEndDate() != null
				&& !StringUtils.isEmpty(peersEvaluationOpenSessionForm.getEndDate())
				&& CommonUtil.isValidDate(peersEvaluationOpenSessionForm.getEndDate())) {

			if (CommonUtil.checkForEmpty(peersEvaluationOpenSessionForm.getStartDate())
					&& CommonUtil.checkForEmpty(peersEvaluationOpenSessionForm.getEndDate())) {
				Date startDate = CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm
						.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm
						.getEndDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if (daysBetween <= 0) {
					errors.add("error", new ActionError(
							CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));

				}
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
	public ActionForward updatePeersEvaluationOpenSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		setUserId(request, peersEvaluationOpenSessionForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = peersEvaluationOpenSessionForm.validate(mapping, request);
		validateStartDate(peersEvaluationOpenSessionForm, errors);
		try {
			if (errors.isEmpty()) {
				boolean duplicatecheck = PeersEvaluationOpenSessionHandler .getInstance().duplicateCheck(peersEvaluationOpenSessionForm);
				if (!duplicatecheck) {
					boolean isUpdated = PeersEvaluationOpenSessionHandler .getInstance().updatePeersEvaluationOpenSession( peersEvaluationOpenSessionForm);
					if (isUpdated) {
						ActionMessage message = new ActionError( "knowledgepro.admin.faculty.evaluation.updatedsuccess");
						messages.add("messages", message);
						saveMessages(request, messages);
						peersEvaluationOpenSessionForm.clear(mapping,request);
					} else {
						errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.updatedfailure"));
						saveErrors(request, errors);
						request.setAttribute("peersOpenConnection", "edit");
						setDepartmentListToRequest(peersEvaluationOpenSessionForm);
						setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
						setSessionListToRequest(request,peersEvaluationOpenSessionForm);
						return mapping .findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
					}
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.exists.already"));
					saveErrors(request, errors);
					request.setAttribute("peersOpenConnection", "edit");
					setDepartmentListToRequest(peersEvaluationOpenSessionForm);
					setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
					setSessionListToRequest(request,peersEvaluationOpenSessionForm);
					return mapping .findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
				}
			} else {
				saveErrors(request, errors);
				request.setAttribute("peersOpenConnection", "edit");
				setDepartmentListToRequest(peersEvaluationOpenSessionForm);
				setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
				setSessionListToRequest(request,peersEvaluationOpenSessionForm);
				return mapping .findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
			}
		}catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.faculty.evaluation.exists.already"));
			request.setAttribute("peersOpenConnection", "edit");
			saveErrors(request, errors);
			setDepartmentListToRequest(peersEvaluationOpenSessionForm);
			setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
			setSessionListToRequest(request,peersEvaluationOpenSessionForm);
			return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		peersEvaluationOpenSessionForm.setFlag(false);
		request.setAttribute("peersOpenConnection", "add");
		setDepartmentListToRequest(peersEvaluationOpenSessionForm);
		setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
		setSessionListToRequest(request,peersEvaluationOpenSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePeersOpenSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		setUserId(request, peersEvaluationOpenSessionForm);
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		
		try {
			if (errors != null && errors.isEmpty()) {
				boolean isDeleted = PeersEvaluationOpenSessionHandler .getInstance().deleteOpenConnection(peersEvaluationOpenSessionForm);
				if (isDeleted) {
					ActionMessage message = new ActionError( "knowledgepro.admin.faculty.evaluation.deletedsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
				} else {
					errors .add( "error", new ActionError( "knowledgepro.admin.faculty.evaluation.deletedfailure"));
					saveErrors(request, errors);
				}
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		peersEvaluationOpenSessionForm.setFlag(false);
		setDepartmentListToRequest(peersEvaluationOpenSessionForm);
		setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
		setSessionListToRequest(request,peersEvaluationOpenSessionForm);
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSessionByYear(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		try{
			setSessionListToRequest(request,peersEvaluationOpenSessionForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPeersOpenSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm = (PeersEvaluationOpenSessionForm)form;
		try{
			PeersEvaluationOpenSessionHandler.getInstance().editPeersEvaluationOpenSession(peersEvaluationOpenSessionForm);
			peersEvaluationOpenSessionForm.setFlag(true);
			peersEvaluationOpenSessionForm.setSessionId(peersEvaluationOpenSessionForm.getSessionId());
			setDepartmentListToRequest(peersEvaluationOpenSessionForm);
			setOpenConnectionListToForm(peersEvaluationOpenSessionForm);
			setSessionListToRequest(request,peersEvaluationOpenSessionForm);
			request.setAttribute("peersOpenConnection", "edit");
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			peersEvaluationOpenSessionForm.setErrorMessage(msg);
			peersEvaluationOpenSessionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PEERS_EVALUATION_OPEN_SESSION);
	}
}
