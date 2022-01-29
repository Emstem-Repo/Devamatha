package com.kp.cms.transactions.exam;

import java.util.List;
import java.util.Map;

public interface IUploadStudentSubjectHistoryTransaction {

	boolean addUploadedDate(Map<String, List<Integer>> resultMap, String user) throws Exception;

}
