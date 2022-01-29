package com.kp.cms.helpers.pettycash;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.bo.admin.PettyCashCollectionDetails;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;
import com.kp.cms.transactions.pettycash.IPettyCashAccountHeads;
import com.kp.cms.transactionsimpl.pettycash.PettyCashAccountHeadsImpl;
import com.kp.cms.utilities.CommonUtil;

public class PettyCashAccountHeadsHelper {
	
	private static volatile PettyCashAccountHeadsHelper pettyCashAccountHeadsHelper = null;
	private static final Log log = LogFactory.getLog(PettyCashAccountHeadGroupCodeHelper.class);
	private PettyCashAccountHeadsHelper() {

	}
	public static PettyCashAccountHeadsHelper getInstance() {
		if (pettyCashAccountHeadsHelper == null) {
			pettyCashAccountHeadsHelper = new PettyCashAccountHeadsHelper();
		}
		return pettyCashAccountHeadsHelper;
	}
	
	public PcAccountHead createBoObjcet(PettyCashAccountHeadsForm pettyCashAccountHeadsForm,PcAccountHead pcAccountHead,String mode)
	{
		log.info("entering into createBoObjcet in PettyCashAccountHeadsHelper class..");
		PcAccHeadGroup pcAccountHeadGroup=new PcAccHeadGroup();
		PcBankAccNumber pcBankAccountNumber=new PcBankAccNumber();
		
		
		pcAccountHead.setAccCode(pettyCashAccountHeadsForm.getAccCode());
		pcAccountHead.setAccName(pettyCashAccountHeadsForm.getAccName());
		pcAccountHead.setIsActive(!(mode.equalsIgnoreCase("delete")));
		pcAccountHead.setBankAccNo(pettyCashAccountHeadsForm.getBankAccNo());
		if(pettyCashAccountHeadsForm.getProgramName()!=null && !pettyCashAccountHeadsForm.getProgramName().isEmpty()){
		Program program=new Program();
		program.setId(Integer.parseInt(pettyCashAccountHeadsForm.getProgramName()));
		pcAccountHead.setProgramId(program);
		}
		if(pettyCashAccountHeadsForm.getAmount()!=null && !pettyCashAccountHeadsForm.getAmount().isEmpty())
		{
		pcAccountHead.setAmount(new BigDecimal(pettyCashAccountHeadsForm.getAmount()));
		}
		else
		{
			pcAccountHead.setAmount(null);
		}
		if(pettyCashAccountHeadsForm.getIsFixedAmount()==null)
		{
			pcAccountHead.setIsFixedAmount(false);
		}
		else
		{
			pcAccountHead.setIsFixedAmount(pettyCashAccountHeadsForm.getIsFixedAmount());
		}
		if(pettyCashAccountHeadsForm.getPcAccHeadGroupCodeId()!=null && !pettyCashAccountHeadsForm.getPcAccHeadGroupCodeId().isEmpty())
		{
		pcAccountHeadGroup.setId(Integer.valueOf(pettyCashAccountHeadsForm.getPcAccHeadGroupCodeId()));
		pcAccountHead.setPcAccHeadGroup(pcAccountHeadGroup);
		}
		else
		{
			pcAccountHead.setPcAccHeadGroup(null);
		}
		
		if(pettyCashAccountHeadsForm.getPcBankAccNumberId()!=null && !pettyCashAccountHeadsForm.getPcBankAccNumberId().isEmpty())
		pcBankAccountNumber.setId(Integer.valueOf(pettyCashAccountHeadsForm.getPcBankAccNumberId()));
		pcAccountHead.setPcBankAccNumber(pcBankAccountNumber);
		
		if(mode.equalsIgnoreCase("Add"))
		{
			pcAccountHead.setCreatedBy(pettyCashAccountHeadsForm.getUserId());
			pcAccountHead.setCreatedDate(new Date());
			pcAccountHead.setModifiedBy(pettyCashAccountHeadsForm.getUserId());
			pcAccountHead.setLastModifiedDate(new Date());
		}
		else
		{
			pcAccountHead.setModifiedBy(pettyCashAccountHeadsForm.getUserId());
			pcAccountHead.setLastModifiedDate(new Date());
		}
		log.info("leaving from createBoObjcet in PettyCashAccountHeadsHelper class..");
		return pcAccountHead;
	}
	
	
	public PettyCashAccountHeadsForm createFormObject(PettyCashAccountHeadsForm pcAccountHeadsForm,PcAccountHead pcAccountHead)
	{
		log.info("entering into createFormObject in PettyCashAccountHeadsHelper class..");
		if(pcAccountHead.getAccCode()!=null && !pcAccountHead.getAccCode().equals(""))
		{
		pcAccountHeadsForm.setAccCode(pcAccountHead.getAccCode());
		}
		if(pcAccountHead.getAccName()!=null && !pcAccountHead.getAccName().equals(""))
		{
		pcAccountHeadsForm.setAccName(pcAccountHead.getAccName());
		}
		
		pcAccountHeadsForm.setCreatedBy(pcAccountHead.getCreatedBy());
		pcAccountHeadsForm.setCreatedDate(pcAccountHead.getCreatedDate());
		if(pcAccountHead.getBankAccNo()!=null && !pcAccountHead.getBankAccNo().equals(""))
		{
		pcAccountHeadsForm.setBankAccNo(pcAccountHead.getBankAccNo());
		}
		if(pcAccountHead.getAmount()!=null)
		{
		pcAccountHeadsForm.setAmount(pcAccountHead.getAmount().toString());
		}
		pcAccountHeadsForm.setIsFixedAmount(pcAccountHead.getIsFixedAmount());
		pcAccountHeadsForm.setPcBankAccNumberId(String.valueOf(pcAccountHead.getPcBankAccNumber().getId()));
		if(pcAccountHead.getPcAccHeadGroup()!=null){
			pcAccountHeadsForm.setPcAccHeadGroupCodeId(String.valueOf(pcAccountHead.getPcAccHeadGroup().getId()));
		}
		log.info("leaving from createFormObject in PettyCashAccountHeadsHelper class..");
		return pcAccountHeadsForm;
	}
    public List<AccountHeadsTo> convertAccountHeadsBoToTO(List<AccountHeads> accountHeadsList){
    	List<AccountHeadsTo> accountHeadsToList=new ArrayList<AccountHeadsTo>();
    	if(accountHeadsList!=null && !accountHeadsList.isEmpty()){
    		Iterator<AccountHeads> itr=accountHeadsList.iterator();
    		while(itr.hasNext()){
    			AccountHeads heads=itr.next();
    			AccountHeadsTo headsTO=new AccountHeadsTo();
    			headsTO.setAcademicYear(String.valueOf(heads.getAcademicYear()));
    			if(heads.getAccCode()!=null && !heads.getAccCode().isEmpty())
    				headsTO.setAccCode(heads.getAccCode());
    			if(heads.getAccName()!=null && !heads.getAccName().isEmpty())
    				headsTO.setAccName(heads.getAccName());
    			if(heads.getFixedAmt()!=null){
    				if(heads.getFixedAmt())
    					headsTO.setFixedAmt("Yes");
    				else
    					headsTO.setFixedAmt("No");
    			}
    			if(heads.getAmount()!=null)
    				headsTO.setAmount(String.valueOf(heads.getAmount()));
    			if(heads.getAtDate()!=null){
    				String atDate=CommonUtil.ConvertStringToSQLDate2(heads.getAtDate().toString());
    				headsTO.setDate(atDate);
    			}
    			if(heads.getAtStation()!=null && !heads.getAtStation().isEmpty())
    				headsTO.setAtStation(heads.getAtStation());
    			if(heads.getAtTime()!=null && !heads.getAtTime().isEmpty())
    				headsTO.setAtTime(heads.getAtTime());
    			if(heads.getBankAccNo()!=0)
    				headsTO.setBankAccNo(String.valueOf(heads.getBankAccNo()));
    			if(heads.getUserCode()!=null && !heads.getUserCode().isEmpty())
    				headsTO.setUserCode(heads.getUserCode());
    			accountHeadsToList.add(headsTO);
    		}
    	}
    	return accountHeadsToList;
    }
    
    public List<PettyCashCollectionTo> convertCollectionDetailsBoToTO(List<PettyCashCollection> collectionList)throws Exception{
    	List<PettyCashCollectionTo> collectionToList=new ArrayList<PettyCashCollectionTo>();
        Iterator<PettyCashCollection> itr=collectionList.iterator();
        while(itr.hasNext()){
        	PettyCashCollection collection=itr.next();
        	PettyCashCollectionTo collectionTo=setValuesToCollectionTo(collection);
        	IPettyCashAccountHeads transaction=PettyCashAccountHeadsImpl.getInstance();
        	int size=0;
        	if(collection.getAplRegNo()!=null && !collection.getAplRegNo().isEmpty() && collection.getReceiptNo()!=0){
        	List<PettyCashCollectionDetails> details=transaction.getCollectionDetailsWithReceiptNoAndAppNo(collection.getReceiptNo(), collection.getAplRegNo());
        	Iterator<PettyCashCollectionDetails> itr1=details.iterator();
        	size=details.size();
        	while(itr1.hasNext()){
        		PettyCashCollectionDetails collection1=itr1.next();
        		
            if(size>1){
            	PettyCashCollectionTo collectionTo1=setValuesToCollectionTo(collection);
        	   if(collection1.getAmount()!=null){
        		   collectionTo1.setAmount(collection1.getAmount());
        	   } 
        	    if(collection1.getAccCode()!=null && !collection1.getAccCode().isEmpty()){
        		   collectionTo1.setAccCode(collection1.getAccCode());
        	    }
        	    if(collection1.getPuDgPg()!=null && !collection1.getPuDgPg().isEmpty()){
        		   collectionTo1.setPuDgPg(collection1.getPuDgPg());
        	    }
        	
        		collectionToList.add(collectionTo1);
            }else{
            	if(collection1.getAmount()!=null){
            		collectionTo.setAmount(collection1.getAmount());
            	}
            	if(collection1.getAccCode()!=null && !collection1.getAccCode().isEmpty()){
            		collectionTo.setAccCode(collection1.getAccCode());
            	}
            	if(collection1.getPuDgPg()!=null && !collection1.getPuDgPg().isEmpty()){
            		collectionTo.setPuDgPg(collection1.getPuDgPg());
            	}
            }
            	
        	}
        	}
        	if(size==1 || size==0)
        	     collectionToList.add(collectionTo);
        }
    	return collectionToList;
    }
    public PettyCashCollectionTo setValuesToCollectionTo(PettyCashCollection collection){
    	PettyCashCollectionTo collectionTo=new PettyCashCollectionTo();
    	collectionTo.setAcademicYear(String.valueOf(collection.getAcademicYear()));
    	if(collection.getAplRegNo()!=null && !collection.getAplRegNo().isEmpty()){
    		collectionTo.setAplRegNo(collection.getAplRegNo());
    	}
    	if(collection.getAtDate()!=null){
    		String date=CommonUtil.ConvertStringToSQLDate2(collection.getAtDate().toString());
    		collectionTo.setDate1(date);
    	}
    	if(collection.getAtStation()!=null && !collection.getAtStation().isEmpty())
    		collectionTo.setAtStation(collection.getAtStation());
    	if(collection.getAtTime()!=null && !collection.getAtTime().isEmpty())
    		collectionTo.setAtTime(collection.getAtTime());
    	if(collection.getName()!=null && !collection.getName().isEmpty())
    		collectionTo.setName(collection.getName());
    	if(collection.getReceiptNo()!=0)
    		collectionTo.setReceiptNo(String.valueOf(collection.getReceiptNo()));
    	if(collection.getUserCode()!=null && !collection.getUserCode().isEmpty())
    		collectionTo.setUserCode(collection.getUserCode());
    	if(collection.getDate()!=null)
    		collectionTo.setDate(collection.getDate());
    	if(collection.getTime()!=null && !collection.getTime().isEmpty())
    		collectionTo.setTime(collection.getTime());
    	return collectionTo;
    }
}
