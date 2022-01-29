package com.kp.cms.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.handlers.employee.AttendanceRemainderHandler;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;

public class AttendanceRemainderJob implements Job {
	private static final Log log = LogFactory.getLog(AttendanceRemainderJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into AttendanceRemainderJobScheduler"+Calendar.getInstance().getTimeInMillis());
		try {
			String smsAlert=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(0,"EmployeeSettings",false,"smsAlert");
			if(smsAlert!=null && (smsAlert.equalsIgnoreCase("1") || smsAlert.equalsIgnoreCase("true")))
				AttendanceRemainderHandler.getInstance().checkAttendanceForTheCurrentDayAndTime();
		} catch (Exception e) {
			log.error("Error in AttendanceRemainderJobScheduler"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from AttendanceRemainderJobScheduler"+Calendar.getInstance().getTimeInMillis());
	}

}
