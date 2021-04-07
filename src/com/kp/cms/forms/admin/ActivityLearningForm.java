package com.kp.cms.forms.admin;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ActivityLearningTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;

public class ActivityLearningForm extends BaseActionForm {
	
	
	private List<ActivityLearningTO> activityLearningToList;
	private List<ExtraCreditActivityTypeTo> list;
	private int activityLearningId;
	private String extraCreditActivityType;
	private String minmark;
	private String maxmark;
	private String[] courseIds;
	private Map<Integer, String> courseMap;
	private int reActiveId;
	private int orgId;
	private String year;
	private String creditInfo;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.maxmark=null;
		this.minmark=null;
		this.courseIds=null;
		this.extraCreditActivityType=null;
		this.year=null;
		this.creditInfo=null;
	}

	public List<ActivityLearningTO> getActivityLearningToList() {
		return activityLearningToList;
	}

	public void setActivityLearningToList(
			List<ActivityLearningTO> activityLearningToList) {
		this.activityLearningToList = activityLearningToList;
	}

	public int getActivityLearningId() {
		return activityLearningId;
	}

	public void setActivityLearningId(int activityLearningId) {
		this.activityLearningId = activityLearningId;
	}

	public List<ExtraCreditActivityTypeTo> getList() {
		return list;
	}

	public void setList(List<ExtraCreditActivityTypeTo> list) {
		this.list = list;
	}

	public String getExtraCreditActivityType() {
		return extraCreditActivityType;
	}

	public void setExtraCreditActivityType(String extraCreditActivityType) {
		this.extraCreditActivityType = extraCreditActivityType;
	}

	public String getMinmark() {
		return minmark;
	}

	public void setMinmark(String minmark) {
		this.minmark = minmark;
	}

	public String getMaxmark() {
		return maxmark;
	}

	public void setMaxmark(String maxmark) {
		this.maxmark = maxmark;
	}

	

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}

	public String[] getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String[] courseIds) {
		this.courseIds = courseIds;
	}

	public int getReActiveId() {
		return reActiveId;
	}

	public void setReActiveId(int reActiveId) {
		this.reActiveId = reActiveId;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(String creditInfo) {
		this.creditInfo = creditInfo;
	}

	
	
	

}
