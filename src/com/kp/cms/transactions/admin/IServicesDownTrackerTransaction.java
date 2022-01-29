package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.ServicesDownTracker;
import com.kp.cms.forms.admin.ServicesDownTrackerForm;

/**
 * @author dIlIp
 *
 */
public interface IServicesDownTrackerTransaction {

	List<ServicesDownTracker> getTrackerList(String servicesId, String mode) throws Exception;
	
	public boolean duplicateCheck(ServicesDownTrackerForm trackerForm)throws Exception;

	boolean addOrUpdateServicesDownTracker(ServicesDownTracker convertFormToBo,	String mode) throws Exception;

	public ServicesDownTracker getServicesDownTrackerById(int id)throws Exception;

}
