package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.SyllabusEntry;


public interface ISyllabusUploadTransaction {

	public Integer getSubejectByYearAndSubjectCode(String academicYear, String subjectCode);

	public boolean saveSyllabusEntries(List<SyllabusEntry> entriesList);

	public List<SyllabusEntry> getSyllabusEntryBySubjectIdList(List<Integer> subjectIdlist);

}
