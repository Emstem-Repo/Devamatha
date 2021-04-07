package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.to.admission.PersonalDataTO;

public interface IUploadMobileNumbersTransaction {

	boolean uploadData(List<PersonalDataTO> results, String user) throws Exception;

}
