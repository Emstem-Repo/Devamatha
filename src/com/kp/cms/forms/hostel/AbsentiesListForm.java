package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.AbsentiesListTo;

public class AbsentiesListForm extends BaseActionForm{
	private String holidaysFrom;
	private String holidaysTo;
	private String holidaysFromSession;
	private String holidaysToSession;
	private List<AbsentiesListTo> absentiesListTosList;
	private Map<Integer,String> hostelMap;
	private String mailFor;
	private String methodName;
	private Map<Integer, String> blockMap;
	private Map<Integer, String> unitMap;
	
	
	public Map<Integer, String> getBlockMap() {
		return blockMap;
	}
	public void setBlockMap(Map<Integer, String> blockMap) {
		this.blockMap = blockMap;
	}
	public Map<Integer, String> getUnitMap() {
		return unitMap;
	}
	public void setUnitMap(Map<Integer, String> unitMap) {
		this.unitMap = unitMap;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getMailFor() {
		return mailFor;
	}
	public void setMailFor(String mailFor) {
		this.mailFor = mailFor;
	}
	public Map<Integer, String> getHostelMap() {
		return hostelMap;
	}
	public void setHostelMap(Map<Integer, String> hostelMap) {
		this.hostelMap = hostelMap;
	}
	public List<AbsentiesListTo> getAbsentiesListTosList() {
		return absentiesListTosList;
	}
	public void setAbsentiesListTosList(List<AbsentiesListTo> absentiesListTosList) {
		this.absentiesListTosList = absentiesListTosList;
	}
	public String getHolidaysFrom() {
		return holidaysFrom;
	}
	public void setHolidaysFrom(String holidaysFrom) {
		this.holidaysFrom = holidaysFrom;
	}
	public String getHolidaysTo() {
		return holidaysTo;
	}
	public void setHolidaysTo(String holidaysTo) {
		this.holidaysTo = holidaysTo;
	}
	public String getHolidaysFromSession() {
		return holidaysFromSession;
	}
	public void setHolidaysFromSession(String holidaysFromSession) {
		this.holidaysFromSession = holidaysFromSession;
	}
	public String getHolidaysToSession() {
		return holidaysToSession;
	}
	public void setHolidaysToSession(String holidaysToSession) {
		this.holidaysToSession = holidaysToSession;
	}
	/**
	 * form validation
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
