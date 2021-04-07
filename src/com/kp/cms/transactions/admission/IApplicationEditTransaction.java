package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePreference;
import com.kp.cms.bo.admin.CandidatePreferenceEntranceDetails;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocTypeExams;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Income;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.bo.admin.SeatAllocation;
import com.kp.cms.bo.admin.StudentCommonRank;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.bo.admin.StudentCourseAllotmentPrev;
import com.kp.cms.bo.admin.StudentIndexMark;
import com.kp.cms.bo.admin.StudentRank;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.ApplicationEditForm;
import com.kp.cms.bo.admin.AdmSubjectMarkForRank;
import com.kp.cms.to.admin.AdmSubjectMarkForRankTO;


public interface IApplicationEditTransaction {

	AdmAppln getApplicantDetails(String applicationNumber, int applicationYear) throws Exception;

	int getAdmittedThroughIdForInst(int instId) throws Exception;

	int getAdmittedThroughIdForNationality(int natId) throws Exception;

	int getAdmittedThroughIdForUniveristy(int uniId) throws Exception;

	int getAdmittedThroughResidentCategory(int resCategory) throws Exception;

	int getAdmittedThroughForInstNationality(int instId, int natId) throws Exception;

	int getAdmittedThroughForInstUni(int instId, int uniId) throws Exception;

	int getAdmittedThroughForInstRes(int instId, int resCategory) throws Exception;

	int getAdmittedThroughForNatUni(int natId, int uniId) throws Exception;

	int getAdmittedThroughForNatRes(int natId, int resCategory) throws Exception;

	int getAdmittedThroughForResUni(int resCategory, int uniId) throws Exception;

	int getAdmittedThroughForInstNationalityUni(int instId, int natId, int uniId) throws Exception;

	int getAdmittedThroughForInstNatRes(int instId, int natId, int resCategory) throws Exception;

	int getAdmittedThroughForNatUniRes(int natId, int uniId, int resCategory) throws Exception;

	int getAdmittedThroughForUniResInst(int uniId, int resCategory, int instId) throws Exception;

	int getAdmittedThroughForInstNationalityUniRes(int instId, int natId,int uniId, int resCategory) throws Exception;

	List<DocChecklist> getExamtypes(int id, Integer appliedYear) throws Exception;
	
	List<DocChecklist> getDoctypes(int id) throws Exception;
	

	List<DocTypeExams> getDocExamsByID(int id) throws Exception;

	List<DocChecklist> getNeededDocumentList(String courseID) throws Exception;

	List<ExamCenter> getExamCenterForProgram(int programId) throws Exception;

	List<Nationality> getNationalities() throws Exception;

	List<ResidentCategory> getResidentTypes() throws Exception;

	List<Income> getIncomes() throws Exception;

	List<Currency> getCurrencies() throws Exception;

	List<Course> getCourseForPreference(String valueOf) throws Exception;

	Set<Integer> getSubjectGroupByYearAndCourse(Integer appliedYear, int id) throws Exception;

	String getInterviewDateOfApplicant(String applicationNumber, int year) throws Exception;

	boolean updateCompleteApplication(AdmAppln admBO, boolean admissionEdit) throws Exception;
	
	List<InterviewSelectionSchedule> getInterviewSelectionScheduleByPgmId(int programId, int year)throws Exception;
	
	List<Object[]> getInterviewSelectionScheduleOnline(int programId, int year)throws Exception;
	
	List<Object[]> getInterviewSelectionScheduleOffline(int programId, int year)throws Exception;
	
	Object[] getInterviewSelectionVenueForApplicant(ApplicationEditForm admForm) throws ApplicationException;
	
	List<InterviewCard> getGeneratedCardDetails(String applicationNumber)throws Exception;

	List<AdmAppln> getApplicantDetail(ApplicationEditForm admForm) throws Exception;
	
	boolean saveIndexMark(List<StudentIndexMark> appDetails) throws Exception;
	
	Map<Integer, String> get12thclassSubject(String docName,String lang)throws Exception;
	
	Map<Integer, String> get12thclassLangSubject(String docName,String lang)throws Exception;
	
	List<Course> getCourseForPreferencesbyug(String ugId)throws Exception;

	boolean saveRank(List<StudentRank> markdList) throws Exception;

	boolean generateCourseAllotment(List<StudentCourseAllotment> markdList)	throws Exception;
	
	List<StudentRank> getStudentRank(ApplicationEditForm admForm,int admId) throws Exception;
	
	Map<Integer,Integer> getAllotedStudent(ApplicationEditForm admForm) throws Exception;

	List<SeatAllocation> getSeatAllocation(ApplicationEditForm admForm) throws Exception;

	List getAllotedStudentsOnCourse(ApplicationEditForm admForm)	throws Exception;
	
	List getIndexMarksOnCourse(ApplicationEditForm admForm)	throws Exception;
	
	List getRanksOnCourse(ApplicationEditForm admForm)	throws Exception;

	List getSeatCountOnCourse(String quata, String casteName, int courseid,ApplicationEditForm admForm)	throws Exception;

	StudentRank getRankOnAdmApplPreference(int admId,int preNo)	throws Exception;

	List getAdmApplonStudentIndexMark(ApplicationEditForm admForm) throws Exception;

	List<StudentCourseAllotment> getStudentDetails(ApplicationEditForm admForm) throws Exception;

	boolean assignStudentsToCourse(ApplicationEditForm admForm)throws Exception;

	List getAllotedStudentsOnCourseCheck(ApplicationEditForm admForm)throws Exception;
	
	Integer getAllotedNoOnCourse(ApplicationEditForm admForm)	throws Exception;

	List getAdmApplonStudentIndexMarkMultipleTimes(ApplicationEditForm admForm)	throws Exception;

	List getSeatCountOnCourseMultipleTime(String quata, String casteName,int courseid, ApplicationEditForm admForm) throws Exception;

	List getSeatCountOnCourseAllotedNo(String quata, String casteName,int courseid, ApplicationEditForm admForm) throws Exception;

	Map<Integer, Integer> getAllotedStudentMultipleTime(ApplicationEditForm admForm) throws Exception;

	List<StudentCourseAllotment> getStudentDetailsMultipleTime(ApplicationEditForm admForm) throws Exception;

	List<StudentRank> getStudentRankMultipleTime(ApplicationEditForm admForm,int admId) throws Exception;

	boolean assignStudentsToCourseMultipleTime(ApplicationEditForm admForm)	throws Exception;

	List<StudentRank> getAdmApplonStudentOnRankPreference(ApplicationEditForm admForm,int pref,int srank) throws Exception;

	List<StudentRank> getAdmApplonStudentOnRankPreferenceMultiple(ApplicationEditForm admForm, int pref, int srank) throws Exception;

	Integer getMaxRank(ApplicationEditForm admForm) throws Exception;
	
	List<CandidatePreference> getStudentDetailsForExam(ApplicationEditForm admForm) throws Exception;

	boolean assignStudentsForExam(ApplicationEditForm admForm)throws Exception;

	List<CandidatePreferenceEntranceDetails> getStudentDetailsForExamMarks(ApplicationEditForm admForm) throws Exception;

	boolean assignStudentsForExamMarks(ApplicationEditForm admForm)throws Exception;

	List<AdmSubjectMarkForRank> getAdmSubjectMarkList(int id) throws Exception;

	List<AdmSubjectMarkForRank> getAdmList(int id) throws Exception;

	CandidatePreferenceEntranceDetails getEntranceDetails(int id, int id2) throws Exception ;

	Map<String, String> get12thclassGroupSubject(String language) throws Exception;
	
	Map<Integer, Integer> getAllotedStudentMultipleTimeOnRank(ApplicationEditForm admForm,int pre,int rank) throws Exception;

	List<EdnQualification> getAdmSubList(ApplicationEditForm admForm) throws Exception;

	boolean saveGroupMark(List<StudentCommonRank> appDetails) throws Exception;
	
	List getGroupMarksOnCourse(ApplicationEditForm admForm)	throws Exception;

	List<StudentRank> getAdmApplonStudentOnCourseCategory(Integer courseId,	Integer year, String category) throws Exception;

	List<StudentRank> getAdmApplonStudentOnCourseCategoryForMultipleAllotment(Integer courseId, Integer year, String category) throws Exception;

	Map<Integer, StudentCourseAllotment> getallotmentMap(int pgmtype,int year)throws ApplicationException;

	Map<Integer, String> getDegClassGroupSubject(String sub) throws Exception;
	
	void savePrevCourseAllotment(List<StudentCourseAllotmentPrev> prevAllotmentList) throws Exception;
	
	
}
