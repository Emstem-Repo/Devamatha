package com.kp.cms.forms.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.attendance.PeriodTO;

public class PeriodForm extends BaseActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String periodName;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private String[] selectedClasses;
	private Map<Integer, String> classMap;
	private List<PeriodTO> periodTOList;
	private String morningSession;
	private String afternoonSession;
	private String session;
	public String[] getSelectedClasses() {
		return selectedClasses;
	}
	public void setSelectedClasses(String[] selectedClasses) {
		this.selectedClasses = selectedClasses;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getStartHours() {
		return startHours;
	}
	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}
	public String getStartMins() {
		return startMins;
	}
	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}
	public String getEndHours() {
		return endHours;
	}
	public void setEndHours(String endHours) {
		this.endHours = endHours;
	}
	public String getEndMins() {
		return endMins;
	}
	public void setEndMins(String endMins) {
		this.endMins = endMins;
	}
	public List<PeriodTO> getPeriodTOList() {
		return periodTOList;
	}
	public void setPeriodTOList(List<PeriodTO> periodTOList) {
		this.periodTOList = periodTOList;
	}

	public String getMorningSession() {
		return morningSession;
	}
	public void setMorningSession(String morningSession) {
		this.morningSession = morningSession;
	}
	public String getAfternoonSession() {
		return afternoonSession;
	}
	public void setAfternoonSession(String afternoonSession) {
		this.afternoonSession = afternoonSession;
	}
	
	
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request){
		super.reset(mapping, request);
		selectedClasses = null;
		periodName = null;
		startHours = null;
		startMins = null;
		endHours = null;
		endMins = null;
		session=null;
		
		//super.setYear(null);
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
