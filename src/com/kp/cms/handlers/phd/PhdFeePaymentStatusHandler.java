package com.kp.cms.handlers.phd;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.phd.PhdFeePaymentStatusForm;
import com.kp.cms.helpers.phd.PhdFeePaymentStatusHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.phd.PhdFeePaymentStatusTO;
import com.kp.cms.transactions.phd.IPhdFeePaymentStatus;
import com.kp.cms.transactionsimpl.phd.PhdFeePaymentStatusTransactionImpl;

public class PhdFeePaymentStatusHandler {
	private static final Log log = LogFactory .getLog(PhdFeePaymentStatusHandler.class);
 private static volatile PhdFeePaymentStatusHandler documentSubmissionScheduleHandler = null;
 public static PhdFeePaymentStatusHandler getInstance(){
	 if(documentSubmissionScheduleHandler == null){
		 documentSubmissionScheduleHandler = new PhdFeePaymentStatusHandler();
		 return documentSubmissionScheduleHandler;
	 }
	 return documentSubmissionScheduleHandler;
 }
 
 IPhdFeePaymentStatus transactions= PhdFeePaymentStatusTransactionImpl.getInstance();

/**
 * @param feePaymentStatusForm
 * @return
 * @throws Exception
 */
public List<PhdFeePaymentStatusTO> getFeePaymentStatus(PhdFeePaymentStatusForm feePaymentStatusForm) throws Exception{
	log.info("call of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	List<Object[]> documentBo=transactions.getFeePaymentStatus(feePaymentStatusForm);
	List<PhdFeePaymentStatusTO> documentTos = PhdFeePaymentStatusHelper.getInstance().setDataBotoTo(documentBo);
	log.info("end of getSubjectGroupsList method in AssignSubjectGroupHistoryHandler class.");
	return documentTos;
}
/**
 * @param programTypeId
 * @return
 * @throws Exception
 */
public List<CourseTO> getCoursesByProgramTypes(String programTypeId) throws Exception{
	List<Object[]> course=transactions.getCoursesByProgramTypes(programTypeId);
	List<CourseTO> courseTo=PhdFeePaymentStatusHelper.getInstance().SetCourses(course);
	return courseTo;
}

}
