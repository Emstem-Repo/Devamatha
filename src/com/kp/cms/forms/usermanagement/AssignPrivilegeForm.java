package com.kp.cms.forms.usermanagement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.to.usermanagement.RolesTO;

public class AssignPrivilegeForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String roleId;
	private String moduleId;
	private String oldRoleId;
	private List<RolesTO>roleList;
	private List<ModuleTO>moduleList;
	private List<AssignPrivilegeTO>privilegeList;
	private Map<String, Set<String>> moduleMenuMap;
	//Used while edit button is clicked
	private List<AssignPrivilegeTO>assignPrivilegeTOList;
	
	public String getOldRoleId() {
		return oldRoleId;
	}

	public void setOldRoleId(String oldRoleId) {
		this.oldRoleId = oldRoleId;
	}

	public List<AssignPrivilegeTO> getAssignPrivilegeTOList() {
		return assignPrivilegeTOList;
	}

	public void setAssignPrivilegeTOList(
			List<AssignPrivilegeTO> assignPrivilegeTOList) {
		this.assignPrivilegeTOList = assignPrivilegeTOList;
	}

	public Map<String, Set<String>> getModuleMenuMap() {
		return moduleMenuMap;
	}

	public void setModuleMenuMap(Map<String, Set<String>> moduleMenuMap) {
		this.moduleMenuMap = moduleMenuMap;
	}

	public List<AssignPrivilegeTO> getPrivilegeList() {
		return privilegeList;
	}

	public void setPrivilegeList(List<AssignPrivilegeTO> privilegeList) {
		this.privilegeList = privilegeList;
	}

	public List<ModuleTO> getModuleList() {
		return moduleList;
	}

	public void setModuleList(List<ModuleTO> moduleList) {
		this.moduleList = moduleList;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMethod() {
		return method;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<RolesTO> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<RolesTO> roleList) {
		this.roleList = roleList;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear(){
		this.roleId=null;
	}
}
