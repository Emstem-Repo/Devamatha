package com.kp.cms.transactionsimpl.employee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.employee.IEmpLeaveType;
import com.kp.cms.utilities.HibernateUtil;

public class EmpLeaveTypeTransaction implements IEmpLeaveType {
	private static volatile EmpLeaveTypeTransaction instance=null;
	
	private EmpLeaveTypeTransaction(){}
	
	public static EmpLeaveTypeTransaction getInsatance() {
		if(instance==null){
			instance=new EmpLeaveTypeTransaction();
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpLeaveType#getEmpLeaveDetails()
	 */
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpLeaveType#getEmpLeaveDetails()
	 */
	@Override
	public Map<String,EmpLeaveType> getEmpLeaveDetails()throws Exception {
		Map<String,EmpLeaveType> leaveMap=new HashMap<String, EmpLeaveType>();
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpLeaveType e where e.isActive is true");
			List<EmpLeaveType> list=query.list();
			if(list!=null){
				Iterator<EmpLeaveType> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpLeaveType type=iterator.next();
					if(type!=null && type.getId()>0){
						leaveMap.put(String.valueOf(type.getId()),type);
					}
				}
			}
		}catch(Exception e){
			throw new ApplicationException(e);
		}finally{
			session.flush();
			session.close();
		}
		return leaveMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpLeaveType#getEmpLeaveNamesMap()
	 */
	@Override
	public Map<String, EmpLeaveType> getEmpLeaveNamesMap() throws Exception {
		Map<String,EmpLeaveType> leaveMap=new HashMap<String, EmpLeaveType>();
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmpLeaveType e");
			List<EmpLeaveType> list=query.list();
			if(list!=null){
				Iterator<EmpLeaveType> iterator=list.iterator();
				while(iterator.hasNext()){
					EmpLeaveType type=iterator.next();
					if(type!=null && type.getName()!=null && !type.getName().isEmpty()){
						leaveMap.put(type.getName().toUpperCase(),type);
					}
				}
			}
		}catch(Exception e){
			throw new ApplicationException(e);
		}finally{
			session.flush();
			session.close();
		}
		return leaveMap;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.employee.IEmpLeaveType#saveLeaveType(com.kp.cms.bo.admin.EmpLeaveType)
	 */
	@Override
	public boolean saveLeaveType(EmpLeaveType empLeaveType) throws Exception {
		Session session=null;
		Transaction tx=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			tx.begin();
			if(empLeaveType!=null){
				session.saveOrUpdate(empLeaveType);
				flag=true;
			}
			tx.commit();
			
		}catch (Exception exception) {
			tx.rollback();
			throw new ApplicationException(exception);
		}finally{
			session.flush();
			session.close();
		}
		return flag;
	}

}
