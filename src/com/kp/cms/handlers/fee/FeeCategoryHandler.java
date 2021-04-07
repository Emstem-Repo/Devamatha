package com.kp.cms.handlers.fee;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.fee.FeeCategoryForm;
import com.kp.cms.helpers.fee.FeeCategoryHelper;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.transactions.fee.IFeeCategoryTransaction;
import com.kp.cms.transactionsimpl.fee.FeeCategoryTransactionImpl;



public class FeeCategoryHandler {
	private static volatile FeeCategoryHandler feeCategoryHandler = null;
    private FeeCategoryHandler(){
    	
    }
	public static FeeCategoryHandler getInstance() {
		if (feeCategoryHandler == null) {
			feeCategoryHandler = new FeeCategoryHandler();
		}
		return feeCategoryHandler;
	}
	
	public boolean addFeeCategory(FeeCategoryForm feeCategoryForm,HttpServletRequest request) throws Exception{
		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		FeeCategory feeCategory1 = FeeCategoryHelper.convertTOtoBO(feeCategoryForm,"Add");
		FeeCategory feeCategory = FeeCategoryHelper.convertTOtoBO(feeCategoryForm,"Add");
		feeCategory = txn.isFeeCategoryDuplcated(feeCategory);
		boolean isFeeCategoryAdded = false;
		if (feeCategory != null && feeCategory.getIsActive()) {
			throw new DuplicateException();
		} else if (feeCategory != null && !feeCategory.getIsActive()) {
			request.getSession().setAttribute("FeeCategory",feeCategory);
			feeCategoryForm.setReactivateid(feeCategory.getId());
			throw new ReActivateException();
		}else if(txn != null) {
			isFeeCategoryAdded = txn.addFeeCategory(feeCategory1);
		}
		return isFeeCategoryAdded;
	}
	
	public boolean deleteFeeCategory(int feeCategoryId, String userId) throws Exception {
		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		boolean isFeeCategoryDeleted = false;
		if (txn != null) {
			isFeeCategoryDeleted = txn.deleteFeeCategory(feeCategoryId,userId);
		}
		return isFeeCategoryDeleted;
	}
	
	public boolean reActivateFeeCategory(FeeCategory feeCategory, String userId) throws Exception {
		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		boolean isFeeCategoryReActivate=txn.reActivateFeeCategory(feeCategory,userId);
		return isFeeCategoryReActivate;
	}
	
	public List<FeeCategoryTo> getFeeCategories() throws Exception{
		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		List<FeeCategory> feeCategoryBOList = txn.getFeeCategories();
		List<FeeCategoryTo> feeCategoryList = FeeCategoryHelper.convertBOsToTos(feeCategoryBOList);
		return feeCategoryList;
	}
	public boolean updateFeeCategory(FeeCategoryForm feeCategoryForm,HttpServletRequest request)  throws Exception{

		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		boolean isFeeCategoryEdited = false;
		FeeCategory feeCategory1 = FeeCategoryHelper.convertTOtoBO(feeCategoryForm,"Update");
		FeeCategory feeCategory = FeeCategoryHelper.convertTOtoBO(feeCategoryForm,"Update");
		feeCategory = txn.isFeeCategoryDuplcated(feeCategory);
		if(!feeCategoryForm.getFeeCategoryName().equals(feeCategoryForm.getOrigFeeCategoryName().trim())){
		if (feeCategory != null && feeCategory.getIsActive()) {
			throw new DuplicateException();
		}
		}
		if (feeCategory != null && !feeCategory.getIsActive()) {
			request.getSession().setAttribute("FeeCategory",feeCategory);
			feeCategoryForm.setReactivateid(feeCategory.getId());
			throw new ReActivateException();
		}else if (txn != null) {
			isFeeCategoryEdited = txn.updateFeeCategory(feeCategory1);
		}
		return isFeeCategoryEdited;
	
	}
	
	public Map<Integer, String> getFeeCategoryMap() throws Exception {
		IFeeCategoryTransaction txn = new FeeCategoryTransactionImpl();
		List<FeeCategory> feeCategoryList = txn.getFeeCategories();
		Map<Integer, String> feeCategoryMap = new HashMap<Integer, String>();
		Iterator<FeeCategory> itr = feeCategoryList.iterator();
		while (itr.hasNext()) {
			FeeCategory feeCategory = itr.next();
			feeCategoryMap.put(feeCategory.getId(), feeCategory.getName());
		}
		return feeCategoryMap;
	}
}
