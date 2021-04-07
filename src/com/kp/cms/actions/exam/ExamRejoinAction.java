package com.kp.cms.actions.exam;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamRejoinForm;
import com.kp.cms.handlers.exam.ExamRejoinHandler;

public class ExamRejoinAction extends BaseDispatchAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamRejoinHandler handler = new ExamRejoinHandler();

	public ActionForward initRejoin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//ExamRejoinForm objform = (ExamRejoinForm) form;
		return mapping.findForward(CMSConstants.EXAM_REJOIN);
	}

	public ActionForward ExamRejoin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamRejoinForm objform = (ExamRejoinForm) form;
		errors.clear();
		messages.clear();
		errors = objform.validate(mapping, request);
		saveErrors(request, errors);
		setUserId(request, objform);
		if (errors.isEmpty()) {
			if (objform.getRegNumber().length() > 0
					|| objform.getRollNumber().length() > 0) {
				String oldRegno = objform.getRegNumber().trim();
				String oldRollNo = objform.getRollNumber().trim();
				String regno = objform.getNewRegNumber().trim();
				String rollNo = objform.getNewRollNumber().trim();
				try {
					 handler.add(objform.getRegNumber().trim(),
							objform.getRollNumber().trim(), regno, rollNo,
							objform.getJoiningBatch(), Integer.parseInt(objform
									.getReadmittedClass()), objform
									.getJoiningDate(), objform.getUserId());
					ActionMessage message = new ActionMessage(
							"knowledgepro.exam.reJoin.rejoinAdd", "");
					messages.add("messages", message);
					saveMessages(request, messages);
					objform.clearPage();
				} catch (DuplicateException e) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.reJoin.exists", rollNo, regno));
					saveErrors(request, errors);
				} catch (ReActivateException e1) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.reJoin.rollNoReg.notexists", e1
									.getMessage()));
					saveErrors(request, errors);
				} catch (BusinessException e1) {
					errors.add("error", new ActionError(
							"knowledgepro.exam.reJoin.rollNoReg.notMatch",
							oldRollNo, oldRegno));
					saveErrors(request, errors);
				}

			} else {
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.reJoin.registerNo.requred", "");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EXAM_REJOIN);
			}

		}
		return mapping.findForward(CMSConstants.EXAM_REJOIN);

	}

}
