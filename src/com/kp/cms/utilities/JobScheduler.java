package com.kp.cms.utilities;

import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.kp.cms.constants.CMSConstants;


public class JobScheduler extends HttpServlet implements Servlet
{
	private String triggerTime;
@Override
	public void init() throws ServletException
	{
		Properties prop = new Properties();
		InputStream in = JobScheduler.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        try
        {
        	prop.load(in);
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
        triggerTime=prop.getProperty("knowledgepro.employee.readAttendanceFile.triiger.time");
		SchedulerFactory schedFact = new StdSchedulerFactory();
	/*	try
		{
			
			Scheduler schedule = schedFact.getScheduler();
			ServletContext context = this.getServletContext();
			schedule.getContext().put("servletContext", context);
			JobDetail job = new JobDetail("AutoEDBLogPull","EDBLogGroup",ReadAttendanceFileJob.class);
			CronTrigger trigger = new CronTrigger("EDBLogTrigger","EDBLogGroup","AutoEDBLogPull","EDBLogGroup",triggerTime);
			schedule.addJob(job,true);
			schedule.start();
		}
		catch (SchedulerException e)
		{
			e.printStackTrace();
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}*/
	}
}


