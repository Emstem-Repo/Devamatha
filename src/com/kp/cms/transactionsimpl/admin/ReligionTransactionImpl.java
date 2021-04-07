package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.ReligionSection;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.admin.IReligionTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * An implementation class for IReligionTransaction.
 * @author
 */
public class ReligionTransactionImpl implements IReligionTransaction {
	private static final Log log = LogFactory.getLog(ReligionTransactionImpl.class);	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IReligionTransaction#addReligion(java.lang.String)
	 */
	@Override
	public boolean addReligion(Religion religion) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(religion);
			transaction.commit();
			
			return true;
		}  catch (ConstraintViolationException e) {
			log.error("Error during saving Religion data...", e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Religion data..." , e);
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
	 * @see com.kp.cms.transactions.admin.IReligionTransaction#deleteReligion(int)
	 */
	@Override
	public boolean deleteReligion(int religionId) {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Religion religion = (Religion) session.get(
					Religion.class, religionId);
			session.delete(religion);
			transaction.commit();
			session.flush();
			session.close();
			return true;
		} catch (Exception e) {
			if(transaction !=null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IReligionTransaction#editReligion(int, java.lang.String)
	 */
	@Override
	public boolean editReligion(Religion religion) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(religion);
			transaction.commit();
			return true;
		} catch (ConstraintViolationException e) {
			log.error("Error during Updating Religion data...", e);
			if( transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during updating Religion data...", e);
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
	 * @see com.kp.cms.transactions.admin.IReligionTransaction#getReligion()
	 */
	@Override
	public List<Religion> getReligion() {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			String programHibernateQuery = "from Religion r where isActive=1 order by r.name";
			List<Religion> religions = session.createQuery(
					programHibernateQuery).list();
			session.flush();
			//session.close();
			return religions;
		} catch (Exception e) {
			if(transaction !=null){
				transaction.rollback();
			}
			if (session != null){
				session.flush();
				//session.close();
			}
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @Checks whether the given religion already exists in the database or not
	 * returns true if there else false will be returned
	 */
	public boolean existanceCheck(Religion religion) throws Exception
	{
		Session session = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String studenttypeHibernateQuery = "from Religion where name=:Name and isActive=1";
			Query query = session.createQuery(
					studenttypeHibernateQuery);
			query.setString("Name", religion.getName());
			List<Religion> religionlist=query.list();
			session.flush();
			//session.close();
			return (religionlist!= null && !religionlist.isEmpty()?true:false);
			
		}catch (Exception e) {
			if (session != null){
				session.flush();
				//session.close();
			}	
			throw  new ApplicationException(e);
			
		}
			
		
	}
	
	public List<ReligionSection> getReligionSection()throws Exception{
		Session session=null;
		List<ReligionSection> listReligion=new ArrayList<ReligionSection>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from ReligionSection where isActive=1");
			listReligion=query.list();
		}
		catch(Exception e){
			
		}
		finally{
			if(session!=null){
				session.flush();
			}
		}
		return listReligion;
		
		
	}
}
