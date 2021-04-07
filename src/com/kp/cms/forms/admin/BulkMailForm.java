package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.StudentSearchTO;

public class BulkMailForm extends BaseActionForm{
	
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
	
	private String marksObtained;
	
	private String weightage;
	
	private String university;
	
	private String institute;
	
	private String applicantName;
	
	private String birthCountry;
	
	private String birthState;
	
	private String interviewType;
	
	private String previousInterViewType;

	private String programTypeName;
	
	private String programName;
	
	private String courseName;
	

	private String selectedCandidates[];
	private String emailAddresses[];
	private String desc;
	private String subject;

	private List<StudentSearchTO> studentSearch;
	

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

	
	public String getInterviewType() {
		return interviewType;
	}

	public void setInterviewType(String interviewType) {
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

	public String getMarksObtained() {
		return marksObtained;
	}

	public void setMarksObtained(String marksObtained) {
		this.marksObtained = marksObtained;
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
		
		public String getDesc() {
			return desc;
		}
		
		public void setDesc(String desc) {
			this.desc = desc;
		}
		
		public String[] getEmailAddresses() {
			return emailAddresses;
		}

		public void setEmailAddresses(String[] emailAddresses) {
			this.emailAddresses = emailAddresses;
		}
		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	
	public void resetFields() {
		this.programTypeId = null;
		this.programId = null;
		this.courseId = null;
		super.setYear(null);
		this.nationalityId = null;
		this.residentCategoryId = null;
		this.religionId = null;
		this.subReligionId = null;
		this.casteCategoryId = null;
		this.belongsTo = null;
		this.gender = null;
		this.bloodGroup = null;
		this.marksObtained = null;
		this.weightage = null;
		this.university = null;
		this.institute = null;
		this.applicantName = null;
		this.birthCountry = null;
		this.birthState = null;
		this.interviewType = null;
		this.emailAddresses = null;
	}

}
