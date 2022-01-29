package com.kp.cms.transactionsimpl.pettycash;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.pettycash.CashCollectionForm;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class CashCollectionTransactionImpl implements ICashCollectionTransaction
{
	private static final Log log = LogFactory.getLog(CashCollectionTransactionImpl.class);
	
	private static volatile CashCollectionTransactionImpl cashCollectionTransactionImpl;
	
	public static CashCollectionTransactionImpl getInstance()
	{
		if(cashCollectionTransactionImpl==null)
		{
			cashCollectionTransactionImpl = new CashCollectionTransactionImpl();
		return cashCollectionTransactionImpl;
		}
		return cashCollectionTransactionImpl;
	}
	@Override
	public int getLastReceiptNumberAndUpdate() throws Exception {
		log.info("getLastReceiptNumberAndUpdate");
//		String lastRecNumber;
		Session session = null;
		int maxReceiptNumber=0;
//		List<PcReceipts> receiptNumberList = new ArrayList<PcReceipts>();
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			String sqlString ="select max(p.number) from PcReceipts p where p.isActive=true and p.pcFinancialYear.isCurrent=1";
			
			String sqlString1 ="from PcReceipts p where p.isActive=true and p.pcFinancialYear.isCurrent=1";
			
			Query query = session.createQuery(sqlString);
			
			//Query query1 = session.createQuery(sqlString1);
			int temp =0;
			
			query = session.createQuery(sqlString1);
			List<PcReceipts> pcReceiptList = query.list();
			
			if(pcReceiptList!=null && !pcReceiptList.isEmpty()){
				query = session.createQuery(sqlString);
				temp = (Integer) query.uniqueResult();
			}
			if(temp > 0){
				maxReceiptNumber =temp;
				maxReceiptNumber++;
			}
			if(maxReceiptNumber==0){	
				sqlString = "from PcReceiptNumber pr where pr.isActive=1 and pr.pcFinancialYear.isActive=1";
				query = session.createQuery(sqlString);
				List<PcReceiptNumber> list = query.list();
				if(list!=null && !list.isEmpty()){
					sqlString ="select pr.startingNumber from PcReceiptNumber pr where pr.isActive=1 and pr.pcFinancialYear.isActive=1 and pr.pcFinancialYear.isCurrent=1";
					query = session.createQuery(sqlString);
					String number = (String) query.uniqueResult();
					if(number!=null && !number.isEmpty()){
						temp = Integer.parseInt(number);
					}
				}
				if(temp==0){
					maxReceiptNumber = 0;
				}
				else{
					maxReceiptNumber = temp;
				}
			}
			
		} catch (Exception e) {
			log.error("Error during getting receipt Number.." , e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw new ApplicationException(e);
		}
		/*finally {
			if (session != null) {
				//session.flush();
				//session.close();
			}
			}*/
		return maxReceiptNumber;
		
		
	}
	
	
	@Override
	public List<PcAccountHead> getAccountNameWithCodeToList(String type) throws Exception {
		log.debug("impl: inside getAccountNameWithCodeToList");
		Session session = null;
		List<PcAccountHead> accCodeWithNamelist =null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = null;
			if(type.equalsIgnoreCase("PettyCash"))
				query = session.createQuery("from PcAccountHead pah where pah.isActive=1 and pah.programId is null order by pah.accName");
			else if(type.equalsIgnoreCase("ApplicationIssue"))
				query = session.createQuery("from PcAccountHead pah where pah.isActive=1 and pah.programId is not null order by pah.accName");
			else
				query = session.createQuery("from PcAccountHead pah where pah.isActive=1 order by pah.accName");
				
			accCodeWithNamelist =query.list();
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
		return accCodeWithNamelist;
	

	}
	
	
	
	
	
	public List getAmount(String accId) throws Exception {
		log.debug("impl: inside getAmount");
		Session session = null;
		List amountList=null;
		//=new ArrayList<PcAccountHead>();
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("select ph.amount,ph.isFixedAmount from PcAccountHead ph where ph.id="+accId+" and ph.isActive = 1 ");	
			amountList =query.list();
		}
		catch(Exception e)
		{
		log.error("Error while getting the details from pcreceipts" + e);
		throw new ApplicationException(e);
	}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return amountList;
}
	
	@Override
	public List<PersonalData> getStudentName(String queryString)throws Exception {
		// TODO Auto-generated method stub
		log.debug("impl: inside getStudentName");
		Session session = null;
		List<PersonalData> perList;
		//=new ArrayList<PcAccountHead>();
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery(queryString);	
			perList = query.list();
		
		}
		catch(Exception e)
		{
		log.error("Error while getting the details from pcreceipts" + e);
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		
		}
		return perList;
	}
	
	
	
	public String getStudentId(String appRollRegNo)throws Exception {
		// TODO Auto-generated method stub
		log.debug("impl: inside getStudentId");
		Session session = null;
		try
		{
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
		}
		catch(Exception e)
		{
		log.error("Error while getting the details from pcreceipts" + e);
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		
		}
		return null;
	}
	@Override
	public List<PcReceipts> getFineDetails(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Object[]> getUserPrivilage(String userId) throws Exception{
		log.debug("impl: inside getUserPrivilage");
		Session session = null;
		List<Object[]> userList;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select u.roles.name,u.roles.id,u.userName from Users u where u.id="+userId+" and u.isActive=1");	
			userList = query.list();
		
		}
		catch(Exception e)
		{
		log.error("Error while getting the details");
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		
		}
		return userList;
	}
	@Override
	public PcFinancialYear getFinancialyearId(String financialYear) throws Exception {
		
		log.debug("impl: inside getFinancialyearId");
		Session session = null;
		//List<Object[]> userList;
		PcFinancialYear finYearBo=null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query =null;
			if(financialYear!=null)
			query = session.createQuery("from PcFinancialYear fy where fy.isActive=1 and fy.financialYear="+financialYear+" and fy.isCurrent=1");	
			else
				query = session.createQuery("from PcFinancialYear fy where fy.isActive=1 and fy.isCurrent=1");	
			finYearBo = (PcFinancialYear)query.uniqueResult();
		
		}
		catch(Exception e)
		{
		log.error("Error while getting the details");
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		
		}
		return finYearBo;
	}
	public Student getStudentBo(String queryString) throws Exception {
		
		log.debug("impl: inside getStudentBo");
		Session session = null;
		//List<Object[]> userList;
		Student studentBo=null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			if(queryString!=null){
			Query query = session.createQuery(queryString);	
			studentBo = (Student)query.uniqueResult();
			}
			return studentBo;
		}
		catch(Exception e)
		{
		log.error("Error while getting the details");
		throw new ApplicationException(e);
		}
		/*finally {
		if (session != null) {
			//session.flush();
			//session.close();
		}
		
		}*/
		
	}
	/*@Override
	public Integer saveCashCollection(List<PcReceipts> pcReceipts)throws Exception {
		boolean isAdded =false;
		Session session = null;
		Transaction transaction = null;
		Integer receiptNumber=0;
		try {
			
			log.info("call of saveCashCollection in CashCollectionTransactionImpl class.");
			
			if(pcReceipts!=null && !pcReceipts.isEmpty())
			{
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
//				session = HibernateUtil.getSession();
				transaction = session.beginTransaction();
				transaction.begin();
//				receiptNumber=getLastReceiptNumberAndUpdate();
				String sqlString ="select max(p.number) from PcReceipts p where p.isActive=true and p.pcFinancialYear.isCurrent=1";
				
//				String sqlString1 ="from PcReceipts p where p.isActive=true and p.pcFinancialYear.isCurrent=1";
				
//				Query query = session.createQuery(sqlString);
				
				//Query query1 = session.createQuery(sqlString1);
				int temp =0;
				
//				query = session.createQuery(sqlString1);
				
//				List<PcReceipts> pcReceiptList = query.list();
				
//				if(pcReceiptList!=null && !pcReceiptList.isEmpty()){
					Query query = session.createQuery(sqlString);
					query.setLockMode("PcReceipts", LockMode.UPGRADE);
					temp = (Integer) query.uniqueResult();
//				}
				if(temp > 0){
					receiptNumber =temp;
					receiptNumber++;
				}
				if(receiptNumber==0){	
//					sqlString = "from PcReceiptNumber pr where pr.isActive=1 and pr.pcFinancialYear.isActive=1";
//					query = session.createQuery(sqlString);
//					List<PcReceiptNumber> list = query.list();
//					if(list!=null && !list.isEmpty()){
						sqlString ="select pr.startingNumber from PcReceiptNumber pr where pr.isActive=1 and pr.pcFinancialYear.isActive=1 and pr.pcFinancialYear.isCurrent=1";
						query = session.createQuery(sqlString);
						query.setLockMode("PcReceipts", LockMode.UPGRADE);
						String number = (String) query.uniqueResult();
						if(number!=null && !number.isEmpty()){
							temp = Integer.parseInt(number);
						}
//					}
					if(temp==0){
						receiptNumber = 0;
					}
					else{
						receiptNumber = temp;
					}
				}
				if(receiptNumber>0)
				{
//					synchronized (receiptNumber) {
//						receiptNumber=getLastReceiptNumber();
						Iterator<PcReceipts> pcIt = pcReceipts.iterator();
						while(pcIt.hasNext())
						{
							PcReceipts pcrecpts = 	pcIt.next();
							pcrecpts.setNumber(receiptNumber);
							session.save(pcrecpts);
						}
//					}
				}
				transaction.commit();
				isAdded = true;
			}
			else
			{
				isAdded = false;	
				
			}
		}
			catch (Exception e) {
			isAdded = false;
			log.error("Unable to addStudentReceipts" , e);
			throw new ApplicationException(e);
		} 
			finally 
				{
				if (session != null) 
				{
				session.flush();
				session.close();
				}
				}
		
		log.info("end of saveCashCollection in CashCollectionTransactionImpl class.");
		return receiptNumber;
		
	}*/
	
	public List<Object[]> getStartAndEndDate(Integer year) throws Exception
	{
		log.debug("impl: inside getStudentBo");
		List<Object[]> startEndDate;
		//Object[] startEndDate =null;
		Session session = null;
		//List<Object[]> userList;
	//	Student studentBo=null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("select p.startingNumber,p.endingNumber from PcReceiptNumber p where p.pcFinancialYear.financialYear="+year+" and p.isActive=1");	
			startEndDate =query.list();
		
		}
		catch(Exception e)
		{
		log.error("Error while getting the details");
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		
		}
		return startEndDate;
	}
	@Override
	public Users getUserFromUserId(String userId) throws Exception {
		log.debug("impl: inside getUserFromUserId");
		int id = Integer.valueOf(userId);
		Users user=null;
		Session session = null;
		try
		{
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from Users u where u.id="+id+" and u.isActive=1");	
			user =(Users)query.uniqueResult();
		
		}
		catch(Exception e)
		{
		log.error("Error while getting the details");
		throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		
		}
		return user;
	}
	@Override
	public void deletePcReceipt(int pcReceiptId, String accId, String userId) throws Exception {
		log.info("Start of deleteRecommendedBy of RecommendedBy TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query qury=session.createQuery("update PcReceipts pc set pc.isActive=0 where pc.pcAccountHead.id= :acHeadID and pc.number= :NUMBER");
			 qury.setInteger("NUMBER",pcReceiptId);
			 qury.setString("acHeadID", accId);
//			PcReceipts pcReceipts=(PcReceipts)session.get(PcReceipts.class,pcReceiptId);
//			pcReceipts.setIsActive(false);
//			pcReceipts.setLastModifiedDate(new Date());
//			pcReceipts.setModifiedBy(userId);
			int result= qury.executeUpdate();
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured in deleting RecommendedBy in IMPL :"+e);	
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		log.info("End of deleteRecommendedBy of RecommendedBy TransactionImpl");
		}
		
	}
	//printing the receipts
	
	public List<PcFinancialYear> getFinancialYearList() throws Exception {
		log.debug("inside getFinancialYear list");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
	
			String sqlString ="from PcFinancialYear year where year.isActive = true";
			Query query = session.createQuery(sqlString);
			List<PcFinancialYear> fcFinancialYearList = query.list();
			return fcFinancialYearList;
		} catch (Exception e) {
			log.error("Exception occured in getting all financial years in getFinancialYearList :"+e);
			throw  new ApplicationException(e);		
	}
		finally
		{
			if (session != null) {
				session.flush();
				//session.close();
			}	
		}
	}
	
	public int getFinancialYear() throws Exception {
		Session session = null;
		int financialYear = 0;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("select financialYear.id from PcFinancialYear financialYear" +
			 		" where financialYear.isCurrent = 1" +
			 		" and financialYear.isActive = 1");
			 	
			 financialYear = (Integer) query.uniqueResult();
			 return financialYear;
		 } catch (Exception e) {
			 throw e;
		 } finally {
				if (session != null) {
					session.flush();
					//session.close();
				}
		}
	}
	
	
	@Override
	public List<PcReceipts> getReceiptDetailsForEdit (int receiptNo,int financialYearId) throws Exception {
		
		Session session = null;
		PcReceipts pcReceipts = null;
		try {
			 SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();
//			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from PcReceipts pr where pr.number=:receiptNo" +
			 		" and pr.pcFinancialYear.isActive = 1 and pr.pcFinancialYear.id=:financialYearId  " +
			 		" and pr.isCancelled = 0 and pr.isActive = 1 order by pr.id");
			 		query.setInteger("receiptNo", receiptNo); 
			 		query.setInteger("financialYearId", financialYearId);
			
			 List<PcReceipts> pcReceiptsList = query.list();
			return pcReceiptsList;
		 } catch (Exception e) {
			 throw e;
		 } finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}
	@Override
	public int getCurrentFinancialYear() throws Exception {
		log.debug("impl: inside getAccountNameWithCodeToList");
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
	
	
	@Override
	public Integer saveCashCollection(List<PcReceipts> pcReceipts,CashCollectionForm cashCollectionForm,PcFinancialYear pcfinancialYear)throws Exception {
		Session session = null;
		Transaction transaction = null;
		Transaction transaction1=null;
		Integer receiptNumber=0;
		// 1. Try to fetch current id for that year from pc_receipt_id_generator
		// 2. If no record then...make an entry for that financial year and the generated id is used rcpt number (id, year)
		// 3. If entry present then ++ the entry and then use the new id
		try {
			log.info("call of saveCashCollection in CashCollectionTransactionImpl class.");
			if(pcReceipts!=null && !pcReceipts.isEmpty())
			{
				SessionFactory sessionFactory = InitSessionFactory.getInstance();
				session = sessionFactory.openSession();
				transaction1=session.beginTransaction();
				Query query1 = session.createQuery("select p from PcReceiptNumber p where p.pcFinancialYear.financialYear="+pcfinancialYear.getFinancialYear());
				query1.setLockMode("p", LockMode.UPGRADE);
				PcReceiptNumber pcg = (PcReceiptNumber) query1.uniqueResult();
				receiptNumber=pcg.getCurrentNo();
				receiptNumber=receiptNumber+1;
				pcg.setCurrentNo(receiptNumber);
				session.update(pcg);
				transaction1.commit();
				transaction = session.beginTransaction();
				if(receiptNumber>0){
					Iterator<PcReceipts> pcIt = pcReceipts.iterator();
					while(pcIt.hasNext())
					{
						PcReceipts pcrecpts = 	pcIt.next();
						pcrecpts.setNumber(receiptNumber);
						session.save(pcrecpts);
					}
				}
				transaction.commit();
			}
			else{
			}
		}
			catch (Exception e) {
			log.error("Unable to addStudentReceipts" , e);
			throw new ApplicationException(e);
		} 
		finally{
			if (session != null){
				session.flush();
				session.close();
			}
		}
		log.info("end of saveCashCollection in CashCollectionTransactionImpl class.");
		return receiptNumber;
	}

}
