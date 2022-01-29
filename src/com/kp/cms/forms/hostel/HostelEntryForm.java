package com.kp.cms.forms.hostel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;

public class HostelEntryForm extends BaseActionForm {
	private int id;
	private String name;
	private String gender;
	private String noOfFloors;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String zipCode;
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
	private FormFile thefile;
	private int duplId;
	private String stateOthers;
	private String fileName;
	private String contentType;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getNoOfFloors() {
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
	public String getZipCode() {
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
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setNoOfFloors(String noOfFloors) {
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
	public void setZipCode(String zipCode) {
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
	
	public FormFile getThefile() {
		return thefile;
	}
	public void setThefile(FormFile thefile) {
		this.thefile = thefile;
	}
	
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.name = null; 
		this.gender = null;
		this.noOfFloors = null;
		this.addressLine1 = null;
		this.addressLine2 = null;
		this.city = null;
		this.zipCode = null;
		this.phone = null;
		this.faxNo = null;
		this.email = null;
		this.primaryContactName = null;
		this.primaryContactDesignation = null;
		this.primaryContactPhone = null;
		this.primaryContactMobile = null;
		this.primaryContactEmail = null;
		this.secContactName = null;
		this.secContactDesignation = null;
		this.secContactPhone = null;
		this.secContactMobile = null;
		this.secContactEmail = null;
		super.setCountryId(null);
		super.setStateId(null);
		this.setStateOthers(null);
		
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
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
