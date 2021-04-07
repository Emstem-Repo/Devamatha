package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.HonoursCourseEntryTo;

public class HonoursCourseEntryForm extends BaseActionForm{
	private int id;
	private String honoursCourseId;
	private String eligibleCourseId;
	Map<Integer,String> honoursCourseMap;
	Map<Integer,String> eligiableCourseMap;
	private List<HonoursCourseEntryTo> honoursCourseEntryTo;
	private int dupId;
	private String orgHonoursCourseId;
	private String orgEligibleCourseId;
	
	// Student Login
	
	private Map<Integer,String> courseMap;
	private String studentId;
	private Map<Integer, HonoursCourseEntryTo> academicDetails;
	private String arrears;
	private String studentName;
	private String regNo;
	private String combination;
	private String gender;
	private String emailId;
	private String permanentAdd;
	private String presentAdd;
	private String contactNo;
	private String mobileNo;
	private String selectedCourse;
	private String appliedCourseId;
	
	
	private List<HonoursCourseEntryTo> appliedDetails; 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHonoursCourseId() {
		return honoursCourseId;
	}
	public void setHonoursCourseId(String honoursCourseId) {
		this.honoursCourseId = honoursCourseId;
	}
	public String getEligibleCourseId() {
		return eligibleCourseId;
	}
	public void setEligibleCourseId(String eligibleCourseId) {
		this.eligibleCourseId = eligibleCourseId;
	}
	public Map<Integer, String> getHonoursCourseMap() {
		return honoursCourseMap;
	}
	public void setHonoursCourseMap(Map<Integer, String> honoursCourseMap) {
		this.honoursCourseMap = honoursCourseMap;
	}
	public Map<Integer, String> getEligiableCourseMap() {
		return eligiableCourseMap;
	}
	public void setEligiableCourseMap(Map<Integer, String> eligiableCourseMap) {
		this.eligiableCourseMap = eligiableCourseMap;
	}
	public List<HonoursCourseEntryTo> getHonoursCourseEntryTo() {
		return honoursCourseEntryTo;
	}
	public void setHonoursCourseEntryTo(
			List<HonoursCourseEntryTo> honoursCourseEntryTo) {
		this.honoursCourseEntryTo = honoursCourseEntryTo;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.id= 0;
		this.honoursCourseId = null;
		this.eligibleCourseId = null;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getOrgHonoursCourseId() {
		return orgHonoursCourseId;
	}
	public void setOrgHonoursCourseId(String orgHonoursCourseId) {
		this.orgHonoursCourseId = orgHonoursCourseId;
	}
	public String getOrgEligibleCourseId() {
		return orgEligibleCourseId;
	}
	public void setOrgEligibleCourseId(String orgEligibleCourseId) {
		this.orgEligibleCourseId = orgEligibleCourseId;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public Map<Integer, HonoursCourseEntryTo> getAcademicDetails() {
		return academicDetails;
	}
	public void setAcademicDetails(
			Map<Integer, HonoursCourseEntryTo> academicDetails) {
		this.academicDetails = academicDetails;
	}
	public String getArrears() {
		return arrears;
	}
	public void setArrears(String arrears) {
		this.arrears = arrears;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegNo() {
		return regNo;
	}
	public void setRegNo(String regNo) {
		this.regNo = regNo;
	}
	public String getCombination() {
		return combination;
	}
	public void setCombination(String combination) {
		this.combination = combination;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getPermanentAdd() {
		return permanentAdd;
	}
	public void setPermanentAdd(String permanentAdd) {
		this.permanentAdd = permanentAdd;
	}
	public String getPresentAdd() {
		return presentAdd;
	}
	public void setPresentAdd(String presentAdd) {
		this.presentAdd = presentAdd;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getSelectedCourse() {
		return selectedCourse;
	}
	public void setSelectedCourse(String selectedCourse) {
		this.selectedCourse = selectedCourse;
	}
	public String getAppliedCourseId() {
		return appliedCourseId;
	}
	public void setAppliedCourseId(String appliedCourseId) {
		this.appliedCourseId = appliedCourseId;
	}
	public List<HonoursCourseEntryTo> getAppliedDetails() {
		return appliedDetails;
	}
	public void setAppliedDetails(List<HonoursCourseEntryTo> appliedDetails) {
		this.appliedDetails = appliedDetails;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
}
