	
package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;


import com.kp.cms.bo.admin.Address;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ApplnDocDetails;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PasswordMobileMessaging;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentExtracurricular;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.DetailedSubjectsHandler;
import com.kp.cms.handlers.admin.OccupationTransactionHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.StateHandler;
import com.kp.cms.handlers.admin.SubReligionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.usermanagement.LoginHandler;
import com.kp.cms.helpers.admission.OnlineApplicationHelper;
import com.kp.cms.to.admin.ApplnDocDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePGIDetailsTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DistrictTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StateTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.coursesToPrint;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IOnlineApplicationTxn;
import com.kp.cms.transactions.exam.IConsolidatedSubjectStreamTransaction;
import com.kp.cms.transactions.usermanagement.ILoginTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.OnlineApplicationImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.exam.ConsolidatedSubjectStreamTransactionImpl;
import com.kp.cms.transactionsimpl.usermanagement.LoginTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.jms.SMS_Message;

	@SuppressWarnings({"deprecation","resource","rawtypes"})
	public class OnlineApplicationHandler {
	private static final Log log = LogFactory.getLog(OnlineApplicationHandler.class);
	
	private static final String OTHER="Other";
	private static final String PHOTO="Photo";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public static volatile OnlineApplicationHandler self=null;
	public static OnlineApplicationHandler getInstance(){
		if(self==null){
			self= new OnlineApplicationHandler();
		}
		return self;
	}
	private OnlineApplicationHandler(){
		
	}
	IOnlineApplicationTxn txn= new OnlineApplicationImpl();
	OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
	
	
		public boolean saveBasicPage(OnlineApplicationForm admForm) throws Exception {
			log.info("enter createApplicant");
			
			PersonalData persData=new PersonalData();
			ResidentCategory rs=new ResidentCategory();
			rs.setId(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()));
			persData.setResidentCategory(rs);
			persData.setFirstName(admForm.getApplicantName());
			persData.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob()));
			persData.setEmail(admForm.getEmail());
			if(admForm.getMobileNo1()!=null && !admForm.getMobileNo1().isEmpty())
			persData.setMobileNo1(admForm.getMobileNo1().trim());
			if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty())
			persData.setMobileNo2(admForm.getMobileNo2().trim());
			persData.setGender(admForm.getGender());
			persData.setCreatedBy(admForm.getUserId());
			persData.setCreatedDate(new Date());
			persData.setModifiedBy(admForm.getUserId());
			persData.setLastModifiedDate(new Date());
			//persData.setIsmgquota(admForm.getMgquota());
			//ReligionSection religionSection=new ReligionSection();
			//religionSection.setId(Integer.parseInt(admForm.getSubReligion()));
			//persData.setReligionSection(religionSection);
			
			AdmAppln appBO=helper.getBasicPageApplicantBO(admForm);
			Student std= new Student();
			std.setIsAdmitted(false);
			std.setCreatedBy(appBO.getCreatedBy());
			std.setCreatedDate(appBO.getCreatedDate());
			std.setAdmAppln(appBO);
			std.setIsActive(true);
			if(appBO!=null){
				
				appBO.setPersonalData(persData);
			}	
				
			boolean updated=txn.submitBasicPage(std,admForm);
			
			
			log.info("exit createApplicant");
			admForm.setDisplayPage("guidelines");
			return updated;
		}
	
		
		public List<AdmAppln> getSavedApplicantDetails(OnlineApplicationForm admForm) throws Exception {
			List<AdmAppln> admApp=new ArrayList<AdmAppln>();
			admApp=txn.getApplicantSavedDetails(admForm);
			return admApp;
		}
		
		public AdmAppln getAppliedApplicationDetails(OnlineApplicationForm admForm) throws Exception {
			AdmAppln admApp=new AdmAppln();
			admApp=txn.getAppliedApplnDetails(admForm);
			return admApp;
		}
		
		public List<CourseTO> getCourse(int id) throws Exception {
			ICourseTransaction iCourseTransaction = CourseTransactionImpl.getInstance();
			List<Course> courseList = iCourseTransaction.getCourse(id);
			List<CourseTO> courseToList = helper.copyCourseBosToTos(courseList, id);
			return courseToList;
		}
		
		public boolean createApplicant(AdmApplnTO applicantDetail,
				OnlineApplicationForm admForm,boolean isPresidance,String saveMode) throws Exception {
			log.info("enter createApplicant");
			AdmAppln appBO=null;
			Student admBO= null;
			appBO=txn.getAppliedApplnDetails(admForm);
			admBO=txn.getStudentDetailsFromAdmAppln(appBO.getId());
			if(admBO==null){
				admBO=new Student();
			}
			if(appBO!=null){
		 	appBO=helper.getApplicantBO(appBO,applicantDetail,admForm,saveMode);
		 	
			admBO.setIsAdmitted(false);
			if(admForm.getRecommedationApplicationNo() != null && !admForm.getRecommedationApplicationNo().isEmpty()){
			appBO.setRecommedationApplicationNo(admForm.getRecommedationApplicationNo());
			}
			if(appBO!=null){
				
				appBO.setId(Integer.parseInt(admForm.getAdmApplnId()));
				
				/*
				//raghu put comment
				//appBO.setIsCancelled(false);
				//appBO.setIsSelected(false);
				//appBO.setIsApproved(false);
				//appBO.setIsFinalMeritApproved(false);
				//appBO.setIsLig(false);
				//appBO.setIsBypassed(false);
				//appBO.setIsChallanVerified(false);
				
				
				admBO.setCreatedBy(appBO.getCreatedBy());
				admBO.setCreatedDate(appBO.getCreatedDate());
				// setting the candidate Pre requisite details from detailApplicantCreate jsp.
				if(appBO.getCandidatePrerequisiteMarks()!=null && !appBO.getCandidatePrerequisiteMarks().isEmpty()){
					Iterator<CandidatePrerequisiteMarks> candidatePreRequisiteMarksItr=appBO.getCandidatePrerequisiteMarks().iterator();
					while (candidatePreRequisiteMarksItr.hasNext()) {
						CandidatePrerequisiteMarks preRequisiteMarks = (CandidatePrerequisiteMarks) candidatePreRequisiteMarksItr.next();
						preRequisiteMarks.setPrerequisiteMarksObtained(new BigDecimal(applicantDetail.getPreRequisiteObtMarks()));
						preRequisiteMarks.setRollNo(applicantDetail.getPreRequisiteRollNo());
						preRequisiteMarks.setExamMonth(Integer.parseInt(applicantDetail.getPreRequisiteExamMonth()));
						preRequisiteMarks.setExamYear(Integer.parseInt(applicantDetail.getPreRequisiteExamYear()));
					}
				}
				Set<AdmapplnAdditionalInfo> admAddnSet=new HashSet<AdmapplnAdditionalInfo>();
				AdmapplnAdditionalInfo additionalInfo=new AdmapplnAdditionalInfo();
				if(applicantDetail.getTitleOfFather()!=null && !applicantDetail.getTitleOfFather().isEmpty())
					additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
				if(applicantDetail.getTitleOfMother()!=null && !applicantDetail.getTitleOfMother().isEmpty())
					additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
				if(applicantDetail.getApplicantFeedbackId()!=null && !applicantDetail.getApplicantFeedbackId().isEmpty()){
					ApplicantFeedback feedback=new ApplicantFeedback();
					feedback.setId(Integer.parseInt(applicantDetail.getApplicantFeedbackId()));
					additionalInfo.setApplicantFeedback(feedback);
				}
				if(applicantDetail.getInternationalCurrencyId()!=null && !applicantDetail.getInternationalCurrencyId().isEmpty()){
					Currency curr=new Currency();
					curr.setId(Integer.parseInt(applicantDetail.getInternationalCurrencyId()));
					additionalInfo.setInternationalApplnFeeCurrency(curr);
				}
				if(applicantDetail.getIsComeDk()!=null ){
					if(applicantDetail.getIsComeDk())
						additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
					else
						additionalInfo.setIsComedk(false);
				}else{
					additionalInfo.setIsComedk(false);
				}
				
					
				//raghu
				additionalInfo.setIsSpotAdmission(false);
				
				additionalInfo.setCreatedBy(appBO.getCreatedBy());
				additionalInfo.setCreatedDate(new Date());
				additionalInfo.setModifiedBy(appBO.getCreatedBy());
				additionalInfo.setLastModifiedDate(new Date());
				additionalInfo.setBackLogs(admForm.isBackLogs());
				//raghu
				additionalInfo.setIsSaypass(admForm.getIsSaypass());
				
				//raghu
				if(applicantDetail.getAdmapplnAdditionalInfos()!=null && applicantDetail.getAdmapplnAdditionalInfos().size()!=0){
					List<AdmapplnAdditionalInfo> addInfo=new ArrayList<AdmapplnAdditionalInfo>(applicantDetail.getAdmapplnAdditionalInfos());
					additionalInfo.setId(addInfo.get(0).getId());
				}
				
				admAddnSet.add(additionalInfo);
				appBO.setAdmapplnAdditionalInfo(admAddnSet);
				*/}
			}
			admBO.setAdmAppln(appBO);
			boolean updated=txn.createNewApplicant(admBO, admForm,saveMode);
			
			
			log.info("exit createApplicant");
			
			return updated;
		}
		/**
		 * add ApplnDoc BOs to session
		 * @param admForm
		 * @param session
		 * @throws Exception
		 */
		public void persistAdmissionFormAttachments(OnlineApplicationForm admForm,HttpSession session) throws Exception {
			log.info("Enter persistAdmissionFormAttachments ...");
			
			
			List<ApplnDoc> uploadList=helper.getDocUploadBO(admForm.getUploadDocs(),admForm.getUserId());
			session.setAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS, uploadList);
			log.info("Exit persistAdmissionFormAttachments ...");
		}
		
		/**
		 * get resident category list
		 * @return
		 */
		public List<ResidentCategoryTO> getResidentTypes()throws Exception{
			log.info("Enter getResidentTypes ...");
			
			List<ResidentCategory> residentbos=txn.getResidentTypes();
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			List<ResidentCategoryTO> residents=helper.convertResidentBOToTO(residentbos);
			log.info("Exit getResidentTypes ...");
			return residents;
		}
		/**
		 * save student data tom session
		 * @param studentpersonaldata
		 * @param admForm
		 * @return
		 */
		public boolean saveStudentPersonaldataToSession(PersonalDataTO studentpersonaldata, OnlineApplicationForm admForm,HttpSession session) throws Exception {
			log.info("Enter saveStudentPersonaldataToSession ...");
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			PersonalData studentdata=helper.convertPersonalDataTOtoBO(studentpersonaldata);
			studentdata.setCreatedBy(admForm.getUserId());
			studentdata.setCreatedDate(new Date());
			// changed to current address mandatory.
			AddressTO permAddTo=admForm.getTempAddr();
			Address permAddBo=helper.convertPermanentAddressTOToBO(permAddTo);
			Address temAddrBO=null;
			if(admForm.isSameTempAddr()){
				temAddrBO=permAddBo;
			}else
			{
				temAddrBO=helper.convertPermanentAddressTOToBO(admForm.getPermAddr());
			}
			


			/*PreferenceTO firstpref= admForm.getFirstPref();
			PreferenceTO secpref= admForm.getSecondPref();
			PreferenceTO thirdpref= admForm.getThirdPref();*/
			
			//List<CandidatePreference> preferenceBos= helper.convertPreferenceTOToBO(firstpref,secpref,thirdpref,session);
			//work experience set
			/*Set<ApplicantWorkExperience> workExperiences = new HashSet<ApplicantWorkExperience>();
			if(!CMSConstants.LINK_FOR_CJC)
				workExperiences=helper.convertExperienceTostoBOs(admForm);*/
			// maintain session data
			if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
			{
				log.info("application no set session application data...");
				AdmAppln applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
				//applicationdata.setApplicantWorkExperiences(workExperiences);
//				// assigned first preference course as selected course by default
//				if(firstpref!=null && firstpref.getCourseId()!=null && !StringUtils.isEmpty(firstpref.getCourseId()) && StringUtils.isNumeric(firstpref.getCourseId()) )
//				{
//					Course crs= new Course();
//					crs.setId(Integer.parseInt(firstpref.getCourseId()));
//					applicationdata.setCourseBySelectedCourseId(crs);
//				}
				
				if (!admForm.isOnlineApply()) {
					applicationdata.setApplnNo(Integer.parseInt(admForm
							.getApplicationNumber()));
					if (!checkApplicationNoUniqueForYear(applicationdata)) {
						return false;
					}
				}
				session.setAttribute(CMSConstants.APPLICATION_DATA, applicationdata);
			}
			
			session.setAttribute(CMSConstants.STUDENT_PERSONAL_DATA, studentdata);
			log.info("student data set to session ...");
			session.setAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS, temAddrBO);
			log.info("perm address set to session ...");
			session.setAttribute(CMSConstants.STUDENT_COMM_ADDRESS,permAddBo );
			log.info("comm addr set to session ...");
			/*if(preferenceBos!=null && !preferenceBos.isEmpty()){
			session.setAttribute(CMSConstants.STUDENT_PREFERENCES, preferenceBos);
			}*/
			log.info("preferences set to session ...");
			return true;
		}
		
		
		/**
		 * application details add to session
		 * @param admForm
		 * @param session
		 * @return
		 */
		public boolean saveApplicationDetailsToSession(OnlineApplicationForm admForm)throws Exception {
			log.info("Enter saveApplicationDetailsToSession ...");
			boolean result=false;
			AdmAppln appln=new AdmAppln();
			System.out.println("inside haandler saveApplicationDetailsToSession method -> before getting admAppln ");
			appln=txn.getAppliedApplnDetails(admForm);
			System.out.println("inside haandler saveApplicationDetailsToSession method -> after getting admAppln ");
			//save challan
			if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("SBI")){
				if(admForm.getApplicationAmount()!=null && !StringUtils.isEmpty(admForm.getApplicationAmount())&& CommonUtil.isValidDecimal(admForm.getApplicationAmount())){
					appln.setAmount(new BigDecimal(admForm.getApplicationAmount()));
				}else{
						appln.setAmount(new BigDecimal("0.0"));
				}
				appln.setChallanRefNo(admForm.getChallanNo());
				appln.setJournalNo(admForm.getJournalNo());
				appln.setBankBranch(admForm.getBankBranch());
				appln.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getApplicationDate()));
				//raghu
				appln.setMode("Challan");
				appln.setIsChallanRecieved(false);
				appln.setIsChallanVerified(false);
				admForm.setMode("Challan");
				
			} 
			//save neft detail
			else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("NEFT")){
					if(admForm.getApplicationAmount()!=null && !StringUtils.isEmpty(admForm.getApplicationAmount())&& CommonUtil.isValidDecimal(admForm.getApplicationAmount())){
						appln.setAmount(new BigDecimal(admForm.getApplicationAmount()));
					}else{
							appln.setAmount(new BigDecimal("0.0"));
					}
					appln.setChallanRefNo(admForm.getChallanNo());
					appln.setJournalNo(admForm.getJournalNo());
					appln.setBankBranch(admForm.getBankBranch());
					appln.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getApplicationDate()));
					//raghu
					appln.setMode("NEFT");
					appln.setIsChallanRecieved(false);
					appln.setIsChallanVerified(false);
					admForm.setMode("NEFT");
					
				}
				
			//online payment store
			else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() 
					&& admForm.getSelectedFeePayment().equalsIgnoreCase("OnlinePayment")){
				if(admForm.getTxnAmt()!=null && !StringUtils.isEmpty(admForm.getTxnAmt())&& CommonUtil.isValidDecimal(admForm.getTxnAmt())){
					appln.setAmount(new BigDecimal(Integer.parseInt(admForm.getTxnAmt())/100));
				}else{
						appln.setAmount(new BigDecimal("0.0"));
				}
				appln.setJournalNo(admForm.getTxnRefNo());
				appln.setDate(CommonUtil.ConvertStringToSQLDateValue(admForm.getTxnDate()));
				if(admForm.getApplicationNumber()!=null && !admForm.getApplicationNumber().isEmpty())
				appln.setApplnNo(Integer.parseInt(admForm.getApplicationNumber()));
				appln.setMode("Online");
				appln.setIsChallanRecieved(false);
				appln.setIsChallanVerified(false);
				admForm.setMode("Online");
			}
			System.out.println("inside haandler saveApplicationDetailsToSession method -> before saving admAppln ");
			result=txn.saveChallanDetail(appln, admForm);
			System.out.println("inside haandler saveApplicationDetailsToSession method -> after saving admAppln ");
			if(result){
				admForm.setCurrentPageNo("payment");
				admForm.setDisplayPage("paymentsuccess");
				admForm.setDataSaved(true);
				
			}else{
				admForm.setCurrentPageNo("basic");
				admForm.setDisplayPage("payment");
				
			}
			//result=true;
			log.info("application details set to session ...");
			return result;
		}
		
		
		
		/**
		 * application details add to session
		 * @param admForm
		 * @param session
		 * @return
		 */
		public boolean saveFeeSuccessPage(OnlineApplicationForm admForm,HttpSession session, ActionMessages err)throws Exception {
			log.info("Enter saveApplicationDetailsToSession ...");
			boolean result=false;
			AdmAppln appln=new AdmAppln();
			appln=txn.getAppliedApplnDetails(admForm);
			result=txn.savePaymentSuccessPage(appln, admForm);
			if(result){
				admForm.setCurrentPageNo("paymentsuccess");
				admForm.setDisplayPage("preferences");
				
				admForm.setDataSaved(true);
				
			}else{
				admForm.setCurrentPageNo("payment");
				admForm.setDisplayPage("paymentsuccess");
				
			}
			//result=true;
			log.info("application details set to session ...");
			return result;
		}
		
		
		//raghu
		
		/**
		 * application details add to session
		 * @param admForm
		 * @param session
		 * @return
		 */
		public boolean  saveCompleteApplicationGenerateNo(OnlineApplicationForm admForm,ActionMessages errors)throws Exception {
			log.info("Enter saveApplicationDetailsToSession ...");
			String appno="";
			boolean result=false;
			AdmAppln appln=new AdmAppln();
			appln=txn.getAppliedApplnDetails(admForm);
			//if new app create appno
			if(appln.getApplnNo()==0){
				appno=txn.saveCompleteApplicationGenerateNo(Integer.parseInt(admForm.getCourseId()), Integer.parseInt(admForm.getApplicationYear()), true, appln, admForm, errors);
				if(appno!=null){
					result=true;
				}
			}
			//if already app no is there no need create
			else{
				result=true;
				//close session
				HibernateUtil.closeSession();
			}
			
			log.info("application details set to session ...");
			return result;
		}
		
		
		
		/**
		 * application details add to session
		 * @param admForm
		 * @param session
		 * @return
		 */
		public boolean  saveCompleteApplicationGenerateNoWithNoMoreEdit(AdmApplnTO applicantDetail,OnlineApplicationForm admForm,ActionMessages errors,String saveMode)throws Exception {
			log.info("Enter saveApplicationDetailsToSession ...");
			String appno="";
			boolean result=false;
			AdmAppln appBO=null;
			Student admBO= null;
			appBO=txn.getAppliedApplnDetails(admForm);
			
			//this one newly we have to generate app no so we have to merge 
			if(appBO.getApplnNo()==0){
				
				appno=txn.saveCompleteApplicationGenerateNoWithNoMoreEdit(Integer.parseInt(admForm.getCourseId()), Integer.parseInt(admForm.getApplicationYear()), true, appBO, admForm, errors);
				if(appno!=null){
					result=true;
				}
				
			}
			
			//this one already generated appno so we have to update
			else {
			
			admBO=txn.getStudentDetailsFromAdmAppln(appBO.getId());
				
			if(admBO==null){
				admBO=new Student();
			}
			if(appBO!=null){
		 	appBO=helper.getApplicantBO(appBO,applicantDetail,admForm,saveMode);
		 	
			admBO.setIsAdmitted(false);
			if(appBO!=null){
				
				appBO.setId(Integer.parseInt(admForm.getAdmApplnId()));
				
				/*
				appBO.setId(Integer.parseInt(admForm.getAdmApplnId()));
				//raghu put comment
				//appBO.setIsCancelled(false);
				//appBO.setIsSelected(false);
				//appBO.setIsApproved(false);
				//appBO.setIsFinalMeritApproved(false);
				//appBO.setIsLig(false);
				//appBO.setIsBypassed(false);
				//appBO.setIsChallanVerified(false);
				
				
				admBO.setCreatedBy(appBO.getCreatedBy());
				admBO.setCreatedDate(appBO.getCreatedDate());
				// setting the candidate Pre requisite details from detailApplicantCreate jsp.
				if(appBO.getCandidatePrerequisiteMarks()!=null && !appBO.getCandidatePrerequisiteMarks().isEmpty()){
					Iterator<CandidatePrerequisiteMarks> candidatePreRequisiteMarksItr=appBO.getCandidatePrerequisiteMarks().iterator();
					while (candidatePreRequisiteMarksItr.hasNext()) {
						CandidatePrerequisiteMarks preRequisiteMarks = (CandidatePrerequisiteMarks) candidatePreRequisiteMarksItr.next();
						preRequisiteMarks.setPrerequisiteMarksObtained(new BigDecimal(applicantDetail.getPreRequisiteObtMarks()));
						preRequisiteMarks.setRollNo(applicantDetail.getPreRequisiteRollNo());
						preRequisiteMarks.setExamMonth(Integer.parseInt(applicantDetail.getPreRequisiteExamMonth()));
						preRequisiteMarks.setExamYear(Integer.parseInt(applicantDetail.getPreRequisiteExamYear()));
					}
				}
				Set<AdmapplnAdditionalInfo> admAddnSet=new HashSet<AdmapplnAdditionalInfo>();
				AdmapplnAdditionalInfo additionalInfo=new AdmapplnAdditionalInfo();
				if(applicantDetail.getTitleOfFather()!=null && !applicantDetail.getTitleOfFather().isEmpty())
					additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
				if(applicantDetail.getTitleOfMother()!=null && !applicantDetail.getTitleOfMother().isEmpty())
					additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
				if(applicantDetail.getApplicantFeedbackId()!=null && !applicantDetail.getApplicantFeedbackId().isEmpty()){
					ApplicantFeedback feedback=new ApplicantFeedback();
					feedback.setId(Integer.parseInt(applicantDetail.getApplicantFeedbackId()));
					additionalInfo.setApplicantFeedback(feedback);
				}
				if(applicantDetail.getInternationalCurrencyId()!=null && !applicantDetail.getInternationalCurrencyId().isEmpty()){
					Currency curr=new Currency();
					curr.setId(Integer.parseInt(applicantDetail.getInternationalCurrencyId()));
					additionalInfo.setInternationalApplnFeeCurrency(curr);
				}
				if(applicantDetail.getIsComeDk()!=null ){
					if(applicantDetail.getIsComeDk())
						additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
					else
						additionalInfo.setIsComedk(false);
				}else{
					additionalInfo.setIsComedk(false);
				}
				
					
				//raghu
				additionalInfo.setIsSpotAdmission(false);
				
				additionalInfo.setCreatedBy(appBO.getCreatedBy());
				additionalInfo.setCreatedDate(new Date());
				additionalInfo.setModifiedBy(appBO.getCreatedBy());
				additionalInfo.setLastModifiedDate(new Date());
				additionalInfo.setBackLogs(admForm.isBackLogs());
				//raghu
				additionalInfo.setIsSaypass(admForm.getIsSaypass());
				
				//raghu
				if(applicantDetail.getAdmapplnAdditionalInfos()!=null && applicantDetail.getAdmapplnAdditionalInfos().size()!=0){
					List<AdmapplnAdditionalInfo> addInfo=new ArrayList<AdmapplnAdditionalInfo>(applicantDetail.getAdmapplnAdditionalInfos());
					additionalInfo.setId(addInfo.get(0).getId());
				}
				
				admAddnSet.add(additionalInfo);
				appBO.setAdmapplnAdditionalInfo(admAddnSet);
				*/}
			}
			
			admBO.setAdmAppln(appBO);
		
		
			result=txn.createNewApplicant(admBO, admForm,saveMode);
			
			}
			if(result) {
				send_email_new(admForm);
				//send_sms_new(admForm);
			}
			log.info("application details set to session ...");
			return result;
		}
		
	
		/**
		 * prepare EdnQualificationTos for application form
		 * @param admForm
		 * @return
		 * @throws Exception
		 */
		public List<EdnQualificationTO> getEdnQualifications(OnlineApplicationForm admForm) throws Exception {
			log.info("Enter getEdnQualifications ...");
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<DocChecklist> exambos= txn.getExamtypes(Integer.parseInt(admForm.getCourseId()),Integer.parseInt(admForm.getApplicationYear()));
			
			
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			log.info("Exit getEdnQualifications ...");
			return helper.prepareQualificationsFromExamBos(exambos);
		}
		

		/**
		 * @param admForm
		 * @param session
		 * @throws Exception 
		 */
		@SuppressWarnings("unchecked")
		public void saveEducationDetailsToSession(OnlineApplicationForm admForm,
				HttpSession session,boolean isPresidance) throws Exception {
			log.info("Enter saveEducationDetailsToSession ...");
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			List<EdnQualification> educationDetails=helper.getEducationDetailsBO(admForm,isPresidance);
			Set<CandidateEntranceDetails> candidateentrances=new HashSet<CandidateEntranceDetails>();
			if(admForm.isDisplayEntranceDetails()){
				candidateentrances=helper.getCandidateEntranceDetails(admForm,candidateentrances);
			}
			session.setAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS, educationDetails);
			
			AdmAppln applicationData=null;
			if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null){
				applicationData=(AdmAppln) session.getAttribute(CMSConstants.APPLICATION_DATA);
			}
			Set<ApplicantLateralDetails> lateraldata=null;
			if(session.getAttribute(CMSConstants.STUDENT_LATERALDETAILS)!=null){
				lateraldata=(HashSet<ApplicantLateralDetails>) session.getAttribute(CMSConstants.STUDENT_LATERALDETAILS);
			}
			Set<ApplicantTransferDetails> transferdata=null;
			if(session.getAttribute(CMSConstants.STUDENT_TRANSFERDETAILS)!=null){
				transferdata=(HashSet<ApplicantTransferDetails>) session.getAttribute(CMSConstants.STUDENT_TRANSFERDETAILS);
			}
				if(applicationData!=null){
					if(admForm.isDisplayTCDetails()){
						if(admForm.getTcDate()!=null && !StringUtils.isEmpty(admForm.getTcDate()) && CommonUtil.isValidDate(admForm.getTcDate())){
						applicationData.setTcDate(CommonUtil.ConvertStringToSQLDate(admForm.getTcDate()));
						}
						applicationData.setTcNo(admForm.getTcNo());
						if(admForm.getMarkcardDate()!=null && !StringUtils.isEmpty(admForm.getMarkcardDate()) && CommonUtil.isValidDate(admForm.getMarkcardDate())){
						applicationData.setMarkscardDate(CommonUtil.ConvertStringToSQLDate(admForm.getMarkcardDate()));
						}
						applicationData.setMarkscardNo(admForm.getMarkcardNo());
					}
					if(candidateentrances!=null && !candidateentrances.isEmpty()){
						applicationData.setCandidateEntranceDetailses(candidateentrances);
					}
					if(lateraldata!=null && !lateraldata.isEmpty()){
						applicationData.setApplicantLateralDetailses(lateraldata);
					}
					if(transferdata!=null && !transferdata.isEmpty()){
						applicationData.setApplicantTransferDetailses(transferdata);
					}
					session.setAttribute(CMSConstants.APPLICATION_DATA,applicationData);
				}
				
			
			
			log.info("Education details set to session ...");
		}
		
		/**
		 * create list of required doc.s to be uploaded in application form
		 * @param courseID
		 * @param appliedyear
		 * @return
		 * @throws Exception
		 */
		public List<ApplnDocTO> getRequiredDocList(String courseID, int appliedyear)throws Exception{
			log.info("Enter getRequiredDocList ...");
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<DocChecklist> checklistDocs=txn.getNeededDocumentList(courseID);
			log.info("Exit getRequiredDocList ...");
			return helper.createDocUploadListForCourse(checklistDocs,appliedyear);
		}
		/**
		 * get currency list
		 * @return
		 */
		public List<CurrencyTO> getCurrencies()throws Exception{
			log.info("Enter getCurrencies ...");
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<Currency> currancybos=txn.getCurrencies();
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			List<CurrencyTO> currencies=helper.convertCurrencyBOToTO(currancybos);
			log.info("Exit getCurrencies ...");
			return currencies;
		}
		/**
		 * get income list
		 * @return
		 */
		public List<IncomeTO> getIncomes()throws Exception{
			log.info("Enter getIncomes ...");
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<Object[]> incomebos=txn.getIncomes();
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			List<IncomeTO> currencies=helper.convertIncomeBOToTO(incomebos);
			log.info("Exit getIncomes ...");
			return currencies;
		}
		/**
		 * retrives everything from session and saves it...
		 * @param session
		 * @param admForm
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public boolean saveCompleteApplication(HttpSession session,
				OnlineApplicationForm admForm) throws Exception {
			log.info("Entered Save complete application in handler ...");
			PersonalData personaldata=updateParentdata(session,admForm);
			AdmAppln applicationData=null;
			Address permAddr=null;
			Address commAddr=null;
			List<CandidatePreference> preference=null;
			List<EdnQualification> qualifications=null;
			List<ApplnDoc> uploads=null;
			
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				applicationData=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
			if(session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS)!=null)
				permAddr=(Address)session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS);
			if(session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS)!=null)
				commAddr=(Address)session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS);
				updateAddressToPersonalData(permAddr,commAddr,personaldata);
			if(session.getAttribute(CMSConstants.STUDENT_PREFERENCES)!=null)
				preference=(List<CandidatePreference>)session.getAttribute(CMSConstants.STUDENT_PREFERENCES);
			if(session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS)!=null)
				qualifications=(List<EdnQualification>)session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS);
			if(session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
				uploads=(List<ApplnDoc>)session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
			
			if(applicationData!=null)
			{

				
				if(!checkApplicationNoUniqueForYear(applicationData))
				{
					return false;
				}
			
				session.setAttribute(CMSConstants.APPLICATION_DATA, applicationData);
			}
			
			
			Student newStudent=createStudentBO(applicationData, personaldata,preference, qualifications, uploads);
			boolean result=txn.persistCompleteApplicationData(newStudent, admForm);
			log.info("Exit Save complete application in handler ..."+result);
			return result;
		}
		
		
		/**
		 * retrives everything from session and makes a admappln to object..
		 * @param session
		 * @param admForm
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public AdmApplnTO getCompleteApplication(HttpSession session,
				OnlineApplicationForm admForm) throws Exception {
			log.info("Entered Save complete application in handler ...");
			PersonalData personaldata=updateParentdata(session,admForm);
			AdmAppln applicationData=null;
			Address permAddr=null;
			Address commAddr=null;
			List<CandidatePreference> preference=null;
			List<EdnQualification> qualifications=null;
			List<ApplnDoc> uploads=null;
			
			//IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
				applicationData=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
			if(session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS)!=null)
				permAddr=(Address)session.getAttribute(CMSConstants.STUDENT_PERMANENT_ADDRESS);
			if(session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS)!=null)
				commAddr=(Address)session.getAttribute(CMSConstants.STUDENT_COMM_ADDRESS);
				updateAddressToPersonalData(permAddr,commAddr,personaldata);
			if(session.getAttribute(CMSConstants.STUDENT_PREFERENCES)!=null)
				preference=(List<CandidatePreference>)session.getAttribute(CMSConstants.STUDENT_PREFERENCES);
			if(session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS)!=null)
				qualifications=(List<EdnQualification>)session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS);
			if(session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS)!=null)
				uploads=(List<ApplnDoc>)session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
			
//			if(applicationData!=null)
//			{
	//
//				
//				if(!checkApplicationNoUniqueForYear(applicationData))
//				{
//					return false;
//				}
//			
//				session.setAttribute(CMSConstants.APPLICATION_DATA, applicationData);
//			}
			
			
			Student newStudent=createStudentBO(applicationData, personaldata,preference, qualifications, uploads);
			AdmApplnTO applicantdetails=OnlineApplicationHelper.getInstance().copyPropertiesValue(newStudent.getAdmAppln(),session);
			applicantdetails.setOriginalPreferences(newStudent.getAdmAppln().getCandidatePreferences());
			log.info("Exit get complete applicant details in handler ...");
			return applicantdetails;
		}
		
		/**
		 * checks application number already exists for year and course or not
		 * @param applicationData
		 * @param courseId
		 * @return
		 * @throws Exception
		 */
		public boolean checkApplicationNoUniqueForYear(AdmAppln applicationData)throws Exception {
			log.info("Enter checkApplicationNoUniqueForYear ...");
			int applnNo=applicationData.getApplnNo();
			boolean unique=false;
			int year=0;
			if(applicationData.getAppliedYear()!=null)
				year=applicationData.getAppliedYear().intValue();
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			unique=txn.checkApplicationNoUniqueForYear(applnNo,year);
			log.info("Exit checkApplicationNoUniqueForYear ...");
			return unique;
		}
		
		
		/**
		 * adds address details to personal data
		 * @param permAddr
		 * @param commAddr
		 * @param personaldata
		 */
		private void updateAddressToPersonalData(Address permAddr,
				Address commAddr, PersonalData personaldata) {
			log.info("Enter updateAddressToPersonalData ...");
			if(permAddr!=null){
				personaldata.setPermanentAddressLine1(permAddr.getAddrLine1());
				personaldata.setPermanentAddressLine2(permAddr.getAddrLine2());
				personaldata.setPermanentAddressZipCode(permAddr.getPinCode());
				personaldata.setCityByPermanentAddressCityId(permAddr.getCity());
				if(permAddr.getState()!=null){
					personaldata.setStateByPermanentAddressStateId(permAddr.getState());
				}else{
					personaldata.setStateByPermanentAddressStateId(null);
					personaldata.setPermanentAddressStateOthers(permAddr.getStateOthers());
				}
				personaldata.setCountryByPermanentAddressCountryId(permAddr.getCountry());
			}
			if(commAddr!=null){
				personaldata.setCurrentAddressLine1(commAddr.getAddrLine1());
				personaldata.setCurrentAddressLine2(commAddr.getAddrLine2());
				personaldata.setCurrentAddressZipCode(commAddr.getPinCode());
				personaldata.setCityByCurrentAddressCityId(commAddr.getCity());
				if(commAddr.getState()!=null){
					personaldata.setStateByCurrentAddressStateId(commAddr.getState());
				}else{
					personaldata.setCurrentAddressStateOthers(commAddr.getStateOthers());
				}
				personaldata.setCountryByCurrentAddressCountryId(commAddr.getCountry());
			}
			log.info("Exit updateAddressToPersonalData ...");
		}
		
		
		/**
		 * creates student BO to persist
		 * @param applicationData
		 * @param personaldata
		 * @param preference
		 * @param qualifications
		 * @param uploads
		 * @return
		 */
		private Student createStudentBO(AdmAppln applicationData,PersonalData personaldata,List<CandidatePreference> preference,List<EdnQualification> qualifications,List<ApplnDoc> uploads) {
			log.info("Entered complete object graph creation ...");
			Student newStudent= new Student();
			newStudent.setCreatedBy(applicationData.getCreatedBy());
			newStudent.setCreatedDate(new Date());
			if (preference!=null && !preference.isEmpty()) {
				Set<CandidatePreference> preferenceset = new HashSet<CandidatePreference>();
				preferenceset.addAll(preference);
				applicationData.setCandidatePreferences(preferenceset);
			}
			if (uploads!=null && !uploads.isEmpty()) {
				Set<ApplnDoc> uploadSet = new HashSet<ApplnDoc>();
				uploadSet.addAll(uploads);
				applicationData.setApplnDocs(uploadSet);
			}
			if (qualifications!=null && !qualifications.isEmpty()) {
				Set<EdnQualification> qualfSet = new HashSet<EdnQualification>();
				qualfSet.addAll(qualifications);
				personaldata.setEdnQualifications(qualfSet);
			}
			applicationData.setPersonalData(personaldata);
			newStudent.setAdmAppln(applicationData);
			log.info("Exit complete object graph creation ...");
			return newStudent;
		}
		
		
		/**
		 * adds parent data to personal data
		 * @param session
		 * @return
		 */
		private PersonalData updateParentdata(HttpSession session,OnlineApplicationForm admForm) {
			log.info("Enter updateParentdata ...");
			PersonalData studentdata=null;
			if(session.getAttribute(CMSConstants.STUDENT_PERSONAL_DATA)!=null)
				studentdata=(PersonalData)session.getAttribute(CMSConstants.STUDENT_PERSONAL_DATA);
			if(studentdata!=null){
				studentdata.setCreatedBy(admForm.getUserId());
				studentdata.setCreatedDate(new Date());
				studentdata.setFatherEducation(admForm.getFatherEducation());
				studentdata.setMotherEducation(admForm.getMotherEducation());
				
				studentdata.setFatherName(admForm.getFatherName());
				studentdata.setMotherName(admForm.getMotherName());
				
				studentdata.setFatherEmail(admForm.getFatherEmail());
				studentdata.setMotherEmail(admForm.getMotherEmail());
				
				
				if (admForm.getFatherIncome()!=null && !StringUtils.isEmpty(admForm.getFatherIncome()) && StringUtils.isNumeric(admForm.getFatherIncome())) {
					Income fatherIncome = new Income();
					if (admForm.getFatherCurrency() != null
							&& !StringUtils.isEmpty(admForm.getFatherCurrency())
							&& StringUtils.isNumeric(admForm.getFatherCurrency())) {
						Currency fatherCurrency = new Currency();
						fatherCurrency.setId(Integer.parseInt(admForm
								.getFatherCurrency()));
						fatherIncome.setCurrency(fatherCurrency);
						studentdata
								.setCurrencyByFatherIncomeCurrencyId(fatherCurrency);
					} else {
						fatherIncome.setCurrency(null);
						studentdata.setCurrencyByFatherIncomeCurrencyId(null);
					}
					
					fatherIncome.setId(Integer.parseInt(admForm.getFatherIncome()));
					studentdata.setIncomeByFatherIncomeId(fatherIncome);
				}else{
					studentdata.setIncomeByFatherIncomeId(null);
					if (admForm.getFatherCurrency() != null
							&& !StringUtils.isEmpty(admForm.getFatherCurrency())
							&& StringUtils.isNumeric(admForm.getFatherCurrency())) {
						Currency fatherCurrency = new Currency();
						fatherCurrency.setId(Integer.parseInt(admForm
								.getFatherCurrency()));
						studentdata
								.setCurrencyByFatherIncomeCurrencyId(fatherCurrency);
					} else {
						studentdata.setCurrencyByFatherIncomeCurrencyId(null);
					}
				}
				if (admForm.getMotherIncome()!=null && !StringUtils.isEmpty(admForm.getMotherIncome()) && StringUtils.isNumeric(admForm.getMotherIncome())) {
					Income motherIncome = new Income();
					if (admForm.getMotherCurrency() != null
							&& !StringUtils.isEmpty(admForm.getMotherCurrency())
							&& StringUtils.isNumeric(admForm.getMotherCurrency())) {
						Currency motherCurrency = new Currency();
						motherCurrency.setId(Integer.parseInt(admForm
								.getMotherCurrency()));
						motherIncome.setCurrency(motherCurrency);
						studentdata
								.setCurrencyByMotherIncomeCurrencyId(motherCurrency);
					} else {
						motherIncome.setCurrency(null);
						studentdata.setCurrencyByMotherIncomeCurrencyId(null);
					}
					
					
					motherIncome.setId(Integer.parseInt(admForm.getMotherIncome()));
					studentdata.setIncomeByMotherIncomeId(motherIncome);
				}else{
					studentdata.setIncomeByMotherIncomeId(null);
					if (admForm.getMotherCurrency() != null
							&& !StringUtils.isEmpty(admForm.getMotherCurrency())
							&& StringUtils.isNumeric(admForm.getMotherCurrency())) {
						Currency motherCurrency = new Currency();
						motherCurrency.setId(Integer.parseInt(admForm
								.getMotherCurrency()));
						studentdata
								.setCurrencyByMotherIncomeCurrencyId(motherCurrency);
					} else {
						studentdata.setCurrencyByMotherIncomeCurrencyId(null);
					}
				}
				
				if(admForm.getFatherOccupation()!=null && !StringUtils.isEmpty(admForm.getFatherOccupation()) && StringUtils.isNumeric(admForm.getFatherOccupation())){
				Occupation fatherOccupation= new Occupation();
				fatherOccupation.setId(Integer.parseInt(admForm.getFatherOccupation()));
				studentdata.setOccupationByFatherOccupationId(fatherOccupation);
				}else{
					studentdata.setOccupationByFatherOccupationId(null);
				}
				if(admForm.getMotherOccupation()!=null && !StringUtils.isEmpty(admForm.getMotherOccupation()) && StringUtils.isNumeric(admForm.getMotherOccupation())){
				Occupation motherOccupation= new Occupation();
				motherOccupation.setId(Integer.parseInt(admForm.getMotherOccupation()));
				studentdata.setOccupationByMotherOccupationId(motherOccupation);
				}else{
					studentdata.setOccupationByMotherOccupationId(null);
				}
				
				AddressTO parentAddress=admForm.getParentAddress();
				AddressTO guardianAddress=admForm.getGuardianAddress();
				studentdata.setParentAddressLine1(parentAddress.getAddrLine1());
				studentdata.setGuardianAddressLine1(guardianAddress.getAddrLine1());
				studentdata.setParentAddressLine2(parentAddress.getAddrLine2());
				studentdata.setGuardianAddressLine2(guardianAddress.getAddrLine2());
				studentdata.setParentAddressLine3(parentAddress.getAddrLine3());
				studentdata.setGuardianAddressLine3(guardianAddress.getAddrLine3());
				studentdata.setParentAddressZipCode(parentAddress.getPinCode());
				studentdata.setGuardianAddressZipCode(guardianAddress.getPinCode());
				if(parentAddress.getCountryId()!=null && !StringUtils.isEmpty(parentAddress.getCountryId()) && StringUtils.isNumeric(parentAddress.getCountryId())){
				Country parentcountry= new Country();
				parentcountry.setId(Integer.parseInt(parentAddress.getCountryId()));
				studentdata.setCountryByParentAddressCountryId(parentcountry);
				}else{
					studentdata.setCountryByParentAddressCountryId(null);
				}
				if(guardianAddress.getCountryId()!=null && !StringUtils.isEmpty(guardianAddress.getCountryId()) && StringUtils.isNumeric(guardianAddress.getCountryId())){
					Country parentcountry= new Country();
					parentcountry.setId(Integer.parseInt(guardianAddress.getCountryId()));
					studentdata.setCountryByGuardianAddressCountryId(parentcountry);
					}else{
						studentdata.setCountryByGuardianAddressCountryId(null);
					}
				if (parentAddress.getStateId()!=null && !StringUtils.isEmpty(parentAddress.getStateId() ) && StringUtils.isNumeric(parentAddress.getStateId())) {
					State parentState = new State();
					parentState.setId(Integer.parseInt(parentAddress.getStateId()));
					studentdata.setStateByParentAddressStateId(parentState);
				}else {
					studentdata.setStateByParentAddressStateId(null);
					studentdata.setParentAddressStateOthers(parentAddress.getOtherState());
				}
				if (guardianAddress.getStateId()!=null && !StringUtils.isEmpty(guardianAddress.getStateId() ) && StringUtils.isNumeric(guardianAddress.getStateId())) {
					State parentState = new State();
					parentState.setId(Integer.parseInt(guardianAddress.getStateId()));
					studentdata.setStateByGuardianAddressStateId(parentState);
				}else {
					studentdata.setStateByGuardianAddressStateId(null);
					studentdata.setGuardianAddressStateOthers(guardianAddress.getOtherState());
				}
				studentdata.setCityByParentAddressCityId(parentAddress.getCity());
				studentdata.setCityByGuardianAddressCityId(guardianAddress.getCity());
				studentdata.setParentPh1(admForm.getParentPhone1());
				studentdata.setParentPh2(admForm.getParentPhone2());
				studentdata.setParentPh3(admForm.getParentPhone3());
				
				studentdata.setGuardianPh1(admForm.getGuardianPhone1());
				studentdata.setGuardianPh2(admForm.getGuardianPhone2());
				studentdata.setGuardianPh3(admForm.getGuardianPhone3());
				
				studentdata.setParentMob1(admForm.getParentMobile1());
				studentdata.setParentMob2(admForm.getParentMobile2());
				studentdata.setParentMob3(admForm.getParentMobile3());
				
				studentdata.setGuardianMob1(admForm.getGuardianMobile1());
				studentdata.setGuardianMob2(admForm.getGuardianMobile2());
				studentdata.setGuardianMob3(admForm.getGuardianMobile3());
				
				
				studentdata.setBrotherName(admForm.getBrotherName());
				studentdata.setBrotherEducation(admForm.getBrotherEducation());
				studentdata.setBrotherOccupation(admForm.getBrotherOccupation());
				studentdata.setBrotherIncome(admForm.getBrotherIncome());
				
				studentdata.setBrotherAge(admForm.getBrotherAge());
				studentdata.setGuardianName(admForm.getGuardianName());
				studentdata.setSisterName(admForm.getSisterName());
				studentdata.setSisterEducation(admForm.getSisterEducation());
				studentdata.setSisterOccupation(admForm.getSisterOccupation());
				studentdata.setSisterIncome(admForm.getSisterIncome());
				studentdata.setSisterAge(admForm.getSisterAge());
				
			}
			log.info("Exit updateParentdata ...");
			return studentdata;
		}

		/**
		 * FETCHES ALL NATIONALITIES
		 * @return
		 * @throws Exception
		 */
		public List<NationalityTO> getNationalities()throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<Nationality> nationBOs=txn.getNationalities();
			OnlineApplicationHelper helper= OnlineApplicationHelper.getInstance();
			List<NationalityTO> nationTOs=helper.convertNationalityBOtoTO(nationBOs);
			return nationTOs;
		}

		/**
		 * checks validity of application number for the course
		 * @param applicationNumber
		 * @param onlineApply
		 * @param appliedyear
		 * @param courseId
		 * @return
		 * @throws Exception
		 */
		public boolean checkApplicationNoInRange(String applicationNumber,
				boolean onlineApply, int appliedyear, String courseId) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			int applnNo=0;
			if(StringUtils.isNumeric(applicationNumber)){
				applnNo=Integer.parseInt(applicationNumber);
				return txn.checkApplicationNoInRange(applnNo,onlineApply,appliedyear,courseId);
			}else{
				return false;
			}
			
		}
		/**
		 * checks validity of application number for the course
		 * @param appliedyear
		 * @param courseId
		 * @return
		 * @throws Exception
		 */
		public boolean checkApplicationNoConfigured(int appliedyear, String courseId) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			
			return txn.checkApplicationNoConfigured(appliedyear,courseId);
		
		}
		/**
		 * generate application  number for online
		 * @param courseId
		 * @param year
		 * @param isOnline
		 * @return
		 * @throws Exception
		 */
		public String getGeneratedOnlineAppNo(String courseId, int year,boolean isOnline) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			return txn.getGeneratedOnlineAppNo(Integer.parseInt(courseId),year,isOnline);
			
		}

		/**
		 * FETCHES APPLICANT DETAILS
		 * @param applicationNumber
		 * @param applicationYear
		 * @return appDetails
		 */
		public AdmApplnTO getApplicantDetails(String applicationNumber,
				int applicationYear,boolean admissionForm,OnlineApplicationForm admForm,HttpServletRequest request) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			OnlineApplicationHelper helper = OnlineApplicationHelper.getInstance();

			AdmAppln applicantDetails = txn.getApplicantDetails(applicationNumber, applicationYear,admissionForm);
			int admittedThroughId = 0;
			if(applicantDetails!= null){
				Set<EdnQualification> ednQualificationSet = applicantDetails.getPersonalData().getEdnQualifications();
				Iterator<EdnQualification> eduItr = ednQualificationSet.iterator();
				int uniId = 0;
				int instId = 0;
				while (eduItr.hasNext()) {
					EdnQualification ednQualification = (EdnQualification) eduItr
							.next();
					if(ednQualification.getDocChecklist()!= null && ednQualification.getDocChecklist().getIsPreviousExam()){
						if(ednQualification.getUniversity()!= null){
							uniId = ednQualification.getUniversity().getId();
						}
						if(ednQualification.getCollege()!= null){
							instId = ednQualification.getCollege().getId();
						}
					}
					
				
				}
				int natId = 0;
				int resCategory = 0;
				if(applicantDetails.getPersonalData()!= null && applicantDetails.getPersonalData().getResidentCategory()!= null){
					resCategory = applicantDetails.getPersonalData().getResidentCategory().getId();
				}
				if(applicantDetails.getPersonalData().getNationality()!= null){
					natId = applicantDetails.getPersonalData().getNationality().getId();
				}
				
				//-----------intstitute, nationality, university
				if(admittedThroughId == 0 && instId > 0){
					admittedThroughId = txn.getAdmittedThroughIdForInst(instId);
				}
				if(admittedThroughId == 0 && natId > 0){
					admittedThroughId = txn.getAdmittedThroughIdForNationality(natId);
				}
				if(admittedThroughId == 0 && uniId > 0){
					admittedThroughId = txn.getAdmittedThroughIdForUniveristy(uniId);
				}
				if(admittedThroughId == 0 && resCategory > 0){
					admittedThroughId = txn.getAdmittedThroughResidentCategory(resCategory);
				}
				//------------------
				
				
				if(admittedThroughId == 0 && instId > 0 && natId > 0){
					admittedThroughId = txn.getAdmittedThroughForInstNationality(instId, natId);
				}
				
				if(admittedThroughId == 0 && instId > 0 && uniId > 0){
					admittedThroughId =  txn.getAdmittedThroughForInstUni(instId, uniId);
				}
				if(admittedThroughId == 0 &&  instId > 0 &&  resCategory > 0 ){
					admittedThroughId =  txn.getAdmittedThroughForInstRes(instId, resCategory);
				}

				if(admittedThroughId == 0 && natId > 0 && uniId > 0){
					admittedThroughId =  txn.getAdmittedThroughForNatUni(natId, uniId);
				}
				if(admittedThroughId == 0 &&  natId > 0 &&  resCategory > 0 ){
					admittedThroughId =  txn.getAdmittedThroughForNatRes(natId, resCategory );
				}
				
				if(admittedThroughId == 0 &&  resCategory > 0 &&  uniId > 0 ){
					admittedThroughId = txn.getAdmittedThroughForResUni(resCategory, uniId);
				}
				
				
				if(admittedThroughId == 0 && instId > 0 && natId > 0 && uniId > 0 ){
					admittedThroughId = txn.getAdmittedThroughForInstNationalityUni(instId, natId, uniId);
				}
				
				
				if(admittedThroughId == 0 && instId > 0 && natId > 0 && resCategory > 0 ){
					admittedThroughId = txn.getAdmittedThroughForInstNatRes(instId, natId, resCategory);
				}
				if(admittedThroughId == 0 &&  natId > 0 && uniId > 0 && resCategory > 0 ){
					admittedThroughId = txn.getAdmittedThroughForNatUniRes(natId, uniId, resCategory);
				}

				if(admittedThroughId == 0 &&  uniId > 0 && resCategory > 0 && instId > 0){
					admittedThroughId = txn.getAdmittedThroughForUniResInst(uniId, resCategory, instId);
				}
				
				
				if(admittedThroughId == 0 && instId > 0 && natId > 0 && uniId > 0 && resCategory > 0){
					admittedThroughId = txn.getAdmittedThroughForInstNationalityUniRes(instId, natId, uniId, resCategory);
				}
//				set uniqueId to the form.
				if(applicantDetails.getStudentOnlineApplication()!=null ){
					 admForm.setUniqueId(String.valueOf(applicantDetails.getStudentOnlineApplication().getId()));
				}else{
					admForm.setUniqueId(null);
				}
			}
			
			if(admittedThroughId > 0){
				AdmittedThrough admittedThrough = new AdmittedThrough();
				admittedThrough.setId(admittedThroughId);
				admittedThrough.setIsActive(true);
				applicantDetails.setAdmittedThrough(admittedThrough);
			}
			
			if(applicantDetails!=null){
				Set<Student> students=applicantDetails.getStudents();
				if(students!=null && !students.isEmpty()){
					for(Student student:students){
						request.setAttribute("STUDENT_IMAGE", "images/StudentPhotos/"+student.getId()+".jpg?"+applicantDetails.getLastModifiedDate());
					}
					
				}
			}
			
			
			//to copy the BO properties to TO
			AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails,request.getSession());
			return appDetails;
		}
		
		/**
		 * FETCHES LIST OF DETAIL SUBJECTS
		 * @param courseId
		 * @return
		 * @throws Exception
		 */
		public Map<Integer,String> getDetailedSubjectsByCourse(String courseId) throws Exception {
			Map<Integer,String> detailedSubMap = new HashMap<Integer,String>();
			List<DetailedSubjectsTO> detailedSubjectsList = DetailedSubjectsHandler.getInstance().getDetailedsubjectsByCourse(courseId);
			Iterator<DetailedSubjectsTO> itr = detailedSubjectsList.iterator();
			DetailedSubjectsTO detailedSubjectsTo;
			while(itr.hasNext()) {
				detailedSubjectsTo = itr.next();
				detailedSubMap.put(detailedSubjectsTo.getId(),detailedSubjectsTo.getSubjectName());
			}
		return detailedSubMap;	
		}

		/**
		 * CHECKS DUPLICATE PAYMENT INFORMATION OR NOT
		 * @param challanNo
		 * @param journalNo
		 * @param applicationDate
		 * @param year
		 * @param firstName 
		 * @return
		 * @throws Exception
		 */
		public boolean checkPaymentDetails(String challanNo, String journalNo,
				String applicationDate, int year) throws Exception{
			java.sql.Date applnDate=CommonUtil.ConvertStringToSQLDate(applicationDate);
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			boolean duplicate = txn.checkPaymentDetailsDuplicate(challanNo,journalNo,applnDate,year);
			return duplicate;
		}
		
		
		
		/**
		 * FETCHES Application Details
		 * @param applicationNumber
		 * @param applicationYear
		 * @param regNO 
		 * @return appDetails
		 */
		public AdmApplnTO getApplicationDetails(String applicationNumber,
				int applicationYear, String regNO,HttpServletRequest request) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			OnlineApplicationHelper helper = OnlineApplicationHelper.getInstance();

			AdmAppln applicantDetails = txn.getApplicationDetails(applicationNumber, applicationYear,regNO);

			//to copy the BO properties to TO
			AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails,request.getSession());

			return appDetails;
		}		
	
		/**
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public List<DocTypeExamsTO> getDocExamsByID(int id) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			List<DocTypeExams> examBos=txn.getDocExamsByID(id);
			return OnlineApplicationHelper.getInstance().convertDocExamBosToTOS(examBos);
		}
		
		

		/*
		 * Single application end
		 */
		public int getApplicationYear(int courseId) throws Exception{
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			int year = txn.getAppliedYearForCourse(courseId);
			return year;
		}

		
		/**
		 * getting the course Map which contain Course Code and Id from the database.
		 * @param parseInt
		 * @param i
		 * @return
		 * @throws Exception
		 */
		public Map<String, Integer> getCoursesById(int id, int mode) throws Exception{
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			String searchQuery=OnlineApplicationHelper.getInstance().getSearchQuery(id,mode);
			return txn.getCourseMapByInputId(searchQuery);
		}

		public Map<String, String> getYear() throws Exception {
			return OnlineApplicationImpl.getInstance().getYear();
		}
		
		
		public Map<String, String> getYearByMonth(String Month) throws Exception {
			return OnlineApplicationImpl.getInstance().getYearByMonth(Month);
		}
		
		
		
		/**
		 * @return
		 * @throws Exception
		 */
		public String getParameterForPGI(OnlineApplicationForm admForm) throws Exception {
			CandidatePGIDetails bo= new CandidatePGIDetails();
			bo.setCandidateName(admForm.getApplicantName());
			bo.setCandidateDob(CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob()));
			if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
				Course course=new Course();
				course.setId(Integer.parseInt(admForm.getCourseId()));
				bo.setCourse(course);
			}
			bo.setTxnStatus("Pending");
			if(admForm.getIndianCandidate()){
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().isEmpty())
					bo.setTxnAmount(new BigDecimal(admForm.getApplicationAmount()));
			}else{
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().isEmpty())
					bo.setTxnAmount(new BigDecimal(admForm.getApplicationAmount()));
			
			}
			//below setting of txn amount has to be commented once the Production phase of PGI has started 
			bo.setMobileNo1(admForm.getMobileNo1());
			bo.setMobileNo2(admForm.getMobileNo2());
			bo.setEmail(admForm.getEmail());
			
			ResidentCategory rc=new ResidentCategory();
			rc.setId(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()));
			bo.setResidentCategory(rc);
			
			bo.setRefundGenerated(false);
			bo.setCreatedBy(admForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(admForm.getUserId());
			//raghu new
			if(admForm.getUniqueId()!=null && !admForm.getUniqueId().isEmpty()){
				StudentOnlineApplication uniqueIdBO = new StudentOnlineApplication();
				uniqueIdBO.setId(Integer.parseInt(admForm.getUniqueId()));
				bo.setUniqueId(uniqueIdBO);
			}
			IAdmissionFormTransaction transaction=AdmissionFormTransactionImpl.getInstance();
			String candidateRefNo=transaction.generateCandidateRefNo(bo);
			/*StringBuilder temp=new StringBuilder();
			String productinfo="productinfo";
			admForm.setRefNo(candidateRefNo);
			admForm.setProductinfo(productinfo);
			
			//change the url of response in the msg below when it has to be released to the production. Please put the production IP
			if(candidateRefNo!=null && !candidateRefNo.isEmpty()){
				 //temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(candidateRefNo).append("|NA|").append(bo.getTxnAmount()).append("|NA|NA|NA|INR|NA|R|").append(CMSConstants.PGI_SECURITY_ID).append("|NA|NA|F|applicationFee|NA|NA|NA|NA|NA|NA|").append(CMSConstants.pgiLink);
				if(admForm.isAided())	
					temp.append(CMSConstants.PGI_WLS_MERCHANT_ID).append("|").append(candidateRefNo).append("|").append(bo.getTxnAmount()).append("|").append(productinfo).append("|").append(bo.getCandidateName()).append("|").append(bo.getEmail()).append("|||||||||||").append(CMSConstants.PGI_WLS_ENCRIPTION_KEY);
				else
					temp.append(CMSConstants.PGI_WLS_MERCHANT_ID).append("|").append(candidateRefNo).append("|").append(bo.getTxnAmount()).append("|").append(productinfo).append("|").append(bo.getCandidateName()).append("|").append(bo.getEmail()).append("|||||||||||").append(CMSConstants.PGI_WLS_ENCRIPTION_KEY);
			}
			//raghu write for pay e
			String hash=hashCal("SHA-512",temp.toString());
			admForm.setTest(temp.toString());*/
			
			//if(checkSum!=null && !checkSum.isEmpty())
			// msg.append(temp).append("|").append(checkSum);
			return candidateRefNo;
		}
		
		
		
		/**
		 * updating the response received from third party Payment Gateway
		 * @param admForm
		 * @return
		 * @throws Exception
		 */
		public boolean updateResponse(OnlineApplicationForm admForm) throws Exception{
			boolean isUpdated=false;
			/*CandidatePGIDetails bo=OnlineApplicationHelper.getInstance().convertToBo(admForm);
			IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
			isUpdated=transaction.updateReceivedStatus(bo,admForm);*/
			IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
			isUpdated=transaction.updateReceivedStatus1(admForm);
			return isUpdated;
		}
		
		
		/**
		 * Checks if Application Fee is paid through inline and Not filled the complete appln
		 * @param admForm
		 * @return
		 * @throws Exception
		 */
		public boolean checkApplnFeePaidThroughOnline(OnlineApplicationForm admForm) throws Exception{
			boolean applnFeePaid=false;
			String appName=admForm.getApplicantName();
			if(admForm.getApplicantName().contains("'")){
				appName = admForm.getApplicantName().replaceAll("'", "''");
			}
			String query="from CandidatePGIDetails c where c.candidateName='" +appName+
					"' and c.candidateDob='" +CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob())+
					//"' and c.course.id="+admForm.getCourseId()+
					"' and c.uniqueId.id="+admForm.getUniqueId()+
					
					"  and c.mobileNo1='" +admForm.getMobileNo1()+
					"' and c.mobileNo2='" +admForm.getMobileNo2()+
					"' and c.email='" +admForm.getEmail()+
					"' and c.residentCategory.id=" +admForm.getResidentCategoryForOnlineAppln()+ 
					" and c.admAppln.id is null and c.txnStatus='Success' and c.refundGenerated=0 " +
					" order by c.id desc";
			IOnlineApplicationTxn txn=new OnlineApplicationImpl();
			/* code modified by sudhir 
			 * getting the list of bos and
			 * taking the latest record from the list
			 * */
			List<CandidatePGIDetails> candidatePGIDetailsList=txn.getPaidCandidateDetails(query);
			if(candidatePGIDetailsList!=null && !candidatePGIDetailsList.isEmpty()){
				CandidatePGIDetails candidatePGIDetails = candidatePGIDetailsList.get(0);
			/* code modified by sudhir ended here	*/
				applnFeePaid=true;
				admForm.setTxnAmt(candidatePGIDetails.getTxnAmount().toPlainString());
				admForm.setTxnRefNo(candidatePGIDetails.getTxnRefNo());
				admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(candidatePGIDetails.getTxnDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
				admForm.setCandidateRefNo(candidatePGIDetails.getCandidateRefNo());
				admForm.setPaymentSuccess(true);
			}
			return applnFeePaid;
		}
		
	
		/**
		 * @param stForm
		 * @throws Exception 
		 */
		/*public void checkWorkExperianceMandatory(OnlineApplicationForm stForm) throws Exception {
			IOnlineApplicationTxn txn=new OnlineApplicationImpl();
			boolean mandatory = txn.checkWorkExperianceMandatory(stForm.getCourseId());
			stForm.setShowWorkExp(mandatory);
		}*/
		
		
		/**
		 * @param admApplnId
		 * @return
		 * @throws Exception
		 */
		public String getCandidatePGIDetails(int admApplnId ) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();

			String txnRefNo = txn.getCandidatePGIDetails(admApplnId);

			return txnRefNo;
		}
		
		
		
		
		
		
		
		/**
		 * to get checkSum
		 * @param message
		 * @param secret
		 * @return
		 */
		public static String HmacSHA256(String message,String secret)  {
			//MessageDigest md = null;
				try {
					Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
					 SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
					 sha256_HMAC.init(secret_key);
					byte raw[] = sha256_HMAC.doFinal(message.getBytes());
					StringBuffer ls_sb=new StringBuffer();
					for(int i=0;i<raw.length;i++)
						ls_sb.append(char2hex(raw[i]));
						return ls_sb.toString(); //step 6
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			}

		public static String char2hex(byte x){
			    char arr[]={
			                  '0','1','2','3',
			                  '4','5','6','7',
			                  '8','9','A','B',
			                  'C','D','E','F'
			                };

			    char c[] = {arr[(x & 0xF0)>>4],arr[x & 0x0F]};
			    return (new String(c));
			  }
		
		
		
		
		/**
		 * @param admForm
		 * @param candidatePGIId
		 */
		public void setCandidatePGIDetailsToForm(OnlineApplicationForm admForm, int candidatePGIId) throws Exception{
			IOnlineApplicationTxn transaction = OnlineApplicationImpl.getInstance();
			CandidatePGIDetails candidatePGIDetails = transaction.getCandiadatePGIDetailsById(candidatePGIId);
			if(candidatePGIDetails!=null){
				if(candidatePGIDetails.getCandidateName()!=null && !candidatePGIDetails.getCandidateName().isEmpty()){
					admForm.setApplicantName(candidatePGIDetails.getCandidateName());
				}
				if(candidatePGIDetails.getCandidateDob()!=null){
					admForm.setApplicantDob(CommonUtil.formatDates(candidatePGIDetails.getCandidateDob()));
				}
				if(candidatePGIDetails.getCourse()!=null){
						admForm.setCourseId(String.valueOf(candidatePGIDetails.getCourse().getId()));
					if(candidatePGIDetails.getCourse().getProgram()!=null){
						admForm.setProgramId(String.valueOf(candidatePGIDetails.getCourse().getProgram().getId()));
						if(candidatePGIDetails.getCourse().getProgram().getProgramType()!=null){
							admForm.setProgramTypeId(String.valueOf(candidatePGIDetails.getCourse().getProgram().getProgramType().getId()));
						}
					}
				}
				if(candidatePGIDetails.getEmail()!=null && !candidatePGIDetails.getEmail().isEmpty()){
					admForm.setEmail(candidatePGIDetails.getEmail());
					admForm.setConfirmEmail(candidatePGIDetails.getEmail());
				}
				if(candidatePGIDetails.getMobileNo1()!=null && !candidatePGIDetails.getMobileNo1().isEmpty()){
					admForm.setMobileNo1(candidatePGIDetails.getMobileNo1());
				}
				if(candidatePGIDetails.getMobileNo2()!=null && !candidatePGIDetails.getMobileNo2().isEmpty()){
					admForm.setMobileNo2(candidatePGIDetails.getMobileNo2());
				}
				if(candidatePGIDetails.getResidentCategory()!=null){
					admForm.setResidentCategoryForOnlineAppln(String.valueOf(candidatePGIDetails.getResidentCategory().getId()));
				}
				// get Program Map details.
				Map<Integer,String> programMap = CommonAjaxHandler.getInstance() .getApplnProgramsByProgramType(Integer.parseInt(admForm.getProgramTypeId()));
			 if(programMap!=null && !programMap.isEmpty()){
				 admForm.setProgramMap(programMap);
			 }else{
				 admForm.setProgramMap(null);
			 }
			 // get Course Map based on programId.
			 Map<Integer,String>  courseMap = CommonAjaxHandler.getInstance().getCourseByProgramForOnline( Integer.parseInt(admForm.getProgramId()));
			 if(courseMap!=null && !courseMap.isEmpty()){
				 admForm.setCourseMap(courseMap);
			 }else{
				 admForm.setCourseMap(null);
				 
			 }
			}
			
		}
		
		public void getUniqueId(OnlineApplicationForm admForm) throws Exception {
			log.info("Enter getEdnQualifications ...");
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			/*OnlineApplicationForm adm= */txn.getUniqueId(admForm);
		}
		
		
		
		public boolean checkCourseInDraftMode(String uniqueId, int courseId) throws Exception {
			IOnlineApplicationTxn txn= new OnlineApplicationImpl();
			return txn.checkCourseInDraftMode(uniqueId,courseId);
		}
		
		
		
		
		/**
		 * @return
		 * @throws Exception
		 */
		/*public String getParameterForPGI(AdmissionFormForm admForm) throws Exception {
			
			CandidatePGIDetails bo= new CandidatePGIDetails();
			bo.setCandidateName(admForm.getApplicantName());
			bo.setCandidateDob(CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob()));
			if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
			Course course=new Course();
			course.setId(Integer.parseInt(admForm.getCourseId()));
			bo.setCourse(course);
			}
			bo.setTxnStatus("Pending");
			if(admForm.getIndianCandidate()){
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().isEmpty())
					bo.setTxnAmount(new BigDecimal(admForm.getApplicationAmount()));
			}else{
				//if(admForm.getEquivalentCalcApplnFeeINR()!=null && !admForm.getEquivalentCalcApplnFeeINR().isEmpty())
					//bo.setTxnAmount(new BigDecimal(admForm.getEquivalentCalcApplnFeeINR()));
				if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().isEmpty())
					bo.setTxnAmount(new BigDecimal(admForm.getApplicationAmount()));
			
			}
			//below setting of txn amount has to be commented once the Production phase of PGI has started 
//			bo.setTxnAmount(new BigDecimal("2"));// for testing purpose we are passing 2 rupees
			bo.setMobileNo1(admForm.getMobileNo1());
			bo.setMobileNo2(admForm.getMobileNo2());
			bo.setEmail(admForm.getEmail());
			ResidentCategory rc=new ResidentCategory();
			rc.setId(Integer.parseInt(admForm.getResidentCategoryForOnlineAppln()));
			bo.setRefundGenerated(false);
			bo.setResidentCategory(rc);
			bo.setCreatedBy(admForm.getUserId());
			bo.setCreatedDate(new Date());
			bo.setLastModifiedDate(new Date());
			bo.setModifiedBy(admForm.getUserId());
			
			IAdmissionFormTransaction transaction=AdmissionFormTransactionImpl.getInstance();
			String candidateRefNo=transaction.generateCandidateRefNo(bo);
			StringBuilder temp=new StringBuilder();
			//StringBuilder msg=new StringBuilder();
			
			
			/*JSONObject productinfo1 = new JSONObject();
			productinfo1.put("name","abc");
			productinfo1.put("description","abcd");
			productinfo1.put("value",bo.getTxnAmount());
			productinfo1.put("isRequired","true");
			productinfo1.put("settlementEvent",bo.getEmail());
			
			String productinfo="productinfo";
			admForm.setRefNo(candidateRefNo);
			//admForm.setProductinfo1(productinfo1);
			admForm.setProductinfo(productinfo);
			
			//change the url of response in the msg below when it has to be released to the production. Please put the production IP
			if(candidateRefNo!=null && !candidateRefNo.isEmpty())
			 //temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(candidateRefNo).append("|NA|").append(bo.getTxnAmount()).append("|NA|NA|NA|INR|NA|R|").append(CMSConstants.PGI_SECURITY_ID).append("|NA|NA|F|applicationFee|NA|NA|NA|NA|NA|NA|").append(CMSConstants.pgiLink);
				temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(candidateRefNo).append("|").append(bo.getTxnAmount()).append("|").append(productinfo).append("|").append(bo.getCandidateName()).append("|").append(bo.getEmail()).append("|||||||||||").append(CMSConstants.PGI_SECURITY_ID);
			//sha512 ("key|txnid|amount|productinfo|firstname|email|||||||||||","<SALT>");
			//raghu write for pay e
			String hash=hashCal("SHA-512",temp.toString());
			admForm.setTest(temp.toString());
			
			//if(checkSum!=null && !checkSum.isEmpty())
			// msg.append(temp).append("|").append(checkSum);
			return hash;
	}
		
	*/
		public String hashCal(String type,String str){
			byte[] hashseq=str.getBytes();
			StringBuffer hexString = new StringBuffer();
			try{
			MessageDigest algorithm = MessageDigest.getInstance(type);
			algorithm.reset();
			algorithm.update(hashseq);
			byte messageDigest[] = algorithm.digest();

			for (int i=0;i<messageDigest.length;i++) {
				String hex=Integer.toHexString(0xFF & messageDigest[i]);
				if(hex.length()==1) hexString.append("0");
				hexString.append(hex);
			}
				
			}catch(NoSuchAlgorithmException nsae){ }
			
			return hexString.toString();


		}
		
		
		public String hasha1(String type,String str){
			
			
			java.security.MessageDigest digest = null;
			String myString =str;
			  try {
				  
			 
			  digest = java.security.MessageDigest.getInstance("SHA-1");

			  digest.reset();
			  
			  digest.update(myString.getBytes("UTF-8"));
			  
			  } catch (Exception e) {
				e.printStackTrace();
			  }
			 // System.out.println( new String(Base64.encodeBase64(digest.digest())));
			return new String(Base64.encodeBase64(digest.digest()));

		

		}
		
		private void send_email_new(OnlineApplicationForm admForm) throws Exception{
		 	
		 	boolean sent=false;
			Properties prop = new Properties();
			String toAddress="";
			if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !admForm.getApplicantDetails().getPersonalData().getEmail().isEmpty()){
				toAddress = toAddress + admForm.getApplicantDetails().getPersonalData().getEmail();
	 		}
			String collegeName = CMSConstants.COLLEGE_NAME;
			String subject="Application submitted successfully "+collegeName;
			String msg= "Your application " + admForm.getApplicantDetails().getApplnNo() + " has been submitted successfully. You shall submit the printout of the application along with all necessary documents and certifications to the college office by post / hand within 5 days.  Please save this message for all future reference.";
			
		 	
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
					InternetAddress from = new InternetAddress(adminmail, collegeName + " Online Admission");
					
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
		
		private void send_sms_new(OnlineApplicationForm objForm) throws Exception{

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
			temp=temp+URLEncoder.encode("Your application " + objForm.getApplicantDetails().getApplnNo() + " has been submitted successfully. Please save this message for all future reference."
										,"UTF-8");		
			
			String candidateMobileNumber = objForm.getApplicantDetails().getPersonalData().getMobileNo();
			String[] array = candidateMobileNumber.split(" ");
			candidateMobileNumber = array[0] + array[1];
			
			PasswordMobileMessaging mob=new PasswordMobileMessaging();						
			mob.setDestinationNumber(candidateMobileNumber);
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
		
		public List<CandidatePGIDetailsTO> getCandidatesPendingStatus(OnlineApplicationForm admForm) throws Exception{
			
			IOnlineApplicationTxn pgiTxn = OnlineApplicationImpl.getInstance();
			List<CandidatePGIDetails> pendingTxns = pgiTxn.getAllPendingTransactionStatausOfCandidate(admForm);
			return OnlineApplicationHelper.getInstance().covertPGIBOToTO(pendingTxns);
		}
		public PersonalDataTO convertBOTO(PersonalData personalData,OnlineApplicationForm loginForm, Student student)throws Exception {
			PersonalDataTO personalDataTO = new PersonalDataTO();
			String name="";
			int year=0;
			if (personalData != null) {
				personalDataTO = new PersonalDataTO();
				personalDataTO.setId(personalData.getId());
				personalDataTO.setCreatedBy(personalData.getCreatedBy());
				personalDataTO.setCreatedDate(personalData.getCreatedDate());
				personalDataTO.setIsmgquota(personalData.getIsmgquota());
				if(personalData.getFirstName()!=null){
					name=personalData.getFirstName();
				}
				if(personalData.getMiddleName()!=null){
					name=name+" "+personalData.getMiddleName();
				}
				if(personalData.getLastName()!=null){
					name=name+" "+personalData.getLastName();
				}
				personalDataTO.setFirstName(name);
				if( personalData.getDateOfBirth()!= null){
					personalDataTO.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getDateOfBirth()), OnlineApplicationHandler.SQL_DATEFORMAT,OnlineApplicationHandler.FROM_DATEFORMAT));
				}
				personalDataTO.setBirthPlace(personalData.getBirthPlace());
				if(personalData.getIsHandicapped()!= null)
					personalDataTO.setHandicapped(personalData.getIsHandicapped());
				if(personalData.getIsSportsPerson()!= null)
					personalDataTO.setSportsPerson(personalData.getIsSportsPerson());
				if(personalData.getHandicappedDescription()!= null)
					personalDataTO.setHadnicappedDescription(personalData.getHandicappedDescription());
				if(personalData.getSportsPersonDescription()!= null)
				    personalDataTO.setSportsDescription(personalData.getSportsPersonDescription());
				
				//raghu
				if(personalData.getSports()!= null)
				personalDataTO.setSports(personalData.getSports());
				if(personalData.getArts()!= null)
				personalDataTO.setArts(personalData.getArts());
				if(personalData.getSportsParticipate()!= null)
				personalDataTO.setSportsParticipate(personalData.getSportsParticipate());
				if(personalData.getArtsParticipate()!= null)
				personalDataTO.setArtsParticipate(personalData.getArtsParticipate());
				if(personalData.getFatherMobile()!= null)
				personalDataTO.setFatherMobile(personalData.getFatherMobile());
				if(personalData.getMotherMobile()!= null)
				personalDataTO.setMotherMobile(personalData.getMotherMobile());
				if(personalData.getIsNcccertificate()!=null){
					personalDataTO.setNcccertificate(personalData.getIsNcccertificate());
				}
				if(personalData.getIsNsscertificate()!=null){
					personalDataTO.setNsscertificate(personalData.getIsNsscertificate());
				}
				if(personalData.getIsExcervice()!=null){
					personalDataTO.setExservice(personalData.getIsExcervice());
				}
				if(personalData.getIsNcccertificate()!=null && personalData.getIsNcccertificate()){
					if(personalData.getNccgrade()!=null){
					personalDataTO.setNccgrades(personalData.getNccgrade());
					}else{
						personalDataTO.setNccgrades("");
					}
					
				}
				else{
					personalDataTO.setNccgrades("");
				}
				if(personalData.getIsNsscertificate()!=null && personalData.getIsNsscertificate()){
					if(personalData.getNssgrade()!=null){
					personalDataTO.setNssgrades(personalData.getNssgrade());
					}else{
						personalDataTO.setNssgrades("");
					}
					
				}
				else{
					personalDataTO.setNssgrades("");
				}
			
			if(personalData.getDioceseOthers()!=null &&!StringUtils.isEmpty(personalData.getDioceseOthers()) ){
				personalDataTO.setDioceseOthers(personalData.getDioceseOthers());
			}
			
			if(personalData.getParishOthers()!=null &&!StringUtils.isEmpty(personalData.getParishOthers()) ){
				
				personalDataTO.setParishOthers(personalData.getParishOthers());
			}
			
		  if(personalData.getUgcourse()!=null)
			personalDataTO.setUgcourse(personalData.getUgcourse().getId()+"");
					
				
		  if( personalData.getPermanentAddressDistrcictOthers()!= null && !personalData.getPermanentAddressDistrcictOthers().isEmpty()){
				personalDataTO.setPermanentAddressDistrictOthers(personalData.getPermanentAddressDistrcictOthers());
				personalDataTO.setPermanentDistricId(OnlineApplicationHandler.OTHER);
		  }else if (personalData.getStateByParentAddressDistrictId() != null) {
				personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
				personalDataTO.setPermanentDistricId(""+personalData.getStateByParentAddressDistrictId().getId());
			}
		
			if( personalData.getCurrenttAddressDistrcictOthers()!= null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()){
				personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
				personalDataTO.setCurrentDistricId(OnlineApplicationHandler.OTHER);
			}else if (personalData.getStateByCurrentAddressDistrictId() != null) {
				personalDataTO.setCurrentDistricName(personalData.getStateByCurrentAddressDistrictId().getName());
				personalDataTO.setCurrentDistricId(""+personalData.getStateByCurrentAddressDistrictId().getId());
			}
			
			 if(personalData.getStream()!=null)
					personalDataTO.setStream(personalData.getStream().getId()+"");
			 if(personalData.getNoofenglishCoreCourses()!=null)	
				 personalDataTO.setNoofenglishCoreCourses(personalData.getNoofenglishCoreCourses());
			 
				if(personalData.getHeight()!=null)
				personalDataTO.setHeight(String.valueOf(personalData.getHeight().intValue()));
				if(personalData.getWeight()!=null)
				personalDataTO.setWeight(String.valueOf(personalData.getWeight().doubleValue()));
				if(personalData.getLanguageByLanguageRead()!=null)
				personalDataTO.setLanguageByLanguageRead(personalData.getLanguageByLanguageRead());
				if(personalData.getLanguageByLanguageSpeak()!=null)
				personalDataTO.setLanguageByLanguageSpeak(personalData.getLanguageByLanguageSpeak());
				if(personalData.getLanguageByLanguageWrite()!=null)
				personalDataTO.setLanguageByLanguageWrite(personalData.getLanguageByLanguageWrite());
				if(personalData.getMotherTongue()!=null)
				personalDataTO.setMotherTongue(String.valueOf(personalData.getMotherTongue().getId()));
				if(personalData.getTrainingDuration()!=null)
				personalDataTO.setTrainingDuration(String.valueOf(personalData.getTrainingDuration()));
				personalDataTO.setTrainingInstAddress(personalData.getTrainingInstAddress());
				personalDataTO.setTrainingProgName(personalData.getTrainingProgName());
				personalDataTO.setTrainingPurpose(personalData.getTrainingPurpose());
				
				personalDataTO.setCourseKnownBy(personalData.getCourseKnownBy());
				personalDataTO.setCourseOptReason(personalData.getCourseOptReason());
				personalDataTO.setStrength(personalData.getStrength());
				personalDataTO.setWeakness(personalData.getWeakness());
				personalDataTO.setOtherAddnInfo(personalData.getOtherAddnInfo());
				personalDataTO.setSecondLanguage(personalData.getSecondLanguage());
				if(personalData.getStudentExtracurriculars()!=null && !personalData.getStudentExtracurriculars().isEmpty()){
					Iterator<StudentExtracurricular> extrItr=personalData.getStudentExtracurriculars().iterator();
					List<StudentExtracurricular> templist= new ArrayList<StudentExtracurricular>();
					List<StudentExtracurricular> origlist= new ArrayList<StudentExtracurricular>();
					templist.addAll(personalData.getStudentExtracurriculars());
					StringBuffer extrcurNames=new StringBuffer();
					String[] extraIds=new String[templist.size()];
					int i=0;
					while (extrItr.hasNext()) {
						StudentExtracurricular studentExtr = (StudentExtracurricular) extrItr.next();
						if(studentExtr.getExtracurricularActivity()!=null && studentExtr.getIsActive()==true){
							origlist.add(studentExtr);
							ExtracurricularActivity bo=studentExtr.getExtracurricularActivity();
							if(bo.getIsActive()==true){
								extraIds[i]=String.valueOf(bo.getId());
								if(i==personalData.getStudentExtracurriculars().size()-1){
								extrcurNames.append(bo.getName());
								}else{
									extrcurNames.append(bo.getName());
									extrcurNames.append(",");
								}
								i++;
							}
						}
						
					}
					personalDataTO.setStudentExtracurriculars(origlist);
					personalDataTO.setExtracurricularIds(extraIds);
					personalDataTO.setExtracurricularNames(extrcurNames.toString());
				}
				
				
				
				if( personalData.getStateOthers() != null &&  !personalData.getStateOthers().isEmpty()){
					personalDataTO.setBirthState(OnlineApplicationHandler.OTHER);
					personalDataTO.setStateOthers(personalData.getStateOthers());
					personalDataTO.setStateOfBirth(personalData.getStateOthers());
				}else if (personalData.getStateByStateId() != null) {
					personalDataTO.setStateOfBirth(personalData.getStateByStateId().getName());
					personalDataTO.setBirthState(String.valueOf(personalData.getStateByStateId().getId()));
				}
				if( personalData.getCountryOthers() != null &&  !personalData.getCountryOthers().isEmpty()){
					personalDataTO.setCountryOfBirth(personalData.getCountryOthers());
				} else if (personalData.getCountryByCountryId() != null) {
					personalDataTO.setCountryOfBirth(personalData.getCountryByCountryId().getName());
					personalDataTO.setBirthCountry(String.valueOf(personalData.getCountryByCountryId().getId()));
				}
				if( personalData.getNationalityOthers()!= null && !personalData.getNationalityOthers().isEmpty()){
					personalDataTO.setCitizenship(personalData.getNationalityOthers());
					personalDataTO.setNationalityOthers(personalData.getNationalityOthers());
				}else if( personalData.getNationality() != null){
					personalDataTO.setCitizenship(personalData.getNationality().getName());
					personalDataTO.setNationality(String.valueOf(personalData.getNationality().getId()));
				}
				if (personalData.getResidentCategory() != null) {
					personalDataTO.setResidentCategoryName(personalData.getResidentCategory().getName());
					personalDataTO.setResidentCategory(String.valueOf(personalData.getResidentCategory().getId()));
				}
				if( personalData.getReligionOthers()!=null && !personalData.getReligionOthers().isEmpty()){
					personalDataTO.setReligionId(OnlineApplicationHandler.OTHER);
					personalDataTO.setReligionOthers(personalData.getReligionOthers());
					personalDataTO.setReligionName(personalData.getReligionOthers());
				} else if (personalData.getReligion() != null) {
					personalDataTO.setReligionName(personalData.getReligion().getName());
					personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
				}
				if( personalData.getReligionSectionOthers()!=null && !personalData.getReligionSectionOthers().isEmpty()){
					personalDataTO.setSubReligionId(OnlineApplicationHandler.OTHER);
					personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
					personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
				}else if (personalData.getReligionSection() != null) {
					personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
					personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
				
					if(personalData.getReligionSection().getName().equalsIgnoreCase("SEBC")){
						personalDataTO.setReservation("Applicable");
					}else{
						personalDataTO.setReservation("Not Applicable");
					}
					
					if(personalData.getReligionSection().getName().equalsIgnoreCase("OEC")){
						personalDataTO.setReservation1("Applicable");
					}else{
						personalDataTO.setReservation1("Not Applicable");
					}
					
				}
				if (personalData.getCasteOthers() != null && !personalData.getCasteOthers().isEmpty()) {
					personalDataTO.setCasteCategory(personalData.getCasteOthers());
					personalDataTO.setCasteOthers(personalData.getCasteOthers());
					personalDataTO.setCasteId(OnlineApplicationHandler.OTHER);
				}else if (personalData.getCaste() != null) {
					personalDataTO.setCasteCategory(personalData.getCaste().getName());
					personalDataTO.setCasteId(String.valueOf(personalData.getCaste().getId()));
				}
				if(personalData.getRuralUrban()!=null){
					personalDataTO.setRuralUrban(personalData.getRuralUrban());
					personalDataTO.setAreaType(personalData.getRuralUrban());
				}
				personalDataTO.setGender(personalData.getGender());
				personalDataTO.setBloodGroup(personalData.getBloodGroup());
				personalDataTO.setPhNo1(personalData.getPhNo1());
				personalDataTO.setPhNo2(personalData.getPhNo2());
				personalDataTO.setPhNo3(personalData.getPhNo3());
				personalDataTO.setMobileNo1(personalData.getMobileNo1());
				personalDataTO.setMobileNo2(personalData.getMobileNo2());
				personalDataTO.setMobileNo3(personalData.getMobileNo3());
				if (personalData.getPhNo1()!=null && personalData.getPhNo2()!=null && personalData.getPhNo3()!=null) {
					personalDataTO.setLandlineNo(personalData.getPhNo1() + " "+ personalData.getPhNo2() + " " + personalData.getPhNo3());
				}else if(personalData.getPhNo2()!=null && personalData.getPhNo3()!=null){
					personalDataTO.setLandlineNo(personalData.getPhNo2() + " " + personalData.getPhNo3());
				}else if (personalData.getPhNo3()!=null) {
					personalDataTO.setLandlineNo(personalData.getPhNo3());
				}
				personalDataTO.setMobileNo(personalData.getMobileNo1() + " "+ personalData.getMobileNo2());
				personalDataTO.setEmail(personalData.getEmail());
				personalDataTO.setPassportNo(personalData.getPassportNo());
				personalDataTO.setResidentPermitNo(personalData.getResidentPermitNo());
				if (personalData.getCountryByPassportCountryId() != null) {
					personalDataTO.setPassportCountry(personalData.getCountryByPassportCountryId().getId());
					personalDataTO.setPassportIssuingCountry(personalData.getCountryByPassportCountryId().getName());
				}
				if( personalData.getPassportValidity() != null ){
					personalDataTO.setPassportValidity(CommonUtil.getStringDate(personalData.getPassportValidity()));
				}
				if( personalData.getResidentPermitDate() != null ){
					personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), OnlineApplicationHandler.SQL_DATEFORMAT,OnlineApplicationHandler.FROM_DATEFORMAT));
				}
				personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
				personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
				if (personalData.getCityByPermanentAddressCityId() != null) {
					personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
				}
				if (personalData.getPermanentAddressStateOthers()!= null && !personalData.getPermanentAddressStateOthers().isEmpty()){
					personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
					personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
					personalDataTO.setPermanentStateId(OnlineApplicationHandler.OTHER);
				}else if (personalData.getStateByPermanentAddressStateId() != null) {
					personalDataTO.setPermanentStateName(personalData.getStateByPermanentAddressStateId().getName());
					personalDataTO.setPermanentStateId(String.valueOf(personalData.getStateByPermanentAddressStateId().getId()));
				}
				if(personalData.getPermanentAddressCountryOthers()!=null && !personalData.getPermanentAddressCountryOthers().isEmpty()){
					personalDataTO.setPermanentCountryName(personalData.getPermanentAddressCountryOthers());
				}
				if (personalData.getCountryByPermanentAddressCountryId() != null) {
					personalDataTO.setPermanentCountryName(personalData.getCountryByPermanentAddressCountryId().getName());
					personalDataTO.setPermanentCountryId(personalData.getCountryByPermanentAddressCountryId().getId());
				}
				personalDataTO.setPermanentAddressZipCode(personalData.getPermanentAddressZipCode());
				personalDataTO.setCurrentAddressLine1(personalData.getCurrentAddressLine1());
				personalDataTO.setCurrentAddressLine2(personalData.getCurrentAddressLine2());
				if (personalData.getCityByCurrentAddressCityId() != null) {
					personalDataTO.setCurrentCityName(personalData.getCityByCurrentAddressCityId());
				}
				if (personalData.getCurrentAddressStateOthers()!= null && !personalData.getCurrentAddressStateOthers().isEmpty()){
					personalDataTO.setCurrentStateName(personalData.getCurrentAddressStateOthers());
					personalDataTO.setCurrentAddressStateOthers(personalData.getCurrentAddressStateOthers());
					personalDataTO.setCurrentStateId(OnlineApplicationHandler.OTHER);
				}else if (personalData.getStateByCurrentAddressStateId() != null) {
					personalDataTO.setCurrentStateName(personalData.getStateByCurrentAddressStateId().getName());
					personalDataTO.setCurrentStateId(String.valueOf(personalData.getStateByCurrentAddressStateId().getId()));
				}
				if( personalData.getCurrentAddressCountryOthers()!= null && !personalData.getCurrentAddressCountryOthers().isEmpty()){
					personalDataTO.setCurrentCountryName(personalData.getCurrentAddressCountryOthers());
				}else if (personalData.getCountryByCurrentAddressCountryId() != null) {
					personalDataTO.setCurrentCountryName(personalData.getCountryByCurrentAddressCountryId().getName());
					personalDataTO.setCurrentCountryId(personalData.getCountryByCurrentAddressCountryId().getId());
				}
				personalDataTO.setCurrentAddressZipCode(personalData.getCurrentAddressZipCode());
				
				if(personalData.getCurrentStreet()!=null && !personalData.getCurrentStreet().isEmpty())
					personalDataTO.setCurrentStreet(personalData.getCurrentStreet());
				if(personalData.getPermanentStreet()!=null && !personalData.getPermanentStreet().isEmpty())
					personalDataTO.setPermanentStreet(personalData.getPermanentStreet());
			
				if(personalData.getFatherOccupationAddress()!=null && !personalData.getFatherOccupationAddress().isEmpty())
					personalDataTO.setFatherOccupationAddress(personalData.getFatherOccupationAddress());
				
				if(personalData.getMotherOccupationAddress()!=null && !personalData.getMotherOccupationAddress().isEmpty())
					personalDataTO.setMotherOccupationAddress(personalData.getMotherOccupationAddress());
				
				personalDataTO.setFatherName(personalData.getFatherName());
				personalDataTO.setFatherEducation(personalData.getFatherEducation());
				if (personalData.getIncomeByFatherIncomeId()!=null && personalData.getIncomeByFatherIncomeId() != null) {
					if(personalData.getCurrencyByFatherIncomeCurrencyId()!=null){
					personalDataTO.setFatherIncome(personalData.getCurrencyByFatherIncomeCurrencyId().getCurrencyCode() +personalData.getIncomeByFatherIncomeId().getIncomeRange());
					personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
					personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
					}else{
					personalDataTO.setFatherIncome(personalData.getIncomeByFatherIncomeId().getIncomeRange());
					}
					personalDataTO.setFatherIncomeRange(personalData.getIncomeByFatherIncomeId().getIncomeRange());
					personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
					
				}
				if (personalData.getOccupationByFatherOccupationId() != null) {
					personalDataTO.setFatherOccupation(personalData.getOccupationByFatherOccupationId().getName());
					personalDataTO.setFatherOccupationId(String.valueOf(personalData.getOccupationByFatherOccupationId().getId()));
				}else if(personalData.getOtherOccupationFather() != null){
					personalDataTO.setFatherOccupationId(OnlineApplicationHandler.OTHER);
					personalDataTO.setOtherOccupationFather(personalData.getOtherOccupationFather());
				}
				personalDataTO.setFatherEmail(personalData.getFatherEmail());
				//Mary..
				personalDataTO.setMotherName(personalData.getMotherName());
				personalDataTO.setMotherEducation(personalData.getMotherEducation());

				if (personalData.getIncomeByMotherIncomeId() != null) {
					if(personalData.getCurrencyByMotherIncomeCurrencyId() !=null) 
						personalDataTO.setMotherIncome(personalData.getCurrencyByMotherIncomeCurrencyId().getCurrencyCode()+ personalData.getIncomeByMotherIncomeId().getIncomeRange());
					else
						personalDataTO.setMotherIncome(personalData.getIncomeByMotherIncomeId().getIncomeRange());
					personalDataTO.setMotherIncomeRange(personalData.getIncomeByMotherIncomeId().getIncomeRange());
					personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
				}
				
				if(personalData.getIncomeByFatherIncomeId()!=null && personalData.getCurrencyByFatherIncomeCurrencyId()==null) {
					personalDataTO.setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
				}
				if(personalData.getIncomeByMotherIncomeId()!=null && personalData.getCurrencyByMotherIncomeCurrencyId()==null) {
					personalDataTO.setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
				}
				if(personalData.getCurrencyByMotherIncomeCurrencyId() != null) {
					personalDataTO.setMotherCurrencyId(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getId()));
					personalDataTO.setMotherCurrency(personalData.getCurrencyByMotherIncomeCurrencyId().getName());
				}
				if(personalData.getCurrencyByFatherIncomeCurrencyId() != null) {
					personalDataTO.setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
					personalDataTO.setFatherCurrency(personalData.getCurrencyByFatherIncomeCurrencyId().getName());
				}
				if (personalData.getOccupationByMotherOccupationId() != null) {
					personalDataTO.setMotherOccupation(personalData.getOccupationByMotherOccupationId().getName());
					personalDataTO.setMotherOccupationId(String.valueOf(personalData.getOccupationByMotherOccupationId().getId()));
				}else if(personalData.getOtherOccupationMother() != null){
					personalDataTO.setMotherOccupationId(OnlineApplicationHandler.OTHER);
					personalDataTO.setOtherOccupationMother(personalData.getOtherOccupationMother());
				}
				personalDataTO.setMotherEmail(personalData.getMotherEmail());
				personalDataTO.setParentAddressLine1(personalData.getParentAddressLine1());
				personalDataTO.setParentAddressLine2(personalData.getParentAddressLine2());
				personalDataTO.setParentAddressLine3(personalData.getParentAddressLine3());
				if (personalData.getCityByParentAddressCityId() != null) {
					personalDataTO.setParentCityName(personalData.getCityByParentAddressCityId());
				}
				if ( personalData.getParentAddressStateOthers()!= null && !personalData.getParentAddressStateOthers().isEmpty()){
					personalDataTO.setParentStateName(personalData.getParentAddressStateOthers());
					personalDataTO.setParentAddressStateOthers(personalData.getParentAddressStateOthers());
					personalDataTO.setParentStateId(OnlineApplicationHandler.OTHER);
				}else if (personalData.getStateByParentAddressStateId() != null) {
					personalDataTO.setParentStateName(personalData.getStateByParentAddressStateId().getName());
					personalDataTO.setParentStateId(String.valueOf(personalData.getStateByParentAddressStateId().getId()));
				}
				if( personalData.getParentAddressCountryOthers() != null && !personalData.getParentAddressCountryOthers().isEmpty()){
					personalDataTO.setParentCountryName(personalData.getParentAddressCountryOthers());
				}else if (personalData.getCountryByParentAddressCountryId() != null) {
					personalDataTO.setParentCountryName(personalData.getCountryByParentAddressCountryId().getName());
					personalDataTO.setParentCountryId(personalData.getCountryByParentAddressCountryId().getId());
				}
				personalDataTO.setParentAddressZipCode(personalData.getParentAddressZipCode());
				personalDataTO.setParentPhone(personalData.getParentPh1() + " "+ personalData.getParentPh2() + " "+ personalData.getParentPh3());
				personalDataTO.setParentPh1(personalData.getParentPh1());
				personalDataTO.setParentPh2(personalData.getParentPh2());
				personalDataTO.setParentPh3(personalData.getParentPh3());
				personalDataTO.setParentMobile(personalData.getParentMob1() + " "+ personalData.getParentMob2() + " "+ personalData.getParentMob3());
				personalDataTO.setParentMob1(personalData.getParentMob1());
				personalDataTO.setParentMob2(personalData.getParentMob2());
				personalDataTO.setParentMob3(personalData.getParentMob3());
				
				personalDataTO.setGuardianAddressLine1(personalData.getGuardianAddressLine1());
				personalDataTO.setGuardianAddressLine2(personalData.getGuardianAddressLine2());
				personalDataTO.setGuardianAddressLine3(personalData.getGuardianAddressLine3());
				if (personalData.getCityByGuardianAddressCityId() != null) {
					personalDataTO.setCityByGuardianAddressCityId(personalData.getCityByGuardianAddressCityId());
				}
				if ( personalData.getGuardianAddressStateOthers()!= null && !personalData.getGuardianAddressStateOthers().isEmpty()){
					personalDataTO.setGuardianAddressStateOthers(personalData.getGuardianAddressStateOthers());
					personalDataTO.setGuardianStateName(personalData.getGuardianAddressStateOthers());
					personalDataTO.setStateByGuardianAddressStateId(OnlineApplicationHandler.OTHER);
				}else if (personalData.getStateByGuardianAddressStateId() != null) {
					personalDataTO.setGuardianStateName(personalData.getStateByGuardianAddressStateId().getName());
					personalDataTO.setStateByGuardianAddressStateId(String.valueOf(personalData.getStateByGuardianAddressStateId().getId()));
				}
				if (personalData.getCountryByGuardianAddressCountryId() != null) {
					personalDataTO.setCountryByGuardianAddressCountryId(personalData.getCountryByGuardianAddressCountryId().getId());
					personalDataTO.setGuardianCountryName(personalData.getCountryByGuardianAddressCountryId().getName());
				}
				personalDataTO.setGuardianAddressZipCode(personalData.getGuardianAddressZipCode());
				
				personalDataTO.setGuardianPh1(personalData.getGuardianPh1());
				personalDataTO.setGuardianPh2(personalData.getGuardianPh2());
				personalDataTO.setGuardianPh3(personalData.getGuardianPh3());
				
				personalDataTO.setGuardianMob1(personalData.getGuardianMob1());
				personalDataTO.setGuardianMob2(personalData.getGuardianMob2());
				personalDataTO.setGuardianMob3(personalData.getGuardianMob3());
				
				personalDataTO.setBrotherName(personalData.getBrotherName());
				personalDataTO.setBrotherEducation(personalData.getBrotherEducation());
				personalDataTO.setBrotherOccupation(personalData.getBrotherOccupation());
				personalDataTO.setBrotherIncome(personalData.getBrotherIncome());
				
				personalDataTO.setBrotherAge(personalData.getBrotherAge());
				
				personalDataTO.setSisterName(personalData.getSisterName());
				personalDataTO.setGuardianName(personalData.getGuardianName());
				personalDataTO.setSisterEducation(personalData.getSisterEducation());
				personalDataTO.setSisterOccupation(personalData.getSisterOccupation());
				personalDataTO.setSisterIncome(personalData.getSisterIncome());
				personalDataTO.setSisterAge(personalData.getSisterAge());
				if(personalData.getUniversityEmail()!=null && !personalData.getUniversityEmail().isEmpty())
					personalDataTO.setUniversityEmail(personalData.getUniversityEmail());
				if(personalData.getAadharCardNumber() != null && !personalData.getAadharCardNumber().isEmpty())
					personalDataTO.setAadhaarCardNumber(personalData.getAadharCardNumber());
				if(personalData.getMotherTongue() != null)
					personalDataTO.setMotherTongueId(String.valueOf(personalData.getMotherTongue().getId()));
				if(personalData.getFatherPANNumber() != null && !personalData.getFatherPANNumber().isEmpty())
					personalDataTO.setFatherPANNumber(personalData.getFatherPANNumber());
				if(personalData.getFatherAadhaarNumber() != null && !personalData.getFatherAadhaarNumber().isEmpty())
					personalDataTO.setFatherAadhaarNumber(personalData.getFatherAadhaarNumber());
				if(personalData.getIsmgquota() != null)
					personalDataTO.setIsmgquota(personalData.getIsmgquota());
				if(personalData.getRecommentedBy() != null && !personalData.getRecommentedBy().isEmpty())
					personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
				if(personalData.getRecommendedBy() != null && !personalData.getRecommendedBy().isEmpty())
					personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
				if(personalData.getRecommentedPersonDesignation()!= null && !personalData.getRecommentedPersonDesignation().isEmpty())
					personalDataTO.setRecommentedPersonDesignation(personalData.getRecommentedPersonDesignation());
				if(personalData.getRecommentedPersonMobile() != null && !personalData.getRecommentedPersonMobile().isEmpty())
					personalDataTO.setRecommentedPersonMobile(personalData.getRecommentedPersonMobile());
				if(personalData.getPreferenceNoCAP() != null) {
					personalDataTO.setPreferenceNoCAP(personalData.getPreferenceNoCAP());
				}
				ISingleFieldMasterTransaction singleFieldTx = SingleFieldMasterTransactionImpl.getInstance();
				List<MotherTongue> motherTongueList = singleFieldTx.getMotherTongueFields();
				Map<Integer, String> motherTongues = new HashMap<Integer, String>();
				Iterator<MotherTongue> mtItr = motherTongueList.iterator();
				while(mtItr.hasNext()) {
					
					MotherTongue motherTongue = mtItr.next();
					motherTongues.put(motherTongue.getId(), motherTongue.getName());
				}
				personalDataTO.setMotherTongues(motherTongues);
				loginForm.setResidentTypes(OnlineApplicationHandler.getInstance().getResidentTypes());
				loginForm.setNationalities(OnlineApplicationHandler.getInstance().getNationalities());
				loginForm.setCountries(CountryHandler.getInstance().getCountries());
				
				Map<Integer,String> stateMap =new HashMap<Integer,String>();
				
				if(loginForm.getBirthCountryId()!=0){
					stateMap = CommonAjaxImpl.getInstance().getStates(loginForm.getBirthCountryId());
				}else{
					 stateMap = CommonAjaxImpl.getInstance().getStates(0);
				}
				loginForm.setStateMap(stateMap);
				Map<Integer,String> curAddrStateMap =new HashMap<Integer,String>();
				
				if(loginForm.getCurAddrCountyId()!=0){
					curAddrStateMap = CommonAjaxImpl.getInstance().getStates(loginForm.getCurAddrCountyId());
				}else{
					curAddrStateMap = CommonAjaxImpl.getInstance().getStates(0);
				}
				loginForm.setCurAddrStateMap(curAddrStateMap);
				
				
				Map<Integer,String> perAddrStateMap =new HashMap<Integer,String>();
				
				if(loginForm.getPerAddrCountyId()!=0){
					perAddrStateMap = CommonAjaxImpl.getInstance().getStates(loginForm.getPerAddrCountyId());
				}else{
					perAddrStateMap = CommonAjaxImpl.getInstance().getStates(0);
				}
				loginForm.setPerAddrStateMap(perAddrStateMap);
				Map<Integer,String> parentAddstateMap =new HashMap<Integer,String>();
				
				if(loginForm.getParentAddrCountyId()!=0){
					parentAddstateMap = CommonAjaxImpl.getInstance().getStates(loginForm.getParentAddrCountyId());
				}else{
					parentAddstateMap = CommonAjaxImpl.getInstance().getStates(0);
				}
				loginForm.setParentStateMap(parentAddstateMap);
				
				
				Map<Integer,String> guardianAddstateMap =new HashMap<Integer,String>();
				
				if(loginForm.getGuardianAddrCountyId()!=0){
					guardianAddstateMap = CommonAjaxImpl.getInstance().getStates(loginForm.getGuardianAddrCountyId());
				}else{
					guardianAddstateMap = CommonAjaxImpl.getInstance().getStates(0);
				}
				loginForm.setGuardianStateMap(guardianAddstateMap);
				
				
				List<StateTO> permanentStates = StateHandler.getInstance().getStates();
				if (permanentStates!=null) {
					Iterator<StateTO> stitr= permanentStates.iterator();
					while (stitr.hasNext()) {
						StateTO stateTO = (StateTO) stitr.next();
						if(personalData.getStateByPermanentAddressStateId()!=null ){
						if(stateTO.getId()== personalData.getStateByPermanentAddressStateId().getId()){
							List<DistrictTO> districtList = stateTO.getDistrictList();
							Collections.sort(districtList);
							loginForm.setEditPermanentDistrict(districtList);
						}
					
						}else{
							
							List<DistrictTO> districtList = new ArrayList<DistrictTO>();
							loginForm.setEditPermanentDistrict(districtList);
						
						}
					}
				}
				
				
				//currentDistrict & states
				List<StateTO> currentStates = StateHandler.getInstance().getStates();
				if (currentStates!=null) {
					//loginForm.setCountries(permanentCountries);
					Iterator<StateTO> stitr= currentStates.iterator();
					while (stitr.hasNext()) {
						StateTO stateTO = (StateTO) stitr.next();
						if(personalData.getStateByCurrentAddressStateId()!=null ){
							
						
						if(stateTO.getId()== personalData.getStateByCurrentAddressStateId().getId()){
							List<DistrictTO> districtList = stateTO.getDistrictList();
							Collections.sort(districtList);
							loginForm.setEditCurrentDistrict(districtList);
						}
					}
						else{
							List<DistrictTO> districtList = new ArrayList<DistrictTO>();
							
							loginForm.setEditCurrentDistrict(districtList);
						}
					}
				}
				loginForm.setOccupations(OccupationTransactionHandler.getInstance().getAllOccupation());
				loginForm.setIncomeList(OnlineApplicationHandler.getInstance().getIncomes());
				List<CurrencyTO> currencyList = OnlineApplicationHandler.getInstance().getCurrencies();
				Map<Integer,String> currencyMap=new HashMap<Integer,String>();
				if(currencyList!=null && currencyList.size()>0){
					for(CurrencyTO curTo:currencyList){
						if(curTo!=null){
							currencyMap.put(curTo.getId(), curTo.getName());
						}
						
					}
					
				}
				loginForm.setCurrencyList(currencyList);
				loginForm.setCurrencyMap(currencyMap);
				loginForm.setReligions(ReligionHandler.getInstance().getReligion());
				loginForm.setSubReligions(SubReligionHandler.getInstance().getSubReligion());
				loginForm.setSubReligionMap(CommonAjaxHandler.getInstance().getSubReligion());
				loginForm.setSubCasteMap(CommonAjaxHandler.getInstance().getSubCasteByReligion(personalData.getReligion().getId()));
				loginForm.setCheckReligionId(CMSConstants.RELIGION_CHRISTIAN_TYPE);
				DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
				long startTime = fmt.parse(CMSConstants.RADIOS_HIDE_DATE_SPORTS).getTime();
				long presentTime = Calendar.getInstance().getTime().getTime();
				if(presentTime > startTime) {
					loginForm.setDateExpired(true);
				}
				else {
					loginForm.setDateExpired(false);
				}
				AdmApplnTO applnTO = new AdmApplnTO();
				year=student.getAdmAppln().getAppliedYear();
				if(student.getAdmAppln().getApplnDocs()!=null && student.getAdmAppln().getApplnDocs().size()!=0){
					 List<ApplnDocTO> editDocuments =OnlineApplicationHelper.getInstance().copyPropertiesEditDocValue(student.getAdmAppln(), Integer.parseInt(loginForm.getCourseId()), applnTO, year);
					 applnTO.setEditDocuments(editDocuments);
				}else{
					List<ApplnDocTO> reqList=OnlineApplicationHandler.getInstance().getRequiredDocList(String.valueOf(loginForm.getCourseId()),year);
					applnTO.setEditDocuments(reqList);
				
				}
				loginForm.setApplicantDetails(applnTO);
				
				if(personalData.getMbaEntranceExam() != null) {
					personalDataTO.setMbaEntranceExamId(String.valueOf(personalData.getMbaEntranceExam().getId()));
					personalDataTO.setEntranceMarksSecured(String.valueOf(personalData.getEntranceMarksSecured()));
				}
			}
			return personalDataTO;
		}
		public boolean savePersonalData(OnlineApplicationForm loginForm,PersonalDataTO dataTO,PersonalData data,Student student,HttpServletRequest request)throws Exception {
			
			if(loginForm.getPersonalData()!=null){

				PersonalDataTO dataTo=loginForm.getPersonalData();
				data.setModifiedBy(loginForm.getUserId());
				data.setLastModifiedDate(new Date());
				data.setFirstName(dataTo.getFirstName().toUpperCase());
				data.setMiddleName(dataTo.getMiddleName());
				data.setLastName(dataTo.getLastName());
				if( dataTo.getDob()!= null){
					data.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(dataTo.getDob()));
				}
				if(dataTo.getBirthPlace()!=null && !dataTo.getBirthPlace().isEmpty()){
					data.setBirthPlace(dataTo.getBirthPlace());
				}
				if (dataTo.getBirthState() != null && !StringUtils.isEmpty(dataTo.getBirthState()) && StringUtils.isNumeric(dataTo.getBirthState())) {
					State birthState=new State();
					birthState.setId(Integer.parseInt(dataTo.getBirthState()));
					data.setStateByStateId(birthState);
					data.setStateOthers(null);
				}
				else if( dataTo.getStateOthers() != null &&  !dataTo.getStateOthers().isEmpty()){
					data.setStateByStateId(null);
					data.setStateOthers(dataTo.getStateOthers());
				}
				
				if(dataTo.getHeight()!=null && !StringUtils.isEmpty(dataTo.getHeight()) && StringUtils.isNumeric(dataTo.getHeight()))
					data.setHeight(Integer.parseInt(dataTo.getHeight()));
				if(dataTo.getWeight()!=null && !StringUtils.isEmpty(dataTo.getWeight()) && CommonUtil.isValidDecimal(dataTo.getWeight()))
					data.setWeight(new BigDecimal(dataTo.getWeight()));
				if(dataTo.getLanguageByLanguageRead()!=null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageRead()) ){
					data.setLanguageByLanguageRead(dataTo.getLanguageByLanguageRead());
				}else{
					data.setLanguageByLanguageRead(null);
				}
				if(dataTo.getLanguageByLanguageWrite()!=null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageWrite()) ){
					data.setLanguageByLanguageWrite(dataTo.getLanguageByLanguageWrite());
				}else{
					data.setLanguageByLanguageWrite(null);
				}
				if(dataTo.getLanguageByLanguageSpeak()!=null && !StringUtils.isEmpty(dataTo.getLanguageByLanguageSpeak()) ){
					data.setLanguageByLanguageSpeak(dataTo.getLanguageByLanguageSpeak());
				}else{
					data.setLanguageByLanguageSpeak(null);
				}
				if(dataTo.getMotherTongue()!=null && !StringUtils.isEmpty(dataTo.getMotherTongue()) && StringUtils.isNumeric(dataTo.getMotherTongue())){
					MotherTongue readlang= new MotherTongue();
					readlang.setId(Integer.parseInt(dataTo.getMotherTongue()));
					data.setMotherTongue(readlang);
				}else{
					data.setMotherTongue(null);
				}
				
				if(dataTo.getTrainingDuration()!=null && !StringUtils.isEmpty(dataTo.getTrainingDuration()) && StringUtils.isNumeric(dataTo.getTrainingDuration()))
					data.setTrainingDuration(Integer.parseInt(dataTo.getTrainingDuration()));
				else
					data.setTrainingDuration(0);
				if(dataTo.getTrainingInstAddress()!=null && !dataTo.getTrainingInstAddress().isEmpty())
					data.setTrainingInstAddress(dataTo.getTrainingInstAddress());
				if(dataTo.getTrainingProgName()!=null && !dataTo.getTrainingProgName().isEmpty())
					data.setTrainingProgName(dataTo.getTrainingProgName());
				if(dataTo.getTrainingPurpose()!=null && !dataTo.getTrainingPurpose().isEmpty())
					data.setTrainingPurpose(dataTo.getTrainingPurpose());
				if(dataTo.getCourseKnownBy()!=null && !dataTo.getCourseKnownBy().isEmpty())
				    data.setCourseKnownBy(dataTo.getCourseKnownBy());
				if(dataTo.getCourseOptReason()!=null && !dataTo.getCourseOptReason().isEmpty())
				    data.setCourseOptReason(dataTo.getCourseOptReason());
				if(dataTo.getStrength()!=null && !dataTo.getStrength().isEmpty())
				    data.setStrength(dataTo.getStrength());
				if(dataTo.getWeakness()!=null && !dataTo.getWeakness().isEmpty())
				    data.setWeakness(dataTo.getWeakness());
				if(dataTo.getOtherAddnInfo()!=null && !dataTo.getOtherAddnInfo().isEmpty())
				    data.setOtherAddnInfo(dataTo.getOtherAddnInfo());
				if(dataTo.getSecondLanguage()!=null && !dataTo.getSecondLanguage().isEmpty())
				    data.setSecondLanguage(dataTo.getSecondLanguage());
				 
				 
				
				if (dataTo.getNationality()!=null && !StringUtils.isEmpty(dataTo.getNationality()) && StringUtils.isNumeric(dataTo.getNationality())) {
					Nationality nat= new Nationality();
					nat.setId(Integer.parseInt(dataTo.getNationality()));
					data.setNationality(nat);
				}
				if(dataTo.getBloodGroup()!=null && !dataTo.getBloodGroup().isEmpty())
					data.setBloodGroup(dataTo.getBloodGroup().toUpperCase());
				if(dataTo.getEmail()!=null && !dataTo.getEmail().isEmpty())
					data.setEmail(dataTo.getEmail());
				if(dataTo.getGender()!=null && !dataTo.getGender().isEmpty())
					data.setGender(dataTo.getGender().toUpperCase());
				data.setIsHandicapped(dataTo.isHandicapped());
				data.setIsSportsPerson(dataTo.isSportsPerson());
				if(dataTo.getSportsDescription()!=null && !dataTo.getSportsDescription().isEmpty())
				data.setSportsPersonDescription(dataTo.getSportsDescription());
				if(dataTo.getHadnicappedDescription()!=null && !dataTo.getHadnicappedDescription().isEmpty())
				data.setHandicappedDescription(dataTo.getHadnicappedDescription());
				 if (dataTo.getUgcourse()!=null && !StringUtils.isEmpty(dataTo.getUgcourse()) && StringUtils.isNumeric(dataTo.getUgcourse())) {
						UGCoursesBO ug= new UGCoursesBO();
						ug.setId(Integer.parseInt(dataTo.getUgcourse()));
						data.setUgcourse(ug);
				 }
				data.setSports(dataTo.getSports());
				data.setArts(dataTo.getArts());
				data.setSportsParticipate(dataTo.getSportsParticipate());
				data.setArtsParticipate(dataTo.getArtsParticipate());
				data.setFatherMobile(dataTo.getFatherMobile());
				data.setMotherMobile(dataTo.getMotherMobile());
				
				data.setIsNcccertificate(dataTo.isNcccertificate());
				data.setIsNsscertificate(dataTo.isNsscertificate());
				data.setIsExcervice(dataTo.isExservice());
				if(dataTo.isNcccertificate()){
					data.setNccgrade(dataTo.getNccgrades());
				}
				else
					data.setNccgrade(null);
				
				if(dataTo.isNsscertificate())
					data.setNssgrade(dataTo.getNssgrades());
				else
					data.setNssgrade(null);
		
				if(dataTo.getDioceseOthers()!=null &&!StringUtils.isEmpty(dataTo.getDioceseOthers()) ){
					data.setDioceseOthers(dataTo.getDioceseOthers());
				}else{
					data.setDioceseOthers(null);
				}
				
				if(dataTo.getParishOthers()!=null &&!StringUtils.isEmpty(dataTo.getParishOthers()) ){
					
					data.setParishOthers(dataTo.getParishOthers());
				}else{
					data.setParishOthers(null);
				}
				
				if(dataTo.getPermanentDistricId()!=null && !StringUtils.isEmpty(dataTo.getPermanentDistricId()) && StringUtils.isNumeric(dataTo.getPermanentDistricId())){
					if(Integer.parseInt(dataTo.getPermanentDistricId())!=0){
						District permState=new District();
						permState.setId(Integer.parseInt(dataTo.getPermanentDistricId()));
						data.setStateByParentAddressDistrictId(permState);
						data.setPermanentAddressDistrcictOthers(null);
					}
				}else{
					data.setStateByParentAddressDistrictId(null);
					data.setPermanentAddressDistrcictOthers(dataTo.getPermanentAddressDistrictOthers());
				}
				
				if(dataTo.getCurrentDistricId()!=null && !StringUtils.isEmpty(dataTo.getCurrentDistricId()) && StringUtils.isNumeric(dataTo.getCurrentDistricId())){
					if(Integer.parseInt(dataTo.getCurrentDistricId())!=0){
						District currState=new District();
					currState.setId(Integer.parseInt(dataTo.getCurrentDistricId()));
					data.setStateByCurrentAddressDistrictId(currState);
					data.setCurrenttAddressDistrcictOthers(null);
					}
				}else{
					data.setStateByCurrentAddressDistrictId(null);
					data.setCurrenttAddressDistrcictOthers(dataTo.getCurrentAddressDistrictOthers());
				}
				
				
				 if (dataTo.getStream()!=null && !StringUtils.isEmpty(dataTo.getStream()) && StringUtils.isNumeric(dataTo.getStream())) {
						EducationStream stream= new EducationStream();
						stream.setId(Integer.parseInt(dataTo.getStream()));
						data.setStream(stream);
				 }
				if(loginForm.getCourseId()!=null && loginForm.getCourseId().equalsIgnoreCase("18")){
					if(dataTo.getNoofenglishCoreCourses()!=null)	
						 data.setNoofenglishCoreCourses(dataTo.getNoofenglishCoreCourses());
					
				}else{
					 data.setNoofenglishCoreCourses(null);
				}
				ResidentCategory res_cat=null;
				if (dataTo.getResidentCategory()!=null && !StringUtils.isEmpty(dataTo.getResidentCategory()) && StringUtils.isNumeric(dataTo.getResidentCategory())) {
					res_cat = new ResidentCategory();
					res_cat.setId(Integer.parseInt(dataTo.getResidentCategory()));
					data.setResidentCategory(res_cat);
				}
					data.setRuralUrban(dataTo.getAreaType());
				if(dataTo.getPhNo1()!=null && !dataTo.getPhNo1().isEmpty())
					data.setPhNo1(dataTo.getPhNo1());
				if(dataTo.getPhNo2()!=null && !dataTo.getPhNo2().isEmpty())
					data.setPhNo2(dataTo.getPhNo2());
				if(dataTo.getPhNo3()!=null && !dataTo.getPhNo3().isEmpty())
					data.setPhNo3(dataTo.getPhNo3());
				if(dataTo.getMobileNo1()!=null && !dataTo.getMobileNo1().isEmpty())
					data.setMobileNo1(dataTo.getMobileNo1());
				if(dataTo.getMobileNo2()!=null && !dataTo.getMobileNo2().isEmpty())
					data.setMobileNo2(dataTo.getMobileNo2());
				if(dataTo.getMobileNo3()!=null && !dataTo.getMobileNo3().isEmpty())
					data.setMobileNo3(dataTo.getMobileNo3());
				
				Religion religionbo=null;
				if(dataTo.getReligionId()!=null && !StringUtils.isEmpty(dataTo.getReligionId()) && StringUtils.isNumeric(dataTo.getReligionId())){
					religionbo= new Religion();
					religionbo.setId(Integer.parseInt(dataTo.getReligionId()));
						if (dataTo.getSubReligionId()!=null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
							ReligionSection subreligionBO = new ReligionSection();
							subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
							subreligionBO.setReligion(religionbo);
							data.setReligionSection(subreligionBO);
							data.setReligionSectionOthers(null);
						}else{
							data.setReligionSection(null);
							data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
						}
						data.setReligion(religionbo);	
						data.setReligionOthers(null);
					}else{
						data.setReligion(null);	
						data.setReligionOthers(dataTo.getReligionOthers());
						if (dataTo.getSubReligionId()!=null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
							ReligionSection subreligionBO = new ReligionSection();
							subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
							subreligionBO.setReligion(religionbo);
							data.setReligionSection(subreligionBO);
							data.setReligionSectionOthers(null);
						}else{
							data.setReligionSection(null);
							data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
						}
					}
					if (dataTo.getCasteId()!=null &&!StringUtils.isEmpty(dataTo.getCasteId()) && StringUtils.isNumeric(dataTo.getCasteId()) ) {
						Caste casteBO = new Caste();
						casteBO.setId(Integer.parseInt(dataTo.getCasteId()));
						data.setCaste(casteBO);
						data.setCasteOthers(null);
					}else{
						data.setCaste(null);
						data.setCasteOthers(dataTo.getCasteOthers());
					}
				if(dataTo.getPassportNo()!=null && !dataTo.getPassportNo().isEmpty())
						data.setPassportNo(dataTo.getPassportNo());
				if(dataTo.getResidentPermitNo()!=null && !dataTo.getResidentPermitNo().isEmpty())
					data.setResidentPermitNo(dataTo.getResidentPermitNo());
					if(dataTo.getPassportValidity()!=null && !StringUtils.isEmpty(dataTo.getPassportValidity()))
						data.setPassportValidity(CommonUtil.ConvertStringToSQLDate(dataTo.getPassportValidity()));
					if(dataTo.getResidentPermitDate()!=null && !StringUtils.isEmpty(dataTo.getResidentPermitDate()))
							data.setResidentPermitDate(CommonUtil.ConvertStringToSQLDate(dataTo.getResidentPermitDate()));
					if (dataTo.getPassportCountry()!=0) {
						Country passportcnt = new Country();
						passportcnt.setId(dataTo.getPassportCountry());
						data.setCountryByPassportCountryId(passportcnt);
					}
					if(dataTo.getBirthCountry()!=null  && !StringUtils.isEmpty(dataTo.getBirthCountry().trim()) && StringUtils.isNumeric(dataTo.getBirthCountry())){
					Country ownCnt= new Country();
					ownCnt.setId(Integer.parseInt(dataTo.getBirthCountry()));
					data.setCountryByCountryId(ownCnt);
					loginForm.setBirthCountryId(Integer.parseInt(dataTo.getBirthCountry()));
					
					}
				if(dataTo.getPermanentAddressLine1()!=null && !dataTo.getPermanentAddressLine1().isEmpty())
					data.setPermanentAddressLine1(dataTo.getPermanentAddressLine1());
				if(dataTo.getPermanentAddressLine2()!=null && !dataTo.getPermanentAddressLine2().isEmpty())
					data.setPermanentAddressLine2(dataTo.getPermanentAddressLine2());
				if(dataTo.getPermanentAddressZipCode()!=null && !dataTo.getPermanentAddressZipCode().isEmpty())
					data.setPermanentAddressZipCode(dataTo.getPermanentAddressZipCode());
				if(dataTo.getPermanentCityName()!=null && !dataTo.getPermanentCityName().isEmpty())
					data.setCityByPermanentAddressCityId(dataTo.getPermanentCityName());
				if(dataTo.getPermanentStreet()!=null && !dataTo.getPermanentStreet().isEmpty())
					data.setPermanentStreet(dataTo.getPermanentStreet());
				
						if(dataTo.getPermanentStateId()!=null && !StringUtils.isEmpty(dataTo.getPermanentStateId()) && StringUtils.isNumeric(dataTo.getPermanentStateId())){
							if(Integer.parseInt(dataTo.getPermanentStateId())!=0){
								State permState=new State();
								permState.setId(Integer.parseInt(dataTo.getPermanentStateId()));
								data.setStateByPermanentAddressStateId(permState);
								data.setPermanentAddressStateOthers(null);
							}
						}else{
							data.setStateByPermanentAddressStateId(null);
							data.setPermanentAddressStateOthers(dataTo.getPermanentAddressStateOthers());
						}
				if(dataTo.getPermanentCountryId()!=0){
						Country permCnt= new Country();
						permCnt.setId(dataTo.getPermanentCountryId());
						data.setCountryByPermanentAddressCountryId(permCnt);
						loginForm.setPerAddrCountyId(dataTo.getPermanentCountryId());
				}
				if(dataTo.getCurrentAddressLine1()!=null && !dataTo.getCurrentAddressLine1().isEmpty())	
						data.setCurrentAddressLine1(dataTo.getCurrentAddressLine1());
				if(dataTo.getCurrentAddressLine2()!=null && !dataTo.getCurrentAddressLine2().isEmpty())
						data.setCurrentAddressLine2(dataTo.getCurrentAddressLine2());
				if(dataTo.getCurrentAddressZipCode()!=null && !dataTo.getCurrentAddressZipCode().isEmpty())
						data.setCurrentAddressZipCode(dataTo.getCurrentAddressZipCode());
				if(dataTo.getCurrentCityName()!=null && !dataTo.getCurrentCityName().isEmpty())
						data.setCityByCurrentAddressCityId(dataTo.getCurrentCityName());
				
				if(dataTo.getCurrentStateId()!=null && !StringUtils.isEmpty(dataTo.getCurrentStateId()) && StringUtils.isNumeric(dataTo.getCurrentStateId())){
								if(Integer.parseInt(dataTo.getCurrentStateId())!=0){
								State currState=new State();
								currState.setId(Integer.parseInt(dataTo.getCurrentStateId()));
								data.setStateByCurrentAddressStateId(currState);
								data.setCurrentAddressStateOthers(null);
					}
				}else{
								data.setStateByCurrentAddressStateId(null);
								data.setCurrentAddressStateOthers(dataTo.getCurrentAddressStateOthers());
				}
				if(dataTo.getCurrentCountryId()>0){
							Country currCnt= new Country();
							currCnt.setId(dataTo.getCurrentCountryId());
							data.setCountryByCurrentAddressCountryId(currCnt);	
							loginForm.setCurAddrCountyId(dataTo.getCurrentCountryId());

				}	
				
				if(dataTo.getCurrentStreet()!=null && !dataTo.getCurrentStreet().isEmpty())
					data.setCurrentStreet(dataTo.getCurrentStreet());
				
				if(dataTo.getFatherOccupationAddress()!=null && !dataTo.getFatherOccupationAddress().isEmpty())
					data.setFatherOccupationAddress(dataTo.getFatherOccupationAddress());
				
				if(dataTo.getMotherOccupationAddress()!=null && !dataTo.getMotherOccupationAddress().isEmpty())
					data.setMotherOccupationAddress(dataTo.getMotherOccupationAddress());
				
				if(dataTo.getFatherEducation()!=null && !dataTo.getFatherEducation().isEmpty())		
							data.setFatherEducation(dataTo.getFatherEducation());
				if(dataTo.getMotherEducation()!=null && !dataTo.getMotherEducation().isEmpty())	
							data.setMotherEducation(dataTo.getMotherEducation());
				if(dataTo.getFatherName()!=null && !dataTo.getFatherName().isEmpty())				
							data.setFatherName(dataTo.getFatherName());
				if(dataTo.getMotherName()!=null && !dataTo.getMotherName().isEmpty())	
							data.setMotherName(dataTo.getMotherName());
				if(dataTo.getFatherEmail()!=null && !dataTo.getFatherEmail().isEmpty())				
							data.setFatherEmail(dataTo.getFatherEmail());
				if(dataTo.getMotherEmail()!=null && !dataTo.getMotherEmail().isEmpty())		
							data.setMotherEmail(dataTo.getMotherEmail());
							
							if(dataTo.getFatherIncomeId()!=null && !StringUtils.isEmpty(dataTo.getFatherIncomeId()) && StringUtils.isNumeric(dataTo.getFatherIncomeId())){
								Income fatherIncome= new Income();
							
										if(dataTo.getFatherCurrencyId()!=null && !StringUtils.isEmpty(dataTo.getFatherCurrencyId())  && StringUtils.isNumeric(dataTo.getFatherCurrencyId())){
											Currency fatherCurrency= new Currency();
											fatherCurrency.setId(Integer.parseInt(dataTo.getFatherCurrencyId()));
										fatherIncome.setCurrency(fatherCurrency);
										data.setCurrencyByFatherIncomeCurrencyId(fatherCurrency);
										}else{
											fatherIncome.setCurrency(null);
											data.setCurrencyByFatherIncomeCurrencyId(null);
										}
									fatherIncome.setId(Integer.parseInt(dataTo.getFatherIncomeId()));
									data.setIncomeByFatherIncomeId(fatherIncome);
							}else{
								data.setIncomeByFatherIncomeId(null);
								if(dataTo.getFatherCurrencyId()!=null && !StringUtils.isEmpty(dataTo.getFatherCurrencyId())  && StringUtils.isNumeric(dataTo.getFatherCurrencyId())){
									Currency fatherCurrency= new Currency();
									fatherCurrency.setId(Integer.parseInt(dataTo.getFatherCurrencyId()));
									data.setCurrencyByFatherIncomeCurrencyId(fatherCurrency);
								}else{
									data.setCurrencyByFatherIncomeCurrencyId(null);
								}
							}
							if(dataTo.getMotherIncomeId()!=null && !StringUtils.isEmpty(dataTo.getMotherIncomeId()) && StringUtils.isNumeric(dataTo.getMotherIncomeId())){
								Income motherIncome= new Income();
							
								if(dataTo.getMotherCurrencyId()!=null && !StringUtils.isEmpty(dataTo.getMotherCurrencyId())  && StringUtils.isNumeric(dataTo.getMotherCurrencyId())){
									Currency motherCurrency= new Currency();
									motherCurrency.setId(Integer.parseInt(dataTo.getMotherCurrencyId()));
									motherIncome.setCurrency(motherCurrency);
									data.setCurrencyByMotherIncomeCurrencyId(motherCurrency);
								}else{
									motherIncome.setCurrency(null);
									data.setCurrencyByMotherIncomeCurrencyId(null);
								}
								motherIncome.setId(Integer.parseInt(dataTo.getMotherIncomeId()));
								data.setIncomeByMotherIncomeId(motherIncome);
							}else{
								
								data.setIncomeByMotherIncomeId(null);
								if(dataTo.getMotherCurrencyId()!=null && !StringUtils.isEmpty(dataTo.getMotherCurrencyId())  && StringUtils.isNumeric(dataTo.getMotherCurrencyId())){
									Currency motherCurrency= new Currency();
									motherCurrency.setId(Integer.parseInt(dataTo.getMotherCurrencyId()));
									data.setCurrencyByMotherIncomeCurrencyId(motherCurrency);
								}else{
									data.setCurrencyByMotherIncomeCurrencyId(null);
								}
							}
						
							
							if(dataTo.getFatherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) && StringUtils.isNumeric(dataTo.getFatherOccupationId()) && !dataTo.getFatherOccupationId().equalsIgnoreCase("other")){
								Occupation fatherOccupation= new Occupation();
								fatherOccupation.setId(Integer.parseInt(dataTo.getFatherOccupationId()));
								data.setOccupationByFatherOccupationId(fatherOccupation);
								data.setOtherOccupationFather(null);
							}else if(dataTo.getFatherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) 
									&& dataTo.getFatherOccupationId().equalsIgnoreCase(OnlineApplicationHandler.OTHER) && dataTo.getOtherOccupationFather()!=null 
									&& !StringUtils.isEmpty(dataTo.getOtherOccupationFather())){
								data.setOtherOccupationFather(dataTo.getOtherOccupationFather());
								data.setOccupationByFatherOccupationId(null);
							}else{
								data.setOccupationByFatherOccupationId(null);
							}
							if(dataTo.getMotherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getMotherOccupationId()) && StringUtils.isNumeric(dataTo.getMotherOccupationId()) && !dataTo.getMotherOccupationId().equalsIgnoreCase("other")){
								Occupation motherOccupation= new Occupation();
								motherOccupation.setId(Integer.parseInt(dataTo.getMotherOccupationId()));
								data.setOccupationByMotherOccupationId(motherOccupation);
								data.setOtherOccupationMother(null);
							}else if(dataTo.getMotherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getMotherOccupationId())  
									&& dataTo.getMotherOccupationId().equalsIgnoreCase(OnlineApplicationHandler.OTHER) && dataTo.getOtherOccupationMother()!=null 
									&& !StringUtils.isEmpty(dataTo.getOtherOccupationMother())){
								data.setOtherOccupationMother(dataTo.getOtherOccupationMother());
								data.setOccupationByMotherOccupationId(null);
							}else{
								data.setOccupationByMotherOccupationId(null);
							}
							if(dataTo.getParentAddressLine1()!=null && !dataTo.getParentAddressLine1().isEmpty())
								data.setParentAddressLine1(dataTo.getParentAddressLine1());
							if(dataTo.getParentAddressLine2()!=null && !dataTo.getParentAddressLine2().isEmpty())
								data.setParentAddressLine2(dataTo.getParentAddressLine2());
							if(dataTo.getParentAddressLine3()!=null && !dataTo.getParentAddressLine3().isEmpty())
								data.setParentAddressLine3(dataTo.getParentAddressLine3());
							if(dataTo.getParentAddressZipCode()!=null && !dataTo.getParentAddressZipCode().isEmpty())
								data.setParentAddressZipCode(dataTo.getParentAddressZipCode());
							if(dataTo.getParentCountryId()!=0){
							Country parentcountry= new Country();
							parentcountry.setId(dataTo.getParentCountryId());
							data.setCountryByParentAddressCountryId(parentcountry);
							loginForm.setParentAddrCountyId(dataTo.getParentCountryId());
							}else{
								data.setCountryByParentAddressCountryId(null);
							}
							if (dataTo.getParentStateId()!=null && !StringUtils.isEmpty(dataTo.getParentStateId()) && StringUtils.isNumeric(dataTo.getParentStateId())) {
								State parentState = new State();
								parentState.setId(Integer.parseInt(dataTo.getParentStateId()));
								data.setStateByParentAddressStateId(parentState);
								data.setParentAddressStateOthers(null);
							}else{
								data.setStateByParentAddressStateId(null);
								data.setParentAddressStateOthers(dataTo.getParentAddressStateOthers());
							}
							if(dataTo.getParentCityName()!=null && !dataTo.getParentCityName().isEmpty())
								data.setCityByParentAddressCityId(dataTo.getParentCityName());
							if(dataTo.getParentPh1()!=null && !dataTo.getParentPh1().isEmpty())
								data.setParentPh1(dataTo.getParentPh1());
							if(dataTo.getParentPh2()!=null && !dataTo.getParentPh2().isEmpty())
								data.setParentPh2(dataTo.getParentPh2());
							if(dataTo.getParentPh3()!=null && !dataTo.getParentPh3().isEmpty())
								data.setParentPh3(dataTo.getParentPh3());
							if(dataTo.getParentMob1()!=null && !dataTo.getParentMob1().isEmpty())
								data.setParentMob1(dataTo.getParentMob1());
							if(dataTo.getParentMob2()!=null && !dataTo.getParentMob2().isEmpty())
								data.setParentMob2(dataTo.getParentMob2());
							if(dataTo.getParentMob3()!=null && !dataTo.getParentMob3().isEmpty())
								data.setParentMob3(dataTo.getParentMob3());
							
							if(dataTo.getGuardianAddressLine1()!=null && !dataTo.getGuardianAddressLine1().isEmpty())
								data.setGuardianAddressLine1(dataTo.getGuardianAddressLine1());
							if(dataTo.getGuardianAddressLine2()!=null && !dataTo.getGuardianAddressLine2().isEmpty())
								data.setGuardianAddressLine2(dataTo.getGuardianAddressLine2());
							if(dataTo.getGuardianAddressLine3()!=null && !dataTo.getGuardianAddressLine3().isEmpty())
								data.setGuardianAddressLine3(dataTo.getGuardianAddressLine3());
							if(dataTo.getGuardianAddressZipCode()!=null && !dataTo.getGuardianAddressZipCode().isEmpty())
								data.setGuardianAddressZipCode(dataTo.getGuardianAddressZipCode());
							if(dataTo.getCountryByGuardianAddressCountryId()!=0){
								Country parentcountry= new Country();
								parentcountry.setId(dataTo.getCountryByGuardianAddressCountryId());
								data.setCountryByGuardianAddressCountryId(parentcountry);
								loginForm.setGuardianAddrCountyId(dataTo.getCountryByGuardianAddressCountryId());
							}else{
								data.setCountryByGuardianAddressCountryId(null);
							}
							if (dataTo.getStateByGuardianAddressStateId()!=null && !StringUtils.isEmpty(dataTo.getStateByGuardianAddressStateId()) && StringUtils.isNumeric(dataTo.getStateByGuardianAddressStateId())) {
								State parentState = new State();
								parentState.setId(Integer.parseInt(dataTo.getStateByGuardianAddressStateId()));
								data.setStateByGuardianAddressStateId(parentState);
							}else{
								data.setStateByGuardianAddressStateId(null);
								data.setGuardianAddressStateOthers(dataTo.getGuardianAddressStateOthers());
							}
							if(dataTo.getCityByGuardianAddressCityId()!=null && !dataTo.getCityByGuardianAddressCityId().isEmpty())
								data.setCityByGuardianAddressCityId(dataTo.getCityByGuardianAddressCityId());
							if(dataTo.getGuardianPh1()!=null && !dataTo.getGuardianPh1().isEmpty())
								data.setGuardianPh1(dataTo.getGuardianPh1());
							if(dataTo.getGuardianPh2()!=null && !dataTo.getGuardianPh2().isEmpty())
								data.setGuardianPh2(dataTo.getGuardianPh2());
							if(dataTo.getGuardianPh3()!=null && !dataTo.getGuardianPh3().isEmpty())
								data.setGuardianPh3(dataTo.getGuardianPh3());
							if(dataTo.getGuardianMob1()!=null && !dataTo.getGuardianMob1().isEmpty())
								data.setGuardianMob1(dataTo.getGuardianMob1());
							if(dataTo.getGuardianMob2()!=null && !dataTo.getGuardianMob2().isEmpty())
								data.setGuardianMob2(dataTo.getGuardianMob2());
							if(dataTo.getGuardianMob3()!=null && !dataTo.getGuardianMob3().isEmpty())
								data.setGuardianMob3(dataTo.getGuardianMob3());
							if(dataTo.getBrotherName()!=null && !dataTo.getBrotherName().isEmpty())
								data.setBrotherName(dataTo.getBrotherName());
							if(dataTo.getBrotherEducation()!=null && !dataTo.getBrotherEducation().isEmpty())
								data.setBrotherEducation(dataTo.getBrotherEducation());
							if(dataTo.getBrotherOccupation()!=null && !dataTo.getBrotherOccupation().isEmpty())
								data.setBrotherOccupation(dataTo.getBrotherOccupation());
							if(dataTo.getBrotherIncome()!=null && !dataTo.getBrotherIncome().isEmpty())
								data.setBrotherIncome(dataTo.getBrotherIncome());
							if(dataTo.getBrotherAge()!=null && !dataTo.getBrotherAge().isEmpty())
								data.setBrotherAge(dataTo.getBrotherAge());
							if(dataTo.getGuardianName()!=null && !dataTo.getGuardianName().isEmpty())
								data.setGuardianName(dataTo.getGuardianName());
							if(dataTo.getSisterName()!=null && !dataTo.getSisterName().isEmpty())
								data.setSisterName(dataTo.getSisterName());
							if(dataTo.getSisterEducation()!=null && !dataTo.getSisterEducation().isEmpty())
								data.setSisterEducation(dataTo.getSisterEducation());
							if(dataTo.getSisterOccupation()!=null && !dataTo.getSisterOccupation().isEmpty())
								data.setSisterOccupation(dataTo.getSisterOccupation());
							if(dataTo.getSisterIncome()!=null && !dataTo.getSisterIncome().isEmpty())
								data.setSisterIncome(dataTo.getSisterIncome());
							if(dataTo.getSisterAge()!=null && !dataTo.getSisterAge().isEmpty())
								data.setSisterAge(dataTo.getSisterAge());
							if(loginForm.getRecomendedBy()!=null && !loginForm.getRecomendedBy().isEmpty()){
								data.setRecommendedBy(loginForm.getRecomendedBy());
							}
							if(dataTo.getUniversityEmail()!=null && !dataTo.getUniversityEmail().isEmpty())
								data.setUniversityEmail(dataTo.getUniversityEmail());
							if(dataTo.getAadhaarCardNumber() != null && !dataTo.getAadhaarCardNumber().isEmpty())
								data.setAadharCardNumber(dataTo.getAadhaarCardNumber());
							
							if(dataTo.getMotherTongueId() != null && !dataTo.getMotherTongueId().isEmpty()) {
							
								MotherTongue motherTongue = new MotherTongue();
								motherTongue.setId(Integer.parseInt(dataTo.getMotherTongueId()));
								data.setMotherTongue(motherTongue);
							}							
							
							if(dataTo.getFatherPANNumber() != null && !dataTo.getFatherPANNumber().isEmpty())
								data.setFatherPANNumber(dataTo.getFatherPANNumber().toUpperCase());
							if(dataTo.getFatherAadhaarNumber() != null && !dataTo.getFatherAadhaarNumber().isEmpty())
								data.setFatherAadhaarNumber(dataTo.getFatherAadhaarNumber());
							if(dataTo.getIsmgquota() != null)
							data.setIsmgquota(dataTo.getIsmgquota());
							if(dataTo.getRecommentedBy() != null && !dataTo.getRecommentedBy().isEmpty()) {
								data.setRecommentedBy(dataTo.getRecommentedBy());
							}
							if(dataTo.getRecommendDeatails() != null && !dataTo.getRecommendDeatails().isEmpty()) {
								data.setRecommendedBy(dataTo.getRecommendDeatails());
							}
							if(dataTo.getRecommentedPersonDesignation() != null && !dataTo.getRecommentedPersonDesignation().isEmpty()) {
								data.setRecommentedPersonDesignation(dataTo.getRecommentedPersonDesignation());
							}
							if(dataTo.getRecommentedPersonMobile() != null && !dataTo.getRecommentedPersonMobile().isEmpty()) {
								data.setRecommentedPersonMobile(dataTo.getRecommentedPersonMobile());
							}
							if(dataTo.getPreferenceNoCAP() != null) {
								data.setPreferenceNoCAP(dataTo.getPreferenceNoCAP());
							}
							
							if(dataTo.getMbaEntranceExamId() != null && !dataTo.getMbaEntranceExamId().isEmpty()) {
								MBAEntranceExam entranceExam = new MBAEntranceExam();
								entranceExam.setId(Integer.parseInt(dataTo.getMbaEntranceExamId()));
								data.setMbaEntranceExam(entranceExam);
							}
							if(dataTo.getEntranceMarksSecured() != null && !dataTo.getEntranceMarksSecured().isEmpty()) {
								data.setEntranceMarksSecured(Double.parseDouble(dataTo.getEntranceMarksSecured()));
							}
							setDocUploads(student.getAdmAppln(),loginForm.getApplicantDetails(),loginForm,student,request);
			}
			
			boolean isUpdated=false;
			ILoginTransaction loginTransaction = new LoginTransactionImpl();
			if(data!=null && !data.toString().isEmpty()){
				isUpdated=loginTransaction.savePersonalData(data);
			}
			return isUpdated;
		}
		private void setDocUploads(AdmAppln appBO,
				AdmApplnTO applicantDetails, OnlineApplicationForm loginForm,
				Student student, HttpServletRequest request)throws Exception {
			
			ApplnDoc docBO= new ApplnDoc();
			if(applicantDetails!=null && applicantDetails.getEditDocuments()!=null){
				List<ApplnDocTO> docItr=applicantDetails.getEditDocuments();
					ApplnDocTO docTO = docItr.get(0);
					if(docTO.getId()!=0)
					docBO.setId(docTO.getId());
					docBO.setCreatedBy(docTO.getCreatedBy());
					docBO.setCreatedDate(docTO.getCreatedDate());
					docBO.setModifiedBy(appBO.getModifiedBy());
					docBO.setLastModifiedDate(new Date());
					docBO.setIsVerified(docTO.isVerified());
					if (docTO.getDocTypeId()!=0) {
						DocType doctype = new DocType();
						doctype.setId(docTO.getDocTypeId());
						docBO.setDocType(doctype);
					}else{
						docBO.setDocType(null);
					}
					docBO.setHardcopySubmitted(docTO.isHardSubmitted());
					docBO.setNotApplicable(docTO.isNotApplicable());
					if(!docBO.getHardcopySubmitted() && !docBO.getNotApplicable()){
						if(loginForm.getSubmitDate()!=null && !StringUtils.isEmpty(loginForm.getSubmitDate()))
						docBO.setSubmissionDate(CommonUtil.ConvertStringToSQLDate(loginForm.getSubmitDate()));
					}else{
						docBO.setSubmissionDate(null);
					}
					docBO.setIsPhoto(docTO.isPhoto());
					docBO.setIsMngQuotaForm(docTO.isMngQuotaForm());
					if(docTO.getEditDocument()!=null && docTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(docTO.getEditDocument().getFileName()) && !docTO.isMngQuotaForm()){
						
						FormFile editDoc=docTO.getEditDocument();
						docBO.setDocument(editDoc.getFileData());
						if(applicantDetails.getStudentId() != 0){
							try {
								FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+applicantDetails.getStudentId()+".jpg");
								fos.write(editDoc.getFileData());
								fos.close();
								
							} catch (Exception e) {
								
							}
							
						}
						docBO.setName(editDoc.getFileName());
						docBO.setContentType(editDoc.getContentType());
					}
					else if(docTO.getEditDocument()!=null && docTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(docTO.getEditDocument().getFileName()) && docTO.isMngQuotaForm()) {
						FormFile editDoc=docTO.getEditDocument();
						docBO.setDocument(editDoc.getFileData());
						docBO.setIsMngQuotaForm(docTO.isMngQuotaForm());
						if(applicantDetails.getStudentId() != 0){
							try {
								FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_MANAGEMENT_QUOTA_FORM_FOLDER_PATH+applicantDetails.getStudentId()+".jpg");
								fos.write(editDoc.getFileData());
								fos.close();
								
							} catch (Exception e) {
								
							}
							
						}
						docBO.setName(editDoc.getFileName());
						docBO.setContentType(editDoc.getContentType());
					}
					else{
						docBO.setDocument(docTO.getCurrDocument());
						docBO.setName(docTO.getName());
						docBO.setContentType(docTO.getContentType());
					}
					List<ApplnDocDetailsTO> list=docTO.getDocDetailsList();
					if(list!=null && !list.isEmpty()){
						Set<ApplnDocDetails> docDetailSet=new HashSet<ApplnDocDetails>();
						Iterator<ApplnDocDetailsTO> itr=list.iterator();
						while (itr.hasNext()) {
							ApplnDocDetailsTO to = (ApplnDocDetailsTO) itr.next();
							if(to.getChecked()!=null &&  to.getChecked().equals("on")){
								ApplnDocDetails bo=new ApplnDocDetails();
								if(to.getId()>0){
								bo.setId(to.getId());
								}
								bo.setSemesterNo(to.getSemNo());
								bo.setIsHardCopySubmited(true);
								bo.setApplnDoc(docBO);
								docDetailSet.add(bo);
							}
						}
						docBO.setApplnDocDetails(docDetailSet);
					}
					docBO.setSemNo(docTO.getSemisterNo());
					if(docTO.getSemType()!=null && !docTO.getSemType().isEmpty() && docTO.getSemisterNo()!=null && !docTO.getSemisterNo().isEmpty())
					docBO.setSemType(docTO.getSemType());
			}
			boolean isUpdated=false;
			ILoginTransaction loginTransaction = new LoginTransactionImpl();
			if(docBO!=null && !docBO.toString().isEmpty()){
				isUpdated = loginTransaction.saveStudentPhoto(docBO); 
			}
			if(isUpdated){
				try{
					if(student.getId() !=0 && request.getSession().getAttribute("PhotoBytes") != null){
						FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_PATH+student.getId()+".jpg");
						fos.write((byte[])request.getSession().getAttribute("PhotoBytes"));
						fos.close();
					}
				}catch (Exception f) {
					f.printStackTrace();
				}
				}
		}
		public boolean uploadPhoto(OnlineApplicationForm onlineApplicationForm,Student student)throws Exception {
			IConsolidatedSubjectStreamTransaction tx = ConsolidatedSubjectStreamTransactionImpl.getInstance();
			ILoginTransaction transaction = new LoginTransactionImpl();
			ApplnDoc doc=tx.getStudentPhoto(student.getAdmAppln().getId());
			if(doc!=null && !doc.toString().isEmpty()){
				FormFile editDoc=onlineApplicationForm.getStudentPhoto();
				doc.setDocument(editDoc.getFileData());
				doc.setModifiedBy(onlineApplicationForm.getUserId());
				doc.setLastModifiedDate(new Date());
				doc.setName(editDoc.getFileName());
				doc.setContentType(editDoc.getContentType());
			}else if(doc==null || doc.toString().isEmpty()){
				doc = new  ApplnDoc();
				AdmAppln appln= new AdmAppln();
				FormFile editDoc=onlineApplicationForm.getStudentPhoto();
				doc.setDocument(editDoc.getFileData());
				doc.setCreatedBy(onlineApplicationForm.getUserId());
				doc.setCreatedDate(new Date());
				doc.setIsPhoto(true);
				doc.setIsMngQuotaForm(false);
				appln.setId(student.getAdmAppln().getId());
				doc.setAdmAppln(appln);
				doc.setName(editDoc.getFileName());
				doc.setContentType(editDoc.getContentType());
			}
			return transaction.saveStudentPhoto(doc);
		}
	
	public void updateResponseByPass(OnlineApplicationForm admForm) throws Exception{
		CandidatePGIDetails bo=OnlineApplicationHelper.getInstance().getPGIBOForBypass(admForm);
		IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
		transaction.updateReceivedStatus(bo, admForm);
	}
	public List<coursesToPrint> getCourses(OnlineApplicationForm admForm) {
		int ptype=Integer.parseInt(admForm.getProgramTypeId());
		List<Object[]> courseList=null;
		List<coursesToPrint> crsList=null;
		OnlineApplicationHelper helper=OnlineApplicationHelper.getInstance();
		IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
		try {
			courseList=transaction.getAllCourses(ptype);
			crsList=helper.convertcourseBo(courseList,admForm);
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		return crsList;
		
	}
	public String getCourses(BaseActionForm bForm) {
		List<Object[]> courseList=null;
		Boolean result=false;
		String resultMsg=null;
		OnlineApplicationHelper helper=OnlineApplicationHelper.getInstance();
		IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
		try {
			courseList=transaction.getAllCourses();
			result=helper.checkAided(courseList,bForm.getCourseId());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
		if (result) {
			resultMsg="You have selected an Aided Programme";
		}
		else {
			resultMsg="You have selected an Unaided Programme";
		}
		return resultMsg;
		
	}
	public String getIsCommunityquota(String admApplnId) throws ApplicationException {
		IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
		return transaction.getIsCommunityQuota(admApplnId);
	}
	public String getIsManagementquota(String admApplnId) throws ApplicationException {
		IOnlineApplicationTxn transaction=OnlineApplicationImpl.getInstance();
		return transaction.getIsMngQuota(admApplnId);
	}
}
