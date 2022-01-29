package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.employee.EmployeeApproveLeaveForm;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;

public interface IEmployeeApproveLeaveTxn {
	public List<EmpApplyLeave> getLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm,String employeeId) throws Exception;
	public boolean updateStatus(List<EmployeeApproveLeaveTO> leaveList) throws Exception;
	public List<EmpLeave> getAvailableLeaveDetails(String employeeId) throws Exception;
	public List<EmpLeave>getApprovedLeaveDetails(List<EmployeeApproveLeaveTO> approveList)throws Exception;
	public void updateLeavesSanctioned(List<EmpLeave> leaveL1ist)throws Exception;
	public List<EmpApplyLeave> getCancelLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm, String employeeId)throws Exception;
	public boolean updateCancelStatus(List<EmployeeApproveLeaveTO> leaveApproveList)throws Exception;
	public List<EmpLeave> getCanceledLeaveDetails(List<EmployeeApproveLeaveTO> leaveApproveList)throws Exception;
}

