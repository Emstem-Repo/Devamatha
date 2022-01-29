package com.kp.cms.handlers.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.helpers.employee.BiometricDetailsHelper;
import com.kp.cms.helpers.employee.EventLoactionBiometricDetailsHelper;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;
import com.kp.cms.transactions.employee.IBiometricDetailsTxn;
import com.kp.cms.transactions.employee.IEventLoactionBiometricDetailsTransaction;
import com.kp.cms.transactionsimpl.employee.BiometricDetailsTxnImpl;
import com.kp.cms.transactionsimpl.employee.EventLoactionBiometricDetailsTransactionImpl;

public class EventLoactionBiometricDetailsHandler {
	private static final Log log = LogFactory.getLog(EventLoactionBiometricDetailsHandler.class);
	public static volatile EventLoactionBiometricDetailsHandler biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EventLoactionBiometricDetailsHandler getInstance(){
		if(biometric==null){
			biometric= new EventLoactionBiometricDetailsHandler();}
		return biometric;
	}
	/**
	 * checking for duplicate entry
	 * @param eventLocationBiometricDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		boolean duplicate=transaction.duplicateCheck(eventLocationBiometricDetailsForm.getMachineId(),eventLocationBiometricDetailsForm.getMachineIp(),eventLocationBiometricDetailsForm.getMachineName(),eventLocationBiometricDetailsForm );
		return duplicate;
	}
	/**
	 * add location details
	 * @param eventLocationBiometricDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean addBimetricDetails(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		EventLoactionBiometricDetailsBo biometric=EventLoactionBiometricDetailsHelper.getInstance().convertFormTOBO(eventLocationBiometricDetailsForm);
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		boolean isAdded=transaction.addBiometricDetails(biometric);
		return isAdded;
	}
	/**
	 * get event location details
	 * @return
	 * @throws Exception
	 */
	public List<EventLoactionBiometricDetailsTo> getBiometricList()throws Exception{
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		List<EventLoactionBiometricDetailsBo> biometric=transaction.getBiometricDetails();
		List<EventLoactionBiometricDetailsTo> biometricTO=EventLoactionBiometricDetailsHelper.getInstance().convertBosToTOs(biometric);
		return biometricTO;
	}
	/**
	 * edit event Loaction details
	 * @param eventLocationBiometricDetailsForm
	 * @throws Exception
	 */
	public void editBiometricDetails(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		EventLoactionBiometricDetailsBo biometric=transaction.getBiometricDetailsById(eventLocationBiometricDetailsForm.getId());
		EventLoactionBiometricDetailsHelper.getInstance().setBotoForm(eventLocationBiometricDetailsForm, biometric);
	}
	/**
	 * update event location details
	 * @param eventLocationBiometricDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateBiometricDetails(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		EventLoactionBiometricDetailsBo biometric=EventLoactionBiometricDetailsHelper.getInstance().convertFormToBo(eventLocationBiometricDetailsForm);
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		boolean isUpdated=transaction.updateBiometricDetails(biometric);
		return isUpdated;
	}
	/**
	 * delete event location details
	 * @param eventLocationBiometricDetailsForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteBiometricDetails(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
			boolean isDeleted=transaction.deleteBiometricDetails(eventLocationBiometricDetailsForm.getId());
				return isDeleted;
			}
	
	public void getEventLocationData(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm) throws Exception{
				IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
				Map<String,String> eventLocationMap=transaction.getEventLocationData();
				if(eventLocationMap !=null){
					eventLocationBiometricDetailsForm.setEventLocationMap(eventLocationMap);
				}
		}
	
	public boolean duplicateCheckInUpdateMode(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm)throws Exception{
		IEventLoactionBiometricDetailsTransaction transaction=new EventLoactionBiometricDetailsTransactionImpl();
		boolean duplicate=transaction.duplicateCheckInUpdateMode(eventLocationBiometricDetailsForm.getMachineId(),eventLocationBiometricDetailsForm.getMachineIp(),eventLocationBiometricDetailsForm.getMachineName(),eventLocationBiometricDetailsForm );
		return duplicate;
	}
	
}
