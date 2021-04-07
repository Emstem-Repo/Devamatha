package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.StudentCertificateNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.MigrationMasterForm;
import com.kp.cms.transactions.admin.IMigrationMasterTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author dIlIp
 *
 */
public class MigrationMasterTransactionImpl implements IMigrationMasterTransaction {
	
	private static volatile MigrationMasterTransactionImpl migrationMasterTransactionImpl = null;
	private static final Log log = LogFactory.getLog(TCMasterTransactionImpl.class);
	
	private MigrationMasterTransactionImpl() {
		
	}
	
	public static MigrationMasterTransactionImpl getInstance() {
		if (migrationMasterTransactionImpl == null) {
			migrationMasterTransactionImpl = new MigrationMasterTransactionImpl();
		}
		return migrationMasterTransactionImpl;
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMigrationMasterTransaction#getAllMigrationNumber()
	 */
	public List<StudentCertificateNumber> getAllMigrationNumber() throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			List<StudentCertificateNumber> migList = session.createQuery("from StudentCertificateNumber m where m.isActive=1").list();
			session.flush();
			return migList;
		} catch (Exception e) {
			if (session != null){
				session.flush();
			}
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMigrationMasterTransaction#addMigrationMaster(com.kp.cms.bo.admin.MigrationNumber, java.lang.String)
	 */
	public boolean addMigrationMaster(StudentCertificateNumber bo,String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			/*session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();*/
			if(mode.equals("add")){
				session.save(bo);
			}else{
				session.merge(bo);
			}
			transaction.commit();
			
			session.close();
			return true;
			
		} 
		catch (RuntimeException e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}catch (Exception e) {
			if ( transaction != null){
				transaction.rollback();
			}
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMigrationMasterTransaction#isMigrationNumberDuplcated(com.kp.cms.forms.admin.MigrationMasterForm)
	 */
	public StudentCertificateNumber isMigrationNumberDuplcated(MigrationMasterForm migrationMasterForm) throws Exception {
		Session session = null;
		StudentCertificateNumber migrationNumber;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();

			StringBuffer sqlString = new StringBuffer("from StudentCertificateNumber m where m.type=:migType and m.isActive=1");
			Query query = session.createQuery(sqlString.toString());
			//query.setString("migPrefix", migrationMasterForm.getPrefix());
			query.setString("migType", migrationMasterForm.getType());
			//query.setString("startNo", migrationMasterForm.getStartNo());
			migrationNumber = (StudentCertificateNumber) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
		} 
		catch (RuntimeException e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			throw new ApplicationException(e);
		}
		return migrationNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IMigrationMasterTransaction#deleteMigrationMaster(int, java.lang.Boolean, com.kp.cms.forms.admin.MigrationMasterForm)
	 */
	public boolean deleteMigrationMaster(int id, Boolean activate, MigrationMasterForm migrationMasterForm) throws Exception {
		
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			StudentCertificateNumber migrationNumber = (StudentCertificateNumber) session.get(StudentCertificateNumber.class, id);
			if (activate) {
				migrationNumber.setIsActive(true);
			}else
			{
				migrationNumber.setIsActive(false);
			}
			migrationNumber.setModifiedBy(migrationMasterForm.getUserId());
			migrationNumber.setLastModifiedDate(new Date());
			session.update(migrationNumber);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error in deleteCounter.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}

}
