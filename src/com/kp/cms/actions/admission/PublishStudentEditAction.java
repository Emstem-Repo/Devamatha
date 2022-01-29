package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.PrintPasswordForm;
import com.kp.cms.forms.admission.PublishStudentEditForm;
import com.kp.cms.handlers.admin.ConvocationSessionHandler;
import com.kp.cms.handlers.admin.PasswordPrintHandler;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admission.PublishStudentEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ConsolidatedSubjectStreamHandler;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admission.PublishStudentEditTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings({ "unused", "deprecation" })
public class PublishStudentEditAction extends BaseDispatchAction{
	private static final String CLASSMAP = "classMap";
	
	public ActionForward initPublishMethod(ActionMapping mapping, ActionForm form,
											HttpServletRequest request, HttpServletResponse response)
											throws Exception {
		PublishStudentEditForm publishStudentEditForm = (PublishStudentEditForm) form;
		publishStudentEditForm.setRegNoFrom(null);
		publishStudentEditForm.setRegNoTo(null);
		publishStudentEditForm.setIsWholeStudent("false");
		publishStudentEditForm.setClassIds(null);
		publishStudentEditForm.setEditEndDate(null);
		publishStudentEditForm.setEditStartDate(null);
		setRequiredDataToForm(publishStudentEditForm,request);
		List<PublishStudentEditTO> publishStudentEditTOs = PublishStudentEditHandler.getInstance().getToList(publishStudentEditForm);
		publishStudentEditForm.setPublishStudentEditTOs(publishStudentEditTOs);
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);		
	}
	
	
	public void setRequiredDataToForm(PublishStudentEditForm publishStudentEditForm,HttpServletRequest request) throws Exception{
			try {
				if (publishStudentEditForm.getAcademicYear() == null || publishStudentEditForm.getAcademicYear().isEmpty()) {
					// Below statements is used to get the current year and for the
					// year get the class Map.
					Calendar calendar = Calendar.getInstance();
					int currentYear = calendar.get(Calendar.YEAR);
		
					int year = CurrentAcademicYear.getInstance().getAcademicyear();
					if (year != 0) {
						currentYear = year;
					}
					Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
					request.setAttribute(CLASSMAP, classMap);
					publishStudentEditForm.setAcademicYear(String.valueOf(currentYear));
					publishStudentEditForm.setClassMap(classMap);
				}
				// Otherwise get the classMap for the selected year
				else {
					int year = Integer.parseInt(publishStudentEditForm.getAcademicYear().trim());
					Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
					request.setAttribute(CLASSMAP, classMap);
					publishStudentEditForm.setAcademicYear(String.valueOf(year));
					publishStudentEditForm.setClassMap(classMap);
					
				}
			}catch (Exception e) {
				throw new ApplicationException(e);
			}
	}
	
	
	@SuppressWarnings("deprecation")
	public ActionForward saveMethod(ActionMapping mapping,ActionForm form,
									HttpServletRequest request,HttpServletResponse response)
									throws Exception{
		PublishStudentEditForm publishStudentEditForm = (PublishStudentEditForm) form;
		ActionMessages errors=new ActionMessages();
		ActionMessages messages = new ActionMessages();
		 ActionErrors actionErrors = publishStudentEditForm.validate(mapping, request);
		try{
			
			boolean isValidRegdNo;
			boolean isAdded=false;
			boolean isDuplicate;
			setUserId(request, publishStudentEditForm);
			if(actionErrors.isEmpty()){
				if(publishStudentEditForm.getRegNoFrom()!=null && !publishStudentEditForm.getRegNoFrom().isEmpty() && publishStudentEditForm.getRegNoTo() != null && !publishStudentEditForm.getRegNoTo().isEmpty())
				{
				isValidRegdNo = validateRegdNos(publishStudentEditForm.getRegNoFrom().trim(), publishStudentEditForm.getRegNoTo().trim()); 
					if(!isValidRegdNo){
						if(publishStudentEditForm.getIsWholeStudent()!= null && "true".equalsIgnoreCase(publishStudentEditForm.getIsWholeStudent())){
							actionErrors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_ROLLNO_TYPE));
						}
						else{
							actionErrors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
						}
						saveErrors(request, actionErrors);
					} 	
					isDuplicate=PublishStudentEditHandler.getInstance().checkDuplicateStudentWise(publishStudentEditForm);
					if(isDuplicate){
						actionErrors.add( "error", new ActionError("knowledgepro.admission.publish.student.edit"));
						saveErrors(request, actionErrors);
						return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
					}
					isAdded = PublishStudentEditHandler.getInstance().publishByRegNowise(publishStudentEditForm);
				}else if(publishStudentEditForm.getAcademicYear()!=null && !publishStudentEditForm.getAcademicYear().isEmpty()){
					
					if(publishStudentEditForm.getClassIds()!=null && !publishStudentEditForm.getClassIds().toString().isEmpty()){
						isDuplicate=PublishStudentEditHandler.getInstance().checkDuplicate(publishStudentEditForm);
						if(isDuplicate){
							if(publishStudentEditForm.getErrorMessage() != null && !publishStudentEditForm.getErrorMessage().isEmpty()){
								actionErrors .add( "error", new ActionError("knowledgepro.admin.publish.student.already.exist.class",publishStudentEditForm.getErrorMessage()));
								saveErrors(request, actionErrors);
							}
							return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
						}
						ActionErrors errorsDate = validateData(publishStudentEditForm);
						saveErrors(request, errorsDate);
						if(errorsDate.isEmpty()){
							isAdded = PublishStudentEditHandler.getInstance().publishByClassId(publishStudentEditForm);
						}
					}else{
						actionErrors .add( "error", new ActionError("admissionFormForm.class.required"));
						saveErrors(request, actionErrors);
					}		
				}else {
					errors .add( "error", new ActionError("knowledgepro.admin.publish.select"));
					saveErrors(request, errors);
				}
				
				if(isAdded){
					ActionMessage message = new ActionMessage("knowledgepro.admin.publish.student.success");
					messages.add("messages", message);
					saveMessages(request, messages);
					List<PublishStudentEditTO> publishStudentEditTOs = PublishStudentEditHandler.getInstance().getToList(publishStudentEditForm);
					publishStudentEditForm.setPublishStudentEditTOs(publishStudentEditTOs);
				}
			}else {
				saveErrors(request, actionErrors);
			}
		}catch (ReActivateException e1) 
		{
			errors.add("error", new ActionError("knowledgepro.studentedit.reactivate"));
			saveErrors(request, errors);			
			return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
	}

	private ActionErrors validateData(PublishStudentEditForm publishStudentEditForm) {
		ActionErrors errors = new ActionErrors();
		Date startDate=null;
		Date endDate=null;
		if ((publishStudentEditForm.getEditStartDate() != null && publishStudentEditForm
				.getEditEndDate().trim().length() > 0)) {
			startDate = new Date(publishStudentEditForm.getEditStartDate());

		}
		if ((publishStudentEditForm.getEditStartDate() != null && publishStudentEditForm
				.getEditEndDate().trim().length() > 0)) {
			endDate = new Date(publishStudentEditForm.getEditEndDate());
		}

		startDate = CommonUtil.ConvertStringToSQLDate(publishStudentEditForm
				.getEditStartDate());
		endDate = CommonUtil.ConvertStringToSQLDate(publishStudentEditForm
				.getEditEndDate());
		int flag  = CommonUtil.getDaysDiff(startDate, endDate);
		if (flag < 0) {
			errors.add("error", new ActionError( "knowledgepro.exam.publishHM.MarksCard.validDate"));

		}

		return errors;
	}
	
	public ActionForward editDetails(ActionMapping mapping,ActionForm form,
									HttpServletRequest request,HttpServletResponse response) throws Exception{
		PublishStudentEditForm publishStudentEditForm =  (PublishStudentEditForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, publishStudentEditForm);
		try{
			PublishStudentEditHandler.getInstance().editDetails(publishStudentEditForm);
			request.setAttribute("publish", "edit");
			request.setAttribute("editId", publishStudentEditForm.getId());
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			publishStudentEditForm.setErrorMessage(msg);
			publishStudentEditForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
	}
	
	public ActionForward updateDetails(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)throws Exception{
		PublishStudentEditForm publishStudentEditForm = (PublishStudentEditForm) form;
		ActionMessages errors=new ActionMessages();
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession(true);
		boolean isDuplicate=false;
		boolean isAdded=false;
		try{
			String studentid = (String) session.getAttribute("editId");
			//isDuplicate=PublishStudentEditHandler.getInstance().checkDuplicate(publishStudentEditForm);
			if(isDuplicate){
				if(publishStudentEditForm.getErrorMessage() != null && !publishStudentEditForm.getErrorMessage().isEmpty()){
					errors .add( "error", new ActionError("knowledgepro.admin.publish.student.already.exist.class",publishStudentEditForm.getErrorMessage()));
					saveErrors(request, errors);
				}
				return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
			}
			isAdded=PublishStudentEditHandler.getInstance().updatePublish(publishStudentEditForm);
			if(isAdded){
				ActionMessage message = new ActionMessage("knowledgepro.admin.publish.student.updated");
				messages.add("messages", message);
				saveMessages(request, messages);
				List<PublishStudentEditTO> publishStudentEditTOs = PublishStudentEditHandler.getInstance().getToList(publishStudentEditForm);
				publishStudentEditForm.setPublishStudentEditTOs(publishStudentEditTOs);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
	}
	
	public ActionForward deleteDetails(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)throws Exception{
		PublishStudentEditForm publishStudentEditForm = (PublishStudentEditForm) form;
		ActionMessages messages = new ActionMessages();
		setUserId(request, publishStudentEditForm);
				try{
					boolean delete = PublishStudentEditHandler.getInstance().deletePublishDate(publishStudentEditForm);
					if (delete) {
						ActionMessage message = new ActionError("knowledgepro.admission.publish.student.edit.deleted");
						messages.add("messages", message);
						saveMessages(request, messages);
						publishStudentEditForm.reset(mapping, request);
						List<PublishStudentEditTO> publishStudentEditTOs = PublishStudentEditHandler.getInstance().getToList(publishStudentEditForm);
						publishStudentEditForm.setPublishStudentEditTOs(publishStudentEditTOs);
					} 
				}catch (Exception e) {
					String msg = super.handleApplicationException(e);
					publishStudentEditForm.setErrorMessage(msg);
					publishStudentEditForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
		
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
	}
	
	public ActionForward reActivateClass(ActionMapping mapping,ActionForm form,
										HttpServletRequest request,HttpServletResponse response)throws Exception{
		PublishStudentEditForm publishStudentEditForm = (PublishStudentEditForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try{
			if(publishStudentEditForm.getDupId()!=0){
				int dupId = publishStudentEditForm.getDupId();
				setUserId(request, publishStudentEditForm);
				isActivated = PublishStudentEditHandler.getInstance().reactiveClass(dupId, true, publishStudentEditForm);
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		if (isActivated) {
			ActionMessage message = new ActionMessage("knowledgepro.studentedit.reactivationSucess");
			messages.add("messages", message);
			saveMessages(request, messages);
			List<PublishStudentEditTO> publishStudentEditTOs = PublishStudentEditHandler.getInstance().getToList(publishStudentEditForm);
			publishStudentEditForm.setPublishStudentEditTOs(publishStudentEditTOs);
		}
		return mapping.findForward(CMSConstants.INIT_PUBLISH_EDIT);
	}
	private boolean validateRegdNos(String regdNoFrom, String regdNoTo) throws Exception{
		if(StringUtils.isAlphanumeric(regdNoFrom) && StringUtils.isAlphanumeric(regdNoTo)){
			return true;
		}
		else{
			return false;
		}
	}
	
}
