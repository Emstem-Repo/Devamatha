package com.kp.cms.actions.fee;

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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.HonorsEntryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.fee.HonorsEntryHandler;
import com.kp.cms.to.admin.ProgramTO;

public class HonorsEntryAction extends BaseDispatchAction
{
	public ActionForward initHonorsEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		HonorsEntryForm entryForm=(HonorsEntryForm)form;
		try 
		{
			List<ProgramTO> programList=HonorsEntryHandler.getInstance().getProgram();
			entryForm.setProgramList(programList);
		}
		catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			entryForm.setErrorMessage(msg);
			entryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.HONORS_ENTRY);
	}
	
	public ActionForward updateHonorsEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		HonorsEntryForm entryForm=(HonorsEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = entryForm.validate(mapping, request);
		boolean isAdded=false,isDeleted=false;
		try 
		{
			setUserId(request, entryForm);
			if (errors != null && !errors.isEmpty()) 
			{
				saveErrors(request, errors);
				retainDropDowns(request,entryForm);
				return mapping.findForward(CMSConstants.HONORS_ENTRY);
			}
			Student student=HonorsEntryHandler.getInstance().getStudent(entryForm);
			if(student!=null)
			{	
				if(entryForm.getAdd().equalsIgnoreCase("Add")){
				isAdded = HonorsEntryHandler.getInstance().updateHonorsEntry(entryForm,student);
					if (isAdded) 
					{
						// success .
						ActionMessage message = new ActionMessage("knowledgepro.fee.honors.entry.added.success",entryForm.getRegNo(),entryForm.getSemister());
						messages.add("messages", message);
						saveMessages(request, messages);
						entryForm.reset(mapping, request);
					} 
					else 
					{
						// failed
						errors.add("error", new ActionError("knowledgepro.fee.honors.entry.add.failure",entryForm.getRegNo(),entryForm.getSemister()));
						saveErrors(request, errors);
						retainDropDowns(request,entryForm);
						return mapping.findForward(CMSConstants.HONORS_ENTRY);
					}
				}else{
					isDeleted = HonorsEntryHandler.getInstance().deleteHonorsEntry(entryForm,student);
						if (isDeleted) 
						{
							// success .
							ActionMessage message = new ActionMessage("knowledgepro.fee.honors.entry.delete.success",entryForm.getRegNo(),entryForm.getSemister());
							messages.add("messages", message);
							saveMessages(request, messages);
							entryForm.reset(mapping, request);
						} 
						else 
						{
							// failed
							errors.add("error", new ActionError("knowledgepro.fee.No.honors.entry"));
							saveErrors(request, errors);
							retainDropDowns(request,entryForm);
							return mapping.findForward(CMSConstants.HONORS_ENTRY);
						}
				}
			}
			else
			{
				errors.add("error", new ActionError("knowledgepro.hostel.disciplinary.details.invalidRegisterNo"));
				saveErrors(request, errors);
				retainDropDowns(request,entryForm);
				return mapping.findForward(CMSConstants.HONORS_ENTRY);
			}
		} 
		catch (DuplicateException e1) 
		{
			errors.add("error", new ActionError("knowledgepro.fee.honors.entry.duplicate.error",entryForm.getRegNo(),entryForm.getSemister()));
			saveErrors(request, errors);
			retainDropDowns(request,entryForm);
			return mapping.findForward(CMSConstants.HONORS_ENTRY);
		} 
		catch (ReActivateException e2) 
		{
			errors.add("error", new ActionError("knowledgepro.fee.honors.entry.reActivate.error",e2.getID()));
			saveErrors(request, errors);
			retainDropDowns(request,entryForm);
			return mapping.findForward(CMSConstants.HONORS_ENTRY);
		} 
		catch (BillGenerationException e3) 
		{
			errors.add("error", new ActionError("knowledgepro.fee.honors.entry.delete.fee.already"));
			saveErrors(request, errors);
			retainDropDowns(request,entryForm);
			return mapping.findForward(CMSConstants.HONORS_ENTRY);
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
				entryForm.setErrorMessage(msg);
				entryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} 
			else 
			{
				throw e;
			}
		}

		return mapping.findForward(CMSConstants.HONORS_ENTRY);

	}

	private void retainDropDowns(HttpServletRequest request,HonorsEntryForm entryForm) 
	{
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		Map<Integer,Integer> semisterMap = new HashMap<Integer,Integer>();
		if(entryForm.getProgramId().length() != 0) {
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(entryForm.getProgramId()));
		}	
		if(entryForm.getAcademicYear().length() != 0 && entryForm.getCourseId().length() != 0) {
			semisterMap = CommonAjaxHandler.getInstance().getSemistersByYearAndCourse(Integer.parseInt(entryForm.getAcademicYear()), Integer.parseInt(entryForm.getCourseId()));
		}
		request.setAttribute("courseMap", courseMap);
		request.setAttribute("semistersMap", semisterMap);
	}
	
	public ActionForward reActivateHonorsEntry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		HonorsEntryForm entryForm=(HonorsEntryForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try
		{
			setUserId(request, entryForm);
			String userId = entryForm.getUserId();
			int id = entryForm.getId();
			boolean isReActivate;
			isReActivate = HonorsEntryHandler.getInstance().reActivateHonorsEntry(id, userId);
			if(isReActivate)
			{
				ActionMessage message = new ActionMessage("knowledgepro.fee.honors.entry.reActivate.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				entryForm.reset(mapping, request);
			}
			else{
				errors.add("error", new ActionError("knowledgepro.fee.honors.entry.reActivate.failure"));
				saveErrors(request, errors);
				retainDropDowns(request,entryForm);
				return mapping.findForward(CMSConstants.HONORS_ENTRY);
			}
			
		}
		catch(Exception e)
		{
			String msgKey = super.handleApplicationException(e);
			entryForm.setErrorMessage(msgKey);
			entryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		return mapping.findForward(CMSConstants.HONORS_ENTRY);
	}
}
