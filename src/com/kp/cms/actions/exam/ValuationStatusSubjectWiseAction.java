package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ValuationStatusSubjectWiseForm;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.handlers.exam.ValuationStatusSubjectWiseHandler;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ValuationStatusSubjectWiseAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ValuationStatusSubjectWiseAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationStatusSubjectWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm = (ValuationStatusSubjectWiseForm)form;
		valuationStatusSubjectWiseForm.resetFields();
		setRequestDataToForm(valuationStatusSubjectWiseForm);
		return mapping.findForward(CMSConstants.INIT_VALUATION_STATUS_SUBJECT_WISE);
	}

	/**
	 * @param valuationStatusSubjectWiseForm
	 * @throws Exception
	 */
	private void setRequestDataToForm( ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm) throws Exception{
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(valuationStatusSubjectWiseForm.getYear()!=null && !valuationStatusSubjectWiseForm.getYear().isEmpty()){
			year=Integer.parseInt(valuationStatusSubjectWiseForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		/*         getting exam list based on exam Type  */
		Map<Integer,String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(valuationStatusSubjectWiseForm.getExamType(), year);
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		valuationStatusSubjectWiseForm.setExamNameMap(examNameMap);// setting the examNameMap to form
		/* ---------------------------------------------*/
		/*           For Making default Current Exam    */
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(valuationStatusSubjectWiseForm.getExamType());
		if(valuationStatusSubjectWiseForm.getExamId() == null && currentExam!=null && !currentExam.isEmpty()){
			valuationStatusSubjectWiseForm.setExamId(currentExam);
		}
		/* ---------------------------------------------*/
		
		if(valuationStatusSubjectWiseForm.getExamId()!=null && !valuationStatusSubjectWiseForm.getExamId().isEmpty()){
			/* commented by chandra 
			  ArrayList<KeyValueTO> subjectList=CommonAjaxExamHandler.getInstance().getSubjectCodeName(valuationStatusSubjectWiseForm.getDisplaySubType(), Integer.parseInt(valuationStatusSubjectWiseForm.getExamId()));
			valuationStatusSubjectWiseForm.setSubjectList(subjectList);*/
			Map<Integer, String> subjectMap=NewSecuredMarksEntryHandler.getInstance().getSubjects(valuationStatusSubjectWiseForm.getExamId(),valuationStatusSubjectWiseForm.getDisplaySubType(),valuationStatusSubjectWiseForm.getExamType(),String.valueOf(year));
			valuationStatusSubjectWiseForm.setSubjectMap(subjectMap);
		}
		if(valuationStatusSubjectWiseForm.getSubjectId()!=null && !valuationStatusSubjectWiseForm.getSubjectId().isEmpty()){
			String value = "";
			Map<String, String> subjectTypeMap = new HashMap<String, String>();
			int subjectId = Integer.parseInt(valuationStatusSubjectWiseForm.getSubjectId());
			value = CommonAjaxHandler.getInstance().getSubjectsTypeBySubjectId(subjectId);
			if (value.equalsIgnoreCase("T")) {
				subjectTypeMap.put("T", "Theory");
			}
			if (value.equalsIgnoreCase("P")) {
				subjectTypeMap.put("P", "Practical");
			}
			if (value.equalsIgnoreCase("B")) {
				subjectTypeMap.put("T", "Theory");
				subjectTypeMap.put("P", "Practical");
				value = "t";
			}
			valuationStatusSubjectWiseForm.setSubjectType(value.toUpperCase());
			valuationStatusSubjectWiseForm.setSubjectTypeList(subjectTypeMap);
		}else{
			valuationStatusSubjectWiseForm.setSubjectType(null);
			valuationStatusSubjectWiseForm.setSubjectTypeList(null);
		}
		
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuationStatusSubjectWiseDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ValuationStatusSubjectWiseForm valuationStatusSubjectWiseForm = (ValuationStatusSubjectWiseForm)form;
		ActionErrors errors = new ActionErrors();
		if(valuationStatusSubjectWiseForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(valuationStatusSubjectWiseForm.getSchemeNo()==null || valuationStatusSubjectWiseForm.getSchemeNo().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.STUDENT_ATTENDANCE_FOR_EXAM_SCHEME_REQD));
				addErrors(request, errors);
				setRequestDataToForm(valuationStatusSubjectWiseForm);
				log.info("Exit ValuationStatusSubjectWiseAction - getValuationStatusSubjectWiseDetails errors not empty ");
				return mapping.findForward(CMSConstants.INIT_VALUATION_STATUS_SUBJECT_WISE);
			}
		}
		errors = valuationStatusSubjectWiseForm.validate(mapping, request);
		setUserId(request, valuationStatusSubjectWiseForm);
		if(errors.isEmpty()){
			try{
				ValuationStatusSubjectWiseHandler.getInstance().getValuationStatusSubjectWise(valuationStatusSubjectWiseForm);
				setRequestDataToForm(valuationStatusSubjectWiseForm);
				}
			catch (ApplicationException exception) {
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.MarksCorrection.DataNotFound"));
				addErrors(request, errors);
				setRequestDataToForm(valuationStatusSubjectWiseForm);
				return mapping.findForward(CMSConstants.INIT_VALUATION_STATUS_SUBJECT_WISE);
			}
			catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				valuationStatusSubjectWiseForm.setErrorMessage(msg);
				valuationStatusSubjectWiseForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else {
			addErrors(request, errors);
			log.info("Exit ValuationStatusSubjectWiseAction - getValuationStatusSubjectWiseDetails errors not empty ");
			return mapping.findForward(CMSConstants.INIT_VALUATION_STATUS_SUBJECT_WISE);
		}
		
		return mapping.findForward(CMSConstants.INIT_VALUATION_STATUS_SUBJECT_WISE);
	}
}
