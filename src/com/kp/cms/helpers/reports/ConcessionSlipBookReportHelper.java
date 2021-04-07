package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.to.reports.ConcessionSlipBookReportTO;


public class ConcessionSlipBookReportHelper {
	private static final Log log = LogFactory.getLog(ConcessionSlipBookReportHelper.class);
	public static volatile ConcessionSlipBookReportHelper concessionSlipBookReportHelper = null;

	public static ConcessionSlipBookReportHelper getInstance() {
		if (concessionSlipBookReportHelper == null) {
			concessionSlipBookReportHelper = new ConcessionSlipBookReportHelper();
			return concessionSlipBookReportHelper;
		}
		return concessionSlipBookReportHelper;
	}

	public List<ConcessionSlipBookReportTO> copyBosToTO(List<FeeVoucher> voucherList) throws Exception {
		log.debug("inside copyBosToTO");
		FeeVoucher feeVoucher;
		Iterator<FeeVoucher> itr= voucherList.iterator();
		List<ConcessionSlipBookReportTO> voucherToList = new ArrayList<ConcessionSlipBookReportTO>();
		while (itr.hasNext()){
			feeVoucher = itr.next();
			ConcessionSlipBookReportTO concessionSlipBookReportTO = new ConcessionSlipBookReportTO();
			concessionSlipBookReportTO.setBookNo(feeVoucher.getSlipBookNumber());
			concessionSlipBookReportTO.setType(feeVoucher.getType());
			concessionSlipBookReportTO.setStartVoucherNo(feeVoucher.getStartingNumber());
			concessionSlipBookReportTO.setEndVoucherNo(feeVoucher.getEndingNumber());
			concessionSlipBookReportTO.setYear(feeVoucher.getFeeFinancialYear().getYear());
			voucherToList.add(concessionSlipBookReportTO);
		}
		log.debug("exit copyBosToTO");
		return voucherToList;
	}
	
}
