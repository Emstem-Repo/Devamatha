package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.University;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.UniversityForm;
import com.kp.cms.helpers.admin.UniversityHelper;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.transactions.admin.IUniversityTxn;
import com.kp.cms.transactionsimpl.admin.UniversityTxnImpl;

public class UniversityHandler {
	private static volatile UniversityHandler universityHandler = null;

	public static UniversityHandler getInstance() {
		if (universityHandler == null) {
			universityHandler = new UniversityHandler();
		}
		return universityHandler;
	}	
	
	public List<UniversityTO> getUniversity() {
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		List<University> recommendorBOList = txn.getUniversity();
		List<UniversityTO> universitylist = UniversityHelper.getInstance().converttoUniversityTO(recommendorBOList);
		return universitylist;
	}

	public boolean addUniversity(UniversityForm uniForm)throws Exception 
	{
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		University dupUniversity=txn.checkDuplicate(uniForm.getUniversity(),Integer.parseInt(uniForm.getDocTypeId()));
		if(dupUniversity!=null)
		{
			if(dupUniversity.getIsActive())
			{
				throw new DuplicateException();
			}
			else
			{
				uniForm.setId(dupUniversity.getId());
				throw new ReActivateException();
			}
		}
		University university=UniversityHelper.getInstance().convertFormToBo(uniForm);
		return txn.addUniversity(university);
	}
	
	public void getUniversityDetails(UniversityForm uniForm)throws Exception
	{
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		
		University university=txn.getUniversityDetails(uniForm.getId());
		UniversityHelper.getInstance().convertBoToForm(university,uniForm);
	}
	
	public boolean updateUniversity(UniversityForm uniForm)throws Exception 
	{
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		if(uniForm.getUniversity()!=null){
			if(!uniForm.getUniversity().equalsIgnoreCase(uniForm.getDummyUniversity()) || !uniForm.getDocTypeId().equalsIgnoreCase(uniForm.getDummyDocTypeId()))
			{	
				University dupUniversity=txn.checkDuplicate(uniForm.getUniversity(),Integer.parseInt(uniForm.getDocTypeId()));
				if(dupUniversity!=null)
				{
					if(dupUniversity.getIsActive())
					{
						throw new DuplicateException();
					}
					else
					{
						uniForm.setId(dupUniversity.getId());
						throw new ReActivateException();
					}
				}
			}
			}
		University oldUniversity=txn.getUniversityDetails(uniForm.getId());
		oldUniversity=UniversityHelper.getInstance().convertFormToBo(uniForm,oldUniversity);
		return txn.addUniversity(oldUniversity);
	}
	
	public boolean deleteOrReactivate(Integer id,String action)throws Exception
	{
		IUniversityTxn txn = UniversityTxnImpl.getInstance();
		return txn.deleteOrReactivate(id,action);
	}

}
