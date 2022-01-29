package com.kp.cms.transactionsimpl.fee;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.transactions.fee.IFeePaidTransaction;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author microhard
 * This class is for Fee paid related operations.
 */
public class FeePaidTransactionImpl implements IFeePaidTransaction {
	private static FeePaidTransactionImpl feePaidTransactionImpl = null;
	private static Log log = LogFactory.getLog(FeePaidTransactionImpl.class);	
	
	/*
	 * returns the singleton object each time.
	 */
	public static FeePaidTransactionImpl getInstance() {
			    if(feePaidTransactionImpl == null ){
			    	feePaidTransactionImpl = new FeePaidTransactionImpl();
			       return feePaidTransactionImpl;
			    }
			    return feePaidTransactionImpl;
	}
	
	/**
	 *  This method makes paid column as true.
	 */
	public  void markAsPaid(Set<FeePaymentTO> feePaymentToSet, Date paidDate) throws Exception {
		log.debug("TXN Impl : Entering markAsPaid");
		Session session = null;
//		Date date = new Date();
		FeePaymentTO feePaymentTO;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			
			Iterator<FeePaymentTO> itr = feePaymentToSet.iterator();
			int count = 0 ;
			Transaction tx = session.beginTransaction();
			tx.begin();
			while(itr.hasNext()) {
				feePaymentTO = new FeePaymentTO();
				feePaymentTO = itr.next();
//				int id = itr.next();
				Query query = session.createQuery("update FeePayment set isFeePaid = 1,feePaidDate = :date, paymentVerified = 1" +
												 " where id = :id");
				query.setDate("date", paidDate);
//				query.setInteger("id", id);
				query.setInteger("id", feePaymentTO.getId());
				/*if(feePaymentTO.getConversionRate() == null || feePaymentTO.getConversionRate().trim().isEmpty()){
					query.setBigDecimal("convRate", new BigDecimal(0));
				}else{
					query.setBigDecimal("convRate", new BigDecimal(feePaymentTO.getConversionRate().trim()));
				}*/
				query.executeUpdate();
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}	
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving markAsPaid with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving markAsPaid with Exception");
			throw e;
		}
	}
	
	/**
	 *  This method makes paid column as true.
	 */
	public  void markAsCancel(List<FeePaymentTO> ids) throws Exception {
		log.debug("TXN Impl : Entering markAsPaid");
		Session session = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Iterator<FeePaymentTO> itr = ids.iterator();
			int count = 0 ;
			Transaction tx = session.beginTransaction();
			tx.begin();
			while(itr.hasNext()) {
				FeePaymentTO fpt=itr.next();
				Query query = session.createQuery("update FeePayment set isCancelChallan = 1,cancellationReason=:reason " +
												 " where id = :id");
				query.setInteger("id", fpt.getId());
				query.setString("reason", fpt.getCancelReason());
				query.executeUpdate();
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}	
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving markAsPaid with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving markAsPaid with Exception");
			throw e;
		}
	}
	public  void updatePaidDateInDetailTable(Set<FeePaymentTO> feePaymentToSet, Date paidDate) throws Exception {
		log.debug("TXN Impl : Entering markAsPaid");
		Session session = null;
//		Date date = new Date();
		FeePaymentTO feePaymentTO;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Iterator<FeePaymentTO> itr = feePaymentToSet.iterator();
			int count = 0 ;
			Transaction tx = session.beginTransaction();
			tx.begin();
			while(itr.hasNext()) {
				feePaymentTO = new FeePaymentTO();
				feePaymentTO = itr.next();
				Query query = session.createQuery("update FeePaymentDetail set paidDate = :date" +
												 " where feePayment.id = :id");
				query.setDate("date", paidDate);
				query.setInteger("id", feePaymentTO.getId());
				query.executeUpdate();
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}	
			tx.commit();
			session.close();
			//sessionFactory.close();
			log.debug("TXN Impl : Leaving updatePaidDateInDetailTable with success");	
		return;
		} catch (Exception e) {
			log.debug("TXN Impl : Leaving updatePaidDateInDetailTable with Exception");
			throw e;
		}
	}
	
	public List<FeePayment> checkPreviousBalance(Student student,
			FeePaymentForm feePaymentForm)throws Exception{
		Session session=null;
		try{
			session = HibernateUtil.getSession();
				String q = "select fp from FeePayment fp " +
						" join fp.feePaymentDetails fd" +
						" where fp.student.id=" +student.getId()+
						" and fp.isCancelChallan=0" +
						" and fd.feeFinancialYear.id=" +feePaymentForm.getPreFinId()+
						" and fp.paymentVerified=1" +
						" group by fp.id";
				Query query = session.createQuery(q);
				List<FeePayment> list = query.list();
				if(list!=null || !list.isEmpty()){
					return list;
				}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
		
	public void setFinancialYearId(FeePaymentForm feePaymentForm)throws Exception{
		Session session=null;
		try{
			session = HibernateUtil.getSession();
			String year=null;
			if(feePaymentForm.getPreYear()!=null && feePaymentForm.getCurYear()!=null){
				year = feePaymentForm.getPreYear()+","+feePaymentForm.getCurYear();
			}
			Query qr = session.createQuery("from FeeFinancialYear f where f.year in("+year+")");
			List<FeeFinancialYear> fList = qr.list();
			if(fList!=null){
				for(FeeFinancialYear f:fList){
					if(f.getYear().substring(0, 4).equalsIgnoreCase(feePaymentForm.getPreYear())){
						feePaymentForm.setPreFinId(Integer.toString(f.getId()));
					}
					if(f.getYear().substring(0, 4).equalsIgnoreCase(feePaymentForm.getCurYear())){
						feePaymentForm.setCurFinId(Integer.toString(f.getId()));
					}
				}
			}
			Query q = session.createQuery("from FeeFinancialYear f where f.isCurrent=1");
			FeeFinancialYear finYear = (FeeFinancialYear) q.uniqueResult();
			if(finYear!=null){
				feePaymentForm.setFinancialYearId(Integer.toString(finYear.getId()));
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
