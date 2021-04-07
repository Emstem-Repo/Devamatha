package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CountryTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.StudentSearchTO;

public class StudentSearchForm extends BaseActionForm {

	private String programTypeId;
	
	private String programId;
	
	private String courseId;

	private String appliedYear;
	
	private String nationalityId;	

	private String residentCategoryId;	
	
	private String religionId;
	
	private String subReligionId;	

	private String casteCategoryId;
	
	private Character belongsTo;

	private String gender;	

	private String bloodGroup;
	
	private String percentageFrom;
	
	private String percentageTo;
	
	private String weightage;
	
	private String university;
	
	private String institute;
	
	private String applicantName;
	
	private String birthCountry;
	
	private String birthState;
	
	private String[] interviewType;
	private String searchedIntrvwType;
	
	private String previousInterViewType;

	private String programTypeName;
	
	private String programName;
	
	private String courseName;
	
	private Boolean sportsPerson;
	
	private Boolean handicapped;
	private boolean needApproval;
	private Boolean orderBy;
	private String selectedCandidates[];
	private String status;
	private String shortStatus;
	
	private String applicationNumber;
	private String applicationYear;
	private String appliedDateFrom;
	private String appliedDateTo;
	

	private List<StudentSearchTO> studentSearch;
	private List<CasteTO> casteList;
	private List<ReligionTO> religionList;
	private List<NationalityTO> nationTOs;
	private List<UniversityTO> universities;
	private List<CountryTO> countries;
	private String interviewDate;
	private List<ResidentCategoryTO> residentCategory;
	
	public String getAppliedDateFrom() {
		return appliedDateFrom;
	}

	public void setAppliedDateFrom(String appliedDateFrom) {
		this.appliedDateFrom = appliedDateFrom;
	}

	public String getAppliedDateTo() {
		return appliedDateTo;
	}

	public void setAppliedDateTo(String appliedDateTo) {
		this.appliedDateTo = appliedDateTo;
	}

	public String getPreviousInterViewType() {
		return previousInterViewType;
	}

	public void setPreviousInterViewType(String previousInterViewType) {
		this.previousInterViewType = previousInterViewType;
	}


	public String[] getSelectedCandidates() {
		return selectedCandidates;
	}

	public void setSelectedCandidates(String[] selectedCandidates) {
		this.selectedCandidates = selectedCandidates;
	}

	
	public String[] getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String[] interviewType) {
		this.interviewType = interviewType;
	}

	public List<StudentSearchTO> getStudentSearch() {
		return studentSearch;
	}

	public void setStudentSearch(List<StudentSearchTO> studentSearch) {
		this.studentSearch = studentSearch;
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

	

	public String getNationalityId() {
		return nationalityId;
	}

	public void setNationalityId(String nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getResidentCategoryId() {
		return residentCategoryId;
	}

	public void setResidentCategoryId(String residentCategoryId) {
		this.residentCategoryId = residentCategoryId;
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

	public String getCasteCategoryId() {
		return casteCategoryId;
	}

	public void setCasteCategoryId(String casteCategoryId) {
		this.casteCategoryId = casteCategoryId;
	}

	public Character getBelongsTo() {
		return belongsTo;
	}

	public void setBelongsTo(Character belongsTo) {
		this.belongsTo = belongsTo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	

	public String getWeightage() {
		return weightage;
	}

	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getInstitute() {
		return institute;
	}

	public void setInstitute(String institute) {
		this.institute = institute;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getBirthCountry() {
		return birthCountry;
	}

	public void setBirthCountry(String birthCountry) {
		this.birthCountry = birthCountry;
	}

	public String getBirthState() {
		return birthState;
	}

	public void setBirthState(String birthState) {
		this.birthState = birthState;
	}
	
	 public String getProgramTypeName() {
			return programTypeName;
		}

		public void setProgramTypeName(String programTypeName) {
			this.programTypeName = programTypeName;
		}

		public String getProgramName() {
			return programName;
		}

		public void setProgramName(String programName) {
			this.programName = programName;
		}

		public String getCourseName() {
			return courseName;
		}

		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		

		public String getAppliedYear() {
			return appliedYear;
		}

		public void setAppliedYear(String appliedYear) {
			this.appliedYear = appliedYear;
		}

	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		super.setYear(null);
		this.programTypeId = null;
		this.programId = null;
		this.courseId = null;
		this.nationalityId = null;
		this.residentCategoryId = null;
		this.religionId = null;
		this.subReligionId = null;
		this.casteCategoryId = null;
		this.belongsTo = null;
		this.gender = null;
		this.bloodGroup = null;
		this.percentageFrom = null;
		this.percentageTo = null;
		this.weightage = null;
		this.university = null;
		this.institute = null;
		this.applicantName = null;
		this.birthCountry = null;
		this.birthState = null;
		this.interviewType = null;
		this.sportsPerson = null;
		this.handicapped = null;
		this.interviewDate=null;
		this.orderBy=true;
		this.appliedDateFrom=null;
		this.appliedDateTo=null;
	}

	public String getPercentageFrom() {
		return percentageFrom;
	}

	public void setPercentageFrom(String percentageFrom) {
		this.percentageFrom = percentageFrom;
	}

	public String getPercentageTo() {
		return percentageTo;
	}

	public void setPercentageTo(String percentageTo) {
		this.percentageTo = percentageTo;
	}

	public void setSportsPerson(Boolean sportsPerson) {
		this.sportsPerson = sportsPerson;
	}

	public void setHandicapped(Boolean handicapped) {
		this.handicapped = handicapped;
	}

	public Boolean getSportsPerson() {
		return sportsPerson;
	}

	public Boolean getHandicapped() {
		return handicapped;
	}

	public String getSearchedIntrvwType() {
		return searchedIntrvwType;
	}

	public void setSearchedIntrvwType(String searchedIntrvwType) {
		this.searchedIntrvwType = searchedIntrvwType;
	}

	public boolean isNeedApproval() {
		return needApproval;
	}

	public void setNeedApproval(boolean needApproval) {
		this.needApproval = needApproval;
	}

	public List<CasteTO> getCasteList() {
		return casteList;
	}

	public void setCasteList(List<CasteTO> casteList) {
		this.casteList = casteList;
	}

	public List<ReligionTO> getReligionList() {
		return religionList;
	}

	public void setReligionList(List<ReligionTO> religionList) {
		this.religionList = religionList;
	}

	public List<NationalityTO> getNationTOs() {
		return nationTOs;
	}

	public void setNationTOs(List<NationalityTO> nationTOs) {
		this.nationTOs = nationTOs;
	}

	public List<UniversityTO> getUniversities() {
		return universities;
	}

	public void setUniversities(List<UniversityTO> universities) {
		this.universities = universities;
	}

	public List<CountryTO> getCountries() {
		return countries;
	}

	public void setCountries(List<CountryTO> countries) {
		this.countries = countries;
	}

	public String getInterviewDate() {
		return interviewDate;
	}

	public void setInterviewDate(String interviewDate) {
		this.interviewDate = interviewDate;
	}

	public List<ResidentCategoryTO> getResidentCategory() {
		return residentCategory;
	}

	public void setResidentCategory(List<ResidentCategoryTO> residentCategory) {
		this.residentCategory = residentCategory;
	}

	public Boolean getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(Boolean orderBy) {
		this.orderBy = orderBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getApplicationNumber() {
		return applicationNumber;
	}

	public void setApplicationNumber(String applicationNumber) {
		this.applicationNumber = applicationNumber;
	}

	public String getApplicationYear() {
		return applicationYear;
	}

	public void setApplicationYear(String applicationYear) {
		this.applicationYear = applicationYear;
	}

	public String getShortStatus() {
		return shortStatus;
	}

	public void setShortStatus(String shortStatus) {
		this.shortStatus = shortStatus;
	}

	


}
