package com.kp.cms.handlers.employee;



import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpOnlineResume;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestEducationalDetails;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.bo.employee.GuestPreviousExperience;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.GuestFacultyInfoForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.attendance.AttendanceEntryHelper;
import com.kp.cms.helpers.employee.EmpEventVacationHelper;
import com.kp.cms.helpers.employee.GuestFacultyInfoHelper;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.employee.GuestEducationalDetailsTO;
import com.kp.cms.to.employee.GuestFacultyInfoTo;
import com.kp.cms.to.employee.GuestFacultyTO;
import com.kp.cms.to.employee.GuestPreviousExperienceTO;
import com.kp.cms.transactions.employee.IGuestFacultyInfoTransaction;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmpEventVacationImpl;
import com.kp.cms.transactionsimpl.employee.GuestFacultyInfoImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.RepeatMidSemReminderSmsMail;
import com.kp.cms.utilities.jms.MailTO;

public class GuestFacultyInfoHandler {
	
	private static final Log log = LogFactory.getLog(GuestFacultyInfoHandler.class);
	IGuestFacultyInfoTransaction empTransaction=GuestFacultyInfoImpl.getInstance();
	
	private static volatile GuestFacultyInfoHandler instance=null;
	
	/**
	 * 
	 */
	private GuestFacultyInfoHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static GuestFacultyInfoHandler getInstance(){
		log.info("Start getInstance of GuestFacultyInfoHandler");
		if(instance==null){
			instance=new GuestFacultyInfoHandler();
		}
		log.info("End getInstance of GuestFacultyInfoHandler");
		return instance;
	}
	
/**
 * @param stForm
 * @return
 * @throws Exception
 */
public List<GuestFacultyTO> getSearchedEmployee(GuestFacultyInfoForm stForm)throws Exception {
log.info("enter getSearchedStudents");
IGuestFacultyInfoTransaction txn = new GuestFacultyInfoImpl();
GuestFacultyInfoHelper helper= GuestFacultyInfoHelper.getInstance();

int designationId = 0;
int departmentId = 0;
int streamId = 0;
	
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

StringBuffer query = txn.getSerchedEmployeeQuery(departmentId , designationId, stForm.getTempName(), stForm.getTempActive(), streamId);
List<GuestFaculty> employeelist=txn.getSerchedEmployee(query);



List<GuestFacultyTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist, departmentId , designationId);
log.info("exit getSearchedStudents");
return employeeToList;
}
	/**
	 * @param objform
	 * @return
	 */
	public boolean getApplicantDetails(GuestFacultyInfoForm objform) throws Exception {
		IGuestFacultyInfoTransaction txn = new GuestFacultyInfoImpl();
		boolean flag=false;
		GuestFaculty empApplicantDetails=txn.GetEmpDetails(objform);
		if (empApplicantDetails != null) {
			flag=true;
			GuestFacultyInfoHelper.getInstance().convertBoToForm(empApplicantDetails,objform);
		}
		return flag;
	}
	
	/**
	 * @param objform
	 * @return
	 */
	public boolean getApplicantResumeDetails(GuestFacultyInfoForm objform) throws Exception {
		IGuestFacultyInfoTransaction txn = new GuestFacultyInfoImpl();
		boolean flag=false;
		EmpOnlineResume empApplicantDetails=txn.GetGuestResumeDetails(objform);
		if (empApplicantDetails != null) {
			flag=true;
			objform.setForwardFlag("true");
			GuestFacultyInfoHelper.getInstance().convertResumeBoToForm(empApplicantDetails,objform);
		}
		else
			objform.setForwardFlag("false");
		return flag;
	}
	
	/*public void getEmployeeDetailsForEdit(String empId) throws Exception
	{		
		//GuestFacultyInfoForm employeeInfoEditForm=new GuestFacultyInfoForm();
		IGuestFacultyInfoTransaction transaction = GuestFacultyInfoImpl.getInstance();
		GuestFaculty emp=transaction.GetEditEmpDetails(empId);
		//setBOtoForm(emp);
		
	}*/
	

	public void getInitialPageData(GuestFacultyInfoForm employeeInfoEditForm)throws Exception
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
		 
	}
	
	public void getInitialData(GuestFacultyInfoForm employeeInfoEditForm)throws Exception {
		log.info("Start getInitialData of EmployeeInfoEditHandler");
		/*if(employeeInfoEditForm.getGuestId()!=null && !employeeInfoEditForm.getGuestId().isEmpty()){
		String empId=empTransaction.getEmpId(employeeInfoEditForm.getGuestId());
		employeeInfoEditForm.setGuestId(empId);
		}*/
		 
		 Map<String,String> designationMap=empTransaction.getDesignationMap();
		 if(designationMap!=null){
			 employeeInfoEditForm.setDesignationMap(designationMap);
		 }
		
		 Map<String,String> titleMap=empTransaction.getTitleMap();
		 if(titleMap!=null){
			 employeeInfoEditForm.setTitleMap(titleMap);
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
	}
		 Map<String,String> countryMap=empTransaction.getCountryMap();
		 if(countryMap!=null){
			 employeeInfoEditForm.setCountryMap(countryMap);
			 employeeInfoEditForm.setCurrentCountryMap(countryMap);
			
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
			 GuestFacultyInfoTo to=new  GuestFacultyInfoTo();
			 to.setEmpQualificationFixedTo(qualificationFixedTo);
			 employeeInfoEditForm.setEmployeeInfoTONew(to);
		 }
		
		 employeeInfoEditForm.setActive("1");
		 employeeInfoEditForm.setNoOfPublicationsNotRefered("0");
		 employeeInfoEditForm.setNoOfPublicationsRefered("0");
		 employeeInfoEditForm.setBooks("0");
		
		 Map<String,String> subjectAreaMap=empTransaction.getSubjectAreaMap();
		 if(subjectAreaMap!=null){
			 employeeInfoEditForm.setSubjectAreaMap(subjectAreaMap);
		 }
		 
		 Map<String,String> jobTypeMap=empTransaction.getJobType();
		 if(jobTypeMap!=null){
			 employeeInfoEditForm.setJobTypeMap(jobTypeMap);
		 }
		
		
		log.info("End getInitialData of EmpEditHandler");
	}

	/**
	 * @param employeeInfoEditForm
	 * @return
	 * @throws Exception 
	 */
	public boolean saveEmp(GuestFacultyInfoForm employeeInfoEditForm) throws Exception
	{
		boolean flag=false;
		//boolean flag1=false;
		GuestFaculty employee=GuestFacultyInfoHelper.getInstance().convertFormToBo(employeeInfoEditForm);
		flag=empTransaction.saveEmployee(employee,employeeInfoEditForm.getPhoto());
		employeeInfoEditForm.setPhoto(null);
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
					desc=desc.replace("[TYPE]", "Guest");
					desc=desc.replace("[NAME]", employeeInfoEditForm.getName());
					if(employeeInfoEditForm.getDepartmentId() != null && !employeeInfoEditForm.getDepartmentId().isEmpty())
						desc=desc.replace("[DEPARTMENT]", employeeInfoEditForm.getDepartmentMap().get(employeeInfoEditForm.getDepartmentId()));
					mailto.setFromAddress(CMSConstants.MAIL_USERID);
					mailto.setFromName(prop.getProperty("knowledgepro.employee.add.notification.fromName"));
					mailto.setMessage(desc);
					mailto.setSubject("Guest Add Notification Mail");
					mailto.setToAddress(prop.getProperty("knowledgepro.employee.add.notification.sendmail.id"));
					CommonUtil.sendMail(mailto);
				}
			}
		}
	/*	if(employeeInfoEditForm.getGuestId()!=null)
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
			}*/
		
		return flag;
	}
		
	/**
	 * @param employeeInfoEditForm
	 * @return
	 * @throws Exception 
	 */
	public boolean saveEmpEdit(GuestFacultyInfoForm employeeInfoEditForm) throws Exception
	{
		boolean flag=false;
		GuestFaculty employee=GuestFacultyInfoHelper.getInstance().convertFormToBoEdit(employeeInfoEditForm);
		flag=empTransaction.saveEmployee(employee,employeeInfoEditForm.getPhoto());
		employeeInfoEditForm.setPhoto(null);
		if(employeeInfoEditForm.getGuestId()!=null)
		{
			if(employeeInfoEditForm.getActive()!=null && employeeInfoEditForm.getActive().equals("0"))
			{
			Users user = empTransaction.userExists(employeeInfoEditForm) ;
			if(user!=null)
			{
			user.setActive(false);
			user.setLastModifiedDate(new Date());
			user.setModifiedBy(employeeInfoEditForm.getUserId());
			empTransaction.updateUser(user);
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
				
					empTransaction.updateUser(user);
					}
				}
			}
		
		return flag;
	}
		
	
/*	public EmployeeInfoEditTO getSearchedEmployee(
			GuestFacultyInfoForm objform) {
		
		IGuestFacultyInfoTransaction txn = new GuestFacultyInfoImpl();
		GuestFacultyInfoHelper helper= GuestFacultyInfoHelper.getInstance();
		String empId= objform.getGuestId();
		try {
			GuestFaculty emplist=txn.GetEditEmpDetails(empId);
			
			EmployeeInfoTONew employeeInfoTONew = null;
			if (emplist != null) {
				
				objform.setEmployeeId(objform.getGuestId());
				employeeInfoTONew = helper.copyPropertiesValue(emplist);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}*/

	 public void AgeCalculation(GuestFacultyInfoForm employeeInfoEditForm)throws Exception
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
		
		
		public void setEmpIdToForm(GuestFacultyInfoForm empForm)throws Exception{
			IGuestFacultyInfoTransaction transaction = GuestFacultyInfoImpl.getInstance();
			if(empForm.getUserId()!=null && !empForm.getUserId().isEmpty()){
				transaction.getEmployeeId(Integer.parseInt(empForm.getUserId()),empForm);
			}
		}

		public void getResumeDetails(GuestFacultyInfoForm empForm,HttpSession session, HttpServletRequest request) throws Exception{
			String empId = empForm.getSelectedEmployeeId();
			GuestFaculty guest = empTransaction.getDetailsForEmployee(empId);
			List<GuestEducationalDetails> eduDetails = empTransaction.getEmployeeEducationDetails(empId);
			List<GuestPreviousExperience> teachingExperience = empTransaction.getEmployeeExperienceDetails(empId);
			 List<GuestFacultyTO> guestTOs = GuestFacultyInfoHelper.getInstance().convertBoToToForPrint(guest, teachingExperience, empForm,session);
			empForm.setTos(guestTOs);
			 List<GuestEducationalDetailsTO> empEduDetails = GuestFacultyInfoHelper.getInstance().convertBoToToForEduDetails(eduDetails,empForm);
			List<GuestPreviousExperienceTO> tos = GuestFacultyInfoHelper.getInstance().convertBoToToForExpDetails(teachingExperience,guest,empForm);
			empForm.setTeachingExperience(tos);
			empForm.setEmpEducationalDetails(empEduDetails);
		}
		
		public void setEmployeeDetailsToForm(GuestFacultyInfoForm empForm) throws Exception{
			IDownloadEmployeeResumeTransaction transaction = DownloadEmployeeResumeTransactionImpl.getInstance();
			List<Department> departmentList = transaction.getDepartmentList();
			List<Designation> designationList = transaction.getDesignationList();
			List<EmpQualificationLevel> qualificationList = transaction.getEmpQualificationList();
			empForm.setDepartmentList(departmentList);
			empForm.setDesignationList(designationList);
			empForm.setQualificationList(qualificationList);
		}
		/**
		 * @param StreamId
		 * @return
		 * @throws Exception
		 */
		public Map<String, String> getFilteredDepartmentsStreamNames(String StreamId,String teachingStaff) throws Exception {
			List<Department> list = EmpEventVacationImpl.getInstance().getSearchedDepartmentStreamNames(StreamId,teachingStaff);
			Map<String, String> streamMap = GuestFacultyInfoHelper.getInstance().convertBoToForm(list);
			return streamMap;
		}

		public Map<Integer,String> getGuestFacultyMap()throws Exception{
			IGuestFacultyInfoTransaction transaction = GuestFacultyInfoImpl.getInstance();
			Map<Integer,String> guestFacultyMap = transaction.getGuestFacultyMap();
			return guestFacultyMap;
		}
		public GuestFacultyTO getGuestFacultyDetails(GuestFacultyInfoForm guestFacultyInfoForm)throws Exception{
			IGuestFacultyInfoTransaction transaction = GuestFacultyInfoImpl.getInstance();
			GuestFaculty guestFacultyBo = transaction.getGuestFacultyBo(Integer.parseInt(guestFacultyInfoForm.getGuestId()));
			GuestFacultyTO facultyTo = GuestFacultyInfoHelper.getInstance().convertGuestBoToGuestTo(guestFacultyBo,guestFacultyInfoForm);
			return facultyTo;
		}
		public boolean updateEditedGuestFacultyDetails(GuestFacultyInfoForm empForm)throws Exception{
			boolean flag = GuestFacultyInfoImpl.getInstance().updateEditedGuestFacultyBankDetails(empForm);
			return flag;
		}
	  	
}



