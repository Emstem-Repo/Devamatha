package com.kp.cms.handlers.employee;

	import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.EditMyProfile;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.to.admin.EmpAllowanceTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpLeaveAllotTO;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.EmpTypeTo;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

	public class EmployeeInfoEditHandler {
		
		private static final Log log = LogFactory.getLog(EmployeeInfoEditHandler.class);
		IEmployeeInfoEditTransaction empTransaction=EmployeeInfoEditTransactionImpl.getInstance();
		
		private static volatile EmployeeInfoEditHandler instance=null;
		
		/**
		 * 
		 */
		private EmployeeInfoEditHandler(){
			
		}
		
		/**
		 * @return
		 */
		public static EmployeeInfoEditHandler getInstance(){
			log.info("Start getInstance of EmployeeInfoEditHandler");
			if(instance==null){
				instance=new EmployeeInfoEditHandler();
			}
			log.info("End getInstance of EmployeeInfoEditHandler");
			return instance;
		}
		
	/**
	 * @param stForm
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeTO> getSearchedEmployee(int eid,int rid2,EmployeeInfoEditForm stForm,int principleid,int adminid,int testid)throws Exception {
	log.info("enter getSearchedStudents");
	IEmployeeInfoEditTransaction txn = new EmployeeInfoEditTransactionImpl();
	EmployeeInfoEditHelper helper= EmployeeInfoEditHelper.getInstance();
	
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
			departmentId , designationId, stForm.getTempName(), stForm.getTempActive(), streamId, stForm.getTempTeachingStaff(),empTypeId,eid,rid2,principleid,adminid,testid);
	List<Employee> employeelist=txn.getSerchedEmployee(query);
	

	
	List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist, departmentId , designationId);
	log.info("exit getSearchedStudents");
	return employeeToList;
}
		/**
		 * @param objform
		 * @return
		 */
		public boolean getApplicantDetails(EmployeeInfoEditForm objform) throws Exception {
			IEmployeeInfoEditTransaction txn = new EmployeeInfoEditTransactionImpl();
			boolean flag=false;
			Employee empApplicantDetails=txn.GetEmpDetails(objform);
			if (empApplicantDetails != null) {
				flag=true;
				EmployeeInfoEditHelper.getInstance().convertBoToForm(empApplicantDetails,objform);
			}
			return flag;
		}
		
		/**
		 * @param empTypeId
		 * @return
		 * @throws Exception
		 */
		public List<EmpTypeTo> getWorkTimeEntry(String empTypeId) throws Exception
		{		
			String dataQuery=EmployeeInfoEditHelper.getInstance().getQueryByselectedEmpTypeId(empTypeId);
			IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
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
			//String dataQuery=EmployeeInfoEditHelper.getInstance().getQueryByselectedPayscaleId(payscaleId);
			IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
			String scale=transaction.getScaleQueryByPayscaleId(payscaleId);
			return scale;
		}
		
		
		/*public void getEmployeeDetailsForEdit(String empId) throws Exception
		{		
			//EmployeeInfoEditForm employeeInfoEditForm=new EmployeeInfoEditForm();
			IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
			Employee emp=transaction.GetEditEmpDetails(empId);
			//setBOtoForm(emp);
			
		}*/
		
	/*	public List<EmpLeaveAllotTO> getEmpLeaveList(String empTypeId) throws Exception
		{		
			String dataQuery=EmployeeInfoEditHelper.getInstance().getLeaveByEmpTypeId(empTypeId);
			IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
			List<EmpLeaveAllotment> Leave=transaction.getEmpLeaveListQueryByEmpTyptId(dataQuery);
			List<EmpLeaveAllotTO> toList=new ArrayList<EmpLeaveAllotTO>();
			if(Leave!=null && !Leave.isEmpty()){
				
				Iterator itr=Leave.iterator();
				while (itr.hasNext()) {
					EmpLeaveAllotment obj = (EmpLeaveAllotment) itr.next();
					EmpLeaveAllotTO to=new EmpLeaveAllotTO();
					to.setEmpLeaveType(obj.getEmpLeaveType());
					to.setLeaveType(obj.getEmpLeaveType().getName());
					to.setAllottedLeave(String.valueOf("0"));
					to.setSanctionedLeave(String.valueOf("0"));
					toList.add(to);
				}
			}
				return toList;
		
		}*/
		
		public void getInitialPageData(EmployeeInfoEditForm employeeInfoEditForm)throws Exception
		{
			 Map<String,String> streamMap=empTransaction.getStreamMap();
			 if(streamMap!=null)
			 {
				 employeeInfoEditForm.setTempStreamMap(streamMap);
			 }
			 Map<String,String> designationMap=empTransaction.getDesignationMap();
			 if(designationMap!=null){
				 employeeInfoEditForm.setTempDesignationMap(designationMap);
			 }
			 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 employeeInfoEditForm.setTempDepartmentMap(departmentMap);
			 }
			 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
			 if(empTypeMap!=null){
				 employeeInfoEditForm.setTempEmpTypeMap(empTypeMap);
			 }
			 
		}
		
		public void getInitialData(EmployeeInfoEditForm employeeInfoEditForm)throws Exception {
			log.info("Start getInitialData of EmployeeInfoEditHandler");
			String empId=empTransaction.getEmpId(employeeInfoEditForm.getEmployeeId());
			employeeInfoEditForm.setEmployeeId(empId);
			 Map<String,String> lakhsAndThousands=new LinkedHashMap<String, String>();
			 
			 Map<String,String> designationMap=empTransaction.getDesignationMap();
			 if(designationMap!=null){
				 employeeInfoEditForm.setDesignationMap(designationMap);
				// employeeInfoEditForm.setPostAppliedMap(designationMap);
			 }
			 Map<String,String> empTypeMap=empTransaction.getEmpTypeMap();
			 if(empTypeMap!=null){
				 employeeInfoEditForm.setEmpTypeMap(empTypeMap);
			 }
			/* payScaleMap=empTransaction.getPayScaleMap();
			 if(payScaleMap!=null){
				 employeeInfoEditForm.setPayScaleMap(payScaleMap);
			 }*/
			 Map<String,String> titleMap=empTransaction.getTitleMap();
			 if(titleMap!=null){
				 employeeInfoEditForm.setTitleMap(titleMap);
			 }
			 Map<String,String> ReportToMap=empTransaction.getReportToMap();
			 if(ReportToMap!=null){
				 employeeInfoEditForm.setReportToMap(ReportToMap);
			 }
			 Map<String,String> streamMap=empTransaction.getStreamMap();
			 if(streamMap!=null){
				 employeeInfoEditForm.setStreamMap(streamMap);
			 }
			 Map<String,String> workLocationMap=empTransaction.getWorkLocationMap();
			 if(workLocationMap!=null){
				 employeeInfoEditForm.setWorkLocationMap(workLocationMap);
			 }
			 Map<String,String> religionMap=empTransaction.getReligionMap();
			 if(religionMap!=null){
				 employeeInfoEditForm.setReligionMap(religionMap);
			}
			 Map<String,String> departmentMap=empTransaction.getDepartmentMap();
			 if(departmentMap!=null)
			 {
				 employeeInfoEditForm.setDepartmentMap(departmentMap);
				 employeeInfoEditForm.setDeputationDepartmentMap(departmentMap);
		}
			 Map<String,String> countryMap=empTransaction.getCountryMap();
			 if(countryMap!=null){
				 employeeInfoEditForm.setCountryMap(countryMap);
				 employeeInfoEditForm.setCurrentCountryMap(countryMap);
				 employeeInfoEditForm.setPassportCountryMap(countryMap);
				 employeeInfoEditForm.setVisaCountryMap(countryMap);
			 }
			 Map<String,String> nationalityMap=empTransaction.getNationalityMap();
			 if(nationalityMap!=null)
				 employeeInfoEditForm.setNationalityMap(nationalityMap);
			 Map<String,String> qualificationLevelMap=empTransaction.getQualificationLevelMap();
			 if(qualificationLevelMap!=null){
				 employeeInfoEditForm.setQualificationLevelMap(qualificationLevelMap);
			 }
			 Map<String,String> qualificationMap=empTransaction.getQualificationMap();
			 if(qualificationMap!=null){
				 employeeInfoEditForm.setQualificationMap(qualificationMap);
			 }
			 List<EmpQualificationLevelTo> qualificationFixedTo=empTransaction.getQualificationFixedMap();
			 if(qualificationFixedTo!=null){
				 employeeInfoEditForm.getEmployeeInfoTONew().setEmpQualificationFixedTo(qualificationFixedTo);
			 }
			 List<EmpAllowanceTO> payscaleFixedTo=empTransaction.getPayAllowanceFixedMap();
			 if(payscaleFixedTo!=null){
				 employeeInfoEditForm.getEmployeeInfoTONew().setPayscaleFixedTo(payscaleFixedTo);
			 }
			 employeeInfoEditForm.setActive("1");
			 employeeInfoEditForm.setNoOfPublicationsNotRefered("0");
			 employeeInfoEditForm.setNoOfPublicationsRefered("0");
			 employeeInfoEditForm.setBooks("0");
			/* empLeaveTo=empTransaction.getEmpLeaveList(empTypeId);
			 if(empLeaveTo!=null){
				 employeeInfoEditForm.getEmployeeInfoTONew().setEmpLeaveTo(empLeaveTo);
			 }*/
			 Map<String,String> subjectAreaMap=empTransaction.getSubjectAreaMap();
			 if(subjectAreaMap!=null){
				 employeeInfoEditForm.setSubjectAreaMap(subjectAreaMap);
			 }
			 
			 Map<String,String> jobTypeMap=empTransaction.getJobType();
			 if(jobTypeMap!=null){
				 employeeInfoEditForm.setJobTypeMap(jobTypeMap);
			 }
			 for(int i=0;i<100;i++){
				 lakhsAndThousands.put(String.valueOf(i),String.valueOf(i));
			 }
			 if(lakhsAndThousands!=null){
				 employeeInfoEditForm.setLakhsAndThousands(lakhsAndThousands);
			 }
			
			log.info("End getInitialData of EmpEditHandler");
		}

		/**
		 * @param employeeInfoEditForm
		 * @return
		 * @throws Exception 
		 */
		public boolean saveEmp(EmployeeInfoEditForm employeeInfoEditForm) throws Exception
		{
			boolean flag=false;
			boolean flag1=false;
			Employee employee=EmployeeInfoEditHelper.getInstance().convertFormToBo(employeeInfoEditForm);
			flag=empTransaction.saveEmployee(employee,employeeInfoEditForm);
			if(employeeInfoEditForm.getEmployeeId()!=null)
			{
				if(employeeInfoEditForm.getActive()!=null && employeeInfoEditForm.getActive().equals("0"))
				{
				Users user = empTransaction.userExists(employeeInfoEditForm) ;
				if(user!=null)
				{
				user.setActive(false);
				user.setLastModifiedDate(new Date());
				user.setModifiedBy(employeeInfoEditForm.getUserId());
				
				flag1=empTransaction.updateUser(user);
				}	
				}
				else if(employeeInfoEditForm.getActive()!=null && employeeInfoEditForm.getActive().equals("1"))
				{
					Users user = empTransaction.userExists(employeeInfoEditForm) ;
					if(user!=null)
					{
					user.setActive(true);
					user.setLastModifiedDate(new Date());
					user.setModifiedBy(employeeInfoEditForm.getUserId());
					
					flag1=empTransaction.updateUser(user);
						
					}
				}
				}
			
			return flag;
		}
			
		
	/*	public EmployeeInfoEditTO getSearchedEmployee(
				EmployeeInfoEditForm objform) {
			
			IEmployeeInfoEditTransaction txn = new EmployeeInfoEditTransactionImpl();
			EmployeeInfoEditHelper helper= EmployeeInfoEditHelper.getInstance();
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

		 public void AgeCalculation(EmployeeInfoEditForm employeeInfoEditForm)throws Exception
		 {
				
			  Calendar cal1 = new GregorianCalendar();
		      Calendar cal2 = new GregorianCalendar();
		      int age = 0;
		      int factor = 0; 
		      Date today= new Date();
		     if(employeeInfoEditForm.getDateOfBirth()!=null && !employeeInfoEditForm.getDateOfBirth().isEmpty())
		     {
		      Date dateOfBirth;
		      dateOfBirth= CommonUtil.ConvertStringToDate(employeeInfoEditForm.getDateOfBirth());
		    //  Date date1 = new SimpleDateFormat("MM-dd-yyyy").parse(dateOfBirth);
		      
		   //   Date date2 = new SimpleDateFormat("MM-dd-yyyy").parse(String.valueOf(today));
		      
		      cal1.setTime(dateOfBirth);
		      cal2.setTime(today);
		      if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
		            factor = -1; 
		      }
		      age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
		     String Age=String.valueOf(age);
		      employeeInfoEditForm.setAge(Age + " yrs");
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
				//IEmployeeInfoNewTransaction txn = new EmployeeInfoNewTransactionImpl();
				unique = empTransaction.checkUidUnique(Uid, empId );
					log.info("Exit checkUidUnique ...");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					return unique;
			}
			
			public List<EmpLeaveAllotTO> getEmpLeaveList(String empTypeId, EmployeeInfoEditForm empForm) throws Exception
			{	
				IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
				int month=transaction.getInitializationMonth(Integer.parseInt(empForm.getEmptypeId()));
				int oldMonth=transaction.getInitializationMonth(Integer.parseInt(empForm.getEmpTypeIdOld()));
				
				int currentMonth=EmployeeInfoEditHelper.currentMonth();
				int year1=0;
				int year=Calendar.getInstance().get(Calendar.YEAR);
				String dataQuery=EmployeeInfoEditHelper.getInstance().getLeaveByEmpTypeId(empTypeId);
			
				List<EmpLeaveAllotTO> LeaveById=EmployeeInfoEditHelper.getInstance().getLeaveByEmpId(empTypeId, year, currentMonth, month, empForm, oldMonth);
				if(LeaveById!=null && !LeaveById.isEmpty())
				{
					return LeaveById;
				}
				else
				{
						List<EmpLeaveAllotTO> toList=new ArrayList<EmpLeaveAllotTO>();
						List<EmpLeaveAllotment> Leave=transaction.getEmpLeaveListQueryByEmpTyptId(dataQuery);
						
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
							Collections.sort(toList);
						return toList;
						}
					}
			public void setEmpIdToForm(EmployeeInfoEditForm empForm)throws Exception{
				IEmployeeInfoEditTransaction transaction = EmployeeInfoEditTransactionImpl.getInstance();
				if(empForm.getUserId()!=null && !empForm.getUserId().isEmpty()){
					transaction.getEmployeeId(Integer.parseInt(empForm.getUserId()),empForm);
				}
			}

			public boolean getApplicationDetails(EmployeeInfoEditForm objform,HttpSession session) {
				
				IEmployeeInfoEditTransaction txn = new EmployeeInfoEditTransactionImpl();
				//EmployeeInfoEditHelper helper= EmployeeInfoEditHelper.getInstance();
				boolean flag=false;
				
				try {
					Employee empApplicantDetails=txn.GetEmpDetails(objform);
					session.setAttribute("applicationDeatils", empApplicantDetails);
					if (empApplicantDetails != null) {
						flag=true;
						EmployeeInfoEditHelper.getInstance().convertBoToForm(empApplicantDetails,objform);
						
						
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// TODO Auto-generated method stub
				return flag;
			
			}
			
			public boolean saveMyProfile(EmployeeInfoEditForm employeeInfoEditForm,HttpSession session) throws Exception
			{
				EditMyProfile myProfile=empTransaction.getEditMyProfile(employeeInfoEditForm);
				String mode="";
				if(myProfile==null){
					myProfile=new EditMyProfile();
					setEditMyProfile(myProfile);
					mode="new";
				}
				Employee employee=EmployeeInfoEditHelper.getInstance().convertFormToBoOfMyProfile(employeeInfoEditForm,session,myProfile);
				boolean flag=empTransaction.saveEditEmployee(employee, employeeInfoEditForm,mode);
				return flag;
			}
			public EditMyProfile setEditMyProfile(EditMyProfile profile){
				profile.setActive(0);
				profile.setBankAccountNo(0);
				profile.setBloodGroup(0);
				profile.setBooks(0);
				profile.setCode(0);
				profile.setCurrentAddress(0);
				profile.setDateOfBirth(0);
				profile.setDateOfJoin(0);
				profile.setDateOfRetirement(0);
				profile.setDepartment(0);
			    profile.setDesignation(0);
			    profile.setEmpAcheivements(0);
			    profile.setEmpContactDetails(0);
			    profile.setEmpDependents(0);
			    profile.setEmpEducationDetails(0);
			    profile.setEmpFeeConcession(0);
			    profile.setEmpFinancial(0);
			    profile.setEmpImmigrations(0);
			    profile.setEmpIncentives(0);
			    profile.setEmpJobType(0);
			    profile.setEmpLeave(0);
			    profile.setEmpLoan(0);
			    profile.setEmpPayAllowances(0);
			    profile.setEmpPreviousExp(0);
			    profile.setEmpRemarks(0);
			    profile.setEmpStream(0);
			    profile.setEmpType(0);
			    profile.setFingerPrintId(0);
			    profile.setFourWheelerNo(0);
			    profile.setGender(0);
			    profile.setGrade(0);
			    profile.setHomeTelephone(0);
			    profile.setIsSCDataGenerated(0);
			    profile.setIsSmartCardDelivered(0);
			    profile.setIsTeachingStaff(0);
			    profile.setMaritalStatus(0);
			    profile.setMobile(0);
			    profile.setName(0);
			    profile.setNationality(0);
			    profile.setNoOfPublicationsNotRefered(0);
			    profile.setNoOfPublicationsRefered(0);
			    profile.setOtherEmail(0);
			    profile.setPanNo(0);
			    profile.setPayScale(0);
			    profile.setPermanentAddress(0);
			    profile.setPfNo(0);
			    profile.setReligion(0);
			    profile.setReservationCategory(0);
			    profile.setResignationDetails(0);
			    profile.setSameAddress(0);
			    profile.setSmartCardNo(0);
			    profile.setSubjectArea(0);
			    profile.setTwoWheelerNo(0);
			    profile.setUid(0);
			    profile.setWorkEmail(0);
			    profile.setWorkLocation(0);
			    profile.setWorkTimeEntry(0);
			    profile.setTitle(0);
			    profile.setQualificationLevel(0);
			    profile.setReportTo(0);
			    profile.setWorkTelephone(0);
				return profile;
			}

			
		  	
	}



