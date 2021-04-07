package com.kp.cms.actions.attendance;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;

import com.kp.cms.forms.attendance.AttendanceCondonationForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.attendance.AttendanceCondonationHandler;
import com.kp.cms.to.admin.ProgramTypeTO;

public class AttendanceCondonationAction extends BaseDispatchAction {
	
	
	public ActionForward initAttendanceCondonation(ActionMapping mapping,ActionForm form, HttpServletRequest request, 
			HttpServletResponse response){
		
		AttendanceCondonationForm stForm = (AttendanceCondonationForm) form;
		
		stForm.setProgramId(null);
		stForm.setCourseId(null);
		stForm.setClassId(null);
		stForm.setCutoff(null);
		stForm.setProgramTypeId(null);
		
		try {
			setUserId(request, stForm);
			
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler
					.getInstance().getProgramType();
			
			stForm.setProgramTypeList(programTypeList);
		} catch (ApplicationException e) {
			
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		
		return mapping.findForward("initcondonation");
	}
	
	
	
	public ActionForward getSearchedStudents(ActionMapping mapping,ActionForm form, HttpServletRequest request, 
			HttpServletResponse response){
		AttendanceCondonationForm stform = (AttendanceCondonationForm) form;
		
		ActionErrors errors = stform.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		
		try {
			     if (!errors.isEmpty()) {
				    saveErrors(request, errors);
				    return mapping.findForward("initcondonation");
			     }
			
			     
			    if(stform.getClassId()!=null && !StringUtils.isEmpty(stform.getClassId()) && stform.getProgramTypeId()!=null && 
			    		!StringUtils.isEmpty(stform.getProgramTypeId())&& stform.getMode().equalsIgnoreCase("add")){
				 
				 AttendanceCondonationHandler handler = AttendanceCondonationHandler.getInstance();
				 List studentList;
				 studentList = handler.getSearchedStudent(stform);
				 
				 if(studentList.size()!=0){
					 stform.setStudentList(studentList);	 
				 }else{
					 
					 ActionError error = new ActionError("knowledgepro.att.condon.No_record");
					 errors.add("errors", error);
					 saveErrors(request, errors);
					 return mapping.findForward("initcondonation");
				 }
				 
				 
			 }else if(stform.getClassId()!=null && !StringUtils.isEmpty(stform.getClassId()) && stform.getProgramTypeId()!=null && 
			    		!StringUtils.isEmpty(stform.getProgramTypeId())&& stform.getMode().equalsIgnoreCase("edit")){
				 
				 AttendanceCondonationHandler handler = AttendanceCondonationHandler.getInstance();
				 List studentList;
				 studentList = handler.getStudentToEdit(stform);
                 if(studentList.size()!=0){
					 stform.setStudentList(studentList);	 
				 }else{
					 ActionError error = new ActionError("knowledgepro.att.condon.No_record");
					 errors.add("errors", error);
					 saveErrors(request, errors);
					 return mapping.findForward("initcondonation");
				 }
				 
			 }else{
				 
				 ActionError error = new ActionError("knowledgepro.att.condon.No_record");
				 errors.add("errors", error);
				 saveErrors(request, errors);
				 return mapping.findForward("initcondonation");
			 }
			
			
			
			
			
			
		} catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("condonationPage");
	}
	
	
	public ActionForward saveAttendanceCondonation(ActionMapping mapping,ActionForm form, HttpServletRequest request, 
			HttpServletResponse response){
		    
		boolean isSaved = false;
		AttendanceCondonationForm stform = (AttendanceCondonationForm) form;
		
		ActionErrors errors = stform.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			AttendanceCondonationHandler handler = AttendanceCondonationHandler.getInstance();
			isSaved=handler.saveCondonation(stform);
			if(!isSaved){
				ActionMessage message = new ActionMessage("knowledgepro.att.condon.saved");
			    messages.add("messages", message);
			    saveMessages(request, messages);
			    return mapping.findForward("initcondonation"); 
			}else{
			
			    ActionError error = new ActionError("knowledgepro.att.condon.save.error");
			    errors.add("errors", error);
			    saveErrors(request, errors);
			    return mapping.findForward("initcondonation"); 
			}
			
			
		} catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	
	

}
