package com.kp.cms.handlers.admin;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.UGCoursesBO;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.UGCoursesForm;
import com.kp.cms.helpers.admin.UGCoursesHelper;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.transactions.admin.IUGCoursesTransaction;
import com.kp.cms.transactionsimpl.admin.UGCoursesTransactionImpl;
public class UGCoursesHandler {
	public static volatile UGCoursesHandler ugCoursesHandler = null;
    public static final Log log = LogFactory.getLog(UGCoursesHandler.class);
    private UGCoursesHandler(){
    	
    }
	public static UGCoursesHandler getInstance() {
		if (ugCoursesHandler == null) {
			ugCoursesHandler = new UGCoursesHandler();
			return ugCoursesHandler;
		}
		return ugCoursesHandler;
	}

	/**
	 * 
	 * @return list of admittedThroughTO objects, will be used in UI to display.
	 * @throws Exception 
	 */
	public List<UGCoursesTO> getUGCourses() throws Exception {
		
		IUGCoursesTransaction iUGCoursesTransaction = UGCoursesTransactionImpl.getInstance();
		List<UGCoursesBO> ugCoursesList = iUGCoursesTransaction.getUGCourses();
		List<UGCoursesTO> ugCoursesTOList = UGCoursesHelper.getInstance().copyUGCoursesBosToTos(ugCoursesList);
		log.error("ending of getUGCourses method in UGCoursesHandler");
		return ugCoursesTOList;
	}

	/**
	 * 
	 * @return Map of admittedThrough <KEY,VALUE> EX :<id,name>.
	 * @throws Exception
	 */
	public Map<Integer, String> getUGCoursesMap() throws Exception {
		IUGCoursesTransaction iUGCoursesTransaction = UGCoursesTransactionImpl.getInstance();
		List<UGCoursesBO> ugCoursesList = iUGCoursesTransaction.getUGCourses();
		Map<Integer, String> ugCoursesMap = new HashMap<Integer, String>();
		Iterator<UGCoursesBO> itr = ugCoursesList.iterator();
		while (itr.hasNext()) {
			UGCoursesBO ugCourses = itr.next();
			ugCoursesMap.put(ugCourses.getId(), ugCourses.getName());
		}
		log.error("ending of getUGCourses method in UGCoursesHandler");
		return ugCoursesMap;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */
	public boolean addUGCourses(UGCoursesForm ugCoursesForm, String mode) throws Exception {
		IUGCoursesTransaction iUGCoursesTransaction = UGCoursesTransactionImpl.getInstance();
		boolean isAdded = false;

		Boolean originalNotChanged = false;
		String ugCourses = "";
		String origugCourses = "";
		if(ugCoursesForm.getUgCoursesName() != null && !ugCoursesForm.getUgCoursesName().equals("")){
			ugCourses = ugCoursesForm.getUgCoursesName().trim();
		}
		
		if(ugCoursesForm.getOrigUGCourses() != null && !ugCoursesForm.getOrigUGCourses().equals("")){
			origugCourses = ugCoursesForm.getOrigUGCourses().trim();
		}
		
		if (ugCourses.equalsIgnoreCase(origugCourses)) {
			originalNotChanged = true;
		}

		if (mode.equals("Add")) {
			originalNotChanged = false; // for add no need to check original
										// changed
		}

		if (!originalNotChanged) {
			UGCoursesBO duplugCourses = UGCoursesHelper.getInstance().populateUGCoursesDataFormForm(ugCoursesForm);

			duplugCourses = iUGCoursesTransaction.isUGCoursesDuplcated(duplugCourses);
			if (duplugCourses != null	&& duplugCourses.getIsActive() == true) {
				throw new DuplicateException();
			} else if (duplugCourses != null && duplugCourses.getIsActive() == false) {
				ugCoursesForm.setDuplId(duplugCourses.getId());
				throw new ReActivateException();
			}
		}

		//AdmittedThrough admittedThrough = new AdmittedThrough();
		UGCoursesBO ugCourses1 = UGCoursesHelper.getInstance().populateUGCoursesDataFormForm(ugCoursesForm);

		if (mode.equals("Add")) {
			ugCourses1.setCreatedBy(ugCoursesForm.getUserId());
			ugCourses1.setModifiedBy(ugCoursesForm.getUserId());
			ugCourses1.setCreatedDate(new Date());
			ugCourses1.setLastModifiedDate(new Date());
		} else // edit
		{
			ugCourses1.setModifiedBy(ugCoursesForm.getUserId());
			ugCourses1.setLastModifiedDate(new Date());

		}

		isAdded = iUGCoursesTransaction.addUGCourses(ugCourses1, mode);
		log.error("ending of getUGCourses method in UGCoursesHandler");
		return isAdded;
	}

	/**
	 * 
	 * @param
	 * 
	 * @return boolean true / false based on result.
	 */

	public boolean deleteUGCourses(int id, Boolean activate, UGCoursesForm ugCoursesForm)	throws Exception {
		IUGCoursesTransaction iUGCoursesTransaction = UGCoursesTransactionImpl.getInstance();
		boolean result = iUGCoursesTransaction.deleteUGCourses(id, activate, ugCoursesForm);
		log.error("ending of deleteUGCourses method in UGCoursesHandler");
		return result;
	}

	/**
	 * 
	 * @return list of admittedThroughTO objects, will be used in UI to dispaly.
	 * @throws Exception
	 */
	
}
