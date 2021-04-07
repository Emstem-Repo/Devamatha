package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.forms.fee.ConcessionSlipBooksForm;
import com.kp.cms.to.fee.FeeVoucherTO;

public class ConcessionSlipBooksHelper {
	private static final Log log = LogFactory.getLog(ConcessionSlipBooksHelper.class);
	public static ConcessionSlipBooksHelper concessionSlipBooksHelper = null;

	public static ConcessionSlipBooksHelper getInstance() {
		if (concessionSlipBooksHelper == null) {
			concessionSlipBooksHelper = new ConcessionSlipBooksHelper();
			return concessionSlipBooksHelper;
		}
		return concessionSlipBooksHelper;
	}
	/**
	 * 
	 * @param slipBooksForm
	 * @return
	 * @throws Exception
	 */
	public FeeVoucher copyDataFromFormToBO(ConcessionSlipBooksForm slipBooksForm) throws Exception{
		log.debug("inside copyDataFromFormToBO");
		FeeVoucher feeVoucher = new FeeVoucher();
		if(slipBooksForm.getId() != 0) {
			feeVoucher.setId(slipBooksForm.getId());
		}
		feeVoucher.setType(slipBooksForm.getType());
		feeVoucher.setSlipBookNumber(slipBooksForm.getBookNo());
		feeVoucher.setStartingNumber(slipBooksForm.getStartingNo());
		feeVoucher.setEndingNumber(slipBooksForm.getEndingNo());
		if(slipBooksForm.getFinacialYear()!= null && !slipBooksForm.getFinacialYear().trim().isEmpty()){
			FeeFinancialYear financialYear = new FeeFinancialYear();
			financialYear.setId(Integer.parseInt(slipBooksForm.getFinacialYear()));
			feeVoucher.setFeeFinancialYear(financialYear);
		}
		
		feeVoucher.setIsActive(true);
		feeVoucher.setCreatedBy(slipBooksForm.getUserId());
		feeVoucher.setModifiedBy(slipBooksForm.getUserId());
		feeVoucher.setCreatedDate(new Date());
		feeVoucher.setLastModifiedDate(new Date());
		feeVoucher.setAlpha(slipBooksForm.getStartPrefix());
		log.debug("leaving copyDataFromFormToBO");
		return feeVoucher;
		}
	
	/**
	 * 
	 * @param counterList
	 *            this will copy the InvCounter BO to TO
	 * @return invCounterTOList having InvCounterTO objects.
	 */
	public List<FeeVoucherTO> copyVoucherBosToTos(List<FeeVoucher> voucherList) {
		log.debug("inside copyVoucherBosToTos");
		List<FeeVoucherTO> voucherTOList = new ArrayList<FeeVoucherTO>();
		Iterator<FeeVoucher> iterator = voucherList.iterator();
		FeeVoucher feeVoucher;
		FeeVoucherTO feeVoucherTO;
		while (iterator.hasNext()) {
			feeVoucherTO = new FeeVoucherTO();
			feeVoucher = (FeeVoucher) iterator.next();
			feeVoucherTO.setId(feeVoucher.getId());
			feeVoucherTO.setBookNo(feeVoucher.getSlipBookNumber());
			feeVoucherTO.setType(feeVoucher.getType());
			feeVoucherTO.setStartingNo(feeVoucher.getStartingNumber());
			feeVoucherTO.setEndingNo(feeVoucher.getEndingNumber());
			feeVoucherTO.setFinancialYear(feeVoucher.getFeeFinancialYear().getYear());
			feeVoucherTO.setFinId(feeVoucher.getFeeFinancialYear().getId());
			feeVoucherTO.setStartingPrefix(feeVoucher.getAlpha());
			voucherTOList.add(feeVoucherTO);
		}
		log.debug("leaving copyVoucherBosToTos");
		return voucherTOList;
	}

}
