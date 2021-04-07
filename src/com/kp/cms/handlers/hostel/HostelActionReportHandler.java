package com.kp.cms.handlers.hostel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.hostel.HostelActionReportForm;
import com.kp.cms.helpers.hostel.HostelActionReportHelper;
import com.kp.cms.to.hostel.HostelActionReportTO;
import com.kp.cms.transactions.hostel.IHostelActionReportTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelActionReportTransactionImpl;

public class HostelActionReportHandler {
	private static final Log log = LogFactory.getLog(HostelActionReportHandler.class);
	private static volatile HostelActionReportHandler actionReportHandler = null;

	IHostelActionReportTransaction iactionReportTransaction = new HostelActionReportTransactionImpl();

	@SuppressWarnings("unused")
	private HostelActionReportHandler() {
	
	}
	public static HostelActionReportHandler getInstance() {
		if (actionReportHandler == null) {
			actionReportHandler = new HostelActionReportHandler();
		}
		return actionReportHandler;
	}
	
	@SuppressWarnings("unchecked")
	public List<HostelActionReportTO> getHostelActionReportDetails(HostelActionReportForm actionReportForm) throws Exception{		
		
		
		
		HostelActionReportTO actionReportTO=new HostelActionReportTO();
		List<HostelActionReportTO> actionReportTOList=new ArrayList<HostelActionReportTO>();
		
		if(!actionReportForm.getHostelId().equals("") && actionReportForm.getHostelId()!=null ){ 
			actionReportTO.setHostelId(actionReportForm.getHostelId());
		}
		if(!actionReportForm.getStartDate().equals("") && actionReportForm!=null ){ 
			actionReportTO.setStartDate(actionReportForm.getStartDate());
		}
		if(!actionReportForm.getEndDate().equals("") && actionReportForm.getEndDate()!=null ){ 
			actionReportTO.setEndDate(actionReportForm.getEndDate());
		}
		if(!actionReportForm.getActionId().equals("") && actionReportForm.getActionId()!=null ){ 
			actionReportTO.setTypeId(actionReportForm.getActionId());
		}		
		
		if(iactionReportTransaction!=null){
			try {				
				List<Object> objList=iactionReportTransaction.getHostelActionReportDetails(actionReportTO);
					HostelActionReportHelper actionReportHelper=new HostelActionReportHelper();
					actionReportTOList=actionReportHelper.getHostelActionReportDetails(objList);					
			} catch (Exception e) {				
				log.error("error occured in qyery--->"+e.getMessage());
			}
		}
		return actionReportTOList;		
	}	
}
