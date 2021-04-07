	package com.kp.cms.forms.admin;
	
	import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AppStatusTO;
	
	public class AppStatusForm extends BaseActionForm{
		
	 private int id;
	 private String applicationStatus;
	 private String shortName;
	 private int dupId;
	 private String orgApplicationStatus;
	 private String orgShortName;
	 private List<AppStatusTO> appStatusList; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getOrgApplicationStatus() {
		return orgApplicationStatus;
	}
	public void setOrgApplicationStatus(String orgApplicationStatus) {
		this.orgApplicationStatus = orgApplicationStatus;
	}
	public String getOrgShortName() {
		return orgShortName;
	}
	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.applicationStatus=null;
		this.shortName = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void setAppStatusList(List<AppStatusTO> appStatusList) {
		this.appStatusList = appStatusList;
	}
	public List<AppStatusTO> getAppStatusList() {
		return appStatusList;
	}
	
	}
