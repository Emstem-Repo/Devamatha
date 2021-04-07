package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.BankMis;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.BankMISTO;
import com.kp.cms.transactions.admin.IBankMISTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class BankMISTransactionImpl implements IBankMISTransaction{

	
		private static final Logger log = Logger.getLogger(BankMISTransactionImpl.class);
		public static volatile BankMISTransactionImpl bankMISTransactionImpl = null;

		/**
		 * This method is used to create single instance of CurrencyMasterTransactionImpl.
		 * @return instance of CurrencyMasterTransactionImpl.
		 */

		public static BankMISTransactionImpl getInstance() {
			if (bankMISTransactionImpl == null) {
				bankMISTransactionImpl = new BankMISTransactionImpl();
				return bankMISTransactionImpl;
			}
			return bankMISTransactionImpl;
		}

		@Override
		public boolean saveBankDetails(List<BankMISTO> bankList) throws Exception {
			Session session = null;
			Transaction transaction = null;
			
			boolean isAdded = false;
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				if(bankList != null && !bankList.isEmpty()){
					Iterator itr = bankList.iterator();
					while (itr.hasNext()) {
						BankMISTO dataTO = (BankMISTO) itr.next();
						BankMis bankMis = new BankMis();
						
							bankMis.setJournalNo(dataTO.getJournalNo());
							bankMis.setRefNo(dataTO.getRefNumber());
							bankMis.setTxnBranch(dataTO.getTxnBranch());
							bankMis.setTxnDate(dataTO.getTxnDate());
						
						session.save(bankMis);
						session.flush();
					}
					transaction.commit();
					isAdded = true;
				}
				
			}catch (Exception e) {
				isAdded = false;
				log.error("Error while getting applicant details...",e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			return isAdded;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List getRefNos() throws Exception {
			log.info("call of getRefNos in BankMISTransactionImpl class.");
			Session session = null;
			List<BankMis> refList;

			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				refList = session.createQuery(
						"select b.refNo from BankMis b").list();

			} catch (Exception e) {
				log.error("Unable to get getRefNos" ,e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
			log.info("end of getRefNos in BankMISTransactionImpl class.");
			return refList;
		}
}
