package com.kp.cms.utilities;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.bo.exam.ExamMidsemRepeat;
import com.kp.cms.bo.exam.ExamMidsemRepeatSetting;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.handlers.exam.ExamMidsemRepeatHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.transactionsimpl.exam.ExamMidsemRepeatTransactionImpl;

public class RepeatMidSemReminderSmsMail implements Job{
	private static final Log log = LogFactory.getLog(RepeatMidSemReminderSmsMail.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into Repeat Mid Sem Reminder Sms Mail"+Calendar.getInstance().getTimeInMillis());
		try {
			Properties prop = new Properties();
			InputStream in = RepeatMidSemReminderSmsMail.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			InputStream sin = AttendanceEntryHelper.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
	        try
	        {
	        	prop.load(in);
	        	prop.load(sin);
	        }
	        catch (Exception e) {
				// TODO: handle exception
	        	e.printStackTrace();
			}
			ExamMidsemRepeatSetting checkForFeesDate=ExamMidsemRepeatHandler.getInstance().ReminderMailForFeePayment();
			if(checkForFeesDate!=null) {
			List<ExamMidsemRepeat> BoData = ExamMidsemRepeatTransactionImpl.getInstance().repeatMidSemExamReminder(checkForFeesDate.getMidSemExamId().getId());
			
			if(BoData != null){
				String templetNameSms=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_SMS;
				String templetNameMails=CMSConstants.MID_SEM_REPEAT_EXAM_FEEPAYMENT_REMINDER_MAIL;
				ExamMidsemRepeatHandler.getInstance().SendSmsToStudent(checkForFeesDate,BoData,templetNameSms,prop);
				ExamMidsemRepeatHandler.getInstance().SendMailsToStudent(checkForFeesDate,BoData,templetNameMails,prop,templetNameMails);
			}
		}
		} catch (Exception e) {
			log.error("Error in Reminder MailSms For Mid Sem Repeat Exam Fee Payment "+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Error in Reminder MailSms For Mid Sem Repeat Exam Fee Payment "+Calendar.getInstance().getTimeInMillis());
	}
}
