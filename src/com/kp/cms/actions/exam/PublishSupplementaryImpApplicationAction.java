package com.kp.cms.actions.exam;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.PublishSupplementaryImpApplicationHandler;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class PublishSupplementaryImpApplicationAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(PublishSupplementaryImpApplicationAction.class);
	
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
		PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm = (PublishSupplementaryImpApplicationForm) form;
		publishSupplementaryImpApplicationForm.resetFields();
		setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
		log.info("Exit from initPublishSupplementary");
		
		return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);
	}

	/**
	 * @param publishSupplementaryImpApplicationForm
	 * @param messages 
	 * @param request 
	 */
	private void setRequiredDatatoForm(PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm) throws Exception {
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(publishSupplementaryImpApplicationForm.getYear()!=null && !publishSupplementaryImpApplicationForm.getYear().isEmpty()){
			year=Integer.parseInt(publishSupplementaryImpApplicationForm.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(publishSupplementaryImpApplicationForm.getExamType(),year); ;// getting exam list based on exam Type and academic year
		examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
		publishSupplementaryImpApplicationForm.setExamNameList(examNameMap);// setting the examNameMap to form
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(publishSupplementaryImpApplicationForm.getExamType());
		if((publishSupplementaryImpApplicationForm.getExamId()==null || publishSupplementaryImpApplicationForm.getExamId().trim().isEmpty()) && currentExam!=null){
			publishSupplementaryImpApplicationForm.setExamId(currentExam);
		}
		//Added By Manu
		List<PublishSupplementaryImpApplicationTo> toList=null;
		if(year >0 && publishSupplementaryImpApplicationForm.getExamId()!=null && !publishSupplementaryImpApplicationForm.getExamId().isEmpty()){
		  toList=PublishSupplementaryImpApplicationHandler.getInstance().getListOnYearChange(Integer.toString(year),publishSupplementaryImpApplicationForm.getExamId());
			publishSupplementaryImpApplicationForm.setToList(toList);
		}else{
		   publishSupplementaryImpApplicationForm.setToList(toList);
		}
	}

	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward addOrUpdatePublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm = (PublishSupplementaryImpApplicationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = publishSupplementaryImpApplicationForm.validate(mapping, request);
		validateDates(publishSupplementaryImpApplicationForm,errors);
		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, publishSupplementaryImpApplicationForm);
			isPublishSupAppAdded =PublishSupplementaryImpApplicationHandler.getInstance().addOrUpdate(publishSupplementaryImpApplicationForm,errors);
			}catch (Exception e) {
				if(e instanceof DuplicateException ){
					errors.add("error", new ActionError("knowledgepro.admin.publishSupplementary.name.exists",e.getMessage()));
					saveErrors(request, errors);
					setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
					if(publishSupplementaryImpApplicationForm.getMode().equals("update"))
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				publishSupplementaryImpApplicationForm.setErrorMessage(msg);
				publishSupplementaryImpApplicationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				if(publishSupplementaryImpApplicationForm.getMode().equalsIgnoreCase("add")){
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess","Publish Supplementary Improvement Application");
					messages.add("messages", message);
				}
				else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.updatesuccess","Publish Supplementary Improvement Application");
					messages.add("messages", message);
				}
				saveMessages(request, messages);
				publishSupplementaryImpApplicationForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("kknowledgepro.admin.addfailure","Publish Supplementary Improvement Application"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
		if(publishSupplementaryImpApplicationForm.getMode().equals("update"))
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);
	}
	
	/**
	 * @param publishSupplementaryImpApplicationForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateDates( PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm, ActionErrors errors) throws Exception {
		if(CommonUtil.checkForEmpty(publishSupplementaryImpApplicationForm.getStartDate()) && CommonUtil.checkForEmpty(publishSupplementaryImpApplicationForm.getEndDate())){
			Date startDate = CommonUtil.ConvertStringToDate(publishSupplementaryImpApplicationForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(publishSupplementaryImpApplicationForm.getEndDate());

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

	/**
	 * Performs the add Caste action.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return The forward to which control should be transferred.
     * @throws Exception if an exception occurs
	 */
	public ActionForward deleteOrReactivatePublish(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered into initPublishSupplementary");
		PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm = (PublishSupplementaryImpApplicationForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = publishSupplementaryImpApplicationForm.validate(mapping, request);

		boolean isPublishSupAppAdded = false;
		if (errors.isEmpty()) {
			try{
			setUserId(request, publishSupplementaryImpApplicationForm);
			isPublishSupAppAdded =PublishSupplementaryImpApplicationHandler.getInstance().deleteOrReactivate(publishSupplementaryImpApplicationForm.getId(),publishSupplementaryImpApplicationForm.getMode(),publishSupplementaryImpApplicationForm.getUserId());
			}catch (Exception e) {
				if(e instanceof DuplicateException){
					errors.add("error", new ActionError("knowledgepro.admin.publishSupplementary.name.exists","Publish Supplementary"));
					saveErrors(request, errors);
					setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
					return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);	
				}
				log.error("Error occured in caste Entry Action", e);
				String msg = super.handleApplicationException(e);
				publishSupplementaryImpApplicationForm.setErrorMessage(msg);
				publishSupplementaryImpApplicationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			}
			if (isPublishSupAppAdded) {
				ActionMessage message = new ActionMessage("knowledgepro.admin.deletesuccess","Publish Supplementary Application");
				messages.add("messages", message);
				saveMessages(request, messages);
				publishSupplementaryImpApplicationForm.resetFields();
			}else{
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.addfailure","Publish Supplementary Application"));
				saveErrors(request, errors);
			}
		} else {
			saveErrors(request, errors);
		}
		setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
		return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);
	}
	/**
	 * Used when edit button is clicked
	 */

	public ActionForward editPublishSupplementaryApp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into editRoomType of RoomType Action");
		PublishSupplementaryImpApplicationForm publishSupplementaryImpApplicationForm = (PublishSupplementaryImpApplicationForm) form;
		try {
			PublishSupplementaryImpApplicationHandler.getInstance().getDetailsById(publishSupplementaryImpApplicationForm);
			setRequiredDatatoForm(publishSupplementaryImpApplicationForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			publishSupplementaryImpApplicationForm.setErrorMessage(msg);
			publishSupplementaryImpApplicationForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into editRoomType of RoomType Action");
		return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY); 	
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *added by mahi
	 */
	public ActionForward loadClassByExamNameAndYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		PublishSupplementaryImpApplicationForm actionForm= (PublishSupplementaryImpApplicationForm) form;
		try {
			Map<Integer, String> classMap=PublishSupplementaryImpApplicationHandler.getInstance().loadClassByExamNameAndYear(actionForm);
			classMap=CommonUtil.sortMapByValueForAlphaNumeric(classMap);
			actionForm.setMapClass(classMap);
	//Added By manu.
			Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(actionForm.getExamType(),Integer.parseInt(actionForm.getYear())); ;// getting exam list based on exam Type and academic year
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
			actionForm.setExamNameList(examNameMap);// setting the examNameMap to form
			List<PublishSupplementaryImpApplicationTo> toList=PublishSupplementaryImpApplicationHandler.getInstance().getListOnYearChange(actionForm.getYear(),actionForm.getExamId());
			actionForm.setToList(toList);
	//end by manu
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		return mapping.findForward(CMSConstants.INIT_PUBLISH_SUPPLEMENTARY);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setListOnYearChange(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PublishSupplementaryImpApplicationForm actionForm= (PublishSupplementaryImpApplicationForm) form;
		if (actionForm.getYear()!= null && actionForm.getExamId()!=null && !actionForm.getExamId().isEmpty()) {
			try {
				List<PublishSupplementaryImpApplicationTo> toList=PublishSupplementaryImpApplicationHandler.getInstance().getListOnYearChange(actionForm.getYear(),actionForm.getExamId());
					actionForm.setToList(toList);
					request.setAttribute("ToList",toList);
			  } catch (Exception e) {
				log.debug(e.getMessage());
		 	}
		 }else{
			 request.setAttribute("ToList",null);
		 }
		return mapping.findForward("ajaxListOnYearChange");
	}
}