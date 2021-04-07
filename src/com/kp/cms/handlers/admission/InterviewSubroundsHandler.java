package com.kp.cms.handlers.admission;

import java.util.List;

import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.InterviewSubroundsForm;
import com.kp.cms.helpers.admission.CopyInterviewDefinitionHelper;
import com.kp.cms.helpers.admission.InterviewSubroundsHelper;
import com.kp.cms.to.admission.InterviewProgramCourseTO;
import com.kp.cms.to.admission.InterviewSubroundsTO;
import com.kp.cms.transactions.admission.IInterviewSubroundsTransaction;
import com.kp.cms.transactionsimpl.admission.InterviewSubroundsTransactionImpl;

public class InterviewSubroundsHandler {

	private static volatile InterviewSubroundsHandler interviewSubroundsHandler = null;

	private InterviewSubroundsHandler() {
	}

	public static InterviewSubroundsHandler getInstance() {
		if (interviewSubroundsHandler == null) {
			interviewSubroundsHandler = new InterviewSubroundsHandler();
		}
		return interviewSubroundsHandler;
	}
	
	/**
	 * @param id
	 * @return interviewSubroundList
	 * @throws Exception
	 */
	public List<InterviewSubroundsTO> getInterviewSubrounds(int id) throws Exception {
		IInterviewSubroundsTransaction transaction = new InterviewSubroundsTransactionImpl();
		InterviewSubroundsHelper interviewSubroundsHelper = new InterviewSubroundsHelper();

		List<InterviewSubRounds> interviewSubrounds = transaction.getInterviewSubrounds(id);

		//to copy the BO properties to TO
		List<InterviewSubroundsTO> interviewSubroundsList = interviewSubroundsHelper.convertBOstoTOs(interviewSubrounds);

		return interviewSubroundsList;
	}
	
	/**
	 * @param interviewSubroundsForm
	 * @param mode
	 * @return boolean value, true if added/updated successfully else false
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addInterviewSubround(InterviewSubroundsForm interviewSubroundsForm, String mode) throws DuplicateException, Exception {
		IInterviewSubroundsTransaction transaction = new InterviewSubroundsTransactionImpl();
		InterviewSubroundsHelper interviewSubroundsHelper = new InterviewSubroundsHelper();
		
		boolean originalNotChanged = false;
		int tempOrigInterviewTypeId =0;
		String tempSubroundName = null;
		String tempOrgSubroundName = null;
		int tempInterviewTypeId = Integer.parseInt(interviewSubroundsForm.getInterviewTypeId());
		if(interviewSubroundsForm.getOriginalInterviewTypeId() != null && !interviewSubroundsForm.getOriginalInterviewTypeId().isEmpty()){
			tempOrigInterviewTypeId = Integer.parseInt(interviewSubroundsForm.getOriginalInterviewTypeId());
		}
		if(interviewSubroundsForm.getSubroundName()!= null)
			tempSubroundName = interviewSubroundsForm.getSubroundName().trim();
		if(interviewSubroundsForm.getOriginalSubroundName()!=null)
			tempOrgSubroundName = interviewSubroundsForm.getOriginalSubroundName().trim();
		if ((tempInterviewTypeId == tempOrigInterviewTypeId) && (tempSubroundName.equals(tempOrgSubroundName))){ 
			originalNotChanged = true; 
		}
		
		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original changed
		}
		
		if (!originalNotChanged) {
			InterviewSubRounds duplicateInterviewSubround = interviewSubroundsHelper.convertBOs(interviewSubroundsForm, mode);
			duplicateInterviewSubround = transaction.isInterviewSubroundDuplicated(duplicateInterviewSubround);
			if ( duplicateInterviewSubround != null && duplicateInterviewSubround.getIsActive()){
				throw new DuplicateException();
			} else if ( duplicateInterviewSubround != null && !duplicateInterviewSubround.getIsActive()){
				interviewSubroundsForm.setActivationRefId(duplicateInterviewSubround.getId());
				throw new ReActivateException(); 
			}
		}
		
		 InterviewSubRounds interviewSubrounds = interviewSubroundsHelper.convertBOs(interviewSubroundsForm, mode);
		
		return transaction.addInterviewSubround(interviewSubrounds, mode, originalNotChanged);
	}
	
	/**
	 * @param id
	 * @param activate
	 * @param userId
	 * @return boolean value, true if deleted successfully else false
	 * @throws Exception
	 */
	public boolean deleteInterviewSubround(int id, Boolean activate, String userId) throws Exception {
		IInterviewSubroundsTransaction transaction = new InterviewSubroundsTransactionImpl();
		
		boolean isInterviewSubroundDeleted = false;
		if (transaction != null) {
			isInterviewSubroundDeleted = transaction.deleteInterviewSubround(id, activate, userId);
		}
		return isInterviewSubroundDeleted;
	}

	public List<InterviewSubroundsTO> getSubRoundDefinitionByYear(
			int currentYear, InterviewSubroundsForm copyForm) throws Exception {
		
		//getting the details of InterviewSubRounds BO from Impl.. 
		IInterviewSubroundsTransaction transaction= new InterviewSubroundsTransactionImpl();
		List<InterviewSubRounds> interviewSubRounds = transaction.getInterviewSubrounds(0);
		//conversion of BO to TO in helper by passing interviewSubRounds of type InterviewSubRounds..
		
		List<InterviewSubroundsTO> interviewSubRoundsTO = new InterviewSubroundsHelper().convertBOstoSubRoundsTOs(interviewSubRounds,currentYear);
		return interviewSubRoundsTO;
	}

	/**
	 * passing the form to helper to convert TO to BO
	 * @param copyForm
	 * @return
	 * @throws Exception
	 */
	public boolean copySubRoundDefinition(InterviewSubroundsForm copyForm) throws  Exception{
	boolean isCopied =false;
	List<InterviewSubRounds> intSubRounds = new InterviewSubroundsHelper().convertTOToBO(copyForm);
	IInterviewSubroundsTransaction transaction= new InterviewSubroundsTransactionImpl();
	if(intSubRounds != null && !intSubRounds.isEmpty()){
		isCopied = transaction.copyInterviewDefinition(intSubRounds);
	}
	return isCopied;
	}

	public List<InterviewSubroundsTO> getInterviewSubroundsList(int year) throws Exception{
		IInterviewSubroundsTransaction transaction = new InterviewSubroundsTransactionImpl();
		InterviewSubroundsHelper interviewSubroundsHelper = new InterviewSubroundsHelper();

		List<InterviewSubRounds> interviewSubrounds = transaction.getInterviewSubroundsList(year);

		//to copy the BO properties to TO
		List<InterviewSubroundsTO> interviewSubroundsList = interviewSubroundsHelper.convertBOstoTOs(interviewSubrounds);

		return interviewSubroundsList;
	}
}