package com.kp.cms.handlers.hostel;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.helpers.hostel.HostelCheckoutHelper;
import com.kp.cms.helpers.hostel.StudentInoutHelper;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlCheckinCheckoutFacility;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlRoomTypeFacility;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.HostelCheckoutForm;
import com.kp.cms.forms.hostel.StudentInoutForm;
import com.kp.cms.transactions.hostel.IStudentInoutTransactions;
import com.kp.cms.transactionsimpl.hostel.StudentInoutTransactionImpl;
import com.kp.cms.to.hostel.HostelCheckoutTo;
import com.kp.cms.to.hostel.StudentInoutTo;
import com.kp.cms.bo.admin.HlInOut;

public class StudentInoutHandler {
	
	private static Log log = LogFactory.getLog(HostelCheckoutHandler.class);
	private static volatile StudentInoutHandler studentInoutHandler = null;
	IStudentInoutTransactions transaction = StudentInoutTransactionImpl.getInstance();

	private StudentInoutHandler() {
	}

	/**
	 * This method returns the single instance of this StudentInoutHandler.
	 * 
	 * @return
	 */
	public static StudentInoutHandler getInstance() {
		if (studentInoutHandler == null) {
			studentInoutHandler = new StudentInoutHandler();
		}
		return studentInoutHandler;
	}
	/**
	 * 
	 * @param StudentInoutForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentInoutTo> getStudentDetails(StudentInoutForm studentInoutForm) throws Exception {

		log.info("Entering getStudentDetails of StudentInoutHandler");
		StudentInoutHelper studentInoutHelper = StudentInoutHelper.getInstance();
		List<Object> studentInoutDetails = transaction.getStudentDetails(studentInoutHelper.getSearchCriteria(studentInoutForm));
		List<StudentInoutTo> studentInoutToList = studentInoutHelper.convertBOtoTO(studentInoutDetails,studentInoutForm );
		log.info("Exiting getStudentDetails of StudentInoutHandler");
		return studentInoutToList;
	}
	/**
	 * saving the data to database
	 * 
	 * @param hostelCheckinForm
	 * @param hostelApplicantDetails
	 * @return
	 * @throws Exception
	 */
	public String submitStudentDetails(StudentInoutForm studentInoutForm,List<StudentInoutTo> studentInoutToList) throws Exception {
		log.info("Entering saveCheckoutDetails of HostelCheckoutHandler");
		String isSaved="false";
		StudentInoutHelper studentInoutHelper = StudentInoutHelper.getInstance();
		Iterator<StudentInoutTo > stdIterator = studentInoutToList.iterator();
		StudentInoutTo studentInoutTo=null;
		while (stdIterator.hasNext()) {	
			studentInoutTo = (StudentInoutTo) stdIterator.next();
			HlInOut hlInOut = studentInoutHelper.submitStudentDetails(studentInoutForm, studentInoutTo);
			isSaved = transaction.submitStudentDetails(hlInOut); 
		}				
		return isSaved;
	}
	
	public boolean checkStudentInOutForADay(List<StudentInoutTo> studentInoutToList) throws Exception {
		boolean inOutTaken=false;
		Iterator<StudentInoutTo > stdIterator = studentInoutToList.iterator();
		StudentInoutTo studentInoutTo=null;
		while (stdIterator.hasNext()) {	
			studentInoutTo = (StudentInoutTo) stdIterator.next();
			inOutTaken = transaction.checkStudentInOutForADay(studentInoutTo); 
		}
		return inOutTaken;
	}


}
