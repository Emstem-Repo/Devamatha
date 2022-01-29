package com.kp.cms.helpers.supportrequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.forms.supportrequest.CategoryForm;
import com.kp.cms.to.supportrequest.CategoryTo;

public class CategoryHelper {
	public static volatile CategoryHelper categoryHelper = null;
	
	private CategoryHelper(){
		
	}
	private static Log log = LogFactory.getLog(CategoryHelper.class);
	public static CategoryHelper getInstance() {
		if (categoryHelper == null) {
			categoryHelper = new CategoryHelper();
			return categoryHelper;
		}
		return categoryHelper;
	}
	/**
	 * convert from CategoryForm to CategoryBo
	 * @param categoryForm
	 * @return
	 */
	public CategoryBo convertFormToBo(CategoryForm categoryForm) {
		CategoryBo categoryBo=new CategoryBo();
		categoryBo.setName(categoryForm.getName());
		Department department=new Department();
		department.setId(Integer.parseInt(categoryForm.getDepartmentId()));
		categoryBo.setDepartmentId(department);
		categoryBo.setCategoryFor(categoryForm.getCategoryFor());
		categoryBo.setIsActive(true); 
		categoryBo.setCreatedDate(new Date());
		categoryBo.setLastModifiedDate(new Date());
		categoryBo.setModifiedBy(categoryForm.getUserId());
		categoryBo.setCreatedBy(categoryForm.getUserId());
		if(categoryForm.getEmail()!=null && !categoryForm.getEmail().isEmpty()){
			categoryBo.setEmail(categoryForm.getEmail());
		}
		return categoryBo;
	}
	/**
	 * convert from CategoryBo to CategoryTo
	 * @param categoryBos
	 * @return
	 */
	public List<CategoryTo> convertBoToTos(List<CategoryBo> categoryBos) {
		List<CategoryTo> categoryTos=new ArrayList<CategoryTo>();
		Iterator<CategoryBo> iterator=categoryBos.iterator();
		CategoryTo categoryTo=null;
		while (iterator.hasNext()) {
			CategoryBo categoryBo = (CategoryBo) iterator.next();
			categoryTo=new CategoryTo();
			categoryTo.setId(categoryBo.getId());
			categoryTo.setName(categoryBo.getName());
			categoryTo.setDepartment(categoryBo.getDepartmentId().getName());
			if(categoryBo.getCategoryFor().equalsIgnoreCase("U")){
				categoryTo.setCategoryFor("Staff");
			}else if(categoryBo.getCategoryFor().equalsIgnoreCase("S")){
				categoryTo.setCategoryFor("Student");
			}else if(categoryBo.getCategoryFor().equalsIgnoreCase("B")){
				categoryTo.setCategoryFor("Both");
			}
			categoryTos.add(categoryTo);
		}
		return categoryTos;
	}
}
