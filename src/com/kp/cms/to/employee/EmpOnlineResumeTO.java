package com.kp.cms.to.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.employee.EmpOnlineEducationalDetails;

public class EmpOnlineResumeTO {
	private int id;
	private String name;
	private String gender;
	private String nationality;
	private String address;
	private Integer age;
	private String dateofBirth;
	private String email;
	private String maritalStatus;
	private String country;
	private String city;
	private String phone;
	private String mobileNo;
	private String zipCode;
	private String acheivementName;
	private String details;
	private String employmentStatus;
	private String dateOfJoining;
	private String recommendedBy;
	private String currentlyWorking;
	private String currentDesignation;
	private String currentOrganization;
	private int totalExpYear;
	private int totalExpMonths;
	private String qualificationName;
	private String empJobType;
	private String empDesiredPost;
	private String empPreviousOrg;
	private String teachingExperience;
	private String industryExperience;
	private String expectedSalary;
	private Integer expectedSalaryPerMonth;
	private String department;
	private String empFunctionalArea;
	private String informationKnown;
	private String totExp;
	private List<EmpEducationalDetailsTO> educationalDetailsSet = new ArrayList<EmpEducationalDetailsTO>();
	private String age1;
	private String applnNo;
	private String appliedDate;
	private String joiningTime;
	private String eligibilityTest;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getDateofBirth() {
		return dateofBirth;
	}
	public void setDateofBirth(String dateofBirth) {
		this.dateofBirth = dateofBirth;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	
	public String getAcheivementName() {
		return acheivementName;
	}
	public void setAcheivementName(String acheivementName) {
		this.acheivementName = acheivementName;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	
	public String getRecommendedBy() {
		return recommendedBy;
	}
	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}
	public String isCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setCurrentlyWorking(String currentlyWorking) {
		this.currentlyWorking = currentlyWorking;
	}
	public String getCurrentDesignation() {
		return currentDesignation;
	}
	public void setCurrentDesignation(String currentDesignation) {
		this.currentDesignation = currentDesignation;
	}
	public String getCurrentOrganization() {
		return currentOrganization;
	}
	public void setCurrentOrganization(String currentOrganization) {
		this.currentOrganization = currentOrganization;
	}
	public int getTotalExpYear() {
		return totalExpYear;
	}
	public void setTotalExpYear(int totalExpYear) {
		this.totalExpYear = totalExpYear;
	}
	public int getTotalExpMonths() {
		return totalExpMonths;
	}
	public void setTotalExpMonths(int totalExpMonths) {
		this.totalExpMonths = totalExpMonths;
	}
	public String getQualificationName() {
		return qualificationName;
	}
	public void setQualificationName(String qualificationName) {
		this.qualificationName = qualificationName;
	}
	public String getEmpJobType() {
		return empJobType;
	}
	public void setEmpJobType(String empJobType) {
		this.empJobType = empJobType;
	}
	public void setEmpDesiredPost(String empDesiredPost) {
		this.empDesiredPost = empDesiredPost;
	}
	public String getEmpDesiredPost() {
		return empDesiredPost;
	}
	public void setEmpPreviousOrg(String empPreviousOrg) {
		this.empPreviousOrg = empPreviousOrg;
	}
	public String getEmpPreviousOrg() {
		return empPreviousOrg;
	}
	public String getCurrentlyWorking() {
		return currentlyWorking;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	public String getExpectedSalary() {
		return expectedSalary;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDepartment() {
		return department;
	}
	public void setEmpFunctionalArea(String empFunctionalArea) {
		this.empFunctionalArea = empFunctionalArea;
	}
	public String getEmpFunctionalArea() {
		return empFunctionalArea;
	}
	public void setEducationalDetailsSet(List<EmpEducationalDetailsTO> educationalDetailsSet) {
		this.educationalDetailsSet = educationalDetailsSet;
	}
	public List<EmpEducationalDetailsTO> getEducationalDetailsSet() {
		return educationalDetailsSet;
	}
	public void setInformationKnown(String informationKnown) {
		this.informationKnown = informationKnown;
	}
	public String getInformationKnown() {
		return informationKnown;
	}
	public void setTotExp(String totExp) {
		this.totExp = totExp;
	}
	public String getTotExp() {
		return totExp;
	}
	public void setTeachingExperience(String teachingExperience) {
		this.teachingExperience = teachingExperience;
	}
	public String getTeachingExperience() {
		return teachingExperience;
	}
	public void setIndustryExperience(String industryExperience) {
		this.industryExperience = industryExperience;
	}
	public String getIndustryExperience() {
		return industryExperience;
	}
	public void setExpectedSalaryPerMonth(Integer expectedSalaryPerMonth) {
		this.expectedSalaryPerMonth = expectedSalaryPerMonth;
	}
	public Integer getExpectedSalaryPerMonth() {
		return expectedSalaryPerMonth;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge1(String age1) {
		this.age1 = age1;
	}
	public String getAge1() {
		return age1;
	}
	public String getApplnNo() {
		return applnNo;
	}
	public void setApplnNo(String applnNo) {
		this.applnNo = applnNo;
	}
	public String getAppliedDate() {
		return appliedDate;
	}
	public void setAppliedDate(String appliedDate) {
		this.appliedDate = appliedDate;
	}
	public void setJoiningTime(String joiningTime) {
		this.joiningTime = joiningTime;
	}
	public String getJoiningTime() {
		return joiningTime;
	}
	public String getEligibilityTest() {
		return eligibilityTest;
	}
	public void setEligibilityTest(String eligibilityTest) {
		this.eligibilityTest = eligibilityTest;
	}
	
	
	
	
}
