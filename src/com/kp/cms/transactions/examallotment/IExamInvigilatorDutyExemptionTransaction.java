package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamInvigilatorDutyExemption;

public interface IExamInvigilatorDutyExemptionTransaction {

	Map<Integer, String> getExemptionMap()throws Exception;

	List<ExamInvigilatorDutyExemption> getSearchedInvigilators(
			StringBuilder query)throws Exception;

	List<ExamInvigilatorDutyExemption> getExamInvigilators(
			List<Integer> therecordsExists)throws Exception;

	boolean updateInvigilators(
			List<ExamInvigilatorDutyExemption> examInvigilatorsToUpdate)throws Exception;

	boolean saveInvigilators(
			List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions1)throws Exception;

}
