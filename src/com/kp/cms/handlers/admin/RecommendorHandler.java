package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.List;

import com.kp.cms.bo.admin.Recommendor;
import com.kp.cms.helpers.admin.RecommendorHelper;
import com.kp.cms.to.admin.RecommendorTO;
import com.kp.cms.transactions.admin.IRecommendedTxn;
import com.kp.cms.transactionsimpl.admin.RecommendedTxnImpl;

public class RecommendorHandler {

	private static volatile RecommendorHandler recommendorHandler = null;

	private RecommendorHandler() {
	}

	public static RecommendorHandler getInstance() {
		if (recommendorHandler == null) {
			recommendorHandler = new RecommendorHandler();
		}
		return recommendorHandler;
	}
	
	public List<RecommendorTO> getRecommendor() {
		IRecommendedTxn txn = RecommendedTxnImpl.getInstance();
		List<Recommendor> recommendorBOList = txn.getRecommendor();
		List<RecommendorTO> recommendorlist = RecommendorHelper.convertRecommenTO(recommendorBOList);
		return recommendorlist;
	}

}
