package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.employee.EmpJobTitle;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadEmployeeDetailsTransaction implements IUploadEmployeeDetailsTransaction {
	private static final Log log = LogFactory
	.getLog(UploadEmployeeDetailsTransaction.class);
	private static volatile UploadEmployeeDetailsTransaction instance=null;
	
	/**
	 * 
	 */
	private UploadEmployeeDetailsTransaction(){}
	
	/**
	 * @return
	 */
	public static UploadEmployeeDetailsTransaction getInstance(){
		
		if(instance==null){
			instance=new UploadEmployeeDetailsTransaction();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getUploadEmployeeList()
	 */
	@Override
	public Map<String,Employee> getUploadEmployeeList() throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		List<Employee> list=null;
		Map<String,Employee> empMap=new HashMap<String, Employee>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Employee e where e.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<Employee> iterator=list.iterator();
			while(iterator.hasNext()){
				Employee employee=iterator.next();
				if(employee.getId()>0){
					empMap.put(String.valueOf(employee.getId()), employee);
				}
			}
		}
		
		return empMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getCountyMap()
	 */
	@Override
	public Map<String, String> getCountyMap() throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> countyMap =new HashMap<String, String>();
		List<Country> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Country c where c.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		
		if(list!=null){
			Iterator<Country> iterator=list.iterator();
			while(iterator.hasNext()){
				Country country=iterator.next();
				if(country.getName()!=null && !country.getName().isEmpty() && country.getId()>0)
				countyMap.put(country.getName(), String.valueOf(country.getId()));
			}
		}
		return countyMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getDepartmentMap()
	 */
	@Override
	public Map<String, String> getDepartmentMap() throws Exception {
		Map<String,String> departmentMap =new HashMap<String, String>();
		List<Department> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		
		if(list!=null){
			Iterator<Department> iterator=list.iterator();
			while(iterator.hasNext()){
				Department department=iterator.next();
				if(department.getName()!=null && !department.getName().isEmpty() && department.getId()>0){
					departmentMap.put(department.getName(),String.valueOf(department.getId()));
				}
			}
		}
		return departmentMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getDesignationMap()
	 */
	@Override
	public Map<String, String> getDesignationMap() throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> designationMap =new HashMap<String, String>();
		List<Designation> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Designation d where d.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		
		if(list!=null){
			Iterator<Designation> iterator=list.iterator();
			while(iterator.hasNext()){
				Designation designation=iterator.next();
				if(designation.getName()!=null && !designation.getName().isEmpty() && designation.getId()>0){
					designationMap.put(designation.getName(),String.valueOf(designation.getId()));
				}
			}
		}
		return designationMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getStateMap()
	 */
	@Override
	public Map<String, String> getStateMap() throws Exception {
		// TODO Auto-generated method stub
		Map<String,String> stateMap =new HashMap<String, String>();
		List<State> list=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from State s where s.isActive=true");
			list=query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		
		if(list!=null){
			Iterator<State> iterator=list.iterator();
			while(iterator.hasNext()){
				State state=iterator.next();
				if(state.getName()!=null && !state.getName().isEmpty() && state.getId()>0){
					stateMap.put(state.getName(),String.valueOf(state.getId()));
				}
			}
		}
		return stateMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#saveEmployeeDetails(java.util.List)
	 */
	@Override
	public boolean saveEmployeeDetails(List<Employee> list) throws Exception {
		// TODO Auto-generated method stub
		int count=1;
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try {
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			if(list!=null){
				Iterator<Employee> iterator=list.iterator();
				while(iterator.hasNext()){
					Employee employee=iterator.next();
					System.out.println(employee.getFirstName());
					if(employee!=null){
						session.saveOrUpdate(employee);
						//transaction.commit();
						flag=true;
						//count++;
					}
					/*if(count==645){
						System.out.println("h");
					}*/
					if(++count%20==0){
						session.flush();
						session.clear();
					}
				}
				transaction.commit();
			}
			
		} catch (Exception e) {
			if(transaction!=null)
			transaction.rollback();
			log.debug("Error during saving data...", e);
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
				//transaction.commit();
				session.close();
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IUploadEmployeeDetailsTransaction#getEmpTypeMap()
	 */
	@Override
	public Map<String, String> getEmpTypeMap() throws Exception {
		Session session=null;
		Map<String, String> empTypeMap = new HashMap<String, String>();
		List<EmpType> list = null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpType emp where emp.isActive=1";
			Query query=session.createQuery(str);
			list= query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<EmpType> iterator=list.iterator();
			while (iterator.hasNext()) {
				EmpType empType = (EmpType) iterator.next();
				if(empType.getName()!=null && !empType.getName().isEmpty() && empType.getId()>0){
					empTypeMap.put(empType.getName(), String.valueOf(empType.getId()));
				}
			}
		}
		return empTypeMap;
	}
	@Override
	public Map<String, String> getJobTitleMap() throws Exception {
		Session session=null;
		Map<String, String> jobTitleMap = new HashMap<String, String>();
		List<EmpJobTitle> list = null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmpJobTitle job where job.isActive=1";
			Query query=session.createQuery(str);
			list= query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<EmpJobTitle> iterator=list.iterator();
			while (iterator.hasNext()) {
				EmpJobTitle jobTitle = (EmpJobTitle) iterator.next();
				if(jobTitle.getTitle()!=null && !jobTitle.getTitle().isEmpty() && jobTitle.getId()>0){
					jobTitleMap.put(jobTitle.getTitle(), String.valueOf(jobTitle.getId()));
				}
			}
		}
		return jobTitleMap;
	}
	@Override
	public Map<String, String> getWorkLocationMap() throws Exception {
		Session session=null;
		Map<String, String> workLocationMap = new HashMap<String, String>();
		List<EmployeeWorkLocationBO> list = null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmployeeWorkLocationBO work where work.isActive=1";
			Query query=session.createQuery(str);
			list= query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<EmployeeWorkLocationBO> iterator=list.iterator();
			while (iterator.hasNext()) {
				EmployeeWorkLocationBO workLocation = (EmployeeWorkLocationBO) iterator.next();
				if(workLocation.getName()!=null && !workLocation.getName().isEmpty() && workLocation.getId()>0){
					workLocationMap.put(workLocation.getName(), String.valueOf(workLocation.getId()));
				}
			}
		}
		return workLocationMap;
	}
	@Override
	public Map<String, String> getStreamMap() throws Exception {
		Session session=null;
		Map<String, String> streamMap = new HashMap<String, String>();
		List<EmployeeStreamBO> list = null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmployeeStreamBO stream where stream.isActive=1";
			Query query=session.createQuery(str);
			list= query.list();
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
		}
		if(list!=null){
			Iterator<EmployeeStreamBO> iterator=list.iterator();
			while (iterator.hasNext()) {
				EmployeeStreamBO stream = (EmployeeStreamBO) iterator.next();
				if(stream.getName()!=null && !stream.getName().isEmpty() && stream.getId()>0){
					streamMap.put(stream.getName(), String.valueOf(stream.getId()));
				}
			}
		}
		return streamMap;
	}
}
