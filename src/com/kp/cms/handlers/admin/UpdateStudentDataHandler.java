package com.kp.cms.handlers.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.forms.admin.UpdateStudentDataForm;
import com.kp.cms.helpers.admin.UpdateStudentDataHelper;
import com.kp.cms.to.admin.UpdateStudentDataTO;
import com.kp.cms.transactions.admin.IUpdateStudentDataTransaction;
import com.kp.cms.transactionsimpl.admin.UpdateStudentDataTransactionImpl;

public class UpdateStudentDataHandler {
	private static UpdateStudentDataHandler updateStudentDataHandler = null;
	private static final Log log = LogFactory.getLog(UpdateStudentDataHandler.class);
	
	public static UpdateStudentDataHandler getInstance() {
		if(updateStudentDataHandler == null) {
			updateStudentDataHandler = new UpdateStudentDataHandler();
			return updateStudentDataHandler;
		}
		return updateStudentDataHandler;
	}
	
	IUpdateStudentDataTransaction txn = UpdateStudentDataTransactionImpl.getInstance();

	public List<UpdateStudentDataTO> getStudentLIst(UpdateStudentDataForm updateStudentDataForm) throws Exception {
		List stuList = txn.getStudentDetailsList(updateStudentDataForm);
		return UpdateStudentDataHelper.getInstance().convertBoToTO(stuList);
	}

	public List<UpdateStudentDataTO> getPrevStudentList(UpdateStudentDataForm updateStudentDataForm)  throws Exception{
		List prevStudentList = txn.getPrevStudent(updateStudentDataForm);
		return UpdateStudentDataHelper.getInstance().convertBOToTO1(prevStudentList);
	}

	public String getClassName(UpdateStudentDataForm updateStudentDataForm) throws Exception {
		String className = txn.getClassName(updateStudentDataForm);
		return className;
	}

	public boolean updateStudentData(List<UpdateStudentDataTO> list,UpdateStudentDataForm updateStudentDataForm) throws Exception {
		List<PersonalData> bo = UpdateStudentDataHelper.getInstance().convertTOtoBO(list,updateStudentDataForm);
		return txn.updateStudentData(bo);
	}
}
