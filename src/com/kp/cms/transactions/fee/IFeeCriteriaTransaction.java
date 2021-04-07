package com.kp.cms.transactions.fee;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.FeeCriteria;
import com.kp.cms.forms.fee.FeeCriteriaForm;

public interface IFeeCriteriaTransaction {
	public boolean addFeeCriteriaToTable(FeeCriteria feeCriteria) throws Exception;
	public boolean checkDuplicate(FeeCriteriaForm feeCriteriaForm) throws Exception;
	public Map<Integer,String> getAllFeesGroup() throws Exception;
	public List<FeeCriteria> getFeeCriterias() throws Exception;
	public boolean deleteFeeCriteria(int id) throws Exception;
}
