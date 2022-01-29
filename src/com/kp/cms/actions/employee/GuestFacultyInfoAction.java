package com.kp.cms.actions.employee;
	import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import com.kp.cms.forms.employee.GuestFacultyInfoForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.GuestFacultyInfoHandler;
import com.kp.cms.to.employee.EligibilityTestTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.GuestFacultyInfoTo;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.employee.GuestPreviousChristWorkDetailsTO;
import com.kp.cms.to.employee.GuestPreviousExperienceTO;
import com.kp.cms.transactionsimpl.employee.GuestFacultyInfoImpl;
import com.kp.cms.utilities.CommonUtil;

	@SuppressWarnings("deprecation")
	public class GuestFacultyInfoAction   extends BaseDispatchAction {
			private static final Log log = LogFactory.getLog(GuestFacultyInfoAction.class);
			private static final String MESSAGE_KEY = "messages";
			private static final String PHOTOBYTES = "PhotoBytes";
			private static final String TO_DATEFORMAT = "MM/dd/yyyy";
			private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
			
			private void cleanupEditSessionData(HttpServletRequest request) {
				log.info("enter cleanupEditSessionData...");
				HttpSession session = request.getSession(false);
				if (session == null) {
					return;
				} else {
					if (session.getAttribute(GuestFacultyInfoAction.PHOTOBYTES) != null)
						session.removeAttribute(GuestFacultyInfoAction.PHOTOBYTES);
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
			public ActionForward initGuestAdd(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				objform.setApplicationNO(null);
				
				return mapping.findForward(CMSConstants.INIT_GUEST_INFO_ADD);
			}
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
			//	GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				return mapping.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);
			}
			
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward loadResumeInfo(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				if( StringUtils.isNotEmpty(objform.getApplicationNO()));
				{
				cleanUpPageData(objform);
				
				setDataToForm(objform);
				setUserId(request,objform);
				flag=GuestFacultyInfoHandler.getInstance().getApplicantResumeDetails(objform);
				
				if (objform.getPhotoBytes()!= null)
				{
				HttpSession session = request.getSession(false);
					if (session != null) {
						session.setAttribute(GuestFacultyInfoAction.PHOTOBYTES, objform.getPhotoBytes());
					}	
				  }
				}
				if(flag){
					objform.setForwardFlag("true");
					return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
				}
				else
				{
					objform.setForwardFlag("false");	
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.GUEST_NOT_VALID_APPLICATIONNO));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_GUEST_INFO_ADD);
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
					
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
					String flag=null;
					cleanupEditSessionData(request);
					if( StringUtils.isNotEmpty(objform.getApplicationNO()))
					{
						loadResumeInfo(mapping, form, request,response);
						flag=objform.getForwardFlag();
						if(flag.equals("false"))
							return mapping.findForward(CMSConstants.INIT_GUEST_INFO_ADD);	
						else
							return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
					}
					else
						
						initEmployeeInfo(mapping, form, request,response);
					return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
					}
			
			
			
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward getSearchedGuest(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				GuestFacultyInfoForm stForm = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				ActionMessages errors = null;
				//errors=stForm.validate(mapping, request);
			try {
				if (errors != null && !errors.isEmpty()) {
						saveErrors(request, errors);
						return mapping
								.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);
					}
					GuestFacultyInfoHandler handler = GuestFacultyInfoHandler.getInstance();
					List<GuestFacultyTO> employeeToList = handler.getSearchedEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {
						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
						saveMessages(request, messages);
						return mapping
								.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);
					}
					stForm.setEmployeeToList(employeeToList);
				} catch (ApplicationException e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);

				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);

				}
				log.info("exit getSearchedStudents..");
				return mapping.findForward(CMSConstants.GUEST_EDITLISTPAGE);
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
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				objform.setTempEmployeeId(null);
				objform.setTempUid(null);
				objform.setTempCode(null);
				objform.setTempName(null);
				objform.setTempDesignationPfId(null);
				objform.setTempDepartmentId(null);
				objform.setTempActive("1");
				objform.setTempStreamId(null);
				objform.setTempTeachingStaff("2");
				setDataToInitForm(objform);
				return mapping.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);
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
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				cleanUpPageData(objform);
				ActionMessages messages=new ActionMessages();
				try
				{
				boolean flag=false;
				if( StringUtils.isNotEmpty(objform.getSelectedEmployeeId()))
				{
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				setDataToForm(objform);
				setUserId(request,objform);
				flag=GuestFacultyInfoHandler.getInstance().getApplicantDetails(objform);
				if(CMSConstants.LINK_FOR_CJC){
					if (objform.getPhotoBytes()!= null){
						HttpSession session = request.getSession(false);
						if (session != null) {
							session.setAttribute(GuestFacultyInfoAction.PHOTOBYTES, objform.getPhotoBytes());
						}	
					}
				}else{
				
					request.setAttribute("EMP_IMAGE", "images/EmployeePhotos/G"+objform.getSelectedEmployeeId()+".jpg?"+new Date());
				}
				}
				if(flag){
					return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
				}
				else
				{
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_GUEST_INFO_EDIT);
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
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				objform.setNationalityId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
				objform.setCurrentCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
				objform.setCountryId(String.valueOf(CMSConstants.KP_DEFAULT_COUNTRY_ID));
				objform.setTempEmployeeId(objform.getEmployeeId());
				cleanUpPageData(objform);
				if(request.getSession().getAttribute("PhotoBytes")!=null){
					request.getSession().removeAttribute("PhotoBytes");
				}
				//initializeQualificationAndEducation(objform);
				setDataToForm(objform);
				setUserId(request,objform);
		//		GuestFacultyInfoHandler handler = GuestFacultyInfoHandler.getInstance();
		//		handler.getSearchedEmployee(objform);
				log.info("Exit from the initEmployeeInfo in EmployeeInfoAction");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
			}

			/**
			 * @param objform
			 */
			private void cleanUpPageData(GuestFacultyInfoForm objform) {
				log.info("enter cleanUpPageData..");
				
				if (objform != null) {
					objform.setListSize(null);
					objform.setFocusValue(null);
		            objform.setBankAccNo(null);
		           objform.setEmployeeId(null);
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
					
					
					objform.setDateOfJoining(null);
					
					objform.setFourWheelerNo(null);
					
					objform.setEmContactHomeTel(null);
					objform.setEmContactMobile(null);
					objform.setEmContactName(null);
					objform.setEmContactRelationship(null);
					objform.setEmContactWorkTel(null);
					objform.setEmail(null);
					
					objform.setMobile(null);
					objform.setMobileNo1(null);
					objform.setMobileNo2(null);
					objform.setMobileNo3(null);
					objform.setMaritalStatus(null);
					objform.setMobPhone2(null);
					objform.setMobPhone3(null);
					objform.setMode(null);
					
					objform.setNoOfPublicationsNotRefered(null);
					objform.setNoOfPublicationsRefered(null);
					objform.setName(null);
					objform.setOrgAddress(null);
					objform.setOrganizationName(null);
					objform.setOtherInfo(null);
					objform.setPanno(null);
					
					objform.setPermanentZipCode(null);
					objform.setPfNo(null);
					objform.setHomePhone1(null);
					objform.setHomePhone2(null);
					objform.setHomePhone3(null);
					
					objform.setReligion(null);
					
					objform.setReservationCategory(null);
					
					objform.setStatus(null);
					objform.setTelNo(null);
					objform.setTwoWheelerNo(null);
					objform.setuId(null);
					objform.setCode(null);
					objform.setVehicleNo(null);
					
					
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
					
					objform.setEligibilityTestNET(null);
					objform.setEligibilityTestNone(null);
					objform.setEligibilityTestSET(null);
					objform.setEligibilityTestSLET(null);
					
					objform.setEmpImageId(null);
					objform.setIndustryFunctionalArea(null);
					objform.setEligibilityTestOther(null);
					objform.setEligibilityTest(null);
					objform.setOtherEligibilityTestValue(null);
					objform.setHandicappedDescription(null);
					objform.setEmContactAddress(null);
					objform.setEligibilityList(null);
					objform.setBloodGroup(null);
					objform.setSemester(null);
					objform.setSubjectSpecilization(null);
					objform.setHonorariumPerHours(null);
					objform.setWorkingHoursPerWeek(null);
					objform.setReferredBy(null);
					objform.setPhotoBytes(null);
					objform.setForwardFlag(null);	
					objform.setEmpImageId(null);
					objform.setGuestId(null);
					objform.setEmpSubjectAreaId(null);
					objform.setIndustryFunctionalArea(null);
					objform.setHighQualifForWebsite(null);
					objform.setStaffId(null);
					objform.setDisplayInWebsite("0");
					objform.setBankBranch(null);
					objform.setBankIfscCode(null);
					objform.setPreviousStaffId(null);
				}
				log.info("exit cleanUpPageData..");

			}
			/**
			 * @param employeeInfoEditForm
			 */
			private void initializeQualificationAndEducation(GuestFacultyInfoForm employeeInfoEditForm) {
				List<GuestPreviousExperienceTO> list=new ArrayList<GuestPreviousExperienceTO>();
				GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
				empPreviousOrgTo.setIndustryExpYears("");
				empPreviousOrgTo.setIndustryExpMonths("");
				empPreviousOrgTo.setCurrentDesignation("");
				empPreviousOrgTo.setCurrentOrganisation("");
				employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()));
				list.add(empPreviousOrgTo);
				
				List<GuestPreviousExperienceTO> teachingList=new ArrayList<GuestPreviousExperienceTO>();
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
			public  void setDataToInitForm(GuestFacultyInfoForm employeeInfoEditForm)throws Exception {
				boolean isCjc = CMSConstants.LINK_FOR_CJC;
				employeeInfoEditForm.setIsCjc(isCjc);
				GuestFacultyInfoHandler.getInstance().getInitialPageData(employeeInfoEditForm);
				employeeInfoEditForm.setTempActive("1");
			}
			
			public  void setDataToForm(GuestFacultyInfoForm employeeInfoEditForm)throws Exception {
				boolean isCjc = CMSConstants.LINK_FOR_CJC;
				employeeInfoEditForm.setIsCjc(isCjc);
				GuestFacultyInfoHandler.getInstance().getInitialData(employeeInfoEditForm);
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
				initializeQualificationAndEducation(employeeInfoEditForm);
				initializeCurrentWorkData(employeeInfoEditForm);
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
				log.info("Befinning of resetExperienceInfo of GUEST_FACULTY_DETAILS");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
						empPreviousOrgTo.setIndustryExpYears("");
						empPreviousOrgTo.setIndustryExpMonths("");
						empPreviousOrgTo.setCurrentDesignation("");
						empPreviousOrgTo.setCurrentOrganisation("");
						employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						employeeInfoEditForm.setMode(null);
						String size=String.valueOf(list.size()-1);
						employeeInfoEditForm.setFocusValue("industry_"+size);
					}
				}
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);

		}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward resetExperienceInfoEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of GUEST_FACULTY_DETAILS");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
						empPreviousOrgTo.setIndustryExpYears("");
						empPreviousOrgTo.setIndustryExpMonths("");
						empPreviousOrgTo.setCurrentDesignation("");
						empPreviousOrgTo.setCurrentOrganisation("");
						employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						employeeInfoEditForm.setMode(null);
						String size=String.valueOf(list.size()-1);
						employeeInfoEditForm.setFocusValue("industry_"+size);
					}
				}
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);

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
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<GuestPreviousExperienceTO> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
						if(list.size()>0)
						list.remove(list.size()-1);
						employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()-1));
					}
				}
				log.info("End of removeExperienceInfo of GUEST_FACULTY_DETAILS");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);

			}
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward removeExperienceInfoEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of removeExperienceInfo of EmpResumeSubmissionAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<GuestPreviousExperienceTO> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getExperiences()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						// add one blank to add extra one
						list=employeeInfoEditForm.getEmployeeInfoTONew().getExperiences();
						if(list.size()>0)
						list.remove(list.size()-1);
						employeeInfoEditForm.setIndustryExpLength(String.valueOf(list.size()-1));
					}
				}
				log.info("End of removeExperienceInfo of GUEST_FACULTY_DETAILS");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);

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
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
						empPreviousOrgTo.setTeachingExpYears("");
						empPreviousOrgTo.setTeachingExpMonths("");
						empPreviousOrgTo.setCurrentTeachnigDesignation("");
						empPreviousOrgTo.setCurrentTeachingOrganisation("");
						employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						employeeInfoEditForm.setMode(null);
						String size=String.valueOf(list.size()-1);
						employeeInfoEditForm.setFocusValue("teach_"+size);
					}
				}
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
		}
			
			public ActionForward resetTeachingExperienceInfoEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmpInfoAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
						empPreviousOrgTo.setTeachingExpYears("");
						empPreviousOrgTo.setTeachingExpMonths("");
						empPreviousOrgTo.setCurrentTeachnigDesignation("");
						empPreviousOrgTo.setCurrentTeachingOrganisation("");
						employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
						list.add(empPreviousOrgTo);
						employeeInfoEditForm.setMode(null);
						String size=String.valueOf(list.size()-1);
						employeeInfoEditForm.setFocusValue("teach_"+size);
					}
				}
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
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
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
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
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
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
			public ActionForward resetTeachingExperienceInfo1Edit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmpInfoAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getMode()!=null){
					if (employeeInfoEditForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						List<GuestPreviousExperienceTO> list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						
						GuestPreviousExperienceTO empPreviousOrgTo=new GuestPreviousExperienceTO();
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
				log.info("End of resetExperienceInfo of GuestFacultyInfoForm");
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
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<GuestPreviousExperienceTO> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience().size()>0){
						list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						list.remove(list.size()-1);
						employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
				}
				employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of GUEST_FACULTY_DETAILS");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
			}
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward removeTeachingExperienceInfoEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<GuestPreviousExperienceTO> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience()!=null)
				if(employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience().size()>0){
						list=employeeInfoEditForm.getEmployeeInfoTONew().getTeachingExperience();
						list.remove(list.size()-1);
						employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()));
				}
				employeeInfoEditForm.setTeachingExpLength(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of GUEST_FACULTY_DETAILS");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
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
				GuestFacultyInfoForm employeeInfoEditForm =(GuestFacultyInfoForm)form;
				List<EmpQualificationLevelTo> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
						if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()>0){
								list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
								list.remove(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()-1);
						}
					}
				employeeInfoEditForm.setLevelSize(String.valueOf(list.size()));
				employeeInfoEditForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
			}
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward removeQualificationLevelEdit(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
				GuestFacultyInfoForm employeeInfoEditForm =(GuestFacultyInfoForm)form;
				List<EmpQualificationLevelTo> list=null;
				if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos()!=null){
						if(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()>0){
								list=employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos();
								list.remove(employeeInfoEditForm.getEmployeeInfoTONew().getEmpQualificationLevelTos().size()-1);
						}
					}
				employeeInfoEditForm.setLevelSize(String.valueOf(list.size()));
				employeeInfoEditForm.getEmployeeInfoTONew().setEmpQualificationLevelTos(list);
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
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
				GuestFacultyInfoForm employeeInfoEditForm =(GuestFacultyInfoForm)form;
				ActionMessages messages=new ActionMessages();
				ActionErrors errors=employeeInfoEditForm.validate(mapping, request);
				validateEditPhone(employeeInfoEditForm,errors);
				//validateEmployee(employeeInfoEditForm,errors);
				validateData(employeeInfoEditForm,errors);
				validateDate(employeeInfoEditForm,errors);
				//validateUnique(employeeInfoEditForm, errors);
				boolean flag=false;
				if(errors.isEmpty()){
					try {
						//staffid duplicate checking by giri
						if(employeeInfoEditForm.getStaffId()!=null && !employeeInfoEditForm.getStaffId().isEmpty()){
							Boolean staffDupl=CommonAjaxHandler.getInstance().checkDupilcateOfStaffId(employeeInfoEditForm.getStaffId());
							if(staffDupl){
								errors.add("error", new ActionError("KnowledgePro.guest.staffid.duplicate"));
								saveErrors(request, errors);
								return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
							}
						}
						//end by giri
						flag=GuestFacultyInfoHandler.getInstance().saveEmp(employeeInfoEditForm);
						if(flag){
							
							ActionMessage message=new ActionMessage(CMSConstants.GUEST_SUBMIT_SUCCESS);
							messages.add(CMSConstants.MESSAGES,message);
							saveMessages(request, messages);
							cleanupEditSessionData(request);
							return mapping.findForward(CMSConstants.GUEST_SUBMIT_SUCCESS);
						}else{
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
							saveMessages(request, messages);
							cleanupEditSessionData(request);
							return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
						}
					} catch (Exception exception) {
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					}
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
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
			public ActionForward saveEditEmpDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
				GuestFacultyInfoForm employeeInfoEditForm =(GuestFacultyInfoForm)form;
				ActionMessages messages=new ActionMessages();
				ActionErrors errors=employeeInfoEditForm.validate(mapping, request);
				validateEditPhone(employeeInfoEditForm,errors);
				validateData(employeeInfoEditForm,errors);
				validateDate(employeeInfoEditForm,errors);
				boolean flag=false;
				if(errors.isEmpty()){
					try {
						//staffid duplicate checking by giri
						if(employeeInfoEditForm.getStaffId()!=null && !employeeInfoEditForm.getStaffId().isEmpty() 
								&& employeeInfoEditForm.getPreviousStaffId()!=null && !employeeInfoEditForm.getPreviousStaffId().isEmpty()){
							if(!employeeInfoEditForm.getStaffId().equalsIgnoreCase(employeeInfoEditForm.getPreviousStaffId())){
								Boolean staffDupl=CommonAjaxHandler.getInstance().checkDupilcateOfStaffId(employeeInfoEditForm.getStaffId());
								if(staffDupl){
									errors.add("error", new ActionError("KnowledgePro.guest.staffid.duplicate"));
									saveErrors(request, errors);
									return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
								}
							}
						}else if(employeeInfoEditForm.getStaffId()!=null && !employeeInfoEditForm.getStaffId().isEmpty()){
							if(employeeInfoEditForm.getPreviousStaffId()==null){
								Boolean staffDupl=CommonAjaxHandler.getInstance().checkDupilcateOfStaffId(employeeInfoEditForm.getStaffId());
								if(staffDupl){
									errors.add("error", new ActionError("KnowledgePro.guest.staffid.duplicate"));
									saveErrors(request, errors);
									return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
								}
							}
						}
						//end by giri
						flag=GuestFacultyInfoHandler.getInstance().saveEmpEdit(employeeInfoEditForm);
						if(flag){
							
							ActionMessage message=new ActionMessage(CMSConstants.GUEST_EDIT_SUBMIT_SUCCESS);
							messages.add(CMSConstants.MESSAGES,message);
							saveMessages(request, messages);
							cleanupEditSessionData(request);
							return mapping.findForward(CMSConstants.GUEST_EDIT_SUBMIT_SUCCESS);
						}else{
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
						}
					} catch (Exception exception) {
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					}
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
				}
			}
			
			/**
			 * @param employeeInfoEditForm
			 * @param errors
			 */
			@SuppressWarnings("unused")
			private void validateEmployee(GuestFacultyInfoForm employeeInfoEditForm, ActionErrors errors) {
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

			
			
			private void validateData(GuestFacultyInfoForm employeeInfoEditForm,ActionErrors errors) {
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

	@SuppressWarnings("unused")
	private void validateFields(GuestFacultyInfoForm employeeInfoFormNew,ActionErrors errors) {
		log.info("enter validateEditPhone..");
		if (errors == null)
			errors = new ActionErrors();
		
		
		
		if (employeeInfoFormNew.getuId() != null && !employeeInfoFormNew.getuId().isEmpty() && employeeInfoFormNew.getuId().trim().length() > 10)  {
			if (errors.get(CMSConstants.EMPLOYEE_UID_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_UID_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_UID_INVALID);
				errors.add(CMSConstants.EMPLOYEE_UID_INVALID, error);
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
		
	    if (employeeInfoFormNew.getFingerPrintId() != null && !employeeInfoFormNew.getFingerPrintId().isEmpty() && employeeInfoFormNew.getFingerPrintId().trim().length() > 30)  {
			if (errors.get(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID) != null
					&& !errors.get(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID);
				errors.add(CMSConstants.EMPLOYEE_FINGERPRINTID_INVALID, error);
			}
		}
	   
		
	log.info("exit validateEditPhone..");
	}

			
			private void validateEditPhone(GuestFacultyInfoForm employeeInfoFormNew,ActionErrors errors
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
			}
			
			/**
			 * @param employeeInfoEditFormb
			 * @param errors
			 */
			private void validateDate(GuestFacultyInfoForm employeeInfoFormNew,ActionErrors errors) {
			
			
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
			
			 if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails()!=null && !employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails().isEmpty())
			   {
				  
				   List<GuestPreviousChristWorkDetailsTO> depTo = employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails();
					Iterator<GuestPreviousChristWorkDetailsTO> itr = depTo.iterator();
					GuestPreviousChristWorkDetailsTO empDepTO;
					while (itr.hasNext()) {
						empDepTO = itr.next();
						if(empDepTO.getStartDate()==null || empDepTO.getStartDate().isEmpty())
						{
							if (errors.get(CMSConstants.GUEST_STARTDATE_REQUIRED) != null && !errors.get(CMSConstants.GUEST_STARTDATE_REQUIRED).hasNext()) {
								errors.add(CMSConstants.GUEST_STARTDATE_REQUIRED, new ActionError(CMSConstants.GUEST_STARTDATE_REQUIRED));
							}
						}
						if(empDepTO.getStartDate()!=null && !empDepTO.getStartDate().isEmpty() ){

							if (!CommonUtil.isValidDate(empDepTO.getStartDate())) 
								{
								if (errors.get(CMSConstants.GUEST_STARTDATE_INVALID) != null && !errors.get(CMSConstants.GUEST_STARTDATE_INVALID).hasNext()) {
									errors.add(CMSConstants.GUEST_STARTDATE_INVALID, new ActionError(CMSConstants.GUEST_STARTDATE_INVALID));
								}
							}
						}
						
						if(empDepTO.getEndDate()==null || empDepTO.getEndDate().isEmpty())
						{
							if (errors.get(CMSConstants.GUEST_ENDDATE_REQUIRED) != null && !errors.get(CMSConstants.GUEST_ENDDATE_REQUIRED).hasNext()) {
								errors.add(CMSConstants.GUEST_ENDDATE_REQUIRED, new ActionError(CMSConstants.GUEST_ENDDATE_REQUIRED));
							}
						}
						if(empDepTO.getEndDate()!=null && !empDepTO.getEndDate().isEmpty() ){

							if (!CommonUtil.isValidDate(empDepTO.getEndDate())) 
								{
								if (errors.get(CMSConstants.GUEST_ENDDATE_INVALID) != null && !errors.get(CMSConstants.GUEST_ENDDATE_INVALID).hasNext()) {
									errors.add(CMSConstants.GUEST_ENDDATE_INVALID, new ActionError(CMSConstants.GUEST_ENDDATE_INVALID));
								}
							}
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
			public ActionForward addQualificationLevel(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
				log.info("Befinning of addQualificationLevel of EmpResumeSubmissionAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<EmpQualificationLevelTo> list=null;
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
				log.info("End of addQualificationLevel of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
			}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward addQualificationLevelEdit(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response)throws Exception{
				log.info("Befinning of addQualificationLevel of EmpResumeSubmissionAction");
				GuestFacultyInfoForm employeeInfoEditForm=(GuestFacultyInfoForm)form;
				List<EmpQualificationLevelTo> list=null;
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
				log.info("End of addQualificationLevel of GuestFacultyInfoForm");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
			}
		
		
		
			@SuppressWarnings("unused")
			private void validateUnique(GuestFacultyInfoForm employeeInfoFormNew , ActionErrors errors) {
				log.info("enter validateEditPhone..");
				if (errors == null)
					errors = new ActionErrors();
				if (employeeInfoFormNew.getuId() != null && !StringUtils.isEmpty(employeeInfoFormNew.getuId().trim()) ) {
					boolean duplicateUid;
					try {
						duplicateUid = GuestFacultyInfoHandler.getInstance().checkUidUnique(employeeInfoFormNew.getuId(), employeeInfoFormNew.getSelectedEmployeeId());
					
					if (!duplicateUid) {
						errors.add(CMSConstants.ADDEMPLOYEE_UID_NOTUNIQUE,
								new ActionError(CMSConstants.ADDEMPLOYEE_UID_NOTUNIQUE));
						
					}
					
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				
				if (employeeInfoFormNew.getCode()!= null && !StringUtils.isEmpty(employeeInfoFormNew.getCode().trim()) ) {
					boolean duplicatecode;
					try {
						duplicatecode = GuestFacultyInfoHandler.getInstance().checkCodeUnique(employeeInfoFormNew.getCode(), employeeInfoFormNew.getSelectedEmployeeId());
					
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
				GuestFacultyInfoForm stForm = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
			try {
				
					
					GuestFacultyInfoHandler handler = GuestFacultyInfoHandler.getInstance();
					
					List<GuestFacultyTO> employeeToList = handler.getSearchedEmployee(stForm);
					if (employeeToList == null || employeeToList.isEmpty()) {

						ActionMessages messages = new ActionMessages();
						ActionMessage message = null;
						message = new ActionMessage(
								CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
						messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
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
					messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_INFO_EMPLOYEEID);

				} catch (Exception e) {
					log.error("error in getSearchedStudents...", e);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					message = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(GuestFacultyInfoAction.MESSAGE_KEY, message);
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
						dateString, GuestFacultyInfoAction.FROM_DATEFORMAT,
						GuestFacultyInfoAction.TO_DATEFORMAT);
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
			
			private void initializeCurrentWorkData(GuestFacultyInfoForm employeeInfoFormNew)throws Exception {
				
				List<GuestPreviousChristWorkDetailsTO> list=new ArrayList<GuestPreviousChristWorkDetailsTO>();
				GuestPreviousChristWorkDetailsTO guestWorkTo=new GuestPreviousChristWorkDetailsTO();
				guestWorkTo.setStartDate("");
				guestWorkTo.setEndDate("");
				guestWorkTo.setSemester("");
				//start by giri
				guestWorkTo.setDeptId("");
				guestWorkTo.setStrmId("");
				guestWorkTo.setWorkLocId("");
				guestWorkTo.setWorkHoursPerWeek("");
				guestWorkTo.setHonorarium("");
				Map<String,String> workMap=GuestFacultyInfoImpl.getInstance().getWorkLocationMap();
				 if(workMap!=null){
					 guestWorkTo.setWorkMap(workMap);
				 }
				 Map<String,String> departmentMap=GuestFacultyInfoImpl.getInstance().getDepartmentMap();
				 if(departmentMap!=null){
					 guestWorkTo.setDeptMap(departmentMap);
				 }
				 Map<String,String> streamMap=GuestFacultyInfoImpl.getInstance().getStreamMap();
				 if(streamMap!=null){
					 guestWorkTo.setStrmMap(streamMap);
				 }
				 //end by giri
				guestWorkTo.setIsCurrentWorkingDates(true);
				employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list.size()));
				list.add(guestWorkTo);
						
				employeeInfoFormNew.getEmployeeInfoTONew().setPreviousworkDetails(list);
				
			}

			public ActionForward resetPrevWork(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				GuestFacultyInfoForm employeeInfoFormNew=(GuestFacultyInfoForm)form;
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails()!=null)
				if(employeeInfoFormNew.getMode()!=null){
					if (employeeInfoFormNew.getMode().equalsIgnoreCase("PrevWorkAddMore")) {
						List<GuestPreviousChristWorkDetailsTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails();
						List<GuestPreviousChristWorkDetailsTO> list1 = new ArrayList<GuestPreviousChristWorkDetailsTO>();
						if(list != null){
							Iterator<GuestPreviousChristWorkDetailsTO> iterator = list.iterator();
							while (iterator.hasNext()) {
								GuestPreviousChristWorkDetailsTO guestPreviousChristWorkDetailsTO = (GuestPreviousChristWorkDetailsTO) iterator.next();
								//by giri
								Map<String,String> departmentMap = GuestFacultyInfoHandler.getInstance().getFilteredDepartmentsStreamNames(guestPreviousChristWorkDetailsTO.getStrmId(),employeeInfoFormNew.getStaffId());
								guestPreviousChristWorkDetailsTO.setDeptMap(departmentMap);
								//end by giri
								guestPreviousChristWorkDetailsTO.setIsCurrentWorkingDates(false);
								list1.add(guestPreviousChristWorkDetailsTO);
							}
						}
						GuestPreviousChristWorkDetailsTO guestWorkTo=new GuestPreviousChristWorkDetailsTO();
						guestWorkTo.setStartDate("");
						guestWorkTo.setEndDate("");
						guestWorkTo.setSemester("");
						//start by giri
						guestWorkTo.setDeptId("");
						guestWorkTo.setStrmId("");
						guestWorkTo.setWorkLocId("");
						guestWorkTo.setWorkHoursPerWeek("");
						guestWorkTo.setHonorarium("");
						Map<String,String> workMap=GuestFacultyInfoImpl.getInstance().getWorkLocationMap();
						 if(workMap!=null){
							 guestWorkTo.setWorkMap(workMap);
						 }
						 Map<String,String> departmentMap=GuestFacultyInfoImpl.getInstance().getDepartmentMap();
						 if(departmentMap!=null){
							 guestWorkTo.setDeptMap(departmentMap);
						 }
						 Map<String,String> streamMap=GuestFacultyInfoImpl.getInstance().getStreamMap();
						 if(streamMap!=null){
							 guestWorkTo.setStrmMap(streamMap);
						 }
						 //end by giri
						guestWorkTo.setIsCurrentWorkingDates(true);
						employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list1.size()));
						list1.add(guestWorkTo);
						employeeInfoFormNew.setMode(null);
						String size=String.valueOf(list1.size()-1);
						employeeInfoFormNew.setFocusValue("startDate_"+size);
						GuestFacultyInfoTo to = employeeInfoFormNew.getEmployeeInfoTONew();
						to.setPreviousworkDetails(list1);
						employeeInfoFormNew.setEmployeeInfoTONew(to);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoFormNew");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
		}
			
			public ActionForward resetPrevWorkEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetEmpLoan of EmpInfoAction");
				GuestFacultyInfoForm employeeInfoFormNew=(GuestFacultyInfoForm)form;
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails()!=null)
				if(employeeInfoFormNew.getMode()!=null){
					if (employeeInfoFormNew.getMode().equalsIgnoreCase("PrevWorkAddMore")) {
						List<GuestPreviousChristWorkDetailsTO> list=employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails();
						List<GuestPreviousChristWorkDetailsTO> list1 = new ArrayList<GuestPreviousChristWorkDetailsTO>();
						if(list != null){
							Iterator<GuestPreviousChristWorkDetailsTO> iterator = list.iterator();
							while (iterator.hasNext()) {
								GuestPreviousChristWorkDetailsTO guestPreviousChristWorkDetailsTO = (GuestPreviousChristWorkDetailsTO) iterator.next();
								//by giri
								Map<String,String> departmentMap = GuestFacultyInfoHandler.getInstance().getFilteredDepartmentsStreamNames(guestPreviousChristWorkDetailsTO.getStrmId(),employeeInfoFormNew.getStaffId());
								guestPreviousChristWorkDetailsTO.setDeptMap(departmentMap);
								//end by giri
								guestPreviousChristWorkDetailsTO.setIsCurrentWorkingDates(false);
								list1.add(guestPreviousChristWorkDetailsTO);
							}
						}
						GuestPreviousChristWorkDetailsTO guestWorkTo=new GuestPreviousChristWorkDetailsTO();
						guestWorkTo.setStartDate("");
						guestWorkTo.setEndDate("");
						guestWorkTo.setSemester("");
						//start by giri
						guestWorkTo.setDeptId("");
						guestWorkTo.setStrmId("");
						guestWorkTo.setWorkLocId("");
						guestWorkTo.setWorkHoursPerWeek("");
						guestWorkTo.setHonorarium("");
						Map<String,String> workMap=GuestFacultyInfoImpl.getInstance().getWorkLocationMap();
						 if(workMap!=null){
							 guestWorkTo.setWorkMap(workMap);
						 }
						 Map<String,String> departmentMap=GuestFacultyInfoImpl.getInstance().getDepartmentMap();
						 if(departmentMap!=null){
							 guestWorkTo.setDeptMap(departmentMap);
						 }
						 Map<String,String> streamMap=GuestFacultyInfoImpl.getInstance().getStreamMap();
						 if(streamMap!=null){
							 guestWorkTo.setStrmMap(streamMap);
						 }
						 //end by giri
						guestWorkTo.setIsCurrentWorkingDates(true);
						employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list1.size()));
						list1.add(guestWorkTo);
						employeeInfoFormNew.setMode(null);
						String size=String.valueOf(list1.size()-1);
						employeeInfoFormNew.setFocusValue("startDate_"+size);
						GuestFacultyInfoTo to = employeeInfoFormNew.getEmployeeInfoTONew();
						to.setPreviousworkDetails(list1);
						employeeInfoFormNew.setEmployeeInfoTONew(to);
					}
				}
				log.info("End of resetEmpLoan of EmployeeInfoFormNew");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
		}
			public ActionForward removePrevWorkRow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmployeeInfoFormNew");
				GuestFacultyInfoForm employeeInfoFormNew=(GuestFacultyInfoForm)form;
				List<GuestPreviousChristWorkDetailsTO> list=null;
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails()!=null)
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails().size()>0){
						list=employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails();
						list.remove(list.size()-1);
						employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list.size()));
						String size=String.valueOf(list.size()-1);
						employeeInfoFormNew.setFocusValue("startDate_"+size);
				}
				employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_DETAILS);
			}
			
			public ActionForward removePrevWorkRowEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
				log.info("Befinning of resetExperienceInfo of EmployeeInfoFormNew");
				GuestFacultyInfoForm employeeInfoFormNew=(GuestFacultyInfoForm)form;
				List<GuestPreviousChristWorkDetailsTO> list=null;
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails()!=null)
				if(employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails().size()>0){
						list=employeeInfoFormNew.getEmployeeInfoTONew().getPreviousworkDetails();
						list.remove(list.size()-1);
						employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list.size()));
						String size=String.valueOf(list.size()-1);
						employeeInfoFormNew.setFocusValue("startDate_"+size);
				}
				employeeInfoFormNew.setPrevWorkListSize(String.valueOf(list.size()-1));
				log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_EDIT);
			}
			
			
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward getGuestDetails(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				log.info("Entering into the getGuestDetails in GuestInfoAction");
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				cleanupEditSessionData(request);
				ActionErrors errors = new ActionErrors();
				HttpSession session = request.getSession();
				session.setAttribute("PhotoBytes", null);
				try{
					GuestFacultyInfoHandler.getInstance().getResumeDetails(objform,session,request);
					objform.setPrintPage("true");
				}catch (Exception e) {
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.GUEST_EDITLISTPAGE);
				}
				GuestFacultyInfoHandler.getInstance().setEmployeeDetailsToForm(objform);
				log.info("Exit from the getGuestDetails in GuestInfoAction");
				return mapping.findForward(CMSConstants.GUEST_EDITLISTPAGE);
			}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward printGuest(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				log.info("Entering into the printGuest in GuestInfoAction");
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				objform.setPrintPage(null);
				GuestFacultyInfoHandler.getInstance().setEmployeeDetailsToForm(objform);
				log.info("Exit from the printGuest in GuestInfoAction");
				return mapping.findForward(CMSConstants.GUEST_FACULTY_PRINT_DETAILS);
			}
			
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward initGuestFacultyBankDetailsEdit(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				objform.reset1();
				setGuestFacultyMapToForm(objform);
				return mapping.findForward(CMSConstants.GUEST_FACULTY_BANK_DETAILS_EDIT);
			}
			/**
			 * @param objform
			 * @throws Exception
			 */
			@SuppressWarnings("unchecked")
			private void setGuestFacultyMapToForm(GuestFacultyInfoForm objform)throws Exception{
				Map<Integer,String> guestFacultyMap =GuestFacultyInfoHandler.getInstance().getGuestFacultyMap();
				guestFacultyMap=CommonUtil.sortMapByValue(guestFacultyMap);
				objform.setGuestFacultyMap(guestFacultyMap);
			}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward getGuestFacultyInformation(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				GuestFacultyInfoForm guestFacultyInfoForm = (GuestFacultyInfoForm) form;
				try{
					GuestFacultyTO facultyTo=GuestFacultyInfoHandler.getInstance().getGuestFacultyDetails(guestFacultyInfoForm);
					guestFacultyInfoForm.setGuestFacultyTo(facultyTo);
				}catch(Exception exception){
					log.error("Error occured in GuestFacultyInfoAction", exception);
					String msg = super.handleApplicationException(exception);
					guestFacultyInfoForm.setErrorMessage(msg);
					guestFacultyInfoForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.GUEST_FACULTY_BANK_DETAILS_EDIT);
			}
			/**
			 * @param mapping
			 * @param form
			 * @param request
			 * @param response
			 * @return
			 * @throws Exception
			 */
			public ActionForward saveEditedGuestFacultyBankDetails(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				GuestFacultyInfoForm objform = (GuestFacultyInfoForm) form;
				ActionMessages messages = new ActionMessages();
				ActionErrors errors=objform.validate(mapping, request);
				try{
					if(!objform.getBankAccNo().matches("^[0-9]+$")){
						errors.add("error", new ActionError( "knowledgepro.employee.bankaccno.format"));
					}
					if(errors.isEmpty()){
						boolean flag = GuestFacultyInfoHandler.getInstance().updateEditedGuestFacultyDetails(objform);
						if(flag){
							messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.employee.guestfaculty.bankdetails.updated"));
							saveMessages(request, messages);
							objform.reset1();
						}else{
							errors.add("error", new ActionError( "knowledgepro.employee.guestfaculty.bankdetails.update.failed"));
							addErrors(request, errors);
						}
					}else{
						addErrors(request, errors);
					}
					
				}catch(Exception exception){
					log.error("Error occured in GuestFacultyInfoAction", exception);
					String msg = super.handleApplicationException(exception);
					objform.setErrorMessage(msg);
					objform.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				return mapping.findForward(CMSConstants.GUEST_FACULTY_BANK_DETAILS_EDIT);
			}
		}
