package com.kp.cms.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.forms.admin.EducationStreamForm;
import com.kp.cms.handlers.admin.ApplicationFeeHandler;
import com.kp.cms.handlers.admin.EducationStreamHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.EducationStreamTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;

public class EducationStreamAction extends BaseDispatchAction
{
	private static final Log log = LogFactory.getLog(EducationStreamAction.class);
	
	public ActionForward initEducationStreamMaster(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		EducationStreamForm educstreamform=(EducationStreamForm) form;
		HttpSession session=request.getSession();
		ActionErrors errors=educstreamform.validate(mapping, request);
		session.setAttribute("field", "appfee");
		try
		{	
			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamform.setEducationStreamList(educstreamList);
			
		}
		catch(Exception e)
		{
			log.error("error occured in education stream action");
			String msg=super.handleApplicationException(e);
			educstreamform.setErrorMessage(msg);
			educstreamform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EDUC_STREAM);
	}
	
	
	
	
	public ActionForward addEducStream(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EducationStreamForm educstreamform=(EducationStreamForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=educstreamform.validate(mapping, request);
		boolean isEducStreamAdded=false;
		
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,educstreamform);
				isEducStreamAdded=EducationStreamHandler.getInstance().addEducStream(educstreamform,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.admin.appfee.name.exists"));
					saveErrors(request, errors);
					
					List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
					educstreamform.setEducationStreamList(educstreamList);
					return mapping.findForward(CMSConstants.EDUC_STREAM);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.admin.educstream.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
					educstreamform.setEducationStreamList(educstreamList);
					return mapping.findForward(CMSConstants.EDUC_STREAM);
				}
				log.error("Error occured in EducationStream Action",e);
				String msg=super.handleApplicationException(e);
				educstreamform.setErrorMessage(msg);
				educstreamform.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			if(isEducStreamAdded)
			{
				ActionMessage message=new ActionMessage("knowledgepro.admin.educstream.addsuccess");
				messages.add("messages",message);
				saveMessages(request, messages);
				educstreamform.reset(mapping, request);
				
				
				List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
				educstreamform.setEducationStreamList(educstreamList);
			}
			//failed to add
			else
			{
				errors.add("error",new ActionError("knowledgepro.admin.educstream.addfailure",educstreamform.getEducstreamName()));
				saveErrors(request, errors);
				
				List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
				educstreamform.setEducationStreamList(educstreamList);
				
			}
		}
		else
		{
			saveErrors(request, errors);

			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamform.setEducationStreamList(educstreamList);
			return mapping.findForward(CMSConstants.EDUC_STREAM);
		}
		return mapping.findForward(CMSConstants.EDUC_STREAM);
	}
	
	
	
	
	
	public ActionForward updateEducStream(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EducationStreamForm educstreamForm=(EducationStreamForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=educstreamForm.validate(mapping, request);
		boolean isEducStreamEdited=false;
		
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,educstreamForm);
				isEducStreamEdited=EducationStreamHandler.getInstance().updateEducStream(educstreamForm,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.admin.educstream.name.exists"));
					saveErrors(request, errors);
					
					
					List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
					educstreamForm.setEducationStreamList(educstreamList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.EDUC_STREAM);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.admin.educstream.addfailure.alreadyexist.reactivate"));
					saveErrors(request, errors);
					
					
					List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
					educstreamForm.setEducationStreamList(educstreamList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.EDUC_STREAM);
				}
				log.error("Error occured in EducationStream Action",e);
				String msg=super.handleApplicationException(e);
				educstreamForm.setErrorMessage(msg);
				educstreamForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else
		{
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");


			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
			return mapping.findForward(CMSConstants.EDUC_STREAM);
		}
		if(isEducStreamEdited)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.educstream.updatesuccess",educstreamForm.getEducstreamName());
			messages.add("messages",message);
			saveMessages(request, messages);
			educstreamForm.reset(mapping, request);
				
				
			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
		}
			//failed to add
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.educstream.updatefailure",educstreamForm.getEducstreamName()));
			saveErrors(request, errors);

			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
		}
		return mapping.findForward(CMSConstants.EDUC_STREAM);
	}
	
	
	public ActionForward deleteEducStream(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EducationStreamForm educstreamForm=(EducationStreamForm) form;
		int educstreamId=educstreamForm.getEducstreamId();
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		boolean isEducStreamDeleted;
		try
		{
			setUserId(request, educstreamForm);
			isEducStreamDeleted=EducationStreamHandler.getInstance().deleteEducStream(educstreamId,educstreamForm.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in Application Fee Action", e);
			String msg=super.handleApplicationException(e);
			educstreamForm.setErrorMessage(msg);
			educstreamForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		if(isEducStreamDeleted)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.educstream.deletesuccess");
			messages.add("messages",message);
			saveMessages(request, messages);
			educstreamForm.reset(mapping, request);

			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.educstream.deletefailure"));
			saveErrors(request, errors);

			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
			
		}
		return mapping.findForward(CMSConstants.EDUC_STREAM);
		
	}
	
	
	
	
	public ActionForward reactivateEducStream(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		EducationStreamForm educstreamForm=(EducationStreamForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		EducationStream educstream=(EducationStream)request.getSession().getAttribute("educstream");
		boolean isReActiveEducStream=false;
		try
		{
			setUserId(request, educstreamForm);
			isReActiveEducStream=EducationStreamHandler.getInstance().reActivateEducStream(educstream,educstreamForm.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in caste Entry Action",e);
			String msg=super.handleApplicationException(e);
			educstreamForm.setErrorMessage(msg);
			educstreamForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isReActiveEducStream)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.educstream.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
			educstreamForm.reset(mapping, request);
			
			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.educstream.activatefailure"));
			saveErrors(request, errors);

			List<EducationStreamTO> educstreamList=EducationStreamHandler.getInstance().getEducStreams();
			educstreamForm.setEducationStreamList(educstreamList);
		}
		request.removeAttribute("educstream");
		return mapping.findForward(CMSConstants.EDUC_STREAM);
	}
	
	

}







	
	
	
