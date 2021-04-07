package com.kp.cms.helpers.exam;

import java.util.Date;

import com.kp.cms.bo.exam.ExamRoomTypeBO;

public class ExamRoomTypeHelper {

	public ExamRoomTypeBO convertERTTOtoBO(int eRTId, String room_Type,
			String room_Desc, String createdBy, Date createdDate,
			Date lastModifiedDate, String modifiedBy, boolean isActive) throws Exception {

		ExamRoomTypeBO objERTBO = new ExamRoomTypeBO(eRTId, room_Type,
				room_Desc, createdBy, createdDate, lastModifiedDate,
				modifiedBy, true);

		return objERTBO;
	}	
}
