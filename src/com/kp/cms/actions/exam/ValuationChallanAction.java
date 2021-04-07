package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ValuationChallanForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.ValuationChallanHandler;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.ValuationChallanDetailsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ValuationChallanAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ValuationChallanAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationChallan ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFields();
		
		valuationChallanForm.setEmployeeId(null);
		try{
			setRequiredDatatoForm(valuationChallanForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationChallan ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN);
	}
	/**
	 * @param newSecuredMarksEntryForm
	 * @param request
	 */
	@SuppressWarnings("unchecked")
	private void setRequiredDatatoForm(ValuationChallanForm valuationChallanForm,HttpServletRequest request) throws Exception{
		
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		if(valuationChallanForm.getAcademicYear()!=null && !valuationChallanForm.getAcademicYear().isEmpty()){
			currentYear=Integer.parseInt(valuationChallanForm.getAcademicYear());
		}else{
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			 if(year!=0){
				currentYear=year;
				
			}
			 valuationChallanForm.setAcademicYear(Integer.toString(currentYear));
		}
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(valuationChallanForm.getExamType());
		if((valuationChallanForm.getExamId()==null || valuationChallanForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			valuationChallanForm.setExamId(currentExam);
		HttpSession session = request.getSession();
		ValuationChallanHandler.getInstance().getEmployeeList(session);
		Map<Integer, String>  otherEvaluatorsMap = ExamValidationDetailsHandler.getInstance().getOtherEmployeeList(valuationChallanForm.getSubjectId());
		request.getSession().setAttribute("OtherEvaluatorsMap", otherEvaluatorsMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchValuationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm,errors,request);
			valuationChallanForm.resetSomeFields();
			if(errors.isEmpty()){
				ValuationChallanHandler.getInstance().searchValuationDetails(valuationChallanForm);
//				ValuationChallanHandler.getInstance().getGeneratedChallanDetailsForInput(valuationChallanForm);
				if(valuationChallanForm.getMap()== null || valuationChallanForm.getMap().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(valuationChallanForm, request);
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN);
	}
	/**
	 * @param valuationChallanForm
	 * @param errors
	 * @param request
	 */
	private void validateForm(ValuationChallanForm valuationChallanForm,ActionErrors errors, HttpServletRequest request) {
		if(valuationChallanForm.getEmployeeId() == null || valuationChallanForm.getEmployeeId().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
			saveErrors(request, errors);
		}
		if(valuationChallanForm.getEmployeeId() != null && valuationChallanForm.getEmployeeId().equalsIgnoreCase("Other")){
			if(valuationChallanForm.getOtherEmpId() == null || valuationChallanForm.getOtherEmpId().isEmpty())
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.employee.required"));
			saveErrors(request, errors);
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
	public ActionForward saveDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFieldsEmpDetails();
		setUserId(request, valuationChallanForm);
		ActionErrors errors=new ActionErrors();
		try{
			validationForMarksEntry(valuationChallanForm, errors, request);
			if(errors.isEmpty()){
			validateForm(valuationChallanForm, errors, request);
			ValuationChallanHandler.getInstance().saveDetails(valuationChallanForm);
//			ValuationChallanHandler.getInstance().getDetailsForChallanPrint(valuationChallanForm, request);
			valuationChallanForm.setViewFields(false);
			valuationChallanForm.setEmployeeId(null);
			valuationChallanForm.setPrintPage("true");
			valuationChallanForm.setStartDate(null);
			valuationChallanForm.setEndDate(null);
			}else{
				setBoardMeetingSelection(valuationChallanForm);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printChallanPrint(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printChallanPrint ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.setPrintPage(null);
		log.info("Exit printChallanPrint ");
		if(valuationChallanForm.getPrintExternal())
			return mapping.findForward(CMSConstants.VALUATION_CHALLAN_EXTERNAL_PRINT);
		else
			return mapping.findForward(CMSConstants.VALUATION_CHALLAN_PRINT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rePrintDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFieldsEmpDetails();
		setUserId(request, valuationChallanForm);
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm, errors, request);
			ValuationChallanHandler.getInstance().getDetailsForChallanPrint(valuationChallanForm, request);
			valuationChallanForm.setViewFields(false);
			valuationChallanForm.setEmployeeId(null);
			valuationChallanForm.setPrintPage("true");
			valuationChallanForm.setStartDate(null);
			valuationChallanForm.setEndDate(null);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN);
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.actions.BaseDispatchAction#setUserId(javax.servlet.http.HttpServletRequest, com.kp.cms.forms.BaseActionForm)
	 */
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
	public ActionForward initReprintChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationChallan ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFields();
		valuationChallanForm.setAnswerScripts(null);
		valuationChallanForm.setEmployeeId(null);
		try{
			setRequiredDatatoForm(valuationChallanForm, request);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationChallan ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN_REPRINT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getGeneratedChallanDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm,errors,request);
			valuationChallanForm.resetSomeFields();
			if(errors.isEmpty()){
				ValuationChallanHandler.getInstance().getGeneratedChallanDetailsForInput(valuationChallanForm);
				if(valuationChallanForm.getGeneratedChallnList() == null || valuationChallanForm.getGeneratedChallnList().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		setRequiredDatatoForm(valuationChallanForm, request);
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN_REPRINT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reprintChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initValuationChallan ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFields();
		try{
			ValuationChallanHandler.getInstance().getValuationDetailsForRePrint(valuationChallanForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit initValuationChallan ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN_REPRINT_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAndPrintChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFieldsEmpDetails();
		setUserId(request, valuationChallanForm);
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm, errors, request);
			ValuationChallanHandler.getInstance().saveDetails(valuationChallanForm);
			ValuationChallanHandler.getInstance().getDetailsForChallanPrint(valuationChallanForm, request);
			valuationChallanForm.setViewFields(false);
			valuationChallanForm.setEmployeeId(null);
			valuationChallanForm.setPrintPage("true");
			valuationChallanForm.setStartDate(null);
			valuationChallanForm.setEndDate(null);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN_REPRINT_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveAndRePrintChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFieldsEmpDetails();
		setUserId(request, valuationChallanForm);
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm, errors, request);
			ValuationChallanHandler.getInstance().saveChallanDetails(valuationChallanForm);
			ValuationChallanHandler.getInstance().getDetailsForChallanPrint(valuationChallanForm, request);
			valuationChallanForm.setViewFields(false);
			valuationChallanForm.setEmployeeId(null);
			valuationChallanForm.setPrintPage("true");
			valuationChallanForm.setStartDate(null);
			valuationChallanForm.setEndDate(null);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN_REPRINT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rePrintValuationChallan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered printChallanPrint ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.setPrintPage("false");
		log.info("Exit printChallanPrint ");
		if(valuationChallanForm.getPrintExternal())
			return mapping.findForward(CMSConstants.VALUATION_CHALLAN_EXTERNAL_PRINT);
		else
			return mapping.findForward(CMSConstants.VALUATION_CHALLAN_PRINT);
	}
	
	private void validationForMarksEntry(ValuationChallanForm valuationChallanForm,ActionErrors errors, HttpServletRequest request)throws Exception {
		boolean isValuationCompleted=true;
		String subjectName="";
		String[] subjetNames;
		Map<Integer,List<Integer>> examMap=valuationChallanForm.getExamMap();
		if(examMap!=null && !examMap.isEmpty()){
		Iterator<Entry<Integer,List<Integer>>> itr = examMap.entrySet().iterator();
		while (itr .hasNext()) {
			Map.Entry<Integer,List<Integer>> entry = (Map.Entry<Integer,List<Integer>>) itr.next();
			List<Integer> subList=entry.getValue();
			int examId=entry.getKey();
			List<ExamValuationStatusTO> getStatisticsSubjectWise=ValuationChallanHandler.getInstance().getStatisticsSubjectWise(subList,examId);
			if(getStatisticsSubjectWise!=null && !getStatisticsSubjectWise.isEmpty()){
				Iterator<ExamValuationStatusTO> itr1=getStatisticsSubjectWise.iterator();
				while(itr1.hasNext()){
					ExamValuationStatusTO to=(ExamValuationStatusTO)itr1.next();
					if(to.getTotalStudent()!=to.getTotalEntered()){
						isValuationCompleted=false;
						if(subjectName.isEmpty()){
							subjectName=to.getSubjectName()+"("+to.getSubjectCode()+")";
						}else{
							subjetNames=subjectName.split(",");
							for (int x = 0; x < subjetNames.length; x++) {
								if (subjetNames[x] != null && subjetNames[x].length() > 0) {
									if(!subjetNames[x].equalsIgnoreCase(to.getSubjectName()+"("+to.getSubjectCode()+")")){
										subjectName=subjectName+","+to.getSubjectName()+"("+to.getSubjectCode()+")";
									}
								}
							}
						}
					}
				}
			}
			
						}
		}
		if(!isValuationCompleted){
			errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.valuation.challana.marks.entry.pending",subjectName));
			//errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.valuation.challana.can.not.generated.msg"));
			errors.add(CMSConstants.ERRORS,new ActionError("knowledgepro.valuation.challana.Please.check.valuation.status.or.dashboard.msg"));
			saveErrors(request, errors);
		}
	}
	
	public ActionForward saveDetailsFromWarningMessage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchValuationDetails ");
		ValuationChallanForm valuationChallanForm  = (ValuationChallanForm)form;
		valuationChallanForm.resetFieldsEmpDetails();
		setUserId(request, valuationChallanForm);
		ActionErrors errors=new ActionErrors();
		try{
			validateForm(valuationChallanForm, errors, request);
			ValuationChallanHandler.getInstance().saveDetails(valuationChallanForm);
//			ValuationChallanHandler.getInstance().getDetailsForChallanPrint(valuationChallanForm, request);
			valuationChallanForm.setViewFields(false);
			valuationChallanForm.setEmployeeId(null);
			valuationChallanForm.setPrintPage("true");
			valuationChallanForm.setStartDate(null);
			valuationChallanForm.setEndDate(null);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationChallanForm.setErrorMessage(msg);
			valuationChallanForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit searchValuationDetails ");
		return mapping.findForward(CMSConstants.VALUATION_CHALLAN);
	}
	
	public void setBoardMeetingSelection(ValuationChallanForm valuationChallanForm){
		List<ValuationChallanDetailsTO> valuationDetailsList=valuationChallanForm.getValuationDetailsList();
		if(valuationDetailsList!=null && !valuationDetailsList.isEmpty()){
			Iterator<ValuationChallanDetailsTO> iterator1 = valuationDetailsList.iterator();
			while (iterator1.hasNext()) {
				ValuationChallanDetailsTO valuationChallanTO = (ValuationChallanDetailsTO)iterator1.next();
				if(valuationChallanTO.getChecked()==null || !valuationChallanTO.getChecked().equalsIgnoreCase("on")){
					valuationChallanTO.setIsBoardMeetingApplicable("off");
				}else{
					valuationChallanTO.setIsBoardMeetingApplicable("on");
				}
			}
		}
		
	}
}