package com.kp.cms.transactionsimpl.hostel;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.hostel.IUploadHostelStudentsTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class UploadHostelStudentsTransactionImpl implements
		IUploadHostelStudentsTransaction {
	private static final Log log = LogFactory.getLog(UploadHostelStudentsTransactionImpl.class);
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IUploadHostelStudentsTransaction#getAdmMap(java.lang.String)
	 */
	@Override
	public Map<String, Integer> getAdmMap(String searchQuery) throws Exception {
		Session session = null;
		Map<String,Integer> map = new HashMap<String, Integer>();
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session.createQuery(searchQuery);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put(obj[0].toString(),(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getAppDetails method in  UploadSecondLanguageTransactionImpl class.");
		return map;
	}
	@Override
	public Map<Integer, Integer> getMaxCountForRoomType(String searchQuery)
			throws Exception {
		Session session = null;
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();
		try {
			session = InitSessionFactory.getInstance().openSession();
			Query query = session.createQuery(searchQuery);
			Iterator iterator = query.iterate();
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				if(obj!=null){
					map.put((Integer)obj[0],(Integer)obj[1]);
				}
			}
		} catch (Exception e) {
			log.error("Error while getting applicant details...",e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of getAppDetails method in  UploadSecondLanguageTransactionImpl class.");
		return map;
	}
	@Override
	public boolean addUploadData(List<HlRoomTransaction> results)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		HlRoomTransaction hlRoomTransaction=null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<HlRoomTransaction> tcIterator = results.iterator();
			int count = 0;
			while(tcIterator.hasNext()){
				hlRoomTransaction = tcIterator.next();
				HlApplicationForm hlApplicationForm=hlRoomTransaction.getHlApplicationForm();
				session.save(hlApplicationForm);
				hlRoomTransaction.setHlApplicationForm(hlApplicationForm);
				session.saveOrUpdate(hlRoomTransaction);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			//session.close();
			log.debug("leaving addTermsConditionCheckList");
			return true;
		}  catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addTermsConditionCheckList impl...", e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
	}

}
