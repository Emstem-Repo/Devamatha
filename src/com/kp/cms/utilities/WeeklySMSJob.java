package com.kp.cms.utilities;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.ibm.wsdl.util.StringUtils;
import com.itextpdf.text.log.SysoLogger;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.MobileSMSCriteriaHandler;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;

public class WeeklySMSJob implements Job {
	private static final Log log = LogFactory.getLog(SendingSmsJob.class);	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());

		try {
			Date attToday=new Date();

			String todatDate=CommonUtil.ConvertStringToDateFormat(
					CommonUtil.getStringDate(attToday),
					WeeklySMSJob.SQL_DATEFORMAT,
					WeeklySMSJob.FROM_DATEFORMAT);

			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);

			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			int smsTimehours=calendar.getTime().getHours();
			int smsMinitue=calendar.getTime().getMinutes();
			String[] smsClass=MobileSMSCriteriaHandler.getInstance().getSmsClassList(currentYear,smsTimehours,smsMinitue);

			if(smsClass!=null && !smsClass.equals(null))
			{
				
					NewAttendanceSmsForm newAttendanceSmsForm = new NewAttendanceSmsForm();
					newAttendanceSmsForm.setAttendancedate(todatDate);
					String syear=""+year;
					newAttendanceSmsForm.setYear(syear);




					newAttendanceSmsForm.setClasses(smsClass);
					
					
					IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
					List students;
					Set<Integer> classSet = new HashSet<Integer>();
				    for (String str : newAttendanceSmsForm.getClasses()) {
						if(str != null){
							classSet.add(Integer.parseInt(str));
							
						}
					}
					students = attendanceEntryTransaction.getStudentByClassBYWeeklyAttendance(classSet);
					
					if(students!=null && students.size()!=0){
						
					
					boolean isSent=false;
					//if(classSet!=null && !classSet.isEmpty() && students.size()!=0 && !students.isEmpty()){
						isSent=NewAttendanceSmsHandler.getInstance().getStudentConvertBotoTo(students,newAttendanceSmsForm);
						if(isSent)
						{
							System.out.println("Message sent sucess"); 
							log.info("Message sent sucess");
						}
						else
						{
							System.out.println("Message Sent Fail");
							log.error("Message Sent Fail");
						}
					}else{
						System.out.println("Message Sent Fail");
						log.error("Message Sent Fail");
					}
					//}
					//else
					//{
					//	log.error("Class List is Null ");
					//}
					
					
					
					//NewAttendanceSmsHandler.getInstance().getAbsentees(newAttendanceSmsForm);
					//NewAttendanceSmsHandler.getInstance().getStudents(newAttendanceSmsForm);
					//boolean isMsgSent=false;
					/*if(newAttendanceSmsForm.getAbsenteesList()!=null && newAttendanceSmsForm.getAbsenteesList().size()!=0 && smsClass!=null && smsClass.length!=0 )
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
					else
					{
						log.error("Class List is Null ");
					}
				*/

				System.out.println("calling triger by job scheduler");
			}

		} catch (Exception e) {
			log.error("Error in SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from SendingNewSMSJob"+Calendar.getInstance().getTimeInMillis());
	}
}
