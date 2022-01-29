package com.kp.cms.handlers.admission;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicationStatus;
import com.kp.cms.bo.admin.ApplicationStatusUpdate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ApplicationStatusUpdateForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.helpers.admission.ApplicationStatusUpdateHelper;
import com.kp.cms.to.admission.ApplicationStatusUpdateTO;
import com.kp.cms.transactions.admission.IApplicationStatusUpdateTxn;
import com.kp.cms.transactionsimpl.admission.ApplicationStatusUpdateTxnImpl;
import com.kp.cms.utilities.PropertyUtil;

public class ApplicationStatusUpdateHandler {
	private static final Log log = LogFactory.getLog(ApplicationStatusUpdateHandler.class);
	public static volatile ApplicationStatusUpdateHandler statusUpdate=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static ApplicationStatusUpdateHandler getInstance(){
		if(statusUpdate==null){
			statusUpdate= new ApplicationStatusUpdateHandler();}
		return statusUpdate;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getApplicationStatus()throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		List<ApplicationStatus> applicationStatus=transaction.getApplicationStatus();
		Map<Integer,String> statusMap=ApplicationStatusUpdateHelper.getInstance().setToMap(applicationStatus);
		return statusMap;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<ApplicationStatusUpdateTO> getApplicationStatusList()throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		List<ApplicationStatusUpdate> statusUpdate=transaction.getApplicationStatusUpdate();
		List<ApplicationStatusUpdateTO> statusUpdateTO=ApplicationStatusUpdateHelper.getInstance().convertBosToTOs(statusUpdate);
		return statusUpdateTO;
	}
	/**
	 * @param applicationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		boolean duplicate=transaction.duplicateCheck(applicationStatusUpdateForm,transaction.getAdmApplnId(applicationStatusUpdateForm.getApplicationNo()));
		return duplicate;
	}
	/**
	 * @param applicationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	public boolean addApplicationStatusUpdate(ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception{
		boolean isAdded=false;
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		boolean isCardGenerated=transaction.checkIfCardGeneratedForStudent(applicationStatusUpdateForm.getApplicationNo());
		if(!isCardGenerated){
			ApplicationStatusUpdate statusUpdate=ApplicationStatusUpdateHelper.getInstance().convertFormTOBO(applicationStatusUpdateForm);
			 isAdded=transaction.addApplicationStatusUpdate(statusUpdate,applicationStatusUpdateForm);
		}
		else{
			throw new BusinessException("CardGenerated");
		}
		return isAdded;
	}
	/**
	 * @param applicationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	public List<ApplicationStatusUpdateTO> getApplicationStatusUpdate(ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		List<ApplicationStatusUpdate> statusUpdate=transaction.getApplicationStatusUpdateByAppNo(applicationStatusUpdateForm);
		List<ApplicationStatusUpdateTO> statusUpdateTo=ApplicationStatusUpdateHelper.getInstance().convertBosToTOs(statusUpdate);
		return statusUpdateTo;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getApplicationStatusMap()throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		return transaction.getApplicationStatusMap();
	}
	/**
	 * @param input
	 * @param applnStatusMap
	 * @param applicationStatusUpdateForm
	 * @return
	 * @throws Exception
	 */
	public boolean addUploadedData(InputStream input,Map<String,String> applnStatusMap,ApplicationStatusUpdateForm applicationStatusUpdateForm)throws Exception{
		IApplicationStatusUpdateTxn transaction=new ApplicationStatusUpdateTxnImpl();
		List<ApplicationStatusUpdate> statusUpdate=ApplicationStatusUpdateHelper.getInstance().parseOMRData(input, applnStatusMap,applicationStatusUpdateForm);
		boolean flag=transaction.saveApplicationStatusUpdate(statusUpdate,applicationStatusUpdateForm);
		return flag;
	}
	/**
	 * @param mobileNo
	 * @param String applnNo 
	 * @param applnNo 
	 * @param smsTemplateMissingDocument
	 * @throws Exception
	 */
	public void sendSMSToStudent(String mobileNo, String templateName,String applnNo) throws Exception{
		/*Properties prop = new Properties();
        InputStream in1 = ApplicationStatusUpdateHandler.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
        prop.load(in1);*/
		String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
		String senderName=CMSConstants.SMS_SENDER_NAME;
		String desc="";
		SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
		List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,templateName);
		if(list != null && !list.isEmpty()) {
			desc = list.get(0).getTemplateDescription();
		}
		//Application No Added By Manu	
		desc=desc.replace(CMSConstants.TEMPLATE_APPLICATION_NO, applnNo);
		if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
			MobileMessaging mob=new MobileMessaging();
			mob.setDestinationNumber(mobileNo);
			mob.setMessageBody(desc);
			mob.setMessagePriority(3);
			mob.setSenderName(senderName);
			mob.setSenderNumber(senderNumber);
			mob.setMessageEnqueueDate(new Date());
			mob.setIsMessageSent(false);
			PropertyUtil.getInstance().save(mob);
		}
		
	}
}
