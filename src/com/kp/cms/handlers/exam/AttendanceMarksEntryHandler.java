package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.exam.ExamCourseUtilBO;
import com.kp.cms.bo.exam.ExamSubCoursewiseAttendanceMarksBO;
import com.kp.cms.forms.exam.AttendanceMarksEntryForm;
import com.kp.cms.helpers.exam.AttendanceMarksEntryHelper;
import com.kp.cms.to.exam.AttendanceMarkAndPercentageTO;
import com.kp.cms.to.exam.ExamCourseUtilTO;
import com.kp.cms.transactions.exam.IAttendanceMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.AttendanceMarksEntryTransactionImpl;

public class AttendanceMarksEntryHandler {
	private static volatile AttendanceMarksEntryHandler attendanceMarksEntryHandler= null;
	private static final Log log = LogFactory.getLog(AttendanceMarksEntryHandler.class);
	public static AttendanceMarksEntryHandler getInstance()
	{
		if(attendanceMarksEntryHandler== null)
		{
			AttendanceMarksEntryHandler attendanceMarksEntryHandler = new AttendanceMarksEntryHandler();
			return attendanceMarksEntryHandler; 
		}
		return attendanceMarksEntryHandler;
	}
	public List<ExamCourseUtilTO> getCourseList() throws Exception {
		log.debug("call of getCourseList method in AttendanceMarksEntryHandler.class");
		List<ExamCourseUtilTO>  courseList = new ArrayList<ExamCourseUtilTO>();
		List<ExamCourseUtilBO> courseBOList = new ArrayList<ExamCourseUtilBO>();
		IAttendanceMarksEntryTransaction transaction = new AttendanceMarksEntryTransactionImpl().getInstance();
		courseBOList = transaction.getCourseList();
		courseList = AttendanceMarksEntryHelper.getInstance().convertBOtoTO(courseBOList);
		log.debug("end of getCourseList method in AttendanceMarksEntryHandler.class");
		return courseList;
	}
	public boolean addAttendanceMarks(AttendanceMarksEntryForm attendanceMarksEntryForm,HttpServletRequest request, ActionErrors errors)  throws Exception{
		log.debug("call of addAttendanceMarks method  in AttendanceMarksEntryHandler.class");
		boolean isAdded = false;
		IAttendanceMarksEntryTransaction transaction = new AttendanceMarksEntryTransactionImpl().getInstance();
		List<ExamSubCoursewiseAttendanceMarksBO> attendaceBoList = new ArrayList<ExamSubCoursewiseAttendanceMarksBO>();
		attendaceBoList = AttendanceMarksEntryHelper.getInstance().converFormTOBOList(attendanceMarksEntryForm,request,errors,transaction);
		isAdded =  transaction.addAttendanceMark(attendaceBoList);
		log.debug("end of addAttendanceMarks method  in AttendanceMarksEntryHandler.class");
		return isAdded;
	}
	public List<AttendanceMarkAndPercentageTO> getAttendanceList() throws Exception {
		log.debug("call of getAttendanceList method in AttendanceMarksEntryHandler.class");
		List<AttendanceMarkAndPercentageTO> attendanceList = new ArrayList<AttendanceMarkAndPercentageTO>();
		List<Object[]> bolists = new ArrayList<Object[]>();
		IAttendanceMarksEntryTransaction transaction = new AttendanceMarksEntryTransactionImpl().getInstance();
		bolists = transaction.getAttendanceList();
		attendanceList = AttendanceMarksEntryHelper.getInstance().convertObjectListOfAttendanceToTOList(bolists);
		log.debug("end of getAttendanceList method in AttendanceMarksEntryHandler.class");
		return attendanceList;
	}

}
