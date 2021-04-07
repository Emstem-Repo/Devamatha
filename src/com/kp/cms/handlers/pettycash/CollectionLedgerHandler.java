package com.kp.cms.handlers.pettycash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.pettycash.CollectionLedgerForm;
import com.kp.cms.helpers.pettycash.CollectionLedgerHelper;
import com.kp.cms.helpers.reports.ScoreSheetHelper;
import com.kp.cms.to.inventory.ItemTO;
import com.kp.cms.to.pettycash.CollectionLedgerTO;
import com.kp.cms.to.pettycash.PcAccountHeadsTO;
import com.kp.cms.to.reports.ScoreSheetTO;
import com.kp.cms.transactions.pettycash.ICollectionLedger;
import com.kp.cms.transactionsimpl.pettycash.CollectionLedgerTransactionImpl;

public class CollectionLedgerHandler {
	/**
	 * Singleton object of CollectionLedgerHandler
	 */
	private static volatile CollectionLedgerHandler collectionLedgerHandler = null;
	private static final Log log = LogFactory.getLog(CollectionLedgerHandler.class);
	private CollectionLedgerHandler() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static CollectionLedgerHandler getInstance() {
		if (collectionLedgerHandler == null) {
			collectionLedgerHandler = new CollectionLedgerHandler();
		}
		return collectionLedgerHandler;
	}
	/**
	 * getting the AccountList
	 * @return
	 */
	public List<PcAccountHeadsTO> getAccountList() throws Exception{
		log.info("Entering into getAccountList in CollectionLedgerHandler");
		ICollectionLedger transaction=new CollectionLedgerTransactionImpl();
		List<PcAccountHead> accountList=transaction.getAccountList();
		log.info("Exit from getAccountList in CollectionLedgerHandler");
		return CollectionLedgerHelper.getInstance().convertBOtoTO(accountList);
	}
	
	public String getAccountList1(String accCode) throws Exception{
		log.info("Entering into getAccountList in CollectionLedgerHandler");
		ICollectionLedger transaction=new CollectionLedgerTransactionImpl();
		String accountName=transaction.getAccountName(accCode);
		//log.info("Exit from getAccountList in CollectionLedgerHandler");
		return CollectionLedgerHelper.getInstance().convertBOtoTOCodeName(accountName);
	}
	/**
	 * @param collectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public List<CollectionLedgerTO> getListOfCandidates(CollectionLedgerForm collectionLedgerForm,boolean account,int userId)throws Exception{
		ICollectionLedger iCollectionLedger=new CollectionLedgerTransactionImpl();
		String searchCriteria=null;
		if(account){
			searchCriteria=CollectionLedgerHelper.getInstance().getSearchQueryForGroupCode(collectionLedgerForm,userId);
		}
		else{
			searchCriteria=CollectionLedgerHelper.getInstance().getSearchQueryForAccountHeadCode(collectionLedgerForm,userId);
		}
		List listofStudents=iCollectionLedger.getListOfData(searchCriteria);
		return CollectionLedgerHelper.getInstance().convertListOfBOtoTOs1(listofStudents,collectionLedgerForm);
	}
	/**
	 * verifying the username is existed or not in Handler
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	public Users verifyUser(String userName) throws Exception{
		ICollectionLedger iCollectionLedger=new CollectionLedgerTransactionImpl();
		Users users=iCollectionLedger.verifyUser(userName);
		return users;
	}
	/**
	 * verifying the group code
	 * @param groupCode
	 * @return
	 */
	public PcAccHeadGroup verifyGroupCode(String groupCode) throws Exception{
		ICollectionLedger iCollectionLedger=new CollectionLedgerTransactionImpl();
		PcAccHeadGroup group=iCollectionLedger.verifyGroupCode(groupCode);
		return group;
	}
	public boolean exportTOExcel(CollectionLedgerForm collectionLedgerForm,	HttpServletRequest request)throws Exception {
		boolean isUpdated=false;
		CollectionLedgerHelper healper=CollectionLedgerHelper.getInstance();
		CollectionLedgerTO collectionTo=healper.selectedColumn(collectionLedgerForm);
		isUpdated=healper.convertToExcel(collectionTo,collectionLedgerForm,request);
		return isUpdated;
	}
	
}
