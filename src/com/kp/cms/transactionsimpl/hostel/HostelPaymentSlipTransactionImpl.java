package com.kp.cms.transactionsimpl.hostel;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlDamage;
import com.kp.cms.bo.admin.HlFeePayment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelPaymentSlipForm;
import com.kp.cms.transactions.hostel.IHostelPaymentSlipTransaction;
import com.kp.cms.utilities.HibernateUtil;

	
	public class HostelPaymentSlipTransactionImpl implements IHostelPaymentSlipTransaction{
		
	private static final Log log = LogFactory.getLog(HostelDamageTransactionImpl.class);
		
		public static volatile HostelPaymentSlipTransactionImpl hostelPaymentSlipTransactionImpl = null;
		
		public static HostelPaymentSlipTransactionImpl getInstance() {
			if (hostelPaymentSlipTransactionImpl == null) {
				hostelPaymentSlipTransactionImpl = new HostelPaymentSlipTransactionImpl();
				return hostelPaymentSlipTransactionImpl;
			}
			return hostelPaymentSlipTransactionImpl;
		}
		
		//get the details based on query
		public HlApplicationForm getHlapplicationByQuery(String query)
				throws Exception {
			Session session = null;
			HlApplicationForm hlApplicationForm = null;
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query hlApplFormQuery=session.createQuery(query);
				hlApplicationForm =(HlApplicationForm) hlApplFormQuery.uniqueResult();
				return hlApplicationForm;
			} catch (Exception e) {
				log.error("Error while HlapplicationByQuery." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		}
	
		
		public List<HlDamage> getHostelDamageByHlApplicationFormId(int hlApplicationFormId,int hostelId)
			throws Exception {
		Session session = null;
		List<HlDamage> damageList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from HlDamage h where h.hlHostel.id=:hostelId and h.hlApplicationForm.id=:hlAppId and h.isActive=1");
			query.setInteger("hostelId", hostelId);
			query.setInteger("hlAppId", hlApplicationFormId);
			damageList =query.list();
			return damageList;
		} catch (Exception e) {
			log.error("Error while HlapplicationByQuery." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	} 	
		
		public HlApplicationForm getHlApplicationFormByBillNo(Integer billNo)throws ApplicationException {
			
			log.info("entering into getHlApplicationFormByBillN of HostelPaymentSlipTransactionImpl class.");
			Session session = null;
			HlApplicationForm hlApplicationForm = null;
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query hlApplicationQuery = session
						.createQuery(" from HlApplicationForm hlApplicationForm "
				+ "where hlApplicationForm.billNo = :billNo");
				hlApplicationQuery.setInteger("billNo",billNo);			
				hlApplicationForm = (HlApplicationForm) hlApplicationQuery.uniqueResult();			
			 } catch (Exception e) {
				log.info("error in getHlApplicationFormByBillNo of HostelPaymentSlipTransactionImpl class.",e);
					throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
			   log
					.info("exit of getHlApplicationFormByBillNo of HostelPaymentSlipTransactionImpl class.");
			   return hlApplicationForm;
			}	
		
		public boolean saveHlApplicationForm(HlApplicationForm hlApplicationForm) throws ApplicationException
		{
			log.info("start of saveHlApplicationForm in HostelPaymentSlipTransactionImpl class.");
			Session session = null;
			Transaction transaction = null;
			boolean isAdded = false;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
				session.update(hlApplicationForm);
				transaction.commit();
				isAdded = true;
			} catch (Exception e) {
				isAdded = false;
				log.error("Unable to save HlApplicationForm" , e);
				throw new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
			log.info("end of saveHlApplicationForm in HostelPaymentSlipTransactionImpl class.");
			return isAdded;		
		}
		
         public Integer getMaxBillNoFromHlApplicationForm()throws ApplicationException {
			
			log.info("entering into getMaxBillNoFromHlApplicationForm of HostelPaymentSlipTransactionImpl class.");
			Session session = null;
			Integer maxBillNo = null;
			try {
				//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query billNoQuery = session
						.createQuery(" select max(hlApplicationForm.billNo) from HlApplicationForm hlApplicationForm");		
				maxBillNo = (Integer)billNoQuery.uniqueResult();			
			 } catch (Exception e) {
				log.info("error in getMaxBillNoFromHlApplicationForm of HostelPaymentSlipTransactionImpl class.",e);
					throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
			   log
					.info("exit of getMaxBillNoFromHlApplicationForm of HostelPaymentSlipTransactionImpl class.");
			   return maxBillNo;
			}	
		
         public boolean saveHlFeePayment(HlFeePayment hlFeePayment) throws ApplicationException{
        	 
     			log.info("start of saveHlFeePayment() in HostelPaymentSlipTransactionImpl class.");
     			Session session = null;
     			Transaction transaction = null;
     			boolean isAdded = false;
     			try {
     				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
     				//session = sessionFactory.openSession();
     				session = HibernateUtil.getSession();
     				transaction = session.beginTransaction();
     				transaction.begin();
     				session.save(hlFeePayment);
     				transaction.commit();
     				isAdded = true;
     			} catch (Exception e) {
     				isAdded = false;
     				log.error("Unable to save saveHlFeePayment()" , e);
     				throw new ApplicationException(e);
     			} finally {
     				if (session != null) {
     					session.flush();
     					session.close();
     				}
     			}
     			log.info("end of saveHlFeePayment() in HostelPaymentSlipTransactionImpl class.");
     			return isAdded;		
         }
         
        public List<HlApplicationForm> getHlApplicationForm(String query)
			throws Exception {
		Session session = null;
		List<HlApplicationForm> hlApplicationForm = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query hlApplFormQuery=session.createQuery(query);
			hlApplicationForm = hlApplFormQuery.list();
			return hlApplicationForm;
		} catch (Exception e) {
			log.error("Error while HlapplicationByQuery." +e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

		@Override
		public List<HlDamage> getHostelDamageByQuery(String query)
				throws Exception {
			Session session = null;
			List<HlDamage> hlDamages = null;
			try {
				session = HibernateUtil.getSession();
				Query hlApplFormQuery=session.createQuery(query);
				hlDamages = hlApplFormQuery.list();
				return hlDamages;
			} catch (Exception e) {
				log.error("Error while HlapplicationByQuery." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		}

		@Override
		public boolean isValidBillNo(String billNo) throws Exception {
			Session session = null;
			List<HlDamage> hlDamages = null;
			try {
				String query="from HlDamage d where d.isActive=1 and d.isPaid=0 and d.billNo="+billNo;
				session = HibernateUtil.getSession();
				Query hlApplFormQuery=session.createQuery(query);
				hlDamages =hlApplFormQuery.list();
				if(hlDamages!=null && !hlDamages.isEmpty()){
					return true;
				}else{
					return false;
				}
			} catch (Exception e) {
				log.error("Error while HlapplicationByQuery." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		}

		@Override
		public boolean markFinePaid(HostelPaymentSlipForm hostelPaymentSlipForm)
				throws Exception {
			Session session = null;
			List<HlDamage> hlDamages = null;
			try {
				String query="from HlDamage d where d.isActive=1 and d.isPaid=0 and d.billNo="+hostelPaymentSlipForm.getBillNo();
				session = HibernateUtil.getSession();
				Query hlApplFormQuery=session.createQuery(query);
				hlDamages =hlApplFormQuery.list();
				if(hlDamages!=null && !hlDamages.isEmpty()){
					Iterator<HlDamage> itr=hlDamages.iterator();
					int count=0;
					while (itr.hasNext()) {
						HlDamage bo = (HlDamage) itr.next();
						bo.setIsPaid(true);
						session.update(bo);
						if(++count % 20 == 0){
							session.flush();
							session.clear();
						}
					}
				}
				return true;
			} catch (Exception e) {
				log.error("Error while HlapplicationByQuery." +e);
				throw  new ApplicationException(e);
			} finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
			}
		}
}
