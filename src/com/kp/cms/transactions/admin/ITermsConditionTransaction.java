package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.TermsConditionChecklist;
import com.kp.cms.bo.admin.TermsConditions;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.to.admin.TermsConditionChecklistTO;

public interface ITermsConditionTransaction {
	public List<TermsConditions> getTermsCondition(int id) throws Exception;
	public boolean addTermsCondition(TermsConditions termsConditions, String mode, Boolean originalChangedNotChanged) throws DuplicateException, Exception;
	public boolean deleteTermsCondition(int id, Boolean activate, String uId) throws Exception;
	public TermsConditions isTermsAndConditionsDuplcated(TermsConditions oldTermsConditions) throws Exception;
	public List<TermsConditions> getTermsConditionForYear(int id, int year) throws Exception;	
	public boolean addTermsConditionCheckList(List<TermsConditionChecklist> tcBOList) throws Exception;
	public List<TermsConditionChecklist> getTermsConditionCheckList() throws Exception;
	public List<TermsConditionChecklist> getTermsConditionCheckListWithCourseId(int courseId/*, Integer year*/) throws Exception;
	public boolean deleteTermsConditionCheckList(int courseId/*, int year*/)throws Exception;
	public boolean deleteTermsConditionCheckListById(int id/*, int year*/)throws Exception;
	public boolean updateTermsConditionList(
			List<TermsConditionChecklistTO> termsConditionList,String userid) throws Exception;
}
