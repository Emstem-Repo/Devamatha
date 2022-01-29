package com.kp.cms.helpers.reports;


import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.ColumnReportForm;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;
import com.kp.cms.to.reports.ReportNameSummaryTO;


public class ConfigureColumnHelper {
	private static volatile ConfigureColumnHelper configureColumnHelper = null;
	private static final Log log = LogFactory.getLog(ConfigureColumnHelper.class);
	/**
	 * 
	 * @returns a single instance (Singleton)every time. 
	 */

	public static ConfigureColumnHelper getInstance() {
		if (configureColumnHelper == null) {
			configureColumnHelper = new ConfigureColumnHelper();
			return configureColumnHelper;
		}
		return configureColumnHelper;
	}
	
	/**
	 * Used to convert report Name from BO to TO
	 */
	
	public List<ConfigureColumnForReportTO> populateReportNames(List<String> reportNameList)throws Exception{
		log.info("Inside of populateReportNames of ConfigureColumnHelper");
		List<ConfigureColumnForReportTO> nameList = new ArrayList<ConfigureColumnForReportTO>();
		ConfigureColumnForReportTO reportTO =null;
		if(reportNameList!=null && !reportNameList.isEmpty()){			
			Iterator iterator = reportNameList.iterator();
			while (iterator.hasNext()) {				
				String  reportName = (String) iterator.next();;
				if(reportName!=null){
				reportTO = new ConfigureColumnForReportTO();
				reportTO.setReportName(reportName);
				nameList.add(reportTO);
				}				
			}
		}
		log.info("End of populateReportNames of ConfigureColumnHelper");
		return nameList;
	}

	/**
	 * 
	 * @param detailsList
	 * @param reportName
	 * @return Converts the details based on report Name from BO to TO
	 * @throws Exception
	 */
	public ConfigureColumnForReportTO populateBOToTO(List<Object[]> detailsList , String reportName)throws Exception{
		log.info("Inside of populateBOToTO of ConfigureColumnHelper");
		ConfigureColumnForReportTO reportTO = new ConfigureColumnForReportTO();
		List<ReportNameSummaryTO> summaryList = new ArrayList<ReportNameSummaryTO>();
		ReportNameSummaryTO summaryTO = null;
		
		if(detailsList!=null && !detailsList.isEmpty()){
			Iterator iterator = detailsList.iterator();			
			reportTO.setReportName(reportName);			
			while (iterator.hasNext()) {
				Object[] object = (Object[]) iterator.next();	
				
				int position=0;
				String columnName ="";
				boolean showColumn = false;
				int configReportId =0;
				String createdBy ="";
				Date createdDate = null;
				
				if(object[0]!=null){
				columnName = (String)object[0].toString();
				}
				if(object[1]!=null){
				showColumn = (Boolean)object[1];
				}
				if(object[2]!= null){
				position = (Integer)object[2];
				}
				if(object[3]!=null){
				configReportId = (Integer)object[3];
				}
				if(object[5]!=null){
				createdBy = (String)object[4];
				}
				if(object[5]!=null){
				createdDate = (Date)object[5];
				}
				summaryTO = new ReportNameSummaryTO();	
				
				summaryTO.setConfigReportId(configReportId);	
				
				if(columnName!=null){
					summaryTO.setColumnName(columnName);
				}
				if(position!=0){
					summaryTO.setPosition(String.valueOf(position));
				}
				else{
					summaryTO.setPosition("");
				}
				if(showColumn){
					summaryTO.setShowColumn(String.valueOf(showColumn));
				}
				else{
					summaryTO.setShowColumn(CMSConstants.KNOWLEDGEPRO_FALSE);
				}
				summaryTO.setCreatedBy(createdBy);
				summaryTO.setCreatedDate(createdDate);
				summaryList.add(summaryTO);
			}
			reportTO.setReportNameSummaryList(summaryList);
		}
		log.info("End of populateBOToTO of ConfigureColumnHelper");
		return reportTO;
	}
	
	/**
	 * Used in update
	 * Converts TO to BO's
	 */
	public List<ConfigReportsColumn> copyToTOBO(ConfigureColumnForReportTO reportTO,ColumnReportForm columnForm)throws Exception{
		log.info("Inside of copyToTOBO of ConfigureColumnHelper");
		ConfigReportsColumn column = null;
		List<ConfigReportsColumn> newUpdateList = new ArrayList<ConfigReportsColumn>();
		if(reportTO!=null){
			List<ReportNameSummaryTO> detailsList = reportTO.getReportNameSummaryList();
			if(detailsList!=null && !detailsList.isEmpty()){
			Iterator<ReportNameSummaryTO> it = detailsList.iterator();
			while (it.hasNext()) {
				ReportNameSummaryTO summaryTO = (ReportNameSummaryTO) it.next();
				column=new ConfigReportsColumn();
				column.setId(summaryTO.getConfigReportId());
				if(reportTO.getReportName()!= null){
				column.setReportName(reportTO.getReportName());
				}
				if(summaryTO.getColumnName()!=null){
					column.setColumnName(summaryTO.getColumnName());
				}
				
				if(summaryTO.getShowColumn().equals(CMSConstants.KNOWLEDGEPRO_FALSE)){
					column.setShowColumn(Boolean.valueOf(summaryTO.getShowColumn()));
				}
				else if(summaryTO.getShowColumn().equals(CMSConstants.KNOWLEDGEPRO_TRUE)){
					column.setShowColumn(Boolean.valueOf(summaryTO.getShowColumn()));
					column.setPosition(Integer.parseInt(summaryTO.getPosition()));
				}
				
				column.setLastModifiedDate(new Date());
				if(columnForm.getUserId()!=null){
				column.setModifiedBy(columnForm.getUserId());
				}
				column.setCreatedBy(summaryTO.getCreatedBy());
				column.setCreatedDate(summaryTO.getCreatedDate());
				newUpdateList.add(column);
			}
			}
		}
		log.info("End of copyToTOBO of ConfigureColumnHelper");
		return newUpdateList;
	}
}
