package com.kp.cms.actions.admin;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;
import org.hibernate.cfg.Configuration;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.DatabaseForm;


public class DbScriptDownload extends DownloadAction {

	private static final Logger log = Logger.getLogger(DbScriptDownload.class);
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered into getStreamInfo method of DbScriptDownload action class.");
		DatabaseForm databaseForm = (DatabaseForm)form;

		Configuration config = new Configuration().configure();
		 // gets the ip of database from properties file.
		String url= config.getProperty("hibernate.connection.url");
		String ip = url.substring(url.indexOf("//")+2,url.lastIndexOf(":"));

		String databaseName="";
		String username = "";
		String password = "";
		if(ip.equalsIgnoreCase("localhost")){
			try {
		        InetAddress addr = InetAddress.getLocalHost();
		        // Get IP Address
		        byte[] ipAddr = addr.getAddress();
//		        ip = addr.getHostAddress();
		        databaseName = url.substring(url.lastIndexOf("/")+1);
		        // gets the username of database from properties file.
				username = config.getProperty("hibernate.connection.username");
				// gets the password of database from properties file.
				password = config.getProperty("hibernate.connection.password");
		    } catch (Exception e) {
		    	log.error("Error occured in getStreamInfo of DbScriptDownload class..");
				if (e instanceof ApplicationException) {
					databaseForm.setErrorStack(e.getMessage());
				}else {
					throw e;
				}
		    }
		}else{
//			ip= config.getProperty("hibernate.connection.url").split(":");
			//gets the database of database from properties file.
			databaseName = url.substring(url.lastIndexOf("/")+1);
			// gets the username of database from properties file.
			username = config.getProperty("hibernate.connection.username");
			// gets the password of database from properties file.
			password = config.getProperty("hibernate.connection.password");
		}
	    // gets the mode from UI.
		String mode = databaseForm.getMode();
		String option = "";
		String fileName = "";
		String meridian = "";
		File test = null;
		Calendar  calendar = Calendar.getInstance();
		//getting month name form month value by calling getMonthForInt() method.
		String monthName = getMonthForInt(calendar.get(Calendar.MONTH));
		if(calendar.get(Calendar.HOUR_OF_DAY)<12)
			meridian = "AM";
		else
			meridian = "PM";
			if(mode.equals("script")){
				// for database script.
				fileName = databaseName+"script"+calendar.get(Calendar.DATE)+monthName+calendar.get(Calendar.HOUR_OF_DAY)+meridian;
				option = "--no-data";
				test = new File(fileName+".sql"); //filename will be assigned here.
			}else{
				// for database data backup.
				fileName = databaseName+"databackup"+calendar.get(Calendar.DATE)+monthName+calendar.get(Calendar.HOUR_OF_DAY)+meridian;
				option = "-t";
				test = new File(fileName+".sql");  //filename will be assigned here.
			}
	        FileWriter writer = null;
	        try {
	        	writer = new FileWriter(test);
		        Runtime runtime = Runtime.getRuntime();

	        // execute the command to get the files.
		        Process child = runtime.exec("mysqldump -h"+ip+" "+option+" -u"+username+" -p"+password+" "+databaseName+"");
		        InputStream in = child.getInputStream();
		        InputStreamReader xx = new InputStreamReader(in, "utf8");
		        char[] chars = new char[1];     
		        int ibyte = 0;
		        //writing the files.
		        while ((ibyte = xx.read(chars)) > 0) {
		        	writer.write(chars);
		        }
		        writer.close();
	        }catch(Exception e){
	        	log.error("error in getStreamInfo method of DbScriptDownload action class",e);
				if (e instanceof ApplicationException) {
					log.error("error in getStreamInfo method of DbScriptDownload action class",e);
					databaseForm.setErrorStack(e.getMessage());
				} else {
					throw e;
				}
	        } 
		String contentType = response.getContentType();
		response.setHeader("Content-disposition", "attachment; filename ="+test);
		log.info("end of getStreamInfo method of DbScriptDownload action class.");
		return new FileStreamInfo(contentType, test);
	}
	
	
	/**
	 * This method gives month name if month value is provided.
	 * @param month value.
	 * @return month name.
	 */
	
	public String getMonthForInt(int monthValue) {
	    String month = "invalid";
	    DateFormatSymbols dfs = new DateFormatSymbols();
	    String[] months = dfs.getMonths();
	    if (monthValue >= 0 && monthValue <= 11 ) {
	        month = months[monthValue];
	    }
	    return month;
	}
}