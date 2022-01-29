package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvCompanyTO;

public class CounterMasterForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String type;
	private String startNo;
	private String prefix;
	private int duplId;
	private String origType;
	private String origStartNo;
	private String origPrefix;
	private String currentNo;
	private Boolean isEdit;
	private List<InvCompanyTO> invCompanyList;
	private String companyId;
	
	public int getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getStartNo() {
		return startNo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setStartNo(String startNo) {
		this.startNo = startNo;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigType() {
		return origType;
	}
	public String getOrigStartNo() {
		return origStartNo;
	}
	public String getOrigPrefix() {
		return origPrefix;
	}
	public void setOrigType(String origType) {
		this.origType = origType;
	}
	public void setOrigStartNo(String origStartNo) {
		this.origStartNo = origStartNo;
	}
	public void setOrigPrefix(String origPrefix) {
		this.origPrefix = origPrefix;
	}
	public String getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}
	
	public Boolean getIsEdit() {
		return isEdit;
	}
	public void setIsEdit(Boolean isEdit) {
		this.isEdit = isEdit;
	}
	public List<InvCompanyTO> getInvCompanyList() {
		return invCompanyList;
	}
	public void setInvCompanyList(List<InvCompanyTO> invCompanyList) {
		this.invCompanyList = invCompanyList;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
