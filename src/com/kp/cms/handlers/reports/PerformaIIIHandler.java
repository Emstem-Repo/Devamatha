package com.kp.cms.handlers.reports;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.PerformaIIIForm;
import com.kp.cms.helpers.reports.PerformaIIIHelper;
import com.kp.cms.to.reports.PerformaIIITO;
import com.kp.cms.transactions.reports.IPerformaIIITransaction;
import com.kp.cms.transactionsimpl.reports.PerformaIIITransactionImpl;

public class PerformaIIIHandler {

private static final Log log = LogFactory.getLog(PerformaIIIHandler.class);
	
	private static volatile PerformaIIIHandler performaIIIHandler= null;
	
	private PerformaIIIHandler(){
	}
	
	/**
	 * This method returns the single instance of this ClassEntryHandler.
	 * @return
	 */
	public static PerformaIIIHandler getInstance() {
      if(performaIIIHandler == null) {
    	  performaIIIHandler = new PerformaIIIHandler();
      }
      return performaIIIHandler;
	}
	
	/**
	 * 
	 * @param studentsAttendanceReportForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, PerformaIIITO> getCourseIntakeDetails(
			PerformaIIIForm performaIIIForm) throws Exception {
		log.info("Entered getCourseIntakeDetails");
		IPerformaIIITransaction performaTransaction = new PerformaIIITransactionImpl();
		PerformaIIIHelper performaIIIHelper = new PerformaIIIHelper();
		
		List<Student> performIIIBOList = performaTransaction.getCourseIntakeDetails(performaIIIHelper.getSelectionCriteria(performaIIIForm));
		Map<Integer, PerformaIIITO> performIIITOMap = performaIIIHelper.convertBoToTo(performIIIBOList);
		log.info("Exit getCourseIntakeDetails");
		return performIIITOMap;
	}
}
