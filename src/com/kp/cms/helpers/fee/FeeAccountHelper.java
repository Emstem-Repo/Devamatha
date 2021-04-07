package com.kp.cms.helpers.fee;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.forms.fee.FeeAccountForm;
import com.kp.cms.to.fee.FeeAccountTO;

public class FeeAccountHelper {
	
	private static FeeAccountHelper feeAccountHelper= null;
	public static FeeAccountHelper getInstance() {
	      if(feeAccountHelper == null) {
	    	  feeAccountHelper = new FeeAccountHelper();
	    	  return feeAccountHelper;
	      }
	      return feeAccountHelper;
	}
	
	public FeeAccount createBoObjcet(ActionForm form,String mode) throws FileNotFoundException, IOException
	{
		FeeAccountForm feeAccountForm= (FeeAccountForm) form;
		
		
		FeeAccount feeAccount=new FeeAccount();
		feeAccount.setId(feeAccountForm.getId());
		feeAccount.setCode(feeAccountForm.getCode());
		feeAccount.setName(feeAccountForm.getName());
		
		if(feeAccountForm.getBankInfo() != null){
			feeAccount.setBankInformation(feeAccountForm.getBankInfo());
		}
		if(feeAccountForm.getPrintAccName() != null){
			feeAccount.setPrintAccountName(feeAccountForm.getPrintAccName());
		}
		if(feeAccountForm.getPosition() != null){
			feeAccount.setPrintPosition(Integer.parseInt(feeAccountForm.getPosition()));
		}
		if(feeAccountForm.getFormFile() != null){
			feeAccount.setLogo(feeAccountForm.getFormFile().getFileData());
		}
		if(feeAccountForm.getFormFile() != null && feeAccountForm.getFormFile().getFileName() != null
				&& feeAccountForm.getFormFile().getContentType() != null){
			feeAccount.setFileName(feeAccountForm.getFormFile().getFileName());
			feeAccount.setContentType(feeAccountForm.getFormFile().getContentType());
		}
		feeAccount.setIsActive(!(mode.equalsIgnoreCase("delete")));
		if(mode.equalsIgnoreCase("Add"))
		{
			feeAccount.setCreatedBy(feeAccountForm.getUserId());
			feeAccount.setModifiedBy(feeAccountForm.getUserId());
			feeAccount.setCreatedDate(new Date());
		}else{
			feeAccount.setModifiedBy(feeAccountForm.getUserId());
			feeAccount.setLastModifiedDate(new Date());
		}
		return feeAccount;
	}
	
	
	public FeeAccountForm createFormObjcet(ActionForm form,FeeAccount feeAccount)
	{
		FeeAccountForm feeAccountForm=(FeeAccountForm)form;
		feeAccountForm.setId(feeAccount.getId());
		feeAccountForm.setCode(feeAccount.getCode());
		feeAccountForm.setName(feeAccount.getName());
		feeAccountForm.setOriginalcode(feeAccount.getCode());
		feeAccountForm.setPosition(Integer.toString(feeAccount.getPrintPosition()));
		if(feeAccount.getPrintAccountName() != null){
			feeAccountForm.setPrintAccName(feeAccount.getPrintAccountName());
		}
		if(feeAccount.getBankInformation() != null){
			feeAccountForm.setBankInfo(feeAccount.getBankInformation());
		}
		if(feeAccount.getFileName() != null){
			feeAccountForm.setFileName(feeAccount.getFileName());
		}
		if(feeAccount.getContentType() != null){
			feeAccountForm.setContentType(feeAccount.getContentType());
		}
		return feeAccountForm;
	}
	
	
	public List<FeeAccountTO> convertBOstoTos(List <FeeAccount> feeAccountlist)
	{
		List <FeeAccountTO> feeAccounttolist=new ArrayList<FeeAccountTO>();
		Iterator<FeeAccount> itr=feeAccountlist.iterator();
		FeeAccountTO feeAccountTO=null;
		FeeAccount feeAccount=null;
		while(itr.hasNext())
		{
			feeAccountTO=new FeeAccountTO();
			feeAccount=(FeeAccount) itr.next();
			feeAccountTO.setId(feeAccount.getId());
			feeAccountTO.setName(feeAccount.getName());
			feeAccountTO.setCode(feeAccount.getCode());
			feeAccountTO.setPrintPosition(Integer.toString(feeAccount.getPrintPosition()));
			if(feeAccount.getFileName() != null){
				feeAccountTO.setFileName(feeAccount.getFileName());
			}
			if(feeAccount.getContentType() != null){
				feeAccountTO.setContentType(feeAccount.getContentType());
			}
			if(feeAccount.getPrintAccountName() != null){
				feeAccountTO.setPrintAccName(feeAccount.getPrintAccountName());
			}
			
			feeAccounttolist.add(feeAccountTO);
		}
		
		return feeAccounttolist;
	}

}