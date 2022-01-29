package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewProgramCourse;

public interface ICopyInterviewDefinitionTransaction {

	List<InterviewProgramCourse> getInterviewDefinitionByYear(int year) throws Exception;
	 
	boolean checkDuplicate(String query) throws Exception;

	boolean copyInterviewDefinition(List<InterviewProgramCourse> intPrgCourse) throws Exception;

}
