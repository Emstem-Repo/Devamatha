package com.kp.cms.actions.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.admin.SyllabusEntryForm;
import com.kp.cms.handlers.admin.SyllabusEntryHandler;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.to.admin.SyllabusEntryDupTo;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;


@SuppressWarnings("deprecation")
public class SyllabusEntryAction extends BaseDispatchAction{
	SyllabusEntryHandler syllabusEntryHandler=SyllabusEntryHandler.getInstance();
	/**
	 * admin syllabus entry first page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initAdminSyllabusEntryFirstPage(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		clearFirstPage(syllabusEntryForm);
		syllabusEntryHandler.getAllMaps(syllabusEntryForm);
		return mapping.findForward("initAdminSyllabusEntryFirstPage");
	}	
	private void clearFirstPage(SyllabusEntryForm syllabusEntryForm) throws Exception{
		if(syllabusEntryForm.getIsFromSecondPageToFirstPage()!=null && syllabusEntryForm.getIsFromSecondPageToFirstPage().equalsIgnoreCase("Yes")){
			syllabusEntryForm.setIsFromSecondPageToFirstPage(null);
			List<SyllabusEntryDupTo> list= syllabusEntryHandler.getsubjectAndStatus(syllabusEntryForm);
	    	if(list!=null && !list.isEmpty()){
	    		syllabusEntryForm.setList1(list);
	    	}
		}else{
			int year = Calendar.getInstance().get(Calendar.YEAR);
			syllabusEntryForm.setBatchYear(String.valueOf(year));
			syllabusEntryForm.setDepartmentId(null);
			syllabusEntryForm.setSemisterId(null);
			syllabusEntryForm.setSubjectId(null);
			syllabusEntryForm.setSemesterMap(null);
			syllabusEntryForm.setStatus(null);
			syllabusEntryForm.setList1(null);
		}
		syllabusEntryForm.setTeacherFlag(null);
		syllabusEntryForm.setTempBatchYear(syllabusEntryForm.getBatchYear());
		clearSecondPage(syllabusEntryForm);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initAdminSyllabusEntrySecondPage(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		clearSecondPage(syllabusEntryForm);
		ActionErrors errors = syllabusEntryForm.validate(mapping, request);
		if(errors.isEmpty()){
			String status=syllabusEntryHandler.getSyllabusEntryStatus(syllabusEntryForm.getBatchYear(), syllabusEntryForm.getSubjectId());
			syllabusEntryForm.setStatus(status);
			if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("Pending")
					|| status.equalsIgnoreCase("HOD Rejected") || status.equalsIgnoreCase("Rejected")){
				if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("HOD Rejected") 
						|| status.equalsIgnoreCase("Rejected")){
					syllabusEntryHandler.setDataWhichIsInProgress(syllabusEntryForm);
				}else{
					setRequiredData(syllabusEntryForm);
				}
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry",status);
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward("initAdminSyllabusEntryFirstPage");
	}	
	private void clearSecondPage(SyllabusEntryForm syllabusEntryForm) {
		syllabusEntryForm.setCourseObjective(null);
		syllabusEntryForm.setLerningOutcome(null);
		syllabusEntryForm.setFreeText(null);
		syllabusEntryForm.setTextBooksAndRefBooks(null);
		syllabusEntryForm.setUnitsFlag(false);
		syllabusEntryForm.setMaxMarks(null);
		syllabusEntryForm.setTotTeachHrsPerSem(null);
		syllabusEntryForm.setNoOfLectureHrsPerWeek(null);
		syllabusEntryForm.setCredits(null);
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(null);
		syllabusEntryForm.setHeadingsFocus(null);
		syllabusEntryForm.setUnitsFocus(null);
		syllabusEntryForm.setUnitOrHead(null);
		syllabusEntryForm.setUnitsFlag(false);
		syllabusEntryForm.setChangeInSyllabus(null);
		syllabusEntryForm.setTempChangeInSyllabus(null);
		syllabusEntryForm.setChangeReason(null);
		syllabusEntryForm.setBriefDetailsExistingSyllabus(null);
		syllabusEntryForm.setBriefDetalsAboutChange(null);
		syllabusEntryForm.setRemarks(null);
		syllabusEntryForm.setTheoryOrPractical(null);
		syllabusEntryForm.setSecondLanguage(null);
		syllabusEntryForm.setQuestionBankRequired(null);
		syllabusEntryForm.setParentDepartment(null);
		syllabusEntryForm.setRejectReason(null);
		syllabusEntryForm.setRecommendedReading(null);
	}
	private void setRequiredData(SyllabusEntryForm syllabusEntryForm) throws Exception{
		syllabusEntryHandler.getsubjectCodeAndName(syllabusEntryForm);
		//start units and hours
		List<SyllabusEntryUnitsHoursTo> list=new ArrayList<SyllabusEntryUnitsHoursTo>();
		SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
		syllabusEntryUnitsHoursTo.setUnitNo(list.size()+1);
		syllabusEntryUnitsHoursTo.setUnits("Unit-"+syllabusEntryUnitsHoursTo.getUnitNo());
		syllabusEntryUnitsHoursTo.setPosition(list.size()+1);
		syllabusEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
		//start headings and description
		List<SyllabusEntryHeadingDescTo> list2=new ArrayList<SyllabusEntryHeadingDescTo>();
			SyllabusEntryHeadingDescTo syllabusEntryHeadingDescTo=new SyllabusEntryHeadingDescTo();
			syllabusEntryHeadingDescTo.setHeadingTemplate("Heading");
			syllabusEntryHeadingDescTo.setDescriptionTemplate("Description");
			syllabusEntryHeadingDescTo.setHeadingNO(list2.size()+1);
			list2.add(syllabusEntryHeadingDescTo);
		syllabusEntryUnitsHoursTo.setSyllabusEntryHeadingDescTos(list2);
		//end heading and descriptions
		list.add(syllabusEntryUnitsHoursTo);
		//end units and hours
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(list);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward adminSaveDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean flag=false;
		setUserId(request, syllabusEntryForm);
		try {
			flag=syllabusEntryHandler.adminSaveDraft(syllabusEntryForm,request);
			if(flag){
		    	syllabusEntryHandler.setDataWhichIsInProgress(syllabusEntryForm);
		    	syllabusEntryForm.setStatus("Inprogress");
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry.draft.save.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.draft.save.fail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("field")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForm.setErrorMessage(msg);
				syllabusEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
			return mapping.findForward("initAdminSyllabusEntryFirstPage");
		
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward adminSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = syllabusEntryForm.validate(mapping, request);
		boolean flag=false;
		setUserId(request, syllabusEntryForm);
		try {
			if(errors.isEmpty()){
				if(syllabusEntryForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
					if(syllabusEntryForm.getBriefDetailsExistingSyllabus().trim()==null || syllabusEntryForm.getBriefDetailsExistingSyllabus().trim().isEmpty()
							|| syllabusEntryForm.getBriefDetalsAboutChange().trim()==null || syllabusEntryForm.getBriefDetalsAboutChange().trim().isEmpty()
							|| syllabusEntryForm.getChangeReason().trim()==null || syllabusEntryForm.getChangeReason().trim().isEmpty()
							|| syllabusEntryForm.getRemarks().trim()==null || syllabusEntryForm.getRemarks().trim().isEmpty()){
						errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.additional.info"));
						saveErrors(request, errors);
						return mapping.findForward("initAdminSyllabusEntrySecondPage");
					}
				}
				flag=syllabusEntryHandler.adminSave(syllabusEntryForm,request);
				if(flag){
					List<SyllabusEntryDupTo> list= syllabusEntryHandler.getsubjectAndStatus(syllabusEntryForm);
			    	if(list!=null && !list.isEmpty()){
			    		syllabusEntryForm.setList1(list);
			    	}
					ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearFirstPage(syllabusEntryForm);
				}else {
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail","Syllabus Entry"));
					saveErrors(request, errors);
				}
			}else {
				saveErrors(request, errors);
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}
			
		} catch (Exception e) {
			if(request.getAttribute("description")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}
			if(request.getAttribute("field1")!=null){
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.syllabus","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief1")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.change","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief2")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");				
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief3")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
			}
			if(request.getAttribute("field")!=null){
				errors.add("error", new ActionError("errors.required",request.getAttribute("field")));
				saveErrors(request, errors);
				return mapping.findForward("initAdminSyllabusEntrySecondPage");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForm.setErrorMessage(msg);
				syllabusEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
			return mapping.findForward("initAdminSyllabusEntryFirstPage");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getsubjectAndStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		syllabusEntryForm.setTempBatchYear(syllabusEntryForm.getBatchYear());
		syllabusEntryForm.setList1(null);
		ActionErrors errors = syllabusEntryForm.validate(mapping, request);
       // HttpSession session = request.getSession(false);
	    if (errors.isEmpty()) {
	    	List<SyllabusEntryDupTo> list= syllabusEntryHandler.getsubjectAndStatus(syllabusEntryForm);
	    	if(list!=null && !list.isEmpty()){
	    		syllabusEntryForm.setList1(list);
	    		/*request.setAttribute("ToList", list);
	    		return mapping.findForward("ajaxResponseForSubjectList"); */
	    	}else{
	    		errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.subject.not.available"));
				saveErrors(request, errors);
	    	}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward("initAdminSyllabusEntryFirstPage");
	}
	/**
	 * add MoreHeadings And Description
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMoreHeadingsAndDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		try {
			syllabusEntryHandler.addMoreHeadingsAndDescription(syllabusEntryForm);
			/*if(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForm.setUnitsFlag(true);
			}else{
				syllabusEntryForm.setUnitsFocus(null);
			}*/
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForm.setErrorMessage(msg);
			syllabusEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(syllabusEntryForm.getTeacherFlag()!=null && syllabusEntryForm.getTeacherFlag().equalsIgnoreCase("teacher")){
			return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
		}else{
			return mapping.findForward("initAdminSyllabusEntrySecondPage");
		}
	}
	/**
	 * add MoreHeadings And Description
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeMoreHeadingsAndDescription(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		try {
			syllabusEntryHandler.removeMoreHeadingsAndDescription(syllabusEntryForm);
			/*if(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForm.setUnitsFlag(true);
			}else{
				syllabusEntryForm.setUnitsFlag(false);
				syllabusEntryForm.setUnitsFocus(null);
			}*/
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForm.setErrorMessage(msg);
			syllabusEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(syllabusEntryForm.getTeacherFlag()!=null && syllabusEntryForm.getTeacherFlag().equalsIgnoreCase("teacher")){
			return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
		}else{
			return mapping.findForward("initAdminSyllabusEntrySecondPage");
		}
	}
	/**
	 * add MoreHeadings And Description
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMoreUnitsAndHours(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		try {
			syllabusEntryHandler.addMoreUnitsAndHours(syllabusEntryForm);
			if(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForm.setUnitsFocus("unit_"+(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()-1));
				syllabusEntryForm.setUnitsFlag(true);
			}else{
				syllabusEntryForm.setUnitsFlag(false);
				syllabusEntryForm.setUnitsFocus(null);
				syllabusEntryForm.setHeadingsFocus(null);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForm.setErrorMessage(msg);
			syllabusEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(syllabusEntryForm.getTeacherFlag()!=null && syllabusEntryForm.getTeacherFlag().equalsIgnoreCase("teacher")){
			return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
		}else{
			return mapping.findForward("initAdminSyllabusEntrySecondPage");
		}
	}
	/**
	 * add MoreHeadings And Description
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeMoreUnitsAndHours(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		try {
			syllabusEntryHandler.removeMoreUnitsAndHours(syllabusEntryForm);
			if(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForm.setUnitsFocus("unit_"+(syllabusEntryForm.getSyllabusEntryUnitsHoursTos().size()-1));
				syllabusEntryForm.setUnitsFlag(true);
			}else{
				syllabusEntryForm.setUnitsFlag(false);
				syllabusEntryForm.setUnitsFocus(null);
				syllabusEntryForm.setHeadingsFocus(null);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForm.setErrorMessage(msg);
			syllabusEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(syllabusEntryForm.getTeacherFlag()!=null && syllabusEntryForm.getTeacherFlag().equalsIgnoreCase("teacher")){
			return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
		}else{
			return mapping.findForward("initAdminSyllabusEntrySecondPage");
		}
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initSyllabusEntryForTeacher(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionErrors errors = syllabusEntryForm.validate(mapping, request);
		syllabusEntryForm.setTeacherFlag(null);
		syllabusEntryForm.setBatchYear(syllabusEntryForm.getYear());
		syllabusEntryHandler.getSubjectsAndStatusForTeacherOtherThanLanguages(syllabusEntryForm);
		if(syllabusEntryForm.getSubjectMapBySem()==null){
			errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.subject.not.available"));
			saveErrors(request, errors);
		}
		if(syllabusEntryForm.getType()!=null && syllabusEntryForm.getType().equalsIgnoreCase("languages")){
			return mapping.findForward("initSyllabusEntryForTeacherLanguages");
		}else{
			return mapping.findForward("initSyllabusEntryForTeacher");
		}
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward initSyllabusEntryForTeacherSecondPage(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		clearSecondPage(syllabusEntryForm);
		ActionErrors errors = new ActionErrors();
		if(errors.isEmpty()){
			String status=syllabusEntryHandler.getSyllabusEntryStatus(syllabusEntryForm.getBatchYear(), syllabusEntryForm.getSubjectId());
			syllabusEntryForm.setStatus(status);
			if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("Pending") 
					|| status.equalsIgnoreCase("HOD Rejected") || status.equalsIgnoreCase("Rejected")){
				if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("HOD Rejected")
						|| status.equalsIgnoreCase("Rejected")){
					syllabusEntryHandler.setDataWhichIsInProgress(syllabusEntryForm);
				}else{
					setRequiredData(syllabusEntryForm);
				}
				syllabusEntryForm.setTeacherFlag("teacher");
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}else{
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry",status);
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		}else{
			saveErrors(request, errors);
		}
		return mapping.findForward("initSyllabusEntryForTeacher");
	}	
	/**
	 * syllabus entry save draft by teacher
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward teacherSaveDraft(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean flag=false;
		setUserId(request, syllabusEntryForm);
		try {
			flag=syllabusEntryHandler.adminSaveDraft(syllabusEntryForm,request);
			if(flag){
				syllabusEntryHandler.setDataWhichIsInProgress(syllabusEntryForm);
				syllabusEntryForm.setStatus("In-Progress");
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry.draft.save.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.draft.save.fail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("field")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForm.setErrorMessage(msg);
				syllabusEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		syllabusEntryHandler.getSubjectsAndStatusForTeacherOtherThanLanguages(syllabusEntryForm);
		return mapping.findForward("initSyllabusEntryForTeacher");
	}
	private void clearTeacherSecondPage(SyllabusEntryForm syllabusEntryForm) throws Exception{
		syllabusEntryForm.setCourseObjective(null);
		syllabusEntryForm.setLerningOutcome(null);
		syllabusEntryForm.setFreeText(null);
		syllabusEntryForm.setTextBooksAndRefBooks(null);
		syllabusEntryForm.setUnitsFlag(false);
		syllabusEntryForm.setMaxMarks(null);
		syllabusEntryForm.setTotTeachHrsPerSem(null);
		syllabusEntryForm.setNoOfLectureHrsPerWeek(null);
		syllabusEntryForm.setCredits(null);
		syllabusEntryForm.setSyllabusEntryUnitsHoursTos(null);
		syllabusEntryForm.setHeadingsFocus(null);
		syllabusEntryForm.setUnitsFocus(null);
		syllabusEntryForm.setBriefDetailsExistingSyllabus(null);
		syllabusEntryForm.setBriefDetalsAboutChange(null);
		syllabusEntryForm.setChangeReason(null);
		syllabusEntryForm.setDepartmentName(null);
	}
	/**
	 * save syllabus entry entered by teacher
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward teacherSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = syllabusEntryForm.validate(mapping, request);
		boolean flag=false;
		setUserId(request, syllabusEntryForm);
		try {
			if(errors.isEmpty()){
				if(syllabusEntryForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
					if(syllabusEntryForm.getBriefDetailsExistingSyllabus().trim()==null || syllabusEntryForm.getBriefDetailsExistingSyllabus().trim().isEmpty()
							|| syllabusEntryForm.getBriefDetalsAboutChange().trim()==null || syllabusEntryForm.getBriefDetalsAboutChange().trim().isEmpty()
							|| syllabusEntryForm.getChangeReason().trim()==null || syllabusEntryForm.getChangeReason().trim().isEmpty()
							|| syllabusEntryForm.getRemarks().trim()==null || syllabusEntryForm.getRemarks().trim().isEmpty()){
						errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.additional.info"));
						saveErrors(request, errors);
						return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
					}
				}
				flag=syllabusEntryHandler.adminSave(syllabusEntryForm,request);
				if(flag){
					syllabusEntryHandler.getSubjectsAndStatusForTeacherOtherThanLanguages(syllabusEntryForm);
					ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearTeacherSecondPage(syllabusEntryForm);
				}else {
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail","Syllabus Entry"));
					saveErrors(request, errors);
				}
			}else {
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}
			
		} catch (Exception e) {
			if(request.getAttribute("description")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}
			if(request.getAttribute("field1")!=null){
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.syllabus","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief1")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.change","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief2")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");				
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief3")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
				}
			}
			if(request.getAttribute("field")!=null){
				errors.add("error", new ActionError("errors.required",request.getAttribute("field")));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForm.setErrorMessage(msg);
				syllabusEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		if(syllabusEntryForm.getType()!=null && syllabusEntryForm.getType().equalsIgnoreCase("languages")){
			return mapping.findForward("initSyllabusEntryForTeacherLanguages");
		}else{
			return mapping.findForward("initSyllabusEntryForTeacher");
		}
	}
	/**
	 * copy the previously entered syllabus entry by year and subject to present year and subject fro admin
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward previousSyllabusEntryBySubjectAndYearForAdmin(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		syllabusEntryHandler.previousSyllabusEntryBySubjectAndYear(syllabusEntryForm);
		syllabusEntryForm.setPreviousYear(null);
		syllabusEntryForm.setPreviousYearSubjectId(null);
		return mapping.findForward("initAdminSyllabusEntrySecondPage");
	}	
	/**
	 * copy the previously entered syllabus entry by year and subject to present year and subject for teacher
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward previousSyllabusEntryBySubjectAndYearForTeacher(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		syllabusEntryHandler.previousSyllabusEntryBySubjectAndYear(syllabusEntryForm);
		syllabusEntryForm.setPreviousYear(null);
		syllabusEntryForm.setPreviousYearSubjectId(null);
		return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward checkIsSyllabusEntryOpen(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		syllabusEntryForm.setBatchYearList(null);
		boolean syllabusEntry=LoginHandler.getInstance().checkForSyllabusEntryLink(syllabusEntryForm);
		if(syllabusEntry){
			int deptId=Integer.parseInt(session.getAttribute("DepartmentId").toString());
			if(deptId==24 || deptId==25 || deptId==26 ||deptId==27 || deptId==28 || deptId==43){
				session.setAttribute("syllabusEntryOpenForLanguages", "openLanguage");
			}else if(deptId==54){
				session.setAttribute("syllabusEntryOpenForLanguages", "openLanguage");
				session.setAttribute("syllabusEntryOpen", "open");
			}else{
				session.setAttribute("syllabusEntryOpen", "open");
			}
		}else{
			ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry","is not opened");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
			
		return mapping.findForward("initCheckSyllabusEntryIsOpen");
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward preview(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryForm syllabusEntryForm=(SyllabusEntryForm)form;
		clearTeacherSecondPage(syllabusEntryForm);
		try {
			syllabusEntryHandler.setDataForPreview(syllabusEntryForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("initSyllabusEntryPreview");
	}
	
}
