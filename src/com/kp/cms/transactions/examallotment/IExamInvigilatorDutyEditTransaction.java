package com.kp.cms.transactions.examallotment;

import java.util.List;

import com.kp.cms.bo.examallotment.ExamInviligatorDuties;

public interface IExamInvigilatorDutyEditTransaction {

	List<ExamInviligatorDuties> getInvigilators(String query)throws Exception;

	boolean updateInvigilator(ExamInviligatorDuties examInviligatorDuties1)throws Exception;

	ExamInviligatorDuties getExamInviligatorDutiesById(int id)throws Exception;

	List<ExamInviligatorDuties> getExamInvigilators(List<Integer> list)throws Exception;

	boolean update(List<ExamInviligatorDuties> examInviligatorDuties1, String string)throws Exception;

}
