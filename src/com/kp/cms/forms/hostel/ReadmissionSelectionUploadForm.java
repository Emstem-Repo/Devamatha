package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.HostelTO;

public class ReadmissionSelectionUploadForm extends BaseActionForm {
	private FormFile thefile;
	private List<HostelTO> hostelList;
	private String academicYear1;
	private String hostelId;
	private Boolean dupliRegNum;
	private Boolean isRoomTypeNotMaching;
	private String dupRegNumMsg;
	private String dupRegNum;
	private String roomTypeNotMachingRegNum;
	private String roomTypeNotMachingMsg;
	private Boolean isRegNosUpload;
	private String notUploadRegNos;
	private String notUploadRegNosMsg;
	private List<HostelOnlineApplicationTo> selectedStudentList;
	
	
	public void resetFields(){
		this.thefile=null;
		this.academicYear1=null;
		this.hostelId=null;
		this.dupliRegNum=false;
		this.isRoomTypeNotMaching=false;
		this.dupRegNumMsg=null;
		this.dupliRegNum=null;
		this.roomTypeNotMachingRegNum=null;
		this.roomTypeNotMachingMsg=null;
		this.isRegNosUpload=false;
		this.notUploadRegNos=null;
		this.notUploadRegNosMsg=null;
		this.selectedStudentList=null;
		
	}
	public void reset(){
		this.dupliRegNum=false;
		this.isRoomTypeNotMaching=false;
		this.dupRegNumMsg=null;
		this.dupliRegNum=null;
		this.roomTypeNotMachingRegNum=null;
		this.roomTypeNotMachingMsg=null;
		this.isRegNosUpload=false;
		this.notUploadRegNos=null;
		this.notUploadRegNosMsg=null;
		this.selectedStudentList=null;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public FormFile getThefile() {
		return thefile;
	}


	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}


	public List<HostelTO> getHostelList() {
		return hostelList;
	}


	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}


	public String getAcademicYear1() {
		return academicYear1;
	}


	public void setAcademicYear1(String academicYear1) {
		this.academicYear1 = academicYear1;
	}


	public String getHostelId() {
		return hostelId;
	}


	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}


	public Boolean getDupliRegNum() {
		return dupliRegNum;
	}


	public void setDupliRegNum(Boolean dupliRegNum) {
		this.dupliRegNum = dupliRegNum;
	}


	public Boolean getIsRoomTypeNotMaching() {
		return isRoomTypeNotMaching;
	}


	public void setIsRoomTypeNotMaching(Boolean isRoomTypeNotMaching) {
		this.isRoomTypeNotMaching = isRoomTypeNotMaching;
	}


	public String getDupRegNumMsg() {
		return dupRegNumMsg;
	}


	public void setDupRegNumMsg(String dupRegNumMsg) {
		this.dupRegNumMsg = dupRegNumMsg;
	}


	public String getDupRegNum() {
		return dupRegNum;
	}


	public void setDupRegNum(String dupRegNum) {
		this.dupRegNum = dupRegNum;
	}


	public String getRoomTypeNotMachingRegNum() {
		return roomTypeNotMachingRegNum;
	}


	public void setRoomTypeNotMachingRegNum(String roomTypeNotMachingRegNum) {
		this.roomTypeNotMachingRegNum = roomTypeNotMachingRegNum;
	}


	public String getRoomTypeNotMachingMsg() {
		return roomTypeNotMachingMsg;
	}


	public void setRoomTypeNotMachingMsg(String roomTypeNotMachingMsg) {
		this.roomTypeNotMachingMsg = roomTypeNotMachingMsg;
	}
	public Boolean getIsRegNosUpload() {
		return isRegNosUpload;
	}
	public void setIsRegNosUpload(Boolean isRegNosUpload) {
		this.isRegNosUpload = isRegNosUpload;
	}
	public String getNotUploadRegNos() {
		return notUploadRegNos;
	}
	public void setNotUploadRegNos(String notUploadRegNos) {
		this.notUploadRegNos = notUploadRegNos;
	}
	public String getNotUploadRegNosMsg() {
		return notUploadRegNosMsg;
	}
	public void setNotUploadRegNosMsg(String notUploadRegNosMsg) {
		this.notUploadRegNosMsg = notUploadRegNosMsg;
	}
	public List<HostelOnlineApplicationTo> getSelectedStudentList() {
		return selectedStudentList;
	}
	public void setSelectedStudentList(
			List<HostelOnlineApplicationTo> selectedStudentList) {
		this.selectedStudentList = selectedStudentList;
	}
}
