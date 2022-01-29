package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;

public interface IInvigilatorsForExamTrans {

	Map<Integer, String> getExam(String academicYear)throws Exception;

	Map<Integer, String> geDeanaryMap()throws Exception;

	Map<Integer, String> getDepartmentMap() throws Exception;

	List<Users> getInvigilatorsList(StringBuilder query)throws Exception;

	boolean saveInvigilators(List<ExamInvigilatorAvailable> examInvigilatorAvailables) throws Exception;

	List<ExamInvigilatorAvailable> getSearchedInvigilators(StringBuilder query)throws Exception;

	boolean updateInvigilators(List<ExamInvigilatorAvailable> list)throws Exception;

	List<ExamInvigilatorAvailable> getExamInvigilators(
			List<Integer> therecordsExists)throws Exception;

	List<Integer> getExamInvigilatorsAvailableWhoAreExemted()throws Exception;
	
	public Map<Integer, String> getExamSession()throws Exception;

}
