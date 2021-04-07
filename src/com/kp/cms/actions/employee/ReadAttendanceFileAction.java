package com.kp.cms.actions.employee;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.ReadAttendanceFileForm;
import com.kp.cms.handlers.employee.ReadAttendanceFileHandler;

public class ReadAttendanceFileAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(WorkDiaryAction.class);

	public ActionForward initReadAttendanceFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.READ_ATTENDANCE_FILE);
	}

	public ActionForward readAttendanceFile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReadAttendanceFileForm formObj=(ReadAttendanceFileForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		try 
		{
			if(formObj.getThefile().getFileName().isEmpty())
			{
				errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.employee.read.attendance.file.upload.file.required"));
			}
			if(!errors.isEmpty())
			{
				saveErrors(request, errors);
			}
			else
			{
				setUserId(request, formObj);
				boolean isAdded;
				isAdded=ReadAttendanceFileHandler.getInstance().readAttandanceFile(formObj);
				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.employee.read.attendance.file.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				else
				{
					errors.add("error", new ActionMessage("knowledgepro.employee.read.attendance.file.failed"));
					saveErrors(request, errors);
				}
			}	
		}
		catch (Exception e) 
		{
			log.error("error in final submit of education page...", e);
			if (e instanceof BusinessException) 
			{
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				formObj.setErrorMessage(msg);
				formObj.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward(CMSConstants.READ_ATTENDANCE_FILE);
	}
}
