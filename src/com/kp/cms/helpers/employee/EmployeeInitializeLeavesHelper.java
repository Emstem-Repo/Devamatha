package com.kp.cms.helpers.employee;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpInitializeLeaves;
import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.admin.EmployeeTypeBO;
import com.kp.cms.forms.employee.EmployeeInitializeLeavesForm;
import com.kp.cms.to.employee.EmpInitializeTo;
import com.kp.cms.utilities.CommonUtil;

public class EmployeeInitializeLeavesHelper {
	private static final String FROM_DATEFORMAT="dd/MM/yyyy";
	private static final String SQL_DATEFORMAT="dd-MMM-yyyy";
	/**
	 * Singleton object of EmployeeInitializeLeavesHelper
	 */
	private static volatile EmployeeInitializeLeavesHelper employeeInitializeLeavesHelper = null;
	private static final Log log = LogFactory.getLog(EmployeeInitializeLeavesHelper.class);
	private EmployeeInitializeLeavesHelper() {
		
	}
	/**
	 * return singleton object of EmployeeInitializeLeavesHelper.
	 * @return
	 */
	public static EmployeeInitializeLeavesHelper getInstance() {
		if (employeeInitializeLeavesHelper == null) {
			employeeInitializeLeavesHelper = new EmployeeInitializeLeavesHelper();
		}
		return employeeInitializeLeavesHelper;
	}
	public List<EmpInitializeTo> convertBoListToToList(
			List<EmpInitializeLeaves> list) {
		List<EmpInitializeTo> finalList=new ArrayList<EmpInitializeTo>();
		if(list!=null){
			Iterator<EmpInitializeLeaves> itr=list.iterator();
			while (itr.hasNext()) {
				EmpInitializeLeaves empInitializeLeaves = (EmpInitializeLeaves) itr.next();
				EmpInitializeTo empInitializeTo=new EmpInitializeTo();
				empInitializeTo.setId(empInitializeLeaves.getId());
				empInitializeTo.setAllotedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(empInitializeLeaves.getAllotedDate()), EmployeeInitializeLeavesHelper.SQL_DATEFORMAT,EmployeeInitializeLeavesHelper.FROM_DATEFORMAT));
				empInitializeTo.setIsActive(empInitializeLeaves.getIsActive());
				empInitializeTo.setIsInitializeRequired(empInitializeLeaves.getIsInitializeRequired());
				empInitializeTo.setAllotLeaves(empInitializeLeaves.getAllotLeaves());
				empInitializeTo.setCreatedBy(empInitializeLeaves.getCreatedBy());
				empInitializeTo.setCreatedDate(empInitializeLeaves.getCreatedDate());
				empInitializeTo.setLastModifiedDate(empInitializeLeaves.getLastModifiedDate());
				empInitializeTo.setModifiedBy(empInitializeLeaves.getModifiedBy());
				empInitializeTo.setEmpTypeName(empInitializeLeaves.getEmpTypeId().getName());
				if(empInitializeLeaves.getLeaveType()!=null){
					empInitializeTo.setLeaveTypeName(empInitializeLeaves.getLeaveType().getName());
				}
				finalList.add(empInitializeTo);
			}
		}
		return finalList;
	}
	/**
	 * @param employeeInitializeLeavesForm
	 * @return
	 * @throws Exception
	 */
	public EmpInitializeLeaves convertFormToBo(EmployeeInitializeLeavesForm employeeInitializeLeavesForm) throws Exception {
		EmpInitializeLeaves empInitializeLeaves=new EmpInitializeLeaves();
		if(employeeInitializeLeavesForm.getId()>0){
			empInitializeLeaves.setId(employeeInitializeLeavesForm.getId());
		}
		if(employeeInitializeLeavesForm.getIsInitializeRequired()){
			empInitializeLeaves.setIsInitializeRequired(true);
		}else{
			empInitializeLeaves.setIsInitializeRequired(false);
		}
		if(employeeInitializeLeavesForm.getEmpTypeId()!=null && !employeeInitializeLeavesForm.getEmpTypeId().isEmpty()){
			EmployeeTypeBO employeeTypeBO=new EmployeeTypeBO();
			employeeTypeBO.setId(Integer.parseInt(employeeInitializeLeavesForm.getEmpTypeId()));
			empInitializeLeaves.setEmpTypeId(employeeTypeBO);
		}
		empInitializeLeaves.setAllotedDate(CommonUtil.ConvertStringToSQLDate(employeeInitializeLeavesForm.getAllotedDate()));
		empInitializeLeaves.setIsActive(true);
		empInitializeLeaves.setCreatedBy(employeeInitializeLeavesForm.getUserId());
		empInitializeLeaves.setCreatedDate(new java.util.Date());
		empInitializeLeaves.setModifiedBy(employeeInitializeLeavesForm.getUserId());
		empInitializeLeaves.setLastModifiedDate(new Date());
		empInitializeLeaves.setAllotLeaves(Integer.parseInt(employeeInitializeLeavesForm.getAllotedLeaves()));
		if(employeeInitializeLeavesForm.getLeaveTypeId()!=null && !employeeInitializeLeavesForm.getLeaveTypeId().isEmpty()){
			EmpLeaveType empLeaveType=new EmpLeaveType();
			empLeaveType.setId(Integer.parseInt(employeeInitializeLeavesForm.getLeaveTypeId()));
			empInitializeLeaves.setLeaveType(empLeaveType);
		}
		return empInitializeLeaves;
	}
	
	/**
	 * @param empInitializeLeaves
	 * @param employeeInitializeLeavesForm
	 * @throws Exception
	 */
	public void convertBOToForm(EmpInitializeLeaves empInitializeLeaves,EmployeeInitializeLeavesForm employeeInitializeLeavesForm) throws Exception{
		employeeInitializeLeavesForm.setId(empInitializeLeaves.getId());
		employeeInitializeLeavesForm.setEmpTypeId(Integer.toString(empInitializeLeaves.getEmpTypeId().getId()));
		employeeInitializeLeavesForm.setLeaveTypeId(Integer.toString(empInitializeLeaves.getLeaveType().getId()));
		employeeInitializeLeavesForm.setAllotedLeaves(empInitializeLeaves.getAllotLeaves().toString());
		employeeInitializeLeavesForm.setIsInitializeRequired(empInitializeLeaves.getIsInitializeRequired());
		employeeInitializeLeavesForm.setAllotedDate(CommonUtil.formatSqlDate1(empInitializeLeaves.getAllotedDate().toString()));
	}
}
