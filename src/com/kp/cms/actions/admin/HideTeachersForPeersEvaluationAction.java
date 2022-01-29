package com.kp.cms.actions.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm;
import com.kp.cms.handlers.admin.HideTeachersForPeersEvaluationHandler;
import com.kp.cms.handlers.admin.PeersEvaluationOpenSessionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.HideTeachersForPeersEvaluationTo;

public class HideTeachersForPeersEvaluationAction extends BaseDispatchAction{
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHideTeachers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HideTeachersForPeersEvaluationForm hideTeacherform = (HideTeachersForPeersEvaluationForm) form;
		setUserId(request, hideTeacherform);
		try {
			hideTeacherform.setDepartmentId(null);
			hideTeacherform.setTeachersMap(null);
			hideTeacherform.setHiddenTeachersList(null);
			List<DepartmentEntryTO> departmentTO = PeersEvaluationOpenSessionHandler
					.getInstance().getDepartmentList();
			hideTeacherform.setDepartmentList(departmentTO);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hideTeacherform.setErrorMessage(msg);
			hideTeacherform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.INIT_HIDE_TEACHERS_PEERS_EVALUATION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchTeacherByDepartmentWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		HideTeachersForPeersEvaluationForm hideTeacherform = (HideTeachersForPeersEvaluationForm) form;
		ActionErrors errors = new ActionErrors();
		setUserId(request, hideTeacherform);
		try {
			if(hideTeacherform.getDepartmentId()==null || hideTeacherform.getDepartmentId().isEmpty()){
				errors .add( "error", new ActionError("knowledgepro.employee.validate.department"));
				saveErrors(request, errors);
				return mapping .findForward(CMSConstants.INIT_HIDE_TEACHERS_PEERS_EVALUATION);
			}
			setDataToForm(hideTeacherform);
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hideTeacherform.setErrorMessage(msg);
			hideTeacherform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping .findForward(CMSConstants.INIT_HIDE_TEACHERS_PEERS_EVALUATION);
	}

	/**
	 * @param hideTeacherform
	 */
	private void setHiddenTeachersList( HideTeachersForPeersEvaluationForm hideTeacherform) throws Exception {
		List<HideTeachersForPeersEvaluationTo> hiddenTeachersList = HideTeachersForPeersEvaluationHandler
				.getInstance().getHiddenTeachers(hideTeacherform);
		hideTeacherform.setHiddenTeachersList(hiddenTeachersList);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward hideTeacherForPeersEvaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HideTeachersForPeersEvaluationForm hideTeacherform = (HideTeachersForPeersEvaluationForm) form;
		setUserId(request, hideTeacherform);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isAdded = false;
		try {
			boolean isDuplicate = HideTeachersForPeersEvaluationHandler
					.getInstance().duplicateCheck(hideTeacherform);
			if (!isDuplicate) {
				isAdded = HideTeachersForPeersEvaluationHandler.getInstance()
						.hideTeacherForPeersEvaluation(hideTeacherform);
				if (isAdded) {
					ActionMessage message = new ActionMessage( "knowledgepro.admin.hideTeachers.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				} else {
					errors .add( "error", new ActionError("knowledgepro.admin.hideTeachers.failure"));
					saveErrors(request, errors);
				}
			} else {
				errors .add( "error", new ActionError("knowledgepro.admin.hideTeachers.already.exists"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			hideTeacherform.setErrorMessage(msg);
			hideTeacherform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToForm(hideTeacherform);
		return mapping .findForward(CMSConstants.INIT_HIDE_TEACHERS_PEERS_EVALUATION);
	}
	/**
	 * @param hideTeacherform
	 * @throws Exception 
	 */
	private void setDataToForm( HideTeachersForPeersEvaluationForm hideTeacherform) throws Exception {
		hideTeacherform.setTeachersMap(null);
		int departmentId = Integer.parseInt(hideTeacherform.getDepartmentId()
				.trim());
		String departmentName = HideTeachersForPeersEvaluationHandler.getInstance().getDepartmentName(departmentId);
		hideTeacherform.setDeptartmentName(departmentName);
		// The below map contains key as userid and value as name of Employee.
		Map<Integer, String> teachersMap = CommonAjaxHandler.getInstance().getFacultyByDepartment(
				departmentId);
		/*
		 * getting hidden teachers list from the HideTeachersForPeersEvaluation  bo.
		 */
		setHiddenTeachersList(hideTeacherform);
		teachersMap = HideTeachersForPeersEvaluationHandler.getInstance()
				.iterateTeachersMap(hideTeacherform, teachersMap);
		hideTeacherform.setTeachersMap(teachersMap);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteHiddenTeachers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		HideTeachersForPeersEvaluationForm hideTeacherform = (HideTeachersForPeersEvaluationForm) form;
		setUserId(request, hideTeacherform);
		ActionMessages messages = new ActionMessages();
	    ActionErrors errors = new ActionErrors();
		boolean isdeleted = false;
		try{
			isdeleted = HideTeachersForPeersEvaluationHandler.getInstance().deleteHiddenTeachers(hideTeacherform);
			if(isdeleted){
				ActionMessage message = new ActionError("knowledgepro.admin.hideTeachers.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors .add( "error", new ActionError( "knowledgepro.admin.hideTeachers.deletefailure"));
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			hideTeacherform.setErrorMessage(msg);
			hideTeacherform.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setDataToForm(hideTeacherform);
		return mapping .findForward(CMSConstants.INIT_HIDE_TEACHERS_PEERS_EVALUATION);
	}
}
