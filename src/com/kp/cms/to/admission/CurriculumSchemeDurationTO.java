package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class CurriculumSchemeDurationTO  implements Serializable{
	private int id;
	private int semester;
	private int curriculumSchemeId;
	private CurriculumSchemeTO curriculumSchemeTO;
	
	private String startDate;
	private String endDate;
	private int academicYear;
	private String[] subjectGroups;
	private int selectedIndex;
	private String tempYear;
		
	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}

	private Set<CurriculumSchemeSubjectTO>curriculumSchemeSubjectTO;
	
	private List<CurriculumSchemeSubjectTO> curriculumSubjectTOList;
	
	public CurriculumSchemeTO getCurriculumSchemeTO() {
		return curriculumSchemeTO;
	}
	
	public void setCurriculumSchemeTO(CurriculumSchemeTO curriculumSchemeTO) {
		this.curriculumSchemeTO = curriculumSchemeTO;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Set<CurriculumSchemeSubjectTO> getCurriculumSchemeSubjectTO() {
		return curriculumSchemeSubjectTO;
	}
	public void setCurriculumSchemeSubjectTO(
			Set<CurriculumSchemeSubjectTO> curriculumSchemeSubjectTO) {
		this.curriculumSchemeSubjectTO = curriculumSchemeSubjectTO;
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
	public int getSemester() {
		return semester;
	}
	public void setSemester(int semester) {
		this.semester = semester;
	}
	public int getCurriculumSchemeId() {
		return curriculumSchemeId;
	}
	public void setCurriculumSchemeId(int curriculumSchemeId) {
		this.curriculumSchemeId = curriculumSchemeId;
	}

	public int getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(int academicYear) {
		this.academicYear = academicYear;
	}

	public List<CurriculumSchemeSubjectTO> getCurriculumSubjectTOList() {
		return curriculumSubjectTOList;
	}

	public void setCurriculumSubjectTOList(
			List<CurriculumSchemeSubjectTO> curriculumSubjectTOList) {
		this.curriculumSubjectTOList = curriculumSubjectTOList;
	}

	
	public String[] getSubjectGroups() {
		return subjectGroups;
	}

	public void setSubjectGroups(String[] subjectGroups) {
		this.subjectGroups = subjectGroups;
	}

	public String getTempYear() {
		return tempYear;
	}

	public void setTempYear(String tempYear) {
		this.tempYear = tempYear;
	}


}
