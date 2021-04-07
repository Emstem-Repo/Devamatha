package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamGracingSubjectMarksBo;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentFinalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.bo.exam.StudentOverallInternalRedoMarkDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.to.exam.SubjectMarksTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;

public interface INewUpdateProccessTransaction {

	List getDataByQuery(String query) throws Exception;

	boolean saveUpdateProcess(List<StudentSupplementaryImprovementApplication> boList) throws Exception;

	List getDataByStudentAndClassId(int studentId, int classId,List<Integer> subList,int appliedYear) throws Exception;

	Map<String, SubjectMarksTO> getMinMarksMap(ClassesTO to) throws Exception;

	boolean deleteOldRecords(int id, String examId) throws Exception;

	Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettings(int courseId,Integer year, Integer termNo) throws Exception;

	public Map<String, StudentAttendanceTO> getAttendanceForStudent(int classId, int studentId,List<Integer> subIdList , String batchYear,int termNo, String academicYear) throws Exception;

	double getAttendanceMarksForPercentage(int courseId, Integer subId,float percentage) throws Exception;

	Map<Integer, StudentAttendanceTO> getAssignmentMarksForStudent(int classId,int studentId,List<Integer> subIdList) throws Exception;

	List<Integer> getInternalExamId(int examId) throws Exception;

	double getStudentMarksForSubject(Integer subId, int studentId,List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception;

	boolean saveInternalOverAll(List<StudentOverallInternalMarkDetails> boList) throws Exception ;

	Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettingsForRegularOverAll(int courseId, Integer year, Integer termNo) throws Exception;

	boolean saveRegularOverAll(List<StudentFinalMarkDetails> boList) throws Exception;

	Map<Integer, List<StudentMarksTO>> getStudentRegularMarks(int studentId, List<Integer> subjectIdList, int examId, int classId, NewUpdateProccessForm newUpdateProccessForm) throws Exception;

	public boolean isPgStudent(int courseId) throws Exception ;

	List<Integer> getStudentIds(int id, int examId) throws ApplicationException;

	List<Integer> getSubjectIds(int id, int examId,Integer  stdnt) throws ApplicationException;

	Map<Integer, StudentFinalMarkDetails> getgetFinalMarkMap(int id,
			int examId, Integer stdnt) throws ApplicationException;

	List getMaxModeration(int id, int examId, Integer stdnt,
			Integer subId) throws ApplicationException;

	List<Integer> getStudentIdsForGracing(int id, int examId) throws Exception;

	List<Integer> getSubjectIdsForGracing(int id, int examId, Integer stdnt, int batchYear, int programTypeId) throws Exception;

	ExamGracingSubjectMarksBo getGraceMarks(int id, int examId, Integer stdnt,
			Integer subId)throws Exception;

	Map<Integer, StudentOverallInternalMarkDetails> getFinalInternalMarkMap(
			int id, int examId, Integer stdnt) throws Exception;
	public List<MarksEntryDetails> getStudentMarksForSubjectNew(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm) throws Exception;
	public Map<String, Boolean> getSuppImpExamDetails(int classId) throws Exception;

	boolean deleteOldInternalOverAllMarks(int classId, int examId) throws Exception;

	public List<StudentOverallInternalMarkDetails> getInternalMarksForGivenExamAndClass(int examId, int classId) throws Exception;
	
	public StudentOverallInternalMarkDetails getInternalMarksForGivenExamAndClass(int examId, int classId,int studentId, int subId) throws Exception;	
	public boolean updateMarksForInternalRedo(List<StudentOverallInternalRedoMarkDetails> boList) throws Exception;
	
	public boolean updateMarksForInternalRedoDetails(List<StudentOverallInternalRedoMarkDetails> boList) throws Exception;
	
	public List<MarksEntryDetails> getStudentMarksForSubjectNewForRedo(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
	throws Exception;
	
	public double getStudentMarksForSubjectForRedo(Integer subId, int studentId, List<Integer> intExamId, ClassesTO to, String subType,int limit,boolean isInd,int examId,NewUpdateProccessForm newUpdateProccessForm)
	throws Exception;
	
	public Map<Integer, List<StudentOverallInternalMarkDetails>> getStudentOverallMarks(
			Map<Integer, List<Integer>> stuMapWithSubjects, int classId) throws Exception;
	
	public boolean updateOverAllInternalmarksDetails(List<StudentOverallInternalMarkDetails> boList) throws Exception;
	
	public List<StudentOverallInternalMarkDetails> getInternalMarksForGivenExamClassAndStudent(int examId, int classId, int studentId) throws Exception;

	public boolean saveRegularOverAllAfterRevaluation(List<StudentFinalMarkDetails> boList) throws Exception;
	
	public StudentFinalMarkDetails getRegularMarksForComparison(int classId, int studentId, int subjectId) throws Exception;
}
