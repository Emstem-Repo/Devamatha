package com.kp.cms.actions.fee;

import java.util.GregorianCalendar;
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
import com.kp.cms.forms.fee.FeeAssignmentCopyYearWiseForm;
import com.kp.cms.forms.fee.FeeAssignmentForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.fee.FeeAssignmentCopyHandler;
import com.kp.cms.handlers.fee.FeeAssignmentCopyYearWiseHandler;
import com.kp.cms.handlers.fee.FeeAssignmentHandler;
import com.kp.cms.to.admin.CourseTO;

public class FeeAssignmentCopyYearWiseAction extends BaseDispatchAction
{
	public ActionForward initFeeAssignmentCopy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		FeeAssignmentCopyYearWiseForm assignmentCopyForm=(FeeAssignmentCopyYearWiseForm)form;
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
		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_YEAR_WISE);
	}

	private void setRequiredDataToForm(FeeAssignmentCopyYearWiseForm assignmentCopyForm)throws Exception 
	{
		List<CourseTO>courseList=CourseHandler.getInstance().getActiveCourses();
		assignmentCopyForm.setCourseList(courseList);
		GregorianCalendar cal=new GregorianCalendar();
		assignmentCopyForm.setFromYear(""+(cal.get(GregorianCalendar.YEAR)-1));
		assignmentCopyForm.setToYear(""+cal.get(GregorianCalendar.YEAR));
		
	}
	
	public ActionForward copyFeeAssignment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		FeeAssignmentCopyYearWiseForm assignmentCopyForm=(FeeAssignmentCopyYearWiseForm)form;
	    ActionMessages errors = assignmentCopyForm.validate(mapping, request);
	    ActionMessages messages = new ActionMessages();
	    if(errors.isEmpty()) {
	        try
	        {
	        	boolean isFeeCopied=FeeAssignmentCopyYearWiseHandler.getInstance().copyFeeAssignmentCopy(assignmentCopyForm);
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
	    		return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_YEAR_WISE);
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
	    	return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_YEAR_WISE);
	    }
	    return mapping.findForward(CMSConstants.FEE_ASSIGNMENT_COPY_YEAR_WISE);
	}
}
