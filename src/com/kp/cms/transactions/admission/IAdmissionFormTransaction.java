package com.kp.cms.transactions.admission;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.bo.exam.ExamTimeTableBO;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.AdmissionStatusForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.forms.sap.ExamRegistrationDetailsForm;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.InterviewSelectionScheduleTO;
import com.kp.cms.to.admission.InterviewTimeSelectionTO;
import com.kp.cms.to.admission.InterviewVenuSelectionTO;
/**
 * 
 * 
 * Interface for AdmissionFormTransactionImpl
 * 
 */
public interface IAdmissionFormTransaction {

	List<Currency> getCurrencies()throws Exception;
	List<Income> getIncomes()throws Exception;
	boolean persistCompleteApplicationData(Student newStudent, AdmissionFormForm admForm) throws Exception;
	List<ResidentCategory> getResidentTypes() throws Exception ;
	List<DocChecklist> getExamtypes(int courseId,int year)throws Exception;
//	boolean checkConsolidatedNeeded(DocChecklist doc,int courseId);
	List<DocChecklist> getNeededDocumentList(String courseID)throws Exception;
	List<Course> getCourseForPreference(String courseId)throws Exception;
	boolean checkApplicationNoUniqueForYear(int applnNo, int year)throws Exception;
	List<Nationality> getNationalities() throws Exception;
	String getCurrentChallanNo()throws Exception;
	boolean updateLatestChallanNo(String latestRefNo) throws Exception;
	List<CoursePrerequisite> getCoursePrerequisites(int courseID) throws Exception;
	boolean checkApplicationNoInRange(int applicationNumber,
			boolean onlineApply, int appliedyear, String courseId) throws Exception;
	String getGeneratedOnlineAppNo(int courseID, int year,boolean isOnline)throws Exception;
	boolean updateCompleteApplication(AdmAppln admBO,boolean admissionForm)throws Exception;
	public boolean checkApplicationNoConfigured(int appliedyear, String courseId) throws Exception;
	boolean checkSeatAllocationExceeded(int parseInt, int courseID)throws Exception;
	List<Integer> checkApplicationNumberCancel(String searchCriteria) throws Exception;
	int getStudentId(int appNumber, int admissionYear) throws Exception;
	boolean updateApplicationNumberCancel(int appNumber, int admissionYear, String remarks,String cancelDate, String lastModifiedBy) throws Exception;
	boolean updateStudentRecord(int studentId,Boolean removeRegNo) throws Exception;

	AdmAppln getApplicantDetails(String applicationNumber, int applicationYear,boolean admissionForm) throws Exception;
	AdmAppln getApprovalApplicantDetails(String applicationNumber, int applicationYear,boolean approval) throws Exception;
	boolean updateApproval(int appNO, int appYear,String appRemark,String admthrid) throws Exception;
	boolean checkDuplicatePrerequisite(int examYear, String rollNo)throws Exception;
	boolean checkPaymentDetailsDuplicate(String challanNo, String journalNo,
			Date applicationDate, int yeare) throws Exception;
	boolean checkValidOfflineNumber(int applicationNumber, int appliedyear) throws Exception;
	List<EligibilityCriteria> getEligibiltyCriteriaForCourse(int courseId, Integer year) throws Exception;
	boolean checkAdmittedOrNot(int applnNo, int id, Integer appliedYear) throws Exception;
	AdmAppln getApplicationDetails(String applicationNumber, int applicationYear) throws Exception;
	public boolean createNewApplicant(Student admBO, AdmissionFormForm admForm) throws Exception;
	public List<DocTypeExams> getDocExamsByID(int id) throws Exception;
	int getAppliedYearForProgram(int programId) throws Exception;
	public List<ExamCenter> getExamCenterForProgram(int programId) throws Exception;
	
	Map<String, Integer> getCourseMapByInputId(String searchQuery) throws Exception;
	String getInterviewDateOfApplicant(String applicationNumber,int year) throws Exception;
	public int getAdmittedThroughIdForInst(int instId) throws Exception;
	public int getAdmittedThroughIdForNationality(int natId) throws Exception;
	public int getAdmittedThroughIdForUniveristy(int uniId) throws Exception;
	public int getAdmittedThroughResidentCategory(int resId) throws Exception;
	public int getAdmittedThroughForInstNationality(int instId, int natId ) throws Exception;
	public int getAdmittedThroughForInstNationalityUni(int instId, int natId, int uniId ) throws Exception;
	public int getAdmittedThroughForInstNationalityUniRes(int instId, int natId, int uniId, int resId ) throws Exception;
	public int getAdmittedThroughForOtherThanIndia() throws Exception;
	public int getAdmittedThroughForInstUni(int instId, int uniId ) throws Exception;
	public int getAdmittedThroughForInstRes(int instId, int resId ) throws Exception;
	public int getAdmittedThroughForNatUni(int natId, int uniId ) throws Exception;
	public int getAdmittedThroughForNatRes(int natId, int resId ) throws Exception;
	public int getAdmittedThroughForResUni(int resId, int uniId ) throws Exception;
	public int getAdmittedThroughForInstNatRes(int instId, int natId, int resId ) throws Exception;
	public int getAdmittedThroughForNatUniRes(int natId, int uniId, int resId ) throws Exception;
	public int getAdmittedThroughForUniResInst(int uniId, int resId, int instId ) throws Exception;
	Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear, int id) throws Exception;
	public AdmAppln getListOfSubmittedDetails(int applicationNo, int applicationYear)throws Exception;
	public int getProgrameIdForCourse(int courseId)throws Exception;
	public List<DocChecklist> getRequiredDocsForCourseAndProgram(int id,
			int programId, int applicationYear)throws Exception;
	public  List<ApplnDoc> getListOfDocuments(String applicationNumber, int applicationYear)throws Exception;
	public AdmAppln checkApplicationNumberForCancel(String searchCriteria)throws Exception;
	Map<String, Integer> getCourseMap() throws Exception;
	public List getApplicantStatusDetails(String applicationNumber, int applicationYear, int courseId) throws Exception;
	Map<String, String> getMonthMap()throws Exception;
	Map<String, String> getYear()throws Exception;
	Map<String, String> getYearByMonth(String month)throws Exception;
	List<GenerateMail> getMails() throws Exception;
	String generateCandidateRefNo(CandidatePGIDetails bo) throws Exception;
	boolean updateReceivedStatus(CandidatePGIDetails bo,AdmissionFormForm admForm) throws Exception;
	List<CandidatePGIDetails> getPaidCandidateDetails(String query) throws Exception;
	List<ApplicantFeedback> getApplicantFeedback() throws Exception;
	boolean checkWorkExperianceMandatory(String courseId) throws Exception;
	List<CandidatePrerequisiteMarks> getPrerequisiteMarks(String applicationNumber) throws Exception;
	public String getCandidatePGIDetails(int admApplnId) throws Exception;
	CandidatePGIDetails getCandidateDetails(int admApplnId) throws Exception;
	List<CandidatePGIDetails> getStudentMails() throws Exception;
	List<PhdDocumentSubmissionSchedule> DocumentPendingMail(String mode) throws Exception;
	List<PhdDocumentSubmissionSchedule> PendingDocumentSearch(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;
	List<Object[]> getExamTimeTableDetailsForPreviousDay( Date previousDayDate)throws Exception ;
	List<ExamValuationScheduleDetails> getvaluationScheduleDeatils( int examId,List<Integer> subjectId)throws Exception;
	Map<Integer, Map<Integer, Integer>> getNoOfScriptsByExamIdSubjId( int examId, List<Integer> subjectIds)throws Exception;
	List<Object[]> SendMailForAm(int year)throws Exception;
	List<Object[]> SendMailForPm(int year) throws Exception;
	
	List<Object[]> getInterviewSelectionScheduleOnline(int programId, int year)throws Exception;
	List<Object[]> getInterviewSelectionScheduleOffline(int programId, int year)throws Exception;
	//List<InterviewVenueSelection> getInterviewVenuSelection(int programId)throws Exception;
	//List<InterviewTimeSelection> getInterviewTimeSelection(int programId)throws Exception;
	public List<Object[]> getPreviousInterviewSelectionSchedule(int programId, int year, boolean isOnlineApply)throws Exception;
	public int getExamCenterFromInterviewVenue(String interviewVenue, AdmissionFormForm admForm)throws Exception;
	
	public InterviewCardTO getInterviewScheduleDetails(AdmissionFormForm admForm)throws Exception;
	
	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO,AdmissionFormForm admForm) throws Exception;

	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception;
	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user,AdmissionFormForm admForm, Integer interviewPgmCourse) throws Exception;
	
	public void getDateFromSelectionProcessId(String InterviewSelectionDate,AdmissionFormForm admForm)throws Exception;
	
	public String getAdmApplnId(AdmissionFormForm admForm) throws Exception;
	
	public String getCouseNameByCourseId(String courseID)throws Exception;
	public String getResidenceNameByResidanceId(String courseID)throws Exception;
	
	public List<InterviewSelectionSchedule> getInterviewSelectionScheduleByPgmId(int programId, int year)throws Exception;
	
	public Integer getInterViewPgmCourse(AdmissionFormForm admForm)throws Exception; 
	
	Map<Integer, String> getParishByDiose(String month)throws Exception;
	
	List<Course> getCourseForPreferencesbyug(String ugId)throws Exception;
	Map<Integer, String> get12thclassSubject(String docName,String lang)throws Exception;
	Map<Integer, String> get12thclassLangSubject(String docName,String lang)throws Exception;
	String getProgramId(int courseId) throws Exception;
	//vibin
	List<AdmSubjectMarkForRank> get12thsubjmark(int id, String doctype) throws Exception;
	List<CandidatePreference> getpreferences(int id) throws ApplicationException;
	StudentCourseAllotment getallotmentdetails(int id, int mxal) throws Exception;
	Integer getmaxallotment(int i,int appliedYear) throws Exception;
	Map<Integer, String> get12thclassSub(String docName,String sub)throws Exception;
	StudentRank getrankdetails(int id, int id2) throws ApplicationException;
	
	int getAppliedYearForProgramType(int programtypeId) throws Exception;
	Map<Integer, String> getStreamMap()throws Exception;

	//this is for optional
	Map<Integer, String> get12thclassSub1(String docName,String sub)throws Exception;
	
	// vinodha
	public StudentIndexMark getStudentIndexMarks(AdmissionStatusForm admForm) throws Exception;
	public CandidatePreference getApplicationDetailsForMemoFromAdmAppln(AdmissionStatusForm admForm) throws Exception;
	public String getPaymentStatus(String applicationNo) throws Exception;
	boolean updatePendingOnlineApp(AdmissionFormForm admForm)  throws Exception;
	Boolean getApplicantDetails(AdmissionFormForm admForm) throws Exception;

}
