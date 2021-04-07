package com.kp.cms.transactions.auditorium;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionErrors;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.forms.auditorium.VenueDetailsForm;

public interface IVenueDetailsTransaction {
    public Map<Integer,String> getBlockDetails()throws Exception;
    
    public boolean duplicateCheck(String name,HttpSession session,ActionErrors errors,VenueDetailsForm venueDetailsForm)throws Exception;
    
    public boolean addVenueDetails(VenueDetails venue,String mode)throws Exception;
    
    public VenueDetails getVenueDetailsById(int id)throws Exception; 
    
    public boolean deleteVenueDetails(int id)throws Exception;
    
    public boolean reactivateVenueDetails(VenueDetailsForm venueDetailsForm)throws Exception;
    
    public List<VenueDetails> getVenueDetails()throws Exception;
}
