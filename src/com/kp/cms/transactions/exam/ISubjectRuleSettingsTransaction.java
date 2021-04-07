package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamAssignmentTypeMasterBO;
import com.kp.cms.bo.exam.ExamInternalExamTypeBO;
import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.to.admin.SubjectTO;

public interface ISubjectRuleSettingsTransaction {

	List<ExamSubjectRuleSettingsBO> isDuplicateCheck(String query) throws Exception;

	List<SubjectGroup> getSubjectGroupsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception;

	List<ExamInternalExamTypeBO> getAllExamSubjectRuleSettingsSubInternals()throws Exception;

	List<ExamAssignmentTypeMasterBO> getAllAssignment() throws Exception;

	List<ExamMultipleAnswerScriptMasterBO> getAllMultipleAnswerScript() throws Exception ;

	List<Subject> getSubjectsByCourseYearSemester(String academicYear,String courseId, String schemeNo) throws Exception;

	boolean addAll(List<SubjectRuleSettings> bos) throws Exception;

	List<String> checkExited(String query, String[] courseIds) throws Exception;

	List<Object[]> getSubjectListByQuery(String query) throws Exception;

	SubjectRuleSettings getBOobjectByQuery(String query) throws Exception;

	boolean deleteSubjectRuleSettingsForSubject(SubjectRuleSettingsForm subjectRuleSettingsForm, String query) throws Exception;

	boolean reActivateSubjectRuleSettings(int id) throws Exception;

	boolean deleteCompleteSubjectRuleSettings(SubjectRuleSettingsForm subjectRuleSettingsForm, String query) throws Exception;
	
	public List<String> checkExitedForCopy(String query, String[] courseIds) throws Exception;

	List<String> getSubjectsForCopy(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception;

	boolean copySubjectRuleSettings(List<String> list, String query,SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception;

	String getNamesForBos(String string, String courseIds) throws Exception;
}
