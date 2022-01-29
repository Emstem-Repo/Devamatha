package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.TelephoneExtensionNumBo;
import com.kp.cms.forms.employee.TelephoneDirectoryForm;
import com.kp.cms.helpers.employee.TelephoneDirectoryHelper;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.transactions.employee.ITelephoneDirectoryTransaction;
import com.kp.cms.transactionsimpl.employee.TelephoneDirectoryTransactionImpl;

public class TelephoneDirectoryHandler {
	private static volatile TelephoneDirectoryHandler handler = null;
	public static TelephoneDirectoryHandler getInstance(){
		if(handler == null){
			handler = new TelephoneDirectoryHandler();
			return handler;
		}
		return handler;
	}
	ITelephoneDirectoryTransaction transaction = TelephoneDirectoryTransactionImpl.getInstance();
	/**
	 * @param objForm
	 * @return
	 */
	public List<EmployeeTO> getSearchDetails(TelephoneDirectoryForm objForm,HttpServletRequest request)throws Exception {
		List<Employee> employee = transaction.getSearchDetails(objForm);
		List<EmployeeTO> empTOList = TelephoneDirectoryHelper.getInstance().convertBOToTOList(employee,request);
		return empTOList;
	}
	public List<DepartmentEntryTO> getDepartmentList()throws Exception {
		List<Department> deptBoList = transaction.getDepartmentList();
		List<DepartmentEntryTO> tos = TelephoneDirectoryHelper.getInstance().convertDeptBOToTOList(deptBoList);
		return tos;
	}
	/*
	 * getting the extension numbers details
	 */
	
	public void getExtensionNumbers(TelephoneDirectoryForm objForm)throws Exception {
		List<TelephoneExtensionNumBo> extensionNumBos=transaction.getTelephoneExtensionNumBo();
		Map<String,Map<String,String>> map=TelephoneDirectoryHelper.getInstance().convertListToMap(extensionNumBos);
		//map=CommonUtil.sortMapByKey(map);
		objForm.setExtensionNumMap(map);
	}
}
