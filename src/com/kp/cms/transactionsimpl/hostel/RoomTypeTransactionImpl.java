package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.bo.hostel.HlRoomTypeFees;
import com.kp.cms.bo.admin.HlRoomTypeImage;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.hostel.RoomTypeForm;
import com.kp.cms.transactions.hostel.IRoomTypeTransaction;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class RoomTypeTransactionImpl implements IRoomTypeTransaction{
	private static final Log log = LogFactory.getLog(RoomTypeTransactionImpl.class);

	/**
	 * Used to get all facilities
	 */
	public List<HlFacility> getAllFacility() throws Exception {
		log.info("Inside of getAllFacility of RoomTypeTransactionImpl");
		Session session = null;
		try {
			///session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlFacility> facilityList = session.createQuery("from HlFacility h where h.isActive = 1 order by h.id").list();
			log.info("End of getAllFacility of RoomTypeTransactionImpl");
			return facilityList;
		} catch (Exception e) {
			log.error("Error occured in getAllFacility of RoomTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Used for inserting roomtpe
	 */
	public boolean submitRoomType(HlRoomType roomType) throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of submitRoomType");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			session.save(roomType);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while adding RoomType in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into RoomTypeTransactionImpl of submitRoomType");
		}
	}

	/**
	 * Used to get all roomtype
	 * Displays in UI
	 */
	public List<HlRoomType> getAllRoomType() throws Exception {
		log.info("Inside of getAllRoomType of RoomTypeTransactionImpl");
		Session session = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			List<HlRoomType> roomTypeList = session.createQuery("from HlRoomType h where h.isActive = 1 and h.hlHostel.isActive = 1 order by h.id").list();
			log.info("End of getAllRoomType of RoomTypeTransactionImpl");
			return roomTypeList;
		} catch (Exception e) {
			log.error("Error occured in getAllRoomType of RoomTypeTransactionImpl");
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
	}

	/**
	 * Used to delete a roomtype
	 */
	public boolean deleteRoomType(int roomTypeId, String userId) throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of deleteRoomType");
		Session session = null;
		Transaction transaction = null;
		try {
			//session = InitSessionFactory.getInstance().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlRoomType roomType=(HlRoomType)session.get(HlRoomType.class,roomTypeId);
			roomType.setId(roomTypeId);
			roomType.setIsActive(false);
			roomType.setModifiedBy(userId);
			roomType.setLastModifiedDate(new Date());
			session.update(roomType);
			transaction.commit();
			return true;
		} catch (Exception e) {	
			if(transaction != null){
				transaction.rollback();
			}
			log.error("Exception occured while deleteRoomType in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
		log.info("Leaving into RoomTypeTransactionImpl of deleteRoomType");
		}
	}

	/**
	 * Used to check duplicate on hostel Id and roomtype
	 */
	public HlRoomType getRoomTypeOnHostelName(int hostelId, String roomType)
			throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of getRoomTypeOnHostelName");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		HlRoomType type = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from HlRoomType h where h.hlHostel.id = :hostelId" +
		 								   " and h.name = :roomType");				                          
		 query.setInteger("hostelId", hostelId);
		 query.setString("roomType",roomType);
		 type = (HlRoomType) query.uniqueResult();	
		 return type;
		}
		catch (Exception e) {	
			log.error("Exception occured while getRoomTypeOnHostelName in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			//session.close();
		}
		log.info("Leaving into RoomTypeTransactionImpl of getRoomTypeOnHostelName");
		}
	}

	/**
	 * Used to reactivate roomtype
	 */
	public boolean reactivateRoomType(int hostelId, String roomType,
			String userId) throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of reactivateRoomType");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		HlRoomType type = null;
		Transaction transaction = null;
		try { 
		 //session =sessionFactory.openSession();
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from HlRoomType h where h.hlHostel.id = :hostelId" +
		 								   " and h.name = :roomType");				                          
		 query.setInteger("hostelId", hostelId);
		 query.setString("roomType",roomType);
		 type = (HlRoomType) query.uniqueResult();	
		 transaction = session.beginTransaction();
		 type.setIsActive(true);
		 type.setModifiedBy(userId);
		 type.setLastModifiedDate(new Date());
		 session.update(type);
		 transaction.commit();
		 return true;
		}
		catch (Exception e) {	
			if(transaction!=null){
				transaction.rollback();
			}
			log.error("Exception occured while reactivateRoomType in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			session.close();
		}
		log.info("Leaving into RoomTypeTransactionImpl of reactivateRoomType");
		}
	}
	/**
	 * Used when edit button is clicked
	 */	
	public HlRoomType getDetailByRoomTypeId(int roomTypeId) throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of getDetailByRoomTypeId");
		//SessionFactory sessionFactory = InitSessionFactory.getInstance();		
		Session session = null;
		HlRoomType type = null;
		try { 
		 //session =sessionFactory.openSession();
			session = HibernateUtil.getSession();
		 Query query = session.createQuery("from HlRoomType h where h.isActive = 1 and h.id = :roomTypeId");				                          
		 query.setInteger("roomTypeId", roomTypeId);
		 type = (HlRoomType) query.uniqueResult();	
		 return type;
		}
		catch (Exception e) {	
			log.error("Exception occured while getDetailByRoomTypeId in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			//sessionFactory.close();
			session.flush();
			//session.close();
		}
		log.info("Leaving into RoomTypeTransactionImpl of getDetailByRoomTypeId");
		}
	}

	/**
	 * Used when updating a roomtype
	 */
	public boolean updateRoomType(HlRoomType roomType) throws Exception {
		log.info("Entering into updateRoomType of RoomTypeTransactionImpl");
		Session session = null;
		Transaction transaction=null;
		try {
				session=InitSessionFactory.getInstance().openSession();
//				session = HibernateUtil.getSession(); 
				transaction= session.beginTransaction();
				session.update(roomType);
				transaction.commit();
				return true;
			}
			catch(Exception e){
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Unable to update in RoomTypeTransactionImpl---");			
				throw  new ApplicationException(e);				
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
			}	
	}

	/**
	 * Used to delete roomtype Image
	 */
	public boolean deleteRoomTypeImage(int roomTypeImageId) throws Exception {
		log.info("Entering into deleteRoomTypeImage of RoomTypeTransactionImpl");
		Session session = null;
		Transaction transaction=null;
		try {
				//session=InitSessionFactory.getInstance().openSession();
				session = HibernateUtil.getSession(); 
				transaction= session.beginTransaction();
				HlRoomTypeImage image = new HlRoomTypeImage();
				image.setId(roomTypeImageId);
				session.delete(image);
				transaction.commit();
				log.info("Leaving into deleteRoomTypeImage of RoomTypeTransactionImpl");
				return true;
			}
			catch(Exception e){
				if(transaction != null){
					transaction.rollback();
				}
				log.error("Unable to deleteRoomTypeImage in RoomTypeTransactionImpl---");			
				throw  new ApplicationException(e);				
				} finally {
					if (session != null) {
						session.flush();
						session.close();
					}
			}	
	}

	@Override
	public List<FeeGroup> getAssignFeeForRoom() throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of getAssignFeeForRoom");
		Session session = null;
		List<FeeGroup> type = null;
		try { 
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from FeeGroup h where h.isActive = 1 and h.hostelFees = 1");				                          
		 type =query.list();	
		 return type;
		}
		catch (Exception e) {	
			log.error("Exception occured while getAssignFeeForRoom in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
		}
		log.info("Leaving into RoomTypeTransactionImpl of getAssignFeeForRoom");
		}
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IRoomTypeTransaction#addSynopsisDefense(com.kp.cms.bo.phd.PhdGuideDetailsBO, java.lang.String)
	 */

	@Override
	public boolean addSynopsisDefense(List<HlRoomTypeFees> roomFees)throws Exception {
		Session session=null;
		Transaction transaction=null;
		try{
			Iterator<HlRoomTypeFees> itr=roomFees.iterator();
			while (itr.hasNext()) {
			HlRoomTypeFees roomTypeFees = (HlRoomTypeFees) itr.next();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(roomTypeFees);
			transaction.commit();
			}
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

	@Override
	
	
	public List<FeeGroup> getFee() throws Exception {
        log.info("call of getFee ");
        Session session = null;
        List<FeeGroup> purposeList;
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("from FeeGroup fg where fg.isActive=1 and fg.hostelFees = 1");
            purposeList = query.list();
        }
        catch(Exception e)
        {
            log.error("Unable to getFee", e);
            throw e;
        }
        log.info("end of getFee ");
        return purposeList;
    
	}
	@Override
	public Map<Integer,Integer> getAssignFee(RoomTypeForm typeForm) throws Exception {
        log.info("call of getAssignFee");
        Session session = null;
        Map<Integer,Integer> map = new HashMap<Integer, Integer>();
       // List<CertificateDetailsRoles> AssignList = new ArrayList<CertificateDetailsRoles>();
        try
        {
            session = HibernateUtil.getSession();
            Query query = session.createQuery("select h.id, h.feegroupId.id from HlRoomTypeFees h where h.isActive = 1 and h.hostelId.id="+typeForm.getHostelId()+" and roomTypeId.id="+typeForm.getRoomTypeId());
           List<Object[]> list = query.list();
            if(list!=null && !list.isEmpty()){
            	Iterator<Object[]> iterator = list.iterator();
            	while (iterator.hasNext()) {
					Object[] objects = (Object[]) iterator.next();
					if(objects[0]!=null && !objects[0].toString().isEmpty()&& objects[1]!=null && !objects[1].toString().isEmpty()){
						map.put(Integer.parseInt(objects[1].toString()), Integer.parseInt(objects[0].toString()));
					}
				}
            }
        }
        catch(Exception e)
        {
            log.error("In AssignFees", e);
            throw e;
        }
        
        log.info("end of AssignFees.");
        return map;
    }
	public List<HlRoomTypeFees> setAssignRoomFees(RoomTypeForm typeForm)throws Exception {
		log.info("Entering into RoomTypeTransactionImpl of getAssignFeeForRoom");
		Session session = null;
		List<HlRoomTypeFees> type = null;
		try { 
		 session = HibernateUtil.getSession();
		 Query query = session.createQuery("from HlRoomTypeFees h where h.isActive = 1 and h.hostelId.id="+typeForm.getHostelId()+" and roomTypeId.id="+typeForm.getRoomTypeId());				                          
		 type =query.list();	
		 return type;
		}
		catch (Exception e) {	
			log.error("Exception occured while getAssignFeeForRoom in RoomTypeTransactionImpl :"+e);
			throw  new ApplicationException(e);
		} finally {
		if (session != null) {
			session.flush();
		}
		log.info("Leaving into RoomTypeTransactionImpl of getAssignFeeForRoom");
		}
	}
	
	public HlRoomTypeFacility getInActiveRoomTypeFacility(int id) throws Exception {
		Session session=null;
		HlRoomTypeFacility eventStAtt=null;
		try{
			session=HibernateUtil.getSession();
			String str="from HlRoomTypeFacility stAtt where stAtt.isActive=0 and stAtt.id="+id;
			Query query=session.createQuery(str);
			eventStAtt=(HlRoomTypeFacility)query.uniqueResult();
			
		}catch(Exception e){
			log.error("Error during getting EventLoactionBiometricDetailsBo by id..." , e);
			session.flush();
			session.close();
		}
		return eventStAtt;
	
	}
	

}
