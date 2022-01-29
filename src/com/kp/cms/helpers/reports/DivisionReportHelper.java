package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.FeeAccount;
import com.kp.cms.bo.admin.FeeDivision;
import com.kp.cms.forms.reports.DivisionReportForm;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeDivisionTO;

public class DivisionReportHelper {
	private static final Log log = LogFactory.getLog(DivisionReportHelper.class);
	public static volatile DivisionReportHelper divisionReportHelper = null;

	public static DivisionReportHelper getInstance() {
		if (divisionReportHelper == null) {
			divisionReportHelper = new DivisionReportHelper();
			return divisionReportHelper;
		}
		return divisionReportHelper;
	}

	public List<FeeDivisionTO> copyBosToTO(List<FeeDivision> divisionList, DivisionReportForm divisionReportForm) throws Exception {
		log.debug("inside copyBosToTO");
		FeeAccountTO feeAccountTO;
		FeeDivision feeDivision;
		FeeAccount feeAccount;
		Iterator<FeeDivision> itr= divisionList.iterator();
		List<FeeDivisionTO> divisionTOList = new ArrayList<FeeDivisionTO>();
		List<FeeAccountTO> accountTOList;
		int count = 0;
		while (itr.hasNext()){
			feeDivision = itr.next();
			FeeDivisionTO divisionTO = new FeeDivisionTO();
			divisionTO.setId(feeDivision.getId());
			divisionTO.setName(feeDivision.getName());
			Iterator<FeeAccount> itr1 = feeDivision.getFeeAccounts().iterator();
			accountTOList = new ArrayList<FeeAccountTO>(); 
			while(itr1.hasNext()){
				feeAccount = itr1.next();
				if(!feeAccount.getIsActive()){
					continue;
				}
				feeAccountTO = new FeeAccountTO();
				feeAccountTO.setId(feeAccount.getId());
				feeAccountTO.setCode(feeAccount.getCode());
				accountTOList.add(feeAccountTO);
			}
				if(accountTOList.size() > count){
					count = accountTOList.size();
				}
			divisionReportForm.setMaxAccCount(count);
			divisionTO.setFeeAccountList(accountTOList);
			divisionTOList.add(divisionTO);
		}
		log.debug("exit copyBosToTO");
		return divisionTOList;
	}

}
