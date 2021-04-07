package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Country;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.fee.IFeeApplicableTransaction;
import com.kp.cms.transactions.fee.IFeeHeadingTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class FeeHeadingTransactionImpl implements IFeeApplicableTransaction,
		IFeeHeadingTransaction {
	
	private static final Log log = LogFactory.getLog(FeeHeadingTransactionImpl.class);

	/**
	 * This method is return a single instance of FeeHeadingTransactionImpl.
	 */
	private static FeeHeadingTransactionImpl feeApplicableTransactionImpl = null;

	public static FeeHeadingTransactionImpl getInstance() {
		if (feeApplicableTransactionImpl == null) {
			feeApplicableTransactionImpl = new FeeHeadingTransactionImpl();
			return feeApplicableTransactionImpl;
		}
		return feeApplicableTransactionImpl;
	}

	/**
	 * This method is used to get all FeeApplicable details from database.
	 * @return List containing all the FeeApplicable.
	 * @throws Exception
	 */

	@Override
	public List getAllFeeApplicable() throws Exception {
		log.info("call of getAllFeeApplicable in FeeHeadingTransactionImpl class.");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ApplicableFees");
			List<Country> list = query.list();
			//session.close();
			//sessionFactory.close();
			log.info("end of getAllFeeApplicable in FeeHeadingTransactionImpl class.");
			return list;
		} catch (Exception e) {
			log.error("Unable to get FeeApplicable" + e);
			throw e;
		}
	}

	/**
	 * This method is used for Duplicate entry checking.
	 * @return instance of FeeHeading BO.
	 * @param feeGroupId and feesName.
	 */

	@Override
	public FeeHeading isFeeHeadingNameExist(int feeGroupId, String feesName)
			throws Exception {
		log.info("call of FeeHeadingNameExist in FeeHeadingTransactionImpl class.");
		Session session = null;
		FeeHeading feeHeading;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query q = session
					.createQuery("from FeeHeading fh where fh.feeGroup.id= :feeGroupId and fh.name= :feesName ");
			q.setInteger("feeGroupId", feeGroupId);
			q.setString("feesName", feesName);
			feeHeading = (FeeHeading) q.uniqueResult();

		} catch (Exception e) {
			log.error("Unable to get subjectgroup" + e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of FeeHeadingNameExist in FeeHeadingTransactionImpl class.");
		return feeHeading;
	}

	/**
	 * This method is used for saving a record to database.
	 * @return boolean value after adding.
	 * @param FeeHeading BO instance.
	 */

	@Override
	public boolean addFeeHeading(FeeHeading feeHeading) throws Exception {
		log.info("call of addFeeHeading in FeeHeadingTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(feeHeading);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to add FeeHeadings" + e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of addFeeHeading in FeeHeadingTransactionImpl class.");
		return isAdded;
	}

	@Override
	public List getAllFeeHeadings(int groupId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This method is used to get all FeeHeading details from database.
	 * @return list of type FeeHeading from database.
	 */

	@Override
	public List<FeeHeading> getAllFeeHeadings() throws Exception {
		log.info("call of getAllFeeHeadings in FeeHeadingTransactionImpl class.");
		Session session = null;
		List<FeeHeading> list;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeHeading fh where fh.isActive= :isActive");
			query.setBoolean("isActive", true);
			list = query.list();
		} catch (Exception e) {
			log.error("Unable to get FeeHeadings" + e);
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getAllFeeHeadings in FeeHeadingTransactionImpl class.");
		return list;
	}

	/**
	 * This method is used for getting a record from database based on id for
	 * edit purpose.
	 * @return list of FeeHeading.
	 * @param id of type int.
	 */

	@Override
	public List<FeeHeading> editFeeHeading(int id) throws Exception {
		log.info("call of editFeeHeading in FeeHeadingTransactionImpl class.");
		Session session = null;
		List<FeeHeading> feeHeadingList;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeHeading fh where fh.id= :id");
			query.setInteger("id", id);
			feeHeadingList = query.list();
		} catch (Exception e) {
			log.error("Unable to get FeeHeadings for edit" + e);
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of editFeeHeading in FeeHeadingTransactionImpl class.");
		return feeHeadingList;
	}

	/**
	 * This method is used for updating a record to database.
	* @return boolean value.
	 * @param FeeHeading
	 *  BO instance.
	 */

	@Override
	public boolean updateFeeHeadings(FeeHeading feeHeading) throws Exception {
		log.info("call of updateFeeHeadings in FeeHeadingTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.update(feeHeading);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to update FeeHeadings" + e);
			throw e;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of updateFeeHeadings in FeeHeadingTransactionImpl class.");
		return isUpdated;
	}

	/**
	 * This method is used for deleting a record from database and making
	 * isActive = false.
	 * @return boolean value.
	 * @param id of type int.
	 */

	@Override
	public boolean deleteFeeHeadings(int id, String userId) throws Exception {
		log.info("call of deleteFeeHeadings in FeeHeadingTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			FeeHeading feeHeading = (FeeHeading) session.get(FeeHeading.class,
					id);
			feeHeading.setModifiedBy(userId);
			feeHeading.setLastModifiedDate(new Date());
			feeHeading.setIsActive(Boolean.FALSE);
			session.update(feeHeading);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" + e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteFeeHeadings in FeeHeadingTransactionImpl class.");
		return isDeleted;
	}

	/**
	 * This method is used for restoring a record from database based on id and
	 * setting isActive = true.
	 * @param feesName of type String.
	 * 
	 */

	@Override
	public void reActivateFeeHeadings(String feesName, String userId)
			throws Exception {
		log.info("call of reActivateFeeHeadings in FeeHeadingTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeHeading fh where fh.name = :name");
			query.setString("name", feesName);
			FeeHeading feeHeading = (FeeHeading) query.uniqueResult();
			transaction = session.beginTransaction();
			feeHeading.setIsActive(true);
			feeHeading.setModifiedBy(userId);
			feeHeading.setLastModifiedDate(new Date());
			session.update(feeHeading);
			transaction.commit();
		} catch (Exception e) {
			log.error("Unable to restore FeeHeadings" + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of reActivateFeeHeadings in FeeHeadingTransactionImpl class.");
	}
}