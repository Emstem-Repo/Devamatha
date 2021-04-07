package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HostelHolidaysBo;
import com.kp.cms.bo.hostel.AvailableSeatsBo;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.HolidaysForm;

public interface IAvailableSeatsTransaction {
	Map<String,String> getHostelMap()throws Exception;
	public boolean saveAvailableSeats(AvailableSeatsBo boList)throws Exception;
	public List<AvailableSeatsBo> getAvailableSeatsList()throws Exception;
	public boolean deleteAvailableSeats(AvailableSeatsForm availableSeatsForm)throws Exception;
	public AvailableSeatsBo getAvailableSeatsDetails(int id)throws Exception;
	Map<Integer,String> getRoomTypeMap(String id)throws Exception;
	public boolean updateAvailableSeats(AvailableSeatsBo availableSeatsBo)throws Exception;
	public int getTotalRoomsByRoomIdAndHostelId(int hostelId,int roomId)throws Exception;
}
