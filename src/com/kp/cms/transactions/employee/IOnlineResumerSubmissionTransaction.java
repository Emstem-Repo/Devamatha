package com.kp.cms.transactions.employee;

import com.kp.cms.bo.admin.EmpOnlineResume;

public interface IOnlineResumerSubmissionTransaction {

	boolean saveOnlineResume(EmpOnlineResume objBO)throws Exception;

}
