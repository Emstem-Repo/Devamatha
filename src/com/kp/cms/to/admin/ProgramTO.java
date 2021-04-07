package com.kp.cms.to.admin;

// Generated Jan 7, 2009 2:40:32 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Program generated by hbm2java
 */
public class ProgramTO implements java.io.Serializable,Comparable<ProgramTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6130038861770711162L;
	private int id;
	private ProgramTypeTO programTypeTo;
	private String code;
	private String name;
	private Boolean isActive;
	private Integer createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private Set courses = new HashSet(0);
	private List<CourseTO> courseList;
	private Boolean isMotherTongue;
	private Boolean isDisplayLanguageKnown;
	private Boolean isHeightWeight;
	private Boolean isDisplayTrainingCourse;
	private Boolean isAdditionalInfo;
	private Boolean isExtraDetails;
	private Boolean isSecondLanguage;
	private Boolean isFamilyBackground;
	private Boolean isLateralDetails;
	private Boolean isEntranceDetails;
	private Boolean isTransferCourse;
	private Boolean isTCDetails;
	private Boolean isExamCenterRequired;
	private Boolean isRegistrationNo;
	private Boolean isOpen;
	
	
	private String created;
	private String modified;
	private String active;
	private String motherTongue;
	private String secondLanguage;
	private String displayLanguageKnown;
	private String familyBackground;
	private String heightWeight;
	private String entranceDetails;
	private String lateralDetails;
	private String displayTrainingCourse;
	private String transferCourse;
	private String additionalInfo;
	private String extraDetails;
	private String tcDetails;
	private String registrationNo;
	private String cDate;
	private String lDate;
	private String certificateProgramName;
	private String stream;
	private int streamId;
	private String academicYear;
	
		
	public ProgramTO(int id, ProgramTypeTO programTypeTo, String code,
			String name, Boolean isActive, Integer createdBy, Date createdDate,
			Integer modifiedBy, Date lastModifiedDate, Set courses,
			List<CourseTO> courseList, Boolean isMotherTongue,
			Boolean isDisplayLanguageKnown, Boolean isHeightWeight,
			Boolean isDisplayTrainingCourse, Boolean isAdditionalInfo,
			Boolean isExtraDetails, Boolean isSecondLanguage,
			Boolean isFamilyBackground, Boolean isLateralDetails,
			Boolean isEntranceDetails, Boolean isTransferCourse,
			Boolean isTCDetails, Boolean isExamCenterRequired, String created,
			String modified, String active, String motherTongue,
			String secondLanguage, String displayLanguageKnown,
			String familyBackground, String heightWeight,
			String entranceDetails, String lateralDetails,
			String displayTrainingCourse, String transferCourse,
			String additionalInfo, String extraDetails, String tcDetails,
			String registrationNo, String cDate, String lDate,
			String certificateProgramName, String stream, int streamId) {
		super();
		this.id = id;
		this.programTypeTo = programTypeTo;
		this.code = code;
		this.name = name;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = (Date)createdDate.clone();
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
		this.courses = courses;
		this.courseList = courseList;
		this.isMotherTongue = isMotherTongue;
		this.isDisplayLanguageKnown = isDisplayLanguageKnown;
		this.isHeightWeight = isHeightWeight;
		this.isDisplayTrainingCourse = isDisplayTrainingCourse;
		this.isAdditionalInfo = isAdditionalInfo;
		this.isExtraDetails = isExtraDetails;
		this.isSecondLanguage = isSecondLanguage;
		this.isFamilyBackground = isFamilyBackground;
		this.isLateralDetails = isLateralDetails;
		this.isEntranceDetails = isEntranceDetails;
		this.isTransferCourse = isTransferCourse;
		this.isTCDetails = isTCDetails;
		this.isExamCenterRequired = isExamCenterRequired;
		this.created = created;
		this.modified = modified;
		this.active = active;
		this.motherTongue = motherTongue;
		this.secondLanguage = secondLanguage;
		this.displayLanguageKnown = displayLanguageKnown;
		this.familyBackground = familyBackground;
		this.heightWeight = heightWeight;
		this.entranceDetails = entranceDetails;
		this.lateralDetails = lateralDetails;
		this.displayTrainingCourse = displayTrainingCourse;
		this.transferCourse = transferCourse;
		this.additionalInfo = additionalInfo;
		this.extraDetails = extraDetails;
		this.tcDetails = tcDetails;
		this.registrationNo = registrationNo;
		this.cDate = cDate;
		this.lDate = lDate;
		this.certificateProgramName = certificateProgramName;
		this.stream = stream;
		this.streamId = streamId;
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

	public ProgramTO() {
	}

	/*public Program(int id, ProgramType programType) {
		this.id = id;
		this.programType = programType;
	}*/

	

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return (Date)this.createdDate.clone();
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = (Date)createdDate.clone();
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return (Date)this.lastModifiedDate.clone();
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = (Date)lastModifiedDate.clone();
	}

	public Set getCourses() {
		return this.courses;
	}

	public void setCourses(Set courses) {
		this.courses = courses;
	}

	public ProgramTypeTO getProgramTypeTo() {
		return programTypeTo;
	}

	public void setProgramTypeTo(ProgramTypeTO programTypeTo) {
		this.programTypeTo = programTypeTo;
	}

	public List<CourseTO> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<CourseTO> courseList) {
		this.courseList = courseList;
	}

	public Boolean getIsMotherTongue() {
		return isMotherTongue;
	}

	public void setIsMotherTongue(Boolean isMotherTongue) {
		this.isMotherTongue = isMotherTongue;
	}

	public Boolean getIsDisplayLanguageKnown() {
		return isDisplayLanguageKnown;
	}

	public void setIsDisplayLanguageKnown(Boolean isDisplayLanguageKnown) {
		this.isDisplayLanguageKnown = isDisplayLanguageKnown;
	}

	public Boolean getIsHeightWeight() {
		return isHeightWeight;
	}

	public void setIsHeightWeight(Boolean isHeightWeight) {
		this.isHeightWeight = isHeightWeight;
	}

	public Boolean getIsDisplayTrainingCourse() {
		return isDisplayTrainingCourse;
	}

	public void setIsDisplayTrainingCourse(Boolean isDisplayTrainingCourse) {
		this.isDisplayTrainingCourse = isDisplayTrainingCourse;
	}

	public Boolean getIsAdditionalInfo() {
		return isAdditionalInfo;
	}

	public void setIsAdditionalInfo(Boolean isAdditionalInfo) {
		this.isAdditionalInfo = isAdditionalInfo;
	}

	public Boolean getIsExtraDetails() {
		return isExtraDetails;
	}

	public void setIsExtraDetails(Boolean isExtraDetails) {
		this.isExtraDetails = isExtraDetails;
	}

	public Boolean getIsSecondLanguage() {
		return isSecondLanguage;
	}

	public void setIsSecondLanguage(Boolean isSecondLanguage) {
		this.isSecondLanguage = isSecondLanguage;
	}

	public Boolean getIsFamilyBackground() {
		return isFamilyBackground;
	}

	public void setIsFamilyBackground(Boolean isFamilyBackground) {
		this.isFamilyBackground = isFamilyBackground;
	}

	public Boolean getIsLateralDetails() {
		return isLateralDetails;
	}

	public void setIsLateralDetails(Boolean isLateralDetails) {
		this.isLateralDetails = isLateralDetails;
	}

	public Boolean getIsEntranceDetails() {
		return isEntranceDetails;
	}

	public void setIsEntranceDetails(Boolean isEntranceDetails) {
		this.isEntranceDetails = isEntranceDetails;
	}

	public Boolean getIsTransferCourse() {
		return isTransferCourse;
	}

	public void setIsTransferCourse(Boolean isTransferCourse) {
		this.isTransferCourse = isTransferCourse;
	}

	public Boolean getIsTCDetails() {
		return isTCDetails;
	}

	public void setIsTCDetails(Boolean isTCDetails) {
		this.isTCDetails = isTCDetails;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getModified() {
		return modified;
	}

	public void setModified(String modified) {
		this.modified = modified;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public String getDisplayLanguageKnown() {
		return displayLanguageKnown;
	}

	public void setDisplayLanguageKnown(String displayLanguageKnown) {
		this.displayLanguageKnown = displayLanguageKnown;
	}

	public String getFamilyBackground() {
		return familyBackground;
	}

	public void setFamilyBackground(String familyBackground) {
		this.familyBackground = familyBackground;
	}

	public String getHeightWeight() {
		return heightWeight;
	}

	public void setHeightWeight(String heightWeight) {
		this.heightWeight = heightWeight;
	}

	public String getEntranceDetails() {
		return entranceDetails;
	}

	public void setEntranceDetails(String entranceDetails) {
		this.entranceDetails = entranceDetails;
	}

	public String getLateralDetails() {
		return lateralDetails;
	}

	public void setLateralDetails(String lateralDetails) {
		this.lateralDetails = lateralDetails;
	}

	public String getDisplayTrainingCourse() {
		return displayTrainingCourse;
	}

	public void setDisplayTrainingCourse(String displayTrainingCourse) {
		this.displayTrainingCourse = displayTrainingCourse;
	}

	public String getTransferCourse() {
		return transferCourse;
	}

	public void setTransferCourse(String transferCourse) {
		this.transferCourse = transferCourse;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public String getExtraDetails() {
		return extraDetails;
	}

	public void setExtraDetails(String extraDetails) {
		this.extraDetails = extraDetails;
	}


	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getTcDetails() {
		return tcDetails;
	}

	public void setTcDetails(String tcDetails) {
		this.tcDetails = tcDetails;
	}

	public Boolean getIsExamCenterRequired() {
		return isExamCenterRequired;
	}

	public void setIsExamCenterRequired(Boolean isExamCenterRequired) {
		this.isExamCenterRequired = isExamCenterRequired;
	}

	public void setCertificateProgramName(String certificateProgramName) {
		this.certificateProgramName = certificateProgramName;
	}

	public String getCertificateProgramName() {
		return certificateProgramName;
	}
	
	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public int getStreamId() {
		return streamId;
	}

	public void setStreamId(int streamId) {
		this.streamId = streamId;
	}

	@Override
	public int compareTo(ProgramTO temp) {
		if(temp.getName()!=null && this.name!=null)
			return this.name.compareToIgnoreCase(temp.getName());
		return 0;
	}

	public Boolean getIsRegistrationNo() {
		return isRegistrationNo;
	}

	public void setIsRegistrationNo(Boolean isRegistrationNo) {
		this.isRegistrationNo = isRegistrationNo;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}
	
}