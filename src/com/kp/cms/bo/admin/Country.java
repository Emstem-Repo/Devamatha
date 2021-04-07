package com.kp.cms.bo.admin;

// Generated Jan 22, 2009 3:35:47 PM by Hibernate Tools 3.2.0.b9

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Country generated by hbm2java
 */
public class Country implements java.io.Serializable {

	private int id;
	private String createdBy;;
	private String modifiedBy;
	private String name;
	private Date createdDate;
	private Date lastModifiedDate;
	private Boolean isActive;
	
	
	private Set<PersonalData> personalDatasForGuardianAddressCountryId = new HashSet<PersonalData>(
			0);
	private Set<State> states = new HashSet<State>(0);
	private Set<InvVendor> invVendors = new HashSet<InvVendor>(0);
	private Set<WeightageDefinition> weightageDefinitions = new HashSet<WeightageDefinition>(
			0);
	private Set<Employee> employeesForCommunicationAddressCountryId = new HashSet<Employee>(
			0);
	private Set<EmpImmigration> empImmigrationsForPassportCountryId = new HashSet<EmpImmigration>(
			0);
	private Set<EmpImmigration> empImmigrationsForVisaCountryId = new HashSet<EmpImmigration>(
			0);
	private Set<Employee> employeesForPermanentAddressCountryId = new HashSet<Employee>(
			0);
	private Set<HlHostel> hlHostels = new HashSet<HlHostel>(0);
	private Set<PersonalData> personalDatasForParentAddressCountryId = new HashSet<PersonalData>(
			0);
	private Set<PersonalData> personalDatasForPermanentAddressCountryId = new HashSet<PersonalData>(
			0);
	private Set<Recommendor> recommendors = new HashSet<Recommendor>(0);
	private Set<EmpOnlineResume> empOnlineResumes = new HashSet<EmpOnlineResume>(
			0);
	private Set<PersonalData> personalDatasForCurrentAddressCountryId = new HashSet<PersonalData>(
			0);
	private Set<Address> addresses = new HashSet<Address>(0);
	private Set<PersonalData> personalDatasForPassportCountryId = new HashSet<PersonalData>(
			0);
	private Set<PersonalData> personalDatasForCountryId = new HashSet<PersonalData>(
			0);
	private String bankCCode;

	public Country() {
	}

	public Country(int id) {
		this.id = id;
	}

	public Country(int id, String name, String createdBy, Date createdDate,
			String modifiedBy, Date lastModifiedDate, Boolean isActive,
			Set<PersonalData> personalDatasForGuardianAddressCountryId,
			Set<State> states, Set<InvVendor> invVendors,
			Set<WeightageDefinition> weightageDefinitions,
			Set<Employee> employeesForCommunicationAddressCountryId,
			Set<EmpImmigration> empImmigrationsForPassportCountryId,
			Set<EmpImmigration> empImmigrationsForVisaCountryId,
			Set<Employee> employeesForPermanentAddressCountryId,
			Set<HlHostel> hlHostels,
			Set<PersonalData> personalDatasForParentAddressCountryId,
			Set<PersonalData> personalDatasForPermanentAddressCountryId,
			Set<Recommendor> recommendors,
			Set<EmpOnlineResume> empOnlineResumes,
			Set<PersonalData> personalDatasForCurrentAddressCountryId,
			Set<Address> addresses,
			Set<PersonalData> personalDatasForPassportCountryId,
			Set<PersonalData> personalDatasForCountryId) {
		this.id = id;
		this.name = name;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.lastModifiedDate = lastModifiedDate;
		this.isActive = isActive;
		this.personalDatasForGuardianAddressCountryId = personalDatasForGuardianAddressCountryId;
		this.states = states;
		this.invVendors = invVendors;
		this.weightageDefinitions = weightageDefinitions;
		this.employeesForCommunicationAddressCountryId = employeesForCommunicationAddressCountryId;
		this.empImmigrationsForPassportCountryId = empImmigrationsForPassportCountryId;
		this.empImmigrationsForVisaCountryId = empImmigrationsForVisaCountryId;
		this.employeesForPermanentAddressCountryId = employeesForPermanentAddressCountryId;
		this.hlHostels = hlHostels;
		this.personalDatasForParentAddressCountryId = personalDatasForParentAddressCountryId;
		this.personalDatasForPermanentAddressCountryId = personalDatasForPermanentAddressCountryId;
		this.recommendors = recommendors;
		this.empOnlineResumes = empOnlineResumes;
		this.personalDatasForCurrentAddressCountryId = personalDatasForCurrentAddressCountryId;
		this.addresses = addresses;
		this.personalDatasForPassportCountryId = personalDatasForPassportCountryId;
		this.personalDatasForCountryId = personalDatasForCountryId;
	}


	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy)  {
		this.createdBy = createdBy;
	}

	public String getModifiedBy()  {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return this.lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Set<PersonalData> getPersonalDatasForCurrentAddressCountryId() {
		return this.personalDatasForCurrentAddressCountryId;
	}

	public void setPersonalDatasForCurrentAddressCountryId(
			Set<PersonalData> personalDatasForCurrentAddressCountryId) {
		this.personalDatasForCurrentAddressCountryId = personalDatasForCurrentAddressCountryId;
	}

	

	public Set<State> getStates() {
		return this.states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	public Set<PersonalData> getPersonalDatasForPassportCountryId() {
		return this.personalDatasForPassportCountryId;
	}

	public void setPersonalDatasForPassportCountryId(
			Set<PersonalData> personalDatasForPassportCountryId) {
		this.personalDatasForPassportCountryId = personalDatasForPassportCountryId;
	}

	public Set<Address> getAddresses() {
		return this.addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<PersonalData> getPersonalDatasForCountryId() {
		return this.personalDatasForCountryId;
	}

	public void setPersonalDatasForCountryId(
			Set<PersonalData> personalDatasForCountryId) {
		this.personalDatasForCountryId = personalDatasForCountryId;
	}

	public Set<PersonalData> getPersonalDatasForPermanentAddressCountryId() {
		return this.personalDatasForPermanentAddressCountryId;
	}

	public void setPersonalDatasForPermanentAddressCountryId(
			Set<PersonalData> personalDatasForPermanentAddressCountryId) {
		this.personalDatasForPermanentAddressCountryId = personalDatasForPermanentAddressCountryId;
	}

	public Set<PersonalData> getPersonalDatasForParentAddressCountryId() {
		return this.personalDatasForParentAddressCountryId;
	}

	public void setPersonalDatasForParentAddressCountryId(
			Set<PersonalData> personalDatasForParentAddressCountryId) {
		this.personalDatasForParentAddressCountryId = personalDatasForParentAddressCountryId;
	}

	public Set<Recommendor> getRecommendors() {
		return this.recommendors;
	}

	public void setRecommendors(Set<Recommendor> recommendors) {
		this.recommendors = recommendors;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public Set<HlHostel> getHlHostels() {
		return hlHostels;
	}

	public void setHlHostels(Set<HlHostel> hlHostels) {
		this.hlHostels = hlHostels;
	}

	public Set<InvVendor> getInvVendors() {
		return invVendors;
	}

	public void setInvVendors(Set<InvVendor> invVendors) {
		this.invVendors = invVendors;
	}

	public Set<EmpOnlineResume> getEmpOnlineResumes() {
		return empOnlineResumes;
	}

	public void setEmpOnlineResumes(Set<EmpOnlineResume> empOnlineResumes) {
		this.empOnlineResumes = empOnlineResumes;
	}

	public Set<Employee> getEmployeesForPermanentAddressCountryId() {
		return employeesForPermanentAddressCountryId;
	}

	public void setEmployeesForPermanentAddressCountryId(
			Set<Employee> employeesForPermanentAddressCountryId) {
		this.employeesForPermanentAddressCountryId = employeesForPermanentAddressCountryId;
	}

	public Set<PersonalData> getPersonalDatasForGuardianAddressCountryId() {
		return personalDatasForGuardianAddressCountryId;
	}

	public void setPersonalDatasForGuardianAddressCountryId(
			Set<PersonalData> personalDatasForGuardianAddressCountryId) {
		this.personalDatasForGuardianAddressCountryId = personalDatasForGuardianAddressCountryId;
	}

	public Set<WeightageDefinition> getWeightageDefinitions() {
		return weightageDefinitions;
	}

	public void setWeightageDefinitions(
			Set<WeightageDefinition> weightageDefinitions) {
		this.weightageDefinitions = weightageDefinitions;
	}

	public Set<Employee> getEmployeesForCommunicationAddressCountryId() {
		return employeesForCommunicationAddressCountryId;
	}

	public void setEmployeesForCommunicationAddressCountryId(
			Set<Employee> employeesForCommunicationAddressCountryId) {
		this.employeesForCommunicationAddressCountryId = employeesForCommunicationAddressCountryId;
	}

	public Set<EmpImmigration> getEmpImmigrationsForPassportCountryId() {
		return empImmigrationsForPassportCountryId;
	}

	public void setEmpImmigrationsForPassportCountryId(
			Set<EmpImmigration> empImmigrationsForPassportCountryId) {
		this.empImmigrationsForPassportCountryId = empImmigrationsForPassportCountryId;
	}

	public Set<EmpImmigration> getEmpImmigrationsForVisaCountryId() {
		return empImmigrationsForVisaCountryId;
	}

	public void setEmpImmigrationsForVisaCountryId(
			Set<EmpImmigration> empImmigrationsForVisaCountryId) {
		this.empImmigrationsForVisaCountryId = empImmigrationsForVisaCountryId;
	}

	public String getBankCCode() {
		return bankCCode;
	}

	public void setBankCCode(String bankCCode) {
		this.bankCCode = bankCCode;
	}

}
