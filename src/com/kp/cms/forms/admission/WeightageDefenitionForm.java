package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CasteWeightageTO;
import com.kp.cms.to.admission.CountryWeightageTO;
import com.kp.cms.to.admission.CoursePrerequisiteWeightageTO;
import com.kp.cms.to.admission.EducationalWeightageDefenitionTO;
import com.kp.cms.to.admission.GenderWeightageTO;
import com.kp.cms.to.admission.InstituteWeightageTO;
import com.kp.cms.to.admission.InterviewWeightageDefenitionTO;
import com.kp.cms.to.admission.NationalityWeightageTO;
import com.kp.cms.to.admission.PreviousQualificationWeightageTO;
import com.kp.cms.to.admission.ReligionWeightageTO;
import com.kp.cms.to.admission.ResidentCategoryWeightageTO;
import com.kp.cms.to.admission.RuralUrbanWeightageTO;
import com.kp.cms.to.admission.SubReligionWeightageTO;
import com.kp.cms.to.admission.UniversityWeightageTO;
import com.kp.cms.to.admission.WorkExperienceWeightageTO;

public class WeightageDefenitionForm extends BaseActionForm {

	private String programTypeId;
	
	private String programId;
	
	private String courseId;
	
	private String year;
	
	private String educationWeightage ;
	
	private String interviewWeightage;
	
	private String prerequisiteWeightage;
	
	private String totalWeightage;	
	
	private String weightageType;
	
	private Map<Integer,String> weightageTypeMap;

	private List<EducationalWeightageDefenitionTO> educationWeightageList;
	
	private List<InterviewWeightageDefenitionTO> interviewWeightageList;
	
	private List<CasteWeightageTO> casteWeightageList;
	
	private List<SubReligionWeightageTO> religionSectionWeightageList; 
	
	private List<RuralUrbanWeightageTO> ruralUrbanWeightageList;
	
	private List<CountryWeightageTO> countryWeightageList ;
	
	private List<GenderWeightageTO> genderWeightageList;
	
	private List<InstituteWeightageTO> instituteWeightageList;
	
	private List<CoursePrerequisiteWeightageTO> prerequisiteWeightageList;
	
	private List<NationalityWeightageTO> nationalityWeightageList;
	
	private List<ReligionWeightageTO> religionWeightageList;
	
	private List<ResidentCategoryWeightageTO> residentCategoryWeightageList;
	
	private List<UniversityWeightageTO> universityWeightageList;
	
	private List<PreviousQualificationWeightageTO> previousQualificationWeightageList;
	
	private List<WorkExperienceWeightageTO> workExperienceWeightageList;
	
	
	private String selectionType;
	
	private String totalEducationWeightage;
	
	private String totalInterviewWeightage;
	
	private String totalPrerequisiteWeightage;
	
	private int weightageId;
	
	
	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public String getProgramTypeId() {
		return programTypeId;
	}

	public void setProgramTypeId(String programTypeId) {
		this.programTypeId = programTypeId;
	}

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getEducationWeightage() {
		return educationWeightage;
	}

	public void setEducationWeightage(String educationWeightage) {
		this.educationWeightage = educationWeightage;
	}

	public String getInterviewWeightage() {
		return interviewWeightage;
	}

	public void setInterviewWeightage(String interviewWeightage) {
		this.interviewWeightage = interviewWeightage;
	}



	public List<EducationalWeightageDefenitionTO> getEducationWeightageList() {
		return educationWeightageList;
	}

	public void setEducationWeightageList(
			List<EducationalWeightageDefenitionTO> educationWeightageList) {
		this.educationWeightageList = educationWeightageList;
	}

	public List<InterviewWeightageDefenitionTO> getInterviewWeightageList() {
		return interviewWeightageList;
	}

	public void setInterviewWeightageList(
			List<InterviewWeightageDefenitionTO> interviewWeightageList) {
		this.interviewWeightageList = interviewWeightageList;
	}	

	public String getWeightageType() {
		return weightageType;
	}

	public void setWeightageType(String weightageType) {
		this.weightageType = weightageType;
	}
	

	public Map<Integer, String> getWeightageTypeMap() {
		return weightageTypeMap;
	}

	public void setWeightageTypeMap(Map<Integer, String> weightageTypeMap) {
		this.weightageTypeMap = weightageTypeMap;
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public List<RuralUrbanWeightageTO> getRuralUrbanWeightageList() {
		return ruralUrbanWeightageList;
	}

	public void setRuralUrbanWeightageList(
			List<RuralUrbanWeightageTO> ruralUrbanWeightageList) {
		this.ruralUrbanWeightageList = ruralUrbanWeightageList;
	}

	public List<CountryWeightageTO> getCountryWeightageList() {
		return countryWeightageList;
	}

	public void setCountryWeightageList(
			List<CountryWeightageTO> countryWeightageList) {
		this.countryWeightageList = countryWeightageList;
	}

	public List<GenderWeightageTO> getGenderWeightageList() {
		return genderWeightageList;
	}

	public void setGenderWeightageList(List<GenderWeightageTO> genderWeightageList) {
		this.genderWeightageList = genderWeightageList;
	}

	public List<InstituteWeightageTO> getInstituteWeightageList() {
		return instituteWeightageList;
	}

	public void setInstituteWeightageList(
			List<InstituteWeightageTO> instituteWeightageList) {
		this.instituteWeightageList = instituteWeightageList;
	}

	public List<NationalityWeightageTO> getNationalityWeightageList() {
		return nationalityWeightageList;
	}

	public void setNationalityWeightageList(
			List<NationalityWeightageTO> nationalityWeightageList) {
		this.nationalityWeightageList = nationalityWeightageList;
	}

	public List<ReligionWeightageTO> getReligionWeightageList() {
		return religionWeightageList;
	}

	public void setReligionWeightageList(
			List<ReligionWeightageTO> religionWeightageList) {
		this.religionWeightageList = religionWeightageList;
	}

	public List<ResidentCategoryWeightageTO> getResidentCategoryWeightageList() {
		return residentCategoryWeightageList;
	}

	public void setResidentCategoryWeightageList(
			List<ResidentCategoryWeightageTO> residentCategoryWeightageList) {
		this.residentCategoryWeightageList = residentCategoryWeightageList;
	}

	public List<UniversityWeightageTO> getUniversityWeightageList() {
		return universityWeightageList;
	}

	public void setUniversityWeightageList(
			List<UniversityWeightageTO> universityWeightageList) {
		this.universityWeightageList = universityWeightageList;
	}

	public List<CasteWeightageTO> getCasteWeightageList() {
		return casteWeightageList;
	}

	public void setCasteWeightageList(List<CasteWeightageTO> casteWeightageList) {
		this.casteWeightageList = casteWeightageList;
	}

	

	public String getTotalEducationWeightage() {
		return totalEducationWeightage;
	}

	public void setTotalEducationWeightage(String totalEducationWeightage) {
		this.totalEducationWeightage = totalEducationWeightage;
	}

	public String getTotalInterviewWeightage() {
		return totalInterviewWeightage;
	}

	public void setTotalInterviewWeightage(String totalInterviewWeightage) {
		this.totalInterviewWeightage = totalInterviewWeightage;
	}

	public int getWeightageId() {
		return weightageId;
	}

	public void setWeightageId(int weightageId) {
		this.weightageId = weightageId;
	}
	
	
	
	public void resetWeightageEntry() {
		
		programTypeId = null;
		programId = null;
		courseId = null;
		year = null;
		reset();
	}
	
	public void reset() {
		interviewWeightage = null;
		educationWeightage = null;
		totalWeightage = null;
		selectionType = null;
		prerequisiteWeightage = null;
	}

	public String getTotalWeightage() {
		return totalWeightage;
	}

	public void setTotalWeightage(String totalWeightage) {
		this.totalWeightage = totalWeightage;
	}

	public List<SubReligionWeightageTO> getReligionSectionWeightageList() {
		return religionSectionWeightageList;
	}

	public void setReligionSectionWeightageList(
			List<SubReligionWeightageTO> religionSectionWeightageList) {
		this.religionSectionWeightageList = religionSectionWeightageList;
	}

	public List<CoursePrerequisiteWeightageTO> getPrerequisiteWeightageList() {
		return prerequisiteWeightageList;
	}

	public void setPrerequisiteWeightageList(
			List<CoursePrerequisiteWeightageTO> prerequisiteWeightageList) {
		this.prerequisiteWeightageList = prerequisiteWeightageList;
	}

	public String getTotalPrerequisiteWeightage() {
		return totalPrerequisiteWeightage;
	}

	public void setTotalPrerequisiteWeightage(String totalPrerequisiteWeightage) {
		this.totalPrerequisiteWeightage = totalPrerequisiteWeightage;
	}

	public String getPrerequisiteWeightage() {
		return prerequisiteWeightage;
	}

	public void setPrerequisiteWeightage(String prerequisiteWeightage) {
		this.prerequisiteWeightage = prerequisiteWeightage;
	}

	public List<PreviousQualificationWeightageTO> getPreviousQualificationWeightageList() {
		return previousQualificationWeightageList;
	}

	public void setPreviousQualificationWeightageList(
			List<PreviousQualificationWeightageTO> previousQualificationWeightageList) {
		this.previousQualificationWeightageList = previousQualificationWeightageList;
	}

	public List<WorkExperienceWeightageTO> getWorkExperienceWeightageList() {
		return workExperienceWeightageList;
	}

	public void setWorkExperienceWeightageList(
			List<WorkExperienceWeightageTO> workExperienceWeightageList) {
		this.workExperienceWeightageList = workExperienceWeightageList;
	}
	
}
