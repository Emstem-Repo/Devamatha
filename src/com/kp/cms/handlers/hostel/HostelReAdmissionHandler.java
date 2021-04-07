package com.kp.cms.handlers.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HostelOnlineApplication;
import com.kp.cms.forms.hostel.HostelReAdmissionForm;
import com.kp.cms.helpers.hostel.HostelReAdmissionHelper;
import com.kp.cms.to.hostel.HostelReAdmissionTo;
import com.kp.cms.transactions.hostel.IHostelReAdmissionTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelReAdmissionTxnImpl;

public class HostelReAdmissionHandler {
 
	
	IHostelReAdmissionTransaction transaction = HostelReAdmissionTxnImpl.getInstance();
    public static volatile HostelReAdmissionHandler hostelReAdmissionHandler = null;
    
    
    /**
     * @return
     */
    public static HostelReAdmissionHandler getInstance() {
		if (hostelReAdmissionHandler == null) {
			hostelReAdmissionHandler = new HostelReAdmissionHandler();
			return hostelReAdmissionHandler;
		}
		return hostelReAdmissionHandler;
	}


	/**
	 * @param admissionForm
	 * @return
	 * @throws Exception
	 */
	public List<HostelReAdmissionTo> getStudentDetailsByHosteIdAndYear(HostelReAdmissionForm admissionForm) throws Exception {
             List<HostelOnlineApplication> applicationList=transaction.getStudentDetailsByHostelIdAndYear(admissionForm);
             return HostelReAdmissionHelper.getInstance().convertBotoTo(applicationList);
	}


	public boolean updateHostelOnlineApplication(HostelReAdmissionForm admissionForm) throws Exception {
            List<HostelOnlineApplication> applications= HostelReAdmissionHelper.getInstance().convertTOtoBo(admissionForm);
           return transaction.updateHostelOnlineApplication(applications);
	}
}
