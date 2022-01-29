package com.kp.cms.handlers.usermanagement;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Modules;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.usermanagement.ModuleForm;
import com.kp.cms.helpers.usermanagement.ModuleHelper;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.transactions.usermanagement.IModuleTransaction;
import com.kp.cms.transactionsimpl.usermanagement.ModuleTransactionImpl;

public class ModuleHandler {
	private static Log log = LogFactory.getLog(ModuleHandler.class);
	public static volatile ModuleHandler moduleHandler = null;

	public static ModuleHandler getInstance() {
		if (moduleHandler == null) {
			moduleHandler = new ModuleHandler();
			return moduleHandler;
		}
		return moduleHandler;
	}

	/**
	 * 
	 * @return list of moduleTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	public List<ModuleTO> getModule() throws Exception {
		log.debug("inside getModule");
		IModuleTransaction iModuleTransaction = ModuleTransactionImpl.getInstance();
		List<Modules> moduleList = iModuleTransaction.getModule();
		List<ModuleTO> moduleToList = ModuleHelper.getInstance().copyModuleBosToTos(moduleList);
		log.debug("leaving getModule");
		return moduleToList;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addModule(ModuleForm moduleForm, String mode) throws Exception {
		log.debug("inside addModule");
		IModuleTransaction iModuleTransaction = ModuleTransactionImpl.getInstance();
		Boolean originalModuleNameNotChanged = false;
		Boolean originalPosNotChanged = false;
		//originalchanged variable used in update. if original is not changed then no need to check duplicate
		
		int pos = 0;
		int origPos = 0;

		String modulename = "";
		String origModuleName = "";
		
		modulename = moduleForm.getName();
		origModuleName = moduleForm.getOrigName();
		
		if((moduleForm.getPosition()!= null) && !moduleForm.getPosition().isEmpty()){
			pos = Integer.parseInt(moduleForm.getPosition());
		}
		if((moduleForm.getOrigPos()!= null) && !moduleForm.getOrigPos().isEmpty()) {
			origPos = Integer.parseInt(moduleForm.getOrigPos());
		}
		if (pos == origPos) {
			originalPosNotChanged = true;
		}
		if (modulename.trim().equalsIgnoreCase(origModuleName.trim())) {
			originalModuleNameNotChanged = true;
		}
		if (mode.equalsIgnoreCase("Add")) {
			originalModuleNameNotChanged = false;
			originalPosNotChanged = false;
		}

		if (!originalModuleNameNotChanged || !originalPosNotChanged) {
			if(!originalModuleNameNotChanged){
				Modules duplmodules = ModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
				duplmodules = iModuleTransaction.isModuleNameDuplcated(duplmodules, true, false/*reActivate*/);
				if (duplmodules != null
						&& duplmodules.getIsActive() == true) {
					throw new DuplicateException();
				} 
			}
			
			
			if (!originalPosNotChanged)
			{
				Modules duplPosmodules = ModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
				duplPosmodules = iModuleTransaction.isModuleNameDuplcated(duplPosmodules, false, false/*reActivate*/);
				if (duplPosmodules != null
						&& duplPosmodules.getIsActive() == true) {
					throw new DuplicateException();
				} 
			}
			/*
			Modules reActivatemodules = new Modules();
			reActivatemodules = ModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
			reActivatemodules = iModuleTransaction.isModuleNameDuplcated(reActivatemodules, false, true);
			if (reActivatemodules != null
					&& reActivatemodules.getIsActive() == false) {
				moduleForm.setDuplId(reActivatemodules
						.getId());
				throw new ReActivateException();
			}	*/		
		}
		

		boolean isAdded = false;
		Modules modules = ModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
		Employee employee = new Employee();
		employee.setId(1);

		if ("Add".equalsIgnoreCase(mode)) {
			modules.setCreatedDate(new Date());
			modules.setLastModifiedDate(new Date());
			modules.setModifiedBy(moduleForm.getUserId());
			modules.setCreatedBy(moduleForm.getUserId());
		} else // edit
		{
			modules.setLastModifiedDate(new Date());
			modules.setModifiedBy(moduleForm.getUserId());
		}

		isAdded = iModuleTransaction.addModules(modules, mode);
		log.debug("leaving addModule");
		return isAdded;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 * @throws Exception
	 */

	public boolean deleteModule(int id, ModuleForm moduleForm,Boolean activate) throws Exception {
		log.debug("inside deleteModule");
		IModuleTransaction iModuleTransaction = ModuleTransactionImpl.getInstance();
//		Modules modules = new Modules();
//		modules.setId(id);
//		modules.setDisplayName(moduleName);
		boolean result = iModuleTransaction.deleteModule(id,moduleForm,false);
		log.debug("leaving deleteModule");
		return result;
	}


}

