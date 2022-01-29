package com.kp.cms.transactions.hostel;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.HlLeave;
import com.kp.cms.forms.hostel.HostelLeaveApprovalForm;
import com.kp.cms.to.hostel.HostelLeaveApprovalTo;

public interface IHostelLeaveApprovalTransaction {

	public List<Object[]> getLeaveApprovalDetails(HostelLeaveApprovalForm objForm)throws Exception;

	public Map<String, List<HostelLeaveApprovalTo>> getPreviousLeaveDetailsByRegisterNo( HostelLeaveApprovalForm objForm)throws Exception;

	public void setStatus(int id, HostelLeaveApprovalForm objForm, String mode)throws Exception;

}
