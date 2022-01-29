package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlFees;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomType;
import com.kp.cms.bo.admin.HlStatus;

public interface IHostelApplicationTransaction {

	/**
	 * Used to get roomType Names Based on HostelID
	 */
	public List<HlRoomType>getRoomTypesonHostelId(int hostelId)throws Exception;
	
	/**
	 * Used to get AdmApplnID based on studentID
	 */
	public int getAdmApplnIDOnStudentID(int studentID)throws Exception;
	
	/**
	 * Used to get the applied status Id
	 */
	public int getAppliedStatusId()throws Exception;
	/**
	 * Used to get the cheked-in status Id
	 */
	public HlStatus getCheckedInStatus()throws Exception;
	/**
	 * Used to get Max Requisition No
	 */
	public int getMaxRequisitionNo()throws Exception;
	/**
	 * Used for Save
	 */
	public boolean submitApplicationStudentDetails(HlApplicationForm hlApplicationFormBO)throws Exception;
	
	/**
	 * Used to get Terms & Condition for the hostel
	 */
	public HlHostel getTermsConditionforHostel(int hostelId)throws Exception;
	
	/**
	 * Used to view hostel details based on hostelId
	 */
	public HlHostel getHostelRoomTypesByHostelID(int hostelId)throws Exception;
	public boolean isDuplicateApplication(String studentID) throws Exception;
	public List<HlFees> getRoomTypewiseHostelFeesonHostelId(int hostelId) throws Exception;
}
