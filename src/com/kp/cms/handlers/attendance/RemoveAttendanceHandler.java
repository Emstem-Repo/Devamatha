package com.kp.cms.handlers.attendance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.forms.attendance.RemoveAttendanceForm;
import com.kp.cms.helpers.attendance.RemoveAttendanceHelper;
import com.kp.cms.to.attendance.AttendanceReEntryTo;
import com.kp.cms.transactions.attandance.IRemoveAttendanceTransaction;
import com.kp.cms.transactionsimpl.attendance.RemoveAttendanceTransactionImpl;

public class RemoveAttendanceHandler {
	
	IRemoveAttendanceTransaction transaction=RemoveAttendanceTransactionImpl.getInstance();
	
	/**
	 * Singleton object of RemoveAttendanceHandler
	 */
	private static volatile RemoveAttendanceHandler removeAttendanceHandler = null;
	private static final Log log = LogFactory.getLog(RemoveAttendanceHandler.class);
	private RemoveAttendanceHandler() {
		
	}
	/**
	 * return singleton object of RemoveAttendanceHandler.
	 * @return
	 */
	public static RemoveAttendanceHandler getInstance() {
		if (removeAttendanceHandler == null) {
			removeAttendanceHandler = new RemoveAttendanceHandler();
		}
		return removeAttendanceHandler;
	}
	/**
	 * @param removeAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public Map<Date, AttendanceReEntryTo> getAttendanceList(RemoveAttendanceForm removeAttendanceForm) throws Exception {
		String query=RemoveAttendanceHelper.getInstance().getAttendanceSearchQuery(removeAttendanceForm);
		List<AttendanceStudent> attStuList=transaction.getAttStudentList(query);
		return RemoveAttendanceHelper.getInstance().convertBosToMap(attStuList);
	}
	/**
	 * @param list
	 * @param removeAttendanceForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveAttendanceReEntry(List<AttendanceReEntryTo> list,RemoveAttendanceForm removeAttendanceForm) throws Exception {
		List<Integer> finalList=RemoveAttendanceHelper.getInstance().getFinalListToRemove(list,removeAttendanceForm);
 		return transaction.saveAttendanceReEntry(finalList,removeAttendanceForm.getUserId());
	}
}
