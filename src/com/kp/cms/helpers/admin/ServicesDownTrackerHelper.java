package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Services;
import com.kp.cms.bo.admin.ServicesDownTracker;
import com.kp.cms.forms.admin.ServicesDownTrackerForm;
import com.kp.cms.to.admin.ServicesDownTrackerTO;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ServicesDownTrackerHelper {
	
	private static final Log log = LogFactory.getLog(ServicesDownTrackerHelper.class);
	private static volatile ServicesDownTrackerHelper servicesDownTrackerHelper = null;

	private ServicesDownTrackerHelper() {
	}
	
	public static ServicesDownTrackerHelper getInstance() {

		if (servicesDownTrackerHelper == null) {
			servicesDownTrackerHelper = new ServicesDownTrackerHelper();
		}
		return servicesDownTrackerHelper;
	}

	/**
	 * @param trackerList
	 * @return
	 */
	public List<ServicesDownTrackerTO> pupulateServicesBOtoTO(List<ServicesDownTracker> trackerList) {
		
		ServicesDownTrackerTO servicesDownTrackerTO = null;

		List<ServicesDownTrackerTO> newTrackerList = new ArrayList<ServicesDownTrackerTO>();
		if (trackerList != null && !trackerList.isEmpty()) {
			Iterator<ServicesDownTracker> iterator = trackerList.iterator();
			while (iterator.hasNext()) {
				ServicesDownTracker servicesDownTracker = iterator.next();
				servicesDownTrackerTO = new ServicesDownTrackerTO();
				if (servicesDownTracker.getId() != 0) {
					servicesDownTrackerTO.setId(servicesDownTracker.getId());
					servicesDownTrackerTO.setDate(CommonUtil.formatDates(servicesDownTracker.getDate()));
					servicesDownTrackerTO.setDownFrom(servicesDownTracker.getDownFrom());
					servicesDownTrackerTO.setDownTill(servicesDownTracker.getDownTill());
					servicesDownTrackerTO.setRemarks(servicesDownTracker.getRemarks());
					if (servicesDownTracker.getServiceId() != null)
						servicesDownTrackerTO.setServiceName(servicesDownTracker.getServiceId().getName());
					
					newTrackerList.add(servicesDownTrackerTO);
				}
			}
		}
		return newTrackerList;
	}
	

	/**
	 * @param trackerForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public ServicesDownTracker convertFormToBo(ServicesDownTrackerForm trackerForm,String mode)throws Exception {
		
		ServicesDownTracker servicesDownTracker = new ServicesDownTracker();
		
		Services services = new Services();
		services.setId(Integer.parseInt(trackerForm.getServicesId()));
		servicesDownTracker.setServiceId(services);
		servicesDownTracker.setDate(CommonUtil.ConvertStringToDate(trackerForm.getDate()));
		servicesDownTracker.setDownFrom(trackerForm.getDownFrom());
		servicesDownTracker.setDownTill(trackerForm.getDownTill());
		if(trackerForm.getRemarks()!=null && !trackerForm.getRemarks().isEmpty())
			servicesDownTracker.setRemarks(trackerForm.getRemarks());
		
		if(mode.equalsIgnoreCase("Add")){
			servicesDownTracker.setCreatedBy(trackerForm.getUserId());
			servicesDownTracker.setCreatedDate(new Date());
			servicesDownTracker.setModifiedBy(trackerForm.getUserId());
			servicesDownTracker.setLastModifiedDate(new Date());
		}else if(mode.equalsIgnoreCase("Update")){
			servicesDownTracker.setId(trackerForm.getId());
			servicesDownTracker.setModifiedBy(trackerForm.getUserId());
			servicesDownTracker.setLastModifiedDate(new Date());
	      }
		servicesDownTracker.setIsActive(true);
		return servicesDownTracker;
			
	}

}
