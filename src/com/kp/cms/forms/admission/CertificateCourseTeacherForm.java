package com.kp.cms.forms.admission;

import java.util.Map;
import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;

public class CertificateCourseTeacherForm extends BaseActionForm{
	private int id;
	private String venue;
	private Map<Integer,String> teachersMap;
	private java.util.List<CertificateCourseTO> courseList;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private String certificateCourseId;
	private String teacherId;
	private List<CertificateCourseTeacherTO> certificateCourseTeacherList;
	private String certificateCourseTeaId;
	private String oldCertificateCourseId;
	private String oldTeacherId;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public java.util.List<CertificateCourseTO> getCourseList() {
		return courseList;
	}


	public void setCourseList(java.util.List<CertificateCourseTO> courseList) {
		this.courseList = courseList;
	}


	public String getVenue() {
		return venue;
	}


	public void setVenue(String venue) {
		this.venue = venue;
	}


	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}


	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
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


	public String getCertificateCourseId() {
		return certificateCourseId;
	}


	public void setCertificateCourseId(String certificateCourseId) {
		this.certificateCourseId = certificateCourseId;
	}


	public String getTeacherId() {
		return teacherId;
	}


	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

	public void reset(){
		this.venue = null;
		this.certificateCourseId=null;
		this.teacherId=null;
		this.startHours=null;
		this.startMins=null;
		this.endHours=null;
		this.endMins=null;
	}


	public List<CertificateCourseTeacherTO> getCertificateCourseTeacherList() {
		return certificateCourseTeacherList;
	}


	public void setCertificateCourseTeacherList(
			List<CertificateCourseTeacherTO> certificateCourseTeacherList) {
		this.certificateCourseTeacherList = certificateCourseTeacherList;
	}


	public String getCertificateCourseTeaId() {
		return certificateCourseTeaId;
	}


	public void setCertificateCourseTeaId(String certificateCourseTeaId) {
		this.certificateCourseTeaId = certificateCourseTeaId;
	}


	public String getOldCertificateCourseId() {
		return oldCertificateCourseId;
	}


	public void setOldCertificateCourseId(String oldCertificateCourseId) {
		this.oldCertificateCourseId = oldCertificateCourseId;
	}


	public String getOldTeacherId() {
		return oldTeacherId;
	}


	public void setOldTeacherId(String oldTeacherId) {
		this.oldTeacherId = oldTeacherId;
	}
	

}
