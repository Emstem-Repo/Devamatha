package com.kp.cms.handlers.attendance;

import java.util.List;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.forms.attendance.AttnBiodataPucForm;
import com.kp.cms.helpers.attendance.AttnBiodataPucHelper;
import com.kp.cms.to.attendance.AttnBioDataPucTo;
import com.kp.cms.transactions.attandance.IAttnBiodataPucTransction;
import com.kp.cms.transactionsimpl.attendance.AttnBiodataPucTxnImpl;

public class AttnBiodataPucHandler {
	private static volatile AttnBiodataPucHandler attnBiodataPucHandler = null;
	public static AttnBiodataPucHandler getInstance(){
		if(attnBiodataPucHandler == null){
			attnBiodataPucHandler = new AttnBiodataPucHandler();
			return attnBiodataPucHandler;
		}
		return attnBiodataPucHandler;
	}
	/**
	 * @param bioDataList
	 * @param attnBioDataForm 
	 * @return
	 * @throws Exception
	 */
	public boolean attnBioDataUpload(List<AttnBioDataPucTo> bioDataList, AttnBiodataPucForm attnBioDataForm)throws Exception {
		IAttnBiodataPucTransction transaction = AttnBiodataPucTxnImpl.getInstance();
		List<AttnBiodataPuc> biodataPuc = AttnBiodataPucHelper.getInstance().convertToTOBo(bioDataList);
		return transaction.uploadAttnBioData(biodataPuc,attnBioDataForm);
	}
	
}
