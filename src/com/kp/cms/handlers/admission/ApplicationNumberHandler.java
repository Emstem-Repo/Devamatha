package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplicationNumber;
import com.kp.cms.bo.admin.CourseApplicationNumber;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.ApplicationNumberForm;
import com.kp.cms.helpers.admission.ApplicationNumberHelper;
import com.kp.cms.to.admission.ApplicationNumberTO;
import com.kp.cms.transactions.admission.IApplicationNumberTransaction;
import com.kp.cms.transactionsimpl.admission.ApplicationNumberTransactionImpl;

public class ApplicationNumberHandler {
	private static final Log log = LogFactory.getLog(ApplicationNumberHandler.class);
	private static volatile ApplicationNumberHandler applicationNumberHandler = null;

	public static ApplicationNumberHandler getInstance() {
		if (applicationNumberHandler == null) {
			applicationNumberHandler = new ApplicationNumberHandler();
		}
		return applicationNumberHandler;
	}
	/**
	 * 
	 * @param applicationNumberForm
	 * @param mode
	 * @return true/false
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public boolean addApplicationNumber(ApplicationNumberForm applicationNumberForm, String mode)
										throws DuplicateException, Exception {
		log.debug("inside addApplicationNumber in Handler");
		IApplicationNumberTransaction iappApplicationNumberTransaction = ApplicationNumberTransactionImpl.getInstance();
		boolean isAdded = false;
		ApplicationNumber applicationNumber;
		Boolean isCourseDuplicated = false;
		
		int offLineStartInForm = 0;
		int offLineEndInForm = 0;
		if(applicationNumberForm.getOfflineAppNoFrom() != null && !applicationNumberForm.getOfflineAppNoFrom().trim().isEmpty()){
			offLineStartInForm = Integer.parseInt(applicationNumberForm.getOfflineAppNoFrom());
		}
		if(applicationNumberForm.getOfflineAppNoTill() != null && !applicationNumberForm.getOfflineAppNoTill().trim().isEmpty()){
			offLineEndInForm = Integer.parseInt(applicationNumberForm.getOfflineAppNoTill());
		}
		
		boolean originalNotChanged = false;
		boolean origYearNotChanged = false;
		if(mode.equalsIgnoreCase("Edit")){
			int tempOrgYear = applicationNumberForm.getOrigYear();
			int tempYear = Integer.parseInt(applicationNumberForm.getYear());
			
			int orgOnFrom = Integer.parseInt(applicationNumberForm.getOrigOnlineAppNoFrom());
			int orgOnTo = Integer.parseInt(applicationNumberForm.getOrigOnlineAppNoTill());
			
			int orgOffFrom = 0;
			if(applicationNumberForm.getOrigOfflineAppNoFrom() != null && !applicationNumberForm.getOrigOfflineAppNoFrom().trim().isEmpty()){
				orgOffFrom = Integer.parseInt(applicationNumberForm.getOrigOfflineAppNoFrom());
			}
			
			int orgOffTo = 0;
			if(applicationNumberForm.getOrigOfflineAppNoTill()!= null && !applicationNumberForm.getOrigOfflineAppNoTill().trim().isEmpty()){
				orgOffTo =  Integer.parseInt(applicationNumberForm.getOrigOfflineAppNoTill());
			}
			
			int OnFrom = Integer.parseInt(applicationNumberForm.getOnlineAppNoFrom());
			int OnTo = Integer.parseInt(applicationNumberForm.getOnlineAppNoTill());
			int OffFrom = offLineStartInForm;
			int OffTo =  offLineEndInForm;
			
			if(tempYear == tempOrgYear){
				origYearNotChanged = true;
			}
			if((orgOnFrom == OnFrom) && (orgOnTo == OnTo) && (orgOffFrom == OffFrom ) && (orgOffTo == OffTo) && (tempYear == tempOrgYear )){
				originalNotChanged = true;
			}
		}
		
		if(mode.equalsIgnoreCase("Add")){
			originalNotChanged = false;
		}
		
		if(mode.equalsIgnoreCase("Add")){
			if(iappApplicationNumberTransaction.isApplNoDuplcated(Integer.parseInt(applicationNumberForm.getYear()), 
					Integer.parseInt(applicationNumberForm.getOnlineAppNoFrom()),
					Integer.parseInt(applicationNumberForm.getOnlineAppNoTill()), 
					offLineStartInForm, offLineEndInForm, applicationNumberForm.getId())){
				throw new DuplicateException();
			}

			for (int x = 0; x < applicationNumberForm.getSelectedCourse().length; x++){
				isCourseDuplicated = iappApplicationNumberTransaction.checkIsCourseDuplicated(Integer.parseInt(applicationNumberForm.getSelectedCourse()[x]), Integer.parseInt(applicationNumberForm.getYear()));
				if(isCourseDuplicated){
					throw new DuplicateException();
				}
			}			
			
			applicationNumber = ApplicationNumberHelper.getInstance().populateApplicationNumber(applicationNumberForm,mode);
		}
		else {
			if(!originalNotChanged){
				if(iappApplicationNumberTransaction.isApplNoDuplcated(Integer.parseInt(applicationNumberForm.getYear()), 
						Integer.parseInt(applicationNumberForm.getOnlineAppNoFrom()),
						Integer.parseInt(applicationNumberForm.getOnlineAppNoTill()), 
						offLineStartInForm, 
						offLineEndInForm, applicationNumberForm.getId())){
					throw new DuplicateException();
				}
			}
			List<Integer> duplCheckList = ApplicationNumberHelper.getInstance().assignDuplicationCheckItemsToList(applicationNumberForm, origYearNotChanged);
			for (int x = 0; x < duplCheckList.size(); x++){
				isCourseDuplicated = iappApplicationNumberTransaction.checkIsCourseDuplicated(duplCheckList.get(x), Integer.parseInt(applicationNumberForm.getYear()));
				if(isCourseDuplicated){
					throw new DuplicateException();
				}
			}
			applicationNumber = ApplicationNumberHelper.getInstance().populateApplicationNumberinUpdate(applicationNumberForm,mode);
		}
				
		
		isAdded = iappApplicationNumberTransaction.addApplicaionNumber(applicationNumber, mode);
		log.debug("leaving addApplicationNumber in Handler");
		return isAdded;
	}

	/**
	 * 
	 * @param id
	 * @param applicationNumberForm
	 * @return applicationNoToList. using to display th list in the UI
	 * @throws Exception
	 */
	public List<ApplicationNumberTO> getApplicationNumber(int id,ApplicationNumberForm applicationNumberForm,int currentYear) throws Exception {
		log.debug("inside getApplicationNumber");
		IApplicationNumberTransaction iapApplicationNumberTransaction = new ApplicationNumberTransactionImpl();
		List<ApplicationNumber> applicationNoList = iapApplicationNumberTransaction.getApplicationNumber(id,currentYear);
		List<ApplicationNumberTO> applicationNoToList = ApplicationNumberHelper.getInstance().copyApplicationNoBosToTos(applicationNoList, applicationNumberForm, id);
		log.debug("leaving getApplicationNumber");
		return applicationNoToList;
	}

	/**
	 * 
	 * @param id
	 * @return true/false
	 * @throws Exception
	 */
	public boolean deleteApplicationNo(int id,Boolean activate,ApplicationNumberForm applicationNumberForm) throws Exception {
		log.debug("inside deleteApplicationNo");
		IApplicationNumberTransaction iApplicationNumberTransaction = ApplicationNumberTransactionImpl.getInstance();

		List<CourseApplicationNumber> courseAppList = iApplicationNumberTransaction.getCourseApplicationNumber(id); 
		iApplicationNumberTransaction.deleteCourseApplicationNumber(courseAppList);
		
//		ApplicationNumber applicationNumber = new ApplicationNumber();
//		applicationNumber.setId(id);
		boolean result = iApplicationNumberTransaction.deleteApplicationNumber(id,activate,applicationNumberForm);
		log.debug("inside deleteApplicationNo");
		return result;
	}
}
