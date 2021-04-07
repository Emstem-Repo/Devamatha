package com.kp.cms.actions.admission;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.EntranceDetailsHandler;
import com.kp.cms.handlers.admin.GuideLinesCheckListHandler;
import com.kp.cms.handlers.admin.GuidelinesEntryHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OfflineDetailsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.EntrancedetailsTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.GuideLinesCheckListTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.TermsConditionChecklistTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.transactions.admin.IProgramTransaction;
import com.kp.cms.transactions.admin.ISubReligionTransaction;
import com.kp.cms.transactionsimpl.admin.ProgramTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SubReligionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class PresidanceAdmissionFormAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(PresidanceAdmissionFormAction.class);
	private static final String OTHER="other";
	private static final String TO_DATEFORMAT="MM/dd/yyyy";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String PHOTOBYTES="PhotoBytes";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String COUNTID="countID";
	
	
	/**
	 * Link from outside sites -single page Form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOutsideSinglePageAccess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session= request.getSession(false);
		AdmissionFormForm admForm=null;
		try {
			cleanupSessionData(session);
			admForm=(AdmissionFormForm)form;
			admForm.setCoursePrerequisites(new ArrayList<CoursePrerequisiteTO>());
			cleanUpPageData(admForm);
			admForm.setProgramTypeId(null);
			admForm.setReviewWarned(false);
			admForm.setDataSaved(false);
			setUserId(request,admForm);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
		} catch (Exception e) {
			log.error("error in initOutsideSinglePageAccess...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}

		log.info("exit init initOutsideSinglePageAccess...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINEAPPLY_PAGE);
	}
	
	
	/**
	 * cleans up session data
	 * @param session
	 */
	private void cleanupSessionData(HttpSession session) {
		log.info("cleaning up session data...");
		if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
			session.removeAttribute(CMSConstants.APPLICATION_DATA);
		if(session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS);
		if(session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_COMM_ADDRESS);
			
		if(session.getAttribute(CMSConstants.STUDENT_PREFERENCES)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PREFERENCES);
		if(session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS);
		if(session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
		if(session.getAttribute(CMSConstants.STUDENT_PREREQUISITES)!=null)
			session.removeAttribute(CMSConstants.STUDENT_PREREQUISITES);
		if(session.getAttribute(CMSConstants.STUDENT_LATERALDETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_LATERALDETAILS);
		if(session.getAttribute(CMSConstants.COURSE_PREFERENCES)!=null)
			session.removeAttribute(CMSConstants.COURSE_PREFERENCES);
		if(session!= null && session.getAttribute(PresidanceAdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(PresidanceAdmissionFormAction.PHOTOBYTES);
		
		session.removeAttribute("baseActionForm");
		log.info("session data cleaned...");
	}
	
	/**
	 * cleaned up data
	 * @param admForm
	 */
	private void cleanUpPageData(AdmissionFormForm admForm) {
		log.info("enter cleanUpPageData..");
		if(admForm!=null){
			admForm.setProgramId("");
			admForm.setProgramTypeId("");
			admForm.setCourseId("");
			admForm.setChallanNo(null);
			admForm.setJournalNo(null);
			admForm.setApplicationDate(null);
			admForm.setApplicationAmount(null);
			admForm.setBankBranch("");
			admForm.setCourseName(null);
			admForm.setProgramName(null);
		}
		log.info("exit cleanUpPageData..");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOnlineApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm=(AdmissionFormForm)form;
		admForm.setOnlineApply(true);
		HttpSession session=null;
		String courseName="";
		String progName="";
		String progTypeName="";
		String decCourseID=(String)request.getParameter("decText");
		CourseHandler crsHandler= CourseHandler.getInstance();
		ActionMessages errors=admForm.validate(mapping, request);
		// validating program course
		validateProgramCourse(errors,admForm,true);
		if(!errors.isEmpty() )
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINEAPPLY_PAGE);
		}		
		
		int appliedYear = AdmissionFormHandler.getInstance().getApplicationYear(Integer.parseInt(admForm.getProgramId()));
		admForm.setApplicationYear(Integer.toString(appliedYear));
		
		if(decCourseID!=null && !StringUtils.isEmpty(decCourseID.trim())){
			
		int courseID=-1;
		int progID=-1;
		int progtypeID=-1;
	
		if(decCourseID!=null && !StringUtils.isEmpty(decCourseID.trim()) && StringUtils.isNumeric(decCourseID.trim()))
		{
			courseID=Integer.parseInt(decCourseID);
			admForm.setOutsideCourseSelected(true);
			List<CourseTO> courselist=crsHandler.getCourse(courseID);
			if(courselist!=null && !courselist.isEmpty()){
				CourseTO to= courselist.get(0);
				//PROGRAM LEVEL CONFIG SETTINGS
				if(to!=null){
					courseName=to.getName();
					if(to.getProgramTo()!=null){
					progID=to.getProgramTo().getId();
					progName=to.getProgramTo().getName();
						admForm.setDisplayMotherTongue(to.getProgramTo().getIsMotherTongue());
						admForm.setDisplayLanguageKnown(to.getProgramTo().getIsDisplayLanguageKnown());
						admForm.setDisplayHeightWeight(to.getProgramTo().getIsHeightWeight());
						admForm.setDisplayTrainingDetails(to.getProgramTo().getIsDisplayTrainingCourse());
						admForm.setDisplayAdditionalInfo(to.getProgramTo().getIsAdditionalInfo());
						admForm.setDisplayExtracurricular(to.getProgramTo().getIsExtraDetails());
						admForm.setDisplaySecondLanguage(to.getProgramTo().getIsSecondLanguage());
						admForm.setDisplayFamilyBackground(to.getProgramTo().getIsFamilyBackground());
						admForm.setDisplayLateralDetails(to.getProgramTo().getIsLateralDetails());
						admForm.setDisplayTransferCourse(to.getProgramTo().getIsTransferCourse());
						admForm.setDisplayEntranceDetails(to.getProgramTo().getIsEntranceDetails());
						admForm.setDisplayTCDetails(to.getProgramTo().getIsTCDetails());
					}
					if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null ){
					progtypeID=to.getProgramTo().getProgramTypeTo().getProgramTypeId();
					progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
					}
				}
			}
		}
		if(courseID!=-1){
			session= request.getSession(true);
			admForm.setUserId(CMSConstants.ONLINE_USERID);
			admForm.setCourseId(String.valueOf(courseID));
			admForm.setCourseName(courseName);
		}
		else{
			admForm.setCourseId(null);
			admForm.setCourseName("");
		}
		if(progID!=-1){
			admForm.setProgramId(String.valueOf(progID));
			admForm.setProgramName(progName);
		}
		else{
			admForm.setProgramId(null);
			admForm.setProgramName("");
		}
		if(progtypeID!=-1){
			admForm.setProgramTypeId(String.valueOf(progtypeID));
			admForm.setProgTypeName(progTypeName);
		}
		else{
			admForm.setProgramTypeId(null);
			admForm.setProgTypeName("");
		}
		}
		session=request.getSession(false);
		if(session==null){
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		}
		setUserId(request,admForm);
		if((admForm.getCourseName()==null || StringUtils.isEmpty(admForm.getCourseName())) && (admForm.getProgramName()==null || StringUtils.isEmpty(admForm.getProgramName())) && (admForm.getProgramTypeName()==null || StringUtils.isEmpty(admForm.getProgramTypeName()))){
		List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
		if(courselist!=null && ! courselist.isEmpty()){
			CourseTO to=courselist.get(0);
			// PROGRAM LEVEL CONFIG SETTINGS
			courseName=to.getName();
			if (to.getAmount() != null) {
				admForm.setApplicationAmount(to.getAmount().toPlainString());
			}
			if(to.getProgramTo()!=null){
				progName=to.getProgramTo().getName();
				if(to.getProgramTo().getIsMotherTongue()==true)
					admForm.setDisplayMotherTongue(true);
				if(to.getProgramTo().getIsDisplayLanguageKnown()==true)
					admForm.setDisplayLanguageKnown(true);
				if(to.getProgramTo().getIsHeightWeight()==true)
					admForm.setDisplayHeightWeight(true);
				if(to.getProgramTo().getIsDisplayTrainingCourse()==true)
					admForm.setDisplayTrainingDetails(true);
				if(to.getProgramTo().getIsAdditionalInfo()==true)
					admForm.setDisplayAdditionalInfo(true);
				if(to.getProgramTo().getIsExtraDetails()==true)
					admForm.setDisplayExtracurricular(true);
				if(to.getProgramTo().getIsSecondLanguage()==true)
					admForm.setDisplaySecondLanguage(true);
				if(to.getProgramTo().getIsFamilyBackground()==true)
					admForm.setDisplayFamilyBackground(true);
				if(to.getProgramTo().getIsLateralDetails()==true)
					admForm.setDisplayLateralDetails(true);
				if(to.getProgramTo().getIsTransferCourse()==true)
					admForm.setDisplayTransferCourse(true);
				if(to.getProgramTo().getIsEntranceDetails()==true)
					admForm.setDisplayEntranceDetails(true);
				if(to.getProgramTo().getIsTCDetails()==true)
					admForm.setDisplayTCDetails(true);
			}
			if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null)
				progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
		}
		// CHECK EXTRA BLOCK DISPLAY
		checkExtradetailsDisplay(admForm);
		// CHECK LATERAL AND TRANSFER LINK DISPLAY
		checkLateralTransferDisplay(admForm);
		admForm.setCourseName(courseName);
		admForm.setProgramName(progName);
		admForm.setProgTypeName(progTypeName);
		}
		List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
		if(courselist!=null && ! courselist.isEmpty()){
			CourseTO to=courselist.get(0);
			// Amount setting make sure
			if (to.getAmount() != null) {
				admForm.setApplicationAmount(to.getAmount().toPlainString());
			}
		}
		try {
			GuidelinesEntryHandler guidelineHandler=GuidelinesEntryHandler.getInstance();
			int year = Integer.parseInt(admForm.getApplicationYear());
			admForm.setGuidelineExist(guidelineHandler.isGuidelinesExist(Integer.parseInt(admForm.getCourseId()),year));
		} catch (Exception e) {
			log.error("error in init online application page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit init application detail page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITGUIDELINE_PAGE);
	}
	
	/**
	 * LATERAL AND TRANSFER COURSE LINK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkLateralTransferDisplay(AdmissionFormForm admForm) {
		boolean isextra=false;
		
		if(admForm.isDisplayLateralDetails())
			isextra=true;
		if(admForm.isDisplayTransferCourse())
			isextra=true;
		admForm.setDisplayLateralTransfer(isextra);
		
	}
	/**
	 * EXTRA DETAILS BLOCK DISPLAY CHECK
	 * @param admForm
	 */
	private void checkExtradetailsDisplay(AdmissionFormForm admForm) {
		boolean isextra=false;
		if(admForm.isDisplayMotherTongue())
			isextra=true;
		if(admForm.isDisplayHeightWeight())
			isextra=true;
		if(admForm.isDisplayTrainingDetails())
			isextra=true;
		if(admForm.isDisplayAdditionalInfo())
			isextra=true;
		if(admForm.isDisplayExtracurricular())
			isextra=true;
		if(admForm.isDisplayLanguageKnown())
			isextra=true;
		
		admForm.setDisplayExtraDetails(isextra);
		
	}
	
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateProgramCourse(ActionMessages errors,
			AdmissionFormForm admForm,boolean isFirstprefLable) {
		log.info("enter validate program course...");
		if(admForm.getProgramTypeId() ==null || admForm.getProgramTypeId().length()==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}
		}
		if(admForm.getProgramId() ==null || admForm.getProgramId().length()==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}else
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}
		}
		if(admForm.getCourseId()==null ||admForm.getCourseId().length()==0 )
		{
			if(errors==null){
				errors= new ActionMessages();
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
				}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
				}
			}else{
				if(isFirstprefLable){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
				}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
				}
			}
		}
		log.info("exit validate program course...");
		return errors;
	}
	
	
	
	/**
	 * INITIALIZES GUIDELINES PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initGuidelinesPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initGuidelinesPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		// fetch guideline template for course and display
		
		//if template empty,go to terms condition
		String guidelines="";
		//get template for term condition
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(Integer.parseInt(admForm.getCourseId()),CMSConstants.GUIDELINES_TEMPLATE);
		if(list != null && !list.isEmpty()) {
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null)
				guidelines = list.get(0).getTemplateDescription();
			String logoPath = "";
			byte[] logo = null;
			HttpSession session = request.getSession(false);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if (organisation != null) {
				logo = organisation.getLogo();
			}
			if (session != null) {
				session.setAttribute("LogoBytes", logo);
			}
			logoPath = request.getContextPath();
			logoPath = "<img src="
					+ logoPath
					+ "/LogoServlet alt='Logo not available' width='210' height='100' >";
			
			//REPLACE CHALLAN LINK
			String link=CMSConstants.PRINT_CHALLANLINK;

			guidelines=guidelines.replace(CMSConstants.TEMPLATE_PRINT_CHALLAN,link);
			guidelines=guidelines.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
			
		}
		//fetch checklists
		GuideLinesCheckListHandler guidHandle=GuideLinesCheckListHandler.getInstance();
		List<GuideLinesCheckListTO> guidelist=guidHandle.getGuidelinesChecklist();
		
		
		admForm.setGuidelines(guidelines);
		admForm.setGuidelineChecklists(guidelist);
		if((guidelines!=null && !StringUtils.isEmpty(guidelines)) || (guidelist!=null && !guidelist.isEmpty()))
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINELINKS_PAGE);
		else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_TERMCONDITION_PAGE);
		
	}
	
	/**
	 * FORWARD CHALLAN TEMPLATE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardChallanTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter forward challan page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
		CourseHandler crsHandler= CourseHandler.getInstance();
		String courseName="";
		String payCode="";
		String courseAmount="";
		List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
		if(courselist!=null && ! courselist.isEmpty()){
			CourseTO to=courselist.get(0);
			courseName=to.getName();
			if(to.getPayCode()!=null )
			payCode=to.getPayCode();
			if(to.getAmount()!=null)
			courseAmount=String.valueOf(to.getAmount().doubleValue());
		}
		admForm.setCourseName(courseName);
		admForm.setCoursePayCode(payCode);
		admForm.setCourseAmount(courseAmount);
		AdmissionFormHandler handler=AdmissionFormHandler.getInstance();
		IProgramTransaction transaction=new ProgramTransactionImpl();
		Program program=transaction.getProgramDetails(Integer.parseInt(admForm.getProgramId()));
		int year =(program.getAcademicYear()!=null)?program.getAcademicYear():0;
		if(year==0){
			year=Calendar.YEAR;
		}
		String y= String.valueOf(year);
		int year1=Integer.parseInt(y.substring(2));
		String latestChallanNo=handler.getNewChallanNo();
		admForm.setChallanRefNo(latestChallanNo+"/"+year1+"/"+(year1+1));
		} catch (Exception e) {
			log.error("error in submit application detail page...",e);
			if(e instanceof BusinessException){
				String msgKey=super.handleBusinessException(e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINELINKS_PAGE);
			}
			else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit forward chaalan page...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_CHALLAN_PAGE);
	}
	
	/**
	 * SUBMIT GUIDELINES PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitGuidelinesPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitGuidelinesPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//check for mandatory check box check
		List<GuideLinesCheckListTO> guidelist=admForm.getGuidelineChecklists();
		ActionErrors errors=admForm.validate(mapping, request);
		if(guidelist!=null){
			Iterator<GuideLinesCheckListTO> guidItr=guidelist.iterator();
			while (guidItr.hasNext()) {
				GuideLinesCheckListTO guideCheckTO = (GuideLinesCheckListTO) guidItr.next();
				if(!guideCheckTO.isChecked()){
					if(errors==null)errors=new ActionErrors();
					if (errors.get(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED);
						errors.add(CMSConstants.ADMISSIONFORM_GUIDELINES_NOTCHECKED, error);
						}
				}
				
			}
		}
		if(!errors.isEmpty())
		{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINELINKS_PAGE);
		}else
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_TERMCONDITION_PAGE);
	}
	
	/**
	 * init terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTermConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initTermConditions...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		CourseHandler crsHandle=CourseHandler.getInstance();
		String termcondition="";
		//get template for term condition
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(Integer.parseInt(admForm.getCourseId()),CMSConstants.TERMS_AND_CONDITION_TEMPLATE);
		if(list != null && !list.isEmpty()) {
			if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null)
				termcondition = list.get(0).getTemplateDescription();
			String logoPath = "";
			byte[] logo = null;
			HttpSession session = request.getSession(false);
			
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if (organisation != null) {
				logo = organisation.getLogo();
			}
			if (session != null) {
				session.setAttribute("LogoBytes", logo);
			}
			logoPath = request.getContextPath();
			logoPath = "<img src="
					+ logoPath
					+ "/LogoServlet alt='Logo not available' width='210' height='100'>";
			//REPLACE LOGO
			termcondition=termcondition.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
		}
		int year = Integer.parseInt(admForm.getApplicationYear());
		List<TermsConditionChecklistTO> checkList=crsHandle.getTermsConditionCheckLists(Integer.parseInt(admForm.getCourseId()),year);
		admForm.setTermConditions(termcondition);
		admForm.setConditionChecklists(checkList);
		if((checkList==null || checkList.isEmpty() ) && (termcondition==null || StringUtils.isEmpty(termcondition) ) )
		{
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_PREREQUISITE_PAGE);
		}
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_TERMCONDITION_PAGE);
	}
	
	/**
	 * Accept terms and conditions
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward acceptTermsConditions(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			 ActionErrors errors = admForm.validate(mapping, request);
			validateOtherConditions(errors,admForm.getConditionChecklists());
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
					resetTermChecklistSubmit(admForm);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_TERMCONDITION_PAGE);
				}
			}catch (Exception e) {
				log.error("error in acceptTermsConditions...",e);
				throw e;
			}
			
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_PREREQUISITE_PAGE);
	}
	
	/**
	 * validates other mandatory conditions
	 * @param errors
	 * @param conditionChecklists
	 */
	private void validateOtherConditions(ActionErrors errors,
			List<TermsConditionChecklistTO> conditionChecklists) {
		//check whether mandatory is checked or not
		if(conditionChecklists!=null){
			Iterator<TermsConditionChecklistTO> chkItr=conditionChecklists.iterator();
			while (chkItr.hasNext()) {
				TermsConditionChecklistTO chkTO = (TermsConditionChecklistTO) chkItr.next();
				if(chkTO.isMandatory() && !chkTO.isChecked())
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED);
						errors.add(CMSConstants.ADMISSIONFORM_TERMCHKLIST_NOTCHECKED, error);
						}
				}
				
			}
		}
	}
	
	/**
	 * reset check option for term condition
	 * @param applicantDetail
	 */
	private void resetTermChecklistSubmit(AdmissionFormForm admform) {
		log.info("enter resetTermChecklistSubmit...");
		if(admform.getConditionChecklists()!=null){
			Iterator<TermsConditionChecklistTO> chkItr=admform.getConditionChecklists().iterator();
			while (chkItr.hasNext()) {
				TermsConditionChecklistTO chkTO = (TermsConditionChecklistTO) chkItr.next();
				if(chkTO.isChecked()){
					chkTO.setChecked(false);
					chkTO.setTempChecked(true);
				}else{
					chkTO.setChecked(false);
					chkTO.setTempChecked(false);
				}
				
			}
		}
	}
	
	/**
	 * INITIALIZES PREREQUISITE APPLY
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPrerequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initPrerequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
		if (prerequisites!=null && !prerequisites.isEmpty()) {
			admForm.setCoursePrerequisites(prerequisites);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PREREQUISITE_PAGE);
		}else{
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE);
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
	public ActionForward submitPreRequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitPreRequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		//Validate prerequisite and back if not
		ActionMessages messages= new ActionMessages();
		// VALIDATE PREREQUISTES REQUIREDS
		messages= validatePrerequisiteRequireds(prerequisites,messages);
		if(messages!=null && messages.isEmpty() )
			// VALIDATE PREREQUISTES ELIGIBILITY
		messages= validatePrerequisite(admForm,messages);
		if(messages!=null && !messages.isEmpty() )
		{
			saveErrors(request, messages);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PREREQUISITE_PAGE);
		}
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		HttpSession session= request.getSession(false);
		if(session==null)
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		// SAVE THE PREREQUISITE DETAILS
		handler.savePrerequisitesToSession(session,admForm.getEligPrerequisites(),admForm.getUserId());
		}catch(ApplicationException e){
			log.error("error in submitPreRequisiteApply...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submitPreRequisiteApply...",e);
				throw e;
			
		}
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE);
	}
	
	/**
	 * checks pre requisite requireds
	 * @param prerequisites
	 * @param messages
	 * @return
	 */
	private ActionMessages validatePrerequisiteRequireds(
			List<CoursePrerequisiteTO> prerequisites, ActionMessages messages) {
		log.info("enter validatePrerequisiteRequireds...");
		if(messages==null){
			messages= new ActionMessages();
		}
		if(prerequisites!=null)
		{
			Iterator<CoursePrerequisiteTO> reqItr=prerequisites.iterator();
			while (reqItr.hasNext()) {
				CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr.next();
				if(reqTO.getUserMark()!=0.0 && reqTO.getTotalMark()!=0.0){
					if(reqTO.getExamMonth()==0){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_REQUIRED, error);
							return messages;
						}
					}
					if(reqTO.getExamYear()==null || StringUtils.isEmpty(reqTO.getExamYear())){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_REQUIRED, error);
							return messages;
						}
					}
					if(reqTO.getRollNo()==null || StringUtils.isEmpty(reqTO.getRollNo().trim())){
						if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_ROLL_REQUIRED, error);
						}
					}
					
					if(reqTO.getExamYear()!=null && !StringUtils.isEmpty(reqTO.getExamYear()) && StringUtils.isNumeric(reqTO.getExamYear())){
						if(CommonUtil.isFutureYear(Integer.parseInt(reqTO.getExamYear()))){
							if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE).hasNext()) {
								ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE);
								messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_YEAR_FUTURE, error);
							}
						}
					}
					
					if(reqTO.getExamMonth()!=0){
						if(CommonUtil.isFutureOrCurrentYear(Integer.parseInt(reqTO.getExamYear()))){
							if(CommonUtil.isFutureMonth(reqTO.getExamMonth()-1)){
								if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE).hasNext()) {
									ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE);
									messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_MONTH_FUTURE, error);
								}
							}
						}
					}
				}
				if(reqTO.getUserMark()>reqTO.getTotalMark()){
					if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER);
						messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_USERMARK_LARGER, error);
					}
				}
			}
		}
		return messages;
	}
	
	/**
	 * checks pre requisite eligibility
	 * @param admForm
	 * @param messages
	 * @return
	 */
	private ActionMessages validatePrerequisite(
			AdmissionFormForm admForm,ActionMessages messages) throws Exception {
		log.info("enter validatePrerequisite...");
		if(messages==null){
			messages= new ActionMessages();
		}
		boolean required=true;
		List<CoursePrerequisiteTO> finalprereqList= new ArrayList<CoursePrerequisiteTO>();
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		if(prerequisites!=null)
		{
			Iterator<CoursePrerequisiteTO> reqItr=prerequisites.iterator();
			while (reqItr.hasNext()) {
				CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr.next();
				if(reqTO.getUserMark()!=0.0){
					required=false;
					break;
				}
			}
			// IF ANY THING EMPTY
			if(required){
				if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED);
					messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_REQUIRED, error);
					}
			}else {
						boolean eligible=false;
						Iterator<CoursePrerequisiteTO> reqItr3=prerequisites.iterator();
						while (reqItr3.hasNext()) {
							CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr3.next();
							double percentage=0.0;
							if(reqTO.getTotalMark()!=0.0)
								percentage=(reqTO.getUserMark()/reqTO.getTotalMark())*100;
							reqTO.setUserPercentage(percentage);
							if(reqTO.getUserPercentage()>=reqTO.getPercentage())
							{
								
								eligible=true;
								finalprereqList.add(reqTO);
								break;
							}
						}
						if(eligible==false){
							if (messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID)!=null && !messages.get(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID);
							messages.add(CMSConstants.ADMISSIONFORM_PREREQUISITE_INVALID, error);
							}
						}
			}
		}
		admForm.setEligPrerequisites(finalprereqList);
		return messages;
	}
	
	/**
	 * saves Application details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitApplicationFormInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit application detail page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		//presently , it is true. it will be false after login implementation
		HttpSession session= request.getSession(false);
		AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
		ActionMessages errors=admForm.validate(mapping, request);
		if(errors!=null && !errors.isEmpty() )
		{
			saveErrors(request, errors);
			setProgramAndCourseMap(admForm,request);
			request.setAttribute("operation", "load");
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PRESIDANCE_ONLINE_APPLICATIONDETAIL_PAGE);
		}
		// validation done
		try {
			handler.setWorkExpNeeded(admForm);
			handler.saveApplicationDetailsToSession(admForm,session);
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			admForm.setSecondLanguageList(secondLanguage);
		} 
		catch (Exception e) {
			log.error("error in submit application detail...",e);
				throw e;
			
		}
		log.info("exit submit application detail page...");
		
		return mapping.findForward(CMSConstants.PRC_INIT_APPLICANT_SINGLE_PAGE);
	}
	
	/**
	 * @param admissionFormForm
	 * @param request
	 * @throws Exception
	 */
	public void setProgramAndCourseMap(AdmissionFormForm admissionFormForm,HttpServletRequest request) throws Exception {
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(admissionFormForm.getProgramTypeId().length() != 0 )
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(admissionFormForm.getProgramTypeId()));
		if(admissionFormForm.getProgramId().length() != 0)
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(admissionFormForm.getProgramId()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
	}
	
	
	
	/**
	 * INITIALIZES Application Single page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicantCreationDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initApplicantCreationDetail...");
		AdmissionFormForm stForm=(AdmissionFormForm)form; 
		try{
			HttpSession session = request.getSession(false);
			AdmissionFormHandler handler=AdmissionFormHandler.getInstance();
			AdmApplnTO applicantDetails = handler.getNewStudent(session,stForm);
			
			if(applicantDetails!=null){
				
				if(applicantDetails.getSelectedCourse()!=null){
					ProgramTO progTo=applicantDetails.getSelectedCourse().getProgramTo();
					if(progTo!=null){ 
					
						if(progTo.getProgramTypeTo()!=null){
							stForm.setAgeFromLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeFrom()));
							stForm.setAgeToLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeTo()));
						}
					}
					
					if(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(applicantDetails.getSelectedCourse().getIsDetailMarkPrint()))
						stForm.setDetailMarksPrint(true);
					else
						stForm.setDetailMarksPrint(false);
				}
				
				
				int appliedyear=Integer.parseInt(stForm.getApplicationYear());

				applicantDetails.setAppliedYear(appliedyear);
				String applnNOgenerated=handler.getGeneratedOnlineAppNo(stForm.getCourseId(),appliedyear,true);
				if(applnNOgenerated!=null && !StringUtils.isEmpty(applnNOgenerated) && StringUtils.isNumeric(applnNOgenerated) )
				{
					applicantDetails.setApplnNo(Integer.parseInt(applnNOgenerated));
					stForm.setApplicationNumber(applnNOgenerated);
					
				}else{
					// sends mail to admin to configure applnno.
					handler.sendMailToAdmin(stForm,appliedyear);
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, error);
					saveErrors(request, message);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINEAPPLY_PAGE);
				} 
				//get states list for edn qualification
				List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
				stForm.setEdnStates(ednstates);
				
				
				
				stForm.setApplicantDetails(applicantDetails);
				String workExpNeeded=applicantDetails.getCourse().getIsWorkExperienceRequired();
				if(stForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
				{
					stForm.setWorkExpNeeded(true);
				}else{
					stForm.setWorkExpNeeded(false);
				}

				CourseTO applicantCourse = applicantDetails.getCourse();
				ProgramTO tempMainProgramTO =applicantCourse.getProgramTo();
				if(tempMainProgramTO!=null){
					applicantCourse.setProgramId(tempMainProgramTO.getId());
					if(tempMainProgramTO.getProgramTypeTo()!=null){
						applicantCourse.setProgramTypeId(tempMainProgramTO.getProgramTypeTo().getProgramTypeId());
					}
				}
				CourseTO selectedCourse=applicantDetails.getSelectedCourse();
				ProgramTO tempProgramTO =selectedCourse.getProgramTo();
				if(tempProgramTO!=null){
					selectedCourse.setProgramId(tempProgramTO.getId());
					if(tempProgramTO.getProgramTypeTo()!=null){
						selectedCourse.setProgramTypeId(tempProgramTO.getProgramTypeTo().getProgramTypeId());
					}
				}
				applicantDetails.setSelectedCourse(selectedCourse);
				applicantDetails.setCourse(applicantCourse);
				List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(selectedCourse.getProgramId());
				stForm.setEntranceList(entrnaceList);
				setSelectedCourseAsPreference(stForm);
				Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());


				applicantDetails.setCreatedBy(stForm.getUserId());
				applicantDetails.setCreatedDate(cal.getTime());

											ProgramTO programTO =selectedCourse.getProgramTo();
												if(programTO!=null){ 
													if(programTO.getIsMotherTongue()==true)
														stForm.setDisplayMotherTongue(true);
													if(programTO.getIsDisplayLanguageKnown()==true)
														stForm.setDisplayLanguageKnown(true);
													if(programTO.getIsHeightWeight()==true)
														stForm.setDisplayHeightWeight(true);
													if(programTO.getIsDisplayTrainingCourse()==true)
														stForm.setDisplayTrainingDetails(true);
													if(programTO.getIsAdditionalInfo()==true)
														stForm.setDisplayAdditionalInfo(true);
													if(programTO.getIsExtraDetails()==true)
														stForm.setDisplayExtracurricular(true);
													if(programTO.getIsSecondLanguage()==true)
														stForm.setDisplaySecondLanguage(true);
													if(programTO.getIsFamilyBackground()==true)
														stForm.setDisplayFamilyBackground(true);
													if(programTO.getIsTCDetails()==true)
														stForm.setDisplayTCDetails(true);
													if(programTO.getIsEntranceDetails()==true)
														stForm.setDisplayEntranceDetails(true);
													if(programTO.getIsLateralDetails()==true)
														stForm.setDisplayLateralDetails(true);
													if(programTO.getIsTransferCourse()==true)
														stForm.setDisplayTransferCourse(true);
													if(programTO.getIsExamCenterRequired()==true)
														stForm.setExamCenterRequired(true);													
												}
											

					
					checkExtradetailsDisplay(stForm);
					checkLateralTransferDisplay(stForm);
					
				if(CountryHandler.getInstance().getCountries()!=null){
					//birthCountry & states
					List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
					if (!birthCountries.isEmpty()) {
						stForm.setCountries(birthCountries);
						
					}
					
				}
				
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
							if(orgTO.getExtracurriculars()!=null)
								applicantDetails.getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
					}

				
				//Nationality
					AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
					stForm.setNationalities(formhandler.getNationalities());
				// languages	
					LanguageHandler langHandler=LanguageHandler.getHandler();
					stForm.setMothertongues(langHandler.getLanguages());
					stForm.setLanguages(langHandler.getOriginalLanguages());
					
					if(stForm.isDisplayAdditionalInfo())
					{
						List<OrganizationTO> orgtos=orgHandler.getOrganizationDetails();
						if(orgtos!=null && !orgtos.isEmpty())
						{
							OrganizationTO orgTO=orgtos.get(0);
							stForm.setOrganizationName(orgTO.getOrganizationName());
							stForm.setNeedApproval(orgTO.isNeedApproval());
						}
					}
					
				// res. catg
					stForm.setResidentTypes(formhandler.getResidentTypes());	
					
					ReligionHandler religionhandler = ReligionHandler.getInstance();
					if(religionhandler.getReligion()!=null){
						List<ReligionTO> religionList=religionhandler.getReligion();
						stForm.setReligions(religionList);
					}
				// caste category
				List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
				stForm.setCasteList(castelist);
				
				// Admitted Through
				List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
				stForm.setAdmittedThroughList(admittedList);
				// subject Group
				List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(selectedCourse.getId());
				stForm.setSubGroupList(sujectgroupList);
				String[] subjectgroups=applicantDetails.getSubjectGroupIds();
				if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
					subjectgroups=new String[sujectgroupList.size()];
					applicantDetails.setSubjectGroupIds(subjectgroups);
				}
				
				//incomes
				List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
				stForm.setIncomeList(incomeList);
					
				//currencies
				List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
				stForm.setCurrencyList(currencyList);
				
				UniversityHandler unhandler = UniversityHandler.getInstance();
				List<UniversityTO> universities = unhandler.getUniversity();
				stForm.setUniversities(universities);
				
				OccupationTransactionHandler occhandler = OccupationTransactionHandler
				.getInstance();
				stForm.setOccupations(occhandler.getAllOccupation());
				
				List<ExamCenterTO> examCenterList = AdmissionFormHandler.getInstance().getExamCenters(selectedCourse.getProgramId());
				stForm.setExamCenters(examCenterList);
				
				// set photo to session
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							stForm.setSubmitDate(docTO.getSubmitDate());
						}
					}
				}
				
				
				
					List<CourseTO> preferences=null;
					// preferences
					if(applicantDetails.getPreference()!=null){
						PreferenceTO prefTo= applicantDetails.getPreference();
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						if(prefTo.getFirstPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getFirstPrefCourseId()))
						{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							firstTo.setId(prefTo.getFirstPerfId());
							firstTo.setAdmApplnid(applicantDetails.getId());
							firstTo.setCoursId(String.valueOf(applicantCourse.getId()));
							firstTo.setCoursName(applicantCourse.getName());
							firstTo.setProgId(String.valueOf(applicantCourse.getProgramId()));
							firstTo.setProgramtypeId(String.valueOf(applicantCourse.getProgramTypeId()));
							firstTo.setPrefNo(1);
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(1);
							prefTos.add(firstTo);
						}
						if(prefTo.getSecondPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getSecondPrefCourseId()))
						{
							CandidatePreferenceTO secTo=new CandidatePreferenceTO();
							secTo.setId(prefTo.getSecondPerfId());
							secTo.setAdmApplnid(applicantDetails.getId());
							secTo.setCoursId(prefTo.getSecondPrefCourseId());
							secTo.setProgId(prefTo.getSecondPrefProgramId());
							secTo.setProgramtypeId(prefTo.getSecondPrefProgramTypeId());
							secTo.setPrefNo(2);
							formhandler.populatePreferenceTO(secTo,applicantCourse);
							preferences=secTo.getPrefcourses();
							prefTos.add(secTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(2);
							prefTos.add(firstTo);
						}
						if(prefTo.getThirdPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getThirdPrefCourseId()))
						{
							CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
							thirdTo.setId(prefTo.getThirdPerfId());
							thirdTo.setPrefNo(3);
							thirdTo.setAdmApplnid(applicantDetails.getId());
							thirdTo.setCoursId(prefTo.getThirdPrefCourseId());
							thirdTo.setProgId(prefTo.getThirdPrefProgramId());
							thirdTo.setProgramtypeId(prefTo.getThirdPrefProgramTypeId());
							formhandler.populatePreferenceTO(thirdTo,applicantCourse);
							preferences=thirdTo.getPrefcourses();
							prefTos.add(thirdTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(3);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}
						stForm.setPreferenceList(prefTos);
					}else{
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,applicantCourse);
						preferences=firstTo.getPrefcourses();
						firstTo.setPrefNo(1);
						prefTos.add(firstTo);
						CandidatePreferenceTO secTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(secTo,applicantCourse);
						secTo.setPrefNo(2);
						prefTos.add(secTo);
						CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(thirdTo,applicantCourse);
						thirdTo.setPrefNo(3);
						prefTos.add(thirdTo);
						stForm.setPreferenceList(prefTos);
					}
					
					//for ajax setting put preference lists in session
					
					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}
				
					stForm.setApplicantDetails(applicantDetails);
			
			}else{
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage("knowledgepro.admission.NoCourseSelected");
					messages.add("messages", message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.PRC_INIT_APPLICANT_SINGLE_PAGE);
			}
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			stForm.setSecondLanguageList(secondLanguage);
		}catch(Exception e){
			log.error("Error in  initApplicantCreationDetail...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}	
		}
		log.info("exit initApplicantCreationDetail...");
		return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
		
	}
	
	/**
	 * sets selected course as default preference
	 * @param stForm
	 */
	private void setSelectedCourseAsPreference(AdmissionFormForm stForm) {
		PreferenceTO to= new PreferenceTO();
		if(stForm.getApplicantDetails()!=null && stForm.getApplicantDetails().getSelectedCourse()!=null){
			
			to.setFirstPrefCourseId(String.valueOf(stForm.getApplicantDetails().getSelectedCourse().getId()));
			}
			stForm.getApplicantDetails().setPreference(to);
		
	}
	
	
	/**
	 * creates a applicant record
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitApplicantCreation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitApplicantCreation...");
		AdmissionFormForm stForm=(AdmissionFormForm)form;
		
		try{
			if(stForm.isDataSaved()){
				ActionMessages messages = new ActionMessages(); 
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,stForm.getApplicantDetails().getApplnNo(),stForm.getApplicantDetails().getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.PRC_APPLICANT_SINGLE_CONFIRM_PAGE);
			}			
			setConfirmationPageDetails(stForm, request);
			ActionMessages errors=stForm.validate(mapping, request);
			if(stForm.getApplicantDetails().getApplnNo()==0){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED) != null&& !errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED,new ActionError(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}
			if(stForm.getApplicantDetails().getSelectedCourse().getProgramTo()!= null && stForm.getApplicantDetails().getSelectedCourse().getProgramTo().getIsExamCenterRequired() &&
				stForm.getApplicantDetails().getExamCenterId() == 0){
				if (errors.get("knowledgepro.admission.appln.exam.center.required") != null&& !errors.get("knowledgepro.admission.appln.exam.center.required").hasNext()) {
					errors.add("knowledgepro.admission.appln.exam.center.required",new ActionError("knowledgepro.admission.appln.exam.center.required"));
				}
			}
			
//			if(stForm.isDisplaySecondLanguage() && (stForm.getApplicantDetails().getPersonalData().getSecondLanguage() == null || stForm.getApplicantDetails().getPersonalData().getSecondLanguage().trim().isEmpty())){
//				if (errors.get("knowledgepro.admin._Exam_Second_Language_Master.required") != null&& !errors.get("knowledgepro.admin._Exam_Second_Language_Master.required").hasNext()) {
//					errors.add("knowledgepro.admin._Exam_Second_Language_Master.required",new ActionError("knowledgepro.admin._Exam_Second_Language_Master.required"));
//				}	
//			}
			
			if(stForm.isSameTempAddr()){
				copyCurrToPermAddress(stForm);
			}
			
			validateOnlineConfirmRequireds(stForm, errors);
			validateParentConfirmOnlineRequireds(stForm, errors);
			
			//email comparision
			if(stForm.getApplicantDetails().getPersonalData()!=null && stForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getEmail())){
				if(stForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
						if(!stForm.getApplicantDetails().getPersonalData().getEmail().equals(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(stForm.getApplicantDetails().getPersonalData().getConfirmEmail()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getConfirmEmail())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}
			
			if(stForm.getApplicantDetails().getPersonalData().getMobileNo1()==null || stForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile Country Code"));
			}
			if(stForm.getApplicantDetails().getPersonalData().getMobileNo2()==null || stForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("errors.required"," Mobile No "));
			}
			
			// online age range check
			
			if(stForm.getAgeToLimit()!=0 && stForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getDob())){
				boolean valid=validateOnlineDOB(stForm.getAgeFromLimit(),stForm.getAgeToLimit(),stForm.getApplicantDetails().getPersonalData().getDob());
				if(!valid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
					}
				}
		}
			validateEditPhone(stForm, errors);
			validateEditParentPhone(stForm, errors);
			validateEditGuardianPhone(stForm, errors);
			validateEditPassportIfNRI(stForm, errors);
			validateEditOtherOptions(stForm, errors);
			
			validateEditCommAddress(stForm, errors);
			validatePermAddress(stForm, errors);
//			validateSubjectGroups(stForm, errors);
			if(stForm.isDisplayTCDetails())
			validateTcDetailsEdit(stForm, errors);
			if(stForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(stForm, errors);
			if (stForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// validate Admission Date
			if (stForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getAdmissionDate())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getAdmissionDate())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getAdmissionDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
					}
				}
			}
			
			
			if (stForm.getApplicantDetails().getPersonalData()!=null && stForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(stForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			}
			if(stForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(stForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(stForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (stForm.getSubmitDate()!=null && !StringUtils.isEmpty(stForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(stForm.getSubmitDate())){
				boolean	isValid = validatePastDate(stForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			
			
			List<ApplicantWorkExperienceTO> expList=stForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
				}
			}
			validateEditEducationDetails(errors, stForm);
			//validateEditDocumentSize(stForm, errors);
			validateEditDocumentSizeOnline(stForm, errors,request); //semester marks card required msg is coming. could not replicate the problem. so created another method without that validation
			if(stForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(stForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(stForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(stForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(stForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(stForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			int prefLength = 0;
			int selectedLen = 0;
			List<Integer> notSelectedPref = new ArrayList<Integer>();
			List<CandidatePreferenceTO> prfList = stForm.getPreferenceList();
			int count = 0;
			if(prfList!= null && prfList.size() > 0){
				Iterator<CandidatePreferenceTO> prItr =  prfList.iterator();
				while (prItr.hasNext()) {
					CandidatePreferenceTO candidatePreferenceTO = (CandidatePreferenceTO) prItr
							.next();
					if(candidatePreferenceTO.getPrefcourses()!= null && candidatePreferenceTO.getPrefcourses().size() > 0){
						prefLength = candidatePreferenceTO.getPrefcourses().size();
						
						if(candidatePreferenceTO.getCoursId()!= null && !candidatePreferenceTO.getCoursId().trim().isEmpty() && candidatePreferenceTO.getPrefNo() > 1){
							selectedLen++;
						}
						else if (candidatePreferenceTO.getPrefNo() > 1 && count <= prefLength){
							notSelectedPref.add(candidatePreferenceTO.getPrefNo());
						}
					}
					count++;
					
				}
			}
			if(prefLength > selectedLen){
				Iterator<Integer> strItr  = notSelectedPref.iterator();
				while (strItr.hasNext()) {
					Integer prefNo = (Integer) strItr.next();
					errors.add("knowledgepro.admission.online.apply.pref.required",new ActionError("knowledgepro.admission.online.apply.pref.required", prefNo));				
				}
			}
			if (errors != null && !errors.isEmpty()) {
				resetHardCopySubmit(stForm.getApplicantDetails());
				if(stForm.isReviewWarned()){
					setDocumentForView(stForm, request);	
				}
				saveErrors(request, errors);
				stForm.setReviewWarned(false);
				if (stForm.isRemove()) {
					removePhotoDoc(stForm, request);
				}
				return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
				
			}

			
			AdmApplnTO applicantDetail=stForm.getApplicantDetails();
			int year=Integer.parseInt(stForm.getApplicationYear());
			//set current year
			if(applicantDetail!=null)
				applicantDetail.setAppliedYear(year);
			AdmissionFormHandler admHandler = AdmissionFormHandler.getInstance();
			if(!stForm.isReviewWarned())
			{
				stForm.setReviewWarned(true);
				resetHardCopySubmit(applicantDetail);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.APPLICATION_REVIEW_WARN);
				messages.add("messages", message);
				saveMessages(request, messages);
				setDocumentForView(stForm, request);
				return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
			}
			
			boolean updated=admHandler.createApplicant(applicantDetail,stForm,true);

			if(updated){
				admHandler.sendMailToStudentSinglePage(stForm);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,stForm.getApplicantDetails().getApplnNo(),applicantDetail.getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				stForm.setDataSaved(false);
			}
			
			
		}catch(Exception e){
			log.error("Error in  submitStudentCreation...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				stForm.setErrorMessage(msg);
				stForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}	
		}
		log.info("exit submitStudentCreation...");
//		return mapping.findForward(CMSConstants.SINGLE_PAGE_APP_VERIFY);
		return mapping.findForward(CMSConstants.PRC_APPLICANT_SINGLE_CONFIRM_PAGE);
		
	}
	
	/**
	 * 
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setConfirmationPageDetails(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(PresidanceAdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(PresidanceAdmissionFormAction.PHOTOBYTES);
		
		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
	
		
		if(stForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =stForm.getApplicantDetails().getEditDocuments().iterator();
			ApplnDocTO applnDocTO;
			
			while(itr.hasNext()){
				applnDocTO= itr.next();
				if(applnDocTO.getEditDocument()!= null && applnDocTO.getEditDocument().getFileName()!= null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
					applnDocTO.setCurrDocument(applnDocTO.getEditDocument().getFileData());
					if(!applnDocTO.isPhoto()){
						applnDocTO.setDocumentPresent(true);
					}
					applnDocTO.setName(applnDocTO.getEditDocument().getFileName());
					applnDocTO.setContentType(applnDocTO.getEditDocument().getContentType());					
					
				}
				if(applnDocTO.isPhoto()){
					if(applnDocTO.getEditDocument()!= null){
						if(applnDocTO.getDocName()!=null && applnDocTO.getDocName().equalsIgnoreCase("Photo")){
							if(applnDocTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
								if(session!=null){
									stForm.setRemove(false);
									session.setAttribute(PresidanceAdmissionFormAction.PHOTOBYTES, applnDocTO.getEditDocument().getFileData());
								}
							}
						else{
							if(session!=null){
								stForm.setRemove(false);
								session.setAttribute(PresidanceAdmissionFormAction.PHOTOBYTES, applnDocTO.getCurrDocument());
							}
							}
						}
					}
				}
				
				
			}
		}
	}
	
	/**
	 * COPIES CURENT ADDRESS TO PERMANENT ADDRESS
	 * @param stForm
	 */
	private void copyCurrToPermAddress(AdmissionFormForm stForm) {
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressLine1(stForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1());
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressLine2(stForm.getApplicantDetails().getPersonalData().getCurrentAddressLine2());
		stForm.getApplicantDetails().getPersonalData().setPermanentCityName(stForm.getApplicantDetails().getPersonalData().getCurrentCityName());
		stForm.getApplicantDetails().getPersonalData().setPermanentCountryId(stForm.getApplicantDetails().getPersonalData().getCurrentCountryId());
		if(stForm.getApplicantDetails().getPersonalData().getCurrentStateId()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentStateId().trim()))
		stForm.getApplicantDetails().getPersonalData().setPermanentStateId(stForm.getApplicantDetails().getPersonalData().getCurrentStateId());
		if(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()!=null && !StringUtils.isEmpty(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()))
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressStateOthers(stForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers());
		stForm.getApplicantDetails().getPersonalData().setPermanentAddressZipCode(stForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode());
		
	}
	
	/**
	 * 
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void setDocumentForView(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(stForm.getApplicantDetails().getEditDocuments()!= null){
			Iterator<ApplnDocTO> itr =stForm.getApplicantDetails().getEditDocuments().iterator();
			ApplnDocTO applnDocTO;
			ApplnDoc applnDoc;
			List<ApplnDoc> upLoadList = new ArrayList<ApplnDoc>();
			DocType docType;
			while(itr.hasNext()){
				applnDocTO= itr.next();
				applnDoc = new ApplnDoc();
				docType = new DocType();
				if(applnDocTO.getEditDocument()!= null && applnDocTO.getEditDocument().getFileName()!= null && !StringUtils.isEmpty(applnDocTO.getEditDocument().getFileName())){
					applnDoc.setDocument(applnDocTO.getEditDocument().getFileData());
					applnDoc.setName(applnDocTO.getName());
					applnDoc.setContentType(applnDocTO.getEditDocument().getContentType());
					docType.setId(applnDocTO.getDocTypeId());
					applnDoc.setDocType(docType);
					upLoadList.add(applnDoc);
				}
				else
				{
					applnDoc.setDocument(applnDocTO.getCurrDocument());
					applnDoc.setName(applnDocTO.getName());
					applnDoc.setContentType(applnDocTO.getContentType());
					docType.setId(applnDocTO.getDocTypeId());
					applnDoc.setDocType(docType);
					upLoadList.add(applnDoc);
					
				}
			}
			session.setAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS, upLoadList);
		}
	}
	
	/**
	 * reset check option for validation fail
	 * @param applicantDetail
	 */
	private void resetHardCopySubmit(AdmApplnTO applicantDetail) {
		log.info("enter resetHardCopySubmit...");
		if(applicantDetail!=null && applicantDetail.getEditDocuments()!=null){
			Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if(docTO.isHardSubmitted()){
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(true);
				}else{
					docTO.setHardSubmitted(false);
					docTO.setTemphardSubmitted(false);
				}
				if(docTO.isNotApplicable()){
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(true);
				}else{
					docTO.setNotApplicable(false);
					docTO.setTempNotApplicable(false);
				}
			}
		}
	}
	/**
	 * 
	 * @param stForm
	 * @param request
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void removePhotoDoc(AdmissionFormForm stForm, HttpServletRequest request) throws FileNotFoundException, IOException{
		HttpSession session=request.getSession(false);
		if(session!= null && session.getAttribute(PresidanceAdmissionFormAction.PHOTOBYTES)!=null)
			session.removeAttribute(PresidanceAdmissionFormAction.PHOTOBYTES);
		
		if(session!= null && session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
			session.removeAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
		
		List<ApplnDocTO> applnDocTOs=stForm.getApplicantDetails().getEditDocuments();
		if(applnDocTOs!= null){
			Iterator<ApplnDocTO> itr =applnDocTOs.iterator();
			ApplnDocTO applnDocTO;
			
			while(itr.hasNext()){
				applnDocTO= itr.next();
				if(applnDocTO.isPhoto()){
					applnDocTO.setEditDocument(null);
					applnDocTO.setCurrDocument(null);
				}
			}
			stForm.getApplicantDetails().setEditDocuments(applnDocTOs);
		}
	}
	
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateOnlineConfirmRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("entered validateOnlineRequireds..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if((admForm.getApplicantDetails().getPersonalData().getBirthPlace()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthPlace())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHPLACE_REQUIRED, error);
			}
		}
		
		if((admForm.getApplicantDetails().getPersonalData().getBloodGroup()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBloodGroup())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BLOODGROUP_REQUIRED, error);
			}
		}
		
//		if((admForm.getApplicantDetails().getPersonalData().getEmail()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())))
//		{
//			if (errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED).hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_EMAILID_REQUIRED, error);
//			}
//		}
		
		if(admForm.getApplicantDetails().getPersonalData().getAreaType()==' ')
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_AREATYPE_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHCNT_REQUIRED, error);
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())|| admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))&& (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
			}
		}
		
//		if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1())) && (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2())) && (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
//		{
//			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
//			}
//		}
		
		
		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,
						error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED,error);
			}
		}
		
		if(admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()==0 )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED,error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED).hasNext()) {
			ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
			errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED,error);
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}
	
		log.info("exit validateOnlineRequireds..");
	}
	
	/**
	 * online parent mandatory check
	 * @param admForm
	 * @param errors
	 */
	private void validateParentConfirmOnlineRequireds(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("entered validateParentConfirmOnlineRequireds..");
		if(errors==null){
			errors= new ActionMessages();
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null && (admForm.getApplicantDetails().getPersonalData().getFatherName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERNAME_REQUIRED, error);
			}
		}
		
		if(admForm.getApplicantDetails().getPersonalData()!=null && (admForm.getApplicantDetails().getPersonalData().getMotherName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherName())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERNAME_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null &&(admForm.getApplicantDetails().getPersonalData().getFatherIncomeId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getFatherIncomeId())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FATHERINCOME_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData()!=null &&(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMotherIncomeId())))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_MOTHERINCOME_REQUIRED, error);
			}
		}
		log.info("exit validateParentConfirmOnlineRequireds..");
	}
	
	/**
	 * checks year difference
	 * @param ageFromLimit
	 * @param ageToLimit
	 * @param dateOfBirth 
	 * @return
	 */
	private boolean validateOnlineDOB(int ageFromLimit, int ageToLimit, String dateOfBirth) {
		
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
		Date birthdate = new Date(formattedString);
		Calendar cal2= Calendar.getInstance();
		cal2.setTime(birthdate);
		cal2.set(Calendar.HOUR_OF_DAY, 0);
		cal2.set(Calendar.MINUTE, 0);
		cal2.set(Calendar.SECOND, 0);
		cal2.set(Calendar.MILLISECOND, 0);
		Date brtdate=cal2.getTime();
		Date curdate = new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date todaydate=cal.getTime();
		int yearDiff=CommonUtil.getYearsDiff(brtdate, todaydate);
		if(yearDiff>=ageFromLimit && yearDiff<=ageToLimit)
			return true;
		else
			return false;
	}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPhone(AdmissionFormForm admForm, ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();
		
//				if((admForm.getApplicantDetails().getPersonalData().getPhNo1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()))
//						&& (admForm.getApplicantDetails().getPersonalData().getPhNo2()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()))
//						&& (admForm.getApplicantDetails().getPersonalData().getPhNo3()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3())))
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_PHONE_REQUIRED);
//						errors.add(CMSConstants.ADMISSIONFORM_PHONE_REQUIRED, error);
//					}
//				}

				if(admForm.getApplicantDetails().getPersonalData().getPhNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				
//				if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && admForm.getApplicantDetails().getPersonalData().getMobileNo2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				log.info("exit validateEditPhone..");
	}
	
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditParentPhone(AdmissionFormForm admForm, ActionMessages errors) {
		log.info("enter validateEditParentPhone..");
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getApplicantDetails().getPersonalData().getParentPh1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentPh2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentPh3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentPh3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentPh3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTPHONE_INVALID, error);
					}
				}
				
				if(admForm.getApplicantDetails().getPersonalData().getParentMob1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentMob2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getParentMob3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getParentMob3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getParentMob3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PARENTMOBILE_INVALID, error);
					}
				}
				log.info("exit validateEditParentPhone..");
	}
	
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditGuardianPhone(AdmissionFormForm admForm, ActionMessages errors) {
		log.info("enter validateEditGuardianPhone..");
		if(errors==null)
			errors= new ActionMessages();
		

				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianPh3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANPHONE_INVALID, error);
					}
				}
				
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getGuardianMob3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_GUARDIANMOBILE_INVALID, error);
					}
				}
				log.info("exit validateEditGuardianPhone..");
	}
	
	/**
	 * admission form PASSPORT DATA validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditPassportIfNRI(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPassportIfNRI..");
		if(errors==null)
			errors= new ActionMessages();
		if(admForm.getApplicantDetails().getPersonalData().getResidentCategory()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentCategory())){
			if(admForm.getApplicantDetails().getPersonalData().getResidentCategory()!=null && StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getResidentCategory())&& !CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.valueOf((Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getResidentCategory()))))){
				if(admForm.getApplicantDetails().getPersonalData().getPassportNo()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportNo()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED,error);
				}
				if(admForm.getApplicantDetails().getPersonalData().getPassportCountry()==0)
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED,error);
				}
				if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED,error);
				}else if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
				}
				
			}
			
		}
		log.info("exit validateEditPassportIfNRI..");
}
	
	/**
	 * past date validation
	 * @param dateString
	 * @return
	 */
	private boolean validatePastDate(String dateString) {
		log.info("enter validatePastDate..");
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate=cal.getTime();
		
		log.info("exit validatePastDate..");
		return !(date.compareTo(origdate) < 0);
			
	
	}
	
	/**
	 * future date validation
	 * @param dateString
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
			Date date = new Date(formattedString);
			Date curdate = new Date();
			Calendar cal= Calendar.getInstance();
			cal.setTime(curdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Date origdate=cal.getTime();
			log.info("exit validatefutureDate..");
			return !(date.compareTo(origdate) == 1);
			
		
	}
	
	
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception {
		log.info("enter validateEditOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}
//		if((admForm.getApplicantDetails().getPersonalData().getBirthState()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getBirthState())|| admForm.getApplicantDetails().getPersonalData().getBirthState().equalsIgnoreCase(AdmissionFormAction.OTHER))&& (admForm.getApplicantDetails().getPersonalData().getStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getStateOthers()) ))
//		{
//			if (errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED).hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_BIRTHSTATE_REQUIRED, error);
//			}
//		}
		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getPersonalData().getReligionId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId()) && StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId())){
			ISubReligionTransaction relTxn=new SubReligionTransactionImpl();
			//if master entry exists
			if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()))){
				if(admForm.isCasteDisplay()){
					if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
						}
					}
				}
			}
		}
		if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) )&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
			}
		}
		log.info("exit validateEditOtherOptions..");
	}
	
	/**
	 * application form validation
	 * @param TO
	 * @param errors
	 */
	private void validateWorkExperience(ApplicantWorkExperienceTO TO,ActionMessages errors) {
		log.info("enter validateWorkExperience..");
		if(errors==null)
			errors= new ActionMessages();
		if(TO!=null)
		{
			boolean validTODate=false;
			boolean validFromDate=false;
			if(TO.getFromDate()!=null && !StringUtils.isEmpty(TO.getFromDate()) && !CommonUtil.isValidDate(TO.getFromDate())){
				validFromDate=false;
			}else{
				validFromDate=true;
			}
			if(TO.getToDate()!=null && !StringUtils.isEmpty(TO.getToDate()) && !CommonUtil.isValidDate(TO.getToDate())){
				validTODate=false;
			}else{
				validTODate=true;
			}
			if(validTODate && validFromDate){
			if(TO.getFromDate()!=null && !TO.getFromDate().isEmpty() && TO.getToDate()!=null && !TO.getToDate().isEmpty())
			{
				String formdate=CommonUtil.ConvertStringToDateFormat(TO.getFromDate(), PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
				String todate=CommonUtil.ConvertStringToDateFormat(TO.getToDate(), PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
				Date startdate=new Date(formdate);
				Date enddate=new Date(todate);
				
				
					if(startdate.compareTo(enddate)==1)
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_ENDDATE));
						}
					}
					if(enddate.compareTo(startdate)==0)
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_EXP_DATESAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EXP_DATESAME).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_EXP_DATESAME, new ActionError(CMSConstants.ADMISSIONFORM_EXP_DATESAME));
						}
						
					}

			}
		}else if(!validTODate){
			if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
			}
		}else if(!validFromDate){
			if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_STARTDATE_INVALID));
			}
		}
			if(TO.getSalary()!=null && !StringUtils.isEmpty(TO.getSalary()) && !CommonUtil.isValidDecimal(TO.getSalary()) ){
				if (errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_WORKEXP_SALARY_INVALID));
				}
			}
		}
		log.info("exit validateWorkExperience..");
	}
	
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCommAddress(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditCommAddress..");
		if(errors==null)
			errors= new ActionMessages();
		
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressLine1()))
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS1_REQUIRED,error);
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentCityName()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_CITY_REQUIRED,error);
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getCurrentCountryId()==0 )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_COUNTRY_REQUIRED,error);
			}
			if(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode()) )
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ZIP_REQUIRED,error);
			}else if(!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getCurrentAddressZipCode())){
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID,error);
			}
			if((admForm.getApplicantDetails().getPersonalData().getCurrentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCurrentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_COMM_STATE_REQUIRED, error);
				}
			}
			log.info("exit validateEditCommAddress..");
	}
	/**
	 * permanent address validation
	 * @param stForm
	 * @param errors
	 */
	private void validatePermAddress(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validatePermAddress..");
		if(errors==null)
			errors= new ActionMessages();
		
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressLine1()))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ADDRESS1_REQUIRED,error);
				}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentCityName()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM__CITY_REQUIRED,error);
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()==0 )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_COUNTRY_REQUIRED,error);
				}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode()) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_REQUIRED,error);
				}
			}else if(!StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPermanentAddressZipCode())){
				if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID,error);
				}
			}
			if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()))&& (admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())) )
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
				}
			}
		
			log.info("exit validatePermAddress..");
	}
	
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditEducationDetails(ActionMessages errors,
			AdmissionFormForm admForm) {
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications=admForm.getApplicantDetails().getEdnQualificationList();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 )|| qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				if((qualfTO.getInstitutionId()==null ||qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other") && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 ))))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				if(qualfTO.isExamConfigured()&& (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,error);
						}
				}
				if(qualfTO.getPercentage()==null || StringUtils.isEmpty(qualfTO.getPercentage()))
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Percentage"));
				}else{
					if(!CommonUtil.isValidDecimal(qualfTO.getPercentage())){
						errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.totalmarks.numeric"));
					}else{
						double d=Double.parseDouble(qualfTO.getPercentage());
						if(d>100){
							errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.percentage.greater"));
						}
					}
				}
				if(qualfTO.getYearPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,error);
						}
				}else{
					boolean valid=CommonUtil.isFutureNotCurrentYear(qualfTO.getYearPassing());
					if(valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,error);
						}
					}
					
				}
				if(!admForm.getPucApplicationEdit() && !admForm.getPucApplicationDetailEdit()){
				if(qualfTO.getMonthPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,error);
						}
				}
//				if(qualfTO.isLastExam() && (qualfTO.getPreviousRegNo()==null || StringUtils.isEmpty(qualfTO.getPreviousRegNo().trim()) ))
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED).hasNext()) {
//						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED);
//						errors.add(CMSConstants.ADMISSIONFORM_REGNO_REQUIRED,error);
//					}
//				}
				}
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				if(qualfTO.isConsolidated()){
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) && !CommonUtil.isValidDecimal(qualfTO.getTotalMarks())){
						if (errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,error);
						}
					}
//					if(qualfTO.getMarksObtained()==null || StringUtils.isEmpty(qualfTO.getMarksObtained()))
//					{
//							if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED).hasNext()) {
//								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED);
//								errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_REQUIRED,error);
//							}
//					}
					if(qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) && !CommonUtil.isValidDecimal(qualfTO.getMarksObtained())){
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,error);
						}
					}
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) 
							&& CommonUtil.isValidDecimal(qualfTO.getMarksObtained()) &&
							qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) 
							&& CommonUtil.isValidDecimal(qualfTO.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks())< Double.parseDouble(qualfTO.getMarksObtained()))
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,error);
						}
					}
					
				}else{
					if(qualfTO.isSemesterWise()){
						if(qualfTO.getSemesterList()==null)
						{
								if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
									ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
									errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
								}
						}
					}else if(qualfTO.getDetailmark()==null)
					{
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							}
					}
				}
				
				if(qualfTO.getYearPassing()!=0 && qualfTO.getMonthPassing()!=0){
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
						boolean futurethanBirth=isPassYearGreaterThanBirth(qualfTO.getYearPassing(),qualfTO.getMonthPassing(),admForm.getApplicantDetails().getPersonalData().getDob());
						if(!futurethanBirth){
							if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
							}
						}
					}
				}
				
			}
			log.info("exit validate education...");
		}
		return errors;
	}
	
	/**
	 * compares birth date with pass year,month
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanBirth(int yearPassing,int monthPassing,
			String dateOfBirth) {
		boolean result=false;
		if(yearPassing!=0 && dateOfBirth!=null && !StringUtils.isEmpty(dateOfBirth)){
			String formattedString=CommonUtil.ConvertStringToDateFormat(dateOfBirth, PresidanceAdmissionFormAction.FROM_DATEFORMAT,PresidanceAdmissionFormAction.TO_DATEFORMAT);
			Date birthdate = new Date(formattedString);
			Calendar cal = Calendar.getInstance();
			cal.setTime(birthdate);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
//			cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			if(monthPassing >1)
			cal2.set(Calendar.MONTH, monthPassing-1);
			else
				cal2.set(Calendar.MONTH,1);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
//			if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing> cal.get(Calendar.YEAR))
			if(cal2.getTime().after(cal.getTime()))
				result=true;
		}
		return result;
	}
	
	/**
	 * tc detail validation
	 * @param admForm
	 * @param errors
	 */
	private void validateTcDetailsEdit(AdmissionFormForm admForm,
			ActionMessages errors) {
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getTcDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getTcDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getApplicantDetails().getTcDate().trim()) ){
				if(!validatefutureDate(admForm.getApplicantDetails().getTcDate())){
					if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE,error);
					}
				}
			
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_TCDATE_INVALID,error);
					}
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getMarkscardDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getMarkscardDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getApplicantDetails().getMarkscardDate().trim()) ){
			if(!validatefutureDate(admForm.getApplicantDetails().getMarkscardDate().trim())){
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE,error);
					}
				}
			
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID,error);
					}
				}
		}
	}
	
	/**
	 * entrance validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEntanceDetailsEdit(AdmissionFormForm admForm,
			ActionMessages errors) {
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null && 
				admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null &&
				admForm.getApplicantDetails().getEntranceDetail().getMarksObtained()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null &&
				admForm.getApplicantDetails().getEntranceDetail().getMarksObtained()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) && CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained().trim()) 
				&& admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim()) && CommonUtil.isValidDecimal(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks().trim())
				&& Double.parseDouble(admForm.getApplicantDetails().getEntranceDetail().getMarksObtained())> Double.parseDouble(admForm.getApplicantDetails().getEntranceDetail().getTotalMarks()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE, error);
				}
		}
		
		//check date of birth cross and present date cross
		if((admForm.getApplicantDetails()!=null && admForm.getApplicantDetails().getEntranceDetail()!=null && admForm.getApplicantDetails().getEntranceDetail().getYearPassing()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()) && StringUtils.isNumeric(admForm.getApplicantDetails().getEntranceDetail().getYearPassing())) 
				&& admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()) && StringUtils.isNumeric(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing())){
			if(admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob()) 
					&& CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean futurethanBirth=isPassYearGreaterThanBirth(Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()),Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()),admForm.getApplicantDetails().getPersonalData().getDob());
				if(!futurethanBirth){
					if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE,error);
					}
				}
			}
			Calendar cal= Calendar.getInstance();
			Date today= cal.getTime();
			boolean futurethantoday=isPassYearGreaterThanToday(Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getYearPassing()),Integer.parseInt(admForm.getApplicantDetails().getEntranceDetail().getMonthPassing()),today);
			if(futurethantoday){
				if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE);
					errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE,error);
				}
			}
		}
	}
	
	/**
	 * compares curent date with pass year,month
	 * @param yearPassing
	 * @param monthPassing
	 * @param dateOfBirth
	 * @return
	 */
	private boolean isPassYearGreaterThanToday(int yearPassing,int monthPassing,
			Date today) {
		boolean result=false;
		if(yearPassing!=0 && monthPassing!=0 && today!=null){
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(today);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Calendar cal2 = Calendar.getInstance();
//			cal2.setTime(birthdate);
			cal2.set(Calendar.DATE, 1);
			if(monthPassing >1)
				cal2.set(Calendar.MONTH, monthPassing-1);
			else
				cal2.set(Calendar.MONTH,1);
			cal2.set(Calendar.YEAR, yearPassing);
			cal2.set(Calendar.HOUR_OF_DAY, 0);
			cal2.set(Calendar.MINUTE, 0);
			cal2.set(Calendar.SECOND, 0);
			cal2.set(Calendar.MILLISECOND, 0);
			// if pass year larger than birth year
//			if(yearPassing== cal.get(Calendar.YEAR)|| yearPassing> cal.get(Calendar.YEAR))
			if(cal2.getTime().after(cal.getTime()))
				result=true;
		}
		return result;
	}
	
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSizeOnline(AdmissionFormForm admForm,
			ActionMessages errors,HttpServletRequest request) throws Exception {
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
		int maXSize=0;
		int maxPhotoSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error in Reading key from properties file....",e);
		}
		if(uploadlist!=null){
			Iterator<ApplnDocTO> uploaditr=uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file=null;
				if(docTo!=null)
					file=docTo.getEditDocument();
				if(file!=null)
				{
					String filename=file.getFileName();
					if(!StringUtils.isEmpty(filename)&& filename.length()>30)
					{
						if(errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME, error);
						}
					}
				}
				if(docTo.isPhoto() && file!=null ){
					boolean remove=validateImageHeightWidth(file.getFileData(), file.getFileName(), file.getContentType(), errors,request);
					admForm.setRemove(remove);
					if(maxPhotoSize< file.getFileSize()){
					if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
					}
					}
					if(file.getFileName()!=null && !StringUtils.isEmpty(file.getFileName().trim())){
						String extn="";
						int index = file.getFileName().lastIndexOf(".");
						if(index != -1){
							extn = file.getFileName().substring(index, file.getFileName().length());
						}
						if(!extn.equalsIgnoreCase(".jpg")){
							if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
							}
						}
					}
					
				}
				else if(file!=null && maXSize< file.getFileSize())
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
					}
				}
			}
			
		}
		
	}
	
	/**
	 * 
	 * @param fileData
	 * @param contentType
	 * @param fileName
	 * @param errors
	 * @returns dimension validation error
	 * Checks for an image height and width
	 * Only allows to upload image of dimension 238*100
	 * @throws Exception
	 */
	public boolean validateImageHeightWidth(byte[] fileData,String fileName,String contentType, ActionMessages errors,HttpServletRequest request)throws Exception{
		boolean remove=false;
		if(fileData!=null && fileName != null && !StringUtils.isEmpty(fileName) && contentType!=null && !StringUtils.isEmpty(contentType) ){
		
			File file = null;
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+fileName);
			InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			file = new File(filePath+fileName);
			String path = file.getAbsolutePath();
			Image image = Toolkit.getDefaultToolkit().getImage(path);
			ImageIcon icon = new ImageIcon(image);
		    int height = icon.getIconHeight();
		    int width = icon.getIconWidth();
		      if(width > 97 || height > 97){
		    	  remove=true;
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_DIMENSION));
		      }
		}
		return remove;
		}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationModify(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		cleanupEditData(admForm);
		admForm.setApplicationYear(null);
		admForm.setOnlineApply(false);
		admForm.setPucApplicationEdit(false);
		log.info("exit initApplicationEdit..");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITMODIFY_PAGE);
	}
	
	/**
	 * reset blank display after admission submit
	 * @param admForm
	 */
	private void cleanupEditData(AdmissionFormForm admForm) {
		log.info("enter cleanupEditData...");
		admForm.setProgramId("");
		admForm.setProgramTypeId("");
		admForm.setCourseId("");
		admForm.setQuotaCheck(false);
		admForm.setEligibleCheck(false);
		admForm.setReviewWarned(false);
		admForm.setSubmitDate(null);
		admForm.setLateralDetails(null);
		admForm.setLateralInstituteAddress(null);
		admForm.setLateralStateName(null);
		admForm.setLateralUniversityName(null);
		admForm.setApplicationConfirm(false);
		admForm.setExamCenterRequired(false);
		admForm.setPucApplicationEdit(false);
		admForm.setPucInterviewDate(null);
		admForm.setRecomendedBy(null);
	}
	
	
	/**
	 * prepare admission data to display
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getApplicantDetailsForEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered detailApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			 ActionErrors errors = admForm.validate(mapping, request);
			
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				if(!admForm.isAdmissionEdit())
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITMODIFY_PAGE);
				else
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITEDIT_PAGE);
			}
				String applicationNumber = admForm.getApplicationNumber().trim();
				int applicationYear = Integer.parseInt(admForm.getApplicationYear());
				AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicantDetails(applicationNumber, applicationYear,admForm.isAdmissionEdit());
				
				if( applicantDetails == null){
					
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
						message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add("messages", message);
						saveMessages(request, messages);
						
						if(!admForm.isAdmissionEdit())
							return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITMODIFY_PAGE);
						else
						return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITEDIT_PAGE);
					
				} else {
					List<ExamCenterTO> examCenterList = AdmissionFormHandler.getInstance().getExamCenters(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setExamCenters(examCenterList);

					//get states list for edn qualification
					List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
					admForm.setEdnStates(ednstates);
					List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(applicantDetails.getSelectedCourse().getProgramId());
					admForm.setEntranceList(entrnaceList);
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
						applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
					if(applicantDetails.getChallanDate()!=null )
						applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
					
					if(applicantDetails.getAdmissionDate() == null || !applicantDetails.getAdmissionDate().trim().isEmpty()){
						applicantDetails.setAdmissionDate(CommonUtil.getTodayDate());
					}
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getRecommentedBy()!=null )
						admForm.setRecomendedBy(applicantDetails.getPersonalData().getRecommentedBy());
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getRecommendDeatails()!=null )
						admForm.setRecomendedBy(applicantDetails.getPersonalData().getRecommendDeatails());
					admForm.setApplicantDetails(applicantDetails);
					String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
					if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
					{
						admForm.setWorkExpNeeded(true);
					}else{
						admForm.setWorkExpNeeded(false);
					}
						admForm.setApplicantDetails(applicantDetails);
					ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
						List<ProgramTypeTO> programtypes = programtypehandler
									.getProgramType();
					CourseTO applicantCourse = applicantDetails.getCourse();
					CourseTO selectedCourse=applicantDetails.getSelectedCourse();
						if (programtypes!=null) {
							admForm.setEditProgramtypes(programtypes);
							Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
							while (typeitr.hasNext()) {
								ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
								if(typeTO.getProgramTypeId()==selectedCourse.getProgramTypeId()){
									if(typeTO.getPrograms()!=null){
										admForm.setEditprograms(typeTO.getPrograms());
										Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
											while (programitr.hasNext()) {
												ProgramTO programTO = (ProgramTO) programitr
														.next();
												// PROGRAM LEVEL CONFIG SETTINGS
												if(programTO.getId()== selectedCourse.getProgramId()){
													admForm.setEditcourses(programTO.getCourseList());
													if(programTO!=null){ 
														if(programTO.getIsMotherTongue()==true)
														admForm.setDisplayMotherTongue(true);
														if(programTO.getIsDisplayLanguageKnown()==true)
															admForm.setDisplayLanguageKnown(true);
														if(programTO.getIsHeightWeight()==true)
															admForm.setDisplayHeightWeight(true);
														if(programTO.getIsDisplayTrainingCourse()==true)
															admForm.setDisplayTrainingDetails(true);
														if(programTO.getIsAdditionalInfo()==true)
															admForm.setDisplayAdditionalInfo(true);
														if(programTO.getIsExtraDetails()==true)
															admForm.setDisplayExtracurricular(true);
														if(programTO.getIsSecondLanguage()==true)
															admForm.setDisplaySecondLanguage(true);
														if(programTO.getIsFamilyBackground()==true)
															admForm.setDisplayFamilyBackground(true);
														if(programTO.getIsTCDetails()==true)
															admForm.setDisplayTCDetails(true);
														if(programTO.getIsEntranceDetails()==true)
															admForm.setDisplayEntranceDetails(true);
														if(programTO.getIsLateralDetails()==true)
															admForm.setDisplayLateralDetails(true);
														if(programTO.getIsTransferCourse()==true)
															admForm.setDisplayTransferCourse(true);
														if(programTO.getIsExamCenterRequired()==true)
															admForm.setExamCenterRequired(true);
													}
												}
											}
									}	
								}
							}
						}
						
						checkExtradetailsDisplay(admForm);
						checkLateralTransferDisplay(admForm);
						
					if(CountryHandler.getInstance().getCountries()!=null){
						//birthCountry & states
						List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
						if (!birthCountries.isEmpty()) {
							admForm.setCountries(birthCountries);
							Iterator<CountryTO> cntitr= birthCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()!=null && countryTO.getId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) && admForm.getApplicantDetails().getPersonalData()!= null){
									List<StateTO> stateList=countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditStates(stateList);
								}
							}
						}
						
						//permanentCountry & states
						List<CountryTO> permanentCountries = CountryHandler.getInstance().getCountries();
						if (permanentCountries!=null) {
							admForm.setCountries(permanentCountries);
							Iterator<CountryTO> cntitr= permanentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()){
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditPermanentStates(stateList);
								}
							}
						}
						
						//currentCountry & states
						List<CountryTO> currentCountries = CountryHandler.getInstance().getCountries();
						if (currentCountries!=null) {
							admForm.setCountries(currentCountries);
							Iterator<CountryTO> cntitr= currentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCurrentCountryId()){
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditCurrentStates(stateList);
								}
							}
						}
						
						//parentCountry & states
						List<CountryTO> parentCountries = CountryHandler.getInstance().getCountries();
						if (parentCountries!=null) {
							admForm.setCountries(parentCountries);
							Iterator<CountryTO> cntitr= parentCountries.iterator();
							while (cntitr.hasNext()) {
								CountryTO countryTO = (CountryTO) cntitr.next();
								if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getParentCountryId()){
									admForm.setEditParentStates(null);
									List<StateTO> stateList = countryTO.getStateList();
									Collections.sort(stateList);
									admForm.setEditParentStates(stateList);
								}
							}
						}
					}
					
					//guardian states
					
					List<CountryTO> guardianCountries = CountryHandler.getInstance().getCountries();
					if (guardianCountries!=null) {
						admForm.setCountries(guardianCountries);
						Iterator<CountryTO> cntitr= guardianCountries.iterator();
						while (cntitr.hasNext()) {
							CountryTO countryTO = (CountryTO) cntitr.next();
							if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCountryByGuardianAddressCountryId()){
								admForm.setEditGuardianStates(null);
								List<StateTO> stateList = countryTO.getStateList();
								Collections.sort(stateList);
								admForm.setEditGuardianStates(stateList);
							}
						}
					}
					if(applicantDetails.getPersonalData()!=null){
						OrganizationHandler orgHandler= OrganizationHandler.getInstance();
						List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
						if(tos!=null && !tos.isEmpty())
						{
							OrganizationTO orgTO=tos.get(0);
								if(orgTO.getExtracurriculars()!=null)
									applicantDetails.getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
							}
						}
					//Nationality
						AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
						admForm.setNationalities(formhandler.getNationalities());
					// languages	
						LanguageHandler langHandler=LanguageHandler.getHandler();
						admForm.setMothertongues(langHandler.getLanguages());
						admForm.setLanguages(langHandler.getOriginalLanguages());
						
						if(admForm.isDisplayAdditionalInfo())
						{
							OrganizationHandler orgHandler= OrganizationHandler.getInstance();
							List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
							if(tos!=null && !tos.isEmpty())
							{
								OrganizationTO orgTO=tos.get(0);
								admForm.setOrganizationName(orgTO.getOrganizationName());
								admForm.setNeedApproval(orgTO.isNeedApproval());
							}
						}
						
					// res. catg
						admForm.setResidentTypes(formhandler.getResidentTypes());	
						
						ReligionHandler religionhandler = ReligionHandler.getInstance();
						if(religionhandler.getReligion()!=null){
							List<ReligionTO> religionList=religionhandler.getReligion();
							admForm.setReligions(religionList);
							Iterator<ReligionTO> relItr=religionList.iterator();
							while (relItr.hasNext()) {
								ReligionTO relTO = (ReligionTO) relItr.next();
								if(admForm.getApplicantDetails().getPersonalData().getReligionId() !=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())  
										&& StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId() ) && relTO.getReligionId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()) ){
									List<ReligionSectionTO> subreligions=relTO.getSubreligions();
									admForm.setSubReligions(subreligions);
								}
							}
						}
					// caste category
					List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
					admForm.setCasteList(castelist);
					
					// Admitted Through
					List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
					admForm.setAdmittedThroughList(admittedList);
					// subject Group
					List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetailsByCourseAndTermNo(selectedCourse.getId(),applicantDetails.getAppliedYear(),1);
					admForm.setSubGroupList(sujectgroupList);
					String[] subjectgroups=applicantDetails.getSubjectGroupIds();
					if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
						subjectgroups=new String[sujectgroupList.size()];
						applicantDetails.setSubjectGroupIds(subjectgroups);
					}
					
					//incomes
					List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
					admForm.setIncomeList(incomeList);
						
					//currencies
					List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
					admForm.setCurrencyList(currencyList);
					
					UniversityHandler unhandler = UniversityHandler.getInstance();
					List<UniversityTO> universities = unhandler.getUniversity();
					admForm.setUniversities(universities);
					
					OccupationTransactionHandler occhandler = OccupationTransactionHandler
					.getInstance();
					admForm.setOccupations(occhandler.getAllOccupation());
					
					if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
						applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
					
					// set photo to session
					if(applicantDetails.getEditDocuments()!=null){
						Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
						while (docItr.hasNext()) {
							ApplnDocTO docTO = (ApplnDocTO) docItr.next();
							if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
							{
								admForm.setSubmitDate(docTO.getSubmitDate());
							}
							if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
								HttpSession session= request.getSession(false);
								if(session!=null){
									session.setAttribute(PresidanceAdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
					List<CourseTO> preferences=null;
					// preferences
					if(applicantDetails.getPreference()!=null){
						PreferenceTO prefTo= applicantDetails.getPreference();
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						if(prefTo.getFirstPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getFirstPrefCourseId()))
						{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							firstTo.setId(prefTo.getFirstPerfId());
							firstTo.setAdmApplnid(applicantDetails.getId());
							firstTo.setCoursId(prefTo.getFirstPrefCourseId());
							firstTo.setCoursName(prefTo.getFirstprefCourseName());
							firstTo.setProgId(prefTo.getFirstPrefProgramId());
							firstTo.setProgramtypeId(prefTo.getFirstPrefProgramTypeId());
							firstTo.setPrefNo(1);
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(1);
							prefTos.add(firstTo);
						}
						if(prefTo.getSecondPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getSecondPrefCourseId()))
						{
							CandidatePreferenceTO secTo=new CandidatePreferenceTO();
							secTo.setId(prefTo.getSecondPerfId());
							secTo.setAdmApplnid(applicantDetails.getId());
							secTo.setCoursId(prefTo.getSecondPrefCourseId());
							secTo.setProgId(prefTo.getSecondPrefProgramId());
							secTo.setProgramtypeId(prefTo.getSecondPrefProgramTypeId());
							secTo.setPrefNo(2);
							formhandler.populatePreferenceTO(secTo,applicantCourse);
							preferences=secTo.getPrefcourses();
							prefTos.add(secTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(2);
							prefTos.add(firstTo);
						}
						if(prefTo.getThirdPrefCourseId()!=null || !StringUtils.isEmpty(prefTo.getThirdPrefCourseId()))
						{
							CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
							thirdTo.setId(prefTo.getThirdPerfId());
							thirdTo.setPrefNo(3);
							thirdTo.setAdmApplnid(applicantDetails.getId());
							thirdTo.setCoursId(prefTo.getThirdPrefCourseId());
							thirdTo.setProgId(prefTo.getThirdPrefProgramId());
							thirdTo.setProgramtypeId(prefTo.getThirdPrefProgramTypeId());
							formhandler.populatePreferenceTO(thirdTo,applicantCourse);
							preferences=thirdTo.getPrefcourses();
							prefTos.add(thirdTo);
						}else{
							CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
							formhandler.populatePreferenceTO(firstTo,applicantCourse);
							firstTo.setPrefNo(3);
							preferences=firstTo.getPrefcourses();
							prefTos.add(firstTo);
						}
						admForm.setPreferenceList(prefTos);
					}else{
						List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
						CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(firstTo,applicantCourse);
						preferences=firstTo.getPrefcourses();
						firstTo.setPrefNo(1);
						prefTos.add(firstTo);
						CandidatePreferenceTO secTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(secTo,applicantCourse);
						secTo.setPrefNo(2);
						prefTos.add(secTo);
						CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
						formhandler.populatePreferenceTO(thirdTo,applicantCourse);
						thirdTo.setPrefNo(3);
						prefTos.add(thirdTo);
						admForm.setPreferenceList(prefTos);
					}
					ExamGenHandler genHandler = new ExamGenHandler();
					HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
					admForm.setSecondLanguageList(secondLanguage);
					// this condition is for Christ Puc  Admission Edit
					if(admForm.getPucApplicationEdit()==true){
						Iterator<SubjectGroupTO> subList=sujectgroupList.iterator();
						String id[] = new String[sujectgroupList.size()];
						int count=0;
						Set<Integer> subSet=AdmissionFormHelper.getInstance().getSubjectGroupByYearAndCourse(applicantDetails.getAppliedYear(),selectedCourse.getId());
						while (subList.hasNext()) {
							SubjectGroupTO subjectGroupTO = (SubjectGroupTO) subList.next();
							if(subjectGroupTO.getIsCommonSubGrp()!=null && subjectGroupTO.getIsCommonSubGrp() && subSet.contains(subjectGroupTO.getId())){
								id[count]=String.valueOf(subjectGroupTO.getId());
								count=count+1;
							}
						}
						admForm.getApplicantDetails().setSubjectGroupIds(id);
						String interviewDate=AdmissionFormHandler.getInstance().getInterviewDateOfApplicant(applicationNumber,applicationYear);
						if(interviewDate!=null)
						admForm.setPucInterviewDate(interviewDate);
					}
					if(CMSConstants.SHOW_LINK.equals("true")){
						admForm.setIsPresidance(false);
					}else{
						admForm.setIsPresidance(true);
					}
					//for ajax setting put preference lists in session
					HttpSession session= request.getSession(false);
					if(session!=null){
						session.setAttribute(CMSConstants.COURSE_PREFERENCES, preferences);
					}
				}
			}catch(ApplicationException e){
				log.error("error in detailApplicationEdit...",e);
				String msg=super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
				log.error("error in detailApplicationEdit...",e);
					throw e;
				
			}
		log.info("exit detailApplicationEdit..");
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
		
	}
	
	/**
	 * admission form submit
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateApplicationEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered updateApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			//validattion if needed
			ActionMessages errors=admForm.validate(mapping, request);
			validateEditProgramCourse(errors, admForm);
			validateEditPhone(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateEditCommAddress(admForm, errors);
			validateEditCoursePreferences(admForm, errors);
			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);
			
			
			if(admForm.isAdmissionEdit() && (admForm.getApplicantDetails().getAdmissionDate() == null || admForm.getApplicantDetails().getAdmissionDate().isEmpty())){
				errors.add("knowledgepro.admission.date", new ActionError("knowledgepro.admission.date"));
			}
			if(!admForm.isAdmissionEdit() && (admForm.getRecomendedBy()!= null || !admForm.getRecomendedBy().isEmpty())){
				if(admForm.getRecomendedBy().length()>200)
				errors.add("knowledgepro.admission.recommended.By.maxlength", new ActionError("knowledgepro.admission.recommended.By.maxlength"));
			}
			if (admForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// this validation for puc christ college
			if(admForm.getPucApplicationEdit()==true && admForm.getPucInterviewDate()!=null){
				Date startDate = new Date();
				Date endDate = CommonUtil.ConvertStringToDate(admForm.getPucInterviewDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError("knowledgePro.interview.date.puc.admissionForm"));
				}
			}
			
			// if admission validate admission date
			if(admForm.isAdmissionEdit()){
				// validate Admission Date
				if (admForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getAdmissionDate())) {
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getAdmissionDate())){
					boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getAdmissionDate());
					if(!isValid){
						if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
						}
					}
				}
			}
			
			
			
			
			if (admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(admForm.getSubmitDate())){
				boolean	isValid = validatePastDate(admForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			
			
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
				}
			}
			if(!admForm.isApplicationConfirm())
			validateEditEducationDetails(errors, admForm);
			validateEditDocumentSize(admForm, errors,request);
			if(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}

			if(!admForm.isAdmissionEdit()){
				if(admForm.isExamCenterRequired() && admForm.getApplicantDetails().getExamCenterId() == 0){
					if (errors.get("knowledgepro.admission.appln.exam.center.required") != null&& !errors.get("knowledgepro.admission.appln.exam.center.required").hasNext()) {
						errors.add("knowledgepro.admission.appln.exam.center.required",new ActionError("knowledgepro.admission.appln.exam.center.required"));
					}
				}
			}
			
			if(errors==null)
				errors= new ActionMessages();

				if (!errors.isEmpty()) {
					resetHardCopySubmit(admForm.getApplicantDetails());
					saveErrors(request, errors);
					if(!admForm.isAdmissionEdit())
						return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
					else
						return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
					
				}
		AdmApplnTO applicantDetail=admForm.getApplicantDetails();
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		//check for seat allocation exceeded for admitted through or not
		boolean exceeded=false;
		if(admForm.isAdmissionEdit()){
			if(!admForm.isQuotaCheck()){
			if(applicantDetail.getAdmittedThroughId()!=null && !StringUtils.isEmpty(applicantDetail.getAdmittedThroughId()) && StringUtils.isNumeric(applicantDetail.getAdmittedThroughId()) && applicantDetail.getCourse()!=null && applicantDetail.getCourse().getId()!=0){
				exceeded=handler.checkSeatAllocationExceeded(Integer.parseInt(applicantDetail.getAdmittedThroughId()),applicantDetail.getCourse().getId());
			}
			}
		}
		if(exceeded)
		{
			admForm.setQuotaCheck(true);
			resetHardCopySubmit(applicantDetail);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					CMSConstants.ADMISSIONFORM_EDIT_WARN);
			messages.add("messages", message);
			saveMessages(request, messages);
			if(!admForm.isAdmissionEdit())
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
			else
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
		}else{
			if(admForm.isAdmissionEdit() && !admForm.isEligibleCheck()){
				boolean eligible=handler.checkEligibility(applicantDetail);
				
					if(!eligible)
					{
						admForm.setQuotaCheck(true);
						admForm.setEligibleCheck(true);
						resetHardCopySubmit(applicantDetail);
						ActionMessages messages = new ActionMessages();
						ActionMessage message = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ELIGIBILITY_WARN);
						messages.add("messages", message);
						saveErrors(request, messages);
						return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
					}
				
			}
			if(admForm.isAdmissionEdit()){
				boolean admitted=handler.checkAdmitted(applicantDetail);
				if(admitted)
				{
					admForm.setQuotaCheck(true);
					admForm.setEligibleCheck(true);
					resetHardCopySubmit(applicantDetail);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage(
							CMSConstants.ADMISSIONFORM_ADMISSION_DONE);
					messages.add("messages", message);
					saveErrors(request, messages);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
				}
			}
			boolean result=handler.updateCompleteApplication(applicantDetail,admForm,true);

			if(result){
				admForm.setQuotaCheck(false);
				admForm.setEligibleCheck(false);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.ADMISSIONFORM_EDIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		}
		}catch (Exception e){
		log.error("Error in  getApplicantDetails application form edit page...",e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else {
			throw e;
		}
	}
		boolean pucAdmissionEdit=admForm.getPucApplicationEdit();
		boolean pucApplicationDetailEdit=admForm.getPucApplicationDetailEdit();
		cleanupEditData(admForm);
		cleanupEditSessionData(request);
		admForm.setPucApplicationEdit(pucAdmissionEdit);
		admForm.setPucApplicationDetailEdit(pucApplicationDetailEdit);
		log.info("exit updateApplicationEdit..");
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_MODIFY_CONFIRM_PAGE);
		else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDIT_CONFIRM_PAGE);
		
	}
	
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEditProgramCourse(ActionMessages errors,
			AdmissionFormForm admForm) {
		log.info("enter validate program course...");
		if(admForm.getApplicantDetails().getCourse().getProgramTypeId() ==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAMTYPE_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getCourse().getProgramId() ==0)
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}else
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_PROGRAM_REQUIRED, error);
			}
		}
		if(admForm.getApplicantDetails().getCourse().getId() ==0 )
		{
			if(errors==null){
				errors= new ActionMessages();
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}else{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_COURSE_REQUIRED, error);
			}
		}
		log.info("exit validate program course...");
		return errors;
	}
	/**
	 * admission form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateEditCoursePreferences(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditCoursePreferences..");
		List<CandidatePreferenceTO> prefcourses = admForm.getPreferenceList();
		Iterator<CandidatePreferenceTO> itr = prefcourses.iterator();
		CandidatePreferenceTO candidatePreferenceTO;
		while(itr.hasNext()){
			candidatePreferenceTO = itr.next();
			if(candidatePreferenceTO.getPrefNo() == 1 && candidatePreferenceTO.getCoursId().length() == 0) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_FIRSTPREF_REQUIRED, error);
			}
		}
		log.info("exit validateEditCoursePreferences..");
	}
	
	/**
	 * Validate document size
	 * @param admForm
	 * @param errors
	 */
	private void validateEditDocumentSize(AdmissionFormForm admForm,
			ActionMessages errors,HttpServletRequest request) throws Exception {
		log.info("enter validate dcument size...");
		List<ApplnDocTO> uploadlist=admForm.getApplicantDetails().getEditDocuments();
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
		int maXSize=0;
		int maxPhotoSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error in Reading key from properties file....",e);
		}
		if(uploadlist!=null){
			Iterator<ApplnDocTO> uploaditr=uploadlist.iterator();
			while (uploaditr.hasNext()) {
				ApplnDocTO docTo = (ApplnDocTO) uploaditr.next();
				FormFile file=null;
				if(docTo!=null)
					file=docTo.getEditDocument();
				if(file!=null)
				{
					String filename=file.getFileName();
					if(!StringUtils.isEmpty(filename)&& filename.length()>30)
					{
						if(errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_FILENAME, error);
						}
					}
				}
				if(docTo.isPhoto() && file!=null ){
					boolean remove=validateImageHeightWidth(file.getFileData(), file.getFileName(), file.getContentType(), errors,request);
					admForm.setRemove(remove);
					if(maxPhotoSize< file.getFileSize()){
					if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
					}
					}
					if(file.getFileName()!=null && !StringUtils.isEmpty(file.getFileName().trim())){
						String extn="";
						int index = file.getFileName().lastIndexOf(".");
						if(index != -1){
							extn = file.getFileName().substring(index, file.getFileName().length());
						}
						if(!extn.equalsIgnoreCase(".jpg")){
							if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
								errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
							}
						}
					}
					
				}
				else if(file!=null && maXSize< file.getFileSize())
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
						errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
					}
				}
				if(docTo.isNeedToProduceSemWiseMC() && !admForm.isOnlineApply()){
					if(docTo.getSemisterNo()==null || docTo.getSemisterNo().isEmpty()){
						errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Sem No in Document(s)"));
					}
				}
			}
			
		}
	}
	
	/**
	 * validate subject groups
	 * @param admForm
	 * @param errors
	 */
	private void validateSubjectGroups(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateSubjectGroups...");
		if(errors==null)
			errors= new ActionMessages();
			if(admForm.isAdmissionEdit()){
				if((admForm.getApplicantDetails().getAdmittedThroughId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getAdmittedThroughId())))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_ADMITTEDTHROUGH_REQUIRED, error);
					}
				}
				if((admForm.getApplicantDetails().getIsFreeShip()==null))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_FREESHIP_REQUIRED, error);
					}
				}
				
				if((admForm.getApplicantDetails().getIsLIG()==null))
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_LIG_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_LIG_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_LIG_REQUIRED, error);
					}
				}
			}
	}
	/**
	 * clean up session after edit done
	 * @param request
	 */
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session=request.getSession(false);
		if(session==null){
			return;
		}else{
			if(session.getAttribute(PresidanceAdmissionFormAction.PHOTOBYTES)!=null)
				session.removeAttribute(PresidanceAdmissionFormAction.PHOTOBYTES);
			if(session.getAttribute(CMSConstants.KNOWLEDGEPRO_LOGO)!=null){
				session.removeAttribute(CMSConstants.KNOWLEDGEPRO_LOGO);}
			
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
	public ActionForward initApplicationEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		cleanupEditData(admForm);
		admForm.setApplicationYear(null);
		admForm.setOnlineApply(false);
		log.info("exit initApplicationEdit..");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INITEDIT_PAGE);
	}
	
	
	/**
	 * INIT SEMESTER MARK Review PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initSemesterMarkConfirmPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			
			String indexString = request.getParameter(PresidanceAdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					session.setAttribute(PresidanceAdmissionFormAction.COUNTID, indexString);
					index=Integer.parseInt(indexString);
				}else
					session.removeAttribute(PresidanceAdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			admForm.setIsLanguageWiseMarks("false");
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if(qualTO.getSemesterList()!=null){
									int totalObtainWithLan = 0;
									int totalMarkWithLan = 0;
									int totalObtainWithoutLan = 0;
									int totalMarkWithOutLan = 0;
									
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize=semList.size();
									int sizeDiff=CMSConstants.MAX_CANDIDATE_SEMESTERS-listSize;
									if(sizeDiff>0){
										for(int i=listSize+1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
											ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
											to.setSemesterNo(i);
											to.setSemesterName("Semester"+i);
											semList.add(to);
										}
									}
									Iterator<ApplicantMarkDetailsTO> semItr = semList.iterator();
									ApplicantMarkDetailsTO applicantMarkDetailsTO ;
									while (semItr.hasNext()){
										applicantMarkDetailsTO = semItr.next();
										if(applicantMarkDetailsTO.getMarksObtained_languagewise()!= null && !applicantMarkDetailsTO.getMarksObtained_languagewise().trim().isEmpty()){
											totalObtainWithLan = totalObtainWithLan + Integer.parseInt(applicantMarkDetailsTO.getMarksObtained_languagewise());
										}
										if(applicantMarkDetailsTO.getMaxMarks_languagewise()!= null && !applicantMarkDetailsTO.getMaxMarks_languagewise().trim().isEmpty()){
											totalMarkWithLan = totalMarkWithLan +  Integer.parseInt(applicantMarkDetailsTO.getMaxMarks_languagewise());
										}
										if(applicantMarkDetailsTO.getMarksObtained()!= null && !applicantMarkDetailsTO.getMarksObtained().trim().isEmpty()){
											totalObtainWithoutLan = totalObtainWithoutLan + Integer.parseInt(applicantMarkDetailsTO.getMarksObtained());
										}
										if(applicantMarkDetailsTO.getMaxMarks()!= null && !applicantMarkDetailsTO.getMaxMarks().trim().isEmpty()){
											totalMarkWithOutLan = totalMarkWithOutLan + Integer.parseInt(applicantMarkDetailsTO.getMaxMarks());
										}
										
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
									admForm.setTotalobtainedMarkWithLanguage(Integer.toString(totalObtainWithLan));
									admForm.setTotalMarkWithLanguage(Integer.toString(totalMarkWithLan));
									admForm.setTotalobtainedMarkWithoutLan(Integer.toString(totalObtainWithoutLan));
									admForm.setTotalMarkWithoutLan(Integer.toString(totalMarkWithOutLan));
								}
								else{
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									admForm.setTotalobtainedMarkWithLanguage(null);
									admForm.setTotalMarkWithLanguage(null);
									admForm.setTotalobtainedMarkWithoutLan(null);
									admForm.setTotalMarkWithoutLan(null);
									for(int i=1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
										ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
										to.setSemesterNo(i);
										to.setSemesterName("Semester"+i);
										semList.add(to);
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								}
							  admForm.setIsLanguageWiseMarks(String.valueOf(qualTO.isLanguage()));
									
							}
							
				}
			}
			
			if(admForm.getDetailMark()==null)
				admForm.setDetailMark(new CandidateMarkTO());
		} catch (Exception e) {
			log.error("error in initSemesterMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit initSemesterMarkConfirmPage...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
	}
	
	
	/**
	 * SUBMITS SUBMIT MARK REVIEW
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterConfirmMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitSemesterConfirmMark...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(PresidanceAdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm.getSemesterList();
			ActionMessages errors = validateEditSemesterMarks(semesterMarks);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINE_REVIEW_SEM_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						Set<ApplicantMarkDetailsTO> semDetails=new HashSet<ApplicantMarkDetailsTO>();
						semDetails.addAll(semesterMarks);
						qualTO.setSemesterList(semDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitSemesterConfirmMark...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit submitSemesterConfirmMark...");
		return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
	}
	
	/**
	 * validate semester mark in edit page 
	 * @param semesterMarks
	 * @return
	 */
	private ActionMessages validateEditSemesterMarks(
			List<ApplicantMarkDetailsTO> semesterMarks) {
		log.info("enter validateEditSemesterMarks...");
		ActionMessages errors= new ActionMessages();
		if(semesterMarks!=null){
			Iterator<ApplicantMarkDetailsTO> semItr=semesterMarks.iterator();
			while (semItr.hasNext()) {
				ApplicantMarkDetailsTO semMarkTO = (ApplicantMarkDetailsTO) semItr.next();
				
				
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) && !StringUtils.isNumeric(semMarkTO.getMarksObtained())){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
					}
				}
				
				if(semMarkTO!=null && semMarkTO.getMaxMarks()!=null && !StringUtils.isEmpty(semMarkTO.getMaxMarks()) && !StringUtils.isNumeric(semMarkTO.getMaxMarks())){
					if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
					errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
					}
				}
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && (semMarkTO.getMaxMarks()==null || StringUtils.isEmpty(semMarkTO.getMaxMarks())) && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) ){
					if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
					}
				}
				
				if(semMarkTO!=null && semMarkTO.getMarksObtained()!=null && semMarkTO.getMaxMarks()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained()) && StringUtils.isNumeric(semMarkTO.getMarksObtained())
					&& !StringUtils.isEmpty(semMarkTO.getMaxMarks()) && StringUtils.isNumeric(semMarkTO.getMaxMarks())){
					if(Integer.parseInt(semMarkTO.getMarksObtained())>Integer.parseInt(semMarkTO.getMaxMarks())){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
						errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
						}
					}
				}
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) && !StringUtils.isNumeric(semMarkTO.getMarksObtained_languagewise())){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER, error);
						}
					}
					
					if(semMarkTO!=null && semMarkTO.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise()) && !StringUtils.isNumeric(semMarkTO.getMaxMarks_languagewise())){
						if(errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
						errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER, error);
						}
					}
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && (semMarkTO.getMaxMarks_languagewise()==null || StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise())) && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) ){
						if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
						}
					}
					
					if(semMarkTO!=null && semMarkTO.getMarksObtained_languagewise()!=null && semMarkTO.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(semMarkTO.getMarksObtained_languagewise()) && StringUtils.isNumeric(semMarkTO.getMarksObtained_languagewise())
						&& !StringUtils.isEmpty(semMarkTO.getMaxMarks_languagewise()) && StringUtils.isNumeric(semMarkTO.getMaxMarks_languagewise())){
						if(Integer.parseInt(semMarkTO.getMarksObtained_languagewise())>Integer.parseInt(semMarkTO.getMaxMarks_languagewise())){
							if(errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE, error);
							}
						}
					}
			}
		}
		log.info("exit validateEditSemesterMarks...");
		return errors;
	}
	
	/**
	 * INIT DETAILMARK REVIEW PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkConfirmPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initDetailMarkConfirmPage page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			
			String indexString = request.getParameter(PresidanceAdmissionFormAction.COUNTID);
			HttpSession session = request.getSession(false);
			int index=-1;
			if (session != null) {
				if (indexString != null){
					index=Integer.parseInt(indexString);
					session.setAttribute(PresidanceAdmissionFormAction.COUNTID, indexString);
				}else
					session.removeAttribute(PresidanceAdmissionFormAction.COUNTID);
			}
			List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
			if(quals!=null ){
				
				Iterator<EdnQualificationTO> qualItr=quals.iterator();
				while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if (qualTO.getCountId()==index) {
								if (qualTO.getDetailmark() != null)
									admForm.setDetailMark(qualTO
											.getDetailmark());
							}
							
						}
			}
			
			Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			
			if(admForm.getDetailMark()==null){
				CandidateMarkTO markTo=new CandidateMarkTO();
				AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
				admForm.setDetailMark(markTo);
			}
		
		} catch (Exception e) {
			log.error("error initDetailMarkConfirmPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit initDetailMarkConfirmPage...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE);
	}
	
	/**
	 * SUBMITS DETAIL MARK REVIEW
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMarkConfirm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkConfirm page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute(PresidanceAdmissionFormAction.COUNTID);
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = AdmissionFormAction.validateMarks(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ONLINE_REVIEW_DET_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getCountId()==detailMarkIndex){
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitDetailMarkConfirm page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("enter ssubmitDetailMarkConfirm page...");
		return mapping.findForward(CMSConstants.PRC_DETAIL_APPLICANT_SINGLE_PAGE);
	}
	
	/**
	 * INIT SEMESTER MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSemesterMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initSemesterMarkEditPage...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute("editcountID", indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
					
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getSemesterList()!=null){
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									semList.addAll(qualTO.getSemesterList());
									int listSize=semList.size();
									int sizeDiff=CMSConstants.MAX_CANDIDATE_SEMESTERS-listSize;
									if(sizeDiff>0){
										for(int i=listSize+1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
											ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
											to.setSemesterNo(i);
											to.setSemesterName("Semester"+i);
											semList.add(to);
										}
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								}
								else{
									List<ApplicantMarkDetailsTO> semList=new ArrayList<ApplicantMarkDetailsTO>();
									for(int i=1; i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
										ApplicantMarkDetailsTO to= new ApplicantMarkDetailsTO();
										to.setSemesterNo(i);
										to.setSemesterName("Semester"+i);
										semList.add(to);
									}
									Collections.sort(semList);
									admForm.setSemesterList(semList);
								}
							  admForm.setIsLanguageWiseMarks(String.valueOf(qualTO.isLanguage()));
								 
							}
						}
						int totalobtainedMarkWithLanguage=0;
						int totalMarkWithLanguage=0;
						int totalobtainedMarkWithoutLan=0;
						int totalMarkWithoutLan=0;
						for(ApplicantMarkDetailsTO detailsTO:admForm.getSemesterList())
						{
							if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
							{
								totalobtainedMarkWithLanguage+=Integer.parseInt(detailsTO.getMarksObtained_languagewise());
								totalMarkWithLanguage+=Integer.parseInt(detailsTO.getMaxMarks_languagewise());
							}
							totalobtainedMarkWithoutLan+=Integer.parseInt(detailsTO.getMarksObtained());
							totalMarkWithoutLan+=Integer.parseInt(detailsTO.getMaxMarks());	
						}
						
						if(admForm.getIsLanguageWiseMarks().equalsIgnoreCase("true"))
						{
							admForm.setTotalobtainedMarkWithLanguage(""+totalobtainedMarkWithLanguage);
							admForm.setTotalMarkWithLanguage(""+totalMarkWithLanguage);
						}
						admForm.setTotalobtainedMarkWithoutLan(""+totalobtainedMarkWithoutLan);
						admForm.setTotalMarkWithoutLan(""+totalMarkWithoutLan);
					}
				}
				else
					session.removeAttribute("editcountID");
			}
		} catch (Exception e) {
			log.error("error in initSemesterMarkEditPage...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit initSemesterMarkEditPage...");
		return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE);
	}
	
	/**
	 * FORWARDS ADMISSION FORM PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEditApplicationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
	}
	
	/**
	 * FORWARDS APPLICATION EDIT PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardModifyApplicationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
	}
	
	/**
	 * SUBMITS SUBMIT MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitSemesterEditMark(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitSemesterEditMark...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			List<ApplicantMarkDetailsTO> semesterMarks = admForm.getSemesterList();
			ActionMessages errors = validateEditSemesterMarks(semesterMarks);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.ADMISSIONFORM_EDIT_SEMESTER_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
						Set<ApplicantMarkDetailsTO> semDetails=new HashSet<ApplicantMarkDetailsTO>();
						semDetails.addAll(semesterMarks);
						qualTO.setSemesterList(semDetails);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submitSemesterEditMark...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit submitSemesterEditMark...");
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
	}
	
	/**
	 * INT DETAIL MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDetailMarkEditPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init detail mark page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			Map<Integer,String> subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(admForm.getCourseId());
			admForm.setDetailedSubjectsMap(subjectMap);
			String indexString = request.getParameter("editcountID");
			HttpSession session = request.getSession(false);
			if (session != null) {
				if (indexString != null){
					session.setAttribute("editcountID", indexString);
					int index=Integer.parseInt(indexString);
					List<EdnQualificationTO> quals=admForm.getApplicantDetails().getEdnQualificationList();
					if(quals!=null ){
						Iterator<EdnQualificationTO> qualItr=quals.iterator();
						while (qualItr.hasNext()) {
							EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
							if(qualTO.getId()==index){
								if(qualTO.getDetailmark()!=null)
								admForm.setDetailMark(qualTO.getDetailmark());
								else{
									CandidateMarkTO markTo=new CandidateMarkTO();
									AdmissionFormHelper.getInstance().setDetailedSubjectsFormMap(subjectMap,markTo);
									admForm.setDetailMark(markTo);
								}
							}
						}
					}
				}
				else
					session.removeAttribute("editcountID");
			}
		} catch (Exception e) {
			log.error("error in init detail mark page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit init detail mark page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE);
	}
	
	/**
	 * SUBMITS DETAIL MARK EDIT
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitDetailMarkEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitDetailMarkEdit page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		HttpSession session=request.getSession(false);
		try {
			String indexString = null;
			if (session != null)
				indexString = (String) session.getAttribute("editcountID");
			int detailMarkIndex = -1;
			if (indexString != null)
				detailMarkIndex = Integer.parseInt(indexString);
			CandidateMarkTO detailmark = admForm.getDetailMark();
			ActionMessages errors = AdmissionFormAction.validateMarks(detailmark,new ActionMessages());
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDIT_DETAIL_MARK_PAGE);
			}
			List<EdnQualificationTO> qualifications = admForm.getApplicantDetails().getEdnQualificationList();
			if (qualifications != null) {
				Iterator<EdnQualificationTO> qualItr=qualifications.iterator();
				while (qualItr.hasNext()) {
					EdnQualificationTO qualTO = (EdnQualificationTO) qualItr.next();
					if(qualTO.getId()==detailMarkIndex){
						qualTO.setDetailmark(detailmark);
					}
				}
			}
		} catch (Exception e) {
			log.error("error in submit detail mark page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("enter submitDetailMarkEdit page...");
		if(!admForm.isAdmissionEdit())
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILMODIFY_PAGE);
		else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_DETAILEDIT_PAGE);
	}
	
	// Offline Code
	public ActionForward initApplicationForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init application detail page...");
		HttpSession session= request.getSession(true);
		try {
				cleanupSessionData(session);
				AdmissionFormAction.cleanupFormFromSession(session);
				cleanupEditSessionData(request);
		} 
		catch (Exception e) {
			log.error("error in init studentpage...",e);
				throw e;
			
		}
		
		log.info("exit init application detail page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_FIRST_PAGE);
	}
	
	/**
	 * initialize application form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward loadInitApplicationForm(ActionMapping mapping,
												ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		log.info("enter loadInitApplicationForm...");
		AdmissionFormForm admissionFormForm=(AdmissionFormForm)form;
		ActionMessages errors=admissionFormForm.validate(mapping, request);
		if (admissionFormForm.getApplicationNumber() != null && admissionFormForm.getApplicationYear() == null) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONYEAR_REQUIRED));
			}
		}
		if (admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber()) && !StringUtils.isNumeric(admissionFormForm.getApplicationNumber())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_INVALID));
			}
		}
		if(admissionFormForm.getApplicationNumber() != null && !StringUtils.isEmpty(admissionFormForm.getApplicationNumber().trim()) && StringUtils.isNumeric(admissionFormForm.getApplicationNumber()) && admissionFormForm.getYear() != null && !StringUtils.isEmpty(admissionFormForm.getYear().trim())){
			AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
			
			boolean applnNoInrange = handler.checkValidOfflineNumber(Integer.parseInt(admissionFormForm.getApplicationNumber()), Integer.parseInt(admissionFormForm.getYear()));
			if (!applnNoInrange) {
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINE_INVALID));
				}
				
			}
		}
		if(errors!=null && !errors.isEmpty()){
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_FIRST_PAGE);
		} 
		
		if(admissionFormForm.getApplicationNumber() == null || admissionFormForm.getApplicationNumber().length() == 0) {
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_COURSE_SELCTION_PAGE);
		}
		
		try {
			boolean result = OfflineDetailsHandler.getInstance().getApplicationOfflineDetails(admissionFormForm);
			if(result) {
				setProgramAndCourseMap(admissionFormForm,request);
				request.setAttribute("operation", "load");
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_COURSE_SELCTION_PAGE);
			} else {
				if(!admissionFormForm.isCheckOfflineAppNo()) {
					errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_OFFLINEDATA_NOTEXIST));
		    		saveErrors(request,errors);
		    		admissionFormForm.setCheckOfflineAppNo(true);
		    		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_FIRST_PAGE);
				}
			}
			
		} catch (DuplicateException e1) {
	    	errors.add("error", new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_FIRST_PAGE);
	    }catch(ApplicationException e){
			log.error("error in loadInitApplicationForm...",e);
			String msg=super.handleApplicationException(e);
			admissionFormForm.setErrorMessage(msg);
			admissionFormForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init studentpage...",e);
				throw e;
			
		}
	    log.info("exit loadInitApplicationForm...");
	    return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_COURSE_SELCTION_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOfflinePrerequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initOfflinePrerequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		
		try{
			 ActionErrors errors = admForm.validate(mapping, request);
			validateProgramCourse(errors, admForm,false);
			if (errors!=null && !errors.isEmpty()) {
				saveErrors(request, errors);
				
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_COURSE_SELCTION_PAGE);
			}
			String courseName=null;
			String progName=null;
			String progTypeName=null;
			List<CourseTO> courselist=null;
			CourseHandler crsHandler=CourseHandler.getInstance();
			if(admForm.getCourseId()!=null && !StringUtils.isEmpty(admForm.getCourseId()) && StringUtils.isNumeric(admForm.getCourseId())){
				courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
			}
			if(courselist!=null && !courselist.isEmpty()){
				CourseTO to= courselist.get(0);
				if(to!=null){
					courseName=to.getName();
					if(to.getProgramTo()!=null){
						progName=to.getProgramTo().getName();
						
					}
					if(to.getProgramTo()!=null && to.getProgramTo().getProgramTypeTo()!=null ){
						progTypeName=to.getProgramTo().getProgramTypeTo().getProgramTypeName();
					}
				}
			}
			admForm.setCourseName(courseName);
			admForm.setProgramName(progName);
			admForm.setProgTypeName(progTypeName);
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		// GET ALL PREREQUISTES FOR COURSE
		List<CoursePrerequisiteTO> prerequisites=handler.getCoursePrerequisites(Integer.parseInt(admForm.getCourseId()));
		if (prerequisites!=null && !prerequisites.isEmpty()) {
			admForm.setCoursePrerequisites(prerequisites);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE);
		}else{
			
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
		}
		}catch(Exception e){
			log.error("error initOfflinePrerequisiteApply...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
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
	public ActionForward submitOfflinePreRequisiteApply(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitOfflinePreRequisiteApply...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
		List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();
		//Validate prerequisite and back if not
		ActionMessages messages= new ActionMessages();
		// VALIDATE PREREQUISTES REQUIREDS
		messages= validatePrerequisiteRequireds(prerequisites,messages);
		if(messages!=null && messages.isEmpty() )
			// VALIDATE PREREQUISTES ELIGIBILITY
		messages= validatePrerequisite(admForm,messages);
		
		if(messages!=null && !messages.isEmpty() )
		{
			saveErrors(request, messages);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_OFFLINE_PREREQUISITE_PAGE);
		}
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		HttpSession session= request.getSession(false);
		if(session==null)
			return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
		// SAVE THE PREREQUISITE DETAILS
		handler.savePrerequisitesToSession(session,admForm.getEligPrerequisites(),admForm.getUserId());
		}catch(ApplicationException e){
			log.error("error in submitOfflinePreRequisiteApply...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submitOfflinePreRequisiteApply...",e);
				throw e;
			
		}
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
	}
	
	/**
	 * saves Application details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitOfflineApplicationFormInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit application detail page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		setUserId(request, admForm);
		//presently , it is true. it will be false after login implementation
		HttpSession session= request.getSession(false);
		AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
		
		ActionMessages errors=admForm.validate(mapping, request);
		if(!admForm.getIsPresidance()){
		validateProgramCourse(errors,admForm,false);
		
		if (admForm.getApplicationDate()!=null && !StringUtils.isEmpty(admForm.getApplicationDate())) {
			if(CommonUtil.isValidDate(admForm.getApplicationDate())){
			boolean	isValid = validatefutureDate(admForm.getApplicationDate());
			if(!isValid){
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
				}
			}
			}else{
				if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
				}
			}
		}
		if(admForm.getApplicationAmount()!=null && !StringUtils.isEmpty(admForm.getApplicationAmount().trim())){
				if(!CommonUtil.isValidDecimal(admForm.getApplicationAmount().trim())){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONAMNT_INVALID));
					}
				}
		}

		if(errors.isEmpty() ){
			int year; 
			if(admForm.getApplicationYear()!= null &&  !admForm.getApplicationYear().trim().isEmpty()){
				year = Integer.parseInt(admForm.getApplicationYear()); 	
			}
			else
			{
				final Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());
				year=cal.get(cal.YEAR);	
			}
			if(admForm.getChallanNo()!=null && !StringUtils.isEmpty(admForm.getChallanNo().trim()) 
			&& admForm.getJournalNo()!=null && !StringUtils.isEmpty(admForm.getJournalNo().trim())
			&& admForm.getApplicationDate()!=null && !StringUtils.isEmpty(admForm.getApplicationDate().trim())
			&& CommonUtil.isValidDate(admForm.getApplicationDate())){
				
				boolean duplicate = handler.checkPaymentDetails(admForm.getChallanNo(),admForm.getJournalNo(),admForm.getApplicationDate(),year);
				boolean preRequisiteduplicate=false;
				if(admForm.getCoursePrerequisites()!= null && admForm.getCoursePrerequisites().size() > 0){
					List<CoursePrerequisiteTO> prerequisites=admForm.getCoursePrerequisites();				
					Iterator<CoursePrerequisiteTO> reqItr2=prerequisites.iterator();
					while (reqItr2.hasNext()) {
						CoursePrerequisiteTO reqTO = (CoursePrerequisiteTO) reqItr2.next();
						//AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
						boolean duplicateroll=handler.checkDuplicatePrerequisite(reqTO.getExamYear(),reqTO.getRollNo());
						if(duplicateroll){
							preRequisiteduplicate=true;
							break;
						}
					}
				}
				if(preRequisiteduplicate){
					if(duplicate){
						if(errors==null){
							errors= new ActionMessages();
						}
						if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
						}	
					}
				}
				else{
					if(duplicate){
						if(errors==null){
							errors= new ActionMessages();
						}
						if (errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE, new ActionError(CMSConstants.ADMISSIONFORM_PAYMENT_DUPLICATE));
						}	
					}
				}
			}
		}
		}
		
		if(!errors.isEmpty() )
		{
			saveErrors(request, errors);
			setProgramAndCourseMap(admForm,request);
			request.setAttribute("operation", "load");
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
		}
		// validation done
		try {
			handler.setWorkExpNeeded(admForm);
			handler.saveApplicationDetailsToSession(admForm,session);
			ExamGenHandler genHandler = new ExamGenHandler();
			HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
			admForm.setSecondLanguageList(secondLanguage);
		} 
		catch (Exception e) {
			log.error("error in submit application detail...",e);
				throw e;
		}
		log.info("exit submit application detail page...");
		
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_STUDENT_PAGE);
	}
	
	/**
	 * initialize admission form
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmissionForm(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init student page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
			admForm.setCasteList(castelist);
			//set caste show or not
			admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
			ProgramTypeHandler programtypehandler = ProgramTypeHandler
					.getInstance();
			List<ProgramTypeTO> programtypes = programtypehandler
					.getProgramType();
			admForm.setProgramtypeList(programtypes);
			
			CountryHandler countryhandler = CountryHandler.getInstance();
			admForm.setCountries(countryhandler.getCountries());

				CourseHandler crsHandler= CourseHandler.getInstance();
				List<CourseTO> courselist=crsHandler.getCourse(Integer.parseInt(admForm.getCourseId()));
				String courseName="";
				//SET ALL PROGRAM LEVEL CONFIGURATIONS
				if(courselist!=null && ! courselist.isEmpty()){
					CourseTO to=courselist.get(0);
					if(to!=null){
						ProgramTO progTo=to.getProgramTo();
						if(progTo!=null){ 
						
							admForm.setDisplayMotherTongue(progTo.getIsMotherTongue());
							
							admForm.setDisplayLanguageKnown(progTo.getIsDisplayLanguageKnown());
							admForm.setDisplayHeightWeight(progTo.getIsHeightWeight());
							admForm.setDisplayTrainingDetails(progTo.getIsDisplayTrainingCourse());
							admForm.setDisplayAdditionalInfo(progTo.getIsAdditionalInfo());
							admForm.setDisplayExtracurricular(progTo.getIsExtraDetails());
							admForm.setDisplaySecondLanguage(progTo.getIsSecondLanguage());
							admForm.setDisplayFamilyBackground(progTo.getIsFamilyBackground());
							admForm.setDisplayLateralDetails(progTo.getIsLateralDetails());
							admForm.setDisplayTransferCourse(progTo.getIsTransferCourse());
							admForm.setDisplayEntranceDetails(progTo.getIsEntranceDetails());
							admForm.setDisplayTCDetails(progTo.getIsTCDetails());
							admForm.setProgramId(String.valueOf(progTo.getId()));
							if(progTo.getProgramTypeTo()!=null){
								admForm.setAgeFromLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeFrom()));
								admForm.setAgeToLimit(Integer.parseInt(progTo.getProgramTypeTo().getAgeTo()));
							}
						}
						courseName=to.getName();
						if(to.getIsDetailMarkPrint()!=null && "Yes".equalsIgnoreCase(to.getIsDetailMarkPrint()))
								admForm.setDetailMarksPrint(true);
						else
							admForm.setDetailMarksPrint(false);
					}
				}
				admForm.setCourseName(courseName);

			// EXTRA DETAILS BLOCK DISPLAY CHECK
			checkExtradetailsDisplay(admForm);
			// LATERAL AND TRANSFER COURSE LINK DISPLAY CHECK
			checkLateralTransferDisplay(admForm);
			//PREPARE ALL REQUIRED DATAS TO SELECT
			AdmissionFormHandler formhandler = AdmissionFormHandler
					.getInstance();
			formhandler.populatePreferenceList(admForm);
			formhandler.setDefaultPreferences(admForm);
			//for ajax setting put preference lists in session
			HttpSession session= request.getSession(false);
			if(session!=null){
				session.setAttribute(CMSConstants.COURSE_PREFERENCES, admForm.getPrefcourses());
			}
//			formhandler.setWorkExpNeeded(admForm);
			admForm.setResidentTypes(formhandler.getResidentTypes());
			admForm.setNationalities(formhandler.getNationalities());
			ReligionHandler religionhandler = ReligionHandler.getInstance();
			admForm.setReligions(religionhandler.getReligion());
			LanguageHandler langHandler=LanguageHandler.getHandler();
			admForm.setMothertongues(langHandler.getLanguages());
			admForm.setLanguages(langHandler.getOriginalLanguages());
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			
			
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				admForm.setNeedApproval(orgTO.isNeedApproval());
				if(admForm.isDisplayAdditionalInfo())
				{
					admForm.setOrganizationName(orgTO.getOrganizationName());
					
				}
				if(admForm.isDisplayExtracurricular()){
					if(orgTO.getExtracurriculars()!=null)
					admForm.setExtracurriculars(orgTO.getExtracurriculars());
				}
		}
			
			
		} catch(ApplicationException e){
			log.error("error in init student page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init studentpage...",e);
				throw e;
			
		}
		log.info("exit init student page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_STUDENTPAGE);
	}
	
	
	/**
	 * saves student details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormStudentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered submit student info..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		HttpSession session= request.getSession(false);
		AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
		try {
			//validations
			validatePhone(admForm, errors);
			AdmissionFormAction.validateCommAddress(admForm, errors);
			validateOtherOptions(admForm, errors);
			validatePassportIfNRI(admForm, errors);
			
			validateWorkExperience(admForm.getFirstExp(), errors);
			validateWorkExperience(admForm.getSecExp(), errors);
			validateWorkExperience(admForm.getThirdExp(), errors);
			//email comparision
			if(admForm.getEmailId()!=null && !StringUtils.isEmpty(admForm.getEmailId())){
				if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
						if(!admForm.getEmailId().equals(admForm.getConfirmEmailId())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}
			
			if(admForm.getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getTrainingDuration()) && !StringUtils.isNumeric(admForm.getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getHeight()!=null && !StringUtils.isEmpty(admForm.getHeight()) && !StringUtils.isNumeric(admForm.getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getWeight()!=null && !StringUtils.isEmpty(admForm.getWeight()) && !CommonUtil.isValidDecimal(admForm.getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			if(admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth())&& !CommonUtil.isValidDate(admForm.getDateOfBirth())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			
			
			if (admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth())&& CommonUtil.isValidDate(admForm.getDateOfBirth())) {
				
				final boolean	isValid = validatefutureDate(admForm.getDateOfBirth());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				
				}
			}
			if(admForm.isOnlineApply() && admForm.getAgeToLimit()!=0 && admForm.getDateOfBirth()!=null && !StringUtils.isEmpty(admForm.getDateOfBirth()) && CommonUtil.isValidDate(admForm.getDateOfBirth())){
					boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getDateOfBirth());
					if(!valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
						}
					}
			}
			
			//passport validation
			if(admForm.getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getPassportValidity())){
				
				
				if(CommonUtil.isValidDate(admForm.getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getPermitDate()!=null && !StringUtils.isEmpty(admForm.getPermitDate())&& !CommonUtil.isValidDate(admForm.getPermitDate())){
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			if(!admForm.isOnlineApply() && (admForm.getApplicationNumber()==null || StringUtils.isEmpty(admForm.getApplicationNumber()))){
				if (errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED, new ActionError(CMSConstants.ADMISSIONFORM_APPPLNNO_REQUIRED));
				}
			}
			
			
			// if errors
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_STUDENTPAGE);
			}
			
			AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
			PersonalDataTO studentpersonaldata = helper
					.getStudentPersonaldataTofromForm(admForm);
			if (session != null) {
				int appliedyear=0;
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					AdmAppln applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null){
						appliedyear=applicationdata.getAppliedYear();
					}
				}
				admForm.setApplicationYear(String.valueOf(appliedyear));
				boolean appNoConfigured=handler.checkApplicationNoConfigured(appliedyear, admForm.getCourseId());
				if(!appNoConfigured){
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTCONFIGURED,error);
					saveErrors(request, message);
					admForm.setApplicationError(true);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_STUDENTPAGE);
				}
				if (!admForm.isOnlineApply()) {
					boolean applnNoInrange = handler.checkApplicationNoInRange(
							admForm.getApplicationNumber(), admForm
									.isOnlineApply(), appliedyear, admForm
									.getCourseId());
					if (!applnNoInrange) {
						ActionMessages message = new ActionMessages();
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE);
						message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTINRANGE,error);
						saveErrors(request, message);
						admForm.setApplicationError(true);
						return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_STUDENTPAGE);
					}
				}
				boolean result = handler.saveStudentPersonaldataToSession(
						studentpersonaldata, admForm, session);
				
				if(!result){
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE, error);
					saveErrors(request, message);
					admForm.setApplicationError(true);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_STUDENTPAGE);
				}
			} else {
				cleanupSessionData(session);
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}
		}catch (ApplicationException e) {
			
			log.error("error submit student page...",e);
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			
			log.error("error submit student page...",e);
				throw e;
		}
		log.info("exit submit student info..");

		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_EDUCATION_PAGE);
	}
	
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validatePhone(AdmissionFormForm admForm, ActionMessages errors) {
		log.info("enter validatePhone..");
		if(errors==null)
			errors= new ActionMessages();
		
				if(admForm.getPhone1()!=null && !StringUtils.isEmpty(admForm.getPhone1()) && !StringUtils.isNumeric(admForm.getPhone1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getPhone2()!=null && !StringUtils.isEmpty(admForm.getPhone2()) && !StringUtils.isNumeric(admForm.getPhone2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getPhone3()!=null && !StringUtils.isEmpty(admForm.getPhone3()) && !StringUtils.isNumeric(admForm.getPhone3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				
				if(admForm.getMobile2()!=null && !StringUtils.isEmpty(admForm.getMobile2()) && admForm.getMobile2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getMobile2()!=null && !StringUtils.isEmpty(admForm.getMobile2()) && !StringUtils.isNumeric(admForm.getMobile2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				log.info("exit validatePhone..");
	}
	
	/**
	 * application form validation
	 * @param admForm
	 * @param errors
	 */
	private void validateOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception{
		log.info("enter validateOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}
		
		if((admForm.getReligionId()==null || StringUtils.isEmpty(admForm.getReligionId())|| (admForm.getReligionId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getOtherReligion()==null ||StringUtils.isEmpty(admForm.getOtherReligion()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		if(admForm.getReligionId()!=null && !StringUtils.isEmpty(admForm.getReligionId()) && StringUtils.isNumeric(admForm.getReligionId())){
			ISubReligionTransaction relTxn=new SubReligionTransactionImpl();
			//if master entry exists
			if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getReligionId()))){
				if((admForm.getSubReligion()==null || StringUtils.isEmpty(admForm.getSubReligion())|| (admForm.getSubReligion().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getOtherSubReligion()==null ||StringUtils.isEmpty(admForm.getOtherSubReligion())) )
					if(admForm.isCasteDisplay()){
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
					}
				}
              }
			}
		}
		
		
		if (admForm.getPermAddr().getPinCode() != null
				&& !StringUtils.isEmpty(admForm.getPermAddr().getPinCode())&& !StringUtils.isNumeric(admForm.getPermAddr().getPinCode())){
			if (errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PERM_ZIP_INVALID,error);
			}
		}
		log.info("exit validateOtherOptions..");
	}
	
	/**
	 * saves student EDUCATION details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEducationPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init education info..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			admForm.setTcDate(null);
			admForm.setTcNo(null);
			admForm.setMarkcardDate(null);
			admForm.setMarkcardNo(null);
			admForm.setEntranceId(null);
			admForm.setEntranceMarkObtained(null);
			admForm.setEntranceTotalMark(null);
			admForm.setEntranceMonthPass(null);
			admForm.setEntranceYearPass(null);
			admForm.setEntranceRollNo(null);
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			List<EdnQualificationTO> qualifications = handler
					.getEdnQualifications(admForm);
			admForm.setQualifications(qualifications);
			// Admitted Through
			List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
			admForm.setAdmittedThroughList(admittedList);
			UniversityHandler unhandler = UniversityHandler.getInstance();
			List<UniversityTO> universities = unhandler.getUniversity();
			admForm.setUniversities(universities);
			
			List<StateTO> ednstates=StateHandler.getInstance().getNativeStates(CMSConstants.KP_DEFAULT_COUNTRY_ID);
			admForm.setEdnStates(ednstates);
			
			List<EntrancedetailsTO> entrnaceList=EntranceDetailsHandler.getInstance().getEntranceDeatilsByProgram(Integer.parseInt(admForm.getProgramId()));
			admForm.setEntranceList(entrnaceList);
			
		}catch(ApplicationException e){
			log.error("error in init education page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in init education page...",e);
			throw e;
		}
		log.info("exit init education info..");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDUCATION_PAGE);
	}
	
	/**
	 * saves education details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitEducationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit education page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		//validation if needed
		ActionMessages errors=admForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			validateEducationDetails(errors, admForm);
			if(admForm.isDisplayTCDetails())
				validateTCdetails(errors, admForm);
			if(admForm.isDisplayEntranceDetails())
				validateEntranceDetails(errors, admForm);
			
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDUCATION_PAGE);
			}
			// save to session education Data
			HttpSession session = request.getSession(false);
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			if (session == null){
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_EDUCATION_PAGE);
			}
			handler.saveEducationDetailsToSession(admForm, session,true);
		} 
		catch (Exception e) {
			log.error("error in submit education page...",e);
				throw e;
		}
		log.info("exit submit education page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_ATTACHMENT_PAGE);
	}
	/**
	 * ENTRANCE DETAILS VALIDATION
	 * @param errors
	 * @param admForm
	 */
	private void validateEntranceDetails(ActionMessages errors,
			AdmissionFormForm admForm) {
		if(admForm.getEntranceTotalMark()!=null && !StringUtils.isEmpty(admForm.getEntranceTotalMark().trim()) && !StringUtils.isNumeric(admForm.getEntranceTotalMark().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_TOTALMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getEntranceMarkObtained()!=null && !StringUtils.isEmpty(admForm.getEntranceMarkObtained().trim()) && !StringUtils.isNumeric(admForm.getEntranceMarkObtained().trim()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER);
				errors.add(CMSConstants.ADMISSIONFORM_ENTRANCE_OBTAINMARK_NOTINTEGER, error);
				}
		}
		
		if(admForm.getEntranceMarkObtained()!=null && !StringUtils.isEmpty(admForm.getEntranceMarkObtained().trim()) && StringUtils.isNumeric(admForm.getEntranceMarkObtained().trim()) 
				&& admForm.getEntranceTotalMark()!=null && !StringUtils.isEmpty(admForm.getEntranceTotalMark().trim()) && StringUtils.isNumeric(admForm.getEntranceTotalMark().trim())
				&& Integer.parseInt(admForm.getEntranceMarkObtained())> Integer.parseInt(admForm.getEntranceTotalMark()))
		{
			if(errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE).hasNext()) {
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE);
				errors.add(CMSConstants.ADMISSIONFORM_ENT_OBTAINMARK_LARGE, error);
				}
		}
		
		//check date of birth cross and present date cross
		if((admForm.getEntranceYearPass()!=null && !StringUtils.isEmpty(admForm.getEntranceYearPass()) && StringUtils.isNumeric(admForm.getEntranceYearPass())) 
				&& admForm.getEntranceMonthPass()!=null && !StringUtils.isEmpty(admForm.getEntranceMonthPass()) && StringUtils.isNumeric(admForm.getEntranceMonthPass())){
			if(CommonUtil.isValidDate(admForm.getDateOfBirth())){
				boolean futurethanBirth=isPassYearGreaterThanBirth(Integer.parseInt(admForm.getEntranceYearPass()),Integer.parseInt(admForm.getEntranceMonthPass()),admForm.getDateOfBirth());
				if(!futurethanBirth){
					if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_BIRTHYR_FUTURE,error);
					}
				}
			}
			Calendar cal= Calendar.getInstance();
			Date today= cal.getTime();
			boolean futurethantoday=isPassYearGreaterThanToday(Integer.parseInt(admForm.getEntranceYearPass()),Integer.parseInt(admForm.getEntranceMonthPass()),today);
			if(futurethantoday){
				if (errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE);
					errors.add(CMSConstants.ADMISSIONFORM_ENT_PASSYEAR_TODAY_FUTURE,error);
				}
			}
		}
	}
	
	/**
	 * TC DETAILS VALIDATION
	 * @param errors
	 * @param admForm
	 */
	private void validateTCdetails(ActionMessages errors,
			AdmissionFormForm admForm) {
		if(admForm.getTcDate()!=null && !StringUtils.isEmpty(admForm.getTcDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getTcDate().trim()) ){
			if(!validatefutureDate(admForm.getTcDate().trim())){
				if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE);
					errors.add(CMSConstants.ADMISSIONFORM_TCDATE_FUTURE,error);
				}
			}
			
			}else{
				if(errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TCDATE_INVALID).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TCDATE_INVALID);
					errors.add(CMSConstants.ADMISSIONFORM_TCDATE_INVALID,error);
				}
			}
			
		}
		
		if(admForm.getMarkcardDate()!=null && !StringUtils.isEmpty(admForm.getMarkcardDate().trim()))
		{
			if(CommonUtil.isValidDate(admForm.getMarkcardDate().trim()) ){
				if(!validatefutureDate(admForm.getMarkcardDate())){
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_FUTURE,error);
					}
				}
			
				}else{
					if(errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MARKCARDDATE_INVALID,error);
					}
				}
		}
	}
	/**
	 * validate programtype,course and program
	 * @param errors
	 * @param admForm
	 * @return
	 */
	private ActionMessages validateEducationDetails(ActionMessages errors,
			AdmissionFormForm admForm) {
		log.info("enter validate education...");
		List<EdnQualificationTO> qualifications=admForm.getQualifications();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> qualificationTO= qualifications.iterator();
			while (qualificationTO.hasNext()) {
				EdnQualificationTO qualfTO = (EdnQualificationTO) qualificationTO
						.next();
				if((qualfTO.getUniversityId()==null ||(qualfTO.getUniversityId().length()==0 ) || qualfTO.getUniversityId().equalsIgnoreCase("Other")) && (qualfTO.getUniversityOthers()==null ||(qualfTO.getUniversityOthers().trim().length()==0 )))
				{
						if(errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_UNIVERSITY_REQUIRED,error);
						}
				}
				if((qualfTO.getInstitutionId()==null ||(qualfTO.getInstitutionId().length()==0 )||(qualfTO.getInstitutionId().equalsIgnoreCase("Other")) ) && (qualfTO.getOtherInstitute()==null ||(qualfTO.getOtherInstitute().trim().length()==0 )))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_INSTITUTE_REQUIRED,error);
						}
				}
				if(/*qualfTO.isExamRequired()*/qualfTO.isExamConfigured() && (qualfTO.getSelectedExamId()==null || StringUtils.isEmpty(qualfTO.getSelectedExamId())))
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_EDN_EXAM_REQUIRED,error);
						}
				}
				if(qualfTO.getPercentage()==null || StringUtils.isEmpty(qualfTO.getPercentage()))
				{
					errors.add(CMSConstants.ERROR,new ActionMessage("errors.required","Percentage"));
				}else{
					if(!CommonUtil.isValidDecimal(qualfTO.getPercentage())){
						errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.totalmarks.numeric"));
					}else{
						double d=Double.parseDouble(qualfTO.getPercentage());
						if(d>100){
							errors.add(CMSConstants.ERROR,new ActionMessage("knowledgepro.admission.percentage.greater"));
						}
					}
				}
				if(qualfTO.getYearPassing()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_REQUIRED,error);
						}
				}else{
					boolean valid=CommonUtil.isFutureNotCurrentYear(qualfTO.getYearPassing());
					if(valid){
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE);
							errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_FUTURE,error);
						}
					}
					
				}
					if(qualfTO.getMonthPassing()==0)
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_PASSMONTH_REQUIRED,error);
						}
					}
				if(qualfTO.getNoOfAttempts()==0)
				{
						if (errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED);
							errors.add(CMSConstants.ADMISSIONFORM_NOATTEMPT_REQUIRED,error);
						}
				}
				if(qualfTO.isConsolidated()){
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) && !CommonUtil.isValidDecimal(qualfTO.getTotalMarks())){
						if (errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_TOTALMARK_NOTINTEGER,error);
						}
					}
					if(qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) && !CommonUtil.isValidDecimal(qualfTO.getMarksObtained())){
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_NOTINTEGER,error);
						}
					}
					if(qualfTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualfTO.getMarksObtained()) 
							&& CommonUtil.isValidDecimal(qualfTO.getMarksObtained()) &&
							qualfTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualfTO.getTotalMarks()) 
							&& CommonUtil.isValidDecimal(qualfTO.getTotalMarks())
							&& Double.parseDouble(qualfTO.getTotalMarks())< Double.parseDouble(qualfTO.getMarksObtained()))
					{
						if (errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE).hasNext()) {
							ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE);
							errors.add(CMSConstants.ADMISSIONFORM_OBTAINMARK_LARGE,error);
						}
					}
					
				}else{
					if(qualfTO.getDetailmark()!=null && !qualfTO.getDetailmark().isPopulated())
					{
						if(!qualfTO.isSemesterWise()){
							if (errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_DETAILMARK_REQUIRED,error);
							}
						}else if(qualfTO.isSemesterWise()){
							if (errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED);
								errors.add(CMSConstants.ADMISSIONFORM_SEMESTERMARK_REQUIRED,error);
							}
						}
					}
				}
				if(qualfTO.getYearPassing()!=0 && qualfTO.getMonthPassing()!=0){
					if(CommonUtil.isValidDate(admForm.getDateOfBirth())){
						boolean futurethanBirth=isPassYearGreaterThanBirth(qualfTO.getYearPassing(),qualfTO.getMonthPassing(),admForm.getDateOfBirth());
						if(!futurethanBirth){
							if (errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE).hasNext()) {
								ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE);
								errors.add(CMSConstants.ADMISSIONFORM_PASSYEAR_BIRTHYR_FUTURE,error);
							}
						}
					}
				}
			}
			log.info("exit validate education...");
		}
		return errors;
	}
	
	/**
	 * INITIALIZES ATTACHMENT PAGE
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttachMentPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init attachment page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			int appliedyear=0;
			HttpSession session = request.getSession(false);
			if (session == null) {
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}
			
			AdmAppln applicationdata=null;
			if (session != null) {
				
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null)
						appliedyear=applicationdata.getAppliedYear();
				}
			}
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			admForm.setUploadDocs(handler.getRequiredDocList(admForm
					.getCourseId(),appliedyear));
		} catch (Exception e) {
			log.error("error in init attachment page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("enter exit attachment page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ATTACHMENT_PAGE);
	}
	
	/**
	 * application form PASSPORT DATA validation
	 * @param admForm
	 * @param errors
	 */
	private  void validatePassportIfNRI(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validatePassportIfNRI..");
		if(errors==null)
			errors= new ActionMessages();
		if(admForm.getResidentCategory()!=null && !StringUtils.isEmpty(admForm.getResidentCategory())){
			if(admForm.getResidentCategory()!=null && StringUtils.isNumeric(admForm.getResidentCategory())&& !CMSConstants.INDIAN_RESIDENT_LIST.contains(Integer.valueOf((Integer.parseInt(admForm.getResidentCategory()))))){
				if(admForm.getPassportNo()==null || StringUtils.isEmpty(admForm.getPassportNo()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORTNO_REQUIRED,error);
				}
				if(admForm.getPassportcountry()==null || StringUtils.isEmpty(admForm.getPassportcountry()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_COUNTRY_REQUIRED,error);
				}
				if(admForm.getPassportValidity()==null || StringUtils.isEmpty(admForm.getPassportValidity()))
				{
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_REQUIRED,error);
				}else if(admForm.getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getPassportValidity())){
					if(CommonUtil.isValidDate(admForm.getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
					}
				}
				}
				
			}
		}
		log.info("exit validatePassportIfNRI..");
		}
	
	/**
	 * submit uploaded docs.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormAttachments(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit attachment...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors= admForm.validate(mapping, request);
		if(errors==null)
			errors= new ActionMessages();
		try {
			AdmissionFormAction.validateDocumentSize(admForm, errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_ATTACHMENT_PAGE);
			}
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			HttpSession session = request.getSession(false);
			if (session == null)
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			handler.persistAdmissionFormAttachments(admForm, session);
		} catch(ApplicationException e){
			log.error("error in submit education page...",e);
			String msg=super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error in submit education page...",e);
				throw e;
			
		}
		log.info("exit submit attachment...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_INIT_PARENT_PAGE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initParentPage(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter init parent page...");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try {
			OccupationTransactionHandler occhandler = OccupationTransactionHandler
					.getInstance();
			admForm.setOccupations(occhandler.getAllOccupation());
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			admForm.setCurrencies(handler.getCurrencies());
			admForm.setIncomes(handler.getIncomes());
		} catch (Exception e) {
			log.error("error in init parent page...",e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				admForm.setErrorMessage(msg);
				admForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
			{
				throw e;
			}
		}
		log.info("exit init parent page...");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PARENT_PAGE);
	}
	
	
	
	/**
	 * @param admForm
	 */
	private void setOtherReviewRequireds(AdmissionFormForm admForm, HttpServletRequest request) throws Exception{

		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null )
			admForm.getApplicantDetails().getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getPersonalData().getDob(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
		if(admForm.getApplicantDetails().getChallanDate()!=null )
			admForm.getApplicantDetails().setChallanDate(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getChallanDate(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
		
		admForm.setApplicantDetails(admForm.getApplicantDetails());
		String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
		if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
		{
			admForm.setWorkExpNeeded(true);
		}else{
			admForm.setWorkExpNeeded(false);
		}
			admForm.setApplicantDetails(admForm.getApplicantDetails());
		ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
			List<ProgramTypeTO> programtypes = programtypehandler
						.getProgramType();
		CourseTO applicantCourse = admForm.getApplicantDetails().getCourse();
		CourseTO selectedCourse=admForm.getApplicantDetails().getSelectedCourse();
			if (programtypes!=null) {
				admForm.setEditProgramtypes(programtypes);
				Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
				while (typeitr.hasNext()) {
					ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
					if(typeTO.getProgramTypeId()==selectedCourse.getProgramTypeId()){
						if(typeTO.getPrograms()!=null){
							admForm.setEditprograms(typeTO.getPrograms());
							Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
								while (programitr.hasNext()) {
									ProgramTO programTO = (ProgramTO) programitr
											.next();
									// PROGRAM LEVEL CONFIG SETTINGS
									if(programTO.getId()== selectedCourse.getProgramId()){
										admForm.setEditcourses(programTO.getCourseList());
										if(programTO!=null){ 
											if(programTO.getIsMotherTongue()==true)
											admForm.setDisplayMotherTongue(true);
											if(programTO.getIsDisplayLanguageKnown()==true)
												admForm.setDisplayLanguageKnown(true);
											if(programTO.getIsHeightWeight()==true)
												admForm.setDisplayHeightWeight(true);
											if(programTO.getIsDisplayTrainingCourse()==true)
												admForm.setDisplayTrainingDetails(true);
											if(programTO.getIsAdditionalInfo()==true)
												admForm.setDisplayAdditionalInfo(true);
											if(programTO.getIsExtraDetails()==true)
												admForm.setDisplayExtracurricular(true);
											if(programTO.getIsSecondLanguage()==true)
												admForm.setDisplaySecondLanguage(true);
											if(programTO.getIsFamilyBackground()==true)
												admForm.setDisplayFamilyBackground(true);
											if(programTO.getIsTCDetails()==true)
												admForm.setDisplayTCDetails(true);
											if(programTO.getIsEntranceDetails()==true)
												admForm.setDisplayEntranceDetails(true);
											if(programTO.getIsLateralDetails()==true)
												admForm.setDisplayLateralDetails(true);
											if(programTO.getIsTransferCourse()==true)
												admForm.setDisplayTransferCourse(true);
										}
									}
								}
						}	
					}
				}
			}
			
			checkExtradetailsDisplay(admForm);
			checkLateralTransferDisplay(admForm);
			
		if(CountryHandler.getInstance().getCountries()!=null){
			//birthCountry & states
			List<CountryTO> birthCountries= CountryHandler.getInstance().getCountries();
			if (!birthCountries.isEmpty()) {
				admForm.setCountries(birthCountries);
				Iterator<CountryTO> cntitr= birthCountries.iterator();
				while (cntitr.hasNext()) {
					CountryTO countryTO = (CountryTO) cntitr.next();
					if(admForm.getApplicantDetails().getPersonalData().getBirthCountry()!=null && countryTO.getId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getBirthCountry()) && admForm.getApplicantDetails().getPersonalData()!= null){
						List<StateTO> stateList=countryTO.getStateList();
						admForm.setEditStates(stateList);
					}
				}
			}
			
			//permanentCountry & states
			List<CountryTO> permanentCountries = CountryHandler.getInstance().getCountries();
			if (permanentCountries!=null) {
				admForm.setCountries(permanentCountries);
				Iterator<CountryTO> cntitr= permanentCountries.iterator();
				while (cntitr.hasNext()) {
					CountryTO countryTO = (CountryTO) cntitr.next();
					if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getPermanentCountryId()){
						List<StateTO> stateList = countryTO.getStateList();
						admForm.setEditPermanentStates(stateList);
					}
				}
			}
			
			//currentCountry & states
			List<CountryTO> currentCountries = CountryHandler.getInstance().getCountries();
			if (currentCountries!=null) {
				admForm.setCountries(currentCountries);
				Iterator<CountryTO> cntitr= currentCountries.iterator();
				while (cntitr.hasNext()) {
					CountryTO countryTO = (CountryTO) cntitr.next();
					if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCurrentCountryId()){
						List<StateTO> stateList = countryTO.getStateList();
						admForm.setEditCurrentStates(stateList);
					}
				}
			}
			
			//parentCountry & states
			List<CountryTO> parentCountries = CountryHandler.getInstance().getCountries();
			if (parentCountries!=null) {
				admForm.setCountries(parentCountries);
				Iterator<CountryTO> cntitr= parentCountries.iterator();
				while (cntitr.hasNext()) {
					CountryTO countryTO = (CountryTO) cntitr.next();
					if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getParentCountryId()){
						admForm.setEditParentStates(null);
						List<StateTO> stateList = countryTO.getStateList();
						admForm.setEditParentStates(stateList);
					}
				}
			}
		}
		
		//guardian states
		
		List<CountryTO> guardianCountries = CountryHandler.getInstance().getCountries();
		if (guardianCountries!=null) {
			admForm.setCountries(guardianCountries);
			Iterator<CountryTO> cntitr= guardianCountries.iterator();
			while (cntitr.hasNext()) {
				CountryTO countryTO = (CountryTO) cntitr.next();
				if(countryTO.getId()== admForm.getApplicantDetails().getPersonalData().getCountryByGuardianAddressCountryId()){
					admForm.setEditGuardianStates(null);
					List<StateTO> stateList = countryTO.getStateList();
					admForm.setEditGuardianStates(stateList);
				}
			}
		}
	
		if(admForm.getApplicantDetails().getPersonalData()!=null){
			
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
					if(orgTO.getExtracurriculars()!=null)
						admForm.getApplicantDetails().getPersonalData().setStudentExtracurricularsTos(orgTO.getExtracurriculars());
				}
			
		}
		
		//Nationality
			AdmissionFormHandler formhandler = AdmissionFormHandler.getInstance();
			admForm.setNationalities(formhandler.getNationalities());
		// languages	
			LanguageHandler langHandler=LanguageHandler.getHandler();
			admForm.setMothertongues(langHandler.getLanguages());
			admForm.setLanguages(langHandler.getOriginalLanguages());
			
			if(admForm.isDisplayAdditionalInfo())
			{
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);
					admForm.setOrganizationName(orgTO.getOrganizationName());
					admForm.setNeedApproval(orgTO.isNeedApproval());
				}
			}
			
		// res. catg
			admForm.setResidentTypes(formhandler.getResidentTypes());	
			
			ReligionHandler religionhandler = ReligionHandler.getInstance();
			if(religionhandler.getReligion()!=null){
				List<ReligionTO> religionList=religionhandler.getReligion();
				admForm.setReligions(religionList);
				Iterator<ReligionTO> relItr=religionList.iterator();
				while (relItr.hasNext()) {
					ReligionTO relTO = (ReligionTO) relItr.next();
					if(admForm.getApplicantDetails().getPersonalData().getReligionId() !=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())  
							&& StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId() ) && relTO.getReligionId()== Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()) ){
						List<ReligionSectionTO> subreligions=relTO.getSubreligions();
						admForm.setSubReligions(subreligions);
					}
				}
			}
		// caste category
		List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
		admForm.setCasteList(castelist);
		
		// Admitted Through
		List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
		admForm.setAdmittedThroughList(admittedList);
		// subject Group
		List<SubjectGroupTO> sujectgroupList=SubjectGroupHandler.getInstance().getSubjectGroupDetails(selectedCourse.getId());
		admForm.setSubGroupList(sujectgroupList);
		String[] subjectgroups=admForm.getApplicantDetails().getSubjectGroupIds();
		if (subjectgroups!=null && subjectgroups.length==0 && sujectgroupList!=null) {
			subjectgroups=new String[sujectgroupList.size()];
			admForm.getApplicantDetails().setSubjectGroupIds(subjectgroups);
		}
		
		//incomes
		List<IncomeTO> incomeList = AdmissionFormHandler.getInstance().getIncomes();
		admForm.setIncomeList(incomeList);
			
		//currencies
		List<CurrencyTO> currencyList = AdmissionFormHandler.getInstance().getCurrencies();
		admForm.setCurrencyList(currencyList);
		
		UniversityHandler unhandler = UniversityHandler.getInstance();
		List<UniversityTO> universities = unhandler.getUniversity();
		admForm.setUniversities(universities);
		
		OccupationTransactionHandler occhandler = OccupationTransactionHandler
		.getInstance();
		admForm.setOccupations(occhandler.getAllOccupation());
		
		if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity()) )
			admForm.getApplicantDetails().getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(admForm.getApplicantDetails().getPersonalData().getPassportValidity(), PresidanceAdmissionFormAction.SQL_DATEFORMAT,PresidanceAdmissionFormAction.FROM_DATEFORMAT));
		
		// set photo to session
		if(admForm.getApplicantDetails().getEditDocuments()!=null){
			Iterator<ApplnDocTO> docItr=admForm.getApplicantDetails().getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
				{
					admForm.setSubmitDate(docTO.getSubmitDate());
				}
				if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
					HttpSession session= request.getSession(false);
					if(session!=null){
						session.setAttribute(PresidanceAdmissionFormAction.PHOTOBYTES, docTO.getPhotoBytes());
					}
				}
			}
		}
		
		
		
		// preferences
			
		if (admForm.getApplicantDetails().getOriginalPreferences() != null) {
			List<CandidatePreferenceTO> prefTos=new ArrayList<CandidatePreferenceTO>();
			Iterator<CandidatePreference> iterator = admForm.getApplicantDetails().getOriginalPreferences().iterator();
			while (iterator.hasNext()) {
				CandidatePreference preferenceBO = (CandidatePreference) iterator.next();
				if(preferenceBO.getPrefNo() == 1){
					CandidatePreferenceTO firstTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(firstTo,applicantCourse);
					firstTo.setPrefNo(1);
					// as course name is in fomr set it to display
					firstTo.setCoursName(admForm.getCourseName());
					firstTo.setCoursId(String.valueOf(applicantCourse.getId()));
					prefTos.add(firstTo);
				}else if (preferenceBO.getPrefNo() == 2) {
					CandidatePreferenceTO secTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(secTo,applicantCourse);
					secTo.setPrefNo(2);
					secTo.setCoursId(String.valueOf(preferenceBO.getCourse().getId()));
					prefTos.add(secTo);
				} else if (preferenceBO.getPrefNo() == 3) {
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(String.valueOf(preferenceBO.getCourse().getId()));
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}
				
			}
			if(prefTos!=null && prefTos.size()<3){
				int sizediff=3-prefTos.size();
				if(sizediff==2){
					CandidatePreferenceTO secTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(secTo,applicantCourse);
					secTo.setCoursId(null);
					secTo.setPrefNo(2);
					prefTos.add(secTo);
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(null);
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}else if(sizediff==1){
					CandidatePreferenceTO thirdTo=new CandidatePreferenceTO();
					formhandler.populatePreferenceTO(thirdTo,applicantCourse);
					thirdTo.setCoursId(null);
					thirdTo.setPrefNo(3);
					prefTos.add(thirdTo);
				}
			}
			Collections.sort(prefTos);
			admForm.setPreferenceList(prefTos);
		}
		
		// Re-position the education list
		if (admForm.getQualifications() != null) {
			Iterator<EdnQualificationTO> itr= admForm.getApplicantDetails().getEdnQualificationList().iterator();
			

			while (itr.hasNext()) {
				EdnQualificationTO qualificationTO = (EdnQualificationTO) itr
						.next();
				Iterator<EdnQualificationTO> itr2= admForm.getQualifications().iterator();
				while (itr2.hasNext()) {
					EdnQualificationTO origTO = (EdnQualificationTO) itr2.next();
					if(origTO.getDocCheckListId()!=0 && 
							origTO.getDocCheckListId()==qualificationTO.getDocCheckListId()){
						qualificationTO.setCountId(origTO.getCountId());
					}
						
				}
			}
		}
		
	
	}
	
	/**
	 * saves parent details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAdmissionFormParentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submit full application..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		ActionMessages errors=admForm.validate(mapping, request);
		AdmissionFormAction.validateParentPhone(admForm, errors);
		AdmissionFormAction.validateGuardianPhone(admForm, errors);
		if(errors!=null && !errors.isEmpty() )
				{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PARENT_PAGE);
				}
		try {
			AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
			HttpSession session = request.getSession(false);
			if (session == null) {
				return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
			}
			int appliedyear=0;
			AdmAppln applicationdata=null;
			if (session != null) {
				// get applied year
				if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				{
					log.info("application no set session application data...");
					applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
					if(applicationdata!=null){
						appliedyear=applicationdata.getAppliedYear();
						applicationdata.setIsApproved(!admForm.isNeedApproval());
						applicationdata.setIsLig(false);
					}
				}
			}
			// if online,generate online application number
			if(admForm.isOnlineApply()){
				String applnNOgenerated=handler.getGeneratedOnlineAppNo(admForm.getCourseId(),appliedyear,true);
				if(applnNOgenerated!=null && applicationdata!=null && !StringUtils.isEmpty(applnNOgenerated) && StringUtils.isNumeric(applnNOgenerated) )
				{
					applicationdata.setApplnNo(Integer.parseInt(applnNOgenerated));
					admForm.setApplicationNumber(applnNOgenerated);
					session.setAttribute(CMSConstants.APPLICATION_DATA, applicationdata);
				}else{
					// sends mail to admin to configure applnno.
					handler.sendMailToAdmin(admForm,appliedyear);
					ActionMessages message = new ActionMessages();
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL);
					message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_ONLINERANGE_FULL, error);
					saveErrors(request, message);
					cleanUpPageData(admForm);
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_APPLICATIONDETAIL_PAGE);
				}
			}else{
				String applnNOoffLine=admForm.getApplicationNumber();
				if(applnNOoffLine!=null && applicationdata!=null  && !StringUtils.isEmpty(applnNOoffLine) && StringUtils.isNumeric(applnNOoffLine) )
				{
					applicationdata.setApplnNo(Integer.parseInt(applnNOoffLine));
					session.setAttribute(CMSConstants.APPLICATION_DATA, applicationdata);
				}
			}
			
			// above section will be implemented as confirm page >> edit >> submit, print
			
			AdmApplnTO applicantdetails=handler.getCompleteApplication(session, admForm);
			admForm.setApplicantDetails(applicantdetails);
			
			setOtherReviewRequireds(admForm,request);
			
			log.info("exit submit full application..");
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			
		}catch (BusinessException e) {
		
			log.error("error in final submit of application page...",e);
			String msgKey=super.handleBusinessException(e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_PARENT_PAGE);
		} catch (ApplicationException e) {
		
			log.error("error in final submit of application page...",e);
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in final submit of application page...",e);
			throw e;	
		}
		
	}
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validateConfirmOtherOptions(AdmissionFormForm admForm,
			ActionMessages errors) throws Exception {
		log.info("enter validateEditOtherOptions..");
		if(errors==null){
			errors= new ActionMessages();
		}

		if((admForm.getApplicantDetails().getPersonalData().getReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId())|| (admForm.getApplicantDetails().getPersonalData().getReligionId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionOthers()) ))
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED).hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED);
				errors.add(CMSConstants.ADMISSIONFORM_RELIGION_REQUIRED, error);
			}
		}
		
		if(admForm.getApplicantDetails().getPersonalData().getReligionId()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionId()) && StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getReligionId())){
			ISubReligionTransaction relTxn=new SubReligionTransactionImpl();
			//if master entry exists
			if(relTxn.checkSubReligionExists(Integer.parseInt(admForm.getApplicantDetails().getPersonalData().getReligionId()))){
				if((admForm.getApplicantDetails().getPersonalData().getSubReligionId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getSubReligionId())|| (admForm.getApplicantDetails().getPersonalData().getSubReligionId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getReligionSectionOthers())) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED);
						errors.add(CMSConstants.ADMISSIONFORM_SUBRELIGION_REQUIRED, error);
					}
				}
			}
			}
			
//		if((admForm.getApplicantDetails().getPersonalData().getCasteId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteId())|| (admForm.getApplicantDetails().getPersonalData().getCasteId().equalsIgnoreCase(PresidanceAdmissionFormAction.OTHER))) && (admForm.getApplicantDetails().getPersonalData().getCasteOthers()==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getCasteOthers()) ))
//		{
//			if (errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED).hasNext()) {
//				ActionMessage error = new ActionMessage(
//						CMSConstants.ADMISSIONFORM_CASTE_REQUIRED);
//				errors.add(CMSConstants.ADMISSIONFORM_CASTE_REQUIRED, error);
//			}
//		}
		if(admForm.isOnlineApply()){
			if((admForm.getApplicantDetails().getPersonalData().getPermanentStateId()==null || StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentStateId()) )&& ((admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers())==null ||StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPermanentAddressStateOthers()) ))
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED).hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_PERMSTATE_REQUIRED, error);
				}
			}
		}
		log.info("exit validateEditOtherOptions..");
	}
	/**
	 * @param admForm
	 * @param errors
	 */
	private void validatePhoneValidity(AdmissionFormForm admForm,
			ActionMessages errors) {
		log.info("enter validateEditPhone..");
		if(errors==null)
			errors= new ActionMessages();
		
				

				if(admForm.getApplicantDetails().getPersonalData().getPhNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo1()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getPhNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPhNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getPhNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_PHONE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
					}
				}
				
//				if(admForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo1()) )
//				{
//					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
//						ActionMessage error = new ActionMessage(
//								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
//						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
//					}
//				}
				
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && admForm.getApplicantDetails().getPersonalData().getMobileNo2().trim().length()!=10 )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo2()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				if(admForm.getApplicantDetails().getPersonalData().getMobileNo3()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getMobileNo3()) )
				{
					if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
						errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
					}
				}
				log.info("exit validateEditPhone..");
	}
	/**
	 * admission form submit after confirmation
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveConfirmedApplication(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("entered updateApplicationEdit..");
		AdmissionFormForm admForm = (AdmissionFormForm) form;
		try{
			//validattion if needed
			ActionMessages errors=admForm.validate(mapping, request);
			validateEditProgramCourse(errors, admForm);
			validatePhoneValidity(admForm, errors);
			validateEditParentPhone(admForm, errors);
			validateEditGuardianPhone(admForm, errors);
			validateEditPassportIfNRI(admForm, errors);
			validateEditOtherOptions(admForm, errors);
			validateConfirmOtherOptions(admForm, errors);
			validateEditCommAddress(admForm, errors);
			validateEditCoursePreferences(admForm, errors);
			validateSubjectGroups(admForm, errors);
			if(admForm.isDisplayTCDetails())
			validateTcDetailsEdit(admForm, errors);
			if(admForm.isDisplayEntranceDetails())
			validateEntanceDetailsEdit(admForm, errors);
			//email comparision
			if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
				if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
						if(!admForm.getApplicantDetails().getPersonalData().getEmail().equals(admForm.getConfirmEmailId())){
							if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
							}
						}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
					}
				}
			}else if(admForm.getConfirmEmailId()!=null && !StringUtils.isEmpty(admForm.getConfirmEmailId())){
				if (errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL) != null&& !errors.get(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL,new ActionError(CMSConstants.ADMISSIONFORM_EMAIL_CONFIRM_UNEQUAL));
				}
			}
			
			if (admForm.getApplicantDetails().getChallanDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getChallanDate())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getChallanDate())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getChallanDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_FUTURE));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_APPLICATIONDT_INVALID));
					}
				}
			}
			// if admission validate admission date
			if(admForm.isAdmissionEdit()){
				// validate Admission Date
				if (admForm.getApplicantDetails().getAdmissionDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getAdmissionDate())) {
					if(CommonUtil.isValidDate(admForm.getApplicantDetails().getAdmissionDate())){
					boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getAdmissionDate());
					if(!isValid){
						if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_FUTURE));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_ADMISSIONDT_INVALID));
						}
					}
				}
			}
			
			
			if(admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
			
			if (admForm.getApplicantDetails().getPersonalData()!=null && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())) {
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getDob())){
				boolean	isValid = validatefutureDate(admForm.getApplicantDetails().getPersonalData().getDob());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
				// online age range check
				
					if(admForm.isOnlineApply() && admForm.getAgeToLimit()!=0 && admForm.getApplicantDetails().getPersonalData().getDob()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getDob())){
						boolean valid=validateOnlineDOB(admForm.getAgeFromLimit(),admForm.getAgeToLimit(),admForm.getApplicantDetails().getPersonalData().getDob());
						if(!valid){
							if (errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS).hasNext()) {
								errors.add(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS, new ActionError(CMSConstants.ADMISSIONFORM_DOB_EXCEEDS));
							}
						}
				}
				
				
				
				
			}
//				else{
//				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
//					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
//				}
//			}
			}
			if(admForm.getApplicantDetails().getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
				
				if(CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity())){
					boolean isValid=validatePastDate(admForm.getApplicantDetails().getPersonalData().getPassportValidity());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_INVALID));
						}
					}
					}else{
						if (errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_PASSPORT_VALIDITY_INVALID));
						}
					}
			}
			if(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())&& !CommonUtil.isValidDate(admForm.getApplicantDetails().getPersonalData().getResidentPermitDate())){
				
				if (errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_PERMITDT_INVAID));
				}
			}
			
			if (admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate())) {
				if(CommonUtil.isValidDate(admForm.getSubmitDate())){
				boolean	isValid = validatePastDate(admForm.getSubmitDate());
				if(!isValid){
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_PAST));
					}
				}
				}else{
					if (errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID, new ActionError(CMSConstants.ADMISSIONFORM_SUBMITDT_INVALID));
					}
				}
			}
			
			
			List<ApplicantWorkExperienceTO> expList=admForm.getApplicantDetails().getWorkExpList();
			if(expList!=null){
				Iterator<ApplicantWorkExperienceTO> toItr=expList.iterator();
				while (toItr.hasNext()) {
					ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) toItr	.next();
					validateWorkExperience(expTO, errors);
				}
			}
			validateEditEducationDetails(errors, admForm);
			validateEditDocumentSize(admForm, errors,request);
			if(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getTrainingDuration()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getTrainingDuration())){
				if (errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DURATION_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DURATION_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_DURATION_INVALID));
				}
			}
			
			// validate height and weight
			if(admForm.getApplicantDetails().getPersonalData().getHeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getHeight()) && !StringUtils.isNumeric(admForm.getApplicantDetails().getPersonalData().getHeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_HEIGHT_INVALID));
				}
			}
			
			if(admForm.getApplicantDetails().getPersonalData().getWeight()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getWeight()) && !CommonUtil.isValidDecimal(admForm.getApplicantDetails().getPersonalData().getWeight())){
				if (errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID,new ActionError(CMSConstants.ADMISSIONFORM_WEIGHT_INVALID));
				}
			}
			
			
			if(errors==null)
				errors= new ActionMessages();

				if (errors != null && !errors.isEmpty()) {
					resetHardCopySubmit(admForm.getApplicantDetails());
					saveErrors(request, errors);
					
					if(admForm.isOnlineApply())
						return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
					else
					return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
					
				}
		HttpSession session = request.getSession(false);
				if (session == null) {
					return mapping.findForward(CMSConstants.KNOWLEDGEPRO_HOMEPAGE);
				}
				
		AdmApplnTO applicantDetail=admForm.getApplicantDetails();
		//remove pasport country default check if passport number not selected
		removePassportCountryDefault(applicantDetail);
		AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
		if(!admForm.isReviewWarned())
		{
			admForm.setReviewWarned(true);
			resetHardCopySubmit(applicantDetail);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = new ActionMessage(
					CMSConstants.APPLICATION_REVIEW_WARN);
			messages.add("messages", message);
			saveMessages(request, messages);
		
			if(admForm.isOnlineApply())
				return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINE_CONFIRMSUBMIT_PAGE);
			else
			return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
		}
			// set preferences to one to
			PreferenceTO preferenceTO = new PreferenceTO();
			if(admForm.getPreferenceList()!=null){
				Iterator<CandidatePreferenceTO> iterator = admForm.getPreferenceList().iterator();
				
				while (iterator.hasNext()) {
					CandidatePreferenceTO prefTO = (CandidatePreferenceTO) iterator.next();
					if (prefTO.getPrefNo() == 1) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setFirstPerfId(prefTO.getId());
						preferenceTO.setFirstPrefCourseId(String.valueOf(applicantDetail.getCourse().getId()));						
					} else if (prefTO.getPrefNo() == 2) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setSecondPerfId(prefTO.getId());
						preferenceTO.setSecondPrefCourseId(String.valueOf(prefTO.getCoursId()));
					} else if (prefTO.getPrefNo() == 3) {
						preferenceTO.setId(prefTO.getId());
						preferenceTO.setThirdPerfId(prefTO.getId());
						preferenceTO.setThirdPrefCourseId(String.valueOf(prefTO.getCoursId()));
						
					}
				}
			}
			applicantDetail.setPreference(preferenceTO);
			boolean result=handler.saveCompleteApplication(applicantDetail,admForm,session,true);
			if(result){
				admForm.setQuotaCheck(false);
				admForm.setEligibleCheck(false);
				handler.sendMailToStudent(admForm);
				request.setAttribute("transactionstatus", "success");
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_SUCCESS_STATUS,applicantDetail.getApplnNo(),applicantDetail.getPersonalData().getDob());
				messages.add("messages", message);
				saveMessages(request, messages);
				cleanupSessionData(session);
				cleanUpPageData(admForm);
			}else {
				ActionMessages message = new ActionMessages();
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE);
				message.add(CMSConstants.ADMISSIONFORM_APPLICATIONNO_NOTUNIQUE, error);
				saveErrors(request, message);
				return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_CONFIRMSUBMIT_PAGE);
			}
			

//		}
		}catch (Exception e){
		log.error("Error in  getApplicantDetails application form edit page...",e);
		if (e instanceof ApplicationException) {
			String msg = super.handleApplicationException(e);
			admForm.setErrorMessage(msg);
			admForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else {
			throw e;
		}
	}
		cleanupEditData(admForm);
		cleanupEditSessionData(request);
		log.info("exit updateApplicationEdit..");
		return mapping.findForward(CMSConstants.PRC_ADMISSIONFORM_CONFIRMPRINT_PAGE);
	}
	
	/**
	 * @param applicantDetail
	 */
	private void removePassportCountryDefault(AdmApplnTO applicantDetail) {
		if(applicantDetail!=null && applicantDetail.getPersonalData()!=null)
		{
			if((applicantDetail.getPersonalData().getPassportNo()==null || StringUtils.isEmpty(applicantDetail.getPersonalData().getPassportNo())
					)&& (applicantDetail.getPersonalData().getPassportCountry()!=0)){
				//clear the default value
				applicantDetail.getPersonalData().setPassportCountry(0);
			}
					
		}
	}
}
