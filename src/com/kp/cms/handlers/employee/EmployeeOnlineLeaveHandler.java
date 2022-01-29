package com.kp.cms.handlers.employee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.blip.WMF;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeave;
import com.kp.cms.bo.admin.EmpOnlineLeave;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.DownloadEmployeeResumeForm;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.EmployeeApproverForm;
import com.kp.cms.forms.employee.EmployeeOnlineLeaveForm;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.helpers.employee.EmployeeApplyLeaveHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.helpers.employee.EmployeeOnlineLeaveHelper;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpApplyLeaveTO;
import com.kp.cms.to.hostel.LeaveTypeTo;
import com.kp.cms.transactions.employee.ILeaveInitializationTransaction;
import com.kp.cms.transactions.exam.IDownloadEmployeeResumeTransaction;
import com.kp.cms.transactions.exam.IEmployeeApplyLeaveTransaction;
import com.kp.cms.transactions.exam.IEmployeeOnlineLeaveTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.DownloadEmployeeResumeTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeApplyLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeOnlineLeaveTransactionImpl;
import com.kp.cms.transactionsimpl.employee.LeaveInitializationTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class EmployeeOnlineLeaveHandler {
	/**
	 * Singleton object of EmployeeApplyLeaveHandler
	 */
	private static volatile EmployeeOnlineLeaveHandler handler = null;
	private static final Log log = LogFactory.getLog(EmployeeOnlineLeaveHandler.class);
	private EmployeeOnlineLeaveHandler() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHandler.
	 * @return
	 */
	public static EmployeeOnlineLeaveHandler getInstance() {
		if (handler == null) {
			handler = new EmployeeOnlineLeaveHandler();
		}
		return handler;
	}
	/**
	 * @param empCode
	 * @param fingerPrintId
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployeeDetails(String userId) throws Exception {
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		return transaction.getEmployeeDetails(userId);
	}
	/**
	 * @param employeeId
	 * @param isExemption
	 * @return
	 */
	public List<LeaveTypeTo> getLeaveTypesForEmployee() throws Exception {
		String 	query="select e.id,e.name from EmpLeaveType e where e.isActive=1 and e.canapplyonline=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> list=transaction.getDataForQuery(query);
		return EmployeeApplyLeaveHelper.getInstance().convertBotoTo(list);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkAlreadyExists( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		String query=EmployeeOnlineLeaveHelper.getInstance().getAlreadyExistsQuery(employeeOnlineLeaveForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		String query1 = EmployeeOnlineLeaveHelper.getInstance().getAlreadyExistsQuery1(employeeOnlineLeaveForm);
		List list1=transaction.getDataForQuery(query1);
		boolean isHalfDay=false;
		boolean isExist=false;
		boolean isAm=false;
		if(employeeOnlineLeaveForm.getIsHalfday()!=null && employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes"))
			isHalfDay=true;
		if(employeeOnlineLeaveForm.getIsAm()!=null && employeeOnlineLeaveForm.getIsAm().equalsIgnoreCase("am"))
			isAm=true;
		
		if(list!=null && !list.isEmpty()){
			if(isHalfDay){
			Iterator<EmpApplyLeave> itr=list.iterator();
			while (itr.hasNext()) {
				EmpApplyLeave bo= itr.next();
				if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
					isExist=true;
				else{
					if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
						isExist=true;
					else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
						isExist=true;
				}
			}
			}else{
				isExist=true;
			}
		}
		if(list1!=null && !list1.isEmpty()){
			if(isHalfDay){
			Iterator<EmpOnlineLeave> itr=list1.iterator();
			while (itr.hasNext()) {
				EmpOnlineLeave bo= itr.next();
				if(bo.getIsHalfDay()==null || !bo.getIsHalfDay())
					isExist=true;
				else{
					if(isAm && bo.getIsAm().equalsIgnoreCase("am"))
						isExist=true;
					else if(!isAm && bo.getIsAm().equalsIgnoreCase("pm"))
						isExist=true;
				}
			}
			}else{
				isExist=true;
			}
		}
		
		
		return isExist;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkLeavesAvailableOrNot(EmployeeOnlineLeaveForm employeeOnlineLeaveForm,int year) throws Exception{
		List<Integer> list=getCommonLeaves();
		boolean isValid=true;
		if(!list.contains(Integer.parseInt(employeeOnlineLeaveForm.getLeaveTypeId()))){
			IEmployeeOnlineLeaveTransaction transaction1=EmployeeOnlineLeaveTransactionImpl.getInstance();
			double remainingDays=transaction1.getRemainingDaysForEmployeeAndLeaveType(employeeOnlineLeaveForm,year);
			Date startDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getEndDate());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(employeeOnlineLeaveForm.getIsHalfday()!=null && !employeeOnlineLeaveForm.getIsHalfday().isEmpty()){
				if(employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					daysBetween=daysBetween-0.5;
				}
			}
			if(remainingDays>=daysBetween){
				isValid=false;
			}
		}else{
			isValid=false;
		}
		return isValid;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	private List<Integer> getCommonLeaves() throws Exception{
		String query="select e.id from EmpLeaveType e where e.isActive=1 and e.isLeave=0";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		return transaction.getDataForQuery(query);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveApplyLeave(EmployeeOnlineLeaveForm employeeOnlineLeaveForm,int year) throws Exception {
		Date startDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getStartDate());
		Date endDate = CommonUtil.ConvertStringToDate(employeeOnlineLeaveForm.getEndDate());
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);
		double daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
		if(employeeOnlineLeaveForm.getIsHalfday()!=null && !employeeOnlineLeaveForm.getIsHalfday().isEmpty()){
			if(employeeOnlineLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
				daysBetween=daysBetween-0.5;
			}
		}
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		boolean continuousdays = transaction.checkLeaveType(employeeOnlineLeaveForm.getLeaveTypeId());
		if(!continuousdays){
			int count = CommonUtil.getSundaysForDateRange(employeeOnlineLeaveForm.getStartDate(),employeeOnlineLeaveForm.getEndDate());
			daysBetween = daysBetween-count;
		}
//		EmpApplyLeave bo=EmployeeApplyLeaveHelper.getInstance().convertFormToBO(employeeApplyLeaveForm,daysBetween,year);
		
		EmpOnlineLeave bo=EmployeeOnlineLeaveHelper.getInstance().convertFormToBO(employeeOnlineLeaveForm,daysBetween,year);
		return EmployeeOnlineLeaveTransactionImpl.getInstance().saveApplyLeave(bo);
	}
	/**
	 * @param employeeId
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public double getLeavesTaken(String employeeId, String date,int year) throws Exception{
		String[] dates=date.split("/");
		String query="select sum(e.noOfDays) from EmpApplyLeave e where e.isCanceled=0 and (e.year)='"+year+"' and month(e.fromDate)='"+dates[1]+"' and e.employee.id="+employeeId;
		//String query="select sum(e.noOfDays) from EmpApplyLeave e where e.isCanceled=0 and year(e.fromDate)='"+year+"' and month(e.fromDate)='"+dates[1]+"' and e.employee.id="+employeeId;
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		return transaction.getLeavesTaken(query);
	}
	/**
	 * @param employeeOnlineLeaveForm 
	 * @param employeeApplyLeaveForm
	 * @return
	 */
	public List<EmpLeaveTO> getDetails( String employeeId,int year, EmployeeOnlineLeaveForm employeeOnlineLeaveForm)  throws Exception{
		String query="from EmpLeave e where e.employee.id="+employeeId+" and e.isActive=1 and e.year="+year;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<EmpLeave> list=transaction.getDataForQuery(query);
		return EmployeeOnlineLeaveHelper.convertBoListtoToList(list,employeeOnlineLeaveForm);
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getEmpApplyLeaves(EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeOnlineLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			year=year-1;
	     }
		List<EmpOnlineLeave> applyLeave=transaction.getEmpApplyLeaves(Integer.parseInt(employeeOnlineLeaveForm.getUserId()),year);
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		return applyLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getEmpLeaves(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			academicYear=academicYear-1;
	     }
		List<EmpLeave> empLeave=transaction.getEmpLeaves(Integer.parseInt(employeeApplyLeaveForm.getUserId()),academicYear);
		List<EmpLeaveTO> empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		
		return empLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getAplyLeavesWithFingerPrintIdOrEmpCode(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="";
		String name="";
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		List<EmpApplyLeaveTO> applyLeaveTo=null;
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			mode="fingerPrint";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
			if(month==6 && curMonth < month){
				year=year-1;
		     }
		List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithFingerPrintId(Integer.parseInt(employeeApplyLeaveForm.getFingerPrintId()),year);
		 applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty()){
			mode="empCode";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
			if(month==6 && curMonth < month){
				year=year-1;
		     }
			List<EmpApplyLeave> applyLeave=transaction.getApplyLeavesWithEmpCode(employeeApplyLeaveForm.getEmpCode(),year);
			applyLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		}
		return applyLeaveTo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpLeaveTO> getEmpLeavesWithFingerPrintIdOrEmpCode(EmployeeApplyLeaveForm employeeApplyLeaveForm)throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		//int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		int academicYear=Calendar.getInstance().get(Calendar.YEAR);
		List<EmpLeaveTO> empLeaveTo=null;
		String mode="";
		String name="";
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(employeeApplyLeaveForm.getFingerPrintId()!=null && !employeeApplyLeaveForm.getFingerPrintId().isEmpty()){
			mode="fingerPrint";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getFingerPrintId(),mode,name);
			
			if(month==6 && curMonth < month){
			      academicYear=academicYear-1;
		     }
			List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getFingerPrintId(),academicYear,mode);
			empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		}else if(employeeApplyLeaveForm.getEmpCode()!=null && !employeeApplyLeaveForm.getEmpCode().isEmpty())
		{   
			mode="empCode";
			int month=transaction.getInitializeMonth(employeeApplyLeaveForm.getEmpCode(),mode,name);
			if(month==6 && curMonth < month){
			      academicYear=academicYear-1;
		     }
			List<EmpLeave> empLeave=transaction.getEmpLeaves(employeeApplyLeaveForm.getEmpCode(),academicYear,mode);
			empLeaveTo=EmployeeApplyLeaveHelper.getInstance().convertEmpLeaveBOtoTO(empLeave,employeeApplyLeaveForm);
		}
		return empLeaveTo;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String,String> getLeaveTypeMap()throws Exception{
		IEmployeeApplyLeaveTransaction transaction=EmployeeApplyLeaveTransactionImpl.getInstance();
		return transaction.getLeaveTypeMap();
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getEmpApproveLeaves(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeOnlineLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			year=year-1;
	     }
		List<EmpOnlineLeave> applyLeave=transaction.getEmpApproveLeaves(Integer.parseInt(employeeOnlineLeaveForm.getUserId()),year);
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHelper.getInstance().convertApplyLeaveBOtoTO(applyLeave);
		return applyLeaveTo;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @param request 
	 * @throws Exception 
	 */
	public void sendMailToApprover(EmployeeOnlineLeaveForm employeeOnlineLeaveForm, HttpServletRequest request) throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			
		}
		String subject="Leave Application";
		String message = "";
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_LEAVE_MAIL);
		if(list != null && !list.isEmpty()) {
			String desc = list.get(0).getTemplateDescription();
			message = desc;
			if(employeeOnlineLeaveForm.getEmployeeName() != null){
				message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,employeeOnlineLeaveForm.getEmployeeName());
			}
			if(employeeOnlineLeaveForm.getFingerPrintId() != null){
				message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,employeeOnlineLeaveForm.getFingerPrintId());
			}
			Map<Integer, String> leaveTypes = (Map<Integer, String>) request.getSession().getAttribute("leaveTypes");
			String leaveType = leaveTypes.get(Integer.parseInt(employeeOnlineLeaveForm.getLeaveTypeId()));
			if(leaveType != null){
				message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_LEAVETYPE,leaveType);
			}
		}
		String toAddress = employeeOnlineLeaveForm.getApproverMailId();
		String fromAddress = employeeOnlineLeaveForm.getEmployeeEmailId();
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		sendMail(toAddress, subject, message,fromAddress,fromName);
	}
	/**
	 * Common Send mail
	 * @param fromAddress 
	 * @param fromName 
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID,String sub,String message, String fromAddress, String fromName) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream inStr = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inStr);
			} catch (FileNotFoundException e) {	
				log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
				String adminmail=fromAddress;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(adminmail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(fromName);
				//uses JMS 
//				sent=CommonUtil.postMail(mailto);
				if(adminmail != null && toAddress != null){
					sent=CommonUtil.sendMail(mailto);
				}
			return sent;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEmpLeave(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		boolean isApprove = false;
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		// code changed by sudhir
		/*List<EmpApplyLeave> approveList = EmployeeOnlineLeaveHelper.getInstance().convertApplyLeaveTOtoBO(leaveList,employeeOnlineLeaveForm);
		if(approveList != null && !approveList.isEmpty()){
				ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
				Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();
				isApprove =  transaction.saveAndApproveEmployeeLeave(approveList,employeeOnlineLeaveForm,getCommonLeaves(),monthMap);
		}*/
		List<Integer> approveIds = new ArrayList<Integer>();
		if(leaveList!=null && !leaveList.toString().isEmpty()){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator .next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					approveIds.add(empApplyLeaveTO.getId());
				}
			}
			employeeOnlineLeaveForm.setApprovedList(approveIds);
		}
		isApprove = transaction .approveEmployeeLeave(employeeOnlineLeaveForm);
		//
		return isApprove;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployeeMap() throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		Map<Integer, String> empMap = transaction.getEmployeeMap();
		return empMap;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	public void updateEmpOnlineLeave(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		List<Integer> forwardList = new ArrayList<Integer>();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					forwardList.add(empApplyLeaveTO.getId());
				}
			}
		}
		transaction.updateEmpOnlineLeave(forwardList,employeeOnlineLeaveForm);
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	public boolean sendMailToLeaveApprover(EmployeeOnlineLeaveForm employeeOnlineLeaveForm)  throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			throw new Exception();
		}
		String subject="Leave Application";
		String message = employeeOnlineLeaveForm.getMailBody();
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		String adminmail = employeeOnlineLeaveForm.getApproverMailId();
		return sendMail(adminmail, subject, message, fromAddress, fromName);
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendReturnMailTOEmployee(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			throw new Exception();
		}
		boolean send = false;
		String subject="Leave Application";
		String message = "";
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_RETURN_LEAVE_MAIL);
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					if(list != null && !list.isEmpty()) {
						String desc = list.get(0).getTemplateDescription();
						message = desc;
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFirstName() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,empApplyLeaveTO.getEmployee().getFirstName());
						}
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFingerPrintId() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,empApplyLeaveTO.getEmployee().getFingerPrintId());
						}
						message = message.replace(CMSConstants.TEMPLATE_REASON,employeeOnlineLeaveForm.getRejectedReason());
					}
					if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getWorkEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getWorkEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}else if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getOtherEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getOtherEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}
				}
			}
		}
		return send;
	}
	
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	public void rejectEmpOnlineLeave(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		List<Integer> forwardList = new ArrayList<Integer>();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					forwardList.add(empApplyLeaveTO.getId());
				}
			}
		}
		transaction.rejectEmpOnlineLeave(forwardList,employeeOnlineLeaveForm);
		
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	public void sendMailToEmployees(EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			
		}
		String subject="Leave Application";
		String message = "";
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_APPLY_LEAVE_MAIL);
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					if(list != null && !list.isEmpty()) {
						String desc = list.get(0).getTemplateDescription();
						message = desc;
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFirstName() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,empApplyLeaveTO.getEmployee().getFirstName());
						}
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFingerPrintId() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,empApplyLeaveTO.getEmployee().getFingerPrintId());
						}
					}
					if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getWorkEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getWorkEmail();
						sendMail(adminmail, subject, message,fromAddress,fromName);
					}else if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getOtherEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getOtherEmail();
						sendMail(adminmail, subject, message,fromAddress,fromName);
					}
				}
			}
		}
	}
	/**
	 * @param employeeId
	 * @return
	 * @throws Exception
	 */
	public Employee getEmployee(String employeeId) throws Exception {
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		return transaction.getEmployee(employeeId);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getEmployeeLeaveTypeMap() throws Exception{
		Map<Integer, String> map = new HashMap<Integer, String>();
		String 	query="select e.id,e.name from EmpLeaveType e where e.isActive=1 and e.canapplyonline=1";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> list=transaction.getDataForQuery(query);
		if(list != null){
			Iterator<Object[]> iterator = list.iterator();
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				if(objects[0] != null && objects[1] != null && objects[0].toString() != null && objects[1].toString() != null){
					map.put(Integer.parseInt(objects[0].toString()), objects[1].toString());
				}
			}
		}
		return map;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getEmpAuthorizationLeaves( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		String mode="userId";
		String name="";
		int month=transaction.getInitializeMonth(employeeOnlineLeaveForm.getUserId(),mode,name);
		int curMonth=EmployeeInfoEditHelper.currentMonth();
		if(month==6 && curMonth < month){
			year=year-1;
	     }
		List<EmpOnlineLeave> applyLeave=transaction.getEmpAuthorizationLeaves(Integer.parseInt(employeeOnlineLeaveForm.getUserId()),year);
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(applyLeave);
		return applyLeaveTo;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEmpAuthorizedLeave( EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception {
		boolean isApprove = false;
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		List<EmpApplyLeave> approveList = EmployeeOnlineLeaveHelper.getInstance().convertApplyLeaveTOtoBO(leaveList,employeeOnlineLeaveForm);
		if(!approveList.isEmpty()){
				ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
				Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();
				isApprove =  transaction.saveAndApproveEmployeeLeave(approveList,employeeOnlineLeaveForm,getCommonLeaves(),monthMap);
		}
		return isApprove;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @throws Exception
	 */
	public void sendAuthorizationMailToEmployees( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception {
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			
		}
		String subject="Leave Application";
		String message = "";
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_APPLY_AUTHORIZED_LEAVE_MAIL);
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					if(list != null && !list.isEmpty()) {
						String desc = list.get(0).getTemplateDescription();
						message = desc;
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFirstName() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,empApplyLeaveTO.getEmployee().getFirstName());
						}
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFingerPrintId() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,empApplyLeaveTO.getEmployee().getFingerPrintId());
						}
					}
					if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getWorkEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getWorkEmail();
						sendMail(adminmail, subject, message,fromAddress,fromName);
					}else if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getOtherEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getOtherEmail();
						sendMail(adminmail, subject, message,fromAddress,fromName);
					}
				}
			}
		}
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean sendReturnMail( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			throw new Exception();
		}
		boolean send = false;
		String subject="Leave Application";
		String message = "";
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_AUTHORIZATION_RETURN_LEAVE_MAIL);
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					if(list != null && !list.isEmpty()) {
						String desc = list.get(0).getTemplateDescription();
						message = desc;
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFirstName() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,empApplyLeaveTO.getEmployee().getFirstName());
						}
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFingerPrintId() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,empApplyLeaveTO.getEmployee().getFingerPrintId());
						}
						message = message.replace(CMSConstants.TEMPLATE_REASON,employeeOnlineLeaveForm.getRejectedReason());
					}
					if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getWorkEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getWorkEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}else if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getOtherEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getOtherEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}
				}
			}
		}
		return send;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 */
	public boolean sendRequestDocMailToEmployee( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
			throw new Exception();
		}
		boolean send = false;
		String subject="Leave Application";
		String message = "";
		String fromName=employeeOnlineLeaveForm.getEmployeeName();
		String fromAddress =employeeOnlineLeaveForm.getEmployeeEmailId();
		TemplateHandler temphandle=TemplateHandler.getInstance();
		List<GroupTemplate> list= temphandle.getDuplicateCheckList(0,CMSConstants.EMPLOYEE_AUTHORIZATION_REQUEST_DOC_LEAVE_MAIL);
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					if(list != null && !list.isEmpty()) {
						String desc = list.get(0).getTemplateDescription();
						message = desc;
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFirstName() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,empApplyLeaveTO.getEmployee().getFirstName());
						}
						if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getFingerPrintId() != null){
							message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_FINGETPRINTID,empApplyLeaveTO.getEmployee().getFingerPrintId());
						}
						message = message.replace(CMSConstants.TEMPLATE_REASON,employeeOnlineLeaveForm.getRequestDocReason());
					}
					if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getWorkEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getWorkEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}else if(empApplyLeaveTO.getEmployee() != null && empApplyLeaveTO.getEmployee().getOtherEmail() != null){
						String adminmail = empApplyLeaveTO.getEmployee().getOtherEmail();
						send = sendMail(adminmail, subject, message,fromAddress,fromName);
					}
				}
			}
		}
		return send;
	}
	/**
	 * @param employeeOnlineLeaveForm
	 */
	public void requestDocEmpLeave( EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception {
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		List<Integer> forwardList = new ArrayList<Integer>();
		if(leaveList != null){
			Iterator<EmpApplyLeaveTO> iterator = leaveList.iterator();
			while (iterator.hasNext()) {
				EmpApplyLeaveTO empApplyLeaveTO = (EmpApplyLeaveTO) iterator.next();
				if(empApplyLeaveTO.getChecked() != null && empApplyLeaveTO.getChecked().equalsIgnoreCase("on")){
					forwardList.add(empApplyLeaveTO.getId());
				}
			}
		}
		transaction.requestDocEmpLeave(forwardList,employeeOnlineLeaveForm);
		
	}
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeaveTO> getViewReturnedReqDoc( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpOnlineLeave> applyLeave=transaction.getViewReturnedReqDoc(Integer.parseInt(employeeOnlineLeaveForm.getUserId()));
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(applyLeave);
		return applyLeaveTo;
	}
	
	public List<EmpApplyLeaveTO> getViewReturnedReqDocForApproval( EmployeeOnlineLeaveForm employeeOnlineLeaveForm) throws Exception{
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpOnlineLeave> applyLeave=transaction.getViewReturnedReqDocForApproval(Integer.parseInt(employeeOnlineLeaveForm.getUserId()));
		List<EmpApplyLeaveTO> applyLeaveTo=EmployeeOnlineLeaveHelper.getInstance().convertEmpOnlineLeaveBoTOTo(applyLeave);
		return applyLeaveTo;
	}
	
	/**
	 * @param employeeOnlineLeaveForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveEmpApproveLeave( EmployeeOnlineLeaveForm employeeOnlineLeaveForm)throws Exception {
		boolean isApprove = false;
		IEmployeeOnlineLeaveTransaction transaction=EmployeeOnlineLeaveTransactionImpl.getInstance();
		List<EmpApplyLeaveTO> leaveList = employeeOnlineLeaveForm.getApplyLeaveTo();
		List<EmpApplyLeave> approveList = EmployeeOnlineLeaveHelper.getInstance().convertApplyLeaveTOtoBO(leaveList,employeeOnlineLeaveForm);
		if(!approveList.isEmpty()){
				ILeaveInitializationTransaction transaction2=LeaveInitializationTransactionImpl.getInstance();
				Map<Integer,String> monthMap=transaction2.getMonthByEmployeeType();
				isApprove =  transaction.saveAndApproveReturnedLeaves(approveList,employeeOnlineLeaveForm,getCommonLeaves(),monthMap);
		}
		return isApprove;
	}
	
	
}
