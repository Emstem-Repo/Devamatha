package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.transactions.fee.IFeeDivisionTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 *
 */
public class FeeDivisionTransactionImpl implements IFeeDivisionTransaction {

	private static FeeDivisionTransactionImpl feeDivisionTransactionImpl = null;

	public static FeeDivisionTransactionImpl getInstance() {
		if (feeDivisionTransactionImpl == null) {
			feeDivisionTransactionImpl = new FeeDivisionTransactionImpl();
			return feeDivisionTransactionImpl;
		}
		return feeDivisionTransactionImpl;
	}

	/**
	 * This method return list of FeeGroup all objects.
	 */
	public List<FeeDivision> getFeeDivisions() throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Query query = session
					.createQuery("from FeeDivision feeDivision where feeDivision.isActive = :isActive");
			query.setBoolean("isActive", true);
			List<FeeDivision> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return list;
		} catch (Exception e) {
			
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
		}
	}

	/**
	 * This method return list of FeeGroup all objects.
	 */
	public FeeDivision getFeeDivision(int divisionId) throws Exception {
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeDivision where id = :divisionId");
			query.setInteger("divisionId", divisionId);
			FeeDivision feeDivision = (FeeDivision) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			return feeDivision;
		} catch (Exception e) {
			if (session != null) {
				session.flush();
				//session.close();
			}
			throw new ApplicationException(e);
		}
	}

	private boolean isDuplicateEntry(String feeDivisionname , String feeDivisionNameOriginal)
			throws DuplicateException, ReActivateException, Exception {
		boolean isDuplicateEntry = true;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeDivision feeDivision where feeDivision.name = :divisionName");
			query.setString("divisionName", feeDivisionname);
			FeeDivision feeDivision = (FeeDivision) query.uniqueResult();
		
			if(!StringUtils.isEmpty(feeDivisionNameOriginal)) {
				
				 if (feeDivision != null && feeDivision.getIsActive()) {
					 if (feeDivisionname.equalsIgnoreCase(feeDivisionNameOriginal)) {
						isDuplicateEntry = false;
					} else {
						throw new DuplicateException();
					}

				} else if (feeDivision != null
						&& !feeDivision.getIsActive()) {
					throw new ReActivateException();
				} else {
					isDuplicateEntry = false;
				}
			} else {
				if (feeDivision != null && feeDivision.getIsActive()) {
					throw new DuplicateException();
				} else if (feeDivision != null
						&& !feeDivision.getIsActive()) {
					throw new ReActivateException();
				} else {
					isDuplicateEntry = false;
				}
			}
		
			
			
			

		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return isDuplicateEntry;

	}

	public void addFeeDivisionEntry(String feeDivisionname, String userName) throws DuplicateException,ReActivateException,ApplicationException {

		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (!isDuplicateEntry(feeDivisionname,"")) {

				FeeDivision feeDivision = new FeeDivision();
				feeDivision.setName(feeDivisionname);
				feeDivision.setCreatedDate(new Date());
				feeDivision.setIsActive(true);
				feeDivision.setCreatedBy(userName);
				feeDivision.setModifiedBy(userName);
				feeDivision.setLastModifiedDate(new Date());
				session.save(feeDivision);
				transaction.commit();
			}
		} catch (DuplicateException duplicateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw duplicateException;
		} catch (ReActivateException reActivateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw reActivateException;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);

		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}

	}

	public void updateFeeDivisionEntry(int feeDivisionId, String feeDivisionName,String originalDivisionName, String userName)
			throws DuplicateException, ReActivateException, Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			if (!isDuplicateEntry(feeDivisionName,originalDivisionName)) {
				transaction = session.beginTransaction();
				FeeDivision feeDivision = (FeeDivision) session.get(
						FeeDivision.class, feeDivisionId);
				feeDivision.setName(feeDivisionName);
				feeDivision.setModifiedBy(userName);
				feeDivision.setLastModifiedDate(new Date());
				session.update(feeDivision);
				transaction.commit();
			}

		} catch (DuplicateException duplicateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw duplicateException;
		} catch (ReActivateException reActivateException) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw reActivateException;
		} catch (Exception e) {
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
	}

	@Override
	public void deleteFeeDivisionEntry(int feeDivisionId) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			transaction = session.beginTransaction();
			FeeDivision feeDivision = (FeeDivision) session.get(
					FeeDivision.class, feeDivisionId);

			feeDivision.setIsActive(false);
			feeDivision.setLastModifiedDate(new Date());
			session.update(feeDivision);

			transaction.commit();
		} catch (Exception e) {
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

	}

	@Override
	public void reActivateFeeDivisionEntry(String feeDivisionname)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeDivision feeDivision where feeDivision.name = :divisionName");
			query.setString("divisionName", feeDivisionname);
			FeeDivision feeDivision = (FeeDivision) query.uniqueResult();
			transaction = session.beginTransaction();
			feeDivision.setIsActive(true);
			feeDivision.setLastModifiedDate(new Date());
			session.update(feeDivision);
			transaction.commit();
		} catch (Exception e) {
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
		
	}

}
