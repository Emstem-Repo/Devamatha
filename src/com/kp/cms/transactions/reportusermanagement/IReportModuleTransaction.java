package com.kp.cms.transactions.reportusermanagement;

import java.util.List;

import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.forms.reportusermanagement.ReportModuleForm;

public interface IReportModuleTransaction {
	public ReportModules isModuleNameDuplcated(ReportModules oldModule, Boolean nameChanged, Boolean reActivate) throws Exception;
	public boolean addModules(ReportModules modules, String mode) throws Exception;
	public List<ReportModules> getModule() throws Exception;	
	public boolean deleteModule(int id,ReportModuleForm moduleForm,Boolean activate) throws Exception;
	public boolean ReactivateModule(int id, ReportModules modules, boolean b) throws Exception;
}
