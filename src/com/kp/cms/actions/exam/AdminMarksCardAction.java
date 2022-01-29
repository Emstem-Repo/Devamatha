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
import com.kp.cms.forms.exam.AdminMarksCardForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.AdminMarksCardHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class AdminMarksCardAction extends BaseDispatchAction{

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
	public ActionForward initAdminMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		AdminMarksCardForm adminMarksCardForm = (AdminMarksCardForm) form;
		adminMarksCardForm.resetFields();
		setRequiredDatatoForm(adminMarksCardForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_ADMIN_MARKS_CARD);
	}

	/**
	 * @param adminMarksCardForm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setRequiredDatatoForm(AdminMarksCardForm adminMarksCardForm) throws Exception {
		adminMarksCardForm.setProgramTypeList(handler.getProgramTypeList());
		//added by Smitha - for new academic year input addition
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(adminMarksCardForm.getYear()!=null && !adminMarksCardForm.getYear().isEmpty()){
			year=Integer.parseInt(adminMarksCardForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		
	//	Map<Integer, String> examNameMap = CommonAjaxExamHandler.getInstance().getExamNameByExamType(adminMarksCardForm.getExamType());// getting exam list based on exam Type
		Map<Integer,String> examNameMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(adminMarksCardForm.getExamType(),year); 
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		adminMarksCardForm.setExamNameMap(examNameMap);
		if(adminMarksCardForm.getExamId()!=null && !adminMarksCardForm.getExamId().isEmpty()){
			adminMarksCardForm.setClassMap(CommonUtil.sortMapByValue(ExamBlockUnblockHallTicketHandler.getInstance().getClassCodeByExamName(Integer.parseInt(adminMarksCardForm.getExamId()))));
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
		
		AdminMarksCardForm adminMarksCardForm = (AdminMarksCardForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = adminMarksCardForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<MarksCardTO> studentList=AdminMarksCardHandler.getInstance().getStudentForInput(adminMarksCardForm,request);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(adminMarksCardForm);
					adminMarksCardForm.setPrint(false);
					return mapping.findForward(CMSConstants.INIT_ADMIN_MARKS_CARD);
				}
				adminMarksCardForm.setStudentList(studentList);
				adminMarksCardForm.setExam1(adminMarksCardForm.getExamType());
				//adminMarksCardForm.resetFields();
				adminMarksCardForm.setPrint(true);
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				HttpSession session=request.getSession();
				byte[] logo=null;
				if (organisation != null) {
					logo = organisation.getLogo();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
				}
				
				setRequiredDatatoForm(adminMarksCardForm);	
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				adminMarksCardForm.setErrorMessage(msg);
				adminMarksCardForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(adminMarksCardForm);			
			log.info("Exit NewExamMarksEntryAction - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_ADMIN_MARKS_CARD);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_ADMIN_MARKS_CARD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarksCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.ADMIN_MARKS_CARD_RESULT);
	}
	
}
