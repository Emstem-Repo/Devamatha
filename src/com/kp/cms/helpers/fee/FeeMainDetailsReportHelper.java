package com.kp.cms.helpers.fee;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.FeesFeeDetails;
import com.kp.cms.to.fee.FeeMainDetailsTO;
import com.kp.cms.transactions.fee.IFeeMainDetailsTxn;
import com.kp.cms.transactionsimpl.fee.FeeMainDetailsReportTxnImpl;
import com.kp.cms.utilities.CommonUtil;

public class FeeMainDetailsReportHelper {
	private static volatile FeeMainDetailsReportHelper feeMainDetailsHelper=null;
	public static FeeMainDetailsReportHelper getInstance() {
		   if(feeMainDetailsHelper == null ){
			   feeMainDetailsHelper = new FeeMainDetailsReportHelper();
			   return feeMainDetailsHelper;
		   }
		   return feeMainDetailsHelper;
	}
	public List<FeeMainDetailsTO> convertFeeMainDetailsBoToTO(List<AdmFeeMain> feeMainList)throws Exception{
    	List<FeeMainDetailsTO> feeMainDetailsToList=new ArrayList<FeeMainDetailsTO>();
        Iterator<AdmFeeMain> itr=feeMainList.iterator();
        while(itr.hasNext()){
        	AdmFeeMain feeMain=itr.next();
        	FeeMainDetailsTO feeMainDetailsTO=setValuesToFeeMainDetailsTO(feeMain);
        	IFeeMainDetailsTxn transaction=FeeMainDetailsReportTxnImpl.getInstance();
        	int size=0;
        	if(feeMain.getBillNo()!=null && feeMain.getBillNo()!=0){
        	List<FeesFeeDetails> details=transaction.getFeeDetailsWithBillNo(feeMain.getBillNo());
        	Iterator<FeesFeeDetails> itr1=details.iterator();
        	size=details.size();
        	while(itr1.hasNext()){
        		FeesFeeDetails feeDetails=itr1.next();
        		
            if(size>1){
            	FeeMainDetailsTO feeMainTO=setValuesToFeeMainDetailsTO(feeMain);
        	   if(feeDetails.getAddFee22()!=0){
        		   feeMainTO.setAddFee22(feeDetails.getAddFee22());
        	   } 
        	    if(feeDetails.getAddFee993()!=0){
        	    	feeMainTO.setAddFee993(feeDetails.getAddFee993());
        	    }
        	    if(feeDetails.getFeesCode()!=null && !feeDetails.getFeesCode().isEmpty()){
        	    	feeMainTO.setFeesCode(feeDetails.getFeesCode());
        	    }
        	
        	    feeMainDetailsToList.add(feeMainTO);
            }else{
            	if(feeDetails.getAddFee22()!=0){
            		feeMainDetailsTO.setAddFee22(feeDetails.getAddFee22());
         	   } 
         	    if(feeDetails.getAddFee993()!=0){
         	    	feeMainDetailsTO.setAddFee993(feeDetails.getAddFee993());
         	    }
         	    if(feeDetails.getFeesCode()!=null && !feeDetails.getFeesCode().isEmpty()){
         	    	feeMainDetailsTO.setFeesCode(feeDetails.getFeesCode());
         	    }
            }
            	
        	}
        	}
        	if(size==1 || size==0)
        		feeMainDetailsToList.add(feeMainDetailsTO);
        }
    	return feeMainDetailsToList;
    }
	 public FeeMainDetailsTO setValuesToFeeMainDetailsTO(AdmFeeMain feeMain){
		 FeeMainDetailsTO feeMainTo=new FeeMainDetailsTO();
		 feeMainTo.setAcademicYear(feeMain.getAcademicYear());
	    	if(feeMain.getApplnRegNo()!=null && !feeMain.getApplnRegNo().isEmpty()){
	    		feeMainTo.setApplnRegNo((feeMain.getApplnRegNo()));
	    	}
	    	if(feeMain.getDate()!=null){
	    		String date=CommonUtil.ConvertStringToSQLDate2(feeMain.getDate().toString());
	    		feeMainTo.setDate1(date);
	    	}
	    	if(feeMain.getBillNo()!=null && feeMain.getBillNo()!=0)
	    		feeMainTo.setBillNo(feeMain.getBillNo());
	    	if(feeMain.getTime()!=null && !feeMain.getTime().isEmpty())
	    		feeMainTo.setTime(feeMain.getTime());
	    	if(feeMain.getClasses()!=null && !feeMain.getClasses().isEmpty())
	    		feeMainTo.setClasses(feeMain.getClasses());
	    	if(feeMain.getNormalFee()!=null){
	    		feeMainTo.setNormalFee(feeMain.getNormalFee().toString());
	    	}
	    	if(feeMain.getMaintFee()!=null)
	    		feeMainTo.setMaintFee(feeMain.getMaintFee().toString());
	    	if(feeMain.getExcemption()!=null){
	    		if(feeMain.getExcemption())
	    			feeMainTo.setExcemption("Yes");
	    		else
	    			feeMainTo.setExcemption("No");
	    	}
	    	if(feeMain.getMoneyPaid()!=null){
	    		if(feeMain.getMoneyPaid())
	    			feeMainTo.setMoneyPaid("Yes");
	    		else
	    			feeMainTo.setMoneyPaid("No");
	    	}
	    	if(feeMain.getUserCode()!=null && !feeMain.getUserCode().isEmpty())
	    		feeMainTo.setUserCode(feeMain.getUserCode());
	    	if(feeMain.getMaintConcesion()!=null)
	    		feeMainTo.setMaintConcesion(feeMain.getMaintConcesion().toString());
	    	if(feeMain.getConcDesc()!=null && !feeMain.getConcDesc().isEmpty())
	    		feeMainTo.setConcDesc(feeMain.getConcDesc());
	    	return feeMainTo;
	    }
}
