package com.kp.cms.transactions.usermanagement;

import java.util.List;

import com.kp.cms.bo.admin.Modules;
import com.kp.cms.forms.usermanagement.ModuleForm;

public interface IModuleTransaction {
	public Modules isModuleNameDuplcated(Modules oldModule, Boolean nameChanged, Boolean reActivate) throws Exception;
	public boolean addModules(Modules modules, String mode) throws Exception;
	public List<Modules> getModule() throws Exception;	
	public boolean deleteModule(int id,ModuleForm moduleForm,Boolean activate) throws Exception;

}
