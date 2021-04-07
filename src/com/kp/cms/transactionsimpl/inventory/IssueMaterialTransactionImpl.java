package com.kp.cms.transactionsimpl.inventory;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.InvIssue;
import com.kp.cms.bo.admin.InvItemStock;
import com.kp.cms.bo.admin.InvRequest;
import com.kp.cms.bo.admin.InvTx;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.inventory.IIssueMaterialTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class IssueMaterialTransactionImpl implements IIssueMaterialTransaction{
	
	private static final Log log = LogFactory.getLog(IssueMaterialTransactionImpl.class);

	/**
	 * Gets InvRequest By requisition No.
	 */
	public InvRequest getRequestByRequisitionNo(int requisitionNo)
			throws Exception {
		String status = CMSConstants.INVENTORY_STATUS_APPROVE;
		log.info("Entering into IssueMaterialTransactionImpl of getRequestByRequisitionNo");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		InvRequest request = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from InvRequest i where i.isActive = 1" +
		 								   " and i.requisitionNo = :requisitionNo and i.status = :status");				                          
		 query.setInteger("requisitionNo", requisitionNo);	
		 query.setString("status", status);
		 request = (InvRequest) query.uniqueResult();	
		 return request;
		}
		catch (Exception e) {	
			log.error("Exception occured while getRequestByRequisitionNo in IssueMaterialTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			//session.close();
		}
		log.info("Leaving into IssueMaterialTransactionImpl of getRequestByRequisitionNo");
		}
	}
	
	/**
	 * This method is used to submit the issue mateiral (Save pupose)
	 * And
	 * Update the inventory present stock
	 */
	public boolean submitIssueMaterial(InvIssue issue,
			List<InvItemStock> updateStockList, List<InvTx> invTXList) throws Exception {
		log.info("Entering into submitIssueMaterial of IssueMaterialTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		InvItemStock invItemStock;
		InvTx invTx;
		try {
			//Used to update the item stock
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Iterator<InvItemStock> itr = updateStockList.iterator();
			int count = 0;
			while (itr.hasNext()) {
				invItemStock = itr.next();
				session.merge(invItemStock);
				if (++count % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();		
			session.clear();
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			//Used to save the Issued materials for the request id of the inventory
			int referenceId =0;
			if(issue.getId()!=0){
				referenceId = issue.getId();
				session.saveOrUpdate(issue);
			}
			else{
				referenceId = (Integer)session.save(issue);
			}			
			transaction.commit();
			
			
			
			//Used to update the invTx
			transaction = session.beginTransaction();
			Iterator<InvTx> it = invTXList.iterator();
			int temp = 0;
			while (it.hasNext()) {
				invTx = it.next();
				invTx.setReferenceId(referenceId);
				session.save(invTx);
				if (++temp % 20 == 0) {
					session.flush();
					session.clear();
				}
			}
			transaction.commit();	
			log.info("Leaving into submitIssueMaterial of IssueMaterialTransactionImpl");
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while submitIssueMaterial in IssueMaterialTransactionImpl:"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}

	/**
	 * Gets issued item details by InvRequest ID
	 */
	
	public InvIssue getIssueDetailsByInvRequestID(int invRequestID)
			throws Exception {
		log.info("Entering into IssueMaterialTransactionImpl of getIssueDetailsByInvRequestID");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		InvIssue issue = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from InvIssue i where i.isActive = 1" +
		 								   " and i.invRequest.id = :invRequestID");				                          
		 query.setInteger("invRequestID", invRequestID);	
		 issue = (InvIssue) query.uniqueResult();	
		 log.info("Leaving into IssueMaterialTransactionImpl of getIssueDetailsByInvRequestID");
		 return issue;
		}
		catch (Exception e) {	
			log.error("Exception occured while getIssueDetailsByInvRequestID in IssueMaterialTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			//session.close();
		}
		}
	}

	/**
	 * Used to get the department Name based on the user namae
	 */
	
	public String getDepartmentByUserName(String userName) throws Exception {
		log.info("Entering into IssueMaterialTransactionImpl of getDepartmentByUserName");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		Users users = null;
		String departmentName = "";
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from Users u where u.isActive = 1" +
		 								   " and u.userName = :userName");				                          
		 query.setString("userName", userName);	
		 users = (Users) query.uniqueResult();	
		 if(users!=null && users.getEmployee()!=null && users.getEmployee().getDepartment()!=null
			&& users.getEmployee().getDepartment().getName()!=null){
			 departmentName = users.getEmployee().getDepartment().getName();
		 }		 
		 log.info("Leaving into IssueMaterialTransactionImpl of getDepartmentByUserName");
		 return departmentName;
		}
		catch (Exception e) {	
			log.error("Exception occured while getDepartmentByUserName in IssueMaterialTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			//session.close();
		}
		}
	}
}
