package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.SecondLanguageAbstractForm;
import com.kp.cms.helpers.reports.SecondLanguageAbstractHelper;
import com.kp.cms.to.reports.SecLanguageAbstractTO;
import com.kp.cms.transactions.reports.ISecondLanguageAbstractTxn;
import com.kp.cms.transactionsimpl.reports.SecondLanguageAbstractTxnImpl;

public class SecondLanguageAbstractHandler {
	private static final Log log = LogFactory.getLog(SecondLanguageAbstractHandler.class);
	public static volatile SecondLanguageAbstractHandler secondLanguageAbstractHandler = null;

	public static SecondLanguageAbstractHandler getInstance() {
		if (secondLanguageAbstractHandler == null) {
			secondLanguageAbstractHandler = new SecondLanguageAbstractHandler();
			return secondLanguageAbstractHandler;
		}
		return secondLanguageAbstractHandler;
	}
	/**
	 * 
	 * @param stickerForm
	 * @throws Exception
	 */
	
	public List<SecLanguageAbstractTO> getStudents(SecondLanguageAbstractForm abstractForm) throws Exception {
		log.debug("inside getStudents");
		ISecondLanguageAbstractTxn iAbstractTxn = SecondLanguageAbstractTxnImpl.getInstance();
		int programTypeId = 0;
		int semester = 0;
		if(abstractForm.getProgramTypeId()!= null && !abstractForm.getProgramTypeId().trim().isEmpty()){
			programTypeId = Integer.parseInt(abstractForm.getProgramTypeId());
		}
		if(abstractForm.getSemister()!= null && !abstractForm.getSemister().trim().isEmpty()){
			semester = Integer.parseInt(abstractForm.getSemister());
		}
		
		List<Object[]> stuList = iAbstractTxn.getStudentsWithLanguageCount(programTypeId, semester, abstractForm.getLanguage());
		
		return SecondLanguageAbstractHelper.getInstance().copyBosToList(stuList);
	}
	  		
	

}
