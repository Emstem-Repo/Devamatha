package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts.upload.FormFile;
/**
 * 
 * @author kshirod.k
 * A TO class for GuidelinesEntry
 *
 */
@SuppressWarnings("serial")
public class GuidelinesEntryTO implements Serializable {

	private int id;
	private int year;
	private String programType;
	private String program;
	private String course;
	private FormFile thefile;
	private String fileName;
	private String createdBy;
	private String modifiedBy;
	
	
	private String guidelinesYear;
	private String guidelinesFileName;
	private String contentType;
	private String isActive;
	private Date createdDate;
	private Date lastModifiedDate;
	private String academicYear;
	private String cDate;
	private String lDate;
	
	public GuidelinesEntryTO(){
		
	}
	public GuidelinesEntryTO(int id, int year, String programType,
			String program, String course, FormFile thefile, String fileName,
			String createdBy, String modifiedBy, String guidelinesYear,
			String guidelinesFileName, String contentType, String isActive,
			Date createdDate, Date lastModifiedDate, String academicYear,
			String cDate, String lDate, CourseTO courseTO) {
		super();
		this.id = id;
		this.year = year;
		this.programType = programType;
		this.program = program;
		this.course = course;
		this.thefile = thefile;
		this.fileName = fileName;
		this.createdBy = createdBy;
		this.modifiedBy = modifiedBy;
		this.guidelinesYear = guidelinesYear;
		this.guidelinesFileName = guidelinesFileName;
		this.contentType = contentType;
		this.isActive = isActive;
		this.createdDate = (Date)createdDate.clone();
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.academicYear = academicYear;
		this.cDate = cDate;
		this.lDate = lDate;
		this.courseTO = courseTO;
	}

	public String getCDate() {
		return cDate;
	}

	public void setCDate(String date) {
		cDate = date;
	}

	public String getLDate() {
		return lDate;
	}

	public void setLDate(String date) {
		lDate = date;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	private CourseTO courseTO;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	

	public CourseTO getCourseTO() {
		return courseTO;
	}

	public void setCourseTO(CourseTO courseTO) {
		this.courseTO = courseTO;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public String getProgramType() {
		return programType;
	}

	public void setProgramType(String programType) {
		this.programType = programType;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getGuidelinesYear() {
		return guidelinesYear;
	}

	public void setGuidelinesYear(String guidelinesYear) {
		this.guidelinesYear = guidelinesYear;
	}

	public String getGuidelinesFileName() {
		return guidelinesFileName;
	}

	public void setGuidelinesFileName(String guidelinesFileName) {
		this.guidelinesFileName = guidelinesFileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return (Date)createdDate.clone();
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}

	public Date getLastModifiedDate() {
		return (Date)lastModifiedDate.clone();
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
}