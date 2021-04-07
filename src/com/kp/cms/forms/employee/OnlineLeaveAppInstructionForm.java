package com.kp.cms.forms.employee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.OnlineLeaveAppInstructionTO;

public class OnlineLeaveAppInstructionForm extends BaseActionForm {
	 private int id;
	 private String description;
	 List<OnlineLeaveAppInstructionTO> leaveInstructionsTo;
	 private int onlineLeaveAppId;
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescription() {
		return description;
	}
	public List<OnlineLeaveAppInstructionTO> getLeaveInstructionsTo() {
		return leaveInstructionsTo;
	}
	public void setLeaveInstructionsTo(
			List<OnlineLeaveAppInstructionTO> leaveInstructionsTo) {
		this.leaveInstructionsTo = leaveInstructionsTo;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.description = null;
	}
	public void setOnlineLeaveAppId(int onlineLeaveAppId) {
		this.onlineLeaveAppId = onlineLeaveAppId;
	}
	public int getOnlineLeaveAppId() {
		return onlineLeaveAppId;
	}
	}
