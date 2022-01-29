package com.kp.cms.forms.exam;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.exam.GradePointRangeBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgressCardTo;
import com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.exam.ProgressCardStudentDetailTO;

public class AdminMarksCardForm extends BaseActionForm {
	
	private static final long serialVersionUID = 1L;
	private String regNoFrom;
	private String regNoTo;
	private String examId;
	private String classId;
	private String examType;
	private List<KeyValueTO> programTypeList;
	Map<Integer, String> examNameMap;
	Map<Integer, String> classMap;
	private String description;
	List<MarksCardTO> studentList;
	private boolean print;
	private boolean pg;
	private String exam1;
	private boolean displaySem1and2;
	private String batch;
	private String programTypeIdNew;
	private String dynamicSize;
	private List<ExamSubCoursewiseGradeDefnTO> examSubCoursewiseGradeDefnTOList;
	private List<GradePointRangeBO> courseWiseGradeList;
	private List<ProgressCardStudentDetailTO> candidateList;
	private String studentId;
	private String registrationNo;
	private List<ProgressCardTo> progressCardList;

	
	public List<ExamSubCoursewiseGradeDefnTO> getExamSubCoursewiseGradeDefnTOList() {
		return examSubCoursewiseGradeDefnTOList;
	}
	public void setExamSubCoursewiseGradeDefnTOList(
			List<ExamSubCoursewiseGradeDefnTO> examSubCoursewiseGradeDefnTOList) {
		this.examSubCoursewiseGradeDefnTOList = examSubCoursewiseGradeDefnTOList;
	}
	public String getDynamicSize() {
		return dynamicSize;
	}
	public void setDynamicSize(String dynamicSize) {
		this.dynamicSize = dynamicSize;
	}
	public String getRegNoFrom() {
		return regNoFrom;
	}
	public void setRegNoFrom(String regNoFrom) {
		this.regNoFrom = regNoFrom;
	}
	public String getRegNoTo() {
		return regNoTo;
	}
	public void setRegNoTo(String regNoTo) {
		this.regNoTo = regNoTo;
	}
	
	public String getExamId() {
		return examId;
	}
	public void setExamId(String examId) {
		this.examId = examId;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}
	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	public Map<Integer, String> getExamNameMap() {
		return examNameMap;
	}
	public void setExamNameMap(Map<Integer, String> examNameMap) {
		this.examNameMap = examNameMap;
	}
	public Map<Integer, String> getClassMap() {
		return classMap;
	}
	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}
	public String getExamType() {
		return examType;
	}
	public void setExamType(String examType) {
		this.examType = examType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MarksCardTO> getStudentList() {
		return studentList;
	}
	public void setStudentList(List<MarksCardTO> studentList) {
		this.studentList = studentList;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}
	
	public void resetFields() {
		this.regNoFrom=null;
		this.regNoTo=null;
		this.examId=null;
		this.classId=null;
		super.setProgramTypeId(null);
		this.examType="Regular";
//		this.description=null;
		this.print=false;
	}
	public boolean isPrint() {
		return print;
	}
	public void setPrint(boolean print) {
		this.print = print;
	}
	public boolean isPg() {
		return pg;
	}
	public void setPg(boolean pg) {
		this.pg = pg;
	}
	public String getExam1() {
		return exam1;
	}
	public void setExam1(String exam1) {
		this.exam1 = exam1;
	}
	public boolean isDisplaySem1and2() {
		return displaySem1and2;
	}
	public void setDisplaySem1and2(boolean displaySem1and2) {
		this.displaySem1and2 = displaySem1and2;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getProgramTypeIdNew() {
		return programTypeIdNew;
	}
	public void setProgramTypeIdNew(String programTypeIdNew) {
		this.programTypeIdNew = programTypeIdNew;
	}
	public List<GradePointRangeBO> getCourseWiseGradeList() {
		return courseWiseGradeList;
	}
	public void setCourseWiseGradeList(List<GradePointRangeBO> courseWiseGradeList) {
		this.courseWiseGradeList = courseWiseGradeList;
	}
	public List<ProgressCardStudentDetailTO> getCandidateList() {
		return candidateList;
	}
	public void setCandidateList(List<ProgressCardStudentDetailTO> candidateList) {
		this.candidateList = candidateList;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getRegistrationNo() {
		return registrationNo;
	}
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	public List<ProgressCardTo> getProgressCardList() {
		return progressCardList;
	}
	public void setProgressCardList(List<ProgressCardTo> progressCardList) {
		this.progressCardList = progressCardList;
	}

	
}
