package com.kp.cms.transactions.phd;

import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.forms.phd.PhdInternalGuideForm;


public interface IPhdInternalGuideTransactions {

    public Map<String, String> guideShipMap() throws Exception;
	
	public Map<String, String> employeeMap() throws Exception;
	
	public Map<String, String> employeMap(String departmentId) throws Exception;
	
	public Map<String, String> getDepartmentMap() throws Exception;
	
	public List<PhdInternalGuideBo> getInternalGuideDetails(PhdInternalGuideForm objForm) throws Exception;
	
	public boolean addPhdEmployee(PhdInternalGuideBo guideBo,PhdInternalGuideForm objForm, ActionErrors errors) throws Exception;
	
	public PhdInternalGuideBo getGuideDetailsById(int id) throws Exception;

	public boolean deletePhdemployee(int id) throws Exception;

}
