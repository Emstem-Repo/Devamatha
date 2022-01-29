package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeDivisionTO;

public class SlipRegisterForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String divisionid ;
	private String feetype ;
	private List<FeeDivisionTO> feeDivList;
	private Map<Integer,String> feeDivisionMap;
	
	
	

	
		public List<FeeDivisionTO> getFeeDivList() {
		return feeDivList;
	}
	public void setFeeDivList(List<FeeDivisionTO> feeDivList) {
		this.feeDivList = feeDivList;
	}
		public Map<Integer, String> getFeeDivisionMap() {
		return feeDivisionMap;
	}
	public void setFeeDivisionMap(Map<Integer, String> feeDivisionMap) {
		this.feeDivisionMap = feeDivisionMap;
	}
		public String getFeetype() {
		return feetype;
	}
	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}
	
	public void resetFields() {
		this.divisionid=null;
		this.feetype=null;
		super.setProgramTypeId(null);	
		
	}
	

	public String getDivisionid() {
		return divisionid;
	}
	public void setDivisionid(String divisionid) {
		this.divisionid = divisionid;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
	
	}
