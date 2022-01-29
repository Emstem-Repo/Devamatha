package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.StudentsListReportForm;
import com.kp.cms.helpers.reports.StudentsListReportHelper;
import com.kp.cms.to.reports.StudentsListReportTO;
import com.kp.cms.transactions.reports.ISudentsListTransaction;
import com.kp.cms.transactionsimpl.reports.StudentsListReportTransactionImpl;

public class StudentsListHandler {
	
private static final Log log = LogFactory.getLog(StudentsListHandler.class);
	
	public static volatile StudentsListHandler studentsListHandler = null;
	
	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static StudentsListHandler getInstance() {
		if (studentsListHandler == null) {
			studentsListHandler = new StudentsListHandler();
			return studentsListHandler;
		}
		return studentsListHandler;
	}
	
	public List<StudentsListReportTO> getStudentsListReportDetails(StudentsListReportForm listReportForm) throws Exception{
		log.info("entered getStudentsListReportDetails method of StudentsListHandler class.");
		ISudentsListTransaction transaction = StudentsListReportTransactionImpl.getInstance();
		List<Student> list = transaction.getStudentsListReportDetails(StudentsListReportHelper.getSelectionSearchCriteria(listReportForm));
		if(list != null && !list.isEmpty()){
			List<StudentsListReportTO> studentsList = StudentsListReportHelper.convertBOstoTOs(list,listReportForm);
			return studentsList;
		}
		log.info("Exit of getStudentsListReportDetails method of StudentsListHandler class.");
		return new ArrayList<StudentsListReportTO>();
	}
		

}
