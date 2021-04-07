package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.GenerateProcess;

public interface IDownloadStudentInterviewStatusTransaction {

	List<GenerateProcess> getStudentList(String query) throws Exception;

}
