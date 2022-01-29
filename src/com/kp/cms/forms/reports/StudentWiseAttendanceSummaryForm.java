package com.kp.cms.forms.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admission.SessionAttendnceToAm;
import com.kp.cms.to.admission.SessionAttendnceToPm;
import com.kp.cms.to.admission.StudentRemarksTO;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamDefinitionTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.utilities.CommonUtil;

public class StudentWiseAttendanceSummaryForm extends BaseActionForm {
	private String semister;
	private String academicYear;
	private String startDate;
	private String endDate;
	private String courseName;
	private String[] attendanceType;
	private String startRegisterNo;
	private String endRegisterNo;
	private String startRollNo;
	private String endRollNo;
	private String method;
	private String studentName;
	private List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList ;
	private List<StudentTO> studentTOList ;
	private Student student;
	private String attendanceID;
	private String studentID;
	private String classesAbsent;
	private List<PeriodTO> periodList;
	private String activityName;
	private String subjectAttendance;
	private Map<Integer, String> classMap = new HashMap<Integer, String>();
	private String[] classesName;
	private List<StudentRemarksTO> staffRemarkList;
	private String principalRemarks;
	private int princiCommnentId;
	private String leaveApproved;
	private boolean isMarkPresent;
	private String totalPercentage;
	private String totalConducted;
	private String totalPresent;
	private String totalAbscent;
	private String attendanceTypeName;
	List<StudentAttendanceTO> attList;
	List<String> periodNameList;
	Map<String,Integer> subMap;
	private int totalCoLeave;
	private int abscent;
	private int total;
	private boolean markPublished;
	private List<ExamDefinitionBO> examDefinitionBO;
	private List<SubjectTO> subjectListWithMarks;
	private String classesId;
	private String className;
	
	//raghu
	private List<SessionAttendnceToAm> amList;
	private List<SessionAttendnceToPm> pmList;
	private String am;
	private String pm;
	private String totamattper;
	private String totpmattper;
	private String totamattabs;
	private String totpmattabs;
	private String totamattpre;
	private String totpmattpre;
	private String totamattcon;
	private String totpmattcon;
	private String totalWorkingDays;
	private String totalPresentDays;
	private String totalAbsentDays;
	private String totalCurrPercentage;
	

	

	//raghu
	private List<StudentAttendanceTO> attDateList;
	private String[] teacherIds;
	private Map<Integer,String> teachersMap;
	
	private boolean isFaculty;
	
	public String getTotamattpre() {
		return totamattpre;
	}

	public void setTotamattpre(String totamattpre) {
		this.totamattpre = totamattpre;
	}

	public String getTotpmattpre() {
		return totpmattpre;
	}

	public void setTotpmattpre(String totpmattpre) {
		this.totpmattpre = totpmattpre;
	}

	public String getTotamattcon() {
		return totamattcon;
	}

	public void setTotamattcon(String totamattcon) {
		this.totamattcon = totamattcon;
	}

	public String getTotpmattcon() {
		return totpmattcon;
	}

	public void setTotpmattcon(String totpmattcon) {
		this.totpmattcon = totpmattcon;
	}

	public List<SessionAttendnceToAm> getAmList() {
		return amList;
	}

	public void setAmList(List<SessionAttendnceToAm> amList) {
		this.amList = amList;
	}

	public List<SessionAttendnceToPm> getPmList() {
		return pmList;
	}

	public void setPmList(List<SessionAttendnceToPm> pmList) {
		this.pmList = pmList;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getPm() {
		return pm;
	}

	public void setPm(String pm) {
		this.pm = pm;
	}

	public String getTotamattper() {
		return totamattper;
	}

	public void setTotamattper(String totamattper) {
		this.totamattper = totamattper;
	}

	public String getTotpmattper() {
		return totpmattper;
	}

	public void setTotpmattper(String totpmattper) {
		this.totpmattper = totpmattper;
	}

	public String getTotamattabs() {
		return totamattabs;
	}

	public void setTotamattabs(String totamattabs) {
		this.totamattabs = totamattabs;
	}

	public String getTotpmattabs() {
		return totpmattabs;
	}

	public void setTotpmattabs(String totpmattabs) {
		this.totpmattabs = totpmattabs;
	}

	public String getSemister() {
		return semister;
	}

	public void setSemister(String semister) {
		this.semister = semister;
	}

	public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	public void resetFields() {
		this.subjectwiseAttendanceTOList = null;
		this.student = null;
		this.semister = null;
		this.academicYear = null;
		this.startDate = null;
		this.endDate = CommonUtil.getTodayDate();
		this.courseName = null;
		this.startRegisterNo= null;
		this.endRegisterNo= null;
		this.startRollNo= null;
		this.endRollNo= null;
		this.studentName = null;
		super.setProgramTypeId(null);
		super.setProgramId(null);
		super.setCourseId(null);
		this.classMap = null;
		this.classesName = null;
		this.attendanceType = null;
		this.totalConducted=null;
		this.totalPresent=null;
		this.markPublished = false;
		this.classesId=null;
		this.className=null;
		this.isFaculty=false;
		this.teacherIds=null;
	}

	public String[] getAttendanceType() {
		return attendanceType;
	}

	public void setAttendanceType(String[] attendanceType) {
		this.attendanceType = attendanceType;
	}

	public List<StudentWiseSubjectSummaryTO> getSubjectwiseAttendanceTOList() {
		return subjectwiseAttendanceTOList;
	}

	public void setSubjectwiseAttendanceTOList(
			List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList) {
		this.subjectwiseAttendanceTOList = subjectwiseAttendanceTOList;
	}

	public String getStartRegisterNo() {
		return startRegisterNo;
	}

	public void setStartRegisterNo(String startRegisterNo) {
		this.startRegisterNo = startRegisterNo;
	}

	public String getEndRegisterNo() {
		return endRegisterNo;
	}

	public void setEndRegisterNo(String endRegisterNo) {
		this.endRegisterNo = endRegisterNo;
	}

	public String getStartRollNo() {
		return startRollNo;
	}

	public void setStartRollNo(String startRollNo) {
		this.startRollNo = startRollNo;
	}

	public String getEndRollNo() {
		return endRollNo;
	}

	public void setEndRollNo(String endRollNo) {
		this.endRollNo = endRollNo;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public List<StudentTO> getStudentTOList() {
		return studentTOList;
	}

	public void setStudentTOList(List<StudentTO> studentTOList) {
		this.studentTOList = studentTOList;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getAttendanceID() {
		return attendanceID;
	}

	public String getStudentID() {
		return studentID;
	}

	public String getClassesAbsent() {
		return classesAbsent;
	}

	public void setAttendanceID(String attendanceID) {
		this.attendanceID = attendanceID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public void setClassesAbsent(String classesAbsent) {
		this.classesAbsent = classesAbsent;
	}

	public List<PeriodTO> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<PeriodTO> periodList) {
		this.periodList = periodList;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getSubjectAttendance() {
		return subjectAttendance;
	}

	public void setSubjectAttendance(String subjectAttendance) {
		this.subjectAttendance = subjectAttendance;
	}

	public Map<Integer, String> getClassMap() {
		return classMap;
	}

	public void setClassMap(Map<Integer, String> classMap) {
		this.classMap = classMap;
	}

	public String[] getClassesName() {
		return classesName;
	}

	public void setClassesName(String[] classesName) {
		this.classesName = classesName;
	}

	public List<StudentRemarksTO> getStaffRemarkList() {
		return staffRemarkList;
	}

	public void setStaffRemarkList(List<StudentRemarksTO> staffRemarkList) {
		this.staffRemarkList = staffRemarkList;
	}

	public String getPrincipalRemarks() {
		return principalRemarks;
	}

	public void setPrincipalRemarks(String principalRemarks) {
		this.principalRemarks = principalRemarks;
	}

	public int getPrinciCommnentId() {
		return princiCommnentId;
	}

	public void setPrinciCommnentId(int princiCommnentId) {
		this.princiCommnentId = princiCommnentId;
	}

	public String getLeaveApproved() {
		return leaveApproved;
	}

	public void setLeaveApproved(String leaveApproved) {
		this.leaveApproved = leaveApproved;
	}

	public boolean isMarkPresent() {
		return isMarkPresent;
	}

	public void setMarkPresent(boolean isMarkPresent) {
		this.isMarkPresent = isMarkPresent;
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

	public String getAttendanceTypeName() {
		return attendanceTypeName;
	}

	public void setAttendanceTypeName(String attendanceTypeName) {
		this.attendanceTypeName = attendanceTypeName;
	}

	public List<StudentAttendanceTO> getAttList() {
		return attList;
	}

	public void setAttList(List<StudentAttendanceTO> attList) {
		this.attList = attList;
	}

	public List<String> getPeriodNameList() {
		return periodNameList;
	}

	public void setPeriodNameList(List<String> periodNameList) {
		this.periodNameList = periodNameList;
	}

	public Map<String, Integer> getSubMap() {
		return subMap;
	}

	public void setSubMap(Map<String, Integer> subMap) {
		this.subMap = subMap;
	}

	public int getTotalCoLeave() {
		return totalCoLeave;
	}

	public void setTotalCoLeave(int totalCoLeave) {
		this.totalCoLeave = totalCoLeave;
	}

	public int getAbscent() {
		return abscent;
	}

	public void setAbscent(int abscent) {
		this.abscent = abscent;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public boolean isMarkPublished() {
		return markPublished;
	}

	public void setMarkPublished(boolean markPublished) {
		this.markPublished = markPublished;
	}

	public List<ExamDefinitionBO> getExamDefinitionBO() {
		return examDefinitionBO;
	}

	public void setExamDefinitionBO(List<ExamDefinitionBO> examDefinitionBO) {
		this.examDefinitionBO = examDefinitionBO;
	}

	public List<SubjectTO> getSubjectListWithMarks() {
		return subjectListWithMarks;
	}

	public void setSubjectListWithMarks(List<SubjectTO> subjectListWithMarks) {
		this.subjectListWithMarks = subjectListWithMarks;
	}

	public String getClassesId() {
		return classesId;
	}

	public void setClassesId(String classesId) {
		this.classesId = classesId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<StudentAttendanceTO> getAttDateList() {
		return attDateList;
	}

	public void setAttDateList(List<StudentAttendanceTO> attDateList) {
		this.attDateList = attDateList;
	}

	public String[] getTeacherIds() {
		return teacherIds;
	}

	public void setTeacherIds(String[] teacherIds) {
		this.teacherIds = teacherIds;
	}

	public boolean getIsFaculty() {
		return isFaculty;
	}

	public void setIsFaculty(boolean isFaculty) {
		this.isFaculty = isFaculty;
	}

	public Map<Integer, String> getTeachersMap() {
		return teachersMap;
	}

	public void setTeachersMap(Map<Integer, String> teachersMap) {
		this.teachersMap = teachersMap;
	}
	public String getTotalWorkingDays() {
		return totalWorkingDays;
	}

	public void setTotalWorkingDays(String totalWorkingDays) {
		this.totalWorkingDays = totalWorkingDays;
	}

	public String getTotalPresentDays() {
		return totalPresentDays;
	}

	public void setTotalPresentDays(String totalPresentDays) {
		this.totalPresentDays = totalPresentDays;
	}

	public String getTotalAbsentDays() {
		return totalAbsentDays;
	}

	public void setTotalAbsentDays(String totalAbsentDays) {
		this.totalAbsentDays = totalAbsentDays;
	}
	public String getTotalCurrPercentage() {
		return totalCurrPercentage;
	}

	public void setTotalCurrPercentage(String totalCurrPercentage) {
		this.totalCurrPercentage = totalCurrPercentage;
	}
	
}
