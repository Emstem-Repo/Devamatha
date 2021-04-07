package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;

public interface IUploadSupplementaryApllicationTransaction {

	public boolean saveSupplementaryDetails(List<ExamSupplementaryImprovementApplicationBO> examSupplementaryImprovementApplicationBOs) throws Exception;

}
