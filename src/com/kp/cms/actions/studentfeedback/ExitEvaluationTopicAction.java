package com.kp.cms.actions.studentfeedback;

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
import com.kp.cms.bo.studentfeedback.ExitEvaluationTopic;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.studentfeedback.ExitEvaluationTopicForm;
import com.kp.cms.handlers.studentfeedback.ExitEvaluationTopicHandler;
import com.kp.cms.to.studentfeedback.ExitEvaluationTopicTo;

public class ExitEvaluationTopicAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExitEvaluationTopicAction.class);
	
	public ActionForward initExitEvalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		ExitEvaluationTopicForm admForm=(ExitEvaluationTopicForm) form;
		HttpSession session=request.getSession();
		ActionErrors errors=admForm.validate(mapping, request);
		session.setAttribute("field", "appfee");
		try
		{	
			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
			
		}
		catch(Exception e)
		{
			log.error("error occured in education stream action");
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
	}
	
	public ActionForward addExitEvalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ExitEvaluationTopicForm admForm=(ExitEvaluationTopicForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=admForm.validate(mapping, request);
		boolean isExitEvalTopicAdded=false;
		
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,admForm);
				isExitEvalTopicAdded=ExitEvaluationTopicHandler.getInstance().addExitEvalTopic(admForm,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.studfeedback.exitevaltop.name.exists"));
					saveErrors(request, errors);
					
					List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
					admForm.setExitEvalTopicList(exitEvalTopicList);
					return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.studfeedback.exitevaltop.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
					admForm.setExitEvalTopicList(exitEvalTopicList);
					return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
				}
				log.error("Error occured in EducationStream Action",e);
				String msg=super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			if(isExitEvalTopicAdded)
			{
				ActionMessage message=new ActionMessage("knowledgepro.studfeedback.exitevaltop.addsuccess");
				messages.add("messages",message);
				saveMessages(request, messages);
				admForm.reset(mapping, request);
				
				
				List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
				admForm.setExitEvalTopicList(exitEvalTopicList);
			}
			//failed to add
			else
			{
				errors.add("error",new ActionError("knowledgepro.studfeedback.exitevaltop.addfailure",admForm.getTopicName()));
				saveErrors(request, errors);
				
				List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
				admForm.setExitEvalTopicList(exitEvalTopicList);
				
			}
		}
		else
		{
			saveErrors(request, errors);

			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
			return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
		}
		return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
	}
	
	public ActionForward updateExitEvalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ExitEvaluationTopicForm admForm=(ExitEvaluationTopicForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=admForm.validate(mapping, request);
		boolean isExitEvalTopicEdited=false;
		
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,admForm);
				isExitEvalTopicEdited=ExitEvaluationTopicHandler.getInstance().updateExitEvalTopic(admForm,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.studfeedback.exitevaltop.name.exists"));
					saveErrors(request, errors);
					
					
					List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
					admForm.setExitEvalTopicList(exitEvalTopicList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.studfeedback.exitevaltop.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					
					
					List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
					admForm.setExitEvalTopicList(exitEvalTopicList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
				}
				log.error("Error occured in EducationStream Action",e);
				String msg=super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else
		{
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");


			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
			return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
		}
		if(isExitEvalTopicEdited)
		{
			ActionMessage message=new ActionMessage("knowledgepro.studfeedback.exitevaltop.updatesuccess",admForm.getTopicName());
			messages.add("messages",message);
			saveMessages(request, messages);
			admForm.reset(mapping, request);
				
				
			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
		}
			//failed to add
		else
		{
			errors.add("error",new ActionError("knowledgepro.studfeedback.exitevaltop.updatefailure",admForm.getTopicName()));
			saveErrors(request, errors);

			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
		}
		return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
	}
	
	public ActionForward deleteExitEvalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ExitEvaluationTopicForm admForm=(ExitEvaluationTopicForm) form;
		int exitEvalTopicId=admForm.getExitEvalTopicId();
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		boolean isExitEvalTopicDeleted;
		try
		{
			setUserId(request, admForm);
			isExitEvalTopicDeleted=ExitEvaluationTopicHandler.getInstance().deleteExitEvalTopic(exitEvalTopicId,admForm.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in Application Fee Action", e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		if(isExitEvalTopicDeleted)
		{
			ActionMessage message=new ActionMessage("knowledgepro.studfeedback.exitevaltop.deletesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			admForm.reset(mapping, request);

			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.studfeedback.exitevaltop.deletefailure"));
			saveErrors(request, errors);

			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
			
		}
		return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
		
	}
	
	public ActionForward reactivateExitEvalTopic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ExitEvaluationTopicForm admForm=(ExitEvaluationTopicForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		ExitEvaluationTopic exitEvalTopic=(ExitEvaluationTopic)request.getSession().getAttribute("exitEvalTopic");
		boolean isReActiveExitEvalTopic=false;
		try
		{
			setUserId(request, admForm);
			isReActiveExitEvalTopic=ExitEvaluationTopicHandler.getInstance().reActivateExitEvalTopic(exitEvalTopic,admForm.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in caste Entry Action",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isReActiveExitEvalTopic)
		{
			ActionMessage message=new ActionMessage("knowledgepro.studfeedback.exitevaltop.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
			admForm.reset(mapping, request);
			
			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.studfeedback.exitevaltop.activatefailure"));
			saveErrors(request, errors);

			List<ExitEvaluationTopicTo> exitEvalTopicList=ExitEvaluationTopicHandler.getInstance().getExitEvalTopics();
			admForm.setExitEvalTopicList(exitEvalTopicList);
		}
		request.removeAttribute("exitEvalTopic");
		return mapping.findForward(CMSConstants.EXIT_EVAL_TOPIC);
	}

}
