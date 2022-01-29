package com.kp.cms.handlers.reports;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.AdmissionAbstractForm;
import com.kp.cms.helpers.reports.AdmissionAbstractHelper;
import com.kp.cms.to.reports.AdmissionAbstractTO;
import com.kp.cms.transactions.reports.IAdmissionAbstractTransaction;
import com.kp.cms.transactionsimpl.reports.AdmissionAbstractTxnImpl;

public class AdmissionAbstractHandler {
	public static final Log log = LogFactory.getLog(AdmissionAbstractHandler.class);
	public static volatile AdmissionAbstractHandler admissionAbstractHandler = null;
	public static AdmissionAbstractHandler getInstance() {
		if (admissionAbstractHandler == null) {
			admissionAbstractHandler = new AdmissionAbstractHandler();
			return admissionAbstractHandler;
		}
		return admissionAbstractHandler;
	}
	/**
	 * 
	 * @param subForm
	 * @return
	 * @throws Exception
	 */

	public Map<Integer, AdmissionAbstractTO> getAdmissionAbstract(AdmissionAbstractForm admForm) throws Exception {
		log.debug("start getAdmissionAbstract");
		IAdmissionAbstractTransaction admTransaction = new AdmissionAbstractTxnImpl();
		List<Student> admList = admTransaction.getAdmittedStudents(admForm);
		Map<Integer, AdmissionAbstractTO> admissionAbstractTOMap = AdmissionAbstractHelper.getInstance().copyBosToTO(admList);
		log.debug("exit getAdmissionAbstract");
		return admissionAbstractTOMap;
	}	
}
