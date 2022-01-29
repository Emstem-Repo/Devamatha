package com.kp.cms.forms.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.ApproveLeaveTO;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;

public class ExtraCocurricularLeaveEntryForm extends BaseActionForm{
	
	private String classes;
	private Map<Integer, String> cocurriculartActivityMap;
	Map<Date, CocurricularAttendnaceEntryTo> cocurricularAttendanceEntryToList;
	private int stuId;
	private List<ApproveLeaveTO> approveLeaveTOs;
	List<CocurricularAttendnaceEntryTo> list;
	//newly added
	private boolean dislaySubmitButton;
	public void clearAll(){
		this.classes = null;
		this.cocurriculartActivityMap = null;
		this.approveLeaveTOs=null;
		this.cocurricularAttendanceEntryToList = null;
		this.approveLeaveTOs = null;
		this.list = null;
	}
	//newly added
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public String getClasses() {
		return classes;
	}

	public void setClasses(String classes) {
		this.classes = classes;
	}

	public Map<Integer, String> getCocurriculartActivityMap() {
		return cocurriculartActivityMap;
	}

	public void setCocurriculartActivityMap(
			Map<Integer, String> cocurriculartActivityMap) {
		this.cocurriculartActivityMap = cocurriculartActivityMap;
	}

	public Map<Date, CocurricularAttendnaceEntryTo> getCocurricularAttendanceEntryToList() {
		return cocurricularAttendanceEntryToList;
	}

	public void setCocurricularAttendanceEntryToList(
			Map<Date, CocurricularAttendnaceEntryTo> cocurricularAttendanceEntryToList) {
		this.cocurricularAttendanceEntryToList = cocurricularAttendanceEntryToList;
	}

	public int getStuId() {
		return stuId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}

	public List<ApproveLeaveTO> getApproveLeaveTOs() {
		return approveLeaveTOs;
	}

	public void setApproveLeaveTOs(List<ApproveLeaveTO> approveLeaveTOs) {
		this.approveLeaveTOs = approveLeaveTOs;
	}

	public List<CocurricularAttendnaceEntryTo> getList() {
		return list;
	}

	public void setList(List<CocurricularAttendnaceEntryTo> list) {
		this.list = list;
	}
	public boolean isDislaySubmitButton() {
		return dislaySubmitButton;
	}
	public void setDislaySubmitButton(boolean dislaySubmitButton) {
		this.dislaySubmitButton = dislaySubmitButton;
	}
	
}
