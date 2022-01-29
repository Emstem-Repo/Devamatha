package com.kp.cms.handlers.hostel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.HolidaysForm;
import com.kp.cms.helpers.hostel.AvailableSeatsHelper;
import com.kp.cms.helpers.hostel.HostelHolidaysHelper;
import com.kp.cms.to.hostel.AvailableSeatsTo;
import com.kp.cms.to.hostel.HostelHolidaysTo;
import com.kp.cms.transactions.hostel.IAvailableSeatsTransaction;
import com.kp.cms.transactions.hostel.IHolidaysTransaction;
import com.kp.cms.transactions.hostel.IHostelEntryTransactions;
import com.kp.cms.transactionsimpl.hostel.AvalilableTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HolidaysTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelEntryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class AvailableSeatsHandler {
	IAvailableSeatsTransaction iAvailableSeatsTransaction = AvalilableTransactionImpl.getInstance();
	/**
	 * instance()
	 */
	public static volatile AvailableSeatsHandler availableSeatsHandler = null;

	public static AvailableSeatsHandler getInstance() {
		if (availableSeatsHandler == null) {
			availableSeatsHandler = new AvailableSeatsHandler();
		}
		return availableSeatsHandler;
	}
	/**
	 * get hostelMap
	 * @return
	 */
	public Map<String, String> getHostelMap()throws Exception {
		Map<String, String> hostelMap = iAvailableSeatsTransaction.getHostelMap();
		hostelMap = (HashMap<String, String>) CommonUtil.sortMapByValue(hostelMap);
		return hostelMap;
	}
	/**
	 * submitAvailableSeats
	 * @param holidaysForm
	 * @return
	 * @throws Exception
	 */
	public boolean submitAvailableSeats( AvailableSeatsForm availableSeatsForm) throws Exception{
		boolean isAdded = false;
			AvailableSeatsBo boList = AvailableSeatsHelper.getInstance().copyFormToAvailableSeatsBo(availableSeatsForm);
			isAdded = iAvailableSeatsTransaction.saveAvailableSeats(boList);
		return isAdded;
	}
	/**
	 * get the hostel Holidays list and set it to HostelHolidaysTo
	 * @return
	 * @throws Exception
	 */
	public List<AvailableSeatsTo> getDetails()throws Exception {
		List<AvailableSeatsBo> availableSeatsBos = iAvailableSeatsTransaction.getAvailableSeatsList();
		List<AvailableSeatsTo> availableSeatsToList = AvailableSeatsHelper.getInstance().copyBOToToList(availableSeatsBos);
		return availableSeatsToList;
	}
	/**
	 * duplicate check
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(AvailableSeatsForm availableSeatsForm) throws Exception{
		List<AvailableSeatsBo> hList=iAvailableSeatsTransaction.getAvailableSeatsList();
		boolean isCheckDuplicate = AvailableSeatsHelper.getInstance().checkDuplicate(hList,availableSeatsForm);
		return isCheckDuplicate;
	}
	/**
	 * delete Hostel Holidays Details
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAvailableSeats(AvailableSeatsForm availableSeatsForm) throws Exception{
		boolean isDeleted = iAvailableSeatsTransaction.deleteAvailableSeats(availableSeatsForm);
		return isDeleted;
	}
	/**
	 * to get hostel holidays details for editing
	 * @param connectionForm
	 * @throws Exception
	 */
	public  void getAvailableSeatsDetails(AvailableSeatsForm availableSeatsForm) throws Exception{
		AvailableSeatsBo availableSeatsBo = iAvailableSeatsTransaction.getAvailableSeatsDetails(availableSeatsForm.getId());
		if(availableSeatsBo!=null && !availableSeatsBo.toString().isEmpty()){
			if(availableSeatsBo.getId()!=0 ){
				availableSeatsForm.setId(availableSeatsBo.getId());
			}
			if(availableSeatsBo.getAcademicYear()!=null && !availableSeatsBo.getAcademicYear().isEmpty()){
				availableSeatsForm.setAcademicYear(availableSeatsBo.getAcademicYear());
			}
			if(availableSeatsBo.getHostelId()!=null){
				availableSeatsForm.setHostelId(String.valueOf(availableSeatsBo.getHostelId().getId()));
			}
			if(availableSeatsBo.getRoomTypeId()!=null){
				availableSeatsForm.setRoomId(String.valueOf(availableSeatsBo.getRoomTypeId().getId()));
			}
			if(availableSeatsBo.getNumOfAvailableSeats()!=null){
				availableSeatsForm.setNumOfAvailableSeats(availableSeatsBo.getNumOfAvailableSeats());
			}
			if(availableSeatsBo.getHostelId()!=null && availableSeatsBo.getRoomTypeId()!=null){
				int hostelId=availableSeatsBo.getHostelId().getId();
				int roomId=availableSeatsBo.getRoomTypeId().getId();
				int totalSeats=iAvailableSeatsTransaction.getTotalRoomsByRoomIdAndHostelId(hostelId,roomId);
				availableSeatsForm.setTotalNumOfSeatsAvail(String.valueOf(totalSeats));
			}
			availableSeatsForm.setFlag(true);
		}
	}
	/**
	 * get roomTypeMap
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getRoomTypeMap(String id)throws Exception {
		Map<Integer, String> roomTypeMap = iAvailableSeatsTransaction.getRoomTypeMap(id);
		roomTypeMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(roomTypeMap);
		return roomTypeMap;
	}
	/**
	 * update the hostel holidays details
	 * @param connectionForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateAvailableSeats(AvailableSeatsForm availableSeatsForm)throws Exception {
		AvailableSeatsBo boList = AvailableSeatsHelper.getInstance().copyEditFormToAvailableSeatsBo(availableSeatsForm);
		boolean isUpdated = iAvailableSeatsTransaction.updateAvailableSeats(boList);
		return isUpdated;
	}
	public int getTotalAvailSeatsByRoomIdAndHostelId(BaseActionForm actionForm) throws Exception{
		int totalSeats=iAvailableSeatsTransaction.getTotalRoomsByRoomIdAndHostelId(Integer.parseInt(actionForm.getHostelId()),Integer.parseInt(actionForm.getRoomId()));
		return totalSeats;
		
	}
	public Map<String,String> getHoursMap()throws Exception{
		Map<String,String> map=new HashMap<String, String>();
		for(int i=0;i<24;i++){
			String hour=String.valueOf(i);
			String hor=null;
			if(hour.length()<2){
				hor="0"+hour;
			}else{
				hor=hour;
			}
			map.put(hor, hor);
		}
		return map=CommonUtil.sortMapByKey(map);
	}
	public Map<String,String> getMinMap()throws Exception{
		Map<String,String> map=new HashMap<String, String>();
		for(int i=0;i<60;i++){
			String hour=String.valueOf(i);
			String hor=null;
			if(hour.length()<2){
				hor="0"+hour;
			}else{
				hor=hour;
			}
			map.put(hor, hor);
		}
		return map=CommonUtil.sortMapByKey(map);
	}
}
