package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.AssignCocurricularSubjectTeacherTO;

public class AssignCocurricularSubjectTeacherForm  extends BaseActionForm{

	private String id;
	private Map<Integer, String> userMap ;
	private String teacherID;
	private Map<Integer, String> activityMap;
	private String[] activityIds;
	private String dupliateId;
	private List<AssignCocurricularSubjectTeacherTO> assignCocurricularSubjectTeacherTOList;
	public void ResetAllData()
	{
		this.teacherID = null;
		this.userMap = null;
		this.activityMap = null;
		this.activityIds = null;
		this.assignCocurricularSubjectTeacherTOList = null;
		this.id = null;
		this.dupliateId = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<Integer, String> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<Integer, String> userMap) {
		this.userMap = userMap;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public Map<Integer, String> getActivityMap() {
		return activityMap;
	}
	public void setActivityMap(Map<Integer, String> activityMap) {
		this.activityMap = activityMap;
	}
	public String[] getActivityIds() {
		return activityIds;
	}
	public void setActivityIds(String[] activityIds) {
		this.activityIds = activityIds;
	}
	public List<AssignCocurricularSubjectTeacherTO> getAssignCocurricularSubjectTeacherTOList() {
		return assignCocurricularSubjectTeacherTOList;
	}
	public void setAssignCocurricularSubjectTeacherTOList(
			List<AssignCocurricularSubjectTeacherTO> assignCocurricularSubjectTeacherTOList) {
		this.assignCocurricularSubjectTeacherTOList = assignCocurricularSubjectTeacherTOList;
	}
	public String getDupliateId() {
		return dupliateId;
	}
	public void setDupliateId(String dupliateId) {
		this.dupliateId = dupliateId;
	}
	
	
}
