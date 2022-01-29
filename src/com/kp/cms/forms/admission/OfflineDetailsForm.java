package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.OfflineDetailsTO;

/**
 * 
 * @author kshirod.k
 * A formbean for OfflineDetails Entry
 */

public class OfflineDetailsForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationNo;
	private String receiptNo;
	private String amount;
	private String date;
	private String academicYear;
	private String method;
	private List<CourseTO> courseList;
	private List<OfflineDetailsTO> offlineDetailsList;
	private String operation;
	private int oldApplicationNo;
	private int oldYear;
	
	
	public int getOldYear() {
		return oldYear;
	}
	public void setOldYear(int oldYear) {
		this.oldYear = oldYear;
	}
	public int getOldApplicationNo() {
		return oldApplicationNo;
	}
	public void setOldApplicationNo(int oldApplicationNo) {
		this.oldApplicationNo = oldApplicationNo;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<OfflineDetailsTO> getOfflineDetailsList() {
		return offlineDetailsList;
	}
	public void setOfflineDetailsList(List<OfflineDetailsTO> offlineDetailsList) {
		this.offlineDetailsList = offlineDetailsList;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public void clear(){
		this.amount=null;
		this.receiptNo=null;
		this.date=null;
		this.applicationNo=null;
		this.academicYear = null;
		super.clear();
	}
	
	public void clearWhileReset(){
		this.amount=null;
		this.date=null;
		this.applicationNo=null;
		this.academicYear = null;
		super.clear();
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
