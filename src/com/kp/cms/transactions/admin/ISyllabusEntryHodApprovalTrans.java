package com.kp.cms.transactions.admin;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.SyllabusEntryProgramDetails;
import com.kp.cms.forms.admin.SyllabusEntryHodApprovalForm;

public interface ISyllabusEntryHodApprovalTrans {

	boolean checkForSyllabusEntryOpen(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
			Date convertStringToSQLDate)throws Exception;

	Map<String, Integer> getCourseMap()throws Exception;

	List<SyllabusEntry> getSyllabusEntriesByIds(List<Integer> approveList)throws Exception;

	boolean updateByHod(List<SyllabusEntry> list)throws Exception;

	boolean saveProgramDetails(
			SyllabusEntryProgramDetails syllabusEntryProgramDetails)throws Exception;

	List<SyllabusEntryProgramDetails> getProgramDetailsByBatch(String batchYear)throws Exception;

	List<Integer> getSubjectsByCOurse(int courseId, int batch)throws Exception;

	SyllabusEntryProgramDetails getProgramDetailsByBatchAndCourse(int course,
			int batch)throws Exception;

}
