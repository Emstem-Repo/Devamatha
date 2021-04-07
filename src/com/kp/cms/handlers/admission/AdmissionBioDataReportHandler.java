package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.forms.admission.AdmissionBioDataReportForm;
import com.kp.cms.helpers.admission.AdmissionBioDataReportHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admission.AdmissionBioDataReportTo;
import com.kp.cms.transactions.admission.IAdmissionBioDataReportTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionBioDataReportTxnImpl;

public class AdmissionBioDataReportHandler {
	private static volatile AdmissionBioDataReportHandler admissionBioDataReportHandler = null;
	/**
	 * @return
	 */
	public static AdmissionBioDataReportHandler getInstance(){
		if(admissionBioDataReportHandler == null){
			admissionBioDataReportHandler = new AdmissionBioDataReportHandler();
			return admissionBioDataReportHandler;
		}
		return admissionBioDataReportHandler;
	}
	/**
	 * @param dataMigrationForm
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public List<AdmBioDataCJCTo> getAdmBioSearch(AdmissionBioDataReportForm dataMigrationForm, HttpServletRequest request) throws Exception {
		IAdmissionBioDataReportTransaction transaction = AdmissionBioDataReportTxnImpl.getInstance();
		AdmissionBioDataReportHelper reportHelper = AdmissionBioDataReportHelper.getInstance();
		StringBuffer query =reportHelper.selectedQuery(dataMigrationForm);
		List<AdmBioDataCJC> admBioDataCJCList =  transaction.getAdmBioDataDetails(query);
		List<AdmBioDataCJCTo> bioDataCJCTos = reportHelper.populateBOToTO(admBioDataCJCList);
		return bioDataCJCTos;
	}
	/**
	 * @param dataMigrationForm
	 * @param request
	 * @return
	 */
	public boolean exportTOExcel(AdmissionBioDataReportForm dataMigrationForm, HttpServletRequest request) throws Exception{
		boolean isUpdated = false;
		AdmissionBioDataReportHelper reportHelper = AdmissionBioDataReportHelper.getInstance();
		AdmissionBioDataReportTo admBioTo = reportHelper.selectedColumns(dataMigrationForm);
		isUpdated = reportHelper.convertToTOExcel(admBioTo,dataMigrationForm,request);
		return isUpdated;
	}
	
}
