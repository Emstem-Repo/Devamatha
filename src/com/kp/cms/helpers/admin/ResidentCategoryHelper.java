package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.to.admin.ResidentCategoryTO;

public class ResidentCategoryHelper {
	
	public static List<ResidentCategoryTO> convertBOstoTos(List<ResidentCategory> residentCategoryBo) {
		List<ResidentCategoryTO> restdentCategoryList=new ArrayList<ResidentCategoryTO>();
		if(restdentCategoryList != null) {
			Iterator< ResidentCategory> iterator=residentCategoryBo.iterator();
			while (iterator.hasNext()) {
				ResidentCategory residentCategory = (ResidentCategory) iterator.next();
				ResidentCategoryTO residentCategoryTo = new ResidentCategoryTO();
				residentCategoryTo.setId(residentCategory.getId());
				residentCategoryTo.setName(residentCategory.getName());
				restdentCategoryList.add(residentCategoryTo);
			}			
		}		
		return restdentCategoryList;
		
	}

}
