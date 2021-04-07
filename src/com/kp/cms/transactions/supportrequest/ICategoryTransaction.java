package com.kp.cms.transactions.supportrequest;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.supportrequest.CategoryBo;
import com.kp.cms.forms.supportrequest.CategoryForm;

public interface ICategoryTransaction {

public	Map<Integer, String> getDepartmentMap()throws Exception;
public boolean addOrUpdateCategory(CategoryBo categoryBo, String string)throws Exception;
public boolean checkDuplicate(CategoryForm categoryForm)throws Exception;
public List<CategoryBo> getCategory()throws Exception;
public boolean deleteFineCategory(int id, boolean activate,CategoryForm categoryForm)throws Exception;
public CategoryBo getCategoryById(int id)throws Exception;

}
