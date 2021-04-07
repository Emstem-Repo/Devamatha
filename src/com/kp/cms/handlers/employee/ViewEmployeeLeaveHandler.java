package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.helpers.employee.ViewEmployeeLeaveHelper;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactions.employee.IViewEmployeeLeaveTransaction;
import com.kp.cms.transactionsimpl.employee.ViewEmployeeLeaveTransactionImpl;

public class ViewEmployeeLeaveHandler {
	private static final Log log = LogFactory
			.getLog(ViewEmployeeLeaveHandler.class);
	public static volatile ViewEmployeeLeaveHandler viewEmployeeLeaveHandler = null;

	public static ViewEmployeeLeaveHandler getInstance() {
		if (viewEmployeeLeaveHandler == null) {
			viewEmployeeLeaveHandler = new ViewEmployeeLeaveHandler();
			return viewEmployeeLeaveHandler;
		}
		return viewEmployeeLeaveHandler;
	}

	public Map<Integer, String> getEmployeeNmaes() throws Exception {
		log.debug("inside getEmployeeNmaes");
		Map<Integer, String> list = ViewEmployeeLeaveHelper.getInstance()
				.getEmployeeDetails();
		log.debug("leaving getEmployeeNmaes");
		return list;
	}

	public List<EmployeeLeaveTO> getEmployeeNmaes(String academicYear,
			String employeeId) throws Exception{
		IViewEmployeeLeaveTransaction iTransaction = ViewEmployeeLeaveTransactionImpl.getInstance();
		
		 List<Object[]> listEmployees=iTransaction.getEmployeeDetails(academicYear,employeeId);
		return ViewEmployeeLeaveHelper.getInstance().convertBOToTO(listEmployees);
	}
}
