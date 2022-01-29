package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmpAllowanceTO;
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
import com.kp.cms.to.employee.EmpResearchProjectTO;
import com.kp.cms.to.employee.EmpSeminarsOrganizedTO;
import com.kp.cms.to.employee.EmpWorkshopsFdpTrainingTO;

public class EmpResPubDetailsForm extends BaseActionForm{
	
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
	private String empInvitedTalkLength;
	private String empCasesNotesWorkingLength;
	private String empSeminarsOrganizedLength;
	private String empPhdThesisGuidedLength;
	private String empOwnPhdThesisLength;
	private String empWorkshopsAttendedLength;
	private String empConfSeminarsAttendedLength;
	private String empAwardsLength;
	private String mode;
	private String focusValue;
	private String approverEmailId;
	private String fingerPrintId;
	private String verifyFlag;
	private String submitName;
	private String employeeEmailId;
	
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
	private boolean selectedCategory;
	private String errorMessage;
	private String errMsg;
	
	
	//for Employee search Admin edit screen
	private String tempFingerPrintId;
	private String tempName;
	private String tempCode;
	private String tempDesignationPfId;
	private String tempDepartmentId;
	private Map<String,String> tempDesignationMap;
	private Map<String,String> tempDepartmentMap;
	private String tempDesignationName;
	private String tempDepartmentName;
	private Map<String,String> tempStreamMap;
	private String tempStreamId;
	private String tempStreamName;
	private String selectedEmployeeId;
	private String tempActive;
	private String tempTeachingStaff;
	private String tempEmptypeId;
	private String tempEmployeeId;
	private Map<String,String> tempEmpTypeMap;
	private List<EmployeeTO> employeeToList;
	private String isEdit;
	private String isRejectScreen;
	private String isAdminScreen;
	private String selectedResId;
	private String isDelete;
	private String empName;
	private String fingerprintId;
	private byte[] researchPhotoBytes;
	private String empDepartment;
	private String isEmployee;
	
	
	public void reset(){
		submitName=null;
		approverEmailId=null;
		approverComment=null;
		approvedDate=null;
		entryCreatedate=null;
		approverId=null;
		employeeId=null;
		empResPubMasterId=null;
		verifyFlag="false";
		errorMessage=null;
		errMsg=null;
		isEdit="false";
		isRejectScreen="false";
		isAdminScreen="false";
		isEmployee="true";
		}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public List<EmpConferencePresentationTO> getEmpConferencePresentationTO() {
		return empConferencePresentationTO;
	}
	public void setEmpConferencePresentationTO(
			List<EmpConferencePresentationTO> empConferencePresentationTO) {
		this.empConferencePresentationTO = empConferencePresentationTO;
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

	public List<EmpResProjectTO> getEmpResearchProjectTO() {
		return empResearchProjectTO;
	}

	public void setEmpResearchProjectTO(List<EmpResProjectTO> empResearchProjectTO) {
		this.empResearchProjectTO = empResearchProjectTO;
	}

	public String getEmpResPubName() {
		return empResPubName;
	}

	public void setEmpResPubName(String empResPubName) {
		this.empResPubName = empResPubName;
	}

	public List<EmpResPublicMasterTO> getResPubMasterToList() {
		return resPubMasterToList;
	}

	public void setResPubMasterToList(List<EmpResPublicMasterTO> resPubMasterToList) {
		this.resPubMasterToList = resPubMasterToList;
	}

	public String getOrigEmpResPubName() {
		return origEmpResPubName;
	}

	public void setOrigEmpResPubName(String origEmpResPubName) {
		this.origEmpResPubName = origEmpResPubName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getApproverEmailId() {
		return approverEmailId;
	}

	public void setApproverEmailId(String approverEmailId) {
		this.approverEmailId = approverEmailId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getFingerPrintId() {
		return fingerPrintId;
	}

	public void setFingerPrintId(String fingerPrintId) {
		this.fingerPrintId = fingerPrintId;
	}

	public String getVerifyFlag() {
		return verifyFlag;
	}

	public void setVerifyFlag(String verifyFlag) {
		this.verifyFlag = verifyFlag;
	}

	public List<EmpInvitedTalkTO> getEmpInvitedTalkTO() {
		return empInvitedTalkTO;
	}

	public void setEmpInvitedTalkTO(List<EmpInvitedTalkTO> empInvitedTalkTO) {
		this.empInvitedTalkTO = empInvitedTalkTO;
	}

	public String getEmpInvitedTalkLength() {
		return empInvitedTalkLength;
	}

	public void setEmpInvitedTalkLength(String empInvitedTalkLength) {
		this.empInvitedTalkLength = empInvitedTalkLength;
	}

	public String getSubmitName() {
		return submitName;
	}

	public void setSubmitName(String submitName) {
		this.submitName = submitName;
	}

	public String getEmployeeEmailId() {
		return employeeEmailId;
	}

	public void setEmployeeEmailId(String employeeEmailId) {
		this.employeeEmailId = employeeEmailId;
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

	public String getEmpWorkshopsAttendedLength() {
		return empWorkshopsAttendedLength;
	}

	public void setEmpWorkshopsAttendedLength(String empWorkshopsAttendedLength) {
		this.empWorkshopsAttendedLength = empWorkshopsAttendedLength;
	}

	public String getEmpConfSeminarsAttendedLength() {
		return empConfSeminarsAttendedLength;
	}

	public void setEmpConfSeminarsAttendedLength(
			String empConfSeminarsAttendedLength) {
		this.empConfSeminarsAttendedLength = empConfSeminarsAttendedLength;
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

	public boolean isSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(boolean selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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

	public String getTempCode() {
		return tempCode;
	}

	public void setTempCode(String tempCode) {
		this.tempCode = tempCode;
	}

	public String getTempDesignationPfId() {
		return tempDesignationPfId;
	}

	public void setTempDesignationPfId(String tempDesignationPfId) {
		this.tempDesignationPfId = tempDesignationPfId;
	}

	public String getTempDepartmentId() {
		return tempDepartmentId;
	}

	public void setTempDepartmentId(String tempDepartmentId) {
		this.tempDepartmentId = tempDepartmentId;
	}

	public Map<String, String> getTempDesignationMap() {
		return tempDesignationMap;
	}

	public void setTempDesignationMap(Map<String, String> tempDesignationMap) {
		this.tempDesignationMap = tempDesignationMap;
	}

	public Map<String, String> getTempDepartmentMap() {
		return tempDepartmentMap;
	}

	public void setTempDepartmentMap(Map<String, String> tempDepartmentMap) {
		this.tempDepartmentMap = tempDepartmentMap;
	}

	public String getTempDesignationName() {
		return tempDesignationName;
	}

	public void setTempDesignationName(String tempDesignationName) {
		this.tempDesignationName = tempDesignationName;
	}

	public String getTempDepartmentName() {
		return tempDepartmentName;
	}

	public void setTempDepartmentName(String tempDepartmentName) {
		this.tempDepartmentName = tempDepartmentName;
	}

	public Map<String, String> getTempStreamMap() {
		return tempStreamMap;
	}

	public void setTempStreamMap(Map<String, String> tempStreamMap) {
		this.tempStreamMap = tempStreamMap;
	}

	public String getTempStreamId() {
		return tempStreamId;
	}

	public void setTempStreamId(String tempStreamId) {
		this.tempStreamId = tempStreamId;
	}

	public String getTempStreamName() {
		return tempStreamName;
	}

	public void setTempStreamName(String tempStreamName) {
		this.tempStreamName = tempStreamName;
	}

	public String getSelectedEmployeeId() {
		return selectedEmployeeId;
	}

	public void setSelectedEmployeeId(String selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}

	public String getTempActive() {
		return tempActive;
	}

	public void setTempActive(String tempActive) {
		this.tempActive = tempActive;
	}

	public String getTempTeachingStaff() {
		return tempTeachingStaff;
	}

	public void setTempTeachingStaff(String tempTeachingStaff) {
		this.tempTeachingStaff = tempTeachingStaff;
	}

	public String getTempEmptypeId() {
		return tempEmptypeId;
	}

	public void setTempEmptypeId(String tempEmptypeId) {
		this.tempEmptypeId = tempEmptypeId;
	}

	public Map<String, String> getTempEmpTypeMap() {
		return tempEmpTypeMap;
	}

	public void setTempEmpTypeMap(Map<String, String> tempEmpTypeMap) {
		this.tempEmpTypeMap = tempEmpTypeMap;
	}

	public List<EmployeeTO> getEmployeeToList() {
		return employeeToList;
	}

	public void setEmployeeToList(List<EmployeeTO> employeeToList) {
		this.employeeToList = employeeToList;
	}

	public String getTempEmployeeId() {
		return tempEmployeeId;
	}

	public void setTempEmployeeId(String tempEmployeeId) {
		this.tempEmployeeId = tempEmployeeId;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsRejectScreen() {
		return isRejectScreen;
	}

	public void setIsRejectScreen(String isRejectScreen) {
		this.isRejectScreen = isRejectScreen;
	}

	public String getIsAdminScreen() {
		return isAdminScreen;
	}

	public void setIsAdminScreen(String isAdminScreen) {
		this.isAdminScreen = isAdminScreen;
	}
	public String getSelectedResId() {
		return selectedResId;
	}

	public void setSelectedResId(String selectedResId) {
		this.selectedResId = selectedResId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public byte[] getResearchPhotoBytes() {
		return researchPhotoBytes;
	}

	public void setResearchPhotoBytes(byte[] researchPhotoBytes) {
		this.researchPhotoBytes = researchPhotoBytes;
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

	public String getIsEmployee() {
		return isEmployee;
	}

	public void setIsEmployee(String isEmployee) {
		this.isEmployee = isEmployee;
	}

	
}
