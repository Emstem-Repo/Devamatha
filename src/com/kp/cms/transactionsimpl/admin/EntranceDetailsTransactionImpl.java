package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Entrance;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EntranceDetailsForm;
import com.kp.cms.transactions.admin.IEntranceDetails;
import com.kp.cms.utilities.HibernateUtil;

public class EntranceDetailsTransactionImpl implements IEntranceDetails{
	public static volatile EntranceDetailsTransactionImpl entranceDetailsTransactionImpl = null;
	private static final Log log = LogFactory.getLog(EntranceDetailsTransactionImpl.class);

	public static EntranceDetailsTransactionImpl getInstance() {
		if (entranceDetailsTransactionImpl == null) {
			entranceDetailsTransactionImpl = new EntranceDetailsTransactionImpl();
			return entranceDetailsTransactionImpl;
		}
		return entranceDetailsTransactionImpl;
	}
	
	/**
	 * 
	 * This method returns a Entrance object with all the records
	 * 
	 * @throws ApplicationException
	 */

	public List<Entrance> getEntranceDetails() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Entrance en where en.isActive=1");
			List<Entrance> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error in getEntranceDetails in impl..", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * 
	 * This method adds a record into the entrance table
	 * 
	 * @throws DuplicateException
	 *             , Exception
	 */

	public boolean addEntranceDetails(Entrance entrance, String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();			
			transaction = session.beginTransaction();
			transaction.begin();
			if (mode.equalsIgnoreCase("Add")) {
				session.save(entrance);
			} else {
				session.update(entrance);
			}
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving entrance data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(transaction!=null)
			    transaction.rollback();
			log.error("Error during saving entrance data...", e);
			throw new ApplicationException(e);
		}
	}
	public List<Entrance> getEntranceDetailsById(int id) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Entrance en where en.isActive=1 and id = " + "'" + id + "'");
			List<Entrance> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error in getEntranceDetailsById in impl..", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * checking for name duplication based on program
	 */
	public Entrance isEntranceNameDuplcated(Entrance duplEntrance) throws Exception {
		Session session = null;
		Entrance entrance;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from Entrance a where name = :name and program.id = :prgId");
			if(duplEntrance.getId()!= 0){
				sqlString.append(" and id !='" + duplEntrance.getId() + "'");
			}			
			Query query = session.createQuery(sqlString.toString());
			query.setString("name", duplEntrance.getName());
			query.setInteger("prgId", duplEntrance.getProgram().getId());
			entrance = (Entrance) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return entrance;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * 
	 * @param id
	 * @param enForm
	 * @param activate
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEntranceDetails(int id,EntranceDetailsForm enForm, Boolean activate) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Entrance entrance = (Entrance) session.get(Entrance.class, id);
			if (activate) {
				entrance.setIsActive(true);
			}else
			{
				entrance.setIsActive(false);
			}
			entrance.setLastModifiedDate(new Date());
			entrance.setModifiedBy(enForm.getUserId());
			session.update(entrance);
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during deleting entrance details data...",e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during deleting entrance details data..." + e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new ApplicationException(e);
		}
		finally  {
			
			if (session != null){
				session.flush();
				session.close();
			}
			
		}
			
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IEntranceDetails#getEntranceDetailsByProgram(int)
	 */
	@Override
	public List<Entrance> getEntranceDetailsByProgram(int programId)
			throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Entrance en where en.isActive=1 and en.program.id= :ProgID");
			query.setInteger("ProgID", programId);
			List<Entrance> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			log.error("Error in getEntranceDetailsByProgram in impl..", e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}	
		
}
