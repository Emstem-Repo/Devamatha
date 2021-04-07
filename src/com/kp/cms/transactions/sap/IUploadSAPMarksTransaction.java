package com.kp.cms.transactions.sap;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.sap.UploadSAPMarksBo;
import com.kp.cms.forms.sap.UploadSAPMarksForm;

public interface IUploadSAPMarksTransaction {
	public boolean saveUploadSAPMarks(Map<Integer,UploadSAPMarksBo> boList,UploadSAPMarksForm objform)throws Exception;
	public	Map<String,Integer> getStudentIdByStudentRegNum(String year,List<String> regNumList) throws Exception;
}
