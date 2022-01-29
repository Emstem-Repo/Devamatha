package com.kp.cms.helpers.admission;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zefer.html.doc.s;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplicantLateralDetails;
import com.kp.cms.bo.admin.ApplicantMarksDetails;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ApplicantTransferDetails;
import com.kp.cms.bo.admin.ApplicantWorkExperience;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ApplnDocDetails;
import com.kp.cms.bo.admin.CandidateEntranceDetails;
import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.DetailedSubjects;
import com.kp.cms.bo.admin.DioceseBO;
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
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.MBAEntranceExam;
import com.kp.cms.bo.admin.MotherTongue;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.bo.admin.ParishBO;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCommonRank;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentExtracurricular;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admin.StudentVehicleDetails;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.admission.ApplicationEditHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplicantWorkExperienceTO;
import com.kp.cms.to.admin.ApplnDocDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidateEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CandidatePrerequisiteMarksTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.SeatAllocationTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admin.StudentVehicleDetailsTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.AdmSubjectForRankTo;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.admission.PreferenceTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationEditTransactionimpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class ApplicationEditHelper {
	/**
	 * Singleton object of ApplicationEditHelper
	 */
	private static volatile ApplicationEditHelper applicationEditHelper = null;
	private static final Log log = LogFactory.getLog(ApplicationEditHelper.class);
	private static final String OTHER="Other";
	private static final String PHOTO="Photo";
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	private static final String SQL_DATEFORMAT1="yyyy-MM-dd";
	//raghu added newly
	private static Map<Integer,String> prefNameMap=null;
	
	
	static {
		
		prefNameMap = new HashMap<Integer, String>();
		prefNameMap.put(0, "First Preference");
		prefNameMap.put(1, "Second Preference");
		prefNameMap.put(2, "Third Preference");
		prefNameMap.put(3, "Fourth Preference");
		prefNameMap.put(4, "Fifth Preference");
		prefNameMap.put(5, "Sixth Preference");
		prefNameMap.put(6, "Seventh Preference");
		prefNameMap.put(7, "Eighth Preference");
		prefNameMap.put(8, "Ninth Preference");
		prefNameMap.put(9, "Tenth Preference");
		prefNameMap.put(10, "Eleventh Preference");
		prefNameMap.put(11, "Twelfth Preference");
		prefNameMap.put(12, "Thirteenth Preference");
		prefNameMap.put(13, "Fourteenth Preference");
		prefNameMap.put(14, "Fifteenth Preference");
		prefNameMap.put(15, "Sixteenth Preference");
		prefNameMap.put(16, "Seventeenth Preference");
		
		
	}
	

	private ApplicationEditHelper() {
		
	}
	/**
	 * return singleton object of ApplicationEditHelper.
	 * @return
	 */
	public static ApplicationEditHelper getInstance() {
		if (applicationEditHelper == null) {
			applicationEditHelper = new ApplicationEditHelper();
		}
		return applicationEditHelper;
	}
	/**
	 * @param applicantDetails
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO copyPropertiesValue(AdmAppln admApplnBo, int year, ApplicationEditForm admForm) throws Exception {
		log.info("enter copyPropertiesValue admappln" );
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		AdmApplnTO adminAppTO = null;
		PersonalDataTO personalDataTO = null;
		CourseTO courseTO = null;
		//Commented By Manu.List not be null initialized
		/*List<EdnQualificationTO> ednQualificationList = null;
		List<ApplicantWorkExperienceTO> workExpList = null;
		List<CandidatePrerequisiteMarksTO> prereqList = null;*/
		List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
		List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
		List<CandidatePrerequisiteMarksTO> prereqList = new ArrayList<CandidatePrerequisiteMarksTO>();
		List<AdmSubjectMarkForRankTO> admsubList = new ArrayList<AdmSubjectMarkForRankTO>();
		PreferenceTO preferenceTO = null;

		List<ApplnDocTO> editDocuments = null;


		if (admApplnBo != null) {
			adminAppTO = new AdmApplnTO();
			adminAppTO.setId(admApplnBo.getId());
			adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
			adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
			//added by vishnu
			adminAppTO.setAdmissionNumber(admApplnBo.getAdmissionNumber());
			adminAppTO.setIsFinalMeritApproved(admApplnBo.getIsFinalMeritApproved());
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
			//
			adminAppTO.setIsBypassed(admApplnBo.getIsBypassed());
			adminAppTO.setIsCancelled(admApplnBo.getIsCancelled());
			if(admApplnBo.getIsFreeShip()!=null && admApplnBo.getIsFreeShip()){
				adminAppTO.setIsFreeShip(true);
			}else{
				adminAppTO.setIsFreeShip(false);
			}
			// added for challan verification 
			adminAppTO.setIsChallanVerified(admApplnBo.getIsChallanVerified());
			
			//raghu added for challan and dd status
			adminAppTO.setIsDDRecieved(admApplnBo.getIsDDRecieved());
			adminAppTO.setIsChallanRecieved(admApplnBo.getIsChallanRecieved());
			adminAppTO.setRecievedDDNo(admApplnBo.getRecievedDDNo());
			adminAppTO.setRecievedChallanNo(admApplnBo.getRecievedChallanNo());
			if(admApplnBo.getRecievedChallanDate()!=null )
				adminAppTO.setRecievedChallanDate(CommonUtil.ConvertStringToDateFormat(admApplnBo.getRecievedChallanDate().toString(), ApplicationEditHelper.SQL_DATEFORMAT1,ApplicationEditHelper.FROM_DATEFORMAT));
			if(admApplnBo.getRecievedDate()!=null )
				adminAppTO.setRecievedDate(CommonUtil.ConvertStringToDateFormat(admApplnBo.getRecievedDate().toString(), ApplicationEditHelper.SQL_DATEFORMAT1,ApplicationEditHelper.FROM_DATEFORMAT));

			//addition for challan verification completed
			//raghu newly added
			adminAppTO.setIsDraftMode(admApplnBo.getIsDraftMode());
			adminAppTO.setCurrentPageName(admApplnBo.getCurrentPageName());
			adminAppTO.setIsDraftCancelled(admApplnBo.getIsDraftCancelled());
			if(admApplnBo.getStudentOnlineApplication()!=null){
				adminAppTO.setUniqueId(admApplnBo.getStudentOnlineApplication().getId());
			}
			
			


			adminAppTO.setIsApproved(admApplnBo.getIsApproved());
			adminAppTO.setIsLIG(admApplnBo.getIsLig());
			adminAppTO.setPersonalDataid(admApplnBo.getPersonalData().getId());
			adminAppTO.setIsInterviewSelected(admApplnBo.getIsInterviewSelected());

			adminAppTO.setTcNo(admApplnBo.getTcNo());
			adminAppTO.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getTcDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
			adminAppTO.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getMarkscardDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			
			if(admApplnBo.getDate()!= null){
				adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo.getDate()));
			}
			if(admApplnBo.getAdmissionDate()!= null){
				adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getAdmissionDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			}
			if(admApplnBo.getCourseChangeDate()!= null){
				adminAppTO.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getCourseChangeDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			}
			
			if(admApplnBo.getAmount()!=null)
			adminAppTO.setAmount(String.valueOf(admApplnBo.getAmount().doubleValue()));
			
			adminAppTO.setCandidatePrerequisiteMarks(admApplnBo.getCandidatePrerequisiteMarks());
			personalDataTO = copyPropertiesValue(admApplnBo.getPersonalData());
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
			
			courseTO = copyPropertiesValue(admApplnBo.getCourse(),admForm);
			adminAppTO.setCourse(courseTO);
			
			//raghu added newly
			admForm.setProgramId(new Integer(admApplnBo.getCourse().getProgram().getId()).toString());
			admForm.setProgramTypeId(new Integer(admApplnBo.getCourse().getProgram().getProgramType().getId()).toString());
			//raghu here write newly code for preferences
			Map<Integer, String> courseMap=CommonAjaxHandler.getInstance().getCourseByProgramTypeForOnlineNew(Integer.parseInt(admForm.getProgramTypeId()));
			admForm.setCourseMap(courseMap);
			 
			CourseTO courseTO1 = copyPropertiesValueSelected(admApplnBo.getCourseBySelectedCourseId());
			adminAppTO.setSelectedCourse(courseTO1);
			 CourseTO courseTO2 = copyPropertiesValueSelected(admApplnBo.getAdmittedCourseId());
				adminAppTO.setAdmittedCourse(courseTO2);
			
			String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
			boolean workExpNeed=false;
			if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
				workExpNeed=true;
			}
			workExpList=copyWorkExpValue(admApplnBo.getApplicantWorkExperiences(),workExpList,workExpNeed);
			adminAppTO.setWorkExpList(workExpList);

			//newly added selected course to admitted course
			if(adminAppTO.getAppliedYear()>2015){
				ednQualificationList = copyPropertiesValue(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getAdmittedCourse(),adminAppTO.getAppliedYear(),admForm);
				adminAppTO.setEdnQualificationList(ednQualificationList);
				
			}else{
				ednQualificationList = copyPropertiesValue(admApplnBo
				.getPersonalData().getEdnQualifications(), adminAppTO
				.getCourse(), adminAppTO.getAppliedYear(),admForm);
	
			}
			
			Iterator<EdnQualificationTO> itr=ednQualificationList.iterator();
			while(itr.hasNext()){
				
				EdnQualificationTO ednQualificationTO=(EdnQualificationTO) itr.next();
			if(ednQualificationTO.getDocName().equalsIgnoreCase("Class 12")){
				admsubList= copyPropertiesValuesofMark(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear(),admForm);
				admForm.setAdmsubMarkList(admsubList);
			}
			
			if(ednQualificationTO.getDocName().equalsIgnoreCase("DEG")){
				admsubList= copyPropertiesValuesofMark(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear(),admForm);
				admForm.setAdmsubMarkListUG(admsubList);
			}
			
			
			}

			preferenceTO = copyPropertiesValue(admApplnBo.getCandidatePreferences());
			adminAppTO.setPreference(preferenceTO);
			
			//raghu write for preference store
			//copyPropertiesValue(admApplnBo.getCandidatePreferences(),admForm);
			copyPropertiesValueNew(admApplnBo.getCandidatePreferences(),admForm);
			
			editDocuments = copyPropertiesEditDocValue(admApplnBo.getApplnDocs(),adminAppTO.getSelectedCourse().getId(),adminAppTO,admApplnBo.getAppliedYear());
			adminAppTO.setEditDocuments(editDocuments);
			
			prereqList=copyPrerequisiteDetails(admApplnBo.getCandidatePrerequisiteMarks());
			adminAppTO.setPrerequisiteTos(prereqList);
			
			if(admApplnBo.getStudentQualifyexamDetails()!=null && !admApplnBo.getStudentQualifyexamDetails().isEmpty()){
				adminAppTO.setOriginalQualDetails(admApplnBo.getStudentQualifyexamDetails());
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
				admForm.setMode(admApplnBo.getMode());
			}
			adminAppTO.setAdmStatus(admApplnBo.getAdmStatus());
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
			//added for student specialization prefered and adm appln additional info -Smitha
			if(admApplnBo.getStudentSpecializationPrefered()!=null && !admApplnBo.getStudentSpecializationPrefered().isEmpty()){
				adminAppTO.setStudentSpecializationPrefered(admApplnBo.getStudentSpecializationPrefered());
			}
			if(admApplnBo.getAdmapplnAdditionalInfo()!=null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
				adminAppTO.setAdmapplnAdditionalInfos(admApplnBo.getAdmapplnAdditionalInfo());
			}
			if(admApplnBo.getStudents() != null){
				for (Student student : admApplnBo.getStudents()) {
					adminAppTO.setStudentId(student.getId());
				}
			}
		}
	if(admForm.getProgramId()!=null && !admForm.getProgramId().isEmpty() && year>0){
	List<InterviewSelectionSchedule> InterviewDefinedList = txn.getInterviewSelectionScheduleByPgmId(Integer.parseInt(admForm.getProgramId()), year);
		if(InterviewDefinedList!=null && !InterviewDefinedList.isEmpty()){
			admForm.setIsInterviewSelectionSchedule("true");
				if(admForm.isOnlineApply()){
					getInterviewSelectionScheduleOnline(admForm,year);
				}else
				{
					getInterviewSelectionScheduleOffline(admForm, year);
				}
				
				List<InterviewCard> card1=txn.getGeneratedCardDetails(admForm.getApplicationNumber());
				if(card1!=null && !card1.isEmpty()){
					admForm.setEnableData("false");
				}
				else
				{
					admForm.setEnableData("true");
				}
				
				if(admApplnBo.getExamCenter()!= null && admApplnBo.getExamCenter().getCenter()!= null && !admApplnBo.getExamCenter().getCenter().isEmpty()){ 
					adminAppTO.setExamCenterName(admApplnBo.getExamCenter().getCenter());
					adminAppTO.setExamCenterId(admApplnBo.getExamCenter().getId());
					admForm.setInterviewVenue(String.valueOf(admApplnBo.getExamCenter().getId()));
					admForm.setTempVenuId(String.valueOf(admApplnBo.getExamCenter().getId()));
					admForm.setSelectedVenue(admApplnBo.getExamCenter().getCenter());
				}
				if(admApplnBo.getInterScheduleSelection()!=null && admApplnBo.getInterScheduleSelection().getId()>0){
					adminAppTO.setInterviewSelectionScheduleId(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
					admForm.setInterviewSelectionDate(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
					admForm.setSelectedDate(CommonUtil.formatSqlDate(admApplnBo.getInterScheduleSelection().getSelectionProcessDate().toString()));
					admForm.setTempSelectedDate(String.valueOf(admApplnBo.getInterScheduleSelection().getId()));
				}


		}else
		{
			admForm.setIsInterviewSelectionSchedule("false");
			admForm.setEnableData("false");
		}
	}else
	{
		admForm.setIsInterviewSelectionSchedule("false");
		admForm.setEnableData("false");
	}
			/* Code added by mary for interview selection schedule only---- ends*/

		log.info("exit copyPropertiesValue admappln" );
		return adminAppTO;
	}
	
	
	//at raghu
	public List<AdmApplnTO> copyProperties(List<AdmAppln> admApplnBos, int year, ApplicationEditForm admForm) throws Exception {
		log.info("enter copyPropertiesValue admappln" );
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<AdmApplnTO> adminAppTOs = null;
		PersonalDataTO personalDataTO = null;
		CourseTO courseTO = null;
		//Commented By Manu.List not be null initialized
		/*List<EdnQualificationTO> ednQualificationList = null;
		List<ApplicantWorkExperienceTO> workExpList = null;
		List<CandidatePrerequisiteMarksTO> prereqList = null;*/
		List<ApplicantWorkExperienceTO> workExpList = new ArrayList<ApplicantWorkExperienceTO>();
		List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
		List<CandidatePrerequisiteMarksTO> prereqList = new ArrayList<CandidatePrerequisiteMarksTO>();
		
		PreferenceTO preferenceTO = null;

		List<ApplnDocTO> editDocuments = null;

Iterator<AdmAppln> itr=admApplnBos.iterator();
while(itr.hasNext()){
	
	AdmAppln admApplnBo=(AdmAppln) itr.next() ;
		if (admApplnBo != null) {
			adminAppTOs = new ArrayList<AdmApplnTO>();
			AdmApplnTO adminAppTO=new AdmApplnTO();
			adminAppTO.setId(admApplnBo.getId());
			adminAppTO.setCreatedBy(admApplnBo.getCreatedBy());
			adminAppTO.setCreatedDate(admApplnBo.getCreatedDate());
			//added by vishnu
			adminAppTO.setAdmissionNumber(admApplnBo.getAdmissionNumber());
			adminAppTO.setIsFinalMeritApproved(admApplnBo.getIsFinalMeritApproved());
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
			//
			adminAppTO.setIsBypassed(admApplnBo.getIsBypassed());
			adminAppTO.setIsCancelled(admApplnBo.getIsCancelled());
			if(admApplnBo.getIsFreeShip()!=null && admApplnBo.getIsFreeShip()){
				adminAppTO.setIsFreeShip(true);
			}else{
				adminAppTO.setIsFreeShip(false);
			}
			// added for challan verification 
			adminAppTO.setIsChallanVerified(admApplnBo.getIsChallanVerified());
			
			
			//raghu added for challan and dd status
			adminAppTO.setIsDDRecieved(admApplnBo.getIsDDRecieved());
			adminAppTO.setIsChallanRecieved(admApplnBo.getIsChallanRecieved());
			adminAppTO.setRecievedDDNo(admApplnBo.getRecievedDDNo());
			adminAppTO.setRecievedChallanNo(admApplnBo.getRecievedChallanNo());
			if(admApplnBo.getRecievedChallanDate()!=null )
				adminAppTO.setRecievedChallanDate(CommonUtil.ConvertStringToDateFormat(admApplnBo.getRecievedChallanDate().toString(), ApplicationEditHelper.SQL_DATEFORMAT1,ApplicationEditHelper.FROM_DATEFORMAT));
			if(admApplnBo.getRecievedDate()!=null )
				adminAppTO.setRecievedDate(CommonUtil.ConvertStringToDateFormat(admApplnBo.getRecievedDate().toString(), ApplicationEditHelper.SQL_DATEFORMAT1,ApplicationEditHelper.FROM_DATEFORMAT));

			//addition for challan verification completed
			adminAppTO.setIsApproved(admApplnBo.getIsApproved());
			adminAppTO.setIsLIG(admApplnBo.getIsLig());
			adminAppTO.setPersonalDataid(admApplnBo.getPersonalData().getId());
			adminAppTO.setIsInterviewSelected(admApplnBo.getIsInterviewSelected());

			adminAppTO.setTcNo(admApplnBo.getTcNo());
			adminAppTO.setTcDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getTcDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			adminAppTO.setMarkscardNo(admApplnBo.getMarkscardNo());
			adminAppTO.setMarkscardDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getMarkscardDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			
			if(admApplnBo.getDate()!= null){
				adminAppTO.setChallanDate(CommonUtil.getStringDate(admApplnBo.getDate()));
			}
			if(admApplnBo.getAdmissionDate()!= null){
				adminAppTO.setAdmissionDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getAdmissionDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			}
			if(admApplnBo.getCourseChangeDate()!= null){
				adminAppTO.setCourseChangeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admApplnBo.getCourseChangeDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			}
			
			if(admApplnBo.getAmount()!=null)
			adminAppTO.setAmount(String.valueOf(admApplnBo.getAmount().doubleValue()));
			
			adminAppTO.setCandidatePrerequisiteMarks(admApplnBo.getCandidatePrerequisiteMarks());
			personalDataTO = copyPropertiesValue(admApplnBo.getPersonalData());
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
			
			courseTO = copyPropertiesValue(admApplnBo.getCourse(),admForm);
			adminAppTO.setCourse(courseTO);
			
			 CourseTO courseTO1 = copyPropertiesValueSelected(admApplnBo.getCourseBySelectedCourseId());
			adminAppTO.setSelectedCourse(courseTO1);
			 CourseTO courseTO2 = copyPropertiesValueSelected(admApplnBo.getAdmittedCourseId());
				adminAppTO.setAdmittedCourse(courseTO2);
			
			String workExpNeeded=adminAppTO.getCourse().getIsWorkExperienceRequired();
			boolean workExpNeed=false;
			if(workExpNeeded!=null && "Yes".equalsIgnoreCase(workExpNeeded)){
				workExpNeed=true;
			}
			workExpList=copyWorkExpValue(admApplnBo.getApplicantWorkExperiences(),workExpList,workExpNeed);
			adminAppTO.setWorkExpList(workExpList);

			ednQualificationList = copyPropertiesValue(admApplnBo.getPersonalData().getEdnQualifications(),adminAppTO.getSelectedCourse(),adminAppTO.getAppliedYear(),admForm);
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
				admForm.setMode(admApplnBo.getMode());
			}
			adminAppTO.setAdmStatus(admApplnBo.getAdmStatus());
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
			//added for student specialization prefered and adm appln additional info -Smitha
			if(admApplnBo.getStudentSpecializationPrefered()!=null && !admApplnBo.getStudentSpecializationPrefered().isEmpty()){
				adminAppTO.setStudentSpecializationPrefered(admApplnBo.getStudentSpecializationPrefered());
			}
			if(admApplnBo.getAdmapplnAdditionalInfo()!=null && !admApplnBo.getAdmapplnAdditionalInfo().isEmpty()){
				adminAppTO.setAdmapplnAdditionalInfos(admApplnBo.getAdmapplnAdditionalInfo());
			}
			if(admApplnBo.getStudents() != null){
				for (Student student : admApplnBo.getStudents()) {
					adminAppTO.setStudentId(student.getId());
				}
			}
			
			adminAppTOs.add(adminAppTO);
		}
}
	

		log.info("exit copyPropertiesValue admappln" );
		return adminAppTOs;
	}
	
	/**
	 * @param candidatePrerequisiteMarks
	 * @return
	 */
	private List<CandidatePrerequisiteMarksTO> copyPrerequisiteDetails(Set<CandidatePrerequisiteMarks> candidatePrerequisiteMarks) {
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
	 * @param applnDocs
	 * @param id
	 * @param adminAppTO
	 * @param appliedYear
	 * @return
	 */
	private List<ApplnDocTO> copyPropertiesEditDocValue(Set<ApplnDoc> docUploadSet, int courseId, AdmApplnTO adminAppTO,Integer appliedYear) throws Exception {
		log.info("enter copyPropertiesEditDocValue" );
		List<ApplnDocTO> documentsList = new ArrayList<ApplnDocTO>();
		ApplnDocTO applnDocTO = null;
		
	
			ApplicationEditHandler handler = ApplicationEditHandler.getInstance();
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
				applnDocTO.setSubmitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(applnDocBO.getSubmissionDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
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
					applnDocTO.setDocName(ApplicationEditHelper.PHOTO);
					applnDocTO.setPrintName(ApplicationEditHelper.PHOTO);
					if(applnDocBO.getName()!=null && ApplicationEditHelper.PHOTO.equalsIgnoreCase(applnDocBO.getName()))
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
//							to.setIsHardCopySubmitted(bo.getIsHardCopySubmited());
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
	 * @param candidatePreferences
	 * @return
	 */
	private PreferenceTO copyPropertiesValue(Set<CandidatePreference> preferencesSet) {
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
	 * @param ednQualifications
	 * @param selectedCourse
	 * @param appliedYear
	 * @return
	 */
	private List<EdnQualificationTO> copyPropertiesValue(Set<EdnQualification> qualificationSet, CourseTO selectedCourse,Integer appliedYear,ApplicationEditForm admForm) throws Exception {
		log.info("enter copyPropertiesValue ednqualification" );
		List<EdnQualificationTO> ednQualificationList = new ArrayList<EdnQualificationTO>();
		EdnQualificationTO ednQualificationTO = null;
		AdmSubjectMarkForRankTO admSubjectMarkForRankTO=null;
		IApplicationEditTransaction txn=  ApplicationEditTransactionimpl.getInstance();
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
					if(ednQualificationBO.getDocTypeExams().getDocType()!=null){
						ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocTypeExams().getDocType().getDisplayOrder());
					}
				}
				if(ednQualificationBO.getDocChecklist()!=null && ednQualificationBO.getDocChecklist().getDocType()!=null){
					ApplicationEditHandler handler= ApplicationEditHandler.getInstance();
					List<DocTypeExamsTO> examtos=handler.getDocExamsByID(ednQualificationBO.getDocChecklist().getDocType().getId());
					ednQualificationTO.setExamTos(examtos);
					if(examtos!=null && !examtos.isEmpty())
						ednQualificationTO.setExamRequired(true);
					ednQualificationTO.setDisplayOrder(ednQualificationBO.getDocChecklist().getDocType().getDisplayOrder());
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
//							Set<ApplicantMarkDetailsTO> markdetails= new TreeSet<ApplicantMarkDetailsTO>(new SemesterComparator());
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
																
								//raghu write newly for admission edit
								if(appliedYear>2015){
									convertDetailMarkBOtoTO(detailMarkBO, markTO, ednQualificationBO);
								}else{
									convertDetailMarkBOtoTO(detailMarkBO, markTO);
								}
								
								ednQualificationTO.setDetailmark(markTO);
							}
						}else{
							ednQualificationTO.setDetailmark(null);
						}
					}
					
				}
				if(ednQualificationBO.getUniversityOthers()!= null && !ednQualificationBO.getUniversityOthers().isEmpty()){
					ednQualificationTO.setUniversityId(ApplicationEditHelper.OTHER);
					ednQualificationTO.setUniversityOthers(ednQualificationBO.getUniversityOthers());
					ednQualificationTO.setUniversityName(ednQualificationBO.getUniversityOthers());
				}else if(ednQualificationBO.getUniversity()!= null){
					ednQualificationTO.setUniversityId(String.valueOf(ednQualificationBO.getUniversity().getId()));
					ednQualificationTO.setUniversityName(ednQualificationBO.getUniversity().getName());
				}
				if(ednQualificationBO.getInstitutionNameOthers()!= null && !ednQualificationBO.getInstitutionNameOthers().isEmpty()){
					ednQualificationTO.setInstitutionId(ApplicationEditHelper.OTHER);
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
				float obtainmrk=0.0f;
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
				}
				if(ednQualificationBO.getNoOfAttempts()!=null)
				ednQualificationTO.setNoOfAttempts(ednQualificationBO.getNoOfAttempts());
				
				if( UniversityHandler.getInstance().getUniversity() != null){
					List<UniversityTO> universityList = UniversityHandler.getInstance().getUniversity();
					if( universityList != null  && ednQualificationTO.getUniversityId()!=null && !ednQualificationTO.getUniversityId().equalsIgnoreCase(ApplicationEditHelper.OTHER)){
						ednQualificationTO.setUniversityList(universityList);
						if(ednQualificationTO.getInstitutionId()!=null && !ednQualificationTO.getInstitutionId().equalsIgnoreCase(ApplicationEditHelper.OTHER)){
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
				
				//raghu
				if(ednQualificationBO.getUgPattern()!=null && !ednQualificationBO.getUgPattern().equalsIgnoreCase("")){
					ednQualificationTO.setUgPattern(ednQualificationBO.getUgPattern());
					admForm.setPatternofStudy(ednQualificationBO.getUgPattern());
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
	 * @param extraList
	 * @return
	 */
	private List<EdnQualificationTO> prepareQualificationsFromExamBos(List<DocChecklist> exambos) throws Exception {
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
							 subjectMap = ApplicationEditHandler.getInstance().getDetailedSubjectsByCourse(String.valueOf(examType.getCourse().getId()));
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
	 * @param detailMarkBO
	 * @param markTO
	 */
	private void convertDetailMarkBOtoTO(CandidateMarks detailMarkBO,CandidateMarkTO markTO) {
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

			//raghu
			if(detailMarkBO.getSubject1Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject1Credit().toString()))
				markTO.setSubject1Credit(detailMarkBO.getSubject1Credit().toString());
			if(detailMarkBO.getSubject2Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject2Credit().toString()))
				markTO.setSubject2Credit(detailMarkBO.getSubject2Credit().toString());
			if(detailMarkBO.getSubject3Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject3Credit().toString()))
				markTO.setSubject3Credit(detailMarkBO.getSubject3Credit().toString());
			if(detailMarkBO.getSubject4Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject4Credit().toString()))
				markTO.setSubject4Credit(detailMarkBO.getSubject4Credit().toString());
			if(detailMarkBO.getSubject5Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject5Credit().toString()))
				markTO.setSubject5Credit(detailMarkBO.getSubject5Credit().toString());
			if(detailMarkBO.getSubject6Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject6Credit().toString()))
				markTO.setSubject6Credit(detailMarkBO.getSubject6Credit().toString());
			if(detailMarkBO.getSubject7Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject7Credit().toString()))
				markTO.setSubject7Credit(detailMarkBO.getSubject7Credit().toString());
			if(detailMarkBO.getSubject15Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject15Credit().toString()))
				markTO.setSubject15Credit(detailMarkBO.getSubject15Credit().toString());
			if(detailMarkBO.getSubject16Credit()!=null  && CommonUtil.isValidDecimal(detailMarkBO.getSubject16Credit().toString()))
				markTO.setSubject16Credit(detailMarkBO.getSubject16Credit().toString());
			
			
			if(detailMarkBO.getSubject1TotalMarks()!=null && detailMarkBO.getSubject1TotalMarks().floatValue()!=0)
				markTO.setSubject1TotalMarks(String.valueOf(detailMarkBO.getSubject1TotalMarks().floatValue()));
			if(detailMarkBO.getSubject2TotalMarks()!=null && detailMarkBO.getSubject2TotalMarks().floatValue()!=0)
			markTO.setSubject2TotalMarks(String.valueOf(detailMarkBO.getSubject2TotalMarks().floatValue()));
			if(detailMarkBO.getSubject3TotalMarks()!=null && detailMarkBO.getSubject3TotalMarks().floatValue()!=0)
			markTO.setSubject3TotalMarks(String.valueOf(detailMarkBO.getSubject3TotalMarks().floatValue()));
			if(detailMarkBO.getSubject4TotalMarks()!=null && detailMarkBO.getSubject4TotalMarks().floatValue()!=0)
			markTO.setSubject4TotalMarks(String.valueOf(detailMarkBO.getSubject4TotalMarks().floatValue()));
			if(detailMarkBO.getSubject5TotalMarks()!=null && detailMarkBO.getSubject5TotalMarks().floatValue()!=0)
			markTO.setSubject5TotalMarks(String.valueOf(detailMarkBO.getSubject5TotalMarks().floatValue()));
			if(detailMarkBO.getSubject6TotalMarks()!=null && detailMarkBO.getSubject6TotalMarks().floatValue()!=0)
			markTO.setSubject6TotalMarks(String.valueOf(detailMarkBO.getSubject6TotalMarks().floatValue()));
			if(detailMarkBO.getSubject7TotalMarks()!=null && detailMarkBO.getSubject7TotalMarks().floatValue()!=0)
			markTO.setSubject7TotalMarks(String.valueOf(detailMarkBO.getSubject7TotalMarks().floatValue()));
			if(detailMarkBO.getSubject8TotalMarks()!=null && detailMarkBO.getSubject8TotalMarks().floatValue()!=0)
			markTO.setSubject8TotalMarks(String.valueOf(detailMarkBO.getSubject8TotalMarks().floatValue()));
			if(detailMarkBO.getSubject9TotalMarks()!=null && detailMarkBO.getSubject9TotalMarks().floatValue()!=0)
			markTO.setSubject9TotalMarks(String.valueOf(detailMarkBO.getSubject9TotalMarks().floatValue()));
			if(detailMarkBO.getSubject10TotalMarks()!=null && detailMarkBO.getSubject10TotalMarks().floatValue()!=0)
			markTO.setSubject10TotalMarks(String.valueOf(detailMarkBO.getSubject10TotalMarks().floatValue()));
			if(detailMarkBO.getSubject11TotalMarks()!=null && detailMarkBO.getSubject11TotalMarks().floatValue()!=0)
			markTO.setSubject11TotalMarks(String.valueOf(detailMarkBO.getSubject11TotalMarks().floatValue()));
			if(detailMarkBO.getSubject12TotalMarks()!=null && detailMarkBO.getSubject12TotalMarks().floatValue()!=0)
			markTO.setSubject12TotalMarks(String.valueOf(detailMarkBO.getSubject12TotalMarks().floatValue()));
			if(detailMarkBO.getSubject13TotalMarks()!=null && detailMarkBO.getSubject13TotalMarks().floatValue()!=0)
			markTO.setSubject13TotalMarks(String.valueOf(detailMarkBO.getSubject13TotalMarks().floatValue()));
			if(detailMarkBO.getSubject14TotalMarks()!=null && detailMarkBO.getSubject14TotalMarks().floatValue()!=0)
			markTO.setSubject14TotalMarks(String.valueOf(detailMarkBO.getSubject14TotalMarks().floatValue()));
			if(detailMarkBO.getSubject15TotalMarks()!=null && detailMarkBO.getSubject15TotalMarks().floatValue()!=0)
			markTO.setSubject15TotalMarks(String.valueOf(detailMarkBO.getSubject15TotalMarks().floatValue()));
			if(detailMarkBO.getSubject16TotalMarks()!=null && detailMarkBO.getSubject16TotalMarks().floatValue()!=0)
			markTO.setSubject16TotalMarks(String.valueOf(detailMarkBO.getSubject16TotalMarks().floatValue()));
			if(detailMarkBO.getSubject17TotalMarks()!=null && detailMarkBO.getSubject17TotalMarks().floatValue()!=0)
			markTO.setSubject17TotalMarks(String.valueOf(detailMarkBO.getSubject17TotalMarks().floatValue()));
			if(detailMarkBO.getSubject18TotalMarks()!=null && detailMarkBO.getSubject18TotalMarks().floatValue()!=0)
			markTO.setSubject18TotalMarks(String.valueOf(detailMarkBO.getSubject18TotalMarks().floatValue()));
			if(detailMarkBO.getSubject19TotalMarks()!=null && detailMarkBO.getSubject19TotalMarks().floatValue()!=0)
			markTO.setSubject19TotalMarks(String.valueOf(detailMarkBO.getSubject19TotalMarks().floatValue()));
			if(detailMarkBO.getSubject20TotalMarks()!=null && detailMarkBO.getSubject20TotalMarks().floatValue()!=0)
			markTO.setSubject20TotalMarks(String.valueOf(detailMarkBO.getSubject20TotalMarks().floatValue()));
			
			if(detailMarkBO.getSubject1ObtainedMarks()!=null && detailMarkBO.getSubject1ObtainedMarks().floatValue()!=0)
			markTO.setSubject1ObtainedMarks(String.valueOf(detailMarkBO.getSubject1ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject2ObtainedMarks()!=null && detailMarkBO.getSubject2ObtainedMarks().floatValue()!=0)
			markTO.setSubject2ObtainedMarks(String.valueOf(	detailMarkBO.getSubject2ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject3ObtainedMarks()!=null && detailMarkBO.getSubject3ObtainedMarks().floatValue()!=0)
			markTO.setSubject3ObtainedMarks(String.valueOf(detailMarkBO.getSubject3ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject4ObtainedMarks()!=null && detailMarkBO.getSubject4ObtainedMarks().floatValue()!=0)
			markTO.setSubject4ObtainedMarks(String.valueOf(detailMarkBO.getSubject4ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject5ObtainedMarks()!=null && detailMarkBO.getSubject5ObtainedMarks().floatValue()!=0)
			markTO.setSubject5ObtainedMarks(String.valueOf(detailMarkBO.getSubject5ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject6ObtainedMarks()!=null && detailMarkBO.getSubject6ObtainedMarks().floatValue()!=0)
			markTO.setSubject6ObtainedMarks(String.valueOf(detailMarkBO.getSubject6ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject7ObtainedMarks()!=null && detailMarkBO.getSubject7ObtainedMarks().floatValue()!=0)
			markTO.setSubject7ObtainedMarks(String.valueOf(detailMarkBO.getSubject7ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject8ObtainedMarks()!=null && detailMarkBO.getSubject8ObtainedMarks().floatValue()!=0)
			markTO.setSubject8ObtainedMarks(String.valueOf(detailMarkBO.getSubject8ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject9ObtainedMarks()!=null && detailMarkBO.getSubject9ObtainedMarks().floatValue()!=0)
			markTO.setSubject9ObtainedMarks(String.valueOf(detailMarkBO.getSubject9ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject10ObtainedMarks()!=null && detailMarkBO.getSubject10ObtainedMarks().floatValue()!=0)
			markTO.setSubject10ObtainedMarks(String.valueOf(detailMarkBO.getSubject10ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject11ObtainedMarks()!=null && detailMarkBO.getSubject11ObtainedMarks().floatValue()!=0)
			markTO.setSubject11ObtainedMarks(String.valueOf(detailMarkBO.getSubject11ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject12ObtainedMarks()!=null && detailMarkBO.getSubject12ObtainedMarks().floatValue()!=0)
			markTO.setSubject12ObtainedMarks(String.valueOf(detailMarkBO.getSubject12ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject13ObtainedMarks()!=null && detailMarkBO.getSubject13ObtainedMarks().floatValue()!=0)
			markTO.setSubject13ObtainedMarks(String.valueOf(detailMarkBO.getSubject13ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject14ObtainedMarks()!=null && detailMarkBO.getSubject14ObtainedMarks().floatValue()!=0)
			markTO.setSubject14ObtainedMarks(String.valueOf(detailMarkBO.getSubject14ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject15ObtainedMarks()!=null && detailMarkBO.getSubject15ObtainedMarks().floatValue()!=0)
			markTO.setSubject15ObtainedMarks(String.valueOf(detailMarkBO.getSubject15ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject16ObtainedMarks()!=null && detailMarkBO.getSubject16ObtainedMarks().floatValue()!=0)
			markTO.setSubject16ObtainedMarks(String.valueOf(detailMarkBO.getSubject16ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject17ObtainedMarks()!=null && detailMarkBO.getSubject17ObtainedMarks().floatValue()!=0)
			markTO.setSubject17ObtainedMarks(String.valueOf(detailMarkBO.getSubject17ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject18ObtainedMarks()!=null && detailMarkBO.getSubject18ObtainedMarks().floatValue()!=0)
			markTO.setSubject18ObtainedMarks(String.valueOf(detailMarkBO.getSubject18ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject19ObtainedMarks()!=null && detailMarkBO.getSubject19ObtainedMarks().floatValue()!=0)
			markTO.setSubject19ObtainedMarks(String.valueOf(detailMarkBO.getSubject19ObtainedMarks().floatValue()));
			if(detailMarkBO.getSubject20ObtainedMarks()!=null && detailMarkBO.getSubject20ObtainedMarks().floatValue()!=0)
			markTO.setSubject20ObtainedMarks(String.valueOf(detailMarkBO.getSubject20ObtainedMarks().floatValue()));
			if(detailMarkBO.getTotalMarks()!=null && detailMarkBO.getTotalMarks().floatValue()!=0)
			markTO.setTotalMarks(String.valueOf(detailMarkBO.getTotalMarks().floatValue()));
			if(detailMarkBO.getTotalObtainedMarks()!=null && detailMarkBO.getTotalObtainedMarks().floatValue()!=0)
			markTO.setTotalObtainedMarks(String.valueOf(detailMarkBO.getTotalObtainedMarks().floatValue()));
		}
		log.info("exit convertDetailMarkBOtoTO" );
	}
	/**
	 * @param detailMarkBO
	 * @param markTO
	 * @param lastExam
	 */
	private void convertApplicantMarkBOtoTO(ApplicantMarksDetails detailMarkBO,ApplicantMarkDetailsTO markTO, boolean lastExam) {
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
	 * @param applicantWorkExperiences
	 * @param workExpList
	 * @param workExpNeed
	 * @return
	 */
	private List<ApplicantWorkExperienceTO> copyWorkExpValue(
			Set<ApplicantWorkExperience> applicantWorkExperiences,
			List<ApplicantWorkExperienceTO> workExpList, boolean workExpNeed) {
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
						expTo.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getFromDate()),ApplicationEditHelper.SQL_DATEFORMAT, ApplicationEditHelper.FROM_DATEFORMAT));
					if (workExp.getToDate() != null)
						expTo.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(workExp.getToDate()),ApplicationEditHelper.SQL_DATEFORMAT, ApplicationEditHelper.FROM_DATEFORMAT));
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
	 * @param course
	 * @return
	 */
	private CourseTO copyPropertiesValue(Course course, ApplicationEditForm admForm) {
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
				admForm.setProgramId(String.valueOf(course.getProgram().getId()));
				if(course.getProgram().getAcademicYear()!=null)
				admForm.setProgramYear(String.valueOf(course.getProgram().getAcademicYear()));
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
	private CourseTO copyPropertiesValueSelected(Course course) {
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
				//admForm.setProgramId(String.valueOf(course.getProgram().getId()));
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
	 * @param applicantTransferDetailses
	 * @param adminAppTO
	 */
	private void copyTransferDetails(Set<ApplicantTransferDetails> applicantTransferDetailses,AdmApplnTO adminAppTO) {
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
	private void copyLateralDetails(Set<ApplicantLateralDetails> applicantLateralDetailses,AdmApplnTO adminAppTO) {
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
	private void copyEntranceValue(Set<CandidateEntranceDetails> candidateEntranceDetailses,AdmApplnTO adminAppTO) {
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
	 * @param personalDataBO
	 * @return personalDataTO
	 */
	public PersonalDataTO copyPropertiesValue(PersonalData personalData) {
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
			if(personalData.getNccgrade()!=null){
				personalDataTO.setNccgrades(personalData.getNccgrade());
			}
			if(personalData.getNssgrade()!=null){
				personalDataTO.setNssgrades(personalData.getNssgrade());
			}
			if(personalData.getIsSportsPerson()!= null)
				personalDataTO.setSportsPerson(personalData.getIsSportsPerson());
			if(personalData.getHandicappedDescription()!= null)
				personalDataTO.setHadnicappedDescription(personalData.getHandicappedDescription());
			if(personalData.getSportsPersonDescription()!= null)
			    personalDataTO.setSportsDescription(personalData.getSportsPersonDescription());
			//basim
			personalDataTO.setSports(personalData.getSports());
			personalDataTO.setArts(personalData.getArts());
			personalDataTO.setSportsParticipate(personalData.getSportsParticipate());
			personalDataTO.setArtsParticipate(personalData.getArtsParticipate());
			personalDataTO.setCurrentStreet(personalData.getCurrentStreet());
			personalDataTO.setPermanentStreet(personalData.getPermanentStreet());
			
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
				personalDataTO.setBirthState(ApplicationEditHelper.OTHER);
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
				personalDataTO.setReligionId(ApplicationEditHelper.OTHER);
				personalDataTO.setReligionOthers(personalData.getReligionOthers());
				personalDataTO.setReligionName(personalData.getReligionOthers());
			} else if (personalData.getReligion() != null) {
				personalDataTO.setReligionName(personalData.getReligion().getName());
				personalDataTO.setReligionId(String.valueOf(personalData.getReligion().getId()));
			}
			if( personalData.getReligionSectionOthers()!=null && !personalData.getReligionSectionOthers().isEmpty()){
				personalDataTO.setSubReligionId(ApplicationEditHelper.OTHER);
				personalDataTO.setReligionSectionOthers(personalData.getReligionSectionOthers());
				personalDataTO.setSubregligionName(personalData.getReligionSectionOthers());
			}else if (personalData.getReligionSection() != null) {
				personalDataTO.setSubregligionName(personalData.getReligionSection().getName());
				personalDataTO.setSubReligionId(String.valueOf(personalData.getReligionSection().getId()));
			}
			if (personalData.getCasteOthers() != null && !personalData.getCasteOthers().isEmpty()) {
				personalDataTO.setCasteCategory(personalData.getCasteOthers());
				personalDataTO.setCasteOthers(personalData.getCasteOthers());
				personalDataTO.setCasteId(ApplicationEditHelper.OTHER);
			}else if (personalData.getCaste() != null) {
				personalDataTO.setCasteCategory(personalData.getCaste().getName());
				personalDataTO.setCasteId(String.valueOf(personalData.getCaste().getId()));
			}
			
			
			/*if(personalData.getDiocese()!=null){
				personalDataTO.setDioceseName(personalData.getDiocese().getName());
				personalDataTO.setDioceseId(String.valueOf(personalData.getDiocese().getId()));
			}
			
			
			if(personalData.getParish()!=null){
				personalDataTO.setParishName(personalData.getParish().getName());
				personalDataTO.setParishId(String.valueOf(personalData.getParish().getId()));
			}
				
			
			
			if (personalData.getParishOthers()!= null && !personalData.getParishOthers().isEmpty()){
				
				personalDataTO.setParishOthers(personalData.getParishOthers());
				personalDataTO.setParishName(personalData.getParishOthers());
				personalDataTO.setParishId(ApplicationEditHelper.OTHER);
				
			}	else if (personalData.getParish() != null) {
				
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
			
			if(personalData.getUgcourse()!= null){
				personalDataTO.setUgcourse(new Integer(personalData.getUgcourse().getId()).toString());
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
			personalDataTO.setLandlineNo(personalData.getPhNo1() + " "+ personalData.getPhNo2() + " " + personalData.getPhNo3());
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
				personalDataTO.setResidentPermitDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(personalData.getResidentPermitDate()), ApplicationEditHelper.SQL_DATEFORMAT,ApplicationEditHelper.FROM_DATEFORMAT));
			}
			
			
			
			//raghu
			
			if (personalData.getPermanentAddressDistrcictOthers()!= null && !personalData.getPermanentAddressDistrcictOthers().isEmpty()){
				personalDataTO.setPermanentDistricName(personalData.getPermanentAddressDistrcictOthers());
				personalDataTO.setPermanentAddressDistrictOthers(personalData.getPermanentAddressDistrcictOthers());
				personalDataTO.setPermanentDistricId(ApplicationEditHelper.OTHER);
			}
			else if (personalData.getStateByParentAddressDistrictId() != null) {
				personalDataTO.setPermanentDistricName(personalData.getStateByParentAddressDistrictId().getName());
				personalDataTO.setPermanentDistricId(String.valueOf(personalData.getStateByParentAddressDistrictId().getId()));
			}
			
			//raghu added newly
			
			
			 if(personalData.getStream()!=null)
					personalDataTO.setStream(personalData.getStream().getId()+"");
				
			 
			 //if(personalData.getFatherIncome1()!=null && !StringUtils.isEmpty(personalData.getFatherIncome1())){
				// personalDataTO.setFatherIncome1(personalData.getFatherIncome1());
	 			//}
	 			
			 if(personalData.getFatherMobile()!= null)
				personalDataTO.setFatherMobile(personalData.getFatherMobile());
			if(personalData.getMotherMobile()!= null)
				personalDataTO.setMotherMobile(personalData.getMotherMobile());

			 if(personalData.getNoofenglishCoreCourses()!=null)	
				 personalDataTO.setNoofenglishCoreCourses(personalData.getNoofenglishCoreCourses());
			
			personalDataTO.setPermanentAddressLine1(personalData.getPermanentAddressLine1());
			personalDataTO.setPermanentAddressLine2(personalData.getPermanentAddressLine2());
			if (personalData.getCityByPermanentAddressCityId() != null) {
				personalDataTO.setPermanentCityName(personalData.getCityByPermanentAddressCityId());
			}
			if (personalData.getPermanentAddressStateOthers()!= null && !personalData.getPermanentAddressStateOthers().isEmpty()){
				personalDataTO.setPermanentStateName(personalData.getPermanentAddressStateOthers());
				personalDataTO.setPermanentAddressStateOthers(personalData.getPermanentAddressStateOthers());
				personalDataTO.setPermanentStateId(ApplicationEditHelper.OTHER);
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
			
			
			//raghu
			
			if (personalData.getCurrenttAddressDistrcictOthers()!= null && !personalData.getCurrenttAddressDistrcictOthers().isEmpty()){
				personalDataTO.setCurrentDistricName(personalData.getCurrenttAddressDistrcictOthers());
				personalDataTO.setCurrentAddressDistrictOthers(personalData.getCurrenttAddressDistrcictOthers());
				personalDataTO.setCurrentDistricId(ApplicationEditHelper.OTHER);
			}
			else if (personalData.getStateByCurrentAddressDistrictId() != null) {
				personalDataTO.setCurrentDistricName(personalData.getStateByCurrentAddressDistrictId().getName());
				personalDataTO.setCurrentDistricId(String.valueOf(personalData.getStateByCurrentAddressDistrictId().getId()));
			}
			
			
			personalDataTO.setCurrentAddressLine1(personalData.getCurrentAddressLine1());
			personalDataTO.setCurrentAddressLine2(personalData.getCurrentAddressLine2());
			if (personalData.getCityByCurrentAddressCityId() != null) {
				personalDataTO.setCurrentCityName(personalData.getCityByCurrentAddressCityId());
			}
			if (personalData.getCurrentAddressStateOthers()!= null && !personalData.getCurrentAddressStateOthers().isEmpty()){
				personalDataTO.setCurrentStateName(personalData.getCurrentAddressStateOthers());
				personalDataTO.setCurrentAddressStateOthers(personalData.getCurrentAddressStateOthers());
				personalDataTO.setCurrentStateId(ApplicationEditHelper.OTHER);
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
				personalDataTO.setParentStateId(ApplicationEditHelper.OTHER);
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
				personalDataTO.setStateByGuardianAddressStateId(ApplicationEditHelper.OTHER);
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
			if(personalData.getRecommentedBy()!=null)
				personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
			if(personalData.getRecommendedBy()!=null)
				personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
			
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
			personalDataTO.setIsmgquota(personalData.getIsmgquota());
			if(personalData.getIsmgquota()!=null && personalData.getIsmgquota()) {
				/*personalDataTO.setRecommentedBy(personalData.getRecommentedBy());
				personalDataTO.setRecommentedPersonDesignation(personalData.getRecommentedPersonDesignation());
				personalDataTO.setRecommentedPersonMobile(personalData.getRecommentedPersonMobile());*/
				personalDataTO.setRecommendDeatails(personalData.getRecommendedBy());
			}
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
	/**
	 * @param examBos
	 * @return
	 * @throws Exception
	 */
	public List<DocTypeExamsTO> convertDocExamBosToTOS(List<DocTypeExams> examBos) throws Exception {
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
	/**
	 * @param checklistDocs
	 * @param appliedYear
	 * @return
	 */
	public List<ApplnDocTO> createDocUploadListForCourse(List<DocChecklist> listofdocs, Integer appliedYear) {
		log.info("enter createDocUploadListForCourse" );
		List<ApplnDocTO> doclist= new ArrayList<ApplnDocTO>();
		
		 if(listofdocs!=null && ! listofdocs.isEmpty()){
			 Iterator<DocChecklist> boitr=listofdocs.iterator();
				 while (boitr.hasNext()) {
					DocChecklist docChecklist = (DocChecklist) boitr.next();
					
						  if( (docChecklist.getNeedToProduce()==true && docChecklist.getIsActive() && docChecklist.getYear()==appliedYear ))
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
		 photoTo.setName(ApplicationEditHelper.PHOTO);
		 photoTo.setDocName(ApplicationEditHelper.PHOTO);
		 photoTo.setPrintName(ApplicationEditHelper.PHOTO);
		 doclist.add(photoTo);
			
		 log.info("exit createDocUploadListForCourse" );
		return doclist;
	}
	/**
	 * @param examCenter
	 * @return
	 */
	public List<ExamCenterTO> convertexamCenterBOToTO(List<ExamCenter> examcenterList) throws Exception{
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
	/**
	 * @param nationBOs
	 * @return
	 */
	public List<NationalityTO> convertNationalityBOtoTO(List<Nationality> nationBOs) {
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
	 * @param residentbos
	 * @return
	 */
	public List<ResidentCategoryTO> convertResidentBOToTO(List<ResidentCategory> residentbos) {
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
	 * @param incomebos
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
	 * @param appliedYear
	 * @param id
	 * @return
	 */
	public Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear,int id) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		return txn.getSubjectGroupByYearAndCourse(appliedYear,id);
	}
	/**
	 * @param applicantDetail
	 * @param admForm
	 * @param isPresidance
	 * @return
	 */
	public AdmAppln getApplicantBO(AdmApplnTO applicantDetail,ApplicationEditForm admForm, boolean isPresidance) throws Exception {
		log.info("enter getApplicantBO" );
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
			if(applicantDetail.getAmount()!=null && !StringUtils.isEmpty(applicantDetail.getAmount().trim()))
				appBO.setAmount(new BigDecimal(applicantDetail.getAmount()));
			if(applicantDetail.getChallanDate()!= null){
				appBO.setDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getChallanDate()));
			}
			if(applicantDetail.getDdDrawnOn()!=null && !applicantDetail.getDdDrawnOn().isEmpty())
				appBO.setDdDrawnOn(applicantDetail.getDdDrawnOn());
			if(applicantDetail.getDdIssuingBank()!=null && !applicantDetail.getDdIssuingBank().isEmpty())
				appBO.setDdIssuingBank(applicantDetail.getDdIssuingBank());
			appBO.setRemarks(applicantDetail.getRemark());
			appBO.setIsCancelled(applicantDetail.getIsCancelled());
			appBO.setIsFreeShip(applicantDetail.getIsFreeShip());
			//added for challan verification
			appBO.setIsChallanVerified(applicantDetail.getIsChallanVerified());
			
			//raghu added for challan and dd status
			appBO.setIsDDRecieved(applicantDetail.getIsDDRecieved());
			appBO.setIsChallanRecieved(applicantDetail.getIsChallanRecieved());
			appBO.setRecievedDDNo(applicantDetail.getRecievedDDNo());
			appBO.setRecievedChallanNo(applicantDetail.getRecievedChallanNo());
			if(applicantDetail.getRecievedChallanDate()!=null )
				appBO.setRecievedChallanDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getRecievedChallanDate()));
			if(applicantDetail.getRecievedDate()!=null )
				appBO.setRecievedDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getRecievedDate()));
			
			//addition for challan verification completed
			//raghu newly added
			appBO.setIsDraftMode(applicantDetail.getIsDraftMode());
			appBO.setCurrentPageName(applicantDetail.getCurrentPageName());
			appBO.setIsDraftCancelled(applicantDetail.getIsDraftCancelled());
			if(applicantDetail.getUniqueId()!=0){
				StudentOnlineApplication st=new StudentOnlineApplication();
				st.setId(applicantDetail.getUniqueId());
				appBO.setStudentOnlineApplication(st);
			}
			
			appBO.setIsLig(applicantDetail.getIsLIG());
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
			if(admForm.getInterviewSelectionDate()!= null && !admForm.getInterviewSelectionDate().isEmpty()){
				InterviewSelectionSchedule iss =new InterviewSelectionSchedule();
				iss.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
				appBO.setInterScheduleSelection(iss);
			}
			
			
			appBO.setTcNo(applicantDetail.getTcNo());
			if(applicantDetail.getTcDate()!=null && !StringUtils.isEmpty(applicantDetail.getTcDate()) && CommonUtil.isValidDate(applicantDetail.getTcDate()))
			appBO.setTcDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getTcDate()));
			appBO.setMarkscardNo(applicantDetail.getMarkscardNo());
			if(applicantDetail.getMarkscardDate()!=null && !StringUtils.isEmpty(applicantDetail.getMarkscardDate()) && CommonUtil.isValidDate(applicantDetail.getMarkscardDate()))
			appBO.setMarkscardDate(CommonUtil.ConvertStringToSQLDate(applicantDetail.getMarkscardDate()));
			
			
			
			CourseTO crsto=applicantDetail.getCourse();
			
			if (crsto!=null) {
				Course crs=new Course();
				//ProgramType programType = new ProgramType();
				//programType.setId(crsto.getProgramTypeId());
				
				//Program program = new Program();
				//program.setProgramType(programType);
				//program.setId(crsto.getProgramId());
				//crs.setProgram(program);
				//raghu added newly
				if(applicantDetail.getCourseChangeDate()!=null){
					crs.setId(crsto.getId());
				}else{
					crs.setId(Integer.parseInt(admForm.getCourseId()));
				}
				
				appBO.setCourse(crs);
				
			}
			CourseTO crsto1=applicantDetail.getSelectedCourse();
			
			if (crsto1!=null) {
				Course crs=new Course();
				//raghu added newly
				if(applicantDetail.getCourseChangeDate()!=null){
					crs.setId(crsto1.getId());
				}else{
					crs.setId(Integer.parseInt(admForm.getCourseId()));
				}
					
				
					
				
				
				
				appBO.setCourseBySelectedCourseId(crs);
			}
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
			//
			appBO.setIsBypassed(applicantDetail.getIsBypassed());
			appBO.setIsInterviewSelected(applicantDetail.getIsInterviewSelected());
			appBO.setCandidatePrerequisiteMarks(applicantDetail.getCandidatePrerequisiteMarks());
			Iterator<CandidatePrerequisiteMarks> candidatePreRequisiteMarksItr=appBO.getCandidatePrerequisiteMarks().iterator();
			while (candidatePreRequisiteMarksItr.hasNext()) {
				CandidatePrerequisiteMarks preRequisiteMarks = (CandidatePrerequisiteMarks) candidatePreRequisiteMarksItr.next();
				preRequisiteMarks.setPrerequisiteMarksObtained(new BigDecimal(applicantDetail.getPreRequisiteObtMarks()));
				preRequisiteMarks.setRollNo(applicantDetail.getPreRequisiteRollNo());
				preRequisiteMarks.setExamMonth(Integer.parseInt(applicantDetail.getPreRequisiteExamMonth()));
				preRequisiteMarks.setExamYear(Integer.parseInt(applicantDetail.getPreRequisiteExamYear()));
			}
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
			if(admForm.getInterviewVenue()!=null){
				ExamCenter examCenter = new ExamCenter();
				examCenter.setId(Integer.parseInt(admForm.getInterviewVenue()));
				appBO.setExamCenter(examCenter);
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
			appBO.setCreatedDate(applicantDetail.getCreatedDate());
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
				// raghu commented
				//setPreferenceBos(appBO,admForm.getPreferenceList());
			}
			//raghu added newly
			if(admForm.getPrefcourses()!=null && !admForm.getPrefcourses().isEmpty()){
				setPreferenceBo( appBO,admForm.getPrefcourses());
			}
				
			//set vehicle details if any
			setvehicleDetailsEdit(appBO,applicantDetail);
			setworkExpDetails(appBO,applicantDetail);
			setDocUploads(appBO,applicantDetail,admForm);
			setOriginalSubmittedDocsList(applicantDetail,admForm);
			appBO.setMode(applicantDetail.getMode());
			appBO.setAdmStatus(applicantDetail.getAdmStatus());
			appBO.setSeatNo(applicantDetail.getSeatNo());
			appBO.setIsPreferenceUpdated(applicantDetail.getIsPreferenceUpdated());
			appBO.setFinalMeritListApproveDate(applicantDetail.getFinalMeritListApproveDate());
			appBO.setVerifiedBy(applicantDetail.getVerifiedBy());
			//added for StudentSpecializationPreferred and AdmApplnAdditionalInfo-Smitha
			if(applicantDetail.getStudentSpecializationPrefered()!=null && !applicantDetail.getStudentSpecializationPrefered().isEmpty())
				appBO.setStudentSpecializationPrefered(applicantDetail.getStudentSpecializationPrefered());
			if(applicantDetail.getAdmapplnAdditionalInfos()!=null && !applicantDetail.getAdmapplnAdditionalInfos().isEmpty()){
				Set<AdmapplnAdditionalInfo> set = new HashSet<AdmapplnAdditionalInfo>(); 
				List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(applicantDetail.getAdmapplnAdditionalInfos());
				AdmapplnAdditionalInfo additionalInfo = additionalList.get(0);
				additionalInfo.setTitleFather(applicantDetail.getTitleOfFather());
				additionalInfo.setTitleMother(applicantDetail.getTitleOfMother());
				additionalInfo.setBackLogs(admForm.isBackLogs());
				//raghu added newly
				if(admForm.getIsSaypass() != null){
					additionalInfo.setIsSaypass(admForm.getIsSaypass());
				}else{
				additionalInfo.setIsSaypass(false);
				
				}
				additionalInfo.setIsComedk(applicantDetail.getIsComeDk());
				set.add(additionalInfo);
				appBO.setAdmapplnAdditionalInfo(set);
			}
		}
		log.info("exit getApplicantBO" );
		return appBO;
	}
	/**
	 * @param applicantDetail
	 * @param admForm
	 */
	private void setOriginalSubmittedDocsList(AdmApplnTO applicantDetail,ApplicationEditForm admForm) {
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
						name=ApplicationEditHelper.PHOTO;
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
	 * @param appBO
	 * @param applicantDetail
	 * @param admForm
	 */
	private void setDocUploads(AdmAppln appBO, AdmApplnTO applicantDetail,ApplicationEditForm admForm) throws Exception {
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
					docBO.setName(editDoc.getFileName());
					docBO.setContentType(editDoc.getContentType());
					//raghu
					// save the student photo into file system.
					if(docTO.isPhoto()){
						try{
						FileOutputStream fos = new FileOutputStream(CMSConstants.STUDENT_PHOTO_FOLDER_PATH+applicantDetail.getStudentId()+".jpg");
						//FileOutputStream fos = new FileOutputStream("D:/"+admForm.getStudentId()+".jpg");
						fos.write(editDoc.getFileData());
						fos.close();
						}
						catch(Exception e)
						{
							
						}
					}
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
	/**
	 * @param appBO
	 * @param applicantDetail
	 * @param admForm
	 * @param isPresidance
	 * @throws Exception 
	 */
	private void setPersonaldataBO(AdmAppln appBO, AdmApplnTO applicantDetail,ApplicationEditForm admForm, boolean isPresidance) throws Exception {
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
			data.setIsNcccertificate(dataTo.isNcccertificate());
			data.setIsNsscertificate(dataTo.isNsscertificate());
			data.setIsExcervice(dataTo.isExservice());
			
			//basim
			data.setSports(dataTo.getSports());
			data.setArts(dataTo.getArts());
			data.setSportsParticipate(dataTo.getSportsParticipate());
			data.setArtsParticipate(dataTo.getArtsParticipate());
			data.setCurrentStreet(dataTo.getCurrentStreet());
			data.setPermanentStreet(dataTo.getPermanentStreet());
			
			if(dataTo.isNcccertificate()){
				data.setNccgrade(dataTo.getNccgrades());
			}
			else
				data.setNccgrade(null);
			
			if(dataTo.isNsscertificate())
				data.setNssgrade(dataTo.getNssgrades());
			else
				data.setNssgrade(null);
			data.setSportsPersonDescription(dataTo.getSportsDescription());
			data.setHandicappedDescription(dataTo.getHadnicappedDescription());
			
			if(admForm.getCourseId()!=null && admForm.getCourseId().equalsIgnoreCase("18")){
				if(dataTo.getNoofenglishCoreCourses()!=null)	
					 data.setNoofenglishCoreCourses(dataTo.getNoofenglishCoreCourses());
				
			}else{
				 data.setNoofenglishCoreCourses(null);
			}
			
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
					//raghu
					casteBO.setReligion(religionbo);
					data.setCaste(casteBO);
				}else{
					data.setCaste(null);
					data.setCasteOthers(dataTo.getCasteOthers());
				}
				
				if(dataTo.getUgcourse()!= null && !dataTo.getUgcourse().equalsIgnoreCase("")){
					UGCoursesBO ug=new UGCoursesBO();
					ug.setId(Integer.parseInt(dataTo.getUgcourse()));
					data.setUgcourse(ug);
				}
				
				/*if(dataTo.getDioceseId()!=null && !StringUtils.isEmpty(dataTo.getDioceseId()) && StringUtils.isNumeric(dataTo.getDioceseId())){
					DioceseBO dioceseBo=new DioceseBO();
					dioceseBo.setId(Integer.parseInt(dataTo.getDioceseId()));
					data.setDiocese(dioceseBo);
				}
				if(dataTo.getParishId()!=null && !StringUtils.isEmpty(dataTo.getParishId()) && StringUtils.isNumeric(dataTo.getParishId())){
					ParishBO parishBO=new ParishBO();
					parishBO.setId(Integer.parseInt(dataTo.getParishId()));
					data.setParish(parishBO);
				}else if(dataTo.getParishOthers()!=null && !StringUtils.isEmpty(dataTo.getParishOthers()) ){
					
					data.setParishOthers(dataTo.getParishOthers());
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
					
					
					//raghu district
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
					
					//raghu added newly
					 if (dataTo.getStream()!=null && !StringUtils.isEmpty(dataTo.getStream()) && StringUtils.isNumeric(dataTo.getStream())) {
							EducationStream stream= new EducationStream();
							stream.setId(Integer.parseInt(dataTo.getStream()));
							data.setStream(stream);
					 }
					
					 
					/* if(dataTo.getFatherIncome1()!=null && !StringUtils.isEmpty(dataTo.getFatherIncome1())){
						 data.setFatherIncome1(dataTo.getFatherIncome1());
			 			}
					*/
					data.setFatherMobile(dataTo.getFatherMobile());
					data.setMotherMobile(dataTo.getMotherMobile());

					if(dataTo.getPermanentCountryId()!=0){
					Country permCnt= new Country();
					permCnt.setId(dataTo.getPermanentCountryId());
					data.setCountryByPermanentAddressCountryId(permCnt);
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
						//district
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
						
						
						if(dataTo.getCurrentCountryId()!=0){
						Country currCnt= new Country();
						currCnt.setId(dataTo.getCurrentCountryId());
						data.setCountryByCurrentAddressCountryId(currCnt);	
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
					
						
						if(dataTo.getFatherOccupationId()!=null && !StringUtils.isEmpty(dataTo.getFatherOccupationId()) && StringUtils.isNumeric(dataTo.getFatherOccupationId())&& !dataTo.getFatherOccupationId().equalsIgnoreCase("other")){
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
							data.setRecommentedBy(admForm.getRecomendedBy());
						}
						if(dataTo.getUniversityEmail()!=null && !dataTo.getUniversityEmail().isEmpty())
							data.setUniversityEmail(dataTo.getUniversityEmail());
						appBO.setPersonalData(data);
						//raghu write newly
						if(applicantDetail.getAppliedYear()>2015){
							 setEdnqualificationBO(appBO, applicantDetail, admForm);
								
						 }else{
							 setEdnqualificationBO(appBO,applicantDetail,isPresidance,admForm);
								
						 }
						
						setPreferences(appBO,applicantDetail);
							
						//raghu wrote set preferences to bo 
						setPreferencesToBo(appBO,admForm);
						
						setDocumentupload(appBO,applicantDetail);
			
			if(dataTo.getAadhaarCardNumber() != null && !dataTo.getAadhaarCardNumber().isEmpty())
				data.setAadharCardNumber(dataTo.getAadhaarCardNumber());
			if(dataTo.getMotherTongueId() != null && !dataTo.getMotherTongueId().isEmpty()) {
			
				MotherTongue motherTongue = new MotherTongue();
				motherTongue.setId(Integer.parseInt(dataTo.getMotherTongueId()));
				data.setMotherTongue(motherTongue);
			}				
			if(dataTo.getFatherPANNumber() != null && !dataTo.getFatherPANNumber().isEmpty())
				data.setFatherPANNumber(dataTo.getFatherPANNumber());
			if(dataTo.getFatherAadhaarNumber() != null && !dataTo.getFatherAadhaarNumber().isEmpty())
				data.setFatherAadhaarNumber(dataTo.getFatherAadhaarNumber());
			if(dataTo.getIsmgquota() != null)
				data.setIsmgquota(dataTo.getIsmgquota());
			if(dataTo.getRecommentedBy() != null && !dataTo.getRecommentedBy().isEmpty()) {
				data.setRecommentedBy(dataTo.getRecommentedBy());
			}
			if(dataTo.getRecommendDeatails()!= null && !dataTo.getRecommendDeatails().isEmpty()) {
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
	 * @param appBO
	 * @param applicantDetail
	 * @param isPresidance
	 */
	private void setEdnqualificationBO(AdmAppln appBO,
			AdmApplnTO applicantDetail, boolean isPresidance,ApplicationEditForm form) {
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
				
				
				
				 DecimalFormat d=new DecimalFormat("#.##");
				if(!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise())
				if((qualificationTO.getDocName().equalsIgnoreCase("Class 12"))){
				Set<AdmSubjectMarkForRank> submarks=new HashSet<AdmSubjectMarkForRank>();
				List<AdmSubjectMarkForRankTO> subList=form.getAdmsubMarkList();
								
				if(subList!=null){
					Iterator<AdmSubjectMarkForRankTO> itrt=subList.iterator();

					while(itrt.hasNext()){
						
					AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itrt.next();
						//ra
					//if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
					if((admSubjectMarkForRankTO.getAdmSubjectForRankTo()!=null && admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId()!=0) || (admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase(""))){
								
					AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
					admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
					admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
					//old record
					if(admSubjectMarkForRankTO.getId()!=0){
						admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
					}
					
					AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
					if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
						admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
					}else{
						admSubjectForRank.setId(admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId());
					}
					
					
					
					admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
					
					Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
					Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
					Double conmark=(obtmark/maxmark)*200;
					admSubjectMarkForRank.setConversionmark(new Double( d.format(conmark).toString()).toString());
					admSubjectMarkForRank.setIsActive(true);
					admSubjectMarkForRank.setModifiedBy(form.getUserId());
					admSubjectMarkForRank.setLastModifiedDate(new Date());
					submarks.add(admSubjectMarkForRank);
					
						}//if close
					}//while close
					
				}
				bo.setAdmSubjectMarkForRank(submarks);
				}
				
				
				
				//raghu
				if(!qualificationTO.isConsolidated() && !qualificationTO.isSemesterWise())
				if((qualificationTO.getDocName().equalsIgnoreCase("DEG"))){
					Set<AdmSubjectMarkForRank> submarks=new HashSet<AdmSubjectMarkForRank>();
					List<AdmSubjectMarkForRankTO> subList=form.getAdmsubMarkListUG();
									
					if(subList!=null){
						Iterator<AdmSubjectMarkForRankTO> itrt=subList.iterator();

						while(itrt.hasNext()){
							
                            AdmSubjectMarkForRankTO admSubjectMarkForRankTO=(AdmSubjectMarkForRankTO) itrt.next();
                            if((form.getPatternofStudy()!=null && form.getPatternofStudy().equalsIgnoreCase("CBCSS")) || (qualificationTO.getUgPattern()!=null && qualificationTO.getUgPattern().equalsIgnoreCase("CBCSS"))){
                                  
                            	if((admSubjectMarkForRankTO.getAdmSubjectForRankTo()!=null && admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId()!=0) || (admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase(""))){
									
            						AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
            						admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
            						admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
            						admSubjectMarkForRank.setCredit(admSubjectMarkForRankTO.getCredit());
            						//old record
            						if(admSubjectMarkForRankTO.getId()!=0){
            							admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
            						}
            						
            						AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
            						if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
            							admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
            						}else{
            							admSubjectForRank.setId(admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId());
            						}
            						
            						
            						
            						admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
            						
            						Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
            						Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
            						Double conmark=(obtmark/maxmark)*4;
            						admSubjectMarkForRank.setConversionmark(new Double( d.format(conmark).toString()).toString());
            						admSubjectMarkForRank.setIsActive(true);
            						admSubjectMarkForRank.setLastModifiedDate(new Date());
            						admSubjectMarkForRank.setModifiedBy(form.getUserId());
            						
            						submarks.add(admSubjectMarkForRank);
            						
            					}//if close
            						       
                            }else {
                                  
                                  
                            	if((admSubjectMarkForRankTO.getAdmSubjectForRankTo()!=null && admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId()!=0) || (admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase(""))){
									
            						AdmSubjectMarkForRank admSubjectMarkForRank=new AdmSubjectMarkForRank();
            						admSubjectMarkForRank.setObtainedmark(admSubjectMarkForRankTO.getObtainedmark());
            						admSubjectMarkForRank.setMaxmark(admSubjectMarkForRankTO.getMaxmark());
            						//old record
            						if(admSubjectMarkForRankTO.getId()!=0){
            							admSubjectMarkForRank.setId(admSubjectMarkForRankTO.getId());
            						}
            						
            						AdmSubjectForRank admSubjectForRank=new AdmSubjectForRank();
            						if(admSubjectMarkForRankTO.getSubid()!=null && !admSubjectMarkForRankTO.getSubid().equalsIgnoreCase("")){
            							admSubjectForRank.setId(Integer.parseInt(admSubjectMarkForRankTO.getSubid()));
            						}else{
            							admSubjectForRank.setId(admSubjectMarkForRankTO.getAdmSubjectForRankTo().getId());
            						}
            						
            						
            						
            						admSubjectMarkForRank.setAdmSubjectForRank(admSubjectForRank);
            						
            						Double obtmark=Double.parseDouble(admSubjectMarkForRankTO.getObtainedmark());
            						Double maxmark=Double.parseDouble(admSubjectMarkForRankTO.getMaxmark());
            						Double conmark=(obtmark/maxmark)*4;
            						admSubjectMarkForRank.setConversionmark(new Double( d.format(conmark).toString()).toString());
            						admSubjectMarkForRank.setIsActive(true);
            						admSubjectMarkForRank.setLastModifiedDate(new Date());
            						admSubjectMarkForRank.setModifiedBy(form.getUserId());
            						
            						submarks.add(admSubjectMarkForRank);
            						
            							}//if close
            						
                            
                            
                            }//if close
                            
                          
                            
						}//while close
						
					}
					bo.setAdmSubjectMarkForRank(submarks);
					 if(form.getPatternofStudy()!=null && form.getPatternofStudy().equalsIgnoreCase("CBCSS")){
						 bo.setUgPattern(form.getPatternofStudy());
					 }else{
						 bo.setUgPattern(qualificationTO.getUgPattern());
					 }
					 
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
						//ra set edn
						//detailMark.setEdnQualification(bo);
						
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
						if(detailMarkto.getTotalObtainedMarks()!=null && !StringUtils.isEmpty(detailMarkto.getTotalObtainedMarks())&& CommonUtil.isValidDecimal(detailMarkto.getTotalObtainedMarks())){
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
								
								if(detailMarkto.getMaxMarks()!=null && !StringUtils.isEmpty(detailMarkto.getMaxMarks()) && CommonUtil.isValidDecimal(detailMarkto.getMaxMarks())){
									totalMark=Integer.parseInt(detailMarkto.getMaxMarks());
								}
								if(detailMarkto.getMarksObtained()!=null && !StringUtils.isEmpty(detailMarkto.getMarksObtained()) && CommonUtil.isValidDecimal(detailMarkto.getMarksObtained())){
									obtainMark=Integer.parseInt(detailMarkto.getMarksObtained());
								}
								
								if(detailMarkto.getMaxMarks_languagewise()!=null && !StringUtils.isEmpty(detailMarkto.getMaxMarks_languagewise()) && CommonUtil.isValidDecimal(detailMarkto.getMaxMarks_languagewise())){
									totallanguagewiseMark=Integer.parseInt(detailMarkto.getMaxMarks_languagewise());
								}
								if(detailMarkto.getMarksObtained_languagewise()!=null && !StringUtils.isEmpty(detailMarkto.getMarksObtained_languagewise()) && CommonUtil.isValidDecimal(detailMarkto.getMarksObtained_languagewise())){
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
							bo.setApplicantMarksDetailses(semesterDetails)	;
						}
					
				}
					
					qualificationSet.add(bo);
			}
			if(appBO.getPersonalData()!=null)
				appBO.getPersonalData().setEdnQualifications(qualificationSet);
		
		}
		log.info("exit setEdnqualificationBO" );
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
	
	public void getInterviewSelectionScheduleOnline(ApplicationEditForm admForm, int year) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<Object[]>  schedule =txn.getInterviewSelectionScheduleOnline(Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramYear()));
		Object[] interviewSS= txn.getInterviewSelectionVenueForApplicant(admForm);
		prepareRequiredScheduleOnline(interviewSS,schedule,admForm);
	}
	
	public void getInterviewSelectionScheduleOffline(ApplicationEditForm admForm, int year) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<Object[]>  schedule =txn.getInterviewSelectionScheduleOffline(Integer.parseInt(admForm.getProgramId()), Integer.parseInt(admForm.getProgramYear()));
		Object[] interviewSS= txn.getInterviewSelectionVenueForApplicant(admForm);
		prepareRequiredScheduleOffline(interviewSS,schedule,admForm);
	}
	
	public void prepareRequiredScheduleOnline(Object[] interviewSS, List<Object[]> interviewBos,ApplicationEditForm admForm) throws Exception {
		List<InterviewSelectionScheduleTO> interschedule= new ArrayList<InterviewSelectionScheduleTO>();
		Map<Integer, String> interviewVenueSelection= new HashMap<Integer, String>();
		Map<Integer, String> interviewSelectionSchedule= new HashMap<Integer, String>();
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
					if(inter[0].toString()!=null && inter[5].toString()!=null)
					{
						interviewSelectionSchedule.put(Integer.parseInt(inter[0].toString()), inter[5].toString());
					}
					}
				}
			}
			
		if(interviewSS!=null){
			interviewVenueSelection.put(Integer.parseInt(interviewSS[2].toString()), interviewSS[3].toString());
			interviewSelectionSchedule.put(Integer.parseInt(interviewSS[0].toString()), CommonUtil.formatSqlDate(interviewSS[1].toString()));
			}
			admForm.setInterviewSelectionSchedule(interviewSelectionSchedule);
			admForm.setInterviewVenueSelection(interviewVenueSelection);
}

	
	
	
	
	

	
	public void prepareRequiredScheduleOffline(Object[] interviewSS, List<Object[]> interviewBos,ApplicationEditForm admForm) throws Exception {
		List<InterviewSelectionScheduleTO> interschedule= new ArrayList<InterviewSelectionScheduleTO>();
		Map<Integer, String> interviewVenueSelection= new HashMap<Integer, String>();
		Map<Integer, String> interviewSelectionSchedule= new HashMap<Integer, String>();
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
				if(inter[0].toString()!=null && inter[5].toString()!=null)
				{
					interviewSelectionSchedule.put(Integer.parseInt(inter[0].toString()), inter[5].toString());
				}

			 }
		  }
		}
		if(interviewSS!=null){
			interviewVenueSelection.put(Integer.parseInt(interviewSS[2].toString()), interviewSS[3].toString());
			interviewSelectionSchedule.put(Integer.parseInt(interviewSS[0].toString()), CommonUtil.formatSqlDate(interviewSS[1].toString()));
		}
			admForm.setInterviewSelectionSchedule(interviewSelectionSchedule);
			admForm.setInterviewVenueSelection(interviewVenueSelection);

}
	
	
	
	//raghu added newly
	
	private void setPreferenceBo(AdmAppln appBO,
			List<CourseTO> preferenceList) {
		log.info("enter setPreferenceBos" );
		Set<CandidatePreference> preferencebos=new HashSet<CandidatePreference>();
		Iterator<CourseTO> toItr= preferenceList.iterator();
		
		while (toItr.hasNext()) {
			CourseTO prefTO = (CourseTO) toItr.next();
			//if (prefTO!=null && prefTO.getId()!=0) {
				
				if(prefTO.getPrefNo()!=null &&!prefTO.getPrefNo().equalsIgnoreCase("")){
				CandidatePreference bo = new CandidatePreference();
				if(prefTO.getPrefId()!=0)
				bo.setId(prefTO.getPrefId());
				bo.setPrefNo(Integer.parseInt(prefTO.getPrefNo()));
				if(prefTO.getId()!=0){
				Course prefcrs = new Course();
				prefcrs.setId(prefTO.getId());
				bo.setCourse(prefcrs);
				}
				
				/*AdmAppln appB=new AdmAppln();
				appB.setId(appBO.getId());
				bo.setAdmAppln(appB);*/
				
				preferencebos.add(bo);
				}
				
			//}
		}
		appBO.setCandidatePreferences(preferencebos);
		log.info("exit setPreferenceBos" );
	}
	

	



	private List<AdmSubjectMarkForRankTO> copyPropertiesValuesofMark(Set<EdnQualification> qualificationSet, CourseTO selectedCourse,Integer appliedYear, ApplicationEditForm admForm) throws Exception {
		log.info("enter copyPropertiesValue ednqualification" );
		
		Set<AdmSubjectMarkForRank> SubmarkList=new HashSet<AdmSubjectMarkForRank>();
		Set<AdmSubjectMarkForRank> SubmarkListUG=new HashSet<AdmSubjectMarkForRank>();
		Iterator<EdnQualification> iterator = qualificationSet.iterator();
		List<AdmSubjectMarkForRankTO> admList=new ArrayList<AdmSubjectMarkForRankTO>();
		
		while (iterator.hasNext())
		{
			EdnQualification ednQualificationBO = iterator.next();
			
			if(ednQualificationBO.getDocChecklist().getDocType().getName().equalsIgnoreCase("Class 12"))
			{
				 SubmarkList=ednQualificationBO.getAdmSubjectMarkForRank();
			}
			
			if(ednQualificationBO.getDocChecklist().getDocType().getName().equalsIgnoreCase("DEG"))
			{
				 SubmarkListUG=ednQualificationBO.getAdmSubjectMarkForRank();
			}
		}
	
		Iterator<AdmSubjectMarkForRank> itr=null;
		if(SubmarkList.size()!=0){
			itr=SubmarkList.iterator();
		}
		if(SubmarkListUG.size()!=0){
			itr=SubmarkListUG.iterator();
		}
		
	    AdmSubjectMarkForRankTO admSubjectMarkForRankTO=null;
	    if(itr!=null)
	    while(itr.hasNext())
	    {
	    	AdmSubjectMarkForRank admSubjectMarkForRank=(AdmSubjectMarkForRank) itr.next();
	    	admSubjectMarkForRankTO=new AdmSubjectMarkForRankTO();
	    	admSubjectMarkForRankTO.setId(admSubjectMarkForRank.getId());
	    	admSubjectMarkForRankTO.setObtainedmark(admSubjectMarkForRank.getObtainedmark());
	    	admSubjectMarkForRankTO.setMaxmark(admSubjectMarkForRank.getMaxmark());
	    	admSubjectMarkForRankTO.setConversionmark(admSubjectMarkForRank.getConversionmark());
	    	if(admSubjectMarkForRank.getCredit()!=null)
	    	admSubjectMarkForRankTO.setCredit(admSubjectMarkForRank.getCredit());
	    	
	    	AdmSubjectForRankTo admSubjectForRankTo=new AdmSubjectForRankTo();
	    	admSubjectForRankTo.setId(admSubjectMarkForRank.getAdmSubjectForRank().getId());
	    	admSubjectForRankTo.setGroupname(admSubjectMarkForRank.getAdmSubjectForRank().getGroupName());
	    	admSubjectForRankTo.setSubjectname(admSubjectMarkForRank.getAdmSubjectForRank().getName());
	    	admSubjectMarkForRankTO.setAdmSubjectForRankTo(admSubjectForRankTo);
	    	
	    	EdnQualificationTO ednQualificationTO=new EdnQualificationTO();
	    	ednQualificationTO.setId(admSubjectMarkForRank.getEdnQualification().getId());
	    	admSubjectMarkForRankTO.setEdnQualificationTO(ednQualificationTO);
	    	
	    	admSubjectMarkForRankTO.setIsActive(admSubjectMarkForRank.getIsActive());
	    	admList.add(admSubjectMarkForRankTO);	
	    }
	    
		return admList;
	}
	
	

	
	//raghu for preferences
	
	
	/**
	 * @param candidatePreferences
	 * @return
	 */
	private List<CandidatePreferenceTO> copyPropertiesValue(Set<CandidatePreference> preferencesSet,ApplicationEditForm form) {
		log.info("enter copyPropertiesValue preferences" ); 
		
		List<CandidatePreferenceTO> preferenceTOList = new LinkedList<CandidatePreferenceTO>();

		if (preferencesSet != null) {
			Iterator<CandidatePreference> iterator = preferencesSet.iterator();
			

			while (iterator.hasNext()) {
				CandidatePreference candidatePreferenceBO = (CandidatePreference) iterator.next();
				CandidatePreferenceTO  preferenceTO=new CandidatePreferenceTO();
				// select the preferences depending upon the preference no.
			
						preferenceTO.setId(candidatePreferenceBO.getId());
						preferenceTO.setPrefNo(candidatePreferenceBO.getPrefNo());
						preferenceTO.setCoursId(String.valueOf(candidatePreferenceBO.getCourse().getId()));
						preferenceTO.setAdmApplnid(candidatePreferenceBO.getAdmAppln().getId());
									
						preferenceTOList.add(preferenceTO);
				
			}
		}
		log.info("exit copyPropertiesValue preferences" );
		form.setPreferenceList(preferenceTOList);
		
		return preferenceTOList;
	}


	
	/**
	 * @param appBO
	 * @param applicantDetail
	 */
	private void setPreferencesToBo(AdmAppln appBO, ApplicationEditForm form) {

		log.info("enter setPreferences" );
		//raghu added newly
		Set<CandidatePreference> preferences= new HashSet<CandidatePreference>();
		//List<CandidatePreferenceTO> prefToList=form.getPreferenceList();
		//Iterator<CandidatePreferenceTO> i=prefToList.iterator();
		List<CourseTO> prefToList=form.getPrefcourses();
		Iterator<CourseTO> toItr=prefToList.iterator();
		while(toItr.hasNext()){
			CourseTO courseTO = (CourseTO) toItr.next();
				/*AdmAppln appB=new AdmAppln();
				appB.setId(appBO.getId());
				bo.setAdmAppln(appB);*/
				if(courseTO.getPrefNo()!=null &&!courseTO.getPrefNo().equalsIgnoreCase("")){
				CandidatePreference bo = new CandidatePreference();
				if(courseTO.getPrefId()!=0)
				bo.setId(courseTO.getPrefId());
				bo.setPrefNo(Integer.parseInt(courseTO.getPrefNo()));
				if(courseTO.getId()!=0){
					Course course = new Course();
					course.setId(courseTO.getId());
					bo.setCourse(course);
				}
				
				preferences.add(bo);
				}
				
			
		}
			
		appBO.setCandidatePreferences(preferences);
		log.info("exit setPreferences" );
	}

	
	//raghu added newly
	
	private List<CandidatePreferenceTO> copyPropertiesValueNew(Set<CandidatePreference> preferencesList,ApplicationEditForm admForm) {
		log.info("enter copyPropertiesValue preferences" ); 
		
		List<CandidatePreferenceTO> preferenceTOList = new LinkedList<CandidatePreferenceTO>();

		
		if(preferencesList!=null && preferencesList.size() > 0){
			List<CourseTO> list=new ArrayList<CourseTO>();
			
			for(CandidatePreference preferenceBo:preferencesList){
				if(preferenceBo.getPrefNo()!=null && preferenceBo.getPrefNo()>0){
					CourseTO courseTO=new CourseTO();
					if(preferenceBo.getId()!=0)
					courseTO.setPrefId(preferenceBo.getId());
					courseTO.setPrefNo(preferenceBo.getPrefNo()+"");
					
					if(preferenceBo.getCourse()!=null){
						courseTO.setId(preferenceBo.getCourse().getId());
						courseTO.setName(preferenceBo.getCourse().getName());
					}
					
					courseTO.setCourseMap(admForm.getCourseMap());
					courseTO.setPrefName(prefNameMap.get(preferenceBo.getPrefNo()-1));
					list.add(courseTO);	
				}
				
			}
			Collections.sort(list);
			admForm.setPrefcourses(list);
			admForm.setPreferenceSize(list.size());
			
			}
		
		return preferenceTOList;
	}
	
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
					
					String Core="Core";
					String Compl="Complementary";
					String Common="Common";
					String Open="Open";
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
				

				
				
					
				String Core="Core";
				String Compl="Complementary";
				String Common="Common";
				String Open="Open";
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
	
	
	
	
	
	
	//raghu get EdnqualificationBO and storing database
	private void setEdnqualificationBO(AdmAppln appBO,
			AdmApplnTO applicantDetail,ApplicationEditForm admForm) throws Exception {
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
				/*if(qualificationTO.getMarksObtained()!=null && !StringUtils.isEmpty(qualificationTO.getMarksObtained()) && CommonUtil.isValidDecimal(qualificationTO.getMarksObtained()))
					bo.setMarksObtained(new BigDecimal(qualificationTO.getMarksObtained()));
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
								//raghu
								bo.setTotalMarks(new BigDecimal(totalmark));
								bo.setMarksObtained(new BigDecimal(totalobtained));
								bo.setPercentage(new BigDecimal(percentage));
							
										
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
							//raghu
							bo.setTotalMarks(new BigDecimal(totalmark));
							bo.setMarksObtained(new BigDecimal(totalobtained));
							bo.setPercentage(new BigDecimal(percentage));
							
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
						//raghu
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
								//raghu
								float totalMark=0;
								float obtainMark=0;
								float totallanguagewiseMark=0;
								float obtainlanguagewiseMark=0;
								
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
								
								//raghu
								float percentage=0;
								float percentageLanguagewise = 0;
								
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
								//raghu
								bo.setTotalMarks(new BigDecimal(overallanguagewiseMark));
								bo.setMarksObtained(new BigDecimal(overallanguagewiseObtain));
								double calPerc=0.0;
								if(overallanguagewiseMark!=0.0 && overallanguagewiseObtain!=0.0)
								calPerc=(overallanguagewiseObtain/overallanguagewiseMark)*100;
								bo.setPercentage(new BigDecimal(calPerc));
							}else{
								//raghu
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
	
	private void setDetailSubjectMarkBOClass12(EdnQualification ednQualification,EdnQualificationTO ednQualificationTO,CandidateMarks candidateMarks,CandidateMarkTO candidateMarkTO, ApplicationEditForm admForm,int docName) throws Exception {
		
		
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
	
	private void setDetailSubjectMarkBODegree(EdnQualification ednQualification,EdnQualificationTO ednQualificationTO,CandidateMarks candidateMarks,CandidateMarkTO candidateMarkTO, ApplicationEditForm admForm,int docName,String patternofStudy) throws Exception {
		
	
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
	private AdmSubjectMarkForRank getAdmSubjectMarkForRank(int docname,CandidateMarkTO candidateMarkTO,String subjectId,String subjectMarkId,String subjectOther,String subjectTotalMark,String subjectObtainMark,String subjectCredit, ApplicationEditForm admForm) {
		
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
             DecimalFormat df=new DecimalFormat("#.##");
             admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
    
            // admSubjectMarkForRank.setAdmSubjectOther(subjectOther);
            // admSubjectMarkForRank.setCredit(subjectCredit);
        
         }
         
         //deg
			 int doctypeId1=CMSConstants.DEGREE_DOCTYPEID;
			
         if(docname==doctypeId1){
        	 Double obtmark=Double.parseDouble(subjectObtainMark);
             Double maxmark=Double.parseDouble(subjectTotalMark);
             Double conmark=(obtmark/maxmark)*4;
             DecimalFormat df=new DecimalFormat("#.##");
             admSubjectMarkForRank.setConversionmark(new Double(df.format(conmark)).toString());
             
             admSubjectMarkForRank.setAdmSubjectOther(subjectOther);
             admSubjectMarkForRank.setCredit(subjectCredit);
        
         }
         
		return admSubjectMarkForRank;
		}
		else{
			
		return null;
		}
	}


	



}