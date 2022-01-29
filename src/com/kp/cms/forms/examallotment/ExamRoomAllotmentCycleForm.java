package com.kp.cms.forms.examallotment;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.examallotment.ExamRoomAllotmentCycleTO;

public class ExamRoomAllotmentCycleForm extends BaseActionForm {
	private int id;
	private String cycleName;
	private String sessionName;
	private String schemeNo;
	private String[] courseIds;
	private String midOrEndSem;
	private String[] unselectedCourseIds;
	private Map<Integer,String> selectedCourseMap;
	private List<ExamRoomAllotmentCycleTO> toList;
	private String orgMidEndSem;
	private String orgCycleName;
	private String orgSessionName;
	private Map<Integer,String> cycleMap;
	private String cycleId;
	private String courseList;
	Map<Integer,Map<String,Map<String,List<Integer>>>> cycleDetailsMap;
	private String flag;
	private String sessionId;
	private Map<Integer, String> sessionMap;
	private String orgSessionId;
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCycleName() {
		return cycleName;
	}
	public void setCycleName(String cycleName) {
		this.cycleName = cycleName;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getSchemeNo() {
		return schemeNo;
	}
	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}
	public String[] getCourseIds() {
		return courseIds;
	}
	public void setCourseIds(String[] courseIds) {
		this.courseIds = courseIds;
	}
	public String getMidOrEndSem() {
		return midOrEndSem;
	}
	public void setMidOrEndSem(String midOrEndSem) {
		this.midOrEndSem = midOrEndSem;
	}
	public String[] getUnselectedCourseIds() {
		return unselectedCourseIds;
	}
	public void setUnselectedCourseIds(String[] unselectedCourseIds) {
		this.unselectedCourseIds = unselectedCourseIds;
	}
	public Map<Integer, String> getSelectedCourseMap() {
		return selectedCourseMap;
	}
	public void setSelectedCourseMap(Map<Integer, String> selectedCourseMap) {
		this.selectedCourseMap = selectedCourseMap;
	}
	public List<ExamRoomAllotmentCycleTO> getToList() {
		return toList;
	}
	public void setToList(List<ExamRoomAllotmentCycleTO> toList) {
		this.toList = toList;
	}
	public String getOrgMidEndSem() {
		return orgMidEndSem;
	}
	public void setOrgMidEndSem(String orgMidEndSem) {
		this.orgMidEndSem = orgMidEndSem;
	}
	public String getOrgCycleName() {
		return orgCycleName;
	}
	public void setOrgCycleName(String orgCycleName) {
		this.orgCycleName = orgCycleName;
	}
	public String getOrgSessionName() {
		return orgSessionName;
	}
	public void setOrgSessionName(String orgSessionName) {
		this.orgSessionName = orgSessionName;
	}
	public void reset() {
		super.setCourseMap(null);
		this.midOrEndSem = null;
		this.schemeNo =null;
		this.sessionName = null;
		this.unselectedCourseIds = null;
		this.courseIds = null;
		this.selectedCourseMap = null;
		this.cycleMap = null;
		this.cycleId =null;
		this.cycleDetailsMap = null;
		this.courseList = null;
		this.flag = "false";
	}
	public void reset1() {
		this.midOrEndSem = null;
		this.cycleName = null;
		this.sessionName = null;
		this.orgCycleName = null;
		this.orgMidEndSem = null;
		this.orgSessionName = null;
		this.toList = null;
		this.sessionId=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	public Map<Integer, String> getCycleMap() {
		return cycleMap;
	}
	public void setCycleMap(Map<Integer, String> cycleMap) {
		this.cycleMap = cycleMap;
	}
	public String getCycleId() {
		return cycleId;
	}
	public void setCycleId(String cycleId) {
		this.cycleId = cycleId;
	}
	public Map<Integer, Map<String, Map<String, List<Integer>>>> getCycleDetailsMap() {
		return cycleDetailsMap;
	}
	public void setCycleDetailsMap(
			Map<Integer, Map<String, Map<String, List<Integer>>>> cycleDetailsMap) {
		this.cycleDetailsMap = cycleDetailsMap;
	}
	public String getCourseList() {
		return courseList;
	}
	public void setCourseList(String courseList) {
		this.courseList = courseList;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Map<Integer, String> getSessionMap() {
		return sessionMap;
	}
	public void setSessionMap(Map<Integer, String> sessionMap) {
		this.sessionMap = sessionMap;
	}
	public String getOrgSessionId() {
		return orgSessionId;
	}
	public void setOrgSessionId(String orgSessionId) {
		this.orgSessionId = orgSessionId;
	}
	
}
