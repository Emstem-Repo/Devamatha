package com.kp.cms.actions.employee;

import java.io.InputStream;
import java.net.Inet4Address;
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
import com.kp.cms.bo.admin.PfGratuityNominees;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.handlers.employee.EmployeeInfoHandlerNew;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.PfGratuityNomineesTO;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeInfoActionNew  extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(EmployeeInfoActionNew.class);
	
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	
	
	private boolean validatefutureDate(String dateString) {
		log.info("enter validatefutureDate..");
		String formattedString = CommonUtil.ConvertStringToDateFormat(
				dateString, EmployeeInfoActionNew.FROM_DATEFORMAT,
				EmployeeInfoActionNew.TO_DATEFORMAT);
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
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(EmployeeInfoActionNew.PHOTOBYTES) != null)
				session.removeAttribute(EmployeeInfoActionNew.PHOTOBYTES);
		}
	}
	
	
	public ActionForward initEmpSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInfoFormNew objform = (EmployeeInfoFormNew) form;
		cleanupEditSessionData(request);
		cleanUpPageData(objform);
		
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	public ActionForward InitEmpApplicationNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInfoFormNew objform = (EmployeeInfoFormNew) form;
		
		String ipAddress = request.getHeader("X-FORWARDED-FOR");  
		   if (ipAddress == null) {  
			   ipAddress = request.getRemoteAddr();  
		   }
		   
		//IPADDRESS CHECK
		if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
			
			//return mapping.findForward("logout");
		}
		
		cleanUpPageData(objform);
		cleanupEditSessionData(request);
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		objform.setIsCjc(isCjc);
		
		try{
		objform.setApplicationNO(null);
		}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_APPLICATIONNO);
	}
	
	public ActionForward loadResumeEmployeeInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		EmployeeInfoFormNew objform = (EmployeeInfoFormNew) form;
		cleanUpPageData(objform);
		cleanupEditSessionData(request);
		ActionMessages messages=new ActionMessages();
		try
		{
			objform.setForwardFlag(null);
		boolean flag=false;
		
		if( StringUtils.isNotEmpty(objform.getApplicationNO()))
		{
		
		objform.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		initializeQualificationAndEducation(objform);
		initializeLoanFinancial(objform);
		initializeFeeConcessionRemarks(objform);
		initializeIncentivesAchievements(objform);
		initializeDependents(objform);
		initializeImmigration(objform);
		initializePfgratuity(objform);
		setDataToForm(objform);
		setUserId(request,objform);
		flag=EmployeeInfoHandlerNew.getInstance().getApplicantDetails(objform);
		if (objform.getPhotoBytes()!= null)
		{
		HttpSession session = request.getSession(false);
			if (session != null) {
				session.setAttribute(EmployeeInfoActionNew.PHOTOBYTES, objform.getPhotoBytes());
			}	
		}
		}
		if(flag){
			objform.setForwardFlag("true");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
		}
		else
		{
			objform.setForwardFlag("false");	
			messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID_APPLICATIONNO));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_APPLICATIONNO);
		}
		}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				objform.setErrorMessage(msg);
				objform.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
		}
		
	}
	public ActionForward initEmployeeInfoAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			
			EmployeeInfoFormNew objform = (EmployeeInfoFormNew) form;
			String flag=null;
			cleanupEditSessionData(request);
			if( StringUtils.isNotEmpty(objform.getApplicationNO()))
			{
				loadResumeEmployeeInfo(mapping, form, request,response);
				flag=objform.getForwardFlag();
				if(flag.equals("false"))
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_APPLICATIONNO);	
				else
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
			}
			else
				
				initEmployeeInfo(mapping, form, request,response);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
			}
	
	
	
	
	
	
	public ActionForward initEmployeeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initEmployeeInfo in EmployeeInfoAction");
		EmployeeInfoFormNew objform = (EmployeeInfoFormNew) form;
		objform.reset();
		cleanupEditSessionData(request);
		cleanUpPageData(objform);
		setDataToForm(objform);
	//	HttpSession session=request.getSession();
		setUserId(request,objform);
		objform.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		objform.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
		initializeQualificationAndEducation(objform);
		initializeLoanFinancial(objform);
		initializeFeeConcessionRemarks(objform);
		initializeIncentivesAchievements(objform);
		initializeDependents(objform);
		initializeImmigration(objform);
		initializePfgratuity(objform);
		
		log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}

	private void initializePfgratuity(EmployeeInfoFormNew employeeInfoFormNew) {
		List<PfGratuityNomineesTO> pfGratuityList=new ArrayList<PfGratuityNomineesTO>();
		PfGratuityNomineesTO pfGratuityTO=new PfGratuityNomineesTO();
		pfGratuityTO.setNameAdressNominee("");
		pfGratuityTO.setMemberRelationship("");
		pfGratuityTO.setDobMember("");
		pfGratuityTO.setShare("");
		pfGratuityTO.setNameAdressGuardian("");
		employeeInfoFormNew.setPfGratuityListSize(String.valueOf(pfGratuityList.size()));
		pfGratuityList.add(pfGratuityTO);
		employeeInfoFormNew.getEmployeeInfoTONew().setPfGratuityNominee(pfGratuityList);
		
}
	private void cleanUpPageData(EmployeeInfoFormNew objform) {
		log.info("enter cleanUpPageData..");
		if (objform != null) {
            objform.setBankAccNo(null);
            objform.setRejoinDate(null);
            objform.setWorkLocationId(null);
            objform.setReportToId(null);
            objform.setStreamId(null);
            objform.setOfficialEmail(null);
            objform.setPayScaleId(null);
			objform.setName(null);
			objform.setAddressLine1(null);
			objform.setAddressLine2(null);
			objform.setAddressLine3(null);
			objform.setNationalityId(null);
			//objform.setZipCodeCurrent(null);
			objform.setGender(null);
			objform.setCountryId(null);
			objform.setReligionId(null);
			objform.setMaritalStatus(null);
			objform.setCity(null);
			objform.setDateOfBirth(null);
			objform.setWorkPhNo1(null);
			objform.setWorkPhNo2(null);
			objform.setWorkPhNo3(null);
			objform.setAge(null);
			objform.setMobPhone1(null);
			objform.setMobPhone2(null);
			objform.setMobPhone3(null);
			objform.setEmail(null);
			objform.setEmploymentStatus(null);
			objform.setLoanAmount(null);
			objform.setLoanDate(null);
			objform.setLoanDetails(null);
			objform.setFinancialAmount(null);
			objform.setFinancialDate(null);
			objform.setFinancialDetails(null);
			objform.setFeeConcessionAmount(null);
			objform.setFeeConcessionDate(null);
			objform.setFeeConcessionDetails(null);
			objform.setAchievementTOs(null);
			objform.setDependantDOB(null);
			objform.setDependantName(null);
			objform.setDependantRelationShip(null);
			objform.setDateOfJoining(null);
			objform.setDateOfLeaving(null);
			objform.setDateOfResignation(null);
			objform.setDateOfResignation(null);
			objform.setDetails(null);
			objform.setIncentivesAmount(null);
			objform.setIncentivesDate(null);
			objform.setIncentivesDetails(null);
			objform.setFourWheelerNo(null);
			objform.setGrade(null);
			objform.setHra(null);
			objform.setDa(null);
			objform.setGrossPay(null);
			objform.setEmContactHomeTel(null);
			objform.setEmContactMobile(null);
			objform.setEmContactName(null);
			objform.setEmContactRelationship(null);
			objform.setEmContactWorkTel(null);
			objform.setEmail(null);
			objform.setEmpLeaveAllotTo(null);
			objform.setMobile(null);
			objform.setMobileNo1(null);
			objform.setMobileNo2(null);
			objform.setMobileNo3(null);
			objform.setMaritalStatus(null);
			objform.setMobPhone2(null);
			objform.setMobPhone3(null);
			objform.setMode(null);
			objform.setAcheivementName(null);
			objform.setNoOfPublicationsNotRefered(null);
			objform.setNoOfPublicationsRefered(null);
			objform.setName(null);
			objform.setOrgAddress(null);
			objform.setOrganizationName(null);
			objform.setOtherInfo(null);
			objform.setPanno(null);
			objform.setPassportComments(null);
			objform.setPassportExpiryDate(null);
			objform.setPassportIssueDate(null);
			objform.setPassportNo(null);
			objform.setPassportReviewStatus(null);
			objform.setPassportStatus(null);
			objform.setPermanentZipCode(null);
			objform.setPfNo(null);
			objform.setHomePhone1(null);
			objform.setHomePhone2(null);
			objform.setHomePhone3(null);
			objform.setReasonOfLeaving(null);
			objform.setReasonOfLeaving(null);
			objform.setReligion(null);
			objform.setRemarkDate(null);
			objform.setRemarkDetails(null);
			objform.setEnteredBy(null);
			objform.setReservationCategory(null);
			objform.setRetirementDate(null);
			objform.setScale(null);
			objform.setStatus(null);
			objform.setTelNo(null);
			objform.setTwoWheelerNo(null);
			objform.setuId(null);
			objform.setCode(null);
			objform.setVehicleNo(null);
			objform.setVisaComments(null);
			objform.setVisaCountryId(null);
			objform.setVisaExpiryDate(null);
			objform.setVisaIssueDate(null);
			objform.setVisaNo(null);
			objform.setVisaReviewStatus(null);
			objform.setVisaStatus(null);
			objform.setLoanListSize(null);
			objform.setFinancialListSize(null);
			objform.setFeeListSize(null);
			objform.setIncentivesListSize(null);
			objform.setAchievementListSize(null);
			objform.setDependantsListSize(null);
			objform.setRemarksListSize(null);
			objform.setOtherPermanentState(null);
			objform.setOtherCurrentState(null);
			objform.setSaturdayTimeOut(null);
			objform.setSaturdayTimeOutMin(null);
			objform.setSatEndingTimeHours(null);
			objform.setSatEndingTimeMins(null);
			objform.setTimeIn(null);
			objform.setTimeInEndMIn(null);
			objform.setTimeInEnds(null);
			objform.setTimeInMin(null);
			objform.setTimeOut(null);
			objform.setTimeOutMin(null);
			objform.setHalfDayEndTime(null);
			objform.setHalfDayEndTimeMin(null);
			objform.setHalfDayStartTime(null);
			objform.setHalfDayStartTimeMin(null);
			objform.setTitleId(null);
			objform.setCurrentlyWorking(null);
			objform.setEmptypeId(null);
			objform.setTeachingStaff(null);
			objform.setRecommendedBy(null);
			objform.setCurrentAddressLine1(null);
			objform.setCurrentAddressLine2(null);
			objform.setCurrentCity(null);
			objform.setCurrentCountryId(null);
			objform.setCurrentZipCode(null);
			objform.setCurrentStateOthers(null);
			objform.setFingerPrintId(null);
			objform.setExpMonths("0");
			objform.setExpYears("0");
			objform.setRelevantExpMonths("0");
			objform.setRelevantExpYears("0");
			objform.setHighQualifForAlbum(null);
			objform.setDepartmentId(null);
			objform.setDesignationPfId(null);
			objform.setOrgAddress(null);
			objform.setDesignation(null);
			objform.setEligibilityTestNET(null);
			objform.setEligibilityTestNone(null);
			objform.setEligibilityTestSET(null);
			objform.setEligibilityTestSLET(null);
			objform.setSmartCardNo(null);
			objform.setFocusValue(null);
			objform.setOtherEligibilityTestValue(null);
			objform.setHandicappedDescription(null);
			objform.setIndustryFunctionalArea(null);
			objform.setEligibilityTestOther(null);
			objform.setEmContactAddress(null);
			objform.setEligibilityList(null);
			objform.setBloodGroup(null);
			objform.setPhotoBytes(null);
			objform.setAlbumDesignation(null);
			objform.setExtensionNumber(null);
			objform.setLicGratuityDate(null);
			objform.setLicGratuityNo(null);
			objform.setNomineePfDate(null);
			objform.setNomineePfNo(null);
			objform.setDeputaionToDepartmentId(null);
			objform.setAppointmentLetterDate(null);
			objform.setReferenceNumberForReleaving(null);
			objform.setReferenceNumber(null);
			objform.setReleavingOrderDate(null);
			objform.setMotherName(null);
			objform.setFatherName(null);
		}
		log.info("exit cleanUpPageData..");

	}
	/**
	 * @param employeeInfoFormNew
	 */
	private void initializeQualificationAndEducation(EmployeeInfoFormNew employeeInfoFormNew) {
		
		List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
		EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
		empPreviousOrgTo.setIndustryExpYears("");
		empPreviousOrgTo.setIndustryExpMonths("");
		empPreviousOrgTo.setCurrentDesignation("");
		empPreviousOrgTo.setCurrentOrganisation("");

		employeeInfoFormNew.setIndustryExpLength(String.valueOf(list.size()));
		list.add(empPreviousOrgTo);
		
		List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.setTeachingExpYears("");
		empPreviousOrgTo.setTeachingExpMonths("");
		empPreviousOrgTo.setCurrentTeachingOrganisation("");
		empPreviousOrgTo.setCurrentTeachnigDesignation("");
		employeeInfoFormNew.setTeachingExpLength(String.valueOf(teachingList.size()));
		teachingList.add(empPreviousOrgTo);
		employeeInfoFormNew.getEmployeeInfoTONew().setExperiences(list);
		employeeInfoFormNew.getEmployeeInfoTONew().setTeachingExperience(teachingList);
		
	}

private void initializeQualificationAndEducation(EmployeeInfoEditForm employeeInfoFormNew) {
		
		List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
		EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
		empPreviousOrgTo.setIndustryExpYears("");
		empPreviousOrgTo.setIndustryExpMonths("");
		empPreviousOrgTo.setCurrentDesignation("");
		empPreviousOrgTo.setCurrentOrganisation("");
		list.add(empPreviousOrgTo);
		
		List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
		empPreviousOrgTo.setTeachingExpYears("");
		empPreviousOrgTo.setTeachingExpMonths("");
		empPreviousOrgTo.setCurrentTeachingOrganisation("");
		empPreviousOrgTo.setCurrentTeachnigDesignation("");
		teachingList.add(empPreviousOrgTo);
		employeeInfoFormNew.getEmployeeInfoTONew().setExperiences(list);
		employeeInfoFormNew.getEmployeeInfoTONew().setTeachingExperience(teachingList);
		
	}

	/**
	 * 
	 */
	public  void setDataToForm(EmployeeInfoFormNew employeeInfoFormNew)throws Exception {
		boolean isCjc = CMSConstants.LINK_FOR_CJC;
		employeeInfoFormNew.setIsCjc(isCjc);
		EmployeeInfoHandlerNew.getInstance().getInitialData(employeeInfoFormNew);
		employeeInfoFormNew.setCurrentlyWorking("YES");
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
		    employeeInfoFormNew.setEligibilityList(list);
		  //Initializing Eligiblity list ends
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
			Iterator<EmpQualificationLevelTo> iterator=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo().iterator();
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
		log.info("Befinning of resetExperienceInfo of EMPLOYEE_INFO_SUBMISSION");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getExperiences()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("ExpAddMore")) {
				// add one blank to add extra one
				List<EmpPreviousOrgTo> list=employeeInfoFormNew.getEmployeeInfoTONew().getExperiences();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				/*-----------------code added by sudhir----------------*/
				empPreviousOrgTo.setIndustryFromDate("");
				empPreviousOrgTo.setIndustryToDate("");
				employeeInfoFormNew.setTeaching("false");
				employeeInfoFormNew.setIndustry("true");
				/*-----------------code added by sudhir----------------*/
				employeeInfoFormNew.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("industry_"+size);
			}
		}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);

}
	public ActionForward removeExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeExperienceInfo of EmpResumeSubmissionAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpPreviousOrgTo> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getExperiences()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("ExpAddMore")) {
				// add one blank to add extra one
				list=employeeInfoFormNew.getEmployeeInfoTONew().getExperiences();
				if(list.size()>0)
				list.remove(list.size()-1);
				employeeInfoFormNew.setIndustryExpLength(String.valueOf(list.size()-1));
				/*-----------------code added by sudhir----------------*/
				employeeInfoFormNew.setTeaching("false");
				employeeInfoFormNew.setIndustry("true");
			}
		}
		log.info("End of removeExperienceInfo of EMPLOYEE_INFO_SUBMISSION");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);

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
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("ExpAddMore")) {
				List<EmpPreviousOrgTo> list=employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				/*-----------------code added by sudhir----------------*/
				empPreviousOrgTo.setTeachingFromDate("");
				empPreviousOrgTo.setTeachingToDate("");
				employeeInfoFormNew.setTeaching("true");
				employeeInfoFormNew.setIndustry("false");
				/*-----------------code added by sudhir----------------*/
				employeeInfoFormNew.setTeachingExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("teach_"+size);
			}
		}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}
	public ActionForward removeTeachingExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpPreviousOrgTo> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience();
				list.remove(list.size()-1);
				employeeInfoFormNew.setTeachingExpLength(String.valueOf(list.size()));
				employeeInfoFormNew.setTeaching("true");
				employeeInfoFormNew.setIndustry("false");
		}
		employeeInfoFormNew.setTeachingExpLength(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EMPLOYEE_INFO_SUBMISSION");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	public ActionForward removeQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmployeeInfoFormNew employeeInfoFormNew =(EmployeeInfoFormNew)form;
		List<EmpQualificationLevelTo> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
				if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()>0){
						list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos();
						list.remove(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()-1);
				}
			}
		employeeInfoFormNew.setLevelSize(String.valueOf(list.size()));
		employeeInfoFormNew.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @return
	 * @return
	 * @return
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward saveEmpDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		EmployeeInfoFormNew employeeInfoFormNew =(EmployeeInfoFormNew)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors=employeeInfoFormNew.validate(mapping, request);
		validateDate(employeeInfoFormNew,errors);
		//validateEmployee(employeeInfoFormNew,errors);
		validateData(employeeInfoFormNew,errors);
		validateEditPhone1(employeeInfoFormNew,errors);
		validateTime(employeeInfoFormNew, errors);
		validateUnique(employeeInfoFormNew, errors);
		
		boolean flag=false;
		if(errors.isEmpty()){
			try {
				flag=EmployeeInfoHandlerNew.getInstance().saveEmp(employeeInfoFormNew);
				if(flag){
					ActionMessage message=new ActionMessage(CMSConstants.EMP_INFO_SUBMIT_CONFIRM);
					messages.add(CMSConstants.MESSAGES,message);
					saveMessages(request, messages);
					cleanUpPageData(employeeInfoFormNew);
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.EMP_INFO_SUBMIT_CONFIRM);
				}else{
					messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
					saveMessages(request, messages);
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
				}
			} catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					employeeInfoFormNew.setErrorMessage(msg);
					employeeInfoFormNew.setErrorStack(exception.getMessage());
					cleanupEditSessionData(request);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else
				throw exception;
			}
				
		}else{
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
		}
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	private void validateUnique(EmployeeInfoFormNew employeeInfoFormNew, ActionErrors errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionErrors();
		if (employeeInfoFormNew.getuId() != null && !StringUtils.isEmpty(employeeInfoFormNew.getuId().trim()) ) {
			boolean duplicateUid;
			try {
				duplicateUid = EmployeeInfoHandlerNew.getInstance().checkUidUnique(employeeInfoFormNew.getuId());
			
			if (!duplicateUid) {
				errors.add(CMSConstants.ADDEMPLOYEE_UID_NOTUNIQUE,
						new ActionError(CMSConstants.ADDEMPLOYEE_UID_NOTUNIQUE));
				
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (employeeInfoFormNew.getFingerPrintId() != null && !StringUtils.isEmpty(employeeInfoFormNew.getFingerPrintId().trim()) ) {
			boolean duplicateFingerPrintId;
			try {
				duplicateFingerPrintId = EmployeeInfoHandlerNew.getInstance().checkFingerPrintIdUnique(employeeInfoFormNew.getFingerPrintId());
			
			if (!duplicateFingerPrintId) {
				errors.add(CMSConstants.ADDEMPLOYEE_FINGERPRINTID_NOTUNIQUE,
						new ActionError(CMSConstants.ADDEMPLOYEE_FINGERPRINTID_NOTUNIQUE));
				
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (employeeInfoFormNew.getCode()!= null && !StringUtils.isEmpty(employeeInfoFormNew.getCode().trim()) ) {
			boolean duplicatecode;
			try {
				duplicatecode = EmployeeInfoHandlerNew.getInstance().checkCodeUnique(employeeInfoFormNew.getCode());
			
			if (!duplicatecode) {
				errors.add(CMSConstants.ADDEMPLOYEE_CODE_NOTUNIQUE,
						new ActionError(CMSConstants.ADDEMPLOYEE_CODE_NOTUNIQUE));
				
			}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
	/**
	 * @param employeeInfoFormNew
	 * @param errors
	 */
	@SuppressWarnings("deprecation")
	
	private void validateEmployee(EmployeeInfoFormNew employeeInfoFormNew, ActionErrors errors) {
		/*List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>(); 
		if(employeeInfoFormNew.isCurrentlyWorking()){
			if(employeeInfoFormNew.getEmployeeInfoTONew().getExperiences()!=null)
				list=employeeInfoFormNew.getEmployeeInfoTONew().getExperiences();
			Iterator<EmpPreviousOrgTo> iterator=list.iterator();
			while(iterator.hasNext()){
				EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
				if(empPreviousOrgTo!=null){
					if(empPreviousOrgTo.getIndustryExpYears()!=null && empPreviousOrgTo.getIndustryExpYears().isEmpty())
						if (errors.get(CMSConstants.INDUSTRY_EXP_NOT_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_NOT_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_NOT_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_NOT_EMPTY));
					
					if(empPreviousOrgTo.getIndustryExpMonths()!=null && empPreviousOrgTo.getIndustryExpMonths().isEmpty())
						if (errors.get(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_MON_NOT_EMPTY));
					
					if(empPreviousOrgTo.getCurrentDesignation()!=null && empPreviousOrgTo.getCurrentDesignation().isEmpty())
						if (errors.get(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_DESIGNATION_EMPTY));
					
					if(empPreviousOrgTo.getCurrentOrganisation()!=null && empPreviousOrgTo.getCurrentOrganisation().isEmpty())
						if (errors.get(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY) != null&& !errors.get(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY).hasNext()) 
						errors.add(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY, new ActionError(CMSConstants.INDUSTRY_EXP_ORGANIZATION_EMPTY));
						
				}
			}
			
			list=null;
			if(employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience()!=null){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getTeachingExperience();
				Iterator<EmpPreviousOrgTo> iterator2=list.iterator();
				while(iterator.hasNext()){
					EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
					if(empPreviousOrgTo!=null){
						if(empPreviousOrgTo.getTeachingExpYears()!=null && empPreviousOrgTo.getTeachingExpYears().isEmpty())
							if (errors.get(CMSConstants.TEACHING_EXP_NOT_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_NOT_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_NOT_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_NOT_EMPTY));
						
						if(empPreviousOrgTo.getTeachingExpMonths()!=null && empPreviousOrgTo.getTeachingExpMonths().isEmpty())
							if (errors.get(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_MON_NOT_EMPTY));
						
						if(empPreviousOrgTo.getCurrentTeachnigDesignation()!=null && empPreviousOrgTo.getCurrentTeachnigDesignation().isEmpty())
							if (errors.get(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_DESIGNATION_EMPTY));
						
						if(empPreviousOrgTo.getCurrentTeachingOrganisation()!=null && empPreviousOrgTo.getCurrentTeachingOrganisation().isEmpty())
							if (errors.get(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY) != null&& !errors.get(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY).hasNext()) 
							errors.add(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY, new ActionError(CMSConstants.TEACHING_EXP_ORGANIZATION_EMPTY));
					}
				}
			}
		}*/
		
		/*List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null)
			level=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationFixedTo();
		if(level!=null){
			Iterator<EmpQualificationLevelTo> iterator=level.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo to=iterator.next();
				if(to!=null){
					if(to.getCourse()!=null && to.getCourse().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_COURSE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_COURSE_REQUIRED));
					
					if(to.getSpecialization()!=null && to.getSpecialization().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED));
					
					if(to.getYearOfComp()!=null && to.getYearOfComp().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED, new ActionError(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED));
					
					if(to.getGrade()!=null && to.getGrade().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_GRADE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_GRADE_REQUIRED));
					
					if(to.getInstitute()!=null && to.getInstitute().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED));
				}
			}
		}
		
		level=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null)
			level=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos();
		if(level!=null){
			Iterator<EmpQualificationLevelTo> iterator=level.iterator();
			while(iterator.hasNext()){
				EmpQualificationLevelTo to=iterator.next();
				if(to!=null){
					if(to.getCourse()!=null && to.getCourse().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_COURSE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_COURSE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_COURSE_REQUIRED));
					
					if(to.getSpecialization()!=null && to.getSpecialization().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_SPECIALIZATION_REQUIRED));
					
					if(to.getYear()!=null && to.getYear().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED, new ActionError(CMSConstants.EMPLOYEE_YEARCOMPREQUIRED));
					
					if(to.getGrade()!=null && to.getGrade().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_GRADE_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_GRADE_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_GRADE_REQUIRED));
					
					if(to.getInstitute()!=null && to.getInstitute().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED) != null&& !errors.get(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED).hasNext())
							errors.add(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED, new ActionError(CMSConstants.EMPLOYEE_INS_UNI_REQUIRED));
					
					if(to.getEducationId()!=null && to.getEducationId().isEmpty())
						if (errors.get(CMSConstants.EMPLOYEE_QUAL_LEVEL_REQ) != null&& !errors.get(CMSConstants.EMPLOYEE_QUAL_LEVEL_REQ).hasNext())
							errors.add(CMSConstants.EMPLOYEE_QUAL_LEVEL_REQ, new ActionError(CMSConstants.EMPLOYEE_QUAL_LEVEL_REQ));
				}
			}
		}*/
	}

	
	
	@SuppressWarnings("deprecation")
	private void validateData(EmployeeInfoFormNew employeeInfoFormNew,ActionErrors errors) {
		try{
		InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
		int maxPhotoSize=0;
		Properties prop=new Properties();
		prop.load(propStream);
		maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		FormFile file=null;
		boolean photoError = false;
		if(employeeInfoFormNew.getEmpPhoto()!=null)
			file=employeeInfoFormNew.getEmpPhoto();
		
		if( file!=null && maxPhotoSize< file.getFileSize()){
			if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
				errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
				photoError = true;
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
					photoError = true;
				}
			}
		}
		if(!photoError && file!=null && file.getFileData().length !=0){
			employeeInfoFormNew.setPhoto(file.getFileData());
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	private void validateEditPhone1(EmployeeInfoFormNew employeeInfoFormNew, ActionErrors errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionErrors();
		

		 if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements().isEmpty())
		   {
			  
			   List<EmpAcheivementTO> depTo = employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements();
				Iterator<EmpAcheivementTO> itr = depTo.iterator();
				EmpAcheivementTO empDepTO;
				while (itr.hasNext()) {
					empDepTO = itr.next();
					if (empDepTO.getDetails() != null && !empDepTO.getDetails().isEmpty() && empDepTO.getDetails().trim().length() > 500)  {
						if (errors.get(CMSConstants.EMPLOYEE_ACHIEVEMENT_INVALID) != null
								&& !errors.get(CMSConstants.EMPLOYEE_ACHIEVEMENT_INVALID)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.EMPLOYEE_ACHIEVEMENT_INVALID);
							errors.add(CMSConstants.EMPLOYEE_ACHIEVEMENT_INVALID, error);
						}
					}
						}
					}
			

		if (employeeInfoFormNew.getEmContactHomeTel() != null && !employeeInfoFormNew.getEmContactHomeTel().isEmpty() && employeeInfoFormNew.getEmContactHomeTel().trim().length() > 10)  {
			if (errors.get(CMSConstants.EMPLOYEE_EMERGENCY_HOME_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_EMERGENCY_HOME_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_EMERGENCY_HOME_INVALID);
				errors.add(CMSConstants.EMPLOYEE_EMERGENCY_HOME_INVALID, error);
			}
		}
		if (employeeInfoFormNew.getEmContactWorkTel() != null && !employeeInfoFormNew.getEmContactWorkTel().isEmpty() && employeeInfoFormNew.getEmContactWorkTel().trim().length() > 10)  {
			if (errors.get(CMSConstants.EMPLOYEE_EMERGENCY_WORK_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_EMERGENCY_WORK_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_EMERGENCY_WORK_INVALID);
				errors.add(CMSConstants.EMPLOYEE_EMERGENCY_WORK_INVALID, error);
			}
		}
		if (employeeInfoFormNew.getEmContactMobile() != null && !employeeInfoFormNew.getEmContactMobile().isEmpty() && employeeInfoFormNew.getEmContactMobile().trim().length() > 10) {
			if (errors.get(CMSConstants.EMPLOYEE_EMERGENCY_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_EMERGENCY_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_EMERGENCY_MOBILE_INVALID);
				errors.add(CMSConstants.EMPLOYEE_EMERGENCY_MOBILE_INVALID, error);
			}
		}
				
		if(employeeInfoFormNew.getIsCjc().equals(true))
		{
			if (employeeInfoFormNew.getCurrentAddressLine1() != null && !employeeInfoFormNew.getCurrentAddressLine1().isEmpty() && employeeInfoFormNew.getCurrentAddressLine1().trim().length() > 100)  {
		
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT) != null
					&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT)
							.hasNext()) {
					ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT, error);
			}
		}
			if (employeeInfoFormNew.getCurrentAddressLine2() != null && !employeeInfoFormNew.getCurrentAddressLine2().isEmpty() && employeeInfoFormNew.getCurrentAddressLine2().trim().length() > 100)  {
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT, error);
				}
			}
			if (employeeInfoFormNew.getAddressLine1() != null && !employeeInfoFormNew.getAddressLine1().isEmpty() && employeeInfoFormNew.getAddressLine1().trim().length() > 100)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT, error);
				}
			}
			if (employeeInfoFormNew.getAddressLine2() != null && !employeeInfoFormNew.getAddressLine2().isEmpty() && employeeInfoFormNew.getAddressLine2().trim().length() > 100)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_ABOVELIMIT) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_ABOVELIMIT)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE2_ABOVELIMIT);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE2_ABOVELIMIT, error);
				}
			}
		}else
		{
		if (employeeInfoFormNew.getAlbumDesignation() == null || employeeInfoFormNew.getAlbumDesignation().isEmpty())  {
					if (errors.get(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM) != null
							&& !errors.get(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM);
						errors.add(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM, error);
					}
				}
			
		if (employeeInfoFormNew.getAddressLine1() != null && !employeeInfoFormNew.getAddressLine1().isEmpty() && employeeInfoFormNew.getAddressLine1().trim().length() > 35)  {
			if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID);
				errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID, error);
			}
		}
		if (employeeInfoFormNew.getAddressLine2() != null && !employeeInfoFormNew.getAddressLine2().isEmpty() && employeeInfoFormNew.getAddressLine2().trim().length() > 40)  {
			if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID);
				errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID, error);
			}
		}
		
		if (employeeInfoFormNew.getCurrentAddressLine1() != null && !employeeInfoFormNew.getCurrentAddressLine1().isEmpty() && employeeInfoFormNew.getCurrentAddressLine1().trim().length() > 35)  {
			if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID);
				errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID, error);
			}
		}
		if (employeeInfoFormNew.getCurrentAddressLine2() != null && !employeeInfoFormNew.getCurrentAddressLine2().isEmpty() && employeeInfoFormNew.getCurrentAddressLine2().trim().length() > 40)  {
			if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID);
				errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID, error);
			}
		}
		}
			if (employeeInfoFormNew.getHomePhone3() != null	&& !employeeInfoFormNew.getHomePhone3().isEmpty() && employeeInfoFormNew.getHomePhone3().trim().length() > 10) {
				if (errors.get(CMSConstants.EMPLOYEE_HOME_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_HOME_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_HOME_INVALID);
					errors.add(CMSConstants.EMPLOYEE_HOME_INVALID, error);
				}
		}
			/*if (employeeInfoFormNew.getHomePhone1() != null	&& employeeInfoFormNew.getHomePhone1().trim().length() != 10) {
				if (errors.get(CMSConstants.EMPLOYEE_HOME_COUNTRY_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_HOME_COUNTRY_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_HOME_COUNTRY_INVALID);
					errors.add(CMSConstants.EMPLOYEE_HOME_COUNTRY_INVALID, error);
				}
		}
			if (employeeInfoFormNew.getHomePhone2() != null	&& employeeInfoFormNew.getHomePhone2().trim().length() != 10) {
				if (errors.get(CMSConstants.EMPLOYEE_HOME_STATE_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_HOME_STATE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_HOME_STATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_HOME_STATE_INVALID, error);
				}
		}*/	
			if (employeeInfoFormNew.getWorkPhNo3() != null && !employeeInfoFormNew.getWorkPhNo3().isEmpty() && employeeInfoFormNew.getWorkPhNo3().trim().length() > 10) {
				if (errors.get(CMSConstants.EMPLOYEE_WORK_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_WORK_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_WORK_INVALID);
					errors.add(CMSConstants.EMPLOYEE_WORK_INVALID, error);
				}
		}
		/*	if (employeeInfoFormNew.getWorkPhNo2() != null && employeeInfoFormNew.getWorkPhNo2().trim().length() != 10) {
				if (errors.get(CMSConstants.EMPLOYEE_WORK_STATE_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_WORK_STATE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_WORK_STATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_WORK_STATE_INVALID, error);
				}
		}
			if (employeeInfoFormNew.getWorkPhNo1() != null && employeeInfoFormNew.getWorkPhNo1().trim().length() != 10) {
				if (errors.get(CMSConstants.EMPLOYEE_WORK_COUNTRY_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_WORK_COUNTRY_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_WORK_COUNTRY_INVALID);
					errors.add(CMSConstants.EMPLOYEE_WORK_COUNTRY_INVALID, error);
				}
		}*/
			if (employeeInfoFormNew.getMobileNo1() != null && !StringUtils.isEmpty(employeeInfoFormNew.getMobileNo1())
					&& employeeInfoFormNew.getMobileNo1().trim().length() != 10) {
				if (errors.get(CMSConstants.EMPLOYEE_MOBILE_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_MOBILE_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_MOBILE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_MOBILE_INVALID, error);
				}
		}
			if (employeeInfoFormNew.getuId() != null && !employeeInfoFormNew.getuId().isEmpty() && employeeInfoFormNew.getuId().trim().length() > 15)  {
				if (errors.get(CMSConstants.EMPLOYEE_UID_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_UID_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_UID_INVALID);
					errors.add(CMSConstants.EMPLOYEE_UID_INVALID, error);
				}
			}
		    if (employeeInfoFormNew.getPanno() != null && !employeeInfoFormNew.getPanno().isEmpty() && employeeInfoFormNew.getPanno().trim().length() > 10)  {
				if (errors.get(CMSConstants.EMPLOYEE_PANNO_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_PANNO_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_PANNO_INVALID);
					errors.add(CMSConstants.EMPLOYEE_PANNO_INVALID, error);
				}
			}
		    if (employeeInfoFormNew.getPfNo() != null && !employeeInfoFormNew.getPfNo().isEmpty() && employeeInfoFormNew.getPfNo().trim().length() > 25)  {
				if (errors.get(CMSConstants.EMPLOYEE_PFNO_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_PFNO_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_PFNO_INVALID);
					errors.add(CMSConstants.EMPLOYEE_PFNO_INVALID, error);
				}
			}
		    if (employeeInfoFormNew.getFingerPrintId() != null && !employeeInfoFormNew.getFingerPrintId().isEmpty() && employeeInfoFormNew.getFingerPrintId().trim().length() > 30)  {
				if (errors.get(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID);
					errors.add(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID, error);
				}
			}
		    if (employeeInfoFormNew.getOtherInfo() != null && !employeeInfoFormNew.getOtherInfo().isEmpty() && employeeInfoFormNew.getOtherInfo().trim().length() > 500)  {
				if (errors.get(CMSConstants.EMPLOYEE_OTHERINFO_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_OTHERINFO_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_OTHERINFO_INVALID);
					errors.add(CMSConstants.EMPLOYEE_OTHERINFO_INVALID, error);
				}
			}
		    Iterator<PfGratuityNomineesTO> itr=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().iterator();
			while (itr.hasNext()) {
				PfGratuityNomineesTO pfTo = (PfGratuityNomineesTO) itr.next();
				
				 if (pfTo.getNameAdressNominee() != null && !pfTo.getNameAdressNominee().isEmpty() && pfTo.getNameAdressNominee().trim().length() > 500)  {
						if (errors.get(CMSConstants.EMPLOYEE_NAMENOMINEE_INVALID) != null
								&& !errors.get(CMSConstants.EMPLOYEE_NAMENOMINEE_INVALID)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.EMPLOYEE_NAMENOMINEE_INVALID);
							errors.add(CMSConstants.EMPLOYEE_NAMENOMINEE_INVALID, error);
						}
					}
				    if (pfTo.getNameAdressGuardian() != null && !pfTo.getNameAdressGuardian().isEmpty() && pfTo.getNameAdressGuardian().trim().length() > 500)  {
						if (errors.get(CMSConstants.EMPLOYEE_NAMEGUARDIAN_INVALID) != null
								&& !errors.get(CMSConstants.EMPLOYEE_NAMEGUARDIAN_INVALID)
										.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.EMPLOYEE_NAMEGUARDIAN_INVALID);
							errors.add(CMSConstants.EMPLOYEE_NAMEGUARDIAN_INVALID, error);
						}
					}
				
		       }
			
		log.info("exit validateEditPhone..");
	}

	
	
	
	/**
	 * @param employeeInfoFormNew
	 * @param errors
	 */
	@SuppressWarnings("deprecation")
	private void validateDate(EmployeeInfoFormNew employeeInfoFormNew,ActionErrors errors) {
		
		
		if(employeeInfoFormNew.getDateOfBirth()!=null && !employeeInfoFormNew.getDateOfBirth().isEmpty() ){

			if (CommonUtil.isValidDate(employeeInfoFormNew.getDateOfBirth())) {
				boolean isValid = validatefutureDate(employeeInfoFormNew.getDateOfBirth());
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
		
		
		/*if(employeeInfoFormNew.getDependantDOB()!=null && !employeeInfoFormNew.getDependantDOB().isEmpty() ){

			if (CommonUtil.isValidDate(employeeInfoFormNew.getDependantDOB())) {
				boolean isValid = validatefutureDate(employeeInfoFormNew.getDependantDOB());
				if (!isValid) {
					if (errors.get(CMSConstants.DEPENDENTDOB_FUTURE_DATE) != null && !errors.get(CMSConstants.DEPENDENTDOB_FUTURE_DATE).hasNext()) {
						errors.add(CMSConstants.DEPENDENTDOB_FUTURE_DATE, new ActionError(CMSConstants.DEPENDENTDOB_FUTURE_DATE));
					}
				}
			} else {
				if (errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID).hasNext()) {
					errors.add(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID, new ActionError(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID));
				}
			}
		}*/
		
	
		 if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses().isEmpty())
		   {
			  
			   List<EmpDependentsTO> depTo = employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses();
				Iterator<EmpDependentsTO> itr = depTo.iterator();
				EmpDependentsTO empDepTO;
				while (itr.hasNext()) {
					empDepTO = itr.next();
					if(empDepTO.getDependantDOB()!=null && !empDepTO.getDependantDOB().isEmpty() ){

						if (CommonUtil.isValidDate(empDepTO.getDependantDOB())) {
							boolean isValid = validatefutureDate(empDepTO.getDependantDOB());
							if (!isValid) {
								if (errors.get(CMSConstants.DEPENDENTDOB_FUTURE_DATE) != null && !errors.get(CMSConstants.DEPENDENTDOB_FUTURE_DATE).hasNext()) {
									errors.add(CMSConstants.DEPENDENTDOB_FUTURE_DATE, new ActionError(CMSConstants.DEPENDENTDOB_FUTURE_DATE));
								}
							}
						} else {
							if (errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID).hasNext()) {
								errors.add(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID, new ActionError(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID));
							}
						}
					}
				}
				
			}
		
		
	   if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan().isEmpty())
	   {
		  
		   List<EmpLoanTO> loanTo = employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan();
			Iterator<EmpLoanTO> itr = loanTo.iterator();
			EmpLoanTO empLoanTO;
			while (itr.hasNext()) {
				empLoanTO = itr.next();
				if (empLoanTO.getLoanDate()!=null && !empLoanTO.getLoanDate().isEmpty() && !CommonUtil.isValidDate(empLoanTO.getLoanDate()))
					 {
					ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_LOANDATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_LOANDATE_INVALID,error);
				}
			}
			
		}
		   
			
	   if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession().isEmpty())
	   {
		    List<EmpFeeConcessionTO> empFeeConcession = employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession();
			Iterator<EmpFeeConcessionTO> itr = empFeeConcession.iterator();
			EmpFeeConcessionTO empFeeConcessionTo;
			while (itr.hasNext()) {
				empFeeConcessionTo = itr.next();
				if (empFeeConcessionTo.getFeeConcessionDate()!=null && !empFeeConcessionTo.getFeeConcessionDate().isEmpty() &&  !CommonUtil.isValidDate(empFeeConcessionTo.getFeeConcessionDate()))
					 {
					ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_FEECONCESSIONDATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_FEECONCESSIONDATE_INVALID,error);
				}
			}
			
		}
	   if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial().isEmpty())
	   {
		    List<EmpFinancialTO> empFinancial = employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial();
			Iterator<EmpFinancialTO> itr = empFinancial.iterator();
			EmpFinancialTO empFinancialTO;
			while (itr.hasNext()) {
				empFinancialTO = itr.next();
				if (empFinancialTO.getFinancialDate()!=null && !empFinancialTO.getFinancialDate().isEmpty() && !CommonUtil.isValidDate(empFinancialTO.getFinancialDate()))
					 {
					ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_FINANCIALDATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_FINANCIALDATE_INVALID,error);
				}
			}
		}
		
	   if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives().isEmpty())
	   {
		    List<EmpIncentivesTO> empIncentives = employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives();
			Iterator<EmpIncentivesTO> itr = empIncentives.iterator();
			EmpIncentivesTO empIncentivesTO;
			while (itr.hasNext()) {
				empIncentivesTO = itr.next();
				if (empIncentivesTO.getIncentivesDate()!=null && !empIncentivesTO.getIncentivesDate().isEmpty() && !CommonUtil.isValidDate(empIncentivesTO.getIncentivesDate()))
					 {
					ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_INCENTIVESDATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_INCENTIVESDATE_INVALID,error);
				}
			}
			
		}
	   if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks().isEmpty())
	   {
		    List<EmpRemarksTO> empRemarks = employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks();
			Iterator<EmpRemarksTO> itr = empRemarks.iterator();
			EmpRemarksTO empRemarksTO;
			while (itr.hasNext()) {
				empRemarksTO = itr.next();
				if (empRemarksTO.getRemarkDate()!=null && !empRemarksTO.getRemarkDate().isEmpty() && !CommonUtil.isValidDate(empRemarksTO.getRemarkDate()))
					 {
					ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_REMARKDATE_INVALID);
					errors.add(CMSConstants.EMPLOYEE_REMARKDATE_INVALID,error);
				}
			}
			
		}
	   
		
		if(employeeInfoFormNew.getDateOfJoining()!=null && !employeeInfoFormNew.getDateOfJoining().isEmpty() && !CommonUtil.isValidDate(employeeInfoFormNew.getDateOfJoining())){
			if (errors.get(CMSConstants.EMPLOYEE_JOINDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_JOINDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_JOINDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_JOINDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getDateOfLeaving()!=null && !employeeInfoFormNew.getDateOfLeaving().isEmpty() && !CommonUtil.isValidDate(employeeInfoFormNew.getDateOfLeaving())){
			if (errors.get(CMSConstants.EMPLOYEE_LEAVINGDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_LEAVINGDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_LEAVINGDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_LEAVINGDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getDateOfResignation()!=null && !employeeInfoFormNew.getDateOfResignation().isEmpty() &&  !CommonUtil.isValidDate(employeeInfoFormNew.getDateOfResignation())){
			if (errors.get(CMSConstants.EMPLOYEE_RESIGNATIONDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_RESIGNATIONDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_RESIGNATIONDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_RESIGNATIONDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getDependantDOB()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getDependantDOB())){
			if (errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getPassportExpiryDate()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getPassportExpiryDate())){
			if (errors.get(CMSConstants.EMPLOYEE_PASSEXPDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_PASSEXPDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_PASSEXPDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_PASSEXPDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getPassportIssueDate()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getPassportIssueDate())){
			if (errors.get(CMSConstants.EMPLOYEE_PASSISSUEDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_PASSISSUEDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_PASSISSUEDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_PASSISSUEDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getRejoinDate()!=null && !employeeInfoFormNew.getRejoinDate().isEmpty() && !CommonUtil.isValidDate(employeeInfoFormNew.getRejoinDate())){
			if (errors.get(CMSConstants.EMPLOYEE_REJOINDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_REJOINDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_REJOINDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_REJOINDATE_INVALID));
				}
		}
		
		/*if(employeeInfoFormNew.getRetirementDate()!=null && !employeeInfoFormNew.getRetirementDate().isEmpty() && !CommonUtil.isValidDate(employeeInfoFormNew.getRetirementDate())){
			if (errors.get(CMSConstants.EMPLOYEE_RETIREMENTDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_RETIREMENTDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_RETIREMENTDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_RETIREMENTDATE_INVALID));
				}
		}*/
		if(employeeInfoFormNew.getVisaExpiryDate()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getVisaExpiryDate())){
			if (errors.get(CMSConstants.EMPLOYEE_VISAEXPDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_VISAEXPDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_VISAEXPDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_VISAEXPDATE_INVALID));
				}
		}
		if(employeeInfoFormNew.getVisaIssueDate()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getVisaIssueDate())){
			if (errors.get(CMSConstants.EMPLOYEE_VISSAISSUEDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_VISSAISSUEDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_VISSAISSUEDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_VISSAISSUEDATE_INVALID));
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
	public ActionForward addQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
		log.info("Befinning of addQualificationLevel of EmpResumeSubmissionAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpQualificationLevelTo> list=null;
		EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
		empQualificationLevelTo.setEducationId("");
		empQualificationLevelTo.setCourse("");
		empQualificationLevelTo.setSpecialization("");
		empQualificationLevelTo.setGrade("");
		empQualificationLevelTo.setInstitute("");
		empQualificationLevelTo.setYearOfComp("");
		
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("ExpAddMore")) {
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpQualificationLevelTos();
				list.add(empQualificationLevelTo);
				employeeInfoFormNew.setMode(null);
			}
		}
		}else{
			list=new ArrayList<EmpQualificationLevelTo>();
			list.add(empQualificationLevelTo);
			employeeInfoFormNew.setMode(null);
		}
		employeeInfoFormNew.setLevelSize(String.valueOf(list.size()));
		employeeInfoFormNew.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
		String size=String.valueOf(list.size()-1);
		employeeInfoFormNew.setFocusValue("course_"+size);
		log.info("End of addQualificationLevel of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	
	public ActionForward getWorkTimeEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoFormNew empForm = (EmployeeInfoFormNew) form;
		try {
			getEmpLeaveList(mapping, form, request, response);
		String empTypeId=empForm.getEmptypeId();
		List<EmpTypeTo> empTypeToList=EmployeeInfoHandlerNew.getInstance().getWorkTimeEntry(empTypeId);
		empForm.getEmployeeInfoTONew().setEmpTypeToList(empTypeToList);
		
//		empForm.setEmpTypeTo(empTypeToList);
		}
		
		catch (Exception e) {
			log.error("Error occured ",e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	/*public ActionForward getEmpLeaveList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoFormNew empForm = (EmployeeInfoFormNew) form;
		try {
		String empTypeId=empForm.getEmptypeId();
		List<EmpLeaveAllotTO> empLeaveToList=EmployeeInfoHandlerNew.getInstance().getEmpLeaveList(empTypeId);
		empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
		//empForm.setEmpLeaveAllotTo(empLeaveToList);
		request.setAttribute(CMSConstants.OPTION_MAP, empForm.getEmployeeInfoTONew().getEmpLeaveToList());
		}
		
		catch (Exception e) {
			log.error("Error occured ",e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
		return mapping.findForward("ajaxResponseForEmpLeave");
	}*/
	
	public ActionForward getEmpLeaveList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoFormNew empForm = (EmployeeInfoFormNew) form;
		try {
			String empTypeId=empForm.getEmptypeId();
			List<EmpLeaveAllotTO> empLeaveToList=EmployeeInfoHandlerNew.getInstance().getEmpLeaveList(empTypeId);
			empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
			ActionErrors errors = new ActionErrors();
			//empForm.setEmpLeaveAllotTo(empLeaveToList);
			validateData(empForm, errors);
		}catch (Exception e) {
			log.error("Error occured ",e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	public ActionForward removeEmpLeaveList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoFormNew empForm = (EmployeeInfoFormNew) form;
		try {
		ActionErrors errors = new ActionErrors();
		validateData(empForm, errors );
		//if(empTypeId!=null && empTypeId!="")
		//{
		List<EmpLeaveAllotTO> empLeaveToList=null;
		empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
		//empForm.setEmpLeaveAllotTo(empLeaveToList);
		//}
		}
		catch (Exception e) {
			log.error("Error occured ",e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
private void initializeLoanFinancial(EmployeeInfoFormNew employeeInfoFormNew) {
		
		List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
		EmpLoanTO emploanTo=new EmpLoanTO();
		emploanTo.setLoanAmount("");
		emploanTo.setLoanDate("");
		emploanTo.setLoanDetails("");
		employeeInfoFormNew.setLoanListSize(String.valueOf(list.size()));
		list.add(emploanTo);
		
		

		List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
		EmpFinancialTO empFinancialTO=new EmpFinancialTO();
		empFinancialTO.setFinancialAmount("");
		empFinancialTO.setFinancialDate("");
		empFinancialTO.setFinancialDetails("");
		employeeInfoFormNew.setFinancialListSize(String.valueOf(flist.size()));
		flist.add(empFinancialTO);
		employeeInfoFormNew.getEmployeeInfoTONew().setEmpLoan(list);
		employeeInfoFormNew.getEmployeeInfoTONew().setEmpFinancial(flist);
		
	}
private void initializeFeeConcessionRemarks(EmployeeInfoFormNew employeeInfoFormNew) {
	
	List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
	EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
	empFeeConcessionTO.setFeeConcessionAmount("");
	empFeeConcessionTO.setFeeConcessionDate("");
	empFeeConcessionTO.setFeeConcessionDetails("");
	employeeInfoFormNew.setFeeListSize(String.valueOf(list.size()));
	list.add(empFeeConcessionTO);
	
	List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
	EmpRemarksTO empRemarksTO=new EmpRemarksTO();
	empRemarksTO.setEnteredBy("");
	empRemarksTO.setRemarkDate("");
	empRemarksTO.setRemarkDetails("");
	employeeInfoFormNew.setRemarksListSize(String.valueOf(list.size()));
	flist.add(empRemarksTO);
	
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpFeeConcession(list);
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpRemarks(flist);
	
}
private void initializeIncentivesAchievements(EmployeeInfoFormNew employeeInfoFormNew) {
	
	List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
	EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
	empIncentivesTO.setIncentivesAmount("");
	empIncentivesTO.setIncentivesDate("");
	empIncentivesTO.setIncentivesDetails("");
	employeeInfoFormNew.setIncentivesListSize(String.valueOf(list.size()));
	list.add(empIncentivesTO);
	

	List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
	EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
	empAcheivementTO.setAcheivementName("");
	empAcheivementTO.setDetails("");
	employeeInfoFormNew.setAchievementListSize(String.valueOf(flist.size()));
	flist.add(empAcheivementTO);
	
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpIncentives(list);
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpAcheivements(flist);
	
}

private void initializeDependents(EmployeeInfoFormNew employeeInfoFormNew) {
	
	List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
	EmpDependentsTO empDependentsTO=new EmpDependentsTO();
	empDependentsTO.setDependantDOB("");
	empDependentsTO.setDependantName("");
	empDependentsTO.setDependentRelationship("");
	employeeInfoFormNew.setDependantsListSize(String.valueOf(empDependentses.size()));
	empDependentses.add(empDependentsTO);
	
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
	
}
private void initializeImmigration(EmployeeInfoFormNew employeeInfoFormNew) {
	
	List<EmpImmigrationTO> empImmigration=new ArrayList<EmpImmigrationTO>();
	EmpImmigrationTO empImmigrationTO=new EmpImmigrationTO();
	empImmigrationTO.setPassportComments("");
	empImmigrationTO.setPassportCountryId("");
	empImmigrationTO.setPassportExpiryDate("");
	empImmigrationTO.setPassportIssueDate("");
	empImmigrationTO.setPassportNo("");
	empImmigrationTO.setPassportReviewStatus("");
	empImmigrationTO.setPassportStatus("");
	
	empImmigrationTO.setVisaComments("");
	empImmigrationTO.setVisaCountryId("");
	empImmigrationTO.setVisaExpiryDate("");
	empImmigrationTO.setVisaIssueDate("");
	empImmigrationTO.setVisaNo("");
	empImmigrationTO.setVisaReviewStatus("");
	empImmigrationTO.setVisaStatus("");
	employeeInfoFormNew.setImmigrationListSize(String.valueOf(empImmigration.size()));	
	empImmigration.add(empImmigrationTO);
	employeeInfoFormNew.getEmployeeInfoTONew().setEmpImmigration(empImmigration);
	
}

private void initializeLoanFinancial(EmployeeInfoEditForm empInfoFormEdit) {
	
	List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
	EmpLoanTO emploanTo=new EmpLoanTO();
	emploanTo.setLoanAmount("");
	emploanTo.setLoanDate("");
	emploanTo.setLoanDetails("");
	empInfoFormEdit.setLoanListSize(String.valueOf(list.size()));
	list.add(emploanTo);
	
	

	List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
	EmpFinancialTO empFinancialTO=new EmpFinancialTO();
	empFinancialTO.setFinancialAmount("");
	empFinancialTO.setFinancialDate("");
	empFinancialTO.setFinancialDetails("");
	empInfoFormEdit.setFinancialListSize(String.valueOf(flist.size()));
	flist.add(empFinancialTO);
	empInfoFormEdit.getEmployeeInfoTONew().setEmpLoan(list);
	empInfoFormEdit.getEmployeeInfoTONew().setEmpFinancial(flist);
	
}
private void initializeFeeConcessionRemarks(EmployeeInfoEditForm empInfoFormEdit) {

List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
empFeeConcessionTO.setFeeConcessionAmount("");
empFeeConcessionTO.setFeeConcessionDate("");
empFeeConcessionTO.setFeeConcessionDetails("");
list.add(empFeeConcessionTO);

List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
EmpRemarksTO empRemarksTO=new EmpRemarksTO();
empRemarksTO.setEnteredBy("");
empRemarksTO.setRemarkDate("");
empRemarksTO.setRemarkDetails("");
flist.add(empRemarksTO);

empInfoFormEdit.getEmployeeInfoTONew().setEmpFeeConcession(list);
empInfoFormEdit.getEmployeeInfoTONew().setEmpRemarks(flist);

}
private void initializeIncentivesAchievements(EmployeeInfoEditForm empInfoFormEdit) {

List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
empIncentivesTO.setIncentivesAmount("");
empIncentivesTO.setIncentivesDate("");
empIncentivesTO.setIncentivesDetails("");
list.add(empIncentivesTO);


List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
empAcheivementTO.setAcheivementName("");
empAcheivementTO.setDetails("");
flist.add(empAcheivementTO);

empInfoFormEdit.getEmployeeInfoTONew().setEmpIncentives(list);
empInfoFormEdit.getEmployeeInfoTONew().setEmpAcheivements(flist);

}


	public ActionForward resetLoan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("LoanAddMore")) {
				List<EmpLoanTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan();
				EmpLoanTO emploanTo=new EmpLoanTO();
				emploanTo.setLoanAmount("");
				emploanTo.setLoanDate("");
				emploanTo.setLoanDetails("");
				employeeInfoFormNew.setLoanListSize(String.valueOf(list.size()));
				list.add(emploanTo);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("loanDate_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}
	
	public ActionForward removeLoanRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpLoanTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpLoan();
				list.remove(list.size()-1);
				employeeInfoFormNew.setLoanListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setLoanListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	public ActionForward removeFinancialRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpFinancialTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial();
				list.remove(list.size()-1);
				employeeInfoFormNew.setFinancialListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setFinancialListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	public ActionForward resetFinancial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("FinancialAddMore")) {
				List<EmpFinancialTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFinancial();
				EmpFinancialTO empFinancialTO=new EmpFinancialTO();
				empFinancialTO.setFinancialAmount("");
				empFinancialTO.setFinancialDate("");
				empFinancialTO.setFinancialDetails("");
				employeeInfoFormNew.setFinancialListSize(String.valueOf(list.size()));
				list.add(empFinancialTO);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("financialDate_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}
	public ActionForward resetFeeConcession(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("FeeConcessionAddMore")) {
				List<EmpFeeConcessionTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession();
				EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
				empFeeConcessionTO.setFeeConcessionAmount("");
				empFeeConcessionTO.setFeeConcessionDate("");
				empFeeConcessionTO.setFeeConcessionDetails("");
				employeeInfoFormNew.setFeeListSize(String.valueOf(list.size()));
				list.add(empFeeConcessionTO);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("feeConcessionDate_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}
	
	public ActionForward removeFeeConcessionRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpFeeConcessionTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpFeeConcession();
				list.remove(list.size()-1);
				employeeInfoFormNew.setFeeListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setFeeListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	public ActionForward resetIncentives(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("IncentivesAddMore")) {
				List<EmpIncentivesTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives();
				EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
				empIncentivesTO.setIncentivesAmount("");
				empIncentivesTO.setIncentivesDate("");
				empIncentivesTO.setIncentivesDetails("");
				employeeInfoFormNew.setIncentivesListSize(String.valueOf(list.size()));
				list.add(empIncentivesTO);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("incentivesDate_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}	
	public ActionForward removeIncentivesRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpIncentivesTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpIncentives();
				list.remove(list.size()-1);
				employeeInfoFormNew.setIncentivesListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setIncentivesListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
	public ActionForward resetRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("RemarksAddMore")) {
				List<EmpRemarksTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks();
				EmpRemarksTO empRemarksTO=new EmpRemarksTO();
				empRemarksTO.setEnteredBy("");
				empRemarksTO.setRemarkDate("");
				empRemarksTO.setRemarkDetails("");
				employeeInfoFormNew.setRemarksListSize(String.valueOf(list.size()));
				list.add(empRemarksTO);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("remarkDate_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}
	public ActionForward removeRemarksRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpRemarksTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpRemarks();
				list.remove(list.size()-1);
				employeeInfoFormNew.setRemarksListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setRemarksListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	public ActionForward resetAchievement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("AchievementAddMore")) {
				List<EmpAcheivementTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements();
				EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
				empAcheivementTO.setAcheivementName("");
				empAcheivementTO.setDetails("");
				employeeInfoFormNew.setAchievementListSize(String.valueOf(list.size()));
				list.add(empAcheivementTO);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("acheivementName_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}	
	public ActionForward removeAchievementRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpAcheivementTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpAcheivements();
				list.remove(list.size()-1);
				employeeInfoFormNew.setAchievementListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setAchievementListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	public ActionForward resetDependents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetEmpLoan of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("DependentAddMore")) {
				List<EmpDependentsTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses();
				EmpDependentsTO empDependentsTo=new EmpDependentsTO();
				empDependentsTo.setDependantDOB("");
				empDependentsTo.setDependantName("");
				empDependentsTo.setDependentRelationship("");
				employeeInfoFormNew.setDependantsListSize(String.valueOf(list.size()));
				list.add(empDependentsTo);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("dependantName_"+size);
			}
		}
		log.info("End of resetEmpLoan of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}	
	public ActionForward removeDependentsRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removeFeeConcessionRow of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<EmpDependentsTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getEmpDependentses();
				list.remove(list.size()-1);
				employeeInfoFormNew.setDependantsListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setDependantsListSize(String.valueOf(list.size()-1));
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}	
	
	public ActionForward addPfGratuityNominees(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of addPfGratuityNominees of EmpInfoAction");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee()!=null)
		if(employeeInfoFormNew.getMode()!=null){
			if (employeeInfoFormNew.getMode().equalsIgnoreCase("PfGratuityAddMore")) {
				List<PfGratuityNomineesTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee();
				PfGratuityNomineesTO pfGratuityTo=new PfGratuityNomineesTO();
				pfGratuityTo.setNameAdressNominee("");
				pfGratuityTo.setMemberRelationship("");
				pfGratuityTo.setDobMember("");
				pfGratuityTo.setShare("");
				pfGratuityTo.setNameAdressGuardian("");
				employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()));
				list.add(pfGratuityTo);
				employeeInfoFormNew.setMode(null);
				String size=String.valueOf(list.size()-1);
				employeeInfoFormNew.setFocusValue("nameAdress_"+size);
			}
		}
		log.info("End of addPfGratuityNominees of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
}	
	public ActionForward removePfGratuitynominees(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of removePfGratuitynominees of EmployeeInfoFormNew");
		EmployeeInfoFormNew employeeInfoFormNew=(EmployeeInfoFormNew)form;
		List<PfGratuityNomineesTO> list=null;
		if(employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee()!=null)
		if(employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().size()>0){
				list=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee();
				list.remove(list.size()-1);
				employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()));
		}
		employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()-1));
		log.info("End of removePfGratuitynominees of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}	
	
		public ActionForward getPayscale(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoFormNew empForm = (EmployeeInfoFormNew) form;
		try {
			
		String payScale=empForm.getPayScaleId();
		String Scale =EmployeeInfoHandlerNew.getInstance().getPayscale(payScale);
		empForm.setScale(Scale);

		}
		
		catch (Exception e) {
			log.error("Error occured ",e);
			
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_INFO_SUBMISSION);
	}
	
		
		
		public void validateTime(EmployeeInfoFormNew form,ActionErrors errors){
			
			
				if(!StringUtils.isNumeric(form.getTimeIn())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
						
			}
			
			
				if(!StringUtils.isNumeric(form.getTimeInMin())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
				if(!StringUtils.isNumeric(form.getTimeInEnds())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getTimeInEndMIn())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getTimeOut())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getTimeOutMin())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}	
			
			
				if(!StringUtils.isNumeric(form.getSaturdayTimeOut())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getSaturdayTimeOutMin())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getHalfDayStartTime())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			
			
			
				if(!StringUtils.isNumeric(form.getHalfDayStartTimeMin())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}
				if(!StringUtils.isNumeric(form.getHalfDayEndTime())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
		
			
			if(CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
				if(!StringUtils.isNumeric(form.getHalfDayEndTimeMin())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeIn())){
				if(Integer.parseInt(form.getTimeIn())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeInEnds())){
				if(Integer.parseInt(form.getTimeInEnds())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeOut())){
				if(Integer.parseInt(form.getTimeOut())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getSaturdayTimeOut())){
				if(Integer.parseInt(form.getSaturdayTimeOut())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getHalfDayStartTime())){
				if(Integer.parseInt(form.getHalfDayStartTime())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getHalfDayEndTime())){
				if(Integer.parseInt(form.getHalfDayEndTime())>=24){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeInMin())){
				if(Integer.parseInt(form.getTimeInMin())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeInEndMIn())){
				if(Integer.parseInt(form.getTimeInEndMIn())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeOutMin())){
				if(Integer.parseInt(form.getTimeOutMin())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getSaturdayTimeOutMin())){
				if(Integer.parseInt(form.getSaturdayTimeOutMin())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(form.getHalfDayStartTimeMin())){
				if(Integer.parseInt(form.getHalfDayStartTimeMin())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			
			if(CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
				if(Integer.parseInt(form.getHalfDayEndTimeMin())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			Integer h1=0;
			Integer h2=0;
			Integer m1=0;
			Integer m2=0;
			if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin()) 
					&& CommonUtil.checkForEmpty(form.getTimeOut()) && CommonUtil.checkForEmpty(form.getTimeOutMin())){
				h1=Integer.parseInt(form.getTimeIn());
				h2=Integer.parseInt(form.getTimeOut());
				m1=Integer.parseInt(form.getTimeInMin());
				m2=Integer.parseInt(form.getTimeOutMin());
				if(h1<24 && h2<24){
					if(m1<60 && m2<60){
						if(h1>h2){
							
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_LESS));	
						}else if(h1==h2){
							if(m1>m2)
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_LESS));	
						}
					}
				}
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin())
					&& CommonUtil.checkForEmpty(form.getTimeInEnds()) && CommonUtil.checkForEmpty(form.getTimeInEndMIn())){
				h1=Integer.parseInt(form.getTimeIn());
				h2=Integer.parseInt(form.getTimeInEnds());
				m1=Integer.parseInt(form.getTimeInMin());
				m2=Integer.parseInt(form.getTimeInEndMIn());
				if(h1<24 && h2<24){
					if(m1<60 && m2<60){
						if(h1>h2){
							
								errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_TIMEINEND));	
						}else if(h1==h2){
							if(m1>m2)
								errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_TIMEINEND));	
							
						}
					}
				}
				
			}
			
			if(CommonUtil.checkForEmpty(form.getTimeIn()) && CommonUtil.checkForEmpty(form.getTimeInMin())
					&& CommonUtil.checkForEmpty(form.getSaturdayTimeOut()) && CommonUtil.checkForEmpty(form.getSaturdayTimeOutMin())){
				h1=Integer.parseInt(form.getTimeIn());
				h2=Integer.parseInt(form.getSaturdayTimeOut());
				m1=Integer.parseInt(form.getTimeInMin());
				m2=Integer.parseInt(form.getSaturdayTimeOutMin());
				if(h1<24 && h2<24){
					if(m1<60 && m2<60){
						if(h1>h2){
							
								errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_SATTIMEOUT));	
						}else if(h1==h2){
							if(m1>m2)
								errors.add("error",new ActionError(CMSConstants.EMPLOYEE_TIMEIN_SATTIMEOUT));	
						}
						
					}
				}
			}
				
				if(CommonUtil.checkForEmpty(form.getHalfDayStartTime()) && CommonUtil.checkForEmpty(form.getHalfDayStartTimeMin())
						&& CommonUtil.checkForEmpty(form.getHalfDayEndTime()) && CommonUtil.checkForEmpty(form.getHalfDayEndTimeMin())){
					h1=Integer.parseInt(form.getHalfDayStartTime());
					h2=Integer.parseInt(form.getHalfDayEndTime());
					m1=Integer.parseInt(form.getHalfDayStartTimeMin());
					m2=Integer.parseInt(form.getHalfDayEndTimeMin());
					if(h1<24 && h2<24){
						if(m1<60 && m2<60){
							if(h1>h2){
								errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_HALFDAY_LESS));	
							}else if(h1==h2){
								if(m1>m2)
									errors.add("error",new ActionError(CMSConstants.EMP_TYPE_TIME_OUT_HALFDAY_LESS));	
								
							}
						}
					}
			}
		}
	
				
}