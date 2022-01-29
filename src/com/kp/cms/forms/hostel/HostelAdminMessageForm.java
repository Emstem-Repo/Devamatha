package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelAdminMessageTO;
import com.kp.cms.to.hostel.LeaveTypeTo;

public class HostelAdminMessageForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hostelId;
	private String roomId;
	private String statusId;
	private String messageTypeId;
	private String messageType;
	private String status;
	private String fromDate;
	private String toDate;
	private String hlAdmnApplId;
	private String leaveTypeId;
	private String leaveId;
	private String approvedDate;
	private String remarks;
	private String commonId;
	private Map<Integer,String> statusMap=new HashMap<Integer,String>();
	private String complaintId;
	private String actionTaken;
	
	private Map<Integer,String> messageTypeMap=new HashMap<Integer,String>();
	private List<HostelAdminMessageTO> adminMessageTOList=new ArrayList<HostelAdminMessageTO>();
	private LeaveTypeTo leaveTypeTo=new LeaveTypeTo();
	private List<HlStatus> statusList=new ArrayList<HlStatus>();
	//private List<LeaveTypeTo> hlLeaveList=new ArrayList<LeaveTypeTo>();
	private String floorNo;
	
	public String getLeaveId() {
		return leaveId;
	}
	public String getActionTaken() {
		return actionTaken;
	}
	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
	}
	public String getComplaintId() {
		return complaintId;
	}
	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}
	public String getCommonId() {
		return commonId;
	}
	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}
	public List<HlStatus> getStatusList() {
		return statusList;
	}
	public void setStatusList(List<HlStatus> statusList) {
		this.statusList = statusList;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	
	public LeaveTypeTo getLeaveTypeTo() {
		return leaveTypeTo;
	}
	public void setLeaveTypeTo(LeaveTypeTo leaveTypeTo) {
		this.leaveTypeTo = leaveTypeTo;
	}
	public String getLeaveTypeId() {
		return leaveTypeId;
	}
	public void setLeaveTypeId(String leaveTypeId) {
		this.leaveTypeId = leaveTypeId;
	}
	public String getHlAdmnApplId() {
		return hlAdmnApplId;
	}
	public void setHlAdmnApplId(String hlAdmnApplId) {
		this.hlAdmnApplId = hlAdmnApplId;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public List<HostelAdminMessageTO> getAdminMessageTOList() {
		return adminMessageTOList;
	}
	public void setAdminMessageTOList(List<HostelAdminMessageTO> adminMessageTOList) {
		this.adminMessageTOList = adminMessageTOList;
	}
	public Map<Integer, String> getMessageTypeMap() {
		return messageTypeMap;
	}
	public void setMessageTypeMap(Map<Integer, String> messageTypeMap) {
		this.messageTypeMap = messageTypeMap;
	}
	public String getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(String messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}
	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
	}
	
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public void clear()
	{
		this.hostelId=null;
		this.roomId=null;
		this.statusId=null;
		this.fromDate=null;
		this.toDate=null;
		this.commonId=null;
		this.actionTaken=null;
		this.floorNo=null;
	}
	public void messageclear()
	{
		this.hostelId=null;
		this.roomId=null;
		this.messageTypeId=null;
		this.statusId=null;
		this.fromDate=null;
		this.toDate=null;
		this.commonId=null;
		this.actionTaken=null;
	}
	
	public void clearList()
	{
		this.statusMap=null;
		this.messageTypeMap=null;
		this.adminMessageTOList=null;
		this.leaveTypeTo=null;
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

}
