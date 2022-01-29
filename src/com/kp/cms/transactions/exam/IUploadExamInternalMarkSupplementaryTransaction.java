package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;

public interface IUploadExamInternalMarkSupplementaryTransaction {

	public boolean saveExamInternalMarkSupplementaryDetailsBOList(List<ExamInternalMarkSupplementaryDetailsBO> list) throws Exception;

}
