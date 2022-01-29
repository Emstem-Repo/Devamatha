package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AssignPeersGroupsTo;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationGroupsTO;

public class AssignPeersGroupsForm extends BaseActionForm{
	private int id;
	private String groupId;
	List<DepartmentEntryTO> departmentTo;
	List<PeersEvaluationGroupsTO> groupsTOs;
	private String[] empIds;
	private List<String> dupFacultyIds;
	private String displayErrorMsg;
	private String msg;
	List<AssignPeersGroupsTo> peersGroupsTos;
	private String departmentId;
	Map<Integer,Integer> assignPeersIds;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<DepartmentEntryTO> getDepartmentTo() {
		return departmentTo;
	}
	public void setDepartmentTo(List<DepartmentEntryTO> departmentTo) {
		this.departmentTo = departmentTo;
	}
	public List<PeersEvaluationGroupsTO> getGroupsTOs() {
		return groupsTOs;
	}
	public void setGroupsTOs(List<PeersEvaluationGroupsTO> groupsTOs) {
		this.groupsTOs = groupsTOs;
	}
	public String[] getEmpIds() {
		return empIds;
	}
	public void setEmpIds(String[] empIds) {
		this.empIds = empIds;
	}
	public List<String> getDupFacultyIds() {
		return dupFacultyIds;
	}
	public void setDupFacultyIds(List<String> dupFacultyIds) {
		this.dupFacultyIds = dupFacultyIds;
	}
	public void clear(ActionMapping mapping, HttpServletRequest request) {
		this.departmentId = null;
		this.id = 0;
		this.empIds = null;
		this.groupId = null;
		this.empIds = null;
	}
	public String getDisplayErrorMsg() {
		return displayErrorMsg;
	}
	public void setDisplayErrorMsg(String displayErrorMsg) {
		this.displayErrorMsg = displayErrorMsg;
	}
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
	public List<AssignPeersGroupsTo> getPeersGroupsTos() {
		return peersGroupsTos;
	}
	public void setPeersGroupsTos(List<AssignPeersGroupsTo> peersGroupsTos) {
		this.peersGroupsTos = peersGroupsTos;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public Map<Integer, Integer> getAssignPeersIds() {
		return assignPeersIds;
	}
	public void setAssignPeersIds(Map<Integer, Integer> assignPeersIds) {
		this.assignPeersIds = assignPeersIds;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
