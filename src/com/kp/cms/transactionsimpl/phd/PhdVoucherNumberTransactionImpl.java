package com.kp.cms.transactionsimpl.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdVoucherNumberForm;
import com.kp.cms.transactions.phd.IPhdVoucherNumberTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

/**
 * @author kolli.ramamohan
 * @version 1.0
 * @since
 */
public class PhdVoucherNumberTransactionImpl implements IPhdVoucherNumberTransaction {

	private static final Logger log = Logger
			.getLogger(PhdVoucherNumberTransactionImpl.class);

	/** 
	 * Used to get PCFinancialYear Details
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#getPCFinancialYearDetails()
	 * @param void
	 * @return List<PCFinancialYear>
	 * @throws Exception
	 */
	public List<PhdVoucherNumber> setDataToToList() throws Exception {
		log.info("Start of setDataToToList of PhdVoucherNumberTransactionImpl");
		Session session = null;
		List<PhdVoucherNumber> voucherList = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			voucherList = session.createQuery("from PhdVoucherNumber number where number.isActive = 1").list();
		} catch (Exception e) {
			log.error("Exception occured in getPCFinancialYearDetails in PCFinancialYearTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("End of getPCFinancialYearDetails of PCFinancialYearTransactionImpl");
		return voucherList;
	}
	/** 
	 * Used to add PCFinancialYear
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#addPCFinancialYear(com.kp.cms.bo.admin.PCFinancialYear)
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean addValuationNumber(PhdVoucherNumber phdVoucherNumber,ActionErrors errors,PhdVoucherNumberForm phdVoucherNumberForm)
			throws Exception {
		log.info("Start of PhdVoucherNumber of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		PhdVoucherNumber phdVoucher=null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from PhdVoucherNumber phdYear where phdYear.financialYear=:year");
			query.setString("year", phdVoucherNumber.getFinancialYear());
			phdVoucher = (PhdVoucherNumber) query.uniqueResult();
			if(phdVoucher==null){
			if (phdVoucherNumber != null) {
				session.save(phdVoucherNumber);
			}
			}else{
				if(phdVoucher.getIsActive()){
				errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
				return false;
				}else if(!phdVoucher.getIsActive()){
					phdVoucherNumber.setId(phdVoucher.getId());
					session.merge(phdVoucherNumber);
				}
			}
			transaction.commit();
			log.info("End of addValuationNumber of PhdVoucherNumberTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in addValuationNumber in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}	
	
	/** 
	 * Used to get PCFinancialYear with Id
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#getPCFinancialDetailsWithId(int)
	 * @param int
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PhdVoucherNumber getVoucherNumberById(PhdVoucherNumberForm phdVoucherNumberForm)
			throws Exception {
		log.info("Start of getVoucherNumberById of PhdVoucherNumberTransactionImpl");
		Session session = null;
		PhdVoucherNumber phdVoucherNumber = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PhdVoucherNumber voucherNo where voucherNo.id= :rowId");
			query.setInteger("rowId",phdVoucherNumberForm.getId());
			phdVoucherNumber = (PhdVoucherNumber) query.uniqueResult();
		} catch (Exception e) {
			log.error("Exception occured in getVoucherNumberById in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("End of getVoucherNumberById of PhdVoucherNumberTransactionImpl");
		return phdVoucherNumber;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.phd.IPhdVoucherNumberTransaction#getPhdFinancialYear(java.lang.String)
	 */
	public PhdVoucherNumber getPhdFinancialYear(String phdYear)
			throws Exception {
		log.info("Start of getPhdFinancialYear of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		PhdVoucherNumber phdVoucherNumber = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from PhdVoucherNumber phdYear where phdYear.financialYear=:year");
			query.setString("year", phdYear);
			phdVoucherNumber = (PhdVoucherNumber) query.uniqueResult();
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in getPhdFinancialYear in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("End of getPhdFinancialYear of PhdVoucherNumberTransactionImpl");
		return phdVoucherNumber;
	}
	/** 
	 * Used to update PCFinancialYear
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#updatePCFinancialYear(com.kp.cms.bo.admin.PCFinancialYear)
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return boolean
	 * @throws Exception
	 */
	public boolean updateVoucherNumber(PhdVoucherNumber phdVoucherNumber)
			throws Exception {
		log.info("Start of updateVoucherNumber of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			session.update(phdVoucherNumber);
			transaction.commit();
			log.info("End of updateVoucherNumber of PhdVoucherNumberTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in updateVoucherNumber in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	
	/** 
	 * Used to delete PCFinancialYear Details
	 * @see com.kp.cms.transactions.PC.IPCFinancialYearTransaction#deletePCFinancialYearDetails(int, java.lang.String)
	 * @param int, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean deleteVoucherNumber(int feeId, String userId)
			throws Exception {
		log.info("Start of deleteVoucherNumber of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
 		    transaction = session.beginTransaction();
 		   PhdVoucherNumber phdVoucherNumber = (PhdVoucherNumber) session.get(PhdVoucherNumber.class, feeId);
 		   phdVoucherNumber.setCurrentYear(false);
 		   phdVoucherNumber.setIsActive(false);
 		   phdVoucherNumber.setModifiedBy(userId);
 		   phdVoucherNumber.setLastModifiedDate(new Date());
			session.update(phdVoucherNumber);
			transaction.commit();
			log.info("End of deleteVoucherNumber of PhdVoucherNumberTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in deleteVoucherNumber in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

	/** 
	 * Used to reactivate PCFinancialYear
	 * @see com.kp.cms.transactions.fee.IPCFinancialYearTransaction#reActivatePCFinancialYear(java.lang.String, java.lang.String)
	 * @param java.lang.String, java.lang.String
	 * @return boolean
	 * @throws Exception
	 */
	public boolean reActivateVoucherNumber(String year, String userId,PhdVoucherNumberForm phdVoucherNumberForm)throws Exception {
		log.info("Start of reActivateVoucherNumber of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PhdVoucherNumber phdYear where phdYear.financialYear = :year");
			query.setString("year", year);
			PhdVoucherNumber phdYear = (PhdVoucherNumber) query.uniqueResult();
			transaction = session.beginTransaction();
			phdYear.setCurrentYear(phdVoucherNumberForm.getCurrentYear().equalsIgnoreCase("true") ? true:false);
			phdYear.setIsActive(true);
			phdYear.setModifiedBy(userId);
			phdYear.setLastModifiedDate(new Date());
			session.update(phdYear);
			transaction.commit();
			log.info("End of reActivateVoucherNumber of PhdVoucherNumberTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in reActivateVoucherNumber in PhdVoucherNumberTransactionImpl : "
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}
	@Override
	public boolean changeAllToNo(PhdVoucherNumberForm phdVoucherNumberForm) throws Exception {
		log.info("Start of changeAllToNo of PhdVoucherNumberTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		List<PhdVoucherNumber> phdYear=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PhdVoucherNumber phdYear where phdYear.isActive = 1");
			phdYear =query.list();
			transaction = session.beginTransaction();
			Iterator<PhdVoucherNumber> itr=phdYear.iterator();
			while (itr.hasNext()) {
				PhdVoucherNumber voucherNumber = (PhdVoucherNumber) itr.next();
				voucherNumber.setCurrentYear(false);
				session.update(voucherNumber);
			}
			transaction.commit();
			log.info("End of changeAllToNo of PhdVoucherNumberTransactionImpl");
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			log.error("Exception occured in changeAllToNo in PhdVoucherNumberTransactionImpl : "
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	@Override
	public boolean duplicatecheck(ActionErrors errors,PhdVoucherNumberForm phdVoucherNumberForm) throws Exception {
		log.info("Start of PhdVoucherNumber of PhdVoucherNumberTransactionImpl");
		Session session = null;
		PhdVoucherNumber phdVoucher=null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PhdVoucherNumber phdYear where phdYear.financialYear=:year");
			query.setString("year", phdVoucherNumberForm.getFinancialYear());
			phdVoucher = (PhdVoucherNumber) query.uniqueResult();
			if(phdVoucher==null){
			return false;
			}else{
				if(phdVoucher.getIsActive()){
				errors.add("error", new ActionError("knowledgepro.employee.leave.allotment.duplicate"));
				return true;
				}
			}
			log.info("End of addValuationNumber of PhdVoucherNumberTransactionImpl");
			return false;
		} catch (Exception e) {
			log.error("Exception occured in addValuationNumber in PhdVoucherNumberTransactionImpl : "+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
}
