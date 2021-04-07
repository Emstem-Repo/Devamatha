package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.admin.UniversityTO;

public class FeeCriteriaForm extends BaseActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	List<NationalityTO> nationalities;
	List<CollegeTO> collegeList;
	List<UniversityTO> universityList;
	List<ResidentCategoryTO> residentCategoryList;
	List<AdmittedThroughTO> admittedList;
	private String instituteID;
	private String nationalityID;
	private String residentCategoryId;
	private String admittedThroghId;
	private String language;
	private Map<Integer, String> feeOptionalGroupMap;
	private String additionalFeeGroup1;
	private String additionalFeeGroup2;
	private String additionalFeeGroup3;
	private Map<String, String> languageMap;

	public List<NationalityTO> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<NationalityTO> nationalities) {
		this.nationalities = nationalities;
	}

	public List<CollegeTO> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(List<CollegeTO> collegeList) {
		this.collegeList = collegeList;
	}

	public List<UniversityTO> getUniversityList() {
		return universityList;
	}

	public void setUniversityList(List<UniversityTO> universityList) {
		this.universityList = universityList;
	}

	public List<ResidentCategoryTO> getResidentCategoryList() {
		return residentCategoryList;
	}

	public void setResidentCategoryList(
			List<ResidentCategoryTO> residentCategoryList) {
		this.residentCategoryList = residentCategoryList;
	}

	public List<AdmittedThroughTO> getAdmittedList() {
		return admittedList;
	}

	public void setAdmittedList(List<AdmittedThroughTO> admittedList) {
		this.admittedList = admittedList;
	}

	public String getInstituteID() {
		return instituteID;
	}

	public void setInstituteID(String instituteID) {
		this.instituteID = instituteID;
	}

	public String getNationalityID() {
		return nationalityID;
	}

	public void setNationalityID(String nationalityID) {
		this.nationalityID = nationalityID;
	}

	public String getResidentCategoryId() {
		return residentCategoryId;
	}

	public void setResidentCategoryId(String residentCategoryId) {
		this.residentCategoryId = residentCategoryId;
	}

	public String getAdmittedThroghId() {
		return admittedThroghId;
	}

	public void setAdmittedThroghId(String admittedThroghId) {
		this.admittedThroghId = admittedThroghId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public Map<Integer, String> getFeeOptionalGroupMap() {
		return feeOptionalGroupMap;
	}

	public void setFeeOptionalGroupMap(Map<Integer, String> feeOptionalGroupMap) {
		this.feeOptionalGroupMap = feeOptionalGroupMap;
	}

	public String getAdditionalFeeGroup1() {
		return additionalFeeGroup1;
	}

	public void setAdditionalFeeGroup1(String additionalFeeGroup1) {
		this.additionalFeeGroup1 = additionalFeeGroup1;
	}

	public String getAdditionalFeeGroup2() {
		return additionalFeeGroup2;
	}

	public void setAdditionalFeeGroup2(String additionalFeeGroup2) {
		this.additionalFeeGroup2 = additionalFeeGroup2;
	}

	public String getAdditionalFeeGroup3() {
		return additionalFeeGroup3;
	}

	public void setAdditionalFeeGroup3(String additionalFeeGroup3) {
		this.additionalFeeGroup3 = additionalFeeGroup3;
	}

	public Map<String, String> getLanguageMap() {
		return languageMap;
	}

	public void setLanguageMap(Map<String, String> languageMap) {
		this.languageMap = languageMap;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		

}
