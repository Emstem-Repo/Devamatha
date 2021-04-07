package com.kp.cms.handlers.employee;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.employee.EmployeeInfoViewForm;
import com.kp.cms.helpers.employee.EmployeeApplyLeaveHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.helpers.employee.EmployeeViewHelper;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.transactions.employee.IEmployeeViewTransaction;
import com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeApplyLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeViewTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeViewHandler {
		
		private static final Log log = LogFactory.getLog(EmployeeViewHandler.class);
		IEmployeeViewTransaction empTransaction=EmployeeViewTransactionImpl.getInstance();
		
		private static volatile EmployeeViewHandler instance=null;
		
		/**
		 * 
		 */
		private EmployeeViewHandler(){
			
		}
		
		/**
		 * @return
		 */
		public static EmployeeViewHandler getInstance(){
			log.info("Start getInstance of EmployeeViewHandler");
			if(instance==null){
				instance=new EmployeeViewHandler();
			}
			log.info("End getInstance of EmployeeViewHandler");
			return instance;
		}
		
	public List<EmployeeTO> getSearchedEmployee(EmployeeInfoViewForm stForm)throws Exception {
	log.info("enter getSearchedStudents");
	IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
	EmployeeViewHelper helper= EmployeeViewHelper.getInstance();
	
	int designationId = 0;
	int departmentId = 0;
	int streamId = 0;
	int empTypeId=0;
		
	if (stForm.getTempDepartmentId() != null
			&& !StringUtils.isEmpty(stForm.getTempDepartmentId().trim())
			&& StringUtils.isNumeric(stForm.getTempDepartmentId())) {
		departmentId = Integer.parseInt(stForm.getTempDepartmentId());
	}
	if (stForm.getTempDesignationPfId() != null
			&& !StringUtils.isEmpty(stForm.getTempDesignationPfId().trim())
			&& StringUtils.isNumeric(stForm.getTempDesignationPfId())) {
		designationId = Integer.parseInt(stForm.getTempDesignationPfId());
	}
	if (stForm.getTempStreamId() != null
			&& !StringUtils.isEmpty(stForm.getTempStreamId().trim())
			&& StringUtils.isNumeric(stForm.getTempStreamId())) {
		streamId = Integer.parseInt(stForm.getTempStreamId());
	}
	if (stForm.getTempEmptypeId() != null
			&& !StringUtils.isEmpty(stForm.getTempEmptypeId().trim())
			&& StringUtils.isNumeric(stForm.getTempEmptypeId())) {
		empTypeId = Integer.parseInt(stForm.getTempEmptypeId());
	}
	
	StringBuffer query = txn.getSerchedEmployeeQuery(stForm.getTempCode(), stForm.getTempUid(), stForm.getTempFingerPrintId(),
			departmentId , designationId, stForm.getTempName(), stForm.getTempActive(), streamId, stForm.getTempTeachingStaff(), empTypeId);
	List<Employee> employeelist=txn.getSerchedEmployee(query);
	
/*	List<Integer> studentPhotoList = txn.getSerchedEmployeePhotoList(stForm.getCode(), stForm.getuId(), stForm.getFingerPrintId(),
			departmentId , designationId, stForm.getName());*/
	
	List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist, departmentId , designationId);
	log.info("exit getSearchedStudents");
	return employeeToList;
}

	public boolean getMyDetails(EmployeeInfoViewForm objform) {
		
		IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
		EmployeeViewHelper helper= EmployeeViewHelper.getInstance();
		boolean flag=false;
		
		try {
			Employee empMyDetails=txn.GetMyDetails(objform);
			
			if (empMyDetails != null) {
				flag=true;
				helper.convertBoToForm(empMyDetails,objform);
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return flag;
	
	}
		
		
		
		
		
		
		public boolean getApplicantDetails(EmployeeInfoViewForm objform) {
			
			IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
			EmployeeViewHelper helper= EmployeeViewHelper.getInstance();
			boolean flag=false;
			
			try {
				Employee empApplicantDetails=txn.GetEmpDetails(objform);
				
				/*	if (empApplicantDetails != null) {
					flag=true;
					helper.convertBoToForm(empApplicantDetails,objform);
					
					
					
				}*/
				Users userApplicantDetails=txn.GetUsersDetails(objform);
				
				if(userApplicantDetails!=null){
					flag=true;
					helper.convertBoToFormNew(userApplicantDetails,objform);
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return flag;
		
		}
		
			
		public List<EmpTypeTo> getWorkTimeEntry(String empTypeId) throws Exception
		{		
			String dataQuery=EmployeeViewHelper.getInstance().getQueryByselectedEmpTypeId(empTypeId);
			IEmployeeViewTransaction transaction = EmployeeViewTransactionImpl.getInstance();
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
			//String dataQuery=EmployeeViewHelper.getInstance().getQueryByselectedPayscaleId(payscaleId);
			IEmployeeViewTransaction transaction = EmployeeViewTransactionImpl.getInstance();
			String scale=transaction.getScaleQueryByPayscaleId(payscaleId);
			return scale;
		}
		
		
		/*public void getEmployeeDetailsForEdit(String empId) throws Exception
		{		
			//EmployeeInfoViewForm EmployeeInfoViewForm=new EmployeeInfoViewForm();
			IEmployeeViewTransaction transaction = EmployeeViewTransactionImpl.getInstance();
			Employee emp=transaction.GetEditEmpDetails(empId);
			//setBOtoForm(emp);
			
		}*/
		
		public List<EmpLeaveAllotTO> getEmpLeaveList(String empTypeId, EmployeeInfoViewForm empForm) throws Exception
		{	
			IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
			int month=txn.getInitializationMonth(Integer.parseInt(empForm.getEmptypeId()));
			int currentMonth=EmployeeInfoEditHelper.currentMonth();
			int year1=0;
			int year=Calendar.getInstance().get(Calendar.YEAR);
			String dataQuery=EmployeeViewHelper.getInstance().getLeaveByEmpTypeId(empTypeId);
		//	List<EmpLeaveAllotment> Leave=transaction.getEmpLeaveListQueryByEmpTyptId(dataQuery);
			
			List<EmpLeaveAllotment> Leave=txn.getEmpLeaveListQueryByEmpTyptId(dataQuery);
			List<EmpLeaveAllotTO> toList=new ArrayList<EmpLeaveAllotTO>();
			
				Iterator itr=Leave.iterator();
				while (itr.hasNext()) {
					EmpLeaveAllotment obj = (EmpLeaveAllotment) itr.next();
					EmpLeaveAllotTO to=new EmpLeaveAllotTO();
					if(month==6 && currentMonth < month ){
					      year1=year-1;
				     }
					EmpLeaveType empLeaveType=new EmpLeaveType();
					empLeaveType.setId(obj.getEmpLeaveType().getId());
					to.setEmpLeaveType(empLeaveType);
					//to.setEmpLeaveType(obj.getEmpLeaveType().getId());
					
					to.setLeaveType(obj.getEmpLeaveType().getName());
					to.setAllottedLeave(String.valueOf("0"));
					to.setSanctionedLeave(String.valueOf("0"));
					to.setRemainingLeave(String.valueOf("0"));
					String monthString = new DateFormatSymbols().getMonths()[month-1];
					to.setMonth(monthString);
					to.setYear(year1);
					toList.add(to);
				}
			
				return toList;
		
		}
			
		
		public void getInitialPageData(EmployeeInfoViewForm EmployeeInfoViewForm)throws Exception
		{
			 Map<String,String> streamMap=empTransaction.getStreamMap();
			 if(streamMap!=null)
			 {
				 EmployeeInfoViewForm.setTempStreamMap(streamMap);
			 }
			 Map<String,String> designationMap=empTransaction.getDesignationMap();
			 if(designationMap!=null){
				 EmployeeInfoViewForm.setTempDesignationMap(designationMap);
			 }
			 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 EmployeeInfoViewForm.setTempDepartmentMap(departmentMap);
			 }
			 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
			 if(empTypeMap!=null){
				 EmployeeInfoViewForm.setTempEmpTypeMap(empTypeMap);
			 }
			 
		}
		
		public void getInitialData(EmployeeInfoViewForm EmployeeInfoViewForm)throws Exception {
			log.info("Start getInitialData of EmployeeInfoEditHandler");
			String empId=empTransaction.getEmpId(EmployeeInfoViewForm.getEmployeeId());
			EmployeeInfoViewForm.setEmployeeId(empId);
			 Map<String,String> lakhsAndThousands=new LinkedHashMap<String, String>();
			 Map<String,String> designationMap=empTransaction.getDesignationMap();
			 if(designationMap!=null){
				 EmployeeInfoViewForm.setDesignationMap(designationMap);
				// EmployeeInfoViewForm.setPostAppliedMap(designationMap);
			 }
			 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
			 if(empTypeMap!=null){
				 EmployeeInfoViewForm.setEmpTypeMap(empTypeMap);
			 }
			/* payScaleMap=empTransaction.getPayScaleMap();
			 if(payScaleMap!=null){
				 EmployeeInfoViewForm.setPayScaleMap(payScaleMap);
			 }*/
			 Map<String,String> titleMap=empTransaction.getTitleMap();
			 if(titleMap!=null){
				 EmployeeInfoViewForm.setTitleMap(titleMap);
			 }
			 Map<String,String> ReportToMap=empTransaction.getReportToMap();
			 if(ReportToMap!=null){
				 EmployeeInfoViewForm.setReportToMap(ReportToMap);
			 }
			 Map<String,String> streamMap=empTransaction.getStreamMap();
			 if(streamMap!=null){
				 EmployeeInfoViewForm.setStreamMap(streamMap);
			 }
			 Map<String,String> workLocationMap=empTransaction.getWorkLocationMap();
			 if(workLocationMap!=null){
				 EmployeeInfoViewForm.setWorkLocationMap(workLocationMap);
			 }
			 Map<String,String> religionMap=empTransaction.getReligionMap();
			 if(religionMap!=null){
				 EmployeeInfoViewForm.setReligionMap(religionMap);
			}
			 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 EmployeeInfoViewForm.setDepartmentMap(departmentMap);
		}
			 Map<String,String> countryMap=empTransaction.getCountryMap();
			 if(countryMap!=null){
				 EmployeeInfoViewForm.setCountryMap(countryMap);
				 EmployeeInfoViewForm.setCurrentCountryMap(countryMap);
				 EmployeeInfoViewForm.setPassportCountryMap(countryMap);
				 EmployeeInfoViewForm.setVisaCountryMap(countryMap);
			 }
			 Map<String,String> nationalityMap=empTransaction.getNationalityMap();
			 if(nationalityMap!=null)
				 EmployeeInfoViewForm.setNationalityMap(nationalityMap);
			 Map<String,String> qualificationLevelMap=empTransaction.getQualificationLevelMap();
			 if(qualificationLevelMap!=null){
				 EmployeeInfoViewForm.setQualificationLevelMap(qualificationLevelMap);
			 }
			 Map<String,String> qualificationMap=empTransaction.getQualificationMap();
			 if(qualificationMap!=null){
				 EmployeeInfoViewForm.setQualificationMap(qualificationMap);
			 }
			 List<EmpQualificationLevelTo> qualificationFixedTo=empTransaction.getQualificationFixedMap();
			 if(qualificationFixedTo!=null){
				 EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpQualificationFixedTo(qualificationFixedTo);
			 }
			 List<EmpAllowanceTO> payscaleFixedTo=empTransaction.getPayAllowanceFixedMap();
			 if(payscaleFixedTo!=null){
				 EmployeeInfoViewForm.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
			 }
			 EmployeeInfoViewForm.setActive("1");
			 EmployeeInfoViewForm.setNoOfPublicationsNotRefered("0");
			 EmployeeInfoViewForm.setNoOfPublicationsRefered("0");
			 EmployeeInfoViewForm.setBooks("0");
			/* empLeaveTo=empTransaction.getEmpLeaveList(empTypeId);
			 if(empLeaveTo!=null){
				 EmployeeInfoViewForm.getEmployeeInfoTONew().setEmpLeaveTo(empLeaveTo);
			 }*/
			 Map<String,String> subjectAreaMap=empTransaction.getSubjectAreaMap();
			 if(subjectAreaMap!=null){
				 EmployeeInfoViewForm.setSubjectAreaMap(subjectAreaMap);
			 }
			 
			 Map<String,String> jobTypeMap=empTransaction.getJobType();
			 if(jobTypeMap!=null){
				 EmployeeInfoViewForm.setJobTypeMap(jobTypeMap);
			 }
			 for(int i=0;i<100;i++){
				 lakhsAndThousands.put(String.valueOf(i),String.valueOf(i));
			 }
			 if(lakhsAndThousands!=null){
				 EmployeeInfoViewForm.setLakhsAndThousands(lakhsAndThousands);
			 }
			
			log.info("End getInitialData of EmpEditHandler");
		}

		/**
		 * @param EmployeeInfoViewForm
		 * @return
		 * @throws Exception 
		 */
	

	/*	public EmployeeInfoEditTO getSearchedEmployee(
				EmployeeInfoViewForm objform) {
			
			IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
			EmployeeViewHelper helper= EmployeeViewHelper.getInstance();
			String empId= objform.getEmployeeId();
			try {
				Employee emplist=txn.GetEditEmpDetails(empId);
				
				EmployeeInfoTONew employeeInfoTONew = null;
				if (emplist != null) {
					
					objform.setEmployeeId(objform.getEmployeeId());
					employeeInfoTONew = helper.copyPropertiesValue(emplist);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// TODO Auto-generated method stub
			return null;
		}*/

		 public void AgeCalculation(EmployeeInfoViewForm EmployeeInfoViewForm)throws Exception
		 {
				
			  Calendar cal1 = new GregorianCalendar();
		      Calendar cal2 = new GregorianCalendar();
		      int age = 0;
		      int factor = 0; 
		      Date today= new Date();
		     if(EmployeeInfoViewForm.getDateOfBirth()!=null && !EmployeeInfoViewForm.getDateOfBirth().isEmpty())
		     {
		      Date dateOfBirth;
		      dateOfBirth= CommonUtil.ConvertStringToDate(EmployeeInfoViewForm.getDateOfBirth());
		    //  Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(dateOfBirth);
		      
		   //   Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(String.valueOf(today));
		      
		      cal1.setTime(dateOfBirth);
		      cal2.setTime(today);
		      if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
		            factor = -1; 
		      }
		      age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		     String Age=String.valueOf(age);
		      EmployeeInfoViewForm.setAge(Age + " yrs");
		     }
	 }
		 
		 public boolean checkFingerPrintIdUnique(String fingerPrintId, String empId)
			throws Exception {
				boolean unique = false;
				try{
				log.info("Enter checkFingerPrintIdUnique ...");
				
				
				unique = empTransaction.checkFingerPrintIdUnique(fingerPrintId, empId);
					log.info("Exit checkFingerPrintIdUnique ...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					return unique;
			}
			public boolean checkCodeUnique(String code, String empId)
			throws Exception {
				log.info("Enter checkCodeUnique ...");
				boolean unique = false;
				try
				{
				
				unique = empTransaction.checkCodeUnique(code, empId);
					log.info("Exit checkCodeUnique ...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					return unique;
			}
			public boolean checkUidUnique(String Uid, String empId)throws Exception {
				log.info("Enter checkUidUnique ...");
				boolean unique = false;
				try
				{
				unique = empTransaction.checkUidUnique(Uid, empId );
					log.info("Exit checkUidUnique ...");
			} catch (Exception e) {
				e.printStackTrace();
			}
					return unique;
			}
			
		public List<EmployeeTO> getSrchDeptEmp(EmployeeInfoViewForm stForm)throws Exception {
				//IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
				IEmployeeViewTransaction txn1 = new EmployeeViewTransactionImpl();
				//int year=Calendar.getInstance().get(Calendar.YEAR);
				List<EmployeeTO> empTo=null;
				if(stForm.getTempFingerPrintId()!=null && !stForm.getTempFingerPrintId().isEmpty()){
					 empTo=getSearchedDeptEmployee(stForm);
						 if(empTo==null || empTo.isEmpty())
						 {
							 String departmentName = txn1.getDepartmentNameForFingerPrintId(stForm.getTempFingerPrintId());
								stForm.setDepartmentName(departmentName);
								throw new ApplicationException(); 
						 }
					
				}else if(stForm.getTempCode()!=null && !stForm.getTempCode().isEmpty()){
					empTo=getSearchedDeptEmployee(stForm);
					if(empTo==null || empTo.isEmpty())
					{
						String departmentName = txn1.getDepartmentNameForEmpCode(stForm.getTempCode());
						stForm.setDepartmentName(departmentName);
						throw new ApplicationException();
					}
				}else if(stForm.getTempName()!=null && !stForm.getTempName().isEmpty()){
				
					empTo=getSearchedDeptEmployee(stForm);
				   if(empTo==null || empTo.isEmpty())
				   {
					throw new BusinessException();
				   }
				}
				else
				{
					empTo=getSearchedDeptEmployee(stForm);
				}
				
				return empTo;
			}
			/**
			 * @param stForm
			 * @return
			 * @throws Exception
			 */
		public List<EmployeeTO> getSearchedDeptEmployee(EmployeeInfoViewForm stForm)throws Exception {
			log.info("enter getSearchedStudents");
			IEmployeeViewTransaction txn = new EmployeeViewTransactionImpl();
			EmployeeViewHelper helper= EmployeeViewHelper.getInstance();
			
			StringBuffer query = txn.getSerDeptEmpQuery(stForm.getTempCode(), stForm.getTempFingerPrintId(),
					stForm.getTempName(), stForm.getUserId());
			List<Employee> employeelist=txn.getSerchedDeptEmployee(query,stForm);
				
			List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist);
			log.info("exit getSearchedStudents");
			return employeeToList;
		}
					  
	}






