package com.kp.cms.forms.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.TCNumberTO;
import com.kp.cms.to.exam.GrievanceMasterTo;

public class GrievanceMasterForm extends BaseActionForm {

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
	private String origYear;
	private String origCreatedBy;
	private String origCreatedDate;
	List<GrievanceMasterTo> list;
	private String currentNo;
	private String slNo;
	private String toCollege;
	private String collegeName;
	private String origCollegName;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartNo() {
		return startNo;
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
	public void setOrigType(String origType) {
		this.origType = origType;
	}
	public String getOrigStartNo() {
		return origStartNo;
	}
	public void setOrigStartNo(String origStartNo) {
		this.origStartNo = origStartNo;
	}
	public String getOrigPrefix() {
		return origPrefix;
	}
	public void setOrigPrefix(String origPrefix) {
		this.origPrefix = origPrefix;
	}
	
	public String getOrigYear() {
		return origYear;
	}
	public void setOrigYear(String origYear) {
		this.origYear = origYear;
	}
	
	public List<GrievanceMasterTo> getList() {
		return list;
	}
	public void setList(List<GrievanceMasterTo> list) {
		this.list = list;
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
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	/**
	 * initialize
	 * @param counterMasterForm
	 */
	
	public void resetFields()
	{
		this.setCollegeName("Cjc");
		this.setType(null);
		this.setStartNo(null);
		this.setPrefix(null);
		super.setYear(null);
	}
	public String getCurrentNo() {
		return currentNo;
	}
	public void setCurrentNo(String currentNo) {
		this.currentNo = currentNo;
	}
	public String getSlNo() {
		return slNo;
	}
	public void setSlNo(String slNo) {
		this.slNo = slNo;
	}
	public String getToCollege() {
		return toCollege;
	}
	public void setToCollege(String toCollege) {
		this.toCollege = toCollege;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getOrigCollegName() {
		return origCollegName;
	}
	public void setOrigCollegName(String origCollegName) {
		this.origCollegName = origCollegName;
	}
	
	


}
