package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.DuplicateException;

/**
 * Interface for IInterviewDefinitionTransactionImpl
 */
public interface IInterviewDefinitionTransaction {
	
	public List<InterviewProgramCourse> getInterviewDefinition(int id) throws Exception;
	
	public boolean addInterviewDefinition(InterviewProgramCourse interviewProgramCourse, String mode, Boolean originalChangedNotChanged) throws DuplicateException, Exception;
	
	public boolean deleteInterviewDefinition(int id, Boolean activate, String userId) throws Exception;
	
	public InterviewProgramCourse isInterviewDefinitionDuplicated(InterviewProgramCourse oldInterviewProgramCourse) throws Exception;
	public List<InterviewProgramCourse> getInterviewTypebyProgram(int id, int year);

	public List<InterviewProgramCourse> getInterviewDefinitionList(int year) throws Exception;
}