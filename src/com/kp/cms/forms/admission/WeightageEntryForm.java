package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.RecommendorTO;
import com.kp.cms.to.admin.ReligionTO;

public class WeightageEntryForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	
	private String programType;
	private List<ProgramTypeTO> programTypeList;
	private String program;
	private String course;	
	private String religion;
	private String religionWeightage;
	private List<ReligionTO> religionList;
	private String caste;
	private String casteWeightage;
	private List<CasteTO> castelist;
	private String lastUniversity;
	private String lastUniversityWeightage;
	private List universityList;
	private String specialCategory;
	private String specialCategoryWeightage;
	private String gender;
	private String genderWeightage;	
	private String originCountry;
	private String originCountryWeightage;	
	private Map originCountryMap;
	private String originCity;
	private String originCityWeightage;
	private String lastCollege;
	private String lastCollegeWeightage;
	private String referredBy;
	private String referredByWeightage;
	private List<RecommendorTO> referredlist;
	private String method;
	

	public String getProgramType() {
		return programType;
	}
	public void setProgramType(String programType) {
		this.programType = programType;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getCaste() {
		return caste;
	}
	public void setCaste(String caste) {
		this.caste = caste;
	}
	
	public String getReligion() {
		return religion;
	}
	public void setReligion(String religion) {
		this.religion = religion;
	}
	public String getLastUniversity() {
		return lastUniversity;
	}
	public void setLastUniversity(String lastUniversity) {
		this.lastUniversity = lastUniversity;
	}
	public String getSpecialCategory() {
		return specialCategory;
	}
	public void setSpecialCategory(String specialCategory) {
		this.specialCategory = specialCategory;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	public String getOriginCity() {
		return originCity;
	}
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	public String getLastCollege() {
		return lastCollege;
	}
	public void setLastCollege(String lastCollege) {
		this.lastCollege = lastCollege;
	}
	public String getReferredBy() {
		return referredBy;
	}
	public void setReferredBy(String referredBy) {
		this.referredBy = referredBy;
	}
	public String getReligionWeightage() {
		return religionWeightage;
	}
	public void setReligionWeightage(String religionWeightage) {
		this.religionWeightage = religionWeightage;
	}
	public String getCasteWeightage() {
		return casteWeightage;
	}
	public void setCasteWeightage(String casteWeightage) {
		this.casteWeightage = casteWeightage;
	}
	public String getLastUniversityWeightage() {
		return lastUniversityWeightage;
	}
	public void setLastUniversityWeightage(String lastUniversityWeightage) {
		this.lastUniversityWeightage = lastUniversityWeightage;
	}
	public String getSpecialCategoryWeightage() {
		return specialCategoryWeightage;
	}
	public void setSpecialCategoryWeightage(String specialCategoryWeightage) {
		this.specialCategoryWeightage = specialCategoryWeightage;
	}
	public String getGenderWeightage() {
		return genderWeightage;
	}
	public void setGenderWeightage(String genderWeightage) {
		this.genderWeightage = genderWeightage;
	}
	public String getOriginCountryWeightage() {
		return originCountryWeightage;
	}
	public void setOriginCountryWeightage(String originCountryWeightage) {
		this.originCountryWeightage = originCountryWeightage;
	}
	public String getOriginCityWeightage() {
		return originCityWeightage;
	}
	public void setOriginCityWeightage(String originCityWeightage) {
		this.originCityWeightage = originCityWeightage;
	}
	public String getLastCollegeWeightage() {
		return lastCollegeWeightage;
	}
	public void setLastCollegeWeightage(String lastCollegeWeightage) {
		this.lastCollegeWeightage = lastCollegeWeightage;
	}
	public String getReferredByWeightage() {
		return referredByWeightage;
	}
	public void setReferredByWeightage(String referredByWeightage) {
		this.referredByWeightage = referredByWeightage;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public List<CasteTO> getCastelist() {
		return castelist;
	}
	public void setCastelist(List<CasteTO> castelist) {
		this.castelist = castelist;
	}
	public List<RecommendorTO> getReferredlist() {
		return referredlist;
	}
	public void setReferredlist(List<RecommendorTO> referredlist) {
		this.referredlist = referredlist;
	}
	public Map getOriginCountryMap() {
		return originCountryMap;
	}
	public void setOriginCountryMap(Map originCountryMap) {
		this.originCountryMap = originCountryMap;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public List getUniversityList() {
		return universityList;
	}
	public void setUniversityList(List universityList) {
		this.universityList = universityList;
	}
	public List<ReligionTO> getReligionList() {
		return religionList;
	}
	public void setReligionList(List<ReligionTO> religionList) {
		this.religionList = religionList;
	}
	

}
