package com.kp.cms.transactions.teacherfeedback;

import java.util.List;

import com.kp.cms.bo.teacherfeedback.TeacherFeedbackInstructions;
import com.kp.cms.forms.teacherfeedback.TeacherFeedbackInstructionsForm;

public interface ITeacherFeedbackInstructionsTransaction {

	public List<TeacherFeedbackInstructions> getInstructions()throws Exception;

	public boolean checkDuplicateInstructions(TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)throws Exception;

	public boolean addTeacherFeedbackInstructions(TeacherFeedbackInstructions feedbackInstructions,String mode)throws Exception;

	public TeacherFeedbackInstructions editFeedbackInstructions(TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)throws Exception;

	public boolean deleteInstructions(TeacherFeedbackInstructionsForm teacherFeedbackInstructionsForm)throws Exception;

}
