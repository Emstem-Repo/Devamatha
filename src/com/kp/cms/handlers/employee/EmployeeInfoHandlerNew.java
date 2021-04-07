package com.kp.cms.handlers.employee;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.actions.employee.EmployeeInfoActionNew;
import com.kp.cms.actions.employee.EmployeeUploadPhotoAction;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.EmployeeUploadPhoto;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.forms.employee.EmployeeUploadPhotoForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.helpers.employee.EmpEventVacationHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.helpers.employee.EmployeeInfoHelperNew;
import com.kp.cms.helpers.employee.EmployeeUploadPhotoHelper;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.to.employee.EmployeeInfoTONew;
import com.kp.cms.to.employee.EmployeeUploadPhotoTO;
import com.kp.cms.transactions.admission.IStudentEditTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoNew;
import com.kp.cms.transactions.employee.IEmployeeInfoNewTransaction;
import com.kp.cms.transactionsimpl.admission.StudentEditTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmpEventVacationImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoNewTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeUploadPhotoTxnImpl;
import com.kp.cms.transactionsimpl.employee.IEmployeeUploadPhotoTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.RepeatMidSemReminderSmsMail;
import com.kp.cms.utilities.jms.MailTO;

public class EmployeeInfoHandlerNew {
	
	private static final Log log = LogFactory.getLog(EmpResumeSubmissionHandler.class);
	IEmployeeInfoNewTransaction empTransaction=EmployeeInfoNewTransactionImpl.getInstance();
	
	private static volatile EmployeeInfoHandlerNew instance=null;
	
	/**
	 * 
	 */
	private EmployeeInfoHandlerNew(){
		
	}
	
	/**
	 * @return
	 */
	public static EmployeeInfoHandlerNew getInstance(){
		log.info("Start getInstance of EmployeeInfoHandlerNew");
		if(instance==null){
			instance=new EmployeeInfoHandlerNew();
		}
		log.info("End getInstance of EmployeeInfoHandlerNew");
		return instance;
	}
		
	public List<EmpTypeTo> getWorkTimeEntry(String empTypeId) throws Exception
	{		
		String dataQuery=EmployeeInfoHelperNew.getInstance().getQueryByselectedEmpTypeId(empTypeId);
		IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
		EmpType worktimeEntry=transaction.getWorkTimeListQueryByEmpTyptId(dataQuery);
		List<EmpTypeTo> toList=new ArrayList<EmpTypeTo>();
		if(worktimeEntry!=null ){
			

				EmpTypeTo to=new EmpTypeTo();
				to.setTimeIn(worktimeEntry.getTimeIn());
				to.setTimeInEnds(worktimeEntry.getTimeInEnds());
				to.setHalfDatyEndTime(worktimeEntry.getHalfDatyEndTime());
				to.setHalfDayStartTime(worktimeEntry.getHalfDayStartTime());
				to.setSaturdayTimeOut(worktimeEntry.getSaturdayTimeOut());
				to.setTimeOut(worktimeEntry.getTimeOut());
				toList.add(to);

		}
			return toList;
	
	}
	
	
	public String getPayscale(String payscaleId) throws Exception
	{		
		//String dataQuery=EmployeeInfoHelperNew.getInstance().getQueryByselectedPayscaleId(payscaleId);
		IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
		String scale=transaction.getScaleQueryByPayscaleId(payscaleId);
		return scale;
	}
			
		public Map<Integer, String> getPayscaleTeachingStaff(String teachingStaff) throws Exception {
			IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
			List<Object[]> list = transaction.getPayscaleTeachingStaff(teachingStaff);
			Map<Integer, String> payScaleMap = EmployeeInfoHelperNew.getInstance().convertBoToForm(list);
			return payScaleMap;
		}
	
	
	/*public String getEmpAgeRetirement() throws Exception
	{		
		//String dataQuery=EmployeeInfoHelperNew.getInstance().getQueryByselectedPayscaleId(payscaleId);
		IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
		String Age=transaction.getEmpAgeRetirement();
		return Age;
	}*/
		
	
	public List<EmpLeaveAllotTO> getEmpLeaveList(String empTypeId) throws Exception
	{	
		IEmployeeInfoNewTransaction transaction = EmployeeInfoNewTransactionImpl.getInstance();
		
		String dataQuery=EmployeeInfoHelperNew.getInstance().getLeaveByEmpTypeId(empTypeId);
	//	List<EmpLeaveAllotment> Leave=transaction.getEmpLeaveListQueryByEmpTyptId(dataQuery);
		
		List<EmpLeaveAllotment> Leave=transaction.getEmpLeaveListQueryByEmpTyptId(dataQuery);
		List<EmpLeaveAllotTO> toList=new ArrayList<EmpLeaveAllotTO>();
		if(Leave!=null && !Leave.isEmpty()){
			
			Iterator itr=Leave.iterator();
			while (itr.hasNext()) {
				EmpLeaveAllotment obj = (EmpLeaveAllotment) itr.next();
				EmpLeaveAllotTO to=new EmpLeaveAllotTO();
				
				EmpLeaveType empLeaveType=new EmpLeaveType();
				empLeaveType.setId(obj.getEmpLeaveType().getId());
				to.setEmpLeaveType(empLeaveType);
				//to.setEmpLeaveType(obj.getEmpLeaveType().getId());
				
				to.setLeaveType(obj.getEmpLeaveType().getName());
				to.setAllottedLeave(String.valueOf("0"));
				to.setSanctionedLeave(String.valueOf("0"));
				to.setRemainingLeave(String.valueOf("0"));
					toList.add(to);
			}
		}
			return toList;
	
	}
		
	public void getInitialData(EmployeeInfoFormNew employeeInfoFormNew)throws Exception {
		log.info("Start getInitialData of EmployeeInfoHandlerNew");
		//String empId=empTransaction.getEmpId(employeeInfoFormNew.getEmployeeId());
		Integer Age =empTransaction.getEmpAgeRetirement();
		
		 Map<String,String> lakhsAndThousands=new LinkedHashMap<String, String>();
		 employeeInfoFormNew.setEmpRetirementAge(String.valueOf(Age));
		 Map<String,String> designationMap=empTransaction.getDesignationMap();
		 if(designationMap!=null){
			 employeeInfoFormNew.setDesignationMap(designationMap);
			
		 }
		 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
		 if(empTypeMap!=null){
			 employeeInfoFormNew.setEmpTypeMap(empTypeMap);
		 }
		 Map<String,String> payScaleMap=empTransaction.getPayScaleMap();
		 if(payScaleMap!=null){
			 employeeInfoFormNew.setPayScaleMap(payScaleMap);
		 }
		 Map<String,String> ReportToMap=empTransaction.getReportToMap();
		 if(ReportToMap!=null){
			 employeeInfoFormNew.setReportToMap(ReportToMap);
		 }
		 Map<String,String> streamMap=empTransaction.getStreamMap();
		 if(streamMap!=null){
			 employeeInfoFormNew.setStreamMap(streamMap);
		 }
		 Map<String,String> titleMap=empTransaction.getTitleMap();
		 if(titleMap!=null){
			 employeeInfoFormNew.setTitleMap(titleMap);
		 }
		 Map<String,String> workLocationMap=empTransaction.getWorkLocationMap();
		 if(workLocationMap!=null){
			 employeeInfoFormNew.setWorkLocationMap(workLocationMap);
		 }
		 Map<String,String> religionMap=empTransaction.getReligionMap();
		 if(religionMap!=null){
			 employeeInfoFormNew.setReligionMap(religionMap);
		}
		 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
		  if(departmentMap!=null){
			 employeeInfoFormNew.setDepartmentMap(departmentMap);
			 employeeInfoFormNew.setDeputaionToDepartmentMap(departmentMap);
		 }
		 
		 Map<String,String> countryMap=empTransaction.getCountryMap();
		 if(countryMap!=null){
			 employeeInfoFormNew.setCountryMap(countryMap);
			 employeeInfoFormNew.setCurrentCountryMap(countryMap);
			 employeeInfoFormNew.setPassportCountryMap(countryMap);
			 employeeInfoFormNew.setVisaCountryMap(countryMap);
		 }
		 Map<String,String> nationalityMap=empTransaction.getNationalityMap();
		 if(nationalityMap!=null)
			 employeeInfoFormNew.setNationalityMap(nationalityMap);
		 Map<String,String> qualificationLevelMap=empTransaction.getQualificationLevelMap();
		 if(qualificationLevelMap!=null){
			 employeeInfoFormNew.setQualificationLevelMap(qualificationLevelMap);
		 }
		 Map<String,String> qualificationMap=empTransaction.getQualificationMap();
		 if(qualificationMap!=null){
			 employeeInfoFormNew.setQualificationMap(qualificationMap);
		 }
		 List<EmpQualificationLevelTo> qualificationFixedTo=empTransaction.getQualificationFixedMap();
		 if(qualificationFixedTo!=null){
			 employeeInfoFormNew.getEmployeeInfoTONew().setEmpQualificationFixedTo(qualificationFixedTo);
		 }
		 List<EmpAllowanceTO> payscaleFixedTo=empTransaction.getPayAllowanceFixedMap();
		 if(payscaleFixedTo!=null){
			 employeeInfoFormNew.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
		 }
		 employeeInfoFormNew.setActive("1");
		 employeeInfoFormNew.setNoOfPublicationsNotRefered("0");
		 employeeInfoFormNew.setNoOfPublicationsRefered("0");
		 employeeInfoFormNew.setBooks("0");
		/* empLeaveTo=empTransaction.getEmpLeaveList(empTypeId);
		 if(empLeaveTo!=null){
			 employeeInfoFormNew.getEmployeeInfoTONew().setEmpLeaveTo(empLeaveTo);
		 }*/
		 Map<String,String> subjectAreaMap=empTransaction.getSubjectAreaMap();
		 if(subjectAreaMap!=null){
			 employeeInfoFormNew.setSubjectAreaMap(subjectAreaMap);
		 }
		 
		 Map<String,String> jobTypeMap=empTransaction.getJobType();
		 if(jobTypeMap!=null){
			 employeeInfoFormNew.setJobTypeMap(jobTypeMap);
		 }
		 for(int i=0;i<100;i++){
			 lakhsAndThousands.put(String.valueOf(i),String.valueOf(i));
		 }
		 if(lakhsAndThousands!=null){
			 employeeInfoFormNew.setLakhsAndThousands(lakhsAndThousands);
		 }
		
		log.info("End getInitialData of EmpResumeSubmissionHandler");
	}

	/**
	 * @param employeeInfoFormNew
	 * @return
	 * @throws Exception 
	 */
	public boolean saveEmp(EmployeeInfoFormNew employeeInfoFormNew) throws Exception
	{
		boolean flag=false;
		
		
		Employee employee=EmployeeInfoHelperNew.getInstance().convertFormToBo(employeeInfoFormNew);
		flag=empTransaction.saveEmployee(employee,employeeInfoFormNew);
		if(flag){
			List<GroupTemplate> list= TemplateHandler.getInstance().getDuplicateCheckList(0,"Employee Add Notification");
			if(list != null && !list.isEmpty()) {
				Properties prop = new Properties();
				InputStream in = RepeatMidSemReminderSmsMail.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				InputStream sin = AttendanceEntryHelper.class.getClassLoader().getResourceAsStream(CMSConstants.SMS_FILE_CFG);
		        prop.load(in);
		        prop.load(sin);
		        
				MailTO mailto = new MailTO();
				String desc = list.get(0).getTemplateDescription();
				if(desc != null){
					desc=desc.replace("[TYPE]", "Employee ");
					desc=desc.replace("[NAME]", employeeInfoFormNew.getName());
					if(employeeInfoFormNew.getDepartmentId() != null && !employeeInfoFormNew.getDepartmentId().isEmpty())
						desc=desc.replace("[DEPARTMENT]", employeeInfoFormNew.getDepartmentMap().get(employeeInfoFormNew.getDepartmentId()));
					mailto.setFromAddress(CMSConstants.MAIL_USERID);
					mailto.setFromName(prop.getProperty("knowledgepro.employee.add.notification.fromName"));
					mailto.setMessage(desc);
					mailto.setSubject("Employee Add Notification Mail");
					mailto.setToAddress(prop.getProperty("knowledgepro.employee.add.notification.sendmail.id"));
					CommonUtil.sendMail(mailto);
				}
			}
		}
		return flag;
	}

	
	public void getInitialDataForEdit(EmployeeInfoEditForm employeeInfoFormNew)throws Exception {
		log.info("Start getInitialData of EmployeeInfoHandlerNew");
		//String empId=empTransaction.getEmpId(employeeInfoFormNew.getEmployeeId());
		
		 Map<String,String> lakhsAndThousands=new LinkedHashMap<String, String>();
		 Map<String,String> designationMap=empTransaction.getDesignationMap();
		 if(designationMap!=null){
			 employeeInfoFormNew.setDesignationMap(designationMap);
		}
		 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
		 if(empTypeMap!=null){
			 employeeInfoFormNew.setEmpTypeMap(empTypeMap);
		 }
		 Map<String,String> payScaleMap=empTransaction.getPayScaleMap();
		 if(payScaleMap!=null){
			 employeeInfoFormNew.setPayScaleMap(payScaleMap);
		 }
		 Map<String,String> ReportToMap=empTransaction.getReportToMap();
		 if(ReportToMap!=null){
			 employeeInfoFormNew.setReportToMap(ReportToMap);
		 }
		 Map<String,String> streamMap=empTransaction.getStreamMap();
		 if(streamMap!=null){
			 employeeInfoFormNew.setStreamMap(streamMap);
		 }
		 Map<String,String> workLocationMap=empTransaction.getWorkLocationMap();
		 if(workLocationMap!=null){
			 employeeInfoFormNew.setWorkLocationMap(workLocationMap);
		 }
		 Map<String,String> religionMap=empTransaction.getReligionMap();
		 if(religionMap!=null){
			 employeeInfoFormNew.setReligionMap(religionMap);
		}
		 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
		 if(departmentMap!=null)
			 employeeInfoFormNew.setDepartmentMap(departmentMap);
		 Map<String,String> countryMap=empTransaction.getCountryMap();
		 if(countryMap!=null){
			 employeeInfoFormNew.setCountryMap(countryMap);
			 employeeInfoFormNew.setCurrentCountryMap(countryMap);
			 employeeInfoFormNew.setPassportCountryMap(countryMap);
			 employeeInfoFormNew.setVisaCountryMap(countryMap);
		 }
		 Map<String,String> nationalityMap=empTransaction.getNationalityMap();
		 if(nationalityMap!=null)
			 employeeInfoFormNew.setNationalityMap(nationalityMap);
		 Map<String,String> qualificationLevelMap=empTransaction.getQualificationLevelMap();
		 if(qualificationLevelMap!=null){
			 employeeInfoFormNew.setQualificationLevelMap(qualificationLevelMap);
		 }
		 Map<String,String> qualificationMap=empTransaction.getQualificationMap();
		 if(qualificationMap!=null){
			 employeeInfoFormNew.setQualificationMap(qualificationMap);
		 }
		 List<EmpQualificationLevelTo> qualificationFixedTo=empTransaction.getQualificationFixedMap();
		 if(qualificationFixedTo!=null){
			 employeeInfoFormNew.getEmployeeInfoTONew().setEmpQualificationFixedTo(qualificationFixedTo);
		 }
		/* empLeaveTo=empTransaction.getEmpLeaveList(empTypeId);
		 if(empLeaveTo!=null){
			 employeeInfoFormNew.getEmployeeInfoTONew().setEmpLeaveTo(empLeaveTo);
		 }*/
		 Map<String,String> subjectAreaMap=empTransaction.getSubjectAreaMap();
		 if(subjectAreaMap!=null){
			 employeeInfoFormNew.setSubjectAreaMap(subjectAreaMap);
		 }
		 
		 Map<String,String> jobTypeMap=empTransaction.getJobType();
		 if(jobTypeMap!=null){
			 employeeInfoFormNew.setJobTypeMap(jobTypeMap);
		 }
		 for(int i=0;i<100;i++){
			 lakhsAndThousands.put(String.valueOf(i),String.valueOf(i));
		 }
		 if(lakhsAndThousands!=null){
			 employeeInfoFormNew.setLakhsAndThousands(lakhsAndThousands);
		 }
		
		log.info("End getInitialData of EmpResumeSubmissionHandler");
	}


	
	
	
	
	
	
	public boolean SaveEditEmpDetails(EmployeeInfoFormNew employeeInfoFormNew) throws Exception
	{
		boolean flag=false;
		
		
		//Employee employee=EmployeeInfoHelperNew.getInstance().convertFormToBoEmpEdit(employeeInfoFormNew);
	//	flag=empTransaction.SaveEditEmpDetails(employee);
		
		return flag;
	}

	public boolean getApplicantDetails(EmployeeInfoFormNew objform) {
		
		IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
		boolean flag=false;
		
		try {
			EmpOnlineResume empApplicantDetails=txn.GetEmpDetails(objform);
			
			if (empApplicantDetails != null) {
				flag=true;
				EmployeeInfoHelperNew.getInstance().convertBoToForm(empApplicantDetails,objform);
				
				objform.setEmployeeId(objform.getEmployeeId());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return flag;
	
	}
	
	public boolean checkFingerPrintIdUnique(String fingerPrintId)
	throws Exception {
		boolean unique = false;
		try{
		log.info("Enter checkFingerPrintIdUnique ...");
		
		IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
		unique = txn.checkFingerPrintIdUnique(fingerPrintId);
			log.info("Exit checkFingerPrintIdUnique ...");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			return unique;
	}
	public boolean checkCodeUnique(String code)
	throws Exception {
		log.info("Enter checkCodeUnique ...");
		boolean unique = false;
		try
		{
		IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
		unique = txn.checkCodeUnique(code);
			log.info("Exit checkCodeUnique ...");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			return unique;
	}
	public boolean checkUidUnique(String Uid)throws Exception {
		log.info("Enter checkUidUnique ...");
		boolean unique = false;
		try
		{
		IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
		unique = txn.checkUidUnique(Uid);
			log.info("Exit checkUidUnique ...");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			return unique;
	}
	
	
	
	
	
	
	
	public boolean uploadPhotos(EmployeeInfoFormNew employeeUploadPhotoForm,HttpServletRequest request) throws Exception {
		boolean flag=false;
		List<EmployeeUploadPhotoTO> uploadPhotoTOs= new ArrayList<EmployeeUploadPhotoTO>();
		String relativePath;
		
		String s1=request.getRealPath("");
		Properties prop = new Properties();
		InputStream in = EmployeeInfoActionNew.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		 prop.load(in);
	        String path=prop.getProperty("knowledgepro.employee.employeephotosource");
	        relativePath=s1+path;
            File f = new File(relativePath);
	        
	        File[] filearr =  f.listFiles();
	       // log.error("relativePath********" + relativePath );
	       // log.error("filearr.length   " + filearr);
	        if(filearr.length!=0){
	        	for(int j=0;j<filearr.length;j++){
	        		File f3=filearr[j];
	        		//employeeUploadPhotoForm= new EmployeeUploadPhotoTO();
	       			byte[] b = new byte[(int)f3.length ()];;
	       			FileInputStream fis=new FileInputStream(f3);
	       			// convert image into byte array
	       			fis.read(b);
	       
	       			
	       			employeeUploadPhotoForm.setPhoto(b);
	       		 		
	        	
	        	 log.error(uploadPhotoTOs + "uploadPhotoTOs" + "size" + uploadPhotoTOs.size());
	        	// int uploadPhotos=uploadEmployeePhotos(uploadPhotoTOs);
	        	// log.error(uploadPhotos + "uploadPhotos");
	             
	      /*if (uploadPhotos==uploadPhotoTOs.size()) {
	      			// success .
	          	   File f1 = new File(relativePath);
	      	       File[] filearr1 =  f1.listFiles();
	      	       for(int j=0;j<filearr1.length;j++){
	      	   		File f3=filearr[j];
	      	   		if(f3.delete()){
	      	   			int i=0;
	      	   		}		   		
	      	       }
	      	       flag=true;
	      	       }*/
	             }
	        }
	              else{
	              	throw new NullPointerException();
	              }
	        	return flag;
	}
	/**
	 * @param uploadPhotoTOs
	 * to insert images into the database
	 * @return
	 */
	/*private int uploadEmployeePhotos(List<EmployeeUploadPhotoTO> uploadPhotoTOs) throws Exception{
		List<EmployeeUploadPhoto> employeeUploadPhotoList=EmployeeUploadPhotoHelper.getInstance().copyTOToBO(uploadPhotoTOs);
		log.error("employeeUploadPhotoList" + employeeUploadPhotoList.size());
		IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
		return txn.uploadEmployeePhotos(employeeUploadPhotoList);
	}*/
	/**
	 * @param name
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return null;
	}
	}

	
	
	
	
	
	

