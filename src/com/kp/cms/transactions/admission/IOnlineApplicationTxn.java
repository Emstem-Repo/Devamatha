package com.kp.cms.transactions.admission;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.ActionMessages;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantFeedback;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CandidatePrerequisiteMarks;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.GenerateMail;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentOnlineApplication;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;
import com.kp.cms.to.admission.InterviewCardTO;

@SuppressWarnings("rawtypes")
public interface IOnlineApplicationTxn {
	
	public boolean submitBasicPage(Student std,OnlineApplicationForm admForm) throws Exception;
	
	public List<AdmAppln> getApplicantSavedDetails(OnlineApplicationForm admForm) throws Exception;
	
	public AdmAppln getAppliedApplnDetails(OnlineApplicationForm admForm) throws Exception;
	
	public Student getStudentDetailsFromAdmAppln(int applnId)throws Exception;
	
	public boolean submitCurrentPageDetails(OnlineApplicationForm admForm) throws Exception;
	
	public boolean createNewApplicant(Student admBO, OnlineApplicationForm admForm,String saveMode) throws Exception;
	
	public boolean savePayment(AdmAppln admAppln,OnlineApplicationForm admForm, ActionMessages errors) throws Exception;
	
	String getGeneratedOnlineAppNo(int courseID, int year,boolean isOnline)throws Exception;
	
	//public boolean savePrerequisitesPage(OnlineApplicationForm admForm,List<CandidatePrerequisiteMarks> prerequisitesList) throws Exception;
	
	List<Currency> getCurrencies()throws Exception;
	
	List<Object[]> getIncomes()throws Exception;
	
	boolean persistCompleteApplicationData(Student newStudent, OnlineApplicationForm admForm) throws Exception;
	
	List<ResidentCategory> getResidentTypes() throws Exception ;
	
	List<DocChecklist> getExamtypes(int courseId,int year)throws Exception;
	
	List<DocChecklist> getNeededDocumentList(String courseID)throws Exception;
	
	List<Course> getCourseForPreference(String courseId)throws Exception;
	
	boolean checkApplicationNoUniqueForYear(int applnNo, int year)throws Exception;
	
	List<Nationality> getNationalities() throws Exception;
	
	//String getCurrentChallanNo()throws Exception;
	
	//boolean updateLatestChallanNo(String latestRefNo) throws Exception;
	
	//List<CoursePrerequisite> getCoursePrerequisites(int courseID) throws Exception;
	
	boolean checkApplicationNoInRange(int applicationNumber,
			boolean onlineApply, int appliedyear, String courseId) throws Exception;
	
	boolean updateCompleteApplication(AdmAppln admBO,boolean admissionForm)throws Exception;
	
	public boolean checkApplicationNoConfigured(int appliedyear, String courseId) throws Exception;
	
	int getStudentId(int appNumber, int admissionYear) throws Exception;
	
	AdmAppln getApplicantDetails(String applicationNumber, int applicationYear,boolean admissionForm) throws Exception;
	
    //boolean checkDuplicatePrerequisite(int examYear, String rollNo)throws Exception;
	
    boolean checkPaymentDetailsDuplicate(String challanNo, String journalNo,
			Date applicationDate, int yeare) throws Exception;
	
   
    //boolean checkAdmittedOrNot(int applnNo, int id, Integer appliedYear) throws Exception;
	
    AdmAppln getApplicationDetails(String applicationNumber, int applicationYear, String regNO) throws Exception;
	
    public List<DocTypeExams> getDocExamsByID(int id) throws Exception;
	
    int getAppliedYearForCourse(int courseId) throws Exception;
	
    Map<String, Integer> getCourseMapByInputId(String searchQuery) throws Exception;
	
    
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
	
	
	public int getProgrameIdForCourse(int courseId)throws Exception;
	
	public List<DocChecklist> getRequiredDocsForCourseAndProgram(int id,
			int programId, int applicationYear)throws Exception;
	
	public  List<ApplnDoc> getListOfDocuments(String applicationNumber, int applicationYear)throws Exception;
	
	//public AdmAppln checkApplicationNumberForCancel(String searchCriteria)throws Exception;
	
	Map<String, Integer> getCourseMap() throws Exception;
	
	//public List getApplicantStatusDetails(String applicationNumber, int applicationYear, int courseId) throws Exception;
	
	Map<String, String> getMonthMap()throws Exception;
	
	Map<String, String> getYear()throws Exception;
	
	Map<String, String> getYearByMonth(String month)throws Exception;
	
	String generateCandidateRefNo(CandidatePGIDetails bo) throws Exception;
	
	boolean updateReceivedStatus(CandidatePGIDetails bo,OnlineApplicationForm admForm) throws Exception;
	
	List<CandidatePGIDetails> getPaidCandidateDetails(String query) throws Exception;
	
//	boolean checkWorkExperianceMandatory(String courseId) throws Exception;
//	
//	List<CandidatePrerequisiteMarks> getPrerequisiteMarks(String applicationNumber) throws Exception;
	
	public String getCandidatePGIDetails(int admApplnId) throws Exception;
	
	CandidatePGIDetails getCandidateDetails(int admApplnId) throws Exception;
	
	public String getAdmApplnId(OnlineApplicationForm admForm) throws Exception;
	
	public String getCouseNameByCourseId(String courseID)throws Exception;
	
	public String getResidenceNameByResidanceId(String courseID)throws Exception;
	
	LinkedList<AdmAppln> getAdmApplnList(String uniqueId) throws Exception;
	
	StudentOnlineApplication getUniqueId(OnlineApplicationForm admForm, String academicYear)throws Exception;
	
	AdmAppln getAdmApplnDetails(String applnNo)throws Exception;
	
	CandidatePGIDetails getCandiadatePGIDetailsById(int candidatePGIId)throws Exception;
	
	public OnlineApplicationForm getUniqueId(OnlineApplicationForm admForm)throws Exception;
	
	
	public StudentOnlineApplication checkUniqueIdIsExists(String mobile,String dob, String academicYear)throws Exception;
	
	
	//public CandidatePrerequisiteMarks getCandidatePreRequisiteMarks(int admAppId)throws Exception;
	
	public boolean checkCourseInDraftMode(String uniqueId, int courseId)throws Exception;
	
	//raghu
	boolean saveChallanDetail(AdmAppln admAppln, OnlineApplicationForm admForm) throws Exception;
	
	boolean savePaymentSuccessPage(AdmAppln admAppln,OnlineApplicationForm admForm) throws Exception;
	
	String saveCompleteApplicationGenerateNo(int courseID, int appliedyear,
			boolean isOnline, AdmAppln admAppln, OnlineApplicationForm admForm,
			ActionMessages errors) throws Exception;

	String saveCompleteApplicationGenerateNoWithNoMoreEdit(int courseID,
			int appliedyear, boolean isOnline, AdmAppln admAppln,
			OnlineApplicationForm admForm, ActionMessages errors)
			throws Exception;
	
	
	java.sql.Timestamp getDateofApp(String uniqueId) throws Exception;
	
	public List<CandidatePGIDetails> getAllPendingTransactionStatausOfCandidate(OnlineApplicationForm admForm) throws Exception;
	
	public boolean updatePendingOnlineApp(OnlineApplicationForm admForm, String transactionId, String transactionAmount, String paymentMode, String transactionDate, String newTransactionStatus) throws Exception;

	public String getcandidatepgi(String uniqueId)throws Exception;

	public List<Object[]> getAllCourses(int ptype) throws ApplicationException;
	public List<Object[]> getAllCourses() throws ApplicationException;

	public boolean updateReceivedStatus1(OnlineApplicationForm admForm)throws Exception;

	public String getIsCommunityQuota(String admApplnId) throws ApplicationException;
	public String getIsMngQuota(String admApplnId) throws ApplicationException;

	public AdmAppln getApplicantDet(OnlineApplicationForm admForm) throws Exception;

}
