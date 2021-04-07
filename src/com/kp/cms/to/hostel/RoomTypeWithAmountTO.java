package com.kp.cms.to.hostel;

import java.util.List;

public class RoomTypeWithAmountTO {
	private String roomType;
	private List<HlApplicationFeeTO> amountList;
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public List<HlApplicationFeeTO> getAmountList() {
		return amountList;
	}
	public void setAmountList(List<HlApplicationFeeTO> amountList) {
		this.amountList = amountList;
	}
	
}
