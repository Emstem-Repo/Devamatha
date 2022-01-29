package com.kp.cms.utilities;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.kp.cms.forms.employee.ReadAttendanceFileForm;
import com.kp.cms.handlers.employee.ReadAttendanceFileHandler;

public class ReadAttendanceFileJob implements Job{

     public ReadAttendanceFileJob() {
        
     }


    public void execute(JobExecutionContext context) throws JobExecutionException {
        ReadAttendanceFileForm formObj=new ReadAttendanceFileForm();
        try
        {
        	ReadAttendanceFileHandler.getInstance().readAttandanceFile(formObj);
        }
        catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
    }
}
