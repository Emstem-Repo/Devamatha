package com.kp.cms.helpers.employee;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.EmpReadAttendanceFileBO;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ReadAttendanceFileForm;
import com.kp.cms.handlers.employee.ReadAttendanceFileHandler;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.to.employee.AttendanceFileTO;
import com.kp.cms.transactions.employee.IReadAttendanceFileTransaction;
import com.kp.cms.transactionsimpl.employee.ReadAttendanceFileTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ReadAttendanceFileHelper 
{
	@SuppressWarnings("unused")
	private static final Log log = LogFactory.getLog(ReadAttendanceFileHelper.class);
	public static volatile ReadAttendanceFileHelper objHelper = null;

	public static ReadAttendanceFileHelper getInstance() {
		if (objHelper == null) {
			objHelper = new ReadAttendanceFileHelper();
			return objHelper;
		}
		return objHelper;
	}

	public ArrayList<EmpReadAttendanceFileBO> copyDataFromFormToBO(
			List<AttendanceFileTO> objTO, String inPutDateFormat, String userId)
			throws Exception {
		ArrayList<EmpReadAttendanceFileBO> list = new ArrayList<EmpReadAttendanceFileBO>();
		EmpReadAttendanceFileBO objBO = null;
		// String date=getDateFormat(dateFormat);
		for (AttendanceFileTO to : objTO) {
			objBO = new EmpReadAttendanceFileBO();
			
			objBO.setTerminalId(to.getTerminalId());
			objBO.setFingerPrintId(to.getFingerPrintId());
			objBO.setEmployeeCode(to.getEmployeeCode());
			objBO.setEmployeeName(to.getEmployeeName());
			objBO.setLoginDate(CommonUtil.ConvertDateToSQLDate(to
					.getLoginDate(), "dd/MMM/yyyy hh:mm:ss a"));
//			objBO.setLoginTime(CommonUtil.ConvertDateToSQLTime(to
//					.getLoginDate(), "dd/MMM/yyyy hh:mm:ss a"));
			
			objBO.setFunctionkey(to.getFunctionkey());
			objBO.setStatus(to.getStatus());

			objBO.setCreatedBy(userId);
			objBO.setCreatedDate(new Date());
			objBO.setModifiedBy(userId);
			objBO.setLastModifiedDate(new Date());
			objBO.setIsActive(true);
			list.add(objBO);

		}
		return list;
	}

	public void getAttendanceList(List<EmpAttendanceTo> attendanceTOList,FormFile file,Map<String, Integer> codeIdMap,Map<String,String>fileFormat)throws Exception
	{
		    // Get the object of DataInputStream
		    InputStream in = file.getInputStream();
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    //Read File Line By Line
		    int i=0;
		    while ((strLine = br.readLine()) != null)   {
		      // Print the content on the console
		      String[] str=strLine.split(fileFormat.get("delimitedWith"));
		      if(str.length==fileFormat.size()-3)
		      {	  
		    	  if(i%2==0)
		    		  readEachRecord(str,attendanceTOList,codeIdMap,fileFormat);
		    	  i++;
		      }
		    }
		    
		    //Close the input stream
		    in.close();
		
	}
	
	public void readEachRecord(String[] line,List<EmpAttendanceTo> detailsList,Map<String, Integer> codeIdMap,Map<String,String>fileFormat) throws Exception
	{
		String terminalId="";
		String fingerPrintId="";
		String employeeCode="";
		String functionKey="";
		String employeeName="";
		String dateAndTime="";
		String status="";
		String testCode="";
		if(fileFormat.get("terminalId")!=null)
			terminalId=line[Integer.parseInt(fileFormat.get("terminalId"))-1].trim();
		if(fileFormat.get("fingerPrintId")!=null)
			fingerPrintId=line[Integer.parseInt(fileFormat.get("fingerPrintId"))-1].trim();
		if(fileFormat.get("employeeCode")!=null)
			employeeCode=line[Integer.parseInt(fileFormat.get("employeeCode"))-1].trim();
		if(fileFormat.get("functionkey")!=null)
			functionKey=line[Integer.parseInt(fileFormat.get("functionkey"))-1].trim();
		if(fileFormat.get("employeeName")!=null)
			employeeName=line[Integer.parseInt(fileFormat.get("employeeName"))-1].trim();
		if(fileFormat.get("dateTime")!=null)
			dateAndTime=line[Integer.parseInt(fileFormat.get("dateTime"))-1].trim();
		if(fileFormat.get("status")!=null)
			status=line[Integer.parseInt(fileFormat.get("status"))-1].trim();
		if(fileFormat.get("testCode")!=null)
			testCode=line[Integer.parseInt(fileFormat.get("testCode"))-1].trim();
		
		int employeeId=0;
		if(codeIdMap.get(employeeCode)!=null)
		{
			employeeId=Integer.parseInt(codeIdMap.get(employeeCode).toString());
		}
		else
		{
			IReadAttendanceFileTransaction trans=new ReadAttendanceFileTransactionImpl();
			Integer tempEmployeeId=trans.getEmployeeId(employeeCode);
			if(tempEmployeeId!=null)
			{
				employeeId=tempEmployeeId;
				codeIdMap.put(employeeCode,employeeId);
			}
		}
		String attendanceDate=dateAndTime;
		boolean employeeFound=false;
		for(EmpAttendanceTo details:detailsList)
		{
			if(details.getEmployee()==employeeId && !testCode.equalsIgnoreCase("7") && getDaysDiff(fileFormat.get("dateFormat"),details.getAttendanceDate(),attendanceDate)==0)
			{
				employeeFound=true;
				details.setOutTime(splitTime(dateAndTime));
				break;
			}
		}
		if(employeeId!=0 && !employeeFound && !testCode.equalsIgnoreCase("7")) 
		{
			EmpAttendanceTo details=ReadAttendanceFileHandler.getInstance().getEmployeeAttendanceForDate(employeeId,CommonUtil.ConvertStringToDateFormat(attendanceDate, fileFormat.get("dateFormat"), "yyyy-MM-dd"));
			if(details!=null)
			{	
				details.setOutTime(splitTime(dateAndTime));
				detailsList.add(details);
			}
			else
			{	
				EmpAttendanceTo detail=new EmpAttendanceTo();
				detail.setEmployee(employeeId);
				detail.setInTime(splitTime(dateAndTime));
				detail.setAttendanceDate(splitDate(attendanceDate));
				detailsList.add(detail);
			}	
		}
	}
	  
	public  int getDaysDiff(String dateFormat,String srcDate, String destDate) throws Exception
	{
		Date src=ConvertStringToDate(srcDate,dateFormat);
		Date dest=ConvertStringToDate(destDate,dateFormat);
		return CommonUtil.getDaysDiff(src, dest);
	}
	
	public static Date ConvertStringToDate(String dateString,String dateFormat) 
	{
		String formatDate = "";
		if (dateString != null & !dateString.isEmpty())
			formatDate = CommonUtil.ConvertStringToDateFormat(dateString,dateFormat,
					"MM/dd/yyyy");
		Date date = null;
		if (formatDate != null & !formatDate.isEmpty()) {
			date = new Date(formatDate);
		}
		return date;
	}
	  
	
	
	private String splitTime(String timeStamp)
	{
		String time="";
		StringTokenizer str=new StringTokenizer(timeStamp," ");
		str.nextToken();
		time=str.nextToken();
		return time;
	}
	
	private String splitDate(String timeStamp)
	{
		StringTokenizer str=new StringTokenizer(timeStamp," ");
		return str.nextToken();
	}

	public List<EmpAttendance> convertTotoBo(List<EmpAttendanceTo> attendanceTOList,ReadAttendanceFileForm formObj,String dateFormat) throws Exception 
	{
		List<EmpAttendance> empAttendanceList=new ArrayList<EmpAttendance>();
		EmpAttendance attendance=null;
		if(attendanceTOList.size()!=0)
		{
			for(EmpAttendanceTo attendanceTo:attendanceTOList)
			{
				attendance=new EmpAttendance();
				if(!ReadAttendanceFileHandler.getInstance().CheckDuplicate(attendanceTo.getEmployee(), CommonUtil.ConvertStringToDateFormat(attendanceTo.getAttendanceDate(), dateFormat, "yyyy-MM-dd")))
				{
					if(attendanceTo.getId()!=null)
					{
						attendance.setId(attendanceTo.getId());
					}
					if(attendanceTo.getAttendanceDate()!=null && !attendanceTo.getAttendanceDate().isEmpty())
					{
						attendance.setDate(ConvertStringToDate(attendanceTo.getAttendanceDate(),dateFormat));
					}
					if(attendanceTo.getEmployee()!=null)
					{
						Employee emp=new Employee();
						emp.setId(attendanceTo.getEmployee());
						attendance.setEmployee(emp);
					}
					if(attendanceTo.getInTime()!=null && !attendanceTo.getInTime().isEmpty())
					{
						attendance.setInTime(attendanceTo.getInTime());
					}
					if(attendanceTo.getOutTime()!=null && !attendanceTo.getOutTime().isEmpty())
					{
						attendance.setOutTime(attendanceTo.getOutTime());
					}
					attendance.setCreatedBy(formObj.getUserId());
					attendance.setCreatedDate(new Date());
					attendance.setModifiedBy(formObj.getUserId());
					attendance.setLastModifiedDate(new Date());
					attendance.setIsActive(true);
					empAttendanceList.add(attendance);
				}	
			}
		}
		return empAttendanceList;
	}

	public EmpAttendanceTo convertBotoTo(EmpAttendance empAttendance) 
	{
		EmpAttendanceTo empAttendanceTo=null;
		if(empAttendance!=null)
		{	
			empAttendanceTo=new EmpAttendanceTo();
			empAttendanceTo.setId(empAttendance.getId());
			empAttendanceTo.setAttendanceDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empAttendance.getDate()),"dd-MMM-yyyy", "dd/MMM/yyyy"));
			empAttendanceTo.setEmployee(empAttendance.getEmployee().getId());
			empAttendanceTo.setInTime(empAttendance.getInTime());
		}
		return empAttendanceTo;
	}
	
	public void copyDirectory(File sourceDir, File destDir) throws IOException
	{
		if(!destDir.exists())
		{
			destDir.mkdirs();
		}
		File[] children = sourceDir.listFiles();
		for(File sourceChild : children)
		{
			String name = sourceChild.getName();
			File destChild = new File(destDir, name);
			if(sourceChild.isDirectory())
			{
				copyDirectory(sourceChild, destChild);
			}
			else
			{
				copyFile(sourceChild, destChild);
			}
		}
	}
	
	public void copyFile(File source, File dest) throws IOException
	{
		if(!dest.exists())
		{
			dest.createNewFile();
		}
		InputStream in = null;
		OutputStream out = null;
		try
		{
			in = new FileInputStream(source);
	        out = new FileOutputStream(dest);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len = in.read(buf)) > 0)
	        {
	        	out.write(buf, 0, len);
            }
	    }
		finally
		{
			in.close();
		    out.close();
		}
	}
	
	public boolean delete(File resource) throws IOException{ 
	      if(resource.isDirectory()){
		      File[] childFiles = resource.listFiles();
		      for(File child : childFiles){
		    	  child.delete();
		      }
	      }
	      return true;
	  }
}	
	
