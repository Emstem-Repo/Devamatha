package com.kp.cms.forms.employee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.EdicationDetailsTO;
import com.kp.cms.to.employee.JobTypeTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;

@SuppressWarnings("serial")
public class OnlineResumerSubmissionForm extends BaseActionForm{
	
	

	private String name;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String nationalityId;
	private String zipCode;
	private String gender;
	private String countryId;
	private String maritalStatus;
	private String city;
	private String dateOfBirth;
	private String phone1;
	private String phone2;
	private String phone3;
	private String age;
	private String mobPhone1;
	private String mobPhone2;
	private String mobPhone3;
	private String email;
	
	private String employmentStatus;
	private String expectedSalaryLack;
	private String expectedSalaryThousands;
	private String desiredPost;
	private String departmentAppliedFor;
	private String dateOfJoining;
	private String vacancyType;
	private String recommendedBy;
	private FormFile photo;
	private String jobType;
	
	private Map<Integer, String> listNationalityMap;
	private Map<Integer, String> listCountryMap;
	
	private Map<Integer, String> listEdicationMap;
	private Map<Integer, String> listDepartmentMap;
	
	private Map<Integer, String> listDesignationMap;
	private Map<Integer, String> listQualificationMap;
	private Map<Integer, String> listfunctionalAreaMap;
	
	
	
	private Map<Integer, String> listDQualificationMap;
	private List<EdicationDetailsTO> listOfEdicationDetails;
	private List<ProfessionalExperienceTO> listOfProfessionalExperience;
	private List<AchievementsTO> listOfAchievements;
	private List<JobTypeTO> listOfJobType;
	private Map<Integer, String> stateMap;
	private String state;
	private String otherState;
	
	public Map<Integer, String> getListNationalityMap() {
		return listNationalityMap;
	}

	public void setListNationalityMap(Map<Integer, String> listNationalityMap) {
		this.listNationalityMap = listNationalityMap;
	}

	public Map<Integer, String> getListCountryMap() {
		return listCountryMap;
	}

	public void setListCountryMap(Map<Integer, String> listCountryMap) {
		this.listCountryMap = listCountryMap;
	}

	

	public Map<Integer, String> getListEdicationMap() {
		return listEdicationMap;
	}

	public void setListEdicationMap(Map<Integer, String> listEdicationMap) {
		this.listEdicationMap = listEdicationMap;
	}

	public Map<Integer, String> getListDepartmentMap() {
		return listDepartmentMap;
	}

	public void setListDepartmentMap(Map<Integer, String> listDepartmentMap) {
		this.listDepartmentMap = listDepartmentMap;
	}

	public List<EdicationDetailsTO> getListOfEdicationDetails() {
		return listOfEdicationDetails;
	}

	public void setListOfEdicationDetails(
			List<EdicationDetailsTO> listOfEdicationDetails) {
		this.listOfEdicationDetails = listOfEdicationDetails;
	}

	public void setListOfProfessionalExperience(
			List<ProfessionalExperienceTO> listOfProfessionalExperience) {
		this.listOfProfessionalExperience = listOfProfessionalExperience;
	}

	public List<ProfessionalExperienceTO> getListOfProfessionalExperience() {
		return listOfProfessionalExperience;
	}

	public void setListOfAchievements(List<AchievementsTO> listOfAchievements) {
		this.listOfAchievements = listOfAchievements;
	}

	public List<AchievementsTO> getListOfAchievements() {
		return listOfAchievements;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
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

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getExpectedSalaryLack() {
		return expectedSalaryLack;
	}

	public void setExpectedSalaryLack(String expectedSalaryLack) {
		this.expectedSalaryLack = expectedSalaryLack;
	}

	public String getExpectedSalaryThousands() {
		return expectedSalaryThousands;
	}

	public void setExpectedSalaryThousands(String expectedSalaryThousands) {
		this.expectedSalaryThousands = expectedSalaryThousands;
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

	

	public List<JobTypeTO> getListOfJobType() {
		return listOfJobType;
	}

	public void setListOfJobType(List<JobTypeTO> listOfJobType) {
		this.listOfJobType = listOfJobType;
	}

	
	public Map<Integer, String> getListfunctionalAreaMap() {
		return listfunctionalAreaMap;
	}

	public void setListfunctionalAreaMap(Map<Integer, String> listfunctionalAreaMap) {
		this.listfunctionalAreaMap = listfunctionalAreaMap;
	}

	public Map<Integer, String> getListDQualificationMap() {
		return listDQualificationMap;
	}

	public void setListDQualificationMap(Map<Integer, String> listDQualificationMap) {
		this.listDQualificationMap = listDQualificationMap;
	}

	public void setListDesignationMap(Map<Integer, String> listDesignationMap) {
		this.listDesignationMap = listDesignationMap;
	}

	public Map<Integer, String> getListDesignationMap() {
		return listDesignationMap;
	}

	public void setListQualificationMap(Map<Integer, String> listQualificationMap) {
		this.listQualificationMap = listQualificationMap;
	}

	public Map<Integer, String> getListQualificationMap() {
		return listQualificationMap;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobType() {
		return jobType;
	}

	public void setPhoto(FormFile photo) {
		this.photo = photo;
	}

	public FormFile getPhoto() {
		return photo;
	}
	public Map<Integer, String> getStateMap() {
		return stateMap;
	}

	public void setStateMap(Map<Integer, String> stateMap) {
		this.stateMap = stateMap;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getOtherState() {
		return otherState;
	}

	public void setOtherState(String otherState) {
		this.otherState = otherState;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts.validator.ValidatorForm#validate(org.apache.struts.action.ActionMapping, javax.servlet.http.HttpServletRequest)
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
}
