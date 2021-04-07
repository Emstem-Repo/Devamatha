package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;
import com.kp.cms.handlers.admin.SyllabusTrackerForSupplementaryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.studentLogin.SyllabusDisplayForStudentHandler;
import com.kp.cms.to.admin.SyllabusTrackerForSupplementaryTo;
import com.kp.cms.to.studentLogin.SyllabusDisplayForStudentTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class SyllabusTrackerForSupplementaryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(SyllabusTrackerForSupplementaryAction.class);
	
	public ActionForward initSyllabusTrackerForSupplementary(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception  {
		
		SyllabusTrackerForSupplementaryForm syllabusTrackerForSupplementaryForm = (SyllabusTrackerForSupplementaryForm) form;
		syllabusTrackerForSupplementaryForm.reset();
		setRequiredDatatoForm(syllabusTrackerForSupplementaryForm);
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_DISPLAY_FOR_SUPPLIMENTORY);
	}
	private void setRequiredDatatoForm(SyllabusTrackerForSupplementaryForm form) throws Exception {
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(form.getYear()!=null && !form.getYear().isEmpty()){
			year=Integer.parseInt(form.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear("Supplementary",year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		form.setExamNameList(examNameMap);
		Map<String, String>  deanaryList=SyllabusTrackerForSupplementaryHandler.getInstance().getDeanaryList();
		form.setDeanaryList(deanaryList);
	}
	
	public ActionForward getStudentBacklocks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		SyllabusTrackerForSupplementaryForm syllabusTrackerForSupplementaryForm = (SyllabusTrackerForSupplementaryForm) form;
		syllabusTrackerForSupplementaryForm.setSyllabusTrackerForSupplementaryTo(null);
         ActionErrors errors = syllabusTrackerForSupplementaryForm.validate(mapping, request);
        validateDates(syllabusTrackerForSupplementaryForm,request,errors);
		try{
			if(errors.isEmpty()){
			
				List<SyllabusTrackerForSupplementaryTo> toList=SyllabusTrackerForSupplementaryHandler.getInstance().getStudentBacklocs(syllabusTrackerForSupplementaryForm);
				if(toList!=null && !toList.isEmpty()){
					syllabusTrackerForSupplementaryForm.setSyllabusTrackerForSupplementaryTo(toList);
				}else{
					syllabusTrackerForSupplementaryForm.setSyllabusTrackerForSupplementaryTo(toList);
					 errors.add("error", new ActionError("knowledgepro.norecords"));
	                 addErrors(request, errors);
	                 saveErrors(request, errors);
				}
				setRequiredDatatoForm(syllabusTrackerForSupplementaryForm);
				Map<Integer, String> programList=CommonAjaxHandler.getInstance().getProgramBydeanaryNameAndExam(syllabusTrackerForSupplementaryForm.getDeanery(),Integer.parseInt(syllabusTrackerForSupplementaryForm.getExamId()));
				syllabusTrackerForSupplementaryForm.setProgramList(programList);
			}else{
				
				saveErrors(request, errors);
				setRequiredDatatoForm(syllabusTrackerForSupplementaryForm);
			}
		}catch(Exception exception)
        {
            log.error("Error occured in caste Entry Action", exception);
            String msg = super.handleApplicationException(exception);
            syllabusTrackerForSupplementaryForm.setErrorMessage(msg);
            syllabusTrackerForSupplementaryForm.setErrorStack(exception.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
		
		return mapping.findForward(CMSConstants.INIT_SYLLABUS_DISPLAY_FOR_SUPPLIMENTORY);
	}
	public void validateDates(SyllabusTrackerForSupplementaryForm form,HttpServletRequest request,ActionErrors errors){
		if((form.getFromDate()!=null && !form.getFromDate().isEmpty())|| (form.getToDate()!=null && !form.getToDate().isEmpty())){
			if(form.getFromDate()!=null && !form.getFromDate().isEmpty()){
				if(form.getToDate()==null || form.getToDate().isEmpty()){
					 errors.add("error", new ActionError("knowledgepro.attendance.activityattendence.todate.required"));
	                 addErrors(request, errors);
	                 saveErrors(request, errors);
				}
			}
			if(form.getToDate()!=null && !form.getToDate().isEmpty()){
				if(form.getFromDate()==null || form.getFromDate().isEmpty()){
					 errors.add("error", new ActionError("knowledgepro.attendance.activityattendence.fromdate.required"));
	                 addErrors(request, errors);
	                 saveErrors(request, errors);
				}
			}
		}
	}
}
