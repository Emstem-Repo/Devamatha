package com.kp.cms.forms.serviceLearning;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.serviceLearning.DepartmentNameEntryTo;
import com.kp.cms.to.serviceLearning.ProgrammeEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningActivityTo;
import com.kp.cms.to.serviceLearning.ServiceLearningMarksEntryTo;
import com.kp.cms.to.serviceLearning.ServiceLearningmarksOverallEntryTo;

public class ServiceLearningMarksEntryForm extends BaseActionForm {
	private static final long serialVersionUID = 1L;
	private String id;
	private String dupId;
	private String startDate;
	private List<ProgrammeEntryTo> programmeEntryToList;
	private String departmentName;
	private String inchargeName;
	private List<ServiceLearningActivityTo> serviceLearningActivityToList;
	private String studentId;
	private String overallMark;
	private Boolean showList;
	private String appliedYear;
	List<ProgramTypeTO> programTypeList;
	Map<Integer, String> courseMap;
	List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList;
	private String courseTitle;
	private String studentName;
	private String registerNo;
	Map<Integer, String> programeMap;
	private List<DepartmentNameEntryTo> departmentNameEntryToList;
	private List<ExtraCreditActivityTypeTo> list;
	private String extraCreditActivityType;
	Map<Integer, String> programeCode;

	
	public List<DepartmentNameEntryTo> getDepartmentNameEntryToList() {
		return departmentNameEntryToList;
	}
	public void setDepartmentNameEntryToList(
			List<DepartmentNameEntryTo> departmentNameEntryToList) {
		this.departmentNameEntryToList = departmentNameEntryToList;
	}
	public Map<Integer, String> getProgrameMap() {
		return programeMap;
	}
	public void setProgrameMap(Map<Integer, String> programeMap) {
		this.programeMap = programeMap;
	}

	List<ServiceLearningMarksEntryTo> serviceLearningmarksEntryToList;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDupId() {
		return dupId;
	}
	public void setDupId(String dupId) {
		this.dupId = dupId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public List<ProgrammeEntryTo> getProgrammeEntryToList() {
		return programmeEntryToList;
	}
	public void setProgrammeEntryToList(List<ProgrammeEntryTo> programmeEntryToList) {
		this.programmeEntryToList = programmeEntryToList;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getInchargeName() {
		return inchargeName;
	}
	public void setInchargeName(String inchargeName) {
		this.inchargeName = inchargeName;
	}
	public List<ServiceLearningActivityTo> getServiceLearningActivityToList() {
		return serviceLearningActivityToList;
	}
	public void setServiceLearningActivityToList(
			List<ServiceLearningActivityTo> serviceLearningActivityToList) {
		this.serviceLearningActivityToList = serviceLearningActivityToList;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getOverallMark() {
		return overallMark;
	}
	public void setOverallMark(String overallMark) {
		this.overallMark = overallMark;
	}
	public Boolean getShowList() {
		return showList;
	}
	public void setShowList(Boolean showList) {
		this.showList = showList;
	}
	
	public String getAppliedYear() {
		return appliedYear;
	}
	public void setAppliedYear(String appliedYear) {
		this.appliedYear = appliedYear;
	}
	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public Map<Integer, String> getCourseMap() {
		return courseMap;
	}
	public void setCourseMap(Map<Integer, String> courseMap) {
		this.courseMap = courseMap;
	}
	public List<ServiceLearningmarksOverallEntryTo> getServiceLearningmarksOverallEntryToList() {
		return serviceLearningmarksOverallEntryToList;
	}
	public void setServiceLearningmarksOverallEntryToList(
			List<ServiceLearningmarksOverallEntryTo> serviceLearningmarksOverallEntryToList) {
		this.serviceLearningmarksOverallEntryToList = serviceLearningmarksOverallEntryToList;
	}
	
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	public List<ServiceLearningMarksEntryTo> getServiceLearningmarksEntryToList() {
		return serviceLearningmarksEntryToList;
	}
	public void setServiceLearningmarksEntryToList(
			List<ServiceLearningMarksEntryTo> serviceLearningmarksEntryToList) {
		this.serviceLearningmarksEntryToList = serviceLearningmarksEntryToList;
	}

	
	public List<ExtraCreditActivityTypeTo> getList() {
		return list;
	}
	public void setList(List<ExtraCreditActivityTypeTo> list) {
		this.list = list;
	}
	public String getExtraCreditActivityType() {
		return extraCreditActivityType;
	}
	public void setExtraCreditActivityType(String extraCreditActivityType) {
		this.extraCreditActivityType = extraCreditActivityType;
	}
	
	public Map<Integer, String> getProgrameCode() {
		return programeCode;
	}
	public void setProgrameCode(Map<Integer, String> programeCode) {
		this.programeCode = programeCode;
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.programeCode = null;
			}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
