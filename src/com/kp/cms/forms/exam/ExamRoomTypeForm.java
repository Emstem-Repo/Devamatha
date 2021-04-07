package com.kp.cms.forms.exam;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.ExamRoomTypeBO;
import com.kp.cms.forms.BaseActionForm;

public class ExamRoomTypeForm extends BaseActionForm {
	
	/**
	 * Used in Exam Module
	 */
	private int examRoomTypeId;
	private String examRoomTypeName;
	private String examRoomTypeDesc;
	private String createdBy;;
	private String modifiedBy;
	private Date createdDate;
	private Date lastModifiedDate;
	private List<ExamRoomTypeBO> roomTypeList;
	private int origERTId;
	private String origERTName;
	private String origERTDesc;
	private String returnValue;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void clearPage(ActionMapping mapping, HttpServletRequest request) {
		this.examRoomTypeName = null;
		this.examRoomTypeDesc = null;

	}
	

	public int getExamRoomTypeId() {
		return examRoomTypeId;
	}

	public void setExamRoomTypeId(int examRoomTypeId) {
		this.examRoomTypeId = examRoomTypeId;
	}

	public String getExamRoomTypeName() {
		return examRoomTypeName;
	}

	public void setExamRoomTypeName(String examRoomTypeName) {
		this.examRoomTypeName = examRoomTypeName;
	}

	public String getExamRoomTypeDesc() {
		return examRoomTypeDesc;
	}

	public void setExamRoomTypeDesc(String examRoomTypeDesc) {
		this.examRoomTypeDesc = examRoomTypeDesc;
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

	public int getOrigERTId() {
		return origERTId;
	}

	public void setOrigERTId(int origERTId) {
		this.origERTId = origERTId;
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

	public void setOrigERTDesc(String origERTDesc) {
		this.origERTDesc = origERTDesc;
	}

	public String getOrigERTDesc() {
		return origERTDesc;
	}

}
