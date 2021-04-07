package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.pettycash.CollectionsReportForm;
import com.kp.cms.helpers.pettycash.CollectionsHelper;
import com.kp.cms.to.pettycash.AccountNOTo;
import com.kp.cms.to.pettycash.DailyCollectionsTo;
import com.kp.cms.transactions.pettycash.ICollectionsTransaction;
import com.kp.cms.transactionsimpl.pettycash.CollectionsTransactionImpl;

public class ColllectionHandeler {
	private static final Log log = LogFactory.getLog(ColllectionHandeler.class);
	
	private static volatile ColllectionHandeler colllectionHandeler = null;

	private ColllectionHandeler() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static ColllectionHandeler getInstance() {
		if (colllectionHandeler == null) {
			colllectionHandeler = new ColllectionHandeler();
		}
		return colllectionHandeler;
	}
	
	
	public List<AccountNOTo> getAccountNo()throws Exception{
		log.info("Inside handler getAccountNo");
		ICollectionsTransaction transaction=new CollectionsTransactionImpl();
		List<PcBankAccNumber> accontBo = transaction.getAccountNos();
		List<AccountNOTo> accountList =CollectionsHelper.copyAcconoBOToTO(accontBo);
			
			log.info("Leaving into getAccountNo Details of of ColllectionHandeler");
			return  accountList;
			
	}
	
	public List<DailyCollectionsTo> getDailyCollections(CollectionsReportForm collectionsReportForm,int userId)throws Exception{
		log.info("Inside handler getAccountNo");
		ICollectionsTransaction transaction=new CollectionsTransactionImpl();
		List<String> applNoList = new ArrayList<String>();
		List<String> RegNoList = new ArrayList<String>();
		if(collectionsReportForm.getAppiNo()!=null){
			for(String applnNo : collectionsReportForm.getAppiNo())
			{
				applNoList.add(applnNo);
			}
			//for(String regNo : collectionsReportForm.getRegNo1())
			//{
				//	RegNoList.add(regNo);
			//}
			collectionsReportForm.setFinalAccList(collectionsReportForm.getAccNOList());
			List<AccountNOTo> accountList=collectionsReportForm.getFinalAccList();
			Iterator<AccountNOTo> iterator = accountList.iterator(); //iterating List
			while (iterator.hasNext()) {
				AccountNOTo accountNOTo = (AccountNOTo) iterator.next();
				if(!applNoList.contains(String.valueOf(accountNOTo.getId())))
					iterator.remove();
			}
			//set the sequences
			List<AccountNOTo> accountList2=collectionsReportForm.getFinalAccList();
			Iterator<AccountNOTo> iterator2 = accountList2.iterator(); 
			//sequence count
			int count=1; 
			while (iterator2.hasNext()) {
				AccountNOTo accountNOTo = (AccountNOTo) iterator2.next();
				accountNOTo.setSequence(count);
				count++;
			}
			// sort as per sequence
			Collections.sort(collectionsReportForm.getFinalAccList());
		}	
		collectionsReportForm.setApplNoList(applNoList);
		collectionsReportForm.setRegNoList(RegNoList);
		String dynamicQuery = CollectionsHelper.buildQuery(collectionsReportForm,userId);
		List<PcReceipts> collectionsBo = transaction.getCollectionsDetails(dynamicQuery);
		List<DailyCollectionsTo> collectionsList =CollectionsHelper.copycollectionsBOToTO(collectionsBo,collectionsReportForm);
          log.info("Leaving into getAccountNo Details of of ColllectionHandeler");
			return  collectionsList;
			
	}
}
