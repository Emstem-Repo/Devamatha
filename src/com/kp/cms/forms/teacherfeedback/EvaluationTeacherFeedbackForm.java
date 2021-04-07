package com.kp.cms.forms.teacherfeedback;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.teacherfeedback.EvaTeacherFeedBackQuestionTo;
import com.kp.cms.to.teacherfeedback.EvaluationTeacherFeedbackTo;
import com.kp.cms.to.teacherfeedback.TeacherFeedbackInstructionsTO;

/**
 * @author vinodha
 *
 */
/**
 * @author vinodha
 *
 */
/**
 * @author vinodha
 *
 */
public class EvaluationTeacherFeedbackForm extends BaseActionForm{

	private String classId;
	private Map<Integer,String> subjectMap = new LinkedHashMap<Integer, String>();
	private List<TeacherFeedbackInstructionsTO> instructionsTOsList;
	private List<EvaluationTeacherFeedbackTo>  studentList;
	private String errorMsg;
	private boolean submitSuccessfully;
	private boolean evaluationCompleted;
	private boolean lastStudent;
	private boolean exist;
	private List<EvaTeacherFeedBackQuestionTo> evaTeacherQuestionsToList;
	private int studentId;
	private int totalStudents;
	private int studentNo;
	private String remarks;
	private int totalQuestions;
	private String previousStudentName;
	private EvaluationTeacherFeedbackTo tempTeachersEvaList;
	private Map<String, String> classMapForFeedback;
	private Map<Integer, EvaluationTeacherFeedbackTo> studentsFeedbackMap;
	private boolean comparisionReport;
	private String totalAverage;
	private String totalPointsScoredByTeacher;
	private String totalWeightedPoints;
	private String totalWeightsAndIndexPoints;
	private Integer roleId;
	private Integer teacherId;
	private Map<Integer, String> teacherMap;
	private boolean isTeacher;
	
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Map<Integer, String> getSubjectMap() {
		return subjectMap;
	}

	public void setSubjectMap(Map<Integer, String> subjectMap2) {
		this.subjectMap = subjectMap2;
	}

	public List<TeacherFeedbackInstructionsTO> getInstructionsTOsList() {
		return instructionsTOsList;
	}

	public void setInstructionsTOsList(
			List<TeacherFeedbackInstructionsTO> instructionsTOsList) {
		this.instructionsTOsList = instructionsTOsList;
	}

	public List<EvaluationTeacherFeedbackTo> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<EvaluationTeacherFeedbackTo> studentList) {
		this.studentList = studentList;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isSubmitSuccessfully() {
		return submitSuccessfully;
	}

	public void setSubmitSuccessfully(boolean submitSuccessfully) {
		this.submitSuccessfully = submitSuccessfully;
	}

	public boolean isEvaluationCompleted() {
		return evaluationCompleted;
	}

	public void setEvaluationCompleted(boolean evaluationCompleted) {
		this.evaluationCompleted = evaluationCompleted;
	}

	public boolean isLastStudent() {
		return lastStudent;
	}

	public void setLastStudent(boolean lastStudent) {
		this.lastStudent = lastStudent;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}

	public List<EvaTeacherFeedBackQuestionTo> getEvaTeacherQuestionsToList() {
		return evaTeacherQuestionsToList;
	}

	public void setEvaTeacherQuestionsToList(
			List<EvaTeacherFeedBackQuestionTo> evaTeacherQuestionsToList) {
		this.evaTeacherQuestionsToList = evaTeacherQuestionsToList;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getTotalStudents() {
		return totalStudents;
	}

	public void setTotalStudents(int totalStudents) {
		this.totalStudents = totalStudents;
	}

	public int getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(int studentNo) {
		this.studentNo = studentNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public String getPreviousStudentName() {
		return previousStudentName;
	}

	public void setPreviousStudentName(String previousStudentName) {
		this.previousStudentName = previousStudentName;
	}

	public EvaluationTeacherFeedbackTo getTempTeachersEvaList() {
		return tempTeachersEvaList;
	}

	public void setTempTeachersEvaList(
			EvaluationTeacherFeedbackTo tempTeachersEvaList) {
		this.tempTeachersEvaList = tempTeachersEvaList;
	}
	
	
	public Map<String, String> getClassMapForFeedback() {
		return classMapForFeedback;
	}

	public void setClassMapForFeedback(Map<String, String> classMapForFeedback) {
		this.classMapForFeedback = classMapForFeedback;
	}

	public Map<Integer, EvaluationTeacherFeedbackTo> getStudentsFeedbackMap() {
		return studentsFeedbackMap;
	}

	public void setStudentsFeedbackMap(
			Map<Integer, EvaluationTeacherFeedbackTo> studentsFeedbackMap) {
		this.studentsFeedbackMap = studentsFeedbackMap;
	}

	public boolean isComparisionReport() {
		return comparisionReport;
	}

	public void setComparisionReport(boolean comparisionReport) {
		this.comparisionReport = comparisionReport;
	}

	public String getTotalAverage() {
		return totalAverage;
	}

	public void setTotalAverage(String totalAverage) {
		this.totalAverage = totalAverage;
	}

	public String getTotalPointsScoredByTeacher() {
		return totalPointsScoredByTeacher;
	}

	public void setTotalPointsScoredByTeacher(String totalPointsScoredByTeacher) {
		this.totalPointsScoredByTeacher = totalPointsScoredByTeacher;
	}

	public String getTotalWeightedPoints() {
		return totalWeightedPoints;
	}

	public void setTotalWeightedPoints(String totalWeightedPoints) {
		this.totalWeightedPoints = totalWeightedPoints;
	}

	public String getTotalWeightsAndIndexPoints() {
		return totalWeightsAndIndexPoints;
	}

	public void setTotalWeightsAndIndexPoints(String totalWeightsAndIndexPoints) {
		this.totalWeightsAndIndexPoints = totalWeightsAndIndexPoints;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Map<Integer, String> getTeacherMap() {
		return teacherMap;
	}

	public void setTeacherMap(Map<Integer, String> teacherMap) {
		this.teacherMap = teacherMap;
	}

	public boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

	public void clear() {
		super.clear();
		this.classId = null;
		this.subjectMap = null;
		this.instructionsTOsList = null;
		this.studentList = null;
		this.errorMsg = null;
		this.submitSuccessfully = false;
		this.evaluationCompleted = false;
		this.lastStudent = false;
		this.exist = false;
		this.evaTeacherQuestionsToList = null;
		this.remarks = null;
		this.previousStudentName = null;
		this.tempTeachersEvaList = null;
		this.studentsFeedbackMap = null;
		this.totalAverage = null;
		this.totalPointsScoredByTeacher = null;
		this.totalWeightedPoints = null;
		this.totalWeightsAndIndexPoints = null;
		this.roleId = null;
		this.teacherId = null;
		this.teacherMap = null;
	}
	
}
