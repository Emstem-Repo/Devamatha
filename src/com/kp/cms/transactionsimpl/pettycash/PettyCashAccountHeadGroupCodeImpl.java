package com.kp.cms.transactionsimpl.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;


import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.transactions.pettycash.IPettyCashAccountHeadGroupCode;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;



public class PettyCashAccountHeadGroupCodeImpl implements IPettyCashAccountHeadGroupCode {
	
	private static final Log log = LogFactory
	.getLog(PettyCashAccountHeadGroupCodeImpl.class);
	private static volatile PettyCashAccountHeadGroupCodeImpl pettyCashAccHeadGroupCode = null;
	
	public static PettyCashAccountHeadGroupCodeImpl getInstance() {
		   if(pettyCashAccHeadGroupCode == null ){
			   pettyCashAccHeadGroupCode = new PettyCashAccountHeadGroupCodeImpl();
			   return pettyCashAccHeadGroupCode;
		   }
		   return pettyCashAccHeadGroupCode;
	}
	
	@SuppressWarnings("unchecked")
	public List<PcAccHeadGroup> getAllpettyCashAccHeadGroupCode() throws Exception{
		log.info("entering into getAllpettyCashAccHeadGroupCode in PettyCashAccountHeadGroupCodeImpl class..");
		Session session = null;
		try {
			/* SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 session =sessionFactory.openSession();*/
			 session = HibernateUtil.getSession();
			 String queryString="from PcAccHeadGroup p where p.isActive=1";
			 Query query = session.createQuery(queryString);
			 List<PcAccHeadGroup> list = query.list();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAllpettyCashAccHeadGroupCode in pettyCashAccountHeadGrooupCodeImpl class..");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAllpettyCashAccHeadGroupCode data..." , e);
			 throw e;
		 }
	}
	
	public boolean manageAccountHeadGroupCode(PcAccHeadGroup pcAccHeadGroup,String mode) throws Exception
	{
		log.info("Start of managePettyCashAccountHeadGroupCode");
		Session session = null;
		Transaction transaction = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add")){
				session.save(pcAccHeadGroup);	
			}else{
				session.update(pcAccHeadGroup);
			}
			transaction.commit();
			session.flush();
			session.close();
			log.info("End of managePettyCashAccountHeadGroupCode");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error during saving Account HeadGroupCode data..." , e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error during saving Account HeadGroupCode data..." , e);
			throw new ApplicationException(e);
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public PcAccHeadGroup getPettyCashAccGroupCode(Integer id) throws Exception{
		log.info("entering into getPettyCashAccGroupCode in PettyCashAccountHeadGroupCodeImpl class");
		PcAccHeadGroup temppcAccHeadGroup=null;
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			//temppcAccHeadGroup=(PcAccHeadGroup) session.get(PcAccHeadGroup.class,id);
			String queryString="from PcAccHeadGroup  where id=:Name and isActive=1";
			 Query query = session.createQuery(queryString);
			 query.setInteger("Name", id);
			 List<PcAccHeadGroup> list = query.list();
			 temppcAccHeadGroup=list.get(0);
			session.flush();
			session.close();
			log.info("leaving from getPettyCashAccGroupCode in PettyCashAccountHeadGroupCodeImpl class");
			return temppcAccHeadGroup!=null?temppcAccHeadGroup:null;
		} catch (Exception e) {
			 log.error("Error during getting HeadGroupCode..." , e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	public PcAccHeadGroup existanceCheck(String  groupCode) throws Exception{
		log.info("enter into existanceCheck in PettyCashAccountHeadGroupCodeImpl class");
		Session session = null;
		PcAccHeadGroup pcAccHeadGroup=null;
		try
		{
		/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = sessionFactory.openSession();*/
		session = HibernateUtil.getSession();
		String studenttypeHibernateQuery = "from PcAccHeadGroup where code=:Name and isActive=1" ;
		Query query = session.createQuery(studenttypeHibernateQuery);
		query.setString("Name", groupCode);
		pcAccHeadGroup =(PcAccHeadGroup) query.uniqueResult();
		log.info("leaving from existanceCheck in PettyCashAccountHeadGroupCodeImpl class");
		session.flush();
		return (pcAccHeadGroup!=null)?pcAccHeadGroup:null;
		}catch (Exception e) {
			
			log.error("Error during existance check...",e);
			if (session != null) {
				session.flush();
				session.close();
			}
			throw  new ApplicationException(e);
		}
	}
	
	

}
