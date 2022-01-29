package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;

public interface IExamInternalMarkSupplementaryTransaction {

	public List<ExamInternalMarkSupplementaryDetailsBO> select_InternalSuppMarksToDisplay(int courseId, String rollNo,
			String regNo, int schemeNo, int examId) throws Exception;
	
	public List<ExamStudentOverallInternalMarkDetailsBO> select_OverAllInternalMarksToDisplay(int courseId, String rollNo,
			String regNo, int schemeNo, int examId) throws Exception;

	public void saveExamInternalMarkDetails(
			List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs) throws Exception;
}
