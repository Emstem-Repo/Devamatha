package com.kp.cms.to.examallotment;

import java.io.Serializable;
import java.util.List;

public class RoomAllotmentStatusTo implements Serializable {

	private List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList;
	public List<RoomAllotmentStatusDetailsTo> getAllotmentStatusDetailsToList() {
		return allotmentStatusDetailsToList;
	}

	public void setAllotmentStatusDetailsToList(
			List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList) {
		this.allotmentStatusDetailsToList = allotmentStatusDetailsToList;
	}
	
	
	
	
}
