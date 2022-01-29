package com.kp.cms.transactions.examallotment;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;

public interface IExamInvigilatorExemptionDatewise {
	
	Map<Integer, String> getExam(String academicYear)throws Exception;

	Map<Integer, String> geDeanaryMap()throws Exception;

	Map<Integer, String> getDepartmentMap() throws Exception;

	List<Users> getInvigilatorsList(String query)throws Exception;

	boolean saveInvigilators(List<ExamInviligatorExemptionDatewise> invigilatorsForExamBos) throws Exception;

	List<ExamInviligatorExemptionDatewise> getSearchedInvigilators(String query)throws Exception;

	boolean updateInvigilators(List<Integer> list)throws Exception;

	List<ExamInviligatorExemptionDatewise> getInvListAlreadyExempted(String query)throws Exception;
	
	public boolean deleteExemptedEntry(int Id, boolean activate,ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm)throws Exception;
	
	List<Integer> getExamInvigilatorsAvailableWhoAreExemted()throws Exception;

	Map<Integer, String> getsessionMap()throws Exception;

}
