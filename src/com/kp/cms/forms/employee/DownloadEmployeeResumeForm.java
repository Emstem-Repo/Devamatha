package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Designation;
import com.kp.cms.bo.admin.EmpQualificationLevel;
import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;
import com.kp.cms.bo.employee.EmployeeSubject;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.DownloadEmployeeResumeTO;
import com.kp.cms.to.admin.EmpOnlineEducationalDetailsTO;
import com.kp.cms.to.admin.EmpOnlinePreviousExperienceTO;
@SuppressWarnings("serial")
public class DownloadEmployeeResumeForm extends BaseActionForm{
	
	private String startDate;
	private String endDate;
	private String employeeName;
	private String departmentName;
	private String employeeId;
	private List<Department> departmentList;
	private List<Designation> designationList;
	private String departmentId;
	private String designationId;
	private List<EmpQualificationLevel> qualificationList;
	private String qualificationId;
	private List<DownloadEmployeeResumeTO> downloadEmployeeResumeTOs; 
	private String printPage;
	private String method;
	private String emailIds;
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
	private String mailBody;
	private String status;
	private String statusDate; 
	private List<String> appNosList;
	private Map<Integer,String> usersMap;
	private Map<Integer,String> usersMapWithEmails;
	private String[] selectedUserIdsArray;
	private String[] userIdsArray;
	private boolean clear;
	private Boolean isCjc;
	private Map<Integer,String> empSubjects;
	private String empSubjectId;
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	public void resetFields() {
		this.startDate=null;
		this.endDate=null;
		this.employeeId="0";
		this.employeeName=null;
		this.departmentName=null;
		//this.departmentList=null;
		this.departmentId = null;
		this.designationList=null;
		this.designationId = null;
		this.printPage = null;
		this.downloadEmployeeResumeTOs = null;
		//this.emailIds = null;
		this.applnNo=null;
		this.name=null;
		this.qualificationId=null;
		this.usersMap=null;
		this.usersMapWithEmails=null;
		this.selectedUserIdsArray = null;
		this.userIdsArray=null;
	}
	public void resetValues(){
		this.applicationNo =null;
		this.statusDate = null;
	}
	
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
	public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
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
	public String getEmailIds() {
		return emailIds;
	}
	public void setEmailIds(String emailIds) {
		this.emailIds = emailIds;
	}
	public List<EmpOnlineEducationalDetailsTO> getEmpEducationalDetails() {
		return empEducationalDetails;
	}
	public void setEmpEducationalDetails(
			List<EmpOnlineEducationalDetailsTO> empEducationalDetails) {
		this.empEducationalDetails = empEducationalDetails;
	}
	public List<DownloadEmployeeResumeTO> getTos() {
		return tos;
	}
	public void setTos(List<DownloadEmployeeResumeTO> tos) {
		this.tos = tos;
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
	public String getMailBody() {
		return mailBody;
	}
	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
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
	public List<String> getAppNosList() {
		return appNosList;
	}
	public void setAppNosList(List<String> appNosList) {
		this.appNosList = appNosList;
	}
	public Map<Integer, String> getUsersMap() {
		return usersMap;
	}
	public void setUsersMap(Map<Integer, String> usersMap) {
		this.usersMap = usersMap;
	}
	public Map<Integer, String> getUsersMapWithEmails() {
		return usersMapWithEmails;
	}
	public void setUsersMapWithEmails(Map<Integer, String> usersMapWithEmails) {
		this.usersMapWithEmails = usersMapWithEmails;
	}
	public String[] getSelectedUserIdsArray() {
		return selectedUserIdsArray;
	}
	public void setSelectedUserIdsArray(String[] selectedUserIdsArray) {
		this.selectedUserIdsArray = selectedUserIdsArray;
	}
	public String[] getUserIdsArray() {
		return userIdsArray;
	}
	public void setUserIdsArray(String[] userIdsArray) {
		this.userIdsArray = userIdsArray;
	}
	public boolean isClear() {
		return clear;
	}
	public void setClear(boolean clear) {
		this.clear = clear;
	}
	public Boolean getIsCjc() {
		return isCjc;
	}
	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
	}
	public Map<Integer,String> getEmpSubjects() {
		return empSubjects;
	}
	public void setEmpSubjects(Map<Integer,String> empSubjects) {
		this.empSubjects = empSubjects;
	}
	public String getEmpSubjectId() {
		return empSubjectId;
	}
	public void setEmpSubjectId(String empSubjectId) {
		this.empSubjectId = empSubjectId;
	}
}
