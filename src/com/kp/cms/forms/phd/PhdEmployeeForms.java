package com.kp.cms.forms.phd;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.phd.PhdEmpImagesTO;
import com.kp.cms.to.phd.PhdEmployeeTo;

public class PhdEmployeeForms extends BaseActionForm{
	
	private int id;
	private String name;
	private String nameSearch;
	private String guideShipSearch;
	private String gender;
	private String empanelmentNo;
	private String dateOfBirth;
	private String placeOfBirth;
	private String nationalityId;
	private String religionId;
	private String bloodGroup;
	private String domicialStatus;
	private String passPortNo;
	private String panNo;
	private String email;
	private String dateOfAward;
	private String qualificationId;
	private String noOfresearch;
	private String noOfBookAuthored;
	private String nameAddress;
	private String departmentId;
	private String desiginitionId;
	private String yearOfExp;
	private String permanentAddress;
	private String pAddressPhonNo;
	private String contactAddress;
	private String cAddressPhonNo;
	private String bankAccNo;
	private String bankName;
	private String bankBranch;
	private String qExamName;
	private String degree;
	private String nameOfUniversity;
	private String qstate;
	private String percentage;
	private String yearOfComp;
	private String attempts;
	private String tNameOfInstitution;
	private String tNameOfUniversity;
	private String tSubject;
	private String tYearsOfExpe;
	private String rNameOfInstitution;
	private String rNameOfTheUniversity;
	private String rSubject;
	private String rYearOfExper;
	private String pNameOfTitles;
	private String pJournalPubli;
	private String pyear;
	private String subjectGuideShip;
	private byte[] photoBytes;
	private List<EmpQualificationLevelTo> phdEmployeequalificationTos;
	private List<EmpQualificationLevelTo> phdEmployeequalificationFixedTo;
	private Map<String,String> statesMap;
	private Map<String,String> nationalityMap;
	private Map<String,String> qualificationLevelMap;
	private Map<String,String> qualificationMap;
	private Map<String,String> religionMap;
	private Map<String,String> guideShipMap;
	private String levelSize;
	private List<EmpPreviousOrgTo> teachingExperience;
	private String teaching;
	private String teachingExpLength;
	private List<EmpPreviousOrgTo> researchExperience;
	private String researchlength;
	private String researchs;
	private List<EmpPreviousOrgTo> publicationExperience;
	private String publicationLength;
	private String publications;
	private String mode;
	private String focusValue;
	private FormFile empPhoto;
	private String empImageId;
	private List<PhdEmployeeTo> phdEmployeeDetails;
	private List<PhdEmpImagesTO> empImages;	
	private int noMphilScolars;
	private int noPhdScolars;
	private int noPhdScolarOutside;
	
	
	public ActionErrors validate(ActionMapping mapping,HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clear1(){
		this.nameSearch=null;
		this.guideShipSearch=null;
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


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
	}


	public String getEmpanelmentNo() {
		return empanelmentNo;
	}


	public void setEmpanelmentNo(String empanelmentNo) {
		this.empanelmentNo = empanelmentNo;
	}


	public String getDateOfBirth() {
		return dateOfBirth;
	}


	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}


	public String getPlaceOfBirth() {
		return placeOfBirth;
	}


	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}


	public String getNationalityId() {
		return nationalityId;
	}


	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}


	public String getReligionId() {
		return religionId;
	}


	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}


	public String getBloodGroup() {
		return bloodGroup;
	}


	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}


	public String getDomicialStatus() {
		return domicialStatus;
	}


	public void setDomicialStatus(String domicialStatus) {
		this.domicialStatus = domicialStatus;
	}


	public String getPassPortNo() {
		return passPortNo;
	}


	public void setPassPortNo(String passPortNo) {
		this.passPortNo = passPortNo;
	}


	public String getPanNo() {
		return panNo;
	}


	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getDateOfAward() {
		return dateOfAward;
	}


	public void setDateOfAward(String dateOfAward) {
		this.dateOfAward = dateOfAward;
	}


	public String getQualificationId() {
		return qualificationId;
	}


	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}


	public String getNoOfresearch() {
		return noOfresearch;
	}


	public void setNoOfresearch(String noOfresearch) {
		this.noOfresearch = noOfresearch;
	}


	public String getNoOfBookAuthored() {
		return noOfBookAuthored;
	}


	public void setNoOfBookAuthored(String noOfBookAuthored) {
		this.noOfBookAuthored = noOfBookAuthored;
	}


	public String getNameAddress() {
		return nameAddress;
	}


	public void setNameAddress(String nameAddress) {
		this.nameAddress = nameAddress;
	}


	public String getDepartmentId() {
		return departmentId;
	}


	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}


	public String getDesiginitionId() {
		return desiginitionId;
	}


	public void setDesiginitionId(String desiginitionId) {
		this.desiginitionId = desiginitionId;
	}


	public String getYearOfExp() {
		return yearOfExp;
	}


	public void setYearOfExp(String yearOfExp) {
		this.yearOfExp = yearOfExp;
	}


	public String getPermanentAddress() {
		return permanentAddress;
	}


	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}


	public String getpAddressPhonNo() {
		return pAddressPhonNo;
	}


	public void setpAddressPhonNo(String pAddressPhonNo) {
		this.pAddressPhonNo = pAddressPhonNo;
	}


	public String getContactAddress() {
		return contactAddress;
	}


	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}


	public String getcAddressPhonNo() {
		return cAddressPhonNo;
	}


	public void setcAddressPhonNo(String cAddressPhonNo) {
		this.cAddressPhonNo = cAddressPhonNo;
	}


	public String getBankAccNo() {
		return bankAccNo;
	}


	public void setBankAccNo(String bankAccNo) {
		this.bankAccNo = bankAccNo;
	}


	public String getBankName() {
		return bankName;
	}


	public void setBankName(String bankName) {
		this.bankName = bankName;
	}


	public String getBankBranch() {
		return bankBranch;
	}


	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}


	public String getqExamName() {
		return qExamName;
	}


	public void setqExamName(String qExamName) {
		this.qExamName = qExamName;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public String getNameOfUniversity() {
		return nameOfUniversity;
	}


	public void setNameOfUniversity(String nameOfUniversity) {
		this.nameOfUniversity = nameOfUniversity;
	}


	public String getQstate() {
		return qstate;
	}


	public void setQstate(String qstate) {
		this.qstate = qstate;
	}


	public String getPercentage() {
		return percentage;
	}


	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}


	public String getYearOfComp() {
		return yearOfComp;
	}


	public void setYearOfComp(String yearOfComp) {
		this.yearOfComp = yearOfComp;
	}


	public String getAttempts() {
		return attempts;
	}


	public void setAttempts(String attempts) {
		this.attempts = attempts;
	}


	public String gettNameOfInstitution() {
		return tNameOfInstitution;
	}


	public void settNameOfInstitution(String tNameOfInstitution) {
		this.tNameOfInstitution = tNameOfInstitution;
	}


	public String gettNameOfUniversity() {
		return tNameOfUniversity;
	}


	public void settNameOfUniversity(String tNameOfUniversity) {
		this.tNameOfUniversity = tNameOfUniversity;
	}


	public String gettSubject() {
		return tSubject;
	}


	public void settSubject(String tSubject) {
		this.tSubject = tSubject;
	}


	public String gettYearsOfExpe() {
		return tYearsOfExpe;
	}


	public void settYearsOfExpe(String tYearsOfExpe) {
		this.tYearsOfExpe = tYearsOfExpe;
	}


	public String getrNameOfInstitution() {
		return rNameOfInstitution;
	}


	public void setrNameOfInstitution(String rNameOfInstitution) {
		this.rNameOfInstitution = rNameOfInstitution;
	}


	public String getrNameOfTheUniversity() {
		return rNameOfTheUniversity;
	}


	public void setrNameOfTheUniversity(String rNameOfTheUniversity) {
		this.rNameOfTheUniversity = rNameOfTheUniversity;
	}


	public String getrSubject() {
		return rSubject;
	}


	public void setrSubject(String rSubject) {
		this.rSubject = rSubject;
	}


	public String getrYearOfExper() {
		return rYearOfExper;
	}


	public void setrYearOfExper(String rYearOfExper) {
		this.rYearOfExper = rYearOfExper;
	}


	public String getpNameOfTitles() {
		return pNameOfTitles;
	}


	public void setpNameOfTitles(String pNameOfTitles) {
		this.pNameOfTitles = pNameOfTitles;
	}


	public String getpJournalPubli() {
		return pJournalPubli;
	}


	public void setpJournalPubli(String pJournalPubli) {
		this.pJournalPubli = pJournalPubli;
	}


	public String getPyear() {
		return pyear;
	}


	public void setPyear(String pyear) {
		this.pyear = pyear;
	}


	public String getSubjectGuideShip() {
		return subjectGuideShip;
	}


	public void setSubjectGuideShip(String subjectGuideShip) {
		this.subjectGuideShip = subjectGuideShip;
	}


	public byte[] getPhotoBytes() {
		return photoBytes;
	}


	public void setPhotoBytes(byte[] photoBytes) {
		this.photoBytes = photoBytes;
	}


	public List<EmpQualificationLevelTo> getPhdEmployeequalificationTos() {
		return phdEmployeequalificationTos;
	}


	public void setPhdEmployeequalificationTos(
			List<EmpQualificationLevelTo> phdEmployeequalificationTos) {
		this.phdEmployeequalificationTos = phdEmployeequalificationTos;
	}


	public List<EmpQualificationLevelTo> getPhdEmployeequalificationFixedTo() {
		return phdEmployeequalificationFixedTo;
	}


	public void setPhdEmployeequalificationFixedTo(
			List<EmpQualificationLevelTo> phdEmployeequalificationFixedTo) {
		this.phdEmployeequalificationFixedTo = phdEmployeequalificationFixedTo;
	}


	public Map<String, String> getStatesMap() {
		return statesMap;
	}


	public void setStatesMap(Map<String, String> statesMap) {
		this.statesMap = statesMap;
	}


	public Map<String, String> getNationalityMap() {
		return nationalityMap;
	}


	public void setNationalityMap(Map<String, String> nationalityMap) {
		this.nationalityMap = nationalityMap;
	}


	public Map<String, String> getQualificationMap() {
		return qualificationMap;
	}


	public void setQualificationMap(Map<String, String> qualificationMap) {
		this.qualificationMap = qualificationMap;
	}


	public Map<String, String> getReligionMap() {
		return religionMap;
	}


	public void setReligionMap(Map<String, String> religionMap) {
		this.religionMap = religionMap;
	}


	public Map<String, String> getGuideShipMap() {
		return guideShipMap;
	}


	public void setGuideShipMap(Map<String, String> guideShipMap) {
		this.guideShipMap = guideShipMap;
	}


	public String getLevelSize() {
		return levelSize;
	}


	public void setLevelSize(String levelSize) {
		this.levelSize = levelSize;
	}


	public List<EmpPreviousOrgTo> getTeachingExperience() {
		return teachingExperience;
	}


	public void setTeachingExperience(List<EmpPreviousOrgTo> teachingExperience) {
		this.teachingExperience = teachingExperience;
	}


	public String getTeaching() {
		return teaching;
	}


	public void setTeaching(String teaching) {
		this.teaching = teaching;
	}


	public String getTeachingExpLength() {
		return teachingExpLength;
	}


	public void setTeachingExpLength(String teachingExpLength) {
		this.teachingExpLength = teachingExpLength;
	}


	public List<EmpPreviousOrgTo> getResearchExperience() {
		return researchExperience;
	}


	public void setResearchExperience(List<EmpPreviousOrgTo> researchExperience) {
		this.researchExperience = researchExperience;
	}


	public String getResearchlength() {
		return researchlength;
	}


	public void setResearchlength(String researchlength) {
		this.researchlength = researchlength;
	}


	public String getResearchs() {
		return researchs;
	}


	public void setResearchs(String researchs) {
		this.researchs = researchs;
	}


	public List<EmpPreviousOrgTo> getPublicationExperience() {
		return publicationExperience;
	}


	public void setPublicationExperience(
			List<EmpPreviousOrgTo> publicationExperience) {
		this.publicationExperience = publicationExperience;
	}


	public String getPublicationLength() {
		return publicationLength;
	}


	public void setPublicationLength(String publicationLength) {
		this.publicationLength = publicationLength;
	}


	public String getPublications() {
		return publications;
	}


	public void setPublications(String publications) {
		this.publications = publications;
	}


	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getFocusValue() {
		return focusValue;
	}


	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}


	public FormFile getEmpPhoto() {
		return empPhoto;
	}


	public void setEmpPhoto(FormFile empPhoto) {
		this.empPhoto = empPhoto;
	}


	public String getEmpImageId() {
		return empImageId;
	}


	public void setEmpImageId(String empImageId) {
		this.empImageId = empImageId;
	}


	public List<PhdEmployeeTo> getPhdEmployeeDetails() {
		return phdEmployeeDetails;
	}


	public void setPhdEmployeeDetails(List<PhdEmployeeTo> phdEmployeeDetails) {
		this.phdEmployeeDetails = phdEmployeeDetails;
	}


	public List<PhdEmpImagesTO> getEmpImages() {
		return empImages;
	}


	public void setEmpImages(List<PhdEmpImagesTO> empImages) {
		this.empImages = empImages;
	}


	public String getNameSearch() {
		return nameSearch;
	}


	public void setNameSearch(String nameSearch) {
		this.nameSearch = nameSearch;
	}


	public String getGuideShipSearch() {
		return guideShipSearch;
	}


	public void setGuideShipSearch(String guideShipSearch) {
		this.guideShipSearch = guideShipSearch;
	}

	public Map<String, String> getQualificationLevelMap() {
		return qualificationLevelMap;
	}

	public void setQualificationLevelMap(Map<String, String> qualificationLevelMap) {
		this.qualificationLevelMap = qualificationLevelMap;
	}

	public int getNoMphilScolars() {
		return noMphilScolars;
	}

	public void setNoMphilScolars(int noMphilScolars) {
		this.noMphilScolars = noMphilScolars;
	}

	public int getNoPhdScolars() {
		return noPhdScolars;
	}

	public void setNoPhdScolars(int noPhdScolars) {
		this.noPhdScolars = noPhdScolars;
	}

	public int getNoPhdScolarOutside() {
		return noPhdScolarOutside;
	}

	public void setNoPhdScolarOutside(int noPhdScolarOutside) {
		this.noPhdScolarOutside = noPhdScolarOutside;
	}

	

}
