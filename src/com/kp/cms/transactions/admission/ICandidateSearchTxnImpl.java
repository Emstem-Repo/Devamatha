package com.kp.cms.transactions.admission;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.exceptions.ApplicationException;

/**
 * @author kalyan.c
 *
 */
public interface ICandidateSearchTxnImpl {
	public List<AdmAppln> getStudentSearch(String searchCriteria) throws Exception;
	public List<ConfigReportsColumn> getSelectedColumns() throws Exception;
	public List<ConfigReportsColumn> getColumnsReportList() throws Exception;
	public ArrayList<Object[]> executeAdmissionReport(String searchCriteria,Connection conn);
	public Integer getMaxColumnNo() throws ApplicationException;
	public TreeMap<String, String> getDocTypesMap(String query) throws Exception;
	public TreeMap<String, String> getInterviewTypeHeading(String intQuery) throws Exception;
	public TreeMap<String, String> getPreRequisteMap(String preReqQuery) throws Exception;
	public List<Integer> getStudentListWithPhotos(List<Integer> admIdsForSearch) ;
}


