package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.MeritSet;
import com.kp.cms.to.admin.MeritSetTO;

public class MeritSetHelper {

	/**
	 * Converts Business Objects to Transaction object of the merit set.
	 * 
	 * @param meritSetBo - Represents the Merit set Business objects.
	 * 
	 * @return List - Merit set Transaction object.
	 */
	public static List<MeritSetTO>  convertBoToTo(List<MeritSet> meritSetBo) {
		List<MeritSetTO> meritSetList=new ArrayList<MeritSetTO>();
		if(meritSetList != null) {
			Iterator< MeritSet> iterator=meritSetBo.iterator();
			while (iterator.hasNext()) {
				MeritSet meritSet = (MeritSet) iterator.next();
				MeritSetTO meritSetTO = new MeritSetTO();
				meritSetTO.setMeritSetId(meritSet.getId());
				meritSetTO.setMeritSetName(meritSet.getName());
				meritSetList.add(meritSetTO);
			}			
		}		
		return meritSetList;
		
	}
	
}
