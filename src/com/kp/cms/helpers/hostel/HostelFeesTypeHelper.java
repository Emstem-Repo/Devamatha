package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admin.HlFeeType;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
/**
 * 
 * @author kolli.ramamohan
 * @version 1.0
 * @since  
 */
public class HostelFeesTypeHelper {
	private static final Logger log = Logger.getLogger(HostelFeesTypeHelper.class);
	private static volatile HostelFeesTypeHelper hostelFeesTypeHelper = null;	

	public static HostelFeesTypeHelper getInstance() {
		if (hostelFeesTypeHelper == null) {
			hostelFeesTypeHelper = new HostelFeesTypeHelper();
		}
		return hostelFeesTypeHelper;
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
	public List<HostelFeesTypeTo> poupulateBOtoTO(List<HlFeeType> feeList) throws Exception {
		log.info("Start of poupulateBOtoTO of HostelFeesTypeHelper");
		List<HostelFeesTypeTo> hostelFeesTypeToList = new ArrayList<HostelFeesTypeTo>();
		if (feeList != null && !feeList.isEmpty()) {
			Iterator<HlFeeType> iterator = feeList.iterator();

			HostelFeesTypeTo hostelFeesTypeTo = null;
			HlFeeType hlFeeType = null;
			while (iterator.hasNext()) {
				hlFeeType = iterator.next();
				hostelFeesTypeTo = new HostelFeesTypeTo();
				hostelFeesTypeTo.setId(hlFeeType.getId());
				hostelFeesTypeTo.setHostelFeesType(hlFeeType.getName());				
				hostelFeesTypeToList.add(hostelFeesTypeTo);
			}
		}
		log.info("End of poupulateBOtoTO of FeeFinancialYearHelper");
		return hostelFeesTypeToList;
	}
	
	/** 
	 * Used to Populate TO to BO	
	 * @param com.kp.cms.to.fee.FeeFinancialYearTO
	 * @return com.kp.cms.bo.admin.FeeFinancialYear
	 * @throws Exception
	 */
	public HlFeeType populateTOtoBO(HostelFeesTypeTo hostelFeesTypeTo) throws Exception {
		log.info("Start of populateTOtoBO of HostelFeesTypeHelper");
		HlFeeType hlFeeType = new HlFeeType();
		if (hostelFeesTypeTo != null) {
			hlFeeType.setCreatedBy(hostelFeesTypeTo.getCreatedBy());
			hlFeeType.setCreatedDate(new Date());
			hlFeeType.setModifiedBy(hostelFeesTypeTo.getModifiedBy());
			hlFeeType.setLastModifiedDate(new Date());
			hlFeeType.setName(hostelFeesTypeTo.getHostelFeesType());			
			hlFeeType.setIsActive(true);
		}
		log.info("End of populateTOtoBO of HostelFeesTypeHelper");
		return hlFeeType;
	}
	
	/** 
	 * Used to Populate BO to TOEdit	
	 * @param com.kp.cms.bo.admin.HlFeeType
	 * @return com.kp.cms.to.fee.HostelFeesTypeTo
	 * @throws Exception
	 */
	public HostelFeesTypeTo populateBOtoTOEdit(HlFeeType hlFeeType) throws Exception {
		log.info("Start of populateBOtoTOEdit of HostelFeesTypeHelper");
		HostelFeesTypeTo hostelFeesTypeTo = new HostelFeesTypeTo();
		if (hlFeeType != null) {
			hostelFeesTypeTo.setId(hlFeeType.getId());
			hostelFeesTypeTo.setHostelFeesType(hlFeeType.getName());			
		}
		log.info("End of populateBOtoTOEdit of HostelFeesTypeHelper");
		return hostelFeesTypeTo;
	}
	
	/** 
	 * Used to Populate TO to BOUpdate	
	 * @param com.kp.cms.to.hostel.HostelFeesTypeTo
	 * @return com.kp.cms.bo.admin.HlFeeType
	 * @throws Exception
	 */
	public HlFeeType populateTOtoBOUpdate(HostelFeesTypeTo hostelFeesTypeTo) throws Exception {
		log.info("Start of populateTOtoBOUpdate of HostelFeesTypeHelper");
		HlFeeType hlFeeType = new HlFeeType();
		hlFeeType.setId(hostelFeesTypeTo.getId());		
		hlFeeType.setName(hostelFeesTypeTo.getHostelFeesType());
		hlFeeType.setIsActive(true);
		hlFeeType.setModifiedBy(hostelFeesTypeTo.getModifiedBy());
		hlFeeType.setLastModifiedDate(new Date());
		hlFeeType.setCreatedBy(hostelFeesTypeTo.getCreatedBy());
		hlFeeType.setCreatedDate(new Date());
		log.info("End of populateTOtoBOUpdate of HostelFeesTypeHelper");
		return hlFeeType;
	}
}
