package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ConvocationSessionTo;
import com.kp.cms.to.admin.HonoursCourseEntryTo;

public class ConvocationSessionForm extends BaseActionForm{
	private int id;
	private String[] courseIds;
	private Map<Integer,String> courseMap;
	private List<ConvocationSessionTo> convocationDetails;
	private String date;
	private String amOrpm;
	private String maxGuest;
	private String passAmount;
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.id= 0;
		this.courseIds=null;
		this.amOrpm="AM";
		this.maxGuest=null;
		this.passAmount=null;
		this.date=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAmOrpm() {
		return amOrpm;
	}
	public void setAmOrpm(String amOrpm) {
		this.amOrpm = amOrpm;
	}
	public String getMaxGuest() {
		return maxGuest;
	}
	public void setMaxGuest(String maxGuest) {
		this.maxGuest = maxGuest;
	}
	public String getPassAmount() {
		return passAmount;
	}
	public void setPassAmount(String passAmount) {
		this.passAmount = passAmount;
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
	public List<ConvocationSessionTo> getConvocationDetails() {
		return convocationDetails;
	}
	public void setConvocationDetails(List<ConvocationSessionTo> convocationDetails) {
		this.convocationDetails = convocationDetails;
	}
	
	
}
