package com.kp.cms.actions.hostel;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.HostelStudentEvaluationForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.HostelStudentEvaluationHandler;
import com.kp.cms.to.hostel.HostelStudentEvaluationTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

public class HostelStudentEvaluationAction extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(HostelStudentEvaluationAction.class);
	
	public ActionForward initHostelStudentEvaluation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		 
		log.info("start initHostelStudentEvaluation");
		HostelStudentEvaluationForm studentEvaluationForm = (HostelStudentEvaluationForm) form;
		studentEvaluationForm.resetFields1();
		setAllHostelsToForm(studentEvaluationForm);
		setUserId(request, studentEvaluationForm);
		log.info("exit initHostelStudentEvaluation");
		return mapping.findForward(CMSConstants.HOSTEL_STUDENT_EVALUATION_INIT);
	}
	
	/**
	 * setting the required data to Form
	 */
	public void setAllHostelsToForm(HostelStudentEvaluationForm studentEvaluationForm)throws Exception 
	{
		log.info("start setAllHostelsToForm student evaluation");
		List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		studentEvaluationForm.setHostelTOList(hostelList);
		log.info("exit setAllHostelsToForm student evaluation");
	}
	
	public ActionForward getStudentEvaluationDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		
		log.info("start getStudentEvaluationDetails");
		HostelStudentEvaluationForm studentEvaluationForm = (HostelStudentEvaluationForm) form;
		ActionErrors errors = studentEvaluationForm.validate(mapping, request);
		validatDateEntry(studentEvaluationForm,errors);
		try { 
			if (errors.isEmpty()) {
				List<HostelStudentEvaluationTO> evaluationTOList =HostelStudentEvaluationHandler.getInstance().
												getEvaluationList(studentEvaluationForm);
				
				if (evaluationTOList == null) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.HOSTEL_STUDENT_EVALUATION_INIT);
				} else {
					studentEvaluationForm .setEvaluationList(evaluationTOList);
					log.info("exit getStudentEvaluationDetails");
					return mapping.findForward(CMSConstants.HOSTEL_STUDENT_EVALUATION);
				}
			} 
			else {
				saveErrors(request, errors);
				setAllHostelsToForm(studentEvaluationForm);
				log.info("exit setAllHostelsToForm student evaluation with errors");
				return mapping.findForward(CMSConstants.HOSTEL_STUDENT_EVALUATION_INIT);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			studentEvaluationForm.setErrorMessage(msg);
			studentEvaluationForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
	}
	
	 public void validatDateEntry(HostelStudentEvaluationForm studentEvaluationForm,ActionErrors errors){
	
		 if(studentEvaluationForm.getFromDate()!=null && !StringUtils.isEmpty(studentEvaluationForm.getFromDate())&& !CommonUtil.isValidDate(studentEvaluationForm.getFromDate())){
				if (errors.get(CMSConstants.HOSTEL_STUDENT_EVALUATION_FROMDATE_INVALID) != null&& !errors.get(CMSConstants.HOSTEL_STUDENT_EVALUATION_FROMDATE_INVALID).hasNext()) {
					errors.add(CMSConstants.HOSTEL_STUDENT_EVALUATION_FROMDATE_INVALID,new ActionError(CMSConstants.HOSTEL_STUDENT_EVALUATION_FROMDATE_INVALID));
				}
			}
		 if(studentEvaluationForm.getToDate()!=null && !StringUtils.isEmpty(studentEvaluationForm.getToDate())&& !CommonUtil.isValidDate(studentEvaluationForm.getToDate())){
				if (errors.get(CMSConstants.HOSTEL_STUDENT_EVALUATION_TODATE_INVALID) != null&& !errors.get(CMSConstants.HOSTEL_STUDENT_EVALUATION_TODATE_INVALID).hasNext()) {
					errors.add(CMSConstants.HOSTEL_STUDENT_EVALUATION_TODATE_INVALID,new ActionError(CMSConstants.HOSTEL_STUDENT_EVALUATION_TODATE_INVALID));
				}
			}
		 
		 if(errors==null ||errors.isEmpty()){	
		   if(CommonUtil.checkForEmpty(studentEvaluationForm.getFromDate()) && CommonUtil.checkForEmpty(studentEvaluationForm.getToDate())){
				Date startDate = CommonUtil.ConvertStringToDate(studentEvaluationForm.getFromDate());
				Date endDate = CommonUtil.ConvertStringToDate(studentEvaluationForm.getToDate());
	
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
		}
		if(studentEvaluationForm.getAcademicYear()==null || studentEvaluationForm.getAcademicYear().trim().isEmpty()){
		  errors.add(CMSConstants.FEE_ACADEMICYEAR_REQUIRED, new ActionError(CMSConstants.FEE_ACADEMICYEAR_REQUIRED));	
			
		}
	 }
}
