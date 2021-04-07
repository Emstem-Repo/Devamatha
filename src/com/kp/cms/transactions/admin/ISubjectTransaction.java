package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.forms.admin.SubjectEntryForm;

public interface ISubjectTransaction {
	public List<Subject> getSubjects(String schemeNo)throws Exception;
	public Subject existanceCheck(String code,String subjectName, String subType)throws Exception ;
	public boolean addSubject(Subject subject)throws Exception;
	public Subject loadSubject(Subject subject) throws Exception;
	public boolean updateSubject(Subject subject)throws Exception;
	public boolean reActivateSubjectEntry(String code, String userId, SubjectEntryForm subjectEntryForm) throws Exception;
	public int getYear(int id);
	Map<String, String> getDepartmentMap()throws Exception;
	public Map<Integer, String> getEligibleCourseMap() throws Exception;
	public List<SubjectCodeGroup> getSubjectCodeGroupMap()throws Exception;
	public List<String> getSubjectCodes()throws Exception;
	public List<String> getSubjectNameList()throws Exception;
	public Subject subjectCodeDuplicated(String code) throws Exception;
}
