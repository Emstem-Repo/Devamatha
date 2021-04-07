package com.kp.cms.utilities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.BulkSMStoEmployeeMobileMessaging;
import com.kp.cms.bo.admin.BulkSMStoStudentMobileMessaging;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.MobileMessagingSchedule;
import com.kp.cms.bo.admin.SendBulkSmsToStudentParentsBo;
import com.kp.cms.bo.admin.StudentBulkAttendanceSMS;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.utilities.jms.SMS_Message;
import com.kp.cms.bo.admin.PasswordMobileMessaging;

public class ConverationUtil {
	public static final String DATE_FORMAT_NOW = "dd-MM-yyyy HH:mm:ss";
	private static final Log log = LogFactory.getLog(StudentBulkAttendanceSMS.class);
	/**
	 * @param mobileList
	 * @return
	 */
	public List<SMS_Message> convertBotoTO(List<MobileMessaging> mobileList){
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<MobileMessaging> itr=mobileList.iterator();
			while (itr.hasNext()) {
				MobileMessaging mobileMessaging = (MobileMessaging) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				
				//raghu
				sms.setStudentId(mobileMessaging.getStudentId());
				sms.setAttendanceDate(mobileMessaging.getAttendanceDate());
				
				smsList.add(sms);
			}
		}
		return smsList;
	}
	/**
	 * @param mobileList
	 * @return
	 */
	public List<MobileMessaging> convertTotoBO(List<SMS_Message> mobileList){
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SMS_Message> itr=mobileList.iterator();
			//int i=1;
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				MobileMessaging mobileMessaging=new MobileMessaging();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				
				mobileMessaging.setStudentId(sms.getStudentId());
				mobileMessaging.setAttendanceDate(sms.getAttendanceDate());
				mobileMessaging.setLastModifiedDate(new Date());
				System.out.println(sms.getAttendanceDate()+"++++++++++++++++++ error string in MobileMessaging STUDENT ID to++++++++++++++++++: "+mobileMessaging.getStudentId());
				
				//if(i==1)
				smsList.add(mobileMessaging);
				//i++;
			}
		}
		return smsList;
	}
	public List<SMS_Message> convertSheduleBOtoTO(
			List<MobileMessagingSchedule> messageList)throws Exception {
		// TODO Auto-generated method stub
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		try{
		if(messageList!=null && !messageList.isEmpty()){
			Iterator<MobileMessagingSchedule> itr = messageList.iterator();
			while (itr.hasNext()) {
				MobileMessagingSchedule mobileMessagingSchedule = (MobileMessagingSchedule) itr.next();
				SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
				String date1 = sdf1.format(mobileMessagingSchedule.getSendingDate());
				Calendar cal = Calendar.getInstance();
				Formatter fmt = new Formatter();
				SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_FORMAT_NOW);
				String fullDate = sdf2.format(cal.getTime());
				String date2 = fullDate.substring(0,10);
				String time1 = fmt.format("%tr", cal).toString().substring(0, 5);
//				String time1 = fullDate.substring(11,16);
				if(date1.equalsIgnoreCase(date2)){
					String time2 = mobileMessagingSchedule.getSendTime().substring(0, 5);
					if(time1.equalsIgnoreCase(time2)){
						SMS_Message sms=new SMS_Message();
						sms.setDestination_number(mobileMessagingSchedule.getDestinationNumber());
						sms.setMessageEnqueueDate(mobileMessagingSchedule.getMessageEnqueueDate());
						sms.setMessage_body(mobileMessagingSchedule.getMessageBody());
						sms.setMessage_id(mobileMessagingSchedule.getId());
						if(mobileMessagingSchedule.getMessagePriority()!=null)
						sms.setMessage_priority(Integer.toString(mobileMessagingSchedule.getMessagePriority()));
						if(mobileMessagingSchedule.getMessageStatus()!=null)
						sms.setMessage_status(Integer.parseInt(mobileMessagingSchedule.getMessageStatus()));
						sms.setSender_name(mobileMessagingSchedule.getSenderName());
						sms.setSender_number(mobileMessagingSchedule.getSenderNumber());
						sms.setSms_gateway_response(mobileMessagingSchedule.getGatewayError());
						sms.setSendingDate(mobileMessagingSchedule.getSendingDate());
						sms.setSendTime(mobileMessagingSchedule.getSendTime());
						smsList.add(sms);

					}
				}
			}
		}
		return smsList;
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public List<MobileMessagingSchedule> convertTotoBOForSchedule(List<SMS_Message> mobileList){
		List<MobileMessagingSchedule> smsList=new ArrayList<MobileMessagingSchedule>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SMS_Message> itr=mobileList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				MobileMessagingSchedule mobileMessagingSchedule=new MobileMessagingSchedule();
				mobileMessagingSchedule.setDestinationNumber(sms.getDestination_number());
				mobileMessagingSchedule.setMessageBody(sms.getMessage_body());
				mobileMessagingSchedule.setId(sms.getMessage_id());
				mobileMessagingSchedule.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessagingSchedule.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessagingSchedule.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessagingSchedule.setSenderName(sms.getSender_name());
				mobileMessagingSchedule.setSenderNumber(sms.getSender_number());
				mobileMessagingSchedule.setGatewayError(sms.getSms_gateway_response());
				mobileMessagingSchedule.setSendingDate(sms.getSendingDate());
				mobileMessagingSchedule.setSendTime(sms.getSendTime());
				smsList.add(mobileMessagingSchedule);
			}
		}
		return smsList;
}
	
	
	public List<SMS_Message> convertBotoTOBulSMS(List<BulkSMStoStudentMobileMessaging> listOfBulkSMS)throws Exception {
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(listOfBulkSMS!=null && !listOfBulkSMS.isEmpty()){
			Iterator<BulkSMStoStudentMobileMessaging> itr=listOfBulkSMS.iterator();
			while (itr.hasNext()) {
				BulkSMStoStudentMobileMessaging mobileMessaging = (BulkSMStoStudentMobileMessaging) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				sms.setStudentId(mobileMessaging.getStudentId());
				sms.setClassId(mobileMessaging.getClassId());
				sms.setAttendanceDate(mobileMessaging.getSmsDate());
				smsList.add(sms);
			}
		}
		return smsList;
	}
	
	
	public List<BulkSMStoStudentMobileMessaging> convertTotoBOBulkSMS(List<SMS_Message> mobList) throws Exception{
		List<BulkSMStoStudentMobileMessaging> smsList=new ArrayList<BulkSMStoStudentMobileMessaging>();
		if(mobList!=null && !mobList.isEmpty()){
			Iterator<SMS_Message> itr=mobList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				BulkSMStoStudentMobileMessaging mobileMessaging=new BulkSMStoStudentMobileMessaging();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				mobileMessaging.setStudentId(sms.getStudentId());
			    mobileMessaging.setClassId(sms.getClassId());
			    mobileMessaging.setSmsDate(sms.getAttendanceDate());
				smsList.add(mobileMessaging);
			}
		}
		return smsList;
	}
	
	public List<SMS_Message> convertBotoTOBulSMSEmployee(List<BulkSMStoEmployeeMobileMessaging> listOfBulkSMS) {
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(listOfBulkSMS!=null && !listOfBulkSMS.isEmpty()){
			Iterator<BulkSMStoEmployeeMobileMessaging> itr=listOfBulkSMS.iterator();
			while (itr.hasNext()) {
				BulkSMStoEmployeeMobileMessaging mobileMessaging = (BulkSMStoEmployeeMobileMessaging) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				sms.setStudentId(mobileMessaging.getUserId());
				sms.setClassId(mobileMessaging.getRollid());
				sms.setAttendanceDate(mobileMessaging.getSmsDate());
				smsList.add(sms);
			}
		}
		return smsList;
	}
	public List<BulkSMStoEmployeeMobileMessaging> convertTotoBOBulkSMSEmployee(
			List<SMS_Message> mobList) {
		List<BulkSMStoEmployeeMobileMessaging> smsList=new ArrayList<BulkSMStoEmployeeMobileMessaging>();
		if(mobList!=null && !mobList.isEmpty()){
			Iterator<SMS_Message> itr=mobList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				BulkSMStoEmployeeMobileMessaging mobileMessaging=new BulkSMStoEmployeeMobileMessaging();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				mobileMessaging.setUserId(sms.getStudentId());
			    mobileMessaging.setRollid(sms.getClassId());
			    mobileMessaging.setSmsDate(sms.getAttendanceDate());
				smsList.add(mobileMessaging);
			}
		}
		return smsList;
	}
	
	/**
	 * @param mobileList
	 * @return
	 */
	public List<SMS_Message> convertBotoTOStudentBulkAttendanceSMS(List<StudentBulkAttendanceSMS> mobileList){
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<StudentBulkAttendanceSMS> itr=mobileList.iterator();
			while (itr.hasNext()) {
				StudentBulkAttendanceSMS mobileMessaging = (StudentBulkAttendanceSMS) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				sms.setStudentId(mobileMessaging.getStudentId());
				sms.setClassId(mobileMessaging.getClassId());
				smsList.add(sms);
				log.debug(" ******************* update bo to to object in log debug ***********************");
				System.out.println("++++++++++++++++++ update bo to to object in log debug ++++++++++++++++++: ");
			}
		}
		return smsList;
	}
	
	/**
	 * @param mobileList
	 * @return
	 */
	public List<StudentBulkAttendanceSMS> convertTotoBOStudentBulkAttendanceSMS(List<SMS_Message> mobileList){
		List<StudentBulkAttendanceSMS> smsList=new ArrayList<StudentBulkAttendanceSMS>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SMS_Message> itr=mobileList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				StudentBulkAttendanceSMS mobileMessaging=new StudentBulkAttendanceSMS();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				mobileMessaging.setStudentId(sms.getStudentId());
				mobileMessaging.setClassId(sms.getClassId());
				mobileMessaging.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				smsList.add(mobileMessaging);
				System.out.println(mobileMessaging.getClassId()+"++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo STUDENT ID to++++++++++++++++++: "+mobileMessaging.getStudentId());
				
			}
		}
		return smsList;
	}
	
	
	
	
	


	
	public List<SMS_Message> convertBotoTOPassword(List<PasswordMobileMessaging> mobileList){
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<PasswordMobileMessaging> itr=mobileList.iterator();
			while (itr.hasNext()) {
				PasswordMobileMessaging mobileMessaging = (PasswordMobileMessaging) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				if(mobileMessaging.getGatewayError()!=null)
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				if(mobileMessaging.getGatewayGuid()!=null)
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				smsList.add(sms);
			}
		}
		return smsList;
	}
	
	/**
	 * @param mobileList
	 * @return
	 */
	public List<PasswordMobileMessaging> convertTotoBOPassword(List<SMS_Message> mobileList){
		List<PasswordMobileMessaging> smsList=new ArrayList<PasswordMobileMessaging>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SMS_Message> itr=mobileList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				PasswordMobileMessaging mobileMessaging=new PasswordMobileMessaging();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				//if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				smsList.add(mobileMessaging);
			}
		}
		return smsList;
	}
	
	public List<SMS_Message> converttoTo(List<SendBulkSmsToStudentParentsBo> mobileList){
		List<SMS_Message> smsList=new ArrayList<SMS_Message>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SendBulkSmsToStudentParentsBo> itr=mobileList.iterator();
			while (itr.hasNext()) {
				SendBulkSmsToStudentParentsBo mobileMessaging = (SendBulkSmsToStudentParentsBo) itr.next();
				SMS_Message sms=new SMS_Message();
				sms.setDestination_number(mobileMessaging.getDestinationNumber());
				sms.setMessageEnqueueDate(mobileMessaging.getMessageEnqueueDate());
				sms.setMessage_body(mobileMessaging.getMessageBody());
				sms.setMessage_id(mobileMessaging.getId());
				if(mobileMessaging.getMessagePriority()!=null)
				sms.setMessage_priority(Integer.toString(mobileMessaging.getMessagePriority()));
				if(mobileMessaging.getMessageStatus()!=null)
				sms.setMessage_status(Integer.parseInt(mobileMessaging.getMessageStatus()));
				sms.setSender_name(mobileMessaging.getSenderName());
				sms.setSender_number(mobileMessaging.getSenderNumber());
				sms.setSms_gateway_response(mobileMessaging.getGatewayError());
				sms.setSms_guid(mobileMessaging.getGatewayGuid());
				sms.setStudent(mobileMessaging.getIsStudent());
				sms.setStudentId(mobileMessaging.getStudentId());
				sms.setClassId(mobileMessaging.getClassId());
				sms.setSendingDate(mobileMessaging.getCreatedDate());
				
				//System.out.println("++++++++++++++++++ messgae status in SendBulkSmsToStudentParentsBo to++++++++++++++++++: "+mobileMessaging.getMessageStatus());
				//System.out.println("++++++++++++++++++SMS_Message messgae status in  bo++++++++++++++++++: "+sms.getMessage_status());
				//System.out.println("++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo to++++++++++++++++++: "+mobileMessaging.getGatewayError());
				//System.out.println("++++++++++++++++++SMS_Message error string in  bo++++++++++++++++++: "+sms.getSms_gateway_response());
				
				smsList.add(sms);
				log.debug(" ******************* update bo to to object in log debug ***********************");
				System.out.println("++++++++++++++++++ update bo to to object in log debug ++++++++++++++++++: ");
				
			}
		}
		return smsList;
	}
	
	public List<SendBulkSmsToStudentParentsBo> converttoBO(List<SMS_Message> mobileList){
		List<SendBulkSmsToStudentParentsBo> smsList=new ArrayList<SendBulkSmsToStudentParentsBo>();
		if(mobileList!=null && !mobileList.isEmpty()){
			Iterator<SMS_Message> itr=mobileList.iterator();
			while (itr.hasNext()) {
				SMS_Message sms = (SMS_Message) itr.next();
				SendBulkSmsToStudentParentsBo mobileMessaging=new SendBulkSmsToStudentParentsBo();
				mobileMessaging.setDestinationNumber(sms.getDestination_number());
				mobileMessaging.setMessageBody(sms.getMessage_body());
				mobileMessaging.setId(sms.getMessage_id());
				
				//System.out.println("====================sms.getMessage_id()"+sms.getMessage_id());
				//System.out.println("====================sms.getMessage_status()"+sms.getMessage_status());
				//System.out.println("====================sms.getSms_gateway_response()"+sms.getSms_gateway_response());
				//System.out.println("====================sms.getStudentId()"+sms.getStudentId());
				
				
				mobileMessaging.setMessageEnqueueDate(sms.getMessageEnqueueDate());
				if(sms.getMessage_priority()!=null)
					mobileMessaging.setMessagePriority(Integer.parseInt(sms.getMessage_priority()));
				if(sms.getMessage_status()>0)
					mobileMessaging.setMessageStatus(Integer.toString(sms.getMessage_status()));
				mobileMessaging.setSenderName(sms.getSender_name());
				mobileMessaging.setSenderNumber(sms.getSender_number());
				mobileMessaging.setGatewayError(sms.getSms_gateway_response());
				mobileMessaging.setGatewayGuid(sms.getSms_guid());
				mobileMessaging.setStudentId(sms.getStudentId());
				mobileMessaging.setIsStudent(sms.isStudent());
				mobileMessaging.setClassId(sms.getClassId());
				
				//System.out.println("++++++++++++++++++ messgae status in SendBulkSmsToStudentParentsBo to++++++++++++++++++: "+mobileMessaging.getMessageStatus());
				//System.out.println("++++++++++++++++++SMS_Message messgae status in  bo++++++++++++++++++: "+sms.getMessage_status());
				//System.out.println("++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo to++++++++++++++++++: "+mobileMessaging.getGatewayError());
				//System.out.println("++++++++++++++++++SMS_Message error string in  bo++++++++++++++++++: "+sms.getSms_gateway_response());
				//System.out.println("++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo  PRIMARY KEY ID to++++++++++++++++++: "+mobileMessaging.getId());
				//System.out.println("++++++++++++++++++SMS_Message PRIMARY KEY ID string in  bo++++++++++++++++++: "+sms.getMessage_id());
				System.out.println("++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo STUDENT ID to++++++++++++++++++: "+mobileMessaging.getStudentId());
				//System.out.println("++++++++++++++++++SMS_Message error string in  STUDET ID bo++++++++++++++++++: "+sms.getStudentId());
				//System.out.println("++++++++++++++++++ error string in SendBulkSmsToStudentParentsBo STUDENT MOBILE to++++++++++++++++++: "+mobileMessaging.getDestinationNumber());
				
				smsList.add(mobileMessaging);
			}
		}
		return smsList;
	}
}
