package com.kp.cms.bo.exam;

/**
 * Dec 25, 2009 Created By 9Elements Team
 */
import java.math.BigDecimal;
import java.util.Date;

public class ExamSettingsBO extends ExamGenBO {

	private String absentCodeMarkEntry;
	private String notProcedCodeMarkEntry;
	private String securedMarkEntryBy;
	private BigDecimal maxAllwdDiffPrcntgMultiEvaluator;
	private BigDecimal gradePointForFail;
	private String gradeForFail;
	private String withHeldCodeMarkEntry;

	public ExamSettingsBO() {
		super();
	}

	public ExamSettingsBO(String absentCodeMarkEntry,
			String notProcedCodeMarkEntry, String withHeldCodeMarkEntry,String securedMarkEntryBy,
			BigDecimal maxAllwdDiffPrcntgMultiEvaluator,
			BigDecimal gradePointForFail, String gradeForFail, String userId) {
		super();
		this.absentCodeMarkEntry = absentCodeMarkEntry;
		this.gradeForFail = gradeForFail;
		this.gradePointForFail = gradePointForFail;
		this.maxAllwdDiffPrcntgMultiEvaluator = maxAllwdDiffPrcntgMultiEvaluator;
		this.notProcedCodeMarkEntry = notProcedCodeMarkEntry;
		this.withHeldCodeMarkEntry = withHeldCodeMarkEntry;
		this.securedMarkEntryBy = securedMarkEntryBy;
		this.createdBy = userId;
		this.createdDate = new Date();
		this.modifiedBy = userId;
		this.lastModifiedDate = new Date();
		this.isActive = true;
	}

	public String getAbsentCodeMarkEntry() {
		return absentCodeMarkEntry;
	}

	public void setAbsentCodeMarkEntry(String absentCodeMarkEntry) {
		this.absentCodeMarkEntry = absentCodeMarkEntry;
	}

	public String getNotProcedCodeMarkEntry() {
		return notProcedCodeMarkEntry;
	}

	public void setNotProcedCodeMarkEntry(String notProcedCodeMarkEntry) {
		this.notProcedCodeMarkEntry = notProcedCodeMarkEntry;
	}

	public String getSecuredMarkEntryBy() {
		return securedMarkEntryBy;
	}

	public void setSecuredMarkEntryBy(String securedMarkEntryBy) {
		this.securedMarkEntryBy = securedMarkEntryBy;
	}

	public BigDecimal getMaxAllwdDiffPrcntgMultiEvaluator() {
		return maxAllwdDiffPrcntgMultiEvaluator;
	}

	public void setMaxAllwdDiffPrcntgMultiEvaluator(
			BigDecimal maxAllwdDiffPrcntgMultiEvaluator) {
		this.maxAllwdDiffPrcntgMultiEvaluator = maxAllwdDiffPrcntgMultiEvaluator;
	}

	public BigDecimal getGradePointForFail() {
		return gradePointForFail;
	}

	public void setGradePointForFail(BigDecimal gradePointForFail) {
		this.gradePointForFail = gradePointForFail;
	}

	public String getGradeForFail() {
		return gradeForFail;
	}

	public void setGradeForFail(String gradeForFail) {
		this.gradeForFail = gradeForFail;
	}

	public String getWithHeldCodeMarkEntry() {
		return withHeldCodeMarkEntry;
	}

	public void setWithHeldCodeMarkEntry(String withHeldCodeMarkEntry) {
		this.withHeldCodeMarkEntry = withHeldCodeMarkEntry;
	}
}
