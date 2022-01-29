package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.District;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.DistrictForm;
import com.kp.cms.transactions.admin.IDistrictTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class DistrictTransactionImpl implements IDistrictTransaction {

	private static final Log log = LogFactory.getLog(DistrictTransactionImpl.class);
	public static volatile DistrictTransactionImpl stateTransactionImpl = null;

	public static DistrictTransactionImpl getInstance() {
		if (stateTransactionImpl == null) {
			stateTransactionImpl = new DistrictTransactionImpl();
			return stateTransactionImpl;
		}
		return stateTransactionImpl;
	}

	/**
	 * This method add a single state to Database.
	 * 
	 * @return true / false based on result.
	 * @throws BusinessException 
	 */
	public boolean addDistrict(District state) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session != null){
			     tx = session.beginTransaction();
			     tx.begin();
			     session.save(state);
			     tx.commit();
				 session.flush();
			     return true;
			}else
				 return false;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			      tx.rollback();
			log.error("Error during saving state data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			      tx.rollback();
			log.error("Error during saving state data..." , e);
			throw new ApplicationException(e);
		}

	}

	/**
	 * This method return list of state all objects.
	 * @throws ApplicationException 
	 */
	public List<District> getDistrict() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			Query query = session.createQuery("from District d where d.isActive = 1 and d.state.isActive = 1 order by d.name");
			List<District> list = query.list();
			session.flush();
			return list;
			}else 
				return null;
		} catch (Exception e) {
			log.error("Error during getting state..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method return list of state all objects.
	 * @throws ApplicationException 
	 */
	public List<District> getNativeDistrict(int stateId) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			Query query = session.createQuery("from District s where s.isActive = 1 and  s.state.id = :cntId and s.state.isActive = 1 order by s.name");
			query.setInteger("cntId", stateId);
			List<District> list = query.list();
			session.flush();
			return list;
			}else 
				return null;
		} catch (Exception e) {
			log.error("Error during getNativeStates..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	
	/**
	 * This method update the state to Database.
	 * 
	 * @return true / flase based on result.
	 */
	public boolean updateDistrict(District state) {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			tx = session.beginTransaction();
			tx.begin();
			session.update(state);
			tx.commit();
			session.flush();
			return true;
			}else 
				return false;
		} catch (Exception e) {
			log.error("Error during getting state..." , e);
			if (tx != null) {
				tx.rollback();
			}
			if (session != null) {
				session.flush();
				//session.close();
			}
			return false;
		}
	}

	/**
	 * This method delete the state to Database.
	 * 
	 * @return true / false based on result.
	 */
	public boolean deleteDistrict(int stateId, Boolean activate, DistrictForm countryStateCityForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			District state = (District) session.get(District.class, stateId);
			if (activate) {
				state.setIsActive(true);
			} else {
				state.setIsActive(false);
			}
			state.setModifiedBy(countryStateCityForm.getUserId());
			state.setLastModifiedDate(new Date());
			session.update(state);
			tx.commit();
			session.flush();
			//session.close();
			return true;
		} catch (ConstraintViolationException e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during deleting state data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			if(tx!=null)
			     tx.rollback();
			log.error("Error during deleting state data..." , e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * state name duplication checking based on country
	 */
	public District isDistrictNameDuplcated(DistrictForm countryStateCityForm) throws Exception {
		Session session = null;
		District state;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from District a where (a.name = :stateName and a.state.id = :countryId) ");
			query.setString("stateName", countryStateCityForm.getDistrictName());
			query.setInteger("countryId", Integer.parseInt(countryStateCityForm.getStateId()));
			state = (District) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return state;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	

}
