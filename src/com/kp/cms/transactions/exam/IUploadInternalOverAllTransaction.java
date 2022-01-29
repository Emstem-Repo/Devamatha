package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;

public interface IUploadInternalOverAllTransaction {

	Map<String, Integer> getStudentMap() throws Exception;

	Map<String, Integer> getclassMap() throws Exception;

	Map<String, Integer> getSubjectCodeMap() throws Exception;

	Map<String, Integer> getExamMap() throws Exception;

	boolean saveExamStudentOverallInternalMarkDetailsBOList(List<ExamStudentOverallInternalMarkDetailsBO> list,String action) throws Exception;

	boolean checkDuplicate(String studentId, String subjectId, String classId,String examId) throws Exception;

	boolean uploadESEOverAllData(List<ExamStudentFinalMarkDetailsBO> eseList) throws Exception ;

}
