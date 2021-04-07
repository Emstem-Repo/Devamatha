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
import com.kp.cms.handlers.attendance.AttendanceEntryDayHandler;
import com.kp.cms.handlers.attendance.MobileSMSCriteriaHandler;
import com.kp.cms.handlers.attendance.NewAttendanceSmsHandler;
import com.kp.cms.helpers.admission.StudentEditHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;

public class AttendanceDayEntryJob implements Job {
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
		log.info("Entered into AttendanceDayEntry"+Calendar.getInstance().getTimeInMillis());

		try {
			AttendanceEntryDayHandler.getInstance().AddAttendanceEntryUsingJobRemainder();
			
			
		} catch (Exception e) {
			log.error("Error in AttendanceDayEntry"+Calendar.getInstance().getTimeInMillis());
			e.printStackTrace();
		}
		log.info("Exit from AttendanceDayEntry"+Calendar.getInstance().getTimeInMillis());
	}
}
