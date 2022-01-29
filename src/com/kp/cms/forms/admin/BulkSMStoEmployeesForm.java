package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class BulkSMStoEmployeesForm extends BaseActionForm{
	private Map<Integer, String> rollMap;
	private String messageBody;
	private String rollNames;
	private List<UserInfoTO> userList;
	private String[] rolls;
	
	public void clearAll() {
		this.rollMap = null;
		this.messageBody = null;
		this.rollNames = null;
		this.userList = null;
		this.rolls = null;
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public Map<Integer, String> getRollMap() {
		return rollMap;
	}
	public void setRollMap(Map<Integer, String> rollMap) {
		this.rollMap = rollMap;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	public String getRollNames() {
		return rollNames;
	}
	public void setRollNames(String rollNames) {
		this.rollNames = rollNames;
	}
	public List<UserInfoTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserInfoTO> userList) {
		this.userList = userList;
	}
	public String[] getRolls() {
		return rolls;
	}
	public void setRolls(String[] rolls) {
		this.rolls = rolls;
	}
	
	
	

}
