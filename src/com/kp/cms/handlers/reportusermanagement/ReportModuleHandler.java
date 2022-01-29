package com.kp.cms.handlers.reportusermanagement;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.reportusermanagement.ReportModuleForm;
import com.kp.cms.helpers.reportusermanagement.ReportModuleHelper;
import com.kp.cms.to.usermanagement.ModuleTO;
import com.kp.cms.transactions.reportusermanagement.IReportModuleTransaction;
import com.kp.cms.transactionsimpl.reportusermanagement.ReportModuleTransactionImpl;

public class ReportModuleHandler {
	private static Log log = LogFactory.getLog(ReportModuleHandler.class);
	public static volatile ReportModuleHandler moduleHandler = null;

	public static ReportModuleHandler getInstance() {
		if (moduleHandler == null) {
			moduleHandler = new ReportModuleHandler();
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
		IReportModuleTransaction iReportModuleTransaction = ReportModuleTransactionImpl.getInstance();
		List<ReportModules> moduleList = iReportModuleTransaction.getModule();
		List<ModuleTO> moduleToList = ReportModuleHelper.getInstance().copyModuleBosToTos(moduleList);
		log.debug("leaving getModule");
		return moduleToList;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addModule(ReportModuleForm moduleForm, String mode) throws Exception {
		log.debug("inside addModule");
		IReportModuleTransaction iReportModuleTransaction = ReportModuleTransactionImpl.getInstance();
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
				ReportModules duplmodules = ReportModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
				duplmodules = iReportModuleTransaction.isModuleNameDuplcated(duplmodules, true, false/*reActivate*/);
				if (duplmodules != null	&& duplmodules.getIsActive() == true) {
					throw new DuplicateException();
				} 
			}
			if (!originalPosNotChanged)
			{
				ReportModules duplPosmodules = ReportModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
				duplPosmodules = iReportModuleTransaction.isModuleNameDuplcated(duplPosmodules, false, false/*reActivate*/);
				if (duplPosmodules != null && duplPosmodules.getIsActive() == true) {
					throw new DuplicateException();
				} 
			}
			
			ReportModules reActivatemodules = ReportModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
			reActivatemodules = iReportModuleTransaction.isModuleNameDuplcated(reActivatemodules, false, true);
			if (reActivatemodules != null && reActivatemodules.getIsActive() == false) {
				moduleForm.setDuplId(reActivatemodules.getId());
				throw new ReActivateException();
			}			
		}
		

		boolean isAdded = false;
		ReportModules modules = ReportModuleHelper.getInstance().populateModuleDataFormForm(moduleForm, mode);
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

		isAdded = iReportModuleTransaction.addModules(modules, mode);
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

	public boolean deleteModule(int id, ReportModuleForm moduleForm,Boolean activate) throws Exception {
		log.debug("inside deleteModule");
		IReportModuleTransaction iReportModuleTransaction = ReportModuleTransactionImpl.getInstance();
		boolean result = iReportModuleTransaction.deleteModule(id,moduleForm,false);
		log.debug("leaving deleteModule");
		return result;
	}
	/**
	 * @param id
	 * @param moduleForm
	 * @param activate
	 * @return
	 * @throws Exception
	 */
	public boolean ReactivateModule(int id, ReportModuleForm moduleForm,Boolean activate) throws Exception {
		log.debug("inside deleteModule");
		IReportModuleTransaction iReportModuleTransaction = ReportModuleTransactionImpl.getInstance();
		ReportModules modules = new ReportModules();
		modules.setId(id);
		modules.setDisplayName(moduleForm.getName());
		modules.setIsActive(activate);
		boolean result = iReportModuleTransaction.ReactivateModule(id,modules,activate);
		log.debug("leaving deleteModule");
		return result;
	}

}

