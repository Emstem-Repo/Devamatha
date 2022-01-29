package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.hostel.FineCategoryBo;
import com.kp.cms.forms.admin.SingleFieldMasterForm;
import com.kp.cms.forms.hostel.FineCategoryForm;

public interface IFineCategoryTransaction {
	public List<FineCategoryBo> getFineCategory() throws Exception;
	public FineCategoryBo isFineCategoryDuplcated(FineCategoryBo fineCategoryBo) throws Exception;
	public boolean addFineCategoryDetails(FineCategoryBo fineCategoryBo, String mode) throws Exception;
	public boolean deleteFineCategory(int id, Boolean activate,FineCategoryForm fineCategoryForm)throws Exception;
	public FineCategoryBo getFineCategory(int id)throws Exception;
}
