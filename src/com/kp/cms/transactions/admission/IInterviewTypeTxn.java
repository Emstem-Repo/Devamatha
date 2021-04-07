package com.kp.cms.transactions.admission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.ApplicationException;

public interface IInterviewTypeTxn {
	/**
	 * Get all Interview Type list from the database.
	 * 
	 * @return List - Interview Type transaction List object
	 */
	public List getInterviewType() throws Exception;

	public boolean persistInterview(Interview interview) throws Exception;

	public int getCandidateCount(int courseID) throws Exception;

	public boolean updateAdmAppln(int courseID) throws Exception;

	public List getCandidates(int courseID, int programID, int years,
			ArrayList<Integer> interviewList,int noOfCandidates, int examCenterId) throws Exception;

	public InterviewProgramCourse getProgramDetails(int interviewTypeId)
			throws Exception;

	public boolean persistInterviewCard(InterviewCard[] interviewCard,
			String[] CandidateList) throws Exception;

	public List getStudentsList(int courseID, int years) throws Exception;

	public List getByStudentDetails(int applicationID, int interviewtypeid,
			int programTypeID, int programID, int courseID, int year,
			java.sql.Date birthDate, String firstName) throws Exception;

	public List getByBirthDate(java.sql.Date birthDate, int courseID)
			throws Exception;

	public List getByFirstName(String firstName, int courseID) throws Exception;

	public boolean updateInterviewCard(List interviewCardList) throws Exception;

	public boolean updateInterviewSelected(Set applNoSet) throws Exception;

	public List getInterviewCard(Set applNoSet) throws Exception;

	public String getIntCardContentFromTable(int interviewTypeid,
			String interviewType) throws Exception;

	public int getcourseID(int applnid) throws Exception;

	public List getInterviewCardForMail(Set applNoSet) throws Exception;
	// public InterviewProgramCourse getCourseId(int interviewTypeId);
	// public int getCourseId();
	public Integer getExamCenter(int programId, int examCenterId) throws Exception;
	public void updateSeatNo(Set applNoSet) throws ApplicationException;	
}
