package com.kp.cms.handlers.auditorium;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.PayScaleBO;
import com.kp.cms.bo.auditorium.VenueDetails;
import com.kp.cms.forms.auditorium.VenueDetailsForm;
import com.kp.cms.forms.employee.PayScaleDetailsForm;
import com.kp.cms.helpers.auditorium.VenueDetailsHelper;
import com.kp.cms.helpers.employee.PayScaleDetailsHelper;
import com.kp.cms.to.auditorium.VenueDetailsTO;
import com.kp.cms.transactions.auditorium.IVenueDetailsTransaction;
import com.kp.cms.transactions.employee.IPayScaleTransactions;
import com.kp.cms.transactionsimpl.auditorium.VenueDetailsTxnImpl;
import com.kp.cms.transactionsimpl.employee.PayScaleTransactionImpl;

public class VenueDetailsHandler {
	IVenueDetailsTransaction transaction = VenueDetailsTxnImpl.getInstance();
    public static volatile VenueDetailsHandler venueDetailsHandler = null;
    private static Log log = LogFactory.getLog(VenueDetailsHandler.class);
    public static VenueDetailsHandler getInstance() {
		if (venueDetailsHandler == null) {
			venueDetailsHandler = new VenueDetailsHandler();
			return venueDetailsHandler;
		}
		return venueDetailsHandler;
	}
    public Map<Integer,String> getBlockDetails()throws Exception{
    	
    	Map<Integer,String> blockMap =transaction.getBlockDetails();
    	return blockMap;
    }
    public boolean duplicateCheck(VenueDetailsForm venueDetailsForm,ActionErrors errors,HttpSession session)throws Exception{
		boolean duplicate=transaction.duplicateCheck(venueDetailsForm.getVenueName(), session, errors, venueDetailsForm);
		return duplicate;
	}
    public boolean addVenueDetails(VenueDetailsForm venueDetailsForm,String mode)throws Exception{
    	VenueDetails venueDetails=VenueDetailsHelper.getInstance().convertFormTOBO(venueDetailsForm,mode);
		boolean isAdded=transaction.addVenueDetails(venueDetails, mode);
		return isAdded;
	}
    public void editVenueDetails(VenueDetailsForm venueDetailsForm)throws Exception{
		VenueDetails venue=transaction.getVenueDetailsById(venueDetailsForm.getId());
		VenueDetailsHelper.getInstance().setBotoForm(venueDetailsForm, venue);
	}
    public boolean deleteVenueDetails(VenueDetailsForm venueDetailsForm)throws Exception{
			boolean isDeleted=transaction.deleteVenueDetails(venueDetailsForm.getId());
			return isDeleted;
	}
    public boolean reactivateVenueDetails(VenueDetailsForm venueDetailsForm)
	 throws Exception{
       return transaction.reactivateVenueDetails(venueDetailsForm);
   }
    public List<VenueDetailsTO> getVenueDetails(VenueDetailsForm venueDetailsForm)
	 throws Exception{
    	List<VenueDetails> venues = transaction.getVenueDetails();
    	List<VenueDetailsTO> venueTos = VenueDetailsHelper.getInstance().convertBoToTO(venues);
        return venueTos;
  }
}
