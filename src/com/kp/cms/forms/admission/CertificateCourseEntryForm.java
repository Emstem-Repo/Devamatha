package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CCGroupTo;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;

public class CertificateCourseEntryForm extends BaseActionForm {
	private String certificateCourseName;
	private int id;
	private int duplId;
	private String venue;
	private String maxIntake;
	private String startHours;
	private String startMins;
	private String endHours;
	private String endMins;
	private Map<Integer,String> teachersMap;
	private List<CertificateCourseTeacherTO> certificateCourseTeacherList;
	private String certificateCourseTeaId;
	private String oldTeacherId;
	private String teacherId;
	private String semType;
	private String extracurricular;
	private Map<Integer,Integer> groupId;
	private List<CCGroupTo> groupList1;
	private String description;
	
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		certificateCourseName = null;
		id = 0;
		subjectId = null;
		maxIntake=null;
		startHours =null;
		startMins = null;
		endHours = null;
		endMins = null;
		teacherId = null;
		venue = null;
		semType="ODD";
		extracurricular="false";
		description=null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getCertificateCourseName() {
		return certificateCourseName;
	}
	public void setCertificateCourseName(String certificateCourseName) {
		this.certificateCourseName = certificateCourseName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getMaxIntake() {
		return maxIntake;
	}
	public void setMaxIntake(String maxIntake) {
		this.maxIntake = maxIntake;
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
	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}
	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
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
	public String getOldTeacherId() {
		return oldTeacherId;
	}
	public void setOldTeacherId(String oldTeacherId) {
		this.oldTeacherId = oldTeacherId;
	}
	public String getTeacherId() {
		return teacherId;
	}
	public String getSemType() {
		return semType;
	}
	public void setSemType(String semType) {
		this.semType = semType;
	}
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	public String getExtracurricular() {
		return extracurricular;
	}
	public void setExtracurricular(String extracurricular) {
		this.extracurricular = extracurricular;
	}
	public Map<Integer, Integer> getGroupId() {
		return groupId;
	}
	public void setGroupId(Map<Integer, Integer> groupId) {
		this.groupId = groupId;
	}
	public List<CCGroupTo> getGroupList1() {
		return groupList1;
	}
	public void setGroupList1(List<CCGroupTo> groupList1) {
		this.groupList1 = groupList1;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}