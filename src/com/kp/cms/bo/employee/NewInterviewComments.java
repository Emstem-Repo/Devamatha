	package com.kp.cms.bo.employee;
	
	import java.util.Date;
	import java.util.HashSet;
	import java.util.Set;
	
	import com.kp.cms.bo.admin.EmpOnlineResume;
	
	public class NewInterviewComments {
	private int id;
	private EmpOnlineResume empOnlineResume;
	private Integer noOfInternalInterviewers;
	private Integer noOfExternalInterviewers;
	private String nameOfExternalInterviewer1;
	private String nameOfExternalInterviewer2;
	private String nameOfExternalInterviewer3;
	private String nameOfInternalInterviewer1;
	private String nameOfInternalInterviewer2;
	private String nameOfInternalInterviewer3;
	private String comments;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date lastModifiedDate;
	private Boolean isActive;
	private Set<NewInterviewCommentsDetails> newInterviewCommentsDetails=new HashSet<NewInterviewCommentsDetails>(0);
	//Added 
	private String nameOfExternalInterviewer4;
	private String nameOfExternalInterviewer5;
	private String nameOfExternalInterviewer6;
	private String nameOfInternalInterviewer4;
	private String nameOfInternalInterviewer5;
	private String nameOfInternalInterviewer6;
	private String joiningTime;
	public NewInterviewComments() {
		super();
	}
	
	public NewInterviewComments(int id, EmpOnlineResume empOnlineResume,
			Integer noOfInternalInterviewers, Integer noOfExternalInterviewers,
			String nameOfExternalInterviewer1, String nameOfExternalInterviewer2,
			String nameOfExternalInterviewer3, String nameOfInternalInterviewer1,
			String nameOfInternalInterviewer2, String nameOfInternalInterviewer3,
			String comments, String createdBy, Date createdDate, String modifiedBy,
			Date lastModifiedDate, Boolean isActive,Set<NewInterviewCommentsDetails> newInterviewCommentsDetails,
			String nameOfExternalInterviewer4,String nameOfExternalInterviewer5,String nameOfExternalInterviewer6,
			String nameOfInternalInterviewer4, String nameOfInternalInterviewer5,String nameOfInternalInterviewer6,String joiningTime) {
		super();
		this.id = id;
		this.empOnlineResume = empOnlineResume;
		this.noOfInternalInterviewers = noOfInternalInterviewers;
		this.noOfExternalInterviewers = noOfExternalInterviewers;
		this.nameOfExternalInterviewer1 = nameOfExternalInterviewer1;
		this.nameOfExternalInterviewer2 = nameOfExternalInterviewer2;
		this.nameOfExternalInterviewer3 = nameOfExternalInterviewer3;
		this.nameOfInternalInterviewer1 = nameOfInternalInterviewer1;
		this.nameOfInternalInterviewer2 = nameOfInternalInterviewer2;
		this.nameOfInternalInterviewer3 = nameOfInternalInterviewer3;
		this.comments = comments;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.newInterviewCommentsDetails = newInterviewCommentsDetails;
		this.nameOfExternalInterviewer4 = nameOfExternalInterviewer4;
		this.nameOfExternalInterviewer5 = nameOfExternalInterviewer5;
		this.nameOfExternalInterviewer6= nameOfExternalInterviewer6;
		this.nameOfInternalInterviewer4 = nameOfInternalInterviewer4;
		this.nameOfInternalInterviewer5 = nameOfInternalInterviewer5;
		this.nameOfInternalInterviewer6 = nameOfInternalInterviewer6;
		this.joiningTime = joiningTime;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public EmpOnlineResume getEmpOnlineResume() {
		return empOnlineResume;
	}
	
	public void setEmpOnlineResume(EmpOnlineResume empOnlineResume) {
		this.empOnlineResume = empOnlineResume;
	}
	
	public Integer getNoOfInternalInterviewers() {
		return noOfInternalInterviewers;
	}
	
	public void setNoOfInternalInterviewers(Integer noOfInternalInterviewers) {
		this.noOfInternalInterviewers = noOfInternalInterviewers;
	}
	
	public Integer getNoOfExternalInterviewers() {
		return noOfExternalInterviewers;
	}
	
	public void setNoOfExternalInterviewers(Integer noOfExternalInterviewers) {
		this.noOfExternalInterviewers = noOfExternalInterviewers;
	}
	
	public String getNameOfExternalInterviewer1() {
		return nameOfExternalInterviewer1;
	}
	
	public void setNameOfExternalInterviewer1(String nameOfExternalInterviewer1) {
		this.nameOfExternalInterviewer1 = nameOfExternalInterviewer1;
	}
	
	public String getNameOfExternalInterviewer2() {
		return nameOfExternalInterviewer2;
	}
	
	public void setNameOfExternalInterviewer2(String nameOfExternalInterviewer2) {
		this.nameOfExternalInterviewer2 = nameOfExternalInterviewer2;
	}
	
	public String getNameOfExternalInterviewer3() {
		return nameOfExternalInterviewer3;
	}
	
	public void setNameOfExternalInterviewer3(String nameOfExternalInterviewer3) {
		this.nameOfExternalInterviewer3 = nameOfExternalInterviewer3;
	}
	
	public String getNameOfInternalInterviewer1() {
		return nameOfInternalInterviewer1;
	}
	
	public void setNameOfInternalInterviewer1(String nameOfInternalInterviewer1) {
		this.nameOfInternalInterviewer1 = nameOfInternalInterviewer1;
	}
	
	public String getNameOfInternalInterviewer2() {
		return nameOfInternalInterviewer2;
	}
	
	public void setNameOfInternalInterviewer2(String nameOfInternalInterviewer2) {
		this.nameOfInternalInterviewer2 = nameOfInternalInterviewer2;
	}
	
	public String getNameOfInternalInterviewer3() {
		return nameOfInternalInterviewer3;
	}
	
	public void setNameOfInternalInterviewer3(String nameOfInternalInterviewer3) {
		this.nameOfInternalInterviewer3 = nameOfInternalInterviewer3;
	}
	
	public String getComments() {
		return comments;
	}
	
	public void setComments(String comments) {
		this.comments = comments;
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
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	
	public Boolean getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public void setNewInterviewCommentsDetails(
			Set<NewInterviewCommentsDetails> newInterviewCommentsDetails) {
		this.newInterviewCommentsDetails = newInterviewCommentsDetails;
	}

	public Set<NewInterviewCommentsDetails> getNewInterviewCommentsDetails() {
		return newInterviewCommentsDetails;
	}

	public String getNameOfExternalInterviewer4() {
		return nameOfExternalInterviewer4;
	}

	public void setNameOfExternalInterviewer4(String nameOfExternalInterviewer4) {
		this.nameOfExternalInterviewer4 = nameOfExternalInterviewer4;
	}

	public String getNameOfExternalInterviewer5() {
		return nameOfExternalInterviewer5;
	}

	public void setNameOfExternalInterviewer5(String nameOfExternalInterviewer5) {
		this.nameOfExternalInterviewer5 = nameOfExternalInterviewer5;
	}

	public String getNameOfExternalInterviewer6() {
		return nameOfExternalInterviewer6;
	}

	public void setNameOfExternalInterviewer6(String nameOfExternalInterviewer6) {
		this.nameOfExternalInterviewer6 = nameOfExternalInterviewer6;
	}

	public String getNameOfInternalInterviewer4() {
		return nameOfInternalInterviewer4;
	}

	public void setNameOfInternalInterviewer4(String nameOfInternalInterviewer4) {
		this.nameOfInternalInterviewer4 = nameOfInternalInterviewer4;
	}

	public String getNameOfInternalInterviewer5() {
		return nameOfInternalInterviewer5;
	}

	public void setNameOfInternalInterviewer5(String nameOfInternalInterviewer5) {
		this.nameOfInternalInterviewer5 = nameOfInternalInterviewer5;
	}

	public String getNameOfInternalInterviewer6() {
		return nameOfInternalInterviewer6;
	}

	public void setNameOfInternalInterviewer6(String nameOfInternalInterviewer6) {
		this.nameOfInternalInterviewer6 = nameOfInternalInterviewer6;
	}

	public void setJoiningTime(String joiningTime) {
		this.joiningTime = joiningTime;
	}

	public String getJoiningTime() {
		return joiningTime;
	}
	
	}
