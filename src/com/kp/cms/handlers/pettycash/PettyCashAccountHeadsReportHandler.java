package com.kp.cms.handlers.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.to.pettycash.PettyCashAccountHeadsTO;

public class PettyCashAccountHeadsReportHandler {

	private static volatile PettyCashAccountHeadsReportHandler pcAccountHeadsReportHandler = null;
	private static final Log log = LogFactory
			.getLog(PettyCashAccountHeadsReportHandler.class);

	private PettyCashAccountHeadsReportHandler() {

	}
	public static PettyCashAccountHeadsReportHandler getInstance() {
		if (pcAccountHeadsReportHandler == null) {
			pcAccountHeadsReportHandler = new PettyCashAccountHeadsReportHandler();
		}
		return pcAccountHeadsReportHandler;
	}
	public List<PettyCashAccountHeadsTO> getAccountHeadsReportList() throws Exception {
		log.info("entering into getAccountHeadsReportList method in PettycashAccountHeadsReportHandler class..");
		List<PettyCashAccountHeadsTO> pcAccountHeadList=null;
		 pcAccountHeadList=PettyCashAccountHeadsHandler.getInstance().getAllPettyCashAccHeads();
		 if(pcAccountHeadList!=null && !pcAccountHeadList.isEmpty())
		 {
			 return pcAccountHeadList;
		 }
		 log.info("leaving from getAccountHeadsReportList method in PettycashAccountHeadsReportHandler class..");
		 return pcAccountHeadList;
		 
	}	
	
	
	
}
