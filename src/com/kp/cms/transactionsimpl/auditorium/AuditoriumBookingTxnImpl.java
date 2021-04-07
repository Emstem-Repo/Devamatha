package com.kp.cms.transactionsimpl.auditorium;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.auditorium.AuditoriumBooking;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.auditorium.AuditoriumBookingForm;
import com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AuditoriumBookingTxnImpl implements IAuditoriumBookingTransaction{
	private static final Log log = LogFactory.getLog(AuditoriumBookingTxnImpl.class);
	public static volatile AuditoriumBookingTxnImpl auditoriumTxn = null;
	public static AuditoriumBookingTxnImpl getInstance() {
		if (auditoriumTxn == null) {
			auditoriumTxn = new AuditoriumBookingTxnImpl();
		}
		return auditoriumTxn;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getBlockDetails()
	 */
	@Override
	public List<BlockDetails> getBlockDetails() throws Exception {
		Session session = null;
		List<BlockDetails> blockList = null;
		try{
			session = HibernateUtil.getSession();
			String str="from BlockDetails block where block.isActive = 1";
			Query query = session.createQuery(str);
			blockList = query.list();
		}catch(Exception e){
			log.error("Exception occured while getting Block Details", e);
			throw new ApplicationException();
		}
		return blockList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getVenueDetails()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<VenueDetails> getVenueDetails() throws Exception {
		Session session = null;
		List<VenueDetails> venueList = null;
		try{
			session = HibernateUtil.getSession();
			String str="from VenueDetails venue where venue.isActive = 1";
			Query query = session.createQuery(str);
			venueList = query.list();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occured while getting Venue Details", e);
			throw new ApplicationException();
		}
		System.out.println("txn end");
		return venueList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getVenuesByBlockDetails(int)
	 */
	@Override
	public Map<Integer, String> getVenuesByBlockDetails(int blockId)
			throws Exception {
		Session session = null;
		Map<Integer, String> venueMap=new HashMap<Integer, String>(); 
		try{
			session=HibernateUtil.getSession();
			String str="select v.id,v.venueName from VenueDetails v where v.block.id="+blockId;
			Query query = session.createQuery(str);
			List list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator itr = list.iterator();
				while (itr.hasNext()) {
					Object[] object = (Object[]) itr.next();
					if(object[0]!=null && object[1]!=null)
					venueMap.put(Integer.parseInt(object[0].toString()), object[1].toString());
				}
			}
			return venueMap;
		}catch(Exception e){
			log.error("Exception occured while getting Time", e);
			throw new ApplicationException();
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getBookingRequirements()
	 */
	@Override
	public List<BookingRequirements> getBookingRequirements() throws Exception {
		Session session = null;
		try{
			session=HibernateUtil.getSession();
			String str=" from BookingRequirements br";
			Query query = session.createQuery(str);
			List<BookingRequirements> list = query.list();
			
			return list;
		}catch(Exception e){
			log.error("Exception occured while getting Time", e);
			throw new ApplicationException();
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#saveAuditoriumDetails(com.kp.cms.bo.auditorium.AuditoriumBooking)
	 */
	@Override
	public boolean saveAuditoriumDetails(AuditoriumBooking booking)
			throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean flag = false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.save(booking);
			transaction.commit();
			flag = true;
		}
		catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
			log.debug("Error during saving data...", exception);
		}
		finally{
			session.flush();
			session.close();
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getCalendarEventsData()
	 */
	@Override
	public List<AuditoriumBooking> getCalendarEventsData() throws Exception {
		Session session = null;
		List<AuditoriumBooking> savedEventsList = null;
		try{
			session = HibernateUtil.getSession();
			String str="from AuditoriumBooking booking where booking.eventCanceled=0";
			Query query = session.createQuery(str);
			savedEventsList = query.list();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occured while getting Events", e);
			throw new ApplicationException();
		}
		return savedEventsList;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getUserName(java.lang.String)
	 */
	@Override
	public Users getUserName(String uid) throws Exception {
		Session session = null;
		Users user = null;
		try{
			session = HibernateUtil.getSession();
			String str="select user from Users user where user.id='"+uid+"'";
			Query query = session.createQuery(str);
			user =  (Users) query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Exception occured while getting UserName", e);
			throw new ApplicationException();
		}
		return user;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#updateCalendarEventsData(com.kp.cms.forms.auditorium.AuditoriumBookingForm)
	 */
	@Override
	public boolean updateCalendarEventsData(AuditoriumBookingForm bookingForm) {


		Session session = null;
		Transaction transaction = null;
        AuditoriumBooking auditoriumBooking=null;
        boolean update=false;
        Set<BookingRequirements> bookingRequirementSet=new HashSet<BookingRequirements>();
		try {
			String query = "from AuditoriumBooking aud where aud.id='"+bookingForm.getEventId()+"' and aud.eventCanceled=0";
			session = HibernateUtil.getSession();
            transaction=session.beginTransaction();
			Query query2 = session.createQuery(query);
			auditoriumBooking = (AuditoriumBooking) query2.uniqueResult();
			if(auditoriumBooking!=null)
			{
				if(bookingForm.getMode().equalsIgnoreCase("AdminApproved")|| bookingForm.getMode().equalsIgnoreCase("AdminDropAndResize"))
				{
				auditoriumBooking.setAuthorizedBy(bookingForm.getUserId());
				auditoriumBooking.setIsApproved(true);
				auditoriumBooking.setRejected(false);
				auditoriumBooking.setAdminRemarks(null);
				}
				else if( bookingForm.getMode().equalsIgnoreCase("AdminModify"))
				{
				auditoriumBooking.setAdminRemarks(bookingForm.getAdminRemarks());
			    auditoriumBooking.setAuthorizedBy(bookingForm.getUserId());
			    auditoriumBooking.setIsApproved(true);
			    auditoriumBooking.setRejected(false);
				}
				else{
					auditoriumBooking.setModifiedBy(bookingForm.getUserId());
				}
				setUserMailIdAndMobileNoByUserIdToForm(Integer.parseInt(auditoriumBooking.getCreatedBy()),bookingForm);
				auditoriumBooking.setLastModifiedDate(new Date());
				if(bookingForm.getStartTime()!=null && !bookingForm.getStartTime().isEmpty())
				{
				auditoriumBooking.setStartTime(bookingForm.getStartTime());
				}
				if(bookingForm.getEndTime()!=null && !bookingForm.getEndTime().isEmpty())
				{
				auditoriumBooking.setEndTime(bookingForm.getEndTime());
				}
				if(bookingForm.getDate()!=null && !bookingForm.getDate().isEmpty())
				{
				auditoriumBooking.setDate(CommonUtil.ConvertStringToDateForAuditorium(bookingForm.getDate()));
				}
				if(bookingForm.getBlockId()!=null && !bookingForm.getBlockId().isEmpty())
				{
					BlockDetails block=getBlockByID(Integer.parseInt(bookingForm.getBlockId()));
					auditoriumBooking.setBlock(block);
				}
				if(bookingForm.getVenueId()!=null && !bookingForm.getVenueId().isEmpty())
				{
					VenueDetails details=getVenueByID(Integer.parseInt(bookingForm.getVenueId()));
				    auditoriumBooking.setVenue(details);
				}
				if(bookingForm.getRemarks()!=null && !bookingForm.getRemarks().isEmpty());
				{
					auditoriumBooking.setRemarks(bookingForm.getRemarks());
				}
				if(bookingForm.getMultipleCheckValues()!=null && !bookingForm.getMultipleCheckValues().isEmpty())
				{
				String bookingRequir[]=bookingForm.getMultipleCheckValues().split(",");
				for (String bookedRequirId : bookingRequir) {
		            BookingRequirements requirements=getBookingRequirById(Integer.parseInt(bookedRequirId)) ;
					bookingRequirementSet.add(requirements);
				}
				auditoriumBooking.setBookingRequirements(bookingRequirementSet);
				}
				
				auditoriumBooking.setRejected(false);
				session.saveOrUpdate(auditoriumBooking);
				transaction.commit();
				session.flush();
				update=true;
			}
			
       		}		
			catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
		}
		return update;
	}




	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getRequirementsMap()
	 */
	@Override
	public List<BookingRequirements> getRequirementsMap() throws Exception {
		Session session = null;
		List<BookingRequirements> bookingRequirementList = null;
		try{
			session = HibernateUtil.getSession();
			String str="from BookingRequirements requir where requir.isActive = 1";
			Query query = session.createQuery(str);
			bookingRequirementList = query.list();
		}catch(Exception e){
			log.error("Exception occured while getting Block Details", e);
			throw new ApplicationException();
		}
		return bookingRequirementList;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getBookingRequirById(int)
	 */
	public BookingRequirements getBookingRequirById(int id) throws Exception
		{
			Session session = null;
	       BookingRequirements bookingRequirements=null;
			try{
				session = HibernateUtil.getSession();
				String str="from BookingRequirements requir where requir.isActive = 1 and requir.id="+id;
				Query query = session.createQuery(str);
				bookingRequirements = (BookingRequirements) query.uniqueResult();
			}catch(Exception e){
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}
			return bookingRequirements;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getRequirementsId()
		 */
		@Override
		public List<Integer> getRequirementsId() throws Exception {
			Session session = null;
			List<Integer> bookingRequirementIdList = null;
			try{
				session = HibernateUtil.getSession();
				String str="select requir.id from BookingRequirements requir where requir.isActive = 1";
				Query query = session.createQuery(str);
				bookingRequirementIdList = query.list();
			}catch(Exception e){
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}
			return bookingRequirementIdList;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#deleteEventByEventId(com.kp.cms.forms.auditorium.AuditoriumBookingForm)
		 */
		@Override
		public boolean deleteEventByEventId(AuditoriumBookingForm auditoriumBookingForm) throws Exception {
			Session session = null;
			Transaction transaction = null;
	        AuditoriumBooking auditoriumBooking=null; 
	        boolean deleted=false;
			try {
				String query = "from AuditoriumBooking aud where aud.rejected=0 and aud.id='"+auditoriumBookingForm.getEventId()+"' and aud.eventCanceled=0";
				session = HibernateUtil.getSession();
	            transaction=session.beginTransaction();
				Query query2 = session.createQuery(query);
				auditoriumBooking = (AuditoriumBooking) query2.uniqueResult();
				if(auditoriumBooking!=null)
				{
					if(auditoriumBookingForm.getMode().equalsIgnoreCase("CancelEventByUser")){
						setUserMailIdAndMobileNoByUserIdToForm(Integer.parseInt(auditoriumBooking.getCreatedBy()),auditoriumBookingForm);
						auditoriumBookingForm.setAuditoriumBooking(auditoriumBooking);
						auditoriumBooking.setEventCanceled(true);
						auditoriumBooking.setModifiedBy(auditoriumBookingForm.getUserId());
						auditoriumBooking.setLastModifiedDate(new Date());
						session.merge(auditoriumBooking);
						transaction.commit();
						session.flush();
						deleted=true;
					}else{
						setUserMailIdAndMobileNoByUserIdToForm(Integer.parseInt(auditoriumBooking.getCreatedBy()),auditoriumBookingForm);
						auditoriumBookingForm.setAuditoriumBooking(auditoriumBooking);
						session.delete(auditoriumBooking);
						transaction.commit();
						session.flush();
						deleted=true;
					}
					
				}
			}catch(Exception e){
				if (transaction != null)
					transaction.rollback();
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}
			return deleted;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getAuditoriumBookingByEventId(int)
		 */
		public AuditoriumBooking getAuditoriumBookingByEventId(int eventId) throws Exception
		{
			Session session = null;
			Transaction transaction = null;
	        AuditoriumBooking auditoriumBooking=null; 
			try {
				String query = "from AuditoriumBooking aud where aud.id='"+eventId+"' and aud.eventCanceled=0";
				session = HibernateUtil.getSession();
	            transaction=session.beginTransaction();
				Query query2 = session.createQuery(query);
				auditoriumBooking = (AuditoriumBooking) query2.uniqueResult();
		}catch(Exception e){
			if (transaction != null)
				transaction.rollback();
			log.error("Exception occured while getting Block Details", e);
			throw new ApplicationException();
		}
			return auditoriumBooking;
		}
		
		
		/**
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public BlockDetails getBlockByID(int id) throws Exception
		{
			Session session = null;
			BlockDetails block = null;
			try{
				session = HibernateUtil.getSession();
				String str="from BlockDetails block where block.isActive = 1 and block.id="+id;
				Query query = session.createQuery(str);
				block = (BlockDetails) query.uniqueResult();
			}catch(Exception e){
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}
			return block;
			
		}
		
		/**
		 * @param id
		 * @return
		 * @throws Exception
		 */
		public VenueDetails getVenueByID(int id)throws Exception{
			Session session = null;
			VenueDetails venue = null;
			try{
				session = HibernateUtil.getSession();
				String str="from VenueDetails venue where venue.isActive = 1 and venue.id="+id;
				Query query = session.createQuery(str);
				venue = (VenueDetails) query.uniqueResult();
			}catch(Exception e){
				e.printStackTrace();
				log.error("Exception occured while getting Venue Details", e);
				throw new ApplicationException();
			}
			return venue;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#rejectEventByApprover(com.kp.cms.forms.auditorium.AuditoriumBookingForm)
		 */
		@Override
		public boolean rejectEventByApprover(
				AuditoriumBookingForm auditoriumBookingForm) throws Exception {
			Session session = null;
			Transaction transaction = null;
	        AuditoriumBooking auditoriumBooking=null; 
	        boolean rejected=false;
			try {
				String query = "from AuditoriumBooking aud where aud.id='"+auditoriumBookingForm.getEventId()+"' and aud.eventCanceled=0";
				session = HibernateUtil.getSession();
	            transaction=session.beginTransaction();
				Query query2 = session.createQuery(query);
				auditoriumBooking = (AuditoriumBooking) query2.uniqueResult();
				if(auditoriumBooking!=null)
				{
					setUserMailIdAndMobileNoByUserIdToForm(Integer.parseInt(auditoriumBooking.getCreatedBy()),auditoriumBookingForm);
					auditoriumBookingForm.setAuditoriumBooking(auditoriumBooking);
					auditoriumBooking.setAdminRemarks(auditoriumBookingForm.getAdminRemarks());
					auditoriumBooking.setRejected(true);
					auditoriumBooking.setIsApproved(false);
					auditoriumBooking.setAuthorizedBy(auditoriumBookingForm.getUserId());
					auditoriumBooking.setRejectedBy(auditoriumBookingForm.getUserId());
					session.merge(auditoriumBooking);
					transaction.commit();
					session.flush();
					rejected=true;
				}
			}catch(Exception e){
				if (transaction != null)
					transaction.rollback();
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}
			return rejected;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#setUserMailIdAndMobileNoByUserIdToForm(int, com.kp.cms.forms.auditorium.AuditoriumBookingForm)
		 */
		public void setUserMailIdAndMobileNoByUserIdToForm(int id,AuditoriumBookingForm bookingForm) throws Exception
		{
			Session session = null;
			Transaction transaction = null;
			try {
				String query = "select user.employee.workEmail,user.employee.otherEmail, user.employee.currentAddressMobile1,user.employee.firstName from Users user where user.id="+id;
				session = HibernateUtil.getSession();
	            transaction=session.beginTransaction();
				Query query2 = session.createQuery(query);
				Object[] userData =  (Object[]) query2.uniqueResult();
				if(userData!=null)
				{
	               if(userData[0]!=null && !userData[0].toString().isEmpty()){
	            	   bookingForm.setMailId(userData[0].toString());
	               }else if(userData[1]!=null && !userData[1].toString().isEmpty()){
	            	   bookingForm.setMailId(userData[1].toString());
	               }
	               if(userData[2]!=null && !userData[2].toString().isEmpty()){
	            	   bookingForm.setMobileNo(userData[2].toString());
	               }
	               if(userData[3]!=null && !userData[3].toString().isEmpty()){
	            	   bookingForm.setUserName(userData[3].toString());
	               }
				}
			}catch(Exception e){
				if (transaction != null)
					transaction.rollback();
				log.error("Exception occured while getting Block Details", e);
				throw new ApplicationException();
			}	
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getAdminMailIdByVenueAndBlock(java.lang.String, int)
		 */
		@Override
		public String getAdminMailIdByVenueAndBlock(String venue, int blockId)
				throws Exception {
			Session session = null;
            String emailId=null;
			try{
				session=HibernateUtil.getSession();
				String str="select venue.emailId from VenueDetails venue where venue.venueName='"+venue+"' and venue.block.id="+blockId+" and venue.isActive=1";
				Query query = session.createQuery(str);
				emailId = (String) query.uniqueResult();
			}catch(Exception e){
				log.error("Exception occured while getting Time", e);
				throw new ApplicationException();
			}
			return emailId;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getCalendarEventsByBlockAndVenue(com.kp.cms.forms.auditorium.AuditoriumBookingForm)
		 */
		@Override
		public List<AuditoriumBooking> getCalendarEventsByBlockAndVenue(
				AuditoriumBookingForm auditoriumBookingForm) throws Exception {
			Session session = null;
			List<AuditoriumBooking> savedEventsListByBlockAndVenue = null;
			try{
				session = HibernateUtil.getSession();
				String str="from AuditoriumBooking booking where booking.block.id='"+auditoriumBookingForm.getBlockId()+"' and booking.venue.id='"+auditoriumBookingForm.getVenueId()+"' and booking.eventCanceled=0";
				Query query = session.createQuery(str);
				savedEventsListByBlockAndVenue = query.list();
			}catch(Exception e){
				e.printStackTrace();
				log.error("Exception occured while getting Events", e);
				throw new ApplicationException();
			}
			return savedEventsListByBlockAndVenue;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getTotalCalendarEventId()
		 */
	/*	@Override
		public Long getTotalCalendarEventId() throws Exception {
			Session session = null;
			Long totalCalendarEventId = null;
			try{
				session = HibernateUtil.getSession();
				String str="select count(*) from AuditoriumBooking booking";
				Query query = session.createQuery(str);
				totalCalendarEventId = (Long) query.uniqueResult();
			}catch(Exception e){
				e.printStackTrace();
				log.error("Exception occured while getting Events", e);
				throw new ApplicationException();
			}
			return totalCalendarEventId;
		}*/
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getApprovedEvents()
		 */
		@Override
		public List<AuditoriumBooking> getApprovedEvents() throws Exception {
			Session session = null;
			List<AuditoriumBooking> auditoriumBookingList = null;
			try{
				Date utilDate=new Date();
				session = HibernateUtil.getSession();
				String str="from AuditoriumBooking booking where booking.eventCanceled=0 and booking.isApproved=1  and booking.date>='"+new java.sql.Date(utilDate.getTime())+"' order by booking.date,booking.startTime";
				Query query = session.createQuery(str);
				auditoriumBookingList = query.list();
			}catch(Exception e){
				e.printStackTrace();
				log.error("Exception occured while getting Events", e);
				throw new ApplicationException();
			}
			return auditoriumBookingList;
		}
		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction#getPendingApprovedEvents()
		 */
		@Override
		public List<AuditoriumBooking> getPendingApprovedEvents()
				throws Exception {
			Session session = null;
			List<AuditoriumBooking> auditoriumBookingList = null;
			try{
				Date utilDate=new Date();
				session = HibernateUtil.getSession();
				String str="from AuditoriumBooking booking where booking.eventCanceled=0 and booking.isApproved=0  and booking.date>='"+new java.sql.Date(utilDate.getTime())+"' order by booking.date,booking.startTime";
				Query query = session.createQuery(str);
				auditoriumBookingList = query.list();
			}catch(Exception e){
				e.printStackTrace();
				log.error("Exception occured while getting Events", e);
				throw new ApplicationException();
			}
			return auditoriumBookingList;
		}
}
