package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdVoucherNumberForm;
import com.kp.cms.to.phd.PhdVoucherNumberTO;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since  
 */
public class PhdVoucherNumberHelper {
	private static final Logger log = Logger
			.getLogger(PhdVoucherNumberHelper.class);
	private static volatile PhdVoucherNumberHelper phdVoucherNumberHelper = null;

	private PhdVoucherNumberHelper() {
	}

	public static PhdVoucherNumberHelper getInstance() {
		if (phdVoucherNumberHelper == null) {
			phdVoucherNumberHelper = new PhdVoucherNumberHelper();
		}
		return phdVoucherNumberHelper;
	}
	
	/**
	 * @param phdVoucherNumberForm
	 * @return
	 * @throws Exception
	 */
	public PhdVoucherNumber populateTOtoBO(PhdVoucherNumberForm phdVoucherNumberForm)
			throws Exception {
		log.info("Start of populateTOtoBO of PhdVoucherNumberHelper");
		PhdVoucherNumber voucherNumber = new PhdVoucherNumber();
		if (phdVoucherNumberForm != null) {
			voucherNumber.setFinancialYear(phdVoucherNumberForm.getFinancialYear());
			voucherNumber.setStartNo(phdVoucherNumberForm.getStartNo());
			voucherNumber.setCurrentNo(phdVoucherNumberForm.getCurrentNo());
			voucherNumber.setCurrentYear(phdVoucherNumberForm.getCurrentYear().equalsIgnoreCase("true") ? true:false);
			voucherNumber.setCreatedBy(phdVoucherNumberForm.getUserId());
			voucherNumber.setCreatedDate(new Date());
			voucherNumber.setModifiedBy(phdVoucherNumberForm.getUserId());
			voucherNumber.setLastModifiedDate(new Date());
			voucherNumber.setIsActive(true);
		}
		log.info("End of populateTOtoBO of PhdVoucherNumberHelper");
		return voucherNumber;
	}
	/**
	 * @param phdFinancialYearList
	 * @return
	 * @throws Exception
	 */
	public List<PhdVoucherNumberTO> poupulateBOtoTO(List<PhdVoucherNumber> voucherBoList) throws Exception {
		log.info("Start of poupulateBOtoTO of PhdVoucherNumberHelper");
		List<PhdVoucherNumberTO> voucherToList = new ArrayList<PhdVoucherNumberTO>();
		if (voucherBoList != null && !voucherBoList.isEmpty()) {
			Iterator<PhdVoucherNumber> iterator = voucherBoList.iterator();

			PhdVoucherNumberTO voucherTo = null;
			PhdVoucherNumber voucherBo = null;
			while (iterator.hasNext()) {
				voucherBo = iterator.next();
				voucherTo = new PhdVoucherNumberTO();
				voucherTo.setId(voucherBo.getId());
				voucherTo.setFinancialYear(voucherBo.getFinancialYear());
				voucherTo.setStartNo(voucherBo.getStartNo());
				voucherTo.setCurrentNo(voucherBo.getCurrentNo());
				if (voucherBo.getCurrentYear() != null) {
					if (String.valueOf(voucherBo.getCurrentYear()).equalsIgnoreCase("true"))
						voucherTo.setCurrentYear("Yes");
					else if (String.valueOf(voucherBo.getCurrentYear()).equalsIgnoreCase("false"))
						voucherTo.setCurrentYear("No");
				}
				voucherToList.add(voucherTo);
			}
		}
		log.info("End of poupulateBOtoTO of PhdVoucherNumberHelper");
		return voucherToList;
	}

	
	/** 
	 * Used to Populate BO to TOEdit	
	 * @param phdVoucherNumberForm 
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return com.kp.cms.to.fee.PCFinancialYearTO
	 * @throws Exception
	 */
	public PhdVoucherNumberTO populateBOtoTOEdit(PhdVoucherNumber phdVoucherNumber, PhdVoucherNumberForm phdVoucherNumberForm) throws Exception {
		log.info("Start of populateBOtoTOEdit of PhdVoucherNumberHelper");
		PhdVoucherNumberTO phdVoucherNumberTO = new PhdVoucherNumberTO();
		if (phdVoucherNumber != null) {
			phdVoucherNumberForm.setId(phdVoucherNumber.getId());
			phdVoucherNumberForm.setFinancialYear(String.valueOf(phdVoucherNumber.getFinancialYear()));
			phdVoucherNumberForm.setStartNo(phdVoucherNumber.getStartNo());
			phdVoucherNumberForm.setCurrentNo(phdVoucherNumber.getCurrentNo());
			if (phdVoucherNumber.getCurrentYear() != null) {
				if (String.valueOf(phdVoucherNumber.getCurrentYear()).equalsIgnoreCase("true"))
					phdVoucherNumberForm.setCurrentYear("true");
				else if (String.valueOf(phdVoucherNumber.getCurrentYear()).equalsIgnoreCase("false"))
					phdVoucherNumberForm.setCurrentYear("false");
			}
		}
		log.info("End of populateBOtoTOEdit of PhdVoucherNumberHelper");
		return phdVoucherNumberTO;
	}
	
	/** 
	 * Used to Populate TO to BOUpdate	
	 * @param com.kp.cms.to.fee.PCFinancialYearTO
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PhdVoucherNumber populateTOtoBOUpdate(PhdVoucherNumberForm phdVoucherNumberForm) throws Exception {
		log.info("Start of populateTOtoBOUpdate of PhdVoucherNumberHelper");
		PhdVoucherNumber phdVoucherNumber = null;
		phdVoucherNumber = new PhdVoucherNumber();
		phdVoucherNumber.setId(phdVoucherNumberForm.getId());
		phdVoucherNumber.setCurrentYear(phdVoucherNumberForm.getCurrentYear().equalsIgnoreCase("true") ? true:false);
		phdVoucherNumber.setFinancialYear(phdVoucherNumberForm.getFinancialYear());
		phdVoucherNumber.setStartNo(phdVoucherNumberForm.getStartNo());
		phdVoucherNumber.setCurrentNo(phdVoucherNumberForm.getCurrentNo());
		phdVoucherNumber.setIsActive(true);
		phdVoucherNumber.setModifiedBy(phdVoucherNumberForm.getUserId());
		phdVoucherNumber.setLastModifiedDate(new Date());
		phdVoucherNumber.setCreatedBy(phdVoucherNumberForm.getUserId());
		phdVoucherNumber.setCreatedDate(new Date());
		log.info("End of populateTOtoBOUpdate of PhdVoucherNumberHelper");
		return phdVoucherNumber;
	}
}
