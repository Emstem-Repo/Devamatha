package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.exam.ExamPublishExamResultsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.exceptions.ApplicationException;

public interface ICiaOverallReportTxn {
	public List<ExamStudentOverallInternalMarkDetailsBO> getStudentWiseOverAllExamMarkDetails( int studentId, int classId, int examId) throws ApplicationException;
	public int getExamIdByClassId(int classId) throws Exception;
	public int getClassId(int studentId) throws Exception ;
	public ExamPublishExamResultsBO getClassIds(int studentId, int curClassId)throws Exception;

}
