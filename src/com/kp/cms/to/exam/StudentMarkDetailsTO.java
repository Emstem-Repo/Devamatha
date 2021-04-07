package com.kp.cms.to.exam;


public class StudentMarkDetailsTO {

	private Integer subjectId;
	private String name;
	private String maxthoery;
	private String maxpractical;
	private String minpractical;
	private String minthoery;
	private String avgthoery;
	private String avgpractical;
	private String is_theory_practical;
	private Integer studentId;

	// Student Marks
	private String stuTheoryIntMark;
	private String stuPracIntMark;
	private String stuTheoryRegMark;
	private String stuPracRegMark;
	private String isTheoryPrac;

	// Subject Settings
	private String theoryEseMin;
	private String theoryEseTheoryFinalMin;
	private String theoryIntMinMarksTotal;
	private String practicalEseMin;
	private String practicalEsePracFinalMin;
	private String practicalIntMinMarksTotal;

	private String passRegular;
	private String passInternal;
	
	private int isFailedTheory;
	private int isFailedPractical;
	private Boolean isTheoryFailed;
	private Boolean isPracticalFailed;
	private double tempSubTotal;
	private double tempStuTotal;
	private String subjectName;
	private String comment;
	private String correctedDate;
	private Boolean addTotal;
	//by giri
	private String semester;
	private String mandOrOptional;
	private String status;
	//end by giri
	
	
	public String getSemester() {
		return semester;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMandOrOptional() {
		return mandOrOptional;
	}

	public void setMandOrOptional(String mandOrOptional) {
		this.mandOrOptional = mandOrOptional;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public String getTheoryEseMin() {
		return theoryEseMin;
	}

	public void setTheoryEseMin(String theoryEseMin) {
		this.theoryEseMin = theoryEseMin;
	}

	public String getTheoryEseTheoryFinalMin() {
		return theoryEseTheoryFinalMin;
	}

	public void setTheoryEseTheoryFinalMin(String theoryEseTheoryFinalMin) {
		this.theoryEseTheoryFinalMin = theoryEseTheoryFinalMin;
	}

	public String getTheoryIntMinMarksTotal() {
		return theoryIntMinMarksTotal;
	}

	public void setTheoryIntMinMarksTotal(String theoryIntMinMarksTotal) {
		this.theoryIntMinMarksTotal = theoryIntMinMarksTotal;
	}

	public String getPracticalEseMin() {
		return practicalEseMin;
	}

	public void setPracticalEseMin(String practicalEseMin) {
		this.practicalEseMin = practicalEseMin;
	}

	public String getPracticalEsePracFinalMin() {
		return practicalEsePracFinalMin;
	}

	public void setPracticalEsePracFinalMin(String practicalEsePracFinalMin) {
		this.practicalEsePracFinalMin = practicalEsePracFinalMin;
	}

	public String getPracticalIntMinMarksTotal() {
		return practicalIntMinMarksTotal;
	}

	public void setPracticalIntMinMarksTotal(String practicalIntMinMarksTotal) {
		this.practicalIntMinMarksTotal = practicalIntMinMarksTotal;
	}

	public void setIs_theory_practical(String isTheoryPractical) {
		is_theory_practical = isTheoryPractical;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStuTheoryIntMark() {
		return stuTheoryIntMark;
	}

	public void setStuTheoryIntMark(String stuTheoryIntMark) {
		this.stuTheoryIntMark = stuTheoryIntMark;
	}

	public String getStuPracIntMark() {
		return stuPracIntMark;
	}

	public void setStuPracIntMark(String stuPracIntMark) {
		this.stuPracIntMark = stuPracIntMark;
	}

	public String getStuTheoryRegMark() {
		return stuTheoryRegMark;
	}

	public void setStuTheoryRegMark(String stuTheoryRegMark) {
		this.stuTheoryRegMark = stuTheoryRegMark;
	}

	public String getStuPracRegMark() {
		return stuPracRegMark;
	}

	public void setStuPracRegMark(String stuPracRegMark) {
		this.stuPracRegMark = stuPracRegMark;
	}

	private String theoryPreferred;
	private String practicalPreferred;

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaxthoery() {
		return maxthoery;
	}

	public void setMaxthoery(String maxthoery) {
		this.maxthoery = maxthoery;
	}

	public String getMaxpractical() {
		return maxpractical;
	}

	public void setMaxpractical(String maxpractical) {
		this.maxpractical = maxpractical;
	}

	public String getMinthoery() {
		return minthoery;
	}

	public void setMinthoery(String minthoery) {
		this.minthoery = minthoery;
	}

	public String getAvgthoery() {
		return avgthoery;
	}

	public void setAvgthoery(String avgthoery) {
		this.avgthoery = avgthoery;
	}

	public String getAvgpractical() {
		return avgpractical;
	}

	public void setAvgpractical(String avgpractical) {
		this.avgpractical = avgpractical;
	}

	public String getIs_theory_practical() {
		return is_theory_practical;
	}

	public void setIs_theory_practical(Character is_theory_practical) {
		this.is_theory_practical = is_theory_practical.toString();
	}

	public void setMinpractical(String minpractical) {
		this.minpractical = minpractical;
	}

	public String getMinpractical() {
		return minpractical;
	}

	public void setTheoryPreferred(String theoryPreferred) {
		this.theoryPreferred = theoryPreferred;
	}

	public String getTheoryPreferred() {
		return theoryPreferred;
	}

	public void setPracticalPreferred(String practicalPreferred) {
		this.practicalPreferred = practicalPreferred;
	}

	public String getPracticalPreferred() {
		return practicalPreferred;
	}

	public void setIsTheoryPrac(String isTheoryPrac) {
		this.isTheoryPrac = isTheoryPrac;
	}

	public String getIsTheoryPrac() {
		return isTheoryPrac;
	}

	public void setPassRegular(String passRegular) {
		this.passRegular = passRegular;
	}

	public String getPassRegular() {
		return passRegular;
	}

	public void setPassInternal(String passInternal) {
		this.passInternal = passInternal;
	}

	public String getPassInternal() {
		return passInternal;
	}

	public int getIsFailedTheory() {
		return isFailedTheory;
	}

	public void setIsFailedTheory(int isFailedTheory) {
		this.isFailedTheory = isFailedTheory;
	}

	public int getIsFailedPractical() {
		return isFailedPractical;
	}

	public void setIsFailedPractical(int isFailedPractical) {
		this.isFailedPractical = isFailedPractical;
	}

	public Boolean getIsTheoryFailed() {
		return isTheoryFailed;
	}

	public void setIsTheoryFailed(Boolean isTheoryFailed) {
		this.isTheoryFailed = isTheoryFailed;
	}

	public Boolean getIsPracticalFailed() {
		return isPracticalFailed;
	}

	public void setIsPracticalFailed(Boolean isPracticalFailed) {
		this.isPracticalFailed = isPracticalFailed;
	}

	public double getTempSubTotal() {
		return tempSubTotal;
	}

	public void setTempSubTotal(double tempSubTotal) {
		this.tempSubTotal = tempSubTotal;
	}

	public double getTempStuTotal() {
		return tempStuTotal;
	}

	public void setTempStuTotal(double tempStuTotal) {
		this.tempStuTotal = tempStuTotal;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCorrectedDate() {
		return correctedDate;
	}

	public void setCorrectedDate(String correctedDate) {
		this.correctedDate = correctedDate;
	}

	public Boolean getAddTotal() {
		return addTotal;
	}

	public void setAddTotal(Boolean addTotal) {
		this.addTotal = addTotal;
	}
}
