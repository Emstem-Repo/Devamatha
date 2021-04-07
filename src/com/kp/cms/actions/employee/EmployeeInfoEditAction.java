package com.kp.cms.actions.employee;

import java.io.IOException;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandlerNew;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmployeeTO;
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
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

	@SuppressWarnings("deprecation")
	public class EmployeeInfoEditAction  extends BaseDispatchAction {
		private static final Log log = LogFactory.getLog(EmpResumeSubmissionAction.class);
		private static final String MESSAGE_KEY = "messages";
		private static final String PHOTOBYTES = "PhotoBytes";
		private static final String TO_DATEFORMAT = "MM/dd/yyyy";
		private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initEmpSearch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			@SuppressWarnings("unused")
			EmployeeInfoEditForm stForm = (EmployeeInfoEditForm) form;
			cleanupEditSessionData(request);
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		private void cleanupEditSessionData(HttpServletRequest request) {
			log.info("enter cleanupEditSessionData...");
			HttpSession session = request.getSession(false);
			if (session == null) {
				return;
			} else {
				if (session.getAttribute(EmployeeInfoEditAction.PHOTOBYTES) != null)
					session.removeAttribute(EmployeeInfoEditAction.PHOTOBYTES);
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
		public ActionForward getSearchedEmployee(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm stForm = (EmployeeInfoEditForm) form;
			cleanupEditSessionData(request);
			HttpSession session=request.getSession(false);
			String eid1=(String)session.getAttribute("employeeId");
			String rid1=(String)session.getAttribute("rid");
			Integer rid2=new Integer(rid1);
		    Integer eid=new Integer(eid1);
		    Properties prop = new Properties();
			try {
				InputStream inputStream = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
			} 
			catch (IOException e) {
				log.error("properties ",e);
				throw new IOException(e);
			}
			String fileName1=prop.getProperty(CMSConstants.ROLE_ID_PRINCIPLE);
			String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
			String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
			int principleid=Integer.parseInt(fileName1);
			int adminid=Integer.parseInt(fileName2);
			int testid=Integer.parseInt(fileName3);
			ActionMessages errors = stForm.validate(mapping, request);
		try {
			stForm.setPhoto(null);
			if (errors != null && !errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
				}
				EmployeeInfoEditHandler handler = EmployeeInfoEditHandler.getInstance();
				List<EmployeeTO> employeeToList = handler.getSearchedEmployee(eid,rid2,stForm,principleid,adminid,testid);
				if (employeeToList == null || employeeToList.isEmpty()) {
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
				}
				stForm.setEmployeeToList(employeeToList);
			} catch (ApplicationException e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

			} catch (Exception e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

			}
			log.info("exit getSearchedStudents..");
			return mapping.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE);
		}

		
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward InitEmpId(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			EmployeeInfoEditForm objform = (EmployeeInfoEditForm) form;
			int roleId=0;
			HttpSession session=request.getSession();
			roleId=Integer.parseInt(session.getAttribute("rid").toString());
			String ipAddress = request.getHeader("X-FORWARDED-FOR");  
			   if (ipAddress == null) {  
				   ipAddress = request.getRemoteAddr();  
			   }
			   
			//IPADDRESS CHECK
			if(!CMSConstants.IPADDRESSLIST.contains(ipAddress)){
				
				//return mapping.findForward("logout");
			}
			
			cleanupEditSessionData(request);
			objform.setTempEmployeeId(null);
			objform.setTempUid(null);
			objform.setTempCode(null);
			objform.setTempFingerPrintId(null);
			objform.setTempName(null);
			objform.setTempDesignationPfId(null);
			objform.setTempDepartmentId(null);
			objform.setTempActive("1");
			objform.setTempStreamId(null);
			objform.setTempTeachingStaff("2");
			objform.setTempEmptypeId(null);
			setDataToInitForm(objform);
			
			if (CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)) {
				objform.setIsDirectpage("Yes");
			}
			else {
				objform.setIsDirectpage("No");
			}
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward loadEmployeeInfo(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			EmployeeInfoEditForm objform = (EmployeeInfoEditForm) form;
			cleanupEditSessionData(request);
			ActionMessages messages=new ActionMessages();
			HttpSession session = request.getSession(false);
			int roleId = Integer.parseInt(session.getAttribute("rid").toString());
			try
			{
			boolean flag=false;
			if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
			{
			cleanUpPageData(objform);
			initializeQualificationAndEducation(objform);
			initializeLoanFinancial(objform);
			initializeFeeConcessionRemarks(objform);
			initializeIncentivesAchievements(objform);
			initializeDependents(objform);
			initializeImmigration(objform);
			initializePfgratuity(objform);
			setDataToForm(objform);
			setUserId(request,objform);
			flag=EmployeeInfoEditHandler.getInstance().getApplicantDetails(objform);
			objform.setEmpTypeIdOld(objform.getEmptypeId());
			if(CMSConstants.LINK_FOR_CJC){
				if (objform.getPhotoBytes()!= null){
					if (session != null) {
						session.setAttribute(EmployeeInfoEditAction.PHOTOBYTES, objform.getPhotoBytes());
					}	
				}
			}else{
			
				request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+objform.getSelectedEmployeeId()+".jpg?"+new Date());
			}
			EmployeeInfoEditHandler.getInstance().AgeCalculation(objform);
			}
			String rid1=(String)session.getAttribute("rid");
			//Properties prop = new Properties();
			//String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
			//String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
			//int adminid=Integer.parseInt(fileName2);
			//int testid=Integer.parseInt(fileName3);
			if(flag){
				if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
				}else {
					return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
				}
			}
			else
			{
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
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
		
		private void initializePfgratuity(EmployeeInfoEditForm employeeInfoEditForm) {
			List<PfGratuityNomineesTO> pfGratuityList=new ArrayList<PfGratuityNomineesTO>();
			PfGratuityNomineesTO pfGratuityTO=new PfGratuityNomineesTO();
			pfGratuityTO.setNameAdressNominee("");
			pfGratuityTO.setMemberRelationship("");
			pfGratuityTO.setDobMember("");
			pfGratuityTO.setShare("");
			pfGratuityTO.setNameAdressGuardian("");
			employeeInfoEditForm.setPfGratuityListSize(String.valueOf(pfGratuityList.size()));
			pfGratuityList.add(pfGratuityTO);
			employeeInfoEditForm.getEmployeeInfoTONew().setPfGratuityNominee(pfGratuityList);
			

			// TODO Auto-generated method stub
			
		}

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initEmployeeInfo(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.info("Entering into the initEmployeeInfo in EmployeeInfoEditAction");
			EmployeeInfoEditForm objform = (EmployeeInfoEditForm) form;
			cleanupEditSessionData(request);
			
			objform.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
			objform.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
			objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
			objform.setTempEmployeeId(objform.getEmployeeId());
			cleanUpPageData(objform);
			setDataToForm(objform);
			setUserId(request,objform);
			EmployeeInfoEditHandler handler = EmployeeInfoEditHandler.getInstance();
			HttpSession session=request.getSession(false);
			String eid1=(String)session.getAttribute("employeeId");
			Integer eid=new Integer(eid1);
			String rid1=(String)session.getAttribute("rid");
			Integer rid2=new Integer(rid1);
			 Properties prop = new Properties();
				try {
					InputStream inputStream = CommonUtil.class.getClassLoader()
							.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inputStream);
				} 
				catch (IOException e) {
					log.error("properties ",e);
					throw new IOException(e);
				}
				String fileName1=prop.getProperty(CMSConstants.ROLE_ID_PRINCIPLE);
				String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
				String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
				int principleid=Integer.parseInt(fileName1);
				int adminid=Integer.parseInt(fileName2);
				int testid=Integer.parseInt(fileName3);
			handler.getSearchedEmployee(eid,rid2,objform,principleid,adminid,testid);
			log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		/**
		 * @param objform
		 */
		private void cleanUpPageData(EmployeeInfoEditForm objform) {
			log.info("enter cleanUpPageData..");
			
			if (objform != null) {
				objform.setListSize(null);
				objform.setFocusValue(null);
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
				objform.setHomePhone1(null);
				objform.setHomePhone2(null);
				objform.setHomePhone3(null);
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
				objform.setReservationCategory(null);
				objform.setEmptypeId(null);
				objform.setTeachingStaff(null);
				objform.setCurrentAddressLine1(null);
				objform.setCurrentAddressLine2(null);
				objform.setCurrentCity(null);
				objform.setCurrentCountryId(null);
				objform.setCurrentZipCode(null);
				objform.setCurrentStateOthers(null);		
				objform.setRecommendedBy(null);
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
				objform.setPhotoBytes(null);
				objform.setEmpPhoto(null);
				objform.setQualificationId(null);
				objform.setIsPunchingExcemption("0");
				objform.setEligibilityTestNET(null);
				objform.setEligibilityTestNone(null);
				objform.setEligibilityTestSET(null);
				objform.setEligibilityTestSLET(null);
				objform.setSmartCardNo(null);
				objform.setEmpImageId(null);
				objform.setIndustryFunctionalArea(null);
				objform.setEligibilityTestOther(null);
				objform.setEligibilityTest(null);
				objform.setOtherEligibilityTestValue(null);
				objform.setHandicappedDescription(null);
				objform.setEmContactAddress(null);
				objform.setEligibilityList(null);
				objform.setBloodGroup(null);
				objform.setPhotoBytes(null);
				objform.setAlbumDesignation(null);
				/* code added by sudhir*/
				objform.setCurrentExpMonths(0);
				objform.setCurrentExpYears(0);
				objform.setTotalCurrentExpMonths(0);
				objform.setTotalCurrentExpYears(0);
				objform.setExtensionNumber(null);
				objform.setLicGratuityDate(null);
				objform.setLicGratuityNo(null);
				objform.setNomineePfDate(null);
				objform.setNomineePfNo(null);
				objform.setResearchPapersNonRefereed(null);
				objform.setResearchPapersProceedings(null);
				objform.setResearchPapersRefereed(null);
				objform.setInternationalPublications(null);
				objform.setNationalPublications(null);
				objform.setLocalPublications(null);
				objform.setNational(null);
				objform.setInternational(null);
				objform.setPhdResearchGuidance(null);
				objform.setMphilResearchGuidance(null);
				objform.setFdp1Training(null);
				objform.setFdp2Training(null);
				objform.setInternationalConference(null);
				objform.setNationalConference(null);
				objform.setLocalConference(null);
				objform.setRegionalConference(null);
				objform.setMajorProjects(null);
				objform.setMinorProjects(null);
				objform.setConsultancyPrjects1(null);
				objform.setConsultancyProjects2(null);
				objform.setDeputationDepartmentId(null);
				objform.setAppointmentLetterDate(null);
				objform.setReferenceNumberForReleaving(null);
				objform.setReferenceNumberForAppointment(null);
				objform.setReleavingOrderDate(null);
				objform.setMotherName(null);
				objform.setFatherName(null);
				
			}
			log.info("exit cleanUpPageData..");

		}
		/**
		 * @param employeeInfoEditForm
		 */
		private void initializeQualificationAndEducation(EmployeeInfoEditForm employeeInfoEditForm) {
			List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
			EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
			empPreviousOrgTo.setIndustryExpYears("");
			empPreviousOrgTo.setIndustryExpMonths("");
			empPreviousOrgTo.setCurrentDesignation("");
			empPreviousOrgTo.setCurrentOrganisation("");
			employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()));
			list.add(empPreviousOrgTo);
			
			List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
			empPreviousOrgTo.setTeachingExpYears("");
			empPreviousOrgTo.setTeachingExpMonths("");
			empPreviousOrgTo.setCurrentTeachingOrganisation("");
			empPreviousOrgTo.setCurrentTeachnigDesignation("");
			employeeInfoEditForm.setTeachingExpLength(String.valueOf(teachingList.size()));
			teachingList.add(empPreviousOrgTo);
			employeeInfoEditForm.getEmployeeInfoTONew().setExperiences(list);
			employeeInfoEditForm.getEmployeeInfoTONew().setTeachingExperience(teachingList);
		}
		
		/**
		 * @param employeeInfoEditFormfrom Department d where d.isActive=true and d.isAcademic=truealida
		 * @throws Exception
		 */
		public  void setDataToInitForm(EmployeeInfoEditForm employeeInfoEditForm)throws Exception {
			boolean isCjc = CMSConstants.LINK_FOR_CJC;
			employeeInfoEditForm.setIsCjc(isCjc);
			EmployeeInfoEditHandler.getInstance().getInitialPageData(employeeInfoEditForm);
			employeeInfoEditForm.setTempActive("1");
		}
		
		public  void setDataToForm(EmployeeInfoEditForm employeeInfoEditForm)throws Exception {
			boolean isCjc = CMSConstants.LINK_FOR_CJC;
			employeeInfoEditForm.setIsCjc(isCjc);
			EmployeeInfoEditHandler.getInstance().getInitialData(employeeInfoEditForm);
			employeeInfoEditForm.setCurrentlyWorking("YES");
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
			    employeeInfoEditForm.setEligibilityList(list);
			  //Initializing Eligiblity list ends
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
				Iterator<EmpQualificationLevelTo> iterator=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo().iterator();
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
			log.info("Befinning of resetExperienceInfo of EMPLOYEE_INFO_EDIT");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					// add one blank to add extra one
					List<EmpPreviousOrgTo> list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setIndustryExpYears("");
					empPreviousOrgTo.setIndustryExpMonths("");
					empPreviousOrgTo.setCurrentDesignation("");
					empPreviousOrgTo.setCurrentOrganisation("");
					/*-----------------code added by sudhir----------------*/
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					employeeInfoEditForm.setTeaching("false");
					employeeInfoEditForm.setIndustry("true");
					/*-----------------code added by sudhir----------------*/
					employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("industry_"+size);
				}
			}
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);

	}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward removeExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeExperienceInfo of EmpResumeSubmissionAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpPreviousOrgTo> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					// add one blank to add extra one
					list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
					if(list.size()>0)
					list.remove(list.size()-1);
					employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()-1));
					/*-----------------code added by sudhir----------------*/
					employeeInfoEditForm.setTeaching("false");
					employeeInfoEditForm.setIndustry("true");
					/*-----------------code added by sudhir----------------*/
				}
			}
			log.info("End of removeExperienceInfo of EMPLOYEE_INFO_EDIT");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);

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
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					List<EmpPreviousOrgTo> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					/*-----------------code added by sudhir----------------*/
					empPreviousOrgTo.setIndustryFromDate("");
					empPreviousOrgTo.setIndustryToDate("");
					employeeInfoEditForm.setTeaching("true");
					employeeInfoEditForm.setIndustry("false");
					/*-----------------code added by sudhir----------------*/
					employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("teach_"+size);
					
				}
			}
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward resetTeachingExperienceInfo1(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetExperienceInfo of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					List<EmpPreviousOrgTo> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
					
					EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
					empPreviousOrgTo.setTeachingExpYears("");
					empPreviousOrgTo.setTeachingExpMonths("");
					empPreviousOrgTo.setCurrentTeachnigDesignation("");
					empPreviousOrgTo.setCurrentTeachingOrganisation("");
					employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
					list.add(empPreviousOrgTo);
					request.setAttribute("empList", list);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("teach_"+size);
				}
			}
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT_TEACHING_AJAX);
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
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			HttpSession session = request.getSession(false);
			int roleId = Integer.parseInt(session.getAttribute("rid").toString());
			List<EmpPreviousOrgTo> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
					list.remove(list.size()-1);
					employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
					/*-----------------code added by sudhir----------------*/
					employeeInfoEditForm.setTeaching("true");
					employeeInfoEditForm.setIndustry("false");
					/*-----------------code added by sudhir----------------*/
			}
			employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EMPLOYEE_INFO_EDIT");
			if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
			}else {
				return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
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
		public ActionForward removeQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
			EmployeeInfoEditForm employeeInfoEditForm =(EmployeeInfoEditForm)form;
			List<EmpQualificationLevelTo> list=null;
			HttpSession session = request.getSession(false);
			int roleId = Integer.parseInt(session.getAttribute("rid").toString());
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
					if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()>0){
							list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
							list.remove(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()-1);
					}
				}
			employeeInfoEditForm.setLevelSize(String.valueOf(list.size()));
			employeeInfoEditForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
			if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
			}else {
				return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
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
		public ActionForward saveEmpDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
			EmployeeInfoEditForm employeeInfoEditForm =(EmployeeInfoEditForm)form;
			ActionMessages messages=new ActionMessages();
			HttpSession session = request.getSession(false);
			int roleId = Integer.parseInt(session.getAttribute("rid").toString());
			ActionErrors errors=employeeInfoEditForm.validate(mapping, request);
			validateEmpType(employeeInfoEditForm,errors);
			validateEditPhone(employeeInfoEditForm,errors);
			//validateEmployee(employeeInfoEditForm,errors);
			validateData(employeeInfoEditForm,errors);
			validateDate(employeeInfoEditForm,errors);
			validateTime(employeeInfoEditForm, errors);
			validateUnique(employeeInfoEditForm, errors);
			
			boolean flag=false;
			if(errors.isEmpty()){
				try {
					flag=EmployeeInfoEditHandler.getInstance().saveEmp(employeeInfoEditForm);
					if(flag){
						ActionMessage message=new ActionMessage(CMSConstants.EMP_INFO_SUBMIT_CONFIRM);
						messages.add(CMSConstants.MESSAGES,message);
						saveMessages(request, messages);
						cleanupEditSessionData(request);
						return mapping.findForward(CMSConstants.EMP_INFO_SUBMIT_CONFIRM);
					}else{
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
						saveMessages(request, messages);
						if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
							return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
						}else {
							return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
						}
					}
				} catch (Exception exception) {
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+employeeInfoEditForm.getSelectedEmployeeId()+".jpg?"+new Date());
				saveErrors(request, errors);
				if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
				}else {
					return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
				}
			}
		}
		
		/**
		 * @param employeeInfoEditForm
		 * @param errors
		 */
		@SuppressWarnings("unused")
		private void validateEmployee(EmployeeInfoEditForm employeeInfoEditForm, ActionErrors errors) {
			/*List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>(); 
			if(employeeInfoEditForm.isCurrentlyWorking()){
				if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
					list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
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
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
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
			
			List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null)
				level=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationFixedTo();
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
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null)
				level=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
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
			}
		}

		
		
		private void validateData(EmployeeInfoEditForm employeeInfoEditForm,ActionErrors errors) {
			try{
			InputStream propStream=AdmissionFormAction.class.getResourceAsStream("/resources/application.properties");
			int maxPhotoSize=0;
			Properties prop=new Properties();
			prop.load(propStream);
			maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
			FormFile file=null;
			if(employeeInfoEditForm.getEmpPhoto()!=null)
				file=employeeInfoEditForm.getEmpPhoto();
			if(employeeInfoEditForm.getIsCjc().equals(true))
			{
				if (employeeInfoEditForm.getCurrentAddressLine1() != null && !employeeInfoEditForm.getCurrentAddressLine1().isEmpty() && employeeInfoEditForm.getCurrentAddressLine1().trim().length() > 100)  {
			
					if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT)
								.hasNext()) {
						ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT);
						errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_ABOVELIMIT, error);
				}
			}
				if (employeeInfoEditForm.getCurrentAddressLine2() != null && !employeeInfoEditForm.getCurrentAddressLine2().isEmpty() && employeeInfoEditForm.getCurrentAddressLine2().trim().length() > 100)  {
					if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT) != null
							&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT);
						errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_ABOVELIMIT, error);
					}
				}
				if (employeeInfoEditForm.getAddressLine1() != null && !employeeInfoEditForm.getAddressLine1().isEmpty() && employeeInfoEditForm.getAddressLine1().trim().length() > 100)  {
					if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT) != null
							&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT);
						errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE1_ABOVELIMIT, error);
					}
				}
				if (employeeInfoEditForm.getAddressLine2() != null && !employeeInfoEditForm.getAddressLine2().isEmpty() && employeeInfoEditForm.getAddressLine2().trim().length() > 100)  {
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
		    if (employeeInfoEditForm.getCurrentAddressLine1() != null && !employeeInfoEditForm.getCurrentAddressLine1().isEmpty() && employeeInfoEditForm.getCurrentAddressLine1().trim().length() > 35)  {
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE1_INVALID, error);
				}
			}
		   	
			if (employeeInfoEditForm.getCurrentAddressLine2() != null && !employeeInfoEditForm.getCurrentAddressLine2().isEmpty() && employeeInfoEditForm.getCurrentAddressLine2().trim().length() > 40)  {
				if (errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID);
					errors.add(CMSConstants.EMPLOYEE_CURRENTADDRESSLINE2_INVALID, error);
				}
			}
			if (employeeInfoEditForm.getAddressLine1() != null && !employeeInfoEditForm.getAddressLine1().isEmpty() && employeeInfoEditForm.getAddressLine1().trim().length() > 35)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE1_INVALID, error);
				}
			}
			if (employeeInfoEditForm.getAddressLine2() != null && !employeeInfoEditForm.getAddressLine2().isEmpty() && employeeInfoEditForm.getAddressLine2().trim().length() > 40)  {
				if (errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID) != null
						&& !errors.get(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID)
								.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID);
					errors.add(CMSConstants.EMPLOYEE_ADDRESSLINE2_INVALID, error);
				}
			}
			 if (employeeInfoEditForm.getAlbumDesignation() == null || employeeInfoEditForm.getAlbumDesignation().isEmpty())  {
					if (errors.get(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM) != null
							&& !errors.get(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM);
						errors.add(CMSConstants.EMPLOYEE_DESIGNATION_STAFF_ALBUM, error);
					}
				}
			    	
			}
			/*if(!employeeInfoEditForm.getSameAddress()){
				if(employeeInfoEditForm.getAddressLine1()!=null && !employeeInfoEditForm.getAddressLine1().isEmpty()){
					errors.add(CMSConstants.EMPLOYEE_PERMANENT_ADDRESS,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_ADDRESS));
				}
				
				if(employeeInfoEditForm.getCountryId()!=null && !employeeInfoEditForm.getCountryId().isEmpty()){
					errors.add(CMSConstants.EMPLOYEE_PERMANENT_COUNTRY,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_COUNTRY));
				}
				
				if(employeeInfoEditForm.getStateId()!=null && !employeeInfoEditForm.getStateId().isEmpty()){
					errors.add(CMSConstants.EMPLOYEE_PERMANENT_STATE,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_STATE));
				}
				
				if(employeeInfoEditForm.getCity()!=null && !employeeInfoEditForm.getCity().isEmpty()){
					errors.add(CMSConstants.EMPLOYEE_PERMANENT_CITY,new ActionError(CMSConstants.EMPLOYEE_PERMANENT_CITY));
				}
			}*/
			boolean photoError = false;
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
			if(!photoError && file!=null && file.getFileData().length != 0){
				employeeInfoEditForm.setPhoto(file.getFileData());
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		private void validateEditPhone(EmployeeInfoEditForm employeeInfoFormNew,ActionErrors errors
				) {
			log.info("enter validateEditPhone..");
			if (errors == null)
				errors = new ActionErrors();


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

			
				if (employeeInfoFormNew.getHomePhone3() != null	&& !employeeInfoFormNew.getHomePhone3().isEmpty() && employeeInfoFormNew.getHomePhone3().trim().length() > 10) {
					if (errors.get(CMSConstants.EMPLOYEE_HOME_INVALID) != null
							&& !errors.get(CMSConstants.EMPLOYEE_HOME_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_HOME_INVALID);
						errors.add(CMSConstants.EMPLOYEE_HOME_INVALID, error);
					}
			}
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
						&& employeeInfoFormNew.getMobileNo1().trim().length() > 10) {
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
			    if (employeeInfoFormNew.getCode() != null && !employeeInfoFormNew.getCode().isEmpty() && employeeInfoFormNew.getCode().trim().length() > 10)  {
					if (errors.get(CMSConstants.EMPLOYEE_CODE_INVALID) != null
							&& !errors.get(CMSConstants.EMPLOYEE_CODE_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_CODE_INVALID);
						errors.add(CMSConstants.EMPLOYEE_CODE_INVALID, error);
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
			    Iterator<PfGratuityNomineesTO> itrre=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().iterator();
				while (itrre.hasNext()) {
					PfGratuityNomineesTO pfTo = (PfGratuityNomineesTO) itrre.next();
					
					 if (pfTo.getNameAdressNominee() != null && !pfTo.getNameAdressNominee().isEmpty() && pfTo.getNameAdressNominee().trim().length() > 500)  {
							if (errors.get(CMSConstants.EMPLOYEE_NAMENOMINEE_INVALID) != null
									&& !errors.get(CMSConstants.EMPLOYEE_OTHERINFO_INVALID)
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
					
				
			log.info("exit validateEditPhone..");
		}

		
		
		
		/**
		 * @param employeeInfoEditFormb
		 * @param errors
		 */
		private void validateDate(EmployeeInfoEditForm employeeInfoFormNew,ActionErrors errors) {
		
		
		if(employeeInfoFormNew.getDateOfBirth()!=null && !employeeInfoFormNew.getDateOfBirth().isEmpty()){

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
		/*if(employeeInfoFormNew.getDependantDOB()!=null && !CommonUtil.isValidDate(employeeInfoFormNew.getDependantDOB())){
			if (errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID).hasNext()) {
				errors.add(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID,new ActionError(CMSConstants.EMPLOYEE_DEPENDANTDOBDATE_INVALID));
				}
		}*/
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
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpQualificationLevelTo> list=null;
			HttpSession session=request.getSession(false);
			int roleId = Integer.parseInt(session.getAttribute("rid").toString());
			EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
			empQualificationLevelTo.setEducationId("");
			empQualificationLevelTo.setCourse("");
			empQualificationLevelTo.setSpecialization("");
			empQualificationLevelTo.setGrade("");
			empQualificationLevelTo.setInstitute("");
			empQualificationLevelTo.setYearOfComp("");
			
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
					list.add(empQualificationLevelTo);
					employeeInfoEditForm.setMode(null);
				}
			}
			}else{
				list=new ArrayList<EmpQualificationLevelTo>();
				list.add(empQualificationLevelTo);
				employeeInfoEditForm.setMode(null);
			}
			employeeInfoEditForm.setLevelSize(String.valueOf(list.size()));
			employeeInfoEditForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
			String size=String.valueOf(list.size()-1);
			employeeInfoEditForm.setFocusValue("course_"+size);
			log.info("End of addQualificationLevel of EmployeeInfoEditForm");
			if(!CMSConstants.EDIT_PROFILE_ROLE_ID_LIST.contains(roleId)){
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
			}else {
				return mapping.findForward(CMSConstants.INDIVIDUAL_EMPLOYEE_INFO_EDIT);
			}
		}
		
		
		public ActionForward getWorkTimeEntry(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm empForm = (EmployeeInfoEditForm) form;
			try {
				getEmpLeaveList(mapping, form, request, response);
			String empTypeId=empForm.getEmptypeId();
			List<EmpTypeTo> empTypeToList=EmployeeInfoHandlerNew.getInstance().getWorkTimeEntry(empTypeId);
			empForm.getEmployeeInfoTONew().setEmpTypeToList(empTypeToList);
			
//			empForm.setEmpTypeTo(empTypeToList);
			}
			
			catch (Exception e) {
				log.error("Error occured ",e);
				
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		public ActionForward getEmpLeaveList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm empForm = (EmployeeInfoEditForm) form;
			IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
			ActionErrors errors=new ActionErrors();
			try {
			List<EmpLeaveAllotTO> empLeaveToList=null;
			String empTypeId=empForm.getEmptypeId();
			//String oldEmpTypeId=empForm.getEmpTypeIdOld();
			int month=transaction.getInitializationMonth(Integer.parseInt(empForm.getEmptypeId()));
			int oldMonth=transaction.getInitializationMonth(Integer.parseInt(empForm.getEmpTypeIdOld()));
			empForm.setEmpLeaveInitOldMonth(String.valueOf(oldMonth));
			empForm.setEmpLeaveInitNewMonth(String.valueOf(month));
			request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+empForm.getSelectedEmployeeId()+".jpg?"+new Date());
				if(month==oldMonth)
				{
					empLeaveToList=EmployeeInfoEditHandler.getInstance().getEmpLeaveList(empTypeId, empForm);
				}
				else{
					validateEmpType(empForm, errors);
					//String message="Leave Initialized month is changed from " +oldMonth+ " to " +month;
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
				}
			empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
			validateData(empForm, errors);
			}
			catch (Exception e) {
				log.error("Error occured ",e);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		 public void validateEmpType(EmployeeInfoEditForm empForm, ActionErrors errors){
			 
			//	ActionMessages messages="Leave Initialized month is changed from " +empForm.getEmpLeaveInitOldMonth()+ " to " +empForm.getEmpLeaveInitNewMonth();
				if(empForm.getEmpLeaveInitNewMonth()!=null && empForm.getEmpLeaveInitOldMonth()!=null)
				{
				if(!empForm.getEmpLeaveInitNewMonth().equals(empForm.getEmpLeaveInitOldMonth()))
				{			
					if(errors.get(CMSConstants.KNOWLEDGEPRO_EMPLOYEE_TYPECHANGE)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_EMPLOYEE_TYPECHANGE).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_EMPLOYEE_TYPECHANGE,new ActionError(CMSConstants.KNOWLEDGEPRO_EMPLOYEE_TYPECHANGE));
					}
				}	}	
			}
		
	private void initializeLoanFinancial(EmployeeInfoEditForm employeeInfoEditForm) {
			
			List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
			EmpLoanTO emploanTo=new EmpLoanTO();
			emploanTo.setLoanAmount("");
			emploanTo.setLoanDate("");
			emploanTo.setLoanDetails("");
			employeeInfoEditForm.setLoanListSize(String.valueOf(list.size()));
			list.add(emploanTo);
			
			

			List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
			EmpFinancialTO empFinancialTO=new EmpFinancialTO();
			empFinancialTO.setFinancialAmount("");
			empFinancialTO.setFinancialDate("");
			empFinancialTO.setFinancialDetails("");
			employeeInfoEditForm.setFinancialListSize(String.valueOf(flist.size()));
			flist.add(empFinancialTO);
			employeeInfoEditForm.getEmployeeInfoTONew().setEmpLoan(list);
			employeeInfoEditForm.getEmployeeInfoTONew().setEmpFinancial(flist);
			
		}
	private void initializeFeeConcessionRemarks(EmployeeInfoEditForm employeeInfoEditForm) {
		
		List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
		EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
		empFeeConcessionTO.setFeeConcessionAmount("");
		empFeeConcessionTO.setFeeConcessionDate("");
		empFeeConcessionTO.setFeeConcessionDetails("");
		employeeInfoEditForm.setFeeListSize(String.valueOf(list.size()));
		list.add(empFeeConcessionTO);
		
		List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
		EmpRemarksTO empRemarksTO=new EmpRemarksTO();
		empRemarksTO.setEnteredBy("");
		empRemarksTO.setRemarkDate("");
		empRemarksTO.setRemarkDetails("");
		employeeInfoEditForm.setRemarksListSize(String.valueOf(flist.size()));
		flist.add(empRemarksTO);
		
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpFeeConcession(list);
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpRemarks(flist);
		
	}
	private void initializeIncentivesAchievements(EmployeeInfoEditForm employeeInfoEditForm) {
		
		List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
		EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
		empIncentivesTO.setIncentivesAmount("");
		empIncentivesTO.setIncentivesDate("");
		empIncentivesTO.setIncentivesDetails("");
		employeeInfoEditForm.setIncentivesListSize(String.valueOf(list.size()));
		list.add(empIncentivesTO);
		

		List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
		EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
		empAcheivementTO.setAcheivementName("");
		empAcheivementTO.setDetails("");
		employeeInfoEditForm.setAchievementListSize(String.valueOf(flist.size()));
		flist.add(empAcheivementTO);
		
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpIncentives(list);
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpAcheivements(flist);
		
	}

	private void initializeDependents(EmployeeInfoEditForm employeeInfoEditForm) {
		
		List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
		EmpDependentsTO empDependentsTO=new EmpDependentsTO();
		empDependentsTO.setDependantDOB("");
		empDependentsTO.setDependantName("");
		empDependentsTO.setDependentRelationship("");
		employeeInfoEditForm.setDependantsListSize(String.valueOf(empDependentses.size()));
		empDependentses.add(empDependentsTO);
		
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
		
	}
	private void initializeImmigration(EmployeeInfoEditForm employeeInfoEditForm) {
		
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
		employeeInfoEditForm.setImmigrationListSize(String.valueOf(empImmigration.size()));	
		empImmigration.add(empImmigrationTO);
		employeeInfoEditForm.getEmployeeInfoTONew().setEmpImmigration(empImmigration);
		
	}

		

		public ActionForward resetLoan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("LoanAddMore")) {
					List<EmpLoanTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan();
					EmpLoanTO emploanTo=new EmpLoanTO();
					emploanTo.setLoanAmount("");
					emploanTo.setLoanDate("");
					emploanTo.setLoanDetails("");
					employeeInfoEditForm.setLoanListSize(String.valueOf(list.size()));
					list.add(emploanTo);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("loanDate_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}
		
		public ActionForward removeLoanRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetExperienceInfo of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpLoanTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpLoan();
					list.remove(list.size()-1);
					employeeInfoEditForm.setLoanListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setLoanListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		public ActionForward removeFinancialRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetExperienceInfo of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpFinancialTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFinancial()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFinancial().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpFinancial();
					list.remove(list.size()-1);
					employeeInfoEditForm.setFinancialListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setFinancialListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		public ActionForward resetFinancial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFinancial()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("FinancialAddMore")) {
					List<EmpFinancialTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpFinancial();
					EmpFinancialTO empFinancialTO=new EmpFinancialTO();
					empFinancialTO.setFinancialAmount("");
					empFinancialTO.setFinancialDate("");
					empFinancialTO.setFinancialDetails("");
					employeeInfoEditForm.setFinancialListSize(String.valueOf(list.size()));
					list.add(empFinancialTO);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("financialDate_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}
		public ActionForward resetFeeConcession(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("FeeConcessionAddMore")) {
					List<EmpFeeConcessionTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpFeeConcession();
					EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
					empFeeConcessionTO.setFeeConcessionAmount("");
					empFeeConcessionTO.setFeeConcessionDate("");
					empFeeConcessionTO.setFeeConcessionDetails("");
					employeeInfoEditForm.setFeeListSize(String.valueOf(list.size()));
					list.add(empFeeConcessionTO);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("feeConcessionDate_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}
		
		public ActionForward removeFeeConcessionRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpFeeConcessionTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpFeeConcession().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpFeeConcession();
					list.remove(list.size()-1);
					employeeInfoEditForm.setFeeListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setFeeListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		public ActionForward resetIncentives(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpIncentives()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("IncentivesAddMore")) {
					List<EmpIncentivesTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpIncentives();
					EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
					empIncentivesTO.setIncentivesAmount("");
					empIncentivesTO.setIncentivesDate("");
					empIncentivesTO.setIncentivesDetails("");
					employeeInfoEditForm.setIncentivesListSize(String.valueOf(list.size()));
					list.add(empIncentivesTO);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("incentivesDate_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}	
		public ActionForward removeIncentivesRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpIncentivesTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpIncentives()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpIncentives().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpIncentives();
					list.remove(list.size()-1);
					employeeInfoEditForm.setIncentivesListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setIncentivesListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
		public ActionForward resetRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("RemarksAddMore")) {
					List<EmpRemarksTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks();
					EmpRemarksTO empRemarksTO=new EmpRemarksTO();
					empRemarksTO.setEnteredBy("");
					empRemarksTO.setRemarkDate("");
					empRemarksTO.setRemarkDetails("");
					employeeInfoEditForm.setRemarksListSize(String.valueOf(list.size()));
					list.add(empRemarksTO);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("remarkDate_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}
		public ActionForward removeRemarksRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpRemarksTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpRemarks();
					list.remove(list.size()-1);
					employeeInfoEditForm.setRemarksListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setRemarksListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		public ActionForward resetAchievement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpAcheivements()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("AchievementAddMore")) {
					List<EmpAcheivementTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpAcheivements();
					EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
					empAcheivementTO.setAcheivementName("");
					empAcheivementTO.setDetails("");
					employeeInfoEditForm.setAchievementListSize(String.valueOf(list.size()));
					list.add(empAcheivementTO);
					employeeInfoEditForm.setMode(null);
					//employeeInfoEditForm.setListSize(String.valueOf(list.size()));
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("acheivementName_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}	
		public ActionForward removeAchievementRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpAcheivementTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpAcheivements()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpAcheivements().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpAcheivements();
					list.remove(list.size()-1);
					employeeInfoEditForm.setAchievementListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setAchievementListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		public ActionForward resetDependents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of resetEmpLoan of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpDependentses()!=null)
			if(employeeInfoEditForm.getMode()!=null){
				if (employeeInfoEditForm.getMode().equalsIgnoreCase("DependentAddMore")) {
					List<EmpDependentsTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpDependentses();
					EmpDependentsTO empDependentsTo=new EmpDependentsTO();
					empDependentsTo.setDependantDOB("");
					empDependentsTo.setDependantName("");
					empDependentsTo.setDependentRelationship("");
					employeeInfoEditForm.setDependantsListSize(String.valueOf(list.size()));
					list.add(empDependentsTo);
					employeeInfoEditForm.setMode(null);
					String size=String.valueOf(list.size()-1);
					employeeInfoEditForm.setFocusValue("dependantName_"+size);
				}
			}
			log.info("End of resetEmpLoan of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}	
		public ActionForward removeDependentsRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removeFeeConcessionRow of EmployeeInfoEditForm");
			EmployeeInfoEditForm employeeInfoEditForm=(EmployeeInfoEditForm)form;
			List<EmpDependentsTO> list=null;
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpDependentses()!=null)
			if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpDependentses().size()>0){
					list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpDependentses();
					list.remove(list.size()-1);
					employeeInfoEditForm.setDependantsListSize(String.valueOf(list.size()));
			}
			employeeInfoEditForm.setDependantsListSize(String.valueOf(list.size()-1));
			log.info("End of resetExperienceInfo of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}	
		
		public ActionForward addPfGratuityNominees(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of addPfGratuityNominees of EmpInfoAction");
			EmployeeInfoEditForm employeeInfoFormNew=(EmployeeInfoEditForm)form;
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
			log.info("End of addPfGratuityNominees of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
	}	
		public ActionForward removePfGratuitynominees(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
			log.info("Befinning of removePfGratuitynominees of EmployeeInfoFormNew");
			EmployeeInfoEditForm employeeInfoFormNew=(EmployeeInfoEditForm)form;
			List<PfGratuityNomineesTO> list=null;
			if(employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee()!=null)
			if(employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee().size()>0){
					list=employeeInfoFormNew.getEmployeeInfoTONew().getPfGratuityNominee();
					list.remove(list.size()-1);
					employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()));
			}
			employeeInfoFormNew.setPfGratuityListSize(String.valueOf(list.size()-1));
			log.info("End of removePfGratuitynominees of EmployeeInfoEditForm");
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}	
		
		
		
		public ActionForward getPayscale(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm empForm = (EmployeeInfoEditForm) form;
			try {
				
			String payScale=empForm.getPayScaleId();
			String Scale =EmployeeInfoEditHandler.getInstance().getPayscale(payScale);
			empForm.setScale(Scale);
			

			}
			
			catch (Exception e) {
				log.error("Error occured ",e);
				
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
    
		
		public void validateTime(EmployeeInfoEditForm form,ActionErrors errors){
			
			
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
		
		public ActionForward removeEmpLeaveList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm empForm=(EmployeeInfoEditForm)form;
			try {
				request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+empForm.getSelectedEmployeeId()+".jpg?"+new Date());	
				String empTypeId=empForm.getEmptypeId();
				if(empTypeId == null || empTypeId.equals(""))
				{
				List<EmpLeaveAllotTO> empLeaveToList=null;
				empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				//empForm.setEmpLeaveAllotTo(empLeaveToList);
				}
				ActionErrors errors = new ActionErrors();
				validateData(empForm, errors);
			}catch (Exception e) {
				log.error("Error occured ",e);
				
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
		}
		
	
		private void validateUnique(EmployeeInfoEditForm employeeInfoFormNew , ActionErrors errors) {
			log.info("enter validateEditPhone..");
			if (errors == null)
				errors = new ActionErrors();
			if (employeeInfoFormNew.getuId() != null && !StringUtils.isEmpty(employeeInfoFormNew.getuId().trim()) ) {
				boolean duplicateUid;
				try {
					duplicateUid = EmployeeInfoEditHandler.getInstance().checkUidUnique(employeeInfoFormNew.getuId(), employeeInfoFormNew.getSelectedEmployeeId());
				
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
					duplicateFingerPrintId = EmployeeInfoEditHandler.getInstance().checkFingerPrintIdUnique(employeeInfoFormNew.getFingerPrintId(), employeeInfoFormNew.getSelectedEmployeeId());
				
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
					duplicatecode = EmployeeInfoEditHandler.getInstance().checkCodeUnique(employeeInfoFormNew.getCode(), employeeInfoFormNew.getSelectedEmployeeId());
				
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

		public ActionForward getSearchedEmployeeList(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			EmployeeInfoEditForm stForm = (EmployeeInfoEditForm) form;
			cleanupEditSessionData(request);
			cleanUpPageData(stForm);
			//ActionMessages errors = stForm.validate(mapping, request);
		try {
			
				/*stForm.setTempUid(stForm.getuId());
				stForm.setTempFingerPrintId(stForm.getFingerPrintId());
				stForm.setTempCode(stForm.getCode());
				stForm.setTempName(stForm.getName());
				stForm.setTempDepartmentId(stForm.getDepartmentId());// check this line
				stForm.setTempDesignationPfId(stForm.getDesignationPfId());*/
				
				EmployeeInfoEditHandler handler = EmployeeInfoEditHandler.getInstance();
				HttpSession session=request.getSession(false);
				String eid1=(String)session.getAttribute("employeeId");
				Integer eid=new Integer(eid1);
				String rid1=(String)session.getAttribute("rid");
				Integer rid2=new Integer(rid1);
				 Properties prop = new Properties();
					try {
						InputStream inputStream = CommonUtil.class.getClassLoader()
								.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
						prop.load(inputStream);
					} 
					catch (IOException e) {
						log.error("properties ",e);
						throw new IOException(e);
					}
					String fileName1=prop.getProperty(CMSConstants.ROLE_ID_PRINCIPLE);
					String fileName2=prop.getProperty(CMSConstants.ROLE_ID_ADMIN);
					String fileName3=prop.getProperty(CMSConstants.ROLE_ID_TEST);
					int principleid=Integer.parseInt(fileName1);
					int adminid=Integer.parseInt(fileName2);
					int testid=Integer.parseInt(fileName3);
				List<EmployeeTO> employeeToList = handler.getSearchedEmployee(eid,rid2,stForm,principleid,adminid,testid);
				if (employeeToList == null || employeeToList.isEmpty()) {

					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

				}
				stForm.setEmployeeToList(employeeToList);
			} catch (ApplicationException e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

			} catch (Exception e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(EmployeeInfoEditAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

			}
			log.info("exit getSearchedStudents..");
			return mapping.findForward(CMSConstants.EMPLOYEE_EDITLISTPAGE);
		}
		
		private boolean validatefutureDate(String dateString) {
			log.info("enter validatefutureDate..");
			String formattedString = CommonUtil.ConvertStringToDateFormat(
					dateString, EmployeeInfoEditAction.FROM_DATEFORMAT,
					EmployeeInfoEditAction.TO_DATEFORMAT);
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

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward editMyProfile(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			EmployeeInfoEditForm objform = (EmployeeInfoEditForm) form;
			setUserId(request,objform);
			ActionMessages messages=new ActionMessages();
			HttpSession session1=request.getSession();
			request.setAttribute("mode", "editMyProfile");
			try
			{
				objform.setEditMyProfile(null);
				EmployeeInfoEditHandler.getInstance().setEmpIdToForm(objform);
			    boolean flag=false;
			    if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
			    {
			       cleanUpPageData(objform);
			       if(request.getSession().getAttribute("PhotoBytes")!=null){
				       request.getSession().removeAttribute("PhotoBytes");
			        }
			        initializeQualificationAndEducation(objform);
			        initializeLoanFinancial(objform);
			        initializeFeeConcessionRemarks(objform);
			        initializeIncentivesAchievements(objform);
			        initializeDependents(objform);
			        initializeImmigration(objform);
			        setDataToForm(objform);
			        setUserId(request,objform);
			        flag=EmployeeInfoEditHandler.getInstance().getApplicationDetails(objform,session1);
			        if (objform.getPhotoBytes()!= null)
			        {
			            HttpSession session = request.getSession(false);
				        if (session != null) {
					         session.setAttribute(EmployeeInfoEditAction.PHOTOBYTES, objform.getPhotoBytes());
				        }	
			        }
			        EmployeeInfoEditHandler.getInstance().AgeCalculation(objform);
			    }
			    if(flag){
				    return mapping.findForward(CMSConstants.EDIT_MY_PROFILE);
			    }
			    else
			    {
				    messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
				    saveMessages(request, messages);
				    return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
			    }
		    }
			catch (Exception exception) {
				if (exception instanceof ApplicationException) {
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
			    }else
				   throw exception;
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
		public ActionForward saveMyProfileDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
			EmployeeInfoEditForm employeeInfoEditForm =(EmployeeInfoEditForm)form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors=employeeInfoEditForm.validate(mapping, request);
			HttpSession session=request.getSession();
			validateEditPhone(employeeInfoEditForm,errors);
			//validateEmployee(employeeInfoEditForm,errors);
			validateData(employeeInfoEditForm,errors);
			validateDate(employeeInfoEditForm,errors);
			validateTime(employeeInfoEditForm, errors);
			validateUnique(employeeInfoEditForm, errors);
			boolean flag=false;
			if(errors.isEmpty()){
				try {
					flag=EmployeeInfoEditHandler.getInstance().saveMyProfile(employeeInfoEditForm,session);
					if(flag){
						
						ActionMessage message=new ActionMessage(CMSConstants.EDIT_INFO_SUBMIT);
						messages.add(CMSConstants.MESSAGES,message);
						saveMessages(request, messages);
						session.setAttribute("methodName", "editMyProfile");
						return mapping.findForward(CMSConstants.EDIT_INFO_SUBMIT);
					}else{
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
					}
				}catch (Exception exception) {
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_EDIT);
			}
		}
		
		
	}


