package com.kp.cms.transactions.employee;

import java.util.List;

import com.kp.cms.bo.admin.EmpExceptionDetailsBO;
import com.kp.cms.bo.admin.EmpExceptionDetailsDates;

public interface IExceptionDetailsTransaction {

public 	boolean addExceptionDetails(EmpExceptionDetailsBO convertFormTOToBO)throws Exception;
public List<EmpExceptionDetailsBO> getExceptionDetails() throws Exception;
public boolean deleteExceptionDetails(int id,String userId)throws Exception;
public boolean reActivateExceptionDetails(int id, String userId) throws Exception;
public EmpExceptionDetailsBO getExceptionDetailsOnId(int id) throws Exception;
public boolean updateExceptionDetails(EmpExceptionDetailsBO exceptionDetailsBo, List<EmpExceptionDetailsDates> list) throws Exception;
public EmpExceptionDetailsBO duplicateCheckException(String query) throws Exception;
}
