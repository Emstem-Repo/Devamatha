package com.kp.cms.transactions.inventory;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.forms.inventory.InvSubCategoryForm;

public interface IInvSubCategoryTransaction {

	List<InvItemCategory> getCategory()throws Exception;

	List<InvSubCategoryBo> getSubCategoryList()throws Exception;

	boolean addSubCategory(InvSubCategoryBo subCategoryBo, String mode)throws Exception;

	InvSubCategoryBo getSubcategoryById(int id) throws Exception;

	boolean deleteSubCategory(int id) throws Exception;

	boolean reactivateSubCategory(InvSubCategoryForm invSubCategoryForm) throws Exception;

	boolean duplicateCheck(InvSubCategoryForm invSubCategoryForm,String invItemCategory, ActionErrors errors, HttpSession session);

}
