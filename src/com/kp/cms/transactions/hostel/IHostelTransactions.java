package com.kp.cms.transactions.hostel;

import java.util.List;

import com.kp.cms.bo.admin.HlAdmissionBo;
import com.kp.cms.forms.hostel.HostelTransactionForm;


public interface IHostelTransactions {
	public List<HlAdmissionBo> getStudentList(HostelTransactionForm  form)throws Exception;
}
