package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.SMSTemplate;

public interface ISMSTemplateTransaction {
	public boolean saveSMSTemplate(SMSTemplate smsTemplate) throws Exception;
	public List<SMSTemplate> getSMSTemplates(int id) throws Exception;
	public boolean deleteSMSTemplate(SMSTemplate smsTemplate) throws Exception;
	public List<SMSTemplate> getDuplicateCheckList(int courseId, String templateName) throws Exception;
	public int getProgrameId(int courseId) throws Exception;
	public List<SMSTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception;
	public  List<SMSTemplate> getSMSTemplate(int courseId, String templateName,
			int programId) throws Exception;
	public List<SMSTemplate> getDuplicateCheckList(String templateName)throws Exception;
}
