package com.kp.cms.transactions.admission;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.forms.admission.ReceivedThroughForm;
import com.kp.cms.to.admission.ReceivedThroughTo;

public interface IReceivedThroughTxn {
    public boolean addReceivedThrough(ReceivedThrough received,String mode)throws Exception;
    public boolean duplicateCheck(HttpSession session,ActionErrors errors,ReceivedThroughForm receivedThroughForm);
    public ReceivedThrough getReceivedThroughById(int id); 
    public boolean deleteReceivedThrough(int id);
    public boolean reactivateReceivedThrough(ReceivedThroughForm receivedThroughForm)throws Exception;
    public List<ReceivedThrough> getReceivedThroughList();
}
