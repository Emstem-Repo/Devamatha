package com.kp.cms.handlers.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpAttendance;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.ViewMyAttendanceForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.employee.ViewMyAttendanceHelper;
import com.kp.cms.to.admin.EmpAttendanceTo;
import com.kp.cms.transactions.employee.IViewMyAttendancetransaction;
import com.kp.cms.transactionsimpl.employee.ViewMyAttendanceTransactionimpl;

public class ViewMyAttendanceHandler {
	/**
	 * Singleton object of ViewMyAttendanceHandler
	 */
	private static volatile ViewMyAttendanceHandler viewMyAttendanceHandler = null;
	private static final Log log = LogFactory.getLog(ViewMyAttendanceHandler.class);
	private ViewMyAttendanceHandler() {
		
	}
	/**
	 * return singleton object of ViewMyAttendanceHandler.
	 * @return
	 */
	public static ViewMyAttendanceHandler getInstance() {
		if (viewMyAttendanceHandler == null) {
			viewMyAttendanceHandler = new ViewMyAttendanceHandler();
		}
		return viewMyAttendanceHandler;
	}
	/**
	 * passing the form to impl to get the AttendanceTO
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpAttendanceTo> getEmployeeAttendance(ViewMyAttendanceForm viewAttForm) throws Exception{
		String query=ViewMyAttendanceHelper.getInstance().getQuery(viewAttForm);
		IViewMyAttendancetransaction txn=ViewMyAttendanceTransactionimpl.getInstance();
		List<EmpAttendance> empAttBo=txn.getEmpAttendanceBO(query);
		
		if(!viewAttForm.getViewEmpAttendance()){
			// added by nagarjuna
			Employee emp = txn.getEmployeeDetails(Integer.parseInt(viewAttForm.getUserId()),viewAttForm,"User");
			if(emp != null){
				if(emp.getFingerPrintId() !=null && !emp.getFingerPrintId().isEmpty())
					viewAttForm.setFingerPrintId(emp.getFingerPrintId());
				else 
					viewAttForm.setFingerPrintId("");
				if(emp.getCode() !=null  && !emp.getCode().isEmpty()){
					viewAttForm.setEmpCode(emp.getCode());
				}else
					viewAttForm.setEmpCode("");
				if(emp.getFirstName()!=null && !emp.getFirstName().isEmpty())
					viewAttForm.setEmpName(emp.getFirstName());
				else 
					viewAttForm.setEmpName("");
				if(emp.getDepartment() != null && emp.getDepartment().getId() !=0){
					viewAttForm.setDepartmentId(String.valueOf(emp.getDepartment().getId()));
				}
				viewAttForm.setTeachingStaff(emp.getTeachingStaff()!=null?String.valueOf(emp.getTeachingStaff()):"");
			}
			// commented by nagarjun
			/*String fingerPrintId=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(viewAttForm.getUserId()),"Users", true, "employee.fingerPrintId");
			if(fingerPrintId!=null && !fingerPrintId.equalsIgnoreCase("null"))
			viewAttForm.setFingerPrintId(fingerPrintId);
			else viewAttForm.setFingerPrintId("");
			String empCode=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(viewAttForm.getUserId()),"Users", true, "employee.code");
			if(empCode!=null  && !empCode.equalsIgnoreCase("null")){
				viewAttForm.setEmpCode(empCode);
				}
			else viewAttForm.setEmpCode("");
			String empName=NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(viewAttForm.getUserId()),"Users", true, "employee.firstName");
			if(empName!=null && !empName.equalsIgnoreCase("null"))
			viewAttForm.setEmpName(empName);
			else viewAttForm.setEmpName("");*/
		}else {
			// this code modified by nagarjuna 
			Employee emp = txn.getEmployeeDetails(Integer.parseInt(viewAttForm.getUserId()),viewAttForm,"Others");
			if(emp != null){
				viewAttForm.setEmpName(emp.getFirstName()!=null?emp.getFirstName():"");
				viewAttForm.setDepartmentId(emp.getDepartment() != null?String.valueOf(emp.getDepartment().getId()):"");
				viewAttForm.setTeachingStaff(emp.getTeachingStaff()!=null?String.valueOf(emp.getTeachingStaff()):"");
			}
		}
		List<EmpAttendanceTo> empAttTo=ViewMyAttendanceHelper.getInstance().convertBOtoTOs(empAttBo,viewAttForm);
		return empAttTo;
	}
	/**
	 * passing the form to get the leave details of logged in employee
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeave> getEmployeeLeaveDetails(ViewMyAttendanceForm viewAttForm) throws Exception{
		String query=ViewMyAttendanceHelper.getQueryForLeave(viewAttForm);
		IViewMyAttendancetransaction txn=ViewMyAttendanceTransactionimpl.getInstance();
		List<EmpApplyLeave> empLeaveList=txn.getEmpLeaveList(query);
	//	List<EmpApplyLeaveTO> empLeaveListTo=ViewMyAttendanceHelper.getInstance().convertBOtoTOLeave(empLeaveList);
		return empLeaveList;
	}
	/**
	 * method to get the previous month attendance from impl
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpAttendanceTo> getEmployeePreviousMonthAttendance(ViewMyAttendanceForm viewAttForm) throws Exception{
		
		String query=ViewMyAttendanceHelper.getInstance().getQueryForPreviousAttendance(viewAttForm);
		IViewMyAttendancetransaction txn=ViewMyAttendanceTransactionimpl.getInstance();
		List<EmpAttendance> empAttBo=txn.getEmpAttendanceBO(query);
		List<EmpAttendanceTo> empAttTo=ViewMyAttendanceHelper.getInstance().convertBOtoTOs(empAttBo,viewAttForm);
		return empAttTo;
	}
	/**
	 * method to get previous month leave details of employee
	 * @param viewAttForm
	 * @return
	 * @throws Exception
	 */
	public List<EmpApplyLeave> getEmployeePreviousLeaveDetails(ViewMyAttendanceForm viewAttForm) throws Exception {
		String query=ViewMyAttendanceHelper.getQueryForPreviousLeave(viewAttForm);
		IViewMyAttendancetransaction txn=ViewMyAttendanceTransactionimpl.getInstance();
		List<EmpApplyLeave> empLeaveList=txn.getEmpLeaveList(query);
	//	List<EmpApplyLeaveTO> empLeaveListTo=ViewMyAttendanceHelper.getInstance().convertBOtoTOLeave(empLeaveList);
		return empLeaveList;
	}
}
