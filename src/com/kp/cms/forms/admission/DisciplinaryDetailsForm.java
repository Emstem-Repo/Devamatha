package com.kp.cms.forms.admission;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.handlers.admin.CandidateMarkTO;
import com.kp.cms.to.admin.ApplicantMarkDetailsTO;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.admission.CourseDetailsTO;
import com.kp.cms.to.admission.DisciplinaryDetailsTo;
import com.kp.cms.to.admission.FeesTO;
import com.kp.cms.to.admission.RemarcksTO;
import com.kp.cms.to.admission.SessionAttendnceToAm;
import com.kp.cms.to.admission.SessionAttendnceToPm;
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.exam.HallTicketTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.hostel.FineEntryTo;
import com.kp.cms.to.hostel.HlDisciplinaryDetailsTO;
import com.kp.cms.to.hostel.HlStudentAttendanceTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;

@SuppressWarnings("serial")
public class DisciplinaryDetailsForm extends BaseActionForm{
	
	private int remarkid;
	private String rollRegNo;
	private String oldRegNo;
	private int isAddRemarks;
	private String tempName;
	private String tempFirstName;
	private String tempRegRollNo;
	private String tempApplicationNo;
	private List<StudentTO> studentTOList;
	
	private String studentId;
	private String remarks;
	private DisciplinaryDetailsTo objto;
	private List<CourseDetailsTO> listCourseDetails;
	private List<CourseDetailsTO> listActivityDetails;
	

	private List<FeesTO> listFees;
	private List<RemarcksTO> listRemarcks;
	private List<EdnQualificationTO> eduList;
	private CandidateMarkTO detailMark;
	private List<ApplicantMarkDetailsTO> semesterList;
	private String isLanguageWiseMarks;
	private String recommendedBy;
	private float totalAggrPer;
	private float totalPresent;
	private float totalConducted;
	private String studentID;
	private String classesAbsent;
	private String subjectAttendance;
	private List<PeriodTO> periodList;
	private String activityName;
	private String detentionDate;
	private String detentionReason;
	private String discontinuedDate;
	private String discontinuedReason;
	private String rejoinDate;
	private String rejoinReason;
	private String previousSemester;
	private String previous;
	private Integer regularMarkFlag;
	private Integer supMarkFlag;
	private List<MarksCardTO> listSemDetails;
	private List<MarksCardTO> supSemDetails;
	private int examId;


	private int termNo;
	private int examIDForMCard;
	private int marksCardClassId;
	private int semesterYearNo;
	private MarksCardTO marksCardTo;
	private MarksCardTO marksCardTo1;
	private MarksCardTO marksCardToSup;
	private MarksCardTO marksCardSemExamList;
	private MarksCardTO supMCSemExamList;
	private MarksCardTO cjcMarksCardTo;
	

	private ExamStudentPreviousClassTo previousSem;
	private int  supHallTicketexamID;
	private int supMCexamID;
	private int supMCsemesterYearNo;
	private int supMCClassId;
	private Boolean isCjc;
	
	//
	private List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList ;
	private Student student;
	private String attendanceID;
	private boolean isMarkPresent;
	private String totalPercentage;
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
	private int supExamId;
	private int classIdCjc;
	private int examIdCjc;
	private String cocurricularLeave;
	private String status;
	private String lastExamName;
	private String examMonthYear;
	private String checkFinalYear;
	private String studentStatusInHostel;
	private String checkHostelAdmission;
	private String hostelName;
	private String block;
	private String unit;
	private String roomNo;
	private String bedNo;
	private List<HostelTO> hostelTOList;
	private List<HlStudentAttendanceTo> hlStudentAttendanceToList;
	private List<HlDisciplinaryDetailsTO> hlDisciplinaryDetailsTOList;
	private List<FineEntryTo> fineEntryToList;
	private String displayPercentage;
	
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

	public List<FineEntryTo> getFineEntryToList() {
		return fineEntryToList;
	}

	public void setFineEntryToList(List<FineEntryTo> fineEntryToList) {
		this.fineEntryToList = fineEntryToList;
	}

	public List<HlDisciplinaryDetailsTO> getHlDisciplinaryDetailsTOList() {
		return hlDisciplinaryDetailsTOList;
	}

	public void setHlDisciplinaryDetailsTOList(
			List<HlDisciplinaryDetailsTO> hlDisciplinaryDetailsTOList) {
		this.hlDisciplinaryDetailsTOList = hlDisciplinaryDetailsTOList;
	}

	public List<HlStudentAttendanceTo> getHlStudentAttendanceToList() {
		return hlStudentAttendanceToList;
	}

	public void setHlStudentAttendanceToList(
			List<HlStudentAttendanceTo> hlStudentAttendanceToList) {
		this.hlStudentAttendanceToList = hlStudentAttendanceToList;
	}

	public List<HostelTO> getHostelTOList() {
		return hostelTOList;
	}

	public void setHostelTOList(List<HostelTO> hostelTOList) {
		this.hostelTOList = hostelTOList;
	}

	public String getHostelName() {
		return hostelName;
	}

	public void setHostelName(String hostelName) {
		this.hostelName = hostelName;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public String getBedNo() {
		return bedNo;
	}

	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}

	public String getCheckHostelAdmission() {
		return checkHostelAdmission;
	}

	public void setCheckHostelAdmission(String checkHostelAdmission) {
		this.checkHostelAdmission = checkHostelAdmission;
	}

	public String getStudentStatusInHostel() {
		return studentStatusInHostel;
	}

	public void setStudentStatusInHostel(String studentStatusInHostel) {
		this.studentStatusInHostel = studentStatusInHostel;
	}

	public String getCheckFinalYear() {
		return checkFinalYear;
	}

	public void setCheckFinalYear(String checkFinalYear) {
		this.checkFinalYear = checkFinalYear;
	}

	public String getLastExamName() {
		return lastExamName;
	}

	public void setLastExamName(String lastExamName) {
		this.lastExamName = lastExamName;
	}

	public String getExamMonthYear() {
		return examMonthYear;
	}

	public void setExamMonthYear(String examMonthYear) {
		this.examMonthYear = examMonthYear;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getClassIdCjc() {
		return classIdCjc;
	}

	public void setClassIdCjc(int classIdCjc) {
		this.classIdCjc = classIdCjc;
	}

	public int getExamIdCjc() {
		return examIdCjc;
	}

	public void setExamIdCjc(int examIdCjc) {
		this.examIdCjc = examIdCjc;
	}

	public MarksCardTO getCjcMarksCardTo() {
		return cjcMarksCardTo;
	}

	public void setCjcMarksCardTo(MarksCardTO cjcMarksCardTo) {
		this.cjcMarksCardTo = cjcMarksCardTo;
	}
	

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	
	
    public String getPrevious() {
		return previous;
	}

	public void setPrevious(String previous) {
		this.previous = previous;
	}

	public String getPreviousSemester() {
		return previousSemester;
	}

	public void setPreviousSemester(String previousSemester) {
		this.previousSemester = previousSemester;
	}

	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	public DisciplinaryDetailsTo getObjto() {
		return objto;
	}
	public void setObjto(DisciplinaryDetailsTo objto) {
		this.objto = objto;
	}
	public List<CourseDetailsTO> getListCourseDetails() {
		return listCourseDetails;
	}
	public void setListCourseDetails(List<CourseDetailsTO> listCourseDetails) {
		this.listCourseDetails = listCourseDetails;
	}
	public List<FeesTO> getListFees() {
		return listFees;
	}
	public void setListFees(List<FeesTO> listFees) {
		this.listFees = listFees;
	}
	public List<RemarcksTO> getListRemarcks() {
		return listRemarcks;
	}
	public void setListRemarcks(List<RemarcksTO> listRemarcks) {
		this.listRemarcks = listRemarcks;
	}

	public void setRollRegNo(String rollRegNo) {
		this.rollRegNo = rollRegNo;
	}

	public String getRollRegNo() {
		return rollRegNo;
	}

	public void setIsAddRemarks(int isAddRemarks) {
		this.isAddRemarks = isAddRemarks;
	}

	public int getIsAddRemarks() {
		return isAddRemarks;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRemarks() {
		return remarks;
	}

	public List<EdnQualificationTO> getEduList() {
		return eduList;
	}

	public void setEduList(List<EdnQualificationTO> eduList) {
		this.eduList = eduList;
	}

	public CandidateMarkTO getDetailMark() {
		return detailMark;
	}

	public void setDetailMark(CandidateMarkTO detailMark) {
		this.detailMark = detailMark;
	}

	public List<ApplicantMarkDetailsTO> getSemesterList() {
		return semesterList;
	}

	public void setSemesterList(List<ApplicantMarkDetailsTO> semesterList) {
		this.semesterList = semesterList;
	}

	public String getIsLanguageWiseMarks() {
		return isLanguageWiseMarks;
	}

	public void setIsLanguageWiseMarks(String isLanguageWiseMarks) {
		this.isLanguageWiseMarks = isLanguageWiseMarks;
	}

	public String getRecommendedBy() {
		return recommendedBy;
	}

	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}

	public float getTotalAggrPer() {
		return totalAggrPer;
	}

	public void setTotalAggrPer(float totalAggrPer) {
		this.totalAggrPer = totalAggrPer;
	}

	public float getTotalPresent() {
		return totalPresent;
	}

	public void setTotalPresent(float totalPresent) {
		this.totalPresent = totalPresent;
	}

	public float getTotalConducted() {
		return totalConducted;
	}

	public void setTotalConducted(float totalConducted) {
		this.totalConducted = totalConducted;
	}

	public String getStudentID() {
		return studentID;
	}

	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getClassesAbsent() {
		return classesAbsent;
	}

	public void setClassesAbsent(String classesAbsent) {
		this.classesAbsent = classesAbsent;
	}

	public String getSubjectAttendance() {
		return subjectAttendance;
	}

	public void setSubjectAttendance(String subjectAttendance) {
		this.subjectAttendance = subjectAttendance;
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

	public String getDetentionDate() {
		return detentionDate;
	}

	public void setDetentionDate(String detentionDate) {
		this.detentionDate = detentionDate;
	}

	public String getDetentionReason() {
		return detentionReason;
	}

	public void setDetentionReason(String detentionReason) {
		this.detentionReason = detentionReason;
	}

	public String getDiscontinuedDate() {
		return discontinuedDate;
	}

	public void setDiscontinuedDate(String discontinuedDate) {
		this.discontinuedDate = discontinuedDate;
	}

	public String getDiscontinuedReason() {
		return discontinuedReason;
	}

	public void setDiscontinuedReason(String discontinuedReason) {
		this.discontinuedReason = discontinuedReason;
	}

	public String getRejoinDate() {
		return rejoinDate;
	}

	public void setRejoinDate(String rejoinDate) {
		this.rejoinDate = rejoinDate;
	}

	public String getRejoinReason() {
		return rejoinReason;
	}

	public void setRejoinReason(String rejoinReason) {
		this.rejoinReason = rejoinReason;
	}

	public int getExamId() {
		return examId;
	}

	public int getTermNo() {
		return termNo;
	}

	public int getExamIDForMCard() {
		return examIDForMCard;
	}

	public int getMarksCardClassId() {
		return marksCardClassId;
	}

	public int getSemesterYearNo() {
		return semesterYearNo;
	}

	public MarksCardTO getMarksCardTo() {
		return marksCardTo;
	}

	public int getSupHallTicketexamID() {
		return supHallTicketexamID;
	}

	public int getSupMCexamID() {
		return supMCexamID;
	}

	public int getSupMCsemesterYearNo() {
		return supMCsemesterYearNo;
	}

	public int getSupMCClassId() {
		return supMCClassId;
	}

	public void setExamId(int examId) {
		this.examId = examId;
	}

	public void setTermNo(int termNo) {
		this.termNo = termNo;
	}

	public void setExamIDForMCard(int examIDForMCard) {
		this.examIDForMCard = examIDForMCard;
	}

	public void setMarksCardClassId(int marksCardClassId) {
		this.marksCardClassId = marksCardClassId;
	}

	public void setSemesterYearNo(int semesterYearNo) {
		this.semesterYearNo = semesterYearNo;
	}

	public void setMarksCardTo(MarksCardTO marksCardTo) {
		this.marksCardTo = marksCardTo;
	}

	public void setSupHallTicketexamID(int supHallTicketexamID) {
		this.supHallTicketexamID = supHallTicketexamID;
	}

	public void setSupMCexamID(int supMCexamID) {
		this.supMCexamID = supMCexamID;
	}

	public void setSupMCsemesterYearNo(int supMCsemesterYearNo) {
		this.supMCsemesterYearNo = supMCsemesterYearNo;
	}

	public void setSupMCClassId(int supMCClassId) {
		this.supMCClassId = supMCClassId;
	}

	public MarksCardTO getMarksCardToSup() {
		return marksCardToSup;
	}

	public void setMarksCardToSup(MarksCardTO marksCardToSup) {
		this.marksCardToSup = marksCardToSup;
	}

	public MarksCardTO getMarksCardSemExamList() {
		return marksCardSemExamList;
	}

	public void setMarksCardSemExamList(MarksCardTO marksCardSemExamList) {
		this.marksCardSemExamList = marksCardSemExamList;
	}

	public List<MarksCardTO> getListSemDetails() {
		return listSemDetails;
	}

	public void setListSemDetails(List<MarksCardTO> listSemDetails) {
		this.listSemDetails = listSemDetails;
	}

	public MarksCardTO getMarksCardTo1() {
		return marksCardTo1;
	}

	public void setMarksCardTo1(MarksCardTO marksCardTo1) {
		this.marksCardTo1 = marksCardTo1;
	}

	public String getTempFirstName() {
		return tempFirstName;
	}

	public void setTempFirstName(String tempFirstName) {
		this.tempFirstName = tempFirstName;
	}

	public String getTempRegRollNo() {
		return tempRegRollNo;
	}

	public void setTempRegRollNo(String tempRegRollNo) {
		this.tempRegRollNo = tempRegRollNo;
	}

	public List<StudentTO> getStudentTOList() {
		return studentTOList;
	}

	public void setStudentTOList(List<StudentTO> studentTOList) {
		this.studentTOList = studentTOList;
	}

	public String getTempApplicationNo() {
		return tempApplicationNo;
	}

	public void setTempApplicationNo(String tempApplicationNo) {
		this.tempApplicationNo = tempApplicationNo;
	}
	public List<CourseDetailsTO> getListActivityDetails() {
		return listActivityDetails;
	}

	public void setListActivityDetails(List<CourseDetailsTO> listActivityDetails) {
		this.listActivityDetails = listActivityDetails;
	}

	public Boolean getIsCjc() {
		return isCjc;
	}

	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
	}

	public List<StudentWiseSubjectSummaryTO> getSubjectwiseAttendanceTOList() {
		return subjectwiseAttendanceTOList;
	}

	public void setSubjectwiseAttendanceTOList(
			List<StudentWiseSubjectSummaryTO> subjectwiseAttendanceTOList) {
		this.subjectwiseAttendanceTOList = subjectwiseAttendanceTOList;
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

	public void setAttendanceID(String attendanceID) {
		this.attendanceID = attendanceID;
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

	public int getSupExamId() {
		return supExamId;
	}

	public void setSupExamId(int supExamId) {
		this.supExamId = supExamId;
	}

	public String getCocurricularLeave() {
		return cocurricularLeave;
	}

	public void setCocurricularLeave(String cocurricularLeave) {
		this.cocurricularLeave = cocurricularLeave;
	}

	public String getOldRegNo() {
		return oldRegNo;
	}

	public void setOldRegNo(String oldRegNo) {
		this.oldRegNo = oldRegNo;
	}

	public int getRemarkid() {
		return remarkid;
	}

	public void setRemarkid(int remarkid) {
		this.remarkid = remarkid;
	}

	public ExamStudentPreviousClassTo getPreviousSem() {
		return previousSem;
	}

	public void setPreviousSem(ExamStudentPreviousClassTo previousSem) {
		this.previousSem = previousSem;
	}

	public Integer getRegularMarkFlag() {
		return regularMarkFlag;
	}

	public void setRegularMarkFlag(Integer regularMarkFlag) {
		this.regularMarkFlag = regularMarkFlag;
	}

	public Integer getSupMarkFlag() {
		return supMarkFlag;
	}

	public void setSupMarkFlag(Integer supMarkFlag) {
		this.supMarkFlag = supMarkFlag;
	}

	public MarksCardTO getSupMCSemExamList() {
		return supMCSemExamList;
	}

	public void setSupMCSemExamList(MarksCardTO supMCSemExamList) {
		this.supMCSemExamList = supMCSemExamList;
	}

	public List<MarksCardTO> getSupSemDetails() {
		return supSemDetails;
	}

	public void setSupSemDetails(List<MarksCardTO> supSemDetails) {
		this.supSemDetails = supSemDetails;
	}

	public String getDisplayPercentage() {
		return displayPercentage;
	}

	public void setDisplayPercentage(String displayPercentage) {
		this.displayPercentage = displayPercentage;
	}

	
}
