package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.fee.FeeDivisionTO;

public class FeeDivisionHelper {
	
	/**
	 * Converts from BO to TO
	 * @param feeDivision
	 * @return
	 * @throws Exception
	 */
	public static List<FeeDivisionTO> convertBoToTo(
			List<FeeDivision> feeDivision) throws Exception {
		List<FeeDivisionTO> feeDivisionToList = new ArrayList<FeeDivisionTO>();
		if (feeDivision != null) {
			Iterator<FeeDivision> feeDivisionIterator = feeDivision
					.listIterator();
			while (feeDivisionIterator.hasNext()) {
				FeeDivision feeDivisionBo = (FeeDivision) feeDivisionIterator
						.next();
				FeeDivisionTO feeDivisionTO = new FeeDivisionTO();
				feeDivisionTO.setId(feeDivisionBo.getId());
				feeDivisionTO.setName(feeDivisionBo.getName());
				feeDivisionToList.add(feeDivisionTO);
			}
			try {
			} catch (Exception e) {
				throw new ApplicationException(e);
			}
		}
		return feeDivisionToList;
	}
	
	

}
