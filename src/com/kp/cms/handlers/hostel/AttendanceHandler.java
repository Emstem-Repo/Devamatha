package com.kp.cms.handlers.hostel;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.bo.admin.HlGroupStudent;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.hostel.AttendanceForm;
import com.kp.cms.helpers.hostel.AttendanceHelper;
import com.kp.cms.to.hostel.HostelGroupStudentTO;
import com.kp.cms.transactions.hostel.IAttendanceTransactions;
import com.kp.cms.transactionsimpl.hostel.AttendanceTransactionImpl;

public class AttendanceHandler {
	public static Log log = LogFactory.getLog(AttendanceHandler.class);
	public static volatile AttendanceHandler attendanceHandler;
	
	public static AttendanceHandler getInstance(){
		if(attendanceHandler == null){
			attendanceHandler = new AttendanceHandler();
			return attendanceHandler;
		}
		return attendanceHandler;
	}
	/**
	 * 
	 * @return list of HostelGroupStudentTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<HostelGroupStudentTO> getStudentDetails(int groupId) throws Exception {
		log.debug("inside getStudentDetails");
		IAttendanceTransactions iHTransactions = AttendanceTransactionImpl.getInstance();
		List<HlGroupStudent> studList = iHTransactions.getStudentDeatilsByGroupId(groupId);
		List<HostelGroupStudentTO> studTOList = AttendanceHelper.getInstance().copyHostelGroupBosToTos(studList); 
		log.debug("leaving getStudentDetails");
		return studTOList;
	}

	/*
	save method
	*/

	public boolean saveAttendance(AttendanceForm attForm) throws DuplicateException, Exception {
		log.debug("inside saveAttendance");
		IAttendanceTransactions iaTransactions =AttendanceTransactionImpl.getInstance();
		boolean isAdded;
		List<HlAttendance> hlAttendanceList = AttendanceHelper.getInstance().populateAttendanceDetails(attForm); 
		isAdded = iaTransactions.addAttendance(hlAttendanceList);
		log.debug("leaving saveAttendance");
		return isAdded;
	}
	
}
