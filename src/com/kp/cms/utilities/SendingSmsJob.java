package com.kp.cms.utilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ibm.icu.util.Calendar;
import com.kp.cms.handlers.employee.AttendanceRemainderHandler;

public class SendingSmsJob implements Job{
	private static final Log log = LogFactory.getLog(SendingSmsJob.class);	
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		log.info("Entered into SendingSmsJob"+Calendar.getInstance().getTimeInMillis());
		try {
				AttendanceRemainderHandler.getInstance().sendingSMS();
		} catch (Exception e) {
			log.error("Error in SendingSmsJob"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from SendingSmsJob"+Calendar.getInstance().getTimeInMillis());
	}

}
