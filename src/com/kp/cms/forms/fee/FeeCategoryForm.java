package com.kp.cms.forms.fee;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.fee.FeeCategoryTo;

public class FeeCategoryForm extends BaseActionForm{
	
	private static final long serialVersionUID = 1L;
	
	private int feeCategoryId;
	private String feeCategoryName;
	private String editedField;
	private int duplId;
	private int id;
	private List<FeeCategoryTo> feeCategoryList;
	private int reactivateid;
	private String origFeeCategoryName;
	private String origFeeCategoryId;
	
	public int getFeeCategoryId() {
		return feeCategoryId;
	}
	public void setFeeCategoryId(int feeCategoryId) {
		this.feeCategoryId = feeCategoryId;
	}
	public String getFeeCategoryName() {
		return feeCategoryName;
	}
	public void setFeeCategoryName(String feeCategoryName) {
		this.feeCategoryName = feeCategoryName;
	}
	public String getEditedField() {
		return editedField;
	}
	public void setEditedField(String editedField) {
		this.editedField = editedField;
	}
	public int getDuplId() {
		return duplId;
	}
	public void setDuplId(int duplId) {
		this.duplId = duplId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<FeeCategoryTo> getFeeCategoryList() {
		return feeCategoryList;
	}
	public void setFeeCategoryList(List<FeeCategoryTo> feeCategoryList) {
		this.feeCategoryList = feeCategoryList;
	}	
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}	
	public String getOrigFeeCategoryName() {
		return origFeeCategoryName;
	}
	public void setOrigFeeCategoryName(String origFeeCategoryName) {
		this.origFeeCategoryName = origFeeCategoryName;
	}
	
	public String getOrigFeeCategoryId() {
		return origFeeCategoryId;
	}
	public void setOrigFeeCategoryId(String origFeeCategoryId) {
		this.origFeeCategoryId = origFeeCategoryId;
	}
	@Override
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.feeCategoryName = null;	
		this.feeCategoryList = null;
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}

	
}
