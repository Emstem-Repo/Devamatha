package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.HostelReAdmissionTo;

public class HostelReAdmissionForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hostelIdForReAdm;
	private Map<Integer, String> hostelMap;
	private List<HostelReAdmissionTo> reAdmissionToList;
	private Map<Integer, String> roomTypeMap;
	private List<HostelOnlineApplicationTo> updatedStudentList;
	private String hlAdmSelection;

	
	
	public Map<Integer, String> getRoomTypeMap() {
		return roomTypeMap;
	}

	public void setRoomTypeMap(Map<Integer, String> roomTypeMap) {
		this.roomTypeMap = roomTypeMap;
	}

	public List<HostelReAdmissionTo> getReAdmissionToList() {
		return reAdmissionToList;
	}

	public void setReAdmissionToList(List<HostelReAdmissionTo> reAdmissionToList) {
		this.reAdmissionToList = reAdmissionToList;
	}

	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}

	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	
	public String getHostelIdForReAdm() {
		return hostelIdForReAdm;
	}

	public void setHostelIdForReAdm(String hostelIdForReAdm) {
		this.hostelIdForReAdm = hostelIdForReAdm;
	}

	public void resetFields(){
		this.hostelIdForReAdm=null;
		this.hostelMap=null;
		this.reAdmissionToList=null;
		 super.clear3();
		 this.updatedStudentList=null;
		 this.hlAdmSelection="NotSelected";
	}

	public List<HostelOnlineApplicationTo> getUpdatedStudentList() {
		return updatedStudentList;
	}

	public void setUpdatedStudentList(
			List<HostelOnlineApplicationTo> updatedStudentList) {
		this.updatedStudentList = updatedStudentList;
	}

	public String getHlAdmSelection() {
		return hlAdmSelection;
	}

	public void setHlAdmSelection(String hlAdmSelection) {
		this.hlAdmSelection = hlAdmSelection;
	}
	
	

}
