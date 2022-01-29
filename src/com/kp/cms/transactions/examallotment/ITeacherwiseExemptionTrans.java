package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.to.examallotment.TeacherwiseExemptionTo;

public interface ITeacherwiseExemptionTrans {

	List<ExamInviligatorExemptionDatewise> getTeachersList(String query)throws Exception;

	ExamInviligatorExemptionDatewise getExamInviligatorExemptionDatewise(int id)throws Exception;

	boolean update(
			ExamInviligatorExemptionDatewise examInviligatorExemptionDatewise1)throws Exception;

	boolean update(List<ExamInviligatorExemptionDatewise> list, String string)throws Exception;


}
