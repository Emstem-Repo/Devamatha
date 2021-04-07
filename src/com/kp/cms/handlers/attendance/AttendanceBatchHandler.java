package com.kp.cms.handlers.attendance;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Batch;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.attendance.AttendanceBatchForm;
import com.kp.cms.helpers.attendance.AttendanceBatchHelper;
import com.kp.cms.to.attendance.CreatePracticalBatchTO;
import com.kp.cms.transactions.attandance.IAttendanceBatchTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.attendance.AttendanceBatchTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class AttendanceBatchHandler {
	/**
	 * Singleton object of AttendanceBatchHandler
	 */
	private static volatile AttendanceBatchHandler attendanceBatchHandler = null;
	private static final Log log = LogFactory.getLog(AttendanceBatchHandler.class);
	private AttendanceBatchHandler() {
		
	}
	/**
	 * return singleton object of AttendanceBatchHandler.
	 * @return
	 */
	public static AttendanceBatchHandler getInstance() {
		if (attendanceBatchHandler == null) {
			attendanceBatchHandler = new AttendanceBatchHandler();
		}
		return attendanceBatchHandler;
	}
	/**
	 * @param classIds
	 * @param subjectId
	 * @param activityAttendance
	 * @return
	 * @throws Exception
	 */
	public List<CreatePracticalBatchTO> getPracticalBatchDetailsBySubjectClass(String[] classIds, String subjectId, String activityAttendance) throws Exception {
		IAttendanceBatchTransaction transaction=AttendanceBatchTransactionImpl.getInstance();
		List<Batch> allBatchBODetailsList =transaction.getBatchsByClassId(classIds,subjectId,activityAttendance,true,"");
		Set<Integer> classesIdsSet = new HashSet<Integer>();
		for (int i = 0; i < classIds.length; i++) {
			classesIdsSet.add(Integer.parseInt(classIds[i]));
		}
		return AttendanceBatchHelper.getInstance().copyAllBatchBOToTO(allBatchBODetailsList,classesIdsSet);
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public List<Batch> checkAlreadyExists(AttendanceBatchForm attendanceBatchForm) throws Exception {
		IAttendanceBatchTransaction transaction=AttendanceBatchTransactionImpl.getInstance();
		List<Batch> existBatch =transaction.getBatchsByClassId(attendanceBatchForm.getClassesId(),attendanceBatchForm.getSubjectId(),attendanceBatchForm.getActivityAttendance(),false,attendanceBatchForm.getBatchName());
		return existBatch;
	}
	/**
	 * @param batchId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deletePracticalBatch(String batchId, String userId) throws Exception {
		IAttendanceBatchTransaction transaction=AttendanceBatchTransactionImpl.getInstance();
		return transaction.deletePracticalBatch(batchId,userId);
	}
	/**
	 * @param batchId
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean reactivePracticalBatch(String batchId, String userId) throws Exception {
		IAttendanceBatchTransaction transaction=AttendanceBatchTransactionImpl.getInstance();
		return transaction.reactivePracticalBatch(batchId,userId);
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public List<CreatePracticalBatchTO> getStudentList(AttendanceBatchForm attendanceBatchForm) throws Exception {
		String query=AttendanceBatchHelper.getInstance().getStudentsByClassQuery(attendanceBatchForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Student> studentBoList=transaction.getDataForQuery(query);
		return AttendanceBatchHelper.getInstance().convertBotoTO(studentBoList,attendanceBatchForm,new HashMap<Integer, Integer>());
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 */
	public List<CreatePracticalBatchTO> getStudentListForEdit(AttendanceBatchForm attendanceBatchForm) throws Exception{
		String query=AttendanceBatchHelper.getInstance().getStudentsByClassQueryForEdit(attendanceBatchForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Student> studentBoList=transaction.getDataForQuery(query);
		IAttendanceBatchTransaction transaction1=AttendanceBatchTransactionImpl.getInstance();
		Map<Integer,Integer> existsMap=transaction1.getExistsStudentForBatch(attendanceBatchForm.getBatchId());
		return AttendanceBatchHelper.getInstance().convertBotoTO(studentBoList,attendanceBatchForm,existsMap);
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public boolean savePracticalBatch(AttendanceBatchForm attendanceBatchForm) throws Exception {
		IAttendanceBatchTransaction transaction1=AttendanceBatchTransactionImpl.getInstance();
		return transaction1.savePracticalBatch(attendanceBatchForm);
	}
	/**
	 * @param attendanceBatchForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getStudentExistInAnotherBatch(AttendanceBatchForm attendanceBatchForm) throws Exception {
		String query=AttendanceBatchHelper.getInstance().getStudentsExistInAnotherBatch(attendanceBatchForm);
		IAttendanceBatchTransaction transaction1=AttendanceBatchTransactionImpl.getInstance();
		return transaction1.getStudentExistInAnotherBatch(query);
	}
}
