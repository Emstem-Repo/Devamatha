package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.forms.admission.SettlementExceptionuploadForm;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;
import com.kp.cms.transactions.admission.ISettlementExceptionUploadTransaction;
import com.kp.cms.transactionsimpl.admission.SettlementExceptionUploadTransactionImpl;

public class SettlementExceptionUploadHandler {
	private static final Log log = LogFactory.getLog(SettlementExceptionUploadHandler.class);
	private static volatile SettlementExceptionUploadHandler settlementExceptionHandler = null;
	
	private SettlementExceptionUploadHandler() {
	}
	
	/**
	 * @return
	 */
	public static SettlementExceptionUploadHandler getInstance() {
		if (settlementExceptionHandler == null) {
			settlementExceptionHandler = new SettlementExceptionUploadHandler();
		}
		return settlementExceptionHandler;
	}
	ISettlementExceptionUploadTransaction transaction=SettlementExceptionUploadTransactionImpl.getInstance();
	/**
	 * @param candidateRefNoList
	 * @return
	 * @throws Exception
	 */
	public Map<String,CandidatePGIDetails>  getTrnsactionPendingStatuscandiates(List<String> candidateRefNoList)throws Exception {
		return transaction.getTnxPendingStatuscandiates(candidateRefNoList);
	}
	
	/**
	 * @param candidateRefNoMap
	 * @param results
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public boolean  uploadSettlementException(Map<String,CandidatePGIDetails> candidateRefNoMap,List<GenerateSettlementOrRefundPgiTo> results,SettlementExceptionuploadForm objform)throws Exception {
		List<String> dupRegNumber=new ArrayList<String>();
		String str= "";
		String str2="Duplicate entry found for the following CustomerId:";
		StringBuilder dupCandidateRefNo=new StringBuilder();
		List<CandidatePGIDetails> boList = new ArrayList<CandidatePGIDetails>();
		boolean isUpdate=false;
		if(candidateRefNoMap!=null && !candidateRefNoMap.isEmpty()){
			if(results!=null && !results.isEmpty()){
				Iterator<GenerateSettlementOrRefundPgiTo> itr = results.iterator();
				while (itr.hasNext()) {
					GenerateSettlementOrRefundPgiTo to = (GenerateSettlementOrRefundPgiTo) itr.next();
				if(!dupRegNumber.contains(to.getCandidateRefNo())){
					if(candidateRefNoMap.containsKey(to.getCandidateRefNo())){
						if(to.getCandidateRefNo()!=null && !to.getCandidateRefNo().isEmpty()){
							CandidatePGIDetails bo=candidateRefNoMap.get(to.getCandidateRefNo());
							bo.setTxnRefNo(to.getTxnRefNo());
							bo.setTxnDate(to.getTxnDate());
							bo.setBankId(to.getBankId());
							bo.setBankRefNo(to.getBankRefNo());
							bo.setTxnStatus("Success");
							boList.add(bo);
							dupRegNumber.add(to.getCandidateRefNo());
						}
					}else{
						if(to.getCandidateRefNo()!=null && !to.getCandidateRefNo().isEmpty()){
							if(str==null || str.isEmpty()){
								str=str+removeFileExtension(to.getCandidateRefNo());
							}else{
								str=str+","+removeFileExtension(to.getCandidateRefNo());
							}
						}
					}
				}else{
					if(dupCandidateRefNo.length()==0)
						dupCandidateRefNo=dupCandidateRefNo.append(removeFileExtension(to.getCandidateRefNo()));
					else
						dupCandidateRefNo=dupCandidateRefNo.append(",").append(removeFileExtension(to.getCandidateRefNo()));
					objform.setDuplicanRefNo(true);
				}
					
				}
			}
			
			if(objform.getDuplicanRefNo()!=null && objform.getDuplicanRefNo()){
				objform.setCanRefNoMsg(str2);
				objform.setCanRefNo(dupCandidateRefNo.toString());
			}
			if(str!=null && !str.isEmpty()){
				objform.setExceptedCanRefNo(str);
			}
				
		}	
		if(boList!=null && !boList.isEmpty()){
			isUpdate=transaction.updateCandidatePGIDetails(boList);
		}
		
		return isUpdate;
	}

	public String removeFileExtension(String fileName)
	{ 
			if(null != fileName && fileName.contains("."))
			{
			return fileName.substring(0, fileName.lastIndexOf("."));
			}
		return fileName;
	}
}
