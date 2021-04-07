package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.admin.SyllabusTrackerForSupplementaryForm;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;

public interface ISyllabusTrackerForSupplementaryTransaction {
	public Map<String, String>  gettingDeanaryList()throws Exception;
	public List<Object[]> getStudentDetails(SyllabusTrackerForSupplementaryForm form)throws Exception;
	public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusTrackerForSupplementaryForm form)throws Exception;
	public String getSubjectName(int csdId,SyllabusTrackerForSupplementaryForm form)throws Exception;
}
