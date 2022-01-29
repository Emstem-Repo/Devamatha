package com.kp.cms.to.employee;

import java.util.List;

import org.apache.struts.upload.FormFile;

public class InterviewCommentsTO {
	private int id;
	private String name;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String nationality;
	private String zipCode;
	private String gender;
	private String country;
	private String maritalStatus;
	private String city;
	private String dateOfBirth;
	private String phone1;
	private String phone2;
	private String phone3;
	private int age;
	private String mobPhone1;
	private String mobPhone2;
	private String mobPhone3;
	private String email;
	
	private String employmentStatus;
	private String expectedSalary;
	private String desiredPost;
	private String departmentAppliedFor;
	private String dateOfJoining;
	private String vacancyType;
	private String recommendedBy;
	
	private String jobType;
	private String status;
	
	
	
	private List<EdicationDetailsTO> listOfEdicationDetails;
	private List<ProfessionalExperienceTO> listOfProfessionalExperience;
	private List<AchievementsTO> listOfAchievements;
	
	public InterviewCommentsTO() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getAddressLine3() {
		return addressLine3;
	}
	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public String getPhone3() {
		return phone3;
	}
	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getMobPhone1() {
		return mobPhone1;
	}
	public void setMobPhone1(String mobPhone1) {
		this.mobPhone1 = mobPhone1;
	}
	public String getMobPhone2() {
		return mobPhone2;
	}
	public void setMobPhone2(String mobPhone2) {
		this.mobPhone2 = mobPhone2;
	}
	public String getMobPhone3() {
		return mobPhone3;
	}
	public void setMobPhone3(String mobPhone3) {
		this.mobPhone3 = mobPhone3;
	}
	public String getEmploymentStatus() {
		return employmentStatus;
	}
	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}
	public String getExpectedSalary() {
		return expectedSalary;
	}
	public void setExpectedSalary(String expectedSalary) {
		this.expectedSalary = expectedSalary;
	}
	
	public String getDesiredPost() {
		return desiredPost;
	}
	public void setDesiredPost(String desiredPost) {
		this.desiredPost = desiredPost;
	}
	public String getDepartmentAppliedFor() {
		return departmentAppliedFor;
	}
	public void setDepartmentAppliedFor(String departmentAppliedFor) {
		this.departmentAppliedFor = departmentAppliedFor;
	}
	public String getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(String dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public String getVacancyType() {
		return vacancyType;
	}
	public void setVacancyType(String vacancyType) {
		this.vacancyType = vacancyType;
	}
	public String getRecommendedBy() {
		return recommendedBy;
	}
	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}
	
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	public List<EdicationDetailsTO> getListOfEdicationDetails() {
		return listOfEdicationDetails;
	}
	public void setListOfEdicationDetails(
			List<EdicationDetailsTO> listOfEdicationDetails) {
		this.listOfEdicationDetails = listOfEdicationDetails;
	}
	public List<ProfessionalExperienceTO> getListOfProfessionalExperience() {
		return listOfProfessionalExperience;
	}
	public void setListOfProfessionalExperience(
			List<ProfessionalExperienceTO> listOfProfessionalExperience) {
		this.listOfProfessionalExperience = listOfProfessionalExperience;
	}
	public List<AchievementsTO> getListOfAchievements() {
		return listOfAchievements;
	}
	public void setListOfAchievements(List<AchievementsTO> listOfAchievements) {
		this.listOfAchievements = listOfAchievements;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
