package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.GenerateSettlementOrRefundPgiForm;
import com.kp.cms.transactions.admission.IGenerateSettlementOrRefundPgiTransation;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class GenerateSettlementOrRefundPgiTransactionImpl implements IGenerateSettlementOrRefundPgiTransation {
	private static volatile GenerateSettlementOrRefundPgiTransactionImpl generateSettlementOrRefundPgiTransactionImpl = null;
	private static final Log log = LogFactory.getLog(GenerateSettlementOrRefundPgiTransactionImpl.class);
	
	public static GenerateSettlementOrRefundPgiTransactionImpl getInstance() {
		if (generateSettlementOrRefundPgiTransactionImpl == null) {
			generateSettlementOrRefundPgiTransactionImpl = new GenerateSettlementOrRefundPgiTransactionImpl();
		}
		return generateSettlementOrRefundPgiTransactionImpl;
	}
	public List<CandidatePGIDetails> getSettlementOrRefundPgiData(String fromDate, String toDate){

		List<CandidatePGIDetails> candidatePGIDetails=new ArrayList();
		Session session=null; 
		try{
			session=HibernateUtil.getSession();
		String query=" select cpd from CandidatePGIDetails cpd " +
				     " where cpd.txnStatus='Success'" +
				     " and cpd.txnDate between '"+CommonUtil.ConvertStringToSQLDate(fromDate)+"'"+
				     " and '"+CommonUtil.ConvertStringToSQLDate(toDate)+"'";
		Query querys=session.createQuery(query);
		candidatePGIDetails=querys.list();
		}catch(Exception exception){
			log.error("error in getClasses ");
		}
		return candidatePGIDetails;
	}
	@SuppressWarnings("unchecked")
	public void updateRefundFlag( List<Integer> idList,GenerateSettlementOrRefundPgiForm form,String string) throws ApplicationException
	{
		Session session=null;
		boolean flag=false;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			//session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(idList!=null && !idList.isEmpty()){
			Iterator<Integer> itr = idList.iterator();
			while(itr.hasNext()){
			Integer	id = (Integer)itr.next();
			String query="select cpd from CandidatePGIDetails cpd where cpd.id="+id ;
			Query querys=session.createQuery(query);
			CandidatePGIDetails candidatePGI=(CandidatePGIDetails)querys.uniqueResult();
			if(candidatePGI !=null){
				if(string.equalsIgnoreCase("refund")){
					candidatePGI.setRefundGenerated(true);
				}else{
					candidatePGI.setSettlementGenerated(true);
				}
				candidatePGI.setModifiedBy(form.getUserId());
				candidatePGI.setLastModifiedDate(new Date());
				session.update(candidatePGI);
					}
				}
			}
			transaction.commit();
			session.flush();
			log.debug("leaving saveCompletedCertificate");
		}catch(Exception e){
			transaction.rollback();
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		
		
	}
}
