package com.kp.cms.actions.admin;

import java.util.ArrayList;
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
import com.kp.cms.forms.admin.SyllabusEntryHodApprovalForm;
import com.kp.cms.handlers.admin.SyllabusEntryHodApprovalHandler;
import com.kp.cms.to.admin.SyllabusEntryHeadingDescTo;
import com.kp.cms.to.admin.SyllabusEntryUnitsHoursTo;

public class SyllabusEntryHodApprovalAction extends BaseDispatchAction{
	SyllabusEntryHodApprovalHandler handler=SyllabusEntryHodApprovalHandler.getInstance();
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
	public ActionForward checkIsSyllabusEntryOpenForHod(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		syllabusEntryForHodApprovalForm.setBatchYearList(null);
		try {
			boolean flag=handler.checkForSyllabusEntryLink(syllabusEntryForHodApprovalForm);
			if(flag){
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
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
			
		return mapping.findForward("initCheckSyllabusEntryIsOpenForHod");
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
	public ActionForward initSyllabusEntryHodApprovalFirstPage(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			syllabusEntryForHodApprovalForm.setList(null);
			syllabusEntryForHodApprovalForm.setTempRejectReason(null);
			syllabusEntryForHodApprovalForm.setRejectReason(null);
			syllabusEntryForHodApprovalForm.setBatchYear(syllabusEntryForHodApprovalForm.getYear());
			handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
			if(syllabusEntryForHodApprovalForm.getList()!=null && !syllabusEntryForHodApprovalForm.getList().isEmpty()){
				return mapping.findForward("initSyllabusEntryFirstPageForHod");
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.subject.not.found.approve"));
				saveErrors(request, errors);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initCheckSyllabusEntryIsOpenForHod");
	}	
	/**
	 * this method is used for hod approval
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward syllabusEntryHodApprove(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			setUserId(request, syllabusEntryForHodApprovalForm);
			boolean flag=handler.syllabusEntryHodApprove(syllabusEntryForHodApprovalForm,request);
			if(flag){
				syllabusEntryForHodApprovalForm.setList(null);
				handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
				ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilator.duty.update.approved.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilator.duty.update.approved.fail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("approve")!=null && request.getAttribute("approve").toString().equals("approve")){
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.reject","approve"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntryFirstPageForHod");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForHodApprovalForm.setErrorMessage(msg);
				syllabusEntryForHodApprovalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		
		}
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
	}
	/**
	 * this method is used for hod approval
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward rejectSyllabusEntry(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			setUserId(request, syllabusEntryForHodApprovalForm);
			boolean flag=handler.rejectSyllabusEntry(syllabusEntryForHodApprovalForm,request);
			if(flag){
				syllabusEntryForHodApprovalForm.setList(null);
				syllabusEntryForHodApprovalForm.setTempRejectReason(null);
				syllabusEntryForHodApprovalForm.setRejectReason(null);
				handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry.reject.success");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.reject.fail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("approve")!=null && request.getAttribute("approve").toString().equals("approve")){
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.reject","reject"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntryFirstPageForHod");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForHodApprovalForm.setErrorMessage(msg);
				syllabusEntryForHodApprovalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		
		}
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
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
	public ActionForward initSyllabusEntryForHodSecondPage(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		clearSecondPage(syllabusEntryForHodApprovalForm);
		ActionErrors errors = new ActionErrors();
		try {
			if(errors.isEmpty()){
				String status=handler.getSyllabusEntryStatus(syllabusEntryForHodApprovalForm.getBatchYear(), syllabusEntryForHodApprovalForm.getSubjectId());
				syllabusEntryForHodApprovalForm.setStatus(status);
				if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("Pending")
						|| status.equalsIgnoreCase("HOD Rejected") || status.equalsIgnoreCase("Rejected")){
					if(status.equalsIgnoreCase("Inprogress") || status.equalsIgnoreCase("HOD Rejected") 
							|| status.equalsIgnoreCase("Rejected")){
						handler.setDataWhichIsInProgress(syllabusEntryForHodApprovalForm);
					}else{
						setRequiredData(syllabusEntryForHodApprovalForm);
					}
					syllabusEntryForHodApprovalForm.setTeacherFlag("teacher");
					return mapping.findForward("initSyllabusEntrySecondPageForHod");
				}else{
					ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry",status);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
			}else{
				saveErrors(request, errors);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
	}
	private void setRequiredData(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		handler.getsubjectCodeAndName(syllabusEntryForHodApprovalForm);
		//start units and hours
		List<SyllabusEntryUnitsHoursTo> list=new ArrayList<SyllabusEntryUnitsHoursTo>();
		SyllabusEntryUnitsHoursTo syllabusEntryUnitsHoursTo=new SyllabusEntryUnitsHoursTo();
		syllabusEntryUnitsHoursTo.setUnits("Unit-"+(list.size()+1));
		syllabusEntryUnitsHoursTo.setPosition(list.size()+1);
		syllabusEntryUnitsHoursTo.setTeachingHoursTemplate("Teaching Hours");
		syllabusEntryUnitsHoursTo.setUnitNo(list.size()+1);
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
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(list);
	
	}
	private void clearSecondPage(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{

		syllabusEntryForHodApprovalForm.setCourseObjective(null);
		syllabusEntryForHodApprovalForm.setLerningOutcome(null);
		syllabusEntryForHodApprovalForm.setFreeText(null);
		syllabusEntryForHodApprovalForm.setTextBooksAndRefBooks(null);
		syllabusEntryForHodApprovalForm.setUnitsFlag(false);
		syllabusEntryForHodApprovalForm.setMaxMarks(null);
		syllabusEntryForHodApprovalForm.setTotTeachHrsPerSem(null);
		syllabusEntryForHodApprovalForm.setNoOfLectureHrsPerWeek(null);
		syllabusEntryForHodApprovalForm.setCredits(null);
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(null);
		syllabusEntryForHodApprovalForm.setHeadingsFocus(null);
		syllabusEntryForHodApprovalForm.setUnitsFocus(null);
		syllabusEntryForHodApprovalForm.setUnitOrHead(null);
		syllabusEntryForHodApprovalForm.setUnitsFlag(false);
		syllabusEntryForHodApprovalForm.setChangeInSyllabus(null);
		syllabusEntryForHodApprovalForm.setTempChangeInSyllabus(null);
		syllabusEntryForHodApprovalForm.setChangeReason(null);
		syllabusEntryForHodApprovalForm.setBriefDetailsExistingSyllabus(null);
		syllabusEntryForHodApprovalForm.setBriefDetalsAboutChange(null);
		syllabusEntryForHodApprovalForm.setRemarks(null);
		syllabusEntryForHodApprovalForm.setTheoryOrPractical(null);
		syllabusEntryForHodApprovalForm.setSecondLanguage(null);
		syllabusEntryForHodApprovalForm.setQuestionBankRequired(null);
		syllabusEntryForHodApprovalForm.setParentDepartment(null);
		syllabusEntryForHodApprovalForm.setRejectReason(null);
		syllabusEntryForHodApprovalForm.setRecommendedReading(null);
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
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			handler.addMoreHeadingsAndDescription(syllabusEntryForHodApprovalForm);
			/*if(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForHodApprovalForm.setUnitsFlag(true);
			}else{
				syllabusEntryForHodApprovalForm.setUnitsFocus(null);
			}*/
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initSyllabusEntrySecondPageForHod");
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
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			handler.removeMoreHeadingsAndDescription(syllabusEntryForHodApprovalForm);
			/*if(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForHodApprovalForm.setUnitsFlag(true);
			}else{
				syllabusEntryForHodApprovalForm.setUnitsFlag(false);
				syllabusEntryForHodApprovalForm.setUnitsFocus(null);
			}*/
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initSyllabusEntrySecondPageForHod");
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
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			handler.addMoreUnitsAndHours(syllabusEntryForHodApprovalForm);
			if(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForHodApprovalForm.setUnitsFocus("unit_"+(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()-1));
				syllabusEntryForHodApprovalForm.setUnitsFlag(true);
			}else{
				syllabusEntryForHodApprovalForm.setUnitsFlag(false);
				syllabusEntryForHodApprovalForm.setUnitsFocus(null);
				syllabusEntryForHodApprovalForm.setHeadingsFocus(null);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initSyllabusEntrySecondPageForHod");
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
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionErrors errors = new ActionErrors();
		try {
			handler.removeMoreUnitsAndHours(syllabusEntryForHodApprovalForm);
			if(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()>1){
				syllabusEntryForHodApprovalForm.setUnitsFocus("unit_"+(syllabusEntryForHodApprovalForm.getSyllabusEntryUnitsHoursTos().size()-1));
				syllabusEntryForHodApprovalForm.setUnitsFlag(true);
			}else{
				syllabusEntryForHodApprovalForm.setUnitsFlag(false);
				syllabusEntryForHodApprovalForm.setUnitsFocus(null);
				syllabusEntryForHodApprovalForm.setHeadingsFocus(null);
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			syllabusEntryForHodApprovalForm.setErrorMessage(msg);
			syllabusEntryForHodApprovalForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initSyllabusEntrySecondPageForHod");
	}
	/**
	 * syllabus entry save draft by Hod
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward saveDraftByHod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean flag=false;
		setUserId(request, syllabusEntryForHodApprovalForm);
		try {
			flag=handler.saveDraftBYHod(syllabusEntryForHodApprovalForm,request);
			if(flag){
				handler.setDataWhichIsInProgress(syllabusEntryForHodApprovalForm);
				syllabusEntryForHodApprovalForm.setStatus("In-progress");
				ActionMessage message = new ActionMessage("knowledgepro.admin.syllabus.entry.draft.save.success");
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward("initSyllabusEntrySecondPageForHod");
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.draft.save.fail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("field")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForHod");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForHodApprovalForm.setErrorMessage(msg);
				syllabusEntryForHodApprovalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
	}
	/**
	 * save syllabus entry  by Hod
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return save the email template to database.
	 * @throws Exception
	 */
	public ActionForward SaveByHod(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = syllabusEntryForHodApprovalForm.validate(mapping, request);
		boolean flag=false;
		setUserId(request, syllabusEntryForHodApprovalForm);
		try {
			if(errors.isEmpty()){
				if(syllabusEntryForHodApprovalForm.getChangeInSyllabus().equalsIgnoreCase("yes")){
					if(syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus().trim()==null || syllabusEntryForHodApprovalForm.getBriefDetailsExistingSyllabus().trim().isEmpty()
							|| syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange().trim()==null || syllabusEntryForHodApprovalForm.getBriefDetalsAboutChange().trim().isEmpty()
							|| syllabusEntryForHodApprovalForm.getChangeReason().trim()==null || syllabusEntryForHodApprovalForm.getChangeReason().trim().isEmpty()
							|| syllabusEntryForHodApprovalForm.getRemarks().trim()==null || syllabusEntryForHodApprovalForm.getRemarks().trim().isEmpty()){
						errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.additional.info"));
						saveErrors(request, errors);
						return mapping.findForward("initSyllabusEntrySecondPageForTeacher");
					}
				}
				flag=handler.saveByHod(syllabusEntryForHodApprovalForm,request);
				if(flag){
					handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
					ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Syllabus Entry");
					messages.add("messages", message);
					saveMessages(request, messages);
					clearTeacherSecondPage(syllabusEntryForHodApprovalForm);
				}else {
					errors.add("error", new ActionError("knowledgepro.admin.programtype.name.addfail","Syllabus Entry"));
					saveErrors(request, errors);
				}
			}else {
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForHod");
			}
			
		} catch (Exception e) {
			if(request.getAttribute("description")!=null && request.getAttribute("field").toString().equals("Description")){
				errors.add("error", new ActionError("knowledgepro.admin.open.syllabus.entry.description.length"));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForHod");
			}
			if(request.getAttribute("field1")!=null){
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.syllabus","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForHod");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief1")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.brief.details.change","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForHod");
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief2")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForHod");			
				}
				if(request.getAttribute("field1").toString().equalsIgnoreCase("brief3")){
					errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.change.reason","length must less than 500 characters"));
					saveErrors(request, errors);
					return mapping.findForward("initSyllabusEntrySecondPageForHod");
				}
			}
			if(request.getAttribute("field")!=null){
				errors.add("error", new ActionError("errors.required",request.getAttribute("field")));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntrySecondPageForHod");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForHodApprovalForm.setErrorMessage(msg);
				syllabusEntryForHodApprovalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
	}
	private void clearTeacherSecondPage(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		syllabusEntryForHodApprovalForm.setCourseObjective(null);
		syllabusEntryForHodApprovalForm.setLerningOutcome(null);
		syllabusEntryForHodApprovalForm.setFreeText(null);
		syllabusEntryForHodApprovalForm.setTextBooksAndRefBooks(null);
		syllabusEntryForHodApprovalForm.setUnitsFlag(false);
		syllabusEntryForHodApprovalForm.setMaxMarks(null);
		syllabusEntryForHodApprovalForm.setTotTeachHrsPerSem(null);
		syllabusEntryForHodApprovalForm.setNoOfLectureHrsPerWeek(null);
		syllabusEntryForHodApprovalForm.setCredits(null);
		syllabusEntryForHodApprovalForm.setSyllabusEntryUnitsHoursTos(null);
		syllabusEntryForHodApprovalForm.setHeadingsFocus(null);
		syllabusEntryForHodApprovalForm.setUnitsFocus(null);
		syllabusEntryForHodApprovalForm.setBriefDetailsExistingSyllabus(null);
		syllabusEntryForHodApprovalForm.setBriefDetalsAboutChange(null);
		syllabusEntryForHodApprovalForm.setChangeReason(null);
		syllabusEntryForHodApprovalForm.setDepartmentName(null);
	}
	/**
	 * save program details by course and batch
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward saveProgramDetails(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			boolean flag=handler.saveProgramDetails(syllabusEntryForHodApprovalForm,request);
			if(flag){
				handler.getCourseWithSubjects(syllabusEntryForHodApprovalForm);
				syllabusEntryForHodApprovalForm.setTempOpen(null);
				ActionMessage message = new ActionMessage("knowledgepro.admin.programtype.name.addsuccess","Syllabus Entry");
				messages.add("messages", message);
				saveMessages(request, messages);
			}else {
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.program.details","is required"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			if(request.getAttribute("program")!=null){
				String course=" is required for ";
				course=course+request.getAttribute("program").toString();
				errors.add("error", new ActionError("knowledgepro.admin.syllabus.entry.program.details",course));
				saveErrors(request, errors);
				return mapping.findForward("initSyllabusEntryFirstPageForHod");
			}
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				syllabusEntryForHodApprovalForm.setErrorMessage(msg);
				syllabusEntryForHodApprovalForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("initSyllabusEntryFirstPageForHod");
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
	public ActionForward hodPreview(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		clearPreviewPage(syllabusEntryForHodApprovalForm);
		handler.setDataForPreview(syllabusEntryForHodApprovalForm);
		return mapping.findForward("initHodSyllabusEntryPreview");
	}
	private void clearPreviewPage(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm) throws Exception{
		syllabusEntryForHodApprovalForm.setHodPreviewList(null);
		syllabusEntryForHodApprovalForm.setDepartmentOverview(null);
		syllabusEntryForHodApprovalForm.setMissionStatement(null);
		syllabusEntryForHodApprovalForm.setIntroductionProgramme(null);
		syllabusEntryForHodApprovalForm.setProgramObjective(null);
		syllabusEntryForHodApprovalForm.setAssesmentPattern(null);
		syllabusEntryForHodApprovalForm.setExaminationAndAssesments(null);
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
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		clearTeacherSecondPage(syllabusEntryForHodApprovalForm);
		handler.setDataForPreviewForSubject(syllabusEntryForHodApprovalForm);
		return mapping.findForward("initSyllabusEntryForSubjectHodPreview");
	}
	/**
	 * copy the previously entered syllabus entry by year and subject to present year and subject for Hod
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return load the first page of the password template.
	 * @throws Exception
	 *         
	 */
	public ActionForward previousSyllabusEntryBySubjectAndYearForHod(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm=(SyllabusEntryHodApprovalForm)form;
		ActionMessages messages = new ActionMessages();
		handler.previousSyllabusEntryBySubjectAndYear(syllabusEntryForHodApprovalForm);
		syllabusEntryForHodApprovalForm.setPreviousYear(null);
		syllabusEntryForHodApprovalForm.setPreviousYearSubjectId(null);
		return mapping.findForward("initSyllabusEntrySecondPageForHod");
	}	
}
