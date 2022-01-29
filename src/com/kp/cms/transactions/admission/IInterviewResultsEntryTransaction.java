package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantRecommendor;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.exceptions.ApplicationException;

/**
 * Interface for IInterviewResultsEntryTransactionImpl
 */
public interface IInterviewResultsEntryTransaction {

	public AdmAppln getApplicantDetails(String applicationNumber, int applicationYear, int courseId) throws Exception;

	public Map<Integer, String> getInterviewTypesByCourse(int courseId, int applicationId, int appliedYear)throws Exception;
	
	public Map<Integer, String> getInterviewTypesByProgram(int programId, int applicationId, int appliedYear)throws Exception;

	public Map<Integer, String> getInterviewStatus()throws Exception;
	
	public Map<Integer, String> getReferredBy()throws Exception;
	
	public Map<Integer, String> getGrades()throws Exception;

	public boolean addInterviewResult(List<ApplnDoc> uploadList, InterviewResult interviewResultDetail, ApplicantRecommendor applicantRecommendor)throws Exception;
	
	public boolean addInterviewResult(List<ApplnDoc> uploadList, InterviewResult interviewResultDetail)throws Exception;

	public AdmAppln getApplicantDetailsByPreference(String applicationNumber, int applicationYear, int courseId) throws ApplicationException;

	public int getSelectedCourseId(String applicationNumber, int applicationYear) throws ApplicationException;
	
	public int getInterviewersPerPanel(int mainroundId, int subroundId) throws Exception;
}
