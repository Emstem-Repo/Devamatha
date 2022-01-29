package com.kp.cms.actions.admission;

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
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.CertificateCourseTeacherForm;
import com.kp.cms.handlers.admission.CertificateCourseEntryHandler;
import com.kp.cms.handlers.admission.CertificateCourseTeacherHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CertificateCourseTeacherAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(CertificateCourseTeacherAction.class);
	
	
	/**
	 * setting the required data to the request
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initCertificateCourseTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CertificateCourseTeacherForm certificateCourseTeacherForm = (CertificateCourseTeacherForm)form;
		log.info("Entering into initCertificateCourseTeacher in CertificateCourseTeacherAction");
		try{
//			certificateCourseTeacherForm.reset();
			setRequestedDataToForm(certificateCourseTeacherForm);
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error occured in CertificateCourseTeacherAction", e);
			String msg = super.handleApplicationException(e);
			certificateCourseTeacherForm.setErrorMessage(msg);
			certificateCourseTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
		}
		log.info("Exit from initCertificateCourseTeacher in CertificateCourseTeacherAction");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
	}


	/**
	 * getting the list of document type and setting into form
	 * @param documentExamEntryForm
	 */
	
	
	
//	public void setCertificateCourseListToRequest(HttpServletRequest request) throws Exception {
//		log.debug("inside setCertificateCourseListToRequest");
//		List<CertificateCourseTO> courseList = CertificateCourseEntryHandler.getInstance().getActiveCourses();
//		request.setAttribute("courseList", courseList);
//		log.debug("leaving setCertificateCourseListToRequest");
//	}
	
	public ActionForward addCertificateCourseTeacher(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		CertificateCourseTeacherForm certificateCourseTeacherForm = (CertificateCourseTeacherForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = certificateCourseTeacherForm.validate(mapping, request);
		certificateCourseTeacherForm.setId(0);
		boolean isAdded;
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		try {
			//mandatory message for start & end time
			if(certificateCourseTeacherForm.getStartHours().trim().equals("00") || certificateCourseTeacherForm.getStartHours().trim().equals("0") || certificateCourseTeacherForm.getStartHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.startHours",new ActionError("knowledgepro.attn.period.startHours"));
			}
			
			if(certificateCourseTeacherForm.getEndHours().trim().equals("00") || certificateCourseTeacherForm.getEndHours().trim().equals("0") || certificateCourseTeacherForm.getEndHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.endHours",new ActionError("knowledgepro.attn.period.endHours"));
			}
		
			//time validation
			if(errors.isEmpty()){
				validateTime(certificateCourseTeacherForm, errors);
			}				
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				int tempYear; 
				if(certificateCourseTeacherForm.getYear() != null && !certificateCourseTeacherForm.getYear().isEmpty()){
					tempYear = Integer.parseInt(certificateCourseTeacherForm.getYear());
				}
				else
				{
					tempYear = currentYear; 
				}
				setRequestedDataToForm(certificateCourseTeacherForm);	//setting classList to form based on the year			
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
			}
			if(errors.isEmpty()){
				int cerCourseId = Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseId());
				int teacherId = Integer.parseInt(certificateCourseTeacherForm.getTeacherId());
				CertificateCourseTeacher certificateCourseTeacher = CertificateCourseTeacherHandler.getInstance().checkDuplicate(cerCourseId, teacherId);
				if(certificateCourseTeacher!=null && certificateCourseTeacher.getIsActive()){
					errors.add("knowledgepro.admission.certificate.course.teacher.duplicate", new ActionError("knowledgepro.admission.certificate.course.teacher.duplicate"));
					setRequestedDataToForm(certificateCourseTeacherForm);
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
				}else if(certificateCourseTeacher!=null && !certificateCourseTeacher.getIsActive()){
					errors.add("knowledgepro.admission.certificate.course.teacher.reactivate", new ActionError("knowledgepro.admission.certificate.course.teacher.reactivate"));
					setRequestedDataToForm(certificateCourseTeacherForm);
					certificateCourseTeacherForm.setId(certificateCourseTeacher.getId());
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
				}else if(certificateCourseTeacher==null){
				setUserId(request, certificateCourseTeacherForm);  //setting user id to update last changed details
				isAdded = CertificateCourseTeacherHandler.getInstance().addCertificateCourseTeacher(certificateCourseTeacherForm);
				setRequestedDataToForm(certificateCourseTeacherForm);
				
				if (isAdded) {
					// success .
					ActionMessage message = new ActionMessage("knowledgepro.admission.certificate.course.teacher.addsuccess");
					messages.add("messages", message);
					saveMessages(request, messages);
					certificateCourseTeacherForm.reset();
					certificateCourseTeacherForm.setStartHours("00");
					certificateCourseTeacherForm.setStartMins("00");
					certificateCourseTeacherForm.setEndHours("00");
					certificateCourseTeacherForm.setEndMins("00");
				}
				} else {
					// failed
					errors.add("error", new ActionError("knowledgepro.admission.certificate.course.teacher.addfailure"));
					saveErrors(request, errors);
				}
				currentYear=CurrentAcademicYear.getInstance().getAcademicyear();
				if(currentYear>0)
					setRequestedDataToForm(certificateCourseTeacherForm);
			}
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.attn.duplicate"));
			saveErrors(request, errors);
			int tempYear = currentYear; 
			if(certificateCourseTeacherForm.getYear() != null && !certificateCourseTeacherForm.getYear().isEmpty()){
				tempYear = Integer.parseInt(certificateCourseTeacherForm.getYear());
			}
			return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				certificateCourseTeacherForm.setErrorMessage(msg);
				certificateCourseTeacherForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			
		}
		log.debug("Leaving action class addPeriod");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);

	}
	public void setRequestedDataToForm(
			CertificateCourseTeacherForm certificateCourseTeacherForm) throws Exception{
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaffByEmployeeName();
		certificateCourseTeacherForm.setTeachersMap(teachersMap);
		List<CertificateCourseTO> courseList = CertificateCourseEntryHandler.getInstance().getActiveCourses(0);
		certificateCourseTeacherForm.setCourseList(courseList);
		List<CertificateCourseTeacherTO> certificateCourseTeacherTOList = CertificateCourseTeacherHandler.getInstance().getList();
		certificateCourseTeacherForm.setCertificateCourseTeacherList(certificateCourseTeacherTOList);
	}
	
	
	private void validateTime(CertificateCourseTeacherForm certificateCourseTeacherForm, ActionErrors errors){
//		if (errors == null){
//			errors = new ActionErrors();}
			//time format checking
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getStartHours())){
			if(Integer.parseInt(certificateCourseTeacherForm.getStartHours())>=24){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}
		
		//time format checking
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getStartMins())){
			if(Integer.parseInt(certificateCourseTeacherForm.getStartMins())>=60){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}		
		
		//time format checking
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getEndHours())){
			if(Integer.parseInt(certificateCourseTeacherForm.getEndHours())>=24){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}		
		
		//time format checking
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getEndMins())){
			if(Integer.parseInt(certificateCourseTeacherForm.getEndMins())>=60){
				if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
					errors.add("knowledgepro.admin.timeformat",new ActionError("knowledgepro.admin.timeformat"));
					return;
				}
			}			
		}
		//if start time greater than end time then showing error message
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getStartHours()) && CommonUtil.checkForEmpty(certificateCourseTeacherForm.getEndHours())){
			if(Integer.parseInt(certificateCourseTeacherForm.getStartHours())> Integer.parseInt(certificateCourseTeacherForm.getEndHours())){
				if(errors.get("knowledgepro.attn.start.greater")!=null && !errors.get("knowledgepro.attn.start.greater").hasNext()){									
					errors.add("knowledgepro.attn.start.greater",new ActionError("knowledgepro.attn.start.greater"));
					return;
				}
			}			
		}
		
		
		
		String startMins; 
		String endMins;
		if(certificateCourseTeacherForm.getStartMins() != null && !certificateCourseTeacherForm.getStartMins().isEmpty()){
			startMins = certificateCourseTeacherForm.getStartMins();
		}
		else{
			startMins = "00";
		}
		if(certificateCourseTeacherForm.getEndMins() != null && !certificateCourseTeacherForm.getEndMins().isEmpty()){
			endMins = certificateCourseTeacherForm.getEndMins();
		}
		else{
			endMins = "00";
		}					

		String startTime = CommonUtil.getTime(certificateCourseTeacherForm.getStartHours(), startMins).trim();
		String endTime = CommonUtil.getTime(certificateCourseTeacherForm.getEndHours(), endMins).trim();
		
		int startTimeInMins = 0;
		int endTimeInMins = 0;
		if(certificateCourseTeacherForm.getStartHours()!= null && !certificateCourseTeacherForm.getStartHours().trim().isEmpty()){
			startTimeInMins = Integer.parseInt(certificateCourseTeacherForm.getStartHours()) * 60;
		}
		if(certificateCourseTeacherForm.getEndHours()!= null && !certificateCourseTeacherForm.getEndHours().trim().isEmpty()){
			endTimeInMins = Integer.parseInt(certificateCourseTeacherForm.getEndHours()) * 60;
		}
		if(certificateCourseTeacherForm.getStartMins()!= null && !certificateCourseTeacherForm.getStartMins().trim().isEmpty()){
			startTimeInMins = startTimeInMins + Integer.parseInt(certificateCourseTeacherForm.getStartMins());
		}
		if(certificateCourseTeacherForm.getEndMins()!= null && !certificateCourseTeacherForm.getEndMins().trim().isEmpty()){
			endTimeInMins = endTimeInMins + Integer.parseInt(certificateCourseTeacherForm.getEndMins());
		}
		
		if(CommonUtil.checkForEmpty(certificateCourseTeacherForm.getStartHours()) && CommonUtil.checkForEmpty(certificateCourseTeacherForm.getEndHours())){
			 if(startTime.equals(endTime) ){
					if(errors.get("knowledgepro.admin.timeformat")!=null && !errors.get("knowledgepro.admin.timeformat").hasNext()){									
						if(errors.get("knowledgepro.attn.start.greater")!=null && !errors.get("knowledgepro.attn.start.greater").hasNext()){
							errors.add("knowledgepro.attn.start.end.equal",new ActionError("knowledgepro.attn.start.end.equal"));
							return;
						}
					}
			 }
			
			 if(startTimeInMins > endTimeInMins){
				errors.add("knowledgepro.attn.start.greater",new ActionError("knowledgepro.attn.start.greater"));
			 }
		}
	}
	
	public ActionForward reActiveCertificateCourseTeacher(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		log.info("Entering into reActiveCertificateCourseTeacher Action");
		CertificateCourseTeacherForm certificateCourseTeacherForm = (CertificateCourseTeacherForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, certificateCourseTeacherForm);
			boolean isReactivate;
			int oldId =certificateCourseTeacherForm.getId();
//			String oldExamName = certificateCourseTeacherForm.getExamName().trim();
			String userId = certificateCourseTeacherForm.getUserId();
			isReactivate = CertificateCourseTeacherHandler.getInstance().reactivateCertificateCourTeacher( oldId, userId);
			if(isReactivate){
				messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.DOC_TYPE_EXAM_REACTIVATE_SUCCESS));
				setRequestedDataToForm(certificateCourseTeacherForm);
				certificateCourseTeacherForm.reset();
				saveMessages(request, messages);
			}
			else{
				setRequestedDataToForm(certificateCourseTeacherForm);
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.DOC_TYPE_EXAM_REACTIVATE_FAILURE));
				addErrors(request, errors);
			}
			
		} catch (Exception e) {
			log.error("Error occured in reActiveCertificateCourseTeacher Action", e);
			String msg = super.handleApplicationException(e);
			certificateCourseTeacherForm.setErrorMessage(msg);
			certificateCourseTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into reActiveCertificateCourseTeacher Action");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
	}
	
	public ActionForward editCertificateCourseTeacher(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response){
		CertificateCourseTeacherForm certificateCourseTeacherForm = (CertificateCourseTeacherForm)form;
		try{
			CertificateCourseTeacherHandler.getInstance().getDetailById(certificateCourseTeacherForm, request);
			setRequestedDataToForm(certificateCourseTeacherForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
			log.error("Error occured in editRoomType of RoomTypeAction", e);
			String msg = super.handleApplicationException(e);
			certificateCourseTeacherForm.setErrorMessage(msg);
			certificateCourseTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into editCertificateCourseTeacher Action");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
	}
	
	public ActionForward deleteCertificateCourseTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into  deleteDocExamType action");
		CertificateCourseTeacherForm certificateCourseTeacherForm=(CertificateCourseTeacherForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, certificateCourseTeacherForm);
			boolean isDeleted=false;
			int certificateCourTeaId = Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseTeaId());
			String userId=certificateCourseTeacherForm.getUserId();
			//Request the handler to delete the selected roomtype
			isDeleted = CertificateCourseTeacherHandler.getInstance().deleteCertificateCourseTeacher(certificateCourTeaId, userId);
			//If success then append the success message
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.admission.certificate.course.teacher.deletesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
//				messages.add("knowledgepro.admission.certificate.course.teacher.deletesuccess",new ActionMessage("knowledgepro.admission.certificate.course.teacher.deletesuccess"));
				setRequestedDataToForm(certificateCourseTeacherForm);
				
			}
			//Else add the error message
			else {
				setRequestedDataToForm(certificateCourseTeacherForm);
				errors.add("knowledgepro.admission.certificate.course.teacher.deletefailure", new ActionError("knowledgepro.admission.certificate.course.teacher.deletefailure"));
				addErrors(request, errors);
			}
			certificateCourseTeacherForm.reset();
		} catch (Exception e) {
			log.error("Error occured in deleteDocExamType of DocumentExamEntryAction", e);
			String msg = super.handleApplicationException(e);
			certificateCourseTeacherForm.setErrorMessage(msg);
			certificateCourseTeacherForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit from  deleteDocExamType action");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY); 	
	}
	
	public ActionForward updateCertificateCourseTeacher(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into updateDocTypeExam of DocumentExamEntry Action");
		CertificateCourseTeacherForm certificateCourseTeacherForm=(CertificateCourseTeacherForm)form;
		request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = certificateCourseTeacherForm.validate(mapping, request);
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		try {	

			//mandatory message for start & end time
			if(certificateCourseTeacherForm.getStartHours().trim().equals("00") || certificateCourseTeacherForm.getStartHours().trim().equals("0") || certificateCourseTeacherForm.getStartHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.startHours",new ActionError("knowledgepro.attn.period.startHours"));
			}
			
			if(certificateCourseTeacherForm.getEndHours().trim().equals("00") || certificateCourseTeacherForm.getEndHours().trim().equals("0") || certificateCourseTeacherForm.getEndHours().trim().equals("")){
				errors.add("knowledgepro.attn.period.endHours",new ActionError("knowledgepro.attn.period.endHours"));
			}
		
			//time validation
			if(errors.isEmpty()){
				validateTime(certificateCourseTeacherForm, errors);
			}				
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				int tempYear; 
				if(certificateCourseTeacherForm.getYear() != null && !certificateCourseTeacherForm.getYear().isEmpty()){
					tempYear = Integer.parseInt(certificateCourseTeacherForm.getYear());
				}
				else
				{
					tempYear = currentYear; 
				}
				setRequestedDataToForm(certificateCourseTeacherForm);	//setting classList to form based on the year			
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
			}
			//This condition works when reset button will click in update mode
			if(isCancelled(request)){
				CertificateCourseTeacherHandler.getInstance().getDetailById(certificateCourseTeacherForm, request);
				setRequestedDataToForm(certificateCourseTeacherForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
			}
			
			// This condition when when there will be no validation error occurs
			else if(errors.isEmpty()) {
				setUserId(request, certificateCourseTeacherForm);
				boolean isUpdated;
				int cerCourseId = Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseId());
				int teacherId = Integer.parseInt(certificateCourseTeacherForm.getTeacherId());
				int oldCertificateCourseId = Integer.parseInt(certificateCourseTeacherForm.getOldCertificateCourseId());
				int oldTeacherId = Integer.parseInt(certificateCourseTeacherForm.getOldTeacherId());
				if(!(oldCertificateCourseId == cerCourseId) || !(oldTeacherId == teacherId)){
					CertificateCourseTeacher certificateCourseTeacher = CertificateCourseTeacherHandler.getInstance().checkDuplicate(cerCourseId,teacherId);
					if(certificateCourseTeacher!=null && certificateCourseTeacher.getIsActive()){
						errors.add("knowledgepro.admission.certificate.course.teacher.duplicate", new ActionError("knowledgepro.admission.certificate.course.teacher.duplicate"));
						setRequestedDataToForm(certificateCourseTeacherForm);
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
					}else if(certificateCourseTeacher!=null && !certificateCourseTeacher.getIsActive()){
						errors.add("knowledgepro.admission.certificate.course.teacher.reactivate", new ActionError("knowledgepro.admission.certificate.course.teacher.reactivate"));
						setRequestedDataToForm(certificateCourseTeacherForm);
						certificateCourseTeacherForm.setId(certificateCourseTeacher.getId());
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
					}
				
					isUpdated = CertificateCourseTeacherHandler.getInstance().updateCertificateCourseTeacher(certificateCourseTeacherForm);
					if(isUpdated){
						messages.add("knowledgepro.admission.certificate.course.teacher.updatesuccess",new ActionMessage("knowledgepro.admission.certificate.course.teacher.updatesuccess"));
						setRequestedDataToForm(certificateCourseTeacherForm);
						certificateCourseTeacherForm.reset();
						saveMessages(request, messages);
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.ADD);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY); 
					}
					else{
						setRequestedDataToForm(certificateCourseTeacherForm);
						errors.add("knowledgepro.admission.certificate.course.teacher.updatefailure", new ActionError("knowledgepro.admission.certificate.course.teacher.updatefailure"));
						addErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY); 
					}
				}
				else{
				isUpdated = CertificateCourseTeacherHandler.getInstance().updateCertificateCourseTeacher(certificateCourseTeacherForm);
				if(isUpdated){
					messages.add("knowledgepro.admission.certificate.course.teacher.updatesuccess",new ActionMessage("knowledgepro.admission.certificate.course.teacher.updatesuccess"));
					setRequestedDataToForm(certificateCourseTeacherForm);
					certificateCourseTeacherForm.reset();
					saveMessages(request, messages);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.ADD);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY); 
				}
				else{
					setRequestedDataToForm(certificateCourseTeacherForm);
					errors.add("knowledgepro.admission.certificate.course.teacher.updatefailure", new ActionError("knowledgepro.admission.certificate.course.teacher.updatefailure"));
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY); 
				}
				}
			}else
			{
				saveErrors(request, errors);
			}
			}catch (Exception e) {
					log.error("Error occured in editRoomType of RoomTypeAction", e);
					String msg = super.handleApplicationException(e);
					certificateCourseTeacherForm.setErrorMessage(msg);
					certificateCourseTeacherForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			log.info("exit from updateDocTypeexam  deleteDocExamType action");
			return mapping.findForward(CMSConstants.INIT_CERTIFICATE_COURSE_TEACHER_ENTRY);
	}
}


		
