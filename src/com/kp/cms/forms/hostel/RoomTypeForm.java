package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeAdditionalTO;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.hostel.FacilityTO;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.RoomTypeFacilityTO;
import com.kp.cms.to.hostel.RoomTypeFeesTO;
import com.kp.cms.to.hostel.RoomTypeImageTO;
import com.kp.cms.to.hostel.RoomTypeTO;

public class RoomTypeForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<HostelTO> hostelList;
	private List<FacilityTO> facilityList;
	private String method;
	private String hostelId;
	private String roomType;
	private String description;
	private String maxOccupants;
	private String roomCategory;
	private FormFile image;
	private List<RoomTypeImageTO> imageList;
	private List<RoomTypeTO> roomTypeList;
	private String roomTypeId;
	private String oldHostelId;
	private String oldRoomType;
	private Map<Integer, RoomTypeFacilityTO> roomTypeFacilityMap;
	private Map<Integer, Integer> roomTypeImageMap;
	private int position;
	private int imageId;
	private List<FeeGroupTO> feeList;
	private List<RoomTypeFeesTO> roomTypeFeesList;
	private int oldCountt;
	private String hostelName;
	private boolean feeSelected;
	private Map<Integer, Integer> inActiveRoomTypeFacilityMap;
	
	public Map<Integer, Integer> getInActiveRoomTypeFacilityMap() {
		return inActiveRoomTypeFacilityMap;
	}
	public void setInActiveRoomTypeFacilityMap(
			Map<Integer, Integer> inActiveRoomTypeFacilityMap) {
		this.inActiveRoomTypeFacilityMap = inActiveRoomTypeFacilityMap;
	}
	public boolean isFeeSelected() {
		return feeSelected;
	}
	public void setFeeSelected(boolean feeSelected) {
		this.feeSelected = feeSelected;
	}
	public String getHostelId() {
		return hostelId;
	}
	public void setHostelId(String hostelId) {
		this.hostelId = hostelId;
	}
	public List<HostelTO> getHostelList() {
		return hostelList;
	}
	public void setHostelList(List<HostelTO> hostelList) {
		this.hostelList = hostelList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<FacilityTO> getFacilityList() {
		return facilityList;
	}
	public void setFacilityList(List<FacilityTO> facilityList) {
		this.facilityList = facilityList;
	}
	public String getRoomType() {
		return roomType;
	}
	public String getDescription() {
		return description;
	}
	public String getMaxOccupants() {
		return maxOccupants;
	}
	public FormFile getImage() {
		return image;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setMaxOccupants(String maxOccupants) {
		this.maxOccupants = maxOccupants;
	}
	public void setImage(FormFile image) {
		this.image = image;
	}
	public List<RoomTypeImageTO> getImageList() {
		return imageList;
	}
	public List<RoomTypeTO> getRoomTypeList() {
		return roomTypeList;
	}
	public void setRoomTypeList(List<RoomTypeTO> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}
	public void setImageList(List<RoomTypeImageTO> imageList) {
		this.imageList = imageList;
	}
	
	
	public String getOldRoomType() {
		return oldRoomType;
	}
	
	public void setOldRoomType(String oldRoomType) {
		this.oldRoomType = oldRoomType;
	}
	
	
	public Map<Integer, RoomTypeFacilityTO> getRoomTypeFacilityMap() {
		return roomTypeFacilityMap;
	}
	public void setRoomTypeFacilityMap(
			Map<Integer, RoomTypeFacilityTO> roomTypeFacilityMap) {
		this.roomTypeFacilityMap = roomTypeFacilityMap;
	}
	public Map<Integer, Integer> getRoomTypeImageMap() {
		return roomTypeImageMap;
	}
	public void setRoomTypeImageMap(Map<Integer, Integer> roomTypeImageMap) {
		this.roomTypeImageMap = roomTypeImageMap;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clear(){
		this.hostelId = null;
		this.roomType = null;
		this.description = null;
		this.maxOccupants = null;
		this.roomCategory= "Student";
		this.image = null;
		this.feeSelected=false;
	}
	
	public List<RoomTypeFeesTO> getRoomTypeFeesList() {
		return roomTypeFeesList;
	}
	public void setRoomTypeFeesList(List<RoomTypeFeesTO> roomTypeFeesList) {
		this.roomTypeFeesList = roomTypeFeesList;
	}
	public List<FeeGroupTO> getFeeList() {
		return feeList;
	}
	public void setFeeList(List<FeeGroupTO> feeList) {
		this.feeList = feeList;
	}
	public String getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getOldHostelId() {
		return oldHostelId;
	}
	public void setOldHostelId(String oldHostelId) {
		this.oldHostelId = oldHostelId;
	}
	public String getRoomCategory() {
		return roomCategory;
	}
	public void setRoomCategory(String roomCategory) {
		this.roomCategory = roomCategory;
	}
	public int getOldCountt() {
		return oldCountt;
	}
	public void setOldCountt(int oldCountt) {
		this.oldCountt = oldCountt;
	}
	public String getHostelName() {
		return hostelName;
	}
	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}


}
