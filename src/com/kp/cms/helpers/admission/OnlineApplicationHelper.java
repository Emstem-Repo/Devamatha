package com.kp.cms.helpers.admission;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Address;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantMarksDetails;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ApplnDocDetails;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentExtracurricular;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplnDocDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePGIDetailsTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.to.admission.coursesToPrint;
import com.kp.cms.transactions.admission.IOnlineApplicationTxn;
import com.kp.cms.transactionsimpl.admission.OnlineApplicationImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

	@SuppressWarnings("unchecked")
	public class OnlineApplicationHelper {
		
		private static final Log log = LogFactory.getLog(OnlineApplicationHelper.class);
		private static final String OTHER="Other";
		private static final String PHOTO="Photo";
		private static final String FROM_DATEFORMAT="dd/MM/yyyy";
		private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
		//private static final String SQL_DATEFORMAT="yyyy-MM-dd";
		private static final String MANAGEMENT_QUOTA_FORM = "Management Quota Form";
		public static volatile OnlineApplicationHelper self=null;
		
		public static OnlineApplicationHelper getInstance(){
			if(self==null){
				self= new OnlineApplicationHelper();
			}
			return self;
		}
		
		private OnlineApplicationHelper(){
			
		}
		
		IOnlineApplicationTxn txn= new OnlineApplicationImpl();
		
		
		
		public AdmAppln getBasicPageApplicantBO(OnlineApplicationForm admForm) throws Exception {
			log.info("enter getBasicPageApplicantBO" );
			AdmAppln appBO=null;
			if (admForm!=null) {
				appBO= new AdmAppln();
				appBO.setAppliedYear(Integer.parseInt(admForm.getApplicationYear()));
				
					appBO.setMode("Online");
				
				//Course cs=new Course();
				//cs.setId(Integer.parseInt(admForm.getCourseId()));
				
				//appBO.setCourse(cs);
				//appBO.setCourseBySelectedCourseId(cs);
				appBO.setIsCancelled(false);
				appBO.setIsSelected(false);
				appBO.setIsApproved(false);
				appBO.setIsFinalMeritApproved(false);
				appBO.setIsLig(false);
				appBO.setIsBypassed(false);
				appBO.setIsChallanVerified(false);
				appBO.setIsDraftMode(true);
				appBO.setIsDraftCancelled(false);
				appBO.setCurrentPageName("basic");
				appBO.setCreatedBy(admForm.getUserId());
				appBO.setCreatedDate(new Date());
				appBO.setModifiedBy(admForm.getUserId());
				appBO.setLastModifiedDate(new Date());
				//appBO.setJournalNo(admForm.getJournalNo());
				
				if(admForm.getUniqueId()!=null && !admForm.getUniqueId().isEmpty()){
					StudentOnlineApplication onlineApplication = new StudentOnlineApplication();
					onlineApplication.setId(Integer.parseInt(admForm.getUniqueId()));
					appBO.setStudentOnlineApplication(onlineApplication);
				}
				
				
			}
			return appBO;
		}
		
		public List<CourseTO> copyCourseBosToTos(List<Course> courseList, int id) {
			List<CourseTO> courseToList = new ArrayList<CourseTO>();
			Iterator<Course> iterator = courseList.iterator();
			Course course;
			CourseTO courseTO;
			
			while (iterator.hasNext()) {
				courseTO = new CourseTO();
				ProgramTO programTO = new ProgramTO();
				ProgramTypeTO programTypeTO = new ProgramTypeTO();
				CurrencyMasterTO currenctTo=new CurrencyMasterTO();
				course = (Course) iterator.next();
				courseTO.setLocationId(course.getWorkLocation().getId());
				courseTO.setId(course.getId());
				if (id == 0) {
					courseTO.setName(splitString(course.getName()));
				} else {
					courseTO.setName(course.getName());
				}
				courseTO.setCode(course.getCode());
				courseTO.setIsActive(course.getIsActive());
				courseTO.setAmount(course.getAmount());
				courseTO.setPayCode(course.getPayCode());
				courseTO.setWorkExpMandatory(course.getIsWorkExperienceMandatory());
				//raghu
				//courseTO.setInterdisiplinary(String.valueOf(course.getIsInterdisciplinary()));
				if(course.getBankIncludeSection()!=null)
				{
				courseTO.setBankIncludeSection(course.getBankIncludeSection());
				}if(course.getCommencementDate()!=null){
				courseTO.setCommencementDate(CommonUtil.formatDate(course.getCommencementDate(),"dd/MM/yyyy"));
				}if(course.getCurrencyId()!=null){
				currenctTo.setId(course.getCurrencyId().getId());
				courseTO.setCurrencyId(currenctTo);
				}if(course.getApplicationFees()!=null){
					courseTO.setIntApplicationFees(course.getApplicationFees());
				}
				courseTO.setAppearInOnline(course.getIsAppearInOnline());
				courseTO.setApplicationProcessSms(course.getIsApplicationProcessSms());
				if(course.getOnlyForApplication()!=null)
				courseTO.setOnlyForApplication(course.getOnlyForApplication());
				
				programTypeTO.setProgramTypeId(course.getProgram().getProgramType().getId());
				programTypeTO.setProgramTypeName(course.getProgram().getProgramType().getName());
				
				programTypeTO.setAgeFrom(String.valueOf(course.getProgram().getProgramType().getAgeFrom()));
				programTypeTO.setAgeTo(String.valueOf(course.getProgram().getProgramType().getAgeTo()));

				programTO.setId(course.getProgram().getId());
				programTO.setName(course.getProgram().getName());
				
				
				//this is old code
				programTO.setId(course.getProgram().getId());
				programTO.setName(course.getProgram().getName());
				programTO.setIsMotherTongue(course.getProgram().getIsMotherTongue());
				programTO.setIsDisplayLanguageKnown(course.getProgram().getIsDisplayLanguageKnown());
				programTO.setIsHeightWeight(course.getProgram().getIsHeightWeight());
				programTO.setIsDisplayTrainingCourse(course.getProgram().getIsDisplayTrainingCourse());
				programTO.setIsAdditionalInfo(course.getProgram().getIsAdditionalInfo());
				programTO.setIsExtraDetails(course.getProgram().getIsExtraDetails());
				programTO.setIsSecondLanguage(course.getProgram().getIsSecondLanguage());
				programTO.setIsFamilyBackground(course.getProgram().getIsFamilyBackground());
				programTO.setIsLateralDetails(course.getProgram().getIsLateralDetails());
				programTO.setIsTransferCourse(course.getProgram().getIsTransferCourse());
				programTO.setIsEntranceDetails(course.getProgram().getIsEntranceDetails());
				programTO.setIsTCDetails(course.getProgram().getIsTCDetails());
				programTO.setIsExamCenterRequired(course.getProgram().getIsExamCenterRequired());
				programTO.setProgramTypeTo(programTypeTO);
				//old code end
				
				
				courseTO.setIsMotherTongue(course.getProgram().getIsMotherTongue());
				courseTO.setIsDisplayLanguageKnown(course.getProgram().getIsDisplayLanguageKnown());
				courseTO.setIsHeightWeight(course.getProgram().getIsHeightWeight());
				//courseTO.setIsDisplayTrainingCourse(course.getIsTrainingShortCourse());
				courseTO.setIsAdditionalInfo(course.getProgram().getIsAdditionalInfo());
				courseTO.setIsExtraDetails(course.getProgram().getIsExtraDetails());
				courseTO.setIsSecondLanguage(course.getProgram().getIsSecondLanguage());
				courseTO.setIsFamilyBackground(course.getProgram().getIsFamilyBackground());
				courseTO.setIsLateralDetails(course.getProgram().getIsLateralDetails());
				courseTO.setIsTransferCourse(course.getProgram().getIsTransferCourse());
				courseTO.setIsEntranceDetails(course.getProgram().getIsEntranceDetails());
				courseTO.setIsTCDetails(course.getProgram().getIsTCDetails());
				//courseTO.setIsExamCenterRequired(course.getIsSeatNoGenRequired());
				programTO.setProgramTypeTo(programTypeTO);
				courseTO.setProgramTo(programTO);
				courseTO.setMaxInTake(course.getMaxIntake());
				//courseTO.setStatusTextOnAcknowledgementOffline(course.getStatusTextOnAcknowledgementOffline());
				//courseTO.setStatusTextOnAcknowledgementOnline(course.getStatusTextOnAcknowledgementOnline());
				//courseTO.setStatusTextOnSubmisstionOfApplnOffline(course.getStatusTextOnSubmisstionOfApplnOffline());
				//courseTO.setStatusTextOnSubmisstionOfApplnOnline(course.getStatusTextOnSubmisstionOfApplnOnline());
				if (course.getIsAutonomous().equals(true)) {
					courseTO.setIsAutonomous("Yes");
				} else {
					courseTO.setIsAutonomous("No");
				}
				if (course.getIsWorkExperienceRequired().equals(true)) {
					courseTO.setIsWorkExperienceRequired("Yes");
				} else {
					courseTO.setIsWorkExperienceRequired("No");
				}
				if (course.getIsDetailMarksPrint().equals(true)) {
					courseTO.setIsDetailMarkPrint("Yes");
				} else {
					courseTO.setIsDetailMarkPrint("No");
				}
				
				Set<SeatAllocation> seatAllocSet = course.getSeatAllocations();
				Iterator<SeatAllocation> it = seatAllocSet.iterator();
				List<SeatAllocationTO> tempList = new ArrayList<SeatAllocationTO>();
				while (it.hasNext()) {
					SeatAllocationTO seatAllocationTo = new SeatAllocationTO();
					AdmittedThroughTO admittedThroughTO = new AdmittedThroughTO();
					SeatAllocation seatAlloc = (SeatAllocation) it.next();
					seatAllocationTo.setId(seatAlloc.getId());

					admittedThroughTO.setId(seatAlloc.getAdmittedThrough().getId());
					admittedThroughTO.setName(seatAlloc.getAdmittedThrough()
							.getName());
					seatAllocationTo.setAdmittedThroughTO(admittedThroughTO);

					seatAllocationTo.setAdmittedThroughId(seatAlloc
							.getAdmittedThrough().getId());
					seatAllocationTo.setCourseId(seatAlloc.getCourse().getId());
					seatAllocationTo.setNoofSeats(seatAlloc.getNoOfSeats());
					tempList.add(seatAllocationTo);
				}
				courseTO.setSeatAllocation(tempList);
				courseTO.setCertificateCourseName(course.getCertificateCourseName());
				if(course.getBankName()!=null && !course.getBankName().isEmpty()){
					courseTO.setBankName(course.getBankName());
				}
				if(course.getBankNameFull()!=null && !course.getBankNameFull().isEmpty()){
					courseTO.setBankNameFull(course.getBankNameFull());
				}
				if(course.getCourseMarksCard()!=null && !course.getCourseMarksCard().isEmpty()){
					courseTO.setCourseMarksCard(course.getCourseMarksCard());
				}
				if(course.getNoOfAttemtsMidSem()!=null && course.getNoOfAttemtsMidSem()!=0){
					courseTO.setNoOfMidsemAttempts(course.getNoOfAttemtsMidSem());
				}
				
				//end by giri
				courseToList.add(courseTO);
			}
			log.error("ending of copyCourseBosToTos method in CourseHelper");
			return courseToList;
		}
		
		/**
		 * prepares student personaldata to from form
		 * @param admForm
		 * @return
		 */
		/*public PersonalDataTO getStudentPersonaldataTofromForm(OnlineApplicationForm admForm) {
			log.info("enter getStudentPersonaldataTofromForm" );
			PersonalDataTO personaldataTO= new PersonalDataTO();
			//set name details
			personaldataTO.setFirstName(admForm.getFirstName());
			personaldataTO.setMiddleName(admForm.getMiddleName());
			personaldataTO.setLastName(admForm.getLastName());
			//set birth details
			personaldataTO.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(admForm.getDateOfBirth()));
			personaldataTO.setBirthPlace(admForm.getBirthPlace());
			personaldataTO.setSportsPerson(admForm.isSportsPerson());
			personaldataTO.setHandicapped(admForm.isHandicapped());
			personaldataTO.setSportsDescription(admForm.getSportsDescription());
			personaldataTO.setHadnicappedDescription(admForm.getHadnicappedDescription());
			
			//raghu
			personaldataTO.setSports(admForm.getSports());
			personaldataTO.setArts(admForm.getArts());
			personaldataTO.setSportsParticipate(admForm.getSportsParticipate());
			personaldataTO.setArtsParticipate(admForm.getArtsParticipate());
			personaldataTO.setFatherMobile(admForm.getFatherMobile());
			personaldataTO.setMotherMobile(admForm.getMotherMobile());
			
			personaldataTO.setHeight(admForm.getHeight());
			personaldataTO.setWeight(admForm.getWeight());
			personaldataTO.setLanguageByLanguageRead(admForm.getLanguageRead());
			personaldataTO.setLanguageByLanguageSpeak(admForm.getLanguageSpeak());
			personaldataTO.setLanguageByLanguageWrite(admForm.getLanguageWrite());
			personaldataTO.setMotherTongue(admForm.getMotherTongue());
			
			personaldataTO.setTrainingInstAddress(admForm.getTrainingInstAddr());
			personaldataTO.setTrainingProgName(admForm.getTrainingProgName());
			personaldataTO.setTrainingPurpose(admForm.getTrainingPurpose());
			if(admForm.getTrainingDuration()!=null && !StringUtils.isEmpty(admForm.getTrainingDuration()) && StringUtils.isNumeric(admForm.getTrainingDuration())){
				personaldataTO.setTrainingDuration(admForm.getTrainingDuration());
			}
			
			personaldataTO.setCourseKnownBy(admForm.getCourseKnownBy());
			personaldataTO.setCourseOptReason(admForm.getCourseOptReason());
			personaldataTO.setStrength(admForm.getStrength());
			personaldataTO.setWeakness(admForm.getWeakness());
			personaldataTO.setOtherAddnInfo(admForm.getOtherAddnInfo());
			
			personaldataTO.setSecondLanguage(admForm.getSecondLanguage());
			
			if(admForm.getExtracurricularIds()!=null && admForm.getExtracurricularIds().length>0){
				String[] extraIDs=admForm.getExtracurricularIds();
				List<StudentExtracurricular> extracurrs=new ArrayList<StudentExtracurricular>();
				for(int i=0;i<extraIDs.length;i++){
					StudentExtracurricular extr= new StudentExtracurricular();
					ExtracurricularActivity extrAct= new ExtracurricularActivity();
					extrAct.setId(Integer.parseInt(extraIDs[i]));
					extrAct.setIsActive(true);
					extr.setExtracurricularActivity(extrAct);
					extr.setCreatedBy(admForm.getUserId());
					extr.setCreatedDate(new Date());
					extr.setIsActive(true);
					extracurrs.add(extr);
				}
				personaldataTO.setStudentExtracurriculars(extracurrs);
			}
			
			
			personaldataTO.setBirthCountry(admForm.getCountry());
			if (admForm.getBirthState()!=null && admForm.getBirthState().equalsIgnoreCase(OnlineApplicationHelper.OTHER)) {
				personaldataTO.setBirthState(OnlineApplicationHelper.OTHER);
				personaldataTO.setStateOthers(admForm.getOtherBirthState());
			}else{
				personaldataTO.setBirthState(admForm.getBirthState());
			}
			if(admForm.getPassportcountry()!=null && !StringUtils.isEmpty(admForm.getPassportcountry())){
			personaldataTO.setPassportCountry(Integer.parseInt(admForm.getPassportcountry()));
			}
			if(admForm.getPassportNo()!=null && !StringUtils.isEmpty(admForm.getPassportNo())){
			personaldataTO.setPassportNo(admForm.getPassportNo());
			}
			if(admForm.getResidentPermit()!=null && !StringUtils.isEmpty(admForm.getResidentPermit())){
				personaldataTO.setResidentPermitNo(admForm.getResidentPermit());
			}
			if(admForm.getPassportValidity()!=null && !StringUtils.isEmpty(admForm.getPassportValidity())){
			personaldataTO.setPassportValidity(admForm.getPassportValidity());
			}
			if(admForm.getPermitDate()!=null && !StringUtils.isEmpty(admForm.getPermitDate())){
				personaldataTO.setResidentPermitDate(admForm.getPermitDate());
			}
			personaldataTO.setCitizenship(admForm.getNationality());
			
			personaldataTO.setResidentCategory(admForm.getResidentCategory());
			personaldataTO.setGender(admForm.getGender());
			if(admForm.getBloodGroup()!=null){
			personaldataTO.setBloodGroup(admForm.getBloodGroup().toUpperCase());
			}
			personaldataTO.setPhNo1(admForm.getPhone1());
			personaldataTO.setPhNo2(admForm.getPhone2());
			personaldataTO.setPhNo3(admForm.getPhone3());
			personaldataTO.setMobileNo1(admForm.getMobile1());
			personaldataTO.setMobileNo2(admForm.getMobile2());
			personaldataTO.setMobileNo3(admForm.getMobile3());
			personaldataTO.setEmail(admForm.getEmailId());
			
			if (admForm.getCastCategory()!=null && admForm.getCastCategory().equalsIgnoreCase(OnlineApplicationHelper.OTHER) ) {
				personaldataTO.setCaste(null);
				personaldataTO.setCasteOthers(admForm.getOtherCastCategory());
			}else if(admForm.getCastCategory()!=null && !StringUtils.isEmpty( admForm.getCastCategory()) && StringUtils.isNumeric( admForm.getCastCategory())){
				CasteTO casteTO = new CasteTO();
				casteTO.setCasteId(Integer.parseInt(admForm.getCastCategory()));
				personaldataTO.setCaste(casteTO);
			}
			personaldataTO.setRuralUrban(admForm.getAreaType());
			ReligionTO religionTO= new ReligionTO();
			if (admForm.getReligionId()!=null && admForm.getReligionId().equalsIgnoreCase(OnlineApplicationHelper.OTHER) ) {
				personaldataTO.setReligion(null);
				personaldataTO.setReligionOthers(admForm.getOtherReligion());
			}else if(admForm.getReligionId()!=null){
				
				religionTO.setReligionId(Integer.parseInt(admForm.getReligionId()));
				personaldataTO.setReligion(religionTO);
			}
			
			if (admForm.getSubReligion()!=null && admForm.getSubReligion().equalsIgnoreCase(OnlineApplicationHelper.OTHER)) {
				personaldataTO.setReligionSection(null);
				personaldataTO.setReligionSectionOthers(admForm.getOtherSubReligion());
			}else if(admForm.getSubReligion()==null || StringUtils.isEmpty(admForm.getSubReligion())){
				personaldataTO.setReligionSection(null);
			}else{
				
				ReligionSectionTO subreligionto=new ReligionSectionTO();
				
				subreligionto.setReligionTO(religionTO);
				subreligionto.setId(Integer.parseInt(admForm.getSubReligion()));
				personaldataTO.setReligionSection(subreligionto);
			}
			log.info("exit getStudentPersonaldataTofromForm" );
			return personaldataTO;
	}*/
		
		 /**
		  * doc upload to to BO conversion
		 * @param uploadtoList
		 * @param userId 
		 * @return
		 */
		public List<ApplnDoc> getDocUploadBO(List<ApplnDocTO> uploadtoList, String userId){
			log.info("enter getDocUploadBO" );
			List<ApplnDoc> uploadbolist=new ArrayList<ApplnDoc>();
		
			 if(uploadtoList!=null && !uploadtoList.isEmpty()){
				 Iterator<ApplnDocTO> toItr=uploadtoList.iterator();
				 while (toItr.hasNext()) {
					ApplnDocTO uploadto = (ApplnDocTO) toItr.next();
					ApplnDoc uploadBO= new ApplnDoc();
					uploadBO.setCreatedBy(userId);
					uploadBO.setCreatedDate(new Date());
					if (uploadto.getDocTypeId()!=0) {
						DocType doctype = new DocType();
						doctype.setId(uploadto.getDocTypeId());
						doctype.setPrintName(uploadto.getPrintName());
						uploadBO.setDocType(doctype);
					}else{
						uploadBO.setDocType(null);
					}
					uploadBO.setIsVerified(false);
					uploadBO.setIsPhoto(uploadto.isPhoto());
					uploadBO.setIsMngQuotaForm(uploadto.isMngQuotaForm());
					
						try {
							if(uploadto.getDocument()!=null 
									&& uploadto.getDocument().getFileName()!=null 
									&& !StringUtils.isEmpty(uploadto.getDocument().getFileName())){
								uploadBO.setDocument(uploadto.getDocument().getFileData());
								uploadBO.setName(uploadto.getDocument().getFileName());
								
								uploadBO.setContentType(uploadto.getDocument().getContentType());
								uploadbolist.add(uploadBO);	
							}else if(uploadto.isPhoto() 
									&& (uploadto.getDocument()==null || uploadto.getDocument().getFileName()==null 
									|| StringUtils.isEmpty(uploadto.getDocument().getFileName())))
							{
								// set default photo image
								try {
									// Code Commented By Balaji Christ is Not Required default Images
//									InputStream photoin=OnlineApplicationHelper.class.getClassLoader().getResourceAsStream(CMSConstants.DEFAULT_PHOTO_PATH);
								/*	InputStream photoin=null;
									if(photoin!=null){
										byte[] fileData= new byte[photoin.available()];
										photoin.read(fileData, 0, photoin.available());
										uploadBO.setDocument(fileData);
										uploadBO.setName(OnlineApplicationHelper.PHOTO);
										uploadBO.setContentType("image/gif");
										uploadbolist.add(uploadBO);	
									}*/
								} catch (Exception e) {
									log.error(e);
								}
							}
						} catch (FileNotFoundException e) {
							log.error(e);
						} catch (IOException e) {
							log.error(e);
						}
					
				}
			 }
				log.info("exit getDocUploadBO" );
				return uploadbolist;
		 }
		
		
		

		/**
		 * personal data TO to BO conversion
		 * @param studentpersonaldata
		 * @return
		 */
		public PersonalData convertPersonalDataTOtoBO(
				PersonalDataTO studentpersonaldata) {
			log.info("enter convertPersonalDataTOtoBO" );
			PersonalData personaldataBO=new PersonalData();
			personaldataBO.setBirthPlace(studentpersonaldata.getBirthPlace());
			if(studentpersonaldata.getBloodGroup()!=null)
			personaldataBO.setBloodGroup(studentpersonaldata.getBloodGroup().toUpperCase());
			personaldataBO.setIsSportsPerson(studentpersonaldata.isSportsPerson());
			personaldataBO.setIsHandicapped(studentpersonaldata.isHandicapped());
			personaldataBO.setSportsPersonDescription(studentpersonaldata.getSportsDescription());
			personaldataBO.setHandicappedDescription(studentpersonaldata.getHadnicappedDescription());
			if(studentpersonaldata.getHeight()!=null && !StringUtils.isEmpty(studentpersonaldata.getHeight()) && StringUtils.isNumeric(studentpersonaldata.getHeight()))
				personaldataBO.setHeight(Integer.parseInt(studentpersonaldata.getHeight()));
			if(studentpersonaldata.getWeight()!=null && !StringUtils.isEmpty(studentpersonaldata.getWeight()) && CommonUtil.isValidDecimal(studentpersonaldata.getWeight()))
				personaldataBO.setWeight(new BigDecimal(studentpersonaldata.getWeight()));
			if(studentpersonaldata.getLanguageByLanguageRead()!=null && !StringUtils.isEmpty(studentpersonaldata.getLanguageByLanguageRead()) ){
				
				personaldataBO.setLanguageByLanguageRead(studentpersonaldata.getLanguageByLanguageRead());
			}else{
				personaldataBO.setLanguageByLanguageRead(null);
			}
			if(studentpersonaldata.getLanguageByLanguageWrite()!=null && !StringUtils.isEmpty(studentpersonaldata.getLanguageByLanguageWrite()) ){
				
				personaldataBO.setLanguageByLanguageWrite(studentpersonaldata.getLanguageByLanguageWrite());
			}else{
				personaldataBO.setLanguageByLanguageWrite(null);
			}
			if(studentpersonaldata.getLanguageByLanguageSpeak()!=null && !StringUtils.isEmpty(studentpersonaldata.getLanguageByLanguageSpeak()) ){
				
				personaldataBO.setLanguageByLanguageSpeak(studentpersonaldata.getLanguageByLanguageSpeak());
			}else{
				personaldataBO.setLanguageByLanguageSpeak(null);
			}
			if(studentpersonaldata.getMotherTongue()!=null && !StringUtils.isEmpty(studentpersonaldata.getMotherTongue()) && StringUtils.isNumeric(studentpersonaldata.getMotherTongue())){
				MotherTongue readlang= new MotherTongue();
				readlang.setId(Integer.parseInt(studentpersonaldata.getMotherTongue()));
				personaldataBO.setMotherTongue(readlang);
			}else{
				personaldataBO.setMotherTongue(null);
			}
			
			if(studentpersonaldata.getTrainingDuration()!=null && !StringUtils.isEmpty(studentpersonaldata.getTrainingDuration())){
			personaldataBO.setTrainingDuration(Integer.parseInt(studentpersonaldata.getTrainingDuration()));
			}else{
				personaldataBO.setTrainingDuration(0);
			}
			personaldataBO.setTrainingProgName(studentpersonaldata.getTrainingProgName());
			personaldataBO.setTrainingInstAddress(studentpersonaldata.getTrainingInstAddress());
			personaldataBO.setTrainingPurpose(studentpersonaldata.getTrainingPurpose());
			
			personaldataBO.setCourseKnownBy(studentpersonaldata.getCourseKnownBy());
			personaldataBO.setCourseOptReason(studentpersonaldata.getCourseOptReason());
			personaldataBO.setStrength(studentpersonaldata.getStrength());
			personaldataBO.setWeakness(studentpersonaldata.getWeakness());
			personaldataBO.setOtherAddnInfo(studentpersonaldata.getOtherAddnInfo());
			
			personaldataBO.setSecondLanguage(studentpersonaldata.getSecondLanguage());
			
			Set<StudentExtracurricular> extracurrs=new HashSet<StudentExtracurricular>();
			if(studentpersonaldata.getStudentExtracurriculars()!=null && !studentpersonaldata.getStudentExtracurriculars().isEmpty())
			{
				extracurrs.addAll(studentpersonaldata.getStudentExtracurriculars());
				personaldataBO.setStudentExtracurriculars(extracurrs);
			}
			
			if (studentpersonaldata.getCitizenship()!=null && !StringUtils.isEmpty(studentpersonaldata.getCitizenship())&& StringUtils.isNumeric(studentpersonaldata.getCitizenship())) {
				Nationality nat= new Nationality();
				nat.setId(Integer.parseInt(studentpersonaldata.getCitizenship()));
				personaldataBO.setNationality(nat);
			}else{
				personaldataBO.setNationalityOthers(studentpersonaldata.getNationalityOthers());
			}
			personaldataBO.setEmail(studentpersonaldata.getEmail());
			personaldataBO.setFirstName(studentpersonaldata.getFirstName());
			if(studentpersonaldata.getGender()!=null){
			personaldataBO.setGender(studentpersonaldata.getGender().toUpperCase());
			}
			personaldataBO.setDateOfBirth(studentpersonaldata.getDateOfBirth());
			ResidentCategory res_cat=null;
			if (studentpersonaldata.getResidentCategory()!=null && !StringUtils.isEmpty(studentpersonaldata.getResidentCategory()) && StringUtils.isNumeric(studentpersonaldata.getResidentCategory())) {
				res_cat = new ResidentCategory();
				res_cat.setId(Integer.parseInt(studentpersonaldata.getResidentCategory()));
			}
			
			
			
			//raghu
			personaldataBO.setSports(studentpersonaldata.getSports());
			personaldataBO.setArts(studentpersonaldata.getArts());
			personaldataBO.setSportsParticipate(studentpersonaldata.getSportsParticipate());
			personaldataBO.setArtsParticipate(studentpersonaldata.getArtsParticipate());
			personaldataBO.setFatherMobile(studentpersonaldata.getFatherMobile());
			personaldataBO.setMotherMobile(studentpersonaldata.getMotherMobile());
			
			personaldataBO.setResidentCategory(res_cat);
			personaldataBO.setRuralUrban(studentpersonaldata.getRuralUrban());
			personaldataBO.setPhNo1(studentpersonaldata.getPhNo1());
			personaldataBO.setPhNo2(studentpersonaldata.getPhNo2());
			personaldataBO.setPhNo3(studentpersonaldata.getPhNo3());
			personaldataBO.setMobileNo1(studentpersonaldata.getMobileNo1());
			personaldataBO.setMobileNo2(studentpersonaldata.getMobileNo2());
			personaldataBO.setMobileNo3(studentpersonaldata.getMobileNo3());
			personaldataBO.setLastName(studentpersonaldata.getLastName());
			personaldataBO.setMiddleName(studentpersonaldata.getMiddleName());
			
			/*if(studentpersonaldata.getReligion()!=null){
			Religion religionbo= new Religion();
			religionbo.setId(studentpersonaldata.getReligion().getReligionId());
			
				if (studentpersonaldata.getReligionSection()!=null) {
					ReligionSection subreligionBO = new ReligionSection();
					subreligionBO.setId(studentpersonaldata.getReligionSection()
							.getId());
					subreligionBO.setReligion(religionbo);
					personaldataBO.setReligionSection(subreligionBO);
				}else if(studentpersonaldata.getReligionSectionOthers()!=null && !StringUtils.isEmpty(studentpersonaldata.getReligionSectionOthers())){
					personaldataBO.setReligionSection(null);
					personaldataBO.setReligionSectionOthers(studentpersonaldata.getReligionSectionOthers());
				}else{
					personaldataBO.setReligionSection(null);
				}
				personaldataBO.setReligion(religionbo);	
			}else{
				personaldataBO.setReligion(null);	
				personaldataBO.setReligionOthers(studentpersonaldata.getReligionOthers());
				personaldataBO.setReligionSection(null);
				personaldataBO.setReligionSectionOthers(studentpersonaldata.getReligionSectionOthers());
			}
			*/
			
			//raghu write new
			Religion religionbo=null;
			if(studentpersonaldata.getReligionId()!=null && !StringUtils.isEmpty(studentpersonaldata.getReligionId()) && StringUtils.isNumeric(studentpersonaldata.getReligionId())){
				religionbo= new Religion();
				religionbo.setId(Integer.parseInt(studentpersonaldata.getReligionId()));
					if (studentpersonaldata.getSubReligionId()!=null && !StringUtils.isEmpty(studentpersonaldata.getSubReligionId()) && StringUtils.isNumeric(studentpersonaldata.getSubReligionId())) {
						ReligionSection subreligionBO = new ReligionSection();
						subreligionBO.setId(Integer.parseInt(studentpersonaldata.getSubReligionId()));
						subreligionBO.setReligion(religionbo);
						personaldataBO.setReligionSection(subreligionBO);
					}else{
						personaldataBO.setReligionSection(null);
						personaldataBO.setReligionSectionOthers(studentpersonaldata.getReligionSectionOthers());
					}
					personaldataBO.setReligion(religionbo);	
				}else{
					personaldataBO.setReligion(null);	
					personaldataBO.setReligionOthers(studentpersonaldata.getReligionOthers());
					if (studentpersonaldata.getSubReligionId()!=null && !StringUtils.isEmpty(studentpersonaldata.getSubReligionId()) && StringUtils.isNumeric(studentpersonaldata.getSubReligionId())) {
						ReligionSection subreligionBO = new ReligionSection();
						subreligionBO.setId(Integer.parseInt(studentpersonaldata.getSubReligionId()));
						subreligionBO.setReligion(religionbo);
						personaldataBO.setReligionSection(subreligionBO);
					}else{
						personaldataBO.setReligionSection(null);
						personaldataBO.setReligionSectionOthers(studentpersonaldata.getReligionSectionOthers());
					}
				}
			
			
			if (studentpersonaldata.getCaste()!=null) {
				Caste casteBO = new Caste();
				casteBO.setId(studentpersonaldata.getCaste().getCasteId());
				personaldataBO.setCaste(casteBO);
			}else{
				personaldataBO.setCaste(null);
				personaldataBO.setCasteOthers(studentpersonaldata.getCasteOthers());
			}
			
			
			personaldataBO.setPassportNo(studentpersonaldata.getPassportNo());
			personaldataBO.setResidentPermitNo(studentpersonaldata.getResidentPermitNo());
			if(studentpersonaldata.getPassportValidity()!=null && !StringUtils.isEmpty(studentpersonaldata.getPassportValidity()))

			personaldataBO.setPassportValidity(CommonUtil.ConvertStringToSQLDate(studentpersonaldata.getPassportValidity()));
			
			if(studentpersonaldata.getResidentPermitDate()!=null && !StringUtils.isEmpty(studentpersonaldata.getResidentPermitDate())){
				personaldataBO.setResidentPermitDate(CommonUtil.ConvertStringToSQLDate(studentpersonaldata.getResidentPermitDate()));
			}
			if (studentpersonaldata.getPassportCountry()!=0) {
				Country passportcnt = new Country();
				passportcnt.setId(studentpersonaldata.getPassportCountry());
				personaldataBO.setCountryByPassportCountryId(passportcnt);
			}
			if(studentpersonaldata.getBirthCountry()!=null && !StringUtils.isEmpty(studentpersonaldata.getBirthCountry().trim()) && StringUtils.isNumeric(studentpersonaldata.getBirthCountry())){
			Country ownCnt= new Country();
			ownCnt.setId(Integer.parseInt(studentpersonaldata.getBirthCountry()));
			personaldataBO.setCountryByCountryId(ownCnt);
			}else{
				personaldataBO.setCountryByCountryId(null);
			}
				
			
			if (studentpersonaldata.getBirthState()!=null && !StringUtils.isEmpty(studentpersonaldata.getBirthState()) && StringUtils.isNumeric(studentpersonaldata.getBirthState())) {
				State ownSt = new State();
				ownSt.setId(Integer.parseInt(studentpersonaldata.getBirthState()));
				personaldataBO.setStateByStateId(ownSt);
			}else{
				personaldataBO.setStateOthers(studentpersonaldata.getStateOthers());
			}
			if (studentpersonaldata.getNationality()!=null && !StringUtils.isEmpty(studentpersonaldata.getNationality()) && StringUtils.isNumeric(studentpersonaldata.getNationality())) {
				Nationality nation = new Nationality();
				nation.setId(Integer.parseInt(studentpersonaldata.getNationality()));
				personaldataBO.setNationality(nation);
			}
			log.info("exit convertPersonalDataTOtoBO" );
			return personaldataBO;
		}
		/**
		 * Address TO to BO conversion
		 * @param permAddTo
		 * @return
		 */
		public Address convertPermanentAddressTOToBO(AddressTO permAddTo) {
			log.info("enter convertPermanentAddressTOToBO" );
			if (permAddTo!=null) {
				Address permAddress = new Address();
				permAddress.setAddrLine1(permAddTo.getAddrLine1());
				permAddress.setAddrLine2(permAddTo.getAddrLine2());
				permAddress.setAddrLine3(permAddTo.getAddrLine3());
				
				permAddress.setCity(permAddTo.getCity());
				if (permAddTo.getStateId()!=null && !StringUtils.isEmpty(permAddTo.getStateId()) && StringUtils.isNumeric(permAddTo.getStateId())) {
					State stt = new State();
					stt.setId(Integer.parseInt(permAddTo.getStateId()));
					permAddress.setState(stt);
				}else{
					permAddress.setStateOthers(permAddTo.getOtherState());
				}
				if (permAddTo.getCountryId()!=null && !StringUtils.isEmpty(permAddTo.getCountryId()) && StringUtils.isNumeric(permAddTo.getCountryId())) {
				Country cnt= new Country();
				cnt.setId(Integer.parseInt(permAddTo.getCountryId()));
				permAddress.setCountry(cnt);
				}else{
					permAddress.setCountry(null);
				}
				permAddress.setPinCode(permAddTo.getPinCode());
				log.info("exit convertPermanentAddressTOToBO" );
				return permAddress;
			}
			else{
				log.info("exit convertPermanentAddressTOToBO" );
				return null;
			}
		}
		
		
		/**
		 * ResidentBO to TO Conversion
		 * @param residentbos
		 * @return
		 */
		public List<ResidentCategoryTO> convertResidentBOToTO(
				List<ResidentCategory> residentbos) {
			log.info("enter convertResidentBOToTO" );
			List<ResidentCategoryTO> tolist= new ArrayList<ResidentCategoryTO>();
			if(residentbos!=null){
				Iterator<ResidentCategory> itr= residentbos.iterator();
				while (itr.hasNext()) {
					ResidentCategory resident = (ResidentCategory) itr.next();
					ResidentCategoryTO resto= new ResidentCategoryTO();
					resto.setId(resident.getId());
					resto.setName(resident.getName());
					tolist.add(resto);
					
				}
			}
			log.info("exit convertResidentBOToTO" );
			return tolist;

		}
		/**
		 * preference to to BO
		 * @param firstpref
		 * @param secpref
		 * @param thirdpref
		 * @param session
		 * @return
		 */
		/*public List<CandidatePreference> convertPreferenceTOToBO(PreferenceTO firstpref,
				PreferenceTO secpref, PreferenceTO thirdpref,HttpSession session) {
			log.info("enter convertPreferenceTOToBO" );
			
			List<CandidatePreference> preferences= new ArrayList<CandidatePreference>();
			AdmAppln applicationdata=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);
			
			CandidatePreference firstPref;
			if (firstpref!=null && firstpref.getCourseId()!=null && !StringUtils.isEmpty(firstpref.getCourseId()) ) {
				firstPref = new CandidatePreference();
				Course firstCourse = new Course();
				firstCourse.setId(Integer.parseInt(firstpref.getCourseId()));
				firstPref.setCourse(firstCourse);
				firstPref.setAdmAppln(applicationdata);
				firstPref.setPrefNo(1);
				preferences.add(firstPref);
			}
			CandidatePreference secPref;
			if (secpref!=null && secpref.getCourseId()!=null && !StringUtils.isEmpty(secpref.getCourseId()) ) {
				secPref = new CandidatePreference();
				Course secCourse = new Course();
				secCourse.setId(Integer.parseInt(secpref.getCourseId()));
				secPref.setCourse(secCourse);
				secPref.setAdmAppln(applicationdata);
				secPref.setPrefNo(2);
				preferences.add(secPref);
			}
			CandidatePreference thirdPref;
			
			if (thirdpref!=null && thirdpref.getCourseId()!=null && !StringUtils.isEmpty(thirdpref.getCourseId()) ) {
				thirdPref = new CandidatePreference();
				Course thirdCourse = new Course();
				thirdCourse.setId(Integer.parseInt(thirdpref.getCourseId()));
				thirdPref.setCourse(thirdCourse);
				thirdPref.setAdmAppln(applicationdata);
				thirdPref.setPrefNo(3);
				preferences.add(thirdPref);
			}
			log.info("exit convertPreferenceTOToBO" );
			return preferences;
		}*/
		/**
		 * Qualifcation TO creation
		 * @param exambos
		 * @return
		 */
		public List<EdnQualificationTO> prepareQualificationsFromExamBos(List<DocChecklist> exambos) throws Exception {
			log.info("enter prepareQualificationsFromExamBos" );
			List<EdnQualificationTO> qualifications= new ArrayList<EdnQualificationTO>();
			List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
			if(exambos!=null){
				int cnt=0;
				for (DocChecklist examType : exambos) {
							EdnQualificationTO ednto = new EdnQualificationTO();
							ednto.setOrignalCheckList(examType);
							ednto.setDocCheckListId(examType.getId());
							ednto.setDocName(examType.getDocType().getName());
							if(examType.getDocType().getDisplayOrder() != null){
								ednto.setDisplayOrder(examType.getDocType().getDisplayOrder());
							}
							int docTypeId = examType.getDocType().getId();
							ednto.setDocTypeId(docTypeId);
							ednto.setCountId(cnt);
 							if(examType!=null && examType.getIsActive() && examType.getIsMarksCard() && !examType.getIsConsolidatedMarks())
								ednto.setConsolidated(false);
							else
								ednto.setConsolidated(true);
							if(examType!=null && examType.getIsActive() && examType.getIsMarksCard() &&  !examType.getIsConsolidatedMarks() && examType.getIsSemesterWise())
								ednto.setSemesterWise(true);
							else
								ednto.setSemesterWise(false);
							if(examType!=null && examType.getIsActive() && examType.getIsPreviousExam()!=null && examType.getIsPreviousExam()){ 
								ednto.setLastExam(true);
								/*if (examType.getCourse().getBlockMarkEntryQualifyingExam()!=null && examType.getCourse().getBlockMarkEntryQualifyingExam()) {
									ednto.setBlockedMarks(true);
									ednto.setNoOfAttempts(1);
								}*/
							}else{
								ednto.setLastExam(false);
							}
							if(examType!=null && examType.getIsActive() && examType.getIsExamRequired()!=null && examType.getIsExamRequired()){ 
								ednto.setExamConfigured(true);
							}else{
								ednto.setExamConfigured(false);
							}
							//doc type exam setup
							if(examType.getDocType()!=null 
									&& examType.getDocType().getDocTypeExamses()!=null ){
								List<DocTypeExamsTO> examTos= new ArrayList<DocTypeExamsTO>();
								for (DocTypeExams docExams : examType.getDocType().getDocTypeExamses()) {
									if(docExams.getIsActive()!=null && docExams.getIsActive()){
										DocTypeExamsTO examto= new DocTypeExamsTO();
										examto.setId(docExams.getId());
										examto.setName(docExams.getName());
										examTos.add(examto);
									}
								}
								ednto.setExamTos(examTos);
								if(!examTos.isEmpty())
								{
									ednto.setExamRequired(true);
								}
								
							}
							
							Map<Integer,String> subjectMap =null;
							CandidateMarkTO markTo=new CandidateMarkTO();
							if(!ednto.isConsolidated() &&!ednto.isSemesterWise()){
								if(examType.getCourse()!=null)
								 subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(String.valueOf(examType.getCourse().getId()));
								if(subjectMap!=null){
									setDetailedSubjectsFormMap(subjectMap,markTo);
								}
								
								
								
								//class 12
								int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
								
								//raghu write newly
								if(examType.getDocType().getId()==doctypeId){
									
									String language="Language";
									Map<Integer, String> admsubjectLangMap;
									
										admsubjectLangMap = AdmissionFormHandler.getInstance().get12thclassLangSubjects(examType.getDocType().getName(),language);
										//find id from english in Map
								        String strValue="English";
								        String intKey = null;
								        for(Map.Entry entry: admsubjectLangMap.entrySet()){
								            if(strValue.equals(entry.getValue())){
								            	intKey =entry.getKey().toString();
								            	markTo.setSubjectid1(intKey);
								                break; //breaking because its one to one map
								            }
								        }
								          	
								}
							
								//degree
								int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
								
								
								
								if(examType.getDocType().getId()==doctypeId1){
									

									//String Core="Core";
									//String Compl="Complementary";
									String Common="Common";
									//String Open="Open";
									//String Sub="Sub";
									
										//Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Core);
										//Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Compl);
										Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(examType.getDocType().getName(),Common);
										//Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Open);
										//Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(docNmae,Common);
										
										
										//find id from english in Map
										String strValue="English";
										String intKey = null;
										for(Map.Entry entry: admCommonMap.entrySet()){
										 if(strValue.equals(entry.getValue())){
											intKey =entry.getKey().toString();
											markTo.setSubjectid6(intKey);
											markTo.setSubjectid16(intKey);
											break; //breaking because its one to one map
										 	}
										}
												
								}
								
								
							}
							ednto.setDetailmark(markTo);
							ednto.setLanguage(examType.getIsIncludeLanguage());
							List<UniversityTO> universityTempList = new ArrayList<UniversityTO>();
							if(universityList!=null && !universityList.isEmpty()){
								for (UniversityTO universityTO : universityList) {
									
									if(universityTO.getDocTypeId() == docTypeId){
										universityTempList.add(universityTO);
									}
								}
								
							}
							ednto.setUniversityList(universityTempList);
							if(universityTempList!=null && universityTempList.isEmpty()){
								ednto.setUniversityId("Other");
								ednto.setInstitutionId("Other");
							}
						qualifications.add(ednto);
						cnt++;
				}
			}
			Collections.sort(qualifications);
			log.info("exit prepareQualificationsFromExamBos" );
			return qualifications;
		}
		/**
		 * EDN QUALIFICATION BO CREATION
		 * @param admForm
		 * @return
		 * @throws Exception 
		 */
		public List<EdnQualification> getEducationDetailsBO(
				OnlineApplicationForm admForm,boolean isPresidance) throws Exception {
			log.info("enter getEducationDetailsBO" );
			List<EdnQualification> educationDetails= new ArrayList<EdnQualification>();
			List<EdnQualificationTO> qualifications=admForm.getQualifications();
			if(qualifications!=null){
				Iterator<EdnQualificationTO> itr= qualifications.iterator();
				while (itr.hasNext()) {
					EdnQualificationTO qualificationTO = (EdnQualificationTO) itr.next();
					EdnQualification ednbo=new EdnQualification();
					//raghu write new
					ednbo.setId(qualificationTO.getId());
					ednbo.setCreatedBy(admForm.getUserId());
					ednbo.setCreatedDate(new Date());
					if (qualificationTO.getInstitutionId()!=null && !StringUtils.isEmpty(qualificationTO.getInstitutionId())&& StringUtils.isNumeric(qualificationTO.getInstitutionId())) {
						College col= new College();
						col.setId(Integer.parseInt(qualificationTO.getInstitutionId()));
						ednbo.setCollege(col);
					}else{
						ednbo.setInstitutionNameOthers(qualificationTO.getOtherInstitute());
					}
					//we are storing here only percentage raghu
					/*if(qualificationTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && StringUtils.isNumeric(qualificationTO.getMarksObtained()))
						ednbo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
					if(qualificationTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualificationTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualificationTO.getTotalMarks()))
					ednbo.setTotalMarks(new BigDecimal(qualificationTO.getTotalMarks()));
					
					if(ednbo.getMarksObtained()!=null && ednbo.getTotalMarks()!=null){
						float percentageMarks = ednbo.getMarksObtained().floatValue()/ednbo.getTotalMarks().floatValue()*100 ;
						ednbo.setPercentage(new BigDecimal(percentageMarks));
					}else if(isPresidance){
						if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty())
						ednbo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
					}*/
					
					if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty())
						ednbo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
					
					
					
					if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& StringUtils.isNumeric(qualificationTO.getStateId())){
						State ednst= new State();
						ednst.setId(Integer.parseInt(qualificationTO.getStateId()));
						ednbo.setState(ednst);
					}else if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& qualificationTO.getStateId().equalsIgnoreCase(CMSConstants.OUTSIDE_INDIA)){
						ednbo.setState(null);
						ednbo.setIsOutsideIndia(true);
					}
				
					ednbo.setNoOfAttempts(qualificationTO.getNoOfAttempts());
					
					
					
					if (qualificationTO.getUniversityId()!=null && !StringUtils.isEmpty(qualificationTO.getUniversityId())&& StringUtils.isNumeric(qualificationTO.getUniversityId())) {
						University un=new University();
						un.setId(Integer.parseInt(qualificationTO.getUniversityId()));
						ednbo.setUniversity(un);
					}else{
						ednbo.setUniversityOthers(qualificationTO.getUniversityOthers());
					}
					ednbo.setYearPassing(qualificationTO.getYearPassing());
					ednbo.setMonthPassing(qualificationTO.getMonthPassing());
					ednbo.setPreviousRegNo(qualificationTO.getPreviousRegNo());
					ednbo.setDocChecklist(qualificationTO.getOrignalCheckList());
					//doc type exam set
					
					if(qualificationTO.getSelectedExamId()!=null && !StringUtils.isEmpty(qualificationTO.getSelectedExamId()) && StringUtils.isNumeric(qualificationTO.getSelectedExamId())){
						DocTypeExams examBo=new DocTypeExams();
						examBo.setId(Integer.parseInt(qualificationTO.getSelectedExamId()));
						ednbo.setDocTypeExams(examBo);
					}
					Set<CandidateMarks> detailMarks=new HashSet<CandidateMarks>();
					
					if(!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise()){
						CandidateMarks detailMark= new CandidateMarks();
						CandidateMarkTO detailMarkto=qualificationTO.getDetailmark();
						if (detailMarkto!=null) {
							detailMark.setCreatedBy(admForm.getUserId());
							detailMark.setCreatedDate(new Date());
							//raghu write new
							detailMark.setId(detailMarkto.getId());
							
							double totalmark=0.0;
							double totalobtained=0.0;
							double percentage=0.0;
							
							if((detailMarkto.getDetailedSubjects1()!= null) && (detailMarkto.getDetailedSubjects1().getId() != -1) && (detailMarkto.getDetailedSubjects1().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects1().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects1().getSubjectName());
								detailMark.setDetailedSubjects1(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects2()!= null) &&(detailMarkto.getDetailedSubjects2().getId() != -1) && (detailMarkto.getDetailedSubjects2().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects2().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects2().getSubjectName());
								detailMark.setDetailedSubjects2(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects3()!= null) && (detailMarkto.getDetailedSubjects3().getId() != -1) && (detailMarkto.getDetailedSubjects3().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects3().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects3().getSubjectName());
								detailMark.setDetailedSubjects3(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects4()!= null) &&(detailMarkto.getDetailedSubjects4().getId() != -1) && (detailMarkto.getDetailedSubjects4().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects4().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects4().getSubjectName());
								detailMark.setDetailedSubjects4(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects5()!= null) &&(detailMarkto.getDetailedSubjects5().getId() != -1) && (detailMarkto.getDetailedSubjects5().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects5().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects5().getSubjectName());
								detailMark.setDetailedSubjects5(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects6()!= null) &&(detailMarkto.getDetailedSubjects6().getId() != -1) && (detailMarkto.getDetailedSubjects6().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects6().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects6().getSubjectName());
								detailMark.setDetailedSubjects6(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects7()!= null) &&(detailMarkto.getDetailedSubjects7().getId() != -1) && (detailMarkto.getDetailedSubjects7().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects7().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects7().getSubjectName());
								detailMark.setDetailedSubjects7(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects8()!= null) &&(detailMarkto.getDetailedSubjects8().getId() != -1) && (detailMarkto.getDetailedSubjects8().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects8().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects8().getSubjectName());
								detailMark.setDetailedSubjects8(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects9()!= null) &&(detailMarkto.getDetailedSubjects9().getId() != -1) && (detailMarkto.getDetailedSubjects9().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects9().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects9().getSubjectName());
								detailMark.setDetailedSubjects9(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects10()!= null) &&(detailMarkto.getDetailedSubjects10().getId() != -1) && (detailMarkto.getDetailedSubjects10().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects10().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects10().getSubjectName());
								detailMark.setDetailedSubjects10(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects11()!= null) &&(detailMarkto.getDetailedSubjects11().getId() != -1) && (detailMarkto.getDetailedSubjects11().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects11().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects11().getSubjectName());
								detailMark.setDetailedSubjects11(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects12()!= null) &&(detailMarkto.getDetailedSubjects12().getId() != -1) && (detailMarkto.getDetailedSubjects12().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects12().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects12().getSubjectName());
								detailMark.setDetailedSubjects12(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects13()!= null) &&(detailMarkto.getDetailedSubjects13().getId() != -1) && (detailMarkto.getDetailedSubjects13().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects13().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects13().getSubjectName());
								detailMark.setDetailedSubjects13(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects14()!= null) &&(detailMarkto.getDetailedSubjects14().getId() != -1) && (detailMarkto.getDetailedSubjects14().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects14().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects14().getSubjectName());
								detailMark.setDetailedSubjects14(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects15()!= null) &&(detailMarkto.getDetailedSubjects15().getId() != -1) && (detailMarkto.getDetailedSubjects15().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects15().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects15().getSubjectName());
								detailMark.setDetailedSubjects15(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects16()!= null) &&(detailMarkto.getDetailedSubjects16().getId() != -1) && (detailMarkto.getDetailedSubjects16().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects16().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects16().getSubjectName());
								detailMark.setDetailedSubjects16(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects17()!= null) &&(detailMarkto.getDetailedSubjects17().getId() != -1) && (detailMarkto.getDetailedSubjects17().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects17().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects17().getSubjectName());
								detailMark.setDetailedSubjects17(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects18()!= null) &&(detailMarkto.getDetailedSubjects18().getId() != -1) && (detailMarkto.getDetailedSubjects18().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects18().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects18().getSubjectName());
								detailMark.setDetailedSubjects18(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects19()!= null) &&(detailMarkto.getDetailedSubjects19().getId() != -1) && (detailMarkto.getDetailedSubjects19().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects19().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects19().getSubjectName());
								detailMark.setDetailedSubjects19(detailedSubjects);
							}
							if((detailMarkto.getDetailedSubjects20()!= null) &&(detailMarkto.getDetailedSubjects20().getId() != -1) && (detailMarkto.getDetailedSubjects20().getId() != 0)) {
								DetailedSubjects detailedSubjects = new DetailedSubjects();
								detailedSubjects.setId(detailMarkto.getDetailedSubjects20().getId());
								detailedSubjects.setSubjectName(detailMarkto.getDetailedSubjects20().getSubjectName());
								detailMark.setDetailedSubjects20(detailedSubjects);
							}
							detailMark.setSubject1(detailMarkto.getSubject1());
							detailMark.setSubject2(detailMarkto.getSubject2());
							detailMark.setSubject3(detailMarkto.getSubject3());
							detailMark.setSubject4(detailMarkto.getSubject4());
							detailMark.setSubject5(detailMarkto.getSubject5());
							detailMark.setSubject6(detailMarkto.getSubject6());
							detailMark.setSubject7(detailMarkto.getSubject7());
							detailMark.setSubject8(detailMarkto.getSubject8());
							detailMark.setSubject9(detailMarkto.getSubject9());
							detailMark.setSubject10(detailMarkto.getSubject10());
							detailMark.setSubject11(detailMarkto.getSubject11());
							detailMark.setSubject12(detailMarkto.getSubject12());
							detailMark.setSubject13(detailMarkto.getSubject13());
							detailMark.setSubject14(detailMarkto.getSubject14());
							detailMark.setSubject15(detailMarkto.getSubject15());
							detailMark.setSubject16(detailMarkto.getSubject16());
							detailMark.setSubject17(detailMarkto.getSubject17());
							detailMark.setSubject18(detailMarkto.getSubject18());
							detailMark.setSubject19(detailMarkto.getSubject19());
							detailMark.setSubject20(detailMarkto.getSubject20());

							
							//new
							detailMark.setSubjectid1(detailMarkto.getSubjectid1());
							detailMark.setSubjectid2(detailMarkto.getSubjectid2());
							detailMark.setSubjectid3(detailMarkto.getSubjectid3());
							detailMark.setSubjectid4(detailMarkto.getSubjectid4());
							detailMark.setSubjectid5(detailMarkto.getSubjectid5());
							detailMark.setSubjectid6(detailMarkto.getSubjectid6());
							detailMark.setSubjectid7(detailMarkto.getSubjectid7());
							detailMark.setSubjectid8(detailMarkto.getSubjectid8());
							detailMark.setSubjectid9(detailMarkto.getSubjectid9());
							detailMark.setSubjectid10(detailMarkto.getSubjectid10());
							detailMark.setSubjectid11(detailMarkto.getSubjectid11());
							detailMark.setSubjectid12(detailMarkto.getSubjectid12());
							detailMark.setSubjectid13(detailMarkto.getSubjectid13());
							detailMark.setSubjectid14(detailMarkto.getSubjectid14());
							detailMark.setSubjectid15(detailMarkto.getSubjectid15());
							detailMark.setSubjectid16(detailMarkto.getSubjectid16());
							detailMark.setSubjectid17(detailMarkto.getSubjectid17());
							detailMark.setSubjectid18(detailMarkto.getSubjectid18());
							detailMark.setSubjectid19(detailMarkto.getSubjectid19());
							detailMark.setSubjectid20(detailMarkto.getSubjectid20());

							//class 12
							int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
							
							//raghu storing database
							if(qualificationTO.getDocTypeId()==doctypeId){
								String language="Language";
								Map<Integer,String> admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(qualificationTO.getDocName(),language);
								admForm.setAdmSubjectMap(admsubjectMap);
								Map<Integer,String> admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualificationTO.getDocName(),language);
								admForm.setAdmSubjectLangMap(admsubjectLangMap);
								
								setDetailSubjectMarkBOClass12(ednbo,qualificationTO, detailMark,detailMarkto, admForm,qualificationTO.getDocTypeId());
							
								
								
							
							}
							
							//degree
							int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
							
							if(qualificationTO.getDocTypeId()==doctypeId1 ){
								
								String Core="Core";
								String Compl="Complimentary";
								String Common="Common";
								String Open="Open";
								//String Sub="Sub";
								Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Core);
								admForm.setAdmCoreMap(admCoreMap);
								Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Compl);
								admForm.setAdmComplMap(admComplMap);
								Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Common);
								admForm.setAdmCommonMap(admCommonMap);
								Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Open);
								admForm.setAdmMainMap(admopenMap);
								Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(qualificationTO.getDocName(),Common);
								admForm.setAdmSubMap(admSubMap);
								
								setDetailSubjectMarkBODegree(ednbo,qualificationTO, detailMark,detailMarkto, admForm,qualificationTO.getDocTypeId(),admForm.getPatternofStudy());
							}
							
							//for ranklist
	                        //ug programtype id=1 
	                        /*if((qualificationTO.getDocName().equalsIgnoreCase("Class 12")) ){
	                       
	                        }
	                        
	                        
	                        
	                       
	                        if((qualificationTO.getDocName().equalsIgnoreCase("DEG")) ){
	                             
	                        
	                        }  
	                        
	                        */

							
							
							if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks()))
							detailMark.setSubject1TotalMarks(new BigDecimal(
									detailMarkto.getSubject1TotalMarks()));
							if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks()))
							detailMark.setSubject2TotalMarks(new BigDecimal(
									detailMarkto.getSubject2TotalMarks()));
							if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks()))
							detailMark.setSubject3TotalMarks(new BigDecimal(
									detailMarkto.getSubject3TotalMarks()));
							if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks()))
							detailMark.setSubject4TotalMarks(new BigDecimal(
									detailMarkto.getSubject4TotalMarks()));
							if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks()))
							detailMark.setSubject5TotalMarks(new BigDecimal(
									detailMarkto.getSubject5TotalMarks()));
							if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks()))
							detailMark.setSubject6TotalMarks(new BigDecimal(
									detailMarkto.getSubject6TotalMarks()));
							if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks()))
							detailMark.setSubject7TotalMarks(new BigDecimal(
									detailMarkto.getSubject7TotalMarks()));
							if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks()))
							detailMark.setSubject8TotalMarks(new BigDecimal(
									detailMarkto.getSubject8TotalMarks()));
							if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks()))
							detailMark.setSubject9TotalMarks(new BigDecimal(
									detailMarkto.getSubject9TotalMarks()));
							if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks()))
							detailMark.setSubject10TotalMarks(new BigDecimal(
									detailMarkto.getSubject10TotalMarks()));
							if(detailMarkto.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11TotalMarks()))
							detailMark.setSubject11TotalMarks(new BigDecimal(
									detailMarkto.getSubject11TotalMarks()));
							if(detailMarkto.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12TotalMarks()))
							detailMark.setSubject12TotalMarks(new BigDecimal(
									detailMarkto.getSubject12TotalMarks()));
							if(detailMarkto.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13TotalMarks()))
							detailMark.setSubject13TotalMarks(new BigDecimal(
									detailMarkto.getSubject13TotalMarks()));
							if(detailMarkto.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14TotalMarks()))
							detailMark.setSubject14TotalMarks(new BigDecimal(
									detailMarkto.getSubject14TotalMarks()));
							if(detailMarkto.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15TotalMarks()))
							detailMark.setSubject15TotalMarks(new BigDecimal(
									detailMarkto.getSubject15TotalMarks()));
							if(detailMarkto.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16TotalMarks()))
							detailMark.setSubject16TotalMarks(new BigDecimal(
									detailMarkto.getSubject16TotalMarks()));
							if(detailMarkto.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17TotalMarks()))
							detailMark.setSubject17TotalMarks(new BigDecimal(
									detailMarkto.getSubject17TotalMarks()));
							if(detailMarkto.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18TotalMarks()))
							detailMark.setSubject18TotalMarks(new BigDecimal(
									detailMarkto.getSubject18TotalMarks()));
							if(detailMarkto.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19TotalMarks()))
							detailMark.setSubject19TotalMarks(new BigDecimal(
									detailMarkto.getSubject19TotalMarks()));
							if(detailMarkto.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20TotalMarks()))
							detailMark.setSubject20TotalMarks(new BigDecimal(
									detailMarkto.getSubject20TotalMarks()));
							
							if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks()))
							detailMark.setSubject1ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject1ObtainedMarks()));
							if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks()))
							detailMark.setSubject2ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject2ObtainedMarks()));
							if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks()))
							detailMark.setSubject3ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject3ObtainedMarks()));
							if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks()))
							detailMark.setSubject4ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject4ObtainedMarks()));
							if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks()))
							detailMark.setSubject5ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject5ObtainedMarks()));
							if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks()))
							detailMark.setSubject6ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject6ObtainedMarks()));
							if(detailMarkto.getSubject7ObtainedMarks()!=null  && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks()))
							detailMark.setSubject7ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject7ObtainedMarks()));
							if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks()))
							detailMark.setSubject8ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject8ObtainedMarks()));
							if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks()))
							detailMark.setSubject9ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject9ObtainedMarks()));
							if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks()))
							detailMark.setSubject10ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject10ObtainedMarks()));
							if(detailMarkto.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11ObtainedMarks()))
							detailMark.setSubject11ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject11ObtainedMarks()));
							if(detailMarkto.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12ObtainedMarks()))
							detailMark.setSubject12ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject12ObtainedMarks()));
							if(detailMarkto.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13ObtainedMarks()))
							detailMark.setSubject13ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject13ObtainedMarks()));
							if(detailMarkto.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14ObtainedMarks()))
							detailMark.setSubject14ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject14ObtainedMarks()));
							if(detailMarkto.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15ObtainedMarks()))
							detailMark.setSubject15ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject15ObtainedMarks()));
							if(detailMarkto.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16ObtainedMarks()))
							detailMark.setSubject16ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject16ObtainedMarks()));
							if(detailMarkto.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17ObtainedMarks()))
							detailMark.setSubject17ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject17ObtainedMarks()));
							if(detailMarkto.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18ObtainedMarks()))
							detailMark.setSubject18ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject18ObtainedMarks()));
							if(detailMarkto.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19ObtainedMarks()))
							detailMark.setSubject19ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject19ObtainedMarks()));
							if(detailMarkto.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20ObtainedMarks()))
							detailMark.setSubject20ObtainedMarks(new BigDecimal(
									detailMarkto.getSubject20ObtainedMarks()));
							if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && StringUtils.isNumeric(detailMarkto.getTotalMarks())){
							detailMark.setTotalMarks(new BigDecimal(detailMarkto
									.getTotalMarks()));
							totalmark=detailMark.getTotalMarks().doubleValue();
							}
							if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getTotalObtainedMarks())){
							detailMark.setTotalObtainedMarks(new BigDecimal(
									detailMarkto.getTotalObtainedMarks()));
							totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
							}
							if(totalobtained!=0.0 && totalmark!=0.0)
							percentage=(totalobtained/totalmark)*100;
							ednbo.setTotalMarks(new BigDecimal(totalmark));
							ednbo.setMarksObtained(new BigDecimal(totalobtained));
							ednbo.setPercentage(new BigDecimal(percentage));
							detailMarks.add(detailMark);
						}
						ednbo.setCandidateMarkses(detailMarks);
					}	
						if(!qualificationTO.isConsolidated() && qualificationTO.isSemesterWise()){
							Set<ApplicantMarksDetails> applicantMarks= new HashSet<ApplicantMarksDetails>();
							CandidateMarkTO detailMarkto=qualificationTO.getDetailmark();
							//if only without language
							if (detailMarkto!=null  && !detailMarkto.isSemesterMark()) {
								
								int totalmark=0;
								int totalObtain=0;
								int count=0;
								double overallMark=0.0;
								double overallObtain=0.0;
								double overallPerc=0.0;
								// calculate over all percentage and set corresponding attribute values
								if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject1TotalMarks());
									if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject1ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(1);
									applicantMark.setSemesterName(detailMarkto.getSubject1());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									applicantMarks.add(applicantMark);
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
								}
								if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject2TotalMarks());
									if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject2ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(2);
									applicantMark.setSemesterName(detailMarkto.getSubject2());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject3TotalMarks());
									if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject3ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(3);
									applicantMark.setSemesterName(detailMarkto.getSubject3());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject4TotalMarks());
									if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject4ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(4);
									applicantMark.setSemesterName(detailMarkto.getSubject4());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject5TotalMarks());
									if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject5ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(5);
									applicantMark.setSemesterName(detailMarkto.getSubject5());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject6TotalMarks());
									if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject6ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(6);
									applicantMark.setSemesterName(detailMarkto.getSubject6());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject7TotalMarks());
									if(detailMarkto.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject7ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(7);
									applicantMark.setSemesterName(detailMarkto.getSubject7());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject8TotalMarks());
									if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject8ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(8);
									applicantMark.setSemesterName(detailMarkto.getSubject8());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject9TotalMarks());
									if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject9ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(9);
									applicantMark.setSemesterName(detailMarkto.getSubject9());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject10TotalMarks());
									if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks()))
										totalObtain=Integer.parseInt(detailMarkto.getSubject10ObtainedMarks());
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(10);
									applicantMark.setSemesterName(detailMarkto.getSubject10());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								ednbo.setTotalMarks(new BigDecimal(overallMark));
								ednbo.setMarksObtained(new BigDecimal(overallObtain));
								double calPerc=0.0;
								if(overallMark!=0.0 && overallObtain!=0.0)
								calPerc=overallPerc/count;
								ednbo.setPercentage(new BigDecimal(calPerc));
								ednbo.setApplicantMarksDetailses(applicantMarks);
							}//else with language check
							else {
								
								int totalmark=0;
								int totalObtain=0;
								int totalLanguagewisemark = 0;
								int totalLanguagewiseObtained = 0;
								int count=0;
								double overallMark=0.0;
								double overallObtain=0.0;
								double overallPerc=0.0;
								double overallanguagewiseMark=0.0;
								double overallangauagelObtain=0.0;
								double overallanguagePerc=0.0;		
								
								if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks()) && detailMarkto.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject1TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject1_languagewise_TotalMarks());
									if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks()) && detailMarkto.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject1ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject1_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(1);
									applicantMark.setSemesterName(detailMarkto.getSubject1());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtained(totalObtain);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentage=(totalObtain*100)/totalmark;
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									applicantMarks.add(applicantMark);
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
								}
								if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks())&& detailMarkto.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject2TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject2_languagewise_TotalMarks());
									if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks())&& detailMarkto.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject2ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject2_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(2);
									applicantMark.setSemesterName(detailMarkto.getSubject2());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks())&& detailMarkto.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject3TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject3_languagewise_TotalMarks());
									if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks())&& detailMarkto.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject3ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject3_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(3);
									applicantMark.setSemesterName(detailMarkto.getSubject3());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks())&& detailMarkto.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject4TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject4_languagewise_TotalMarks());
									if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks())&& detailMarkto.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject4ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject4_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(4);
									applicantMark.setSemesterName(detailMarkto.getSubject4());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks())&& detailMarkto.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject5TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject5_languagewise_TotalMarks());
									if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks())&& detailMarkto.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject5ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject5_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(5);
									applicantMark.setSemesterName(detailMarkto.getSubject5());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks())&& detailMarkto.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject6TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject6_languagewise_TotalMarks());
									if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks())&& detailMarkto.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject6ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject6_languagewise_ObtainedMarks());
									}
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(6);
									applicantMark.setSemesterName(detailMarkto.getSubject6());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks())&& detailMarkto.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject7TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject7_languagewise_TotalMarks());
									if(detailMarkto.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks())&& detailMarkto.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject7ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject7_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(7);
									applicantMark.setSemesterName(detailMarkto.getSubject7());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
								}
								if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks())&& detailMarkto.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject8TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject8_languagewise_TotalMarks());
									if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks()) && detailMarkto.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject8ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject8_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(8);
									applicantMark.setSemesterName(detailMarkto.getSubject8());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks())&& detailMarkto.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject9TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject9_languagewise_TotalMarks());
									if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks()) && detailMarkto.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject9ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject9_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(9);
									applicantMark.setSemesterName(detailMarkto.getSubject9());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks())&& detailMarkto.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10_languagewise_TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10_languagewise_TotalMarks())){
									totalmark=Integer.parseInt(detailMarkto.getSubject10TotalMarks());
									totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject10_languagewise_TotalMarks());
									if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks()) && detailMarkto.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10_languagewise_ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10_languagewise_ObtainedMarks())){
										totalObtain=Integer.parseInt(detailMarkto.getSubject10ObtainedMarks());
										totalLanguagewiseObtained = Integer.parseInt(detailMarkto.getSubject10_languagewise_ObtainedMarks());
									}	
									ApplicantMarksDetails applicantMark=new ApplicantMarksDetails();
									applicantMark.setSemesterNo(10);
									applicantMark.setSemesterName(detailMarkto.getSubject10());
									applicantMark.setMaxMarks(totalmark);
									applicantMark.setMarksObtained(totalObtain);
									int percentage=(totalObtain*100)/totalmark;
									applicantMark.setPercentage(new BigDecimal(percentage));
									applicantMark.setMaxMarksLanguagewise(totalLanguagewisemark);
									applicantMark.setMarksObtainedLanguagewise(totalLanguagewiseObtained);
									int percentageLanguagewise = (totalLanguagewiseObtained*100)/totalLanguagewisemark;
									applicantMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									applicantMark.setIsLastExam(detailMarkto.isLastExam());
									overallMark=overallMark+totalmark;
									overallObtain=overallObtain+totalObtain;
									overallPerc=overallPerc+percentage;
									overallanguagewiseMark=overallanguagewiseMark+totalLanguagewisemark;
									overallangauagelObtain=overallangauagelObtain+totalLanguagewiseObtained;
									overallanguagePerc=overallanguagePerc+percentageLanguagewise;
									count++;
									applicantMarks.add(applicantMark);
									
								}
								ednbo.setTotalMarks(new BigDecimal(overallanguagewiseMark));
								ednbo.setMarksObtained(new BigDecimal(overallangauagelObtain));
								double calPerc=0.0;
								if(overallanguagewiseMark!=0.0 && overallangauagelObtain!=0.0)
								calPerc=overallanguagePerc/count;
								ednbo.setPercentage(new BigDecimal(calPerc));
								ednbo.setApplicantMarksDetailses(applicantMarks);
							}
						}
						educationDetails.add(ednbo);
				}
				
			}
			log.info("exit getEducationDetailsBO" );
			return educationDetails;
		}
		/**
		 * DOCUPLOAD TO List Creation
		 * @param listofdocs
		 * @param appliedyear 
		 * @return
		 */
		public List<ApplnDocTO> createDocUploadListForCourse(
				List<DocChecklist> listofdocs, int appliedyear) {
			log.info("enter createDocUploadListForCourse" );
			List<ApplnDocTO> doclist= new ArrayList<ApplnDocTO>();
			
			 if(listofdocs!=null && ! listofdocs.isEmpty()){
				 Iterator<DocChecklist> boitr=listofdocs.iterator();
					 while (boitr.hasNext()) {
						DocChecklist docChecklist = (DocChecklist) boitr.next();
						
							  if( (docChecklist.getNeedToProduce()==true && docChecklist.getIsActive() && docChecklist.getYear()==appliedyear ))
							  {
								  ApplnDocTO uploadTo= new ApplnDocTO();
								  uploadTo.setName(docChecklist.getDocType().getPrintName());
								  uploadTo.setPrintName(docChecklist.getDocType().getPrintName());
								  uploadTo.setDocName(docChecklist.getDocType().getName());
								  uploadTo.setDocTypeId(docChecklist.getDocType().getId());
								  if(docChecklist.getNeedToProduceSemwiseMc()!=null && docChecklist.getNeedToProduceSemwiseMc()){
									  uploadTo.setNeedToProduceSemWiseMC(docChecklist.getNeedToProduceSemwiseMc());
									  List<ApplnDocDetailsTO> list=new ArrayList<ApplnDocDetailsTO>();
									  for(int i=1;i<=12;i++){
										  ApplnDocDetailsTO to=new ApplnDocDetailsTO();
										  to.setTempHardCopySubmitted(false);
										  to.setSemNo(String.valueOf(i));
										  to.setChecked("no");
										  list.add(to);
									  }
									  uploadTo.setDocDetailsList(list);
								  }
								  else{
									  uploadTo.setNeedToProduceSemWiseMC(false);
								  }
								  doclist.add(uploadTo);
							  }
					}
					 
				}
				
			 //Photo 
			 ApplnDocTO photoTo= new ApplnDocTO();
			 photoTo.setPhoto(true);
			 photoTo.setName(OnlineApplicationHelper.PHOTO);
			 photoTo.setDocName(OnlineApplicationHelper.PHOTO);
			 photoTo.setPrintName(OnlineApplicationHelper.PHOTO);
			 doclist.add(photoTo);
			 
			//Management Quota form upload (Now uploaded as image) 
			 ApplnDocTO mgQuotaFormImg= new ApplnDocTO();
			 mgQuotaFormImg.setPhoto(false);
			 mgQuotaFormImg.setMngQuotaForm(true);
			 mgQuotaFormImg.setName(OnlineApplicationHelper.MANAGEMENT_QUOTA_FORM);
			 mgQuotaFormImg.setDocName(OnlineApplicationHelper.MANAGEMENT_QUOTA_FORM);
			 mgQuotaFormImg.setPrintName(OnlineApplicationHelper.MANAGEMENT_QUOTA_FORM);
			 doclist.add(mgQuotaFormImg);
				
			 log.info("exit createDocUploadListForCourse" );
			return doclist;
		}
		/**
		 * currency BO conversion
		 * @param currancybos
		 * @return
		 */
		public List<CurrencyTO> convertCurrencyBOToTO(List<Currency> currancybos) {
			log.info("enter convertCurrencyBOToTO" );
			List<CurrencyTO> currencylist= new ArrayList<CurrencyTO>();
			if(currancybos!=null){
				Iterator<Currency> itr= currancybos.iterator();
				while (itr.hasNext()) {
					Currency currency = (Currency) itr.next();
					CurrencyTO to= new CurrencyTO();
					to.setId(currency.getId());
					to.setName(currency.getCurrencyCode()+"-"+currency.getName());
					currencylist.add(to);
					
				}
			}
			log.info("exit convertCurrencyBOToTO" );
			return currencylist;
		}
		/**
		 * Income TO creation
		 * @param currancybos
		 * @return
		 */
		public List<IncomeTO> convertIncomeBOToTO(List<Object[]> incomebos) {
			log.info("enter convertIncomeBOToTO" );
			List<IncomeTO> incomelist= new ArrayList<IncomeTO>();
			if(incomebos!=null){
				Iterator<Object[]> itr= incomebos.iterator();
				while (itr.hasNext()) {
					Object[] income = (Object[]) itr.next();
					IncomeTO to= new IncomeTO();
					to.setId(Integer.parseInt(income[0].toString()));
					to.setIncomeRange(income[1].toString());
					incomelist.add(to);
					
				}
			}
			log.info("exit convertIncomeBOToTO" );
			return incomelist;
		}
		/**
		 * @param nationBOs
		 * @return
		 */
		public List<NationalityTO> convertNationalityBOtoTO(
				List<Nationality> nationBOs) {
			log.info("enter convertNationalityBOtoTO" );
			List<NationalityTO> toList=new ArrayList<NationalityTO>();
			if(nationBOs!=null && !nationBOs.isEmpty()){
				Iterator<Nationality> natItr=nationBOs.iterator();
				while (natItr.hasNext()) {
					Nationality nation = (Nationality) natItr.next();
					NationalityTO to=new NationalityTO();
					to.setId(String.valueOf(nation.getId()));
					to.setName(nation.getName());
					toList.add(to);
				}
			}
			log.info("exit convertNationalityBOtoTO" );
			return toList;
		}
		/**
		 * getting all course prerequisite tos
		 * @param requisiteBOs
		 * @return
		 */
		/*public List<CoursePrerequisiteTO> convertRequisiteBOstoTOS(List<CoursePrerequisite> requisiteBOs)throws Exception
		{
			log.info("enter convertRequisiteBOstoTOS" );
						
			List<CoursePrerequisiteTO> toList= new ArrayList<CoursePrerequisiteTO>();
			if(requisiteBOs!=null){
				Iterator<CoursePrerequisite> reqItr=requisiteBOs.iterator();
				while (reqItr.hasNext()) {
					CoursePrerequisite requisiteBO = (CoursePrerequisite) reqItr.next();
					CoursePrerequisiteTO to=new CoursePrerequisiteTO();
					double reqMark=0.0;
					double totMark=0.0;
					double perc=0.0;
					if(requisiteBO.getPercentage()!=null)
						reqMark=requisiteBO.getPercentage().doubleValue();
					if(requisiteBO.getTotalMark()!=null)
						totMark=requisiteBO.getTotalMark().doubleValue();
					if(reqMark!=0.0 && totMark!=0.0)
						perc=(reqMark/totMark)*100;
					to.setPercentage(perc);
					to.setTotalMark(totMark);
					
					Prerequisite preReqBO=requisiteBO.getPrerequisite();
					PrerequisiteTO reqTo= new PrerequisiteTO();
					reqTo.setId(preReqBO.getId());
					reqTo.setName(preReqBO.getName());
					to.setPrerequisiteTO(reqTo);
					toList.add(to);
				}
				
			}
			log.info("exit convertRequisiteBOstoTOS" );
			return toList;
		}*/
		/**
		 * @param admForm
		 * @return
		 */
		/*public Set<ApplicantWorkExperience> convertExperienceTostoBOs(
				OnlineApplicationForm admForm) {
			log.info("enter convertExperienceTostoBOs" );
			List<ApplicantWorkExperienceTO> toList= new ArrayList<ApplicantWorkExperienceTO>();
			Set<ApplicantWorkExperience> experienceBOS= new HashSet<ApplicantWorkExperience>();
			toList.add(admForm.getFirstExp());
			toList.add(admForm.getSecExp());
			toList.add(admForm.getThirdExp());
			Iterator<ApplicantWorkExperienceTO> toItr= toList.iterator();
			while (toItr.hasNext()) {
				ApplicantWorkExperienceTO tO = (ApplicantWorkExperienceTO) toItr.next();
				ApplicantWorkExperience bo= new ApplicantWorkExperience();
				bo.setOrganization(tO.getOrganization());
				bo.setPosition(tO.getPosition());
				bo.setFromDate(CommonUtil.ConvertStringToSQLDate(tO.getFromDate()));
				bo.setToDate(CommonUtil.ConvertStringToSQLDate(tO.getToDate()));
				if(tO.getSalary()!=null && !StringUtils.isEmpty(tO.getSalary())&& CommonUtil.isValidDecimal(tO.getSalary()))
				bo.setSalary(new BigDecimal(tO.getSalary()));
				bo.setReportingTo(tO.getReportingTo());
				experienceBOS.add(bo);
			}
			log.info("exit convertExperienceTostoBOs" );
			return experienceBOS;
		}*/

		
		//edit section started
		/**
		 * @param applicantDetail
		 * @param admForm 
		 * @return
		 */
		public AdmAppln getApplicantBO(AdmAppln appBO,AdmApplnTO applicantDetail,OnlineApplicationForm admForm,String saveMode) throws Exception {
			log.info("enter getApplicantBO" );
			
			if (applicantDetail!=null) {
				
				//appBO.setApplnNo(applicantDetail.getApplnNo());
				if(applicantDetail.getChallanRefNo()!=null)
				appBO.setChallanRefNo(applicantDetail.getChallanRefNo());
				if(applicantDetail.getJournalNo()!=null)
				appBO.setJournalNo(applicantDetail.getJournalNo());
				if(applicantDetail.getBankBranch()!=null)
				appBO.setBankBranch(applicantDetail.getBankBranch());
				if(applicantDetail.getChallanDate()!= null){
					appBO.setDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getChallanDate()));
				}
				if(applicantDetail.getDdDrawnOn()!=null){
					appBO.setDdDrawnOn(applicantDetail.getDdDrawnOn());
				}
				if(applicantDetail.getDdIssuingBank()!=null){
					appBO.setDdIssuingBank(applicantDetail.getDdIssuingBank());
				}
				if(applicantDetail.getRemark()!=null && !applicantDetail.getRemark().isEmpty())
					appBO.setRemarks(applicantDetail.getRemark());
				if(applicantDetail.getIsCancelled()!=null)
					appBO.setIsCancelled(applicantDetail.getIsCancelled());
				if(applicantDetail.getIsFreeShip()!=null)
					appBO.setIsFreeShip(applicantDetail.getIsFreeShip());
				if(applicantDetail.getIsChallanVerified()!=null)
					appBO.setIsChallanVerified(applicantDetail.getIsChallanVerified());
				if(applicantDetail.getIsLIG()!=null)
					appBO.setIsLig(applicantDetail.getIsLIG());
				if(applicantDetail.getIsFinalMeritApproved()!=null)
					appBO.setIsFinalMeritApproved(applicantDetail.getIsFinalMeritApproved());
				if(admForm.isAdmissionEdit()){
					appBO.setIsApproved(!admForm.isNeedApproval());
				}else
				{
					appBO.setIsApproved(applicantDetail.getIsApproved());
				}
				if(applicantDetail.getCourseChangeDate()!= null){
					appBO.setCourseChangeDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getCourseChangeDate()));
				}
						
				appBO.setIsChallanRecieved(applicantDetail.getIsChallanRecieved());
				appBO.setRecievedChallanNo(applicantDetail.getRecievedChallanNo());
				if(applicantDetail.getRecievedChallanDate()!=null )
					appBO.setRecievedChallanDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getRecievedChallanDate()));
						

				if(applicantDetail.getAdmissionDate()!= null){
					appBO.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getAdmissionDate()));
				}
				if(applicantDetail.getAmount()!=null && !StringUtils.isEmpty(applicantDetail.getAmount().trim()))
					appBO.setAmount(new BigDecimal(applicantDetail.getAmount()));
				if(applicantDetail.getTcNo()!=null && !StringUtils.isEmpty(applicantDetail.getTcNo().trim()))
					appBO.setTcNo(applicantDetail.getTcNo());
				if(applicantDetail.getTcDate()!=null && !StringUtils.isEmpty(applicantDetail.getTcDate()) && CommonUtil.isValidDate(applicantDetail.getTcDate()))
					appBO.setTcDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getTcDate()));
				if(applicantDetail.getMarkscardNo()!= null){
					appBO.setMarkscardNo(applicantDetail.getMarkscardNo());
				}
				if(applicantDetail.getMarkscardDate()!=null && !StringUtils.isEmpty(applicantDetail.getMarkscardDate()) && CommonUtil.isValidDate(applicantDetail.getMarkscardDate()))
					appBO.setMarkscardDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getMarkscardDate()));
				
				if(admForm.getInterviewSelectionDate()!= null && !admForm.getInterviewSelectionDate().isEmpty()){
					InterviewSelectionSchedule iss=new InterviewSelectionSchedule();
					iss.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
					appBO.setInterScheduleSelection(iss);
				}else
				{
					if(applicantDetail.getInterviewSelectionScheduleId()!=null && !applicantDetail.getInterviewSelectionScheduleId().isEmpty())
					{
						InterviewSelectionSchedule iss=new InterviewSelectionSchedule();
						iss.setId(Integer.parseInt(applicantDetail.getInterviewSelectionScheduleId()));
						appBO.setInterScheduleSelection(iss);
					}
				}
				if(admForm.getInterviewVenue()!= null && !admForm.getInterviewVenue().isEmpty()){
					ExamCenter examCenter = new ExamCenter();
					examCenter.setId(Integer.parseInt(admForm.getInterviewVenue()));
					appBO.setExamCenter(examCenter);
				}else
				{
					if(applicantDetail.getExamCenterId()>0){
						ExamCenter examCenter = new ExamCenter();
						examCenter.setId(applicantDetail.getExamCenterId());
						appBO.setExamCenter(examCenter);
					}
				}
				
				//CourseTO crsto=applicantDetail.getCourse();
				
				/*if (crsto!=null) {
					Course crs=new Course();
					ProgramType programType = new ProgramType();
					programType.setId(crsto.getProgramTypeId());
					
					Program program = new Program();
					program.setProgramType(programType);
					program.setId(crsto.getProgramId());
					crs.setProgram(program);
					crs.setId(crsto.getId());
					
					appBO.setCourse(crs);
					appBO.setCourseBySelectedCourseId(crs);
					
				}else{*/
					//raghu
					if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
						Course crs=new Course();
						crs.setId(Integer.parseInt(admForm.getCourseId()));
						appBO.setCourse(crs);
					}
				//}
				
				//CourseTO crsto1=applicantDetail.getSelectedCourse();
				
			/*	if (crsto1!=null) {
					Course crs=new Course();
					crs.setId(crsto1.getId());
					appBO.setCourseBySelectedCourseId(crs);
				}else{*/
					//raghu
					if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
						Course crs=new Course();
						crs.setId(Integer.parseInt(admForm.getCourseId()));
						appBO.setCourseBySelectedCourseId(crs);
					}
				//}
					
					
				CourseTO crsto2=applicantDetail.getAdmittedCourse();
				
				if (crsto2!=null) {
					Course crs=new Course();
					crs.setId(crsto2.getId());
					appBO.setAdmittedCourseId(crs);
				}else{
					//raghu
					if(admForm.getCourseId()!=null && !admForm.getCourseId().equalsIgnoreCase("")){
						Course crs=new Course();
						crs.setId(Integer.parseInt(admForm.getCourseId()));
						appBO.setAdmittedCourseId(crs);
					}
				}
				
				
				
				if(applicantDetail.getAppliedYear()!=null){
					appBO.setAppliedYear(applicantDetail.getAppliedYear());
					admForm.setApplicationYear(String.valueOf(applicantDetail.getAppliedYear()));
				}
				if(applicantDetail.getTotalWeightage()!=null)
					appBO.setTotalWeightage(new BigDecimal(applicantDetail.getTotalWeightage()));
				if(applicantDetail.getWeightageAdjustMark()!=null){
					appBO.setWeightageAdjustedMarks(new BigDecimal(applicantDetail.getWeightageAdjustMark()));
				}
				if(applicantDetail.getIsSelected()!=null){
					appBO.setIsSelected(applicantDetail.getIsSelected());
				}
				if(applicantDetail.getNotSelected()==null){
					appBO.setNotSelected(false);
				}
				else{
					appBO.setNotSelected(applicantDetail.getNotSelected());
				}
				if(applicantDetail.getIsWaiting()==null){
					appBO.setIsWaiting(false);
				}
				else{
					appBO.setIsWaiting(applicantDetail.getIsWaiting());
				}
				if(applicantDetail.getIsBypassed()!=null){
					appBO.setIsBypassed(applicantDetail.getIsBypassed());
				}
				if(applicantDetail.getIsInterviewSelected()!=null){
					appBO.setIsInterviewSelected(applicantDetail.getIsInterviewSelected());
				}
				if(applicantDetail.getCandidatePrerequisiteMarks()!=null){
					appBO.setCandidatePrerequisiteMarks(applicantDetail.getCandidatePrerequisiteMarks());
				}
				if(applicantDetail.getOriginalQualDetails()!=null){
					appBO.setStudentQualifyexamDetails(applicantDetail.getOriginalQualDetails());
				}
				if(applicantDetail.getAdmittedThroughId()!=null && !StringUtils.isEmpty(applicantDetail.getAdmittedThroughId()) && StringUtils.isNumeric(applicantDetail.getAdmittedThroughId()))
				{
					AdmittedThrough admit= new AdmittedThrough();
					admit.setId(Integer.parseInt(applicantDetail.getAdmittedThroughId()));
					appBO.setAdmittedThrough(admit);
				}
				
				//lateral entry details
				if(applicantDetail.getLateralDetailBos()!=null && !applicantDetail.getLateralDetailBos().isEmpty()){
					appBO.setApplicantLateralDetailses(applicantDetail.getLateralDetailBos());
				}
				
				//transfer entry details
				if(applicantDetail.getTransferDetailBos()!=null && !applicantDetail.getTransferDetailBos().isEmpty()){
					appBO.setApplicantTransferDetailses(applicantDetail.getTransferDetailBos());
				}
			

				if(applicantDetail.getSubjectGroupIds()!=null && applicantDetail.getSubjectGroupIds().length!=0)
				{
					Set<ApplicantSubjectGroup> sgSet=new HashSet<ApplicantSubjectGroup>();
					
					ApplicantSubjectGroup sg;
					
					if (applicantDetail.getSubjectGroupIds().length!=0) {
						
							String[] sgids = applicantDetail.getSubjectGroupIds();
							List<ApplicantSubjectGroup> applicants =applicantDetail.getApplicantSubjectGroups();
							sg=new ApplicantSubjectGroup();
							for (int j = 0; j < sgids.length; j++) {
								if(applicants!=null && applicants.size()>j){
									sg=applicants.get(j);	
								}else{
									sg=new ApplicantSubjectGroup();
								}
								if (StringUtils.isNumeric(sgids[j])) {

									SubjectGroup group = new SubjectGroup();
									group.setId(Integer.parseInt(sgids[j]));
									sg.setSubjectGroup(group);

								}
								sgSet.add(sg);
							
						}
					}
					appBO.setApplicantSubjectGroups(sgSet);
				}
				appBO.setCreatedBy(applicantDetail.getCreatedBy());
				appBO.setCreatedDate(new Date());
				appBO.setModifiedBy(admForm.getUserId());
				appBO.setLastModifiedDate(new Date());
				if(applicantDetail.getIsDraftMode()!=null){
					if(applicantDetail.getIsDraftMode())
						appBO.setIsDraftMode(applicantDetail.getIsDraftMode());
					else
						appBO.setIsDraftMode(false);
				}
				else{
					appBO.setIsDraftMode(false);
				}
				 if(applicantDetail.getEntranceDetail()!=null){
					 Set<CandidateEntranceDetails> entranceSet=new HashSet<CandidateEntranceDetails>();
					 CandidateEntranceDetailsTO detTo= applicantDetail.getEntranceDetail();
					 CandidateEntranceDetails bo= new CandidateEntranceDetails();
					 	if(detTo.getId()!=0)
					 		bo.setId(detTo.getId());
						if(detTo.getAdmApplnId()!=0){
							AdmAppln adm= new AdmAppln();
							adm.setId(detTo.getAdmApplnId());
							bo.setAdmAppln(adm);
						}
						if(detTo.getEntranceId()!=0){
							Entrance admt= new Entrance();
							admt.setId(detTo.getEntranceId());
							bo.setEntrance(admt);
						}
							if(detTo.getMarksObtained()!=null && !StringUtils.isEmpty(detTo.getMarksObtained().trim()) && CommonUtil.isValidDecimal(detTo.getMarksObtained().trim()))
							bo.setMarksObtained(new BigDecimal(detTo.getMarksObtained()));
							if(detTo.getTotalMarks()!=null && !StringUtils.isEmpty(detTo.getTotalMarks().trim()) && CommonUtil.isValidDecimal(detTo.getTotalMarks().trim()))
							bo.setTotalMarks(new BigDecimal(detTo.getTotalMarks()));
							bo.setEntranceRollNo(detTo.getEntranceRollNo());
							if(detTo.getMonthPassing()!=null && !StringUtils.isEmpty(detTo.getMonthPassing().trim()) && StringUtils.isNumeric(detTo.getMonthPassing().trim()))
							bo.setMonthPassing(Integer.valueOf(detTo.getMonthPassing()));
							if(detTo.getYearPassing()!=null && !StringUtils.isEmpty(detTo.getYearPassing().trim()) && StringUtils.isNumeric(detTo.getYearPassing().trim()))
							bo.setYearPassing(Integer.valueOf(detTo.getYearPassing()));
						
							entranceSet.add(bo);
							appBO.setCandidateEntranceDetailses(entranceSet);
				 }
				
				//set personal and edn doc details
				setPersonaldataBO(appBO,applicantDetail,admForm);
				if(admForm.getPreferenceList()!=null && !admForm.getPreferenceList().isEmpty()){
					//setPreferenceBos(appBO,admForm.getPreferenceList());
				}
				//raghu
				if(admForm.getPrefcourses()!=null && !admForm.getPrefcourses().isEmpty()){
					setPreferenceBo( appBO,admForm.getPrefcourses());
				}
				/*setvehicleDetailsEdit(appBO,applicantDetail);
				setworkExpDetails(appBO,applicantDetail);*/
				
				//raghu
				setDocUploads(appBO,applicantDetail,admForm,saveMode);
				
				
				setOriginalSubmittedDocsList(applicantDetail,admForm);
				if(applicantDetail.getMode()!=null && !applicantDetail.getMode().isEmpty())
					appBO.setMode(applicantDetail.getMode());
				if(applicantDetail.getAdmStatus()!=null && !applicantDetail.getAdmStatus().isEmpty())
					appBO.setAdmStatus(applicantDetail.getAdmStatus());
				
				if(applicantDetail.getStudentSpecializationPrefered()!=null && !applicantDetail.getStudentSpecializationPrefered().isEmpty())
					appBO.setStudentSpecializationPrefered(applicantDetail.getStudentSpecializationPrefered());
				if(applicantDetail.getSeatNo()!=null && !applicantDetail.getSeatNo().isEmpty())
					appBO.setSeatNo(applicantDetail.getSeatNo());
				if(applicantDetail.getIsPreferenceUpdated()!=null)
					appBO.setIsPreferenceUpdated(applicantDetail.getIsPreferenceUpdated());
				if(applicantDetail.getFinalMeritListApproveDate()!=null)
					appBO.setFinalMeritListApproveDate(applicantDetail.getFinalMeritListApproveDate());
				if(applicantDetail.getVerifiedBy()!=null && !applicantDetail.getVerifiedBy().isEmpty())
					appBO.setVerifiedBy(applicantDetail.getVerifiedBy());
				if(applicantDetail.getAdmapplnAdditionalInfos()!=null && !applicantDetail.getAdmapplnAdditionalInfos().isEmpty()){
					Set<AdmapplnAdditionalInfo> set = new HashSet<AdmapplnAdditionalInfo>(); 
					List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(applicantDetail.getAdmapplnAdditionalInfos());
					AdmapplnAdditionalInfo additionalInfo = additionalList.get(0);
					additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
					additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
					additionalInfo.setBackLogs(admForm.isBackLogs());
					//raghu
					additionalInfo.setIsSaypass(admForm.getIsSaypass());
					additionalInfo.setIsSpotAdmission(false);
					
					additionalInfo.setModifiedBy(admForm.getUserId());
					additionalInfo.setLastModifiedDate(new Date());
					if(applicantDetail.getIsComeDk()!=null){
						if(applicantDetail.getIsComeDk())
							additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
						else
							additionalInfo.setIsComedk(false);
					}
					else{
						additionalInfo.setIsComedk(false);
					}
					// 
					set.add(additionalInfo);
					appBO.setAdmapplnAdditionalInfo(set);
				}else{
					Set<AdmapplnAdditionalInfo> admAddnSet=new HashSet<AdmapplnAdditionalInfo>();
					AdmapplnAdditionalInfo additionalInfo=new AdmapplnAdditionalInfo();
					additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
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
					additionalInfo.setCreatedBy(appBO.getCreatedBy());
					additionalInfo.setCreatedDate(new Date());
					additionalInfo.setModifiedBy(appBO.getCreatedBy());
					additionalInfo.setLastModifiedDate(new Date());
					additionalInfo.setBackLogs(admForm.isBackLogs());
					//raghu
					additionalInfo.setIsSaypass(admForm.getIsSaypass());
					additionalInfo.setIsSpotAdmission(false);
					
					if(applicantDetail.getIsComeDk()!=null){
						if(applicantDetail.getIsComeDk())	
							additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
						else
							additionalInfo.setIsComedk(false);
						}else{
							additionalInfo.setIsComedk(false);
					}
					admAddnSet.add(additionalInfo);
					appBO.setAdmapplnAdditionalInfo(admAddnSet);
				}
				if(admForm.getUniqueId()!=null && !admForm.getUniqueId().isEmpty()){
					StudentOnlineApplication onlineApplication = new StudentOnlineApplication();
					onlineApplication.setId(Integer.parseInt(admForm.getUniqueId()));
					appBO.setStudentOnlineApplication(onlineApplication);
				}
				
				
				
				
			}
			log.info("exit getApplicantBO" );
			return appBO;
		}
		/**
		 * create list of original document submitted
		 * @param applicantDetail
		 * @param admForm
		 */
		private void setOriginalSubmittedDocsList(AdmApplnTO applicantDetail,
			OnlineApplicationForm admForm) {
			log.info("enter setOriginalSubmittedDocsList" );
			List<String> originalList= new ArrayList<String>();
			if(applicantDetail!=null && applicantDetail.getEditDocuments()!=null){
				
				Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
				while (docItr.hasNext()) {
					ApplnDocTO docTO = (ApplnDocTO) docItr.next();
					if(docTO.isHardSubmitted()){
						String name="";
						if(docTO.isPhoto())
						{
							name=OnlineApplicationHelper.PHOTO;
						}else{
						name=docTO.getPrintName();
						}
						originalList.add(name);
					}
					
				}
				
			}
			admForm.setOriginalDocList(originalList);
			log.info("exit setOriginalSubmittedDocsList" );
	}
		/**
		 * Doc uploads set to AdmAppln
		 * @param appBO
		 * @param applicantDetail
		 * @param admForm
		 * @throws Exception
		 */
		private void setDocUploads(AdmAppln appBO, AdmApplnTO applicantDetail, OnlineApplicationForm admForm,String saveMode) throws Exception{
			log.info("enter setDocUploads" );
			if(applicantDetail!=null && applicantDetail.getEditDocuments()!=null){
				Set<ApplnDoc> docSet= new HashSet<ApplnDoc>();
				Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
				while (docItr.hasNext()) {
					ApplnDocTO docTO = (ApplnDocTO) docItr.next();
					ApplnDoc docBO= new ApplnDoc();
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
						if(admForm.getSubmitDate()!=null && !StringUtils.isEmpty(admForm.getSubmitDate()))
						docBO.setSubmissionDate(CommonUtil.ConvertStringToSQLDate(admForm.getSubmitDate()));
					}else{
						docBO.setSubmissionDate(null);
					}
					docBO.setIsPhoto(docTO.isPhoto());
					docBO.setIsMngQuotaForm(docTO.isMngQuotaForm());
					//raghu
					//if(saveMode.equalsIgnoreCase("Submit") && docTO.getEditDocument()!=null && docTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(docTO.getEditDocument().getFileName())){
					if(docTO.getEditDocument()!=null && docTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(docTO.getEditDocument().getFileName()) && !docTO.isMngQuotaForm()){
						
						FormFile editDoc=docTO.getEditDocument();
						docBO.setDocument(editDoc.getFileData());
						if(applicantDetail.getStudentId() != 0){
							try {
								FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+applicantDetail.getStudentId()+".jpg");
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
						if(applicantDetail.getStudentId() != 0){
							try {
								FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_MANAGEMENT_QUOTA_FORM_FOLDER_PATH+applicantDetail.getStudentId()+".jpg");
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
					docSet.add(docBO);
				}
				appBO.setApplnDocs(docSet);
			}
			log.info("exit setDocUploads" );
	}
		/**
		 * Admission Form work Exp set
		 * @param appBO
		 * @param applicantDetail
		 */
		/*private void setworkExpDetails(AdmAppln appBO, AdmApplnTO applicantDetail) {
			log.info("enter setworkExpDetails" );
		if(applicantDetail!=null && applicantDetail.getWorkExpList()!=null){
			Set<ApplicantWorkExperience> expSet= new HashSet<ApplicantWorkExperience>();
			Iterator<ApplicantWorkExperienceTO> expItr=applicantDetail.getWorkExpList().iterator();
			while (expItr.hasNext()) {
				ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) expItr.next();
				ApplicantWorkExperience expBo=new ApplicantWorkExperience();
				if(expTO.getId()!=0)
				expBo.setId(expTO.getId());
				if(expTO.getFromDate()!=null && !StringUtils.isEmpty(expTO.getFromDate()))
				expBo.setFromDate(CommonUtil.ConvertStringToSQLDate(expTO.getFromDate()));
				if(expTO.getToDate()!=null && !StringUtils.isEmpty(expTO.getToDate()))
				expBo.setToDate(CommonUtil.ConvertStringToSQLDate(expTO.getToDate()));
				expBo.setOrganization(expTO.getOrganization());
				expBo.setPosition(expTO.getPosition());
				expBo.setIsCurrent(expTO.getIsCurrent());
				if(expTO.getSalary()!=null && !StringUtils.isEmpty(expTO.getSalary()) && CommonUtil.isValidDecimal(expTO.getSalary()))
				expBo.setSalary(new BigDecimal(expTO.getSalary()));
				expBo.setReportingTo(expTO.getReportingTo());
				expBo.setAddress(expTO.getAddress());
				expBo.setPhoneNo(expTO.getPhoneNo());
				if(expTO.getOccupationId()!=null && !expTO.getOccupationId().isEmpty() && !expTO.getOccupationId().equalsIgnoreCase("Other")){
					Occupation occupation=new Occupation();
					occupation.setId(Integer.parseInt(expTO.getOccupationId()));
					expBo.setOccupation(occupation);
					expBo.setPosition(null);
				}
				expSet.add(expBo);
			}
			appBO.setApplicantWorkExperiences(expSet);
		}
		log.info("exit setworkExpDetails" );
	}*/
		/**
		 * Admission Form Vehicle Details Set
		 * @param appBO
		 * @param applicantDetail
		 */
		/*private void setvehicleDetailsEdit(AdmAppln appBO,
				AdmApplnTO applicantDetail) {
			log.info("enter setvehicleDetailsEdit" );
			if(applicantDetail.getVehicleDetail()!=null){
				if((applicantDetail.getVehicleDetail().getId()==0) 
						&& (applicantDetail.getVehicleDetail().getVehicleType()==null || StringUtils.isEmpty(applicantDetail.getVehicleDetail().getVehicleType()))
						&& (applicantDetail.getVehicleDetail().getVehicleNo()==null || StringUtils.isEmpty(applicantDetail.getVehicleDetail().getVehicleNo()))){
					return;
				}
				else{
					Set<StudentVehicleDetails> vehSet=new HashSet<StudentVehicleDetails>();
					StudentVehicleDetails vehBo=new StudentVehicleDetails();
					if(applicantDetail.getVehicleDetail().getId()!=0)
					vehBo.setId(applicantDetail.getVehicleDetail().getId());
					vehBo.setVehicleNo(applicantDetail.getVehicleDetail().getVehicleNo());
					vehBo.setVehicleType(applicantDetail.getVehicleDetail().getVehicleType());
					vehSet.add(vehBo);
					appBO.setStudentVehicleDetailses(vehSet);
				}
			}
			log.info("exit setvehicleDetailsEdit" );
		}*/
		/**
		 * Set preference BOs to AdmApplnBO
		 * @param appBO
		 * @param preferenceList
		 */
		/*private void setPreferenceBos(AdmAppln appBO,
				List<CandidatePreferenceTO> preferenceList) {
			log.info("enter setPreferenceBos" );
			Set<CandidatePreference> preferencebos=new HashSet<CandidatePreference>();
			Iterator<CandidatePreferenceTO> toItr= preferenceList.iterator();
			while (toItr.hasNext()) {
				CandidatePreferenceTO prefTO = (CandidatePreferenceTO) toItr.next();
				if (prefTO!=null && prefTO.getCoursId()!=null && !StringUtils.isEmpty(prefTO.getCoursId()) 
						&& StringUtils.isNumeric(prefTO.getCoursId()) && Integer.parseInt(prefTO.getCoursId())!=0) {
					CandidatePreference bo = new CandidatePreference();
					if(prefTO.getId()!=0)
					bo.setId(prefTO.getId());
					bo.setPrefNo(prefTO.getPrefNo());
					
					Course prefcrs = new Course();
					
					prefcrs.setId(Integer.parseInt(prefTO.getCoursId()));
					bo.setCourse(prefcrs);
					preferencebos.add(bo);
				}
			}
			appBO.setCandidatePreferences(preferencebos);
			log.info("exit setPreferenceBos" );
		}*/
		
		
		//raghu set new preferences
		private void setPreferenceBo(AdmAppln appBO,
				List<CourseTO> preferenceList) {
			log.info("enter setPreferenceBos" );
			Set<CandidatePreference> preferencebos=new HashSet<CandidatePreference>();
			Iterator<CourseTO> toItr= preferenceList.iterator();
			
			while (toItr.hasNext()) {
				CourseTO courseTO = (CourseTO) toItr.next();
				if (courseTO!=null && courseTO.getId()!=0) {
					
					
					/*AdmAppln appB=new AdmAppln();
					appB.setId(appBO.getId());
					bo.setAdmAppln(appB);*/
					if(courseTO.getPrefNo()!=null &&!courseTO.getPrefNo().equalsIgnoreCase("")){
					CandidatePreference bo = new CandidatePreference();
					if(courseTO.getPrefId()!=0)
					bo.setId(courseTO.getPrefId());
					bo.setPrefNo(Integer.parseInt(courseTO.getPrefNo()));
					Course course = new Course();
					course.setId(courseTO.getId());
					bo.setCourse(course);
					preferencebos.add(bo);
					}
					
				}
			}
			appBO.setCandidatePreferences(preferencebos);
			log.info("exit setPreferenceBos" );
		}
		
		
		/**
		 * Admission Form personal data BO Populate
		 * @param appBO
		 * @param applicantDetail
		 * @throws Exception 
		 */
		private void setPersonaldataBO(AdmAppln appBO, AdmApplnTO applicantDetail,OnlineApplicationForm admForm) throws Exception {
			log.info("enter setPersonaldataBO" );
			if(applicantDetail.getPersonalData()!=null){
				PersonalDataTO dataTo=applicantDetail.getPersonalData();
				PersonalData data= appBO.getPersonalData();
				//data.setId(dataTo.getId());
				//data.setCreatedBy(applicantDetail.getPersonalData().getCreatedBy());
				//data.setCreatedDate(applicantDetail.getPersonalData().getCreatedDate());
				//data.setIsmgquota(dataTo.getIsmgquota());
				data.setModifiedBy(admForm.getUserId());
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
				 
				 setExtracurricullars(dataTo,data);
				 
				
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
				
				//extra details
				//raghu
				
				 if (dataTo.getUgcourse()!=null && !StringUtils.isEmpty(dataTo.getUgcourse()) && StringUtils.isNumeric(dataTo.getUgcourse())) {
						UGCoursesBO ug= new UGCoursesBO();
						ug.setId(Integer.parseInt(dataTo.getUgcourse()));
						data.setUgcourse(ug);
				 }
				 
				/* if(dataTo.isHosteladmission()==true && admForm.isHostelcheck()==true){
						data.setIsHostelAdmission(true);
						}
						else
							data.setIsHostelAdmission(false);
				*/	
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
				if(admForm.getCourseId()!=null && admForm.getCourseId().equalsIgnoreCase("18")){
					if(dataTo.getNoofenglishCoreCourses()!=null)	
						 data.setNoofenglishCoreCourses(dataTo.getNoofenglishCoreCourses());
					
				}else{
					 data.setNoofenglishCoreCourses(null);
				}
				 
				 
				/*if(dataTo.getDioceseId()!=null &&!StringUtils.isEmpty(dataTo.getDioceseId()) && StringUtils.isNumeric(dataTo.getDioceseId())){
				DioceseBO  dioceseBO=new DioceseBO();
				dioceseBO.setId(Integer.parseInt(dataTo.getDioceseId()));
				data.setDiocese(dioceseBO);
			}
			if(dataTo.getParishId()!=null &&!StringUtils.isEmpty(dataTo.getParishId()) && StringUtils.isNumeric(dataTo.getParishId())){
				ParishBO  parishBO=new ParishBO();
				parishBO.setId(Integer.parseInt(dataTo.getParishId()));
				data.setParish(parishBO);
			}else {
				data.setParishId(null);
				if(dataTo.getParishOthers()!=null){
					data.setParishOthers(dataTo.getParishOthers());
				}
			}
			*/
				
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
					admForm.setBirthCountryId(Integer.parseInt(dataTo.getBirthCountry()));
					
					}
				if(dataTo.getPermanentAddressLine1()!=null && !dataTo.getPermanentAddressLine1().isEmpty())
					data.setPermanentAddressLine1(dataTo.getPermanentAddressLine1());
				if(dataTo.getPermanentAddressLine2()!=null && !dataTo.getPermanentAddressLine2().isEmpty())
					data.setPermanentAddressLine2(dataTo.getPermanentAddressLine2());
				if(dataTo.getPermanentAddressZipCode()!=null && !dataTo.getPermanentAddressZipCode().isEmpty())
					data.setPermanentAddressZipCode(dataTo.getPermanentAddressZipCode());
				if(dataTo.getPermanentCityName()!=null && !dataTo.getPermanentCityName().isEmpty())
					data.setCityByPermanentAddressCityId(dataTo.getPermanentCityName());
				//raghu
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
						admForm.setPerAddrCountyId(dataTo.getPermanentCountryId());
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
							admForm.setCurAddrCountyId(dataTo.getCurrentCountryId());

				}	
				
				//raghu
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
									&& dataTo.getFatherOccupationId().equalsIgnoreCase(OnlineApplicationHelper.OTHER) && dataTo.getOtherOccupationFather()!=null 
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
									&& dataTo.getMotherOccupationId().equalsIgnoreCase(OnlineApplicationHelper.OTHER) && dataTo.getOtherOccupationMother()!=null 
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
							admForm.setParentAddrCountyId(dataTo.getParentCountryId());
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
							
							//guardian's
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
								admForm.setGuardianAddrCountyId(dataTo.getCountryByGuardianAddressCountryId());
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
							if(admForm.getRecomendedBy()!=null && !admForm.getRecomendedBy().isEmpty()){
								data.setRecommendedBy(admForm.getRecomendedBy());
							}
							if(dataTo.getUniversityEmail()!=null && !dataTo.getUniversityEmail().isEmpty())
								data.setUniversityEmail(dataTo.getUniversityEmail());
							appBO.setPersonalData(data);
							//raghu
							//setEdnqualificationBO(appBO,applicantDetail);
							//set education details
							setEdnqualificationBO(appBO,applicantDetail,admForm);
							//setPreferences(appBO,applicantDetail);
							setDocumentupload(appBO,applicantDetail);
							
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
			}
			log.info("exit setPersonaldataBO" );
		}
		
		/**
		 * @param dataTo
		 * @param data
		 */
		private void setExtracurricullars(PersonalDataTO dataTo, PersonalData data) {
			log.info("enter setExtracurricullars" );
			Set<StudentExtracurricular> exSet= new HashSet<StudentExtracurricular>();
			List<Integer> unmodifiedlist= new ArrayList<Integer>();
			String[] updatedIDs=dataTo.getExtracurricularIds();
				 if(updatedIDs!=null && updatedIDs.length!=0){
					 
					 
					 for(int i=0;i<dataTo.getExtracurricularIds().length;i++){
						 int updateID=Integer.parseInt(updatedIDs[i]);
								 unmodifiedlist.add(updateID);
					 }
				 }

			 
			 if(!unmodifiedlist.isEmpty()){
				 List<StudentExtracurricular> originalBos=dataTo.getStudentExtracurriculars();
				 if(originalBos!=null){
					 Iterator<StudentExtracurricular> origItr=originalBos.iterator();
					 while (origItr.hasNext()) {
						StudentExtracurricular extracur = (StudentExtracurricular) origItr.next();
						if(extracur.getExtracurricularActivity()!=null){
							if(unmodifiedlist.contains(extracur.getExtracurricularActivity().getId())){
								extracur.setModifiedBy(data.getModifiedBy());
								extracur.setLastModifiedDate(new Date());
								exSet.add(extracur);
								unmodifiedlist.remove(Integer.valueOf((extracur.getExtracurricularActivity().getId())));
							}
						}
					}
				 }
			 }
			 
			 if(!unmodifiedlist.isEmpty())
			 {
				 Iterator<Integer> unmItr=unmodifiedlist.iterator();
				 while (unmItr.hasNext()) {
					Integer newID = (Integer) unmItr.next();
					ExtracurricularActivity newAct=new ExtracurricularActivity();
					newAct.setId(newID);
					StudentExtracurricular ext=new StudentExtracurricular();
					ext.setExtracurricularActivity(newAct);
					ext.setIsActive(true);
					ext.setCreatedBy(data.getModifiedBy());
					ext.setCreatedDate(new Date());
					exSet.add(ext);
					
				}
			 }
			 
			 data.setStudentExtracurriculars(exSet);
			 log.info("exit setExtracurricullars" );
		}
		/**
		 * Admission Form attachment set
		 * @param appBO
		 * @param applicantDetail
		 */
		private void setDocumentupload(AdmAppln appBO, AdmApplnTO applicantDetail) {
			log.info("enter setDocumentupload" );
			Set<ApplnDoc> uploadbolist=new HashSet<ApplnDoc>();
			List<ApplnDocTO> uploadtoList=applicantDetail.getDocumentsList();
			 if(uploadtoList!=null && !uploadtoList.isEmpty()){
				 Iterator<ApplnDocTO> toItr=uploadtoList.iterator();
				 while (toItr.hasNext()) {
					ApplnDocTO uploadto = (ApplnDocTO) toItr.next();
					ApplnDoc uploadBO= new ApplnDoc();
					if(uploadto.getId()!=0)
					uploadBO.setId(uploadto.getId());
					uploadBO.setCreatedBy(uploadto.getCreatedBy());
					uploadBO.setCreatedDate(uploadto.getCreatedDate());
					uploadBO.setModifiedBy(appBO.getModifiedBy());
					uploadBO.setLastModifiedDate(new Date());
					DocType doctype= new DocType();
					doctype.setId(uploadto.getDocTypeId());
					uploadBO.setDocType(doctype);
					uploadBO.setIsVerified(uploadto.isVerified());
						try {
							//raghu write new
							//if(uploadto.getDocument()!=null && uploadto.getDocument().getFileName()!=null && !StringUtils.isEmpty(uploadto.getDocument().getFileName())){
							if(uploadto.getDocument()!=null && uploadto.getDocument().getFileName()!=null && !StringUtils.isEmpty(uploadto.getDocument().getFileName()) && uploadto.getDocument().getFileData()!=null){
								uploadBO.setDocument(uploadto.getDocument().getFileData());
								uploadBO.setName(uploadto.getDocument().getFileName());
								uploadBO.setContentType(uploadto.getDocument().getContentType());
								uploadbolist.add(uploadBO);	
							}
						} catch (FileNotFoundException e) {
							log.error(e);
						} catch (IOException e) {
							log.error(e);
						}
					
				}
			 }

			 log.info("exit setDocumentupload" );
		}
		/**
		 * Admission Form preference set
		 * @param appBO
		 * @param applicantDetail
		 */
		/*private void setPreferences(AdmAppln appBO, AdmApplnTO applicantDetail) {

			log.info("enter setPreferences" );
			
			Set<CandidatePreference> preferences= new HashSet<CandidatePreference>();
			PreferenceTO prefTo=applicantDetail.getPreference();
			CandidatePreference firstPref;
			if(prefTo!=null){
			if (prefTo.getFirstPrefCourseId()!=null && !StringUtils.isEmpty(prefTo.getFirstPrefCourseId()) && StringUtils.isNumeric(prefTo.getFirstPrefCourseId())) {
				firstPref = new CandidatePreference();
				firstPref.setId(prefTo.getFirstPerfId());
				Course firstCourse = new Course();
				firstCourse.setId(Integer.parseInt(prefTo.getFirstPrefCourseId()));
				firstPref.setCourse(firstCourse);
				firstPref.setPrefNo(1);
				preferences.add(firstPref);
			}
			CandidatePreference secPref;
			if (prefTo.getSecondPrefCourseId()!=null&& !StringUtils.isEmpty(prefTo.getSecondPrefCourseId()) && StringUtils.isNumeric(prefTo.getSecondPrefCourseId()) ) {
				secPref = new CandidatePreference();
				secPref.setId(prefTo.getSecondPerfId());
				Course secCourse = new Course();
				secCourse.setId(Integer.parseInt(prefTo.getSecondPrefCourseId()));
				secPref.setCourse(secCourse);
				secPref.setPrefNo(2);
				preferences.add(secPref);
			}
			CandidatePreference thirdPref;
			
			if (prefTo.getThirdPrefCourseId()!=null && !StringUtils.isEmpty(prefTo.getThirdPrefCourseId()) && StringUtils.isNumeric(prefTo.getThirdPrefCourseId()) ) {
				thirdPref = new CandidatePreference();
				thirdPref.setId(prefTo.getThirdPerfId());
				Course thirdCourse = new Course();
				thirdCourse.setId(Integer.parseInt(prefTo.getThirdPrefCourseId()));
				thirdPref.setCourse(thirdCourse);
				thirdPref.setPrefNo(3);
				preferences.add(thirdPref);
			}
			
			appBO.setCandidatePreferences(preferences);
			}
			log.info("exit setPreferences" );
		}*/
		
		
		/**
		 * Admission Form BO Creation
		 * @param appBO
		 * @param applicantDetail
		 * @throws Exception 
		 */
		//raghu get EdnqualificationBO and storing database
		private void setEdnqualificationBO(AdmAppln appBO,
				AdmApplnTO applicantDetail,OnlineApplicationForm admForm) throws Exception {
			log.info("enter setEdnqualificationBO" );
			
			Set<EdnQualification> qualificationSet=null;
			if(applicantDetail.getEdnQualificationList()!=null){
				qualificationSet=new HashSet<EdnQualification>();
				List<EdnQualificationTO> qualifications=applicantDetail.getEdnQualificationList();
				Iterator<EdnQualificationTO> itr= qualifications.iterator();
				
				while (itr.hasNext()) {
					
					EdnQualificationTO qualificationTO = (EdnQualificationTO) itr.next();
					EdnQualification bo=new EdnQualification();
					if(qualificationTO.getId()!=0)
					bo.setId(qualificationTO.getId());
					bo.setCreatedBy(qualificationTO.getCreatedBy());
					bo.setCreatedDate(qualificationTO.getCreatedDate());
					bo.setModifiedBy(appBO.getModifiedBy());
					bo.setLastModifiedDate(new Date());
					
					if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& StringUtils.isNumeric(qualificationTO.getStateId())){
						State st= new State();
						st.setId(Integer.parseInt(qualificationTO.getStateId()));
						bo.setState(st);
					}else if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& qualificationTO.getStateId().equalsIgnoreCase(CMSConstants.OUTSIDE_INDIA)){
						bo.setState(null);
						bo.setIsOutsideIndia(true);
					}
					
					if (qualificationTO.getInstitutionId()!=null && !StringUtils.isEmpty(qualificationTO.getInstitutionId())&& StringUtils.isNumeric(qualificationTO.getInstitutionId())) {
						if(Integer.parseInt(qualificationTO.getInstitutionId())!=0){
							College col= new College();
							col.setId(Integer.parseInt(qualificationTO.getInstitutionId()));
							bo.setCollege(col);
						}else
						{
							bo.setCollege(null);
						}
					}else{
						if(qualificationTO.getOtherInstitute()!=null)
						bo.setInstitutionNameOthers(WordUtils.capitalize(qualificationTO.getOtherInstitute().toLowerCase()));
					}
					
					//set doc exam type
					if(qualificationTO.getSelectedExamId()!=null && !StringUtils.isEmpty(qualificationTO.getSelectedExamId()) && StringUtils.isNumeric(qualificationTO.getSelectedExamId())){
						DocTypeExams exmBo= new DocTypeExams();
						exmBo.setId(Integer.parseInt(qualificationTO.getSelectedExamId()));
						bo.setDocTypeExams(exmBo);
					}
					
					//we are storing only percentage raghu
					//consolidate marks and education marks
					if (qualificationTO.getDocName()=="Class 12" || qualificationTO.getDocName().equalsIgnoreCase("Class 12")) {
					if(qualificationTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualificationTO.getMarksObtained()))
						bo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
					if(qualificationTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualificationTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualificationTO.getTotalMarks()))
					bo.setTotalMarks(new BigDecimal(qualificationTO.getTotalMarks()));
					}
					/*
					if(bo.getMarksObtained()!=null && bo.getTotalMarks()!=null && bo.getTotalMarks().floatValue()!=0.0 && bo.getMarksObtained().floatValue()!=0 ){
					float percentageMarks = bo.getMarksObtained().floatValue()/bo.getTotalMarks().floatValue()*100 ;
					bo.setPercentage(new BigDecimal(percentageMarks));
					}else{
						bo.setPercentage(new BigDecimal(0));
						if(isPresidance && qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
							bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
						}
					}*/
					
					if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
						bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
					}else{
						bo.setPercentage(new BigDecimal(0));
						
					}
					
					//consolidate marks and education marks over
					
					if (qualificationTO.getUniversityId()!=null && !StringUtils.isEmpty(qualificationTO.getUniversityId())&& StringUtils.isNumeric(qualificationTO.getUniversityId())) {
						if(Integer.parseInt(qualificationTO.getUniversityId())!=0){
							University un=new University();
							un.setId(Integer.parseInt(qualificationTO.getUniversityId()));
							bo.setUniversity(un);
						}else
						{
							bo.setUniversity(null);
						}
					}else{
						if(qualificationTO.getUniversityOthers()!=null)
						bo.setUniversityOthers(WordUtils.capitalize(qualificationTO.getUniversityOthers().toLowerCase()));
					}
					
					bo.setNoOfAttempts(qualificationTO.getNoOfAttempts());
					bo.setYearPassing(qualificationTO.getYearPassing());
					bo.setMonthPassing(qualificationTO.getMonthPassing());
					bo.setPreviousRegNo(qualificationTO.getPreviousRegNo());
					if(qualificationTO.getDocCheckListId()!=0){
					DocChecklist docList= new DocChecklist();
					docList.setId(qualificationTO.getDocCheckListId());
					bo.setDocChecklist(docList);
					}else
					{
						bo.setDocChecklist(null);
					}
					
					
					//detail mark of class12 and degree
					Set<CandidateMarks> detailMarks=new HashSet<CandidateMarks>();
					
					if(!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise()){
						CandidateMarks detailMark= new CandidateMarks();
						CandidateMarkTO detailMarkto=qualificationTO.getDetailmark();
						if (detailMarkto!=null) {
							if(detailMarkto.getId()!=0)
							detailMark.setId(detailMarkto.getId());
							detailMark.setCreatedBy(detailMarkto.getCreatedBy());
							detailMark.setCreatedDate(detailMarkto.getCreatedDate());
							detailMark.setModifiedBy(appBO.getModifiedBy());
							detailMark.setLastModifiedDate(new Date());
							double totalmark=0.0;
							double totalobtained=0.0;
							double percentage=0.0;
							
							
							
							//degree
							int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
							
							
							
							//raghu storing database
							if(qualificationTO.getDocTypeId()==doctypeId){
								
								
								
								/*String language="Language";
								Map<Integer,String> admsubjectMap=AdmissionFormHandler.getInstance().get12thclassSubjects(qualificationTO.getDocName(),language);
								admForm.setAdmSubjectMap(admsubjectMap);
								Map<Integer,String> admsubjectLangMap=AdmissionFormHandler.getInstance().get12thclassLangSubjects(qualificationTO.getDocName(),language);
								admForm.setAdmSubjectLangMap(admsubjectLangMap);
								*/
								setDetailSubjectMarkBOClass12(bo,qualificationTO, detailMark,detailMarkto, admForm,qualificationTO.getDocTypeId());
								
								if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && CommonUtil.isValidDecimal((detailMarkto.getTotalMarks()))){
									detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarks()));
									totalmark=detailMark.getTotalMarks().doubleValue();
									}
									if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())){
									detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarks()));
									totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
									}
									
									if(totalmark!=0.0 && totalobtained!=0.0)
									percentage=(totalobtained/totalmark)*100;
									DecimalFormat df=new DecimalFormat("#.##");
									bo.setTotalMarks(new BigDecimal(new Double(df.format(totalmark)).toString()));
									bo.setMarksObtained(new BigDecimal(new Double(df.format(totalobtained)).toString()));
									bo.setPercentage(new BigDecimal(new Double(df.format(percentage)).toString()));
								
											
							}
							
							//degree
							int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
							
							//if entry comes from fresh educatuin details or for edit, we hav to check PatternofStudy
							if((qualificationTO.getDocTypeId()==doctypeId1 && admForm.getPatternofStudy()!=null) ){
								
								bo.setUgPattern(admForm.getPatternofStudy());
								qualificationTO.setUgPattern(admForm.getPatternofStudy());
								/*String Core="Core";
								String Compl="Complementary";
								String Common="Common";
								String Open="Open";
								
								Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Core);
								admForm.setAdmCoreMap(admCoreMap);
								Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Compl);
								admForm.setAdmComplMap(admComplMap);
								Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Common);
								admForm.setAdmCommonMap(admCommonMap);
								Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(qualificationTO.getDocName(),Open);
								admForm.setAdmMainMap(admopenMap);
								Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(qualificationTO.getDocName(),Common);
								admForm.setAdmSubMap(admSubMap);
							*/	
								
								
								
								setDetailSubjectMarkBODegree(bo,qualificationTO, detailMark,detailMarkto, admForm,qualificationTO.getDocTypeId(),admForm.getPatternofStudy());
						
								if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS") || admForm.getPatternofStudy().equalsIgnoreCase("CBCSS NEW")){
									//start grand total max and grand total obtain marks
									if(detailMarkto.getTotalMarksCGPA()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarksCGPA()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarksCGPA())){
									detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarksCGPA()));
									totalmark=detailMark.getTotalMarks().doubleValue();
									}
									if(detailMarkto.getTotalObtainedMarksCGPA()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarksCGPA()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarksCGPA())){
									detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarksCGPA()));
									totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
									}
									
								}
								else{
									//start grand total max and grand total obtain marks
									if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarks())){
									detailMark.setTotalMarks(new BigDecimal(detailMarkto.getTotalMarks()));
									totalmark=detailMark.getTotalMarks().doubleValue();
									}
									if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())){
									detailMark.setTotalObtainedMarks(new BigDecimal(detailMarkto.getTotalObtainedMarks()));
									totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
									}
									
								}
								
								if(totalmark!=0.0 && totalobtained!=0.0)
								percentage=(totalobtained/totalmark)*100;
								DecimalFormat df=new DecimalFormat("#.###");
								bo.setTotalMarks(new BigDecimal(new Double(df.format(totalmark)).toString()));
								bo.setMarksObtained(new BigDecimal(new Double(df.format(totalobtained)).toString()));
								bo.setPercentage(new BigDecimal(new Double(df.format(percentage)).toString()));
							
							}
							
							//for ranklist
	                        //ug programtype id=1 
	                        /*if((qualificationTO.getDocName().equalsIgnoreCase("Class 12")) ){
	                       
	                        }
	                        
	                        
	                        
	                       
	                        if((qualificationTO.getDocName().equalsIgnoreCase("DEG")) ){
	                             
	                        
	                        }  
	                        
	                        */
	                        
	                        
							

							
							//start grand total max and grand total obtain marks
							if(qualificationTO.getDocTypeId()!=doctypeId && qualificationTO.getDocTypeId()!=doctypeId1){
								
								detailMark.setSubject1(detailMarkto.getSubject1());
								detailMark.setSubject2(detailMarkto.getSubject2());
								detailMark.setSubject3(detailMarkto.getSubject3());
								detailMark.setSubject4(detailMarkto.getSubject4());
								detailMark.setSubject5(detailMarkto.getSubject5());
								detailMark.setSubject6(detailMarkto.getSubject6());
								detailMark.setSubject7(detailMarkto.getSubject7());
								detailMark.setSubject8(detailMarkto.getSubject8());
								detailMark.setSubject9(detailMarkto.getSubject9());
								detailMark.setSubject10(detailMarkto.getSubject10());
								detailMark.setSubject11(detailMarkto.getSubject11());
								detailMark.setSubject12(detailMarkto.getSubject12());
								detailMark.setSubject13(detailMarkto.getSubject13());
								detailMark.setSubject14(detailMarkto.getSubject14());
								detailMark.setSubject15(detailMarkto.getSubject15());
								detailMark.setSubject16(detailMarkto.getSubject16());
								detailMark.setSubject17(detailMarkto.getSubject17());
								detailMark.setSubject18(detailMarkto.getSubject18());
								detailMark.setSubject19(detailMarkto.getSubject19());
								detailMark.setSubject20(detailMarkto.getSubject20());
						
								
								if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1TotalMarks()))
									detailMark.setSubject1TotalMarks(new BigDecimal(
											detailMarkto.getSubject1TotalMarks()));
									if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2TotalMarks()))
									detailMark.setSubject2TotalMarks(new BigDecimal(
											detailMarkto.getSubject2TotalMarks()));
									if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3TotalMarks()))
									detailMark.setSubject3TotalMarks(new BigDecimal(
											detailMarkto.getSubject3TotalMarks()));
									if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4TotalMarks()))
									detailMark.setSubject4TotalMarks(new BigDecimal(
											detailMarkto.getSubject4TotalMarks()));
									if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5TotalMarks()))
									detailMark.setSubject5TotalMarks(new BigDecimal(
											detailMarkto.getSubject5TotalMarks()));
									if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6TotalMarks()))
									detailMark.setSubject6TotalMarks(new BigDecimal(
											detailMarkto.getSubject6TotalMarks()));
									if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7TotalMarks()))
									detailMark.setSubject7TotalMarks(new BigDecimal(
											detailMarkto.getSubject7TotalMarks()));
									if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8TotalMarks()))
									detailMark.setSubject8TotalMarks(new BigDecimal(
											detailMarkto.getSubject8TotalMarks()));
									if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9TotalMarks()))
									detailMark.setSubject9TotalMarks(new BigDecimal(
											detailMarkto.getSubject9TotalMarks()));
									if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10TotalMarks()))
									detailMark.setSubject10TotalMarks(new BigDecimal(
											detailMarkto.getSubject10TotalMarks()));
									if(detailMarkto.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11TotalMarks()))
									detailMark.setSubject11TotalMarks(new BigDecimal(
											detailMarkto.getSubject11TotalMarks()));
									if(detailMarkto.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12TotalMarks()))
									detailMark.setSubject12TotalMarks(new BigDecimal(
											detailMarkto.getSubject12TotalMarks()));
									if(detailMarkto.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13TotalMarks()))
									detailMark.setSubject13TotalMarks(new BigDecimal(
											detailMarkto.getSubject13TotalMarks()));
									if(detailMarkto.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14TotalMarks()))
									detailMark.setSubject14TotalMarks(new BigDecimal(
											detailMarkto.getSubject14TotalMarks()));
									if(detailMarkto.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15TotalMarks()))
									detailMark.setSubject15TotalMarks(new BigDecimal(
											detailMarkto.getSubject15TotalMarks()));
									if(detailMarkto.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16TotalMarks()))
									detailMark.setSubject16TotalMarks(new BigDecimal(
											detailMarkto.getSubject16TotalMarks()));
									if(detailMarkto.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17TotalMarks()))
									detailMark.setSubject17TotalMarks(new BigDecimal(
											detailMarkto.getSubject17TotalMarks()));
									if(detailMarkto.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18TotalMarks()))
									detailMark.setSubject18TotalMarks(new BigDecimal(
											detailMarkto.getSubject18TotalMarks()));
									if(detailMarkto.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19TotalMarks()))
									detailMark.setSubject19TotalMarks(new BigDecimal(
											detailMarkto.getSubject19TotalMarks()));
									if(detailMarkto.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20TotalMarks()))
									detailMark.setSubject20TotalMarks(new BigDecimal(
											detailMarkto.getSubject20TotalMarks()));
									
									if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject1ObtainedMarks()))
									detailMark.setSubject1ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject1ObtainedMarks()));
									if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject2ObtainedMarks()))
									detailMark.setSubject2ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject2ObtainedMarks()));
									if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject3ObtainedMarks()))
									detailMark.setSubject3ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject3ObtainedMarks()));
									if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject4ObtainedMarks()))
									detailMark.setSubject4ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject4ObtainedMarks()));
									if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject5ObtainedMarks()))
									detailMark.setSubject5ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject5ObtainedMarks()));
									if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject6ObtainedMarks()))
									detailMark.setSubject6ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject6ObtainedMarks()));
									if(detailMarkto.getSubject7ObtainedMarks()!=null  && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject7ObtainedMarks()))
									detailMark.setSubject7ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject7ObtainedMarks()));
									if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject8ObtainedMarks()))
									detailMark.setSubject8ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject8ObtainedMarks()));
									if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject9ObtainedMarks()))
									detailMark.setSubject9ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject9ObtainedMarks()));
									if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject10ObtainedMarks()))
									detailMark.setSubject10ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject10ObtainedMarks()));
									if(detailMarkto.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject11ObtainedMarks()))
									detailMark.setSubject11ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject11ObtainedMarks()));
									if(detailMarkto.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject12ObtainedMarks()))
									detailMark.setSubject12ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject12ObtainedMarks()));
									if(detailMarkto.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject13ObtainedMarks()))
									detailMark.setSubject13ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject13ObtainedMarks()));
									if(detailMarkto.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject14ObtainedMarks()))
									detailMark.setSubject14ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject14ObtainedMarks()));
									if(detailMarkto.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject15ObtainedMarks()))
									detailMark.setSubject15ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject15ObtainedMarks()));
									if(detailMarkto.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject16ObtainedMarks()))
									detailMark.setSubject16ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject16ObtainedMarks()));
									if(detailMarkto.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject17ObtainedMarks()))
									detailMark.setSubject17ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject17ObtainedMarks()));
									if(detailMarkto.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject18ObtainedMarks()))
									detailMark.setSubject18ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject18ObtainedMarks()));
									if(detailMarkto.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject19ObtainedMarks()))
									detailMark.setSubject19ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject19ObtainedMarks()));
									if(detailMarkto.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getSubject20ObtainedMarks()))
									detailMark.setSubject20ObtainedMarks(new BigDecimal(
											detailMarkto.getSubject20ObtainedMarks()));
									//set total max and total obtain marks over
									
								
							if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && StringUtils.isNumeric(detailMarkto.getTotalMarks())){
							detailMark.setTotalMarks(new BigDecimal(detailMarkto
									.getTotalMarks()));
							totalmark=detailMark.getTotalMarks().doubleValue();
							}
							if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && StringUtils.isNumeric(detailMarkto.getTotalObtainedMarks())){
							detailMark.setTotalObtainedMarks(new BigDecimal(
									detailMarkto.getTotalObtainedMarks()));
							totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
							}
							
							if(totalmark!=0.0 && totalobtained!=0.0)
							percentage=(totalobtained/totalmark)*100;
							bo.setTotalMarks(new BigDecimal(totalmark));
							bo.setMarksObtained(new BigDecimal(totalobtained));
							bo.setPercentage(new BigDecimal(percentage));
							
							}
							/*if(isPresidance){
								if(percentage!=0.0){
									if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
										bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
									}
								}
							}*/
							detailMarks.add(detailMark);
						}
						bo.setCandidateMarkses(detailMarks);
					}//detail mark of class12 and degree over
					
					
					//semister wise marks
					if(!qualificationTO.isConsolidated() && qualificationTO.isSemesterWise()){
						
						Set<ApplicantMarkDetailsTO> semesterlist=qualificationTO.getSemesterList();
							if (semesterlist!=null && !semesterlist.isEmpty()) {
								Set<ApplicantMarksDetails> semesterDetails= new HashSet<ApplicantMarksDetails>();
								Iterator<ApplicantMarkDetailsTO> semitr=semesterlist.iterator();
								double overallMark=0.0;
								double overallObtain=0.0;
								double overallPerc=0.0;
								double overallanguagewiseMark=0.0;
								double overallanguagewiseObtain=0.0;
								double overallanguagewisePerc=0.0;
								
								while (semitr.hasNext()) {
									ApplicantMarkDetailsTO detailMarkto = (ApplicantMarkDetailsTO) semitr.next();
									ApplicantMarksDetails semesterMark= new ApplicantMarksDetails();
									semesterMark.setId(detailMarkto.getId());
									semesterMark.setSemesterName(detailMarkto.getSemesterName());
									semesterMark.setSemesterNo(detailMarkto.getSemesterNo());
									semesterMark.setIsLastExam(detailMarkto.isLastExam());
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
									
									semesterMark.setMaxMarks(totalMark);
									semesterMark.setMarksObtained(obtainMark);
									semesterMark.setMaxMarksLanguagewise(totallanguagewiseMark);
									semesterMark.setMarksObtainedLanguagewise(obtainlanguagewiseMark);
									int percentage=0;
									int percentageLanguagewise = 0;
									
									if (obtainMark!=0 && totalMark!=0) {
										percentage = (obtainMark * 100) / totalMark;
									}
									if (obtainlanguagewiseMark!=0 && totallanguagewiseMark!=0) {
										percentageLanguagewise = (obtainlanguagewiseMark * 100) / totallanguagewiseMark;
									}
									overallMark=overallMark+totalMark;
									overallObtain=overallObtain+obtainMark;
									overallPerc=overallPerc+percentage;
									
									overallanguagewiseMark = overallanguagewiseMark + totallanguagewiseMark;
									overallanguagewiseObtain = overallanguagewiseObtain + obtainlanguagewiseMark;
									overallanguagewisePerc = overallanguagewisePerc + percentageLanguagewise;

									semesterMark.setPercentage(new BigDecimal(percentage));
									/*if(isPresidance){
										if(percentage!=0.0){
											if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
												bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
											}
										}
									}*/
									semesterMark.setPercentageLanguagewise(new BigDecimal(percentageLanguagewise));
									semesterDetails.add(semesterMark);
								}
								if(overallanguagewiseMark>0 && overallanguagewiseObtain >0){
									bo.setTotalMarks(new BigDecimal(overallanguagewiseMark));
									bo.setMarksObtained(new BigDecimal(overallanguagewiseObtain));
									double calPerc=0.0;
									if(overallanguagewiseMark!=0.0 && overallanguagewiseObtain!=0.0)
									calPerc=(overallanguagewiseObtain/overallanguagewiseMark)*100;
									bo.setPercentage(new BigDecimal(calPerc));
								}else{
								bo.setTotalMarks(new BigDecimal(overallMark));
								bo.setMarksObtained(new BigDecimal(overallObtain));
								double calPerc=0.0;
								if(overallMark!=0.0 && overallObtain!=0.0)
								calPerc=(overallObtain/overallMark)*100;
								bo.setPercentage(new BigDecimal(calPerc));
								}
								bo.setApplicantMarksDetailses(semesterDetails)	;
							}
						
					}//semister wise marks over
						
						qualificationSet.add(bo);
				}//final education iterator over
				
				if(appBO.getPersonalData()!=null)
					appBO.getPersonalData().setEdnQualifications(qualificationSet);
			
			}//education null check over
			
			log.info("exit setEdnqualificationBO" );
		}
		
		
		//raghu storing database
		
		private void setDetailSubjectMarkBOClass12(EdnQualification ednQualification,EdnQualificationTO ednQualificationTO,CandidateMarks candidateMarks,CandidateMarkTO candidateMarkTO, OnlineApplicationForm admForm,int docName) throws Exception {
			
			
			Map<Integer,String> admlangmap=admForm.getAdmSubjectLangMap();
			Map<Integer,String> admsubmap=admForm.getAdmSubjectMap();
			Set<AdmSubjectMarkForRank> submarksSet=new HashSet<AdmSubjectMarkForRank>();
			int submarkCount=0;
			
			
			
			//settting subject1
			if(candidateMarkTO.getSubjectid1()!=null && !candidateMarkTO.getSubjectid1().equalsIgnoreCase("")){
				
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid1(),candidateMarkTO.getSubjectmarkid1(),candidateMarkTO.getSubjectOther1(),candidateMarkTO.getSubject1TotalMarks(),candidateMarkTO.getSubject1ObtainedMarks(),candidateMarkTO.getSubject1Credit(), admForm);
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					candidateMarks.setSubjectid1(candidateMarkTO.getSubjectid1());

					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}
					
					if(candidateMarkTO.getSubjectOther1()!=null && !candidateMarkTO.getSubjectOther1().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther1(candidateMarkTO.getSubjectOther1());
					}
					
					if(candidateMarkTO.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1TotalMarks()))
						candidateMarks.setSubject1TotalMarks(new BigDecimal(candidateMarkTO.getSubject1TotalMarks()));
					if(candidateMarkTO.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1ObtainedMarks()))
						candidateMarks.setSubject1ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject1ObtainedMarks()));
						
				}else{
					submarkCount++;
				}
					
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid1("");
			
			}
				
			//settting subject2
			if(candidateMarkTO.getSubjectid2()!=null && !candidateMarkTO.getSubjectid2().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid2(),candidateMarkTO.getSubjectmarkid2(),candidateMarkTO.getSubjectOther2(),candidateMarkTO.getSubject2TotalMarks(),candidateMarkTO.getSubject2ObtainedMarks(),candidateMarkTO.getSubject2Credit(), admForm);
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					candidateMarks.setSubjectid2(candidateMarkTO.getSubjectid2());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}
					
					if(candidateMarkTO.getSubjectOther2()!=null && !candidateMarkTO.getSubjectOther2().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther2(candidateMarkTO.getSubjectOther2());
					}
					
					if(candidateMarkTO.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2TotalMarks()))
						candidateMarks.setSubject2TotalMarks(new BigDecimal(candidateMarkTO.getSubject2TotalMarks()));
					if(candidateMarkTO.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2ObtainedMarks()))
						candidateMarks.setSubject2ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject2ObtainedMarks()));
				
					
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid2("");
			
			}
			
			//settting subject3
			if(candidateMarkTO.getSubjectid3()!=null && !candidateMarkTO.getSubjectid3().equalsIgnoreCase("")){
				
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid3(),candidateMarkTO.getSubjectmarkid3(),candidateMarkTO.getSubjectOther3(),candidateMarkTO.getSubject3TotalMarks(),candidateMarkTO.getSubject3ObtainedMarks(),candidateMarkTO.getSubject3Credit(), admForm);
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					candidateMarks.setSubjectid3(candidateMarkTO.getSubjectid3());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}
					
					if(candidateMarkTO.getSubjectOther3()!=null && !candidateMarkTO.getSubjectOther3().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther3(candidateMarkTO.getSubjectOther3());
					}
					
					if(candidateMarkTO.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3TotalMarks()))
						candidateMarks.setSubject3TotalMarks(new BigDecimal(candidateMarkTO.getSubject3TotalMarks()));
					if(candidateMarkTO.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3ObtainedMarks()))
						candidateMarks.setSubject3ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject3ObtainedMarks()));
				
					
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid3("");
			
			}
			
			//settting subject4
			if(candidateMarkTO.getSubjectid4()!=null && !candidateMarkTO.getSubjectid4().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid4(),candidateMarkTO.getSubjectmarkid4(),candidateMarkTO.getSubjectOther4(),candidateMarkTO.getSubject4TotalMarks(),candidateMarkTO.getSubject4ObtainedMarks(),candidateMarkTO.getSubject4Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					candidateMarks.setSubjectid4(candidateMarkTO.getSubjectid4());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}
					
					if(candidateMarkTO.getSubjectOther4()!=null && !candidateMarkTO.getSubjectOther4().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther4(candidateMarkTO.getSubjectOther4());
					}
					
					if(candidateMarkTO.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4TotalMarks()))
						candidateMarks.setSubject4TotalMarks(new BigDecimal(candidateMarkTO.getSubject4TotalMarks()));
					if(candidateMarkTO.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4ObtainedMarks()))
						candidateMarks.setSubject4ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject4ObtainedMarks()));
				
					
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid4("");
			
			}
			
			//settting subject5
			if(candidateMarkTO.getSubjectid5()!=null && !candidateMarkTO.getSubjectid5().equalsIgnoreCase("")){
				
					AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid5(),candidateMarkTO.getSubjectmarkid5(),candidateMarkTO.getSubjectOther5(),candidateMarkTO.getSubject5TotalMarks(),candidateMarkTO.getSubject5ObtainedMarks(),candidateMarkTO.getSubject5Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid5(candidateMarkTO.getSubjectid5());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}
					
					if(candidateMarkTO.getSubjectOther5()!=null && !candidateMarkTO.getSubjectOther5().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther5(candidateMarkTO.getSubjectOther5());
					}
					
					if(candidateMarkTO.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5TotalMarks()))
						candidateMarks.setSubject5TotalMarks(new BigDecimal(candidateMarkTO.getSubject5TotalMarks()));
					if(candidateMarkTO.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5ObtainedMarks()))
						candidateMarks.setSubject5ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject5ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid5("");
			
			}
			
			//settting subject6
			if(candidateMarkTO.getSubjectid6()!=null && !candidateMarkTO.getSubjectid6().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid6(),candidateMarkTO.getSubjectmarkid6(),candidateMarkTO.getSubjectOther6(),candidateMarkTO.getSubject6TotalMarks(),candidateMarkTO.getSubject6ObtainedMarks(),candidateMarkTO.getSubject6Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid6(candidateMarkTO.getSubjectid6());
					
                  if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}
					
					if(candidateMarkTO.getSubjectOther6()!=null && !candidateMarkTO.getSubjectOther6().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther6(candidateMarkTO.getSubjectOther6());
					}
					
					if(candidateMarkTO.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6TotalMarks()))
						candidateMarks.setSubject6TotalMarks(new BigDecimal(candidateMarkTO.getSubject6TotalMarks()));
					if(candidateMarkTO.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6ObtainedMarks()))
						candidateMarks.setSubject6ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject6ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid6("");
					
					
			}
			
			//settting subject7
			if(candidateMarkTO.getSubjectid7()!=null && !candidateMarkTO.getSubjectid7().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid7(),candidateMarkTO.getSubjectmarkid7(),candidateMarkTO.getSubjectOther7(),candidateMarkTO.getSubject7TotalMarks(),candidateMarkTO.getSubject7ObtainedMarks(),candidateMarkTO.getSubject7Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid7(candidateMarkTO.getSubjectid7());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}
					
					if(candidateMarkTO.getSubjectOther7()!=null && !candidateMarkTO.getSubjectOther7().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther7(candidateMarkTO.getSubjectOther7());
					}
					
					if(candidateMarkTO.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7TotalMarks()))
						candidateMarks.setSubject7TotalMarks(new BigDecimal(candidateMarkTO.getSubject7TotalMarks()));
					if(candidateMarkTO.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7ObtainedMarks()))
						candidateMarks.setSubject7ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject7ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
					submarkCount++;
					candidateMarkTO.setSubjectid7("");
			
			}
			
			//settting subject8
			if(candidateMarkTO.getSubjectid8()!=null && !candidateMarkTO.getSubjectid8().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid8(),candidateMarkTO.getSubjectmarkid8(),candidateMarkTO.getSubjectOther8(),candidateMarkTO.getSubject8TotalMarks(),candidateMarkTO.getSubject8ObtainedMarks(),candidateMarkTO.getSubject8Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid8(candidateMarkTO.getSubjectid8());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}
					
					if(candidateMarkTO.getSubjectOther8()!=null && !candidateMarkTO.getSubjectOther8().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther8(candidateMarkTO.getSubjectOther8());
					}
					
					if(candidateMarkTO.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8TotalMarks()))
						candidateMarks.setSubject8TotalMarks(new BigDecimal(candidateMarkTO.getSubject8TotalMarks()));
					if(candidateMarkTO.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8ObtainedMarks()))
						candidateMarks.setSubject8ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject8ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid8("");
			
			}
			
			//settting subject9
			if(candidateMarkTO.getSubjectid9()!=null && !candidateMarkTO.getSubjectid9().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid9(),candidateMarkTO.getSubjectmarkid9(),candidateMarkTO.getSubjectOther9(),candidateMarkTO.getSubject9TotalMarks(),candidateMarkTO.getSubject9ObtainedMarks(),candidateMarkTO.getSubject9Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid9(candidateMarkTO.getSubjectid9());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}
					
					if(candidateMarkTO.getSubjectOther9()!=null && !candidateMarkTO.getSubjectOther9().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther9(candidateMarkTO.getSubjectOther9());
					}
				
					if(candidateMarkTO.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9TotalMarks()))
						candidateMarks.setSubject9TotalMarks(new BigDecimal(candidateMarkTO.getSubject9TotalMarks()));
					if(candidateMarkTO.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9ObtainedMarks()))
						candidateMarks.setSubject9ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject9ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid9("");
			
			}
			
			//settting subject10
			if(candidateMarkTO.getSubjectid10()!=null && !candidateMarkTO.getSubjectid10().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid10(),candidateMarkTO.getSubjectmarkid10(),candidateMarkTO.getSubjectOther10(),candidateMarkTO.getSubject10TotalMarks(),candidateMarkTO.getSubject10ObtainedMarks(),candidateMarkTO.getSubject10Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid10(candidateMarkTO.getSubjectid10());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}
					
					if(candidateMarkTO.getSubjectOther10()!=null && !candidateMarkTO.getSubjectOther10().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther10(candidateMarkTO.getSubjectOther10());
					}
				
					if(candidateMarkTO.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10TotalMarks()))
						candidateMarks.setSubject10TotalMarks(new BigDecimal(candidateMarkTO.getSubject10TotalMarks()));
					if(candidateMarkTO.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10ObtainedMarks()))
						candidateMarks.setSubject10ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject10ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid10("");
			
			}
			
			//settting subject11
			if(candidateMarkTO.getSubjectid11()!=null && !candidateMarkTO.getSubjectid11().equalsIgnoreCase("")){
				
					AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid11(),candidateMarkTO.getSubjectmarkid11(),candidateMarkTO.getSubjectOther11(),candidateMarkTO.getSubject11TotalMarks(),candidateMarkTO.getSubject11ObtainedMarks(),candidateMarkTO.getSubject11Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid11(candidateMarkTO.getSubjectid11());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}
					
					if(candidateMarkTO.getSubjectOther11()!=null && !candidateMarkTO.getSubjectOther11().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther11(candidateMarkTO.getSubjectOther11());
					}
					
					if(candidateMarkTO.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject11TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11TotalMarks()))
						candidateMarks.setSubject11TotalMarks(new BigDecimal(candidateMarkTO.getSubject11TotalMarks()));
					if(candidateMarkTO.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject11ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11ObtainedMarks()))
						candidateMarks.setSubject11ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject11ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid11("");
			
			}
			
			//settting subject12
			if(candidateMarkTO.getSubjectid12()!=null && !candidateMarkTO.getSubjectid12().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid12(),candidateMarkTO.getSubjectmarkid12(),candidateMarkTO.getSubjectOther12(),candidateMarkTO.getSubject12TotalMarks(),candidateMarkTO.getSubject12ObtainedMarks(),candidateMarkTO.getSubject12Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid12(candidateMarkTO.getSubjectid12());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}
					
					if(candidateMarkTO.getSubjectOther12()!=null && !candidateMarkTO.getSubjectOther12().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther12(candidateMarkTO.getSubjectOther12());
					}
					
					if(candidateMarkTO.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject12TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12TotalMarks()))
						candidateMarks.setSubject12TotalMarks(new BigDecimal(candidateMarkTO.getSubject12TotalMarks()));
					if(candidateMarkTO.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject12ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12ObtainedMarks()))
						candidateMarks.setSubject12ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject12ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid12("");
			
			}
			
			//settting subject13
			if(candidateMarkTO.getSubjectid13()!=null && !candidateMarkTO.getSubjectid13().equalsIgnoreCase("")){
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid13(),candidateMarkTO.getSubjectmarkid13(),candidateMarkTO.getSubjectOther13(),candidateMarkTO.getSubject13TotalMarks(),candidateMarkTO.getSubject13ObtainedMarks(),candidateMarkTO.getSubject13Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid13(candidateMarkTO.getSubjectid13());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}
					
					if(candidateMarkTO.getSubjectOther13()!=null && !candidateMarkTO.getSubjectOther13().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther13(candidateMarkTO.getSubjectOther13());
					}
					
					if(candidateMarkTO.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject13TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13TotalMarks()))
						candidateMarks.setSubject13TotalMarks(new BigDecimal(candidateMarkTO.getSubject13TotalMarks()));
					if(candidateMarkTO.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject13ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13ObtainedMarks()))
						candidateMarks.setSubject13ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject13ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid13("");
			
			}
			
			//settting subject14
			if(candidateMarkTO.getSubjectid14()!=null && !candidateMarkTO.getSubjectid14().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid14(),candidateMarkTO.getSubjectmarkid14(),candidateMarkTO.getSubjectOther14(),candidateMarkTO.getSubject14TotalMarks(),candidateMarkTO.getSubject14ObtainedMarks(),candidateMarkTO.getSubject14Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid14(candidateMarkTO.getSubjectid14());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}
					
					if(candidateMarkTO.getSubjectOther14()!=null && !candidateMarkTO.getSubjectOther14().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther14(candidateMarkTO.getSubjectOther14());
					}
				
					if(candidateMarkTO.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject14TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14TotalMarks()))
						candidateMarks.setSubject14TotalMarks(new BigDecimal(candidateMarkTO.getSubject14TotalMarks()));
					if(candidateMarkTO.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject14ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14ObtainedMarks()))
						candidateMarks.setSubject14ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject14ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid14("");
			
			}
			
			//settting subject15
			if(candidateMarkTO.getSubjectid15()!=null && !candidateMarkTO.getSubjectid15().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid15(),candidateMarkTO.getSubjectmarkid15(),candidateMarkTO.getSubjectOther15(),candidateMarkTO.getSubject15TotalMarks(),candidateMarkTO.getSubject15ObtainedMarks(),candidateMarkTO.getSubject15Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid15(candidateMarkTO.getSubjectid15());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}
					
					if(candidateMarkTO.getSubjectOther15()!=null && !candidateMarkTO.getSubjectOther15().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther15(candidateMarkTO.getSubjectOther15());
					}
				
					if(candidateMarkTO.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject15TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15TotalMarks()))
						candidateMarks.setSubject15TotalMarks(new BigDecimal(candidateMarkTO.getSubject15TotalMarks()));
					if(candidateMarkTO.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject15ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15ObtainedMarks()))
						candidateMarks.setSubject15ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject15ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid15("");
			
			}
			
			//settting subject16
			if(candidateMarkTO.getSubjectid16()!=null && !candidateMarkTO.getSubjectid16().equalsIgnoreCase("")){
				
						
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid16(),candidateMarkTO.getSubjectmarkid16(),candidateMarkTO.getSubjectOther16(),candidateMarkTO.getSubject16TotalMarks(),candidateMarkTO.getSubject16ObtainedMarks(),candidateMarkTO.getSubject16Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid16(candidateMarkTO.getSubjectid16());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}
					
					if(candidateMarkTO.getSubjectOther16()!=null && !candidateMarkTO.getSubjectOther16().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther16(candidateMarkTO.getSubjectOther16());
					}
			
					if(candidateMarkTO.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject16TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16TotalMarks()))
						candidateMarks.setSubject16TotalMarks(new BigDecimal(candidateMarkTO.getSubject16TotalMarks()));
					if(candidateMarkTO.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject16ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16ObtainedMarks()))
						candidateMarks.setSubject16ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject16ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid16("");
			
			}
			
			//settting subject17
			if(candidateMarkTO.getSubjectid17()!=null && !candidateMarkTO.getSubjectid17().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid17(),candidateMarkTO.getSubjectmarkid17(),candidateMarkTO.getSubjectOther17(),candidateMarkTO.getSubject17TotalMarks(),candidateMarkTO.getSubject17ObtainedMarks(),candidateMarkTO.getSubject17Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid17(candidateMarkTO.getSubjectid17());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}
					
					if(candidateMarkTO.getSubjectOther17()!=null && !candidateMarkTO.getSubjectOther17().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther17(candidateMarkTO.getSubjectOther17());
					}
					
					if(candidateMarkTO.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject17TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17TotalMarks()))
						candidateMarks.setSubject17TotalMarks(new BigDecimal(candidateMarkTO.getSubject17TotalMarks()));
					if(candidateMarkTO.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject17ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17ObtainedMarks()))
						candidateMarks.setSubject17ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject17ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid17("");
			
			}
			
			//settting subject18
			if(candidateMarkTO.getSubjectid18()!=null && !candidateMarkTO.getSubjectid18().equalsIgnoreCase("")){
				
						
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid18(),candidateMarkTO.getSubjectmarkid18(),candidateMarkTO.getSubjectOther18(),candidateMarkTO.getSubject18TotalMarks(),candidateMarkTO.getSubject18ObtainedMarks(),candidateMarkTO.getSubject18Credit(), admForm);

				candidateMarks.setSubjectid18(candidateMarkTO.getSubjectid18());
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}
					
					if(candidateMarkTO.getSubjectOther18()!=null && !candidateMarkTO.getSubjectOther18().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther18(candidateMarkTO.getSubjectOther18());
					}
					
					if(candidateMarkTO.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject18TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18TotalMarks()))
						candidateMarks.setSubject18TotalMarks(new BigDecimal(candidateMarkTO.getSubject18TotalMarks()));
					if(candidateMarkTO.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject18ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18ObtainedMarks()))
						candidateMarks.setSubject18ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject18ObtainedMarks()));
				
			
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid18("");
			
			}
			
			//settting subject19
			if(candidateMarkTO.getSubjectid19()!=null && !candidateMarkTO.getSubjectid19().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid19(),candidateMarkTO.getSubjectmarkid19(),candidateMarkTO.getSubjectOther19(),candidateMarkTO.getSubject19TotalMarks(),candidateMarkTO.getSubject19ObtainedMarks(),candidateMarkTO.getSubject19Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid19(candidateMarkTO.getSubjectid19());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}
					
					if(candidateMarkTO.getSubjectOther19()!=null && !candidateMarkTO.getSubjectOther19().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther19(candidateMarkTO.getSubjectOther19());
					}
				
					if(candidateMarkTO.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject19TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19TotalMarks()))
						candidateMarks.setSubject19TotalMarks(new BigDecimal(candidateMarkTO.getSubject19TotalMarks()));
					if(candidateMarkTO.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject19ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19ObtainedMarks()))
						candidateMarks.setSubject19ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject19ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid19("");
			
			}
			
			//settting subject20
			if(candidateMarkTO.getSubjectid20()!=null && !candidateMarkTO.getSubjectid20().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid20(),candidateMarkTO.getSubjectmarkid20(),candidateMarkTO.getSubjectOther20(),candidateMarkTO.getSubject20TotalMarks(),candidateMarkTO.getSubject20ObtainedMarks(),candidateMarkTO.getSubject20Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid20(candidateMarkTO.getSubjectid20());
					
					if(admlangmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admlangmap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}else if(admsubmap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admsubmap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}
					
					if(candidateMarkTO.getSubjectOther20()!=null && !candidateMarkTO.getSubjectOther20().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther20(candidateMarkTO.getSubjectOther20());
					}
				
					if(candidateMarkTO.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject20TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20TotalMarks()))
						candidateMarks.setSubject20TotalMarks(new BigDecimal(candidateMarkTO.getSubject20TotalMarks()));
					if(candidateMarkTO.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject20ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20ObtainedMarks()))
						candidateMarks.setSubject20ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject20ObtainedMarks()));
				
				}else{
					submarkCount++;
				}	
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid20("");
			
			}
			
			if(submarkCount!=20)
			ednQualification.setAdmSubjectMarkForRank(submarksSet);
            
			
             
		}
		
		
//raghu storing database
		
		private void setDetailSubjectMarkBODegree(EdnQualification ednQualification,EdnQualificationTO ednQualificationTO,CandidateMarks candidateMarks,CandidateMarkTO candidateMarkTO, OnlineApplicationForm admForm,int docName,String patternofStudy) throws Exception {
			
		
			Map<Integer,String> admCoreMap=admForm.getAdmCoreMap();
			Map<Integer,String> admComplMap=admForm.getAdmComplMap();
			Map<Integer,String> admCommonMap=admForm.getAdmCommonMap();
			Map<Integer,String> admopenMap=admForm.getAdmMainMap();
			Map<Integer,String> admSubMap=admForm.getAdmSubMap();
			Set<AdmSubjectMarkForRank> submarksSet=new HashSet<AdmSubjectMarkForRank>();
			int submarkCount=0;
			
			if(patternofStudy.equalsIgnoreCase("CBCSS") || patternofStudy.equalsIgnoreCase("CBCSS NEW")){
				
			
			//settting subject1
			if(candidateMarkTO.getSubjectid1()!=null && !candidateMarkTO.getSubjectid1().equalsIgnoreCase("")){
				
			
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid1(),candidateMarkTO.getSubjectmarkid1(),candidateMarkTO.getSubjectOther1(),candidateMarkTO.getSubject1TotalMarks(),candidateMarkTO.getSubject1ObtainedMarks(),candidateMarkTO.getSubject1Credit(), admForm);
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid1(candidateMarkTO.getSubjectid1());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid1()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid1()));
						candidateMarks.setSubject1(s);
					}
					
					if(candidateMarkTO.getSubjectOther1()!=null && !candidateMarkTO.getSubjectOther1().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther1(candidateMarkTO.getSubjectOther1());
					}
					
					//set credit
					if(candidateMarkTO.getSubject1Credit()!=null && !candidateMarkTO.getSubject1Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject1Credit(new BigDecimal(candidateMarkTO.getSubject1Credit()));
					}
					
					if(candidateMarkTO.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1TotalMarks()))
						candidateMarks.setSubject1TotalMarks(new BigDecimal(candidateMarkTO.getSubject1TotalMarks()));
					if(candidateMarkTO.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject1ObtainedMarks()))
						candidateMarks.setSubject1ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject1ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid1("");
			
			}
				
			//settting subject2
			if(candidateMarkTO.getSubjectid2()!=null && !candidateMarkTO.getSubjectid2().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid2(),candidateMarkTO.getSubjectmarkid2(),candidateMarkTO.getSubjectOther2(),candidateMarkTO.getSubject2TotalMarks(),candidateMarkTO.getSubject2ObtainedMarks(),candidateMarkTO.getSubject2Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid2(candidateMarkTO.getSubjectid2());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid2()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid2()));
						candidateMarks.setSubject2(s);
					}
					
					if(candidateMarkTO.getSubjectOther2()!=null && !candidateMarkTO.getSubjectOther2().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther2(candidateMarkTO.getSubjectOther2());
					}
				
					//set credit
					if(candidateMarkTO.getSubject2Credit()!=null && !candidateMarkTO.getSubject2Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject2Credit(new BigDecimal(candidateMarkTO.getSubject2Credit()));
					}
					
					if(candidateMarkTO.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2TotalMarks()))
						candidateMarks.setSubject2TotalMarks(new BigDecimal(candidateMarkTO.getSubject2TotalMarks()));
					if(candidateMarkTO.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject2ObtainedMarks()))
						candidateMarks.setSubject2ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject2ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid2("");
			
			}
			
			//settting subject3
			if(candidateMarkTO.getSubjectid3()!=null && !candidateMarkTO.getSubjectid3().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid3(),candidateMarkTO.getSubjectmarkid3(),candidateMarkTO.getSubjectOther3(),candidateMarkTO.getSubject3TotalMarks(),candidateMarkTO.getSubject3ObtainedMarks(),candidateMarkTO.getSubject3Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid3(candidateMarkTO.getSubjectid3());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid3()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid3()));
						candidateMarks.setSubject3(s);
					}
					
					if(candidateMarkTO.getSubjectOther3()!=null && !candidateMarkTO.getSubjectOther3().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther3(candidateMarkTO.getSubjectOther3());
					}
				
					//set credit
					if(candidateMarkTO.getSubject3Credit()!=null && !candidateMarkTO.getSubject3Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject3Credit(new BigDecimal(candidateMarkTO.getSubject3Credit()));
					}
					
					if(candidateMarkTO.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3TotalMarks()))
						candidateMarks.setSubject3TotalMarks(new BigDecimal(candidateMarkTO.getSubject3TotalMarks()));
					if(candidateMarkTO.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject3ObtainedMarks()))
						candidateMarks.setSubject3ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject3ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid3("");
			
			}
			
			//settting subject4
			if(candidateMarkTO.getSubjectid4()!=null && !candidateMarkTO.getSubjectid4().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid4(),candidateMarkTO.getSubjectmarkid4(),candidateMarkTO.getSubjectOther4(),candidateMarkTO.getSubject4TotalMarks(),candidateMarkTO.getSubject4ObtainedMarks(),candidateMarkTO.getSubject4Credit(), admForm);
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid4(candidateMarkTO.getSubjectid4());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid4()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid4()));
						candidateMarks.setSubject4(s);
					}
					
					if(candidateMarkTO.getSubjectOther4()!=null && !candidateMarkTO.getSubjectOther4().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther4(candidateMarkTO.getSubjectOther4());
					}
				
					//set credit
					if(candidateMarkTO.getSubject4Credit()!=null && !candidateMarkTO.getSubject4Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject4Credit(new BigDecimal(candidateMarkTO.getSubject4Credit()));
					}
					
					if(candidateMarkTO.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4TotalMarks()))
						candidateMarks.setSubject4TotalMarks(new BigDecimal(candidateMarkTO.getSubject4TotalMarks()));
					if(candidateMarkTO.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject4ObtainedMarks()))
						candidateMarks.setSubject4ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject4ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid4("");
			
			}
			
			//settting subject5
			if(candidateMarkTO.getSubjectid5()!=null && !candidateMarkTO.getSubjectid5().equalsIgnoreCase("")){
				
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid5(),candidateMarkTO.getSubjectmarkid5(),candidateMarkTO.getSubjectOther5(),candidateMarkTO.getSubject5TotalMarks(),candidateMarkTO.getSubject5ObtainedMarks(),candidateMarkTO.getSubject5Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid5(candidateMarkTO.getSubjectid5());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid5()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid5()));
						candidateMarks.setSubject5(s);
					}
					
					if(candidateMarkTO.getSubjectOther5()!=null && !candidateMarkTO.getSubjectOther5().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther5(candidateMarkTO.getSubjectOther5());
					}
					//set credit
					if(candidateMarkTO.getSubject5Credit()!=null && !candidateMarkTO.getSubject5Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject5Credit(new BigDecimal(candidateMarkTO.getSubject5Credit()));
					}
					
					if(candidateMarkTO.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5TotalMarks()))
						candidateMarks.setSubject5TotalMarks(new BigDecimal(candidateMarkTO.getSubject5TotalMarks()));
					if(candidateMarkTO.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject5ObtainedMarks()))
						candidateMarks.setSubject5ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject5ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid5("");
			
			}
			
			//settting subject6
			if(candidateMarkTO.getSubjectid6()!=null && !candidateMarkTO.getSubjectid6().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid6(),candidateMarkTO.getSubjectmarkid6(),candidateMarkTO.getSubjectOther6(),candidateMarkTO.getSubject6TotalMarks(),candidateMarkTO.getSubject6ObtainedMarks(),candidateMarkTO.getSubject6Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid6(candidateMarkTO.getSubjectid6());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid6()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid6()));
						candidateMarks.setSubject6(s);
					}
					
					if(candidateMarkTO.getSubjectOther6()!=null && !candidateMarkTO.getSubjectOther6().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther6(candidateMarkTO.getSubjectOther6());
					}
				
					//set credit
					if(candidateMarkTO.getSubject6Credit()!=null && !candidateMarkTO.getSubject6Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject6Credit(new BigDecimal(candidateMarkTO.getSubject6Credit()));
					}
					
					if(candidateMarkTO.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6TotalMarks()))
						candidateMarks.setSubject6TotalMarks(new BigDecimal(candidateMarkTO.getSubject6TotalMarks()));
					if(candidateMarkTO.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject6ObtainedMarks()))
						candidateMarks.setSubject6ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject6ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid6("");
			
			}
			
			//settting subject7
			if(candidateMarkTO.getSubjectid7()!=null && !candidateMarkTO.getSubjectid7().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid7(),candidateMarkTO.getSubjectmarkid7(),candidateMarkTO.getSubjectOther7(),candidateMarkTO.getSubject7TotalMarks(),candidateMarkTO.getSubject7ObtainedMarks(),candidateMarkTO.getSubject7Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid7(candidateMarkTO.getSubjectid7());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid7()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid7()));
						candidateMarks.setSubject7(s);
					}
					
					if(candidateMarkTO.getSubjectOther7()!=null && !candidateMarkTO.getSubjectOther7().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther7(candidateMarkTO.getSubjectOther7());
					}
					
					//set credit
					if(candidateMarkTO.getSubject7Credit()!=null && !candidateMarkTO.getSubject7Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject7Credit(new BigDecimal(candidateMarkTO.getSubject7Credit()));
					}
					
					if(candidateMarkTO.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7TotalMarks()))
						candidateMarks.setSubject7TotalMarks(new BigDecimal(candidateMarkTO.getSubject7TotalMarks()));
					if(candidateMarkTO.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject7ObtainedMarks()))
						candidateMarks.setSubject7ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject7ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid7("");
			
			}
			
			//settting subject8
			if(candidateMarkTO.getSubjectid8()!=null && !candidateMarkTO.getSubjectid8().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid8(),candidateMarkTO.getSubjectmarkid8(),candidateMarkTO.getSubjectOther8(),candidateMarkTO.getSubject8TotalMarks(),candidateMarkTO.getSubject8ObtainedMarks(),candidateMarkTO.getSubject8Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid8(candidateMarkTO.getSubjectid8());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid8()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid8()));
						candidateMarks.setSubject8(s);
					}
					
					if(candidateMarkTO.getSubjectOther8()!=null && !candidateMarkTO.getSubjectOther8().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther8(candidateMarkTO.getSubjectOther8());
					}
					
					//set credit
					if(candidateMarkTO.getSubject8Credit()!=null && !candidateMarkTO.getSubject8Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject8Credit(new BigDecimal(candidateMarkTO.getSubject8Credit()));
					}
					
					if(candidateMarkTO.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8TotalMarks()))
						candidateMarks.setSubject8TotalMarks(new BigDecimal(candidateMarkTO.getSubject8TotalMarks()));
					if(candidateMarkTO.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject8ObtainedMarks()))
						candidateMarks.setSubject8ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject8ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid8("");
			
			}
			
			//settting subject9
			if(candidateMarkTO.getSubjectid9()!=null && !candidateMarkTO.getSubjectid9().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid9(),candidateMarkTO.getSubjectmarkid9(),candidateMarkTO.getSubjectOther9(),candidateMarkTO.getSubject9TotalMarks(),candidateMarkTO.getSubject9ObtainedMarks(),candidateMarkTO.getSubject9Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid9(candidateMarkTO.getSubjectid9());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid9()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid9()));
						candidateMarks.setSubject9(s);
					}
					
					if(candidateMarkTO.getSubjectOther9()!=null && !candidateMarkTO.getSubjectOther9().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther9(candidateMarkTO.getSubjectOther9());
					}
					
					//set credit
					if(candidateMarkTO.getSubject9Credit()!=null && !candidateMarkTO.getSubject9Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject9Credit(new BigDecimal(candidateMarkTO.getSubject9Credit()));
					}
					
					if(candidateMarkTO.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9TotalMarks()))
						candidateMarks.setSubject9TotalMarks(new BigDecimal(candidateMarkTO.getSubject9TotalMarks()));
					if(candidateMarkTO.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject9ObtainedMarks()))
						candidateMarks.setSubject9ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject9ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid9("");
			
			}
			
			//settting subject10
			if(candidateMarkTO.getSubjectid10()!=null && !candidateMarkTO.getSubjectid10().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid10(),candidateMarkTO.getSubjectmarkid10(),candidateMarkTO.getSubjectOther10(),candidateMarkTO.getSubject10TotalMarks(),candidateMarkTO.getSubject10ObtainedMarks(),candidateMarkTO.getSubject10Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid10(candidateMarkTO.getSubjectid10());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid10()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid10()));
						candidateMarks.setSubject10(s);
					}
					
					if(candidateMarkTO.getSubjectOther10()!=null && !candidateMarkTO.getSubjectOther10().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther10(candidateMarkTO.getSubjectOther10());
					}
					
					//set credit
					if(candidateMarkTO.getSubject10Credit()!=null && !candidateMarkTO.getSubject10Credit().equalsIgnoreCase("")){
						candidateMarks.setSubject10Credit(new BigDecimal(candidateMarkTO.getSubject10Credit()));
					}
					
					if(candidateMarkTO.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10TotalMarks()))
						candidateMarks.setSubject10TotalMarks(new BigDecimal(candidateMarkTO.getSubject10TotalMarks()));
					if(candidateMarkTO.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject10ObtainedMarks()))
						candidateMarks.setSubject10ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject10ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid10("");
			
			}
			
			}//close of cbcss 
			
			//other
			else{
				
			
			//settting subject11
			if(candidateMarkTO.getSubjectid11()!=null && !candidateMarkTO.getSubjectid11().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid11(),candidateMarkTO.getSubjectmarkid11(),candidateMarkTO.getSubjectOther11(),candidateMarkTO.getSubject11TotalMarks(),candidateMarkTO.getSubject11ObtainedMarks(),candidateMarkTO.getSubject11Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid11(candidateMarkTO.getSubjectid11());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid11()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid11()));
						candidateMarks.setSubject11(s);
					}
					
					if(candidateMarkTO.getSubjectOther11()!=null && !candidateMarkTO.getSubjectOther11().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther11(candidateMarkTO.getSubjectOther11());
					}
					
					if(candidateMarkTO.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject11TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11TotalMarks()))
						candidateMarks.setSubject11TotalMarks(new BigDecimal(candidateMarkTO.getSubject11TotalMarks()));
					if(candidateMarkTO.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject11ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject11ObtainedMarks()))
						candidateMarks.setSubject11ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject11ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid11("");
			
			}
			
			//settting subject12
			if(candidateMarkTO.getSubjectid12()!=null && !candidateMarkTO.getSubjectid12().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid12(),candidateMarkTO.getSubjectmarkid12(),candidateMarkTO.getSubjectOther12(),candidateMarkTO.getSubject12TotalMarks(),candidateMarkTO.getSubject12ObtainedMarks(),candidateMarkTO.getSubject12Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid12(candidateMarkTO.getSubjectid12());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid12()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid12()));
						candidateMarks.setSubject12(s);
					}
					
					if(candidateMarkTO.getSubjectOther12()!=null && !candidateMarkTO.getSubjectOther12().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther12(candidateMarkTO.getSubjectOther12());
					}
					
					if(candidateMarkTO.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject12TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12TotalMarks()))
						candidateMarks.setSubject12TotalMarks(new BigDecimal(candidateMarkTO.getSubject12TotalMarks()));
					if(candidateMarkTO.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject12ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject12ObtainedMarks()))
						candidateMarks.setSubject12ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject12ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid12("");
			
			}
			
			//settting subject13
			if(candidateMarkTO.getSubjectid13()!=null && !candidateMarkTO.getSubjectid13().equalsIgnoreCase("")){
				
			
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid13(),candidateMarkTO.getSubjectmarkid13(),candidateMarkTO.getSubjectOther13(),candidateMarkTO.getSubject13TotalMarks(),candidateMarkTO.getSubject13ObtainedMarks(),candidateMarkTO.getSubject13Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid13(candidateMarkTO.getSubjectid13());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid13()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid13()));
						candidateMarks.setSubject13(s);
					}
					
					if(candidateMarkTO.getSubjectOther13()!=null && !candidateMarkTO.getSubjectOther13().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther13(candidateMarkTO.getSubjectOther13());
					}
					
					if(candidateMarkTO.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject13TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13TotalMarks()))
						candidateMarks.setSubject13TotalMarks(new BigDecimal(candidateMarkTO.getSubject13TotalMarks()));
					if(candidateMarkTO.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject13ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject13ObtainedMarks()))
						candidateMarks.setSubject13ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject13ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid13("");
			
			}
			
			//settting subject14
			if(candidateMarkTO.getSubjectid14()!=null && !candidateMarkTO.getSubjectid14().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid14(),candidateMarkTO.getSubjectmarkid14(),candidateMarkTO.getSubjectOther14(),candidateMarkTO.getSubject14TotalMarks(),candidateMarkTO.getSubject14ObtainedMarks(),candidateMarkTO.getSubject14Credit(), admForm);

				candidateMarks.setSubjectid14(candidateMarkTO.getSubjectid14());
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid14()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid14()));
						candidateMarks.setSubject14(s);
					}
					
					if(candidateMarkTO.getSubjectOther14()!=null && !candidateMarkTO.getSubjectOther14().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther14(candidateMarkTO.getSubjectOther14());
					}
					
					if(candidateMarkTO.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject14TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14TotalMarks()))
						candidateMarks.setSubject14TotalMarks(new BigDecimal(candidateMarkTO.getSubject14TotalMarks()));
					if(candidateMarkTO.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject14ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject14ObtainedMarks()))
						candidateMarks.setSubject14ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject14ObtainedMarks()));
				
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid14("");
			
			}
			
			//settting subject15
			if(candidateMarkTO.getSubjectid15()!=null && !candidateMarkTO.getSubjectid15().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid15(),candidateMarkTO.getSubjectmarkid15(),candidateMarkTO.getSubjectOther15(),candidateMarkTO.getSubject15TotalMarks(),candidateMarkTO.getSubject15ObtainedMarks(),candidateMarkTO.getSubject15Credit(), admForm);

				candidateMarks.setSubjectid15(candidateMarkTO.getSubjectid15());
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid15()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid15()));
						candidateMarks.setSubject15(s);
					}
					
					if(candidateMarkTO.getSubjectOther15()!=null && !candidateMarkTO.getSubjectOther15().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther15(candidateMarkTO.getSubjectOther15());
					}
					
					if(candidateMarkTO.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject15TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15TotalMarks()))
						candidateMarks.setSubject15TotalMarks(new BigDecimal(candidateMarkTO.getSubject15TotalMarks()));
					if(candidateMarkTO.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject15ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject15ObtainedMarks()))
						candidateMarks.setSubject15ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject15ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid15("");
			
			}
			
			//settting subject16
			if(candidateMarkTO.getSubjectid16()!=null && !candidateMarkTO.getSubjectid16().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid16(),candidateMarkTO.getSubjectmarkid16(),candidateMarkTO.getSubjectOther16(),candidateMarkTO.getSubject16TotalMarks(),candidateMarkTO.getSubject16ObtainedMarks(),candidateMarkTO.getSubject16Credit(), admForm);

				candidateMarks.setSubjectid16(candidateMarkTO.getSubjectid16());
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid16()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid16()));
						candidateMarks.setSubject16(s);
					}
					
					if(candidateMarkTO.getSubjectOther16()!=null && !candidateMarkTO.getSubjectOther16().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther16(candidateMarkTO.getSubjectOther16());
					}
				
					if(candidateMarkTO.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject16TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16TotalMarks()))
						candidateMarks.setSubject16TotalMarks(new BigDecimal(candidateMarkTO.getSubject16TotalMarks()));
					if(candidateMarkTO.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject16ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject16ObtainedMarks()))
						candidateMarks.setSubject16ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject16ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid16("");
			
			}
			
			//settting subject17
			if(candidateMarkTO.getSubjectid17()!=null && !candidateMarkTO.getSubjectid17().equalsIgnoreCase("")){
				
					AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid17(),candidateMarkTO.getSubjectmarkid17(),candidateMarkTO.getSubjectOther17(),candidateMarkTO.getSubject17TotalMarks(),candidateMarkTO.getSubject17ObtainedMarks(),candidateMarkTO.getSubject17Credit(), admForm);

					candidateMarks.setSubjectid17(candidateMarkTO.getSubjectid17());
					
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid17()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid17()));
						candidateMarks.setSubject17(s);
					}
					
					if(candidateMarkTO.getSubjectOther17()!=null && !candidateMarkTO.getSubjectOther17().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther17(candidateMarkTO.getSubjectOther17());
					}
					
					if(candidateMarkTO.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject17TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17TotalMarks()))
						candidateMarks.setSubject17TotalMarks(new BigDecimal(candidateMarkTO.getSubject17TotalMarks()));
					if(candidateMarkTO.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject17ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject17ObtainedMarks()))
						candidateMarks.setSubject17ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject17ObtainedMarks()));
				
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid17("");
			
			}
			
			//settting subject18
			if(candidateMarkTO.getSubjectid18()!=null && !candidateMarkTO.getSubjectid18().equalsIgnoreCase("")){
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid18(),candidateMarkTO.getSubjectmarkid18(),candidateMarkTO.getSubjectOther18(),candidateMarkTO.getSubject18TotalMarks(),candidateMarkTO.getSubject18ObtainedMarks(),candidateMarkTO.getSubject18Credit(), admForm);

				candidateMarks.setSubjectid18(candidateMarkTO.getSubjectid18());
				
				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid18()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid18()));
						candidateMarks.setSubject18(s);
					}
					
					if(candidateMarkTO.getSubjectOther18()!=null && !candidateMarkTO.getSubjectOther18().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther18(candidateMarkTO.getSubjectOther18());
					}
					
					if(candidateMarkTO.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject18TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18TotalMarks()))
						candidateMarks.setSubject18TotalMarks(new BigDecimal(candidateMarkTO.getSubject18TotalMarks()));
					if(candidateMarkTO.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject18ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject18ObtainedMarks()))
						candidateMarks.setSubject18ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject18ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid18("");
			
			}
			
			//settting subject19
			if(candidateMarkTO.getSubjectid19()!=null && !candidateMarkTO.getSubjectid19().equalsIgnoreCase("")){
				
					
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid19(),candidateMarkTO.getSubjectmarkid19(),candidateMarkTO.getSubjectOther19(),candidateMarkTO.getSubject19TotalMarks(),candidateMarkTO.getSubject19ObtainedMarks(),candidateMarkTO.getSubject19Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid19(candidateMarkTO.getSubjectid19());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid19()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid19()));
						candidateMarks.setSubject19(s);
					}
					
					if(candidateMarkTO.getSubjectOther19()!=null && !candidateMarkTO.getSubjectOther19().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther19(candidateMarkTO.getSubjectOther19());
					}
				
					if(candidateMarkTO.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject19TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19TotalMarks()))
						candidateMarks.setSubject19TotalMarks(new BigDecimal(candidateMarkTO.getSubject19TotalMarks()));
					if(candidateMarkTO.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject19ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject19ObtainedMarks()))
						candidateMarks.setSubject19ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject19ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid19("");
			
			}
			
			//settting subject20
			if(candidateMarkTO.getSubjectid20()!=null && !candidateMarkTO.getSubjectid20().equalsIgnoreCase("")){
				
				
				
				AdmSubjectMarkForRank admSubjectMarkForRank=getAdmSubjectMarkForRank(docName,candidateMarkTO,candidateMarkTO.getSubjectid20(),candidateMarkTO.getSubjectmarkid20(),candidateMarkTO.getSubjectOther20(),candidateMarkTO.getSubject20TotalMarks(),candidateMarkTO.getSubject20ObtainedMarks(),candidateMarkTO.getSubject20Credit(), admForm);

				if(admSubjectMarkForRank!=null){
					submarksSet.add(admSubjectMarkForRank);

					candidateMarks.setSubjectid20(candidateMarkTO.getSubjectid20());
					
					if(admCoreMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admCoreMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}else if(admComplMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admComplMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}else if(admCommonMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admCommonMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}else if(admopenMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admopenMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}else if(admSubMap.containsKey(Integer.parseInt(candidateMarkTO.getSubjectid20()))){
						
						String s=(String)admSubMap.get(Integer.parseInt(candidateMarkTO.getSubjectid20()));
						candidateMarks.setSubject20(s);
					}
					
					if(candidateMarkTO.getSubjectOther20()!=null && !candidateMarkTO.getSubjectOther20().equalsIgnoreCase("")){
						candidateMarks.setSubjectOther20(candidateMarkTO.getSubjectOther20());
					}
					
					if(candidateMarkTO.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject20TotalMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20TotalMarks()))
						candidateMarks.setSubject20TotalMarks(new BigDecimal(candidateMarkTO.getSubject20TotalMarks()));
					if(candidateMarkTO.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(candidateMarkTO.getSubject20ObtainedMarks()) && CommonUtil.isValidDecimal(candidateMarkTO.getSubject20ObtainedMarks()))
						candidateMarks.setSubject20ObtainedMarks(new BigDecimal(candidateMarkTO.getSubject20ObtainedMarks()));
				
				}else{
					submarkCount++;
				}
				
				
				
			}else{
				submarkCount++;
					candidateMarkTO.setSubjectid20("");
			
			}
			
			}//other over
			
			
			
			if(submarkCount!=10)
			ednQualification.setAdmSubjectMarkForRank(submarksSet);
            ednQualification.setUgPattern(admForm.getPatternofStudy());
			
             
		}
		
		
		//raghu storing database
		private AdmSubjectMarkForRank getAdmSubjectMarkForRank(int docname,CandidateMarkTO candidateMarkTO,String subjectId,String subjectMarkId,String subjectOther,String subjectTotalMark,String subjectObtainMark,String subjectCredit, OnlineApplicationForm admForm) {
			
			if(subjectId!=null && !subjectId.equalsIgnoreCase("") && subjectTotalMark!=null && !subjectTotalMark.equalsIgnoreCase("") && subjectObtainMark!=null && !subjectObtainMark.equalsIgnoreCase("")){
				
			
			 AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
			 if(subjectMarkId!=null && !subjectMarkId.equalsIgnoreCase("")){
            	 admSubjectMarkForRank.setId(Integer.parseInt(subjectMarkId));
             }
			 AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
             admSubjectForRank.setId(Integer.parseInt(subjectId));
             admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
             admSubjectMarkForRank.setObtainedmark(subjectObtainMark);
             admSubjectMarkForRank.setMaxmark(subjectTotalMark);
             admSubjectMarkForRank.setIsActive(true);
             admSubjectMarkForRank.setCreatedDate(new Date());
             admSubjectMarkForRank.setCreatedBy(admForm.getUserId());
             admSubjectMarkForRank.setLastModifiedDate(new Date());
             admSubjectMarkForRank.setModifiedBy(admForm.getUserId());
             
            //class 12
 			int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
 			
             if(docname==doctypeId){
            	 Double obtmark=Double.parseDouble(subjectObtainMark);
                 Double maxmark=Double.parseDouble(subjectTotalMark);
                 Double conmark=(obtmark/maxmark)*200;
                 DecimalFormat df=new DecimalFormat("#.###");
                 admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
        
                // admSubjectMarkForRank.setAdmSubjectOther(subjectOther);
                // admSubjectMarkForRank.setCredit(subjectCredit);
            
             }
             
             //deg
  			 int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
  			
             if(docname==doctypeId1){
            	 
            	// if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS") || admForm.getPatternofStudy().equalsIgnoreCase("OTHER")){
         			
            	 Double obtmark=Double.parseDouble(subjectObtainMark);
                 Double maxmark=Double.parseDouble(subjectTotalMark);
                 Double conmark=(obtmark/maxmark)*4;
                 DecimalFormat df=new DecimalFormat("#.###");
                 admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
                 
                 admSubjectMarkForRank.setAdmSubjectOther(subjectOther);
                 admSubjectMarkForRank.setCredit(subjectCredit);
                 
                 /* }else{
            		 //new cbcsss with 10 marks

                	 Double obtmark=Double.parseDouble(subjectObtainMark);
                     Double maxmark=Double.parseDouble(subjectTotalMark);
                     Double conmark=(obtmark/maxmark)*10;
                     DecimalFormat df=new DecimalFormat("#.##");
                     admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
                     
                     admSubjectMarkForRank.setAdmSubjectOther(subjectOther);
                     admSubjectMarkForRank.setCredit(subjectCredit);
                	 
            	 }*/
                 
             }
             
			return admSubjectMarkForRank;
			}
			else{
				
			return null;
			}
		}
	
		
		/**
		 * @param admApplnBO
		 * @return adminAppTO
		 */
		public AdmApplnTO copyPropertiesValue(AdmAppln admApplnBo,HttpSession session)throws Exception {
			log.info("enter copyPropertiesValue admappln" );
			AdmApplnTO adminAppTO = null;
			PersonalDataTO personalDataTO = null;
			CourseTO courseTO = null;
			//Commented By Manu.List not be null initialized
		   /*List<EdnQualificationTO> ednQualificationList = null;
			List<ApplicantWorkExperienceTO> workExpList = null;
			List<CandidatePrerequisiteMarksTO> prereqList = null*/
			List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
			/*List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
			List<CandidatePrerequisiteMarksTO> prereqList =new ArrayList<CandidatePrerequisiteMarksTO>();*/
			
			PreferenceTO preferenceTO = null;

			List<ApplnDocTO> editDocuments = null;


			if (admApplnBo != null) {
				adminAppTO = new AdmApplnTO();
				adminAppTO.setId(admApplnBo.getId());
				adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
				adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
				if(admApplnBo.getIsFinalMeritApproved()!=null)
				adminAppTO.setIsFinalMeritApproved(admApplnBo.getIsFinalMeritApproved());
				
				/*if(admApplnBo.getStudentVehicleDetailses()!=null && !admApplnBo.getStudentVehicleDetailses().isEmpty()){
					Iterator<StudentVehicleDetails> vehItr=admApplnBo.getStudentVehicleDetailses().iterator();
					StudentVehicleDetailsTO vehTO= new StudentVehicleDetailsTO();
					while (vehItr.hasNext()) {
						StudentVehicleDetails vehDet = (StudentVehicleDetails) vehItr.next();
						vehTO.setId(vehDet.getId());
						vehTO.setVehicleNo(vehDet.getVehicleNo());
						vehTO.setVehicleType(vehDet.getVehicleType());
					}
					adminAppTO.setVehicleDetail(vehTO);
				}else{
				adminAppTO.setVehicleDetail(new StudentVehicleDetailsTO());
				}*/
				adminAppTO.setApplicationId(admApplnBo.getId());
				adminAppTO.setRemark(admApplnBo.getRemarks());
				adminAppTO.setApplnNo(admApplnBo.getApplnNo());
				adminAppTO.setChallanRefNo(admApplnBo.getChallanRefNo());
				adminAppTO.setJournalNo(admApplnBo.getJournalNo());
				adminAppTO.setBankBranch(admApplnBo.getBankBranch());
				adminAppTO.setAppliedYear(admApplnBo.getAppliedYear());
				if(admApplnBo.getRecommedationApplicationNo() != null && !admApplnBo.getRecommedationApplicationNo().isEmpty()){
					adminAppTO.setRecommedationApplicationNo(admApplnBo.getRecommedationApplicationNo());
				}
				if(admApplnBo.getTotalWeightage()!=null)
				adminAppTO.setTotalWeightage(admApplnBo.getTotalWeightage().toString());
				if(admApplnBo.getWeightageAdjustedMarks()!=null)
					adminAppTO.setWeightageAdjustMark(admApplnBo.getWeightageAdjustedMarks().toString());
				adminAppTO.setIsSelected(admApplnBo.getIsSelected());
				//Added By Manu
				if(admApplnBo.getNotSelected()==null){
					adminAppTO.setNotSelected(false);
				}
				else{
					adminAppTO.setNotSelected(admApplnBo.getNotSelected());
				}
				if(admApplnBo.getIsWaiting()==null){
					adminAppTO.setIsWaiting(false);
				}
				else{
					adminAppTO.setIsWaiting(admApplnBo.getIsWaiting());
				}
				
				adminAppTO.setIsBypassed(admApplnBo.getIsBypassed());
				adminAppTO.setIsCancelled(admApplnBo.getIsCancelled());
				if(admApplnBo.getIsFreeShip()!=null && admApplnBo.getIsFreeShip()){
					adminAppTO.setIsFreeShip(true);
				}else{
					adminAppTO.setIsFreeShip(false);
				}
				// added for challan verification 
				adminAppTO.setIsChallanVerified(admApplnBo.getIsChallanVerified());
				//addition for challan verification completed
				adminAppTO.setIsApproved(admApplnBo.getIsApproved());
				adminAppTO.setIsLIG(admApplnBo.getIsLig());
				adminAppTO.setPersonalDataid(admApplnBo.getPersonalData().getId());
				adminAppTO.setIsInterviewSelected(admApplnBo.getIsInterviewSelected());

				adminAppTO.setTcNo(admApplnBo.getTcNo());
				adminAppTO.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getTcDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
				adminAppTO.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getMarkscardDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				
				if(admApplnBo.getDate()!= null){
					adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo.getDate()));
				}
				if(admApplnBo.getAdmissionDate()!= null){
					adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getAdmissionDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				}
				if(admApplnBo.getCourseChangeDate()!= null){
					adminAppTO.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getCourseChangeDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				}
				
				if(admApplnBo.getAmount()!=null)
				adminAppTO.setAmount(String.valueOf(admApplnBo.getAmount().doubleValue()));
				
				adminAppTO.setCandidatePrerequisiteMarks(admApplnBo.getCandidatePrerequisiteMarks());
				//set title for mother and father
				AdmapplnAdditionalInfo additionalInfo = new AdmapplnAdditionalInfo();
				if(admApplnBo.getAdmapplnAdditionalInfo() != null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
					List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(admApplnBo.getAdmapplnAdditionalInfo());
					additionalInfo = additionalList.get(0);
				}
				if(additionalInfo.getTitleFather() != null){
					adminAppTO.setTitleOfFather(additionalInfo.getTitleFather());
				}
				if(additionalInfo.getTitleMother() != null){
					adminAppTO.setTitleOfMother(additionalInfo.getTitleMother());
				}
				if(additionalInfo.getBackLogs() != null){
					adminAppTO.setBackLogs(additionalInfo.getBackLogs());
				}
				
				//raghu
				if(additionalInfo.getIsSaypass()!=null ){
					adminAppTO.setIsSaypass(additionalInfo.getIsSaypass());
				}
				// /* code added by chandra
				if(additionalInfo!=null){
					if(additionalInfo.getIsComedk()!=null && additionalInfo.getIsComedk())
						adminAppTO.setIsComeDk(true);
					else
						adminAppTO.setIsComeDk(false);
				}/*else{
					adminAppTO.setIsComeDk(false);
				}*/
				//  */ code added by chandra
				
				personalDataTO = copyPropertiesValue(admApplnBo.getPersonalData(),admApplnBo);
				adminAppTO.setPersonalData(personalDataTO);

				if(admApplnBo.getCandidateEntranceDetailses()!=null && !admApplnBo.getCandidateEntranceDetailses().isEmpty()){
					copyEntranceValue(admApplnBo.getCandidateEntranceDetailses(),adminAppTO);
				}else{
					adminAppTO.setEntranceDetail(new CandidateEntranceDetailsTO());
				}
				
				if(admApplnBo.getApplicantLateralDetailses()!=null && !admApplnBo.getApplicantLateralDetailses().isEmpty()){
					copyLateralDetails(admApplnBo.getApplicantLateralDetailses(),adminAppTO);
				}
				
				if(admApplnBo.getApplicantTransferDetailses()!=null && !admApplnBo.getApplicantTransferDetailses().isEmpty()){
					copyTransferDetails(admApplnBo.getApplicantTransferDetailses(),adminAppTO);
				}
				
				courseTO = copyPropertiesValue(admApplnBo.getCourse());
				adminAppTO.setCourse(courseTO);
				
				CourseTO courseTO1 = copyPropertiesValue(admApplnBo.getCourseBySelectedCourseId());
				adminAppTO.setSelectedCourse(courseTO1);
				//code added by chandra
				adminAppTO.setAdmittedCourse(courseTO1);
				
				/*String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
				boolean workExpNeed=false;
				if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
					workExpNeed=true;
				}
				workExpList=copyWorkExpValue(admApplnBo.getApplicantWorkExperiences(),workExpList,workExpNeed);
				adminAppTO.setWorkExpList(workExpList);
				session.setAttribute("workExpListSize",workExpList.size());*/
				ednQualificationList = copyPropertiesValue(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear());
				adminAppTO.setEdnQualificationList(ednQualificationList);
				session.setAttribute("eduQualificationListSize", ednQualificationList.size());

				preferenceTO = copyPropertiesValue(admApplnBo.getCandidatePreferences());
				adminAppTO.setPreference(preferenceTO);
		
				editDocuments = copyPropertiesEditDocValue(admApplnBo,adminAppTO.getSelectedCourse().getId(),adminAppTO,admApplnBo.getAppliedYear());
				adminAppTO.setEditDocuments(editDocuments);
				
				/*prereqList=copyPrerequisiteDetails(admApplnBo.getCandidatePrerequisiteMarks());
				adminAppTO.setPrerequisiteTos(prereqList);*/
				
				if(admApplnBo.getStudentQualifyexamDetails()!=null && !admApplnBo.getStudentQualifyexamDetails().isEmpty()){
					adminAppTO.setOriginalQualDetails(admApplnBo.getStudentQualifyexamDetails());
				}
				
				/*if(admApplnBo.getExamCenter()!= null && admApplnBo.getExamCenter().getCenter()!= null && !admApplnBo.getExamCenter().getCenter().trim().isEmpty()){ 
					adminAppTO.setExamCenterName(admApplnBo.getExamCenter().getCenter());
					adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
				}
				if(admApplnBo.getInterScheduleSelection()!= null && admApplnBo.getInterScheduleSelection().getSelectionProcessDate()!= null){ 
					adminAppTO.setInterviewDate(CommonUtil.ConvertStringToDateFormat(
					admApplnBo.getInterScheduleSelection().getSelectionProcessDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
					//adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
					adminAppTO.setInterviewSelectionScheduleId(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
				}*/
				
				if(admApplnBo.getAdmittedThrough()!=null && admApplnBo.getAdmittedThrough().getIsActive()==true){
					adminAppTO.setAdmittedThroughId(String.valueOf(admApplnBo.getAdmittedThrough().getId()));
				}
				/*if(admApplnBo.getApplicantSubjectGroups()!=null && !admApplnBo.getApplicantSubjectGroups().isEmpty()){

					List<ApplicantSubjectGroup> applicantgroups=new ArrayList<ApplicantSubjectGroup>();
					
					Iterator<ApplicantSubjectGroup> subItr=admApplnBo.getApplicantSubjectGroups().iterator();
					while (subItr.hasNext()) {
						ApplicantSubjectGroup appSubGroup = (ApplicantSubjectGroup) subItr.next();
						if(appSubGroup.getSubjectGroup()!=null && appSubGroup.getSubjectGroup().getCourse()!=null && appSubGroup.getSubjectGroup().getCourse().getId()!=admApplnBo.getCourseBySelectedCourseId().getId()){
							subItr.remove();
						}else{
							applicantgroups.add(appSubGroup);
						}
					}
					if(!applicantgroups.isEmpty()){
						String[] subjectgroups=new String[applicantgroups.size()];
					Iterator<ApplicantSubjectGroup> subItr2=applicantgroups.iterator();
						for(int i=0;subItr2.hasNext();i++) {
							ApplicantSubjectGroup appSubGroup = (ApplicantSubjectGroup) subItr2.next();
							if(appSubGroup.getSubjectGroup()!=null && appSubGroup.getSubjectGroup().getCourse()!=null && appSubGroup.getSubjectGroup().getCourse().getId()==admApplnBo.getCourseBySelectedCourseId().getId()){
								subjectgroups[i]=String.valueOf(appSubGroup.getSubjectGroup().getId());
							}
						}
						adminAppTO.setSubjectGroupIds(subjectgroups);
					}
					adminAppTO.setApplicantSubjectGroups(applicantgroups);
				}*/
				if(admApplnBo.getMode() != null && !admApplnBo.getMode().isEmpty()){
					adminAppTO.setMode(admApplnBo.getMode());
				}
				adminAppTO.setAdmStatus(admApplnBo.getAdmStatus());
				//added for student specialization prefered and adm appln additional info -Smitha
				if(admApplnBo.getStudentSpecializationPrefered()!=null && !admApplnBo.getStudentSpecializationPrefered().isEmpty()){
					adminAppTO.setStudentSpecializationPrefered(admApplnBo.getStudentSpecializationPrefered());
				}
				if(admApplnBo.getAdmapplnAdditionalInfo()!=null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
					adminAppTO.setAdmapplnAdditionalInfos(admApplnBo.getAdmapplnAdditionalInfo());
				}
				adminAppTO.setSeatNo(admApplnBo.getSeatNo());
				adminAppTO.setFinalMeritListApproveDate(admApplnBo.getFinalMeritListApproveDate());
				adminAppTO.setIsPreferenceUpdated(admApplnBo.getIsPreferenceUpdated());
				adminAppTO.setVerifiedBy(admApplnBo.getVerifiedBy());
				if(admApplnBo.getDdDrawnOn()!=null && !admApplnBo.getDdDrawnOn().isEmpty() && admApplnBo.getDdIssuingBank()!=null && !admApplnBo.getDdIssuingBank().isEmpty()){
					adminAppTO.setDdPayment(true);
					adminAppTO.setOnlinePayment(false);
					adminAppTO.setChallanPayment(false);
					adminAppTO.setDdDrawnOn(admApplnBo.getDdDrawnOn());
					adminAppTO.setDdIssuingBank(admApplnBo.getDdIssuingBank());
				}
				else if (admApplnBo.getChallanRefNo()==null || admApplnBo.getChallanRefNo().isEmpty()){
					adminAppTO.setOnlinePayment(true);
					adminAppTO.setDdPayment(false);
					adminAppTO.setChallanPayment(false);
				}
				else {
					
					adminAppTO.setChallanPayment(true);
					adminAppTO.setDdPayment(false);
					adminAppTO.setOnlinePayment(false);
				}
				 if(admApplnBo.getStudents() != null){
						for (Student student : admApplnBo.getStudents()) {
							adminAppTO.setStudentId(student.getId());
						}
					}
			   //code added by mahi
				 //raghu
			  /* if (admApplnBo.getIntrSessionDetails()!=null) {
				   adminAppTO.setInterSessionDetails(String.valueOf(admApplnBo.getIntrSessionDetails().getId()));
				   adminAppTO.setInterSessionName(admApplnBo.getIntrSessionDetails().getSemesterName());
				   
			   }*/
			   if (admApplnBo.getIsDraftMode()!=null) {
					adminAppTO.setIsDraftMode(admApplnBo.getIsDraftMode());
				}
				if (admApplnBo.getIsDraftCancelled()!=null) {
					adminAppTO.setIsDraftCancelled(admApplnBo.getIsDraftCancelled());
				}
				if (admApplnBo.getCurrentPageName()!=null) {
					adminAppTO.setCurrentPageName(admApplnBo.getCurrentPageName());
				}
			  //end 
			}
			log.info("exit copyPropertiesValue admappln" );
			return adminAppTO;
		}
		
		
		//method added by chandra with out session parameter
		
		public AdmApplnTO copyPropertiesValue(AdmAppln admApplnBo)throws Exception {
			log.info("enter copyPropertiesValue admappln" );
			AdmApplnTO adminAppTO = null;
			PersonalDataTO personalDataTO = null;
			CourseTO courseTO = null;
			//Commented By Manu.List not be null initialized
		   /*List<EdnQualificationTO> ednQualificationList = null;
			List<ApplicantWorkExperienceTO> workExpList = null;
			List<CandidatePrerequisiteMarksTO> prereqList = null*/
			List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
			/*List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
			List<CandidatePrerequisiteMarksTO> prereqList =new ArrayList<CandidatePrerequisiteMarksTO>();*/
			
			PreferenceTO preferenceTO = null;

			List<ApplnDocTO> editDocuments = null;


			if (admApplnBo != null) {
				adminAppTO = new AdmApplnTO();
				adminAppTO.setId(admApplnBo.getId());
				adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
				adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
				if(admApplnBo.getIsFinalMeritApproved()!=null)
				adminAppTO.setIsFinalMeritApproved(admApplnBo.getIsFinalMeritApproved());
				
				/*if(admApplnBo.getStudentVehicleDetailses()!=null && !admApplnBo.getStudentVehicleDetailses().isEmpty()){
					Iterator<StudentVehicleDetails> vehItr=admApplnBo.getStudentVehicleDetailses().iterator();
					StudentVehicleDetailsTO vehTO= new StudentVehicleDetailsTO();
					while (vehItr.hasNext()) {
						StudentVehicleDetails vehDet = (StudentVehicleDetails) vehItr.next();
						vehTO.setId(vehDet.getId());
						vehTO.setVehicleNo(vehDet.getVehicleNo());
						vehTO.setVehicleType(vehDet.getVehicleType());
					}
					adminAppTO.setVehicleDetail(vehTO);
				}else{
				adminAppTO.setVehicleDetail(new StudentVehicleDetailsTO());
				}*/
				adminAppTO.setApplicationId(admApplnBo.getId());
				adminAppTO.setRemark(admApplnBo.getRemarks());
				adminAppTO.setApplnNo(admApplnBo.getApplnNo());
				adminAppTO.setChallanRefNo(admApplnBo.getChallanRefNo());
				adminAppTO.setJournalNo(admApplnBo.getJournalNo());
				adminAppTO.setBankBranch(admApplnBo.getBankBranch());
				adminAppTO.setAppliedYear(admApplnBo.getAppliedYear());
				if(admApplnBo.getRecommedationApplicationNo() != null && !admApplnBo.getRecommedationApplicationNo().isEmpty() ){
					adminAppTO.setRecommedationApplicationNo(admApplnBo.getRecommedationApplicationNo());
				}
				if(admApplnBo.getTotalWeightage()!=null)
				adminAppTO.setTotalWeightage(admApplnBo.getTotalWeightage().toString());
				if(admApplnBo.getWeightageAdjustedMarks()!=null)
					adminAppTO.setWeightageAdjustMark(admApplnBo.getWeightageAdjustedMarks().toString());
				adminAppTO.setIsSelected(admApplnBo.getIsSelected());
				//Added By Manu
				if(admApplnBo.getNotSelected()==null){
					adminAppTO.setNotSelected(false);
				}
				else{
					adminAppTO.setNotSelected(admApplnBo.getNotSelected());
				}
				if(admApplnBo.getIsWaiting()==null){
					adminAppTO.setIsWaiting(false);
				}
				else{
					adminAppTO.setIsWaiting(admApplnBo.getIsWaiting());
				}
				
				adminAppTO.setIsBypassed(admApplnBo.getIsBypassed());
				adminAppTO.setIsCancelled(admApplnBo.getIsCancelled());
				if(admApplnBo.getIsFreeShip()!=null && admApplnBo.getIsFreeShip()){
					adminAppTO.setIsFreeShip(true);
				}else{
					adminAppTO.setIsFreeShip(false);
				}
				// added for challan verification 
				adminAppTO.setIsChallanVerified(admApplnBo.getIsChallanVerified());
				//addition for challan verification completed
				adminAppTO.setIsApproved(admApplnBo.getIsApproved());
				adminAppTO.setIsLIG(admApplnBo.getIsLig());
				adminAppTO.setPersonalDataid(admApplnBo.getPersonalData().getId());
				adminAppTO.setIsInterviewSelected(admApplnBo.getIsInterviewSelected());

				adminAppTO.setTcNo(admApplnBo.getTcNo());
				adminAppTO.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getTcDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
				adminAppTO.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getMarkscardDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				
				if(admApplnBo.getDate()!= null){
					adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo.getDate()));
				}
				if(admApplnBo.getAdmissionDate()!= null){
					adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getAdmissionDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				}
				if(admApplnBo.getCourseChangeDate()!= null){
					adminAppTO.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getCourseChangeDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				}
				
				if(admApplnBo.getAmount()!=null)
				adminAppTO.setAmount(String.valueOf(admApplnBo.getAmount().doubleValue()));
				
				adminAppTO.setCandidatePrerequisiteMarks(admApplnBo.getCandidatePrerequisiteMarks());
				//set title for mother and father
				AdmapplnAdditionalInfo additionalInfo = new AdmapplnAdditionalInfo();
				if(admApplnBo.getAdmapplnAdditionalInfo() != null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
					List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(admApplnBo.getAdmapplnAdditionalInfo());
					additionalInfo = additionalList.get(0);
				}
				if(additionalInfo.getTitleFather() != null){
					adminAppTO.setTitleOfFather(additionalInfo.getTitleFather());
				}
				if(additionalInfo.getTitleMother() != null){
					adminAppTO.setTitleOfMother(additionalInfo.getTitleMother());
				}
				if(additionalInfo.getBackLogs() != null){
					adminAppTO.setBackLogs(additionalInfo.getBackLogs());
				}
				//raghu
				if(additionalInfo.getIsSaypass()!=null ){
					adminAppTO.setIsSaypass(additionalInfo.getIsSaypass());
				}
				// /* code added by chandra
				if(additionalInfo!=null){
					if(additionalInfo.getIsComedk()!=null && additionalInfo.getIsComedk())
						adminAppTO.setIsComeDk(true);
					else
						adminAppTO.setIsComeDk(false);
				}/*else{
					adminAppTO.setIsComeDk(false);
				}*/
				//  */ code added by chandra
				
				personalDataTO = copyPropertiesValue(admApplnBo.getPersonalData(),admApplnBo);
				adminAppTO.setPersonalData(personalDataTO);

				if(admApplnBo.getCandidateEntranceDetailses()!=null && !admApplnBo.getCandidateEntranceDetailses().isEmpty()){
					copyEntranceValue(admApplnBo.getCandidateEntranceDetailses(),adminAppTO);
				}else{
					adminAppTO.setEntranceDetail(new CandidateEntranceDetailsTO());
				}
				
				if(admApplnBo.getApplicantLateralDetailses()!=null && !admApplnBo.getApplicantLateralDetailses().isEmpty()){
					copyLateralDetails(admApplnBo.getApplicantLateralDetailses(),adminAppTO);
				}
				
				if(admApplnBo.getApplicantTransferDetailses()!=null && !admApplnBo.getApplicantTransferDetailses().isEmpty()){
					copyTransferDetails(admApplnBo.getApplicantTransferDetailses(),adminAppTO);
				}
				
				courseTO = copyPropertiesValue(admApplnBo.getCourse());
				adminAppTO.setCourse(courseTO);
				
				CourseTO courseTO1 = copyPropertiesValue(admApplnBo.getCourseBySelectedCourseId());
				adminAppTO.setSelectedCourse(courseTO1);
				//code added by chandra
				adminAppTO.setAdmittedCourse(courseTO1);
				
				/*String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
				boolean workExpNeed=false;
				if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
					workExpNeed=true;
				}
				workExpList=copyWorkExpValue(admApplnBo.getApplicantWorkExperiences(),workExpList,workExpNeed);
				adminAppTO.setWorkExpList(workExpList);*/
				ednQualificationList = copyPropertiesValue(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear());
				adminAppTO.setEdnQualificationList(ednQualificationList);

				preferenceTO = copyPropertiesValue(admApplnBo.getCandidatePreferences());
				adminAppTO.setPreference(preferenceTO);
		
				editDocuments = copyPropertiesEditDocValue(admApplnBo,adminAppTO.getSelectedCourse().getId(),adminAppTO,admApplnBo.getAppliedYear());
				adminAppTO.setEditDocuments(editDocuments);
				
				/*prereqList=copyPrerequisiteDetails(admApplnBo.getCandidatePrerequisiteMarks());
				adminAppTO.setPrerequisiteTos(prereqList);*/
				
				if(admApplnBo.getStudentQualifyexamDetails()!=null && !admApplnBo.getStudentQualifyexamDetails().isEmpty()){
					adminAppTO.setOriginalQualDetails(admApplnBo.getStudentQualifyexamDetails());
				}
				
				/*if(admApplnBo.getExamCenter()!= null && admApplnBo.getExamCenter().getCenter()!= null && !admApplnBo.getExamCenter().getCenter().trim().isEmpty()){ 
					adminAppTO.setExamCenterName(admApplnBo.getExamCenter().getCenter());
					adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
				}
				if(admApplnBo.getInterScheduleSelection()!= null && admApplnBo.getInterScheduleSelection().getSelectionProcessDate()!= null){ 
					adminAppTO.setInterviewDate(CommonUtil.ConvertStringToDateFormat(
					admApplnBo.getInterScheduleSelection().getSelectionProcessDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
					//adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
					adminAppTO.setInterviewSelectionScheduleId(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
				}*/
				
				if(admApplnBo.getAdmittedThrough()!=null && admApplnBo.getAdmittedThrough().getIsActive()==true){
					adminAppTO.setAdmittedThroughId(String.valueOf(admApplnBo.getAdmittedThrough().getId()));
				}
				/*if(admApplnBo.getApplicantSubjectGroups()!=null && !admApplnBo.getApplicantSubjectGroups().isEmpty()){

					List<ApplicantSubjectGroup> applicantgroups=new ArrayList<ApplicantSubjectGroup>();
					
					Iterator<ApplicantSubjectGroup> subItr=admApplnBo.getApplicantSubjectGroups().iterator();
					while (subItr.hasNext()) {
						ApplicantSubjectGroup appSubGroup = (ApplicantSubjectGroup) subItr.next();
						if(appSubGroup.getSubjectGroup()!=null && appSubGroup.getSubjectGroup().getCourse()!=null && appSubGroup.getSubjectGroup().getCourse().getId()!=admApplnBo.getCourseBySelectedCourseId().getId()){
							subItr.remove();
						}else{
							applicantgroups.add(appSubGroup);
						}
					}
					if(!applicantgroups.isEmpty()){
						String[] subjectgroups=new String[applicantgroups.size()];
					Iterator<ApplicantSubjectGroup> subItr2=applicantgroups.iterator();
						for(int i=0;subItr2.hasNext();i++) {
							ApplicantSubjectGroup appSubGroup = (ApplicantSubjectGroup) subItr2.next();
							if(appSubGroup.getSubjectGroup()!=null && appSubGroup.getSubjectGroup().getCourse()!=null && appSubGroup.getSubjectGroup().getCourse().getId()==admApplnBo.getCourseBySelectedCourseId().getId()){
								subjectgroups[i]=String.valueOf(appSubGroup.getSubjectGroup().getId());
							}
						}
						adminAppTO.setSubjectGroupIds(subjectgroups);
					}
					adminAppTO.setApplicantSubjectGroups(applicantgroups);
				}*/
				if(admApplnBo.getMode() != null && !admApplnBo.getMode().isEmpty()){
					adminAppTO.setMode(admApplnBo.getMode());
				}
				adminAppTO.setAdmStatus(admApplnBo.getAdmStatus());
				//added for student specialization prefered and adm appln additional info -Smitha
				if(admApplnBo.getStudentSpecializationPrefered()!=null && !admApplnBo.getStudentSpecializationPrefered().isEmpty()){
					adminAppTO.setStudentSpecializationPrefered(admApplnBo.getStudentSpecializationPrefered());
				}
				if(admApplnBo.getAdmapplnAdditionalInfo()!=null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
					adminAppTO.setAdmapplnAdditionalInfos(admApplnBo.getAdmapplnAdditionalInfo());
				}
				adminAppTO.setSeatNo(admApplnBo.getSeatNo());
				adminAppTO.setFinalMeritListApproveDate(admApplnBo.getFinalMeritListApproveDate());
				adminAppTO.setIsPreferenceUpdated(admApplnBo.getIsPreferenceUpdated());
				adminAppTO.setVerifiedBy(admApplnBo.getVerifiedBy());
				if(admApplnBo.getDdDrawnOn()!=null && !admApplnBo.getDdDrawnOn().isEmpty() && admApplnBo.getDdIssuingBank()!=null && !admApplnBo.getDdIssuingBank().isEmpty()){
					adminAppTO.setDdPayment(true);
					adminAppTO.setOnlinePayment(false);
					adminAppTO.setChallanPayment(false);
					adminAppTO.setDdDrawnOn(admApplnBo.getDdDrawnOn());
					adminAppTO.setDdIssuingBank(admApplnBo.getDdIssuingBank());
				}
				else if (admApplnBo.getChallanRefNo()==null || admApplnBo.getChallanRefNo().isEmpty()){
					adminAppTO.setOnlinePayment(true);
					adminAppTO.setDdPayment(false);
					adminAppTO.setChallanPayment(false);
				}
				else {
					
					adminAppTO.setChallanPayment(true);
					adminAppTO.setDdPayment(false);
					adminAppTO.setOnlinePayment(false);
				}
				 if(admApplnBo.getStudents() != null){
						for (Student student : admApplnBo.getStudents()) {
							adminAppTO.setStudentId(student.getId());
						}
					}
			   //code added by mahi
				 //raghu
			 /*  if (admApplnBo.getIntrSessionDetails()!=null) {
				   adminAppTO.setInterSessionDetails(String.valueOf(admApplnBo.getIntrSessionDetails().getId()));
				   adminAppTO.setInterSessionName(admApplnBo.getIntrSessionDetails().getSemesterName());
				   
			   }*/
			   if (admApplnBo.getIsDraftMode()!=null) {
					adminAppTO.setIsDraftMode(admApplnBo.getIsDraftMode());
				}
				if (admApplnBo.getIsDraftCancelled()!=null) {
					adminAppTO.setIsDraftCancelled(admApplnBo.getIsDraftCancelled());
				}
				if (admApplnBo.getCurrentPageName()!=null) {
					adminAppTO.setCurrentPageName(admApplnBo.getCurrentPageName());
				}
			  //end 
			}
			log.info("exit copyPropertiesValue admappln" );
			return adminAppTO;
		}
		/**
		 * @param applicantTransferDetailses
		 * @param adminAppTO
		 */
		private void copyTransferDetails(
				Set<ApplicantTransferDetails> applicantTransferDetailses,
				AdmApplnTO adminAppTO) {
			log.info("enter copyTransferDetails" );
			Iterator<ApplicantTransferDetails> entItr=applicantTransferDetailses.iterator();
			adminAppTO.setTransferDetailBos(applicantTransferDetailses);
			List<ApplicantTransferDetailsTO> transferTos= new ArrayList<ApplicantTransferDetailsTO>();
			ApplicantTransferDetailsTO to=null;
			while (entItr.hasNext()) {
				ApplicantTransferDetails entDetails = (ApplicantTransferDetails) entItr.next();
				
				if(entDetails!=null){
					to= new ApplicantTransferDetailsTO();
					to.setId(entDetails.getId());
					if(entDetails.getAdmAppln()!=null)
					to.setAdmApplnId(entDetails.getAdmAppln().getId());
					
					to.setUniversityAppNo(entDetails.getUniversityAppNo());
					to.setRegistationNo(entDetails.getRegistationNo());
					to.setInstituteAddr(entDetails.getInstituteAddr());
					to.setArrearDetail(entDetails.getArrearDetail());
					to.setSemesterName(entDetails.getSemesterName());
					to.setSemesterNo(entDetails.getSemesterNo());
					if(entDetails.getMaxMarks()!=null)
					to.setMaxMarks(String.valueOf(entDetails.getMaxMarks()));
				
					if(entDetails.getMinMarks()!=null)
						to.setMinMarks(String.valueOf(entDetails.getMinMarks()));
					if(entDetails.getMarksObtained()!=null)
						to.setMarksObtained(String.valueOf(entDetails.getMarksObtained()));
					if(entDetails.getYearPass()!=null)
						to.setYearPass(String.valueOf(entDetails.getYearPass()));
					if(entDetails.getMonthPass()!=null)
						to.setMonthPass(String.valueOf(entDetails.getMonthPass()));
					transferTos.add(to);
					
				}
				
			}
			adminAppTO.setTransferDetails(transferTos);
			log.info("exit copyTransferDetails" );
		}
		/**
		 * @param applicantLateralDetailses
		 * @param adminAppTO
		 */
		private void copyLateralDetails(
				Set<ApplicantLateralDetails> applicantLateralDetailses,
				AdmApplnTO adminAppTO) {
			log.info("enter copyLateralDetails" );
			Iterator<ApplicantLateralDetails> entItr=applicantLateralDetailses.iterator();
			adminAppTO.setLateralDetailBos(applicantLateralDetailses);
			List<ApplicantLateralDetailsTO> lateralTos= new ArrayList<ApplicantLateralDetailsTO>();
			ApplicantLateralDetailsTO to=null;
			while (entItr.hasNext()) {
				ApplicantLateralDetails entDetails = (ApplicantLateralDetails) entItr.next();
				
				if(entDetails!=null){
					to= new ApplicantLateralDetailsTO();
					to.setId(entDetails.getId());
					if(entDetails.getAdmAppln()!=null)
					to.setAdmApplnId(entDetails.getAdmAppln().getId());
					
					to.setUniversityName(entDetails.getUniversityName());
					to.setStateName(entDetails.getStateName());
					to.setInstituteAddress(entDetails.getInstituteAddress());
					to.setSemesterNo(entDetails.getSemesterNo());
					to.setSemesterName(entDetails.getSemesterName());
					if(entDetails.getMaxMarks()!=null)
					to.setMaxMarks(String.valueOf(entDetails.getMaxMarks()));
				
					if(entDetails.getMinMarks()!=null)
						to.setMinMarks(String.valueOf(entDetails.getMinMarks()));
					if(entDetails.getMarksObtained()!=null)
						to.setMarksObtained(String.valueOf(entDetails.getMarksObtained()));
					if(entDetails.getYearPass()!=null)
						to.setYearPass(String.valueOf(entDetails.getYearPass()));
					if(entDetails.getMonthPass()!=null)
						to.setMonthPass(String.valueOf(entDetails.getMonthPass()));
					lateralTos.add(to);
					
				}
				
			}
			adminAppTO.setLateralDetails(lateralTos);
			log.info("exit copyLateralDetails" );
		}
		/**
		 * @param candidateEntranceDetailses
		 * @param adminAppTO
		 */
		private void copyEntranceValue(
				Set<CandidateEntranceDetails> candidateEntranceDetailses,
				AdmApplnTO adminAppTO) {
			log.info("enter copyEntranceValue" );
			Iterator<CandidateEntranceDetails> entItr=candidateEntranceDetailses.iterator();
			CandidateEntranceDetailsTO to=null;
			while (entItr.hasNext()) {
				CandidateEntranceDetails entDetails = (CandidateEntranceDetails) entItr.next();
				
				if(entDetails!=null){
					to= new CandidateEntranceDetailsTO();
					to.setId(entDetails.getId());
					if(entDetails.getAdmAppln()!=null){
					to.setAdmApplnId(entDetails.getAdmAppln().getId());
					}
					if(entDetails.getEntrance()!=null){
						to.setEntranceId(entDetails.getEntrance().getId());
						to.setEntranceName(entDetails.getEntrance().getName());
					}
					
					to.setMonthPassing(String.valueOf(entDetails.getMonthPassing()));
					to.setYearPassing(String.valueOf(entDetails.getYearPassing()));
					to.setEntranceRollNo(entDetails.getEntranceRollNo());
					
					if(entDetails.getMarksObtained()!=null)
					to.setMarksObtained(entDetails.getMarksObtained().toString());
					if(entDetails.getTotalMarks()!=null)
					to.setTotalMarks(entDetails.getTotalMarks().toString());
					
				}
				
			}
			if(to!=null)
			{
				adminAppTO.setEntranceDetail(to);
			}
			log.info("exit copyEntranceValue" );
		}
		/**
		 * @param candidatePrerequisiteMarks
		 * @return
		 */
		/*private List<CandidatePrerequisiteMarksTO> copyPrerequisiteDetails(
				Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks) {
			log.info("enter copyPrerequisiteDetails" );
			List<CandidatePrerequisiteMarksTO> toList= new ArrayList<CandidatePrerequisiteMarksTO>();
			if(candidatePrerequisiteMarks!=null){
				Iterator<CandidatePrerequisiteMarks> candItr=candidatePrerequisiteMarks.iterator();
				while (candItr.hasNext()) {
					CandidatePrerequisiteMarks prereqBo = (CandidatePrerequisiteMarks) candItr.next();
					if(prereqBo.getIsActive()){
					CandidatePrerequisiteMarksTO to= new CandidatePrerequisiteMarksTO();
					to.setId(prereqBo.getId());
					if(prereqBo.getPrerequisite()!=null)
						to.setPrerequisiteName(prereqBo.getPrerequisite().getName());
					to.setExamMonth(String.valueOf(prereqBo.getExamMonth()));
					to.setExamYear(String.valueOf(prereqBo.getExamYear()));
					to.setPrerequisiteMarksObtained(String.valueOf(prereqBo.getPrerequisiteMarksObtained()));
					to.setPrerequisiteTotalMarks(String.valueOf(prereqBo.getPrerequisiteTotalMarks()));
					to.setRollNo(prereqBo.getRollNo());
					toList.add(to);
					}
				}
			}
			log.info("exit copyPrerequisiteDetails" );
			return toList;
		}*/
		/**
		 * @param personalDataBO
		 * @return personalDataTO
		 */
		public PersonalDataTO copyPropertiesValue(PersonalData personalData, AdmAppln admAppln) {
			log.info("enter copyPropertiesValue personal data" );
			PersonalDataTO personalDataTO = null;
			String name="";
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
//				personalDataTO.setMiddleName(personalData.getMiddleName());
//				personalDataTO.setLastName(personalData.getLastName());
				if( personalData.getDateOfBirth()!= null){
					personalDataTO.setDob(CommonUtil.getStringDate(personalData.getDateOfBirth()));
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
				/*if(personalData.getIsHostelAdmission()!= null)
					personalDataTO.setHosteladmission(personalData.getIsHostelAdmission());
				*/if(personalData.getIsNcccertificate()!=null){
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
				/*if(personalData.getDiocese()!=null){
				personalDataTO.setDioceseName(personalData.getDiocese().getName());
				personalDataTO.setDioceseId(String.valueOf(personalData.getDiocese().getId()));
				
			}
			if(personalData.getParish()!=null){
				personalDataTO.setParishName(personalData.getParish().getName());
				personalDataTO.setParishId(String.valueOf(personalData.getParish().getId()));
			}
			*/
			
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
				personalDataTO.setPermanentDistricId(OnlineApplicationHelper.OTHER);
		  }else if (personalData.getStateByParentAddressDistrictId() != null) {
				personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
				personalDataTO.setPermanentDistricId(""+personalData.getStateByParentAddressDistrictId().getId());
			}
		
			if( personalData.getCurrenttAddressDistrcictOthers()!= null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()){
				personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
				personalDataTO.setCurrentDistricId(OnlineApplicationHelper.OTHER);
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
					personalDataTO.setBirthState(OnlineApplicationHelper.OTHER);
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
					personalDataTO.setReligionId(OnlineApplicationHelper.OTHER);
					personalDataTO.setReligionOthers(personalData.getReligionOthers());
					personalDataTO.setReligionName(personalData.getReligionOthers());
				} else if (personalData.getReligion() != null) {
					personalDataTO.setReligionName(personalData.getReligion().getName());
					personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
				}
				if( personalData.getReligionSectionOthers()!=null && !personalData.getReligionSectionOthers().isEmpty()){
					personalDataTO.setSubReligionId(OnlineApplicationHelper.OTHER);
					personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
					personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
				}else if (personalData.getReligionSection() != null) {
					personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
					personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
				
				
				
				
					//raghu
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
					personalDataTO.setCasteId(OnlineApplicationHelper.OTHER);
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
//				personalDataTO.setMobileNo(personalData.getMobileNo1() + " "+ personalData.getMobileNo2() + " "+ personalData.getMobileNo3());
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
					personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
				}
				personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
				personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
				if (personalData.getCityByPermanentAddressCityId() != null) {
					personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
				}
				if (personalData.getPermanentAddressStateOthers()!= null && !personalData.getPermanentAddressStateOthers().isEmpty()){
					personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
					personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
					personalDataTO.setPermanentStateId(OnlineApplicationHelper.OTHER);
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
					personalDataTO.setCurrentStateId(OnlineApplicationHelper.OTHER);
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
				
				//raghu
				if(personalData.getCurrentStreet()!=null && !personalData.getCurrentStreet().isEmpty())
					personalDataTO.setCurrentStreet(personalData.getCurrentStreet());
				//raghu
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
					personalDataTO.setFatherOccupationId(OnlineApplicationHelper.OTHER);
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
					personalDataTO.setMotherOccupationId(OnlineApplicationHelper.OTHER);
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
					personalDataTO.setParentStateId(OnlineApplicationHelper.OTHER);
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
				
				//guardian address
				personalDataTO.setGuardianAddressLine1(personalData.getGuardianAddressLine1());
				personalDataTO.setGuardianAddressLine2(personalData.getGuardianAddressLine2());
				personalDataTO.setGuardianAddressLine3(personalData.getGuardianAddressLine3());
				if (personalData.getCityByGuardianAddressCityId() != null) {
					personalDataTO.setCityByGuardianAddressCityId(personalData.getCityByGuardianAddressCityId());
				}
				if ( personalData.getGuardianAddressStateOthers()!= null && !personalData.getGuardianAddressStateOthers().isEmpty()){
					personalDataTO.setGuardianAddressStateOthers(personalData.getGuardianAddressStateOthers());
					personalDataTO.setGuardianStateName(personalData.getGuardianAddressStateOthers());
					personalDataTO.setStateByGuardianAddressStateId(OnlineApplicationHelper.OTHER);
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
				
				if(personalData.getMbaEntranceExam() != null) {
					personalDataTO.setMbaEntranceExamId(String.valueOf(personalData.getMbaEntranceExam().getId()));
					personalDataTO.setEntranceMarksSecured(String.valueOf(personalData.getEntranceMarksSecured()));
				}
			}
			
			log.info("exit copyPropertiesValue personal data" );
			return personalDataTO;
		}
			/**
			 * @param courseBO
			 * @return courseTO
			 */
			public CourseTO copyPropertiesValue(Course course) {
				log.info("enter copyPropertiesValue course" );
				CourseTO courseTO = null;

				if (course != null) {
					courseTO = new CourseTO();
					courseTO.setId(course.getId());
					courseTO.setName(course.getName());
					courseTO.setProgramId(course.getProgram().getId());
					course.getProgram().getId();
					courseTO.setCode(course.getCode());
					if(course.getProgram()!=null){
						courseTO.setProgramCode(course.getProgram().getName());
						courseTO.setProgramId(course.getProgram().getId());
						courseTO.setProgramName(course.getProgram().getName());
					}
					if(course.getProgram() != null && course.getProgram().getProgramType()!=null){
						courseTO.setProgramTypeCode(course.getProgram().getProgramType().getName());
						courseTO.setProgramTypeId(course.getProgram().getProgramType().getId());
					}
					if (course.getIsWorkExperienceRequired().equals(true)) {
						courseTO.setIsWorkExperienceRequired("Yes");
					} else {
						courseTO.setIsWorkExperienceRequired("No");
					}
					
					if(course.getIsDetailMarksPrint()!=null && course.getIsDetailMarksPrint()){
						courseTO.setIsDetailMarkPrint("Yes");
					}else{
						courseTO.setIsDetailMarkPrint("No");
					}
				}
				log.info("exit copyPropertiesValue course" );
				return courseTO;
				
			}
			/**
			 * @param applicantWorkExperiences
			 * @param workExpList
			 * @param workExpNeed
			 * @return
			 */
			/*private List<ApplicantWorkExperienceTO> copyWorkExpValue(
					Set<ApplicantWorkExperience> applicantWorkExperiences, List<ApplicantWorkExperienceTO> workExpList, boolean workExpNeed) {
				log.info("enter copyWorkExpValue" );
				if (workExpNeed) {
					if (applicantWorkExperiences != null
							&& !applicantWorkExperiences.isEmpty()) {
						Iterator<ApplicantWorkExperience> expItr = applicantWorkExperiences
								.iterator();
						while (expItr.hasNext()) {
							ApplicantWorkExperience workExp = (ApplicantWorkExperience) expItr.next();
							ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
							expTo.setId(workExp.getId());
							if (workExp.getFromDate() != null)
								expTo.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getFromDate()),OnlineApplicationHelper.SQL_DATEFORMAT, OnlineApplicationHelper.FROM_DATEFORMAT));
							if (workExp.getToDate() != null)
								expTo.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getToDate()),OnlineApplicationHelper.SQL_DATEFORMAT, OnlineApplicationHelper.FROM_DATEFORMAT));
							if(workExp.getAdmApplnId()!=null)
							expTo.setAdmApplnId(workExp.getAdmApplnId().getId());
							expTo.setOrganization(workExp.getOrganization());
							expTo.setPosition(workExp.getPosition());
							expTo.setIsCurrent(workExp.getIsCurrent());
							if(workExp.getSalary()!=null)
							expTo.setSalary(String.valueOf(workExp.getSalary()));
							expTo.setReportingTo(workExp.getReportingTo());
							expTo.setPhoneNo(workExp.getPhoneNo());
							expTo.setAddress(workExp.getAddress());
							if(workExp.getOccupation()!=null){
								expTo.setOccupationId(Integer.toString(workExp.getOccupation().getId()));
							}else{
								if(workExp.getPosition()!=null)
								expTo.setOccupationId("Other");
							}
							workExpList.add(expTo);
						}
					}else{
						for(int i=0;i<CMSConstants.MAX_CANDIDATE_WORKEXP;i++){
							ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
							workExpList.add(expTo);
						}
					}
				}
				log.info("exit copyWorkExpValue" );
				return workExpList;
			}*/


			/**
			 * @param appliedYear 
			 * @param selectedCourse 
			 * @param qualificationSetBO
			 * @return List ednQualificationListTO
			 */
			public List<EdnQualificationTO> copyPropertiesValue(Set<EdnQualification> qualificationSet, CourseTO selectedCourse, Integer appliedYear) throws Exception {
				log.info("enter copyPropertiesValue ednqualification" );
				List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
				EdnQualificationTO ednQualificationTO = null;
				IOnlineApplicationTxn txn= new OnlineApplicationImpl();
				List<DocChecklist> exambos= txn.getExamtypes(selectedCourse.getId(),appliedYear);
				int sizediff=0;
				//doctype ids already assigned
				List<Integer> presentIds= new ArrayList<Integer>();
				List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
				if (qualificationSet != null && !qualificationSet.isEmpty() ) {
					if(exambos!=null)
						sizediff=exambos.size()-qualificationSet.size();
					Iterator<EdnQualification> iterator = qualificationSet.iterator();
					while (iterator.hasNext()) {
						EdnQualification ednQualificationBO = iterator.next();

						ednQualificationTO = new EdnQualificationTO();
						ednQualificationTO.setId(ednQualificationBO.getId());
						ednQualificationTO.setCreatedBy(ednQualificationBO.getCreatedBy());
						ednQualificationTO.setCreatedDate(ednQualificationBO.getCreatedDate());
						if(ednQualificationBO.getState()!=null){
							ednQualificationTO.setStateId(String.valueOf(ednQualificationBO.getState().getId()));
							ednQualificationTO.setStateName(ednQualificationBO.getState().getName());
						}else if(ednQualificationBO.getIsOutsideIndia()!=null && ednQualificationBO.getIsOutsideIndia()){
							ednQualificationTO.setStateId(CMSConstants.OUTSIDE_INDIA);
							ednQualificationTO.setStateName(CMSConstants.OUTSIDE_INDIA);
						}
						//copy doc type exam
						if(ednQualificationBO.getDocTypeExams()!=null)
						{
							ednQualificationTO.setSelectedExamId(String.valueOf(ednQualificationBO.getDocTypeExams().getId()));
							if(ednQualificationBO.getDocTypeExams().getName()!=null)
							ednQualificationTO.setSelectedExamName(String.valueOf(ednQualificationBO.getDocTypeExams().getName()));
						}
						if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getDocType()!=null){
							AdmissionFormHandler handler= AdmissionFormHandler.getInstance();
							List<DocTypeExamsTO> examtos=handler.getDocExamsByID(ednQualificationBO.getDocChecklist().getDocType().getId());
							ednQualificationTO.setExamTos(examtos);
							if(examtos!=null && !examtos.isEmpty())
								ednQualificationTO.setExamRequired(true);
						}
						
						if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getIsPreviousExam())
							ednQualificationTO.setLastExam(true);
						if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getIsExamRequired())
							ednQualificationTO.setExamConfigured(true);
						if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getDocType()!=null){
							ednQualificationTO.setDocCheckListId(ednQualificationBO.getDocChecklist().getId());
							presentIds.add(ednQualificationBO.getDocChecklist().getDocType().getId());
							ednQualificationTO.setDocName(ednQualificationBO.getDocChecklist().getDocType().getName());
							ednQualificationTO.setDocTypeId(ednQualificationBO.getDocChecklist().getDocType().getId());
							ednQualificationTO.setSemesterWise(false);
							ednQualificationTO.setConsolidated(true);
							if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getIsActive()==true && ednQualificationBO.getDocChecklist().getIsMarksCard()==true &&  ednQualificationBO.getDocChecklist().getIsConsolidatedMarks()==false && ednQualificationBO.getDocChecklist().getIsSemesterWise()==true){
								ednQualificationTO.setLanguage(ednQualificationBO.getDocChecklist().getIsIncludeLanguage());
								ednQualificationTO.setSemesterWise(true);
								ednQualificationTO.setConsolidated(false);
								Set<ApplicantMarksDetails> detailMarks=ednQualificationBO.getApplicantMarksDetailses();
								if(detailMarks!=null && !detailMarks.isEmpty())
								{
//									Set<ApplicantMarkDetailsTO> markdetails= new TreeSet<ApplicantMarkDetailsTO>(new SemesterComparator());
									Set<ApplicantMarkDetailsTO> markdetails= new HashSet<ApplicantMarkDetailsTO>();
									ApplicantMarksDetails detailMarkBO=null;
									Iterator<ApplicantMarksDetails> markItr=detailMarks.iterator();
									while (markItr.hasNext()) {
										detailMarkBO= (ApplicantMarksDetails) markItr.next();
										ApplicantMarkDetailsTO markTO= new ApplicantMarkDetailsTO();
										// converts semester marks to to
										convertApplicantMarkBOtoTO(detailMarkBO,markTO,ednQualificationTO.isLastExam());
										markdetails.add(markTO);
									}
									if(markdetails!=null && !markdetails.isEmpty()){
										List<ApplicantMarkDetailsTO> appMarks=new ArrayList<ApplicantMarkDetailsTO>(markdetails);
										Collections.sort(appMarks);
										ednQualificationTO.setSemesterList(markdetails);
										ednQualificationTO.setSemesters(appMarks);
									}
									else{
									
										for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
											ApplicantMarkDetailsTO to=new ApplicantMarkDetailsTO();
											to.setSemesterNo(i);
											to.setLastExam(ednQualificationTO.isLastExam());
											markdetails.add(to);
										}
										ednQualificationTO.setSemesterList(markdetails);
										List<ApplicantMarkDetailsTO> appMarks=new ArrayList<ApplicantMarkDetailsTO>(markdetails);
										Collections.sort(appMarks);
										ednQualificationTO.setSemesters(appMarks);
									}
								}else{
									Set<ApplicantMarkDetailsTO> markdetails= new HashSet<ApplicantMarkDetailsTO>();
									for(int i=1;i<=CMSConstants.MAX_CANDIDATE_SEMESTERS;i++){
										ApplicantMarkDetailsTO to=new ApplicantMarkDetailsTO();
										to.setSemesterNo(i);
										to.setSemesterName("Semester"+i);
										to.setLastExam(ednQualificationTO.isLastExam());
										markdetails.add(to);
									}
									ednQualificationTO.setSemesterList(markdetails);
								}
							}else if(ednQualificationBO.getDocChecklist()!=null&& ednQualificationBO.getDocChecklist().getIsActive()==true && ednQualificationBO.getDocChecklist().getIsMarksCard()==true && ednQualificationBO.getDocChecklist().getIsConsolidatedMarks()==false && ednQualificationBO.getDocChecklist().getIsSemesterWise()==false){
								ednQualificationTO.setConsolidated(false);
								Set<CandidateMarks> detailMarks=ednQualificationBO.getCandidateMarkses();
								if(detailMarks!=null && !detailMarks.isEmpty())
								{
									CandidateMarks detailMarkBO=null;
									Iterator<CandidateMarks> markItr=detailMarks.iterator();
									while (markItr.hasNext()) {
										detailMarkBO= (CandidateMarks) markItr.next();
										CandidateMarkTO markTO= new CandidateMarkTO();
										//convertDetailMarkBOtoTO(detailMarkBO,markTO);
										//raghu
										convertDetailMarkBOtoTO(detailMarkBO,markTO,ednQualificationBO);
										ednQualificationTO.setDetailmark(markTO);
										
									}
								}else{
									ednQualificationTO.setDetailmark(null);
								}
							}
						}
						if(ednQualificationBO.getUniversityOthers()!= null && !ednQualificationBO.getUniversityOthers().isEmpty()){
							ednQualificationTO.setUniversityId(OnlineApplicationHelper.OTHER);
							ednQualificationTO.setUniversityOthers(ednQualificationBO.getUniversityOthers());
							ednQualificationTO.setUniversityName(ednQualificationBO.getUniversityOthers());
						}else if(ednQualificationBO.getUniversity()!= null){
							ednQualificationTO.setUniversityId(String.valueOf(ednQualificationBO.getUniversity().getId()));
							ednQualificationTO.setUniversityName(ednQualificationBO.getUniversity().getName());
						}
						if(ednQualificationBO.getInstitutionNameOthers()!= null && !ednQualificationBO.getInstitutionNameOthers().isEmpty()){
							ednQualificationTO.setInstitutionId(OnlineApplicationHelper.OTHER);
							ednQualificationTO.setInstitutionName(ednQualificationBO.getInstitutionNameOthers());
							ednQualificationTO.setOtherInstitute(ednQualificationBO.getInstitutionNameOthers());
						}else if(ednQualificationBO.getCollege()!= null){
							ednQualificationTO.setInstitutionId(String.valueOf(ednQualificationBO.getCollege().getId()));
							ednQualificationTO.setInstitutionName(ednQualificationBO.getCollege().getName());
						}
						if(ednQualificationBO.getYearPassing()!=null)
						ednQualificationTO.setYearPassing(ednQualificationBO.getYearPassing());
						if(ednQualificationBO.getMonthPassing()!=null)
						ednQualificationTO.setMonthPassing(ednQualificationBO.getMonthPassing());
						ednQualificationTO.setPreviousRegNo(ednQualificationBO.getPreviousRegNo());
						
						//here we are storing only percentage raghu
						/*float obtainmrk=0.0f;
						float totmrk=0.0f;
						
						if( ednQualificationBO.getMarksObtained()!= null){
							ednQualificationTO.setMarksObtained(String.valueOf(ednQualificationBO.getMarksObtained().doubleValue()));
							obtainmrk=ednQualificationBO.getMarksObtained().floatValue();
						}
						if(ednQualificationBO.getTotalMarks()!=null){
							ednQualificationTO.setTotalMarks(String.valueOf(ednQualificationBO.getTotalMarks().doubleValue()));
							totmrk=ednQualificationBO.getTotalMarks().floatValue();
						}
						if(totmrk!=0.0){
							float perc=(obtainmrk/totmrk)*100;
							ednQualificationTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(perc, 2)));
						}else{
							ednQualificationTO.setPercentage(String.valueOf(ednQualificationBO.getPercentage()));
						}*/
						
						ednQualificationTO.setPercentage(String.valueOf(ednQualificationBO.getPercentage()));
						
						if(ednQualificationBO.getNoOfAttempts()!=null)
						ednQualificationTO.setNoOfAttempts(ednQualificationBO.getNoOfAttempts());
						if( universityList != null && !universityList.isEmpty()){
							if( universityList != null  && ednQualificationTO.getUniversityId()!=null && !ednQualificationTO.getUniversityId().equalsIgnoreCase(OnlineApplicationHelper.OTHER)){
								ednQualificationTO.setUniversityList(universityList);
								if(ednQualificationTO.getInstitutionId()!=null && !ednQualificationTO.getInstitutionId().equalsIgnoreCase(OnlineApplicationHelper.OTHER)){
									for (UniversityTO unTO : universityList) {
										if(unTO.getId()== Integer.parseInt(ednQualificationTO.getUniversityId()))
										{
											ednQualificationTO.setInstituteList(unTO.getCollegeTos());
										}
									}
								}
							}
							
						}
						ednQualificationList.add(ednQualificationTO);
					}

					// add extra Tos
					if(sizediff>0){
						// extra checklist configured
						List<DocChecklist> extraList= new ArrayList<DocChecklist>();
						extraList.addAll(exambos);
						if(!extraList.isEmpty()){
							Iterator<DocChecklist> docItr=extraList.iterator();
							while (docItr.hasNext()) {
								DocChecklist doc = (DocChecklist) docItr.next();
								if(doc.getDocType()!=null && doc.getDocType().getId()!=0)
								{
									// filter old ones
									if(presentIds.contains(doc.getDocType().getId()))
										docItr.remove();
								}
							}
						}
						// add the new items
						List<EdnQualificationTO> newItems=this.prepareQualificationsFromExamBos(extraList);
						
						if (newItems != null && !newItems.isEmpty() ) {
							for (EdnQualificationTO ednTO : newItems) {
								if( universityList != null && !universityList.isEmpty()){
									ednTO.setUniversityList(universityList);
									ednTO.setInstituteList(new ArrayList<CollegeTO>());
								}
							}
						}
						ednQualificationList.addAll(newItems);
						
						
					}
				}else{
					
					ednQualificationList=this.prepareQualificationsFromExamBos(exambos);
					
					if (ednQualificationList != null && !ednQualificationList.isEmpty() ) {
						for (EdnQualificationTO ednTO : ednQualificationList) {
							if( universityList != null && !universityList.isEmpty()){
								ednTO.setUniversityList(universityList);
								ednTO.setInstituteList(new ArrayList<CollegeTO>());
							}
						}
						
					}
				}
				Collections.sort(ednQualificationList);
				log.info("exit copyPropertiesValue ednqualification" );
				return ednQualificationList;
			}
			/**
			 * @param detailMarkBO
			 * @param markTO
			 * @param lastExam
			 */
			private void convertApplicantMarkBOtoTO(ApplicantMarksDetails detailMarkBO,
					ApplicantMarkDetailsTO markTO, boolean lastExam) {
				log.info("enter convertApplicantMarkBOtoTO" );
				if(detailMarkBO!=null){
					markTO.setId(detailMarkBO.getId());
					if(detailMarkBO.getEdnQualification()!=null)
					markTO.setEdnQualificationId(detailMarkBO.getEdnQualification().getId());
					markTO.setLastExam(lastExam);
					markTO.setMarksObtained(String.valueOf(detailMarkBO.getMarksObtained()));
					markTO.setMaxMarks(String.valueOf(detailMarkBO.getMaxMarks()));
					markTO.setSemesterName(detailMarkBO.getSemesterName());
					markTO.setSemesterNo(detailMarkBO.getSemesterNo());
					if(detailMarkBO.getMarksObtainedLanguagewise()!=null)
					markTO.setMarksObtained_languagewise(String.valueOf(detailMarkBO.getMarksObtainedLanguagewise()));
					if(detailMarkBO.getMaxMarksLanguagewise()!=null)
					markTO.setMaxMarks_languagewise(String.valueOf(detailMarkBO.getMaxMarksLanguagewise()));
				}
				log.info("exit convertApplicantMarkBOtoTO" );
			}

			/**
			 * @param detailMarkBO
			 * @param markTO
			 * @throws Exception 
			 */
			//raghu this is for edit
			public void convertDetailMarkBOtoTO(CandidateMarks detailMarkBO,
					CandidateMarkTO markTO,EdnQualification ednQualificationBO) throws Exception {
				log.info("enter convertDetailMarkBOtoTO" );
				if(detailMarkBO!=null){
					
					if(detailMarkBO.getDetailedSubjects1() != null) {
						DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
						detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects1().getId());
						markTO.setSubject1(detailMarkBO.getDetailedSubjects1().getSubjectName());
						markTO.setSubject1Mandatory(true);
						markTO.setDetailedSubjects1(detailedSubjectsTO);
					} else if(detailMarkBO.getSubject1() != null && detailMarkBO.getSubject1().length() !=0){
						// setting others.	
						DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
						detailedSubjectsTO.setId(-1);
						markTO.setDetailedSubjects1(detailedSubjectsTO);
						markTO.setSubject1(detailMarkBO.getSubject1());
					}
					
					
					if(detailMarkBO.getId()!=0)
					markTO.setId(detailMarkBO.getId());
					markTO.setCreatedBy(detailMarkBO.getCreatedBy());
					markTO.setCreatedDate(detailMarkBO.getCreatedDate());
					
					//class 12
					int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
					
					
					if(ednQualificationBO.getDocChecklist().getDocType().getId()==doctypeId){
						
					        
						 setDetailMarkSubjectBOtoTO(detailMarkBO, markTO, ednQualificationBO);
					        
						if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().floatValue()!=0)
						markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
						if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().floatValue()!=0)
						markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
								
						
						String language="Language";
						Map<Integer, String> admsubjectLangMap;
						
							admsubjectLangMap = AdmissionFormHandler.getInstance().get12thclassLangSubjects(ednQualificationBO.getDocChecklist().getDocType().getName(),language);
							//find id from english in Map
					        String strValue="English";
					        String intKey = null;
					        for(Map.Entry entry: admsubjectLangMap.entrySet()){
					            if(strValue.equals(entry.getValue())){
					            	intKey =entry.getKey().toString();
					            	if(detailMarkBO.getSubjectid1()!=null && !detailMarkBO.getSubjectid1().equalsIgnoreCase("")){
					            	intKey=detailMarkBO.getSubjectid1();
					            	}
					            	markTO.setSubjectid1(intKey);
					                break; //breaking because its one to one map
					            }
					        }
					}
				
					//deg
					int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
					
					
					//if entry comes from fresh educatuin details or for edit, we hav to check PatternofStudy
					
					if(ednQualificationBO.getDocChecklist().getDocType().getId()==doctypeId1 && ednQualificationBO.getUgPattern()!=null){
						

							
						
							setDetailMarkSubjectBOtoTO(detailMarkBO, markTO, ednQualificationBO);
							
							if(ednQualificationBO.getUgPattern().equalsIgnoreCase("CBCSS") || ednQualificationBO.getUgPattern().equalsIgnoreCase("CBCSS NEW")){
								if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().floatValue()!=0)
								markTO.setTotalMarksCGPA(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
								if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().floatValue()!=0)
								markTO.setTotalObtainedMarksCGPA(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
									
							}else{
								if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().floatValue()!=0)
								markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
								if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().floatValue()!=0)
								markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
									
							}
							
							//String Core="Core";
							//String Compl="Complementary";
							String Common="Common";
							//String Open="Open";
							//String Sub="Sub";
							
								//Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Core);
								//Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Compl);
								Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationBO.getDocChecklist().getDocType().getName(),Common);
								//Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Open);
								//Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(docNmae,Common);
								
								
								//find id from english in Map
								String strValue="English";
								String intKey = null;
								for(Map.Entry entry: admCommonMap.entrySet()){
								 if(strValue.equals(entry.getValue())){
									intKey =entry.getKey().toString();
									if(detailMarkBO.getSubjectid6()!=null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("")){
						            	intKey=detailMarkBO.getSubjectid6();
						            	}
									markTO.setSubjectid6(intKey);
									
									intKey =entry.getKey().toString();
									if(detailMarkBO.getSubjectid16()!=null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("")){
						            	intKey=detailMarkBO.getSubjectid16();
						            	}
									markTO.setSubjectid16(intKey);
									break; //breaking because its one to one map
								 	}
								}
							
							
								
					}else if(ednQualificationBO.getDocChecklist().getDocType().getId()==doctypeId1 ){
						

						
						
							
						//String Core="Core";
						//String Compl="Complementary";
						String Common="Common";
						//String Open="Open";
						//String Sub="Sub";
						
							//Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Core);
							//Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Compl);
							Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(ednQualificationBO.getDocChecklist().getDocType().getName(),Common);
							//Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Open);
							//Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(docNmae,Common);
							
							
							//find id from english in Map
							String strValue="English";
							String intKey = null;
							for(Map.Entry entry: admCommonMap.entrySet()){
							 if(strValue.equals(entry.getValue())){
								intKey =entry.getKey().toString();
								if(detailMarkBO.getSubjectid6()!=null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("")){
					            	intKey=detailMarkBO.getSubjectid6();
					            	}
								markTO.setSubjectid6(intKey);
								
								intKey =entry.getKey().toString();
								if(detailMarkBO.getSubjectid16()!=null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("")){
					            	intKey=detailMarkBO.getSubjectid16();
					            	}
								markTO.setSubjectid16(intKey);
								break; //breaking because its one to one map
							 	}
							}
						
						
							
				}
					
					
					
					if(ednQualificationBO.getDocChecklist().getDocType().getId()!=doctypeId && ednQualificationBO.getDocChecklist().getDocType().getId()!=doctypeId1){
						
						markTO.setSubject1(detailMarkBO.getSubject1());
						markTO.setSubject2(detailMarkBO.getSubject2());
						markTO.setSubject3(detailMarkBO.getSubject3());
						markTO.setSubject4(detailMarkBO.getSubject4());
						markTO.setSubject5(detailMarkBO.getSubject5());
						markTO.setSubject6(detailMarkBO.getSubject6());
						markTO.setSubject7(detailMarkBO.getSubject7());
						markTO.setSubject8(detailMarkBO.getSubject8());
						markTO.setSubject9(detailMarkBO.getSubject9());
						markTO.setSubject10(detailMarkBO.getSubject10());
						markTO.setSubject11(detailMarkBO.getSubject11());
						markTO.setSubject12(detailMarkBO.getSubject12());
						markTO.setSubject13(detailMarkBO.getSubject13());
						markTO.setSubject14(detailMarkBO.getSubject14());
						markTO.setSubject15(detailMarkBO.getSubject15());
						markTO.setSubject16(detailMarkBO.getSubject16());
						markTO.setSubject17(detailMarkBO.getSubject17());
						markTO.setSubject18(detailMarkBO.getSubject18());
						markTO.setSubject19(detailMarkBO.getSubject19());
						markTO.setSubject20(detailMarkBO.getSubject20());

						
						
					
					if(detailMarkBO.getSubject1TotalMarks()!=null && detailMarkBO.getSubject1TotalMarks().intValue()!=0)
						markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO.getSubject1TotalMarks().intValue()));
					if(detailMarkBO.getSubject2TotalMarks()!=null && detailMarkBO.getSubject2TotalMarks().intValue()!=0)
					markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO.getSubject2TotalMarks().intValue()));
					if(detailMarkBO.getSubject3TotalMarks()!=null && detailMarkBO.getSubject3TotalMarks().intValue()!=0)
					markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO.getSubject3TotalMarks().intValue()));
					if(detailMarkBO.getSubject4TotalMarks()!=null && detailMarkBO.getSubject4TotalMarks().intValue()!=0)
					markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO.getSubject4TotalMarks().intValue()));
					if(detailMarkBO.getSubject5TotalMarks()!=null && detailMarkBO.getSubject5TotalMarks().intValue()!=0)
					markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO.getSubject5TotalMarks().intValue()));
					if(detailMarkBO.getSubject6TotalMarks()!=null && detailMarkBO.getSubject6TotalMarks().intValue()!=0)
					markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO.getSubject6TotalMarks().intValue()));
					if(detailMarkBO.getSubject7TotalMarks()!=null && detailMarkBO.getSubject7TotalMarks().intValue()!=0)
					markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO.getSubject7TotalMarks().intValue()));
					if(detailMarkBO.getSubject8TotalMarks()!=null && detailMarkBO.getSubject8TotalMarks().intValue()!=0)
					markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO.getSubject8TotalMarks().intValue()));
					if(detailMarkBO.getSubject9TotalMarks()!=null && detailMarkBO.getSubject9TotalMarks().intValue()!=0)
					markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO.getSubject9TotalMarks().intValue()));
					if(detailMarkBO.getSubject10TotalMarks()!=null && detailMarkBO.getSubject10TotalMarks().intValue()!=0)
					markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO.getSubject10TotalMarks().intValue()));
					if(detailMarkBO.getSubject11TotalMarks()!=null && detailMarkBO.getSubject11TotalMarks().intValue()!=0)
					markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO.getSubject11TotalMarks().intValue()));
					if(detailMarkBO.getSubject12TotalMarks()!=null && detailMarkBO.getSubject12TotalMarks().intValue()!=0)
					markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO.getSubject12TotalMarks().intValue()));
					if(detailMarkBO.getSubject13TotalMarks()!=null && detailMarkBO.getSubject13TotalMarks().intValue()!=0)
					markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO.getSubject13TotalMarks().intValue()));
					if(detailMarkBO.getSubject14TotalMarks()!=null && detailMarkBO.getSubject14TotalMarks().intValue()!=0)
					markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO.getSubject14TotalMarks().intValue()));
					if(detailMarkBO.getSubject15TotalMarks()!=null && detailMarkBO.getSubject15TotalMarks().intValue()!=0)
					markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO.getSubject15TotalMarks().intValue()));
					if(detailMarkBO.getSubject16TotalMarks()!=null && detailMarkBO.getSubject16TotalMarks().intValue()!=0)
					markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO.getSubject16TotalMarks().intValue()));
					if(detailMarkBO.getSubject17TotalMarks()!=null && detailMarkBO.getSubject17TotalMarks().intValue()!=0)
					markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO.getSubject17TotalMarks().intValue()));
					if(detailMarkBO.getSubject18TotalMarks()!=null && detailMarkBO.getSubject18TotalMarks().intValue()!=0)
					markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO.getSubject18TotalMarks().intValue()));
					if(detailMarkBO.getSubject19TotalMarks()!=null && detailMarkBO.getSubject19TotalMarks().intValue()!=0)
					markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO.getSubject19TotalMarks().intValue()));
					if(detailMarkBO.getSubject20TotalMarks()!=null && detailMarkBO.getSubject20TotalMarks().intValue()!=0)
					markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO.getSubject20TotalMarks().intValue()));
					
					if(detailMarkBO.getSubject1ObtainedMarks()!=null && detailMarkBO.getSubject1ObtainedMarks().intValue()!=0)
					markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO.getSubject1ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject2ObtainedMarks()!=null && detailMarkBO.getSubject2ObtainedMarks().intValue()!=0)
					markTO.setSubject2ObtainedMarks(String.valueOf(	detailMarkBO.getSubject2ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject3ObtainedMarks()!=null && detailMarkBO.getSubject3ObtainedMarks().intValue()!=0)
					markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO.getSubject3ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject4ObtainedMarks()!=null && detailMarkBO.getSubject4ObtainedMarks().intValue()!=0)
					markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO.getSubject4ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject5ObtainedMarks()!=null && detailMarkBO.getSubject5ObtainedMarks().intValue()!=0)
					markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO.getSubject5ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject6ObtainedMarks()!=null && detailMarkBO.getSubject6ObtainedMarks().intValue()!=0)
					markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO.getSubject6ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject7ObtainedMarks()!=null && detailMarkBO.getSubject7ObtainedMarks().intValue()!=0)
					markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO.getSubject7ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject8ObtainedMarks()!=null && detailMarkBO.getSubject8ObtainedMarks().intValue()!=0)
					markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO.getSubject8ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject9ObtainedMarks()!=null && detailMarkBO.getSubject9ObtainedMarks().intValue()!=0)
					markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO.getSubject9ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject10ObtainedMarks()!=null && detailMarkBO.getSubject10ObtainedMarks().intValue()!=0)
					markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO.getSubject10ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject11ObtainedMarks()!=null && detailMarkBO.getSubject11ObtainedMarks().intValue()!=0)
					markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO.getSubject11ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject12ObtainedMarks()!=null && detailMarkBO.getSubject12ObtainedMarks().intValue()!=0)
					markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO.getSubject12ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject13ObtainedMarks()!=null && detailMarkBO.getSubject13ObtainedMarks().intValue()!=0)
					markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO.getSubject13ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject14ObtainedMarks()!=null && detailMarkBO.getSubject14ObtainedMarks().intValue()!=0)
					markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO.getSubject14ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject15ObtainedMarks()!=null && detailMarkBO.getSubject15ObtainedMarks().intValue()!=0)
					markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO.getSubject15ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject16ObtainedMarks()!=null && detailMarkBO.getSubject16ObtainedMarks().intValue()!=0)
					markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO.getSubject16ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject17ObtainedMarks()!=null && detailMarkBO.getSubject17ObtainedMarks().intValue()!=0)
					markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO.getSubject17ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject18ObtainedMarks()!=null && detailMarkBO.getSubject18ObtainedMarks().intValue()!=0)
					markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO.getSubject18ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject19ObtainedMarks()!=null && detailMarkBO.getSubject19ObtainedMarks().intValue()!=0)
					markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO.getSubject19ObtainedMarks().intValue()));
					if(detailMarkBO.getSubject20ObtainedMarks()!=null && detailMarkBO.getSubject20ObtainedMarks().intValue()!=0)
					markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO.getSubject20ObtainedMarks().intValue()));
					
					//set grand total
					if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().intValue()!=0)
					markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().intValue()));
					if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().intValue()!=0)
					markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().intValue()));
					
					}
					
					
					
					
					
					}
				log.info("exit convertDetailMarkBOtoTO" );
			}
			/**
			 * @param preferencesSetBO
			 * @return 
			 */
			
			
			//raghu for edit subjectrank id set to detailmark subjectrank id
			public void setDetailMarkSubjectBOtoTO(CandidateMarks detailMarkBO,
					CandidateMarkTO markTO,EdnQualification ednQualificationBO)  {
				log.info("enter convertDetailMarkBOtoTO" );
				
				
				Set<AdmSubjectMarkForRank> submarkListClass=new HashSet<AdmSubjectMarkForRank>();
				submarkListClass=ednQualificationBO.getAdmSubjectMarkForRank();
				
				if(submarkListClass.size()!=0)
				{
				Iterator<AdmSubjectMarkForRank> itr=submarkListClass.iterator();
			 
			    while(itr.hasNext())
			    {
			    AdmSubjectMarkForRank admSubjectMarkForRank=(AdmSubjectMarkForRank) itr.next();
			    //here code overriding for duplicate subjects
			    if(detailMarkBO.getSubjectid1()!=null && !detailMarkBO.getSubjectid1().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid1())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid1(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject1(detailMarkBO.getSubject1());
			    	markTO.setSubjectOther1(detailMarkBO.getSubjectOther1());
			    	if(detailMarkBO.getSubject1Credit()!=null)
					markTO.setSubject1Credit(detailMarkBO.getSubject1Credit().toString());
			    	if(detailMarkBO.getSubject1TotalMarks()!=null && detailMarkBO.getSubject1TotalMarks().floatValue()!=0)
					markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO.getSubject1TotalMarks().floatValue()));
					if(detailMarkBO.getSubject1ObtainedMarks()!=null && detailMarkBO.getSubject1ObtainedMarks().floatValue()!=0)
					markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO.getSubject1ObtainedMarks().floatValue()));
					markTO.setSubjectid1(detailMarkBO.getSubjectid1());
					
			    }
			    else  if(detailMarkBO.getSubjectid2()!=null && !detailMarkBO.getSubjectid2().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid2())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid2(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject2(detailMarkBO.getSubject2());
			    	markTO.setSubjectOther2(detailMarkBO.getSubjectOther2());
			    	if(detailMarkBO.getSubject2Credit()!=null)
						markTO.setSubject2Credit(detailMarkBO.getSubject2Credit().toString());
			    	if(detailMarkBO.getSubject2TotalMarks()!=null && detailMarkBO.getSubject2TotalMarks().floatValue()!=0)
					markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO.getSubject2TotalMarks().floatValue()));
					if(detailMarkBO.getSubject2ObtainedMarks()!=null && detailMarkBO.getSubject2ObtainedMarks().floatValue()!=0)
					markTO.setSubject2ObtainedMarks(String.valueOf(detailMarkBO.getSubject2ObtainedMarks().floatValue()));
					markTO.setSubjectid2(detailMarkBO.getSubjectid2());
				
			    }
			    else  if(detailMarkBO.getSubjectid3()!=null && !detailMarkBO.getSubjectid3().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid3())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid3(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject3(detailMarkBO.getSubject3());
			    	markTO.setSubjectOther3(detailMarkBO.getSubjectOther3());
			    	if(detailMarkBO.getSubject3Credit()!=null)
						markTO.setSubject3Credit(detailMarkBO.getSubject3Credit().toString());
			    	if(detailMarkBO.getSubject3TotalMarks()!=null && detailMarkBO.getSubject3TotalMarks().floatValue()!=0)
					markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO.getSubject3TotalMarks().floatValue()));
					if(detailMarkBO.getSubject3ObtainedMarks()!=null && detailMarkBO.getSubject3ObtainedMarks().floatValue()!=0)
					markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO.getSubject3ObtainedMarks().floatValue()));
					markTO.setSubjectid3(detailMarkBO.getSubjectid3());
				
			    }
			    else  if(detailMarkBO.getSubjectid4()!=null && !detailMarkBO.getSubjectid4().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid4())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid4(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject4(detailMarkBO.getSubject4());
			    	markTO.setSubjectOther4(detailMarkBO.getSubjectOther4());
			    	if(detailMarkBO.getSubject4Credit()!=null)
						markTO.setSubject4Credit(detailMarkBO.getSubject4Credit().toString());
			    	if(detailMarkBO.getSubject4TotalMarks()!=null && detailMarkBO.getSubject4TotalMarks().floatValue()!=0)
					markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO.getSubject4TotalMarks().floatValue()));
					if(detailMarkBO.getSubject4ObtainedMarks()!=null && detailMarkBO.getSubject4ObtainedMarks().floatValue()!=0)
					markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO.getSubject4ObtainedMarks().floatValue()));
					markTO.setSubjectid4(detailMarkBO.getSubjectid4());
				
			    }
			    else  if(detailMarkBO.getSubjectid5()!=null && !detailMarkBO.getSubjectid5().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid5())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid5(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject5(detailMarkBO.getSubject5());
			    	markTO.setSubjectOther5(detailMarkBO.getSubjectOther5());
			    	if(detailMarkBO.getSubject5Credit()!=null)
						markTO.setSubject5Credit(detailMarkBO.getSubject5Credit().toString());
			    	if(detailMarkBO.getSubject5TotalMarks()!=null && detailMarkBO.getSubject5TotalMarks().floatValue()!=0)
					markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO.getSubject5TotalMarks().floatValue()));
					if(detailMarkBO.getSubject5ObtainedMarks()!=null && detailMarkBO.getSubject5ObtainedMarks().floatValue()!=0)
					markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO.getSubject5ObtainedMarks().floatValue()));
					markTO.setSubjectid5(detailMarkBO.getSubjectid5());
				
			    }
			    else  if(detailMarkBO.getSubjectid6()!=null && !detailMarkBO.getSubjectid6().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid6())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid6(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject6(detailMarkBO.getSubject6());
			    	markTO.setSubjectOther6(detailMarkBO.getSubjectOther6());
			    	if(detailMarkBO.getSubject6Credit()!=null)
						markTO.setSubject6Credit(detailMarkBO.getSubject6Credit().toString());
			    	if(detailMarkBO.getSubject6TotalMarks()!=null && detailMarkBO.getSubject6TotalMarks().floatValue()!=0)
					markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO.getSubject6TotalMarks().floatValue()));
					if(detailMarkBO.getSubject6ObtainedMarks()!=null && detailMarkBO.getSubject6ObtainedMarks().floatValue()!=0)
					markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO.getSubject6ObtainedMarks().floatValue()));
					markTO.setSubjectid6(detailMarkBO.getSubjectid6());
				
			    }
			    else  if(detailMarkBO.getSubjectid7()!=null && !detailMarkBO.getSubjectid7().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid7())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid7(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject7(detailMarkBO.getSubject7());
			    	markTO.setSubjectOther7(detailMarkBO.getSubjectOther7());
			    	if(detailMarkBO.getSubject7Credit()!=null)
						markTO.setSubject7Credit(detailMarkBO.getSubject7Credit().toString());
			    	if(detailMarkBO.getSubject7TotalMarks()!=null && detailMarkBO.getSubject7TotalMarks().floatValue()!=0)
					markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO.getSubject7TotalMarks().floatValue()));
					if(detailMarkBO.getSubject7ObtainedMarks()!=null && detailMarkBO.getSubject7ObtainedMarks().floatValue()!=0)
					markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO.getSubject7ObtainedMarks().floatValue()));
					markTO.setSubjectid7(detailMarkBO.getSubjectid7());
				
			    }
			    else  if(detailMarkBO.getSubjectid8()!=null && !detailMarkBO.getSubjectid8().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid8())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid8(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject8(detailMarkBO.getSubject8());
			    	markTO.setSubjectOther8(detailMarkBO.getSubjectOther8());
			    	if(detailMarkBO.getSubject8Credit()!=null)
						markTO.setSubject8Credit(detailMarkBO.getSubject8Credit().toString());
			    	if(detailMarkBO.getSubject8TotalMarks()!=null && detailMarkBO.getSubject8TotalMarks().floatValue()!=0)
					markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO.getSubject8TotalMarks().floatValue()));
					if(detailMarkBO.getSubject8ObtainedMarks()!=null && detailMarkBO.getSubject8ObtainedMarks().floatValue()!=0)
					markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO.getSubject8ObtainedMarks().floatValue()));
					markTO.setSubjectid8(detailMarkBO.getSubjectid8());
				
			    }
			    else  if(detailMarkBO.getSubjectid9()!=null && !detailMarkBO.getSubjectid9().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid9())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid9(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject9(detailMarkBO.getSubject9());
			    	markTO.setSubjectOther9(detailMarkBO.getSubjectOther9());
			    	if(detailMarkBO.getSubject9Credit()!=null)
						markTO.setSubject9Credit(detailMarkBO.getSubject9Credit().toString());
			    	if(detailMarkBO.getSubject9TotalMarks()!=null && detailMarkBO.getSubject9TotalMarks().floatValue()!=0)
					markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO.getSubject9TotalMarks().floatValue()));
					if(detailMarkBO.getSubject9ObtainedMarks()!=null && detailMarkBO.getSubject9ObtainedMarks().floatValue()!=0)
					markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO.getSubject9ObtainedMarks().floatValue()));
					markTO.setSubjectid9(detailMarkBO.getSubjectid9());
				
			    }
			    else  if(detailMarkBO.getSubjectid10()!=null && !detailMarkBO.getSubjectid10().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid10())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid10(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject10(detailMarkBO.getSubject10());
			    	markTO.setSubjectOther10(detailMarkBO.getSubjectOther10());
			    	if(detailMarkBO.getSubject10Credit()!=null)
						markTO.setSubject10Credit(detailMarkBO.getSubject10Credit().toString());
			    	if(detailMarkBO.getSubject10TotalMarks()!=null && detailMarkBO.getSubject10TotalMarks().floatValue()!=0)
					markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO.getSubject10TotalMarks().floatValue()));
					if(detailMarkBO.getSubject10ObtainedMarks()!=null && detailMarkBO.getSubject10ObtainedMarks().floatValue()!=0)
					markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO.getSubject10ObtainedMarks().floatValue()));
					markTO.setSubjectid10(detailMarkBO.getSubjectid10());
				
			    }
			    else  if(detailMarkBO.getSubjectid11()!=null && !detailMarkBO.getSubjectid11().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid11())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid11(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject11(detailMarkBO.getSubject11());
			    	markTO.setSubjectOther11(detailMarkBO.getSubjectOther11());
			    	if(detailMarkBO.getSubject11TotalMarks()!=null && detailMarkBO.getSubject11TotalMarks().floatValue()!=0)
					markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO.getSubject11TotalMarks().floatValue()));
					if(detailMarkBO.getSubject11ObtainedMarks()!=null && detailMarkBO.getSubject11ObtainedMarks().floatValue()!=0)
					markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO.getSubject11ObtainedMarks().floatValue()));
					markTO.setSubjectid11(detailMarkBO.getSubjectid11());
					
				
			    }
			    else  if(detailMarkBO.getSubjectid12()!=null && !detailMarkBO.getSubjectid12().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid12())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid12(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject12(detailMarkBO.getSubject12());
			    	markTO.setSubjectOther12(detailMarkBO.getSubjectOther12());
			    	if(detailMarkBO.getSubject12TotalMarks()!=null && detailMarkBO.getSubject12TotalMarks().floatValue()!=0)
					markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO.getSubject12TotalMarks().floatValue()));
					if(detailMarkBO.getSubject12ObtainedMarks()!=null && detailMarkBO.getSubject12ObtainedMarks().floatValue()!=0)
					markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO.getSubject12ObtainedMarks().floatValue()));
					markTO.setSubjectid12(detailMarkBO.getSubjectid12());
				
			    } 
			    else  if(detailMarkBO.getSubjectid13()!=null && !detailMarkBO.getSubjectid13().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid13())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid13(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject13(detailMarkBO.getSubject13());
			    	markTO.setSubjectOther13(detailMarkBO.getSubjectOther13());
			    	if(detailMarkBO.getSubject13TotalMarks()!=null && detailMarkBO.getSubject13TotalMarks().floatValue()!=0)
					markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO.getSubject13TotalMarks().floatValue()));
					if(detailMarkBO.getSubject13ObtainedMarks()!=null && detailMarkBO.getSubject13ObtainedMarks().floatValue()!=0)
					markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO.getSubject13ObtainedMarks().floatValue()));
					markTO.setSubjectid13(detailMarkBO.getSubjectid13());
				
			    }
			    else  if(detailMarkBO.getSubjectid14()!=null && !detailMarkBO.getSubjectid14().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid14())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid14(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject14(detailMarkBO.getSubject14());
			    	markTO.setSubjectOther14(detailMarkBO.getSubjectOther14());
			    	if(detailMarkBO.getSubject14TotalMarks()!=null && detailMarkBO.getSubject14TotalMarks().floatValue()!=0)
					markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO.getSubject14TotalMarks().floatValue()));
					if(detailMarkBO.getSubject14ObtainedMarks()!=null && detailMarkBO.getSubject14ObtainedMarks().floatValue()!=0)
					markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO.getSubject14ObtainedMarks().floatValue()));
					markTO.setSubjectid14(detailMarkBO.getSubjectid14());
				
			    }
			    else   if(detailMarkBO.getSubjectid15()!=null && !detailMarkBO.getSubjectid15().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid15())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid15(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject15(detailMarkBO.getSubject15());
			    	markTO.setSubjectOther15(detailMarkBO.getSubjectOther15());
			    	if(detailMarkBO.getSubject15TotalMarks()!=null && detailMarkBO.getSubject15TotalMarks().floatValue()!=0)
					markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO.getSubject15TotalMarks().floatValue()));
					if(detailMarkBO.getSubject15ObtainedMarks()!=null && detailMarkBO.getSubject15ObtainedMarks().floatValue()!=0)
					markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO.getSubject15ObtainedMarks().floatValue()));
					markTO.setSubjectid15(detailMarkBO.getSubjectid15());
				
			    }
			    else  if(detailMarkBO.getSubjectid16()!=null && !detailMarkBO.getSubjectid16().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid16())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid16(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject16(detailMarkBO.getSubject16());
			    	markTO.setSubjectOther16(detailMarkBO.getSubjectOther16());
			    	if(detailMarkBO.getSubject16TotalMarks()!=null && detailMarkBO.getSubject16TotalMarks().floatValue()!=0)
					markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO.getSubject16TotalMarks().floatValue()));
					if(detailMarkBO.getSubject16ObtainedMarks()!=null && detailMarkBO.getSubject16ObtainedMarks().floatValue()!=0)
					markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO.getSubject16ObtainedMarks().floatValue()));
					markTO.setSubjectid16(detailMarkBO.getSubjectid16());
				
			    }
			    else   if(detailMarkBO.getSubjectid17()!=null && !detailMarkBO.getSubjectid17().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid17())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid17(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject17(detailMarkBO.getSubject17());
			    	markTO.setSubjectOther17(detailMarkBO.getSubjectOther17());
			    	if(detailMarkBO.getSubject17TotalMarks()!=null && detailMarkBO.getSubject17TotalMarks().floatValue()!=0)
					markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO.getSubject17TotalMarks().floatValue()));
					if(detailMarkBO.getSubject17ObtainedMarks()!=null && detailMarkBO.getSubject17ObtainedMarks().floatValue()!=0)
					markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO.getSubject17ObtainedMarks().floatValue()));
					markTO.setSubjectid17(detailMarkBO.getSubjectid17());
				
			    }
			    else  if(detailMarkBO.getSubjectid18()!=null && !detailMarkBO.getSubjectid18().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid18())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid18(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject18(detailMarkBO.getSubject18());
			    	markTO.setSubjectOther18(detailMarkBO.getSubjectOther18());
			    	if(detailMarkBO.getSubject18TotalMarks()!=null && detailMarkBO.getSubject18TotalMarks().floatValue()!=0)
					markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO.getSubject18TotalMarks().floatValue()));
					if(detailMarkBO.getSubject18ObtainedMarks()!=null && detailMarkBO.getSubject18ObtainedMarks().floatValue()!=0)
					markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO.getSubject18ObtainedMarks().floatValue()));
					markTO.setSubjectid18(detailMarkBO.getSubjectid18());
				
			    }
			    else  if(detailMarkBO.getSubjectid19()!=null && !detailMarkBO.getSubjectid19().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid19())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid19(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject19(detailMarkBO.getSubject19());
			    	markTO.setSubjectOther19(detailMarkBO.getSubjectOther19());
			    	if(detailMarkBO.getSubject19TotalMarks()!=null && detailMarkBO.getSubject19TotalMarks().floatValue()!=0)
					markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO.getSubject19TotalMarks().floatValue()));
					if(detailMarkBO.getSubject19ObtainedMarks()!=null && detailMarkBO.getSubject19ObtainedMarks().floatValue()!=0)
					markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO.getSubject19ObtainedMarks().floatValue()));
					markTO.setSubjectid19(detailMarkBO.getSubjectid19());
				
			    }
			    else  if(detailMarkBO.getSubjectid20()!=null && !detailMarkBO.getSubjectid20().equalsIgnoreCase("") && Integer.parseInt(detailMarkBO.getSubjectid20())==admSubjectMarkForRank.getAdmSubjectForRank().getId()){
			    	markTO.setSubjectmarkid20(admSubjectMarkForRank.getId()+"");
			    	markTO.setSubject20(detailMarkBO.getSubject20());
			    	markTO.setSubjectOther20(detailMarkBO.getSubjectOther20());
			    	if(detailMarkBO.getSubject20TotalMarks()!=null && detailMarkBO.getSubject20TotalMarks().floatValue()!=0)
					markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO.getSubject20TotalMarks().floatValue()));
					if(detailMarkBO.getSubject20ObtainedMarks()!=null && detailMarkBO.getSubject20ObtainedMarks().floatValue()!=0)
					markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO.getSubject20ObtainedMarks().floatValue()));
					markTO.setSubjectid20(detailMarkBO.getSubjectid20());
				
			    }
			    
				
			    
			    }//close while
			    
			    
				} //close if  
				
				
				
				
				
			
			}
			
			
			
			public PreferenceTO copyPropertiesValue(Set<CandidatePreference> preferencesSet) {
				log.info("enter copyPropertiesValue preferences" );
				PreferenceTO preferenceTO = null;

				if (preferencesSet != null) {
					Iterator<CandidatePreference> iterator = preferencesSet.iterator();
					preferenceTO = new PreferenceTO();

					while (iterator.hasNext()) {
						CandidatePreference candidatePreferenceBO = (CandidatePreference) iterator.next();

						// select the preferences depending upon the preference no.
						if (candidatePreferenceBO.getCourse()!= null && candidatePreferenceBO.getCourse().getProgram() != null && candidatePreferenceBO.getCourse().getProgram().getProgramType()!= null){
							if (candidatePreferenceBO.getPrefNo() == 1) {
								preferenceTO.setId(candidatePreferenceBO.getId());
								preferenceTO.setFirstPerfId(candidatePreferenceBO.getId());
								preferenceTO.setFirstprefCourseName(candidatePreferenceBO.getCourse().getName());
								preferenceTO.setFirstprefCourseCode(candidatePreferenceBO.getCourse().getCode());
								preferenceTO.setFirstPrefCourseId(String.valueOf(candidatePreferenceBO.getCourse().getId()));
								preferenceTO.setFirstprefPgmName(candidatePreferenceBO.getCourse().getProgram().getName());
								preferenceTO.setFirstprefPgmTypeName(candidatePreferenceBO.getCourse().getProgram().getProgramType().getName());
								preferenceTO.setFirstPrefProgramId(String.valueOf(candidatePreferenceBO.getCourse().getProgram().getId()));
								preferenceTO.setFirstPrefProgramTypeId(String.valueOf(candidatePreferenceBO.getCourse().getProgram().getProgramType().getId()));
							} else if (candidatePreferenceBO.getPrefNo() == 2) {
								preferenceTO.setId(candidatePreferenceBO.getId());
								preferenceTO.setSecondPerfId(candidatePreferenceBO.getId());
								preferenceTO.setSecondprefCourseName(candidatePreferenceBO.getCourse().getName());
								preferenceTO.setSecondprefCourseCode(candidatePreferenceBO.getCourse().getCode());
								preferenceTO.setSecondPrefCourseId(String.valueOf(candidatePreferenceBO.getCourse().getId()));
								preferenceTO.setSecondprefPgmName(candidatePreferenceBO.getCourse().getProgram().getName());
								preferenceTO.setSecondprefPgmTypeName(candidatePreferenceBO.getCourse().getProgram().getProgramType().getName());
								preferenceTO.setSecondPrefProgramId((String.valueOf(candidatePreferenceBO.getCourse().getProgram().getId())));
								preferenceTO.setSecondPrefProgramTypeId(String.valueOf(candidatePreferenceBO.getCourse().getProgram().getProgramType().getId()));
							} else if (candidatePreferenceBO.getPrefNo() == 3) {
								preferenceTO.setId(candidatePreferenceBO.getId());
								preferenceTO.setThirdPerfId(candidatePreferenceBO.getId());
								preferenceTO.setThirdprefCourseName(candidatePreferenceBO.getCourse().getName());
								preferenceTO.setThirdprefCourseCode(candidatePreferenceBO.getCourse().getCode());
								preferenceTO.setThirdPrefCourseId(String.valueOf(candidatePreferenceBO.getCourse().getId()));
								preferenceTO.setThirdprefPgmName(candidatePreferenceBO.getCourse().getProgram().getName());
								preferenceTO.setThirdprefPgmTypeName(candidatePreferenceBO.getCourse().getProgram().getProgramType().getName());
								preferenceTO.setThirdPrefProgramId((String.valueOf(candidatePreferenceBO.getCourse().getProgram().getId())));
								preferenceTO.setThirdPrefProgramTypeId(String.valueOf(candidatePreferenceBO.getCourse().getProgram().getProgramType().getId()));
							}
						}
					}
				}
				log.info("exit copyPropertiesValue preferences" );
				return preferenceTO;
			}
			/**
			 * @param courseId 
			 * @param adminAppTO 
			 * @param docUploadSetBO
			 * @return documentsListTO
			 */
			public List<ApplnDocTO> copyPropertiesEditDocValue(AdmAppln applnDoc, int courseId, AdmApplnTO adminAppTO, int appliedYear) throws Exception {
				log.info("enter copyPropertiesEditDocValue" );
				List<ApplnDocTO> documentsList = new ArrayList<ApplnDocTO>();
				ApplnDocTO applnDocTO = null;
				Set<ApplnDoc> docUploadSet=applnDoc.getApplnDocs();
			
					AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
					List<ApplnDocTO> reqList=handler.getRequiredDocList(String.valueOf(courseId),appliedYear);
					
					
					boolean photoexist=false;
				if (docUploadSet != null && !docUploadSet.isEmpty()) {
					Iterator<ApplnDoc> iterator = docUploadSet.iterator();
					while (iterator.hasNext()) {
						
						ApplnDoc applnDocBO = (ApplnDoc) iterator.next();

						applnDocTO = new ApplnDocTO();
						if(applnDocBO.getId()!=0)
						applnDocTO.setId(applnDocBO.getId());
						applnDocTO.setCreatedBy(applnDocBO.getCreatedBy());
						applnDocTO.setCreatedDate(applnDocBO.getCreatedDate());
						if( applnDocBO.getDocType() != null ){
						applnDocTO.setDocTypeId(applnDocBO.getDocType().getId());
						applnDocTO.setDocName(applnDocBO.getDocType().getPrintName());
						applnDocTO.setPrintName(applnDocBO.getDocType().getPrintName());
						}
						applnDocTO.setName(applnDocBO.getName());
						if(applnDocBO.getSubmissionDate()!=null)
						applnDocTO.setSubmitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applnDocBO.getSubmissionDate()), OnlineApplicationHelper.SQL_DATEFORMAT,OnlineApplicationHelper.FROM_DATEFORMAT));
						applnDocTO.setContentType(applnDocBO.getContentType());
						if(applnDocBO.getIsVerified()!=null && applnDocBO.getIsVerified()){
							applnDocTO.setVerified(true);
						}else{
							applnDocTO.setVerified(false);
						}
						if(applnDocBO.getHardcopySubmitted()!=null && applnDocBO.getHardcopySubmitted()){
							applnDocTO.setHardSubmitted(false);
							applnDocTO.setTemphardSubmitted(true);
						}else{
							applnDocTO.setHardSubmitted(false);
							applnDocTO.setTemphardSubmitted(false);
						}
						if(applnDocBO.getNotApplicable()!=null && applnDocBO.getNotApplicable()){
							applnDocTO.setNotApplicable(false);
							applnDocTO.setTempNotApplicable(true);
						}else{
							applnDocTO.setNotApplicable(false);
							applnDocTO.setTempNotApplicable(false);
						}
						int studentId=0;
						
						if(applnDoc.getStudents()!=null){
							for(Student student:applnDoc.getStudents()){
								if(student!=null){
									studentId=student.getId();
								}
							}
						}
						
						
						if(applnDocBO.getIsPhoto()!=null && applnDocBO.getIsPhoto()){
							applnDocTO.setPhoto(true);
							applnDocTO.setDocName(OnlineApplicationHelper.PHOTO);
							applnDocTO.setPrintName(OnlineApplicationHelper.PHOTO);
							if(applnDocBO.getName()!=null && OnlineApplicationHelper.PHOTO.equalsIgnoreCase(applnDocBO.getName())) {
								applnDocTO.setDefaultPhoto(true);
							}
							photoexist=true;
							 
							File f = new File(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+studentId+".jpg");
							
							if (f.exists()) {
								FileInputStream fis = new FileInputStream(f);
						        ByteArrayOutputStream bos = new ByteArrayOutputStream();
						        byte[] buf = new byte[1024];
						        try {
						            for (int readNum; (readNum = fis.read(buf)) != -1;) {
						                bos.write(buf, 0, readNum); 
						            }
						        } catch (IOException ex) {
						            
						        }
						        byte[] myFileBytes = bos.toByteArray();
						        applnDocTO.setPhotoBytes(myFileBytes);
						        fis.close();
							}else{
								//raghu
								if(applnDocBO.getDocument()!=null){
									byte [] myFileBytes = applnDocBO.getDocument();
									 applnDocTO.setPhotoBytes(myFileBytes);
								}
							}
						}
						else if(applnDocBO.getIsMngQuotaForm()) {
							applnDocTO.setPhoto(false);
							applnDocTO.setMngQuotaForm(true);
							applnDocTO.setDocName(OnlineApplicationHelper.MANAGEMENT_QUOTA_FORM);
							applnDocTO.setPrintName(OnlineApplicationHelper.MANAGEMENT_QUOTA_FORM);
							if(applnDocBO.getName()!=null && OnlineApplicationHelper.PHOTO.equalsIgnoreCase(applnDocBO.getName())) {
								applnDocTO.setDefaultPhoto(true);
							}
							photoexist=true;
							
							File f = new File(CMSConstants.STUDENT_MANAGEMENT_QUOTA_FORM_FOLDER_PATH+studentId+".jpg");
							if (f.exists()) {
								FileInputStream fis = new FileInputStream(f);
						        ByteArrayOutputStream bos = new ByteArrayOutputStream();
						        byte[] buf = new byte[1024];
						        try {
						            for (int readNum; (readNum = fis.read(buf)) != -1;) {
						                bos.write(buf, 0, readNum); 
						            }
						        } catch (IOException ex) {
						            
						        }
						        byte[] myFileBytes = bos.toByteArray();
						        applnDocTO.setPhotoBytes(myFileBytes);
						        fis.close();
							}else{
								//raghu
								if(applnDocBO.getDocument()!=null && applnDocBO.getIsPhoto()){
									byte [] myFileBytes = applnDocBO.getDocument();
									 applnDocTO.setPhotoBytes(myFileBytes);
								}
								if(applnDocBO.getDocument()!=null && applnDocBO.getIsMngQuotaForm()){
									byte [] myFileBytes = applnDocBO.getDocument();
									 applnDocTO.setMngQuotaBytes(myFileBytes);
								}
							}
						}
						else{
							applnDocTO.setPhoto(false);
						}
						
						if(applnDocBO.getDocument() != null){
							applnDocTO.setDocumentPresent(true);
							applnDocTO.setCurrDocument(applnDocBO.getDocument());
							
						}else{
							applnDocTO.setDocumentPresent(false);
						}
						if(applnDocBO.getSemNo()!=null){
							applnDocTO.setSemisterNo(applnDocBO.getSemNo());
						}
						if(applnDocBO.getSemType()!=null){
							applnDocTO.setSemType(applnDocBO.getSemType());
						}
						Set<DocChecklist> docChecklistDoc = null;
						if( applnDocBO.getDocType() != null && applnDocBO.getDocType().getDocChecklists()!=null){
							docChecklistDoc = applnDocBO.getDocType().getDocChecklists();
						}
						
						if (docChecklistDoc != null) {
							for (DocChecklist docChecklistBO : docChecklistDoc) {
								// condition to check whether course id and applicant course id are matching
								if( docChecklistBO.getCourse().getId() == applnDocBO.getAdmAppln().getCourse().getId() && docChecklistBO.getYear()== appliedYear){
									if (docChecklistBO.getNeedToProduce()!= null && docChecklistBO.getNeedToProduce() && docChecklistBO.getIsActive()) {
										applnDocTO.setNeedToProduce(true);
									} else {
										applnDocTO.setNeedToProduce(false);
									}
									if(docChecklistBO.getNeedToProduceSemwiseMc()!=null && docChecklistBO.getNeedToProduceSemwiseMc()&& docChecklistBO.getIsActive()){
										applnDocTO.setNeedToProduceSemWiseMC(true);
									}else{
										applnDocTO.setNeedToProduceSemWiseMC(false);
									}
								}
							}
						}
						if(applnDocTO.isNeedToProduceSemWiseMC()){
							List<Integer> noList=new ArrayList<Integer>();
							
							Set<ApplnDocDetails> docDetailsSet=applnDocBO.getApplnDocDetails();
							List<ApplnDocDetailsTO> detailList=new ArrayList<ApplnDocDetailsTO>();
							if(docDetailsSet!=null && !docDetailsSet.isEmpty()){
								Iterator<ApplnDocDetails> itr=docDetailsSet.iterator();
								while (itr.hasNext()) {
									ApplnDocDetails bo = (ApplnDocDetails) itr.next();
									ApplnDocDetailsTO to=new ApplnDocDetailsTO();
									if(bo.getId()!=0)
									to.setId(bo.getId());
									to.setApplnDocId(applnDocBO.getId());
									to.setSemNo(bo.getSemesterNo());
//									to.setIsHardCopySubmitted(bo.getIsHardCopySubmited());
									to.setTempHardCopySubmitted(bo.getIsHardCopySubmited());
									if(bo.getIsHardCopySubmited()!=null && bo.getIsHardCopySubmited()){
										to.setChecked("yes");
									}else{
										to.setChecked("no");
									}
									noList.add(Integer.parseInt(bo.getSemesterNo()));
									detailList.add(to);
								}
							}
							for (int i=1;i<=12;i++) {
								if(!noList.contains(i)){
								ApplnDocDetailsTO to=new ApplnDocDetailsTO();
								to.setSemNo(String.valueOf(i));
								to.setTempHardCopySubmitted(false);
								to.setChecked("no");
								detailList.add(to);
								}
							}
							Collections.sort(detailList);
							applnDocTO.setDocDetailsList(detailList);
						}
						//remove exists,add new requireds
						if (reqList != null) {
							Iterator<ApplnDocTO> it = reqList.iterator();
							while (it.hasNext()) {
								ApplnDocTO reqTo = (ApplnDocTO) it.next();
								if(reqTo.getDocTypeId()!=0 && applnDocTO.getDocTypeId()!=0 && reqTo.getDocTypeId()==applnDocTO.getDocTypeId()){
									//remove from required list

									it.remove();
									
									}
									if(photoexist){
										if(reqTo.isPhoto()){

											it.remove();
										}
									}
								}
							}
						
						
						documentsList.add(applnDocTO);
					}

					// add requireds
					if(reqList!=null && !reqList.isEmpty()){
						Iterator<ApplnDocTO> it = reqList.iterator();
						while (it.hasNext()) {
							ApplnDocTO reqTo = (ApplnDocTO) it.next();
							documentsList.add(reqTo);
						}
					}
				}else{
					documentsList=reqList;
				}
				log.info("exit copyPropertiesEditDocValue" );
				return documentsList;
			}
			/**
			 * creates entrance details BO
			 * @param admForm
			 * @param candidateentrances 
			 * @return
			 */
			public Set<CandidateEntranceDetails> getCandidateEntranceDetails(
					OnlineApplicationForm admForm, Set<CandidateEntranceDetails> candidateentrances) {
				log.info("enter getCandidateEntranceDetails" );
				if(candidateentrances!=null){
					CandidateEntranceDetails entBO= new CandidateEntranceDetails();
					if(admForm.getEntranceMonthPass()!=null && !StringUtils.isEmpty(admForm.getEntranceMonthPass().trim())&& StringUtils.isNumeric(admForm.getEntranceMonthPass().trim()))
					entBO.setMonthPassing(Integer.valueOf(admForm.getEntranceMonthPass().trim()));
					if(admForm.getEntranceYearPass()!=null && !StringUtils.isEmpty(admForm.getEntranceYearPass().trim())&& StringUtils.isNumeric(admForm.getEntranceYearPass().trim()))
						entBO.setYearPassing(Integer.valueOf(admForm.getEntranceYearPass().trim()));
					
					 entBO.setEntranceRollNo(admForm.getEntranceRollNo());
					if(admForm.getEntranceMarkObtained()!=null && !StringUtils.isEmpty(admForm.getEntranceMarkObtained().trim())&& CommonUtil.isValidDecimal(admForm.getEntranceMarkObtained().trim()))
					 entBO.setMarksObtained(new BigDecimal(admForm.getEntranceMarkObtained().trim()));
					if(admForm.getEntranceTotalMark()!=null && !StringUtils.isEmpty(admForm.getEntranceTotalMark().trim())&& CommonUtil.isValidDecimal(admForm.getEntranceTotalMark().trim()))
						 entBO.setTotalMarks(new BigDecimal(admForm.getEntranceTotalMark().trim()));
					 
					 if(admForm.getEntranceId()!=null && !StringUtils.isEmpty(admForm.getEntranceId()) && StringUtils.isNumeric(admForm.getEntranceId())){
					 Entrance ad= new Entrance();
					 ad.setId(Integer.parseInt(admForm.getEntranceId()));
					 entBO.setEntrance(ad);
					 }else
						 entBO.setEntrance(null);

					 candidateentrances.add(entBO);
					
				}
				log.info("exit getCandidateEntranceDetails" );
				return candidateentrances;
			}
			
			/**
			 * @param subjectMap
			 * @param markTo
			 */
			public  void setDetailedSubjectsFormMap(Map<Integer, String> subjectMap,
					CandidateMarkTO markTo) {
				if(subjectMap!=null){
				
						int count=1;
						for (Entry<Integer, String> entry : subjectMap.entrySet()) {
						        if(count==1){
						        	markTo.setSubject1((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setSubjectName((String)entry.getValue());
						        	detTo.setId((Integer)entry.getKey());
						        	markTo.setDetailedSubjects1(detTo);
						        	markTo.setSubject1Mandatory(true);
						        	
						        }else if(count==2)
						        {
						        	markTo.setSubject2((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects2(detTo);
						        	markTo.setSubject2Mandatory(true);
						        }
						        else if(count==3)
						        {
						        	markTo.setSubject3((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects3(detTo);
						        	markTo.setSubject3Mandatory(true);
						        }
						        else if(count==4)
						        {
						        	markTo.setSubject4((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects4(detTo);
						        	markTo.setSubject4Mandatory(true);
						        }
						        else if(count==5)
						        {
						        	markTo.setSubject5((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects5(detTo);
						        	markTo.setSubject5Mandatory(true);
						        }
						        else if(count==6)
						        {
						        	markTo.setSubject6((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects6(detTo);
						        	markTo.setSubject6Mandatory(true);
						        }
						        
						        else if(count==7)
						        {
						        	markTo.setSubject7((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects7(detTo);
						        	markTo.setSubject7Mandatory(true);
						        }
						        else if(count==8)
						        {
						        	markTo.setSubject8((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	markTo.setDetailedSubjects8(detTo);
						        	markTo.setSubject8Mandatory(true);
						        }
						        else if(count==9)
						        {
						        	markTo.setSubject9((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects9(detTo);
						        	markTo.setSubject9Mandatory(true);
						        }
						        else if(count==10)
						        {
						        	markTo.setSubject10((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects10(detTo);
						        	markTo.setSubject10Mandatory(true);
						        }
						        else if(count==11)
						        {
						        	markTo.setSubject11((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects11(detTo);
						        	markTo.setSubject11Mandatory(true);
						        }else if(count==12)
						        {
						        	markTo.setSubject12((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	markTo.setDetailedSubjects12(detTo);
						        	markTo.setSubject12Mandatory(true);
						        }else if(count==13)
						        {
						        	markTo.setSubject13((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects13(detTo);
						        	markTo.setSubject13Mandatory(true);
						        }else if(count==14)
						        {
						        	markTo.setSubject14((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects14(detTo);
						        	markTo.setSubject14Mandatory(true);
						        }else if(count==15)
						        {
						        	markTo.setSubject15((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects15(detTo);
						        	markTo.setSubject15Mandatory(true);
						        }else if(count==16)
						        {
						        	markTo.setSubject16((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects16(detTo);
						        	markTo.setSubject16Mandatory(true);
						        }else if(count==17)
						        {
						        	markTo.setSubject17((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects17(detTo);
						        	markTo.setSubject17Mandatory(true);
						        }else if(count==18)
						        {
						        	markTo.setSubject18((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects18(detTo);
						        	markTo.setSubject18Mandatory(true);
						        }else if(count==19)
						        {
						        	markTo.setSubject19((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects19(detTo);
						        	markTo.setSubject19Mandatory(true);
						        }else if(count==20)
						        {
						        	markTo.setSubject20((String)entry.getValue());
						        	DetailedSubjectsTO detTo= new DetailedSubjectsTO();
						        	detTo.setId((Integer)entry.getKey());
						        	detTo.setSubjectName((String)entry.getValue());
						        	markTo.setDetailedSubjects20(detTo);
						        	markTo.setSubject20Mandatory(true);
						        }
						        
						        count++;
						    }

					
				}
				
			}
			
			
		
			/*
			 * Single Application Start	
			 */
				/**
				 * prepares a blank student object
				 * @param courseID 
				 * @return
				 */
				/*public AdmApplnTO getNewStudent(HttpSession session,String courseID,OnlineApplicationForm admForm) throws Exception {
					log.info("enter getNewStudent");
					int year = Integer.parseInt(admForm.getApplicationYear());
					AdmApplnTO adminAppTO = admForm.getApplicantDetails();
					PersonalData personalData = null;
					//PersonalDataTO personalDataTO = adminAppTO.getPersonalData();
//					 get the AdmAppln information if uniqueId is available,otherwise get AdmAppln data with application no.
					Map<Integer,EdnQualificationTO> eduQualificationMap = null;
					if(admForm.getAdmApplnId()==null || admForm.getAdmApplnId().isEmpty()){
					LinkedList<AdmAppln> applnsList =  txn.getAdmApplnList(admForm.getUniqueId());
					if(applnsList == null || applnsList.isEmpty()){
						applnsList = new LinkedList<AdmAppln>();
						AdmAppln admAppln= txn.getAdmApplnDetails(admForm.getApplnNo());
						if(admAppln!=null){
							applnsList.add(admAppln);
						}
					}
//					EduQualification map
					
					if(applnsList!=null && !applnsList.isEmpty()){
						AdmAppln appln =applnsList.getLast();
						if(appln.getPersonalData()!=null){
							personalData= appln.getPersonalData();
							if(personalData.getGender()!=null && !personalData.getGender().isEmpty()){
								adminAppTO.getPersonalData().setGender(personalData.getGender());
							}
							if(personalData.getNationality()!=null){
								adminAppTO.getPersonalData().setNationality(personalData.getNationality().getName());
							}
							if(personalData.getBloodGroup()!=null && !personalData.getBloodGroup().isEmpty()){
								adminAppTO.getPersonalData().setBloodGroup(personalData.getBloodGroup());
							}
							if(personalData.getSecondLanguage()!=null && !personalData.getSecondLanguage().isEmpty()){
								adminAppTO.getPersonalData().setSecondLanguage(personalData.getSecondLanguage());
							}
							if(personalData.getBirthPlace()!=null && !personalData.getBirthPlace().isEmpty()){
								adminAppTO.getPersonalData().setBirthPlace(personalData.getBirthPlace());
							}
							if(personalData.getStateByStateId()!=null && personalData.getStateByStateId().getId()!=0){
								adminAppTO.getPersonalData().setBirthState(String.valueOf(personalData.getStateByStateId().getId()));
							}
							if(personalData.getMotherTongue()!=null && personalData.getMotherTongue().getId()!=0){
								adminAppTO.getPersonalData().setMotherTongue(String.valueOf(personalData.getMotherTongue().getId()));
							}
//							Resident Info
							if(personalData.getResidentCategory()!=null){
								adminAppTO.getPersonalData().setResidentCategory(String.valueOf(personalData.getResidentCategory().getId()));
							}
							if(personalData.getReligion()!=null){
								adminAppTO.getPersonalData().setReligionId(String.valueOf(personalData.getReligion().getId()));
							}
							if(personalData.getCaste()!=null){
								adminAppTO.getPersonalData().setCasteId(String.valueOf(personalData.getCaste().getId()));
							}
							if(personalData.getRuralUrban()!=null){
								adminAppTO.getPersonalData().setAreaType(personalData.getRuralUrban());
							}
							if(personalData.getPhNo1()!=null){
								adminAppTO.getPersonalData().setPhNo1(personalData.getPhNo1());
							}
							if(personalData.getPhNo2()!=null){
								adminAppTO.getPersonalData().setPhNo2(personalData.getPhNo2());
							}
							if(personalData.getPhNo3()!=null){
								adminAppTO.getPersonalData().setPhNo3(personalData.getPhNo3());
							}
							if(personalData.getMobileNo1()!=null){
								adminAppTO.getPersonalData().setMobileNo1(personalData.getMobileNo1());
							}
							if(personalData.getMobileNo2()!=null){
								adminAppTO.getPersonalData().setMobileNo2(personalData.getMobileNo2());
							}
//							passport details
							if(personalData.getPassportNo()!=null && !personalData.getPassportNo().isEmpty()){
								adminAppTO.getPersonalData().setPassportNo(personalData.getPassportNo());
							}
							if(personalData.getCountryByPassportCountryId()!=null && personalData.getCountryByPassportCountryId().getId()!=0){
								adminAppTO.getPersonalData().setPassportCountry(personalData.getCountryByPassportCountryId().getId());
							}
							if(personalData.getPassportValidity()!=null){
								adminAppTO.getPersonalData().setPassportValidity(CommonUtil.formatDates(personalData.getPassportValidity()));
							}
							if(personalData.getResidentPermitNo()!=null && !personalData.getResidentPermitNo().isEmpty()){
								adminAppTO.getPersonalData().setResidentPermitNo(personalData.getResidentPermitNo());
							}
							if(personalData.getResidentPermitDate()!=null){
								adminAppTO.getPersonalData().setResidentPermitDate(CommonUtil.formatDates(personalData.getResidentPermitDate()));
							}
							
//							Current Address Details
							if(personalData.getCurrentAddressLine1()!=null && !personalData.getCurrentAddressLine1().isEmpty()){
								adminAppTO.getPersonalData().setCurrentAddressLine1(personalData.getCurrentAddressLine1());
							}
							if(personalData.getCurrentAddressLine2()!=null && !personalData.getCurrentAddressLine2().isEmpty()){
								adminAppTO.getPersonalData().setCurrentAddressLine2(personalData.getCurrentAddressLine2());
							}
							if(personalData.getCityByCurrentAddressCityId()!=null && !personalData.getCityByCurrentAddressCityId().isEmpty()){
								adminAppTO.getPersonalData().setCurrentCityName(personalData.getCityByCurrentAddressCityId());
							}
							if(personalData.getCountryByCurrentAddressCountryId()!=null){
								adminAppTO.getPersonalData().setCurrentCountryId(personalData.getCountryByCurrentAddressCountryId().getId());
							}
							if(personalData.getStateByCurrentAddressStateId()!=null){
								adminAppTO.getPersonalData().setCurrentStateId(String.valueOf(personalData.getStateByCurrentAddressStateId().getId()));
							}
							if(personalData.getCurrentAddressZipCode()!=null && !personalData.getCurrentAddressZipCode().isEmpty()){
								adminAppTO.getPersonalData().setCurrentAddressZipCode(personalData.getCurrentAddressZipCode());
							}
//							permanent Address details
							
							if(personalData.getPermanentAddressLine1()!=null && !personalData.getPermanentAddressLine1().isEmpty()){
								adminAppTO.getPersonalData().setPermanentAddressLine1(personalData.getPermanentAddressLine1());
							}
							if(personalData.getPermanentAddressLine2()!=null && !personalData.getPermanentAddressLine2().isEmpty()){
								adminAppTO.getPersonalData().setPermanentAddressLine2(personalData.getPermanentAddressLine2());
							}
							if(personalData.getCityByPermanentAddressCityId()!=null && !personalData.getCityByPermanentAddressCityId().isEmpty()){
								adminAppTO.getPersonalData().setPermanentCityName(personalData.getCityByPermanentAddressCityId());
							}
							if(personalData.getCountryByPermanentAddressCountryId()!=null){
								adminAppTO.getPersonalData().setPermanentCountryId(personalData.getCountryByPermanentAddressCountryId().getId());
							}
							if(personalData.getStateByPermanentAddressStateId()!=null){
								adminAppTO.getPersonalData().setPermanentStateId(String.valueOf(personalData.getStateByPermanentAddressStateId().getId()));
							}
							if(personalData.getPermanentAddressZipCode()!=null && !personalData.getPermanentAddressZipCode().isEmpty()){
								adminAppTO.getPersonalData().setPermanentAddressZipCode(personalData.getPermanentAddressZipCode());
							}
//							parents details
							if(appln.getAdmapplnAdditionalInfo()!=null && !appln.getAdmapplnAdditionalInfo().isEmpty()){
								List<AdmapplnAdditionalInfo> additionalInfos = new ArrayList<AdmapplnAdditionalInfo>(appln.getAdmapplnAdditionalInfo());
								if(additionalInfos.get(0).getTitleFather()!=null && !additionalInfos.get(0).getTitleFather().isEmpty()){
									adminAppTO.setTitleOfFather(additionalInfos.get(0).getTitleFather());
								}
								if(additionalInfos.get(0).getTitleMother()!=null && !additionalInfos.get(0).getTitleMother().isEmpty()){
									adminAppTO.setTitleOfMother(additionalInfos.get(0).getTitleMother());
								}
								if(additionalInfos.get(0).getBackLogs()!=null ){
									adminAppTO.setBackLogs(additionalInfos.get(0).getBackLogs());
								}
								if(additionalInfos.get(0).getApplicantFeedback()!=null ){
									adminAppTO.setApplicantFeedbackId(String.valueOf(additionalInfos.get(0).getApplicantFeedback().getId()));
								}
								//raghu
								if(additionalInfos.get(0).getIsSaypass()!=null ){
									adminAppTO.setIsSaypass(additionalInfos.get(0).getIsSaypass());
								}
							}
							if(personalData.getFatherName()!=null && !personalData.getFatherName().isEmpty()){
								adminAppTO.getPersonalData().setFatherName(personalData.getFatherName());
							}
							if(personalData.getFatherEducation()!=null && !personalData.getFatherEducation().isEmpty()){
								adminAppTO.getPersonalData().setFatherEducation(personalData.getFatherEducation());
							}
							if(personalData.getFatherEmail()!=null && !personalData.getFatherEmail().isEmpty()){
								adminAppTO.getPersonalData().setFatherEmail(personalData.getFatherEmail());
							}
							if(personalData.getCurrencyByFatherIncomeCurrencyId()!=null){
								adminAppTO.getPersonalData().setFatherCurrencyId(String.valueOf(personalData.getCurrencyByFatherIncomeCurrencyId().getId()));
							}
							if(personalData.getOccupationByFatherOccupationId()!=null){
								adminAppTO.getPersonalData().setFatherOccupationId(String.valueOf(personalData.getOccupationByFatherOccupationId().getId()));
							}
							if(personalData.getIncomeByFatherIncomeId()!=null ){
								adminAppTO.getPersonalData().setFatherIncomeId(String.valueOf(personalData.getIncomeByFatherIncomeId().getId()));
							}
							if(personalData.getMotherName()!=null && !personalData.getMotherName().isEmpty()){
								adminAppTO.getPersonalData().setMotherName(personalData.getMotherName());
							}
							if(personalData.getMotherEducation()!=null && !personalData.getMotherEducation().isEmpty()){
								adminAppTO.getPersonalData().setMotherEducation(personalData.getMotherEducation());
							}
							if(personalData.getMotherEmail()!=null && !personalData.getMotherEmail().isEmpty()){
								adminAppTO.getPersonalData().setMotherEmail(personalData.getMotherEmail());
							}
							if(personalData.getCurrencyByMotherIncomeCurrencyId()!=null){
								adminAppTO.getPersonalData().setMotherCurrencyId(String.valueOf(personalData.getCurrencyByMotherIncomeCurrencyId().getId()));
							}
							if(personalData.getOccupationByMotherOccupationId()!=null){
								adminAppTO.getPersonalData().setMotherOccupationId(String.valueOf(personalData.getOccupationByMotherOccupationId().getId()));
							}
							if(personalData.getIncomeByMotherIncomeId()!=null){
								adminAppTO.getPersonalData().setMotherIncomeId(String.valueOf(personalData.getIncomeByMotherIncomeId().getId()));
							}
//							parent address
							if(personalData.getParentAddressLine1()!=null && !personalData.getParentAddressLine1().isEmpty()){
								adminAppTO.getPersonalData().setParentAddressLine1(personalData.getParentAddressLine1());
							}
							if(personalData.getParentAddressLine2()!=null && !personalData.getParentAddressLine2().isEmpty()){
								adminAppTO.getPersonalData().setParentAddressLine2(personalData.getParentAddressLine2());
							}
							if(personalData.getParentAddressLine3()!=null && !personalData.getParentAddressLine3().isEmpty()){
								adminAppTO.getPersonalData().setParentAddressLine3(personalData.getParentAddressLine3());
							}
							if(personalData.getParentAddressZipCode()!=null && !personalData.getParentAddressZipCode().isEmpty()){
								adminAppTO.getPersonalData().setParentAddressZipCode(personalData.getParentAddressZipCode());
							}
							if(personalData.getCityByParentAddressCityId()!=null && !personalData.getCityByParentAddressCityId().isEmpty()){
								adminAppTO.getPersonalData().setParentCityName(personalData.getCityByParentAddressCityId());
							}
							if(personalData.getCountryByParentAddressCountryId()!=null){
								adminAppTO.getPersonalData().setParentCountryId(personalData.getCountryByParentAddressCountryId().getId());
							}
							if(personalData.getStateByParentAddressStateId()!=null){
								adminAppTO.getPersonalData().setParentStateId(String.valueOf(personalData.getStateByParentAddressStateId().getId()));
							}
							if(personalData.getParentPh1()!=null && !personalData.getParentPh1().isEmpty()){
								adminAppTO.getPersonalData().setParentPh1(personalData.getParentPh1());
							}
							if(personalData.getParentPh2()!=null && !personalData.getParentPh2().isEmpty()){
								adminAppTO.getPersonalData().setParentPh2(personalData.getParentPh2());
							}
							if(personalData.getParentPh3()!=null && !personalData.getParentPh3().isEmpty()){
								adminAppTO.getPersonalData().setParentPh3(personalData.getParentPh3());
							}
							if(personalData.getParentMob1()!=null && !personalData.getParentMob1().isEmpty()){
								adminAppTO.getPersonalData().setParentMob1(personalData.getParentMob1());
							}
							if(personalData.getParentMob2()!=null && !personalData.getParentMob2().isEmpty()){
								adminAppTO.getPersonalData().setParentMob2(personalData.getParentMob2());
							}
							if(personalData.getParentMob3()!=null && !personalData.getParentMob3().isEmpty()){
								adminAppTO.getPersonalData().setParentMob3(personalData.getParentMob3());
							}
//							guardian address
							if(personalData.getGuardianName()!=null && !personalData.getGuardianName().isEmpty()){
								adminAppTO.getPersonalData().setGuardianName(personalData.getGuardianName());
							}
							if(personalData.getGuardianAddressLine1()!=null && !personalData.getGuardianAddressLine1().isEmpty()){
								adminAppTO.getPersonalData().setGuardianAddressLine1(personalData.getGuardianAddressLine1());
							}
							if(personalData.getGuardianAddressLine2()!=null && !personalData.getGuardianAddressLine2().isEmpty()){
								adminAppTO.getPersonalData().setGuardianAddressLine2(personalData.getGuardianAddressLine2());
							}
							if(personalData.getGuardianAddressLine3()!=null && !personalData.getGuardianAddressLine3().isEmpty()){
								adminAppTO.getPersonalData().setGuardianAddressLine3(personalData.getGuardianAddressLine3());
							}
							if(personalData.getGuardianAddressZipCode()!=null && !personalData.getGuardianAddressZipCode().isEmpty()){
								adminAppTO.getPersonalData().setGuardianAddressZipCode(personalData.getGuardianAddressZipCode());
							}
							if(personalData.getCityByGuardianAddressCityId()!=null && !personalData.getCityByGuardianAddressCityId().isEmpty()){
								adminAppTO.getPersonalData().setCityByGuardianAddressCityId(personalData.getCityByGuardianAddressCityId());
							}
							if(personalData.getCountryByGuardianAddressCountryId()!=null){
								adminAppTO.getPersonalData().setCountryByGuardianAddressCountryId(personalData.getCountryByGuardianAddressCountryId().getId());
							}
							if(personalData.getStateByGuardianAddressStateId()!=null){
								adminAppTO.getPersonalData().setStateByGuardianAddressStateId(String.valueOf(personalData.getStateByGuardianAddressStateId().getId()));
							}
//							Education Qualifications
							if(personalData.getEdnQualifications()!=null && !personalData.getEdnQualifications().isEmpty()){
//								CourseTO courseTO = new CourseTO();
//								List<EdnQualificationTO> ednQualificationTOs = StudentEditHelper.getInstance().copyPropertiesValue(personalData.getEdnQualifications(), courseTO, 0);
//								if(ednQualificationTOs!=null && !ednQualificationTOs.isEmpty()){
//									adminAppTO.setEdnQualificationList(ednQualificationTOs);
//								}
								eduQualificationMap = prepareEduQualificationMap(personalData.getEdnQualifications(),admForm);
							}
						}
					}
					
					//setting the candidate name , dob,email, mobile no and resident category 
					if(personalData!=null && personalData.getFirstName()!=null && !personalData.getFirstName().isEmpty()){

						adminAppTO.getPersonalData().setFirstName(personalData.getFirstName());
					}else{
						adminAppTO.getPersonalData().setFirstName(admForm.getApplicantName());
					}
					if(personalData!=null && personalData.getDateOfBirth()!=null){
						adminAppTO.getPersonalData().setDob(CommonUtil.formatDates(personalData.getDateOfBirth()));
					}else{
						adminAppTO.getPersonalData().setDob(admForm.getApplicantDob());
					}
					if(personalData!=null && personalData.getResidentCategory()!=null ){
						adminAppTO.getPersonalData().setResidentCategory(String.valueOf(personalData.getResidentCategory().getId()));
					}else{
						adminAppTO.getPersonalData().setResidentCategory(admForm.getResidentCategoryForOnlineAppln());
					}
					if(personalData!=null && personalData.getEmail()!=null && !personalData.getEmail().isEmpty()){
						adminAppTO.getPersonalData().setEmail(personalData.getEmail());
					}else{
						adminAppTO.getPersonalData().setEmail(admForm.getEmail());
					}
				}
					
					if(admForm.getConfirmEmail()!=null && !admForm.getConfirmEmail().isEmpty()){
						adminAppTO.getPersonalData().setConfirmEmail(admForm.getConfirmEmail());
					}
					if(admForm.getMobileNo1()!=null && !admForm.getMobileNo1().isEmpty()){
						adminAppTO.getPersonalData().setMobileNo1(admForm.getMobileNo1());
					}
					if(admForm.getMobileNo2()!=null && !admForm.getMobileNo2().isEmpty()){
						adminAppTO.getPersonalData().setMobileNo2(admForm.getMobileNo2());
					}
					//adminAppTO.setPersonalData(personalDataTO);
					adminAppTO.setEntranceDetail(new CandidateEntranceDetailsTO());
					String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
					boolean workExpNeed=false;
					if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
						workExpNeed=true;
					}
					
					List<ApplicantWorkExperienceTO> workExpList=new ArrayList<ApplicantWorkExperienceTO>();
					if(workExpNeed)
					{
						for(int i=0;i<CMSConstants.MAX_CANDIDATE_WORKEXP;i++){
							ApplicantWorkExperienceTO expTo = new ApplicantWorkExperienceTO();
							workExpList.add(expTo);
						}
					}
					adminAppTO.setWorkExpList(workExpList);
					session.setAttribute("workExpListSize",workExpList.size());
//					 code added by sudhir
					if(eduQualificationMap!=null && !eduQualificationMap.isEmpty()){
						List<DocChecklist> exambos= txn.getExamtypes(Integer.parseInt(admForm.getCourseId()),year);
						List<EdnQualificationTO> eduQualificationList = getFinalEduQualificationList(exambos,eduQualificationMap);
						adminAppTO.setEdnQualificationList(eduQualificationList);
						session.setAttribute("eduQualificationListSize", eduQualificationList.size());
					}else{
						List<EdnQualificationTO> ednQualificationList = getEdnQualifications(admForm, year);
						adminAppTO.setEdnQualificationList(ednQualificationList);
						session.setAttribute("eduQualificationListSize", ednQualificationList.size());
					}
					
					OnlineApplicationHandler handler= OnlineApplicationHandler.getInstance();
					List<ApplnDocTO> reqList=handler.getRequiredDocList(String.valueOf(admForm.getCourseId()),year);
					adminAppTO.setEditDocuments(reqList);
					List<ApplicantLateralDetailsTO> lateralTos= new ArrayList<ApplicantLateralDetailsTO>();
					adminAppTO.setLateralDetails(lateralTos);
					List<ApplicantTransferDetailsTO> transferTos= new ArrayList<ApplicantTransferDetailsTO>();
					adminAppTO.setTransferDetails(transferTos);
					log.info("exit getNewStudent");
					return adminAppTO;
					
				}*/
				
				/**
				 * @param exambos
				 * @param eduQualificationMap
				 * @return
				 * @throws Exception
				 */
				public List<EdnQualificationTO> getFinalEduQualificationList( List<DocChecklist> exambos, Map<Integer, EdnQualificationTO> 
					eduQualificationMap)throws Exception {
					List<EdnQualificationTO> toList = null;
					if(exambos!=null && !exambos.isEmpty()){
					toList = new ArrayList<EdnQualificationTO>();
					List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
					int cnt =0;
					for (DocChecklist docChecklist : exambos) {
						if(docChecklist.getDocType()!=null){
							//setting old eduqlifition to list from map
							if(eduQualificationMap.containsKey(docChecklist.getDocType().getId())){
//								checking doctype is existing in map . if it is there adding to the list 
//								docCheckList may different for different courses thats why setting the docchecklist Id of the current course to the TO.
								EdnQualificationTO ednTo = eduQualificationMap.get(docChecklist.getDocType().getId());
								ednTo.setDocCheckListId(docChecklist.getId());
								ednTo.setCountId(cnt);
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsMarksCard() && !docChecklist.getIsConsolidatedMarks())
									ednTo.setConsolidated(false);
								else
									ednTo.setConsolidated(true);
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsPreviousExam()!=null && docChecklist.getIsPreviousExam()){ 
									ednTo.setLastExam(true);
									
								}else{
									ednTo.setLastExam(false);
								}
								toList.add(ednTo);
								cnt++;
							}
							
							//setting new eduqlifition to list
							else{

								EdnQualificationTO ednto = new EdnQualificationTO();
								ednto.setOrignalCheckList(docChecklist);
								ednto.setDocCheckListId(docChecklist.getId());
								ednto.setDocName(docChecklist.getDocType().getName());
								if(docChecklist.getDocType().getDisplayOrder() != null){
									ednto.setDisplayOrder(docChecklist.getDocType().getDisplayOrder());
								}
								int docTypeId = docChecklist.getDocType().getId();
								ednto.setDocTypeId(docTypeId);
								ednto.setCountId(cnt);
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsMarksCard() && !docChecklist.getIsConsolidatedMarks())
									ednto.setConsolidated(false);
								else
									ednto.setConsolidated(true);
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsMarksCard() &&  !docChecklist.getIsConsolidatedMarks() && docChecklist.getIsSemesterWise())
									ednto.setSemesterWise(true);
								else
									ednto.setSemesterWise(false);
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsPreviousExam()!=null && docChecklist.getIsPreviousExam()){ 
									ednto.setLastExam(true);
									
								}else{
									ednto.setLastExam(false);
								}
								if(docChecklist!=null && docChecklist.getIsActive() && docChecklist.getIsExamRequired()!=null && docChecklist.getIsExamRequired()){ 
									ednto.setExamConfigured(true);
								}else{
									ednto.setExamConfigured(false);
								}
								//doc type exam setup
								if(docChecklist.getDocType()!=null 
										&& docChecklist.getDocType().getDocTypeExamses()!=null ){
									List<DocTypeExamsTO> examTos= new ArrayList<DocTypeExamsTO>();
									for (DocTypeExams docExams : docChecklist.getDocType().getDocTypeExamses()) {
										if(docExams.getIsActive()!=null && docExams.getIsActive()){
											DocTypeExamsTO examto= new DocTypeExamsTO();
											examto.setId(docExams.getId());
											examto.setName(docExams.getName());
											examTos.add(examto);
										}
									}
									ednto.setExamTos(examTos);
									if(!examTos.isEmpty())
									{
										ednto.setExamRequired(true);
									}
									
								}
								
								Map<Integer,String> subjectMap =null;
								CandidateMarkTO markTo=new CandidateMarkTO();
								if(!ednto.isConsolidated() &&!ednto.isSemesterWise()){
									if(docChecklist.getCourse()!=null)
									 subjectMap = AdmissionFormHandler.getInstance().getDetailedSubjectsByCourse(String.valueOf(docChecklist.getCourse().getId()));
									if(subjectMap!=null){
										setDetailedSubjectsFormMap(subjectMap,markTo);
									}
									
									
									//class 12
									int doctypeId=CMSConstants.CLASS12_DOCTYPEID;
									
									
									//raghu write new
									
									
									if(docChecklist.getDocType().getId()==doctypeId){
										
										String language="Language";
										Map<Integer, String> admsubjectLangMap;
										
											admsubjectLangMap = AdmissionFormHandler.getInstance().get12thclassLangSubjects(docChecklist.getDocType().getName(),language);
											//find id from english in Map
									        String strValue="English";
									        String intKey = null;
									        for(Map.Entry entry: admsubjectLangMap.entrySet()){
									            if(strValue.equals(entry.getValue())){
									            	intKey =entry.getKey().toString();
									            	markTo.setSubjectid1(intKey);
									                break; //breaking because its one to one map
									            }
									        }
									          	
									}
								
									
									//deg
									int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
									
									
									if(docChecklist.getDocType().getId()==doctypeId1){
										

										//String Core="Core";
										//String Compl="Complementary";
										String Common="Common";
										//String Open="Open";
										//String Sub="Sub";
										
											//Map<Integer,String> admCoreMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Core);
											//Map<Integer,String> admComplMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Compl);
											Map<Integer,String> admCommonMap=AdmissionFormHandler.getInstance().get12thclassSub(docChecklist.getDocType().getName(),Common);
											//Map<Integer,String> admopenMap=AdmissionFormHandler.getInstance().get12thclassSub(docNmae,Open);
											//Map<Integer,String> admSubMap=AdmissionFormHandler.getInstance().get12thclassSub1(docNmae,Common);
											
											
											//find id from english in Map
											String strValue="English";
											String intKey = null;
											for(Map.Entry entry: admCommonMap.entrySet()){
											 if(strValue.equals(entry.getValue())){
												intKey =entry.getKey().toString();
												markTo.setSubjectid6(intKey);
												markTo.setSubjectid16(intKey);
												break; //breaking because its one to one map
											 	}
											}
													
									}
									
									
								}//check over
								
								ednto.setDetailmark(markTo);
								ednto.setLanguage(docChecklist.getIsIncludeLanguage());
								List<UniversityTO> universityTempList = new ArrayList<UniversityTO>();
								if(universityList!=null && !universityList.isEmpty()){
									for (UniversityTO universityTO : universityList) {
										
										if(universityTO.getDocTypeId() == docTypeId){
											universityTempList.add(universityTO);
										}
									}
									
								}
								ednto.setUniversityList(universityTempList);
								if(universityTempList!=null && universityTempList.isEmpty()){
									ednto.setUniversityId("Other");
									ednto.setInstitutionId("Other");
								}
								toList.add(ednto);
							cnt++;
					
							}
						}
						
					}
					Collections.sort(toList);
				}
				
				return toList;
			}
				/**
				 * @param ednQualifications
				 * @return
				 * @throws Exception 
				 */
				public Map<Integer, EdnQualificationTO> prepareEduQualificationMap( Set<EdnQualification> ednQualifications,OnlineApplicationForm admForm) throws Exception {
					Map<Integer, EdnQualificationTO> eduQualificationMap = new HashMap<Integer, EdnQualificationTO>();
					if(ednQualifications!=null && !ednQualifications.isEmpty()){
					Iterator<EdnQualification> iterator = ednQualifications.iterator();
					List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
					while (iterator.hasNext()) {
						EdnQualification ednQualificationBO = iterator.next();

						EdnQualificationTO ednQualificationTO = new EdnQualificationTO();
						//raghu remove comment
						if(ednQualificationBO.getId()!=0)
						ednQualificationTO.setId(ednQualificationBO.getId());
						
						ednQualificationTO.setCreatedBy(ednQualificationBO
								.getCreatedBy());
						ednQualificationTO.setCreatedDate(ednQualificationBO
								.getCreatedDate());
						if (ednQualificationBO.getState() != null) {
							ednQualificationTO.setStateId(String
									.valueOf(ednQualificationBO.getState().getId()));
							ednQualificationTO.setStateName(ednQualificationBO
									.getState().getName());
						} else if (ednQualificationBO.getIsOutsideIndia() != null
								&& ednQualificationBO.getIsOutsideIndia()) {
							ednQualificationTO.setStateId(CMSConstants.OUTSIDE_INDIA);
							ednQualificationTO.setStateName(CMSConstants.OUTSIDE_INDIA);
						}
						// copy doc type exam
						if (ednQualificationBO.getDocTypeExams() != null) {
							ednQualificationTO.setSelectedExamId(String
									.valueOf(ednQualificationBO.getDocTypeExams()
											.getId()));
							if (ednQualificationBO.getDocTypeExams().getName() != null)
								ednQualificationTO.setSelectedExamName(String
										.valueOf(ednQualificationBO.getDocTypeExams()
												.getName()));
							if(ednQualificationBO.getDocTypeExams().getDocType()!=null){
								ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocTypeExams().getDocType().getDisplayOrder());
							}
						}
						
						if (ednQualificationBO.getDocChecklist() != null
								&& ednQualificationBO.getDocChecklist().getDocType() != null) {
							AdmissionFormHandler handler = AdmissionFormHandler
									.getInstance();
							List<DocTypeExamsTO> examtos = handler
									.getDocExamsByID(ednQualificationBO
											.getDocChecklist().getDocType().getId());
							ednQualificationTO.setExamTos(examtos);
							if (examtos != null && !examtos.isEmpty())
								ednQualificationTO.setExamRequired(true);
							if(ednQualificationBO.getDocChecklist().getDocType()!=null){
								ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocChecklist().getDocType().getDisplayOrder());
							}
						}

						if (ednQualificationBO.getDocChecklist() != null
								&& ednQualificationBO.getDocChecklist()
										.getIsPreviousExam())
							ednQualificationTO.setLastExam(true);
						if (ednQualificationBO.getDocChecklist() != null
								&& ednQualificationBO.getDocChecklist()
										.getIsExamRequired())
							ednQualificationTO.setExamConfigured(true);
						if (ednQualificationBO.getDocChecklist() != null
								&& ednQualificationBO.getDocChecklist().getDocType() != null) {
							ednQualificationTO.setDocCheckListId(ednQualificationBO
									.getDocChecklist().getId());
							ednQualificationTO.setDocName(ednQualificationBO
									.getDocChecklist().getDocType().getName());
							ednQualificationTO.setDocTypeId(ednQualificationBO
									.getDocChecklist().getDocType().getId());
							ednQualificationTO.setSemesterWise(false);
							ednQualificationTO.setConsolidated(true);
							if (ednQualificationBO.getDocChecklist() != null
									&& ednQualificationBO.getDocChecklist()
											.getIsActive()
									&& ednQualificationBO.getDocChecklist()
											.getIsMarksCard()
									&& !ednQualificationBO.getDocChecklist()
											.getIsConsolidatedMarks()
									&& ednQualificationBO.getDocChecklist()
											.getIsSemesterWise()) {
								ednQualificationTO.setLanguage(ednQualificationBO
										.getDocChecklist().getIsIncludeLanguage());
								ednQualificationTO.setSemesterWise(true);
								ednQualificationTO.setConsolidated(false);
								Set<ApplicantMarksDetails> detailMarks = ednQualificationBO
										.getApplicantMarksDetailses();
								if (detailMarks != null && !detailMarks.isEmpty()) {
									Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
									ApplicantMarksDetails detailMarkBO = null;
									Iterator<ApplicantMarksDetails> markItr = detailMarks
											.iterator();
									while (markItr.hasNext()) {
										detailMarkBO = (ApplicantMarksDetails) markItr
												.next();
										ApplicantMarkDetailsTO markTO = new ApplicantMarkDetailsTO();
										convertApplicantMarkBOtoTO(detailMarkBO,
												markTO, ednQualificationTO.isLastExam());
										markdetails.add(markTO);
									}
									if (!markdetails.isEmpty()) {
										ednQualificationTO.setSemesterList(markdetails);
									} else {

										for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
											ApplicantMarkDetailsTO to = new ApplicantMarkDetailsTO();
											to.setSemesterNo(i);
											to.setLastExam(ednQualificationTO
													.isLastExam());
											markdetails.add(to);
										}
										ednQualificationTO.setSemesterList(markdetails);

									}
								} else {
									Set<ApplicantMarkDetailsTO> markdetails = new HashSet<ApplicantMarkDetailsTO>();
									for (int i = 1; i <= CMSConstants.MAX_CANDIDATE_SEMESTERS; i++) {
										ApplicantMarkDetailsTO to = new ApplicantMarkDetailsTO();
										to.setSemesterNo(i);
										to.setSemesterName("Semester" + i);
										to.setLastExam(ednQualificationTO.isLastExam());
										markdetails.add(to);
									}
									ednQualificationTO.setSemesterList(markdetails);
								}
							} else if (ednQualificationBO.getDocChecklist() != null
									&& ednQualificationBO.getDocChecklist()
											.getIsActive()
									&& ednQualificationBO.getDocChecklist()
											.getIsMarksCard()
									&& !ednQualificationBO.getDocChecklist()
											.getIsConsolidatedMarks()
									&& !ednQualificationBO.getDocChecklist()
											.getIsSemesterWise()) {
								ednQualificationTO.setConsolidated(false);
								Set<CandidateMarks> detailMarks = ednQualificationBO
										.getCandidateMarkses();
								if (detailMarks != null && !detailMarks.isEmpty()) {
									CandidateMarks detailMarkBO = null;
									Iterator<CandidateMarks> markItr = detailMarks
											.iterator();
									while (markItr.hasNext()) {
										detailMarkBO = (CandidateMarks) markItr.next();
										CandidateMarkTO markTO = new CandidateMarkTO();
										//convertDetailMarkBOtoTO(detailMarkBO, markTO);
										//raghu
										convertDetailMarkBOtoTO(detailMarkBO, markTO,ednQualificationBO);
										//set ugpattern
										//degree
										int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
										
										if(ednQualificationBO.getDocChecklist().getDocType().getId()==doctypeId1){
										admForm.setPatternofStudy(ednQualificationBO.getUgPattern());
										ednQualificationTO.setUgPattern(ednQualificationBO.getUgPattern());
										}
										ednQualificationTO.setDetailmark(markTO);
									}
								} else {
									ednQualificationTO.setDetailmark(null);
								}
							}
						}
						if (ednQualificationBO.getUniversityOthers() != null
								&& !ednQualificationBO.getUniversityOthers().isEmpty()) {
							ednQualificationTO.setUniversityId(OnlineApplicationHelper.OTHER);
							ednQualificationTO.setUniversityOthers(ednQualificationBO
									.getUniversityOthers());
							ednQualificationTO.setUniversityName(ednQualificationBO
									.getUniversityOthers());
						} else if (ednQualificationBO.getUniversity() != null
								&& ednQualificationBO.getUniversity().getId() != 0) {
					/*	if(ednQualificationBO.getDocChecklist().getIsPreviousExam()){
							boolean flag = CommonAjaxHandler.getInstance()
							.getMarkEntryAvailable(ednQualificationBO.getUniversity().getId(),Integer.parseInt(admForm.getCourseId()));
							if(flag){
								admForm.setMarksNoEntry(false);
							}
							else
							{
								admForm.setMarksNoEntry(true);
							}
						}*/
							ednQualificationTO
									.setUniversityId(String.valueOf(ednQualificationBO
											.getUniversity().getId()));
							ednQualificationTO.setUniversityName(ednQualificationBO
									.getUniversity().getName());
						}
						if (ednQualificationBO.getInstitutionNameOthers() != null
								&& !ednQualificationBO.getInstitutionNameOthers()
										.isEmpty()) {
							ednQualificationTO
									.setInstitutionId(OnlineApplicationHelper.OTHER);
							ednQualificationTO.setInstitutionName(ednQualificationBO
									.getInstitutionNameOthers());
							ednQualificationTO.setOtherInstitute(ednQualificationBO
									.getInstitutionNameOthers());
						} else if (ednQualificationBO.getCollege() != null
								&& ednQualificationBO.getCollege().getId() != 0) {
							ednQualificationTO.setInstitutionId(String
									.valueOf(ednQualificationBO.getCollege().getId()));
							ednQualificationTO.setInstitutionName(ednQualificationBO
									.getCollege().getName());
						}
						if (ednQualificationBO.getYearPassing() != null)
							ednQualificationTO.setYearPassing(ednQualificationBO
									.getYearPassing());
						if (ednQualificationBO.getMonthPassing() != null)
							ednQualificationTO.setMonthPassing(ednQualificationBO
									.getMonthPassing());
						ednQualificationTO.setPreviousRegNo(ednQualificationBO
								.getPreviousRegNo());
						if (ednQualificationBO.getMarksObtained() != null) {
							ednQualificationTO.setMarksObtained(String.valueOf(String
									.valueOf(ednQualificationBO.getMarksObtained()
											.doubleValue())));
						}
						if (ednQualificationBO.getTotalMarks() != null) {
							ednQualificationTO.setTotalMarks(String.valueOf(String
									.valueOf(ednQualificationBO.getTotalMarks()
											.doubleValue())));
						}
						if(ednQualificationBO.getNoOfAttempts()!=null)
						ednQualificationTO.setNoOfAttempts(ednQualificationBO.getNoOfAttempts());
						//raghu
						if(ednQualificationBO.getPercentage()!=null)
							ednQualificationTO.setPercentage(String.valueOf(ednQualificationBO.getPercentage()));

						/*	List<UniversityTO> universityList = UniversityHandler
									.getInstance().getUniversity();*/
							if (ednQualificationTO.getUniversityId() != null
									&& !ednQualificationTO.getUniversityId()
											.equalsIgnoreCase(OnlineApplicationHelper.OTHER)) {
								//raghu
								/*
								ednQualificationTO.setUniversityList(universityList);
								if (ednQualificationTO.getInstitutionId() != null
										&& !ednQualificationTO.getInstitutionId()
												.equalsIgnoreCase(
														OnlineApplicationHelper.OTHER)) {
									Iterator<UniversityTO> colItr = universityList
											.iterator();
									while (colItr.hasNext()) {
										UniversityTO unTO = (UniversityTO) colItr
												.next();
										if (unTO.getId() == Integer
												.parseInt(ednQualificationTO
														.getUniversityId())) {
											ednQualificationTO.setInstituteList(unTO
													.getCollegeTos());
										}
									}
								}
							*/}
							
							//raghu
							//ednQualificationTO.setUniversityList(universityList);
							
							List<UniversityTO> universityTempList = new ArrayList<UniversityTO>();
							if(universityList!=null && !universityList.isEmpty()){
								for (UniversityTO universityTO : universityList) {
									
									if(universityTO.getDocTypeId() == ednQualificationTO.getDocTypeId()){
										universityTempList.add(universityTO);
									}	
								}
								
							}
							
							ednQualificationTO.setUniversityList(universityTempList);
							
							
							
							if( universityList != null && !universityList.isEmpty()){
								if( universityList != null  && ednQualificationTO.getUniversityId()!=null && !ednQualificationTO.getUniversityId().equalsIgnoreCase(OnlineApplicationHelper.OTHER)){
									//ednQualificationTO.setUniversityList(universityList);
									if(ednQualificationTO.getInstitutionId()!=null && !ednQualificationTO.getInstitutionId().equalsIgnoreCase(OnlineApplicationHelper.OTHER)){
										for (UniversityTO unTO : universityList) {
											if(unTO.getId()== Integer.parseInt(ednQualificationTO.getUniversityId()))
											{
												ednQualificationTO.setInstituteList(unTO.getCollegeTos());
											}
										}
									}
								}
								
							}
							
				
							
							eduQualificationMap.put(ednQualificationTO.getDocTypeId(), ednQualificationTO);
					}
					}
					return eduQualificationMap;
				}
				/**
				 * prepare EdnQualificationTos for Admission form
				 * @param admForm
				 * @return
				 * @throws Exception
				 */
				public List<EdnQualificationTO> getEdnQualifications(OnlineApplicationForm admForm, int year) throws Exception {
					log.info("Enter getEdnQualifications ...");
					/*
					Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					int year=cal.get(cal.YEAR);
					*/
					IOnlineApplicationTxn txn= new OnlineApplicationImpl();
					List<DocChecklist> exambos= txn.getExamtypes(Integer.parseInt(admForm.getCourseId()),year);
					
					log.info("Exit getEdnQualifications ...");
					return this.prepareQualificationsFromExamBos(exambos);
				}
				/**
				 * @param examBos
				 * @return
				 */
				public List<DocTypeExamsTO> convertDocExamBosToTOS(
						List<DocTypeExams> examBos) {
					List<DocTypeExamsTO> examtos= new ArrayList<DocTypeExamsTO>();
					if(examBos!=null){
						Iterator<DocTypeExams> exmItr= examBos.iterator();
						while (exmItr.hasNext()) {
							DocTypeExams docExams = (DocTypeExams) exmItr.next();
							DocTypeExamsTO examTo= new DocTypeExamsTO();
							examTo.setId(docExams.getId());
							examTo.setName(docExams.getName());
							examtos.add(examTo);
						}
					}
					return examtos;
				}
				
			/*
			 * Single Application end
			 */

				
			public String getSearchQuery(int id, int mode) throws Exception{
				String query="from Course c where c.isActive=1 ";
				if(mode==1){
					query=query+" and c.id="+id;
				}else if(mode==2){
					query=query+" and c.program.id="+id+" and c.program.isActive=1 ";
				}else if(mode==3){
					query=query+" and c.program.programType.id="+id+" and c.program.programType.isActive=1 ";
				}
				query=query+" order by c.id";
				return query;
			}
			
			public AdmApplnTO convertBOToTO(List<ApplnDoc> appDocList,AdmApplnTO appDetails)throws Exception  {
					 List<String> originalList=new ArrayList<String>();
					 List<String> pendingDocList=new ArrayList<String>();
					 String submittedDate="";
					if(appDocList!=null && appDocList.size()!=0)
					{
						for(ApplnDoc appDoc:appDocList)
						{
							
							StringBuilder detailName =new StringBuilder();
							StringBuilder detailName1 =new StringBuilder();
							int semNo=0;
							if(appDoc.getSemNo()!=null && !appDoc.getSemNo().isEmpty()){
								semNo=Integer.parseInt(appDoc.getSemNo());
							}
							
							String type="";
							if(appDoc.getSemType()!=null){
								type=appDoc.getSemType();
								type=WordUtils.capitalize(type);
							}
							Set<ApplnDocDetails> details=appDoc.getApplnDocDetails();
							if(details!=null && !details.isEmpty()){
								List<Integer> subList=new ArrayList<Integer>();
								List<Integer> noList=new ArrayList<Integer>();
								for(ApplnDocDetails bo:details){
									if(bo.getIsHardCopySubmited()!=null && bo.getIsHardCopySubmited()){
//										detailName=detailName+type+bo.getSemesterNo()+": Submitted ";
										noList.add(Integer.parseInt(bo.getSemesterNo()));
										subList.add(Integer.parseInt(bo.getSemesterNo()));
									}
								}
								Collections.sort(subList);
								for(Integer sem:subList){
									detailName.append(" ").append(type).append(sem).append(": Submitted");
								}
								
								for(int i=1;i<=semNo;i++){
									if(!noList.contains(i))
									detailName1.append(" ").append(type).append(i).append(": Pending");
								}
							}
						if(appDoc.getHardcopySubmitted()){
								String name="";
								if(appDoc.getIsPhoto())
								{
									name=OnlineApplicationHelper.PHOTO;
								}
								else
								{
									if(appDoc.getDocType()!=null){
										name=appDoc.getDocType().getPrintName();
									}else if(appDoc.getName()!=null){
										name=appDoc.getName();
									}
								}
								if(!detailName.toString().isEmpty()){
									originalList.add(name+"-"+detailName);
								}else{
									originalList.add(name);
								}
								if(!detailName1.toString().isEmpty()){
									pendingDocList.add(name+"-"+detailName1);
								}
							}
							else
							{
								String name="";
								if(appDoc.getDocType()!=null && appDoc.getNotApplicable()!=true)
								{	
									  name=appDoc.getDocType().getPrintName();
									  if(appDoc.getIsPhoto())
										{
											name=OnlineApplicationHelper.PHOTO;
										}
								}else if(appDoc.getName()!=null && appDoc.getNotApplicable()!=true){
									name=appDoc.getName();
									if(appDoc.getIsPhoto())
									{
										name=OnlineApplicationHelper.PHOTO;
									}
								}	
								if(!detailName1.toString().isEmpty()){
									pendingDocList.add(name+"-"+detailName1);
								}else if(appDoc.getNotApplicable()!=true){
									pendingDocList.add(name);
								}
								if(!detailName.toString().isEmpty()){
									originalList.add(name+"-"+detailName);
								}
							}
						if(appDoc.getSubmissionDate()!=null)
						{
							submittedDate=CommonUtil.getStringDate(appDoc.getSubmissionDate());
						}
						  
							}
						
						appDetails.setOriginalList(originalList);
						appDetails.setPendingDocList(pendingDocList);
						appDetails.setSubmissionDate(submittedDate);
						
						}
					return appDetails;
				}
			
			
			
			/**
			 * @param admForm
			 * @return
			 * @throws Exception
			 */
			public CandidatePGIDetails convertToBo(OnlineApplicationForm admForm) throws Exception {
				CandidatePGIDetails bo=new CandidatePGIDetails();
				StringBuilder temp=new StringBuilder();
				//raghu
				
				if(!admForm.getAdditionalCharges().equalsIgnoreCase("0")){
					//check student curretn mail and payumail same or not 
					if(admForm.isAided()){
						if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							temp.append(admForm.getAdditionalCharges()).append("|")
								.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|")
								.append(admForm.getStatus()).append("|||||||||||")
								.append(admForm.getEmail()).append("|")
								.append(admForm.getApplicantName()).append("|")
								.append(admForm.getProductinfo()).append("|")
								.append(admForm.getAmount()).append("|")
								.append(admForm.getTxnid()).append("|")
								.append(CMSConstants.PGI_MERCHANT_ID_AIDED);
							
						}else if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && !admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							
							temp.append(admForm.getAdditionalCharges()).append("|")
								.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|")
								.append(admForm.getStatus()).append("|||||||||||")
								.append(admForm.getPaymentMail()).append("|")
								.append(admForm.getApplicantName()).append("|")
								.append(admForm.getProductinfo()).append("|")
								.append(admForm.getAmount()).append("|")
								.append(admForm.getTxnid()).append("|")
								.append(CMSConstants.PGI_MERCHANT_ID_AIDED);
						}else{
							temp.append(admForm.getAdditionalCharges()).append("|")
								.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|")
								.append(admForm.getStatus()).append("|||||||||||")
								.append(admForm.getEmail()).append("|")
								.append(admForm.getApplicantName()).append("|")
								.append(admForm.getProductinfo()).append("|")
								.append(admForm.getAmount()).append("|")
								.append(admForm.getTxnid()).append("|")
								.append(CMSConstants.PGI_MERCHANT_ID_AIDED);
						}
					}
					else{
						if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							temp.append(admForm.getAdditionalCharges()).append("|")
								.append(CMSConstants.PGI_SECURITY_ID).append("|")
								.append(admForm.getStatus()).append("|||||||||||")
								.append(admForm.getEmail()).append("|")
								.append(admForm.getApplicantName()).append("|")
								.append(admForm.getProductinfo()).append("|")
								.append(admForm.getAmount()).append("|")
								.append(admForm.getTxnid()).append("|")
								.append(CMSConstants.PGI_MERCHANT_ID);
							
						}else if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && !admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							
							temp.append(admForm.getAdditionalCharges()).append("|")
								.append(CMSConstants.PGI_SECURITY_ID).append("|")
								.append(admForm.getStatus()).append("|||||||||||")
								.append(admForm.getPaymentMail()).append("|")
								.append(admForm.getApplicantName()).append("|")
								.append(admForm.getProductinfo()).append("|")
								.append(admForm.getAmount()).append("|")
								.append(admForm.getTxnid()).append("|")
								.append(CMSConstants.PGI_MERCHANT_ID);
						}else{
							temp.append(admForm.getAdditionalCharges()).append("|").append(CMSConstants.PGI_SECURITY_ID).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getEmail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID);
						}
					}
					
				}else{
					if(admForm.isAided()){
						if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("")  && admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
						    temp.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getEmail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID_AIDED);
						
						}else if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && !admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							
							temp.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getPaymentMail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID_AIDED);
							
						}else {
							temp.append(CMSConstants.PGI_SECURITY_ID_AIDED).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getEmail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID_AIDED);
							
						}
					}
					else
					{
						if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("")  && admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
						    temp.append(CMSConstants.PGI_SECURITY_ID).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getEmail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID);
						
						}else if(admForm.getPaymentMail()!=null && !admForm.getPaymentMail().equalsIgnoreCase("") && !admForm.getPaymentMail().equalsIgnoreCase(admForm.getEmail())){
							
							temp.append(CMSConstants.PGI_SECURITY_ID).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getPaymentMail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID);
							
						}else {
							
							temp.append(CMSConstants.PGI_SECURITY_ID).append("|").append(admForm.getStatus()).append("|||||||||||").append(admForm.getEmail()).append("|").append(admForm.getApplicantName()).append("|").append(admForm.getProductinfo()).append("|").append(admForm.getAmount()).append("|").append(admForm.getTxnid()).append("|").append(CMSConstants.PGI_MERCHANT_ID);
							
						}
					}
				}
				
				//raghu write for pay e
				String hash=AdmissionFormHandler.getInstance().hashCal("SHA-512",temp.toString());
				
				if(admForm.getHash()!=null && hash!=null && !admForm.getHash().equals(hash)){
					throw  new BusinessException();
				}else{
					bo.setCandidateRefNo(admForm.getTxnid());
					bo.setTxnRefNo(admForm.getPayuMoneyId());
					bo.setTxnAmount(new BigDecimal(admForm.getAmount()));
					bo.setAdditionalCharges(new BigDecimal(admForm.getAdditionalCharges()));
					bo.setBankRefNo(admForm.getBank_ref_num());
					bo.setTxnStatus(admForm.getStatus());
					bo.setErrorStatus(admForm.getError1());
					//raghu setting current date
					bo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy")));
					bo.setMode(admForm.getMode1());
					bo.setUnmappedStatus(admForm.getUnmappedstatus());
					bo.setMihpayId(admForm.getMihpayid());
					bo.setPgType(admForm.getPG_TYPE());
					//raghu new
					bo.setPaymentEmail(admForm.getPaymentMail());
					admForm.setPgiStatus("Payment Successful");
					admForm.setTxnAmt(admForm.getAmount());
					if(admForm.getPayuMoneyId() != null)
						admForm.setTxnRefNo(admForm.getPayuMoneyId());
					else
						admForm.setTxnRefNo(admForm.getTxnid()+""+admForm.getBank_ref_num());
					admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy"));
					
				}
				
				return bo;
			
			}
			
			
			
			
			
			
			
			
				
				public List<Object[]> getDateSessionDetailsOfWorkLocation(int workLocationId) throws Exception {
					List<Object[]> examScheduleVenuesList;
					Session session = null;
					try{
						session = HibernateUtil.getSession();
						Query query = session.createSQLQuery("select exam_schedule_date.id as sessionid,exam_schedule_date.exam_date,exam_schedule_date.session, " +
															 " sum(exam_venue.capacity) from exam_schedule_venue" +
															 " left join exam_schedule_date on exam_schedule_venue.exam_schedule_date_id = exam_schedule_date.id" +
															 " left join exam_venue on exam_schedule_venue.exam_venue_id = exam_venue.id" +
															 " where exam_venue.work_location_id ="+workLocationId+" and exam_schedule_date.is_active=1" +
															 " and exam_schedule_venue.is_active=1 and exam_venue.is_active=1 " +
															 " and  exam_schedule_date.exam_date>=curdate() " +
															 " group by exam_schedule_date.id " +
															 " order by exam_schedule_date.exam_date asc,exam_schedule_date.session_order");
						examScheduleVenuesList= query.list();
					}catch (Exception e) {
						if (session != null){
							session.flush();
							//session.close();
						}	
						throw  new ApplicationException(e);
					}
					return examScheduleVenuesList;
				}
			
			@SuppressWarnings({ "unused", "deprecation" })
			private boolean validatefutureDate(String dateString) {
				log.info("enter validatefutureDate..");
				boolean result=false;
				String formattedString = CommonUtil.ConvertStringToDateFormat(
						dateString, "dd/MM/yyyy",
						"MM/dd/yyyy");
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
				if(date.compareTo(origdate) == 1){
					result=false;
				}else if(date.compareTo(origdate) == 0){
					result=false; 
				}else if(date.compareTo(origdate) == -1)
					result=true;
				return result;

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
	
	
	public String splitString(String value) {
		StringBuffer appendedvalue = new StringBuffer("");
		int length = value.length();

		String[] temp = new String[length];
		int begindex = 0, endindex = 20;
		int count = 0;

		while (true) {
			if (endindex < length) {
				temp[count] = value.substring(begindex, endindex);
				begindex = begindex + 20;
				endindex = endindex + 20;
				appendedvalue = appendedvalue.append(temp[count] + " ");

			} else {
				if (count == 0)
					temp[count] = value.substring(0, length);
				temp[count] = value.substring(begindex, length);
				appendedvalue = appendedvalue.append(temp[count]);
				break;
			}
			count++;
		}
		log.error("ending of splitString method in CourseHelper");
		return appendedvalue.toString();
	}
	
	public List<CandidatePGIDetailsTO> covertPGIBOToTO(List<CandidatePGIDetails> pgiBOS) throws Exception {
		
		List<CandidatePGIDetailsTO> pgiTOs = new ArrayList<CandidatePGIDetailsTO>();
		Iterator<CandidatePGIDetails> it = pgiBOS.iterator();
		while(it.hasNext()) {
			
			CandidatePGIDetails bo = it.next();
			CandidatePGIDetailsTO to = new CandidatePGIDetailsTO();
			
			to.setCandidateRefNo(bo.getCandidateRefNo());
			to.setTxnStatus(bo.getTxnStatus());
			to.setTxnAmount(bo.getTxnAmount());
			to.setTxnRefNo(bo.getTxnRefNo());
			
			pgiTOs.add(to);
		}
		
		return pgiTOs;
	}
	
	public CandidatePGIDetails getPGIBOForBypass(OnlineApplicationForm admForm) throws Exception {
		CandidatePGIDetails bo=new CandidatePGIDetails();
		
		bo.setCandidateRefNo(admForm.getTxnid());
		bo.setTxnRefNo(admForm.getPayuMoneyId());
		bo.setTxnAmount(new BigDecimal(admForm.getAmount()));
		bo.setAdditionalCharges(new BigDecimal(admForm.getAdditionalCharges()));
		bo.setBankRefNo(admForm.getBank_ref_num());
		bo.setTxnStatus(admForm.getStatus());
		bo.setErrorStatus(admForm.getError1());
		//raghu setting current date
		bo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy")));
		bo.setMode(admForm.getMode1());
		bo.setUnmappedStatus(admForm.getUnmappedstatus());
		bo.setMihpayId(admForm.getMihpayid());
		bo.setPgType(admForm.getPG_TYPE());
		//raghu new
		bo.setPaymentEmail(admForm.getPaymentMail());
		admForm.setPgiStatus("Payment Successful");
		admForm.setTxnAmt(admForm.getAmount());
		if(admForm.getPayuMoneyId() != null)
			admForm.setTxnRefNo(admForm.getPayuMoneyId());
		else
			admForm.setTxnRefNo(admForm.getTxnid()+""+admForm.getBank_ref_num());
		admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy"));
		
		return bo;
	
	}

	public List<coursesToPrint> convertcourseBo(List<Object[]> courseList, OnlineApplicationForm admForm) {
		Iterator<Object[]> itr= courseList.iterator();
		List<coursesToPrint> courses=new ArrayList<coursesToPrint>();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			coursesToPrint course=new coursesToPrint();
			course.setCourse(obj[0].toString());
			course.setAidedOrNot((Boolean) obj[1]);
			course.setCourseid((Integer) obj[2]);
			courses.add(course);
		}
		admForm.setCoursesList(courses);
		return courses;
	}
	public Boolean checkAided(List<Object[]> courseList, String courseId) {
		Iterator<Object[]> itr= courseList.iterator();
		List<coursesToPrint> courses=new ArrayList<coursesToPrint>();
		boolean result = false;
		int frmCid=Integer.parseInt(courseId);
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			coursesToPrint course=new coursesToPrint();
			course.setCourse(obj[0].toString());
			course.setAidedOrNot((Boolean) obj[1]);
			course.setCourseid((Integer) obj[2]);
			courses.add(course);
		}
		Iterator<coursesToPrint> courseItr=courses.iterator();
		while (courseItr.hasNext()) {
			coursesToPrint course = (coursesToPrint) courseItr.next();
			int ExCid=course.getCourseid();
			if (ExCid==frmCid) {
				result=course.isAidedOrNot();
			}
			
		}
		return result;
	}
}

