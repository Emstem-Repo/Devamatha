package com.kp.cms.transactions.reports;

import java.util.List;
import java.util.Map;

public interface ISecondLanguageTransaction {

	Map<String, String> getAllSecondLanguages()throws Exception;

	List<Object[]> getSecondLanguageStudents(String searchCriteria)throws Exception;
	Map<String, String> getAllSecondLanguagesFromExamSecondLanguage()throws Exception;

}
