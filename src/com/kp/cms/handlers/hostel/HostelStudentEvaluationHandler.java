package com.kp.cms.handlers.hostel;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlAttendance;
import com.kp.cms.forms.hostel.HostelStudentEvaluationForm;
import com.kp.cms.helpers.hostel.HostelStudentEvaluationHelper;
import com.kp.cms.to.hostel.HostelStudentEvaluationTO;
import com.kp.cms.transactions.hostel.IHostelStudentEvaluationTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelStudentEvaluationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class HostelStudentEvaluationHandler {
	
	private static volatile HostelStudentEvaluationHandler hostelStudentEvaluationHandler=null;
	
	private static final Log log=LogFactory.getLog(HostelStudentEvaluationHandler.class);
	
	private HostelStudentEvaluationHandler(){
		
	}
	public static HostelStudentEvaluationHandler getInstance(){
		
		if(hostelStudentEvaluationHandler==null){
			hostelStudentEvaluationHandler=new  HostelStudentEvaluationHandler();
		}
		return hostelStudentEvaluationHandler;
	}
	
	public List<HostelStudentEvaluationTO> getEvaluationList(HostelStudentEvaluationForm studentEvaluationForm)throws Exception{
		
		HostelStudentEvaluationHelper helper = HostelStudentEvaluationHelper.getInstance();
		IHostelStudentEvaluationTransaction transaction = new HostelStudentEvaluationTransactionImpl();
		int hostelId=Integer.parseInt(studentEvaluationForm.getHostelId());
		Date fromDate=CommonUtil.ConvertStringToDate(studentEvaluationForm.getFromDate());
		Date toDate=CommonUtil.ConvertStringToDate(studentEvaluationForm.getToDate());
		List<HlApplicationForm> hlApplicationList = transaction.getHlApplicationForm(hostelId, fromDate, toDate,studentEvaluationForm.getAcademicYear());
		List<HostelStudentEvaluationTO> evaluationTOList = null;
		if (hlApplicationList != null && hlApplicationList.size()>0) {
			evaluationTOList = helper.convertBOtoTO(hlApplicationList,studentEvaluationForm,hostelId);
		}
		return evaluationTOList;
	}

}
