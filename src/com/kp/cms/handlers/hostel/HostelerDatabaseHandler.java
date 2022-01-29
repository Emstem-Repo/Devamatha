package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlStatus;
import com.kp.cms.forms.hostel.HostelerDatabaseForm;
import com.kp.cms.helpers.hostel.HostelerDatabaseHelper;
import com.kp.cms.helpers.hostel.RequisitionHelper;
import com.kp.cms.to.hostel.HostelerDataBaseResultsTO;
import com.kp.cms.to.hostel.HostelerDatabaseTO;
import com.kp.cms.to.hostel.RequisitionsTo;
import com.kp.cms.transactions.hostel.IHostelApplicationTransaction;
import com.kp.cms.transactions.hostel.IHostelerDatabaseTransaction;
import com.kp.cms.transactions.hostel.IRequisitionTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelApplicationTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.HostelerDatabaseTransactionImpl;
import com.kp.cms.transactionsimpl.hostel.RequisitionTransactionImpl;

public class HostelerDatabaseHandler {
	private static final Log log = LogFactory.getLog(HostelerDatabaseHandler.class);
	public static volatile HostelerDatabaseHandler hostelerDatabaseHandler = null;

	private HostelerDatabaseHandler(){
	
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */
	public static HostelerDatabaseHandler getInstance() {
		if (hostelerDatabaseHandler == null) {
			hostelerDatabaseHandler = new HostelerDatabaseHandler();
		}
		return hostelerDatabaseHandler;
	}
	
	/** @author 
	 * Used to get the students
	 */
	public List<HostelerDatabaseTO> submitHostelerDatabase(HostelerDatabaseForm databaseForm)throws Exception{
		List<HlRoomTransaction> appBOList = new ArrayList<HlRoomTransaction>();		
		IHostelerDatabaseTransaction transaction = new HostelerDatabaseTransactionImpl();
		IHostelApplicationTransaction appTransaction = new HostelApplicationTransactionImpl();
		HlStatus status = appTransaction.getCheckedInStatus();		
		if(status!=null){
			int checkedInStatusId =status.getId();
		
				
			appBOList = transaction.submitHostelerDatabase(HostelerDatabaseHelper.getInstance().searchCriteria(databaseForm, checkedInStatusId));
		}
		return HostelerDatabaseHelper.getInstance().convertAppFormBOToTO(appBOList);
	}
/* @author Hari Kumar .L
	*/
	public HostelerDataBaseResultsTO studentDetailsToShow(String hlAppId,HttpServletRequest req) throws Exception {
		IHostelerDatabaseTransaction transaction=new HostelerDatabaseTransactionImpl();
		String dynamicQuery =HostelerDatabaseHelper.getQuery1(hlAppId);
		List<HlApplicationForm> hostelerBO = transaction.getRequisitionDetailsToShow(dynamicQuery);
		HostelerDataBaseResultsTO requisitionsTo =HostelerDatabaseHelper.copyRequisitionBOToTO(hostelerBO,req);
		log.info("Leaving into studentDetailsToShow of  HostelerDatabaseHandler");
		return requisitionsTo;
	}
}
