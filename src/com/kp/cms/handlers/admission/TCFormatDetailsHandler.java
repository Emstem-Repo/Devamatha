package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.forms.admission.TCFormatDetailsForm;
import com.kp.cms.helpers.admission.TCDetailsHelper;
import com.kp.cms.helpers.admission.TCFormatDetailsHelper;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.transactions.admission.ITCFormatDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.TCFormatDetailsTransactionImpl;

public class TCFormatDetailsHandler {
	/**
	 * Singleton object of TCFormatDetailsHandler
	 */
	private static volatile TCFormatDetailsHandler tCFormatDetailsHandler = null;
	private static final Log log = LogFactory.getLog(TCFormatDetailsHandler.class);
	private TCFormatDetailsHandler() {
		
	}
	/**
	 * return singleton object of TCFormatDetailsHandler.
	 * @return
	 */
	public static TCFormatDetailsHandler getInstance() {
		if (tCFormatDetailsHandler == null) {
			tCFormatDetailsHandler = new TCFormatDetailsHandler();
		}
		return tCFormatDetailsHandler;
	}
	
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<BoardDetailsTO> getListOfCandidates(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		log.info("Entered into getListOfCandidates- TCDetailsHandler");
		
		ITCFormatDetailsTransaction transaction=TCFormatDetailsTransactionImpl.getInstance();
		String query=TCFormatDetailsHelper.getInstance().getSearchQuery(tcFormatDetailsForm);
		List<Student> studentList=transaction.getStudentDetails(query);
		
		log.info("Exit from getListOfCandidates- TCDetailsHandler");
		return TCDetailsHelper.getInstance().convertBotoToList(studentList);
	}
	/**
	 * @param tcDetailsForm
	 * @throws Exception
	 */
	public void getStudentTCDetails(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		log.info("Entered into getStudentTCDetails- BoardDetailsHandler");
		
		String query=TCFormatDetailsHelper.getInstance().getSearchQueryForTCDetails(tcFormatDetailsForm);
		ITCFormatDetailsTransaction transaction=TCFormatDetailsTransactionImpl.getInstance();
		Student student=transaction.getStudentTCDetails(query);
		TCFormatDetailsHelper.getInstance().convertBotoForm(student,tcFormatDetailsForm);
		
		log.info("Exit from getStudentTCDetails- TCDetailsHandler");
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateStudentTCDetails(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		StudentTCDetails bo=TCFormatDetailsHelper.getInstance().convertTotoBo(tcFormatDetailsForm);
		ITCFormatDetailsTransaction transaction=TCFormatDetailsTransactionImpl.getInstance();
		return transaction.saveStudentTCDetails(bo);
	}
	/**
	 * 
	 * @param tcFormatDetailsForm
	 * @param studentList
	 * @returnprocessUpdateStudentTCDetails
	 * @throws Exception
	 */
	public boolean processUpdateStudentTCDetails(TCFormatDetailsForm tcFormatDetailsForm, List<BoardDetailsTO> studentList) throws Exception {
		List<StudentTCDetails> tcDetailsList = TCFormatDetailsHelper.getInstance().processConvertTotoBo(tcFormatDetailsForm, studentList);
		ITCFormatDetailsTransaction transaction=TCFormatDetailsTransactionImpl.getInstance();
		return transaction.processUpdateTCDetails(tcDetailsList);
	}
}
