package com.kp.cms.actions.exam;

import java.util.Calendar;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.ValuationStatisticsForm;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamValidationDetailsHandler;
import com.kp.cms.handlers.exam.ValuationStatisticsHandler;
import com.kp.cms.to.exam.ValuationStatisticsTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ValuationStatisticsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ValuationStatisticsAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationStatistics (ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of initValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		valuationStatisticsForm.reset();
		try{
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			if(valuationStatisticsForm.getAcademicYear()!=null && !valuationStatisticsForm.getAcademicYear().isEmpty()){
				currentYear=Integer.parseInt(valuationStatisticsForm.getAcademicYear());
			}else{
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				 if(year!=0){
					currentYear=year;
					
				}
				 valuationStatisticsForm.setAcademicYear(Integer.toString(currentYear));
			}
			String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(valuationStatisticsForm.getExamType());
			if((valuationStatisticsForm.getExamId()==null || valuationStatisticsForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
				valuationStatisticsForm.setExamId(currentExam);
			Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(valuationStatisticsForm.getExamType(),valuationStatisticsForm.getAcademicYear());
			
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
			valuationStatisticsForm.setExamNameList(examNameMap);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initValuationStatistics ");
	    return mapping.findForward(CMSConstants.INIT_VALUATION_STATISTICS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuationStatistics(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of getValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(valuationStatisticsForm.getExamId() == null || valuationStatisticsForm.getExamId().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(errors.isEmpty()){
				ValuationStatisticsTO statisticsTO = ValuationStatisticsHandler.getInstance().getStatistics(valuationStatisticsForm);
				valuationStatisticsForm.setStatistics(statisticsTO);
			}else{
				return mapping.findForward(CMSConstants.INIT_VALUATION_STATISTICS);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving getValuationStatistics ");
	    return mapping.findForward(CMSConstants.VALUATION_STATISTICS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayDeaneryWise(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of getValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		try{
			List<ValuationStatisticsTO> deaneryWise = ValuationStatisticsHandler.getInstance().getStatisticsDeaneryWise(valuationStatisticsForm);
			valuationStatisticsForm.setDeaneryWise(deaneryWise);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving getValuationStatistics ");
	    return mapping.findForward(CMSConstants.VALUATION_STATISTICS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDepartmentWise(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of getValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		try{
			List<ValuationStatisticsTO> dapartmentWise = ValuationStatisticsHandler.getInstance().getStatisticsDepartmentWise(valuationStatisticsForm);
			valuationStatisticsForm.setDepartmentWise(dapartmentWise);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving getValuationStatistics ");
	    return mapping.findForward(CMSConstants.VALUATION_STATISTICS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSubjectWise(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of getValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		try{
			ValuationStatisticsHandler.getInstance().getStatisticsSubjectWise(valuationStatisticsForm);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving getValuationStatistics ");
	    return mapping.findForward(CMSConstants.VALUATION_STATISTICS_SUBJECTS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewValuationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewValuationDetails input");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(valuationStatisticsForm.getExamId() == null || valuationStatisticsForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(valuationStatisticsForm.getSubjectId()==null || valuationStatisticsForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ValuationStatisticsHandler.getInstance().viewValuationDetails(valuationStatisticsForm);
			}else{
				return mapping.findForward(CMSConstants.VALUATION_STATISTICS_SUBJECTS);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewValuationDetails input");
		
		return mapping.findForward(CMSConstants.VALUATION_STATISTICS_STUDENTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewVerificationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered viewVerificationDetails input");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(valuationStatisticsForm.getExamId() == null || valuationStatisticsForm.getExamId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(valuationStatisticsForm.getSubjectId()==null || valuationStatisticsForm.getSubjectId().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.student.achivement.termno.required"));
				saveErrors(request, errors);
			}
			if(errors==null || errors.isEmpty()){
				ValuationStatisticsHandler.getInstance().viewVerificationDetails(valuationStatisticsForm);
			}else{
				return mapping.findForward(CMSConstants.VALUATION_STATISTICS_SUBJECTS);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit viewVerificationDetails input");
		
		return mapping.findForward(CMSConstants.VALUATION_STATISTICS_STUDENTS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initValuationStatisticsForDean(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of initValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		valuationStatisticsForm.reset();
		try{
			valuationStatisticsForm.setForUser("true");
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			if(valuationStatisticsForm.getAcademicYear()!=null && !valuationStatisticsForm.getAcademicYear().isEmpty()){
				currentYear=Integer.parseInt(valuationStatisticsForm.getAcademicYear());
			}else{
				int year=CurrentAcademicYear.getInstance().getAcademicyear();
				 if(year!=0){
					currentYear=year;
					
				}
				 valuationStatisticsForm.setAcademicYear(Integer.toString(currentYear));
			}
			String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(valuationStatisticsForm.getExamType());
			if((valuationStatisticsForm.getExamId()==null || valuationStatisticsForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
				valuationStatisticsForm.setExamId(currentExam);
			Map<Integer, String> examNameMap = ExamValidationDetailsHandler.getInstance().getExamNameByExamType(valuationStatisticsForm.getExamType(),valuationStatisticsForm.getAcademicYear());
			
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
			valuationStatisticsForm.setExamNameList(examNameMap);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving initValuationStatistics ");
	    return mapping.findForward(CMSConstants.INIT_VALUATION_STATISTICS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getValuationStatisticsForUser(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			            HttpServletResponse response) throws Exception {
		log.info("end of getValuationStatistics method in ValuatorChargesAction class.");
		ValuationStatisticsForm valuationStatisticsForm = (ValuationStatisticsForm) form;
		ActionErrors errors=new ActionErrors();
		try{
			if(valuationStatisticsForm.getExamId() == null || valuationStatisticsForm.getExamId().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.validation.exam.required"));
				saveErrors(request, errors);
			}
			if(errors.isEmpty()){
				setUserId(request, valuationStatisticsForm);
				List<ValuationStatisticsTO> dapartmentWise = ValuationStatisticsHandler.getInstance().getStatisticsDepartmentWiseForUser(valuationStatisticsForm);
				valuationStatisticsForm.setDepartmentWise(dapartmentWise);
			}else{
				return mapping.findForward(CMSConstants.INIT_VALUATION_STATISTICS);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			valuationStatisticsForm.setErrorMessage(msg);
			valuationStatisticsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.debug("Leaving getValuationStatistics ");
	    return mapping.findForward(CMSConstants.VALUATION_STATISTICS_DEAN);
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
}
