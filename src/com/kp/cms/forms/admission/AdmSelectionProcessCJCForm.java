package com.kp.cms.forms.admission;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.University;
import com.kp.cms.forms.BaseActionForm;

public class AdmSelectionProcessCJCForm extends BaseActionForm {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String date;
	private String time;
	private String venue;
	private String courseId;
	private String gender;
	private String universityId; 
	private String batchYear;
	private String listName;
	private String categoryType;
	private String casteId;
	private	String religionId;
	private	String	institutionId;
	private String selectionOrder;
	private Map<String,String> institutionMap;
	private Map<String,String> religionMap;
	private Map<String,String> casteMap; 
	private Map<String,String> courseMaps;
	private Map<String, String> universityMap;
	private List<Integer> courseList;
	private List<Integer> notDefinedIntPgmCourse;
	
	public List<Integer> getNotDefinedIntPgmCourse() {
		return notDefinedIntPgmCourse;
	}
	public void setNotDefinedIntPgmCourse(List<Integer> notDefinedIntPgmCourse) {
		this.notDefinedIntPgmCourse = notDefinedIntPgmCourse;
	}
		
	public List<Integer> getCourseList() {
		return courseList;
	}
	public void setCourseList(List<Integer> courseList) {
		this.courseList = courseList;
	}
	public Map<String, String> getUniversityMap() {
		return universityMap;
	}
	public void setUniversityMap(Map<String, String> universityMap) {
		this.universityMap = universityMap;
	}
	public Map<String, String> getCourseMaps() {
		return courseMaps;
	}
	public void setCourseMaps(Map<String, String> courseMaps) {
		this.courseMaps = courseMaps;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	public String getBatchYear() {
		return batchYear;
	}
	public void setBatchYear(String batchYear) {
		this.batchYear = batchYear;
	}
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public String getCasteId() {
		return casteId;
	}
	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}
	public String getReligionId() {
		return religionId;
	}
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public String getSelectionOrder() {
		return selectionOrder;
	}
	public void setSelectionOrder(String selectionOrder) {
		this.selectionOrder = selectionOrder;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public Map<String, String> getInstitutionMap() {
		return institutionMap;
	}
	public void setInstitutionMap(Map<String, String> institutionMap) {
		this.institutionMap = institutionMap;
	}
	public Map<String, String> getReligionMap() {
		return religionMap;
	}
	public void setReligionMap(Map<String, String> religionMap) {
		this.religionMap = religionMap;
	}
	public Map<String, String> getCasteMap() {
		return casteMap;
	}
	public void setCasteMap(Map<String, String> casteMap) {
		this.casteMap = casteMap;
	}
	
	
	public void clearAll(){
		this.batchYear=null;
		this.casteId=null;
		this.categoryType=null;
		this.courseId=null;
		this.date=null;
		this.gender=null;
		this.institutionId=null;
		this.listName=null;
		this.religionId=null;
		this.selectionOrder=null;
		this.time=null;
		this.universityId=null;
		this.venue=null;
	}
	

}
