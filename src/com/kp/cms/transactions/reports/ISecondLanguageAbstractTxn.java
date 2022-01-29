package com.kp.cms.transactions.reports;

import java.util.List;


public interface ISecondLanguageAbstractTxn {
	public List<Object[]> getStudentsWithLanguageCount(int programTypeId, int semester, String language) throws Exception;

}
