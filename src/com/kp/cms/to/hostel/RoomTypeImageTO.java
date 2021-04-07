package com.kp.cms.to.hostel;

import java.io.Serializable;

public class RoomTypeImageTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte[] image;
	private int id;
	private int countId;
	private int roomTypeId;
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}


	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
}
