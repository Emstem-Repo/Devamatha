package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpExceptionTypeBO;
import com.kp.cms.forms.employee.ExceptionTypesForm;

public interface IExceptionTypesTransaction {

	boolean addAttendance(EmpExceptionTypeBO bo,String mode)throws Exception;

	List<EmpExceptionTypeBO> getExceptionTypes()throws Exception;

	void isDuplicated(ExceptionTypesForm exForm,String exceptionType,String exceptionShortName, Integer id)throws Exception;

	boolean deleteExceptionTypes(int id, String userId) throws Exception;

	EmpExceptionTypeBO getException(int id) throws Exception;

	boolean reactivate(int oldId, String userId) throws Exception;

}
