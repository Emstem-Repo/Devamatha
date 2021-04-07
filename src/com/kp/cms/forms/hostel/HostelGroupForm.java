package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.ApplicationFormTO;
import com.kp.cms.to.hostel.HostelGroupStudentTO;

public class HostelGroupForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String groupName;
	private String floorNo;
	private List<ApplicationFormTO> studList;
	private Map<Integer, HostelGroupStudentTO> hostelGroupStudentMap;
	private int duplId;
	private Boolean studentDuplicated;
	
	
	public int getId() {
		return id;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public List<ApplicationFormTO> getStudList() {
		return studList;
	}
	public void setStudList(List<ApplicationFormTO> studList) {
		this.studList = studList;
	}
	public Map<Integer, HostelGroupStudentTO> getHostelGroupStudentMap() {
		return hostelGroupStudentMap;
	}
	public void setHostelGroupStudentMap(
			Map<Integer, HostelGroupStudentTO> hostelGroupStudentMap) {
		this.hostelGroupStudentMap = hostelGroupStudentMap;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public Boolean getStudentDuplicated() {
		return studentDuplicated;
	}
	public void setStudentDuplicated(Boolean studentDuplicated) {
		this.studentDuplicated = studentDuplicated;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

}
