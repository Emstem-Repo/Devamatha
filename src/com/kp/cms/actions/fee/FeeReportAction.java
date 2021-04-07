package com.kp.cms.actions.fee;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;

import com.kp.cms.forms.fee.FeeReportForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.fee.FeeReportHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CommonUtil;



/**
 * @author kalyan.c
 * This class is implemented for Fee Report
 */
@SuppressWarnings("deprecation")
public class FeeReportAction extends BaseDispatchAction  {
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method is used to get Fee report search page
	 */
	public ActionForward initFeeReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			
		FeeReportForm feeReportForm = (FeeReportForm)form;
		setRequiredDataToForm(feeReportForm,request);
		feeReportForm.resetFields();
		HttpSession session = request.getSession(false);
		session.removeAttribute(CMSConstants.FEE_REPORT);
		return mapping.findForward(CMSConstants.INIT_FEE_REPORT);
}

/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 * This method will give the search result of the selected criteria
 */
public ActionForward submitFeeReport(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
	
	FeeReportForm feeReportForm = (FeeReportForm)form;
	HttpSession session = request.getSession(false);
	if(session.getAttribute(CMSConstants.FEE_REPORT)==null)
	{
		ActionErrors errors = new ActionErrors();
	
		validateFeeReport(feeReportForm,errors);
		validateForNumaric(feeReportForm,errors);
		if (errors.isEmpty()) {
		
		try{
			String applno = feeReportForm.getApplicationNo();
			String regNo = feeReportForm.getRegNo();
			if(applno!=null && applno!=""){
				List feeReportTOList =  FeeReportHandler.getInstance().getFeePaymentsByApplicationNoAndSems(applno);
				feeReportForm.setStudentSearch(feeReportTOList);
				session.setAttribute(CMSConstants.FEE_REPORT,feeReportTOList );
			}
			if(regNo!=null && regNo!=""){
				List feeReportTOList =  FeeReportHandler.getInstance().getFeePaymentsByRegistrationNoAndSems(regNo);
				feeReportForm.setStudentSearch(feeReportTOList);
				session.setAttribute(CMSConstants.FEE_REPORT,feeReportTOList );
			}
			String status = "";
			if(feeReportForm.getStatus()!=null){
				status = feeReportForm.getStatus();
			}
			if(CommonUtil.checkForEmpty(feeReportForm.getProgramTypeId()) && CommonUtil.checkForEmpty(feeReportForm.getProgramId()) && CommonUtil.checkForEmpty(feeReportForm.getCourseId()) && CommonUtil.checkForEmpty(feeReportForm.getAcademicYear()) && CommonUtil.checkForEmpty(feeReportForm.getSemister())){
				List feeReportTOList =  FeeReportHandler.getInstance().getFeePaymentsByStudentDetails(Integer.parseInt(feeReportForm.getCourseId()),Integer.parseInt(feeReportForm.getAcademicYear()),Integer.parseInt(feeReportForm.getSemister()),status);
				feeReportForm.setStudentSearch(feeReportTOList);
				session.setAttribute(CMSConstants.FEE_REPORT,feeReportTOList );
			}
			}catch(ApplicationException ae){
				String msg=super.handleApplicationException(ae);
				feeReportForm.setErrorMessage(msg);
				feeReportForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
						throw e;
			}
		} else {
			setRequiredDataToForm(feeReportForm,request);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_FEE_REPORT);
		}
	}
	return mapping.findForward(CMSConstants.SUBMIT_FEE_REPORT);
}


/*
 * This method sets the required data to form and request.
 */
public void setRequiredDataToForm(FeeReportForm feeForm,HttpServletRequest request) throws Exception{
	    
	    //setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
}
/**
 * @param feeForm
 * @param errors
 * This method is used to validate numarics
 */
private void validateForNumaric(FeeReportForm feeForm,
		ActionErrors errors) {
	if (errors == null){
		errors = new ActionErrors();}
	if(CommonUtil.checkForEmpty(feeForm.getApplicationNo())){
		if(!StringUtils.isNumeric(feeForm.getApplicationNo())){
			if(errors.get(CMSConstants.KNOWLEDGEPRO_FEE_APPLNO_REPORT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_FEE_APPLNO_REPORT).hasNext()){									
				errors.add(CMSConstants.KNOWLEDGEPRO_FEE_APPLNO_REPORT,new ActionError(CMSConstants.KNOWLEDGEPRO_FEE_APPLNO_REPORT));
			}
		}			
	}
	if(CommonUtil.checkForEmpty(feeForm.getRegNo())){
		if(!StringUtils.isNumeric(feeForm.getRegNo())){
			if(errors.get(CMSConstants.KNOWLEDGEPRO_FEE_REGNO_REPORT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_FEE_REGNO_REPORT).hasNext()){									
				errors.add(CMSConstants.KNOWLEDGEPRO_FEE_REGNO_REPORT,new ActionError(CMSConstants.KNOWLEDGEPRO_FEE_REGNO_REPORT));
			}
		}			
	}

}
/**
 * @param feeForm
 * @param errors
 * This method is used to validate required fields
 */
private void validateFeeReport(FeeReportForm feeForm,
		ActionErrors errors) {
	if (errors == null)
		errors = new ActionErrors();
	if(!CommonUtil.checkForEmpty(feeForm.getApplicationNo()) && !CommonUtil.checkForEmpty(feeForm.getRegNo())){
		if (!CommonUtil.checkForEmpty(feeForm.getProgramTypeId())
				 |!CommonUtil.checkForEmpty(feeForm.getProgramId())
				 |!CommonUtil.checkForEmpty(feeForm.getCourseId())
				 |!CommonUtil.checkForEmpty(feeForm.getAcademicYear())
				 |!CommonUtil.checkForEmpty(feeForm.getSemister())) {
			errors.add("error", new ActionError(
					CMSConstants.KNOWLEDGEPRO_FEE_APPREGNO_REPORT));
			if(!CommonUtil.checkForEmpty(feeForm.getProgramTypeId())){
				errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_PROGRAMTYPE_REQUIRED));
			}
			if(!CommonUtil.checkForEmpty(feeForm.getProgramId())){		
			errors.add("error", new ActionError(
					CMSConstants.INTERVIEWPROCESSFORM_PROGRAM_REQUIRED));
			}
			if(!CommonUtil.checkForEmpty(feeForm.getCourseId())){		
				errors.add("error", new ActionError(
						CMSConstants.INTERVIEWPROCESSFORM_COURSE_REQUIRED));
				}
			if(!CommonUtil.checkForEmpty(feeForm.getAcademicYear())){
			errors.add("error", new ActionError(
					CMSConstants.KNOWLEDGEPRO_FEE_APPLIYEAR_REPORT));
			}
			if(!CommonUtil.checkForEmpty(feeForm.getSemister())){
				errors.add("error", new ActionError(
						CMSConstants.KNOWLEDGEPRO_FEE_SEMESTER_REPORT));
				}
	
		}
	}
}

}
