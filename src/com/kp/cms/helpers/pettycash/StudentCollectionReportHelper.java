package com.kp.cms.helpers.pettycash;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.attendance.AttendanceTeacherReportForm;
import com.kp.cms.forms.pettycash.StudentCollectionReportForm;
import com.kp.cms.handlers.pettycash.StudentCollectionReportHandler;
import com.kp.cms.to.pettycash.CashCollectionTO;
import com.kp.cms.to.pettycash.StudentCollectionReportTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentCollectionReportHelper {
	

	private static final Log log = LogFactory.getLog(StudentCollectionReportHelper.class);
	
	private static volatile StudentCollectionReportHelper studentCollectionReportHelper;
	
	
	public static StudentCollectionReportHelper getinstance()
	{
		if(studentCollectionReportHelper==null)
		{
			studentCollectionReportHelper = new StudentCollectionReportHelper();
			return studentCollectionReportHelper;
		}
		return studentCollectionReportHelper;
		
	}
	
	
	public String getStudentId(StudentCollectionReportForm studentCollectionreportForm)
	{
		String id=null;
		
		return id;
	}
	public String getSearchCriteria(StudentCollectionReportForm studentForm) {
		log.info("entered into  getSearchCriteria..");
		String searchCriteria = "select pcReceipts.paidDate,pcReceipts.pcAccountHead.accName,pcReceipts.pcAccountHead.accCode,pcReceipts.number,pcReceipts.amount,pcReceipts.name,pcReceipts.refNo from PcReceipts pcReceipts";
		if(studentForm.getEndDate()!=null&& studentForm.getStartDate()!=null){
		searchCriteria=searchCriteria+" where pcReceipts.paidDate >= '"+CommonUtil.ConvertStringToSQLDate(studentForm.getStartDate())+"'" +
				"and pcReceipts.paidDate <= '"+CommonUtil.ConvertStringToSQLDate(studentForm.getEndDate())+" 23:59:59.0'";
		}
		String appRegRollNo = studentForm.getAppNo();
		if(appRegRollNo.equals("1") ){
			searchCriteria=searchCriteria+" and pcReceipts.student.admAppln.applnNo="+studentForm.getAppRegRollNo();
		}else if(appRegRollNo.equals("2") ){
			searchCriteria=searchCriteria+" and pcReceipts.student.rollNo='"+studentForm.getAppRegRollNo()+"'";
		}else if(appRegRollNo.equals("3")){
			searchCriteria=searchCriteria+" and pcReceipts.student.registerNo='"+studentForm.getAppRegRollNo()+"'";
		}
		searchCriteria=searchCriteria+" and pcReceipts.student.isActive=1";
		log.info("exit commonSearch..");
		return searchCriteria;
	}
	
	public StudentCollectionReportTO getCorrectStudentDetails(StudentCollectionReportForm studentForm,List<StudentCollectionReportTO> studentTo)
	{
		
		
		log.info("entered into  getDetails..");
		String searchCriteria = "";
		
		String appNo = studentForm.getAppNo();
		String regNo = studentForm.getRegNo();
		String  rollNo = studentForm.getRollNo();
		String academicyear = studentForm.getAcademicYear();
		StudentCollectionReportTO studentCollectionreportTo = null;
		if(appNo!=null || regNo!=null || rollNo!=null ){
		
			if(appNo!=null &&((regNo==null)||(rollNo==null)))
			{
				 Iterator<StudentCollectionReportTO> toit = studentTo.iterator();
				 while(toit.hasNext())
				 {
					 studentCollectionreportTo = toit.next();
					 if((studentCollectionreportTo.getAppNo().equals(appNo))&& (studentCollectionreportTo.getAcademicYear().equals(academicyear)))
					 {
						return  studentCollectionreportTo;
						
					 }
					
				 }
			}
			else if(regNo!=null &&((appNo==null)||(rollNo==null)))
			{
				 Iterator<StudentCollectionReportTO> toit = studentTo.iterator();
				 while(toit.hasNext())
				 {
					 studentCollectionreportTo = toit.next();
					 if((studentCollectionreportTo.getRegNo().equals(regNo)))
					 {
						return  studentCollectionreportTo;
						
					 }
					
				 }
			}
			
			else 
			{
				 Iterator<StudentCollectionReportTO> toit = studentTo.iterator();
				 while(toit.hasNext())
				 {
					 studentCollectionreportTo = toit.next();
					 if((studentCollectionreportTo.getRollNo().equals(rollNo)))
					 {
						return  studentCollectionreportTo;
						
					 }
					
				 }
			}
		}
		return null;
	}
		
		
		public List<StudentCollectionReportTO> convertBotoTo(List studentBo)
		{
			
			
			log.info("entered into  getDetails..");
			List<StudentCollectionReportTO> studentToList = new ArrayList<StudentCollectionReportTO>();
			Iterator boit= studentBo.iterator();
			while(boit.hasNext())
			{
				Object[] object =(Object[]) boit.next();
				StudentCollectionReportTO studentTo = new StudentCollectionReportTO();
				if (object[0]!=null) {
					studentTo.setDate(object[0].toString().substring(0,10));
					studentTo.setTime(getTimeinHoursndMinutes(object[0].toString()));
				}
				if (object[1]!=null) {
					studentTo.setAccName(object[1].toString());
				}
				if (object[2]!=null) {
					studentTo.setAccCode(object[2].toString());
				}
				if (object[3]!=null) {
					studentTo.setRecNumber(object[3].toString());
				}
				if (object[4]!=null) {
					studentTo.setAmount(object[4].toString());
				}
				if (object[5]!=null) {
					studentTo.setStudentName(object[5].toString());
				}
				if (object[6]!=null) {
					studentTo.setRefNo(object[5].toString());
				}
				//set all values to to
				studentToList.add(studentTo);
			}
			
			return studentToList;
		}
		
		public String getTimeinHoursndMinutes(String time)
		{
			//Calendar calendar = new GregorianCalendar();
			String actualTime = time.substring(10,16);
			return actualTime;
		}

}
