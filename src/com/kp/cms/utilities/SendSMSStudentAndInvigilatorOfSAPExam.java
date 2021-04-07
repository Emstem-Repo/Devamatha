package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.ExamScheduleUsers;
import com.kp.cms.bo.sap.ExamScheduleVenue;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.to.sap.ExamRegistrationDetailsTo;

public class SendSMSStudentAndInvigilatorOfSAPExam implements Job{
	private static final Log log = LogFactory.getLog(SendSMSStudentAndInvigilatorOfSAPExam.class);	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		String todayDate = CommonUtil.getTodayDate();
		String tomorrowDate = CommonUtil.getNextDate(todayDate);
		java.util.Date nextDay = CommonUtil.ConvertStringToSQLDate(tomorrowDate);
		try {
			List<ExamRegistrationDetailsTo> registrationDetailsTos =null;
			Map<Integer,Map<Integer,ExamRegistrationDetailsTo>> invigilatorMap =null;
			String HQL_QUERY = getSAPExamRegistraionDetailsForTomorrowDate(nextDay);
			List<ExamRegistrationDetails> sapExamRegistraionList = PropertyUtil.getInstance().getListOfData(HQL_QUERY);
			if(sapExamRegistraionList!=null && !sapExamRegistraionList.isEmpty()){
				 registrationDetailsTos = new ArrayList<ExamRegistrationDetailsTo>();
				 invigilatorMap = new HashMap<Integer, Map<Integer,ExamRegistrationDetailsTo>>();
				 setStudentAndInvigilatorDetailsTOSendSMSSAPExam(sapExamRegistraionList,registrationDetailsTos,invigilatorMap);
			
					Properties prop = new Properties();
					InputStream in = SmsMailForAmJob.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(in);
					InputStream in1 = ExamValuatorPreInfo.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
					prop.load(in1);
					String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
					String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
					String sendSms= prop.getProperty("knowledgepro.sms.send");
					List<SMSTemplate> smsListForStudents= SMSTemplateHandler.getInstance().getDuplicateCheckList("SAP Exam - Reminder for student");
					List<SMSTemplate> smsListForInvigilators= SMSTemplateHandler.getInstance().getDuplicateCheckList("SAP Exam - Reminder for Invigilators");
					/* Iterate the TO and Send SMS to  Students who are applied for tomorrow's exam*/
					if(registrationDetailsTos!=null && !registrationDetailsTos.isEmpty()){
						if(smsListForStudents!=null && !smsListForStudents.isEmpty() && sendSms!=null && sendSms.equalsIgnoreCase("true")){
							Iterator<ExamRegistrationDetailsTo> iterator = registrationDetailsTos.iterator();
							List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
							while (iterator.hasNext()) {
								String desc ="";
								String mobileNo = "";
								ExamRegistrationDetailsTo to = (ExamRegistrationDetailsTo) iterator .next();
								if(to.getStudentMobileNo()!=null && !to.getStudentMobileNo().isEmpty() && to.getStudentMobileNo().length()==10){
								mobileNo="91"+to.getStudentMobileNo();
		//							mobileNo="919611928560";
									if(smsListForStudents.get(0)!=null && smsListForStudents.get(0).getTemplateDescription()!=null){
										desc = smsListForStudents.get(0).getTemplateDescription();
									  }
									desc = desc.replace("[EXAM_DATE]", to.getExamDate());
									desc = desc.replace("[SESSION]", to.getSessionName());
									desc = desc.replace("[VENUE]", to.getVenueName());
									desc = desc.replace("[REGISTER_NO]", to.getRegNO());
									MobileMessaging mobileMessagingBO = new MobileMessaging();
									mobileMessagingBO.setMessageBody(desc);
									mobileMessagingBO.setSenderName(senderName);
									mobileMessagingBO.setSenderNumber(senderNumber);
									mobileMessagingBO.setDestinationNumber(mobileNo);
									mobileMessagingBO.setMessagePriority(3);
									mobileMessagingBO.setIsMessageSent(false);
									mobileMessagingBO.setMessageEnqueueDate(new Date());
									smsListBos.add(mobileMessagingBO);
								}
							}
							PropertyUtil.getInstance().saveSMSList(smsListBos);
						}
					}
					/*--------------------------------------------------------------------------------*/
					
					/* Iterate Map and send sms to Invigilator, who are Invigilating the Tomorrow's SAP Exam */
					if(invigilatorMap!=null && !invigilatorMap.isEmpty()){
						if(smsListForInvigilators!=null && !smsListForInvigilators.isEmpty() && sendSms!=null && sendSms.equalsIgnoreCase("true")){
							Iterator<Entry<Integer, Map<Integer,ExamRegistrationDetailsTo>>> iterator = invigilatorMap.entrySet().iterator();
							while (iterator.hasNext()) {
								Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.sap.ExamRegistrationDetailsTo>> entry = (Map.Entry<java.lang.Integer, java.util.Map<java.lang.Integer, com.kp.cms.to.sap.ExamRegistrationDetailsTo>>) iterator
										.next();
								 Map<Integer,ExamRegistrationDetailsTo> subMap = entry.getValue();
								 if(subMap!=null && !subMap.isEmpty()){
									 List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
									 Iterator<Map.Entry<Integer, ExamRegistrationDetailsTo>> iterator2 = subMap.entrySet().iterator();
									 while (iterator2.hasNext()) {
									    String desc ="";
										String mobileNo = "";
										Map.Entry<java.lang.Integer, com.kp.cms.to.sap.ExamRegistrationDetailsTo> entry2 = (Map.Entry<java.lang.Integer, com.kp.cms.to.sap.ExamRegistrationDetailsTo>) iterator2
												.next();
										ExamRegistrationDetailsTo to = entry2.getValue();
										if(to.getInvigilatorMobileNo()!=null && !to.getInvigilatorMobileNo().isEmpty() && to.getInvigilatorMobileNo().length()==10){
											mobileNo = "91"+to.getInvigilatorMobileNo();
											if(smsListForInvigilators.get(0)!=null && smsListForInvigilators.get(0).getTemplateDescription()!=null){
												desc = smsListForInvigilators.get(0).getTemplateDescription();
											  }
											desc = desc.replace("[EXAM_DATE]", to.getExamDate());
											desc = desc.replace("[SESSION]", to.getSessionName());
											desc = desc.replace("[VENUE]", to.getVenueName());
											desc = desc.replace("[STAFF_NAME]", to.getInvigilatorName());
											MobileMessaging mobileMessagingBO = new MobileMessaging();
											mobileMessagingBO.setMessageBody(desc);
											mobileMessagingBO.setSenderName(senderName);
											mobileMessagingBO.setSenderNumber(senderNumber);
											mobileMessagingBO.setDestinationNumber(mobileNo);
											mobileMessagingBO.setMessagePriority(3);
											mobileMessagingBO.setIsMessageSent(false);
											mobileMessagingBO.setMessageEnqueueDate(new Date());
											smsListBos.add(mobileMessagingBO);
										}
									}
									 PropertyUtil.getInstance().saveSMSList(smsListBos);
								 }
							}
						}
					}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @param sapExamRegistraionList
	 * @param registrationDetailsTos
	 * @param invigilatorMap
	 * @throws Exception
	 */
	private void setStudentAndInvigilatorDetailsTOSendSMSSAPExam( List<ExamRegistrationDetails> sapExamRegistraionList,
			List<ExamRegistrationDetailsTo> registrationDetailsTos, Map<Integer, Map<Integer, ExamRegistrationDetailsTo>> invigilatorMap)throws Exception {
		Iterator<ExamRegistrationDetails> iterator = sapExamRegistraionList.iterator();
		while (iterator.hasNext()) {
			ExamRegistrationDetails examRegistrationDetails = (ExamRegistrationDetails) iterator .next();
			ExamRegistrationDetailsTo examRegTO  = new ExamRegistrationDetailsTo();
			String studentMobileNo="";
			Set<ExamScheduleVenue> examScheduleVenuesSet =null;
			
			if(examRegistrationDetails.getExamScheduleDateId()!=null && !examRegistrationDetails.getExamScheduleDateId().toString().isEmpty()){
				examRegTO.setSessionName(examRegistrationDetails.getExamScheduleDateId().getSession());
				examRegTO.setExamDate(CommonUtil.formatDates(examRegistrationDetails.getExamScheduleDateId().getExamDate()));
				if(examRegistrationDetails.getExamScheduleDateId().getExamScheduleVenue()!=null){
					 examScheduleVenuesSet = examRegistrationDetails.getExamScheduleDateId().getExamScheduleVenue();
				}
			}
			if(examRegistrationDetails.getSapVenueId()!=null && examRegistrationDetails.getSapVenueId().getVenueName()!=null){
				examRegTO.setVenueId(examRegistrationDetails.getSapVenueId().getId());
				examRegTO.setVenueName(examRegistrationDetails.getSapVenueId().getVenueName());
			}
			if(examRegistrationDetails.getStudentId()!=null && examRegistrationDetails.getStudentId().getRegisterNo()!=null){
				examRegTO.setRegNO(examRegistrationDetails.getStudentId().getRegisterNo());
			}
			
			/*if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1()!=null 
					&& !examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
				if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || 
						examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
						|| examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || 
						examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
					studentMobileNo = "91";
				else
					studentMobileNo=examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo1();
			}else{
				studentMobileNo="91";
			}*/
			if(examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo2()!=null && !examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
				studentMobileNo=studentMobileNo+examRegistrationDetails.getStudentId().getAdmAppln().getPersonalData().getMobileNo2();
			}
			examRegTO.setStudentMobileNo(studentMobileNo);
			
			/* set Invigilator Details for Venue */
			if(examScheduleVenuesSet!=null && !examScheduleVenuesSet.isEmpty()){
				Iterator<ExamScheduleVenue> iterator2 = examScheduleVenuesSet.iterator();
				while (iterator2.hasNext()) {
					ExamScheduleVenue examScheduleVenue = (ExamScheduleVenue) iterator2 .next();
					if(examScheduleVenue.getVenue()!=null){
						if(examScheduleVenue.getVenue().getId() == examRegTO.getVenueId()){
							if(examScheduleVenue.getExamScheduleUsers()!=null && !examScheduleVenue.getExamScheduleUsers().isEmpty()){
								Set<ExamScheduleUsers> examScheduleUsersSet = examScheduleVenue.getExamScheduleUsers();
								Iterator<ExamScheduleUsers> iterator3 = examScheduleUsersSet.iterator();
								Map<Integer,ExamRegistrationDetailsTo> subMap = new HashMap<Integer, ExamRegistrationDetailsTo>();
								while (iterator3.hasNext()) {
									ExamScheduleUsers examScheduleUsers = (ExamScheduleUsers) iterator3 .next();
									ExamRegistrationDetailsTo examRegTO1  = new ExamRegistrationDetailsTo();
									if(examScheduleUsers.getUsers()!=null){
										if(examScheduleUsers.getUsers().getEmployee()!=null ){
											if(examScheduleUsers.getUsers().getEmployee().getFirstName()!=null && !examScheduleUsers.getUsers().getEmployee().getFirstName().isEmpty()){
												examRegTO1.setInvigilatorName(examScheduleUsers.getUsers().getEmployee().getFirstName());
											} else if(examScheduleUsers.getUsers().getUserName()!=null && !examScheduleUsers.getUsers().getUserName().isEmpty()){
												examRegTO1.setInvigilatorName(examScheduleUsers.getUsers().getUserName().toUpperCase());
											}
											if(examScheduleUsers.getUsers().getEmployee().getCurrentAddressMobile1()!=null 
													&& !examScheduleUsers.getUsers().getEmployee().getCurrentAddressMobile1().isEmpty()){
												examRegTO1.setInvigilatorMobileNo(examScheduleUsers.getUsers().getEmployee().getCurrentAddressMobile1());
											}
											}else{
												if(examScheduleUsers.getUsers().getGuest()!=null){
													if(examScheduleUsers.getUsers().getGuest()!=null && !examScheduleUsers.getUsers().getGuest().toString().isEmpty()){
														if(examScheduleUsers.getUsers().getGuest().getFirstName()!=null && !examScheduleUsers.getUsers().getGuest().getFirstName().isEmpty()){
															examRegTO1.setInvigilatorName(examScheduleUsers.getUsers().getGuest().getFirstName());
														}
														if(examScheduleUsers.getUsers().getGuest().getCurrentAddressMobile1()!=null && !examScheduleUsers.getUsers().getGuest().getCurrentAddressMobile1().isEmpty()){
															examRegTO1.setInvigilatorMobileNo(examScheduleUsers.getUsers().getGuest().getCurrentAddressMobile1());
														}
													}
												}
											}
										if(examScheduleVenue.getVenue()!=null && !examScheduleVenue.getVenue().toString().isEmpty()){
											examRegTO1.setVenueName(examScheduleVenue.getVenue().getVenueName());
											examRegTO1.setVenueId(examScheduleVenue.getVenue().getId());
										}
										if(examScheduleVenue.getExamScheduleDate()!=null && examScheduleVenue.getExamScheduleDate().getSession()!=null){
											examRegTO1.setSessionName(examScheduleVenue.getExamScheduleDate().getSession());
											examRegTO1.setExamDate(CommonUtil.formatDates(examScheduleVenue.getExamScheduleDate().getExamDate()));
										}
										subMap.put(examScheduleUsers.getUsers().getId(),examRegTO1);
										if(invigilatorMap.containsKey(examRegTO.getVenueId())){
											Map<Integer,ExamRegistrationDetailsTo> map = invigilatorMap.get(examRegTO.getVenueId());
											if(!map.containsKey(examScheduleUsers.getUsers().getId())){
												invigilatorMap.put(examRegTO.getVenueId(), subMap);
											}
										}else{
											invigilatorMap.put(examRegTO.getVenueId(), subMap);
										}
									}
								}
							}
							
						}
					}
				}
			}
			//
			registrationDetailsTos.add(examRegTO);
		}
	}

	/**
	 * @param nextDay
	 * @return
	 * @throws Exception
	 */
	private String getSAPExamRegistraionDetailsForTomorrowDate(Date nextDay) throws Exception {
		String hqlQuery = "from ExamRegistrationDetails examRegDetails where examRegDetails.isActive=1 "
						+ "	and examRegDetails.isCancelled=0 and examRegDetails.isPaymentFailed=0"
						+ " and examRegDetails.examScheduleDateId.examDate='"+nextDay+"'";
		return hqlQuery;
	}

}
