package com.kp.cms.transactionsimpl.employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.employee.EmpType;
import com.kp.cms.bo.employee.ShiftEntry;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.employee.EmpTypeForm;
import com.kp.cms.transactions.employee.IEmpTypeTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class EmpTypeTransaction implements IEmpTypeTransaction{
	
	private static volatile EmpTypeTransaction instance=null;
	
	/**
	 * 
	 */
	private EmpTypeTransaction(){
		
	}

	/**
	 * @return
	 */
	public static EmpTypeTransaction getInstance(){
		if(instance==null){
			instance=new EmpTypeTransaction();
		}
		return instance;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmpTypeMap(com.kp.cms.forms.employee.EmpTypeForm)
	 */
	@Override
	public Map<String, EmpType> getEmpTypeMap(EmpTypeForm empTypeForm) throws Exception {
		// TODO Auto-generated method stub
		
		Session session=null;
		Map<String,EmpType> empTypeMap=new HashMap<String, EmpType>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpType e where e.isActive=true");
			List<EmpType> list=query.list();
			if(list!=null && list.size()!=0){
				Iterator<EmpType> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpType type=iterator.next();
					if(type!=null){
						if(type.getId()>0 && type.getName()!=null && !type.getName().isEmpty()){
							empTypeMap.put(String.valueOf(type.getId()),type);
						}
					}
				}
			}
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return empTypeMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#addEmpType(com.kp.cms.bo.employee.EmpType)
	 */
	@Override
	public Boolean addEmpType(EmpType empType) throws Exception {
		// TODO Auto-generated method stub
		Boolean flag=false;
		Session session=null;
		Transaction tx=null;
		try{
			session=HibernateUtil.getSession();
			session.clear();
			tx=session.beginTransaction();
			tx.begin();
			if(empType!=null){
				session.saveOrUpdate(empType);
			}
			session.flush();
			tx.commit();
			flag=true;
			
		}catch (Exception exception) {
			// TODO: handle exception
			tx.rollback();
			throw new ApplicationException(exception);
		}finally{
			if(session!=null){
				session.flush();
		}
		return flag;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmpType(java.lang.String)
	 */
	@Override
	public Map<String, EmpType> getEmpType() throws Exception {
		// TODO Auto-generated method stub
		Map<String, EmpType> type=new HashMap<String, EmpType>();
		List<EmpType> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpType e");
			list= query.list();
			if(list!=null){
				Iterator<EmpType> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpType empType=iterator.next();
					if(empType!=null){
						if(empType.getName()!=null && !empType.getName().isEmpty()){
							type.put(String.valueOf(empType.getId()),empType);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return type;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmployeeMap()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<Integer, String> getEmployeeMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		List<Employee> list=null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Employee e where e.isActive=1 and e.active=1");
			list= query.list();
			if(list!=null){
				Iterator<Employee> iterator=list.iterator();
				while(iterator.hasNext()){
					Employee employee=iterator.next();
					if(employee!=null){
						if(employee.getFirstName()!=null && employee.getDepartment() != null && employee.getDepartment().getName() != null ){
							map.put(employee.getId(),employee.getFirstName()+"("+employee.getDepartment().getName()+")");
						}
					}
				}
				map = (HashMap<Integer, String>)CommonUtil.sortMapByValue(map);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return map;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#saveShiftEntry(java.util.List)
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean saveShiftEntry(List<ShiftEntry> entryBos , String mode) throws Exception {
		Boolean flag=false;
		Session session=null;
		Transaction tx=null;
		try{
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
			tx.begin();
			if(entryBos!=null){
				Iterator<ShiftEntry> iterator = entryBos.iterator();
				while (iterator.hasNext()) {
					ShiftEntry shiftEntry = (ShiftEntry) iterator.next();
					if(mode.equalsIgnoreCase("add")){
						session.save(shiftEntry);
					}else{
						session.saveOrUpdate(shiftEntry);
					}
				}
			}
			session.flush();
			tx.commit();
			flag=true;
		}catch (Exception exception) {
			// TODO: handle exception
			tx.rollback();
			throw new ApplicationException(exception);
		}finally{
			if(session!=null){
				session.flush();
		}
		return flag;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmpEntryList()
	 */
	@Override
	public List<ShiftEntry> getEmpEntryList() throws Exception {
		List<ShiftEntry> list = null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ShiftEntry s where s.isActive=1 group by s.employee.id order by s.employee.firstName");
			list= query.list();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpTypeTransaction#getEmpShiftEntryList(java.lang.String)
	 */
	@Override
	public List<ShiftEntry> getEmpShiftEntryList(String employeeId ,String mode)
			throws Exception {
		
		List<ShiftEntry> list = null;
		Session session=null;
		try {
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("reActivate")){
				Query query=session.createQuery("from ShiftEntry s where s.isActive=0 and s.employee.id="+employeeId+" order by s.id");
				list= query.list();
			}else{
				Query query=session.createQuery("from ShiftEntry s where s.isActive=1 and s.employee.id="+employeeId+" order by s.id");
				list= query.list();

			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return list;
	}

	
}
