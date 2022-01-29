package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

public interface IDownloadInterviewFormat {

	Map<Integer, Integer> getInterviewPerPanelMap(String query) throws Exception;

	List getCandidates(String query) throws Exception;

}
