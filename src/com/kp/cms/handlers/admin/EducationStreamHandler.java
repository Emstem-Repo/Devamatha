package com.kp.cms.handlers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.ApplicationFee;
import com.kp.cms.bo.admin.EducationStream;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.ApplicationFeeForm;
import com.kp.cms.forms.admin.EducationStreamForm;
import com.kp.cms.helpers.admin.ApplicationFeeHelper;
import com.kp.cms.helpers.admin.EducationStreamHelper;
import com.kp.cms.to.admin.ApplicationFeeTO;
import com.kp.cms.to.admin.EducationStreamTO;
import com.kp.cms.transactions.admin.IApplicationFeeTransaction;
import com.kp.cms.transactions.admin.IEducationStreamTransaction;
import com.kp.cms.transactionsimpl.admin.ApplicationFeeTransactionImpl;
import com.kp.cms.transactionsimpl.admin.EducationStreamTransactionImpl;

public class EducationStreamHandler
{
	private static volatile EducationStreamHandler educstreamHandler = null;
	private EducationStreamHandler()
	{
		
	}
	
	
	public static EducationStreamHandler getInstance() 
	{
	
		if (educstreamHandler == null) {
			educstreamHandler = new EducationStreamHandler();
		}
		return educstreamHandler;
	}
	
	public List<EducationStreamTO> getEducStreams()
	{
		IEducationStreamTransaction transaction=new EducationStreamTransactionImpl();
		List<EducationStream> educstreamBOList=transaction.getEducStreams();
		List<EducationStreamTO> educstreamList = EducationStreamHelper.convertBOsToTos(educstreamBOList);
		return educstreamList;
	}
	
	public boolean addEducStream(EducationStreamForm educstreamform,HttpServletRequest request) throws Exception
	{
		IEducationStreamTransaction educstreamImpl=new EducationStreamTransactionImpl();
		EducationStream educstream=EducationStreamHelper.convertTOtoBO(educstreamform,"Add");
		EducationStream educstream1=null;
		educstream1=educstreamImpl.isEducStreamDuplicated(educstream);
		boolean isEducStreamAdded=false;
		if(educstream1!=null && educstream1.getIsActive()==true)
		{
			throw new DuplicateException();
		}
		else if(educstream1!=null && educstream1.getIsActive()==false)
		{
			request.getSession().setAttribute("educstream", educstream1);
			educstreamform.setReactivateid(educstream.getId());
			throw new ReActivateException();
			
		}
		else if(educstreamImpl!=null)
		{
			isEducStreamAdded=educstreamImpl.addEducStream(educstream);
		}
		return isEducStreamAdded;
		
	}
	
	
	public boolean updateEducStream(EducationStreamForm educstreamForm,HttpServletRequest request) throws Exception
	{
		IEducationStreamTransaction educstreamImpl=new EducationStreamTransactionImpl();
		boolean isEducStreamEdited=false;
		EducationStream educstream=EducationStreamHelper.convertTOtoBO(educstreamForm,"Update");
		EducationStream educstream1=EducationStreamHelper.convertTOtoBO(educstreamForm,"Update");
		//EducationStream educstream2=educstreamImpl.getEducStreamList(educstreamForm.getEducstreamId());
		educstream=educstreamImpl.isEducStreamDuplicated(educstream);
		if(!educstreamForm.getEducstreamName().equals(educstreamForm.getOrgName()))
		{
			if(educstream!=null && educstream.getIsActive())
			{
			throw new DuplicateException();
			}
			else if(educstream!=null && !educstream.getIsActive())
			{
				request.getSession().setAttribute("educstream", educstream);
				educstreamForm.setReactivateid(educstream.getId());
				throw new ReActivateException();
			}
			else if(educstreamImpl!=null)
			{
				isEducStreamEdited=educstreamImpl.updateEducStream(educstream1);
			}
		}
		return isEducStreamEdited;
		
	}
	
	
	public boolean deleteEducStream(int educstreamId,String userId) throws Exception
	{
		IEducationStreamTransaction educstreamImpl=new EducationStreamTransactionImpl();
		boolean isEducStreamDeleted=false;
		if(educstreamImpl!=null)
		{
			isEducStreamDeleted=educstreamImpl.deleteEducStream(educstreamId,userId);
		}
		return isEducStreamDeleted;
	}
	
	public boolean reActivateEducStream(EducationStream educstream,String userId)throws Exception
	{
		IEducationStreamTransaction educstreamImpl=new EducationStreamTransactionImpl();
		boolean isReActivateEducStream=educstreamImpl.reActivateEducStream(educstream,userId);
		return isReActivateEducStream;
	}

}
