package com.kp.cms.to.usermanagement;

import java.io.Serializable;
import java.util.List;

public class RolesTO implements Serializable{
	
	private int id;
	private String name;
	private List<AssignPrivilegeTO> assignPrivilegeTOList;
	private boolean tempChecked;
	private boolean checked;
	private int certificateDetailsRolesId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AssignPrivilegeTO> getAssignPrivilegeTOList() {
		return assignPrivilegeTOList;
	}
	public void setAssignPrivilegeTOList(
			List<AssignPrivilegeTO> assignPrivilegeTOList) {
		this.assignPrivilegeTOList = assignPrivilegeTOList;
	}
	public boolean isTempChecked() {
		return tempChecked;
	}
	public void setTempChecked(boolean tempChecked) {
		this.tempChecked = tempChecked;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public int getCertificateDetailsRolesId() {
		return certificateDetailsRolesId;
	}
	public void setCertificateDetailsRolesId(int certificateDetailsRolesId) {
		this.certificateDetailsRolesId = certificateDetailsRolesId;
	}
	

}
