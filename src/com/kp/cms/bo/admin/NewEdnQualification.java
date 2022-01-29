package com.kp.cms.bo.admin;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class NewEdnQualification implements java.io.Serializable{
	
	private int id;
	private String createdBy;;
	private NewDocChecklist docChecklist;
	private String modifiedBy;
	private PersonalData personalData;
	private College college;
	private Integer noOfAttempts;
	private Integer yearPassing;
	private Integer monthPassing;
	private BigDecimal marksObtained;
	private BigDecimal percentage;
	private University university;
	private Date createdDate;
	private Date lastModifiedDate;
	private BigDecimal totalMarks;
	private String institutionNameOthers;
	private String previousRegNo;
	private String universityOthers;
	private BigDecimal weightageAdjustedMarks;
	private State state;
	private Boolean isOutsideIndia;
	private DocTypeExams docTypeExams;
	private Set<EdnSemesterMarks> ednSemesterMarkses = new HashSet<EdnSemesterMarks>(
			0);
	private Set<ApplicantMarksDetails> applicantMarksDetailses = new HashSet<ApplicantMarksDetails>(
			0);
	private Set<CandidateMarks> candidateMarkses = new HashSet<CandidateMarks>(
			0);
	
	public NewEdnQualification() {
	}

	public NewEdnQualification(int id) {
		this.id = id;
	}

	public NewEdnQualification(int id, String createdBy,
			NewDocChecklist docChecklist,DocTypeExams docTypeExams, String modifiedBy,
			PersonalData personalData, College college,BigDecimal weightageAdjustedMarks,
			Integer noOfAttempts, Integer yearPassing,Integer monthPassing,
			BigDecimal marksObtained,BigDecimal percentage, University university, Date createdDate,
			Date lastModifiedDate, BigDecimal totalMarks,
			String institutionNameOthers,String previousRegNo, String universityOthers,State state,
			Set<EdnSemesterMarks> ednSemesterMarkses,
			Set<ApplicantMarksDetails> applicantMarksDetailses,
			Set<CandidateMarks> candidateMarkses,Boolean isOutsideIndia) {
		this.id = id;
		this.createdBy = createdBy;
		this.docChecklist = docChecklist;
		this.modifiedBy = modifiedBy;
		this.personalData = personalData;
		this.college = college;
		this.noOfAttempts = noOfAttempts;
		this.yearPassing = yearPassing;
		this.monthPassing = monthPassing;
		this.marksObtained = marksObtained;
		this.university = university;
		this.createdDate = createdDate;
		this.lastModifiedDate = lastModifiedDate;
		this.totalMarks = totalMarks;
		this.institutionNameOthers = institutionNameOthers;
		this.previousRegNo = previousRegNo;
		this.universityOthers = universityOthers;
		this.ednSemesterMarkses = ednSemesterMarkses;
		this.candidateMarkses = candidateMarkses;
		this.percentage = percentage;
		this.applicantMarksDetailses = applicantMarksDetailses;
		this.weightageAdjustedMarks = weightageAdjustedMarks;
		this.state=state;
		this.isOutsideIndia=isOutsideIndia;
		this.docTypeExams = docTypeExams;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public University getUniversity() {
		return university;
	}

	public void setUniversity(University university) {
		this.university = university;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}


	public NewDocChecklist getDocChecklist() {
		return docChecklist;
	}

	public void setDocChecklist(NewDocChecklist docChecklist) {
		this.docChecklist = docChecklist;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public PersonalData getPersonalData() {
		return this.personalData;
	}

	public void setPersonalData(PersonalData personalData) {
		this.personalData = personalData;
	}

	public Integer getNoOfAttempts() {
		return this.noOfAttempts;
	}

	public void setNoOfAttempts(Integer noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}

	public Integer getYearPassing() {
		return this.yearPassing;
	}

	public void setYearPassing(Integer yearPassing) {
		this.yearPassing = yearPassing;
	}

	public BigDecimal getMarksObtained() {
		return this.marksObtained;
	}

	public void setMarksObtained(BigDecimal marksObtained) {
		this.marksObtained = marksObtained;
	}

	

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public BigDecimal getTotalMarks() {
		return this.totalMarks;
	}

	public void setTotalMarks(BigDecimal totalMarks) {
		this.totalMarks = totalMarks;
	}

	public String getInstitutionNameOthers() {
		return this.institutionNameOthers;
	}

	public void setInstitutionNameOthers(String institutionNameOthers) {
		this.institutionNameOthers = institutionNameOthers;
	}

	public String getUniversityOthers() {
		return this.universityOthers;
	}

	public void setUniversityOthers(String universityOthers) {
		this.universityOthers = universityOthers;
	}

	public Set<EdnSemesterMarks> getEdnSemesterMarkses() {
		return this.ednSemesterMarkses;
	}

	public void setEdnSemesterMarkses(Set<EdnSemesterMarks> ednSemesterMarkses) {
		this.ednSemesterMarkses = ednSemesterMarkses;
	}

	public Set<CandidateMarks> getCandidateMarkses() {
		return this.candidateMarkses;
	}

	public void setCandidateMarkses(Set<CandidateMarks> candidateMarkses) {
		this.candidateMarkses = candidateMarkses;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getWeightageAdjustedMarks() {
		return weightageAdjustedMarks;
	}
	public Set<ApplicantMarksDetails> getApplicantMarksDetailses() {
		return this.applicantMarksDetailses;
	}

	public void setWeightageAdjustedMarks(BigDecimal weightageAdjustedMarks) {
		this.weightageAdjustedMarks = weightageAdjustedMarks;
	}

	public void setApplicantMarksDetailses(
			Set<ApplicantMarksDetails> applicantMarksDetailses) {
		this.applicantMarksDetailses = applicantMarksDetailses;
	}

	public Integer getMonthPassing() {
		return monthPassing;
	}

	public void setMonthPassing(Integer monthPassing) {
		this.monthPassing = monthPassing;
	}

	public String getPreviousRegNo() {
		return previousRegNo;
	}

	public void setPreviousRegNo(String previousRegNo) {
		this.previousRegNo = previousRegNo;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Boolean getIsOutsideIndia() {
		return isOutsideIndia;
	}

	public void setIsOutsideIndia(Boolean isOutsideIndia) {
		this.isOutsideIndia = isOutsideIndia;
	}
	public DocTypeExams getDocTypeExams() {
		return this.docTypeExams;
	}

	public void setDocTypeExams(DocTypeExams docTypeExams) {
		this.docTypeExams = docTypeExams;
	}

}
