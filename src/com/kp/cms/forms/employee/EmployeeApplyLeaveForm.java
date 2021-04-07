package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;

public class EmployeeApplyLeaveForm extends BaseActionForm {
	
	private String isExemption;
	private String empCode;
	private String fingerPrintId;
	private String leaveTypeId;
	private String startDate;
	private String endDate;
	private String isHalfday;
	private String isAm;
	private String employeeName;
	private String designationName;
	private String departmentName;
	private String employeeId;
	private Boolean halfDayDisplay;
	private Boolean amDisplay;
	private List<LeaveTypeTo> leaveTypes;
	private String reason;
	private String empTypeId;
	private List<EmpApplyLeaveTO> applyLeaveTo;
	private List<EmpLeaveTO> empLeaveTO;
	private FormFile csvFile;
	private List<String> fingerPrintIds;
	private double noOfDays;
	private List<String> dateErrorIds;
	private boolean isWrong;
	private List<String> overFlowIds;
	private String focusValue;
	private List<EmpApplyLeaveTO> empOnlineLeave;
	private String displayMessage;
	private List<String> dupFingDatesErrorMes; 
	private List<String> wrongDatesErrorMes; 
	private List<String> wrongDatesFormats;
	private String[] period;
	
	public String getIsExemption() {
		return isExemption;
	}
	public void setIsExemption(String isExemption) {
		this.isExemption = isExemption;
	}
	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getFingerPrintId() {
		return fingerPrintId;
	}
	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getIsHalfday() {
		return isHalfday;
	}
	public void setIsHalfday(String isHalfday) {
		this.isHalfday = isHalfday;
	}
	public String getIsAm() {
		return isAm;
	}
	public void setIsAm(String isAm) {
		this.isAm = isAm;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Boolean getHalfDayDisplay() {
		return halfDayDisplay;
	}
	public void setHalfDayDisplay(Boolean halfDayDisplay) {
		this.halfDayDisplay = halfDayDisplay;
	}
	public Boolean getAmDisplay() {
		return amDisplay;
	}
	public void setAmDisplay(Boolean amDisplay) {
		this.amDisplay = amDisplay;
	}
	public List<LeaveTypeTo> getLeaveTypes() {
		return leaveTypes;
	}
	public void setLeaveTypes(List<LeaveTypeTo> leaveTypes) {
		this.leaveTypes = leaveTypes;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		this.empCode=null;
		this.isAm=null;
		this.isHalfday=null;
		this.empCode=null;
		this.fingerPrintId=null;
		this.leaveTypeId=null;
		this.startDate=null;
		this.endDate=null;
		this.employeeId="0";
		this.isExemption="no";
		this.reason=null;
		this.employeeName=null;
		this.departmentName=null;
		this.designationName=null;
		this.csvFile=null;
		this.displayMessage =null;
		this.empOnlineLeave =null;
	}
	public List<EmpApplyLeaveTO> getApplyLeaveTo() {
		return applyLeaveTo;
	}
	public void setApplyLeaveTo(List<EmpApplyLeaveTO> applyLeaveTo) {
		this.applyLeaveTo = applyLeaveTo;
	}
	public List<EmpLeaveTO> getEmpLeaveTO() {
		return empLeaveTO;
	}
	public void setEmpLeaveTO(List<EmpLeaveTO> empLeaveTO) {
		this.empLeaveTO = empLeaveTO;
	}
	public FormFile getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(FormFile csvFile) {
		this.csvFile = csvFile;
	}
	public List<String> getFingerPrintIds() {
		return fingerPrintIds;
	}
	public void setFingerPrintIds(List<String> fingerPrintIds) {
		this.fingerPrintIds = fingerPrintIds;
	}
	public double getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(double noOfDays) {
		this.noOfDays = noOfDays;
	}
	public List<String> getDateErrorIds() {
		return dateErrorIds;
	}
	public void setDateErrorIds(List<String> dateErrorIds) {
		this.dateErrorIds = dateErrorIds;
	}
	public boolean isWrong() {
		return isWrong;
	}
	public void setWrong(boolean isWrong) {
		this.isWrong = isWrong;
	}
	public List<String> getOverFlowIds() {
		return overFlowIds;
	}
	public void setOverFlowIds(List<String> overFlowIds) {
		this.overFlowIds = overFlowIds;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
	public void setEmpOnlineLeave(List<EmpApplyLeaveTO> empOnlineLeave) {
		this.empOnlineLeave = empOnlineLeave;
	}
	public List<EmpApplyLeaveTO> getEmpOnlineLeave() {
		return empOnlineLeave;
	}
	public String getDisplayMessage() {
		return displayMessage;
	}
	public void setDisplayMessage(String displayMessage) {
		this.displayMessage = displayMessage;
	}
	public List<String> getDupFingDatesErrorMes() {
		return dupFingDatesErrorMes;
	}
	public void setDupFingDatesErrorMes(List<String> dupFingDatesErrorMes) {
		this.dupFingDatesErrorMes = dupFingDatesErrorMes;
	}
	public List<String> getWrongDatesErrorMes() {
		return wrongDatesErrorMes;
	}
	public void setWrongDatesErrorMes(List<String> wrongDatesErrorMes) {
		this.wrongDatesErrorMes = wrongDatesErrorMes;
	}
	public List<String> getWrongDatesFormats() {
		return wrongDatesFormats;
	}
	public void setWrongDatesFormats(List<String> wrongDatesFormats) {
		this.wrongDatesFormats = wrongDatesFormats;
	}
	public String[] getPeriod() {
		return period;
	}
	public void setPeriod(String[] period) {
		this.period = period;
	}
}
