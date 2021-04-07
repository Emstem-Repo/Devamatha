package com.kp.cms.forms.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.EvaluationStudentFeedbackTO;
import com.kp.cms.to.admin.StudentFeedbackInstructionsTO;
import com.kp.cms.to.admin.TeacherClassSubjectTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackGroupTo;
import com.kp.cms.to.studentfeedback.EvaStudentFeedBackQuestionTo;
import com.kp.cms.to.studentfeedback.ExitEvaluationQuestionTo;

@SuppressWarnings("serial")
public class EvaluationStudentFeedbackForm extends BaseActionForm{
	int id;
	public List<StudentFeedbackInstructionsTO> instructionsList;
	private List<EvaluationStudentFeedbackTO> facultyEvaluationTo;
	private int teacherId;
	private String teacherName;
	private String subjectName;
	private int subjectNo;
	private int totalSubjects;
	private int evaluationFeedbackId;
	private int studentId;
	private List<TeacherClassSubjectTO>  teacherClassSubjectToList;
	private List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList ;
	private String totalPercentage;
	private String totalConducted;
	private String totalPresent;
	private String totalAbscent;
	private int totalGroups;
	private int totalQuestions;
	private int tempTotalGroups;
	private int tempTotalQuestions;
	private int sessionId;
	private List<EvaStudentFeedBackQuestionTo> questionListTo; 
	private String remarks;
	private String additionalInfo;
	private int admApplnId;
	private boolean isExitEval;
	private String studentName;
	Map<String,List<ExitEvaluationQuestionTo>> exitEvalMap=new HashMap<String, List<ExitEvaluationQuestionTo>>();
	List<ExitEvaluationQuestionTo> exitEvalQuestionList=new ArrayList<ExitEvaluationQuestionTo>();
	private String exitEvalRemarks;
	private List<TeacherClassSubjectTO> studentFeedbackSubjectWise;
	private int studentFeedbackSubjectWiseTotalSubjects;
	private List<EvaStudentFeedBackQuestionTo> questionListSubjectWiseTo; 
	private List<EvaluationStudentFeedbackTO> subjectWiseEvalutionEvaluationTo;

	

	

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<StudentFeedbackInstructionsTO> getInstructionsList() {
		return instructionsList;
	}

	public void setInstructionsList(
			List<StudentFeedbackInstructionsTO> instructionsList) {
		this.instructionsList = instructionsList;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public int getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(int subjectNo) {
		this.subjectNo = subjectNo;
	}

	public int getTotalSubjects() {
		return totalSubjects;
	}

	public void setTotalSubjects(int totalSubjects) {
		this.totalSubjects = totalSubjects;
	}


	public int getEvaluationFeedbackId() {
		return evaluationFeedbackId;
	}

	public void setEvaluationFeedbackId(int evaluationFeedbackId) {
		this.evaluationFeedbackId = evaluationFeedbackId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public List<EvaluationStudentFeedbackTO> getFacultyEvaluationTo() {
		return facultyEvaluationTo;
	}

	public void setFacultyEvaluationTo(
			List<EvaluationStudentFeedbackTO> facultyEvaluationTo) {
		this.facultyEvaluationTo = facultyEvaluationTo;
	}

	public List<TeacherClassSubjectTO> getTeacherClassSubjectToList() {
		return teacherClassSubjectToList;
	}

	public void setTeacherClassSubjectToList(
			List<TeacherClassSubjectTO> teacherClassSubjectToList) {
		this.teacherClassSubjectToList = teacherClassSubjectToList;
	}


	public List<StudentWiseSubjectSummaryTO> getSubjectwiseAttendanceTOList() {
		return subjectwiseAttendanceTOList;
	}

	public void setSubjectwiseAttendanceTOList(
			List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList) {
		this.subjectwiseAttendanceTOList = subjectwiseAttendanceTOList;
	}

	public String getTotalPercentage() {
		return totalPercentage;
	}

	public void setTotalPercentage(String totalPercentage) {
		this.totalPercentage = totalPercentage;
	}

	public String getTotalConducted() {
		return totalConducted;
	}

	public void setTotalConducted(String totalConducted) {
		this.totalConducted = totalConducted;
	}

	public String getTotalPresent() {
		return totalPresent;
	}

	public void setTotalPresent(String totalPresent) {
		this.totalPresent = totalPresent;
	}

	public String getTotalAbscent() {
		return totalAbscent;
	}

	public void setTotalAbscent(String totalAbscent) {
		this.totalAbscent = totalAbscent;
	}


	public int getTotalGroups() {
		return totalGroups;
	}

	public void setTotalGroups(int totalGroups) {
		this.totalGroups = totalGroups;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public int getTempTotalGroups() {
		return tempTotalGroups;
	}

	public void setTempTotalGroups(int tempTotalGroups) {
		this.tempTotalGroups = tempTotalGroups;
	}

	public int getTempTotalQuestions() {
		return tempTotalQuestions;
	}

	public void setTempTotalQuestions(int tempTotalQuestions) {
		this.tempTotalQuestions = tempTotalQuestions;
	}

	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}

	public List<EvaStudentFeedBackQuestionTo> getQuestionListTo() {
		return questionListTo;
	}

	public void setQuestionListTo(List<EvaStudentFeedBackQuestionTo> questionListTo) {
		this.questionListTo = questionListTo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public int getAdmApplnId() {
		return admApplnId;
	}

	public void setAdmApplnId(int admApplnId) {
		this.admApplnId = admApplnId;
	}

	public boolean getIsExitEval() {
		return isExitEval;
	}

	public void setIsExitEval(boolean isExitEval) {
		this.isExitEval = isExitEval;
	}

	public Map<String, List<ExitEvaluationQuestionTo>> getExitEvalMap() {
		return exitEvalMap;
	}

	public void setExitEvalMap(
			Map<String, List<ExitEvaluationQuestionTo>> exitEvalMap) {
		this.exitEvalMap = exitEvalMap;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<ExitEvaluationQuestionTo> getExitEvalQuestionList() {
		return exitEvalQuestionList;
	}

	public void setExitEvalQuestionList(
			List<ExitEvaluationQuestionTo> exitEvalQuestionList) {
		this.exitEvalQuestionList = exitEvalQuestionList;
	}

	public String getExitEvalRemarks() {
		return exitEvalRemarks;
	}

	public void setExitEvalRemarks(String exitEvalRemarks) {
		this.exitEvalRemarks = exitEvalRemarks;
	}
	public List<TeacherClassSubjectTO> getStudentFeedbackSubjectWise() {
		return studentFeedbackSubjectWise;
	}

	public void setStudentFeedbackSubjectWise(
			List<TeacherClassSubjectTO> studentFeedbackSubjectWise) {
		this.studentFeedbackSubjectWise = studentFeedbackSubjectWise;
	}
	public int getStudentFeedbackSubjectWiseTotalSubjects() {
		return studentFeedbackSubjectWiseTotalSubjects;
	}

	public void setStudentFeedbackSubjectWiseTotalSubjects(
			int studentFeedbackSubjectWiseTotalSubjects) {
		this.studentFeedbackSubjectWiseTotalSubjects = studentFeedbackSubjectWiseTotalSubjects;
	}
	public List<EvaStudentFeedBackQuestionTo> getQuestionListSubjectWiseTo() {
		return questionListSubjectWiseTo;
	}

	public void setQuestionListSubjectWiseTo(
			List<EvaStudentFeedBackQuestionTo> questionListSubjectWiseTo) {
		this.questionListSubjectWiseTo = questionListSubjectWiseTo;
	}
	public List<EvaluationStudentFeedbackTO> getSubjectWiseEvalutionEvaluationTo() {
		return subjectWiseEvalutionEvaluationTo;
	}

	public void setSubjectWiseEvalutionEvaluationTo(
			List<EvaluationStudentFeedbackTO> subjectWiseEvalutionEvaluationTo) {
		this.subjectWiseEvalutionEvaluationTo = subjectWiseEvalutionEvaluationTo;
	}
	

	

}
