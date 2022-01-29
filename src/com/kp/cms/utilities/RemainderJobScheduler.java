package com.kp.cms.utilities;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.kp.cms.constants.CMSConstants;

/**
 * Servlet implementation class RemainderJobScheduler
 */
public class RemainderJobScheduler extends HttpServlet {
	private String triggerTime;

	public void init() throws ServletException {
		Properties prop = new Properties();
		InputStream in = JobScheduler.class.getClassLoader()
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		try {
			prop.load(in);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String[] triggers = prop.getProperty("knowledgepro.employee.remainder.triger.time").split("///");
		String sendSms= prop.getProperty("knowledgepro.sms.send");
		String sendMail= prop.getProperty("knowledgepro.mail.send");
		String newSmsSent=prop.getProperty("knowledgepro.new.scheduled.sms.send");
		String consolidatedSMS=prop.getProperty("knowledgepro.consolidated.attendance.sms.send.sent");
		String cronExpression = prop.getProperty("knowledgepro.student.mail.remainder.triger.time");
		String pendingMails = prop.getProperty("knowledgepro.Document.pending.remainder.triger.time");
		String dueMails = prop.getProperty("knowledgepro.document.pending.due.triger.time");
		String valuationScheduleRemainder = prop.getProperty("knowledgepro.document.valuation.schedule.remainder.triger.time");
		String forAmMails = prop.getProperty("knowledgepro.exam.am.remainder.mailsms.time");
		String forPmMails = prop.getProperty("knowledgepro.exam.pm.remainder.mailsms.time");
		String[] studentHostelTime = prop.getProperty("knowledgepro.student.hostel.remainder.triger.time").split(",");
		String sapExamSMSPm = prop.getProperty("knowledgepro.sap.exam.sms.remainder.trigger.time");
		String supportRequestOpenIssueMail=prop.getProperty("knowledgepro.support.request.mail.remainder.trigger.time");
		String repeatMidSemReminderSmsMail = prop.getProperty("knowledgepro.exam.Repeat.MidSemReminder.SmsMail.time");
		String attendanceDayEntry = prop.getProperty("knowledgepro.exam.am.remainder.att.day.entry.time");
		try
		
		{
			if(newSmsSent!=null && newSmsSent.equalsIgnoreCase("true"))
			{
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("ftm", "ftmgroup" , SendingNewSMSJob.class);
				cronTrigger = new CronTrigger("mailcronTrigger" , "mailGroup" , "0  0/5  *  *  *  ?");
				sche.scheduleJob(jDetail, cronTrigger);
			}
			if (attendanceDayEntry!=null) {
			JobDetail jDetail;
			CronTrigger cronTrigger;
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sche = sf.getScheduler();
			sche.start();
			jDetail = new JobDetail("attdayentryjd", "attdayentryjdGroup" , AttendanceDayEntryJob.class);
			cronTrigger = new CronTrigger("attdayentrycronTrigger" , "attdayentryGroup" , attendanceDayEntry);
			sche.scheduleJob(jDetail, cronTrigger);
			}
			if(consolidatedSMS!=null && consolidatedSMS.equalsIgnoreCase("true"))
			{
				/*JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("test", "test2" , SendingConsolidatedAttendanceSMSJob.class);
				cronTrigger = new CronTrigger("cronTrigger" , "mailGroup" , "0  0/1  *  *  *  ?");
				sche.scheduleJob(jDetail, cronTrigger);
		*/	}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		
		}
		
		/*
		try {
		if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				for (int i = 0; i < triggers.length; i++) {
					triggerTime = triggers[i].trim(); 
					jDetail = new JobDetail("Job" + i, "group" + i, AttendanceRemainderJob.class);
					cronTrigger = new CronTrigger("cronTrigger" + i, "group" + i, triggerTime);
					sche.scheduleJob(jDetail, cronTrigger);
				}
				jDetail = new JobDetail("Job", "group" , SendingSmsJob.class);
				cronTrigger = new CronTrigger("cronTrigger" , "group" , "0  0/1  *  *  *  ?");
				sche.scheduleJob(jDetail, cronTrigger);
			}
			if(sendMail!=null && sendMail.equalsIgnoreCase("true")){

				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("MailJob", "mailGroup" , SendingMailJob.class);
				cronTrigger = new CronTrigger("mailcronTrigger" , "mailGroup" , "0  0/1  *  *  *  ?");
				sche.scheduleJob(jDetail, cronTrigger);
			
			}if(sendMail!=null && sendMail.equalsIgnoreCase("true")){

				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("PendinMailJob", "PendingMail" , DocumentPendingMailJob.class);
				cronTrigger = new CronTrigger("PendinTrigger" , "PendingMail" , pendingMails);
				sche.scheduleJob(jDetail, cronTrigger);
			}
			if(sendMail!=null && sendMail.equalsIgnoreCase("true")){

				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("DueMailJob", "DueMail" , DocumentDueMail.class);
				cronTrigger = new CronTrigger("DueTrigger" , "DueMail" , dueMails);
				sche.scheduleJob(jDetail, cronTrigger);
			}
			// Online Application Remainder Mail 
			{
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("StudentMailJob", "StudentMail" , StudentSendingMailJob.class);
				cronTrigger = new CronTrigger("Trigger" , "StudentMail" , cronExpression);
				sche.scheduleJob(jDetail, cronTrigger);
			}

			
			{
				JobDetail jDetails;
				CronTrigger cronTrigger1;
				SchedulerFactory sf1 = new StdSchedulerFactory();
				Scheduler scheduler = sf1.getScheduler();
				scheduler.start();
				jDetails = new JobDetail("ExamValuatorInfoJob","ExamValuatorInfoMail",ExamValuatorPreInfo.class);
				cronTrigger1 = new CronTrigger("ExamValuatorInfoTrigger","ExamValuatorInfoMail",valuationScheduleRemainder);
				scheduler.scheduleJob(jDetails,cronTrigger1);
			}
			if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				JobDetail jDetails;
				CronTrigger cronTrigger1;
				SchedulerFactory sf1 = new StdSchedulerFactory();
				Scheduler scheduler = sf1.getScheduler();
				scheduler.start();
				for (int i = 0; i < studentHostelTime .length; i++) {
					jDetails = new JobDetail("HostelAttJob"+i,"HostelAttMail"+i,StudentHostelAttendance.class);
					cronTrigger1 = new CronTrigger("HostelAttTrigger"+i,"HostelAttMail"+i,studentHostelTime[i]);
					scheduler.scheduleJob(jDetails,cronTrigger1);
				}
			}
			if((sendMail!=null && sendMail.equalsIgnoreCase("true")) || (sendSms!=null && sendSms.equalsIgnoreCase("true")) ){

				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("SmsMailJobForAm", "SmsMailAm" , SmsMailForAmJob.class);
				cronTrigger = new CronTrigger("SmsMailAmTrigger" , "SmsMailAm" , forAmMails);
				sche.scheduleJob(jDetail, cronTrigger);
			}
			if((sendMail!=null && sendMail.equalsIgnoreCase("true")) || (sendSms!=null && sendSms.equalsIgnoreCase("true")) ){

				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("SmsMailJobForPm", "SmsMailPm" , SmsMailForPmJob.class);
				cronTrigger = new CronTrigger("SmsMailPmTrigger" , "SmsMailPm" , forPmMails);
				sche.scheduleJob(jDetail, cronTrigger);
			}
			if(sendSms!=null && sendSms.equalsIgnoreCase("true")){
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("SmsJobForStudentAndInvigilator", "SmsJobForStudentAndInvigilator6Pm" , SendSMSStudentAndInvigilatorOfSAPExam.class);
				cronTrigger = new CronTrigger("SmsPmTrigger" , "SmsJobForStudentAndInvigilator6Pm" , sapExamSMSPm);
				sche.scheduleJob(jDetail, cronTrigger);
			}
			//start by giri
			if(sendMail!=null && sendMail.equalsIgnoreCase("true")){
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("ScheduledMailForOpenIssueJob", "OpenIssueMail" , ScheduledEmailForOpenIssues.class);
				cronTrigger = new CronTrigger("ScheduledMailForOpenIssueTrigger" , "OpenIssueMail" , supportRequestOpenIssueMail);
				sche.scheduleJob(jDetail, cronTrigger);
			}//end by giri
			if((sendMail!=null && sendMail.equalsIgnoreCase("true")) || (sendSms!=null && sendSms.equalsIgnoreCase("true")) ){
				JobDetail jDetail;
				CronTrigger cronTrigger;
				SchedulerFactory sf = new StdSchedulerFactory();
				Scheduler sche = sf.getScheduler();
				sche.start();
				jDetail = new JobDetail("RepeatMidSemReminderSmsMail", "SmsMailRepeatExam" , RepeatMidSemReminderSmsMail.class);
				cronTrigger = new CronTrigger("SmsMailRepeatExamTrigger" , "SmsMailRepeatExam" , repeatMidSemReminderSmsMail);
				sche.scheduleJob(jDetail, cronTrigger);
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
		
		
		
		
	}
}
