package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.transactions.hostel.IHostelCheckoutTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelCheckoutTransactionImpl implements IHostelCheckoutTransactions  {
	
	private static final Log log = LogFactory.getLog(HostelCheckoutTransactionImpl.class);
	private static volatile HostelCheckoutTransactionImpl hostelCheckoutTransactionImpl = null;

	public static HostelCheckoutTransactionImpl getInstance() {
		if (hostelCheckoutTransactionImpl == null) {
			hostelCheckoutTransactionImpl = new HostelCheckoutTransactionImpl();
		}
		return hostelCheckoutTransactionImpl;
	}
	
	@Override
	public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelCheckoutTransactionImpl");
		Session session = null;
		List<HlHostel> hlhostellist = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlHostel h where h.isActive = 1 order by h.name";
			hlhostellist = session.createQuery(sqlString).list();
			if(hlhostellist!= null && hlhostellist.size() > 0){
				return hlhostellist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getHostelNames of HostelCheckoutTransactionImpl");
		return hlhostellist;
	}
	
	public List<Object> getApplicantHostelDetailsList(String searchQuery) throws Exception {
		log.info("Entering getApplicantHostelDetailsList HostelCheckoutTransactionImpl");
		Session session = null;
		List<Object> applicationDetails;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applicationDetails = session.createQuery(searchQuery).list();
			} catch (Exception e) {
			log.error("error while getting the getApplicantHostelDetailsList  "+e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getApplicantHostelDetailsList HostelCheckoutTransactionImpl");
		return applicationDetails;
	}
	
	public List<Object> getDamageDetails(String damageQuery) throws Exception {
		log.info("Entering getDamageDetails HostelCheckoutTransactionImpl");
		Session session = null;
		List<Object> damageDetailsList;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			damageDetailsList = session.createQuery(damageQuery).list();
			} catch (Exception e) {
			log.error("error while getting the  getDamageDetailsList  "+e);
			throw new ApplicationException(e);
			} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getDamageDetailsList HostelCheckoutTransactionImpl");
		return damageDetailsList;
	}
	
	public boolean saveCheckinCheckoutFecilities(HlCheckinCheckoutFacility hlCheckinCheckoutFacility) throws Exception{
		log.info("Entering saveFecilities HostelCheckoutTransactionImpl");
		Session session=null;
		boolean data=false;
		try {
			//SessionFactory sessFactory=InitSessionFactory.getInstance();
			//session=sessFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction trans=session.beginTransaction();
			session.save(hlCheckinCheckoutFacility);
			//session.save(hlRoomTransaction.getHlApplicationForm());
			trans.commit();
			data=true;
		} catch (Exception e) {
			throw new ApplicationException(e);			
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return data;
	}
	public boolean saveCheckoutDetails(HlRoomTransaction hlRoomTransaction) throws Exception{
		log.info("Entering saveCheckoutDetails HostelCheckoutTransactionImpl");
		Session session=null;
		boolean data=false;
		try {
			//SessionFactory sessFactory=InitSessionFactory.getInstance();
			//session=sessFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction trans=session.beginTransaction();
			session.save(hlRoomTransaction);
			//session.save(hlRoomTransaction.getHlApplicationForm());
			trans.commit();
			data=true;
		} catch (Exception e) {
			throw new ApplicationException(e);			
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return data;
	}
	
	public List<HlApplicationForm> getAppFormDetails(int hlAppFormId) throws Exception{
		log.debug("Entering getAppFormDetails of HostelCheckoutTransactionImpl");
		Session session = null;
		List<HlApplicationForm> hlApplicationFormlist = null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlApplicationForm  h where h.isActive = 1 and h.id="+hlAppFormId;
			hlApplicationFormlist = session.createQuery(sqlString).list();
			if(hlApplicationFormlist!= null && hlApplicationFormlist.size() > 0){
				return hlApplicationFormlist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getAppFormDetails of HostelCheckoutTransactionImpl");
		return hlApplicationFormlist;
		
	}
	
	public boolean updateAppFormDetails(HlApplicationForm hlApplicationForm) throws Exception{
		log.debug("Entering updateAppFormDetails of HostelCheckoutTransactionImpl");
		Session session=null;
		boolean data=false;
		try {
			//SessionFactory sessFactory=InitSessionFactory.getInstance();
			//session=sessFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction trans=session.beginTransaction();
			session.update(hlApplicationForm);
			//session.save(hlRoomTransaction.getHlApplicationForm());
			trans.commit();
			data=true;
		} catch (Exception e) {
			throw new ApplicationException(e);			
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		log.debug("Entering updateAppFormDetails of HostelCheckioutTransactionImpl");
		return data;
		
	}

	public List<HlCheckinCheckoutFacility> getCheckinCheckoutFacilityList(int formId,int roomTxId) throws Exception{
		log.debug("Entering getHlFacility of HostelCheckoutTransactionImpl");
		Session session = null;
		List<HlCheckinCheckoutFacility> hlCheckinCheckoutlist=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlCheckinCheckoutFacility hlcf where hlcf.isActive=1 and hlcf.hlApplicationForm.id="+formId+" and hlcf.hlRoomTransaction="+roomTxId;
			hlCheckinCheckoutlist = session.createQuery(sqlString).list();
			if(hlCheckinCheckoutlist!= null && hlCheckinCheckoutlist.size() > 0){
				return hlCheckinCheckoutlist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
				throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getHostelNames of HostelCheckoutTransactionImpl");
		return hlCheckinCheckoutlist;
	}

	
	public List<HlRoomTypeFacility > getHlFecilityDetails(int roomTypeId) throws Exception {
		log.debug("Entering getHlFacility of HostelCheckoutTransactionImpl");
		Session session = null;
		List<HlRoomTypeFacility > hlFacilitylist=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlRoomTypeFacility h where h.isActive=1 and h.hlRoomType.id="+roomTypeId;
			hlFacilitylist = session.createQuery(sqlString).list();
			if(hlFacilitylist!= null && hlFacilitylist.size() > 0){
				return hlFacilitylist;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at getting all records of hostels :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting getHostelNames of HostelCheckoutTransactionImpl");
		return hlFacilitylist;
	}
}



