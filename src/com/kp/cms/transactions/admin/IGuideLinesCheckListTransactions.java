package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.GuidelinesChecklist;
import com.kp.cms.forms.admin.GuideLinesCheckListForm;

public interface IGuideLinesCheckListTransactions {
	public boolean addGuidelinesCheckList(GuidelinesChecklist guidelinesChecklist, String mode) throws Exception;
	public List<GuidelinesChecklist> getGuideLineCheckList() throws Exception;
	public boolean deleteGuidelinesChecklist(int id, Boolean activate, GuideLinesCheckListForm guideLinesCheckListForm) throws Exception;
	public GuidelinesChecklist isGuideLinesDuplicated(GuidelinesChecklist duplChecklist) throws Exception;	
}
