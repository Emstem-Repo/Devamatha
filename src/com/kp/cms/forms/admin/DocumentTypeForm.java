package com.kp.cms.forms.admin;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;

public class DocumentTypeForm extends BaseActionForm{
	private Integer id;
	private String name;
	private String docShortName;
	private String origName;
	private String origShortName;
	private String printName;
	private String origPrintName;
	private int duplId;
	private String origEducationalInfo;
	private String  educationalInfo;
	private String displayOrder;
	private String origDisplayOrder;
	
	public String getOrigEducationalInfo() {
		return origEducationalInfo;
	}
	public void setOrigEducationalInfo(String origEducationalInfo) {
		this.origEducationalInfo = origEducationalInfo;
	}
	public String getEducationalInfo() {
		return educationalInfo;
	}
	public void setEducationalInfo(String educationalInfo) {
		this.educationalInfo = educationalInfo;
	}
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getDocShortName() {
		return docShortName;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDocShortName(String docShortName) {
		this.docShortName = docShortName;
	}
	public String getOrigName() {
		return origName;
	}
	public String getOrigShortName() {
		return origShortName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	public void setOrigShortName(String origShortName) {
		this.origShortName = origShortName;
	}

	public String getPrintName() {
		return printName;
	}
	public void setPrintName(String printName) {
		this.printName = printName;
	}
	
	public String getOrigPrintName() {
		return origPrintName;
	}
	public void setOrigPrintName(String origPrintName) {
		this.origPrintName = origPrintName;
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
		this.name = null;
		this.docShortName = "";
		this.printName = "";
		this.id=null;
		this.displayOrder=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(String displayOrder) {
		this.displayOrder = displayOrder;
	}
	public String getOrigDisplayOrder() {
		return origDisplayOrder;
	}
	public void setOrigDisplayOrder(String origDisplayOrder) {
		this.origDisplayOrder = origDisplayOrder;
	}
	
		
}
