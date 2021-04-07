package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AccountHeads;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcBankAccNumber;
import com.kp.cms.bo.admin.PettyCashCollection;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.helpers.pettycash.PettyCashAccountHeadsHelper;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;
import com.kp.cms.to.pettycash.PettyCashAccountHeadsTO;
import com.kp.cms.to.pettycash.PettyCashAccountNumberTO;
import com.kp.cms.to.pettycash.PettyCashCollectionTo;
import com.kp.cms.transactions.pettycash.IPettyCashAccountHeads;
import com.kp.cms.transactionsimpl.pettycash.PettyCashAccountHeadsImpl;

public class PettyCashAccountHeadsHandler {
	
	private static final Log log=LogFactory.getLog(PettyCashAccountHeadsHandler.class);
	private static volatile PettyCashAccountHeadsHandler pettyCashAccountHeadsHandler=null;
	PettyCashAccountHeadsTO pettyCashAccountHeadsTO=null;
	PettyCashAccountHeadGroupCodeTO pettyCashAccountHeadGroupCodeTO=null;
	PettyCashAccountNumberTO pettyCashAccountNumberTO=null;
	IPettyCashAccountHeads pettyCashAccountHeads = PettyCashAccountHeadsImpl.getInstance();
	PettyCashAccountHeadsHelper pettyCashAccountHelper=PettyCashAccountHeadsHelper.getInstance();
	List<PettyCashAccountHeadsTO> list=null;
	List<PcAccHeadGroup> pcAccHeadGroupList=null;
	List<PcBankAccNumber> pcBankAccNumberList=null;
	PcAccountHead pcAccountHead=null;
	PcAccountHead temppcAccountHead=null;
	
	public static PettyCashAccountHeadsHandler getInstance() {
		   if(pettyCashAccountHeadsHandler == null ){
			   pettyCashAccountHeadsHandler = new PettyCashAccountHeadsHandler();
			   return pettyCashAccountHeadsHandler;
		   }
		   return pettyCashAccountHeadsHandler;
	}
	
	
	public List<PettyCashAccountHeadsTO> getAllPettyCashAccHeads() throws Exception
	{
		log.info("entering into getAllPettyCashAccHeads in PettyCashAccountHeadsHandler class..");
		list=new ArrayList<PettyCashAccountHeadsTO>();
		List<PcAccountHead> pettyCashAccountHeadList = pettyCashAccountHeads.getAllpettyCashAccHeads();
		if(pettyCashAccountHeadList!=null && !pettyCashAccountHeadList.isEmpty())
		{
		for(PcAccountHead pcAccountHead:pettyCashAccountHeadList)
		{
			pettyCashAccountHeadsTO=new PettyCashAccountHeadsTO();
			pettyCashAccountHeadsTO.setId(Integer.valueOf(pcAccountHead.getId()));
			if(pcAccountHead.getAccCode()!=null && !pcAccountHead.getAccCode().isEmpty())
			{
			pettyCashAccountHeadsTO.setAccCode(pcAccountHead.getAccCode());
			}
			if(pcAccountHead.getAccName()!=null && !pcAccountHead.getAccName().equals(""))
			{
			pettyCashAccountHeadsTO.setAccName(pcAccountHead.getAccName());
			}
			if(pcAccountHead.getBankAccNo()!=null && !pcAccountHead.getBankAccNo().isEmpty())
			{
			pettyCashAccountHeadsTO.setBankAccNo(pcAccountHead.getBankAccNo());
			}
			if(pcAccountHead.getAmount()!=null)
			{
			pettyCashAccountHeadsTO.setAmount(String.valueOf(pcAccountHead.getAmount()));
			}
			if(pcAccountHead.getIsFixedAmount()!=null)
			{
				if(pcAccountHead.getIsFixedAmount())
				{
					pettyCashAccountHeadsTO.setIsActives("Yes");
				}
				else{
					pettyCashAccountHeadsTO.setIsActives("No");
				}
			}
			else{
				pettyCashAccountHeadsTO.setIsActives("No");
			}

			if(pcAccountHead.getPcAccHeadGroup()!=null){
				pettyCashAccountHeadsTO.setPcAccHeadGroupCodeId(pcAccountHead.getPcAccHeadGroup().getId());
			}
			
			if(pcAccountHead!=null && pcAccountHead.getPcBankAccNumber()!=null && Integer.valueOf(pcAccountHead.getPcBankAccNumber().getId())!=null && Integer.valueOf(pcAccountHead.getPcBankAccNumber().getId())!=null )
			{
			pettyCashAccountHeadsTO.setPcBankAccNumberId(pcAccountHead.getPcBankAccNumber().getId());
			}
			
			if(pcAccountHead.getPcAccHeadGroup()!=null && pcAccountHead.getPcAccHeadGroup().getName()!=null && !pcAccountHead.getPcAccHeadGroup().getName().equals(""))
			{
				pettyCashAccountHeadsTO.setGroupName(pcAccountHead.getPcAccHeadGroup().getName());
			}
			if(pcAccountHead.getPcBankAccNumber()!=null && !pcAccountHead.getPcBankAccNumber().getAccountNo().equals(""))
			{
				pettyCashAccountHeadsTO.setPcBankAccNumber(pcAccountHead.getPcBankAccNumber().getAccountNo());
			}
			if(pcAccountHead.getProgramId()!=null && !pcAccountHead.getProgramId().toString().isEmpty()){
				pettyCashAccountHeadsTO.setProgramName(pcAccountHead.getProgramId().getName());
			}
			pettyCashAccountHeadsTO.setCreatedBy(pcAccountHead.getCreatedBy());
			pettyCashAccountHeadsTO.setCreatedDate(pcAccountHead.getCreatedDate());
			pettyCashAccountHeadsTO.setModifiedBy(pcAccountHead.getModifiedBy());
			pettyCashAccountHeadsTO.setLastModifiedDate(pcAccountHead.getLastModifiedDate());
			pettyCashAccountHeadsTO.setIsActive(pcAccountHead.getIsActive());
			list.add(pettyCashAccountHeadsTO);
		}
		}
		log.debug("leaving from getAllPettyCashAccHeads in PettyCashAccountHeadsHandler class..");
		return list;	
	}
	
	public List<PettyCashAccountHeadGroupCodeTO> getAllpettyCashAccHeadGroup() throws Exception{
		log.info("entering into getAllpettyCashAccHeadGroup in PettyCashAccountHeadsHandler class..");
		List<PettyCashAccountHeadGroupCodeTO> list=new ArrayList<PettyCashAccountHeadGroupCodeTO>();
		pcAccHeadGroupList=pettyCashAccountHeads.getAllpettyCashAccHeadGroup();
		for(PcAccHeadGroup pcHeadGroup:pcAccHeadGroupList)
		{
			pettyCashAccountHeadGroupCodeTO=new PettyCashAccountHeadGroupCodeTO();
			pettyCashAccountHeadGroupCodeTO.setId(pcHeadGroup.getId());
			pettyCashAccountHeadGroupCodeTO.setGroupCode(pcHeadGroup.getCode());
			pettyCashAccountHeadGroupCodeTO.setGroupName(pcHeadGroup.getName());
		    list.add(pettyCashAccountHeadGroupCodeTO);
		}
		log.info("leaving from getAllpettyCashAccHeadGroup in PettyCashAccountHeadsHandler class..");
		return list;
	}
	
	public List<PettyCashAccountNumberTO> getAllpettyCashBankAccNumber() throws Exception{
		log.info("entering into getAllpettyCashBankAccNumber in PettyCashAccountHeadsHandler class..");
		List<PettyCashAccountNumberTO> list=new ArrayList<PettyCashAccountNumberTO>();
		pcBankAccNumberList=pettyCashAccountHeads.getAllpettyCashBankAccNumber();
		for(PcBankAccNumber pcBankAccNumber:pcBankAccNumberList)
		{
			pettyCashAccountNumberTO=new PettyCashAccountNumberTO();
			pettyCashAccountNumberTO.setId(pcBankAccNumber.getId());
			if(pcBankAccNumber.getAccountNo()!=null && !pcBankAccNumber.getAccountNo().isEmpty())
			{
			pettyCashAccountNumberTO.setBankAccountNo(pcBankAccNumber.getAccountNo());
			}
			list.add(pettyCashAccountNumberTO);
		}
		log.info("leaving from getAllpettyCashBankAccNumber in PettyCashAccountHeadsHandler class..");
		return list;
	}

	public boolean manageAccountHead(PettyCashAccountHeadsForm pettyCashAccHeadForm,String mode) throws Exception
	{
		log.info("entering into manageAccountHead in  PettyCashAccountHeadsHandler class..");
		PcBankAccNumber pcAccNumber=pettyCashAccountHeads.getPettyCashBankAccNumberById(pettyCashAccHeadForm.getPcBankAccNumberId());
		if(pcAccNumber.getAccountNo()!=null && !pcAccNumber.getAccountNo().isEmpty())
		{
		pettyCashAccHeadForm.setBankAccNo(pcAccNumber.getAccountNo());
		}
		PcAccountHead typecheckpcAccHead=null;
		String formAccCode=pettyCashAccHeadForm.getAccCode();
		log.info("Start of addPettyCashAccountHeads");
		if(mode.equalsIgnoreCase("Update"))
		{
			temppcAccountHead=pettyCashAccountHeads.getPettyCashAccountHead(pettyCashAccHeadForm.getId());
			if(temppcAccountHead!=null)
			{
				pcAccountHead=new PcAccountHead();
				//String formAccountCode=pettyCashAccHeadForm.getAccCode();
				pcAccountHead=(PcAccountHead)pettyCashAccountHelper.createBoObjcet(pettyCashAccHeadForm,temppcAccountHead,mode);
				
			}
		}
		else if(mode.equalsIgnoreCase("Add"))
		{
			pcAccountHead=new PcAccountHead();
			pcAccountHead=(PcAccountHead)pettyCashAccountHelper.createBoObjcet(pettyCashAccHeadForm,pcAccountHead,mode);
			typecheckpcAccHead=pettyCashAccountHeads.existanceCheck(formAccCode);
		}
		if(typecheckpcAccHead!=null)
		{
			throw new DuplicateException();
		}
		pettyCashAccountHeads.manageAccountHead(pcAccountHead, mode);
		
		log.info("leaving from manageAccountHead in  PettyCashAccountHeadsHandler class..");
		return true;
	}
	
	public boolean deleteAccountHead(PettyCashAccountHeadsForm pettyCashAccHeadForm,String mode) throws Exception
	{
		log.info("entering into deleteAccountHead in PettyCashAccountHeadsHandler class..");
		pcAccountHead=new PcAccountHead();
		temppcAccountHead=pettyCashAccountHeads.getPettyCashAccountHead(pettyCashAccHeadForm.getId());
		if(temppcAccountHead!=null)
		{
		pettyCashAccHeadForm=pettyCashAccountHelper.createFormObject(pettyCashAccHeadForm, temppcAccountHead);
		}
		pcAccountHead=(PcAccountHead)pettyCashAccountHelper.createBoObjcet(pettyCashAccHeadForm,temppcAccountHead,mode);
		pettyCashAccountHeads.manageAccountHead(pcAccountHead, mode);
		log.info("leaving from deleteAccountHead in PettyCashAccountHeadsHandler class..");
		return true;
	}
	
	
	public void editAccountHead(PettyCashAccountHeadsForm pettyCashAccHeadForm)throws Exception
	{
		log.info("entering into editAccountHead in PettyCashAccountHeadsHandler class..");
		temppcAccountHead=pettyCashAccountHeads.getPettyCashAccountHead(pettyCashAccHeadForm.getId());
		if(temppcAccountHead!=null)
		{
			
			if(temppcAccountHead.getAccCode()!=null && !temppcAccountHead.getAccCode().isEmpty())
			{
			pettyCashAccHeadForm.setAccCode(temppcAccountHead.getAccCode());
			pettyCashAccHeadForm.setOldAccountCode(temppcAccountHead.getAccCode());
			}
			if(temppcAccountHead.getAccName()!=null && !temppcAccountHead.getAccName().isEmpty())
			{
			pettyCashAccHeadForm.setAccName(temppcAccountHead.getAccName());
			}
			if(temppcAccountHead.getBankAccNo()!=null && !temppcAccountHead.getBankAccNo().isEmpty())
			{
			pettyCashAccHeadForm.setBankAccNo(temppcAccountHead.getBankAccNo());
			}
			if(temppcAccountHead.getAmount()!=null)
			{
			pettyCashAccHeadForm.setAmount(temppcAccountHead.getAmount().toString());
			}
			if(temppcAccountHead.getIsFixedAmount()!=null)
			{
			pettyCashAccHeadForm.setIsFixedAmount(temppcAccountHead.getIsFixedAmount());
			}
			if(temppcAccountHead.getPcAccHeadGroup()!=null){
				pettyCashAccHeadForm.setPcAccHeadGroupCodeId(String.valueOf(temppcAccountHead.getPcAccHeadGroup().getId()));
			}
			if(temppcAccountHead.getPcBankAccNumber()!=null){
				pettyCashAccHeadForm.setPcBankAccNumberId(String.valueOf(temppcAccountHead.getPcBankAccNumber().getId()));
			}
			if(Integer.valueOf(temppcAccountHead.getId())!=null)
			{
				pettyCashAccHeadForm.setOldId(temppcAccountHead.getId());
			}if(temppcAccountHead.getProgramId()!=null && !temppcAccountHead.getProgramId().toString().isEmpty()){
				//pettyCashAccHeadForm.setProgramName(temppcAccountHead.getProgramId().getName());
				pettyCashAccHeadForm.setProgramName(String.valueOf(temppcAccountHead.getProgramId().getId()));
				
			}
			/*pettyCashAccHeadForm.setCreatedBy(temppcAccountHead.getCreatedBy());
			pettyCashAccHeadForm.setCreatedDate(temppcAccountHead.getCreatedDate());
			pettyCashAccHeadForm.setModifiedBy(temppcAccountHead.getModifiedBy());
			pettyCashAccHeadForm.setLastModifiedDate(temppcAccountHead.getLastModifiedDate());*/
		}
		log.info("leaving from editAccountHead in PettyCashAccountHeadsHandler class..");
	}


	public PcAccountHead existanceCheck(
			String accountCode)throws Exception {
		PcAccountHead head=pettyCashAccountHeads.existanceCheck(accountCode);
		return head;
	}
	
	public List<AccountHeadsTo> getAccountHeads(PettyCashAccountHeadsForm pettyCashAccHeadForm)throws Exception{
		List<AccountHeads> accountHeadsList=pettyCashAccountHeads.getAccountHeadsDetails(pettyCashAccHeadForm);
		List<AccountHeadsTo> accountHeadsTOList=PettyCashAccountHeadsHelper.getInstance().convertAccountHeadsBoToTO(accountHeadsList);
		return accountHeadsTOList;
	}
	
	public List<PettyCashCollectionTo> getCollections(PettyCashAccountHeadsForm pettyCashAccHeadForm)throws Exception{
		List<PettyCashCollection> pettyCashCollection=pettyCashAccountHeads.getCollectionDetails(pettyCashAccHeadForm);
		List<PettyCashCollectionTo> collectionToList=PettyCashAccountHeadsHelper.getInstance().convertCollectionDetailsBoToTO(pettyCashCollection);
		return collectionToList;
	}
}
