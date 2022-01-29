package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admission.AdmissionTcDetails;
import com.kp.cms.forms.admission.AdmissionTcDetailsForm;
import com.kp.cms.helpers.admission.AdmissionTcDetailsHelper;
import com.kp.cms.to.admission.AdmissionTcDetailsReportTo;
import com.kp.cms.to.admission.AdmissionTcDetailsTo;
import com.kp.cms.transactions.admission.IAdmissionTcDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionTcDetailsTxnImpl;

public class AdmissionTcDetailsHandler {
	private static volatile AdmissionTcDetailsHandler  admissionTcDetailsHandler = null;
	public static AdmissionTcDetailsHandler getInstance(){
		if(admissionTcDetailsHandler == null){
			admissionTcDetailsHandler = new AdmissionTcDetailsHandler();
			return admissionTcDetailsHandler;
		}
		return admissionTcDetailsHandler;
	}
	/**
	 * @param admissionTcDetailsTo
	 * @return
	 * @throws Exception
	 */
	public boolean uploadAdmTcDetails( List<AdmissionTcDetailsTo> admissionTcDetailsTo) throws Exception{
		IAdmissionTcDetailsTransaction transaction = AdmissionTcDetailsTxnImpl.getInstance();
		List<AdmissionTcDetails> tcDetailsList = AdmissionTcDetailsHelper.getInstance().convertToTOBo(admissionTcDetailsTo);
		return transaction.uploadTcDetails(tcDetailsList);
	}
	/**
	 * @param tcDetailsForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<AdmissionTcDetailsTo> getAdmTcDetailsSearch( AdmissionTcDetailsForm tcDetailsForm, HttpServletRequest request)throws Exception {
		IAdmissionTcDetailsTransaction transaction=AdmissionTcDetailsTxnImpl.getInstance();
		AdmissionTcDetailsHelper helper =AdmissionTcDetailsHelper.getInstance();
		StringBuffer query =helper.selectedQuery(tcDetailsForm);
		List<AdmissionTcDetails> tcDetails =  transaction.getAdmTcDetailsList(query);
		List<AdmissionTcDetailsTo> tos = helper.populateBOToTO(tcDetails);
		return tos;
	}
	/**
	 * @param tcDetailsForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(AdmissionTcDetailsForm tcDetailsForm, HttpServletRequest request)throws Exception {
		boolean isUpdated = false;
		AdmissionTcDetailsHelper helper =AdmissionTcDetailsHelper.getInstance();
		AdmissionTcDetailsReportTo reportTo= helper.selectedColumns(tcDetailsForm);
		isUpdated = helper.convertToExcel(reportTo,tcDetailsForm,request);
		return isUpdated;
	}
}
