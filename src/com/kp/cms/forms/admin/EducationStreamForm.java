package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EducationStreamTO;

public class EducationStreamForm extends BaseActionForm 
{
	
	private int educstreamId;
	private String educstreamName;
	private int reactivateid;
	private String orgName;
	
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	private List<EducationStreamTO> educationStreamList;
	
	
	
	public List<EducationStreamTO> getEducationStreamList() {
		return educationStreamList;
	}
	public void setEducationStreamList(List<EducationStreamTO> educationStreamList) {
		this.educationStreamList = educationStreamList;
	}
	public int getEducstreamId() {
		return educstreamId;
	}
	public void setEducstreamId(int educstreamId) {
		this.educstreamId = educstreamId;
	}
	
	public String getEducstreamName() {
		return educstreamName;
	}
	public void setEducstreamName(String educstreamName) {
		this.educstreamName = educstreamName;
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
	

}
