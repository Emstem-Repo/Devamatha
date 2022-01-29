package com.kp.cms.handlers.pettycash;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionReportForm;
import com.kp.cms.helpers.pettycash.ConsolidatedCollectionReportHelper;
import com.kp.cms.to.pettycash.ConsolidatedCollectionReportTO;
import com.kp.cms.to.pettycash.PcAccountTo;
import com.kp.cms.transactions.pettycash.IConsolidatedCollectionReport;
import com.kp.cms.transactionsimpl.pettycash.ConsolidatedCollectionReportTransactionImpl;

public class ConsolidatedCollectionReportHandler {
	/**
	 * Singleton object of CollectionLedgerHandler
	 */
	private static volatile ConsolidatedCollectionReportHandler consolidatedCollectionReportHandler = null;
	private static final Log log = LogFactory.getLog(ConsolidatedCollectionReportHandler.class);
	private ConsolidatedCollectionReportHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ConsolidatedCollectionReportHandler getInstance() {
		if (consolidatedCollectionReportHandler == null) {
			consolidatedCollectionReportHandler = new ConsolidatedCollectionReportHandler();
		}
		return consolidatedCollectionReportHandler;
	}
	/**
	 * @param consolidatedCollectionReportForm
	 * @return
	 * @throws Exception
	 */
	public List<ConsolidatedCollectionReportTO> getListOfCandidates(ConsolidatedCollectionReportForm consolidatedCollectionReportForm,boolean account,int userId)throws Exception{
		IConsolidatedCollectionReport iConsolidatedCollectionReport=new ConsolidatedCollectionReportTransactionImpl();
		String searchCriteria=null;
		if(account){
			searchCriteria=ConsolidatedCollectionReportHelper.getInstance().getSearchQueryForGroupCode(consolidatedCollectionReportForm,userId);
		}
		else{
			searchCriteria=ConsolidatedCollectionReportHelper.getInstance().getSearchQueryForAccountNumber(consolidatedCollectionReportForm, userId);
		}
		List listofStudents=iConsolidatedCollectionReport.getListOfData(searchCriteria);
		List<ConsolidatedCollectionReportTO> listOfData=ConsolidatedCollectionReportHelper.getInstance().convertListOfBOtoTO(listofStudents, consolidatedCollectionReportForm);
		return listOfData;
	}
	/**
	 * getting the account Numbers and keeping in to list
	 * @return
	 * @throws Exception
	 */
	public List<PcAccountTo> getListOfAccNo() throws Exception{
		IConsolidatedCollectionReport iConsolidatedCollectionReport=new ConsolidatedCollectionReportTransactionImpl();
		List<PcBankAccNumber> listOfAccNo=iConsolidatedCollectionReport.getListOfAccNo();
		List<PcAccountTo> accNoList=ConsolidatedCollectionReportHelper.getInstance().convertBOtoTOList(listOfAccNo);
		return accNoList;
	}
}
