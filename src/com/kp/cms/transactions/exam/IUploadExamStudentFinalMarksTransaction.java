package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;

public interface IUploadExamStudentFinalMarksTransaction {

	boolean saveExamStudentOverallInternalMarkDetailsBOList(List<ExamStudentFinalMarkDetailsBO> list, String action) throws Exception;

}
