package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.forms.BaseActionForm;

import com.kp.cms.to.hostel.LeaveTypeTo;

public class HostelStudentViewMessageForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private String messageTypeId;
	private Map<Integer,String> messageTypeMap=new HashMap<Integer,String>();
	private List<LeaveTypeTo> leaveTypeTOList=new ArrayList<LeaveTypeTo>();
	private HlLeave hlLeave=new HlLeave();
	private LeaveTypeTo leaveTypeTo=new LeaveTypeTo();
	//private ComplaintsTO complaintTo=new ComplaintsTO();
	private String leaveId;
	/*public ComplaintsTO getComplaintTo() {
		return complaintTo;
	}
	public void setComplaintTo(ComplaintsTO complaintTo) {
		this.complaintTo = complaintTo;
	}*/
	public LeaveTypeTo getLeaveTypeTo() {
		return leaveTypeTo;
	}
	public void setLeaveTypeTo(LeaveTypeTo leaveTypeTo) {
		this.leaveTypeTo = leaveTypeTo;
	}
	public String getLeaveId() {
		return leaveId;
	}
	public void setLeaveId(String leaveId) {
		this.leaveId = leaveId;
	}
	public HlLeave getHlLeave() {
		return hlLeave;
	}
	public void setHlLeave(HlLeave hlLeave) {
		this.hlLeave = hlLeave;
	}
	public List<LeaveTypeTo> getLeaveTypeTOList() {
		return leaveTypeTOList;
	}
	public void setLeaveTypeTOList(List<LeaveTypeTo> leaveTypeTOList) {
		this.leaveTypeTOList = leaveTypeTOList;
	}
	public Map<Integer, String> getMessageTypeMap() {
		return messageTypeMap;
	}
	public void setMessageTypeMap(Map<Integer, String> messageTypeMap) {
		this.messageTypeMap = messageTypeMap;
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
	public String getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(String messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	
	
	public void clear()
	{
		this.fromDate=null;
		this.toDate=null;
		this.messageTypeId=null;
		
	}
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
}
