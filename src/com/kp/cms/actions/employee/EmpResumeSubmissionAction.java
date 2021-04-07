package com.kp.cms.actions.employee;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpResumeSubmissionForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.employee.EmpResumeSubmissionHandler;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.utilities.CommonUtil;

public class EmpResumeSubmissionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmpResumeSubmissionAction.class);
	
	
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(EmpResumeSubmissionAction.PHOTOBYTES) != null)
				session.removeAttribute(EmpResumeSubmissionAction.PHOTOBYTES);
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
	public ActionForward initEmpResume(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("starting of initEmpResume of EmpResumeSubmission");
		System.out.println();
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		cleanupEditSessionData(request);
		try{
		empResumeSubmissionForm.reset();
		empResumeSubmissionForm.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		empResumeSubmissionForm.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		empResumeSubmissionForm.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		setDataToForm(empResumeSubmissionForm);
		HttpSession session=request.getSession();
		byte[] str= (byte[]) session.getAttribute("photo");
		setUserId(request,empResumeSubmissionForm);
		empResumeSubmissionForm.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		empResumeSubmissionForm.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		empResumeSubmissionForm.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		empResumeSubmissionForm.getEmpResumeSubmissionTo().setEmpQualificationLevelTos(null);
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
		//added by mahi start
		 empResumeSubmissionForm.setServerDownMessage(null);
		 String maintenanceMessage =  MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
		 if(maintenanceMessage!=null){
			 empResumeSubmissionForm.setServerDownMessage(maintenanceMessage);
			 session.setAttribute("serverDownMessage", maintenanceMessage);
		 }
		//end
		}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				empResumeSubmissionForm.setErrorMessage(msg);
				empResumeSubmissionForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
		initializeQualificationAndEducation(empResumeSubmissionForm);
		log.info("End of initEmpResume of EmpResumeSubmission");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
	}
	
	/**
	 * @param empResumeSubmissionForm
	 */
	private void initializeQualificationAndEducation(EmpResumeSubmissionForm empResumeSubmissionForm) {
		
		List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
		EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
		empPreviousOrgTo.setIndustryExpYears("");
		empPreviousOrgTo.setIndustryExpMonths("");
		empPreviousOrgTo.setCurrentDesignation("");
		empPreviousOrgTo.setCurrentOrganisation("");
//		empPreviousOrgTo.setExperienceLength(String.valueOf(list.size()));
		empResumeSubmissionForm.setIndustryExpLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		
		List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.setTeachingExpYears("");
		empPreviousOrgTo.setTeachingExpMonths("");
		empPreviousOrgTo.setCurrentTeachingOrganisation("");
		empPreviousOrgTo.setCurrentTeachnigDesignation("");
//		empPreviousOrgTo.setTeachingExpLength(String.valueOf(teachingList.size()));
		empResumeSubmissionForm.setTeachingExpLength(String.valueOf(teachingList.size()));
		teachingList.add(empPreviousOrgTo);
		empResumeSubmissionForm.getEmpResumeSubmissionTo().setExperiences(list);
		empResumeSubmissionForm.getEmpResumeSubmissionTo().setTeachingExperience(teachingList);
		
	}

	/**
	 * 
	 */
	public  void setDataToForm(EmpResumeSubmissionForm empResumeSubmissionForm)throws Exception {
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		empResumeSubmissionForm.setIsCjc(isCjc);
		EmpResumeSubmissionHandler.getInstance().getInitialData(empResumeSubmissionForm);
		empResumeSubmissionForm.setCurrentlyWorking("YES");
		//Initializing Eligiblity list	
		 List<EligibilityTestTO> list = new ArrayList<EligibilityTestTO>();
		    EligibilityTestTO  to1 = new EligibilityTestTO();
		    to1.setEligibilityTest("None");
		   
		    list.add(to1);
		    EligibilityTestTO  to2 = new EligibilityTestTO();
		    to2.setEligibilityTest("NET");
		    
		    list.add(to2);
		    EligibilityTestTO  to3 = new EligibilityTestTO();
		    to3.setEligibilityTest("SLET");
		   
		    list.add(to3);
		    EligibilityTestTO  to4 = new EligibilityTestTO();
		    to4.setEligibilityTest("SET");
		    
		    list.add(to4);
		    EligibilityTestTO  to5 = new EligibilityTestTO();
		    to5.setEligibilityTest("OTHER");
		   
		    list.add(to5);
		    empResumeSubmissionForm.setEligibilityList(list);
		  //Initializing Eligiblity list	
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo()!=null){
			Iterator<EmpQualificationLevelTo> iterator=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo().iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo empQualificationLevelTo=iterator.next();
				if(empQualificationLevelTo!=null){
					empQualificationLevelTo.setCourse("");
					empQualificationLevelTo.setSpecialization("");
					empQualificationLevelTo.setGrade("");
					empQualificationLevelTo.setInstitute("");
					empQualificationLevelTo.setYearOfComp("");
				}
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
	public ActionForward resetExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences()!=null)
		if(empResumeSubmissionForm.getMode()!=null){
			if (empResumeSubmissionForm.getMode().equalsIgnoreCase("ExpAddMore")) {
				// add one blank to add extra one
				List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				empResumeSubmissionForm.setTeaching("false");
				empResumeSubmissionForm.setIndustry("true");
				/*----------------------*/
				empResumeSubmissionForm.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				empResumeSubmissionForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				empResumeSubmissionForm.setFocusValue("industry_"+size);
			}
		}
		log.info("End of resetExperienceInfo of EmpResumeSubmissionForm");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);

	}
	
	public ActionForward removeExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeExperienceInfo of EmpResumeSubmissionAction");
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		List<EmpPreviousOrgTo> list=null;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences()!=null)
		if(empResumeSubmissionForm.getMode()!=null){
			if (empResumeSubmissionForm.getMode().equalsIgnoreCase("ExpAddMore")) {
				// add one blank to add extra one
				list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences();
				if(list.size()>0)
				list.remove(list.size()-1);
				empResumeSubmissionForm.setIndustryExpLength(String.valueOf(list.size()-1));
				empResumeSubmissionForm.setTeaching("false");
				empResumeSubmissionForm.setIndustry("true");
			}
		}
		log.info("End of removeExperienceInfo of EmpResumeSubmissionForm");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);

	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetTeachingExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience()!=null)
		if(empResumeSubmissionForm.getMode()!=null){
			if (empResumeSubmissionForm.getMode().equalsIgnoreCase("ExpAddMore")) {
				List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				/* code added by sudhir*/
				empPreviousOrgTo.setTeachingFromDate("");
				empPreviousOrgTo.setTeachingToDate("");
				empResumeSubmissionForm.setTeaching("true");
				empResumeSubmissionForm.setIndustry("false");
				/*----------------------*/
				empResumeSubmissionForm.setTeachingExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				empResumeSubmissionForm.setMode(null);
				String size=String.valueOf(list.size()-1);
				empResumeSubmissionForm.setFocusValue("teach_"+size);
			}
		}
		log.info("End of resetExperienceInfo of EmpResumeSubmissionForm");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeTeachingExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		List<EmpPreviousOrgTo> list=null;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience()!=null)
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience().size()>0){
				list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience();
				list.remove(list.size()-1);
				empResumeSubmissionForm.setTeachingExpLength(String.valueOf(list.size()));
				empResumeSubmissionForm.setTeaching("true");
				empResumeSubmissionForm.setIndustry("false");
				
		}
		empResumeSubmissionForm.setTeachingExpLength(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmpResumeSubmissionForm");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOnlineResume(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpResumeSubmissionForm empResumeSubmissionForm =(EmpResumeSubmissionForm)form;
		ActionMessages messages=new ActionMessages();
		HttpSession session= request.getSession();
		ActionErrors errors=empResumeSubmissionForm.validate(mapping, request);
		validateDate(empResumeSubmissionForm,errors);
		isQualificationValid(empResumeSubmissionForm,errors);
		validateDepartmentOrEmpSubject(empResumeSubmissionForm,errors);
		validatePreviousExpDate(empResumeSubmissionForm,errors);
		validateEmployee(empResumeSubmissionForm,errors);
		validateWorking(empResumeSubmissionForm,errors);
		if(empResumeSubmissionForm.getIsCjc()){
			if(session.getAttribute("photo")==null && empResumeSubmissionForm.getFile().getFileSize()==0){
				errors.add(CMSConstants.EMPLOYEE_PHOTO_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_PHOTO_REQUIRED));
			}
		}
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=EmpResumeSubmissionHandler.getInstance().saveEmpResume(empResumeSubmissionForm,session);
				if(flag){
					EmpResumeSubmissionHandler.getInstance().sendMailToEmployee(empResumeSubmissionForm);
					EmpResumeSubmissionHandler.getInstance().sendMailToAdmin(empResumeSubmissionForm);
					ActionMessage message=new ActionMessage(CMSConstants.EMPLOYEE_RESUME_SUBMIT_SUCCESS,empResumeSubmissionForm.getApplicationNO());
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanupEditSessionData(request);
					session.setAttribute("photo", null);
					return mapping.findForward(CMSConstants.EMPLOYEE_RESUME_CONFIRM);
				}else{
					errors.add(CMSConstants.EMPLOYEE_RESUME_NOT_SUBMIT, new ActionError(CMSConstants.EMPLOYEE_RESUME_NOT_SUBMIT));
					saveErrors(request, errors);
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
				}
			} catch (Exception exception) {
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, errors);
			if(empResumeSubmissionForm.getFile()!=null && empResumeSubmissionForm.getFile().getFileData().length>0){
				FormFile file= empResumeSubmissionForm.getFile();
				byte[] photo =file.getFileData();
				session.setAttribute("photo", photo);
			}
			
			cleanupEditSessionData(request);
			return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
		}
	}
	
private void validatePreviousExpDate(EmpResumeSubmissionForm empResumeSubmissionForm, ActionErrors errors) {
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience()!=null){
			List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience();
			if(list!=null){
				Iterator<EmpPreviousOrgTo> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
					if(empPreviousOrgTo!=null){
						
						if(empPreviousOrgTo.getTeachingExpMonths()!=null && !empPreviousOrgTo.getTeachingExpMonths().isEmpty()||
								empPreviousOrgTo.getTeachingExpYears()!=null && !empPreviousOrgTo.getTeachingExpYears().isEmpty() ||
								empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()||
								empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
								if(empPreviousOrgTo.getCurrentTeachnigDesignation()==null || empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty()){
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUME_TEACHING_DESIGNATION));
								}
								if(empPreviousOrgTo.getCurrentTeachingOrganisation()==null || empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty()){
									errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUME_TEACHING_INSITUTION));
								}
							}
							if(empPreviousOrgTo.getTeachingFromDate()!=null && !empPreviousOrgTo.getTeachingFromDate().isEmpty()){
							  if (CommonUtil.isValidDate(empPreviousOrgTo.getTeachingFromDate())) {
								boolean isValid = validatefutureDate(empPreviousOrgTo.getTeachingFromDate());
								
									if (!isValid) {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMEFROMDATE_LARGE));
										}
									} else {
										errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMEFROMDATE_INVALID));
									}
							}
						if(empPreviousOrgTo.getTeachingToDate()!=null && !empPreviousOrgTo.getTeachingToDate().isEmpty()){
							if (CommonUtil.isValidDate(empPreviousOrgTo.getTeachingToDate())) {
									boolean isValid = validatefutureDate(empPreviousOrgTo.getTeachingToDate());
									
										if (!isValid) {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMETODATE_LARGE));
											}
										} else {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMETODATE_INVALID));
										}
								}
							
				}
		}
}
		}
	   
	if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences()!=null){
		List<EmpPreviousOrgTo> list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences();
		if(list!=null){
			Iterator<EmpPreviousOrgTo> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
				if(empPreviousOrgTo!=null){
					if(empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()||
						empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty() ||
						empPreviousOrgTo.getIndustryExpMonths()!=null && !empPreviousOrgTo.getIndustryExpMonths().isEmpty()||
						empPreviousOrgTo.getIndustryExpYears()!=null && !empPreviousOrgTo.getIndustryExpYears().isEmpty()){
						if(empPreviousOrgTo.getCurrentDesignation()==null || empPreviousOrgTo.getCurrentDesignation().isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUME_INDUSTRY_DESIGNATION));
						}
						if(empPreviousOrgTo.getCurrentOrganisation()==null || empPreviousOrgTo.getCurrentOrganisation().isEmpty()){
							errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUME_INDUSTRY_INSITUTION));
						}
					}
					
					
						if(empPreviousOrgTo.getIndustryFromDate()!=null && !empPreviousOrgTo.getIndustryFromDate().isEmpty()){
							  if (CommonUtil.isValidDate(empPreviousOrgTo.getIndustryFromDate())) {
								boolean isValid = validatefutureDate(empPreviousOrgTo.getIndustryFromDate());
								
									if (!isValid) {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMEFROMDATE_LARGE));
										}
									} else {
										errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMEFROMDATE_INVALID));
									}
							}
						if(empPreviousOrgTo.getIndustryToDate()!=null && !empPreviousOrgTo.getIndustryToDate().isEmpty()){
							if (CommonUtil.isValidDate(empPreviousOrgTo.getIndustryToDate())) {
									boolean isValid = validatefutureDate(empPreviousOrgTo.getIndustryToDate());
									
										if (!isValid) {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMETODATE_LARGE));
											}
										} else {
											errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.EMPLOYEE_RESUMETODATE_INVALID));
										}
								}
							
				}
				}
}
		}
}

	private void validateWorking(EmpResumeSubmissionForm empResumeSubmissionForm, ActionErrors errors) {
		if(empResumeSubmissionForm.getCurrentlyWorking()!=null && !empResumeSubmissionForm.getCurrentlyWorking().isEmpty() 
			&& empResumeSubmissionForm.getCurrentlyWorking().equalsIgnoreCase("yes")){
			if(empResumeSubmissionForm.getDesignationPfId()!=null && empResumeSubmissionForm.getDesignationPfId().isEmpty()){
				errors.add(CMSConstants.EMP_ONLINE_CURRENT_DESIG, new ActionError(CMSConstants.EMP_ONLINE_CURRENT_DESIG));
			}
			
			if(empResumeSubmissionForm.getOrgAddress()!=null && empResumeSubmissionForm.getOrgAddress().isEmpty()){
				errors.add(CMSConstants.EMP_ONLINE_CURRENTLY_ORG,new ActionError(CMSConstants.EMP_ONLINE_CURRENTLY_ORG));
			}
		}
		if(empResumeSubmissionForm.getCurrentState()!=null && !empResumeSubmissionForm.getCurrentState().isEmpty() 
				&& empResumeSubmissionForm.getCurrentState().equalsIgnoreCase("other")){
			if(empResumeSubmissionForm.getOtherCurrentState()!=null && empResumeSubmissionForm.getOtherCurrentState().isEmpty()){
				errors.add(CMSConstants.EMP_CURRENT_OTHER_STATE,new ActionError(CMSConstants.EMP_CURRENT_OTHER_STATE));
			}
		}
		
		if(empResumeSubmissionForm.getStateId()!=null && !empResumeSubmissionForm.getStateId().isEmpty() && empResumeSubmissionForm.getStateId().equalsIgnoreCase("other")){
			if(empResumeSubmissionForm.getOtherPermanentState()!=null && empResumeSubmissionForm.getOtherPermanentState().isEmpty()){
				errors.add(CMSConstants.EMP_PERMANENT_OTHER_STATE,new ActionError(CMSConstants.EMP_PERMANENT_OTHER_STATE));
			}
		}
	}

	private void isQualificationValid(EmpResumeSubmissionForm empResumeSubmissionForm,ActionErrors errors) {
		boolean flag=false;
		List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo()!=null)
			level=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo();
		if(level!=null){
			Iterator<EmpQualificationLevelTo> iterator=level.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevelTo to=iterator.next();
					if(to!=null){
						if(to.getCourse()!=null && !to.getCourse().isEmpty()  &&
								to.getYearOfComp()!=null && !to.getYearOfComp().isEmpty() &&
								to.getGrade()!=null && !to.getGrade().isEmpty() &&
								to.getInstitute()!=null && !to.getInstitute().isEmpty()){
							flag=true;
						}
				}
					if(flag)
						break;
			}
		}
		if(!flag){
			level=null;
			if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos()!=null)
				level=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos();
			if(level!=null){
				Iterator<EmpQualificationLevelTo> iterator=level.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevelTo to=iterator.next();
					if(to!=null){
						if(to.getCourse()!=null && !to.getCourse().isEmpty()  &&
								to.getGrade()!=null && !to.getGrade().isEmpty() &&
								to.getInstitute()!=null && !to.getInstitute().isEmpty()&&
								to.getYear()!=null && !to.getYear().isEmpty() && to.getEducationId()!=null && !to.getEducationId().isEmpty()){
							flag=true;
						}
					}
					if(flag)
						break;
				}
			}
		}
		if(!flag){
			errors.add(CMSConstants.EDUCATION_DETAILS_MIN_ONE_ROW_REQD,new ActionError(CMSConstants.EDUCATION_DETAILS_MIN_ONE_ROW_REQD));
		}
	}

	/**
	 * @param empResumeSubmissionForm
	 * @param errors
	 */
	@SuppressWarnings("deprecation")
	private void validateEmployee(EmpResumeSubmissionForm empResumeSubmissionForm, ActionErrors errors) {
		/*boolean flag=false;
		List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>(); 
		if(empResumeSubmissionForm.getCurrentlyWorking()!=null && !empResumeSubmissionForm.getCurrentlyWorking().isEmpty() 
				&& empResumeSubmissionForm.getCurrentlyWorking().equalsIgnoreCase("yes")){
			if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences()!=null)
				list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getExperiences();
			Iterator<EmpPreviousOrgTo> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
				if(empPreviousOrgTo!=null){
					if(empPreviousOrgTo.getIndustryExpYears()!=null && empPreviousOrgTo.getIndustryExpYears().isEmpty()
							&& empPreviousOrgTo.getIndustryExpMonths()!=null && empPreviousOrgTo.getIndustryExpMonths().isEmpty()
							&& empPreviousOrgTo.getCurrentDesignation()!=null && empPreviousOrgTo.getCurrentDesignation().isEmpty()
							&& empPreviousOrgTo.getCurrentOrganisation()!=null && empPreviousOrgTo.getCurrentOrganisation().isEmpty())
						break;
					
					if(empPreviousOrgTo.getIndustryExpYears()!=null && empPreviousOrgTo.getIndustryExpYears().isEmpty()){
						if (errors.get(CMSConstants.INDUSTRY_EXP_NOT_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_NOT_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_NOT_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_NOT_EMPTY));
					}else
						flag=true;
					
					if(empPreviousOrgTo.getIndustryExpMonths()!=null && empPreviousOrgTo.getIndustryExpMonths().isEmpty()){
						if (errors.get(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY));
					}else{
						flag=true;
					}
					
					if(empPreviousOrgTo.getCurrentDesignation()!=null && empPreviousOrgTo.getCurrentDesignation().isEmpty()){
						if (errors.get(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY));
					}else
						flag=true;
					
					if(empPreviousOrgTo.getCurrentOrganisation()!=null && empPreviousOrgTo.getCurrentOrganisation().isEmpty()){
						if (errors.get(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY));
					}else
						flag=true;
				}
			}
			
			list=null;
			if(!flag)
			if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience()!=null){
				list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getTeachingExperience();
				Iterator<EmpPreviousOrgTo> iterator2=list.iterator();
				while(iterator2.hasNext()){
					EmpPreviousOrgTo empPreviousOrgTo=iterator2.next();
					if(empPreviousOrgTo!=null){
						if(empPreviousOrgTo.getTeachingExpYears()!=null && empPreviousOrgTo.getTeachingExpYears().isEmpty()){
							if (errors.get(CMSConstants.TEACHING_EXP_NOT_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_NOT_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_NOT_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_NOT_EMPTY));
						}else
							flag=true;
						
						if(empPreviousOrgTo.getTeachingExpMonths()!=null && empPreviousOrgTo.getTeachingExpMonths().isEmpty()){
							if (errors.get(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY));
						}else
							flag=true;
						
						if(empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty()){
							if (errors.get(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY));
						}else
							flag=true;
						
						if(empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty()){
							if (errors.get(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY));
						}else
							flag=true;
					}
				}
			}
		}
		
		if(!flag){
			errors.add(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY));
		}*/
		List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
	/*	boolean flag=false;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo()!=null)
			level=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationFixedTo();
		if(level!=null){
			Iterator<EmpQualificationLevelTo> iterator=level.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo to=iterator.next();
				if(to!=null){
					if(to.getCourse()!=null && to.getCourse().trim().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_COURSE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_COURSE_REQUIRED));
					
//					if(to.getSpecialization()!=null && to.getSpecialization().isEmpty())
//						if (errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED).hasNext())
//							errors.add(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED));
					
					if(to.getYearOfComp()!=null && to.getYearOfComp().trim().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED, new ActionError(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED));
					
					if(to.getGrade()!=null && to.getGrade().trim().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_GRADE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_GRADE_REQUIRED));
					
					if(to.getInstitute()!=null && to.getInstitute().trim().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED));
				}
			}
		}*/
		
		level=null;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos()!=null)
			level=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos();
		if(level!=null){
			Iterator<EmpQualificationLevelTo> iterator=level.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo to=iterator.next();
				if(to!=null){
				/*	if(to.getCourse()!=null && to.getCourse().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_COURSE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_COURSE_REQUIRED));
					
//					if(to.getSpecialization()!=null && to.getSpecialization().isEmpty())
//						if (errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED).hasNext())
//							errors.add(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED));
					
					if(to.getYear()!=null && to.getYear().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED, new ActionError(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED));
					
					if(to.getGrade()!=null && to.getGrade().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_GRADE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_GRADE_REQUIRED));
					
					if(to.getInstitute()!=null && to.getInstitute().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED));
					*/
					if(to.getEducationId()!=null && to.getEducationId().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_QUAL_REQ) != null&& !errors.get(CMSConstants.EMPLOYEE_QUAL_REQ).hasNext())
							errors.add(CMSConstants.EMPLOYEE_QUAL_REQ, new ActionError(CMSConstants.EMPLOYEE_QUAL_REQ));
				}
			}
		}
	}

	/**
	 * @param empResumeSubmissionForm
	 * @param errors
	 */
	@SuppressWarnings("deprecation")
	private void validateDate(EmpResumeSubmissionForm empResumeSubmissionForm,ActionErrors errors) {
		try{
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
		int maxPhotoSize=0;
		Properties prop=new Properties();
		prop.load(propStream);
		maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		FormFile file=null;
		if(empResumeSubmissionForm.getFile()!=null)
			file=empResumeSubmissionForm.getFile();

	 /*   if(empResumeSubmissionForm.getEligibilityList()!=null && !empResumeSubmissionForm.getEligibilityList().isEmpty()){
	    	int count=0, Error=0;
	    	 List<EligibilityTestTO> depTo = empResumeSubmissionForm.getEligibilityList();
	    	 Iterator<EligibilityTestTO> itr = depTo.iterator();
	    	 EligibilityTestTO eligibilityTestTO;
	    	 
	 		while (itr.hasNext()) {
	 			
	 			eligibilityTestTO = itr.next();
	 			if(eligibilityTestTO.getChecked()== null && StringUtils.isEmpty(eligibilityTestTO.getChecked())) {
	 				Error=Error+1;
	 			}
	 			count=count+1;
	 		}
	    if(count==Error)
	    {
	    	if (errors.get(CMSConstants.EMPLOYEE_ELIGIBLITY_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_ELIGIBLITY_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_ELIGIBLITY_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_ELIGIBLITY_REQUIRED));
	    }
	    }
		}*/
	    if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && empResumeSubmissionForm.getDesignationId().equals("Teaching")){
	    	 if(empResumeSubmissionForm.getEmail()== null || StringUtils.isEmpty(empResumeSubmissionForm.getEmail())){
	    	if (errors.get(CMSConstants.EMPLOYEE_EMAIL_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_EMAIL_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_EMAIL_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_EMAIL_REQUIRED));
			}
	    }
	    }
	    if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && !empResumeSubmissionForm.getDesignationId().equals("Guest")){
	    	 if(empResumeSubmissionForm.getEmploymentStatus()== null || StringUtils.isEmpty(empResumeSubmissionForm.getEmploymentStatus())){
	    	if (errors.get(CMSConstants.EMPLOYEE_STATUS_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_STATUS_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_STATUS_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_STATUS_REQUIRED));
			}
	    }
	    }
	    if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && !empResumeSubmissionForm.getDesignationId().equals("Guest")){
	    	 if(empResumeSubmissionForm.getExpectedSalaryLakhs()== null || StringUtils.isEmpty(empResumeSubmissionForm.getExpectedSalaryLakhs())){
	    	if (errors.get(CMSConstants.EMPLOYEE_SALARY_LAKHS_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_SALARY_LAKHS_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_SALARY_LAKHS_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_SALARY_LAKHS_REQUIRED));
			}
	    }
	    }
	    if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && !empResumeSubmissionForm.getDesignationId().equals("Guest")){
	    	 if(empResumeSubmissionForm.getExpectedSalaryThousands()== null || StringUtils.isEmpty(empResumeSubmissionForm.getExpectedSalaryThousands())){
	    	if (errors.get(CMSConstants.EMPLOYEE_SALARY_THOUSANDS_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_SALARY_THOUSANDS_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_SALARY_THOUSANDS_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_SALARY_THOUSANDS_REQUIRED));
			}
	    }
	    }
	    if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && !empResumeSubmissionForm.getDesignationId().equals("Guest")){
	    	 if(empResumeSubmissionForm.getEmpJobTypeId()== null || StringUtils.isEmpty(empResumeSubmissionForm.getEmpJobTypeId())){
	    	if (errors.get(CMSConstants.EMPLOYEE_JOB_TYPE_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_JOB_TYPE_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_JOB_TYPE_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_JOB_TYPE_REQUIRED));
			}
	    }
	    }
	    if(empResumeSubmissionForm.getEmail()!= null && !StringUtils.isEmpty(empResumeSubmissionForm.getEmail()) && !CommonUtil.validateEmailID(empResumeSubmissionForm.getEmail())){
	    	if (errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL) != null && !errors.get(CMSConstants.EMPLOYEE_VALID_EMAIL).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_VALID_EMAIL,new ActionError(CMSConstants.EMPLOYEE_VALID_EMAIL));
			}
	    }
	    
	    if(empResumeSubmissionForm.getMobileNo1()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getMobileNo1()) && empResumeSubmissionForm.getMobileNo1().trim().length()!=10){
	    	if (errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG) != null&& !errors.get(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG,new ActionError(CMSConstants.EMPLOYEE_MOBILE_NO_WRONG));
			}
	    }
	    /*if(empResumeSubmissionForm.getDesignationId()!=null && !StringUtils.isEmpty(empResumeSubmissionForm.getDesignationId()) && empResumeSubmissionForm.getDesignationId().equals("Teaching")){
	    	 if(empResumeSubmissionForm.getDepartmentId()== null || StringUtils.isEmpty(empResumeSubmissionForm.getDepartmentId())){
	    	if (errors.get(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED) != null && !errors.get(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED));
			}
	    }
	    }*/
	/*	if(empResumeSubmissionForm.getDateOfBirth()!=null && !empResumeSubmissionForm.getDateOfBirth().isEmpty() && !CommonUtil.isValidDate(empResumeSubmissionForm.getDateOfBirth())){
			if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null&& !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
				errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID,new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
			}
		}*/
		
		if(empResumeSubmissionForm.getDateOfBirth()!=null && !empResumeSubmissionForm.getDateOfBirth().isEmpty()){

			if (CommonUtil.isValidDate(empResumeSubmissionForm.getDateOfBirth())) {
				boolean isValid = validatefutureDate(empResumeSubmissionForm.getDateOfBirth());
				if (!isValid) {
					if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
						errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
					}
				}
			} else {
				if (errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_INVAID).hasNext()) {
					errors.add(CMSConstants.ADMISSIONFORM_DOB_INVAID, new ActionError(CMSConstants.ADMISSIONFORM_DOB_INVAID));
				}
			}
		}
		 if (empResumeSubmissionForm.getCurrentAddressLine1() != null && !empResumeSubmissionForm.getCurrentAddressLine1().isEmpty() && empResumeSubmissionForm.getCurrentAddressLine1().trim().length() > 35)  {
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID, error);
			}
		}
			if (empResumeSubmissionForm.getCurrentAddressLine2() != null && !empResumeSubmissionForm.getCurrentAddressLine2().isEmpty() && empResumeSubmissionForm.getCurrentAddressLine2().trim().length() > 40)  {
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID, error);
			}
		}
			if (empResumeSubmissionForm.getAddressLine1() != null && !empResumeSubmissionForm.getAddressLine1().isEmpty() && empResumeSubmissionForm.getAddressLine1().trim().length() > 35)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID, error);
				}
			}
			if (empResumeSubmissionForm.getAddressLine2() != null && !empResumeSubmissionForm.getAddressLine2().isEmpty() && empResumeSubmissionForm.getAddressLine2().trim().length() > 40)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID, error);
				}
			}
			 if (empResumeSubmissionForm.getOtherInfo() != null && !empResumeSubmissionForm.getOtherInfo().isEmpty() && empResumeSubmissionForm.getOtherInfo().trim().length() > 500)  {
					if (errors.get(CMSConstants.EMPLOYEE_OTHERINFO_INVALID) != null
							&& !errors.get(CMSConstants.EMPLOYEE_OTHERINFO_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_OTHERINFO_INVALID);
						errors.add(CMSConstants.EMPLOYEE_OTHERINFO_INVALID, error);
					}
				}
		
		/*if(!empResumeSubmissionForm.getSameAddress()){
			if(empResumeSubmissionForm.getAddressLine1()!=null && empResumeSubmissionForm.getAddressLine1().isEmpty()){
				errors.add(CMSConstants.EMPLOYEE_PERMANENT_ADDRESS,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_ADDRESS));
			}
			
			if(empResumeSubmissionForm.getCountryId()!=null && empResumeSubmissionForm.getCountryId().isEmpty()){
				errors.add(CMSConstants.EMPLOYEE_PERMANENT_COUNTRY,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_COUNTRY));
			}
			
			if(empResumeSubmissionForm.getStateId()!=null && empResumeSubmissionForm.getStateId().isEmpty()){
				errors.add(CMSConstants.EMPLOYEE_PERMANENT_STATE,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_STATE));
			}
			
			if(empResumeSubmissionForm.getCity()!=null && empResumeSubmissionForm.getCity().isEmpty()){
				errors.add(CMSConstants.EMPLOYEE_PERMANENT_CITY,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_CITY));
			}
		}*/
		
		if( file!=null && maxPhotoSize< file.getFileSize()){
			if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
				errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
			}
		}
		if(file!=null ){
			String extn="";
			int index = file.getFileName().lastIndexOf(".");
			if(index != -1){
				extn = file.getFileName().substring(index, file.getFileName().length());
			}
			if(!extn.isEmpty() && !extn.equalsIgnoreCase(".jpg")){
				if(errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR).hasNext()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSIONFORM_PHOTO_FILETYPEERROR));
				}
			}
		}
		
		/*if(empResumeSubmissionForm.getEligibilityTest()!=null && empResumeSubmissionForm.getEligibilityTest().length>0){
			String[] eligibilityTest=empResumeSubmissionForm.getEligibilityTest();
			for(int i=0;i<eligibilityTest.length;i++){
			if(eligibilityTest[i].equalsIgnoreCase("Other") ){
				if(empResumeSubmissionForm.getgetOtherEligibilityTest()==null || empResumeSubmissionForm.getOtherEligibilityTest().isEmpty())
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ELIGIBILITY_TEST_OTHER_REQD));	
				}
			}
		}*/
		}catch (Exception e) {
			e.printStackTrace();
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
	public ActionForward addQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		log.info("Befinning of addQualificationLevel of EmpResumeSubmissionAction");
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		List<EmpQualificationLevelTo> list=null;
		EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
		empQualificationLevelTo.setEducationId("");
		empQualificationLevelTo.setCourse("");
		empQualificationLevelTo.setSpecialization("");
		empQualificationLevelTo.setGrade("");
		empQualificationLevelTo.setInstitute("");
		empQualificationLevelTo.setYearOfComp("");
		
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos()!=null){
		if(empResumeSubmissionForm.getMode()!=null){
			if (empResumeSubmissionForm.getMode().equalsIgnoreCase("ExpAddMore")) {
				list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos();
				list.add(empQualificationLevelTo);
				empResumeSubmissionForm.setMode(null);
			}
		}
		}else{
			list=new ArrayList<EmpQualificationLevelTo>();
			list.add(empQualificationLevelTo);
			empResumeSubmissionForm.setMode(null);
		}
		empResumeSubmissionForm.setLevelSize(String.valueOf(list.size()));
		empResumeSubmissionForm.getEmpResumeSubmissionTo().setEmpQualificationLevelTos(list);
		String size=String.valueOf(list.size()-1);
		empResumeSubmissionForm.setFocusValue("course_"+size);
		log.info("End of addQualificationLevel of EmpResumeSubmissionForm");
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmpResumeSubmissionForm empResumeSubmissionForm=(EmpResumeSubmissionForm)form;
		List<EmpQualificationLevelTo> list=null;
		if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos()!=null){
				if(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos().size()>0){
						list=empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos();
						list.remove(empResumeSubmissionForm.getEmpResumeSubmissionTo().getEmpQualificationLevelTos().size()-1);
				}
			}
		empResumeSubmissionForm.setLevelSize(String.valueOf(list.size()));
		empResumeSubmissionForm.getEmpResumeSubmissionTo().setEmpQualificationLevelTos(list);
		return mapping.findForward(CMSConstants.EMP_ONLINE_RESUME);
	}
	
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, EmpResumeSubmissionAction.FROM_DATEFORMAT,
				EmpResumeSubmissionAction.TO_DATEFORMAT);
		Date date = new Date(formattedString);
		Date curdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(curdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date origdate = cal.getTime();
		log.info("exit validatefutureDate..");
		return !(date.compareTo(origdate) == 1);

	}
	private void validateDepartmentOrEmpSubject(EmpResumeSubmissionForm empResumeSubmissionForm, ActionErrors errors) {
		if(empResumeSubmissionForm.getIsCjc()){
			if(empResumeSubmissionForm.getEmpSubjectId()==null || empResumeSubmissionForm.getEmpSubjectId().isEmpty()){
				errors.add(CMSConstants.EMPLOYEE_SUBJECT_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_SUBJECT_REQUIRED));
			}
		}
		else{
			if((empResumeSubmissionForm.getDesignationId()!=null && !empResumeSubmissionForm.getDesignationId().equalsIgnoreCase("Non-Teaching") && empResumeSubmissionForm.getDepartmentId().isEmpty())){
				errors.add(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED,new ActionError(CMSConstants.EMPLOYEE_DEPARTMENTID_REQUIRED));
			}
		}
	}
}