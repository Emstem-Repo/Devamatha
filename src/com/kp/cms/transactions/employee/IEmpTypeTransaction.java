package com.kp.cms.transactions.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.ShiftEntry;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.to.employee.ShiftEntryTo;

public interface IEmpTypeTransaction {

	Map<String, EmpType> getEmpTypeMap(EmpTypeForm empTypeForm)throws Exception;

	Boolean addEmpType(EmpType empType)throws Exception;

	Map<String, EmpType> getEmpType()throws Exception;

	Map<Integer, String> getEmployeeMap() throws Exception;

	boolean saveShiftEntry(List<ShiftEntry> entryBos, String mode) throws Exception;

	List<ShiftEntry> getEmpEntryList() throws Exception;

	List<ShiftEntry> getEmpShiftEntryList(String employeeId, String mode) throws Exception;
}
