package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ModifyEmployeeLeaveTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
@SuppressWarnings("serial")
public class ModifyEmployeeLeaveForm extends BaseActionForm{
	
	private String academicYear;
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
	private List<ModifyEmployeeLeaveTO> modifyEmployeeLeaveTOs;
	private int applyLeaveId;
	private String empTypeId;
	private Double noOfDays;
	private String cancelReason;
	private String oldLeaveTypeId;
	private double leavesTaken;
	
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
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
	public List<ModifyEmployeeLeaveTO> getModifyEmployeeLeaveTOs() {
		return modifyEmployeeLeaveTOs;
	}
	public void setModifyEmployeeLeaveTOs(
			List<ModifyEmployeeLeaveTO> modifyEmployeeLeaveTOs) {
		this.modifyEmployeeLeaveTOs = modifyEmployeeLeaveTOs;
	}
	public int getApplyLeaveId() {
		return applyLeaveId;
	}
	public void setApplyLeaveId(int applyLeaveId) {
		this.applyLeaveId = applyLeaveId;
	}
	public String getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	public Double getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Double noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public String getOldLeaveTypeId() {
		return oldLeaveTypeId;
	}
	public void setOldLeaveTypeId(String oldLeaveTypeId) {
		this.oldLeaveTypeId = oldLeaveTypeId;
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
		this.modifyEmployeeLeaveTOs=null;
		this.cancelReason = null;
	}
	public double getLeavesTaken() {
		return leavesTaken;
	}
	public void setLeavesTaken(double leavesTaken) {
		this.leavesTaken = leavesTaken;
	}
	

}
