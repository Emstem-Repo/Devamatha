package com.kp.cms.handlers.admission;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.PasswordMobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.constants.KPPropertiesConfiguration;
import com.kp.cms.forms.admission.UniqueIdRegistrationForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admin.CandidatePGIDetailsTO;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IUniqueIdRegistration;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.UniqueIdRegistrationImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PasswordGenerator;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.jms.SMS_Message;

public class UniqueIdRegistrationHandler {
	/* Singleton Design Pattern */
	private static volatile UniqueIdRegistrationHandler handler = null;
	private static final Log log = LogFactory.getLog(UniqueIdRegistrationHandler.class);
	
	public static UniqueIdRegistrationHandler getInstance() {
		if (handler == null) {
			handler = new UniqueIdRegistrationHandler();
		}
		return handler;
	}

	private UniqueIdRegistrationHandler() {

	}
	/* Singleton Design Pattern */
	
	IUniqueIdRegistration txnImpl = UniqueIdRegistrationImpl.getInstance();

	/**
	 * @param objForm
	 * @param session 
	 * @return
	 * @throws Exception
	 */
	public boolean loginApplicant(UniqueIdRegistrationForm objForm, HttpSession session)throws Exception {
		boolean isSuccess = false;
		String academicYear = txnImpl.getAdmissionAcademicYear();
		objForm.setYear(academicYear);
		StudentOnlineApplication onlineApplication= txnImpl.getLoginOnlineApplicationDetails(objForm,"login");
		// check whether user login through unique id or not, if it is true if-condition , otherwise else condition.
			if(onlineApplication!=null){
				objForm.setMobileNo(onlineApplication.getMobileNo());
				objForm.setMobileCode(onlineApplication.getMobileCode());
				objForm.setGender(onlineApplication.getGender());
				objForm.setEmailId(onlineApplication.getEmailId());
				objForm.setDob(CommonUtil.formatDates(onlineApplication.getDateOfBirth()));
				objForm.setOnlineApplicationUniqueId(onlineApplication.getId());
				objForm.setApplicantMobileNo(onlineApplication.getMobileNo());
				objForm.setApplicantMobileCode(onlineApplication.getMobileCode());
				objForm.setApplicantEmailId(onlineApplication.getEmailId());
				objForm.setApplicantName(onlineApplication.getName());
				objForm.setProgramTypeId(onlineApplication.getProgramTypeId());
				objForm.setResidentCategoryId(onlineApplication.getCategoryId());
				objForm.setSubReligionId(onlineApplication.getSubReligionId());
				objForm.setAcademicYear(onlineApplication.getYear()+"");
				objForm.setChallanRefNo(onlineApplication.getChallanNumber());
				if (onlineApplication.isMngQuota()==true) {
					objForm.setMngQuota("1");
				}
				if (onlineApplication.isMngQuota()==false) {
					objForm.setMngQuota("0");
				}
				session.setAttribute("MNGQUOTA", onlineApplication.isMngQuota());
				objForm.setAided(onlineApplication.getIsAided());
				session.setAttribute("COURSE_AIDED", onlineApplication.getIsAided());
				List<AdmAppln> admApplnList=txnImpl.getAppliedCoursesList(onlineApplication);
				setRequiredDataTOForm(admApplnList,objForm);
				setPhotoTOSession(admApplnList,session,objForm);
				// online payment details ,if adm_appln is null 
				setOnlinePaymentDetails(onlineApplication.getId(),objForm);
				objForm.setIsCommuQuota(onlineApplication.getIsCommuQuota());
				isSuccess =true;
			}else { // check whether user login through applnNo, if the admAppln contains uniqueId then set the value to the form.
				AdmAppln admAppln = txnImpl.getApplicationNoDetails(objForm,"login");
				if(admAppln!=null){
					if(admAppln.getStudentOnlineApplication()!=null){
						objForm.setMobileNo(admAppln.getStudentOnlineApplication().getMobileNo());
						objForm.setMobileCode(admAppln.getStudentOnlineApplication().getMobileCode());
						objForm.setGender(admAppln.getStudentOnlineApplication().getGender());
						objForm.setEmailId(admAppln.getStudentOnlineApplication().getEmailId());
						objForm.setDob(CommonUtil.formatDates(admAppln.getStudentOnlineApplication().getDateOfBirth()));
						objForm.setOnlineApplicationUniqueId(admAppln.getStudentOnlineApplication().getId());
						objForm.setApplicantMobileNo(admAppln.getStudentOnlineApplication().getMobileNo());
						objForm.setApplicantMobileCode(admAppln.getStudentOnlineApplication().getMobileCode());
						objForm.setApplicantEmailId(admAppln.getStudentOnlineApplication().getEmailId());
						objForm.setUniqueId(admAppln.getStudentOnlineApplication().getUniqueId());
						objForm.setSubReligionId(admAppln.getStudentOnlineApplication().getSubReligionId());
						objForm.setResidentCategoryId(admAppln.getStudentOnlineApplication().getCategoryId());
						objForm.setAcademicYear(admAppln.getStudentOnlineApplication().getYear()+"");
						objForm.setChallanRefNo(admAppln.getStudentOnlineApplication().getChallanNumber());
						
						List<AdmAppln> admApplnList=txnImpl.getAppliedCoursesList(admAppln.getStudentOnlineApplication());
						setRequiredDataTOForm(admApplnList,objForm);
						setPhotoTOSession(admApplnList,session,objForm);
						// online payment details ,if adm_appln is null 
						setOnlinePaymentDetails(admAppln.getStudentOnlineApplication().getId(),objForm);
						isSuccess =true;
					}else{
						objForm.setDisplayMode("applnNo");
						if(admAppln.getApplnNo()!=0){
							objForm.setApplicationNo(String.valueOf(admAppln.getApplnNo()));
						}
						List<AdmAppln> admApplnList = new ArrayList<AdmAppln>();
						admApplnList.add(admAppln);
						setRequiredDataTOForm(admApplnList,objForm);
						setPhotoTOSession(admApplnList,session,objForm);
						isSuccess =true;
					}
				}
			}
		return isSuccess;
	}
	/**
	 * @param uniqueId
	 * @param objForm
	 * @throws Exception
	 */
	private void setOnlinePaymentDetails(int uniqueId, UniqueIdRegistrationForm objForm) throws Exception {
		//raghu
		//List<CandidatePGIDetails> candidatePGIDetails = txnImpl.getOnlinePaymentDetails(uniqueId);
		//List<CandidatePGIDetailsTO> candidatePGIDetailsTOList = getCandidatePGIDetails(candidatePGIDetails);
		List<CandidatePGIDetailsTO> candidatePGIDetailsTOList = null;
		if(candidatePGIDetailsTOList!=null && !candidatePGIDetailsTOList.isEmpty()){
			objForm.setCandidatePGIDetailsTOs(candidatePGIDetailsTOList);
		}else{
			objForm.setCandidatePGIDetailsTOs(null);
		}
	}
	/**
	 * @param candidatePGIDetails
	 * @return
	 * @throws Exception
	 */
	private List<CandidatePGIDetailsTO> getCandidatePGIDetails( List<CandidatePGIDetails> candidatePGIDetails) throws Exception{
		List<CandidatePGIDetailsTO>   candidatePGIDetailsTOList = null;
		if(candidatePGIDetails!=null && !candidatePGIDetails.isEmpty()){
			candidatePGIDetailsTOList = new ArrayList<CandidatePGIDetailsTO>();
			for (CandidatePGIDetails bo : candidatePGIDetails) {
				CandidatePGIDetailsTO to  = new CandidatePGIDetailsTO();
				to.setId(bo.getId());
				if(bo.getRefundGenerated()!=null && bo.getRefundGenerated()){
					to.setRefundGenerated("Yes");
				}else if(bo.getRefundGenerated()!=null && !bo.getRefundGenerated()){
					to.setRefundGenerated("No");
				}
				if(bo.getCourse()!=null && bo.getCourse().getName()!=null && !bo.getCourse().getName().isEmpty()){
					to.setCourseName(bo.getCourse().getName());
				}
				to.setAdmApplnId("");
				candidatePGIDetailsTOList.add(to);
			}
		}
		return candidatePGIDetailsTOList;
	}

	/**
	 * @param admApplnList
	 * @param session
	 * @param objForm 
	 * @throws Exception
	 */
	private void setPhotoTOSession(List<AdmAppln> admApplnList, HttpSession session, UniqueIdRegistrationForm objForm) throws Exception{
		LinkedList<AdmAppln> admApplnsList = new LinkedList<AdmAppln>(admApplnList);
		AdmAppln admAppln = null;
		if(admApplnsList!=null && !admApplnsList.isEmpty()){
			 admAppln = admApplnsList.getLast();
			
			if(admAppln.getStudents()!=null && !admAppln.getStudents().isEmpty()){
				for (Student student : admAppln.getStudents()) {
					File file=new File(KPPropertiesConfiguration.STUDENT_PHOTO_PATH+student.getId()+".jpg");
					if (file.exists()) {
						objForm.setStudentPhotoName(student.getId()+".jpg");
						objForm.setIsPhoto("true");
					}else{
						objForm.setStudentPhotoName(null);
						objForm.setIsPhoto("false");
					}
				}
			}
			
			
			//new photo set
			
			//set photo
			Set<ApplnDoc> docUploadSet=admAppln.getApplnDocs();
			

			Iterator<ApplnDoc> iterator = docUploadSet.iterator();
			while (iterator.hasNext()) {
				
				ApplnDoc applnDocBO = (ApplnDoc) iterator.next();

				
				if(applnDocBO.getIsPhoto()!=null && applnDocBO.getIsPhoto()){
						
						//raghu
						if(applnDocBO.getDocument()!=null){
							byte [] myFileBytes = applnDocBO.getDocument();
							// applnDocTO.setPhotoBytes(myFileBytes);
							session.setAttribute("PhotoBytes", myFileBytes);	
							objForm.setIsPhoto("true");
						}else{
							objForm.setIsPhoto("false");
						}
						
					}
				
			}
			
			
		}
		/*// set photo to session
		if(admAppln!=null){
			for (ApplnDoc applnDoc : admAppln.getApplnDocs()) {
				 if(applnDoc!=null &&  applnDoc.getIsPhoto()!=null && applnDoc.getIsPhoto()){
					if(session!=null){
						session.setAttribute("PhotoBytes",  applnDoc.getDocument());
						objForm.setIsPhoto("true");
					}
				}
			}
		}*/
	}

	/**
	 * @param admApplnList
	 * @param objForm
	 * @param errors
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setRequiredDataTOForm(List<AdmAppln> admApplnList,
			UniqueIdRegistrationForm objForm) throws Exception {
		if(admApplnList!=null && !admApplnList.isEmpty()){
			LinkedList<AdmAppln> applnsList = new LinkedList(admApplnList);
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getFirstName()!=null){
				objForm.setApplicantName(applnsList.getLast().getPersonalData().getFirstName());
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getDateOfBirth()!=null){
				objForm.setDob(CommonUtil.formatDates(applnsList.getLast().getPersonalData().getDateOfBirth()));
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getMobileNo1()!=null && !applnsList.getLast().getPersonalData().getMobileNo1().isEmpty()){
				objForm.setApplicantMobileCode(applnsList.getLast().getPersonalData().getMobileNo1());
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getMobileNo2()!=null && !applnsList.getLast().getPersonalData().getMobileNo2().isEmpty()){
				objForm.setApplicantMobileNo(applnsList.getLast().getPersonalData().getMobileNo2());
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getEmail()!=null && !applnsList.getLast().getPersonalData().getEmail().isEmpty()){
				objForm.setApplicantEmailId(applnsList.getLast().getPersonalData().getEmail());
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getGender()!=null && !applnsList.getLast().getPersonalData().getGender().isEmpty()){
				objForm.setGender(applnsList.getLast().getPersonalData().getGender());
			}
			if(applnsList.getLast().getPersonalData()!=null && applnsList.getLast().getPersonalData().getResidentCategory()!=null){
				objForm.setResidentCategoryId(String.valueOf(applnsList.getLast().getPersonalData().getResidentCategory().getId()));
			}
			if(applnsList.getLast().getIsDraftMode()!=null && applnsList.getLast().getIsDraftMode()!=null){
				if(applnsList.getLast().getIsDraftMode()){
					objForm.setIsDraftMode("true");
					if(applnsList.getLast().getCurrentPageName()!=null && applnsList.getLast().getCurrentPageName()!=null){
						objForm.setCurrentPage(applnsList.getLast().getCurrentPageName());
					}
					else
					{
						objForm.setCurrentPage("basic");
					}
				
				}else{
					objForm.setIsDraftMode("false");
					objForm.setCurrentPage("basic");
				}
			}
			setApplicationStatusDetails(applnsList,objForm);
		}
	}

	/**
	 * @param applnsList
	 * @param objForm
	 * @param errors
	 * @throws Exception
	 */
	@SuppressWarnings({ "null", "rawtypes" })
	private void setApplicationStatusDetails(List<AdmAppln> applnsList, UniqueIdRegistrationForm objForm) throws Exception{

		objForm.setOnlineAcknowledgement(false);
		List<AdmissionStatusTO> admStatusTOList = new ArrayList<AdmissionStatusTO>();
		Map<Integer,List<String>> onlinePaymentDetailsMap = new HashMap<Integer, List<String>>();
		boolean isIncomplete=false;
		
		if(applnsList!=null && !applnsList.isEmpty()){
			for (AdmAppln admAppln : applnsList) {
				System.out.println(admAppln.getId());
				AdmissionStatusTO admissionStatusTO = new AdmissionStatusTO();
				
//				set required data to TO.
				admissionStatusTO=setDataFromAdmApplnBOToTO(admissionStatusTO,admAppln,objForm,onlinePaymentDetailsMap);
				
				
				if (admissionStatusTO.getIsSelected() == null || admissionStatusTO.getIsSelected().isEmpty())
				{
					admissionStatusTO.setInterviewStatus(CMSConstants.ADMISSION_ADMISSIONSTATUS_UNDER_PROCESS);
				}
				
				if(admAppln.getIsDraftMode()!= null && admAppln.getIsDraftCancelled()!= null)
				{ 
					
					if(admAppln.getMode()!=null){
						admissionStatusTO.setApplnMode("Online");
					} 
					
				if(admAppln.getIsDraftMode() && !admAppln.getIsDraftCancelled()){
						admissionStatusTO.setInterviewStatus("Please complete your application  ");
						admissionStatusTO.setIsInterviewSelected("DraftMode");
					}else if(admAppln.getIsDraftMode() && admAppln.getIsDraftCancelled()){
						admissionStatusTO.setInterviewStatus("Application Incomplete and Removed ");
					}else if(!admAppln.getIsDraftMode() && admAppln.getIsDraftCancelled()){
						admissionStatusTO.setInterviewStatus("Application Incomplete and Removed ");
					}
				
				
					else if(!admAppln.getIsDraftMode() && !admAppln.getIsDraftCancelled())
					{
						if(admissionStatusTO.getApplicationNo()!=0 && admissionStatusTO.getIsSelected()!=null && admissionStatusTO.getDateOfBirth() !=null){
		
							
							//					check whether the application is cancelled
							if(admissionStatusTO.isCancelled()){
								admissionStatusTO.setInterviewStatus(CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_CANCELLED);
		//							get sms and email details of the applied student.
								// setSMS_EMAIL_Details(admissionStatusTO,admAppln,objForm);
								 if(admissionStatusTO.getIsInterviewSelected()!=null && admissionStatusTO.getIsInterviewSelected().equalsIgnoreCase("DraftMode")){
									 isIncomplete=true;
								 }
								 admStatusTOList.add(admissionStatusTO);
								continue;
							}
							
							
		//					if admitted 
							if(admissionStatusTO.isAdmitted() && admissionStatusTO.isChallenVerified()){
								admissionStatusTO.setInterviewStatus("Admission Processed");
		//						get sms and email details of the applied student.
								// setSMS_EMAIL_Details(admissionStatusTO,admAppln,objForm);
								 if(admissionStatusTO.getIsInterviewSelected()!=null && admissionStatusTO.getIsInterviewSelected().equalsIgnoreCase("DraftMode")){
									 isIncomplete=true;
								 }
								 admStatusTOList.add(admissionStatusTO);
								continue;
							}
							
							
							
							
							//raghu write new
							if(admissionStatusTO.isDraftMode() && !admissionStatusTO.isDraftCancelled()){
								//admStatusTO.setIsInterviewSelected("DraftMode");
								isIncomplete=true;
							}
							
						
					
						}
						
					}
				
				
				
						admissionStatusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(admissionStatusTO.getDateOfBirth(), CMSConstants.SOURCE_DATE,CMSConstants.SOURCE_DATE));
						//--------added for bypass
						if(admissionStatusTO.isByPassed()){
							// if student is selected and also bypasses=1 then do not show the status. modified on 30/05/2015 by sudhir.
							//admissionStatusTO.setIsInterviewSelected(CMSConstants.ADMISSION);
							admissionStatusTO.setIsInterviewSelected("false");
							admissionStatusTO.setInterviewStatus("");
						}
						//-------
//							get sms and email details of the applied student.
						 //setSMS_EMAIL_Details(admissionStatusTO,admAppln,objForm);
						if(admissionStatusTO.getIsInterviewSelected()!=null && admissionStatusTO.getIsInterviewSelected().equalsIgnoreCase("DraftMode")){
							isIncomplete=true;
						}
						
						//raghu write new
						if(admissionStatusTO.isDraftMode() ){
							isIncomplete=true;
						}
						
						admStatusTOList.add(admissionStatusTO);
						
						
						
					}
				}
			}
		
		
		
		
		    if (isIncomplete) {
		    	objForm.setIncompleteApplication("Incomplete");
			}else{
				objForm.setIncompleteApplication(null);
				objForm.setDisplayName("applyCourses");
			}
		    
		    
			if(admStatusTOList!=null && !admStatusTOList.isEmpty()){
				//objForm.setDisplayName("dashBoard");
				objForm.setDisplayName("applyCourses");
				objForm.setAdmissionStatusTOList(admStatusTOList);
				
			}else{
				objForm.setAdmissionStatusTOList(null);
				objForm.setDisplayName("applyCourses");
				
			}
			
			
		}

	/**
	 * @param admissionStatusTO
	 * @param admAppln
	 * @param objForm
	 * @throws Exception
	 */
	private void setSMS_EMAIL_Details(AdmissionStatusTO admissionStatusTO, AdmAppln admAppln, UniqueIdRegistrationForm objForm)throws Exception {
		if(admAppln.getStudents()!=null && !admAppln.getStudents().isEmpty()){
			List<Student> studentList = new ArrayList<Student>(admAppln.getStudents());
			int studentId = studentList.get(0).getId();
			// get sms and email details for studentid.
			admissionStatusTO.setStudentId(studentId);
			
			
		}
	}

	

	/**
	 * @param admissionStatusTO
	 * @param admAppln
	 * @param objForm
	 * @param onlinePaymentDetailsMap 
	 * @throws Exception
	 */
	private AdmissionStatusTO setDataFromAdmApplnBOToTO(AdmissionStatusTO admissionStatusTO,
			AdmAppln admAppln, UniqueIdRegistrationForm objForm, Map<Integer, List<String>> onlinePaymentDetailsMap)throws Exception {
		InterviewResultTO interviewResultTO = null;
		if (admAppln != null) {
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			admissionStatusTO = new AdmissionStatusTO();
			
			/**
			 * Checks for the columns. If does not contain  null values then set those values from BO to TO
			 */
			admissionStatusTO.setId(admAppln.getId());
			if(admAppln.getIsBypassed()!= null && admAppln.getIsBypassed()){
				admissionStatusTO.setByPassed(admAppln.getIsBypassed());
			}
			if(admAppln.getIsDraftMode()!= null && admAppln.getIsDraftMode()){
				admissionStatusTO.setDraftMode(admAppln.getIsDraftMode());
			}
			if(admAppln.getIsDraftCancelled()!= null && admAppln.getIsDraftCancelled()){
				admissionStatusTO.setDraftCancelled(admAppln.getIsDraftCancelled());
			}
			
			if(admAppln.getIsDraftMode()!= null && admAppln.getIsDraftCancelled()!= null)
			{
				
				
				if(admAppln.getIsDraftMode() && !admAppln.getIsDraftCancelled()){
					admissionStatusTO.setAdmStatus("Please complete your application  ");
				}else if(admAppln.getIsDraftMode() && admAppln.getIsDraftCancelled()){
					admissionStatusTO.setAdmStatus("Application Incomplete and Removed ");
				}else if(!admAppln.getIsDraftMode() && admAppln.getIsDraftCancelled()){
					admissionStatusTO.setAdmStatus("Application Incomplete and Removed ");
				}
				
				
				
				//this is original code
				else if(!admAppln.getIsDraftMode() && !admAppln.getIsDraftCancelled())
				{
					
					if(admAppln.getIsSelected()!=null && admAppln.getPersonalData()!=null  
					&& admAppln.getPersonalData().getId()!=0 && admAppln.getIsCancelled()!=null){
						
							if(admAppln.getApplnNo()>0){
								admissionStatusTO.setApplicationNo(admAppln.getApplnNo());
								//raghu
								objForm.setApplicationNo(admAppln.getApplnNo()+"");
							}
							
							if(admAppln.getPersonalData().getDateOfBirth()!=null){
								admissionStatusTO.setDateOfBirth(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()));
							admissionStatusTO.setPersonalDataId(admAppln.getPersonalData().getId());
							boolean isFinalMeritApproved = false;
							if(admAppln.getIsFinalMeritApproved()!=null){
								isFinalMeritApproved = admAppln.getIsFinalMeritApproved();
							}
							admissionStatusTO.setIsSelected(convertBoolValueIsSelected(admAppln.getIsSelected(),isFinalMeritApproved));				
					}
							
					if(admAppln.getPersonalData()!=null && admAppln.getPersonalData().getEmail()!=null){
						admissionStatusTO.setEmail(admAppln.getPersonalData().getEmail());
					}	
					
					admissionStatusTO.setCancelled(admAppln.getIsCancelled());
					if(admAppln.getAppliedYear()!=null && admAppln.getAppliedYear()!=0){
						admissionStatusTO.setAppliedYear(admAppln.getAppliedYear());
						//raghu
						objForm.setAppliedYear(admAppln.getAppliedYear());
						objForm.setAcademicYear(admAppln.getAppliedYear()+"");
					}
					
					if(admAppln.getCourseBySelectedCourseId() != null){
						admissionStatusTO.setCourseId(admAppln.getCourseBySelectedCourseId().getId());
						admissionStatusTO.setCourseName(admAppln.getCourseBySelectedCourseId().getName());
					}
					
					
					
					
					if(admAppln.getIsChallanVerified()!=null){
					admissionStatusTO.setChallenVerified(admAppln.getIsChallanVerified());
					}else{
						admissionStatusTO.setChallenVerified(false);
					}
					
					
					admissionStatusTO.setAdmStatus(admAppln.getAdmStatus());
					
						
						
						
				}
			}
				
			
			}
		}
		return admissionStatusTO;
}

	/**
	 * @param value
	 * @param isFinalMeritApproved
	 * @return
	 * @throws Exception
	 */
	private static String convertBoolValueIsSelected(Boolean value, boolean isFinalMeritApproved) throws Exception{
		if (value.booleanValue() && isFinalMeritApproved) {
			return CMSConstants.SELECTED_FOR_ADMISSION;
		} 
		else{
			return CMSConstants.NOT_SELECTED_FOR_ADMISSION;			
		}
	}
	/**
	 * @param onlineApplication
	 * @throws Exception
	 */
	private void send_sms(StudentOnlineApplication onlineApplication) throws Exception{
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String templateName="Unique Id Creation Sms";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		 	if(list != null && !list.isEmpty()) {
		 		String desc = list.get(0).getTemplateDescription();
		 		if(onlineApplication.getUniqueId()!=null && !onlineApplication.getUniqueId().isEmpty()){
		 			desc = desc.replace("[UNIQUE_ID]", onlineApplication.getUniqueId());
		 		}
		 		if(StringUtils.isNumeric(onlineApplication.getMobileNo()) && (onlineApplication.getMobileNo().length()==10 && desc.length()<=160) && desc!=null && !desc.isEmpty()){
		 			MobileMessaging mob=new MobileMessaging();
					mob.setDestinationNumber("91"+onlineApplication.getMobileNo());
					mob.setMessageBody(desc);
					mob.setMessagePriority(3);
					mob.setSenderName(senderName);
					mob.setSenderNumber(senderNumber);
					mob.setMessageEnqueueDate(new Date());
					mob.setIsMessageSent(false);
					PropertyUtil.getInstance().save(mob);
		 		}
		 	}
	}

	/**
	 * @param onlineApplication
	 * @throws Exception
	 */
	private void send_email(StudentOnlineApplication onlineApplication) throws Exception{
		String templateName="Unique Id Creation Mail";
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate>  list= temphandle.getDuplicateCheckList(templateName);
		 	if(list != null && !list.isEmpty()) {
		 		String desc = list.get(0).getTemplateDescription();
		 		if(onlineApplication.getUniqueId()!=null && !onlineApplication.getUniqueId().isEmpty()){
		 			desc = desc.replace("[UNIQUE_ID]", onlineApplication.getUniqueId());
		 		}
		 		if(onlineApplication.getEmailId()!=null && !onlineApplication.getEmailId().isEmpty()){
		 			desc = desc.replace("[EMAIL]", onlineApplication.getEmailId());
		 		}
		 		if(onlineApplication.getDateOfBirth()!=null ){
		 			desc = desc.replace("[DOB]", CommonUtil.formatDates(onlineApplication.getDateOfBirth()));
		 		}
		 		if(onlineApplication.getEmailId()!=null && !onlineApplication.getEmailId().isEmpty()){
		 			String fromName=KPPropertiesConfiguration.ADMIN_FROM_MAIL_NAME;
		 			String fromAddress=KPPropertiesConfiguration.USER_NAME;
		 			String subjectLine = "Application Unique Id";
		 			String toAddress = onlineApplication.getEmailId();
		 			String msg = desc;
		 			CommonUtil.sendMail(fromName, fromAddress, toAddress, subjectLine, msg);
		 		}
		 	}
	}

	/**
	 * @param objForm
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	public boolean  registerApplicant(UniqueIdRegistrationForm objForm,
			ActionErrors errors) throws Exception {
		
		boolean isRegisted = false;
		String isCommunityQuota=null;
		String academicYear = txnImpl.getAdmissionAcademicYear();
		objForm.setYear(academicYear);
		objForm.setAcademicYear(academicYear);
		//			check duplicate registration .
		StudentOnlineApplication onlineApplication = null;
		 onlineApplication = txnImpl.getLoginOnlineApplicationDetails(objForm,"register");
		 
		 	
		if(onlineApplication == null){
			
			String uniq = PasswordGenerator.getPasswordNum();
			String uniqalpha = PasswordGenerator.getPasswordAlpha();
			String challanNo="";
			if(objForm.getProgramTypeId()!=null && objForm.getProgramTypeId().equalsIgnoreCase("1")){
				challanNo=CMSConstants.COLLEGE_CODE+uniq+uniqalpha;
			}else{
				challanNo=CMSConstants.COLLEGE_CODE+uniqalpha+uniq;
			}
			boolean generate=true;
			boolean duplicate=false;
			//duplicate challan check
			while(generate){
			
				duplicate=txnImpl.checkDuplicateNumber(Integer.parseInt(academicYear), challanNo);
			  if (duplicate){
				  continue ;
				  }else{
					  generate=false;
					  break;
				  }
			}
			
			objForm.setChallanRefNo(challanNo);
			if(CMSConstants.INDIAN_RESIDENT_ID.contains(objForm.getResidentCategoryId())){
				objForm.setMobileCode("91");
			}
			if (objForm.getIsCommuQuota()=="yes" || objForm.getIsCommuQuota().equalsIgnoreCase("yes")) {
				isCommunityQuota="1";
			}
			else {
				isCommunityQuota="0";
			}
			boolean managmtQuota=false;
			if (objForm.getMngQuota()=="1" || objForm.getMngQuota().equals("1")) {
				managmtQuota=true;
			}
			onlineApplication = new StudentOnlineApplication();
			isRegisted = generateUniqueIDForApplicant(onlineApplication,objForm.getYear(),
													  objForm.getMobileNo().trim(),	objForm.getMobileCode().trim(),
													  objForm.getGender(),objForm.getEmailId().trim(),
													  objForm.getRegisterDateOfBirth(),objForm.getUserId(),"OnlineMode",
													  objForm.getApplicantName(),objForm.getProgramTypeId(),
													  objForm.getResidentCategoryId(),objForm.getSubReligionId(),
													  objForm.getChallanRefNo(), objForm.isAided(), managmtQuota,isCommunityQuota);
		}
		
		if(isRegisted){
			objForm.setOnlineApplicationUniqueId(onlineApplication.getId());
			objForm.setDob(CommonUtil.formatDates(onlineApplication.getDateOfBirth()));
			objForm.setApplicantMobileNo(onlineApplication.getMobileNo());
			objForm.setApplicantMobileCode(onlineApplication.getMobileCode());
			objForm.setGender(onlineApplication.getGender());
			objForm.setApplicantEmailId(onlineApplication.getEmailId());
			objForm.setUniqueId(onlineApplication.getUniqueId());
			objForm.setApplicantName(onlineApplication.getName());
			objForm.setProgramTypeId(onlineApplication.getProgramTypeId());
			objForm.setResidentCategoryId(onlineApplication.getCategoryId());
			objForm.setSubReligionId(onlineApplication.getSubReligionId());
			objForm.setAcademicYear(onlineApplication.getYear()+"");
			objForm.setChallanRefNo(onlineApplication.getChallanNumber());
			objForm.setIsCommuQuota(onlineApplication.getIsCommuQuota());
		}
		
		return isRegisted;
		
		}

	/**
	 * @param onlineApplication
	 * @param year
	 * @param trim
	 * @param trim2
	 * @param registerDateOfBirth
	 * @param userId
	 * @param mode 
	 * @return
	 * @throws Exception 
	 */
	public synchronized boolean generateUniqueIDForApplicant(StudentOnlineApplication onlineApplication, String admissionAcademicYear,
			String mobileNo, String mobileCode, String gender,String emailId, String registerDateOfBirth, String userId, 
			String mode,String name, String programTypeId, String categoryId, String subReligionId,String challanNo, boolean isAided, boolean mngQuota,String isCommunityQuota) throws Exception {
		
		boolean isRegisted = false;
		//StudentUniqueIdGenerator uniqueIdGenerator = txnImpl.getUniqueIdGenerator(admissionAcademicYear);
		onlineApplication.setMobileNo(mobileNo);
		onlineApplication.setMobileCode(mobileCode);
		onlineApplication.setGender(gender);
		onlineApplication.setEmailId(emailId);
		if(registerDateOfBirth!=null && !registerDateOfBirth.isEmpty()){
			onlineApplication.setDateOfBirth(CommonUtil.ConvertStringToDate(registerDateOfBirth));
		}
		onlineApplication.setYear(Integer.parseInt(admissionAcademicYear));
		onlineApplication.setCreatedBy(userId);
		onlineApplication.setCreatedDate(new Date());
		onlineApplication.setModifiedBy(userId);
		onlineApplication.setLastModifiedDate(new Date());
		onlineApplication.setIsActive(true);
		onlineApplication.setCategoryId(categoryId);
		onlineApplication.setProgramTypeId(programTypeId);
		onlineApplication.setName(name);
		onlineApplication.setSubReligionId(subReligionId);
		onlineApplication.setChallanNumber(challanNo);
		onlineApplication.setIsAided(isAided);
		onlineApplication.setMngQuota(mngQuota);
		onlineApplication.setIsCommuQuota(isCommunityQuota);
		String collegeCode = CMSConstants.COLLEGE_CODE;
		
		String day[] = registerDateOfBirth.split("/");
		String dayNumber = day[0];
		
		String uniqueId=""; 
		boolean generate=true;
		boolean duplicate=false;
		//duplicate challan check
		while(generate){
			//random generation of 4 nos 
			String uniq = PasswordGenerator.getPassword().substring(0,6);
			uniqueId = collegeCode+dayNumber+uniq;
			
			duplicate=txnImpl.checkDuplicateUniqueId(Integer.parseInt(admissionAcademicYear), uniqueId);
		  if (duplicate){
			  continue ;
			  }else{
				  generate=false;
				  break;
			  }
		}
		
		onlineApplication.setUniqueId(uniqueId);
			
			
			
		isRegisted = txnImpl.registerOnlineApplication(onlineApplication);
		if(isRegisted && !mode.equalsIgnoreCase("forOMRUpload")){ // for OMR Upload send mail is not required.
		//send email & sms to the registered applicant.
		sendMail_SMS(onlineApplication);
		}
	
		return isRegisted;
	}

	/**
	 * @param onlineApplication
	 */
	private void sendMail_SMS(StudentOnlineApplication onlineApplication) throws Exception{
		String sendMail = KPPropertiesConfiguration.INSERT_MAIL;
		/*String sendSms= KPPropertiesConfiguration.INSERT_SMS;
		
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
			send_sms_new(onlineApplication);
		}*/
		
		if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
			send_email_new(onlineApplication);
		}
		
	}

	/**
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public boolean IsExistedMail(UniqueIdRegistrationForm objForm) throws Exception{
		boolean isMailExisting = txnImpl.IsExistedMail(objForm);
		if(isMailExisting) {
			send_email_new_duplicateMailOrMobileFound(objForm);
			//send_sms_new_duplicateMailOrMobileFound(objForm);
		}
		return isMailExisting;
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	
	public boolean getForgotUniqueId(UniqueIdRegistrationForm objForm) throws Exception {
		String academicYear = txnImpl.getAdmissionAcademicYear();
		objForm.setYear(academicYear);
		StudentOnlineApplication onlineApplication= txnImpl.getLoginOnlineApplicationDetails(objForm,"forgotUniqueId");
		boolean isAvailable = false;
		if(onlineApplication!=null){
			isAvailable = true;
//			send email & sms to the registered applicant.
			sendMail_SMS(onlineApplication);
			objForm.setUniqueId(onlineApplication.getUniqueId());
		}

		return isAvailable;
	}
	
	
	
	/**
	 * @param onlineApplication
	 * @throws Exception
	 */
	private void send_email_new(StudentOnlineApplication onlineApplication) throws Exception{
		 	
		 	boolean sent=false;
			Properties prop = new Properties();
			String toAddress="";
			if(onlineApplication.getEmailId()!=null && !onlineApplication.getEmailId().isEmpty()){
				toAddress = toAddress +onlineApplication.getEmailId();
	 		}
			String collegeName = CMSConstants.COLLEGE_NAME;
			String subject="Password for Online Application from "+collegeName;
			String msg= "Dear "+onlineApplication.getName()+",<br/>"+" Your userid is "+CommonUtil.formatDates(onlineApplication.getDateOfBirth())+
			" and Your password is "+onlineApplication.getUniqueId()+
			"<br/>"+" Keep safe for future reference.";
			
		 	
			final String adminmail=CMSConstants.MAIL_USERID;
			final String password = CMSConstants.MAIL_PASSWORD;
			final String port = CMSConstants.MAIL_PORT;
			final String host = CMSConstants.MAIL_HOST;
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.socketFactory.port", port);
			props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", port);
	 
			Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
	 
			MailTO mailto=new MailTO();
			mailto.setFromName(CMSConstants.COLLEGE_NAME);
			mailto.setFromAddress(adminmail);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
				
			try
			{
				 MimeMessage message1 = new MimeMessage(session);
					
					// Set from & to addresses
					InternetAddress from = new InternetAddress(adminmail,collegeName + " Online Admission");
					
					InternetAddress toAssociate = new InternetAddress(toAddress);
					message1.setSubject(subject);
					message1.setFrom(from);
					message1.addRecipient(Message.RecipientType.TO, toAssociate);
				    MimeBodyPart mailBody = new MimeBodyPart();
				    mailBody.setText(msg, "US-ASCII", "html");
				    MimeMultipart mimeMultipart = new MimeMultipart();
				    
				    mimeMultipart.addBodyPart(mailBody);
				    message1.setContent(mimeMultipart);
				
				    Properties config = new Properties() {
						{
							put("mail.smtps.auth", "true");
							put("mail.smtp.host", host);
							put("mail.smtp.port", port);
							put("mail.smtp.starttls.enable", "true");
							put("mail.transport.protocol", "smtps");
						}
					}; 
					
					
					
				Session carrierSession = Session.getInstance(config, new Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(adminmail,password);
					}
				});
				
				
				
				Transport transport = carrierSession.getTransport("smtps");
				transport.connect(host,adminmail,password);
				transport.sendMessage(message1,message1.getRecipients(Message.RecipientType.TO));  //set
		        transport.close();
	 
				System.out.println("==========Done========");
			}
			
			catch (Exception e) {
				System.out.println(e.getMessage());
				
			}
			
			
			//uses JMS 
			sent=CommonUtil.sendMail(mailto);
			
		 	
		 	
		 	
	}
	
	
	
	
	/**
	 * @param onlineApplication
	 * @throws Exception
	 */
	private void send_sms_new(StudentOnlineApplication onlineApplication) throws Exception{
		
		
		
		Properties prop = new Properties();
		try {
			InputStream in = UniqueIdRegistrationHandler.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			
		}
		
		String collegeName = CMSConstants.COLLEGE_NAME;
		
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		
		//String temp = "";
		StringBuffer temp = new StringBuffer();
		
		temp=temp.append("Dear " + onlineApplication.getName());
		temp=temp.append(" your user Id is " + CommonUtil.formatDates(onlineApplication.getDateOfBirth()));
		temp=temp.append(" and your password is " + onlineApplication.getUniqueId());
		temp=temp.append(" for " + (onlineApplication.getIsAided() ? "Aided" : "Unaided") + " course" + ".");
		temp=temp.append(" Keep safe for future reference.");
		temp=temp.append(" From ");
		temp=temp.append("BMCollege");
		String temp1= URLEncoder.encode(temp.toString(),"UTF-8");
		
		
//		temp=temp+URLEncoder.encode(onlineApplication.getName()+",","UTF-8");
//		
//		temp=temp+ URLEncoder.encode("Your userid is ","UTF-8");
//		temp=temp+ URLEncoder.encode(""+CommonUtil.formatDates(onlineApplication.getDateOfBirth())+"","UTF-8");
//		temp=temp+ URLEncoder.encode(" and Your password is "+onlineApplication.getUniqueId()+"","UTF-8");
//		temp=temp+ URLEncoder.encode(" Keep safe for future reference.","UTF-8");
//		temp=temp+URLEncoder.encode(" from "+collegeName,"UTF-8");
		
		
		PasswordMobileMessaging mob=new PasswordMobileMessaging();						
		mob.setDestinationNumber(onlineApplication.getMobileNo());
		mob.setMessagePriority(3);
		mob.setSenderName(senderName);
		mob.setSenderNumber(senderNumber);
		mob.setMessageEnqueueDate(new Date());
		mob.setIsMessageSent(false);									
		mob.setMessageBody(temp1);
		
		PropertyUtil.getInstance().save(mob);
		SMSUtil s=new SMSUtil();
		SMSUtils smsUtils=new SMSUtils();

		ConverationUtil converationUtil=new ConverationUtil();
		List<SMS_Message> listSms=converationUtil.convertBotoTOPassword(s.getListOfSMSPassword());
		List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
		s.updateSMSPassword(converationUtil.convertTotoBOPassword(mobList));

		
	}
	
	private void send_email_new_duplicateMailOrMobileFound(UniqueIdRegistrationForm objForm) throws Exception{
	 	
	 	boolean sent=false;
		Properties prop = new Properties();
		String toAddress="";
		if(objForm.getEmailId()!=null && !objForm.getEmailId().isEmpty()){
			toAddress = toAddress +objForm.getEmailId();
 		}
		String collegeName = CMSConstants.COLLEGE_NAME;
		String subject="Email Id already registered "+collegeName;
		String msg= "Your application is already registered. Contact the admission office for clarification.";
		
	 	
		final String adminmail=CMSConstants.MAIL_USERID;
		final String password = CMSConstants.MAIL_PASSWORD;
		final String port = CMSConstants.MAIL_PORT;
		final String host = CMSConstants.MAIL_HOST;
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.socketFactory.port", port);
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", port);
 
		Session session = Session.getInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(adminmail,password);
				}
			});
 
		MailTO mailto=new MailTO();
		mailto.setFromName(CMSConstants.COLLEGE_NAME);
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
			
		try
		{
			 MimeMessage message1 = new MimeMessage(session);
				
				// Set from & to addresses
				InternetAddress from = new InternetAddress(adminmail,collegeName + " Online Admission");
				
				InternetAddress toAssociate = new InternetAddress(toAddress);
				message1.setSubject(subject);
				message1.setFrom(from);
				message1.addRecipient(Message.RecipientType.TO, toAssociate);
			    MimeBodyPart mailBody = new MimeBodyPart();
			    mailBody.setText(msg, "US-ASCII", "html");
			    MimeMultipart mimeMultipart = new MimeMultipart();
			    
			    mimeMultipart.addBodyPart(mailBody);
			    message1.setContent(mimeMultipart);
			
			    Properties config = new Properties() {
					{
						put("mail.smtps.auth", "true");
						put("mail.smtp.host", host);
						put("mail.smtp.port", port);
						put("mail.smtp.starttls.enable", "true");
						put("mail.transport.protocol", "smtps");
					}
				}; 
				
				
				
			Session carrierSession = Session.getInstance(config, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(adminmail,password);
				}
			});
			
			
			
			Transport transport = carrierSession.getTransport("smtps");
			transport.connect(host,adminmail,password);
			transport.sendMessage(message1,message1.getRecipients(Message.RecipientType.TO));  //set
	        transport.close();
 
			System.out.println("==========Done========");
		}
		
		catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		
		
		//uses JMS 
		sent=CommonUtil.sendMail(mailto);
	}
	
	public boolean IsExistedMobileNumber(UniqueIdRegistrationForm objForm) throws Exception{
		boolean isExistedMobileNumber = txnImpl.IsExistedPhoneNumber(objForm);
		if(isExistedMobileNumber) {
			send_email_new_duplicateMailOrMobileFound(objForm);
			//send_sms_new_duplicateMailOrMobileFound(objForm);
		}
		return isExistedMobileNumber;
	}
	
	private void send_sms_new_duplicateMailOrMobileFound(UniqueIdRegistrationForm objForm) throws Exception{

		Properties prop = new Properties();
		try {
			InputStream in = UniqueIdRegistrationHandler.class.getClassLoader()
			.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
			prop.load(in);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			
		}
		
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		
		String temp = "";
		temp=temp+URLEncoder.encode("Your application is already registered. Contact the admission office for clarification.","UTF-8");		
		
		PasswordMobileMessaging mob=new PasswordMobileMessaging();						
		mob.setDestinationNumber(objForm.getMobileCode()+ objForm.getMobileNo());
		mob.setMessagePriority(3);
		mob.setSenderName(senderName);
		mob.setSenderNumber(senderNumber);
		mob.setMessageEnqueueDate(new Date());
		mob.setIsMessageSent(false);									
		mob.setMessageBody(temp);
		
		PropertyUtil.getInstance().save(mob);
		SMSUtil s=new SMSUtil();
		SMSUtils smsUtils=new SMSUtils();

		ConverationUtil converationUtil=new ConverationUtil();
		List<SMS_Message> listSms=converationUtil.convertBotoTOPassword(s.getListOfSMSPassword());
		List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
		s.updateSMSPassword(converationUtil.convertTotoBOPassword(mobList));
	}
}
