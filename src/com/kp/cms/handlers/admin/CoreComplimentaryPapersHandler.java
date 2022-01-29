package com.kp.cms.handlers.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.CoreComplimentaryPapers;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CasteForm;
import com.kp.cms.forms.admin.CoreComplimentaryPapersForm;
import com.kp.cms.helpers.admin.CasteHelper;
import com.kp.cms.helpers.admin.CoreComplimentaryPapersHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.CoreComplimentaryPapersTo;
import com.kp.cms.transactions.admin.ICasteTransaction;
import com.kp.cms.transactions.admin.ICoreComplimentaryPapersTransaction;
import com.kp.cms.transactionsimpl.admin.CasteTransactionImpl;
import com.kp.cms.transactionsimpl.admin.CoreComplimentaryPapersTransactionImpl;

public class CoreComplimentaryPapersHandler
{
	private static volatile CoreComplimentaryPapersHandler coreCompHandler = null;

	public CoreComplimentaryPapersHandler() 
	{
		
	}
	
	public static CoreComplimentaryPapersHandler getInstance()
	{
		if(coreCompHandler==null)
		{
			coreCompHandler=new CoreComplimentaryPapersHandler();
		}
		return coreCompHandler;
	}
	
	public boolean addCoreComplimentaryPapers(CoreComplimentaryPapersForm objform,HttpServletRequest request) throws Exception{
		ICoreComplimentaryPapersTransaction corecompImpl = new CoreComplimentaryPapersTransactionImpl();
		CoreComplimentaryPapers corecomp1=CoreComplimentaryPapersHelper.convertTotoBo(objform,"Add");
		CoreComplimentaryPapers corecomp=CoreComplimentaryPapersHelper.convertTotoBo(objform,"Add");
		corecomp = corecompImpl.isCoreComplimentaryPapersDuplicate(corecomp);
		boolean isCoreComplimentaryPapersAdded = false;
		if (corecomp != null && corecomp.getIsActive()) {
			throw new DuplicateException();
		} else if (corecomp != null && !corecomp.getIsActive()) {
			request.getSession().setAttribute("CoreComplimentaryPapers",corecomp);
			objform.setReactivateid(corecomp.getId());
			throw new ReActivateException();
		}else if(corecompImpl != null) {
			isCoreComplimentaryPapersAdded = corecompImpl.addCoreComplimentaryPapers(corecomp1);
		}
		return isCoreComplimentaryPapersAdded;
	}
	
	public boolean updateCoreComplimentaryPapers(CoreComplimentaryPapersForm objform,HttpServletRequest request) throws Exception{
		ICoreComplimentaryPapersTransaction corecompImpl = new CoreComplimentaryPapersTransactionImpl();
		boolean isCasteEdited = false;
		CoreComplimentaryPapers corecomp1=CoreComplimentaryPapersHelper.convertTotoBo(objform,"Update");
		CoreComplimentaryPapers corecomp=CoreComplimentaryPapersHelper.convertTotoBo(objform,"Update");
		corecomp = corecompImpl.isCoreComplimentaryPapersDuplicate(corecomp);
		if(!objform.getCourseId().equals(objform.getOrgcourseId())){
		if (corecomp != null && corecomp.getIsActive()) {
			throw new DuplicateException();
		}
		}
		if (corecomp != null && !corecomp.getIsActive()) {
			request.getSession().setAttribute("CoreComplimentaryPapers",corecomp);
			objform.setReactivateid(corecomp.getId());
			throw new ReActivateException();
		}else if (corecomp1 != null) {
			isCasteEdited = corecompImpl.updateCoreComplimentaryPapers(corecomp1);
		}
		return isCasteEdited;
	}
	
	public boolean deleteCoreComplimentaryPapers(int corecompId,String userId) throws Exception{
		ICoreComplimentaryPapersTransaction corecompImpl = new CoreComplimentaryPapersTransactionImpl();
		boolean isCoreComplimentaryPapersDeleted = false;
		if (corecompImpl != null) {
			isCoreComplimentaryPapersDeleted = corecompImpl.deleteCoreComplimentaryPapers(corecompId,userId);
		}
		return isCoreComplimentaryPapersDeleted;
	}
	
	
	public List<CoreComplimentaryPapersTo> getCoreComplimentaryPapers(){
		ICoreComplimentaryPapersTransaction corecompImpl = new CoreComplimentaryPapersTransactionImpl();
		List<CoreComplimentaryPapers> corecompBoList = corecompImpl.getCoreComplimentaryPapers();
		List<CoreComplimentaryPapersTo> corecompToList = CoreComplimentaryPapersHelper.convertBosToTos(corecompBoList);
		return corecompToList;
	}
	
	public boolean reCoreComplimentaryPapers(CoreComplimentaryPapers corecomp,String userId) throws Exception{
		ICoreComplimentaryPapersTransaction corecompImpl = new CoreComplimentaryPapersTransactionImpl();
		boolean isCoreComplimentaryPapersReActivate=corecompImpl.isReactivateCoreComplimentaryPapers(corecomp,userId);
		return isCoreComplimentaryPapersReActivate;
	}

}
