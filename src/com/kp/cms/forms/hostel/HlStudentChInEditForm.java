package com.kp.cms.forms.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.AssignRoomMasterTo;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HlRoomTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;

public class HlStudentChInEditForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String studentId;
	private String studentName;
	private String studentClass;
	private String regNo;
	private String applNo;
	private String courseId;
	private String courseName;
	private String floorNo;
	private String noOfRooms;
	private List<HlRoomTO> roomList;
	/*private String roomTypeId;*/
	private String hostelName;
	private List<RoomTypeTO> roomTypeList;
	private String roomNo;
	private AssignRoomMasterTo roomMaster;
	private String searchedFloorNumber;
	private String searchedHostelId;
	private String searchedHostelName;
	private String createdBy;
	private String createdDate;
	private Map<Integer, String> hostelMap;
	private Map<Integer, String> roomTypeMap;
	private Map<Integer, String> roomMap; 
	private Map<Integer, String> bedMap;
	private Map<Integer, String> unitMap;
	private Map<Integer, String> blockMap;
	private Map<Integer, String> floorMap;
	private String unit;
	private String block;
	/*private String unitId;
	private String blockId;*/
	private List<HostelTO> hostelList = new ArrayList<HostelTO>();
	private String unitName;
	private String blockName;
	//private Map<Integer, Integer> roomTypeMap;
	private String year;
	private String checkRegNo;
	private String admittedDate;
	//private String roomId;
	private String roomName;
	//private String bedId;
	private String bedNo;
	private String hostelApplNo;
	private String gender;
	private String hlApplicationNo;
	private String tempSeatAvailable;
	private String tempStudentName;
	private String tempGender;
	private String roomTypeName;
	private String checkAdmission;
	private List<HlAdmissionTo> hlAdmissionList;
	private String print;
	private String biometricId;
	private String isCheckIn;
	private String checkInDate;
	private String tempStudentClass;
	private String hostelAdmissionId;
	private String hostelTempName;
	private String roomTypeTempName;
	private String hostelId;
	private String dataAvailable;
	List<FacilityTO> facilityList;
	
	
	public List<FacilityTO> getFacilityList() {
		return facilityList;
	}
	public void setFacilityList(List<FacilityTO> facilityList) {
		this.facilityList = facilityList;
	}
	public String getDataAvailable() {
		return dataAvailable;
	}
	public void setDataAvailable(String dataAvailable) {
		this.dataAvailable = dataAvailable;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getHostelTempName() {
		return hostelTempName;
	}
	public void setHostelTempName(String hostelTempName) {
		this.hostelTempName = hostelTempName;
	}
	public String getRoomTypeTempName() {
		return roomTypeTempName;
	}
	public void setRoomTypeTempName(String roomTypeTempName) {
		this.roomTypeTempName = roomTypeTempName;
	}
	public String getHostelAdmissionId() {
		return hostelAdmissionId;
	}
	public void setHostelAdmissionId(String hostelAdmissionId) {
		this.hostelAdmissionId = hostelAdmissionId;
	}
	public String getTempStudentClass() {
		return tempStudentClass;
	}
	public void setTempStudentClass(String tempStudentClass) {
		this.tempStudentClass = tempStudentClass;
	}
	public String getBiometricId() {
		return biometricId;
	}
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}
	public String getIsCheckIn() {
		return isCheckIn;
	}
	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
	}
	public List<HlAdmissionTo> getHlAdmissionList() {
		return hlAdmissionList;
	}
	public void setHlAdmissionList(List<HlAdmissionTo> hlAdmissionList) {
		this.hlAdmissionList = hlAdmissionList;
	}
	public String getCheckAdmission() {
		return checkAdmission;
	}
	public void setCheckAdmission(String checkAdmission) {
		this.checkAdmission = checkAdmission;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getApplNo() {
		return applNo;
	}
	public void setApplNo(String applNo) {
		this.applNo = applNo;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getNoOfRooms() {
		return noOfRooms;
	}
	public void setNoOfRooms(String noOfRooms) {
		this.noOfRooms = noOfRooms;
	}
	public List<HlRoomTO> getRoomList() {
		return roomList;
	}
	public void setRoomList(List<HlRoomTO> roomList) {
		this.roomList = roomList;
	}
	/*public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}*/
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public List<RoomTypeTO> getRoomTypeList() {
		return roomTypeList;
	}
	public void setRoomTypeList(List<RoomTypeTO> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public AssignRoomMasterTo getRoomMaster() {
		return roomMaster;
	}
	public void setRoomMaster(AssignRoomMasterTo roomMaster) {
		this.roomMaster = roomMaster;
	}
	public String getSearchedFloorNumber() {
		return searchedFloorNumber;
	}
	public void setSearchedFloorNumber(String searchedFloorNumber) {
		this.searchedFloorNumber = searchedFloorNumber;
	}
	public String getSearchedHostelId() {
		return searchedHostelId;
	}
	public void setSearchedHostelId(String searchedHostelId) {
		this.searchedHostelId = searchedHostelId;
	}
	public String getSearchedHostelName() {
		return searchedHostelName;
	}
	public void setSearchedHostelName(String searchedHostelName) {
		this.searchedHostelName = searchedHostelName;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public Map<Integer, String> getFloorMap() {
		return floorMap;
	}
	public void setFloorMap(Map<Integer, String> floorMap) {
		this.floorMap = floorMap;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	/*public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}*/
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public String getBlockName() {
		return blockName;
	}
	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getCheckRegNo() {
		return checkRegNo;
	}
	public void setCheckRegNo(String checkRegNo) {
		this.checkRegNo = checkRegNo;
	}
	public String getAdmittedDate() {
		return admittedDate;
	}
	public void setAdmittedDate(String admittedDate) {
		this.admittedDate = admittedDate;
	}
	
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getHostelApplNo() {
		return hostelApplNo;
	}
	public void setHostelApplNo(String hostelApplNo) {
		this.hostelApplNo = hostelApplNo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHlApplicationNo() {
		return hlApplicationNo;
	}
	public void setHlApplicationNo(String hlApplicationNo) {
		this.hlApplicationNo = hlApplicationNo;
	}
	public String getTempSeatAvailable() {
		return tempSeatAvailable;
	}
	public void setTempSeatAvailable(String tempSeatAvailable) {
		this.tempSeatAvailable = tempSeatAvailable;
	}
	public String getTempStudentName() {
		return tempStudentName;
	}
	public void setTempStudentName(String tempStudentName) {
		this.tempStudentName = tempStudentName;
	}
	public String getTempGender() {
		return tempGender;
	}
	public void setTempGender(String tempGender) {
		this.tempGender = tempGender;
	}
	public String getStudentClass() {
		return studentClass;
	}
	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}
	
	
	
	public void reset() {
		id=0;
		studentId=null;
		studentName=null;
		courseId=null;
		courseName=null;
		hostelId=null;
		hostelName=null;
	//	roomTypeId=null;
		roomTypeName=null;
		regNo=null;
		applNo=null;
		year=null;
		hostelTempName=null;
		roomTypeTempName=null;
		gender=null;
		hlApplicationNo=null;
		tempSeatAvailable=null;
		tempStudentName=null;
		tempGender=null;
		isCheckIn="false";
		checkInDate=null;
		biometricId=null;
		roomName=null;
		bedNo=null;
		hostelAdmissionId=null;
		hostelApplNo=null;
		applNo=null;
		dataAvailable="false";
		hlApplicationNo=null;
		roomMap =null;
		
	}
	public void reset1() {
		id=0;
		studentId=null;
		studentName=null;
		courseId=null;
		courseName=null;
		hostelId=null;
		hostelName=null;
	//	roomTypeId=null;
		roomTypeName=null;
		regNo=null;
		applNo=null;
		hlAdmissionList=null;
		year=null;
		hostelTempName=null;
		roomTypeTempName=null;
		hostelApplNo=null;
		gender=null;
		hlApplicationNo=null;
		tempSeatAvailable=null;
		tempStudentName=null;
		tempGender=null;
		isCheckIn="false";
		checkInDate=null;
		biometricId=null;
		roomName=null;
		bedNo=null;
		hlApplicationNo=null;
	}
	public Map<Integer, String> getRoomTypeMap() {
		return roomTypeMap;
	}
	public void setRoomTypeMap(Map<Integer, String> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}
	public Map<Integer, String> getRoomMap() {
		return roomMap;
	}
	public void setRoomMap(Map<Integer, String> roomMap) {
		this.roomMap = roomMap;
	}
	public Map<Integer, String> getBedMap() {
		return bedMap;
	}
	public void setBedMap(Map<Integer, String> bedMap) {
		this.bedMap = bedMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request){
		String formName = request.getParameter("formName");
		 ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	

}
