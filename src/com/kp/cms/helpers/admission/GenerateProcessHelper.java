package com.kp.cms.helpers.admission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.GenerateProcessForm;
import com.kp.cms.handlers.admission.AdmissionStatusHandler;
import com.kp.cms.handlers.admission.GenerateProcessHandler;
import com.kp.cms.to.admission.AdmissionStatusTO;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IGenerateProcessTransaction;
import com.kp.cms.transactionsimpl.admission.GenerateProcessTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class GenerateProcessHelper {
	private static final String PHOTOBYTES="PhotoBytes";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	/**
	 * Singleton object of GenerateProcessHelper
	 */
	private static volatile GenerateProcessHelper generateProcessHelper = null;
	private static final Log log = LogFactory.getLog(GenerateProcessHelper.class);
	private GenerateProcessHelper() {
		
	}
	/**
	 * return singleton object of generateProcessHelper.
	 * @return
	 */
	public static GenerateProcessHelper getInstance() {
		if (generateProcessHelper == null) {
			generateProcessHelper = new GenerateProcessHelper();
		}
		return generateProcessHelper;
	}
	/**
	 * @param generateProcessForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchCriteria(GenerateProcessForm generateProcessForm) throws Exception {
		
		String startTime = generateProcessForm.getStartingTimeHours() +":"+ generateProcessForm.getStartingTimeMins();
		String endTime = generateProcessForm.getEndingTimeHours() +":"+ generateProcessForm.getEndingTimeMins();
		StringBuilder intType =new StringBuilder();
		if (generateProcessForm.getInterviewType()!=null &&  generateProcessForm.getInterviewType().length > 0) {
			String [] tempArray = generateProcessForm.getInterviewType();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		}
		
		String fromQuery=" from AdmAppln admAppln ";
		String joinQuery="";
		String whereQuery="";
		if(intType!=null && !intType.toString().isEmpty()){
			joinQuery=joinQuery+" join admAppln.interviewSelecteds interviewSelected" +
			" with interviewSelected.interviewProgramCourse.id  in ("+intType+") and interviewSelected.isCardGenerated = 1 ";
		}
		if((generateProcessForm.getInterviewStartDate()!=null && !generateProcessForm.getInterviewStartDate().isEmpty())
				|| (generateProcessForm.getInterviewEndDate()!=null && !generateProcessForm.getInterviewEndDate().isEmpty())){
			joinQuery=joinQuery+" join admAppln.interviewCards interviewCard ";
			if(generateProcessForm.getInterviewStartDate()!=null && !generateProcessForm.getInterviewStartDate().isEmpty()){
				whereQuery=whereQuery+" and interviewCard.interview.date  >= '"+CommonUtil.ConvertStringToSQLDate(generateProcessForm.getInterviewStartDate())+"'";
			}
			if(generateProcessForm.getInterviewEndDate()!=null && !generateProcessForm.getInterviewEndDate().isEmpty()){
				whereQuery=whereQuery+" and interviewCard.interview.date  <= '"+CommonUtil.ConvertStringToSQLDate(generateProcessForm.getInterviewEndDate())+"'";
			}
		}
		
		String query=fromQuery+joinQuery+" where admAppln.isCancelled=0" +
				" and admAppln.appliedYear ="+generateProcessForm.getAppliedYear()+" and " +
				" admAppln.course.program.id="+generateProcessForm.getProgramId()
				+" and admAppln.course.program.programType.id="+generateProcessForm.getProgramTypeId()+whereQuery;
		
		
//		String query="select admAppln from AdmAppln admAppln " +
//				" join admAppln.interviewSelecteds interviewSelected" +
//				" with interviewSelected.interviewProgramCourse.id  in ("+intType+") and interviewSelected.isCardGenerated = 1 " +
//				" join admAppln.interviewCards interviewCard where admAppln.appliedYear ="+generateProcessForm.getAppliedYear()+
//				" and interviewCard.interview.date  between '"+CommonUtil.ConvertStringToSQLDate(generateProcessForm.getInterviewStartDate())+"' and '" 
//				+CommonUtil.ConvertStringToSQLDate(generateProcessForm.getInterviewEndDate())+"' and " +
//				" admAppln.courseBySelectedCourseId.program.id="+generateProcessForm.getProgramId()
//				+" and admAppln.courseBySelectedCourseId.program.programType.id="+generateProcessForm.getProgramTypeId();
		
		
		if(generateProcessForm.getCourseId()!=null && !generateProcessForm.getCourseId().isEmpty()){
			query=query+" and admAppln.course.id="+generateProcessForm.getCourseId();
		}
		if((generateProcessForm.getAppNoForm()!=null && !generateProcessForm.getAppNoForm().isEmpty())){
			query=query+"  and admAppln.applnNo>="+generateProcessForm.getAppNoForm();
		}
		if(generateProcessForm.getAppNoTo()!=null && !generateProcessForm.getAppNoTo().isEmpty()){
			query=query+"  and admAppln.applnNo<="+generateProcessForm.getAppNoTo();
		}
		if(!endTime.equals("00:00")){
			query=query+" and interviewCard.time<='"+endTime+"'";
		}
		if(!startTime.equals("00:00")){
			query=query+" and interviewCard.time>='"+startTime+"'";
		}
		return query;
	}
	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> convertBoToTO(List<AdmAppln> list,int courseId,int programId,HttpServletRequest request) throws Exception {
		log.info("Inside of populateAdmApplnBOtoTO of AdmissionStatusHelper");
		List<Integer> dupList=new ArrayList<Integer>();
		IGenerateProcessTransaction transaction=GenerateProcessTransactionImpl.getInstance();
		
		Map<Integer,String> admitCardMap=transaction.getTemplateDescriptions(CMSConstants.E_ADMITCARD_TEMPLATE,courseId,programId);
		Map<Integer,String> admissionCardMap=transaction.getTemplateDescriptions(CMSConstants.E_ADMISSIONCARD_TEMPLATE,courseId,programId);
		
		AdmissionStatusTO admissionStatusTO = null; 
		InterviewResultTO interviewResultTO = null;
		List<AdmissionStatusTO> admAppln = new ArrayList<AdmissionStatusTO>();		
		if (list != null && !list.isEmpty()) {
			Iterator<AdmAppln> iterator = list.iterator();
			while (iterator.hasNext()) {
			AdmAppln appln = iterator.next();
			if(!dupList.contains(appln.getId())){
			admissionStatusTO = new AdmissionStatusTO();
			
			/**
			 * Checks for the columns. If does not contain  null values then set those values from BO to TO
			 */
			admissionStatusTO.setId(appln.getId());
			if(appln.getIsBypassed()!= null && appln.getIsBypassed()){
				admissionStatusTO.setByPassed(appln.getIsBypassed());
			}
			
			if(appln.getIsSelected()!=null && appln.getPersonalData()!=null && appln.getPersonalData().getDateOfBirth()!=null 
			&& appln.getPersonalData().getId()!=0 && appln.getIsCancelled()!=null){
					
					admissionStatusTO.setApplicationNo(appln.getApplnNo());
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
			
			
			if(admissionStatusTO.getAdmStatus()== null || admissionStatusTO.getAdmStatus().trim().isEmpty()){

				if (admissionStatusTO.getIsSelected() == null || admissionStatusTO.getIsSelected().isEmpty())
				{
					admissionStatusTO.setIsSelected(CMSConstants.ADMISSION_ADMISSIONSTATUS_UNDER_PROCESS);
				}
				
				if(admissionStatusTO.getIsSelected()!=CMSConstants.SELECTED_FOR_ADMISSION && admissionStatusTO.isCancelled())
				{
					admissionStatusTO.setIsSelected(CMSConstants.ADMISSION_ADMISSIONSTATUS_APPLICATION_CANCELLED);
				}
				if(admissionStatusTO.getIsSelected()!=CMSConstants.SELECTED_FOR_ADMISSION)
				{
				//Used to get the interview status of the application					
				admissionStatusTO = GenerateProcessHandler.getInstance().getInterviewResult(String.valueOf(admissionStatusTO.getApplicationNo()), admissionStatusTO.getAppliedYear(),admissionStatusTO,appln);
				}		
				
				if(admissionStatusTO.getApplicationNo()>0){
					List interviewCardTOList = AdmissionStatusHandler.getInstance().getStudentsList(String.valueOf(admissionStatusTO.getApplicationNo()));
					if(interviewCardTOList!=null && !interviewCardTOList.isEmpty()){
						if(admissionStatusTO.getIsSelected()!= null){		
								if(admissionStatusTO.getIsSelected()==CMSConstants.SELECTED_FOR_ADMISSION){
									admissionStatusTO.setIsInterviewSelected(CMSConstants.ADMISSION);
								}
						}				
					}
					else{
						admissionStatusTO.setIsInterviewSelected("false");						
					}
				}		
			}
			
			
			if(admissionStatusTO.getAdmStatus()== null || admissionStatusTO.getAdmStatus().trim().isEmpty()){

				if (admissionStatusTO.getIsInterviewSelected() != null && !admissionStatusTO.getIsInterviewSelected().isEmpty())
				{
					if(admissionStatusTO.getIsInterviewSelected().equals("interview")){
						List<InterviewCard> admitCardList = AdmissionStatusHandler.getInstance().getStudentAdmitCardDetails(String.valueOf(appln.getApplnNo()), String.valueOf(admissionStatusTO.getInterviewProgramCourseId()));
						/*code added by sudhir */
						List<InterviewCardHistory> admitCardHistoryList = AdmissionStatusHandler.getInstance().getStudentAdmitCardHistoryDetails(String.valueOf(appln.getApplnNo()));
						/*---------------------*/
						if(admitCardList !=null && !admitCardList.isEmpty()){
							String finalTemplate =generateAdmitCard(admitCardMap.get(appln.getCourseBySelectedCourseId().getId()), admitCardList, request,admitCardHistoryList);
							admissionStatusTO.setFinalTemplate(finalTemplate);
						}
					}else if(admissionStatusTO.getIsInterviewSelected().equals("admission")){
						String finalTemplate =generateAdmissionCard(admissionCardMap.get(appln.getCourseBySelectedCourseId().getId()), appln, request);
						admissionStatusTO.setFinalTemplate(finalTemplate);
					}else if(admissionStatusTO.getIsSelected().equals("viewapplication")){
					}
					
				}		
			}
			if(admissionStatusTO.getInterviewStatus()!=null && admissionStatusTO.getInterviewStatus().equals("Application under Review")){
				admissionStatusTO.setIsInterviewSelected("viewapplication");		
			}
			admAppln.add(admissionStatusTO);
			dupList.add(appln.getId());
			}
			}
		}
		log.info("End of populateAdmApplnBOtoTO of AdmissionStatusHelper");
		return admAppln;
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
	 * @param templateDescription
	 * @param admitCardList
	 * @param request
	 * @param admitCardHistoryList 
	 * @return
	 * @throws Exception
	 */
	public static String generateAdmitCard(String templateDescription,
			List<InterviewCard> admitCardList, HttpServletRequest request, List<InterviewCardHistory> admitCardHistoryList)
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
		String mode="";
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
					if (session != null) {
						contextPath = request.getContextPath();
						contextPath = "<img src="
								+ contextPath
								+ "/PhotoServlet alt='Photo not available' width='150' height='150' >";
					}
//					if (applicantDetails.getAdmAppln().getApplnDocs() != null) {
//						Iterator<ApplnDoc> applnDocItr = applicantDetails
//								.getAdmAppln().getApplnDocs().iterator();
//
//						while (applnDocItr.hasNext()) {
//							ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
//							if (applnDoc.getIsPhoto() != null
//									&& applnDoc.getIsPhoto()) {
//								
//								
//							}
//						}
//					}
					
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
	 * 
	 * @param templateDescription
	 * @param applicantDetails
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static String generateAdmissionCard(String templateDescription,
			AdmAppln applicantDetails, HttpServletRequest request)
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
		String mode="";
		String admissionScheduledDate="";
		String admissionScheduledTime="";
		String specializationPrefered="";
		String approvedate="";
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
					if (session != null) {
						contextPath = request.getContextPath();
						contextPath = "<img src="
								+ contextPath
								+ "/PhotoServlet alt='Photo not available' width='150' height='150' >";
					}
//					if (applicantDetails.getApplnDocs() != null) {
//						Iterator<ApplnDoc> applnDocItr = applicantDetails
//								.getApplnDocs().iterator();
//
//						while (applnDocItr.hasNext()) {
//							ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
//							if (applnDoc.getIsPhoto() != null
//									&& applnDoc.getIsPhoto()) {
//								if (session != null) {
//									contextPath = request.getContextPath();
//									contextPath = "<img src="
//											+ contextPath
//											+ "/PhotoServlet alt='Photo not available' width='150' height='150' >";
//								}
//							}
//						}
//					}
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
					}
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
			finalTemplate=finalTemplate.replace(CMSConstants.TEMPLATE_MODE, mode);
			//Additions by Smitha- Modifications to display admission scheduled date and time.
			finalTemplate=finalTemplate.replace(CMSConstants.ADMISSION_SCHEDULED_DATE,admissionScheduledDate);
			finalTemplate=finalTemplate.replace(CMSConstants.ADMISSION_SCHEDULED_TIME,admissionScheduledTime);
			finalTemplate=finalTemplate.replace(CMSConstants.STUDENT_SPECIALIZATION_PREFERED,specializationPrefered);
			finalTemplate = finalTemplate.replace(CMSConstants.FINAL_APPROVE_DATE, approvedate);
		}
		log.info("End of generateAdmitCard of AdmissionStatusHelper");
		return finalTemplate;
	}
	
	/**
	 * Used to get the Interview Status
	 * Checks for the main round as well as subrounds of interview.
	 * Checks for interview card generation.
	 */
	public AdmissionStatusTO getInterviewStatus(AdmAppln admAppln,AdmissionStatusTO statusTO)throws Exception{
		log.info("Begin of getInterviewStatus of AdmissionStatusHelper");
		//get interview selecteds for main round check
		//get interview results for subround check
		
		Set<InterviewSelected> interviewSelectedSet = admAppln.getInterviewSelecteds();
		Set<InterviewResult> interviewresultSet = admAppln.getInterviewResults();
		boolean subroundexist=false;
		boolean cardgenerated=false;
		InterviewSelected oldInterviewSelected = null;
		
		String status="";
		if(interviewSelectedSet!=null && !interviewSelectedSet.isEmpty() && interviewSelectedSet.size()>1){   }
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
					
					Iterator<InterviewResult> resIterator = interviewresultSet.iterator();
					while (resIterator.hasNext()) {
						InterviewResult interviewResult = (InterviewResult) resIterator.next();
						//check subround exists or not
						if(interviewResult.getInterviewProgramCourse()!=null && interviewResult.getInterviewProgramCourse().getId()==selectProgcrsID){
							subroundexist=true;
							status=CMSConstants.RESULT_UNDER_PROCESS;
							if(interviewResult.getAdmAppln()!=null){
								if(interviewResult.getAdmAppln().getApplnNo()!=0){
								statusTO.setApplicationNo(interviewResult.getAdmAppln().getApplnNo());
								}if(interviewResult.getAdmAppln().getPersonalData()!=null){
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
								}if(interviewSelected.getAdmAppln().getPersonalData()!=null){
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
	 * @param list
	 * @param studentIds
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> getAdmissionStatusTosFromBo( List<AdmAppln> list, List<Integer> studentIds,boolean isAdmStatus,String admStatus,boolean isAdmissionCard,Map<Integer,String> templateMap,HttpServletRequest request,boolean isPreview) throws Exception {
		Iterator<AdmAppln> itr=list.iterator();
		List<AdmissionStatusTO> admList=new ArrayList<AdmissionStatusTO>();
		AdmissionStatusTO to;
		while (itr.hasNext()) {
			AdmAppln admAppln = itr.next();
			studentIds.remove(Integer.valueOf(admAppln.getId()));
			to=new AdmissionStatusTO();
			to.setApplicationNo(admAppln.getApplnNo());
			to.setAppliedYear(admAppln.getAppliedYear());
			to.setId(admAppln.getId());
			to.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
			to.setEmail(admAppln.getPersonalData().getEmail());
			if(isPreview){
				to.setAdmStatus(admStatus);
				to.setInterviewStatus(CMSConstants.ADMISSION_ADMISSIONSTATUS_UNDER_PROCESS);
				to.setIsInterviewSelected("viewapplication");
			}else if(isAdmStatus){
				to.setAdmStatus(admAppln.getAdmStatus());
			}else{
				to.setInterviewStatus(admStatus);
				to.setAdmStatus(null);
				String template=generateAdmissionCard(templateMap.get(admAppln.getCourseBySelectedCourseId().getId()),admAppln, request);
				to.setFinalTemplate(template);
				to.setIsInterviewSelected("admission");
			}
			to.setPersonalDataId(admAppln.getPersonalData().getId());
			to.setCourseId(admAppln.getCourseBySelectedCourseId().getId());
			admList.add(to);
		}
		return admList;
	}
	/**
	 * @param list
	 * @param studentIds
	 * @param request
	 * @param admitCardMap
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionStatusTO> getInterviewAdmitCard(List<InterviewCard> list, List<Integer> studentIds, HttpServletRequest request, Map<Integer, String> admitCardMap) throws Exception{
		Iterator<InterviewCard> itr=list.iterator();
		List<AdmissionStatusTO> admList=new ArrayList<AdmissionStatusTO>();
		AdmissionStatusTO to;
		while (itr.hasNext()) {
			InterviewCard card=itr.next();
			AdmAppln admAppln = card.getAdmAppln();
			studentIds.remove(Integer.valueOf(admAppln.getId()));
			to=new AdmissionStatusTO();
			to.setApplicationNo(admAppln.getApplnNo());
			to.setAppliedYear(admAppln.getAppliedYear());
			to.setId(admAppln.getId());
			to.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()),SQL_DATEFORMAT,SQL_DATEFORMAT));
			to.setEmail(admAppln.getPersonalData().getEmail());
			to.setAdmStatus(null);
			Set<InterviewResult> result=admAppln.getInterviewResults();
			boolean isContain=false;
			String resultRound="";
			if(result!=null && !result.isEmpty()){
				Iterator<InterviewResult> it=result.iterator();
				while (it.hasNext()) {
					InterviewResult ir = it.next();
					if(ir.getInterviewProgramCourse()!=null && ir.getInterviewProgramCourse().getId()==card.getInterview().getInterview().getInterviewProgramCourse().getId()){
						isContain=true;
						resultRound=ir.getInterviewProgramCourse().getName();
					}
				}
			}
			if(!isContain){
				List<InterviewCard> l=new ArrayList<InterviewCard>();
				l.add(card);
				/*code added by sudhir */
				List<InterviewCardHistory> admitCardHistoryList = AdmissionStatusHandler.getInstance().getStudentAdmitCardHistoryDetails(String.valueOf(admAppln.getApplnNo()));
				/*---------------------*/
				String template=generateAdmitCard(admitCardMap.get(admAppln.getCourseBySelectedCourseId().getId()),l, request,admitCardHistoryList);
				to.setFinalTemplate(template);
				to.setIsInterviewSelected("interview");
				to.setInterviewStatus(CMSConstants.CALL_FOR+card.getInterview().getInterview().getInterviewProgramCourse().getName());
				to.setInterviewTime(card.getTime());
				to.setInterviewDate(card.getInterview().getDate());
			}else{
				to.setIsInterviewSelected(null);
				to.setInterviewStatus(resultRound+" Results under process");
			}
			to.setPersonalDataId(admAppln.getPersonalData().getId());
			to.setCourseId(admAppln.getCourseBySelectedCourseId().getId());
			admList.add(to);
		}
		return admList;
	}
	public static String getTimeByDate(Date dateString) {
    	final long timestamp = dateString.getTime();

    	// with java.util.Date/Calendar api
    	final Calendar cal = Calendar.getInstance();
    	cal.setTimeInMillis(timestamp);
    	// and here's how to get the String representation
    	final String timeString =
    	    new SimpleDateFormat("HH:mm:ss a").format(cal.getTime());
		 return timeString;
			}
	
}
