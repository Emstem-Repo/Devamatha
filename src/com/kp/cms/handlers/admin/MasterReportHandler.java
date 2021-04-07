package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.admin.MasterReportForm;
import com.kp.cms.helpers.admin.MasterReportHelper;
import com.kp.cms.to.attendance.MasterReportTO;
import com.kp.cms.transactions.admission.IMasterReportTxn;
import com.kp.cms.transactionsimpl.admin.MasterReportImpl;

public class MasterReportHandler {
	private static final Log log = LogFactory.getLog(MasterReportHandler.class);
	public static volatile MasterReportHandler self=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static MasterReportHandler getInstance(){
		if(self==null)
			self= new MasterReportHandler();
		return self;
	}
	/**
	 * 
	 */
	private MasterReportHandler(){
		
	}
	public List<MasterReportTO> getMasterResult(
			MasterReportForm masterReportForm,HttpServletRequest req) throws Exception {
		log.info("entered getMasterResult..");
		IMasterReportTxn mTxn = MasterReportImpl.getInstance();
		
		Map masterResult = mTxn.getMasterResult(masterReportForm.getMasterTable());
		List masterList = new ArrayList();
		String []fields = null;
		if(masterResult!=null){
			fields = (String[]) masterResult.get("metadata");
			masterList = (List) masterResult.get("masterList");
		}
		List mastList = MasterReportHelper.convertBoToTo(masterList, masterReportForm, fields,req); 
//		List attendanceResults = attendanceSummary.getStudentAttendance(AttendanceSummaryReportHelper.getSelectionSearchCriteria(attendanceSummaryReportForm));
//		List<MasterReportTO> studentSearchTo = AttendanceSummaryReportHelper.convertBoToTo(attendanceResults,attendanceSummaryReportForm);
		log.info("exit getMasterResult..");
		return mastList;
	}

}
