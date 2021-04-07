package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.inventory.IInvRequisitionTxn;
import com.kp.cms.utilities.HibernateUtil;

public class InvRequisitionTxnImpl implements IInvRequisitionTxn{
	private static volatile InvRequisitionTxnImpl self=null;
	private static Log log = LogFactory.getLog(InvRequisitionTxnImpl.class);

	/**
	 * @return
	 * This method will return the instance of this classes
	 */
	public static InvRequisitionTxnImpl getInstance(){
		if(self==null)
			self= new InvRequisitionTxnImpl();
		return self;
	}
	
	private InvRequisitionTxnImpl(){
		
	}
	/***
	 * this method will add Requisition 
	 */
	public boolean addRequisition(InvRequest invRequest) throws  Exception {
		log.debug("inside addRequisition in impl");
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
				session.save(invRequest);
			transaction.commit();
			if (session != null) {
				session.flush();
				session.close();
			}
			//sessionFactory.close();
			result = true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving addRequisition data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving addRequisition data..." , e);
			throw new ApplicationException(e);
		}
		log.debug("leaving addRequisition in impl");
		return result;
	}
	
	/**
	 * Get Max Requisition No
	 */
	public int getMaxRequisitionNo() throws Exception {
		log.info("Entering into InvRequisitionTxnImpl of getMaxRequisitionNo");
		Session session = null;
		List<InvRequest> appForm;
		int maxRequisitionNo = 0;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			appForm = session.createQuery("from InvRequest invReq").list();
			if(appForm.isEmpty()){
				maxRequisitionNo = 0;
			}else {
				maxRequisitionNo = (Integer) session.createQuery("select max(requisitionNo) from InvRequest invReq").uniqueResult();
			}
		} catch (Exception e) {
		log.error("Error occured in getMaxRequisitionNo of InvRequisitionTxnImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into InvRequisitionTxnImpl of getMaxReceiptNo");
		return maxRequisitionNo;
	}
	
	/*
	 * Get the list of requested items
	 */
	public List<InvRequest> getRequisitionItems() throws Exception {
		log.info("Entering into getRequisitionItems of InvRequisitionTxnImpl");
		Session session = null;
		List<InvRequest> appForm;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			appForm = session.createQuery("from InvRequest invReq where invReq.status = 'Request' or invReq.status = 'Hold'").list();
		} catch (Exception e) {
		log.error("Error occured in getRequisitionItems of InvRequisitionTxnImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into InvRequisitionTxnImpl of getRequisitionItems");
		return appForm;
	}

	/*
	 * Get the list of requested items
	 */
	public List<InvRequest> getInvRequestByLocation(int locationId) throws Exception {
		log.info("Entering into getInvRequestByLocation of InvRequisitionTxnImpl");
		Session session = null;
		List<InvRequest> appForm;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvRequest invReq where invReq.invLocation.id = :locationId");
			query.setInteger("locationId", locationId);
			appForm = query.list();
		} catch (Exception e) {
		log.error("Error occured in getInvRequestByLocation of InvRequisitionTxnImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into InvRequisitionTxnImpl of getInvRequestByLocation");
		return appForm;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IInvRequisitionTxn#updateRequisition(java.util.List)
	 */
	public boolean updateRequisition(List<InvRequest> invRequestList) throws ApplicationException{

		Session session = null;
		Transaction transaction = null;
		boolean flag = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			

			if (invRequestList != null) {
				int count = 0;
				Iterator<InvRequest> iterator = invRequestList.iterator();
				Iterator<InvRequest> iterator1 = invRequestList.iterator();
				while (iterator.hasNext()) {
					InvRequest invRequest = iterator.next();
					
					String invUpdate = "update InvRequest inv set inv.comments = :invComments where inv.id = :invReqId";
				Query query = session.createQuery(invUpdate);
				query.setInteger("invReqId", invRequest.getId());
				query.setString("invComments", invRequest.getComments());
//				query.setString("invStatus", invRequest.getStatus());
				int temp = 	query.executeUpdate();
				flag = true;
				}
				while (iterator1.hasNext()) {
					InvRequest invRequest = iterator1.next();
					
					String invUpdate = "update InvRequest inv set inv.status = :invStatus where inv.id = :invReqId";
				Query query = session.createQuery(invUpdate);
				query.setInteger("invReqId", invRequest.getId());
//				query.setString("invComments", invRequest.getComments());
				query.setString("invStatus", invRequest.getStatus());
				int temp = 	query.executeUpdate();
				flag = true;
				}
				transaction.commit();
			}
			
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Error while saving interview card data ..."+e);
			throw new ApplicationException(e);
		} finally{
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return flag;	
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IInvRequisitionTxn#getDetails(int)
	 */
	public InvRequest getDetails(int reqId) throws ApplicationException {
		log.info("Entering into getDetails");
		Session session = null;
		InvRequest invRequest;
		
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InvRequest invReq where invReq.id = :reqId");
							query.setInteger("reqId", reqId);
			invRequest = (InvRequest) query.uniqueResult();
		
		} catch (Exception e) {
		log.error("Error occured in getDetails of InvRequisitionTxnImpl");
		throw new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
			}
		}
		log.info("Leaving into getDetails of InvRequisitionTxnImpl");
		return invRequest;
	}

}
