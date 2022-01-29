package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.forms.admission.AdmissionStatusForm;



public interface IAdmissionStatusTransaction {
	/**
	 * 
	 * @param Passing application no. and gets the exactly matching record from AdmAppln table
	 * @return
	 */
	public List<AdmAppln> getDetailsAdmAppln(String applicationNo)throws Exception ;
	
	
	/**
	 * 
	 * @param applicationNo
	 * @param year 
	 * @return This method gets the interview result based on the application no.
	 * @throws Exception
	 */
	public AdmAppln getInterviewResult(String applicationNo, int year) throws Exception ;
	
	public List<InterviewCard> getStudentsList(int applicationNo ) throws Exception;
	
	public List<InterviewCard> getStudentAdmitCardDetails(int applicationNo, int interviewTypeId) throws Exception;


	public boolean getApplnAcknowledgement(String applicationNo, String dateOfBirth) throws Exception;


	public CandidatePGIDetails getCandidateDetails(AdmissionStatusForm admissionStatusForm) throws Exception;


	public List<InterviewCardHistory> getStudentAdmitCardHistoryDetails( int parseInt)throws Exception;
	
	public AdmAppln getApplicationDetailsForMemo(AdmissionStatusForm admForm) throws Exception;
}
