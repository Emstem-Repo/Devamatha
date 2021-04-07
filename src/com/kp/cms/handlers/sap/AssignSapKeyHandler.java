package com.kp.cms.handlers.sap;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.sap.SapKeysBo;
import com.kp.cms.bo.sap.SapRegistration;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.sap.AssignSapKeyForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.helpers.sap.AssignSapKeyHelper;
import com.kp.cms.to.sap.SapKeysTo;
import com.kp.cms.transactions.sap.IAssignSapKeyTransaction;
import com.kp.cms.transactionsimpl.sap.AssignSapKeyTransImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class AssignSapKeyHandler {
	AssignSapKeyHelper helper=AssignSapKeyHelper.getInstance();
	IAssignSapKeyTransaction transaction=AssignSapKeyTransImpl.getInstance();
	public static volatile AssignSapKeyHandler assignSapKeyHandler = null;
	private static Log log = LogFactory.getLog(AssignSapKeyHandler.class);
	
	public static AssignSapKeyHandler getInstance() {
		if (assignSapKeyHandler == null) {
			assignSapKeyHandler = new AssignSapKeyHandler();
			return assignSapKeyHandler;
		}
		return assignSapKeyHandler;
	}
	
	public void getRegisterdStudentsForSap(AssignSapKeyForm assignSapKeyForm) throws Exception{
		List<SapRegistration> list=transaction.getRegisteredStudentsForSap(CommonUtil.ConvertStringToSQLDate(assignSapKeyForm.getStartDate()),
					CommonUtil.ConvertStringToSQLDate(assignSapKeyForm.getEndDate()),assignSapKeyForm.getStatus(),assignSapKeyForm.getClassId());
		List<SapKeysTo> sapKeysTos=helper.convertBoToTos(list);
		assignSapKeyForm.setSapKeysTos(sapKeysTos);
		}
	
	public boolean updateSapKeysInSapRegistration(
			AssignSapKeyForm assignSapKeyForm, List<SapKeysTo> list, HttpServletRequest request) throws Exception{
		boolean flag=false;
		Map<Integer,SapRegistration> map=transaction.getSapRegistration();
		List<Integer> updatedStudents=new ArrayList<Integer>();
		//start to update the SapRegistration keys
		if(list!=null && !list.isEmpty()){
			List<Integer> admApplList=transaction.getAdminApplIds();
			List<SapRegistration> sapRegistrations=new ArrayList<SapRegistration>();
			Map<Integer,List<Integer>> keysMap=transaction.getKeys();
			//if work location is kengari campus
			List<Integer> kengerikeys=keysMap.get(1);
			//if work location is main campus
			List<Integer> mainKeys=keysMap.get(2);
			
			if((kengerikeys!=null && !kengerikeys.isEmpty()) || (mainKeys!=null && !mainKeys.isEmpty())){
				SapRegistration sapRegistration=null;
				SapKeysBo sapKeysBo=null;
				Iterator<SapKeysTo> iterator= list.iterator();
				while (iterator.hasNext()) {
					SapKeysTo sapKeysTo = (SapKeysTo) iterator.next();
					sapRegistration=map.get(sapKeysTo.getId());
					//if keys are there update the sapRegistraion else breaks the while loop
						if(sapRegistration.getStdId().getAdmAppln()!=null){
							sapKeysBo=new SapKeysBo();
							if(admApplList.contains(sapRegistration.getStdId().getAdmAppln().getId())){
								if(kengerikeys.size()>0 && kengerikeys!=null && !kengerikeys.isEmpty()){
									sapKeysBo.setId(kengerikeys.get(0));
									sapRegistration.setSapKeyId(sapKeysBo);
									sapRegistration.setLastModifiedDate(new Date());
									sapRegistrations.add(sapRegistration);
									kengerikeys.remove(0);
								}
							}else{
								if(mainKeys.size()>0 && mainKeys!=null && !mainKeys.isEmpty()){
									sapKeysBo.setId(mainKeys.get(0));
									sapRegistration.setSapKeyId(sapKeysBo);
									sapRegistration.setLastModifiedDate(new Date());
									sapRegistrations.add(sapRegistration);
									mainKeys.remove(0);
								}
							}
						}
						updatedStudents.add(sapKeysTo.getId());
				}
				if(sapRegistrations!=null){
					flag=transaction.updateSapRegistration(sapRegistrations);
				}
			}else{
				request.setAttribute("keys","key");
				throw new Exception();
			}
			
		}
		//end of update Sapregistration keys
		//get the students whose sapregistration keys are updated and send mail and sms
		if(updatedStudents!=null && !updatedStudents.isEmpty()){
			List<SapRegistration> sapRegistrations=transaction.getStudentsWhoHadUpdatedSapKeys(updatedStudents);
			sendMailAndSms(sapRegistrations);
		}
		//end the students whose sapregistration keys are updated
		return flag;
		
	}
	
	private void sendMailAndSms(List<SapRegistration> sapRegistrations)throws Exception {
		Properties prop1 = new Properties();
        InputStream in1 = AbsentiesListHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop1.load(in1);
		String senderNumber=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
		String senderName=prop1.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
		Properties prop = new Properties();
		String desc="";
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
		}
			List<GroupTemplate> list=null;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(CMSConstants.SAP_KEY_MAIL_FOR_STUDENT);
			if(list != null && !list.isEmpty()) {
				desc = list.get(0).getTemplateDescription();
				String emailID="";
				String regNo="";
				String name="";
				String key="";
				String phoneNum="";
				String body="Dear Student, SAP installation procedure and serial key has been sent to your University Email -- Christ University";
				String des = "";
				Iterator<SapRegistration> iterator=sapRegistrations.iterator();
				while (iterator.hasNext()) {
					emailID = "";
					regNo="";
					name="";
					key="";
					phoneNum="";
					des=desc;
					SapRegistration sapRegistration = (SapRegistration) iterator.next();
					if(sapRegistration.getStdId()!=null){
						if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getUniversityEmail()!=null){
							emailID=sapRegistration.getStdId().getAdmAppln().getPersonalData().getUniversityEmail();
						}
						if(sapRegistration.getStdId().getRegisterNo()!=null){
							regNo=sapRegistration.getStdId().getRegisterNo();
						}
						if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getMobileNo2()!=null){
							phoneNum="91"+sapRegistration.getStdId().getAdmAppln().getPersonalData().getMobileNo2();
						}
						if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getFirstName()!=null){
							name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getFirstName();
						}
						if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getMiddleName()!=null){
							name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getMiddleName();
						}
						if(sapRegistration.getStdId().getAdmAppln().getPersonalData().getLastName()!=null){
							name=name+sapRegistration.getStdId().getAdmAppln().getPersonalData().getLastName();
						}
					}
					if(sapRegistration.getSapKeyId()!=null){
						key=sapRegistration.getSapKeyId().getKey();
					}
					des=des.replace(CMSConstants.TEMPLATE_SMS_REGISTERNO,regNo);
					des=des.replace(CMSConstants.TEMPLATE_STUDENT_NAME,name);
					des=des.replace(CMSConstants.TEMPLATE_SAP_KEY,key);
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=CMSConstants.MAIL_USERID;
					String subject="SAP installation procedure and serial key";
				sendMail(emailID, subject, des, fromName, fromAddress);
				sendSMS(phoneNum,body,senderNumber,senderName);
				}
			}
		}
	
	private boolean sendSMS(String phoneNum, String body,String senderNumber,String senderName)throws Exception {
		boolean sentSms=false;
		if(StringUtils.isNumeric(phoneNum) && (phoneNum.length()==12 && body.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(phoneNum);
			mob.setMessageBody(body);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			sentSms=PropertyUtil.getInstance().save(mob);
		}
		return sentSms;
	}
	
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
		boolean sent=false;
			String toAddress=mailID;
			// MAIL TO CONSTRUCTION
			String subject=sub;
			String msg=message;
			MailTO mailto=new MailTO();
			mailto.setFromAddress(fromAddress);
			mailto.setToAddress(toAddress);
			mailto.setSubject(subject);
			mailto.setMessage(msg);
			mailto.setFromName(fromName);
			sent=CommonUtil.sendMail(mailto);
		return sent;
	}

	public boolean updateKeys(AssignSapKeyForm assignSapKeyForm,
			HttpServletRequest request) throws Exception{
		boolean flag=false;
		//which are checked
		List<SapKeysTo> list=new ArrayList<SapKeysTo>();
		//disabling the tos which are checked
		List<SapKeysTo> sapKeysTos=assignSapKeyForm.getSapKeysTos();
		Iterator<SapKeysTo> iterator=sapKeysTos.iterator();
		while (iterator.hasNext()) {
			SapKeysTo sapKeysTo = (SapKeysTo) iterator.next();
			if(sapKeysTo.getChecked()!=null && !sapKeysTo.getChecked().isEmpty() && sapKeysTo.getChecked().equalsIgnoreCase("on")){
				list.add(sapKeysTo);
				sapKeysTo.setChecked(null);
			}
		}
		return flag=updateSapKeysInSapRegistration(assignSapKeyForm,list,request);
	}

	public void getclassMap(AssignSapKeyForm assignSapKeyForm) throws Exception{
		Map<Integer,String> map=transaction.getclassMap();
		assignSapKeyForm.setClassMap(map);
	}
}
