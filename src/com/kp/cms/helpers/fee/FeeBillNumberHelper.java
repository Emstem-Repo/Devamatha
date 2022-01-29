package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeBillNumber;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.to.fee.FeeBillNumberTO;



public class FeeBillNumberHelper {
	private static final Log log = LogFactory.getLog(FeeBillNumberHelper.class);
	
	private static FeeBillNumberHelper feeBillNumberHelper = null;
	

	private FeeBillNumberHelper() {
	}

	public static FeeBillNumberHelper getInstance() {
		if (feeBillNumberHelper == null) {
			feeBillNumberHelper = new FeeBillNumberHelper();
		}
		return feeBillNumberHelper;
	}
/**
 * 	
 * @param feeList
 * Used in getting all feeBillNumber
 * @return
 */
	public List<FeeBillNumberTO> poupulateBOtoTO(List<FeeBillNumber> feeList)throws Exception
	{
		log.info("Start of poupulateBOtoTO of FeeBillNumber Helper");
		List<FeeBillNumberTO>feeBillNoList=new ArrayList<FeeBillNumberTO>();
		if(feeList!=null && !feeList.isEmpty()){
			Iterator<FeeBillNumber> iterator=feeList.iterator();
			
			FeeBillNumberTO feeBillNumberTO;
			while (iterator.hasNext()) {
				FeeBillNumber feeBillNumber = iterator.next();
				feeBillNumberTO=new FeeBillNumberTO();
				feeBillNumberTO.setId(feeBillNumber.getId());
				if(feeBillNumber.getFeeFinancialYear()!=null && feeBillNumber.getFeeFinancialYear().getIsActive()!=null
				&& feeBillNumber.getFeeFinancialYear().getIsActive() && feeBillNumber.getFeeFinancialYear().getYear()!=null){
					feeBillNumberTO.setAcademicYear(feeBillNumber.getFeeFinancialYear().getYear());
				}
				if(feeBillNumber.getBillNoFrom()!=null && !feeBillNumber.getBillNoFrom().isEmpty()){
					feeBillNumberTO.setBillNo(feeBillNumber.getBillNoFrom());
				}
				feeBillNoList.add(feeBillNumberTO);
			}			
		}
		log.info("End of poupulateBOtoTO of FeeBillNumber Helper");
		return feeBillNoList;		
	}
	
	/**
	 * Used while adding
	 */

	public FeeBillNumber populateTotoBO(FeeBillNumberTO feeBillNumberTO)throws Exception
	{
		log.info("Start of populateTotoBO of FeeBillNumber Helper");
		FeeBillNumber billNumber=new FeeBillNumber();
		if(feeBillNumberTO!=null){
		billNumber.setBillNoFrom(String.valueOf(feeBillNumberTO.getBillNo()));
		billNumber.setCurrentBillNo(String.valueOf(feeBillNumberTO.getBillNo()));
		FeeFinancialYear financialYear = new FeeFinancialYear();
		financialYear.setId(Integer.parseInt(feeBillNumberTO.getAcademicYear()));
		billNumber.setFeeFinancialYear(financialYear);
		billNumber.setCreatedBy(feeBillNumberTO.getCreatedBy());
		billNumber.setModifiedBy(feeBillNumberTO.getModifiedBy());
		billNumber.setCreatedDate(new Date());
		billNumber.setLastModifiedDate(new Date());
		billNumber.setIsActive(true);
	}
		log.info("End of populateTotoBO of FeeBillNumber Helper");
		return billNumber;
	}
	
	/**
	 * Used in editing
	 */
	public FeeBillNumberTO populateBotoTOEdit(FeeBillNumber feeBillNumber)throws Exception
	{
		log.info("Start of populateBotoTOEdit of FeeBillNumber Helper");
		FeeBillNumberTO billNumberTO=new FeeBillNumberTO();
		if(feeBillNumber!=null){
			billNumberTO.setBillNo(feeBillNumber.getBillNoFrom()!=null ? feeBillNumber.getBillNoFrom():null);
			if(feeBillNumber.getFeeFinancialYear()!=null){
				if(feeBillNumber.getFeeFinancialYear().getYear()!=null){
					billNumberTO.setAcademicYear(feeBillNumber.getFeeFinancialYear().getYear());
				}
				billNumberTO.setFinacialYearID(feeBillNumber.getFeeFinancialYear().getId());
			}
			billNumberTO.setId(feeBillNumber.getId());
		}
		log.info("End of populateBotoTOEdit of FeeBillNumber Helper");
		return billNumberTO;
	}
	/**
	 * Used in update
	 */
	public FeeBillNumber populateTotoBOUpdate(FeeBillNumberTO feeBillNumberTO)throws Exception
	{
		log.info("Start of populateTotoBOUpdate of FeeBillNumber Helper");
		FeeBillNumber feeBillNumber = null;
			feeBillNumber=new FeeBillNumber();
			feeBillNumber.setId(feeBillNumberTO.getId());
			feeBillNumber.setBillNoFrom(String.valueOf(feeBillNumberTO.getBillNo()));
			feeBillNumber.setCurrentBillNo(String.valueOf(feeBillNumberTO.getBillNo()));
			FeeFinancialYear financialYear = new FeeFinancialYear();
			financialYear.setId(Integer.parseInt(feeBillNumberTO.getAcademicYear()));
			feeBillNumber.setFeeFinancialYear(financialYear);
			feeBillNumber.setIsActive(true);
			feeBillNumber.setModifiedBy(feeBillNumberTO.getModifiedBy());
			feeBillNumber.setLastModifiedDate(new Date());
		log.info("End of populateTotoBOUpdate of FeeBillNumber Helper");
		return feeBillNumber;
	}	
}
