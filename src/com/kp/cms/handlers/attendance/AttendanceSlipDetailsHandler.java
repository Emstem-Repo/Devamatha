package com.kp.cms.handlers.attendance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.attendance.AttendanceEntryAction;
import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceEntryForm;
import com.kp.cms.helpers.attendance.AttendanceSlipDetailsHelper;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.transactions.attandance.AttendanceSlipDetailsTransactionImpl;
import com.kp.cms.transactions.attandance.IAttendanceSlipDetailsTransaction;
public class AttendanceSlipDetailsHandler {
	
	private static final Log log = LogFactory.getLog(AttendanceEntryAction.class);
	private static AttendanceSlipDetailsHandler attendanceSlipDetailsHandler=new AttendanceSlipDetailsHandler();

	public static AttendanceSlipDetailsHandler getInstance() {
		// TODO Auto-generated method stub
		return attendanceSlipDetailsHandler;
	}

	public Map<String, Map<String,List<AttendanceTO>>> getAttendanceSlipDetails(AttendanceEntryForm attendanceEntryForm, HttpServletRequest request)throws Exception {
		// TODO Auto-generated method stub
		log.info("Inside of getAttendanceSlipDetails of AttendanceSlipDetailsHandler");
		IAttendanceSlipDetailsTransaction iAttendanceSlipDetailsTransaction=new AttendanceSlipDetailsTransactionImpl();
		String searchCriteria=AttendanceSlipDetailsHelper.getInstance().getSlipDetailsSearchQuery(attendanceEntryForm);//getting AttendaceSlipDetails Query
		List<Attendance> attendaceSlipList=iAttendanceSlipDetailsTransaction.getListOfSlipDetails(searchCriteria);//getting AttendaceSlipDetails
		String periodQuery=AttendanceSlipDetailsHelper.getInstance().getPeriods(attendanceEntryForm);//getting periods Query
		List<String> periodList=iAttendanceSlipDetailsTransaction.getPeriods(periodQuery);//getting period details
		Map<String, Map<String,List<AttendanceTO>>> listOfSlipDetails=AttendanceSlipDetailsHelper.getInstance().convertBotoTo(attendaceSlipList,periodList,attendanceEntryForm,request);
		log.info("Leaving  getAttendanceSlipDetails of AttendanceSlipDetailsHandler");
		return listOfSlipDetails;
	}

	public List<String> getPeriodList(AttendanceEntryForm attendanceEntryForm, HttpServletRequest request) throws ApplicationException {
		// TODO Auto-generated method stub
		log.info("Start getPeriodList of AttendanceSlipDetailsHandler");
		IAttendanceSlipDetailsTransaction iAttendanceSlipDetailsTransaction=new AttendanceSlipDetailsTransactionImpl();
		String listPeriods=AttendanceSlipDetailsHelper.getInstance().getPeriods(attendanceEntryForm);
		List<String> periodList=iAttendanceSlipDetailsTransaction.getPeriods(listPeriods);
		log.info("End of getPeriodList of AttendanceSlipDetailsHandler");
		return periodList;
	}

	/*public Map<String, String> getSubjectDetails() {
		// TODO Auto-generated method stub
		Map<String,String> subjectMap=AttendanceSlipDetailsHelper.subjectMap;
		return subjectMap;
	}*/
	
	public String getClassNames(AttendanceEntryForm attendanceEntryForm) throws Exception{
		IAttendanceSlipDetailsTransaction iAttendanceSlipDetailsTransaction=new AttendanceSlipDetailsTransactionImpl();
		String classesQuery=AttendanceSlipDetailsHelper.getClassNames(attendanceEntryForm);
		List<String> classNames=iAttendanceSlipDetailsTransaction.getClassNames(classesQuery);
		String className=AttendanceSlipDetailsHelper.getClasses(classNames);
		return className;
	}
}
