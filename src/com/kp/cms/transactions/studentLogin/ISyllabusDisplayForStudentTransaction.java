package com.kp.cms.transactions.studentLogin;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.forms.studentLogin.SyllabusDisplayForStudentForm;

public interface ISyllabusDisplayForStudentTransaction {
	public List<Object[]>  getConsolidateMarksCardBo(SyllabusDisplayForStudentForm form )throws Exception;
	public Student getStudentDetails(String registerNumber)throws Exception;
	public List<Object[]> getCurrentStudyingBatchjoiningYear(SyllabusDisplayForStudentForm form)throws Exception;
	public String getSubjectName(int csdId,SyllabusDisplayForStudentForm form)throws Exception;

}
