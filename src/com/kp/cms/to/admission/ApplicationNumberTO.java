package com.kp.cms.to.admission;

import java.util.List;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.CourseTO;

public class ApplicationNumberTO {
	private CourseTO courseTo;
	private int year;
	private String onlineAppNoFrom;
	private String onlineAppNoTill;
	private String offlineAppNoFrom;
	private String offlineAppNoTill;
	private int id;
	private int endYear;
	private String isActive;
	private List<CourseApplicationNoTO> courseApplicationNoTO;
	private String academicYear;
	private String createdBy;;
	private String modifiedBy;
	private String createdDate;
	private String lastModifiedDate;
	private String currentOnlineApplnNo;
	private String currentOfflineApplnNo;
	
	public String getAcademicYear() {
		return academicYear;
	}
	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	public CourseTO getCourseTo() {
		return courseTo;
	}
	public void setCourseTo(CourseTO courseTo) {
		this.courseTo = courseTo;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getOnlineAppNoFrom() {
		return onlineAppNoFrom;
	}
	public void setOnlineAppNoFrom(String onlineAppNoFrom) {
		this.onlineAppNoFrom = onlineAppNoFrom;
	}
	public String getOnlineAppNoTill() {
		return onlineAppNoTill;
	}
	public void setOnlineAppNoTill(String onlineAppNoTill) {
		this.onlineAppNoTill = onlineAppNoTill;
	}
	public String getOfflineAppNoFrom() {
		return offlineAppNoFrom;
	}
	public void setOfflineAppNoFrom(String offlineAppNoFrom) {
		this.offlineAppNoFrom = offlineAppNoFrom;
	}
	public String getOfflineAppNoTill() {
		return offlineAppNoTill;
	}
	public void setOfflineAppNoTill(String offlineAppNoTill) {
		this.offlineAppNoTill = offlineAppNoTill;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getEndYear() {
		return endYear;
	}
	public void setEndYear(int endYear) {
		this.endYear = endYear;
	}
	public List<CourseApplicationNoTO> getCourseApplicationNoTO() {
		return courseApplicationNoTO;
	}
	public void setCourseApplicationNoTO(
			List<CourseApplicationNoTO> courseApplicationNoTO) {
		this.courseApplicationNoTO = courseApplicationNoTO;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setLastModifiedDate(String lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public String getCurrentOnlineApplnNo() {
		return currentOnlineApplnNo;
	}
	public void setCurrentOnlineApplnNo(String currentOnlineApplnNo) {
		this.currentOnlineApplnNo = currentOnlineApplnNo;
	}
	public String getCurrentOfflineApplnNo() {
		return currentOfflineApplnNo;
	}
	public void setCurrentOfflineApplnNo(String currentOfflineApplnNo) {
		this.currentOfflineApplnNo = currentOfflineApplnNo;
	}

	
}
