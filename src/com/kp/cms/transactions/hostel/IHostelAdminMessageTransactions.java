package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.Users;
public interface IHostelAdminMessageTransactions {
	
	public List<HlStatus> getHostelStatusList()throws Exception;
	public List<Object[]>  getHostelAdminMessageList(String str)throws Exception;
	public List<Object[]>  getLeaveStatusListByadmnApplId(String commonId,String messageTypeId) throws Exception;
	public boolean manageLeaveStatus(Object obj) throws Exception;
	public Users getUserNameByUserId(String id) throws Exception;
	public HlLeave getHlLeaveByLeaveId(Integer leaveId)throws Exception;
	//public List<HlLeave>  getStudentLeaveList(String hlformId,String type,String mode) throws Exception;
	public List<Object[]>  getStudentViewMessageList(String queryString) throws Exception;
	public List<Object[]> getEmployeeDetails() throws Exception;
	public HlApplicationForm  getHlApplicationFormByTypeId(String typeId) throws Exception;
	public List<Object[]> getStudentId() throws Exception;
	public HlComplaint getHlComplaintByComplaintId(Integer complaintId)throws Exception;
}