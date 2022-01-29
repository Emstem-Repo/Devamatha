package com.kp.cms.transactions.phd;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.forms.admission.AssignSubjectGroupHistoryForm;
import com.kp.cms.forms.phd.PhdDocumentSubmissionScheduleForm;

public interface IPhdDocumentSubmissionSchedule {

	public List<Object[]> getDocumentSubmissionScheduleList(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

	public List<Object[]> getCoursesByProgramTypes(String programTypeId) throws Exception;

	public List<DocumentDetailsBO> getDocumentDetailsList() throws Exception;

	public boolean addSubCategory(List<PhdDocumentSubmissionSchedule> documentDetails, String mode) throws Exception;

	public List<PhdDocumentSubmissionSchedule> getStudentDetailsById(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

	public DocumentDetailsBO getDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

	public List<DocumentDetailsBO> getDocumentDetailsBo(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;
	
	public List<DocumentDetailsBO> getDocumentList(	PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

	public List<Object[]> getDocumentSubmissionSchedule(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

	public boolean deletePhdDocumentDetails(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm, String mode) throws Exception;

	public List<PhdDocumentSubmissionSchedule> getDocumentSubmissionByReg(PhdDocumentSubmissionScheduleForm objForm) throws Exception;

	public boolean addDocumentList(List<PhdDocumentSubmissionSchedule> documentList) throws Exception;

	public List<PhdDocumentSubmissionSchedule> getPendingDocumentSearch(PhdDocumentSubmissionScheduleForm documentSubmissionScheduleForm) throws Exception;

}
