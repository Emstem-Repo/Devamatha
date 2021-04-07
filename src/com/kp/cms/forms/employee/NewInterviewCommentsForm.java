package com.kp.cms.forms.employee;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.EmpAcheivement;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.employee.EmpEducationalDetailsTO;
import com.kp.cms.to.employee.EmpOnlineResumeTO;
import com.kp.cms.to.employee.InterviewRatingFactorTO;
import com.kp.cms.to.employee.NewInterviewCommentsDetailsTo;
import com.kp.cms.to.employee.NewInterviewCommentsTO;

public class NewInterviewCommentsForm extends BaseActionForm{
	private int id;
	private String name;
	private String applicationNo;
	private String email;
	private Integer noOfInternalInterviewers;
	private Integer noOfExternalInterviewers;
	private String comments;
	private String nameOfInternalInterviewer1;
	private String nameOfInternalInterviewer2;
	private String nameOfInternalInterviewer3;
	private String nameOfExternalInterviewer1;
	private String nameOfExternalInterviewer2;
	private String nameOfExternalInterviewer3;
	private String method;
	private int interviewCommentId;
	private int interviewCommentDetailsId;
	private String department;
	private int maxScore;
	private byte[] logo;
	private Map<Integer, Integer> map;
	private List<NewInterviewCommentsDetailsTo> newInterviewCommentsDetailsList;
	private List<NewInterviewCommentsTO> newInterviewCommentsList;
	private List<InterviewRatingFactorTO> interviewRatingFactorTOs;
	private List<EmpEducationalDetailsTO> educationalDetailsList;
	private List<EmpOnlineResumeTO> empOnlineResumeList;
	private List<EmpAcheivementTO> acheivementSet;
	//Added by sudhir
	private String nameOfInternalInterviewer4;
	private String nameOfInternalInterviewer5;
	private String nameOfInternalInterviewer6;
	private String nameOfExternalInterviewer4;
	private String nameOfExternalInterviewer5;
	private String nameOfExternalInterviewer6;
	private String joiningTime;
	private Boolean isInternal1;
	private Boolean isInternal2;
	private Boolean isInternal3;
	private Boolean isInternal4;
	private Boolean isInternal5;
	private Boolean isInternal6;
	private Boolean isExternal1;
	private Boolean isExternal2;
	private Boolean isExternal3;
	private Boolean isExternal4;
	private Boolean isExternal5;
	private Boolean isExternal6;
	private Boolean isTeachingStaff;
	private List<DepartmentEntryTO>  departmentMap;
	public String getJoiningTime() {
		return joiningTime;
	}

	public void setJoiningTime(String joiningTime) {
		this.joiningTime = joiningTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
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

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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
	public List<NewInterviewCommentsDetailsTo> getNewInterviewCommentsDetailsList() {
		return newInterviewCommentsDetailsList;
	}

	public void setNewInterviewCommentsDetailsList(
			List<NewInterviewCommentsDetailsTo> newInterviewCommentsDetailsList) {
		this.newInterviewCommentsDetailsList = newInterviewCommentsDetailsList;
	}

	public void setNewInterviewCommentsList(List<NewInterviewCommentsTO> newInterviewCommentsList) {
		this.newInterviewCommentsList = newInterviewCommentsList;
	}

	public List<InterviewRatingFactorTO> getInterviewRatingFactorTOs() {
		return interviewRatingFactorTOs;
	}

	public void setInterviewRatingFactorTOs(
			List<InterviewRatingFactorTO> interviewRatingFactorTOs) {
		this.interviewRatingFactorTOs = interviewRatingFactorTOs;
	}

	public List<NewInterviewCommentsTO> getNewInterviewCommentsList() {
		return newInterviewCommentsList;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDepartment() {
		return department;
	}

	public int getInterviewCommentId() {
		return interviewCommentId;
	}

	public void setInterviewCommentId(int interviewCommentId) {
		this.interviewCommentId = interviewCommentId;
	}

	public void setInterviewCommentDetailsId(int interviewCommentDetailsId) {
		this.interviewCommentDetailsId = interviewCommentDetailsId;
	}

	public int getInterviewCommentDetailsId() {
		return interviewCommentDetailsId;
	}

	public List<EmpEducationalDetailsTO> getEducationalDetailsList() {
		return educationalDetailsList;
	}

	public void setEducationalDetailsList(
			List<EmpEducationalDetailsTO> educationalDetailsList) {
		this.educationalDetailsList = educationalDetailsList;
	}

	public void setEmpOnlineResumeList(List<EmpOnlineResumeTO> empOnlineResumeList) {
		this.empOnlineResumeList = empOnlineResumeList;
	}

	public List<EmpOnlineResumeTO> getEmpOnlineResumeList() {
		return empOnlineResumeList;
	}

	

	public void setLogo(byte[] logo) {
		this.logo = logo;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setMap(Map<Integer, Integer> map) {
		this.map = map;
	}

	public Map<Integer, Integer> getMap() {
		return map;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setAcheivementSet(List<EmpAcheivementTO> acheivementSet) {
		this.acheivementSet = acheivementSet;
	}

	public List<EmpAcheivementTO> getAcheivementSet() {
		return acheivementSet;
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


	public Boolean getIsInternal1() {
		return isInternal1;
	}

	public void setIsInternal1(Boolean isInternal1) {
		this.isInternal1 = isInternal1;
	}

	public Boolean getIsInternal2() {
		return isInternal2;
	}

	public void setIsInternal2(Boolean isInternal2) {
		this.isInternal2 = isInternal2;
	}

	public Boolean getIsInternal3() {
		return isInternal3;
	}

	public void setIsInternal3(Boolean isInternal3) {
		this.isInternal3 = isInternal3;
	}

	public Boolean getIsInternal4() {
		return isInternal4;
	}

	public void setIsInternal4(Boolean isInternal4) {
		this.isInternal4 = isInternal4;
	}

	public Boolean getIsInternal5() {
		return isInternal5;
	}

	public void setIsInternal5(Boolean isInternal5) {
		this.isInternal5 = isInternal5;
	}

	public Boolean getIsInternal6() {
		return isInternal6;
	}

	public void setIsInternal6(Boolean isInternal6) {
		this.isInternal6 = isInternal6;
	}

	public Boolean getIsExternal1() {
		return isExternal1;
	}

	public void setIsExternal1(Boolean isExternal1) {
		this.isExternal1 = isExternal1;
	}

	public Boolean getIsExternal2() {
		return isExternal2;
	}

	public void setIsExternal2(Boolean isExternal2) {
		this.isExternal2 = isExternal2;
	}

	public Boolean getIsExternal3() {
		return isExternal3;
	}

	public void setIsExternal3(Boolean isExternal3) {
		this.isExternal3 = isExternal3;
	}

	public Boolean getIsExternal4() {
		return isExternal4;
	}

	public void setIsExternal4(Boolean isExternal4) {
		this.isExternal4 = isExternal4;
	}

	public Boolean getIsExternal5() {
		return isExternal5;
	}

	public void setIsExternal5(Boolean isExternal5) {
		this.isExternal5 = isExternal5;
	}

	public Boolean getIsExternal6() {
		return isExternal6;
	}

	public void setIsExternal6(Boolean isExternal6) {
		this.isExternal6 = isExternal6;
	}

	public Boolean getIsTeachingStaff() {
		return isTeachingStaff;
	}

	public void setIsTeachingStaff(Boolean isTeachingStaff) {
		this.isTeachingStaff = isTeachingStaff;
	}

	public List<DepartmentEntryTO> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(List<DepartmentEntryTO> departmentMap) {
		this.departmentMap = departmentMap;
	}

}
