package com.kp.cms.transactionsimpl.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.bo.hostel.BiometricBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.BiometricForm;
import com.kp.cms.transactions.hostel.IBiometricTransaction;
import com.kp.cms.transactionsimpl.employee.EmpLeaveAllotmentTxnImpl;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class BiometricTransactionImpl implements IBiometricTransaction{
	private static final Log log = LogFactory.getLog(BiometricTransactionImpl.class);
	/**
	 * instance()
	 */
	public static volatile BiometricTransactionImpl biometricTransactionImpl = null;

	public static BiometricTransactionImpl getInstance() {
		if (biometricTransactionImpl == null) {
			biometricTransactionImpl = new BiometricTransactionImpl();
		}
		return biometricTransactionImpl;
	}

	@Override
	public boolean addBiometricDetails(BiometricBo biometricBo)
			throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session  = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				session.save(biometricBo);
			tx.commit();
			isAdded = true;
			}catch (Exception e) {
				isAdded =false;
				throw new ApplicationException(e);
			}
			finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		return isAdded;
		}

	@Override
	public boolean checkDuplicate(BiometricForm biometricForm) throws Exception {
		Session session=null;
		boolean flag=false;
		BiometricBo biometricBo=null;
		try{
			String hId=biometricForm.getHostelId();
			String bId=biometricForm.getBlock();
			String uId=biometricForm.getUnit();
			String machineId=biometricForm.getMachineId();
			String machineIp=biometricForm.getMachineIp();
			String machineName=biometricForm.getMachineName();
			
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from BiometricBo d where d.isActive=1 and d.hlHostel.id="+hId+
					"and d.hlBlock.id="+bId+"and d.hlUnit.id="+uId+"and machineId='"+machineId+"' and d.machineIp='"+machineIp+
					"' and d.machineName='"+machineName+"'");
			biometricBo=(BiometricBo)query.uniqueResult();
			if(biometricBo!=null){
				flag=true;
			}
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return flag;
	}

	@Override
	public List<BiometricBo> getBiometricDetails() throws Exception {
		Session session=null;
		List<BiometricBo> biometricBos=null;
		try{
			String query="from BiometricBo metric where metric.isActive=1";
			session=HibernateUtil.getSession();
			Query querry=session.createQuery(query);
			biometricBos=querry.list();
		}catch(Exception exception){
			log.error("Error during getting BiometricBo list..." , exception);
		}
		finally{
			session.flush();
		}
		return biometricBos;
	}

	@Override
	public BiometricBo getBiometricDetailsById(int id) throws Exception {
		Session session=null;
		BiometricBo biometricBo=null;
		try{
			session=HibernateUtil.getSession();
			String str="from BiometricBo metric where metric.id="+id;
			Query query=session.createQuery(str);
			biometricBo=(BiometricBo)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting BiometricDetails by id..." , e);
			session.flush();
			session.close();
		}
		return biometricBo;
	
	}

	@Override
	public boolean updateBiometricDetails(BiometricBo biometricBo)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.merge(biometricBo);
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
      	    String str="from BiometricBo metric where metric.id="+id;
      	  BiometricBo biometric=(BiometricBo)session.createQuery(str).uniqueResult();
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
