package com.kp.cms.transactionsimpl.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.pettycash.IPettyCashCancelCashCollectionTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class PettyCashCancelCashCollectionTransactionImpl implements IPettyCashCancelCashCollectionTransaction {
	
	private static final Log log=LogFactory.getLog(PettyCashCancelCashCollectionTransactionImpl.class);
	private static volatile PettyCashCancelCashCollectionTransactionImpl pcCancelCollectionTransactionImpl=null;

	public static PettyCashCancelCashCollectionTransactionImpl getInstance()
	{
		if(pcCancelCollectionTransactionImpl==null) 
		{
			pcCancelCollectionTransactionImpl=new PettyCashCancelCashCollectionTransactionImpl();
			return pcCancelCollectionTransactionImpl;
		}
		return pcCancelCollectionTransactionImpl;
	}
	
	@SuppressWarnings("unchecked")
	public List<PcReceipts> getAllCashCollectionByReceiptNumber(String number,String finYearId) throws Exception
	{
		log.info("entering into getAllCashCollectionByReceiptNumber in PettyCashCancelCashCollectionTransactionImpl class..");
		Session session = null;
		try
		{
    		session = HibernateUtil.getSession();
  		
    		Query query = session.createQuery("from PcReceipts p where p.number="+"'"+number+"'"+" and p.isCancelled=0 and p.isActive=1 and p.pcFinancialYear.id="+finYearId);
    		
			List<PcReceipts> pcReceiptsList=query.list();
			session.flush();
			
			log.info("leaving from getAllCashCollectionByReceiptNumber in PettyCashCancelCashCollectionTransactionImpl class..");
			return pcReceiptsList;
		}
		catch(Exception e){
			log.error("Error during getAllpcCashCollection data...",e);
			throw e;
		}	
	}
	
	
	@SuppressWarnings("unchecked")
	public PcReceipts getCashCollectionByReceiptNumber(String number, String finYearId) throws Exception
	{
		log.info("call of get class");
		Session session = null;
		PcReceipts pcReceipts=null;
		List<PcReceipts> pcReceiptsList=null;
		try
		{
			
			session = HibernateUtil.getSession();
			/*String queryString="from PcReceipts pr where pr.number='"+number+"' and pr.isActive=1 and pr.isCancelled=0 and pr.pcFinancialYear.financialYear="+finYearId;*/
			String queryString="from PcReceipts pr where pr.number='"+number+"' and pr.isActive=1 and pr.isCancelled=0 and pr.pcFinancialYear.id="+finYearId;
			Query query=session.createQuery(queryString);
			
			 pcReceiptsList=query.list();
			if(pcReceiptsList!=null && !pcReceiptsList.isEmpty())
			{
				pcReceipts =(PcReceipts) pcReceiptsList.get(0);
			}
			
			log.info("end of getAllpcCashCollection in pettyCashCancelCollectionTransactionImpl class");
			return pcReceipts;
		}
		catch(Exception e){
			log.error("Error during getAllpcCashCollection data...",e);
			throw e;
		}	
	}
	
	
	public boolean manageCashCollection(PcReceipts pcReceipts) throws Exception
	{
		log.info("Start of manageCashCollection");
		Session session = null;
		Transaction transaction = null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
    		session = HibernateUtil.getSession();

			transaction = session.beginTransaction();
			transaction.begin();
		
			String hql = "update PcReceipts pr set pr.isCancelled =:IsCancelled,pr.modifiedBy=:ModifiedBy,pr.lastModifiedDate=:LastModifiedDate,pr.cancelComments=:CancelComments  where pr.number=:Number and pr.pcFinancialYear.id=:FinId";
	        Query query = session.createQuery(hql);
	        query.setBoolean("IsCancelled", pcReceipts.getIsCancelled());
	        query.setString("ModifiedBy", pcReceipts.getModifiedBy());
	        query.setDate("LastModifiedDate", pcReceipts.getLastModifiedDate());
	        query.setString("CancelComments", pcReceipts.getCancelComments());
	        query.setString("Number", String.valueOf(pcReceipts.getNumber()));
	       // query.setString("AcademicYear", pcReceipts.getPcFinancialYear().getFinancialYear());
	        query.setInteger("FinId", pcReceipts.getPcFinancialYear().getId());
	        @SuppressWarnings("unused")
			int rowCount = query.executeUpdate();
			transaction.commit();;
			session.flush();
			session.close();
			log.info("End of manageCashCollection");
			return true;
		}
		catch (Exception e) {
			transaction.rollback();
			log.error("Error during manageCashCollection data..." , e);
			throw new ApplicationException(e);
		}
		
	}

	@Override
	public int getCurrentFinancialYear() throws Exception {
		Session session = null;
		PcFinancialYear pcf=null;
		int id=0;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from PcFinancialYear p where p.isActive=1 and p.isCurrent=1");
			pcf =(PcFinancialYear) query.uniqueResult();
			if(pcf!=null){
				id=Integer.parseInt(pcf.getFinancialYear());
				id=pcf.getId();
			}
		}
		catch(Exception e)
		{
		log.error("Error while getting the account head name and code..." + e);
		throw new ApplicationException(e);
	}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return id;
	}
	
	
	
	
}
