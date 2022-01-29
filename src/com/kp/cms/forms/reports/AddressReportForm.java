package com.kp.cms.forms.reports;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.reports.StudentAddressTo;

public class AddressReportForm extends BaseActionForm {

		
	private String method;
	private String addressType;
	private String academicYear;
	private String startRegisterNo;
	private String endRegisterNo;
	private String withName;
	private List<StudentAddressTo> studentAddressList;
	private String mode;
	private int count;
	
	
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
	public String getAddressType() {
		return addressType;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStartRegisterNo() {
		return startRegisterNo;
	}

	public void setStartRegisterNo(String startRegisterNo) {
		this.startRegisterNo = startRegisterNo;
	}

	public String getEndRegisterNo() {
		return endRegisterNo;
	}

	public void setEndRegisterNo(String endRegisterNo) {
		this.endRegisterNo = endRegisterNo;
	}

	public String getWithName() {
		return withName;
	}

	public void setWithName(String withName) {
		this.withName = withName;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public List<StudentAddressTo> getStudentAddressList() {
		return studentAddressList;
	}

	public void setStudentAddressList(List<StudentAddressTo> studentAddressList) {
		this.studentAddressList = studentAddressList;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		
		
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
	public void resetFields() {
		this.startRegisterNo=null;
		this.endRegisterNo=null;
		this.withName=null;
		this.addressType="Parents Address";
		super.setProgramTypeId(null);	
		super.setProgramId(null);	
		this.count=0;
		
	}
}
