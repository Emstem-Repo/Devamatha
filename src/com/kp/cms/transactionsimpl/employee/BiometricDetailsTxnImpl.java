package com.kp.cms.transactionsimpl.employee;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EmpLeaveAllotment;
import com.kp.cms.transactions.employee.IBiometricDetailsTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class BiometricDetailsTxnImpl implements IBiometricDetailsTxn{
	private static final Log log = LogFactory.getLog(EmpLeaveAllotmentTxnImpl.class);
	@Override
	public boolean duplicateCheck(String machineId, String machineIp,
			String machineName,int id) throws Exception {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			
			String quer="from BiometricDetails metric where metric.machineId= :id and metric.machineIp= :ip and metric.isActive=1";
			Query query=session.createQuery(quer);
			query.setString("id", machineId);
			query.setString("ip", machineIp);
			BiometricDetails biometric=(BiometricDetails)query.uniqueResult();
			if(biometric!=null && !biometric.toString().isEmpty()){
				if(id!=0){
			if(biometric.getId()==id){
				flag=false;
			}
			else{
				flag=true;
			}}
				else
				flag=true;
			}
			else
				flag=false;
				
		}catch(Exception e){
			log.error("Error during duplicate check..." , e);
			session.flush();
			session.close();
		}
		return flag;
		
	}
	@Override
	public List<BiometricDetails> getBiometricDetails() throws Exception {
		Session session=null;
		List<BiometricDetails> empBiometric=null;
		try{
			String query="from BiometricDetails metric where metric.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			empBiometric=querry.list();
		}catch(Exception exception){
			log.error("Error during getting empBiometric list..." , exception);
		}
		finally{
			session.flush();
		}
		return empBiometric;
	}
	@Override
	public boolean addBiometricDetails(BiometricDetails biometric)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(biometric);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
		
	}
	@Override
	public BiometricDetails getBiometricDetailsById(int id) throws Exception {
		Session session=null;
		BiometricDetails biometric=null;
		try{
			session=HibernateUtil.getSession();
			String str="from BiometricDetails metric where metric.id="+id;
			Query query=session.createQuery(str);
			biometric=(BiometricDetails)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting BiometricDetails by id..." , e);
			session.flush();
			session.close();
		}
		return biometric;
	
	}
	@Override
	public boolean updateBiometricDetails(BiometricDetails biometric)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(biometric);
			transaction.commit();
			flag=true;
			session.flush();
			session.close();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during updating BiometricDetails data...", e);
		}
		return flag;
	
	}
	@Override
	public boolean deleteBiometricDetails(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="from BiometricDetails metric where metric.id="+id;
      	  BiometricDetails biometric=(BiometricDetails)session.createQuery(str).uniqueResult();
      	    transaction=session.beginTransaction();
      	  biometric.setIsActive(false);
      	    session.update(biometric);
      	    transaction.commit();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting biometricDetails data...", e);
      }
return true;
		
	}

}
