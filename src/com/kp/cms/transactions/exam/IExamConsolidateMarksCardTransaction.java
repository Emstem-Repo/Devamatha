package com.kp.cms.transactions.exam;

import java.util.List;

public interface IExamConsolidateMarksCardTransaction {

	public List<Object[]> getConsolidateMarksCardDetails(int courseId,int year) throws Exception;

	public boolean saveConsolidateMarksCardDetails(List<Object[]> ugConsolidateMarksCardBO) throws Exception;

}
