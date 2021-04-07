package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.fee.FeePaidForm;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.utilities.CommonUtil;

public class FeePaidHelper {
	private static FeePaidHelper feePaidHelper= null;
	private static final Log log = LogFactory.getLog(FeePaidHelper.class);
	public static FeePaidHelper getInstance() {
		if(feePaidHelper == null) {
			feePaidHelper = new FeePaidHelper();
		    return feePaidHelper;
		}
	return feePaidHelper;
	}
		
	/**
	 * 
	 * @param feePaymentList
	 * @return
	 */
	public List<FeePaymentTO> copyBotoTo(List<FeePayment> feePaymentList) {
		log.debug("Helper : Entering copyBotoTo");
		List<FeePaymentTO> feePaymentToList = new ArrayList<FeePaymentTO>();
		FeePayment feePayment;
		Iterator<FeePayment> itr = feePaymentList.iterator();
		FeePaymentTO feePaymentTO;
		StringBuffer studentName = new StringBuffer();
		
		while(itr.hasNext()) {
			feePaymentTO = new FeePaymentTO();
			feePayment = itr.next();
			
			if(feePayment.getStudent().getRegisterNo() != null && feePayment.getStudent().getRegisterNo().length() != 0) {
				feePaymentTO.setRegistrationNo(feePayment.getStudent().getRegisterNo());
			} else  {	
				feePaymentTO.setRegistrationNo(String.valueOf(feePayment.getStudent().getAdmAppln().getApplnNo()));
			}	
			studentName = new StringBuffer("");
			if(feePayment.getStudent()!= null){
				studentName.append(feePayment.getStudent().getAdmAppln().getPersonalData().getFirstName());
				if(feePayment.getStudent().getAdmAppln().getPersonalData().getMiddleName()!= null && !feePayment.getStudent().getAdmAppln().getPersonalData().getMiddleName().trim().isEmpty()){
					studentName.append(" ");
					studentName.append(feePayment.getStudent().getAdmAppln().getPersonalData().getMiddleName());
				}
				if(feePayment.getStudent().getAdmAppln().getPersonalData().getLastName()!= null && !feePayment.getStudent().getAdmAppln().getPersonalData().getLastName().trim().isEmpty()){
					studentName.append(" ");
					studentName.append(feePayment.getStudent().getAdmAppln().getPersonalData().getLastName());
				}
			}
			
			feePaymentTO.setStudentName(studentName.toString());			
			
			feePaymentTO.setBillNo(feePayment.getBillNo());
			feePaymentTO.setId(feePayment.getId());
			feePaymentTO.setChallenPrintedDate(CommonUtil.getStringDate(feePayment.getChallenPrintedDate()));
			feePaymentTO.setTotalFeePaid(feePayment.getTotalFeePaid());
			feePaymentTO.setIsFeePaid(feePayment.getIsFeePaid());
			feePaymentTO.setPaymentVerified(feePayment.getPaymentVerified());
			feePaymentTO.setIsChallanCanceled(feePayment.getIsCancelChallan());
			StringBuffer className = new StringBuffer("");
			if(feePayment.getStudent()!= null && feePayment.getStudent().getClassSchemewise()!= null && feePayment.getStudent().getClassSchemewise().getClasses()!= null
					&& feePayment.getStudent().getClassSchemewise().getClasses().getName()!= null){
//				className.append(feePayment.getStudent().getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo());
				className.append(feePayment.getStudent().getClassSchemewise().getClasses().getName().trim());
//				className.append(feePayment.getStudent().getClassSchemewise().getClasses().getSectionName());
				
			}
			if(className == null || className.toString().isEmpty()){
				className.append(feePayment.getCourse().getCode());
			}
			feePaymentTO.setClassName(className.toString());
			if(feePayment.getCurrency()!= null && feePayment.getCurrency().getCurrencyCode()!= null){
				feePaymentTO.setCurrencyCode(feePayment.getCurrency().getCurrencyCode());
			}
			feePaymentToList.add(feePaymentTO);
		}
		log.debug("Helper : Leaving copyBotoTo");
	return feePaymentToList;
	}
		
	/**
	 * 
	 * @param feePaidForm
	 * @return
	 * @throws Exception
	 */
	public Set<FeePaymentTO>/*Set<Integer>*/ getMarkAsPaidIds(FeePaidForm feePaidForm) throws Exception {
		log.debug("Helper : Entering getMarkAsPaidIds");		
		List<FeePaymentTO> list = feePaidForm.getFeePaymentList();
		Iterator<FeePaymentTO> itr = list.iterator();
		Set<FeePaymentTO> feePaymentSet = new HashSet<FeePaymentTO>();
		
//		Set<Integer> ids = new HashSet<Integer>();
		FeePaymentTO feePaymentTO;
		while(itr.hasNext()) {
			feePaymentTO = itr.next();
			if(feePaymentTO.getConversionRate()!=null)
			if(!CommonUtil.isValidDecimal(feePaymentTO.getConversionRate())){
				throw new BusinessException("knowledgepro.fee.is.not.decimal.number");
			}
			if(feePaymentTO.getPaymentVerified() == true) {
				feePaymentSet.add(feePaymentTO);
//				ids.add(feePaymentTO.getId());
			}
		}
		log.debug("Helper : Leaving getMarkAsPaidIds");	
	return feePaymentSet/*ids*/;
	}
	
	/**
	 * 
	 * @param feePaidForm
	 * @return
	 * @throws Exception
	 */
	public List<FeePaymentTO> getMarkAsCancelIds(FeePaidForm feePaidForm) throws Exception {
		log.debug("Helper : Entering getMarkAsPaidIds");		
		List<FeePaymentTO> list = feePaidForm.getFeePaymentList();
		List<FeePaymentTO> list1=new ArrayList<FeePaymentTO>();
		Iterator<FeePaymentTO> itr = list.iterator();	
		//Set<Integer> ids = new HashSet<Integer>();
		FeePaymentTO feePaymentTO;
		while(itr.hasNext()) {
			feePaymentTO = itr.next();
			FeePaymentTO feePaymentTO1=new FeePaymentTO();
			if(feePaymentTO.getIsChallanCanceled()) {
				feePaymentTO1.setId(feePaymentTO.getId());
				feePaymentTO1.setCancelReason(feePaymentTO.getCancelReason());
				list1.add(feePaymentTO1);
				//ids.add(feePaymentTO.getId());
			}
		}
		log.debug("Helper : Leaving getMarkAsPaidIds");	
	return list1;
	}
}
