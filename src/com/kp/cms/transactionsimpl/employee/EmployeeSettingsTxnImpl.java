package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmpLeaveType;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.bo.employee.Holidays;
import com.kp.cms.transactions.employee.IEmployeeSettingsTxn;
import com.kp.cms.utilities.HibernateUtil;

public class EmployeeSettingsTxnImpl implements IEmployeeSettingsTxn{
	private static final Log log = LogFactory.getLog(EmployeeSettingsTxnImpl.class);
	@Override
	public List<EmployeeSettings> getEmpSettList() {
		Session session=null;
		List<EmployeeSettings> empSett=null;
		try{
			String query="from EmployeeSettings empSettings";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			empSett=querry.list();
			session.close();
		}catch(Exception exception){
			log.debug("Exception" + exception.getMessage());
		}
		return empSett;
	}
	@Override
	public List<EmpLeaveType> getEmpLeaveType() {
	Session session=null;
	List<EmpLeaveType> empLeaveType=null;
	try{
		String query="from EmpLeaveType empleave where empleave.isActive=1 and empleave.isLeave=1";
		session=HibernateUtil.getSession();
		Query quer=session.createQuery(query);
		empLeaveType=quer.list();
	}catch(Exception e){
		log.debug("Exception" + e.getMessage());
	}
		return empLeaveType;
	}
	@Override
	public EmployeeSettings getEmpSettingsListById(int id) {
		Session session=null;
		EmployeeSettings empSettings=null;
		try{
			session=HibernateUtil.getSession();
			String str="from EmployeeSettings empsett where empsett.id="+id;
			Query query=session.createQuery(str);
			empSettings=(EmployeeSettings)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting TeacherDepartment by id..." , e);
			session.flush();
			session.close();
			
		}
		return empSettings;
		
	}
	@Override
	public Integer getCurrentApplicationNO(){
		String currentAppNo=null;
		Integer appNo=null;
		Session session=null;
		try{
			session=HibernateUtil.getSession();
			String str="select empSett.currentApplicationNo from EmployeeSettings empSett";
			Query query=session.createQuery(str);
			currentAppNo=query.uniqueResult().toString();
			appNo=Integer.parseInt(currentAppNo);
		}catch(Exception e){
			log.error("Error during getting current application no..." , e);
			session.flush();
			session.close();
		}
		return appNo;
	}
	@Override
	public boolean updateEmpSettings(EmployeeSettings empSettings)
			 {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.update(empSettings);
			transaction.commit();
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating Employee Settings data...", e);
		}
		return true;
	}
	
}
