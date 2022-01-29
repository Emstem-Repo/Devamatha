package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmapplnAdditionalInfo;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.StudentCommonRank;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.handlers.admin.DetailedSubjectsHandler;
import com.kp.cms.helpers.admission.ApplicationEditHelper;
import com.kp.cms.helpers.admission.ApplicationRankHelper;
import com.kp.cms.helpers.admission.ApplicationRankHelperforPg;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.CandidatePreferenceEntranceDetailsTO;
import com.kp.cms.to.admin.CandidatePreferenceTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.CurrencyTO;
import com.kp.cms.to.admin.DetailedSubjectsTO;
import com.kp.cms.to.admin.DocTypeExamsTO;
import com.kp.cms.to.admin.ExamCenterTO;
import com.kp.cms.to.admin.IncomeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StudentCourseAllotmentTo;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.admin.ICourseTransaction;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IApplicationEditTransaction;
import com.kp.cms.transactionsimpl.admin.CourseTransactionImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.ApplicationEditTransactionimpl;

public class ApplicationEditHandler {
	/**
	 * Singleton object of ApplicationEditHandler
	 */
	private static volatile ApplicationEditHandler applicationEditHandler = null;
	private static final Log log = LogFactory.getLog(ApplicationEditHandler.class);
	private ApplicationEditHandler() {
		
	}
	/**
	 * return singleton object of ApplicationEditHandler.
	 * @return
	 */
	public static ApplicationEditHandler getInstance() {
		if (applicationEditHandler == null) {
			applicationEditHandler = new ApplicationEditHandler();
		}
		return applicationEditHandler;
	}
	
	
	/**
	 * FETCHES APPLICANT DETAILS
	 * @param applicationNumber
	 * @param applicationYear
	 * @return appDetails
	 */
	public AdmApplnTO getApplicantDetails(String applicationNumber,
			int applicationYear,ApplicationEditForm admForm) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		ApplicationEditHelper helper = ApplicationEditHelper.getInstance();

		AdmAppln applicantDetails = txn.getApplicantDetails(applicationNumber, applicationYear);
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
			if(applicantDetails.getMode()!=null && !applicantDetails.getMode().isEmpty() && applicantDetails.getMode().equalsIgnoreCase("Online")){
				admForm.setOnlineApply(true);
			}else
			{
				admForm.setOnlineApply(false);
			}
			if(applicantDetails.getPersonalData()!= null && applicantDetails.getPersonalData().getResidentCategory()!= null){
				resCategory = applicantDetails.getPersonalData().getResidentCategory().getId();
			}
			if(applicantDetails.getPersonalData().getNationality()!= null){
				natId = applicantDetails.getPersonalData().getNationality().getId();
			}
			}
		//to copy the BO properties to TO
		AdmApplnTO appDetails = helper.copyPropertiesValue(applicantDetails, applicationYear,admForm);
		
		if(applicantDetails != null){
			AdmapplnAdditionalInfo additionalInfo = new AdmapplnAdditionalInfo();
			if(applicantDetails.getAdmapplnAdditionalInfo() != null && !applicantDetails.getAdmapplnAdditionalInfo().isEmpty()){
				List<AdmapplnAdditionalInfo> additionalList = new ArrayList<AdmapplnAdditionalInfo>(applicantDetails.getAdmapplnAdditionalInfo());
				additionalInfo = additionalList.get(0);
			}
			if(additionalInfo.getTitleFather() != null){
				appDetails.setTitleOfFather(additionalInfo.getTitleFather());
			}
			if(additionalInfo.getTitleMother() != null){
				appDetails.setTitleOfMother(additionalInfo.getTitleMother());
			}
			if(applicantDetails.getRecommedationApplicationNo() != null && !applicantDetails.getRecommedationApplicationNo().isEmpty()){
				appDetails.setRecommedationApplicationNo(applicantDetails.getRecommedationApplicationNo());
			}
			if(additionalInfo.getBackLogs() != null)
				appDetails.setBackLogs(additionalInfo.getBackLogs());
			if(additionalInfo.getIsComedk()!=null && additionalInfo.getIsComedk()){
				appDetails.setIsComeDk(true);
			}else{
				appDetails.setIsComeDk(false);
			}
			//raghu added newly
			if(additionalInfo.getIsSaypass() != null){
				appDetails.setIsSaypass(additionalInfo.getIsSaypass());
			}
				
		}
		return appDetails;
	}
	
	//for RankList
	
	public List<AdmAppln> getApplicantDetail(ApplicationEditForm admForm) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();

		List<AdmAppln> applicantDetails = txn.getApplicantDetail(admForm);
		
		return applicantDetails;
	}
	
	
	
	/**
	 * @param valueOf
	 * @return
	 */
	public Map<Integer, String> getDetailedSubjectsByCourse(String courseId) throws Exception {
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
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public List<DocTypeExamsTO> getDocExamsByID(int id) throws Exception{
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<DocTypeExams> examBos=txn.getDocExamsByID(id);
		return ApplicationEditHelper.getInstance().convertDocExamBosToTOS(examBos);
	}
	/**
	 * @param valueOf
	 * @param appliedYear
	 * @return
	 */
	public List<ApplnDocTO> getRequiredDocList(String courseID,Integer appliedYear) throws Exception {
		log.info("Enter getRequiredDocList ...");
		ApplicationEditHelper helper= ApplicationEditHelper.getInstance();
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<DocChecklist> checklistDocs=txn.getNeededDocumentList(courseID);
		log.info("Exit getRequiredDocList ...");
		return helper.createDocUploadListForCourse(checklistDocs,appliedYear);
	}
	/**
	 * @param programId
	 * @return
	 * @throws Exception
	 */
	public List<ExamCenterTO> getExamCenters(int programId) throws Exception {
		log.info("Enter getResidentTypes ...");
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<ExamCenter> examCenter=txn.getExamCenterForProgram(programId);
		List<ExamCenterTO> examCenterList =ApplicationEditHelper.getInstance().convertexamCenterBOToTO(examCenter);
		log.info("Exit getResidentTypes ...");
		return examCenterList;
	}
	/**
	 * @return
	 */
	public List<NationalityTO> getNationalities() throws Exception{
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<Nationality> nationBOs=txn.getNationalities();
		ApplicationEditHelper helper= ApplicationEditHelper.getInstance();
		List<NationalityTO> nationTOs=helper.convertNationalityBOtoTO(nationBOs);
		return nationTOs;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ResidentCategoryTO> getResidentTypes() throws Exception {
		log.info("Enter getResidentTypes ...");
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<ResidentCategory> residentbos=txn.getResidentTypes();
		List<ResidentCategoryTO> residents=ApplicationEditHelper.getInstance().convertResidentBOToTO(residentbos);
		log.info("Exit getResidentTypes ...");
		return residents;
	}
	/**
	 * @return
	 */
	public List<IncomeTO> getIncomes() throws Exception {
		log.info("Enter getIncomes ...");
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<Income> incomebos=txn.getIncomes();
		List<IncomeTO> currencies=ApplicationEditHelper.getInstance().convertIncomeBOToTO(incomebos);
		log.info("Exit getIncomes ...");
		return currencies;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CurrencyTO> getCurrencies() throws Exception {
		log.info("Enter getCurrencies ...");
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
		List<Currency> currancybos=txn.getCurrencies();
		List<CurrencyTO> currencies=ApplicationEditHelper.getInstance().convertCurrencyBOToTO(currancybos);
		log.info("Exit getCurrencies ...");
		return currencies;
	}
	/**
	 * @param firstTo
	 * @param applicantCourse
	 */
	public void populatePreferenceTO(CandidatePreferenceTO prefTO,CourseTO courseTO) throws Exception {
		IApplicationEditTransaction txn= ApplicationEditTransactionimpl.getInstance();
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
	private List<ProgramTypeTO> removeDuplicateProgramTypes(List<ProgramTypeTO> prefProgramtypes) {
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
	 * @param applicationNumber
	 * @param applicationYear
	 * @return
	 */
	public String getInterviewDateOfApplicant(String applicationNumber,int year) throws Exception{
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		return txn.getInterviewDateOfApplicant(applicationNumber,year);
	}
	/**
	 * @param applicantDetail
	 * @param admForm
	 * @param b
	 * @return
	 */
	public boolean updateCompleteApplication(AdmApplnTO applicantDetail,ApplicationEditForm admForm, boolean isPresidance) throws Exception {
		AdmAppln admBO=ApplicationEditHelper.getInstance().getApplicantBO(applicantDetail,admForm,isPresidance);
		
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		boolean updated=txn.updateCompleteApplication(admBO,admForm.isAdmissionEdit());
		return updated;
	}
	
	
	
	//raghu added newly
	
	public void getPreference(ApplicationEditForm admForm, String ugId) throws Exception {
		//AdmissionFormForm admForm=(AdmissionFormForm)form;
		IApplicationEditTransaction txn= new ApplicationEditTransactionimpl();
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
	
	
	
	
	
	public void getPreferences(ApplicationEditForm admForm, String courseId) throws Exception {
		//AdmissionFormForm admForm=(AdmissionFormForm)form;
		IApplicationEditTransaction txn= new ApplicationEditTransactionimpl();
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
	
	
	
	public Map<Integer,String> get12thclassSubjects(String docName,String lang) throws Exception{
		return ApplicationEditTransactionimpl.getInstance().get12thclassSubject(docName,lang);
	}
	
	public Map<Integer,String> get12thclassLangSubjects(String docName,String lang) throws Exception{
		return ApplicationEditTransactionimpl.getInstance().get12thclassLangSubject(docName,lang);
	}
	
	
	
	//at
	public  boolean calculateIndexMark(List<AdmAppln> applicantDetails,ApplicationEditForm form) throws Exception{
		boolean iscreated=false;
		List<StudentIndexMark> marklist=null;
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		if(Integer.parseInt(form.getProgramTypeId())==1){
		 marklist=ApplicationRankHelper.getInstance().calculateMark(applicantDetails,form);
		}
		else if(Integer.parseInt(form.getProgramTypeId())==2){
			 marklist=ApplicationRankHelperforPg.getInstance().calculateMark(applicantDetails,form);
			}
		if(marklist.size()!=0){
		iscreated=txn.saveIndexMark(marklist);
		}
		return iscreated;
		
	}
	
	
	//at
	public  boolean calculateRank(ApplicationEditForm form) throws Exception{
		boolean iscreated=false;
		List<StudentRank> marklist=null;
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		if(Integer.parseInt(form.getProgramTypeId())==1){
		 marklist=ApplicationRankHelper.getInstance().calculateRank(form);
		}
		else if(Integer.parseInt(form.getProgramTypeId())==2){
			marklist=ApplicationRankHelperforPg.getInstance().calculateRank(form);
		}
		if(marklist.size()!=0){
			iscreated=txn.saveRank(marklist);
		}
		
		return iscreated;
		
	}
	
	
	//at allotting courses for all people based on student highest index mark priority
	
	public  boolean generateCourseAllotmentOnIndexMark(ApplicationEditForm form) throws Exception{
		boolean isGenerated=ApplicationRankHelper.getInstance().generateCourseAllotmentOnIndexMark(form);
		
		return isGenerated;
		
	}
	
	
	
	
	
	//at allotting courses for all people based on preferences priority
	public  boolean generateCourseAllotmentOnPreference(ApplicationEditForm form) throws Exception{
		
		boolean isGenerated=ApplicationRankHelper.getInstance().generateCourseAllotmentOnPreference(form);
		
		return isGenerated;
		
	}
	
	
	
	// get students for assign course list
	public List<StudentCourseAllotmentTo> getStudentDetails(ApplicationEditForm admForm) throws Exception {
		// TODO Auto-generated method stub
	
		
		
		
		IApplicationEditTransaction transaction = ApplicationEditTransactionimpl.getInstance();
		List<StudentCourseAllotment> studentList =transaction.getStudentDetails(admForm);
		List<StudentCourseAllotmentTo> list = ApplicationRankHelper.getInstance().copyBoToTO(studentList,new HashMap<Integer, Integer>());
		int halfLength = 0;
		int totLength = list.size(); 
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		admForm.setHalfLength(halfLength);
		
		
		
		return list;
		
	}
	
	
	
	
	public boolean assignStudentsToCourse(ApplicationEditForm admForm) throws Exception {
		 boolean isAdded=false;
		   IApplicationEditTransaction transaction =new ApplicationEditTransactionimpl();
		   if(admForm.getAllotedNo()==0){
			   isAdded=transaction.assignStudentsToCourse(admForm);
		   }else{
			   isAdded=transaction.assignStudentsToCourseMultipleTime(admForm);
		   }
			
			
			
			return isAdded;
	}
	
	
//at allotting courses multiple times for all people based on student highest index mark priority
	
	public  boolean generateCourseAllotmentOnIndexMarkMultipleTimes(ApplicationEditForm form) throws Exception{
		boolean isGenerated=ApplicationRankHelper.getInstance().generateCourseAllotmentOnIndexMarkMultipleTimes(form);
		
		return isGenerated;
		
	}
	
	
	
	// get students for assign course list
	public List<StudentCourseAllotmentTo> getStudentDetailsMultipleTime(ApplicationEditForm admForm) throws Exception {
		// TODO Auto-generated method stub
	
		
		
		
		IApplicationEditTransaction transaction = ApplicationEditTransactionimpl.getInstance();
		List<StudentCourseAllotment> studentList =transaction.getStudentDetailsMultipleTime(admForm);
		List<StudentCourseAllotmentTo> list = ApplicationRankHelper.getInstance().copyBoToTO(studentList,new HashMap<Integer, Integer>());
		int halfLength = 0;
		int totLength = list.size(); 
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		admForm.setHalfLength(halfLength);
		
		
		
		return list;
		
	}
	
	
	
	
//at allotting courses for all people based on Rank and Preference	
	public  boolean generateCourseAllotmentOnRankPreference(ApplicationEditForm form) throws Exception{
		boolean isGenerated=ApplicationRankHelper.getInstance().generateCourseAllotmentOnCourseRank(form);
		
		return isGenerated;
		
	}
	
	
//at allotting courses multiple times for all people based on student highest index mark priority
	
	public  boolean generateCourseAllotmentOnRankPreferenceMultipleTimes(ApplicationEditForm form) throws Exception{
		boolean isGenerated=ApplicationRankHelper.getInstance().generateCourseAllotmentOnCourseRankForMultipleAllotment(form);
		
		return isGenerated;
		
	}
	
	
	
	// get students for assign course list
	public List<StudentCourseAllotmentTo> getStudentDetailsForExam(ApplicationEditForm admForm) throws Exception {
		// TODO Auto-generated method stub
	
		
		IApplicationEditTransaction transaction = ApplicationEditTransactionimpl.getInstance();
		List<CandidatePreference> studentList =transaction.getStudentDetailsForExam(admForm);
		List<StudentCourseAllotmentTo> list = ApplicationRankHelper.getInstance().copyBoToTO1(studentList,new HashMap<Integer, Integer>());
		int halfLength = 0;
		int totLength = list.size(); 
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		admForm.setHalfLength(halfLength);
		
		
		
		return list;
		
	}
	
	
	public boolean assignStudentsForExam(ApplicationEditForm admForm) throws Exception {
		 boolean isAdded=false;
		   IApplicationEditTransaction transaction =new ApplicationEditTransactionimpl();
		   
			   isAdded=transaction.assignStudentsForExam(admForm);
		   
			
			
			
			return isAdded;
	}
	
	
	
	
	// get students for assign course list
	public List<CandidatePreferenceEntranceDetailsTO> getStudentDetailsForExamMarks(ApplicationEditForm admForm) throws Exception {
		// TODO Auto-generated method stub
	
		
		IApplicationEditTransaction transaction = ApplicationEditTransactionimpl.getInstance();
		List<CandidatePreferenceEntranceDetails> studentList =transaction.getStudentDetailsForExamMarks(admForm);
		List<CandidatePreferenceEntranceDetailsTO> list = ApplicationRankHelper.getInstance().copyBoToTO2(studentList);
		
		
		
		return list;
		
	}
	
	
	public boolean assignStudentsForExamMarks(ApplicationEditForm admForm) throws Exception {
		 boolean isAdded=false;
		   IApplicationEditTransaction transaction =new ApplicationEditTransactionimpl();
		   
			   isAdded=transaction.assignStudentsForExamMarks(admForm);
		   
			
			return isAdded;
	}
	

	public Map<String,String> get12thclassGroupSubjects(String language) throws Exception{
		return ApplicationEditTransactionimpl.getInstance().get12thclassGroupSubject(language);
	}
	
	
	
	
	//at
	public  boolean calculateGroupMarks(ApplicationEditForm form) throws Exception{
		boolean iscreated=false;
		IApplicationEditTransaction txn=ApplicationEditTransactionimpl.getInstance();
		List<EdnQualification> ednList=txn.getAdmSubList(form);
		List<StudentCommonRank> marklist=ApplicationRankHelper.getInstance().calculateGroupMarks(ednList,form);
		if(marklist.size()!=0){
		iscreated=txn.saveGroupMark(marklist);
		}
		return iscreated;
		
	}
	
	public Map<Integer,String> getDegClassGroupSubject(String sub) throws Exception{
		return ApplicationEditTransactionimpl.getInstance().getDegClassGroupSubject(sub);
	}
	
}
