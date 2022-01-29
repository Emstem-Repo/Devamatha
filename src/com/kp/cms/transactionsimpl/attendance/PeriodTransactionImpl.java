package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.Period;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.attendance.PeriodForm;
import com.kp.cms.transactions.attandance.IPeriodTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PeriodTransactionImpl implements IPeriodTransaction{
	private static final Log log = LogFactory.getLog(PeriodTransactionImpl.class);	
	public static volatile PeriodTransactionImpl periodTransactionImpl = null;
	public static PeriodTransactionImpl getInstance() {
		if (periodTransactionImpl == null) {
			periodTransactionImpl = new PeriodTransactionImpl();
			return periodTransactionImpl;
		}
		return periodTransactionImpl;
	}
	/***
	 * this method will add new Period record 
	 */
	public boolean addPeriod(List<Period> periodList, String mode) throws Exception {
		log.debug("inside addPeriod");
		Session session = null;
		Transaction transaction = null;
		Period period;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			//session = HibernateUtil.getSessionFactory().openSession();
			Iterator<Period> itr = periodList.iterator();
			int count = 0;
			transaction = session.beginTransaction();
			transaction.begin();
			while (itr.hasNext()) {
				period = itr.next();
				if(mode.equalsIgnoreCase("Add")){
					session.save(period);
				}
				else
				{
					session.saveOrUpdate(period);
				}
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();
			session.close();
			//sessionFactory.close();
			log.info("End of addPeriod TransactionImpl");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.debug("Error during saving period data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.debug("Error during saving period data...", e);
			throw new ApplicationException(e);
		}
	}
	/**
	 * 
	 * This method returns a list of Period object with all the records, when there is id returns a single record according to the id
	 * 
	 * @throws ApplicationException
	 */

	public List<Period> getPeriod(int id) throws Exception {
		Session session = null;
		List<Period> periodList;

		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			if (id != 0) {
				Query query = session.createQuery("from Period a where id = :id and isActive=1");
				query.setInteger("id", id);
				List<Period> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				periodList = list;
			} else {
				Query query = session.createQuery("from Period a where isActive=1");
				List<Period> list = query.list();
				session.flush();
				//session.close();
				//sessionFactory.close();
				periodList = list;
			}

		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList;
	}

	/**
	 * 
	 * This method returns a List of Period object
	 * 
	 * 	@throws ApplicationException
 	*/

	public List<Period> getAllPeriod(String year) throws Exception{
		Session session = null;
		List<Period> periodList;
	
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Period p where p.isActive=1 and p.classSchemewise.curriculumSchemeDuration.academicYear=:year and p.classSchemewise.classes.isActive=1 order by trim(p.classSchemewise.classes.name)");
			query.setInteger("year", Integer.parseInt(year));
			List<Period> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			periodList = list;
	
		} catch (Exception e) {
			log.debug("Error during getting applicaition numbers...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		return periodList;
	}	
	/**
	 * checking for duplication
	 */
	
	public Boolean isClassAndPeriodNameDuplicated(int classId, String periodName, int periodId)  throws Exception {
		log.debug("inside isClassAndPeriodNameDuplicated");
		Session session = null;
		Period period;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from Period where classSchemewise.id = :clsId and periodName = :periodNm and isActive = 1");
			if(periodId!= 0){
				sqlString = sqlString.append(" and id != :perId ");
			}
			Query query = session.createQuery(sqlString.toString());
				
			query.setInteger("clsId", classId);
			query.setString("periodNm", periodName);
			if(periodId!= 0){
				query.setInteger("perId", periodId);
			}
			period = (Period) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isClassAndPeriodNameDuplicated");
			if(period!= null){
				return true;
			}
			else
			{
				return false;
			}
		} catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
	
	/**
	 * This will delete a period from database.
	 * 
	 * @return true/false
	 * @throws Exception
	 */

	public Boolean deletePeriod(int perId,Boolean activate,PeriodForm periodForm) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
//			session.delete(period);
			Period period=(Period)session.get(Period.class, perId);
			period.setLastModifiedDate(new Date());
			period.setModifiedBy(periodForm.getUserId());
			period.setIsActive(activate);
			session.update(period);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.debug("Error during deleting period data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.debug("Error during deleting period data...", e);
			throw new ApplicationException(e);
		}
		return result;
	}
	
	/**
	 * checking for duplication
	 */
	
	public Boolean isClassAndPeriodDuplicated(int classId, String startTime, String endTime, int periodId)  throws Exception {
		log.debug("inside isClassAndPeriodDuplicated");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from Period where classSchemewise.id = :clsId and isActive = 1 and " +
								" ((:stTime between startTime and endTime and :stTime <> endTime ) or " +
								" (:enTime between startTime and endTime ) or " +
								" (:stTime <= startTime and :enTime >= endTime))");
			if(periodId!= 0){
				sqlString = sqlString.append(" and id != :perId ");
			}
			Query query = session.createQuery(sqlString.toString());
			query.setInteger("clsId", classId);
			query.setString("stTime", startTime);
			query.setString("enTime", endTime);
			if(periodId!= 0){
				query.setInteger("perId", periodId);
			}
			List<Period> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving isClassAndPeriodDuplicated");
			if(list!= null && list.isEmpty()){
				return false;
			}
			else
			{
				return true;
			}
		} catch (Exception e) {
			log.debug("Error during duplcation checking...", e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}
		
}
