package com.kp.cms.transactionsimpl.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelStatusInfoForm;
import com.kp.cms.to.admin.RoomTO;
import com.kp.cms.to.hostel.HlFloorTo;
import com.kp.cms.to.hostel.HostelStatusInfoTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.transactions.hostel.IHostelStatusInfoTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class HostelStatusInfoTransactionImpl implements
		IHostelStatusInfoTransaction {
	private static final Log log = LogFactory.getLog(HostelStatusInfoTransactionImpl.class);
	@SuppressWarnings("unchecked")
	public List<HlRoom> getHostelStatusInfoDetails(
			HostelStatusInfoTO hostelStatusInfoTO) throws Exception {
		Session session = null;
		List<HlRoom> roomList;
		try {
			Query query = null;
			session = HibernateUtil.getSession();
			StringBuffer stb=new StringBuffer("from HlRoom room ");

			if(hostelStatusInfoTO.getHostelId()!=null){
				stb.append(" where room.hlHostel.id="+Integer.parseInt(hostelStatusInfoTO.getHostelId()));			
			}
			if(hostelStatusInfoTO.getRoomTypeName()!=null){
				
				stb.append(" and room.hlRoomType.id in (");			
				for(int cnt=0;cnt<hostelStatusInfoTO.getRoomTypeName().length;){
					stb=stb.append(hostelStatusInfoTO.getRoomTypeName()[cnt]);
					if(cnt<hostelStatusInfoTO.getRoomTypeName().length-1){
						stb=stb.append(",");
					}
					cnt++;
				}
				stb.append(")");
			}
			if(hostelStatusInfoTO.getFloorNo()!=null){
				stb.append(" and room.floorNo="+hostelStatusInfoTO.getFloorNo());			
			}

			//objList = (List<Object>) query.list();
			query = session.createQuery(stb.toString());
			roomList = query.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		return roomList;
	}

	public StringBuffer getQueryConditions(HostelStatusInfoTO hostTo,
			StringBuffer stb) throws Exception {
		int cnt=0;
		if (hostTo.getHostelId() != null) {
			stb.append(" where hostel.id=:hostelId");
		}
		if (hostTo.getFloorNo() != null) {
			if (hostTo.getHostelId() == null || hostTo.getHostelId().equals("")) {
				stb.append(" where");
			} else {
				stb.append(" and");
			}
			stb.append(" room.floorNo=:floorNo");
		}
		if (hostTo.getRoomTypeName() != null) {
			if (hostTo.getHostelId() == null || hostTo.getHostelId().equals("")) {
				if (hostTo.getFloorNo() == null
						|| hostTo.getFloorNo().equals("")) {
					stb.append(" where");
				} else {
					stb.append(" and");
				}
			} else {
				stb.append(" and");
			}		
			
			stb.append(" roomtype.id in (");			
			for(;cnt<hostTo.getRoomTypeName().length;){
				stb=stb.append(hostTo.getRoomTypeName()[cnt]);
				if(cnt<hostTo.getRoomTypeName().length-1){
					stb=stb.append(",");
				}
				cnt++;
			}
			stb.append(")");
		}
		return stb;
	}

	public List<Object> dipslayRoomDetails(HostelStatusInfoTO hostTo) throws Exception {
		Session session = null;
		List<Object> objRoomList;

		try {
			Query query = null;
			session = InitSessionFactory.getInstance().openSession();

			
			StringBuffer stb=new StringBuffer("select distinct roomtype.name,room.name,trans.txnDate,stat.statusType," +
					" trans.currentOccupantsCount, trans.currentReservationCount from HlRoom room join room.hlRoomTransactions trans join trans.hlStatus stat" +
					" join room.hlHostel hostel join room.hlRoomType roomtype ");
			
			if (hostTo.getHostelId() != null
					|| hostTo.getFloorNo() != null
					|| hostTo.getRoomTypeName() != null) {
				getQueryConditions(hostTo, stb);
			}
			//stb.append(")");
			if(hostTo.getRoomTxnDate() != null){
				stb.append(" and trans.txnDate="+"'"+CommonUtil.ConvertStringToSQLExactDate1(hostTo.getRoomTxnDate().trim())+"'");
			}			
			
			query = session.createQuery(stb.toString());
			
			if (hostTo.getHostelId() != null) {
				query.setInteger("hostelId", Integer.parseInt(hostTo.getHostelId()));
			}
			if (hostTo.getFloorNo() != null) {
				query.setString("floorNo", hostTo.getFloorNo());
			}		
			
			objRoomList = (List<Object>)query.list();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return objRoomList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelStatusInfoTransaction#getFloorsByHostel(int)
	 */
	@Override
	public List<HlFloorTo> getFloorsByHostel(int parseInt) throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from HlHostel h where h.isActive = 1 and  h.id ="+ parseInt);
			List<HlHostel> hostelList = query.list();
			List<HlFloorTo> floorTos = new ArrayList<HlFloorTo>();
			Iterator<HlHostel> itr = hostelList.iterator();
			HlHostel hlHostel;
			int floorNo = 0;
			while (itr.hasNext()) {
				hlHostel = (HlHostel) itr.next();
				floorNo = hlHostel.getNoOfFloors();
			}
			for (int i = 1; i <= floorNo; i++) {
				HlFloorTo hlFloorTo=new HlFloorTo();
				hlFloorTo.setName(Integer.toString(i));
				floorTos.add(hlFloorTo);
			}
			session.flush();
			// session.close();
			return floorTos;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new ArrayList<HlFloorTo>();
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.hostel.IHostelStatusInfoTransaction#getRoomTypeList(int, java.lang.String)
	 */
	@Override
	public List<HlRoomType> getRoomTypeList(int parseInt, String name)
			throws Exception {
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("select h.hlRoomType " +
							" from HlRoom h where h.isActive=1 " +
							" and h.hlHostel.id=" +parseInt+
							" and h.floorNo='"+name+"' and h.hlRoomType.isActive=1  group by h.hlRoomType.id");
			List<HlRoomType> hostelList = query.list();
			session.flush();
			// session.close();
			return hostelList;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new ArrayList<HlRoomType>();
	}

	@Override
	public List<RoomTypeTO> getFinalRoomTypeList(int hostelId, String floorNO,
			List<RoomTypeTO> finalList,HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
		Session session = null;
		List<RoomTypeTO> list1=new ArrayList<RoomTypeTO>();
		int totalVacant=0;
		int totalReserve=0;
		int totalPartial=0;
		int totalOccupied=0;
		
		try {
			session = InitSessionFactory.getInstance().openSession();
			Iterator<RoomTypeTO> itr=finalList.iterator();
			while (itr.hasNext()) {
				totalVacant=0;
				totalReserve=0;
				totalPartial=0;
				totalOccupied=0;
				RoomTypeTO roomTypeTO = (RoomTypeTO) itr.next();
				int maxCount=roomTypeTO.getMaxCount();
				Query query = session.createQuery("from HlRoom h where h.isActive=1 "+
						" and h.hlHostel.id="+hostelId+
						" and h.floorNo=" +floorNO+
							"and h.hlRoomType.id="+roomTypeTO.getId()+
							"and h.hlRoomType.isActive=1");
				List<HlRoom> list=query.list();
				List<RoomTO> roomTOs=new ArrayList<RoomTO>();
				Iterator<HlRoom> iterator=list.iterator();
				while (iterator.hasNext()) {
					HlRoom hlRoom = (HlRoom) iterator.next();
					RoomTO roomTO=new RoomTO();
					roomTO.setId(hlRoom.getId());
					roomTO.setName(hlRoom.getName());
					String searchQuery="from HlRoomTransaction h where h.isActive=1 " +
							" and h.hlRoom.id=" +hlRoom.getId()+
							" and h.txnDate=(select max(h1.txnDate) " +
							" from HlRoomTransaction h1 where h1.isActive=1 and h1.hlRoom.id="+hlRoom.getId();
					if(hostelStatusInfoForm.getRoomStatusOnDate()!=null && !hostelStatusInfoForm.getRoomStatusOnDate().isEmpty()){
						searchQuery=searchQuery+"  and h1.txnDate<='"+CommonUtil.ConvertStringToSQLDateTime(hostelStatusInfoForm.getRoomStatusOnDate()+" 23:59:59")+"'";
					}
					searchQuery=searchQuery+")";
					Query query1 = session.createQuery(searchQuery);
					HlRoomTransaction hlRoomTransaction=(HlRoomTransaction)query1.uniqueResult();
					int currentCount=0;
					int currentReservationCount=0;
					
					if(hlRoomTransaction!=null){
						if(hlRoomTransaction.getCurrentOccupantsCount()!=null)
							currentCount=hlRoomTransaction.getCurrentOccupantsCount();
						if(hlRoomTransaction.getCurrentReservationCount()!=null)
							currentReservationCount=hlRoomTransaction.getCurrentReservationCount();
					}
					// to set the colour in the jsp
					if((currentCount+currentReservationCount)==0){
						roomTO.setStatus("1");
					}else if(currentCount==0 && currentReservationCount>0){
						roomTO.setStatus("4");
					}else if((currentCount+currentReservationCount)>=maxCount){
						roomTO.setStatus("2");
					}else if(currentReservationCount>=maxCount){
						roomTO.setStatus("4");
					}else{
						roomTO.setStatus("3");
					}
					
					// calculating the total count accourding to room
					
					if((currentCount+currentReservationCount)==0){
						totalVacant=totalVacant+maxCount;
					}else if(currentReservationCount==0 && currentCount>=maxCount){
						totalOccupied=totalOccupied+maxCount;
					}else if(currentReservationCount==0 && currentCount<maxCount){
						totalPartial=totalPartial+currentCount;
						totalVacant=totalVacant+(maxCount-currentCount);
					}else if(currentCount==0 && currentReservationCount>=maxCount){
						totalReserve=totalReserve+maxCount;
					}else{
						totalReserve=totalReserve+currentReservationCount;
						totalOccupied=totalOccupied+currentCount;
						totalVacant=totalVacant+(maxCount-(currentCount+currentReservationCount));
					}
					roomTOs.add(roomTO);
				}
				roomTypeTO.setRoomTOs(roomTOs);
				roomTypeTO.setTotalOccupied(totalOccupied);
				roomTypeTO.setTotalPartial(totalPartial);
				roomTypeTO.setTotalReserve(totalReserve);
				roomTypeTO.setTotalVacant(totalVacant);
				list1.add(roomTypeTO);
			}
			session.flush();
			session.close();
			return list1;
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return new ArrayList<RoomTypeTO>();
	}

	/* (non-Javadoc)
	 * @author venkat
	 * @see com.kp.cms.transactions.hostel.IHostelStatusInfoTransaction#getStudentAdmDeatils(com.kp.cms.forms.hostel.HostelStatusInfoForm)
	 */
	@Override
	public Map<String,HlAdmissionBo> getStudentAdmDeatils(
			HostelStatusInfoForm hostelStatusInfoForm) throws Exception {
        List<HlAdmissionBo> admissionBoList=null;
        Map<String,HlAdmissionBo> admMap = new HashMap<String, HlAdmissionBo>();
		Session session = null;
		try {
			String queryString = "select admBo from HlAdmissionBo admBo where admBo.hostelId.id='"+hostelStatusInfoForm.getHostelId()+"' and admBo.academicYear='"+hostelStatusInfoForm.getAcademicYear()+"' and admBo.isActive=1 and admBo.isCancelled=0 and (admBo.checkOut=0 or admBo.checkOut=null)";
			if(hostelStatusInfoForm.getBlockId()!=null && !hostelStatusInfoForm.getBlockId().isEmpty()){
				queryString = queryString+" and admBo.roomId.hlBlock.id="+hostelStatusInfoForm.getBlockId()+"";
			}
			if(hostelStatusInfoForm.getUnitId()!=null && !hostelStatusInfoForm.getUnitId().isEmpty()){
				queryString = queryString+" and admBo.roomId.hlUnit.id="+hostelStatusInfoForm.getUnitId();
			}
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery(queryString);
			admissionBoList = query.list();
			Iterator itr=admissionBoList.iterator();
			
			
			while(itr.hasNext()){
				HlAdmissionBo hlAdmissionBo=(HlAdmissionBo)itr.next();
				if(hlAdmissionBo.getBedId()!=null)
				admMap.put(String.valueOf(hlAdmissionBo.getBedId().getId()), hlAdmissionBo);
			}
			session.flush();
		} catch (Exception e) {
			log.debug("Exception" + e.getMessage());
		}
		return admMap;
	}

	@Override
	public List<HlBeds> getRoomAndBedsDetailByHostelId(HostelStatusInfoForm hostelStatusInfoForm)
			throws Exception {
		 List<HlBeds> hlBedsList=null;
			Session session = null;
			try {
				session = HibernateUtil.getSession();
				String queryString = "select beds from HlBeds beds where beds.hlRoom.hlHostel.id="+hostelStatusInfoForm.getHostelId()+" and beds.isActive=1";
				if(hostelStatusInfoForm.getBlockId()!=null && !hostelStatusInfoForm.getBlockId().isEmpty()){
					queryString = queryString+" and beds.hlRoom.hlBlock.id="+hostelStatusInfoForm.getBlockId();
				}if(hostelStatusInfoForm.getUnitId()!=null && !hostelStatusInfoForm.getUnitId().isEmpty()){
					queryString= queryString+" and beds.hlRoom.hlUnit.id="+hostelStatusInfoForm.getUnitId();
				}
				Query query = session.createQuery(queryString);
				hlBedsList = query.list();
				session.flush();
			} catch (Exception e) {
				log.debug("Exception" + e.getMessage());
			}
			return hlBedsList;
	}
}
