package com.kp.cms.transactionsimpl.fee;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.transactions.fee.IFeeGroupTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * 
 * Transaction Class for Fee category 
 * @Date 10/Feb/2009
 */
public class FeeGroupTransactionImpl implements IFeeGroupTransaction {
		
	private static final Log log = LogFactory.getLog(FeeGroupTransactionImpl.class);
	
	private static FeeGroupTransactionImpl feeGroupTransactionImpl = null;
	
	public static FeeGroupTransactionImpl getInstance() {
		   if(feeGroupTransactionImpl == null ){
			   feeGroupTransactionImpl = new FeeGroupTransactionImpl();
			   return feeGroupTransactionImpl;
		   }
		   return feeGroupTransactionImpl;
	}
	
	
	/**
	 * This method return list of FeeGroup all objects.
	 */
	
	public List<FeeGroup> getFeeGroups() throws Exception{
		log.info("call of getFeeGroups method in FeeGroupTransactionImpl class.");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 
			 Query query = session.createQuery("from FeeGroup feegroup where feegroup.isActive = :isActive");
			 query.setBoolean("isActive", true);
			 List<FeeGroup> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getFeeGroups method in FeeGroupTransactionImpl class.");
			 return list;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	 }

	

	
	/**
	 *	This method is used to get fee group details from database to UI. 
	 */
	
	public FeeGroup getFeeGroup(int groupId) throws Exception {
		log.info("call of getFeeGroup method in FeeGroupTransactionImpl class.");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeGroup where id = :groupId");
			 query.setInteger("groupId", groupId);
			 FeeGroup feeGroup = (FeeGroup)query.uniqueResult();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getFeeGroup method in FeeGroupTransactionImpl class.");
			 return feeGroup;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	 }

	/**
	 * This method is used to add fee group details to database.
	 */

	@Override
	public void addFeeGroupEntry(String feeGroupname, String optional, String userId) throws Exception {
		log.info("call of addFeeGroupEntry method in FeeGroupTransactionImpl class.");

		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			if (!isDuplicateEntry(feeGroupname, "",optional)) {

				FeeGroup feeGroup = new FeeGroup();
				feeGroup.setName(feeGroupname);
				feeGroup.setIsOptional(Boolean.valueOf(optional));
				feeGroup.setCreatedBy(userId);
				feeGroup.setModifiedBy(userId);
				feeGroup.setCreatedDate(new Date());
				feeGroup.setIsActive(true);
				session.save(feeGroup);
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
		log.info("end of addFeeGroupEntry method in FeeGroupTransactionImpl class.");
	}

	/**
	 * This method is used to delete fee group details from database by making isActive as 0.
	 */

	@Override
	public void deleteFeeGroupEntry(int feeGroupId, String userId) throws Exception {
		log.info("call of deleteFeeGroupEntry method in FeeGroupTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			transaction = session.beginTransaction();
			FeeGroup feeGroup = (FeeGroup) session.get(
					FeeGroup.class, feeGroupId);

			feeGroup.setIsActive(false);
			feeGroup.setModifiedBy(userId);
			feeGroup.setLastModifiedDate(new Date());
			session.update(feeGroup);

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
		log.info("end of deleteFeeGroupEntry method in FeeGroupTransactionImpl class.");
	}

	/**
	 * This method is used to restore fee group details from database.
	 */

	@Override
	public void reActivateFeeGroupEntry(String feeGroupname, String userId) throws Exception {
		log.info("call of reActivateFeeGroupEntry method in FeeGroupTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeGroup feeGroup where feeGroup.name = :GroupName");
			query.setString("GroupName", feeGroupname);
			FeeGroup feeGroup = (FeeGroup) query.uniqueResult();
			transaction = session.beginTransaction();
			feeGroup.setIsActive(true);
			feeGroup.setModifiedBy(userId);
			feeGroup.setLastModifiedDate(new Date());
			session.update(feeGroup);
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
		log.info("end of reActivateFeeGroupEntry method in FeeGroupTransactionImpl class.");
	}

	/**
	 * This method is used to update fee group details in database.
	 */

	@Override
	public void updateFeeGroupEntry(int feeGroupId, String feeGroupName, String option,String feeGroupNameOriginal, String userId)
			throws DuplicateException, ReActivateException, Exception {
		log.info("call of updateFeeGroupEntry method in FeeGroupTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();

			if (!isDuplicateEntry(feeGroupName,feeGroupNameOriginal,option)) {
				transaction = session.beginTransaction();
				FeeGroup feeGroup = (FeeGroup) session.get(
						FeeGroup.class, feeGroupId);
				feeGroup.setName(feeGroupName);
				feeGroup.setIsOptional(Boolean.valueOf(option));
				feeGroup.setModifiedBy(userId);
				feeGroup.setLastModifiedDate(new Date());
				session.update(feeGroup);
				transaction.commit();
			}

		}  catch (DuplicateException duplicateException) {
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
		log.info("end of updateFeeGroupEntry method in FeeGroupTransactionImpl class.");
	}
	
	/**
	 * This method is used to check duplicate fee group details.
	 * @param feeGroupname
	 * @param feeGroupNameOriginal
	 * @param option
	 * @return boolean value.
	 * @throws DuplicateException
	 * @throws ReActivateException
	 * @throws Exception
	 */
	
	private boolean isDuplicateEntry(String feeGroupname , String feeGroupNameOriginal, String option)
			throws DuplicateException, ReActivateException, Exception {
		log.info("call of isDuplicateEntry method in FeeGroupTransactionImpl class.");
		boolean isDuplicateEntry = true;
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from FeeGroup feeGroup where feeGroup.name = :GroupName  and feeGroup.isOptional = " +option);
			query.setString("GroupName", feeGroupname);
			FeeGroup feeGroup = (FeeGroup) query.uniqueResult();
			
			if(feeGroupNameOriginal.trim().length() >0) {
				if (feeGroup != null && feeGroup.getIsActive()) {
					 if (feeGroupname.equalsIgnoreCase(feeGroupNameOriginal)) {
							isDuplicateEntry = false;
						} else {
							throw new DuplicateException();
						}

				} else if (feeGroup != null && !feeGroup.getIsActive()) {
					throw new ReActivateException();
				} else {
					isDuplicateEntry = false;
				}
			} else {
				if (feeGroup != null && feeGroup.getIsActive() ) {
					throw new DuplicateException();
				} else if (feeGroup != null && !feeGroup.getIsActive()) {
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
		log.info("end of isDuplicateEntry method in FeeGroupTransactionImpl class.");
		return isDuplicateEntry;

	}
	
	/**
	 * This method return list of FeeGroup all objects.
	 */
	
	public List<FeeGroup> getOptionalFeeGroups() throws Exception{
		log.info("call of getOptionalFeeGroups method in FeeGroupTransactionImpl class.");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeGroup feegroup where feegroup.isOptional = :isOptional" +
			 									" and isActive = :isActive");
			 query.setBoolean("isOptional", true);
			 query.setBoolean("isActive", true);
			 List<FeeGroup> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getOptionalFeeGroups method in FeeGroupTransactionImpl class.");
			 return list;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	 }
	
	/**
	 * This method return list of FeeGroup all objects.
	 */
	public List<FeeGroup> getNonOptionalFeeGroups() throws Exception{
		log.info("call of getNonOptionalFeeGroups method in FeeGroupTransactionImpl class.");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeGroup feegroup where feegroup.isOptional = :isOptional" +
			 								   " and isActive = :isActive");
			 query.setBoolean("isOptional", false);
			 query.setBoolean("isActive", true);
			 List<FeeGroup> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getNonOptionalFeeGroups method in FeeGroupTransactionImpl class.");
			 return list;
		 } catch (Exception e) {
			 if( session != null){
				 session.flush();
				 //session.close();
			 }
			 throw new ApplicationException(e);
		 }
	 }
}