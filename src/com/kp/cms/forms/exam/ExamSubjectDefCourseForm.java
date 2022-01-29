package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ExamSubCoursewiseAttendanceMarksTO;
import com.kp.cms.to.exam.ExamSubCoursewiseGradeDefnTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseDisplayTO;
import com.kp.cms.to.exam.ExamSubDefinitionCourseWiseTO;
import com.kp.cms.to.exam.KeyValueTO;

public class ExamSubjectDefCourseForm extends BaseActionForm {

	private String id;
	private String academicYear;
	private String academicYear_value;
	private String isTheoryOrPractical;
	private String courseName;
	private String schemeName;
	private String course;
	private String scheme;
	private String isinitialise;
	private String universitySubjectCode;
	private String subId;
	private String subjectCode;
	private String subjectName;
	private String subjectOrder;
	private String subjectSection;
	private String theoryHours;
	private String theoryCredit;
	private String practHours;
	private String practCredit;
	private String subjectType;
	private String maxMarks;
	private String minMarks;
	private String attendanceMarks;
	private String doNotAdd;
	private String doNotConsider;
	private String showInternalMarks;
	private String showOnlyGrade;
	private int orgSubid = 0;
	private String gradePoint;
	private String resultClass;
	private String interpretation;
	private String grade;
	private String endPercentage;
	private String startPercentage;
	private String orgStartPercentage = "";
	private String orgEndPercentage = "";
	private String orgGrade = "";
	private String orgInterpretation = "";
	private String orgResultClass = "";
	private String orgGradePoint = "";
	private String marks;
	private String orgMarks = "";
	private Map<String, String> schemeMapList;
	private Map<Integer, String> courseNameList;
	private List<ExamSubDefinitionCourseWiseDisplayTO> listCheckBoxTo;
	private List<ExamSubCoursewiseGradeDefnTO> listGradeDefinition;
	private ArrayList<ExamSubCoursewiseAttendanceMarksTO> listAttendanceDetails;
	private List<KeyValueTO> listAccYear;
	private List<KeyValueTO> listSubjectSection;
	private List<ExamSubDefinitionCourseWiseTO> listSubjects;
	private Map<Integer, String> programList;
	private List<ProgramTypeTO> programTypeList;
	private String dupsubjectType;
	private String dupmaxMarks;
	private String dupminMarks;
	private String dupattendanceMarks;
	private String dupdoNotAdd;
	private String dupdoNotConsider;
	private String dupshowInternalMarks;
	private String dupshowOnlyGrade;
	private int countcheck;
	private String schemeType;
	private List<Object[]> allDatas;
    private String courseIds[];
    private String toYear;

	public String getGradePoint() {
		return gradePoint;
	}

	public void setGradePoint(String gradePoint) {
		this.gradePoint = gradePoint;
	}

	public String getResultClass() {
		return resultClass;
	}

	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

	public String getInterpretation() {
		return interpretation;
	}

	public void setInterpretation(String interpretation) {
		this.interpretation = interpretation;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getEndPercentage() {
		return endPercentage;
	}

	public void setEndPercentage(String endPercentage) {
		this.endPercentage = endPercentage;
	}

	public String getStartPercentage() {
		return startPercentage;
	}

	public void setStartPercentage(String startPercentage) {
		this.startPercentage = startPercentage;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getAcademicYear_value() {
		return academicYear_value;
	}

	public void setAcademicYear_value(String academicYear_value) {
		this.academicYear_value = academicYear_value;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getSchemeName() {
		return schemeName;
	}

	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getUniversitySubjectCode() {
		return universitySubjectCode;
	}

	public void setUniversitySubjectCode(String universitySubjectCode) {
		this.universitySubjectCode = universitySubjectCode;
	}

	public List<KeyValueTO> getListAccYear() {
		return listAccYear;
	}

	public void setListAccYear(List<KeyValueTO> listAccYear) {
		this.listAccYear = listAccYear;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void setIsinitialise(String isinitialise) {
		this.isinitialise = isinitialise;
	}

	public String getIsinitialise() {
		return isinitialise;
	}

	public void setListSubjects(List<ExamSubDefinitionCourseWiseTO> listSubjects) {
		this.listSubjects = listSubjects;
	}

	public List<ExamSubDefinitionCourseWiseTO> getListSubjects() {
		return listSubjects;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectSection(String subjectSection) {
		this.subjectSection = subjectSection;
	}

	public String getSubjectSection() {
		return subjectSection;
	}

	public void setTheoryHours(String theoryHours) {
		this.theoryHours = theoryHours;
	}

	public String getTheoryHours() {
		return theoryHours;
	}

	public void setTheoryCredit(String theoryCredit) {
		this.theoryCredit = theoryCredit;
	}

	public String getTheoryCredit() {
		return theoryCredit;
	}

	public void setPractHours(String practHours) {
		this.practHours = practHours;
	}

	public String getPractHours() {
		return practHours;
	}

	public void setPractCredit(String practCredit) {
		this.practCredit = practCredit;
	}

	public String getPractCredit() {
		return practCredit;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setMaxMarks(String maxMarks) {
		this.maxMarks = maxMarks;
	}

	public String getMaxMarks() {
		return maxMarks;
	}

	public void setMinMarks(String minMarks) {
		this.minMarks = minMarks;
	}

	public String getMinMarks() {
		return minMarks;
	}

	public void setAttendanceMarks(String attendanceMarks) {
		this.attendanceMarks = attendanceMarks;
	}

	public String getAttendanceMarks() {
		return attendanceMarks;
	}

	public void setDoNotAdd(String doNotAdd) {
		this.doNotAdd = doNotAdd;
	}

	public String getDoNotAdd() {
		return doNotAdd;
	}

	public void setDoNotConsider(String doNotConsider) {
		this.doNotConsider = doNotConsider;
	}

	public String getDoNotConsider() {
		return doNotConsider;
	}

	public void setShowInternalMarks(String showInternalMarks) {
		this.showInternalMarks = showInternalMarks;
	}

	public String getShowInternalMarks() {
		return showInternalMarks;
	}

	public void setShowOnlyGrade(String showOnlyGrade) {
		this.showOnlyGrade = showOnlyGrade;
	}

	public String getShowOnlyGrade() {
		return showOnlyGrade;
	}

	public void setListSubjectSection(List<KeyValueTO> listSubjectSection) {
		this.listSubjectSection = listSubjectSection;
	}

	public List<KeyValueTO> getListSubjectSection() {
		return listSubjectSection;
	}

	public void setListGradeDefinition(
			List<ExamSubCoursewiseGradeDefnTO> listGradeDefinition) {
		this.listGradeDefinition = listGradeDefinition;
	}

	public List<ExamSubCoursewiseGradeDefnTO> getListGradeDefinition() {
		return listGradeDefinition;
	}

	public void clearPage() {
		this.marks = "";
		this.startPercentage = "";
		this.endPercentage = "";
		this.grade = "";
		this.interpretation = "";
		this.gradePoint = "";
		this.resultClass = "";

	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setOrgStartPercentage(String orgStartPercentage) {
		this.orgStartPercentage = orgStartPercentage;
	}

	public String getOrgStartPercentage() {
		return orgStartPercentage;
	}

	public void setOrgEndPercentage(String orgEndPercentage) {
		this.orgEndPercentage = orgEndPercentage;
	}

	public String getOrgEndPercentage() {
		return orgEndPercentage;
	}

	public void setOrgGrade(String orgGrade) {
		this.orgGrade = orgGrade;
	}

	public String getOrgGrade() {
		return orgGrade;
	}

	public void setOrgInterpretation(String orgInterpretation) {
		this.orgInterpretation = orgInterpretation;
	}

	public String getOrgInterpretation() {
		return orgInterpretation;
	}

	public void setOrgResultClass(String orgResultClass) {
		this.orgResultClass = orgResultClass;
	}

	public String getOrgResultClass() {
		return orgResultClass;
	}

	public void setOrgGradePoint(String orgGradePoint) {
		this.orgGradePoint = orgGradePoint;
	}

	public String getOrgGradePoint() {
		return orgGradePoint;
	}

	public void setOrgSubid(int orgSubid) {
		this.orgSubid = orgSubid;
	}

	public int getOrgSubid() {
		return orgSubid;
	}

	public void setListAttendanceDetails(
			ArrayList<ExamSubCoursewiseAttendanceMarksTO> listAttendanceDetails) {
		this.listAttendanceDetails = listAttendanceDetails;
	}

	public ArrayList<ExamSubCoursewiseAttendanceMarksTO> getListAttendanceDetails() {
		return listAttendanceDetails;
	}

	public void setMarks(String marks) {
		this.marks = marks;
	}

	public String getMarks() {
		return marks;
	}

	public void setOrgMarks(String orgMarks) {
		this.orgMarks = orgMarks;
	}

	public String getOrgMarks() {
		return orgMarks;
	}

	public void setSubjectOrder(String subjectOrder) {
		this.subjectOrder = subjectOrder;
	}

	public String getSubjectOrder() {
		return subjectOrder;
	}

	public void setListCheckBoxTo(
			List<ExamSubDefinitionCourseWiseDisplayTO> listCheckBoxTo) {
		this.listCheckBoxTo = listCheckBoxTo;
	}

	public List<ExamSubDefinitionCourseWiseDisplayTO> getListCheckBoxTo() {
		return listCheckBoxTo;
	}

	public void setIsTheoryOrPractical(String isTheoryOrPractical) {
		this.isTheoryOrPractical = isTheoryOrPractical;
	}

	public String getIsTheoryOrPractical() {
		return isTheoryOrPractical;
	}

	public void setSchemeMapList(Map<String, String> schemeMapList) {
		this.schemeMapList = schemeMapList;
	}

	public Map<String, String> getSchemeMapList() {
		return schemeMapList;
	}

	public void setCourseNameList(Map<Integer, String> courseNameList) {
		this.courseNameList = courseNameList;
	}

	public Map<Integer, String> getCourseNameList() {
		return courseNameList;
	}
	
	public void resetFields(){
		this.setDoNotAdd(null);
		this.setDoNotConsider(null);
		this.setShowOnlyGrade(null);
		this.setShowInternalMarks(null);
		this.setSubjectType(null);
		this.setMaxMarks(null);
		this.setMinMarks(null);
		this.setAttendanceMarks(null);
		this.setSubjectCode(null);
//		this.setSubId(null);
		this.setSubjectSection(null);
		this.setSubjectOrder(null);
		this.setUniversitySubjectCode(null);
		this.setTheoryCredit(null);
		this.setTheoryHours(null);
		this.setPractCredit(null);
		this.setPractHours(null);
		this.setId(null);
		this.setDupsubjectType(null);
		this.setDupmaxMarks(null);
		this.setDupminMarks(null);
		this.setDupattendanceMarks(null);
		this.setDupdoNotAdd(null);
		this.setDupdoNotConsider(null);
		this.setDupshowInternalMarks(null);
		this.setDupshowOnlyGrade(null);
		this.setSchemeType(null);
		this.setCountcheck(0);
		this.allDatas=null;
		this.listSubjects=null;
	}

	public Map<Integer, String> getProgramList() {
		return programList;
	}

	public void setProgramList(Map<Integer, String> programList) {
		this.programList = programList;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public String getDupsubjectType() {
		return dupsubjectType;
	}

	public void setDupsubjectType(String dupsubjectType) {
		this.dupsubjectType = dupsubjectType;
	}

	public String getDupmaxMarks() {
		return dupmaxMarks;
	}

	public void setDupmaxMarks(String dupmaxMarks) {
		this.dupmaxMarks = dupmaxMarks;
	}

	public String getDupminMarks() {
		return dupminMarks;
	}

	public void setDupminMarks(String dupminMarks) {
		this.dupminMarks = dupminMarks;
	}

	public String getDupattendanceMarks() {
		return dupattendanceMarks;
	}

	public void setDupattendanceMarks(String dupattendanceMarks) {
		this.dupattendanceMarks = dupattendanceMarks;
	}

	public String getDupdoNotAdd() {
		return dupdoNotAdd;
	}

	public void setDupdoNotAdd(String dupdoNotAdd) {
		this.dupdoNotAdd = dupdoNotAdd;
	}

	public String getDupdoNotConsider() {
		return dupdoNotConsider;
	}

	public void setDupdoNotConsider(String dupdoNotConsider) {
		this.dupdoNotConsider = dupdoNotConsider;
	}

	public String getDupshowInternalMarks() {
		return dupshowInternalMarks;
	}

	public void setDupshowInternalMarks(String dupshowInternalMarks) {
		this.dupshowInternalMarks = dupshowInternalMarks;
	}

	public String getDupshowOnlyGrade() {
		return dupshowOnlyGrade;
	}

	public void setDupshowOnlyGrade(String dupshowOnlyGrade) {
		this.dupshowOnlyGrade = dupshowOnlyGrade;
	}

	public int getCountcheck() {
		return countcheck;
	}

	public void setCountcheck(int countcheck) {
		this.countcheck = countcheck;
	}

	public String getSchemeType() {
		return schemeType;
	}

	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}

	public List<Object[]> getAllDatas() {
		return allDatas;
	}

	public void setAllDatas(List<Object[]> allDatas) {
		this.allDatas = allDatas;
	}

	public String[] getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String[] courseIds) {
		this.courseIds = courseIds;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}
	
	
}
