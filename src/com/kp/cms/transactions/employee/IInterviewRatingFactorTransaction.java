package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.employee.InterviewRatingFactor;
import com.kp.cms.forms.employee.InterviewRatingFactorForm;

public interface IInterviewRatingFactorTransaction {

	public List<InterviewRatingFactor> getInterviewRatingList()throws Exception;

	public InterviewRatingFactor isDuplicated(InterviewRatingFactorForm interviewRatingForm) throws Exception;

	public boolean addInterviewRatingFactor(InterviewRatingFactor ratingFactor,
			String mode) throws Exception;

	public InterviewRatingFactor editInterviewRatingFactor(int id) throws Exception;

	public boolean deleteInterviewRatingFactor(int id, boolean activate,
			InterviewRatingFactorForm ratingFactorForm) throws Exception;

	public InterviewRatingFactor isDuplicatedDisplayOrder(
			InterviewRatingFactorForm interviewRatingForm)throws Exception;

	public InterviewRatingFactor isReactivate(
			InterviewRatingFactorForm interviewRatingForm)throws Exception;

	

}
