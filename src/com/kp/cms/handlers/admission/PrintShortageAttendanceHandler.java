package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.admission.PrintShortageAttendanceForm;
import com.kp.cms.helpers.admission.PrintShortageAttendanceHelper;
import com.kp.cms.to.admission.PrintShortageAttendanceTo;
import com.kp.cms.transactions.admission.IPrintShortageAttendanceTransaction;
import com.kp.cms.transactionsimpl.admission.PrintShortageAttendanceTransactionImpl;

public class PrintShortageAttendanceHandler {
	/**
	 * Singleton object of printShortageAttendanceHandler
	 */
	private static volatile PrintShortageAttendanceHandler printShortageAttendanceHandler = null;
	private static final Log log = LogFactory.getLog(PrintShortageAttendanceHandler.class);
	private PrintShortageAttendanceHandler() {
		
	}
	/**
	 * return singleton object of printShortageAttendanceHandler.
	 * @return
	 */
	public static PrintShortageAttendanceHandler getInstance() {
		if (printShortageAttendanceHandler == null) {
			printShortageAttendanceHandler = new PrintShortageAttendanceHandler();
		}
		return printShortageAttendanceHandler;
	}
	/**
	 * @param printShortageAttendanceForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<String> getListOfStudentDetails(PrintShortageAttendanceForm printShortageAttendanceForm,HttpServletRequest request) throws Exception{
		String listQuery=PrintShortageAttendanceHelper.getInstance().getListOfStudentQuery(printShortageAttendanceForm);
		IPrintShortageAttendanceTransaction transaction=new PrintShortageAttendanceTransactionImpl();
		List<Object[]> listOfAttedance=transaction.getListOfDetails(listQuery);
		String mapQuery=PrintShortageAttendanceHelper.getInstance().getMapOfStudentQuery(printShortageAttendanceForm);
		Map<Integer,Integer> attMap=transaction.getMapOfStudent(mapQuery);
		List<Integer> detainedStudentList=transaction.getDetainedOrDiscontinuedStudents();
		List<PrintShortageAttendanceTo> finalList=PrintShortageAttendanceHelper.getFinalList(listOfAttedance,attMap,printShortageAttendanceForm,detainedStudentList);
		List<PrintShortageAttendanceTo> finaliListTo=PrintShortageAttendanceHelper.getFinalShortAttendenceList(finalList,printShortageAttendanceForm);
		return PrintShortageAttendanceHelper.getInstance().getPrintList(finaliListTo,request);
	}
}
