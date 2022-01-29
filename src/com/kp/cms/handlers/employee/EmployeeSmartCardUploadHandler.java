package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.handlers.admission.SmartCardNumberUploadHandler;
import com.kp.cms.transactions.employee.IEmployeeSmartCardUploadTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeSmartCardTransactionImpl;

public class EmployeeSmartCardUploadHandler {
	private static final Log log = LogFactory.getLog(EmployeeSmartCardUploadHandler.class);
	private static volatile EmployeeSmartCardUploadHandler cardUploadHandler = null;
	public static EmployeeSmartCardUploadHandler getInstance(){
	 if(cardUploadHandler == null){
		 cardUploadHandler = new EmployeeSmartCardUploadHandler();
		 return cardUploadHandler;
	 }
	 return cardUploadHandler;
 }
/**
 * @return
 * @throws Exception
 */
 public Map<String, Employee> getFingerPrintIds() throws Exception {
	IEmployeeSmartCardUploadTransaction transaction = EmployeeSmartCardTransactionImpl.getInstance();
	return transaction.getFingerPrintIds();
}
 /**
 * @param list
 * @param userId
 * @return
 * @throws Exception
 */
public boolean addEmpSmartCardNo(List<Employee> list, String userId) throws Exception {
	log.info("call of addEmpSmartCardNo method in EmployeeSmartCardUploadHandler class.");
	IEmployeeSmartCardUploadTransaction transaction = EmployeeSmartCardTransactionImpl.getInstance();
	boolean isAdded= false;
	isAdded=transaction.addEmpSmartCardNo(list,userId);
	log.info("end of addEmpSmartCardNo method in EmployeeSmartCardUploadHandler class.");
	return isAdded;
}
}
