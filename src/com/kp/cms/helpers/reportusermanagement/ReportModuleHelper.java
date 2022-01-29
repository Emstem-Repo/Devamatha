package com.kp.cms.helpers.reportusermanagement;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Modules;
import com.kp.cms.bo.admin.ReportModules;
import com.kp.cms.forms.reportusermanagement.ReportModuleForm;
import com.kp.cms.to.usermanagement.ModuleTO;

public class ReportModuleHelper {
	public static volatile ReportModuleHelper moduleHelper = null;
	private static Log log = LogFactory.getLog(ReportModuleHelper.class);

	public static ReportModuleHelper getInstance() {
		if (moduleHelper == null) {
			moduleHelper = new ReportModuleHelper();
			return moduleHelper;
		}
		return moduleHelper;
	}

	/**
	 * 
	 * @param moduleList
	 *            this will copy the module BO to TO
	 * @return moduleToList having moduleTO objects.
	 */
	public List<ModuleTO> copyModuleBosToTos(List<ReportModules> moduleList) {
		log.debug("inside copyModuleBosToTos");
		List<ModuleTO> moduleToList = new ArrayList<ModuleTO>();
		Iterator<ReportModules> i = moduleList.iterator();
		ReportModules modules;
		ModuleTO moduleTO;
		while (i.hasNext()) {
			moduleTO = new ModuleTO();
			modules = (ReportModules) i.next();
			moduleTO.setId(modules.getId());
			moduleTO.setName(modules.getDisplayName());
			moduleTO.setPosition(modules.getPosition());
			moduleTO.setIsActive(modules.getIsActive());
			moduleToList.add(moduleTO);
		}
		log.debug("leaving copyModuleBosToTos");
		return moduleToList;
	}

	/**
	 * 
	 * @param moduleForm
	 *            creates BO from moduleForm
	 * 
	 * @return Module BO object
	 */

	public ReportModules populateModuleDataFormForm(ReportModuleForm moduleForm, String mode) throws Exception {
		log.debug("inside populateModuleDataFormForm");
		ReportModules modules = new ReportModules();
		if (!mode.equalsIgnoreCase("Add")) {
			modules.setId(Integer.parseInt(moduleForm.getId()));
		}
		modules.setDisplayName(moduleForm.getName().trim());
		if((moduleForm.getPosition()!= null) && !moduleForm.getPosition().isEmpty() ) {
			modules.setPosition(Integer.parseInt(moduleForm.getPosition()));
		}
		modules.setIsActive(true);
		log.debug("leaving populateModuleDataFormForm");
		return modules;
	}

}
