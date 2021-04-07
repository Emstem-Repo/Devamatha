package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.StudentSearchForm;


/**
 * An interface for the interview selection transaction.
 */
public interface IStudentSearchTransaction {

	/**
	 * Get the list of students for interview selection.
	 * @param searchCriteria - Represents the search criteria.
	 * @return               - 
	 *                         List of personal data objects
	 * @throws Exception
	 */
	public List<PersonalData> getStudentSearch(String searchCriteria) throws ApplicationException;
	
	/**
	 * Persists the selected candidates list.
	 * @param selectedCandidates       - 
	 *                                   Represents the array of selected candidates.
	 * @param studentInterviewMap - 
	 *                                   Represents interview program course id.
	 * @param userId - Represents User Id.
	 * @throws Exception
	 */
	public void updateSelectedCandidatesList(String[] selectedCandidates ,Map<Integer, String> studentInterviewMap , String userId) throws Exception;
	
	
	/**
	 * Persists the selected bypass interview candidates list.
	 * @param studentSearchForm 
	 * 								- Represents the student search form.
	 * @throws ApplicationException
	 */
	public void updateBypassInterviewCandidatesList (StudentSearchForm studentSearchForm, boolean isSelected) throws ApplicationException;
	
	/**
	 * Get the selected students.
	 * @param searchCriteria -
	 *                          Represents the search criteria.
	 * @return List of selected students.
	 * @throws Exception
	 */
	public List getSelectedStudents(String searchCriteria) throws Exception;
	

	/**
	 * Removes the candidates from the database
	 * @param selectedCandidates - Represents the selected candidates to remove.
	 * @throws Exception
	 */
	public void removeSelectedCandidate(String[] selectedCandidates ) throws Exception ;
	
	
	/**
	 * Gets the Previous Round ID if available
	 * @param currentRoundId
	 * @return
	 * @throws Exception
	 */
	public int getpreviousRoundId(String currentRoundId) throws Exception;

	public AdmAppln getAdmAppln(String selectedCandidate) throws Exception;
	
	
}
