package com.kp.cms.handlers.supportrequest;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.forms.supportrequest.CategoryForm;
import com.kp.cms.helpers.supportrequest.CategoryHelper;
import com.kp.cms.to.supportrequest.CategoryTo;
import com.kp.cms.transactions.supportrequest.ICategoryTransaction;
import com.kp.cms.transactionsimpl.supportrequest.CategoryTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class CategoryHandler {
	ICategoryTransaction transaction=CategoryTransactionImpl.getInstance();
	CategoryHelper categoryHelper=CategoryHelper.getInstance();
	public static volatile CategoryHandler categoryHandler = null;
	
	private CategoryHandler(){
		
	}
	private static Log log = LogFactory.getLog(CategoryHandler.class);
	public static CategoryHandler getInstance() {
		if (categoryHandler == null) {
			categoryHandler = new CategoryHandler();
			return categoryHandler;
		}
		return categoryHandler;
	}
	public void getDepartmentMap(CategoryForm categoryForm)throws Exception {
		Map<Integer,String> departmentMap=transaction.getDepartmentMap();
		if(departmentMap!=null && !departmentMap.isEmpty()){
			categoryForm.setDepartmentMap(CommonUtil.sortMapByValue(departmentMap));
		}
		
	}
	/**
	 * save category details
	 * @param categoryForm
	 * @return
	 */
	public boolean addCategory(CategoryForm categoryForm)throws Exception {
		CategoryBo categoryBo=categoryHelper.convertFormToBo(categoryForm);
		boolean flag=transaction.addOrUpdateCategory(categoryBo,categoryForm.getMode());
		return flag;
	}
	/**
	 * duplicate checking
	 * @param categoryForm
	 * @return
	 * @throws Exception
	 */
	public boolean checkDuplicate(CategoryForm categoryForm)throws Exception {
		boolean flag=transaction.checkDuplicate(categoryForm);
		return flag;
	}
	public void getCategory(CategoryForm categoryForm)throws Exception {
		List<CategoryTo> categoryTos=null;
		List<CategoryBo> categoryBos=transaction.getCategory();
		if(categoryBos!=null && !categoryBos.isEmpty()){
			categoryTos=categoryHelper.convertBoToTos(categoryBos);
		}
		categoryForm.setCategoryList(categoryTos);
	}
	public boolean deleteCategory(int id, boolean activate, CategoryForm categoryForm)throws Exception {
		boolean result = transaction.deleteFineCategory(id, activate, categoryForm);
		log.debug("Handler: deleteSingleFieldMaster");
		return result;
	}
	public void editFineEntry(CategoryForm categoryForm) throws Exception{
		CategoryBo categoryBo=transaction.getCategoryById(categoryForm.getId());
		if(categoryBo!=null){
			categoryForm.setId(categoryBo.getId());
			categoryForm.setDepartmentId(String.valueOf(categoryBo.getDepartmentId().getId()));
			categoryForm.setName(categoryBo.getName());
			categoryForm.setCategoryFor(categoryBo.getCategoryFor());
			if(categoryBo.getEmail()!=null && !categoryBo.getEmail().isEmpty()){
				categoryForm.setEmail(categoryBo.getEmail());
			}
		}
		
	}
	public boolean updateCategory(CategoryForm categoryForm)throws Exception {
		boolean flag=false;
		CategoryBo categoryBo=transaction.getCategoryById(categoryForm.getId());
		if(categoryBo!=null){
			categoryBo.setName(categoryForm.getName());
			categoryBo.setCategoryFor(categoryForm.getCategoryFor());
			Department department=new Department();
			department.setId(Integer.parseInt(categoryForm.getDepartmentId()));
			categoryBo.setDepartmentId(department);
			categoryBo.setLastModifiedDate(new Date());
			categoryBo.setModifiedBy(categoryForm.getUserId());
			if(categoryForm.getEmail()!=null && !categoryForm.getEmail().isEmpty()){
					categoryBo.setEmail(categoryForm.getEmail());
			}else{
				categoryBo.setEmail(null);
			}
		}
		flag=transaction.addOrUpdateCategory(categoryBo,categoryForm.getMode());
		return flag;
	}
}
