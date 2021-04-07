package com.kp.cms.handlers.pettycash;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.PcAccHeadGroup;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadGroupCodeForm;
import com.kp.cms.helpers.pettycash.PettyCashAccountHeadGroupCodeHelper;
import com.kp.cms.to.pettycash.PettyCashAccountHeadGroupCodeTO;
import com.kp.cms.transactions.pettycash.IPettyCashAccountHeadGroupCode;
import com.kp.cms.transactionsimpl.pettycash.PettyCashAccountHeadGroupCodeImpl;

public class PettyCashAccountHeadGroupCodeHandler {
	
	private static final Log log=LogFactory.getLog(PettyCashAccountHeadGroupCodeHandler.class);
	private static volatile PettyCashAccountHeadGroupCodeHandler pettyCashAccountHeadCodeHandler=null;
	PettyCashAccountHeadGroupCodeTO pettyCashAccountHeadGroupCodeTO=null;
	IPettyCashAccountHeadGroupCode pettyCashAccountHeadGroupCode = PettyCashAccountHeadGroupCodeImpl.getInstance();
	List<PettyCashAccountHeadGroupCodeTO> list=null;
	PcAccHeadGroup pcAccHeadGroup=null;
	PcAccHeadGroup temppcAccHeadGroup=null;

	
	public static PettyCashAccountHeadGroupCodeHandler getInstance() {
		   if(pettyCashAccountHeadCodeHandler == null ){
			   pettyCashAccountHeadCodeHandler = new PettyCashAccountHeadGroupCodeHandler();
			   return pettyCashAccountHeadCodeHandler;
		   }
		   return pettyCashAccountHeadCodeHandler;
	}
	
	public List<PettyCashAccountHeadGroupCodeTO> getAllPettyCashAccHeadGroupCode() throws Exception
	{
		list=new ArrayList<PettyCashAccountHeadGroupCodeTO>();
		log.debug("inside getPettyCashAccHeadGroupCode");
		List<PcAccHeadGroup> pettyCashAccountGroupCodeList = pettyCashAccountHeadGroupCode.getAllpettyCashAccHeadGroupCode();
		if(pettyCashAccountGroupCodeList!=null && !pettyCashAccountGroupCodeList.isEmpty())
		{
		for(PcAccHeadGroup pcAccHeadGroup:pettyCashAccountGroupCodeList)
		{
			pettyCashAccountHeadGroupCodeTO=new PettyCashAccountHeadGroupCodeTO();
			pettyCashAccountHeadGroupCodeTO.setGroupCode(pcAccHeadGroup.getCode());
			pettyCashAccountHeadGroupCodeTO.setGroupName(pcAccHeadGroup.getName());
			pettyCashAccountHeadGroupCodeTO.setId(Integer.valueOf(pcAccHeadGroup.getId()));
			list.add(pettyCashAccountHeadGroupCodeTO);
		}
		}
		log.debug("leaving PettyCashAccHeadGroupCode");
		return list;	
	}
	
	
	public boolean manageAccountHeadGroupCode(PettyCashAccountHeadGroupCodeForm pettyCashAccGroupCodeForm,String mode) throws Exception
	{
		
		
		PcAccHeadGroup typecheckpcAccHeadGroup=null;
		log.info("Start of managePettyCashAccountHeadGroupCode");
		if(mode.equalsIgnoreCase("Update"))
		{
			temppcAccHeadGroup=pettyCashAccountHeadGroupCode.getPettyCashAccGroupCode(pettyCashAccGroupCodeForm.getId());
			if(temppcAccHeadGroup!=null)
			{
				pcAccHeadGroup=new PcAccHeadGroup();
				//String formGroupCode=pettyCashAccGroupCodeForm.getGroupCode();
				pcAccHeadGroup=(PcAccHeadGroup)PettyCashAccountHeadGroupCodeHelper.getInstance().createBoObjcet(pettyCashAccGroupCodeForm,temppcAccHeadGroup,mode);
			}
		}
		else if(mode.equalsIgnoreCase("Add"))
		{
			pcAccHeadGroup=new PcAccHeadGroup();
			pcAccHeadGroup=(PcAccHeadGroup)PettyCashAccountHeadGroupCodeHelper.getInstance().createBoObjcet(pettyCashAccGroupCodeForm,pcAccHeadGroup,mode);
			typecheckpcAccHeadGroup=pettyCashAccountHeadGroupCode.existanceCheck(pcAccHeadGroup.getCode());
		}
	
		if(typecheckpcAccHeadGroup!=null)
		{
			throw new DuplicateException();
		}
		pettyCashAccountHeadGroupCode.manageAccountHeadGroupCode(pcAccHeadGroup, mode);
		log.info("End of managePettyCashAccountHeadGroupCode");
		return true;
	}
	
	public boolean deleteAccountHeadGroupCode(PettyCashAccountHeadGroupCodeForm pettyCashAccGroupCodeForm,String mode) throws Exception
	{
		log.info("entering into managePettyCashAccountHeadGroupCode");
		pcAccHeadGroup=new PcAccHeadGroup();
		temppcAccHeadGroup=pettyCashAccountHeadGroupCode.getPettyCashAccGroupCode(pettyCashAccGroupCodeForm.getId());
		pettyCashAccGroupCodeForm=PettyCashAccountHeadGroupCodeHelper.getInstance().createFormObject(pettyCashAccGroupCodeForm, temppcAccHeadGroup);
		pcAccHeadGroup=(PcAccHeadGroup)PettyCashAccountHeadGroupCodeHelper.getInstance().createBoObjcet(pettyCashAccGroupCodeForm,temppcAccHeadGroup,mode);
		pettyCashAccountHeadGroupCode.manageAccountHeadGroupCode(pcAccHeadGroup, mode);
		log.info("leaving from managePettyCashAccountHeadGroupCode");
		return true;
	}
	
	
	public void editAccountHeadGroupCode(Integer id,PettyCashAccountHeadGroupCodeForm pcAccHeadGroupCodeForm)throws Exception
	{
		log.info("entering into editAccountHeadGroupCode in PettyCashAccountHeadGroupCodeHandler..");
		temppcAccHeadGroup=pettyCashAccountHeadGroupCode.getPettyCashAccGroupCode(id);
		if(temppcAccHeadGroup!=null)
		{
			pcAccHeadGroupCodeForm.setId(new Integer(String.valueOf(temppcAccHeadGroup.getId())));
			pcAccHeadGroupCodeForm.setGroupCode(temppcAccHeadGroup.getCode());
			pcAccHeadGroupCodeForm.setGroupName(temppcAccHeadGroup.getName());
			pcAccHeadGroupCodeForm.setCreatedBy(temppcAccHeadGroup.getCreatedBy());
			pcAccHeadGroupCodeForm.setCreatedDate(temppcAccHeadGroup.getCreatedDate());
			pcAccHeadGroupCodeForm.setModifiedBy(temppcAccHeadGroup.getModifiedBy());
			pcAccHeadGroupCodeForm.setLastModifiedDate(temppcAccHeadGroup.getLastModifiedDate());
			pcAccHeadGroupCodeForm.setOldGroupCode(temppcAccHeadGroup.getCode());
			pcAccHeadGroupCodeForm.setOldGroupName(temppcAccHeadGroup.getName());
			pcAccHeadGroupCodeForm.setOldId(temppcAccHeadGroup.getId());
		}
		log.info("leaving from editAccountHeadGroupCode in PettyCashAccountHeadGroupCodeHandler..");
	}


	public PcAccHeadGroup checkDuplicate(String formGroupCode) throws Exception{
		PcAccHeadGroup group = pettyCashAccountHeadGroupCode.existanceCheck(formGroupCode);
		return group;
	}
}
