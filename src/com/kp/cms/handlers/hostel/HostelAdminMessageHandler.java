package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.hostel.HostelAdminMessageForm;
import com.kp.cms.forms.hostel.HostelStudentViewMessageForm;
import com.kp.cms.helpers.hostel.HostelAdminMessageHelper;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.HostelAdminMessageTO;
import com.kp.cms.to.hostel.HostelMessageTypeTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.hostel.IHostelAdminMessageTransactions;
import com.kp.cms.transactionsimpl.hostel.HostelAdminMessageTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelAdminMessageHandler {

	private static final Log log = LogFactory
			.getLog(HostelAdminMessageHandler.class);
	private static volatile HostelAdminMessageHandler hostelAdminMessageHandler = null;

	public static HostelAdminMessageHandler getInstance() {
		if (hostelAdminMessageHandler == null) {
			hostelAdminMessageHandler = new HostelAdminMessageHandler();
			return hostelAdminMessageHandler;
		}
		return hostelAdminMessageHandler;
	}

	public Map<Integer, String> getHostelStatusMap() throws Exception {
		log
				.info("entering into getHostelList in HostelAdminMessageHandler class..");
		List<HlStatus> statusList = null;
		Map<Integer, String> statusMap = new HashMap<Integer, String>();
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl
				.getInstance();
		statusList = hostelAdminMessageTxImpl.getHostelStatusList();
		if (statusList != null && !statusList.isEmpty()) {
			for (HlStatus hlStatus : statusList) {
				if(hlStatus.getId()==1 || hlStatus.getId()==4 || hlStatus.getId()==9 || hlStatus.getId()==10)
				statusMap.put(hlStatus.getId(), hlStatus.getStatusType());
			}
		}
		log
				.info("leaving from getHostelList in HostelAdminMessageHandler class..");
		return statusMap;
	}
	
	

	public List<HlStatus> getStatusList() throws Exception {
		log.info("entering into getStatusList in HostelAdminMessageHandler class..");
		
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		List<HlStatus> statusList = hostelAdminMessageTxImpl.getHostelStatusList();
		log.info("entering into getStatusList in HostelAdminMessageHandler class..");
		return statusList;
	}
	
	public Map<Integer, String> getStudentIdMap() throws Exception {
		log
				.info("entering into getStudentIdMap in HostelAdminMessageHandler class..");
		List<Object[]> studentList = null;
		String stdId="";
		Integer admnId=null;
		Map<Integer, String> studentIdMap = new HashMap<Integer, String>();
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl
				.getInstance();
		studentList = hostelAdminMessageTxImpl.getStudentId();
		if (studentList != null && !studentList.isEmpty()) {
			
			for (Object[] obj : studentList) {
				stdId="";
				admnId=null;
				if(obj[0]!=null)
				{
					stdId=obj[0].toString();
				}
				if(obj[1]!=null)
				{
					admnId=Integer.valueOf(obj[1].toString());
				}
				studentIdMap.put(admnId, stdId);
					}
				
		}
		log.info("leaving from getStudentIdMap in HostelAdminMessageHandler class..");
		return studentIdMap;
	}
	

	public Map<Integer, String> getEmployeeDetails() throws Exception {
		log.info("entering into getEmployeeDetails in HostelAdminMessageHandler class..");
		List<Object[]> employeesList = null;
		String name = "";
		Map<Integer, String> employeeMap = new HashMap<Integer, String>();
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		employeesList = hostelAdminMessageTxImpl.getEmployeeDetails();
		for (Object obj[] : employeesList) {
			name="";
			if (obj[0] != null) {
				if (obj[1] != null) {
					name = name + obj[1];
				}
				if (obj[2] != null) {
					name = name + obj[2];
				}
				if (obj[3] != null) {
					name = name + obj[3];
				}
			}
			employeeMap.put(Integer.valueOf(obj[0].toString()), name);
		}
		log.info("leaving from getEmployeeDetails in HostelAdminMessageHandler class..");
		return employeeMap;
	}

	public Map<Integer, String> getHostelMessageType() throws Exception {
		log.info("entering into getHostelMessageType in HostelAdminMessageHandler class..");
		Map<Integer, String> messageTypeMap = new HashMap<Integer, String>();
		HostelMessageTypeTO hostelMessageTypeIdTo = new HostelMessageTypeTO();
		HostelMessageTypeTO hostelMessageTypeNameTo = new HostelMessageTypeTO();
		hostelMessageTypeIdTo.setId(1);
		hostelMessageTypeIdTo.setMessageType("Complaint");

		hostelMessageTypeNameTo.setId(2);
		hostelMessageTypeNameTo.setMessageType("Leave");

		messageTypeMap.put(hostelMessageTypeIdTo.getId(), hostelMessageTypeIdTo.getMessageType());
		messageTypeMap.put(hostelMessageTypeNameTo.getId(),hostelMessageTypeNameTo.getMessageType());
		log.info("leaving from getHostelMessageType in HostelAdminMessageHandler class..");
		return messageTypeMap;
	}

	public List<HostelAdminMessageTO> getHostelAdminMessageList(
			HostelAdminMessageForm hostelAdminMessageForm) throws Exception {
		List<HostelAdminMessageTO> hlApplicationFormSortedByDateToList = new ArrayList<HostelAdminMessageTO>();
		boolean flag=false;
		log.info("entering into getHostelAdminMessageList in HostelAdminMessageHandler class..");
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		String dynamicQuery = HostelAdminMessageHelper.buildQuery(hostelAdminMessageForm);
		Map<Integer, String> hostelMessageTypeMap = getHostelMessageType();
		Map<Integer, String> hostelEmployeeMap = getEmployeeDetails();
		Map<Integer, String> hostelstudentMap=  getStudentIdMap();
		List<Object[]> hlApplicationFormList = hostelAdminMessageTxImpl.getHostelAdminMessageList(dynamicQuery);
		List<HostelAdminMessageTO> hlApplicationFormToList = HostelAdminMessageHelper.convertFromObjecttoTO(hostelAdminMessageForm,hlApplicationFormList, hostelMessageTypeMap,hostelEmployeeMap,hostelstudentMap);
		if (hostelAdminMessageForm.getFromDate() != null && !hostelAdminMessageForm.getFromDate().equals("")) {
			flag=true;
			Date fromDate = CommonUtil.ConvertStringToDate(hostelAdminMessageForm.getFromDate());
			if (hostelAdminMessageForm.getToDate() != null && !hostelAdminMessageForm.getToDate().equals("")) {
				Date toDate = CommonUtil.ConvertStringToDate(hostelAdminMessageForm.getToDate());
				for (HostelAdminMessageTO hostelAdmnMsgTo : hlApplicationFormToList) {
					if (CommonUtil.ConvertStringToDate(hostelAdmnMsgTo.getSentDate()).compareTo(toDate) <= 0 && CommonUtil.ConvertStringToDate(hostelAdmnMsgTo.getSentDate()).compareTo(fromDate) >= 0) {
						hlApplicationFormSortedByDateToList.add(hostelAdmnMsgTo);
					}
				}
			} else {
				for (HostelAdminMessageTO hostelAdmnMsgTo : hlApplicationFormToList) {
					if (CommonUtil.ConvertStringToDate(
							hostelAdmnMsgTo.getSentDate()).compareTo(fromDate) >= 0) {
						hlApplicationFormSortedByDateToList.add(hostelAdmnMsgTo);
					}
				}
			}
		} else if (hostelAdminMessageForm.getToDate() != null
				&& !hostelAdminMessageForm.getToDate().equals("")) {
			flag=true;
			Date toDate = CommonUtil.ConvertStringToDate(hostelAdminMessageForm.getToDate());
			for (HostelAdminMessageTO hostelAdmnMsgTo : hlApplicationFormToList) {
				if (CommonUtil.ConvertStringToDate(
						hostelAdmnMsgTo.getSentDate()).compareTo(toDate) <= 0) {
					hlApplicationFormSortedByDateToList.add(hostelAdmnMsgTo);
				}

			}
		}
		
		if(flag)
		{
			log.info("leaving from getHostelAdminMessageList in HostelAdminMessageHandler class..");
			return hlApplicationFormSortedByDateToList;
		}
		else
		{
			log.info("leaving from getHostelAdminMessageList in HostelAdminMessageHandler class..");
			return hlApplicationFormToList;
		}
	}

	public LeaveTypeTo getLeaveStatusListByadmnApplId(
			HostelAdminMessageForm hostelAdminMessageForm) throws Exception {
		log.info("entering into getLeaveStatusListByadmnApplId in HostelAdminMessageHandler class..");
		 List<Object[]> hlLeaveList=null;
		 Map<Integer, String>  studentIdMap=getStudentIdMap();
		LeaveTypeTo leaveTypeTo = null;
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		hlLeaveList = hostelAdminMessageTxImpl.getLeaveStatusListByadmnApplId(hostelAdminMessageForm.getLeaveId(),hostelAdminMessageForm.getMessageTypeId());
		leaveTypeTo = HostelAdminMessageHelper.getLeaveTypeTo(hlLeaveList,hostelAdminMessageForm,studentIdMap);
		log.info("leaving from getLeaveStatusListByadmnApplId in HostelAdminMessageHandler class..");
		return leaveTypeTo;
	}
	
	public ComplaintsTO getComplaintStatusListByadmnApplId(
			HostelAdminMessageForm hostelAdminMessageForm) throws Exception {
		log.info("entering into getComplaintStatusListByadmnApplId in HostelAdminMessageHandler class..");
		 List<Object[]> hlComplaintList=null;
		 Map<Integer, String>  studentIdMap=getStudentIdMap();
		 ComplaintsTO complaintsTo = null;
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		hlComplaintList = hostelAdminMessageTxImpl.getLeaveStatusListByadmnApplId(hostelAdminMessageForm.getLeaveId(),hostelAdminMessageForm.getMessageTypeId());
		complaintsTo = HostelAdminMessageHelper.getComplaintTypeTo(hlComplaintList,hostelAdminMessageForm,studentIdMap);
		log.info("leaving from getComplaintStatusListByadmnApplId in HostelAdminMessageHandler class..");
		return complaintsTo;
	}
	

	public boolean manageLeaveStatus(
			HostelAdminMessageForm hostelAdminMessageForm) throws Exception {
		log.info("entering into manageLeaveStatus in HostelAdminMessageHandler class..");
		Users users = null;
		HlLeave hlLeave = new HlLeave();
		HlComplaint hlComplaint=new HlComplaint();

		HlStatus hlStatus = new HlStatus();

		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl.getInstance();
		if(hostelAdminMessageForm.getMessageTypeId().equalsIgnoreCase("2"))
		{
			if (hostelAdminMessageForm.getLeaveId() != null) {
				hlLeave = hostelAdminMessageTxImpl.getHlLeaveByLeaveId(Integer.valueOf(hostelAdminMessageForm.getLeaveId()));
			}
			if (hostelAdminMessageForm.getUserId() != null && !hostelAdminMessageForm.getUserId().equals("")) {
				users = hostelAdminMessageTxImpl.getUserNameByUserId(hostelAdminMessageForm.getUserId());
				if (users.getUserName() != null && !users.getUserName().equals("")) {
					hlLeave.setApprovedBy(users.getUserName());
				}
			}
	
			if (hostelAdminMessageForm.getApprovedDate() != null) {
				hlLeave.setApprovedDate(CommonUtil.ConvertStringToDate(hostelAdminMessageForm.getApprovedDate()));
			}
			if (hostelAdminMessageForm.getRemarks() != null && !hostelAdminMessageForm.getRemarks().equals("")) {
				hlLeave.setApproverRemarks(hostelAdminMessageForm.getRemarks());
			}
			if (hostelAdminMessageForm.getStatusId() != null && !hostelAdminMessageForm.getStatusId().equals("")) {
				hlStatus.setId(Integer.valueOf(hostelAdminMessageForm.getStatusId()).intValue());
				hlLeave.setHlStatus(hlStatus);
			} else {
				hlLeave.setHlStatus(null);
			}
			hlLeave.setModifiedBy(hostelAdminMessageForm.getUserId());
			hlLeave.setLastModifiedDate(new Date());
			if (hostelAdminMessageForm.getLeaveId() != null && !hostelAdminMessageForm.getLeaveId().equals("")) {
				hlLeave.setId(Integer.valueOf(hostelAdminMessageForm.getLeaveId()).intValue());
			}
			hostelAdminMessageTxImpl.manageLeaveStatus(hlLeave);
		}
		else
		{
			if(hostelAdminMessageForm.getComplaintId()!=null && !hostelAdminMessageForm.getComplaintId().equals(""))
			{
				hlComplaint=hostelAdminMessageTxImpl.getHlComplaintByComplaintId(Integer.valueOf(hostelAdminMessageForm.getComplaintId()));
				//hlComplaint.setId(Integer.valueOf(hostelAdminMessageForm.getComplaintId()));
			}
			if(hostelAdminMessageForm.getActionTaken()!=null && !hostelAdminMessageForm.getActionTaken().equals(""))
			{
				hlComplaint.setActionTaken(hostelAdminMessageForm.getActionTaken());
			}
			
			if (hostelAdminMessageForm.getUserId() != null && !hostelAdminMessageForm.getUserId().equals("")) {
				users = hostelAdminMessageTxImpl.getUserNameByUserId(hostelAdminMessageForm.getUserId());
				if (users.getUserName() != null && !users.getUserName().equals("")) {
					hlComplaint.setApprovedBy(users.getUserName());
				}
			}
	
			if (hostelAdminMessageForm.getApprovedDate() != null) {
				hlComplaint.setApprovedDate(CommonUtil.ConvertStringToDate(hostelAdminMessageForm.getApprovedDate()));
			}
			if (hostelAdminMessageForm.getStatusId() != null && !hostelAdminMessageForm.getStatusId().equals("")) {
				hlStatus.setId(Integer.valueOf(hostelAdminMessageForm.getStatusId()).intValue());
				hlComplaint.setHlStatus(hlStatus);
			} else {
				hlComplaint.setHlStatus(null);
			}
			hlComplaint.setModifiedBy(hostelAdminMessageForm.getUserId());
			hlComplaint.setLastModifiedDate(new Date());
			hostelAdminMessageTxImpl.manageLeaveStatus(hlComplaint);
		}
		log.info("leaving from manageLeaveStatus in HostelAdminMessageHandler class..");
		return true;
	}

	public List<LeaveTypeTo> getStudentLeaveList(
			HostelStudentViewMessageForm hostelStdMsgForm) throws Exception {
		log.info("entering into getStudentLeaveList in HostelAdminMessageHandler class..");
		Users users = null;
		String mode = null;
		String typeId = null;
		String dynamicQuery=null;
		boolean flag=false;
		List<LeaveTypeTo> leaveTypeDateToList = new ArrayList<LeaveTypeTo>();
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl
				.getInstance();
		dynamicQuery = HostelAdminMessageHelper.studentViewMessageListbuildQuery(hostelStdMsgForm);
		List<Object[]> hlLeaveList=  hostelAdminMessageTxImpl.getStudentViewMessageList(dynamicQuery) ;
		List<LeaveTypeTo> leaveTypeTOList=HostelAdminMessageHelper.convertStudentViewMessageListToLeaveTypeTo(hostelStdMsgForm, hlLeaveList);

		// for dates

		if (hostelStdMsgForm.getFromDate() != null
				&& !hostelStdMsgForm.getFromDate().equals("")) {
			flag=true;
			Date fromDate = CommonUtil.ConvertStringToDate(hostelStdMsgForm
					.getFromDate());
			if (hostelStdMsgForm.getToDate() != null
					&& !hostelStdMsgForm.getToDate().equals("")) {
				Date toDate = CommonUtil.ConvertStringToDate(hostelStdMsgForm
						.getToDate());
				for (LeaveTypeTo leaveTypeTo : leaveTypeTOList) {
					if (CommonUtil.ConvertStringToDate(
							leaveTypeTo.getSentDate()).compareTo(toDate) <= 0
							&& CommonUtil.ConvertStringToDate(
									leaveTypeTo.getSentDate()).compareTo(
									fromDate) >= 0) {
						leaveTypeDateToList.add(leaveTypeTo);
					}
				}
			} else {
				for (LeaveTypeTo leaveTypeTo : leaveTypeTOList) {
					if (CommonUtil.ConvertStringToDate(
							leaveTypeTo.getSentDate()).compareTo(fromDate) >= 0) {
						leaveTypeDateToList.add(leaveTypeTo);
					}
				}
			}
		} else if (hostelStdMsgForm.getToDate() != null
				&& !hostelStdMsgForm.getToDate().equals("")) {
			flag=true;
			Date toDate = CommonUtil.ConvertStringToDate(hostelStdMsgForm
					.getToDate());
			for (LeaveTypeTo leaveTypeTo : leaveTypeTOList) {
				if (CommonUtil.ConvertStringToDate(leaveTypeTo.getSentDate())
						.compareTo(toDate) <= 0) {
					leaveTypeDateToList.add(leaveTypeTo);
				}

			}
		}
		
		if(flag)
		{
			log.info("leaving from getStudentLeaveList in HostelAdminMessageHandler class..");
			return leaveTypeDateToList;
		}
		else
		{
			log.info("leaving from getStudentLeaveList in HostelAdminMessageHandler class..");
			return leaveTypeTOList;
		}
		
	}

	public LeaveTypeTo getLeaveStatusByLeaveId(
			HostelStudentViewMessageForm hostelStudentViewMessageForm)
			throws Exception {
		log.info("entering into getLeaveStatusByLeaveId in HostelAdminMessageHandler class..");
		 List<Object[]> hlLeaveList=null;
		 Map<Integer, String>  studentIdMap=getStudentIdMap();
		LeaveTypeTo leaveTypeTo = null;
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl
				.getInstance();
		hlLeaveList = hostelAdminMessageTxImpl.getLeaveStatusListByadmnApplId(hostelStudentViewMessageForm.getLeaveId(),hostelStudentViewMessageForm.getMessageTypeId());
		leaveTypeTo = HostelAdminMessageHelper.getStudentViewMessageLeaveTypeTo(hlLeaveList, hostelStudentViewMessageForm, studentIdMap);
		log.info("leaving from getLeaveStatusByLeaveId in HostelAdminMessageHandler class..");
		return leaveTypeTo;
	}
	
	public ComplaintsTO getComplaintStatusByComplaintId(
			HostelStudentViewMessageForm hostelStudentViewMessageForm)
			throws Exception {
		log.info("entering into getComplaintStatusByComplaintId in HostelAdminMessageHandler class..");
		 List<Object[]> hlComplaintList=null;
		 Map<Integer, String>  studentIdMap=getStudentIdMap();
		ComplaintsTO complaintTypeTo = null;
		IHostelAdminMessageTransactions hostelAdminMessageTxImpl = HostelAdminMessageTransactionImpl
				.getInstance();
		hlComplaintList = hostelAdminMessageTxImpl.getLeaveStatusListByadmnApplId(hostelStudentViewMessageForm.getLeaveId(),hostelStudentViewMessageForm.getMessageTypeId());
		complaintTypeTo = HostelAdminMessageHelper.getStudentViewMessageComplaintTypeTo(hlComplaintList, hostelStudentViewMessageForm, studentIdMap);
		log.info("leaving from getComplaintStatusByComplaintId in HostelAdminMessageHandler class..");
		return complaintTypeTo;
	}
	
	

}
