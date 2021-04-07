package com.kp.cms.to.exam;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Feb 21, 2010 Created By 9Elements Team
 */
@SuppressWarnings("serial")
public class ExamUpdateProcessMapTO implements Serializable {

	private Integer sumOfStudentsPresent;
	private Integer sumOfStudentOnCoCurricularLeave;
	private Integer sumOfStudentOnLeave;
	private Integer totalClassHourHeld;
	
	private HashMap<Integer, ExamUpdateProcessMapTO> attendenceTheoryMap;
	private HashMap<Integer, ExamUpdateProcessMapTO> attendencePracticalMap;

	public HashMap<Integer, ExamUpdateProcessMapTO> getAttendenceTheoryMap() {
		return attendenceTheoryMap;
	}

	public void setAttendenceTheoryMap(
			HashMap<Integer, ExamUpdateProcessMapTO> attendenceTheoryMap) {
		this.attendenceTheoryMap = attendenceTheoryMap;
	}

	public HashMap<Integer, ExamUpdateProcessMapTO> getAttendencePracticalMap() {
		return attendencePracticalMap;
	}

	public void setAttendencePracticalMap(
			HashMap<Integer, ExamUpdateProcessMapTO> attendencePracticalMap) {
		this.attendencePracticalMap = attendencePracticalMap;
	}

	public ExamUpdateProcessMapTO getSubjectTheoryTo(Integer subjectId) {
		return attendenceTheoryMap.get(subjectId);
	}

	public ExamUpdateProcessMapTO getSubjectPracticalTo(Integer subjectId) {
		return attendencePracticalMap.get(subjectId);
	}

	public Integer getSumOfStudentsPresent() {
		return sumOfStudentsPresent;
	}

	public void setSumOfStudentsPresent(Integer sumOfStudentsPresent) {
		this.sumOfStudentsPresent = sumOfStudentsPresent;
	}

	public Integer getSumOfStudentOnCoCurricularLeave() {
		return sumOfStudentOnCoCurricularLeave;
	}

	public void setSumOfStudentOnCoCurricularLeave(
			Integer sumOfStudentOnCoCurricularLeave) {
		this.sumOfStudentOnCoCurricularLeave = sumOfStudentOnCoCurricularLeave;
	}

	public Integer getSumOfStudentOnLeave() {
		return sumOfStudentOnLeave;
	}

	public void setSumOfStudentOnLeave(Integer sumOfStudentOnLeave) {
		this.sumOfStudentOnLeave = sumOfStudentOnLeave;
	}

	public Integer getTotalClassHourHeld() {
		return totalClassHourHeld;
	}

	public void setTotalClassHourHeld(Integer totalClassHourHeld) {
		this.totalClassHourHeld = totalClassHourHeld;
	}

}
