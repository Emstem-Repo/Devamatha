package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.HostelWaitingListViewTo;

import java.util.Map;
import org.apache.struts.upload.FormFile;

public class HlAdmissionForm extends BaseActionForm{
    
	private int id;
	private String studentId;
	private String studentName;
	private String regNo;
	private String applNo;
	private String courseId;
	private String courseName;
	private String hostelId;
	private String hostelName;
	private String hostelTempName;
	private int roomTypeId;
	private String roomTypeName;
	private String roomTypeTempName;
	private String year;
	private List<HlAdmissionTo> hlAdmissionList;
	List<HostelTO> hostelList;
	private String cancelReason;
	private String print;
	private String checkRegNo;
	private String admittedDate;
	private String roomId;
	private String roomName;
	private String bedId;
	private String bedNo;
	private String seatAvailable;
	private String hostelApplNo;
	private String checkAdmission;
	private String gender;
	private String hlApplicationNo;
	private String tempSeatAvailable;
	private String tempStudentName;
	private String tempGender;
	private String isCheckIn;
	private String checkedInDate;
	private String biometricId;
	private String watingPriorityNo;
	private List<HostelWaitingListViewTo> hlWaitingListViewList;
	private Map<String, String> roomAndAvailableSeatsMap;
	private String editCheck;
	private String tRegNo;
	private String tApplNo;
	private HlAdmissionTo printData;
	private String admissionType;
	
	
	
	public void reset() {
		studentId=null;
		studentName=null;
		courseId=null;
		courseName=null;
		hostelId=null;
		hostelName=null;
		roomTypeId=0;
		roomTypeName=null;
		regNo=null;
		applNo=null;
		year=null;
		hostelTempName=null;
		roomTypeTempName=null;
		seatAvailable=null;
		gender=null;
		hlApplicationNo=null;
		tempSeatAvailable=null;
		tempStudentName=null;
		tempGender=null;
		isCheckIn="false";
		checkedInDate=null;
		biometricId=null;
		roomId=null;
		roomName=null;
		bedId=null;
		bedNo=null;
		admittedDate=null;
		editCheck=null;
		tRegNo=null;
		tApplNo=null;
		
	}
	public void reset1() {
		id=0;
		studentId=null;
		studentName=null;
		courseId=null;
		courseName=null;
		hostelId=null;
		hostelName=null;
		roomTypeId=0;
		roomTypeName=null;
		regNo=null;
		applNo=null;
		hlAdmissionList=null;
		year=null;
		hostelTempName=null;
		roomTypeTempName=null;
		seatAvailable=null;
		hostelApplNo=null;
		gender=null;
		hlApplicationNo=null;
		tempSeatAvailable=null;
		tempStudentName=null;
		tempGender=null;
		isCheckIn="false";
		checkedInDate=null;
		biometricId=null;
		roomId=null;
		roomName=null;
		bedId=null;
		bedNo=null;
		watingPriorityNo=null;
		admittedDate=null;
		editCheck=null;
		tRegNo=null;
		tApplNo=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request){
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
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
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}
	public int getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getRoomTypeName() {
		return roomTypeName;
	}
	public void setRoomTypeName(String roomTypeName) {
		this.roomTypeName = roomTypeName;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public List<HlAdmissionTo> getHlAdmissionList() {
		return hlAdmissionList;
	}
	public void setHlAdmissionList(List<HlAdmissionTo> hlAdmissionList) {
		this.hlAdmissionList = hlAdmissionList;
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
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public String getCancelReason() {
		return cancelReason;
	}
	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
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
	public String getPrint() {
		return print;
	}
	public void setPrint(String print) {
		this.print = print;
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
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getBedId() {
		return bedId;
	}
	public void setBedId(String bedId) {
		this.bedId = bedId;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getSeatAvailable() {
		return seatAvailable;
	}
	public void setSeatAvailable(String seatAvailable) {
		this.seatAvailable = seatAvailable;
	}
	public String getHostelApplNo() {
		return hostelApplNo;
	}
	public void setHostelApplNo(String hostelApplNo) {
		this.hostelApplNo = hostelApplNo;
	}
	public String getCheckAdmission() {
		return checkAdmission;
	}
	public void setCheckAdmission(String checkAdmission) {
		this.checkAdmission = checkAdmission;
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
	public String getIsCheckIn() {
		return isCheckIn;
	}
	public void setIsCheckIn(String isCheckIn) {
		this.isCheckIn = isCheckIn;
	}
	public String getCheckedInDate() {
		return checkedInDate;
	}
	public void setCheckedInDate(String checkedInDate) {
		this.checkedInDate = checkedInDate;
	}
	public String getBiometricId() {
		return biometricId;
	}
	public void setBiometricId(String biometricId) {
		this.biometricId = biometricId;
	}
	public String getWatingPriorityNo() {
		return watingPriorityNo;
	}
	public void setWatingPriorityNo(String watingPriorityNo) {
		this.watingPriorityNo = watingPriorityNo;
	}
	private FormFile theFile;
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	Map<String,String> hostelMap;
	public Map<String, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<String, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public void resetValues(){
		this.hostelId=null;
		this.theFile=null;
	}
	public List<HostelWaitingListViewTo> getHlWaitingListViewList() {
		return hlWaitingListViewList;
	}
	public void setHlWaitingListViewList(
			List<HostelWaitingListViewTo> hlWaitingListViewList) {
		this.hlWaitingListViewList = hlWaitingListViewList;
	}
	public Map<String, String> getRoomAndAvailableSeatsMap() {
		return roomAndAvailableSeatsMap;
	}
	public void setRoomAndAvailableSeatsMap(
			Map<String, String> roomAndAvailableSeatsMap) {
		this.roomAndAvailableSeatsMap = roomAndAvailableSeatsMap;
	}
	public String getEditCheck() {
		return editCheck;
	}
	public void setEditCheck(String editCheck) {
		this.editCheck = editCheck;
	}
	public String gettRegNo() {
		return tRegNo;
	}
	public void settRegNo(String tRegNo) {
		this.tRegNo = tRegNo;
	}
	public String gettApplNo() {
		return tApplNo;
	}
	public void settApplNo(String tApplNo) {
		this.tApplNo = tApplNo;
	}
	public HlAdmissionTo getPrintData() {
		return printData;
	}
	public void setPrintData(HlAdmissionTo printData) {
		this.printData = printData;
	}
	public String getAdmissionType() {
		return admissionType;
	}
	public void setAdmissionType(String admissionType) {
		this.admissionType = admissionType;
	}
	
}
