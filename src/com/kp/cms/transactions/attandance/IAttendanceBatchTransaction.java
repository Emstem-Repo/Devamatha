package com.kp.cms.transactions.attandance;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.forms.attendance.AttendanceBatchForm;

public interface IAttendanceBatchTransaction {

	List<Batch> getBatchsByClassId(String[] classIds, String subjectId,String activityAttendance,boolean checkActive,String batchName) throws Exception;

	boolean deletePracticalBatch(String batchId, String userId) throws Exception;

	boolean reactivePracticalBatch(String batchId, String userId) throws Exception;

	Map<Integer, Integer> getExistsStudentForBatch(String batchId) throws Exception;

	Map<Integer, String> getStudentExistInAnotherBatch(String query) throws Exception;

	boolean savePracticalBatch(AttendanceBatchForm attendanceBatchForm) throws Exception;

}
