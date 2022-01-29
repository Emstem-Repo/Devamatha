package com.kp.cms.handlers.admission;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.bo.admin.OfflineDetails;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.OfflineDetailsForm;
import com.kp.cms.helpers.admission.OfflineDetailsHelper;
import com.kp.cms.to.admission.OfflineDetailsTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactions.admission.IOfflineDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;
import com.kp.cms.transactionsimpl.admission.OfflineDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;


/**
 * 
 * @author kshirod.k
 * An handler class for OfflineDetails Entry
 *
 */

public class OfflineDetailsHandler {
	
	private static final Log log = LogFactory.getLog(OfflineDetailsHandler.class);
	public static volatile OfflineDetailsHandler offlineDetailsHandler = null;
	private OfflineDetailsHandler()
	{
		
	}
	
	IOfflineDetailsTransaction transaction=new OfflineDetailsTransactionImpl();
	
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static OfflineDetailsHandler getInstance() {
		if (offlineDetailsHandler == null) {
			offlineDetailsHandler = new OfflineDetailsHandler();
			return offlineDetailsHandler;
		}
		return offlineDetailsHandler;
	}
	/**
	 * 
	 * @param offlineDetailsForm
	 * @return 
	 * Used in adding
	 */
	public boolean addOfflineDetails(OfflineDetailsForm offlineDetailsForm)throws Exception{
		log.info("Entering into OfflineDetailsHandler of addOfflineDetails");
		OfflineDetailsTO offlineDetailsTO=new OfflineDetailsTO();
		offlineDetailsTO.setApplicationNo(offlineDetailsForm.getApplicationNo().trim());
		offlineDetailsTO.setAmount(offlineDetailsForm.getAmount());
		offlineDetailsTO.setReceiptNo(offlineDetailsForm.getReceiptNo());
		offlineDetailsTO.setAcademicYear(Integer.parseInt(offlineDetailsForm.getAcademicYear()));
		offlineDetailsTO.setDate(offlineDetailsForm.getDate());
		offlineDetailsTO.setCreatedBy(offlineDetailsForm.getUserId());
		offlineDetailsTO.setModifiedBy(offlineDetailsForm.getUserId());
		offlineDetailsTO.setCourseId(Integer.parseInt(offlineDetailsForm.getCourseId()));
		OfflineDetails offlineDetails=OfflineDetailsHelper.getInstance().populateTOtoBO(offlineDetailsTO);
		
		if(transaction!=null){
			return transaction.addOfflineDetails(offlineDetails);
		}
		log.info("Leaving into OfflineDetailsHandler of addOfflineDetails");
		return false;
	}
	
	/**
	 * Used in getting max receipt number
	 */
	
	public int getMaxReceiptNo()throws Exception{
		log.info("Entering into OfflineDetailsHandler of getMaxReceiptNo");
		return transaction.getMaxReceiptNo();
	}
	/**
	 * Gets all the offline Details
	 */
	public List<OfflineDetailsTO> getAllOfflineDetails()throws Exception
	{
		log.info("Entering into OfflineDetailsHandler of getAllOfflineDetails");
		if(transaction!=null){
			List<OfflineDetails> offlineDetailsBOList=transaction.getAllOfflineDetails();
			if(offlineDetailsBOList!=null && !offlineDetailsBOList.isEmpty()){
				return OfflineDetailsHelper.getInstance().populateOfflineDetailsBOtoTO(offlineDetailsBOList);
			}
		}
		log.info("Leaving into OfflineDetailsHandler of getAllOfflineDetails");
		return null;
	}
	/**
	 * Used while delete
	 */
	public boolean deleteOfflineDetails(int id)throws Exception{
		log.info("Entering into OfflineDetailsHandler of deleteOfflineDetails");
		if(transaction!=null){
			return transaction.deleteOfflineDetails(id);
		}
		log.info("Leaving into OfflineDetailsHandler of deleteOfflineDetails");
		return false;
	}
	
	/**
	 * Used while edit button is clicked
	 */
	public OfflineDetailsTO getOfflineDetailsbyId(int id)throws Exception{
		log.info("Entering into OfflineDetailsHandler of getOfflineDetailsbyId");
		OfflineDetails offlineDetails = null;
		if(transaction!=null){
			offlineDetails=transaction.getOfflineDetailsbyId(id);
		}
		if(offlineDetails!=null){
			return OfflineDetailsHelper.getInstance().copyOfflineDetailsBOtoTO(offlineDetails);
		}
		log.info("Leaving into OfflineDetailsHandler of getOfflineDetailsbyId");
		return null;
	}
	/**
	 * Used in update operation
	 */
	public boolean updateOfflineDetails(OfflineDetailsForm offlineDetailsForm)throws Exception{
		log.info("Entering into OfflineDetailsHandler of updateOfflineDetails");
		OfflineDetails offlineDetails=OfflineDetailsHelper.getInstance().populateFormtoBO(offlineDetailsForm);
		if(offlineDetails!=null && transaction != null){
			return transaction.updateOfflineDetails(offlineDetails);
		}
		log.info("Leaving into OfflineDetailsHandler of updateOfflineDetails");
		return false;
	}
	/**
	 * Check for duplicate on course and application no
	 */
	public OfflineDetails getOfflineDetailsonApplNoYear(String applicationNo, int year)throws Exception
	{
		log.info("Entering into OfflineDetailsHandler of getOfflineDetailsonCourseApplNo");
		if(transaction != null){
			return transaction.getOfflineDetailsonApplNoYear(applicationNo, year);
		}
		log.info("Leaving into OfflineDetailsHandler of getOfflineDetailsonCourseApplNo");
		return null;
	}
	
	public boolean getApplicationOfflineDetails(AdmissionFormForm admissionFormForm) throws DuplicateException,Exception{
		log.info("Entering into OfflineDetailsHandler of getApplicationOfflineDetails");
		OfflineDetails offlineDetails = new OfflineDetails();
		String applicationNo = admissionFormForm.getApplicationNumber();
		String year = admissionFormForm.getYear();
		if(applicationNo  != null && applicationNo.length() != 0 && year !=null && year.length() != 0) {
			
			IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
			boolean result =txn.checkApplicationNoUniqueForYear(Integer.parseInt(applicationNo),Integer.parseInt(year));
			if(!result) {
				throw new DuplicateException();
			}
			if(transaction != null){
				offlineDetails = transaction.getOfflineDetailsByNoYear(Integer.parseInt(applicationNo), Integer.parseInt(year));
			}
			if(offlineDetails != null) {
				admissionFormForm.setApplicationNumber(offlineDetails.getApplnNo().toString());
				admissionFormForm.setChallanNo(offlineDetails.getReceiptNo().toString());
				admissionFormForm.setApplicationAmount(offlineDetails.getAmount().toString());
				admissionFormForm.setCourseId(String.valueOf(offlineDetails.getCourse().getId()));
				admissionFormForm.setProgramId(String.valueOf(offlineDetails.getCourse().getProgram().getId()));
				admissionFormForm.setProgramTypeId(String.valueOf(offlineDetails.getCourse().getProgram().getProgramType().getId()));
				admissionFormForm.setApplicationDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(offlineDetails.getDate()), "dd-MMM-yyyy","dd/MM/yyyy"));
				return true;
			} else {
				return false;
			}
		}
		log.info("Leaving into OfflineDetailsHandler of getApplicationOfflineDetails");
	return false;
	}
	/**
	 * This method is used to check the offline application range for a course
	 */
	
	public boolean checkValidOfflineNumberForCourse(int courseId, int applicationNo, int year)throws Exception{
			ApplicationNumber applicationNumber;
		if(transaction!=null){			
			applicationNumber = transaction.checkValidOfflineNumberForCourse(applicationNo, year);
			if(applicationNumber!=null){
				Set<CourseApplicationNumber> courseApplicationNumbersSet = applicationNumber.getCourseApplicationNumbers();
				if(courseApplicationNumbersSet!=null && !courseApplicationNumbersSet.isEmpty()){
					CourseApplicationNumber courseApplicationNumber;
					Iterator<CourseApplicationNumber> iterator = courseApplicationNumbersSet.iterator();
					while (iterator.hasNext()) {
						courseApplicationNumber = iterator.next();
						if(courseApplicationNumber.getCourse()!=null && courseApplicationNumber.getCourse().getIsActive()==true){
							if(courseApplicationNumber.getCourse().getId()==courseId){
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}	
}
