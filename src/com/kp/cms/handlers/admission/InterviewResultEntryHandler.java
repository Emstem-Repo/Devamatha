package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantRecommendor;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.helpers.admission.InterviewResultEntryHelper;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.admission.IInterviewResultsEntryTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewResultsEntryTransactionImpl;

public class InterviewResultEntryHandler {
	private static volatile InterviewResultEntryHandler interviewResultEntryHandler = null;

	private InterviewResultEntryHandler() {
	}

	public static InterviewResultEntryHandler getInstance() {
		if (interviewResultEntryHandler == null) {
			interviewResultEntryHandler = new InterviewResultEntryHandler();
		}
		return interviewResultEntryHandler;
	}

	/**
	 * @param applicationNumber
	 * @param applicationYear
	 * @param courseId
	 * @return applicantDetails
	 * @throws Exception
	 */
	public AdmApplnTO getApplicantDetails(String applicationNumber,
			int applicationYear, int courseId,HttpServletRequest request) throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		InterviewResultEntryHelper interviewResultEntryHelper = new InterviewResultEntryHelper();

		AdmAppln applicantDetails = transaction.getApplicantDetails(applicationNumber, applicationYear, courseId);

		//to copy the BO properties to TO
		AdmApplnTO appDetails = interviewResultEntryHelper.copyPropertiesValue(applicantDetails,request);

		return appDetails;
	}

	/**
	 * @param courseId
	 * @param applicationId
	 * @param appliedYear
	 * @return interview types depending on the selected course
	 * @throws Exception
	 */
	public Map<Integer, String> getInterviewTypesByCourse(int courseId, int applicationId, int appliedYear)  throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		
		return transaction.getInterviewTypesByCourse(courseId, applicationId, appliedYear);
	}
	
	/**
	 * @param programId
	 * @param applicationId
	 * @param appliedYear
	 * @return interview types depending on the selected program
	 * @throws Exception
	 */
	public Map<Integer, String> getInterviewTypesByProgram(int programId, int applicationId, int appliedYear) throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		
		return transaction.getInterviewTypesByProgram(programId, applicationId, appliedYear);
	}

	/**
	 * @return InterviewStatus
	 */
	public Map<Integer, String> getInterviewStatus() throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();		 

		return transaction.getInterviewStatus();
	}
	
	/**
	 * @return ReferredBy
	 */
	public Map<Integer, String> getReferredBy() throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		
		return transaction.getReferredBy();
	}
	
	/**
	 * @return grades
	 */
	public Map<Integer, String> getGrades() throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		
		return transaction.getGrades();
	}

	/**
	 * @param interviewResultEntryForm
	 * @return boolean value, true if added successfully else false
	 * @throws Exception
	 */
	public boolean addInterviewResult(InterviewResultEntryForm interviewResultEntryForm) throws Exception {	
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
				
		boolean isInterviewResultAdded = false;
		if (transaction != null) {
			InterviewResultEntryHelper interviewResultHelper = new InterviewResultEntryHelper();
			
			int selectedPrefId = 0;
			int referredById = 0;
			int interviewSubroundId = 0;
			int interviewTypeId = Integer.parseInt(interviewResultEntryForm.getInterviewTypeId());
			if(interviewResultEntryForm.getInterviewSubroundId() !=null &&  !interviewResultEntryForm.getInterviewSubroundId().isEmpty() && interviewResultEntryForm.getInterviewSubroundId().length() != 0) {
				interviewSubroundId = Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId());
			}	
			int interviewStatusId = Integer.parseInt(interviewResultEntryForm.getInterviewStatusId());
			int applicationId = Integer.parseInt(interviewResultEntryForm.getApplicationId());
			if( interviewResultEntryForm.getSelectedPrefId()!= null){
				selectedPrefId = Integer.parseInt(interviewResultEntryForm.getSelectedPrefId());
			}else{
				selectedPrefId = Integer.parseInt(interviewResultEntryForm.getCourseId());
			}
			if (interviewResultEntryForm.getReferredById() != null && !interviewResultEntryForm.getReferredById().trim().isEmpty()){
				referredById = Integer.parseInt(interviewResultEntryForm.getReferredById());
			}
			int gradeObtainedId = Integer.parseInt(interviewResultEntryForm.getGradeObtainedId());
			String comments = interviewResultEntryForm.getComments();
			List<ApplnDocTO> uploadDocList = interviewResultEntryForm.getApplicantDetails().getDocumentsList();
			String userId = interviewResultEntryForm.getUserId();
			
			List<ApplnDoc> uploadList = interviewResultHelper.getDocUploadBO(applicationId, uploadDocList);
			
			InterviewResult interviewResult = interviewResultHelper.getInterviewResultBO(applicationId, interviewTypeId, interviewStatusId, gradeObtainedId, comments,  selectedPrefId, userId, interviewSubroundId); 
				
			ApplicantRecommendor applicantRecommendor = interviewResultHelper.getApplicantRecommendorBO(applicationId, interviewTypeId, referredById, userId);
			
			if( referredById != 0){
				isInterviewResultAdded = transaction.addInterviewResult(uploadList, interviewResult, applicantRecommendor);
			}else{
				isInterviewResultAdded = transaction.addInterviewResult(uploadList, interviewResult);
			}
		}
		return isInterviewResultAdded;
	}

	/**
	 * 
	 * @param applicationNumber
	 * @param applicationYear
	 * @param courseId
	 * @return
	 * @throws Exception
	 */
	public AdmApplnTO getApplicantDetailsByPreference(String applicationNumber,
			int applicationYear, int courseId,HttpServletRequest request) throws Exception {

		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		InterviewResultEntryHelper interviewResultEntryHelper = new InterviewResultEntryHelper();

		AdmAppln applicantDetails = transaction.getApplicantDetailsByPreference(applicationNumber, applicationYear, courseId);

		//to copy the BO properties to TO
		AdmApplnTO appDetails = interviewResultEntryHelper.copyPropertiesValue(applicantDetails,request);

		return appDetails;
	}
	
	/**
	 * 
	 * @param applicationNumber
	 * @param applicationYear
	 * @return
	 * @throws ApplicationException
	 */
	public int getSelectedCourse(String applicationNumber, int applicationYear) throws ApplicationException {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		int selectedCourseId = 	transaction.getSelectedCourseId(applicationNumber,applicationYear);
		return selectedCourseId;
	}
	
	/**
	 * 
	 * @param mainroundId
	 * @param subroundId
	 * @return
	 * @throws Exception
	 */
	public int getInterviewersPerPanel(int mainroundId, int subroundId) throws Exception {
		IInterviewResultsEntryTransaction transaction = new InterviewResultsEntryTransactionImpl();
		int interviewersPerPanel = 	transaction.getInterviewersPerPanel(mainroundId,subroundId);
		return interviewersPerPanel;
	}
}