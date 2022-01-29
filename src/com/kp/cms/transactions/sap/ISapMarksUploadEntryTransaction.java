package com.kp.cms.transactions.sap;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.forms.sap.SapMarksUploadEntryForm;
import com.kp.cms.to.sap.SapMarksUploadEntryTO;

public interface ISapMarksUploadEntryTransaction {
	public Student getStudentDetailsByRegNo(String regNo) throws Exception;
	public List<MarksEntryDetails> checkSapMarksAlreadyExists(List<SapMarksUploadEntryTO> marksUploadEntryTOList,SapMarksUploadEntryForm marksUploadEntryForm) throws Exception;
	public boolean saveSapMarksUploaded(List<SapMarksUploadEntryTO> marksUploadEntryTOList,SapMarksUploadEntryForm marksEntryForm) throws Exception;
	public Object[] getExamSettingsForCheckingMarks() throws Exception;
	List<Integer> getStudentCourseDetailsByExamId(String examId) throws Exception;

}
