package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.GroupTemplate;

public interface ITemplatePassword {
	
	public boolean saveGroupTemplate(GroupTemplate groupTemplate) throws Exception;
	public List<GroupTemplate> getGroupTemplates(int id,String programTypeId,String programId,String templateName) throws Exception;
	public boolean deleteGroupTemplate(GroupTemplate groupTemplate) throws Exception;
	public List<GroupTemplate> getDuplicateCheckList(int courseId, String templateName) throws Exception;
	public int getProgrameId(int courseId) throws Exception;
	public List<GroupTemplate> checkDuplicate(int courseId, String templateName, int programId) throws Exception;
	public  List<GroupTemplate> getGroupTemplate(int courseId, String templateName,
			int programId) throws Exception;
	public List<GroupTemplate> getDuplicateCheckList(String templateName)throws Exception;
	public List<GroupTemplate> getTemplateForNRI(String templateName) throws Exception;
//	public List<CertificateDetailsTemplate> checkDuplicateCertificateTemplate(int certId,String certName) throws Exception;
	public List<GroupTemplate> getGroupTemplateForPT(int courseId, String templateName, int programId,  int ProgramTypeId)	throws Exception;
	
}
