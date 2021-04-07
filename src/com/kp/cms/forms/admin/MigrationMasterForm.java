package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.MigrationNumberTO;

public class MigrationMasterForm extends BaseActionForm {
	
	private int id;
	private int duplId;
	private String prefix;
	private String origPrefix;
	private String startNo;
	private String origCreatedBy;
	private String origCreatedDate;
	List<MigrationNumberTO> list;
	private String origStartNo;
	private String type;
	private String origType;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public void resetFields()
	{
		this.setStartNo(null);
		this.setPrefix(null);
		this.setType(null);
	}

	public int getDuplId() {
		return duplId;
	}

	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getOrigPrefix() {
		return origPrefix;
	}

	public void setOrigPrefix(String origPrefix) {
		this.origPrefix = origPrefix;
	}

	public String getStartNo() {
		return startNo;
	}

	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}

	public String getOrigCreatedBy() {
		return origCreatedBy;
	}

	public void setOrigCreatedBy(String origCreatedBy) {
		this.origCreatedBy = origCreatedBy;
	}

	public String getOrigCreatedDate() {
		return origCreatedDate;
	}

	public void setOrigCreatedDate(String origCreatedDate) {
		this.origCreatedDate = origCreatedDate;
	}

	public List<MigrationNumberTO> getList() {
		return list;
	}

	public void setList(List<MigrationNumberTO> list) {
		this.list = list;
	}

	public String getOrigStartNo() {
		return origStartNo;
	}

	public void setOrigStartNo(String origStartNo) {
		this.origStartNo = origStartNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrigType() {
		return origType;
	}

	public void setOrigType(String origType) {
		this.origType = origType;
	}

}
