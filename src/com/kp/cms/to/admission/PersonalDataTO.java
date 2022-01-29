package com.kp.cms.to.admission;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.StudentExtracurricular;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.LanguageTO;
import com.kp.cms.to.admin.ReligionSectionTO;
import com.kp.cms.to.admin.ReligionTO;
/**
 * 
 *
 * TO Class for PersonalData BO
 * 
 */
public class PersonalDataTO {

	private ReligionSectionTO religionSection;
	private int id;
	private LanguageTO language;
	private CasteTO caste;
	private ReligionTO religion;
	private String firstName;
	private String middleName;
	private String lastName;
	private Date dateOfBirth;
	private String gender;
	private String birthPlace;
	private String birthState;
	private String birthCountry;
	private String bloodGroup;
	private String passportNo;
	private String residentPermitNo;
	private String passportValidity;
	private String residentPermitDate;
	private int passportCountry;
	private String mobileNo1;
	private String mobileNo2;
	private String mobileNo3;
	private String email;
	//for single page appln added
	private String confirmEmail;
	private String phNo1;
	private String phNo2;
	private String phNo3;
	private String citizenship;
	private String residentCategory;
	private String languageOthers;
	private String createdBy;
	private Date createdDate;
	private Integer modifiedBy;
	private Date lastModifiedDate;
	private Integer admittedThroughId;
	private String mobileNo;
	private String landlineNo;
	private String permanentAddressLine1;
	private String permanentAddressLine2;
	private String currentAddressLine1;
	private String currentAddressLine2;
	private String parentAddressLine1;
	private String parentAddressLine2;
	private String parentAddressLine3;
	private String permanentAddressZipCode;
	private String currentAddressZipCode;
	private String parentAddressZipCode;
	private String fatherName;
	private String motherName;
	private String fatherEducation;
	private String motherEducation;
	private String fatherEmail;
	private String motherEmail;
	private String parentPhone;
	private String parentMobile;
	private String stateOfBirth;
	private String countryOfBirth;
	private String passportIssuingCountry;
	private String permanentCityName;
	private String permanentStateName;
	private String permanentCountryName;
	private String currentCityName;
	private String currentStateName;
	private String currentCountryName;
	private String parentCityName;
	private String parentStateName;
	private String parentCountryName;
	private String guardianStateName;
	private String guardianCountryName;
	private String fatherIncome;
	private String fatherOccupation;
	private String fatherOccupationId;
	private String motherIncome;
	private String motherOccupation;
	private String motherOccupationId;
	private char ruralUrban;
	private String parentPh1;
	private String parentPh2;
	private String parentPh3;
	private String parentMob1;
	private String parentMob2;
	private String parentMob3;
	private String nationality;
	private String religionId;
	private String subReligionId;
	private String casteId;
	private String nationalityOthers;
	private String residentCategoryName;
	private String religionName;
	private String subregligionName;
	private String casteCategory;
	private String dob;
	private String belongsTo;
	private char areaType;
	private String stateOthers;
	private String permanentAddressStateOthers;
	private String currentAddressStateOthers;
	private String parentAddressStateOthers;
	private String casteOthers;
	private String religionOthers;
	private String religionSectionOthers;
	private String permanentStateId;
	private int permanentCountryId;
	private String currentStateId;
	private int currentCountryId;
	private String parentStateId;
	private int parentCountryId;
	private String fatherIncomeId;
	private String motherIncomeId;
	private String fatherCurrencyId;
	private String motherCurrencyId;
	private String fatherIncomeRange;
	private String motherIncomeRange;
	private String fatherCurrency;
	private String motherCurrency;
	private boolean sportsPerson;
	private boolean handicapped;
	private String guardianAddressLine1;
	private String guardianAddressLine2;
	private String guardianAddressLine3;
	private String guardianPh1;
	private String guardianPh2;
	private String guardianPh3;
	private String guardianMob1;
	private String guardianMob2;
	private String guardianMob3;
	private int countryByGuardianAddressCountryId;	
	private String stateByGuardianAddressStateId;
	private String cityByGuardianAddressCityId;
	private String guardianAddressZipCode;
	private String guardianAddressStateOthers;
	private String guardianAddressCountryOthers;
	private String sportsDescription;
	private String hadnicappedDescription;
	
	private String height;
	private String weight;
	private String languageByLanguageWrite;
	private String languageByLanguageSpeak;
	private String languageByLanguageRead;
	private String motherTongue;
	
	private String trainingProgName;
	private String trainingInstAddress;
	private String trainingPurpose;
	private String trainingDuration;
	
	private String courseKnownBy;
	private String courseOptReason;
	private String strength;
	private String weakness;
	private String otherAddnInfo;
	
	private String secondLanguage;
	
	private String brotherName;
	private String brotherEducation;
	private String brotherIncome;
	private String brotherOccupation;
	private String brotherAge;
	private String sisterName;
	private String sisterEducation;
	private String sisterIncome;
	private String sisterOccupation;
	private String sisterAge;
	private String guardianName;
	private String employeDesignation;
	private String employeeDepartment;
	private String workEmail;
	private String rollNo;
	private String registerNo;
	private String studentClass;
	
	private List<StudentExtracurricular> studentExtracurriculars;
	List<ExtracurricularActivityTO> studentExtracurricularsTos;
	private String[] extracurricularIds;
	private String extracurricularNames;
	private boolean isRecommended;
	private String subCaste;
	private String universityEmail;
	private String otherOccupationFather;
	private String otherOccupationMother;
	private String fatherTitle;
	private String motherTitle;
	private String motherTongueName;
	private String parishanddiocese;
	private String nccgrades;
	private boolean nsscertificate;
	private boolean exservice;
	private boolean ncccertificate;
	private String dioceseId;
    private String parishId;
    private String dioceseName;
    private String parishName;
    private String permanentDistricId;
    private String permanentDistricName;
    private String currentDistricName;
    private String currentDistricId;
    private String permanentAddressDistrictOthers;
	private String currentAddressDistrictOthers;
	private String parishOthers;
	private String dioceseOthers;
	
	private String ugcourse;
	private String stream;
	private String motherMobile;
	private String fatherMobile;
	private String sports;
	private String arts;
	private String sportsParticipate;
	private String artsParticipate;
	private String reservation;
	private String reservation1;
	private String currentStreet;
	private String permanentStreet;
	private String fatherOccupationAddress;
	private String motherOccupationAddress;
	private String noofenglishCoreCourses;
	
	private String aadhaarCardNumber;
	private String motherTongueId;
	private Map<Integer, String> motherTongues;
	private String fatherPANNumber;
	private String fatherAadhaarNumber;
	private Boolean ismgquota;
	private String recommentedBy;
	private String recommentedPersonDesignation;
	private String recommentedPersonMobile;
	private String nssgrades;
	private String preferenceNoCAP;
	private String titleOfFather;
	private String titleOfMother;
	private String mbaEntranceExamId;
	private String entranceMarksSecured;
	private String recommendDeatails;
	
	
	public String getPermanentDistricId() {
		return permanentDistricId;
	}

	public void setPermanentDistricId(String permanentDistricId) {
		this.permanentDistricId = permanentDistricId;
	}

	public String getPermanentDistricName() {
		return permanentDistricName;
	}

	public void setPermanentDistricName(String permanentDistricName) {
		this.permanentDistricName = permanentDistricName;
	}

	public String getCurrentDistricName() {
		return currentDistricName;
	}

	public void setCurrentDistricName(String currentDistricName) {
		this.currentDistricName = currentDistricName;
	}

	public String getCurrentDistricId() {
		return currentDistricId;
	}

	public void setCurrentDistricId(String currentDistricId) {
		this.currentDistricId = currentDistricId;
	}

	public String getPermanentAddressDistrictOthers() {
		return permanentAddressDistrictOthers;
	}

	public void setPermanentAddressDistrictOthers(
			String permanentAddressDistrictOthers) {
		this.permanentAddressDistrictOthers = permanentAddressDistrictOthers;
	}

	public String getCurrentAddressDistrictOthers() {
		return currentAddressDistrictOthers;
	}

	public void setCurrentAddressDistrictOthers(String currentAddressDistrictOthers) {
		this.currentAddressDistrictOthers = currentAddressDistrictOthers;
	}

	public String getParishOthers() {
		return parishOthers;
	}

	public void setParishOthers(String parishOthers) {
		this.parishOthers = parishOthers;
	}

	public String getNccgrades() {
		return nccgrades;
	}

	public void setNccgrades(String nccgrades) {
		this.nccgrades = nccgrades;
	}

	public boolean isNsscertificate() {
		return nsscertificate;
	}

	public void setNsscertificate(boolean nsscertificate) {
		this.nsscertificate = nsscertificate;
	}

	public boolean isExservice() {
		return exservice;
	}

	public void setExservice(boolean exservice) {
		this.exservice = exservice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPermanentStateId() {
		return permanentStateId;
	}

	public void setPermanentStateId(String permanentStateId) {
		this.permanentStateId = permanentStateId;
	}

	public int getPermanentCountryId() {
		return permanentCountryId;
	}

	public void setPermanentCountryId(int permanentCountryId) {
		this.permanentCountryId = permanentCountryId;
	}

	public String getCurrentStateId() {
		return currentStateId;
	}

	public void setCurrentStateId(String currentStateId) {
		this.currentStateId = currentStateId;
	}

	public int getCurrentCountryId() {
		return currentCountryId;
	}

	public void setCurrentCountryId(int currentCountryId) {
		this.currentCountryId = currentCountryId;
	}

	public String getParentStateId() {
		return parentStateId;
	}

	public void setParentStateId(String parentStateId) {
		this.parentStateId = parentStateId;
	}

	public int getParentCountryId() {
		return parentCountryId;
	}

	public void setParentCountryId(int parentCountryId) {
		this.parentCountryId = parentCountryId;
	}

	public String getNationalityOthers() {
		return nationalityOthers;
	}

	public void setNationalityOthers(String nationalityOthers) {
		this.nationalityOthers = nationalityOthers;
	}

	public String getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(String belongsTo) {
		this.belongsTo = belongsTo;
	}

	public String getResidentCategoryName() {
		return residentCategoryName;
	}

	public void setResidentCategoryName(String residentCategoryName) {
		this.residentCategoryName = residentCategoryName;
	}

	public String getReligionName() {
		return religionName;
	}

	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}

	public String getSubregligionName() {
		return subregligionName;
	}

	public void setSubregligionName(String subregligionName) {
		this.subregligionName = subregligionName;
	}

	public String getCasteCategory() {
		return casteCategory;
	}

	public void setCasteCategory(String casteCategory) {
		this.casteCategory = casteCategory;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public char getRuralUrban() {
		return ruralUrban;
	}

	public void setRuralUrban(char ruralUrban) {
		if( ruralUrban == 'R'){
			setBelongsTo("Rural");
		}else{
			setBelongsTo("Urban");
		}
		this.ruralUrban = ruralUrban;
	}

	public String getParentPh1() {
		return parentPh1;
	}

	public void setParentPh1(String parentPh1) {
		this.parentPh1 = parentPh1;
	}

	public String getParentPh2() {
		return parentPh2;
	}

	public void setParentPh2(String parentPh2) {
		this.parentPh2 = parentPh2;
	}

	public String getParentPh3() {
		return parentPh3;
	}

	public void setParentPh3(String parentPh3) {
		this.parentPh3 = parentPh3;
	}

	public String getParentMob1() {
		return parentMob1;
	}

	public void setParentMob1(String parentMob1) {
		this.parentMob1 = parentMob1;
	}

	public String getParentMob2() {
		return parentMob2;
	}

	public void setParentMob2(String parentMob2) {
		this.parentMob2 = parentMob2;
	}

	public String getParentMob3() {
		return parentMob3;
	}

	public void setParentMob3(String parentMob3) {
		this.parentMob3 = parentMob3;
	}

	public String getBirthState() {
		return birthState;
	}

	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public String getPassportValidity() {
		return passportValidity;
	}

	public void setPassportValidity(String passportValidity) {
		this.passportValidity = passportValidity;
	}

	public int getPassportCountry() {
		return passportCountry;
	}

	public void setPassportCountry(int passportCountry) {
		this.passportCountry = passportCountry;
	}

	public ReligionSectionTO getReligionSection() {
		return religionSection;
	}

	public void setReligionSection(ReligionSectionTO religionSection) {
		this.religionSection = religionSection;
	}

	public LanguageTO getLanguage() {
		return language;
	}

	public void setLanguage(LanguageTO language) {
		this.language = language;
	}

	public CasteTO getCaste() {
		return caste;
	}

	public void setCaste(CasteTO caste) {
		this.caste = caste;
	}

	public ReligionTO getReligion() {
		return religion;
	}

	public void setReligion(ReligionTO religion) {
		this.religion = religion;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPassportNo() {
		return passportNo;
	}

	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNo1() {
		return mobileNo1;
	}

	public void setMobileNo1(String mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public String getMobileNo2() {
		return mobileNo2;
	}

	public void setMobileNo2(String mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public String getMobileNo3() {
		return mobileNo3;
	}

	public void setMobileNo3(String mobileNo3) {
		this.mobileNo3 = mobileNo3;
	}

	public String getPhNo1() {
		return phNo1;
	}

	public void setPhNo1(String phNo1) {
		this.phNo1 = phNo1;
	}

	public String getPhNo2() {
		return phNo2;
	}

	public void setPhNo2(String phNo2) {
		this.phNo2 = phNo2;
	}

	public String getPhNo3() {
		return phNo3;
	}

	public void setPhNo3(String phNo3) {
		this.phNo3 = phNo3;
	}

	public String getCitizenship() {
		return citizenship;
	}

	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	public String getResidentCategory() {
		return residentCategory;
	}

	public void setResidentCategory(String residentCategory) {
		this.residentCategory = residentCategory;
	}

	public String getLanguageOthers() {
		return languageOthers;
	}

	public void setLanguageOthers(String languageOthers) {
		this.languageOthers = languageOthers;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Integer getAdmittedThroughId() {
		return admittedThroughId;
	}

	public void setAdmittedThroughId(Integer admittedThroughId) {
		this.admittedThroughId = admittedThroughId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getPermanentAddressLine1() {
		return permanentAddressLine1;
	}

	public void setPermanentAddressLine1(String permanentAddressLine1) {
		this.permanentAddressLine1 = permanentAddressLine1;
	}

	public String getPermanentAddressLine2() {
		return permanentAddressLine2;
	}

	public void setPermanentAddressLine2(String permanentAddressLine2) {
		this.permanentAddressLine2 = permanentAddressLine2;
	}

	public String getCurrentAddressLine1() {
		return currentAddressLine1;
	}

	public void setCurrentAddressLine1(String currentAddressLine1) {
		this.currentAddressLine1 = currentAddressLine1;
	}

	public String getCurrentAddressLine2() {
		return currentAddressLine2;
	}

	public void setCurrentAddressLine2(String currentAddressLine2) {
		this.currentAddressLine2 = currentAddressLine2;
	}

	public String getParentAddressLine1() {
		return parentAddressLine1;
	}

	public void setParentAddressLine1(String parentAddressLine1) {
		this.parentAddressLine1 = parentAddressLine1;
	}

	public String getParentAddressLine2() {
		return parentAddressLine2;
	}

	public void setParentAddressLine2(String parentAddressLine2) {
		this.parentAddressLine2 = parentAddressLine2;
	}
	
	public String getPermanentAddressZipCode() {
		return permanentAddressZipCode;
	}

	public void setPermanentAddressZipCode(String permanentAddressZipCode) {
		this.permanentAddressZipCode = permanentAddressZipCode;
	}

	public String getCurrentAddressZipCode() {
		return currentAddressZipCode;
	}

	public void setCurrentAddressZipCode(String currentAddressZipCode) {
		this.currentAddressZipCode = currentAddressZipCode;
	}

	public String getParentAddressZipCode() {
		return parentAddressZipCode;
	}

	public void setParentAddressZipCode(String parentAddressZipCode) {
		this.parentAddressZipCode = parentAddressZipCode;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherEducation() {
		return fatherEducation;
	}

	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}

	public String getMotherEducation() {
		return motherEducation;
	}

	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}

	public String getFatherEmail() {
		return fatherEmail;
	}

	public void setFatherEmail(String fatherEmail) {
		this.fatherEmail = fatherEmail;
	}

	public String getMotherEmail() {
		return motherEmail;
	}

	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public String getParentMobile() {
		return parentMobile;
	}

	public void setParentMobile(String parentMobile) {
		this.parentMobile = parentMobile;
	}

	public String getStateOfBirth() {
		return stateOfBirth;
	}

	public void setStateOfBirth(String stateOfBirth) {
		this.stateOfBirth = stateOfBirth;
	}

	public String getCountryOfBirth() {
		return countryOfBirth;
	}

	public void setCountryOfBirth(String countryOfBirth) {
		this.countryOfBirth = countryOfBirth;
	}

	public String getPassportIssuingCountry() {
		return passportIssuingCountry;
	}

	public void setPassportIssuingCountry(String passportIssuingCountry) {
		this.passportIssuingCountry = passportIssuingCountry;
	}

	public String getPermanentCityName() {
		return permanentCityName;
	}

	public void setPermanentCityName(String permanentCityName) {
		this.permanentCityName = permanentCityName;
	}

	public String getPermanentStateName() {
		return permanentStateName;
	}

	public void setPermanentStateName(String permanentStateName) {
		this.permanentStateName = permanentStateName;
	}

	public String getPermanentCountryName() {
		return permanentCountryName;
	}

	public void setPermanentCountryName(String permanentCountryName) {
		this.permanentCountryName = permanentCountryName;
	}

	public String getCurrentCityName() {
		return currentCityName;
	}

	public void setCurrentCityName(String currentCityName) {
		this.currentCityName = currentCityName;
	}

	public String getCurrentStateName() {
		return currentStateName;
	}

	public void setCurrentStateName(String currentStateName) {
		this.currentStateName = currentStateName;
	}

	public String getCurrentCountryName() {
		return currentCountryName;
	}

	public void setCurrentCountryName(String currentCountryName) {
		this.currentCountryName = currentCountryName;
	}

	public String getParentCityName() {
		return parentCityName;
	}

	public void setParentCityName(String parentCityName) {
		this.parentCityName = parentCityName;
	}

	public String getParentStateName() {
		return parentStateName;
	}

	public void setParentStateName(String parentStateName) {
		this.parentStateName = parentStateName;
	}

	public String getParentCountryName() {
		return parentCountryName;
	}

	public void setParentCountryName(String parentCountryName) {
		this.parentCountryName = parentCountryName;
	}

	public String getFatherIncome() {
		return fatherIncome;
	}

	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}

	public String getFatherOccupation() {
		return fatherOccupation;
	}

	public void setFatherOccupation(String fatherOccupation) {
		this.fatherOccupation = fatherOccupation;
	}

	public String getMotherIncome() {
		return motherIncome;
	}

	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}

	public String getMotherOccupation() {
		return motherOccupation;
	}

	public void setMotherOccupation(String motherOccupation) {
		this.motherOccupation = motherOccupation;
	}

	public String getReligionId() {
		return religionId;
	}

	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}

	public String getSubReligionId() {
		return subReligionId;
	}

	public void setSubReligionId(String subReligionId) {
		this.subReligionId = subReligionId;
	}

	public String getCasteId() {
		return casteId;
	}

	public void setCasteId(String casteId) {
		this.casteId = casteId;
	}

	public String getStateOthers() {
		return stateOthers;
	}

	public void setStateOthers(String stateOthers) {
		this.stateOthers = stateOthers;
	}

	public String getPermanentAddressStateOthers() {
		return permanentAddressStateOthers;
	}

	public void setPermanentAddressStateOthers(String permanentAddressStateOthers) {
		this.permanentAddressStateOthers = permanentAddressStateOthers;
	}

	public String getCurrentAddressStateOthers() {
		return currentAddressStateOthers;
	}

	public void setCurrentAddressStateOthers(String currentAddressStateOthers) {
		this.currentAddressStateOthers = currentAddressStateOthers;
	}

	public String getParentAddressStateOthers() {
		return parentAddressStateOthers;
	}

	public void setParentAddressStateOthers(String parentAddressStateOthers) {
		this.parentAddressStateOthers = parentAddressStateOthers;
	}

	public String getCasteOthers() {
		return casteOthers;
	}

	public void setCasteOthers(String casteOthers) {
		this.casteOthers = casteOthers;
	}

	public String getReligionOthers() {
		return religionOthers;
	}

	public void setReligionOthers(String religionOthers) {
		this.religionOthers = religionOthers;
	}

	public String getReligionSectionOthers() {
		return religionSectionOthers;
	}

	public void setReligionSectionOthers(String religionSectionOthers) {
		this.religionSectionOthers = religionSectionOthers;
	}
	
	public String getParentAddressLine3() {
		return parentAddressLine3;
	}

	public void setParentAddressLine3(String parentAddressLine3) {
		this.parentAddressLine3 = parentAddressLine3;
	}

	public String getFatherIncomeId() {
		return fatherIncomeId;
	}

	public void setFatherIncomeId(String fatherIncomeId) {
		this.fatherIncomeId = fatherIncomeId;
	}

	public String getMotherIncomeId() {
		return motherIncomeId;
	}

	public void setMotherIncomeId(String motherIncomeId) {
		this.motherIncomeId = motherIncomeId;
	}

	public String getFatherCurrencyId() {
		return fatherCurrencyId;
	}

	public void setFatherCurrencyId(String fatherCurrencyId) {
		this.fatherCurrencyId = fatherCurrencyId;
	}

	public String getMotherCurrencyId() {
		return motherCurrencyId;
	}

	public void setMotherCurrencyId(String motherCurrencyId) {
		this.motherCurrencyId = motherCurrencyId;
	}

	public String getFatherCurrency() {
		return fatherCurrency;
	}

	public void setFatherCurrency(String fatherCurrency) {
		this.fatherCurrency = fatherCurrency;
	}

	public String getMotherCurrency() {
		return motherCurrency;
	}

	public void setMotherCurrency(String motherCurrency) {
		this.motherCurrency = motherCurrency;
	}

	public String getFatherIncomeRange() {
		return fatherIncomeRange;
	}

	public void setFatherIncomeRange(String fatherIncomeRange) {
		this.fatherIncomeRange = fatherIncomeRange;
	}

	public String getMotherIncomeRange() {
		return motherIncomeRange;
	}

	public void setMotherIncomeRange(String motherIncomeRange) {
		this.motherIncomeRange = motherIncomeRange;
	}

	public String getFatherOccupationId() {
		return fatherOccupationId;
	}

	public void setFatherOccupationId(String fatherOccupationId) {
		this.fatherOccupationId = fatherOccupationId;
	}

	public String getMotherOccupationId() {
		return motherOccupationId;
	}

	public void setMotherOccupationId(String motherOccupationId) {
		this.motherOccupationId = motherOccupationId;
	}

	public char getAreaType() {
		return areaType;
	}

	public void setAreaType(char areaType) {
		this.areaType = areaType;
	}

	public boolean isSportsPerson() {
		return sportsPerson;
	}

	public void setSportsPerson(boolean sportsPerson) {
		this.sportsPerson = sportsPerson;
	}

	public boolean isHandicapped() {
		return handicapped;
	}

	public void setHandicapped(boolean handicapped) {
		this.handicapped = handicapped;
	}

	public String getGuardianAddressLine1() {
		return guardianAddressLine1;
	}

	public void setGuardianAddressLine1(String guardianAddressLine1) {
		this.guardianAddressLine1 = guardianAddressLine1;
	}

	public String getGuardianAddressLine2() {
		return guardianAddressLine2;
	}

	public void setGuardianAddressLine2(String guardianAddressLine2) {
		this.guardianAddressLine2 = guardianAddressLine2;
	}

	public String getGuardianAddressLine3() {
		return guardianAddressLine3;
	}

	public void setGuardianAddressLine3(String guardianAddressLine3) {
		this.guardianAddressLine3 = guardianAddressLine3;
	}

	public String getGuardianPh1() {
		return guardianPh1;
	}

	public void setGuardianPh1(String guardianPh1) {
		this.guardianPh1 = guardianPh1;
	}

	public String getGuardianPh2() {
		return guardianPh2;
	}

	public void setGuardianPh2(String guardianPh2) {
		this.guardianPh2 = guardianPh2;
	}

	public String getGuardianPh3() {
		return guardianPh3;
	}

	public void setGuardianPh3(String guardianPh3) {
		this.guardianPh3 = guardianPh3;
	}

	public String getGuardianMob1() {
		return guardianMob1;
	}

	public void setGuardianMob1(String guardianMob1) {
		this.guardianMob1 = guardianMob1;
	}

	public String getGuardianMob2() {
		return guardianMob2;
	}

	public void setGuardianMob2(String guardianMob2) {
		this.guardianMob2 = guardianMob2;
	}

	public String getGuardianMob3() {
		return guardianMob3;
	}

	public void setGuardianMob3(String guardianMob3) {
		this.guardianMob3 = guardianMob3;
	}

	public int getCountryByGuardianAddressCountryId() {
		return countryByGuardianAddressCountryId;
	}

	public void setCountryByGuardianAddressCountryId(
			int countryByGuardianAddressCountryId) {
		this.countryByGuardianAddressCountryId = countryByGuardianAddressCountryId;
	}

	public String getStateByGuardianAddressStateId() {
		return stateByGuardianAddressStateId;
	}

	public void setStateByGuardianAddressStateId(String stateByGuardianAddressStateId) {
		this.stateByGuardianAddressStateId = stateByGuardianAddressStateId;
	}

	public String getCityByGuardianAddressCityId() {
		return cityByGuardianAddressCityId;
	}

	public void setCityByGuardianAddressCityId(String cityByGuardianAddressCityId) {
		this.cityByGuardianAddressCityId = cityByGuardianAddressCityId;
	}

	public String getGuardianAddressZipCode() {
		return guardianAddressZipCode;
	}

	public void setGuardianAddressZipCode(String guardianAddressZipCode) {
		this.guardianAddressZipCode = guardianAddressZipCode;
	}

	public String getGuardianAddressStateOthers() {
		return guardianAddressStateOthers;
	}

	public void setGuardianAddressStateOthers(String guardianAddressStateOthers) {
		this.guardianAddressStateOthers = guardianAddressStateOthers;
	}

	public String getGuardianAddressCountryOthers() {
		return guardianAddressCountryOthers;
	}

	public void setGuardianAddressCountryOthers(String guardianAddressCountryOthers) {
		this.guardianAddressCountryOthers = guardianAddressCountryOthers;
	}
	
	/**
	 * @return the sportsDescription
	 */
	public String getSportsDescription() {
		return sportsDescription;
	}

	/**
	 * @param sportsDescription the sportsDescription to set
	 */
	public void setSportsDescription(String sportsDescription) {
		this.sportsDescription = sportsDescription;
	}

	/**
	 * @return the hadnicappedDescription
	 */
	public String getHadnicappedDescription() {
		return hadnicappedDescription;
	}

	/**
	 * @param hadnicappedDescription the hadnicappedDescription to set
	 */
	public void setHadnicappedDescription(String hadnicappedDescription) {
		this.hadnicappedDescription = hadnicappedDescription;
	}

	public String getResidentPermitNo() {
		return residentPermitNo;
	}

	public void setResidentPermitNo(String residentPermitNo) {
		this.residentPermitNo = residentPermitNo;
	}

	public String getResidentPermitDate() {
		return residentPermitDate;
	}

	public void setResidentPermitDate(String residentPermitDate) {
		this.residentPermitDate = residentPermitDate;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getLanguageByLanguageWrite() {
		return languageByLanguageWrite;
	}

	public void setLanguageByLanguageWrite(String languageByLanguageWrite) {
		this.languageByLanguageWrite = languageByLanguageWrite;
	}

	public String getLanguageByLanguageSpeak() {
		return languageByLanguageSpeak;
	}

	public void setLanguageByLanguageSpeak(String languageByLanguageSpeak) {
		this.languageByLanguageSpeak = languageByLanguageSpeak;
	}

	public String getLanguageByLanguageRead() {
		return languageByLanguageRead;
	}

	public void setLanguageByLanguageRead(String languageByLanguageRead) {
		this.languageByLanguageRead = languageByLanguageRead;
	}

	public String getMotherTongue() {
		return motherTongue;
	}

	public void setMotherTongue(String motherTongue) {
		this.motherTongue = motherTongue;
	}

	public String getTrainingProgName() {
		return trainingProgName;
	}

	public void setTrainingProgName(String trainingProgName) {
		this.trainingProgName = trainingProgName;
	}

	public String getTrainingInstAddress() {
		return trainingInstAddress;
	}

	public void setTrainingInstAddress(String trainingInstAddress) {
		this.trainingInstAddress = trainingInstAddress;
	}

	public String getTrainingPurpose() {
		return trainingPurpose;
	}

	public void setTrainingPurpose(String trainingPurpose) {
		this.trainingPurpose = trainingPurpose;
	}

	public String getTrainingDuration() {
		return trainingDuration;
	}

	public void setTrainingDuration(String trainingDuration) {
		this.trainingDuration = trainingDuration;
	}

	public String getCourseKnownBy() {
		return courseKnownBy;
	}

	public void setCourseKnownBy(String courseKnownBy) {
		this.courseKnownBy = courseKnownBy;
	}

	public String getCourseOptReason() {
		return courseOptReason;
	}

	public void setCourseOptReason(String courseOptReason) {
		this.courseOptReason = courseOptReason;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getWeakness() {
		return weakness;
	}

	public void setWeakness(String weakness) {
		this.weakness = weakness;
	}

	public String getOtherAddnInfo() {
		return otherAddnInfo;
	}

	public void setOtherAddnInfo(String otherAddnInfo) {
		this.otherAddnInfo = otherAddnInfo;
	}

	public List<StudentExtracurricular> getStudentExtracurriculars() {
		return studentExtracurriculars;
	}

	public void setStudentExtracurriculars(
			List<StudentExtracurricular> studentExtracurriculars) {
		this.studentExtracurriculars = studentExtracurriculars;
	}

	public List<ExtracurricularActivityTO> getStudentExtracurricularsTos() {
		return studentExtracurricularsTos;
	}

	public void setStudentExtracurricularsTos(
			List<ExtracurricularActivityTO> studentExtracurricularsTos) {
		this.studentExtracurricularsTos = studentExtracurricularsTos;
	}

	public String[] getExtracurricularIds() {
		return extracurricularIds;
	}

	public void setExtracurricularIds(String[] extracurricularIds) {
		this.extracurricularIds = extracurricularIds;
	}

	public String getSecondLanguage() {
		return secondLanguage;
	}

	public void setSecondLanguage(String secondLanguage) {
		this.secondLanguage = secondLanguage;
	}

	public String getBrotherName() {
		return brotherName;
	}

	public void setBrotherName(String brotherName) {
		this.brotherName = brotherName;
	}

	public String getBrotherEducation() {
		return brotherEducation;
	}

	public void setBrotherEducation(String brotherEducation) {
		this.brotherEducation = brotherEducation;
	}

	public String getBrotherIncome() {
		return brotherIncome;
	}

	public void setBrotherIncome(String brotherIncome) {
		this.brotherIncome = brotherIncome;
	}

	public String getBrotherOccupation() {
		return brotherOccupation;
	}

	public void setBrotherOccupation(String brotherOccupation) {
		this.brotherOccupation = brotherOccupation;
	}

	public String getBrotherAge() {
		return brotherAge;
	}

	public void setBrotherAge(String brotherAge) {
		this.brotherAge = brotherAge;
	}

	public String getSisterName() {
		return sisterName;
	}

	public void setSisterName(String sisterName) {
		this.sisterName = sisterName;
	}

	public String getSisterEducation() {
		return sisterEducation;
	}

	public void setSisterEducation(String sisterEducation) {
		this.sisterEducation = sisterEducation;
	}

	public String getSisterIncome() {
		return sisterIncome;
	}

	public void setSisterIncome(String sisterIncome) {
		this.sisterIncome = sisterIncome;
	}

	public String getSisterOccupation() {
		return sisterOccupation;
	}

	public void setSisterOccupation(String sisterOccupation) {
		this.sisterOccupation = sisterOccupation;
	}

	public String getSisterAge() {
		return sisterAge;
	}

	public void setSisterAge(String sisterAge) {
		this.sisterAge = sisterAge;
	}

	public String getGuardianCountryName() {
		return guardianCountryName;
	}

	public void setGuardianCountryName(String guardianCountryName) {
		this.guardianCountryName = guardianCountryName;
	}

	public String getGuardianStateName() {
		return guardianStateName;
	}

	public void setGuardianStateName(String guardianStateName) {
		this.guardianStateName = guardianStateName;
	}

	public String getExtracurricularNames() {
		return extracurricularNames;
	}

	public void setExtracurricularNames(String extracurricularNames) {
		this.extracurricularNames = extracurricularNames;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
    
	
	public String getEmployeDesignation() {
		return employeDesignation;
	}

	public void setEmployeDesignation(String employeDesignation) {
		this.employeDesignation = employeDesignation;
	}

	public String getEmployeeDepartment() {
		return employeeDepartment;
	}

	public void setEmployeeDepartment(String employeeDepartment) {
		this.employeeDepartment = employeeDepartment;
	}

	public String getWorkEmail() {
		return workEmail;
	}

	public void setWorkEmail(String workEmail) {
		this.workEmail = workEmail;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentClass() {
		return studentClass;
	}

	public void setStudentClass(String studentClass) {
		this.studentClass = studentClass;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
	}

	public String getRecommentedBy() {
		return recommentedBy;
	}

	public void setRecommentedBy(String recommentedBy) {
		this.recommentedBy = recommentedBy;
	}

	public boolean getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getSubCaste() {
		return subCaste;
	}

	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
	}

	public String getUniversityEmail() {
		return universityEmail;
	}

	public void setUniversityEmail(String universityEmail) {
		this.universityEmail = universityEmail;
	}

	public String getOtherOccupationFather() {
		return otherOccupationFather;
	}

	public void setOtherOccupationFather(String otherOccupationFather) {
		this.otherOccupationFather = otherOccupationFather;
	}

	public String getOtherOccupationMother() {
		return otherOccupationMother;
	}

	public void setOtherOccupationMother(String otherOccupationMother) {
		this.otherOccupationMother = otherOccupationMother;
	}

	public String getFatherTitle() {
		return fatherTitle;
	}

	public void setFatherTitle(String fatherTitle) {
		this.fatherTitle = fatherTitle;
	}

	public String getMotherTitle() {
		return motherTitle;
	}

	public void setMotherTitle(String motherTitle) {
		this.motherTitle = motherTitle;
	}

	public String getMotherTongueName() {
		return motherTongueName;
	}

	public void setMotherTongueName(String motherTongueName) {
		this.motherTongueName = motherTongueName;
	}

	public String getParishanddiocese() {
		return parishanddiocese;
	}

	public void setParishanddiocese(String parishanddiocese) {
		this.parishanddiocese = parishanddiocese;
	}

	public boolean isNcccertificate() {
		return ncccertificate;
	}

	public void setNcccertificate(boolean ncccertificate) {
		this.ncccertificate = ncccertificate;
	}

	public String getDioceseId() {
		return dioceseId;
	}

	public void setDioceseId(String dioceseId) {
		this.dioceseId = dioceseId;
	}

	public String getParishId() {
		return parishId;
	}

	public void setParishId(String parishId) {
		this.parishId = parishId;
	}

	public String getDioceseName() {
		return dioceseName;
	}

	public void setDioceseName(String dioceseName) {
		this.dioceseName = dioceseName;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public String getDioceseOthers() {
		return dioceseOthers;
	}

	public void setDioceseOthers(String dioceseOthers) {
		this.dioceseOthers = dioceseOthers;
	}

	public String getUgcourse() {
		return ugcourse;
	}

	public void setUgcourse(String ugcourse) {
		this.ugcourse = ugcourse;
	}
	
	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getMotherMobile() {
		return motherMobile;
	}

	public void setMotherMobile(String motherMobile) {
		this.motherMobile = motherMobile;
	}

	public String getFatherMobile() {
		return fatherMobile;
	}

	public void setFatherMobile(String fatherMobile) {
		this.fatherMobile = fatherMobile;
	}

	public String getSports() {
		return sports;
	}

	public void setSports(String sports) {
		this.sports = sports;
	}

	public String getArts() {
		return arts;
	}

	public void setArts(String arts) {
		this.arts = arts;
	}

	public String getSportsParticipate() {
		return sportsParticipate;
	}

	public void setSportsParticipate(String sportsParticipate) {
		this.sportsParticipate = sportsParticipate;
	}

	public String getArtsParticipate() {
		return artsParticipate;
	}

	public void setArtsParticipate(String artsParticipate) {
		this.artsParticipate = artsParticipate;
	}

	public String getReservation() {
		return reservation;
	}

	public void setReservation(String reservation) {
		this.reservation = reservation;
	}

	public String getReservation1() {
		return reservation1;
	}

	public void setReservation1(String reservation1) {
		this.reservation1 = reservation1;
	}

	public void setRecommended(boolean isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getCurrentStreet() {
		return currentStreet;
	}

	public void setCurrentStreet(String currentStreet) {
		this.currentStreet = currentStreet;
	}

	public String getPermanentStreet() {
		return permanentStreet;
	}

	public void setPermanentStreet(String permanentStreet) {
		this.permanentStreet = permanentStreet;
	}

	public String getFatherOccupationAddress() {
		return fatherOccupationAddress;
	}

	public void setFatherOccupationAddress(String fatherOccupationAddress) {
		this.fatherOccupationAddress = fatherOccupationAddress;
	}

	public String getMotherOccupationAddress() {
		return motherOccupationAddress;
	}

	public void setMotherOccupationAddress(String motherOccupationAddress) {
		this.motherOccupationAddress = motherOccupationAddress;
	}

	public String getNoofenglishCoreCourses() {
		return noofenglishCoreCourses;
	}

	public void setNoofenglishCoreCourses(String noofenglishCoreCourses) {
		this.noofenglishCoreCourses = noofenglishCoreCourses;
	}

	public String getAadhaarCardNumber() {
		return aadhaarCardNumber;
	}

	public void setAadhaarCardNumber(String aadhaarCardNumber) {
		this.aadhaarCardNumber = aadhaarCardNumber;
	}

	public String getMotherTongueId() {
		return motherTongueId;
	}

	public void setMotherTongueId(String motherTongueId) {
		this.motherTongueId = motherTongueId;
	}

	public Map<Integer, String> getMotherTongues() {
		return motherTongues;
	}

	public void setMotherTongues(Map<Integer, String> motherTongues) {
		this.motherTongues = motherTongues;
	}

	public String getFatherPANNumber() {
		return fatherPANNumber;
	}

	public void setFatherPANNumber(String fatherPANNumber) {
		this.fatherPANNumber = fatherPANNumber;
	}

	public String getFatherAadhaarNumber() {
		return fatherAadhaarNumber;
	}

	public void setFatherAadhaarNumber(String fatherAadhaarNumber) {
		this.fatherAadhaarNumber = fatherAadhaarNumber;
	}
	
	public Boolean getIsmgquota() {
		return ismgquota;
	}

	public void setIsmgquota(Boolean ismgquota) {
		this.ismgquota = ismgquota;
	}

	public String getRecommentedPersonDesignation() {
		return recommentedPersonDesignation;
	}

	public void setRecommentedPersonDesignation(String recommentedPersonDesignation) {
		this.recommentedPersonDesignation = recommentedPersonDesignation;
	}

	public String getRecommentedPersonMobile() {
		return recommentedPersonMobile;
	}

	public void setRecommentedPersonMobile(String recommentedPersonMobile) {
		this.recommentedPersonMobile = recommentedPersonMobile;
	}

	public String getNssgrades() {
		return nssgrades;
	}

	public void setNssgrades(String nssgrades) {
		this.nssgrades = nssgrades;
	}

	public String getPreferenceNoCAP() {
		return preferenceNoCAP;
	}

	public void setPreferenceNoCAP(String preferenceNoCAP) {
		this.preferenceNoCAP = preferenceNoCAP;
	}

	public String getTitleOfFather() {
		return titleOfFather;
	}

	public void setTitleOfFather(String titleOfFather) {
		this.titleOfFather = titleOfFather;
	}

	public String getTitleOfMother() {
		return titleOfMother;
	}

	public void setTitleOfMother(String titleOfMother) {
		this.titleOfMother = titleOfMother;
	}

	public String getMbaEntranceExamId() {
		return mbaEntranceExamId;
	}

	public void setMbaEntranceExamId(String mbaEntranceExamId) {
		this.mbaEntranceExamId = mbaEntranceExamId;
	}

	public String getEntranceMarksSecured() {
		return entranceMarksSecured;
	}

	public void setEntranceMarksSecured(String entranceMarksSecured) {
		this.entranceMarksSecured = entranceMarksSecured;
	}

	public String getRecommendDeatails() {
		return recommendDeatails;
	}

	public void setRecommendDeatails(String recommendDeatails) {
		this.recommendDeatails = recommendDeatails;
	}
	
}
