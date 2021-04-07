package com.kp.cms.handlers.auditorium;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.kp.cms.bo.auditorium.AuditoriumBooking;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.forms.auditorium.AuditoriumBookingForm;
import com.kp.cms.helpers.auditorium.AuditoriumBookingHelper;
import com.kp.cms.to.auditorium.AuditoriumBookingTo;
import com.kp.cms.to.auditorium.BlockDetailsTO;
import com.kp.cms.to.auditorium.VenueDetailsTO;
import com.kp.cms.transactions.auditorium.IAuditoriumBookingTransaction;
import com.kp.cms.transactionsimpl.auditorium.AuditoriumBookingTxnImpl;


public class AuditoriumBookingHandler {
	
	public IAuditoriumBookingTransaction transaction = AuditoriumBookingTxnImpl.getInstance();
	public static volatile AuditoriumBookingHandler auditoriumHandler = null;
	public static AuditoriumBookingHandler getInstance() {
		if (auditoriumHandler == null) {
			auditoriumHandler = new AuditoriumBookingHandler();
		}
		return auditoriumHandler;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	public List<BlockDetailsTO> getBlockDetails()throws Exception{
		List<BlockDetails> blockList = transaction.getBlockDetails();
		List<BlockDetailsTO> blockToList = AuditoriumBookingHelper.getInstance().convertBoToToBlockList(blockList);
		return blockToList;
	}
	/**
	 * @return
	 * @throws Exception
	 */
	/**
	 * @return
	 * @throws Exception
	 */
	public List<VenueDetailsTO> getVenueDetails()throws Exception{
		List<VenueDetails> venueList = transaction.getVenueDetails();
		List<VenueDetailsTO> venueToList = AuditoriumBookingHelper.getInstance().convertBoToToVenueList(venueList);
		return venueToList;
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getBlockDetailsMap()throws Exception{
		List<BlockDetails> blockList = transaction.getBlockDetails();
		Map<Integer,String> blockMap = AuditoriumBookingHelper.getInstance().setBlockMap(blockList);
		return blockMap;
	}
	/**
	 * @param blockId
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getVenuesByBlock(int blockId)throws Exception{
		return transaction.getVenuesByBlockDetails(blockId);
	}
	/**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveCalendarEvents(AuditoriumBookingForm auditoriumBookingForm)throws Exception{
		AuditoriumBooking booking = AuditoriumBookingHelper.getInstance().convertFormToAuditoriumBo(auditoriumBookingForm);
		boolean flag = transaction.saveAuditoriumDetails(booking);
		if(flag)
		{
			    auditoriumBookingForm.setAuditoriumBooking(booking);
			    auditoriumBookingForm.setMode("Save");
		       // AuditoriumBookingHelper.getInstance().sendMailAndSmS(auditoriumBookingForm)	;
		}
		return flag;
	}
	  /**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public JSONArray getCalendarEventsData(AuditoriumBookingForm auditoriumBookingForm) throws Exception{
		  return AuditoriumBookingHelper.getInstance().getSavedEvents(auditoriumBookingForm);
	  }
	  /**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateCalendarEventsData(AuditoriumBookingForm auditoriumBookingForm) throws Exception
		{
			boolean updated = transaction.updateCalendarEventsData(auditoriumBookingForm);
			if(updated)
			{
			AuditoriumBooking auditoriumBooking=transaction.getAuditoriumBookingByEventId(Integer.parseInt(auditoriumBookingForm.getEventId()));
			auditoriumBookingForm.setAuditoriumBooking(auditoriumBooking);
			AuditoriumBookingHelper.getInstance().sendMailAndSmS(auditoriumBookingForm);
			}
			
			return updated;
		}
	  /**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,String> getRequirementsMap()throws Exception{
			List<BookingRequirements> requirementList = transaction.getRequirementsMap();
			Map<Integer,String> requirMap = AuditoriumBookingHelper.getInstance().setRequirementsMap(requirementList);
			return requirMap;
		}
	  /**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteEventByEventId(AuditoriumBookingForm auditoriumBookingForm) throws Exception
		{
			 boolean deleted=transaction.deleteEventByEventId(auditoriumBookingForm);
			// AuditoriumBookingHelper.getInstance().sendMailAndSmS(auditoriumBookingForm);
			 return deleted;
		}
	  /**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws Exception
	 */
	public boolean rejectEventByApprover(AuditoriumBookingForm auditoriumBookingForm) throws Exception
		{
			boolean rejected= transaction.rejectEventByApprover(auditoriumBookingForm);
			 AuditoriumBookingHelper.getInstance().sendMailAndSmS(auditoriumBookingForm);
			return rejected;
		}
	  /**
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<AuditoriumBookingTo>> getApprovedEvents()throws Exception{
		  List<AuditoriumBooking> auditoriumBookingList=transaction.getApprovedEvents();
		  return  AuditoriumBookingHelper.getInstance().setApprovedEventsToMap(auditoriumBookingList);
		 
		  
	  }
	/**
	 * @param auditoriumBookingForm
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	public String getFacilityAvailableByBlockAndVenue(AuditoriumBookingForm auditoriumBookingForm) throws NumberFormatException, Exception{
		VenueDetails venueDetails=transaction.getVenueByID(Integer.parseInt(auditoriumBookingForm.getVenueId()));
		return AuditoriumBookingHelper.getInstance().getFacilityAvailableSetting(auditoriumBookingForm,venueDetails);
	}
	
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<AuditoriumBookingTo>> getPendingEvents()throws Exception{
		  List<AuditoriumBooking> auditoriumBookingList=transaction.getPendingApprovedEvents();
		  return  AuditoriumBookingHelper.getInstance().setApprovedEventsToMap(auditoriumBookingList);
	  }
	
}
