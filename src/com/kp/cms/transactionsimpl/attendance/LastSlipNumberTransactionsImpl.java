package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.actions.attendance.LastSlipNumberAction;
import com.kp.cms.bo.admin.AttendanceSlipNumber;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.attandance.ILastSlipNumberTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class LastSlipNumberTransactionsImpl implements
		ILastSlipNumberTransaction {

	public static final Log log = LogFactory.getLog(LastSlipNumberAction.class);

	private static volatile LastSlipNumberTransactionsImpl slipNumberTransactionImpl;

	/**
	 * used to return slipNumberTransactionImpl object
	 * 
	 * @return
	 */
	public LastSlipNumberTransactionsImpl getInstance() {
		if (slipNumberTransactionImpl == null) {
			slipNumberTransactionImpl = new LastSlipNumberTransactionsImpl();
			return slipNumberTransactionImpl;
		}
		return slipNumberTransactionImpl;

	}

	/*
	 * used to get financialYear list
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * getFinancialYearList()
	 */
	@Override
	public List<PcFinancialYear> getFinancialYearList() throws Exception {
		// TODO Auto-generated method stub
		log.debug("inside getFinancialYear List");
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from PcFinancialYear year where year.isActive = true");
			List<PcFinancialYear> pcFinancialYearList = query.list();
			return pcFinancialYearList;
		} catch (Exception e) {
			log
					.error("Exception occured in getting all financial years in getFinancialYearList :"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}

	}

	/*
	 * used to get slipNumberDetails
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * getSlipNumberDetails()
	 */
	@Override
	public List<AttendanceSlipNumber> getSlipNumberDetails() throws Exception {
		// TODO Auto-generated method stub

		log.info("inside getSlipNumberDetails List");
		Session session = null;
		List<AttendanceSlipNumber> attendanceSlipNumberList;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AttendanceSlipNumber a where a.isActive=1");
			attendanceSlipNumberList = query.list();

		} catch (Exception e) {
			log
					.error("Exception occured in getting all slipnumbers in LastSlipNumberTransactionImpl :"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return attendanceSlipNumberList;
	}

	/*
	 * used to get lastSlipNumber particular year
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * getLastSlipNumberYear(int)
	 */
	@Override
	public AttendanceSlipNumber getLastSlipNumberYear(int year)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.info("Inside of getLastSlipNumberYear of LastSlipNumberTransactionImpl");
		Session session = null;
		AttendanceSlipNumber attendanceSlipNumberYear = null;

		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from AttendanceSlipNumber a where a.pcFinancialYear.id = :row");
			query.setInteger("row", year);
			attendanceSlipNumberYear = (AttendanceSlipNumber) query
					.uniqueResult();
		} catch (Exception e) {
			log
					.error("Exception occured while getting the row based on the year in IMPL :"
							+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null)
				session.flush();
		}
		log
				.info("Exiting getLastSlipNumberYear of LastSlipNumberTransactionImpl");
		return attendanceSlipNumberYear;
	}

	/*
	 * used to add lastSlipNumber
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * addLastSlipNumber(com.kp.cms.bo.admin.AttendanceSlipNumber)
	 */
	@Override
	public boolean addLastSlipNumber(AttendanceSlipNumber attendanceSlipNumber)
			throws Exception {
		// TODO Auto-generated method stub
		log.info("Inside  addLastSlipNumber of LastSlipNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(attendanceSlipNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error(" Exception occured in adding last slip number in IMPL :"
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}

		}

	}

	/*
	 * used to delete lastSlipNumber
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * deleteLastSlipNumber(int, java.lang.String)
	 */
	@Override
	public boolean deleteLastSlipNumber(int slipId, String userId)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.info("Inside  deleteLastSlipNumber of LastSlipNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			AttendanceSlipNumber attendanceSlipNumber = (AttendanceSlipNumber) session
					.get(AttendanceSlipNumber.class, slipId);
			attendanceSlipNumber.setIsActive(false);
			attendanceSlipNumber.setModifiedBy(userId);
			attendanceSlipNumber.setLastModifiedDate(new Date());
			session.update(attendanceSlipNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("Exception occured in deleting deleteLastSlipNumber in IMPL :"
							+ e);
			throw new ApplicationException(e);

		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}

	}

	/*
	 * used to get lastSlipNumberDetails based on id
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * getLastSlipNumberDetailsonId(int)
	 */
	@Override
	public AttendanceSlipNumber getLastSlipNumberDetailsonId(int id)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.info("Inside of getLastSlipNumberDetailsonId of LastSLipNumberTransactionImpl");
		Session session = null;
		AttendanceSlipNumber attendanceSlipNumber = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			Query query = session
					.createQuery("from AttendanceSlipNumber a where a.id=:row");
			query.setInteger("row", id);
			attendanceSlipNumber = (AttendanceSlipNumber) query.uniqueResult();
			
		} catch (Exception e) {
			log
					.error("Exception occured in getLastSlipNumberDetailsonId in IMPL :"
							+ e);
			throw new ApplicationException(e);

		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("End of getLastSlipNumberDetailsonId of LastSLipNumberTransactionImpl");
		return attendanceSlipNumber;
	}

	/*
	 * used to reActivate Last Slip Number
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * reActivateLastSlipNumber(int, java.lang.String)
	 */
	@Override
	public boolean reActivateLastSlipNumber(int year, String userId)
			throws Exception {
		// TODO Auto-generated method stub
		log
				.info("Inside of reActivateLastSlipNumber of LastSLipNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession(); 
				//HibernateUtil.getSession();
			Query query = session
					.createQuery("from AttendanceSlipNumber a where a.pcFinancialYear.id = :year");
			query.setInteger("year", year);
			AttendanceSlipNumber attendanceSlipNumber = (AttendanceSlipNumber) query
					.uniqueResult();
			transaction = session.beginTransaction();
			attendanceSlipNumber.setIsActive(true);
			attendanceSlipNumber.setModifiedBy(userId);
			attendanceSlipNumber.setLastModifiedDate(new Date());
			session.update(attendanceSlipNumber);
			transaction.commit();

			return true;

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
				log
						.error("Exception occured in getLastSlipNumberDetailsonId in IMPL :"
								+ e);
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();

			}
		}

	}

	/*
	 * used to update last slip number
	 * 
	 * @seecom.kp.cms.transactions.attandance.ILastSlipNumberTransaction#
	 * updateLastSlipNumber(com.kp.cms.bo.admin.AttendanceSlipNumber)
	 */
	@Override
	public boolean updateLastSlipNumber(
			AttendanceSlipNumber attendanceSlipNumber) throws Exception {
		// TODO Auto-generated method stub
		log.info("Inside of updateLastSlipNumber of LastSLipNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.update(attendanceSlipNumber);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log
					.error("Exception occured while updating AttendanceSlipNumber in IMPL :"
							+ e);
			throw new ApplicationException(e);

		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
			log.info("Exiting updateLastSlipNumber of LastSlipNumberTransactionImpl");
		}

	}

}
