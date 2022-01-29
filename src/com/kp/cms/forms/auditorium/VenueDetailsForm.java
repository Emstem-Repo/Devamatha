package com.kp.cms.forms.auditorium;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.auditorium.VenueDetailsTO;

public class VenueDetailsForm extends BaseActionForm{
     private int id;
     private String blockId;
     private String venueName;
     private String roomNo;
     private Map<Integer,String> blockMap;
     private List<VenueDetailsTO> venuesTO;
     private String origBlockId;
     private String origVenueName;
     private String origRoomNo;
     private String emailId;
     private String origEmailId;
     private String facilityAvailable;
     private String origFacilityAvailable;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public void reset(){
		this.id = 0;
		this.blockId = null;
		this.roomNo = null;
		this.venueName = null;
		this.emailId=null;
		this.facilityAvailable=null;
	}
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getBlockId() {
		return blockId;
	}
	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}
	public List<VenueDetailsTO> getVenuesTO() {
		return venuesTO;
	}
	public void setVenuesTO(List<VenueDetailsTO> venuesTO) {
		this.venuesTO = venuesTO;
	}
	public String getOrigBlockId() {
		return origBlockId;
	}
	public void setOrigBlockId(String origBlockId) {
		this.origBlockId = origBlockId;
	}
	public String getOrigVenueName() {
		return origVenueName;
	}
	public void setOrigVenueName(String origVenueName) {
		this.origVenueName = origVenueName;
	}
	public String getOrigRoomNo() {
		return origRoomNo;
	}
	public void setOrigRoomNo(String origRoomNo) {
		this.origRoomNo = origRoomNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getOrigEmailId() {
		return origEmailId;
	}
	public void setOrigEmailId(String origEmailId) {
		this.origEmailId = origEmailId;
	}
	public String getFacilityAvailable() {
		return facilityAvailable;
	}
	public void setFacilityAvailable(String facilityAvailable) {
		this.facilityAvailable = facilityAvailable;
	}
	public String getOrigFacilityAvailable() {
		return origFacilityAvailable;
	}
	public void setOrigFacilityAvailable(String origFacilityAvailable) {
		this.origFacilityAvailable = origFacilityAvailable;
	}

}
