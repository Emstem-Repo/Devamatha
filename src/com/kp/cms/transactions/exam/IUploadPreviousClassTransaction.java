package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.to.admin.StudentTO;

	public interface IUploadPreviousClassTransaction {

	public	Map<String, StudentTO> getStudentDetails() throws Exception;

	public boolean addUploadedData(List<ExamStudentPreviousClassDetailsBO> studentPreviousClassBOList) throws Exception;

	public Map<String, Integer> getclassMap() throws Exception;
}
