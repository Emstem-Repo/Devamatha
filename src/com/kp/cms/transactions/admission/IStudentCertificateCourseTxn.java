package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.CurriculumSchemeSubject;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCertificateCourse;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.admin.SubjectGroupSubjects;
import com.kp.cms.to.admission.StudentCertificateCourseTO;

public interface IStudentCertificateCourseTxn {
	public List<CertificateCourse> getMandatoryCertificateCourses(int programTypeId, int programId, String stream)throws Exception;
	public List<CertificateCourse> getOptionalCertificateCourses(int programTypeId, int programId, String stream)throws Exception;
	public Program getProgramByStudentId(int studentId)throws Exception;
	public boolean addStudentCertificateCourse(StudentCertificateCourse certificateCourse) throws Exception;
	public Integer getSchemeNoByStudentId(int studentId)throws Exception;
	public List<CertificateCourseTeacher> getCertificateCourseTeacher(int courseId)throws Exception;
	public Double getFeeAmount(int courseId)throws Exception;
	public Student getStudentDetails(int studentId)throws Exception;
	public List<Integer> getStudentCertificateCourseDetails(int studentId)throws Exception;
	public boolean isAppliedForSemester(int studentId, int schemeNo)throws Exception;
	public Map<Integer, Integer> getSubjectMap() throws Exception;
	public Integer getAcademicYear(int courseId, int schemeNo, int appliedYear)throws Exception;
	public List<StudentCertificateCourse> getAppliedCourses(int studentId)throws Exception;
	public String getCertificateCourseStudentId(String registerNo)throws Exception;
	public Long studentCertificateCourseCount(StudentCertificateCourse certificateCourse) throws Exception;
	public Integer getCertificateCourseMaxIntake(StudentCertificateCourse certificateCourse) throws Exception;
	public List getDataForQuery(String dataQuery) throws Exception;
	public List<StudentCertificateCourse> getStudentCertificateListForCancallation(String query) throws Exception;
	public boolean updateCertificateCourse(List<StudentCertificateCourseTO> studentCertificateCourse,String userId) throws Exception;
	public List<Object[]> getCourseName(String dataQuery) throws Exception;
	public List<Object[]> getCourseData(String dataQuery) throws Exception;
	public List<Object[]> getCourseCodeName(String dataQuery) throws Exception;
	public List getSubjectGroupNameExist(String dataQuery) throws Exception;
	public void savesubjectGroupName(SubjectGroup sb) throws Exception;
	public void savesubgrpSub(SubjectGroupSubjects sbs) throws Exception;
	public List getSubject(String subQuery) throws Exception;
	public void curriculumSchemeSubject(CurriculumSchemeSubject css)throws Exception;
	public void applicantSubjectGroup(ApplicantSubjectGroup asg)throws Exception;
	public List getDuplicateInCurriculum(String dataQuery) throws Exception;
	public List getDuplicateInApplicantSubGrp(String dataQuery) throws Exception;
	public List getDuplicateSubGrpInSGS(String dataQuery) throws Exception;
	

}
