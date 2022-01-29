package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.to.admin.SingleFieldMasterTO;
import com.kp.cms.to.employee.EmployeeLeaveTO;
import com.kp.cms.transactionsimpl.employee.ViewEmployeeLeaveTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ViewEmployeeLeaveHelper {
	private static final Log log = LogFactory
			.getLog(ViewEmployeeLeaveHelper.class);
	public static volatile ViewEmployeeLeaveHelper viewEmployeeLeaveHelper = null;

	public static ViewEmployeeLeaveHelper getInstance() {
		if (viewEmployeeLeaveHelper == null) {
			viewEmployeeLeaveHelper = new ViewEmployeeLeaveHelper();
			return viewEmployeeLeaveHelper;
		}
		return viewEmployeeLeaveHelper;
	}

	public Map<Integer, String> getEmployeeDetails() throws Exception {
		Map<Integer, String> listOfEmployees = new HashMap<Integer, String>();
		Object[] employee;
		List<Object[]> listEmployee = ViewEmployeeLeaveTransactionImpl
				.getInstance().getEmployees();
		if (listEmployee != null) {
			Iterator<Object[]> iterator = listEmployee.iterator();
			while (iterator.hasNext()) {
				employee = (Object[]) iterator.next();
				int id = 0;
				if (employee[0] != null
						&& Integer.parseInt(employee[0].toString()) != 0) {
					id = Integer.parseInt(employee[0].toString());
				}
				String name = "";
				if (employee[1] != null) {
					name = employee[1].toString();
				}
				if (employee[2] != null) {
					name = name + " " + employee[2].toString();
				}
				if (employee[3] != null) {
					name = name + " " + employee[3].toString();
				}

				listOfEmployees.put(id, name);
			}
		}
		return listOfEmployees;
	}

	public List<EmployeeLeaveTO> convertBOToTO(List<Object[]> listEmployees) {
		ArrayList<EmployeeLeaveTO> list = new ArrayList<EmployeeLeaveTO>();
		Object[] employee;
		EmployeeLeaveTO objTo;
		if (listEmployees != null) {
			Iterator<Object[]> iterator = listEmployees.iterator();
			while (iterator.hasNext()) {
				employee = (Object[]) iterator.next();
				objTo = new EmployeeLeaveTO();
				String name = "";
				if (employee[0] != null) {
					name = employee[0].toString();
				}
				if (employee[1] != null) {
					name = name + " " + employee[1].toString();
				}
				if (employee[2] != null) {
					name = name + " " + employee[2].toString();
				}
				objTo.setName(name);
				if (employee[3] != null
						&& employee[3].toString().trim().length() > 0) {
					objTo.setDepartment(employee[3].toString());
				}
				if (employee[4] != null
						&& employee[4].toString().trim().length() > 0) {
					objTo.setLeaveType(employee[4].toString());
				}

				if (employee[5] != null
						&& employee[5].toString().trim().length() > 0) {
					objTo.setFromDate(CommonUtil.formatSqlDate1(employee[5]
								                							.toString()));
				}
				if (employee[6] != null
						&& employee[6].toString().trim().length() > 0) {
					objTo.setToDate(CommonUtil.formatSqlDate1(employee[6]
							.toString()));
				}
				if (employee[7] != null
						&& employee[7].toString().trim().length() > 0) {
					objTo.setReason(employee[7].toString());
				}
				if (employee[8] != null
						&& employee[8].toString().trim().length() > 0) {
					objTo.setStatus(employee[8].toString());
				}else{
					objTo.setStatus("Applied");
				}
				list.add(objTo);
			}
		}

		return list;
	}

}
