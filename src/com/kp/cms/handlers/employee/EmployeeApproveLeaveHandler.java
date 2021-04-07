package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.forms.employee.EmployeeApproveLeaveForm;
import com.kp.cms.helpers.employee.EmployeeApproveLeaveHelper;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.transactions.employee.IEmployeeApproveLeaveTxn;
import com.kp.cms.transactionsimpl.employee.EmployeeApproveLeaveTxnImpl;

public class EmployeeApproveLeaveHandler {
	private static final Log log = LogFactory.getLog(EmployeeApproveLeaveHandler.class);
	public static volatile EmployeeApproveLeaveHandler employeeApproveLeaveHandler = null;

	public static EmployeeApproveLeaveHandler getInstance() {
		if (employeeApproveLeaveHandler == null) {
			employeeApproveLeaveHandler = new EmployeeApproveLeaveHandler();
			return employeeApproveLeaveHandler;
		}
		return employeeApproveLeaveHandler;
	}
	

	/**
	 * 
	 * @param approveLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeApproveLeaveTO> getApplyLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm,String employeeId ) throws Exception {
		log.debug("inside getApplyLeaveDetails");
		IEmployeeApproveLeaveTxn iTxn = EmployeeApproveLeaveTxnImpl.getInstance();
		
		List<EmpApplyLeave> leaveList = iTxn.getLeaveDetails(approveLeaveForm,employeeId);
		List<EmployeeApproveLeaveTO> approveLeaveToList = EmployeeApproveLeaveHelper.getInstance().copyBosToTO(leaveList);
		return approveLeaveToList;
	}
	  		
	/**
	 * 
	 * @param approveList
	 * @return
	 * @throws Exception
	 */
	public boolean updateStatus(List<EmployeeApproveLeaveTO> approveList ) throws Exception {
		log.debug("inside updateStatus");
		IEmployeeApproveLeaveTxn iTxn = EmployeeApproveLeaveTxnImpl.getInstance();
		boolean isUpdated=iTxn.updateStatus(approveList);
		List<EmpLeave>approvedLeaveList=iTxn.getApprovedLeaveDetails(approveList);
		List<EmployeeApproveLeaveTO> approvedList=EmployeeApproveLeaveHelper.getInstance().getApprovedLeaveList(approveList);
		if(approvedLeaveList.size()!=0 && approvedList.size()!=0)
		{
			iTxn.updateLeavesSanctioned(EmployeeApproveLeaveHelper.getInstance().getUpdatedLeaveList(approvedLeaveList,approvedList,"approval"));
		}
		return isUpdated;
	}	
	/**
	 * 
	 * @param approveLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getAvailableLeaves(String employeeId) throws Exception {
		log.debug("inside getApplyLeaveDetails");
		IEmployeeApproveLeaveTxn iTxn = EmployeeApproveLeaveTxnImpl.getInstance();
		
		List<EmpLeave> availableLeaveList = iTxn.getAvailableLeaveDetails(employeeId);
		List<EmpLeaveTO> availableLeaveToList = EmployeeApproveLeaveHelper.getInstance().copyAvailableBosToTO(availableLeaveList);
		return availableLeaveToList;
	}


	public List<EmployeeApproveLeaveTO> getCancelLeaveDetails(EmployeeApproveLeaveForm approveLeaveForm, String employeeId)throws Exception 
	{
		IEmployeeApproveLeaveTxn iTxn = EmployeeApproveLeaveTxnImpl.getInstance();
		List<EmpApplyLeave> leaveList = iTxn.getCancelLeaveDetails(approveLeaveForm,employeeId);
		List<EmployeeApproveLeaveTO> approveLeaveToList = EmployeeApproveLeaveHelper.getInstance().copyBosToTO(leaveList);
		return approveLeaveToList;
	}


	public boolean updateCancelStatus(List<EmployeeApproveLeaveTO> leaveApproveList) throws Exception
	{
		IEmployeeApproveLeaveTxn iTxn = EmployeeApproveLeaveTxnImpl.getInstance();
		boolean isUpdated=iTxn.updateCancelStatus(leaveApproveList);
		List<EmpLeave>canceledLeaveList=iTxn.getCanceledLeaveDetails(leaveApproveList);
		List<EmployeeApproveLeaveTO> canceledList=EmployeeApproveLeaveHelper.getInstance().getCanceledLeaveList(leaveApproveList);
		if(canceledLeaveList.size()!=0 && canceledList.size()!=0)
		{
			iTxn.updateLeavesSanctioned(EmployeeApproveLeaveHelper.getInstance().getUpdatedLeaveList(canceledLeaveList,canceledList,"cancellation"));
		}
		return isUpdated;
	}	
}
