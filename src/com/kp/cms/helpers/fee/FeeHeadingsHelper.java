package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import com.kp.cms.bo.admin.FeeGroup;
import com.kp.cms.bo.admin.FeeHeading;
import com.kp.cms.forms.fee.FeeHeadingsForm;
import com.kp.cms.to.fee.FeeGroupTO;
import com.kp.cms.to.fee.FeeHeadingTO;

public class FeeHeadingsHelper {

	/**
	 * @return a single instance of FeeHeadingsHelper.
	 * @throws Exception
	 */
	private static final Logger log=Logger.getLogger(FeeHeadingsHelper.class);
	private static FeeHeadingsHelper feeHeadingsHelper= null;
	public static FeeHeadingsHelper getInstance() {
	      if(feeHeadingsHelper == null) {
	    	  feeHeadingsHelper = new FeeHeadingsHelper();
	    	  return feeHeadingsHelper;
	      }
	      return feeHeadingsHelper;
	}
	
	/**
	 * This method is used to convert BOs to TOs from feeHeadingList.
	 * @param feeHeadingList
	 * @return list of FeeHeadigTO.
	 * @throws Exception
	 */
	
	public List<FeeHeadingTO> convertBOstoTOs(List<FeeHeading> feeHeadingList) throws Exception {
		log.info("call of convertBOstoTOs in FeeHeadingHelper class.");
		List<FeeHeadingTO> list=new ArrayList<FeeHeadingTO>();
		FeeHeadingTO feeHeadingTO;
		FeeGroupTO feeGroupTO;
		FeeHeading feeHeading;
			Iterator<FeeHeading> iterator = feeHeadingList.iterator();
			while (iterator.hasNext()) {
				feeHeading = (FeeHeading) iterator.next();
				feeHeadingTO = new FeeHeadingTO();
				feeGroupTO = new FeeGroupTO();
				feeHeadingTO.setId(feeHeading.getId());
				feeHeadingTO.setName(feeHeading.getName());
				feeGroupTO.setId(feeHeading.getFeeGroup().getId());
				feeGroupTO.setName(feeHeading.getFeeGroup().getName());
				feeHeadingTO.setFeeGroupTO(feeGroupTO);
				
				list.add(feeHeadingTO);
			}
			log.info("end of convertBOstoTOs in FeeHeadingHelper class.");
		return list;
	}
	
	/**
	 * This method is used to convert TOs to BOs from feeHeadingForm.
	 * @param feeHeadingsForm
	 * @return FeeHeading BO instance.
	 * @throws Exception
	 */
	
	public FeeHeading convertTOstoBOs(FeeHeadingsForm feeHeadingsForm) throws Exception {
		log.info("call of convertTOstoBOs in FeeHeadingHelper class.");
		FeeHeading feeHeading = new FeeHeading();
		feeHeading.setCreatedDate(new Date());
		feeHeading.setCreatedBy(feeHeadingsForm.getUserId());
		feeHeading.setModifiedBy(feeHeadingsForm.getUserId());
		feeHeading.setIsActive(Boolean.TRUE);
		feeHeading.setName(feeHeadingsForm.getFeesName());
		FeeGroup feeGroup= new FeeGroup();
		feeGroup.setId(Integer.valueOf(feeHeadingsForm.getFeeGroup()));
		
		feeHeading.setFeeGroup(feeGroup);
		log.info("end of convertTOstoBOs in FeeHeadingHelper class.");
		return feeHeading;
	}
	
	/**
	 * This method is used to convert BOs to TOs from feeHeadingList.
	 * @param feeHeadingsList.
	 * @return FeeHeading TO instance.
	 * @throws Exception
	 */
	
	public FeeHeadingTO convertBOstoTOsForEdit(List<FeeHeading> feeHeadingList) throws Exception {
		log.info("call of convertBOstoTOsForEdit in FeeHeadingHelper class.");
		FeeHeadingTO feeHeadingTO = null; 
		FeeGroupTO feeGroupTO;
		Iterator<FeeHeading> iterator = feeHeadingList.iterator();
		FeeHeading feeHeading;
		while (iterator.hasNext()) {
			feeHeading = (FeeHeading) iterator.next();
			feeHeadingTO = new FeeHeadingTO();
			feeHeadingTO.setId(feeHeading.getId());
			feeHeadingTO.setName(feeHeading.getName());
			feeGroupTO = new FeeGroupTO();
			feeGroupTO.setId(feeHeading.getFeeGroup().getId());
			feeGroupTO.setName(feeHeading.getFeeGroup().getName());
			feeHeadingTO.setFeeGroupTO(feeGroupTO);
		}
		log.info("end of convertBOstoTOsForEdit in FeeHeadingHelper class.");
		return feeHeadingTO;
	}
	
}