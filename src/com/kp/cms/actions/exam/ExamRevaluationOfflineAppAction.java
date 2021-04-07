package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamRevaluationOfflineAppForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamRevaluationOfflineAppHandler;
import com.kp.cms.to.exam.ExamRevaluationApplicationTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamRevaluationOfflineAppAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(ExamRevaluationOfflineAppAction.class);
	
	/**
	 * Method to set the required data to the form to display it in initRevaluationStatusUpdate
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initRevaluationOfflineApp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initRevaluationOfflineApp Batch input");
		ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm = (ExamRevaluationOfflineAppForm) form;
		examRevaluationOfflineAppForm.resetFields();
		setRequiredDatatoForm(examRevaluationOfflineAppForm);
		log.info("Exit initRevaluationOfflineApp Batch input");
		return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_APP_INIT);
	}
	/**
	 * @param examRevaluationOfflineApp
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm) throws Exception {
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(examRevaluationOfflineAppForm.getYear()!=null && !examRevaluationOfflineAppForm.getYear().isEmpty()){
			year=Integer.parseInt(examRevaluationOfflineAppForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(examRevaluationOfflineAppForm.getExamType(),year); 
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		examRevaluationOfflineAppForm.setExamNameMap(examNameMap);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectsForStudent(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("Entered getSubjectsForStudent method");
		ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm=(ExamRevaluationOfflineAppForm)form;
		ActionErrors errors=examRevaluationOfflineAppForm.validate(mapping, request);
		if(!errors.isEmpty()){
			saveErrors(request, errors);
//			examRevaluationOfflineAppForm.resetFields();
			setRequiredDatatoForm(examRevaluationOfflineAppForm);
			 return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_APP_INIT);
		}
		else {
			try {
				List<ExamRevaluationApplicationTO> examRevaluationApplicationTOs=ExamRevaluationOfflineAppHandler.getInstance().getStudentSubjectsForRevApp(examRevaluationOfflineAppForm);
				if(examRevaluationApplicationTOs.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_NORECORDS));
					saveErrors(request, errors);
					setRequiredDatatoForm(examRevaluationOfflineAppForm);
					log.info("Exit ExamRevaluationOfflineAppAction - getSubjectsForStudent size 0");
					 return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_APP_INIT);
				}
				examRevaluationOfflineAppForm.setRevAppList(examRevaluationApplicationTOs);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examRevaluationOfflineAppForm.setErrorMessage(msg);
				examRevaluationOfflineAppForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving getSubjectsForStudent method");
		 return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_RESULT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitMarksCard(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		log.info("Entered ExamRevaluationOfflineAppAction - submitMarksCard");
		
		ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm=(ExamRevaluationOfflineAppForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examRevaluationOfflineAppForm.validate(mapping, request);
		validateDDdetails(examRevaluationOfflineAppForm, errors);
		if (errors.isEmpty()) {
			try {
				boolean isUpdate=ExamRevaluationOfflineAppHandler.getInstance().saveRevaluationData(examRevaluationOfflineAppForm);
				if(isUpdate){
					ActionMessage message = new ActionMessage("KnowledgePro.Revaluation.added.successfully");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			}catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examRevaluationOfflineAppForm.setErrorMessage(msg);
				examRevaluationOfflineAppForm.setErrorStack(exception.getMessage());
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_RESULT);
		}
		log.info("Entered ExamRevaluationOfflineAppAction - submitMarksCard");
		examRevaluationOfflineAppForm.resetFields();
		return mapping.findForward(CMSConstants.EXAM_REV_OFFLINE_APP_INIT);
	}
	/**
	 * @param examRevaluationOfflineAppForm
	 * @param errors
	 */
	private void validateDDdetails( ExamRevaluationOfflineAppForm examRevaluationOfflineAppForm, ActionErrors errors) throws Exception {
		if (examRevaluationOfflineAppForm.getDdDate()!=null && !StringUtils.isEmpty(examRevaluationOfflineAppForm.getDdDate())) {
			if(CommonUtil.isValidDate(examRevaluationOfflineAppForm.getDdDate())){
			boolean	isValid = AdmissionFormAction.validatefutureDate(examRevaluationOfflineAppForm.getDdDate());
			if(!isValid){
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
				}
			}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
				}
			}
		}
		/*if(errors.isEmpty()){
			boolean isValid=ExamRevaluationOfflineAppHandler.getInstance().checkValidDDdetails(examRevaluationOfflineAppForm);
			if(!isValid)
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","DD Details Already Exists"));
		}*/
	}
	
}
