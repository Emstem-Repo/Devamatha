package com.kp.cms.utilities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.ibm.wsdl.util.StringUtils;
import com.itextpdf.text.log.SysoLogger;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.MobileSMSCriteriaHandler;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;

public class SendingNewSMSJob implements Job {
	private static final Log log = LogFactory.getLog(SendingSmsJob.class);	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";	
	
	private static Map<Integer,String> dayMap=null;
	static{
		dayMap=new HashMap<Integer, String>();
		dayMap.put(1, "Sunday");
		dayMap.put(2, "Monday");
		dayMap.put(3, "Tuesday");
		dayMap.put(4, "Wednesday");
		dayMap.put(5, "Thursday");
		dayMap.put(6, "Friday");
		dayMap.put(7, "Saturday");
	}
	
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());

		try {
			Date attToday=new Date();

			String todatDate=CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(attToday),
					SendingNewSMSJob.SQL_DATEFORMAT,
					SendingNewSMSJob.FROM_DATEFORMAT);

			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear( );
			if(year!=0){
				currentYear=year;
			}
			int day=calendar.get(Calendar.DAY_OF_WEEK);
			int smsTimehours=calendar.getTime().getHours();
			int smsMinitue=calendar.getTime().getMinutes();
			String[] smsClass=MobileSMSCriteriaHandler.getInstance().getSmsClassList(currentYear,smsTimehours,smsMinitue);

			if(smsClass!=null && !smsClass.equals(null))
			{
					IAttendanceEntryTransaction txn=AttendanceEntryTransactionImpl.getInstance();
					NewAttendanceSmsForm newAttendanceSmsForm = new NewAttendanceSmsForm();
					newAttendanceSmsForm.setAttendancedate(todatDate);
					String syear=""+year;
					newAttendanceSmsForm.setYear(syear);
					java.sql.Date date=CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate());
					
					
					newAttendanceSmsForm.setDate(todatDate);
					String attendanceDayCheck=txn.getAttendanceEntryDay(newAttendanceSmsForm);
					if(attendanceDayCheck==null || attendanceDayCheck.isEmpty()){
						attendanceDayCheck=dayMap.get(day);
					}
					newAttendanceSmsForm.setAttendanceDay(attendanceDayCheck);
					boolean isCurriculum=txn.isDateBetweenCurriculumSchemeDuration(smsClass,date,currentYear);
					newAttendanceSmsForm.setClasses(smsClass);
					
					if(isCurriculum){
				//	NewAttendanceSmsHandler.getInstance().getAbsentees(newAttendanceSmsForm);
						//here we are getting all absentees as well as if teacher not entered attendance then that students also considered as absentees implemented by Bhargav
					NewAttendanceSmsHandler.getInstance().getAbsenteesForSms(newAttendanceSmsForm);
					NewAttendanceSmsHandler.getInstance().getStudents(newAttendanceSmsForm);
					}
					boolean isMsgSent=false;
					if(newAttendanceSmsForm.getAbsenteesList()!=null && newAttendanceSmsForm.getAbsenteesList().size()!=0 && smsClass!=null && smsClass.length!=0 )
					{
						isMsgSent=NewAttendanceSmsHandler.getInstance().sendSMS(newAttendanceSmsForm);
						if(isMsgSent)
						{
							System.out.println("Message sent sucess");
							log.info("Message sent sucess");
						}
						else
						{
							System.out.println("Message Sent Fail");
							log.error("Message Sent Fail");
						}
					}
					
				

				System.out.println("calling triger by job scheduler");
			}

		} catch (Exception e) {
			log.error("Error in SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());
	}
}
