package com.kp.cms.to.hostel;

import java.io.Serializable;

public class RoomTypeFacilityTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private RoomTypeTO roomTypeTO;
	private FacilityTO facilityTO;
	public int getId() {
		return id;
	}
	public RoomTypeTO getRoomTypeTO() {
		return roomTypeTO;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setRoomTypeTO(RoomTypeTO roomTypeTO) {
		this.roomTypeTO = roomTypeTO;
	}
	public FacilityTO getFacilityTO() {
		return facilityTO;
	}
	public void setFacilityTO(FacilityTO facilityTO) {
		this.facilityTO = facilityTO;
	}

}
