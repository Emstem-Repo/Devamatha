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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ConsolidatedSubjectStreamForm;
import com.kp.cms.handlers.exam.ConsolidatedSubjectStreamHandler;

@SuppressWarnings("deprecation")
public class ConsolidatedSubjectStreamAction extends BaseDispatchAction  
{
	public ActionForward initConsolidatedSubjectStream(ActionMapping mapping,
			  										   ActionForm form,
			  										   HttpServletRequest request,
			  										   HttpServletResponse response) throws Exception
	{
		ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm = (ConsolidatedSubjectStreamForm) form;
		try
		{
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
			return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
		}
		catch(Exception ex)
		{
			String msg = super.handleApplicationException(ex);
			consolidatedSubjectStreamForm.setErrorMessage(msg);
			consolidatedSubjectStreamForm.setErrorStack(ex.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	public ActionForward submitConsolidatedSubjectStream(ActionMapping mapping,
			  									  		 ActionForm form,
			  									  		 HttpServletRequest request,
			  									  		 HttpServletResponse response) throws Exception
	{
		ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm = (ConsolidatedSubjectStreamForm) form;
		ActionErrors errors = consolidatedSubjectStreamForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		
		try
		{
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);				
				consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
				if(consolidatedSubjectStreamForm.getStreamName().trim().equals("")) {
					consolidatedSubjectStreamForm.setStreamName(null);
				}
				return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
			}
			setUserId(request, consolidatedSubjectStreamForm);
			isAdded = ConsolidatedSubjectStreamHandler.getInstance().addSubjectStream(consolidatedSubjectStreamForm, "Add");
			
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
		}
		catch(DuplicateException ex)
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.exists"));
			saveErrors(request, errors);			
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
			return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
		}
		catch (ReActivateException e1) 
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.reactivate"));
			saveErrors(request, errors);			
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
			return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
		}
		catch(Exception ex)
		{
			if (ex instanceof BusinessException) 
			{
				String msgKey = super.handleBusinessException(ex);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			else if (ex instanceof ApplicationException)
			{
				String msg = super.handleApplicationException(ex);
				consolidatedSubjectStreamForm.setErrorMessage(msg);
				consolidatedSubjectStreamForm.setErrorStack(ex.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			else 
			{
				throw ex;
			}
		}
		
		if (isAdded) {
			ActionMessage message = new ActionMessage("knowledgepro.consolidatedSubjectStream.addSucess",consolidatedSubjectStreamForm.getStreamName());
			messages.add("messages", message);
			saveMessages(request, messages);
			consolidatedSubjectStreamForm.reset(mapping, request);
		}
		else
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.addFailure",consolidatedSubjectStreamForm.getStreamName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
	}
	
	public ActionForward updateConsolidatedSubjectStream(ActionMapping mapping,
												  		 ActionForm form,
												  		 HttpServletRequest request,
												  		 HttpServletResponse response) throws Exception
	{
		ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm = (ConsolidatedSubjectStreamForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = consolidatedSubjectStreamForm.validate(mapping, request);
		boolean isUpdated = false;
		try 
		{
			if (!errors.isEmpty()) 
			{
				saveErrors(request, errors);				
				consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
				if((consolidatedSubjectStreamForm.getStreamName().trim()).isEmpty()){       
					consolidatedSubjectStreamForm.setStreamName(null);
				}
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
			}
			
			setUserId(request, consolidatedSubjectStreamForm);
			isUpdated = ConsolidatedSubjectStreamHandler.getInstance().addSubjectStream(consolidatedSubjectStreamForm, "Edit");
		} 
		catch (DuplicateException e1) 
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.exists"));
			saveErrors(request, errors);
			
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
		}
		catch (ReActivateException e1) 
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.reactivate"));
			saveErrors(request, errors);			
			consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
		}
		catch (Exception e) 
		{
			if (e instanceof BusinessException)
			{
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				consolidatedSubjectStreamForm.setErrorMessage(msg);
				consolidatedSubjectStreamForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			else
			{
				throw e;
			}
		}
	
		consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
		if (isUpdated) {
			ActionMessage message = new ActionMessage("knowledgepro.consolidatedSubjectStream.updateSucess", consolidatedSubjectStreamForm.getStreamName());
			messages.add("messages", message);
			saveMessages(request, messages);
			consolidatedSubjectStreamForm.reset(mapping, request);
		} 
		else {
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.updateFailure", consolidatedSubjectStreamForm.getStreamName()));
			saveErrors(request, errors);
		}
		request.setAttribute("admOperation", "add");
		return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
	}
	
	public ActionForward deleteConsolidatedSubjectStream(ActionMapping mapping,
			  											 ActionForm form,
			  											 HttpServletRequest request,
			  											 HttpServletResponse response) throws Exception
	{
		ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm = (ConsolidatedSubjectStreamForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try 
		{
			if (consolidatedSubjectStreamForm.getId() != 0) 
			{
				int dupId = consolidatedSubjectStreamForm.getId();
				setUserId(request, consolidatedSubjectStreamForm);
				isDeleted = ConsolidatedSubjectStreamHandler.getInstance().deleteSubjectStream(dupId, false, consolidatedSubjectStreamForm);
			}
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
				consolidatedSubjectStreamForm.setErrorMessage(msg);
				consolidatedSubjectStreamForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			else 
			{
				throw e;
			}
		}
		consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
		if (isDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.consolidatedSubjectStream.deleteSucess", consolidatedSubjectStreamForm.getStreamName());
			messages.add("messages", message);
			saveMessages(request, messages);
			consolidatedSubjectStreamForm.reset(mapping, request);
		} else {
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.deleteFailure", consolidatedSubjectStreamForm.getStreamName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);
	}
	
	public ActionForward reActivateConsolidatedSubjectStream(ActionMapping mapping,
			  										  		 ActionForm form,
			  										  		 HttpServletRequest request,
			  										  		 HttpServletResponse response) throws Exception
	{
		ConsolidatedSubjectStreamForm consolidatedSubjectStreamForm = (ConsolidatedSubjectStreamForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try 
		{
			if (consolidatedSubjectStreamForm.getDupId() != 0) 
			{
				int dupId = consolidatedSubjectStreamForm.getDupId();
				setUserId(request, consolidatedSubjectStreamForm);
				isActivated = ConsolidatedSubjectStreamHandler.getInstance().deleteSubjectStream(dupId, true, consolidatedSubjectStreamForm);
			}
		}
		catch (Exception e)
		{
			errors.add("error", new ActionError("knowledgepro.consolidatedSubjectStream.reactivationFailure"));
			saveErrors(request, errors);
		}
		
		consolidatedSubjectStreamForm.setSubjectStreams(ConsolidatedSubjectStreamHandler.getInstance().getSubjectStreams());
		if (isActivated) {
			ActionMessage message = new ActionMessage("knowledgepro.consolidatedSubjectStream.reactivationSucess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.CONSOLIDATED_SUBJECT_STREAM_MASTER);		
	}
}
