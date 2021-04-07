package com.kp.cms.to.hostel;

import org.apache.struts.upload.FormFile;

import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.StateTO;

public class HostelTO {
	private int id;
	private String name;
	private String gender;
	private int noOfFloors;
	private String addressLine1;
	private String addressLine2;
	private CountryTO countryTO;
	private StateTO stateTO;
	private String city;
	private int zipCode;
	private String phone;
	private String faxNo;
	private String email;
	private String primaryContactName;
	private String primaryContactDesignation;
	private String primaryContactPhone;
	private String primaryContactMobile;
	private String primaryContactEmail;
	private String secContactName;
	private String secContactDesignation;
	private String secContactPhone;
	private String secContactMobile;
	private String secContactEmail;
	private FormFile theFile;
	private byte[] termsConditions;
	private String stateOthers;
	private String fileName;
	private String contentType;
	private String leaveFrom;
	private String leaveTo;
	private String hostelName;
	private String leaveType;
	private String status;
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeaveFrom() {
		return leaveFrom;
	}

	public void setLeaveFrom(String leaveFrom) {
		this.leaveFrom = leaveFrom;
	}

	public String getLeaveTo() {
		return leaveTo;
	}

	public void setLeaveTo(String leaveTo) {
		this.leaveTo = leaveTo;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getName() {
		return name;
	}
	
	public String getGender() {
		return gender;
	}

	public int getNoOfFloors() {
		return noOfFloors;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public String getCity() {
		return city;
	}
	public int getZipCode() {
		return zipCode;
	}
	public String getPhone() {
		return phone;
	}
	public String getFaxNo() {
		return faxNo;
	}
	public String getEmail() {
		return email;
	}
	public String getPrimaryContactName() {
		return primaryContactName;
	}
	public String getPrimaryContactDesignation() {
		return primaryContactDesignation;
	}
	public String getPrimaryContactPhone() {
		return primaryContactPhone;
	}
	public String getPrimaryContactMobile() {
		return primaryContactMobile;
	}
	public String getPrimaryContactEmail() {
		return primaryContactEmail;
	}
	public String getSecContactName() {
		return secContactName;
	}
	public String getSecContactDesignation() {
		return secContactDesignation;
	}
	public String getSecContactPhone() {
		return secContactPhone;
	}
	public String getSecContactMobile() {
		return secContactMobile;
	}
	public String getSecContactEmail() {
		return secContactEmail;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setNoOfFloors(int noOfFloors) {
		this.noOfFloors = noOfFloors;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPrimaryContactName(String primaryContactName) {
		this.primaryContactName = primaryContactName;
	}
	public void setPrimaryContactDesignation(String primaryContactDesignation) {
		this.primaryContactDesignation = primaryContactDesignation;
	}
	public void setPrimaryContactPhone(String primaryContactPhone) {
		this.primaryContactPhone = primaryContactPhone;
	}
	public void setPrimaryContactMobile(String primaryContactMobile) {
		this.primaryContactMobile = primaryContactMobile;
	}
	public void setPrimaryContactEmail(String primaryContactEmail) {
		this.primaryContactEmail = primaryContactEmail;
	}
	public void setSecContactName(String secContactName) {
		this.secContactName = secContactName;
	}
	public void setSecContactDesignation(String secContactDesignation) {
		this.secContactDesignation = secContactDesignation;
	}
	public void setSecContactPhone(String secContactPhone) {
		this.secContactPhone = secContactPhone;
	}
	public void setSecContactMobile(String secContactMobile) {
		this.secContactMobile = secContactMobile;
	}
	public void setSecContactEmail(String secContactEmail) {
		this.secContactEmail = secContactEmail;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CountryTO getCountryTO() {
		return countryTO;
	}
	public StateTO getStateTO() {
		return stateTO;
	}
	public void setCountryTO(CountryTO countryTO) {
		this.countryTO = countryTO;
	}
	public void setStateTO(StateTO stateTO) {
		this.stateTO = stateTO;
	}
	public FormFile getTheFile() {
		return theFile;
	}
	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}
	public byte[] getTermsConditions() {
		return termsConditions;
	}
	public void setTermsConditions(byte[] termsConditions) {
		this.termsConditions = termsConditions;
	}
	public String getStateOthers() {
		return stateOthers;
	}
	public void setStateOthers(String stateOthers) {
		this.stateOthers = stateOthers;
	}
	public String getFileName() {
		return fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
}
