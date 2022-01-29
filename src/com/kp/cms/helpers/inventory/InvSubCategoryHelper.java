package com.kp.cms.helpers.inventory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.InvItemCategory;
import com.kp.cms.bo.admin.InvSubCategoryBo;
import com.kp.cms.bo.admin.ProgramType;
import com.kp.cms.forms.inventory.InvSubCategoryForm;
import com.kp.cms.to.inventory.InvCategoryTo;
import com.kp.cms.to.inventory.InvSubCategoryTo;

public class InvSubCategoryHelper {

	private static final Log log=LogFactory.getLog(InvSubCategoryHelper.class);
	public static volatile InvSubCategoryHelper invSubCategoryHelper = null;

	public static InvSubCategoryHelper getInstance() {
		if (invSubCategoryHelper == null) {
			invSubCategoryHelper = new InvSubCategoryHelper();
			return invSubCategoryHelper;
		}
		return invSubCategoryHelper;
	}

	public List<InvCategoryTo> convertBosToTos(List<InvItemCategory> categoryList) {
		List<InvCategoryTo> categoryToList=new ArrayList<InvCategoryTo>();
		if(categoryList!=null)
		{
			Iterator <InvItemCategory> iterator=categoryList.iterator();
			while(iterator.hasNext())
			{
				InvItemCategory invItemCategory = (InvItemCategory) iterator.next();
				InvCategoryTo invCategoryTo = new InvCategoryTo();
				invCategoryTo.setId(invItemCategory.getId());
				invCategoryTo.setCategoryName(invItemCategory.getName());
				categoryToList.add(invCategoryTo);
			}
		}
		Collections.sort(categoryToList);
		return categoryToList;
	}

	public List<InvSubCategoryTo> convertBosToTOs(List<InvSubCategoryBo> subCategoryBoList) {
		List<InvSubCategoryTo> invSubCategoryToList=new ArrayList<InvSubCategoryTo>();
		Iterator itr=subCategoryBoList.iterator();
		while (itr.hasNext()) {
			InvSubCategoryBo invSubCategoryBo=(InvSubCategoryBo)itr.next();
			InvSubCategoryTo  subCategoryTo=new InvSubCategoryTo();
			subCategoryTo.setId(invSubCategoryBo.getId());
			subCategoryTo.setInvItemCategory(String.valueOf(invSubCategoryBo.getInvItemCategory().getName()));
			subCategoryTo.setSubCategoryName(invSubCategoryBo.getSubCategoryName());
			invSubCategoryToList.add(subCategoryTo);
		}
		return invSubCategoryToList;
	}

	public InvSubCategoryBo convertFormTOBO(InvSubCategoryForm invSubCategoryForm) {
		InvSubCategoryBo subCategoryBo=new InvSubCategoryBo();
		
		InvItemCategory type= new InvItemCategory();
		type.setId(Integer.parseInt(invSubCategoryForm.getInvItemCategory()));
		subCategoryBo.setInvItemCategory(type);
		subCategoryBo.setSubCategoryName(invSubCategoryForm.getSubCategoryName());
		
		subCategoryBo.setCreatedBy(invSubCategoryForm.getUserId());
		subCategoryBo.setCreatedDate(new Date());
		subCategoryBo.setLastModifiedDate(new Date());
		subCategoryBo.setModifiedBy(invSubCategoryForm.getUserId());
		subCategoryBo.setIsActive(true);
		return subCategoryBo;
	}

	public void setBotoForm(InvSubCategoryForm invSubCategoryForm,InvSubCategoryBo subCategoryBo) {
		if(subCategoryBo!=null)
		{
			invSubCategoryForm.setInvItemCategory(String.valueOf(subCategoryBo.getInvItemCategory().getId()));
			invSubCategoryForm.setSubCategoryName(subCategoryBo.getSubCategoryName());
		}
		
	}

	public InvSubCategoryBo convertFormToBO(InvSubCategoryBo subCategoryBo,InvSubCategoryForm invSubCategoryForm) {
       InvSubCategoryBo subcategoryBo=new InvSubCategoryBo();
		
		InvItemCategory type= new InvItemCategory();
		type.setId(Integer.parseInt(invSubCategoryForm.getInvItemCategory()));
		subcategoryBo.setInvItemCategory(type);
		subcategoryBo.setSubCategoryName(invSubCategoryForm.getSubCategoryName());
		subcategoryBo.setId(invSubCategoryForm.getId());
		subcategoryBo.setLastModifiedDate(new Date());
		subcategoryBo.setModifiedBy(invSubCategoryForm.getUserId());
		subcategoryBo.setIsActive(true);
		return subcategoryBo;
	}

}
