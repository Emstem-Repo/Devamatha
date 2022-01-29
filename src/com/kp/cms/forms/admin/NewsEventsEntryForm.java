package com.kp.cms.forms.admin;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.NewEventsResourseTO;
import com.kp.cms.to.admin.NewsEventsContactDetailsTO;
import com.kp.cms.to.admin.NewsEventsDetailsTO;
import com.kp.cms.to.admin.NewsEventsParticipantTO;
import com.kp.cms.to.admin.NewsEventsPhotosTO;

public class NewsEventsEntryForm extends BaseActionForm{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private String mobNewaEventsDatailsId;
	private String eventTitle;
	private String eventDescription;
	private String dateTo;
	private String dateFrom;
	private Map<Integer,String> categoryList;
	private String categoryId;
	private List<NewsEventsDetailsTO> mobNewsEventsDetails;
	private byte[] eventImage;
	private byte[] iconImage;
	private FormFile eventTitleFile;
	private FormFile iconTmageFile;
	private int dupId;
	private String academicYear;
	private String participants;
	private String organizedBy;
	private String departmentId;
	private String courseId;
	private String streamId;
	private String splCenterId;
	private Map<String,String> departmentMap;
	private Map<String,String> courseMap1;
	private Map<String,String> streamMap;
	private Map<String,String> splCenterMap;
	private String isRegistrationRequired;
	private String isLiveTelecast;
	private String isInvitationMailRequired;
	private FormFile registrationForm;
	private FormFile invitationMail;
	private String resourseListSize;
	private List<NewEventsResourseTO> resourseTO;
	private String mode;
	private String focusValue;
	private String isStream;
	private String isDepartment;
	private String isCourse;
	private String isSplCentre;
	
	//Approver screen variables....
	private String viewFor;
	private String eventWebPosition;
	private String newsWebPosition;
	private String summary;
	private FormFile materialsPublished;
	private FormFile eventReport;
	//private String materialsPublished;
	//private String eventReport;
	private String preApproverId;
	private String preApprovalDate;
	private String preIsApproved;
	private String preApprovalRemarks;
	private String postApproverId;
	private String postApprovalDate;
	private String postIsApproved;
	private String postApprovalRemarks;
	private String displayFromDate;
	private String displayToDate;
	private String selectedNewsEventsId;
	private String participantsListSize;
	private List<NewsEventsDetailsTO> newsList;
	private List<NewsEventsParticipantTO> partcipantsTO;
	private List<NewsEventsPhotosTO> photosTO;
	private String photoListSize;
	private String selectedPhotoId;
	private byte[] photoBytes;
	private String screen;
	private String prePostEventAdm;
	private String preAdminStatus;
	private String postAdminStatus;
	private String isAdminApprovePre;
	private String isAdminApprovePost;
	private String status;
	private String departmentName;
	private String courseName;
	private String splCentreName;
	private String streamName;
	private String categoryName;
	private int photoId;
	private String invitationName;
	private String regFormName;
	private String reportName;
	private String iconName;
	private String materialName;
	private String newsOrEvents;
	private String[] selectedviewFor;
	private String orgphotoListSize;
	private String orgPartListSize;
	private String orgResListSize;
	private String tempDateTo;
	private String tempDateFrom;
	private String tempCategoryId;
	private String tempAcademicYear;
	private String downloadFileName;
	private String fileType;
	private String regIsImage;
	private String reportIsImage;
	private String invitationIsImage;
	private String materialIsImage;
	private String resourseId;
	private String participantsId;
	private String tempOrganizedBy;
	private String tempCourseName;
	private String tempDepartmentName;
	private String tempSplCentreName;
	private String tempStreamName;
	private List<NewsEventsContactDetailsTO> contactTO;
	private String contactListSize;
	private String orgContactListSize;
	private boolean isEditable;
	private Date todaysDate;
	private String currentAcademicYear;
	private String userEmailId;
	private String userName;
	private String isPostDeptEntry;
	private String contactId;
	private String errorMsg;
	
	public int getDupId() {
		return dupId;
	}

	public void setDupId(int dupId) {
		this.dupId = dupId;
	}

	public FormFile getEventTitleFile() {
		return eventTitleFile;
	}

	public void setEventTitleFile(FormFile eventTitleFile) {
		this.eventTitleFile = eventTitleFile;
	}

	public FormFile getIconTmageFile() {
		return iconTmageFile;
	}

	public void setIconTmageFile(FormFile iconTmageFile) {
		this.iconTmageFile = iconTmageFile;
	}

	public byte[] getEventImage() {
		return eventImage;
	}

	public void setEventImage(byte[] eventImage) {
		this.eventImage = eventImage;
	}

	public byte[] getIconImage() {
		return iconImage;
	}

	public void setIconImage(byte[] iconImage) {
		this.iconImage = iconImage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	

	public Map<Integer, String> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(Map<Integer, String> categoryList) {
		this.categoryList = categoryList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getMobNewaEventsDatailsId() {
		return mobNewaEventsDatailsId;
	}

	public void setMobNewaEventsDatailsId(String mobNewaEventsDatailsId) {
		this.mobNewaEventsDatailsId = mobNewaEventsDatailsId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public void resetTemp(ActionMapping mapping, HttpServletRequest request)
	{
		this.tempAcademicYear=null;
		this.tempCategoryId=null;
		this.tempDateFrom=null;
		this.tempDateTo=null;
		
	}
	public void reset(ActionMapping mapping, HttpServletRequest request)
{
	super.reset(mapping, request);
	this.eventTitle=null;
	this.eventDescription=null;
	this.dateTo=null;
	this.dateFrom=null;
	this.categoryList=null;
	this.categoryId=null;
	this.iconImage=null;
	this.eventImage=null;
	this.iconTmageFile=null;
	this.eventTitleFile=null;
	this.courseId=null;
	this.departmentId=null;
	this.streamId=null;
	this.splCenterId=null;
	this.isInvitationMailRequired="No";
	this.isLiveTelecast="No";
	this.isRegistrationRequired="No";
	this.invitationMail=null;
	this.registrationForm=null;
	this.participants=null;
	this.organizedBy=null;
	this.isCourse="false";
	this.isStream="false";
	this.isDepartment="false";
	this.isSplCentre="false";
	this.viewFor=null;
	this.eventWebPosition=null;
	this.newsWebPosition=null;
	this.summary=null;
	this.materialsPublished=null;
	this.eventReport=null;
	this.preApprovalRemarks=null;
	this.postApprovalRemarks=null;
	this.displayFromDate=null;
	this.displayToDate=null;
	this.preAdminStatus="Not Published";
	this.postAdminStatus="Not Published";
	this.status="Not Published";
	this.selectedviewFor=null;
	this.iconName=null;
	this.invitationMail=null;
	this.newsOrEvents="Events";
	this.photoListSize=null;
	this.orgphotoListSize=null;
	this.participantsListSize=null;
	this.orgPartListSize=null;
	this.resourseListSize=null;
	this.orgResListSize=null;
	this.regIsImage="false";
	this.reportIsImage="false";
	this.materialIsImage="false";
	this.invitationIsImage="false";
	this.downloadFileName=null;
	this.fileType=null;
	this.regFormName=null;
	this.invitationName=null;
	this.materialName=null;
	this.reportName=null;
	this.iconName=null;
} 
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getParticipants() {
		return participants;
	}

	public void setParticipants(String participants) {
		this.participants = participants;
	}

	public String getOrganizedBy() {
		return organizedBy;
	}

	public void setOrganizedBy(String organizedBy) {
		this.organizedBy = organizedBy;
	}


	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId;
	}

	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public Map<String, String> getCourseMap1() {
		return courseMap1;
	}

	public void setCourseMap1(Map<String, String> courseMap1) {
		this.courseMap1 = courseMap1;
	}

	public Map<String, String> getStreamMap() {
		return streamMap;
	}

	public void setStreamMap(Map<String, String> streamMap) {
		this.streamMap = streamMap;
	}

	public String getIsRegistrationRequired() {
		return isRegistrationRequired;
	}

	public void setIsRegistrationRequired(String isRegistrationRequired) {
		this.isRegistrationRequired = isRegistrationRequired;
	}

	public String getIsLiveTelecast() {
		return isLiveTelecast;
	}

	public void setIsLiveTelecast(String isLiveTelecast) {
		this.isLiveTelecast = isLiveTelecast;
	}

	public String getIsInvitationMailRequired() {
		return isInvitationMailRequired;
	}

	public void setIsInvitationMailRequired(String isInvitationMailRequired) {
		this.isInvitationMailRequired = isInvitationMailRequired;
	}

	public FormFile getRegistrationForm() {
		return registrationForm;
	}

	public void setRegistrationForm(FormFile registrationForm) {
		this.registrationForm = registrationForm;
	}

	public FormFile getInvitationMail() {
		return invitationMail;
	}

	public void setInvitationMail(FormFile invitationMail) {
		this.invitationMail = invitationMail;
	}

	public String getSplCenterId() {
		return splCenterId;
	}

	public void setSplCenterId(String splCenterId) {
		this.splCenterId = splCenterId;
	}

	public Map<String, String> getSplCenterMap() {
		return splCenterMap;
	}

	public void setSplCenterMap(Map<String, String> splCenterMap) {
		this.splCenterMap = splCenterMap;
	}

	public List<NewEventsResourseTO> getResourseTO() {
		return resourseTO;
	}

	public void setResourseTO(List<NewEventsResourseTO> resourseTO) {
		this.resourseTO = resourseTO;
	}

	public String getResourseListSize() {
		return resourseListSize;
	}

	public void setResourseListSize(String resourseListSize) {
		this.resourseListSize = resourseListSize;
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

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getIsStream() {
		return isStream;
	}

	public void setIsStream(String isStream) {
		this.isStream = isStream;
	}

	public String getIsDepartment() {
		return isDepartment;
	}

	public void setIsDepartment(String isDepartment) {
		this.isDepartment = isDepartment;
	}

	public String getIsCourse() {
		return isCourse;
	}

	public void setIsCourse(String isCourse) {
		this.isCourse = isCourse;
	}

	public String getIsSplCentre() {
		return isSplCentre;
	}

	public void setIsSplCentre(String isSplCentre) {
		this.isSplCentre = isSplCentre;
	}

	public String getViewFor() {
		return viewFor;
	}

	public void setViewFor(String viewFor) {
		this.viewFor = viewFor;
	}

	public String getEventWebPosition() {
		return eventWebPosition;
	}

	public void setEventWebPosition(String eventWebPosition) {
		this.eventWebPosition = eventWebPosition;
	}

	public String getNewsWebPosition() {
		return newsWebPosition;
	}

	public void setNewsWebPosition(String newsWebPosition) {
		this.newsWebPosition = newsWebPosition;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	
	public String getPreApproverId() {
		return preApproverId;
	}

	public void setPreApproverId(String preApproverId) {
		this.preApproverId = preApproverId;
	}

	public String getPreApprovalDate() {
		return preApprovalDate;
	}

	public void setPreApprovalDate(String preApprovalDate) {
		this.preApprovalDate = preApprovalDate;
	}

	public String getPreIsApproved() {
		return preIsApproved;
	}

	public void setPreIsApproved(String preIsApproved) {
		this.preIsApproved = preIsApproved;
	}

	public String getPreApprovalRemarks() {
		return preApprovalRemarks;
	}

	public void setPreApprovalRemarks(String preApprovalRemarks) {
		this.preApprovalRemarks = preApprovalRemarks;
	}

	public String getPostApproverId() {
		return postApproverId;
	}

	public void setPostApproverId(String postApproverId) {
		this.postApproverId = postApproverId;
	}

	public String getPostApprovalDate() {
		return postApprovalDate;
	}

	public void setPostApprovalDate(String postApprovalDate) {
		this.postApprovalDate = postApprovalDate;
	}

	public String getPostIsApproved() {
		return postIsApproved;
	}

	public void setPostIsApproved(String postIsApproved) {
		this.postIsApproved = postIsApproved;
	}

	public String getPostApprovalRemarks() {
		return postApprovalRemarks;
	}

	public void setPostApprovalRemarks(String postApprovalRemarks) {
		this.postApprovalRemarks = postApprovalRemarks;
	}

	public String getDisplayFromDate() {
		return displayFromDate;
	}

	public void setDisplayFromDate(String displayFromDate) {
		this.displayFromDate = displayFromDate;
	}

	public String getDisplayToDate() {
		return displayToDate;
	}

	public void setDisplayToDate(String displayToDate) {
		this.displayToDate = displayToDate;
	}

	public String getSelectedNewsEventsId() {
		return selectedNewsEventsId;
	}

	public void setSelectedNewsEventsId(String selectedNewsEventsId) {
		this.selectedNewsEventsId = selectedNewsEventsId;
	}

	public List<NewsEventsDetailsTO> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<NewsEventsDetailsTO> newsList) {
		this.newsList = newsList;
	}

	public List<NewsEventsParticipantTO> getPartcipantsTO() {
		return partcipantsTO;
	}

	public void setPartcipantsTO(List<NewsEventsParticipantTO> partcipantsTO) {
		this.partcipantsTO = partcipantsTO;
	}

	public String getParticipantsListSize() {
		return participantsListSize;
	}

	public void setParticipantsListSize(String participantsListSize) {
		this.participantsListSize = participantsListSize;
	}

	public List<NewsEventsPhotosTO> getPhotosTO() {
		return photosTO;
	}

	public void setPhotosTO(List<NewsEventsPhotosTO> photosTO) {
		this.photosTO = photosTO;
	}

	public String getPhotoListSize() {
		return photoListSize;
	}

	public void setPhotoListSize(String photoListSize) {
		this.photoListSize = photoListSize;
	}

	public String getSelectedPhotoId() {
		return selectedPhotoId;
	}

	public void setSelectedPhotoId(String selectedPhotoId) {
		this.selectedPhotoId = selectedPhotoId;
	}

	public byte[] getPhotoBytes() {
		return photoBytes;
	}

	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getPrePostEventAdm() {
		return prePostEventAdm;
	}

	public void setPrePostEventAdm(String prePostEventAdm) {
		this.prePostEventAdm = prePostEventAdm;
	}

	
	public String getIsAdminApprovePre() {
		return isAdminApprovePre;
	}

	public void setIsAdminApprovePre(String isAdminApprovePre) {
		this.isAdminApprovePre = isAdminApprovePre;
	}

	public String getIsAdminApprovePost() {
		return isAdminApprovePost;
	}

	public void setIsAdminApprovePost(String isAdminApprovePost) {
		this.isAdminApprovePost = isAdminApprovePost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPreAdminStatus() {
		return preAdminStatus;
	}

	public void setPreAdminStatus(String preAdminStatus) {
		this.preAdminStatus = preAdminStatus;
	}

	public String getPostAdminStatus() {
		return postAdminStatus;
	}

	public void setPostAdminStatus(String postAdminStatus) {
		this.postAdminStatus = postAdminStatus;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSplCentreName() {
		return splCentreName;
	}

	public void setSplCentreName(String splCentreName) {
		this.splCentreName = splCentreName;
	}

	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public FormFile getMaterialsPublished() {
		return materialsPublished;
	}

	public void setMaterialsPublished(FormFile materialsPublished) {
		this.materialsPublished = materialsPublished;
	}

	public FormFile getEventReport() {
		return eventReport;
	}

	public void setEventReport(FormFile eventReport) {
		this.eventReport = eventReport;
	}

	public int getPhotoId() {
		return photoId;
	}

	public void setPhotoId(int photoId) {
		this.photoId = photoId;
	}

	public String getInvitationName() {
		return invitationName;
	}

	public void setInvitationName(String invitationName) {
		this.invitationName = invitationName;
	}

	public String getRegFormName() {
		return regFormName;
	}

	public void setRegFormName(String regFormName) {
		this.regFormName = regFormName;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getIconName() {
		return iconName;
	}

	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getNewsOrEvents() {
		return newsOrEvents;
	}

	public void setNewsOrEvents(String newsOrEvents) {
		this.newsOrEvents = newsOrEvents;
	}

	public String[] getSelectedviewFor() {
		return selectedviewFor;
	}

	public void setSelectedviewFor(String[] selectedviewFor) {
		this.selectedviewFor = selectedviewFor;
	}

	public String getOrgphotoListSize() {
		return orgphotoListSize;
	}

	public void setOrgphotoListSize(String orgphotoListSize) {
		this.orgphotoListSize = orgphotoListSize;
	}

	public String getOrgPartListSize() {
		return orgPartListSize;
	}

	public void setOrgPartListSize(String orgPartListSize) {
		this.orgPartListSize = orgPartListSize;
	}

	public String getOrgResListSize() {
		return orgResListSize;
	}

	public void setOrgResListSize(String orgResListSize) {
		this.orgResListSize = orgResListSize;
	}

	public String getTempDateTo() {
		return tempDateTo;
	}

	public void setTempDateTo(String tempDateTo) {
		this.tempDateTo = tempDateTo;
	}

	public String getTempDateFrom() {
		return tempDateFrom;
	}

	public void setTempDateFrom(String tempDateFrom) {
		this.tempDateFrom = tempDateFrom;
	}

	public String getTempCategoryId() {
		return tempCategoryId;
	}

	public void setTempCategoryId(String tempCategoryId) {
		this.tempCategoryId = tempCategoryId;
	}

	public String getTempAcademicYear() {
		return tempAcademicYear;
	}

	public void setTempAcademicYear(String tempAcademicYear) {
		this.tempAcademicYear = tempAcademicYear;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getRegIsImage() {
		return regIsImage;
	}

	public void setRegIsImage(String regIsImage) {
		this.regIsImage = regIsImage;
	}

	public String getReportIsImage() {
		return reportIsImage;
	}

	public void setReportIsImage(String reportIsImage) {
		this.reportIsImage = reportIsImage;
	}

	public String getInvitationIsImage() {
		return invitationIsImage;
	}

	public void setInvitationIsImage(String invitationIsImage) {
		this.invitationIsImage = invitationIsImage;
	}

	public String getMaterialIsImage() {
		return materialIsImage;
	}

	public void setMaterialIsImage(String materialIsImage) {
		this.materialIsImage = materialIsImage;
	}

	public String getResourseId() {
		return resourseId;
	}

	public void setResourseId(String resourseId) {
		this.resourseId = resourseId;
	}

	public String getParticipantsId() {
		return participantsId;
	}

	public void setParticipantsId(String participantsId) {
		this.participantsId = participantsId;
	}

	public String getTempOrganizedBy() {
		return tempOrganizedBy;
	}

	public void setTempOrganizedBy(String tempOrganizedBy) {
		this.tempOrganizedBy = tempOrganizedBy;
	}

	public String getTempCourseName() {
		return tempCourseName;
	}

	public void setTempCourseName(String tempCourseName) {
		this.tempCourseName = tempCourseName;
	}

	public String getTempDepartmentName() {
		return tempDepartmentName;
	}

	public void setTempDepartmentName(String tempDepartmentName) {
		this.tempDepartmentName = tempDepartmentName;
	}

	public String getTempSplCentreName() {
		return tempSplCentreName;
	}

	public void setTempSplCentreName(String tempSplCentreName) {
		this.tempSplCentreName = tempSplCentreName;
	}

	public String getTempStreamName() {
		return tempStreamName;
	}

	public void setTempStreamName(String tempStreamName) {
		this.tempStreamName = tempStreamName;
	}

	public List<NewsEventsContactDetailsTO> getContactTO() {
		return contactTO;
	}

	public void setContactTO(List<NewsEventsContactDetailsTO> contactTO) {
		this.contactTO = contactTO;
	}

	public String getContactListSize() {
		return contactListSize;
	}

	public void setContactListSize(String contactListSize) {
		this.contactListSize = contactListSize;
	}

	public String getOrgContactListSize() {
		return orgContactListSize;
	}

	public void setOrgContactListSize(String orgContactListSize) {
		this.orgContactListSize = orgContactListSize;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	public Date getTodaysDate() {
		return todaysDate;
	}

	public void setTodaysDate(Date todaysDate) {
		this.todaysDate = todaysDate;
	}

	public String getCurrentAcademicYear() {
		return currentAcademicYear;
	}

	public void setCurrentAcademicYear(String currentAcademicYear) {
		this.currentAcademicYear = currentAcademicYear;
	}

	public String getUserEmailId() {
		return userEmailId;
	}

	public void setUserEmailId(String userEmailId) {
		this.userEmailId = userEmailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIsPostDeptEntry() {
		return isPostDeptEntry;
	}

	public void setIsPostDeptEntry(String isPostDeptEntry) {
		this.isPostDeptEntry = isPostDeptEntry;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<NewsEventsDetailsTO> getMobNewsEventsDetails() {
		return mobNewsEventsDetails;
	}

	public void setMobNewsEventsDetails(
			List<NewsEventsDetailsTO> mobNewsEventsDetails) {
		this.mobNewsEventsDetails = mobNewsEventsDetails;
	}

		
	
	

	
}
