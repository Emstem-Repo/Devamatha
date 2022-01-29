package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.exam.MarksEntryCorrectionDetails;
import com.kp.cms.to.exam.NewStudentMarkCorrectionTo;

public interface INewStudentMarksCorrectionTransaction {

	void updateModifiedMarks(List<NewStudentMarkCorrectionTo> marksList,String userId,String marksCardNo) throws Exception;
	boolean saveMarksEntryCorrection(List<MarksEntryCorrectionDetails> boList) throws Exception;
	Integer getStudentId(String regNo, String schemeNo,String rollNo) throws Exception;
	
}
