package com.kp.cms.forms.phd;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.QualificationLevelTO;
import com.kp.cms.to.phd.PhdQualificationLevelTo;

public class PhdQualificationLevelForm extends BaseActionForm{
	private int id;
	private int reactivate;

	private String name;
	private boolean fixedDisplay;
	private Integer displayOrder;
	private int duplId;
	private String origName;
	private boolean origFixedDisplay;
	private int origDisplayOrder;
	private PhdQualificationLevelTo qualificationList;
	
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
	public boolean isFixedDisplay() {
		return fixedDisplay;
	}
	public void setFixedDisplay(boolean fixedDisplay) {
		this.fixedDisplay = fixedDisplay;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getOrigName() {
		return origName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public boolean isOrigFixedDisplay() {
		return origFixedDisplay;
	}
	public void setOrigFixedDisplay(boolean origFixedDisplay) {
		this.origFixedDisplay = origFixedDisplay;
	}
	public int getOrigDisplayOrder() {
		return origDisplayOrder;
	}
	public void setOrigDisplayOrder(int origDisplayOrder) {
		this.origDisplayOrder = origDisplayOrder;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
	
		return actionErrors;
	}
	public PhdQualificationLevelTo getQualificationList() {
		return qualificationList;
	}
	public void setQualificationList(PhdQualificationLevelTo qualificationList) {
		this.qualificationList = qualificationList;
	}
	public int getReactivate() {
		return reactivate;
	}
	public void setReactivate(int reactivate) {
		this.reactivate = reactivate;
	}

}
