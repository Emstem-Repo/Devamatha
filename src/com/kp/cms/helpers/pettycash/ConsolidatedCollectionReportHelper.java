package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionReportForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.ConsolidatedCollectionReportTO;
import com.kp.cms.to.pettycash.PcAccountTo;
import com.kp.cms.transactions.pettycash.IConsolidatedCollectionReport;
import com.kp.cms.transactionsimpl.pettycash.ConsolidatedCollectionReportTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


public class ConsolidatedCollectionReportHelper {
	/**
	 * Singleton object of CollectionLedgerHandler
	 */
	private static volatile ConsolidatedCollectionReportHelper consolidatedCollectionReportHelper = null;
	private static final Log log = LogFactory.getLog(ConsolidatedCollectionReportHelper.class);
	private ConsolidatedCollectionReportHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ConsolidatedCollectionReportHelper getInstance() {
		if (consolidatedCollectionReportHelper == null) {
			consolidatedCollectionReportHelper = new ConsolidatedCollectionReportHelper();
		}
		return consolidatedCollectionReportHelper;
	}
	/**
	 * converting the List of Object array to TO
	 * @param listofAccHead
	 * @return
	 * @throws Exception
	 */
	public List<ConsolidatedCollectionReportTO> convertListOfBOtoTO(List<Object[]> listofAccHead,ConsolidatedCollectionReportForm consolidatedCollectionReportForm) throws Exception{
		int position=0;
		Map<String,Integer> accPositionMap=new LinkedHashMap<String, Integer>(); 
		
		if(listofAccHead!=null){
			Iterator itr=listofAccHead.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				
				if(object[7]!=null){
					if(!accPositionMap.containsKey(object[7].toString())){
						++position;
						accPositionMap.put(object[7].toString(),position);
					}
				}
			}
		}
		IConsolidatedCollectionReport transaction=new ConsolidatedCollectionReportTransactionImpl();
		Map<Integer,String> groupNameMap=transaction.getGroupNameMap();
		return populateFinalCosoliatedCollectionReportList(accPositionMap,listofAccHead,consolidatedCollectionReportForm,groupNameMap);
	}
	
	private List<ConsolidatedCollectionReportTO> populateFinalCosoliatedCollectionReportList(
			Map<String, Integer> accPositionMap, List<Object[]> listofAccHead,
			ConsolidatedCollectionReportForm consolidatedCollectionReportForm,Map<Integer,String> groupNameMap)throws Exception {
		List<ConsolidatedCollectionReportTO> finalCollectionReportList=new ArrayList<ConsolidatedCollectionReportTO>();
		List<String> totalAccountList = new ArrayList<String>();
		Set<String> totalAccountSet = new LinkedHashSet<String>();
		List<AccountHeadTO> accountList = null;
		List<AccountHeadTO> allAccountList = new ArrayList<AccountHeadTO>();
		Map<Integer,Double> amountMap=new TreeMap<Integer, Double>();
		ConsolidatedCollectionReportTO reportTO = null;
		AccountHeadTO headTO = null;
		Map<Integer,Double> groupMap=new HashMap<Integer, Double>();
		
		totalAccountSet.addAll(accPositionMap.keySet());
		totalAccountList.addAll(totalAccountSet);
		if(totalAccountList!=null){
			Iterator<String> iterator = totalAccountList.iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				headTO = new AccountHeadTO();
				headTO.setAccName(string);
				allAccountList.add(headTO);
			}
		}
		consolidatedCollectionReportForm.setTotalAccountList(allAccountList);
		
		boolean isFixedAmount=false;
		double amount=0.0;
		double totalAmount=0.0;
		if(listofAccHead!=null){
			Iterator<Object[]> iterator = listofAccHead.iterator();
			while (iterator.hasNext()) {
				Object[] object = iterator.next();
				reportTO = new ConsolidatedCollectionReportTO();
				
				if(object[0]!=null){
					reportTO.setAccountCode(object[0].toString().trim());
				}
				if(object[1]!=null){
					reportTO.setAccountName(object[1].toString().trim());
				}
				if(object[2]!=null){
					reportTO.setTotalNumber(object[2].toString().trim());
				}
				if(object[4]!=null){
					isFixedAmount=(Boolean)object[4];
				}
				if(isFixedAmount){
					if(object[6]!=null){
					reportTO.setFixedAmount(object[6].toString());
					}
				}
				if(object[7]!=null){
					accountList = new ArrayList<AccountHeadTO>();
					
					int currentPosition = 1;
					int requiredPosition = accPositionMap.get(object[7].toString());
					int size = accPositionMap.size();
					
					if(currentPosition <= requiredPosition && requiredPosition <= size){
						for(int i=1; i<requiredPosition; i++){
							headTO = new AccountHeadTO();
							headTO.setAmount("");
							accountList.add(headTO);
						}
						headTO = new AccountHeadTO();
						if((object[4])== null){
							if(object[5]!=null){
								headTO.setAmount(object[5].toString());
								amount=Double.valueOf(object[5].toString());
								totalAmount=totalAmount+amount;
								reportTO.setAmount((Double.valueOf(object[5].toString())));
							}
						}
						else /*if(!isFixedAmount)*/{
							if(object[5]!=null){
								headTO.setAmount(object[5].toString());
								amount=Double.valueOf(object[5].toString());
								totalAmount=totalAmount+amount;
								reportTO.setAmount((Double.valueOf(object[5].toString())));
							}
						}
						/*else if (isFixedAmount){
							if(object[3]!=null){
								headTO.setAmount(object[3].toString());
								amount=Double.valueOf(object[3].toString());
								totalAmount=totalAmount+amount;
								reportTO.setAmount((Double.valueOf(object[3].toString())));
							}
						}*/
						
						if(object[8]!=null){
							int id=Integer.parseInt(object[8].toString());
							if(groupMap.containsKey(id)){
								double am=groupMap.remove(id);
								groupMap.put(id, amount+am);
							}else{
								groupMap.put(id, amount);
							}
						}
						
						if(amountMap.containsKey(requiredPosition)){
							double positionAmount=amountMap.get(requiredPosition);
							positionAmount=positionAmount+amount;
							amountMap.put(requiredPosition, positionAmount);
						}else{
							double positionAmount=amount;
							amountMap.put(requiredPosition, positionAmount);
						}
						accountList.add(headTO);
						if(accountList.size()< size){
							for(int i=accountList.size(); i < size; i++){
								headTO = new AccountHeadTO();
								headTO.setAmount("");
								accountList.add(headTO);
							}
						}
					}	
					reportTO.setAccountList(accountList);
					finalCollectionReportList.add(reportTO);
				}
			}
		}
		List<Double> totalAmountList = new ArrayList<Double>();
		Collection<Double> totalAmountSet = amountMap.values();
		 for (Iterator<Double> i = totalAmountSet.iterator(); i.hasNext();) {
			  Double key = (Double) i.next();
			  totalAmountList.add(key);
		    }
		consolidatedCollectionReportForm.setAmountList(totalAmountList);
		consolidatedCollectionReportForm.setTotalAmount(totalAmount);
		if(!groupMap.isEmpty()){
			Iterator it = groupMap.entrySet().iterator();
			StringBuffer sf=new StringBuffer();
			double a=0.0;
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        if(groupNameMap.containsKey(pairs.getKey())){
		        	sf.append(groupNameMap.get(pairs.getKey()));
		        	sf.append(":");
		        	sf.append(pairs.getValue());
		        	sf.append("  +");
		        	a=a+Double.parseDouble(pairs.getValue().toString());
		        }
		    }
		    consolidatedCollectionReportForm.setGroupCodeValue(StringUtils.chop(sf.toString())+" ="+a);
		}
		return finalCollectionReportList;
	}
	/**
	 * building the query
	 * @param collectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQueryForGroupCode(ConsolidatedCollectionReportForm consolidatedCollectionReportForm,int userId) throws Exception {
		String commonQuery=commonSearch();
		String searchQuery=commonQuery+" from PcReceipts p ";
			if(consolidatedCollectionReportForm.getGroupCode()!=null && !consolidatedCollectionReportForm.getGroupCode().isEmpty()){
				searchQuery=searchQuery+" where p.isCancelled=0 and p.isActive=1 and p.pcAccountHead.pcAccHeadGroup.code= '"+consolidatedCollectionReportForm.getGroupCode()+"'";
			}
			if(consolidatedCollectionReportForm.getStartDate()!=null && consolidatedCollectionReportForm.getEndDate()!=null 
					&& !consolidatedCollectionReportForm.getEndDate().isEmpty()&& !consolidatedCollectionReportForm.getStartDate().isEmpty())
			{
				searchQuery=searchQuery+" and p.paidDate between '"+CommonUtil.ConvertStringToSQLDate(consolidatedCollectionReportForm.getStartDate())+"'"+" and '"+CommonUtil.ConvertStringToSQLExactDate(consolidatedCollectionReportForm.getEndDate())+"'";
			}
			if(consolidatedCollectionReportForm.getProgramTypeId()!=null && !consolidatedCollectionReportForm.getProgramTypeId().isEmpty()){
				searchQuery=searchQuery+" and p.student.admAppln.courseBySelectedCourseId.program.programType.id="+consolidatedCollectionReportForm.getProgramTypeId();
			}
			if(userId!=0){
				searchQuery=searchQuery+"and p.users.id="+userId;
			}
			
			if(consolidatedCollectionReportForm.getGroupCode()!=null){
				searchQuery=searchQuery+" group by p.pcAccountHead.accCode order by p.pcAccountHead.accCode";
			}
			
		return searchQuery;
	}
	/**
	 * @param consolidatedCollectionReportForm
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getSearchQueryForAccountNumber(ConsolidatedCollectionReportForm consolidatedCollectionReportForm,int userId) throws Exception {
		String commonQuery=commonSearch();
		String searchQuery=commonQuery+" from PcReceipts p ";
		if (consolidatedCollectionReportForm.getAccountNumber().length > 0) {
			String [] tempArray = consolidatedCollectionReportForm.getAccountNumber();
			StringBuilder intType =new StringBuilder();
			for(int i=0;i<tempArray.length;i++){
				intType.append(tempArray[i]);
				 if(i<(tempArray.length-1)){
					 intType.append(",");
				 }
			}
		
		String interviewType = " where p.isCancelled=0 and p.isActive=1 and p.pcAccountHead.bankAccNo in ("+ intType +")";		
		searchQuery = searchQuery + interviewType;
		}
		if(consolidatedCollectionReportForm.getStartDate()!=null && consolidatedCollectionReportForm.getEndDate()!=null 
				&& !consolidatedCollectionReportForm.getEndDate().isEmpty()&& !consolidatedCollectionReportForm.getStartDate().isEmpty())
		{
			searchQuery=searchQuery+" and p.paidDate between '"+CommonUtil.ConvertStringToSQLDate(consolidatedCollectionReportForm.getStartDate())+"'"+" and '"+CommonUtil.ConvertStringToSQLExactDate(consolidatedCollectionReportForm.getEndDate())+"'";
		}
		if(consolidatedCollectionReportForm.getProgramTypeId()!=null && !consolidatedCollectionReportForm.getProgramTypeId().isEmpty()){
			searchQuery=searchQuery+" and p.student.admAppln.courseBySelectedCourseId.program.programType.id="+consolidatedCollectionReportForm.getProgramTypeId();
		}
		if(userId!=0){
			searchQuery=searchQuery+"and p.users.id="+userId;
		}
		
		if(consolidatedCollectionReportForm.getGroupCode()!=null){
			searchQuery=searchQuery+" group by p.pcAccountHead.accCode order by p.pcAccountHead.accCode";
		}

		return searchQuery;
	}
	/**
	 * common query for search
	 * @return
	 */
	private String commonSearch() {
		String commonQuery="select p.pcAccountHead.accCode," +
				"p.pcAccountHead.accName," +
				"count(p.pcAccountHead.amount)," +
				"sum(p.pcAccountHead.amount)," +
				"p.pcAccountHead.isFixedAmount," +
				"sum(p.amount)," +
				"p.pcAccountHead.amount," +
				"p.pcAccountHead.bankAccNo,p.pcAccountHead.pcAccHeadGroup.id ";
		return commonQuery;
	}
	/**
	 * converting the List of PcBankAccNumber to To
	 * @param listOfAccNo
	 * @return
	 */
	public List<PcAccountTo> convertBOtoTOList(List<PcBankAccNumber> listOfAccNo) {
		List<PcAccountTo> list=new ArrayList<PcAccountTo>();
		if(!listOfAccNo.isEmpty()){
			Iterator<PcBankAccNumber> itr=listOfAccNo.iterator();
			while (itr.hasNext()) {
				PcBankAccNumber pcBankAccNumber = (PcBankAccNumber) itr.next();
				PcAccountTo pto=new PcAccountTo();
				pto.setAccountNo(pcBankAccNumber.getAccountNo());
				pto.setId(pcBankAccNumber.getId());
				list.add(pto);
			}
		}
		return list;
	}
	/**
	 * keeping the account's in a position
	 * @param list
	 * @param accPositionMap
	 * @return
	 */
	public List<ConsolidatedCollectionReportTO> setAccountsPosition(List<ConsolidatedCollectionReportTO> list,Map<String,Integer> accPositionMap){
		if(list!=null && !list.isEmpty()){
			Iterator<ConsolidatedCollectionReportTO> it=list.iterator();
			while (it.hasNext()) {
				ConsolidatedCollectionReportTO cto = (ConsolidatedCollectionReportTO) it.next();
				if(cto.getAccNo()!=null){
					
				}
			}
		}
		return null;
	}
}
