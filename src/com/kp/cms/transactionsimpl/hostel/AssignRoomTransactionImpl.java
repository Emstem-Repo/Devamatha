package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.AssignRoomMasterForm;
import com.kp.cms.helpers.hostel.AssignRoomMasterHelper;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.transactions.hostel.IAssignRoomTransactions;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AssignRoomTransactionImpl implements IAssignRoomTransactions {
	private static final Log log = LogFactory.getLog(AssignRoomTransactionImpl.class);
	private static volatile AssignRoomTransactionImpl roomTransactionImpl = null;

	public static AssignRoomTransactionImpl getInstance() {
		if (roomTransactionImpl == null) {
			roomTransactionImpl = new AssignRoomTransactionImpl();
		}
		return roomTransactionImpl;
	}

	 /**
	  * saving to table
	  */
	public boolean addRooms(List<HlRoom> roomBOList) throws Exception {
		log.debug("inside addRooms");
		Session session = null;
		Transaction transaction = null;
		HlRoom hlRoom;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<HlRoom> iterator = roomBOList.iterator();
			int count = 0;
			while(iterator.hasNext()){
				hlRoom = iterator.next();
				session.saveOrUpdate(hlRoom);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving addRooms");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addRooms impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addRooms impl...", e);
			throw new ApplicationException(e);
		}
	}

	/**
	 * duplication checking
	 */
	public Boolean isRoomNoDuplicated(int id,String floorNo) throws Exception {
		log.debug("inside isRoomNoDuplicated");
		Session session = null;
		Boolean result = false;
		try {
			//SessionFactory sessionFactory = InitSessionFactory.getInstance();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlRoom h where h.isActive = 1 and h.hlHostel.id = " + id+" and h.floorNo="+floorNo);
			List<HlRoom> hlRoomList = session.createQuery(sqlString.toString()).list();
			if(hlRoomList!= null && hlRoomList.size() > 0){
				result = true;
			}
			session.flush();
			//session.close();
			//sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			//session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isRoomNoDuplicated");
		return result;
	}

	public List<HlRoom> getRoomsByHostelAndFloor(int hosterlId,int floorNo, String blockId, String unitId) throws Exception
	{
		log.debug("in side getRoomsByHostelAndFloor of AssignRoomTransactionImpl");
		Session session=null;
		List<HlRoom>roomList;
		try
		{
			session=HibernateUtil.getSession();
			String query = "from HlRoom h where h.isActive = 1 " +
							"and h.hlRoomType.isActive=1 and h.hlHostel.id = " +hosterlId+
							" and h.floorNo="+floorNo+
							" and h.hlBlock.id="+blockId+" and h.hlUnit.id="+unitId;
			roomList = session.createQuery(query).list();
		}
		catch (Exception e) {
			log.error("Error during getRoomsByHostelAndFloor of AssignRoomTransactionImpl");
			throw new ApplicationException(e);
		}
		return roomList;
	}
	
	public boolean deleteRoom(Integer roomNo)throws Exception
	{
		log.debug("in side delete room  of AssignRoomTransactionImpl");
		Session session=null;
		boolean isDeleted=false;
		Transaction transaction = null;
		try
		{
			session=HibernateUtil.getSession();
			transaction = session.beginTransaction();
			HlRoom room=(HlRoom)session.get(HlRoom.class,roomNo);
			room.setIsActive(false);
			if(room.getHlBeds() != null){
				Iterator<HlBeds> iterator = room.getHlBeds().iterator();
				Set<HlBeds> set = new HashSet<HlBeds>();
				while (iterator.hasNext()) {
					HlBeds hlBeds = (HlBeds) iterator.next();
					hlBeds.setIsActive(false);
					set.add(hlBeds);
				}
				room.setHlBeds(set);
			}
			session.update(room);
			transaction.commit();
			session.flush();
			session.close();
			isDeleted=true;
		}
		catch (Exception e) {
			log.error("Error during getRoomsByHostelAndFloor of AssignRoomTransactionImpl");
			throw new ApplicationException(e);
		}
		return isDeleted;
	}
	
	public boolean updateRooms(List<HlRoom> roomBOList) throws Exception
	{
		log.debug("inside update rooms");
		Session session = null;
		Transaction transaction = null;
		HlRoom hlRoom;
		try {
			//session = HibernateUtil.getSessionFactory().openSession();
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Iterator<HlRoom> iterator = roomBOList.iterator();
			int count = 0;
			while(iterator.hasNext()){
				hlRoom = iterator.next();
				session.merge(hlRoom);
				if(++count % 20 == 0){
					session.flush();
					session.clear();
				}
			}
			
			transaction.commit();
			session.flush();
			session.close();
			log.debug("leaving addRooms");
			return true;
		} catch (ConstraintViolationException e) {
			transaction.rollback();
			log.error("Error in addRooms impl...", e);
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			log.error("Error in addRooms impl...", e);
			throw new ApplicationException(e);
		}
	}

	@Override
	public Map<String, Integer> getRoomsByHostelAndFloorForDuplicateCheck(
			int hostelId, int floorNo) throws Exception {
		log.debug("inside isRoomNoDuplicated");
		Session session = null;
		Map<String, Integer> result = new HashMap<String, Integer>();
		try {
			SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();
//			session = HibernateUtil.getSession();
			StringBuffer sqlString = new StringBuffer("from HlRoom h where h.isActive = 1 and h.hlHostel.id ="+hostelId+" and h.floorNo="+floorNo);
			List<HlRoom> hlRoomList = session.createQuery(sqlString.toString()).list();
			if(hlRoomList!= null && hlRoomList.size() > 0){
				Iterator<HlRoom> itr=hlRoomList.iterator();
				while (itr.hasNext()) {
					HlRoom hlRoom = (HlRoom) itr.next();
					result.put(hlRoom.getName(),hlRoom.getId());
				}
			}
			session.flush();
			session.close();
			sessionFactory.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isRoomNoDuplicated");
		return result;
	}
	
	public boolean getRoomsByHostelAndFloorForDuplicateCheck1(AssignRoomMasterForm roomForm) throws Exception {
		log.debug("inside isRoomNoDuplicated1");
		Session session = null;
		boolean result = false;
		try {
			session = HibernateUtil.getSession();
			String sqlString = "from HlRoom h where h.isActive = 1 " +
								"and h.hlHostel.id ="+roomForm.getHostelId()+" and h.floorNo="+roomForm.getFloorNo()+ 
								" and h.hlBlock.id="+roomForm.getBlock()+" and h.hlUnit.id="+roomForm.getUnit();
			List<HlRoom> hlRoomList = session.createQuery(sqlString).list();
			if(hlRoomList != null){
				List<HlRoomTO> roomToList=AssignRoomMasterHelper.getInstance().getRoomToList(hlRoomList,roomForm);
				roomForm.setRoomList(roomToList);
			}
			int count = hlRoomList.size();
			int noOfRooms = Integer.parseInt(roomForm.getNoOfRooms());
			if(count<noOfRooms ){
				return result;
			}
			if(hlRoomList!= null && hlRoomList.size() > 0){
				result = true;
				}
			
			session.flush();
			session.close();
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			session.close();
			throw new ApplicationException(e);
		}
		log.debug("leaving isRoomNoDuplicated");
		return result;
	}
		

}
