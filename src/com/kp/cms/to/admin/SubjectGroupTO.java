package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * date 20/jan/2009
 */
public class SubjectGroupTO implements Serializable{
		
		private int id;
		private String name;
		private String subjectNames;
		private CourseTO courseTO;
		private Set<SubjectGroupSubjectsTO> subjectGroupSubjectsetTO;
		private SubjectGroupSubjectsTO subjectGroupSubjectsTO;
		
		private String createdBy;;
		private String modifiedBy;
		private Date createdDate;
		private Date lastModifiedDate;
		private String isActive;
		private List<SubjectGroupSubjectsTO> subjectGroupSubjectsTOList;
		private String cDate;
		private String lDate;
		
		
		private Boolean isCommonSubGrp;
		private Integer secondLanguageId;
		private String programTypeName;
		private String programName;
		private String courseName;
		private int programTypeId;
		private int courseId;
		private int programId;
		
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
		/**
		 * @return the id
		 */
		public int getId() {
			return id;
		}
		/**
		 * @param id the id to set
		 */
		public void setId(int id) {
			this.id = id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		public String getSubjectNames() {
			return subjectNames;
		}
		public void setSubjectNames(String subjectNames) {
			this.subjectNames = subjectNames;
		}
		public CourseTO getCourseTO() {
			return courseTO;
		}
		public void setCourseTO(CourseTO courseTO) {
			this.courseTO = courseTO;
		}
		public Set<SubjectGroupSubjectsTO> getSubjectGroupSubjectsetTO() {
			return subjectGroupSubjectsetTO;
		}
		public void setSubjectGroupSubjectsetTO(
				Set<SubjectGroupSubjectsTO> subjectGroupSubjectsetTO) {
			this.subjectGroupSubjectsetTO = subjectGroupSubjectsetTO;
		}
		public SubjectGroupSubjectsTO getSubjectGroupSubjectsTO() {
			return subjectGroupSubjectsTO;
		}
		public void setSubjectGroupSubjectsTO(
				SubjectGroupSubjectsTO subjectGroupSubjectsTO) {
			this.subjectGroupSubjectsTO = subjectGroupSubjectsTO;
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
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		public List<SubjectGroupSubjectsTO> getSubjectGroupSubjectsTOList() {
			return subjectGroupSubjectsTOList;
		}
		public void setSubjectGroupSubjectsTOList(
				List<SubjectGroupSubjectsTO> subjectGroupSubjectsTOList) {
			this.subjectGroupSubjectsTOList = subjectGroupSubjectsTOList;
		}
		public Boolean getIsCommonSubGrp() {
			return isCommonSubGrp;
		}
		public void setIsCommonSubGrp(Boolean isCommonSubGrp) {
			this.isCommonSubGrp = isCommonSubGrp;
		}
		public Integer getSecondLanguageId() {
			return secondLanguageId;
		}
		public void setSecondLanguageId(Integer secondLanguageId) {
			this.secondLanguageId = secondLanguageId;
		}
		public String getProgramTypeName() {
			return programTypeName;
		}
		public void setProgramTypeName(String programTypeName) {
			this.programTypeName = programTypeName;
		}
		public String getProgramName() {
			return programName;
		}
		public void setProgramName(String programName) {
			this.programName = programName;
		}
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public int getProgramTypeId() {
			return programTypeId;
		}
		public void setProgramTypeId(int programTypeId) {
			this.programTypeId = programTypeId;
		}
		public int getCourseId() {
			return courseId;
		}
		public void setCourseId(int courseId) {
			this.courseId = courseId;
		}
		public int getProgramId() {
			return programId;
		}
		public void setProgramId(int programId) {
			this.programId = programId;
		}

}