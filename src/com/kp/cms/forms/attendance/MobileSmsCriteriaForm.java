package com.kp.cms.forms.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.attendance.MobileSMSCriteriaTO;
import com.kp.cms.to.exam.KeyValueTO;

public class MobileSmsCriteriaForm extends BaseActionForm {

	private int id;
	private String courseId;
	private List<CourseTO> courseList;
	private ArrayList<KeyValueTO> courseGroupCodeList;
	private String termNo;
	private String sectionName;
	private String className;
	private String classSchemWiseID;
	private Map<Integer, String> classMap;
	private String startMins;
	private String startHours;
	private String disableSMS;
	private String fromDate;
	Map<Integer, String> classMapforAll; 
	private String toDate;
	private String smsCriteriaId;
	private List<MobileSMSCriteriaTO> mobileSMSCriteriaTOlist;
	private String particular;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public List<CourseTO> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}
	
	public ArrayList<KeyValueTO> getCourseGroupCodeList() {
		return courseGroupCodeList;
	}
	public void setCourseGroupCodeList(ArrayList<KeyValueTO> courseGroupCodeList) {
		this.courseGroupCodeList = courseGroupCodeList;
	}
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	
	public String getClassSchemWiseID() {
		return classSchemWiseID;
	}
	public void setClassSchemWiseID(String classSchemWiseID) {
		this.classSchemWiseID = classSchemWiseID;
	}
	public String getStartMins() {
		return startMins;
	}
	public void setStartMins(String startMins) {
		this.startMins = startMins;
	}
	public String getStartHours() {
		return startHours;
	}
	public void setStartHours(String startHours) {
		this.startHours = startHours;
	}
	public String getDisableSMS() {
		return disableSMS;
	}
	public void setDisableSMS(String disableSMS) {
		this.disableSMS = disableSMS;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public List<MobileSMSCriteriaTO> getMobileSMSCriteriaTOlist() {
		return mobileSMSCriteriaTOlist;
	}
	public void setMobileSMSCriteriaTOlist(
			List<MobileSMSCriteriaTO> mobileSMSCriteriaTOlist) {
		this.mobileSMSCriteriaTOlist = mobileSMSCriteriaTOlist;
	}
	
	public String getSmsCriteriaId() {
		return smsCriteriaId;
	}
	public void setSmsCriteriaId(String smsCriteriaId) {
		this.smsCriteriaId = smsCriteriaId;
	}
	
	public String getParticular() {
		return particular;
	}
	public void setParticular(String particular) {
		this.particular = particular;
	}
	
	public Map<Integer, String> getClassMapforAll() {
		return classMapforAll;
	}
	public void setClassMapforAll(Map<Integer, String> classMapforAll) {
		this.classMapforAll = classMapforAll;
	}
	public void clearAll() {
		this.courseId=null;
		this.courseList=null;
		this.courseGroupCodeList=null;
		this.termNo=null;
		this.sectionName=null;
		this.classMap=null;
		this.classSchemWiseID=null;
		this.startHours=null;
		this.startMins=null;
		this.disableSMS="false";
		this.toDate=null;
		this.fromDate=null;
		this.mobileSMSCriteriaTOlist=null;
		this.smsCriteriaId=null;
		this.particular=null;
		this.classMapforAll=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
}
