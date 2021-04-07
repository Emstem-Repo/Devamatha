package com.kp.cms.transactions.reports;

import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.CancelledAdmissionsForm;

public interface ICancelledAdmissionsTransaction {
	public List<Student> getCancelledAdmissions(CancelledAdmissionsForm cancelledAdmForm) throws Exception;
}
