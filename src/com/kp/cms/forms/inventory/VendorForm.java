package com.kp.cms.forms.inventory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.InvVendor;
import com.kp.cms.forms.BaseActionForm;

public class VendorForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String vendorName;
	private String contactPerson;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String zipCode;
	private String emailId;
	private String tan;
	private String pan;
	private String pin;
	private String vat;
	private String bankAcNo;
	private String bankBranch;
	private String paymentTerms;
	private String deliverySchedule;
	private String phone;
	private String mobile;
	private String authorisedVendor;
	private String fax;
	private String paymentMode;
	private String[] selectedCategory;
	private Map<Integer, String>categoryMap;
	private InvVendor invVendor;
	private int duplId;
	private String countryName;
	private String stateName;
	private String stateOthers;
	private String remarks;
	private String phoneCountryCode;
	private String phoneStateCode;
	private String mobileCountryCode;
	
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public String getVendorName() {
		return vendorName;
	}
	public String getContactPerson() {
		return contactPerson;
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
	public String getTan() {
		return tan;
	}
	public String getPan() {
		return pan;
	}
	public String getPin() {
		return pin;
	}
	public String getVat() {
		return vat;
	}
	public String getBankAcNo() {
		return bankAcNo;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public String getPaymentTerms() {
		return paymentTerms;
	}
	public String getDeliverySchedule() {
		return deliverySchedule;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
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
	public void setTan(String tan) {
		this.tan = tan;
	}
	public void setPan(String pan) {
		this.pan = pan;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public void setVat(String vat) {
		this.vat = vat;
	}
	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}
	public void setDeliverySchedule(String deliverySchedule) {
		this.deliverySchedule = deliverySchedule;
	}
	public String getPhone() {
		return phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAuthorisedVendor() {
		return authorisedVendor;
	}
	public void setAuthorisedVendor(String authorisedVendor) {
		this.authorisedVendor = authorisedVendor;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public Map<Integer, String> getCategoryMap() {
		return categoryMap;
	}
	public void setCategoryMap(Map<Integer, String> categoryMap) {
		this.categoryMap = categoryMap;
	}
	public String[] getSelectedCategory() {
		return selectedCategory;
	}
	public void setSelectedCategory(String[] selectedCategory) {
		this.selectedCategory = selectedCategory;
	}
	public InvVendor getInvVendor() {
		return invVendor;
	}
	public void setInvVendor(InvVendor invVendor) {
		this.invVendor = invVendor;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public String getCountryName() {
		return countryName;
	}
	public String getStateName() {
		return stateName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStateOthers() {
		return stateOthers;
	}
	public void setStateOthers(String stateOthers) {
		this.stateOthers = stateOthers;
	}
	public String getPhoneCountryCode() {
		return phoneCountryCode;
	}
	public void setPhoneCountryCode(String phoneCountryCode) {
		this.phoneCountryCode = phoneCountryCode;
	}
	public String getPhoneStateCode() {
		return phoneStateCode;
	}
	public void setPhoneStateCode(String phoneStateCode) {
		this.phoneStateCode = phoneStateCode;
	}
	public String getMobileCountryCode() {
		return mobileCountryCode;
	}
	public void setMobileCountryCode(String mobileCountryCode) {
		this.mobileCountryCode = mobileCountryCode;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		selectedCategory = null;
	}
}
