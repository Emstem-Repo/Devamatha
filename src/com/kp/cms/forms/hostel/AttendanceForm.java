package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.HostelGroupStudentTO;

public class AttendanceForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String hlGroupId;
	private String attendanceDate;
	private List<HostelGroupStudentTO> hostelGroupStudentList;
	private String hostelName;
	private String groupName;
	
	public int getId() {
		return id;
	}
	
	public String getAttendanceDate() {
		return attendanceDate;
	}
	public List<HostelGroupStudentTO> getHostelGroupStudentList() {
		return hostelGroupStudentList;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	public void setHostelGroupStudentList(
			List<HostelGroupStudentTO> hostelGroupStudentList) {
		this.hostelGroupStudentList = hostelGroupStudentList;
	}

	public String getHlGroupId() {
		return hlGroupId;
	}

	public void setHlGroupId(String hlGroupId) {
		this.hlGroupId = hlGroupId;
	}

	public String getHostelName() {
		return hostelName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
}
