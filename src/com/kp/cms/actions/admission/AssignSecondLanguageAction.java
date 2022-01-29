package com.kp.cms.actions.admission;

import java.util.HashMap;
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

import com.ibm.icu.util.Calendar;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AssignSecondLanguageForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admission.AssignSecondLanguageHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.reports.ScoreSheetHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.AssignSecondLanguageTo;
import com.kp.cms.to.reports.ScoreSheetTO;

public class AssignSecondLanguageAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(AssignSecondLanguageAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAssignSecondLanguage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered into initAssignSecondLanguage-AssignSecondLanguageAction");
		AssignSecondLanguageForm assignSecondLanguageForm=(AssignSecondLanguageForm)form;
		assignSecondLanguageForm.resetFields();
		setRequestedDataToForm(assignSecondLanguageForm,request);
		log.info("Exit from initAssignSecondLanguage-AssignSecondLanguageAction");
		return mapping.findForward(CMSConstants.ASSIGN_SECOND_LANGUAGE);
	}
	/**
	 * setting the requested data to form
	 * @param assignSecondLanguageForm
	 */
	private void setRequestedDataToForm(AssignSecondLanguageForm assignSecondLanguageForm,HttpServletRequest request) throws Exception{
		List<CourseTO> courseList = CourseHandler.getInstance().getActiveCourses();
		assignSecondLanguageForm.setCourseList(courseList);
		ExamGenHandler genHandler = new ExamGenHandler();
		HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
		assignSecondLanguageForm.setSecondLanguageList(secondLanguage);
		if(assignSecondLanguageForm.getCourseId()!=null && !assignSecondLanguageForm.getCourseId().isEmpty()){
			int year=Calendar.YEAR;
			if(assignSecondLanguageForm.getYear()!=null && !assignSecondLanguageForm.getYear().isEmpty()){
				year=Integer.parseInt(assignSecondLanguageForm.getYear());
				Map<Integer,String> schemeMap =  CommonAjaxHandler.getInstance().getSchemeNoByCourseIdAcademicyear(Integer.parseInt(assignSecondLanguageForm.getCourseId()),year);
				request.setAttribute("schemeMap", schemeMap);
			}
			
		}
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAssignSecondLanguage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered AssignSecondLanguageAction - searchAssignSecondLanguage");
		AssignSecondLanguageForm assignSecondLanguageForm=(AssignSecondLanguageForm)form;
		 ActionErrors errors = assignSecondLanguageForm.validate(mapping, request);
		validateSchemeNo(assignSecondLanguageForm,errors);
		if (errors.isEmpty()) {
			try {
				List<AssignSecondLanguageTo> selectedCandidates = AssignSecondLanguageHandler.getInstance().getListOfStudents(assignSecondLanguageForm);
				if (selectedCandidates.isEmpty()) {
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequestedDataToForm(assignSecondLanguageForm,request);
					log.info("Exit Interview Batch Result - getSelectedCandidates size 0");
					return mapping.findForward(CMSConstants.ASSIGN_SECOND_LANGUAGE);
				} 
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				assignSecondLanguageForm.setErrorMessage(msg);
				assignSecondLanguageForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequestedDataToForm(assignSecondLanguageForm,request);
			log.info("Exit AssignSecondLanguageAction - searchAssignSecondLanguage errors not empty ");
			return mapping.findForward(CMSConstants.ASSIGN_SECOND_LANGUAGE);
		}
		log.info("Entered AssignSecondLanguageAction - searchAssignSecondLanguage");
		return mapping.findForward(CMSConstants.SCORE_SHEET_RESULT);
	}
	/**
	 * @param assignSecondLanguageForm
	 * @param errors
	 */
	private void validateSchemeNo(AssignSecondLanguageForm assignSecondLanguageForm,ActionErrors errors) {
		String courseId = assignSecondLanguageForm.getCourseId();
		if(courseId!=null && !courseId.isEmpty() && assignSecondLanguageForm.getYear()!=null && !assignSecondLanguageForm.getYear().isEmpty()){
			Map<Integer,String> schemeMap =  CommonAjaxHandler.getInstance().getSchemeNoByCourseIdAcademicyear(Integer.parseInt(courseId),Integer.parseInt(assignSecondLanguageForm.getYear()));
			if(schemeMap.size()>=1){
				if(assignSecondLanguageForm.getSchemeNo()==null || assignSecondLanguageForm.getSchemeNo().isEmpty()){
					if (errors.get(CMSConstants.FEE_SEMESTER_REQUIRED) != null&& !errors.get(CMSConstants.FEE_SEMESTER_REQUIRED).hasNext()) {
						errors.add(CMSConstants.FEE_SEMESTER_REQUIRED,new ActionError(CMSConstants.FEE_SEMESTER_REQUIRED));
					}
				}
			}
		}
		
	}
}
