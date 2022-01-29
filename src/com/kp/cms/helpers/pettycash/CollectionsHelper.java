package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.forms.pettycash.CollectionsReportForm;
import com.kp.cms.to.pettycash.AccountHeadTO;
import com.kp.cms.to.pettycash.AccountNOTo;
import com.kp.cms.to.pettycash.DailyCollectionsTo;
import com.kp.cms.utilities.CommonUtil;


public class CollectionsHelper {
	private static final Log log = LogFactory.getLog(CollectionsHelper.class);
	
	
	public static List<AccountNOTo> copyAcconoBOToTO(List<PcBankAccNumber> accontBo) {
		log.info("Entering copyAcconoBOToTO of CollectionsHelper");
		List<AccountNOTo> accontList = new ArrayList<AccountNOTo>();
		AccountNOTo accountTo;
		Iterator<PcBankAccNumber> accontIterator = accontBo.iterator();
			while (accontIterator.hasNext()) {
				PcBankAccNumber accountHead = (PcBankAccNumber) accontIterator.next();
				accountTo = new AccountNOTo();
				accountTo.setBankAccNo(accountHead.getAccountNo());
		    	accountTo.setId(accountHead.getId());
				accontList.add(accountTo);
			}	
		
		return accontList;
		}


	public static String buildQuery(CollectionsReportForm collectionsReportForm,int userId) {
		String searchCriteria = "";
		searchCriteria = "from PcReceipts pcr  where pcr.isActive = 1 ";
		if (collectionsReportForm.getStartDate().length() > 0) {
			String date = " and pcr.paidDate between '"+ CommonUtil.ConvertStringToSQLDate(collectionsReportForm.getStartDate())+" 00:00:00.0'"+" and '"+CommonUtil.ConvertStringToSQLDate(collectionsReportForm.getEndDate())+" 23:59:59.0'";
			
			
			
			searchCriteria = searchCriteria + date;
		}
		if (collectionsReportForm.getAllORCancel().length() > 0) {
				if(collectionsReportForm.getAllORCancel().equals("true")){
						String allORCancel = " and pcr.isCancelled = 1 ";
						searchCriteria = searchCriteria + allORCancel;
				}else{
					String allORCancel = " and pcr.isCancelled = 0 ";
					searchCriteria = searchCriteria + allORCancel;
				}
		}

		if (collectionsReportForm.getUserType().length() > 0) {
			if(collectionsReportForm.getUserType().equalsIgnoreCase("currentuser")){
 				String userType = " and pcr.users.id = "+collectionsReportForm.getUserId();
				searchCriteria = searchCriteria + userType;
			}else{
				if(collectionsReportForm.getUserType().equalsIgnoreCase("otheruser")){
					if(collectionsReportForm.getOtherName().length() > 0){
		 				String userType = " and pcr.users.id= "+userId;
						searchCriteria = searchCriteria + userType;
					}
				}
			}
		}
		
		
		
		if (collectionsReportForm.getAppiNo() != null && collectionsReportForm.getAppiNo().length > 0) {
		
			List<AccountNOTo> accountList2=collectionsReportForm.getFinalAccList();
			Iterator<AccountNOTo> iterator2 = accountList2.iterator(); 
			int i =0;
			int j =accountList2.size();
		
		//	    List For Query 
			
			StringBuffer sb =  new StringBuffer();
			while (iterator2.hasNext()) {
				AccountNOTo accountNOTo = (AccountNOTo) iterator2.next();
				  sb.append("'");
	         sb.append(accountNOTo.getBankAccNo());
	         sb.append("'");
					if(i!=(j-1)){
					sb.append(",");
				
					}
					i++;
								
			}
			searchCriteria = searchCriteria+" and pcr.pcAccountHead.bankAccNo in("+ sb.toString() +")";
		}
		if (collectionsReportForm.getOrderofReport().length() > 0) {
			String sortbase =" order by pcr."+collectionsReportForm.getOrderofReport();
			searchCriteria=searchCriteria+sortbase;
		}

		return searchCriteria;
	}

	
	public static List<DailyCollectionsTo> copycollectionsBOToTO(List<PcReceipts> collectionsBo,CollectionsReportForm collectionsReportForm) {
		List<String> totalAccountList = new ArrayList<String>();
		Set<String> totalAccountSet = new LinkedHashSet<String>();
		List<AccountHeadTO> allAccountList = new ArrayList<AccountHeadTO>();
		Map<String,Integer> accPositionMap; 
		accPositionMap=getAccountPositon(collectionsBo);
		totalAccountSet.addAll(accPositionMap.keySet());
		totalAccountList.addAll(totalAccountSet);
		if(totalAccountList!=null){
			Iterator<String> iterator = totalAccountList.iterator();
			while (iterator.hasNext()) {
				String string = iterator.next();
				AccountHeadTO headTO = new AccountHeadTO();
				headTO.setAccName(string);
				allAccountList.add(headTO);
			}
		}
		collectionsReportForm.setTotalAccountList(allAccountList);
		List<DailyCollectionsTo> collectionsList = new ArrayList<DailyCollectionsTo>();
		DailyCollectionsTo dailyCollectionsTo;
		double amount = 0.0;
		double totalAmount=0.0;
		//String comments=null;
		String accountNO=null;
		Map<Integer,Double> amountMap=new TreeMap<Integer, Double>();
		if(collectionsBo!=null){
			Iterator<PcReceipts> iterator = collectionsBo.iterator();
			while (iterator.hasNext()) {
				PcReceipts pcr = iterator.next();
				dailyCollectionsTo = new DailyCollectionsTo();
				if(pcr.getStudent() != null){
					dailyCollectionsTo.setStudentId(pcr.getStudent().getId());
					String appln="";
					String Reg="";
					String RefNo="";
					if(pcr.getStudent().getAdmAppln()!=null){
						appln=String.valueOf(pcr.getStudent().getAdmAppln().getApplnNo());
					}
					if(pcr.getStudent().getRegisterNo() != null){
						Reg=pcr.getStudent().getRegisterNo();
				    }if(pcr.getRefNo()!=null && !pcr.getRefNo().isEmpty()){
						RefNo=String.valueOf(pcr.getRefNo());
					}if(appln.isEmpty()){
						dailyCollectionsTo.setApplicationNo(RefNo);
					}
					dailyCollectionsTo.setApplicationNo(appln);
					dailyCollectionsTo.setRegNo(Reg);
					if(pcr.getStudent().getAdmAppln().getCourseBySelectedCourseId() != null){
						
						dailyCollectionsTo.setCoursecode(pcr.getStudent().getAdmAppln().getCourseBySelectedCourseId().getCode());
						dailyCollectionsTo.setClassname(pcr.getStudent().getClassSchemewise().getClasses().getName());		
					}
				}
					if(String.valueOf(pcr.getNumber())!=null && pcr.getNumber() > 0){
					dailyCollectionsTo.setReceiptOrStudentnum(String.valueOf(pcr.getNumber()));
				}
				if(dailyCollectionsTo.getApplicationNo()==null || dailyCollectionsTo.getApplicationNo().isEmpty()){
					dailyCollectionsTo.setApplicationNo(pcr.getRefNo());
				}
				// avoiding comments
				if(collectionsReportForm.getAllORCancel().equals("true")){
								
				/*if(pcr.getCancelComments() != null){
					comments=pcr.getCancelComments();
				}*/
				}
					
				if(pcr.getName()!= null){
						dailyCollectionsTo.setName(pcr.getName());
				}
				if(pcr.getPaidDate() != null){
					String pdate = pcr.getPaidDate().toString();
					 pdate.substring(0,10);
					 dailyCollectionsTo.setDate(pdate.substring(0,10));
					 dailyCollectionsTo.setTime(pdate.substring(11,16));
				}
				if(pcr.getLastModifiedDate() != null){
					dailyCollectionsTo.setLastModifiedDate(pcr.getLastModifiedDate().toString());
				}
				
				AccountHeadTO accountHeadTO = new AccountHeadTO();
				if(pcr.getPcAccountHead() != null){
					PcAccountHead pch=pcr.getPcAccountHead();
					if(pch.getAccName()!=null){
						dailyCollectionsTo.setAccountName(pch.getAccName());
					}
					if(pch.getBankAccNo() != null){						
						accountHeadTO.setBankAccNo(pch.getBankAccNo());
						accountNO=pch.getBankAccNo();
					}
					if(pch.getPcBankAccNumber()!=null){
						accountHeadTO.setId(String.valueOf(pch.getPcBankAccNumber().getId()));
					}
					
				}
				//set the sequence as it is from master accunt list
				List<AccountNOTo> accountList2=collectionsReportForm.getFinalAccList();
				Iterator<AccountNOTo> iterator2 = accountList2.iterator(); 
				while (iterator2.hasNext()) {
					AccountNOTo accountNOTo = (AccountNOTo) iterator2.next();
					if(accountHeadTO.getId()!=null && 
						accountHeadTO.getId().equals(String.valueOf(accountNOTo.getId()))){
						accountHeadTO.setSequence(accountNOTo.getSequence());
						if(pcr.getAmount() != null){
							amount=Double.parseDouble(pcr.getAmount().toString());
							accountHeadTO.setAmount(pcr.getAmount().toString());
							totalAmount=totalAmount+amount;
							
						}
					}
				}
				List<AccountHeadTO> accounts=new ArrayList<AccountHeadTO>();
				int currentSequence=1;
				int requiredPosition = accPositionMap.get(accountNO);
				int size = accPositionMap.size();
				if(currentSequence<=requiredPosition && requiredPosition<= size){
					for(int i=1; i<requiredPosition; i++){
						AccountHeadTO blankTO = new AccountHeadTO();
						blankTO.setSequence(i);
						blankTO.setAmount("");
						accounts.add(blankTO);
					}
					accounts.add(accountHeadTO);
					if(amountMap.containsKey(requiredPosition)){
						double positionAmount=amountMap.get(requiredPosition);
						positionAmount=positionAmount+amount;
						amountMap.put(requiredPosition, positionAmount);
					}else{
						double positionAmount=amount;
						amountMap.put(requiredPosition, positionAmount);
					}
					if(accounts.size()< size){
						for(int i=accounts.size(); i < size; i++){
							AccountHeadTO blankTO = new AccountHeadTO();
							blankTO.setSequence(i);
							blankTO.setAmount("");
							accounts.add(blankTO);
						}
					}
				}
				dailyCollectionsTo.setAccountHeadList(accounts);
				collectionsList.add(dailyCollectionsTo);	
				}
			}
		List<Double> totalAmountList = new ArrayList<Double>();
		Collection<Double> totalAmountSet = amountMap.values();
		 for (Iterator<Double> i = totalAmountSet.iterator(); i.hasNext();) {
			  Double key = (Double) i.next();
			  totalAmountList.add(key);
		    }
		collectionsReportForm.setTotalAmountList(totalAmountList);
		collectionsReportForm.setTotamount(totalAmount);
		return collectionsList;
	}


	private static Map<String, Integer> getAccountPositon(
			List<PcReceipts> collectionsBo) {
		Map<String, Integer> accPositionMap=new LinkedHashMap<String, Integer>();
		int position=0;
		if(collectionsBo!=null){
			Iterator<PcReceipts> itr=collectionsBo.iterator();
			while (itr.hasNext()) {
				PcReceipts pcReceipts = (PcReceipts) itr.next();
				if(pcReceipts.getPcAccountHead().getPcBankAccNumber()!=null){
					if(!accPositionMap.containsKey(pcReceipts.getPcAccountHead().getPcBankAccNumber().getAccountNo())){
						++position;
						accPositionMap.put(pcReceipts.getPcAccountHead().getPcBankAccNumber().getAccountNo(),position);
					}
				}
			}
		}
		return accPositionMap;
	}
}	