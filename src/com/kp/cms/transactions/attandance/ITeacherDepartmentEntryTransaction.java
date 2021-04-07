package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.TeacherDepartment;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.UsersUtilBO;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.forms.attendance.TeacherDepartmentEntryForm;

public interface ITeacherDepartmentEntryTransaction {
public boolean addTeacherDepartment(TeacherDepartment teacherDepartment)throws Exception;
public List<TeacherDepartment> getTeacherDepartments()throws Exception;	
public boolean checkDuplicate(TeacherDepartmentEntryForm teacherDepartmentForm)throws Exception;
public TeacherDepartment getTeacherDepartmentById(int id)throws Exception;
public boolean updateTeacherDepartment(TeacherDepartment teacherDep)throws Exception;	
public boolean deleteTeacherDepartment(int id)throws Exception;
public List<Object[]> getTeacherDepartmentsName()throws Exception;
public List<Object[]> getTeacherDepartmentsNameFromUser(int userIdEmpl)throws Exception;
public List<Object[]> getSearchedTeacherDepartmentsName(String deptNmae)throws Exception;
public List<Object[]> getSearchedTeacherDepartmentsNameFrmUsers(String deptNmae)throws Exception;
public List<Integer> getUsersId(int rid)throws Exception;
public List<Object[]>  getTeacherDepartmentsNameNew(List<Integer> list)throws Exception;
public List<Object[]> getTeacherDepartmentsNameFromGuest(int userIdEmpl)throws Exception;

}
