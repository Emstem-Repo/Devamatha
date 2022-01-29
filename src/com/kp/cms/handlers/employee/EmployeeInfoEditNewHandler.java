package com.kp.cms.handlers.employee;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.employee.GuestFaculty;
import com.kp.cms.forms.employee.EmployeeInfoEditNewForm;
import com.kp.cms.helpers.employee.EmployeeEditInfoNewHelper;
import com.kp.cms.helpers.employee.EmployeeInfoEditHelper;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.transactions.employee.IEmployeeEditInfoNewTransaction;
import com.kp.cms.transactions.employee.IEmployeeInfoEditTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeEditInfoNewTransactionImpl;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoEditTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class EmployeeInfoEditNewHandler {
	private static final Log log = LogFactory.getLog(EmployeeInfoEditHandler.class);
	IEmployeeEditInfoNewTransaction empTransaction=EmployeeEditInfoNewTransactionImpl.getInstance();
	
	private static volatile EmployeeInfoEditNewHandler instance=null;
	
	/**
	 * 
	 */
	private EmployeeInfoEditNewHandler(){
		
	}
	
	/**
	 * @return
	 */
	public static EmployeeInfoEditNewHandler getInstance(){
		log.info("Start getInstance of EmployeeInfoEditHandler");
		if(instance==null){
			instance=new EmployeeInfoEditNewHandler();
		}
		log.info("End getInstance of EmployeeInfoEditHandler");
		return instance;
	}

	public List<EmployeeTO> getSearchedEmployeeNew(Integer eid, Integer rid2,EmployeeInfoEditNewForm stForm, int principleid, int adminid,
			int testid) throws Exception {
		IEmployeeEditInfoNewTransaction  txn = new EmployeeEditInfoNewTransactionImpl();
		EmployeeEditInfoNewHelper helper= EmployeeEditInfoNewHelper.getInstance();
		
		
		int designationId = 0;
		int departmentId = 0;
		int streamId = 0;
		int empTypeId=0;
			
		if (stForm.getDepartmentId() != null
				&& !StringUtils.isEmpty(stForm.getDepartmentId().trim())
				&& StringUtils.isNumeric(stForm.getDepartmentId())) {
			departmentId = Integer.parseInt(stForm.getDepartmentId());
		}
		if (stForm.getDesignationId() != null
				&& !StringUtils.isEmpty(stForm.getDesignationId().trim())
				&& StringUtils.isNumeric(stForm.getDesignationId())) {
			designationId = Integer.parseInt(stForm.getDesignationId());
		}
		
		if (stForm.getEmpTypeId() != null
				&& !StringUtils.isEmpty(stForm.getEmpTypeId().trim())
				&& StringUtils.isNumeric(stForm.getEmpTypeId())) {
			empTypeId = Integer.parseInt(stForm.getEmpTypeId());
		}
		StringBuffer query = txn.getSerchedEmployeeQuery( departmentId , designationId,   streamId, empTypeId,eid,rid2,principleid,adminid,testid,stForm.getTempTeachingStaff());
		List<Employee> employeelist=txn.getSerchedEmployee(query);
		

		
		List<EmployeeTO> employeeToList = helper.convertEmployeeTOtoBO(employeelist, departmentId , designationId);
		log.info("exit getSearchedStudents");
		return employeeToList;
		
	}

	public boolean getApplicantDetails(EmployeeInfoEditNewForm objform) throws Exception {
		IEmployeeEditInfoNewTransaction  txn = new EmployeeEditInfoNewTransactionImpl();
		boolean flag=false;
		Users userDetails = txn.getUser(objform);
		if(userDetails != null){
			if(userDetails.getEmployee() != null){
				int employeeId = userDetails.getEmployee().getId();
				Employee empApplicantDetails=txn.GetEmpDetails(employeeId);
		if (empApplicantDetails != null) {
			flag=true;
			EmployeeEditInfoNewHelper.getInstance().convertBoToForm(empApplicantDetails,objform);
		}
			}else if(userDetails.getGuest() != null){
				int guestId = userDetails.getGuest().getId();
				GuestFaculty empApplicantDetails = txn.getGuestDetails(guestId);
				if(empApplicantDetails != null){
					flag = true;
					EmployeeEditInfoNewHelper.getInstance().convertB(empApplicantDetails, objform);
				}
			}
		}
		return flag;
	}

	public boolean saveEmp(EmployeeInfoEditNewForm employeeInfoEditNewForm) throws Exception {
		
		boolean flag=false;
		boolean flag1=false;
		if(employeeInfoEditNewForm.getEmployeeId() != null && !employeeInfoEditNewForm.getEmployeeId().isEmpty() 
				&& Integer.parseInt(employeeInfoEditNewForm.getEmployeeId()) > 0){
		Employee employee=EmployeeEditInfoNewHelper.getInstance().convertFormToBo(employeeInfoEditNewForm);
		flag=empTransaction.saveEmployee(employee,employeeInfoEditNewForm);
		}
		if(employeeInfoEditNewForm.getGuestId() != null && ! employeeInfoEditNewForm.getGuestId().isEmpty()
				&& Integer.parseInt(employeeInfoEditNewForm.getGuestId()) >0){
			GuestFaculty guest = EmployeeEditInfoNewHelper.getInstance().convertFormToBoGuest(employeeInfoEditNewForm);
			flag = empTransaction.saveGuestData(guest,employeeInfoEditNewForm);
		}
		if(employeeInfoEditNewForm.getEmployeeId()!=null)
		{
			if(employeeInfoEditNewForm.getActive()!=null && employeeInfoEditNewForm.getActive().equals("0"))
			{
			Users user = empTransaction.userExists(employeeInfoEditNewForm) ;
			if(user!=null)
			{
			user.setActive(false);
			user.setLastModifiedDate(new Date());
			user.setModifiedBy(employeeInfoEditNewForm.getUserId());
			
			flag1=empTransaction.updateUser(user);
			}	
			}
			else if(employeeInfoEditNewForm.getActive()!=null && employeeInfoEditNewForm.getActive().equals("1"))
			{
				Users user = empTransaction.userExists(employeeInfoEditNewForm) ;
				if(user!=null)
				{
				user.setActive(true);
				user.setLastModifiedDate(new Date());
				user.setModifiedBy(employeeInfoEditNewForm.getUserId());
				
				flag1=empTransaction.updateUser(user);
					
				}
			}
			}
		if(employeeInfoEditNewForm.getGuestId()!=null)
		{
			if(employeeInfoEditNewForm.getActive()!=null && employeeInfoEditNewForm.getActive().equals("0"))
			{
			Users user = empTransaction.userExists1(employeeInfoEditNewForm) ;
			if(user!=null)
			{
			user.setActive(false);
			user.setLastModifiedDate(new Date());
			user.setModifiedBy(employeeInfoEditNewForm.getUserId());
			
			flag1=empTransaction.updateUser(user);
			}	
			}
			else if(employeeInfoEditNewForm.getActive()!=null && employeeInfoEditNewForm.getActive().equals("1"))
			{
				Users user = empTransaction.userExists1(employeeInfoEditNewForm) ;
				if(user!=null)
				{
				user.setActive(true);
				user.setLastModifiedDate(new Date());
				user.setModifiedBy(employeeInfoEditNewForm.getUserId());
				
				flag1=empTransaction.updateUser(user);
					
				}
			}
			}
		
		
		return flag;
		
	}

	public List<UserInfoTO> getSearchedUsers(String eid1, String guestId) throws Exception {
		String query="select u from Users u left join u.employee e with (e.active=1 and e.isActive=1) left join u.guest g with (g.active=1 and g.isActive=1) where u.isActive=1" ;
		if(eid1!=null && !eid1.isEmpty())
				query=query+ " and u.employee.id="+eid1;
		if(guestId!=null && !guestId.isEmpty())
			query=query+ " and u.guest.id="+guestId;
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Users> list=transaction.getDataForQuery(query);
		return EmployeeEditInfoNewHelper.getInstance().convertBoToFormProperty(list);
	}

	

		
	}
	
	

