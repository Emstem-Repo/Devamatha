package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;

public interface IUploadStudentBlockTransaction {

	Map<String, String> getStudentDetails(String query) throws Exception;

	boolean uploadData(List<ExamBlockUnBlockCandidatesTO> results, String user) throws Exception;

}
