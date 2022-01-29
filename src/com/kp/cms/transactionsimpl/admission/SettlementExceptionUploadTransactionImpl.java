package com.kp.cms.transactionsimpl.admission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.admission.ISettlementExceptionUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SettlementExceptionUploadTransactionImpl implements ISettlementExceptionUploadTransaction{
	private static final Log log = LogFactory.getLog(SettlementExceptionUploadTransactionImpl.class);
	private static volatile SettlementExceptionUploadTransactionImpl settlementExceptionTnxImpl = null;
	
	private SettlementExceptionUploadTransactionImpl() {
	}
	
	/**
	 * @return
	 */
	public static SettlementExceptionUploadTransactionImpl getInstance() {
		if (settlementExceptionTnxImpl == null) {
			settlementExceptionTnxImpl = new SettlementExceptionUploadTransactionImpl();
		}
		return settlementExceptionTnxImpl;
	}
	
	public	Map<String,CandidatePGIDetails> getTnxPendingStatuscandiates(List<String> candidateRefNoList) throws Exception{

		Session session=null;
		Map<String,CandidatePGIDetails> candidatePGIDMap=new HashMap<String,CandidatePGIDetails>();
		try{
			session=HibernateUtil.getSession();
			Query query = session.createQuery("from CandidatePGIDetails cpd where  cpd.txnStatus !='Success'" +
											  " and cpd.candidateRefNo in (:candidateRefNoList)");
		  query.setParameterList("candidateRefNoList", candidateRefNoList);
		  List<CandidatePGIDetails> listl=query.list();
			Iterator<CandidatePGIDetails> itr = listl.iterator();
			while (itr.hasNext()) {
				CandidatePGIDetails canPGIDBo = (CandidatePGIDetails) itr.next();
				candidatePGIDMap.put(canPGIDBo.getCandidateRefNo(), canPGIDBo);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null)
				session.flush();
			session.close();
		}
		
		return candidatePGIDMap;
    }

	
	
	public boolean updateCandidatePGIDetails(List<CandidatePGIDetails> boList) throws Exception {
		log.debug("inside uploadBlockListForHallticketOrMarkscard");
		
		//ExamBlockUnblockHallTicketBO examBo = new ExamBlockUnblockHallTicketBO();
		
		Session session = null;
		Transaction transaction = null;
		boolean isUpdate=false;
			try {
			
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
			Iterator<CandidatePGIDetails> itr = boList.iterator();
			while (itr.hasNext()) {
				CandidatePGIDetails bo = (CandidatePGIDetails) itr.next();
				if(bo!=null){
					session.update(bo);
					isUpdate=true;
				}
			}
			transaction.commit();
			session.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isDocTypeDuplcated");
		return isUpdate;
	}
}
