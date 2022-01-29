package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.hostel.HostelApplicationByAdminTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeTO;
import com.kp.cms.to.hostel.RoomTypeWithAmountTO;

public class HostelApplicationByAdminForm extends BaseActionForm{
	
	private String applicationNo;
	private String registerNo;
	private String staffId;
	private String rollNo;
	//private String hostelId;
	private List<HostelTO> hostelTOList;
	private HostelTO hostelTO;
	private List<RoomTypeTO> roomTypeNameList;
	private String clinicalRemarks;
	private String sicknessRelatedInfo;
	private String roomTypeId;
	private String maxRequisitionNo;
	private String templateDescription;
	private boolean isEnteredByAdmin;
	private HostelApplicationByAdminTO hostelApplicationTO;
	private PersonalDataTO personalDataTO;
	private List<RoomTypeWithAmountTO> roomTypeWithAmountList;
	private String roomTypeSelect;
	private Integer academicYr;
	
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	
	public HostelTO getHostelTO() {
		return hostelTO;
	}
	public void setHostelTO(HostelTO hostelTO) {
		this.hostelTO = hostelTO;
	}
	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}
	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}
	
	public List<RoomTypeTO> getRoomTypeNameList() {
		return roomTypeNameList;
	}
	public void setRoomTypeNameList(List<RoomTypeTO> roomTypeNameList) {
		this.roomTypeNameList = roomTypeNameList;
	}
	public String getClinicalRemarks() {
		return clinicalRemarks;
	}
	public void setClinicalRemarks(String clinicalRemarks) {
		this.clinicalRemarks = clinicalRemarks;
	}
	public String getSicknessRelatedInfo() {
		return sicknessRelatedInfo;
	}
	public void setSicknessRelatedInfo(String sicknessRelatedInfo) {
		this.sicknessRelatedInfo = sicknessRelatedInfo;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getMaxRequisitionNo() {
		return maxRequisitionNo;
	}
	public void setMaxRequisitionNo(String maxRequisitionNo) {
		this.maxRequisitionNo = maxRequisitionNo;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	public boolean isEnteredByAdmin() {
		return isEnteredByAdmin;
	}
	public void setEnteredByAdmin(boolean isEnteredByAdmin) {
		this.isEnteredByAdmin = isEnteredByAdmin;
	}
	public List<RoomTypeWithAmountTO> getRoomTypeWithAmountList() {
		return roomTypeWithAmountList;
	}
	public void setRoomTypeWithAmountList(
			List<RoomTypeWithAmountTO> roomTypeWithAmountList) {
		this.roomTypeWithAmountList = roomTypeWithAmountList;
	}
	
	public HostelApplicationByAdminTO getHostelApplicationTO() {
		return hostelApplicationTO;
	}
	public void setHostelApplicationTO(
			HostelApplicationByAdminTO hostelApplicationTO) {
		this.hostelApplicationTO = hostelApplicationTO;
	}
	public PersonalDataTO getPersonalDataTO() {
		return personalDataTO;
	}
	public void setPersonalDataTO(PersonalDataTO personalDataTO) {
		this.personalDataTO = personalDataTO;
	}
	
	public String getRoomTypeSelect() {
		return roomTypeSelect;
	}
	public void setRoomTypeSelect(String roomTypeSelect) {
		this.roomTypeSelect = roomTypeSelect;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() 
	{
		super.setHostelId(null);
		applicationNo =null;
		registerNo =null;
		staffId =null;
		rollNo =null;
		roomTypeSelect=null;
	}
	public Integer getAcademicYr() {
		return academicYr;
	}
	public void setAcademicYr(Integer academicYr) {
		this.academicYr = academicYr;
	}
	
	
}
