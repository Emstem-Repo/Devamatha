package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;

public class ExtraCreditActivityTypeForm extends BaseActionForm {
	
	private String activityName;
	private Integer activityTypeId;
	private int reactivateid;
	private String orgActivityName;
	
	private List<ExtraCreditActivityTypeTo> activityTypeList;

	public List<ExtraCreditActivityTypeTo> getActivityTypeList() {
		return activityTypeList;
	}

	public void setActivityTypeList(List<ExtraCreditActivityTypeTo> activityTypeList) {
		this.activityTypeList = activityTypeList;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	
	
	public int getReactivateid() {
		return reactivateid;
	}

	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}

	

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void reset(ActionMapping mapping,HttpServletRequest request){
		super.reset(mapping, request);
		this.activityName=null;
		this.activityTypeId=0;
	}

	

	public String getOrgActivityName() {
		return orgActivityName;
	}

	public void setOrgActivityName(String orgActivityName) {
		this.orgActivityName = orgActivityName;
	}

	public Integer getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	

	
	

}
