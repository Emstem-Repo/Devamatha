package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.Student;

public interface IAssignSecondLanguageTransaction {

	List<Student> getDataForQuery(String query) throws Exception;

}
