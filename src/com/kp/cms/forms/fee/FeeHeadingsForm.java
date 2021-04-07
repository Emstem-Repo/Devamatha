package com.kp.cms.forms.fee;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;

public class FeeHeadingsForm extends BaseActionForm {

	private List<FeeGroupTO> feeGroupToList;
	private List<FeeHeadingTO> feeHeadingList;
	private String method;
	private String feesName;
	private Map<Integer, String> feeGroupMap;
	private String feeGroup;
	private int feeHeadingsId;

	
	public int getFeeHeadingsId() {
		return feeHeadingsId;
	}
	public void setFeeHeadingsId(int feeHeadingsId) {
		this.feeHeadingsId = feeHeadingsId;
	}
	public String getFeeGroup() {
		return feeGroup;
	}
	public void setFeeGroup(String feeGroup) {
		this.feeGroup = feeGroup;
	}
	public List<FeeGroupTO> getFeeGroupToList() {
		return feeGroupToList;
	}
	public void setFeeGroupToList(List<FeeGroupTO> feeGroupToList) {
		this.feeGroupToList = feeGroupToList;
	}
	public List<FeeHeadingTO> getFeeHeadingList() {
		return feeHeadingList;
	}
	public void setFeeHeadingList(List<FeeHeadingTO> feeHeadingList) {
		this.feeHeadingList = feeHeadingList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFeesName() {
		return feesName;
	}
	public void setFeesName(String feesName) {
		this.feesName = feesName;
	}
	public Map<Integer, String> getFeeGroupMap() {
		return feeGroupMap;
	}
	public void setFeeGroupMap(Map<Integer, String> feeGroupMap) {
		this.feeGroupMap = feeGroupMap;
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param request
	 */
	public void clearAll() {
		super.clear();
		this.feesName = null;
		this.feeGroup = null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = new ActionErrors();
		actionErrors = super.validate(mapping, request, formName);
		
		return actionErrors;
	}

}