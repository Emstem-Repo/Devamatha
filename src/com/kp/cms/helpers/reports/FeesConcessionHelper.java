package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.FeesConcessionReportForm;
import com.kp.cms.to.reports.ClassFeeConcessionReportTO;
import com.kp.cms.to.reports.FeeConcessionReportTO;
import com.kp.cms.utilities.CommonUtil;

public class FeesConcessionHelper {
	private static final Log log = LogFactory.getLog(FeesConcessionHelper.class);
	public static volatile FeesConcessionHelper feesConcessionHelper = null;

	public static FeesConcessionHelper getInstance() {
		if (feesConcessionHelper == null) {
			feesConcessionHelper = new FeesConcessionHelper();
			return feesConcessionHelper;
		}
		return feesConcessionHelper;
	}
	
	/**
	 * 
	 * @param concList
	 * @return
	 * @throws Exception
	 */
	public List<FeeConcessionReportTO> copyBosToTO(List<Object[]> concList, FeesConcessionReportForm concessionReportForm) throws Exception {
		log.debug("inside copyBosToTO");
		FeeConcessionReportTO feeConcessionReportTO;
//		FeePaymentDetail feePaymentDetail;
		Iterator<Object[]> itr = concList.iterator();
		Map<String, List<FeeConcessionReportTO>> classConcListTOMap = new HashMap<String, List<FeeConcessionReportTO>>();
		String className;
		while (itr.hasNext()){
			feeConcessionReportTO = new FeeConcessionReportTO();
			Object[] feePaymentDetail = (Object[]) itr.next();
			if(feePaymentDetail[5]!= null){
				className = (String) feePaymentDetail[5];
			}
			else{
				continue;
			}
			
			List<FeeConcessionReportTO> classConcToList = null;
			if(classConcListTOMap.containsKey(className)){
				classConcToList = classConcListTOMap.get(className.trim());
			}else
			{
				classConcToList = new ArrayList<FeeConcessionReportTO>();
			}
			feeConcessionReportTO.setClassName(className);
			feeConcessionReportTO.setSlNo(classConcToList.size() + 1);
			if(feePaymentDetail[1]!= null){
				feeConcessionReportTO.setStudentName( (String)feePaymentDetail[1]);
			}
			
			if(feePaymentDetail[2]!= null){
				String formattedString=CommonUtil.ConvertStringToDateFormat(feePaymentDetail[2].toString(), "yyyy-mm-dd", "dd/mm/yyyy");
//				feeConcessionReportTO.setDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(feePaymentDetail[2].toString()), CMSConstants.SOURCE_DATE ,CMSConstants.DEST_DATE));
				feeConcessionReportTO.setDate(formattedString);
			}
			
			if(feePaymentDetail[3]!= null){
				feeConcessionReportTO.setApplNo( (String)feePaymentDetail[3]);
			}
			if(feePaymentDetail[4]!= null){
				feeConcessionReportTO.setRegNo((String)feePaymentDetail[4]);
			} 
			if(feePaymentDetail[5]!= null){
				feeConcessionReportTO.setClassName((String)feePaymentDetail[5]);
			} 
			if(feePaymentDetail[6]!= null){
				feeConcessionReportTO.setVoucherNo(feePaymentDetail[6].toString());
			} 
			if(feePaymentDetail[7]!= null){
				feeConcessionReportTO.setTotalFees(Double.parseDouble(feePaymentDetail[7].toString()));
			} 
			if(feePaymentDetail[0]!= null){
				feeConcessionReportTO.setConcessionAmt(Double.parseDouble(feePaymentDetail[0].toString()));
			} 
			if(feePaymentDetail[8]!= null){
				if(feePaymentDetail[0].toString().equalsIgnoreCase("true")){
					feeConcessionReportTO.setIsCancelled("Yes");
				}
			}
			classConcToList.add(feeConcessionReportTO);
			classConcListTOMap.put(className.trim(), classConcToList);
		}
		
		Iterator<String> keyIterator = classConcListTOMap.keySet().iterator();
		ClassFeeConcessionReportTO classFeeConcessionReportTO; 
		List<ClassFeeConcessionReportTO> classWithConcList = new ArrayList<ClassFeeConcessionReportTO>();
		List<FeeConcessionReportTO> classwiseTotalList = new ArrayList<FeeConcessionReportTO>();
		while (keyIterator.hasNext()) {
			String string = (String) keyIterator.next();
			classwiseTotalList.addAll(classConcListTOMap.get(string));
			classFeeConcessionReportTO= new ClassFeeConcessionReportTO();
			classFeeConcessionReportTO.setClassName(string);
			classFeeConcessionReportTO.setConcessionList(classConcListTOMap.get(string));
			classWithConcList.add(classFeeConcessionReportTO);
		
		}		
		
		concessionReportForm.setClassFeeConcessionList(classWithConcList);
		return classwiseTotalList;
	}

}
