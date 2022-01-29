package com.kp.cms.forms.serviceLearning;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;

public class DepartmentNameEntryForm extends BaseActionForm {
	
    private String Id;
	private int origId;
	private String origdepartmentName;
	private String departmentName;
	private List<DepartmentNameEntryTo> departmentNameEntryToList;
	private String dupId;
	
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public int getOrigId() {
		return origId;
	}
	public void setOrigId(int origId) {
		this.origId = origId;
	}
	public String getOrigdepartmentName() {
		return origdepartmentName;
	}
	public void setOrigdepartmentName(String origdepartmentName) {
		this.origdepartmentName = origdepartmentName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public List<DepartmentNameEntryTo> getDepartmentNameEntryToList() {
		return departmentNameEntryToList;
	}
	public void setDepartmentNameEntryToList(
			List<DepartmentNameEntryTo> departmentNameEntryToList) {
		this.departmentNameEntryToList = departmentNameEntryToList;
	}
	public String getDupId() {
		return dupId;
	}
	public void setDupId(String dupId) {
		this.dupId = dupId;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.departmentName = null;
		
	
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
	

}
