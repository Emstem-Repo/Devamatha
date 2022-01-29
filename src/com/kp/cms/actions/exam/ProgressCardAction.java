package com.kp.cms.actions.exam;

import java.util.ArrayList;
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
import com.kp.cms.handlers.exam.AdminProgressCardHandler;
import com.kp.cms.handlers.exam.ExamBlockUnblockHallTicketHandler;
import com.kp.cms.handlers.exam.ExamPublishHallTicketHandler;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ProgressCardStudentDetailTO;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ProgressCardAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(ProgressCardAction.class);
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
	public ActionForward initProgressCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initAdminMarksCard input");
		AdminMarksCardForm adminMarksCardForm = (AdminMarksCardForm) form;
		adminMarksCardForm.resetFields();
		setRequiredDatatoForm(adminMarksCardForm);
		log.info("Exit initAdminMarksCard input");
		
		return mapping.findForward(CMSConstants.INIT_ADMIN_PROGRESS_CARD);
	}

	/**
	 * @param adminMarksCardForm
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void setRequiredDatatoForm(AdminMarksCardForm adminMarksCardForm) throws Exception {
		adminMarksCardForm.setProgramTypeList(handler.getProgramTypeList());
		ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
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
		
		
			adminMarksCardForm.setClassMap(CommonUtil.sortMapByValue(iCommonAjax.getClassesByYear(year)));
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */	
	public ActionForward getStudentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdminMarksCardForm adminMarksCardForm = (AdminMarksCardForm) form;
		List<ProgressCardStudentDetailTO> studentList=AdminProgressCardHandler.getInstance().getStudentListByClass(adminMarksCardForm);
		adminMarksCardForm.setCandidateList(studentList);
		return mapping.findForward(CMSConstants.PROGRESS_CARD_STUDENTS_SEARCH);
	}
	
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		
		AdminMarksCardForm adminMarksCardForm = (AdminMarksCardForm) form;// Type casting the Action form to Required Form
		 ActionErrors errors = adminMarksCardForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<ProgressCardTo> studentList=AdminProgressCardHandler.getInstance().getStudentForInput(adminMarksCardForm,request);// getting the student list for input search
				if(studentList==null || studentList.isEmpty()){// if student list is empty display no record found in the input screen
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(adminMarksCardForm);
					adminMarksCardForm.setPrint(false);
					return mapping.findForward(CMSConstants.INIT_ADMIN_PROGRESS_CARD);
				}
				adminMarksCardForm.setProgressCardList(studentList);
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
			return mapping.findForward(CMSConstants.INIT_ADMIN_PROGRESS_CARD);
		}
		log.info("Entered NewExamMarksEntryAction - getCandidates");
		return mapping.findForward(CMSConstants.ADMIN_PROGRESS_CARD);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	
}
