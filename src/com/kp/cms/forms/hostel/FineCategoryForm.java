package com.kp.cms.forms.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.hostel.FineCategoryTo;

public class FineCategoryForm extends BaseActionForm{
	private int id;
	private int reactivateid;
	private String name;
	private String originalValue;
	private String amount;
	private String originalValue1;
	List<FineCategoryTo> fineCategoryTosList;
	
	public String getOriginalValue1() {
		return originalValue1;
	}
	public void setOriginalValue1(String originalValue1) {
		this.originalValue1 = originalValue1;
	}
	public List<FineCategoryTo> getFineCategoryTosList() {
		return fineCategoryTosList;
	}
	public void setFineCategoryTosList(List<FineCategoryTo> fineCategoryTosList) {
		this.fineCategoryTosList = fineCategoryTosList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getReactivateid() {
		return reactivateid;
	}
	public void setReactivateid(int reactivateid) {
		this.reactivateid = reactivateid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(String originalValue) {
		this.originalValue = originalValue;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void reset(){
		this.amount=null;
		this.name=null;
	}
	/**
	 * form validation
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
}
