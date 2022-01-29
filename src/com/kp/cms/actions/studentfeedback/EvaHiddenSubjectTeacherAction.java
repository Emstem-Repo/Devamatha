package com.kp.cms.actions.studentfeedback;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.studentfeedback.EvaHiddenSubjectTeacherForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentfeedback.EvaHiddenSubjectTeacherHandler;
import com.kp.cms.to.attendance.TeacherClassEntryTo;
import com.kp.cms.to.studentfeedback.EvaHiddenSubjectTeacherTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class EvaHiddenSubjectTeacherAction extends BaseDispatchAction
{
	private static final Log log=LogFactory.getLog(EvaHiddenSubjectTeacherAction.class);

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHideSubjectTeacher(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response)
	throws Exception{
		log.info("entering into HideSubjectTeacher of EvaHiddenSubjectTeacherAction class");
		EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm=(EvaHiddenSubjectTeacherForm)form;
		evaHiddenSubjectTeacherForm.resetField();
		try {
			setUserId(request, evaHiddenSubjectTeacherForm);
			setClassMapToForm(evaHiddenSubjectTeacherForm);
				
		} catch (Exception e) {
			log.error("Error while initializing Hide Subject Teacher"+ e.getMessage());
			String msg=super.handleApplicationException(e);
			evaHiddenSubjectTeacherForm.setErrorMessage(msg);
			evaHiddenSubjectTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("leaving from initHideSubjectTeacher of EvaHiddenSubjectTeacherAction class");
		return mapping.findForward(CMSConstants.HIDE_SUBJECT_TEACHER);
	}
    /**
     * @param evaHiddenSubjectTeacherForm
     * @throws Exception
     */
    private void setHideTeacherSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception{
    	List<EvaHiddenSubjectTeacherTo> hidelist=EvaHiddenSubjectTeacherHandler.getInstance().getHideTeacherList(evaHiddenSubjectTeacherForm);
    	evaHiddenSubjectTeacherForm.setHiddenTeacherList(hidelist);
	}
	/**
	 * @param evaHiddenSubjectTeacherForm
	 * @param errors 
	 * @throws Exception
	 */
	private void setTeacherClassSubject(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm, ActionErrors errors) throws Exception{
		List<TeacherClassEntryTo> teacherClassList=EvaHiddenSubjectTeacherHandler.getInstance().getTeacherClassList(evaHiddenSubjectTeacherForm,errors);
		evaHiddenSubjectTeacherForm.setTeacherClassSubjectList(teacherClassList);
		}
	/**
     * @param evaHiddenSubjectTeacherForm
     */
    private void setClassMapToForm(EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm) throws Exception{
    	log.info("entering into setClassMapToForm of EvaHiddenSubjectTeacherAction class.");
    	Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(year!=0){
			currentYear=year;
		}// end
		evaHiddenSubjectTeacherForm.setYear(String.valueOf(currentYear));
		Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
		evaHiddenSubjectTeacherForm.setClassMap(classMap);
     }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward searchTeacherSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception{
        log.info("call of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm=(EvaHiddenSubjectTeacherForm)form;
        setUserId(request, evaHiddenSubjectTeacherForm);
         ActionErrors errors = evaHiddenSubjectTeacherForm.validate(mapping, request);
       if(errors.isEmpty())
        {
            try
            {
            	setTeacherClassSubject(evaHiddenSubjectTeacherForm,errors);
			    setHideTeacherSubject(evaHiddenSubjectTeacherForm);
			    if(errors!=null && !errors.isEmpty()){
			    addErrors(request, errors);
			    }
			   
			}
            catch(Exception exception)
            {
                log.error("Error occured in caste Entry Action", exception);
                String msg = super.handleApplicationException(exception);
                evaHiddenSubjectTeacherForm.setErrorMessage(msg);
                evaHiddenSubjectTeacherForm.setErrorStack(exception.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } else
        {
            saveErrors(request, errors);
            return mapping.findForward(CMSConstants.HIDE_SUBJECT_TEACHER);
        }
        log.info("end of addFeedBackQuestion method in EvaStudentFeedBackQuestionAction class.");
        return mapping.findForward(CMSConstants.HIDE_SUBJECT_TEACHER);
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward hideTeacherSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception {
    	log.debug("Action class. Delete valuatorCharges ");
    	 EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm=(EvaHiddenSubjectTeacherForm)form;
        ActionMessages messages = new ActionMessages();
        ActionErrors errors = new ActionErrors();
       try
        {
        	setUserId(request, evaHiddenSubjectTeacherForm);
        	boolean isDuplicate=EvaHiddenSubjectTeacherHandler.getInstance().isDuplicate(evaHiddenSubjectTeacherForm, errors);
        	 if(!isDuplicate)
             {
               boolean isHide = EvaHiddenSubjectTeacherHandler.getInstance().hideTeacherSubject(evaHiddenSubjectTeacherForm,evaHiddenSubjectTeacherForm.getUserId());
                 if(isHide)
                {
                ActionMessage message = new ActionMessage("knowledgepro.hideteachersubject.success");
                messages.add("messages", message);
                saveMessages(request, messages);
               } else
                 {
                ActionMessage message = new ActionMessage("knowledgepro.hideteachersubject.failure");
                messages.add("messages", message);
                saveMessages(request, messages);
                }
               	    
            } else
            {
                addErrors(request, errors);
            }
        }
        catch(Exception e)
        {
            log.error("error submit valuatorCharges...", e);
            if(e instanceof ApplicationException)
            {
                String msg = super.handleApplicationException(e);
                evaHiddenSubjectTeacherForm.setErrorMessage(msg);
                evaHiddenSubjectTeacherForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            } else
            {
                String msg = super.handleApplicationException(e);
                evaHiddenSubjectTeacherForm.setErrorMessage(msg);
                evaHiddenSubjectTeacherForm.setErrorStack(e.getMessage());
                return mapping.findForward(CMSConstants.ERROR_PAGE);
            }
        } 
        setHideTeacherSubject(evaHiddenSubjectTeacherForm);
        setTeacherClassSubject(evaHiddenSubjectTeacherForm,errors);
	    log.debug("Action class. Delete valuatorCharges ");
        return mapping.findForward(CMSConstants.HIDE_SUBJECT_TEACHER);
    }
    /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward showTeacherSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception {
	log.debug("Action class. Delete valuatorCharges ");
	 EvaHiddenSubjectTeacherForm evaHiddenSubjectTeacherForm=(EvaHiddenSubjectTeacherForm)form;
    ActionMessages messages = new ActionMessages();
     ActionErrors errors = new ActionErrors();
    try
    {
    	setUserId(request, evaHiddenSubjectTeacherForm);
        boolean isHide = EvaHiddenSubjectTeacherHandler.getInstance().showTeacherSubject(evaHiddenSubjectTeacherForm);
        if(isHide)
        {
            ActionMessage message = new ActionMessage("knowledgepro.showteachersubject.success");
            messages.add("messages", message);
            saveMessages(request, messages);
        } else
        {
            ActionMessage message = new ActionMessage("knowledgepro.showteachersubject.failure");
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
            evaHiddenSubjectTeacherForm.setErrorMessage(msg);
            evaHiddenSubjectTeacherForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        } else
        {
            String msg = super.handleApplicationException(e);
            evaHiddenSubjectTeacherForm.setErrorMessage(msg);
            evaHiddenSubjectTeacherForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
    }
    setTeacherClassSubject(evaHiddenSubjectTeacherForm,errors);
    setHideTeacherSubject(evaHiddenSubjectTeacherForm);
    log.debug("Action class. Delete valuatorCharges ");
    return mapping.findForward(CMSConstants.HIDE_SUBJECT_TEACHER);
}

}
