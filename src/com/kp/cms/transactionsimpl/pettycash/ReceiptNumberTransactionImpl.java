package com.kp.cms.transactionsimpl.pettycash;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.bo.admin.PcReceiptNumber;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.pettycash.LastReceiptNumberForm;
import com.kp.cms.transactions.pettycash.IReceiptNumberTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class ReceiptNumberTransactionImpl  implements IReceiptNumberTransaction{
private static final Log log = LogFactory.getLog(ReceiptNumberTransactionImpl.class);
	
	private static volatile ReceiptNumberTransactionImpl receiptNumberTransactionImpl;
	
	public static ReceiptNumberTransactionImpl getInstance()
	{
		if(receiptNumberTransactionImpl==null)
		{
			receiptNumberTransactionImpl = new ReceiptNumberTransactionImpl();
		return receiptNumberTransactionImpl;
		}
		return receiptNumberTransactionImpl;
	}
	@Override
	public List<PcReceiptNumber> getReceiptnumber() throws Exception {
		log.debug("inside getReceiptnumber in impl");
		Session session = null;
		List<PcReceiptNumber> list = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			//Query query = session.createQuery("from ReligionSection r where r.isActive = 1 and r.religion.isActive = 1 order by r.name");
			//Query query = session.createQuery("from PcReceiptNumber pr where pr.isActive=1 and pr.pcFinancialYear.isCurrent=1");
			Query query = session.createQuery("from PcReceiptNumber pr where pr.isActive=1"); 
			list = query.list();
		//	List<PcReceiptNumber> list = 
			if (session != null){
				session.flush();
				//session.close();
			}
			//sessionFactory.close();
			log.debug("leaving getReceiptnumber in impl");
			
			return list;
		} catch (Exception e) {
			log.error("Error in getReceiptnumber.. impl", e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
	}
	@Override
	public boolean addReceiptNumber(PcReceiptNumber pcReceiptNumber, String mode)
			throws Exception {
		log.debug("inside addReceiptNumber in Impl");
		Session session = null;
		Transaction tx = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			if (mode.equalsIgnoreCase("Add")) { //add mode
				session.save(pcReceiptNumber);
			} else if (mode.equalsIgnoreCase("Edit")) {  //edit mode
				session.update(pcReceiptNumber);
			}
			tx.commit();
			
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during saving addSubReligion data...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			throw new ApplicationException(e);
		}
		finally
		{
			if (
					session != null){
				session.flush();
				session.close();	
			}
		}
	}
	
	
	
	public PcReceiptNumber isPcReceiptnumberDuplicated(PcReceiptNumber dupPcReceiptnumber,String mode) throws Exception {
		log.debug("inside isPcReceiptnumberDuplicated");
		Session session = null;
		PcReceiptNumber pcReceiptNumber;
		StringBuffer sqlString =null;
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
 		 session = HibernateUtil.getSession();
		try {
			
			String startingNumber =dupPcReceiptnumber.getStartingNumber();
			int recId = dupPcReceiptnumber.getId();
			//if(dupPcReceiptnumber.getPcFinancialYear()!=null){
				int id = dupPcReceiptnumber.getPcFinancialYear().getId();
			//}
			if("Add".equalsIgnoreCase(mode))
			{
				//int id = dupPcReceiptnumber.getPcFinancialYear().getId();
			sqlString = new StringBuffer("from PcReceiptNumber p where p.startingNumber ="+startingNumber+" and p.pcFinancialYear.id ="+id+" and p.isActive =1");
			}
			else
			{
				if(dupPcReceiptnumber.getId()!=0){
					
				sqlString = new StringBuffer("from PcReceiptNumber p where p.id="+recId+" and p.startingNumber="+startingNumber+" and p.pcFinancialYear.id ="+id+" and p.isActive =1");
				}
			}
				
			Query query = session.createQuery(sqlString.toString());
			//query.setString("endingNumber", dupPcReceiptnumber.getEndingNumber());
			//query.setInteger("finYearId", dupPcReceiptnumber.getPcFinancialYear().getId());
			
			pcReceiptNumber = (PcReceiptNumber) query.uniqueResult();
			log.debug("leaving isPcReceiptnumberDuplicated in impl");
			return pcReceiptNumber;
		} catch (Exception e) {
			log.error("Error during saving.." + e);
			throw new ApplicationException(e);
		}
		finally
		{
			if(session!=null)
			{
				session.flush();
				//session.close();
				//sessionFactory.close();
			}
		}
	}
	@Override
	public PcFinancialYear getFinancialYear() throws Exception {
		log.debug("inside getFinancialYear");
		Session session = null;
		PcFinancialYear pcFinancialYear;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		session = HibernateUtil.getSession();
			String sqlString ="from PcFinancialYear pf where pf.isCurrent = true and pf.isActive = true";
			
			Query query = session.createQuery(sqlString);
			
			
			pcFinancialYear = (PcFinancialYear) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return pcFinancialYear;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
	@Override
	public List<PcFinancialYear> getFinancialYearList() throws Exception {
		log.debug("inside getFinancialYear list");
		Session session = null;
		PcFinancialYear pcFinancialYear;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			String sqlString ="from PcFinancialYear";
			//py where py.isCurrent = true and py.isActive = true";
			
			Query query = session.createQuery(sqlString);
			
			
			List<PcFinancialYear> fcFinancialYearList = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYearList in impl");
			return fcFinancialYearList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
	public boolean deleteReceiptNumber( int receiptNoId,Boolean activate,LastReceiptNumberForm lastReceiptNumberForm) throws Exception
	{
		log.debug("inside deleteReceiptNumber");
		Session session = null;
		Transaction tx = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			PcReceiptNumber pcreceiptNumber = (PcReceiptNumber) session.get(PcReceiptNumber.class, receiptNoId);
			if (activate) {
				pcreceiptNumber.setIsActive(true);
			} else {
				pcreceiptNumber.setIsActive(false);
			}
			pcreceiptNumber.setModifiedBy(lastReceiptNumberForm.getUserId());
			pcreceiptNumber.setLastModifiedDate(new Date());
			session.update(pcreceiptNumber);
			tx.commit();
			session.flush();
			//session.close();
			log.debug("leaving deleteReceiptNumber");
			return true;
		} catch (ConstraintViolationException e) {
			tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			tx.rollback();
			log.error("Error during deleting Sub Religion...", e);
			throw new ApplicationException(e);
		}	
		
	}
	@Override
	public int maxReceiptNumber() throws Exception {
		Session session = null;
		int maxReceiptNumber = 0;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			String sqlString ="select max(p.endingNumber)" +
					"from PcReceiptNumber p where p.isActive=true";
			
			Query query = session.createQuery(sqlString);
			String temp = (String) query.uniqueResult();
			if(temp!=null && !StringUtils.isEmpty(temp) && StringUtils.isNumeric(temp))
				maxReceiptNumber = Integer.parseInt(temp);
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return maxReceiptNumber;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
	public List<Object> financialYearList() throws Exception {
		Session session = null;
		List<Object> finaYearList;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			String sqlString ="select pr.pcFinancialYear.financialYear from PcReceiptNumber pr where pr.isActive=1";
			
			Query query = session.createQuery(sqlString);
			finaYearList =  query.list();
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return finaYearList;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
	
	public PcReceiptNumber getPreviouYearReceiptNumberDetails(int prevfinanicalYear) throws Exception
	{
		PcReceiptNumber prevPcreceiptNumber=null;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
    		session = HibernateUtil.getSession();
			String sqlString ="from  PcReceiptNumber p where p.pcFinancialYear.financialYear="+prevfinanicalYear+" and p.isActive=1";
			
			Query query = session.createQuery(sqlString);
			prevPcreceiptNumber =  (PcReceiptNumber)query.uniqueResult();
			
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return prevPcreceiptNumber;
		} catch (Exception e) {
			log.error("Error during getting previous years receipt number details..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		 
	}
	}
	@Override
	public PcFinancialYear getFinancialYear(int finYearId) throws Exception {
		log.debug("inside getFinancialYear");
		Session session = null;
		PcFinancialYear pcFinancialYear;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
    		 session = HibernateUtil.getSession();
			String sqlString ="from PcFinancialYear pf where pf.id ="+finYearId+" and pf.isActive = true";
			
			Query query = session.createQuery(sqlString);
			
			
			pcFinancialYear = (PcFinancialYear) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return pcFinancialYear;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
	public PcFinancialYear getFinancialYear(String finYear) throws Exception {
		log.debug("inside getFinancialYear");
		Session session = null;
		PcFinancialYear pcFinancialYear;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
    		 session = HibernateUtil.getSession();
			String sqlString ="from PcFinancialYear pf where pf.financialYear ="+finYear+" and pf.isActive = true";
			
			Query query = session.createQuery(sqlString);
			
			
			pcFinancialYear = (PcFinancialYear) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getFinancialYear in impl");
			return pcFinancialYear;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." + e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
	}
	}
}
