package com.kp.cms.handlers.admin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmBioDataCJC;
import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.forms.admin.AdmMeritListForm;
import com.kp.cms.helpers.admin.AdmMeritHelper;
import com.kp.cms.helpers.admission.AdmissionBioDataReportHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admin.AdmMeritListReportTo;
import com.kp.cms.to.admin.AdmMeritTO;
import com.kp.cms.transactions.admin.IAdmMeritTransaction;
import com.kp.cms.transactions.admission.IAdmissionBioDataReportTransaction;
import com.kp.cms.transactionsimpl.admin.AdmMeritTxnImpl;
import com.kp.cms.transactionsimpl.admission.AdmissionBioDataReportTxnImpl;

public class AdmMeritHandler {
	public static volatile AdmMeritHandler admMeritHandler = null;
	private AdmMeritHandler(){
		
	}
	 public static AdmMeritHandler getInstance(){
		 if(admMeritHandler == null){
			 admMeritHandler = new AdmMeritHandler();
			 return admMeritHandler;
		 }
		 return admMeritHandler;
	 }
	 
	 /**
	 * @param file
	 * @param admMeritForm
	 * @return
	 * @throws Exception
	 */
	public boolean uploadMeritList(File file,AdmMeritListForm admMeritForm)throws Exception{
		 IAdmMeritTransaction transaction=AdmMeritTxnImpl.getInstance();
		 List<AdmMeritTO> meritTo=AdmMeritHelper.getInstance().convertExcelTOBO(file);
		 List<AdmMeritList> meritList=AdmMeritHelper.getInstance().convertTOtoBO(meritTo,admMeritForm);
		 boolean isAdd=transaction.addAdmMeritList(meritList, admMeritForm);
		 return isAdd;
	 }
	 /**
	 * @param file
	 * @param admMeritForm
	 * @return
	 * @throws Exception
	 */
	public boolean uploadAdmFeemain(File file,AdmMeritListForm admMeritForm)throws Exception{
		 IAdmMeritTransaction transaction=AdmMeritTxnImpl.getInstance();
		 List<AdmFeeMain> feeMainList=AdmMeritHelper.getInstance().convertExcelTOBOForFee(file,admMeritForm);
		 boolean isAdd=transaction.addAdmFeeMain(feeMainList, admMeritForm);
		 return isAdd;
	 }
	/**
	 * @param admMeritListForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public List<AdmMeritTO> getAdmMeritListSearch( AdmMeritListForm admMeritListForm, HttpServletRequest request) throws Exception {
		 IAdmMeritTransaction transaction=AdmMeritTxnImpl.getInstance();
		 AdmMeritHelper helper =AdmMeritHelper.getInstance();
		StringBuffer query =helper.selectedQuery(admMeritListForm);
		List<AdmMeritList> meritLists =  transaction.getAdmMeritList(query);
		List<AdmMeritTO> meritTOs = helper.populateBOToTO(meritLists);
		return meritTOs;
	}

	/**
	 * @param admMeritListForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean exportTOExcel(AdmMeritListForm admMeritListForm, HttpServletRequest request) throws Exception{
		boolean isUpdated = false;
		 AdmMeritHelper helper =AdmMeritHelper.getInstance();
		 AdmMeritListReportTo meritListSelectedTo = helper.selectedColumns(admMeritListForm);
		 isUpdated = helper.convertToExcel(meritListSelectedTo,admMeritListForm,request);
		return isUpdated;
	}
}
