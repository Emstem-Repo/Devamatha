package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.UniversityForm;
import com.kp.cms.handlers.admin.SingleFieldMasterHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.to.admin.DocTypeTO;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admin.UniversityTO;

public class UniversityAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(UniversityAction.class);
	public ActionForward initUniversity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UniversityForm uniForm  = (UniversityForm) form;
		try {
			uniForm.clear();
			setUserId(request, uniForm);
			assignListToForm(uniForm);
		} catch (Exception e) {
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	private void assignListToForm(UniversityForm uniForm)throws Exception
	{
		List<UniversityTO>universityList=UniversityHandler.getInstance().getUniversity();
		uniForm.setUniversityList(universityList);
		List<SingleFieldMasterTO>doctypeList=SingleFieldMasterHandler.getInstance().getsingleFieldMaster("DocType");
		uniForm.setDoctypeList(doctypeList);
		
	}
	
	public ActionForward addUniversityEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		UniversityForm uniForm  = (UniversityForm) form;
		ActionErrors errors=uniForm.validate(mapping, request);
		ActionMessages messages=new ActionMessages();
		try 
		{
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);
				assignListToForm(uniForm);
			}
			else
			{
				boolean isAdded=UniversityHandler.getInstance().addUniversity(uniForm);
				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.University.addsuccess",uniForm.getUniversity());
					messages.add("messages", message);
					saveMessages(request, messages);
					assignListToForm(uniForm);
					uniForm.clear();
				}
				else
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.addfailure",uniForm.getUniversity()));
					saveErrors(request, errors);
					assignListToForm(uniForm);
				}
			}
		}
		catch (DuplicateException e)
		{
			errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.name.exists",uniForm.getUniversity()));
			saveErrors(request, errors);
			assignListToForm(uniForm);
		}
		catch (ReActivateException e) {
			errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.addfailure.alreadyexist.reactivate",uniForm.getId()));
			saveErrors(request, errors);
			assignListToForm(uniForm);
		}
		catch (Exception e) 
		{
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	
	public ActionForward editUniversity(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		UniversityForm uniForm  = (UniversityForm) form;
		try 
		{
			UniversityHandler.getInstance().getUniversityDetails(uniForm);
			request.setAttribute("operation","edit");
		}
		catch (Exception e) 
		{
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	
	public ActionForward updateUniversity(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		UniversityForm uniForm  = (UniversityForm) form;
		ActionErrors errors=uniForm.validate(mapping, request);
		ActionMessages messages=new ActionMessages();
		try 
		{
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);
				assignListToForm(uniForm);
			}
			else
			{
				boolean isupdated=UniversityHandler.getInstance().updateUniversity(uniForm);
				if(isupdated)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.University.updatesuccess",uniForm.getUniversity());
					messages.add("messages", message);
					saveMessages(request, messages);
					assignListToForm(uniForm);
					uniForm.clear();
				}
				else
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.updatefailure",uniForm.getUniversity()));
					saveErrors(request, errors);
					assignListToForm(uniForm);
				}
			}
		}
		catch (DuplicateException e)
		{
			errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.name.exists",uniForm.getUniversity()));
			saveErrors(request, errors);
			assignListToForm(uniForm);
		}
		catch (ReActivateException e) {
			errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.addfailure.alreadyexist.reactivate",uniForm.getId()));
			saveErrors(request, errors);
			assignListToForm(uniForm);
		}
		catch (Exception e) 
		{
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	
	public ActionForward deleteUniversity(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		UniversityForm uniForm  = (UniversityForm) form;
		ActionErrors errors=new ActionErrors();
		ActionMessages messages=new ActionMessages();
		try 
		{
				boolean isDeleted=UniversityHandler.getInstance().deleteOrReactivate(uniForm.getId(),"delete");
				if(isDeleted)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.University.deletesuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignListToForm(uniForm);
					uniForm.clear();
				}
				else
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admin.University.deletefailure"));
					saveErrors(request, errors);
					assignListToForm(uniForm);
				}
		}
		catch (Exception e) 
		{
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	
	public ActionForward reActivateUniversity(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		
		UniversityForm uniForm  = (UniversityForm) form;
		ActionMessages messages=new ActionMessages();
		try 
		{
				boolean isDeleted=UniversityHandler.getInstance().deleteOrReactivate(uniForm.getId(),"reactivate");
				if(isDeleted)
				{
					ActionMessage message = new ActionMessage("knowledgepro.admin.University.activate");
					messages.add("messages", message);
					saveMessages(request, messages);
					assignListToForm(uniForm);
					uniForm.clear();
				}
		}
		catch (Exception e) 
		{
			log.error("error in getProgramType...", e);
			if (e instanceof ApplicationException) 
			{
				String msg = super.handleApplicationException(e);
				uniForm.setErrorMessage(msg);
				uniForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else 
			{
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.UNIVERSITY_ENTRY);
	}
	

}
