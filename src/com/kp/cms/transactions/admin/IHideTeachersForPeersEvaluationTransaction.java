package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.HideTeachersForPeersEvaluation;
import com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm;

public interface IHideTeachersForPeersEvaluationTransaction {

	public boolean hideTeachers(HideTeachersForPeersEvaluation hideTeachersBo)throws Exception;

	public boolean duplicateCheck(String deleteQuery)throws Exception;

	public List<HideTeachersForPeersEvaluation> getHiddenTeachers( HideTeachersForPeersEvaluationForm hideTeacherform)throws Exception;

	public boolean deleteHiddenTeachers( String deleteQuery, HideTeachersForPeersEvaluationForm hideTeacherform)throws Exception;

	public String getDepartmentName(int departmentId)throws Exception;

}
