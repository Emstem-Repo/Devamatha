package com.kp.cms.transactions.reports;

import java.util.List;

public interface IFreshersListTransaction {

	List<Object[]> getFresherStudentList(String searchCriteria)throws Exception;

}
