package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelCheckinForm;
import com.kp.cms.transactions.hostel.IHostelCheckinTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelCheckinTransactionImpl implements IHostelCheckinTransactions {
	private static final Log log = LogFactory.getLog(HostelCheckinTransactionImpl.class);
	private static volatile HostelCheckinTransactionImpl hostelCheckinTransactionImpl = null;

	public static HostelCheckinTransactionImpl getInstance() {
		if (hostelCheckinTransactionImpl == null) {
			hostelCheckinTransactionImpl = new HostelCheckinTransactionImpl();
		}
		return hostelCheckinTransactionImpl;
	}
	@Override
	public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelCheckinTransactionImpl");
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
				log.debug("Exiting getHostelNames of HostelCheckinTransactionImpl");
		return hlhostellist;
	}
	
	public Integer saveCheckinDetails(HlRoomTransaction hlRoomTransaction) throws Exception{
		log.info("Entering saveCheckinDetails HostelCheckinTransactionImpl");
		Session session=null;
		Integer transactionId=0;
		try {
			//SessionFactory sessFactory=InitSessionFactory.getInstance();
			//session=sessFactory.openSession();
			session = HibernateUtil.getSession();
			Transaction trans=session.beginTransaction();
			session.save(hlRoomTransaction);
			
			//session.save(hlRoomTransaction.getHlApplicationForm());
			trans.commit();
			transactionId=hlRoomTransaction.getId();
		} catch (Exception e) {
			throw new ApplicationException(e);			
		}finally{
			if(session!=null){
				session.flush();
				session.close();
			}
		}
		return transactionId;
	}
	
	
	public List<HlApplicationForm> getAppFormDetails(int hlAppFormId) throws Exception{
		log.debug("Entering getAppFormDetails of HostelCheckinTransactionImpl");
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
				log.debug("Exiting getAppFormDetails of HostelCheckinTransactionImpl");
		return hlApplicationFormlist;
		
	}
	
	public List<HlRoomTransaction> getCheckinDetailedListToView(int statusId,int roomId,int bedNo) throws Exception {

		log.debug("Entering getCheckinDetailedListToView in Hostelcheckin TransactionImpl");
		Session session = null;
		List<HlRoomTransaction> result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlRoomTransaction h where h.isActive=1  and h.hlStatus.isActive=1 and h.bedNo="+bedNo+" and h.hlRoom.id="+roomId+" and h.hlStatus.id ="+ 2 );
			List<HlRoomTransaction> list = query.list();
				result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences..." , e);
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.debug("Exiting getCheckinDetailedListToView in HostelFeeTransactionImpl");
		return result;
	}
	
	
	public List<HlRoomTransaction> getCheckinDetailedListForActive(int statusId,int roomId) throws Exception {
		log.debug("Entering getCheckinDetailedListForActive in Hostelcheckin TransactionImpl");
		Session session = null;
		List<HlRoomTransaction> result;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from HlRoomTransaction h where h.isActive=0 and h.hlRoom.id="+roomId+" and h.hlStatus.id ="+ 2);
			List<HlRoomTransaction> list = query.list();
				result = list;
		} catch (Exception e) {
			log.error("Error during getting Preferences..." , e);
			throw new ApplicationException(e);
		}
		finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.debug("Exiting getCheckinDetailedListForActive in HostelCheckinTransactionImpl");
		return result;
	}
	
	public boolean updateAppFormDetails(HlApplicationForm hlApplicationForm) throws Exception{
		log.debug("Entering updateAppFormDetails of HostelCheckinTransactionImpl");
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
		log.debug("Entering updateAppFormDetails of HostelCheckinTransactionImpl");
		return data;
		
	}
	
	

	public boolean saveFecilities(HlCheckinCheckoutFacility hlCheckinCheckoutFacility) throws Exception{
		log.info("Entering saveFecilities HostelCheckinTransactionImpl");
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
	
	
	public List<Object> getApplicantHostelDetailsList(
			String searchQuery) throws Exception {
		log.info("Entering getApplicantHostelDetailsList HostelCheckinTransactionImpl");
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
		log.info("Exiting getApplicantHostelDetailsList HostelCheckinTransactionImpl");
		return applicationDetails;
	}
	
	public boolean reActivateCheckinList(List<HlRoomTransaction> hlCheckinBoList)throws Exception
	{
		log.info("Inside of reActivateCheckinList of HostelCheckinTransactionImpl");
		Session session = null;
		Transaction transaction = null;
		boolean isSaved= false;
		HlRoomTransaction hlRoomTransaction;
			try {
				//SessionFactory sessionFactory = InitSessionFactory.getInstance();
				//session = sessionFactory.openSession();
				session = HibernateUtil.getSession();
				if(hlCheckinBoList!=null){
					Iterator<HlRoomTransaction> itr = hlCheckinBoList.iterator();
					int count = 0;
					transaction = session.beginTransaction();
					transaction.begin();
					while (itr.hasNext()) {
						hlRoomTransaction = itr.next();
						session.update(hlRoomTransaction);
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
						
					}
				}
				transaction.commit();
				//sessionFactory.close();
				log.info("End of reActivateCheckinList of HostelCheckin TransactionImpl");
				isSaved = true;
			} catch (Exception e) {
				if(transaction!=null){
					transaction.rollback();
				}
				
				log.error("Error occured reActivateCheckinList of HostelCheckinTransactionImpl");
				throw new ApplicationException(e);
			}
			finally
			{
				if (session != null) {
					session.flush();
					session.clear();
					session.close();
				}
				
			}
			return isSaved;
		}
	

	public List<HlRoomTypeFacility > getHlFecilityDetails(int roomTypeId) throws Exception {
		log.debug("Entering getHlFacility of HostelCheckinTransactionImpl");
		Session session = null;
		List<HlRoomTypeFacility > hlFacilitylist=null;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String sqlString = "from HlRoomTypeFacility h where h.isActive=1 and h.hlFacility.isActive=1 and h.hlRoomType.id="+roomTypeId;
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
				log.debug("Exiting getHostelNames of HostelCheckinTransactionImpl");
		return hlFacilitylist;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelCheckinTransactions#checkForDuplicate(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkForDuplicate(String fingerPrintId, String hostelId)
			throws Exception {
		log.debug("Entering checkForDuplicate of HostelCheckinTransactionImpl");
		Session session = null;
		List<HlApplicationForm> hlApplicationForms = null;
		try {
			session = HibernateUtil.getSession();
			String sqlString = "from HlApplicationForm h where h.isActive=1 and h.hlHostelByHlApprovedHostelId.id="+hostelId+" and h.fingerPrintId ='"+fingerPrintId+"'";
			hlApplicationForms = session.createQuery(sqlString).list();
			if(hlApplicationForms!= null && hlApplicationForms.size() > 0){
				return true;
			}
		}
			catch (Exception e) {
				log.error("Exception ocured at checkForDuplicate :",e);
					throw  new ApplicationException(e);
				} finally {
					if (session != null) {
						session.flush();
						//session.close();
					}
				}
				log.debug("Exiting checkForDuplicate of HostelCheckinTransactionImpl");
		return false;
	}
	
	public boolean isCheckedin(HostelCheckinForm hostelCheckinForm) throws Exception
	{
		log.debug("Entering getHlFacility of HostelCheckinTransactionImpl");
		Session session = null;
		List<HlApplicationForm> hlApplicationForms=null;
		try {
			session = HibernateUtil.getSession();
			String sqlString="";
			if(hostelCheckinForm.getIsStaff().equalsIgnoreCase("true"))
				sqlString = "from HlRoomTransaction h where h.isActive=1 and h.hlApplicationForm.hlStatus.id=2 and h.employee.id="+hostelCheckinForm.getAppNo();
			else
				sqlString = "from HlRoomTransaction h where h.isActive=1 and h.hlApplicationForm.hlStatus.id=2 and h.admAppln.id="+hostelCheckinForm.getAppNo();
			hlApplicationForms = session.createQuery(sqlString).list();
			if(hlApplicationForms!= null && hlApplicationForms.size() > 0){
				return true;
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
				log.debug("Exiting getHostelNames of HostelCheckinTransactionImpl");
		return false;
	}
}
