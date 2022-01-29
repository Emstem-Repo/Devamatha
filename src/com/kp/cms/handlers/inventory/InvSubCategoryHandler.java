package com.kp.cms.handlers.inventory;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.forms.inventory.InvSubCategoryForm;
import com.kp.cms.helpers.inventory.InvSubCategoryHelper;
import com.kp.cms.to.inventory.InvCategoryTo;
import com.kp.cms.to.inventory.InvSubCategoryTo;
import com.kp.cms.transactions.inventory.IInvSubCategoryTransaction;
import com.kp.cms.transactionsimpl.inventory.InvSubCategoryImpl;

public class InvSubCategoryHandler {
	private static final Log log=LogFactory.getLog(InvSubCategoryHandler.class);
	public static volatile InvSubCategoryHandler invSubCategoryHandler=null;
	public static InvSubCategoryHandler getInstance()
	{
		if(invSubCategoryHandler==null)
		{
			invSubCategoryHandler=new InvSubCategoryHandler();
			return invSubCategoryHandler;
		}
		return invSubCategoryHandler;
	}
	
	IInvSubCategoryTransaction transaction = new InvSubCategoryImpl();
	
	public List<InvCategoryTo> getCategory() throws Exception{
		List<InvItemCategory> categoryList= transaction.getCategory();
		List<InvCategoryTo> getcategoryList=InvSubCategoryHelper.getInstance().convertBosToTos(categoryList);
		return getcategoryList;
	}

	public List<InvSubCategoryTo> getSubCategoryList() throws Exception{
		   List<InvSubCategoryBo> subCategoryBoList=transaction.getSubCategoryList();
		   List<InvSubCategoryTo> subCategoryToList=InvSubCategoryHelper.getInstance().convertBosToTOs(subCategoryBoList);
			return subCategoryToList;
		}

	public boolean addSubCategory(InvSubCategoryForm invSubCategoryForm,String mode)throws Exception{
		InvSubCategoryBo subCategoryBo=InvSubCategoryHelper.getInstance().convertFormTOBO(invSubCategoryForm);
		boolean isAdded=transaction.addSubCategory(subCategoryBo,mode);
		return isAdded;
	}

	public void editSubCategory(InvSubCategoryForm invSubCategoryForm)throws Exception {
		InvSubCategoryBo subCategoryBo=transaction.getSubcategoryById(invSubCategoryForm.getId());
		InvSubCategoryHelper.getInstance().setBotoForm(invSubCategoryForm, subCategoryBo);
	}

	public boolean updateSubCategory(InvSubCategoryForm invSubCategoryForm,	String mode)throws Exception{
		InvSubCategoryBo subCategoryBo=transaction.getSubcategoryById(invSubCategoryForm.getId());
		subCategoryBo=InvSubCategoryHelper.getInstance().convertFormToBO(subCategoryBo,invSubCategoryForm);
		boolean isUpdated=transaction.addSubCategory(subCategoryBo,mode);
		return isUpdated;
	}

	public boolean deleteSubCategory(InvSubCategoryForm invSubCategoryForm) throws Exception{
		boolean isDeleted=transaction.deleteSubCategory(invSubCategoryForm.getId());
		return isDeleted;
	}

	public boolean reactivateSubCategory(InvSubCategoryForm invSubCategoryForm,	String userId) throws Exception{
	     return transaction.reactivateSubCategory(invSubCategoryForm);
	}

	public boolean duplicateCheck(InvSubCategoryForm invSubCategoryForm,ActionErrors errors, HttpSession session) throws Exception{
		boolean duplicate=transaction.duplicateCheck(invSubCategoryForm,invSubCategoryForm.getInvItemCategory(),errors,session);
		return duplicate;
	}

}
