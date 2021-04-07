package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

/**
 * Dec 28, 2009 Created By 9Elements Team
 */
public class ExamRejoinHelper {

	

	public List<KeyValueTO> convertBOToTO_JoiningBatch(
			ArrayList<ExamTypeUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public HashMap<Integer, String> convertBOToTO_Classes_HashMap(
			List listClassType) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		int lenght = listClassType.size();
		for (int i = 0; i < lenght; i++) {

			Object[] row = (Object[]) listClassType.get(i);
			map.put((Integer) row[0], (String) row[1]);
		}
		return null;
	}

	

}
