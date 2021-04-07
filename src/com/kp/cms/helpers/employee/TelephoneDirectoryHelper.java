package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.TelephoneExtensionNumBo;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.transactions.employee.ITelephoneDirectoryTransaction;
import com.kp.cms.transactionsimpl.employee.TelephoneDirectoryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class TelephoneDirectoryHelper {
	private static volatile TelephoneDirectoryHelper helper = null;
	public static TelephoneDirectoryHelper getInstance(){
		if(helper == null){
			helper = new TelephoneDirectoryHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param employee
	 * @return
	 * @throws Exception
	 */
	public List<EmployeeTO> convertBOToTOList(List<Employee> empList,HttpServletRequest request)throws Exception {
		List<EmployeeTO> list = new ArrayList<EmployeeTO>();
		if(empList!=null && !empList.isEmpty()){
			Iterator<Employee> iterator = empList.iterator();
			while (iterator.hasNext()) {
				Employee employee = (Employee) iterator.next();
				EmployeeTO empTO = new EmployeeTO();
				if(employee.getFirstName()!=null && !employee.getFirstName().isEmpty()){
					empTO.setFirstName(employee.getFirstName());
				}
				if(employee.getDepartment()!=null && !employee.getDepartment().toString().isEmpty()){
					if(employee.getDepartment().getName()!=null && !employee.getDepartment().getName().isEmpty()){
						empTO.setDepartmentName(employee.getDepartment().getName());
					}
				}
				if(employee.getWorkEmail()!=null && !employee.getWorkEmail().isEmpty()){
					empTO.setWorkEmail(employee.getWorkEmail());
				}
				if(employee.getCurrentAddressMobile1()!=null && !employee.getCurrentAddressMobile1().isEmpty()){
					empTO.setMobile(employee.getCurrentAddressMobile1());
				}
				if(employee.getExtensionNumber()!=null && !employee.getExtensionNumber().isEmpty()){
					empTO.setExtensionNumber(employee.getExtensionNumber());
				}
				empTO.setId(employee.getId());
				/*EmpImages empimage=transaction.getEmpimages(employee.getId());
				if(empimage!=null && empimage.getEmpPhoto()!=null &&empimage.getEmpPhoto().length>0){
					empTO.setPhotoEmp(new String(empimage.getEmpPhoto()));
					request.getSession().setAttribute(String.valueOf(empTO.getId()), empimage.getEmpPhoto());
				}*/
				list.add(empTO);
			}
		}
		Collections.sort(list);
		return list;
	}
	/**
	 * @param deptBoList
	 * @return
	 * @throws Exception
	 */
	public List<DepartmentEntryTO> convertDeptBOToTOList( List<Department> deptBoList) throws Exception{
		List<DepartmentEntryTO> newTOList = new ArrayList<DepartmentEntryTO>();
		if(deptBoList!=null && !deptBoList.isEmpty()){
			Iterator<Department> iterator = deptBoList.iterator();
			while (iterator.hasNext()) {
				Department department = (Department) iterator.next();
				DepartmentEntryTO to = new DepartmentEntryTO();
				to.setId(department.getId());
				to.setName(department.getName());
				newTOList.add(to);
			}
		}
		return newTOList;
	}
	public Map<String,Map<String,String>> convertListToMap(
			List<TelephoneExtensionNumBo> extensionNumBos) throws Exception{
		Map<String,Map<String,String>> map2=new HashMap<String, Map<String,String>>();
		if(extensionNumBos!=null){
			Iterator<TelephoneExtensionNumBo> iterator=extensionNumBos.iterator();
			Map<String,String> map3=null;
			while (iterator.hasNext()) {
				TelephoneExtensionNumBo telephoneExtensionNumBo = (TelephoneExtensionNumBo) iterator.next();
				if(map2.containsKey(telephoneExtensionNumBo.getGroupName())){
					map3=map2.remove(telephoneExtensionNumBo.getGroupName());
				}else{
					map3=new HashMap<String, String>();
				}
				map3.put(telephoneExtensionNumBo.getDepName(), telephoneExtensionNumBo.getExtensionNum());
				map3=CommonUtil.sortMapByKey(map3);
				map2.put(telephoneExtensionNumBo.getGroupName(), map3);
				map2=CommonUtil.sortMapByKey(map2);
				
			}
		}
		return map2;
	}
}
