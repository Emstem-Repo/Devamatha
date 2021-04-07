package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamStudentBioDataBO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamSpecializationTO;

public interface IUploadAddlBiodataInfoTrans {

	public Map<String, StudentTO> getStudentDetails() throws Exception;

	public Map<String, ExamSpecializationTO> getSpecializationDetails() throws Exception;

	public boolean addUploadedData(List<ExamStudentBioDataBO> studentBOList) throws Exception;

}
