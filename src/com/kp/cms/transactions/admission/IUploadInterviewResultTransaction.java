package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.forms.admission.InterviewResultEntryForm;

public interface IUploadInterviewResultTransaction {
	
	public Map<Integer, Integer> getAdmApplnDetails(int year, int courseId) throws Exception;
	
	public Map<String ,Integer> getInterviewStatusDetails() throws Exception;
	
	public Map<String ,Integer> getGradeDetails() throws Exception;
	
	public boolean checkDuplicate(int roundId, int admId, int subRoundId) throws Exception;
	
	public boolean checkDuplicateWithoutSubRound(int roundId, int admId) throws Exception;
	
	public int getInterviewResultId(int roundId, int admId, int subRoundId) throws Exception;
	
	public List<InterviewResultDetail> getInterviewResultDetailList(int iresultId, int gradeId) throws Exception;
	
	public List<Integer> getInterviewResultDetailId(int interviewResultId, int gradeId) throws Exception;
	
	public int  getInterviewResultIdWithoutSubround(int roundId, int admId) throws Exception;
	
	public boolean addUploadData(List<InterviewResult> interviewResult,List<StudentSpecializationPrefered> studentSpecializationBoList,String user) throws Exception;

	public int getInterviewersPerPanel(int mainroundId, int subroundId)	throws Exception;
	public boolean addEntranceUploadData(List<InterviewResult> interviewResult) throws Exception;
	public int getEntranceIntResultDetailId(int intId) throws Exception;

	public Map<Integer, Integer> getAdmApplnDetailsForSelectedCourses(int applicationYear, List<Integer> coursesList, InterviewResultEntryForm interviewResultEntryForm) throws Exception;

	public boolean checkSubroundData(InterviewResultEntryForm interviewResultEntryForm) throws Exception;
}