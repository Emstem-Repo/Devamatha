package com.kp.cms.transactions.hostel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlBeds;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.hostel.HlAdmissionBookingWaitingBo;
import com.kp.cms.forms.hostel.HlAdmissionForm;

public interface IHladmissionTransaction {

	List<Object[]> gethlAdmissionList(HlAdmissionForm hlAdmissionForm, String mode) throws Exception;

	boolean duplicateCheck(HlAdmissionForm hlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception;
	
	boolean addhlAdmission(HlAdmissionBo hlAdmissionBo, String mode, HlAdmissionForm hlAdmissionForm) throws Exception;

	List<Object[]> getStudentDetails(HlAdmissionForm hlAdmissionForm)throws Exception;

	Object[] gethlAdmissionById(int id) throws Exception;

	boolean deletehlAdmission(int id, HlAdmissionForm hlAdmissionForm) throws Exception;

	BigDecimal getnumberOfSeat(HlAdmissionForm hlAdmissionForm) throws Exception;

	BigDecimal getoccupaySeats(HlAdmissionForm hlAdmissionForm) throws Exception;

	String getGenderPersonaldata(HlAdmissionForm hlAdmissionForm) throws Exception;

	String getGenderHostel(HlAdmissionForm hlAdmissionForm)  throws Exception;

	List<HlHostel> getHostelDeatils(String gender)  throws Exception;

	boolean duplicateApplNo(HlAdmissionForm hlAdmissionForm,ActionErrors errors) throws Exception;
	
	boolean duplicateCheckWaitingList(HlAdmissionForm hlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception;
	
	boolean addhlAdmissionWaiting(HlAdmissionBookingWaitingBo hlAdmissionBo, String mode) throws Exception;
	
	long getWaitingListPriorityNo(HlAdmissionForm hlAdmissionForm) throws Exception; 
	
	public HlAdmissionBo checkStudentInHlAdmission(HlAdmissionForm admissionForm) throws Exception;
	public HlAdmissionBo getAdmissionId(String hostelId,String regNo,String academicYear)throws Exception;
	public HlRoom getRoomIdAndRoomTypeId(String hostelId,int block,int unit,String roomNo)throws Exception;
	HlBeds getRoomBedId(int roomId,String bedNo,int rTypeId,String hostelId)throws Exception;
	public boolean addUploadRoomDetails(List<HlAdmissionBo> results)throws Exception;
	
    int getStudentInWaitingListWithPriorityNo(HlAdmissionForm hlAdmissionForm,HttpSession session) throws Exception; 
    boolean resetStudentInWaitingList(int waitingId) throws Exception;
    
    List<HlAdmissionBookingWaitingBo> getStudentPriorityNumberInWaitingList(HlAdmissionForm admissionForm) throws Exception;
    void updateWaitingList(HlAdmissionBookingWaitingBo bookingWaitingBo) throws Exception;
    
    List<HlAdmissionBookingWaitingBo> checkStudentsAreThereInWaitingList(HlAdmissionForm hlAdmissionForm) throws Exception;
    public Map<String,Integer> getHlBlocksMap(String hostelId)throws Exception;
    public Map<String,Map<String,Integer>> getHlUnitsMap(String hostelId)throws Exception;

	HlAdmissionBo getAdmissionDetailsForOrint(int admId) throws Exception;

	BigDecimal getNumberOfSeatsAvailable(String hostel, String roomtype,
			String year, HttpServletRequest request) throws Exception;
    
}
