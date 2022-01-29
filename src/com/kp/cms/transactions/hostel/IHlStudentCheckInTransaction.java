package com.kp.cms.transactions.hostel;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.bo.admin.HlFacility;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.hostel.HlStudentFacilityAllotted;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.forms.hostel.HlStudentChInEditForm;
import com.kp.cms.to.hostel.HostelTO;

public interface IHlStudentCheckInTransaction {

	String getGenderPersonaldata(HlStudentChInEditForm hlAdmissionForm) throws Exception;

	String getGenderHostel(HlStudentChInEditForm hlAdmissionForm)  throws Exception;

	List<HlHostel> getHostelDeatils(String gender)  throws Exception;
	
	List<Object[]> getStudentDetails(HlStudentChInEditForm hlAdmissionForm)throws Exception;
	
	List<HlAdmissionBo> gethlAdmissionList(HlStudentChInEditForm hlAdmissionForm, String mode) throws Exception;

	boolean duplicateCheck(HlStudentChInEditForm hlAdmissionForm,ActionErrors errors, HttpSession session) throws Exception;
	
	boolean addhlAdmission(HlAdmissionBo hlAdmissionBo, String mode) throws Exception;

	Object[] gethlAdmissionById(int id) throws Exception;

	boolean deletehlAdmission(int id, HlStudentChInEditForm hlAdmissionForm) throws Exception;

	BigDecimal getnumberOfSeat(HlStudentChInEditForm hlAdmissionForm) throws Exception;

	BigDecimal getoccupaySeats(HlStudentChInEditForm hlAdmissionForm) throws Exception;

	boolean duplicateApplNo(HlStudentChInEditForm hlAdmissionForm,ActionErrors errors) throws Exception;
	
	public List<Object[]> getStudentNameClass(String regNo, String applNo, String academicYear, String hostelApplNo ,HttpServletRequest request) throws Exception; 

	public List<HlFacility> getAllFacility()throws Exception;
	
	public List<HlStudentFacilityAllotted> getHlStudentFacilityAllotted(String HlAdmissionId) throws Exception;
	
	public List<HlRoomTypeFacility> getHlRoomTypeFacility(String RoomTypeName) throws Exception;

	Map<Integer, String> getRoomsAvailable(int hstlName, int roomType, String academicYear, int block, int unit, String floor) throws Exception;

	HlAdmissionBo gethlAdmission(HlStudentChInEditForm hlAdmissionForm, String mode) throws Exception;

	List<HostelTO> getHostelDetails() throws Exception;

	Map<Integer, String> getRoomTypeByHostelBYstudent(int id) throws Exception;

	Map<Integer, String> getBlocks(String hostelId) throws Exception;

	Map<Integer, String> getUnits(String hostelId) throws Exception;

	Map<Integer, String> getFloorsByHostel(String unitId) throws Exception;

	List<Object[]> getRoomsByBlock(String hostelId, String block,
			String unit, String floorNo, String year, String roomTypeName, String studentId) throws Exception;
}
