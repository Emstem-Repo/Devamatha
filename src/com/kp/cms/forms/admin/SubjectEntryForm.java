package com.kp.cms.forms.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;


public class SubjectEntryForm extends BaseActionForm{
	private int id;
	private String name;
	private String code;
	private String totalmarks;
	private String passingmarks;
	private String secondlanguage;
	private String optional;
	private List<SubjectTO> subjectList;
	private String isCertificateCourse;
	private String questionbyrequired;
	private String hourpersem;
	
	// Added by Lohit - 9Elements
	private String subjectTypeId ="";
	private String theoryPractical="" ;
	private String consMCSName ="";
	private String subjectNameprefix ="";
	private String majorDepartmentCodeId ="";
	private List<KeyValueTO> listMajorDepartmentCode;
	private List<KeyValueTO> listSubjectType;
	private Map<Integer,String> courseMap;
//	private String academicYear;
//	private String certificateId;
	private String isAdditionalSubject;
	private Map<String,String> departmentMap;
	private String departmentId;
	private String schemeNo;
	private String coCurricularSubject;
	private String eligibleCourseId;
	private String subjectCodeGroup;
	private Map<Integer, String> subjectCodeGroupMap; 
	private String subjectCodes;
	private String subjectNames;
	
	private String consolidatedSubjectStreamId="";
	private Map<Integer, String> consolidatedSubjectStreams;
	private String subjectTypeBasedOnMarks;
	
	
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
	//  this.schemeNo=Integer.toString(0);
		this.departmentId=null;
		this.code=null;
		this.name=null;
		this.totalmarks=null;
		this.passingmarks=null;
		this.secondlanguage=null;
		this.optional=null;
		this.subjectTypeId="";
		this.theoryPractical=""; 
		this.consMCSName ="";
		this.subjectNameprefix ="";
		this. majorDepartmentCodeId ="";
		this.isCertificateCourse = "";
		this.questionbyrequired="true";
		this.hourpersem="";
		this.coCurricularSubject=null;
		this.isAdditionalSubject=null;
		this.subjectCodeGroup=null;
		this.subjectTypeBasedOnMarks=null;
		this.consolidatedSubjectStreamId="";
	}
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	private String editedcode;
	private String editedname;
	private String editTheoryOrPractical;
	
	public String getEditTheoryOrPractical() {
		return editTheoryOrPractical;
	}

	public void setEditTheoryOrPractical(String editTheoryOrPractical) {
		this.editTheoryOrPractical = editTheoryOrPractical;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTotalmarks() {
		return totalmarks;
	}
	public void setTotalmarks(String totalmarks) {
		this.totalmarks = totalmarks;
	}
	public String getPassingmarks() {
		return passingmarks;
	}
	public void setPassingmarks(String passingmarks) {
		this.passingmarks = passingmarks;
	}
	public String getSecondlanguage() {
		return secondlanguage;
	}
	public void setSecondlanguage(String secondlanguage) {
		this.secondlanguage = secondlanguage;
	}
	public String getOptional() {
		return optional;
	}
	public void setOptional(String optional) {
		this.optional = optional;
	}
	public List<SubjectTO> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<SubjectTO> subjectList) {
		this.subjectList = subjectList;
	}
	public String getEditedcode() {
		return editedcode;
	}
	public void setEditedcode(String editedcode) {
		this.editedcode = editedcode;
	}
	
	public String getSubjectTypeId() {
		return subjectTypeId;
	}
	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}
	public String getTheoryPractical() {
		return theoryPractical;
	}
	public void setTheoryPractical(String theoryPractical) {
		this.theoryPractical = theoryPractical;
	}
	public String getConsMCSName() {
		return consMCSName;
	}
	public void setConsMCSName(String consMCSName) {
		this.consMCSName = consMCSName;
	}
	public String getSubjectNameprefix() {
		return subjectNameprefix;
	}
	public void setSubjectNameprefix(String subjectNameprefix) {
		this.subjectNameprefix = subjectNameprefix;
	}
	public String getMajorDepartmentCodeId() {
		return majorDepartmentCodeId;
	}
	public void setMajorDepartmentCodeId(String majorDepartmentCodeId) {
		this.majorDepartmentCodeId = majorDepartmentCodeId;
	}
	public List<KeyValueTO> getListMajorDepartmentCode() {
		return listMajorDepartmentCode;
	}
	public void setListMajorDepartmentCode(List<KeyValueTO> listMajorDepartmentCode) {
		this.listMajorDepartmentCode = listMajorDepartmentCode;
	}
	public List<KeyValueTO> getListSubjectType() {
		return listSubjectType;
	}
	public void setListSubjectType(List<KeyValueTO> listSubjectType) {
		this.listSubjectType = listSubjectType;
	}

	public String getIsCertificateCourse() {
		return isCertificateCourse;
	}

	public void setIsCertificateCourse(String isCertificateCourse) {
		this.isCertificateCourse = isCertificateCourse;
	}

	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
//
//	public String getAcademicYear() {
//		return academicYear;
//	}
//
//	public void setAcademicYear(String academicYear) {
//		this.academicYear = academicYear;
//	}
//
//	public String getCertificateId() {
//		return certificateId;
//	}
//
//	public void setCertificateId(String certificateId) {
//		this.certificateId = certificateId;
//	}

	public String getIsAdditionalSubject() {
		return isAdditionalSubject;
	}

	public void setIsAdditionalSubject(String isAdditionalSubject) {
		this.isAdditionalSubject = isAdditionalSubject;
	}

	public String getQuestionbyrequired() {
		return questionbyrequired;
	}

	public void setQuestionbyrequired(String questionbyrequired) {
		this.questionbyrequired = questionbyrequired;
	}

	public String getHourpersem() {
		return hourpersem;
	}

	public void setHourpersem(String hourpersem) {
		this.hourpersem = hourpersem;
	}

	public Map<String, String> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<String, String> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSchemeNo() {
		return schemeNo;
	}

	public void setSchemeNo(String schemeNo) {
		this.schemeNo = schemeNo;
	}

	public String getCoCurricularSubject() {
		return coCurricularSubject;
	}

	public void setCoCurricularSubject(String coCurricularSubject) {
		this.coCurricularSubject = coCurricularSubject;
	}

	public String getEligibleCourseId() {
		return eligibleCourseId;
	}

	public void setEligibleCourseId(String eligibleCourseId) {
		this.eligibleCourseId = eligibleCourseId;
	}

	public String getEditedname() {
		return editedname;
	}

	public void setEditedname(String editedname) {
		this.editedname = editedname;
	}

	public String getSubjectCodeGroup() {
		return subjectCodeGroup;
	}

	public void setSubjectCodeGroup(String subjectCodeGroup) {
		this.subjectCodeGroup = subjectCodeGroup;
	}

	public Map<Integer, String> getSubjectCodeGroupMap() {
		return subjectCodeGroupMap;
	}

	public void setSubjectCodeGroupMap(Map<Integer, String> subjectCodeGroupMap) {
		this.subjectCodeGroupMap = subjectCodeGroupMap;
	}
	public String getSubjectNames() {
		return subjectNames;
	}

	public void setSubjectNames(String subjectNames) {
		this.subjectNames = subjectNames;
	}

	public String getSubjectCodes() {
		return subjectCodes;
	}

	public void setSubjectCodes(String subjectCodes) {
		this.subjectCodes = subjectCodes;
	}

	public String getConsolidatedSubjectStreamId() {
		return consolidatedSubjectStreamId;
	}

	public void setConsolidatedSubjectStreamId(String consolidatedSubjectStreamId) {
		this.consolidatedSubjectStreamId = consolidatedSubjectStreamId;
	}

	public Map<Integer, String> getConsolidatedSubjectStreams() {
		return consolidatedSubjectStreams;
	}

	public void setConsolidatedSubjectStreams(
			Map<Integer, String> consolidatedSubjectStreams) {
		this.consolidatedSubjectStreams = consolidatedSubjectStreams;
	}

	public String getSubjectTypeBasedOnMarks() {
		return subjectTypeBasedOnMarks;
	}

	public void setSubjectTypeBasedOnMarks(String subjectTypeBasedOnMarks) {
		this.subjectTypeBasedOnMarks = subjectTypeBasedOnMarks;
	}
}
