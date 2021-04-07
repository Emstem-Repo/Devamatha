package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamSubCoursewiseGradeDefnBO;

public interface IGradeClassDefinitionEntryTransaction {

	public boolean addGradeClassBoList(List<ExamSubCoursewiseGradeDefnBO> gradeClassBoList) throws Exception;
	public boolean deleteExixtingData(Map<Integer, List<Integer>> deleteExistingMap,int academicYear) throws Exception;
	public List<Object[]> getGradeClassDefinitionList() throws Exception;

}
