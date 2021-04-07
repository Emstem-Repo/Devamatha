package com.kp.cms.transactionsimpl.hostel;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.transactions.hostel.IAvailableSeatsTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AvalilableTransactionImpl implements IAvailableSeatsTransaction {
	/**
	 * instance()
	 */
	public static volatile AvalilableTransactionImpl avalilableTransactionImpl = null;

	public static AvalilableTransactionImpl getInstance() {
		if (avalilableTransactionImpl == null) {
			avalilableTransactionImpl = new AvalilableTransactionImpl();
		}
		return avalilableTransactionImpl;
	}

	@Override
	public Map<String, String> getHostelMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HlHostel h where h.isActive = 1 order by h.name asc");
			List<HlHostel> list=query.list();
			if(list!=null){
				Iterator<HlHostel> iterator=list.iterator();
				while(iterator.hasNext()){
					HlHostel hostel=iterator.next();
					map.put(String.valueOf(hostel.getId()),hostel.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	}

	@Override
	public boolean saveAvailableSeats(AvailableSeatsBo boList)throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isAdded = false;
		try{
			session  = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
				session.save(boList);
			tx.commit();
			isAdded = true;
			}catch (Exception e) {
				isAdded =false;
				throw new ApplicationException(e);
			}
			finally {
				if (session != null) {
					session.flush();
					session.close();
				}
			}
		return isAdded;
		}

	@Override
	public List<AvailableSeatsBo> getAvailableSeatsList() throws Exception {
		Session session=null;
		List<AvailableSeatsBo> hList=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AvailableSeatsBo d where d.isActive=1");
			 hList=query.list();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return hList;
	}

	@Override
	public boolean deleteAvailableSeats(AvailableSeatsForm availableSeatsForm)throws Exception {
		Session session = null;
		Transaction tx = null;
		boolean isDeleted = false;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			AvailableSeatsBo availableSeatsBo = (AvailableSeatsBo)session.get(AvailableSeatsBo.class, availableSeatsForm.getId());
			availableSeatsBo.setLastModifiedDate(new Date());
			availableSeatsBo.setModifiedBy(availableSeatsForm.getUserId());
			availableSeatsBo.setIsActive(false);
			session.update(availableSeatsBo);
			tx.commit();
			isDeleted = true;
		}catch (Exception e) {
			isDeleted = false;
			throw new ApplicationException(e);
		}
		finally {
		if (session != null) {
			session.flush();
			session.close();
				}
			}
		return isDeleted;
	}

	@Override
	public AvailableSeatsBo getAvailableSeatsDetails(int id) throws Exception {
		Session session=null;
		AvailableSeatsBo availableSeatsBo=null;
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from AvailableSeatsBo d where d.isActive=1 and d.id="+id);
			availableSeatsBo=(AvailableSeatsBo)query.uniqueResult();
			
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}
		return availableSeatsBo;
	}

	@Override
	public Map<Integer, String> getRoomTypeMap(String id) throws Exception {
		Session session=null;
		Map<Integer,String> map=new HashMap<Integer, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from HlRoomType d where d.isActive=1 and d.hlHostel.id="+id);
			List<HlRoomType> list=query.list();
			if(list!=null){
				Iterator<HlRoomType> iterator=list.iterator();
				while(iterator.hasNext()){
					HlRoomType hlRoomType=iterator.next();
					map.put(hlRoomType.getId(),hlRoomType.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
			return map;
	}

	@Override
	public boolean updateAvailableSeats(AvailableSeatsBo availableSeatsBo)throws Exception {
		Session session=null;
		boolean flag=false;
		Transaction tx=null;
		try{
				session=HibernateUtil.getSession();
				tx=session.beginTransaction();
				tx.begin();
				session.merge(availableSeatsBo);
				tx.commit();
				flag=true;
		}catch(Exception e){
			tx.rollback();
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return flag;
}

	@Override
	public int getTotalRoomsByRoomIdAndHostelId(int hostelId,int roomId) throws Exception {
		Session session=null;
		int totalSeats;
		Transaction tx=null;
		try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("select d.id from HlRoom d where d.isActive=1 and d.hlHostel.id="+hostelId+"and d.hlRoomType.id="+roomId);
				List<HlRoom> list=query.list();
				int totalRooms=list.size();
				Query query1=session.createQuery("select d.noOfOccupants from HlRoomType d where d.isActive=1 and d.hlHostel.id="+hostelId+"and d.id="+roomId);
				int numberOfOccupents=(Integer)query1.uniqueResult();
				totalSeats=totalRooms*numberOfOccupents;
		}catch(Exception e){
			throw new ApplicationException(e);
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		return totalSeats;
}

}
