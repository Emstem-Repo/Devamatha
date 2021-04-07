package com.kp.cms.handlers.admission;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentTCDetails;
import com.kp.cms.forms.admission.TCDetailsClassWiseForm;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.helpers.admission.TCDetailsClassWiseHelper;
import com.kp.cms.helpers.admission.TCDetailsHelper;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.transactions.admission.ITCDetailsClassWiseTransaction;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.TCDetailsClassWiseTransactionImpl;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;

public class TCDetailsClassWiseHandler {
	/**
	 * Singleton object of TCDetailsHandler
	 */
	private static  volatile TCDetailsClassWiseHandler tCDetailsHandler = null;
	private static final Log log = LogFactory.getLog(TCDetailsClassWiseHandler.class);
	private TCDetailsClassWiseHandler() {
		
	}
	/**
	 * return singleton object of TCDetailsHandler.
	 * @return
	 */
	public static TCDetailsClassWiseHandler getInstance() {
		if (tCDetailsHandler == null) {
			tCDetailsHandler = new TCDetailsClassWiseHandler();
		}
		return tCDetailsHandler;
	}
	/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public List<BoardDetailsTO> getListOfCandidates(TCDetailsClassWiseForm tcDetailsForm) throws Exception 
	{
		log.info("Entered into getListOfCandidates- TCDetailsHandler");
		
		ITCDetailsClassWiseTransaction transaction=TCDetailsClassWiseTransactionImpl.getInstance();
		String query=TCDetailsClassWiseHelper.getInstance().getSearchQuery(tcDetailsForm);
		List<Student> studentList=transaction.getStudentDetails(query);
		
		log.info("Exit from getListOfCandidates- TCDetailsHandler");
		return TCDetailsHelper.getInstance().convertBotoToList(studentList);
	}
	/**
	 * @param tcDetailsForm
	 * @throws Exception
	 */
		/**
	 * @param tcDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateStudentTCDetails(TCDetailsClassWiseForm tcDetailsForm) throws Exception 
	{
		List<StudentTCDetails> bolist=TCDetailsClassWiseHelper.getInstance().convertTotoBo(tcDetailsForm);
		ITCDetailsClassWiseTransaction transaction=TCDetailsClassWiseTransactionImpl.getInstance();
		return transaction.saveStudentTCDetails(bolist);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<CharacterAndConductTO> getCharacterAndConductList() throws Exception {
		ITCDetailsTransaction transaction=TCDetailsTransactionImpl.getInstance();
		List<CharacterAndConduct> list=transaction.getAllCharacterAndConduct();
		return TCDetailsHelper.getInstance().convertBoListToTOList(list);
	}
}
