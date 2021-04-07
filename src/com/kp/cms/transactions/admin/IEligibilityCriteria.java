package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.EligibilityCriteria;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admin.EligibilityCriteriaForm;

public interface IEligibilityCriteria {
	public boolean addCriteria(EligibilityCriteria eligibilityCriteria, String mode) throws  DuplicateException, Exception;
	public List<EligibilityCriteria> getEligibilityCriteria(int id) throws Exception;	
	public Boolean isCourseYearDuplicated(int courseId, int year, int id)  throws Exception;	
	//public boolean deleteCriteria(EligibilityCriteria eligibilityCriteria) throws Exception;
	public boolean deleteCriteria(int id, Boolean activate,
			EligibilityCriteriaForm eligibilityCriteriaForm) throws Exception;	
}
