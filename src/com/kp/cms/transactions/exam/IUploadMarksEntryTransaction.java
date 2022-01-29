package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamMarksEntryUtilBO;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.UploadMarksEntryForm;
import com.kp.cms.to.exam.StudentMarksTO;

public interface IUploadMarksEntryTransaction 
{
	Map<String, Integer> getStudentMap(Integer programId,Integer courseId,Integer semister,Integer subjectId, String isPrevious,int examId,String examType,String subType,StringBuffer query) throws Exception;

	public boolean addMarks(List<ExamMarksEntryUtilBO> evaluator1Marks,List<ExamMarksEntryUtilBO> evaluator2Marks,Integer subjectId,String subjectType)throws Exception;

	public List<Integer> getEvaluatorIds(int subjectId,int courseId,int schemeNo,int academicYear)throws Exception;

	public ExamMarksEntryUtilBO getMarkEntryDetails(String examId, String studentId,Integer evaluatorId)throws Exception;

	public boolean checkStudentCourse(String regNo, String courseId)throws Exception;

	public boolean isDuplicateForMarkDetails(Integer marksId, Integer subjectId)throws Exception;

	boolean saveMarks(List<StudentMarksTO> finalList,UploadMarksEntryForm marksEntryForm) throws Exception;

	boolean checkMarksAlreadyExist(List<Integer> studentIdList, UploadMarksEntryForm marksEntryForm) throws Exception;

	Double getMaxMarkOfSubject(UploadMarksEntryForm marksEntryForm) throws Exception;
}
