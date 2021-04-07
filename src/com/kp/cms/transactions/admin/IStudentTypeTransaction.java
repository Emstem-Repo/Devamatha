package com.kp.cms.transactions.admin;

import java.util.List;
import com.kp.cms.bo.admin.StudentType;

public interface IStudentTypeTransaction 
{
	public List<StudentType> getStudentType()throws Exception;
	public boolean addStudentType(String name, String desc)throws Exception;
	public boolean editStudentType(int id, String name, String desc)throws Exception;
	public boolean deleteStudentType(int id, String name, String desc)throws Exception;
	public StudentType existanceCheck(String name)throws Exception;
	public boolean reActivateStudentType(String studentCategory)throws Exception;
}
