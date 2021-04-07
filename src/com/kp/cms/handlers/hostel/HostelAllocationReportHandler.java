package com.kp.cms.handlers.hostel;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.forms.hostel.HostelAllocationReportForm;
import com.kp.cms.helpers.hostel.AllocationReportHelper;
import com.kp.cms.helpers.pettycash.CollectionsHelper;
import com.kp.cms.to.hostel.HostelAllocationReportTO;
import com.kp.cms.to.pettycash.AccountNOTo;
import com.kp.cms.transactions.hostel.IHostelAllocationReportTransactions;
import com.kp.cms.transactions.pettycash.ICollectionsTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelAllocationReportTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CollectionsTransactionImpl;

public class HostelAllocationReportHandler {
	private static volatile HostelAllocationReportHandler hostelAllocationReportHandler = null;
	
	/**
	 * This method returns the single instance of this HostelAllocationHandler.
	 * 
	 * @return
	 */
	public static HostelAllocationReportHandler getInstance() {
		if (hostelAllocationReportHandler == null) {
			hostelAllocationReportHandler = new HostelAllocationReportHandler();
		}
		return hostelAllocationReportHandler;
	}

	public List<HostelAllocationReportTO> getAllocationDetails(
			HostelAllocationReportForm hostelAllocationReportForm,
			HttpServletRequest request) throws Exception {
			IHostelAllocationReportTransactions transaction=new HostelAllocationReportTransactionImpl();
			
		List<Object[]> allocationBo = transaction.getAllocationDetails(hostelAllocationReportForm);
		List<HostelAllocationReportTO> hostelAllocationList=AllocationReportHelper.copyAllocationBOToTO(allocationBo);
 			return  hostelAllocationList;
	}
}
