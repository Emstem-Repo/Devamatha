package com.kp.cms.transactions.admission;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admission.PromoteBioData;

public interface IPromoteBioDataUploadTransaction {

	public boolean uploadPromoteBioData(List<PromoteBioData> bioDataUploadList)throws Exception;
	public Map<Integer, String> getCourseMap() throws Exception;
	public List<PromoteBioData> getProBioDataDetails(StringBuffer query) throws Exception;

}
