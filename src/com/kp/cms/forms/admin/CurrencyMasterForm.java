package com.kp.cms.forms.admin;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CurrencyMasterTO;

public class CurrencyMasterForm extends BaseActionForm{
	
	private String method;
	private String currencyName;
	private String currencyShortName;
	private int currencyMasterId;
	private List<CurrencyMasterTO> currencyMasterList;
	
	public int getCurrencyMasterId() {
		return currencyMasterId;
	}
	public void setCurrencyMasterId(int currencyMasterId) {
		this.currencyMasterId = currencyMasterId;
	}
	public List<CurrencyMasterTO> getCurrencyMasterList() {
		return currencyMasterList;
	}
	public void setCurrencyMasterList(List<CurrencyMasterTO> currencyMasterList) {
		this.currencyMasterList = currencyMasterList;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}
	public String getCurrencyShortName() {
		return currencyShortName;
	}
	public void setCurrencyShortName(String currencyShortName) {
		this.currencyShortName = currencyShortName;
	}

	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.currencyName = null;
		this.currencyShortName = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
	
}
