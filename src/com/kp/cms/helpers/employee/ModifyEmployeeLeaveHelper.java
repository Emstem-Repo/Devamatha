package com.kp.cms.helpers.employee;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpApplyLeave;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.employee.EmployeeApplyLeaveForm;
import com.kp.cms.forms.employee.ModifyEmployeeLeaveForm;
import com.kp.cms.utilities.CommonUtil;

public class ModifyEmployeeLeaveHelper {
	/**
	 * Singleton object of EmployeeApplyLeaveHelper
	 */
	private static volatile ModifyEmployeeLeaveHelper modifyEmployeeLeaveHelper = null;
	private static final Log log = LogFactory.getLog(ModifyEmployeeLeaveHelper.class);
	private ModifyEmployeeLeaveHelper() {
		
	}
	/**
	 * return singleton object of EmployeeApplyLeaveHelper.
	 * @return
	 */
	public static ModifyEmployeeLeaveHelper getInstance() {
		if (modifyEmployeeLeaveHelper == null) {
			modifyEmployeeLeaveHelper = new ModifyEmployeeLeaveHelper();
		}
		return modifyEmployeeLeaveHelper;
	}
	
	
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public EmpApplyLeave convertFormToBO(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm,double daysDiff,int year) throws Exception {
		EmpApplyLeave bo=new EmpApplyLeave();
		bo.setId(modifyEmployeeLeaveForm.getApplyLeaveId());
		if(modifyEmployeeLeaveForm.getIsExemption() != null ){
			if(modifyEmployeeLeaveForm.getIsExemption().equalsIgnoreCase("yes")){
				bo.setIsExemption(true);
			}else if(modifyEmployeeLeaveForm.getIsExemption().equalsIgnoreCase("no")){
				bo.setIsExemption(false);
			}
		}
		bo.setCreatedBy(modifyEmployeeLeaveForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setReason(modifyEmployeeLeaveForm.getReason());
		Employee employee=new Employee();
		employee.setId(Integer.parseInt(modifyEmployeeLeaveForm.getEmployeeId()));
		bo.setEmployee(employee);
		bo.setFromDate(CommonUtil.ConvertStringToSQLDate(modifyEmployeeLeaveForm.getStartDate()));
		bo.setToDate(CommonUtil.ConvertStringToSQLDate(modifyEmployeeLeaveForm.getEndDate()));
		bo.setIsActive(true);
		bo.setIsCanceled(false);
		bo.setNoOfDays(daysDiff);
		bo.setYear(year);
		EmpLeaveType empLeaveType=new EmpLeaveType();
		empLeaveType.setId(Integer.parseInt(modifyEmployeeLeaveForm.getLeaveTypeId()));
		bo.setEmpLeaveType(empLeaveType);
		if(modifyEmployeeLeaveForm.getStartDate().equalsIgnoreCase(modifyEmployeeLeaveForm.getEndDate())){
			if(modifyEmployeeLeaveForm.getIsHalfday()!=null && !modifyEmployeeLeaveForm.getIsHalfday().isEmpty()){
				if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					bo.setIsHalfDay(true);
					if(modifyEmployeeLeaveForm.getIsAm().equalsIgnoreCase("am")){
						bo.setIsAm("AM");
					}else
						bo.setIsAm("PM");
				}else{
					bo.setIsAm("");
					bo.setIsHalfDay(false);
				}
			}
		}else{
			bo.setIsAm("");
			bo.setIsHalfDay(false);
		}
		return bo;
	}
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public EmpApplyLeave convertFormToBOForCancel(ModifyEmployeeLeaveForm modifyEmployeeLeaveForm,double daysDiff,int year) throws Exception {
		EmpApplyLeave bo=new EmpApplyLeave();
		bo.setId(modifyEmployeeLeaveForm.getApplyLeaveId());
		if(modifyEmployeeLeaveForm.getIsExemption() != null ){
			if(modifyEmployeeLeaveForm.getIsExemption().equalsIgnoreCase("yes")){
				bo.setIsExemption(true);
			}else if(modifyEmployeeLeaveForm.getIsExemption().equalsIgnoreCase("no")){
				bo.setIsExemption(false);
			}
		}
		bo.setCreatedBy(modifyEmployeeLeaveForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setModifiedBy(modifyEmployeeLeaveForm.getUserId());
		bo.setLastModifiedDate(new Date());
		bo.setReason(modifyEmployeeLeaveForm.getReason());
		Employee employee=new Employee();
		employee.setId(Integer.parseInt(modifyEmployeeLeaveForm.getEmployeeId()));
		bo.setEmployee(employee);
		bo.setFromDate(CommonUtil.ConvertStringToSQLDate(modifyEmployeeLeaveForm.getStartDate()));
		bo.setToDate(CommonUtil.ConvertStringToSQLDate(modifyEmployeeLeaveForm.getEndDate()));
		bo.setIsActive(false);
		bo.setIsCanceled(true);
		bo.setNoOfDays(daysDiff);
		bo.setYear(year);
		EmpLeaveType empLeaveType=new EmpLeaveType();
		empLeaveType.setId(Integer.parseInt(modifyEmployeeLeaveForm.getLeaveTypeId()));
		bo.setEmpLeaveType(empLeaveType);
		if(modifyEmployeeLeaveForm.getStartDate().equalsIgnoreCase(modifyEmployeeLeaveForm.getEndDate())){
			if(modifyEmployeeLeaveForm.getIsHalfday()!=null && !modifyEmployeeLeaveForm.getIsHalfday().isEmpty()){
				if(modifyEmployeeLeaveForm.getIsHalfday().equalsIgnoreCase("yes")){
					bo.setIsHalfDay(true);
					if(modifyEmployeeLeaveForm.getIsAm().equalsIgnoreCase("am")){
						bo.setIsAm("AM");
					}else
						bo.setIsAm("PM");
				}else{
					bo.setIsAm("");
					bo.setIsHalfDay(false);
				}
			}
		}
		return bo;
	}
	
	/**
	 * @param employeeApplyLeaveForm
	 * @return
	 * @throws Exception
	 */
	public String getSumOfLeavesTakenQuery(ModifyEmployeeLeaveForm employeeApplyLeaveForm) throws Exception{
		String query=" select sum(e.noOfDays) from EmpApplyLeave e where e.isCanceled=0" +
				" and e.employee.id="+employeeApplyLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
	
	public String getAlreadyExistsQuery(ModifyEmployeeLeaveForm employeeApplyLeaveForm) throws Exception{
		String query="from EmpApplyLeave e where e.isCanceled=0 " +
				" and e.employee.id="+employeeApplyLeaveForm.getEmployeeId()+
				" and ((('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') between e.fromDate and e.toDate )" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') between e.fromDate and e.toDate)" +
				" or (('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getStartDate())+"') <= e.fromDate " +
				" and ('"+CommonUtil.ConvertStringToSQLDate(employeeApplyLeaveForm.getEndDate())+"') >= e.toDate )) ";
		return query;
	}
}
