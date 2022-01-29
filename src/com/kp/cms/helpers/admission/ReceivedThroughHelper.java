package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.forms.admission.ReceivedThroughForm;
import com.kp.cms.to.admission.ReceivedThroughTo;

public class ReceivedThroughHelper {
	private static volatile ReceivedThroughHelper receivedThroughHelper = null;
	public static ReceivedThroughHelper getInstance(){
		if(receivedThroughHelper == null){
			receivedThroughHelper = new ReceivedThroughHelper();
			return receivedThroughHelper;
		}
		return receivedThroughHelper;
		
	}
	public ReceivedThrough convertFormToBO(ReceivedThroughForm receivedThroughForm,String mode){
		ReceivedThrough received = new ReceivedThrough();
		if(receivedThroughForm.getReceivedThrough()!=null){
			received.setReceivedThrough(receivedThroughForm.getReceivedThrough());
		}
		if(receivedThroughForm.getSlipRequired()!=null){
			if(receivedThroughForm.getSlipRequired().equalsIgnoreCase("Yes"))
			    received.setSlipRequired(true);
			else
				received.setSlipRequired(false);
		}
		if(mode.equalsIgnoreCase("add")){
			received.setCreatedBy(receivedThroughForm.getUserId());
			received.setCreatedDate(new Date());
			
		}else{
			received.setModifiedBy(receivedThroughForm.getUserId());
			received.setId(receivedThroughForm.getId());
			received.setLastModifiedDate(new Date());
		}
		received.setIsActive(true);
		return received;
	}
	public void setBotoForm(ReceivedThroughForm receivedThroughForm,ReceivedThrough received){
    	if(received!=null){
    		if(received.getReceivedThrough()!=null){
    			receivedThroughForm.setReceivedThrough(received.getReceivedThrough());
    		}
    		if(received.getSlipRequired()){
    			receivedThroughForm.setSlipRequired("Yes");
    		}else
    			receivedThroughForm.setSlipRequired("No");
		}
    }
	public List<ReceivedThroughTo> convertBoToTO(List<ReceivedThrough> receivedBoList){
		List<ReceivedThroughTo> receivedThroughToList = new ArrayList<ReceivedThroughTo>();
		if(receivedBoList!=null && !receivedBoList.isEmpty()){
			Iterator<ReceivedThrough> itr=receivedBoList.iterator();
			while(itr.hasNext()){
				ReceivedThrough received=itr.next();
				ReceivedThroughTo to =new ReceivedThroughTo();
				to.setId(received.getId());
				if(received.getReceivedThrough()!=null)
					to.setReceivedThrough(received.getReceivedThrough());
				if(received.getSlipRequired())
					to.setSlipRequired("Yes");
				else
					to.setSlipRequired("No");
				receivedThroughToList.add(to);
			}
		}
		return receivedThroughToList;
	}
}
