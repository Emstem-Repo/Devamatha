package com.kp.cms.forms.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseSchemeTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admission.CurriculumSchemeDurationTO;
import com.kp.cms.to.admission.CurriculumSchemeTO;

public class CurriculumSchemeForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String method;
	private List<ProgramTypeTO> programTypeList;
	private List<SubjectGroupTO> subjectGroupList;
	private List<CourseSchemeTO> courseSchemeList;
	private CurriculumSchemeTO curriculumSchemeTO;
	private List<CurriculumSchemeTO> curriculumSchemeDetails = null;
	private String programType;
	private String program;
	private String course;
	private String schemeId;
	private String year;
	private String noOfScheme;
	private int id;
	
	private int oldCourseId;
	private int oldYear;
	private int oldNoOfScheme;
	private String schemeName;
	private String academicYear;
	private String noOfSchemeName;
	private List<Integer> curriculumDurationIdList;
	Map<Integer, Integer> subjectGroupMap;
	
	
	// This list is used to display all the curriculumscheme details on the 1st screen
	private List<CurriculumSchemeTO>schemeDetails;
	//This TO holds all the curriculumscheme properties (Programtype, program, course, year etc.). Displaying in the editcurriculumscheme.jsp
	private CurriculumSchemeTO editCurriculumSchemeTO;
	//This list having a set of curriculumschmeduration properties (Startdate & enddate).Displaying in the editcurriculumscheme.jsp
	private List<CurriculumSchemeDurationTO> durationList;
	
	public List<CurriculumSchemeDurationTO> getDurationList() {
		return durationList;
	}

	public void setDurationList(List<CurriculumSchemeDurationTO> durationList) {
		this.durationList = durationList;
	}

	public CurriculumSchemeForm()
	{
		editCurriculumSchemeTO=new CurriculumSchemeTO();
	}

	public CurriculumSchemeTO getEditCurriculumSchemeTO() {
		return editCurriculumSchemeTO;
	}

	public void setEditCurriculumSchemeTO(CurriculumSchemeTO editCurriculumSchemeTO) {
		this.editCurriculumSchemeTO = editCurriculumSchemeTO;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public List<CurriculumSchemeTO> getSchemeDetails() {
		return schemeDetails;
	}

	public void setSchemeDetails(List<CurriculumSchemeTO> schemeDetails) {
		this.schemeDetails = schemeDetails;
	}

	public List<CurriculumSchemeTO> getCurriculumSchemeDetails() {
		return curriculumSchemeDetails;
	}

	public void setCurriculumSchemeDetails(
			List<CurriculumSchemeTO> curriculumSchemeDetails) {
		this.curriculumSchemeDetails = curriculumSchemeDetails;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<SubjectGroupTO> getSubjectGroupList() {
		return subjectGroupList;
	}

	public void setSubjectGroupList(List<SubjectGroupTO> subjectGroupList) {
		this.subjectGroupList = subjectGroupList;
	}

	public List<CourseSchemeTO> getCourseSchemeList() {
		return courseSchemeList;
	}

	public void setCourseSchemeList(List<CourseSchemeTO> courseSchemeList) {
		this.courseSchemeList = courseSchemeList;
	}

	public CurriculumSchemeTO getCurriculumSchemeTO() {
		return curriculumSchemeTO;
	}

	public void setCurriculumSchemeTO(CurriculumSchemeTO curriculumSchemeTO) {
		this.curriculumSchemeTO = curriculumSchemeTO;
	}

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

	public String getSchemeId() {
		return schemeId;
	}

	public void setSchemeId(String schemeId) {
		this.schemeId = schemeId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getNoOfScheme() {
		return noOfScheme;
	}

	public void setNoOfScheme(String noOfScheme) {
		this.noOfScheme = noOfScheme;
	}

	public int getOldCourseId() {
		return oldCourseId;
	}

	public void setOldCourseId(int oldCourseId) {
		this.oldCourseId = oldCourseId;
	}

	public int getOldYear() {
		return oldYear;
	}

	public void setOldYear(int oldYear) {
		this.oldYear = oldYear;
	}

	public int getOldNoOfScheme() {
		return oldNoOfScheme;
	}

	public void setOldNoOfScheme(int oldNoOfScheme) {
		this.oldNoOfScheme = oldNoOfScheme;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getNoOfSchemeName() {
		return noOfSchemeName;
	}

	public void setNoOfSchemeName(String noOfSchemeName) {
		this.noOfSchemeName = noOfSchemeName;
	}

	public List<Integer> getCurriculumDurationIdList() {
		return curriculumDurationIdList;
	}

	public void setCurriculumDurationIdList(List<Integer> curriculumDurationIdList) {
		this.curriculumDurationIdList = curriculumDurationIdList;
	}

	public Map<Integer, Integer> getSubjectGroupMap() {
		return subjectGroupMap;
	}

	public void setSubjectGroupMap(Map<Integer, Integer> subjectGroupMap) {
		this.subjectGroupMap = subjectGroupMap;
	}

	/**
	 * This method is used for validation of the form fields
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	/**
	 * This method clears fields of the UI
	 */
	public void clear()
	{
		this.programType=null;
		this.program=null;
		this.course=null;
		this.noOfScheme=null;
		this.schemeId=null;
	}
}
