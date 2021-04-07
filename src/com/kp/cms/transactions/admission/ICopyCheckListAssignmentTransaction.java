package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.DocChecklist;

public interface ICopyCheckListAssignmentTransaction {

	public List<DocChecklist> getCheckListByYear(int fromYear)throws Exception;

	public boolean copyCheckList(List<DocChecklist> docList) throws Exception;

}
