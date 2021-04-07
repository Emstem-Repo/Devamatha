package com.kp.cms.helpers.pettycash;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.PcFinancialYear;
import com.kp.cms.to.pettycash.PCFinancialYearTO;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since  
 */
public class PCFinancialYearHelper {
	private static final Logger log = Logger
			.getLogger(PCFinancialYearHelper.class);
	private static volatile PCFinancialYearHelper pcFinancialYearHelper = null;

	private PCFinancialYearHelper() {
	}

	public static PCFinancialYearHelper getInstance() {
		if (pcFinancialYearHelper == null) {
			pcFinancialYearHelper = new PCFinancialYearHelper();
		}
		return pcFinancialYearHelper;
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
	 * @return java.util.List<PCFinancialYear>
	 * @throws Exception
	 */
	public List<PCFinancialYearTO> poupulateBOtoTO(
			List<PcFinancialYear> pcList) throws Exception {
		log.info("Start of poupulateBOtoTO of PCFinancialYearHelper");
		List<PCFinancialYearTO> pcFinancialYearList = new ArrayList<PCFinancialYearTO>();
		if (pcList != null && !pcList.isEmpty()) {
			Iterator<PcFinancialYear> iterator = pcList.iterator();

			PCFinancialYearTO pcFinancialYearTO = null;
			PcFinancialYear pcFinancialYear = null;
			while (iterator.hasNext()) {
				pcFinancialYear = iterator.next();
				pcFinancialYearTO = new PCFinancialYearTO();
				pcFinancialYearTO.setId(pcFinancialYear.getId());
				pcFinancialYearTO
						.setYear(pcFinancialYear.getFinancialYear() != null ? pcFinancialYear
								.getFinancialYear()
								: null);
				if (pcFinancialYear.getIsCurrent() != null) {
					if (String.valueOf(pcFinancialYear.getIsCurrent()).equalsIgnoreCase("true"))
						pcFinancialYearTO.setIsCurrent("Yes");
					else if (String.valueOf(pcFinancialYear.getIsCurrent()).equalsIgnoreCase("false"))
						pcFinancialYearTO.setIsCurrent("No");
				}
				pcFinancialYearList.add(pcFinancialYearTO);
			}
		}
		log.info("End of poupulateBOtoTO of PCFinancialYearHelper");
		return pcFinancialYearList;
	}
	
	/** 
	 * Used to Populate TO to BO	
	 * @param com.kp.cms.to.fee.PCFinancialYearTO
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PcFinancialYear populateTOtoBO(PCFinancialYearTO pcFinancialYearTO)
			throws Exception {
		log.info("Start of populateTOtoBO of PCFinancialYearHelper");
		PcFinancialYear pcFinancialYear = new PcFinancialYear();
		if (pcFinancialYearTO != null) {
			pcFinancialYear.setCreatedBy(pcFinancialYearTO.getCreatedBy());
			pcFinancialYear.setCreatedDate(new Date());
			pcFinancialYear.setModifiedBy(pcFinancialYearTO.getModifiedBy());
			pcFinancialYear.setLastModifiedDate(new Date());
			pcFinancialYear.setFinancialYear(pcFinancialYearTO.getYear());
			pcFinancialYear.setIsCurrent(Boolean.valueOf(pcFinancialYearTO
					.getIsCurrent()));
			pcFinancialYear.setIsActive(true);
		}
		log.info("End of populateTOtoBO of PCFinancialYearHelper");
		return pcFinancialYear;
	}
	
	/** 
	 * Used to Populate BO to TOEdit	
	 * @param com.kp.cms.bo.admin.PCFinancialYear
	 * @return com.kp.cms.to.fee.PCFinancialYearTO
	 * @throws Exception
	 */
	public PCFinancialYearTO populateBOtoTOEdit(
			PcFinancialYear pcFinancialYear) throws Exception {
		log.info("Start of populateBOtoTOEdit of PCFinancialYearHelper");
		PCFinancialYearTO pcFinancialYearTO = new PCFinancialYearTO();
		if (pcFinancialYear != null) {
			pcFinancialYearTO.setId(pcFinancialYear.getId());
			pcFinancialYearTO.setIsCurrent(String.valueOf(pcFinancialYear
					.getIsCurrent()));
			pcFinancialYearTO
					.setYear(pcFinancialYear.getFinancialYear() != null ? pcFinancialYear
							.getFinancialYear()
							: null);
		}
		log.info("End of populateBOtoTOEdit of PCFinancialYearHelper");
		return pcFinancialYearTO;
	}
	
	/** 
	 * Used to Populate TO to BOUpdate	
	 * @param com.kp.cms.to.fee.PCFinancialYearTO
	 * @return com.kp.cms.bo.admin.PCFinancialYear
	 * @throws Exception
	 */
	public PcFinancialYear populateTOtoBOUpdate(
			PCFinancialYearTO pcFinancialYearTO) throws Exception {
		log.info("Start of populateTOtoBOUpdate of PCFinancialYearHelper");
		PcFinancialYear pcFinancialYear = null;
		pcFinancialYear = new PcFinancialYear();
		pcFinancialYear.setId(pcFinancialYearTO.getId());
		pcFinancialYear.setIsCurrent(Boolean.valueOf(pcFinancialYearTO
				.getIsCurrent()));
		pcFinancialYear.setFinancialYear(pcFinancialYearTO.getYear());
		pcFinancialYear.setIsActive(true);
		pcFinancialYear.setModifiedBy(pcFinancialYearTO.getModifiedBy());
		pcFinancialYear.setLastModifiedDate(new Date());
		pcFinancialYear.setCreatedBy(pcFinancialYearTO.getCreatedBy());
		pcFinancialYear.setCreatedDate(new Date());
		log.info("End of populateTOtoBOUpdate of PCFinancialYearHelper");
		return pcFinancialYear;
	}
}
