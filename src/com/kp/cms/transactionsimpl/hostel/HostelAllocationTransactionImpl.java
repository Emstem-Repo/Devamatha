package com.kp.cms.transactionsimpl.hostel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.hostel.HlRoomFloorTO;
import com.kp.cms.transactions.hostel.IHostelAllocationTransactions;
import com.kp.cms.utilities.HibernateUtil;

public class HostelAllocationTransactionImpl implements IHostelAllocationTransactions {
	private static final Log log = LogFactory.getLog(HostelAllocationTransactionImpl.class);
	private static volatile HostelAllocationTransactionImpl hostelAllocationTransactionImpl = null;

	public static HostelAllocationTransactionImpl getInstance() {
		if (hostelAllocationTransactionImpl == null) {
			hostelAllocationTransactionImpl = new HostelAllocationTransactionImpl();
		}
		return hostelAllocationTransactionImpl;
	}

	@Override
	public List<HlHostel> getHostelNames() throws Exception {
		log.debug("Entering getHostelNames of HostelAllocationTransactionImpl");
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
				log.debug("Exiting getHostelNames of HostelAllocationTransactionImpl");
		return hlhostellist;
	}
	@Override
	public List<Object> getApplicantHostelDetailsList(
			String searchQuery) throws Exception {
		log.info("Entering getApplicantHostelDetailsList HostelAllocationTransactionImpl");
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
		log.info("Exiting getApplicantHostelDetailsList HostelAllocationTransactionImpl");
		return applicationDetails;
	}

	@Override
	public List<HlRoomFloorTO> getFloorsByHostel(int hostelId) throws Exception{
		 try {
			 log.info("Entering getFloorsByHostel HostelAllocationTransactionImpl");
			 Session session = null;  
			 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
			 //session =sessionFactory.openSession();
			 session = HibernateUtil.getSession();
			 Query query = session.createQuery("from HlHostel h where h.isActive = 1 and  h.id = '" + hostelId + "'");
			 List<HlHostel> hostelList = query.list();
			 List<HlRoomFloorTO> floorList = new ArrayList<HlRoomFloorTO>();
			 Iterator<HlHostel> itr = hostelList.iterator();
			 HlHostel hlHostel;
			 int floorNo = 0;
			   while(itr.hasNext()) {
				   hlHostel = (HlHostel)itr.next();
				   floorNo = hlHostel.getNoOfFloors(); 
				   
			   }
			   for (int i = 1; i <= floorNo; i++){
				   HlRoomFloorTO floorTo = new HlRoomFloorTO();
				   floorTo.setFloorNo(String.valueOf(i));
				   floorList.add(floorTo);
			   }
			 session.flush();
			 //session.close();
			 return floorList;
		 } catch (Exception e) {
			 log.debug("Exception"+e.getMessage());
		 }
		 log.info("Exiting getFloorsByHostel HostelAllocationTransactionImpl");
		 return new ArrayList<HlRoomFloorTO>();
	 }
	
	
	@Override
	public String saveAllocationDetails(HlRoomTransaction hlRoomTransaction)throws Exception {
		Session session = null;
		Transaction transaction = null;
		String result = "failed";
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (hlRoomTransaction != null) {
				session.save(hlRoomTransaction);
			}
			transaction.commit();
			result = "success";
		} catch (ConstraintViolationException e) {
			log.error("Error during saving Allocation details.." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Allocation details." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}
	
	
	@Override
	public int getNumberOfOccupants(int roomId) throws Exception
	{
		Session session = null;
	int noOfOccupants = 0;
	try {
		 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
		 //session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		 Query query = session.createQuery("select hlRoomType.noOfOccupants from HlRoomType hlRoomType" +
		 		" where hlRoomType.id ="+roomId+
		 		" and hlRoomType.isActive = 1");
		 if(query.uniqueResult()!= null)
		 noOfOccupants = (Integer) query.uniqueResult();
		
	 } catch (Exception e) {
		 throw e;
	 } 
	 finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
	}
	 return noOfOccupants;
		
	}
	@Override
	public List<Object> getCurrentOccupantsCount(int roomNoId) throws Exception
	{
		Session session = null;
		List<Object> noOfCurrentOccupants;
		
	try {
		 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
		 //session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		 Query query = session.createQuery("select transaction.currentReservationCount,transaction.currentOccupantsCount"+
				 							" from HlRoom hl_room,"+
				 							"HlRoomTransaction transaction"+
				 							" where transaction.hlRoom.id = hl_room.id"+
				 							" and transaction.hlRoom.id ="+roomNoId+
		 									" and transaction.isActive = 1"+
		 									" and transaction.hlRoom.isActive=1"+
		 									" and transaction.txnDate = (select max(txn.txnDate)"+
		 									" from HlRoomTransaction txn where txn.hlRoom.id="+roomNoId+" and txn.hlRoom.isActive=1)");
		
		 noOfCurrentOccupants = query.list();
		
	 } catch (Exception e) {
		 throw e;
	 } 
	 finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
	}
	 return noOfCurrentOccupants;
		
	}
	@Override
	public Date getReservationDate(Integer applicationformId) throws Exception
	{
		log.debug("impl: inside getReservationDate");
		Date reservationDate = null;
		
		Session session = null;
		try
		{
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select txn.txnDate from HlRoomTransaction txn where txn.hlStatus.statusType = "+"'Reserved'"+" and txn.hlApplicationForm.id ="+applicationformId+" and txn.isActive=1");	
		
			if(query.uniqueResult()!=null)
			reservationDate = (Date)query.uniqueResult();
		
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
		return reservationDate;
	}
	
	
	@Override
	public String updateApplicationFormWithTransactionSet(HlApplicationForm hlApplicationForm)throws Exception {
		Session session = null;
		Transaction transaction = null;
		String result = "failed";
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (hlApplicationForm != null) {
				session.saveOrUpdate(hlApplicationForm);
			}
			transaction.commit();
			result = "success";
		} catch (ConstraintViolationException e) {
			log.error("Error during saving Allocation details.." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Allocation details." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if ( session!= null){
				session.flush();
				session.close();
			}
		}
		return result;
	}
	
	@Override
	public HlApplicationForm getApplicationDetailsToUpdate(int applId) throws Exception {
		log.info("Entering getApplicationDetailsToUpdate HostelAllocationTransactionImpl");
		Session session = null;
		HlApplicationForm hlApplicationForm = new HlApplicationForm();
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			String query = "from HlApplicationForm h where h.id="+applId+" and h.isActive=1";
			if(session.createQuery(query).uniqueResult()!=null)
			hlApplicationForm =(HlApplicationForm)session.createQuery(query).uniqueResult();
			} catch (Exception e) {
			log.error("error while getting the getApplicantHostelDetailsList  "+e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("Exiting getApplicationDetailsToUpdate HostelAllocationTransactionImpl");
		return hlApplicationForm;
	}
	@Override
	public List<Object> getAllocatedBedList(int roomNoId) throws Exception
	{
		Session session = null;
		List<Object> allocatedBedList;
	try {
		 //SessionFactory sessionFactory = InitSessionFactory.getInstance();
		 //session =sessionFactory.openSession();
		session = HibernateUtil.getSession();
		 Query query = session.createQuery("select distinct(txn.bedNo)"+
				 						" from HlRoomTransaction txn"+
				 						 " where txn.hlRoom.id="+roomNoId+" " +
				 						 " and txn.isActive =1"+
				 						 " and txn.hlRoom.isActive=1"+
				 						 " and txn.hlApplicationForm.hlStatus.id IN(2,7)");
		 allocatedBedList = query.list();
		
	 } catch (Exception e) {
		 throw e;
	 } 
	 finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
	}
	 return allocatedBedList;
		
	}

}
