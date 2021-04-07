package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmployeeInfoEditNewTO;
import com.kp.cms.to.employee.EmployeeInfoEditTO;
import com.kp.cms.to.usermanagement.UserInfoTO;

public class EmployeeInfoEditNewForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String employeeId;
	private String guestId;
	private String name;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private Map<String,String> designationMap;
	private Map<String,String> departmentMap;
	private String expYears;
	private String expMonths;
	 private String teachingStaff;
	 private String designationId;
	 private String empTypeId;
	 private String tempTeachingStaff;
	 private String selectedEmployeeId;
	 private String selectedGuestId;
	 private String empTypeIdOld;
	 private String dateOfBirth;
	 private String dateOfJoining;
	 private String active;
	 private String gender;
	 private String email;
	 private String officialEmail;
	 private String mobileNo1;
	 private String departmentId;
	 private int currentExpYears;
	 private int currentExpMonths;
	 private EmployeeInfoEditNewTO employeeInfoEditNewTO;
	 private String empQualificationListSize;
	 private String empIntrestListSize;
	 private String empResearchListSize;
	 private String empGuidshipListSize;
	 private String empDutiesPerformedListSize;
	 private String empResearchProjectListSize;
	 private String empResearchPublicationListSize;
	 private String empBooksPublishListSize;
	 private String empPaperPrsentationListSize;
	 private String empSeminarattendedListSize;
	 private String empProfessionalDevelopmentListSize;
	 private String empAwardListSize;
	 private String empMembershipAcademicListSize;
	 private String mode;
	 private String focusValue;
	 private String designation;
	 private List<EmployeeTO> employeeToList;
	 private List<UserInfoTO> userList;
	 private String addressLine1;
	 private String addressLine2;
	 private String city;
	 private String permanentZipCode;
	 private String otherPermanentState;
	 private String currentAddressLine1;
	 private String currentAddressLine2;
	 private String currentCity;
	 private String otherCurrentState;
	 private String currentZipCode;
	 private String currentCountryId;
	 private String homePhone1;
	 private String homePhone2;
	 private String homePhone3;
	 private String workPhNo1;
	 private String workPhNo2;
	 private String orgAddress;
	 private String designationPfId;
	 private String albumDesignation;
	 private String books;
	 private String noOfPublicationsNotRefered;
	 private String noOfPublicationsRefered;
	 private String dateOfLeaving;
	 private String dateOfResignation;
	 private String retirementDate;
	 private String emContactName;
	 private String emContactHomeTel;
	 private String emContactMobile;
	 private String emContactAddress;
	 private String emContactWorkTel;
	 private String grossPay;
	 private String scale;
	 private String currentState;
	 private String workLocationId;
	 private String emptypeId;
	 private String maritialStatus;
	 private String bloodGroup;
	 private String otherInfo;
	 private String uId;
	 private String SameAddress;
	 private String empQualificationLevelId;
	 private String reservationCategory;
	 private String isSmartCardDataDelivered;
	 private String isSmartCardDataGenerated;
	 private String code;
	 private String displayInWebsite;
	 private String isPunchingExcemption;
	 private String currentlyWorking;
	 private String subjectSpecialisation;
	 private String staffId;
	

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getSubjectSpecialisation() {
		return subjectSpecialisation;
	}

	public void setSubjectSpecialisation(String subjectSpecialisation) {
		this.subjectSpecialisation = subjectSpecialisation;
	}

	public String getCurrentlyWorking() {
		return currentlyWorking;
	}

	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}

	public String getIsPunchingExcemption() {
		return isPunchingExcemption;
	}

	public void setIsPunchingExcemption(String isPunchingExcemption) {
		this.isPunchingExcemption = isPunchingExcemption;
	}

	public String getDisplayInWebsite() {
		return displayInWebsite;
	}

	public void setDisplayInWebsite(String displayInWebsite) {
		this.displayInWebsite = displayInWebsite;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getIsSmartCardDataDelivered() {
		return isSmartCardDataDelivered;
	}

	public void setIsSmartCardDataDelivered(String isSmartCardDataDelivered) {
		this.isSmartCardDataDelivered = isSmartCardDataDelivered;
	}

	public String getIsSmartCardDataGenerated() {
		return isSmartCardDataGenerated;
	}

	public void setIsSmartCardDataGenerated(String isSmartCardDataGenerated) {
		this.isSmartCardDataGenerated = isSmartCardDataGenerated;
	}

	public String getReservationCategory() {
		return reservationCategory;
	}

	public void setReservationCategory(String reservationCategory) {
		this.reservationCategory = reservationCategory;
	}

	public String getEmpQualificationLevelId() {
		return empQualificationLevelId;
	}

	public void setEmpQualificationLevelId(String empQualificationLevelId) {
		this.empQualificationLevelId = empQualificationLevelId;
	}

	public String getSameAddress() {
		return SameAddress;
	}

	public void setSameAddress(String sameAddress) {
		SameAddress = sameAddress;
	}
	private String nationalityId;
	 
	 

	

	

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	

	public String getOtherInfo() {
		return otherInfo;
	}

	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getMaritialStatus() {
		return maritialStatus;
	}

	public void setMaritialStatus(String maritialStatus) {
		this.maritialStatus = maritialStatus;
	}

	public String getEmContactName() {
		return emContactName;
	}

	public void setEmContactName(String emContactName) {
		this.emContactName = emContactName;
	}

	public String getEmContactHomeTel() {
		return emContactHomeTel;
	}

	public void setEmContactHomeTel(String emContactHomeTel) {
		this.emContactHomeTel = emContactHomeTel;
	}

	public String getEmContactMobile() {
		return emContactMobile;
	}

	public void setEmContactMobile(String emContactMobile) {
		this.emContactMobile = emContactMobile;
	}

	public String getEmContactAddress() {
		return emContactAddress;
	}

	public void setEmContactAddress(String emContactAddress) {
		this.emContactAddress = emContactAddress;
	}

	public String getEmContactWorkTel() {
		return emContactWorkTel;
	}

	public void setEmContactWorkTel(String emContactWorkTel) {
		this.emContactWorkTel = emContactWorkTel;
	}

	public String getGrossPay() {
		return grossPay;
	}

	public void setGrossPay(String grossPay) {
		this.grossPay = grossPay;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getWorkLocationId() {
		return workLocationId;
	}

	public void setWorkLocationId(String workLocationId) {
		this.workLocationId = workLocationId;
	}

	public String getEmptypeId() {
		return emptypeId;
	}

	public void setEmptypeId(String emptypeId) {
		this.emptypeId = emptypeId;
	}

	public String getRetirementDate() {
		return retirementDate;
	}

	public void setRetirementDate(String retirementDate) {
		this.retirementDate = retirementDate;
	}

	public String getDateOfLeaving() {
		return dateOfLeaving;
	}

	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

	public String getDateOfResignation() {
		return dateOfResignation;
	}

	public void setDateOfResignation(String dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}

	public String getNoOfPublicationsRefered() {
		return noOfPublicationsRefered;
	}

	public void setNoOfPublicationsRefered(String noOfPublicationsRefered) {
		this.noOfPublicationsRefered = noOfPublicationsRefered;
	}

	public String getNoOfPublicationsNotRefered() {
		return noOfPublicationsNotRefered;
	}

	public void setNoOfPublicationsNotRefered(String noOfPublicationsNotRefered) {
		this.noOfPublicationsNotRefered = noOfPublicationsNotRefered;
	}

	public String getAlbumDesignation() {
		return albumDesignation;
	}

	public void setAlbumDesignation(String albumDesignation) {
		this.albumDesignation = albumDesignation;
	}

	public String getBooks() {
		return books;
	}

	public void setBooks(String books) {
		this.books = books;
	}

	public String getDesignationPfId() {
		return designationPfId;
	}

	public void setDesignationPfId(String designationPfId) {
		this.designationPfId = designationPfId;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getWorkPhNo2() {
		return workPhNo2;
	}

	public void setWorkPhNo2(String workPhNo2) {
		this.workPhNo2 = workPhNo2;
	}

	public String getWorkPhNo3() {
		return workPhNo3;
	}

	public void setWorkPhNo3(String workPhNo3) {
		this.workPhNo3 = workPhNo3;
	}
	private String workPhNo3;
	 

	public String getWorkPhNo1() {
		return workPhNo1;
	}

	public void setWorkPhNo1(String workPhNo1) {
		this.workPhNo1 = workPhNo1;
	}

	public String getHomePhone2() {
		return homePhone2;
	}

	public void setHomePhone2(String homePhone2) {
		this.homePhone2 = homePhone2;
	}

	public String getHomePhone3() {
		return homePhone3;
	}

	public void setHomePhone3(String homePhone3) {
		this.homePhone3 = homePhone3;
	}

	public String getHomePhone1() {
		return homePhone1;
	}

	public void setHomePhone1(String homePhone1) {
		this.homePhone1 = homePhone1;
	}

	public String getCurrentCountryId() {
		return currentCountryId;
	}

	public void setCurrentCountryId(String currentCountryId) {
		this.currentCountryId = currentCountryId;
	}

	public String getCurrentZipCode() {
		return currentZipCode;
	}

	public void setCurrentZipCode(String currentZipCode) {
		this.currentZipCode = currentZipCode;
	}

	public String getOtherCurrentState() {
		return otherCurrentState;
	}

	public void setOtherCurrentState(String otherCurrentState) {
		this.otherCurrentState = otherCurrentState;
	}

	public String getCurrentCity() {
		return currentCity;
	}

	public void setCurrentCity(String currentCity) {
		this.currentCity = currentCity;
	}

	public String getCurrentAddressLine2() {
		return currentAddressLine2;
	}

	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	public String getCurrentAddressLine1() {
		return currentAddressLine1;
	}

	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	public String getOtherPermanentState() {
		return otherPermanentState;
	}

	public void setOtherPermanentState(String otherPermanentState) {
		this.otherPermanentState = otherPermanentState;
	}

	public String getPermanentZipCode() {
		return permanentZipCode;
	}

	public void setPermanentZipCode(String permanentZipCode) {
		this.permanentZipCode = permanentZipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public EmployeeInfoEditNewForm() {
		    this.employeeInfoEditNewTO=new EmployeeInfoEditNewTO();
		}
	 
public String getGuestId() {
		return guestId;
	}
	public void setGuestId(String guestId) {
		this.guestId = guestId;
	}
public String getSelectedGuestId() {
		return selectedGuestId;
	}
	public void setSelectedGuestId(String selectedGuestId) {
		this.selectedGuestId = selectedGuestId;
	}
public List<UserInfoTO> getUserList() {
		return userList;
	}
	public void setUserList(List<UserInfoTO> userList) {
		this.userList = userList;
	}
public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
public String getFocusValue() {
		return focusValue;
	}
	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}
public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
public String getEmpQualificationListSize() {
		return empQualificationListSize;
	}
	public void setEmpQualificationListSize(String empQualificationListSize) {
		this.empQualificationListSize = empQualificationListSize;
	}
	public String getEmpIntrestListSize() {
		return empIntrestListSize;
	}
	public void setEmpIntrestListSize(String empIntrestListSize) {
		this.empIntrestListSize = empIntrestListSize;
	}
	public String getEmpResearchListSize() {
		return empResearchListSize;
	}
	public void setEmpResearchListSize(String empResearchListSize) {
		this.empResearchListSize = empResearchListSize;
	}
	public String getEmpGuidshipListSize() {
		return empGuidshipListSize;
	}
	public void setEmpGuidshipListSize(String empGuidshipListSize) {
		this.empGuidshipListSize = empGuidshipListSize;
	}
	public String getEmpDutiesPerformedListSize() {
		return empDutiesPerformedListSize;
	}
	public void setEmpDutiesPerformedListSize(String empDutiesPerformedListSize) {
		this.empDutiesPerformedListSize = empDutiesPerformedListSize;
	}
	public String getEmpResearchProjectListSize() {
		return empResearchProjectListSize;
	}
	public void setEmpResearchProjectListSize(String empResearchProjectListSize) {
		this.empResearchProjectListSize = empResearchProjectListSize;
	}
	public String getEmpResearchPublicationListSize() {
		return empResearchPublicationListSize;
	}
	public void setEmpResearchPublicationListSize(
			String empResearchPublicationListSize) {
		this.empResearchPublicationListSize = empResearchPublicationListSize;
	}
	public String getEmpBooksPublishListSize() {
		return empBooksPublishListSize;
	}
	public void setEmpBooksPublishListSize(String empBooksPublishListSize) {
		this.empBooksPublishListSize = empBooksPublishListSize;
	}
	public String getEmpPaperPrsentationListSize() {
		return empPaperPrsentationListSize;
	}
	public void setEmpPaperPrsentationListSize(String empPaperPrsentationListSize) {
		this.empPaperPrsentationListSize = empPaperPrsentationListSize;
	}
	public String getEmpSeminarattendedListSize() {
		return empSeminarattendedListSize;
	}
	public void setEmpSeminarattendedListSize(String empSeminarattendedListSize) {
		this.empSeminarattendedListSize = empSeminarattendedListSize;
	}
	public String getEmpProfessionalDevelopmentListSize() {
		return empProfessionalDevelopmentListSize;
	}
	public void setEmpProfessionalDevelopmentListSize(
			String empProfessionalDevelopmentListSize) {
		this.empProfessionalDevelopmentListSize = empProfessionalDevelopmentListSize;
	}
	public String getEmpAwardListSize() {
		return empAwardListSize;
	}
	public void setEmpAwardListSize(String empAwardListSize) {
		this.empAwardListSize = empAwardListSize;
	}
	public String getEmpMembershipAcademicListSize() {
		return empMembershipAcademicListSize;
	}
	public void setEmpMembershipAcademicListSize(
			String empMembershipAcademicListSize) {
		this.empMembershipAcademicListSize = empMembershipAcademicListSize;
	}
public EmployeeInfoEditNewTO getEmployeeInfoEditNewTO() {
		return employeeInfoEditNewTO;
	}
	public void setEmployeeInfoEditNewTO(EmployeeInfoEditNewTO employeeInfoEditNewTO) {
		this.employeeInfoEditNewTO = employeeInfoEditNewTO;
	}
public int getCurrentExpYears() {
			return currentExpYears;
		}
		public void setCurrentExpYears(int currentExpYears) {
			this.currentExpYears = currentExpYears;
		}
		public int getCurrentExpMonths() {
			return currentExpMonths;
		}
		public void setCurrentExpMonths(int currentExpMonths) {
			this.currentExpMonths = currentExpMonths;
		}
public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	public String getMobileNo1() {
		return mobileNo1;
	}
	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}
	
public String getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}
public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
public String getEmpTypeIdOld() {
		return empTypeIdOld;
	}
	public void setEmpTypeIdOld(String empTypeIdOld) {
		this.empTypeIdOld = empTypeIdOld;
	}
public String getSelectedEmployeeId() {
		return selectedEmployeeId;
	}
	public void setSelectedEmployeeId(String selectedEmployeeId) {
		this.selectedEmployeeId = selectedEmployeeId;
	}
public String getTempTeachingStaff() {
		return tempTeachingStaff;
	}
	public void setTempTeachingStaff(String tempTeachingStaff) {
		this.tempTeachingStaff = tempTeachingStaff;
	}
public String getDesignationId() {
		return designationId;
	}
	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}
	public String getEmpTypeId() {
		return empTypeId;
	}
	public void setEmpTypeId(String empTypeId) {
		this.empTypeId = empTypeId;
	}
	//	private  List empEDucationList;
//	private List empIntrestList;
//	private List empReasearchList;
//	private List empGuidshipDetailsList;
//	private List empDutiesList;
//	private List empResearchList;
//	private List empReasearchPublicationList;
//	private List empBooksPublicationList;
//	private List empPaperPresentationList;
//	private List empSeminarsAttendedList;
//	private List empProfessionalDevList;
//	private List empAwardList;
//	private List empMembershipList;
	
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Map<String, String> getDesignationMap() {
		return designationMap;
	}
	public void setDesignationMap(Map<String, String> designationMap) {
		this.designationMap = designationMap;
	}
	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}
	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}
	public String getExpYears() {
		return expYears;
	}
	public void setExpYears(String expYears) {
		this.expYears = expYears;
	}
	public String getExpMonths() {
		return expMonths;
	}
	public void setExpMonths(String expMonths) {
		this.expMonths = expMonths;
	}
	public String getTeachingStaff() {
		return teachingStaff;
	}
	public void setTeachingStaff(String teachingStaff) {
		this.teachingStaff = teachingStaff;
	}
	public List<EmployeeTO> getEmployeeToList() {
		return employeeToList;
	}
	public void setEmployeeToList(List<EmployeeTO> employeeToList) {
		this.employeeToList = employeeToList;
	}
	 

}
