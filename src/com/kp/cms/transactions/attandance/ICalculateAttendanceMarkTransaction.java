package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentAttendanceMarksCjcBO;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;

public interface ICalculateAttendanceMarkTransaction {
	List getDataByQuery(String query) throws Exception;
	boolean saveUpdateProcess(List<StudentSupplementaryImprovementApplication> boList) throws Exception;
	Map<String, StudentAttendanceTO> getAttendanceForStudent(int classId, int studentId,List<Integer> subIdList) throws Exception;
	double getAttendanceMarksForPercentage(int courseId, Integer subId,float percentage) throws Exception;
	boolean deleteOldRecords(int id, String examId) throws Exception;
	boolean saveAttendanceMarks(List<ExamStudentAttendanceMarksCjcBO> boList) throws Exception ;
	Map<Integer, SubjectRuleSettingsTO> getSubjectRuleSettings(int courseId,Integer year, Integer termNo) throws Exception;

}
