package com.kp.cms.forms.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.CurrencyMasterTO;
import com.kp.cms.to.admin.MobNewsEventsCategoryTO;

public class MobNewsEventsCategoryForm extends BaseActionForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int mobNewsEventsCategoryId;
	private String category;
	private List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList;
	private int dupId;
	
	
	
	
	public int getDupId() {
		return dupId;
	}
	public void setDupId(int dupId) {
		this.dupId = dupId;
	}
	public String getMethod() {
		return method;
	}
	public List<MobNewsEventsCategoryTO> getMobNewsEventsCategoryList() {
		return mobNewsEventsCategoryList;
	}
	public void setMobNewsEventsCategoryList(
			List<MobNewsEventsCategoryTO> mobNewsEventsCategoryList) {
		this.mobNewsEventsCategoryList = mobNewsEventsCategoryList;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.category=null;
		
	}
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	private String method;
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getMobNewsEventsCategoryId() {
		return mobNewsEventsCategoryId;
	}
	public void setMobNewsEventsCategoryId(int mobNewsEventsCategoryId) {
		this.mobNewsEventsCategoryId = mobNewsEventsCategoryId;
	}
	
}
