package com.kp.cms.actions.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.WorkDiaryForm;
import com.kp.cms.handlers.employee.WorkDiaryHandler;
import com.kp.cms.handlers.employee.WorkTimeEntryHandler;
import com.kp.cms.to.employee.EmpWorkTimeTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class WorkDiaryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(WorkDiaryAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initWorkDiary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkDiaryForm workDiaryForm = (WorkDiaryForm) form;
		initFields(workDiaryForm);
		return mapping.findForward(CMSConstants.WORK_DIARY_FIRST_PAGE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitWorkDiaryMainPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Entering initWorkDiary");
		WorkDiaryForm workDiaryForm = (WorkDiaryForm) form;
		 ActionErrors errors = workDiaryForm.validate(mapping, request);
		boolean isValidStartDate;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WORK_DIARY_FIRST_PAGE);
			}
			isValidStartDate = CommonUtil.isValidDate(workDiaryForm.getDate());
			if (!isValidStartDate) {
				errors
						.add(
								CMSConstants.ERROR,
								new ActionError(
										CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WORK_DIARY_FIRST_PAGE);
			}
			String formName = mapping.getName();
			request.getSession().setAttribute(CMSConstants.FORMNAME, formName);
			// setWorkTypeListToRequest(request);
		} catch (Exception e) {
			log.error("error initWorkDiary...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workDiaryForm.setErrorMessage(msg);
				workDiaryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.WORK_DIARY_SEC_PAGE);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request0
	 * @param response
	 *            This will add new education level
	 * @return to mapping WORK_DIARY_SEC_PAGE
	 * @throws Exception
	 */
	public ActionForward addWorkDiary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.debug("inside addWorkDiary Action");
		WorkDiaryForm workDiaryForm = (WorkDiaryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = workDiaryForm.validate(mapping, request);
		boolean isAdded = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.WORK_DIARY_SEC_PAGE);
			}
			setUserId(request, workDiaryForm);
			isAdded = WorkDiaryHandler.getInstance().addWorkDiary(
					workDiaryForm, "add");

		} catch (Exception e) {
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				workDiaryForm.setErrorMessage(msg);
				workDiaryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if (isAdded) {
			// success .
			ActionMessage message = new ActionMessage(
					"knowledgepro.employee.work.diary.addsuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			initFields(workDiaryForm);
		} else {
			// failed
			errors.add("error", new ActionError(
					"knowledgepro.employee.work.diary.addfailure"));
			saveErrors(request, errors);
		}
		log.debug("Leaving addWorkDiary Action");
		return mapping.findForward(CMSConstants.WORK_DIARY_FIRST_PAGE);
	}

	
	public void initFields(WorkDiaryForm diaryForm) {
		diaryForm.setDate(null);
		diaryForm.setComments(null);
		diaryForm.setThefile(null);
	}

}
