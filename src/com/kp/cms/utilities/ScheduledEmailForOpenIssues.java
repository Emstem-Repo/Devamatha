package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.admin.StudentSupportRequestHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.utilities.jms.MailTO;

public class ScheduledEmailForOpenIssues implements Job{
	private static final Log log = LogFactory.getLog(DocumentPendingMailJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
		try {
			Properties prop = new Properties();
			InputStream in = ScheduledEmailForOpenIssues.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        try
	        {
	        	prop.load(in);
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
	        int noOfDays2=Integer.parseInt(CMSConstants.SCHEDULED_EMAIL_NO_OF_DAYS2);
			String mailId2=CMSConstants.SCHEDULED_EMAIL_FOR_EMPLOYEE2;
			String mailArray2[]=mailId2.split(",");
			int noOfDays1=Integer.parseInt(CMSConstants.SCHEDULED_EMAIL_NO_OF_DAYS1);
			String mailId1=CMSConstants.SCHEDULED_EMAIL_FOR_EMPLOYEE1;
			String mailArray1[]=mailId1.split(",");
			Date date1=StudentSupportRequestHandler.getInstance().getPreviousDate(new Date(), noOfDays1);
			java.sql.Date sqlDate1=CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(date1));
			Date date2=StudentSupportRequestHandler.getInstance().getPreviousDate(new Date(), noOfDays2);
			java.sql.Date sqlDate2=CommonUtil.ConvertStringToSQLDate(CommonUtil.formatDates(date2));
			String openIssues1=StudentSupportRequestHandler.getInstance().getOpenIssuesToMail(sqlDate1);
			String openIssues2=StudentSupportRequestHandler.getInstance().getOpenIssuesToMail(sqlDate2);
			if(openIssues2!=null){
				List<GroupTemplate> list=TemplateHandler.getInstance().getDuplicateCheckList(CMSConstants.SCHEDULED_EMAIL_FOR_USER2);
				String des="";
				if(list != null && !list.isEmpty()) {
					des =list.get(0).getTemplateDescription();
					MailTO mailto = new MailTO();
					des=des.replace(CMSConstants.SCHEDULED_EMAIL_NO_OF_ISSUES_OPEN,String.valueOf(openIssues2));
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=CMSConstants.MAIL_USERID;
					String subject="Support Request Escalation";
					for(int i=0;i<mailArray2.length;i++){
						mailto.setFromAddress(fromAddress);
						mailto.setFromName(fromName);
						mailto.setMessage(des);
						mailto.setSubject(subject);
						mailto.setToAddress(mailArray2[i]);
						CommonUtil.sendMail(mailto);
					}
				}
			}
			if(openIssues1!=null){
				List<GroupTemplate> list=TemplateHandler.getInstance().getDuplicateCheckList(CMSConstants.SCHEDULED_EMAIL_FOR_USER1);
				String des="";
				if(list != null && !list.isEmpty()) {
					des =list.get(0).getTemplateDescription();
					MailTO mailto = new MailTO();
					des=des.replace(CMSConstants.SCHEDULED_EMAIL_NO_OF_ISSUES_OPEN,String.valueOf(openIssues1));
					String fromName = prop.getProperty(CMSConstants.STUDENTLOGIN_CERTIFICATE_REQUEST_FROMNAME);
					String fromAddress=CMSConstants.MAIL_USERID;
					String subject="Support Request Escalation";
					for(int i=0;i<mailArray1.length;i++){
						mailto.setFromAddress(fromAddress);
						mailto.setFromName(fromName);
						mailto.setMessage(des);
						mailto.setSubject(subject);
						mailto.setToAddress(mailArray1[i]);
						CommonUtil.sendMail(mailto);
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from DocumentDueMailJob"+Calendar.getInstance().getTimeInMillis());
	}
}
