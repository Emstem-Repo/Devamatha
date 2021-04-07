package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpBiometricSetUpTo;

@SuppressWarnings("serial")
public class BiometricLogSetupForm extends BaseActionForm {
  private int id;
  private String terminalId;
  private String fingerPrintId;
  private String employeeCode;
  private String employeeName;
  private String datetime;
  private String functionkey;
  private String status;
  private String delimitedWith;
  private String dateFormat;
  private String textFilePath;
  private String testCode;
  private String dummyTerminalId;
  private String dummyFingerPrintId;
  private String dummyEmployeeCode;
  private String dummyEmployeeName;
  private String dummyDatetime;
  private String dummyFunctionkey;
  private String dummyStatus;
  private String dummyDelimitedWith;
  private String dummyDateFormat;
  private String dummyTestCode;
  List<EmpBiometricSetUpTo> list;
	public List<EmpBiometricSetUpTo> getList() {
	return list;
	}
	public void setList(List<EmpBiometricSetUpTo> list) {
		this.list = list;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTerminalId() {
		return terminalId;
	}


	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}


	public String getFingerPrintId() {
		return fingerPrintId;
	}


	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}


	public String getEmployeeCode() {
		return employeeCode;
	}


	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}


	public String getEmployeeName() {
		return employeeName;
	}


	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


	public String getDatetime() {
		return datetime;
	}


	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}


	public String getFunctionkey() {
		return functionkey;
	}


	public void setFunctionkey(String functionkey) {
		this.functionkey = functionkey;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getDelimitedWith() {
		return delimitedWith;
	}


	public void setDelimitedWith(String delimitedWith) {
		this.delimitedWith = delimitedWith;
	}


	public String getDateFormat() {
		return dateFormat;
	}


	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}


	public String getTextFilePath() {
		return textFilePath;
	}


	public void setTextFilePath(String textFilePath) {
		this.textFilePath = textFilePath;
	}


	public String getDummyTerminalId() {
		return dummyTerminalId;
	}


	public void setDummyTerminalId(String dummyTerminalId) {
		this.dummyTerminalId = dummyTerminalId;
	}


	public String getDummyFingerPrintId() {
		return dummyFingerPrintId;
	}


	public void setDummyFingerPrintId(String dummyFingerPrintId) {
		this.dummyFingerPrintId = dummyFingerPrintId;
	}


	public String getDummyEmployeeCode() {
		return dummyEmployeeCode;
	}


	public void setDummyEmployeeCode(String dummyEmployeeCode) {
		this.dummyEmployeeCode = dummyEmployeeCode;
	}


	public String getDummyEmployeeName() {
		return dummyEmployeeName;
	}


	public void setDummyEmployeeName(String dummyEmployeeName) {
		this.dummyEmployeeName = dummyEmployeeName;
	}


	public String getDummyDatetime() {
		return dummyDatetime;
	}


	public void setDummyDatetime(String dummyDatetime) {
		this.dummyDatetime = dummyDatetime;
	}

	public String getDummyStatus() {
		return dummyStatus;
	}


	public void setDummyStatus(String dummyStatus) {
		this.dummyStatus = dummyStatus;
	}


	public String getDummyDelimitedWith() {
		return dummyDelimitedWith;
	}


	public void setDummyDelimitedWith(String dummyDelimitedWith) {
		this.dummyDelimitedWith = dummyDelimitedWith;
	}


	public String getDummyDateFormat() {
		return dummyDateFormat;
	}


	public void setDummyDateFormat(String dummyDateFormat) {
		this.dummyDateFormat = dummyDateFormat;
	}

	public void setDummyFunctionkey(String dummyFunctionkey) {
		this.dummyFunctionkey = dummyFunctionkey;
	}
	public String getDummyFunctionkey() {
		return dummyFunctionkey;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void resetFields(){
		  this.id=0;
		  this.terminalId=null;
		  this.fingerPrintId=null;
		  this.employeeCode=null;
		  this.employeeName=null;
		  this.datetime=null;
		  this.functionkey=null;
		  this.status=null;
		  this.delimitedWith=null;
		  this.dateFormat=null;
		  this.textFilePath=null;
		  this.dummyTerminalId=null;
		  this.dummyFingerPrintId=null;
		  this.dummyEmployeeCode=null;
		  this.dummyEmployeeName=null;
		  this.dummyDatetime=null;
		  this.dummyFunctionkey=null;
		  this.dummyStatus=null;
		  this.dummyDelimitedWith=null;
		  this.dummyDateFormat=null;
		  this.testCode=null;
		  this.dummyTestCode=null;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getDummyTestCode() {
		return dummyTestCode;
	}
	public void setDummyTestCode(String dummyTestCode) {
		this.dummyTestCode = dummyTestCode;
	}
}
