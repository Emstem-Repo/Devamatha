package com.kp.cms.transactionsimpl.hostel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.HostelReservationForm;
import com.kp.cms.transactions.hostel.IHostelReservationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class HostelReservationTxnImpl implements IHostelReservationTransaction {

	private static final Log log = LogFactory
			.getLog(HostelReservationTxnImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.hostel.IHostelReservationTransaction#
	 * getApplicantHostelDetailsList(java.lang.String)
	 */
	@Override
	public HlApplicationForm getApplicantHostelDetailsList(String searchQuery)
			throws Exception {
		log.info("entered getApplicantHostelDetailsList..");
		Session session = null;
		HlApplicationForm applicationDetails = null;
		try {
			// SessionFactory sessionFactory =
			// HibernateUtil.getSessionFactory();
			// session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			applicationDetails = (HlApplicationForm) session.createQuery(
					searchQuery).uniqueResult();
		} catch (Exception e) {
			log.error("error while getting the getApplicantHostelDetailsList  "
					+ e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		log.info("exit getApplicantHostelDetailsList..");
		return applicationDetails;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.kp.cms.transactions.hostel.IHostelReservationTransaction#
	 * saveReservationDetails(com.kp.cms.bo.admin.HlRoomReservation)
	 */
	@Override
	public boolean saveReservationDetails(HlRoomTransaction hlRoomTransaction,HostelReservationForm hostelReservationForm)
			throws Exception {
		Session session = null;
		Transaction transaction = null;
		boolean result = false;
		try {
			 session = HibernateUtil.getSessionFactory().openSession();
//			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if (hlRoomTransaction != null) {
				session.save(hlRoomTransaction);
				HlApplicationForm appForm=(HlApplicationForm)session.get(HlApplicationForm.class,hlRoomTransaction.getHlApplicationForm().getId());
				appForm.setHlStatus(hlRoomTransaction.getHlStatus());
				appForm.setBillNo(getMaxBillNumber()+1);
	        	if(hostelReservationForm.getIsVeg().equals("true")){
	        		appForm.setIsVeg(true);
	        	}else{
	        		appForm.setIsVeg(false);
	        	}
	        	session.update(appForm);
			}
			transaction.commit();
//			session.close();
			result = true;
		} catch (ConstraintViolationException e) {
			log.error("Error during saving Interview Definition..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new BusinessException(e);
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return result;
	}

	public int findCurrentReservationCount(HostelReservationForm hostelResForm) throws Exception{
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try {			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
		
			String str1="select trans.currentReservationCount"+
						" from HlRoomTransaction trans" +
						" where trans.hlApplicationForm.hlHostelByHlApprovedHostelId.id =:hostelId"+
						" and trans.hlRoom.id=:roomId"+
						" and trans.isActive=1 and trans.txnDate=(select max(t1.txnDate)" +
						" from HlRoomTransaction t1 where " +
						" t1.hlApplicationForm.hlHostelByHlApprovedHostelId.id =:hostelId " +
						" and t1.hlRoom.id=:roomId  and t1.isActive=1)";

			Query query=session.createQuery(str1);
			query.setInteger("hostelId",Integer.parseInt(hostelResForm.getHostelId()));
			query.setInteger("roomId",Integer.parseInt(hostelResForm.getRoomId()));
			Object obj=query.uniqueResult();
			if(obj!=null){
				Integer cnt=(Integer)obj;
				count=cnt.intValue();
			}
			transaction.commit();			
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
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
		return count;
	}
	
	
	public int findCurrentOccupantsCount(HostelReservationForm hostelResForm) throws Exception{
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try {			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
		
			String str1="select trans.currentOccupantsCount"+
			" from HlRoomTransaction trans" +
			" where trans.hlApplicationForm.hlHostelByHlApprovedHostelId.id =:hostelId"+
			" and trans.hlRoom.id=:roomId"+
			" and trans.isActive=1 and trans.txnDate=(select max(t1.txnDate)" +
			" from HlRoomTransaction t1 where " +
			" t1.hlApplicationForm.hlHostelByHlApprovedHostelId.id =:hostelId " +
			" and t1.hlRoom.id=:roomId  and t1.isActive=1)";

			Query query=session.createQuery(str1);
			query.setInteger("hostelId",Integer.parseInt(hostelResForm.getHostelId()));
			query.setInteger("roomId",Integer.parseInt(hostelResForm.getRoomId()));
			Object obj=query.uniqueResult();
			if(obj!=null){
				Integer cnt=(Integer)obj;
				count=cnt.intValue();
			}
			transaction.commit();			
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
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
		return count;
	}
	
	public int getMaxNumberOfOccupantsPerRoom(HostelReservationForm hostelResForm)throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try {			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
		
			String str1="select hlRoom.hlRoomType.noOfOccupants"+
						" from HlRoom hlRoom" +
						" where hlRoom.hlHostel.id ="+Integer.parseInt(hostelResForm.getHostelId())+
						" and hlRoom.id="+Integer.parseInt(hostelResForm.getRoomId()) +
						" and hlRoom.isActive=1";

			Query query=session.createQuery(str1);
			Object obj=query.uniqueResult();
			if(obj!=null){
				Integer cnt=(Integer)obj;
				count=cnt.intValue();
			}
			transaction.commit();			
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
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
		return count;
	}
	
	public int getMaxBillNumber()throws Exception
	{
		Session session = null;
		Transaction transaction = null;
		int count=0;
		try {			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
		
			String str1="select max(app.billNo) from HlApplicationForm app" ;
			
			Query query=session.createQuery(str1);
			Object obj=query.uniqueResult();
			if(obj!=null){
				Integer cnt=(Integer)obj;
				count=cnt.intValue();
			}
			transaction.commit();			
		} catch (Exception e) {
			log.error("Error during saving Interview Definition..." + e);
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
		return count;
	}
}