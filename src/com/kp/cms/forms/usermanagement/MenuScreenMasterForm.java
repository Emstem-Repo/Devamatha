package com.kp.cms.forms.usermanagement;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.usermanagement.AssignPrivilegeTO;
import com.kp.cms.to.usermanagement.MenusTO;
import com.kp.cms.to.usermanagement.ModuleTO;

public class MenuScreenMasterForm extends BaseActionForm{
	
	private String method;
	private String module;
	private String screenName;
	private String sequence;
	private String path;
	private String newtab;
	private List<ModuleTO> moduleToList;
	private List<MenusTO> menusList;
	private List<AssignPrivilegeTO> privilegeList;
	private int moduleId;
	private int menuId;
	private String menuName;
	private int oldModuleId;
	
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	
	public String getNewtab() {
		return newtab;
	}
	public void setNewtab(String newtab) {
		this.newtab = newtab;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<ModuleTO> getModuleToList() {
		return moduleToList;
	}
	public void setModuleToList(List<ModuleTO> moduleToList) {
		this.moduleToList = moduleToList;
	}
	public List<MenusTO> getMenusList() {
		return menusList;
	}
	public void setMenusList(List<MenusTO> menusList) {
		this.menusList = menusList;
	}
	
	public void clearAll() {
		super.clear();
		this.module = null;
		this.path = null;
		this.screenName = null;
		this.sequence = null;
		this.newtab="false";
		this.privilegeList=null;
		this.menuName=null;
		this.menuId=0;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public int getOldModuleId() {
		return oldModuleId;
	}
	public void setOldModuleId(int oldModuleId) {
		this.oldModuleId = oldModuleId;
	}
	public List<AssignPrivilegeTO> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<AssignPrivilegeTO> privilegeList) {
		this.privilegeList = privilegeList;
	}
	public int getModuleId() {
		return moduleId;
	}
	public void setModuleId(int moduleId) {
		this.moduleId = moduleId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

}
