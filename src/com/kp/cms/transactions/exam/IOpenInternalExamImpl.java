package com.kp.cms.transactions.exam;

import java.util.List;

import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.bo.exam.OpenInternalMark;

public interface IOpenInternalExamImpl {
	public boolean saveExam(OpenInternalMark boList,String mode) throws Exception;
	public List<OpenInternalMark> getExamList() throws Exception;
	public List<ProgramType> getProgramTypeList() throws Exception;
}
