package com.kp.cms.transactions.exam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamRevaluationApplicationNew;
import com.kp.cms.bo.exam.ExamStudentCCPA;
import com.kp.cms.bo.exam.ModerationMarksEntryBo;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.NewExamMarksEntryForm;
import com.kp.cms.forms.exam.NewSecuredMarksEntryForm;
import com.kp.cms.forms.exam.NewSupplementaryImpApplicationForm;
import com.kp.cms.to.exam.MarksDetailsTO;

public interface INewExamMarksEntryTransaction {

	List getDataForQuery(String query) throws Exception;

	boolean saveMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	List<String> getExamAbscentCode()throws Exception;

	Double getMaxMarkOfSubject(NewSecuredMarksEntryForm newSecuredMarksEntryForm)throws Exception;

	BigDecimal getExamDiffPercentage() throws Exception;

	Double getMaxMarkOfSubject(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	Map<String, String> getOldRegMap(List<Integer> schemeList) throws Exception;

	Map<String, byte[]> getStudentPhtos(List<Integer> studentIds,boolean isReg) throws Exception;

	ExamBlockUnblockHallTicketBO getExamBlockUnblockHallTicket( Integer studentId, int examId, int classId,String hallOrMark) throws Exception;
	
	Map<Integer, byte[]> getStudentPhtosMap(List<Integer> studentIds,boolean isReg) throws Exception;
	
	List<Integer> getDataForQueryClasses(String query) throws Exception;

	boolean checkAggregateResultClassWise(int sid) throws Exception;

	Map<Integer, String> getSubjects(String examId, String subCode,
			String examType, String year) throws Exception;
	
	Boolean getExtendedDate(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception;
	
	List<Integer> getExemptionStudentsListBySubjectId(String subjectId,String examId) throws Exception;

	//raghu added from mounts
	
	String getApplicationNumber(int studentId) throws Exception;
	
	List<StudentSupplementaryImprovementApplication> getDataForSQLQuery(String query) throws Exception;
	
	public List<Object> checkRevaluationAppAvailable(List<Integer> classIds) throws Exception;
	
	public Boolean getExtendedDateForRegular(int classId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception;
	
	List<StudentFinalMarkDetails> getDataForSQLQueryForRevaluation(String query) throws Exception;

	public Boolean getExtendedDateForRevaluation(int classId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception;
	
	List<ExamRevaluationApplicationNew> getDataForForRevaluationEdit(String query) throws Exception;

	public List getDataForQuery(String query, List studList) throws Exception ;
	
	public boolean saveRevaluationMarks(List<ModerationMarksEntryBo> boList) throws Exception;
	
	public ExamStudentCCPA getCCPAdetails(int studentId) throws Exception;
	
	public List getDataForQuerySQL(String query) throws Exception;
	
	public String getOpenCourseName(int studentId,int subjectTypeid,int streamId) throws Exception;
	
	public Boolean getExtendedDateInternalRedo(int studentId, NewSupplementaryImpApplicationForm newSupplementaryImpApplicationForm) throws Exception;
	
	public boolean isCondonationPaid(int studentId , int classId ) throws Exception;

	List getQueryForAlreadyEnteredMarksForAllInternal(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	public Map<Integer,Double> getMaxMarkOfSubjectForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	boolean isCurrentDateValidForAllInternalMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	boolean saveMarksForAllInternals(NewExamMarksEntryForm newExamMarksEntryForm) throws Exception;

	List getAttendanceMarks(NewExamMarksEntryForm newExamMarksEntryForm) throws ApplicationException;
	
	
}
