package com.kp.cms.handlers.reports;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CategoryWiseIntakeForm;
import com.kp.cms.helpers.reports.CategoryWiseIntakeHelper;
import com.kp.cms.to.reports.CategoryWiseTO;
import com.kp.cms.transactions.reports.ISudentsListTransaction;
import com.kp.cms.transactionsimpl.reports.StudentsListReportTransactionImpl;

public class CategoryWiseIntakeHandler {

private static final Log log = LogFactory.getLog(StudentsListHandler.class);

public static volatile CategoryWiseIntakeHandler wiseIntakeHandler = null;
	
	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static CategoryWiseIntakeHandler getInstance() {
		if (wiseIntakeHandler == null) {
			wiseIntakeHandler = new CategoryWiseIntakeHandler();
			return wiseIntakeHandler;
		}
		return wiseIntakeHandler;
	}
	
	public Map<Integer, CategoryWiseTO> getCategoryWiseIntakeDetails(CategoryWiseIntakeForm categoryWiseIntakeForm) throws Exception{
		log.info("entered getCategoryWiseIntakeDetails method of CategoryWiseIntakeHandler class.");
		ISudentsListTransaction transaction = StudentsListReportTransactionImpl.getInstance();
		List<Student> list = transaction.getStudentsListReportDetails(CategoryWiseIntakeHelper.getSelectionCriteria(categoryWiseIntakeForm));
		if(list != null && !list.isEmpty()){
			Map<Integer, CategoryWiseTO> categoryWiseMap = CategoryWiseIntakeHelper.convertBOstoTOs(list);
			return categoryWiseMap;
		}
		log.info("Exit of getCategoryWiseIntakeDetails method of CategoryWiseIntakeHandler class.");
		return new HashMap<Integer, CategoryWiseTO>();
	}
}
