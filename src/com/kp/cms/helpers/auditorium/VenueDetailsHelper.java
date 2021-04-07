package com.kp.cms.helpers.auditorium;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.forms.auditorium.VenueDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.to.auditorium.VenueDetailsTO;

public class VenueDetailsHelper {
	public static volatile VenueDetailsHelper venueDetailsHelper = null;
    private static Log log = LogFactory.getLog(VenueDetailsHelper.class);
    public static VenueDetailsHelper getInstance() {
		if (venueDetailsHelper == null) {
			venueDetailsHelper = new VenueDetailsHelper();
			return venueDetailsHelper;
		}
		return venueDetailsHelper;
	}
    public VenueDetails convertFormTOBO(VenueDetailsForm venueDetailsForm,String mode){
    	VenueDetails venueDetails=new VenueDetails();
    	venueDetails.setVenueName(venueDetailsForm.getVenueName());
		venueDetails.setRoomNo(venueDetailsForm.getRoomNo());
		BlockDetails block = new BlockDetails();
		block.setId(Integer.parseInt(venueDetailsForm.getBlockId()));
		venueDetails.setBlock(block);
		venueDetails.setIsActive(true);
		venueDetails.setEmailId(venueDetailsForm.getEmailId());
		if(venueDetailsForm.getFacilityAvailable()!=null && !venueDetailsForm.getFacilityAvailable().isEmpty()){
			venueDetails.setFacilityAvailable(venueDetailsForm.getFacilityAvailable());		
		}
    	if(mode.equalsIgnoreCase("Add")){
    		venueDetails.setCreatedBy(venueDetailsForm.getUserId());
    		venueDetails.setCreatedDate(new Date());
    	}else if(mode.equalsIgnoreCase("Edit")){
    		venueDetails.setId(venueDetailsForm.getId());
    		venueDetails.setModifiedBy(venueDetailsForm.getUserId());
    		venueDetails.setLastModifiedDate(new Date());
    	}
		
	return venueDetails;
	}
    public void setBotoForm(VenueDetailsForm venueDetailsForm,VenueDetails venue){
    	if(venue!=null){
    		venueDetailsForm.setVenueName(venue.getVenueName());
    		venueDetailsForm.setRoomNo(venue.getRoomNo());
    		venueDetailsForm.setBlockId(String.valueOf(venue.getBlock().getId()));
    		venueDetailsForm.setOrigBlockId(String.valueOf(venue.getBlock().getId()));
    		venueDetailsForm.setOrigRoomNo(venue.getRoomNo());
    		venueDetailsForm.setOrigVenueName(venue.getVenueName());
    		venueDetailsForm.setEmailId(venue.getEmailId());
    		venueDetailsForm.setOrigEmailId(venue.getEmailId());
    		venueDetailsForm.setFacilityAvailable(venue.getFacilityAvailable());
    		venueDetailsForm.setOrigFacilityAvailable(venue.getFacilityAvailable());
    	}
    }
    public List<VenueDetailsTO> convertBoToTO(List<VenueDetails> venues){
    	List<VenueDetailsTO> venuesTO = new ArrayList<VenueDetailsTO>();
    	if(venues!=null){
    		Iterator<VenueDetails> itr = venues.iterator();
    		while(itr.hasNext()){
    			VenueDetails venue = itr.next();
    			VenueDetailsTO venueTO = new VenueDetailsTO();
    			venueTO.setId(venue.getId());
    			if(venue.getBlock()!=null){
    				venueTO.setBlockName(venue.getBlock().getBlockName());
    			}
    			if(venue.getVenueName()!=null)
    				venueTO.setVenueName(venue.getVenueName());
    			if(venue.getRoomNo()!=null)
    				venueTO.setRoomNo(venue.getRoomNo());
    			venuesTO.add(venueTO);
    		}
    	}
    	return venuesTO;
    }
}
