package com.kp.cms.transactions.reports;

import java.util.List;
import java.util.Map;

public interface IInterviewScoreSheetTransaction {
	List getListOfCandidates(String searchCriteria) throws Exception;
	Map<Integer, String> getInterviewProgramCourseMap(String query) throws Exception;
	Map<Integer, String> getTemplateMap(String query) throws Exception;
}	