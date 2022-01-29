package com.kp.cms.transactionsimpl.inventory;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.InvLocation;
import com.kp.cms.bo.admin.InvPOTermsAndConditions;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.inventory.POTermsConditionsForm;
import com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class POTermsConditionsTransactionimpl implements IPOTermsConditionsTransaction {

	/**
	 * Singleton object of POTermsConditionsTransactionimpl
	 */
	private static volatile POTermsConditionsTransactionimpl poTermsConditionsTransactionimpl = null;
	private static final Log log = LogFactory.getLog(POTermsConditionsTransactionimpl.class);
	private POTermsConditionsTransactionimpl() {
		
	}
	/**
	 * return singleton object of POTermsConditionsTransactionimpl.
	 * @return
	 */
	public static POTermsConditionsTransactionimpl getInstance() {
		if (poTermsConditionsTransactionimpl == null) {
			poTermsConditionsTransactionimpl = new POTermsConditionsTransactionimpl();
		}
		return poTermsConditionsTransactionimpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction#getTCBo()
	 */
	@Override
	public InvPOTermsAndConditions getTCBo() throws Exception {
		Session session = null;
		InvPOTermsAndConditions poTCBO = null;
		try {
			session = HibernateUtil.getSession();
			String query= "from InvPOTermsAndConditions p";
			
			poTCBO = (InvPOTermsAndConditions)session.createQuery(query).uniqueResult();
			return poTCBO;
		} catch (Exception e) {
			log.error("Error while retrieving  T&C.." +e);
			throw  e;
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction#addTermsAndConditions(com.kp.cms.bo.admin.POTermsAndConditions)
	 */
	@Override
	public boolean addTermsAndConditions(InvPOTermsAndConditions bo,POTermsConditionsForm poForm) throws Exception {
		boolean isAdded=false;
		Session session=null;
		Transaction txn=null;
		try {
			session = HibernateUtil.getSession();
			txn=session.beginTransaction();
			if(bo!=null){
				if(bo.getId()>0){
					session.merge(bo);
				}
				else session.saveOrUpdate(bo);
				if(bo.getId()>0)
				poForm.setId(String.valueOf(bo.getId()));
				isAdded=true;
			}
			txn.commit();
			
		} catch (Exception e) {
			if(txn!=null)
				txn.rollback();
			log.error("Error while retrieving saving PO terms & Conditions.." +e);
			throw  new ApplicationException(e);
		}
		finally{
			if (session != null) {
				session.flush();
			}
		}
		return isAdded;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.inventory.IPOTermsConditionsTransaction#deleteTermsConditions(int, boolean, java.lang.String)
	 */
	@Override
	public boolean deleteTermsConditions(int id, boolean activate, String userId) throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			InvPOTermsAndConditions invPoTermsAndConditions = (InvPOTermsAndConditions) session.get(InvPOTermsAndConditions.class, id);
			if (activate) {
				invPoTermsAndConditions.setIsActive(true);
			}else
			{
				invPoTermsAndConditions.setIsActive(false);
			}
			invPoTermsAndConditions.setModifiedBy(userId);
			invPoTermsAndConditions.setLastModifiedDate(new Date());
			session.update(invPoTermsAndConditions);
			tx.commit();
			session.flush();
			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error in deleteEducation..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error in deleteEducation.." , e);
			throw new ApplicationException(e);
		}
		return result;
	}
}
