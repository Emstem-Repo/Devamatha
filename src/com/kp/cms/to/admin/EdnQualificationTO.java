package com.kp.cms.to.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.handlers.admin.CandidateMarkTO;

public class EdnQualificationTO implements Serializable,Comparable<EdnQualificationTO> {
	private int id;

	private String universityId;
	private int docCheckListId;
	private int boardId;
	private String institutionName;
	private String institutionId;
	private String stateId;
	private boolean outsideIndia;
	private String stateName;
	private int noOfAttempts;
	private int yearPassing;
	private int monthPassing;
	private String passGrade;
	private String universityOthers;
	private int countId;
	private String docName;
	private boolean consolidated;
	private boolean semesterWise;
	private boolean lastExam;
	private String marksObtained;
	private String totalMarks;
	private String otherInstitute;
	private CandidateMarkTO detailmark;
	private String universityName;
	private List<UniversityTO> universityList;
	private List<CollegeTO> instituteList;
	Set<ApplicantMarkDetailsTO> semesterList;
	private String previousRegNo;
	private boolean isLanguage;
	private DocChecklist orignalCheckList;
	private String createdBy;
	private Date createdDate;
	private String percentage;
	private List<DocTypeExamsTO> examTos;
	private String selectedExamId;
	private String selectedExamName;
	private boolean examRequired;
	private boolean examConfigured;
	private int docTypeId;
	List<ApplicantMarkDetailsTO> semesters;
	private Integer displayOrder;
	private String ugPattern;
	//new
	private String isHigherEdu;
	private String studentId;
	private boolean blockedMarks;
	private List<AdmSubjectMarkForRankTO> subjectMarkForRankTO;
	
	public List<CollegeTO> getInstituteList() {
		return instituteList;
	}

	public void setInstituteList(List<CollegeTO> instituteList) {
		this.instituteList = instituteList;
	}

	public List<UniversityTO> getUniversityList() {
		return universityList;
	}

	public void setUniversityList(List<UniversityTO> universityList) {
		this.universityList = universityList;
	}
	
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public String getUniversityName() {
		return universityName;
	}
	public void setUniversityName(String universityName) {
		this.universityName = universityName;
	}
	public String getOtherInstitute() {
		return otherInstitute;
	}
	public void setOtherInstitute(String otherInstitute) {
		this.otherInstitute = otherInstitute;
	}
	public CandidateMarkTO getDetailmark() {
		return detailmark;
	}
	public void setDetailmark(CandidateMarkTO detailmark) {
		this.detailmark = detailmark;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getTotalMarks() {
		return totalMarks;
	}
	public void setTotalMarks(String totalMarks) {
		this.totalMarks = totalMarks;
	}
	public boolean isConsolidated() {
		return consolidated;
	}
	public void setConsolidated(boolean consolidated) {
		this.consolidated = consolidated;
	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUniversityId() {
		return universityId;
	}
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	
	public int getDocCheckListId() {
		return docCheckListId;
	}
	public void setDocCheckListId(int docCheckListId) {
		this.docCheckListId = docCheckListId;
	}
	public int getBoardId() {
		return boardId;
	}
	public void setBoardId(int boardId) {
		this.boardId = boardId;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public int getNoOfAttempts() {
		return noOfAttempts;
	}
	public void setNoOfAttempts(int noOfAttempts) {
		this.noOfAttempts = noOfAttempts;
	}
	public int getYearPassing() {
		return yearPassing;
	}
	public void setYearPassing(int yearPassing) {
		this.yearPassing = yearPassing;
	}
	public String getPassGrade() {
		return passGrade;
	}
	public void setPassGrade(String passGrade) {
		this.passGrade = passGrade;
	}
	public String getUniversityOthers() {
		return universityOthers;
	}
	public void setUniversityOthers(String universityOthers) {
		this.universityOthers = universityOthers;
	}
	public boolean isSemesterWise() {
		return semesterWise;
	}
	public void setSemesterWise(boolean semesterWise) {
		this.semesterWise = semesterWise;
	}
	public boolean isLastExam() {
		return lastExam;
	}
	public void setLastExam(boolean lastExam) {
		this.lastExam = lastExam;
	}

	public Set<ApplicantMarkDetailsTO> getSemesterList() {
		return semesterList;
	}

	public int getCountId() {
		return countId;
	}

	public void setCountId(int countId) {
		this.countId = countId;
	}

	public void setSemesterList(Set<ApplicantMarkDetailsTO> semesterList) {
		this.semesterList = semesterList;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getMonthPassing() {
		return monthPassing;
	}

	public void setMonthPassing(int monthPassing) {
		this.monthPassing = monthPassing;
	}

	public String getPreviousRegNo() {
		return previousRegNo;
	}

	public void setPreviousRegNo(String previousRegNo) {
		this.previousRegNo = previousRegNo;
	}

	public boolean isLanguage() {
		return isLanguage;
	}

	public void setLanguage(boolean isLanguage) {
		this.isLanguage = isLanguage;
	}

	public DocChecklist getOrignalCheckList() {
		return orignalCheckList;
	}

	public void setOrignalCheckList(DocChecklist orignalCheckList) {
		this.orignalCheckList = orignalCheckList;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public boolean isOutsideIndia() {
		return outsideIndia;
	}

	public void setOutsideIndia(boolean outsideIndia) {
		this.outsideIndia = outsideIndia;
	}

	public List<DocTypeExamsTO> getExamTos() {
		return examTos;
	}

	public void setExamTos(List<DocTypeExamsTO> examTos) {
		this.examTos = examTos;
	}

	public String getSelectedExamId() {
		return selectedExamId;
	}

	public void setSelectedExamId(String selectedExamId) {
		this.selectedExamId = selectedExamId;
	}

	public boolean isExamRequired() {
		return examRequired;
	}

	public void setExamRequired(boolean examRequired) {
		this.examRequired = examRequired;
	}

	public String getSelectedExamName() {
		return selectedExamName;
	}

	public void setSelectedExamName(String selectedExamName) {
		this.selectedExamName = selectedExamName;
	}

	public boolean isExamConfigured() {
		return examConfigured;
	}

	public void setExamConfigured(boolean examConfigured) {
		this.examConfigured = examConfigured;
	}

	public int getDocTypeId() {
		return docTypeId;
	}

	public void setDocTypeId(int docTypeId) {
		this.docTypeId = docTypeId;
	}

	public List<ApplicantMarkDetailsTO> getSemesters() {
		return semesters;
	}

	public void setSemesters(List<ApplicantMarkDetailsTO> semesters) {
		this.semesters = semesters;
	}
	
	public Integer getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}


	public String getUgPattern() {
		return ugPattern;
	}

	public void setUgPattern(String ugPattern) {
		this.ugPattern = ugPattern;
	}
	
	public String getIsHigherEdu() {
		return isHigherEdu;
	}

	public void setIsHigherEdu(String isHigherEdu) {
		this.isHigherEdu = isHigherEdu;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public boolean isBlockedMarks() {
		return blockedMarks;
	}

	public void setBlockedMarks(boolean blockedMarks) {
		this.blockedMarks = blockedMarks;
	}

	public List<AdmSubjectMarkForRankTO> getSubjectMarkForRankTO() {
		return subjectMarkForRankTO;
	}

	public void setSubjectMarkForRankTO(
			List<AdmSubjectMarkForRankTO> subjectMarkForRankTO) {
		this.subjectMarkForRankTO = subjectMarkForRankTO;
	}

	@Override
	public int compareTo(EdnQualificationTO arg0) {
		if(arg0!=null && this!=null)
		{
				return String.valueOf(this.getYearPassing()).compareTo(String.valueOf(arg0.getYearPassing()));
		}else
		return 0;
	}


	/*@Override
	public int compareTo(EdnQualificationTO arg0) {
		if(arg0!=null && this!=null && arg0.getDisplayOrder()!=null && this.getDisplayOrder()!=null )
		{
				return this.getDisplayOrder().compareTo(arg0.getDisplayOrder());
		}else
		return 0;
		//this is for ascending order
		if(arg0!=null && this!=null ){
			if (arg0.getDisplayOrder() != null && arg0.getDisplayOrder()!=0 && this.getDisplayOrder()!=null && this.getDisplayOrder()!=0){
				if(this.getDisplayOrder() >  arg0.getDisplayOrder())
					return 1;
				else if(this.getDisplayOrder() <  arg0.getDisplayOrder())
					return -1;
				else
					return 0;
			}	
		
		//raghu chande this is for descending order
		if(arg0!=null && this!=null ){
		if (arg0.getDisplayOrder() != null && arg0.getDisplayOrder()!=0 && this.getDisplayOrder()!=null && this.getDisplayOrder()!=0){
			if(arg0.getDisplayOrder() >  this.getDisplayOrder())
				return 1;
			else if(arg0.getDisplayOrder() <  this.getDisplayOrder())
				return -1;
			else
				return 0;
		}		

	}
		return 0;
	}*/

}
