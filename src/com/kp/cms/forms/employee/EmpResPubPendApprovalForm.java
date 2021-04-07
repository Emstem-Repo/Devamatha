package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmpArticlInPeriodicalsTO;
import com.kp.cms.to.employee.EmpArticleJournalsTO;
import com.kp.cms.to.employee.EmpAwardsAchievementsOthersTO;
import com.kp.cms.to.employee.EmpBlogTO;
import com.kp.cms.to.employee.EmpBooksMonographsTO;
import com.kp.cms.to.employee.EmpCasesNotesWorkingTO;
import com.kp.cms.to.employee.EmpChapterArticlBookTO;
import com.kp.cms.to.employee.EmpConferencePresentationTO;
import com.kp.cms.to.employee.EmpConferenceSeminarsAttendedTO;
import com.kp.cms.to.employee.EmpFilmVideosDocTO;
import com.kp.cms.to.employee.EmpInvitedTalkTO;
import com.kp.cms.to.employee.EmpOwnPhdMPilThesisTO;
import com.kp.cms.to.employee.EmpPhdMPhilThesisGuidedTO;
import com.kp.cms.to.employee.EmpResProjectTO;
import com.kp.cms.to.employee.EmpResPublicMasterTO;
import com.kp.cms.to.employee.EmpSeminarsOrganizedTO;
import com.kp.cms.to.employee.EmpWorkshopsFdpTrainingTO;

public class EmpResPubPendApprovalForm extends BaseActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String empResPubName;
	private String origEmpResPubName;
	private String approverComment;
	private String approvedDate;
	private String entryCreatedate;
	
	private String approverId;
	private String employeeName;
	private String employeeId;
	private String empResPubMasterId;
	private Map<String,String> resPubMasterMap;
	private String empResearchProjectLength;
	private String empBooksMonographsLength;
	private String empArticleJournalsLength;
	private String empChapterArticlBookLength;
	private String empBlogLength;
	private String empFilmVideosDocLength;
	private String empArticlPeriodicalsLength;
	private String empConfPresentLength;
	private String empCasesNotesWorkingLength;
	private String empSeminarsOrganizedLength;
	private String empPhdThesisGuidedLength;
	private String empOwnPhdThesisLength;
	private String empInvitedTalkLength;
	private String empAwardsLength;
	private String mode;
	private String focusValue;
	private String approverEmailId;
	private String employeeEmailId;
	private List<EmployeeTO> employeeToList;
	private List<Employee> employeeList;
	private String selectedEmployeeId;
	private String submitName;
	private String selectedId;
	
	private List<EmpResPublicMasterTO> resPubMasterToList;
	private List<EmpResProjectTO> empResearchProjectTO;
	private List<EmpBooksMonographsTO> empBooksMonographsTO;
	private List<EmpArticleJournalsTO> empArticleJournalsTO;
	private List<EmpChapterArticlBookTO> empChapterArticlBookTO;
	private List<EmpBlogTO> empBlogTO;
	private List<EmpFilmVideosDocTO> empFilmVideosDocTO;
	private List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO;
	private List<EmpConferencePresentationTO> empConferencePresentationTO;
	private List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO;
	private List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO;
	private List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO;
	private List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO;
	private List<EmpInvitedTalkTO> empInvitedTalkTO;
	private List<EmpWorkshopsFdpTrainingTO> empWorkshopsTO;
	private List<EmpConferenceSeminarsAttendedTO> empConferenceSeminarsAttendedTO;
	private List<EmpAwardsAchievementsOthersTO> empAwardsAchievementsOthersTO;
	
	private String userId;
	private String tempFingerPrintId;
	private String tempName;
	private String tempDepartmentId;
	private Map<String,String> tempDepartmentMap;
	private String tempDepartmentName;
	private String tempActive;
	private boolean pendingList;
	private boolean approvedList;
	private Map<String,String> departmentMap;
	private String empName;
	private String fingerprintId;
	private byte[] researchPhotoBytes;
	private String empDepartment;
	private boolean selectedCategory;
	private String rejectReason;
	private String isReject;
	private boolean approveFlag;
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmpResPubName() {
		return empResPubName;
	}
	public void setEmpResPubName(String empResPubName) {
		this.empResPubName = empResPubName;
	}
	public String getOrigEmpResPubName() {
		return origEmpResPubName;
	}
	public void setOrigEmpResPubName(String origEmpResPubName) {
		this.origEmpResPubName = origEmpResPubName;
	}
	public String getApproverComment() {
		return approverComment;
	}
	public void setApproverComment(String approverComment) {
		this.approverComment = approverComment;
	}
	public String getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(String approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getEntryCreatedate() {
		return entryCreatedate;
	}
	public void setEntryCreatedate(String entryCreatedate) {
		this.entryCreatedate = entryCreatedate;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmpResPubMasterId() {
		return empResPubMasterId;
	}
	public void setEmpResPubMasterId(String empResPubMasterId) {
		this.empResPubMasterId = empResPubMasterId;
	}
	public Map<String, String> getResPubMasterMap() {
		return resPubMasterMap;
	}
	public void setResPubMasterMap(Map<String, String> resPubMasterMap) {
		this.resPubMasterMap = resPubMasterMap;
	}
	public String getEmpResearchProjectLength() {
		return empResearchProjectLength;
	}
	public void setEmpResearchProjectLength(String empResearchProjectLength) {
		this.empResearchProjectLength = empResearchProjectLength;
	}
	public String getEmpBooksMonographsLength() {
		return empBooksMonographsLength;
	}
	public void setEmpBooksMonographsLength(String empBooksMonographsLength) {
		this.empBooksMonographsLength = empBooksMonographsLength;
	}
	public String getEmpArticleJournalsLength() {
		return empArticleJournalsLength;
	}
	public void setEmpArticleJournalsLength(String empArticleJournalsLength) {
		this.empArticleJournalsLength = empArticleJournalsLength;
	}
	public String getEmpChapterArticlBookLength() {
		return empChapterArticlBookLength;
	}
	public void setEmpChapterArticlBookLength(String empChapterArticlBookLength) {
		this.empChapterArticlBookLength = empChapterArticlBookLength;
	}
	public String getEmpBlogLength() {
		return empBlogLength;
	}
	public void setEmpBlogLength(String empBlogLength) {
		this.empBlogLength = empBlogLength;
	}
	public String getEmpFilmVideosDocLength() {
		return empFilmVideosDocLength;
	}
	public void setEmpFilmVideosDocLength(String empFilmVideosDocLength) {
		this.empFilmVideosDocLength = empFilmVideosDocLength;
	}
	public String getEmpArticlPeriodicalsLength() {
		return empArticlPeriodicalsLength;
	}
	public void setEmpArticlPeriodicalsLength(String empArticlPeriodicalsLength) {
		this.empArticlPeriodicalsLength = empArticlPeriodicalsLength;
	}
	public String getEmpConfPresentLength() {
		return empConfPresentLength;
	}
	public void setEmpConfPresentLength(String empConfPresentLength) {
		this.empConfPresentLength = empConfPresentLength;
	}
	public String getEmpCasesNotesWorkingLength() {
		return empCasesNotesWorkingLength;
	}
	public void setEmpCasesNotesWorkingLength(String empCasesNotesWorkingLength) {
		this.empCasesNotesWorkingLength = empCasesNotesWorkingLength;
	}
	public String getEmpSeminarsOrganizedLength() {
		return empSeminarsOrganizedLength;
	}
	public void setEmpSeminarsOrganizedLength(String empSeminarsOrganizedLength) {
		this.empSeminarsOrganizedLength = empSeminarsOrganizedLength;
	}
	public String getEmpPhdThesisGuidedLength() {
		return empPhdThesisGuidedLength;
	}
	public void setEmpPhdThesisGuidedLength(String empPhdThesisGuidedLength) {
		this.empPhdThesisGuidedLength = empPhdThesisGuidedLength;
	}
	public String getEmpOwnPhdThesisLength() {
		return empOwnPhdThesisLength;
	}
	public void setEmpOwnPhdThesisLength(String empOwnPhdThesisLength) {
		this.empOwnPhdThesisLength = empOwnPhdThesisLength;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
	public String getApproverEmailId() {
		return approverEmailId;
	}
	public void setApproverEmailId(String approverEmailId) {
		this.approverEmailId = approverEmailId;
	}
	public List<EmpResPublicMasterTO> getResPubMasterToList() {
		return resPubMasterToList;
	}
	public void setResPubMasterToList(List<EmpResPublicMasterTO> resPubMasterToList) {
		this.resPubMasterToList = resPubMasterToList;
	}
	public List<EmpResProjectTO> getEmpResearchProjectTO() {
		return empResearchProjectTO;
	}
	public void setEmpResearchProjectTO(List<EmpResProjectTO> empResearchProjectTO) {
		this.empResearchProjectTO = empResearchProjectTO;
	}
	public List<EmpBooksMonographsTO> getEmpBooksMonographsTO() {
		return empBooksMonographsTO;
	}
	public void setEmpBooksMonographsTO(
			List<EmpBooksMonographsTO> empBooksMonographsTO) {
		this.empBooksMonographsTO = empBooksMonographsTO;
	}
	public List<EmpArticleJournalsTO> getEmpArticleJournalsTO() {
		return empArticleJournalsTO;
	}
	public void setEmpArticleJournalsTO(
			List<EmpArticleJournalsTO> empArticleJournalsTO) {
		this.empArticleJournalsTO = empArticleJournalsTO;
	}
	public List<EmpChapterArticlBookTO> getEmpChapterArticlBookTO() {
		return empChapterArticlBookTO;
	}
	public void setEmpChapterArticlBookTO(
			List<EmpChapterArticlBookTO> empChapterArticlBookTO) {
		this.empChapterArticlBookTO = empChapterArticlBookTO;
	}
	public List<EmpBlogTO> getEmpBlogTO() {
		return empBlogTO;
	}
	public void setEmpBlogTO(List<EmpBlogTO> empBlogTO) {
		this.empBlogTO = empBlogTO;
	}
	public List<EmpFilmVideosDocTO> getEmpFilmVideosDocTO() {
		return empFilmVideosDocTO;
	}
	public void setEmpFilmVideosDocTO(List<EmpFilmVideosDocTO> empFilmVideosDocTO) {
		this.empFilmVideosDocTO = empFilmVideosDocTO;
	}
	public List<EmpArticlInPeriodicalsTO> getEmpArticlInPeriodicalsTO() {
		return empArticlInPeriodicalsTO;
	}
	public void setEmpArticlInPeriodicalsTO(
			List<EmpArticlInPeriodicalsTO> empArticlInPeriodicalsTO) {
		this.empArticlInPeriodicalsTO = empArticlInPeriodicalsTO;
	}
	public List<EmpConferencePresentationTO> getEmpConferencePresentationTO() {
		return empConferencePresentationTO;
	}
	public void setEmpConferencePresentationTO(
			List<EmpConferencePresentationTO> empConferencePresentationTO) {
		this.empConferencePresentationTO = empConferencePresentationTO;
	}
	public List<EmpCasesNotesWorkingTO> getEmpCasesNotesWorkingTO() {
		return empCasesNotesWorkingTO;
	}
	public void setEmpCasesNotesWorkingTO(
			List<EmpCasesNotesWorkingTO> empCasesNotesWorkingTO) {
		this.empCasesNotesWorkingTO = empCasesNotesWorkingTO;
	}
	public List<EmpSeminarsOrganizedTO> getEmpSeminarsOrganizedTO() {
		return empSeminarsOrganizedTO;
	}
	public void setEmpSeminarsOrganizedTO(
			List<EmpSeminarsOrganizedTO> empSeminarsOrganizedTO) {
		this.empSeminarsOrganizedTO = empSeminarsOrganizedTO;
	}
	public List<EmpPhdMPhilThesisGuidedTO> getEmpPhdMPhilThesisGuidedTO() {
		return empPhdMPhilThesisGuidedTO;
	}
	public void setEmpPhdMPhilThesisGuidedTO(
			List<EmpPhdMPhilThesisGuidedTO> empPhdMPhilThesisGuidedTO) {
		this.empPhdMPhilThesisGuidedTO = empPhdMPhilThesisGuidedTO;
	}
	public List<EmpOwnPhdMPilThesisTO> getEmpOwnPhdMPilThesisTO() {
		return empOwnPhdMPilThesisTO;
	}
	public void setEmpOwnPhdMPilThesisTO(
			List<EmpOwnPhdMPilThesisTO> empOwnPhdMPilThesisTO) {
		this.empOwnPhdMPilThesisTO = empOwnPhdMPilThesisTO;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


	public List<EmployeeTO> getEmployeeToList() {
		return employeeToList;
	}


	public void setEmployeeToList(List<EmployeeTO> employeeToList) {
		this.employeeToList = employeeToList;
	}


	public String getSelectedEmployeeId() {
		return selectedEmployeeId;
	}


	public void setSelectedEmployeeId(String selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}


	public String getTempFingerPrintId() {
		return tempFingerPrintId;
	}


	public void setTempFingerPrintId(String tempFingerPrintId) {
		this.tempFingerPrintId = tempFingerPrintId;
	}


	public String getTempName() {
		return tempName;
	}


	public void setTempName(String tempName) {
		this.tempName = tempName;
	}


	public String getTempDepartmentId() {
		return tempDepartmentId;
	}


	public void setTempDepartmentId(String tempDepartmentId) {
		this.tempDepartmentId = tempDepartmentId;
	}


	public Map<String, String> getTempDepartmentMap() {
		return tempDepartmentMap;
	}


	public void setTempDepartmentMap(Map<String, String> tempDepartmentMap) {
		this.tempDepartmentMap = tempDepartmentMap;
	}


	public String getTempDepartmentName() {
		return tempDepartmentName;
	}


	public void setTempDepartmentName(String tempDepartmentName) {
		this.tempDepartmentName = tempDepartmentName;
	}


	public String getTempActive() {
		return tempActive;
	}


	public void setTempActive(String tempActive) {
		this.tempActive = tempActive;
	}


	public boolean isPendingList() {
		return pendingList;
	}


	public void setPendingList(boolean pendingList) {
		this.pendingList = pendingList;
	}


	public List<Employee> getEmployeeList() {
		return employeeList;
	}


	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}


	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}


	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}


	public String getEmpInvitedTalkLength() {
		return empInvitedTalkLength;
	}


	public void setEmpInvitedTalkLength(String empInvitedTalkLength) {
		this.empInvitedTalkLength = empInvitedTalkLength;
	}


	public List<EmpInvitedTalkTO> getEmpInvitedTalkTO() {
		return empInvitedTalkTO;
	}


	public void setEmpInvitedTalkTO(List<EmpInvitedTalkTO> empInvitedTalkTO) {
		this.empInvitedTalkTO = empInvitedTalkTO;
	}


	public String getEmployeeEmailId() {
		return employeeEmailId;
	}


	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
	}


	public String getSubmitName() {
		return submitName;
	}


	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}


	public List<EmpWorkshopsFdpTrainingTO> getEmpWorkshopsTO() {
		return empWorkshopsTO;
	}


	public void setEmpWorkshopsTO(List<EmpWorkshopsFdpTrainingTO> empWorkshopsTO) {
		this.empWorkshopsTO = empWorkshopsTO;
	}


	public List<EmpConferenceSeminarsAttendedTO> getEmpConferenceSeminarsAttendedTO() {
		return empConferenceSeminarsAttendedTO;
	}


	public void setEmpConferenceSeminarsAttendedTO(
			List<EmpConferenceSeminarsAttendedTO> empConferenceSeminarsAttendedTO) {
		this.empConferenceSeminarsAttendedTO = empConferenceSeminarsAttendedTO;
	}


	public List<EmpAwardsAchievementsOthersTO> getEmpAwardsAchievementsOthersTO() {
		return empAwardsAchievementsOthersTO;
	}


	public void setEmpAwardsAchievementsOthersTO(
			List<EmpAwardsAchievementsOthersTO> empAwardsAchievementsOthersTO) {
		this.empAwardsAchievementsOthersTO = empAwardsAchievementsOthersTO;
	}


	public String getEmpAwardsLength() {
		return empAwardsLength;
	}


	public void setEmpAwardsLength(String empAwardsLength) {
		this.empAwardsLength = empAwardsLength;
	}


	public String getEmpName() {
		return empName;
	}


	public void setEmpName(String empName) {
		this.empName = empName;
	}


	public String getFingerprintId() {
		return fingerprintId;
	}


	public void setFingerprintId(String fingerprintId) {
		this.fingerprintId = fingerprintId;
	}


	public String getEmpDepartment() {
		return empDepartment;
	}


	public void setEmpDepartment(String empDepartment) {
		this.empDepartment = empDepartment;
	}


	

	public byte[] getResearchPhotoBytes() {
		return researchPhotoBytes;
	}


	public void setResearchPhotoBytes(byte[] researchPhotoBytes) {
		this.researchPhotoBytes = researchPhotoBytes;
	}


	public boolean isSelectedCategory() {
		return selectedCategory;
	}


	public void setSelectedCategory(boolean selectedCategory) {
		this.selectedCategory = selectedCategory;
	}


	public String getRejectReason() {
		return rejectReason;
	}


	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}


	public String getIsReject() {
		return isReject;
	}


	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}


	public boolean isApproveFlag() {
		return approveFlag;
	}


	public void setApproveFlag(boolean approveFlag) {
		this.approveFlag = approveFlag;
	}


	public String getSelectedId() {
		return selectedId;
	}


	public void setSelectedId(String selectedId) {
		this.selectedId = selectedId;
	}


	public boolean isApprovedList() {
		return approvedList;
	}


	public void setApprovedList(boolean approvedList) {
		this.approvedList = approvedList;
	}


	

}
