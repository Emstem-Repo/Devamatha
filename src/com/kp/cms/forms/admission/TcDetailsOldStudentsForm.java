package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CategoryTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TcDetailsOldStudentsTo;

public class TcDetailsOldStudentsForm extends BaseActionForm{
	private String button;
	private String id;
	private Integer acadamicYear;
	private String tcFor;
	private String tcType;
	private String registerNo;
	private String name;
	private String studentNo;
	private String dateOfBirth;
	private String gender;
	private String caste;
	private String subCaste;
	private String fatherName;
	private String motherName;
	private String admissionDate;
	private String dateOfLeaving;
	private String className;
	private String part1Subjects;
	private String part2Subjects;
	private String passed;
	private String publicExamName;
	private String examRegisterNo;
	private String scolorship;
	private String month;
	private String yr;
	private String feePaid;
	private String dateOfApplication;
	private String dateOfIssue;
	private String tcSerialNo;
	private String nationalityId;
	private String categoryId;
	private String characterAndConductId;
	private String tcNumber;
	private String religionOthers;
	private String casteOthers;
	private String nationalityOthers;
	private List<TcDetailsOldStudentsTo> tcDetailsOldStudentsToList;
	private List<CharacterAndConductTO> charAndConductToList;
	private List<CasteTO> categoryToList;
	private List<ReligionTO> religionToList;
	private List<NationalityTO> nationalityToList; 
	private String dobInWords;
	private String regMonthYear;
	private String characterAndConduct;
	private String subjectsPassed;
	private String casteCategoryName;
	private String tempTcFor;
	private String religionName;
	private String nationalityName;
	private String passDetails;
	private String tempYear;
	private String tempTcType;
	private String month1;

	public void clear(){
		this.id=null;
		this.acadamicYear=0;
		this.tcFor=null;
		this.tcType=null;
		this.registerNo=null;
		this.name=null;
		this.studentNo=null;
		this.dateOfBirth=null;
		this.gender=null;
		this.caste=null;
		this.subCaste=null;
		this.fatherName=null;
		this.motherName=null;
		this.admissionDate=null;
		this.dateOfLeaving=null;
		this.className=null;
		this.part1Subjects=null;
		this.part2Subjects=null;
		this.passed=null;
		this.publicExamName=null;
		this.examRegisterNo=null;
		this.scolorship=null;
		this.month=null;
		this.yr=null;
		this.feePaid=null;
		this.dateOfApplication=null;
		this.dateOfIssue=null;
		this.tcSerialNo=null;
		this.nationalityId=null;
		this.categoryId=null;
		this.characterAndConductId=null;
		this.tcNumber=null;
		this.religionOthers=null;
		this.casteOthers=null;
		this.nationalityOthers=null;
		this.button=null;
		this.dobInWords=null;
		this.subjectsPassed=null;
//		this.religionId=null;
		this.tempTcFor=null;
		this.casteCategoryName=null;
		this.religionName=null;
		this.nationalityName=null;
		this.passDetails=null;
		this.tempYear=null;
		this.tempTcType=null;
		this.month1 = null;
	}
	
	
	public String getButton() {
		return button;
	}


	public void setButton(String button) {
		this.button = button;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getAcadamicYear() {
		return acadamicYear;
	}
	public void setAcadamicYear(Integer acadamicYear) {
		this.acadamicYear = acadamicYear;
	}
	public String getTcFor() {
		return tcFor;
	}
	public void setTcFor(String tcFor) {
		this.tcFor = tcFor;
	}
	public String getTcType() {
		return tcType;
	}
	public void setTcType(String tcType) {
		this.tcType = tcType;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStudentNo() {
		return studentNo;
	}
	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	public String getSubCaste() {
		return subCaste;
	}
	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
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
	public String getAdmissionDate() {
		return admissionDate;
	}
	public void setAdmissionDate(String admissionDate) {
		this.admissionDate = admissionDate;
	}
	public String getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getPart1Subjects() {
		return part1Subjects;
	}
	public void setPart1Subjects(String part1Subjects) {
		this.part1Subjects = part1Subjects;
	}
	public String getPart2Subjects() {
		return part2Subjects;
	}
	public void setPart2Subjects(String part2Subjects) {
		this.part2Subjects = part2Subjects;
	}
	public String getPassed() {
		return passed;
	}
	public void setPassed(String passed) {
		this.passed = passed;
	}
	public String getPublicExamName() {
		return publicExamName;
	}
	public void setPublicExamName(String publicExamName) {
		this.publicExamName = publicExamName;
	}
	public String getExamRegisterNo() {
		return examRegisterNo;
	}
	public void setExamRegisterNo(String examRegisterNo) {
		this.examRegisterNo = examRegisterNo;
	}
	public String getScolorship() {
		return scolorship;
	}
	public void setScolorship(String scolorship) {
		this.scolorship = scolorship;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYr() {
		return yr;
	}


	public void setYr(String yr) {
		this.yr = yr;
	}


	public String getFeePaid() {
		return feePaid;
	}
	public void setFeePaid(String feePaid) {
		this.feePaid = feePaid;
	}
	public String getDateOfApplication() {
		return dateOfApplication;
	}
	public void setDateOfApplication(String dateOfApplication) {
		this.dateOfApplication = dateOfApplication;
	}
	public String getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(String dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public String getTcSerialNo() {
		return tcSerialNo;
	}
	public void setTcSerialNo(String tcSerialNo) {
		this.tcSerialNo = tcSerialNo;
	}
	
	public String getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCharacterAndConductId() {
		return characterAndConductId;
	}
	public void setCharacterAndConductId(String characterAndConductId) {
		this.characterAndConductId = characterAndConductId;
	}
	
	public String getTcNumber() {
		return tcNumber;
	}
	public void setTcNumber(String tcNumber) {
		this.tcNumber = tcNumber;
	}
	
	public List<TcDetailsOldStudentsTo> getTcDetailsOldStudentsToList() {
		return tcDetailsOldStudentsToList;
	}
	public void setTcDetailsOldStudentsToList(
			List<TcDetailsOldStudentsTo> tcDetailsOldStudentsToList) {
		this.tcDetailsOldStudentsToList = tcDetailsOldStudentsToList;
	}


	public List<CharacterAndConductTO> getCharAndConductToList() {
		return charAndConductToList;
	}


	public void setCharAndConductToList(
			List<CharacterAndConductTO> charAndConductToList) {
		this.charAndConductToList = charAndConductToList;
	}


	public List<CasteTO> getCategoryToList() {
		return categoryToList;
	}


	public void setCategoryToList(List<CasteTO> categoryToList) {
		this.categoryToList = categoryToList;
	}


	public List<ReligionTO> getReligionToList() {
		return religionToList;
	}


	public void setReligionToList(List<ReligionTO> religionToList) {
		this.religionToList = religionToList;
	}


	public List<NationalityTO> getNationalityToList() {
		return nationalityToList;
	}


	public void setNationalityToList(List<NationalityTO> nationalityToList) {
		this.nationalityToList = nationalityToList;
	}


	public String getReligionOthers() {
		return religionOthers;
	}


	public void setReligionOthers(String religionOthers) {
		this.religionOthers = religionOthers;
	}


	public String getCasteOthers() {
		return casteOthers;
	}


	public void setCasteOthers(String casteOthers) {
		this.casteOthers = casteOthers;
	}


	public String getNationalityOthers() {
		return nationalityOthers;
	}


	public void setNationalityOthers(String nationalityOthers) {
		this.nationalityOthers = nationalityOthers;
	}


	public String getDobInWords() {
		return dobInWords;
	}


	public void setDobInWords(String dobInWords) {
		this.dobInWords = dobInWords;
	}


	public String getRegMonthYear() {
		return regMonthYear;
	}


	public void setRegMonthYear(String regMonthYear) {
		this.regMonthYear = regMonthYear;
	}


	public String getCharacterAndConduct() {
		return characterAndConduct;
	}


	public void setCharacterAndConduct(String characterAndConduct) {
		this.characterAndConduct = characterAndConduct;
	}
	

	public String getSubjectsPassed() {
		return subjectsPassed;
	}


	public void setSubjectsPassed(String subjectsPassed) {
		this.subjectsPassed = subjectsPassed;
	}

	/*public String getReligionId() {
		return religionId;
	}


	public void setReligionId(String religionId) {
		this.religionId = religionId;
	}
*/

	public String getCasteCategoryName() {
		return casteCategoryName;
	}


	public void setCasteCategoryName(String casteCategoryName) {
		this.casteCategoryName = casteCategoryName;
	}


	public String getTempTcFor() {
		return tempTcFor;
	}


	public void setTempTcFor(String tempTcFor) {
		this.tempTcFor = tempTcFor;
	}


	public String getReligionName() {
		return religionName;
	}


	public void setReligionName(String religionName) {
		this.religionName = religionName;
	}


	public String getNationalityName() {
		return nationalityName;
	}


	public void setNationalityName(String nationalityName) {
		this.nationalityName = nationalityName;
	}


	public String getPassDetails() {
		return passDetails;
	}


	public void setPassDetails(String passDetails) {
		this.passDetails = passDetails;
	}

	public String getTempYear() {
		return tempYear;
	}


	public void setTempYear(String tempYear) {
		this.tempYear = tempYear;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}


	public String getTempTcType() {
		return tempTcType;
	}


	public void setTempTcType(String tempTcType) {
		this.tempTcType = tempTcType;
	}


	public String getMonth1() {
		return month1;
	}


	public void setMonth1(String month1) {
		this.month1 = month1;
	}

}
