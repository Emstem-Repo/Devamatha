package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.ServicesDownTracker;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.ServicesDownTrackerForm;
import com.kp.cms.transactions.admin.IServicesDownTrackerTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

/**
 * @author dIlIp
 *
 */
public class ServicesDownTrackerTransactionImpl implements IServicesDownTrackerTransaction{
	
	private static final Log log = LogFactory.getLog(ServicesDownTrackerTransactionImpl.class);
	public static volatile ServicesDownTrackerTransactionImpl servicesDownTrackerTransactionImpl = null;

	public static ServicesDownTrackerTransactionImpl getInstance() {
		if (servicesDownTrackerTransactionImpl == null) {
			servicesDownTrackerTransactionImpl = new ServicesDownTrackerTransactionImpl();
			return servicesDownTrackerTransactionImpl;
		}
	return servicesDownTrackerTransactionImpl;
	}

	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IServicesDownTrackerTransaction#getTrackerList(java.lang.String, java.lang.String)
	 */
	public List<ServicesDownTracker> getTrackerList(String servicesId, String mode) throws Exception {
		Session session = null;
		List<ServicesDownTracker> list;
		
		try {
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			Query query = null;
			session = HibernateUtil.getSession();
			if(!mode.equalsIgnoreCase("services") || servicesId.equalsIgnoreCase(""))
				query = session.createQuery("from ServicesDownTracker s where s.isActive=1 order by s.date desc, s.downFrom desc");
			else
				query = session.createQuery("from ServicesDownTracker s where s.isActive=1 and s.serviceId.id='"+servicesId+"' order by s.date desc, s.downFrom desc");
			
			list = query.list();

			session.flush();
			// session.close();
			// sessionFactory.close();
			//result = list;
		} catch (Exception e) {
			log.error("Error during getting getServices...", e);
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IServicesDownTrackerTransaction#duplicateCheck(com.kp.cms.forms.admin.ServicesDownTrackerForm)
	 */
	@Override
	public boolean duplicateCheck(ServicesDownTrackerForm trackerForm) throws Exception {
		Session session=null;
		boolean flag=false;
		try{
			session=HibernateUtil.getSession();
			String quer="from ServicesDownTracker tr where tr.serviceId.id="+trackerForm.getServicesId()+
			" and tr.date='"+CommonUtil.ConvertStringToSQLDate(trackerForm.getDate())+
			"' and tr.downFrom='"+trackerForm.getDownFrom()+"' and tr.isActive=1";
			Query query=session.createQuery(quer);
			ServicesDownTracker servicesDownTracker = (ServicesDownTracker) query.uniqueResult();
			if(servicesDownTracker!=null){
				if(trackerForm.getId()>0){
					flag=false;
				}else{
					flag=true;
				}
			}else{
				flag=false;
			}
				
		}catch(Exception e){
			log.info("exception occured in duplicate check..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return flag;
	}


	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IServicesDownTrackerTransaction#addOrUpdateServicesDownTracker(com.kp.cms.bo.admin.ServicesDownTracker, java.lang.String)
	 */
	public boolean addOrUpdateServicesDownTracker(ServicesDownTracker servicesDownTracker, String mode) throws Exception {

		Session session=null;
		Transaction transaction=null;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("Add"))
			    session.save(servicesDownTracker);
			else
				session.merge(servicesDownTracker);
			transaction.commit();
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return true;
	
	}
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.IServicesDownTrackerTransaction#getServicesDownTrackerById(int)
	 */
	public ServicesDownTracker getServicesDownTrackerById(int id) throws Exception {
		Session session=null;
		ServicesDownTracker servicesDownTracker=null;
		try{
			session=HibernateUtil.getSession();
			String quer="from ServicesDownTracker tr where tr.id="+id+" and tr.isActive=1";
			Query query=session.createQuery(quer);
			servicesDownTracker = (ServicesDownTracker) query.uniqueResult();
		}catch(Exception e){
			log.info("exception occured in duplicate check..");
			e.printStackTrace();
			if(session!=null){
				session.close();
			}
		}
		return servicesDownTracker;
	}
	
	

}
