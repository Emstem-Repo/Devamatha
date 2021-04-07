package com.kp.cms.handlers.attendance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentBulkAttendanceSMS;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.attendance.NewAttendanceSmsForm;
import com.kp.cms.handlers.admin.SMSTemplateHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.helpers.attendance.NewAttendanceSmsHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.attandance.IAttendanceEntryTransaction;
import com.kp.cms.transactions.attandance.INewAttendanceSmsTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceEntryTransactionImpl;
import com.kp.cms.transactionsimpl.attendance.NewAttendanceSmsTransImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ConverationUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.SMSUtil;
import com.kp.cms.utilities.SMSUtils;
import com.kp.cms.utilities.jms.SMS_Message;

public class NewAttendanceSmsHandler 
{
	private static volatile NewAttendanceSmsHandler newAttendanceSmsHandler = null;
	private static final Log log = LogFactory.getLog(NewAttendanceSmsHandler.class);
	
	public static NewAttendanceSmsHandler getInstance() 
	{
		if (newAttendanceSmsHandler == null) 
		{
			newAttendanceSmsHandler = new NewAttendanceSmsHandler();
			return newAttendanceSmsHandler;
		}
		return newAttendanceSmsHandler;
	}
public static Map<String, String> weekMap = null;
	
	static {
		weekMap = new HashMap<String, String>();
		weekMap.put("Sun", "Sunday");
		weekMap.put("Mon", "Monday");
		weekMap.put("Tue", "Tuesday");
		weekMap.put("Wed", "Wednesday");
		weekMap.put("Thu", "Thursday");
		weekMap.put("Fri", "Friday");
		weekMap.put("Sat", "Saturday");
		
		
		
		
	}
	@SuppressWarnings("deprecation")
	public void getAbsentees(NewAttendanceSmsForm newAttendanceSmsForm) throws Exception 
	{
		log.info("Handler : Inside getStudents");
		INewAttendanceSmsTransaction newAttendanceSmsTransaction = NewAttendanceSmsTransImpl.getInstance();
		IAttendanceEntryTransaction txn=AttendanceEntryTransactionImpl.getInstance();
		int year=0;
		String[] classes=null;
		java.sql.Date date=CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate());
		if(newAttendanceSmsForm.getYear()!=null && !newAttendanceSmsForm.getYear().isEmpty())
		{
			year=Integer.parseInt(newAttendanceSmsForm.getYear());
		}
		if(newAttendanceSmsForm.getYear()!=null && !newAttendanceSmsForm.getYear().isEmpty())
		{
			year=Integer.parseInt(newAttendanceSmsForm.getYear());
		}
		if(newAttendanceSmsForm.getClasses()!=null && newAttendanceSmsForm.getClasses().length>0 && date!=null && year>0)
		{
			classes=newAttendanceSmsForm.getClasses();
			List<Object[]> absenteeList=newAttendanceSmsTransaction.getAbsentees(year,classes,date);
			
			List<StudentTO> absenteesToList=NewAttendanceSmsHelper.getInstance().convertBoToTO(absenteeList);
			newAttendanceSmsForm.setAbsenteesList(absenteesToList);
		}
		
		
	}

	@SuppressWarnings("static-access")
	public boolean sendSMS(NewAttendanceSmsForm newAttendanceSmsForm) throws Exception 
	{
		log.info("Handler : Inside getAttendanceObj");
		INewAttendanceSmsTransaction newAttendanceSmsTransaction = NewAttendanceSmsTransImpl.getInstance();
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		boolean sent=false;
		String date =new Date().toString();
		Iterator<StudentTO> itr=newAttendanceSmsForm.getStudentList().iterator();
		StudentTO studentTO=null;
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		java.sql.Date attDate=CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate());
		
		while(itr.hasNext())
		{
			
			
			studentTO=itr.next();
			String periodNames="";
			String subjectNames="";
			if(studentTO.getSubList()!=null && !studentTO.isChecked() )
			{
				Object[] tempArray1 = studentTO.getSubList().toArray();
				String intType1 ="";
				/*for(int i=0;i<tempArray1.length;i++){
					 intType1 = tempArray1[i].toString();
					 String subquery1="select s.consldtdMarkCardSubName from Subject s where s.id ="+intType1;
						//periodNames=transaction.getPeriodNamesById(query);
					 subjectNames=subjectNames+NewAttendanceSmsTransImpl.getInstance().getSubjectNamesByIdNew(subquery1)+ ",";
					 
					 if(i<(tempArray1.length-1)){
						 intType1 = intType1+",";
					 }
				}*/
		
			
			if(studentTO.getPeriodList() != null && !studentTO.isChecked()) 
			{	
				Object[] tempArray = studentTO.getPeriodList().toArray();
				String intType ="";
				for(int i=0;i<tempArray.length;i++){
					 intType = intType+tempArray[i].toString();
					 if(i<(tempArray.length-1)){
						 intType = intType+",";
					 }
				}
				
				
				String query="select p.periodName from Period p where p.id in ("+intType+")";
				periodNames=attendanceEntryTransaction.getPeriodNamesById(query);
				String[] perNames=periodNames.split(",");
				String periods="Period ";
				for(String p:perNames)
				{
					periods=periods+p.charAt(p.length()-1)+",";
				}				
				String desc="";
				
				
				SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
				List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_ENTRY);
				if(list != null && !list.isEmpty()) {
					desc = list.get(0).getTemplateDescription();
					desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
					desc=desc.replace(CMSConstants.TEMPLATE_SMS_PERIOD, periods);
				}
				Properties prop = new Properties();
				try {
					InputStream in = AttendanceEntryHelper.class.getClassLoader()
					.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
					prop.load(in);
				} catch (FileNotFoundException e) {	
				log.error("Unable to read properties file...", e);
					return false;
				} catch (IOException e) {
					log.error("Unable to read properties file...", e);
					return false;
				}
				String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
				String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
				
				String collegeName = CMSConstants.COLLEGE_NAME;
				
				if (periods.endsWith(",")) {
					periods = periods.substring(0, periods.length() - 1);
					}
				
				//Bhargav for Sending Attendance Details to Parents
				StringBuffer temp = new StringBuffer();
				
				temp=temp.append("Dear Parent,");
				if(studentTO.getStudentName()!=null && !studentTO.getStudentName().isEmpty()){
					temp=temp.append("Your ward " + studentTO.getStudentName().toUpperCase().replaceAll("\\s+","").trim());
					temp=temp.append(" is absent in class during the following periods,");
				}
				temp=temp.append(""+periods.toUpperCase().replaceAll("\\s+","").trim()+".");
				temp=temp.append("Regards, Principal, BMC");
				String temp1= URLEncoder.encode(temp.toString(),"UTF-8");
				
				MobileMessaging mob=new MobileMessaging();						
				mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
				mob.setMessageBody(temp1);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate()));
				mob.setStudentId(studentTO.getId());
				mob.setIsMessageSent(false);
				mob.setCreatedDate(new Date());
				List<Integer> studId=newAttendanceSmsTransaction.getStudentIds(attDate);
				if(!studId.contains(studentTO.getId()))
				{
					smsList.add(mob);	
				}									

				if(studentTO.getAttendanceStudentId()!=null && !studentTO.getAttendanceStudentId().isEmpty())
		    	{
					List<AttendanceStudent> atteStudList=newAttendanceSmsTransaction.getAttendanceStudent(studentTO.getAttendanceStudentId());				    			
	    			sent=newAttendanceSmsTransaction.updateAttendanceStudent(atteStudList);
		    	}				
			}
			}
			
		}
		PropertyUtil.getInstance().saveSMSList(smsList);
		SMSUtil s =new SMSUtil();
    	SMSUtils smsUtils=new SMSUtils();

		ConverationUtil converationUtil=new ConverationUtil();
    	List<SMS_Message> listSms=converationUtil.convertBotoTO(s.getListOfSMS());
    	//raghu
    	//List<SMS_Message> mobList=smsUtils.sendSMS(listSms);
    	List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
    	s.updateSMS(converationUtil.convertTotoBO(mobList));
		
		log.info("Handler : Leaving getAttendanceObj");
		return sent;
	
	}

	public void getStudents(NewAttendanceSmsForm newAttendanceSmsForm) throws Exception 
	{
		log.info("Handler : Inside getStudents");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		List<Student> students;
		Set<Integer> classSet = new HashSet<Integer>();
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		for (String str : newAttendanceSmsForm.getClasses()) {
			if(str != null){
				classSet.add(Integer.parseInt(str));
			}
		}
		students = attendanceEntryTransaction.getStudentByClass(classSet);
						
			studentList = NewAttendanceSmsHelper.getInstance()
					.copyStudentBoToTO(students, listOfDetainedStudents,newAttendanceSmsForm.getAbsenteesList());
			newAttendanceSmsForm.setStudentList(studentList);
		String date = new Date().toLocaleString();
		newAttendanceSmsForm.setStudentList(studentList);
		int halfLength = 0;
		int totLength = studentList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		newAttendanceSmsForm.setHalfLength(halfLength);
		log.info("Handler : Leaving getStudents");
}

	public boolean getStudentConvertBotoTo(List students,
			NewAttendanceSmsForm newAttendanceSmsForm) throws Exception 
		// TODO Auto-generated method stub
			{
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		log.info(" ********************* Student list size in log file  ******************** :"+students.size());
		log.debug(" ******************* Student start in log file ***********************");
		System.out.println("++++++++++++++++++ Student list size in sysout ++++++++++++++++++: "+students.size());
		
		boolean sent=false;
		String date =new Date().toString();
		StudentTO to=null;
	    List<StudentBulkAttendanceSMS> smsList=new ArrayList<StudentBulkAttendanceSMS>();
	    
	    if(students!=null && students.size()!=0){
	    	
	    Iterator<Object[]> itr= students.iterator();	
	    //Calendar cal = Calendar.getInstance();
	    SimpleDateFormat df = new SimpleDateFormat( "dd/MM/yyyy" );
	    SimpleDateFormat df1 = new SimpleDateFormat( "dd/MM/yyyy" );
	    int prevstudentid=0;
	    int noOfabscenthours=0;
	    String day="";
	    String dayname="";
	    
        while(itr.hasNext()) {
        	Object[] data = itr.next();
        	//for checking one student
        	//if(data[1]!=null && data[1].toString().toString().equalsIgnoreCase("5073")){
        		
        	
        	List studentdata=attendanceEntryTransaction.getStudentByClassBYWeeklyAttendanceData(data[0].toString(),data[1].toString());
        	String temp = "";
        	String msg="";
        	Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -7);
			//String day= df.format(cal.getTime() );
			SimpleDateFormat dformat = new SimpleDateFormat( "dd/MM/yyyy" );  
		    String monday= df.format( cal.getTime() ); 
		    //cal.setTime(new Date());
		    Calendar cale = Calendar.getInstance();
		    cale.add(Calendar.DAY_OF_MONTH, -3);
		    String friday= df.format( cale.getTime() );
		    String name=data[3].toString();
		    Iterator<Object[]> itrerator= studentdata.iterator();
		    String mobile="";
		    while(itrerator.hasNext()) {
		         Object[] studata = itrerator.next();
		         noOfabscenthours=Integer.parseInt(studata[6].toString())- Integer.parseInt(studata[4].toString());
		    	 df1.applyPattern( "EEE" );
		    	 Calendar cal2 = Calendar.getInstance();
					String day1= (CommonUtil.ConvertStringToDateFormat(studata[7].toString(), "yyyy-mm-dd","dd/MM/yyyy"));
			    	Date date1 = dformat.parse(day1);
			    	cal2.setTime(date1);
			    	day=df1.format(cal2.getTime());
		    	// day= df.format(CommonUtil.ConvertStringToSQLDateTime(studata[7].toString()));
		    	 dayname=weekMap.get(day);
		    	 
		    	 if(noOfabscenthours!=0){
		    		 msg=msg+" "+dayname+" "+noOfabscenthours+" hrs,";
		    	 }
		    	 
		   
		    if(studata[9]!=null){
		    	mobile=studata[9].toString();
		    }
		     }//indidual day attendance whilw xlose
		    
		    
		     if(Integer.parseInt(data[4].toString())!=100){
		    	 
		    	 temp=temp+URLEncoder.encode("Dear Parent,this is Monday" + " ", "UTF-8");
		    	 if(data[2]!=null)
		    	 
		    	 temp = temp+URLEncoder.encode(monday+" to Friday" + friday +" attendance report of "+name+ " with " +data[2].toString()+ " & was found absent in the following days on","UTF-8" );
		    	 
		    	 else
		    	 temp = temp+URLEncoder.encode(monday+" to " + friday +"attendance report of "+name+" & was found absent in the following days on ","UTF-8" ); 
		     
		    	 temp = temp+URLEncoder.encode(msg + " ","UTF-8" );
		    	 
		    	 if(data[7].toString().equalsIgnoreCase("MALE"))
		    	 temp = temp+URLEncoder.encode("  and his overall attendance percentage is " +data[4].toString()+ " ","UTF-8" );
		    	 else
		    		 temp = temp+URLEncoder.encode("  and her overall attendance percentage is " +data[4].toString()+ " ","UTF-8" );
		     }
		     else{
                 temp=temp+URLEncoder.encode("Dear Parent,this is Monday(" + " ", "UTF-8");
                 if(data[2]!=null)
    		    	 
    		    	 temp = temp+URLEncoder.encode(monday+") to Friday(" + friday +") attendance report of "+name+ " with " +data[2].toString(),"UTF-8" );
    		    	 
    		    	 else
    		    	 temp = temp+URLEncoder.encode(monday+") to Friday(" + friday +") attendance report of "+name+" & was found absent in the following days on","UTF-8" ); 
		    	     temp = temp+URLEncoder.encode(msg + " ","UTF-8" );
		    	 
		    	 if(data[7].toString().equalsIgnoreCase("MALE"))
			    	 temp = temp+URLEncoder.encode("  and his overall attendance percentage is " +data[4].toString()+ " ","UTF-8" );
			    	 else
			    	temp = temp+URLEncoder.encode("  and her overall attendance percentage is " +data[4].toString()+ " ","UTF-8" );
		     }
		     
		     
		    System.out.println("Print detail of message: name="+name+" att per="+data[4].toString()+" absent hrs="+msg); 
		    StudentBulkAttendanceSMS mob=new StudentBulkAttendanceSMS();	
		    mob.setDestinationNumber("91"+mobile);
		    mob.setMessageBody(temp);
		    mob.setMessagePriority(3);
		    mob.setSenderName("SH");
			mob.setSenderNumber("SH");
			mob.setMessageEnqueueDate(new Date());
			mob.setStudentId(Integer.parseInt(data[1].toString()));
			mob.setClassId(Integer.parseInt(data[0].toString()));
			mob.setIsMessageSent(false);
			mob.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
			
			//List<Integer> studId=newAttendanceSmsTransaction.getStudentIds(attDate);
			//if(!studId.contains(studentTO.getId()))
			//{
			smsList.add(mob);	
			//}	
        	//}//student check over					
					
        }//main student while close
        
        
        PropertyUtil.getInstance().saveSMSListStudentBulkAttendanceSMS(smsList);
        log.info(" ********************* adding Student list to StudentBulkAttendanceSMS in log file after size ******************** :"+smsList.size());
		log.debug(" ******************* add list to StudentBulkAttendanceSMS list***********************");
		System.out.println("++++++++++++++++++ adding Student list to StudentBulkAttendanceSMS in sysout after size ++++++++++++++++++: "+smsList.size());
		
		SMSUtil s =new SMSUtil();
    	SMSUtils smsUtils=new SMSUtils();

		ConverationUtil converationUtil=new ConverationUtil();
    	List<SMS_Message> listSms=converationUtil.convertBotoTOStudentBulkAttendanceSMS(s.getListOfSMSStudentBulkAttendanceSMS());
    	
    	log.info(" ********************* after sending sms in log file after size ******************** :"+smsList.size());
		log.debug(" ******************* after sending sms ***********************");
		System.out.println("++++++++++++++++++ after sending sms in sysout after size ++++++++++++++++++: "+smsList.size());
		
    	List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
    	s.updateSMSStudentBulkAttendanceSMS(converationUtil.convertTotoBOStudentBulkAttendanceSMS(mobList));
		
    	log.info(" ********************* final size ******************** :"+mobList.size());
		log.debug(" ******************* final ***********************");
		System.out.println("++++++++++++++++++ final size ++++++++++++++++++: "+mobList.size());
		
        
		log.info("Handler : Leaving getAttendanceObj");
		}
        
        return sent;
	}

	public void getStudentsByClass(NewAttendanceSmsForm newAttendanceSmsForm,int classId) throws Exception{
		log.info("Handler : Inside getStudents");
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		List<StudentTO> studentList = new ArrayList<StudentTO>();
		List<Student> students;
		Set<Integer> classSet = new HashSet<Integer>();
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		classSet.add(classId);
		students = attendanceEntryTransaction.getStudentByClass(classSet);
		studentList = NewAttendanceSmsHelper.getInstance().copyStudentBoToTO(students, listOfDetainedStudents,newAttendanceSmsForm);
		newAttendanceSmsForm.setStudentList(studentList);
		
		
	}

	public boolean sendSMSToAll(NewAttendanceSmsForm newAttendanceSmsForm) throws Exception{
		log.info("Handler : Inside getAttendanceObj");
		INewAttendanceSmsTransaction newAttendanceSmsTransaction = NewAttendanceSmsTransImpl.getInstance();
		IAttendanceEntryTransaction attendanceEntryTransaction = AttendanceEntryTransactionImpl.getInstance();
		boolean sent=false;
		String date =new Date().toString();
		Iterator<StudentTO> itr=newAttendanceSmsForm.getStudentList().iterator();
		StudentTO studentTO=null;
		List<MobileMessaging> smsList=new ArrayList<MobileMessaging>();
		java.sql.Date attDate=CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate());
		
		while(itr.hasNext())
		{
			studentTO=itr.next();
			String periodNames="";
		
			
			if(newAttendanceSmsForm.getTeacherNotEnteredPeriodList() != null) 
			{	
				Object[] tempArray = newAttendanceSmsForm.getTeacherNotEnteredPeriodList().toArray();
				String intType ="";
				for(int i=0;i<tempArray.length;i++){
					 intType = intType+tempArray[i].toString();
					 if(i<(tempArray.length-1)){
						 intType = intType+",";
					 }
				}
				
				
				String query="select p.periodName from Period p where p.id in ("+intType+")";
				periodNames=attendanceEntryTransaction.getPeriodNamesById(query);
				String[] perNames=periodNames.split(",");
				String periods="Period ";
				for(String p:perNames)
				{
					periods=periods+p.charAt(p.length()-1)+",";
				}				
				String desc="";
				
				
				SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
				List<SMSTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.TEMPLATE_ATTENDANCE_ENTRY);
				if(list != null && !list.isEmpty()) {
					desc = list.get(0).getTemplateDescription();
					desc=desc.replace(CMSConstants.TEMPLATE_SMS_DATE, date);
					desc=desc.replace(CMSConstants.TEMPLATE_SMS_PERIOD, periods);
				}
				Properties prop = new Properties();
				try {
					InputStream in = AttendanceEntryHelper.class.getClassLoader()
					.getResourceAsStream(CMSConstants.SMS_FILE_CFG);
					prop.load(in);
				} catch (FileNotFoundException e) {	
				log.error("Unable to read properties file...", e);
					return false;
				} catch (IOException e) {
					log.error("Unable to read properties file...", e);
					return false;
				}
				String senderNumber=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NUMBER);
				String senderName=prop.getProperty(CMSConstants.KNOWLEDGEPRO_SENDER_NAME);
				
				String collegeName = CMSConstants.COLLEGE_NAME;
				
				if (periods.endsWith(",")) {
					periods = periods.substring(0, periods.length() - 1);
					}
				
				//Bhargav for Sending Attendance Details to Parents
				StringBuffer temp = new StringBuffer();
				
				temp=temp.append("Dear Parent,");
				if(studentTO.getStudentName()!=null && !studentTO.getStudentName().isEmpty()){
					temp=temp.append("Your ward " + studentTO.getStudentName().toUpperCase().replaceAll("\\s+","").trim());
					temp=temp.append(" is absent in class during the following periods,");
				}
				temp=temp.append(""+periods.toUpperCase().replaceAll("\\s+","").trim()+".");
				temp=temp.append("Regards, Principal, BMC");
				String temp1= URLEncoder.encode(temp.toString(),"UTF-8");
				
				MobileMessaging mob=new MobileMessaging();						
				mob.setDestinationNumber(studentTO.getMobileNo1()+studentTO.getMobileNo2());
				mob.setMessageBody(temp1);
				mob.setMessagePriority(3);
				mob.setSenderName(senderName);
				mob.setSenderNumber(senderNumber);
				mob.setMessageEnqueueDate(new Date());
				mob.setAttendanceDate(CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate()));
				mob.setStudentId(studentTO.getId());
				mob.setIsMessageSent(false);
				mob.setCreatedDate(new Date());
				List<Integer> studId=newAttendanceSmsTransaction.getStudentIds(attDate);
				if(!studId.contains(studentTO.getId()))
				{
					smsList.add(mob);	
				}													
			}
			
		}
		PropertyUtil.getInstance().saveSMSList(smsList);
		SMSUtil s =new SMSUtil();
    	SMSUtils smsUtils=new SMSUtils();
		ConverationUtil converationUtil=new ConverationUtil();
    	List<SMS_Message> listSms=converationUtil.convertBotoTO(s.getListOfSMS());
    	//raghu
    	//List<SMS_Message> mobList=smsUtils.sendSMS(listSms);
    	List<SMS_Message> mobList=smsUtils.sendSMSAutomatic(listSms);
    	s.updateSMS(converationUtil.convertTotoBO(mobList));
		
		log.info("Handler : Leaving getAttendanceObj");
		return sent;
	
	}

	public void getAbsenteesForSms(NewAttendanceSmsForm newAttendanceSmsForm) throws Exception{
		log.info("Handler : Inside getStudents");
		INewAttendanceSmsTransaction newAttendanceSmsTransaction = NewAttendanceSmsTransImpl.getInstance();
		IAttendanceEntryTransaction txn=AttendanceEntryTransactionImpl.getInstance();
		int year=0;
		String[] classes=null;
		java.sql.Date date=CommonUtil.ConvertStringToSQLDate(newAttendanceSmsForm.getAttendancedate());
		if(newAttendanceSmsForm.getYear()!=null && !newAttendanceSmsForm.getYear().isEmpty())
		{
			year=Integer.parseInt(newAttendanceSmsForm.getYear());
		}
		
		String dayOfthePeriods=newAttendanceSmsForm.getAttendanceDay();
		if(newAttendanceSmsForm.getYear()!=null && !newAttendanceSmsForm.getYear().isEmpty())
		{
			year=Integer.parseInt(newAttendanceSmsForm.getYear());
		}
		if(newAttendanceSmsForm.getClasses()!=null && newAttendanceSmsForm.getClasses().length>0 && date!=null && year>0)
		{
			classes=newAttendanceSmsForm.getClasses();
			//here we are getting all absentees as well as if teacher not entered attendance then that students also considered as absentees
			List<Object[]> absenteeList=newAttendanceSmsTransaction.getAbsenteesForSms(year,classes,date,dayOfthePeriods);
			
			List<StudentTO> absenteesToList=NewAttendanceSmsHelper.getInstance().convertBoToTOforSMS(absenteeList);
			newAttendanceSmsForm.setAbsenteesList(absenteesToList);
		}
		
		
	}


}
