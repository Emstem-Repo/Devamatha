package com.kp.cms.helpers.admission;


import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.to.admission.InterviewScheduleTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class AdmissionStatusHelper {
	private static final Log log = LogFactory.getLog(AdmissionStatusHelper.class);
	public static volatile AdmissionStatusHelper admissionStatusHelper = null;
	public static final String PHOTOBYTES="PhotoBytes";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static AdmissionStatusHelper getInstance() {
		
	if (admissionStatusHelper == null) {
		admissionStatusHelper = new AdmissionStatusHelper();
		return admissionStatusHelper;
	}
	return admissionStatusHelper;
}
	/**
	 * 
	 * @param admissionStatusForm 
	 * @param Populates the AdmApplnBO List to Admission Status TO
	 * @return
	 */
	public List<AdmissionStatusTO> populateAdmApplnBOtoTO(final List<AdmAppln> admApplnBO, AdmissionStatusForm admissionStatusForm)throws Exception
	 {
		log.info("Inside of populateAdmApplnBOtoTO of AdmissionStatusHelper");
			AdmissionStatusTO admissionStatusTO = null; 
			InterviewResultTO interviewResultTO = null;
			List<AdmissionStatusTO> admAppln = new ArrayList<AdmissionStatusTO>();			
			if (admApplnBO != null) {
				IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
				Iterator<AdmAppln> iterator = admApplnBO.iterator();
				while (iterator.hasNext()) {
				AdmAppln appln = iterator.next();
				admissionStatusTO = new AdmissionStatusTO();
				
				/**
				 * Checks for the columns. If does not contain  null values then set those values from BO to TO
				 */
				admissionStatusTO.setId(appln.getId());
				if(appln.getIsBypassed()!= null && appln.getIsBypassed()){
					admissionStatusTO.setByPassed(appln.getIsBypassed());
				}
				/* code Added for courses having intervie_selection_schedule data defined---- code starts here*/
			/*	if(appln.getInterScheduleSelection()!=null && appln.getInterScheduleSelection().getId()>0 && appln.getPersonalData()!=null && appln.getPersonalData().getDateOfBirth()!=null 
						&& appln.getPersonalData().getId()!=0 && appln.getIsCancelled()!=null){
					admissionStatusTO.setApplicationNo(appln.getApplnNo());
					admissionStatusTO.setDateOfBirth(CommonUtil.getStringDate(appln.getPersonalData().getDateOfBirth()));
					admissionStatusTO.setPersonalDataId(appln.getPersonalData().getId());
					boolean isFinalMeritApproved = false;
					if(appln.getIsFinalMeritApproved()!=null){
						isFinalMeritApproved = appln.getIsFinalMeritApproved();
					}
					admissionStatusTO.setIsSelected(convertBoolValueIsSelected(appln.getIsSelected(),isFinalMeritApproved));	
					admissionStatusTO.setInterviewSelectionSchedule(String.valueOf(appln.getInterScheduleSelection().getId()));
				}*/
				/* code Added for courses having intervie_selection_schedule data defined----code ends here*/
				else if(appln.getIsSelected()!=null && appln.getPersonalData()!=null && appln.getPersonalData().getDateOfBirth()!=null 
				&& appln.getPersonalData().getId()!=0 && appln.getIsCancelled()!=null){
						
						admissionStatusTO.setApplicationNo(appln.getApplnNo());
						//applied year added by manu
						admissionStatusTO.setAppliedYear(appln.getAppliedYear());

						admissionStatusTO.setDateOfBirth(CommonUtil.getStringDate(appln.getPersonalData().getDateOfBirth()));
						admissionStatusTO.setPersonalDataId(appln.getPersonalData().getId());
						boolean isFinalMeritApproved = false;
						if(appln.getIsFinalMeritApproved()!=null){
							isFinalMeritApproved = appln.getIsFinalMeritApproved();
						}
						admissionStatusTO.setIsSelected(convertBoolValueIsSelected(appln.getIsSelected(),isFinalMeritApproved));				
				}
				if(appln.getPersonalData()!=null && appln.getPersonalData().getEmail()!=null){
					admissionStatusTO.setEmail(appln.getPersonalData().getEmail());
				}									
				admissionStatusTO.setCancelled(appln.getIsCancelled());
				if(appln.getAppliedYear()!=0){
					admissionStatusTO.setAppliedYear(appln.getAppliedYear());
				}
				if(appln.getCourseBySelectedCourseId() != null){
					//admissionStatusTO.setCourseId(appln.getCourse().getId());
					admissionStatusTO.setCourseId(appln.getCourseBySelectedCourseId().getId());
				}
				Set<InterviewResult> intResultSet=appln.getInterviewResults();
				Set<InterviewResultTO> interviewResultTOSet=new HashSet<InterviewResultTO>();
				Iterator<InterviewResult> iterator1=intResultSet.iterator();
				while (iterator1.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) iterator1.next();
						if(interviewResult!=null)
							{
							interviewResultTO=new InterviewResultTO();
							InterviewProgramCourseTO interviewProgramCourseTO=new InterviewProgramCourseTO();
							if(interviewResult.getInterviewStatus()!=null && interviewResult.getInterviewProgramCourse()!=null){
							if(interviewResult.getInterviewStatus().getName()!=null && !interviewResult.getInterviewStatus().getName().isEmpty())
							{
							interviewResultTO.setInterviewStatus(interviewResult.getInterviewStatus().getName());
							}
							if(interviewResult.getId()!=0){
							interviewResultTO.setId(interviewResult.getId());
							}
							if(interviewResult.getInterviewProgramCourse().getName()!=null && !interviewResult.getInterviewProgramCourse().getName().isEmpty()){
							interviewProgramCourseTO.setName(interviewResult.getInterviewProgramCourse().getName());
							}
							if(interviewResult.getInterviewProgramCourse().getId()!=0){
							interviewProgramCourseTO.setId(interviewResult.getInterviewProgramCourse().getId());
							}
							
							interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
							interviewResultTOSet.add(interviewResultTO);
							}
						}	
				}
				admissionStatusTO.setAdmStatus(appln.getAdmStatus());
				admissionStatusTO.setInterviewResultTO(interviewResultTOSet);
				Set<Student> studentSet = appln.getStudents();
				Iterator<Student> stItr = studentSet.iterator();
				boolean isAdmitted = false;
				while (stItr.hasNext()) {
					Student student = (Student) stItr.next();
					if(student.getIsAdmitted()!=null)
					isAdmitted = student.getIsAdmitted();
				}
				admissionStatusTO.setAdmitted(isAdmitted);
				
				//raghu
				if(appln.getMode()!=null && appln.getMode().equalsIgnoreCase("CHALLAN")){
				if(appln.getIsChallanRecieved()!=null && appln.getIsChallanRecieved()){
					admissionStatusForm.setIsChallanRecieved(1);
				}else{
					admissionStatusForm.setIsChallanRecieved(0);
				}
				}else if(appln.getMode()!=null && appln.getMode().equalsIgnoreCase("NEFT")){
					if(appln.getIsChallanRecieved()!=null && appln.getIsChallanRecieved()){
						admissionStatusForm.setIsChallanRecieved(1);
					}else{
						admissionStatusForm.setIsChallanRecieved(0);
					}
				}else if(appln.getApplnNo()!=0 && appln.getMode()!=null && appln.getMode().equalsIgnoreCase("Online")){
					
					admissionStatusForm.setIsChallanRecieved(1);
					
				}
				
				//vibin for status change
				//raghu add applied year check
				Integer maxallotment = txn.getmaxallotment(appln.getCourse().getProgram().getProgramType().getId(),appln.getAppliedYear());
		         int mxal = 0;
		        if(maxallotment!=null){
		         mxal = maxallotment.intValue();
		        }
		        admissionStatusForm.setMaxallotment(mxal);
		        Integer sureCourseId = 0;
		        Integer prefNo = 100;
		        
				StudentCourseAllotment allotmentdata = txn.getallotmentdetails(appln.getId(),mxal);
				
			    if(allotmentdata!=null){
			       sureCourseId = allotmentdata.getCourse().getId();
     		       admissionStatusTO.setAllotmentflag(true);
     		       prefNo = allotmentdata.getPrefNo();
			    	
			    }
			    
			      List<CandidatePreferenceTO> prefto = new ArrayList<CandidatePreferenceTO>();
				  AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
				  prefto =   helper.copyPropertiesValuepref(appln.getId());

				Iterator<CandidatePreferenceTO> preftoItr = prefto.iterator();
				List<CandidatePreferenceTO> memoList = new LinkedList<CandidatePreferenceTO>();
				while (preftoItr.hasNext()) {
					CandidatePreferenceTO prefTO = (CandidatePreferenceTO) preftoItr.next();
					CandidatePreferenceTO memoTo = new CandidatePreferenceTO();
					memoTo.setCoursId(prefTO.getCoursId());
					memoTo.setCoursName(prefTO.getCoursName());
					memoTo.setPrefNo(prefTO.getPrefNo());
			        
					if(Integer.parseInt(prefTO.getCoursId())==sureCourseId){
						if(allotmentdata.getIsGeneral()){
							memoTo.setCategory("General");
						}else{
							memoTo.setCategory(appln.getPersonalData().getReligionSection().getName());
						}
						
						memoTo.setStatus("Alloted");
					}else{
						memoTo.setCategory("General");
						memoTo.setStatus("Waiting List");
				    }
					// fess for oec st sc
		            if(appln.getPersonalData().getReligionSection().getId()==2 || appln.getPersonalData().getReligionSection().getId()==3 ||appln.getPersonalData().getReligionSection().getId()==12)
				        memoTo.setFees(prefTO.getCastefees());
		            else
		            	memoTo.setFees(prefTO.getGeneralfees());
		            memoTo.setDateAndTime(prefTO.getDateAndTime());
		            
		            if(prefTO.getPrefNo()<=prefNo)
		            memoList.add(memoTo);
			   }
		    if(memoList!=null){
		    	admissionStatusTO.setMemoList(memoList);
		    }				
				
				
				
				
				
				admAppln.add(admissionStatusTO);
					CandidatePGIDetails details = txn.getCandidateDetails(appln.getId());
					TemplateHandler temphandle=TemplateHandler.getInstance();
					List<GroupTemplate> list= temphandle.getDuplicateCheckList("Online payment Acknowledgement Slip");
					
					if(list != null && !list.isEmpty()) {
						if(details != null){
							admissionStatusForm.setOnlineAcknowledgement(true);
						}
					}
				}
			}
			log.info("End of populateAdmApplnBOtoTO of AdmissionStatusHelper");
			return admAppln;
	}
	
	/**
	 * 
	 * @param interviewResult
	 * @return Populates InterviewResultTo object from InterviewResultBO object
	 */
	
	public InterviewResultTO populategetInterviewResultBOtoTO(InterviewResult interviewResult)throws Exception
	{
	log.info("Inside of populategetInterviewResultBOtoTO of AdmissionStatusHelper");
	AdmApplnTO admApplnTO = null;
	PersonalDataTO personalDataTO = null;
	//Checks for the null fields
	if(interviewResult!=null){	
	InterviewResultTO interviewResultTO=new InterviewResultTO();
	if(interviewResult.getId()!=0){
	interviewResultTO.setId(interviewResult.getId());
	}
	if(interviewResult.getAdmAppln()!=null && interviewResult.getAdmAppln().getPersonalData()!=null
	&& interviewResult.getAdmAppln().getPersonalData().getEmail()!=null){
		admApplnTO = new AdmApplnTO();
		personalDataTO = new PersonalDataTO();
		admApplnTO.setId(interviewResult.getAdmAppln().getId());
		personalDataTO.setEmail(interviewResult.getAdmAppln().getPersonalData().getEmail());
		admApplnTO.setPersonalData(personalDataTO);
		interviewResultTO.setAdmApplnTO(admApplnTO);
	}
	if(interviewResult.getAdmAppln() != null && interviewResult.getAdmAppln().getCourseBySelectedCourseId() !=null ){
//		interviewResultTO.setCourseId(String.valueOf(interviewResult.getAdmAppln().getCourse().getId()));
		interviewResultTO.setCourseId(String.valueOf(interviewResult.getAdmAppln().getCourseBySelectedCourseId().getId()));
	}
	if(interviewResult.getInterviewProgramCourse()!=null && interviewResult.getInterviewProgramCourse().getName()!=null
	&& !interviewResult.getInterviewProgramCourse().getName().startsWith(CMSConstants.CALL_FOR)){
	interviewResultTO.setInterviewStatus(CMSConstants.RESULT_UNDER_PROCESS);
	}
	InterviewProgramCourseTO interviewProgramCourseTO=new InterviewProgramCourseTO();
	if(interviewResult.getInterviewProgramCourse()!=null)
	{
	if(interviewResult.getInterviewProgramCourse().getName()!=null && !interviewResult.getInterviewProgramCourse().getName().isEmpty()){
	interviewProgramCourseTO.setName(interviewResult.getInterviewProgramCourse().getName());
	}
	if(interviewResult.getInterviewProgramCourse().getId()!=0){
	interviewProgramCourseTO.setId(interviewResult.getInterviewProgramCourse().getId());
	}
	interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
	}
	return 	interviewResultTO;
	}
	log.info("End of populategetInterviewResultBOtoTO of AdmissionStatusHelper");
		return null;
	}
	
	
	/**
	 * 
	 * @param Converts boolean value getting isSelected 0 or 1 
	 * @return
	 */
	
	private static String convertBoolValueIsSelected(Boolean value, boolean isFinalMeritApproved) throws Exception{
		log.info("Inside of convertBoolValueIsSelected of AdmissionStatusHelper");
		if (value.booleanValue() && isFinalMeritApproved) {
			return CMSConstants.SELECTED_FOR_ADMISSION;
		} 
		else{
			log.info("End of convertBoolValueIsSelected of AdmissionStatusHelper");
			return CMSConstants.NOT_SELECTED_FOR_ADMISSION;			
		}
	}
	
	/**
	 * 
	 * @param interviewCard
	 * @return
	 * @throws Exception
	 */
	public static List<InterviewCardTO> getInterviewCardTO(List<InterviewCard> interviewCard)throws Exception{
		 log.info("Start of getInterviewCardTO of AdmissionStatusHelper");
		InterviewCardTO interviewCardTO = null;
		List<InterviewCardTO> interviewCardTOList = new ArrayList<InterviewCardTO>();
		InterviewCard iCard = null;
		if(interviewCard!=null){
			Iterator<InterviewCard> itr = interviewCard.iterator();		
			while(itr.hasNext()){
				AdmApplnTO admApplnTO = new AdmApplnTO();
				PersonalDataTO personalDataTO =  new PersonalDataTO();
				CourseTO courseTO = new CourseTO();
				InterviewScheduleTO interviewScheduleTO = new InterviewScheduleTO();
	
			iCard = (InterviewCard) itr.next();
			interviewCardTO = new InterviewCardTO();
		if(iCard!=null){
			if(iCard.getAdmAppln()!=null && Integer.valueOf(iCard.getAdmAppln().getApplnNo())!=null){
				admApplnTO.setApplnNo(iCard.getAdmAppln().getApplnNo());
			}			
			if(iCard.getAdmAppln()!=null && iCard.getAdmAppln().getPersonalData()!=null && iCard.getAdmAppln().getPersonalData().getFirstName()!=null){
				personalDataTO.setFirstName(iCard.getAdmAppln().getPersonalData().getFirstName());
				admApplnTO.setPersonalData(personalDataTO);
			}
//			if(iCard.getAdmAppln()!=null && iCard.getAdmAppln().getCourse()!=null && iCard.getAdmAppln().getCourse().getName()!=null){
			if(iCard.getAdmAppln()!=null && iCard.getAdmAppln().getCourseBySelectedCourseId()!=null && iCard.getAdmAppln().getCourseBySelectedCourseId().getName()!=null){
				//courseTO.setCode(iCard.getAdmAppln().getCourse().getName());
				courseTO.setCode(iCard.getAdmAppln().getCourseBySelectedCourseId().getName());
				admApplnTO.setCourse(courseTO);
			}			
			interviewCardTO.setAdmApplnTO(admApplnTO);
			if(iCard.getInterview()!=null && iCard.getInterview().getDate()!=null){
				interviewScheduleTO.setDate(CommonUtil.getStringDate(iCard.getInterview().getDate()));
			}
			interviewCardTO.setInterview(interviewScheduleTO);
			interviewCardTO.setTime(iCard.getTime());
			if(iCard.getInterview()!=null && iCard.getInterview().getInterview()!=null && iCard.getInterview().getInterview().getInterviewProgramCourse()!=null && iCard.getInterview().getInterview().getInterviewProgramCourse().getName()!=null){
			interviewCardTO.setInterviewType(String.valueOf(iCard.getInterview().getInterview().getInterviewProgramCourse().getName()));
			}
		}
			interviewCardTOList.add(interviewCardTO);
			}	
		}
		 log.info("End of getInterviewCardTO of AdmissionStatusHelper");
		return interviewCardTOList;
	}
	
	/**
	 * 
	 * @param templateDescription
	 * @param admitCardList
	 * @param request
	 * @param admitCardHistoryList 
	 * @return
	 * @throws Exception
	 */
	public static String generateAdmitCard(String templateDescription,
			List<InterviewCard> admitCardList, HttpServletRequest request, List<InterviewCardHistory> admitCardHistoryList, File barCodeImgFile)
			throws Exception {
		log.info("Inside of generateAdmitCard of AdmissionStatusHelper");
		String program = "";
		String course = "";
		String selectedCourse = "";
		String applicationNo = "";
		String interviewVenue = "";
		String academicYear = "";
		String applicantName = "";
		String dateOfBirth = "";
		String placeOfBirth = "";
		String nationality = "";
		String religion = "";
		String subreligion = "";
		String residentCategory = "";
		String category = "";
		String gender = "";
		String email = "";
		String finalTemplate = "";
		String interviewType = "";
		String interviewDate = "";
		String interviewTime = "";
		String contextPath = "";
		String logoPath = "";
		String seatNo = "";
		String examCenter = "";
		StringBuffer currentAddress = new StringBuffer();
		StringBuffer permanentAddress = new StringBuffer();
		String examCenterAddress1  = "";
		String examCenterAddress2 = "";
		String examCenterAddress3 = "";
		String examCenterAddress4 = "";
		String seatNoPrefix = "";
		byte[] logo = null;
		String mode="";
		String barCode = "";
		byte[] barCodeByte = null;
		HttpSession session = request.getSession(false);
		if (templateDescription != null
				&& !templateDescription.trim().isEmpty()
				&& admitCardList != null && !admitCardList.isEmpty()) {

			InterviewCard applicantDetails = admitCardList.get(0);
			
			if (applicantDetails != null) {
				if (applicantDetails.getAdmAppln() != null
						&& applicantDetails.getAdmAppln().getPersonalData() != null) {
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getDateOfBirth() != null) {
						dateOfBirth = CommonUtil.getStringDate(applicantDetails
								.getAdmAppln().getPersonalData()
								.getDateOfBirth());
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getBirthPlace() != null) {
						placeOfBirth = applicantDetails.getAdmAppln()
								.getPersonalData().getBirthPlace();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getFirstName() != null
							&& !applicantDetails.getAdmAppln()
									.getPersonalData().getFirstName().trim()
									.isEmpty()) {
						applicantName = applicantDetails.getAdmAppln()
								.getPersonalData().getFirstName();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getMiddleName() != null
							&& !applicantDetails.getAdmAppln()
									.getPersonalData().getMiddleName().trim()
									.isEmpty()) {
						applicantName = applicantName
								+ " "
								+ applicantDetails.getAdmAppln()
										.getPersonalData().getMiddleName();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getLastName() != null
							&& !applicantDetails.getAdmAppln()
									.getPersonalData().getLastName().trim()
									.isEmpty()) {
						applicantName = applicantName
								+ " "
								+ applicantDetails.getAdmAppln()
										.getPersonalData().getLastName();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getNationalityOthers() != null) {
						nationality = applicantDetails.getAdmAppln()
								.getPersonalData().getNationalityOthers();
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getNationality() != null) {
						nationality = applicantDetails.getAdmAppln()
								.getPersonalData().getNationality().getName();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getReligionOthers() != null) {
						religion = applicantDetails.getAdmAppln()
								.getPersonalData().getReligionOthers();
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getReligion() != null) {
						religion = applicantDetails.getAdmAppln()
								.getPersonalData().getReligion().getName();
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getReligionSectionOthers() != null) {
						subreligion = applicantDetails.getAdmAppln()
								.getPersonalData().getReligionSectionOthers();
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getReligionSection() != null) {
						subreligion = applicantDetails.getAdmAppln()
								.getPersonalData().getReligionSection()
								.getName();
					}

					if (applicantDetails.getAdmAppln().getPersonalData().getResidentCategory() != null
							&& applicantDetails.getAdmAppln().getPersonalData().getResidentCategory().getName() != null){
						residentCategory = applicantDetails.getAdmAppln().getPersonalData()
											.getResidentCategory().getName();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getGender() != null) {
						gender = applicantDetails.getAdmAppln()
								.getPersonalData().getGender();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getEmail() != null) {
						email = applicantDetails.getAdmAppln()
								.getPersonalData().getEmail();
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCasteOthers() != null) {
						category = applicantDetails.getAdmAppln()
								.getPersonalData().getCasteOthers();
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getCaste() != null) {
						category = applicantDetails.getAdmAppln()
								.getPersonalData().getCaste().getName();
					}
					if (applicantDetails.getAdmAppln().getCourseBySelectedCourseId() != null) {
						/*course = applicantDetails.getAdmAppln().getCourse()
								.getName();*/
						course = applicantDetails.getAdmAppln().getCourseBySelectedCourseId().getName();
					}
					if (applicantDetails.getAdmAppln().getCourseBySelectedCourseId() != null) {
						selectedCourse = applicantDetails.getAdmAppln().getCourseBySelectedCourseId()
								.getName();
					}
					if (applicantDetails.getAdmAppln().getCourseBySelectedCourseId() != null
							&& applicantDetails.getAdmAppln().getCourseBySelectedCourseId()
									.getProgram() != null) {
						/*program = applicantDetails.getAdmAppln().getCourse()
								.getProgram().getName();*/
						program = applicantDetails.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName();
					}
					applicationNo = String.valueOf(applicantDetails
							.getAdmAppln().getApplnNo());
					academicYear = String.valueOf(applicantDetails
							.getAdmAppln().getAppliedYear());

					/*if (applicantDetails.getAdmAppln().getApplnDocs() != null) {
						Iterator<ApplnDoc> applnDocItr = applicantDetails
								.getAdmAppln().getApplnDocs().iterator();

						while (applnDocItr.hasNext()) {
							ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
							if (applnDoc.getIsPhoto() != null
									&& applnDoc.getIsPhoto()) {
								photo = applnDoc.getDocument();
								
								if (session != null) {
									contextPath = request.getContextPath();
									contextPath = "<img src="
											+ contextPath
											+ "/PhotoServlet alt='Photo not available' width='150' height='150' >";
									session.setAttribute(
											AdmissionStatusHelper.PHOTOBYTES,
											photo);
								}
							}
						}
					}*/
					if(applicantDetails.getAdmAppln() != null && applicantDetails.getAdmAppln().getStudents() != null){
						for (Student student : applicantDetails.getAdmAppln().getStudents()) {
						
							contextPath = "<img src='images/StudentPhotos/" + student.getId() + ".jpg?"+applicantDetails.getAdmAppln().getLastModifiedDate()+"' alt='Photo not available' width='150' height='150' >";
						}
					}else{
						contextPath = "<img src='images/photoblank.gif' width='150' height='150' >";
					}
					
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCurrentAddressLine1() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getCurrentAddressLine1());
						currentAddress.append(' ');
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCurrentAddressLine2() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getCurrentAddressLine2());
						currentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCityByCurrentAddressCityId() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getCityByCurrentAddressCityId());
						currentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getStateByCurrentAddressStateId() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData()
								.getStateByCurrentAddressStateId().getName());
						currentAddress.append(' ');
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getCurrentAddressStateOthers() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getCurrentAddressStateOthers());
						currentAddress.append(' ');
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCountryByCurrentAddressCountryId() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData()
								.getCountryByCurrentAddressCountryId().getName());
						currentAddress.append(' ');
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getCurrentAddressCountryOthers() != null) {
						currentAddress
								.append(applicantDetails.getAdmAppln()
										.getPersonalData()
										.getCurrentAddressCountryOthers());
						currentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCurrentAddressZipCode() != null) {
						currentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getCurrentAddressZipCode());
						currentAddress.append(' ');
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getParentAddressLine1() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getParentAddressLine1());
						permanentAddress.append(' ');
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getParentAddressLine2() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getParentAddressLine2());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCityByPermanentAddressCityId() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData()
								.getCityByPermanentAddressCityId());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getStateByParentAddressStateId() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getStateByParentAddressStateId()
								.getName());
						permanentAddress.append(' ');
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getParentAddressStateOthers() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getParentAddressStateOthers());
						permanentAddress.append(' ');
					}

					if (applicantDetails.getAdmAppln().getPersonalData()
							.getCountryByPermanentAddressCountryId() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData()
								.getCountryByPermanentAddressCountryId().getName());
						permanentAddress.append(' ');
					} else if (applicantDetails.getAdmAppln().getPersonalData()
							.getPermanentAddressCountryOthers() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData()
								.getPermanentAddressCountryOthers());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getAdmAppln().getPersonalData()
							.getPermanentAddressZipCode() != null) {
						permanentAddress.append(applicantDetails.getAdmAppln()
								.getPersonalData().getPermanentAddressZipCode());
						permanentAddress.append(' ');
					}
					if(applicantDetails.getAdmAppln().getSeatNo()!= null){
						seatNo = applicantDetails.getAdmAppln().getSeatNo();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getCenter()!= null){
						examCenter = applicantDetails.getAdmAppln().getExamCenter().getCenter();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getAddress1()!= null){
						examCenterAddress1  = applicantDetails.getAdmAppln().getExamCenter().getAddress1();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getAddress2()!= null){
						examCenterAddress2 = applicantDetails.getAdmAppln().getExamCenter().getAddress2();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getAddress3()!= null){
						examCenterAddress3 = applicantDetails.getAdmAppln().getExamCenter().getAddress3();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getAddress4()!= null){
						examCenterAddress4 = applicantDetails.getAdmAppln().getExamCenter().getAddress4();
					}
					if(applicantDetails.getAdmAppln().getExamCenter()!= null && applicantDetails.getAdmAppln().getExamCenter().getSeatNoPrefix()!= null){
						seatNoPrefix = applicantDetails.getAdmAppln().getExamCenter().getSeatNoPrefix();
					}
					if(applicantDetails.getAdmAppln().getMode()!=null){
						mode=applicantDetails.getAdmAppln().getMode();
					}
					
				}
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
		
				if (applicantDetails.getInterview() != null) {
					interviewTime = applicantDetails.getTime();
					if (applicantDetails.getInterview().getDate() != null) {
						interviewDate = CommonUtil
								.getStringDate(applicantDetails.getInterview()
										.getDate());
						interviewVenue = applicantDetails.getInterview()
								.getVenue();
					}
					if (applicantDetails.getInterview().getInterview() != null
							&& applicantDetails.getInterview().getInterview()
									.getInterviewProgramCourse() != null) {
						interviewType = applicantDetails.getInterview()
								.getInterview().getInterviewProgramCourse()
								.getName();
					}
				}
				
			}

			// replace dynamic data
			finalTemplate = templateDescription;
			//Added By manu For BarCode
			barCodeByte = new byte[(int)barCodeImgFile.length ()];
			FileInputStream fis=new FileInputStream(barCodeImgFile);
			// convert image into byte array
			fis.read(barCodeByte);
			session.setAttribute("barCodeBytes", barCodeByte);
			barCode = request.getContextPath();
			barCode = "<img src="+ barCode+ "/BarCodeServlet alt='Barcode  not available' width='137' height='37' >";
			if(CMSConstants.TEMPLATE_BARCODE!=null && !CMSConstants.TEMPLATE_BARCODE.isEmpty()){
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_BARCODE, barCode);
			}
			//end
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_APPLICANT_NAME, applicantName);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_DOB,
					dateOfBirth);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_POB,
					placeOfBirth);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_NATIONALITY, nationality);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_SUBRELIGION, subreligion);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_RESIDENTCATEGORY, residentCategory);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_RELIGION, religion);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_GENDER,
					gender);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EMAIL,
					email);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_CASTE,
					category);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_INTERVIEW_TYPE, interviewType);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_INTERVIEW_DATE, interviewDate);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_INTERVIEW_TIME, interviewTime);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_PHOTO,
					contextPath);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_LOGO,
					logoPath);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_PROGRAM, program);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_COURSE,
					course);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_SELECTED_COURSE,
					selectedCourse);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_APPLICATION_NO, applicationNo);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_ACADEMIC_YEAR, academicYear);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_INTERVIEW_VENUE, interviewVenue);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_CURRENT_ADDRESS, currentAddress);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_PERMANENT_ADDRESS, permanentAddress);
			if(seatNo!= null && !seatNo.trim().isEmpty()){
				finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_SEAT_NO, String.format("%04d", Integer.parseInt(seatNo)));
			}
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_NAME, examCenter);	
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_ADDRESS, examCenterAddress1 + "<br/>" + examCenterAddress2 + "<br/>" +  examCenterAddress3 + "<br/>" + examCenterAddress4);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_SEAT_NO_PREFIX, seatNoPrefix);
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_MODE, mode);
			/*code added by sudhir*/
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_INTERVIEW_CARD_CREATED_DATE, CommonUtil.formatDates(applicantDetails.getLastModifiedDate()));
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_INTERVIEW_CARD_CREATED_TIME, CommonUtil.getTimeByDate(applicantDetails.getLastModifiedDate()));
			int count = 0;
			if(admitCardHistoryList!=null && !admitCardHistoryList.isEmpty()){
				 String s = "<div align='left'><b>Previous E-Admit Card Details</b></div><div>&nbsp;&nbsp;&nbsp; Call History &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  Admit Card generated Date & Time &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Previous Selection Process Date & Time</div><br> ";
				Iterator<InterviewCardHistory> iterator = admitCardHistoryList.iterator();
				int callHistory = admitCardHistoryList.size();
				String admitCardGeneratedOn = "";
				String previousSelection = "";
				String admitCardGenerationOnTime = "";
				String previousSelectionTime = "";
				while (iterator.hasNext()) {
					InterviewCardHistory interviewCardHistory = (InterviewCardHistory) iterator.next();
					admitCardGeneratedOn = CommonUtil.formatDates(interviewCardHistory.getLastModifiedDate());
					previousSelection = CommonUtil.formatDates(interviewCardHistory.getInterview().getDate());
					admitCardGenerationOnTime = CommonUtil.getTimeByDate(interviewCardHistory.getLastModifiedDate());
					previousSelectionTime = interviewCardHistory.getTime();
					s = s +"<div>"+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+callHistory+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
					+admitCardGeneratedOn+"&nbsp;&nbsp;-&nbsp;&nbsp;"+admitCardGenerationOnTime+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+previousSelection+"&nbsp;&nbsp;-&nbsp;&nbsp;"+previousSelectionTime+"</div><br>";
					count++;
					callHistory = callHistory -1;
				}
				finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_INTERVIEW_SCHEDULED_HISTORY, s);
			}else{
				finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_INTERVIEW_SCHEDULED_HISTORY, "");
			}
			count = count + 1;
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_INTERVIEW_SCHEDULED_COUNT, String.valueOf(count));
			/*code added by sudhir*/
		}
		log.info("End of generateAdmitCard of AdmissionStatusHelper");
		return finalTemplate;
	}
	
	/**
	 * Used to get the Interview Status
	 * Checks for the main round as well as subrounds of interview.
	 * Checks for interview card generation.
	 */
	public AdmissionStatusTO getInterviewStatus(AdmAppln admAppln)throws Exception{
		log.info("Begin of getInterviewStatus of AdmissionStatusHelper");
		AdmissionStatusTO statusTO = new AdmissionStatusTO();
		//get interview selecteds for main round check
		//get interview results for subround check
		
		Set<InterviewSelected> interviewSelectedSet = admAppln.getInterviewSelecteds();
		Set<InterviewResult> interviewresultSet = admAppln.getInterviewResults();
		boolean subroundexist=false;
		boolean cardgenerated=false;
		InterviewSelected oldInterviewSelected = null;
		
		String status="";
		if(interviewSelectedSet!=null && !interviewSelectedSet.isEmpty() && interviewSelectedSet.size()>1){
			
			//Used to get the last interviewselected ID
			//Gets the last main round Id
			//Mainly used to know the last round status. If no then get the old round status
			List<InterviewSelected> interviewSelectedList = new ArrayList<InterviewSelected>();
			interviewSelectedList.addAll(interviewSelectedSet);
			//Sort all main rounds based on the interviewselected Id
			Collections.sort(interviewSelectedList);
			
			InterviewSelected lastMainRoundRecord = interviewSelectedList.get(interviewSelectedSet.size()-1);
			int lastMainRoundBOId = 0;
			int lastMainRoundId = 0;
			
			if(lastMainRoundRecord!=null){
				lastMainRoundBOId = lastMainRoundRecord.getId();
				if(lastMainRoundRecord.getInterviewProgramCourse()!=null){
				lastMainRoundId = lastMainRoundRecord.getInterviewProgramCourse().getId();
				}
			}			
			
			Iterator<InterviewSelected> intvwIterator = interviewSelectedList.iterator();
			while (intvwIterator.hasNext()) {
				InterviewSelected interviewSelected = intvwIterator.next();
								
				if(interviewSelected.getInterviewProgramCourse()!= null){
					
					int currentMainRoundId = interviewSelected.getId();
					
					if(currentMainRoundId == lastMainRoundBOId){						
					
					Iterator<InterviewResult> resIterator = interviewresultSet.iterator();
					while (resIterator.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) resIterator.next();
						//check subround exists or not
						if(interviewResult.getInterviewProgramCourse()!=null && interviewResult.getInterviewProgramCourse().getId()==lastMainRoundId){
							subroundexist=true;
							status=CMSConstants.RESULT_UNDER_PROCESS;
							if(interviewResult.getAdmAppln()!=null){
								if(interviewResult.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(interviewResult.getAdmAppln().getApplnNo());
								}
								//applied year added by manu
								if(interviewResult.getAdmAppln().getAppliedYear()!=null && interviewResult.getAdmAppln().getAppliedYear()!=0){
									statusTO.setAppliedYear(interviewResult.getAdmAppln().getAppliedYear());
									}
								if(interviewResult.getAdmAppln().getPersonalData()!=null){
									if(interviewResult.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewResult.getAdmAppln().getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(interviewResult.getAdmAppln().getPersonalData().getEmail()!=null){
										statusTO.setEmail(interviewResult.getAdmAppln().getPersonalData().getEmail());
									}
								}
								if(interviewResult.getAdmAppln().getCourseBySelectedCourseId()!=null){
//									statusTO.setCourseId(interviewResult.getAdmAppln().getCourse().getId());
									statusTO.setCourseId(interviewResult.getAdmAppln().getCourseBySelectedCourseId().getId());
								}
							}
							if(interviewResult.getInterviewProgramCourse()!=null){
								statusTO.setInterviewProgramCourseId(interviewResult.getInterviewProgramCourse().getId());
								if(interviewResult.getInterviewProgramCourse().getName()!=null){
									statusTO.setInterviewStatus(interviewResult.getInterviewProgramCourse().getName() + " " + status);
								}
							}							
							return statusTO;
						}
					}
					
					if(!subroundexist){
						cardgenerated=interviewSelected.getIsCardGenerated();
						if(cardgenerated)
						{
							//Call for Interview
							status=CMSConstants.CALL_FOR;
							
							if(interviewSelected.getAdmAppln()!=null){
								if(interviewSelected.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(interviewSelected.getAdmAppln().getApplnNo());
								}
								//applied year added by manu.
								if(interviewSelected.getAdmAppln().getAppliedYear()!=null && interviewSelected.getAdmAppln().getAppliedYear()!=0){
								statusTO.setAppliedYear(interviewSelected.getAdmAppln().getAppliedYear());
								}
								if(interviewSelected.getAdmAppln().getPersonalData()!=null){
									if(interviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(interviewSelected.getAdmAppln().getPersonalData().getEmail()!=null){
										statusTO.setEmail(interviewSelected.getAdmAppln().getPersonalData().getEmail());
									}
								}
								if(interviewSelected.getAdmAppln().getCourseBySelectedCourseId()!=null){
//									statusTO.setCourseId(interviewSelected.getAdmAppln().getCourse().getId());
									statusTO.setCourseId(interviewSelected.getAdmAppln().getCourseBySelectedCourseId().getId());
								}
							}
							if(interviewSelected.getInterviewProgramCourse()!=null){
								statusTO.setInterviewProgramCourseId(interviewSelected.getInterviewProgramCourse().getId());
								if(interviewSelected.getInterviewProgramCourse().getName()!=null){
									statusTO.setInterviewStatus(status +interviewSelected.getInterviewProgramCourse().getName());
								}
							}
							statusTO.setIsInterviewSelected(CMSConstants.INTERVIEW);							
							return statusTO;														
						}else if(!cardgenerated)
						{
							//get old status 
							status=CMSConstants.RESULT_UNDER_PROCESS;
							
							if(oldInterviewSelected.getAdmAppln()!=null){
								if(oldInterviewSelected.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(oldInterviewSelected.getAdmAppln().getApplnNo());
								}
								//Applied Year added By manu
								if(oldInterviewSelected.getAdmAppln().getAppliedYear()!=null && oldInterviewSelected.getAdmAppln().getAppliedYear()!=0){
									statusTO.setAppliedYear(oldInterviewSelected.getAdmAppln().getAppliedYear());
								}
								if(oldInterviewSelected.getAdmAppln().getPersonalData()!=null){
									if(oldInterviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(oldInterviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(oldInterviewSelected.getAdmAppln().getPersonalData().getEmail()!=null){
										statusTO.setEmail(oldInterviewSelected.getAdmAppln().getPersonalData().getEmail());
									}
								}
								if(oldInterviewSelected.getAdmAppln().getCourseBySelectedCourseId()!=null){
//									statusTO.setCourseId(oldInterviewSelected.getAdmAppln().getCourse().getId());
									statusTO.setCourseId(oldInterviewSelected.getAdmAppln().getCourseBySelectedCourseId().getId());
								}
							}
							if(oldInterviewSelected.getInterviewProgramCourse()!=null){
								statusTO.setInterviewProgramCourseId(oldInterviewSelected.getInterviewProgramCourse().getId());
								if(oldInterviewSelected.getInterviewProgramCourse().getName()!=null){
									statusTO.setInterviewStatus(oldInterviewSelected.getInterviewProgramCourse().getName()+" " + status);
								}
							}							
							return statusTO;							
						}
						}			
					}					
				}				
				//Keeps the old main round status	
				oldInterviewSelected = interviewSelected;
			}
		}
		else if(interviewSelectedSet == null || interviewSelectedSet.isEmpty()){
			//Only application is submitted
			//Call for Interview
			status=CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
			
					if(admAppln.getApplnNo()!=0){
						statusTO.setApplicationNo(admAppln.getApplnNo());
					}
					if(admAppln.getAppliedYear()!=null){
						statusTO.setAppliedYear(admAppln.getAppliedYear());
					}
				if(admAppln.getPersonalData()!=null){
					if(admAppln.getPersonalData().getDateOfBirth()!=null){
					statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
					}
					if(admAppln.getPersonalData().getEmail()!=null){
						statusTO.setEmail(admAppln.getPersonalData().getEmail());
					}
				}
				statusTO.setInterviewStatus(status);
				statusTO.setIsInterviewSelected(CMSConstants.VIEW_APPLICATION);
			return statusTO;
		}
		else if(interviewSelectedSet!=null && !interviewSelectedSet.isEmpty() && interviewSelectedSet.size()==1){
			Iterator<InterviewSelected> iterator = interviewSelectedSet.iterator();
			while (iterator.hasNext()) {
				InterviewSelected interviewSelected = iterator.next();
				
				if(interviewSelected.getInterviewProgramCourse()!= null){
					int selectProgcrsID=interviewSelected.getInterviewProgramCourse().getId();
					String selectProgcrsSequence=interviewSelected.getInterviewProgramCourse().getSequence();// New varible created to check sequence is equal. This condition is required when there is course change. 
					
					Iterator<InterviewResult> resIterator = interviewresultSet.iterator();
					while (resIterator.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) resIterator.next();
						//check subround exists or not
						if(interviewResult.getInterviewProgramCourse()!=null && (interviewResult.getInterviewProgramCourse().getId()==selectProgcrsID|| interviewResult.getInterviewProgramCourse().getSequence().equalsIgnoreCase(selectProgcrsSequence))){
							subroundexist=true;
							status=CMSConstants.RESULT_UNDER_PROCESS;
							if(interviewResult.getAdmAppln()!=null){
								if(interviewResult.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(interviewResult.getAdmAppln().getApplnNo());
								}
								//applied year added by manu
								if(interviewResult.getAdmAppln().getAppliedYear()!=null && interviewResult.getAdmAppln().getAppliedYear()!=0){
									statusTO.setAppliedYear(interviewResult.getAdmAppln().getAppliedYear());
									}
								if(interviewResult.getAdmAppln().getPersonalData()!=null){
									if(interviewResult.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewResult.getAdmAppln().getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(interviewResult.getAdmAppln().getPersonalData().getEmail()!=null){
										statusTO.setEmail(interviewResult.getAdmAppln().getPersonalData().getEmail());
									}
								}
								if(interviewResult.getAdmAppln().getCourseBySelectedCourseId()!=null){
//									statusTO.setCourseId(interviewResult.getAdmAppln().getCourse().getId());
									statusTO.setCourseId(interviewResult.getAdmAppln().getCourseBySelectedCourseId().getId());
								}
							}
							if(interviewResult.getInterviewProgramCourse()!=null){
								statusTO.setInterviewProgramCourseId(interviewResult.getInterviewProgramCourse().getId());
								if(interviewResult.getInterviewProgramCourse().getName()!=null){
									statusTO.setInterviewStatus(interviewResult.getInterviewProgramCourse().getName()+ " " + status);
								}
							}							
							return statusTO;
						}
					}
					
					if(!subroundexist){
						cardgenerated=interviewSelected.getIsCardGenerated();
						if(cardgenerated)
						{
							//Call for Interview
							status=CMSConstants.CALL_FOR;
							
							if(interviewSelected.getAdmAppln()!=null){
								if(interviewSelected.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(interviewSelected.getAdmAppln().getApplnNo());
								}
								//Applied Year Added By manu
								if(interviewSelected.getAdmAppln().getAppliedYear()!=null && interviewSelected.getAdmAppln().getAppliedYear()!=0){
									statusTO.setAppliedYear(interviewSelected.getAdmAppln().getAppliedYear());
									}
								if(interviewSelected.getAdmAppln().getPersonalData()!=null){
									if(interviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(interviewSelected.getAdmAppln().getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(interviewSelected.getAdmAppln().getPersonalData().getEmail()!=null){
										statusTO.setEmail(interviewSelected.getAdmAppln().getPersonalData().getEmail());
									}
								}
								if(interviewSelected.getAdmAppln().getCourseBySelectedCourseId()!=null){
//									statusTO.setCourseId(interviewSelected.getAdmAppln().getCourse().getId());
									statusTO.setCourseId(interviewSelected.getAdmAppln().getCourseBySelectedCourseId().getId());
								}
							}
							if(interviewSelected.getInterviewProgramCourse()!=null){
								statusTO.setInterviewProgramCourseId(interviewSelected.getInterviewProgramCourse().getId());
								if(interviewSelected.getInterviewProgramCourse().getName()!=null){
									if(interviewSelected.getAdmAppln().getSeatNo()!=null && !interviewSelected.getAdmAppln().getSeatNo().isEmpty() && interviewSelected.getAdmAppln().getExamCenter()!=null && interviewSelected.getAdmAppln().getExamCenter().getSeatNoPrefix()!=null){
										statusTO.setSeatNo(interviewSelected.getAdmAppln().getExamCenter().getSeatNoPrefix()+
										String.format("%04d", Integer.parseInt(interviewSelected.getAdmAppln().getSeatNo())));
									}
									statusTO.setInterviewStatus(status + interviewSelected.getInterviewProgramCourse().getName());
								}
							}
							statusTO.setIsInterviewSelected(CMSConstants.INTERVIEW);							
							return statusTO;														
						}else if(!cardgenerated)
						{
							//get old status 
							status=CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_UNDERREVIEW;
							
									if(admAppln.getApplnNo()!=0){
										statusTO.setApplicationNo(admAppln.getApplnNo());
									}
									if(admAppln.getAppliedYear()!=null){
										statusTO.setAppliedYear(admAppln.getAppliedYear());
									}
								if(admAppln.getPersonalData()!=null){
									if(admAppln.getPersonalData().getDateOfBirth()!=null){
									statusTO.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
									}
									if(admAppln.getPersonalData().getEmail()!=null){
										statusTO.setEmail(admAppln.getPersonalData().getEmail());
									}
								}
								statusTO.setInterviewStatus(status);
								statusTO.setIsInterviewSelected(CMSConstants.VIEW_APPLICATION);
								return statusTO;					
						}
					}
				}			
			}
		}
		log.info("End of getInterviewStatus of AdmissionStatusHelper");
		return statusTO;
	}
	/**
	 * 
	 * @param templateDescription
	 * @param applicantDetails
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String generateAdmissionCard(String templateDescription,
			AdmAppln applicantDetails, HttpServletRequest request, File barCodeImgFile)
			throws Exception {
		log.info("Inside of generateAdmitCard of AdmissionStatusHelper");
		String program = "";
		String course = "";
		String selectedCourse = "";
		String applicationNo = "";
		String interviewVenue = "";
		String academicYear = "";
		String applicantName = "";
		String dateOfBirth = "";
		String placeOfBirth = "";
		String nationality = "";
		String religion = "";
		String subreligion = "";
		String residentCategory = "";
		String category = "";
		String gender = "";
		String email = "";
		String finalTemplate = "";
		String contextPath = "";
		String logoPath = "";
		String seatNo = "";
		String examCenter = "";
		StringBuffer currentAddress = new StringBuffer();
		StringBuffer permanentAddress = new StringBuffer();
		String examCenterAddress1  = "";
		String examCenterAddress2 = "";
		String examCenterAddress3 = "";
		String examCenterAddress4 = "";
		String seatNoPrefix = "";
		byte[] logo = null;
		String approvedate="";
		String mode="";
		String admissionScheduledDate="";
		String admissionScheduledTime="";
		String specializationPrefered="";
		String commencementDate="";
		String barCode = "";
		byte[] barCodeByte = null;
		HttpSession session = request.getSession(false);
		if (templateDescription != null
				&& !templateDescription.trim().isEmpty()
				&& applicantDetails!= null) {

				if (applicantDetails.getPersonalData() != null) {
					if(applicantDetails.getFinalMeritListApproveDate()!=null){
						approvedate=CommonUtil.getStringDate(applicantDetails.getFinalMeritListApproveDate());
					}
					if (applicantDetails.getPersonalData()
							.getDateOfBirth() != null) {
						dateOfBirth = CommonUtil.getStringDate(applicantDetails
								.getPersonalData()
								.getDateOfBirth());
					}
					if (applicantDetails.getPersonalData()
							.getBirthPlace() != null) {
						placeOfBirth = applicantDetails
								.getPersonalData().getBirthPlace();
					}
					if (applicantDetails.getPersonalData()
							.getFirstName() != null
							&& !applicantDetails
									.getPersonalData().getFirstName().trim()
									.isEmpty()) {
						applicantName = applicantDetails
								.getPersonalData().getFirstName();
					}
					if (applicantDetails.getPersonalData()
							.getMiddleName() != null
							&& !applicantDetails
									.getPersonalData().getMiddleName().trim()
									.isEmpty()) {
						applicantName = applicantName
								+ " "
								+ applicantDetails
										.getPersonalData().getMiddleName();
					}
					if (applicantDetails.getPersonalData()
							.getLastName() != null
							&& !applicantDetails
									.getPersonalData().getLastName().trim()
									.isEmpty()) {
						applicantName = applicantName
								+ " "
								+ applicantDetails
										.getPersonalData().getLastName();
					}
					if (applicantDetails.getPersonalData()
							.getNationalityOthers() != null) {
						nationality = applicantDetails
								.getPersonalData().getNationalityOthers();
					} else if (applicantDetails.getPersonalData()
							.getNationality() != null) {
						nationality = applicantDetails
								.getPersonalData().getNationality().getName();
					}
					if (applicantDetails.getPersonalData()
							.getReligionOthers() != null) {
						religion = applicantDetails
								.getPersonalData().getReligionOthers();
					} else if (applicantDetails.getPersonalData()
							.getReligion() != null) {
						religion = applicantDetails
								.getPersonalData().getReligion().getName();
					}

					if (applicantDetails.getPersonalData()
							.getReligionSectionOthers() != null) {
						subreligion = applicantDetails
								.getPersonalData().getReligionSectionOthers();
					} else if (applicantDetails.getPersonalData()
							.getReligionSection() != null) {
						subreligion = applicantDetails
								.getPersonalData().getReligionSection()
								.getName();
					}

					if (applicantDetails.getPersonalData().getResidentCategory() != null
							&& applicantDetails.getPersonalData().getResidentCategory().getName() != null){
						residentCategory = applicantDetails.getPersonalData()
											.getResidentCategory().getName();
					}
					if (applicantDetails.getPersonalData()
							.getGender() != null) {
						gender = applicantDetails
								.getPersonalData().getGender();
					}
					if (applicantDetails.getPersonalData()
							.getEmail() != null) {
						email = applicantDetails
								.getPersonalData().getEmail();
					}
					if (applicantDetails.getPersonalData()
							.getCasteOthers() != null) {
						category = applicantDetails
								.getPersonalData().getCasteOthers();
					} else if (applicantDetails.getPersonalData()
							.getCaste() != null) {
						category = applicantDetails
								.getPersonalData().getCaste().getName();
					}
					if (applicantDetails.getCourseBySelectedCourseId() != null) {
						course = applicantDetails.getCourseBySelectedCourseId()
								.getName();
					}
					if (applicantDetails.getCourseBySelectedCourseId() != null) {
						selectedCourse = applicantDetails.getCourseBySelectedCourseId()
								.getName();
					}
					if (applicantDetails.getCourseBySelectedCourseId() != null
							&& applicantDetails.getCourseBySelectedCourseId()
									.getProgram() != null) {
						program = applicantDetails.getCourseBySelectedCourseId()
								.getProgram().getName();
					}
					applicationNo = String.valueOf(applicantDetails
							.getApplnNo());
					academicYear = String.valueOf(applicantDetails
							.getAppliedYear());

					/*if (applicantDetails.getApplnDocs() != null) {
						Iterator<ApplnDoc> applnDocItr = applicantDetails
								.getApplnDocs().iterator();

						while (applnDocItr.hasNext()) {
							ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
							if (applnDoc.getIsPhoto() != null
									&& applnDoc.getIsPhoto()) {
								photo = applnDoc.getDocument();
								
								if (session != null) {
									contextPath = request.getContextPath();
									contextPath = "<img src="
											+ contextPath
											+ "/PhotoServlet alt='Photo not available' width='150' height='150' >";
									session.setAttribute(
											AdmissionStatusHelper.PHOTOBYTES,
											photo);
								}
							}
						}
					}*/
					
					if(applicantDetails.getStudents() != null){
						for (Student student : applicantDetails.getStudents()) {
							contextPath = "<img src='images/StudentPhotos/" + student.getId() + ".jpg?"+applicantDetails.getLastModifiedDate()+"' alt='Photo not available' width='150' height='150' >";
						}
					}else{
						contextPath = "<img src='images/photoblank.gif' width='150' height='150' >";
					}
					
					if (applicantDetails.getPersonalData()
							.getCurrentAddressLine1() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData().getCurrentAddressLine1());
						currentAddress.append(' ');
					}

					if (applicantDetails.getPersonalData()
							.getCurrentAddressLine2() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData().getCurrentAddressLine2());
						currentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getCityByCurrentAddressCityId() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData().getCityByCurrentAddressCityId());
						currentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getStateByCurrentAddressStateId() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData()
								.getStateByCurrentAddressStateId().getName());
						currentAddress.append(' ');
					} else if (applicantDetails.getPersonalData()
							.getCurrentAddressStateOthers() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData().getCurrentAddressStateOthers());
						currentAddress.append(' ');
					}

					if (applicantDetails.getPersonalData()
							.getCountryByCurrentAddressCountryId() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData()
								.getCountryByCurrentAddressCountryId().getName());
						currentAddress.append(' ');
					} else if (applicantDetails.getPersonalData()
							.getCurrentAddressCountryOthers() != null) {
						currentAddress
								.append(applicantDetails
										.getPersonalData()
										.getCurrentAddressCountryOthers());
						currentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getCurrentAddressZipCode() != null) {
						currentAddress.append(applicantDetails
								.getPersonalData().getCurrentAddressZipCode());
						currentAddress.append(' ');
					}

					if (applicantDetails.getPersonalData()
							.getParentAddressLine1() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData().getParentAddressLine1());
						permanentAddress.append(' ');
					}

					if (applicantDetails.getPersonalData()
							.getParentAddressLine2() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData().getParentAddressLine2());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getCityByPermanentAddressCityId() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData()
								.getCityByPermanentAddressCityId());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getStateByParentAddressStateId() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData().getStateByParentAddressStateId()
								.getName());
						permanentAddress.append(' ');
					} else if (applicantDetails.getPersonalData()
							.getParentAddressStateOthers() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData().getParentAddressStateOthers());
						permanentAddress.append(' ');
					}

					if (applicantDetails.getPersonalData()
							.getCountryByPermanentAddressCountryId() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData()
								.getCountryByPermanentAddressCountryId().getName());
						permanentAddress.append(' ');
					} else if (applicantDetails.getPersonalData()
							.getPermanentAddressCountryOthers() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData()
								.getPermanentAddressCountryOthers());
						permanentAddress.append(' ');
					}
					if (applicantDetails.getPersonalData()
							.getPermanentAddressZipCode() != null) {
						permanentAddress.append(applicantDetails
								.getPersonalData().getPermanentAddressZipCode());
						permanentAddress.append(' ');
					}
					if(applicantDetails.getSeatNo()!= null){
						seatNo = applicantDetails.getSeatNo();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getCenter()!= null){
						examCenter = applicantDetails.getExamCenter().getCenter();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getAddress1()!= null){
						examCenterAddress1  = applicantDetails.getExamCenter().getAddress1();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getAddress2()!= null){
						examCenterAddress2 = applicantDetails.getExamCenter().getAddress2();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getAddress3()!= null){
						examCenterAddress3 = applicantDetails.getExamCenter().getAddress3();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getAddress4()!= null){
						examCenterAddress4 = applicantDetails.getExamCenter().getAddress4();
					}
					if(applicantDetails.getExamCenter()!= null && applicantDetails.getExamCenter().getSeatNoPrefix()!= null){
						seatNoPrefix = applicantDetails.getExamCenter().getSeatNoPrefix();
					}
					if(applicantDetails.getMode()!=null){
						mode=applicantDetails.getMode();
					}
					if(applicantDetails.getStudentSpecializationPrefered()!=null && !applicantDetails.getStudentSpecializationPrefered().isEmpty()){
						Set<StudentSpecializationPrefered> stuSpecialization=applicantDetails.getStudentSpecializationPrefered();
						Iterator<StudentSpecializationPrefered> itr=stuSpecialization.iterator();
						while (itr.hasNext()) {
							StudentSpecializationPrefered studentSpecializationPrefered = (StudentSpecializationPrefered) itr.next();
							if(studentSpecializationPrefered.getSpecializationPrefered()!=null)
							specializationPrefered=studentSpecializationPrefered.getSpecializationPrefered();
						}
					}//added by manu
					if (applicantDetails.getCourseBySelectedCourseId() != null 	&& applicantDetails.getCourseBySelectedCourseId().getCommencementDate()!= null) {
						commencementDate = CommonUtil.getStringDate(applicantDetails.getCourseBySelectedCourseId().getCommencementDate());
					}
				}
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
	// added by Smitha- modifications to the code to display Admission scheduled date and time in EAdmission Card
		if (applicantDetails.getAdmapplnAdditionalInfo()!=null && !applicantDetails.getAdmapplnAdditionalInfo().isEmpty()){
				Iterator<AdmapplnAdditionalInfo> itrAdmAdditional=applicantDetails.getAdmapplnAdditionalInfo().iterator();
				while (itrAdmAdditional.hasNext()) {
					AdmapplnAdditionalInfo admapplnAdditionalInfo = (AdmapplnAdditionalInfo) itrAdmAdditional.next();
					if(admapplnAdditionalInfo.getAdmissionScheduledDate()!=null)
					admissionScheduledDate = CommonUtil.getStringDate(admapplnAdditionalInfo.getAdmissionScheduledDate());
					if(admapplnAdditionalInfo.getAdmissionScheduledTime()!=null)
					admissionScheduledTime=admapplnAdditionalInfo.getAdmissionScheduledTime();
				}
		}
		//ends-Smitha		
		// replace dynamic data
		finalTemplate = templateDescription;
		
		//Added By manu For BarCode
		barCodeByte = new byte[(int)barCodeImgFile.length ()];
		FileInputStream fis=new FileInputStream(barCodeImgFile);
		// convert image into byte array
		fis.read(barCodeByte);
		session.setAttribute("barCodeBytes", barCodeByte);
		barCode = request.getContextPath();
		barCode = "<img src="+ barCode+ "/BarCodeServlet alt='Barcode  not available' width='137' height='37' >";
		finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_BARCODE, barCode);
		//end
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_APPLICANT_NAME, applicantName);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_DOB,
					dateOfBirth);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_POB,
					placeOfBirth);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_NATIONALITY, nationality);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_SUBRELIGION, subreligion);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_RESIDENTCATEGORY, residentCategory);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_RELIGION, religion);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_GENDER,
					gender);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EMAIL,
					email);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_CASTE,
					category);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_PHOTO,
					contextPath);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_LOGO,
					logoPath);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_PROGRAM, program);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_COURSE,
					course);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_SELECTED_COURSE,
					selectedCourse);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_APPLICATION_NO, applicationNo);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_ACADEMIC_YEAR, academicYear);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_INTERVIEW_VENUE, interviewVenue);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_CURRENT_ADDRESS, currentAddress);
			finalTemplate = finalTemplate.replace(
					CMSConstants.TEMPLATE_PERMANENT_ADDRESS, permanentAddress);
			if(seatNo!= null && !seatNo.trim().isEmpty()){
				finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_SEAT_NO, String.format("%04d", Integer.parseInt(seatNo)));
			}
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_NAME, examCenter);	
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_ADDRESS, examCenterAddress1 + "<br/>" + examCenterAddress2 + "<br/>" +  examCenterAddress3 + "<br/>" + examCenterAddress4);
			finalTemplate = finalTemplate.replace(CMSConstants.TEMPLATE_EXAM_CENTER_SEAT_NO_PREFIX, seatNoPrefix);
			finalTemplate = finalTemplate.replace(CMSConstants.FINAL_APPROVE_DATE, approvedate);
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_MODE, mode);
			//Additions by Smitha- Modifications to display admission scheduled date and time.
			finalTemplate=finalTemplate.replace(CMSConstants.ADMISSION_SCHEDULED_DATE,admissionScheduledDate);
			finalTemplate=finalTemplate.replace(CMSConstants.ADMISSION_SCHEDULED_TIME,admissionScheduledTime);
			finalTemplate=finalTemplate.replace(CMSConstants.STUDENT_SPECIALIZATION_PREFERED,specializationPrefered);
			//Additions by Manu- Modifications to display admission scheduled Commencement Date
			finalTemplate=finalTemplate.replace(CMSConstants.COURSE_COMMENCEMENT_DATE,commencementDate);
		}
		log.info("End of generateAdmitCard of AdmissionStatusHelper");
		return finalTemplate;
	}
	
	// vinodha
	public AdmissionStatusTO populateAdmApplnBOtoTOForMemo(AdmAppln appln, AdmissionStatusForm admissionStatusForm)throws Exception
	 {
		log.info("Inside of populateAdmApplnBOtoTO of AdmissionStatusHelper");
			AdmissionStatusTO admissionStatusTO = null; 
			InterviewResultTO interviewResultTO = null;
				IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
				if(appln==null){
					CandidatePreference candidatePreferenceAdm = txn.getApplicationDetailsForMemoFromAdmAppln(admissionStatusForm);
					appln = candidatePreferenceAdm.getAdmAppln();
				}
				if (appln != null) {
				admissionStatusTO = new AdmissionStatusTO();
				
				/**
				 * Checks for the columns. If does not contain  null values then set those values from BO to TO
				 */
				admissionStatusTO.setId(appln.getId());
				if(appln.getIsBypassed()!= null && appln.getIsBypassed()){
					admissionStatusTO.setByPassed(appln.getIsBypassed());
				}
				/* code Added for courses having intervie_selection_schedule data defined---- code starts here*/
			/*	if(appln.getInterScheduleSelection()!=null && appln.getInterScheduleSelection().getId()>0 && appln.getPersonalData()!=null && appln.getPersonalData().getDateOfBirth()!=null 
						&& appln.getPersonalData().getId()!=0 && appln.getIsCancelled()!=null){
					admissionStatusTO.setApplicationNo(appln.getApplnNo());
					admissionStatusTO.setDateOfBirth(CommonUtil.getStringDate(appln.getPersonalData().getDateOfBirth()));
					admissionStatusTO.setPersonalDataId(appln.getPersonalData().getId());
					boolean isFinalMeritApproved = false;
					if(appln.getIsFinalMeritApproved()!=null){
						isFinalMeritApproved = appln.getIsFinalMeritApproved();
					}
					admissionStatusTO.setIsSelected(convertBoolValueIsSelected(appln.getIsSelected(),isFinalMeritApproved));	
					admissionStatusTO.setInterviewSelectionSchedule(String.valueOf(appln.getInterScheduleSelection().getId()));
				}*/
				/* code Added for courses having intervie_selection_schedule data defined----code ends here*/
				else if(appln.getIsSelected()!=null && appln.getPersonalData()!=null && appln.getPersonalData().getDateOfBirth()!=null 
				&& appln.getPersonalData().getId()!=0 && appln.getIsCancelled()!=null){
						
						admissionStatusTO.setApplicationNo(appln.getApplnNo());
						//applied year added by manu
						admissionStatusTO.setAppliedYear(appln.getAppliedYear());

						admissionStatusTO.setDateOfBirth(CommonUtil.getStringDate(appln.getPersonalData().getDateOfBirth()));
						admissionStatusTO.setPersonalDataId(appln.getPersonalData().getId());
						boolean isFinalMeritApproved = false;
						if(appln.getIsFinalMeritApproved()!=null){
							isFinalMeritApproved = appln.getIsFinalMeritApproved();
						}
						admissionStatusTO.setIsSelected(convertBoolValueIsSelected(appln.getIsSelected(),isFinalMeritApproved));				
				}
				if(appln.getPersonalData()!=null && appln.getPersonalData().getEmail()!=null){
					admissionStatusTO.setEmail(appln.getPersonalData().getEmail());
				}									
				admissionStatusTO.setCancelled(appln.getIsCancelled());
				if(appln.getAppliedYear()!=0){
					admissionStatusTO.setAppliedYear(appln.getAppliedYear());
				}
				if(appln.getCourseBySelectedCourseId() != null){
					//admissionStatusTO.setCourseId(appln.getCourse().getId());
					admissionStatusTO.setCourseId(appln.getCourseBySelectedCourseId().getId());
				}
				Set<InterviewResult> intResultSet=appln.getInterviewResults();
				Set<InterviewResultTO> interviewResultTOSet=new HashSet<InterviewResultTO>();
				Iterator<InterviewResult> iterator1=intResultSet.iterator();
				while (iterator1.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) iterator1.next();
						if(interviewResult!=null)
							{
							interviewResultTO=new InterviewResultTO();
							InterviewProgramCourseTO interviewProgramCourseTO=new InterviewProgramCourseTO();
							if(interviewResult.getInterviewStatus()!=null && interviewResult.getInterviewProgramCourse()!=null){
							if(interviewResult.getInterviewStatus().getName()!=null && !interviewResult.getInterviewStatus().getName().isEmpty())
							{
							interviewResultTO.setInterviewStatus(interviewResult.getInterviewStatus().getName());
							}
							if(interviewResult.getId()!=0){
							interviewResultTO.setId(interviewResult.getId());
							}
							if(interviewResult.getInterviewProgramCourse().getName()!=null && !interviewResult.getInterviewProgramCourse().getName().isEmpty()){
							interviewProgramCourseTO.setName(interviewResult.getInterviewProgramCourse().getName());
							}
							if(interviewResult.getInterviewProgramCourse().getId()!=0){
							interviewProgramCourseTO.setId(interviewResult.getInterviewProgramCourse().getId());
							}
							
							interviewResultTO.setInterviewProgramCourseTO(interviewProgramCourseTO);
							interviewResultTOSet.add(interviewResultTO);
							}
						}	
				}
				admissionStatusTO.setAdmStatus(appln.getAdmStatus());
				admissionStatusTO.setInterviewResultTO(interviewResultTOSet);
				Set<Student> studentSet = appln.getStudents();
				Iterator<Student> stItr = studentSet.iterator();
				boolean isAdmitted = false;
				while (stItr.hasNext()) {
					Student student = (Student) stItr.next();
					if(student.getIsAdmitted()!=null)
					isAdmitted = student.getIsAdmitted();
				}
				admissionStatusTO.setAdmitted(isAdmitted);
				
				//raghu
				if(appln.getMode()!=null && appln.getMode().equalsIgnoreCase("CHALLAN")){
				if(appln.getIsChallanRecieved()!=null && appln.getIsChallanRecieved()){
					admissionStatusForm.setIsChallanRecieved(1);
				}else{
					admissionStatusForm.setIsChallanRecieved(0);
				}
				}else if(appln.getMode()!=null && appln.getMode().equalsIgnoreCase("NEFT")){
					if(appln.getIsChallanRecieved()!=null && appln.getIsChallanRecieved()){
						admissionStatusForm.setIsChallanRecieved(1);
					}else{
						admissionStatusForm.setIsChallanRecieved(0);
					}
				}else if(appln.getApplnNo()!=0 && appln.getMode()!=null && appln.getMode().equalsIgnoreCase("Online")){
					
					admissionStatusForm.setIsChallanRecieved(1);
					
				}
				
				//vibin for status change
				//raghu add applied year check
				Integer maxallotment = txn.getmaxallotment(appln.getCourse().getProgram().getProgramType().getId(),appln.getAppliedYear());
		         int mxal = 0;
		        if(maxallotment!=null){
		         mxal = maxallotment.intValue();
		        }
		        admissionStatusForm.setMaxallotment(mxal);
		        Integer sureCourseId = 0;
		        Integer prefNo = 100;
		        
				// StudentCourseAllotment allotmentdata = txn.getallotmentdetails(appln.getId(),mxal);
		        StudentIndexMark allotmentdata = txn.getStudentIndexMarks(admissionStatusForm);
		        
			    if(allotmentdata!=null){
			       sureCourseId = allotmentdata.getCourse().getId();
    		       admissionStatusTO.setAllotmentflag(true);
    		       prefNo = allotmentdata.getPrefNo();
    		       
			       admissionStatusTO.setCurrentcourse(allotmentdata.getCourse().getName());
			       admissionStatusTO.setCurrentcourseid(allotmentdata.getCourse().getId());
			       admissionStatusTO.setPref(allotmentdata.getPrefNo());
			    
    		       if(allotmentdata.getCourse().getDateTime()!=null)
			    	   admissionStatusTO.setDateTime(allotmentdata.getCourse().getDateTime());
			       if(allotmentdata.getCourse().getGeneralFee()!=null)
			    	   admissionStatusTO.setGeneralFee(allotmentdata.getCourse().getGeneralFee());
			       if(allotmentdata.getCourse().getCasteFee()!=null)
			    	   admissionStatusTO.setCasteFee(allotmentdata.getCourse().getCasteFee());
			       
			       if(admissionStatusTO.isAlmntgeneral()){			    	   
			    	  admissionStatusTO.setAltmntcategory("GENERAL"); 
			       }else if(admissionStatusTO.isAlmntcaste()){			    	 
			    	   admissionStatusTO.setAltmntcategory(allotmentdata.getAdmAppln().getPersonalData().getReligionSection().getName());			    	   
			       }			      
			    }else{

			    	CandidatePreference adm = txn.getApplicationDetailsForMemoFromAdmAppln(admissionStatusForm);
			    	if(adm!=null)
			    	{			    		
					    if(adm.getAdmAppln().getCourse().getDateTime()!=null)
					    	  admissionStatusTO.setDateTime(adm.getAdmAppln().getCourse().getDateTime());
					    if(adm.getAdmAppln().getCourse().getGeneralFee()!=null)
					    	  admissionStatusTO.setGeneralFee(adm.getAdmAppln().getCourse().getGeneralFee());
					     if(adm.getAdmAppln().getCourse().getCasteFee()!=null)
					    	  admissionStatusTO.setCasteFee(adm.getAdmAppln().getCourse().getCasteFee());
			    		
			    		admissionStatusTO.setPref(adm.getPrefNo());
					    admissionStatusTO.setCurrentcourse(adm.getCourse().getName());
					    if(admissionStatusTO.isAlmntgeneral()){			    	   
					    	  admissionStatusTO.setAltmntcategory("GENERAL"); 
					       }else if(admissionStatusTO.isAlmntcaste()){			    	 
					    	   admissionStatusTO.setAltmntcategory(adm.getAdmAppln().getPersonalData().getReligionSection().getName());			    	   
					       }
			    	}
			    
			    }
			    
/*			      List<CandidatePreferenceTO> prefto = new ArrayList<CandidatePreferenceTO>();
				  AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
				  prefto =   helper.copyPropertiesValuepref(appln.getId());

				Iterator<CandidatePreferenceTO> preftoItr = prefto.iterator();
				List<CandidatePreferenceTO> memoList = new LinkedList<CandidatePreferenceTO>();
				while (preftoItr.hasNext()) {
					CandidatePreferenceTO prefTO = (CandidatePreferenceTO) preftoItr.next();
					CandidatePreferenceTO memoTo = new CandidatePreferenceTO();
					memoTo.setCoursId(prefTO.getCoursId());
					memoTo.setCoursName(prefTO.getCoursName());
					memoTo.setPrefNo(prefTO.getPrefNo());
			        
					if(Integer.parseInt(prefTO.getCoursId())==sureCourseId){
						if(allotmentdata.getIsGeneral()){
							memoTo.setCategory("General");
						}else{
							memoTo.setCategory(appln.getPersonalData().getReligionSection().getName());
						}
						
						memoTo.setStatus("Alloted");
					}else{
						memoTo.setCategory("General");
						memoTo.setStatus("Waiting List");
				    }
					// fess for oec st sc
		            if(appln.getPersonalData().getReligionSection().getId()==2 || appln.getPersonalData().getReligionSection().getId()==3 ||appln.getPersonalData().getReligionSection().getId()==12)
				        memoTo.setFees(prefTO.getCastefees());
		            else
		            	memoTo.setFees(prefTO.getGeneralfees());
		            memoTo.setDateAndTime(prefTO.getDateAndTime());
		            
		            if(prefTO.getPrefNo()<=prefNo)
		            memoList.add(memoTo);
			   }
			   
		    if(memoList!=null){
		    	admissionStatusTO.setMemoList(memoList);
		    }	
		    
*/		    CandidatePGIDetails details = txn.getCandidateDetails(appln.getId());
			TemplateHandler temphandle=TemplateHandler.getInstance();
			List<GroupTemplate> list= temphandle.getDuplicateCheckList("Online payment Acknowledgement Slip");
					
			if(list != null && !list.isEmpty()) {
				if(details != null){
					admissionStatusForm.setOnlineAcknowledgement(true);
				}
			}
			}
		log.info("End of populateAdmApplnBOtoTO of AdmissionStatusHelper");
		return admissionStatusTO;
	}
}