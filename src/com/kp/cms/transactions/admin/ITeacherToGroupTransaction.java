package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Roles;
import com.kp.cms.bo.admin.TeacherToGroup;
import com.kp.cms.forms.admin.TeacherToGroupForm;

public interface ITeacherToGroupTransaction
{
    public boolean addteacherToGroup(TeacherToGroup teacherToGroup, String s) throws Exception;

    public  TeacherToGroup getteacherToGroupById(int i) throws Exception;

    public boolean deleteTeacherToGroup(int i) throws Exception;

    public boolean reActivateTeacherToGroup(TeacherToGroupForm teacherToGroupForm) throws Exception;

	public List<Roles> getgetRoles() throws Exception;

	public List<TeacherToGroup> getteacherGroupList()throws Exception;

	public List<Object[]> getroles() throws Exception;

	public List<Object[]> getusers()throws Exception;
	
	public boolean duplicateCheck(TeacherToGroupForm teacherToGroupForm, ActionErrors actionerrors, HttpSession httpsession)throws Exception;

	
}
