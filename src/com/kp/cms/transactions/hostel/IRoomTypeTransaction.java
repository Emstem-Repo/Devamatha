package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.FeeAdditional;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.employee.EventScheduleStudentAttendanceBo;
import com.kp.cms.bo.hostel.HlRoomTypeFees;
import com.kp.cms.bo.phd.PhdGuideDetailsBO;
import com.kp.cms.forms.admin.CertificateDetailsForm;
import com.kp.cms.forms.hostel.RoomTypeForm;

public interface IRoomTypeTransaction {
	/**
	 * Used to get all facilities 
	 */
	public List<HlFacility> getAllFacility()throws Exception;
	
	/**
	 * Used for inserting roomType
	 */
	public boolean submitRoomType(HlRoomType roomType)throws Exception;
	/**
	 * Used to get all room type.
	 * Displays in UI
	 */
	public List<HlRoomType> getAllRoomType()throws Exception;
	/**
	 * Used to delete a roomtype
	 */
	public boolean deleteRoomType(int roomTypeId, String userId)throws Exception;
	
	/**
	 * Used to check duplicate on hostel Id and name
	 */
	public HlRoomType getRoomTypeOnHostelName(int hostelId, String roomType)throws Exception;
	
	/**
	 * Used to reactivate roomtype
	 */
	public boolean reactivateRoomType(int hostelId, String roomType, String userId)throws Exception;
	/**
	 * Used when edit button is clicked
	 */
	public HlRoomType getDetailByRoomTypeId(int roomTypeId)throws Exception;
	
	/**
	 * Works when updating a record
	 */
	public boolean updateRoomType(HlRoomType roomType)throws Exception;
	
	/**
	 * Used to delete roomtypeImage in update mode
	 * 
	 */
	public boolean deleteRoomTypeImage(int roomTypeImageId)throws Exception;

	public List<FeeGroup> getAssignFeeForRoom() throws Exception;

	public boolean addSynopsisDefense(List<HlRoomTypeFees> roomFees) throws Exception;

	public List<HlRoomTypeFees> setAssignRoomFees(RoomTypeForm typeForm) throws Exception;
	
     public List<FeeGroup> getFee() throws Exception;
	
	public Map<Integer,Integer> getAssignFee(RoomTypeForm typeForm) throws Exception;
	
	public HlRoomTypeFacility getInActiveRoomTypeFacility(int id)throws Exception;
}
