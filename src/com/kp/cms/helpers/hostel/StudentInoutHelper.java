package com.kp.cms.helpers.hostel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.sql.*;
import java.util.Calendar;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.transactions.hostel.IStudentInoutTransactions;
import com.kp.cms.transactionsimpl.hostel.StudentInoutTransactionImpl;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.forms.hostel.StudentInoutForm;
import com.kp.cms.to.hostel.HlCheckinCheckoutFacilityTo;
import com.kp.cms.to.hostel.HlRoomTypeFacilityTo;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.StudentInoutTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.bo.admin.HlInOut;

public class StudentInoutHelper {
	private static Log log = LogFactory.getLog(StudentInoutHelper.class);
	private static volatile StudentInoutHelper studentInoutHelper = null;
	IStudentInoutTransactions transaction = StudentInoutTransactionImpl.getInstance();
	
	private StudentInoutHelper() {
	}
	
	public static StudentInoutHelper getInstance() {
		if (studentInoutHelper == null) {
			studentInoutHelper = new StudentInoutHelper();
		}
		return studentInoutHelper;
	}
	/**
	 * building the query based on input fields
	 * @param StudentInoutForm 
	 * @return
	 */
	public String getSearchCriteria(StudentInoutForm studentInoutForm) throws Exception{
		
		log.info("Entering getSelectionSearchCriteria of StudentInoutHelper");
		String statusCriteria = commonSearch(studentInoutForm);
		String searchCriteria= "";
		searchCriteria = statusCriteria +" and hlapp.isActive =1 and hlapp.hlHostelByHlApprovedHostelId.id="+studentInoutForm.getHostelId();
		log.info("Exiting getSelectionSearchCriteria of HostelCheckoutHelper");
		return searchCriteria;
	}
	/**
	 * common query for all type of inputs
	 * @param HostelCheckinForm
	 * @return
	 */
	private String commonSearch(StudentInoutForm studentInoutForm) throws Exception{
		
		log.info("entered commonSearch of StudentInoutHelper ");
		   Properties prop = new Properties();
		 InputStream in = HostelCheckoutHelper.class.getClassLoader().getResourceAsStream("resources/application.properties");
	        prop.load(in);
	        String roomStatusId = prop.getProperty("knowledgepro.hostel.visitor.room.CheckInstatus");
	        String searchCriteria = "select hlapp.id," +
					        		"hlapp.admAppln.id," +
					        		"hlapp.employee.id," +
					        		"hlapp.admAppln.personalData.id," +
	        						"hlapp.admAppln.personalData.firstName," +
	        						"hlapp.admAppln.personalData.middleName," +
	        						"hlapp.admAppln.personalData.lastName," +
	        						"hlapp.hlHostelByHlApprovedHostelId.name" +
	        						" from HlApplicationForm hlapp";
	        if(studentInoutForm.getStudId() != null && studentInoutForm.getStudId().length()>0){
	        	String qryForId = " inner join hlapp.admAppln.students student" +
	        			" where hlapp.admAppln.id=student.admAppln.id" +
	        			" and hlapp.hlStatus.id="+roomStatusId+" and (student.rollNo='"+studentInoutForm.getStudId()+"' or student.registerNo='"+studentInoutForm.getStudId()+"')";
	        	searchCriteria = searchCriteria +qryForId;
	        }
	        if(studentInoutForm.getStudName() !=null && studentInoutForm.getStudName().length()>0){
	        	String qryForName = " where  hlapp.admAppln.personalData.firstName like '"+studentInoutForm.getStudName()+"%' and hlapp.hlStatus.id="+roomStatusId;
	        						
	        	searchCriteria = searchCriteria +qryForName;
	        }
	        return searchCriteria;
	}
	
	/**
	 * converting the list of Bo's to TO's
	 * @param applicantHostelDetails
	 * @return  StudentInoutTo studentInoutTo
	 * @throws ExceptionStudentInoutForm studentInoutForm
	 */
	public List<StudentInoutTo> convertBOtoTO(List<Object> studentInoutDetails,StudentInoutForm studentInoutForm) throws Exception{
		 log.info("inside convertBOtoTO of StudentInoutHelper");
		 StudentInoutTo studentInoutTo = null;
		 List<StudentInoutTo> studentInoutToList = new ArrayList<StudentInoutTo>();
		Iterator<Object> applicantIt = studentInoutDetails.iterator();		
		while(applicantIt.hasNext())
		{
			Object[] object =(Object[]) applicantIt.next();
			studentInoutTo = new StudentInoutTo();
			if(object[0]!=null && !object[0].toString().isEmpty())
			{
				studentInoutTo.setAppFormId(Integer.parseInt(object[0].toString()));
			}
			if(object[1]!= null && !object[1].toString().isEmpty()){
				studentInoutTo.setAdmId(Integer.parseInt(object[1].toString()));
			}
			if(object[2]!= null && !object[2].toString().isEmpty()){
				studentInoutTo.setAdmId(Integer.parseInt(object[2].toString()));
			}
			if(object[3]!= null && !object[3].toString().isEmpty()){
				studentInoutTo.setPersonId(Integer.parseInt(object[3].toString()));
			}
			if(object[4]!= null && !object[4].toString().isEmpty()){
				studentInoutTo.setFirstName(object[4].toString());
			}
			if(object[5]!= null && !object[5].toString().isEmpty()){
				studentInoutTo.setMiddleName(object[5].toString());
			}
			if(object[6]!= null && !object[6].toString().isEmpty()){
				studentInoutTo.setLastName(object[6].toString());
			}
			if(object[7]!=null && !object[7].toString().isEmpty())
			{
				studentInoutTo.setHostelName(object[7].toString());
			}
			studentInoutToList.add(studentInoutTo);
		}
		log.info("Exiting convertBOtoTO of StudentInoutHelper");
		return studentInoutToList;
	}
	
	/**
	  * converting string to sqldate
	 * @param StudentInoutForm
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */	 
	public static java.sql.Date ConvertStringToSQLDateTime(String dateString) {
		String formatDate="";
		if(dateString!=null & !dateString.isEmpty())
			formatDate=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy hh:mm:ss", "MM/dd/yyyy hh:mm:ss");
		java.sql.Date sqldate = null;
		if(formatDate!=null & !formatDate.isEmpty()){
		Date date = new Date(formatDate);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		sqldate = new java.sql.Date(cal.getTimeInMillis());
		}
		return sqldate;
	}

	
	

	 /**
	  * saving data to database
	 * @param StudentInoutForm
	 * @param hostelApplicantDetails
	 * @throws Exception
	 */	 

public HlInOut submitStudentDetails(StudentInoutForm studentInoutForm,StudentInoutTo studentInoutTo) throws Exception {
	 log.info("Entering submitStudentDetails of StudentInoutHelper");
	
	 String txnStatus = "failed";
	 String status="failed";
	 HlStatus  hlStatus = null;		
	 HlInOut hlinout = new HlInOut();
	 AdmAppln admAppl = null;			 
	 Employee employee =null;
	 HlApplicationForm hlApplicationForm= null;	 
	
		if(studentInoutTo!=null){
			/*if(hostelCheckinTo.getId()!=null && !hostelCheckinTo.getId().isEmpty()){
				hlRoomTransaction.setId(Integer.valueOf(hostelCheckinTo.getId()));
			}*/			
			if(studentInoutTo.getAdmId()!=0){
				admAppl = new AdmAppln();
				admAppl.setId(studentInoutTo.getAdmId());					
			}
			
			if(studentInoutTo.getAppFormId()!=0){
				hlApplicationForm = new HlApplicationForm();
				hlApplicationForm.setId(studentInoutTo.getAppFormId());
				//hlApplicationForm.setFingerPrintId(hostelCheckoutform.getFingerPrintId().toString());
			}
			if(studentInoutTo.getEmpid()!=0){ 
				employee = new Employee();
				employee.setId(studentInoutTo.getEmpid()); 
			}
			/*if(studentInoutForm.getFinalTimein()!=null){
				hlinout.setInTime(CommonUtil.ConvertStringToSQLDate(studentInoutForm.getFinalTimein()));
			}
			if(studentInoutForm.getFinalTimeout()!=null){
				hlinout.setOutTime(CommonUtil.ConvertStringToSQLDate(studentInoutForm.getFinalTimeout()));
			}*/
			if(studentInoutTo.getInTime()!=null && !studentInoutTo.getInTime().isEmpty() && 
					studentInoutTo.getInTimeone()!=null && !studentInoutTo.getInTimeone().isEmpty() &&
					studentInoutTo.getInTimetwo()!=null && !studentInoutTo.getInTimetwo().isEmpty()){
					String finalTimein = studentInoutTo.getInTime()+" "+studentInoutTo.getInTimeone()+":"+studentInoutTo.getInTimetwo()+":"+"00";
					hlinout.setInTime(ConvertStringToSQLDateTime(finalTimein));
					finalTimein="";
			}
			if(studentInoutTo.getOutTime()!=null && !studentInoutTo.getOutTime().isEmpty() && 
					studentInoutTo.getOutTimeone()!=null && !studentInoutTo.getOutTimeone().isEmpty() &&
					studentInoutTo.getOutTimetwo()!=null && !studentInoutTo.getOutTimetwo().isEmpty()){
					String finalTimeout = studentInoutTo.getOutTime()+" "+studentInoutTo.getOutTimeone()+":"+studentInoutTo.getOutTimetwo()+":"+"00";
					hlinout.setOutTime(ConvertStringToSQLDateTime(finalTimeout));
					finalTimeout="";
			}
			hlinout.setCreatedBy(studentInoutForm.getUserId());
			hlinout.setCreatedDate(new Date()); 
			hlinout.setModifiedBy(studentInoutForm.getUserId());
			hlinout.setLastModifiedDate(new Date());
			hlinout.setIsActive(true);
			hlinout.setEmployee(employee);
			hlinout.setHlApplicationForm(hlApplicationForm);
		}
		return hlinout;
}	
}
