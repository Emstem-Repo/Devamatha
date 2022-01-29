package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ValuationScheduleForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.ValuationScheduleHandler;
import com.kp.cms.to.exam.ValuationScheduleTO;
import com.kp.cms.utilities.CommonUtil;

public class ValuationScheduleAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ValuationScheduleAction.class);
	
	/**
	 * Method to set the required data to the form to display it in valuationSchedule.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationSchedule input");
		ValuationScheduleForm valuationScheduleForm = (ValuationScheduleForm) form;
		valuationScheduleForm.resetFields();
		try{
			setRequiredDatatoForm(valuationScheduleForm, request);
		}catch (Exception e) {
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationSchedule input");
		
		return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
	}
	
	@SuppressWarnings("unchecked")
	private void setRequiredDatatoForm(ValuationScheduleForm valuationScheduleForm,
			HttpServletRequest request)	throws Exception{
		
		Map<Integer, String> examNameMap = ValuationScheduleHandler.getInstance().getExamNameByExamType(valuationScheduleForm.getExamType(),valuationScheduleForm.getAcademicYear());
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		valuationScheduleForm.setExamNameList(examNameMap);
		
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(valuationScheduleForm.getExamType());
		if((valuationScheduleForm.getExamId()==null || valuationScheduleForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			valuationScheduleForm.setExamId(currentExam);
			
		if(valuationScheduleForm.getExamId()!=null && !valuationScheduleForm.getExamId().isEmpty()){
			List<ValuationScheduleTO> valuationList =  ValuationScheduleHandler.getInstance().getValuationScheduleDetails(valuationScheduleForm.getExamId(),valuationScheduleForm.getAcademicYear());
			valuationScheduleForm.setValuationDetails(valuationList);
		}
		else{
			List<ValuationScheduleTO> valuationList =  new ArrayList<ValuationScheduleTO>();
			valuationScheduleForm.setValuationDetails(valuationList);
		}
		
		if(valuationScheduleForm.getExamId()!=null && !valuationScheduleForm.getExamId().isEmpty()){
			Map<Integer, String> subjectList=ValuationScheduleHandler.getInstance().getSubjectCodeName(
					valuationScheduleForm.getAcademicYear() ,valuationScheduleForm.getDisplaySubType(),
					Integer.parseInt(valuationScheduleForm.getExamId()),valuationScheduleForm.getExamType());
			valuationScheduleForm.setSubjectMap(subjectList);
		}
		if(valuationScheduleForm.getSubjectId()!=null && !valuationScheduleForm.getSubjectId().isEmpty())
		{
			Map<Integer, String> valuatorMap = ExamValidationDetailsHandler.getInstance().getValuatorNameListBySubjectDept(valuationScheduleForm.getSubjectId());
			request.getSession().setAttribute("evaluatorsMap", valuatorMap);
			Map<Integer, String>  externalevaluatorsMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeList(valuationScheduleForm.getSubjectId());
			request.getSession().setAttribute("externalEvaluatorsMap", externalevaluatorsMap);
		}else{
		valuationScheduleForm.setEvaluatorsMap(null);
		valuationScheduleForm.setExternalEvaluatorsMap(null);
		}
	}
	
	private void setRequiredDatatoFormEdit(ValuationScheduleForm valuationScheduleForm,
			HttpServletRequest request)	throws Exception{
		
		Map<Integer, String> examNameMap = ValuationScheduleHandler.getInstance().getExamNameByExamType(valuationScheduleForm.getExamType(),valuationScheduleForm.getAcademicYear());
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		valuationScheduleForm.setExamNameList(examNameMap);
		
		if(valuationScheduleForm.getExamId()!=null && !valuationScheduleForm.getExamId().isEmpty()){
			Map<Integer, String> subjectList=ValuationScheduleHandler.getInstance().getSubjectCodeName(
					valuationScheduleForm.getAcademicYear() ,valuationScheduleForm.getDisplaySubType(),
					Integer.parseInt(valuationScheduleForm.getExamId()),valuationScheduleForm.getExamType());
			valuationScheduleForm.setSubjectMap(subjectList);
		}
		
			Map<Integer, String> valuatorMap = ValuationScheduleHandler.getInstance().getValuatorsAllList();
			request.getSession().setAttribute("evaluatorsMap", valuatorMap);
			Map<Integer, String>  externalevaluatorsMap = ValuationScheduleHandler.getInstance().getOtherEmployeeAllList();
			request.getSession().setAttribute("externalEvaluatorsMap", externalevaluatorsMap);
		
/*
		Map<Integer, String>  otherEvaluatorsMap = ValuationScheduleHandler.getInstance().getOtherEmployeeList();
		request.getSession().setAttribute("OtherEvaluatorsMap", otherEvaluatorsMap);*/
		
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered saveDetails input");
		ValuationScheduleForm valuationScheduleForm = (ValuationScheduleForm) form;
		ActionMessages messages = new ActionMessages();
		try{
			setUserId(request, valuationScheduleForm);
			valuationScheduleForm.validate(mapping, request);
			ActionErrors errors = valuationScheduleForm.validate(mapping, request);
			validateForm(valuationScheduleForm, errors, request );
			
			if(errors.isEmpty()){
				valuationScheduleForm.setId(0);
			setUserId(request, valuationScheduleForm);
			
			ExamValuationScheduleDetails scheduleDetails=ValuationScheduleHandler.getInstance().checkForDuplicate(valuationScheduleForm);
			
			if(scheduleDetails!=null ){
				if(scheduleDetails.getIsActive()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.valuation.schedule.add.alreadypresent"));
					saveErrors(request, errors);
					}

					else if(!scheduleDetails.getIsActive()){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.valuation.schedule.add.alreadypresent"));
						saveErrors(request, errors);
					}
			}else{
				
				boolean save = ValuationScheduleHandler.getInstance().saveValuationSchedule(valuationScheduleForm);
				if(save){
					valuationScheduleForm.resetSomeFields();
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuation.schedule.added.success"));
					saveMessages(request, messages);
				}else{
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}
			}
			else{
				saveErrors(request, errors);
			}
			
			setRequiredDatatoForm(valuationScheduleForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationScheduleForm.setErrorMessage(msg);
			valuationScheduleForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit saveDetails input");
		
		return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteValuationSchedule(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered deleteValuationSchedule input");
		ValuationScheduleForm valuationScheduleForm = (ValuationScheduleForm) form;
		ActionMessages messages = new ActionMessages();
		try{
				boolean delete = ValuationScheduleHandler.getInstance().deleteValuationSchedule(valuationScheduleForm);
				if(delete){
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuation.schedule.deleted.success"));
					saveMessages(request, messages);
					valuationScheduleForm.resetFields();
					setRequiredDatatoForm(valuationScheduleForm, request);
				}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationScheduleForm.setErrorMessage(msg);
			valuationScheduleForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit deleteValidationDetails input");
		
		return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
	}
	
	/**
	 * @param valuationScheduleForm
	 * @param errors 
	 * @param request 
	 */
	@SuppressWarnings("deprecation")
	private void validateForm(ValuationScheduleForm valuationScheduleForm, ActionErrors errors, HttpServletRequest request) {
		if(valuationScheduleForm.getExamId() == null || valuationScheduleForm.getExamId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
			saveErrors(request, errors);
		}
		if(valuationScheduleForm.getSubjectId() == null || valuationScheduleForm.getSubjectId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.subject.required"));
			saveErrors(request, errors);
		}
		
		if((valuationScheduleForm.getSelectedEmployeeId() == null || valuationScheduleForm.getSelectedEmployeeId().length==0) && 
			(valuationScheduleForm.getSelectedExternalId() == null || valuationScheduleForm.getSelectedExternalId().length==0)){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
			saveErrors(request, errors);
		}
	/*	if(valuationScheduleForm.getSelectedExternalId() != null && valuationScheduleForm.getEmployeeId().equalsIgnoreCase("Other")){
			if(valuationScheduleForm.getOtherEmpId() == null || valuationScheduleForm.getOtherEmpId().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
				saveErrors(request, errors);
			}
		}*/
		
	}
	
	public void setUserId(HttpServletRequest request, BaseActionForm form){
		HttpSession session = request.getSession(false);
		if(session.getAttribute("uid")!=null){
			form.setUserId(session.getAttribute("uid").toString());
		}
		request.getSession().removeAttribute("baseActionForm");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editValuationSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into---- ValuationScheduleAction --- editValuationSchedule");
		ValuationScheduleForm scheduleForm=(ValuationScheduleForm)form;
		
		try {
			String exDate = "";
			scheduleForm.setEmployeeId(null);
			scheduleForm.setExternalEmployeeId(null);
			setRequiredDatatoFormEdit(scheduleForm, request);
			ValuationScheduleHandler.getInstance().getDetailsonId(scheduleForm);
			List<ValuationScheduleTO> list =  ValuationScheduleHandler.getInstance().getExamNameListSchedule(scheduleForm.getExamId(),scheduleForm.getAcademicYear());
			scheduleForm.setValuationDetails(list);
			exDate = CommonAjaxHandler.getInstance().getExamDateBySubject(scheduleForm.getExamId(),scheduleForm.getSubjectId(),request);
			exDate = CommonUtil.formatSqlDate1(exDate);
			scheduleForm.setExamDate(exDate);
			request.setAttribute("display_Date", exDate);
			request.setAttribute("editType", CMSConstants.EDIT_OPERATION);
			
		} catch (Exception e) {
			log.error("Error in editing ValuationSchedule in Action",e);
				String msg = super.handleApplicationException(e);
				scheduleForm.setErrorMessage(msg);
				scheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from-- ValuationScheduleAction --- editValuationSchedule");
		return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
	}
	
	public ActionForward updateValuationSchedule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into-- ValuationScheduleAction --- updateValuationSchedule");
		ValuationScheduleForm scheduleForm=(ValuationScheduleForm)form; 
		 ActionErrors errors = scheduleForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty())
			{
				setUserId(request, scheduleForm);
				
				boolean isUpdated;
						
						isUpdated=ValuationScheduleHandler.getInstance().updateValuationSchedule(scheduleForm);
						
						if(isUpdated){
							messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuation.schedule.update.success"));
							saveMessages(request, messages);
							scheduleForm.resetFields();
							setRequiredDatatoForm(scheduleForm, request);
							return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);				
						}
						else {
							messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.exam.valuation.schedule.update.failed"));
							saveMessages(request, messages);
							setRequiredDatatoForm(scheduleForm, request);
							return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
						}
			}
		else{
			setRequiredDatatoForm(scheduleForm, request);
			request.setAttribute("editType", CMSConstants.EDIT_OPERATION);
		}
		}
		catch (Exception e) {
			log.error("Error in updateValuationSchedule in ValuationSchedule Action",e);
				String msg = super.handleApplicationException(e);
				scheduleForm.setErrorMessage(msg);
				scheduleForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		log.info("Leaving from --- ValuationScheduleAction --- updateValuationSchedule");
		saveErrors(request, errors);
		setRequiredDatatoForm(scheduleForm, request);
		return mapping.findForward(CMSConstants.INIT_VALUATION_SCHEDULE);
	}
	

}
