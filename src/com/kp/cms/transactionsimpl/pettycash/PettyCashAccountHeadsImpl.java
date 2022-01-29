package com.kp.cms.transactionsimpl.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.transactions.pettycash.IPettyCashAccountHeads;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;



public class PettyCashAccountHeadsImpl implements IPettyCashAccountHeads {
	
	private static final Log log = LogFactory
	.getLog(PettyCashAccountHeadsImpl.class);
	private static volatile PettyCashAccountHeadsImpl pettyCashAccHeads = null;
	
	public static PettyCashAccountHeadsImpl getInstance() {
		   if(pettyCashAccHeads == null ){
			   pettyCashAccHeads = new PettyCashAccountHeadsImpl();
			   return pettyCashAccHeads;
		   }
		   return pettyCashAccHeads;
	}
	
	@SuppressWarnings("unchecked")
	public List<PcAccountHead> getAllpettyCashAccHeads() throws Exception{
		log.info("entering into getAllpettyCashAccHeads method in PettyCashAccountHeadsImpl class..");
		Session session = null;
		try {
			/* SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
      		 session = HibernateUtil.getSession();
			
			 String queryString="from PcAccountHead p where p.isActive=1";
			 Query query = session.createQuery(queryString);
			 List<PcAccountHead> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAllpettyCashAccHeads in PettyCashAccountHeadsImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllpettyCashAccHeads data..." , e);
			 throw e;
		 }
	}
	
	@SuppressWarnings("unchecked")
	public List<PcAccHeadGroup> getAllpettyCashAccHeadGroup() throws Exception{
		log.info("entering into  getAllpettyCashAccHeadGroup in PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			
			 String queryString="from PcAccHeadGroup p where p.isActive=1";
			 Query query = session.createQuery(queryString);
			 List<PcAccHeadGroup> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getAllpettyCashAccHeadGroup in PettyCashAccountHeadsImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllpettyCashAccHeadGroup data..." , e);
			 throw e;
		 }
	}
	
	
	@SuppressWarnings("unchecked")
	public List<PcBankAccNumber> getAllpettyCashBankAccNumber() throws Exception{
		log.info("enter into  getAllpettyCashBankAccNumber method in  PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from PcBankAccNumber p where p.isActive=1";
			 Query query = session.createQuery(queryString);
			 List<PcBankAccNumber> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("end of getAllpettyCashBankAccNumber in PettyCashAccountHeadsImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllpettyCashBankAccNumber data..." , e);
			 throw e;
		 }
	}
	
	public boolean manageAccountHead(PcAccountHead pcAccountHead,String mode) throws Exception
	{
		log.info("entering into manageAccountHead in PettyCashAccountHeadsImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(pcAccountHead);	
			}else{
				session.update(pcAccountHead);
			}
			transaction.commit();
			session.flush();
			session.close();
			log.info("leaving from manageAccountHead in PettyCashAccountHeadsImpl");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving Account HeadGroupCode data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during manageAccountHead data..." , e);
			throw new ApplicationException(e);
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public PcAccountHead getPettyCashAccountHead(Integer id) throws Exception{
		log.info("entering into getPettyCashAccountHead in PettyCashAccountHeadsImpl class");
		PcAccountHead tempPcAccountHead=null;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			//tempPcAccountHead=(PcAccountHead) session.get(PcAccountHead.class,id);
			 String queryString="from PcAccountHead p where p.id=:Id and p.isActive=1";
			 Query query = session.createQuery(queryString);
			 //query.setString("Id",id.toString());
			 query.setInteger("Id", id);
			 List<PcAccountHead> list = query.list();
			 tempPcAccountHead =list.get(0);
			session.flush();
			//session.close();
			log.info("leaving from getPettyCashAccountHead in PettyCashAccountHeadsImpl class");
			return tempPcAccountHead!=null?tempPcAccountHead:null;
		} catch (Exception e) {
			 log.error("Error during getPettyCashAccountHead..." , e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	public PcAccountHead existanceCheck(String  accCode) throws Exception{
		log.info("call of existanceCheck in PettyCashAccountHeads class");
		Session session = null;
		PcAccountHead pcAccHead=null;
		try
		{
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
  		session = HibernateUtil.getSession();
			
		String studenttypeHibernateQuery = "from PcAccountHead where accCode=:Name and isActive=1" ;
		Query query = session.createQuery(studenttypeHibernateQuery);
		query.setString("Name",accCode);
		pcAccHead =(PcAccountHead) query.uniqueResult();
		log.info("end of existanceCheck in PettyCashAccountHeadsImpl class");
		session.flush();
		return (pcAccHead!=null)?pcAccHead:null;
		}catch (Exception e) {
			
			log.error("Error during getting pcAccHead loading...",e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw  new ApplicationException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public PcBankAccNumber getPettyCashBankAccNumberById(String id) throws Exception{
		log.info("entering into getPettyCashBankAccNumberById in PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from PcBankAccNumber p where p.isActive=1 and p.id=:Id";
			 Query query = session.createQuery(queryString);
			 query.setString("Id", id);
			 List<PcBankAccNumber> list = query.list();
			 PcBankAccNumber pcBankAccNumber=list.get(0);
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getPettyCashBankAccNumberById in PettyCashAccountHeadsImpl class");
			 return pcBankAccNumber;
		 } catch (Exception e) {
			 log.error("Error during getPettyCashBankAccNumberById data..." , e);
			 throw e;
		 }
	}

	@Override
	public List<AccountHeads> getAccountHeadsDetails(
			PettyCashAccountHeadsForm pettyCashAccHeadForm) throws Exception {
		log.info("entering into getPettyCashAccountHeads in PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from AccountHeads head where head.atDate >='"+CommonUtil.ConvertStringToSQLDate(pettyCashAccHeadForm.getStartDate())+"' and head.atDate <= '"+CommonUtil.ConvertStringToSQLDate(pettyCashAccHeadForm.getEndDate())+"' and head.academicYear ="+pettyCashAccHeadForm.getAcademicYear();
			 Query query = session.createQuery(queryString);
			 List<AccountHeads> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getPettyCashAccountHeads in PettyCashAccountHeadsImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getPettyCashAccountHeads data..." , e);
			 throw e;
		 }
	}

	@Override
	public List<PettyCashCollection> getCollectionDetails(
			PettyCashAccountHeadsForm pettyCashAccHeadForm) throws Exception {
		log.info("entering into getCollectionDetails in PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from PettyCashCollection pc where pc.atDate >='"+CommonUtil.ConvertStringToSQLDate(pettyCashAccHeadForm.getStartDate())+"' and pc.atDate <= '"+CommonUtil.ConvertStringToSQLDate(pettyCashAccHeadForm.getEndDate())+"' and pc.academicYear ="+pettyCashAccHeadForm.getAcademicYear();
			 Query query = session.createQuery(queryString);
			 List<PettyCashCollection> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getCollectionDetails in PettyCashAccountHeadsImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getCollectionDetails data..." , e);
			 throw e;
		 }
	}

	@Override
	public List<PettyCashCollectionDetails> getCollectionDetailsWithReceiptNoAndAppNo(
			int recieptNo, String AccAppNo) throws Exception {
		log.info("entering into getCollectionDetailsWithReceiptNoAndAppNo in PettyCashAccountHeadsImpl class");
		Session session = null;
		try {
			 /*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
     		 session = HibernateUtil.getSession();
			 String queryString="from PettyCashCollectionDetails pcd where pcd.receiptNo="+recieptNo+" and pcd.aplRegNo='"+AccAppNo+"'";
			 Query query = session.createQuery(queryString);
			 List<PettyCashCollectionDetails> details = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getCollectionDetailsWithReceiptNoAndAppNo in PettyCashAccountHeadsImpl class");
			 return details;
		 } catch (Exception e) {
			 log.error("Error during getCollectionDetailsWithReceiptNoAndAppNo data..." , e);
			 throw e;
		 }
	}
	
	

}
