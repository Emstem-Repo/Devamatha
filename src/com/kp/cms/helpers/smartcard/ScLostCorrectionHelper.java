package com.kp.cms.helpers.smartcard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.CurriculumScheme;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionForm;
import com.kp.cms.forms.smartcard.ScLostCorrectionViewForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.smartcard.ScLostCorrectionTo;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class ScLostCorrectionHelper {
	
	private static volatile ScLostCorrectionHelper scLostCorrectionHelper = null;
	private static final Log log = LogFactory.getLog(ScLostCorrectionHelper.class);
	
	private ScLostCorrectionHelper() {
		
	}
	
	public static ScLostCorrectionHelper getInstance() {

		if (scLostCorrectionHelper == null) {
			scLostCorrectionHelper = new ScLostCorrectionHelper();
		}
		return scLostCorrectionHelper;
	}
	
	
	/**
	 * @param historyList
	 * @param scLostCorrectionForm 
	 * @return
	 * @throws Exception
	 */
	public List<ScLostCorrectionTo> pupulateScHistoryBOtoTO(List<ScLostCorrection> historyList)throws Exception {
		
		ScLostCorrectionTo scLostCorrectionTo = null;

		List<ScLostCorrectionTo> newHistoryList = new ArrayList<ScLostCorrectionTo>();
		if (historyList != null && !historyList.isEmpty()) {
			Iterator<ScLostCorrection> iterator = historyList.iterator();
			while (iterator.hasNext()) {
				ScLostCorrection scLostCorrection = iterator.next();
				scLostCorrectionTo = new ScLostCorrectionTo();
				if (scLostCorrection.getId() != 0) {
					scLostCorrectionTo.setId(scLostCorrection.getId());
					scLostCorrectionTo.setStatus(scLostCorrection.getStatus());
					if(scLostCorrection.getStatus().equalsIgnoreCase("Issued")){
						scLostCorrectionTo.setIssuedDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scLostCorrectionTo.setCancelledDate(null);
					}
					else if(scLostCorrection.getStatus().equalsIgnoreCase("Cancelled")){
						scLostCorrectionTo.setCancelledDate(CommonUtil.formatDates(scLostCorrection.getLastModifiedDate()));
						scLostCorrectionTo.setIssuedDate(null);
					}
					scLostCorrectionTo.setCardType(scLostCorrection.getCardReason());
					scLostCorrectionTo.setAppliedOn(CommonUtil.formatDates(scLostCorrection.getDateOfSubmission()));
					scLostCorrectionTo.setOldSmartCardNo(scLostCorrection.getOldSmartCardNum());
					if(scLostCorrection.getRemarks()!=null && !scLostCorrection.getRemarks().isEmpty())
						scLostCorrectionTo.setRemarks(scLostCorrection.getRemarks());
					
					
					newHistoryList.add(scLostCorrectionTo);
				}
			}
		}
		return newHistoryList;
	}
	
	
	/**
	 * @param scForm
	 * @return
	 * @throws Exception
	 */
	public static String getSelectionSearchCriteria(ScLostCorrectionForm scForm)
	throws Exception {
		
		StringBuilder searchCriteria = new StringBuilder("from Student s where s.isActive=1");
		
		if(scForm.getRegNo()!=null && !scForm.getRegNo().isEmpty()){
			searchCriteria.append(" and s.registerNo = '"+scForm.getRegNo()+"'");
		}
		if(scForm.getLastFiveDigitAccNo()!=null && !scForm.getLastFiveDigitAccNo().isEmpty()){
			searchCriteria.append(" and s.bankAccNo like '%"+scForm.getLastFiveDigitAccNo()+"'");
		}
		
		log.info("exit of getSelectionSearchCriteria of sclost class.");
		return searchCriteria.toString();
	}
	
	

}
