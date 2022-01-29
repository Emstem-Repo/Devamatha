package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IViewReqStatusTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ViewReqStatusTransactionImpl implements IViewReqStatusTransaction {
	private static final Log log = LogFactory.getLog(RequisitionTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IViewReqStatusTransaction#getRequisitionStatusDetails(java.lang.String)
	 */
	public List<HlApplicationForm> getRequisitionStatusDetails(String dynamicQuery)throws Exception {
		
		log.info("Entering into ViewReqStatusTransactionImpl--- getRequisitionStatusDetails");
		Session session = null;
		List<HlApplicationForm> requisitionList;
		try {
			session = HibernateUtil.getSession();
			requisitionList = session.createQuery(dynamicQuery).list();
		} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionStatusDetails Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
		}
		}
		log.info("Leaving into ViewReqStatusTransactionImpl --- getRequisitionStatusDetails");
		return requisitionList;
   }
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IViewReqStatusTransaction#getRequisitionisEmployee(java.lang.String)
	 */
	public String getRequisitionisEmployee(String query) throws Exception {
		Session session = null;
		Object result=null;
		String isStaff="student";
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
		 result= session.createQuery(query).uniqueResult();
		 if(result !=null)
		   isStaff=(result).toString();
		
		} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionStatusDetails Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		  return isStaff;
	}
	public String getRequisitionisFeePaid(String query) throws Exception {
		Session session = null;
		Object result=null;
		String isFeePaid="paid";
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
		 result= session.createQuery(query).uniqueResult();
		 if(result !=null)
			 isFeePaid=(result).toString();
		
		} catch (Exception e) {		
			log.error("Exception occured in getting all getRequisitionStatusDetails Details in IMPL :",e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		  return isFeePaid;
	}
}
