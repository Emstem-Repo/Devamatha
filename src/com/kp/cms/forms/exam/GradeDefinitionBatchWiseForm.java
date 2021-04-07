package com.kp.cms.forms.exam;

/**
 * Dec 31, 2009 Created By 9Elements
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamGradeDefinitionTO;
import com.kp.cms.to.exam.GradeDefinitionBatchWisTo;
import com.kp.cms.to.exam.GradeDefinitionBatchWiseTO;

public class GradeDefinitionBatchWiseForm extends BaseActionForm {
	private int id = 0;
	private String courseName = "";
	private String startPercentage = "";
	private String endPercentage = "";
	private String grade = "";
	private String interpretation = "";
	private String resultClass = "";
	private String gradePoint = "";
	private String fromBatch = "";
	private String courseIds = "";
	private int orgCourseid = 0;
	private String orgStartPercentage = "";
	private String orgEndPercentage = "";
	private String orgGrade;
	private String orgInterpretation = "";
	private String orgResultClass = "";
	private String orgGradePoint = "";
	private String orgfromBatch = "";

	private List<ExamCourseUtilTO> listCourseName;
	private List<GradeDefinitionBatchWisTo> listExamCourseGroup;
	private List<GradeDefinitionBatchWiseTO> listGradeDefinition;

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void clearPage() {
		this.courseName = "";
		this.startPercentage = "";
		this.endPercentage = "";
		this.grade = "";
		this.interpretation = "";
		this.gradePoint = "";
		this.resultClass = "";
		this.fromBatch="";

	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public List<ExamCourseUtilTO> getListCourseName() {
		return listCourseName;
	}

	public void setListCourseName(List<ExamCourseUtilTO> listCourseName) {
		this.listCourseName = listCourseName;
	}

	public List<GradeDefinitionBatchWisTo> getListExamCourseGroup() {
		return listExamCourseGroup;
	}

	public void setListExamCourseGroup(List<GradeDefinitionBatchWisTo> list) {
		this.listExamCourseGroup = list;
	}

	public String getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}

	public String getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public String getResultClass() {
		return resultClass;
	}

	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	public String getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(String gradePoint) {
		this.gradePoint = gradePoint;
	}

	public List<GradeDefinitionBatchWiseTO> getListGradeDefinition() {
		return listGradeDefinition;
	}

	public void setListGradeDefinition(List<GradeDefinitionBatchWiseTO> listGradeDefinition) {
		this.listGradeDefinition = listGradeDefinition;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrgStartPercentage() {
		return orgStartPercentage;
	}

	public void setOrgStartPercentage(String orgStartPercentage) {
		this.orgStartPercentage = orgStartPercentage;
	}

	public String getOrgEndPercentage() {
		return orgEndPercentage;
	}

	public void setOrgEndPercentage(String orgEndPercentage) {
		this.orgEndPercentage = orgEndPercentage;
	}

	public String getOrgGrade() {
		return orgGrade;
	}

	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

	public String getOrgInterpretation() {
		return orgInterpretation;
	}

	public void setOrgInterpretation(String orgInterpretation) {
		this.orgInterpretation = orgInterpretation;
	}

	public String getOrgResultClass() {
		return orgResultClass;
	}

	public void setOrgResultClass(String orgResultClass) {
		this.orgResultClass = orgResultClass;
	}

	public String getOrgGradePoint() {
		return orgGradePoint;
	}

	public void setOrgGradePoint(String orgGradePoint) {
		this.orgGradePoint = orgGradePoint;
	}

	public int getOrgCourseid() {
		return orgCourseid;
	}

	public void setOrgCourseid(int orgCourseid) {
		this.orgCourseid = orgCourseid;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}
	public String getFromBatch() {
		return fromBatch;
	}

	public void setFromBatch(String fromBatch) {
		this.fromBatch = fromBatch;
	}

	public String getOrgfromBatch() {
		return orgfromBatch;
	}

	public void setOrgfromBatch(String orgfromBatch) {
		this.orgfromBatch = orgfromBatch;
	}
}
