package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.GrievanceStudentBo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.forms.exam.GrievanceStudentForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.AdminMarksCardHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.handlers.exam.GrievanceStudentHandler;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class GrievanceStudentAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(GrievanceStudentAction.class);
	ExamPublishHallTicketHandler handler = new ExamPublishHallTicketHandler();
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGrievanceStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		GrievanceStudentForm grievanceStudentForm = (GrievanceStudentForm) form;
		grievanceStudentForm.resetFields();
		setRequiredDatatoForm(grievanceStudentForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
	}

	/**
	 * @param adminMarksCardForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(GrievanceStudentForm grievanceStudentForm) throws Exception {
		//grievanceStudentForm.setProgramTypeList(handler.getProgramTypeList());
		//added by Smitha - for new academic year input addition
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(grievanceStudentForm.getYear()!=null && !grievanceStudentForm.getYear().isEmpty()){
			year=Integer.parseInt(grievanceStudentForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
	//	Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(adminMarksCardForm.getExamType());// getting exam list based on exam Type
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(grievanceStudentForm.getExamType(),year); 
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		grievanceStudentForm.setExamNameMap(examNameMap);
		if(grievanceStudentForm.getExamId()!=null && !grievanceStudentForm.getExamId().isEmpty()){
			grievanceStudentForm.setClassMap(CommonUtil.sortMapByValue(CommonAjaxHandler.getInstance().getSchemeNoByExamId(Integer.parseInt(grievanceStudentForm.getExamId()))));
		}
	}
	
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		GrievanceStudentForm grievanceStudentForm = (GrievanceStudentForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		if(grievanceStudentForm.getDate()==null){
		if(grievanceStudentForm.getYear()==null){
			errors.add("error", new ActionError("knowledgepro.admission.year.required"));
		}
		if(grievanceStudentForm.getExamType()==null){
			errors.add("error", new ActionError("knowledgepro.exam.examType.examType.required"));
		}
		if(grievanceStudentForm.getExamId()==null){
			errors.add("error", new ActionError("knowledgepro.exam.examId.required"));
		}
		if(grievanceStudentForm.getClassId()==null){
			errors.add("error", new ActionError("knowledgepro.exam.class.classId.required"));
		}
		}
		
		// ActionErrors errors = grievanceStudentForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, grievanceStudentForm);
				Integer roleId = GrievanceStudentHandler.getInstance().getRoleIdByUserId(grievanceStudentForm);
				grievanceStudentForm.setRoleId(String.valueOf(roleId));
				
				List<GrievanceStudentTo> studentList = GrievanceStudentHandler.getInstance().getStudents(grievanceStudentForm);
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(grievanceStudentForm);
					return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
				}
				//grievanceStudentForm.resetFields();
				grievanceStudentForm.setStudentList(studentList);
				grievanceStudentForm.setDate(null);
				} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				grievanceStudentForm.setErrorMessage(msg);
				grievanceStudentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(grievanceStudentForm);			
			log.info("Exit GrievanceStudentAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
		}
		log.info("Entered GrievanceStudentAction - getCandidates");
		return mapping.findForward(CMSConstants.GRIEVANCE_STUDENT_LIST);
	}
	
	public ActionForward getGrievanceStudentdetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		GrievanceStudentForm grievanceStudentForm = (GrievanceStudentForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = grievanceStudentForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				setUserId(request, grievanceStudentForm);
				List<GrievanceStudentTo> studentList = GrievanceStudentHandler.getInstance().getGrievanceStudentdetails(grievanceStudentForm);
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_GRIEVANCE_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(grievanceStudentForm);
					
					return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
				}
				//grievanceStudentForm.resetFields();
				grievanceStudentForm.setStudentresultList(studentList);
				} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				grievanceStudentForm.setErrorMessage(msg);
				grievanceStudentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(grievanceStudentForm);			
			log.info("Exit GrievanceStudentAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
		}
		log.info("Entered GrievanceStudentAction - getCandidates");
		return mapping.findForward(CMSConstants.GRIEVANCE_STUDENT_RESULT);
	}
	
	public ActionForward saveRemarks(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		ActionMessages messages = new ActionMessages();
		GrievanceStudentForm grievanceStudentForm = (GrievanceStudentForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = grievanceStudentForm.validate(mapping, request);
		 
		if (errors.isEmpty()) {
			try {
				setUserId(request, grievanceStudentForm);
				List<GrievanceStudentTo> studentList = grievanceStudentForm.getStudentresultList();
				List<GrievanceStudentBo> grievanceStudentBoList = new ArrayList<GrievanceStudentBo>();
				if(studentList!=null || !studentList.isEmpty()){// if student list is empty display no record found in the input screen

					Integer roleId = GrievanceStudentHandler.getInstance().getRoleIdByUserId(grievanceStudentForm);
					boolean issaved = GrievanceStudentHandler.getInstance().saveRemarks(studentList,grievanceStudentForm,roleId);
					List<GrievanceStudentTo> studentList1 = GrievanceStudentHandler.getInstance().getGrievanceStudentdetails(grievanceStudentForm);
					if(studentList1!=null && studentList1.size()!=0){
					grievanceStudentForm.setStudentresultList(studentList1);
					}else{
						if(issaved){
						ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_SUCCESSFULLY);
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
						}
					}
					if(issaved && studentList1!=null && studentList1.size()!=0){
						ActionMessage message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_GRIEVANCE_ADDED_SUCCESSFULLY);
						messages.add("messages", message);
						saveMessages(request, messages);
						
					}
					
				}else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_GRIEVANCE_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(grievanceStudentForm);
					
					return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
				}
				
				} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				grievanceStudentForm.setErrorMessage(msg);
				grievanceStudentForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(grievanceStudentForm);			
			log.info("Exit GrievanceStudentAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_GRIEVANCE_STUDENT);
		}
		log.info("Entered GrievanceStudentAction - getCandidates");
		return mapping.findForward(CMSConstants.GRIEVANCE_STUDENT_RESULT);
	}
}
