package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IEmployeeReportTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeReportTxnImpl implements IEmployeeReportTransaction{
private static volatile EmployeeReportTxnImpl employeeReportTxnImpl = null;
public static EmployeeReportTxnImpl getInstance(){
	if(employeeReportTxnImpl == null){
		employeeReportTxnImpl = new EmployeeReportTxnImpl();
		return employeeReportTxnImpl;
	}
	return employeeReportTxnImpl;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IEmployeeReportTransaction#getStreamMap()
 */
@Override
public Map<String, String> getStreamMap() throws Exception {
	Session session=null;
	Map<String ,String> streamMap = new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmployeeStreamBO s where s.isActive=1");
		List<EmployeeStreamBO> list=query.list();
		if(list!=null){
			Iterator<EmployeeStreamBO> iterator=list.iterator();
			while (iterator.hasNext()) {
				EmployeeStreamBO employeeStreamBO = (EmployeeStreamBO) iterator .next();
				if(employeeStreamBO!=null && employeeStreamBO.getName()!=null && !employeeStreamBO.getName().isEmpty() && employeeStreamBO.getId()>0){
					streamMap.put(String.valueOf(employeeStreamBO.getId()), employeeStreamBO.getName());
				}
			}
		}
	}catch (Exception e) {
		// TODO: handle exception
		throw new ApplicationException();
	}
	finally{
		if(session!=null){
			session.flush();
		}
	}
	streamMap=(Map<String, String>) CommonUtil.sortMapByValue(streamMap);
	return streamMap;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IEmployeeReportTransaction#getDepartmentMap()
 */
@Override
public Map<String, String> getDepartmentMap() throws Exception {
	Session session=null;
	Map<String, String> departmentMap = new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from Department d where d.isActive=1");
		List<Department> departments=query.list();
		if(departments!=null && !departments.isEmpty()){
			Iterator<Department> iterator= departments.iterator();
			while (iterator.hasNext()) {
				Department department = (Department) iterator.next();
				if(department!=null && department.getName()!=null && !department.getName().isEmpty() && department.getId()>0){
					departmentMap.put(String.valueOf(department.getId()), department.getName());
				}
			}
		}
	}catch (Exception e) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	departmentMap=(Map<String,String>) CommonUtil.sortMapByValue(departmentMap);
	return departmentMap;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IEmployeeReportTransaction#getDesignation()
 */
@Override
public Map<String, String> getDesignation() throws Exception {
	Session session=null;
	Map<String,String> designationMap= new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from Designation d where d.isActive=1");
		List<Designation> designationList = query.list();
		if(designationList!=null && !designationList.isEmpty()){
			Iterator<Designation> iterator= designationList.iterator();
			while (iterator.hasNext()) {
				Designation designation = (Designation) iterator.next();
				if(designation!=null && designation.getName()!=null && !designation.getName().isEmpty() && designation.getId()>0){
					designationMap.put(String.valueOf(designation.getId()), designation.getName());
				}
			}
		}
	}catch (Exception e) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	designationMap=(Map<String,String>) CommonUtil.sortMapByValue(designationMap);
	return designationMap;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IEmployeeReportTransaction#getWorkLocationMap()
 */
@Override
public Map<String, String> getWorkLocationMap() throws Exception {
	Session session = null;
	Map<String,String> workLocationMap = new HashMap<String, String>();
	try{
		session=HibernateUtil.getSession();
		Query query=session.createQuery("from EmployeeWorkLocationBO w where w.isActive=1");
		List<EmployeeWorkLocationBO> locationBOs=query.list();
		if(locationBOs!=null && !locationBOs.isEmpty()){
			Iterator<EmployeeWorkLocationBO>iterator=locationBOs.iterator();
			while (iterator.hasNext()) {
				EmployeeWorkLocationBO employeeWorkLocationBO = (EmployeeWorkLocationBO) iterator .next();
				if(employeeWorkLocationBO!=null && employeeWorkLocationBO.getName()!=null && !employeeWorkLocationBO.getName().isEmpty() && employeeWorkLocationBO.getId()>0){
					workLocationMap.put(String.valueOf(employeeWorkLocationBO.getId()), employeeWorkLocationBO.getName());
				}
			}
		}
	}catch (Exception e) {
		// TODO: handle exception
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
	workLocationMap=(Map<String,String>) CommonUtil.sortMapByValue(workLocationMap);
	return workLocationMap;
}
/* (non-Javadoc)
 * @see com.kp.cms.transactions.employee.IEmployeeReportTransaction#getSearchedEmployee(java.lang.StringBuffer)
 */
@Override
public List<Employee> getSearchedEmployee(StringBuffer query) throws Exception {
	Session session = null;
	List<Employee> employees;
	try{
		session=HibernateUtil.getSession();
		Query query1= session.createQuery(query.toString());
		employees = query1.list();
		}
	catch (Exception e) {
		throw new ApplicationException(e);
		}
	return employees;
	}
}
