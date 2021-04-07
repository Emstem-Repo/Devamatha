package com.kp.cms.handlers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.helpers.admin.ApplicationFeeHelper;
import com.kp.cms.helpers.admin.CasteHelper;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.transactions.admin.IApplicationFeeTransaction;
import com.kp.cms.transactionsimpl.admin.ApplicationFeeTransactionImpl;

public class ApplicationFeeHandler 
{
	
	
	private static volatile ApplicationFeeHandler appfeeHandler = null;
	private ApplicationFeeHandler()
	{
		
	}
	
	
	public static ApplicationFeeHandler getInstance() 
	{
	
		if (appfeeHandler == null) {
			appfeeHandler = new ApplicationFeeHandler();
		}
		return appfeeHandler;
	}
	
	public List<ApplicationFeeTO> getAppFees()
	{
		IApplicationFeeTransaction transaction=new ApplicationFeeTransactionImpl();
		List<ApplicationFee> appfeeBOList=transaction.getAppFees();
		List<ApplicationFeeTO> appfeeList = ApplicationFeeHelper.convertBOsToTos(appfeeBOList);
		return appfeeList;
	}
	
	public boolean addAppFee(ApplicationFeeForm appFeeForm,HttpServletRequest request) throws Exception
	{
		IApplicationFeeTransaction appFeeImpl=new ApplicationFeeTransactionImpl();
		ApplicationFee appfee=ApplicationFeeHelper.convertTOtoBO(appFeeForm,"Add");
		ApplicationFee appfee1=null;
		appfee1=appFeeImpl.isAppFeeDuplicated(appfee);
		boolean isAppFeeAdded=false;
		if(appfee1!=null && appfee1.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		else if(appfee1!=null && appfee1.getIsActive()==false)
		{
			request.getSession().setAttribute("appFee", appfee1);
			appFeeForm.setReactivateid(appfee.getId());
			throw new ReActivateException();
			
		}
		else if(appFeeImpl!=null)
		{
			isAppFeeAdded=appFeeImpl.addAppFee(appfee);
		}
		return isAppFeeAdded;
		
	}
	
	
	public boolean updateAppFee(ApplicationFeeForm appFeeForm,HttpServletRequest request) throws Exception
	{
		IApplicationFeeTransaction appFeeImpl=new ApplicationFeeTransactionImpl();
		boolean isAppFeeEdited=false;
		ApplicationFee appfee=ApplicationFeeHelper.convertTOtoBO(appFeeForm,"Update");
		ApplicationFee appfee1=ApplicationFeeHelper.convertTOtoBO(appFeeForm,"Update");
		ApplicationFee appfee2=appFeeImpl.getAppFeeList(appFeeForm.getAppfeeId());
		appfee=appFeeImpl.isAppFeeDuplicated(appfee);
		if(!appFeeForm.getYear().equals(appFeeForm.getOrgYear()) || !appFeeForm.getSubReligionId().equals(appFeeForm.getOrgSubReligionId())||!appFeeForm.getProgramTypeId().equals(appFeeForm.getOrgProgramTypeId()))
		{
			if(appfee!=null && appfee.getIsActive())
			{
			throw new DuplicateException();
			}
			else if(appfee!=null && !appfee.getIsActive())
			{
				request.getSession().setAttribute("appFee", appfee);
				appFeeForm.setReactivateid(appfee.getId());
				throw new ReActivateException();
			}
			else if(appFeeImpl!=null)
			{
				isAppFeeEdited=appFeeImpl.updateAppFee(appfee1);
			}
		}
		return isAppFeeEdited;
		
	}
	
	
	public boolean deleteAppFee(int appfeeId,String userId) throws Exception
	{
		IApplicationFeeTransaction appfeeImpl=new ApplicationFeeTransactionImpl();
		boolean isAppFeeDeleted=false;
		if(appfeeImpl!=null)
		{
			isAppFeeDeleted=appfeeImpl.deleteAppFee(appfeeId,userId);
		}
		return isAppFeeDeleted;
	}
	
	public boolean reActivateAppFee(ApplicationFee appfee,String userId)throws Exception
	{
		IApplicationFeeTransaction appfeeImpl=new ApplicationFeeTransactionImpl();
		boolean isReActivateAppFee=appfeeImpl.reActivateAppFee(appfee,userId);
		return isReActivateAppFee;
	}

}
