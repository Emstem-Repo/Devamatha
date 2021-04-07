package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAssignmentTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsAttendanceTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsEditTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleAnswerScriptTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsMultipleEvaluatorTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsPracticalInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsSubInternalTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryESETO;
import com.kp.cms.to.exam.ExamSubjectRuleSettingsTheoryInternalTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamSubjectRuleSettingsForm extends BaseActionForm {

	private String id;
	private String value;
	private String selectSchemeType;
	private String copyAcademicYear;
	private ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO;
	private ExamSubjectRuleSettingsTheoryESETO theoryESETO;
	private ExamSubjectRuleSettingsPracticalESETO practicalESETO;
	private ExamSubjectRuleSettingsPracticalInternalTO practicalInternalTO;
	private List<ExamSubjectRuleSettingsMultipleEvaluatorTO> mulEvalTOList;
	private List<ExamSubjectRuleSettingsMultipleEvaluatorTO> mulEvalTOListPractical;
	private List<ExamSubjectRuleSettingsEditTO> edittedSubRuleList;
	private ExamSubjectRuleSettingsSubInternalTO subTO;
	private ExamSubjectRuleSettingsAttendanceTO attTO;
	private ExamSubjectRuleSettingsAttendanceTO practicalTO;
	private String academicYearName;
	private String selectedAttendanceType;
	private String schemeName;
	private String selectedProgramType;
	private String[] selectedCourse;
	private String courseIds = "";
	private List<KeyValueTO> programTypeList;
	private List<Integer> schemeTypeList;
	private HashMap<Integer, String> attendanceTypeList;
	private Map<Integer, String> mapCourse;
	private String checkedActiveDummy;
	private ArrayList<Integer> listCourses;
	private List<ExamCourseUtilTO> listCourseName;
	private List<ExamSubjectRuleSettingsSubInternalTO> subInternalList;
	private List<ExamSubjectRuleSettingsSubInternalTO> subInternalListPractical;
	private List<ExamSubjectRuleSettingsAssignmentTO> assignmentList;
	private List<ExamSubjectRuleSettingsAssignmentTO> assignmentListPractical;
	private List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList;
	private List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptListPractical;
	private ExamSubjectRuleSettingsSubInternalTO subTOPractical;
	private String back;
	private String maximumSubjectFinal;
	private String subjectFinalPracticalExamChecked;
	private String subjectFinalInternalExamChecked;
	private String subjectFinalAttendanceChecked;
	private String valuatedSubjectFinal;
	private String minimumSubjectFinal;
	private String subjectFinalTheoryExamChecked;
	private ExamSubjectRuleSettingsTO subjectFinalTO;
	private String subjectName;
	public String getSubjectFinalTheoryExamChecked() {
		return subjectFinalTheoryExamChecked;
	}

	public void setSubjectFinalTheoryExamChecked(
			String subjectFinalTheoryExamChecked) {
		this.subjectFinalTheoryExamChecked = subjectFinalTheoryExamChecked;
	}

	public String getSubjectFinalPracticalExamChecked() {
		return subjectFinalPracticalExamChecked;
	}

	public void setSubjectFinalPracticalExamChecked(
			String subjectFinalPracticalExamChecked) {
		this.subjectFinalPracticalExamChecked = subjectFinalPracticalExamChecked;
	}

	public String getSubjectFinalInternalExamChecked() {
		return subjectFinalInternalExamChecked;
	}

	public void setSubjectFinalInternalExamChecked(
			String subjectFinalInternalExamChecked) {
		this.subjectFinalInternalExamChecked = subjectFinalInternalExamChecked;
	}

	public String getSubjectFinalAttendanceChecked() {
		return subjectFinalAttendanceChecked;
	}

	public void setSubjectFinalAttendanceChecked(
			String subjectFinalAttendanceChecked) {
		this.subjectFinalAttendanceChecked = subjectFinalAttendanceChecked;
	}

	public String getValuatedSubjectFinal() {
		return valuatedSubjectFinal;
	}

	public void setValuatedSubjectFinal(String valuatedSubjectFinal) {
		this.valuatedSubjectFinal = valuatedSubjectFinal;
	}

	public String getMinimumSubjectFinal() {
		return minimumSubjectFinal;
	}

	public void setMinimumSubjectFinal(String minimumSubjectFinal) {
		this.minimumSubjectFinal = minimumSubjectFinal;
	}

	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setSelectedProgramType(String selectedProgramType) {
		this.selectedProgramType = selectedProgramType;
	}

	public String getSelectedProgramType() {
		return selectedProgramType;
	}

	public void setSelectedCourse(String[] selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public String[] getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectSchemeType(String selectSchemeType) {
		this.selectSchemeType = selectSchemeType;
	}

	public String getSelectSchemeType() {
		return selectSchemeType;
	}

	public void setAcademicYearName(String academicYearName) {
		this.academicYearName = academicYearName;
	}

	public String getAcademicYearName() {
		return academicYearName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setListCourseName(List<ExamCourseUtilTO> listCourseName) {
		this.listCourseName = listCourseName;
	}

	public List<ExamCourseUtilTO> getListCourseName() {
		return listCourseName;
	}

	public void setAttendanceTypeList(
			HashMap<Integer, String> attendanceTypeList) {
		this.attendanceTypeList = attendanceTypeList;
	}

	public HashMap<Integer, String> getAttendanceTypeList() {
		return attendanceTypeList;
	}

	public void setSelectedAttendanceType(String selectedAttendanceType) {
		this.selectedAttendanceType = selectedAttendanceType;
	}

	public String getSelectedAttendanceType() {
		return selectedAttendanceType;
	}

	public void setListCourses(ArrayList<Integer> listCourses) {
		this.listCourses = listCourses;
	}

	public ArrayList<Integer> getListCourses() {
		return listCourses;
	}

	public void setSubInternalList(
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalList) {
		this.subInternalList = subInternalList;
	}

	public List<ExamSubjectRuleSettingsSubInternalTO> getSubInternalList() {
		return subInternalList;
	}

	public void setAssignmentList(
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentList) {
		this.assignmentList = assignmentList;
	}

	public List<ExamSubjectRuleSettingsAssignmentTO> getAssignmentList() {
		return assignmentList;
	}

	public void setMultipleAnswerScriptList(
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptList) {
		this.multipleAnswerScriptList = multipleAnswerScriptList;
	}

	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> getMultipleAnswerScriptList() {
		return multipleAnswerScriptList;
	}

	@SuppressWarnings("unchecked")
	public void resetSessionValues(HttpSession session) {

		if (session != null) {

			Enumeration en = session.getAttributeNames();
			while (en.hasMoreElements()) {

				String name = (String) en.nextElement();

				if (name.equals("TheoryInternal") || name.equals("count")
						|| name.equals("TheoryESE")
						|| name.equals("PracticalInternal")
						|| name.equals("PracticalESE")
						|| name.equals("SubjectRule") || name.equals("edit")
						|| name.equals("listCourses")
						|| name.equals("subjectRuleIdList")
						|| name.equals("reactivationTO")
						|| name.equals("duplecateTO") || name.equals("editTO")) {

					session.removeAttribute(name);
				}

			}

		}

	}

	public void setMaximumSubjectFinal(String maximumSubjectFinal) {
		this.maximumSubjectFinal = maximumSubjectFinal;
	}

	public String getMaximumSubjectFinal() {
		return maximumSubjectFinal;
	}

	public void setSubInternalListPractical(
			List<ExamSubjectRuleSettingsSubInternalTO> subInternalListPractical) {
		this.subInternalListPractical = subInternalListPractical;
	}

	public List<ExamSubjectRuleSettingsSubInternalTO> getSubInternalListPractical() {
		return subInternalListPractical;
	}

	public void setAssignmentListPractical(
			List<ExamSubjectRuleSettingsAssignmentTO> assignmentListPractical) {
		this.assignmentListPractical = assignmentListPractical;
	}

	public List<ExamSubjectRuleSettingsAssignmentTO> getAssignmentListPractical() {
		return assignmentListPractical;
	}

	public void setMultipleAnswerScriptListPractical(
			List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> multipleAnswerScriptListPractical) {
		this.multipleAnswerScriptListPractical = multipleAnswerScriptListPractical;
	}

	public List<ExamSubjectRuleSettingsMultipleAnswerScriptTO> getMultipleAnswerScriptListPractical() {
		return multipleAnswerScriptListPractical;
	}

	public void setSchemeTypeList(List<Integer> schemeTypeList) {
		this.schemeTypeList = schemeTypeList;
	}

	public List<Integer> getSchemeTypeList() {
		return schemeTypeList;
	}

	public void setMulEvalTOList(
			List<ExamSubjectRuleSettingsMultipleEvaluatorTO> mulEvalTOList) {
		this.mulEvalTOList = mulEvalTOList;
	}

	public List<ExamSubjectRuleSettingsMultipleEvaluatorTO> getMulEvalTOList() {
		return mulEvalTOList;
	}

	public void setMulEvalTOListPractical(
			List<ExamSubjectRuleSettingsMultipleEvaluatorTO> mulEvalTOListPractical) {
		this.mulEvalTOListPractical = mulEvalTOListPractical;
	}

	public List<ExamSubjectRuleSettingsMultipleEvaluatorTO> getMulEvalTOListPractical() {
		return mulEvalTOListPractical;
	}

	public void setSubTO(ExamSubjectRuleSettingsSubInternalTO subTO) {
		this.subTO = subTO;
	}

	public ExamSubjectRuleSettingsSubInternalTO getSubTO() {
		return subTO;
	}

	public void setSubTOPractical(
			ExamSubjectRuleSettingsSubInternalTO subTOPractical) {
		this.subTOPractical = subTOPractical;
	}

	public ExamSubjectRuleSettingsSubInternalTO getSubTOPractical() {
		return subTOPractical;
	}

	public void setEdittedSubRuleList(
			List<ExamSubjectRuleSettingsEditTO> edittedSubRuleList) {
		this.edittedSubRuleList = edittedSubRuleList;
	}

	public List<ExamSubjectRuleSettingsEditTO> getEdittedSubRuleList() {
		return edittedSubRuleList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setAttTO(ExamSubjectRuleSettingsAttendanceTO attTO) {
		this.attTO = attTO;
	}

	public ExamSubjectRuleSettingsAttendanceTO getAttTO() {
		return attTO;
	}

	public void setPracticalTO(ExamSubjectRuleSettingsAttendanceTO practicalTO) {
		this.practicalTO = practicalTO;
	}

	public ExamSubjectRuleSettingsAttendanceTO getPracticalTO() {
		return practicalTO;
	}

	public void setTheoryIntTO(
			ExamSubjectRuleSettingsTheoryInternalTO theoryIntTO) {
		this.theoryIntTO = theoryIntTO;
	}

	public ExamSubjectRuleSettingsTheoryInternalTO getTheoryIntTO() {
		return theoryIntTO;
	}

	public void setTheoryESETO(ExamSubjectRuleSettingsTheoryESETO theoryESETO) {
		this.theoryESETO = theoryESETO;
	}

	public ExamSubjectRuleSettingsTheoryESETO getTheoryESETO() {
		return theoryESETO;
	}

	public void setPracticalInternalTO(
			ExamSubjectRuleSettingsPracticalInternalTO practicalInternalTO) {
		this.practicalInternalTO = practicalInternalTO;
	}

	public ExamSubjectRuleSettingsPracticalInternalTO getPracticalInternalTO() {
		return practicalInternalTO;
	}

	public void setPracticalESETO(
			ExamSubjectRuleSettingsPracticalESETO practicalESETO) {
		this.practicalESETO = practicalESETO;
	}

	public ExamSubjectRuleSettingsPracticalESETO getPracticalESETO() {
		return practicalESETO;
	}

	public void setBack(String back) {
		this.back = back;
	}

	public String getBack() {
		return back;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setCopyAcademicYear(String copyAcademicYear) {
		this.copyAcademicYear = copyAcademicYear;
	}

	public String getCopyAcademicYear() {
		return copyAcademicYear;
	}

	public void setMapCourse(Map<Integer, String> mapCourse) {
		this.mapCourse = mapCourse;
	}

	public Map<Integer, String> getMapCourse() {
		return mapCourse;
	}

	public void setCheckedActiveDummy(String checkedActiveDummy) {
		this.checkedActiveDummy = checkedActiveDummy;
	}

	public String getCheckedActiveDummy() {
		return checkedActiveDummy;
	}

	public void setSubjectFinalTO(ExamSubjectRuleSettingsTO subjectFinalTO) {
		this.subjectFinalTO = subjectFinalTO;
	}

	public ExamSubjectRuleSettingsTO getSubjectFinalTO() {
		return subjectFinalTO;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	
}
