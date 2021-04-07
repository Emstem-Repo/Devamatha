package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;

public interface IHostelWaitingListViewTransaction {
	
	List<HlAdmissionBookingWaitingBo> SearchStudentsInHostel(HlAdmissionForm hlAdmissionForm) throws Exception;
	
	String getHostelNameById(int hostelId) throws Exception;
	
	String getRoomTypeName(int roomTypeId) throws Exception;
	
	boolean intimatedToStudent(int id) throws Exception;

}
