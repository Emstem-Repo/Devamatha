package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Services;
import com.kp.cms.bo.admin.ServicesDownTracker;
import com.kp.cms.forms.admin.ServicesDownTrackerForm;
import com.kp.cms.helpers.admin.ServicesDownTrackerHelper;
import com.kp.cms.to.admin.ServicesDownTrackerTO;
import com.kp.cms.transactions.admin.IServicesDownTrackerTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactionsimpl.admin.ServicesDownTrackerTransactionImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ServicesDownTrackerHandler {
	
	public static volatile ServicesDownTrackerHandler servicesDownTrackerHandler = null;
	   private static final Log log = LogFactory.getLog(ServicesDownTrackerHandler.class);
	   
	   public static ServicesDownTrackerHandler getInstance() {
		      if(servicesDownTrackerHandler == null) {
		    	  servicesDownTrackerHandler = new ServicesDownTrackerHandler();
		    	  return servicesDownTrackerHandler;
		      }
		      return servicesDownTrackerHandler;
	   }
	   
	   IServicesDownTrackerTransaction iServicesDownTrackerTransaction = ServicesDownTrackerTransactionImpl.getInstance();
	   
	 /**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getServicesMap() throws Exception {
		   ISingleFieldMasterTransaction iSingleFieldMasterTransaction = SingleFieldMasterTransactionImpl.getInstance();
		   List<Services> servicesList = iSingleFieldMasterTransaction.getServices();
		   Map<Integer, String> servicesMap = new LinkedHashMap<Integer, String>();
		   Iterator<Services> i = servicesList.iterator();
		   Services services;
		    while(i.hasNext()) {
		    	services = (Services)i.next();
		    	servicesMap.put(services.getId(), services.getName());
			}
	   return servicesMap;
      }

	/**
	 * @param servicesId
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public List<ServicesDownTrackerTO> getTrackerList(String servicesId, String mode) throws Exception {
		List<ServicesDownTracker> trackerList = iServicesDownTrackerTransaction.getTrackerList(servicesId, mode);
		if(trackerList!=null && !trackerList.isEmpty()){
			return ServicesDownTrackerHelper.getInstance().pupulateServicesBOtoTO(trackerList);
		}
		return new ArrayList<ServicesDownTrackerTO>();
		
	}

	/**
	 * @param trackerForm
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck(ServicesDownTrackerForm trackerForm) throws Exception {
		return iServicesDownTrackerTransaction.duplicateCheck(trackerForm);
	}

	/**
	 * @param trackerForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean addOrUpdateServicesDownTracker(ServicesDownTrackerForm trackerForm, String mode) throws Exception {
		return iServicesDownTrackerTransaction.addOrUpdateServicesDownTracker(ServicesDownTrackerHelper.getInstance().convertFormToBo(trackerForm,mode),mode);
	}

	/**
	 * @param trackerForm
	 * @throws Exception
	 */
	public void editServicesDownTracker(ServicesDownTrackerForm trackerForm) throws Exception {
		ServicesDownTracker tracker = iServicesDownTrackerTransaction.getServicesDownTrackerById(trackerForm.getId());
        if(tracker!=null){
        	trackerForm.setServicesId(String.valueOf(tracker.getServiceId().getId()));
        	trackerForm.setDate(CommonUtil.formatDates(tracker.getDate()));
        	trackerForm.setDownFrom(tracker.getDownFrom());
        	trackerForm.setDownTill(tracker.getDownTill());
        	trackerForm.setRemarks(tracker.getRemarks());
        }
	}

	/**
	 * @param trackerForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean deleteServicesDownTracker(ServicesDownTrackerForm trackerForm, String mode) throws Exception {
		ServicesDownTracker tracker = iServicesDownTrackerTransaction.getServicesDownTrackerById(trackerForm.getId());
		  boolean isDeleted=false;
		  if(tracker!=null){
			  tracker.setIsActive(false);
			  isDeleted = iServicesDownTrackerTransaction.addOrUpdateServicesDownTracker(tracker, mode);
		  }
		return isDeleted;
	}

	

}
