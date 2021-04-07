package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmployeeApproveLeaveTO;
import com.kp.cms.utilities.CommonUtil;


public class EmployeeApproveLeaveHelper {
	private static final Log log = LogFactory.getLog(EmployeeApproveLeaveHelper.class);
	public static volatile EmployeeApproveLeaveHelper employeeApproveLeaveHelper = null;

	public static EmployeeApproveLeaveHelper getInstance() {
		if (employeeApproveLeaveHelper == null) {
			employeeApproveLeaveHelper = new EmployeeApproveLeaveHelper();
			return employeeApproveLeaveHelper;
		}
		return employeeApproveLeaveHelper;
	}
	
	
	/**
	 * 
	 * @param leaveBOList
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeApproveLeaveTO> copyBosToTO(List<EmpApplyLeave> leaveBOList) throws Exception {
		log.debug("inside copyBosToTO");
		EmployeeApproveLeaveTO approveLeaveTO;
		Iterator<EmpApplyLeave> itr = leaveBOList.iterator();
		List<EmployeeApproveLeaveTO> approveToList = new ArrayList<EmployeeApproveLeaveTO>();
		while (itr.hasNext()){
			EmpApplyLeave empApplyLeave = itr.next();
			approveLeaveTO =  new EmployeeApproveLeaveTO();
			approveLeaveTO.setId(empApplyLeave.getId());
			approveLeaveTO.setEmployeeName(empApplyLeave.getEmployee().getFirstName());
			approveLeaveTO.setEmployeeId(empApplyLeave.getEmployee().getId());
			approveLeaveTO.setAppliedOnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getAppliedOn()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
			approveLeaveTO.setFromDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getFromDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
			approveLeaveTO.setToDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empApplyLeave.getToDate()), CMSConstants.SOURCE_DATE,CMSConstants.DEST_DATE));
			approveLeaveTO.setLeaveType(empApplyLeave.getEmpLeaveType().getName());
			approveLeaveTO.setComments(empApplyLeave.getReason());
			approveLeaveTO.setStatus(empApplyLeave.getStatus());
			approveLeaveTO.setRemarks(empApplyLeave.getApprovalRemarks());
			//approveLeaveTO.setNoOfDays(empApplyLeave.getNoOfDays());
			approveLeaveTO.setCancelReason(empApplyLeave.getCancelReason());
			approveLeaveTO.setCancelRemarks(empApplyLeave.getCancelRemarks());
			approveToList.add(approveLeaveTO);
		}
		return approveToList;
	}
	
	/**
	 * 
	 * @param leaveBOList
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> copyAvailableBosToTO(List<EmpLeave> leaveBOList) throws Exception {
		log.debug("inside copyBosToTO");
		EmpLeaveTO empLeaveTO;
		Iterator<EmpLeave> itr = leaveBOList.iterator();
		List<EmpLeaveTO> empLeaveToList = new ArrayList<EmpLeaveTO>();
		while (itr.hasNext()){
			EmpLeave empLeave = itr.next();
			empLeaveTO =  new EmpLeaveTO();
			empLeaveTO.setEmpLeaveTypeName(empLeave.getEmpLeaveType().getName());
			if(empLeave.getLeavesAllocated()!= null){
				empLeaveTO.setLeavesAllocated(empLeave.getLeavesAllocated().toString());
			}
			if(empLeave.getLeavesSanctioned()!= null){
				empLeaveTO.setLeavesSanctioned(empLeave.getLeavesSanctioned().toString());
			}
			if(empLeave.getLeavesRemaining()!= null){
				empLeaveTO.setLeavesRemaining(empLeave.getLeavesRemaining().toString());
			}
			empLeaveToList.add(empLeaveTO);
		}
		
		return empLeaveToList;
	}


	public List<EmpLeave> getUpdatedLeaveList(List<EmpLeave> approvedLeaveList,List<EmployeeApproveLeaveTO> approvedToList,String action) {
		// TODO Auto-generated method stub
		
		int daysDiff=0;
		for(int i=0;i<approvedLeaveList.size() && i<approvedToList.size();i++)
		{
			daysDiff=approvedToList.get(i).getNoOfDays();
			int ls=0;
			/*if(approvedLeaveList.get(i).getLeavesSanctioned()!=null && CommonUtil.isValidDecimal(String.valueOf(approvedLeaveList.get(i).getLeavesSanctioned()))){
				ls=approvedLeaveList.get(i).getLeavesSanctioned();
			}
			int lr=0;
			if(approvedLeaveList.get(i).getLeavesRemaining()!=null && CommonUtil.isValidDecimal(String.valueOf(approvedLeaveList.get(i).getLeavesRemaining()))){
				lr=approvedLeaveList.get(i).getLeavesRemaining();
			}
			if(action.equalsIgnoreCase("approval"))
			{	
				approvedLeaveList.get(i).setLeavesSanctioned(ls+daysDiff);
				approvedLeaveList.get(i).setLeavesRemaining(lr-daysDiff);
			}
			else
			if(action.equalsIgnoreCase("cancellation"))
			{
				approvedLeaveList.get(i).setLeavesSanctioned(ls-daysDiff);
				approvedLeaveList.get(i).setLeavesRemaining(lr+daysDiff);
			}*/
		}
		return approvedLeaveList;
	}


	public List<EmployeeApproveLeaveTO> getApprovedLeaveList(List<EmployeeApproveLeaveTO> approveList) 
	{
		List<EmployeeApproveLeaveTO>approvedList= new ArrayList<EmployeeApproveLeaveTO>();
		for(EmployeeApproveLeaveTO leaveTo:approveList)
		{
			if(leaveTo.getStatus().equalsIgnoreCase("Approved"))
				approvedList.add(leaveTo);
		}
		return approvedList;
	}


	public List<EmployeeApproveLeaveTO> getCanceledLeaveList(List<EmployeeApproveLeaveTO> leaveApproveList) 
	{
		List<EmployeeApproveLeaveTO>canceledList= new ArrayList<EmployeeApproveLeaveTO>();
		for(EmployeeApproveLeaveTO leaveTo:leaveApproveList)
		{
			if(leaveTo.getStatus().equalsIgnoreCase("Canceled"))
				canceledList.add(leaveTo);
		}
		return canceledList;
	}	
	
}
