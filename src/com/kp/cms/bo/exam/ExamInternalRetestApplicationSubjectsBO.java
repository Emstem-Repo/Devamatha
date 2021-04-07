package com.kp.cms.bo.exam;

/**
 * Mar 4, 2010 Created By 9Elements Team
 */
import java.util.Set;

@SuppressWarnings("serial")
public class ExamInternalRetestApplicationSubjectsBO extends ExamGenBO {

	private int examInternalRetestApplicationId;
	private int subjectId;
	private String fees;
	private int isTheory;
	private int isPractical;

	// Many-to-one
	private SubjectUtilBO subjectUtilBO;
	private Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset;

	public ExamInternalRetestApplicationSubjectsBO() {
		super();
	}

	public ExamInternalRetestApplicationSubjectsBO(
			int examInternalRetestApplicationId, int subjectId, String fees,
			int isTheory, int isPractical) {
		super();
		this.examInternalRetestApplicationId = examInternalRetestApplicationId;
		this.subjectId = subjectId;
		this.fees = fees;
		this.isTheory = isTheory;
		this.isPractical = isPractical;
	}

	public int getExamInternalRetestApplicationId() {
		return examInternalRetestApplicationId;
	}

	public void setExamInternalRetestApplicationId(
			int examInternalRetestApplicationId) {
		this.examInternalRetestApplicationId = examInternalRetestApplicationId;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	public String getFees() {
		return fees;
	}

	public void setFees(String fees) {
		this.fees = fees;
	}

	public int getIsTheory() {
		return isTheory;
	}

	public void setIsTheory(int isTheory) {
		this.isTheory = isTheory;
	}

	public int getIsPractical() {
		return isPractical;
	}

	public void setIsPractical(int isPractical) {
		this.isPractical = isPractical;
	}

	public SubjectUtilBO getSubjectUtilBO() {
		return subjectUtilBO;
	}

	public void setSubjectUtilBO(SubjectUtilBO subjectUtilBO) {
		this.subjectUtilBO = subjectUtilBO;
	}

	public void setExamInternalRetestApplicationBOset(
			Set<ExamInternalRetestApplicationBO> examInternalRetestApplicationBOset) {
		this.examInternalRetestApplicationBOset = examInternalRetestApplicationBOset;
	}

	public Set<ExamInternalRetestApplicationBO> getExamInternalRetestApplicationBOset() {
		return examInternalRetestApplicationBOset;
	}

}
