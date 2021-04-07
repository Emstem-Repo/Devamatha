package com.kp.cms.transactionsimpl.admin;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.State;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CountryStateCityForm;
import com.kp.cms.transactions.admin.IStateTransaction;
import com.kp.cms.utilities.HibernateUtil;


public class StateTransactionImpl implements IStateTransaction {

	private static final Log log = LogFactory.getLog(StateTransactionImpl.class);
	public static volatile StateTransactionImpl stateTransactionImpl = null;

	public static StateTransactionImpl getInstance() {
		if (stateTransactionImpl == null) {
			stateTransactionImpl = new StateTransactionImpl();
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
	public boolean addState(State state) throws Exception {
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
	public List<State> getStates() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			Query query = session.createQuery("from State s where isActive = 1 and country.isActive = 1 order by s.name");
			List<State> list = query.list();
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
	public List<State> getNativeStates(int countryId) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if(session!=null){
			Query query = session.createQuery("from State s where s.isActive = 1 and  s.country.id = :cntId and s.country.isActive = 1 order by s.name");
			query.setInteger("cntId", countryId);
			List<State> list = query.list();
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
	public boolean updateState(State state) {
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
	public boolean deleteState(int stateId, Boolean activate, CountryStateCityForm countryStateCityForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			State state = (State) session.get(State.class, stateId);
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
	public State isStateNameDuplcated(CountryStateCityForm countryStateCityForm) throws Exception {
		Session session = null;
		State state;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from State a where (a.name = :stateName and a.country.id = :countryId) or a.bankStateId= :bankStateID");
			query.setString("stateName", countryStateCityForm.getStateName());
			query.setInteger("countryId", Integer.parseInt(countryStateCityForm.getCountryId()));
			query.setString("bankStateID", countryStateCityForm.getBankStateId());
			state = (State) query.uniqueResult();
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
