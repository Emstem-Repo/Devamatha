package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;

public class DownloadApplicationForm extends BaseActionForm{
	private String startDate;
	private String endDate;
	private String employeeName;
	private String departmentName;
	private String employeeId;
	private List<Department> departmentList;
	private List<Designation> designationList;
	private String designationId;
	private List<EmpQualificationLevel> qualificationList;
	private String qualificationId;
	private List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs; 
	private String printPage;
	private String method;
	//to resume print
	private List<DownloadEmployeeResumeTO> tos;
	private List<EmpOnlineEducationalDetailsTO> empEducationalDetails;
	private List<EmpOnlinePreviousExperienceTO> teachingExperience;
	private List<EmpOnlinePreviousExperienceTO> industryExperience;
	private List<EmpOnlineEducationalDetailsTO> additionalQualification;
	private String applicationNo;
	private Boolean publicationDetailsPresent;
	private String name;
	private String applnNo;
	private String status;
	private String statusDate;
	private Boolean isCjc;
	private Map<Integer,String> empSubjects;
	private String empSubjectId;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public List<Department> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}
	public List<Designation> getDesignationList() {
		return designationList;
	}
	public void setDesignationList(List<Designation> designationList) {
		this.designationList = designationList;
	}
	public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public List<EmpQualificationLevel> getQualificationList() {
		return qualificationList;
	}
	public void setQualificationList(List<EmpQualificationLevel> qualificationList) {
		this.qualificationList = qualificationList;
	}
	public String getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}
	public List<DownloadEmployeeResumeTO> getDownloadEmployeeResumeTOs() {
		return downloadEmployeeResumeTOs;
	}
	public void setDownloadEmployeeResumeTOs(
			List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs) {
		this.downloadEmployeeResumeTOs = downloadEmployeeResumeTOs;
	}
	public String getPrintPage() {
		return printPage;
	}
	public void setPrintPage(String printPage) {
		this.printPage = printPage;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<DownloadEmployeeResumeTO> getTos() {
		return tos;
	}
	public void setTos(List<DownloadEmployeeResumeTO> tos) {
		this.tos = tos;
	}
	public List<EmpOnlineEducationalDetailsTO> getEmpEducationalDetails() {
		return empEducationalDetails;
	}
	public void setEmpEducationalDetails(
			List<EmpOnlineEducationalDetailsTO> empEducationalDetails) {
		this.empEducationalDetails = empEducationalDetails;
	}
	public List<EmpOnlinePreviousExperienceTO> getTeachingExperience() {
		return teachingExperience;
	}
	public void setTeachingExperience(
			List<EmpOnlinePreviousExperienceTO> teachingExperience) {
		this.teachingExperience = teachingExperience;
	}
	public List<EmpOnlinePreviousExperienceTO> getIndustryExperience() {
		return industryExperience;
	}
	public void setIndustryExperience(
			List<EmpOnlinePreviousExperienceTO> industryExperience) {
		this.industryExperience = industryExperience;
	}
	public List<EmpOnlineEducationalDetailsTO> getAdditionalQualification() {
		return additionalQualification;
	}
	public void setAdditionalQualification(
			List<EmpOnlineEducationalDetailsTO> additionalQualification) {
		this.additionalQualification = additionalQualification;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public Boolean getPublicationDetailsPresent() {
		return publicationDetailsPresent;
	}
	public void setPublicationDetailsPresent(Boolean publicationDetailsPresent) {
		this.publicationDetailsPresent = publicationDetailsPresent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDate() {
		return statusDate;
	}
	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}
	public void resetFields() {
		this.startDate=null;
		this.endDate=null;
		this.employeeId="0";
		this.employeeName=null;
		this.departmentName=null;
		this.departmentList=null;
		//this.designationList=null;
		this.designationId = null;
		this.printPage = null;
		this.downloadEmployeeResumeTOs = null;
		//this.emailIds = null;
		this.applnNo=null;
		this.name=null;
		this.qualificationId=null;
		this.qualificationList=null;
		super.setDepartmentId(null);
	}
	public Boolean getIsCjc() {
		return isCjc;
	}
	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
	}
	public Map<Integer, String> getEmpSubjects() {
		return empSubjects;
	}
	public void setEmpSubjects(Map<Integer, String> empSubjects) {
		this.empSubjects = empSubjects;
	}
	public String getEmpSubjectId() {
		return empSubjectId;
	}
	public void setEmpSubjectId(String empSubjectId) {
		this.empSubjectId = empSubjectId;
	} 
}
