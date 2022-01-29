package com.kp.cms.forms.admin;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;

public class RemarkTypeForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String remarkType;
	private String color;
	private String maxOccurrences;
	private String createdBy;;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String origRemarkType;
	private String origColor;
	private String origMaxOccurrences;
	private int duplId;

	public String getRemarkType() {
		return remarkType;
	}
	public String getColor() {
		return color;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setRemarkType(String remarkType) {
		this.remarkType = remarkType;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMaxOccurrences() {
		return maxOccurrences;
	}
	public void setMaxOccurrences(String maxOccurrences) {
		this.maxOccurrences = maxOccurrences;
	}
	
	public String getOrigRemarkType() {
		return origRemarkType;
	}
	public String getOrigColor() {
		return origColor;
	}
	public String getOrigMaxOccurrences() {
		return origMaxOccurrences;
	}
	public void setOrigRemarkType(String origRemarkType) {
		this.origRemarkType = origRemarkType;
	}
	public void setOrigColor(String origColor) {
		this.origColor = origColor;
	}
	public void setOrigMaxOccurrences(String origMaxOccurrences) {
		this.origMaxOccurrences = origMaxOccurrences;
	}
	
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		remarkType = null;
		color = null;
		maxOccurrences = null;
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

}
