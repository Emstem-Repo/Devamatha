package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.fees.FeeCategory;
import com.kp.cms.forms.fee.FeeCategoryForm;
import com.kp.cms.to.fee.FeeCategoryTo;

public class FeeCategoryHelper {

	public static FeeCategory convertTOtoBO(FeeCategoryForm feeCategoryForm, String mode) throws Exception{
		FeeCategory feeCategory = new FeeCategory();
		feeCategory.setId(feeCategoryForm.getFeeCategoryId());
		if(mode.equals("Update")){
			feeCategory.setLastModifiedDate(new Date());
			feeCategory.setModifiedBy(feeCategoryForm.getUserId());
		}else if(mode.equals("Add")){
			feeCategory.setCreatedBy(feeCategoryForm.getUserId());
			feeCategory.setCreatedDate(new Date());
			feeCategory.setLastModifiedDate(new Date());
			feeCategory.setModifiedBy(feeCategoryForm.getUserId());
		}
		feeCategory.setName(feeCategoryForm.getFeeCategoryName());
		feeCategory.setIsActive(true);
		return feeCategory;
	}

	public static List<FeeCategoryTo> convertBOsToTos(List<FeeCategory> feeCategoryBOList) {

		List<FeeCategoryTo> feeCategoryList = new ArrayList<FeeCategoryTo>();
		if (feeCategoryBOList != null) {

			Iterator<FeeCategory> iterator = feeCategoryBOList.iterator();
			while (iterator.hasNext()) {
				FeeCategory feeCategorybo = (FeeCategory) iterator.next();
				FeeCategoryTo feeCategoryTo = new FeeCategoryTo();
				feeCategoryTo.setFeeCategoryId(feeCategorybo.getId());
				feeCategoryTo.setFeeCategoryName(feeCategorybo.getName());
				feeCategoryList.add(feeCategoryTo);
			}
		}
		return feeCategoryList;
		}
}
