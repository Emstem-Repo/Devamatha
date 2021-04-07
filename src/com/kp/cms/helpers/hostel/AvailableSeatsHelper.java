package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.to.hostel.AvailableSeatsTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;

public class AvailableSeatsHelper {
	/**
	 * instance()
	 */
	public static volatile AvailableSeatsHelper availableSeatsHelper = null;

	public static AvailableSeatsHelper getInstance() {
		if (availableSeatsHelper == null) {
			availableSeatsHelper = new AvailableSeatsHelper();
		}
		return availableSeatsHelper;
	}
	/**
	 * copy Form to Bo
	 * @param availableSeatsForm
	 * @return
	 * @throws Exception
	 */
	public AvailableSeatsBo copyFormToAvailableSeatsBo( AvailableSeatsForm availableSeatsForm) throws Exception{
		AvailableSeatsBo availableSeatsBo=null;
		if(availableSeatsForm!=null){
			availableSeatsBo=new AvailableSeatsBo();
			if(availableSeatsForm.getAcademicYear()!=null && !availableSeatsForm.getAcademicYear().isEmpty()){
				availableSeatsBo.setAcademicYear(availableSeatsForm.getAcademicYear());
			}
			if(availableSeatsForm.getHostelId()!=null && !availableSeatsForm.getHostelId().isEmpty()){
				HlHostel hlHostel=new HlHostel();
				hlHostel.setId(Integer.valueOf(availableSeatsForm.getHostelId()));
				availableSeatsBo.setHostelId(hlHostel);
			}
			if(availableSeatsForm.getRoomId()!=null && !availableSeatsForm.getRoomId().isEmpty()){
				HlRoomType hlRoomType=new HlRoomType();
				hlRoomType.setId(Integer.valueOf(availableSeatsForm.getRoomId()));
				availableSeatsBo.setRoomTypeId(hlRoomType);
			}
			if(availableSeatsForm.getNumOfAvailableSeats()!=null && !availableSeatsForm.getNumOfAvailableSeats().isEmpty()){
				availableSeatsBo.setNumOfAvailableSeats(availableSeatsForm.getNumOfAvailableSeats());
			}
			availableSeatsBo.setCreatedBy(availableSeatsForm.getUserId());
			availableSeatsBo.setModifiedBy(availableSeatsForm.getUserId());
			availableSeatsBo.setCreatedDate(new Date());
			availableSeatsBo.setLastModifiedDate(new Date());
			availableSeatsBo.setIsActive(true);
			
		}
	return availableSeatsBo;
	}
	/**
	 * convert AvalableseatsBo to AvailableSeatsTo
	 * @param connections
	 * @return
	 * @throws Exception
	 */
	public List<AvailableSeatsTo> copyBOToToList( List<AvailableSeatsBo> availableSeatsBos) throws Exception{
		List<AvailableSeatsTo> list = new ArrayList<AvailableSeatsTo>();
		if(availableSeatsBos!=null && !availableSeatsBos.toString().isEmpty()){
			Iterator<AvailableSeatsBo> iterator = availableSeatsBos.iterator();
			while (iterator.hasNext()) {
				AvailableSeatsBo availableSeatsBo = (AvailableSeatsBo) iterator .next();
				AvailableSeatsTo availableSeatsTo = new AvailableSeatsTo();
				if(availableSeatsBo.getId()!=0){
					availableSeatsTo.setId(availableSeatsBo.getId());
				}
				if(availableSeatsBo.getHostelId()!=null){
					availableSeatsTo.setHostelName(availableSeatsBo.getHostelId().getName());
				}
				if(availableSeatsBo.getRoomTypeId()!=null){
					availableSeatsTo.setRoomTypeName(availableSeatsBo.getRoomTypeId().getName());
				}
				if(availableSeatsBo.getAcademicYear()!=null && !availableSeatsBo.getAcademicYear().isEmpty()){
					availableSeatsTo.setAcademicYear(availableSeatsBo.getAcademicYear());
				}
				if(availableSeatsBo.getNumOfAvailableSeats()!=null && !availableSeatsBo.getNumOfAvailableSeats().isEmpty()){
					availableSeatsTo.setNumOfAvailableSeats(availableSeatsBo.getNumOfAvailableSeats());
				}
					
				list.add(availableSeatsTo);
			}
		}
		return list;
	}
	/**
	 * check duplicate Available seats
	 * @param hList
	 * @param availableSeatsForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(List<AvailableSeatsBo> hList,AvailableSeatsForm availableSeatsForm)throws Exception{
		boolean flag=false;
		Iterator<AvailableSeatsBo> iterator=hList.iterator();
		while (iterator.hasNext()) {
			AvailableSeatsBo availableSeatsBo = (AvailableSeatsBo) iterator.next();
			if(availableSeatsBo.getAcademicYear().equalsIgnoreCase(availableSeatsForm.getAcademicYear()) &&
					availableSeatsBo.getHostelId().getId()==Integer.parseInt(availableSeatsForm.getHostelId()) &&
					availableSeatsBo.getRoomTypeId().getId()==Integer.parseInt(availableSeatsForm.getRoomId())){
				flag=true;
			}
			
		}
		return flag;
		
	}
	/**
	 * copy EditForm to AvailableSeatsBo
	 * @param availableSeatsForm
	 * @return
	 * @throws Exception
	 */
	public AvailableSeatsBo copyEditFormToAvailableSeatsBo( AvailableSeatsForm availableSeatsForm) throws Exception{
		AvailableSeatsBo availableSeatsBo=new AvailableSeatsBo();
		if(availableSeatsForm.getAcademicYear()!=null && !availableSeatsForm.getAcademicYear().isEmpty()){
			availableSeatsBo.setAcademicYear(availableSeatsForm.getAcademicYear());
		}
		if(availableSeatsForm.getHostelId()!=null && !availableSeatsForm.getHostelId().isEmpty()){
			HlHostel hlHostel=new HlHostel();
			hlHostel.setId(Integer.valueOf(availableSeatsForm.getHostelId()));
			availableSeatsBo.setHostelId(hlHostel);
		}
		if(availableSeatsForm.getRoomId()!=null && !availableSeatsForm.getRoomId().isEmpty()){
			HlRoomType hlRoomType=new HlRoomType();
			hlRoomType.setId(Integer.valueOf(availableSeatsForm.getRoomId()));
			availableSeatsBo.setRoomTypeId(hlRoomType);
		}
		if(availableSeatsForm.getNumOfAvailableSeats()!=null && !availableSeatsForm.getNumOfAvailableSeats().isEmpty()){
				availableSeatsBo.setNumOfAvailableSeats(availableSeatsForm.getNumOfAvailableSeats());
			}
			availableSeatsBo.setId(availableSeatsForm.getId());
			availableSeatsBo.setModifiedBy(availableSeatsForm.getUserId());
			availableSeatsBo.setLastModifiedDate(new Date());
			availableSeatsBo.setIsActive(true);
	return availableSeatsBo;
	}

}
