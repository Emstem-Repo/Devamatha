package com.kp.cms.forms.alumini;

import java.util.List;

import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.to.alumni.AlumniEducationDetailsTO;

@SuppressWarnings("serial")
public class AluminiRegistrationForm extends OnlineApplicationForm {

	private String currentStatus;
	private String companyName;
	private String designation;
	private String otherJobDetails;
	private String countryName;
	private String city;
	private List<AlumniEducationDetailsTO> educationDetails;
	private int educationDetailsSize;
	private String highestQualification;
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getOtherJobDetails() {
		return otherJobDetails;
	}
	public void setOtherJobDetails(String otherJobDetails) {
		this.otherJobDetails = otherJobDetails;
	}
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public List<AlumniEducationDetailsTO> getEducationDetails() {
		return educationDetails;
	}
	public void setEducationDetails(List<AlumniEducationDetailsTO> educationDetails) {
		this.educationDetails = educationDetails;
	}
	public int getEducationDetailsSize() {
		return educationDetailsSize;
	}
	public void setEducationDetailsSize(int educationDetailsSize) {
		this.educationDetailsSize = educationDetailsSize;
	}
	public String getHighestQualification() {
		return highestQualification;
	}
	public void setHighestQualification(String highestQualification) {
		this.highestQualification = highestQualification;
	}
	public void clear() {
		super.clear();
		setFirstName(null);
		setMobileNo1(null);
		setMobileNo2(null);
		setEmailId(null);
		currentStatus = null;
		companyName = null;
		designation = null;
		otherJobDetails = null;
		countryName = null;
		city = null;
		educationDetails = null;
		highestQualification = null;
	}
}
