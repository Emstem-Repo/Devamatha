package com.kp.cms.to.admin;

import java.util.Date;
import java.util.List;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamMarksEntryDetailsTO;

public class SubjectTO extends BaseActionForm{
	
		private int id;
		private String name;
		private String code;
		private String optional;
		private String secondlanguage;
		private String passingmarks;
		private String totalmarks;
		private String nameCode;
		private String questionbyrequired;
		private String hourpersem;
		
		private String createdBy;;
		private String modifiedBy;
		private Date createdDate;
		private Date lastModifiedDate;
		private String totalMarks;
		private String passingMarks;
		private String isSecondLanguage;
		private String isOptionalSubject;
		private String isActive;
		private String cDate;
		private String lDate;
		
		//Added by Shwetha - 9Elements
		private String theoryPractical;
		private String subjectType;
		private boolean checked;
		
		private String startDate;
		private String startTime;
		
		// Added  by balaji
		private String ciaMaxMarks;
		private String ciaMarksAwarded;
		private String eseMaxMarks;
		private String eseMinMarks;
		private String eseMarksAwarded;
		private String totalMaxMarks;
		private String totalMarksAwarded;
		private String credits;
		private String grade;
		private String attMaxMarks;
		private String attMarksAwarded;
		private boolean practical;
		private String practicalCiaMaxMarks;
		private String practicalCiaMarksAwarded;
		private String practicalEseMaxMarks;
		private String practicalEseMinMarks;
		private String practicalEseMarksAwarded;
		private String practicalTotalMaxMarks;
		private String practicalTotalMarksAwarded;
		private String practicalCredits;
		private String practicalGrade;
		private String practicalAttMaxMarks;
		private String practicalAttMarksAwarded;
		private int subOrder;
		private boolean theory;
		private List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList;
		private int order;
		private String subjectID;
		private boolean displaySubject;
		private boolean appearedTheory;
		private boolean appearedPractical;
		private String departmentId;
		private String departmentName;
		private String schemeNo;
		private String status;
		private boolean revaluationReq;
		private String revType;
		private String theoryMarks;
		private String practicalMarks;
		private String checked1;
		private String tempChecked1;
		private String examMidsemExemptionDetailsId;
		private String roomNo;
		private String blockNo;
		private String floorNo;
			
		private String endTime;
		private String dateofBirth;
		private String semesterNo;
		private String semesterExt;
		private String examName;
		private String courseName;
		private String secName;
		private String thirdName;
		private String fourthName;
		private String fifthName;
		private String sixthName;
		
		private String gradePoints;
		private String theoryGradePnt;
		private String practicalGradePnt;
		private String creditPoint;
		private String resultClass;
		private String totalGradePoints;
		private String avgmark;
		//raghu
		private String examType;
		private String convertedMark;
		private String convertedMarkMax;
		private String theoryCreditPoint;
		private String practicalCreditPoint;
		private String grievanceRemark;
		
		public String getGrievanceRemark() {
			return grievanceRemark;
		}
		public void setGrievanceRemark(String grievanceRemark) {
			this.grievanceRemark = grievanceRemark;
		}
		private int consolidatedSubjectStreamId;
		private String subjectTypeBasedOnMarks;
		
		public String getTheoryGradePnt() {
			return theoryGradePnt;
		}
		public void setTheoryGradePnt(String theoryGradePnt) {
			this.theoryGradePnt = theoryGradePnt;
		}
		public String getPracticalGradePnt() {
			return practicalGradePnt;
		}
		public void setPracticalGradePnt(String practicalGradePnt) {
			this.practicalGradePnt = practicalGradePnt;
		}
		public String getExamMidsemExemptionDetailsId() {
			return examMidsemExemptionDetailsId;
		}
		public void setExamMidsemExemptionDetailsId(String examMidsemExemptionDetailsId) {
			this.examMidsemExemptionDetailsId = examMidsemExemptionDetailsId;
		}
		public String getTheoryMarks() {
			return theoryMarks;
		}
		public void setTheoryMarks(String theoryMarks) {
			this.theoryMarks = theoryMarks;
		}
		public String getPracticalMarks() {
			return practicalMarks;
		}
		public void setPracticalMarks(String practicalMarks) {
			this.practicalMarks = practicalMarks;
		}
		public String getCDate() {
			return cDate;
		}
		public void setCDate(String date) {
			cDate = date;
		}
		public String getLDate() {
			return lDate;
		}
		public void setLDate(String date) {
			lDate = date;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getPassingmarks() {
			return passingmarks;
		}
		public void setPassingmarks(String passingmarks) {
			this.passingmarks = passingmarks;
		}
		public String getTotalmarks() {
			return totalmarks;
		}
		public void setTotalmarks(String totalmarks) {
			this.totalmarks = totalmarks;
		}
		public String getOptional() {
			return optional;
		}
		public void setOptional(String optional) {
			this.optional = optional;
		}
		public String getSecondlanguage() {
			return secondlanguage;
		}
		public void setSecondlanguage(String secondlanguage) {
			this.secondlanguage = secondlanguage;
		}
		public String getNameCode() {
			return nameCode;
		}
		public void setNameCode(String nameCode) {
			this.nameCode = nameCode;
		}
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public String getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(String modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getLastModifiedDate() {
			return lastModifiedDate;
		}
		public void setLastModifiedDate(Date lastModifiedDate) {
			this.lastModifiedDate = lastModifiedDate;
		}
		public String getTotalMarks() {
			return totalMarks;
		}
		public void setTotalMarks(String totalMarks) {
			this.totalMarks = totalMarks;
		}
		public String getPassingMarks() {
			return passingMarks;
		}
		public void setPassingMarks(String passingMarks) {
			this.passingMarks = passingMarks;
		}
		public String getIsSecondLanguage() {
			return isSecondLanguage;
		}
		public void setIsSecondLanguage(String isSecondLanguage) {
			this.isSecondLanguage = isSecondLanguage;
		}
		public String getIsOptionalSubject() {
			return isOptionalSubject;
		}
		public void setIsOptionalSubject(String isOptionalSubject) {
			this.isOptionalSubject = isOptionalSubject;
		}
		public String getIsActive() {
			return isActive;
		}
		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}
		public String getTheoryPractical() {
			return theoryPractical;
		}
		public void setTheoryPractical(String theoryPractical) {
			this.theoryPractical = theoryPractical;
		}
		public String getSubjectType() {
			return subjectType;
		}
		public void setSubjectType(String subjectType) {
			this.subjectType = subjectType;
		}
		public boolean isChecked() {
			return checked;
		}
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
		public String getStartDate() {
			return startDate;
		}
		public void setStartDate(String startDate) {
			this.startDate = startDate;
		}
		public String getStartTime() {
			return startTime;
		}
		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}
		public String getCredits() {
			return credits;
		}
		public void setCredits(String credits) {
			this.credits = credits;
		}
		public String getGrade() {
			return grade;
		}
		public void setGrade(String grade) {
			this.grade = grade;
		}
		public boolean isPractical() {
			return practical;
		}
		public void setPractical(boolean practical) {
			this.practical = practical;
		}
		public String getPracticalCredits() {
			return practicalCredits;
		}
		public void setPracticalCredits(String practicalCredits) {
			this.practicalCredits = practicalCredits;
		}
		public String getPracticalGrade() {
			return practicalGrade;
		}
		public void setPracticalGrade(String practicalGrade) {
			this.practicalGrade = practicalGrade;
		}
		public String getCiaMarksAwarded() {
			return ciaMarksAwarded;
		}
		public void setCiaMarksAwarded(String ciaMarksAwarded) {
			this.ciaMarksAwarded = ciaMarksAwarded;
		}
		public String getEseMarksAwarded() {
			return eseMarksAwarded;
		}
		public void setEseMarksAwarded(String eseMarksAwarded) {
			this.eseMarksAwarded = eseMarksAwarded;
		}
		public String getTotalMarksAwarded() {
			return totalMarksAwarded;
		}
		public void setTotalMarksAwarded(String totalMarksAwarded) {
			this.totalMarksAwarded = totalMarksAwarded;
		}
		public String getAttMarksAwarded() {
			return attMarksAwarded;
		}
		public void setAttMarksAwarded(String attMarksAwarded) {
			this.attMarksAwarded = attMarksAwarded;
		}
		public String getPracticalCiaMarksAwarded() {
			return practicalCiaMarksAwarded;
		}
		public void setPracticalCiaMarksAwarded(String practicalCiaMarksAwarded) {
			this.practicalCiaMarksAwarded = practicalCiaMarksAwarded;
		}
		public String getPracticalEseMarksAwarded() {
			return practicalEseMarksAwarded;
		}
		public void setPracticalEseMarksAwarded(String practicalEseMarksAwarded) {
			this.practicalEseMarksAwarded = practicalEseMarksAwarded;
		}
		public String getPracticalTotalMarksAwarded() {
			return practicalTotalMarksAwarded;
		}
		public void setPracticalTotalMarksAwarded(String practicalTotalMarksAwarded) {
			this.practicalTotalMarksAwarded = practicalTotalMarksAwarded;
		}
		public String getPracticalAttMarksAwarded() {
			return practicalAttMarksAwarded;
		}
		public void setPracticalAttMarksAwarded(String practicalAttMarksAwarded) {
			this.practicalAttMarksAwarded = practicalAttMarksAwarded;
		}
		public String getCiaMaxMarks() {
			return ciaMaxMarks;
		}
		public void setCiaMaxMarks(String ciaMaxMarks) {
			this.ciaMaxMarks = ciaMaxMarks;
		}
		public String getEseMaxMarks() {
			return eseMaxMarks;
		}
		public void setEseMaxMarks(String eseMaxMarks) {
			this.eseMaxMarks = eseMaxMarks;
		}
		public String getEseMinMarks() {
			return eseMinMarks;
		}
		public void setEseMinMarks(String eseMinMarks) {
			this.eseMinMarks = eseMinMarks;
		}
		public String getTotalMaxMarks() {
			return totalMaxMarks;
		}
		public void setTotalMaxMarks(String totalMaxMarks) {
			this.totalMaxMarks = totalMaxMarks;
		}
		public String getAttMaxMarks() {
			return attMaxMarks;
		}
		public void setAttMaxMarks(String attMaxMarks) {
			this.attMaxMarks = attMaxMarks;
		}
		public String getPracticalCiaMaxMarks() {
			return practicalCiaMaxMarks;
		}
		public void setPracticalCiaMaxMarks(String practicalCiaMaxMarks) {
			this.practicalCiaMaxMarks = practicalCiaMaxMarks;
		}
		public String getPracticalEseMaxMarks() {
			return practicalEseMaxMarks;
		}
		public void setPracticalEseMaxMarks(String practicalEseMaxMarks) {
			this.practicalEseMaxMarks = practicalEseMaxMarks;
		}
		public String getPracticalEseMinMarks() {
			return practicalEseMinMarks;
		}
		public void setPracticalEseMinMarks(String practicalEseMinMarks) {
			this.practicalEseMinMarks = practicalEseMinMarks;
		}
		public String getPracticalTotalMaxMarks() {
			return practicalTotalMaxMarks;
		}
		public void setPracticalTotalMaxMarks(String practicalTotalMaxMarks) {
			this.practicalTotalMaxMarks = practicalTotalMaxMarks;
		}
		public String getPracticalAttMaxMarks() {
			return practicalAttMaxMarks;
		}
		public void setPracticalAttMaxMarks(String practicalAttMaxMarks) {
			this.practicalAttMaxMarks = practicalAttMaxMarks;
		}
		public int getSubOrder() {
			return subOrder;
		}
		public void setSubOrder(int subOrder) {
			this.subOrder = subOrder;
		}
		public boolean isTheory() {
			return theory;
		}
		public void setTheory(boolean theory) {
			this.theory = theory;
		}
		public List<ExamMarksEntryDetailsTO> getExamMarksEntryDetailsTOList() {
			return examMarksEntryDetailsTOList;
		}
		public void setExamMarksEntryDetailsTOList(
				List<ExamMarksEntryDetailsTO> examMarksEntryDetailsTOList) {
			this.examMarksEntryDetailsTOList = examMarksEntryDetailsTOList;
		}
		public int getOrder() {
			return order;
		}
		public void setOrder(int order) {
			this.order = order;
		}
		public String getSubjectID() {
			return subjectID;
		}
		public void setSubjectID(String subjectID) {
			this.subjectID = subjectID;
		}
		public boolean isDisplaySubject() {
			return displaySubject;
		}
		public void setDisplaySubject(boolean displaySubject) {
			this.displaySubject = displaySubject;
		}
		public boolean isAppearedTheory() {
			return appearedTheory;
		}
		public void setAppearedTheory(boolean appearedTheory) {
			this.appearedTheory = appearedTheory;
		}
		public boolean isAppearedPractical() {
			return appearedPractical;
		}
		public void setAppearedPractical(boolean appearedPractical) {
			this.appearedPractical = appearedPractical;
		}
		public String getQuestionbyrequired() {
			return questionbyrequired;
		}
		public void setQuestionbyrequired(String questionbyrequired) {
			this.questionbyrequired = questionbyrequired;
		}
		public String getHourpersem() {
			return hourpersem;
		}
		public void setHourpersem(String hourpersem) {
			this.hourpersem = hourpersem;
		}
		public String getDepartmentId() {
			return departmentId;
		}
		public void setDepartmentId(String departmentId) {
			this.departmentId = departmentId;
		}
		public String getSchemeNo() {
			return schemeNo;
		}
		public void setSchemeNo(String schemeNo) {
			this.schemeNo = schemeNo;
		}
		public String getDepartmentName() {
			return departmentName;
		}
		public void setDepartmentName(String departmentName) {
			this.departmentName = departmentName;
		}
		public boolean isRevaluationReq() {
			return revaluationReq;
		}
		public void setRevaluationReq(boolean revaluationReq) {
			this.revaluationReq = revaluationReq;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getRevType() {
			return revType;
		}
		public void setRevType(String revType) {
			this.revType = revType;
		}
		public String getChecked1() {
			return checked1;
		}
		public void setChecked1(String checked1) {
			this.checked1 = checked1;
		}
		public String getTempChecked1() {
			return tempChecked1;
		}
		public void setTempChecked1(String tempChecked1) {
			this.tempChecked1 = tempChecked1;
		}
		public String getRoomNo() {
			return roomNo;
		}
		public void setRoomNo(String roomNo) {
			this.roomNo = roomNo;
		}
		public String getBlockNo() {
			return blockNo;
		}
		public void setBlockNo(String blockNo) {
			this.blockNo = blockNo;
		}
		public String getFloorNo() {
			return floorNo;
		}
		public void setFloorNo(String floorNo) {
			this.floorNo = floorNo;
		}
		public String getcDate() {
			return cDate;
		}
		public void setcDate(String cDate) {
			this.cDate = cDate;
		}
		public String getlDate() {
			return lDate;
		}
		public void setlDate(String lDate) {
			this.lDate = lDate;
		}
		public String getGradePoints() {
			return gradePoints;
		}
		public void setGradePoints(String gradePoints) {
			this.gradePoints = gradePoints;
		}
		public String getCreditPoint() {
			return creditPoint;
		}
		public void setCreditPoint(String creditPoint) {
			this.creditPoint = creditPoint;
		}
		public String getResultClass() {
			return resultClass;
		}
		public void setResultClass(String resultClass) {
			this.resultClass = resultClass;
		}
		public String getTotalGradePoints() {
			return totalGradePoints;
		}
		public void setTotalGradePoints(String totalGradePoints) {
			this.totalGradePoints = totalGradePoints;
		}
		public String getEndTime() {
			return endTime;
		}
		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}
		public String getDateofBirth() {
			return dateofBirth;
		}
		public void setDateofBirth(String dateofBirth) {
			this.dateofBirth = dateofBirth;
		}
		public String getSemesterNo() {
			return semesterNo;
		}
		public void setSemesterNo(String semesterNo) {
			this.semesterNo = semesterNo;
		}
		public String getSemesterExt() {
			return semesterExt;
		}
		public void setSemesterExt(String semesterExt) {
			this.semesterExt = semesterExt;
		}
		public String getExamName() {
			return examName;
		}
		public void setExamName(String examName) {
			this.examName = examName;
		}
		public String getCourseName() {
			return courseName;
		}
		public void setCourseName(String courseName) {
			this.courseName = courseName;
		}
		public String getSecName() {
			return secName;
		}
		public void setSecName(String secName) {
			this.secName = secName;
		}
		public String getThirdName() {
			return thirdName;
		}
		public void setThirdName(String thirdName) {
			this.thirdName = thirdName;
		}
		public String getFourthName() {
			return fourthName;
		}
		public void setFourthName(String fourthName) {
			this.fourthName = fourthName;
		}
		public String getFifthName() {
			return fifthName;
		}
		public void setFifthName(String fifthName) {
			this.fifthName = fifthName;
		}
		public String getSixthName() {
			return sixthName;
		}
		public void setSixthName(String sixthName) {
			this.sixthName = sixthName;
		}
		public String getAvgmark() {
			return avgmark;
		}
		public void setAvgmark(String avgmark) {
			this.avgmark = avgmark;
		}
		public String getExamType() {
			return examType;
		}
		public void setExamType(String examType) {
			this.examType = examType;
		}
		public String getConvertedMark() {
			return convertedMark;
		}
		public void setConvertedMark(String convertedMark) {
			this.convertedMark = convertedMark;
		}
		public String getConvertedMarkMax() {
			return convertedMarkMax;
		}
		public void setConvertedMarkMax(String convertedMarkMax) {
			this.convertedMarkMax = convertedMarkMax;
		}
		public String getTheoryCreditPoint() {
			return theoryCreditPoint;
		}
		public void setTheoryCreditPoint(String theoryCreditPoint) {
			this.theoryCreditPoint = theoryCreditPoint;
		}
		public String getPracticalCreditPoint() {
			return practicalCreditPoint;
		}
		public void setPracticalCreditPoint(String practicalCreditPoint) {
			this.practicalCreditPoint = practicalCreditPoint;
		}
		public int getConsolidatedSubjectStreamId() {
			return consolidatedSubjectStreamId;
		}
		public void setConsolidatedSubjectStreamId(int consolidatedSubjectStreamId) {
			this.consolidatedSubjectStreamId = consolidatedSubjectStreamId;
		}
		public String getSubjectTypeBasedOnMarks() {
			return subjectTypeBasedOnMarks;
		}
		public void setSubjectTypeBasedOnMarks(String subjectTypeBasedOnMarks) {
			this.subjectTypeBasedOnMarks = subjectTypeBasedOnMarks;
		}
		
}