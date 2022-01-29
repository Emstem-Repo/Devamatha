package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admin.ApplicantLateralDetailsTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.ApplicantTransferDetailsTO;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.AdmApplnTO;

public class InterviewResultEntryForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String applicationNumber;
	private String applicationYear;
	private String applicationId;
	private String interviewStatusId;
	private String interviewTypeId;
	private String referredById;
	private String gradeObtainedId;
	private String comments;
	private Map<Integer, String> interviewTypes;
	private Map<Integer, String> interviewStatus;
	private Map<Integer, String> referredBy;
	private Map<Integer, String> grades;
	private AdmApplnTO applicantDetails;
	private List<ApplnDocTO> uploadDocs;
	private String selectedPrefId;
	private String courseId;
	private List<ProgramTypeTO> programTypeList;
	private List<ApplicantMarkDetailsTO> semesterList;
	private CandidateMarkTO detailMark;
	private FormFile thefile;
	private String method;
	private String interviewSubroundId;
	private String subroundCount;
	private String isLanguageWiseMarks;
	
	private List<ApplicantLateralDetailsTO> lateralDetails;
	private String lateralUniversityName;
	private String lateralStateName;
	private String lateralInstituteAddress;
	
	private List<ApplicantTransferDetailsTO> transferDetails;
	private String transUnvrAppNo;
	private String transRegistationNo;
	private String transArrearDetail;
	private String transInstituteAddr;
	private int interviewersPerPanel;
	private String interviewDate;
	private String startingTimeHours;
	private String startingTimeMins;
	private String endingTimeHours;
	private String endingTimeMins;
	private boolean export;
	private String[] courses;
	private Map<Integer, Integer> intPrgCourseMap;
	private Map<String, Map<Integer, Integer>> interviewTypeMap;
	private Map<Integer, Integer> subroundMap;
	private boolean viewextradetails;
	private boolean viewParish;
	private Integer checkReligionId;
	
	
	public Integer getCheckReligionId() {
		return checkReligionId;
	}

	public void setCheckReligionId(Integer checkReligionId) {
		this.checkReligionId = checkReligionId;
	}

	public List<ApplicantLateralDetailsTO> getLateralDetails() {
		return lateralDetails;
	}

	public void setLateralDetails(List<ApplicantLateralDetailsTO> lateralDetails) {
		this.lateralDetails = lateralDetails;
	}

	public String getLateralUniversityName() {
		return lateralUniversityName;
	}

	public void setLateralUniversityName(String lateralUniversityName) {
		this.lateralUniversityName = lateralUniversityName;
	}

	public String getLateralStateName() {
		return lateralStateName;
	}

	public void setLateralStateName(String lateralStateName) {
		this.lateralStateName = lateralStateName;
	}

	public String getLateralInstituteAddress() {
		return lateralInstituteAddress;
	}

	public void setLateralInstituteAddress(String lateralInstituteAddress) {
		this.lateralInstituteAddress = lateralInstituteAddress;
	}

	public List<ApplicantTransferDetailsTO> getTransferDetails() {
		return transferDetails;
	}

	public void setTransferDetails(List<ApplicantTransferDetailsTO> transferDetails) {
		this.transferDetails = transferDetails;
	}

	public String getTransUnvrAppNo() {
		return transUnvrAppNo;
	}

	public void setTransUnvrAppNo(String transUnvrAppNo) {
		this.transUnvrAppNo = transUnvrAppNo;
	}

	public String getTransRegistationNo() {
		return transRegistationNo;
	}

	public void setTransRegistationNo(String transRegistationNo) {
		this.transRegistationNo = transRegistationNo;
	}

	public String getTransArrearDetail() {
		return transArrearDetail;
	}

	public void setTransArrearDetail(String transArrearDetail) {
		this.transArrearDetail = transArrearDetail;
	}

	public String getTransInstituteAddr() {
		return transInstituteAddr;
	}

	public void setTransInstituteAddr(String transInstituteAddr) {
		this.transInstituteAddr = transInstituteAddr;
	}

	public String getIsLanguageWiseMarks() {
		return isLanguageWiseMarks;
	}

	public void setIsLanguageWiseMarks(String isLanguageWiseMarks) {
		this.isLanguageWiseMarks = isLanguageWiseMarks;
	}

	public String getInterviewSubroundId() {
		return interviewSubroundId;
	}

	public void setInterviewSubroundId(String interviewSubroundId) {
		this.interviewSubroundId = interviewSubroundId;
	}

	public String getSubroundCount() {
		return subroundCount;
	}

	public void setSubroundCount(String subroundCount) {
		this.subroundCount = subroundCount;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}

	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}

	public List<ApplicantMarkDetailsTO> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<ApplicantMarkDetailsTO> semesterList) {
		this.semesterList = semesterList;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getSelectedPrefId() {
		return selectedPrefId;
	}

	public void setSelectedPrefId(String selectedPrefId) {
		this.selectedPrefId = selectedPrefId;
	}

	public List<ApplnDocTO> getUploadDocs() {
		return uploadDocs;
	}

	public void setUploadDocs(List<ApplnDocTO> uploadDocs) {
		this.uploadDocs = uploadDocs;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getReferredById() {
		return referredById;
	}

	public void setReferredById(String referredById) {
		this.referredById = referredById;
	}

	public String getGradeObtainedId() {
		return gradeObtainedId;
	}

	public void setGradeObtainedId(String gradeObtainedId) {
		this.gradeObtainedId = gradeObtainedId;
	}

	public Map<Integer, String> getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(Map<Integer, String> referredBy) {
		this.referredBy = referredBy;
	}

	public Map<Integer, String> getGrades() {
		return grades;
	}

	public void setGrades(Map<Integer, String> grades) {
		this.grades = grades;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public AdmApplnTO getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(AdmApplnTO applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	public String getInterviewStatusId() {
		return interviewStatusId;
	}

	public void setInterviewStatusId(String interviewStatusId) {
		this.interviewStatusId = interviewStatusId;
	}

	public String getInterviewTypeId() {
		return interviewTypeId;
	}

	public void setInterviewTypeId(String interviewTypeId) {
		this.interviewTypeId = interviewTypeId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Map<Integer, String> getInterviewTypes() {
		return interviewTypes;
	}

	public void setInterviewTypes(Map<Integer, String> interviewTypes) {
		this.interviewTypes = interviewTypes;
	}

	public Map<Integer, String> getInterviewStatus() {
		return interviewStatus;
	}

	public void setInterviewStatus(Map<Integer, String> interviewStatus) {
		this.interviewStatus = interviewStatus;
	}

	public String getApplicationYear() {
		return applicationYear;
	}

	public FormFile getThefile() {
		return thefile;
	}

	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public int getInterviewersPerPanel() {
		return interviewersPerPanel;
	}

	public void setInterviewersPerPanel(int interviewersPerPanel) {
		this.interviewersPerPanel = interviewersPerPanel;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public String getStartingTimeHours() {
		return startingTimeHours;
	}

	public void setStartingTimeHours(String startingTimeHours) {
		this.startingTimeHours = startingTimeHours;
	}

	public String getStartingTimeMins() {
		return startingTimeMins;
	}

	public void setStartingTimeMins(String startingTimeMins) {
		this.startingTimeMins = startingTimeMins;
	}

	public String getEndingTimeHours() {
		return endingTimeHours;
	}

	public void setEndingTimeHours(String endingTimeHours) {
		this.endingTimeHours = endingTimeHours;
	}

	public String getEndingTimeMins() {
		return endingTimeMins;
	}

	public void setEndingTimeMins(String endingTimeMins) {
		this.endingTimeMins = endingTimeMins;
	}

	public boolean isExport() {
		return export;
	}

	public void setExport(boolean export) {
		this.export = export;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String[] getCourses() {
		return courses;
	}

	public void setCourses(String[] courses) {
		this.courses = courses;
	}

	public Map<Integer, Integer> getIntPrgCourseMap() {
		return intPrgCourseMap;
	}

	public void setIntPrgCourseMap(Map<Integer, Integer> intPrgCourseMap) {
		this.intPrgCourseMap = intPrgCourseMap;
	}

	public Map<String, Map<Integer, Integer>> getInterviewTypeMap() {
		return interviewTypeMap;
	}

	public void setInterviewTypeMap(
			Map<String, Map<Integer, Integer>> interviewTypeMap) {
		this.interviewTypeMap = interviewTypeMap;
	}

	public Map<Integer, Integer> getSubroundMap() {
		return subroundMap;
	}

	public void setSubroundMap(Map<Integer, Integer> subroundMap) {
		this.subroundMap = subroundMap;
	}

	public boolean isViewextradetails() {
		return viewextradetails;
	}

	public void setViewextradetails(boolean viewextradetails) {
		this.viewextradetails = viewextradetails;
	}

	public boolean isViewParish() {
		return viewParish;
	}

	public void setViewParish(boolean viewParish) {
		this.viewParish = viewParish;
	}

	
}