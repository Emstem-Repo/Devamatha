package com.kp.cms.forms.hostel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
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
import com.kp.cms.to.attendance.PeriodTO;
import com.kp.cms.to.exam.ExamStudentPreviousClassTo;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.hostel.HlAdmissionTo;
import com.kp.cms.to.reports.StudentWiseSubjectSummaryTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;

public class HostelStudentDetailsForm extends BaseActionForm {

	

	private String regNoOrRollNo;
	private String studentName;
	private String remarks;
	private String method;
	private boolean detailsView;
	private int id;
	private List<HlAdmissionTo> hlAdmissionToList;
	private int isAddRemarks;
	private List<CourseDetailsTO> listCourseDetails;
	private List<RemarcksTO> listRemarcks;
	private List<EdnQualificationTO> eduList;
	private float totalAggrPer;
	private float totalPresent;
	private float totalConducted;
	private String detentionReason;
	private String discontinuedDate;
	private String discontinuedReason;
	private String rejoinDate;
	private String rejoinReason;
	private Boolean isCjc;
	private String hostelName;
	private String block;
	private String unit;
	private String roomNo;
	private String floorNo;
	private String bedNo;
	private List<HlLeave> listLeaveDetails; 
	
	
	private String course;
	private String name;
	private String dateOfBirth;
	private String gender;
	private String eMail;
	private String mobNumber;
	private String phoneNo;
	private String nationality;
	private String dateOfAddmission;
	private String currentAddress;
	private String permanentAddress;
	private String passportNumber;
	private String issueCountry;
	private String validUpTo;
	private String resedentPermitNo;
	private String obtainedDate;
//	private byte[] document;
	private String fatherName;
	private String fatherEducation;
	private String fatherIncomeCurrency;
	private String fatherIncome;
	private String fatherOccupaction;
	private String fatheremail;
	private String fatherPhone;
	
	private String motherName;
	private String motherEducation;
	private String motherincomeCurrency;
	private String motherIncome;
	private String motheroccupaction;
	private String motherEmail;
	private String motherPhone;
	
	private String detentionDate;
	private String courseId;
	private String recommendedBy;
	//added for subject order
	private int semNo;
	private int semesterAcademicYear;
	private List<HlAdmissionTo> hostelAttendanceList;
	private List<HlAdmissionTo> hostelHolidayOrVacationList;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public boolean isDetailsView() {
		return detailsView;
	}
	public void setDetailsView(boolean detailsView) {
		this.detailsView = detailsView;
	}
	
	public String getRegNoOrRollNo() {
		return regNoOrRollNo;
	}
	public void setRegNoOrRollNo(String regNoOrRollNo) {
		this.regNoOrRollNo = regNoOrRollNo;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public List<HlAdmissionTo> getHlAdmissionToList() {
		return hlAdmissionToList;
	}
	public void setHlAdmissionToList(List<HlAdmissionTo> hlAdmissionToList) {
		this.hlAdmissionToList = hlAdmissionToList;
	}
	public int getIsAddRemarks() {
		return isAddRemarks;
	}
	public void setIsAddRemarks(int isAddRemarks) {
		this.isAddRemarks = isAddRemarks;
	}
	public List<CourseDetailsTO> getListCourseDetails() {
		return listCourseDetails;
	}
	public void setListCourseDetails(List<CourseDetailsTO> listCourseDetails) {
		this.listCourseDetails = listCourseDetails;
	}
	public List<RemarcksTO> getListRemarcks() {
		return listRemarcks;
	}
	public void setListRemarcks(List<RemarcksTO> listRemarcks) {
		this.listRemarcks = listRemarcks;
	}
	public List<EdnQualificationTO> getEduList() {
		return eduList;
	}
	public void setEduList(List<EdnQualificationTO> eduList) {
		this.eduList = eduList;
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
	public Boolean getIsCjc() {
		return isCjc;
	}
	public void setIsCjc(Boolean isCjc) {
		this.isCjc = isCjc;
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
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public List<HlLeave> getListLeaveDetails() {
		return listLeaveDetails;
	}
	public void setListLeaveDetails(List<HlLeave> listLeaveDetails) {
		this.listLeaveDetails = listLeaveDetails;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getMobNumber() {
		return mobNumber;
	}
	public void setMobNumber(String mobNumber) {
		this.mobNumber = mobNumber;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getDateOfAddmission() {
		return dateOfAddmission;
	}
	public void setDateOfAddmission(String dateOfAddmission) {
		this.dateOfAddmission = dateOfAddmission;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	public String getPermanentAddress() {
		return permanentAddress;
	}
	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getIssueCountry() {
		return issueCountry;
	}
	public void setIssueCountry(String issueCountry) {
		this.issueCountry = issueCountry;
	}
	public String getValidUpTo() {
		return validUpTo;
	}
	public void setValidUpTo(String validUpTo) {
		this.validUpTo = validUpTo;
	}
	public String getResedentPermitNo() {
		return resedentPermitNo;
	}
	public void setResedentPermitNo(String resedentPermitNo) {
		this.resedentPermitNo = resedentPermitNo;
	}
	public String getObtainedDate() {
		return obtainedDate;
	}
	public void setObtainedDate(String obtainedDate) {
		this.obtainedDate = obtainedDate;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public String getFatherEducation() {
		return fatherEducation;
	}
	public void setFatherEducation(String fatherEducation) {
		this.fatherEducation = fatherEducation;
	}
	public String getFatherIncomeCurrency() {
		return fatherIncomeCurrency;
	}
	public void setFatherIncomeCurrency(String fatherIncomeCurrency) {
		this.fatherIncomeCurrency = fatherIncomeCurrency;
	}
	public String getFatherIncome() {
		return fatherIncome;
	}
	public void setFatherIncome(String fatherIncome) {
		this.fatherIncome = fatherIncome;
	}
	public String getFatherOccupaction() {
		return fatherOccupaction;
	}
	public void setFatherOccupaction(String fatherOccupaction) {
		this.fatherOccupaction = fatherOccupaction;
	}
	public String getFatheremail() {
		return fatheremail;
	}
	public void setFatheremail(String fatheremail) {
		this.fatheremail = fatheremail;
	}
	public String getFatherPhone() {
		return fatherPhone;
	}
	public void setFatherPhone(String fatherPhone) {
		this.fatherPhone = fatherPhone;
	}
	public String getMotherName() {
		return motherName;
	}
	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
	public String getMotherEducation() {
		return motherEducation;
	}
	public void setMotherEducation(String motherEducation) {
		this.motherEducation = motherEducation;
	}
	public String getMotherincomeCurrency() {
		return motherincomeCurrency;
	}
	public void setMotherincomeCurrency(String motherincomeCurrency) {
		this.motherincomeCurrency = motherincomeCurrency;
	}
	public String getMotherIncome() {
		return motherIncome;
	}
	public void setMotherIncome(String motherIncome) {
		this.motherIncome = motherIncome;
	}
	public String getMotheroccupaction() {
		return motheroccupaction;
	}
	public void setMotheroccupaction(String motheroccupaction) {
		this.motheroccupaction = motheroccupaction;
	}
	public String getMotherEmail() {
		return motherEmail;
	}
	public void setMotherEmail(String motherEmail) {
		this.motherEmail = motherEmail;
	}
	public String getMotherPhone() {
		return motherPhone;
	}
	public void setMotherPhone(String motherPhone) {
		this.motherPhone = motherPhone;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public String getRecommendedBy() {
		return recommendedBy;
	}
	public void setRecommendedBy(String recommendedBy) {
		this.recommendedBy = recommendedBy;
	}
	public int getSemNo() {
		return semNo;
	}
	public void setSemNo(int semNo) {
		this.semNo = semNo;
	}
	public int getSemesterAcademicYear() {
		return semesterAcademicYear;
	}
	public void setSemesterAcademicYear(int semesterAcademicYear) {
		this.semesterAcademicYear = semesterAcademicYear;
	}
	public List<HlAdmissionTo> getHostelAttendanceList() {
		return hostelAttendanceList;
	}
	public void setHostelAttendanceList(List<HlAdmissionTo> hostelAttendanceList) {
		this.hostelAttendanceList = hostelAttendanceList;
	}
	public List<HlAdmissionTo> getHostelHolidayOrVacationList() {
		return hostelHolidayOrVacationList;
	}
	public void setHostelHolidayOrVacationList(
			List<HlAdmissionTo> hostelHolidayOrVacationList) {
		this.hostelHolidayOrVacationList = hostelHolidayOrVacationList;
	}
	
	public void reset()
	{
	this.studentName=null;
	this.regNoOrRollNo=null;
	this.bedNo=null;
	this.block=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request){
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
}
