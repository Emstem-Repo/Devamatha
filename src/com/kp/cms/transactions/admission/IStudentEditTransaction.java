package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.bo.exam.ExamStudentDetentionDetailsBO;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.bo.exam.StudentOldRegisterNumber;
import com.kp.cms.bo.exam.StudentPreviousClassHistory;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;
import com.kp.cms.forms.admission.StudentEditForm;
/**
 * 
 *
 * Interface for StudentEditTransactionImpl
 * 
 */
public interface IStudentEditTransaction {

	StringBuffer getSerchedStudentsQuery(String academicYear, String applicationNo,
			String regNo, String rollNo, int courseId, int programId,
			String firstName, int semister, int progtypeId) throws Exception;
	Student getApplicantDetails(String applicationNumber, int applicationYear) throws Exception;
	Student getApplicantDetailsForCertificate(String registerNo) throws Exception;
	boolean updateCompleteApplication(Student admBO, List<ExamStudentBioDataBO> stu,
			ExamStudentDetentionRejoinDetails detention,
			ExamStudentDetentionRejoinDetails discontinue,
			ExamStudentDetentionRejoinDetails rejoin, String isDetain, String isDiscontinued, String modifiedBy, List<ExamStudentBioDataBO> deletingList)throws Exception;
	List<ClassSchemewise> getClassSchemeForStudent(int courseid, int year)throws Exception;
	boolean createNewStudent(Student admBO) throws Exception;
	boolean checkRegNoUnique(String regNo, Integer appliedYear)throws Exception;
	boolean deleteStudent(int studId) throws Exception;
	List<SubjectGroup> getSubjectGroupList(int courseId, Integer year) throws Exception;
	List<ClassSchemewise> getClassSchemeByCourseId(int semNo, Integer year,int courseid) throws Exception;
	List<Object[]> getYearandTermNo(int courseid, Integer year) throws Exception;
	List<Integer> getSerchedStudentsPhotoList(String academicYear,
			String applicationNo, String regNo, String rollNo, int courseid,
			int progID, String firstName, int semNo, int progtypeId) throws Exception;
	boolean checkStudentDetailsExists(ExamStudentDetentionDetailsBO stu) throws Exception;
	public ExamStudentDetentionRejoinDetails getDetentionId(int studentId) throws Exception;
	public List<ExamStudentDetentionRejoinDetails> studentDetainHistory(int studentId, boolean detention) throws Exception;
	public List<ExamStudentDetentionRejoinDetails> studentRejoinHistory(int studentId) throws Exception;
	
	List<SubjectGroup> getSubjectGroupListBySemester(int courseId, Integer year,int semesterNo) throws Exception;
	List<ExamStudentSubGrpHistoryBO> getSubHistoryByStudentId(int studentId,int schemeNo,StudentEditForm stForm) throws Exception;
//	List<ExamStudentSubGrpHistoryBO> getSubHistoryByStudentId1(int studentId,int schemeNo) throws Exception;
	boolean updateHistoryDetails(List<ExamStudentSubGrpHistoryBO> stu,List<ExamStudentSubGrpHistoryBO> deleteList,ExamStudentPreviousClassDetailsBO previousClassBo) throws Exception;
	ExamStudentPreviousClassDetailsBO getPreviousClassHistory(int studentId,int scheme) throws Exception;
	Classes getSemesterNoByClassId(int parseInt) throws Exception;
	int getYearForClassId(int classId) throws Exception;
	public int getSemesterNoByClassSchemeId(int classSchemeId) throws Exception;
	public List<ExamStudentPreviousClassDetailsBO> viewHistory_ClassGroupDetails(int studentId);
	boolean checkStudentIsActive(StudentEditForm stForm) throws Exception;
	List<String> getProgramName() throws Exception;
	List<Student> getSerchedStudents(StringBuffer query)throws Exception;
	List<ExamStudentDetentionRejoinDetails> getExamStudentDetentionRejoinDetails(Integer year);
	List<StudentPreviousClassHistory> getStudentClassHistoryList(String regNo) throws Exception;
	void addStudentOldRegisterNumbers(List<StudentOldRegisterNumber> oldDetails, int studentId, String regNo) throws Exception;
	Student getStudent(String regNo) throws Exception;
	void removeStudentOldRegisterNo(StudentEditForm admForm) throws Exception;
	void removeStudentmarksFromMarksEntry(StudentEditForm admForm, List<Integer> classIds) throws Exception;
	List<Integer> getClassesBetweenCurrentToRejoin(StudentEditForm admForm) throws Exception;
	void removeStudentInternalMarks(StudentEditForm admForm,List<Integer> classIds) throws Exception;
	void removeStudentmarksFromMarksEntryCorrection(StudentEditForm admForm,List<Integer> classIds) throws Exception;
	ExamStudentDetentionRejoinDetails checkStudentRejoinDetails(int studentId) throws Exception;
	
}
