package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.to.fee.FeeFinancialYearTO;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since  
 */
public class FeeFinancialYearHelper {
	private static final Logger log = Logger
			.getLogger(FeeFinancialYearHelper.class);
	private static FeeFinancialYearHelper feeFinancialYearHelper = null;

	private FeeFinancialYearHelper() {
	}

	public static FeeFinancialYearHelper getInstance() {
		if (feeFinancialYearHelper == null) {
			feeFinancialYearHelper = new FeeFinancialYearHelper();
		}
		return feeFinancialYearHelper;
	}

	/*
	 * public static FeeFinancialYear convertTOtoBO(FeeFinancialYearEntryForm
	 * feeFinancialYearEntryForm, String mode) throws Exception{
	 * FeeFinancialYear feeFinancialYear=new FeeFinancialYear();
	 * if(mode.equals("Add")){
	 * feeFinancialYear.setCreatedBy(feeFinancialYearEntryForm.getUserId());
	 * feeFinancialYear.setCreatedDate(new Date()); } return feeFinancialYear; }
	 */	
	
	/** 
	 * Used to Populate BO to TO	
	 * @param java.util.List
	 * @return java.util.List<FeeFinancialYear>
	 * @throws Exception
	 */
	public List<FeeFinancialYearTO> poupulateBOtoTO(
			List<FeeFinancialYear> feeList) throws Exception {
		log.info("Start of poupulateBOtoTO of FeeFinancialYearHelper");
		List<FeeFinancialYearTO> feeFinancialYearList = new ArrayList<FeeFinancialYearTO>();
		if (feeList != null && !feeList.isEmpty()) {
			Iterator<FeeFinancialYear> iterator = feeList.iterator();

			FeeFinancialYearTO feeFinancialYearTO = null;
			FeeFinancialYear feeFinancialYear = null;
			while (iterator.hasNext()) {
				feeFinancialYear = iterator.next();
				feeFinancialYearTO = new FeeFinancialYearTO();
				feeFinancialYearTO.setId(feeFinancialYear.getId());
				feeFinancialYearTO
						.setYear(feeFinancialYear.getYear() != null ? feeFinancialYear
								.getYear()
								: null);
				if (feeFinancialYear.getIsCurrent() != null) {
					if (String.valueOf(feeFinancialYear.getIsCurrent()) == "true")
						feeFinancialYearTO.setIsCurrent("Yes");
					else if (String.valueOf(feeFinancialYear.getIsCurrent()) == "false")
						feeFinancialYearTO.setIsCurrent("No");
				}
				feeFinancialYearList.add(feeFinancialYearTO);
			}
		}
		log.info("End of poupulateBOtoTO of FeeFinancialYearHelper");
		return feeFinancialYearList;
	}
	
	/** 
	 * Used to Populate TO to BO	
	 * @param com.kp.cms.to.fee.FeeFinancialYearTO
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYear populateTOtoBO(FeeFinancialYearTO feeFinancialYearTO)
			throws Exception {
		log.info("Start of populateTOtoBO of FeeFinancialYearHelper");
		FeeFinancialYear feeFinancialYear = new FeeFinancialYear();
		if (feeFinancialYearTO != null) {
			feeFinancialYear.setCreatedBy(feeFinancialYearTO.getCreatedBy());
			feeFinancialYear.setCreatedDate(new Date());
			feeFinancialYear.setModifiedBy(feeFinancialYearTO.getModifiedBy());
			feeFinancialYear.setLastModifiedDate(new Date());
			feeFinancialYear.setYear(feeFinancialYearTO.getYear());
			feeFinancialYear.setIsCurrent(Boolean.valueOf(feeFinancialYearTO
					.getIsCurrent()));
			feeFinancialYear.setIsActive(true);
		}
		log.info("End of populateTOtoBO of FeeFinancialYearHelper");
		return feeFinancialYear;
	}
	
	/** 
	 * Used to Populate BO to TOEdit	
	 * @param com.kp.cms.bo.admin.FeeFinancialYear
	 * @return com.kp.cms.to.fee.FeeFinancialYearTO
	 * @throws Exception
	 */
	public FeeFinancialYearTO populateBOtoTOEdit(
			FeeFinancialYear feeFinancialYear) throws Exception {
		log.info("Start of populateBOtoTOEdit of FeeFinancialYearHelper");
		FeeFinancialYearTO feeFinancialYearTO = new FeeFinancialYearTO();
		if (feeFinancialYear != null) {
			feeFinancialYearTO.setId(feeFinancialYear.getId());
			feeFinancialYearTO.setIsCurrent(String.valueOf(feeFinancialYear
					.getIsCurrent()));
			feeFinancialYearTO
					.setYear(feeFinancialYear.getYear() != null ? feeFinancialYear
							.getYear()
							: null);
		}
		log.info("End of populateBOtoTOEdit of FeeFinancialYearHelper");
		return feeFinancialYearTO;
	}
	
	/** 
	 * Used to Populate TO to BOUpdate	
	 * @param com.kp.cms.to.fee.FeeFinancialYearTO
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public FeeFinancialYear populateTOtoBOUpdate(
			FeeFinancialYearTO feeFinancialYearTO) throws Exception {
		log.info("Start of populateTOtoBOUpdate of FeeFinancialYearHelper");
		FeeFinancialYear feeFinancialYear = null;
		feeFinancialYear = new FeeFinancialYear();
		feeFinancialYear.setId(feeFinancialYearTO.getId());
		feeFinancialYear.setIsCurrent(Boolean.valueOf(feeFinancialYearTO
				.getIsCurrent()));
		feeFinancialYear.setYear(feeFinancialYearTO.getYear());
		feeFinancialYear.setIsActive(true);
		feeFinancialYear.setModifiedBy(feeFinancialYearTO.getModifiedBy());
		feeFinancialYear.setLastModifiedDate(new Date());
		feeFinancialYear.setCreatedBy(feeFinancialYearTO.getCreatedBy());
		feeFinancialYear.setCreatedDate(new Date());
		log.info("End of populateTOtoBOUpdate of FeeFinancialYearHelper");
		return feeFinancialYear;
	}
}
