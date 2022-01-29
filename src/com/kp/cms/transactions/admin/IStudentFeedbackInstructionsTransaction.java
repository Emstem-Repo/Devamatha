package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.StudentFeedbackInstructions;
import com.kp.cms.forms.admin.StudentFeedbackInstructionsForm;

public interface IStudentFeedbackInstructionsTransaction {

	public List<StudentFeedbackInstructions> getInstructions()throws Exception;

	public boolean checkDuplicateInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)throws Exception;

	public boolean addStuFeedbackInstructions( StudentFeedbackInstructions feedbackInstructions,String mode)throws Exception;

	public StudentFeedbackInstructions editFeedbackInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)throws Exception;

	public boolean deleteInstructions( StudentFeedbackInstructionsForm stuFeedbackInstructionsForm)throws Exception;

}
