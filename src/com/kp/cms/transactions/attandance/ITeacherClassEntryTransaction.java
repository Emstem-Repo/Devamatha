package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.TeacherClassSubject;
import com.kp.cms.forms.attendance.TeacherClassEntryForm;

public interface ITeacherClassEntryTransaction {

	List<Object> getDetails(int currentYear, TeacherClassEntryForm teacherForm) throws Exception;
	
	List<Object> getUserDetails(int currentYear, TeacherClassEntryForm teacherForm) throws Exception;
	
	List<Object> getGuestDetails(int currentYear, TeacherClassEntryForm teacherForm) throws Exception;

	boolean deleteTeacherClassEntry(int id,Boolean activaate,TeacherClassEntryForm teacherClassEntryForm) throws Exception;

	List getTeacherClassDetails(int id) throws Exception;

	boolean update(TeacherClassSubject tcs)throws Exception;

	List<TeacherClassSubject> getDuplicateList(String query)throws Exception;

	boolean getDuplicates(TeacherClassEntryForm teacherForm) throws Exception;

	List<Object[]> getTeacherDepartmentNames() throws Exception;

	public TeacherClassSubject getReactivate(TeacherClassEntryForm teacherForm)  throws Exception;

}
