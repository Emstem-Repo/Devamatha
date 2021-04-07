package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.forms.attendance.CopyClassTeacherForm;



public interface ICopyClassTeacherTransaction {
	public List<TeacherClass> getTeachers(CopyClassTeacherForm copyClassTeacherForm) throws Exception;
	public boolean copyTeachers(List<TeacherClass> teacher) throws Exception;
	public Map<String, Integer> getToYearClasses(CopyClassTeacherForm copyClassTeacherForm)throws Exception;

}
