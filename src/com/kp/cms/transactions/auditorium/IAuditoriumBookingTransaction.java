package com.kp.cms.transactions.auditorium;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.auditorium.AuditoriumBooking;
import com.kp.cms.bo.auditorium.BlockDetails;
import com.kp.cms.bo.auditorium.BookingRequirements;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.forms.auditorium.AuditoriumBookingForm;

public interface IAuditoriumBookingTransaction {
    public List<BlockDetails> getBlockDetails()throws Exception;
    public List<VenueDetails> getVenueDetails()throws Exception;
    public Map<Integer,String> getVenuesByBlockDetails(int blockId)throws Exception;
    public List<BookingRequirements> getBookingRequirements()throws Exception;
    public boolean saveAuditoriumDetails(AuditoriumBooking booking)throws Exception;
    public Users getUserName(String uid)throws Exception;
    public List<AuditoriumBooking> getCalendarEventsData()throws Exception;
    public boolean updateCalendarEventsData(AuditoriumBookingForm bookingForm);
    public List<BookingRequirements> getRequirementsMap()throws Exception;
    public BookingRequirements getBookingRequirById(int requirId) throws Exception;
    public List<Integer> getRequirementsId() throws Exception;
    public boolean deleteEventByEventId(AuditoriumBookingForm auditoriumBookingForm) throws Exception;
    public void setUserMailIdAndMobileNoByUserIdToForm(int id,AuditoriumBookingForm bookingForm) throws Exception;
    public AuditoriumBooking getAuditoriumBookingByEventId(int eventId) throws Exception;
    public boolean rejectEventByApprover(AuditoriumBookingForm auditoriumBookingForm) throws Exception;
    public String getAdminMailIdByVenueAndBlock(String venue,int blockId)throws Exception;
    public List<AuditoriumBooking> getCalendarEventsByBlockAndVenue(AuditoriumBookingForm auditoriumBookingForm)throws Exception;
   // public Long getTotalCalendarEventId()throws Exception;
    public List<AuditoriumBooking> getApprovedEvents()throws Exception;
    public VenueDetails getVenueByID(int id)throws Exception;
    public List<AuditoriumBooking> getPendingApprovedEvents()throws Exception;
}
