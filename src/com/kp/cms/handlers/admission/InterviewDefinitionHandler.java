package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.InterviewDefinitionForm;
import com.kp.cms.helpers.admission.InterviewDefinitionHelper;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.transactions.admission.IInterviewDefinitionTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewDefinitionTransactionImpl;

public class InterviewDefinitionHandler {
	
	private static volatile InterviewDefinitionHandler interviewDefinitionHandler = null;

	private InterviewDefinitionHandler() {
	}

	public static InterviewDefinitionHandler getInstance() {
		if (interviewDefinitionHandler == null) {
			interviewDefinitionHandler = new InterviewDefinitionHandler();
		}
		return interviewDefinitionHandler;
	}

	/**
	 * @param id
	 * @return interviewDefinitionList
	 * @throws Exception
	 */
	public List<InterviewProgramCourseTO> getInterviewDefinition(int id) throws Exception {
		IInterviewDefinitionTransaction transaction = new InterviewDefinitionTransactionImpl();
		InterviewDefinitionHelper interviewDefinitionHelper = new InterviewDefinitionHelper();

		List<InterviewProgramCourse> interviewDefinition = transaction.getInterviewDefinition(id);

		//to copy the BO properties to TO
		List<InterviewProgramCourseTO> interviewDefinitionList = interviewDefinitionHelper.convertBOstoTOs(interviewDefinition);

		return interviewDefinitionList;
	}
	
	/**
	 * @param interviewDefinitionForm
	 * @param mode
	 * @return boolean value, true if added/updated successfully else false
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addInterviewDefinition(InterviewDefinitionForm interviewDefinitionForm, String mode) throws DuplicateException, Exception {
		IInterviewDefinitionTransaction transaction = new InterviewDefinitionTransactionImpl();
		InterviewDefinitionHelper interviewDefinitionHelper = new InterviewDefinitionHelper();
		
		boolean originalNotChanged = false;
		int tempCourseId = Integer.parseInt(interviewDefinitionForm.getCourseId());
		int tempOrigCourseId = interviewDefinitionForm.getOriginalCourseId();
		int tempYear = Integer.parseInt(interviewDefinitionForm.getYear());
		int tempOrgYear = interviewDefinitionForm.getOriginalYear();
		String tempSequence = interviewDefinitionForm.getSequence();
		String tempOrgSequence = interviewDefinitionForm.getOriginalSequence();
		
		if ( (tempCourseId == tempOrigCourseId) && (tempYear == tempOrgYear) && (tempSequence.equals(tempOrgSequence))){ 
			originalNotChanged = true; 
		}
		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original changed
		}
		
		if (!originalNotChanged) {
			InterviewProgramCourse duplicateInterviewDefinition = interviewDefinitionHelper.convertBOs(interviewDefinitionForm, mode);
			duplicateInterviewDefinition = transaction.isInterviewDefinitionDuplicated(duplicateInterviewDefinition);
			if ( duplicateInterviewDefinition != null && duplicateInterviewDefinition.getIsActive()){
				throw new DuplicateException();
			} else if ( duplicateInterviewDefinition != null && !duplicateInterviewDefinition.getIsActive()){
				interviewDefinitionForm.setActivationRefId(duplicateInterviewDefinition.getId());
				throw new ReActivateException(); 
			}
		}
		
		 InterviewProgramCourse interviewDefinition = interviewDefinitionHelper.convertBOs(interviewDefinitionForm, mode);
		
		return transaction.addInterviewDefinition(interviewDefinition, mode, originalNotChanged);
	}
	
	/**
	 * @param id
	 * @param activate
	 * @param userId
	 * @return boolean value, true if deleted successfully else false
	 * @throws Exception
	 */
	public boolean deleteInterviewDefinition(int id, Boolean activate, String userId) throws Exception {
		IInterviewDefinitionTransaction transaction = new InterviewDefinitionTransactionImpl();
		
		boolean isInterviewDefinitionDeleted = false;
		if (transaction != null) {
			isInterviewDefinitionDeleted = transaction.deleteInterviewDefinition(id, activate, userId);
		}
		return isInterviewDefinitionDeleted;
	}

	/**
	 * @param year
	 * @return
	 * @throws Exception
	 */
	public List<InterviewProgramCourseTO> getInterviewDefinitionList(int year) throws Exception{
		IInterviewDefinitionTransaction transaction = new InterviewDefinitionTransactionImpl();
		InterviewDefinitionHelper interviewDefinitionHelper = new InterviewDefinitionHelper();

		List<InterviewProgramCourse> interviewDefinition = transaction.getInterviewDefinitionList(year);

		//to copy the BO properties to TO
		List<InterviewProgramCourseTO> interviewDefinitionList = interviewDefinitionHelper.convertBOstoTOs(interviewDefinition);

		return interviewDefinitionList;
	}
}