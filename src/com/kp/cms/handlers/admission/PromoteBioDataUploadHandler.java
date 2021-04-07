package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admission.PromoteBioData;
import com.kp.cms.forms.admission.PromoteBioDataUploadForm;
import com.kp.cms.helpers.admission.PromoteBioDataUploadHelper;
import com.kp.cms.to.admission.PromoteBioDataUploadTo;
import com.kp.cms.transactions.admission.IAdmissionBioDataReportTransaction;
import com.kp.cms.transactions.admission.IPromoteBioDataUploadTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionBioDataReportTxnImpl;
import com.kp.cms.transactionsimpl.admission.PromoteBioDataUploadTxnImpl;

public class PromoteBioDataUploadHandler {
 private static volatile PromoteBioDataUploadHandler bioDataUploadHandler = null;
 public static PromoteBioDataUploadHandler getInstance(){
	 if(bioDataUploadHandler == null){
		 bioDataUploadHandler = new PromoteBioDataUploadHandler();
		 return bioDataUploadHandler;
	 }
	 return bioDataUploadHandler;
 }
 
// 
//	public void getCoursePageData(PromoteBioDataUploadForm promoteBioDataForm) throws Exception {
//		Map<Integer,String> courseMap = new HashMap<Integer, String>();
//		
//		courseMap = transaction.getCourseMap();
//		if(courseMap!=null && !courseMap.isEmpty()){
//			promoteBioDataForm.setCourseMap(courseMap);
//		}
//	
//	}
 
public boolean uploadPromoteBioData( List<PromoteBioDataUploadTo> promoteBiodataList) throws Exception{
	IPromoteBioDataUploadTransaction transaction = PromoteBioDataUploadTxnImpl.getInstance();
	List<PromoteBioData> bioDataUploadList  = PromoteBioDataUploadHelper.getInstance().convertToTOBo(promoteBiodataList);
	return transaction.uploadPromoteBioData(bioDataUploadList);
}

public List<PromoteBioDataUploadTo> getProBioSearch(PromoteBioDataUploadForm promoteBioDataForm, HttpServletRequest request) throws Exception{
	IPromoteBioDataUploadTransaction transaction = PromoteBioDataUploadTxnImpl.getInstance();
	PromoteBioDataUploadHelper reportHelper = PromoteBioDataUploadHelper.getInstance();
	StringBuffer query =reportHelper.selectedQuery(promoteBioDataForm);
	List<PromoteBioData> proBioDataCJCList =  transaction.getProBioDataDetails(query);
	List<PromoteBioDataUploadTo> bioDataCJCTos = reportHelper.populateBOToTO(proBioDataCJCList);
	return bioDataCJCTos;
}

}
