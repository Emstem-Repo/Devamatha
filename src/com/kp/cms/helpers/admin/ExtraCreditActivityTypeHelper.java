package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.ExtraCreditActivityType;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.forms.admin.ExtraCreditActivityTypeForm;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.ExtraCreditActivityTypeTo;
import com.kp.cms.to.admin.ReligionTO;

public class ExtraCreditActivityTypeHelper {
	
	
	
	
	public static List<ExtraCreditActivityTypeTo> convertBOtoToActiveList(List<ExtraCreditActivityType> activityBo) {
		List<ExtraCreditActivityTypeTo> activityList = new ArrayList<ExtraCreditActivityTypeTo>();
		if (activityBo != null) {
			Iterator<ExtraCreditActivityType> iterator = activityBo.iterator();
			while (iterator.hasNext()) {
				ExtraCreditActivityType activitybo = (ExtraCreditActivityType) iterator.next();
				ExtraCreditActivityTypeTo extraCreditActivityTypeTo = new ExtraCreditActivityTypeTo();
				extraCreditActivityTypeTo.setActivityName(activitybo.getName());
				extraCreditActivityTypeTo.setActivityTypeId(activitybo.getId());
				activityList.add(extraCreditActivityTypeTo);
			}
		}
		return activityList;
	}

	public static ExtraCreditActivityType convertBOtoTO(
			ExtraCreditActivityTypeForm extraCreditActivityTypeForm,
			String mode) {
		ExtraCreditActivityType type=new ExtraCreditActivityType();
		type.setId(extraCreditActivityTypeForm.getActivityTypeId());
		if(mode.equals("Update")){
			type.setModifiedBy(extraCreditActivityTypeForm.getUserId());
			type.setLastModifiedDate(new Date());
		}else if(mode.equals("Add")){
			type.setCreatedBy(extraCreditActivityTypeForm.getUserId());
			type.setCreatedDate(new Date());
		}
		type.setName(extraCreditActivityTypeForm.getActivityName());
		type.setIsActive(true);
		return type;	
		
	}

}
