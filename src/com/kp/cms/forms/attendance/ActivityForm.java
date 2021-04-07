package com.kp.cms.forms.attendance;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;


public class ActivityForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private int duplActId; 
	private String origName;
	private int editAttId;
	private String attendanceTypeId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getDuplActId() {
		return duplActId;
	}
	public void setDuplActId(int duplActId) {
		this.duplActId = duplActId;
	}
	public String getOrigName() {
		return origName;
	}
	public void setOrigName(String origName) {
		this.origName = origName;
	}
	
	public int getEditAttId() {
		return editAttId;
	}
	public void setEditAttId(int editAttId) {
		this.editAttId = editAttId;
	}
	public String getAttendanceTypeId() {
		return attendanceTypeId;
	}
	public void setAttendanceTypeId(String attendanceTypeId) {
		this.attendanceTypeId = attendanceTypeId;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.name = null;
		this.origName = null;
		this.editAttId = 0;
		this.attendanceTypeId = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	

}
