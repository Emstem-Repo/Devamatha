package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.to.fee.FeeGroupTO;

public class FeeGroupHelper {
	
	/**
	 * Converts from BO to TO
	 * @param feeGroup
	 * @return
	 * @throws Exception
	 */
	public static List<FeeGroupTO> convertBoToTo(
			List<FeeGroup> feeGroup) throws Exception {
		List<FeeGroupTO> feeGroupToList = new ArrayList<FeeGroupTO>();
		if (feeGroup != null) {
			Iterator<FeeGroup> feeGroupIterator = feeGroup
					.listIterator();
			while (feeGroupIterator.hasNext()) {
				FeeGroup feeGroupBo = (FeeGroup) feeGroupIterator
						.next();
				FeeGroupTO feeGroupTO = new FeeGroupTO();
				feeGroupTO.setId(feeGroupBo.getId());
				feeGroupTO.setName(feeGroupBo.getName());
				feeGroupTO.setOptional(String.valueOf(feeGroupBo.getIsOptional()));
				feeGroupToList.add(feeGroupTO);
			}
		}
		return feeGroupToList;
	}
}