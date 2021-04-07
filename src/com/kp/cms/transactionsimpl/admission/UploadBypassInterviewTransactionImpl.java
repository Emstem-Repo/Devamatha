package com.kp.cms.transactionsimpl.admission;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.transactions.admission.IUploadBypassInterviewTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class UploadBypassInterviewTransactionImpl implements
		IUploadBypassInterviewTransaction {
	private static final Log log = LogFactory.getLog(UploadBypassInterviewTransactionImpl.class);
	
	private static volatile UploadBypassInterviewTransactionImpl bypassInterviewTransactionImpl = null;
	
	private UploadBypassInterviewTransactionImpl() {
	}

	public static UploadBypassInterviewTransactionImpl getInstance() {
		
		if (bypassInterviewTransactionImpl == null) {
			bypassInterviewTransactionImpl = new UploadBypassInterviewTransactionImpl();
		}
		return bypassInterviewTransactionImpl;
	}

	@Override
	public boolean addUploadedData(List<AdmApplnTO> interviewResult, String user) throws Exception {
		log.info("call of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();

			if (interviewResult != null && !interviewResult.isEmpty()) {
				Iterator<AdmApplnTO> itr = interviewResult.iterator();
				while (itr.hasNext()) {
					AdmApplnTO admApplnTO = (AdmApplnTO) itr.next();
					AdmAppln admAppln =(AdmAppln) session.get(AdmAppln.class, admApplnTO.getId());				
					admAppln.setIsSelected(admApplnTO.getIsSelected());
					admAppln.setIsBypassed(admApplnTO.getIsBypassed());
					admAppln.setLastModifiedDate(new Date());
					admAppln.setModifiedBy(user);
					session.update(admAppln);
				}
			}

			transaction.commit();
			isAdded = true;

		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			isAdded = false;
			log.error("Error while getting applicant details..."+e);
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of addUploadData method in  UploadInterviewResultTransactionImpl class.");
		return isAdded;
	}
}