package com.kp.cms.actions.studentLogin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.handlers.admin.FreeFoodIssueHandler;
import com.kp.cms.handlers.studentLogin.SyllabusDisplayForStudentHandler;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;

public class SyllabusDisplayForStudentAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SyllabusDisplayForStudentAction.class);	
	/**
	 * @param form
	 * @param mapping
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initSyllabusDisplayForStudent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SyllabusDisplayForStudentForm syllabusDisplayForStudentForm = (SyllabusDisplayForStudentForm) form;
		syllabusDisplayForStudentForm.reset();
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_DISPLAY_FOR_STUDENT);
	}

	public ActionForward getStudentBacklocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SyllabusDisplayForStudentForm syllabusDisplayForStudentForm = (SyllabusDisplayForStudentForm) form;
		syllabusDisplayForStudentForm.setSyllabusDisplayForStudentToList(null);
         ActionErrors errors = syllabusDisplayForStudentForm.validate(mapping, request);
        
		try{
			if(errors.isEmpty()){
				boolean isValidStudent=SyllabusDisplayForStudentHandler.getInstance().checkValidStudentRegNo(syllabusDisplayForStudentForm.getRegistrNo());
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SYLLABUS_DISPLAY_FOR_STUDENT);
				}
				List<SyllabusDisplayForStudentTo> toList=SyllabusDisplayForStudentHandler.getInstance().getStudentBacklocs(syllabusDisplayForStudentForm);
				if(toList!=null && !toList.isEmpty()){
					syllabusDisplayForStudentForm.setSyllabusDisplayForStudentToList(toList);
				}else{
					syllabusDisplayForStudentForm.setSyllabusDisplayForStudentToList(toList);
					 errors.add("error", new ActionError("knowledgepro.norecords"));
	                 addErrors(request, errors);
	                 saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
			}
		}catch(Exception exception)
        {
            log.error("Error occured in caste Entry Action", exception);
            String msg = super.handleApplicationException(exception);
            syllabusDisplayForStudentForm.setErrorMessage(msg);
            syllabusDisplayForStudentForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
		
		
		
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_DISPLAY_FOR_STUDENT);
	}
	
}
