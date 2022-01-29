package com.kp.cms.transactionsimpl.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.IOfflineDetailsTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * @author kshirod.k
 * A TransactionImpl class for OfflineDetails Entry
 *
 */
public class OfflineDetailsTransactionImpl implements IOfflineDetailsTransaction{
	private static final Log log = LogFactory.getLog(OfflineDetailsTransactionImpl.class);
	/**
	 * Used while adding
	 */
	public boolean addOfflineDetails(OfflineDetails details) throws Exception{
		log.info("Entering into OfflineDetailsTransactionImpl of addOfflineDetails");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(details);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while adding Offline Details in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into OfflineDetailsTransactionImpl of addOfflineDetails");
		}
	}

	/**
	 * Used to get the max receipt no. (Auto generated)
	 */

	public int getMaxReceiptNo()throws Exception {
		log.info("Entering into OfflineDetailsTransactionImpl of getMaxReceiptNo");
		Session session = null;
		List<OfflineDetails> offlineDetails;
		int maxReceiptNo;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			offlineDetails = session.createQuery("from OfflineDetails details").list();
			if(offlineDetails.isEmpty()){
				maxReceiptNo=0;
			}else {
				maxReceiptNo = (Integer) session.createQuery("select max(receiptNo) from OfflineDetails details").uniqueResult();
			}
		} catch (Exception e) {
		log.error("Error occured in getMaxReceiptNo of Offline Impl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into OfflineDetailsTransactionImpl of getMaxReceiptNo");
		return maxReceiptNo;
	}
	/**
	 * Used to get all active offline details
	 */

	public List<OfflineDetails> getAllOfflineDetails() throws Exception{
		log.info("Entering into OfflineDetailsTransactionImpl of getAllOfflineDetails");
		Session session = null;
		List<OfflineDetails> offlineDetailsBOList;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			offlineDetailsBOList = session.createQuery("from OfflineDetails details where details.isActive = 1").list();
			}
		catch (Exception e) {
		log.error("Error in getAllOfflineDetails of OfflineDetails Impl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into OfflineDetailsTransactionImpl of getAllOfflineDetails");
		return offlineDetailsBOList;
	}
	/**
	 * Used in delete
	 */
	public boolean deleteOfflineDetails(int id)throws Exception{
		log.info("Entering into OfflineDetailsTransactionImpl of deleteOfflineDetails");
		Session session = null;
		Transaction transaction = null;
		OfflineDetails details;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			details= new OfflineDetails();
			details.setId(id);
			session.delete(details);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting OfflineDetails in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		log.info("Leaving into OfflineDetailsTransactionImpl of deleteOfflineDetails");
		}
	}
	
	/**
	 * Used while edit button is clicked
	 */

	public OfflineDetails getOfflineDetailsbyId(int id)throws Exception {
		log.info("Entering into OfflineDetailsTransactionImpl of getOfflineDetailsbyId");
		Session session = null;
		OfflineDetails offlineDetails;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			offlineDetails = (OfflineDetails)session.createQuery("from OfflineDetails details where details.id =" + id).uniqueResult();
		} catch (Exception e) {
			log.error("Error occured while getting getOfflineDetailsbyId in impl");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			}
		log.info("Leaving into OfflineDetailsTransactionImpl of getOfflineDetailsbyId");
		return offlineDetails;
	}
	
	/**
	 * Used in updation
	 */
	public boolean updateOfflineDetails(OfflineDetails offlineDetails)throws Exception{
		log.info("Entering into OfflineDetailsTransactionImpl of updateOfflineDetails");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.update(offlineDetails);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in updating offline details in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into OfflineDetailsTransactionImpl of updateOfflineDetails");
		}
	}
	
	/**
	 * Used for duplication check
	 * Returns based on the course and application no.
	 */
	
	public OfflineDetails getOfflineDetailsonApplNoYear(String applnNo, int year) throws Exception {
		log.info("Entering into OfflineDetailsTransactionImpl of getOfflineDetailsonCourseApplNo");
		Session session = null;
		OfflineDetails offlineDetails;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			offlineDetails=(OfflineDetails) session.createQuery("from OfflineDetails off where off.applnNo="+ applnNo+ "and off.year=" + year + "and off.isActive=1").uniqueResult();
			return offlineDetails;
		} catch (Exception e) {
			log.error("Error occured in getOfflineDetailsonCourseApplNo in impl");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("Leaving from OfflineDetailsTransactionImpl of getOfflineDetailsonCourseApplNo");
			}
		}
	
	/**
	 * 
	 */
	public OfflineDetails getOfflineDetailsByNoYear(int applnNo, int year) throws Exception {
		log.info("Entering into OfflineDetailsTransactionImpl of getOfflineDetailsonCourseApplNo");
		Session session = null;
		OfflineDetails offlineDetails;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from OfflineDetails off where off.applnNo= :appno and off.year =:year");
			query.setInteger("appno", applnNo);
			query.setInteger("year", year);
			offlineDetails=(OfflineDetails) query.uniqueResult();
			return offlineDetails;
		} catch (Exception e) {
			log.error("Error occured in getOfflineDetailsonCourseApplNo in impl");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
			log.info("Leaving from OfflineDetailsTransactionImpl of getOfflineDetailsonCourseApplNo");
			}
		}

	/**
	 * This method is used to check the offline application number range for a course
	 */
	public ApplicationNumber checkValidOfflineNumberForCourse(int applicationNo, int year)throws Exception {
		log.info("Entering into OfflineDetailsTransactionImpl of checkValidOfflineNumberForCourse");
		Session session = null;
		ApplicationNumber applicationNumber;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
				applicationNumber=(ApplicationNumber) session.createQuery("from ApplicationNumber number " +
				" where number.isActive = 1 " +
				" and number.year = "+year
				+" and "+applicationNo
				+" between number.offlineApplnNoFrom and number.offlineApplnNoTo").uniqueResult();
			return applicationNumber;
		} catch (Exception e) {
			log.error("Error occured in checkValidOfflineNumberForCourse in impl");
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		log.info("Leaving from OfflineDetailsTransactionImpl of checkValidOfflineNumberForCourse");
		}
	}

	
	}