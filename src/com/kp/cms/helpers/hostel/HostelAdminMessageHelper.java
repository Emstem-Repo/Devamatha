package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlComplaint;
import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.HostelAdminMessageForm;
import com.kp.cms.forms.hostel.HostelStudentViewMessageForm;
import com.kp.cms.to.hostel.ComplaintsTO;
import com.kp.cms.to.hostel.HostelAdminMessageTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.utilities.CommonUtil;


public class HostelAdminMessageHelper {
	private static final Log log=LogFactory.getLog(HostelAdminMessageHelper.class);
	public static String buildQuery(HostelAdminMessageForm hostelAdminMessageForm) {
		log.info("entered buildQuery..");
		String searchQuery="";
		if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equalsIgnoreCase("") )
		{
	
			if(hostelAdminMessageForm.getMessageTypeId().equalsIgnoreCase("2"))
			{
				searchQuery=searchQuery+"select hlLeave,aform,rtxn from HlLeave hlLeave, HlApplicationForm aform, HlRoomTransaction rtxn "+
				"where  hlLeave.hlApplicationForm.id= aform.id "+
				"and rtxn.hlApplicationForm.id= aform.id "+
				"and aform.id= rtxn.hlApplicationForm.id "+
				"and hlLeave.isActive=1";
				
				if(hostelAdminMessageForm.getHostelId()!=null && !hostelAdminMessageForm.getHostelId().equals(""))
				{
					searchQuery=searchQuery+" and hlLeave.hlHostel.id="+hostelAdminMessageForm.getHostelId();
				}
				if(hostelAdminMessageForm.getFloorNo()!=null  &&  !hostelAdminMessageForm.getFloorNo().equals(""))
				{
					searchQuery=searchQuery+" and rtxn.hlRoom.floorNo= "+hostelAdminMessageForm.getFloorNo();
				}
				if(hostelAdminMessageForm.getRoomId()!=null  &&  !hostelAdminMessageForm.getRoomId().equals(""))
				{
					searchQuery=searchQuery+" and rtxn.hlRoom.id= "+hostelAdminMessageForm.getRoomId();
				}
				if(hostelAdminMessageForm.getStatusId()!=null  && !hostelAdminMessageForm.getStatusId().equals(""))
				{
					searchQuery=searchQuery+" and hlLeave.hlStatus.id= "+hostelAdminMessageForm.getStatusId();
				}
				searchQuery=searchQuery+" group by hlLeave.id";
			}
			else
			{
				searchQuery=searchQuery+"select hlComplaint,aform,rtxn "+
				"from HlComplaint hlComplaint, HlRoomTransaction rtxn,HlApplicationForm aform "+
				"where rtxn.hlApplicationForm.id= aform.id "+
				"and aform.id= rtxn.hlApplicationForm.id "+
				"and hlComplaint.hlApplicationForm.id= aform.id "+
				"and hlComplaint.isActive=1 ";
				if(hostelAdminMessageForm.getHostelId()!=null && !hostelAdminMessageForm.getHostelId().equals(""))
				{
					searchQuery=searchQuery+" and hlComplaint.hlHostel.id="+hostelAdminMessageForm.getHostelId();
				}
				if(hostelAdminMessageForm.getFloorNo()!=null  &&  !hostelAdminMessageForm.getFloorNo().equals(""))
				{
					searchQuery=searchQuery+" and rtxn.hlRoom.floorNo= "+hostelAdminMessageForm.getFloorNo();
				}
				if(hostelAdminMessageForm.getRoomId()!=null  &&  !hostelAdminMessageForm.getRoomId().equals(""))
				{
					searchQuery=searchQuery+" and rtxn.hlRoom.id= "+hostelAdminMessageForm.getRoomId();
				}
				if(hostelAdminMessageForm.getStatusId()!=null  && !hostelAdminMessageForm.getStatusId().equals(""))
				{
					searchQuery=searchQuery+" and hlComplaint.hlStatus.id= "+hostelAdminMessageForm.getStatusId();
				}
				searchQuery=searchQuery+" group by hlComplaint.id";
			}
			
		}
		log.info("exit buildQuery..");
		return searchQuery;
}
	
	public static List<HostelAdminMessageTO> convertFromObjecttoTO(HostelAdminMessageForm hostelAdminMessageForm,List<Object[]> adminMessageList,Map<Integer,String> hostelMessageTypeMap,Map<Integer,String> hostelEmployeeMap,Map<Integer, String> hostelstudentMap)
	{
		HostelAdminMessageTO hostelAdminMessageTO=null;
		String fromName="";
		boolean isLeave=false;
		if(hostelAdminMessageForm.getMessageTypeId().equalsIgnoreCase("2"))
		{
			isLeave=true;
		}
		List<HostelAdminMessageTO> hostelAdminMessageListTO=new ArrayList<HostelAdminMessageTO>();
		for(Object obj[]:adminMessageList)
		{
			hostelAdminMessageTO=new HostelAdminMessageTO();
			fromName="";
			if(obj[0]!=null && isLeave)
			{
				HlLeave hlLeave=(HlLeave)obj[0];
				hostelAdminMessageTO.setLeaveId(Integer.toString(hlLeave.getId()));
				hostelAdminMessageTO.setStatus(hlLeave.getHlStatus().getStatusType());
				if(hlLeave.getCreatedDate()!=null)
				hostelAdminMessageTO.setSentDate(CommonUtil.formatSqlDate(hlLeave.getCreatedDate().toString()));
			}else{
				HlComplaint hlComplaint=(HlComplaint)obj[0];
				hostelAdminMessageTO.setLeaveId(Integer.toString(hlComplaint.getId()));
				hostelAdminMessageTO.setStatus(hlComplaint.getHlStatus().getStatusType());
				if(hlComplaint.getCreatedDate()!=null)
				hostelAdminMessageTO.setSentDate(CommonUtil.formatSqlDate(hlComplaint.getCreatedDate().toString()));
			}
			if(obj[1]!=null)
			{
				HlApplicationForm applicationForm=(HlApplicationForm)obj[1];
				hostelAdminMessageTO.setHostelName(applicationForm.getHlHostelByHlApprovedHostelId().getName());
				String name="";
				if(applicationForm.getIsStaff()==null || !applicationForm.getIsStaff()){
					PersonalData personalData=applicationForm.getAdmAppln().getPersonalData();
					if(personalData.getFirstName()!=null){
						name=name+personalData.getFirstName();
					}
					if(personalData.getMiddleName()!=null){
						name=name+personalData.getMiddleName();
					}
					if(personalData.getLastName()!=null){
						name=name+personalData.getLastName();
					}
					hostelAdminMessageTO.setFromName(name);
					Set<Student> students=applicationForm.getAdmAppln().getStudents();
					Iterator<Student> itr=students.iterator();
					if (itr.hasNext()) {
						Student student = (Student) itr.next();
						hostelAdminMessageTO.setCommonId(student.getRegisterNo());
					}
				}else{
					Employee employee=applicationForm.getEmployee();
					if(employee.getFirstName()!=null){
						name=name+employee.getFirstName();
					}
				/*	if(employee.getMiddleName()!=null){
						name=name+employee.getMiddleName();
					}
					if(employee.getLastName()!=null){
						name=name+employee.getLastName();
					}*/
					hostelAdminMessageTO.setFromName(name);
					hostelAdminMessageTO.setCommonId(employee.getCode());
				}
				
				
			}
			if(obj[2]!=null && !obj[2].equals("") )
			{
				HlRoomTransaction hlRoomTransaction=(HlRoomTransaction)obj[2];
				hostelAdminMessageTO.setRoomNo(hlRoomTransaction.getHlRoom().getName());
			}
			if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equals(""))
			{
				if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equals(""))
				{
					hostelAdminMessageTO.setMessageTypeId(hostelAdminMessageForm.getMessageTypeId());
				}
				if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equals(""))
				{
					hostelAdminMessageTO.setMessageType(hostelMessageTypeMap.get(Integer.valueOf(hostelAdminMessageForm.getMessageTypeId())));
				}
				}
			hostelAdminMessageListTO.add(hostelAdminMessageTO);
		}
		return hostelAdminMessageListTO;
	}
	
	 public static LeaveTypeTo getLeaveTypeTo(List<Object[]> hlLeaveList,HostelAdminMessageForm hostelAdminMessageForm,Map<Integer, String>  studentIdMap)
	 {
		 LeaveTypeTo leaveTypeTo=new LeaveTypeTo();
		 	//String pattern="dd/MM/yyyy";
		 		if(hlLeaveList!=null && !hlLeaveList.isEmpty())
					{
						//HlLeave hlLeave=(HlLeave)obj[0];
		 				if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equals("") && hostelAdminMessageForm.getMessageTypeId().equals("2"))
		 				{
						leaveTypeTo.setMessageTypeName("Leave");
		 				}
		 				else
		 				{
		 					leaveTypeTo.setMessageTypeName("Complaint");
		 				}
						for(Object obj[]:hlLeaveList){
							if(obj[0]!=null){
								leaveTypeTo.setLeaveId(String.valueOf(obj[0]));
							}
							
							if(obj[1]!=null){
								if(obj[2]!=null){
									leaveTypeTo.setCommonId(obj[2].toString());
								}
								else{
									leaveTypeTo.setCommonId(String.valueOf(studentIdMap.get(obj[1])));
								}
							}
							if(obj[3]!=null){
								leaveTypeTo.setStartDate(CommonUtil.formatSqlDate1(String.valueOf(obj[3])));
							}
							if(obj[4]!=null){
								leaveTypeTo.setEndDate(CommonUtil.formatSqlDate1(String.valueOf(obj[4])));
							}
							if(obj[5]!=null){
								leaveTypeTo.setReasons(obj[5].toString());
							}
							if(obj[6]!=null){
								leaveTypeTo.setLeaveTypeName(obj[6].toString());
							}
							if(obj[7]!=null){
								leaveTypeTo.setStatusId(String.valueOf(obj[7]));
							}
							if(obj[8]!=null){
								leaveTypeTo.setStatusName(String.valueOf(obj[8]));
							}
							
							if(obj[9]!=null){
								leaveTypeTo.setApprovedBy(obj[9].toString());
							}
							if(obj[10]!=null){
								leaveTypeTo.setApprovedDate(CommonUtil.formatSqlDate1(String.valueOf(obj[10])));
							}
							if(obj[11]!=null){
								leaveTypeTo.setRemarks(obj[11].toString());
							}
						}
				}
				
		 return leaveTypeTo;
	 }
	 
	 
	 public static ComplaintsTO getComplaintTypeTo(List<Object[]> hlComplaintList,HostelAdminMessageForm hostelAdminMessageForm,Map<Integer, String>  studentIdMap)
	 {
		 ComplaintsTO complaintsTo=new ComplaintsTO();
		 	//String pattern="dd/MM/yyyy";
		 		if(hlComplaintList!=null && !hlComplaintList.isEmpty())
					{
						//HlLeave hlLeave=(HlLeave)obj[0];
		 				if(hostelAdminMessageForm.getMessageTypeId()!=null && !hostelAdminMessageForm.getMessageTypeId().equals("") && hostelAdminMessageForm.getMessageTypeId().equals("2"))
		 				{
		 					complaintsTo.setMessageTypeName("Leave");
		 				}
		 				else
		 				{
		 					complaintsTo.setMessageTypeName("Complaint");
		 				}
						for(Object obj[]:hlComplaintList){
							if(obj[0]!=null){
								complaintsTo.setComplaintId(String.valueOf(obj[0]));
							}							
							if(obj[1]!=null){
								complaintsTo.setCommonId(String.valueOf(studentIdMap.get(obj[1])));
							}
							if(obj[3]!=null){
								//complaintsTo.setStartDate(CommonUtil.formatSqlDate1(String.valueOf(obj[3])));
								complaintsTo.setComplaintTypeName(String.valueOf(obj[3]));
							}
							if(obj[4]!=null){
								complaintsTo.setTitle(String.valueOf(obj[4]));
							}
							if(obj[5]!=null){
								complaintsTo.setDesc(obj[5].toString());
							}
							if(obj[6]!=null){
								complaintsTo.setActionTaken(obj[6].toString());
							}
							if(obj[7]!=null){
								complaintsTo.setStatusId(obj[7].toString());
							}
							if(obj[8]!=null){
								complaintsTo.setApprovedBy(obj[8].toString());
							}
							if(obj[9]!=null){
								complaintsTo.setApprovedDate(CommonUtil.formatSqlDate1(obj[9].toString()));
							}
							if(obj[10]!=null)
							{
								complaintsTo.setStatusName(obj[10].toString());
							}
							
						}
				}
				
		 return complaintsTo;
	 }
	 
	
	 public static String studentViewMessageListbuildQuery(HostelStudentViewMessageForm hostelStdMsgForm) {
			log.info("entered studentViewMessageListbuildQuery..");
			String searchCriteria="";
			if(hostelStdMsgForm.getMessageTypeId().equalsIgnoreCase("2"))
			{
					searchCriteria=searchCriteria+"select h.id, h.hlApplicationForm.admAppln.personalData.firstName, h.hlApplicationForm.admAppln.personalData.middleName,h.hlApplicationForm.admAppln.personalData.lastName, h.createdDate, h.hlStatus.statusType " +
							"from HlLeave h " +
							"join h.hlApplicationForm.admAppln.students s " +
							"where  h.isActive= 1 " +
							"and s.id=(select sl.student.id from StudentLogin sl where sl.id="+hostelStdMsgForm.getUserId()+") and h.hlStatus.id=2";
			}
			else
			{
					searchCriteria=searchCriteria+"select h.id,h.hlApplicationForm.admAppln.personalData.firstName, h.hlApplicationForm.admAppln.personalData.middleName, h.hlApplicationForm.admAppln.personalData.lastName, h.createdDate, h.hlStatus.statusType " +
							"from HlComplaint h " +
							"join h.hlApplicationForm.admAppln.students s " +
							"where h.isActive=1" +
							"and s.id=(select sl.student.id from StudentLogin sl where sl.id="+hostelStdMsgForm.getUserId()+") and h.hlStatus.id=2";
			}
			
			log.info("leaving from studentViewMessageListbuildQuery..");
			return searchCriteria;
	}

	public static  List<LeaveTypeTo> convertStudentViewMessageListToLeaveTypeTo(HostelStudentViewMessageForm hostelStdMsgForm,List<Object[]> leaveList)
	{
		List<LeaveTypeTo> leaveTypeList=new ArrayList<LeaveTypeTo>();
		String sentBy = "";
		LeaveTypeTo leaveTypeTo=null;
		for(Object obj[]:leaveList)
		{
			leaveTypeTo=new LeaveTypeTo();
			sentBy = "";
			if(obj[0]!=null){
				//sentBy=sentBy+String.valueOf(obj[0]);
				leaveTypeTo.setLeaveId(String.valueOf(obj[0]));
			}
			if(obj[1]!=null){
				sentBy=sentBy+String.valueOf(obj[1]);
			}
			if(obj[2]!=null){
				sentBy=sentBy+String.valueOf(obj[2]);
			}
			if(obj[3]!=null){
				sentBy=sentBy+String.valueOf(obj[3]);
			}
			leaveTypeTo.setSentBy(sentBy);
			if(obj[4]!=null){
				leaveTypeTo.setSentDate(CommonUtil.formatSqlDate1(String.valueOf(obj[4])));
			}
			if(obj[5]!=null){
				leaveTypeTo.setStatusName(obj[5].toString());
			}
			if(hostelStdMsgForm.getMessageTypeId().equalsIgnoreCase("2"))
			{
				leaveTypeTo.setMessageTypeName("Leave");
			}
			else
			{
			leaveTypeTo.setMessageTypeName("Complaint");
			}
			leaveTypeList.add(leaveTypeTo);
		}
		return leaveTypeList;
	}
	 
	 
	 
	 public static LeaveTypeTo getStudentViewMessageLeaveTypeTo( List<Object[]> hlLeaveList,HostelStudentViewMessageForm hostelStudentViewMessageForm,Map<Integer, String>  studentIdMap)
	 {
		 LeaveTypeTo leaveTypeTo=new LeaveTypeTo();
		 	//String pattern="dd/MM/yyyy";
		 		if(hlLeaveList!=null && !hlLeaveList.isEmpty())
					{
						//HlLeave hlLeave=(HlLeave)obj[0];
		 				if(hostelStudentViewMessageForm.getMessageTypeId()!=null && !hostelStudentViewMessageForm.getMessageTypeId().equals("") && hostelStudentViewMessageForm.getMessageTypeId().equals("2"))
		 				{
						leaveTypeTo.setMessageTypeName("Leave");
		 				}
		 				else
		 				{
		 					leaveTypeTo.setMessageTypeName("Complaint");
		 				}
						for(Object obj[]:hlLeaveList){
							if(obj[0]!=null){
								leaveTypeTo.setLeaveId(String.valueOf(obj[0]));
							}
							
							if(obj[1]!=null){
								if(obj[2]!=null){
									leaveTypeTo.setCommonId(obj[2].toString());
								}
								else{
									leaveTypeTo.setCommonId(String.valueOf(studentIdMap.get(obj[1])));
								}
							}
							if(obj[3]!=null){
								leaveTypeTo.setStartDate(CommonUtil.formatSqlDate1(String.valueOf(obj[3])));
							}
							if(obj[4]!=null){
								leaveTypeTo.setEndDate(CommonUtil.formatSqlDate1(String.valueOf(obj[4])));
							}
							if(obj[5]!=null){
								leaveTypeTo.setReasons(obj[5].toString());
							}
							if(obj[6]!=null){
								leaveTypeTo.setLeaveTypeName(obj[6].toString());
							}
							if(obj[7]!=null){
								leaveTypeTo.setStatusId(String.valueOf(obj[7]));
							}
							
							if(obj[8]!=null){
								leaveTypeTo.setStatusName(String.valueOf(obj[8]));
							}
							if(obj[9]!=null){
								leaveTypeTo.setApprovedBy(obj[9].toString());
							}
							if(obj[10]!=null){
								leaveTypeTo.setApprovedDate(CommonUtil.formatSqlDate1(String.valueOf(obj[10])));
							}
							if(obj[11]!=null){
								leaveTypeTo.setRemarks(obj[11].toString());
							}
						}
				}
				
		 return leaveTypeTo;
					
	 }
	 
	 public static ComplaintsTO getStudentViewMessageComplaintTypeTo(List<Object[]> hlComplaintList,HostelStudentViewMessageForm hostelStudentViewMessageForm,Map<Integer, String>  studentIdMap)
	 {
		 ComplaintsTO complaintsTo=new ComplaintsTO();
		 	//String pattern="dd/MM/yyyy";
		 		if(hlComplaintList!=null && !hlComplaintList.isEmpty())
					{
						//HlLeave hlLeave=(HlLeave)obj[0];
		 				if(hostelStudentViewMessageForm.getMessageTypeId()!=null && !hostelStudentViewMessageForm.getMessageTypeId().equals("") && hostelStudentViewMessageForm.getMessageTypeId().equals("2"))
		 				{
		 					complaintsTo.setMessageTypeName("Leave");
		 				}
		 				else
		 				{
		 					complaintsTo.setMessageTypeName("Complaint");
		 				}
						for(Object obj[]:hlComplaintList){
							if(obj[0]!=null){
								complaintsTo.setComplaintId(String.valueOf(obj[0]));
							}							
							if(obj[1]!=null){
								if(obj[2]!=null){
									complaintsTo.setCommonId(obj[2].toString());
								}
								else{
									complaintsTo.setCommonId(String.valueOf(studentIdMap.get(obj[1])));
								}
							}
							if(obj[3]!=null){
								//complaintsTo.setStartDate(CommonUtil.formatSqlDate1(String.valueOf(obj[3])));
								complaintsTo.setComplaintTypeName(String.valueOf(obj[3]));
							}
							if(obj[4]!=null){
								complaintsTo.setTitle(String.valueOf(obj[4]));
							}
							if(obj[5]!=null){
								complaintsTo.setDesc(obj[5].toString());
							}
							if(obj[6]!=null){
								complaintsTo.setActionTaken(obj[6].toString());
							}
							if(obj[7]!=null){
								complaintsTo.setStatusId(obj[7].toString());
							}
							if(obj[8]!=null){
								complaintsTo.setApprovedBy(obj[8].toString());
							}
							if(obj[9]!=null){
								complaintsTo.setApprovedDate(obj[9].toString());
							}
							if(obj[10]!=null)
							{
								complaintsTo.setStatusName(obj[10].toString());
							}
							
						}
				}
				
		 return complaintsTo;
	 }
	 
	 
	 
	// public static String get

	/*public static ComplaintsTO getComplaintTypeTo(
			List<Object[]> hlComplaintList,
			HostelAdminMessageForm hostelAdminMessageForm,
			Map<Integer, String> studentIdMap) {
		// TODO Auto-generated method stub
		return null;
	}*/
	
}
