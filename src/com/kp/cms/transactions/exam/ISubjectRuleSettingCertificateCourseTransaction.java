package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamSubjectRuleSettingsBO;
import com.kp.cms.bo.exam.SubjectRuleSettings;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;

public interface ISubjectRuleSettingCertificateCourseTransaction {
	
	List<CertificateCourse> getCertificateCourseList(SubjectRuleSettingsForm objform) throws Exception;
	
	List<SubjectGroup> getSubjectGroupsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception;
	
	boolean addAll(List<SubjectRuleSettings> bos) throws Exception;

	List<ExamSubjectRuleSettingsBO> getSubjectrulesIs(String academicYear,String courseId, String schemeNo, String subjectId) throws Exception;

	List<Subject> getSubjectsByCourseYearSemester(String academicYear,String course, String subject, String subjectId) throws Exception;

}
