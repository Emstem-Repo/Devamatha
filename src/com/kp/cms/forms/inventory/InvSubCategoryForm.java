package com.kp.cms.forms.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.inventory.InvCategoryTo;
import com.kp.cms.to.inventory.InvSubCategoryTo;


public class InvSubCategoryForm extends BaseActionForm{
    
	private int id;
	private String invItemCategory;
	private String subCategoryName;
	private List<InvSubCategoryTo> subCategoryList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInvItemCategory() {
		return invItemCategory;
	}
	public void setInvItemCategory(String invItemCategory) {
		this.invItemCategory = invItemCategory;
	}
	public String getSubCategoryName() {
		return subCategoryName;
	}
	public void setSubCategoryName(String subCategoryName) {
		this.subCategoryName = subCategoryName;
	}
	
	public List<InvSubCategoryTo> getSubCategoryList() {
		return subCategoryList;
	}
	public void setSubCategoryList(List<InvSubCategoryTo> subCategoryList) {
		this.subCategoryList = subCategoryList;
	}
	public void reset() {
		invItemCategory=null;
		subCategoryName=null;
		this.id =0;
	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request){
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		return actionErrors;
	}
		
}
