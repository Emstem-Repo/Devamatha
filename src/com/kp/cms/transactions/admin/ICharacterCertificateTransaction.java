package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface ICharacterCertificateTransaction {

	List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo) throws Exception;

}
