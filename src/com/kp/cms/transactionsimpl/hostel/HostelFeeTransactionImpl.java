package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelFeeTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HostelFeeTransactionImpl implements IHostelFeeTransactions {
	private static final Log log = LogFactory
			.getLog(HostelFeeTransactionImpl.class);
	private static volatile HostelFeeTransactionImpl hostelFeeTransactionImpl = null;

	public static HostelFeeTransactionImpl getInstance() {
		if (hostelFeeTransactionImpl == null) {
			hostelFeeTransactionImpl = new HostelFeeTransactionImpl();
		}
		return hostelFeeTransactionImpl;
	}

	@Override
	public List<HlFeeType> getFeeListToDisplay() throws Exception {
		log.debug("Entering getFeeListToDisplay in HostelFeeTransactionImpl");
		Session session = null;
		List<HlFeeType> result;

		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlFeeType h where h.isActive=1 order by h.name asc");
			List<HlFeeType> list = query.list();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences...", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.debug("Exiting getFeeListToDisplay in HostelFeeTransactionImpl");
		return result;
	}

	@Override
	public List<HlFees> getFeeDetailedListToDisplay() throws Exception {
		log
				.debug("Entering getFeeDetailedListToDisplay in HostelFeeTransactionImpl");
		Session session = null;
		List<HlFees> result;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlFees h where h.isActive = 1 and h.hlRoomType.isActive=1 and h.hlHostel.isActive=1 group by h.hlHostel,h.hlRoomType");
			List<HlFees> list = query.list();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log
				.debug("Exiting getFeeDetailedListToDisplay in HostelFeeTransactionImpl");
		return result;
	}

	@Override
	public List<HlFees> getFeeDetailedListToView(int hostelId, int roomTypeId)
			throws Exception {
		log
				.debug("Entering getFeeDetailedListToView in HostelFeeTransactionImpl");
		Session session = null;
		List<HlFees> result;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String queryString = "from HlFees h where h.isActive = 1 and h.hlRoomType.isActive=1 and h.hlHostel.isActive=1 and h.hlFeeType.isActive=1 and h.hlHostel.id ="
					+ hostelId + "  and h.hlRoomType.id =" + roomTypeId;
			Query query = session.createQuery(queryString);
			List<HlFees> list = query.list();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log
				.debug("Exiting getFeeDetailedListToView in HostelFeeTransactionImpl");
		return result;
	}

	@Override
	public boolean deleteFeeDetails(int hostelId, int roomId) throws Exception {
		log.info("Entering  deleteFeeDetails of  HostelFeeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		boolean isdeleted = false;
		try {
			// session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query qury = session
					.createQuery("update HlFees hf set hf.isActive=0 where hf.hlHostel.id=:hostelId and hf.hlRoomType.id= :roomTypeId");
			qury.setInteger("hostelId", hostelId);
			qury.setInteger("roomTypeId", roomId);

			int result = qury.executeUpdate();
			if (result > 0)
				isdeleted = true;
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in deleting RecommendedBy in IMPL :"
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("Exiting deleteFeeDetails of HostelFeeTransactionImpl");
		return isdeleted;
	}

	@Override
	public String saveFeeDetails(List<HlFees> hlFeeList) throws Exception {
		log.info("Inside of saveFeeDetails of HostelFeeTransactionImpl");
		String isSaved = "false";
		HlFees hlFees;
		Transaction tx = null;
		Session session = null;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (hlFeeList != null) {
				Iterator<HlFees> itr = hlFeeList.iterator();
				int count = 0;
				tx = session.beginTransaction();
				tx.begin();
				while (itr.hasNext()) {
					hlFees = itr.next();
					session.save(hlFees);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}

				}
			}
			tx.commit();
			// sessionFactory.close();
			log.info("End of saveFeeDetails of HostelFeeTransactionImpl");
			isSaved = "true";
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}

			log
					.error("Error occured saveFeeDetails of HostelFeeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
				session.close();
			}

		}
		return isSaved;
	}

	@Override
	public List<HlFees> getFeeDetailedListForActive(int hostelId, int roomTypeId)
			throws Exception {
		log
				.debug("Entering getFeeDetailedListForActive in HostelFeeTransactionImpl");
		Session session = null;
		List<HlFees> result;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlFees h where h.isActive = 0 and h.hlHostel.id ="
							+ hostelId + "  and h.hlRoomType.id =" + roomTypeId);
			List<HlFees> list = query.list();
			result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log
				.debug("Exiting getFeeDetailedListForActive in HostelFeeTransactionImpl");
		return result;
	}

	public boolean reActivateFeeList(List<HlFees> hlFeeBoList) throws Exception {
		log.info("Inside of reActivateFeeList of HostelFeeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		boolean isSaved = false;
		HlFees hlFees;
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			if (hlFeeBoList != null) {
				Iterator<HlFees> itr = hlFeeBoList.iterator();
				int count = 0;
				transaction = session.beginTransaction();
				transaction.begin();
				while (itr.hasNext()) {
					hlFees = itr.next();
					session.update(hlFees);
					if (++count % 20 == 0) {
						session.flush();
						session.clear();
					}

				}
			}
			transaction.commit();
			// sessionFactory.close();
			log.info("End of reActivateFeeList of HostelFeeTransactionImpl");
			isSaved = true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}

			log
					.error("Error occured reActivateFeeList of HostelFeeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.clear();
				session.close();
			}

		}
		return isSaved;
	}

	public boolean updateFeeDetails(List<HlFees> hlFeesBo) throws Exception {
		log.info("Start of updateHostelFees of HostelFeeTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			// session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<HlFees> itrerator = hlFeesBo.iterator();
			while (itrerator.hasNext()) {
				HlFees hlFees = (HlFees) itrerator.next();
				session.merge(hlFees);
			}

			transaction.commit();
			log.info("End of updateHostelFees of HostelFeeTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("Exception occured in updateHostelFees in HostelFeeTransactionImpl : "
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

}
