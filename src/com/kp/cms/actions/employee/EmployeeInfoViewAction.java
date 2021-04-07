package com.kp.cms.actions.employee;
	import java.io.IOException;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.EmployeeInfoViewForm;
import com.kp.cms.handlers.employee.EmployeeApplyLeaveHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditNewHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandlerNew;
import com.kp.cms.handlers.employee.EmployeeViewHandler;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.employee.EmpFeeConcessionTO;
import com.kp.cms.to.employee.EmpFinancialTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpLoanTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpRemarksTO;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;

	public class EmployeeInfoViewAction extends BaseDispatchAction {
			private static final Log log = LogFactory.getLog(EmpResumeSubmissionAction.class);
			private static final String MESSAGE_KEY = "messages";
			private static final String PHOTOBYTES = "PhotoBytes";
			private static final String TO_DATEFORMAT = "MM/dd/yyyy";
			private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
			
			public ActionForward initEmpSearch(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			public ActionForward getSearchedEmployee(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm stForm = (EmployeeInfoViewForm) form;
				
			try {
					/*stForm.setTempUid(stForm.getuId());
					stForm.setTempFingerPrintId(stForm.getFingerPrintId());
					stForm.setTempCode(stForm.getCode());
					stForm.setTempName(stForm.getName());
					stForm.setTempDepartmentId(stForm.getDepartmentId());// check this line
					stForm.setTempDesignationPfId(stForm.getDesignationPfId());*/
					
				/*	EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
					
					List<EmployeeTO> employeeToList = handler.getSearchedEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {

						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);

						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);

					}
					stForm.setEmployeeToList(employeeToList);*/
				HttpSession session=request.getSession(false);
				String eid1=(String)session.getAttribute("employeeId");
				String rid1=(String)session.getAttribute("rid");
				String guestId = (String)session.getAttribute("guestId");
				String userId = (String)session.getAttribute("userId");
				Integer rid2=new Integer(rid1);
				if(eid1 != null){
			    Integer eid=new Integer(eid1);
				}
				int uId=0;
				if(userId!=null)
			     uId = Integer.parseInt(userId);
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
			
				
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);
					}
				EmployeeInfoEditNewHandler handler = EmployeeInfoEditNewHandler.getInstance();
				List<UserInfoTO> userToList = new ArrayList<UserInfoTO>();
					//List<EmployeeTO> employeeToList = handler.getSearchedEmployeeNew(eid,rid2,stForm,principleid,adminid,testid);
				if(uId == 3 || rid2 == 17 ){
					 userToList = handler.getSearchedUsers( stForm.getSelectedEmployeeId(),guestId);
				}
				if(eid1 != null && uId != 3 && rid2 != 17){
					 userToList = handler.getSearchedUsers(eid1, guestId);
				}
				if(guestId != null && uId != 3 && rid2 != 17){
					 userToList = handler.getSearchedUsers(eid1 ,guestId);
				}
					if (userToList == null || userToList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						//message = new ActionMessage(
								//CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						//messages.add(EmployeeInfoEditNewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);
						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);
					}
					stForm.setUserList(userToList);
				
			}catch (ApplicationException e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);

				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);

				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.EMPLOYEE_VIEW_LISTPAGE);
			}

			
			
			public ActionForward InitEmpId(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
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
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);
			}
			
			public ActionForward loadEmployeeInfo(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				ActionMessages messages=new ActionMessages();
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
				setDataToFormView(objform);
				setUserId(request,objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				flag=EmployeeViewHandler.getInstance().getApplicantDetails(objform);
				if(CMSConstants.LINK_FOR_CJC){
					if (objform.getPhotoBytes()!= null){
						HttpSession session = request.getSession(false);
						if (session != null) {
							session.setAttribute(EmployeeInfoViewAction.PHOTOBYTES, objform.getPhotoBytes());
						}	
					}
				}else{
				
					request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+objform.getSelectedEmployeeId()+".jpg?"+new Date());
				}
				//EmployeeViewHandler.getInstance().AgeCalculation(objform);
				}
				if(flag){
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW_SEARCH);
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
			
			
			
			
			public ActionForward initEmployeeInfo(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				log.info("Entering into the initEmployeeInfo in EmployeeInfoEditAction");
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				objform.setTempEmployeeId(objform.getEmployeeId());
				
				//objform.reset();
		
				cleanUpPageData(objform);
				setDataToForm(objform);
				
				setUserId(request,objform);
				EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
				
				 handler.getSearchedEmployee(objform);
					
				log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}

			@SuppressWarnings("unused")
			private void cleanupEditSessionData(HttpServletRequest request) {
				log.info("enter cleanupEditSessionData...");
				HttpSession session = request.getSession(false);
				if (session == null) {
					return;
				} else {
					if (session.getAttribute(EmployeeInfoViewAction.PHOTOBYTES) != null)
						session.removeAttribute(EmployeeInfoViewAction.PHOTOBYTES);
				}
			}

			
			
			private void cleanUpPageData(EmployeeInfoViewForm objform) {
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
					objform.setBloodGroup(null);
					objform.setSmartCardNo(null);
					objform.getEmployeeInfoTONew().setEmpLeaveToList(null);
					objform.setEligibilityTestdisplay(null);
					objform.setIndustryFunctionalArea(null);
					objform.setEligibilityTestOther(null);
					objform.setEligibilityTest(null);
					objform.setOtherEligibilityTestValue(null);
					objform.setSubjectId(null);
					objform.setEmContactAddress(null);
					objform.setExtensionNumber(null);
				//objform.setPhoto(null);
				//	objform.setState(null);
				}
				log.info("exit cleanUpPageData..");

			}

				
			
			
			private void initializeQualificationAndEducation(EmployeeInfoViewForm EmployeeInfoViewForm) {
				
				List<EmpPreviousOrgTo> list=new ArrayList<EmpPreviousOrgTo>();
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");

				EmployeeInfoViewForm.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
				empPreviousOrgTo.setTeachingExpYears("");
				empPreviousOrgTo.setTeachingExpMonths("");
				empPreviousOrgTo.setCurrentTeachingOrganisation("");
				empPreviousOrgTo.setCurrentTeachnigDesignation("");
				EmployeeInfoViewForm.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				EmployeeInfoViewForm.getEmployeeInfoTONew().setExperiences(list);
				EmployeeInfoViewForm.getEmployeeInfoTONew().setTeachingExperience(teachingList);
				
			}

				/**
			 * 
			 */
			
			public  void setDataToInitForm(EmployeeInfoViewForm EmployeeInfoViewForm)throws Exception {
				EmployeeViewHandler.getInstance().getInitialPageData(EmployeeInfoViewForm);
				EmployeeInfoViewForm.setTempActive("1");
			}
				
			
			public  void setDataToForm(EmployeeInfoViewForm EmployeeInfoViewForm)throws Exception {
				EmployeeViewHandler.getInstance().getInitialData(EmployeeInfoViewForm);
				EmployeeInfoViewForm.setCurrentlyWorking("YES");
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
					Iterator<EmpQualificationLevelTo> iterator=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationFixedTo().iterator();
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
			public  void setDataToFormView(EmployeeInfoViewForm employeeInfoViewForm)throws Exception {
				EmployeeViewHandler.getInstance().getInitialData(employeeInfoViewForm);
				if(employeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationFixedTo()!=null){
					Iterator<EmpQualificationLevelTo> iterator=employeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationFixedTo().iterator();
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
				log.info("Befinning of resetExperienceInfo of EMPLOYEE_INFO_VIEW");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						List<EmpPreviousOrgTo> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getExperiences();
						EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
						empPreviousOrgTo.setIndustryExpYears("");
						empPreviousOrgTo.setIndustryExpMonths("");
						empPreviousOrgTo.setCurrentDesignation("");
						empPreviousOrgTo.setCurrentOrganisation("");
						EmployeeInfoViewForm.setIndustryExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);

		}
			public ActionForward removeExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeExperienceInfo of EmpResumeSubmissionAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpPreviousOrgTo> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getExperiences();
						if(list.size()>0)
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setIndustryExpLength(String.valueOf(list.size()-1));
					}
				}
				log.info("End of removeExperienceInfo of EMPLOYEE_INFO_VIEW");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);

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
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						List<EmpPreviousOrgTo> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getTeachingExperience();
						EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
						empPreviousOrgTo.setTeachingExpYears("");
						empPreviousOrgTo.setTeachingExpMonths("");
						empPreviousOrgTo.setCurrentTeachnigDesignation("");
						empPreviousOrgTo.setCurrentTeachingOrganisation("");
						EmployeeInfoViewForm.setTeachingExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}
			public ActionForward removeTeachingExperienceInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpPreviousOrgTo> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getTeachingExperience().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getTeachingExperience();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setTeachingExpLength(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setTeachingExpLength(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EMPLOYEE_INFO_VIEW");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			public ActionForward removeQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
				EmployeeInfoViewForm EmployeeInfoViewForm =(EmployeeInfoViewForm)form;
				List<EmpQualificationLevelTo> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
						if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()>0){
								list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
								list.remove(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()-1);
						}
					}
				EmployeeInfoViewForm.setLevelSize(String.valueOf(list.size()));
				EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
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
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpQualificationLevelTo> list=null;
				EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
				empQualificationLevelTo.setEducationId("");
				empQualificationLevelTo.setCourse("");
				empQualificationLevelTo.setSpecialization("");
				empQualificationLevelTo.setGrade("");
				empQualificationLevelTo.setInstitute("");
				empQualificationLevelTo.setYearOfComp("");
				
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
						list.add(empQualificationLevelTo);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				}else{
					list=new ArrayList<EmpQualificationLevelTo>();
					list.add(empQualificationLevelTo);
					EmployeeInfoViewForm.setMode(null);
				}
				EmployeeInfoViewForm.setLevelSize(String.valueOf(list.size()));
				EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
				log.info("End of addQualificationLevel of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			
			public ActionForward getWorkTimeEntry(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm empForm = (EmployeeInfoViewForm) form;
				try {
					getEmpLeaveList(mapping, form, request, response);
				String empTypeId=empForm.getEmptypeId();
				List<EmpTypeTo> empTypeToList=EmployeeInfoHandlerNew.getInstance().getWorkTimeEntry(empTypeId);
				empForm.getEmployeeInfoTONew().setEmpTypeToList(empTypeToList);
				
//				empForm.setEmpTypeTo(empTypeToList);
				}
				
				catch (Exception e) {
					log.error("Error occured ",e);
					
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			public ActionForward getEmpLeaveList(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm empForm = (EmployeeInfoViewForm) form;
				try {
				String empTypeId=empForm.getEmptypeId();
				List<EmpLeaveAllotTO> empLeaveToList=EmployeeInfoHandlerNew.getInstance().getEmpLeaveList(empTypeId);
				empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				//empForm.setEmpLeaveAllotTo(empLeaveToList);
				}
				
				catch (Exception e) {
					log.error("Error occured ",e);
					
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
		private void initializeLoanFinancial(EmployeeInfoViewForm EmployeeInfoViewForm) {
				
				List<EmpLoanTO> list=new ArrayList<EmpLoanTO>();
				EmpLoanTO emploanTo=new EmpLoanTO();
				emploanTo.setLoanAmount("");
				emploanTo.setLoanDate("");
				emploanTo.setLoanDetails("");
				EmployeeInfoViewForm.setLoanListSize(String.valueOf(list.size()));
				list.add(emploanTo);
				
				

				List<EmpFinancialTO> flist=new ArrayList<EmpFinancialTO>();
				EmpFinancialTO empFinancialTO=new EmpFinancialTO();
				empFinancialTO.setFinancialAmount("");
				empFinancialTO.setFinancialDate("");
				empFinancialTO.setFinancialDetails("");
				EmployeeInfoViewForm.setFinancialListSize(String.valueOf(flist.size()));
				flist.add(empFinancialTO);
				EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpLoan(list);
				EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpFinancial(flist);
				
			}
		private void initializeFeeConcessionRemarks(EmployeeInfoViewForm EmployeeInfoViewForm) {
			
			List<EmpFeeConcessionTO> list=new ArrayList<EmpFeeConcessionTO>();
			EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
			empFeeConcessionTO.setFeeConcessionAmount("");
			empFeeConcessionTO.setFeeConcessionDate("");
			empFeeConcessionTO.setFeeConcessionDetails("");
			EmployeeInfoViewForm.setFeeListSize(String.valueOf(list.size()));
			list.add(empFeeConcessionTO);
			
			List<EmpRemarksTO> flist=new ArrayList<EmpRemarksTO>();
			EmpRemarksTO empRemarksTO=new EmpRemarksTO();
			empRemarksTO.setEnteredBy("");
			empRemarksTO.setRemarkDate("");
			empRemarksTO.setRemarkDetails("");
			EmployeeInfoViewForm.setRemarksListSize(String.valueOf(flist.size()));
			flist.add(empRemarksTO);
			
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpFeeConcession(list);
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpRemarks(flist);
			
		}
		private void initializeIncentivesAchievements(EmployeeInfoViewForm EmployeeInfoViewForm) {
			
			List<EmpIncentivesTO> list=new ArrayList<EmpIncentivesTO>();
			EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
			empIncentivesTO.setIncentivesAmount("");
			empIncentivesTO.setIncentivesDate("");
			empIncentivesTO.setIncentivesDetails("");
			EmployeeInfoViewForm.setIncentivesListSize(String.valueOf(list.size()));
			list.add(empIncentivesTO);
			

			List<EmpAcheivementTO> flist=new ArrayList<EmpAcheivementTO>();
			EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
			empAcheivementTO.setAcheivementName("");
			empAcheivementTO.setDetails("");
			EmployeeInfoViewForm.setAchievementListSize(String.valueOf(flist.size()));
			flist.add(empAcheivementTO);
			
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpIncentives(list);
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpAcheivements(flist);
			
		}

		private void initializeDependents(EmployeeInfoViewForm EmployeeInfoViewForm) {
			
			List<EmpDependentsTO> empDependentses=new ArrayList<EmpDependentsTO>();
			EmpDependentsTO empDependentsTO=new EmpDependentsTO();
			empDependentsTO.setDependantDOB("");
			empDependentsTO.setDependantName("");
			empDependentsTO.setDependentRelationship("");
			EmployeeInfoViewForm.setDependantsListSize(String.valueOf(empDependentses.size()));
			empDependentses.add(empDependentsTO);
			
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpDependentses(empDependentses);
			
		}
		private void initializeImmigration(EmployeeInfoViewForm EmployeeInfoViewForm) {
			
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
			EmployeeInfoViewForm.setImmigrationListSize(String.valueOf(empImmigration.size()));	
			empImmigration.add(empImmigrationTO);
			EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpImmigration(empImmigration);
			
		}

			

			public ActionForward resetLoan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpLoan()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("LoanAddMore")) {
						List<EmpLoanTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpLoan();
						EmpLoanTO emploanTo=new EmpLoanTO();
						emploanTo.setLoanAmount("");
						emploanTo.setLoanDate("");
						emploanTo.setLoanDetails("");
						EmployeeInfoViewForm.setLoanListSize(String.valueOf(list.size()));
						list.add(emploanTo);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}
			
			public ActionForward removeLoanRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpLoanTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpLoan()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpLoan().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpLoan();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setLoanListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setLoanListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			public ActionForward removeFinancialRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpFinancialTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFinancial()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFinancial().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFinancial();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setFinancialListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setFinancialListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			public ActionForward resetFinancial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFinancial()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("FinancialAddMore")) {
						List<EmpFinancialTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFinancial();
						EmpFinancialTO empFinancialTO=new EmpFinancialTO();
						empFinancialTO.setFinancialAmount("");
						empFinancialTO.setFinancialDate("");
						empFinancialTO.setFinancialDetails("");
						EmployeeInfoViewForm.setFinancialListSize(String.valueOf(list.size()));
						list.add(empFinancialTO);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}
			public ActionForward resetFeeConcession(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("FeeConcessionAddMore")) {
						List<EmpFeeConcessionTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFeeConcession();
						EmpFeeConcessionTO empFeeConcessionTO=new EmpFeeConcessionTO();
						empFeeConcessionTO.setFeeConcessionAmount("");
						empFeeConcessionTO.setFeeConcessionDate("");
						empFeeConcessionTO.setFeeConcessionDetails("");
						EmployeeInfoViewForm.setFeeListSize(String.valueOf(list.size()));
						list.add(empFeeConcessionTO);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}
			
			public ActionForward removeFeeConcessionRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeFeeConcessionRow of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpFeeConcessionTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFeeConcession()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFeeConcession().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpFeeConcession();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setFeeListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setFeeListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			public ActionForward resetIncentives(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpIncentives()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("IncentivesAddMore")) {
						List<EmpIncentivesTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpIncentives();
						EmpIncentivesTO empIncentivesTO=new EmpIncentivesTO();
						empIncentivesTO.setIncentivesAmount("");
						empIncentivesTO.setIncentivesDate("");
						empIncentivesTO.setIncentivesDetails("");
						EmployeeInfoViewForm.setIncentivesListSize(String.valueOf(list.size()));
						list.add(empIncentivesTO);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}	
			public ActionForward removeIncentivesRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeFeeConcessionRow of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpIncentivesTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpIncentives()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpIncentives().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpIncentives();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setIncentivesListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setIncentivesListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			public ActionForward resetRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpRemarks()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("RemarksAddMore")) {
						List<EmpRemarksTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpRemarks();
						EmpRemarksTO empRemarksTO=new EmpRemarksTO();
						empRemarksTO.setEnteredBy("");
						empRemarksTO.setRemarkDate("");
						empRemarksTO.setRemarkDetails("");
						EmployeeInfoViewForm.setRemarksListSize(String.valueOf(list.size()));
						list.add(empRemarksTO);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}
			public ActionForward removeRemarksRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeFeeConcessionRow of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpRemarksTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpRemarks()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpRemarks().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpRemarks();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setRemarksListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setRemarksListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			public ActionForward resetAchievement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpAcheivements()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("AchievementAddMore")) {
						List<EmpAcheivementTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpAcheivements();
						EmpAcheivementTO empAcheivementTO=new EmpAcheivementTO();
						empAcheivementTO.setAcheivementName("");
						empAcheivementTO.setDetails("");
						EmployeeInfoViewForm.setAchievementListSize(String.valueOf(list.size()));
						list.add(empAcheivementTO);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}	
			public ActionForward removeAchievementRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeFeeConcessionRow of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpAcheivementTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpAcheivements()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpAcheivements().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpAcheivements();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setAchievementListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setAchievementListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			public ActionForward resetDependents(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpDependentses()!=null)
				if(EmployeeInfoViewForm.getMode()!=null){
					if (EmployeeInfoViewForm.getMode().equalsIgnoreCase("DependentAddMore")) {
						List<EmpDependentsTO> list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpDependentses();
						EmpDependentsTO empDependentsTo=new EmpDependentsTO();
						empDependentsTo.setDependantDOB("");
						empDependentsTo.setDependantName("");
						empDependentsTo.setDependentRelationship("");
						EmployeeInfoViewForm.setDependantsListSize(String.valueOf(list.size()));
						list.add(empDependentsTo);
						EmployeeInfoViewForm.setMode(null);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
		}	
			public ActionForward removeDependentsRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeFeeConcessionRow of EmployeeInfoViewForm");
				EmployeeInfoViewForm EmployeeInfoViewForm=(EmployeeInfoViewForm)form;
				List<EmpDependentsTO> list=null;
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpDependentses()!=null)
				if(EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpDependentses().size()>0){
						list=EmployeeInfoViewForm.getEmployeeInfoTONew().getEmpDependentses();
						list.remove(list.size()-1);
						EmployeeInfoViewForm.setDependantsListSize(String.valueOf(list.size()));
				}
				EmployeeInfoViewForm.setDependantsListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoViewForm");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}	
			
			public ActionForward getPayscale(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm empForm = (EmployeeInfoViewForm) form;
				try {
					
				String payScale=empForm.getPayScaleId();
				String Scale =EmployeeViewHandler.getInstance().getPayscale(payScale);
				empForm.setScale(Scale);
				

				}
				
				catch (Exception e) {
					log.error("Error occured ",e);
					
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
			
			
			public ActionForward removeEmpLeaveList(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm empForm=(EmployeeInfoViewForm)form;
				try {
				String empTypeId=empForm.getEmptypeId();
				if(empTypeId == null || empTypeId.equals(""))
				{
				List<EmpLeaveAllotTO> empLeaveToList=null;
				empForm.getEmployeeInfoTONew().setEmpLeaveToList(empLeaveToList);
				//empForm.setEmpLeaveAllotTo(empLeaveToList);
				}
				}
				catch (Exception e) {
					log.error("Error occured ",e);
					
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_VIEW);
			}
			
		
			public ActionForward getSearchedEmployeeList(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm stForm = (EmployeeInfoViewForm) form;
			try {
				
					/*stForm.setTempUid(stForm.getuId());
					stForm.setTempFingerPrintId(stForm.getFingerPrintId());
					stForm.setTempCode(stForm.getCode());
					stForm.setTempName(stForm.getName());
					stForm.setTempDepartmentId(stForm.getDepartmentId());// check this line
					stForm.setTempDesignationPfId(stForm.getDesignationPfId());*/
					
					EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
					
					List<EmployeeTO> employeeToList = handler.getSearchedEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {

						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
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
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.EMPLOYEE_VIEW_LISTPAGE);
			}
			
			@SuppressWarnings({ "unused", "deprecation" })
			private boolean validatefutureDate(String dateString) {
				log.info("enter validatefutureDate..");
				String formattedString = CommonUtil.ConvertStringToDateFormat(
						dateString, EmployeeInfoViewAction.FROM_DATEFORMAT,
						EmployeeInfoViewAction.TO_DATEFORMAT);
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

			
			
			
			
			//.....................View My Page.........................................
			
			
			public ActionForward initViewMyInformation11(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				log.info("Entering into the initViewMyLeave in EmployeeApplyLeaveAction");
				EmployeeApplyLeaveForm employeeApplyLeaveForm = (EmployeeApplyLeaveForm) form;
				employeeApplyLeaveForm.resetFields();
				setUserId(request,employeeApplyLeaveForm);
				try{
				List<EmpApplyLeaveTO> applyLeaveTo=EmployeeApplyLeaveHandler.getInstance().getEmpApplyLeaves(employeeApplyLeaveForm);
				employeeApplyLeaveForm.setApplyLeaveTo(applyLeaveTo);
				List<EmpLeaveTO> empLeaveTO=EmployeeApplyLeaveHandler.getInstance().getEmpLeaves(employeeApplyLeaveForm);
				employeeApplyLeaveForm.setEmpLeaveTO(empLeaveTO);
				request.setAttribute("Operation", "viewMyLeave");
				}catch(Exception e){
					String msg = super.handleApplicationException(e);
					employeeApplyLeaveForm.setErrorMessage(msg);
					employeeApplyLeaveForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				log.info("Exit from the initViewMyLeave in EmployeeApplyLeaveAction");
				return mapping.findForward(CMSConstants.VIEW_MY_EMPLOYEE_INFORMATION);
			}


			
			public ActionForward initViewMyInformation(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				setUserId(request,objform);
				if( StringUtils.isNotEmpty(objform.getUserId()))
				{
				cleanUpPageData(objform);
				initializeQualificationAndEducation(objform);
				initializeLoanFinancial(objform);
				initializeFeeConcessionRemarks(objform);
				initializeIncentivesAchievements(objform);
				initializeDependents(objform);
				initializeImmigration(objform);
				setDataToFormView(objform);
				setUserId(request,objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				flag=EmployeeViewHandler.getInstance().getMyDetails(objform);
				if(CMSConstants.LINK_FOR_CJC){
					if (objform.getPhotoBytes()!= null){
						HttpSession session = request.getSession(false);
						if (session != null) {
							session.setAttribute(EmployeeInfoViewAction.PHOTOBYTES, objform.getPhotoBytes());
						}	
					}
				}else{
					request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+objform.getEmployeeId()+".jpg?"+new Date());
				}
				//EmployeeViewHandler.getInstance().AgeCalculation(objform);
				}
				if(flag){
					return mapping.findForward(CMSConstants.VIEW_MY_EMPLOYEE_INFORMATION);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.NO_RECORDS));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.VIEW_MY_EMPLOYEE_INFORMATION);
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
			
			public ActionForward initViewGeneralInformation(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				setUserId(request,objform);
				if( StringUtils.isNotEmpty(objform.getUserId()))
				{
				cleanUpPageData(objform);
				initializeQualificationAndEducation(objform);
				initializeLoanFinancial(objform);
				initializeFeeConcessionRemarks(objform);
				initializeIncentivesAchievements(objform);
				initializeDependents(objform);
				initializeImmigration(objform);
				setDataToFormView(objform);
				setUserId(request,objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				flag=EmployeeViewHandler.getInstance().getMyDetails(objform);
				if (objform.getPhotoBytes()!= null)
				{
				HttpSession session = request.getSession(false);
					if (session != null) {
						session.setAttribute(EmployeeInfoViewAction.PHOTOBYTES, objform.getPhotoBytes());
					}	
				}
				//EmployeeViewHandler.getInstance().AgeCalculation(objform);
				}
				if(flag){
					return mapping.findForward(CMSConstants.VIEW_GENERAL_EMPLOYEE_INFORMATION);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.NO_RECORDS));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.VIEW_GENERAL_EMPLOYEE_INFORMATION);
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
			
		
			public ActionForward getSearchedEmployeeGen(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm stForm = (EmployeeInfoViewForm) form;
				
				ActionMessages errors = stForm.validate(mapping, request);
			try {
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);
					}
										
					EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
					
					List<EmployeeTO> employeeToList = handler.getSearchedEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {

						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);
                       return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);

					}
					stForm.setEmployeeToList(employeeToList);
				} catch (ApplicationException e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);

				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);

				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_LIST);
			}
		
			
			public ActionForward InitEmpIdGen(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
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
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);
			}
			
			public ActionForward loadEmployeeGenInfo(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
				{
				cleanUpPageData(objform);
				setDataToFormView(objform);
				setUserId(request,objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				flag=EmployeeViewHandler.getInstance().getApplicantDetails(objform);
					if(CMSConstants.LINK_FOR_CJC){
						if (objform.getPhotoBytes()!= null){
							HttpSession session = request.getSession(false);
							if (session != null) {
								session.setAttribute(EmployeeInfoViewAction.PHOTOBYTES, objform.getPhotoBytes());
							}	
						}
					}else{
						request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+objform.getSelectedEmployeeId()+".jpg?"+new Date());
					}
				}
				if(flag){
					return mapping.findForward(CMSConstants.VIEW_GENERAL_EMPLOYEE_INFORMATION);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_GENERAL_VIEW_SEARCH);
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
			//-----------------------------Department wise View------------------------------------------------
			public ActionForward getInitDeptEmployee(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm stForm = (EmployeeInfoViewForm) form;
				setUserId(request,stForm);
				stForm.setDisplayMessage(null);
				ActionMessages errors = stForm.validate(mapping, request);
			try {
					stForm.setTempEmployeeId(null);
					stForm.setTempCode(null);
					stForm.setTempFingerPrintId(null);
					stForm.setTempName(null);
					stForm.setDisplayMessage(null);
					
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
					}
					EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
					
					List<EmployeeTO> employeeToList = handler.getSearchedDeptEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);
						stForm.setEmployeeToList(employeeToList);
						return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
						
					}
					stForm.setEmployeeToList(employeeToList);
				} catch (ApplicationException e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);

				
					} catch (BusinessException ex) {
						log.error("error in getSearchedStudents...", ex);
						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);

						return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);

					}catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
			}
			
			public ActionForward getDeptSearchedEmployee(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				EmployeeInfoViewForm stForm = (EmployeeInfoViewForm) form;
				setUserId(request,stForm);
				stForm.setDisplayMessage(null);
				ActionMessages errors = stForm.validate(mapping, request);
			try {
					stForm.setDisplayMessage(null);
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
					}
					EmployeeViewHandler handler = EmployeeViewHandler.getInstance();
					List<EmployeeTO> employeeToList = handler.getSrchDeptEmp(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {

						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
						saveMessages(request, messages);

						return mapping
								.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);

					}
					stForm.setEmployeeToList(employeeToList);
				} 
				catch(ApplicationException e){
					String str = "Employee department belongs to "+stForm.getDepartmentName();
					request.setAttribute("Operation", "viewDepEmpLeaves");
					stForm.setDisplayMessage(str);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
				}
				catch (BusinessException ex) {
					String str = "No match Found for given Name";
					stForm.setDisplayMessage(str);
     				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);

					
				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(EmployeeInfoViewAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
			}
			
			public ActionForward loadDeptEmpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				EmployeeInfoViewForm objform = (EmployeeInfoViewForm) form;
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
				{
				cleanUpPageData(objform);
				initializeQualificationAndEducation(objform);
				setDataToFormView(objform);
				setUserId(request,objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				flag=EmployeeViewHandler.getInstance().getApplicantDetails(objform);
				if(CMSConstants.LINK_FOR_CJC){
					if (objform.getPhotoBytes()!= null){
						HttpSession session = request.getSession(false);
						if (session != null) {
							session.setAttribute(EmployeeInfoViewAction.PHOTOBYTES, objform.getPhotoBytes());
						}	
					}
				}else{
					request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/E"+objform.getSelectedEmployeeId()+".jpg?"+new Date());
				}
				}
				if(flag){
					return mapping.findForward(CMSConstants.VIEW_DEPT_WISE_EMPLOYEE_INFORMATION);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.EMPLOYEE_INFO_DEPT_VIEW_LIST);
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
		}
	
