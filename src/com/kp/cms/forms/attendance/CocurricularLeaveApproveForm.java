package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.ApproveCocurricularLeaveTO;

public class CocurricularLeaveApproveForm  extends BaseActionForm{

	private String fromDate;
	private String toDate;
	private String searchValue;
	private String screenLabel;
	private String teacherName;
	private String teacherID;
	private String activityNames;
	private Map<Integer, String> userMap; 
	private Map<Integer, String> activityMap;
	private String[] activityIds;
	List<ApproveCocurricularLeaveTO> list; 
	//clear All Data
	public void ResetAllData()
	{
		this.fromDate = null;
		this.toDate = null;
		this.list = null;
		this.searchValue= null;
		this.screenLabel = null;
		this.teacherID = null;
		this.userMap = null;
		this.teacherName = null;
		this.activityIds = null;
		this.activityMap = null;
		this.activityNames = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getSearchValue() {
		return searchValue;
	}
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	public String getScreenLabel() {
		return screenLabel;
	}
	public void setScreenLabel(String screenLabel) {
		this.screenLabel = screenLabel;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public Map<Integer, String> getUserMap() {
		return userMap;
	}
	public void setUserMap(Map<Integer, String> userMap) {
		this.userMap = userMap;
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
	public List<ApproveCocurricularLeaveTO> getList() {
		return list;
	}
	public void setList(List<ApproveCocurricularLeaveTO> list) {
		this.list = list;
	}
	public String getActivityNames() {
		return activityNames;
	}
	public void setActivityNames(String activityNames) {
		this.activityNames = activityNames;
	}
	
	
	
	
}
