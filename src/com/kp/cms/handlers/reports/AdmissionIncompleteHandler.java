package com.kp.cms.handlers.reports;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.AdmissionIncompleteForm;
import com.kp.cms.helpers.reports.AdmissionIncompleteHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.reports.IAdmissionIncompleteTransaction;
import com.kp.cms.transactionsimpl.reports.AdmissionIncompleteTransactionImpl;

public class AdmissionIncompleteHandler {
	private static final Log log = LogFactory.getLog(AdmissionIncompleteHandler.class);
	public static volatile AdmissionIncompleteHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static AdmissionIncompleteHandler getInstance(){
		if(self==null)
			self= new AdmissionIncompleteHandler();
		return self;
	}
	/**
	 * Private constructor for a singleton class
	 */
	private AdmissionIncompleteHandler(){		
	}
	/**
	 * 
	 * @param incompleteForm
	 * @returns incomplete admssion students
	 * @throws Exception
	 */
	public List<StudentTO> getIncompleteAdmssionStudents(AdmissionIncompleteForm incompleteForm)throws Exception {
		log.info("entered getIncompleteAdmssionStudents. of AdmissionIncompleteHandler");
		IAdmissionIncompleteTransaction transaction = new AdmissionIncompleteTransactionImpl();
		List<Student> studentBOList = transaction.getIncompleteAdmssionStudents(AdmissionIncompleteHelper.getInstance().getSelectionCriteria(incompleteForm));
		log.info("Leaving getIncompleteAdmssionStudents. of AdmissionIncompleteHandler");
		return AdmissionIncompleteHelper.getInstance().copyStudentBosToTOs(studentBOList);
	}
}
