package com.kp.cms.forms.employee;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.QualificationLevelTO;

public class QualificationLevelForm extends BaseActionForm{
	private int id;
	private int reactivate;

	private String name;
	private boolean fixedDisplay;
	private Integer displayOrder;
	private int duplId;
	private String origName;
	private boolean origFixedDisplay;
	private int origDisplayOrder;
	private QualificationLevelTO qualificationList;
	private boolean phdQualification;
	private boolean orgPhdQualification;
	
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
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
	
		return actionErrors;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public boolean isPhdQualification() {
		return phdQualification;
	}
	public void setPhdQualification(boolean phdQualification) {
		this.phdQualification = phdQualification;
	}
	public boolean isOrgPhdQualification() {
		return orgPhdQualification;
	}
	public void setOrgPhdQualification(boolean orgPhdQualification) {
		this.orgPhdQualification = orgPhdQualification;
	}
	public QualificationLevelTO getQualificationList() {
		return qualificationList;
	}
	public void setQualificationList(QualificationLevelTO qualificationList) {
		this.qualificationList = qualificationList;
	}
	public int getReactivate() {
		return reactivate;
	}
	public void setReactivate(int reactivate) {
		this.reactivate = reactivate;
	}

}
