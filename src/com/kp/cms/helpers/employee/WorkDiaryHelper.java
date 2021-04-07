package com.kp.cms.helpers.employee;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.EmpWorkDairy;
import com.kp.cms.forms.employee.WorkDiaryForm;
import com.kp.cms.utilities.CommonUtil;

public class WorkDiaryHelper {
	private static final Log log = LogFactory.getLog(WorkDiaryHelper.class);
	public static volatile WorkDiaryHelper workDiaryHelper = null;

	public static WorkDiaryHelper getInstance() {
		if (workDiaryHelper == null) {
			workDiaryHelper = new WorkDiaryHelper();
			return workDiaryHelper;
		}
		return workDiaryHelper;
	}

	/**
	 * 
	 * @param workDiaryForm
	 * @return
	 * @throws Exception
	 */
	public EmpWorkDairy copyDataFromFormToBO(WorkDiaryForm workDiaryForm)
			throws Exception {
		log.debug("inside copyDataFromFormToBO");
		EmpWorkDairy empDairy = new EmpWorkDairy();
		empDairy.setDate(CommonUtil.ConvertStringToSQLDate(workDiaryForm
				.getDate()));

		if (workDiaryForm.getComments() != null
				&& !workDiaryForm.getComments().trim().isEmpty()) {
			empDairy.setComments(workDiaryForm.getComments());
		}
		if (workDiaryForm.getThefile() != null
				&& workDiaryForm.getThefile().getFileData() != null) {
			empDairy.setDocument(workDiaryForm.getThefile().getFileData());
		}

		empDairy.setIsActive(true);
		empDairy.setCreatedBy(workDiaryForm.getUserId());
		empDairy.setModifiedBy(workDiaryForm.getUserId());
		empDairy.setCreatedDate(new Date());
		empDairy.setLastModifiedDate(new Date());
		log.debug("leaving copyDataFromFormToBO");
		return empDairy;
	}
}
