package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.studentfeedback.RoomMasterTo;

public class ExamRoomAvailabilityForm extends BaseActionForm{
	private int id;
	private List<RoomMasterTo> roomMasterTO;
	private Map<Integer,String> workLocationMap;
	private int halfLength;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<RoomMasterTo> getRoomMasterTO() {
		return roomMasterTO;
	}
	public void setRoomMasterTO(List<RoomMasterTo> roomMasterTO) {
		this.roomMasterTO = roomMasterTO;
	}
	public Map<Integer, String> getWorkLocationMap() {
		return workLocationMap;
	}
	public void setWorkLocationMap(Map<Integer, String> workLocationMap) {
		this.workLocationMap = workLocationMap;
	}
	
	public int getHalfLength() {
		return halfLength;
	}
	public void setHalfLength(int halfLength) {
		this.halfLength = halfLength;
	}
	public void reset() {
		super.setWorkLocatId(null);
		this.roomMasterTO  = null;
		this.workLocationMap = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		 ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
}
