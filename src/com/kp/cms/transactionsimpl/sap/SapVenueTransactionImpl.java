package com.kp.cms.transactionsimpl.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.bo.sap.ExamRegistrationDetails;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.sap.SapVenueForm;
import com.kp.cms.transactions.sap.ISapVenueTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class SapVenueTransactionImpl implements ISapVenueTransactions{
	
	public static Log log = LogFactory.getLog(SapVenueTransactionImpl.class);
	public static volatile SapVenueTransactionImpl sapVenueTransactionImpl;
	
	public static SapVenueTransactionImpl getInstance(){
		if(sapVenueTransactionImpl == null){
			sapVenueTransactionImpl = new SapVenueTransactionImpl();
			return sapVenueTransactionImpl;
		}
		return sapVenueTransactionImpl;
	}
	
	/**
	 * get location details
	 */
	public List<EmployeeWorkLocationBO> getLocationList() throws Exception{
		log.debug("inside getLocationList");
		Session session = null;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from EmployeeWorkLocationBO e where e.isActive = 1 order by e.name asc");
			List<EmployeeWorkLocationBO> list = query.list();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("leaving getLocationList");
			return list;
		 } catch (Exception e) {
			 log.error("Error in getLocationList...",e);
			 session.flush();
			 session.close();
			 throw  new ApplicationException(e);
		 }
	}
	
	public SapVenue checkForDuplicateonNameAndLoc(String workLocId, String venue)throws Exception
	{
		Session session = null;
		SapVenue sapVenue = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from SapVenue s where s.venueName='"+venue+"' and s.workLocationId="+workLocId);
			sapVenue = (SapVenue)query.uniqueResult();
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for sapVenue in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		return sapVenue;
	}
	
	public boolean addSapVenue(SapVenue sapVenue) throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(sapVenue);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		}
	}
	
	public List<SapVenue> getSapVenueDetails()throws Exception{
	
		Session session = null;
		List<SapVenue> sapVenueList;
		try {
		//	session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			sapVenueList = session.createQuery("from SapVenue s where s.isActive = 1 order by s.workLocationId.name, s.venueName").list();
		} catch (Exception e) {			
		
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
		//	session.close();
			}
		}
		return sapVenueList;		
	}
	
	public boolean deleteSapVenue(int id, String userId) throws Exception
	{
		log.info("Start of deleteSapVenue of SapVenue TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			SapVenue sapVenue=(SapVenue)session.get(SapVenue.class,id);
			sapVenue.setIsActive(false);
			sapVenue.setLastModifiedDate(new Date());
			sapVenue.setModifiedBy(userId);
			session.update(sapVenue);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("End of deleteSapVenue of SapVenue TransactionImpl");
		}
	}
	
	public SapVenue getDetailsonId(int id)throws Exception
	{
		log.info("Start of getDetailsonId of SapVenue TransactionImpl");
		Session session = null;
		SapVenue sapVenue = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			
			Query query=session.createQuery("from SapVenue e where e.id= :row");
			query.setInteger("row", id);
			sapVenue = (SapVenue)query.uniqueResult();
		} catch (Exception e) {			
		log.error("Exception occured while getting the row based on the Id in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of getDetailsonId of SapVenue TransactionImpl");
		return sapVenue;	
	}
	
	public boolean checkForDuplicateonNameUpdate(SapVenueForm sapVenueForm)throws Exception
	{
		log.info("Start of checkForDuplicateonNameUpdate of SapVenue TransactionImpl");
		Session session = null;
		SapVenue sapVenue = null;
		boolean flag=false;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from SapVenue s where s.venueName='"+sapVenueForm.getVenueName()+"' and s.workLocationId.id="+sapVenueForm.getWorkLocId()+" and s.isActive=1");
			sapVenue = (SapVenue)query.uniqueResult();
			if(sapVenue!=null){
				if(sapVenue.getId()!=0){
					if(sapVenue.getId()==sapVenueForm.getId())
					{
						flag=false;
					}
					else{
						flag=true;
					}
				}
			}
			
		} catch (Exception e) {
		log.error("Exception occured in checking duplicate records for sapVenue in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			//session.close();
		}
		}
		log.info("End of updateSapVenue of SapVenue TransactionImpl");
		return flag;	
	}
	
	public boolean updateSapVenue(SapVenue sapVenue)throws Exception
	{
		log.info("Start of updateSapVenueupdateSapVenue of SapVenue TransactionImpl");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.merge(sapVenue);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while updating sapVenue in IMPL :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();		
		}
		log.info("End of updateSapVenue of SapVenue TransactionImpl");
		}
	}
	
	public boolean reActivateSapVenue(String venueName, String userId)throws Exception
	{
		log.info("Start of reActivateSapVenue of SapVenue TransactionImpl");
		Session session = null;
		Transaction transaction = null;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from SapVenue s where s.venueName = :venueName");
				query.setString("venueName",venueName);
				SapVenue sapVenue = (SapVenue) query.uniqueResult();
				transaction = session.beginTransaction();
				sapVenue.setIsActive(true);
				sapVenue.setModifiedBy(userId);
				sapVenue.setLastModifiedDate(new Date());
				session.update(sapVenue);
				transaction.commit();
				return true;
				} catch (Exception e) {
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Exception occured in reActivateSapVenue in IMPL :"+e);
				throw new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
					log.info("End of reActivateSapVenue of SapVenue TransactionImpl");
			}			
	}

	@Override
	public ExamRegistrationDetails  getstudentDetails(String regNo) throws Exception {

		log.debug("impl: inside getFineCategory");
	Session session = null;
	try {
		// SessionFactory sessionFactory = InitSessionFactory.getInstance();
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from ExamRegistrationDetails e where (e.isCancelled=0 or e.isCancelled is null) and e.isActive=1 and e.studentId.registerNo='"+regNo+"'");
		ExamRegistrationDetails examRegistrationDetails =(ExamRegistrationDetails) query.uniqueResult();
		session.flush();
		return examRegistrationDetails;
	} catch (Exception e) {
		log.error("Error during getting Admitted Through...", e);
		if (session != null) {
			session.flush();
			// session.close();
		}
		throw new ApplicationException(e);
	}
	}

	@Override
	public boolean updateExamDetails(String regNo, boolean present)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction transaction = null;
		ExamRegistrationDetails examRegistrationDetails = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			Query query=session.createQuery("from ExamRegistrationDetails e where e.studentId.registerNo='"+regNo+"'");
			examRegistrationDetails = (ExamRegistrationDetails)query.uniqueResult();
			examRegistrationDetails.setIsPresent(present);
			session.update(examRegistrationDetails);
			transaction.commit();
			flag=true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	

}
