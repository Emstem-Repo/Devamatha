package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.SubjectMarksTO;

public interface ISupplementaryDataCreationTransaction {

	List getDataByQuery(String query)throws Exception;

	Map<Integer, SubjectMarksTO> getMinMarksMap(ClassesTO classTO)throws Exception;

	List<Integer> getExcludedFromResultSubjects(int courseId, Integer termNo, Integer year)throws Exception;

	List<Integer> getExcludedFromTotResultSubjects(int courseId, Integer termNo, Integer year)throws Exception;

	boolean deleteOldRecords(int id, int examId)throws Exception;

	List<Object[]> getDataByStudentIdAndClassId(int studentId, int classId, List<Integer> subjectIdList)throws Exception;

	Map<Integer, String> getSubjectSections(int courseId, Integer termNo, Integer year)throws Exception;

	boolean saveSupplimentryStudentsData( List<StudentSupplementaryImprovementApplication> boList)throws Exception;

}
