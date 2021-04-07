package com.kp.cms.handlers.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionLedgerForm;
import com.kp.cms.helpers.pettycash.ConsolidatedCollectionLedgerHelper;
import com.kp.cms.to.pettycash.ConsolidatedCollectionLedgerTO;
import com.kp.cms.transactions.pettycash.IConsolidatedCollectionLedger;
import com.kp.cms.transactionsimpl.pettycash.ConsolidatedCollectionLedgerTransactionImpl;

public class ConsolidatedCollectionLedgerHandler {
	/**
	 * Singleton object of CollectionLedgerHandler
	 */
	private static volatile ConsolidatedCollectionLedgerHandler collectionLedgerHandler = null;
	private static final Log log = LogFactory.getLog(ConsolidatedCollectionLedgerHandler.class);
	private ConsolidatedCollectionLedgerHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ConsolidatedCollectionLedgerHandler getInstance() {
		if (collectionLedgerHandler == null) {
			collectionLedgerHandler = new ConsolidatedCollectionLedgerHandler();
		}
		return collectionLedgerHandler;
	}
	/**
	 * @param ConsolidatedCollectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public List<ConsolidatedCollectionLedgerTO> getListOfCandidates(ConsolidatedCollectionLedgerForm collectionLedgerForm,int userId)throws Exception{
		IConsolidatedCollectionLedger iCollectionLedger=new ConsolidatedCollectionLedgerTransactionImpl();
		String searchCriteria=null;
		searchCriteria=ConsolidatedCollectionLedgerHelper.getInstance().getSearchQueryForAccountHeadCode(collectionLedgerForm,userId);
		
		List listofStudents=iCollectionLedger.getListOfData(searchCriteria);
		return ConsolidatedCollectionLedgerHelper.getInstance().convertListOfBOtoTOs1(listofStudents,collectionLedgerForm);
	}
	/**
	 * verifying the username is existed or not in Handler
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public Users verifyUser(String userName) throws Exception{
		IConsolidatedCollectionLedger iCollectionLedger=new ConsolidatedCollectionLedgerTransactionImpl();
		Users users=iCollectionLedger.verifyUser(userName);
		return users;
	}
	public boolean exportTOExcel(ConsolidatedCollectionLedgerForm collectionLedgerForm,HttpServletRequest request) throws Exception{
		boolean isUpdated = false;
		ConsolidatedCollectionLedgerHelper reportHelper = ConsolidatedCollectionLedgerHelper.getInstance();
		ConsolidatedCollectionLedgerTO admBioTo = reportHelper.selectedColumns(collectionLedgerForm);
		isUpdated = reportHelper.convertToTOExcel(admBioTo,collectionLedgerForm,request);
		return isUpdated;
	}
	
}
