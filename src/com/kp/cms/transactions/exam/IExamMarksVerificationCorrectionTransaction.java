package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamMarksVerificationEntryBO;

public interface IExamMarksVerificationCorrectionTransaction {
	public Integer getStudentId(String regNo) throws Exception;
	public List<ExamMarksVerificationEntryBO> getDataForQuery(String Query)throws Exception;
	public String getSubjectName(Integer subjectId)throws Exception;
	public boolean saveVerificationMarks(List<ExamMarksVerificationEntryBO> saveMarks)throws Exception;
	public String getClassName(Integer studentId)throws Exception;
	public String getStudentName(Integer studentId)throws Exception;
}
