package com.kp.cms.helpers.admission;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SettlementExceptionUploadHelper {
	private static final Log log = LogFactory.getLog(SettlementExceptionUploadHelper.class);
	private static volatile SettlementExceptionUploadHelper settlementExceptionHelper = null;
	
	private SettlementExceptionUploadHelper() {
	}
	
	/**
	 * @return
	 */
	public static SettlementExceptionUploadHelper getInstance() {
		if (settlementExceptionHelper == null) {
			settlementExceptionHelper = new SettlementExceptionUploadHelper();
		}
		return settlementExceptionHelper;
	}

}
