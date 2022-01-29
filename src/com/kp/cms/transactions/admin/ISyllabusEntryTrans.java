package com.kp.cms.transactions.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.to.admin.SyllabusEntryGeneralTo;

public interface ISyllabusEntryTrans {

	boolean save(SyllabusEntry syllabusEntry)throws Exception;

	SyllabusEntry getRecord(int id)throws Exception;

	boolean update(SyllabusEntry syllabusEntry)throws Exception;

	Subject getSubject(String subjectId)throws Exception;

	SyllabusEntry getSyllabusEntryStatus(int year, int subjectId)throws Exception;
	
	public Map<Integer,String> getSubjectMap(int deptId, int schemeNo, int year)throws Exception;

	List<SyllabusEntry> getSyllabusEntries(List<Integer> subjectIds, String year)throws Exception;
	
	Map<Integer,Map<String,SyllabusEntryGeneralTo>> getSubjects(String query, List<Integer> courseIds)throws Exception;

	List<Integer> getcourseIdsFormCourseDept(String departmentId)throws Exception;

	Map<Integer, Map<String, SyllabusEntryGeneralTo>> getlanguageSubjects(
			String query) throws Exception;

}
