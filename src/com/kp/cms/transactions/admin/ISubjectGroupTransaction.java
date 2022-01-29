package com.kp.cms.transactions.admin;

import java.util.List;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.forms.admin.SubjectGroupEntryForm;

/**
 * 
 * @author kshirod.k Transaction Interface for SubjectGroup
 * 
 */
public interface ISubjectGroupTransaction {
	/**
	 * 
	 * @param Returns all the subject names based on the courseId
	 * @return
	 */
	public List<SubjectGroup> getSubjectGroupDetails(int courseId)throws Exception;
	
	public List<SubjectGroup> getSubjectGroup() throws Exception;
	
	public List<Subject> getSubjectDetails() throws Exception;
	
	public SubjectGroup subjectGroupNameExist(int courseId, String subjectGroupName) throws Exception; 
	
	public boolean addSubjectGroupEntry(SubjectGroup subjectGroup) throws Exception;
	
	public SubjectGroup getSubjectGroupEntry(int subjectGroupId) throws Exception;
	
	public boolean deleteSubjectGroupEntry(int id, String userId) throws Exception;

	public boolean updateSubjectGroupEntry(SubjectGroup subjectGroup) throws Exception;
	
	public void reActivateSubjectGroupEntry(SubjectGroupEntryForm subjectGroupEntryForm)throws Exception;

	public List<SubjectGroup> getSubjectGroupDetailsByCourseAndTermNo(int courseId, int year, int semesterNo) throws Exception;

	public List<SubjectGroup> getSubjectGroupList(String programTypeId,	String programId) throws Exception;

}
