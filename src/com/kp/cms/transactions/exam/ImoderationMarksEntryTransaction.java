package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.ModerationMarksEntryForm;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;

public interface ImoderationMarksEntryTransaction {

	boolean checkFinalMark(ModerationMarksEntryForm moderationForm) throws ApplicationException;

	List<StudentFinalMarkDetails> getStudentDetails(ModerationMarksEntryForm moderationForm) throws ApplicationException;

	Map<Integer, ModerationMarksEntryBo> getModerationList(ModerationMarksEntryForm moderationForm) throws ApplicationException;
	
	boolean saveMarks(List<ModerationMarksEntryBo> boList) throws Exception;
	
	public List<StudentFinalMarkDetails> getStudentDetailsForRevaluation(NewExamMarksEntryForm newExamMarksEntryForm,List stuList) throws ApplicationException;

	public Map<Integer, ModerationMarksEntryBo> getModerationList(NewExamMarksEntryForm newExamMarksEntryForm,List stuList) throws ApplicationException ;
}
