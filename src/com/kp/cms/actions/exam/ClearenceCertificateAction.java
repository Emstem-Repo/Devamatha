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
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ClearenceCertificateForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxExamHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ClearenceCertificateHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.exam.ClearanceCertificateTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ClearenceCertificateAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AdminMarksCardAction.class);
	//ExamPublishHallTicketHandler handler = new ExamPublishHallTicketHandler();
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initClearenceCertificate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		ClearenceCertificateForm clearenceCertificateForm=(ClearenceCertificateForm)form;
		clearenceCertificateForm.resetFields();
		setRequiredDatatoForm(clearenceCertificateForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_ADMIN_CLEARANCECERTIFICATE);
	}
	/**
	 * @param clearenceCertificateForm
	 */
	private void setRequiredDatatoForm( ClearenceCertificateForm clearenceCertificateForm) throws Exception {
//		Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(clearenceCertificateForm.getExamType());// getting exam list based on exam Type
//		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
//		clearenceCertificateForm.setExamNameMap(examNameMap);
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(clearenceCertificateForm.getYear()!=null && !clearenceCertificateForm.getYear().isEmpty()){
			year=Integer.parseInt(clearenceCertificateForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(clearenceCertificateForm.getExamType(),year); 
		clearenceCertificateForm.setExamNameMap(examMap);
		if(clearenceCertificateForm.getExamId()!=null && !clearenceCertificateForm.getExamId().isEmpty()){
			clearenceCertificateForm.setClassMap(CommonUtil.sortMapByValue(ExamBlockUnblockHallTicketHandler.getInstance().getClassCodeByExamName(Integer.parseInt(clearenceCertificateForm.getExamId()))));
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
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		ClearenceCertificateForm clearenceCertificateForm=(ClearenceCertificateForm)form;// Type casting the Action form to Required Form
		 ActionErrors errors = clearenceCertificateForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				clearenceCertificateForm.setMsg(null);
				List<ClearanceCertificateTO> studentList=ClearenceCertificateHandler.getInstance().getBlockStudentsForInput(clearenceCertificateForm,request);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					if(clearenceCertificateForm.getMsg()!=null && !clearenceCertificateForm.getMsg().isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message",clearenceCertificateForm.getMsg()));
					}else{
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					}
					saveErrors(request, errors);
					setRequiredDatatoForm(clearenceCertificateForm);
					clearenceCertificateForm.setPrint(false);
					return mapping.findForward(CMSConstants.INIT_ADMIN_CLEARANCECERTIFICATE);
				}
				clearenceCertificateForm.setStudentList(studentList);
				String hallMark=clearenceCertificateForm.getHallTicketOrMarksCard();
				clearenceCertificateForm.resetFields();
				clearenceCertificateForm.setHallTicketOrMarksCard(hallMark);
				clearenceCertificateForm.setPrint(true);
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				HttpSession session=request.getSession();
				byte[] logo=null;
				if (organisation != null) {
					logo = organisation.getLogo();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
				}
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				clearenceCertificateForm.setErrorMessage(msg);
				clearenceCertificateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(clearenceCertificateForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_ADMIN_CLEARANCECERTIFICATE);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_ADMIN_CLEARANCECERTIFICATE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printClearaneceCertificate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMIN_CLEARANCE_CERTIFICATE_RESULT);
	}
}