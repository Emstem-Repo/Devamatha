package com.kp.cms.handlers.reports;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.SecondLanguageForm;
import com.kp.cms.helpers.reports.SecondLanguageHelper;
import com.kp.cms.to.reports.CourseWithStudentTO;
import com.kp.cms.transactions.reports.ISecondLanguageTransaction;
import com.kp.cms.transactionsimpl.reports.SecondLanguageTransactionImpl;

public class SecondLanguageHandler {
	private static final Log log = LogFactory.getLog(SecondLanguageHandler.class);
	public static volatile SecondLanguageHandler secondLanguageHandler = null;

	private SecondLanguageHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static SecondLanguageHandler getInstance() {
		if (secondLanguageHandler == null) {
			secondLanguageHandler = new SecondLanguageHandler();
		}
		return secondLanguageHandler;
	}
	/**
	 * 
	 * @returns all second languages
	 * @throws Exception
	 */
	public Map<String, String> getAllSecondLanguages()throws Exception {
		log.info("entered getAllSecondLanguages. of SecondLanguageHandler");	
		ISecondLanguageTransaction transaction = new SecondLanguageTransactionImpl();
		log.info("Leaving getAllSecondLanguages. of SecondLanguageHandler");	
		return transaction.getAllSecondLanguagesFromExamSecondLanguage();
	}
	
	/**
	 * 
	 * @param languageForm
	 * @returns students withe the second language
	 * @throws Exception
	 */

	public List<CourseWithStudentTO> getSecondLanguageStudents(
			SecondLanguageForm languageForm)throws Exception {
		log.info("entered getSecondLanguageStudents. of SecondLanguageHandler");	
		ISecondLanguageTransaction transaction = new SecondLanguageTransactionImpl();
		List<Object[]> studentList = transaction.getSecondLanguageStudents(SecondLanguageHelper.getInstance().getSearchCriteria1(languageForm));
		log.info("Leaving getSecondLanguageStudents. of SecondLanguageHandler");	
		return SecondLanguageHelper.getInstance().copyStudentBOsToTO(studentList, languageForm);
	}
	
}
