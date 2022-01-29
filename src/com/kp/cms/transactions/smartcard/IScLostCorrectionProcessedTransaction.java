package com.kp.cms.transactions.smartcard;

import java.util.List;

import com.kp.cms.bo.smartcard.ScLostCorrection;
import com.kp.cms.forms.smartcard.ScLostCorrectionProcessedForm;

public interface IScLostCorrectionProcessedTransaction {
	
	public List<ScLostCorrection> getDetailsList(ScLostCorrectionProcessedForm scProcessedForm)throws Exception;
	
	boolean addScLostCorrectionProcessed(ScLostCorrectionProcessedForm scProcessedForm) throws Exception;

	public boolean editRemarks(ScLostCorrectionProcessedForm scProcessedForm) throws Exception;

}
