package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.TeacherToGroupTos;

public class TeacherToGroupForm extends BaseActionForm{
	
	private int id;
	private String usersId;
	private String roleId;
	private Map<Integer, String> rolesMap;
	private Map<Integer,String> usersMap;
	private List<TeacherToGroupTos> teacherGrouplist;
	private String createdby;
	
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
  public void reset(){
	  id=0;
	  usersId=null;
	  roleId=null;
	  rolesMap=null;
	  usersMap=null;
	  
	}
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request){
		String formName=request.getParameter("formName");
		ActionErrors errors=super.validate(mapping, request,formName);
		return errors;
	}

	public Map<Integer, String> getRolesMap() {
		return rolesMap;
	}

	public void setRolesMap(Map<Integer, String> rolesMap) {
		this.rolesMap = rolesMap;
	}

	public Map<Integer, String> getUsersMap() {
		return usersMap;
	}

	public void setUsersMap(Map<Integer, String> usersMap) {
		this.usersMap = usersMap;
	}

	public List<TeacherToGroupTos> getTeacherGrouplist() {
		return teacherGrouplist;
	}

	public void setTeacherGrouplist(List<TeacherToGroupTos> teacherGrouplist) {
		this.teacherGrouplist = teacherGrouplist;
	}

	public String getUsersId() {
		return usersId;
	}

	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}

	public String getCreatedby() {
		return createdby;
	}

	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}

}
