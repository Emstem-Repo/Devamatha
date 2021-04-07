package com.kp.cms.forms.exam;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamRoomMasterTO;

public class ExamRoomMasterForm extends BaseActionForm {
	/**
	 * Used in Exam Module
	 */

	private int id;
	private int roomTypeId;
	private String roomType;
	private String roomNo;
	private int roomCapacity;
	private String comments;
	private int roomCapacityForExams;
	private int roomCapacityForInternalExams;
	private String floorNo;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private List<ExamRoomTypeBO> roomTypeList;
	private List<ExamRoomMasterTO> roomMasterList;
	private String origRoomNo;
	private String origERTName;
	private String returnValue;
	private String blockNo;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		this.comments = "";
		this.floorNo = "";
		this.roomCapacity = 0;
		this.roomCapacityForExams = 0;
		this.roomCapacityForInternalExams = 0;
		this.roomNo = "";
		this.roomTypeId = 0;
		this.blockNo = "";
	}

	private int getInt(String str) {

		int i = 0;
		try {
			i = Integer.parseInt(str);
		} catch (NumberFormatException e) {

		}
		return i;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoomTypeId() {
		return roomTypeId;
	}

	public void setRoomTypeId(int roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public void setRoomCapacity(String roomCapacity) {
		this.roomCapacity = getInt(roomCapacity);
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getRoomCapacityForExams() {
		return roomCapacityForExams;
	}

	public void setRoomCapacityForExams(int roomCapacityForExams) {
		this.roomCapacityForExams = roomCapacityForExams;
	}

	public void setRoomCapacityForExams(String roomCapacityForExams) {
		this.roomCapacityForExams = getInt(roomCapacityForExams);
	}

	public int getRoomCapacityForInternalExams() {
		return roomCapacityForInternalExams;
	}

	public void setRoomCapacityForInternalExams(int roomCapacityForInternalExams) {
		this.roomCapacityForInternalExams = roomCapacityForInternalExams;
	}

	public void setRoomCapacityForInternalExams(
			String roomCapacityForInternalExams) {
		this.roomCapacityForInternalExams = getInt(roomCapacityForInternalExams);
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public List<ExamRoomTypeBO> getRoomTypeList() {
		return roomTypeList;
	}

	public void setRoomTypeList(List<ExamRoomTypeBO> roomTypeList) {
		this.roomTypeList = roomTypeList;
	}

	public List<ExamRoomMasterTO> getRoomMasterList() {
		return roomMasterList;
	}

	public void setRoomMasterList(List<ExamRoomMasterTO> roomMasterList) {
		this.roomMasterList = roomMasterList;
	}

	public String getOrigRoomNo() {
		return origRoomNo;
	}

	public void setOrigRoomNo(String origRoomNo) {
		this.origRoomNo = origRoomNo;
	}

	public String getOrigERTName() {
		return origERTName;
	}

	public void setOrigERTName(String origERTName) {
		this.origERTName = origERTName;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getBlockNo() {
		return blockNo;
	}

	public void setBlockNo(String blockNo) {
		this.blockNo = blockNo;
	}
}
