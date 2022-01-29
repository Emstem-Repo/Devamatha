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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.UpdatePassFailForCjcForm;
import com.kp.cms.handlers.exam.ExamUpdateProcessHandler;

public class UpdatePassFailForCjcAction extends BaseDispatchAction
{
	public ActionForward initUpdatePassFail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		UpdatePassFailForCjcForm updateForm=(UpdatePassFailForCjcForm)form; 
		try
		{
			updateForm.resetFields();
		}
		catch (Exception e) {
			String msg = super.handleApplicationException(e);
			updateForm.setErrorMessage(msg);
			updateForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL_CJC);
	}
	
	public ActionForward updatePassFail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		UpdatePassFailForCjcForm updateForm=(UpdatePassFailForCjcForm)form; 
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = updateForm.validate(mapping, request);
		boolean isAdded = false;
		try{
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL_CJC);
			}
			ExamUpdateProcessHandler handler = new ExamUpdateProcessHandler();
			isAdded = handler.updatePassorFailForCjc(Integer.parseInt(updateForm.getCourseId()),Integer.parseInt(updateForm.getYear()),Integer.parseInt(updateForm.getClassId()));
		} 
		catch (Exception e) 
		{
			if (e instanceof BusinessException) 
			{
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				updateForm.setErrorMessage(msg);
				updateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		if (isAdded) 
		{
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.exam.pass.fail.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			updateForm.resetFields();
		} else 
		{
			// failed
			errors.add("error", new ActionError("knowledgepro.exam.pass.fail.update.failure"));
			saveErrors(request, errors);
			
		}
		return mapping.findForward(CMSConstants.UPDATE_PASS_FAIL_CJC);
	}
}
