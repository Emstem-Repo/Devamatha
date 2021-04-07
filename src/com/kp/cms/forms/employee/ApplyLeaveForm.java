package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpLeaveTypeTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;

public class ApplyLeaveForm extends BaseActionForm {
	private String leaveId;
	private String fromDate;
	private String toDate;
	private String reason;
	private String fromAM;
	private String toAM;
	private List<EmpLeaveTypeTO> leaveList;
	private Boolean isOndutyLeave;
	private Map<Integer, String> employeeMap;
	private List<EmployeeLeaveTO> applyLeaveList;
	private List<EmpLeaveTO> allotedLeaveList;
	private Integer noOfDays;
	private String cancelReason;
	private Integer id;
	
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getFromAM() {
		return fromAM;
	}
	public void setFromAM(String fromAM) {
		this.fromAM = fromAM;
	}
	
	public String getToAM() {
		return toAM;
	}
	public void setToAM(String toAM) {
		this.toAM = toAM;
	}
	public List<EmpLeaveTypeTO> getLeaveList() {
		return leaveList;
	}
	public void setLeaveList(List<EmpLeaveTypeTO> leaveList) {
		this.leaveList = leaveList;
	}

	public Boolean getIsOndutyLeave() {
		return isOndutyLeave;
	}
	public void setIsOndutyLeave(Boolean isOndutyLeave) {
		this.isOndutyLeave = isOndutyLeave;
	}
	public Map<Integer, String> getEmployeeMap() {
		return employeeMap;
	}
	public void setEmployeeMap(Map<Integer, String> employeeMap) {
		this.employeeMap = employeeMap;
	}
	/**
	 * @return the applyLeaveList
	 */
	public List<EmployeeLeaveTO> getApplyLeaveList() {
		return applyLeaveList;
	}
	/**
	 * @param applyLeaveList the applyLeaveList to set
	 */
	public void setApplyLeaveList(List<EmployeeLeaveTO> applyLeaveList) {
		this.applyLeaveList = applyLeaveList;
	}
	public List<EmpLeaveTO> getAllotedLeaveList() {
		return allotedLeaveList;
	}
	public void setAllotedLeaveList(List<EmpLeaveTO> allotedLeaveList) {
		this.allotedLeaveList = allotedLeaveList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.setLeaveId(null);
		this.setFromAM(null);
		this.setToAM(null);
		this.setFromDate(null);
		this.setToDate(null);
		this.setReason(null);
		this.setNoOfDays(null);
		this.setId(null);
		super.setEmployeeId(null);
		this.setCancelReason(null);
	}
	public Integer getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
}
