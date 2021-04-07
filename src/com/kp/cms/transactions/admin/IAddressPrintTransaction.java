package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IAddressPrintTransaction {

	List<Student> getRequiredRollNos(String regNoFrom, String regNoTo) throws Exception;

	List<Student> getRequiredRegdNos(String regNoFrom, String regNoTo) throws Exception;

}
