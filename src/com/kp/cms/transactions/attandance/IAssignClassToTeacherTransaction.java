package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.TeacherClass;
import com.kp.cms.forms.attendance.AssignClassToTeacherForm;

public interface IAssignClassToTeacherTransaction {

	public boolean addTeachers(AssignClassToTeacherForm assignClassTeacherForm) throws Exception;

	public Map<Integer, String> getClassesByYear(int year)throws Exception;

	public boolean getDuplicates(String query, AssignClassToTeacherForm assignClassTeacherForm) throws Exception;

	public List<TeacherClass> getDetails(AssignClassToTeacherForm assignClassTeacherForm) throws Exception;
	
	public List<TeacherClass> getDetailsByYear(AssignClassToTeacherForm assignClassTeacherForm, int Year) throws Exception;

	public boolean deleteTeachers(int id)throws Exception;

	public TeacherClass getClassEntrydetails(int id)throws Exception;

	public boolean updateClassEntry(TeacherClass teacherClass)throws Exception;

	

}
