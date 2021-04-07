package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.CoreComplimentaryPapers;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.forms.admin.CoreComplimentaryPapersForm;


public interface ICoreComplimentaryPapersTransaction 
{
	public boolean addCoreComplimentaryPapers(CoreComplimentaryPapers corecomp) throws Exception;
	public List<CoreComplimentaryPapers> getCoreComplimentaryPapers();
	public boolean updateCoreComplimentaryPapers(CoreComplimentaryPapers corecomp) throws Exception;
	public boolean deleteCoreComplimentaryPapers(int id,String userId) throws Exception;
	public CoreComplimentaryPapers isCoreComplimentaryPapersDuplicate(CoreComplimentaryPapers corecomp) throws Exception;
	public boolean isReactivateCoreComplimentaryPapers(CoreComplimentaryPapers corecomp,String userId) throws Exception;
	
	public CoreComplimentaryPapers getCoreOrComplementarySubjects(BaseActionForm baseActionForm,int courseID) throws Exception;
	
	
	
	

}
