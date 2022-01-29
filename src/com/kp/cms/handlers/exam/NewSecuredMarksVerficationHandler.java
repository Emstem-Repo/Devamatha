package com.kp.cms.handlers.exam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.exam.NewSecuredMarksVerficationForm;
import com.kp.cms.helpers.exam.NewSecuredMarksVerficationHelper;
import com.kp.cms.to.exam.StudentMarksTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.exam.INewSecuredMarksVerficationTransaction;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewSecuredMarksVerficationTransactionImpl;

public class NewSecuredMarksVerficationHandler {
	/**
	 * Singleton object of NewSecuredMarksVerficationHandler
	 */
	private static volatile NewSecuredMarksVerficationHandler newSecuredMarksVerficationHandler = null;
	private static final Log log = LogFactory.getLog(NewSecuredMarksVerficationHandler.class);
	private NewSecuredMarksVerficationHandler() {
		
	}
	/**
	 * return singleton object of NewSecuredMarksVerficationHandler.
	 * @return
	 */
	public static NewSecuredMarksVerficationHandler getInstance() {
		if (newSecuredMarksVerficationHandler == null) {
			newSecuredMarksVerficationHandler = new NewSecuredMarksVerficationHandler();
		}
		return newSecuredMarksVerficationHandler;
	}
	/**
	 * @param newSecuredMarksVerficationForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentMarksTO> getStudentForInput(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception{
		String marksQuery=NewSecuredMarksVerficationHelper.getInstance().getQueryForAlreadyEnteredMarks(newSecuredMarksVerficationForm);
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();// creating object for Transaction Impl class
		List marksList=transaction.getDataForQuery(marksQuery);// calling the method for checking data is there for the marksQuery
		List<StudentMarksTO> existsMarks=null;
		Map<Integer,String> detainMap=new HashMap<Integer, String>();
		if(newSecuredMarksVerficationForm.getSchemeNo()!=null && !newSecuredMarksVerficationForm.getSchemeNo().isEmpty()){
			detainMap=NewExamMarksEntryHandler.getInstance().getOldRegisterNo(newSecuredMarksVerficationForm.getSchemeNo());
		}
		if(marksList!=null && !marksList.isEmpty()){
			existsMarks=NewSecuredMarksVerficationHelper.getInstance().convertBoDataToMarksMap(marksList,detainMap);// converting the database data to Required Map
		}
		return existsMarks;
	}
	/**
	 * @param newSecuredMarksVerficationForm
	 * @return
	 */
	public Double getMaxMarkOfSubject(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception {
		INewSecuredMarksVerficationTransaction transaction=NewSecuredMarksVerficationTransactionImpl.getInstance();
		return transaction.getMaxMarkOfSubject(newSecuredMarksVerficationForm);
	}
	/**
	 * @param newSecuredMarksVerficationForm
	 * @return
	 */
	public boolean saveMarks(NewSecuredMarksVerficationForm newSecuredMarksVerficationForm) throws Exception {
		INewSecuredMarksVerficationTransaction transaction=NewSecuredMarksVerficationTransactionImpl.getInstance();
		return transaction.saveMarks(newSecuredMarksVerficationForm);
	}
}
