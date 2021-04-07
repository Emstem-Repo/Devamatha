package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamRevaluationAppDetails;
import com.kp.cms.bo.exam.ExamRevaluationApplication;
import com.kp.cms.bo.exam.MarksEntry;
import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.forms.exam.RevaluationMarksUpdateForm;
import com.kp.cms.forms.exam.RevaluationOrRetotallingMarksEntryForm;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;
import com.kp.cms.to.exam.StudentMarksTO;

public interface IRevaluationMarksUpdateTransaction {
	public List<Object> getStudentsNewMarksList(RevaluationMarksUpdateForm form)throws Exception;
	public List<MarksEntry> getOldMarksList(int classid,int studentId,RevaluationMarksUpdateForm form)throws Exception ;
	public boolean thirdEvaluation(RevaluationMarksUpdateForm form)throws Exception;
	public boolean saveMarksEntryCorrection(List<MarksEntryCorrectionDetails> boList) throws Exception;
	public boolean updateModifiedMarksForRetotaling(RevaluationMarksUpdateForm form) throws Exception;
	public boolean updateModifiedMarksForRevaluation(RevaluationMarksUpdateForm form) throws Exception;
	public Double getMaxMarkOfSubject(RevaluationMarksUpdateForm form)throws Exception;
	public boolean updateModifiedMarksForRetotalingForUpdateAll(RevaluationMarksUpdateForm form) throws Exception;
	public StudentFinalMarkDetails getRegularFinalMarkDetailsBo(RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception;
	public void saveStudentFinalMarks(StudentFinalMarkDetails bo) throws Exception;
	public List getDataByQuery(String query) throws Exception;
	public Map<Integer, List<StudentMarksTO>> getStudentRegularMarks(
			int studentId, List<Integer> subjectIdList, int examId,
			int classId, RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception;
	public boolean saveRegularOverAll(List<StudentFinalMarkDetails> boList) throws Exception;
	public ExamRevaluationAppDetails getExamRevaluationAppDetails(RevaluationMarksUpdateForm revaluationMarksUpdateForm) throws Exception;
	public void updateRevaluationApllicationStatus(ExamRevaluationAppDetails revaluation) throws Exception;
	public List getDataByQueryForAllClasses(String query,
			List<Integer> classList) throws Exception;
	
	public Double getSubjectMaxMark(RevaluationMarksUpdateForm form,int courseId,int schemeNo,int subjectId)throws Exception;
}
