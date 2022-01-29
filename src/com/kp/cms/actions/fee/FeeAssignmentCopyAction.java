package com.kp.cms.actions.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeAssignmentCopyForm;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.fee.FeeAssignmentCopyHandler;
import com.kp.cms.handlers.fee.FeeAssignmentHandler;
import com.kp.cms.to.admin.CourseTO;

public class FeeAssignmentCopyAction extends BaseDispatchAction
{
	public ActionForward initFeeAssignmentCopy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		FeeAssignmentCopyForm assignmentCopyForm=(FeeAssignmentCopyForm)form;
		assignmentCopyForm.clear();
	    setUserId(request,assignmentCopyForm);
	    // setting the necessary data.
	    try
	    {
	    	setRequiredDataToForm(assignmentCopyForm);
	    }
	    catch(Exception e)
	    {
			String msg = super.handleApplicationException(e);
			assignmentCopyForm.setErrorMessage(msg);
			assignmentCopyForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY);
	}

	private void setRequiredDataToForm(FeeAssignmentCopyForm assignmentCopyForm)throws Exception 
	{
		List<CourseTO>courseList=CourseHandler.getInstance().getActiveCourses();
		assignmentCopyForm.setCourseList(courseList);
		
	}
	
	public ActionForward copyFeeAssignment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		FeeAssignmentCopyForm assignmentCopyForm=(FeeAssignmentCopyForm)form;
	    ActionMessages errors = assignmentCopyForm.validate(mapping, request);
	    ActionMessages messages = new ActionMessages();
	    if(errors.isEmpty()) {
	        try
	        {
	        	boolean isFeeCopied=FeeAssignmentCopyHandler.getInstance().copyFeeAssignmentCopy(assignmentCopyForm);
	        	if(isFeeCopied)
	        	{
	        		ActionMessage message = new ActionMessage(CMSConstants.FEE_COPY_SUCCESS);
	    			messages.add(CMSConstants.MESSAGES, message);
	    			saveMessages(request, messages);
	    			assignmentCopyForm.clear();
	    			request.setAttribute("semistersMap",null);
	        	}
	        	else
	        	{
	        		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_FAILED));
		    		saveErrors(request,errors);
		    		setRequiredDataToForm(assignmentCopyForm);
	        	}
		    }
	        catch (DataNotFoundException e1) 
	        {
		    	if(e1.getMessage().equalsIgnoreCase("No Existing FeeDefinitions"))
		    	{	
		    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_NO_RECOREDS));
		    	}
		    	else
		    	{
		    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_ALREADY_EXISTS));
		    	}
		    	saveErrors(request,errors);
		    	setRequiredDataToForm(assignmentCopyForm);
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY);
		    } 
	        catch (Exception e) 
	        {
				String msg = super.handleApplicationException(e);
				assignmentCopyForm.setErrorMessage(msg);
				assignmentCopyForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
	    }
	    else 
	    {
	    	setRequiredDataToForm(assignmentCopyForm);
	    	request.setAttribute("validateError", true);
	    	saveErrors(request, errors);
	    	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY);
	    }
	    return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY);
	}
	
	
	
	
	public ActionForward initFeeAssignmentCopyCourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		FeeAssignmentCopyForm assignmentCopyForm=(FeeAssignmentCopyForm)form;
		assignmentCopyForm.clear();
	    setUserId(request,assignmentCopyForm);
	    // setting the necessary data.
	    try
	    {
	    	setRequiredDataToFormCourse(assignmentCopyForm);
	    }
	    catch(Exception e)
	    {
			String msg = super.handleApplicationException(e);
			assignmentCopyForm.setErrorMessage(msg);
			assignmentCopyForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	    }
		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_COURSE);
	}

	private void setRequiredDataToFormCourse(FeeAssignmentCopyForm assignmentCopyForm)throws Exception 
	{
		List<CourseTO>courseList=CourseHandler.getInstance().getActiveCourses();
		assignmentCopyForm.setCourseList(courseList);
		
	}
	
	public ActionForward copyFeeAssignmentCourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		FeeAssignmentCopyForm assignmentCopyForm=(FeeAssignmentCopyForm)form;
	    ActionMessages errors = assignmentCopyForm.validate(mapping, request);
	    ActionMessages messages = new ActionMessages();
	    if(errors.isEmpty()) {
	        try
	        {
	        	boolean isFeeCopied=FeeAssignmentCopyHandler.getInstance().copyFeeAssignmentCopyCourse(assignmentCopyForm);
	        	if(isFeeCopied)
	        	{
	        		ActionMessage message = new ActionMessage(CMSConstants.FEE_COPY_SUCCESS);
	    			messages.add(CMSConstants.MESSAGES, message);
	    			saveMessages(request, messages);
	    			assignmentCopyForm.clear();
	    			request.setAttribute("semistersMap",null);
	        	}
	        	else
	        	{
	        		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_FAILED));
		    		saveErrors(request,errors);
		    		setRequiredDataToForm(assignmentCopyForm);
	        	}
		    }
	        catch (DataNotFoundException e1) 
	        {
		    	if(e1.getMessage().equalsIgnoreCase("No Existing FeeDefinitions"))
		    	{	
		    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_NO_RECOREDS));
		    	}
		    	else
		    	{
		    		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_COPY_ALREADY_EXISTS));
		    	}
		    	saveErrors(request,errors);
		    	setRequiredDataToForm(assignmentCopyForm);
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_COURSE);
		    } 
	        catch (Exception e) 
	        {
				String msg = super.handleApplicationException(e);
				assignmentCopyForm.setErrorMessage(msg);
				assignmentCopyForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		    }
	    }
	    else 
	    {
	    	setRequiredDataToForm(assignmentCopyForm);
	    	request.setAttribute("validateError", true);
	    	saveErrors(request, errors);
	    	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_COURSE);
	    }
	    return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_COURSE);
	}

	
	
}
