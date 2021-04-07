package com.kp.cms.handlers.reports;

import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.reports.RegNoOrRollNoInStickerForm;
import com.kp.cms.helpers.reports.RegNoOrRollNoInStickerHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.reports.IRegNoOrRollNoInStickerTransaction;
import com.kp.cms.transactionsimpl.reports.RegNoOrRollNoInStickerTxnImpl;

public class RegNoOrRollNoInStickerHandler {
	private static final Log log = LogFactory.getLog(RegNoOrRollNoInStickerHandler.class);
	public static volatile RegNoOrRollNoInStickerHandler regNoOrRollNoInStickerHandler = null;

	public static RegNoOrRollNoInStickerHandler getInstance() {
		if (regNoOrRollNoInStickerHandler == null) {
			regNoOrRollNoInStickerHandler = new RegNoOrRollNoInStickerHandler();
			return regNoOrRollNoInStickerHandler;
		}
		return regNoOrRollNoInStickerHandler;
	}
	/**
	 * 
	 * @param stickerForm
	 * @throws Exception
	 */
	
	public void getRegNoRollNoPrint(RegNoOrRollNoInStickerForm stickerForm) throws Exception {
		log.debug("inside getRegNoRollNoPrint");
		IRegNoOrRollNoInStickerTransaction iPrintTran = RegNoOrRollNoInStickerTxnImpl.getInstance();
		List<Student> stuList = null;
		Boolean isRollNo = false;
	
		if("true".equalsIgnoreCase(stickerForm.getIsRollNo())){
			isRollNo = true;
			stuList = iPrintTran.getRequiredRollNos(stickerForm.getRegNoFrom(), stickerForm.getRegNoTo(), Integer.parseInt(stickerForm.getProgramTypeId()));
		}
		else
		{
			stuList = iPrintTran.getRequiredRegdNos(stickerForm.getRegNoFrom(), stickerForm.getRegNoTo(), Integer.parseInt(stickerForm.getProgramTypeId()));
		}
		
		List<StudentTO> studentList = RegNoOrRollNoInStickerHelper.getInstance().copyBosToList(stuList, isRollNo);
		stickerForm.setStudentList(studentList);
	}
	  	
	
}
