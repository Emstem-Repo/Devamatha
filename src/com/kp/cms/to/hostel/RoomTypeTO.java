package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.List;

import com.kp.cms.to.admin.RoomTO;

public class RoomTypeTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private HostelTO hostelTO;
	private List<RoomTypeFacilityTO> facilityList;
	private List<RoomTypeImageTO> imageList;
	private List<RoomTO> roomTOs;
	private int maxCount;
	private String roomCategory;
	private int totalVacant;
	private int totalReserve;
	private int totalPartial;
	private int totalOccupied;
	
	public HostelTO getHostelTO() {
		return hostelTO;
	}
	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RoomTypeFacilityTO> getFacilityList() {
		return facilityList;
	}
	public List<RoomTypeImageTO> getImageList() {
		return imageList;
	}
	public void setImageList(List<RoomTypeImageTO> imageList) {
		this.imageList = imageList;
	}
	public void setFacilityList(List<RoomTypeFacilityTO> facilityList) {
		this.facilityList = facilityList;
	}
	public List<RoomTO> getRoomTOs() {
		return roomTOs;
	}
	public void setRoomTOs(List<RoomTO> roomTOs) {
		this.roomTOs = roomTOs;
	}
	public int getMaxCount() {
		return maxCount;
	}
	public void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	public int getTotalVacant() {
		return totalVacant;
	}
	public void setTotalVacant(int totalVacant) {
		this.totalVacant = totalVacant;
	}
	public int getTotalReserve() {
		return totalReserve;
	}
	public void setTotalReserve(int totalReserve) {
		this.totalReserve = totalReserve;
	}
	public int getTotalPartial() {
		return totalPartial;
	}
	public void setTotalPartial(int totalPartial) {
		this.totalPartial = totalPartial;
	}
	public int getTotalOccupied() {
		return totalOccupied;
	}
	public void setTotalOccupied(int totalOccupied) {
		this.totalOccupied = totalOccupied;
	}
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
}
