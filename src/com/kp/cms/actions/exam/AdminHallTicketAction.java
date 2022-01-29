package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.AdminHallTicketForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.AdminHallTicketHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.PropertyUtil;

public class AdminHallTicketAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(AdminMarksCardAction.class);
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
	public ActionForward initAdminHallTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		AdminHallTicketForm adminHallTicketForm = (AdminHallTicketForm) form;
		adminHallTicketForm.resetFields();
		setRequiredDatatoForm(adminHallTicketForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_ADMIN_HALL_TICKET);
	}

	/**
	 * @param adminHallTicketForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(AdminHallTicketForm adminHallTicketForm) throws Exception {
//		adminHallTicketForm.setProgramTypeList(handler.getProgramTypeList());
		//added by Smitha - for new academic year input addition
		int year=PropertyUtil.getInstance().getAcademicyear();
		if(adminHallTicketForm.getYear()!=null && !adminHallTicketForm.getYear().isEmpty()){
			year=Integer.parseInt(adminHallTicketForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(adminHallTicketForm.getExamType(),year); 
	//	Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(adminHallTicketForm.getExamType());// getting exam list based on exam Type
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		adminHallTicketForm.setExamNameMap(examNameMap);
		//Newly Added For Making default Current Exam
		String currentExam = ExamMarksEntryHandler.getInstance().getCurrentExamName(adminHallTicketForm.getExamType());
		if((adminHallTicketForm.getExamId()==null || adminHallTicketForm.getExamId().isEmpty()) && currentExam!=null && !currentExam.isEmpty())
			adminHallTicketForm.setExamId(currentExam);
		if(adminHallTicketForm.getExamId()!=null && !adminHallTicketForm.getExamId().isEmpty()){
			adminHallTicketForm.setClassMap(CommonUtil.sortMapByValue(AdminHallTicketHandler.getInstance().getClasesByExamName(adminHallTicketForm.getExamId(),String.valueOf(year))));
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
		AdminHallTicketForm adminHallTicketForm = (AdminHallTicketForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = adminHallTicketForm.validate(mapping, request);
		validateData(adminHallTicketForm,errors);
		adminHallTicketForm.setStudentList(null);
		List<HallTicketTo> studentList=null;
		if (errors.isEmpty()) {
			adminHallTicketForm.setExamTypePrint(adminHallTicketForm.getExamType());
			try {
				if(adminHallTicketForm.getRegNoFrom()!=null && !adminHallTicketForm.getRegNoFrom().isEmpty()){
					HallTicketTo to = AdminHallTicketHandler.getInstance().getHallticketForStudent(adminHallTicketForm);
					if(to != null){
						studentList=new ArrayList<HallTicketTo>();
						studentList.add(to);
					}
				}else{
					if(adminHallTicketForm.getExamType()!=null && adminHallTicketForm.getExamType().equalsIgnoreCase("Regular")){
						studentList=AdminHallTicketHandler.getInstance().getStudentForInput(adminHallTicketForm,request);// getting the student list for input search
					}else if(adminHallTicketForm.getExamType()!=null && adminHallTicketForm.getExamType().equalsIgnoreCase("Supplementary")){
						studentList=AdminHallTicketHandler.getInstance().getStudentForSupplementaryInput(adminHallTicketForm,request);// getting the student list for input search
					}else{
						studentList=AdminHallTicketHandler.getInstance().getStudentForInternalInput(adminHallTicketForm,request);
					}
				}
				
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(adminHallTicketForm);
					adminHallTicketForm.setPrint(false);
					return mapping.findForward(CMSConstants.INIT_ADMIN_HALL_TICKET);
				}
				Collections.sort(studentList);
				adminHallTicketForm.setStudentList(studentList);
				setRequiredDatatoForm(adminHallTicketForm);
				adminHallTicketForm.setPrint(true);
				adminHallTicketForm.setRegNoFrom(null);
				adminHallTicketForm.setRegNoTo(null);
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
				adminHallTicketForm.setErrorMessage(msg);
				adminHallTicketForm.setErrorStack(exception.getMessage());
				adminHallTicketForm.resetFields();
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			adminHallTicketForm.setPrint(false);
			//adminHallTicketForm.resetFields();
			setRequiredDatatoForm(adminHallTicketForm);	
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_ADMIN_HALL_TICKET);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_ADMIN_HALL_TICKET);
	}
	
	
	
	
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printHallTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMIN_HALL_TICKET_RESULT);
	}
	

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printHallTicketSupp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMIN_HALL_TICKET_RESULT_SUPP);
	}
	
	/**
	 * @param adminHallTicketForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateData(AdminHallTicketForm adminHallTicketForm,ActionErrors errors) throws Exception{
		
		/*if(adminHallTicketForm.getExamType() != null && adminHallTicketForm.getExamType().equalsIgnoreCase("Supplementary")){
			if(adminHallTicketForm.getRegNoFrom()==null || adminHallTicketForm.getRegNoFrom().isEmpty()){
				errors.add(CMSConstants.ERROR,  new ActionMessage("knowledgepro.exam.UnvRegEntry.reg_entry"));
			}
		}else{*/
			if ((adminHallTicketForm.getRegNoFrom()==null || adminHallTicketForm.getRegNoFrom().isEmpty()) 
					&& (adminHallTicketForm.getClassId()==null || adminHallTicketForm.getClassId().isEmpty())){
				errors.add(CMSConstants.ERROR,  new ActionMessage("Knowledge.class.registerNo.required"));
			/*}*/
		}
		
	}
			
	
	

	
	
	
}
