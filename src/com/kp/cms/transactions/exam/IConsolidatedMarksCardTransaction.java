package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ConsolidateMarksCard;
import com.kp.cms.bo.exam.ConsolidatedMarksCardProgrammePart;
import com.kp.cms.bo.exam.ExamGradeDefinitionBO;
import com.kp.cms.to.exam.NewConsolidatedMarksCardTo;

public interface IConsolidatedMarksCardTransaction {

	Map<Integer, Map<Integer, NewConsolidatedMarksCardTo>> getSemesterWiseDetails( List<Integer> studentIds) throws Exception;

	List<Object[]> getMarksDetails(int studentId, NewConsolidatedMarksCardTo to) throws Exception;

	List getStudentMarks(String consolidateQuery, List<Integer> studentIds) throws Exception;

	boolean saveConsolidateMarks( Map<Integer, Map<String, ConsolidateMarksCard>> boMap,int courseId,int year) throws Exception;

	List<String> getSupplimentaryAppeared(List<Integer> studentIds) throws Exception;
	
	public List<ExamGradeDefinitionBO> getGradeDefenitionsForACourse(int courseId) throws Exception;

	public boolean saveConsolidateMarksProgrammePart(Map<String, ConsolidatedMarksCardProgrammePart> map, int courseId, int year) throws Exception;

	public Integer getExamCountBystudentIdSubjectIdAndClassId(int studentId,int classId,int subjectId,int examId) throws Exception;
}
