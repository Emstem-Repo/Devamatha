package com.kp.cms.helpers.admission;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.forms.admission.OfflineDetailsForm;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * 
 * @author kshirod.k
 * An helper class for OfflineDetails Entry
 *
 */

public class OfflineDetailsHelper {

	private static final Log log = LogFactory.getLog(OfflineDetailsHelper.class);
	public static volatile OfflineDetailsHelper offlineDetailsHelper = null;
	private OfflineDetailsHelper()
	{
		
	}
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static OfflineDetailsHelper getInstance() {
		if (offlineDetailsHelper == null) {
			offlineDetailsHelper = new OfflineDetailsHelper();
			return offlineDetailsHelper;
		}
		return offlineDetailsHelper;
	}
	
	/**
	 * Used while inserting
	 * Populates Offline Details TO to BO
	 */
	
	public OfflineDetails populateTOtoBO(OfflineDetailsTO offlineDetailsTO)throws Exception{
		log.info("Entering into OfflineDetailsHelper of populateTOtoBO");
		OfflineDetails offlineDetails = null;

		if(offlineDetailsTO !=null){
			offlineDetails = new OfflineDetails();
			offlineDetails.setApplnNo(Integer.parseInt(offlineDetailsTO.getApplicationNo().trim()));
			offlineDetails.setAmount(new BigDecimal(offlineDetailsTO.getAmount()));
			offlineDetails.setReceiptNo(Integer.parseInt(offlineDetailsTO.getReceiptNo()));
			offlineDetails.setYear(offlineDetailsTO.getAcademicYear());
			offlineDetails.setDate(CommonUtil.ConvertStringToSQLDate(offlineDetailsTO.getDate()));
			Course course =new Course();
			course.setId(offlineDetailsTO.getCourseId());
			offlineDetails.setCourse(course);
			offlineDetails.setCreatedBy(offlineDetailsTO.getCreatedBy());
			offlineDetails.setModifiedBy(offlineDetailsTO.getModifiedBy());
			offlineDetails.setCreatedDate(new Date());
			offlineDetails.setLastModifiedDate(new Date());
			offlineDetails.setIsActive(true);
		}
		log.info("Leaving into OfflineDetailsHelper of populateTOtoBO");
		return offlineDetails;
	}
	/**
	 * Used in view
	 * Populates all offline details Bo to TO
	 */
	public List<OfflineDetailsTO>populateOfflineDetailsBOtoTO(List<OfflineDetails> offlineDetailsBOList)throws Exception
	{
		log.info("Entering into OfflineDetailsHelper of populateOfflineDetailsBOtoTO");
		OfflineDetails offlineDetails;
		OfflineDetailsTO offlineDetailsTO;
		List<OfflineDetailsTO> offlineTOList=new ArrayList<OfflineDetailsTO>();
		Iterator<OfflineDetails>iterator= offlineDetailsBOList.iterator();
		while (iterator.hasNext()) {
			offlineDetails = iterator.next();
				offlineDetailsTO = new OfflineDetailsTO();
				offlineDetailsTO.setId(offlineDetails.getId());
			if(offlineDetails.getCourse().getId()!=0){
				offlineDetailsTO.setCourseId(offlineDetails.getCourse().getId());
				offlineDetailsTO.setCourseName(offlineDetails.getCourse().getName());
			}
				offlineDetailsTO.setApplicationNo(String.valueOf(offlineDetails.getApplnNo()));
				offlineDetailsTO.setReceiptNo(String.valueOf(offlineDetails.getReceiptNo()));
				offlineDetailsTO.setAcademicYear(offlineDetails.getYear());
				offlineDetailsTO.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(offlineDetails.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
				offlineDetailsTO.setAmount(String.valueOf(offlineDetails.getAmount()));
				offlineTOList.add(offlineDetailsTO);
		}
		log.info("Leaving from OfflineDetailsHelper of populateOfflineDetailsBOtoTO");
		return offlineTOList;		
	}
	
	/**
	 * Used while edit button is clicked
	 * Populates Offline Details BO to TO
	 */
	public OfflineDetailsTO copyOfflineDetailsBOtoTO(OfflineDetails offlineDetails)throws Exception{
		log.info("Entering into OfflineDetailsHelper of copyOfflineDetailsBOtoTO");
		OfflineDetailsTO offlineDetailsTO = null;
		if(offlineDetails!=null){
			offlineDetailsTO = new OfflineDetailsTO();
			offlineDetailsTO.setId(offlineDetails.getId()!=0 ? offlineDetails.getId():0);
			offlineDetailsTO.setCourseId(offlineDetails.getCourse().getId()!=0 ? offlineDetails.getCourse().getId():0);
			offlineDetailsTO.setApplicationNo(String.valueOf(offlineDetails.getApplnNo()!=null ? offlineDetails.getApplnNo() : null));
			offlineDetailsTO.setReceiptNo(String.valueOf(offlineDetails.getReceiptNo()!=null ? offlineDetails.getReceiptNo():null));
			offlineDetailsTO.setAmount(String.valueOf(offlineDetails.getAmount()!=null ? offlineDetails.getAmount():null));
			offlineDetailsTO.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(offlineDetails.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
			offlineDetailsTO.setAcademicYear(offlineDetails.getYear() !=0 ? offlineDetails.getYear():0);
			return offlineDetailsTO;
		}
		log.info("Leaving from OfflineDetailsHelper of copyOfflineDetailsBOtoTO");
		return null;
	}
	
	/**
	 * Used in update
	 * Populates form to Offline Details BO
	 */
	public OfflineDetails populateFormtoBO(OfflineDetailsForm offlineDetailsForm)throws Exception{
		log.info("Entering into OfflineDetailsHelper of populateFormtoBO");
			OfflineDetails offlineDetails= new OfflineDetails();
			offlineDetails.setId(offlineDetailsForm.getId());
			Course course = new Course();
			course.setId(Integer.parseInt(offlineDetailsForm.getCourseId()));
			offlineDetails.setCourse(course);
			offlineDetails.setApplnNo(Integer.parseInt(offlineDetailsForm.getApplicationNo()));
			offlineDetails.setReceiptNo(Integer.parseInt(offlineDetailsForm.getReceiptNo()));
			offlineDetails.setYear(Integer.parseInt(offlineDetailsForm.getAcademicYear()));
			offlineDetails.setAmount(new BigDecimal(offlineDetailsForm.getAmount()));
			offlineDetails.setDate(CommonUtil.ConvertStringToSQLDate(offlineDetailsForm.getDate()));
			offlineDetails.setModifiedBy(offlineDetailsForm.getUserId());
			offlineDetails.setLastModifiedDate(new Date());
			offlineDetails.setIsActive(true);
			log.info("Leaving into OfflineDetailsHelper of populateFormtoBO");
			return offlineDetails;
	}
}
