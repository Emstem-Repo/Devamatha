package com.kp.cms.helpers.admission;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.Session;

import com.billdesk.pgidsk.PGIUtil;
import com.kp.cms.actions.admission.AdmissionFormAction;
import com.kp.cms.actions.employee.EmployeeInfoActionNew;
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
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ApplnDocDetails;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.DioceseBO;
import com.kp.cms.bo.admin.District;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentExtracurricular;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admin.StudentVehicleDetails;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.SelectionDateNotAvailableException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.ProgramHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CandidatePrerequisiteMarksTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CoursePrerequisiteTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.PrerequisiteTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StudentVehicleDetailsTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AddressTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmissionFormHelper {
	private static final Log log = LogFactory.getLog(AdmissionFormHelper.class);
	private static final String OTHER="Other";
	private static final String PHOTO="Photo";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	public static volatile AdmissionFormHelper self=null;
	public static AdmissionFormHelper getInstance(){
		if(self==null){
			self= new AdmissionFormHelper();
		}
		return self;
	}
	private AdmissionFormHelper(){
		
	}

	
	/**
	 * prepares student personaldata to from form
	 * @param admForm
	 * @return
	 */
	public PersonalDataTO getStudentPersonaldataTofromForm(AdmissionFormForm admForm) {
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
		if (admForm.getBirthState()!=null && admForm.getBirthState().equalsIgnoreCase(AdmissionFormHelper.OTHER)) {
			personaldataTO.setBirthState(AdmissionFormHelper.OTHER);
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
		
		if (admForm.getCastCategory()!=null && admForm.getCastCategory().equalsIgnoreCase(AdmissionFormHelper.OTHER) ) {
			personaldataTO.setCaste(null);
			personaldataTO.setCasteOthers(admForm.getOtherCastCategory());
		}else if(admForm.getCastCategory()!=null && !StringUtils.isEmpty( admForm.getCastCategory()) && StringUtils.isNumeric( admForm.getCastCategory())){
			CasteTO casteTO = new CasteTO();
			casteTO.setCasteId(Integer.parseInt(admForm.getCastCategory()));
			personaldataTO.setCaste(casteTO);
		}
		personaldataTO.setRuralUrban(admForm.getAreaType());
		ReligionTO religionTO= new ReligionTO();
		if (admForm.getReligionId()!=null && admForm.getReligionId().equalsIgnoreCase(AdmissionFormHelper.OTHER) ) {
			personaldataTO.setReligion(null);
			personaldataTO.setReligionOthers(admForm.getOtherReligion());
		}else if(admForm.getReligionId()!=null){
			
			religionTO.setReligionId(Integer.parseInt(admForm.getReligionId()));
			personaldataTO.setReligion(religionTO);
		}
		
		if (admForm.getSubReligion()!=null && admForm.getSubReligion().equalsIgnoreCase(AdmissionFormHelper.OTHER)) {
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
}
	
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
//								InputStream photoin=AdmissionFormHelper.class.getClassLoader().getResourceAsStream(CMSConstants.DEFAULT_PHOTO_PATH);
								InputStream photoin=null;
								if(photoin!=null){
									byte[] fileData= new byte[photoin.available()];
									photoin.read(fileData, 0, photoin.available());
									uploadBO.setDocument(fileData);
									uploadBO.setName(AdmissionFormHelper.PHOTO);
									uploadBO.setContentType("image/gif");
									uploadbolist.add(uploadBO);	
								}
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
		personaldataBO.setIsmgquota(studentpersonaldata.getIsmgquota());
		if(studentpersonaldata.getGender()!=null){
		personaldataBO.setGender(studentpersonaldata.getGender().toUpperCase());
		}
		personaldataBO.setDateOfBirth(studentpersonaldata.getDateOfBirth());
		ResidentCategory res_cat=null;
		if (studentpersonaldata.getResidentCategory()!=null && !StringUtils.isEmpty(studentpersonaldata.getResidentCategory()) && StringUtils.isNumeric(studentpersonaldata.getResidentCategory())) {
			res_cat = new ResidentCategory();
			res_cat.setId(Integer.parseInt(studentpersonaldata.getResidentCategory()));
		}
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
		if(studentpersonaldata.getReligion()!=null){
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
	public List<CandidatePreference> convertPreferenceTOToBO(PreferenceTO firstpref,
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
	}
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
			Iterator<DocChecklist> itr= exambos.iterator();
			int cnt=0;
			while (itr.hasNext()) {
				DocChecklist examType = (DocChecklist) itr.next();
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
						if(examType!=null && examType.getIsActive()){ 
							if(!examType.getIsPreviousExam()){
								ednto.setLastExam(false);
							}else{
								ednto.setLastExam(true);
							}
						}
						if(examType!=null && examType.getIsActive()){ 
							if(!examType.getIsExamRequired()){
								ednto.setExamConfigured(false);
							}else{
								ednto.setExamConfigured(true);
							}
						}
						//doc type exam setup
						if(examType.getDocType()!=null 
								&& examType.getDocType().getDocTypeExamses()!=null ){
							List<DocTypeExamsTO> examTos= new ArrayList<DocTypeExamsTO>();
							Iterator<DocTypeExams> examItr=examType.getDocType().getDocTypeExamses().iterator();
							while (examItr.hasNext()) {
								DocTypeExams docExams = (DocTypeExams) examItr.next();
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
						}
						ednto.setDetailmark(markTo);
						ednto.setLanguage(examType.getIsIncludeLanguage());
						List<UniversityTO> universityTempList = new ArrayList<UniversityTO>();
						Iterator<UniversityTO> uniItr = universityList.iterator();
						
						UniversityTO universityTO;
						while (uniItr.hasNext()){
							universityTO = uniItr.next();
							if(universityTO.getDocTypeId() == docTypeId){
								universityTempList.add(universityTO);
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
	 */
	public List<EdnQualification> getEducationDetailsBO(
			AdmissionFormForm admForm,boolean isPresidance) {
		log.info("enter getEducationDetailsBO" );
		List<EdnQualification> educationDetails= new ArrayList<EdnQualification>();
		List<EdnQualificationTO> qualifications=admForm.getQualifications();
		if(qualifications!=null){
			Iterator<EdnQualificationTO> itr= qualifications.iterator();
			while (itr.hasNext()) {
				EdnQualificationTO qualificationTO = (EdnQualificationTO) itr
						.next();
				EdnQualification ednbo=new EdnQualification();
				ednbo.setCreatedBy(admForm.getUserId());
				ednbo.setCreatedDate(new Date());
				if (qualificationTO.getInstitutionId()!=null && !StringUtils.isEmpty(qualificationTO.getInstitutionId())&& StringUtils.isNumeric(qualificationTO.getInstitutionId())) {
					College col= new College();
					col.setId(Integer.parseInt(qualificationTO.getInstitutionId()));
					ednbo.setCollege(col);
				}else{
					ednbo.setInstitutionNameOthers(qualificationTO.getOtherInstitute());
				}
				
				if(qualificationTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualificationTO.getMarksObtained()))
					ednbo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
				ednbo.setNoOfAttempts(qualificationTO.getNoOfAttempts());
				if(qualificationTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualificationTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualificationTO.getTotalMarks()))
				ednbo.setTotalMarks(new BigDecimal(qualificationTO.getTotalMarks()));
				if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& StringUtils.isNumeric(qualificationTO.getStateId())){
					State ednst= new State();
					ednst.setId(Integer.parseInt(qualificationTO.getStateId()));
					ednbo.setState(ednst);
				}else if(qualificationTO.getStateId()!=null && !StringUtils.isEmpty(qualificationTO.getStateId())&& qualificationTO.getStateId().equalsIgnoreCase(CMSConstants.OUTSIDE_INDIA)){
					ednbo.setState(null);
					ednbo.setIsOutsideIndia(true);
				}
			
			
				if(ednbo.getMarksObtained()!=null && ednbo.getTotalMarks()!=null){
					float percentageMarks = ednbo.getMarksObtained().floatValue()/ednbo.getTotalMarks().floatValue()*100 ;
					ednbo.setPercentage(new BigDecimal(percentageMarks));
				}else if(isPresidance){
					if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty())
					ednbo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
				}
				
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

						if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1TotalMarks()))
						detailMark.setSubject1TotalMarks(new BigDecimal(
								detailMarkto.getSubject1TotalMarks()));
						if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2TotalMarks()))
						detailMark.setSubject2TotalMarks(new BigDecimal(
								detailMarkto.getSubject2TotalMarks()));
						if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3TotalMarks()))
						detailMark.setSubject3TotalMarks(new BigDecimal(
								detailMarkto.getSubject3TotalMarks()));
						if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4TotalMarks()))
						detailMark.setSubject4TotalMarks(new BigDecimal(
								detailMarkto.getSubject4TotalMarks()));
						if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5TotalMarks()))
						detailMark.setSubject5TotalMarks(new BigDecimal(
								detailMarkto.getSubject5TotalMarks()));
						if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6TotalMarks()))
						detailMark.setSubject6TotalMarks(new BigDecimal(
								detailMarkto.getSubject6TotalMarks()));
						if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7TotalMarks()))
						detailMark.setSubject7TotalMarks(new BigDecimal(
								detailMarkto.getSubject7TotalMarks()));
						if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8TotalMarks()))
						detailMark.setSubject8TotalMarks(new BigDecimal(
								detailMarkto.getSubject8TotalMarks()));
						if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9TotalMarks()))
						detailMark.setSubject9TotalMarks(new BigDecimal(
								detailMarkto.getSubject9TotalMarks()));
						if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10TotalMarks()))
						detailMark.setSubject10TotalMarks(new BigDecimal(
								detailMarkto.getSubject10TotalMarks()));
						if(detailMarkto.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject11TotalMarks()))
						detailMark.setSubject11TotalMarks(new BigDecimal(
								detailMarkto.getSubject11TotalMarks()));
						if(detailMarkto.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject12TotalMarks()))
						detailMark.setSubject12TotalMarks(new BigDecimal(
								detailMarkto.getSubject12TotalMarks()));
						if(detailMarkto.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject13TotalMarks()))
						detailMark.setSubject13TotalMarks(new BigDecimal(
								detailMarkto.getSubject13TotalMarks()));
						if(detailMarkto.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject14TotalMarks()))
						detailMark.setSubject14TotalMarks(new BigDecimal(
								detailMarkto.getSubject14TotalMarks()));
						if(detailMarkto.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject15TotalMarks()))
						detailMark.setSubject15TotalMarks(new BigDecimal(
								detailMarkto.getSubject15TotalMarks()));
						if(detailMarkto.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject16TotalMarks()))
						detailMark.setSubject16TotalMarks(new BigDecimal(
								detailMarkto.getSubject16TotalMarks()));
						if(detailMarkto.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject17TotalMarks()))
						detailMark.setSubject17TotalMarks(new BigDecimal(
								detailMarkto.getSubject17TotalMarks()));
						if(detailMarkto.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject18TotalMarks()))
						detailMark.setSubject18TotalMarks(new BigDecimal(
								detailMarkto.getSubject18TotalMarks()));
						if(detailMarkto.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject19TotalMarks()))
						detailMark.setSubject19TotalMarks(new BigDecimal(
								detailMarkto.getSubject19TotalMarks()));
						if(detailMarkto.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject20TotalMarks()))
						detailMark.setSubject20TotalMarks(new BigDecimal(
								detailMarkto.getSubject20TotalMarks()));
						
						if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1ObtainedMarks()))
						detailMark.setSubject1ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject1ObtainedMarks()));
						if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2ObtainedMarks()))
						detailMark.setSubject2ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject2ObtainedMarks()));
						if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3ObtainedMarks()))
						detailMark.setSubject3ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject3ObtainedMarks()));
						if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4ObtainedMarks()))
						detailMark.setSubject4ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject4ObtainedMarks()));
						if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5ObtainedMarks()))
						detailMark.setSubject5ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject5ObtainedMarks()));
						if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6ObtainedMarks()))
						detailMark.setSubject6ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject6ObtainedMarks()));
						if(detailMarkto.getSubject7ObtainedMarks()!=null  && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7ObtainedMarks()))
						detailMark.setSubject7ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject7ObtainedMarks()));
						if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8ObtainedMarks()))
						detailMark.setSubject8ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject8ObtainedMarks()));
						if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9ObtainedMarks()))
						detailMark.setSubject9ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject9ObtainedMarks()));
						if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10ObtainedMarks()))
						detailMark.setSubject10ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject10ObtainedMarks()));
						if(detailMarkto.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject11ObtainedMarks()))
						detailMark.setSubject11ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject11ObtainedMarks()));
						if(detailMarkto.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject12ObtainedMarks()))
						detailMark.setSubject12ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject12ObtainedMarks()));
						if(detailMarkto.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject13ObtainedMarks()))
						detailMark.setSubject13ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject13ObtainedMarks()));
						if(detailMarkto.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject14ObtainedMarks()))
						detailMark.setSubject14ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject14ObtainedMarks()));
						if(detailMarkto.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject15ObtainedMarks()))
						detailMark.setSubject15ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject15ObtainedMarks()));
						if(detailMarkto.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject16ObtainedMarks()))
						detailMark.setSubject16ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject16ObtainedMarks()));
						if(detailMarkto.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject17ObtainedMarks()))
						detailMark.setSubject17ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject17ObtainedMarks()));
						if(detailMarkto.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject18ObtainedMarks()))
						detailMark.setSubject18ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject18ObtainedMarks()));
						if(detailMarkto.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject19ObtainedMarks()))
						detailMark.setSubject19ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject19ObtainedMarks()));
						if(detailMarkto.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject20ObtainedMarks()))
						detailMark.setSubject20ObtainedMarks(new BigDecimal(
								detailMarkto.getSubject20ObtainedMarks()));
						if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalMarks())){
						detailMark.setTotalMarks(new BigDecimal(detailMarkto
								.getTotalMarks()));
						totalmark=detailMark.getTotalMarks().doubleValue();
						}
						if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())){
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
							if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject1TotalMarks());
								if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1ObtainedMarks()))
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
							if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject2TotalMarks());
								if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2ObtainedMarks()))
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
							if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject3TotalMarks());
								if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3ObtainedMarks()))
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
							if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject4TotalMarks());
								if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4ObtainedMarks()))
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
							if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject5TotalMarks());
								if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5ObtainedMarks()))
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
							if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject6TotalMarks());
								if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6ObtainedMarks()))
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
							if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject7TotalMarks());
								if(detailMarkto.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7ObtainedMarks()))
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
							if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject8TotalMarks());
								if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8ObtainedMarks()))
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
							if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject9TotalMarks());
								if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9ObtainedMarks()))
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
							if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject10TotalMarks());
								if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10ObtainedMarks()))
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
							
							if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1TotalMarks()) && detailMarkto.getSubject1_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject1TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject1_languagewise_TotalMarks());
								if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1ObtainedMarks()) && detailMarkto.getSubject1_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2TotalMarks())&& detailMarkto.getSubject2_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject2TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject2_languagewise_TotalMarks());
								if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2ObtainedMarks())&& detailMarkto.getSubject2_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3TotalMarks())&& detailMarkto.getSubject3_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject3TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject3_languagewise_TotalMarks());
								if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3ObtainedMarks())&& detailMarkto.getSubject3_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4TotalMarks())&& detailMarkto.getSubject4_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject4TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject4_languagewise_TotalMarks());
								if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4ObtainedMarks())&& detailMarkto.getSubject4_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5TotalMarks())&& detailMarkto.getSubject5_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject5TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject5_languagewise_TotalMarks());
								if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5ObtainedMarks())&& detailMarkto.getSubject5_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6TotalMarks())&& detailMarkto.getSubject6_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject6TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject6_languagewise_TotalMarks());
								if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6ObtainedMarks())&& detailMarkto.getSubject6_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7TotalMarks())&& detailMarkto.getSubject7_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject7TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject7_languagewise_TotalMarks());
								if(detailMarkto.getSubject7ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7ObtainedMarks())&& detailMarkto.getSubject7_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8TotalMarks())&& detailMarkto.getSubject8_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject8TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject8_languagewise_TotalMarks());
								if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8ObtainedMarks()) && detailMarkto.getSubject8_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject8_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9TotalMarks())&& detailMarkto.getSubject9_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject9TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject9_languagewise_TotalMarks());
								if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9ObtainedMarks()) && detailMarkto.getSubject9_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject9_languagewise_ObtainedMarks())){
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
							if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10TotalMarks())&& detailMarkto.getSubject10_languagewise_TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10_languagewise_TotalMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10_languagewise_TotalMarks())){
								totalmark=Integer.parseInt(detailMarkto.getSubject10TotalMarks());
								totalLanguagewisemark = Integer.parseInt(detailMarkto.getSubject10_languagewise_TotalMarks());
								if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10ObtainedMarks()) && detailMarkto.getSubject10_languagewise_ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10_languagewise_ObtainedMarks()) && CommonUtil.isValidDecimal(detailMarkto.getSubject10_languagewise_ObtainedMarks())){
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
		 photoTo.setName(AdmissionFormHelper.PHOTO);
		 photoTo.setDocName(AdmissionFormHelper.PHOTO);
		 photoTo.setPrintName(AdmissionFormHelper.PHOTO);
		 doclist.add(photoTo);
			
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
				to.setName(currency.getCurrencyCode());
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
	public List<IncomeTO> convertIncomeBOToTO(List<Income> incomebos) {
		log.info("enter convertIncomeBOToTO" );
		List<IncomeTO> incomelist= new ArrayList<IncomeTO>();
		if(incomebos!=null){
			Iterator<Income> itr= incomebos.iterator();
			while (itr.hasNext()) {
				Income income = (Income) itr.next();
				IncomeTO to= new IncomeTO();
				to.setId(income.getId());
				to.setIncomeRange(income.getIncomeRange());
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
	public List<CoursePrerequisiteTO> convertRequisiteBOstoTOS(List<CoursePrerequisite> requisiteBOs)throws Exception
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
	}
	/**
	 * @param admForm
	 * @return
	 */
	public Set<ApplicantWorkExperience> convertExperienceTostoBOs(
			AdmissionFormForm admForm) {
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
	}

	
	//edit section started
	/**
	 * @param applicantDetail
	 * @param admForm 
	 * @return
	 */
	public AdmAppln getApplicantBO(AdmApplnTO applicantDetail, AdmissionFormForm admForm,boolean isPresidance) throws Exception {
		log.info("enter getApplicantBO" );
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		AdmAppln appBO=null;
		if (applicantDetail!=null) {
			appBO= new AdmAppln();
			appBO.setId(applicantDetail.getId());
			appBO.setApplnNo(applicantDetail.getApplnNo());
			//added by vishnu
			appBO.setAdmissionNumber(applicantDetail.getAdmissionNumber());
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
			appBO.setRemarks(applicantDetail.getRemark());
			appBO.setIsCancelled(applicantDetail.getIsCancelled());
			appBO.setIsFreeShip(applicantDetail.getIsFreeShip());
			//added for challan verification
			appBO.setIsChallanVerified(applicantDetail.getIsChallanVerified());
			//addition for challan verification completed
			appBO.setIsLig(applicantDetail.getIsLIG());
			
			if(applicantDetail.getIsAided()!=null)
				appBO.setIsAided(applicantDetail.getIsAided());
			
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
					
		
			if(applicantDetail.getAdmissionDate()!= null){
				appBO.setAdmissionDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getAdmissionDate()));
			}
			if(applicantDetail.getAmount()!=null && !StringUtils.isEmpty(applicantDetail.getAmount().trim()))
			appBO.setAmount(new BigDecimal(applicantDetail.getAmount()));
			
			appBO.setTcNo(applicantDetail.getTcNo());
			if(applicantDetail.getTcDate()!=null && !StringUtils.isEmpty(applicantDetail.getTcDate()) && CommonUtil.isValidDate(applicantDetail.getTcDate()))
			appBO.setTcDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getTcDate()));
			appBO.setMarkscardNo(applicantDetail.getMarkscardNo());
			if(applicantDetail.getMarkscardDate()!=null && !StringUtils.isEmpty(applicantDetail.getMarkscardDate()) && CommonUtil.isValidDate(applicantDetail.getMarkscardDate()))
			appBO.setMarkscardDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getMarkscardDate()));
			
			if(admForm.getInterviewSelectionDate()!= null && !admForm.getInterviewSelectionDate().isEmpty()){
				InterviewSelectionSchedule iss=new InterviewSelectionSchedule();
				iss.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
				txn.getDateFromSelectionProcessId(admForm.getInterviewSelectionDate(), admForm);
				//admForm.setSelectedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(iss.getSelectionProcessDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
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
				//int examCenterId= txn.getExamCenterFromInterviewVenue(admForm.getInterviewVenue(),admForm);
				examCenter.setId(Integer.parseInt(admForm.getInterviewVenue()));
				//admForm.setSelectedVenue(examCenter.getCenter());
				appBO.setExamCenter(examCenter);
			}else
			{
				if(applicantDetail.getExamCenterId()>0){
					ExamCenter examCenter = new ExamCenter();
					examCenter.setId(applicantDetail.getExamCenterId());
					appBO.setExamCenter(examCenter);
				}
			}
			
			CourseTO crsto=applicantDetail.getCourse();
			
			if (crsto!=null) {
				Course crs=new Course();
				ProgramType programType = new ProgramType();
				programType.setId(crsto.getProgramTypeId());
				
				Program program = new Program();
				program.setProgramType(programType);
				program.setId(crsto.getProgramId());
				crs.setProgram(program);
				crs.setId(crsto.getId());
				
				appBO.setCourse(crs);
				
			}
			CourseTO crsto1=applicantDetail.getSelectedCourse();
			
			if (crsto1!=null) {
				Course crs=new Course();
				crs.setId(crsto1.getId());
				appBO.setCourseBySelectedCourseId(crs);
			}
			//code added by chandra
			CourseTO crsto2=applicantDetail.getAdmittedCourse();
			
			if (crsto2!=null) {
				Course crs=new Course();
				crs.setId(crsto2.getId());
				appBO.setAdmittedCourseId(crs);
			}
			
			appBO.setAppliedYear(applicantDetail.getAppliedYear());
			//for printing
			if(applicantDetail.getAppliedYear()!=null)
			admForm.setApplicationYear(String.valueOf(applicantDetail.getAppliedYear()));
			if(applicantDetail.getTotalWeightage()!=null)
			appBO.setTotalWeightage(new BigDecimal(applicantDetail.getTotalWeightage()));
			if(applicantDetail.getWeightageAdjustMark()!=null)
				appBO.setWeightageAdjustedMarks(new BigDecimal(applicantDetail.getWeightageAdjustMark()));
			appBO.setIsSelected(applicantDetail.getIsSelected());
			//Added By Manu
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
			appBO.setIsBypassed(applicantDetail.getIsBypassed());
			appBO.setIsInterviewSelected(applicantDetail.getIsInterviewSelected());
			appBO.setCandidatePrerequisiteMarks(applicantDetail.getCandidatePrerequisiteMarks());
			appBO.setStudentQualifyexamDetails(applicantDetail.getOriginalQualDetails());
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
		/*	if(applicantDetail.getExamCenterId()!= 0){
				ExamCenter examCenter = new ExamCenter();
				examCenter.setId(applicantDetail.getExamCenterId());
				appBO.setExamCenter(examCenter);
			}*/
			
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
			//appBO.setCreatedDate(applicantDetail.getCreatedDate());
			appBO.setCreatedDate(new Date());
			appBO.setModifiedBy(admForm.getUserId());
			appBO.setLastModifiedDate(new Date());
			 if(applicantDetail.getEntranceDetail()!=null){
				 Set<CandidateEntranceDetails> entranceSet=new HashSet<CandidateEntranceDetails>();
				 CandidateEntranceDetailsTO detTo= applicantDetail.getEntranceDetail();
				 CandidateEntranceDetails bo= new CandidateEntranceDetails();
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
			
			//personal data prepare...
			
			setPersonaldataBO(appBO,applicantDetail,admForm,isPresidance);
			if(admForm.getPreferenceList()!=null && !admForm.getPreferenceList().isEmpty()){
			//	setPreferenceBos(appBO,admForm.getPreferenceList());
				
			}
			if(admForm.getPrefcourses()!=null && !admForm.getPrefcourses().isEmpty()){
				setPreferenceBo( appBO,admForm.getPrefcourses());
			}
			//set vehicle details if any
			setvehicleDetailsEdit(appBO,applicantDetail);
			setworkExpDetails(appBO,applicantDetail);
			setDocUploads(appBO,applicantDetail,admForm);
			setOriginalSubmittedDocsList(applicantDetail,admForm);
			//raghu change 
			appBO.setMode(admForm.getSelectedFeePayment());
			appBO.setAdmStatus(applicantDetail.getAdmStatus());
			//added for StudentSpecializationPreferred and AdmApplnAdditionalInfo-Smitha
			if(applicantDetail.getStudentSpecializationPrefered()!=null && !applicantDetail.getStudentSpecializationPrefered().isEmpty())
				appBO.setStudentSpecializationPrefered(applicantDetail.getStudentSpecializationPrefered());
			/*if(applicantDetail.getAdmapplnAdditionalInfos()!=null && !applicantDetail.getAdmapplnAdditionalInfos().isEmpty())
				appBO.setAdmapplnAdditionalInfo(applicantDetail.getAdmapplnAdditionalInfos());*/
			appBO.setSeatNo(applicantDetail.getSeatNo());
			appBO.setIsPreferenceUpdated(applicantDetail.getIsPreferenceUpdated());
			appBO.setFinalMeritListApproveDate(applicantDetail.getFinalMeritListApproveDate());
			appBO.setVerifiedBy(applicantDetail.getVerifiedBy());
			/* code modified and added by sudhir*/
			if(applicantDetail.getAdmapplnAdditionalInfos()!=null && !applicantDetail.getAdmapplnAdditionalInfos().isEmpty()){
				Set<AdmapplnAdditionalInfo> set = new HashSet<AdmapplnAdditionalInfo>(); 
				List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(applicantDetail.getAdmapplnAdditionalInfos());
				AdmapplnAdditionalInfo additionalInfo = additionalList.get(0);
				additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
				additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
				additionalInfo.setBackLogs(admForm.isBackLogs());
				//raghu added newly
				additionalInfo.setIsSaypass(admForm.getIsSaypass());
				additionalInfo.setModifiedBy(admForm.getUserId());
				additionalInfo.setLastModifiedDate(new Date());
				// flag added by chandra
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
				if(applicantDetail.getApplicantFeedbackId()!=null){
					ApplicantFeedback feedback=new ApplicantFeedback();
					feedback.setId(Integer.parseInt(applicantDetail.getApplicantFeedbackId()));
					additionalInfo.setApplicantFeedback(feedback);
					if(applicantDetail.getInternationalCurrencyId()!=null && !applicantDetail.getInternationalCurrencyId().isEmpty()){
						Currency curr=new Currency();
						curr.setId(Integer.parseInt(applicantDetail.getInternationalCurrencyId()));
						additionalInfo.setInternationalApplnFeeCurrency(curr);
					}
				}
				additionalInfo.setCreatedBy(appBO.getCreatedBy());
				additionalInfo.setCreatedDate(new Date());
				additionalInfo.setModifiedBy(appBO.getCreatedBy());
				additionalInfo.setLastModifiedDate(new Date());
				additionalInfo.setBackLogs(admForm.isBackLogs());
				//raghu added newly
				additionalInfo.setIsSaypass(admForm.getIsSaypass());
				additionalInfo.setIsSpotAdmission(Boolean.FALSE);
				// flag added by chandra
				if(applicantDetail.getIsComeDk()!=null){
					if(applicantDetail.getIsComeDk())	
						additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
					else
						additionalInfo.setIsComedk(false);
					}else{
						additionalInfo.setIsComedk(false);
				}
				// 
				admAddnSet.add(additionalInfo);
				appBO.setAdmapplnAdditionalInfo(admAddnSet);
				
			}
			/* code modified and added by sudhir*/
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
		AdmissionFormForm admForm) {
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
						name=AdmissionFormHelper.PHOTO;
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
	private void setDocUploads(AdmAppln appBO, AdmApplnTO applicantDetail, AdmissionFormForm admForm) throws Exception{
		log.info("enter setDocUploads" );
		if(applicantDetail!=null && applicantDetail.getEditDocuments()!=null){
			Set<ApplnDoc> docSet= new HashSet<ApplnDoc>();
			Iterator<ApplnDocTO> docItr=applicantDetail.getEditDocuments().iterator();
			while (docItr.hasNext()) {
				ApplnDocTO docTO = (ApplnDocTO) docItr.next();
				ApplnDoc docBO= new ApplnDoc();
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
				if(docTO.getEditDocument()!=null && docTO.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(docTO.getEditDocument().getFileName())){
					FormFile editDoc=docTO.getEditDocument();
					docBO.setDocument(editDoc.getFileData());
					if(applicantDetail.getStudentId() != 0){
						FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+applicantDetail.getStudentId()+".jpg");
						fos.write(editDoc.getFileData());
						fos.close();
					}
					docBO.setName(editDoc.getFileName());
					docBO.setContentType(editDoc.getContentType());
				}else{
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
	private void setworkExpDetails(AdmAppln appBO, AdmApplnTO applicantDetail) {
		log.info("enter setworkExpDetails" );
	if(applicantDetail!=null && applicantDetail.getWorkExpList()!=null){
		Set<ApplicantWorkExperience> expSet= new HashSet<ApplicantWorkExperience>();
		Iterator<ApplicantWorkExperienceTO> expItr=applicantDetail.getWorkExpList().iterator();
		while (expItr.hasNext()) {
			ApplicantWorkExperienceTO expTO = (ApplicantWorkExperienceTO) expItr.next();
			ApplicantWorkExperience expBo=new ApplicantWorkExperience();
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
}
	/**
	 * Admission Form Vehicle Details Set
	 * @param appBO
	 * @param applicantDetail
	 */
	private void setvehicleDetailsEdit(AdmAppln appBO,
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
				vehBo.setId(applicantDetail.getVehicleDetail().getId());
				vehBo.setVehicleNo(applicantDetail.getVehicleDetail().getVehicleNo());
				vehBo.setVehicleType(applicantDetail.getVehicleDetail().getVehicleType());
				vehSet.add(vehBo);
				appBO.setStudentVehicleDetailses(vehSet);
			}
		}
		log.info("exit setvehicleDetailsEdit" );
	}
	/**
	 * Set preference BOs to AdmApplnBO
	 * @param appBO
	 * @param preferenceList
	 */
	private void setPreferenceBos(AdmAppln appBO,
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
	}
	
	private void setPreferenceBo(AdmAppln appBO,
			List<CourseTO> preferenceList) {
		log.info("enter setPreferenceBos" );
		Set<CandidatePreference> preferencebos=new HashSet<CandidatePreference>();
		Iterator<CourseTO> toItr= preferenceList.iterator();
		
		while (toItr.hasNext()) {
			CourseTO prefTO = (CourseTO) toItr.next();
			if (prefTO!=null && prefTO.getId()!=0) {
				
				
				/*AdmAppln appB=new AdmAppln();
				appB.setId(appBO.getId());
				bo.setAdmAppln(appB);*/
				if(prefTO.getPrefNo()!=null &&!prefTO.getPrefNo().equalsIgnoreCase("")){
					CandidatePreference bo = new CandidatePreference();
					if(prefTO.getPrefId()!=0)
						bo.setId(prefTO.getPrefId());
				bo.setPrefNo(Integer.parseInt(prefTO.getPrefNo()));
				
				Course prefcrs = new Course();
				
				prefcrs.setId(prefTO.getId());
				bo.setCourse(prefcrs);
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
	 */
	private void setPersonaldataBO(AdmAppln appBO, AdmApplnTO applicantDetail,AdmissionFormForm admForm,boolean isPresidance) {
		log.info("enter setPersonaldataBO" );
		if(applicantDetail.getPersonalData()!=null){
			PersonalDataTO dataTo=applicantDetail.getPersonalData();
			PersonalData data= new PersonalData();
			data.setId(dataTo.getId());
			data.setCreatedBy(applicantDetail.getPersonalData().getCreatedBy());
			data.setCreatedDate(applicantDetail.getPersonalData().getCreatedDate());
			data.setModifiedBy(appBO.getModifiedBy());
			data.setLastModifiedDate(new Date());
			data.setFirstName(dataTo.getFirstName().toUpperCase());
			data.setMiddleName(dataTo.getMiddleName());
			data.setLastName(dataTo.getLastName());
			if( dataTo.getDob()!= null){
				data.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(dataTo.getDob()));
			}
			data.setBirthPlace(dataTo.getBirthPlace());
			
			if (dataTo.getBirthState() != null && !StringUtils.isEmpty(dataTo.getBirthState()) && StringUtils.isNumeric(dataTo.getBirthState())) {
				State birthState=new State();
				birthState.setId(Integer.parseInt(dataTo.getBirthState()));
				data.setStateByStateId(birthState);
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
			 data.setTrainingInstAddress(dataTo.getTrainingInstAddress());
			 data.setTrainingProgName(dataTo.getTrainingProgName());
			 data.setTrainingPurpose(dataTo.getTrainingPurpose());
			 
			 data.setCourseKnownBy(dataTo.getCourseKnownBy());
			 data.setCourseOptReason(dataTo.getCourseOptReason());
			 data.setStrength(dataTo.getStrength());
			 data.setWeakness(dataTo.getWeakness());
			 data.setOtherAddnInfo(dataTo.getOtherAddnInfo());
			 data.setSecondLanguage(dataTo.getSecondLanguage());
			 
			 setExtracurricullars(dataTo,data);
			 
			
			if (dataTo.getNationality()!=null && !StringUtils.isEmpty(dataTo.getNationality()) && StringUtils.isNumeric(dataTo.getNationality())) {
				Nationality nat= new Nationality();
				nat.setId(Integer.parseInt(dataTo.getNationality()));
				data.setNationality(nat);
			}
			if(dataTo.getBloodGroup()!=null)
			data.setBloodGroup(dataTo.getBloodGroup().toUpperCase());
			data.setEmail(dataTo.getEmail());
			if(dataTo.getGender()!=null)
			data.setGender(dataTo.getGender().toUpperCase());
			data.setIsHandicapped(dataTo.isHandicapped());
			data.setIsSportsPerson(dataTo.isSportsPerson());
			data.setSportsPersonDescription(dataTo.getSportsDescription());
			data.setHandicappedDescription(dataTo.getHadnicappedDescription());
			//extra details
			
			data.setIsNcccertificate(dataTo.isNcccertificate());
			data.setIsNsscertificate(dataTo.isNsscertificate());
			data.setIsExcervice(dataTo.isExservice());
			if(dataTo.isNcccertificate()){
				data.setNccgrade(dataTo.getNccgrades());
			}
			else
				data.setNccgrade(null);
			
			if(dataTo.isNsscertificate()){
				data.setNssgrade(dataTo.getNssgrades());
			}
			else
				data.setNssgrade(null);
			ResidentCategory res_cat=null;
			if (dataTo.getResidentCategory()!=null && !StringUtils.isEmpty(dataTo.getResidentCategory()) && StringUtils.isNumeric(dataTo.getResidentCategory())) {
				res_cat = new ResidentCategory();
				res_cat.setId(Integer.parseInt(dataTo.getResidentCategory()));
			}
			data.setResidentCategory(res_cat);
			data.setRuralUrban(dataTo.getAreaType());
			data.setPhNo1(dataTo.getPhNo1());
			data.setPhNo2(dataTo.getPhNo2());
			data.setPhNo3(dataTo.getPhNo3());
			data.setMobileNo1(dataTo.getMobileNo1());
			data.setMobileNo2(dataTo.getMobileNo2());
			data.setMobileNo3(dataTo.getMobileNo3());
			
			if (dataTo.getUgcourse()!=null && !StringUtils.isEmpty(dataTo.getUgcourse()) && StringUtils.isNumeric(dataTo.getUgcourse())) {
				UGCoursesBO ug= new UGCoursesBO();
				ug.setId(Integer.parseInt(dataTo.getUgcourse()));
				data.setUgcourse(ug);
			}
			Religion religionbo=null;
			if(dataTo.getReligionId()!=null && !StringUtils.isEmpty(dataTo.getReligionId()) && StringUtils.isNumeric(dataTo.getReligionId())){
				religionbo= new Religion();
				religionbo.setId(Integer.parseInt(dataTo.getReligionId()));
					if (dataTo.getSubReligionId()!=null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
						ReligionSection subreligionBO = new ReligionSection();
						subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
						subreligionBO.setReligion(religionbo);
						data.setReligionSection(subreligionBO);
					}else{
						data.setReligionSection(null);
						data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
					}
					data.setReligion(religionbo);	
				}else{
					data.setReligion(null);	
					data.setReligionOthers(dataTo.getReligionOthers());
					if (dataTo.getSubReligionId()!=null && !StringUtils.isEmpty(dataTo.getSubReligionId()) && StringUtils.isNumeric(dataTo.getSubReligionId())) {
						ReligionSection subreligionBO = new ReligionSection();
						subreligionBO.setId(Integer.parseInt(dataTo.getSubReligionId()));
						subreligionBO.setReligion(religionbo);
						data.setReligionSection(subreligionBO);
					}else{
						data.setReligionSection(null);
						data.setReligionSectionOthers(dataTo.getReligionSectionOthers());
					}
				}
				if (dataTo.getCasteId()!=null &&!StringUtils.isEmpty(dataTo.getCasteId()) && StringUtils.isNumeric(dataTo.getCasteId()) ) {
					Caste casteBO = new Caste();
					casteBO.setId(Integer.parseInt(dataTo.getCasteId()));
					data.setCaste(casteBO);
				}else{
					data.setCaste(null);
					data.setCasteOthers(dataTo.getCasteOthers());
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
				}
				*/
				
				if(dataTo.getDioceseOthers()!=null &&!StringUtils.isEmpty(dataTo.getDioceseOthers()) ){
					data.setDioceseOthers(dataTo.getDioceseOthers());
				}
				
				if(dataTo.getParishOthers()!=null &&!StringUtils.isEmpty(dataTo.getParishOthers()) ){
					
					data.setParishOthers(dataTo.getParishOthers());
				}
				
				
				data.setPassportNo(dataTo.getPassportNo());
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
				}

				data.setPermanentAddressLine1(dataTo.getPermanentAddressLine1());
				data.setPermanentAddressLine2(dataTo.getPermanentAddressLine2());
				data.setPermanentAddressZipCode(dataTo.getPermanentAddressZipCode());
				data.setCityByPermanentAddressCityId(dataTo.getPermanentCityName());
					if(dataTo.getPermanentStateId()!=null && !StringUtils.isEmpty(dataTo.getPermanentStateId()) && StringUtils.isNumeric(dataTo.getPermanentStateId())){
						if(Integer.parseInt(dataTo.getPermanentStateId())!=0){
							State permState=new State();
							permState.setId(Integer.parseInt(dataTo.getPermanentStateId()));
							data.setStateByPermanentAddressStateId(permState);
						}
					}else{
						data.setStateByPermanentAddressStateId(null);
						data.setPermanentAddressStateOthers(dataTo.getPermanentAddressStateOthers());
					}
					if(dataTo.getPermanentCountryId()!=0){
					Country permCnt= new Country();
					permCnt.setId(dataTo.getPermanentCountryId());
					data.setCountryByPermanentAddressCountryId(permCnt);
					}
					
					
					//raghu
					if(dataTo.getPermanentDistricId()!=null && !StringUtils.isEmpty(dataTo.getPermanentDistricId()) && StringUtils.isNumeric(dataTo.getPermanentDistricId())){
						if(Integer.parseInt(dataTo.getPermanentDistricId())!=0){
							District permState=new District();
							permState.setId(Integer.parseInt(dataTo.getPermanentDistricId()));
							data.setStateByParentAddressDistrictId(permState);
						}
					}else{
						data.setStateByParentAddressDistrictId(null);
						data.setPermanentAddressDistrcictOthers(dataTo.getPermanentAddressDistrictOthers());
					}
					
					
					data.setCurrentAddressLine1(dataTo.getCurrentAddressLine1());
					data.setCurrentAddressLine2(dataTo.getCurrentAddressLine2());
					data.setCurrentAddressZipCode(dataTo.getCurrentAddressZipCode());
					data.setCityByCurrentAddressCityId(dataTo.getCurrentCityName());
						if(dataTo.getCurrentStateId()!=null && !StringUtils.isEmpty(dataTo.getCurrentStateId()) && StringUtils.isNumeric(dataTo.getCurrentStateId())){
							if(Integer.parseInt(dataTo.getCurrentStateId())!=0){
							State currState=new State();
							currState.setId(Integer.parseInt(dataTo.getCurrentStateId()));
							data.setStateByCurrentAddressStateId(currState);
							}
						}else{
							data.setStateByCurrentAddressStateId(null);
							data.setCurrentAddressStateOthers(dataTo.getCurrentAddressStateOthers());
						}
						Country currCnt= new Country();
						currCnt.setId(dataTo.getCurrentCountryId());
						data.setCountryByCurrentAddressCountryId(currCnt);	
					
						
						//raghu
						if(dataTo.getCurrentDistricId()!=null && !StringUtils.isEmpty(dataTo.getCurrentDistricId()) && StringUtils.isNumeric(dataTo.getCurrentDistricId())){
							if(Integer.parseInt(dataTo.getCurrentDistricId())!=0){
								District currState=new District();
							currState.setId(Integer.parseInt(dataTo.getCurrentDistricId()));
							data.setStateByCurrentAddressDistrictId(currState);
							}
						}else{
							data.setStateByCurrentAddressDistrictId(null);
							data.setCurrenttAddressDistrcictOthers(dataTo.getCurrentAddressDistrictOthers());
						}
						
						data.setFatherEducation(dataTo.getFatherEducation());
						data.setMotherEducation(dataTo.getMotherEducation());
						
						data.setFatherName(dataTo.getFatherName());
						data.setMotherName(dataTo.getMotherName());
						
						data.setFatherEmail(dataTo.getFatherEmail());
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
						}else if(dataTo.getFatherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) 
								&& dataTo.getFatherOccupationId().equalsIgnoreCase("other") && dataTo.getOtherOccupationFather()!=null 
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
						}else if(dataTo.getMotherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getMotherOccupationId())  
								&& dataTo.getMotherOccupationId().equalsIgnoreCase("other") && dataTo.getOtherOccupationMother()!=null 
								&& !StringUtils.isEmpty(dataTo.getOtherOccupationMother())){
							data.setOtherOccupationMother(dataTo.getOtherOccupationMother());
							data.setOccupationByMotherOccupationId(null);
						}else{
							data.setOccupationByMotherOccupationId(null);
						}
						data.setParentAddressLine1(dataTo.getParentAddressLine1());
						data.setParentAddressLine2(dataTo.getParentAddressLine2());
						data.setParentAddressLine3(dataTo.getParentAddressLine3());
						data.setParentAddressZipCode(dataTo.getParentAddressZipCode());
						if(dataTo.getParentCountryId()!=0){
						Country parentcountry= new Country();
						parentcountry.setId(dataTo.getParentCountryId());
						data.setCountryByParentAddressCountryId(parentcountry);
						}else{
							data.setCountryByParentAddressCountryId(null);
						}
						if (dataTo.getParentStateId()!=null && !StringUtils.isEmpty(dataTo.getParentStateId()) && StringUtils.isNumeric(dataTo.getParentStateId())) {
							State parentState = new State();
							parentState.setId(Integer.parseInt(dataTo.getParentStateId()));
							data.setStateByParentAddressStateId(parentState);
						}else{
							data.setStateByParentAddressStateId(null);
							data.setParentAddressStateOthers(dataTo.getParentAddressStateOthers());
						}
						data.setCityByParentAddressCityId(dataTo.getParentCityName());
						
						data.setParentPh1(dataTo.getParentPh1());
						data.setParentPh2(dataTo.getParentPh2());
						data.setParentPh3(dataTo.getParentPh3());
						
						data.setParentMob1(dataTo.getParentMob1());
						data.setParentMob2(dataTo.getParentMob2());
						data.setParentMob3(dataTo.getParentMob3());
						
						//guardian's
						data.setGuardianAddressLine1(dataTo.getGuardianAddressLine1());
						data.setGuardianAddressLine2(dataTo.getGuardianAddressLine2());
						data.setGuardianAddressLine3(dataTo.getGuardianAddressLine3());
						data.setGuardianAddressZipCode(dataTo.getGuardianAddressZipCode());
						if(dataTo.getCountryByGuardianAddressCountryId()!=0){
						Country parentcountry= new Country();
						parentcountry.setId(dataTo.getCountryByGuardianAddressCountryId());
						data.setCountryByGuardianAddressCountryId(parentcountry);
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
						data.setCityByGuardianAddressCityId(dataTo.getCityByGuardianAddressCityId());
						
						data.setGuardianPh1(dataTo.getGuardianPh1());
						data.setGuardianPh2(dataTo.getGuardianPh2());
						data.setGuardianPh3(dataTo.getGuardianPh3());
						
						data.setGuardianMob1(dataTo.getGuardianMob1());
						data.setGuardianMob2(dataTo.getGuardianMob2());
						data.setGuardianMob3(dataTo.getGuardianMob3());
						
						data.setBrotherName(dataTo.getBrotherName());
						data.setBrotherEducation(dataTo.getBrotherEducation());
						data.setBrotherOccupation(dataTo.getBrotherOccupation());
						data.setBrotherIncome(dataTo.getBrotherIncome());
						data.setBrotherAge(dataTo.getBrotherAge());
						data.setGuardianName(dataTo.getGuardianName());
						data.setSisterName(dataTo.getSisterName());
						data.setSisterEducation(dataTo.getSisterEducation());
						data.setSisterOccupation(dataTo.getSisterOccupation());
						data.setSisterIncome(dataTo.getSisterIncome());
						data.setSisterAge(dataTo.getSisterAge());
						if(admForm.getRecomendedBy()!=null && !admForm.getRecomendedBy().isEmpty()){
							data.setRecommendedBy(admForm.getRecomendedBy());
						}
						if(dataTo.getUniversityEmail()!=null && !dataTo.getUniversityEmail().isEmpty())
							data.setUniversityEmail(dataTo.getUniversityEmail());
						appBO.setPersonalData(data);
						setEdnqualificationBO(appBO,applicantDetail,isPresidance,admForm);
						setPreferences(appBO,applicantDetail);
						setDocumentupload(appBO,applicantDetail);
						
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
						if(uploadto.getDocument()!=null && uploadto.getDocument().getFileName()!=null && !StringUtils.isEmpty(uploadto.getDocument().getFileName())){
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
	private void setPreferences(AdmAppln appBO, AdmApplnTO applicantDetail) {

		log.info("enter setPreferences" );
		
		Set<CandidatePreference> preferences= new HashSet<CandidatePreference>();
		PreferenceTO prefTo=applicantDetail.getPreference();
		CandidatePreference firstPref;
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
		log.info("exit setPreferences" );
	}
	/**
	 * Admission Form BO Creation
	 * @param appBO
	 * @param applicantDetail
	 */
	private void setEdnqualificationBO(AdmAppln appBO,
			AdmApplnTO applicantDetail,boolean isPresidance,AdmissionFormForm admForm) {
		
        
		log.info("enter setEdnqualificationBO" );
            Set<EdnQualification> qualificationSet=null;
            if(applicantDetail.getEdnQualificationList()!=null){
                  qualificationSet=new HashSet<EdnQualification>();
                  List<EdnQualificationTO> qualifications=applicantDetail.getEdnQualificationList();
                  Iterator<EdnQualificationTO> itr= qualifications.iterator();
                  while (itr.hasNext()) {
                        EdnQualificationTO qualificationTO = (EdnQualificationTO) itr
                                    .next();
                        EdnQualification bo=new EdnQualification();
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
                        
                        if(qualificationTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualificationTO.getMarksObtained()))
                              bo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
                        bo.setNoOfAttempts(qualificationTO.getNoOfAttempts());
                        if(qualificationTO.getTotalMarks()!=null && !StringUtils.isEmpty(qualificationTO.getTotalMarks()) && CommonUtil.isValidDecimal(qualificationTO.getTotalMarks()))
                        bo.setTotalMarks(new BigDecimal(qualificationTO.getTotalMarks()));
                  
                        if(bo.getMarksObtained()!=null && bo.getTotalMarks()!=null && bo.getTotalMarks().floatValue()!=0.0 && bo.getMarksObtained().floatValue()!=0 ){
                        float percentageMarks = bo.getMarksObtained().floatValue()/bo.getTotalMarks().floatValue()*100 ;
                        bo.setPercentage(new BigDecimal(percentageMarks));
                        }else{
                              bo.setPercentage(new BigDecimal(0));
                              if(isPresidance && qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
                                    bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
                              }
                        }
                        
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
                        //for ranklist
                        //ug programtype id=1 
                        if((qualificationTO.getDocName().equalsIgnoreCase("Class 12")) && (Integer.parseInt(admForm.getProgramTypeId())==1)){
                        Set<AdmSubjectMarkForRank> submarks=new HashSet<AdmSubjectMarkForRank>();
                        List<AdmSubjectMarkForRankTO> subList=admForm.getAdmsubMarkList();
                        Iterator<AdmSubjectMarkForRankTO> itrt=subList.iterator();
                        
                        if(subList!=null){
                              while(itrt.hasNext()){
                                    
                              AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itrt.next();
                                    
                              if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
                                          
                              AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
                              admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
                              admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
                              admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
                              AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
                              admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
                              admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
                              
                              Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
                              Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
                              Double conmark=(obtmark/maxmark)*200;
                              DecimalFormat df=new DecimalFormat("#.##");
                              admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
                              admSubjectMarkForRank.setIsActive(true);
                              admSubjectMarkForRank.setCreatedDate(new Date());
                              admSubjectMarkForRank.setCreatedBy(admForm.getUserId());
                              submarks.add(admSubjectMarkForRank);
                              
                                    }//if close
                              }//while close
                              bo.setAdmSubjectMarkForRank(submarks);
                        }
                        
                        
                        }
                        
                        if((qualificationTO.getDocName().equalsIgnoreCase("DEG")) && (Integer.parseInt(admForm.getProgramTypeId())==2)){
                              Set<AdmSubjectMarkForRank> submark=new HashSet<AdmSubjectMarkForRank>();
                              List<AdmSubjectMarkForRankTO> sublist=admForm.getAdmsubMarkList();
                              Iterator<AdmSubjectMarkForRankTO> itr2=sublist.iterator();
                              int i=0;
                              if(sublist!=null){
                                    while(itr2.hasNext()){
                                          
                                    AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itr2.next();
                                    if(admForm.getPatternofStudy().equalsIgnoreCase("CBCSS")){
                                          
                                          if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
                                                
                                                AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
                                                admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
                                                admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
                                                admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
                                                AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
                                                admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
                                                admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
                                                
                                                Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
                                                Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
                                                Double conmark=(obtmark/maxmark)*4;
                                                DecimalFormat df=new DecimalFormat("#.##");
                                                admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
                                                admSubjectMarkForRank.setCredit(admSubjectMarkForRankTO.getCredit());
                                                admSubjectMarkForRank.setIsActive(true);
                                                admSubjectMarkForRank.setCreatedDate(new Date());
                                                admSubjectMarkForRank.setCreatedBy(admForm.getUserId());
                                                submark.add(admSubjectMarkForRank);
                                          }
                                                
                                    }else {
                                          
                                          
                                          if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
                                                
                                                AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
                                                admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
                                                admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
                                                admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
                                                AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
                                                admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
                                                admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
                                                
                                                Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
                                                Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
                                                Double conmark=(obtmark/maxmark)*4;
                                                DecimalFormat df=new DecimalFormat("#.##");
                                                admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
                                                admSubjectMarkForRank.setIsActive(true);
                                                admSubjectMarkForRank.setCreatedDate(new Date());
                                                submark.add(admSubjectMarkForRank);
                                                
                                          
                                    }
                                    
                                    
                                    }//if close
                                    
                                    i++;
                                    }//while close
                                    
                              }
                              
                        bo.setAdmSubjectMarkForRank(submark);
                        bo.setUgPattern(admForm.getPatternofStudy());

                        }
                        
                        
                        
                        Set<CandidateMarks> detailMarks=new HashSet<CandidateMarks>();
                        
                        if(!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise()){
                              CandidateMarks detailMark= new CandidateMarks();
                              CandidateMarkTO detailMarkto=qualificationTO.getDetailmark();
                              if (detailMarkto!=null) {
                                    detailMark.setCreatedBy(detailMarkto.getCreatedBy());
                                    detailMark.setCreatedDate(detailMarkto.getCreatedDate());
                                    detailMark.setModifiedBy(appBO.getModifiedBy());
                                    detailMark.setLastModifiedDate(new Date());
                                    double totalmark=0.0;
                                    double totalobtained=0.0;
                                    double percentage=0.0;
                                    detailMark.setId(detailMarkto.getId());
                                    
                                    if((detailMarkto.getDetailedSubjects1()!= null) && (detailMarkto.getDetailedSubjects1().getId() != -1) && (detailMarkto.getDetailedSubjects1().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects1().getId());
                                          detailMark.setDetailedSubjects1(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects2()!= null) &&(detailMarkto.getDetailedSubjects2().getId() != -1) && (detailMarkto.getDetailedSubjects2().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects2().getId());
                                          detailMark.setDetailedSubjects2(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects3()!= null) && (detailMarkto.getDetailedSubjects3().getId() != -1) && (detailMarkto.getDetailedSubjects3().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects3().getId());
                                          detailMark.setDetailedSubjects3(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects4()!= null) &&(detailMarkto.getDetailedSubjects4().getId() != -1) && (detailMarkto.getDetailedSubjects4().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects4().getId());
                                          detailMark.setDetailedSubjects4(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects5()!= null) &&(detailMarkto.getDetailedSubjects5().getId() != -1) && (detailMarkto.getDetailedSubjects5().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects5().getId());
                                          detailMark.setDetailedSubjects5(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects6()!= null) &&(detailMarkto.getDetailedSubjects6().getId() != -1) && (detailMarkto.getDetailedSubjects6().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects6().getId());
                                          detailMark.setDetailedSubjects6(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects7()!= null) &&(detailMarkto.getDetailedSubjects7().getId() != -1) && (detailMarkto.getDetailedSubjects7().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects7().getId());
                                          detailMark.setDetailedSubjects7(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects8()!= null) &&(detailMarkto.getDetailedSubjects8().getId() != -1) && (detailMarkto.getDetailedSubjects8().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects8().getId());
                                          detailMark.setDetailedSubjects8(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects9()!= null) &&(detailMarkto.getDetailedSubjects9().getId() != -1) && (detailMarkto.getDetailedSubjects9().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects9().getId());
                                          detailMark.setDetailedSubjects9(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects10()!= null) &&(detailMarkto.getDetailedSubjects10().getId() != -1) && (detailMarkto.getDetailedSubjects10().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects10().getId());
                                          detailMark.setDetailedSubjects10(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects11()!= null) &&(detailMarkto.getDetailedSubjects11().getId() != -1) && (detailMarkto.getDetailedSubjects11().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects11().getId());
                                          detailMark.setDetailedSubjects11(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects12()!= null) &&(detailMarkto.getDetailedSubjects12().getId() != -1) && (detailMarkto.getDetailedSubjects12().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects12().getId());
                                          detailMark.setDetailedSubjects12(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects13()!= null) &&(detailMarkto.getDetailedSubjects13().getId() != -1) && (detailMarkto.getDetailedSubjects13().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects13().getId());
                                          detailMark.setDetailedSubjects13(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects14()!= null) &&(detailMarkto.getDetailedSubjects14().getId() != -1) && (detailMarkto.getDetailedSubjects14().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects14().getId());
                                          detailMark.setDetailedSubjects14(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects15()!= null) &&(detailMarkto.getDetailedSubjects15().getId() != -1) && (detailMarkto.getDetailedSubjects15().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects15().getId());
                                          detailMark.setDetailedSubjects15(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects16()!= null) &&(detailMarkto.getDetailedSubjects16().getId() != -1) && (detailMarkto.getDetailedSubjects16().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects16().getId());
                                          detailMark.setDetailedSubjects16(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects17()!= null) &&(detailMarkto.getDetailedSubjects17().getId() != -1) && (detailMarkto.getDetailedSubjects17().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects17().getId());
                                          detailMark.setDetailedSubjects17(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects18()!= null) &&(detailMarkto.getDetailedSubjects18().getId() != -1) && (detailMarkto.getDetailedSubjects18().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects18().getId());
                                          detailMark.setDetailedSubjects18(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects19()!= null) &&(detailMarkto.getDetailedSubjects19().getId() != -1) && (detailMarkto.getDetailedSubjects19().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects19().getId());
                                          detailMark.setDetailedSubjects19(detailedSubjects);
                                    }
                                    if((detailMarkto.getDetailedSubjects20()!= null) &&(detailMarkto.getDetailedSubjects20().getId() != -1) && (detailMarkto.getDetailedSubjects20().getId() != 0)) {
                                          DetailedSubjects detailedSubjects = new DetailedSubjects();
                                          detailedSubjects.setId(detailMarkto.getDetailedSubjects20().getId());
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

                                    
                                    
                                  //raghu
            						if(detailMarkto.getSubject1Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject1Credit()))
            							detailMark.setSubject1Credit(new BigDecimal(detailMarkto.getSubject1Credit()));
            						if(detailMarkto.getSubject2Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject2Credit()))
            							detailMark.setSubject2Credit(new BigDecimal(detailMarkto.getSubject2Credit()));
            						if(detailMarkto.getSubject3Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject3Credit()))
            							detailMark.setSubject3Credit(new BigDecimal(detailMarkto.getSubject3Credit()));
            						if(detailMarkto.getSubject4Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject4Credit()))
            							detailMark.setSubject4Credit(new BigDecimal(detailMarkto.getSubject4Credit()));
            						if(detailMarkto.getSubject5Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject5Credit()))
            							detailMark.setSubject5Credit(new BigDecimal(detailMarkto.getSubject5Credit()));
            						if(detailMarkto.getSubject6Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject6Credit()))
            							detailMark.setSubject6Credit(new BigDecimal(detailMarkto.getSubject6Credit()));
            						if(detailMarkto.getSubject7Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject7Credit()))
            							detailMark.setSubject7Credit(new BigDecimal(detailMarkto.getSubject7Credit()));
            						if(detailMarkto.getSubject15Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject15Credit()))
            							detailMark.setSubject15Credit(new BigDecimal(detailMarkto.getSubject15Credit()));
            						if(detailMarkto.getSubject16Credit()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16Credit()) && CommonUtil.isValidDecimal(detailMarkto.getSubject16Credit()))
            							detailMark.setSubject16Credit(new BigDecimal(detailMarkto.getSubject16Credit()));
            						
            						
                                    if(detailMarkto.getSubject1TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1TotalMarks()) && detailMarkto.getSubject1TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?") )
                                    detailMark.setSubject1TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject1TotalMarks()));
                                    if(detailMarkto.getSubject2TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2TotalMarks()) && detailMarkto.getSubject2TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject2TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject2TotalMarks()));
                                    if(detailMarkto.getSubject3TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3TotalMarks()) && detailMarkto.getSubject3TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject3TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject3TotalMarks()));
                                    if(detailMarkto.getSubject4TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4TotalMarks()) && detailMarkto.getSubject4TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject4TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject4TotalMarks()));
                                    if(detailMarkto.getSubject5TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5TotalMarks()) && detailMarkto.getSubject5TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject5TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject5TotalMarks()));
                                    if(detailMarkto.getSubject6TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6TotalMarks()) && detailMarkto.getSubject6TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject6TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject6TotalMarks()));
                                    if(detailMarkto.getSubject7TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject7TotalMarks()) && detailMarkto.getSubject7TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject7TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject7TotalMarks()));
                                    if(detailMarkto.getSubject8TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8TotalMarks()) && detailMarkto.getSubject8TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject8TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject8TotalMarks()));
                                    if(detailMarkto.getSubject9TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9TotalMarks()) && detailMarkto.getSubject9TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject9TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject9TotalMarks()));
                                    if(detailMarkto.getSubject10TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10TotalMarks()) && detailMarkto.getSubject10TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject10TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject10TotalMarks()));
                                    if(detailMarkto.getSubject11TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11TotalMarks()) && detailMarkto.getSubject11TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject11TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject11TotalMarks()));
                                    if(detailMarkto.getSubject12TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12TotalMarks()) && detailMarkto.getSubject12TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject12TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject12TotalMarks()));
                                    if(detailMarkto.getSubject13TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13TotalMarks()) && detailMarkto.getSubject13TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject13TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject13TotalMarks()));
                                    if(detailMarkto.getSubject14TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14TotalMarks()) && detailMarkto.getSubject14TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject14TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject14TotalMarks()));
                                    if(detailMarkto.getSubject15TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15TotalMarks()) && detailMarkto.getSubject15TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject15TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject15TotalMarks()));
                                    if(detailMarkto.getSubject16TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16TotalMarks()) && detailMarkto.getSubject16TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject16TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject16TotalMarks()));
                                    if(detailMarkto.getSubject17TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17TotalMarks()) && detailMarkto.getSubject17TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject17TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject17TotalMarks()));
                                    if(detailMarkto.getSubject18TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18TotalMarks()) && detailMarkto.getSubject18TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject18TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject18TotalMarks()));
                                    if(detailMarkto.getSubject19TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19TotalMarks()) && detailMarkto.getSubject19TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject19TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject19TotalMarks()));
                                    if(detailMarkto.getSubject20TotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20TotalMarks()) && detailMarkto.getSubject20TotalMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject20TotalMarks(new BigDecimal(
                                                detailMarkto.getSubject20TotalMarks()));
                                    
                                    if(detailMarkto.getSubject1ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject1ObtainedMarks()) && detailMarkto.getSubject1ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject1ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject1ObtainedMarks()));
                                    if(detailMarkto.getSubject2ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject2ObtainedMarks()) && detailMarkto.getSubject2ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject2ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject2ObtainedMarks()));
                                    if(detailMarkto.getSubject3ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject3ObtainedMarks()) && detailMarkto.getSubject3ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject3ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject3ObtainedMarks()));
                                    if(detailMarkto.getSubject4ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject4ObtainedMarks()) && detailMarkto.getSubject4ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject4ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject4ObtainedMarks()));
                                    if(detailMarkto.getSubject5ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject5ObtainedMarks()) && detailMarkto.getSubject5ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject5ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject5ObtainedMarks()));
                                    if(detailMarkto.getSubject6ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject6ObtainedMarks()) && detailMarkto.getSubject6ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject6ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject6ObtainedMarks()));
                                    if(detailMarkto.getSubject7ObtainedMarks()!=null  && !StringUtils.isEmpty(detailMarkto.getSubject7ObtainedMarks()) && detailMarkto.getSubject7ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject7ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject7ObtainedMarks()));
                                    if(detailMarkto.getSubject8ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject8ObtainedMarks()) && detailMarkto.getSubject8ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject8ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject8ObtainedMarks()));
                                    if(detailMarkto.getSubject9ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject9ObtainedMarks()) && detailMarkto.getSubject9ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject9ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject9ObtainedMarks()));
                                    if(detailMarkto.getSubject10ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject10ObtainedMarks()) && detailMarkto.getSubject10ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject10ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject10ObtainedMarks()));
                                    if(detailMarkto.getSubject11ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject11ObtainedMarks()) && detailMarkto.getSubject11ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject11ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject11ObtainedMarks()));
                                    if(detailMarkto.getSubject12ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject12ObtainedMarks()) && detailMarkto.getSubject12ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject12ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject12ObtainedMarks()));
                                    if(detailMarkto.getSubject13ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject13ObtainedMarks()) && detailMarkto.getSubject13ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject13ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject13ObtainedMarks()));
                                    if(detailMarkto.getSubject14ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject14ObtainedMarks()) && detailMarkto.getSubject14ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject14ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject14ObtainedMarks()));
                                    if(detailMarkto.getSubject15ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject15ObtainedMarks()) &&detailMarkto.getSubject15ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject15ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject15ObtainedMarks()));
                                    if(detailMarkto.getSubject16ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject16ObtainedMarks()) && detailMarkto.getSubject16ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject16ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject16ObtainedMarks()));
                                    if(detailMarkto.getSubject17ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject17ObtainedMarks()) && detailMarkto.getSubject17ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject17ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject17ObtainedMarks()));
                                    if(detailMarkto.getSubject18ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject18ObtainedMarks()) && detailMarkto.getSubject18ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject18ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject18ObtainedMarks()));
                                    if(detailMarkto.getSubject19ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject19ObtainedMarks()) && detailMarkto.getSubject19ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject19ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject19ObtainedMarks()));
                                    if(detailMarkto.getSubject20ObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getSubject20ObtainedMarks()) && detailMarkto.getSubject20ObtainedMarks().matches("\\d{0,3}(\\.\\d{1,2})?"))
                                    detailMark.setSubject20ObtainedMarks(new BigDecimal(
                                                detailMarkto.getSubject20ObtainedMarks()));
                                    if(detailMarkto.getTotalMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalMarks()) ){
                                    detailMark.setTotalMarks(new BigDecimal(detailMarkto
                                                .getTotalMarks()));
                                    totalmark=detailMark.getTotalMarks().doubleValue();
                                    }
                                    if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks()) ){
                                    detailMark.setTotalObtainedMarks(new BigDecimal(
                                                detailMarkto.getTotalObtainedMarks()));
                                    totalobtained=detailMark.getTotalObtainedMarks().doubleValue();
                                    }
                                    if(totalmark!=0.0 && totalobtained!=0.0)
                                    percentage=(totalobtained/totalmark)*100;
                                    bo.setTotalMarks(new BigDecimal(totalmark));
                                    bo.setMarksObtained(new BigDecimal(totalobtained));
                                    bo.setPercentage(new BigDecimal(percentage));
                                    if(isPresidance){
                                          if(percentage!=0.0){
                                                if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
                                                      bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
                                                }
                                          }
                                    }
                                    detailMarks.add(detailMark);
                              }
                              bo.setCandidateMarkses(detailMarks);
                        }
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
                                                if(isPresidance){
                                                      if(percentage!=0.0){
                                                            if(qualificationTO.getPercentage()!=null && !qualificationTO.getPercentage().isEmpty() && CommonUtil.isValidDecimal(qualificationTO.getPercentage())){
                                                                  bo.setPercentage(new BigDecimal(qualificationTO.getPercentage()));
                                                            }
                                                      }
                                                }
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
                                          bo.setApplicantMarksDetailses(semesterDetails)  ;
                                    }
                              
                        }
                              
                        		
                        
                        qualificationSet.add(bo);
                        if(appBO.getPersonalData()!=null)
                        appBO.getPersonalData().setEdnQualifications(qualificationSet);
            
            }
            log.info("exit setEdnqualificationBO" );
      }
            
            
      

	}
	
	
	//admission form
	
	/**
	 * @param admApplnBO
	 * @return adminAppTO
	 */
	public AdmApplnTO copyPropertiesValue(AdmAppln admApplnBo)throws Exception {
		log.info("enter copyPropertiesValue admappln" );
		AdmApplnTO adminAppTO = null;
		PersonalDataTO personalDataTO = null;
		CourseTO courseTO = null;
		int applicationYear=admApplnBo.getAppliedYear();
		String applyYear=String.valueOf(applicationYear)+"-"+(applicationYear+1);
		//Commented By Manu.List not be null initialized
	   /*List<EdnQualificationTO> ednQualificationList = null;
		List<ApplicantWorkExperienceTO> workExpList = null;
		List<CandidatePrerequisiteMarksTO> prereqList = null*/
		List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
		List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
		List<CandidatePrerequisiteMarksTO> prereqList =new ArrayList<CandidatePrerequisiteMarksTO>();
		
		PreferenceTO preferenceTO = null;

		List<ApplnDocTO> editDocuments = null;


		if (admApplnBo != null) {
			adminAppTO = new AdmApplnTO();
			adminAppTO.setId(admApplnBo.getId());
			adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
			adminAppTO.setAdmissionNumber(admApplnBo.getAdmissionNumber());
			adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
			adminAppTO.setLastModifiedDate(admApplnBo.getLastModifiedDate());
			adminAppTO.setIsFinalMeritApproved(admApplnBo.getIsFinalMeritApproved());
			if(admApplnBo.getRecommedationApplicationNo() != null && !admApplnBo.getRecommedationApplicationNo().isEmpty()){
				adminAppTO.setRecommedationApplicationNo(admApplnBo.getRecommedationApplicationNo());
			}
			if(admApplnBo.getStudentVehicleDetailses()!=null && !admApplnBo.getStudentVehicleDetailses().isEmpty()){
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
			}
			adminAppTO.setApplicationId(admApplnBo.getId());
			adminAppTO.setRemark(admApplnBo.getRemarks());
			adminAppTO.setApplnNo(admApplnBo.getApplnNo());
			adminAppTO.setChallanRefNo(admApplnBo.getChallanRefNo());
			adminAppTO.setJournalNo(admApplnBo.getJournalNo());
			adminAppTO.setBankBranch(admApplnBo.getBankBranch());
			adminAppTO.setAppliedYear(admApplnBo.getAppliedYear());
			adminAppTO.setApplyYear(applyYear);
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
			adminAppTO.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getTcDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
			adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
			adminAppTO.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getMarkscardDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
			
			if(admApplnBo.getDate()!= null){
				adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo.getDate()));
			}
			if(admApplnBo.getAdmissionDate()!= null){
				adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getAdmissionDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
			}
			if(admApplnBo.getCourseChangeDate()!= null){
				adminAppTO.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getCourseChangeDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
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
			if(admApplnBo.getRecommedationApplicationNo() != null && !admApplnBo.getRecommedationApplicationNo().isEmpty()){
				adminAppTO.setRecommedationApplicationNo(admApplnBo.getRecommedationApplicationNo());
			}
			if(additionalInfo.getTitleMother() != null){
				adminAppTO.setTitleOfMother(additionalInfo.getTitleMother());
			}
			if(additionalInfo.getBackLogs() != null){
				adminAppTO.setBackLogs(additionalInfo.getBackLogs());
			}
			//raghu added newly
			if(additionalInfo.getIsSaypass() != null){
				adminAppTO.setIsSaypass(additionalInfo.getIsSaypass());
			}
		
			// /* code added by chandra
			if(additionalInfo!=null){
				if(additionalInfo.getIsComedk()!=null && additionalInfo.getIsComedk())
					adminAppTO.setIsComeDk(true);
				else
					adminAppTO.setIsComeDk(false);
			}else{
				adminAppTO.setIsComeDk(false);
			}
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
			
			String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
			boolean workExpNeed=false;
			if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
				workExpNeed=true;
			}
			workExpList=copyWorkExpValue(admApplnBo.getApplicantWorkExperiences(),workExpList,workExpNeed);
			adminAppTO.setWorkExpList(workExpList);

			ednQualificationList = copyPropertiesValue(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear());
			adminAppTO.setEdnQualificationList(ednQualificationList);

			preferenceTO = copyPropertiesValue(admApplnBo.getCandidatePreferences());
			adminAppTO.setPreference(preferenceTO);
	
			editDocuments = copyPropertiesEditDocValue(admApplnBo.getApplnDocs(),adminAppTO.getSelectedCourse().getId(),adminAppTO,admApplnBo.getAppliedYear());
			adminAppTO.setEditDocuments(editDocuments);
			
			prereqList=copyPrerequisiteDetails(admApplnBo.getCandidatePrerequisiteMarks());
			adminAppTO.setPrerequisiteTos(prereqList);
			
			if(admApplnBo.getStudentQualifyexamDetails()!=null && !admApplnBo.getStudentQualifyexamDetails().isEmpty()){
				adminAppTO.setOriginalQualDetails(admApplnBo.getStudentQualifyexamDetails());
			}
			
			if(admApplnBo.getExamCenter()!= null && admApplnBo.getExamCenter().getCenter()!= null && !admApplnBo.getExamCenter().getCenter().trim().isEmpty()){ 
				adminAppTO.setExamCenterName(admApplnBo.getExamCenter().getCenter());
				adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
			}
			if(admApplnBo.getInterScheduleSelection()!= null && admApplnBo.getInterScheduleSelection().getSelectionProcessDate()!= null){ 
				adminAppTO.setInterviewDate(CommonUtil.ConvertStringToDateFormat(
				admApplnBo.getInterScheduleSelection().getSelectionProcessDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
				//adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
				adminAppTO.setInterviewSelectionScheduleId(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
			}
			
			if(admApplnBo.getAdmittedThrough()!=null && admApplnBo.getAdmittedThrough().getIsActive()==true){
				adminAppTO.setAdmittedThroughId(String.valueOf(admApplnBo.getAdmittedThrough().getId()));
			}
			if(admApplnBo.getApplicantSubjectGroups()!=null && !admApplnBo.getApplicantSubjectGroups().isEmpty()){

				List<ApplicantSubjectGroup> applicantgroups=new ArrayList<ApplicantSubjectGroup>();
				
				Iterator<ApplicantSubjectGroup> subItr=admApplnBo.getApplicantSubjectGroups().iterator();
				for(int i=0;subItr.hasNext();i++) {
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
			}
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
			
			
			if(admApplnBo.getIsAided()!=null && admApplnBo.getIsAided()){
				adminAppTO.setIsAided(true);
			}else if(admApplnBo.getIsAided()!=null && !admApplnBo.getIsAided()){
				adminAppTO.setIsAided(false);
			}
			
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
			 
			 //vibin 
			 
			 
			  List<AdmSubjectMarkForRankTO> submarklistto12 = new ArrayList<AdmSubjectMarkForRankTO>(); 
			  
			  if(admApplnBo.getCourse().getProgram().getProgramType().getId()==1){
				  submarklistto12 = copyPropertiesValue(admApplnBo.getPersonalData().getId(),"Class 12");	 
				 }
			  else{
				  submarklistto12 = copyPropertiesValue(admApplnBo.getPersonalData().getId(),"DEG");
			  }
			  
			  
			  adminAppTO.setPucsubjectmarkto(submarklistto12);
			  
			  List<CandidatePreferenceTO> prefto = new ArrayList<CandidatePreferenceTO>();
			  prefto = copyPropertiesValuepref(admApplnBo.getId());
			  adminAppTO.setPreflist(prefto);
			  
			  int penalty=0;
			  int bonus=0;
			  Iterator<EdnQualification> itr = admApplnBo.getPersonalData().getEdnQualifications().iterator();
			  Iterator<AdmapplnAdditionalInfo> itr2 = admApplnBo.getAdmapplnAdditionalInfo().iterator();
			  
			  while (itr2.hasNext()){
				  AdmapplnAdditionalInfo admapplnAdditionalInfo = itr2.next();
				  
			  while(itr.hasNext()){
			  
				  EdnQualification ednQualificationBO = itr.next();
				 
				  if(ednQualificationBO.getDocChecklist()!=null&& ednQualificationBO.getDocChecklist().getIsActive()==true && ednQualificationBO.getDocChecklist().getDocType().getName().equalsIgnoreCase("Class 12") && ednQualificationBO.getDocChecklist().getIsSemesterWise()==false){
				  
			//penalty based on attempt
				if(String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()==1){
					penalty=0;//1st chance & this year pass out 
				}
				else if(String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()!=1 && ednQualificationBO.getNoOfAttempts()<=5){
					 penalty=(ednQualificationBO.getNoOfAttempts()-1)*5;
					//totalmarkforpart3=totalmarkforpart3-penalty;// no of attempt is more than one & less than 5 & year of pass out is same
				}
				else if(String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()!=1 && ednQualificationBO.getNoOfAttempts()>5){
					//totalmarkforpart3=totalmarkforpart3-20;// no of attempt is more than one & less than 5 & year of pass out is same
					penalty=20;
				}
				else if(!String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()==1 && admapplnAdditionalInfo.getIsSaypass()){
					int diff=admApplnBo.getAppliedYear()-ednQualificationBO.getYearPassing();
					penalty=diff*5;
					//totalmarkforpart3=totalmarkforpart3-(diff*5);//1st chance & any year pass out other than current year & say pass out
				}
				else if(!String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()!=1 && ednQualificationBO.getNoOfAttempts()<=5){
					 penalty=(ednQualificationBO.getNoOfAttempts()-1)*5;
					//totalmarkforpart3=totalmarkforpart3-penalty;// no of attempt is more than one & less than 5 & year of pass out is different
				
				}
				else if(!String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()==1){
					//totalmarkforpart3=totalmarkforpart3-0;//1st chance & not this year pass out 
					penalty=0;
				}
				
				else if(!String.valueOf(admApplnBo.getAppliedYear()).equalsIgnoreCase(String.valueOf(ednQualificationBO.getYearPassing())) && ednQualificationBO.getNoOfAttempts()!=1 && ednQualificationBO.getNoOfAttempts()>5){
					//totalmarkforpart3=totalmarkforpart3-20;// no of attempt is more than one & less than 5 & year of pass out is different
					penalty=20;
				}
				
			  }
			  }
			  }
				
				//Bonus
				
				
				
				//15 mark for ncc & 3 mark for A grade,5 mark for B grade and 5 mark for C grade
				if(admApplnBo.getPersonalData().getIsNcccertificate()!=null && admApplnBo.getPersonalData().getIsNcccertificate()){
					if(admApplnBo.getPersonalData().getNccgrade().equalsIgnoreCase("A"))
						bonus=bonus+15+3;
					if(admApplnBo.getPersonalData().getNccgrade().equalsIgnoreCase("B"))
						bonus=bonus+15+5;
					if(admApplnBo.getPersonalData().getNccgrade().equalsIgnoreCase("C"))
						bonus=bonus+15+5;
				}
				
				
					
				else if(admApplnBo.getPersonalData().getIsNsscertificate()){
					bonus=bonus+15;
				}
				
				if(admApplnBo.getPersonalData().getIsExcervice()){
					bonus=bonus+15;
				}
				 if(admApplnBo.getAdmittedThrough()!=null && admApplnBo.getAdmittedThrough().getIsActive()==true){
						adminAppTO.setAdmittedThroughName(admApplnBo.getAdmittedThrough().getName());
					}
				  
				  adminAppTO.setCourseName(admApplnBo.getCourseBySelectedCourseId().getName());
				  adminAppTO.setCurretcourseid(admApplnBo.getCourseBySelectedCourseId().getId());
				  adminAppTO.setBonsmark(bonus);
				  adminAppTO.setHandicapmark(penalty);
				  adminAppTO.setProgramType(admApplnBo.getCourse().getProgram().getProgramType().getId());
				  if (admApplnBo.getStudentOnlineApplication().getIsCommuQuota()!=null) {
					adminAppTO.setIsCommuQuota(admApplnBo.getStudentOnlineApplication().getIsCommuQuota());
				}
				  if (admApplnBo.getStudentOnlineApplication().isMngQuota()==false) {
						adminAppTO.setIsmngQuota("0");
					}
				  if (admApplnBo.getStudentOnlineApplication().isMngQuota()==true) {
						adminAppTO.setIsmngQuota("1");
					}
				  adminAppTO.setIsRank(false);
				  //allotment memo after student details edit
				  IAdmissionFormTransaction txn = new AdmissionFormTransactionImpl();
				  StudentRank studentrank = txn.getrankdetails(admApplnBo.getId(),admApplnBo.getCourseBySelectedCourseId().getId());
				  if(studentrank!=null){
				   adminAppTO.setIsRank(true);
				   adminAppTO.setPref(studentrank.getPrefNo());
				   adminAppTO.setRank(studentrank.getRank());
				   adminAppTO.setIndex(studentrank.getIndexMark());
				  }
			  
		
			
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
	private List<CandidatePrerequisiteMarksTO> copyPrerequisiteDetails(
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
	}
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
//			personalDataTO.setMiddleName(personalData.getMiddleName());
//			personalDataTO.setLastName(personalData.getLastName());
			if( personalData.getDateOfBirth()!= null){
				personalDataTO.setDob(CommonUtil.getStringDate(personalData.getDateOfBirth()));
			}
			personalDataTO.setBirthPlace(personalData.getBirthPlace());
			if(personalData.getIsHandicapped()!= null)
				personalDataTO.setHandicapped(personalData.getIsHandicapped());
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
			if(personalData.getIsSportsPerson()!= null)
				personalDataTO.setSportsPerson(personalData.getIsSportsPerson());
			if(personalData.getHandicappedDescription()!= null)
				personalDataTO.setHadnicappedDescription(personalData.getHandicappedDescription());
			if(personalData.getSportsPersonDescription()!= null)
			    personalDataTO.setSportsDescription(personalData.getSportsPersonDescription());
			
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
				personalDataTO.setBirthState(AdmissionFormHelper.OTHER);
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
				personalDataTO.setReligionId(AdmissionFormHelper.OTHER);
				personalDataTO.setReligionOthers(personalData.getReligionOthers());
				personalDataTO.setReligionName(personalData.getReligionOthers());
			} else if (personalData.getReligion() != null) {
				personalDataTO.setReligionName(personalData.getReligion().getName());
				personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
			}
			if( personalData.getReligionSectionOthers()!=null && !personalData.getReligionSectionOthers().isEmpty()){
				personalDataTO.setSubReligionId(AdmissionFormHelper.OTHER);
				personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
				personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
			}else if (personalData.getReligionSection() != null) {
				personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
				personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
			}
			if (personalData.getCasteOthers() != null && !personalData.getCasteOthers().isEmpty()) {
				personalDataTO.setCasteCategory(personalData.getCasteOthers());
				personalDataTO.setCasteOthers(personalData.getCasteOthers());
				personalDataTO.setCasteId(AdmissionFormHelper.OTHER);
			}else if (personalData.getCaste() != null) {
				personalDataTO.setCasteCategory(personalData.getCaste().getName());
				personalDataTO.setCasteId(String.valueOf(personalData.getCaste().getId()));
			}
			if(personalData.getRuralUrban()!=null){
				personalDataTO.setRuralUrban(personalData.getRuralUrban());
				personalDataTO.setAreaType(personalData.getRuralUrban());
			}
			
			
			
			//raghu
			
			if(personalData.getDiocese()!=null ){
				personalDataTO.setDioceseName(personalData.getDiocese().getName());
				personalDataTO.setDioceseId(String.valueOf(personalData.getDiocese().getId()));
			}
			if(personalData.getParish()!=null ){
				personalDataTO.setParishName(personalData.getParish().getName());
				personalDataTO.setParishId(String.valueOf(personalData.getParish().getId()));
			}
			
			
			if(personalData.getDioceseOthers()!=null &&!StringUtils.isEmpty(personalData.getDioceseOthers()) ){
				personalDataTO.setDioceseOthers(personalData.getDioceseOthers());
			}
			
			if(personalData.getParishOthers()!=null &&!StringUtils.isEmpty(personalData.getParishOthers()) ){
				
				personalDataTO.setParishOthers(personalData.getParishOthers());
			}
			
			
			personalDataTO.setGender(personalData.getGender());
			personalDataTO.setBloodGroup(personalData.getBloodGroup());
			personalDataTO.setPhNo1(personalData.getPhNo1());
			personalDataTO.setPhNo2(personalData.getPhNo2());
			personalDataTO.setPhNo3(personalData.getPhNo3());
			personalDataTO.setMobileNo1(personalData.getMobileNo1());
			personalDataTO.setMobileNo2(personalData.getMobileNo2());
			personalDataTO.setMobileNo3(personalData.getMobileNo3());
			//personalDataTO.setLandlineNo(personalData.getPhNo1() + " "+ personalData.getPhNo2() + " " + personalData.getPhNo3());
			personalDataTO.setLandlineNo((personalData.getPhNo1()!=null?personalData.getPhNo1():"") + " "+ (personalData.getPhNo2()!=null?personalData.getPhNo2():"") + " " + (personalData.getPhNo3()!=null?personalData.getPhNo3():""));
			

			//raghu
			
			if( personalData.getPermanentAddressDistrcictOthers()!= null && !personalData.getPermanentAddressDistrcictOthers().isEmpty()){
				personalDataTO.setPermanentAddressDistrictOthers(personalData.getPermanentAddressDistrcictOthers());
			}else if (personalData.getStateByParentAddressDistrictId() != null) {
				personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
				personalDataTO.setPermanentDistricId(""+personalData.getStateByParentAddressDistrictId().getId());
			}
		
			if( personalData.getCurrenttAddressDistrcictOthers()!= null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()){
				personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
			}else if (personalData.getStateByCurrentAddressDistrictId() != null) {
				personalDataTO.setCurrentDistricName(personalData.getStateByCurrentAddressDistrictId().getName());
				personalDataTO.setCurrentDistricId(""+personalData.getStateByCurrentAddressDistrictId().getId());
			}

			personalDataTO.setMobileNo(personalData.getMobileNo1() + " "+ personalData.getMobileNo2());
//			personalDataTO.setMobileNo(personalData.getMobileNo1() + " "+ personalData.getMobileNo2() + " "+ personalData.getMobileNo3());
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
				personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
			}
			personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
			personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
			if (personalData.getCityByPermanentAddressCityId() != null) {
				personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
			}
			if (personalData.getPermanentAddressStateOthers()!= null && !personalData.getPermanentAddressStateOthers().isEmpty()){
				personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
				personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
				personalDataTO.setPermanentStateId(AdmissionFormHelper.OTHER);
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
				personalDataTO.setCurrentStateId(AdmissionFormHelper.OTHER);
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
				personalDataTO.setFatherOccupationId("other");
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
				personalDataTO.setMotherOccupationId("other");
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
				personalDataTO.setParentStateId(AdmissionFormHelper.OTHER);
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
				personalDataTO.setStateByGuardianAddressStateId(AdmissionFormHelper.OTHER);
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
			if(personalData.getRecommentedPersonDesignation() != null && !personalData.getRecommentedPersonDesignation().isEmpty()){
				personalDataTO.setRecommentedPersonDesignation(personalData.getRecommentedPersonDesignation());
			}
			if(personalData.getRecommentedPersonMobile() != null && !personalData.getRecommentedPersonMobile().isEmpty()){
				personalDataTO.setRecommentedPersonMobile(personalData.getRecommentedPersonMobile());
			}
			if(personalData.getRecommentedBy()!=null && !personalData.getRecommentedBy().isEmpty())
				personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
		    
		if(personalData.getRecommendedBy()!=null && !personalData.getRecommendedBy().isEmpty())
			personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
	    }
		   if(personalData.getNccgrade() != null && !personalData.getNccgrade().isEmpty()){
			   personalDataTO.setNccgrades(personalData.getNccgrade());
		   }
		   if(personalData.getNssgrade() != null && !personalData.getNssgrade().isEmpty()){
			   personalDataTO.setNssgrades(personalData.getNssgrade());
		   }
		if(personalData.getUniversityEmail()!=null && !personalData.getUniversityEmail().isEmpty())
			personalDataTO.setUniversityEmail(personalData.getUniversityEmail());
		if (personalData.getFatherMobile()!=null && !personalData.getFatherMobile().isEmpty()) {
			personalDataTO.setFatherMobile(personalData.getFatherMobile());
		}
		if (personalData.getMotherMobile()!=null && !personalData.getMotherMobile().isEmpty()) {
			personalDataTO.setMotherMobile(personalData.getMotherMobile());
		}
		if (personalData.getPreferenceNoCAP()!=null) {
			personalDataTO.setPreferenceNoCAP(personalData.getPreferenceNoCAP());
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
		private List<ApplicantWorkExperienceTO> copyWorkExpValue(
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
							expTo.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getFromDate()),AdmissionFormHelper.SQL_DATEFORMAT, AdmissionFormHelper.FROM_DATEFORMAT));
						if (workExp.getToDate() != null)
							expTo.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getToDate()),AdmissionFormHelper.SQL_DATEFORMAT, AdmissionFormHelper.FROM_DATEFORMAT));
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
		}


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
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			List<DocChecklist> exambos= txn.getExamtypes(selectedCourse.getId(),appliedYear);
			int sizediff=0;
			//doctype ids already assigned
			List<Integer> presentIds= new ArrayList<Integer>();
			
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
//								Set<ApplicantMarkDetailsTO> markdetails= new TreeSet<ApplicantMarkDetailsTO>(new SemesterComparator());
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
									convertDetailMarkBOtoTO(detailMarkBO,markTO);
									ednQualificationTO.setDetailmark(markTO);
								}
							}else{
								ednQualificationTO.setDetailmark(null);
							}
						}
					}
					if(ednQualificationBO.getUniversityOthers()!= null && !ednQualificationBO.getUniversityOthers().isEmpty()){
						ednQualificationTO.setUniversityId(AdmissionFormHelper.OTHER);
						ednQualificationTO.setUniversityOthers(ednQualificationBO.getUniversityOthers());
						ednQualificationTO.setUniversityName(ednQualificationBO.getUniversityOthers());
					}else if(ednQualificationBO.getUniversity()!= null){
						ednQualificationTO.setUniversityId(String.valueOf(ednQualificationBO.getUniversity().getId()));
						ednQualificationTO.setUniversityName(ednQualificationBO.getUniversity().getName());
					}
					if(ednQualificationBO.getInstitutionNameOthers()!= null && !ednQualificationBO.getInstitutionNameOthers().isEmpty()){
						ednQualificationTO.setInstitutionId(AdmissionFormHelper.OTHER);
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
					if(ednQualificationBO.getPreviousRegNo()!=null){
						ednQualificationTO.setPreviousRegNo(ednQualificationBO.getPreviousRegNo());
					}else{
						ednQualificationTO.setPreviousRegNo("-");
					}
					
					float obtainmrk=0.0f;
					float totmrk=0.0f;
					
					if( ednQualificationBO.getMarksObtained()!= null){
						ednQualificationTO.setMarksObtained(String.valueOf(ednQualificationBO.getMarksObtained().doubleValue()));
						obtainmrk=ednQualificationBO.getMarksObtained().floatValue();
					}else{
						ednQualificationTO.setMarksObtained("-");
					}
					if(ednQualificationBO.getTotalMarks()!=null){
						ednQualificationTO.setTotalMarks(String.valueOf(ednQualificationBO.getTotalMarks().doubleValue()));
						totmrk=ednQualificationBO.getTotalMarks().floatValue();
					}else{
						ednQualificationTO.setTotalMarks("-");
					}
					if(totmrk!=0.0){
						float perc=0;
						if (ednQualificationTO.getUniversityId()=="58" || ednQualificationTO.getUniversityId().equalsIgnoreCase("58")) {
							if (obtainmrk >.5 && obtainmrk <5.5) {
								perc=(float) (40+(obtainmrk - .5)*10);
								ednQualificationTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(perc, 2)));
							}
							else if (obtainmrk >5.5) {
								perc=(float) (90+(obtainmrk - 5.5)*20);
								ednQualificationTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(perc, 2)));
							}
							else{
								perc=(float) (90+(obtainmrk - 5.5)*20);
								ednQualificationTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(perc, 2)));
							}
						}else{
						perc=(obtainmrk/totmrk)*100;
						ednQualificationTO.setPercentage(String.valueOf(CommonUtil.roundToDecimal(perc, 2)));
						}
					}else{
						ednQualificationTO.setPercentage(String.valueOf(ednQualificationBO.getPercentage()));
					}
					if(ednQualificationBO.getNoOfAttempts()!=null)
					ednQualificationTO.setNoOfAttempts(ednQualificationBO.getNoOfAttempts());
					
					if( UniversityHandler.getInstance().getUniversity() != null){
						List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
						if( universityList != null  && ednQualificationTO.getUniversityId()!=null && !ednQualificationTO.getUniversityId().equalsIgnoreCase(AdmissionFormHelper.OTHER)){
							ednQualificationTO.setUniversityList(universityList);
							if(ednQualificationTO.getInstitutionId()!=null && !ednQualificationTO.getInstitutionId().equalsIgnoreCase(AdmissionFormHelper.OTHER)){
								Iterator<UniversityTO> colItr=universityList.iterator();
								while (colItr.hasNext()) {
									UniversityTO unTO = (UniversityTO) colItr.next();
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
					// add the new items
					List<EdnQualificationTO> newItems=this.prepareQualificationsFromExamBos(extraList);
					
					List<UniversityTO> universityList =null;
					if (newItems != null && !newItems.isEmpty() ) {
						if( UniversityHandler.getInstance().getUniversity() != null){
							universityList = UniversityHandler.getInstance().getUniversity();
							
							
						}
					
						Iterator<EdnQualificationTO> itr = newItems.iterator();
						while (itr.hasNext()) {
							EdnQualificationTO ednTO = itr.next();
							if( universityList != null){
								ednTO.setUniversityList(universityList);
								ednTO.setInstituteList(new ArrayList<CollegeTO>());
							}
						}
					}
					ednQualificationList.addAll(newItems);
					
					
				}
			}else{
				
				ednQualificationList=this.prepareQualificationsFromExamBos(exambos);
				
				List<UniversityTO> universityList =null;
				if (ednQualificationList != null && !ednQualificationList.isEmpty() ) {
					if( UniversityHandler.getInstance().getUniversity() != null){
						universityList = UniversityHandler.getInstance().getUniversity();
						
						
					}
					
					Iterator<EdnQualificationTO> iterator = ednQualificationList.iterator();
					while (iterator.hasNext()) {
						EdnQualificationTO ednTO = iterator.next();
						if( universityList != null){
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
		 */
		private void convertDetailMarkBOtoTO(CandidateMarks detailMarkBO,
				CandidateMarkTO markTO) {
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
				
				if(detailMarkBO.getDetailedSubjects2() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects2().getId());
					markTO.setSubject2(detailMarkBO.getDetailedSubjects2().getSubjectName());
					markTO.setSubject2Mandatory(true);
					markTO.setDetailedSubjects2(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject2() != null && detailMarkBO.getSubject2().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects2(detailedSubjectsTO);
					markTO.setSubject2(detailMarkBO.getSubject2());
				}
				
				if(detailMarkBO.getDetailedSubjects3() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects3().getId());
					markTO.setSubject3(detailMarkBO.getDetailedSubjects3().getSubjectName());
					markTO.setSubject3Mandatory(true);
					markTO.setDetailedSubjects3(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject3() != null && detailMarkBO.getSubject3().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects3(detailedSubjectsTO);
					markTO.setSubject3(detailMarkBO.getSubject3());
				}
				
				if(detailMarkBO.getDetailedSubjects4() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects4().getId());
					markTO.setSubject4(detailMarkBO.getDetailedSubjects4().getSubjectName());
					markTO.setSubject4Mandatory(true);
					markTO.setDetailedSubjects4(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject4() != null && detailMarkBO.getSubject4().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects4(detailedSubjectsTO);
					markTO.setSubject4(detailMarkBO.getSubject4());
				}
				
				if(detailMarkBO.getDetailedSubjects5() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects5().getId());
					markTO.setSubject5(detailMarkBO.getDetailedSubjects5().getSubjectName());
					markTO.setSubject5Mandatory(true);
					markTO.setDetailedSubjects5(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject5() != null && detailMarkBO.getSubject5().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects5(detailedSubjectsTO);
					markTO.setSubject5(detailMarkBO.getSubject5());
				}
				
				if(detailMarkBO.getDetailedSubjects6() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects6().getId());
					markTO.setSubject6(detailMarkBO.getDetailedSubjects6().getSubjectName());
					markTO.setSubject6Mandatory(true);
					markTO.setDetailedSubjects6(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject6() != null && detailMarkBO.getSubject6().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects6(detailedSubjectsTO);
					markTO.setSubject6(detailMarkBO.getSubject6());
				}
				
				if(detailMarkBO.getDetailedSubjects7() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects7().getId());
					markTO.setSubject7(detailMarkBO.getDetailedSubjects7().getSubjectName());
					markTO.setSubject7Mandatory(true);
					markTO.setDetailedSubjects7(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject7() != null && detailMarkBO.getSubject7().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects7(detailedSubjectsTO);
					markTO.setSubject7(detailMarkBO.getSubject7());
				}
				
				if(detailMarkBO.getDetailedSubjects8() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects8().getId());
					markTO.setSubject8(detailMarkBO.getDetailedSubjects8().getSubjectName());
					markTO.setSubject8Mandatory(true);
					markTO.setDetailedSubjects8(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject8() != null && detailMarkBO.getSubject8().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects8(detailedSubjectsTO);
					markTO.setSubject8(detailMarkBO.getSubject8());
				}
				
				if(detailMarkBO.getDetailedSubjects9() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects9().getId());
					markTO.setSubject9(detailMarkBO.getDetailedSubjects9().getSubjectName());
					markTO.setSubject9Mandatory(true);
					markTO.setDetailedSubjects9(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject9() != null && detailMarkBO.getSubject9().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects9(detailedSubjectsTO);
					markTO.setSubject9(detailMarkBO.getSubject9());
				}
				
				if(detailMarkBO.getDetailedSubjects10() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects10().getId());
					markTO.setSubject10(detailMarkBO.getDetailedSubjects10().getSubjectName());
					markTO.setSubject10Mandatory(true);
					markTO.setDetailedSubjects10(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject10() != null && detailMarkBO.getSubject10().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects10(detailedSubjectsTO);
					markTO.setSubject10(detailMarkBO.getSubject10());
				}
				
				if(detailMarkBO.getDetailedSubjects11() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects11().getId());
					markTO.setSubject11(detailMarkBO.getDetailedSubjects11().getSubjectName());
					markTO.setSubject11Mandatory(true);
					markTO.setDetailedSubjects11(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject11() != null && detailMarkBO.getSubject11().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects11(detailedSubjectsTO);
					markTO.setSubject11(detailMarkBO.getSubject11());
				}
				
				if(detailMarkBO.getDetailedSubjects12() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects12().getId());
					markTO.setSubject12(detailMarkBO.getDetailedSubjects12().getSubjectName());
					markTO.setSubject12Mandatory(true);
					markTO.setDetailedSubjects12(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject12() != null && detailMarkBO.getSubject12().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects12(detailedSubjectsTO);
					markTO.setSubject12(detailMarkBO.getSubject12());
				}
				
				if(detailMarkBO.getDetailedSubjects13() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects13().getId());
					markTO.setSubject13(detailMarkBO.getDetailedSubjects13().getSubjectName());
					markTO.setSubject13Mandatory(true);
					markTO.setDetailedSubjects13(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject13() != null && detailMarkBO.getSubject13().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects13(detailedSubjectsTO);
					markTO.setSubject13(detailMarkBO.getSubject13());
				}
				
				if(detailMarkBO.getDetailedSubjects14() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects14().getId());
					markTO.setSubject14(detailMarkBO.getDetailedSubjects14().getSubjectName());
					markTO.setSubject14Mandatory(true);
					markTO.setDetailedSubjects14(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject14() != null && detailMarkBO.getSubject14().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects14(detailedSubjectsTO);
					markTO.setSubject14(detailMarkBO.getSubject14());
				}
				
				if(detailMarkBO.getDetailedSubjects15() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects15().getId());
					markTO.setSubject15(detailMarkBO.getDetailedSubjects15().getSubjectName());
					markTO.setSubject15Mandatory(true);
					markTO.setDetailedSubjects15(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject15() != null && detailMarkBO.getSubject15().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects15(detailedSubjectsTO);
					markTO.setSubject15(detailMarkBO.getSubject15());
				}
				
				if(detailMarkBO.getDetailedSubjects16() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects16().getId());
					markTO.setSubject16(detailMarkBO.getDetailedSubjects16().getSubjectName());
					markTO.setSubject16Mandatory(true);
					markTO.setDetailedSubjects16(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject16() != null && detailMarkBO.getSubject16().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects16(detailedSubjectsTO);
					markTO.setSubject16(detailMarkBO.getSubject16());
				}
				
				if(detailMarkBO.getDetailedSubjects17() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects17().getId());
					markTO.setSubject17(detailMarkBO.getDetailedSubjects17().getSubjectName());
					markTO.setSubject17Mandatory(true);
					markTO.setDetailedSubjects17(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject17() != null && detailMarkBO.getSubject17().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects17(detailedSubjectsTO);
					markTO.setSubject17(detailMarkBO.getSubject17());
				}
				
				if(detailMarkBO.getDetailedSubjects18() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects18().getId());
					markTO.setSubject18(detailMarkBO.getDetailedSubjects18().getSubjectName());
					markTO.setSubject18Mandatory(true);
					markTO.setDetailedSubjects18(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject18() != null && detailMarkBO.getSubject18().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects18(detailedSubjectsTO);
					markTO.setSubject18(detailMarkBO.getSubject18());
				}
				
				if(detailMarkBO.getDetailedSubjects19() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects19().getId());
					markTO.setSubject19(detailMarkBO.getDetailedSubjects19().getSubjectName());
					markTO.setSubject19Mandatory(true);
					markTO.setDetailedSubjects19(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject19() != null && detailMarkBO.getSubject19().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects19(detailedSubjectsTO);
					markTO.setSubject19(detailMarkBO.getSubject19());
				}
				
				if(detailMarkBO.getDetailedSubjects20() != null) {
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(detailMarkBO.getDetailedSubjects20().getId());
					markTO.setSubject20(detailMarkBO.getDetailedSubjects20().getSubjectName());
					markTO.setSubject20Mandatory(true);
					markTO.setDetailedSubjects20(detailedSubjectsTO);
				}else if(detailMarkBO.getSubject20() != null && detailMarkBO.getSubject20().length() !=0){
					// setting others.	
					DetailedSubjectsTO detailedSubjectsTO = new DetailedSubjectsTO();
					detailedSubjectsTO.setId(-1);
					markTO.setDetailedSubjects20(detailedSubjectsTO);
					markTO.setSubject20(detailMarkBO.getSubject20());
				}
				
				
				markTO.setId(detailMarkBO.getId());
				markTO.setCreatedBy(detailMarkBO.getCreatedBy());
				markTO.setCreatedDate(detailMarkBO.getCreatedDate());
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
				if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().intValue()!=0)
				markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().intValue()));
				if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().intValue()!=0)
				markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().intValue()));
			}
			log.info("exit convertDetailMarkBOtoTO" );
		}
		/**
		 * @param preferencesSetBO
		 * @return 
		 */
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
		public List<ApplnDocTO> copyPropertiesEditDocValue(Set<ApplnDoc> docUploadSet, int courseId, AdmApplnTO adminAppTO, int appliedYear) throws Exception {
			log.info("enter copyPropertiesEditDocValue" );
			List<ApplnDocTO> documentsList = new ArrayList<ApplnDocTO>();
			ApplnDocTO applnDocTO = null;
			
		
				AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
				List<ApplnDocTO> reqList=handler.getRequiredDocList(String.valueOf(courseId),appliedYear);
				
				
				boolean photoexist=false;
			if (docUploadSet != null && !docUploadSet.isEmpty()) {
				Iterator<ApplnDoc> iterator = docUploadSet.iterator();
				while (iterator.hasNext()) {
					
					ApplnDoc applnDocBO = (ApplnDoc) iterator.next();

					applnDocTO = new ApplnDocTO();
					
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
					applnDocTO.setSubmitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applnDocBO.getSubmissionDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
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
					if(applnDocBO.getIsPhoto()!=null && applnDocBO.getIsPhoto()){
						applnDocTO.setPhoto(true);
						applnDocTO.setDocName(AdmissionFormHelper.PHOTO);
						applnDocTO.setPrintName(AdmissionFormHelper.PHOTO);
						if(applnDocBO.getName()!=null && AdmissionFormHelper.PHOTO.equalsIgnoreCase(applnDocBO.getName()))
						{
							applnDocTO.setDefaultPhoto(true);
						}
						photoexist=true;
						byte [] myFileBytes = applnDocBO.getDocument();
						applnDocTO.setPhotoBytes(myFileBytes);
					}else{
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
					if( applnDocBO.getDocType() != null ){
						docChecklistDoc = applnDocBO.getDocType().getDocChecklists();
					}
					
					if (docChecklistDoc != null) {
						Iterator<DocChecklist> it = docChecklistDoc.iterator();
						while (it.hasNext()) {
							DocChecklist docChecklistBO = (DocChecklist) it.next();
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
								to.setId(bo.getId());
								to.setApplnDocId(applnDocBO.getId());
								to.setSemNo(bo.getSemesterNo());
//								to.setIsHardCopySubmitted(bo.getIsHardCopySubmited());
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
				AdmissionFormForm admForm, Set<CandidateEntranceDetails> candidateentrances) {
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
					 for (Iterator it=subjectMap.entrySet().iterator(); it.hasNext(); ) {
					        Map.Entry entry = (Map.Entry)it.next();
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
			public AdmApplnTO getNewStudent(HttpSession session,String courseID,AdmissionFormForm stForm) throws Exception {
				log.info("enter getNewStudent");
				AdmissionFormHandler handler = AdmissionFormHandler.getInstance();
				IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
				AdmAppln applicationData=null;
				if(session!=null && session.getAttribute(CMSConstants.APPLICATION_DATA)!=null){
						applicationData=(AdmAppln)session.getAttribute(CMSConstants.APPLICATION_DATA);

				}
				int courseid=0;
				if(courseID!=null && !StringUtils.isEmpty(courseID.trim()) && StringUtils.isNumeric(courseID))
				{
					courseid=Integer.parseInt(courseID);
				}
				if(courseid!=0){
					
					/*Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					int year=cal.get(cal.YEAR);*/
					int year = Integer.parseInt(stForm.getApplicationYear());
					
				AdmApplnTO adminAppTO = new AdmApplnTO();
				if(applicationData!=null){
				
				
				adminAppTO.setCreatedBy(applicationData.getCreatedBy());
				adminAppTO.setCreatedDate(applicationData.getCreatedDate());
				adminAppTO.setIsFinalMeritApproved(applicationData.getIsFinalMeritApproved());
//				adminAppTO.setApplicationId(applicationData.getId());
				adminAppTO.setRemark(applicationData.getRemarks());
				if(stForm.getApplicationNumber()!=null && !StringUtils.isEmpty(stForm.getApplicationNumber()) && StringUtils.isNumeric(stForm.getApplicationNumber()))
				adminAppTO.setApplnNo(Integer.parseInt(stForm.getApplicationNumber()));
				adminAppTO.setChallanRefNo(applicationData.getChallanRefNo());
				adminAppTO.setJournalNo(applicationData.getJournalNo());
				adminAppTO.setBankBranch(applicationData.getBankBranch());
				adminAppTO.setAppliedYear(applicationData.getAppliedYear());
				adminAppTO.setIsSelected(applicationData.getIsSelected());
				stForm.setMode(applicationData.getMode());
				//Added By Manu
				if(applicationData.getNotSelected()==null){
					adminAppTO.setNotSelected(false);
				}
				else{
					adminAppTO.setNotSelected(applicationData.getNotSelected());
				}
				if(applicationData.getIsWaiting()==null){
					adminAppTO.setIsWaiting(false);
				}
				else{
					adminAppTO.setIsWaiting(applicationData.getIsWaiting());
				}
				adminAppTO.setNotSelected(applicationData.getNotSelected());
				adminAppTO.setIsWaiting(applicationData.getIsWaiting());
				//
				adminAppTO.setIsBypassed(applicationData.getIsBypassed());
				adminAppTO.setIsCancelled(applicationData.getIsCancelled());
				adminAppTO.setIsFreeShip(applicationData.getIsFreeShip());
				adminAppTO.setIsApproved(applicationData.getIsApproved());
				adminAppTO.setIsLIG(applicationData.getIsLig());
			    adminAppTO.setDdDrawnOn(applicationData.getDdDrawnOn());
			    adminAppTO.setDdIssuingBank(applicationData.getDdIssuingBank());
			   if(applicationData.getInternationalCurrencyId()!=null && !applicationData.getInternationalCurrencyId().isEmpty()){
			    adminAppTO.setInternationalCurrencyId(applicationData.getInternationalCurrencyId());
			   }
				adminAppTO.setIsInterviewSelected(applicationData.getIsInterviewSelected());

				
				
				if(applicationData.getDate()!= null){
					adminAppTO.setChallanDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applicationData.getDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
				}
//				if(applicationData.getAdmissionDate()!= null){
//					adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applicationData.getAdmissionDate()), AdmissionFormHelper.SQL_DATEFORMAT,AdmissionFormHelper.FROM_DATEFORMAT));
//				}
				if(applicationData.getAmount()!=null)
				adminAppTO.setAmount(String.valueOf(applicationData.getAmount().doubleValue()));
				
				adminAppTO.setCandidatePrerequisiteMarks(applicationData.getCandidatePrerequisiteMarks());
				//set the pre requisite details to display in single page servlet final page
				if(applicationData.getCandidatePrerequisiteMarks()!=null && !applicationData.getCandidatePrerequisiteMarks().isEmpty()){
					for (CandidatePrerequisiteMarks preRequisiteMarks : applicationData.getCandidatePrerequisiteMarks()) {
						if(preRequisiteMarks!=null){
						adminAppTO.setPreRequisiteObtMarks(preRequisiteMarks.getPrerequisiteMarksObtained()!=null?preRequisiteMarks.getPrerequisiteMarksObtained().toPlainString():"");
						adminAppTO.setPreRequisiteRollNo(preRequisiteMarks.getRollNo()!=null?preRequisiteMarks.getRollNo():"");
						adminAppTO.setPreRequisiteExamMonth(preRequisiteMarks.getExamMonth()!=null?String.valueOf(preRequisiteMarks.getExamMonth()):"");
						adminAppTO.setPreRequisiteExamYear(preRequisiteMarks.getExamYear()!=null?String.valueOf(preRequisiteMarks.getExamYear()):"");
						break;
						}
					}
					
				}
			
				adminAppTO.setVehicleDetail(new StudentVehicleDetailsTO());
				
				PersonalDataTO personalDataTO = new PersonalDataTO();
				//setting the candidate name , dob,email, mobile no and resident category 
				personalDataTO.setFirstName(stForm.getApplicantName());
				personalDataTO.setDob(stForm.getApplicantDob());
				personalDataTO.setResidentCategory(stForm.getResidentCategoryForOnlineAppln());
				personalDataTO.setEmail(stForm.getEmail());
				personalDataTO.setConfirmEmail(stForm.getConfirmEmail());
				personalDataTO.setMobileNo1(stForm.getMobileNo1());
				personalDataTO.setMobileNo2(stForm.getMobileNo2());
				personalDataTO.setGender("FEMALE");
				
				adminAppTO.setPersonalData(personalDataTO);
				adminAppTO.setEntranceDetail(new CandidateEntranceDetailsTO());
				
				CourseHandler crshandle= CourseHandler.getInstance();
				List<CourseTO> courses=crshandle.getCourse(courseid);
				CourseTO courseTO = new CourseTO();
				
				if(courses!=null){
					Iterator<CourseTO> crsItr=courses.iterator();
					while (crsItr.hasNext()) {
						CourseTO crsTO = (CourseTO) crsItr.next();
						if(crsTO.getId()==applicationData.getCourse().getId())
						{
							courseTO=crsTO;
						}
					}
				}
				
				adminAppTO.setCourse(courseTO);
				
				adminAppTO.setSelectedCourse(courseTO);
				
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
				
				
				List<EdnQualificationTO> ednQualificationList = getEdnQualifications(stForm, year);
				adminAppTO.setEdnQualificationList(ednQualificationList);
				
				ProgramHandler pgmhandle= ProgramHandler.getInstance();
				ProgramTO pgmn=pgmhandle.getProgramDetails(applicationData.getCourse().getProgram().getId());
				if(pgmn.getIsExamCenterRequired()!=null){
				 if(pgmn.getIsExamCenterRequired()){
					stForm.setDisplayExamCenterDetails(true);
				}else{
					stForm.setDisplayExamCenterDetails(false);
				}
				 if(pgmn.getAcademicYear()!=null && !pgmn.getAcademicYear().isEmpty()){
					 stForm.setProgramYear(String.valueOf(pgmn.getAcademicYear()));
				 }
				}
				/* Code added by mary for interview selection schedule only--- starts*/
					/*List<InterviewSelectionSchedule> InterviewDefinedList = txn.getInterviewSelectionScheduleByPgmId(Integer.parseInt(stForm.getProgramId()), year);
					if(InterviewDefinedList!=null && !InterviewDefinedList.isEmpty()){
						    stForm.setIsInterviewSelectionSchedule("true");
							Map<Integer,Integer> interviewselectPreviousList = getPreviousInterviewSelectionSchedule(stForm, year);
							List<InterviewSelectionScheduleTO> interviewselectscheduleList = getInterviewSelectionSchedule(interviewselectPreviousList,stForm, year);
							if(interviewselectscheduleList!=null && !interviewselectscheduleList.isEmpty()){
							stForm.setInterviewSelectionSchedule(interviewselectscheduleList);
							}
					}*/
					
			//	List<InterviewSelectionScheduleTO> interviewselectscheduleList=null;
					List<InterviewSelectionSchedule> InterviewDefinedList = txn.getInterviewSelectionScheduleByPgmId(Integer.parseInt(stForm.getProgramId()), year);
					if(InterviewDefinedList!=null && !InterviewDefinedList.isEmpty()){
						    stForm.setIsInterviewSelectionSchedule("true");
						//	Map<Integer,Integer> interviewselectPreviousList = getPreviousInterviewSelectionSchedule(stForm, year);
							if(stForm.isOnlineApply()){
								getInterviewSelectionScheduleOnline(stForm,year);
							}else
							{
								getInterviewSelectionScheduleOffline(stForm, year);
							}
							//if(interviewselectscheduleList!=null && !interviewselectscheduleList.isEmpty()){
							//stForm.setInterviewSelectionSchedule(interviewselectscheduleList);
							//}
					}
				/* Code added by mary for interview selection schedule only---- ends*/
				
				List<ApplnDocTO> reqList=handler.getRequiredDocList(String.valueOf(courseid),year);
				adminAppTO.setEditDocuments(reqList);

				List<ApplicantLateralDetailsTO> lateralTos= new ArrayList<ApplicantLateralDetailsTO>();
				adminAppTO.setLateralDetails(lateralTos);
				List<ApplicantTransferDetailsTO> transferTos= new ArrayList<ApplicantTransferDetailsTO>();
				adminAppTO.setTransferDetails(transferTos);
				}
				log.info("exit getNewStudent");
				return adminAppTO;
				}else{
					log.info("exit getNewStudent");
					return null;
				}
			}
			
			/**
			 * prepare EdnQualificationTos for Admission form
			 * @param admForm
			 * @return
			 * @throws Exception
			 */
			public List<EdnQualificationTO> getEdnQualifications(AdmissionFormForm admForm, int year) throws Exception {
				log.info("Enter getEdnQualifications ...");
				/*
				Calendar cal= Calendar.getInstance();
				cal.setTime(new Date());
				int year=cal.get(cal.YEAR);
				*/
				IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
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

		/**
		 * ExamCenterBO to TO Conversion
		 * @param residentbos
		 * @return
		 */
		public List<ExamCenterTO> convertexamCenterBOToTO(
				List<ExamCenter> examcenterList) {
			log.info("enter convertexamCenterBOToTO" );
			List<ExamCenterTO> tolist= new ArrayList<ExamCenterTO>();
			if(examcenterList!=null){
				Iterator<ExamCenter> itr= examcenterList.iterator();
				while (itr.hasNext()) {
					ExamCenter  examCenter = (ExamCenter) itr.next();
					ExamCenterTO examCenterTO= new ExamCenterTO();
					examCenterTO.setId(examCenter.getId());
					examCenterTO.setCenter(examCenter.getCenter());
					tolist.add(examCenterTO);
					
				}
			}
			log.info("exit convertexamCenterBOToTO" );
			return tolist;

		}			
		
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
		public Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear,int id) throws Exception{
			IAdmissionFormTransaction transaction=new AdmissionFormTransactionImpl();
			return transaction.getSubjectGroupByYearAndCourse(appliedYear,id);
		}
		public AdmApplnTO copyPropertiesValueForPendingDoc(AdmAppln admApplnBo,
				HttpServletRequest request) throws Exception {

			log.info("enter copyPropertiesValue admappln");
			AdmApplnTO adminAppTO = null;
			PersonalDataTO personalDataTO = null;
			CourseTO courseTO = null;
			List<ApplnDocTO> editDocuments = null;

			if (admApplnBo != null) {
				adminAppTO = new AdmApplnTO();
				adminAppTO.setId(admApplnBo.getId());
				adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
				adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
				adminAppTO.setIsFinalMeritApproved(admApplnBo
						.getIsFinalMeritApproved());
				adminAppTO.setApplicationId(admApplnBo.getId());
				;
				adminAppTO.setApplnNo(admApplnBo.getApplnNo());

				adminAppTO.setAppliedYear(admApplnBo.getAppliedYear());

				personalDataTO = copyPropertiesValue(admApplnBo.getPersonalData(),admApplnBo);
				adminAppTO.setPersonalData(personalDataTO);

				courseTO = copyPropertiesValue(admApplnBo.getCourse());
				adminAppTO.setCourse(courseTO);

				CourseTO courseTO1 = copyPropertiesValue(admApplnBo
						.getCourseBySelectedCourseId());
				adminAppTO.setSelectedCourse(courseTO1);

				editDocuments = copyPropertiesEditDocValue(admApplnBo
						.getApplnDocs(), adminAppTO.getCourse().getId(),
						adminAppTO, admApplnBo.getAppliedYear());
				adminAppTO.setEditDocuments(editDocuments);

			}

			return adminAppTO;
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
//									detailName=detailName+type+bo.getSemesterNo()+": Submitted ";
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
								name=AdmissionFormHelper.PHOTO;
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
										name=AdmissionFormHelper.PHOTO;
									}
							}else if(appDoc.getName()!=null && appDoc.getNotApplicable()!=true){
								name=appDoc.getName();
								if(appDoc.getIsPhoto())
								{
									name=AdmissionFormHelper.PHOTO;
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
		
		
		
		
		public CandidatePGIDetails convertToBo(AdmissionFormForm admForm) throws Exception {/*
			String[] responseArray={""};
			StringBuilder responseMsg = new StringBuilder();
			//admForm.setResponseMsg("CHRISTUNIV|TESTN179447|EUPG4065631154|201510155915476|10.00|UPG|552274|02|INR|DIRECT|NA|NA|NA|15-10-2015 16:00:20|0300|NA|applicationFee|NA|NA|NA|NA|NA|NA|NA|Transaction Successful|4137125474");
			String checkSumFromPgi="";
			CandidatePGIDetails bo;
			log.error(admForm.getResponseMsg());
			if(admForm.getResponseMsg()!=null){
			 responseArray=admForm.getResponseMsg().split(("\\|"));
			}
			log.error(responseArray.length);
			for(int i=0;i<(responseArray.length-1);i++){
				if(i<(responseArray.length-2)){
				 responseMsg.append(responseArray[i]).append("|");
				}
				else responseMsg.append(responseArray[i]);
			}
			log.error(responseArray[25]);
			log.error("Response Msg:"+responseMsg);
			checkSumFromPgi=responseArray[25];
			String checkSum=null;
			if (admForm.getInstitute().equals("LV") || admForm.getInstitute().equals("GH")) {
				checkSum=HmacSHA256(responseMsg.toString(),KPPropertiesConfiguration.PGI_CHECKSUM_KEY);
			} else {
				checkSum=PGIUtil.doDigest(responseMsg.toString(),KPPropertiesConfiguration.PGI_CHECKSUM_KEY);
			}
			log.error("CalculatedCheckSum="+checkSum);
			if(checkSumFromPgi!=null && checkSum!=null && !checkSumFromPgi.trim().equalsIgnoreCase(checkSum.trim())){
				throw  new BusinessException("CalculatedCheckSum="+checkSum+", checkSumFromPgi="+checkSumFromPgi);
			}
			else{
			 bo= new CandidatePGIDetails();
			bo.setCandidateRefNo(responseArray[1]);
			bo.setTxnRefNo(responseArray[2]);
			if(responseArray[4]!=null && !responseArray[4].equalsIgnoreCase("NA"))
			bo.setTxnAmount(new BigDecimal(responseArray[4]));
			bo.setBankId(responseArray[5]);
			bo.setBankRefNo(responseArray[6]);
			if(responseArray[13]!=null && !responseArray[13].equalsIgnoreCase("NA"))
			bo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(responseArray[13], "dd-MM-yyyy hh:mm:ss", "dd/MM/yyyy")));
			if(responseArray[14]!=null){
			if(responseArray[14].equalsIgnoreCase("0300")){
				bo.setTxnStatus("Success");
				admForm.setPgiStatus("Payment Successful");
				admForm.setTxnAmt(responseArray[4]);
				admForm.setTxnRefNo(responseArray[2]);
				admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(responseArray[13], "dd-MM-yyyy hh:mm:ss", "dd/MM/yyyy"));
				}
			else if(responseArray[14].equalsIgnoreCase("0399")){
				bo.setTxnStatus("Invalid Authentication at Bank");
				admForm.setPgiStatus("Invalid Authentication at Bank");
				}
			else if(responseArray[14].equalsIgnoreCase("NA")){
				bo.setTxnStatus("Invalid Input in the Request Message");
				admForm.setPgiStatus("Invalid Input in the Request Message");
				}
			else if(responseArray[14].equalsIgnoreCase("0002")){
				bo.setTxnStatus("BillDesk is waiting for Response from Bank");
				admForm.setPgiStatus("BillDesk is waiting for Response from Bank");
				}
			else if(responseArray[14].equalsIgnoreCase("0001")){
				bo.setTxnStatus("Error at BillDesk");
				admForm.setPgiStatus("Error at BillDesk");
				}else{
					bo.setTxnStatus(responseArray[14]); // for checking the status as there are so many pending txs
				}
			}
			if(responseArray[23]!=null && !responseArray[23].equalsIgnoreCase("NA")){
				bo.setErrorStatus(responseArray[23]);
			}
			if(responseArray[24]!=null && !responseArray[24].equalsIgnoreCase("NA")){
				bo.setErrorStatus(responseArray[24]);
			}
			}
			return bo;
		*/
			
		
		
			String[] responseArray={};
			StringBuilder responseMsg = new StringBuilder();
			String checkSumFromPgi="";
			CandidatePGIDetails bo=new CandidatePGIDetails();
			StringBuilder temp=new StringBuilder();
			//raghu
			//log.error(admForm.getResponseMsg());
			log.error(admForm.getHash());
			if(admForm.getResponseMsg()!=null){
			//responseArray=admForm.getResponseMsg().split(("\\|"));
			 responseArray=admForm.getHash().split(("\\|"));
			}
			
			temp.append(CMSConstants.PGI_MERCHANT_ID).append("|").append(admForm.getTxnid()).append("|").append(admForm.getAmount()).append(admForm.getStatus()).append("|").append(admForm.getHash());
			
			
			System.out.println("+++++++++++++++++++++++++++++++++++  this is data before hash alogoritham ++++++++++++++++++++++++++++++"+temp.toString());
			
			//sha512(additionalCharges|<SALT>|status|||||||||||email|firstname|productinfo|amount|txnid|key)
			//raghu write for pay e
			String hash=AdmissionFormHandler.getInstance().sha1("SHA-1",temp.toString());
			
			
			System.out.println("+++++++++++++++++++++++++++++++++++  this is data of after  hash  generation ++++++++++++++++++++++++++++++"+hash);
			
			System.out.println("+++++++++++++++++++++++++++++++++++  this is data of pay u hash ++++++++++++++++++++++++++++++"+admForm.getHash());
			
			if(admForm.getHash()!=null && hash!=null && !admForm.getHash().equals(hash)){
				log.error("############################ Your Data Tamperd ########################");
				throw  new BusinessException();
			}else{
				bo.setCandidateRefNo(admForm.getTxnid());
				//bo.setTxnRefNo(admForm.getPayuMoneyId());
				bo.setTxnAmount(new BigDecimal(admForm.getAmount()));
				//bo.setAdditionalCharges(new BigDecimal(admForm.getAdditionalCharges()));
				//bo.setBankRefNo(admForm.getBank_ref_num());
				bo.setTxnStatus(admForm.getStatus());
				//bo.setErrorStatus(admForm.getError1());
				//raghu setting current date
				bo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy")));
				//bo.setMode(admForm.getMode1());
				//bo.setUnmappedStatus(admForm.getUnmappedstatus());
				//bo.setMihpayId(admForm.getMihpayid());
				//bo.setPgType(admForm.getPG_TYPE());
				//raghu new
				//bo.setPaymentEmail(admForm.getPaymentMail());
				
				
				admForm.setPgiStatus("Payment Successful");
				admForm.setTxnAmt(admForm.getAmount());
				//admForm.setTxnRefNo(admForm.getPayuMoneyId());
				admForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy"));
				
			}
			
			return bo;
		
		}
		
		
		
		
		
		
		
		
		public void getInterviewSelectionScheduleOnline(AdmissionFormForm admForm, int year) throws Exception {
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			List<Object[]>  schedule =txn.getInterviewSelectionScheduleOnline(Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramYear()));
			prepareRequiredScheduleOnline(schedule,admForm);
		}
		
		public void getInterviewSelectionScheduleOffline(AdmissionFormForm admForm, int year) throws Exception {
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			List<Object[]>  schedule =txn.getInterviewSelectionScheduleOffline(Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramYear()));
			prepareRequiredScheduleOffline(schedule,admForm);
		}
		public Map<Integer, Integer> getPreviousInterviewSelectionSchedule(AdmissionFormForm admForm, int year) throws Exception {
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			List<Object[]> schedule =txn.getPreviousInterviewSelectionSchedule(Integer.parseInt(admForm.getProgramId()), year, admForm.isOnlineApply());
			return this.preparePreviousSchedule(schedule,admForm);
		}
			
		public void prepareRequiredScheduleOnline(List<Object[]> interviewBos,AdmissionFormForm admForm) throws Exception {
			List<InterviewSelectionScheduleTO> interschedule= new ArrayList<InterviewSelectionScheduleTO>();
			Map<Integer, String> interviewVenueSelection= new HashMap<Integer, String>();
			if(interviewBos!=null){
				Iterator<Object[]> itr= interviewBos.iterator();
				while (itr.hasNext()) {
					Object[] inter = (Object[]) itr.next();
					InterviewSelectionScheduleTO interTO = new InterviewSelectionScheduleTO();
					if(inter[0].toString()!=null)	{	
							interTO.setId(Integer.parseInt(inter[0].toString()));
					if(admForm.getProgramId()!=null){
								interTO.setProgramId(Integer.parseInt(admForm.getProgramId()));
						}
							interTO.setMaxNoSeatsOnline(Integer.parseInt(inter[2].toString()));	
							interTO.setMaxNoSeatsOffline(Integer.parseInt(inter[3].toString()));
												
							interTO.setCutOffDate(CommonUtil.formatSqlDate(inter[4].toString()));
							interTO.setSelectionProcessDate(CommonUtil.formatSqlDate(inter[5].toString()));
							interTO.setExamCenterId(inter[1].toString());
							interTO.setExamCentreName(inter[7].toString());
						} 				
					if(Integer.parseInt(inter[2].toString())>Integer.parseInt(inter[6].toString())){
						interschedule.add(interTO);
						if(admForm.getCourseId()!=null && (admForm.getCourseId().equals("158") ||admForm.getCourseId().equals("280"))){
							if(inter[7].toString().equalsIgnoreCase("bangalore")){
								interviewVenueSelection.put(Integer.parseInt(inter[1].toString()), inter[7].toString());
							}
						}else
						{
							interviewVenueSelection.put(Integer.parseInt(inter[1].toString()), inter[7].toString());
						}
					}
					
				}
				admForm.setInterviewVenueSelection(interviewVenueSelection);
		}
	}
		
		public void prepareRequiredScheduleOffline(List<Object[]> interviewBos,AdmissionFormForm admForm) throws Exception {
			List<InterviewSelectionScheduleTO> interschedule= new ArrayList<InterviewSelectionScheduleTO>();
			Map<Integer, String> interviewVenueSelection= new HashMap<Integer, String>();
			if(interviewBos!=null){
				Iterator<Object[]> itr= interviewBos.iterator();
				while (itr.hasNext()) {
					Object[] inter = (Object[]) itr.next();
					InterviewSelectionScheduleTO interTO = new InterviewSelectionScheduleTO();
					if(inter[0].toString()!=null)	{	
							interTO.setId(Integer.parseInt(inter[0].toString()));
						if(admForm.getProgramId()!=null){
								interTO.setProgramId(Integer.parseInt(admForm.getProgramId()));
						}
							interTO.setMaxNoSeatsOffline(Integer.parseInt(inter[3].toString()));	
							interTO.setMaxNoSeatsOnline(Integer.parseInt(inter[2].toString()));
												
							interTO.setCutOffDate(CommonUtil.formatSqlDate(inter[4].toString()));
							interTO.setSelectionProcessDate(CommonUtil.formatSqlDate(inter[5].toString()));
							interTO.setExamCenterId(inter[1].toString());
							interTO.setExamCentreName(inter[7].toString());
						} 				
				if(Integer.parseInt(inter[3].toString())>Integer.parseInt(inter[6].toString())){
					interschedule.add(interTO);
					if(admForm.getCourseId()!=null && (admForm.getCourseId().equals("158") ||admForm.getCourseId().equals("280"))){
						if(inter[7].toString().equalsIgnoreCase("bangalore")){
							interviewVenueSelection.put(Integer.parseInt(inter[1].toString()), inter[7].toString());
						}
					}else
					{
						interviewVenueSelection.put(Integer.parseInt(inter[1].toString()), inter[7].toString());
					}
					
				}
				admForm.setInterviewVenueSelection(interviewVenueSelection);
			}
	}

}
		public Map<Integer,Integer> preparePreviousSchedule(List<Object[]> objects,AdmissionFormForm admForm) throws Exception {
			Map<Integer,Integer> interschedule= new HashMap<Integer,Integer>();
			if(objects!=null){
				Iterator<Object[]> itr= objects.iterator();
				while (itr.hasNext()) {
						Object[] obj = (Object[]) itr.next();
						InterviewSelectionScheduleTO interTO = new InterviewSelectionScheduleTO();
						if(obj[0].toString().equalsIgnoreCase("0")){
							break;
						}else
						{
							if(obj[0].toString()!=null && !obj[0].toString().isEmpty()){
								interTO.setAllottedSeats(obj[0].toString());
							}
							if(obj[1].toString()!=null && !obj[1].toString().isEmpty())	{	
									interTO.setId(Integer.parseInt(obj[1].toString()));
							}
											
							interschedule.put(interTO.getId(), Integer.parseInt(interTO.getAllottedSeats()));		
							}
				}
					} 
				return interschedule;	
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
		
		private boolean validatefutureDate(String dateString) {
			log.info("enter validatefutureDate..");
			boolean result=false;
			String formattedString = CommonUtil.ConvertStringToDateFormat(
					dateString, "dd/MM/yyyy",
					"MM/dd/yyyy");
			int i=1;
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

		//vibin
		public List<CandidatePreferenceTO> copyPropertiesValuepref(int id) throws Exception {
			IAdmissionFormTransaction tx = AdmissionFormTransactionImpl.getInstance();
			List<CandidatePreference> pref = tx.getpreferences(id);
			List<CandidatePreferenceTO> prefto = new ArrayList<CandidatePreferenceTO>();
			Iterator<CandidatePreference> itr = pref.iterator();
			if(pref!=null){
			  while(itr.hasNext()){
				  CandidatePreference bo = itr.next();
				  CandidatePreferenceTO to = new CandidatePreferenceTO();
				  to.setPrefNo(bo.getPrefNo());
				  to.setCoursName(bo.getCourse().getName());
				  to.setCoursId(String.valueOf(bo.getCourse().getId()));
				  if(bo.getCourse().getGeneralFee()!=null)
				    to.setGeneralfees(bo.getCourse().getGeneralFee());
				  if(bo.getCourse().getCasteFee()!=null)
				    to.setCastefees(bo.getCourse().getCasteFee());
				  if(bo.getCourse().getDateTime()!=null)
				    to.setDateAndTime(bo.getCourse().getDateTime());
				  prefto.add(to);
				  
			  }
				
			}
			return prefto;
		}
		
		private List<AdmSubjectMarkForRankTO> copyPropertiesValue(int id,String doctype) throws Exception {
		   IAdmissionFormTransaction tx = AdmissionFormTransactionImpl.getInstance();
		   List<AdmSubjectMarkForRank> admsubmarkbo = tx.get12thsubjmark(id,doctype);
		   List<AdmSubjectMarkForRankTO> admsubmarkto = new ArrayList<AdmSubjectMarkForRankTO>();
		   Iterator<AdmSubjectMarkForRank> itr = admsubmarkbo.iterator();
		   if(admsubmarkbo!=null){
		    while(itr.hasNext()){
			  AdmSubjectMarkForRank bo = itr.next();
			  AdmSubjectMarkForRankTO to = new AdmSubjectMarkForRankTO();
			  to.setId(bo.getId());
			  to.setConversionmark(bo.getConversionmark());
			  to.setMaxmark(bo.getMaxmark());
			  to.setObtainedmark(bo.getObtainedmark());
			  to.setSubjectName(bo.getAdmSubjectForRank().getName());
			  to.setGroupname(bo.getAdmSubjectForRank().getGroupName());
			  admsubmarkto.add(to);
		    }
		    
		   }
		   
		  
		    return admsubmarkto;
			
		}
		

}
