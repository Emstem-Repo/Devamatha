package com.kp.cms.actions.exam;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamMarksVerificationCorrectionForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksVerificationCorrectionHandler;
import com.kp.cms.to.exam.ExamEvaluatorTypeMarksTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamMarksVerificationCorrectionAction extends BaseDispatchAction{ 
	
	private static Log log = LogFactory.getLog(ExamMarksVerificationCorrectionAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamMarksVerificationCorrection(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initExamMarksVerificationCorrection input");
		ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm = (ExamMarksVerificationCorrectionForm) form;// Type casting the Action form to Required Form
		examMarksVerificationCorrectionForm.resetFields();//Reseting the fields for input jsp
		log.info("Exit initExamMarksVerificationCorrection input");
		setRequiredDatatoForm(examMarksVerificationCorrectionForm);
		return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
	}

	/**
	 * @param examMarksVerificationCorrectionForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm) throws Exception{
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(examMarksVerificationCorrectionForm.getYear()!=null && !examMarksVerificationCorrectionForm.getYear().isEmpty()){
			year=Integer.parseInt(examMarksVerificationCorrectionForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(examMarksVerificationCorrectionForm.getExamType(),year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);
		if(examNameMap!=null)
			examMarksVerificationCorrectionForm.setExamMap(examNameMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ExamMarksVerificationCorrectionAction - getStudentMarks");
		ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm = (ExamMarksVerificationCorrectionForm) form;
		ActionErrors errors = examMarksVerificationCorrectionForm.validate(mapping, request);
		validateStudentRegOrRollNo(examMarksVerificationCorrectionForm,errors);
		if (errors.isEmpty()) {
			try {
				boolean isValidStudent=ExamMarksVerificationCorrectionHandler.getInstance().checkValidStudentRegNo(examMarksVerificationCorrectionForm);
				if(!isValidStudent){
					errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Register No"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examMarksVerificationCorrectionForm);			
					return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
				}
				List<ExamEvaluatorTypeMarksTo> stuVerificationMarksList=ExamMarksVerificationCorrectionHandler.getInstance().getStudentMarks(examMarksVerificationCorrectionForm);
				if(stuVerificationMarksList==null || stuVerificationMarksList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.no.data.found"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examMarksVerificationCorrectionForm);			
					return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
				}
				examMarksVerificationCorrectionForm.setVerificationlist(stuVerificationMarksList);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examMarksVerificationCorrectionForm.setErrorMessage(msg);
				examMarksVerificationCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			//saveErrors(request, errors);
			setRequiredDatatoForm(examMarksVerificationCorrectionForm);			
			log.info("Exit ExamMarksVerificationCorrectionAction - getStudentMarks errors not empty ");
			return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
		}
		examMarksVerificationCorrectionForm.setFlag(true);
		log.info("Entered ExamMarksVerificationCorrectionAction - getStudentMarks");
		return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
	}
	/**
	 * @param examMarksVerificationCorrectionForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateStudentRegOrRollNo(ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm,ActionErrors errors) throws Exception{
		if(examMarksVerificationCorrectionForm.getRegisterNo()==null || examMarksVerificationCorrectionForm.getRegisterNo().isEmpty()){
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.registerNo.requred"));
		}
	}
	
	public ActionForward saveMarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ExamMarksVerificationCorrectionAction - saveMarks");
		ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm = (ExamMarksVerificationCorrectionForm) form;// Type casting the Action form to Required Form
			ActionMessages messages=new ActionMessages();
			setUserId(request,examMarksVerificationCorrectionForm);
			try {
				boolean isUpdate=ExamMarksVerificationCorrectionHandler.getInstance().compareIsMarksChangedOrNot(examMarksVerificationCorrectionForm);
				if(!isUpdate){
					//errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.update.marks.failure"));
					//saveErrors(request, errors);
				}else{
					ActionMessage message = new ActionMessage("knowledgepro.exam.update.marks.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				examMarksVerificationCorrectionForm.setErrorMessage(msg);
				examMarksVerificationCorrectionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			examMarksVerificationCorrectionForm.resetFieldsForSaveData();
			setRequiredDatatoForm(examMarksVerificationCorrectionForm);	
			log.info("Entered ExamMarksVerificationCorrectionAction - saveMarks");
		return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
	}
	
	public ActionForward cancelVerificationCorrection(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initExamMarksVerificationCorrection input");
		ExamMarksVerificationCorrectionForm examMarksVerificationCorrectionForm = (ExamMarksVerificationCorrectionForm) form;// Type casting the Action form to Required Form
		examMarksVerificationCorrectionForm.resetFieldsForSaveData();//Reseting the fields for input jsp
		log.info("Exit initExamMarksVerificationCorrection input");
		setRequiredDatatoForm(examMarksVerificationCorrectionForm);
		return mapping.findForward(CMSConstants.Exam_MarksVerification_Correction);
	}
}
