package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CandidatePGIDetailsTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.StudentOnlineApplicationSMS_EmailTO;
import com.kp.cms.to.admission.AdmApplnTO;
import com.kp.cms.to.admission.AdmissionStatusTO;

public class UniqueIdRegistrationForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String uniqueId;
	private String emailId;
	private String mobileCode;
	private String mobileNo;
	private String loginDateOfBirth;
	private String registerDateOfBirth;
	private String mode;
	private String pageType;
	private Integer onlineApplicationUniqueId;
	private String applicantName;
	private String applicantEmailId;
	private String applicantMobileCode;
	private String applicantMobileNo;
	private String residentCategoryId;
	private String applicationNo;
	private String dateOfBirth;
	private int appliedYear;
	private List<AdmissionStatusTO> admissionStatusTOList;
	private String admStatus;
	private boolean onlineAcknowledgement;
	private String serverDownMessage;
	private List<StudentOnlineApplicationSMS_EmailTO> emailInfoList ;
	private List<StudentOnlineApplicationSMS_EmailTO> smsInfoList;
	private String displayName;
	private String displayMode;
	private String dateRange;
	private String onlineApplnInstructionMsg;
	private String isPhoto;
	private String studentPhotoName;
	private List<CandidatePGIDetailsTO> candidatePGIDetailsTOs;
	private List<String> messageList;
	private String isDraftMode;
	private String currentPage;
	private Boolean offlinePage;
	private Boolean applnMode;
	private String incompleteApplication;
	private List<ResidentCategoryTO> residentTypes;
	private String gender;
	private String confirmEmailId;
	private String subReligionId;
	private Map<Integer,String> subReligionMap;
	private String nativeCountry;
	private String applicationAmount;
	private String applicationAmountWords;
	private String challanRefNo;
	private AdmApplnTO applicantDetails;
	private String method;
	private boolean aided;
	private String mngQuota;
	private String isCommuQuota;
//	setter getter methods
	
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getLoginDateOfBirth() {
		return loginDateOfBirth;
	}
	public void setLoginDateOfBirth(String loginDateOfBirth) {
		this.loginDateOfBirth = loginDateOfBirth;
	}
	public String getRegisterDateOfBirth() {
		return registerDateOfBirth;
	}
	public void setRegisterDateOfBirth(String registerDateOfBirth) {
		this.registerDateOfBirth = registerDateOfBirth;
	}
	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
	public void clearPage() {
		this.id=0;
		this.uniqueId=null;
		this.emailId = null;
		this.mobileNo=null;
		this.loginDateOfBirth=null;
		this.registerDateOfBirth=null;
		this.mode =null;
		this.pageType = null;
		this.onlineApplicationUniqueId = 0;
		this.applicantEmailId = null;
		this.applicantMobileNo = null;
		this.applicantName = null;
		this.residentCategoryId =null;
		this.emailInfoList=null;
		this.smsInfoList =null;
		this.displayName = null;
		super.setDob(null);
		this.displayMode = "uniqueId";
		super.setApplnNo(null);
		this.applicationNo = null;
		this.dateRange = null;
		this.admissionStatusTOList = null;
		this.onlineApplnInstructionMsg = null;
		this.isPhoto = "false";
		this.studentPhotoName = null;
		this.serverDownMessage = null;
		this.gender="";
		this.mobileCode=null;
		this.confirmEmailId=null;
		this.subReligionId=null;
		this.applicationAmount="";
		this.applicationAmountWords="";
		this.challanRefNo="";
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getOnlineApplicationUniqueId() {
		return onlineApplicationUniqueId;
	}
	public void setOnlineApplicationUniqueId(Integer onlineApplicationUniqueId) {
		this.onlineApplicationUniqueId = onlineApplicationUniqueId;
	}
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getApplicantEmailId() {
		return applicantEmailId;
	}
	public void setApplicantEmailId(String applicantEmailId) {
		this.applicantEmailId = applicantEmailId;
	}
	public String getApplicantMobileNo() {
		return applicantMobileNo;
	}
	public void setApplicantMobileNo(String applicantMobileNo) {
		this.applicantMobileNo = applicantMobileNo;
	}
	public String getResidentCategoryId() {
		return residentCategoryId;
	}
	public void setResidentCategoryId(String residentCategoryId) {
		this.residentCategoryId = residentCategoryId;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public int getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(int appliedYear) {
		this.appliedYear = appliedYear;
	}
	public boolean isOnlineAcknowledgement() {
		return onlineAcknowledgement;
	}
	public void setOnlineAcknowledgement(boolean onlineAcknowledgement) {
		this.onlineAcknowledgement = onlineAcknowledgement;
	}
	public String getServerDownMessage() {
		return serverDownMessage;
	}
	public void setServerDownMessage(String serverDownMessage) {
		this.serverDownMessage = serverDownMessage;
	}
	public List<AdmissionStatusTO> getAdmissionStatusTOList() {
		return admissionStatusTOList;
	}
	public void setAdmissionStatusTOList(
			List<AdmissionStatusTO> admissionStatusTOList) {
		this.admissionStatusTOList = admissionStatusTOList;
	}
	public String getAdmStatus() {
		return admStatus;
	}
	public void setAdmStatus(String admStatus) {
		this.admStatus = admStatus;
	}
	public List<StudentOnlineApplicationSMS_EmailTO> getEmailInfoList() {
		return emailInfoList;
	}
	public void setEmailInfoList(
			List<StudentOnlineApplicationSMS_EmailTO> emailInfoList) {
		this.emailInfoList = emailInfoList;
	}
	public List<StudentOnlineApplicationSMS_EmailTO> getSmsInfoList() {
		return smsInfoList;
	}
	public void setSmsInfoList(List<StudentOnlineApplicationSMS_EmailTO> smsInfoList) {
		this.smsInfoList = smsInfoList;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getDisplayMode() {
		return displayMode;
	}
	public void setDisplayMode(String displayMode) {
		this.displayMode = displayMode;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public String getOnlineApplnInstructionMsg() {
		return onlineApplnInstructionMsg;
	}
	public void setOnlineApplnInstructionMsg(String onlineApplnInstructionMsg) {
		this.onlineApplnInstructionMsg = onlineApplnInstructionMsg;
	}
	public String getIsPhoto() {
		return isPhoto;
	}
	public void setIsPhoto(String isPhoto) {
		this.isPhoto = isPhoto;
	}
	public String getStudentPhotoName() {
		return studentPhotoName;
	}
	public void setStudentPhotoName(String studentPhotoName) {
		this.studentPhotoName = studentPhotoName;
	}
	public List<CandidatePGIDetailsTO> getCandidatePGIDetailsTOs() {
		return candidatePGIDetailsTOs;
	}
	public void setCandidatePGIDetailsTOs(
			List<CandidatePGIDetailsTO> candidatePGIDetailsTOs) {
		this.candidatePGIDetailsTOs = candidatePGIDetailsTOs;
	}
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public String getIsDraftMode() {
		return isDraftMode;
	}
	public void setIsDraftMode(String isDraftMode) {
		this.isDraftMode = isDraftMode;
	}
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public Boolean getOfflinePage() {
		return offlinePage;
	}
	public void setOfflinePage(Boolean offlinePage) {
		this.offlinePage = offlinePage;
	}
	public Boolean getApplnMode() {
		return applnMode;
	}
	public void setApplnMode(Boolean applnMode) {
		this.applnMode = applnMode;
	}
	public String getIncompleteApplication() {
		return incompleteApplication;
	}
	public void setIncompleteApplication(String incompleteApplication) {
		this.incompleteApplication = incompleteApplication;
	}
	public List<ResidentCategoryTO> getResidentTypes() {
		return residentTypes;
	}
	public void setResidentTypes(List<ResidentCategoryTO> residentTypes) {
		this.residentTypes = residentTypes;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}
	public String getApplicantMobileCode() {
		return applicantMobileCode;
	}
	public void setApplicantMobileCode(String applicantMobileCode) {
		this.applicantMobileCode = applicantMobileCode;
	}
	public String getConfirmEmailId() {
		return confirmEmailId;
	}
	public void setConfirmEmailId(String confirmEmailId) {
		this.confirmEmailId = confirmEmailId;
	}
	public String getSubReligionId() {
		return subReligionId;
	}
	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}
	public Map<Integer, String> getSubReligionMap() {
		return subReligionMap;
	}
	public void setSubReligionMap(Map<Integer, String> subReligionMap) {
		this.subReligionMap = subReligionMap;
	}
	public String getNativeCountry() {
		return nativeCountry;
	}
	public void setNativeCountry(String nativeCountry) {
		this.nativeCountry = nativeCountry;
	}
	public String getApplicationAmount() {
		return applicationAmount;
	}
	public void setApplicationAmount(String applicationAmount) {
		this.applicationAmount = applicationAmount;
	}
	public String getApplicationAmountWords() {
		return applicationAmountWords;
	}
	public void setApplicationAmountWords(String applicationAmountWords) {
		this.applicationAmountWords = applicationAmountWords;
	}
	public String getChallanRefNo() {
		return challanRefNo;
	}
	public void setChallanRefNo(String challanRefNo) {
		this.challanRefNo = challanRefNo;
	}
	public AdmApplnTO getApplicantDetails() {
		return applicantDetails;
	}
	public void setApplicantDetails(AdmApplnTO applicantDetails) {
		this.applicantDetails = applicantDetails;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public boolean isAided() {
		return aided;
	}
	public void setAided(boolean aided) {
		this.aided = aided;
	}
	public String getIsCommuQuota() {
		return isCommuQuota;
	}
	public void setIsCommuQuota(String isCommuQuota) {
		this.isCommuQuota = isCommuQuota;
	}
	public String getMngQuota() {
		return mngQuota;
	}
	public void setMngQuota(String mngQuota) {
		this.mngQuota = mngQuota;
	}	
}
