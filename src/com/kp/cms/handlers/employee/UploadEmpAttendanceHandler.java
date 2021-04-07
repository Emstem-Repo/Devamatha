package com.kp.cms.handlers.employee;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.helpers.employee.UploadEmpAttendanceHelper;
import com.kp.cms.to.employee.UploadEmpAttendanceTO;
import com.kp.cms.transactions.employee.IUploadEmpAttendanceTxn;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.employee.UploadEmpAttendanceTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class UploadEmpAttendanceHandler {
	/**
	 * Singleton object of UploadEmpAttendanceHandler
	 */
	private static volatile UploadEmpAttendanceHandler uploadEmpAttendanceHandler = null;
	private static final Log log = LogFactory.getLog(UploadEmpAttendanceHandler.class);
	private UploadEmpAttendanceHandler() {
		
	}
	/**
	 * return singleton object of UploadEmpAttendanceHandler.
	 * @return
	 */
	public static UploadEmpAttendanceHandler getInstance() {
		if (uploadEmpAttendanceHandler == null) {
			uploadEmpAttendanceHandler = new UploadEmpAttendanceHandler();
		}
		return uploadEmpAttendanceHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getAllEmployees() throws Exception {
		String query="select e.fingerPrintId, e.id, e.emptype.id from Employee e where e.isActive=1 and e.fingerPrintId is not null";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List list=transaction.getDataForQuery(query);
		return UploadEmpAttendanceHelper.getInstance().convertBotoMap(list);
	}
	/**
	 * @param results
	 * @param userId
	 * @return
	 */
	public boolean addUploadEmpAttendance(List<UploadEmpAttendanceTO> results, String userId) throws Exception{
		IUploadEmpAttendanceTxn txn=UploadEmpAttendanceTransactionImpl.getInstance();
		return txn.saveUploadEmpAttendance(results,userId);
	}
}
