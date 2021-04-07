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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.handlers.admin.ApplicationFeeHandler;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.sun.org.apache.xalan.internal.xsltc.runtime.ErrorMessages;

public class ApplicationFeeAction extends BaseDispatchAction 
{
	private static final Log log = LogFactory.getLog(CasteAction.class);
	public ActionForward initApplicationFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{
		ApplicationFeeForm appfeeform=(ApplicationFeeForm) form;
		HttpSession session=request.getSession();
		ActionErrors errors=appfeeform.validate(mapping, request);
		session.setAttribute("field", "appfee");
		try
		{
			setAppFeeListToRequest(request);
			
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appfeeform.setAppfeeList(appfeeList);
			
		}
		catch(Exception e)
		{
			log.error("error occured in apllication fee action");
			String msg=super.handleApplicationException(e);
			appfeeform.setErrorMessage(msg);
			appfeeform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.APP_FEE);
	}
	
	public ActionForward addCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ApplicationFeeForm appFeeForm=(ApplicationFeeForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=appFeeForm.validate(mapping, request);
		boolean isAppFeeAdded=false;
		boolean isAmountNumeric=StringUtils.isNumeric(appFeeForm.getAmount());
		if(!isAmountNumeric)
		{
			errors.add("error",new ActionError("knowledgepro.admin.appfee.amount.numeric.required"));
			saveErrors(request, errors);
			
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
		}
		
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,appFeeForm);
				isAppFeeAdded=ApplicationFeeHandler.getInstance().addAppFee(appFeeForm,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.admin.appfee.name.exists"));
					saveErrors(request, errors);
					setAppFeeListToRequest(request);
					List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
					appFeeForm.setAppfeeList(appfeeList);
					return mapping.findForward(CMSConstants.APP_FEE);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.admin.appfee.addfailure.alreadyexist.reactivate",appFeeForm));
					saveErrors(request, errors);
					setAppFeeListToRequest(request);
					List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
					appFeeForm.setAppfeeList(appfeeList);
					return mapping.findForward(CMSConstants.APP_FEE);
				}
				log.error("Error occured in ApplicationFee Action",e);
				String msg=super.handleApplicationException(e);
				appFeeForm.setErrorMessage(msg);
				appFeeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			if(isAppFeeAdded)
			{
				ActionMessage message=new ActionMessage("knowledgepro.admin.appfee.addsuccess");
				messages.add("messages",message);
				saveMessages(request, messages);
				appFeeForm.reset(mapping, request);
				
				
				setAppFeeListToRequest(request);
				List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
				appFeeForm.setAppfeeList(appfeeList);
			}
			//failed to add
			else
			{
				errors.add("error",new ActionError("knowledgepro.admin.appfee.addfailure",appFeeForm.getAppName()));
				saveErrors(request, errors);
				
				setAppFeeListToRequest(request);
				List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
				appFeeForm.setAppfeeList(appfeeList);
				
			}
		}
		else
		{
			saveErrors(request, errors);
			
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
			return mapping.findForward(CMSConstants.APP_FEE);
		}
		return mapping.findForward(CMSConstants.APP_FEE);
	}
	
	
	
	public ActionForward updateCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ApplicationFeeForm appFeeForm=(ApplicationFeeForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=appFeeForm.validate(mapping, request);
		boolean isAppFeeEdited=false;
		boolean isAmountNumeric=StringUtils.isNumeric(appFeeForm.getAmount());
		if(!isAmountNumeric)
		{
			errors.add("error",new ActionError("knowledgepro.admin.appfee.amount.numeric.required"));
			saveErrors(request, errors);
			
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
		}
		if(errors.isEmpty())
		{
			try
			{
				setUserId(request,appFeeForm);
				isAppFeeEdited=ApplicationFeeHandler.getInstance().updateAppFee(appFeeForm,request);
			}
			catch(Exception e)
			{
				if(e instanceof DuplicateException)
				{
					errors.add("error", new ActionError("knowledgepro.admin.appfee.name.exists",appFeeForm));
					saveErrors(request, errors);
					setAppFeeListToRequest(request);
					List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
					appFeeForm.setAppfeeList(appfeeList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.APP_FEE);
				}
				if(e instanceof ReActivateException )
				{
					errors.add("error",  new ActionError("knowledgepro.admin.appfee.addfailure.alreadyexist.reactivate",appFeeForm));
					saveErrors(request, errors);
					setAppFeeListToRequest(request);
					List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
					appFeeForm.setAppfeeList(appfeeList);
					request.setAttribute("operation", CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.APP_FEE);
				}
				log.error("Error occured in ApplicationFee Action",e);
				String msg=super.handleApplicationException(e);
				appFeeForm.setErrorMessage(msg);
				appFeeForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		else
		{
			saveErrors(request, errors);
			request.setAttribute("operation", "edit");
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
			return mapping.findForward(CMSConstants.APP_FEE);
		}
		if(isAppFeeEdited)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.appfee.updatesuccess",appFeeForm.getAppName());
			messages.add("messages",message);
			saveMessages(request, messages);
			appFeeForm.reset(mapping, request);
				
				
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
		}
			//failed to add
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.appfee.updatefailure",appFeeForm.getAppName()));
			saveErrors(request, errors);
				
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList = ApplicationFeeHandler.getInstance().getAppFees();
			appFeeForm.setAppfeeList(appfeeList);
		}
		return mapping.findForward(CMSConstants.APP_FEE);
	}
	
	
	public ActionForward deleteCaste(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ApplicationFeeForm appfeeform=(ApplicationFeeForm) form;
		int appfeeId=appfeeform.getAppfeeId();
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		boolean isAppFeeDeleted;
		try
		{
			setUserId(request, appfeeform);
			isAppFeeDeleted=ApplicationFeeHandler.getInstance().deleteAppFee(appfeeId,appfeeform.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in Application Fee Action", e);
			String msg=super.handleApplicationException(e);
			appfeeform.setErrorMessage(msg);
			appfeeform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		if(isAppFeeDeleted)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.AppFee.deletesuccess",appfeeform);
			messages.add("messages",message);
			saveMessages(request, messages);
			appfeeform.reset(mapping, request);
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appfeeform.setAppfeeList(appfeeList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.AppFee.deletefailure",appfeeform));
			saveErrors(request, errors);
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appfeeform.setAppfeeList(appfeeList);
			
		}
		return mapping.findForward(CMSConstants.APP_FEE);
		
	}
	
	public ActionForward reactivateAppFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		ApplicationFeeForm appfeeform=(ApplicationFeeForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=new ActionErrors();
		ApplicationFee appfee=(ApplicationFee)request.getSession().getAttribute("appFee");
		boolean isReActiveAppFee=false;
		try
		{
			setUserId(request, appfeeform);
			isReActiveAppFee=ApplicationFeeHandler.getInstance().reActivateAppFee(appfee,appfeeform.getUserId());
		}
		catch(Exception e)
		{
			log.error("Error occured in caste Entry Action",e);
			String msg=super.handleApplicationException(e);
			appfeeform.setErrorMessage(msg);
			appfeeform.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(isReActiveAppFee)
		{
			ActionMessage message=new ActionMessage("knowledgepro.admin.AppFee.activate");
			messages.add("messages",message);
			saveMessages(request, messages);
			appfeeform.reset(mapping, request);
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appfeeform.setAppfeeList(appfeeList);
		}
		else
		{
			errors.add("error",new ActionError("knowledgepro.admin.AppFee.activatefailure"));
			saveErrors(request, errors);
			
			setAppFeeListToRequest(request);
			List<ApplicationFeeTO> appfeeList=ApplicationFeeHandler.getInstance().getAppFees();
			appfeeform.setAppfeeList(appfeeList);
		}
		request.removeAttribute("appfee");
		return mapping.findForward(CMSConstants.APP_FEE);
	}
	
	
	
	public void setAppFeeListToRequest(HttpServletRequest request) throws Exception
	{
		List<ReligionSectionTO> subrelegionList=SubReligionHandler.getInstance().getSubReligion();
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("subrelegionList", subrelegionList);
		request.setAttribute("programtypeList", programTypeList);
		log.debug("leaving setCasteListToRequest in Action");
		
	}
	
	


}
