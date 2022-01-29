package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;

/**
 *  An interface for the final merit list transaction.
 */
public interface IFinalMeritListSearchTransaction {

	/**
	 * Get the List of students for final selection.
	 * @param finalMeritListForm - Represents the FinalMeritListForm object.
	 * @param isSelected - 
	 * @param isInterviewSelected
	 * @return - List of students.
	 * @throws Exception
	 */
	public List getFinalMeritListSearch(String finalMeritListqQuery) throws Exception;
	
	/**
	 * Persists the selected candidates.
	 * @param selectedCandidates - An array of selected candidates.
	 * @param isSelected
	 * @param userId - represents the user id.
	 * @throws Exception
	 */
	public void updateSelectCandidates(String[] selectedCandidates,
			boolean isSelected, boolean isApproved ,String userId,String mode) throws Exception;
	
	
	/**
	 * approves the selected candidates.
	 * @param selectedCandidates - An array of selected candidates.
	 * @param isSelected
	 * @param userId - represents the user id.
	 * @throws Exception
	 */
	public void approveSelectCandidates(String[] selectedCandidates,
			boolean isSelected, String userId) throws Exception;


	/**
	 * Get the Maximum intake of candidates for the selected course.
	 * @param courseId - Represents course id
	 * @return - an integer object of max intake of the course.
	 * @throws ApplicationException
	 */
	public int getMaxIntakeFromCourse(int courseId) throws ApplicationException;
	
	/**
	 * Get the Maximum intake of candidates for the selected program.
	 * @param programId - Represents program id
	 * @return - an integer object of max intake of the course.
	 * @throws ApplicationException
	 */
	public Map<Integer, Integer> getMaxIntakeFromProgram(int programId) throws ApplicationException;

	/**
	 * persists the selected preference.
	 * @param interviewResultEntryForm - Represents the InterviewResultEntryForm object.
	 * @throws ApplicationException
	 */
	public void updateSelectedPreference(
			InterviewResultEntryForm interviewResultEntryForm)
			throws ApplicationException;
}
