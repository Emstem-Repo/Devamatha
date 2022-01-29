package com.kp.cms.actions.admission;
	import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.UniqueIdRegistrationForm;
import com.kp.cms.handlers.admin.MaintenanceAlertHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.OnlineApplicationHandler;
import com.kp.cms.handlers.admission.UniqueIdRegistrationHandler;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.admin.IProgramTypeTransaction;
import com.kp.cms.transactionsimpl.admin.ProgramTypeTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


	@SuppressWarnings("deprecation")
	public class UniqueIdRegistrationAction  extends BaseDispatchAction {
		
		private static final Log log = LogFactory.getLog(UniqueIdRegistrationAction.class);
		
		private static final String FROM_DATEFORMAT="dd/MM/yyyy";
		private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
		private static final String PHOTOBYTES="PhotoBytes";
		
		public ActionForward initOnlineApplicationRegistration(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			HttpSession session = request.getSession(true);
			clearSession(session);
			objForm.clearPage();
			objForm.setOfflinePage(false);
			// Maintenance Alert message
			setMaintenanceAlertMessage(objForm, session);
			objForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
			//Map<Integer, String> subReligionMap=CommonAjaxHandler.getInstance().getSubReligion();
			//objForm.setSubReligionMap(subReligionMap);
			objForm.setNativeCountry(CMSConstants.INDIAN_RESIDENT_ID);
			
			IProgramTypeTransaction protrn=new ProgramTypeTransactionImpl();
			List l=protrn.getProgramTypeOnlineOpen();
			//if(date1.compareTo(date2)>0)
			if(l.size()==0)
			{
			  // ActionMessages errors=admForm.validate(mapping, request);
			  // errors.add("knowledgepro.adminssions.closed",new ActionError("knowledgepro.adminssions.closed"));
						
			   System.out.println("Date1 is after Date2");
			   log.info("exit admissions closed in time init initOutsideSinglePageAccess...");
			   return mapping.findForward(CMSConstants.ADMISSIONFORM_ONLINEAPPLY_PAGE_DATE);
		   }
			
			return mapping.findForward("UniqueIdRegistrationPage");
		}
		
		public ActionForward initOfflineApplicationRegistration(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			HttpSession session = request.getSession(true);
			clearSession(session);
			objForm.clearPage();
			objForm.setOfflinePage(true);
			// Maintenance Alert message
			setMaintenanceAlertMessage(objForm, session);
			return mapping.findForward("UniqueIdRegistrationPage");
		}
		/**
		 * @param objForm
		 * @param session 
		 * @throws Exception 
		 */
		private void setMaintenanceAlertMessage(UniqueIdRegistrationForm objForm, HttpSession session) throws Exception {
			String maintenanceMessage = MaintenanceAlertHandler.getInstance().getMaintenanceDetailsByDate();
			if (maintenanceMessage != null) {
				objForm.setServerDownMessage(maintenanceMessage);
				session.setAttribute("serverDownMessage", maintenanceMessage);
			}
		}
		/**
		 * @param session
		 * @throws Exception
		 */
		private void clearSession(HttpSession session)throws Exception {
			if(session!=null){
				session.setAttribute("PhotoBytes",null);
			}
		}
		
		public ActionForward initOnlineApplicationLogin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			HttpSession session = request.getSession(true);
			clearSession(session);
			objForm.clearPage();
			
			ActionMessages errors = new ActionMessages();
			String errorinJSP=null;
			if(session.getAttribute("errorinJSP")!=null){
				errorinJSP=(String)session.getAttribute("errorinJSP");
			}
			
			if(errorinJSP!=null){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admission.empty.err.message","Error occured in jsp page, please login again"));
				saveErrors(request, errors);
			}
			
			session.invalidate();
			// Maintenance Alert message
			setMaintenanceAlertMessage(objForm, session);
		return mapping.findForward("UniqueIdRegistrationLoginPage");
	}
		public ActionForward initOfflineApplicationLogin(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
				UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
				HttpSession session = request.getSession(true);
				clearSession(session);
				objForm.clearPage();
				objForm.setOfflinePage(true);
				// Maintenance Alert message
				setMaintenanceAlertMessage(objForm, session);
			return mapping.findForward("UniqueIdRegistrationLoginPage");
		}
	
		public ActionForward initOnlineApplicationForgotUniqueId(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			HttpSession session = request.getSession(true);
			clearSession(session);
			objForm.clearPage();
			// Maintenance Alert message
			setMaintenanceAlertMessage(objForm, session);
			return mapping.findForward("UniqueIdForgotPage");
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward loginOnlineApplication(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			HttpSession session = request.getSession(false);
			clearSession(session);
//			create new Session object.
			request.getSession(true);
			ActionErrors errors = null;
			if(session.getAttribute("onlineApplicationForm")!=null){
				session.setAttribute("onlineApplicationForm",null);
			}
			errors = objForm.validate(mapping, request);
    		objForm.setMode("login");
			objForm.setEmailInfoList(null);
			objForm.setSmsInfoList(null);
			objForm.setAdmissionStatusTOList(null);
			objForm.setIncompleteApplication(null);
			objForm.setDisplayName("applyCourses");
			objForm.setIsPhoto("false");
			session.setAttribute("PhotoBytes",null);
			boolean loginPage=false;
			try {
				if (objForm.getUniqueId()!=null && !objForm.getUniqueId().isEmpty() && ((objForm.getLoginDateOfBirth()!=null && !objForm.getLoginDateOfBirth().isEmpty())
						|| (objForm .getRegisterDateOfBirth()!=null && !objForm .getRegisterDateOfBirth().isEmpty()))) {
					if (errors.isEmpty()) {
						if (request.getParameter("propertyName") != null && (request.getParameter("propertyName").equalsIgnoreCase("back") || objForm.getPropertyName().equalsIgnoreCase("back"))) {
							if (objForm.getLoginDateOfBirth() == null || objForm.getLoginDateOfBirth().isEmpty()) {
								objForm.setLoginDateOfBirth(objForm .getRegisterDateOfBirth());
							}
						}
						boolean isSuccess = UniqueIdRegistrationHandler .getInstance().loginApplicant(objForm, session);
						if (!isSuccess) {
							objForm.setEmailInfoList(null);
							objForm.setSmsInfoList(null);
							errors .add( "error", new ActionError( "knowledgepro.admission.empty.err.message",
									"Please enter valid UniqueId and Date of Birth"));
							saveErrors(request, errors);
							loginPage=true;
							
						} else {
							
							//get fees
							getApplicationFees(objForm);
							session.setAttribute("isOnline", "1");
							// ------------ Online Application First Page Instruction -
							// Admission----------------
							/*String instructionMsg = AdmissionFormHandler.getInstance() .getOnlineAplnAdmInstructionTemplate();
							if (instructionMsg != null && !instructionMsg.isEmpty()) {
								objForm.setOnlineApplnInstructionMsg(instructionMsg);
							} else {
								objForm.setOnlineApplnInstructionMsg(null);
							}*/
						}
					} else {
						saveErrors(request, errors);
						loginPage=true;
					}
				}else{
					loginPage=true;
				}
			} catch (Exception e) {
					String msg = super.handleApplicationException(e);
					objForm.setErrorMessage(msg);
					objForm.setErrorStack(e.getMessage());
					 errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
				     saveErrors(request, errors);
				
					System.out.println("************************ error details in online admission loginOnlineApplication*************************"+e);
					return mapping.findForward("UniqueIdRegistrationLoginPage");
					//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			
			if(objForm.getOfflinePage() && !loginPage){
				return mapping.findForward("UniqueIdRegistrationSecondPageOffline");
			}else if(loginPage){
				return mapping.findForward("UniqueIdRegistrationLoginPage");
			}else
			{
				return mapping.findForward("UniqueIdRegistrationSecondPage");
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
		public ActionForward registerOnlineApplication(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			ActionErrors errors = null;
			errors = objForm.validate(mapping, request);
			objForm.setMode("register");
			HttpSession session = request.getSession(true);
			clearSession(session);
			
			if(objForm.getEmailId()!=null && objForm.getConfirmEmailId()!=null && !objForm.getEmailId().equals(objForm.getConfirmEmailId())){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
						   "Email id is different from confirm email id."));
				saveErrors(request, errors);
			}
			
			boolean isExistMail = UniqueIdRegistrationHandler
								 .getInstance().IsExistedMail(objForm);
			
			if(isExistMail){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
						   "E-mail id already exists, please register with a new email id."));
				saveErrors(request, errors);
			}
			
			boolean isMobileExist = UniqueIdRegistrationHandler
									.getInstance().IsExistedMobileNumber(objForm);
			
			if(isMobileExist){
				errors.add("error", new ActionError("knowledgepro.admission.empty.err.message",
						   "Mobile number already exists, please register with a new mobile number."));
				saveErrors(request, errors);
			}
			
			try {
				if (errors.isEmpty()) {
					boolean isSuccess = UniqueIdRegistrationHandler
							.getInstance().registerApplicant(objForm, errors);
					
					if (!isSuccess) {
						errors.add("error", new ActionError(
								"knowledgepro.admission.empty.err.message",
								"Registration is already exists"));
						saveErrors(request, errors);
						return mapping .findForward("UniqueIdRegistrationPage");
					}else{
						//get fees
						getApplicationFees(objForm);
						session.setAttribute("isOnline", "1");
					}
					
					
				} else {
					saveErrors(request, errors);
					return mapping.findForward("UniqueIdRegistrationPage");
				}
			} catch (Exception e) {
				e.printStackTrace();
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				 errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			
				System.out.println("************************ error details in online admission registerOnlineApplication*************************"+e);
				return mapping.findForward("UniqueIdRegistrationLoginPage");
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward("UniqueIdRegistrationMsgPage");
		}

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward checkMail(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			ActionMessages errors = new ActionMessages();
			
			try {
				if (objForm.getEmailId() != null && !objForm.getEmailId().isEmpty()) {
					Map<String, String> map = new HashMap<String, String>();
					boolean isExistMail = UniqueIdRegistrationHandler
							.getInstance().IsExistedMail(objForm);
					if (!isExistMail) {
						map.put("false", "false");
					} else {
						map.put("true", "true");
					}
					request.setAttribute(CMSConstants.OPTION_MAP, map);
				}
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				 errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			
				System.out.println("************************ error details in online admission checkMail*************************"+e);
				return mapping.findForward("UniqueIdRegistrationLoginPage");
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward("ajaxResponseForOptions");
		}

		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward getforgotUniqueId(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UniqueIdRegistrationForm objForm = (UniqueIdRegistrationForm) form;
			ActionErrors errors = null;
			errors = objForm.validate(mapping, request);
			objForm.setMode("forgotUniqueId");
			String link = "";
			try {
				boolean isAvailable = UniqueIdRegistrationHandler.getInstance() .getForgotUniqueId(objForm);
				if (isAvailable) {
					link = "UniqueIdRegistrationMsgPage";
				} else {
					errors.add("error", new ActionError( "knowledgepro.admission.empty.err.message", "Please enter the valid Email and Date of Birth"));
					saveErrors(request, errors);
					link = "UniqueIdForgotPage";
				}
			} catch (Exception e) {
				String msg = super.handleApplicationException(e);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(e.getMessage());
				 errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
			     saveErrors(request, errors);
			
				System.out.println("************************ error details in online admission getforgotUniqueId*************************"+e);
				return mapping.findForward("UniqueIdRegistrationLoginPage");
				//return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
			return mapping.findForward(link);
		}
		
		
		//get application fees
		private void getApplicationFees(UniqueIdRegistrationForm objForm)throws Exception {
			
			//IUniqueIdRegistration idRegistration=UniqueIdRegistrationImpl.getInstance();
			//String amount=idRegistration.getApplicationFees(objForm.getAcademicYear(),objForm.getProgramTypeId(),objForm.getSubReligionId());
			
			String amount="0";
			
			if(objForm.getProgramTypeId()!=null && objForm.getProgramTypeId().equalsIgnoreCase("1")){
			if (objForm.getIsCommuQuota()=="1" || objForm.getIsCommuQuota().equals("1")) {
				amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.COMMUNITYUG_FEE)));
			}
			if (objForm.getMngQuota()=="1" || objForm.getMngQuota().equals("1")) {
				amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.ONINE_APPLICATION_UG_FEE)));
			}
			}else{
				if (objForm.getIsCommuQuota()=="1" || objForm.getIsCommuQuota().equals("1")) {
					amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.COMMUNITYPG_FEE)));
				}
				if (objForm.getMngQuota()=="1" || objForm.getMngQuota().equals("1")) {
					amount=String.valueOf((Integer.parseInt(amount)+Integer.parseInt(CMSConstants.ONINE_APPLICATION_PG_FEE)));
				}
			}
			
			if(amount!=null && !amount.equalsIgnoreCase("")){
				String amountWords=CommonUtil.numberToWord1(Integer.parseInt(amount));
				objForm.setApplicationAmount(amount);
				objForm.setApplicationAmountWords(amountWords);
			}
			
		}
		
		//print challan for tieupbank
		public ActionForward forwardChallanTemplateTieupBank(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			 	UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			    
			 	//int year=Calendar.YEAR;
			    //String y= String.valueOf(year);
				//int year1=Integer.parseInt(y.substring(2));
				//String latestChallanNo=OnlineApplicationHandler.getInstance().getNewChallanNo();
				//admForm.setChallanRefNo(latestChallanNo+"/"+year1+"/"+(year1+1));
			 	
				//random generation of 4 nos 
				//String uniq = PasswordGenerator.getPasswordNum();
				//String uniqalpha = PasswordGenerator.getPasswordAlpha();
				//admForm.setChallanRefNo(CMSConstants.COLLEGE_CODE+uniq+uniqalpha);
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().equalsIgnoreCase("")){
					return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CHALLAN_TIEUPBANK);
				}else{
					return mapping.findForward("UniqueIdRegistrationLoginPage");
				}
				
		}
		
		
		//print challan for tieupbank
		public ActionForward forwardChallanTemplateOtherBank(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			 	UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			    
			 	//int year=Calendar.YEAR;
			    //String y= String.valueOf(year);
				//int year1=Integer.parseInt(y.substring(2));
				//String latestChallanNo=OnlineApplicationHandler.getInstance().getNewChallanNo();
				//admForm.setChallanRefNo(latestChallanNo+"/"+year1+"/"+(year1+1));
			 	
				//random generation of 4 nos 
				//String uniq = PasswordGenerator.getPasswordNum();
				//String uniqalpha = PasswordGenerator.getPasswordAlpha();
				//admForm.setChallanRefNo(CMSConstants.COLLEGE_CODE+uniq+uniqalpha);
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().equalsIgnoreCase("")){
					return mapping.findForward(CMSConstants.ONLINE_APPLICATION_CHALLAN_OTHERBANK);
				}else{
					return mapping.findForward("UniqueIdRegistrationLoginPage");
				}
				
		}
		
		public ActionForward printAppln(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			//set caste show or not
			//admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
			try{
			
			
				String applicationNumber =admForm.getApplicationNo().trim();
				
				
			int applicationYear = Integer.parseInt(admForm.getAcademicYear());
			
			//make values null
			admForm.setApplicantDetails(null);

		
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationDetails(applicationNumber, applicationYear);
			
			if( applicantDetails == null){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
					messages.add("messages", message);
					saveMessages(request, messages);
					
					return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);
				
			} else {
				if(applicantDetails.getId()>0)
				{/*
					String txnRefNo = AdmissionFormHandler.getInstance().getCandidatePGIDetails(applicantDetails.getId());
					if(txnRefNo!=null && !txnRefNo.isEmpty())
					admForm.setTxnRefNo(txnRefNo);
					AdmissionFormHandler.getInstance().getOnlinePaymentAcknowledgementDetails(admForm,applicantDetails.getId());
				*/}
				
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				//set applicant details
				admForm.setApplicantDetails(applicantDetails);
				/*String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
				if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
				{
					admForm.setWorkExpNeeded(true);
				}else{
					admForm.setWorkExpNeeded(false);
				}*/
					admForm.setApplicantDetails(applicantDetails);
					
				ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
					List<ProgramTypeTO> programtypes = programtypehandler
								.getProgramType();
					// set flags
					if (programtypes!=null) {/*
						admForm.setEditProgramtypes(programtypes);
						Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
						while (typeitr.hasNext()) {
							ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
							if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
								if(typeTO.getPrograms()!=null){
									admForm.setEditprograms(typeTO.getPrograms());
									Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
										while (programitr.hasNext()) {
											ProgramTO programTO = (ProgramTO) programitr
													.next();
											if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
					*/}
					
					
					//checkExtradetailsDisplay(admForm);
					//checkLateralTransferDisplay(admForm);
					// Admitted Through
					//List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
					//admForm.setAdmittedThroughList(admittedList);
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
//					if(admForm.isDisplayAdditionalInfo())
//					{
						
						List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
						if(tos!=null && !tos.isEmpty())
						{
							OrganizationTO orgTO=tos.get(0);
							admForm.setOrganizationName(orgTO.getOrganizationName());
							//admForm.setNeedApproval(orgTO.isNeedApproval());
						}
//					}
					
				
				
				//if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					//applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				
				// set photo to session
				HttpSession session= request.getSession(false);
				//session.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+admForm.getStudentId()+".jpg?"+applicantDetails.getLastModifiedDate());
				
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							//admForm.setSubmitDate(docTO.getSubmitDate());
						}
						if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
						
							//HttpSession session= request.getSession(false);
							if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo") && docTO.isDefaultPhoto() )
							{
								if(session!=null){
									byte[] fileData= null;
									// set default photo image
									try {
										InputStream photoin=UniqueIdRegistrationAction.class.getClassLoader().getResourceAsStream(CMSConstants.PRINT_PHOTO_PATH);
										if(photoin!=null){
											fileData= new byte[photoin.available()];
											photoin.read(fileData, 0, photoin.available());	
										}
									} catch (Exception e) {
										log.error(e);
									}
									
									if(fileData!=null)
									session.setAttribute(UniqueIdRegistrationAction.PHOTOBYTES,fileData );
								}
							}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
								
								if(session!=null){
									session.setAttribute(UniqueIdRegistrationAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
				}//*/
				
				//get template and replace the data
				//String template=AdmissionFormHandler.getInstance().getDeclarationTemplate(applicantDetails,request);
				//admForm.setInstrTemplate(template);
				
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					StringBuffer orgAdd=new StringBuffer();
					orgAdd.append(organisation.getAddressLine1());
					if(organisation.getAddressLine2()!=null && !StringUtils.isEmpty(organisation.getAddressLine2())){
					orgAdd.append(",");
					orgAdd.append(organisation.getAddressLine2());
					}
					if(organisation.getAddressLine3()!=null && !StringUtils.isEmpty(organisation.getAddressLine3())){
					orgAdd.append(",");
					orgAdd.append(organisation.getAddressLine3());
					}
					
					//admForm.setOrgAddress(orgAdd.toString());
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
							
						}
					}
				}
				
				//------------bar code creation
				if(!applicationNumber.trim().isEmpty()){
					String imgPath=request.getRealPath("");
					imgPath = imgPath + "//BarCode//"+ applicationNumber + ".jpeg";
					File barCodeImgFile =  new File(imgPath);
					if(barCodeImgFile.exists()){
						barCodeImgFile.delete();
					}
					Barcode code = BarcodeFactory.createCode128A(applicationNumber);
					code.setBarWidth(1);
					code.setBarHeight(40);
					code.setDrawingText(false);
					BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
				}					
				
			}
			}catch (Exception e){
				log.error("Error in  getApplicantDetails application form approval page...",e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					admForm.setErrorMessage(msg);
					admForm.setErrorStack(e.getMessage());
					System.out.println("************************ error details in online admission print pdf*************************"+e);
					
					return mapping.findForward("UniqueIdRegistrationSecondPage");
				}else {
					System.out.println("************************ error details in online admission print pdf*************************"+e);
					
					return mapping.findForward("UniqueIdRegistrationSecondPage");
				}
			}
			//return mapping.findForward("printPDF");
			return mapping.findForward(CMSConstants.ADMISSIONFORM_PRINT_PAGE);
		}
		

		
		
		public ActionForward printApplnPDF(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			//set caste show or not
			//admForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
			try{
			
			
				String applicationNumber =admForm.getApplicationNo().trim();
				
				
			int applicationYear = Integer.parseInt(admForm.getAcademicYear());
			
			//make values null
			admForm.setApplicantDetails(null);

		
			AdmApplnTO applicantDetails = AdmissionFormHandler.getInstance().getApplicationDetails(applicationNumber, applicationYear);
			
			if( applicantDetails == null){
				
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS);
					messages.add("messages", message);
					saveMessages(request, messages);
					
					return mapping.findForward(CMSConstants.ADMISSIONFORM_CONFIRMPRINT_PAGE);
				
			} else {
				if(applicantDetails.getId()>0)
				{/*
					String txnRefNo = AdmissionFormHandler.getInstance().getCandidatePGIDetails(applicantDetails.getId());
					if(txnRefNo!=null && !txnRefNo.isEmpty())
					admForm.setTxnRefNo(txnRefNo);
					AdmissionFormHandler.getInstance().getOnlinePaymentAcknowledgementDetails(admForm,applicantDetails.getId());
				*/}
				if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getDob()!=null )
					applicantDetails.getPersonalData().setDob(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getDob(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				if(applicantDetails.getChallanDate()!=null )
					applicantDetails.setChallanDate(CommonUtil.ConvertStringToDateFormat(applicantDetails.getChallanDate(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				//set applicant details
				admForm.setApplicantDetails(applicantDetails);
				/*String workExpNeeded=admForm.getApplicantDetails().getCourse().getIsWorkExperienceRequired();
				if(admForm.getApplicantDetails().getCourse()!=null && "Yes".equalsIgnoreCase(workExpNeeded))
				{
					admForm.setWorkExpNeeded(true);
				}else{
					admForm.setWorkExpNeeded(false);
				}*/
					admForm.setApplicantDetails(applicantDetails);
				ProgramTypeHandler programtypehandler = ProgramTypeHandler.getInstance();
					List<ProgramTypeTO> programtypes = programtypehandler
								.getProgramType();
					// set flags
					if (programtypes!=null) {/*
						admForm.setEditProgramtypes(programtypes);
						Iterator<ProgramTypeTO> typeitr= programtypes.iterator();
						while (typeitr.hasNext()) {
							ProgramTypeTO typeTO = (ProgramTypeTO) typeitr.next();
							if(typeTO.getProgramTypeId()==admForm.getApplicantDetails().getCourse().getProgramTypeId()){
								if(typeTO.getPrograms()!=null){
									admForm.setEditprograms(typeTO.getPrograms());
									Iterator<ProgramTO> programitr= typeTO.getPrograms().iterator();
										while (programitr.hasNext()) {
											ProgramTO programTO = (ProgramTO) programitr
													.next();
											if(programTO.getId()== admForm.getApplicantDetails().getCourse().getProgramId()){
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
					*/}
					
					
					//checkExtradetailsDisplay(admForm);
					//checkLateralTransferDisplay(admForm);
					// Admitted Through
					//List<AdmittedThroughTO> admittedList=AdmittedThroughHandler.getInstance().getAdmittedThrough();
					//admForm.setAdmittedThroughList(admittedList);
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
//					if(admForm.isDisplayAdditionalInfo())
//					{
						
						List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
						if(tos!=null && !tos.isEmpty())
						{
							OrganizationTO orgTO=tos.get(0);
							admForm.setOrganizationName(orgTO.getOrganizationName());
							//admForm.setNeedApproval(orgTO.isNeedApproval());
						}
//					}
					
				
				
				//if(applicantDetails.getPersonalData()!=null && applicantDetails.getPersonalData().getPassportValidity()!=null && !StringUtils.isEmpty(applicantDetails.getPersonalData().getPassportValidity()) )
					//applicantDetails.getPersonalData().setPassportValidity(CommonUtil.ConvertStringToDateFormat(applicantDetails.getPersonalData().getPassportValidity(), UniqueIdRegistrationAction.SQL_DATEFORMAT,UniqueIdRegistrationAction.FROM_DATEFORMAT));
				
				// set photo to session
				HttpSession session= request.getSession(false);
				//session.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+admForm.getStudentId()+".jpg?"+applicantDetails.getLastModifiedDate());
				
				if(applicantDetails.getEditDocuments()!=null){
					Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
					while (docItr.hasNext()) {
						ApplnDocTO docTO = (ApplnDocTO) docItr.next();
						if(docTO.getSubmitDate()!=null && !StringUtils.isEmpty(docTO.getSubmitDate()))
						{
							//admForm.setSubmitDate(docTO.getSubmitDate());
						}
						if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
						
							//HttpSession session= request.getSession(false);
							if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo") && docTO.isDefaultPhoto() )
							{
								if(session!=null){
									byte[] fileData= null;
									// set default photo image
									try {
										InputStream photoin=UniqueIdRegistrationAction.class.getClassLoader().getResourceAsStream(CMSConstants.PRINT_PHOTO_PATH);
										if(photoin!=null){
											fileData= new byte[photoin.available()];
											photoin.read(fileData, 0, photoin.available());	
										}
									} catch (Exception e) {
										log.error(e);
									}
									
									if(fileData!=null)
									session.setAttribute(UniqueIdRegistrationAction.PHOTOBYTES,fileData );
								}
							}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
								
								if(session!=null){
									session.setAttribute(UniqueIdRegistrationAction.PHOTOBYTES, docTO.getPhotoBytes());
								}
							}
						}
					}
				}//*/
				
				//get template and replace the data
				//String template=AdmissionFormHandler.getInstance().getDeclarationTemplate(applicantDetails,request);
				//admForm.setInstrTemplate(template);
				
				Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if(organisation!=null){
					StringBuffer orgAdd=new StringBuffer();
					orgAdd.append(organisation.getAddressLine1());
					if(organisation.getAddressLine2()!=null && !StringUtils.isEmpty(organisation.getAddressLine2())){
					orgAdd.append(",");
					orgAdd.append(organisation.getAddressLine2());
					}
					if(organisation.getAddressLine3()!=null && !StringUtils.isEmpty(organisation.getAddressLine3())){
					orgAdd.append(",");
					orgAdd.append(organisation.getAddressLine3());
					}
					
					//admForm.setOrgAddress(orgAdd.toString());
					// set photo to session
					if(organisation.getLogoContentType()!=null){
						if(session!=null){
							session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
							
						}
					}
				}
				
				//------------bar code creation
				if(!applicationNumber.trim().isEmpty()){
					String imgPath=request.getRealPath("");
					imgPath = imgPath + "//BarCode//"+ applicationNumber + ".jpeg";
					File barCodeImgFile =  new File(imgPath);
					if(barCodeImgFile.exists()){
						barCodeImgFile.delete();
					}
					Barcode code = BarcodeFactory.createCode128A(applicationNumber);
					code.setBarWidth(1);
					code.setBarHeight(40);
					code.setDrawingText(false);
					BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
				}					
				
			}
			}catch (Exception e){
				log.error("Error in  getApplicantDetails application form approval page...",e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					admForm.setErrorMessage(msg);
					admForm.setErrorStack(e.getMessage());
					System.out.println("************************ error details in online admission print pdf*************************"+e);
					
					return mapping.findForward("UniqueIdRegistrationSecondPage");
				}else {
					System.out.println("************************ error details in online admission print pdf*************************"+e);
					
					return mapping.findForward("UniqueIdRegistrationSecondPage");
				}
			}
			
			//return mapping.findForward(CMSConstants.ADMISSIONFORM_PRINT_PAGE);
			return mapping.findForward("printPDF");
		}
		
		
		
		public ActionForward forwardBasicInfo(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			 	UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			    
			 	
				return mapping.findForward("basicInfoMethod");
				
				
		}

		
		public ActionForward forwardOutsideAccess(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			 	UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			 	request.setAttribute("mode","CurrentID");
			 	request.setAttribute("admApplnId",admForm.getAccoId());
			 	//onlineApplicationSubmit.do?method=initOutsideSinglePageAccess&admApplnId="+id+"&mode=CurrentID
				return mapping.findForward("outsideAccessMethod");
				
				
		}


		public ActionForward getStatusOfStudent(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			 	UniqueIdRegistrationForm admForm = (UniqueIdRegistrationForm) form;
			 	//onlineApplicationSubmit.do?method=initOutsideSinglePageAccess&admApplnId="+id+"&mode=CurrentID
				return mapping.findForward("outsideAccessStatus");
				
				
		}


	}
