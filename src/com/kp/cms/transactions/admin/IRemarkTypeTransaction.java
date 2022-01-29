package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.RemarkType;
import com.kp.cms.forms.admin.RemarkTypeForm;


public interface IRemarkTypeTransaction {
	public boolean addRemarkType(RemarkType remarkType, String mode) throws Exception;
	public List<RemarkType> getRemarks() throws Exception;
	public boolean deleteRemarks(int id, Boolean activate, RemarkTypeForm remarkTypeForm) throws Exception;
	public RemarkType isRemarkTypeDuplcated(String remType, int remarkId) throws Exception;
	public RemarkType getRemarkTypeById(int id)throws Exception;
	
}
