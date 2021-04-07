package com.kp.cms.helpers.sap;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.sap.SapVenue;
import com.kp.cms.to.admin.EmployeeWorkLocationTO;
import com.kp.cms.to.sap.SapVenueTO;

public class SapVenueHelper {
	
	public static final Log log = LogFactory.getLog(SapVenueHelper.class);
	public static SapVenueHelper sapVenueHelper;
	
	public static SapVenueHelper getInstance(){
		if(sapVenueHelper == null){
			SapVenueHelper sapVenueHelper = new SapVenueHelper();
			return sapVenueHelper; 
		}
		return sapVenueHelper; 
	}
	
	/**
	 * copying BO values to TO
	 * @param locationTOList
	 * @return
	 */
	public List<EmployeeWorkLocationTO> copyLocationBosToTos(List<EmployeeWorkLocationBO> locationList) {
		log.debug("inside copyLocationBosToTos");
		List<EmployeeWorkLocationTO> locationTOList = new ArrayList<EmployeeWorkLocationTO>();
		Iterator<EmployeeWorkLocationBO> iterator = locationList.iterator();
		EmployeeWorkLocationBO employeeWorkLocationBO;
		EmployeeWorkLocationTO locationTO;
		
		while (iterator.hasNext()) {
			locationTO = new EmployeeWorkLocationTO();
			employeeWorkLocationBO = (EmployeeWorkLocationBO) iterator.next();
			locationTO.setId(employeeWorkLocationBO.getId());
			locationTO.setName(employeeWorkLocationBO.getName());
			
			locationTOList.add(locationTO);
		}
		log.debug("leaving copyLocationBosToTos");
		return locationTOList;
	}
	
	public List<SapVenueTO> pupulateSapVenueBOtoTO(List<SapVenue> venueList)throws Exception 
	{
		log.info("Entering into pupulateSapVenueBOtoTO of SapVenueHelper");
		SapVenueTO sapVenueTO = null;
		//HostelTO hostelTO = null;

		List<SapVenueTO> newVenueList = new ArrayList<SapVenueTO>();
		if (venueList != null && !venueList.isEmpty()) {
			Iterator<SapVenue> iterator = venueList.iterator();
			while (iterator.hasNext()) {
				SapVenue sapVenue = iterator.next();
				sapVenueTO = new SapVenueTO();
				sapVenueTO.setId(sapVenue.getId());
				sapVenueTO.setVenueName(sapVenue.getVenueName());
				sapVenueTO.setCapacity(sapVenue.getCapacity());
				sapVenueTO.setWorkLoc(sapVenue.getWorkLocationId().getName());
				//sapVenueTO.setWorkLocTO(EmployeeWorkLocationTO);
				newVenueList.add(sapVenueTO);
			}
		}
		log.info("Leaving from pupulateSapVenueBOtoTO of SapVenueHelper");
		return newVenueList;
	}
	

}
