package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationOpenSessionTo;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;

public class PeersEvaluationOpenSessionForm extends BaseActionForm{
	private int id;
	private List<DepartmentEntryTO> departmentTO;
	private List<PeersEvaluationOpenSessionTo> sessionToList;
	
	private boolean flag;
	private String sessionId;
	private String startDate;
	private String endDate;
	private String[] departmentIds;
	
 	public void clear( ActionMapping mapping, HttpServletRequest request) {
		super.setAcademicYear(null);
		this.departmentIds = null;
		this.id = 0;
		this.startDate = null;
		this.endDate = null;
		this.sessionId = null;
		this.flag = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<DepartmentEntryTO> getDepartmentTO() {
		return departmentTO;
	}

	public void setDepartmentTO(List<DepartmentEntryTO> departmentTO) {
		this.departmentTO = departmentTO;
	}

	public List<PeersEvaluationOpenSessionTo> getSessionToList() {
		return sessionToList;
	}

	public void setSessionToList(List<PeersEvaluationOpenSessionTo> sessionToList) {
		this.sessionToList = sessionToList;
	}



	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String[] getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String[] departmentIds) {
		this.departmentIds = departmentIds;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
