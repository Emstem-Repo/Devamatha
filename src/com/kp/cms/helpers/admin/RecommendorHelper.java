package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.to.admin.RecommendorTO;

public class RecommendorHelper {

	public static List<RecommendorTO> convertRecommenTO(List<Recommendor> recommendorBOList) {
		List<RecommendorTO> recommendlist = new ArrayList<RecommendorTO>();
		if (recommendorBOList != null) {
			Iterator<Recommendor> itr = recommendorBOList.iterator();
			while (itr.hasNext()) {
				Recommendor recommend = (Recommendor) itr.next();
				RecommendorTO to = new RecommendorTO();
				to.setId(recommend.getId());
				to.setName(recommend.getName());
				to.setDescription(recommend.getComments());
				recommendlist.add(to);
			}
		}
		return recommendlist;
	}

}
