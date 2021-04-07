package com.kp.cms.handlers.admission;

/**
 * 
 * 
 * HANDLER CLASS FOR ADMISSIONFORM ACTION CLASS
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.billdesk.pgidsk.PGIUtil;
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.actions.exam.NewStudentMarksCorrectionAction;
import com.kp.cms.bo.admin.Address;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.EligibleSubjects;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.DetailedSubjectsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.helpers.sap.ExamRegistrationDetailsHelper;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.EligibilityCriteriaCheckTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;
import com.kp.cms.transactions.admin.ITemplatePassword;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IUploadInteviewSelectionTxn;
import com.kp.cms.transactionsimpl.admin.TemplateImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.UploadInterviewSelectionTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;
import org.apache.commons.codec.binary.Base64;
import java.security.*;

public class AdmissionFormHandler {
	private static final Log log = LogFactory.getLog(AdmissionFormHandler.class);
	
	public static volatile AdmissionFormHandler self=null;
	public static AdmissionFormHandler getInstance(){
		if(self==null){
			self= new AdmissionFormHandler();
		}
		return self;
	}
	private AdmissionFormHandler(){
		
	}
	/**
	 * add ApplnDoc BOs to session
	 * @param admForm
	 * @param session
	 * @throws Exception
	 */
	public void persistAdmissionFormAttachments(AdmissionFormForm admForm,HttpSession session) throws Exception {
		log.info("Enter persistAdmissionFormAttachments ...");
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<ResidentCategory> residentbos=txn.getResidentTypes();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
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
	public boolean saveStudentPersonaldataToSession(PersonalDataTO studentpersonaldata, AdmissionFormForm admForm,HttpSession session) throws Exception {
		log.info("Enter saveStudentPersonaldataToSession ...");
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
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
		


		PreferenceTO firstpref= admForm.getFirstPref();
		PreferenceTO secpref= admForm.getSecondPref();
		PreferenceTO thirdpref= admForm.getThirdPref();
		
		List<CandidatePreference> preferenceBos= helper.convertPreferenceTOToBO(firstpref,secpref,thirdpref,session);
		//work experience set
		Set<ApplicantWorkExperience> workExperiences=helper.convertExperienceTostoBOs(admForm);
		// maintain session data
		if(session.getAttribute(CMSConstants.APPLICATION_DATA)!=null)
		{
			log.info("application no set session application data...");
			AdmAppln applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
			applicationdata.setApplicantWorkExperiences(workExperiences);
//			// assigned first preference course as selected course by default
//			if(firstpref!=null && firstpref.getCourseId()!=null && !StringUtils.isEmpty(firstpref.getCourseId()) && StringUtils.isNumeric(firstpref.getCourseId()) )
//			{
//				Course crs= new Course();
//				crs.setId(Integer.parseInt(firstpref.getCourseId()));
//				applicationdata.setCourseBySelectedCourseId(crs);
//			}
			
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
		if(preferenceBos!=null && !preferenceBos.isEmpty()){
		session.setAttribute(CMSConstants.STUDENT_PREFERENCES, preferenceBos);
		}
		log.info("preferences set to session ...");
		return true;
	}
	/**
	 * application details add to session
	 * @param admForm
	 * @param session
	 * @return
	 */
	public boolean saveApplicationDetailsToSession(AdmissionFormForm admForm,HttpSession session) {
		log.info("Enter saveApplicationDetailsToSession ...");
		boolean result=false;
		AdmAppln appln= new AdmAppln();
		//if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("SBI")){
		//raghu
		if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("CHALLAN")){	
		if(admForm.getApplicationAmount()!=null && !StringUtils.isEmpty(admForm.getApplicationAmount())&& CommonUtil.isValidDecimal(admForm.getApplicationAmount())){
				appln.setAmount(new BigDecimal(admForm.getApplicationAmount()));
			}else{
					appln.setAmount(new BigDecimal("0.0"));
			}
			appln.setChallanRefNo(admForm.getChallanNo());
			appln.setJournalNo(admForm.getJournalNo());
			appln.setBankBranch(admForm.getBankBranch());
			appln.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getApplicationDate()));
		}
		else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() && admForm.getSelectedFeePayment().equalsIgnoreCase("DD")){
			if(admForm.getEquivalentApplnFeeINR()!=null && !admForm.getEquivalentApplnFeeINR().isEmpty() && CommonUtil.isValidDecimal(admForm.getEquivalentApplnFeeINR())){
				appln.setAmount(new BigDecimal(admForm.getEquivalentApplnFeeINR()));
			}
			else if(admForm.getDdAmount()!=null && !StringUtils.isEmpty(admForm.getDdAmount())&& CommonUtil.isValidDecimal(admForm.getDdAmount())){
				appln.setAmount(new BigDecimal(admForm.getDdAmount()));
				if(admForm.getInternationalApplnFeeCurrencyId()!=null && !admForm.getInternationalApplnFeeCurrencyId().isEmpty()){
					appln.setInternationalCurrencyId(admForm.getInternationalApplnFeeCurrencyId());
				}
			}else{
					appln.setAmount(new BigDecimal("0.0"));
			}
			appln.setJournalNo(admForm.getDdNo());
			appln.setBankBranch(admForm.getDdBankCode());
			appln.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getDdDate()));
			appln.setDdDrawnOn(admForm.getDdDrawnOn());
			appln.setDdIssuingBank(admForm.getDdIssuingBank());
		}
		else if(admForm.getSelectedFeePayment()!=null && !admForm.getSelectedFeePayment().isEmpty() 
				&& admForm.getSelectedFeePayment().equalsIgnoreCase("OnlinePayment")){
			if(admForm.getTxnAmt()!=null && !StringUtils.isEmpty(admForm.getTxnAmt())&& CommonUtil.isValidDecimal(admForm.getTxnAmt())){
				appln.setAmount(new BigDecimal(admForm.getTxnAmt()));
			}else{
					appln.setAmount(new BigDecimal("0.0"));
			}
			appln.setJournalNo(admForm.getTxnRefNo());
			appln.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getTxnDate()));
		}
		appln.setCreatedBy(admForm.getUserId());
		appln.setCreatedDate(new Date());
		appln.setIsCancelled(false);
		appln.setIsFreeShip(false);
		Course crs1= new Course();
		crs1.setId(Integer.parseInt(admForm.getCourseId()));
		ProgramType progtp= new ProgramType();
		progtp.setId(Integer.parseInt(admForm.getProgramTypeId()));
		Program prog= new Program();
		prog.setId(Integer.parseInt(admForm.getProgramId()));
		prog.setProgramType(progtp);
		crs1.setProgram(prog);
		crs1.setIsWorkExperienceRequired(admForm.isWorkExpNeeded());
		appln.setCourse(crs1);
		appln.setCourseBySelectedCourseId(crs1);
		Calendar cal= Calendar.getInstance();
		cal.setTime(new Date());
		if(admForm.getApplicationYear()!= null && !admForm.getApplicationYear().trim().isEmpty()){
			appln.setAppliedYear(Integer.parseInt(admForm.getApplicationYear()));
		}
		else{
			appln.setAppliedYear(Integer.valueOf(cal.get(cal.YEAR)));
		}
		
		
		appln.setIsSelected(false);
		appln.setIsBypassed(false);
		if(session.getAttribute(CMSConstants.STUDENT_PREREQUISITES)!=null){
			Set<CandidatePrerequisiteMarks> markSet=(HashSet<CandidatePrerequisiteMarks>)session.getAttribute(CMSConstants.STUDENT_PREREQUISITES);
			if(markSet!=null && !markSet.isEmpty()){
				appln.setCandidatePrerequisiteMarks(markSet);
			}
		}
		session.setAttribute(CMSConstants.APPLICATION_DATA, appln);
		result=true;
		log.info("application details set to session ...");
		return result;
	}
	
	/**
	 * prepare EdnQualificationTos for application form
	 * @param admForm
	 * @return
	 * @throws Exception
	 */
	public List<EdnQualificationTO> getEdnQualifications(AdmissionFormForm admForm) throws Exception {
		log.info("Enter getEdnQualifications ...");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<DocChecklist> exambos= txn.getExamtypes(Integer.parseInt(admForm.getCourseId()),Integer.parseInt(admForm.getApplicationYear()));
		
		
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		log.info("Exit getEdnQualifications ...");
		return helper.prepareQualificationsFromExamBos(exambos);
	}
	

	/**
	 * @param admForm
	 * @param session
	 */
	public void saveEducationDetailsToSession(AdmissionFormForm admForm,
			HttpSession session,boolean isPresidance) {
		log.info("Enter saveEducationDetailsToSession ...");
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
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
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Currency> currancybos=txn.getCurrencies();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Income> incomebos=txn.getIncomes();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
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
	public boolean saveCompleteApplication(HttpSession session,
			AdmissionFormForm admForm) throws Exception {
		log.info("Entered Save complete application in handler ...");
		PersonalData personaldata=updateParentdata(session,admForm);
		AdmAppln applicationData=null;
		Address permAddr=null;
		Address commAddr=null;
		List<CandidatePreference> preference=null;
		List<EdnQualification> qualifications=null;
		List<ApplnDoc> uploads=null;
		
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
	public AdmApplnTO getCompleteApplication(HttpSession session,
			AdmissionFormForm admForm) throws Exception {
		log.info("Entered Save complete application in handler ...");
		PersonalData personaldata=updateParentdata(session,admForm);
		AdmAppln applicationData=null;
		Address permAddr=null;
		Address commAddr=null;
		List<CandidatePreference> preference=null;
		List<EdnQualification> qualifications=null;
		List<ApplnDoc> uploads=null;
		
		//IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
		
//		if(applicationData!=null)
//		{
//
//			
//			if(!checkApplicationNoUniqueForYear(applicationData))
//			{
//				return false;
//			}
//		
//			session.setAttribute(CMSConstants.APPLICATION_DATA, applicationData);
//		}
		
		
		Student newStudent=createStudentBO(applicationData, personaldata,preference, qualifications, uploads);
		AdmApplnTO applicantdetails=AdmissionFormHelper.getInstance().copyPropertiesValue(newStudent.getAdmAppln());
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
	private PersonalData updateParentdata(HttpSession session,AdmissionFormForm admForm) {
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
	 * creates list of preferences for application
	 * @param admForm
	 * @throws Exception
	 */
	public void populatePreferenceList(AdmissionFormForm admForm) throws Exception {
		log.info("Enter populatePreferenceList ...");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Course> prefCourses=txn.getCourseForPreference(admForm.getCourseId());
		List<ProgramTO> prefPrograms= new ArrayList<ProgramTO>();
		List<ProgramTypeTO> prefProgramtypes= new ArrayList<ProgramTypeTO>();
		List<CourseTO> prefCoursetos= new ArrayList<CourseTO>();
		if(!prefCourses.isEmpty()){
			Iterator<Course> prefitr=prefCourses.iterator();
			while (prefitr.hasNext()) {
				Course prefCrs = (Course) prefitr.next();
				if (prefCrs.getIsActive()) {
					
					Program prefProg = prefCrs.getProgram();
					ProgramType prefProgtype = prefCrs.getProgram()
							.getProgramType();
					CourseTO toCrs = new CourseTO();
					toCrs.setId(prefCrs.getId());
					toCrs.setName(prefCrs.getName());
					prefCoursetos.add(toCrs);
					ProgramTO toProg = new ProgramTO();
					toProg.setId(prefProg.getId());
					toProg.setName(prefProg.getName());
					prefPrograms.add(toProg);
					ProgramTypeTO toProgtype = new ProgramTypeTO();
					toProgtype.setProgramTypeId(prefProgtype.getId());
					toProgtype.setProgramTypeName(prefProgtype.getName());
					prefProgramtypes.add(toProgtype);
				}
			}
			
		} 
//		else {
//			CourseTO toCrs = new CourseTO();
//			toCrs.setId(Integer.parseInt(admForm.getCourseId()));
//			toCrs.setName(admForm.getCourseName());
//			prefCoursetos.add(toCrs);
//		}
		
		List<CourseTO> uniqueCourse=removeDuplicateCourses(prefCoursetos);
		List<ProgramTO> uniqueprograms=removeDuplicatePrograms(prefPrograms);
		List<ProgramTypeTO> uniqueprogramTypes=removeDuplicateProgramTypes(prefProgramtypes);
		admForm.setPrefcourses(uniqueCourse);
		admForm.setPrefprograms(uniqueprograms);
		admForm.setPrefProgramtypes(uniqueprogramTypes);
		log.info("Exit populatePreferenceList ...");
	}
	
	/**
	 * SETS DEFUALT PREFERENCE AS COURSE SELECTED
	 * @param admForm
	 */
	public void setDefaultPreferences(AdmissionFormForm admForm) {
		PreferenceTO preferenceTO = new PreferenceTO();
		preferenceTO.setCourseId(admForm.getCourseId());
		preferenceTO.setFirstprefCourseName(admForm.getCourseName());
		admForm.setFirstPref(preferenceTO);
	}
	
	/**
	 * POPULATES PREFERENCE TOS FOR SELECTION
	 * @param prefTO
	 * @param courseID
	 * @throws Exception
	 */
	public void populatePreferenceTO(CandidatePreferenceTO prefTO,CourseTO courseTO) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Course> prefCourses=txn.getCourseForPreference(String.valueOf(courseTO.getId()));
		List<ProgramTO> prefPrograms= new ArrayList<ProgramTO>();
		List<ProgramTypeTO> prefProgramtypes= new ArrayList<ProgramTypeTO>();
		List<CourseTO> prefCoursetos= new ArrayList<CourseTO>();
		if(!prefCourses.isEmpty()){
			Iterator<Course> prefitr=prefCourses.iterator();
			while (prefitr.hasNext()) {
				Course prefCrs = (Course) prefitr.next();
				if (prefCrs.getIsActive()) {
					
					Program prefProg = prefCrs.getProgram();
					ProgramType prefProgtype = prefCrs.getProgram()
							.getProgramType();
					CourseTO toCrs = new CourseTO();
					toCrs.setId(prefCrs.getId());
					toCrs.setName(prefCrs.getName());
					prefCoursetos.add(toCrs);
					ProgramTO toProg = new ProgramTO();
					toProg.setId(prefProg.getId());
					toProg.setName(prefProg.getName());
					prefPrograms.add(toProg);
					ProgramTypeTO toProgtype = new ProgramTypeTO();
					toProgtype.setProgramTypeId(prefProgtype.getId());
					toProgtype.setProgramTypeName(prefProgtype.getName());
					prefProgramtypes.add(toProgtype);
				}
			}
			
		} 
//		else {
//			CourseTO toCrs = new CourseTO();
//			toCrs.setId(courseTO.getId());
//			toCrs.setName(courseTO.getName());
//			prefCoursetos.add(toCrs);
//		}
		List<CourseTO> uniqueCourse=removeDuplicateCourses(prefCoursetos);
		List<ProgramTO> uniqueprograms=removeDuplicatePrograms(prefPrograms);
		List<ProgramTypeTO> uniqueprogramTypes=removeDuplicateProgramTypes(prefProgramtypes);
		prefTO.setPrefcourses(uniqueCourse);
		prefTO.setPrefprograms(uniqueprograms);
		prefTO.setPrefProgramtypes(uniqueprogramTypes);
		
	}
	
	/**
	 * @param prefProgramtypes
	 * @return
	 */
	private List<ProgramTypeTO> removeDuplicateProgramTypes(
			List<ProgramTypeTO> prefProgramtypes) {
		Map<Integer, ProgramTypeTO> programtypeMap = new HashMap<Integer, ProgramTypeTO>();
		Iterator<ProgramTypeTO> itr = prefProgramtypes.iterator();
		ProgramTypeTO progTo;
		while(itr.hasNext()) {
			progTo = (ProgramTypeTO)itr.next();
			if(!programtypeMap.containsKey(Integer.valueOf((progTo.getProgramTypeId())))) {
				programtypeMap.put(Integer.valueOf((progTo.getProgramTypeId())), progTo);
			}
		}
		
		return new ArrayList<ProgramTypeTO>(programtypeMap.values());
	}
	/**
	 * @param prefPrograms
	 * @return
	 */
	private List<ProgramTO> removeDuplicatePrograms(List<ProgramTO> prefPrograms) {
		Map<Integer, ProgramTO> programToMap = new HashMap<Integer, ProgramTO>();
		Iterator<ProgramTO> itr = prefPrograms.iterator();
		ProgramTO progTo;
		while(itr.hasNext()) {
			progTo = (ProgramTO)itr.next();
			if(!programToMap.containsKey(Integer.valueOf((progTo.getId())))) {
				programToMap.put(Integer.valueOf((progTo.getId())), progTo);
			}
		}
		
		return new ArrayList<ProgramTO>(programToMap.values());
	}
	/**
	 * @param prefCoursetos
	 * @return
	 */
	private List<CourseTO> removeDuplicateCourses(List<CourseTO> prefCoursetos) {
		Map<Integer, CourseTO> courseToMap = new HashMap<Integer, CourseTO>();
		Iterator<CourseTO> itr = prefCoursetos.iterator();
		CourseTO courseTo;
		while(itr.hasNext()) {
			courseTo = (CourseTO)itr.next();
			if(!courseToMap.containsKey(Integer.valueOf((courseTo.getId())))) {
				courseToMap.put(Integer.valueOf((courseTo.getId())), courseTo);
			}
		}
		
		return new ArrayList<CourseTO>(courseToMap.values());
	}
	/**
	 * Send mail to student after successful submit of application
	 * @param admForm
	 * @return
	 */
	public boolean sendMailToStudent(AdmissionFormForm admForm) throws Exception {
			boolean sent=false;
			List<GroupTemplate> list=null;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			if(admForm.isOnlineApply()){
			 list= temphandle.getDuplicateCheckList(Integer.parseInt(admForm.getCourseId()),CMSConstants.APPLICANT_MAIL_TEMPLATE);
			}
			else{
				 list= temphandle.getDuplicateCheckList(0,CMSConstants.APPLICANT_MAIL_TEMPLATE_OFFLINE);
			}
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant

						String dob = "";
						String name = "";
						String applnno = "";
					
						if(admForm.isOnlineApply() && admForm.getEmailId()!=null && !StringUtils.isEmpty(admForm.getEmailId().trim())){
							dob=admForm.getDateOfBirth();
							name=admForm.getFirstName();
							applnno=admForm.getApplicationNumber();
							String program = "";
							String appliedYear = "";
							if(admForm.getProgramName() !=null) {
								program = admForm.getProgramName();
							}
							
							if(admForm.getApplicationYear()!=null && !admForm.getApplicationYear().isEmpty()) {
								appliedYear = admForm.getApplicationYear() + "-" + (Integer.valueOf(admForm.getApplicationYear())+1);
							}
							
							//replace dyna data
							String subject= "Application for "+program +" "+ appliedYear+ " submitted successfully";
							
							String message =desc;
							message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
							message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
							message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
//							send mail
							sendMail(admForm.getEmailId(),subject,message);
							//print letter
							HtmlPrinter.printHtml(message);

						}
						// added newly to send mail to offline applied students -Smitha
						else if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null 
								&& !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail())){
							dob=admForm.getApplicantDetails().getPersonalData().getDob();
							name=admForm.getApplicantDetails().getPersonalData().getFirstName();
							applnno=String.valueOf(admForm.getApplicantDetails().getApplnNo());
							String program = "";
							String appliedYear = "";
							if(admForm.getApplicantDetails().getSelectedCourse().getProgramId()!=0) {
								program =NewSecuredMarksEntryHandler.getInstance().getPropertyValue(admForm.getApplicantDetails().getSelectedCourse().getProgramId(),"Program",true,"name");
							}
							
							if(admForm.getApplicationYear()!=null && !admForm.getApplicationYear().isEmpty()) {
								appliedYear = admForm.getApplicationYear() + "-" + (Integer.valueOf(admForm.getApplicationYear())+1);
							}
							
							//replace dyna data
							String subject= "Application for "+program +" "+ appliedYear+ " submitted successfully";
							
							String message =desc;
							message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
							message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
							message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
//							send mail
							sendMail(admForm.getApplicantDetails().getPersonalData().getEmail(),subject,message);
							//print letter
							HtmlPrinter.printHtml(message);

						}
					
			
			} 
			return sent;
	}
	
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 * @throws ApplicationException 
	 */
	public boolean sendMail(String mailID,String sub,String message) throws ApplicationException {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
//				String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
				final String adminmail=CMSConstants.MAIL_USERID;
				final String password = CMSConstants.MAIL_PASSWORD;
				final String port = CMSConstants.MAIL_PORT;
				final String host = CMSConstants.MAIL_HOST;
				Properties props = new Properties();
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.socketFactory.port", port);
				props.put("mail.smtp.socketFactory.class",
						"javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", port);
		 
				Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(adminmail,password);
						}
					});
		 
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(adminmail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
				//uses JMS 
				//sent=CommonUtil.postMail(mailto);
				try
				{
					 MimeMessage message1 = new MimeMessage(session);
						
						// Set from & to addresses
						InternetAddress from = new InternetAddress(adminmail,
								"ST. PHILOMENA's COLLEGE");
						
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
							return new PasswordAuthentication(adminmail,
									password);
						}
					});
					Transport transport = carrierSession.getTransport("smtps");
					transport.connect("smtp.gmail.com",adminmail,
							password);
					transport.sendMessage(message1, 
			        		message1.getRecipients(Message.RecipientType.TO));  //set
			        transport.close();
		 
					System.out.println("==========Done========");
				}
				catch (Exception e) {
					System.out.println(e.getMessage());
					throw new ApplicationException(e);
					// TODO: handle exception
				}
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}





	
	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail1(String mailID,String sub,String message) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
//				String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
				String adminmail=CMSConstants.MAIL_USERID;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(adminmail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
				//uses JMS 
				//sent=CommonUtil.postMail(mailto);
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}
	/**
	 * send mail to admin to reconfigure application number
	 * @param admForm
	 * @param appliedyear
	 * @return
	 */
	public boolean sendMailToAdmin(AdmissionFormForm admForm, int appliedyear) {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
//			String adminmail=prop.getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
			String adminmail=CMSConstants.MAIL_USERID;
			String toAddress=adminmail;
			String subject="Please configure online application No. range";
			String msg="Hi!,<br>" +
					"Please configure online application No. range for" +
					admForm.getCourseName()+" for academic year " +appliedyear+".<br>Thanks,<br>Christ University Admin.<br>";
			try {
				sent=sendMail(toAddress, subject, msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return sent;
}
	/**
	 * FETCHES ALL NATIONALITIES
	 * @return
	 * @throws Exception
	 */
	public List<NationalityTO> getNationalities()throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Nationality> nationBOs=txn.getNationalities();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		List<NationalityTO> nationTOs=helper.convertNationalityBOtoTO(nationBOs);
		return nationTOs;
	}
	/**
	 * generates challan NO.
	 * @return
	 * @throws Exception
	 */
	public String getNewChallanNo() throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		String curRefNo=txn.getCurrentChallanNo();
		String latestRefNo="";
		if(curRefNo!=null && StringUtils.isNumeric(curRefNo))
		{
			int curNO=Integer.parseInt(curRefNo);
			curNO++;
			latestRefNo=String.valueOf(curNO);
		}
		boolean updatedLatest=txn.updateLatestChallanNo(latestRefNo);
		if(updatedLatest){
		return latestRefNo;
		}else{
			return null;
		}
	}
	/**
	 * prerequisite preparation
	 * @param courseID
	 * @return
	 * @throws Exception
	 */
	public List<CoursePrerequisiteTO> getCoursePrerequisites(int courseID) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<CoursePrerequisite> requisiteBOs=txn.getCoursePrerequisites(courseID);
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		List<CoursePrerequisiteTO> requisiteTOs=helper.convertRequisiteBOstoTOS(requisiteBOs);
		return requisiteTOs;
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		return txn.getGeneratedOnlineAppNo(Integer.parseInt(courseId),year,isOnline);
		
	}
	/**
	 * checks work exp needed for course or not
	 * @param admForm
	 * @throws Exception
	 */
	public void setWorkExpNeeded(AdmissionFormForm admForm) throws Exception{
		String courseID=admForm.getCourseId();
		CourseHandler handler= CourseHandler.getInstance();
		List<CourseTO> coursetos=handler.getCourse(Integer.parseInt(courseID));
		if(coursetos!=null && !coursetos.isEmpty()){
			CourseTO crsto=coursetos.get(0);
			if("Yes".equalsIgnoreCase(crsto.getIsWorkExperienceRequired()))
			{
				admForm.setWorkExpNeeded(true);
			}else{
				admForm.setWorkExpNeeded(false);
			}
		}
	}
	
	//edit section
	
	
	/**
	 * CHECKS ELIGIBITY CRITERIA
	 * @param applicantDetail
	 * @return
	 * @throws Exception
	 */
	public boolean checkEligibility(AdmApplnTO applicantDetail) throws Exception
	{
		boolean eligible=checkEligibilityCriteria(applicantDetail.getEdnQualificationList(),applicantDetail.getSelectedCourse().getId(),applicantDetail.getAppliedYear());
		return eligible;
	}
	
	/**
	 * CHECKS If Admitted or not
	 * @param applicantDetail
	 * @return
	 * @throws Exception
	 */
	public boolean checkAdmitted(AdmApplnTO applicantDetail) throws Exception
	{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean admitted=txn.checkAdmittedOrNot(applicantDetail.getApplnNo(),applicantDetail.getSelectedCourse().getId(),applicantDetail.getAppliedYear());
		return admitted;
	}
	/**
	 * updated complete application details
	 * @param applicantDetail
	 * @param admForm 
	 * @return
	 */
	public boolean updateCompleteApplication(AdmApplnTO applicantDetail, AdmissionFormForm admForm,boolean isPresidance) throws Exception{
		
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		AdmAppln admBO=helper.getApplicantBO(applicantDetail,admForm,isPresidance);
		
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean updated=txn.updateCompleteApplication(admBO,admForm.isAdmissionEdit());
		
		
		
		
		return updated;
	}
	
	/**
	 * updated complete application details
	 * @param applicantDetail
	 * @param admForm 
	 * @param session 
	 * @return
	 */
	public boolean saveCompleteApplication(AdmApplnTO applicantDetail, AdmissionFormForm admForm, HttpSession session,boolean isPresidance) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		AdmAppln admBO=helper.getApplicantBO(applicantDetail,admForm,isPresidance);
		if(admBO.getInterScheduleSelection()!=null && admBO.getInterScheduleSelection().getId()>0){
			
			admBO.setAdmStatus(null);
		}else
		{
			admBO.setAdmStatus(CMSConstants.ADM_STATUS_OFFLINE_APPLN);
		}
		admBO.setCreatedBy(admForm.getUserId());
		admBO.setModifiedBy(admForm.getUserId());
		//added by vishnu
		admBO.setAdmissionNumber(admForm.getAdmissionNumber());
		PersonalData pBo=admBO.getPersonalData();
		pBo.setCreatedBy(admForm.getUserId());
		pBo.setCreatedDate(new Date());
		pBo.setModifiedBy(admForm.getUserId());
		Student newStudent= new Student();
		newStudent.setCreatedBy(admBO.getCreatedBy());
		newStudent.setModifiedBy(admForm.getUserId());
		newStudent.setCreatedDate(new Date());
		newStudent.setLastModifiedDate(new Date());
		admBO.setIsFinalMeritApproved(false);
		//added for challan verification
		admBO.setIsChallanVerified(false);
		/*if(admForm.getInterviewSelectionDate()!= null){
			InterviewSelectionSchedule iss=new InterviewSelectionSchedule();
			iss.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
			admBO.setInterScheduleSelection(iss);
		}
		if(admForm.getInterviewVenue()!= null){
			ExamCenter examCenter = new ExamCenter();
			int examCenterId= txn.getExamCenterFromInterviewVenue(admForm.getInterviewVenue());
			examCenter.setId(examCenterId);
			admBO.setExamCenter(examCenter);
		}*/
			//addition for challan verification completed
		// no edit for education info
//		List<EdnQualification> qualifications =null;
//		if(session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS)!=null)
//			qualifications=(List<EdnQualification>)session.getAttribute(CMSConstants.STUDENT_EDUCATION_DETAILS);
//		Set<EdnQualification> qualSet= new HashSet<EdnQualification>();
//		qualSet.addAll(qualifications);
//		admBO.getPersonalData().setEdnQualifications(qualSet);
		newStudent.setAdmAppln(admBO);
		
		if(!checkApplicationNoUniqueForYear(admBO))
		{
			return false;
		}
		//IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean updated=txn.persistCompleteApplicationData(newStudent, admForm);
		
		
		
		
		return updated;
	}
	/**
	 * CHECKS ELIGIBILITY CRITERIA FOR ADMISSION
	 * @param ednList
	 * @param year 
	 * @param id
	 * @return
	 */
	private boolean checkEligibilityCriteria(
			List<EdnQualificationTO> ednList, int courseId, Integer year) throws Exception {
		boolean eligible=false;
		
		//semester wise percentage
	
		//main percenatges to be compared
		double withoutlanguagePerc=0.0;
		double percwithLangunage=0.0;
		List<EligibilityCriteriaCheckTO> criterias=new ArrayList<EligibilityCriteriaCheckTO>();
		if(ednList!=null && !ednList.isEmpty()){
			Iterator<EdnQualificationTO> iterator = ednList.iterator();
			while (iterator.hasNext()) {
				EdnQualificationTO ednTO = iterator.next();
				
				//check semester wise, then calculate percenatge
				if(!ednTO.isConsolidated() && ednTO.isSemesterWise()){

					
					Set<ApplicantMarkDetailsTO> semesterlist=ednTO.getSemesterList();
						if (semesterlist!=null && !semesterlist.isEmpty()) {
							Iterator<ApplicantMarkDetailsTO> semitr=semesterlist.iterator();
							double overallMark=0.0;
							double overallObtain=0.0;
							double overallanguagewiseMark=0.0;
							double overallanguagewiseObtain=0.0;

							while (semitr.hasNext()) {
								ApplicantMarkDetailsTO detailMarkto = (ApplicantMarkDetailsTO) semitr.next();
								int totalMark=0;
								int obtainMark=0;
								int totallanguagewiseMark=0;
								int obtainlanguagewiseMark=0;
								if(detailMarkto.getMaxMarks()!=null && !StringUtils.isEmpty(detailMarkto.getMaxMarks()) && StringUtils.isNumeric(detailMarkto.getMaxMarks())){
									totalMark=Integer.parseInt(detailMarkto.getMaxMarks());
								}
								if(detailMarkto.getMarksObtained()!=null && !StringUtils.isEmpty(detailMarkto.getMarksObtained()) && StringUtils.isNumeric(detailMarkto.getMarksObtained())){
									obtainMark=Integer.parseInt(detailMarkto.getMarksObtained());
								}
								if(detailMarkto.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(detailMarkto.getMaxMarks_languagewise()) && StringUtils.isNumeric(detailMarkto.getMaxMarks_languagewise())){
									totallanguagewiseMark=Integer.parseInt(detailMarkto.getMaxMarks_languagewise());
								}
								if(detailMarkto.getMarksObtained_languagewise()!=null && !StringUtils.isEmpty(detailMarkto.getMarksObtained_languagewise()) && StringUtils.isNumeric(detailMarkto.getMarksObtained_languagewise())){
									obtainlanguagewiseMark=Integer.parseInt(detailMarkto.getMarksObtained_languagewise());
								}

								overallMark=overallMark+totalMark;
								overallObtain=overallObtain+obtainMark;
								
								overallanguagewiseMark = overallanguagewiseMark + totallanguagewiseMark;
								overallanguagewiseObtain = overallanguagewiseObtain + obtainlanguagewiseMark;
							}
							if(overallObtain!=0.0 && overallMark!=0.0)
							withoutlanguagePerc=(overallObtain/overallMark)*100;
							if(overallanguagewiseObtain!=0.0 && overallanguagewiseMark!=0.0)
							percwithLangunage=(overallanguagewiseObtain/overallanguagewiseMark)*100;
						}
				}else if(!ednTO.isConsolidated() && !ednTO.isSemesterWise()){
					CandidateMarkTO detailMarkto=ednTO.getDetailmark();
					if (detailMarkto!=null ) {
						double totalmark=0.0;
						double totalobtained=0.0;
						double temptotal=0.0;
						double tempobtain=0.0;
						double tempperc=0.0;
						
						if((detailMarkto.getDetailedSubjects1()!= null) && (detailMarkto.getDetailedSubjects1().getId() != -1) && (detailMarkto.getDetailedSubjects1().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects1().getId());
							if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject1TotalMarks());
							if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject1ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						
						
						if((detailMarkto.getDetailedSubjects2()!= null) && (detailMarkto.getDetailedSubjects2().getId() != -1) && (detailMarkto.getDetailedSubjects2().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects2().getId());
							if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject2TotalMarks());
							if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject2ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects3()!= null) && (detailMarkto.getDetailedSubjects3().getId() != -1) && (detailMarkto.getDetailedSubjects3().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects3().getId());
							if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject3TotalMarks());
							if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject3ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects4()!= null) && (detailMarkto.getDetailedSubjects4().getId() != -1) && (detailMarkto.getDetailedSubjects4().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects4().getId());
							if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject4TotalMarks());
							if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject4ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects5()!= null) && (detailMarkto.getDetailedSubjects5().getId() != -1) && (detailMarkto.getDetailedSubjects5().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects5().getId());
							if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject5TotalMarks());
							if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject5ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects6()!= null) && (detailMarkto.getDetailedSubjects6().getId() != -1) && (detailMarkto.getDetailedSubjects6().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects6().getId());
							if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject6TotalMarks());
							if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject6ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects7()!= null) && (detailMarkto.getDetailedSubjects7().getId() != -1) && (detailMarkto.getDetailedSubjects7().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects7().getId());
							if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject7TotalMarks());
							if(detailMarkto.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject7ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects8()!= null) && (detailMarkto.getDetailedSubjects8().getId() != -1) && (detailMarkto.getDetailedSubjects8().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects8().getId());
							if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject8TotalMarks());
							if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject8ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects9()!= null) && (detailMarkto.getDetailedSubjects9().getId() != -1) && (detailMarkto.getDetailedSubjects9().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects9().getId());
							if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject9TotalMarks());
							if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject9ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects10()!= null) && (detailMarkto.getDetailedSubjects10().getId() != -1) && (detailMarkto.getDetailedSubjects10().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects10().getId());
							if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject10TotalMarks());
							if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject10ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects11()!= null) && (detailMarkto.getDetailedSubjects11().getId() != -1) && (detailMarkto.getDetailedSubjects11().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects11().getId());
							if(detailMarkto.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject11TotalMarks());
							if(detailMarkto.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject11ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects12()!= null) && (detailMarkto.getDetailedSubjects12().getId() != -1) && (detailMarkto.getDetailedSubjects12().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects12().getId());
							if(detailMarkto.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject12TotalMarks());
							if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject12ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects13()!= null) && (detailMarkto.getDetailedSubjects13().getId() != -1) && (detailMarkto.getDetailedSubjects13().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects13().getId());
							if(detailMarkto.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject13TotalMarks());
							if(detailMarkto.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject13ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects14()!= null) && (detailMarkto.getDetailedSubjects14().getId() != -1) && (detailMarkto.getDetailedSubjects14().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects14().getId());
							if(detailMarkto.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject14TotalMarks());
							if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject14ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects15()!= null) && (detailMarkto.getDetailedSubjects15().getId() != -1) && (detailMarkto.getDetailedSubjects15().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects15().getId());
							if(detailMarkto.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject15TotalMarks());
							if(detailMarkto.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject15ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects16()!= null) && (detailMarkto.getDetailedSubjects16().getId() != -1) && (detailMarkto.getDetailedSubjects16().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects16().getId());
							if(detailMarkto.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject15TotalMarks());
							if(detailMarkto.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject16ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects17()!= null) && (detailMarkto.getDetailedSubjects17().getId() != -1) && (detailMarkto.getDetailedSubjects17().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects17().getId());
							if(detailMarkto.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject17TotalMarks());
							if(detailMarkto.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject17ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects18()!= null) && (detailMarkto.getDetailedSubjects18().getId() != -1) && (detailMarkto.getDetailedSubjects18().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects18().getId());
							if(detailMarkto.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject15TotalMarks());
							if(detailMarkto.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject18ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects19()!= null) && (detailMarkto.getDetailedSubjects19().getId() != -1) && (detailMarkto.getDetailedSubjects19().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects19().getId());
							if(detailMarkto.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject19TotalMarks());
							if(detailMarkto.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject19ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if((detailMarkto.getDetailedSubjects20()!= null) && (detailMarkto.getDetailedSubjects20().getId() != -1) && (detailMarkto.getDetailedSubjects20().getId() != 0)){
							EligibilityCriteriaCheckTO elgto= new EligibilityCriteriaCheckTO();
							elgto.setSubjectId(detailMarkto.getDetailedSubjects20().getId());
							if(detailMarkto.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20TotalMarks()))
								temptotal=Double.parseDouble(detailMarkto.getSubject20TotalMarks());
							if(detailMarkto.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20ObtainedMarks()))
								tempobtain=Double.parseDouble(detailMarkto.getSubject20ObtainedMarks());
							tempperc=(tempobtain/temptotal)*100;
							elgto.setPercentage(tempperc);
							criterias.add(elgto);
						}
						if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && StringUtils.isNumeric(detailMarkto.getTotalMarks())){
							totalmark=Double.parseDouble(detailMarkto.getTotalMarks());
						}
						if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getTotalObtainedMarks())){
							totalobtained=Double.parseDouble(detailMarkto.getTotalObtainedMarks());
						}
						if(totalmark!=0.0 && totalobtained!=0.0)
							withoutlanguagePerc=(totalobtained/totalmark)*100;
					
					}
				}
				//else directly take percentage
				else{
				
					double markobtained=0.0;				
					double totalmark=0.0;
					if(ednTO.getMarksObtained()!=null && !StringUtils.isEmpty(ednTO.getMarksObtained()) && CommonUtil.isValidDecimal(ednTO.getMarksObtained())){
						markobtained=Double.parseDouble(ednTO.getMarksObtained());
					}
					if(ednTO.getTotalMarks()!=null && !StringUtils.isEmpty(ednTO.getTotalMarks()) && CommonUtil.isValidDecimal(ednTO.getTotalMarks())){
						totalmark=Double.parseDouble(ednTO.getTotalMarks());
					}
						if(markobtained!=0.0 && totalmark!=0.0)			
						withoutlanguagePerc = (markobtained/totalmark)*100 ;
				}

			}
		}
		
		//compare with eligibilty criteria
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<EligibilityCriteria> eligibilityList=txn.getEligibiltyCriteriaForCourse(courseId,year);
		if(eligibilityList!=null && !eligibilityList.isEmpty())
		{
			//check it is normal check or subject wise check or subject group check
			boolean normalcheck=false;
			boolean normallangcheck=false;
			boolean subjectcheck=false;

			Set<EligibleSubjects> subjects=null;
			double normalperc=0.0;
			double withoutlangperc=0.0;
			Iterator<EligibilityCriteria> elgItr=eligibilityList.iterator();
			while (elgItr.hasNext()) {
				EligibilityCriteria elgCr = (EligibilityCriteria) elgItr.next();
				if(elgCr!=null){
					if(elgCr.getEligibleSubjectses()!=null && !elgCr.getEligibleSubjectses().isEmpty()){
						// remove inactives
						Iterator<EligibleSubjects> subItr=elgCr.getEligibleSubjectses().iterator();
						while (subItr.hasNext()) {
							EligibleSubjects eligibleSub = (EligibleSubjects) subItr.next();
							if(eligibleSub.getIsActive()==null || !eligibleSub.getIsActive())
								subItr.remove();
						}
					}
					if(elgCr.getEligibleSubjectses()!=null && !elgCr.getEligibleSubjectses().isEmpty()){
						subjectcheck=true;
						subjects=elgCr.getEligibleSubjectses();
						withoutlangperc=elgCr.getPercentageWithoutLanguage().doubleValue();
					}else if(elgCr.getPercentageWithoutLanguage()!=null && elgCr.getPercentageWithoutLanguage().doubleValue()!=0.0){
						normallangcheck=true;
						withoutlangperc=elgCr.getPercentageWithoutLanguage().doubleValue();
					}else if(elgCr.getTotalPercentage()!=null && elgCr.getTotalPercentage().doubleValue()!=0.0){
						normalcheck=true;
						normalperc=elgCr.getTotalPercentage().doubleValue();
					}
				}
			}
			//subject check
			if(subjectcheck){
				double obtainedperc=0.0;
				int subNo=0;
				Iterator<EligibleSubjects> elgSubItr=subjects.iterator();
				// get obtained percentage
				while (elgSubItr.hasNext()) {
					EligibleSubjects elgSub = (EligibleSubjects) elgSubItr.next();
					DetailedSubjects detSub=elgSub.getDetailedSubjects();
					if(detSub!=null && detSub.getId()!=0){
						//check criteria tos contain the same subject id or not
						if(criterias!=null && !criterias.isEmpty()){
							Iterator<EligibilityCriteriaCheckTO> checkitr=criterias.iterator();
							while (checkitr.hasNext()) {
								EligibilityCriteriaCheckTO checkTO = (EligibilityCriteriaCheckTO) checkitr.next();
								if(checkTO.getSubjectId()==detSub.getId())
								{
									obtainedperc=obtainedperc+checkTO.getPercentage();
									subNo++;
								}
							}
						}
					}
				}
				
				//if obtained perc >db perc(without language)
				double actualperc=obtainedperc/subNo;
				if(actualperc > withoutlangperc)
					eligible=true;
			}//normal percentage(with language)
			else if(normalcheck){
				//if calcperc> db perc
				if(percwithLangunage>normalperc)
					eligible=true;
			}//percenatge without langugae
			else if(normallangcheck){
				if(withoutlanguagePerc>withoutlangperc)
					eligible=true;
			}
			
			
		}else{
			eligible=true;
		}
		return eligible;
	}
	/**
	 * checks max seat allocation reached for admitted through or not
	 * @param parseInt
	 * @param courseID
	 * @return
	 * @throws Exception
	 */
	public boolean checkSeatAllocationExceeded(int parseInt, int courseID) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean exceeded=txn.checkSeatAllocationExceeded(parseInt,courseID);
		return exceeded;
	}
	/**
	 * FETCHES APPLICANT DETAILS
	 * @param applicationNumber
	 * @param applicationYear
	 * @return appDetails
	 */
	public AdmApplnTO getApplicantDetails(String applicationNumber,
			int applicationYear,boolean admissionForm) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		AdmissionFormHelper helper = AdmissionFormHelper.getInstance();

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
			
		}
		
		if(admittedThroughId > 0){
			AdmittedThrough admittedThrough = new AdmittedThrough();
			admittedThrough.setId(admittedThroughId);
			admittedThrough.setIsActive(true);
			applicantDetails.setAdmittedThrough(admittedThrough);
		}
		
		//to copy the BO properties to TO
		AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails);
		
		return appDetails;
	}
	
	/**
	 * FETCHES APPROVAL APPLICANT DETAILS
	 * @param applicationNumber
	 * @param applicationYear
	 * @return appDetails
	 */
	public AdmApplnTO getApprovalApplicantDetails(String applicationNumber,
			int applicationYear,boolean admissionForm) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		AdmissionFormHelper helper = AdmissionFormHelper.getInstance();

		AdmAppln applicantDetails = txn.getApprovalApplicantDetails(applicationNumber, applicationYear,admissionForm);

		//to copy the BO properties to TO
		AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails);

		return appDetails;
	}
	
	/**
	 * Call of AdmissionFormTransactionImpl method for checking existance of record in DB.
	 * @param appNumber
	 * @param courseId
	 * @param admissionYear
	 * @return
	 * @throws Exception
	 */
	
	public List<Integer> checkApplicationCancel(AdmissionFormForm admForm) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Integer> applno = txn.checkApplicationNumberCancel(getSelectionSearchCriteria(admForm));
		
		return applno;	
	}
	

	/**
	 * @param attendanceSummaryReportForm
	 * @return
	 * This method will build dynamic query
	 */
	private static String commonSearch(AdmissionFormForm admForm) {
		log.info("entered commonSearch..");
		String searchCriteria = "";

		if (admForm.getApplicationYear().trim().length() > 0) {
			String appliedYear = " st.admAppln.appliedYear = "
				+ admForm.getApplicationYear();
			searchCriteria = searchCriteria + appliedYear;
		}
		if (admForm.getRegNumber()!=null && admForm.getRegNumber().trim().length() > 0) {
			String regNo = " and st.registerNo = "+ "'"+admForm.getRegNumber().trim()+"'";
			searchCriteria = searchCriteria + regNo;
		}
		if (admForm.getApplicationNumber()!=null && StringUtils.isNumeric(admForm.getApplicationNumber().trim()) && admForm.getApplicationNumber().trim().length() > 0) {
			String appNo = " and st.admAppln.applnNo = "+ Integer.parseInt(admForm.getApplicationNumber().trim());
			searchCriteria = searchCriteria + appNo;
		}
		if(admForm.getRollNo()!=null && admForm.getRollNo().trim().length() > 0){
			String rollNo = " and st.rollNo = "+ "'"+admForm.getRollNo().trim()+"'";
			searchCriteria = searchCriteria + rollNo;
		}
		if(admForm.getCourseId()!= null && admForm.getCourseId().trim().length() > 0){
			String courseId = " and st.admAppln.courseBySelectedCourseId.id = "+ admForm.getCourseId();
			searchCriteria = searchCriteria + courseId;
		}
		if(admForm.getProgramId()!= null && admForm.getProgramId().trim().length() > 0){
			String programId = " and st.admAppln.courseBySelectedCourseId.program.id = "+ admForm.getProgramId();
			searchCriteria = searchCriteria + programId;
		}
		if(admForm.getProgramTypeId()!= null && admForm.getProgramTypeId().trim().length() > 0){
			String ptypeId = " and st.admAppln.courseBySelectedCourseId.program.programType.id = "+ admForm.getProgramTypeId();
			searchCriteria = searchCriteria + ptypeId;
		}
		searchCriteria = searchCriteria + " and st.admAppln.isSelected = 1";
		log.info("exit commonSearch..");
		return searchCriteria;
	}

	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteria(
			AdmissionFormForm admForm) {
		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(admForm);

		String searchCriteria= "select st.admAppln.applnNo from Student st"
			+" where"+statusCriteria ;					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	
	
	/**
	 * Call of AdmissionFormTransactionImpl method for cancellation of record in DB.
	 * @param appNumber
	 * @param courseId
	 * @param admissionYear
	 * @param remarks
	 * @param lastModifiedBy 
	 * @return
	 * @throws Exception
	 */
	
	public boolean updateApplicationCancel(int appNumber, int admissionYear, String remarks,String cancelDate,Boolean removeRegNo, String lastModifiedBy) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean isStudentUpdated = false;
		boolean isUpdated = txn.updateApplicationNumberCancel(appNumber,admissionYear,remarks,cancelDate,lastModifiedBy);
		if(isUpdated){
			int studentId = txn.getStudentId(appNumber, admissionYear);
			if(studentId != 0){
				isStudentUpdated = txn.updateStudentRecord(studentId,removeRegNo);
			}
		}
		if(isUpdated && isStudentUpdated){
			return true;
		}else{
			return false;
		}
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
	 * SAVES PREREQUISITES TO SESSION
	 * @param session
	 * @param prerequisites
	 * @param userID
	 */
	public void savePrerequisitesToSession(HttpSession session,
			List<CoursePrerequisiteTO> prerequisites,String userID){
		if(prerequisites!=null){
			Set<CandidatePrerequisiteMarks> prerequisiteSet= new HashSet<CandidatePrerequisiteMarks>();
			Iterator<CoursePrerequisiteTO> toItr=prerequisites.iterator();
			while (toItr.hasNext()) {
				CoursePrerequisiteTO to = (CoursePrerequisiteTO) toItr.next();
				CandidatePrerequisiteMarks bo= new CandidatePrerequisiteMarks();
				if(to.getPrerequisiteTO()!=null){
				Prerequisite prereq=new Prerequisite();
				prereq.setId(to.getPrerequisiteTO().getId());
				bo.setPrerequisite(prereq);
				}else
				bo.setPrerequisite(null);
				bo.setPrerequisiteMarksObtained(new BigDecimal(to.getUserMark()));
				bo.setPrerequisiteTotalMarks(new BigDecimal(to.getTotalMark()));
				bo.setRollNo(to.getRollNo());
				bo.setExamMonth(to.getExamMonth());
				bo.setExamYear(Integer.parseInt(to.getExamYear()));
				bo.setIsActive(true);
				bo.setCreatedBy(userID);
				bo.setCreatedDate(new Date());
				prerequisiteSet.add(bo);
			}
			session.setAttribute(CMSConstants.STUDENT_PREREQUISITES, prerequisiteSet);
		}
		
	}
	/**
	 * UPDATES ADMISSION APPROVAL
	 * @param appNO
	 * @param appYear
	 * @param appRemark
	 * @return
	 * @throws Exception
	 */
	public boolean updateApproval(int appNO, int appYear,String appRemark,String admthrid) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean isUpdated = txn.updateApproval(appNO,appYear,appRemark,admthrid);
		return isUpdated;
	}
	/**
	 *  CHECKS DUPLICATE PREREQUISITE INFORMATION OR NOT
	 * @param examYear
	 * @param rollNo
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicatePrerequisite(String examYear, String rollNo) throws Exception {
		int year=0;
		if(examYear!=null && !StringUtils.isEmpty(examYear) && StringUtils.isNumeric(examYear))
			year=Integer.parseInt(examYear);
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean duplicate = txn.checkDuplicatePrerequisite(year,rollNo);
		return duplicate;
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
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean duplicate = txn.checkPaymentDetailsDuplicate(challanNo,journalNo,applnDate,year);
		return duplicate;
	}
	
	/**
	 * CHECKS VALID OFFLINE APPLN.NO OR NOT
	 * @param appNO
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public boolean checkValidOfflineNumber(int appNO, int year) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean valid = txn.checkValidOfflineNumber(appNO,year);
		return valid;
	}
	
	/**
	 * FETCHES Application Details
	 * @param applicationNumber
	 * @param applicationYear
	 * @return appDetails
	 */
	public AdmApplnTO getApplicationDetails(String applicationNumber,
			int applicationYear) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
		String onlinePaymentSts=txn.getPaymentStatus(applicationNumber);
		AdmAppln applicantDetails = txn.getApplicationDetails(applicationNumber, applicationYear);

		//to copy the BO properties to TO
		AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails);
		if (onlinePaymentSts!=null) {
			appDetails.setOnlinPaymentStatus(onlinePaymentSts);
		}

		return appDetails;
	}
	
		
	/**
	 * @param request 
	 * @param id
	 * @return
	 */
	public String getDeclarationTemplate(AdmApplnTO applicantDetails, HttpServletRequest request) throws Exception {
		String template="";
		List<GroupTemplate> gtem =TemplateHandler.getInstance().getDuplicateCheckList(applicantDetails.getCourse().getId(),CMSConstants.APPLN_INSTR_TEMPLATE_NAME);
		if(gtem!=null && !gtem.isEmpty())
		{
			GroupTemplate tempBo=gtem.get(0);
			if(tempBo!=null && tempBo.getTemplateDescription()!=null)
			template=tempBo.getTemplateDescription();
		}
		String course=applicantDetails.getSelectedCourse().getName();
		String program=applicantDetails.getSelectedCourse().getProgramName();
		StringBuffer name=new StringBuffer();
		String applnNo=String.valueOf(applicantDetails.getApplnNo());
		String email="";
		if(applicantDetails.getPersonalData()!=null )
		{
			name.append(applicantDetails.getPersonalData().getFirstName());
			name.append(" ");
			if(applicantDetails.getPersonalData().getMiddleName()!=null)
			name.append(applicantDetails.getPersonalData().getMiddleName());
			name.append(" ");
			if(applicantDetails.getPersonalData().getLastName()!=null)
			name.append(applicantDetails.getPersonalData().getLastName());
			if(applicantDetails.getPersonalData().getEmail()!=null)
			email=applicantDetails.getPersonalData().getEmail();
		}
		String logoPath="";
		logoPath = request.getContextPath();
		logoPath = "<img src="
				+ logoPath
				+ "/LogoServlet alt='Logo not available' width='210' height='100' >";
		
		template = template.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name.toString());
		template = template.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnNo);
		template = template.replace(CMSConstants.TEMPLATE_EMAIL,email);
		template = template.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
		template = template.replace(CMSConstants.TEMPLATE_COURSE,course);
		template = template.replace(CMSConstants.TEMPLATE_PROGRAM,program);
		return template;
	}
	/**
	 * @param id
	 * @return
	 */
	public String getOfficeInstructionTemplate(AdmApplnTO applicantDetails, HttpServletRequest request) throws Exception {
		String template="";
		List<GroupTemplate> gtem =TemplateHandler.getInstance().getDuplicateCheckList(applicantDetails.getCourse().getId(),CMSConstants.OFFICE_INSTR_TEMPLATE_NAME);
		if(gtem!=null && !gtem.isEmpty())
		{
			GroupTemplate tempBo=gtem.get(0);
			if(tempBo!=null && tempBo.getTemplateDescription()!=null)
			template=tempBo.getTemplateDescription();
		}
		String course=applicantDetails.getSelectedCourse().getName();
		String program=applicantDetails.getSelectedCourse().getProgramName();
		StringBuffer name=new StringBuffer();
		String applnNo=String.valueOf(applicantDetails.getApplnNo());
		String email="";
		if(applicantDetails.getPersonalData()!=null )
		{
			name.append(applicantDetails.getPersonalData().getFirstName());
			name.append(" ");
			if(applicantDetails.getPersonalData().getMiddleName()!=null)
			name.append(applicantDetails.getPersonalData().getMiddleName());
			name.append(" ");
			if(applicantDetails.getPersonalData().getLastName()!=null)
			name.append(applicantDetails.getPersonalData().getLastName());
			if(applicantDetails.getPersonalData().getEmail()!=null)
			email=applicantDetails.getPersonalData().getEmail();
		}
		String logoPath="";
		logoPath = request.getContextPath();
		logoPath = "<img src="
				+ logoPath
				+ "/LogoServlet alt='Logo not available' width='210' height='100' >";
		
		template = template.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name.toString());
		template = template.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnNo);
		template = template.replace(CMSConstants.TEMPLATE_EMAIL,email);
		template = template.replace(CMSConstants.TEMPLATE_LOGO,logoPath);
		template = template.replace(CMSConstants.TEMPLATE_COURSE,course);
		template = template.replace(CMSConstants.TEMPLATE_PROGRAM,program);
		return template;
	}
	/*
	 * Single application start
	 */
	
	/**
	 * returns a blank student object
	 * @param stForm
	 * @return
	 */
	public AdmApplnTO getNewStudent(HttpSession session,AdmissionFormForm stForm)throws Exception {
		log.info("Enter getNewStudent ...");
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		log.info("Exit getNewStudent ...");
		return helper.getNewStudent(session,stForm.getCourseId(),stForm);
	}
	
	/**
	 * creates a new student record
	 * @param applicantDetail
	 * @param admForm
	 * @return
	 */
	public boolean createApplicant(AdmApplnTO applicantDetail,
			AdmissionFormForm admForm,boolean isPresidance) throws Exception {
		log.info("enter createApplicant");
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		AdmAppln appBO=helper.getApplicantBO(applicantDetail,admForm,isPresidance);
		Student admBO= new Student();
		admBO.setIsAdmitted(false);
		
		if(appBO!=null){
			appBO.setIsCancelled(false);
			appBO.setIsSelected(false);
			appBO.setIsApproved(false);
			appBO.setIsFinalMeritApproved(false);
			appBO.setIsLig(false);
			appBO.setIsBypassed(false);
			//added for challan verification
			appBO.setIsChallanVerified(false);
			//addition for challan verification completed
			admBO.setCreatedBy(appBO.getCreatedBy());
			admBO.setCreatedDate(appBO.getCreatedDate());
			// setting the candidate Pre requisite details from detailApplicantCreate jsp.
			Iterator<CandidatePrerequisiteMarks> candidatePreRequisiteMarksItr=appBO.getCandidatePrerequisiteMarks().iterator();
			while (candidatePreRequisiteMarksItr.hasNext()) {
				CandidatePrerequisiteMarks preRequisiteMarks = (CandidatePrerequisiteMarks) candidatePreRequisiteMarksItr.next();
				preRequisiteMarks.setPrerequisiteMarksObtained(new BigDecimal(applicantDetail.getPreRequisiteObtMarks()));
				preRequisiteMarks.setRollNo(applicantDetail.getPreRequisiteRollNo());
				preRequisiteMarks.setExamMonth(Integer.parseInt(applicantDetail.getPreRequisiteExamMonth()));
				preRequisiteMarks.setExamYear(Integer.parseInt(applicantDetail.getPreRequisiteExamYear()));
			}
			
			Set<AdmapplnAdditionalInfo> admAddnSet=new HashSet<AdmapplnAdditionalInfo>();
			AdmapplnAdditionalInfo additionalInfo=new AdmapplnAdditionalInfo();
			additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
			additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
			if(applicantDetail.getApplicantFeedbackId()!=null){
			ApplicantFeedback feedback=new ApplicantFeedback();
			feedback.setId(Integer.parseInt(applicantDetail.getApplicantFeedbackId()));
			additionalInfo.setApplicantFeedback(feedback);
			if(applicantDetail.getInternationalCurrencyId()!=null && !applicantDetail.getInternationalCurrencyId().isEmpty()){
				Currency curr=new Currency();
				curr.setId(Integer.parseInt(applicantDetail.getInternationalCurrencyId()));
				additionalInfo.setInternationalApplnFeeCurrency(curr);
			}
			// /* code added by chandra
			if(applicantDetail.getIsComeDk()!=null ){
				if(applicantDetail.getIsComeDk())
					additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
				else
					additionalInfo.setIsComedk(false);
			}else{
				additionalInfo.setIsComedk(false);
			}
			// */ code added by chandra
			additionalInfo.setCreatedBy(appBO.getCreatedBy());
			additionalInfo.setCreatedDate(new Date());
			additionalInfo.setModifiedBy(appBO.getCreatedBy());
			additionalInfo.setLastModifiedDate(new Date());
			additionalInfo.setBackLogs(admForm.isBackLogs());
			//raghu added newly
			additionalInfo.setIsSaypass(admForm.getIsSaypass());
			
			additionalInfo.setIsSpotAdmission(Boolean.FALSE);
			}
			admAddnSet.add(additionalInfo);
			appBO.setAdmapplnAdditionalInfo(admAddnSet);
		}
		admBO.setAdmAppln(appBO);
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		boolean updated=txn.createNewApplicant(admBO, admForm);
		
		
		log.info("exit createApplicant");
		
		return updated;
	}
	/**
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DocTypeExamsTO> getDocExamsByID(int id) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<DocTypeExams> examBos=txn.getDocExamsByID(id);
		return AdmissionFormHelper.getInstance().convertDocExamBosToTOS(examBos);
	}

	/**
	 * Send mail to student after successful submit of application
	 * @param admForm
	 * @return
	 */
	public boolean sendMailToStudentSinglePage(AdmissionFormForm admForm) throws Exception {
			boolean sent=false;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			ITemplatePassword txn= new TemplateImpl();
			List<GroupTemplate> list= txn.getGroupTemplate(Integer.parseInt(admForm.getCourseId()), CMSConstants.APPLICANT_MAIL_TEMPLATE, Integer.parseInt(admForm.getCourseId()));
			//List<GroupTemplate> list= temphandle.getDuplicateCheckList(Integer.parseInt(admForm.getCourseId()),CMSConstants.APPLICANT_MAIL_TEMPLATE);
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant

						String dob = "";
						String name = "";
						String applnno = "";
					
						if(admForm.getApplicantDetails().getPersonalData().getEmail()!=null && !StringUtils.isEmpty(admForm.getApplicantDetails().getPersonalData().getEmail().trim())){
							dob=admForm.getApplicantDetails().getPersonalData().getDob();
							name=admForm.getApplicantDetails().getPersonalData().getFirstName();
							applnno=admForm.getApplicationNumber();
							String program = "";
							String appliedYear = "";
							if(admForm.getProgramName() !=null) {
								program = admForm.getProgramName();
							}
							
							if(admForm.getApplicationYear()!=null && !admForm.getApplicationYear().isEmpty()) {
								appliedYear = admForm.getApplicationYear() + "-" + (Integer.valueOf(admForm.getApplicationYear())+1);
							}
							
							//replace dyna data
							String subject= "Application for "+program +" "+ appliedYear+ " submitted successfully";
							
							String message =desc;
							message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
							message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,applnno);
							message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
//							send mail
							sendMail(admForm.getApplicantDetails().getPersonalData().getEmail(),subject,message);
							//print letter
							HtmlPrinter.printHtml(message);

						}
					
			
			} 
			return sent;
	}	
	/*
	 * Single application end
	 */
	public int getApplicationYear(int programId) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		int year = txn.getAppliedYearForProgram(programId);
		return year;
	}

	/**
	 * get resident category list
	 * @return
	 */
	public List<ExamCenterTO> getExamCenters(int programId)throws Exception{
		log.info("Enter getResidentTypes ...");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<ExamCenter> examCenter=txn.getExamCenterForProgram(programId);
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		List<ExamCenterTO> examCenterList =helper.convertexamCenterBOToTO(examCenter);
		log.info("Exit getResidentTypes ...");
		return examCenterList;
	}
	/**
	 * getting the course Map which contain Course Code and Id from the database.
	 * @param parseInt
	 * @param i
	 * @return
	 * @throws Exception
	 */
	public Map<String, Integer> getCoursesById(int id, int mode) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		String searchQuery=AdmissionFormHelper.getInstance().getSearchQuery(id,mode);
		return txn.getCourseMapByInputId(searchQuery);
	}
	public String getInterviewDateOfApplicant(String applicationNumber,int year) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		return txn.getInterviewDateOfApplicant(applicationNumber,year);
	}
	/**
	 * @param applicationNumber
	 * @param applicationYear
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO getListOfSubmittedDetails(String applicationNumber,
			String applicationYear) throws Exception {
		log.info("Entering into getListOfSubmittedDetails ...");
		AdmApplnTO admApplnTO = null;
		AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
		IAdmissionFormTransaction txn = new AdmissionFormTransactionImpl();
		int applicationNo = (applicationNumber != null
				&& applicationNumber.trim().length() > 0 ? Integer
				.parseInt(applicationNumber) : 0);
		int application_Year = (applicationYear != null
				&& applicationYear.trim().length() > 0 ? Integer
				.parseInt(applicationYear) : 0);
		AdmAppln admAppln = txn.getListOfSubmittedDetails(applicationNo,
				application_Year);

		if (admAppln != null) {
			admApplnTO = helper.copyPropertiesValue(admAppln);
		} else {
			throw new BusinessException();
		}
		// -----Fetch programId from course table---//
		CourseTO course = admApplnTO.getCourse();
		int programId = txn.getProgrameIdForCourse(course.getId());
		List<DocChecklist> docCheckList = txn
				.getRequiredDocsForCourseAndProgram(course.getId(), programId,
						application_Year);
		log.info("Exiting  getListOfSubmittedDetails ...");
		return admApplnTO;
	}

	/**
	 * @param applicantDetails
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getDeclarationTemplateForPendingDoc(
			AdmApplnTO applicantDetails, HttpServletRequest request)
			throws Exception {
		String template = "";
		String courseName = "";
		String courseCode="";
		String photoPath = "";
		String logoPath = "";
		String barCode = "";
		byte[] barCodeByte = null;
		int selectedCourseId=0;
		if(applicantDetails.getSelectedCourse()!=null){
			selectedCourseId=applicantDetails.getSelectedCourse().getId();
		}
		List<GroupTemplate> gtem = TemplateHandler.getInstance()
				.getDuplicateCheckList(selectedCourseId,CMSConstants.APPLN_PENDING_TEMPLATE_NAME);
		if (gtem != null && !gtem.isEmpty()) {
			GroupTemplate tempBo = gtem.get(0);
			if (tempBo != null && tempBo.getTemplateDescription() != null)
				template = tempBo.getTemplateDescription();
		}
		StringBuffer name = new StringBuffer();
		String applnNo = String.valueOf(applicantDetails.getApplnNo());
	//------------bar code creation added by Manu
		Barcode code = null;
		File barCodeImgFile = null;
		if(!applnNo.trim().isEmpty()){
			String imgPath=request.getRealPath("");
			imgPath = imgPath + "//BarCode//"+ applnNo + ".jpeg";
			 barCodeImgFile =  new File(imgPath);
			if(barCodeImgFile.exists()){
				barCodeImgFile.delete();
			}
			code = BarcodeFactory.createCode128A(applnNo);
			code.setBarWidth(1);
			code.setBarHeight(40);
			code.setDrawingText(false);
			BarcodeImageHandler.saveJPEG(code, barCodeImgFile);
		}
		//
		if (applicantDetails.getPersonalData() != null) {
			name.append(applicantDetails.getPersonalData().getFirstName());
			name.append(" ");
			if (applicantDetails.getPersonalData().getMiddleName() != null)
				name.append(applicantDetails.getPersonalData().getMiddleName());
			name.append(" ");
			if (applicantDetails.getPersonalData().getLastName() != null)
				name.append(applicantDetails.getPersonalData().getLastName());
		}
		if (applicantDetails.getSelectedCourse() != null
				&& applicantDetails.getSelectedCourse().getName() != null) {
			courseName = applicantDetails.getSelectedCourse().getName();
			if(applicantDetails.getSelectedCourse().getCode()!=null)
			courseCode= applicantDetails.getSelectedCourse().getCode();
		}
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
		if(applicantDetails.getEditDocuments()!=null){
			Iterator<ApplnDocTO> docItr=applicantDetails.getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo") && docTO.isDefaultPhoto()  && (docTO.getPhotoBytes() == null || docTO.getPhotoBytes().length <= 0))
				{
					if(session!=null){
						byte[] fileData= null;
						// set default photo image
						try {
							InputStream photoin=AdmissionFormAction.class.getClassLoader().getResourceAsStream(CMSConstants.PRINT_PHOTO_PATH);
							
							if(photoin!=null){
								fileData= new byte[photoin.available()];
								photoin.read(fileData, 0, photoin.available());	
							}
							
						} catch (Exception e) {
							log.error(e);
						}
						
						if(fileData!=null)
						session.setAttribute(CMSConstants.PHOTOBYTES,fileData );
					}
				}else if(docTO.getDocName()!=null && docTO.getDocName().equalsIgnoreCase("Photo")){
					
					if(session!=null){
						session.setAttribute(CMSConstants.PHOTOBYTES, docTO.getPhotoBytes());
					}
				}
				
			}
		}
		photoPath= request.getContextPath();
		photoPath = "<img src="
			+ photoPath
			+ "/PhotoServlet  height='150Px' width='150Px' >";
		StringBuffer submittedDoc = new StringBuffer();
		StringBuffer pendingDoc = new StringBuffer();
		List<String> originalList=applicantDetails.getOriginalList();
		List<String> pendingDocList=applicantDetails.getPendingDocList();
		int count=0;
		for(String subName:originalList)
		{
			count=count+1;
			submittedDoc.append(count+"."+subName + "<BR>");
		}
		count=0;
		for(String pendindDocName:pendingDocList)
		{
			count=count+1;
			pendingDoc.append(count+"."+pendindDocName + "<BR>");
		}
		//Added By manu For BarCode
		barCodeByte = new byte[(int)barCodeImgFile.length ()];
		FileInputStream fis=new FileInputStream(barCodeImgFile);
		// convert image into byte array
		fis.read(barCodeByte);
		session.setAttribute("barCodeBytes", barCodeByte);
		barCode = request.getContextPath();
		barCode = "<img src="+ barCode+ "/BarCodeServlet alt='Barcode  not available' width='137' height='37' >";
		template = template.replace(CMSConstants.TEMPLATE_BARCODE, barCode);
		//end
		template = template.replace(CMSConstants.TEMPLATE_COURSE, courseName);
		template=template.replace(CMSConstants.TEMPLATE_COURSE_CODE, courseCode);
		template = template.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, name
				.toString());
		template = template.replace(CMSConstants.TEMPLATE_APPLICATION_NO,
				applnNo);
		template = template.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
		template = template.replace(CMSConstants.TEMPLATE_PHOTO, photoPath);
		template = template.replace(CMSConstants.TEMPLATE_SUBMITTED_DOCUMENTS, submittedDoc.toString());
		template = template.replace(CMSConstants.TEMPLATE_PENDING_DOCUMENTS, pendingDoc.toString());
		template = template.replace(CMSConstants.TEMPLATE_CURRENT_DATE, CommonUtil.getStringDate(new Date()));
		template = template.replace(CMSConstants.TEMPLATE_SUBMITTED_DATE, applicantDetails.getSubmissionDate());
		return template;
	}

	/**
	 * @param applicationNumber
	 * @param applicationYear
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO getApplicationPendingDocDetails(String applicationNumber,
			int applicationYear, HttpServletRequest request) throws Exception {
		IAdmissionFormTransaction txn = new AdmissionFormTransactionImpl();
		AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
     List<ApplnDoc> appDocList=txn.getListOfDocuments(applicationNumber, applicationYear);
		AdmAppln applicantDetails = txn.getApplicationDetails(
				applicationNumber, applicationYear);
		// to copy the BO properties to TO
		AdmApplnTO appDetails = helper.copyPropertiesValueForPendingDoc(
				applicantDetails, request);
		appDetails=helper.convertBOToTO(appDocList,appDetails);
		return appDetails;
	}
	
	/**
	 * @param studentSearchForm
	 * @return
	 * This method will give final query
	 */
	public static String getSelectionSearchCriteriaForApplicant(
			AdmissionFormForm admForm) {
		log.info("entered getSelectionSearchCriteria..");
		String statusCriteria = commonSearch(admForm);

		String searchCriteria= "select st.admAppln from Student st"
			+" where"+statusCriteria ;					
		log.info("exit getSelectionSearchCriteria..");
		return searchCriteria;

	}
	/**
	 * FETCHES APPLICANT DETAILS
	 * @param applicationNumber
	 * @param applicationYear
	 * @return appDetails
	 */
	public AdmApplnTO getApplicantDetailsForCancel(AdmissionFormForm admissionForm) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();

		AdmAppln applicantDetails = txn.checkApplicationNumberForCancel(getSelectionSearchCriteriaForApplicant(admissionForm));
		//to copy the BO properties to TO
		AdmApplnTO admApplnTo = null;
		if(applicantDetails!=null){
			admApplnTo=new AdmApplnTO();
			String name="";
			if(applicantDetails.getAdmissionDate()!=null){
				admApplnTo.setAdmissionDate(applicantDetails.getAdmissionDate().toString());
			}
			if(applicantDetails.getPersonalData()!=null){
				PersonalData pd=applicantDetails.getPersonalData();
				if(pd.getFirstName()!=null){
					name=name+pd.getFirstName();
				}
				if(pd.getMiddleName()!=null){
					name=name+pd.getMiddleName();
				}
				if(pd.getLastName()!=null){
					name=name+pd.getLastName();
				}
				PersonalDataTO pdt=new PersonalDataTO();
				pdt.setFirstName(name);
				if(pd.getSecondLanguage()!=null){
					pdt.setSecondLanguage(pd.getSecondLanguage());
				}
				admApplnTo.setPersonalData(pdt);
				admApplnTo.setApplnNo(applicantDetails.getApplnNo());
			}
			if(applicantDetails.getCourseBySelectedCourseId()!=null){
				CourseTO cto=new CourseTO();
				cto.setName(applicantDetails.getCourseBySelectedCourseId().getName());
				admApplnTo.setSelectedCourse(cto);
			}
		}
		return admApplnTo;
	}
	public Map<String, Integer> getCourses() throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		return txn.getCourseMap();
	}
	@SuppressWarnings("unchecked")
	public AdmApplnTO getApplicantStatusDetails(String applicationNumber,
			int applicationYear, int courseId,HttpServletRequest request) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();


		List applicantDetails = txn.getApplicantStatusDetails(applicationNumber, applicationYear, courseId);
		/*	AdmApplnTO adminAppTO = null;
		if (applicantDetails != null) {
			adminAppTO = new AdmApplnTO();

			adminAppTO.setStatus(applicantDetails.getAdmStatus());
		}*/

		AdmApplnTO	adminAppTO = new AdmApplnTO();
		if (applicantDetails != null && !applicantDetails.isEmpty()) {
			String Status="";
			String ShortStatus="";
			Iterator itr = applicantDetails.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[0] != null) {
					Status = (obj[0].toString());
					adminAppTO.setStatus(Status);
				}
				if (obj[1] != null) {
					ShortStatus = (obj[1].toString());
					adminAppTO.setShortStatus(ShortStatus);
				}
		      		
				
			}	
		}
		return adminAppTO;
	}
	
	public AdmApplnTO getApplicantStatusDetails(String applicationNumber,
			int applicationYear, int courseId) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();


		List applicantDetails = txn.getApplicantStatusDetails(applicationNumber, applicationYear, courseId);
		/*	AdmApplnTO adminAppTO = null;
		if (applicantDetails != null) {
			adminAppTO = new AdmApplnTO();

			adminAppTO.setStatus(applicantDetails.getAdmStatus());
		}*/

		AdmApplnTO	adminAppTO = new AdmApplnTO();
		if (applicantDetails != null && !applicantDetails.isEmpty()) {
			String Status="";
			String ShortStatus="";
			Iterator itr = applicantDetails.iterator();
			while (itr.hasNext()) {
				Object[] obj = (Object[]) itr.next();
				if (obj[0] != null) {
					Status = (obj[0].toString());
					adminAppTO.setStatus(Status);
				}
				if (obj[1] != null) {
					ShortStatus = (obj[1].toString());
					adminAppTO.setShortStatus(ShortStatus);
				}
		      		
				
			}	
		}
		return adminAppTO;
	}
	/**
	 * @param stForm
	 * @throws Exception
	 */
	public void sendSMSToStudent(AdmissionFormForm stForm) throws Exception {
		Properties prop = new Properties();
        InputStream in1 = NewStudentMarksCorrectionAction.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);
		String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SMS_TEMPLATE_ONLINE_SUBMISSION);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, stForm.getApplicationNumber());
		String mobileNo="";
		if(stForm.getApplicantDetails().getPersonalData().getMobileNo1()!=null && !stForm.getApplicantDetails().getPersonalData().getMobileNo1().isEmpty()){
			mobileNo=stForm.getApplicantDetails().getPersonalData().getMobileNo1();
		}else{
			mobileNo="91";
		}
		if(stForm.getApplicantDetails().getPersonalData().getMobileNo2()!=null && !stForm.getApplicantDetails().getPersonalData().getMobileNo2().isEmpty()){
			mobileNo=mobileNo+stForm.getApplicantDetails().getPersonalData().getMobileNo2();
		}
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			PropertyUtil.getInstance().save(mob);
		}
		
	}
	
	public Map<String, String> getYear() throws Exception {
		return AdmissionFormTransactionImpl.getInstance().getYear();
	}
	public Map<String, String> getYearByMonth(String Month) throws Exception {
		return AdmissionFormTransactionImpl.getInstance().getYearByMonth(Month);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<GenerateMail> getMails() throws Exception {
		IAdmissionFormTransaction transaction=AdmissionFormTransactionImpl.getInstance();
		return transaction.getMails();
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public String getParameterForPGI(AdmissionFormForm admForm) throws Exception {
		
		CandidatePGIDetails bo= new CandidatePGIDetails();
		bo.setCandidateName(admForm.getApplicantName());
		bo.setCandidateDob(CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob()));
		if(admForm.getCourseId()!=null && !admForm.getCourseId().isEmpty()){
		Course course=new Course();
		course.setId(Integer.parseInt(admForm.getCourseId()));
		bo.setCourse(course);
		}
		bo.setTxnStatus("Pending");
	//	if(admForm.getIndianCandidate()){
			if(admForm.getApplicationAmount()!=null && !admForm.getApplicationAmount().isEmpty())
				bo.setTxnAmount(new BigDecimal(admForm.getApplicationAmount()));
		//}else{
		//	if(admForm.getEquivalentCalcApplnFeeINR()!=null && !admForm.getEquivalentCalcApplnFeeINR().isEmpty())
			//	bo.setTxnAmount(new BigDecimal(admForm.getEquivalentCalcApplnFeeINR()));
	//	}
		//below setting of txn amount has to be commented once the Production phase of PGI has started 
//		bo.setTxnAmount(new BigDecimal("2"));// for testing purpose we are passing 2 rupees
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
		
		//String productinfo="productinfo";
		admForm.setRefNo(candidateRefNo);
		//admForm.setProductinfo(productinfo);
		
		//change the url of response in the msg below when it has to be released to the production. Please put the production IP
		if(candidateRefNo!=null && !candidateRefNo.isEmpty())
		 //temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(candidateRefNo).append("|NA|").append(bo.getTxnAmount()).append("|NA|NA|NA|INR|NA|R|").append(CMSConstants.PGI_SECURITY_ID).append("|NA|NA|F|applicationFee|NA|NA|NA|NA|NA|NA|").append(CMSConstants.pgiLink);
			temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(CMSConstants.PGI_SECURITY_ID).append("|").append(CMSConstants.PGI_MERCHANT_ID+candidateRefNo).append("|").append(Integer.parseInt(admForm.getApplicationAmount())*100).append("|").append("A").append("|").append(CMSConstants.PGI_CHECKSUM_KEY);
		//sha512 ("key|txnid|amount|productinfo|firstname|email|||||||||||","<SALT>");
		//raghu write for pay e
		String hash=sha1("SHA-1",temp.toString());
		admForm.setTest(temp.toString());
		
		//if(checkSum!=null && !checkSum.isEmpty())
		// msg.append(temp).append("|").append(checkSum);
		return hash;
	}
	/**
	 * updating the response received from third party Payment Gateway
	 * @param admForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateResponse(AdmissionFormForm admForm) throws Exception{
		boolean isUpdated=false;
		CandidatePGIDetails bo=AdmissionFormHelper.getInstance().convertToBo(admForm);
		IAdmissionFormTransaction transaction=AdmissionFormTransactionImpl.getInstance();
		isUpdated=transaction.updateReceivedStatus(bo,admForm);
		return isUpdated;
	}
	/**
	 * Checks if Application Fee is paid through inline and Not filled the complete appln
	 * @param admForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkApplnFeePaidThroughOnline(AdmissionFormForm admForm) throws Exception{
		boolean applnFeePaid=false;
		String appName=admForm.getApplicantName();
		if(admForm.getApplicantName().contains("'")){
			appName = admForm.getApplicantName().replaceAll("'", "''");
		}
		String query="from CandidatePGIDetails c where c.candidateName='" +appName+
				"' and c.candidateDob='" +CommonUtil.ConvertStringToSQLDate(admForm.getApplicantDob())+
				"' and c.course.id="+admForm.getCourseId()+
				"  and c.mobileNo1='" +admForm.getMobileNo1()+
				"' and c.mobileNo2='" +admForm.getMobileNo2()+
				"' and c.email='" +admForm.getEmail()+
				"' and c.residentCategory.id=" +admForm.getResidentCategoryForOnlineAppln()+ 
				" and c.admAppln.id is null and c.txnStatus='Success' and c.refundGenerated=0 " +
				" order by c.id desc";
		IAdmissionFormTransaction txn=new AdmissionFormTransactionImpl();
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
	 * @return
	 * @throws Exception
	 */
	public List<SingleFieldMasterTO> getApplicantFeedback() throws Exception{
		IAdmissionFormTransaction txn=new AdmissionFormTransactionImpl();
		List<ApplicantFeedback> bo=txn.getApplicantFeedback();
		List<SingleFieldMasterTO> toList=null;
		if(bo!=null && !bo.isEmpty()){
			toList= new ArrayList<SingleFieldMasterTO>();
			Iterator<ApplicantFeedback> itr= bo.iterator();
			while (itr.hasNext()) {
				ApplicantFeedback applicantFeedback = (ApplicantFeedback) itr .next();
				SingleFieldMasterTO to=new SingleFieldMasterTO();
				to.setId(applicantFeedback.getId());
				to.setName(applicantFeedback.getName()!=null?applicantFeedback.getName():"");
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param stForm
	 * @throws Exception 
	 */
	public void checkWorkExperianceMandatory(AdmissionFormForm stForm) throws Exception {
		IAdmissionFormTransaction txn=new AdmissionFormTransactionImpl();
		boolean mandatory = txn.checkWorkExperianceMandatory(stForm.getCourseId());
		stForm.setShowWorkExp(mandatory);
	}
	/**
	 * @param applicationNumber
	 * @param applicantDetails
	 * @param admForm 
	 * @throws Exception 
	 */
	public void setPrerequisiteMarks(String applicationNumber, AdmApplnTO applicantDetails, ApplicationEditForm admForm) throws Exception {
		if(applicantDetails != null){
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			List<CandidatePrerequisiteMarks> prerequisites=txn.getPrerequisiteMarks(applicationNumber);
			if (prerequisites!=null && !prerequisites.isEmpty()) {
				Set<CandidatePrerequisiteMarks> set = new HashSet<CandidatePrerequisiteMarks>();
				CandidatePrerequisiteMarks marks = prerequisites.get(0);
				if(marks.getPrerequisiteMarksObtained() != null){
					applicantDetails.setPreRequisiteObtMarks(marks.getPrerequisiteMarksObtained().toString());
				}
				applicantDetails.setPreRequisiteRollNo(marks.getRollNo()!=null?marks.getRollNo():"");
				applicantDetails.setPreRequisiteExamMonth(marks.getExamMonth()!=null?String.valueOf(marks.getExamMonth()):"");
				applicantDetails.setPreRequisiteExamYear(marks.getExamYear()!=null?String.valueOf(marks.getExamYear()):"");
				set.add(marks);
				admForm.setPreRequisiteExists(true);
				applicantDetails.setCandidatePrerequisiteMarks(set);
			}
		}else{
			admForm.setPreRequisiteExists(false);
		}
		
	}
	
	/**
	 * @param admApplnId
	 * @return
	 * @throws Exception
	 */
	public String getCandidatePGIDetails(int admApplnId ) throws Exception {
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();

		String txnRefNo = txn.getCandidatePGIDetails(admApplnId);

		return txnRefNo;
	}
	/**
	 * @param admForm
	 * @param admApplnId 
	 * @throws Exception
	 */
	public void getOnlinePaymentAcknowledgementDetails(AdmissionFormForm admForm, int admApplnId) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<String> messageList = new ArrayList<String>();
		CandidatePGIDetails details = txn.getCandidateDetails(admApplnId);
		if(details != null){
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList("Online payment Acknowledgement Slip");
			
			if(list != null && !list.isEmpty()) {
				String desc ="";
				if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
					desc = list.get(0).getTemplateDescription();
				}
				desc = desc.replace("[Applicant_Name]", details.getCandidateName());
				desc = desc.replace("[APPLICATIONNO]", String.valueOf(details.getAdmAppln().getApplnNo()));
				desc = desc.replace("[COURSE]", details.getAdmAppln().getCourseBySelectedCourseId().getName());
				desc = desc.replace("[Transaction_Ref_number]", details.getTxnRefNo());
				desc = desc.replace("[Bank_Ref_number]", details.getBankRefNo());
				desc = desc.replace("[Bank_ID]", details.getBankId());
				desc = desc.replace("[Candidate_Ref_number]", details.getCandidateRefNo());
				desc = desc.replace("[EMAIL]", details.getEmail());
				desc = desc.replace("[Transaction_Date]",  CommonUtil.getStringDate(details.getTxnDate()));
				desc = desc.replace("[Transaction_Amount]",  details.getTxnAmount().toString());
				messageList.add(desc);
			}
			admForm.setMessageList(messageList);
			admForm.setAcknowledgement(true);
		}
	}
	
	
	public boolean addUploadInterviewSelectedData(AdmApplnTO applicantDetail,AdmissionFormForm admForm,HttpServletRequest request) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();	
		int a=0;
		
		InterviewCardTO interviewCardTO=txn.getInterviewScheduleDetails(admForm);
		InterviewSchedule interviewSchedule;
		boolean isAdd = false;
		List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
	
					 interviewSchedule=txn.getInterviewSchedule(interviewCardTO, admForm);
					if(interviewSchedule!=null){
						InterviewCard interviewCard=new InterviewCard();
						AdmAppln adm=new AdmAppln();
						adm.setId(interviewCardTO.getAdmApplnId());
						interviewCard.setAdmAppln(adm);
						interviewCard.setInterview(interviewSchedule);
						interviewCard.setTime(interviewCardTO.getTime());
						interviewCard.setInterviewer(1);
						interviewCard.setCreatedBy(admForm.getUserId());
						interviewCard.setCreatedDate(new Date());
						interviewCard.setLastModifiedDate(new Date());
						interviewCard.setModifiedBy(admForm.getUserId());
						interviewCardsToSave.add(interviewCard);
					}
			Integer interViewPgmCourse= txn.getInterViewPgmCourse(admForm);
			isAdd=txn.addSelectionProcessWorkflowData(interviewCardsToSave,admForm.getUserId(), admForm,  interViewPgmCourse);
			if(isAdd){
				sendMailAndSMSToStudents(interviewCardsToSave,request);
			}
		return isAdd;
	}
	
	private boolean sendMailAndSMSToStudents(List<InterviewCard> interviewCards,HttpServletRequest request) throws Exception {
		boolean sent = false;
		IUploadInteviewSelectionTxn txn=new UploadInterviewSelectionTxnImpl();
		ITemplatePassword ITemplatePassword = TemplateImpl.getInstance();
		Map<Integer, String> templateMap=new HashMap<Integer, String>();
		String desc="";
		byte[] logo=null;
		List<MailTO> mailToList;
		List<MobileMessaging> mobileMessagesList=null;
		if(interviewCards!=null){
			mailToList=new ArrayList<MailTO>();
			mobileMessagesList=new ArrayList<MobileMessaging>();
			Iterator<InterviewCard> itr =interviewCards.iterator();
			HttpSession session = request.getSession(false);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
				if (organisation != null) {
					logo = organisation.getLogo();
				}
				if (session != null) {
					session.setAttribute("LogoBytes", logo);
				}
			//getting the smsTemplate
				String smsDesc="";
				SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
				List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.SMS_TEMPLATE_E_ADMITCARD);
				if(list != null && !list.isEmpty()) {
					smsDesc = list.get(0).getTemplateDescription();
				}
			while (itr.hasNext()) {
				InterviewCard interviewCard = (InterviewCard) itr.next();
				if(interviewCard!=null && interviewCard.getAdmAppln()!=null && interviewCard.getInterview()!=null){
					//getting the InterviewCard BO for admAppln 
					 interviewCard=txn.getInterviewCardBo(interviewCard.getAdmAppln().getId(),interviewCard.getInterview().getId());
					 if(interviewCard!=null){
							if(templateMap!=null && !templateMap.isEmpty() && templateMap.containsKey(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId())){
								 desc = templateMap.get(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId());
							}
							else{
								List<GroupTemplate> templateList =ITemplatePassword.getGroupTemplate(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId(),
										CMSConstants.INTERVIEW_SCHEDULE_TEMPLATE,  interviewCard.getAdmAppln().getCourseBySelectedCourseId().getProgram().getId());
								if(templateList!=null && !templateList.isEmpty()){
									templateMap.put(interviewCard.getAdmAppln().getCourseBySelectedCourseId().getId(), templateList.get(0).getTemplateDescription());
								desc=templateList.get(0).getTemplateDescription();
								}
							}
							if (interviewCard.getAdmAppln().getPersonalData().getEmail() != null) {
								String message = desc;
								message = replaceMessageText(desc, interviewCard.getAdmAppln(), request);
								String interviewDate = "";
								String interviewTime = "";
								String interviewVenue = "";
								String interviewType = "";
								String program = "";
								String academicyear = "";
								if (interviewCard.getInterview()!= null && interviewCard.getInterview().getDate()!=null) {
									interviewDate =interviewCard.getInterview().getDate().toString();
								}

								if (interviewCard.getTime() != null) {
									interviewTime = interviewCard.getTime();
								}
								if (interviewCard.getInterview().getVenue()!= null) {
									interviewVenue = interviewCard.getInterview().getVenue();
								}
								
								if (interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName()!=null) {
									interviewType = interviewCard.getInterview().getInterview().getInterviewProgramCourse().getName();
								}

								if (interviewCard.getAdmAppln().getCourseBySelectedCourseId()
												.getProgram().getName() != null) {
									program = interviewCard.getAdmAppln().getCourseBySelectedCourseId()
											.getProgram().getName();
								}


							    academicyear = String.valueOf(interviewCard.getAdmAppln().getAppliedYear())
											+ "-"+ (interviewCard.getAdmAppln().getAppliedYear() + 1);
								

								String subject = interviewType + " for " + program + " "
										+ academicyear;
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_DATE, interviewDate);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_TIME, interviewTime);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_VENUE, interviewVenue);
								message = message.replace(CMSConstants.TEMPLATE_INTERVIEW_TYPE, interviewType);
								
		//Sending Mail
								sent= sendMailInterview(interviewCard.getAdmAppln().getPersonalData().getEmail(), subject,message);
								//mailToList.add(mailTo);
		
							} 
							//send SMS
							
					MobileMessaging	mobileMessage=sendSMSToStudentForAdmitCard(interviewCard,smsDesc);
					if(mobileMessage!=null)
					mobileMessagesList.add(mobileMessage);
					 }
				}
			}
			
			txn.sendSmsinBulk(mobileMessagesList);
		}
		
		return sent;
		
	}
	
	private boolean sendMailInterview(String email, String sub, String message) throws Exception {

		boolean sent = false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {	
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
//		String adminmail = prop .getProperty(CMSConstants.KNOWLEDGEPRO_ADMIN_MAIL);
		String adminmail = CMSConstants.MAIL_USERID;
		String toAddress = email;
		// MAIL TO CONSTRUCTION
		String subject = sub;
		String msg = message;

		MailTO mailto = new MailTO();
		mailto.setFromAddress(adminmail);
		mailto.setToAddress(toAddress);
		mailto.setSubject(subject);
		mailto.setMessage(msg);
		mailto.setFromName(prop.getProperty("knowledgepro.admission.studentmail.fromName"));
		// uses JMS
		// sent=CommonUtil.postMail(mailto);
	//	sent = CommonUtil.sendMail(mailto);
		sent=CommonUtil.sendMail(mailto);	
		return sent;
	}
	
	private MobileMessaging sendSMSToStudentForAdmitCard(InterviewCard interviewCard,String desc) throws Exception {
		MobileMessaging mob=null;
				if(interviewCard!=null){
					if (interviewCard.getAdmAppln() != null && interviewCard.getAdmAppln().getCourseBySelectedCourseId().getIsApplicationProcessSms()) {
						String mobileNo="";
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
							if(interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
									|| interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || interviewCard.getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
								mobileNo = "91";
							else
								mobileNo=interviewCard.getAdmAppln().getPersonalData().getMobileNo1();
						}else{
							mobileNo="91";
						}
						if(interviewCard.getAdmAppln().getPersonalData().getMobileNo2()!=null && !interviewCard.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
							mobileNo=mobileNo+interviewCard.getAdmAppln().getPersonalData().getMobileNo2();
						}
							
							String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
							String senderName=CMSConstants.SMS_SENDER_NAME;
							
							if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
								desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, String.valueOf(interviewCard.getAdmAppln().getApplnNo()));
								 mob=new MobileMessaging();
								mob.setDestinationNumber(mobileNo);
								mob.setMessageBody(desc);
								mob.setMessagePriority(3);
								mob.setSenderName(senderName);
								mob.setSenderNumber(senderNumber);
								mob.setMessageEnqueueDate(new Date());
								mob.setIsMessageSent(false);
							//	PropertyUtil.getInstance().save(mob);
							}
					}
				}
		return mob;
	}

	private String replaceMessageText(String messageTemplate, AdmAppln admAppln,HttpServletRequest request) throws Exception {

		String program = "";
		String course = "";
		String selectedCourse = "";
		String applicationNo = "";
		String academicYear = "";
		String applicantName = "";
		String dateOfBirth = "";
		String placeOfBirth = "";
		String nationality = "";
		String religion = "";
		String subreligion = "";
		String category = "";
		String gender = "";
		String email = "";
		String contextPath = "";
		String logoPath = "";
		StringBuffer currentAddress = new StringBuffer();
		StringBuffer permanentAddress = new StringBuffer();
		byte[] photo = null;

		String message = messageTemplate;

		HttpSession session = request.getSession(false);
		if (admAppln != null && admAppln.getPersonalData() != null) {
			if (admAppln.getPersonalData() != null) {
				if (admAppln.getPersonalData().getDateOfBirth() != null) {
					dateOfBirth = CommonUtil.getStringDate(admAppln
							.getPersonalData().getDateOfBirth());
				}
				if (admAppln.getPersonalData().getBirthPlace() != null) {
					placeOfBirth = admAppln.getPersonalData().getBirthPlace();
				}
				if (admAppln.getPersonalData().getFirstName() != null
						&& !admAppln.getPersonalData().getFirstName().trim()
								.isEmpty()) {
					applicantName = admAppln.getPersonalData().getFirstName();
				}
				if (admAppln.getPersonalData().getMiddleName() != null
						&& !admAppln.getPersonalData().getMiddleName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getMiddleName();
				}
				if (admAppln.getPersonalData().getLastName() != null
						&& !admAppln.getPersonalData().getLastName().trim()
								.isEmpty()) {
					applicantName = applicantName + " "
							+ admAppln.getPersonalData().getLastName();
				}
				if (admAppln.getPersonalData().getNationalityOthers() != null) {
					nationality = admAppln.getPersonalData()
							.getNationalityOthers();
				} else if (admAppln.getPersonalData().getNationality() != null) {
					nationality = admAppln.getPersonalData().getNationality()
							.getName();
				}
				if (admAppln.getPersonalData().getReligionOthers() != null) {
					religion = admAppln.getPersonalData().getReligionOthers();
				} else if (admAppln.getPersonalData().getReligion() != null) {
					religion = admAppln.getPersonalData().getReligion()
							.getName();
				}

				if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSectionOthers();
				} else if (admAppln.getPersonalData().getReligionSection() != null) {
					subreligion = admAppln.getPersonalData()
							.getReligionSection().getName();
				}

				if (admAppln.getPersonalData().getGender() != null) {
					gender = admAppln.getPersonalData().getGender();
				}
				if (admAppln.getPersonalData().getEmail() != null) {
					email = admAppln.getPersonalData().getEmail();
				}
				if (admAppln.getPersonalData().getCasteOthers() != null) {
					category = admAppln.getPersonalData().getCasteOthers();
				} else if (admAppln.getPersonalData().getCaste() != null) {
					category = admAppln.getPersonalData().getCaste().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					course = admAppln.getCourseBySelectedCourseId().getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null) {
					selectedCourse = admAppln.getCourseBySelectedCourseId()
							.getName();
				}
				if (admAppln.getCourseBySelectedCourseId() != null
						&& admAppln.getCourseBySelectedCourseId().getProgram() != null) {
					program = admAppln.getCourseBySelectedCourseId().getProgram().getName();
				}
				applicationNo = String.valueOf(admAppln.getApplnNo());
				academicYear = String.valueOf(admAppln.getAppliedYear());

				if (admAppln.getApplnDocs() != null) {
					Iterator<ApplnDoc> applnDocItr = admAppln.getApplnDocs()
							.iterator();

					while (applnDocItr.hasNext()) {
						ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
						if (applnDoc.getIsPhoto() != null
								&& applnDoc.getIsPhoto()) {
							photo = applnDoc.getDocument();

							if (session != null) {
								contextPath = request.getContextPath();
								contextPath = "<img src="
										+ contextPath
										+ "/PhotoServlet alt='Photo not available' width='150' height='150'>";
								session.setAttribute("PhotoBytes", photo);
							}
						}
					}
				}

			
				logoPath = request.getContextPath();
				logoPath = "<img src="
						+ logoPath
						+ "/LogoServlet alt='Logo not available' width='210' height='100'>";
			}

			if (admAppln.getPersonalData().getCurrentAddressLine1() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressLine1());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getCurrentAddressLine2() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressLine2());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCityByCurrentAddressCityId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCityByCurrentAddressCityId());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getStateByCurrentAddressStateId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getStateByCurrentAddressStateId().getName());
				currentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getCurrentAddressStateOthers() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressStateOthers());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData()
					.getCountryByCurrentAddressCountryId() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCountryByCurrentAddressCountryId().getName());
				currentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getCurrentAddressCountryOthers() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressCountryOthers());
				currentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCurrentAddressZipCode() != null) {
				currentAddress.append(admAppln.getPersonalData()
						.getCurrentAddressZipCode());
				currentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getParentAddressLine1() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressLine1());
				permanentAddress.append(' ');
			}

			if (admAppln.getPersonalData().getParentAddressLine2() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressLine2());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getCityByPermanentAddressCityId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getCityByPermanentAddressCityId());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getStateByParentAddressStateId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getStateByParentAddressStateId().getName());
				permanentAddress.append(' ');
			} else if (admAppln.getPersonalData().getParentAddressStateOthers() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getParentAddressStateOthers());
				permanentAddress.append(' ');
			}

			if (admAppln.getPersonalData()
					.getCountryByPermanentAddressCountryId() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getCountryByPermanentAddressCountryId().getName());
				permanentAddress.append(' ');
			} else if (admAppln.getPersonalData()
					.getPermanentAddressCountryOthers() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getPermanentAddressCountryOthers());
				permanentAddress.append(' ');
			}
			if (admAppln.getPersonalData().getPermanentAddressZipCode() != null) {
				permanentAddress.append(admAppln.getPersonalData()
						.getPermanentAddressZipCode());
				permanentAddress.append(' ');
			}
		}

		message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,
				applicantName);
		message = message.replace(CMSConstants.TEMPLATE_DOB, dateOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_POB, placeOfBirth);
		message = message.replace(CMSConstants.TEMPLATE_NATIONALITY,
				nationality);
		message = message.replace(CMSConstants.TEMPLATE_SUBRELIGION,
				subreligion);
		message = message.replace(CMSConstants.TEMPLATE_RELIGION, religion);
		message = message.replace(CMSConstants.TEMPLATE_GENDER, gender);
		message = message.replace(CMSConstants.TEMPLATE_EMAIL, email);
		message = message.replace(CMSConstants.TEMPLATE_CASTE, category);

		message = message.replace(CMSConstants.TEMPLATE_PHOTO, contextPath);
		message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
		message = message.replace(CMSConstants.TEMPLATE_PROGRAM, program);
		message = message.replace(CMSConstants.TEMPLATE_COURSE, course);
		message = message.replace(CMSConstants.TEMPLATE_SELECTED_COURSE,
				selectedCourse);
		message = message.replace(CMSConstants.TEMPLATE_APPLICATION_NO,
				applicationNo);
		message = message.replace(CMSConstants.TEMPLATE_ACADEMIC_YEAR,
				academicYear);
		message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS,
				currentAddress);
		message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS,
				permanentAddress);
		return message;

	}
	
	
	public void getPreferences(AdmissionFormForm admForm, String courseId) throws Exception {
		//AdmissionFormForm admForm=(AdmissionFormForm)form;
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Course> prefCourses=txn.getCourseForPreference(courseId);
		List<ProgramTO> prefPrograms= new ArrayList<ProgramTO>();
		List<ProgramTypeTO> prefProgramtypes= new ArrayList<ProgramTypeTO>();
		List<CourseTO> prefCoursetos= new ArrayList<CourseTO>();
		List<CourseTO> courseprefno=admForm.getPrefcourses();
		if(!prefCourses.isEmpty()){
			Iterator<Course> prefitr=prefCourses.iterator();
			if(courseprefno!=null){
			Iterator<CourseTO> prefitr1=courseprefno.iterator();
			
			while (prefitr1.hasNext()) {
			//while (prefitr.hasNext()) {
				Course prefCrs = (Course) prefitr.next();
				if (prefCrs.getIsActive()) {
					
					Program prefProg = prefCrs.getProgram();
					ProgramType prefProgtype = prefCrs.getProgram()
							.getProgramType();
					CourseTO toCrs = new CourseTO();
					
					
						CourseTO courseTO=(CourseTO) prefitr1.next();
					toCrs.setPrefNo(courseTO.getPrefNo());
					toCrs.setId(courseTO.getId());
					toCrs.setName(courseTO.getName());
					prefCoursetos.add(toCrs);
					ProgramTO toProg = new ProgramTO();
					toProg.setId(prefProg.getId());
					toProg.setName(prefProg.getName());
					prefPrograms.add(toProg);
					ProgramTypeTO toProgtype = new ProgramTypeTO();
					toProgtype.setProgramTypeId(prefProgtype.getId());
					toProgtype.setProgramTypeName(prefProgtype.getName());
					prefProgramtypes.add(toProgtype);
				}
			}
			}
			//}
			else{
				while (prefitr.hasNext()) {
					Course prefCrs = (Course) prefitr.next();
					if (prefCrs.getIsActive()) {
						
						Program prefProg = prefCrs.getProgram();
						ProgramType prefProgtype = prefCrs.getProgram()
								.getProgramType();
						CourseTO toCrs = new CourseTO();
						toCrs.setId(prefCrs.getId());
						toCrs.setName(prefCrs.getName());
						
						prefCoursetos.add(toCrs);
						ProgramTO toProg = new ProgramTO();
						toProg.setId(prefProg.getId());
						toProg.setName(prefProg.getName());
						prefPrograms.add(toProg);
						ProgramTypeTO toProgtype = new ProgramTypeTO();
						toProgtype.setProgramTypeId(prefProgtype.getId());
						toProgtype.setProgramTypeName(prefProgtype.getName());
						prefProgramtypes.add(toProgtype);
					}
				}
			}
		
	}
		List<CourseTO> uniqueCourse=removeDuplicateCourses(prefCoursetos);
		List<ProgramTO> uniqueprograms=removeDuplicatePrograms(prefPrograms);
		List<ProgramTypeTO> uniqueprogramTypes=removeDuplicateProgramTypes(prefProgramtypes);
		admForm.setPrefcourses(uniqueCourse);
		admForm.setPrefprograms(uniqueprograms);
		admForm.setPrefProgramtypes(uniqueprogramTypes);
		log.info("Exit populatePreferenceList ...");
	
}

	
	/*public boolean savePreference(AdmissionFormForm admForm) throws Exception {
		IAdmissionFormTransaction txn=AdmissionFormTransactionImpl.getInstance();
		return txn.savePreference(admForm);
	}*/
	
	
	public void getPreference(AdmissionFormForm admForm, String ugId) throws Exception {
		//AdmissionFormForm admForm=(AdmissionFormForm)form;
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<Course> prefCourses=txn.getCourseForPreferencesbyug(ugId);
		List<ProgramTO> prefPrograms= new ArrayList<ProgramTO>();
		List<ProgramTypeTO> prefProgramtypes= new ArrayList<ProgramTypeTO>();
		List<CourseTO> prefCoursetos= new ArrayList<CourseTO>();
		List<CourseTO> courseprefno=admForm.getPrefcourses();
		if(!prefCourses.isEmpty()){
			Iterator<Course> prefitr=prefCourses.iterator();
			if(courseprefno!=null){
			Iterator<CourseTO> prefitr1=courseprefno.iterator();
			
			while (prefitr1.hasNext()) {
			//while (prefitr.hasNext()) {
				Course prefCrs = (Course) prefitr.next();
				if (prefCrs.getIsActive()) {
					
					Program prefProg = prefCrs.getProgram();
					ProgramType prefProgtype = prefCrs.getProgram()
							.getProgramType();
					CourseTO toCrs = new CourseTO();
					
					
						CourseTO courseTO=(CourseTO) prefitr1.next();
					toCrs.setPrefNo(courseTO.getPrefNo());
					toCrs.setId(courseTO.getId());
					toCrs.setName(courseTO.getName());
					prefCoursetos.add(toCrs);
					ProgramTO toProg = new ProgramTO();
					toProg.setId(prefProg.getId());
					toProg.setName(prefProg.getName());
					prefPrograms.add(toProg);
					ProgramTypeTO toProgtype = new ProgramTypeTO();
					toProgtype.setProgramTypeId(prefProgtype.getId());
					toProgtype.setProgramTypeName(prefProgtype.getName());
					prefProgramtypes.add(toProgtype);
				}
			}
			}
			//}
			else{
				while (prefitr.hasNext()) {
					Course prefCrs = (Course) prefitr.next();
					if (prefCrs.getIsActive()) {
						
						Program prefProg = prefCrs.getProgram();
						ProgramType prefProgtype = prefCrs.getProgram()
								.getProgramType();
						CourseTO toCrs = new CourseTO();
						toCrs.setId(prefCrs.getId());
						toCrs.setName(prefCrs.getName());
						
						prefCoursetos.add(toCrs);
						ProgramTO toProg = new ProgramTO();
						toProg.setId(prefProg.getId());
						toProg.setName(prefProg.getName());
						prefPrograms.add(toProg);
						ProgramTypeTO toProgtype = new ProgramTypeTO();
						toProgtype.setProgramTypeId(prefProgtype.getId());
						toProgtype.setProgramTypeName(prefProgtype.getName());
						prefProgramtypes.add(toProgtype);
					}
				}
			}
		
	}
		List<CourseTO> uniqueCourse=removeDuplicateCourses(prefCoursetos);
		List<ProgramTO> uniqueprograms=removeDuplicatePrograms(prefPrograms);
		List<ProgramTypeTO> uniqueprogramTypes=removeDuplicateProgramTypes(prefProgramtypes);
		admForm.setPrefcourses(uniqueCourse);
		admForm.setPrefprograms(uniqueprograms);
		admForm.setPrefProgramtypes(uniqueprogramTypes);
		log.info("Exit populatePreferenceList ...");
	
}
	

	public void sendMailForOnlinePaymentConformation(AdmissionFormForm admForm) throws Exception {
		IAdmissionFormTransaction txn=AdmissionFormTransactionImpl.getInstance();
		List<GroupTemplate> list=null;
		//get template and replace dynamic data
		TemplateHandler temphandle=TemplateHandler.getInstance();
		
			 list= temphandle.getDuplicateCheckList(CMSConstants.PAYMENT_CONFIRMATION_TEMPLATE);
		
		if(list != null && !list.isEmpty()) {

			String desc = list.get(0).getTemplateDescription();
			//send mail to applicant

					String dob = "";
					String name = "";
					String mobileNo="";
					String eMail="";
					String course="";
					String residentCategory="";
					String candidateRefNo=admForm.getCandidateRefNo();
					String tnxRefNo=admForm.getTransactionRefNO();
						dob=admForm.getApplicantDob();
						name=admForm.getApplicantName();
						course=txn.getCouseNameByCourseId(admForm.getCourseId());
						eMail=admForm.getEmail();
						mobileNo=admForm.getMobileNo1()+" "+admForm.getMobileNo2();
						residentCategory=txn.getResidenceNameByResidanceId(admForm.getResidentCategoryForOnlineAppln());
						
						String subject= "Online payment confirmation";
						
						String message =desc;
						if(name!=null && !name.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_APPLICANT_NAME,name);
						if(dob!=null && !dob.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_DOB,dob);
						if(course!=null && !course.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_APPLIED_COURSE,course);
						if(residentCategory!=null && !residentCategory.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_STUDENT_CATEGORY,residentCategory);
						if(eMail!=null && !eMail.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_EMAIL,eMail);
						if(mobileNo!=null && !mobileNo.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_MOBILENUMBER_WITH_COUNTRYCODE,mobileNo);
						if(candidateRefNo!=null && !candidateRefNo.isEmpty())
						message = message.replace(CMSConstants.TEMPLATE_CANDIDATE_REFNUMBER,candidateRefNo);
						if(tnxRefNo!=null && !tnxRefNo.isEmpty())
							message = message.replace(CMSConstants.TEMPLATE_TRANSACTION_REFNUMBER,tnxRefNo);
						
//						send mail
						sendMail(admForm.getEmail(),subject,message);
						//print letter
						HtmlPrinter.printHtml(message);

					}
		} 
		//return sent;

	
	public Map<Integer, String> getParishByDiose(String dioid) throws Exception {
		return AdmissionFormTransactionImpl.getInstance().getParishByDiose(dioid);
	}
	
	

	
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
	public String sha1(String type,String str){
		
		
			java.security.MessageDigest digest = null;
			
			  try {
				  
			 
			  digest = java.security.MessageDigest.getInstance("SHA-1");

			  digest.reset();
			  
			  digest.update(str.getBytes("UTF-8"));
			  
			  } catch (Exception e) {
				System.out.println("Exception:"+e);
			  }
			 
			return new String(Base64.encodeBase64(digest.digest()));

		

		}
	
	/*bill desk pay u
	 * 
	 * 
	 * public boolean empty(String s)
	{
		if(s== null || s.trim().equals(""))
			return true;
		else
			return false;
	}

	
	public void mac(HttpServletRequest request){
		
		String merchant_key="JBZaLc";
		String salt="GQs7yium";
		String action1 ="";
		String base_url="https://test.payu.in";
		int error=0;
		String hashString="";
		String udf2="";
		
		Enumeration paramNames = request.getParameterNames();
		Map<String,String> params= new HashMap<String,String>();
	    	while(paramNames.hasMoreElements()) 
		{
	      		String paramName = (String)paramNames.nextElement();
	      
	      		String paramValue = request.getParameter(paramName);

			params.put(paramName,paramValue);
		}
		String txnid ="";
		if(empty(params.get("txnid"))){
			Random rand = new Random();
			String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
			txnid=hashCal("SHA-256",rndm).substring(0,20);
		}
		else
			txnid=params.get("txnid");
	        udf2 = txnid;
		String txn="abcd";
		String hash="";
		String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
		if(empty(params.get("hash")) && params.size()>0)
		{
			if( empty(params.get("key"))
				|| empty(params.get("txnid"))
				|| empty(params.get("amount"))
				|| empty(params.get("firstname"))
				|| empty(params.get("email"))
				|| empty(params.get("phone"))
				|| empty(params.get("productinfo"))
				|| empty(params.get("surl"))
				|| empty(params.get("furl"))
				|| empty(params.get("service_provider"))
		)
				
				error=1;
			else{
				String[] hashVarSeq=hashSequence.split("\\|");
				
				for(String part : hashVarSeq)
				{
					hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
					hashString=hashString.concat("|");
				}
				hashString=hashString.concat(salt);
				

				 hash=hashCal("SHA-512",hashString);
				action1=base_url.concat("/_payment");
			}
		}
		else if(!empty(params.get("hash")))
		{
			hash=params.get("hash");
			action1=base_url.concat("/_payment");
		}
			


	}
	
*/	
	public Map<Integer,String> get12thclassSubjects(String docName,String lang) throws Exception{
		return AdmissionFormTransactionImpl.getInstance().get12thclassSubject(docName,lang);
	}
	public Map<Integer,String> get12thclassLangSubjects(String docName,String lang) throws Exception{
		return AdmissionFormTransactionImpl.getInstance().get12thclassLangSubject(docName,lang);
	}
	
	public String getProgramId(int courseId) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		String year = txn.getProgramId(courseId);
		return year;
	}
	public Map<Integer,String> get12thclassSub(String docName,String Sub) throws Exception{
		return AdmissionFormTransactionImpl.getInstance().get12thclassSub(docName,Sub);
	}
	
	//raghu write new
	public int getApplicationYearByProgramtype(int programtypeId) throws Exception{
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		int year = txn.getAppliedYearForProgramType(programtypeId);
		return year;
	}
	
	public Map<Integer,String> getStreamMap() throws Exception{
		return AdmissionFormTransactionImpl.getInstance().getStreamMap();
	}
	

// this is for optional

public Map<Integer,String> get12thclassSub1(String docName,String Sub) throws Exception{
		return AdmissionFormTransactionImpl.getInstance().get12thclassSub1(docName,Sub);
	}

//vinodha
public AdmApplnTO getApplicationDetailsForMemo(AdmissionStatusForm admForm) throws Exception {
	IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
	AdmissionFormHelper helper = AdmissionFormHelper.getInstance();
	StudentIndexMark  studentIndexmark = txn.getStudentIndexMarks(admForm);
	AdmApplnTO appDetails = null;
	AdmAppln applicantDetails = null;
	if(studentIndexmark!=null)
	{
		applicantDetails = studentIndexmark.getAdmAppln();
	}else{
		CandidatePreference details = txn.getApplicationDetailsForMemoFromAdmAppln(admForm);
		if(details!=null)
			applicantDetails = details.getAdmAppln();
	}
	appDetails = helper.copyPropertiesValue(applicantDetails);
	return appDetails;
}

public boolean getPendingOnlineApp(AdmissionFormForm admForm) throws Exception {
	
	boolean isUpdated=false;
	try{
		IAdmissionFormTransaction transaction=new AdmissionFormTransactionImpl();
		isUpdated=transaction.updatePendingOnlineApp(admForm);
		/***
		 * This is for emailing the updated payment status of candidate as success, for those whose payment is done 
		 * but still hitting our ERP as pending.
		 * The content of mail is not proper, so whenever you have to implement the below code, give proper message.
		 */
		
					
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return isUpdated;
			
	}
public Boolean getApplicantDeatails(AdmissionFormForm admForm) throws Exception {
	boolean flag=false;
	try{
		IAdmissionFormTransaction transaction=new AdmissionFormTransactionImpl();
		flag=transaction.getApplicantDetails(admForm);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	return flag;
			
	}
}
