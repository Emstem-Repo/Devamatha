package com.kp.cms.handlers.attendance;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.exam.StudentOverallInternalMarkDetails;
import com.kp.cms.forms.attendance.CalculateAttendanceMarkCjcForm;
import com.kp.cms.forms.exam.NewUpdateProccessForm;
import com.kp.cms.handlers.exam.NewUpdateProccessHandler;
import com.kp.cms.helpers.attendance.CalculateAttendanceMarkHelper;
import com.kp.cms.helpers.exam.NewUpdateProccessHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.ClassesTO;
import com.kp.cms.to.exam.SubjectRuleSettingsTO;
import com.kp.cms.to.usermanagement.StudentAttendanceTO;
import com.kp.cms.transactions.attandance.ICalculateAttendanceMarkTransaction;
import com.kp.cms.transactions.exam.INewUpdateProccessTransaction;
import com.kp.cms.transactionsimpl.attendance.CalculateAttendanceMarkImpl;
import com.kp.cms.transactionsimpl.exam.NewUpdateProccessTransactionImpl;

public class CalculateAttendanceMarkHandler {
	
	private final int PROCESS_CALC_REGULAR_MARKS = 1;
	private final int PROCESS_CALC_INTERNAL_MARKS = 2;
	private final int PROCESS_SUPPLEMENTARY_DATA_CREATION = 3;
	private final int PROCESS_UPDATE_PASS_FAIL = 4;
	
	/**
	 * Singleton object of NewUpdateProccessHandler
	 */
	private static volatile CalculateAttendanceMarkHandler calculateAttMarkHandler = null;
	private static final Log log = LogFactory.getLog(CalculateAttendanceMarkHandler.class);
	private CalculateAttendanceMarkHandler() {
		
	}
	/**
	 * return singleton object of NewUpdateProccessHandler.
	 * @return
	 */
	public static CalculateAttendanceMarkHandler getInstance() {
		if (calculateAttMarkHandler == null) {
			calculateAttMarkHandler = new CalculateAttendanceMarkHandler();
		}
		return calculateAttMarkHandler;
	}
	
	/**
	 * @param newUpdateProccessForm
	 * @return
	 * @throws Exception
	 */
	public List<ClassesTO> getClasses(CalculateAttendanceMarkCjcForm calAttMarkCjcForm) throws Exception{
		String query=CalculateAttendanceMarkHelper.getInstance().getClassesQuery(calAttMarkCjcForm);
		ICalculateAttendanceMarkTransaction transaction=CalculateAttendanceMarkImpl.getInstance();
		List list=transaction.getDataByQuery(query);
		return CalculateAttendanceMarkHelper.getInstance().convertBoListToTOList(list);
	}
	
	public boolean updateProcess(CalculateAttendanceMarkCjcForm calAttMarkCjcForm) throws Exception{
		boolean isUpdated=false;
		
			isUpdated= CalculateAttendanceMarkHelper.getInstance().calculateAttendanceMarks(calAttMarkCjcForm);
			
		
		return isUpdated;
	}

}
