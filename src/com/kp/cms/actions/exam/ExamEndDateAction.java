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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.ExamEndDateForm;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamEndDatesHandlers;
import com.kp.cms.handlers.exam.PublishSupplementaryImpApplicationHandler;
import com.kp.cms.to.exam.ExamEndDateTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamEndDateAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ExamEndDateAction.class);
	
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPublishSupplementary(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered into initPublishSupplementary");
		ExamEndDateForm examEndDateFormForm = (ExamEndDateForm) form;
		examEndDateFormForm.resetFields();
		setRequiredDatatoForm(examEndDateFormForm);
		log.info("Exit from initPublishSupplementary");
		
		return mapping.findForward(CMSConstants.Exam_End_Date);
	}
	
	/**
	 * @param examEndDateFormForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(ExamEndDateForm examEndDateFormForm) throws Exception {
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(examEndDateFormForm.getYear()!=null && !examEndDateFormForm.getYear().isEmpty()){
			year=Integer.parseInt(examEndDateFormForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByYear(String.valueOf(year)); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		examEndDateFormForm.setExamNameList(examNameMap);// setting the examNameMap to form
		List<ExamEndDateTo> toList=ExamEndDatesHandlers.getInstance().getActiveData();
		examEndDateFormForm.setToList(toList);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addOrUpdatePublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into addOrUpdatePublish");
		ExamEndDateForm examEndDateFormForm = (ExamEndDateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examEndDateFormForm.validate(mapping, request);
		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, examEndDateFormForm);
			isPublishSupAppAdded =ExamEndDatesHandlers.getInstance().addOrUpdate(examEndDateFormForm);
			}
				catch (DuplicateException e1) {
					errors.add("error", new ActionError("knowledgepro.admin.examEndDate.name.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examEndDateFormForm);
					return mapping.findForward(CMSConstants.Exam_End_Date);
				}
				catch (ReActivateException e1) {
					errors.add("error", new ActionError("knowledgepro.admin.examEndDate.addfailure.alreadyexist.reactivate","Exam Name"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examEndDateFormForm);
					if(examEndDateFormForm.getMode().equals("update"))
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.Exam_End_Date);	
				} catch (Exception e) {
					log.error("error in final submit of department page...", e);
					if (e instanceof BusinessException) {
						String msgKey = super.handleBusinessException(e);
						ActionMessage message = new ActionMessage(msgKey);
						messages.add("messages", message);
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					} else if (e instanceof ApplicationException) {
						String msg = super.handleApplicationException(e);
						examEndDateFormForm.setErrorMessage(msg);
						examEndDateFormForm.setErrorStack(e.getMessage());
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					} else {
						throw e;
					}
				}
				
			
			if (isPublishSupAppAdded) {
				if(examEndDateFormForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Exam End Date Application");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Exam End Date Application");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				examEndDateFormForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Exam End Date Application"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(examEndDateFormForm);
		if(examEndDateFormForm.getMode().equals("update"))
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.Exam_End_Date);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editExamEndDateId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into edit ExamEndDate type of  ExamEndDateAction");
		ExamEndDateForm examEndDateFormForm = (ExamEndDateForm) form;
			try {
				ExamEndDatesHandlers.getInstance().getDetailsById(examEndDateFormForm);
			setRequiredDatatoForm(examEndDateFormForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in edit ExamEndDate type of  ExamEndDateAction", e);
			String msg = super.handleApplicationException(e);
			examEndDateFormForm.setErrorMessage(msg);
			examEndDateFormForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into eedit ExamEndDate type of  ExamEndDateAction");
		return mapping.findForward(CMSConstants.Exam_End_Date); 	
	}
	
	public ActionForward deleteOrReactivateExamEndDate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into deleteOrReactivate  of  ExamEndDateAction");
		ExamEndDateForm examEndDateFormForm = (ExamEndDateForm) form;	
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examEndDateFormForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, examEndDateFormForm);
			isPublishSupAppAdded =ExamEndDatesHandlers.getInstance().deleteOrReactivate(examEndDateFormForm.getId(),examEndDateFormForm.getMode(),examEndDateFormForm.getUserId());
			}catch (DuplicateException e){
					errors.add("error", new ActionError("knowledgepro.admin.examEndDate.name.exists"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examEndDateFormForm);
						return mapping.findForward(CMSConstants.Exam_End_Date);	
				}
			catch(ReActivateException e){
					errors.add("error", new ActionError("knowledgepro.admin.examEndDate.addfailure.alreadyexist.reactivate","Exam Name"));
					saveErrors(request, errors);
					setRequiredDatatoForm(examEndDateFormForm);
					return mapping.findForward(CMSConstants.Exam_End_Date);	
				}
			catch (Exception e) {
				log.error("error in final submit of department page...", e);
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					examEndDateFormForm.setErrorMessage(msg);
					examEndDateFormForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
			if (isPublishSupAppAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Exam End Date Application");
				messages.add("messages", message);
				saveMessages(request, messages);
				examEndDateFormForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Exam End Date Application"));
				saveErrors(request, errors);
			}
	  }
	else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(examEndDateFormForm);
		return mapping.findForward(CMSConstants.Exam_End_Date);
	}

}
