package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.helpers.admin.ResidentCategoryHelper;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.transactionsimpl.admin.ResidentCategoryTransactionImpl;

public class ResidentCategoryHandler {
	
	private static volatile ResidentCategoryHandler residentCategoryHandler = null;
	
	
	public static ResidentCategoryHandler getInstance() {
		if(residentCategoryHandler == null) {
			residentCategoryHandler = new ResidentCategoryHandler();
			
		}
		return residentCategoryHandler;
	}
	
	public List<ResidentCategoryTO> getResidentCategory() {
		
		ResidentCategoryTransactionImpl residentCategoryTransactionImpl = new ResidentCategoryTransactionImpl();
		List<ResidentCategory> residentCategoryList =	residentCategoryTransactionImpl.getResidentCategory();
		List<ResidentCategoryTO> meritSetToList = ResidentCategoryHelper.convertBOstoTos(residentCategoryList);
		return meritSetToList;
	}
}
