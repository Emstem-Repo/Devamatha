package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CandidateSearchTO;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;

public class CandidateSearchForm extends BaseActionForm  {
//	modifications done by priyatham
	private String programTypeId;
	private String programId;
	private String courseId;
	private String appliedYear;
	private String nationalityId;	
	private String interviewResult;
	private String residentCategoryId;	
	private String religionId;
	private String subReligionId;	
	private String casteCategoryId;
	private Character belongsTo;
	private String gender;	
	private String bloodGroup;
	private String marksObtained;
	private String marksObtainedTO;
	private String weightage;
	private String weightageTO;
	private String university;
	private String institute;
	private String applicantName;
	private String birthCountry;
	private String birthState;
	private String interviewType;
	private String previousInterViewType;
	private String programTypeName;
	private String programName;
	private String courseName;	
	private String status;
	private String interviewStartDate;
	private String interviewEndDate;
	private String selectedCandidates[];
	private List<CandidateSearchTO> studentSearch;
	private String selectedColumnsArray[];
	private String unselectedColumnsArray[];
	private List<ConfigureColumnForReportTO> selectedColumnsList;
	private List<ConfigureColumnForReportTO> unselectedColumnsList;
	private List<ConfigureColumnForReportTO> fullList;	
	private String selectedIndex;
	private String mode;
	private String downloadExcel;
	private String downloadCSV;
	private String admissionDateFrom;
	private String admissionDateTo;
	
	private String feeStatus;
	
	//Mary Addition
	private String appliedDate;
	private String challanPaymentDate;
	private String feeChallanDate;
//	private String feePaidDate;
	private String feeChallanNo;
	private String totalFeePaid; 
	private String isHandicaped;
	private String handicapDetails;
	private String totalWorkExpYearMonths;
	private String appliedDateFrom;
	private String appliedDateTo;
	private String centerId;
	
	public String getInterviewStartDate() {
		return interviewStartDate;
	}
	public void setInterviewStartDate(String interviewStartDate) {
		this.interviewStartDate = interviewStartDate;
	}
	public String getInterviewEndDate() {
		return interviewEndDate;
	}
	public void setInterviewEndDate(String interviewEndDate) {
		this.interviewEndDate = interviewEndDate;
	}
	public String getMarksObtainedTO() {
		return marksObtainedTO;
	}
	public void setMarksObtainedTO(String marksObtainedTO) {
		this.marksObtainedTO = marksObtainedTO;
	}
	public String getWeightageTO() {
		return weightageTO;
	}
	public void setWeightageTO(String weightageTO) {
		this.weightageTO = weightageTO;
	}
	public String getInterviewResult() {
		return interviewResult;
	}
	public void setInterviewResult(String interviewResult) {
		this.interviewResult = interviewResult;
	}
	public String getProgramTypeId() {
		return programTypeId;
	}
	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public String getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getResidentCategoryId() {
		return residentCategoryId;
	}
	public void setResidentCategoryId(String residentCategoryId) {
		this.residentCategoryId = residentCategoryId;
	}
	public String getReligionId() {
		return religionId;
	}
	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
	public String getSubReligionId() {
		return subReligionId;
	}
	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}
	public String getCasteCategoryId() {
		return casteCategoryId;
	}
	public void setCasteCategoryId(String casteCategoryId) {
		this.casteCategoryId = casteCategoryId;
	}
	public Character getBelongsTo() {
		return belongsTo;
	}
	public void setBelongsTo(Character belongsTo) {
		this.belongsTo = belongsTo;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	public String getMarksObtained() {
		return marksObtained;
	}
	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getUniversity() {
		return university;
	}
	public void setUniversity(String university) {
		this.university = university;
	}
	public String getInstitute() {
		return institute;
	}
	public void setInstitute(String institute) {
		this.institute = institute;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getBirthCountry() {
		return birthCountry;
	}
	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}
	public String getBirthState() {
		return birthState;
	}
	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}
	public String getInterviewType() {
		return interviewType;
	}
	public void setInterviewType(String interviewType) {
		this.interviewType = interviewType;
	}
	public String getPreviousInterViewType() {
		return previousInterViewType;
	}
	public void setPreviousInterViewType(String previousInterViewType) {
		this.previousInterViewType = previousInterViewType;
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
	public String[] getSelectedCandidates() {
		return selectedCandidates;
	}
	public void setSelectedCandidates(String[] selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}
	public List<CandidateSearchTO> getStudentSearch() {
		return studentSearch;
	}
	public void setStudentSearch(List<CandidateSearchTO> studentSearch) {
		this.studentSearch = studentSearch;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		if(selectedIndex != null && selectedIndex.equals("-1")){
			this.selectedColumnsList = null;
		}
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		
		this.programTypeId = null;
		this.programId = null;
		this.courseId = null;
		this.interviewResult = null;
		super.setYear(null);
		this.nationalityId = null;
		this.residentCategoryId = null;
		this.religionId = null;
		this.subReligionId = null;
		this.casteCategoryId = null;
		this.belongsTo = null;
		this.gender = null;
		this.bloodGroup = null;
		this.marksObtained = null;
		this.marksObtainedTO = null;
		this.weightage = null;
		this.weightageTO = null;
		this.university = null;
		this.institute = null;
		this.applicantName = null;
		this.birthCountry = null;
		this.birthState = null;
		this.interviewType = null;
		this.status = null;
		this.interviewStartDate = null;
		this.interviewEndDate = null;
		this.mode = null;
		this.downloadExcel = null;
		this.downloadCSV = null;
		this.selectedColumnsList = null;
		this.unselectedColumnsList = null;
		this.fullList = null;
		this.selectedColumnsArray = null;
		this.unselectedColumnsArray = null;
		this.admissionDateFrom = null;
		this.admissionDateTo = null;
		this.feeStatus=null;
		this.appliedDateFrom = null;
		this.appliedDateTo = null;
		this.centerId = null;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	public List<ConfigureColumnForReportTO> getSelectedColumnsList() {
		return selectedColumnsList;
	}
	public void setSelectedColumnsList(
			List<ConfigureColumnForReportTO> selectedColumnsList) {
		this.selectedColumnsList = selectedColumnsList;
	}
	public List<ConfigureColumnForReportTO> getUnselectedColumnsList() {
		return unselectedColumnsList;
	}
	public void setUnselectedColumnsList(
			List<ConfigureColumnForReportTO> unselectedColumnsList) {
		this.unselectedColumnsList = unselectedColumnsList;
	}
	public String getSelectedIndex() {
		return selectedIndex;
	}
	public void setSelectedIndex(String selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String[] getSelectedColumnsArray() {
		return selectedColumnsArray;
	}
	public String[] getUnselectedColumnsArray() {
		return unselectedColumnsArray;
	}
	public void setSelectedColumnsArray(String[] selectedColumnsArray) {
		this.selectedColumnsArray = selectedColumnsArray;
	}
	public void setUnselectedColumnsArray(String[] unselectedColumnsArray) {
		this.unselectedColumnsArray = unselectedColumnsArray;
	}
	public List<ConfigureColumnForReportTO> getFullList() {
		return fullList;
	}
	public void setFullList(List<ConfigureColumnForReportTO> fullList) {
		this.fullList = fullList;
	}
	public String getDownloadExcel() {
		return downloadExcel;
	}
	public String getDownloadCSV() {
		return downloadCSV;
	}
	public void setDownloadExcel(String downloadExcel) {
		this.downloadExcel = downloadExcel;
	}
	public void setDownloadCSV(String downloadCSV) {
		this.downloadCSV = downloadCSV;
	}
	public String getAdmissionDateFrom() {
		return admissionDateFrom;
	}
	public void setAdmissionDateFrom(String admissionDateFrom) {
		this.admissionDateFrom = admissionDateFrom;
	}
	public String getAdmissionDateTo() {
		return admissionDateTo;
	}
	public void setAdmissionDateTo(String admissionDateTo) {
		this.admissionDateTo = admissionDateTo;
	}
	public String getFeeStatus() {
		return feeStatus;
	}
	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public String getChallanPaymentDate() {
		return challanPaymentDate;
	}
	public void setChallanPaymentDate(String challanPaymentDate) {
		this.challanPaymentDate = challanPaymentDate;
	}
	public String getFeeChallanDate() {
		return feeChallanDate;
	}
	public void setFeeChallanDate(String feeChallanDate) {
		this.feeChallanDate = feeChallanDate;
	}
	
	public String getFeeChallanNo() {
		return feeChallanNo;
	}
	public void setFeeChallanNo(String feeChallanNo) {
		this.feeChallanNo = feeChallanNo;
	}
	public String getTotalFeePaid() {
		return totalFeePaid;
	}
	public void setTotalFeePaid(String totalFeePaid) {
		this.totalFeePaid = totalFeePaid;
	}
	public String getIsHandicaped() {
		return isHandicaped;
	}
	public void setIsHandicaped(String isHandicaped) {
		this.isHandicaped = isHandicaped;
	}
	public String getHandicapDetails() {
		return handicapDetails;
	}
	public void setHandicapDetails(String handicapDetails) {
		this.handicapDetails = handicapDetails;
	}
	public String getAppliedDateFrom() {
		return appliedDateFrom;
	}
	public void setAppliedDateFrom(String appliedDateFrom) {
		this.appliedDateFrom = appliedDateFrom;
	}
	public String getAppliedDateTo() {
		return appliedDateTo;
	}
	public void setAppliedDateTo(String appliedDateTo) {
		this.appliedDateTo = appliedDateTo;
	}
	public String getTotalWorkExpYearMonths() {
		return totalWorkExpYearMonths;
	}
	public void setTotalWorkExpYearMonths(String totalWorkExpYearMonths) {
		this.totalWorkExpYearMonths = totalWorkExpYearMonths;
	}
	public String getCenterId() {
		return centerId;
	}
	public void setCenterId(String centerId) {
		this.centerId = centerId;
	}
	
}
