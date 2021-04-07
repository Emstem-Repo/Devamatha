package com.kp.cms.helpers.employee;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EventLocation;
import com.kp.cms.bo.employee.BiometricDetails;
import com.kp.cms.bo.employee.EventLoactionBiometricDetailsBo;
import com.kp.cms.forms.employee.BiometricDetailsForm;
import com.kp.cms.forms.employee.EventLocationBiometricDetailsForm;
import com.kp.cms.handlers.employee.EventLoactionBiometricDetailsHandler;
import com.kp.cms.to.employee.BiometricDetailsTO;
import com.kp.cms.to.employee.EventLoactionBiometricDetailsTo;

public class EventLoactionBiometricDetailsHelper {

	private static final Log log = LogFactory.getLog(EventLoactionBiometricDetailsHelper.class);
	public static volatile EventLoactionBiometricDetailsHelper biometric=null;
	/**
	 * @return
	 * This method gives instance of this method
	 */
	public static EventLoactionBiometricDetailsHelper getInstance(){
		if(biometric==null){
			biometric= new EventLoactionBiometricDetailsHelper();}
		return biometric;
	}

	/**
	 * @param eventLocationBiometricDetailsForm
	 * @return
	 */
	public EventLoactionBiometricDetailsBo convertFormTOBO(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm){
		EventLoactionBiometricDetailsBo biometric=new EventLoactionBiometricDetailsBo();
		biometric.setMachineId(eventLocationBiometricDetailsForm.getMachineId());
		biometric.setMachineIp(eventLocationBiometricDetailsForm.getMachineIp());
		biometric.setWorkLocation(eventLocationBiometricDetailsForm.getWorklocation());
		if(eventLocationBiometricDetailsForm.getMachineName()!=null && !eventLocationBiometricDetailsForm.getMachineName().isEmpty())
		biometric.setMachineName(eventLocationBiometricDetailsForm.getMachineName());
		EventLocation eventLocation=new EventLocation();
		eventLocation.setId(Integer.parseInt(eventLocationBiometricDetailsForm.getEventLocation()));
		biometric.setEventLocation(eventLocation);
		biometric.setCreatedBy(eventLocationBiometricDetailsForm.getUserId());
		biometric.setModifiedBy(eventLocationBiometricDetailsForm.getUserId());
		biometric.setCreatedDate(new Date());
		biometric.setLastModifiedDate(new Date());
		biometric.setIsActive(true);
		return biometric;
	}
	
	/**
	 * @param biometric
	 * @return
	 */
	public List<EventLoactionBiometricDetailsTo> convertBosToTOs(List<EventLoactionBiometricDetailsBo> biometric){
    	List<EventLoactionBiometricDetailsTo> biometricTOs=new ArrayList<EventLoactionBiometricDetailsTo>();
		Iterator itr=biometric.iterator();
		while(itr.hasNext()){
			EventLoactionBiometricDetailsBo biometricBo=(EventLoactionBiometricDetailsBo)itr.next();
			EventLoactionBiometricDetailsTo biometricTo=new EventLoactionBiometricDetailsTo();
			biometricTo.setId(biometricBo.getId());
			biometricTo.setMachineId(biometricBo.getMachineId());
			biometricTo.setMachineIp(biometricBo.getMachineIp());
			if(biometricBo.getMachineName()!=null)
			biometricTo.setMachinName(biometricBo.getMachineName());
			biometricTo.setEventLocation(biometricBo.getEventLocation().getName());
			if(biometricBo.getWorkLocation()!=null && !biometricBo.getWorkLocation().isEmpty())
			biometricTo.setWorkLocation(biometricBo.getWorkLocation());
			biometricTOs.add(biometricTo);
		}
		return biometricTOs;
	}
	
	/**
	 * @param biometricDetailsForm
	 * @param biometric
	 */
	public void setBotoForm(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm,EventLoactionBiometricDetailsBo biometric){
    	if(biometric!=null){
    		eventLocationBiometricDetailsForm.setMachineId(biometric.getMachineId());
    		eventLocationBiometricDetailsForm.setOrigMachineId(biometric.getMachineId());
    		eventLocationBiometricDetailsForm.setMachineIp(biometric.getMachineIp());
    		eventLocationBiometricDetailsForm.setOrigMachineIp(biometric.getMachineIp());
    		if(biometric.getMachineName()!=null && !biometric.getMachineName().isEmpty()){
    			eventLocationBiometricDetailsForm.setMachineName(biometric.getMachineName());
    			eventLocationBiometricDetailsForm.setOrigMachineName(biometric.getMachineName());
    		}
    		eventLocationBiometricDetailsForm.setEventLocation(String.valueOf(biometric.getEventLocation().getId()));
    		eventLocationBiometricDetailsForm.setOrigEventLocation(String.valueOf(biometric.getEventLocation().getId()));
    		eventLocationBiometricDetailsForm.setWorklocation(biometric.getWorkLocation());
    		eventLocationBiometricDetailsForm.setOrgWorklocation(biometric.getWorkLocation());
    	}
    }
	public EventLoactionBiometricDetailsBo convertFormToBo(EventLocationBiometricDetailsForm eventLocationBiometricDetailsForm){
		EventLoactionBiometricDetailsBo biometric=new EventLoactionBiometricDetailsBo();
		biometric.setId(eventLocationBiometricDetailsForm.getId());
		biometric.setMachineId(eventLocationBiometricDetailsForm.getMachineId());
		biometric.setMachineIp(eventLocationBiometricDetailsForm.getMachineIp());
		biometric.setWorkLocation(eventLocationBiometricDetailsForm.getWorklocation());
		if(eventLocationBiometricDetailsForm.getMachineName()!=null && !eventLocationBiometricDetailsForm.getMachineName().isEmpty())
		biometric.setMachineName(eventLocationBiometricDetailsForm.getMachineName());
		biometric.setIsActive(true);
		biometric.setModifiedBy(eventLocationBiometricDetailsForm.getUserId());
		biometric.setLastModifiedDate(new Date());
		EventLocation eventLocation=new EventLocation();
		eventLocation.setId(Integer.parseInt(eventLocationBiometricDetailsForm.getEventLocation()));
		biometric.setEventLocation(eventLocation);
    	return biometric;
    }
}
