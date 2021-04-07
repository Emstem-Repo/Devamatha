package com.kp.cms.transactionsimpl.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeAdditionalAccountAssignment;
import com.kp.cms.transactions.fee.IFeeAdditionalTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author microhard
 * Transaction class for FeeAdditional- CRUD operation for Additional fees.
 */
public class FeeAddtionalTransactionImpl implements IFeeAdditionalTransaction{
	private static final Log log = LogFactory.getLog(FeeAddtionalTransactionImpl.class);	
	private static FeeAddtionalTransactionImpl feeAddtionalTransactionImpl = null;
	
	/*
	 * return Singleton object each time.
	 */
	public static FeeAddtionalTransactionImpl getInstance() {
		    if(feeAddtionalTransactionImpl == null ){
		    	feeAddtionalTransactionImpl = new FeeAddtionalTransactionImpl();
		       return feeAddtionalTransactionImpl;
		    }
		    return feeAddtionalTransactionImpl;
	}
	
	/**
	 * This method will returns all the Fee additional.
	 */
	public List<FeeAdditional> getFeeAdditional(Set<Integer> feeGroupSet) throws Exception{
		log.debug("Trxn Impl : Entering getFeeAdditional ");
		Session session = null;
		try {
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from FeeAdditional where feeGroup.id in (:additionalFeeGroupSet) " +
			 									" and isActive = :isActive ");
			 query.setParameterList("additionalFeeGroupSet", feeGroupSet);
			 query.setBoolean("isActive",true);
			 List<FeeAdditional> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.debug("Trxn Impl : Leaving getFeeAdditional");
			 return list;
		 } catch (Exception e) {
			 log.debug("Exception"+e.getMessage());
			 throw e;
		 }
		 
	}
	
	/**
	 * This method add the FeeAddiional and FeeAdditionalAccountAssignment.
	 */
	public boolean addFeeAdditionalAssignment(FeeAdditional fee) throws ConstraintViolationException,Exception{
		log.debug("Trxn Impl : Entering getFeeAdditional ");
		Session session = null; 
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			 
			tx.begin();
			session.save(fee);
			tx.commit();
			session.close();
			log.debug("Trxn Impl : Leaving getFeeAdditional with success");
	    	return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			//session.close();
			log.debug("Trxn Impl : Leaving getFeeAdditional with Exception");
			throw e;				 
		} catch (Exception e) {
			tx.rollback();
			//session.close();
			log.debug("Trxn Impl : Leaving getFeeAdditional with Exception");
			throw e;				 
		} 
			 
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.fee.IFeeAssignmentTransaction#getAllFeeAssignments()
	 */
	
	public List<FeeAdditional> getAllAdditionalFees() throws Exception {
		log.debug("Trxn Impl : Entering getAllAdditionalFees ");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from FeeAdditional where "+
					 					       " feeGroup.isActive = 1 "+
  			 								   " and isActive = :isActive");
			query.setBoolean("isActive",true);
			List<FeeAdditional> list = query.list();
			//session.close();
			//sessionFactory.close();
			log.debug("Trxn Impl : Leaving getAllAdditionalFees with success");
			return list;
		 } catch (Exception e) {
			log.debug("Trxn Impl : Leaving getAllAdditionalFees with exception");
			throw e;
		 }
	}
	
	/**
	 * This method delete the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return true / flase based on result.
	 */
	public boolean deleteFeeAdditionalAssignment(FeeAdditional fee) throws Exception{
		log.debug("Trxn Impl : Entering deleteFeeAdditionalAssignment ");
		Session session = null;
		Transaction tx = null;
		FeeAdditional persistantFee;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    tx = session.beginTransaction();
			tx.begin();

			persistantFee = (FeeAdditional)session.get(FeeAdditional.class, fee.getId());
			Iterator<FeeAdditionalAccountAssignment> itr = persistantFee.getFeeAdditionalAccountAssignments().iterator();
			while(itr.hasNext()){
				 itr.next().setIsActive(false);
			}
			persistantFee.setIsActive(false);
			persistantFee.setModifiedBy(fee.getModifiedBy());
			persistantFee.setLastModifiedDate(fee.getLastModifiedDate());		 
			tx.commit();
			//session.close();
			log.debug("Trxn Impl : Leaving deleteFeeAdditionalAssignment with success");
	    	return true;
		 } catch (Exception e){
			log.debug("Trxn Impl : Leaving deleteFeeAdditionalAssignment with Exception");
			tx.rollback();
			//session.close();
			throw e;
		 }
	 }
	
	/**
	 * This method delete the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return true / flase based on result.
	 */
	public boolean activateFeeAdditionalAssignment(FeeAdditional fee) throws Exception{
		log.debug("Trxn Impl : Leaving activateFeeAdditionalAssignment"); 
		Session session = null;
		Transaction tx = null;
		FeeAdditional persistantFee;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    tx = session.beginTransaction();
		    tx.begin();

			persistantFee = (FeeAdditional)session.get(FeeAdditional.class, fee.getId());
			Iterator<FeeAdditionalAccountAssignment> itr = persistantFee.getFeeAdditionalAccountAssignments().iterator();
			while(itr.hasNext()){
				 itr.next().setIsActive(true);
			}
			persistantFee.setIsActive(true);
			persistantFee.setModifiedBy(fee.getModifiedBy());
			persistantFee.setLastModifiedDate(fee.getLastModifiedDate());		 
			tx.commit();
			//session.close();
			log.debug("Trxn Impl : Leaving activateFeeAdditionalAssignment with success");
	    	return true;
		 } catch (Exception e){
			log.debug("Trxn Impl : Leaving activateFeeAdditionalAssignment with Exception");
			tx.rollback();
			//session.close();
			throw e;
		 }
	 }
	
	/**
	 * This method add the Fee and FeeAccountAssignment.
	 */
	public boolean updateFeeAdditionalAssignment(FeeAdditional fee) throws Exception{
		log.debug("Trxn Impl : Entering updateFeeAdditionalAssignment");
		Session session = null; 
		Transaction tx = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    tx = session.beginTransaction();
			tx.begin();
			session.update(fee);
			tx.commit();
			session.close();
			log.debug("Trxn Impl : Leaving updateFeeAdditionalAssignment with success");
	    	return true;
		 } catch (Exception e) {
			tx.rollback();
			//session.close();
			log.debug("Trxn Impl : Leaving updateFeeAdditionalAssignment with Exception");
			throw e;				 
		 }
	}
	
	/**
	 * This method load the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return Fee object.
	 */
	public FeeAdditional getFeeAdditionalAssignmentById(int feeId) throws Exception{
		log.debug("Trxn Impl : Entering getFeeAdditionalAssignmentById"); 
		Session session = null;
		FeeAdditional fee;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    Transaction tx = session.beginTransaction();
			tx.begin();
			fee = (FeeAdditional)session.get(FeeAdditional.class, feeId);
			tx.commit();
			//session.close();
			log.debug("Trxn Impl : Leaving getFeeAdditionalAssignmentById with success");
	    	return fee;
		 } catch (Exception e){
			log.debug("Trxn Impl : Leaving getFeeAdditionalAssignmentById with exception");
			//session.close();
			throw e;
		 }
	 
	 }
	
	 /**
	 * This method load the FeeAssignment from fee & fee_account_assignment tables.
	 *  @return Fee object.
	 */
	public FeeAdditional getFeeAdditionalByCompositeKeys(FeeAdditional feeOld) throws Exception{
		log.debug("Trxn Impl : Entering getFeeAdditionalByCompositeKeys");
		Session session = null;
		FeeAdditional fee;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
		    Transaction tx = session.beginTransaction();
			tx.begin();
			Query query = session.createQuery("from FeeAdditional where feeGroup.id = :feeGroup");
			query.setInteger("feeGroup", feeOld.getFeeGroup().getId());
			fee =(FeeAdditional)query.uniqueResult();
			tx.commit();
			//session.close();
			log.debug("Trxn Impl : Leaving getFeeAdditionalByCompositeKeys with success");
	    	return fee;
		 } catch (Exception e){
			log.debug("Trxn Impl : Leaving getFeeAdditionalByCompositeKeys with Exception");
			//session.close();
			throw e;
		 }
	 
	 }
	
	public Map<Integer,String> getAllFeesGroup() throws Exception {
		log.debug("Trxn Impl : Entering getAllFeesGroup");
		Map<Integer,String> feeOptionalGroupMap = new HashMap<Integer,String>();
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select distinct feeAdditional.feeGroup.id," +
					 " feeAdditional.feeGroup.name "
					 +" from FeeAdditional feeAdditional"  
					 +" inner join feeAdditional.feeGroup.feeHeadings headings" 
					 +" where headings.isActive = 1"
					 +" and feeAdditional.feeGroup.isActive = 1"
					 +" and feeAdditional.isActive = 1 "
					 +" order by feeAdditional.feeGroup.name");
			 
			List<Object[]> list = query.list();
			Iterator<Object[]> itr = list.iterator();
			while(itr.hasNext()) {
				 Object[] row = itr.next();
				 feeOptionalGroupMap.put((Integer)row[0], row[1].toString());
			}
			//session.close();
			//sessionFactory.close();
			log.debug("Trxn Impl : Leaving getAllFeesGroup with success");
			return feeOptionalGroupMap;
		 } catch (Exception e) {
			log.debug("Trxn Impl : Leaving getAllFeesGroup with Exception");
			throw e;
		 }
	 }
}
