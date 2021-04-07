package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.exceptions.DuplicateException;

/**
 * Interface for IInterviewSubroundsTransactionImpl
 */
public interface IInterviewSubroundsTransaction {

	public List<InterviewSubRounds> getInterviewSubrounds(int id) throws Exception;
	
	public boolean addInterviewSubround(InterviewSubRounds interviewSubrounds, String mode, Boolean originalChangedNotChanged) throws DuplicateException, Exception;
	
	public boolean deleteInterviewSubround(int id, Boolean activate, String userId) throws Exception;
	
	public InterviewSubRounds isInterviewSubroundDuplicated(InterviewSubRounds oldInterviewSubrounds) throws Exception;

	public boolean copyInterviewDefinition(List<InterviewSubRounds> intSubRounds) throws Exception;

	public boolean checkDuplicate(String query) throws Exception;

	public String getInterviewProgramCourseId(String courseId, int year,String sequence) throws Exception;

	public List<InterviewSubRounds> getInterviewSubroundsList(int year) throws Exception;
}
