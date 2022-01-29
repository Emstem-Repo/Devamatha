package com.kp.cms.bo.exam;

/**
 * Jan 6, 2010
 * Created By 9Elements Team
 */
import java.util.Date;
import java.util.Set;

public class CurriculumSchemeDurationUtilBO extends ExamGenBO {

	private Date startDate;
	private Date endDate;
	private Integer curriculumSchemeId;
	private int semesterYearNo;
	private int academicYear;

	private Set<CurriculumSchemeSubjectUtilBO> curriculumSchemeSubjectUtilBOSet;
	private Set<ClassSchemewiseUtilBO> classSchemewiseUtilBOSet;
	private CurriculumSchemeUtilBO curriculumSchemeUtilBO;

	public CurriculumSchemeDurationUtilBO() {
		super();
	}

	public CurriculumSchemeDurationUtilBO(int academicYear,
			Integer curriculumSchemeId, Date endDate, int semesterYearNo,
			Date startDate) {
		super();
		this.academicYear = academicYear;
		this.curriculumSchemeId = curriculumSchemeId;
		this.endDate = endDate;
		this.semesterYearNo = semesterYearNo;
		this.startDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getCurriculumSchemeId() {
		return curriculumSchemeId;
	}

	public void setCurriculumSchemeId(Integer curriculumSchemeId) {
		this.curriculumSchemeId = curriculumSchemeId;
	}

	public int getSemesterYearNo() {
		return semesterYearNo;
	}

	public void setSemesterYearNo(int semesterYearNo) {
		this.semesterYearNo = semesterYearNo;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public Set<CurriculumSchemeSubjectUtilBO> getCurriculumSchemeSubjectUtilBOSet() {
		return curriculumSchemeSubjectUtilBOSet;
	}

	public void setCurriculumSchemeSubjectUtilBOSet(
			Set<CurriculumSchemeSubjectUtilBO> curriculumSchemeSubjectUtilBOSet) {
		this.curriculumSchemeSubjectUtilBOSet = curriculumSchemeSubjectUtilBOSet;
	}

	public Set<ClassSchemewiseUtilBO> getClassSchemewiseUtilBOSet() {
		return classSchemewiseUtilBOSet;
	}

	public void setClassSchemewiseUtilBOSet(
			Set<ClassSchemewiseUtilBO> classSchemewiseUtilBOSet) {
		this.classSchemewiseUtilBOSet = classSchemewiseUtilBOSet;
	}

	public void setCurriculumSchemeUtilBO(CurriculumSchemeUtilBO curriculumSchemeUtilBO) {
		this.curriculumSchemeUtilBO = curriculumSchemeUtilBO;
	}

	public CurriculumSchemeUtilBO getCurriculumSchemeUtilBO() {
		return curriculumSchemeUtilBO;
	}

	
}
