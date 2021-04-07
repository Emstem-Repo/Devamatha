package com.kp.cms.handlers.reports;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConfigReportsColumn;
import com.kp.cms.forms.reports.ColumnReportForm;
import com.kp.cms.helpers.reports.ConfigureColumnHelper;
import com.kp.cms.to.reports.ConfigureColumnForReportTO;
import com.kp.cms.transactions.reports.IConfigureColumnTransaction;
import com.kp.cms.transactionsimpl.reports.ConfigureColumnTransactionImpl;

public class ConfigureColumnHandler {
	
	private static final Log log = LogFactory.getLog(ConfigureColumnHandler.class);
	
	public static volatile ConfigureColumnHandler configureColumnHandler = null;
	
	/**
	 * This method is used to create a single instance of CurrencyMasterHandler.
	 * @return unique instance of CurrencyMasterHandler class.
	 */
	
	public static ConfigureColumnHandler getInstance() {
		if (configureColumnHandler == null) {
			configureColumnHandler = new ConfigureColumnHandler();
			return configureColumnHandler;
		}
		return configureColumnHandler;
	}

	public boolean saveColumnForReport(ColumnReportForm columnForm) throws Exception{
		log.info("Inside of saveColumnForReport of ConfigureColumnHandler");
		IConfigureColumnTransaction transaction = ConfigureColumnTransactionImpl.getInstance();
		ConfigReportsColumn reportsColumn = new ConfigReportsColumn();
		if(columnForm.getReportName() != null && !StringUtils.isEmpty(columnForm.getReportName())){
			reportsColumn.setReportName(columnForm.getReportName());
		}
		if(columnForm.getColumnName() != null && !StringUtils.isEmpty(columnForm.getColumnName())){
			reportsColumn.setColumnName(columnForm.getColumnName());
		}
		reportsColumn.setShowColumn(Boolean.TRUE);
		reportsColumn.setCreatedBy(columnForm.getUserId());
		reportsColumn.setCreatedDate(new Date());
		boolean isAdded = transaction.saveColumnForReport(reportsColumn);
		log.info("End of saveColumnForReport of ConfigureColumnHandler");
		return isAdded;
	}
	/**
	 * Used to get all the report Names
	 */
	public List<ConfigureColumnForReportTO> getReportNames()throws Exception{
		log.info("Inside of getReportNames of ConfigureColumnHandler");
		IConfigureColumnTransaction transaction = ConfigureColumnTransactionImpl.getInstance();
		List<String> reportNameList = transaction.getReportNames();
		log.info("End of getReportNames of ConfigureColumnHandler");
		return ConfigureColumnHelper.getInstance().populateReportNames(reportNameList);
	}
	
	public void getDetailsOnReportName(ColumnReportForm columnForm)throws Exception{
		log.info("Inside of getDetailsOnReportName of ConfigureColumnHandler");
		String reportName = columnForm.getReportName().trim();
		IConfigureColumnTransaction transaction = ConfigureColumnTransactionImpl.getInstance();
		List<Object[]> detailsList = transaction.getDetailsOnReportName(reportName);
		ConfigureColumnForReportTO reportTO = ConfigureColumnHelper.getInstance().populateBOToTO(detailsList, reportName);
		if(reportTO!=null){
			columnForm.setReportTO(reportTO);
		}
		log.info("End of getDetailsOnReportName of ConfigureColumnHandler");
	}
	public boolean updateConfigReport(ConfigureColumnForReportTO reportTO,ColumnReportForm columnForm)throws Exception{
		log.info("Inside of updateConfigReport of ConfigureColumnHandler");
		List<ConfigReportsColumn> newList = ConfigureColumnHelper.getInstance().copyToTOBO(reportTO, columnForm);
		IConfigureColumnTransaction transaction = ConfigureColumnTransactionImpl.getInstance();
		log.info("End of updateConfigReport of ConfigureColumnHandler");
		return transaction.updateConfigReport(newList);
	}
}
