package com.kp.cms.to.hostel;

import java.io.Serializable;
import java.util.List;

public class HlFloorTo implements Serializable {
	private int id;
	private String name;
	List<RoomTypeTO> roomTypeTOs;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<RoomTypeTO> getRoomTypeTOs() {
		return roomTypeTOs;
	}
	public void setRoomTypeTOs(List<RoomTypeTO> roomTypeTOs) {
		this.roomTypeTOs = roomTypeTOs;
	}
}
