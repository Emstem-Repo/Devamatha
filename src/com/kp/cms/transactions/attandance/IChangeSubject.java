package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.to.exam.KeyValueTO;

public interface IChangeSubject {
	public String subjectNameBySubjectCode(ChangeSubjectForm form)throws Exception;
	public List<Attendance> getClassBySubjectId(ChangeSubjectForm form)throws Exception;
	public List<ChangeSubjectTo> getSubjectNamesByClassNames(Set<String> s)throws Exception;
	public List<Attendance> SelectedSubjectList(List<Integer> idList)throws Exception;
	public boolean saveSubjectId(List<Attendance> boList,ChangeSubjectForm form)throws Exception;
}
