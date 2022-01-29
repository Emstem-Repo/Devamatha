package com.kp.cms.actions.admin;

import java.util.List;
import java.util.Map;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.TeacherToGroupForm;
import com.kp.cms.handlers.admin.TeacherToGroupHandler;
import com.kp.cms.handlers.attendance.TeacherClassEntryHandler;
import com.kp.cms.to.admin.TeacherToGroupTos;

public class TeacherToGroupAction extends BaseDispatchAction
{
  private static final Log log=LogFactory.getLog(TeacherToGroupAction.class);

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("Entering into initTeacherToGroup method in TeacherToGroupAction");
        TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        String formName = mapping.getName();
        request.getSession().setAttribute("formName", formName);
        teacherToGroupForm.reset();
        setDataToForm(teacherToGroupForm);
        setRequestedDataToForm(teacherToGroupForm);
        log.debug("Leaving initTeacherToGroup");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }

    private void setDataToForm(TeacherToGroupForm teacherToGroupForm)  throws Exception{
    	Map<Integer, String> rolesMap = TeacherToGroupHandler.getInstance().getroles();
    	teacherToGroupForm.setRolesMap(rolesMap);
    	Map<Integer, String> userMap = TeacherToGroupHandler.getInstance().getusers();
    	teacherToGroupForm.setUsersMap(userMap);
	}

	/**
     * @param studentFeedBackQuestionForm
     * @throws Exception
     */
    private void setRequestedDataToForm(TeacherToGroupForm teacherToGroupForm) throws Exception{
    	List<TeacherToGroupTos> teacherList = TeacherToGroupHandler.getInstance().getteacherGroupList();
    	teacherToGroupForm.setTeacherGrouplist(teacherList);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward addTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of addTeacherToGroup method in TeacherToGroupAction class.");
        TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        setUserId(request, teacherToGroupForm);
        ActionMessages messages = new ActionMessages();
         ActionErrors errors = teacherToGroupForm.validate(mapping, request);
        HttpSession session = request.getSession();
        if(errors.isEmpty())
        {
            try
            {
                boolean isAdded = false;
                boolean isDuplicate =TeacherToGroupHandler.getInstance().duplicateCheck(teacherToGroupForm, errors, session);
                if(!isDuplicate)
                {
                   isAdded = TeacherToGroupHandler.getInstance().addteacherToGroup(teacherToGroupForm,"Add");
                    if(isAdded)
                    {
                        messages.add("messages", new ActionMessage("knowledgepro.teachertogroup.addsuccess"));
                        saveMessages(request, messages);
                        teacherToGroupForm.reset();
                    } else
                    {
                        errors.add("error", new ActionError("knowledgepro.teachertogroup.addfailure"));
                        addErrors(request, errors);
                        teacherToGroupForm.reset();
                    }
                } else
                {
                    addErrors(request, errors);
                }
            }
            catch(Exception exception)
            {
                log.error("Error occured in Certificate Details Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                teacherToGroupForm.setErrorMessage(msg);
                teacherToGroupForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            setDataToForm(teacherToGroupForm);
            setRequestedDataToForm(teacherToGroupForm);
            return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
        }
        setDataToForm(teacherToGroupForm);
        setRequestedDataToForm(teacherToGroupForm);
        log.info("end of addTeacherToGroup method in TeacherToGroupAction class.");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }
	/**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward editTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.info("call of editTeacherToGroup method in TeacherToGroupAction class.");
    	TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        /*ActionErrors errors = new ActionErrors();
        errors = teacherToGroupForm.validate(mapping, request);*/
        log.debug("Entering teacherToGroupForm ");
        try
        {
        	TeacherToGroupHandler.getInstance().editTeacherToGroup(teacherToGroupForm);
            request.setAttribute("TeacherToGroup", "edit");
            log.debug("Leaving editTeacherToGroup ");
        }
        catch(Exception e)
        {
            log.error("error in editing FeedBackQuestion...", e);
            String msg = super.handleApplicationException(e);
            teacherToGroupForm.setErrorMessage(msg);
            teacherToGroupForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("end of editTeacherToGroup method in TeacherToGroupAction class.");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward updateTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
    	log.debug("Enter: updateTeacherToGroup Action");
    	TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        HttpSession session=request.getSession();
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = teacherToGroupForm.validate(mapping, request);
		boolean isUpdated = false;
		if(errors.isEmpty()){
			try {
				// This condition works when reset button will click in update mode
				if (isCancelled(request)) {
					teacherToGroupForm.reset();
			        String formName = mapping.getName();
			        request.getSession().setAttribute("formName", formName);
			        TeacherToGroupHandler.getInstance().editTeacherToGroup(teacherToGroupForm);
			        request.setAttribute("TeacherToGroup", "edit");
			        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
				}
				setUserId(request, teacherToGroupForm);
				 boolean isDuplicate =TeacherToGroupHandler.getInstance().duplicateCheck(teacherToGroupForm, errors, session);
				if(!isDuplicate){
					isUpdated = TeacherToGroupHandler.getInstance().updateteacherToGroup(teacherToGroupForm, "Edit");
				if (isUpdated) {
                    ActionMessage message = new ActionMessage("knowledgepro.TeacherToGroup.updatesuccess");
                    messages.add("messages", message);
                    saveMessages(request, messages);
                    teacherToGroupForm.reset();
                } else {
                    errors.add("error", new ActionError("knowledgepro.TeacherToGroup.updatefailure"));
                    addErrors(request, errors);
                    teacherToGroupForm.reset();
                }}
				else{
	                request.setAttribute("TeacherToGroup", "edit");
	                addErrors(request, errors);
	            }
			} catch (Exception e) {
	            log.error("Error occured in edit valuatorcharges", e);
	            String msg = super.handleApplicationException(e);
	            teacherToGroupForm.setErrorMessage(msg);
	            teacherToGroupForm.setErrorStack(e.getMessage());
	            return mapping.findForward(CMSConstants.ERROR_PAGE);
	        }}else{
				saveErrors(request, errors);
				setRequestedDataToForm(teacherToGroupForm);
		        request.setAttribute("TeacherToGroup", "edit");
				return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
			}
		setDataToForm(teacherToGroupForm);
        setRequestedDataToForm(teacherToGroupForm);
        log.debug("Exit: action class updateTeacherToGroup");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward deleteTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete TeacherToGroup ");
        TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        ActionMessages messages = new ActionMessages();
        try
        {
            boolean isDeleted = TeacherToGroupHandler.getInstance().deleteTeacherToGroup(teacherToGroupForm);
            if(isDeleted)
            {
                ActionMessage message = new ActionMessage("knowledgepro.teachertogroup.deletesuccess");
                messages.add("messages", message);
                saveMessages(request, messages);
            } else
            {
                ActionMessage message = new ActionMessage("knowledgepro.teachertogroup.deletefailure");
                messages.add("messages", message);
                saveMessages(request, messages);
            }
          }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                teacherToGroupForm.setErrorMessage(msg);
                teacherToGroupForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                teacherToGroupForm.setErrorMessage(msg);
                teacherToGroupForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        }
        setRequestedDataToForm(teacherToGroupForm);
        log.debug("Action class. Delete deleteTeacherToGroup ");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }

    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward reActivateTeacherToGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Entering reActivateTeacherToGroup Action");
        TeacherToGroupForm teacherToGroupForm = (TeacherToGroupForm)form;
        ActionErrors errors = new ActionErrors();
        ActionMessages messages = new ActionMessages();
        HttpSession session = request.getSession();
        try
        {
            setUserId(request, teacherToGroupForm);
            String userId = teacherToGroupForm.getUserId();
            String duplicateId = session.getAttribute("ReactivateId").toString();
            teacherToGroupForm.setId(Integer.parseInt(duplicateId));
            boolean isReactivate = TeacherToGroupHandler.getInstance().reActivateTeacherToGroup(teacherToGroupForm, userId);
            if(isReactivate)
            {
                messages.add("messages", new ActionMessage("knowledgepro.teachertogroup.reactivatesuccess"));
                teacherToGroupForm.reset();
                saveMessages(request, messages);
            } else
            {          	
                errors.add("error", new ActionError("knowledgepro.teachertogroup.reactivatefailure"));
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("Error occured in reActivateTeacherToGroup of Action", e);
            String msg = super.handleApplicationException(e);
            teacherToGroupForm.setErrorMessage(msg);
            teacherToGroupForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        setDataToForm(teacherToGroupForm);
        setRequestedDataToForm(teacherToGroupForm);
        log.info("Leaving into reActivateTeacherToGroup of Action");
        return mapping.findForward(CMSConstants.TEACHER_TO_GROUP);
    }
    
}
