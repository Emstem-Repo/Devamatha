package com.kp.cms.to.usermanagement;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.bo.admin.Menus;

public class LoginTransactionTo implements Serializable,Comparable<LoginTransactionTo> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String moduleName;
	
	private Integer modulePosition;	

	private List<Menus> menuTOList = null;
	
	private String room;
	private String type;
	private String examDate;
	private String session;
	

	
	public List<Menus> getMenuTOList() {
		return menuTOList;
	}

	public void setMenuTOList(List<Menus> menuTOList) {
		this.menuTOList = menuTOList;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public Integer getModulePosition() {
		return modulePosition;
	}

	public void setModulePosition(Integer modulePosition) {
		this.modulePosition = modulePosition;
	}

	@Override
	public int compareTo(LoginTransactionTo arg0) {
		if (arg0 != null && this != null) {
			if (this.getModulePosition() > arg0.getModulePosition()) {
				return 1;
			}else if(this.getModulePosition() < arg0.getModulePosition()){
				return -1;
			}else
				return 0;
		}
		return 0;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExamDate() {
		return examDate;
	}

	public void setExamDate(String examDate) {
		this.examDate = examDate;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}
}