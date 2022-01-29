package com.kp.cms.forms.employee;

import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;

public class EmployeeApproveLeaveForm  extends BaseActionForm{
	private String status;
	private String remarks;
	private List<EmployeeApproveLeaveTO> leaveApprovalList;
	private List<EmpLeaveTO> availableLeaveList;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<EmployeeApproveLeaveTO> getLeaveApprovalList() {
		return leaveApprovalList;
	}
	public void setLeaveApprovalList(List<EmployeeApproveLeaveTO> leaveApprovalList) {
		this.leaveApprovalList = leaveApprovalList;
	}
	public List<EmpLeaveTO> getAvailableLeaveList() {
		return availableLeaveList;
	}
	public void setAvailableLeaveList(List<EmpLeaveTO> availableLeaveList) {
		this.availableLeaveList = availableLeaveList;
	}

}
