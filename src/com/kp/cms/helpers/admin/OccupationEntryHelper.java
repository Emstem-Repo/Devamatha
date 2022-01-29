package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Occupation;
import com.kp.cms.to.admin.OccupationTO;

public class OccupationEntryHelper {
	
	/**
	 * Converts Business Objects to Transaction object of the Occupation.
	 * 
	 * @param occupationBo - Represents the Occupation Business objects.
	 * 
	 * @return List - Occupation Transaction object.
	 */
	public static List<OccupationTO> convertBoToTo(List<Occupation> occupationBo) {
		List<OccupationTO> occupationList=new ArrayList<OccupationTO>();
		if(occupationList != null) {
			Iterator<Occupation> iterator=occupationBo.iterator();
			while (iterator.hasNext()) {
				Occupation occupation = (Occupation) iterator.next();
				OccupationTO occupationTO = new OccupationTO();
				occupationTO.setOccupationId(occupation.getId());
				occupationTO.setOccupationName(occupation.getName());
				occupationList.add(occupationTO);
			}			
		}		
		return occupationList;
		
	}
}
