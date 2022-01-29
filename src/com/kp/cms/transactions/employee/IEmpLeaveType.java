package com.kp.cms.transactions.employee;

import java.util.Map;

import com.kp.cms.bo.admin.EmpLeaveType;

public interface IEmpLeaveType {

	Map<String,EmpLeaveType> getEmpLeaveDetails()throws Exception;

	Map<String, EmpLeaveType> getEmpLeaveNamesMap()throws Exception;

	boolean saveLeaveType(EmpLeaveType empLeaveType)throws Exception;

}
