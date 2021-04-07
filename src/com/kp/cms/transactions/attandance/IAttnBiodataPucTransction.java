package com.kp.cms.transactions.attandance;

import java.util.List;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.forms.attendance.AttnBiodataPucForm;

public interface IAttnBiodataPucTransction {

	public boolean uploadAttnBioData(List<AttnBiodataPuc> biodataPuc, AttnBiodataPucForm attnBioDataForm)throws Exception;

}
