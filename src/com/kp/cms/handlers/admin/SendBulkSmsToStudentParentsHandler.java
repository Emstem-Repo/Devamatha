package com.kp.cms.handlers.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.SendBulkSmsToStudentParentsBo;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.SendBulkSmsToStudentParentsForm;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.INewAttendanceSmsTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.NewAttendanceSmsTransImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class SendBulkSmsToStudentParentsHandler {
	private static final Log log = LogFactory.getLog(SendBulkSmsToStudentParentsHandler.class);
	private static volatile SendBulkSmsToStudentParentsHandler sendBulkSmsToStudentParentsHandler = null;
    private SendBulkSmsToStudentParentsHandler(){
    	
    }
	public static SendBulkSmsToStudentParentsHandler getInstance() {
		if (sendBulkSmsToStudentParentsHandler == null) {
			sendBulkSmsToStudentParentsHandler = new SendBulkSmsToStudentParentsHandler();
		}
		return sendBulkSmsToStudentParentsHandler;
	}
	
	
	public boolean sendSMS(SendBulkSmsToStudentParentsForm sbForm,
			List<Student> studentList) throws Exception{
		// TODO Auto-generated method stub

		log.info(" ********************* Student list size in log file  ******************** :"+studentList.size());
		log.debug(" ******************* Student start in log file ***********************");
		System.out.println("++++++++++++++++++ Student list size in sysout ++++++++++++++++++: "+studentList.size());
		boolean sent=false;
		boolean isAddedOfficeNumber=false;
		//check student size
		if(studentList.size()!=0){
			 
			Properties prop = new Properties();
			try {
				InputStream in = AttendanceEntryHelper.class.getClassLoader()
				.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
				prop.load(in);
			} catch (FileNotFoundException e) {	
			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
		
		Iterator<Student> itr=studentList.iterator();
		Student student=new Student();
		List<SendBulkSmsToStudentParentsBo> list=new ArrayList<SendBulkSmsToStudentParentsBo>();

		while(itr.hasNext())
		{
			student=new Student();
			student=itr.next();
			//testing students
			//if(student.getId()==18537){
				
			//System.out.println("---------------------------s id"+student.getId());
			if(student.getClassSchemewise()!=null){
				
			SendBulkSmsToStudentParentsBo bo=new SendBulkSmsToStudentParentsBo();
				
			 bo.setClassId(student.getClassSchemewise().getClasses().getId());
	
				String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
				String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
				String footer=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_FOOTER);
				
				String temp = "";
				temp=temp+URLEncoder.encode(sbForm.getMessage()+ " ","UTF-8");
				if(footer!=null){
				   temp=temp+URLEncoder.encode(" +'\n'+ Principal "+footer+"", "UTF-8");
				}else{
					temp=temp+URLEncoder.encode(" +\n+ Principal SH College", "UTF-8");
					
				}
					bo.setMessagePriority(3);
				    bo.setSenderName(senderName);
				    bo.setSenderNumber(senderNumber);
				    bo.setMessageEnqueueDate(new Date());
				    bo.setStudentId(student.getId());
				    bo.setIsMessageSent(false);
				    bo.setMessageBody(temp);
				   if(sbForm.getIsStudent()){
				    bo.setIsStudent(true);
				    String countrycode="91";
				    if(student.getAdmAppln().getPersonalData().getMobileNo1()!=null && !student.getAdmAppln().getPersonalData().getMobileNo1().isEmpty())
				    	countrycode=student.getAdmAppln().getPersonalData().getMobileNo1();
				    if(student.getAdmAppln().getPersonalData().getMobileNo2()!=null && !student.getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
				    	bo.setDestinationNumber(countrycode+student.getAdmAppln().getPersonalData().getMobileNo2());
				    }
				    
				   
				   }
				   else
				   {
				    	bo.setIsStudent(false);	

					    String countrycode="91";
					    if(student.getAdmAppln().getPersonalData().getParentMob1()!=null && !student.getAdmAppln().getPersonalData().getParentMob1().isEmpty())
					    	countrycode=student.getAdmAppln().getPersonalData().getParentMob1();
					    if(student.getAdmAppln().getPersonalData().getParentMob2()!=null && !student.getAdmAppln().getPersonalData().getParentMob2().isEmpty()){
					    	bo.setDestinationNumber(countrycode+student.getAdmAppln().getPersonalData().getParentMob2());
					    }
					    
					    else if(student.getAdmAppln().getPersonalData().getGuardianMob2()!=null && !student.getAdmAppln().getPersonalData().getGuardianMob2().isEmpty()){
					    	if(student.getAdmAppln().getPersonalData().getGuardianMob1()!=null && !student.getAdmAppln().getPersonalData().getGuardianMob1().isEmpty()){
					    		countrycode=student.getAdmAppln().getPersonalData().getGuardianMob1();
					    	}
					    	if(student.getAdmAppln().getPersonalData().getGuardianMob2()!=null && !student.getAdmAppln().getPersonalData().getGuardianMob2().isEmpty()){
					    		bo.setDestinationNumber(countrycode+student.getAdmAppln().getPersonalData().getMobileNo2());
					    	}
					    }
					   
				   }
				   
				  bo.setCreatedDate(new Date());
				  
				   list.add(bo);
				   
			//}//class null check over
			
		}
				 
		//}//testing single student  
			
		// sending sms to office number
			if(!isAddedOfficeNumber){
				String temp ="";
				temp=temp+URLEncoder.encode(sbForm.getMessage()+ " ","UTF-8");
				String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
				String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
				String footer=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_FOOTER);
				temp=temp+URLEncoder.encode(" +\n+ Principal SH College", "UTF-8");
				SendBulkSmsToStudentParentsBo bo=new SendBulkSmsToStudentParentsBo();	
				bo.setDestinationNumber("91"+sbForm.getMobileNo());
				bo.setMessagePriority(3);
				bo.setSenderName(senderName);
				bo.setSenderNumber(senderNumber);
				bo.setMessageEnqueueDate(new Date());
				bo.setStudentId(student.getId());
				bo.setIsMessageSent(false);
				bo.setMessageBody(temp);
				 bo.setCreatedDate(new Date());
				 bo.setIsStudent(false);	
				list.add(bo);
				isAddedOfficeNumber=true;
			}
		}
	    PropertyUtil.getInstance().saveSMS(list);
	    
	    log.info(" ********************* adding Student list to SendBulkSmsToStudentParentsBo in log file after size ******************** :"+list.size());
		log.debug(" ******************* add list to SendBulkSmsToStudentParentsBo list***********************");
		System.out.println("++++++++++++++++++ adding Student list to SendBulkSmsToStudentParentsBo in sysout after size ++++++++++++++++++: "+list.size());
		
		SMSUtil s =new SMSUtil();
    	SMSUtils smsUtils=new SMSUtils();
        ConverationUtil converationUtil=new ConverationUtil();
    	List<SMS_Message> listSms=converationUtil.converttoTo(s.getSMSList());
    	
    	log.info(" ********************* after sending sms in log file after size ******************** :"+list.size());
		log.debug(" ******************* after sending sms ***********************");
		System.out.println("++++++++++++++++++ after sending sms in sysout after size ++++++++++++++++++: "+list.size());
		//raghu write new method for bulk sms storing
    	List<SMS_Message> mobList=smsUtils.sendSMSBulk(listSms);
    	sent=s.updateSMSLists(converationUtil.converttoBO(mobList));
    	//this is for checking dummy data
    	//sent=s.updateSMSList(converationUtil.converttoBO(listSms));
		
    	log.info(" ********************* final size ******************** :"+mobList.size());
		log.debug(" ******************* final ***********************");
		System.out.println("++++++++++++++++++ final size ++++++++++++++++++: "+mobList.size());
		
		}// student size check over
		
		
		log.info("Handler : Leaving getAttendanceObj");
		return sent;
	
	}

}
