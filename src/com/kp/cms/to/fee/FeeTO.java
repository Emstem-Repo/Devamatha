package com.kp.cms.to.fee;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupTO;

public class FeeTO implements Serializable{
		
		/**
	    * 
	    */
		private static final long serialVersionUID = 1L;
		private int id;
		private ProgramTypeTO programTypeTo;
		private ProgramTO programTo;
		private CourseTO courseTo;
		private SubjectGroupTO subjectGroup;
		private FeeDivisionTO feeDivisionTO;
		private FeeGroupTO feeGroupTO;
		private Integer semister;
		private Set<FeeAccountAssignmentTO> feeAccountAssignments = new HashSet<FeeAccountAssignmentTO>(
				0);
		private Integer academicYear;
		private String year;
		// This below map variables stores the feeAccounts, 
		// feeApplicables and admittedthroughMap for particular Fee ID.
		// because each fee can have different data's.
		// will be used in Fee PayMent UI or Module.
		private Map feeAccountsMap;
		private Map feeApplicablesMap;
		private Map admitedThroughMap; 
		private String aidedUnaided;
		private String admittedThrough;
		private Map feeCategoryMap;
		private String feeCategory;
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
		 * @return the programTypeTo
		 */
		public ProgramTypeTO getProgramTypeTo() {
			return programTypeTo;
		}
		/**
		 * @param programTypeTo the programTypeTo to set
		 */
		public void setProgramTypeTo(ProgramTypeTO programTypeTo) {
			this.programTypeTo = programTypeTo;
		}
		/**
		 * @return the programTo
		 */
		public ProgramTO getProgramTo() {
			return programTo;
		}
		/**
		 * @param programTo the programTo to set
		 */
		public void setProgramTo(ProgramTO programTo) {
			this.programTo = programTo;
		}
		/**
		 * @return the courseTo
		 */
		public CourseTO getCourseTo() {
			return courseTo;
		}
		/**
		 * @param courseTo the courseTo to set
		 */
		public void setCourseTo(CourseTO courseTo) {
			this.courseTo = courseTo;
		}
		/**
		 * @return the subjectGroup
		 */
		public SubjectGroupTO getSubjectGroup() {
			return subjectGroup;
		}
		/**
		 * @param subjectGroup the subjectGroup to set
		 */
		public void setSubjectGroup(SubjectGroupTO subjectGroup) {
			this.subjectGroup = subjectGroup;
		}
		/**
		 * @return the feeAccountAssignments
		 */
		public Set<FeeAccountAssignmentTO> getFeeAccountAssignments() {
			return feeAccountAssignments;
		}
		/**
		 * @param feeAccountAssignments the feeAccountAssignments to set
		 */
		public void setFeeAccountAssignments(Set<FeeAccountAssignmentTO> feeAccountAssignments) {
			this.feeAccountAssignments = feeAccountAssignments;
		}
		/**
		 * @return the feeAccountsMap
		 */
		public Map getFeeAccountsMap() {
			return feeAccountsMap;
		}
		/**
		 * @param feeAccountsMap the feeAccountsMap to set
		 */
		public void setFeeAccountsMap(Map feeAccountsMap) {
			this.feeAccountsMap = feeAccountsMap;
		}
		/**
		 * @return the feeApplicablesMap
		 */
		public Map getFeeApplicablesMap() {
			return feeApplicablesMap;
		}
		/**
		 * @param feeApplicablesMap the feeApplicablesMap to set
		 */
		public void setFeeApplicablesMap(Map feeApplicablesMap) {
			this.feeApplicablesMap = feeApplicablesMap;
		}
		/**
		 * @return the admitedThroughMap
		 */
		public Map getAdmitedThroughMap() {
			return admitedThroughMap;
		}
		/**
		 * @param admitedThroughMap the admitedThroughMap to set
		 */
		public void setAdmitedThroughMap(Map admitedThroughMap) {
			this.admitedThroughMap = admitedThroughMap;
		}
		/**
		 * @return the semister
		 */
		public Integer getSemister() {
			return semister;
		}
		/**
		 * @param semister the semister to set
		 */
		public void setSemister(Integer semister) {
			this.semister = semister;
		}
		/**
		 * @return the academicYear
		 */
		public Integer getAcademicYear() {
			return academicYear;
		}
		/**
		 * @param academicYear the academicYear to set
		 */
		public void setAcademicYear(Integer academicYear) {
			this.academicYear = academicYear;
		}
		/**
		 * @return the feeDivisionTO
		 */
		public FeeDivisionTO getFeeDivisionTO() {
			return feeDivisionTO;
		}
		/**
		 * @param feeDivisionTO the feeDivisionTO to set
		 */
		public void setFeeDivisionTO(FeeDivisionTO feeDivisionTO) {
			this.feeDivisionTO = feeDivisionTO;
		}
		/**
		 * @return the feeGroupTO
		 */
		public FeeGroupTO getFeeGroupTO() {
			return feeGroupTO;
		}
		/**
		 * @param feeGroupTO the feeGroupTO to set
		 */
		public void setFeeGroupTO(FeeGroupTO feeGroupTO) {
			this.feeGroupTO = feeGroupTO;
		}
		/**
		 * @return the year
		 */
		public String getYear() {
			return year;
		}
		/**
		 * @param year the year to set
		 */
		public void setYear(String year) {
			this.year = year;
		}
		public String getAidedUnaided() {
			return aidedUnaided;
		}
		public void setAidedUnaided(String aidedUnaided) {
			this.aidedUnaided = aidedUnaided;
		}
		public String getAdmittedThrough() {
			return admittedThrough;
		}
		public void setAdmittedThrough(String admittedThrough) {
			this.admittedThrough = admittedThrough;
		}
		public Map getFeeCategoryMap() {
			return feeCategoryMap;
		}
		public void setFeeCategoryMap(Map feeCategoryMap) {
			this.feeCategoryMap = feeCategoryMap;
		}
		public String getFeeCategory() {
			return feeCategory;
		}
		public void setFeeCategory(String feeCategory) {
			this.feeCategory = feeCategory;
		}
		
	
	
}
